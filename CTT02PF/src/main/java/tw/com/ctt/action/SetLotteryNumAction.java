package tw.com.ctt.action;

//import static tw.com.ctt.constant.LotteryConstant.AF11X5;
//import static tw.com.ctt.constant.LotteryConstant.AHK3;
//import static tw.com.ctt.constant.LotteryConstant.BJPK10;
//import static tw.com.ctt.constant.LotteryConstant.CQSSC;
//import static tw.com.ctt.constant.LotteryConstant.FC3D;
//import static tw.com.ctt.constant.LotteryConstant.GD11X5;
//import static tw.com.ctt.constant.LotteryConstant.GX11X5;
//import static tw.com.ctt.constant.LotteryConstant.GXK3;
//import static tw.com.ctt.constant.LotteryConstant.HBK3;
//import static tw.com.ctt.constant.LotteryConstant.HLJ11X5;
//import static tw.com.ctt.constant.LotteryConstant.HLK3;
//import static tw.com.ctt.constant.LotteryConstant.JLK3;
//import static tw.com.ctt.constant.LotteryConstant.JS11X5;
//import static tw.com.ctt.constant.LotteryConstant.JSK3;
//import static tw.com.ctt.constant.LotteryConstant.JX11X5;
//import static tw.com.ctt.constant.LotteryConstant.LN11X5;
//import static tw.com.ctt.constant.LotteryConstant.PL5;
//import static tw.com.ctt.constant.LotteryConstant.QQFFC;
//import static tw.com.ctt.constant.LotteryConstant.SD11X5;
//import static tw.com.ctt.constant.LotteryConstant.SH11X5;
//import static tw.com.ctt.constant.LotteryConstant.TJSSC;
//import static tw.com.ctt.constant.LotteryConstant.TXFFC;
//import static tw.com.ctt.constant.LotteryConstant.XJSSC;
//import static tw.com.ctt.constant.LotteryConstant.YN11X5;
//import static tw.com.ctt.constant.LotteryConstant.YNASSC;

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

import tw.com.ctt.dao.impl.SetLotteryNumDaoImpl;
import tw.com.ctt.service.impl.SetLotteryNumServiceImpl;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "SetLotteryNum", urlPatterns = { "/SetLotteryNum" })
public class SetLotteryNumAction extends BaseAction {

	private static final long serialVersionUID = -7637371133919643134L;
	private static final Logger LOG = LogManager.getLogger(LoginAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = {};
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = { "/SetLotteryNum!getLotteryListData", "/SetLotteryNum!lotteryOpsLogMain", "/SetLotteryNum!getLotteryListData",
			"/SetLotteryNum!setLotteryNum", "/SetLotteryNum!setLotteryFail", "/SetLotteryNum!deletePeriodNum", "/SetLotteryNum!getLotteryOpsLog",
			"/SetLotteryNum!callCheckTheLottery", "/SetLotteryNum!getLotteryNumSetLog", "/SetLotteryNum!recoverPeriodNumWinningMoney",
			"/SetLotteryNum!renewCallCheckTheLottery" };
	private String[] authURL = { "/SetLotteryNum" };

	public SetLotteryNumAction() {
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
		LOG.debug("SetLotteryNumAction.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial SetLotteryTime.js
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_SLT_checkSetLotteryNum(){\n");
			sb.append("    if(typeof SetLotteryNum ==='undefined' || typeof SetLotteryNum != 'function' ){\n");
			sb.append("        checkJS('SetLotteryNum', 'AccountManage/SetLotteryNum.js');\n");
			sb.append("    } else {\n");
			sb.append("        SetLotteryNum();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_SLT_checkSetLotteryNum();\n");
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
			LOG.error("SetLotteryNumAction.main.Exception, " + e.getMessage());
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

	public void lotteryOpsLogMain(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("lotteryOpsLogMain.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial SetLotteryTime.js
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_SLT_checkLotteryOpsLog(){\n");
			sb.append("    if(typeof lotteryOpsLog ==='undefined' || typeof lotteryOpsLog != 'function' ){\n");
			sb.append("        checkJS('lotteryOpsLog', 'AccountManage/SetLotteryNum.js');\n");
			sb.append("    } else {\n");
			sb.append("        lotteryOpsLog();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_SLT_checkLotteryOpsLog();\n");
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
			LOG.error("SetLotteryNumAction.main.Exception, " + e.getMessage());
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

	public void getLotteryListData(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> tmpMap = null;
		SetLotteryNumServiceImpl service = null;
		SetLotteryNumDaoImpl dao = null;
		try {
			if (req.getParameter("lotteryId") != null && !"".equals(req.getParameter("lotteryId").toString())) {
				tmpMap = new ConcurrentHashMap<String, Object>();
				service = new SetLotteryNumServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
				dao = new SetLotteryNumDaoImpl();
				service.setDao(dao);

				int lotteryId = req.getParameter("lotteryId") != null ? !"".equals(clearNum(req.getParameter("lotteryId").toString()))
						? Integer.parseInt(clearNum(req.getParameter("lotteryId").toString()))
						: 0 : 0;
				String searchDate = req.getParameter("searchDate") != null && !"".equals(req.getParameter("searchDate").toString())
						? req.getParameter("searchDate").toString()
						: "";

				tmpMap.put("lotteryList", service.getNextAndPreviousLotteryListData(lotteryId));
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

	public void setLotteryNum(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> tmpMap = null;
		SetLotteryNumServiceImpl service = null;
		SetLotteryNumDaoImpl dao = null;
		try {
			if (req.getParameter("lotteryId") != null && !"".equals(req.getParameter("lotteryId").toString()) && req.getParameter("periodNum") != null
					&& !"".equals(req.getParameter("periodNum").toString()) && req.getParameter("data") != null
					&& !"".equals(req.getParameter("data").toString())) {

				String periodNum = req.getParameter("periodNum").toString();
				String data = req.getParameter("data").toString();
				int id = Integer.parseInt(req.getParameter("lotteryId").toString());

				tmpMap = new ConcurrentHashMap<String, Object>();
				service = new SetLotteryNumServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
				dao = new SetLotteryNumDaoImpl();
				service.setDao(dao);

				tmpMap.put("lotteryStatus", service.setLotteryNum(id, periodNum, data));
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

	public void setLotteryFail(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> tmpMap = null;
		SetLotteryNumServiceImpl service = null;
		SetLotteryNumDaoImpl dao = null;
		try {
			if (req.getParameter("lotteryId") != null && !"".equals(req.getParameter("lotteryId").toString()) && req.getParameter("periodNum") != null
					&& !"".equals(req.getParameter("periodNum").toString()) && req.getParameter("data") != null
					&& !"".equals(req.getParameter("data").toString())) {

				String periodNum = req.getParameter("periodNum").toString();
				String data = req.getParameter("data").toString();
				int id = Integer.parseInt(req.getParameter("lotteryId").toString());

				tmpMap = new ConcurrentHashMap<String, Object>();
				service = new SetLotteryNumServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
				dao = new SetLotteryNumDaoImpl();
				service.setDao(dao);

				tmpMap.put("lotteryStatus", service.setLotteryFali(id, periodNum, data));
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

	public void deletePeriodNum(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> tmpMap = null;
		SetLotteryNumServiceImpl service = null;
		SetLotteryNumDaoImpl dao = null;
		try {
			if (req.getParameter("lotteryId") != null && !"".equals(req.getParameter("lotteryId").toString()) && req.getParameter("periodNum") != null
					&& !"".equals(req.getParameter("periodNum").toString())) {

				String periodNum = req.getParameter("periodNum").toString();
				int id = Integer.parseInt(req.getParameter("lotteryId").toString());

				tmpMap = new ConcurrentHashMap<String, Object>();
				service = new SetLotteryNumServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
				dao = new SetLotteryNumDaoImpl();
				service.setDao(dao);

				tmpMap.put("lotteryStatus", service.deletePeriodNum(id, periodNum));

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

	public void withdrawalPeriodNum(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> tmpMap = null;
		SetLotteryNumServiceImpl service = null;
		SetLotteryNumDaoImpl dao = null;
		try {
			if (req.getParameter("lotteryId") != null && !"".equals(req.getParameter("lotteryId").toString()) && req.getParameter("periodNum") != null
					&& !"".equals(req.getParameter("periodNum").toString())) {

				String periodNum = req.getParameter("periodNum").toString();
				int id = Integer.parseInt(req.getParameter("lotteryId").toString());

				tmpMap = new ConcurrentHashMap<String, Object>();
				service = new SetLotteryNumServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
				dao = new SetLotteryNumDaoImpl();
				service.setDao(dao);

				tmpMap.put("lotteryStatus", service.withdrawalPeriodNum(id, periodNum));
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

	public void callCheckTheLottery(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> tmpMap = null;
		SetLotteryNumServiceImpl service = null;
		SetLotteryNumDaoImpl dao = null;
		try {
			if (req.getParameter("lotteryId") != null && !"".equals(req.getParameter("lotteryId").toString()) && req.getParameter("periodNum") != null
					&& !"".equals(req.getParameter("periodNum").toString())) {

				long periodNum = Long.parseLong(req.getParameter("periodNum").toString());
				int id = Integer.parseInt(req.getParameter("lotteryId").toString());

				tmpMap = new ConcurrentHashMap<String, Object>();
				service = new SetLotteryNumServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
				dao = new SetLotteryNumDaoImpl();
				service.setDao(dao);

				tmpMap.put("lotteryStatus", service.callCheckTheLottery(id, periodNum));
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

	// private boolean checkLotteryNum(int id, String data) {
	// String[] dataArr = data.split(",");
	// int maxNum = Integer.MIN_VALUE;
	// int minNum = Integer.MAX_VALUE;
	// List<Integer> numList = new ArrayList<Integer>();
	// for (int i = 0; i < dataArr.length; i++) {
	// if (!"".equals(checkNumber(dataArr[i]))) {
	// int num = Integer.parseInt(dataArr[i]);
	// numList.add(num);
	// if (maxNum < num) {
	// maxNum = num;
	// }
	// if (minNum > num) {
	// minNum = num;
	// }
	// }
	// }
	//
	// switch (id) {
	// case CQSSC:
	// case TJSSC:
	// case YNASSC:
	// case XJSSC:
	// case QQFFC:
	// case TXFFC:
	// case PL5:
	// if (maxNum > 9 || minNum < 0) {
	// return false;
	// }
	// if (numList.size() == 5) {
	// return true;
	// }
	// return false;
	// case GD11X5:
	// case JX11X5:
	// case SH11X5:
	// case SD11X5:
	// case JS11X5:
	// case LN11X5:
	// case GX11X5:
	// case AF11X5:
	// case HLJ11X5:
	// case YN11X5:
	// if (maxNum > 11 || minNum < 1) {
	// return false;
	// }
	// if (numList.size() == 5) {
	// return true;
	// }
	// return false;
	// case HBK3:
	// case HLK3:
	// case GXK3:
	// case JSK3:
	// case JLK3:
	// case AHK3:
	// if (maxNum > 6 || minNum < 1) {
	// return false;
	// }
	// if (numList.size() == 3) {
	// return true;
	// }
	// return false;
	// case FC3D:
	// if (maxNum > 9 || minNum < 0) {
	// return false;
	// }
	// if (numList.size() == 3) {
	// return true;
	// }
	// return false;
	// case BJPK10:
	// if (maxNum > 10 || minNum < 1) {
	// return false;
	// }
	// if (numList.size() == 10) {
	// return true;
	// }
	// return false;
	// }
	//
	// return false;
	// }

	public void getLotteryNumSetLog(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> tmpMap = null;
		SetLotteryNumServiceImpl service = null;
		SetLotteryNumDaoImpl dao = null;
		try {
			if (accIdToLong("" + req.getSession().getAttribute("accId")) != 0) {
				String startTime = req.getParameter("startTime") != null ? req.getParameter("startTime").toString() : "";
				String endTime = req.getParameter("endTime") != null ? req.getParameter("endTime").toString() : "";
				int logAction = req.getParameter("logAction") != null ? !"".equals(clearNum(req.getParameter("logAction").toString()))
						? Integer.parseInt(clearNum(req.getParameter("logAction").toString()))
						: 0 : 0;
				tmpMap = new ConcurrentHashMap<String, Object>();
				service = new SetLotteryNumServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
				dao = new SetLotteryNumDaoImpl();
				service.setDao(dao);

				tmpMap.put("lotteryNumSetLog", service.getLotteryNumSetLog(logAction, startTime, endTime));

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

	public void recoverPeriodNumWinningMoney(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> tmpMap = null;
		SetLotteryNumServiceImpl service = null;
		SetLotteryNumDaoImpl dao = null;
		try {
			if (req.getParameter("lotteryId") != null && !"".equals(req.getParameter("lotteryId").toString()) && req.getParameter("periodNum") != null
					&& !"".equals(req.getParameter("periodNum").toString())) {

				long periodNum = Long.parseLong(req.getParameter("periodNum").toString());
				int id = Integer.parseInt(req.getParameter("lotteryId").toString());

				tmpMap = new ConcurrentHashMap<String, Object>();
				service = new SetLotteryNumServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
				dao = new SetLotteryNumDaoImpl();
				service.setDao(dao);

				tmpMap.put("lotteryStatus", service.recoverPeriodNumWinningMoney(id, periodNum));
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

	public void renewCallCheckTheLottery(HttpServletRequest req, HttpServletResponse res) {
		Map<String, Object> tmpMap = null;
		SetLotteryNumServiceImpl service = null;
		SetLotteryNumDaoImpl dao = null;
		try {
			if (req.getParameter("lotteryId") != null && !"".equals(req.getParameter("lotteryId").toString()) && req.getParameter("periodNum") != null
					&& !"".equals(req.getParameter("periodNum").toString())) {

				long periodNum = Long.parseLong(req.getParameter("periodNum").toString());
				int id = Integer.parseInt(req.getParameter("lotteryId").toString());

				tmpMap = new ConcurrentHashMap<String, Object>();
				service = new SetLotteryNumServiceImpl(accIdToLong("" + req.getSession().getAttribute("accId")), getIpAddr(req));
				dao = new SetLotteryNumDaoImpl();
				service.setDao(dao);

				tmpMap.put("lotteryStatus", service.renewCallCheckTheLottery(id, periodNum));
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
