package tw.com.ctt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.impl.LotteryOperatorRecordDaoImpl;
import tw.com.ctt.service.impl.LotteryOperatorRecordServiceImpl;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "LotteryOperatorRecord", urlPatterns = { "/LotteryOperatorRecord" })
public class LotteryOperatorRecordAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 354259646260218696L;
	private static final Logger LOG = LogManager.getLogger(LotteryOperatorRecordAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = {};
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = { "/LotteryOperatorRecord!getLotteryLocalList", "/LotteryOperatorRecord!getTodayOperatorRecord",
			"/LotteryOperatorRecord!searchOperatorRecordByDate", "/LotteryOperatorRecord!searchBCRecord", "/LotteryOperatorRecord!searchMemRecord" };
	private String[] authURL = { "/LotteryOperatorRecord!mainLotteryOperatorRecord" };

	public LotteryOperatorRecordAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("LotteryOperatorRecordAction start");
	}

	public void mainLotteryOperatorRecord(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("LotteryOperatorRecordAction.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_AG_checkLotteryOperatorRecord(){\n");
			sb.append("    if(typeof LotteryOperatorRecord === 'undefined' || typeof LotteryOperatorRecord != 'function' ){\n");
			sb.append("        checkJS('LotteryOperatorRecord', 'AccountManage/LotteryOperatorRecord.js');\n");
			sb.append("    } else {\n");
			sb.append("        LotteryOperatorRecord();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_AG_checkLotteryOperatorRecord();\n");
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
			LOG.error("LotteryOperatorRecordAction.main.Exception, " + e.getMessage());
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

	public void getLotteryLocalList(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("getLotteryLocalList");
		Map<String, Object> tmpMap = null;
		LotteryOperatorRecordServiceImpl service = null;
		LotteryOperatorRecordDaoImpl dao = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotteryOperatorRecordServiceImpl();
			dao = new LotteryOperatorRecordDaoImpl();
			service.setDao(dao);

			tmpMap.put("LotteryLocalList", service.getLotteryLocalList());
			tmpMap.put("tokenId", "success");
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

	public void getTodayOperatorRecord(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("getTodayOperatorRecord");
		Map<String, Object> tmpMap = null;
		LotteryOperatorRecordServiceImpl service = null;
		LotteryOperatorRecordDaoImpl dao = null;
		long accId = 0;
		int levelType = 999;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotteryOperatorRecordServiceImpl();
			dao = new LotteryOperatorRecordDaoImpl();
			service.setDao(dao);
			accId = accIdToLong("" + req.getSession().getAttribute("accId"));
			levelType = req.getParameter("levelType") == null ? 999 : Integer.parseInt(req.getParameter("levelType"));

			if (accId > 0 && levelType >= 0 && levelType <= 3) {
				tmpMap.put("TodayOperatorRecord", service.getTodayOperatorRecord(accId, levelType));
			}
			tmpMap.put("tokenId", "success");
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

	public void searchOperatorRecordByDate(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("getTodayOperatorRecord");
		Map<String, Object> tmpMap = null;
		LotteryOperatorRecordServiceImpl service = null;
		LotteryOperatorRecordDaoImpl dao = null;
		long accId = 0;
		String accName = null;
		int localId = 0;
		String dateTime = null;
		int type = 0;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotteryOperatorRecordServiceImpl();
			dao = new LotteryOperatorRecordDaoImpl();
			service.setDao(dao);

			accId = accIdToLong("" + req.getSession().getAttribute("accId"));

			accName = req.getParameter("accName") == null ? "" : req.getParameter("accName").toString();
			localId = req.getParameter("localId") == null ? 0 : Integer.parseInt(req.getParameter("localId").toString());
			dateTime = req.getParameter("dateTime") == null ? "" : req.getParameter("dateTime").toString();
			type = req.getParameter("type") == null ? 0 : Integer.parseInt(req.getParameter("type").toString());

			if (accId > 0 && type > 0 && !"".equals(dateTime) && !dateTime.isEmpty()) {
				tmpMap.put("searchOperatorRecordByDate", service.searchOperatorRecordByDate(accId, accName, localId, dateTime, type));
			}
			tmpMap.put("dateType", type);
			tmpMap.put("tokenId", "success");
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

	public void searchBCRecord(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("searchBCRecord");
		Map<String, Object> tmpMap = null;
		LotteryOperatorRecordServiceImpl service = null;
		LotteryOperatorRecordDaoImpl dao = null;
		long accId = 0;
		int localId = 0;
		String dateTime = null;
		int type = 0;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotteryOperatorRecordServiceImpl();
			dao = new LotteryOperatorRecordDaoImpl();
			service.setDao(dao);

			accId = req.getParameter("accId") == null ? 0 : Integer.parseInt(req.getParameter("accId").toString());
			localId = req.getParameter("localId") == null ? 0 : Integer.parseInt(req.getParameter("localId").toString());
			dateTime = req.getParameter("dateTime") == null ? "" : req.getParameter("dateTime").toString();
			type = req.getParameter("type") == null ? 0 : Integer.parseInt(req.getParameter("type").toString());

			if (accId > 0 && type > 0 && !"".equals(dateTime) && !dateTime.isEmpty()) {
				tmpMap.put("searchBCRecord", service.searchBCRecord(accId, localId, dateTime, type));
			}
			tmpMap.put("dateType", type);
			tmpMap.put("tokenId", "success");
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

	public void searchMemRecord(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("searchMemRecord");
		Map<String, Object> tmpMap = null;
		LotteryOperatorRecordServiceImpl service = null;
		LotteryOperatorRecordDaoImpl dao = null;
		long accId = 0;
		int localId = 0;
		String dateTime = null;
		int type = 0;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotteryOperatorRecordServiceImpl();
			dao = new LotteryOperatorRecordDaoImpl();
			service.setDao(dao);

			accId = req.getParameter("accId") == null ? 0 : Integer.parseInt(req.getParameter("accId").toString());
			localId = req.getParameter("localId") == null ? 0 : Integer.parseInt(req.getParameter("localId").toString());
			dateTime = req.getParameter("dateTime") == null ? "" : req.getParameter("dateTime").toString();
			type = req.getParameter("type") == null ? 0 : Integer.parseInt(req.getParameter("type").toString());

			if (accId > 0 && type > 0 && !"".equals(dateTime) && !dateTime.isEmpty()) {
				tmpMap.put("searchMemRecord", service.searchMemRecord(accId, localId, dateTime, type));
			}
			tmpMap.put("dateType", type);
			tmpMap.put("tokenId", "success");
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
