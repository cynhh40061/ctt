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

import tw.com.ctt.dao.impl.CheckPwdDaoImpl;
import tw.com.ctt.service.impl.CheckPwdServiceImpl;
import tw.com.ctt.util.AESUtil;

@WebServlet(name = "Login", urlPatterns = { "/Login" })
// Extend HttpServlet class & Implements Servlet
public class LoginAction extends BaseAction {
	private static final long serialVersionUID = -1041343440984347497L;
	private static final Logger LOG = LogManager.getLogger(LoginAction.class.getName());

	private String[] loginURL = { "/Login" };
	private String[] loginCheckURL = { "/Login!login" };
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = { "/Login!logout" };
	private String[] authURL = {};

	public LoginAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("LoginAction start");
	}

	public boolean checkLogin() {
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

	// login
	public void login(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> result = null;
		CheckPwdDaoImpl dao = null;
		CheckPwdServiceImpl service = null;
		Map<String, Object> tmpMap = null;
		String userName = checkStringEmpty(req.getParameter("userName")) ? "" : escapeFormInput(req.getParameter("userName"));

		String userIp = getIpAddr(req);

		if (req.getParameterMap().size() > 0 && !"".equals(userName)) {
			result = new ConcurrentHashMap<String, Object>();

			dao = new CheckPwdDaoImpl();
			service = new CheckPwdServiceImpl();
			service.setDao(dao);

			String pwd = AESUtil.encrypt_CBC(checkPWD(req.getParameter("pwd").toString()), KEY);

			if (service.login(result, userName, pwd, userIp)) {
				tmpMap = (Map<String, Object>) result.get("basic");
				req.getSession().setAttribute("accName", tmpMap.get("acc_name").toString());
				req.getSession().setAttribute("accId", tmpMap.get("acc_id").toString());
				req.getSession().setAttribute("nickname", checkNameOut(tmpMap.get("nickname").toString()));
				req.getSession().setAttribute("acc_level_type", tmpMap.get("acc_level_type").toString());

				LOG.debug("seesion ID:" + req.getSession().getId());

				result.put("message", "success");
			} else {

				result.put("message", "fail");

				req.getSession().setAttribute("accName", null);
				req.getSession().setAttribute("accId", null);
				req.getSession().setAttribute("nickname", null);
				req.getSession().setAttribute("acc_level_type", null);
				req.getSession().removeAttribute("accName");
				req.getSession().removeAttribute("accId");
				req.getSession().removeAttribute("nickname");
				req.getSession().removeAttribute("acc_level_type");
			}
		} else {
			result.put("message", "fail");

			req.getSession().setAttribute("accName", null);
			req.getSession().setAttribute("accId", null);
			req.getSession().setAttribute("nickname", null);
			req.getSession().setAttribute("acc_level_type", null);
			req.getSession().removeAttribute("accName");
			req.getSession().removeAttribute("accId");
			req.getSession().removeAttribute("nickname");
			req.getSession().removeAttribute("acc_level_type");
		}

		userName = "";
		userName = null;
		JSONObject responseJSONObject = new JSONObject(result);
		PrintWriter out = res.getWriter();
		out.println(responseJSONObject);
		responseJSONObject = null;
		out.flush();
		out.close();
		out = null;
		if (dao != null) {
			dao.close();
			dao = null;
		}
		if (service != null) {
			service.close();
			service = null;
		}

		return;
	}

	// logout
	public void logout(HttpServletRequest req, HttpServletResponse res) throws Exception {
		boolean boole = false;
		CheckPwdDaoImpl dao = null;
		CheckPwdServiceImpl service = null;
		Map<String, Object> tmpMap = null;
		String tokenId;
		long accId;
		String userName;
		String userIp;
		LOG.debug("logout");
		tmpMap = new ConcurrentHashMap<String, Object>();

		tokenId = checkStringEmpty(req.getParameter("tokenId")) ? "" : escapeFormInput(req.getParameter("tokenId"));
		accId = req.getSession().getAttribute("accId") != null ? accIdToLong(req.getSession().getAttribute("accId").toString()) : 0;
		userName = req.getSession().getAttribute("accName") != null ? req.getSession().getAttribute("accName").toString() : "";
		LOG.debug(accId + ">>>" + userName);
		userIp = getIpAddr(req);
		LOG.debug(userIp);
		LOG.debug(userIp);

		if (req.getParameterMap().size() > 0 && !"".equals(tokenId) && accId != 0 && !"".equals(userName)) {

			dao = new CheckPwdDaoImpl();
			service = new CheckPwdServiceImpl();
			service.setDao(dao);

			boole = service.logout(accId, userName, tokenId, userIp);
			if (boole) {
				req.getSession().setAttribute("accName", null);
				req.getSession().setAttribute("accId", null);
				req.getSession().setAttribute("nickname", null);
				req.getSession().setAttribute("acc_level_type", null);
				req.getSession().removeAttribute("accName");
				req.getSession().removeAttribute("accId");
				req.getSession().removeAttribute("nickname");
				req.getSession().removeAttribute("acc_level_type");

				tmpMap.put("message", "success");
			} else {
				tmpMap.put("message", "fail");
			}
		} else {

			tmpMap.put("message", "fail");
		}
		JSONObject responseJSONObject = new JSONObject(tmpMap);
		PrintWriter out = res.getWriter();
		out.println(responseJSONObject);
		responseJSONObject = null;
		out.flush();
		out.close();
		out = null;
		if (dao != null) {
			dao.close();
			dao = null;
		}
		if (service != null) {
			service.close();
			service = null;
		}
		return;
	}

}
