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

import tw.com.ctt.dao.impl.LotterySetDaoImpl;
import tw.com.ctt.service.impl.LotterySetServiceImpl;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "LotterySet", urlPatterns = { "/LotterySet" })
public class LotterySetAction extends BaseAction {

	private static final long serialVersionUID = -6034008480270604561L;
	private static final Logger LOG = LogManager.getLogger(LotterySetAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = {};
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = { "/LotterySet!getUpperSwitchInfo", "/LotterySet!getSwitchDetail", "/LotterySet!updateLotterySwitch",
			"/LotterySet!getLotteryAmountInfo", "/LotterySet!upadteDescription", "/LotterySet!updateAmountInfo", "/LotterySet!upadteHandicap",
			"/LotterySet!getLotterySwitchSettingLog", "/LotterySet!getLotteryAmountSettingLog" };
	private String[] authURL = { "/LotterySet!mainLotterySwitch", "/LotterySet!mainLotteryAmount", "/LotterySet!mainLotteryLowfreqAmountSet" };

	public LotterySetAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("LotterySetAction start");
	}

	public void mainLotteryAmount(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("LotterySetAction.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial blackWhite.js
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_AG_checkLotteryAmount(){\n");
			sb.append("    if(typeof LotterySet_Amount==='undefined' || typeof LotterySet_Amount != 'function' ){\n");
			sb.append("        checkJS('LotterySet_Amount', 'AccountManage/LotterySet.js');\n");
			sb.append("    } else {\n");
			sb.append("        LotterySet_Amount();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_AG_checkLotteryAmount();\n");
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
			LOG.error("LotterySetAction.main.Exception, " + e.getMessage());
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

	public void mainLotterySwitch(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("LotterySetAction.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial blackWhite.js
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_AG_checkLotterySwitch(){\n");
			sb.append("    if(typeof LotterySet_Switch==='undefined' || typeof LotterySet_Switch != 'function' ){\n");
			sb.append("        checkJS('LotterySet_Switch', 'AccountManage/LotterySet.js');\n");
			sb.append("    } else {\n");
			sb.append("        LotterySet_Switch();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_AG_checkLotterySwitch();\n");
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
			LOG.error("LotterySetAction.main.Exception, " + e.getMessage());
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

	public void mainLotteryLowfreqAmountSet(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("LotteryLowfreqAmountSetAction.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_AG_checkLotteryLowfreqAmountSet(){\n");
			sb.append("    if(typeof LotteryLowfreqAmountSet === 'undefined' || typeof LotteryLowfreqAmountSet != 'function' ){\n");
			sb.append("        checkJS('LotteryLowfreqAmountSet', 'AccountManage/LotteryLowfreqAmountSet.js');\n");
			sb.append("    } else {\n");
			sb.append("        LotteryLowfreqAmountSet();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_AG_checkLotteryLowfreqAmountSet();\n");
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
			LOG.error("LotteryLowfreqAmountSetAction.main.Exception, " + e.getMessage());
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

	public void getUpperSwitchInfo(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		LotterySetServiceImpl service = null;
		LotterySetDaoImpl dao = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotterySetServiceImpl();
			dao = new LotterySetDaoImpl();
			service.setDao(dao);

			tmpMap.put("getLotterySwitchTypeList", service.getLotteryTypeList());
			tmpMap.put("getUpperSwitchInfo", service.getUpperSwitchInfo());
			tmpMap.put("getLotteryMidList", service.getLotteryMidList());

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

	public void getSwitchDetail(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		LotterySetServiceImpl service = null;
		LotterySetDaoImpl dao = null;
		int lotteryId = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			lotteryId = req.getParameter("lotteryId") == null ? 0 : Integer.parseInt(req.getParameter("lotteryId"));
			service = new LotterySetServiceImpl();
			dao = new LotterySetDaoImpl();
			service.setDao(dao);

			if (lotteryId > 0) {
				tmpMap.put("getSwitchDetailTitle", service.getSwitchDetailTitle(lotteryId));
				tmpMap.put("getSwitchDetail", service.getSwitchDetail(lotteryId));
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
			lotteryId = 0;
		}
		return;
	}

	public void updateLotterySwitch(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		LotterySetServiceImpl service = null;
		LotterySetDaoImpl dao = null;
		int lotteryId = 0;
		int totalSwitch = 0;
		String lotterySwitchList = null;
		JSONArray jsonList = null;
		Map<String, Object> map = null;
		List<Map<String, Object>> mapList = null;
		String[] key = { "midId", "playedId", "midTitle", "switch" };
		boolean success = false;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotterySetServiceImpl();

			dao = new LotterySetDaoImpl();
			service = new LotterySetServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
			service.setDao(dao);

			lotterySwitchList = req.getParameter("lotterySwitchList");
			jsonList = new JSONArray(lotterySwitchList);
			mapList = new ArrayList<Map<String, Object>>();

			for (int i = 0; i < jsonList.length(); i++) {
				map = new ConcurrentHashMap<String, Object>();
				for (int j = 0; j < key.length; j++) {
					map.put(key[j], jsonList.getJSONObject(i).get(key[j]) == null ? "" : jsonList.getJSONObject(i).get(key[j]));
				}
				mapList.add(map);
			}

			lotteryId = req.getParameter("lotteryId") == null ? 0 : Integer.parseInt(req.getParameter("lotteryId"));
			totalSwitch = req.getParameter("totalSwitch") == null ? -1 : Integer.parseInt(req.getParameter("totalSwitch"));
			if (lotteryId > 0 && totalSwitch >= 0) {
				success = service.updateLotterySwitch(lotteryId, mapList, totalSwitch);
			}

			if (success) {
				tmpMap.put("success", true);
				tmpMap.put("lotteryId", lotteryId);
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
			lotteryId = 0;
			lotterySwitchList = null;
			jsonList = null;
			key = null;
			success = false;
		}
		return;
	}

	public void getLotteryAmountInfo(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		LotterySetServiceImpl service = null;
		LotterySetDaoImpl dao = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotterySetServiceImpl();
			dao = new LotterySetDaoImpl();
			service.setDao(dao);

			tmpMap.put("getLotteryAmountTypeList", service.getLotteryTypeList());
			tmpMap.put("getLotteryAmountLocalList", service.getLotteryAmountLocalList());
			tmpMap.put("getLotteryAmountPlayedList", service.getLotteryAmountPlayedList());
			tmpMap.put("getHandicapInfo", service.getHandicapInfo());
			tmpMap.put("getTotalAmountInfo", service.getTotalAmountInfo());
			tmpMap.put("getLotteryMidList", service.getLotteryMidList());

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
		LotterySetServiceImpl service = null;
		LotterySetDaoImpl dao = null;
		int minId = 0;
		String playedText = null;
		String lotteryRule = null;
		String lotteryExample = null;
		String descriptionLog = null;
		boolean success = false;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotterySetServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
			dao = new LotterySetDaoImpl();
			service.setDao(dao);

			minId = req.getParameter("minId") == null ? 0 : Integer.parseInt(req.getParameter("minId"));
			playedText = req.getParameter("playedText") == null ? "" : req.getParameter("playedText").toString();
			lotteryRule = req.getParameter("lotteryRule") == null ? "" : req.getParameter("lotteryRule").toString();
			lotteryExample = req.getParameter("lotteryExample") == null ? "" : req.getParameter("lotteryExample").toString();
			descriptionLog = req.getParameter("descriptionLog") == null ? "" : req.getParameter("descriptionLog").toString();

			if (minId > 0) {
				success = service.upadteDescription(minId, playedText, lotteryRule, lotteryExample, descriptionLog);
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
			minId = 0;
			playedText = null;
			lotteryRule = null;
			lotteryExample = null;
			success = false;
		}
		return;
	}

	public void updateAmountInfo(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		LotterySetServiceImpl service = null;
		LotterySetDaoImpl dao = null;
		String amountInfoList = null;
		String amountLogList = null;
		JSONArray jsonList = null;
		Map<String, Object> map = null;
		List<Map<String, Object>> mapList = null;
		String[] key = { "localId", "typeId", "midId", "minId", "minName", "baseBet", "betLevel1", "betLevel2", "baseline", "baselineLevel1",
				"baselineLevel2", "dtSwitch", "dtRatio", "dtBonus", "prizeLevel" };
		boolean success = false;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotterySetServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
			dao = new LotterySetDaoImpl();
			service.setDao(dao);

			amountInfoList = req.getParameter("amountInfoList");
			amountLogList = req.getParameter("amountLogList");

			jsonList = new JSONArray(amountInfoList);
			mapList = new ArrayList<Map<String, Object>>();

			for (int i = 0; i < jsonList.length(); i++) {
				map = new ConcurrentHashMap<String, Object>();
				for (int j = 0; j < key.length; j++) {
					map.put(key[j], jsonList.getJSONObject(i).get(key[j]) == null ? "" : jsonList.getJSONObject(i).get(key[j]));
				}
				mapList.add(map);
			}

			if (mapList.size() > 0) {
				success = service.updateAmountInfo(mapList, amountLogList);
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
			amountInfoList = null;
			jsonList = null;
			key = null;
			success = false;
		}
		return;
	}

	public void upadteHandicap(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		LotterySetServiceImpl service = null;
		LotterySetDaoImpl dao = null;
		int handicapId = 0;
		BigDecimal bonusSetMin = null;
		BigDecimal bonusSetMax = null;
		int relativeBaseline = 0;
		int maxWinBonus = 0;
		String handicapLog = null;
		boolean success = false;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotterySetServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
			dao = new LotterySetDaoImpl();
			service.setDao(dao);

			handicapId = req.getParameter("handicapId") == null ? 0 : Integer.parseInt(req.getParameter("handicapId").toString());
			bonusSetMin = req.getParameter("bonusSetMin") == null ? new BigDecimal(0) : new BigDecimal(req.getParameter("bonusSetMin").toString());
			bonusSetMax = req.getParameter("bonusSetMax") == null ? new BigDecimal(0) : new BigDecimal(req.getParameter("bonusSetMax").toString());
			relativeBaseline = req.getParameter("relativeBaseline") == null ? 0 : Integer.parseInt(req.getParameter("relativeBaseline").toString());
			maxWinBonus = req.getParameter("maxWinBonus") == null ? 0 : Integer.parseInt(req.getParameter("maxWinBonus").toString());
			handicapLog = req.getParameter("handicapLog") == null ? "" : req.getParameter("handicapLog").toString();

			if (handicapId > 0 && bonusSetMin.compareTo(BigDecimal.ZERO) == 1 && bonusSetMax.compareTo(BigDecimal.ZERO) == 1
					&& relativeBaseline > 0) {
				success = service.upadteHandicap(handicapId, bonusSetMin, bonusSetMax, relativeBaseline, maxWinBonus, handicapLog);
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
			handicapId = 0;
			bonusSetMin = null;
			bonusSetMax = null;
			relativeBaseline = 0;
			success = false;
		}
		return;
	}

	public void getLotterySwitchSettingLog(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		LotterySetServiceImpl service = null;
		LotterySetDaoImpl dao = null;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotterySetServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
			dao = new LotterySetDaoImpl();
			service.setDao(dao);

			tmpMap.put("lotterySettingLog", service.getLotterySwitchSettingLog());

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

	public void getLotteryAmountSettingLog(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		LotterySetServiceImpl service = null;
		LotterySetDaoImpl dao = null;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotterySetServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
			dao = new LotterySetDaoImpl();
			service.setDao(dao);

			tmpMap.put("lotterySettingLog", service.getLotteryAmountSettingLog());

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
