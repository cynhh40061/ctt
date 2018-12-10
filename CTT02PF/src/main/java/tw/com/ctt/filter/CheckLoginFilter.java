package tw.com.ctt.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
// import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.dao.impl.BaseDao;

public class CheckLoginFilter implements Filter {

	private String exts = "xhtml,php";

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		// System.out.println("CheckLoginFilter");
		HttpServletRequest requ = (HttpServletRequest) req;
		HttpSession s = null;
		if (requ != null && requ.getSession() != null) {
			s = requ.getSession();
		}
		String uri = requ.getRequestURI();
		String ext = uri.indexOf(".") == -1 ? "" : uri.substring(uri.lastIndexOf(".") + 1);
		String[] tmps = exts.split(",");
		boolean todo = false;
		// 404 500
		if (uri.indexOf("/404") != -1 || uri.indexOf("/building.xhtml") != -1 || uri.indexOf("/500") != -1 || uri.indexOf("/error.xhtml") != -1) {
			req.getRequestDispatcher(uri).forward(req, resp);
			return;
		}
		// over 60 mins not update SERVERS_IP_LIST, will be called checkServerIPs
		if (new Date().getTime() - CommandConstant.CHECK_SERVERS_IP_TIME > 60 * 60 * 1000) {
			BaseDao dao = new BaseDao();
			dao.checkServerIPs();
			dao.close();
			dao = null;
		}
		// over 10 mins not update BLACK_WHITE_LIST, will be called checkBlackWhiteIPs
		if (new Date().getTime() - CommandConstant.CHECK_BLACK_WHITE_TIME > 10 * 60 * 1000) {
			BaseDao dao = new BaseDao();
			dao.checkBlackWhiteIPs();
			dao.close();
			dao = null;
		}
		// check remoteAddr in WHITE_LIST or BLACK_LIST
//		boolean allow = false;
//		if (CommandConstant.WHITE_LIST != null && !CommandConstant.WHITE_LIST.isEmpty() && CommandConstant.WHITE_LIST.size() > 0) {
//			String remoteAddr = req.getRemoteAddr();
//			if (remoteAddr == null || remoteAddr.isEmpty() || remoteAddr.split("\\.").length != 4) {
//				req.getRequestDispatcher("/building.xhtml").forward(req, resp);
//				return;
//			}
//			if (CommandConstant.WHITE_LIST.contains(remoteAddr)
//					|| CommandConstant.WHITE_LIST.contains(remoteAddr.substring(0, remoteAddr.lastIndexOf(".")))) {
//				// allow
//				allow = true;
//			}
//			remoteAddr = "";
//			remoteAddr = null;
//		}
//		if (!allow && CommandConstant.BLACK_LIST != null && !CommandConstant.BLACK_LIST.isEmpty() && CommandConstant.BLACK_LIST.size() > 0) {
//			String remoteAddr = req.getRemoteAddr();
//			if (remoteAddr == null || remoteAddr.isEmpty() || remoteAddr.split("\\.").length != 4) {
//				req.getRequestDispatcher("/building.xhtml").forward(req, resp);
//				return;
//			}
//			if (CommandConstant.BLACK_LIST.contains(remoteAddr)
//					|| CommandConstant.BLACK_LIST.contains(remoteAddr.substring(0, remoteAddr.lastIndexOf(".")))) {
//				req.getRequestDispatcher("/building.xhtml").forward(req, resp);
//				return;
//			}
//			remoteAddr = "";
//			remoteAddr = null;
//			allow = true;
//		}
//		allow = false;
		if (!uri.endsWith("/") && !"".equals(ext)) {
			for (int i = 0; i < tmps.length; i++) {
				if (tmps[i].trim().equalsIgnoreCase(ext.trim())) {
					todo = true;
					break;
				}
			}
		} else if ("".equals(ext)) {
			todo = true;
		}
		if (uri.substring(uri.lastIndexOf(".") + 1).split(".").equals("Login")) {
			todo = false;
		}
		// if (allowURLs != null && allowURLs.length > 0) {
		// for (int i = 0; i < allowURLs.length; i++) {
		// if (uri.indexOf(allowURLs[i]) != -1) {
		// chain.doFilter(req, resp);
		// return;
		// }
		// }
		// }
		if (todo) {
			if (s != null ? s.getAttribute("accIdxxxXXX") != null : false) {
				// logined
				req.getRequestDispatcher("/building.xhtml").forward(req, resp);
				return;
			} else {
				// forward to login page
				// String url = "/Login.html";
				// requ.getRequestDispatcher(url).forward(req, resp);
				// return;
				chain.doFilter(req, resp);
				return;
				// Cookie[] cookies = requ.getCookies();
				// if (cookies != null) {
				// for (Cookie ck : cookies) {
				// if (ck.getName().equals("autologin")) {
				// // 自動登入
				// String val = ck.getValue();
				// String[] vals = val.split("@");
				// s.setAttribute("user", vals[0]);
				// }
				// }
				// }
			}
		} else {
			chain.doFilter(req, resp);
			return;
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String encodingParam = filterConfig.getInitParameter("exts");
		if (encodingParam != null) {
			exts = encodingParam;
		}
	}

	public void destroy() {
		// nothing todo
	}

}
