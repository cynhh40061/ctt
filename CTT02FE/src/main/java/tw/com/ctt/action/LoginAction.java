package tw.com.ctt.action;

//Import required java libraries
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.impl.LoginDaoImpl;
import tw.com.ctt.service.impl.LoginServiceImpl;
import tw.com.ctt.util.AESUtil;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "Login", urlPatterns = { "/Login" })
// Extend HttpServlet class & Implements Servlet
public class LoginAction extends BaseAction {
	private static final long serialVersionUID = -1041343440984347497L;
	private static final Logger LOG = LogManager.getLogger(LoginAction.class.getName());

	public LoginAction() {
		super();
	}

	public boolean checkLogin(HttpServletRequest req, HttpServletResponse res) {
		return true;
	}

	public void main(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			req.getRequestDispatcher("WEB-INF/Account/index.html").forward(req, res);
		} catch (ServletException e) {
			LOG.info("ServletException, " + e.getMessage());
			err(LOG, e);
		} catch (IOException e) {
			LOG.info("IOException, " + e.getMessage());
			err(LOG, e);
		}
	}

	public void login(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		String pwd;
		String accName;
		int platformId = 0;
		LoginServiceImpl service = null;
		LoginDaoImpl dao = null;
		String ip = getIpAddr(req);
		try {
			if (req.getParameter("accName") != null && req.getParameter("pwd") != null && req.getParameter("platformId") != null) {
				pwd = AESUtil.encrypt_CBC(req.getParameter("pwd").toString().trim(), KEY);
				accName = req.getParameter("accName").toString();
				platformId = Integer.parseInt(req.getParameter("platformId").toString());
				service = new LoginServiceImpl();
				dao = new LoginDaoImpl();
				service.setDao(dao);
				tmpMap = service.login(accName, pwd, ip, platformId);

			}
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
			pwd = "";
			pwd = null;
		}
	}

	public void logout(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		String tokenId;
		long accId;
		Boolean boole = false;
		LoginServiceImpl service = null;
		LoginDaoImpl dao = null;
		String ip = getIpAddr(req);
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();

			if (req.getParameter("accId").toString() != null && req.getParameter("tokenId") != null) {
				tokenId = req.getParameter("tokenId").toString();
				accId = accIdToLong(req.getParameter("accId").toString());

				if (!checkLoginSec(req, res)) {
					tmpMap.put("tokenId", "fail");
				} else {
					tmpMap.put("tokenId", "success");

					service = new LoginServiceImpl();
					dao = new LoginDaoImpl();
					service.setDao(dao);

					boole = service.logout(tokenId, accId, ip);

					tmpMap.put("isSuccess", boole);

				}
			}
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
		}
	}

	public void getPlatformAd(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		int platformId = 0;
		LoginServiceImpl service = null;
		LoginDaoImpl dao = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("platformId") != null) {
				if (!"".equals(req.getParameter("platformId").toString())) {
					platformId = Integer.parseInt(req.getParameter("platformId").toString());
					service = new LoginServiceImpl();
					dao = new LoginDaoImpl();
					service.setDao(dao);
					tmpMap.put("adData", service.getPlatformAd(platformId));
				}
			}
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
		}
	}

}
