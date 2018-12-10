package tw.com.ctt.filter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 流量控制過濾器 用於保護當前JVM進程，在過載流量下，處於穩定可控狀態。
 */
public class FlowControlFilter implements Filter {

	// 最大並發量
	private int permits = Runtime.getRuntime().availableProcessors() + 1;// 默認為500

	// 當並發量達到permits後，新的請求將會被buffer，buffer最大尺寸
	// 如果buffer已滿，則直接拒絕
	private int bufferSize = 500;//
	// buffer中的請求被阻塞，此值用於控制最大阻塞時間
	private long timeout = 30000;// 默認阻塞時間

	private String errorUrl;// 跳轉的錯誤頁面

	private BlockingQueue<Node> waitingQueue;

	private Thread selectorThread;
	private Semaphore semaphore;

	private Object lock = new Object();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String p = filterConfig.getInitParameter("permits");
		if (p != null) {
			permits = Integer.parseInt(p);
			if (permits < 0) {
				throw new IllegalArgumentException("FlowControlFilter,permits parameter should be greater than 0 !");
			}
		}

		String t = filterConfig.getInitParameter("timeout");
		if (t != null) {
			timeout = Long.parseLong(t);
			if (timeout < 1) {
				throw new IllegalArgumentException("FlowControlFilter,timeout parameter should be greater than 0 !");
			}
		}

		String b = filterConfig.getInitParameter("bufferSize");
		if (b != null) {
			bufferSize = Integer.parseInt(b);
			if (bufferSize < 0) {
				throw new IllegalArgumentException("FlowControlFilter,bufferSize parameter should be greater than 0 !");
			}
		}

		errorUrl = filterConfig.getInitParameter("errorUrl");

		waitingQueue = new LinkedBlockingQueue<>(bufferSize);
		semaphore = new Semaphore(permits);

		selectorThread = new Thread(new SelectorRunner());
		selectorThread.setDaemon(true);
		selectorThread.start();

	}

	@SuppressWarnings("static-access")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		checkSelector();
		Thread t = Thread.currentThread();
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		Node node = new Node(t, false);
		boolean buffered = waitingQueue.offer(node);
		// 如果buffer已滿
		if (!buffered) {
			if (errorUrl != null) {
				httpServletResponse.sendRedirect(errorUrl);
			}
			return;
		}
		long deadline = System.currentTimeMillis() + timeout;
		// 進入等待隊列後，當前線程阻塞
		LockSupport.parkNanos(this, TimeUnit.MICROSECONDS.toNanos(timeout));
		if (t.isInterrupted()) {
			// 如果線程是中斷返回
			t.interrupted();// clear status

		}
		// 如果等待過期，則直接返回
		// System.out.println("deadline:"+deadline);
		// System.out.println("System.currentTimeMillis():"+System.currentTimeMillis());
		if (deadline <= System.currentTimeMillis()) {
			if (errorUrl != null) {
				httpServletResponse.sendRedirect(errorUrl);
			}
			// 對信號量進行補充
			synchronized (lock) {
				if (node.dequeued) {
					semaphore.release();
				} else {
					node.dequeued = true;
				}
			}
			return;
		}
		// 繼續執行
		try {
			chain.doFilter(request, response);
			return;
		} finally {
			semaphore.release();
			checkSelector();
		}
	}

	private void checkSelector() {
		if (selectorThread != null && selectorThread.isAlive()) {
			return;
		}
		synchronized (lock) {
			if (selectorThread != null && selectorThread.isAlive()) {
				return;
			}
			selectorThread = new Thread(new SelectorRunner());
			selectorThread.setDaemon(true);
			selectorThread.start();
		}
	}

	private class SelectorRunner implements Runnable {

		@Override
		public void run() {
			try {
				while (true) {
					Node node = waitingQueue.take();
					// 如果t，阻塞逃逸，只能在pack超時後退出
					synchronized (lock) {
						if (node.dequeued) {
							// 如果此線程已經pack過期而退出了，則直接忽略
							continue;
						} else {
							node.dequeued = true;
						}

					}
					semaphore.acquire();
					LockSupport.unpark(node.currentThread);
				}
			} catch (Exception e) {
				//
			} finally {
				// 全部釋放阻塞
				Queue<Node> queue = new LinkedList<>();
				waitingQueue.drainTo(queue);
				for (Node n : queue) {
					if (!n.dequeued) {
						LockSupport.unpark(n.currentThread);
					}
				}
			}
		}
	}

	private class Node {
		Thread currentThread;
		boolean dequeued;// 是否已經出隊

		public Node(Thread t, boolean dequeued) {
			this.currentThread = t;
			this.dequeued = dequeued;
		}
	}

	@Override
	public void destroy() {
		selectorThread.interrupt();
	}

}