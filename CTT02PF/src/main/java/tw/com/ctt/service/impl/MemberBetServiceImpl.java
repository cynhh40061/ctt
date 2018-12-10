package tw.com.ctt.service.impl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IMemberBetDao;
import tw.com.ctt.service.IMemberBetService;
import tw.com.ctt.util.ShowLog;

public class MemberBetServiceImpl extends BaseService implements IMemberBetService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7588251442765528152L;
	private static final Logger LOG = LogManager.getLogger(MemberBetServiceImpl.class.getName());
	private static long USERID;
	private static String USERIP;

	public MemberBetServiceImpl() {
		this.USERID = 0;
		this.USERIP = "";
	}

	public MemberBetServiceImpl(long userId, String userIp) {
		this.USERID = userId;
		this.USERIP = userIp;
	}

	public MemberBetServiceImpl(String userIp) {
		this.USERIP = userIp;
	}

	public static String checkDateTimeFormat(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		} else {
			str = str.trim().replaceAll("-", "/");
			Pattern pattern = Pattern.compile(
					"^(?:(?!0000)[0-9]{4}/(?:(?:0[1-9]|1[0-2])/(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])/(?:29|30)|(?:0[13578]|1[02])/31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)/02/29)[ ]([01][0-9]|2[0-3]):[0-5][0-9]$");
			if (pattern.matcher(str).find()) {
				return str;
			} else {
				return "";
			}
		}
	}

	@Override
	public boolean addMainOrder(long accId, JSONObject orderObject, String dateOfTable) {
		int orderStatus = 1;
		int noOfWinningPeriod = 0;
		boolean todo = true;
		long orderId = 0;
		Map<String, Object> logDataMap = null;
		try {
			JSONArray mainOrders = orderObject.getJSONArray("orders");
			for (int i = 0; i < mainOrders.length(); i++) {
				JSONObject eachMainOrder = (JSONObject) mainOrders.get(i);
				long startPeriodNum = eachMainOrder.getLong("startPeriodNum");
				long stopPeriodNum = eachMainOrder.getLong("stopPeriodNum");
				String betData = eachMainOrder.getString("betData");
				long noOfPeriod = eachMainOrder.getLong("noOfPeriod");
				int localId = eachMainOrder.getInt("localId");
				long minAuthId = eachMainOrder.getLong("minAuthId");
				Date actionTime = new Date(eachMainOrder.getLong("actionTime"));
				BigDecimal amount = eachMainOrder.getBigDecimal("amount");
				BigDecimal moneyUnit = eachMainOrder.getBigDecimal("moneyUnit");
				int orderType = eachMainOrder.getInt("orderType");
				int handiCap = eachMainOrder.getInt("handiCap");
				int bonusSetRatio = eachMainOrder.getInt("bonusSetRatio");

				BigDecimal betRatio = eachMainOrder.getBigDecimal("betRatio");
				boolean isDt = (eachMainOrder.getInt("isDt") == 0 ? false : true);
				long totalNoOfBet = 0;
				JSONArray tmpMidOrders = eachMainOrder.getJSONArray("midOrders");
				for (int j = 0; j < tmpMidOrders.length(); j++) {
					JSONObject eachMidOrder = (JSONObject) tmpMidOrders.get(j);
					totalNoOfBet += eachMidOrder.getLong("noOfBet");
				}
				orderId = ((IMemberBetDao) dao).insertMainOrder(accId, startPeriodNum, stopPeriodNum, betData, noOfPeriod, localId, minAuthId,
						actionTime, amount, moneyUnit, orderType, handiCap, bonusSetRatio, noOfWinningPeriod, orderStatus, betRatio, isDt,
						dateOfTable, totalNoOfBet);
				if (orderId > 0) {
					LOG.debug("�l����渹          " + orderId);
					todo = true;
					JSONArray midOrders = eachMainOrder.getJSONArray("midOrders");
					for (int j = 0; j < midOrders.length(); j++) {
						JSONObject eachMidOrder = (JSONObject) midOrders.get(j);
						long periodNum = eachMidOrder.getLong("periodNum");
						long noOfBet = eachMidOrder.getLong("noOfBet");
						long noOfBetTimes = eachMidOrder.getLong("noOfBetTimes");
						BigDecimal amountMidOrder = eachMidOrder.getBigDecimal("amount");
						BigDecimal maxBonus = eachMidOrder.getBigDecimal("maxBonus");
						BigDecimal fanDenMidOrder = eachMidOrder.getBigDecimal("fanDen");
						todo &= ((IMemberBetDao) dao).insertMidOrder(orderId, j, accId, periodNum, localId, minAuthId, amountMidOrder, fanDenMidOrder,
								orderStatus, noOfBet, orderStatus, dateOfTable, maxBonus, noOfBetTimes);
						if (todo) {
							JSONArray betOrders = eachMidOrder.getJSONArray("betOrders");
							List<Map<String, Object>> subOrderList = new ArrayList<Map<String, Object>>();

							for (int k = 0; k < betOrders.length(); k++) {
								JSONObject eachBetOrder = (JSONObject) betOrders.get(k);
								long playedIdSubOrder = eachBetOrder.getLong("playedId");
								long noOfBetSubOrder = eachBetOrder.getLong("noOfBet");
								String subBetData = eachBetOrder.getString("betData");
								BigDecimal amountSubOrder = eachBetOrder.getBigDecimal("amount");
								BigDecimal baseline = eachBetOrder.getBigDecimal("baseline");
								BigDecimal bonus = eachBetOrder.getBigDecimal("bonus");

								Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
								insertMap.put("main_order_id", orderId);
								insertMap.put("mid_order_id", j);
								insertMap.put("sub_order_id", k);
								insertMap.put("acc_id", accId);
								insertMap.put("period_num", periodNum);
								insertMap.put("bet_data", subBetData);
								insertMap.put("game_id", localId);
								insertMap.put("played_id", playedIdSubOrder);
								insertMap.put("amount", amountSubOrder);
								insertMap.put("no_of_bet", noOfBetSubOrder);
								insertMap.put("order_status", orderStatus);
								insertMap.put("bonus", bonus);
								insertMap.put("baseline", baseline);
								subOrderList.add(insertMap);
							}
							todo &= ((IMemberBetDao) dao).insertBetOrder(subOrderList, dateOfTable);

						}
					}
					if (todo) {
						((IMemberBetDao) dao).writeCommit();
					} else {
						((IMemberBetDao) dao).writeRollback();
					}
				}
			}

		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			orderId = 0;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
		return todo;
	}

	@Override
	public Map<String, Object> searchRecordsTotleCount(String startTime, String endTime) {
		return ((IMemberBetDao) dao).searchRecordsTotle(USERID, startTime, endTime);
	}

	@Override
	public List<Map<String, Object>> searchRecords(String accName, String startTime, String endTime, int firstCount, int count) {
		return ((IMemberBetDao) dao).searchRecords(USERID, accName, startTime, endTime, firstCount, count);
	}

	@Override
	public boolean cancleAllPeriodOrder(long accId, long periodNum, int localId) {
		return ((IMemberBetDao) dao).cancleAllPeriodOrder(accId, periodNum, localId);
	}

	@Override
	public boolean cancleMainOrder(long accId, JSONObject orderObject) {
		Map<String, Object> logDataMap = null;
		try {
			JSONArray mainOrders = orderObject.getJSONArray("orders");
			if (mainOrders.length() == 1) {
				JSONObject eachMainOrder = (JSONObject) mainOrders.get(0);
				long mainOrderId = eachMainOrder.getLong("mainOrderId");
				long periodNum = eachMainOrder.getLong("periodNum");
				int localId = eachMainOrder.getInt("localId");
				return ((IMemberBetDao) dao).cancleMainOrder(accId, periodNum, localId, mainOrderId);
			} else {
				return false;
			}
		} catch (Exception e) {
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		} finally {
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
	}

	@Override
	public boolean cancleMidOrder(long accId, JSONObject orderObject) {
		Map<String, Object> logDataMap = null;
		try {
			JSONArray mainOrders = orderObject.getJSONArray("orders");
			if (mainOrders.length() == 1) {
				JSONObject eachMainOrder = (JSONObject) mainOrders.get(0);
				long mainOrderId = eachMainOrder.getLong("mainOrderId");
				long periodNum = eachMainOrder.getLong("periodNum");
				int localId = eachMainOrder.getInt("localId");
				return ((IMemberBetDao) dao).cancleMidOrder(accId, periodNum, localId, mainOrderId);
			} else {
				return false;
			}
		} catch (Exception e) {
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		} finally {
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
	}

	@Override
	public Map<String, Object> getMemMoney(long accId) {
		return ((IMemberBetDao) dao).getMemMoney(accId);
	}
}
