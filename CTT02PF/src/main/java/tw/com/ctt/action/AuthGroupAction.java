package tw.com.ctt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.impl.AuthDaoImpl;
import tw.com.ctt.service.impl.AuthServiceImpl;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "AuthGroup", urlPatterns = { "/AuthGroup" })
public class AuthGroupAction extends BaseAction {

	private static final long serialVersionUID = -7637371133919643134L;
	private static final Logger LOG = LogManager.getLogger(LoginAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = {};
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = { "/AuthGroup!getAuthGroup", "/AuthGroup!addAuth", "/AuthGroup!updateAuthGroup", "/AuthGroup!createAuthGroup",
			"/AuthGroup!checkAuthGroupName", "/AuthGroup!portopnGroupToAccLevel", "/AuthGroup!updayeLevelTypeGroup", "/AuthGroup!checkAuthRemarkText",
			"/AuthGroup!getCreateAuthGroup", "/AuthGroup!getUpdateAuthGroup", "/AuthGroup!getUpLevelAuth" };
	private String[] authURL = { "/AuthGroup", "/AuthGroup!getCreateAuthGroup", "/AuthGroup!getUpLevelAuth", "/AuthGroup!portopnGroupToAccLevel" };

	public AuthGroupAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("AuthGroupAction start");
	}

	public void main(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("AuthGroupAction.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial blackWhite.js
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_AG_checkAuthGroup(){\n");
			sb.append("    if(typeof authGroup==='undefined' || typeof authGroup != 'function' ){\n");
			sb.append("        checkJS('authGroup', 'AccountManage/ManagerAuth.js');\n");
			sb.append("    } else {\n");
			sb.append("        authGroup();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_AG_checkAuthGroup();\n");
			// clean extraJS
			sb.append("if(document.getElementById('extraJS') != null){\n");
			sb.append("    document.getElementById('extraJS').innerHTML = '';\n");
			sb.append("}\n");
			tmpMap.put("extraJS", sb.toString());
			if (tmpMap != null && !tmpMap.isEmpty() && tmpMap.size() > 0) {
				responseJSONObject = new JSONObject(tmpMap);
				out = res.getWriter();
				out.println(responseJSONObject);
				out.flush();
			}
		} catch (Exception e) {
			LOG.error("AuthGroupAction.main.Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			tmpMap = null;
			responseJSONObject = null;
			out = null;
		}
		return;
	}

	public void getAuthGroup(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		String userIp = null;
		List<Map<String, Object>> listMap = null;
		AuthServiceImpl service = null;
		AuthDaoImpl dao = null;
		Map<String, Object> tmpMap = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			listMap = new ArrayList<Map<String, Object>>();
			service = new AuthServiceImpl(userId, userIp);
			dao = new AuthDaoImpl();
			service.setDao(dao);

			listMap = service.getAuthGroup(userId, Integer.parseInt("" + req.getSession().getAttribute("acc_level_type")));

			tmpMap.put("data", listMap);

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

	public void addAuth(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("addAuth");
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;

		AuthServiceImpl service = null;
		AuthDaoImpl dao = null;
		int authLevelType = 0;
		int level1 = 0;
		int level2 = 0;
		String authName = null;
		String authRemark = null;
		String url = null;
		int adminAuth = 1;

		int result = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();

			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			service = new AuthServiceImpl(userId, userIp);
			dao = new AuthDaoImpl();
			service.setDao(dao);
			authLevelType = Integer.parseInt("" + req.getParameter("levelType"));
			level1 = Integer.parseInt("" + req.getParameter("level1"));
			level2 = Integer.parseInt("" + req.getParameter("level2"));
			authName = req.getParameter("authName");
			authRemark = req.getParameter("authRemark");
			adminAuth = Integer.parseInt(req.getParameter("adminAuth"));
			url = req.getParameter("addUrl");

			result = service.addAuth(authLevelType, level1, level2, authName, authRemark, url, adminAuth);

			if (result > 0) {
				tmpMap.put("Action", "addAuth");
				tmpMap.put("Exception", 1);
				tmpMap.put("result", "Y");
			} else {
				tmpMap.put("Action", "addAuth");
				tmpMap.put("Exception", 1);
				tmpMap.put("result", "N");
			}

			LOG.debug(tmpMap.toString());
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			tmpMap.put("result", "N");
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

			userId = 0;
			userIp = null;
			authLevelType = 0;
			level1 = 0;
			level2 = 0;
			authName = null;
			authRemark = null;
			url = null;
			result = 0;
		}
		return;
	}

	public void updateAuthGroup(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("updateAuthGroup");
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;
		AuthServiceImpl service = null;
		AuthDaoImpl dao = null;
		int groupId = 0;
		String authListStr = null;
		String[] authList = null;
		boolean updateBoolean = false;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			service = new AuthServiceImpl(userId, userIp);
			dao = new AuthDaoImpl();
			service.setDao(dao);
			groupId = Integer.parseInt("" + req.getParameter("groupId"));
			authListStr = req.getParameter("authList");
			if (authListStr != null && !"".equals(authListStr)) {
				authList = authListStr.split(",");
			}
			updateBoolean = service.updateAuthGroup(groupId, authList);

			if (updateBoolean) {
				tmpMap.put("result", "Y");
			} else {
				tmpMap.put("result", "N");
			}

			LOG.debug(tmpMap.toString());
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			tmpMap.put("result", "N");
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

			userId = 0;
			userIp = null;
			groupId = 0;
			authListStr = null;
			authList = null;
			updateBoolean = false;
		}
		return;
	}

	public void createAuthGroup(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("createAuthGroup");
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;
		AuthServiceImpl service = null;
		AuthDaoImpl dao = null;
		String groupName = null;
		String authListStr = null;
		String[] authList = null;
		boolean checkGroupNameboo = false;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			service = new AuthServiceImpl(userId, userIp);
			dao = new AuthDaoImpl();
			service.setDao(dao);

			groupName = req.getParameter("groupName");

			authListStr = req.getParameter("authList");

			if (authListStr != null && !"".equals(authListStr)) {
				authList = authListStr.split(",");
			}

			checkGroupNameboo = service.checkAuthGroupName(groupName);

			if (checkGroupNameboo) {
				boolean createBoolean = service.createAuthGroup(groupName, authList);
				if (createBoolean) {
					tmpMap.put("message", "success");
					tmpMap.put("result", "Y");
				} else {
					tmpMap.put("message", "fail");
					tmpMap.put("result", "N");
				}
			} else {
				tmpMap.put("message", "duplicate");
				tmpMap.put("result", "N");
			}
			LOG.debug(tmpMap.toString());
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			tmpMap.put("result", "N");
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

			userId = 0;
			userIp = null;
			groupName = null;
			authListStr = null;
			authList = null;
			checkGroupNameboo = false;
		}
		return;
	}

	public void checkAuthGroupName(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("checkAuthGroupName");
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;
		AuthServiceImpl service = null;
		AuthDaoImpl dao = null;
		String groupName = null;
		boolean checkGroupNameboo = false;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			service = new AuthServiceImpl(userId, userIp);
			dao = new AuthDaoImpl();
			service.setDao(dao);
			groupName = req.getParameter("groupName");
			checkGroupNameboo = service.checkAuthGroupName(groupName);

			if (checkGroupNameboo) {
				tmpMap.put("message", "success");
				tmpMap.put("result", "Y");
			} else {
				tmpMap.put("message", "fail");
				tmpMap.put("result", "N");
			}
			LOG.debug(tmpMap.toString());
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			tmpMap.put("result", "N");
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

			userId = 0;
			userIp = null;
			groupName = null;
			checkGroupNameboo = false;
		}
		return;
	}

	public void portopnGroupToAccLevel(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("portopnGroupLevelType");
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;
		AuthServiceImpl service = null;
		AuthDaoImpl dao = null;
		List<Map<String, Object>> listMap = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);

			service = new AuthServiceImpl(userId, userIp);
			dao = new AuthDaoImpl();
			service.setDao(dao);
			listMap = service.portopnGroupToAccLevel(Integer.parseInt("" + req.getSession().getAttribute("acc_level_type")));

			tmpMap.put("result", listMap);
			LOG.debug(tmpMap.toString());
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
			if (listMap != null) {
				listMap.clear();
				listMap = null;
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

			userId = 0;
			userIp = null;
		}
		return;
	}

	public void updayeLevelTypeGroup(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("updayeLevelTypeGroup");
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;
		AuthServiceImpl service = new AuthServiceImpl();
		AuthDaoImpl dao = null;
		String groupIdStr = null;
		int levelType = 0;
		String[] groupIdList = null;
		boolean checkUpdateResult = false;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			service = new AuthServiceImpl(userId, userIp);
			dao = new AuthDaoImpl();
			service.setDao(dao);
			groupIdStr = req.getParameter("groupIdStr");
			levelType = Integer.parseInt("" + req.getParameter("levelType"));
			if (groupIdStr != null && !"".equals(groupIdStr)) {
				groupIdList = groupIdStr.split(",");
			}
			checkUpdateResult = service.updateLevelTypeGroup(levelType, groupIdList);

			if (checkUpdateResult) {
				tmpMap.put("message", "success");
				tmpMap.put("result", "Y");
			} else {
				tmpMap.put("message", "fail");
				tmpMap.put("result", "N");
			}
			LOG.debug(tmpMap.toString());
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			tmpMap.put("result", "N");
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

			userId = 0;
			userIp = null;
			groupIdStr = null;
			levelType = 0;
			groupIdList = null;
			checkUpdateResult = false;
		}
		return;
	}

	public void checkAuthRemarkText(HttpServletRequest req, HttpServletResponse res) throws IOException {
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;
		AuthServiceImpl service = null;
		AuthDaoImpl dao = null;
		String AuthRemarkText = null;
		boolean checkAuthRemarkTextboo = false;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			service = new AuthServiceImpl(userId, userIp);
			dao = new AuthDaoImpl();
			service.setDao(dao);
			AuthRemarkText = req.getParameter("AuthRemark");
			checkAuthRemarkTextboo = service.checkAuthRemarkText(AuthRemarkText);

			if (checkAuthRemarkTextboo) {
				tmpMap.put("message", "success");
				tmpMap.put("result", "Y");
			} else {
				tmpMap.put("message", "fail");
				tmpMap.put("result", "N");
			}
			LOG.debug(tmpMap.toString());
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			tmpMap.put("result", "N");
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

			userId = 0;
			userIp = null;
			AuthRemarkText = null;
			checkAuthRemarkTextboo = false;
		}
		return;
	}

	public void getCreateAuthGroup(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("getAuthGroup");
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;
		List<Map<String, Object>> listMap = null;
		AuthServiceImpl service = null;
		AuthDaoImpl dao = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			listMap = new ArrayList<Map<String, Object>>();

			service = new AuthServiceImpl(userId, userIp);
			dao = new AuthDaoImpl();
			service.setDao(dao);

			listMap = service.getAuthList();

			tmpMap.put("result", listMap);
			LOG.debug(tmpMap.toString());
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			tmpMap.put("result", "N");
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (listMap != null) {
				listMap.clear();
				listMap = null;
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

			userId = 0;
			userIp = null;
		}
		return;
	}

	public void getUpdateAuthGroup(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("getAuthGroup");
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;
		List<Map<String, Object>> listMap = null;
		AuthServiceImpl service = null;
		AuthDaoImpl dao = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			listMap = new ArrayList<Map<String, Object>>();
			service = new AuthServiceImpl(userId, userIp);
			dao = new AuthDaoImpl();
			service.setDao(dao);

			listMap = service.getGroupAuthList(Integer.parseInt("" + req.getParameter("groupId")));

			tmpMap.put("result", listMap);
			LOG.debug(tmpMap.toString());
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			tmpMap.put("result", "N");
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (listMap != null) {
				listMap.clear();
				listMap = null;
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

			userId = 0;
			userIp = null;
		}
		return;

	}

	public void getUpLevelAuth(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("getUpLevelAuth");
		long userId = 0;
		String userIp = null;
		Map<String, Object> tmpMap = null;
		List<Map<String, Object>> listMap = null;
		AuthServiceImpl service = null;
		AuthDaoImpl dao = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			userId = accIdToLong("" + req.getSession().getAttribute("accId"));
			userIp = getIpAddr(req);
			listMap = new ArrayList<Map<String, Object>>();
			service = new AuthServiceImpl(userId, userIp);
			dao = new AuthDaoImpl();
			service.setDao(dao);

			listMap = service.getUpLevelAuth();

			tmpMap.put("result", listMap);
			LOG.debug(tmpMap.toString());
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			tmpMap.put("result", "N");
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);

			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
			if (listMap != null) {
				listMap.clear();
				listMap = null;
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

			userId = 0;
			userIp = null;
		}
		return;
	}

	// private Map<String, Object> printRequest(HttpServletRequest httpRequest) {
	// Map<String, Object> map = new ConcurrentHashMap<String, Object>();
	// Enumeration params = httpRequest.getParameterNames();
	// while (params.hasMoreElements()) {
	// String paramName = (String) params.nextElement();
	// LOG.debug(paramName + " = " + httpRequest.getParameter(paramName));
	// if (paramName.equals("pwd")) {
	// String encrypted =
	// AESUtil.encrypt_CBC(httpRequest.getParameter(paramName).trim(), KEY);
	// map.put(paramName, encrypted);
	// } else {
	// map.put(paramName, httpRequest.getParameter(paramName).trim());
	// }
	// }
	// map.put("ip", httpRequest.getRemoteAddr());
	// return map;
	// }
}
