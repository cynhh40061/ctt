package tw.com.ctt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.dao.impl.KjInfoDaoImpl;
import tw.com.ctt.dao.impl.LotteryBetOrderQueryDaoImpl;
import tw.com.ctt.dao.impl.MemberBetDaoImpl;
import tw.com.ctt.dao.impl.OrderInfoOfBettingDaoImpl;
import tw.com.ctt.service.impl.KjInfoServiceImpl;
import tw.com.ctt.service.impl.LotteryBetOrderQueryServiceImpl;
import tw.com.ctt.service.impl.MemberBetServiceImpl;
import tw.com.ctt.service.impl.OrderInfoOfBettingServiceImpl;
//import tw.com.ctt.dao.impl.BetOrderDaoImpl;
//import tw.com.ctt.service.impl.BetOrderServiceImpl;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "LotteryBetOrderQuery", urlPatterns = { "/LotteryBetOrderQuery" })
public class LotteryBetOrderQueryAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7857623446545033360L;
	private static final Logger LOG = LogManager.getLogger(LotteryBetOrderQueryAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = {};
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = { "/LotteryBetOrderQuery!queryOrders", "/LotteryBetOrderQuery!getLotteryTypeList",
			"/LotteryBetOrderQuery!getLotteryLocalList", "/LotteryBetOrderQuery!updateKjTimeInfo", "/LotteryBetOrderQuery!cancleMidOrder",
			"/LotteryBetOrderQuery!cancleMainOrder", "/LotteryBetOrderQuery!cancleOrderByPeriod" };
	private String[] authURL = { "/LotteryBetOrderQuery!mainLotteryBetOrderQuery" };

	public LotteryBetOrderQueryAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		loadCheckDataFromDB();
		LOG.info("LotteryBetOrderQueryAction start");
	}

	public void mainLotteryBetOrderQuery(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOG.debug("LotteryBetOrderQueryAction.main start");
		Map<String, String> tmpMap = null;
		StringBuilder sb = null;
		JSONObject responseJSONObject = null;
		PrintWriter out = null;
		try {
			// initial blackWhite.js
			tmpMap = new ConcurrentHashMap<String, String>();
			sb = new StringBuilder();
			sb.append("function f_OQ_checkLotteryBetOrderQuery(){\n");
			sb.append("    if(typeof LotteryBetOrderQuery === 'undefined' || typeof LotteryBetOrderQuery != 'function' ){\n");
			sb.append("        checkJS('LotteryBetOrderQuery', 'AccountManage/LotteryBetOrderQuery.js');\n");
			sb.append("    } else {\n");
			sb.append("        LotteryBetOrderQuery();\n");
			sb.append("    }\n");
			sb.append("}\n");
			sb.append("f_OQ_checkLotteryBetOrderQuery();\n");
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
			LOG.error("LotteryBetOrderQuery.main.Exception, " + e.getMessage());
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
		LotteryBetOrderQueryServiceImpl service = null;
		LotteryBetOrderQueryDaoImpl dao = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new LotteryBetOrderQueryServiceImpl();
			dao = new LotteryBetOrderQueryDaoImpl();
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

	public void queryOrders(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("queryOrders");
		Map<String, Object> tmpMap = null;
		long accId;
		LotteryBetOrderQueryServiceImpl service = null;
		LotteryBetOrderQueryDaoImpl dao = null;
		int type = 0;
		String[] midKey = { "midOrderId", "midAccName", "midlotteryLocal", "midPeriodNum", "midStartTime", "midEndTime" };
		String[] mainKey = { "mainOrderId", "mainAccName", "mainlotteryLocal", "lotteryStatus", "mainStartTime", "mainEndTime" };
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
			service = new LotteryBetOrderQueryServiceImpl();
			dao = new LotteryBetOrderQueryDaoImpl();
			service.setDao(dao);

			page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page").toString()) : 1;
			nextPage = req.getParameter("nextPage") != null ? Integer.parseInt(req.getParameter("nextPage").toString()) : 1;
			count = req.getParameter("count") != null ? Integer.parseInt(req.getParameter("count").toString()) : 25;

			type = req.getParameter("type") != null ? Integer.parseInt(req.getParameter("type").toString()) : 0;
			map = new ConcurrentHashMap<String, Object>();

			if (type == 1) {// mid
				for (int i = 0; i < midKey.length; i++) {
					map.put(midKey[i], req.getParameter(midKey[i]) != null ? req.getParameter(midKey[i]).toString() : "");
				}
				totalCount = service.getMidTotleCount(accId, map);
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
				tmpMap.put("midLastPage", totalPage);
				tmpMap.put("midPage", nextPage);
				if (map.size() > 0) {
					tmpMap.put("BetOrderInfo", service.searchMidBetOrder(accId, map, firstCount, count));
				}
				tmpMap.put("type", 1);
			} else if (type == 2) {// main
				for (int j = 0; j < mainKey.length; j++) {
					map.put(mainKey[j], req.getParameter(mainKey[j]) != null ? req.getParameter(mainKey[j]).toString() : "");
				}
				totalCount = service.getMainTotleCount(accId, map);
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
				tmpMap.put("mainLastPage", totalPage);
				tmpMap.put("mainPage", nextPage);
				if (map.size() > 0) {
					tmpMap.put("BetOrderInfo", service.searchMainBetOrder(accId, map, firstCount, count));
				}
				tmpMap.put("type", 2);
			}
			tmpMap.put("nowDBTime", service.getNowDBTime());
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

	public void loadCheckDataFromDB() {
		if (CommandConstant.KJ_TIME_INFO == null) {
			getKjTimeInfoWhenInit();
		}
	}

	private void getKjTimeInfoWhenInit() {
		LOG.debug("getKjTimeInfoWhenInit");
		KjInfoServiceImpl service = null;
		KjInfoDaoImpl dao = null;
		try {
			service = new KjInfoServiceImpl();
			dao = new KjInfoDaoImpl();
			service.setDao(dao);
			CommandConstant.KJ_TIME_INFO = service.getAllKjTimeStatus();
			CommandConstant.CHECK_UPDATE_KJ_TIME = new Date().getTime();
			CommandConstant.DB_LAST_UPDATE_TIME_OF_KJ = service.getKjLastUpdateTime();
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

	public void cancleMidOrder(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("cancleOrder");
		Map<String, Object> tmpMap = null;
		long accId = 0;
		MemberBetServiceImpl service = null;
		MemberBetDaoImpl dao = null;
		boolean todo = true;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			JSONObject orderObject = new JSONObject(req.getParameter("data").toString());
			service = new MemberBetServiceImpl(accId, getIpAddr(req));
			dao = new MemberBetDaoImpl();
			service.setDao(dao);
			String dateOfTable = getDateOfTableForCancleOrder(CommandConstant.KJ_TIME_INFO, orderObject);
			todo &= (todo ? !"".equals(dateOfTable) : false);
			todo &= (todo ? checkCancleMidOrderStatus(orderObject, dateOfTable) : false);
			todo &= (todo ? service.cancleMidOrder(accId, orderObject) : false);
			if (todo) {
				tmpMap.put("operateStatus", "success");
			} else {
				tmpMap.put("operateStatus", "fail");
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

	public void cancleMainOrder(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("cancleMainOrder");
		Map<String, Object> tmpMap = null;
		long accId = 0;
		MemberBetServiceImpl service = null;
		MemberBetDaoImpl dao = null;
		boolean todo = true;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			JSONObject orderObject = new JSONObject(req.getParameter("data").toString());
			service = new MemberBetServiceImpl(accId, getIpAddr(req));
			dao = new MemberBetDaoImpl();
			service.setDao(dao);
			String dateOfTable = getDateOfTableForCancleOrder(CommandConstant.KJ_TIME_INFO, orderObject);
			todo &= (todo ? !"".equals(dateOfTable) : false);
			todo &= (todo ? checkCancleMainOrderStatus(orderObject, dateOfTable) : false);
			todo &= (todo ? service.cancleMainOrder(accId, orderObject) : false);
			if (todo) {
				tmpMap.put("operateStatus", "success");
			} else {
				tmpMap.put("operateStatus", "fail");
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

	public void cancleOrderByPeriod(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("cancleOrderByPeriod");
		Map<String, Object> tmpMap = null;
		long accId = 0;
		long periodNum = 0;
		int localId = 0;

		MemberBetServiceImpl service = null;
		MemberBetDaoImpl dao = null;
		boolean todo = true;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new MemberBetServiceImpl(accId, getIpAddr(req));
			dao = new MemberBetDaoImpl();
			service.setDao(dao);
			String dateOfTable = getDateOfTableByPeriodAndLocalId(CommandConstant.KJ_TIME_INFO, "" + periodNum, "" + localId);
			todo &= (todo ? !"".equals(dateOfTable) : false);
			todo &= (todo ? checkCanclePeriodStatus(localId, periodNum, dateOfTable) : false);
			todo &= (todo ? service.cancleAllPeriodOrder(accId, periodNum, localId) : false);
			if (todo) {
				tmpMap.put("operateStatus", "success");
			} else {
				tmpMap.put("operateStatus", "fail");
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

	private String getDateOfTableForCancleOrder(Map<String, Object> mapOfKjInfo, JSONObject orderObject) {
		JSONArray mainOrders = orderObject.getJSONArray("orders");
		if (mainOrders.length() > 0) {
			JSONObject eachMainOrder = (JSONObject) mainOrders.get(0);
			String tmpPeriod = eachMainOrder.getString("periodNum");
			String localId = eachMainOrder.getString("localId");
			return getDateOfTableByPeriodAndLocalId(mapOfKjInfo, tmpPeriod, localId);
		}
		return "";
	}

	private String getDateOfTableByPeriodAndLocalId(Map<String, Object> mapOfKjInfo, String tmpPeriod, String localId) {
		if (mapOfKjInfo.containsKey(localId)) {
			if (((Map) mapOfKjInfo.get(localId)).containsKey(tmpPeriod)) {
				return "" + ((Map) ((Map) mapOfKjInfo.get(localId)).get(tmpPeriod)).get("period_date");
			}
		}
		return "";
	}

	private boolean checkCancleMainOrderStatus(JSONObject orderObject, String dateOfTable) {
		OrderInfoOfBettingServiceImpl serviceOrderInfo = null;
		OrderInfoOfBettingDaoImpl daoOrderInfo = null;
		try {
			serviceOrderInfo = new OrderInfoOfBettingServiceImpl();
			daoOrderInfo = new OrderInfoOfBettingDaoImpl();
			serviceOrderInfo.setDao(daoOrderInfo);
			JSONArray mainOrders = orderObject.getJSONArray("orders");
			for (int i = 0; i < mainOrders.length(); i++) {
				JSONObject eachMainOrder = (JSONObject) mainOrders.get(i);
				long mainOrderId = eachMainOrder.getLong("mainOrderId");
				int localId = eachMainOrder.getInt("localId");
				long periodNum = eachMainOrder.getLong("periodNum");
				if (!serviceOrderInfo.checkMainOrderStatusIsNormal(periodNum, localId, mainOrderId, dateOfTable)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		} finally {
			if (daoOrderInfo != null) {
				daoOrderInfo.close();
				daoOrderInfo = null;
			}
			if (serviceOrderInfo != null) {
				serviceOrderInfo.close();
				serviceOrderInfo = null;
			}
		}
	}

	private boolean checkCancleMainOrderStatus(JSONObject orderObject, BigDecimal balance) {
		try {
			BigDecimal amountOfMainOrder = new BigDecimal(0);
			JSONArray mainOrders = orderObject.getJSONArray("orders");
			for (int i = 0; i < mainOrders.length(); i++) {
				JSONObject eachMainOrder = (JSONObject) mainOrders.get(i);
				amountOfMainOrder = amountOfMainOrder.add(eachMainOrder.getBigDecimal("amount"));
			}
			if (amountOfMainOrder.compareTo(balance) == 1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		} finally {
		}
	}

	private boolean checkCanclePeriodStatus(int localId, long periodNum, String dateOfTable) {
		OrderInfoOfBettingServiceImpl serviceOrderInfo = null;
		OrderInfoOfBettingDaoImpl daoOrderInfo = null;
		try {
			serviceOrderInfo = new OrderInfoOfBettingServiceImpl();
			daoOrderInfo = new OrderInfoOfBettingDaoImpl();
			serviceOrderInfo.setDao(daoOrderInfo);
			if (!serviceOrderInfo.checkPeriodStatusIsNormal(periodNum, localId, dateOfTable)) {
				return false;
			}
			return true;
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		} finally {
			if (daoOrderInfo != null) {
				daoOrderInfo.close();
				daoOrderInfo = null;
			}
			if (serviceOrderInfo != null) {
				serviceOrderInfo.close();
				serviceOrderInfo = null;
			}
		}
	}

	private boolean checkCancleMidOrderStatus(JSONObject orderObject, String dateOfTable) {
		OrderInfoOfBettingServiceImpl serviceOrderInfo = null;
		OrderInfoOfBettingDaoImpl daoOrderInfo = null;
		try {
			serviceOrderInfo = new OrderInfoOfBettingServiceImpl();
			daoOrderInfo = new OrderInfoOfBettingDaoImpl();
			serviceOrderInfo.setDao(daoOrderInfo);
			JSONArray mainOrders = orderObject.getJSONArray("orders");
			for (int i = 0; i < mainOrders.length(); i++) {
				JSONObject eachMainOrder = (JSONObject) mainOrders.get(i);
				int localId = eachMainOrder.getInt("localId");
				long periodNum = eachMainOrder.getLong("periodNum");
				long mainOrderId = eachMainOrder.getLong("mainOrderId");
				if (!serviceOrderInfo.checkMidOrderStatusIsNormal(periodNum, localId, mainOrderId, dateOfTable)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		} finally {
			if (daoOrderInfo != null) {
				daoOrderInfo.close();
				daoOrderInfo = null;
			}
			if (serviceOrderInfo != null) {
				serviceOrderInfo.close();
				serviceOrderInfo = null;
			}
		}
	}

}
