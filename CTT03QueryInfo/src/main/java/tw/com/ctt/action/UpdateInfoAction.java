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
import tw.com.ctt.dao.impl.SubOrderInfoDaoImpl;
import tw.com.ctt.service.impl.BaselineInfoServiceImpl;
import tw.com.ctt.service.impl.KjInfoServiceImpl;
import tw.com.ctt.service.impl.SubOrderInfoServiceImpl;
import tw.com.ctt.util.ShowLog;

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
	private static final long serialVersionUID = 2498128181441837157L;
	private static final Logger LOG = LogManager.getLogger(UpdateInfoAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = { "/UpdateInfo!updateKjInfo", "/UpdateInfo!updateInfos", "/UpdateInfo!updateAllLotteryInfos",
			"/UpdateInfo!updateBaseline", "/UpdateInfo!checkIPs" };
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

	public boolean checkLogin() {
		return true;
	}

	/**
	 * forced to update kj info
	 * 
	 * @throws IOException
	 */
	public void updateKjInfo(HttpServletRequest req, HttpServletResponse res) throws IOException {
		KjInfoServiceImpl service = null;
		KjInfoDaoImpl dao = null;
		Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
		try {
			service = new KjInfoServiceImpl();
			dao = new KjInfoDaoImpl();
			service.setDao(dao);
			CommandConstant.CHECK_UPDATE_KJ_TIME = new Date().getTime();
			CommandConstant.DB_LAST_UPDATE_TIME_OF_KJ = service.getKjLastUpdateTime();
			CommandConstant.KJ_TIME_AND_STATUS = service.getAllKjTimeStatus();
			tmpMap.put("result", "success");
		} catch (Exception e) {
			tmpMap.put("result", "fail");
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
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

	/**
	 * updateInfos will forced to update subOrder info from DB.
	 * 
	 * @throws IOException
	 */
	public void updateInfos(HttpServletRequest req, HttpServletResponse res) throws IOException {
		SubOrderInfoServiceImpl service = null;
		SubOrderInfoDaoImpl dao = null;
		Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
		try {
			service = new SubOrderInfoServiceImpl();
			dao = new SubOrderInfoDaoImpl();
			service.setDao(dao);
			CommandConstant.SUB_ORDER_MAPPING_INFO = service.getSubOrderInfo();
			CommandConstant.SUB_ORDER_NO_OF_BET_INFO = service.getSubOrderInfoNoOfBet();
			tmpMap.put("result", "success");
		} catch (Exception e) {
			tmpMap.put("result", "fail");
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
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

	/**
	 * forced to update all lottery struct and basic info like switch etc...
	 * 
	 * @throws IOException
	 */
	public void updateAllLotteryInfos(HttpServletRequest req, HttpServletResponse res) throws IOException {
		SubOrderInfoServiceImpl service = null;
		SubOrderInfoDaoImpl dao = null;
		Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
		try {
			service = new SubOrderInfoServiceImpl();
			dao = new SubOrderInfoDaoImpl();
			service.setDao(dao);
			CommandConstant.ALL_LOTTERY_INFO = service.getAllLottery();
			CommandConstant.ALL_LOTTERY_TITLE_INFO = service.getTitleAuth();
			CommandConstant.CHECK_UPDATE_ALL_LOTTERY_TIME = new Date().getTime();
			CommandConstant.DB_LAST_UPDATE_TIME_OF_ALL_LOTTERY_INFO = service.getAllLotteryLastUpdateTime();
			tmpMap.put("result", "success");
		} catch (Exception e) {
			tmpMap.put("result", "fail");
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
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

	/**
	 * forecd to update newest baseline (high and low frequency)
	 * 
	 * @throws IOException
	 */
	public void updateBaseline(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("updateBaseline");
		BaselineInfoServiceImpl service = null;
		BaselineInfoDaoImpl dao = null;
		Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
		try {
			service = new BaselineInfoServiceImpl();
			dao = new BaselineInfoDaoImpl();
			service.setDao(dao);
			CommandConstant.CURRENT_BET_RATIO = service.getBaseline();
			CommandConstant.CHECK_UPDATE_BASELINE_TIME = new Date().getTime();
			CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE = service.getBaselineLastUpdateTime();

			CommandConstant.CURRENT_BET_RATIO_LF = service.getBaselineLF();
			CommandConstant.CHECK_UPDATE_BASELINE_TIME_LF = new Date().getTime();
			CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE_LF = service.getBaselineLastUpdateTimeLF();

			tmpMap.put("result", "success");
		} catch (Exception e) {
			tmpMap.put("result", "fail");
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
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
