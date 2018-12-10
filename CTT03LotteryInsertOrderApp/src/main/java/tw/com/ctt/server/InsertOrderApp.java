package tw.com.ctt.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.dao.impl.MemberBetDaoImpl;
import tw.com.ctt.dao.impl.MemberBetDaoImpl_ExportFile;
import tw.com.ctt.model.ServerInfo;
import tw.com.ctt.service.impl.MemberBetServiceImpl;
import tw.com.ctt.util.DataSource;

/**
 * this is a application for betting order, this app will connect to localhost
 * redis, and query the last order from CTT03BetOrder. then, insert the order to
 * DB
 * 
 * this app will create many thread to insert orders, 7 threads makes a good
 * performance. and we can config how many orders to insert in one thread once.
 * 
 * because we put many orders in one insert cmd to enhance performance, 1000
 * cmds makes a good performance.
 * 
 * 
 * @author Quanto
 *
 */
public class InsertOrderApp {

	int MAX_WORK_THREAD = 1;
	int MAX_WORK_CMD = 1;

	public static List<String> getListMultValueAfterDel(Jedis jedis, String key, int start, int end) {
		List<Object> list = null;
		List<String> listStr = new ArrayList<String>();
		try {
			Transaction ts = jedis.multi();
			ts.lrange(key, start, end);
			ts.ltrim(key, end + 1, -1);
			list = ts.exec();
		} catch (Exception e) {
		}
		if (list != null && !list.isEmpty()) {
			try {
				listStr = (ArrayList<String>) list.get(0);
			} catch (Exception e) {

			}
		} else {
			return Collections.emptyList();
		}
		return listStr;
	}

	public Service getService() {
		return new Service();
	}

	public Request getRequest(List<String> orderFromRedis) {
		return new Request(orderFromRedis);
	}

	/**
	 * main function will create redis connection, and run into a while loop
	 * forever, then when receive any cmd from redis, it will use service to get a
	 * work thread and create a request for it.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		InsertOrderApp app = new InsertOrderApp();
		app.contextInitialized();
		Jedis jedis = new Jedis("localhost");
		Service serviceForPlayerAndRooms = app.getService();
		System.out.println(" MAIN WHILE START");
		while (true) {
			if (serviceForPlayerAndRooms.pool.isAnyIdle()) {
//				boolean isJedisAlive = false;
//				while (!isJedisAlive) {
//					try {
//						jedis.ping();
//						isJedisAlive = true;
//					} catch (Exception e) {
//						try {
//							Thread.sleep(500);
//						} catch (InterruptedException e2) {
//							e2.printStackTrace();
//						}
//						e.printStackTrace();
//						jedis.close();
//						jedis = null;
//						jedis = new Jedis("localhost");
//					}
//				}

				List<String> cmdList = new ArrayList<String>();
				while (cmdList.size() < app.MAX_WORK_CMD) {
					String orderFromRedis = jedis.rpop("LottOrder");
					if (orderFromRedis != null && !"".equals(orderFromRedis)) {
						cmdList.add(orderFromRedis);
					} else {
						break;
					}
				}
				if (cmdList.size() > 0) {
					Request request = app.getRequest(cmdList);
					serviceForPlayerAndRooms.accept(request);
				}
			} else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * init DB parameters.
	 */
	public void contextInitialized() {
		Connection connR = null;
		Connection connW = null;
		try {
			CommandConstant.SERVER_NAME = java.net.InetAddress.getLocalHost().getHostName() == null ? ""
					: java.net.InetAddress.getLocalHost().getHostName();
			CommandConstant.SERVER_IP = java.net.InetAddress.getLocalHost().getHostAddress() == null ? ""
					: java.net.InetAddress.getLocalHost().getHostAddress();

			connR = DataSource.getReadConnection();
			DataSource.returnReadConnection(connR);
			connW = DataSource.getWriteConnection();
			DataSource.returnWriteConnection(connW);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
	}

	/**
	 * Request class
	 * 
	 * @author Quanto
	 *
	 */
	public class Request {
		List<String> betData;

		Request(List<String> data) {
			betData = data;
		}

		public List<String> execute() {
			return betData;
		}
	}

	/**
	 * service class, include a workthread pool.
	 * 
	 * @author Quanto
	 *
	 */
	public class Service {
		private WorkerThreadPool pool;

		Service() {
			pool = new WorkerThreadPool();
		}

		void accept(Request request) {
			pool.service(request);
		}
	}

	/**
	 * workthread pool, to manage all workthread
	 * 
	 * @author Quanto
	 *
	 */
	public class WorkerThreadPool {
		private List<ExecuteRoomThread> workerThreads;

		WorkerThreadPool() {
			workerThreads = new ArrayList<ExecuteRoomThread>();
			for (int i = 0; i < MAX_WORK_THREAD; i++) {
				ExecuteRoomThread workerThread = createWorkerThread();
				workerThread.setRequest(null);
				workerThreads.add(workerThread);
			}
		}

		/**
		 * insert a request to a idle workthread. or create one.
		 * 
		 * @param request
		 */
		synchronized void service(Request request) {
			boolean idleNotFound = true;
			for (ExecuteRoomThread workerThread : workerThreads) {
				if (workerThread.isIdle()) {
					workerThread.setRequest(request);
					idleNotFound = false;
					break;
				}
			}
			if (idleNotFound) {
				ExecuteRoomThread workerThread = createWorkerThread();
				workerThread.setRequest(request);
				workerThreads.add(workerThread);
			}
		}

		synchronized void cleanIdle() {
			for (ExecuteRoomThread workerThread : workerThreads) {
				if (workerThread.isIdle()) {
					workerThreads.remove(workerThread);
					workerThread.terminate();
				}
			}
		}

		synchronized boolean isAllIdle() {
			for (ExecuteRoomThread workerThread : workerThreads) {
				if (!workerThread.isIdle()) {
					return false;
				}
			}
			return true;
		}

		synchronized boolean isAnyIdle() {
			for (ExecuteRoomThread workerThread : workerThreads) {
				if (workerThread.isIdle()) {
					return true;
				}
			}
			return false;
		}

		private ExecuteRoomThread createWorkerThread() {
			ExecuteRoomThread workerThread = new ExecuteRoomThread();
			workerThread.start();
			try {
				Thread.sleep(10); // 給點時間進入 Runnable
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return workerThread;
		}
	}

	/**
	 * workThread class, we can make a lot of work thread, once work thread get a
	 * request, it will be notify.
	 * 
	 * and when the request is done, the thread will "wait" until get another
	 * request .
	 * 
	 * @author Quanto
	 *
	 */
	public class ExecuteRoomThread extends Thread {
		List<Long> idList;
		private Request request;
		private boolean isContinue = true;

		MemberBetServiceImpl service = null;
		MemberBetDaoImpl dao = null;

		boolean isIdle() {
			return request == null;
		}

		void setRequest(Request request) {
			synchronized (this) {
				if (isIdle()) {
					this.request = request;
					notify();
				}
			}
		}

		void terminate() {
			isContinue = false;
			setRequest(null);
		}

		/**
		 * using this function to insert orders, 1. parse all orders and generate the
		 * insert sql into file. 2. execute mysql with the file. 3. delete file. 4.
		 * update money / noOfBet into baseline table. 5. check if the baseline of high
		 * frequency has been modified and sent URL to update data if need.
		 */
		public void run() {
			while (isContinue) {
				synchronized (this) {
					if (request != null) {
						List<String> cmdFromRedis = request.execute();
						if (cmdFromRedis.size() > 0) {
							boolean todo = true;
							String fileName = "";
							FileWriter fw = null;
							if (todo) {
								if (dao.getClass().getName().indexOf("MemberBetDaoImpl_ExportFile") != -1) {
									if (SystemUtils.IS_OS_WINDOWS) {
										fileName = "c:\\work\\f_allSQL" + new Date().getTime()
												+ (int) (Math.random() * 100) + (int) (Math.random() * 10);
									} else {
										fileName = "/tmp/f_allSQL" + new Date().getTime() + (int) (Math.random() * 100)
												+ (int) (Math.random() * 10);
									}
									try {
										fw = new FileWriter(fileName);
									} catch (IOException e) {
										e.printStackTrace();
									}
									boolean result = service.addMainOrder_ExportFile(cmdFromRedis, fw);
									todo &= result;
//									result = service.addMainOrderBetRatio_ExportFile(cmdFromRedis,fw);
//									todo &= result;
									try {
										fw.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
									if (todo) {
										if (SystemUtils.IS_OS_WINDOWS) {
//											String[] tmpAry = { "cmd", "/C",
//													"c:\\work\\tool\\mysql.exe -uCTT_AP -p1qazxsw2 -h"+tw.com.ctt.util.Configuration.WRITE_DB_IP+" ctt_manager < " + fileName
//															+ " && del " + fileName };

											String[] tmpAry = { "cmd", "/C",
													"c:\\work\\tool\\mysql.exe -u"
															+ tw.com.ctt.util.Configuration.WRITE_DB_USER_NAME + " -p"
															+ tw.com.ctt.util.Configuration.WRITE_DB_PASSWORD + " -h"
															+ tw.com.ctt.util.Configuration.WRITE_DB_IP
															+ " ctt_manager < " + fileName + ">> c:\\work\\debug.txt" };
											try {
												@SuppressWarnings("unused")
												Process proc = Runtime.getRuntime().exec(tmpAry);
											} catch (IOException e) {
												e.printStackTrace();
											}
										} else {
											String[] tmpAry = { "/bin/bash", "-c",
													"/opt/mysql/bin/mysql -u"
															+ tw.com.ctt.util.Configuration.WRITE_DB_USER_NAME + " -p"
															+ tw.com.ctt.util.Configuration.WRITE_DB_PASSWORD + " -h"
															+ tw.com.ctt.util.Configuration.WRITE_DB_IP
															+ " ctt_manager < " + fileName + " && rm -rf " + fileName };
											try {
												@SuppressWarnings("unused")
												Process proc = Runtime.getRuntime().exec(tmpAry);
											} catch (IOException e) {
												e.printStackTrace();
											}
										}
									} else {
										File ff = new File(fileName);
										ff.setWritable(true);
										ff.delete();
									}
									fileName = "";
									fw = null;
								} else {
									boolean result = service.addMainOrder(cmdFromRedis);
									todo &= result;
								}
							}
							if (todo) {
								if (true) {
									// dao.getClass().getName().indexOf("MemberBetDaoImpl_ExportFile") == -1) {
									int result = service.addMainOrderBetRatio(cmdFromRedis);
									if (result == 2) {
										List<ServerInfo> list = service.getServerInfo("/CTT03BetOrder", true);
										System.out.println(list.toString() + "\t" + list.size());
										if (list != null && !list.isEmpty() && list.size() > 0) {
											for (ServerInfo si : list) {
												if (si.getIp().startsWith("127.") || (CommandConstant.SERVER_IP != null
														&& si.getIp().equals(CommandConstant.SERVER_IP))) {
													si.setIp("127.0.0.1");
													if (si.getIp().startsWith("127.")
															|| (CommandConstant.SERVER_IP != null
																	&& si.getIp().equals(CommandConstant.SERVER_IP))) {
														si.setIp("127.0.0.1");
													}
												}
												System.out.println("http://" + si.getIp() + ":" + si.getPort()
														+ "/CTT03BetOrder/UpdateInfo!updateBaseline.php?date="
														+ new Date().getTime());
												dao.sentURL("http://" + si.getIp() + ":" + si.getPort()
														+ "/CTT03BetOrder/UpdateInfo!updateBaseline.php?date="
														+ new Date().getTime(), "", "", "");
											}
										}
										list = service.getServerInfo("/CTT03QueryInfo", true);
										System.out.println(list.toString() + "\t" + list.size());
										if (list != null && !list.isEmpty() && list.size() > 0) {
											for (ServerInfo si : list) {
												if (si.getIp().startsWith("127.") || (CommandConstant.SERVER_IP != null
														&& si.getIp().equals(CommandConstant.SERVER_IP))) {
													si.setIp("127.0.0.1");
												}
												System.out.println("http://" + si.getIp() + ":" + si.getPort()
														+ "/CTT03QueryInfo/UpdateInfo!updateBaseline.php?date="
														+ new Date().getTime());
												dao.sentURL("http://" + si.getIp() + ":" + si.getPort()
														+ "/CTT03QueryInfo/UpdateInfo!updateBaseline.php?date="
														+ new Date().getTime(), "", "", "");
											}
										}
									}
								}
							}
						}
						request = null;
					} else {
						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		public ExecuteRoomThread() {
			service = new MemberBetServiceImpl();
			dao = new MemberBetDaoImpl_ExportFile();
			service.setDao(dao);
		}

	}
}
