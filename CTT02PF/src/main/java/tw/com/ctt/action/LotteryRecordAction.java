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

import tw.com.ctt.dao.impl.LotteryRecordDaoImpl;
import tw.com.ctt.service.impl.LotteryRecordServiceImpl;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "LotteryRecord", urlPatterns = { "/LotteryRecord" })
public class LotteryRecordAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3493176318105942066L;
	private static final Logger LOG = LogManager.getLogger(LotteryRecordAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = {};
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = { "/LotteryRecord!getLotteryLocalList", "/LotteryRecord!getLotteryRecord" };
	private String[] authURL = { "/LotteryRecord!mainLotteryRecord" };

	public LotteryRecordAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("LotteryRecordAction start");
	}

	public void mainLotteryRecord(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("LotteryRecordAction.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_AG_checkLotteryRecord(){\n");
			sb.append("    if(typeof LotteryRecord==='undefined' || typeof LotteryRecord != 'function' ){\n");
			sb.append("        checkJS('LotteryRecord', 'AccountManage/LotteryRecord.js');\n");
			sb.append("    } else {\n");
			sb.append("        LotteryRecord();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_AG_checkLotteryRecord();\n");
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
			LOG.error("LotteryRecordAction.main.Exception, " + e.getMessage());
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
		LotteryRecordServiceImpl service = null;
		LotteryRecordDaoImpl dao = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotteryRecordServiceImpl();
			dao = new LotteryRecordDaoImpl();
			service.setDao(dao);
			if (true) {
				tmpMap.put("LotteryLocalList", service.getLotteryLocalList());
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

	public void getLotteryRecord(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("getLotteryRecord");
		Map<String, Object> tmpMap = null;
		long accId;
		LotteryRecordServiceImpl service = null;
		LotteryRecordDaoImpl dao = null;
		String[] recordKey = { "memberAccName", "recordType", "checkBox", "localId", "startTime", "endTime" };
		Map<String, Object> map = null;

		int firstCount = 0;
		int count = 0;
		int page = 0;
		int nextPage = 0;
		int totalCount = 0;
		int totalPage = 0;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			accId = accIdToLong(req.getSession().getAttribute("accId").toString());
			service = new LotteryRecordServiceImpl();
			dao = new LotteryRecordDaoImpl();
			service.setDao(dao);

			page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page").toString()) : 1;
			nextPage = req.getParameter("nextPage") != null ? Integer.parseInt(req.getParameter("nextPage").toString()) : 1;
			count = req.getParameter("count") != null ? Integer.parseInt(req.getParameter("count").toString()) : 25;

			map = new ConcurrentHashMap<String, Object>();

			for (int i = 0; i < recordKey.length; i++) {
				map.put(recordKey[i], req.getParameter(recordKey[i]) != null ? req.getParameter(recordKey[i]).toString() : "");
			}
			if (!"".equals(map.get("memberAccName").toString())) {
				totalCount = service.getLotteryRecordTotleCount(accId, map);
				LOG.debug(totalCount);
				if (totalCount > 0) {
					totalPage = (int) Math.ceil((totalCount * 1.0) / (count * 1.0));
				} else {
					totalPage = 1;
				}
				if (nextPage == 0) {
					if (totalPage >= page && page > 0) {
						nextPage = page;
						firstCount = count * (page - 1);
					} else if (totalPage < page) {
						nextPage = totalPage;
						firstCount = totalCount - (count * (page - 1));
					} else if (page <= 0) {
						nextPage = 1;
						firstCount = 0;
					}
				} else {
					if (totalPage >= nextPage && nextPage > 0) {
						nextPage = nextPage;
						firstCount = count * (nextPage - 1);
					} else if (totalPage < nextPage) {
						nextPage = totalPage;
						firstCount = totalCount - (count * (nextPage - 1));
					} else if (nextPage <= 0) {
						nextPage = 1;
						firstCount = 0;
					}
				}
				LOG.debug(totalPage);
				tmpMap.put("lotteryRecordLastPage", totalPage);
				tmpMap.put("lotteryRecordPage", nextPage);
				if (map.size() > 0) {
					tmpMap.put("recordInfo", service.getLotteryRecord(accId, map, firstCount, count));
				}
			}
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			tmpMap.put("searchTime", sdFormat.format(new Date()));
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
