package tw.com.ctt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.dao.impl.BaselineInfoDaoImpl;
import tw.com.ctt.service.impl.BaselineInfoServiceImpl;
import tw.com.ctt.util.ShowLog;

/**
 * Using this action to queryBaseline
 * 
 * @author Quanto
 *
 */
@WebServlet(name = "BaselineInfo", urlPatterns = { "/BaselineInfo" })
public class QueryBetRatioAction extends BaseAction {

	private static final long serialVersionUID = -5390951620012326545L;
	private static final Logger LOG = LogManager.getLogger(QueryBetRatioAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = { "/BaselineInfo!queryBaseline" };
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = {};
	private String[] authURL = {};

	public QueryBetRatioAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		getBaselineWhenInit();
		LOG.info("QueryBetRatioAction start");
	}

	public boolean checkLogin() {
		return true;
	}

	/**
	 * getBaselineWhenInit
	 */
	public void getBaselineWhenInit() {
		LOG.debug("getBaselineWhenInit");
		BaselineInfoServiceImpl service = null;
		BaselineInfoDaoImpl dao = null;
		try {
			service = new BaselineInfoServiceImpl();
			dao = new BaselineInfoDaoImpl();
			service.setDao(dao);
			CommandConstant.CURRENT_BET_RATIO = service.getBaseline();
			CommandConstant.CHECK_UPDATE_BASELINE_TIME = new Date().getTime();
			CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE = service.getBaselineLastUpdateTime();
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
		}
	}

	/**
	 * queryBaseline will check LUT, if different from
	 * DB_LAST_UPDATE_TIME_OF_BASELINE , response the newest data.
	 * 
	 * @throws IOException
	 */
	public void queryBaseline(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("queryBaseline");
		Map<String, Object> tmpMap = null;
		String tokenId = null;
		long accId = 0;
		long lastUpdateTimeFromWeb = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null && req.getParameter("LUT") != null) {
				tokenId = req.getParameter("tokenId").toString();
				accId = accIdToLong(req.getParameter("accId").toString());
				lastUpdateTimeFromWeb = accIdToLong(req.getParameter("LUT").toString());
			} else if (req.getAttribute("tokenId") != null && req.getAttribute("accId") != null && req.getAttribute("LUT") != null) {
				tokenId = req.getAttribute("tokenId").toString();
				accId = accIdToLong(req.getAttribute("accId").toString());
				lastUpdateTimeFromWeb = accIdToLong(req.getAttribute("LUT").toString());
			} else {
				tmpMap.put("tokenId", "fail");
			}
			if (tokenId != null && accId != 0) {
				Map<String, String> accData = checkLoginMember(accId, tokenId);
				if ("0".equals(accData.get("tokenIdCheck"))) {
					tmpMap.put("tokenId", "fail");
				} else {
					if (lastUpdateTimeFromWeb != CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE) {
						tmpMap.putAll(accData);
						tmpMap.put("CurrentBaseline", CommandConstant.CURRENT_BET_RATIO != null ? CommandConstant.CURRENT_BET_RATIO : "");
					}
					tmpMap.put("tokenId", "success");
					tmpMap.put("LUT", CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE);
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
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
		}
	}

	/**
	 * queryBaselineLF (low frequency)
	 * 
	 * @throws IOException
	 */
	public void queryBaselineLF(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("queryBaseline");
		Map<String, Object> tmpMap = null;
		String tokenId = null;
		long accId = 0;
		long lastUpdateTimeFromWeb = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null && req.getParameter("LUT") != null) {
				tokenId = req.getParameter("tokenId").toString();
				accId = accIdToLong(req.getParameter("accId").toString());
				lastUpdateTimeFromWeb = accIdToLong(req.getParameter("LUT").toString());
			} else if (req.getAttribute("tokenId") != null && req.getAttribute("accId") != null && req.getAttribute("LUT") != null) {
				tokenId = req.getAttribute("tokenId").toString();
				accId = accIdToLong(req.getAttribute("accId").toString());
				lastUpdateTimeFromWeb = accIdToLong(req.getAttribute("LUT").toString());
			} else {
				tmpMap.put("tokenId", "fail");
			}
			if (tokenId != null && accId != 0) {
				Map<String, String> accData = checkLoginMember(accId, tokenId);
				if ("0".equals(accData.get("tokenIdCheck"))) {
					tmpMap.put("tokenId", "fail");
				} else {
					if (lastUpdateTimeFromWeb != CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE_LF) {
						tmpMap.putAll(accData);
						tmpMap.put("CurrentBaselineLF", CommandConstant.CURRENT_BET_RATIO_LF != null ? CommandConstant.CURRENT_BET_RATIO_LF : "");
					}
					tmpMap.put("tokenId", "success");
					tmpMap.put("LUT", CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE_LF);
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
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
		}
	}
}
