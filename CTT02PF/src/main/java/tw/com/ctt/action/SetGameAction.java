package tw.com.ctt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.impl.SetGameDaoImpl;
import tw.com.ctt.service.impl.SetGameServiceImpl;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "SetGame", urlPatterns = { "/SetGame" })
public class SetGameAction extends BaseAction {

	private static final long serialVersionUID = 7638933731501278687L;
	private static final Logger LOG = LogManager.getLogger(SetGameAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = {};
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = { "/SetGame!getGameParamData", "/SetGame!updateGameParamData" };
	private String[] authURL = { "/SetGame!mainGameServer", "/SetGame!mainGameParam" };

	public SetGameAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("SetGameAction start");
	}

	public void mainGameServer(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("mainGameServer.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial blackWhite.js
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_AG_checkSetGame(){\n");
			sb.append("    if(typeof SetGameServer_GameServer==='undefined' || typeof SetGameServer_GameServer != 'function' ){\n");
			sb.append("        checkJS('SetGameServer_GameServer', 'AccountManage/SetGameServer.js');\n");
			sb.append("    } else {\n");
			sb.append("        SetGameServer_GameServer();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_AG_checkSetGame();\n");
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
			LOG.error("mainGameServer.main.Exception, " + e.getMessage());
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

	public void mainGameParam(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("mainGameParam.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial blackWhite.js
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_AG_checkSetParam(){\n");
			sb.append("    if(typeof SetGameServer_GameServer==='undefined' || typeof SetGameServer_GameServer != 'function' ){\n");
			sb.append("        checkJS('SetGameServer_GameParam', 'AccountManage/SetGameServer.js');\n");
			sb.append("    } else {\n");
			sb.append("        SetGameServer_GameParam();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_AG_checkSetParam();\n");
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
			LOG.error("mainGameParam.main.Exception, " + e.getMessage());
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

	public void getGameParamData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		SetGameServiceImpl service = null;
		SetGameDaoImpl dao = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new SetGameServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
			dao = new SetGameDaoImpl();
			service.setDao(dao);

			tmpMap.put("gameParamData", service.getGameParamData());

			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			tmpMap.put("searchTime", sdFormat.format(new Date()));
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
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
		return;
	}

	public void updateGameParamData(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		SetGameServiceImpl service = null;
		SetGameDaoImpl dao = null;
		String updateObj = null;
		boolean todo = false;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new SetGameServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
			dao = new SetGameDaoImpl();
			service.setDao(dao);
			updateObj = req.getParameter("updateObj") == null ? "" : req.getParameter("updateObj");

			todo = service.updateGameParamData(updateObj);

			if (todo) {
				tmpMap.put("isSuccess", todo);
			} else {
				tmpMap.put("isSuccess", false);
			}
		} catch (Exception e) {
			tmpMap.put("isSuccess", false);
			LOG.info("Exception, " + e.getMessage());
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
		return;
	}
}
