package tw.com.ctt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.dao.impl.SubOrderInfoDaoImpl;
import tw.com.ctt.service.impl.SubOrderInfoServiceImpl;
import tw.com.ctt.util.ShowLog;

/**
 * Using this action to query infos which is not changed often
 * 
 * @author Quanto
 *
 */
@WebServlet(name = "BetInfo", urlPatterns = { "/BetInfo" })
public class QueryBetInfoAction extends BaseAction {

	private static final long serialVersionUID = -5390951620012326545L;
	private static final Logger LOG = LogManager.getLogger(QueryBetInfoAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = { "/BetInfo", "/BetInfo!querySubOrderInfo", "/BetInfo!queryAllLottery", "/BetInfo!queryHandicap",
			"/BetInfo!queryBalance", "/BetInfo!queryAllInfo" };
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = {};
	private String[] authURL = {};

	public QueryBetInfoAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		getInfosWhenInit();
		LOG.info("QueryBetInfoAction start");
	}

	public boolean checkLogin() {
		return true;
	}

	/**
	 * getInfosWhenInit
	 */
	public void getInfosWhenInit() {
		SubOrderInfoServiceImpl service = null;
		SubOrderInfoDaoImpl dao = null;
		try {
			service = new SubOrderInfoServiceImpl();
			dao = new SubOrderInfoDaoImpl();
			service.setDao(dao);
			CommandConstant.SUB_ORDER_MAPPING_INFO = service.getSubOrderInfo();
			CommandConstant.SUB_ORDER_NO_OF_BET_INFO = service.getSubOrderInfoNoOfBet();
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
	 * get the test page.
	 */
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

	/**
	 * when call this function, it will response all infos (so the lottery page will
	 * call this at first time)
	 * 
	 * @throws IOException
	 */
	public void queryAllInfo(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("queryBalence");
		Map<String, Object> tmpMap = null;
		String tokenId = null;
		long accId = 0;
		SubOrderInfoServiceImpl service = null;
		SubOrderInfoDaoImpl dao = null;
		try {
			tmpMap = new HashMap<String, Object>();
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				tokenId = req.getParameter("tokenId").toString();
				accId = accIdToLong(req.getParameter("accId").toString());
			} else if (req.getAttribute("tokenId") != null && req.getAttribute("accId") != null) {
				tokenId = req.getAttribute("tokenId").toString();
				accId = accIdToLong(req.getAttribute("accId").toString());
			} else {
				tmpMap.put("tokenId", "fail");
			}
			if (tokenId != null && accId != 0) {
				Map<String, String> accData = checkLoginMember(accId, tokenId);

				service = new SubOrderInfoServiceImpl(accId, getIpAddr(req));
				dao = new SubOrderInfoDaoImpl();
				service.setDao(dao);
				tmpMap.putAll(accData);
				tmpMap.put("SubOrderInfo", CommandConstant.SUB_ORDER_MAPPING_INFO);
				tmpMap.put("SubOrderInfoNoOfBet", CommandConstant.SUB_ORDER_NO_OF_BET_INFO);
				tmpMap.put("AllLottery", CommandConstant.ALL_LOTTERY_INFO);
				tmpMap.put("AllLotteryTitle", CommandConstant.ALL_LOTTERY_TITLE_INFO);
				tmpMap.put("AllLottery_LUT", CommandConstant.DB_LAST_UPDATE_TIME_OF_ALL_LOTTERY_INFO);
				tmpMap.put("CurrentBaseline", CommandConstant.CURRENT_BET_RATIO != null ? CommandConstant.CURRENT_BET_RATIO : "");
				tmpMap.put("CurrentBaseline_LUT", CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE);
				tmpMap.put("CurrentBaselineLF", CommandConstant.CURRENT_BET_RATIO_LF != null ? CommandConstant.CURRENT_BET_RATIO_LF : "");
				tmpMap.put("CurrentBaselineLF_LUT", CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE_LF);
				tmpMap.put("KjTimeStatus", CommandConstant.KJ_TIME_AND_STATUS);
				tmpMap.put("KjTimeStatus_LUT", CommandConstant.DB_LAST_UPDATE_TIME_OF_KJ);
				tmpMap.put("Handicap", service.getHandicap(accId));
				tmpMap.put("tokenId", "success");
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
	 * queryBalance
	 * 
	 * @throws IOException
	 */
	public void queryBalance(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("queryBalence");
		Map<String, Object> tmpMap = null;
		String tokenId = null;
		long accId = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				tokenId = req.getParameter("tokenId").toString();
				accId = accIdToLong(req.getParameter("accId").toString());
			} else if (req.getAttribute("tokenId") != null && req.getAttribute("accId") != null) {
				tokenId = req.getAttribute("tokenId").toString();
				accId = accIdToLong(req.getAttribute("accId").toString());
			} else {
				tmpMap.put("tokenId", "fail");
			}
			if (tokenId != null && accId != 0) {
				Map<String, String> accData = checkLoginMember(accId, tokenId);
				if ("0".equals(accData.get("tokenIdCheck"))) {
					tmpMap.put("tokenId", "fail");
				} else {
					tmpMap.putAll(accData);
					tmpMap.put("tokenId", "success");
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
	 * querySubOrderInfo
	 * 
	 * @throws IOException
	 */
	public void querySubOrderInfo(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("querySubOrderInfo");
		Map<String, Object> tmpMap = null;
		String tokenId = null;
		long accId = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				tokenId = req.getParameter("tokenId").toString();
				accId = accIdToLong(req.getParameter("accId").toString());
			} else if (req.getAttribute("tokenId") != null && req.getAttribute("accId") != null) {
				tokenId = req.getAttribute("tokenId").toString();
				accId = accIdToLong(req.getAttribute("accId").toString());
			} else {
				tmpMap.put("tokenId", "fail");
			}
			if (tokenId != null && accId != 0) {
				Map<String, String> accData = checkLoginMember(accId, tokenId);
				if ("0".equals(accData.get("tokenIdCheck"))) {
					tmpMap.put("tokenId", "fail");
				} else {
					tmpMap.putAll(accData);
					tmpMap.put("SubOrderInfo", CommandConstant.SUB_ORDER_MAPPING_INFO);
					tmpMap.put("SubOrderInfoNoOfBet", CommandConstant.SUB_ORDER_NO_OF_BET_INFO);
					tmpMap.put("tokenId", "success");
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
	 * the query cmd include "LUT"(last update time) so if the LUT is different from
	 * "DB_LAST_UPDATE_TIME_OF_ALL_LOTTERY_INFO" , response the newest data.
	 * 
	 * @throws IOException
	 */
	public void queryAllLottery(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("queryAllLottery");
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
					if (lastUpdateTimeFromWeb != CommandConstant.DB_LAST_UPDATE_TIME_OF_ALL_LOTTERY_INFO) {
						tmpMap.putAll(accData);
						tmpMap.put("AllLottery", CommandConstant.ALL_LOTTERY_INFO);
					}
					tmpMap.put("tokenId", "success");
					tmpMap.put("LUT", CommandConstant.DB_LAST_UPDATE_TIME_OF_ALL_LOTTERY_INFO);
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
	 * queryHandicap of accId
	 * 
	 * @throws IOException
	 */
	public void queryHandicap(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("queryHandicap");
		Map<String, Object> tmpMap = null;
		String tokenId = null;
		long accId = 0;
		SubOrderInfoServiceImpl service = null;
		SubOrderInfoDaoImpl dao = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				tokenId = req.getParameter("tokenId").toString();
				accId = accIdToLong(req.getParameter("accId").toString());
			} else if (req.getAttribute("tokenId") != null && req.getAttribute("accId") != null) {
				tokenId = req.getAttribute("tokenId").toString();
				accId = accIdToLong(req.getAttribute("accId").toString());
			} else {
				tmpMap.put("tokenId", "fail");
			}
			if (tokenId != null && accId != 0) {
				Map<String, String> accData = checkLoginMember(accId, tokenId);
				if ("0".equals(accData.get("tokenIdCheck"))) {
					tmpMap.put("tokenId", "fail");
				} else {
					tmpMap.putAll(accData);
					service = new SubOrderInfoServiceImpl(accId, getIpAddr(req));
					dao = new SubOrderInfoDaoImpl();
					service.setDao(dao);
					if (true) {
						tmpMap.put("Handicap", service.getHandicap(accId));
					}
					tmpMap.put("tokenId", "success");
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
