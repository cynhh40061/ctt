package tw.com.ctt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StaticResourceDispatchFilter implements Filter {

	// private FilterConfig fc;

	private String exts = "xhtml,php";
	public final String ACTION_METHOD = "theActionMethod";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		// System.out.println("StaticResourceDispatchFilter");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		String ext = uri.indexOf(".") == -1 ? "" : uri.substring(uri.lastIndexOf(".") + 1);
		String[] tmps = exts.split(",");
		boolean todo = false;
		if (!uri.endsWith("/") && !"".equals(ext)) {
			for (int i = 0; i < tmps.length; i++) {
				if (tmps[i].trim().equalsIgnoreCase(ext.trim())) {
					todo = true;
					break;
				}
			}
		} else if (req.getContextPath().indexOf("\\.") == -1) {
			todo = true;
		}
		req.setAttribute(ACTION_METHOD, null);
		req.removeAttribute(ACTION_METHOD);
		if (todo) {
			String url = "";
			url = uri.split(req.getContextPath())[1].split("\\.")[0];
			// System.out.println(url);
			if (uri.indexOf("!") != -1) {
				req.setAttribute(ACTION_METHOD, url.split("!")[1]);
				url = url.split("!")[0];
				if (uri.indexOf("?") != -1) {
					url += "?" + uri.split("?")[1];
				} else {
					// do nothing
				}
			} else {
				// do nothing
			}
			request.getRequestDispatcher(url).forward(req, res);
		} else {
			filterChain.doFilter(request, response);
		}
		// String url = uri.split(req.getContextPath())[1].split("\\.")[0];
		// RequestDispatcher requestDispatcher =
		// fc.getServletContext().getRequestDispatcher(url);
		// requestDispatcher.forward(request, response);
		// requestDispatcher.forward(req, res);
		// request.getRequestDispatcher(url).forward(request, response);
		// req.getSession().getServletContext().getRequestDispatcher(url).forward(req,
		// res);
		return;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String encodingParam = filterConfig.getInitParameter("exts");
		if (encodingParam != null) {
			exts = encodingParam;
		}
		// fc = filterConfig;
	}

	public void destroy() {
		// nothing todo
	}

}
