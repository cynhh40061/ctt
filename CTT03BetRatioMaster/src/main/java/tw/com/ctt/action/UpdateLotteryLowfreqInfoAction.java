package tw.com.ctt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import tw.com.ctt.dao.impl.UpdateLotteryLowfreqInfoDaoImpl;
import tw.com.ctt.service.impl.UpdateLotteryLowfreqInfoServiceImpl;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "UpdateLotteryLowfreqInfo", urlPatterns = { "/UpdateLotteryLowfreqInfo" })
public class UpdateLotteryLowfreqInfoAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7570281421984740274L;
	private static final Logger LOG = LogManager.getLogger(UpdateLotteryLowfreqInfoAction.class.getName());

	private String[] loginURL = {};
	public String[] loginCheckURL = { "/UpdateLotteryLowfreqInfo!upadteHandicap", "/UpdateLotteryLowfreqInfo!upadteDescription",
			"/UpdateLotteryLowfreqInfo!updateBaseAmount", "/UpdateLotteryLowfreqInfo!updateNowAmount",
			"/UpdateLotteryLowfreqInfo!updateMark6BaseAmount", "/UpdateLotteryLowfreqInfo!updateMark6NowAmount" };
	public String[] loginCheckNoUpdateURL = {};
	public String[] extraURL = {};
	public String[] authURL = {};

	public UpdateLotteryLowfreqInfoAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("updateLotteryLowfreqInfo start");
	}

	public boolean checkLogin() {
		return true;
	}

	public void upadteHandicap(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		UpdateLotteryLowfreqInfoServiceImpl service = null;
		UpdateLotteryLowfreqInfoDaoImpl dao = null;

		int handicapId = 0;
		BigDecimal bonusSetMin = null;
		BigDecimal bonusSetMax = null;
		int maxWinBonus = 0;
		String log = null;
		boolean success = false;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new UpdateLotteryLowfreqInfoServiceImpl(accIdToLong(req.getParameter("accId").toString()), getIpAddr(req));
			dao = new UpdateLotteryLowfreqInfoDaoImpl();
			service.setDao(dao);

			handicapId = req.getParameter("handicapId") == null ? 0 : Integer.parseInt(req.getParameter("handicapId").toString());
			bonusSetMin = req.getParameter("bonusSetMin") == null ? new BigDecimal(0) : new BigDecimal(req.getParameter("bonusSetMin").toString());
			bonusSetMax = req.getParameter("bonusSetMax") == null ? new BigDecimal(0) : new BigDecimal(req.getParameter("bonusSetMax").toString());
			maxWinBonus = req.getParameter("maxWinBonus") == null ? 0 : Integer.parseInt(req.getParameter("maxWinBonus").toString());
			log = req.getParameter("log") == null ? null : req.getParameter("log").toString();

			if (handicapId > 0 && bonusSetMin.compareTo(BigDecimal.ZERO) == 1 && bonusSetMax.compareTo(BigDecimal.ZERO) == 1 && maxWinBonus >= 400000
					&& maxWinBonus <= 100000000 && !log.trim().equals("") && !log.isEmpty() && log != null) {
				success = service.upadteHandicap(handicapId, bonusSetMin, bonusSetMax, maxWinBonus, log);
			}

			if (success) {
				tmpMap.put("success", true);
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

	public void upadteDescription(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		UpdateLotteryLowfreqInfoServiceImpl service = null;
		UpdateLotteryLowfreqInfoDaoImpl dao = null;

		int minId = 0;
		String playedText = null;
		String lotteryRule = null;
		String lotteryExample = null;
		String log = null;
		boolean success = false;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new UpdateLotteryLowfreqInfoServiceImpl(accIdToLong(req.getParameter("accId").toString()), getIpAddr(req));
			dao = new UpdateLotteryLowfreqInfoDaoImpl();
			service.setDao(dao);

			minId = req.getParameter("minId") == null ? 0 : Integer.parseInt(req.getParameter("minId").toString());
			playedText = req.getParameter("playedText") == null ? null : req.getParameter("playedText").toString();
			lotteryRule = req.getParameter("lotteryRule") == null ? null : req.getParameter("lotteryRule").toString();
			lotteryExample = req.getParameter("lotteryExample") == null ? null : req.getParameter("lotteryExample").toString();
			log = req.getParameter("log") == null ? null : req.getParameter("log").toString();

			if (minId > 0 && playedText != null && lotteryRule != null && lotteryExample != null && !log.trim().equals("") && !log.isEmpty()
					&& log != null) {
				success = service.upadteDescription(minId, playedText, lotteryRule, lotteryExample, log);
			}

			if (success) {
				tmpMap.put("success", true);
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

	public void updateBaseAmount(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		UpdateLotteryLowfreqInfoServiceImpl service = null;
		UpdateLotteryLowfreqInfoDaoImpl dao = null;
		String updateList = null;
		String logList = null;
		JSONArray jsonList = null;
		Map<String, Object> map = null;
		List<Map<String, Object>> mapList = null;
		String[] key = { "localId", "typeId", "midId", "minId", "baseBet", "baseline", "dtSwitch", "dtRatio", "dtBonus", "prizeLevel", "handicap" };
		boolean success = false;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new UpdateLotteryLowfreqInfoServiceImpl(accIdToLong(req.getParameter("accId").toString()), getIpAddr(req));
			dao = new UpdateLotteryLowfreqInfoDaoImpl();
			service.setDao(dao);

			updateList = req.getParameter("updateList") == null ? null : req.getParameter("updateList").toString();
			logList = req.getParameter("logList") == null ? null : req.getParameter("logList").toString();

			jsonList = new JSONArray(updateList);
			mapList = new ArrayList<Map<String, Object>>();

			for (int i = 0; i < jsonList.length(); i++) {
				map = new ConcurrentHashMap<String, Object>();
				for (int j = 0; j < key.length; j++) {
					map.put(key[j], jsonList.getJSONObject(i).get(key[j]) == null ? "" : jsonList.getJSONObject(i).get(key[j]));
				}
				mapList.add(map);
			}

			if (mapList.size() > 0) {
				success = service.updateBaseAmount(mapList, logList);
			}

			if (success) {
				tmpMap.put("success", true);
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
			if (map != null) {
				map.clear();
				map = null;
			}
			if (mapList != null) {
				mapList.clear();
				mapList = null;
			}
			updateList = null;
			logList = null;
			jsonList = null;
			key = null;
			success = false;
		}
		return;
	}

	public void updateNowAmount(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		UpdateLotteryLowfreqInfoServiceImpl service = null;
		UpdateLotteryLowfreqInfoDaoImpl dao = null;
		String updateList = null;
		String logList = null;
		JSONArray jsonList = null;
		Map<String, Object> map = null;
		List<Map<String, Object>> mapList = null;
		String[] key = { "localId", "typeId", "midId", "minId", "nowBaseline", "prizeLevel", "handicap" };
		boolean success = false;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new UpdateLotteryLowfreqInfoServiceImpl(accIdToLong(req.getParameter("accId").toString()), getIpAddr(req));
			dao = new UpdateLotteryLowfreqInfoDaoImpl();
			service.setDao(dao);

			updateList = req.getParameter("updateList") == null ? null : req.getParameter("updateList").toString();
			logList = req.getParameter("logList") == null ? null : req.getParameter("logList").toString();

			jsonList = new JSONArray(updateList);
			mapList = new ArrayList<Map<String, Object>>();

			for (int i = 0; i < jsonList.length(); i++) {
				map = new ConcurrentHashMap<String, Object>();
				for (int j = 0; j < key.length; j++) {
					map.put(key[j], jsonList.getJSONObject(i).get(key[j]) == null ? "" : jsonList.getJSONObject(i).get(key[j]));
				}
				mapList.add(map);
			}

			if (mapList.size() > 0) {
				success = service.updateNowAmount(mapList, logList);
			}

			if (success) {
				tmpMap.put("success", true);
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
			if (map != null) {
				map.clear();
				map = null;
			}
			if (mapList != null) {
				mapList.clear();
				mapList = null;
			}
			updateList = null;
			logList = null;
			jsonList = null;
			key = null;
			success = false;
		}
		return;
	}

	public void updateMark6BaseAmount(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		UpdateLotteryLowfreqInfoServiceImpl service = null;
		UpdateLotteryLowfreqInfoDaoImpl dao = null;
		String updateList = null;
		String logList = null;
		JSONArray jsonList = null;
		Map<String, Object> map = null;
		List<Map<String, Object>> mapList = null;
		String[] key = { "localId", "typeId", "midId", "minId", "baseBet", "baseline", "dtSwitch", "dtRatio", "dtBonus", "prizeLevel", "handicap" };
		boolean success = false;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new UpdateLotteryLowfreqInfoServiceImpl(accIdToLong(req.getParameter("accId").toString()), getIpAddr(req));
			dao = new UpdateLotteryLowfreqInfoDaoImpl();
			service.setDao(dao);

			updateList = req.getParameter("updateList") == null ? null : req.getParameter("updateList").toString();
			logList = req.getParameter("logList") == null ? null : req.getParameter("logList").toString();

			jsonList = new JSONArray(updateList);
			mapList = new ArrayList<Map<String, Object>>();

			for (int i = 0; i < jsonList.length(); i++) {
				map = new ConcurrentHashMap<String, Object>();
				for (int j = 0; j < key.length; j++) {
					map.put(key[j], jsonList.getJSONObject(i).get(key[j]) == null ? "" : jsonList.getJSONObject(i).get(key[j]));
				}
				mapList.add(map);
			}

			if (mapList.size() > 0) {
				success = service.updateMark6BaseAmount(mapList, logList);
			}

			if (success) {
				tmpMap.put("success", true);
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
			if (map != null) {
				map.clear();
				map = null;
			}
			if (mapList != null) {
				mapList.clear();
				mapList = null;
			}
			updateList = null;
			logList = null;
			jsonList = null;
			key = null;
			success = false;
		}
		return;
	}

	public void updateMark6NowAmount(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		UpdateLotteryLowfreqInfoServiceImpl service = null;
		UpdateLotteryLowfreqInfoDaoImpl dao = null;
		String updateList = null;
		String logList = null;
		JSONArray jsonList = null;
		Map<String, Object> map = null;
		List<Map<String, Object>> mapList = null;
		String[] key = { "localId", "typeId", "midId", "minId", "nowBaseline", "prizeLevel", "handicap" };
		boolean success = false;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new UpdateLotteryLowfreqInfoServiceImpl(accIdToLong(req.getParameter("accId").toString()), getIpAddr(req));
			dao = new UpdateLotteryLowfreqInfoDaoImpl();
			service.setDao(dao);

			updateList = req.getParameter("updateList") == null ? null : req.getParameter("updateList").toString();
			logList = req.getParameter("logList") == null ? null : req.getParameter("logList").toString();

			jsonList = new JSONArray(updateList);
			mapList = new ArrayList<Map<String, Object>>();

			for (int i = 0; i < jsonList.length(); i++) {
				map = new ConcurrentHashMap<String, Object>();
				for (int j = 0; j < key.length; j++) {
					map.put(key[j], jsonList.getJSONObject(i).get(key[j]) == null ? "" : jsonList.getJSONObject(i).get(key[j]));
				}
				mapList.add(map);
			}

			if (mapList.size() > 0) {
				success = service.updateMark6NowAmount(mapList, logList);
			}

			if (success) {
				tmpMap.put("success", true);
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
			if (map != null) {
				map.clear();
				map = null;
			}
			if (mapList != null) {
				mapList.clear();
				mapList = null;
			}
			updateList = null;
			logList = null;
			jsonList = null;
			key = null;
			success = false;
		}
		return;
	}
}
