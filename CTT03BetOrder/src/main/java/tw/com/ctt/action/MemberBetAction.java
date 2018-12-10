package tw.com.ctt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.dao.impl.MemberBetDaoImpl;
import tw.com.ctt.dao.impl.OrderInfoOfBettingDaoImpl;
import tw.com.ctt.service.impl.MemberBetServiceImpl;
import tw.com.ctt.service.impl.OrderInfoOfBettingServiceImpl;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "MemberBet", urlPatterns = { "/MemberBet" })
/**
 * <h1>MemberBetAction</h1> This action major to cancle orders.
 *
 * @author Quanto Lin
 * @version 1.0
 * @since 2018-08-30
 */
public class MemberBetAction extends BaseAction {

	private static final long serialVersionUID = -5390951620012326545L;
	private static final Logger LOG = LogManager.getLogger(MemberBetAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = { "/MemberBet", "/MemberBet!cancleOrder", "/MemberBet!cancleMainOrder", "/MemberBet!cancleOrderByPeriod",
			"/MemberBet!cancleBetOrder" };
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = {};
	private String[] authURL = {};

	public MemberBetAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		LOG.info("MemberBetAction start");
	}

	public boolean checkLogin() {
		return true;
	}

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
	 * to cancle one order
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	public void cancleOrder(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("cancleOrder");
		checkGET(req, res);
		Map<String, Object> tmpMap = null;
		String tokenId = null;
		long accId = 0;
		MemberBetServiceImpl service = null;
		MemberBetDaoImpl dao = null;
		boolean todo = true;
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

	/**
	 * to cancle orders by main_order_id.
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	public void cancleMainOrder(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("cancleMainOrder");
		checkGET(req, res);
		Map<String, Object> tmpMap = null;
		String tokenId = null;
		long accId = 0;
		MemberBetServiceImpl service = null;
		MemberBetDaoImpl dao = null;
		boolean todo = true;
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

	/**
	 * cancle order by period (player doesnt need this function, just for manager.)
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	public void cancleOrderByPeriod(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("cancleOrderByPeriod");
		Map<String, Object> tmpMap = null;
		String tokenId = null;
		long accId = 0;
		long periodNum = 0;
		int localId = 0;
		MemberBetServiceImpl service = null;
		MemberBetDaoImpl dao = null;
		boolean todo = true;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			if (req.getParameter("tokenId") != null && req.getParameter("accId") != null) {
				tokenId = req.getParameter("tokenId").toString();
				accId = accIdToLong(req.getParameter("accId").toString());
				periodNum = accIdToLong(req.getParameter("periodNum").toString());
				localId = Integer.parseInt(req.getParameter("localId").toString());

			} else if (req.getAttribute("tokenId") != null && req.getAttribute("accId") != null) {
				tokenId = req.getAttribute("tokenId").toString();
				accId = accIdToLong(req.getAttribute("accId").toString());
				periodNum = accIdToLong(req.getAttribute("periodNum").toString());
				localId = Integer.parseInt(req.getAttribute("localId").toString());

			} else {
				tmpMap.put("tokenId", "fail");
			}
			if (tokenId != null && accId != 0 && periodNum != 0 && localId != 0) {
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

	public void cancleBetOrder(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.debug("cancleBetOrder");
		Map<String, Object> tmpMap = null;
		long accId;

		MemberBetServiceImpl service = null;
		MemberBetDaoImpl dao = null;
		int type = 0;
		boolean todo = true;

		try {
			tmpMap = new ConcurrentHashMap<String, Object>();
			service = new MemberBetServiceImpl();
			dao = new MemberBetDaoImpl();
			service.setDao(dao);

			accId = req.getParameter("accId") != null ? Integer.parseInt(req.getParameter("accId").toString()) : 0;
			type = req.getParameter("type") != null ? Integer.parseInt(req.getParameter("type").toString()) : 0;

			if (type == 3) {// mid
				JSONObject jsonObj = new JSONObject(req.getParameter("data").toString() == null ? "" : req.getParameter("data").toString());
				if (jsonObj.has("orders")) {
					JSONArray ordersArray = jsonObj.getJSONArray("orders");
					for (int j = 0; j < ordersArray.length(); j++) {
						if (Long.parseLong(ordersArray.getJSONObject(j).get("memberAccId").toString()) == accId) {
							String dateOfTable = getDateOfTableForCancleOrder(CommandConstant.KJ_TIME_INFO, ordersArray.getJSONObject(j));
							todo &= (todo ? !"".equals(dateOfTable) : false);
							todo &= (todo ? checkCancleMidOrderStatus(ordersArray.getJSONObject(j), dateOfTable) : false);
							todo &= (todo ? service.cancleMidOrder(accId, ordersArray.getJSONObject(j)) : false);
						}
					}
				}
				tmpMap.put("type", 3);
			} else if (type == 4) {// main
				JSONObject jsonObj = new JSONObject(req.getParameter("data").toString() == null ? "" : req.getParameter("data").toString());
				if (jsonObj.has("orders")) {
					JSONArray ordersArray = jsonObj.getJSONArray("orders");
					for (int j = 0; j < ordersArray.length(); j++) {
						if (Long.parseLong(ordersArray.getJSONObject(j).get("memberAccId").toString()) == accId) {
							String dateOfTable = getDateOfTableForCancleOrder(CommandConstant.KJ_TIME_INFO, ordersArray.getJSONObject(j));
							todo &= (todo ? !"".equals(dateOfTable) : false);
							todo &= (todo ? checkCancleMainOrderStatus(ordersArray.getJSONObject(j), dateOfTable) : false);
							todo &= (todo ? service.cancleMainOrder(accId, ordersArray.getJSONObject(j)) : false);
						}
					}
				}
				tmpMap.put("type", 4);
			}
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			tmpMap.put("searchTime", sdFormat.format(new Date()));
			tmpMap.put("tokenId", "success");
			LOG.debug(tmpMap);
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
		if (orderObject.length() > 0) {
			JSONObject eachMainOrder = (JSONObject) orderObject;
			String tmpPeriod = eachMainOrder.getString("periodNum");
			String localId = eachMainOrder.getString("localId");
			return getDateOfTableByPeriodAndLocalId(mapOfKjInfo, tmpPeriod, localId);
		}
		return "";
	}

	@SuppressWarnings("rawtypes")
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
			JSONObject eachMainOrder = (JSONObject) orderObject;
			long mainOrderId = eachMainOrder.getLong("mainOrderId");
			int localId = eachMainOrder.getInt("localId");
			long periodNum = eachMainOrder.getLong("periodNum");
			if (!serviceOrderInfo.checkMainOrderStatusIsNormal(periodNum, localId, mainOrderId, dateOfTable)) {
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
			JSONObject eachMainOrder = (JSONObject) orderObject;
			int localId = eachMainOrder.getInt("localId");
			long periodNum = eachMainOrder.getLong("periodNum");
			long mainOrderId = eachMainOrder.getLong("mainOrderId");
			if (!serviceOrderInfo.checkMidOrderStatusIsNormal(periodNum, localId, mainOrderId, dateOfTable)) {
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

	@SuppressWarnings("rawtypes")
	private String getDateOfTable(Map<String, Object> mapOfKjInfo, JSONObject orderObject) {
		JSONArray mainOrders = orderObject.getJSONArray("orders");
		if (mainOrders.length() > 0) {
			JSONObject eachMainOrder = (JSONObject) mainOrders.get(0);
			String tmpPeriod = eachMainOrder.getString("startPeriodNum");
			String localId = eachMainOrder.getString("localId");
			if (mapOfKjInfo.containsKey(localId)) {
				if (((Map) mapOfKjInfo.get(localId)).containsKey(tmpPeriod)) {
					return "" + ((Map) ((Map) mapOfKjInfo.get(localId)).get(tmpPeriod)).get("period_date");
				}
			}
		}
		return "";
	}

}
