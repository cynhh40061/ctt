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

import tw.com.ctt.dao.impl.GetLotteryLowfreqInfoDaoImpl;
import tw.com.ctt.service.impl.GetLotteryLowfreqInfoServiceImpl;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "GetLotteryLowfreqInfo", urlPatterns = { "/GetLotteryLowfreqInfo" })
public class GetLotteryLowfreqInfoAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5444244168633579002L;
	private static final Logger LOG = LogManager.getLogger(GetLotteryLowfreqInfoAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = { "/GetLotteryLowfreqInfo!getAmountBaseList", "/GetLotteryLowfreqInfo!getLowfreqBaseAmount",
			"/GetLotteryLowfreqInfo!getLowfreqNowAmount", "/GetLotteryLowfreqInfo!getMark6BaseAmount", "/GetLotteryLowfreqInfo!getMark6NowAmount",
			"/GetLotteryLowfreqInfo!getLowfreqAmountSettingLog" };
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = {};
	private String[] authURL = {};

	public GetLotteryLowfreqInfoAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("getLotteryLowfreqInfoAction start");
	}

	public boolean checkLogin() {
		return true;
	}

	public void getAmountBaseList(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		GetLotteryLowfreqInfoServiceImpl service = null;
		GetLotteryLowfreqInfoDaoImpl dao = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new GetLotteryLowfreqInfoServiceImpl();
			dao = new GetLotteryLowfreqInfoDaoImpl();
			service.setDao(dao);

			tmpMap.put("getHandicapInfo", service.getHandicapInfo());
			tmpMap.put("getLotteryAmountTypeList", service.getLotteryTypeList());
			tmpMap.put("getLotteryAmountPlayedList", service.getLotteryAmountPlayedList());
			tmpMap.put("getLotteryMidList", service.getLotteryMidList());
			// tmpMap.put("getLotteryAmountLocalList", service.getLotteryAmountLocalList());

		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
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

	public void getLowfreqBaseAmount(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		GetLotteryLowfreqInfoServiceImpl service = null;
		GetLotteryLowfreqInfoDaoImpl dao = null;
		int handicap = 0;
		int typeId = 0;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new GetLotteryLowfreqInfoServiceImpl();
			dao = new GetLotteryLowfreqInfoDaoImpl();
			service.setDao(dao);

			handicap = req.getParameter("handicapPage") == null ? 0 : Integer.parseInt(req.getParameter("handicapPage"));
			typeId = req.getParameter("typePage") == null ? 0 : Integer.parseInt(req.getParameter("typePage"));

			if (handicap >= 1 && handicap <= 5 && (typeId == 6 || typeId == 7)) {
				tmpMap.put("getAmountInfo", service.getLowfreqBaseAmount(handicap, typeId));
			}
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
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

	public void getLowfreqNowAmount(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		GetLotteryLowfreqInfoServiceImpl service = null;
		GetLotteryLowfreqInfoDaoImpl dao = null;
		int handicap = 0;
		int typeId = 0;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new GetLotteryLowfreqInfoServiceImpl();
			dao = new GetLotteryLowfreqInfoDaoImpl();
			service.setDao(dao);

			handicap = req.getParameter("handicapPage") == null ? 0 : Integer.parseInt(req.getParameter("handicapPage"));
			typeId = req.getParameter("typePage") == null ? 0 : Integer.parseInt(req.getParameter("typePage"));

			if (handicap >= 1 && handicap <= 5 && (typeId == 6 || typeId == 7)) {
				tmpMap.put("getAmountInfo", service.getLowfreqNowAmount(handicap, typeId));
			}
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
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

	public void getMark6BaseAmount(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		GetLotteryLowfreqInfoServiceImpl service = null;
		GetLotteryLowfreqInfoDaoImpl dao = null;
		int handicap = 0;
		int typeId = 0;
		int playedId = 0;
		int midId = 0;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new GetLotteryLowfreqInfoServiceImpl();
			dao = new GetLotteryLowfreqInfoDaoImpl();
			service.setDao(dao);

			handicap = req.getParameter("handicapPage") == null ? 0 : Integer.parseInt(req.getParameter("handicapPage"));
			typeId = req.getParameter("typePage") == null ? 0 : Integer.parseInt(req.getParameter("typePage"));
			playedId = req.getParameter("mark6PlayedPage") == null ? 0 : Integer.parseInt(req.getParameter("mark6PlayedPage"));
			midId = req.getParameter("mark6MidPage") == null ? 0 : Integer.parseInt(req.getParameter("mark6MidPage"));

			if (handicap >= 1 && handicap <= 5 && typeId == 618 && playedId >= 619 && playedId <= 630 && midId >= 631 && midId <= 668) {
				tmpMap.put("getAmountInfo", service.getMark6BaseAmount(handicap, playedId, midId));
			}
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
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

	public void getMark6NowAmount(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		GetLotteryLowfreqInfoServiceImpl service = null;
		GetLotteryLowfreqInfoDaoImpl dao = null;
		int handicap = 0;
		int typeId = 0;
		int playedId = 0;
		int midId = 0;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new GetLotteryLowfreqInfoServiceImpl();
			dao = new GetLotteryLowfreqInfoDaoImpl();
			service.setDao(dao);

			handicap = req.getParameter("handicapPage") == null ? 0 : Integer.parseInt(req.getParameter("handicapPage"));
			typeId = req.getParameter("typePage") == null ? 0 : Integer.parseInt(req.getParameter("typePage"));
			playedId = req.getParameter("mark6PlayedPage") == null ? 0 : Integer.parseInt(req.getParameter("mark6PlayedPage"));
			midId = req.getParameter("mark6MidPage") == null ? 0 : Integer.parseInt(req.getParameter("mark6MidPage"));

			if (handicap >= 1 && handicap <= 5 && typeId == 618 && playedId >= 619 && playedId <= 630 && midId >= 631 && midId <= 668) {
				tmpMap.put("getAmountInfo", service.getMark6NowAmount(handicap, playedId, midId));
			}
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
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

	public void getLowfreqAmountSettingLog(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		GetLotteryLowfreqInfoServiceImpl service = null;
		GetLotteryLowfreqInfoDaoImpl dao = null;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();

			service = new GetLotteryLowfreqInfoServiceImpl(accIdToLong(req.getParameter("accId").toString()), getIpAddr(req));
			dao = new GetLotteryLowfreqInfoDaoImpl();
			service.setDao(dao);

			tmpMap.put("log", service.getLowfreqAmountSettingLog());

		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
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
