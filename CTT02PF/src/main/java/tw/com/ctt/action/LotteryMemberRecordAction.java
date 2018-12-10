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

import tw.com.ctt.dao.impl.LotteryMemberRecordDaoImpl;
import tw.com.ctt.service.impl.LotteryMemberRecordServiceImpl;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "LotteryMemberRecord", urlPatterns = { "/LotteryMemberRecord" })
public class LotteryMemberRecordAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 354259646260218696L;
	private static final Logger LOG = LogManager.getLogger(LotteryMemberRecordAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = {};
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = { "/LotteryMemberRecord!getLotteryLocalList", "/LotteryMemberRecord!searchMemDayRecord",
			"/LotteryMemberRecord!searchMemberRecordByDate" };
	private String[] authURL = { "/LotteryMemberRecord!mainLotteryMemberRecord" };

	public LotteryMemberRecordAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("LotteryMemberRecordAction start");
	}

	public void mainLotteryMemberRecord(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("LotteryMemberRecordAction.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_AG_checkLotteryMemberRecord(){\n");
			sb.append("    if(typeof LotteryMemberRecord === 'undefined' || typeof LotteryMemberRecord != 'function' ){\n");
			sb.append("        checkJS('LotteryMemberRecord', 'AccountManage/LotteryMemberRecord.js');\n");
			sb.append("    } else {\n");
			sb.append("        LotteryMemberRecord();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_AG_checkLotteryMemberRecord();\n");
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
			LOG.error("LotteryMemberRecordAction.main.Exception, " + e.getMessage());
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
		LotteryMemberRecordServiceImpl service = null;
		LotteryMemberRecordDaoImpl dao = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotteryMemberRecordServiceImpl();
			dao = new LotteryMemberRecordDaoImpl();
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

	public void searchMemberRecordByDate(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("searchMemberRecordByDate");
		Map<String, Object> tmpMap = null;
		LotteryMemberRecordServiceImpl service = null;
		LotteryMemberRecordDaoImpl dao = null;
		long accId = 0;
		int localId = 0;
		String startTime = null;
		String endTime = null;
		int type = 0;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotteryMemberRecordServiceImpl();
			dao = new LotteryMemberRecordDaoImpl();
			service.setDao(dao);

			accId = accIdToLong("" + req.getSession().getAttribute("accId"));

			localId = req.getParameter("localId") == null ? 0 : Integer.parseInt(req.getParameter("localId").toString());
			startTime = req.getParameter("startTime") == null ? "" : req.getParameter("startTime").toString();
			endTime = req.getParameter("endTime") == null ? "" : req.getParameter("endTime").toString();
			type = req.getParameter("type") == null ? 0 : Integer.parseInt(req.getParameter("type").toString());

			if (!"".equals(startTime) && !startTime.isEmpty() && !"".equals(endTime) && !endTime.isEmpty()) {
				tmpMap.put("searchMemberRecordByDate", service.searchMemberRecordByDate(accId, localId, startTime, endTime, type));
			}
			tmpMap.put("dateType", type);
			tmpMap.put("localId", localId);
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

	public void searchMemDayRecord(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("searchMemDayRecord");
		Map<String, Object> tmpMap = null;
		LotteryMemberRecordServiceImpl service = null;
		LotteryMemberRecordDaoImpl dao = null;
		long userId = 0;
		String accName = null;
		int localId = 0;
		String startTime = null;
		String endTime = null;
		int type = 0;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotteryMemberRecordServiceImpl();
			dao = new LotteryMemberRecordDaoImpl();
			service.setDao(dao);

			userId = req.getSession().getAttribute("accId") == null ? 0 : accIdToLong("" + req.getSession().getAttribute("accId"));
			accName = req.getParameter("accName") == null ? "" : req.getParameter("accName").toString();
			localId = req.getParameter("localId") == null ? 0 : Integer.parseInt(req.getParameter("localId").toString());
			startTime = req.getParameter("startTime") == null ? "" : req.getParameter("startTime").toString();
			endTime = req.getParameter("endTime") == null ? "" : req.getParameter("endTime").toString();
			type = req.getParameter("type") == null ? 0 : Integer.parseInt(req.getParameter("type").toString());

			if (userId > 0 && (localId > 0 || (!"".equals(accName) && !accName.isEmpty())) && !"".equals(startTime) && !startTime.isEmpty()
					&& !"".equals(endTime) && !endTime.isEmpty()) {
				tmpMap.put("searchMemDayRecord", service.searchMemDayRecord(userId, accName, localId, startTime, endTime, type));
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

}
