package tw.com.ctt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
// import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CheckLoginFilter implements Filter {

	private String exts = "xhtml,php";

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		// System.out.println("CheckLoginFilter");
		HttpServletRequest requ = (HttpServletRequest) req;
		HttpSession s = requ.getSession();
		String uri = requ.getRequestURI();
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
		} else if ("".equals(ext)) {
			todo = true;
		}

		if (uri.substring(uri.lastIndexOf(".") + 1).split(".").equals("Login")) {
			todo = false;
		}
		if (todo) {
			if (s.getAttribute("user") != null) {
				// logined
			} else {
				// forward to login page
				// String url = "/Login.html";
				// requ.getRequestDispatcher(url).forward(req, resp);
				// return;
				chain.doFilter(req, resp);
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
		}
		return;
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
