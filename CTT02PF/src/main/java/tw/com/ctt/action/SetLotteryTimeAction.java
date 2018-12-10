package tw.com.ctt.action;

import java.io.IOException;
import java.io.PrintWriter;
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

import tw.com.ctt.dao.impl.SetLotteryTimeDaoImpl;
import tw.com.ctt.model.LotteryTimeSetBean;
import tw.com.ctt.service.impl.SetLotteryTimeServiceImpl;
import tw.com.ctt.util.SettingLottery;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "SetLotteryTime", urlPatterns = { "/SetLotteryTime" })
public class SetLotteryTimeAction extends BaseAction {

	private static final long serialVersionUID = -7637371133919643134L;
	private static final Logger LOG = LogManager.getLogger(LoginAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = {};
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = { "/SetLotteryTime!getLotteryTimeList", "/SetLotteryTime!getLotteryType", 
			"/SetLotteryTime!getLotteryTimeSetLog","/SetLotteryTime!insertLotterTimeyList","/SetLotteryTime!saveLotteryTime" };
	private String[] authURL = { "/SetLotteryTime" };

	public SetLotteryTimeAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("SetLotteryTimeAction start");
	}

	public void main(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("SetLotteryTimeAction.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial SetLotteryTime.js
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_SLT_checkSetLotteryTime(){\n");
			sb.append("    if(typeof SetLotteryTime==='undefined' || typeof SetLotteryTime != 'function' ){\n");
			sb.append("        checkJS('SetLotteryTime', 'AccountManage/SetLotteryTime.js');\n");
			sb.append("    } else {\n");
			sb.append("        SetLotteryTime();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_SLT_checkSetLotteryTime();\n");
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
			LOG.error("SetLotteryTimeAction.main.Exception, " + e.getMessage());
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

	public void getLotteryType(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, Object> tmpMap = null;
		SetLotteryTimeServiceImpl service = null;
		SetLotteryTimeDaoImpl dao = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new SetLotteryTimeServiceImpl();
			dao = new SetLotteryTimeDaoImpl();
			service.setDao(dao);

			tmpMap.put("lotteryType", service.getLotteryType());

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
		return;
	}

	public void getLotteryTimeList(HttpServletRequest req, HttpServletResponse res) throws IOException {
		SetLotteryTimeServiceImpl service = null;
		SetLotteryTimeDaoImpl dao = null;
		List<Map<String, Object>> listMap = null;
		Map<String, Object> tmpMap = null;
		int id = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("id") != null && !"".equals(req.getParameter("id").toString())) {
				listMap = new ArrayList<Map<String, Object>>();
				service = new SetLotteryTimeServiceImpl();
				dao = new SetLotteryTimeDaoImpl();
				service.setDao(dao);
				id = Integer.parseInt(req.getParameter("id").toString());
				listMap = service.getLotteryTimeList(id);
			}
			tmpMap.put("lotteryTimeList", listMap);

		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);
			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);
			if (listMap != null) {
				listMap.clear();
				listMap = null;
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

	public void insertLotterTimeyList(HttpServletRequest req, HttpServletResponse res) throws IOException {
		List<LotteryTimeSetBean> listMap = null;
		Map<String, Object> tmpMap = null;
		int id = 0;
		int createPeriodType = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("data") != null && !"".equals(req.getParameter("data").toString()) && req.getParameter("id") != null
					&& !"".equals(req.getParameter("id").toString()) && req.getParameter("createPeriodType") != null
					&& !"".equals(req.getParameter("createPeriodType").toString())) {
				id = Integer.parseInt(req.getParameter("id").toString());
				createPeriodType = Integer.parseInt(req.getParameter("createPeriodType").toString());

				JSONArray jsonArr = new JSONArray(req.getParameter("data").toString());
				SettingLottery settingLottery = new SettingLottery(id);
				for (int i = 0; i < jsonArr.length(); i++) {
					long kjTime = 0;
					long intervals = 0;
					long stopBettingTime = 0;
					long giveUpKjTime = 0;
					int issue = 0;
					int createWeekType = 0;
					int createPeriodWeekType = 0;
					long createPeriodTime = 0;
					long jumpOffTime = 0;
					long startBettingTime = 0;
					int startBettingWeekType = 0;

					JSONObject json = jsonArr.getJSONObject(i);

					for (String key : json.keySet()) {
						if (key.equals("kjTime")) {
							kjTime = json.getLong(key);
						} else if (key.equals("intervals")) {
							intervals = json.getLong(key);
						} else if (key.equals("stopBettingTime")) {
							stopBettingTime = json.getLong(key);
						} else if (key.equals("giveUpKjTime")) {
							giveUpKjTime = json.getLong(key);
						} else if (key.equals("issue")) {
							issue = json.getInt(key);
						} else if (key.equals("createWeekType")) {
							createWeekType = json.getInt(key);
						} else if (key.equals("createPeriodWeekType")) {
							createPeriodWeekType = json.getInt(key);
						} else if (key.equals("createPeriodTime")) {
							createPeriodTime = json.getLong(key);
						} else if (key.equals("jumpOffTime")) {
							jumpOffTime = json.getLong(key);
						} else if (key.equals("startBettingTime")) {
							startBettingTime = json.getLong(key);
						} else if (key.equals("startBettingWeekType")) {
							startBettingWeekType = json.getInt(key);
						}

					}
					if (createPeriodType == 0 && issue > 0) {
						settingLottery.baseicSet(createPeriodType, kjTime, stopBettingTime, intervals, issue, giveUpKjTime, jumpOffTime);
					} else if (createPeriodType == 1) {
						settingLottery.baseicWeekSet(createPeriodType, kjTime, stopBettingTime, giveUpKjTime, jumpOffTime, startBettingWeekType,
								startBettingTime, createWeekType, createPeriodWeekType, createPeriodTime);
					}
				}
				listMap = settingLottery.getLotterySetting();

			}
			if (listMap == null) {
				listMap = new ArrayList<LotteryTimeSetBean>();
			}
			tmpMap.put("lotteryTimeList", listMap);

		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);
			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);
			if (listMap != null) {
				listMap.clear();
				listMap = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
		}
		return;
	}

	public void saveLotteryTime(HttpServletRequest req, HttpServletResponse res) throws IOException {
		SetLotteryTimeServiceImpl service = null;
		SetLotteryTimeDaoImpl dao = null;
		List<LotteryTimeSetBean> lotteryTimeSetBeanList = null;
		Map<String, Object> tmpMap = null;
		boolean todo = false;
		int zodiacType = 0;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("lotteryTimeJson") != null && !"".equals(req.getParameter("lotteryTimeJson").toString())
					&& req.getParameter("zodiacType") != null && !"".equals(req.getParameter("zodiacType").toString())) {
				lotteryTimeSetBeanList = new ArrayList<LotteryTimeSetBean>();
				zodiacType = Integer.parseInt(req.getParameter("zodiacType").toString());

				JSONObject json = new JSONObject(req.getParameter("lotteryTimeJson").toString());
				if (json != null && json.has("lotteryTimeList")) {
					JSONArray jsonArr = json.getJSONArray("lotteryTimeList");

					for (int i = 0; i < jsonArr.length(); i++) {
						LotteryTimeSetBean b1 = new LotteryTimeSetBean();
						if (jsonArr.getJSONObject(i).has("id")) {
							b1.setId(jsonArr.getJSONObject(i).getInt("id"));
						}
						if (jsonArr.getJSONObject(i).has("issue")) {
							b1.setIssue(jsonArr.getJSONObject(i).getInt("issue"));
						}
						if (jsonArr.getJSONObject(i).has("kjTime")) {
							b1.setKjTime(jsonArr.getJSONObject(i).getLong("kjTime"));
						}
						if (jsonArr.getJSONObject(i).has("platformKjTime")) {
							b1.setPlatformKjTime(jsonArr.getJSONObject(i).getLong("platformKjTime"));
						}
						if (jsonArr.getJSONObject(i).has("stopBettingTime")) {
							b1.setStopBettingTime(jsonArr.getJSONObject(i).getLong("stopBettingTime"));
						}
						if (jsonArr.getJSONObject(i).has("giveUpKjTime")) {
							b1.setGiveUpKjTime(jsonArr.getJSONObject(i).getLong("giveUpKjTime"));
						}
						if (jsonArr.getJSONObject(i).has("createWeekType")) {
							b1.setCreateWeekType(jsonArr.getJSONObject(i).getInt("createWeekType"));
						}
						if (jsonArr.getJSONObject(i).has("createPeriodWeekType")) {
							b1.setCreatePeriodWeekType(jsonArr.getJSONObject(i).getInt("createPeriodWeekType"));
						}
						if (jsonArr.getJSONObject(i).has("createPeriodType")) {
							b1.setCreatePeriodType(jsonArr.getJSONObject(i).getInt("createPeriodType"));
						}
						if (jsonArr.getJSONObject(i).has("jumpOffTime")) {
							b1.setJumpOffTime(jsonArr.getJSONObject(i).getInt("jumpOffTime"));
						}
						if (jsonArr.getJSONObject(i).has("createPeriodTime")) {
							b1.setCreatePeriodTime(jsonArr.getJSONObject(i).getLong("createPeriodTime"));
						}
						if (jsonArr.getJSONObject(i).has("startBettingWeekType")) {
							b1.setStartBettingWeekType(jsonArr.getJSONObject(i).getInt("startBettingWeekType"));
						}
						if (jsonArr.getJSONObject(i).has("startBettingTime")) {
							b1.setStartBettingTime(jsonArr.getJSONObject(i).getLong("startBettingTime"));
						}
						b1.setZodiacType(zodiacType);

						lotteryTimeSetBeanList.add(b1);
					}
					service = new SetLotteryTimeServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
					dao = new SetLotteryTimeDaoImpl();
					service.setDao(dao);

					todo = service.saveLotteryTime(lotteryTimeSetBeanList);

				}
			}

			tmpMap.put("result", todo);

		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);
			PrintWriter out = res.getWriter();
			out.println(responseJSONObject);
			if (lotteryTimeSetBeanList != null) {
				lotteryTimeSetBeanList.clear();
				lotteryTimeSetBeanList = null;
			}
			responseJSONObject = null;
			out.flush();
			out.close();
			out = null;
		}
		return;
	}

	public void getLotteryTimeSetLog(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> tmpMap = null;
		SetLotteryTimeServiceImpl service = null;
		SetLotteryTimeDaoImpl dao = null;
		try {
			if (accIdToLong("" + req.getSession().getAttribute("accId")) != 0) {
				tmpMap = new ConcurrentHashMap<String, Object>();
				service = new SetLotteryTimeServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
				dao = new SetLotteryTimeDaoImpl();
				service.setDao(dao);

				tmpMap.put("lotteryTimeSetLog", service.getLotteryTimeSetLog());

			}
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			res.setContentType("application/json;charset=UTF-8");
			JSONObject responseJSONObject = new JSONObject(tmpMap);

			PrintWriter out;
			try {
				out = res.getWriter();
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return;
	}

}
