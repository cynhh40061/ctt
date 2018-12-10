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
import tw.com.ctt.dao.impl.KjInfoDaoImpl;
import tw.com.ctt.dao.impl.OrderInfoOfBettingDaoImpl;
import tw.com.ctt.service.impl.BaselineInfoServiceImpl;
import tw.com.ctt.service.impl.KjInfoServiceImpl;
import tw.com.ctt.service.impl.OrderInfoOfBettingServiceImpl;

/**
 * <h1>UpdateInfoAction</h1> This action used to update infos for betting order.
 * if some other project or server change the value of DB. it need to call the
 * specific action to make sure other servers has update to the newest data.
 *
 * @author Quanto Lin
 * @version 1.0
 * @since 2018-08-30
 */
@WebServlet(name = "UpdateInfo", urlPatterns = { "/UpdateInfo" })
public class UpdateInfoAction extends BaseAction {
	private static final long serialVersionUID = -8126472505737735647L;
	private static final Logger LOG = LogManager.getLogger(UpdateInfoAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = { "/UpdateInfo!updateAllLotteryInfo", "/UpdateInfo!updateBasicInfoOfSubOrder", "/UpdateInfo!updateKjTimeInfo",
			"/UpdateInfo!updateBaseline" };
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = {};
	private String[] authURL = {};

	public UpdateInfoAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("UpdateInfoAction start");
	}

	public void updateAllLotteryInfo(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("updateAllLotteryInfo");
		Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
		tmpMap.put("result", "success");
		OrderInfoOfBettingServiceImpl service = null;
		OrderInfoOfBettingDaoImpl dao = null;
		try {
			if (new Date().getTime() - CommandConstant.CHECK_UPDATE_ALL_LOTTERY_TIME > 1000) {
				CommandConstant.CHECK_UPDATE_ALL_LOTTERY_TIME = new Date().getTime();
				service = new OrderInfoOfBettingServiceImpl();
				dao = new OrderInfoOfBettingDaoImpl();
				service.setDao(dao);
				long t1 = service.getAllLotteryLastUpdateTime();
				if (CommandConstant.DB_LAST_UPDATE_TIME_OF_ALL_LOTTERY_INFO < t1) {
					CommandConstant.DB_LAST_UPDATE_TIME_OF_ALL_LOTTERY_INFO = t1;
					CommandConstant.ALL_LOTTERY_INFO = service.getAllLottery();
				}
			}
		} catch (Exception e) {
			tmpMap.put("result", "fail");
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);
			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			out.flush();
			out.close();
			out = null;
		}

	}

	public void updateBasicInfoOfSubOrder(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.error("updateBasicInfoOfSubOrder");
		OrderInfoOfBettingServiceImpl service = null;
		OrderInfoOfBettingDaoImpl dao = null;
		Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
		try {
			service = new OrderInfoOfBettingServiceImpl();
			dao = new OrderInfoOfBettingDaoImpl();
			service.setDao(dao);
			CommandConstant.NO_OF_BET_INFO_FOR_SUB_ORDER = service.getSubOrderInfoNoOfBet();
			CommandConstant.BASIC_INFO_OF_SUB_ORDER = service.getSubOrderInfo();
			CommandConstant.ALL_HANDICAP_INFO = service.getAllHandicap();
			CommandConstant.CHECK_UPDATE_SUB_ORDER_TIME = new Date().getTime();
			tmpMap.put("result", "success");
		} catch (Exception e) {
			tmpMap.put("result", "fail");
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);
			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			out.flush();
			out.close();
			out = null;
		}
	}

	public void updateKjTimeInfo(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("updateKjTimeInfo");
		Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
		tmpMap.put("result", "success");
		KjInfoServiceImpl service = null;
		KjInfoDaoImpl dao = null;
		try {
			if (new Date().getTime() - CommandConstant.CHECK_UPDATE_KJ_TIME > 1000) {
				CommandConstant.CHECK_UPDATE_KJ_TIME = new Date().getTime();
				service = new KjInfoServiceImpl();
				dao = new KjInfoDaoImpl();
				service.setDao(dao);
				long t1 = service.getKjLastUpdateTime();
				if (CommandConstant.DB_LAST_UPDATE_TIME_OF_KJ < t1) {
					CommandConstant.DB_LAST_UPDATE_TIME_OF_KJ = t1;
					CommandConstant.KJ_TIME_INFO = service.getAllKjTimeStatus();
				}
			}
		} catch (Exception e) {
			tmpMap.put("result", "fail");
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);
			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			out.flush();
			out.close();
			out = null;
		}
	}

	public void updateBaseline(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.error("updateBaseline");
		Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
		tmpMap.put("result", "success");
		BaselineInfoServiceImpl service = null;
		BaselineInfoDaoImpl dao = null;
		try {
			if (new Date().getTime() - CommandConstant.CHECK_UPDATE_BASELINE_TIME > 1000) {
				CommandConstant.CHECK_UPDATE_BASELINE_TIME = new Date().getTime();
				service = new BaselineInfoServiceImpl();
				dao = new BaselineInfoDaoImpl();
				service.setDao(dao);
				long t1 = service.getBaselineLastUpdateTime();
				if (CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE < t1) {
					CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE = t1;
					CommandConstant.CURRENT_BET_RATIO = service.getBaseline();
				}
			}
		} catch (Exception e) {
			tmpMap.put("result", "fail");
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);
			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);
			if (dao != null) {
				dao.close();
				dao = null;
			}
			if (service != null) {
				service.close();
				service = null;
			}
			out.flush();
			out.close();
			out = null;
		}
	}
}
