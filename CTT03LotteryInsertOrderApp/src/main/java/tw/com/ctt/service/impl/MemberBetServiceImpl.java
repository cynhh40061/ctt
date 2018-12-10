package tw.com.ctt.service.impl;

import java.io.FileWriter;
import java.math.BigDecimal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.dao.IMemberBetDao;
import tw.com.ctt.dao.IOrderInfoOfBettingDao;
import tw.com.ctt.model.MemBean;
import tw.com.ctt.service.IMemberBetService;
import tw.com.ctt.util.CommandUtil;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class MemberBetServiceImpl extends BaseService implements IMemberBetService {
	/**
	 * 
	 */

	private List<Map<String, Object>> subOrderListWaitWrite = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> midOrderListWaitWrite = new ArrayList<Map<String, Object>>();

	private static final long serialVersionUID = 7588251442765528152L;
	private static final Logger LOG = LogManager.getLogger("MemberBetServiceImpl");
	private static long USERID;
	private static String USERIP;

	public MemberBetServiceImpl() {
		this.USERID = 0;
		this.USERIP = "";

		subOrderListWaitWrite = new ArrayList<Map<String, Object>>();
		midOrderListWaitWrite = new ArrayList<Map<String, Object>>();
	}

	public MemberBetServiceImpl(long userId, String userIp) {
		this.USERID = userId;
		this.USERIP = userIp;

		subOrderListWaitWrite = new ArrayList<Map<String, Object>>();
		midOrderListWaitWrite = new ArrayList<Map<String, Object>>();
	}

	public MemberBetServiceImpl(String userIp) {
		this.USERIP = userIp;

		subOrderListWaitWrite = new ArrayList<Map<String, Object>>();
		midOrderListWaitWrite = new ArrayList<Map<String, Object>>();
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
	public boolean checkRealTimeBaselinePeriodAndHandiCap(long accId, long periodNum, long minAuthId, int localId,
			String dateOfTable, int handiCap, Map<String, Map<String, String>> tmpRatios) {
		return ((IMemberBetDao) dao).checkRealTimeBaselinePeriodAndHandiCap(accId, periodNum, minAuthId, localId,
				dateOfTable, handiCap, tmpRatios);
	}

	@Override
	public boolean addMainOrder(List<String> cmdListFromRedis) {
		int orderStatus = 1;
		boolean todo = true;
		long orderId = 0;
		try {
			List<Map<String, Object>> subOrderListWaitWriteInner = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> midOrderListWaitWriteInner = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> mainOrderListWaitWriteInner = new ArrayList<Map<String, Object>>();

			for (int x = 0; x < cmdListFromRedis.size(); x++) {
				String cmdFromRedis = cmdListFromRedis.get(x);
				if (!"".equals(cmdFromRedis) && cmdFromRedis != null) {
					JSONObject cmdJson = new JSONObject(cmdFromRedis);
					JSONObject orderObject = cmdJson.getJSONObject("data");
					String tokenId = cmdJson.getString("tokenId");
					String dateOfTable = cmdJson.getString("dateOfTable");
					long accId = cmdJson.getLong("accId");

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
						String mainTable = "main_order_" + dateOfTable;
						orderId = ((IMemberBetDao) dao).getNextAutoIncrementId(mainTable, "ctt_manager");
						if (orderId > 0) {
							Map<String, Object> insertMapForMain = new ConcurrentHashMap<String, Object>();
							int countOfMidOrders = 0;
							int countOfBetOrders = 0;
							insertMapForMain.put("main_order_id", orderId);
							insertMapForMain.put("acc_id", accId);
							insertMapForMain.put("start_period_num", startPeriodNum);
							insertMapForMain.put("stop_period_num", stopPeriodNum);
							insertMapForMain.put("bet_data", betData);
							insertMapForMain.put("no_of_period", noOfPeriod);
							insertMapForMain.put("game_id", localId);
							insertMapForMain.put("played_id", minAuthId);
							insertMapForMain.put("action_time", actionTime);
							insertMapForMain.put("amount", amount);
							insertMapForMain.put("money_unit", moneyUnit);
							insertMapForMain.put("order_type", orderType);
							insertMapForMain.put("order_status", orderStatus);
							insertMapForMain.put("bonus_set_ratio", bonusSetRatio);
							insertMapForMain.put("handi_cap", handiCap);
							insertMapForMain.put("bet_ratio", betRatio);
							insertMapForMain.put("is_dt", isDt);
							insertMapForMain.put("dateOfTable", dateOfTable);
							JSONArray midOrders = eachMainOrder.getJSONArray("midOrders");
							todo = true;
							
							countOfMidOrders += midOrders.length();
							for (int j = 0; j < midOrders.length(); j++) {
								JSONObject eachMidOrder = (JSONObject) midOrders.get(j);
								long periodNum = eachMidOrder.getLong("periodNum");
								long noOfBet = eachMidOrder.getLong("noOfBet");
								long noOfBetTimes = eachMidOrder.getLong("noOfBetTimes");
								BigDecimal amountMidOrder = eachMidOrder.getBigDecimal("amount");
								BigDecimal maxBonus = eachMidOrder.getBigDecimal("maxBonus");
								BigDecimal fanDenMidOrder = eachMidOrder.getBigDecimal("fanDen");
								Map<String, Object> insertMapForMid = new ConcurrentHashMap<String, Object>();
								insertMapForMid.put("main_order_id", orderId);
								insertMapForMid.put("mid_order_id", j);
								insertMapForMid.put("acc_id", accId);
								insertMapForMid.put("period_num", periodNum);
								insertMapForMid.put("game_id", localId);
								insertMapForMid.put("played_id", minAuthId);
								insertMapForMid.put("amount", amountMidOrder);
								insertMapForMid.put("no_of_bet", noOfBet);
								insertMapForMid.put("max_bonus", maxBonus);
								insertMapForMid.put("fan_den", fanDenMidOrder);
								insertMapForMid.put("no_of_bet_times", noOfBetTimes);
								insertMapForMid.put("order_status", orderStatus);
								insertMapForMid.put("dateOfTable", dateOfTable);
								midOrderListWaitWriteInner.add(insertMapForMid);
								JSONArray betOrders = eachMidOrder.getJSONArray("betOrders");
								
								countOfBetOrders += betOrders.length();
								for (int k = 0; k < betOrders.length(); k++) {
									JSONObject eachBetOrder = (JSONObject) betOrders.get(k);
									long playedIdSubOrder = eachBetOrder.getLong("playedId");
									long noOfBetSubOrder = eachBetOrder.getLong("noOfBet");
									String subBetData = eachBetOrder.getString("betData");
									BigDecimal amountSubOrder = eachBetOrder.getBigDecimal("amount");
									BigDecimal baseline = eachBetOrder.getBigDecimal("baseline");
									BigDecimal bonus = eachBetOrder.getBigDecimal("bonus");
									int subAction = 0;
									if (eachBetOrder.has("subAction")) {
										subAction = eachBetOrder.getInt("subAction");
									}
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
									insertMap.put("sub_action", subAction);
									insertMap.put("dateOfTable", dateOfTable);
									subOrderListWaitWriteInner.add(insertMap);
								}
							}
							insertMapForMain.put("countOfMidOrders", countOfMidOrders);
							insertMapForMain.put("countOfBetOrders", countOfBetOrders);
							mainOrderListWaitWriteInner.add(insertMapForMain);
						}
					}
				}
			}
			if (todo && !mainOrderListWaitWriteInner.isEmpty()) {
				todo &= ((IMemberBetDao) dao).insertMainOrder(mainOrderListWaitWriteInner);
			}
			if (todo && !midOrderListWaitWriteInner.isEmpty()) {
				todo &= ((IMemberBetDao) dao).insertMidOrder(midOrderListWaitWriteInner);
			}
			if (todo && !subOrderListWaitWriteInner.isEmpty()) {
				todo &= ((IMemberBetDao) dao).insertBetOrder(subOrderListWaitWriteInner);
			}
			if (todo && !mainOrderListWaitWriteInner.isEmpty()) {
				((IMemberBetDao) dao).updateUserBalanceOfMainOrderBetting(mainOrderListWaitWriteInner);
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			orderId = 0;
		}
		return todo;
	}
	
	@Override
	public boolean addMainOrder_ExportFile(List<String> cmdListFromRedis, FileWriter fw) {
		int orderStatus = 1;
		boolean todo = true;
		long orderId = 0;
		try {
			List<Map<String, Object>> subOrderListWaitWriteInner = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> midOrderListWaitWriteInner = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> mainOrderListWaitWriteInner = new ArrayList<Map<String, Object>>();

			for (int x = 0; x < cmdListFromRedis.size(); x++) {
				String cmdFromRedis = cmdListFromRedis.get(x);
				if (!"".equals(cmdFromRedis) && cmdFromRedis != null) {
					JSONObject cmdJson = new JSONObject(cmdFromRedis);
					JSONObject orderObject = cmdJson.getJSONObject("data");
					String tokenId = cmdJson.getString("tokenId");
					String dateOfTable = cmdJson.getString("dateOfTable");
					long accId = cmdJson.getLong("accId");

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
						String mainTable = "main_order_" + dateOfTable;
						orderId = ((IMemberBetDao) dao).getNextAutoIncrementId(mainTable, "ctt_manager");
						if (orderId > 0) {
							Map<String, Object> insertMapForMain = new ConcurrentHashMap<String, Object>();
							int countOfMidOrders = 0;
							int countOfBetOrders = 0;
							insertMapForMain.put("main_order_id", orderId);
							insertMapForMain.put("acc_id", accId);
							insertMapForMain.put("start_period_num", startPeriodNum);
							insertMapForMain.put("stop_period_num", stopPeriodNum);
							insertMapForMain.put("bet_data", betData);
							insertMapForMain.put("no_of_period", noOfPeriod);
							insertMapForMain.put("game_id", localId);
							insertMapForMain.put("played_id", minAuthId);
							insertMapForMain.put("action_time", actionTime);
							insertMapForMain.put("amount", amount);
							insertMapForMain.put("money_unit", moneyUnit);
							insertMapForMain.put("order_type", orderType);
							insertMapForMain.put("order_status", orderStatus);
							insertMapForMain.put("bonus_set_ratio", bonusSetRatio);
							insertMapForMain.put("handi_cap", handiCap);
							insertMapForMain.put("bet_ratio", betRatio);
							insertMapForMain.put("is_dt", isDt);
							insertMapForMain.put("dateOfTable", dateOfTable);
							JSONArray midOrders = eachMainOrder.getJSONArray("midOrders");
							todo = true;
							
							countOfMidOrders += midOrders.length();
							for (int j = 0; j < midOrders.length(); j++) {
								JSONObject eachMidOrder = (JSONObject) midOrders.get(j);
								long periodNum = eachMidOrder.getLong("periodNum");
								long noOfBet = eachMidOrder.getLong("noOfBet");
								long noOfBetTimes = eachMidOrder.getLong("noOfBetTimes");
								BigDecimal amountMidOrder = eachMidOrder.getBigDecimal("amount");
								BigDecimal maxBonus = eachMidOrder.getBigDecimal("maxBonus");
								BigDecimal fanDenMidOrder = eachMidOrder.getBigDecimal("fanDen");
								Map<String, Object> insertMapForMid = new ConcurrentHashMap<String, Object>();
								insertMapForMid.put("main_order_id", orderId);
								insertMapForMid.put("mid_order_id", j);
								insertMapForMid.put("acc_id", accId);
								insertMapForMid.put("period_num", periodNum);
								insertMapForMid.put("game_id", localId);
								insertMapForMid.put("played_id", minAuthId);
								insertMapForMid.put("amount", amountMidOrder);
								insertMapForMid.put("no_of_bet", noOfBet);
								insertMapForMid.put("max_bonus", maxBonus);
								insertMapForMid.put("fan_den", fanDenMidOrder);
								insertMapForMid.put("no_of_bet_times", noOfBetTimes);
								insertMapForMid.put("order_status", orderStatus);
								insertMapForMid.put("dateOfTable", dateOfTable);
								midOrderListWaitWriteInner.add(insertMapForMid);
								JSONArray betOrders = eachMidOrder.getJSONArray("betOrders");
								
								countOfBetOrders += betOrders.length();
								for (int k = 0; k < betOrders.length(); k++) {
									JSONObject eachBetOrder = (JSONObject) betOrders.get(k);
									long playedIdSubOrder = eachBetOrder.getLong("playedId");
									long noOfBetSubOrder = eachBetOrder.getLong("noOfBet");
									String subBetData = eachBetOrder.getString("betData");
									BigDecimal amountSubOrder = eachBetOrder.getBigDecimal("amount");
									BigDecimal baseline = eachBetOrder.getBigDecimal("baseline");
									BigDecimal bonus = eachBetOrder.getBigDecimal("bonus");
									int subAction = 0;
									if (eachBetOrder.has("subAction")) {
										subAction = eachBetOrder.getInt("subAction");
									}
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
									insertMap.put("sub_action", subAction);
									insertMap.put("dateOfTable", dateOfTable);
									subOrderListWaitWriteInner.add(insertMap);
								}
							}
							insertMapForMain.put("countOfMidOrders", countOfMidOrders);
							insertMapForMain.put("countOfBetOrders", countOfBetOrders);
							mainOrderListWaitWriteInner.add(insertMapForMain);
						}
					}
				}
			}
			if (todo && !mainOrderListWaitWriteInner.isEmpty()) {
				todo &= ((IMemberBetDao) dao).insertMainOrder_ExportFile(mainOrderListWaitWriteInner, fw);
			}
			if (todo && !midOrderListWaitWriteInner.isEmpty()) {
				todo &= ((IMemberBetDao) dao).insertMidOrder_ExportFile(midOrderListWaitWriteInner, fw);
			}
			if (todo && !subOrderListWaitWriteInner.isEmpty()) {
				todo &= ((IMemberBetDao) dao).insertBetOrder_ExportFile(subOrderListWaitWriteInner, fw);
			}
			if (todo && !mainOrderListWaitWriteInner.isEmpty()) {
				((IMemberBetDao) dao).updateUserBalanceOfMainOrderBetting_ExportFile(mainOrderListWaitWriteInner, fw);
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			orderId = 0;
		}
		return todo;
	}
	
	@Override
	public boolean cancleAllPeriodOrder(long accId, long periodNum, int localId) {
		return ((IMemberBetDao) dao).cancleAllPeriodOrder(accId, periodNum, localId);
	}

	@Override
	public boolean cancleMainOrder(long accId, JSONObject orderObject) {
		Map<String, Object> logDataMap = null;
		try {
			if (Long.parseLong(orderObject.get("memberAccId").toString()) == accId) {
				JSONObject eachMainOrder = (JSONObject) orderObject;
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
			if (Long.parseLong(orderObject.get("memberAccId").toString()) == accId) {
				JSONObject eachMainOrder = (JSONObject) orderObject;
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

	@Override
	public int addMainOrderBetRatio(List<String> cmdListFromRedis) {
		try {
			for (int x = 0; x < cmdListFromRedis.size(); x++) {
				String cmdFromRedis = cmdListFromRedis.get(x);
				if (!"".equals(cmdFromRedis) && cmdFromRedis != null) {
					JSONObject cmdJson = new JSONObject(cmdFromRedis);
					JSONObject orderObject = cmdJson.getJSONObject("data");
					JSONArray mainOrders = orderObject.getJSONArray("orders");
					String dateOfTable = cmdJson.getString("dateOfTable");

					for (int i = 0; i < mainOrders.length(); i++) {
						JSONObject eachMainOrder = (JSONObject) mainOrders.get(i);
						BigDecimal amount = eachMainOrder.getBigDecimal("amount");
						int localId = eachMainOrder.getInt("localId");
						int mainId = eachMainOrder.getInt("mainId");
						int midId = eachMainOrder.getInt("midId");
						long minAuthId = eachMainOrder.getLong("minAuthId");
						long startPeriodNum = eachMainOrder.getLong("startPeriodNum");
						int noOfPeriod = eachMainOrder.getInt("noOfPeriod");
						if("1".equals(eachMainOrder.getString("lotteryLowfreq"))) {
							((IMemberBetDao) dao).updateBetRatioLF(amount, localId, mainId, midId, minAuthId,
									startPeriodNum,dateOfTable, noOfPeriod);
						}else {
							((IMemberBetDao) dao).updateBetRatio(amount, localId, mainId, midId, minAuthId,
									startPeriodNum,dateOfTable);
						}
						
					}
				}
			}
			if (((IMemberBetDao) dao).checkCurrentBetRatio()) {
				return 2;
			}
			return 1;
		} catch (Exception e) {
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
			return 0;
		} finally {
		}
	}
	
	@Override
	public boolean addMainOrderBetRatio_ExportFile(List<String> cmdListFromRedis, FileWriter fw) {
		List<Map<String, Object>> baselineUpdateListWaitWriteInner = new ArrayList<Map<String, Object>>();
		try {
			for (int x = 0; x < cmdListFromRedis.size(); x++) {
				String cmdFromRedis = cmdListFromRedis.get(x);
				if (!"".equals(cmdFromRedis) && cmdFromRedis != null) {
					JSONObject cmdJson = new JSONObject(cmdFromRedis);
					JSONObject orderObject = cmdJson.getJSONObject("data");
					JSONArray mainOrders = orderObject.getJSONArray("orders");
					String dateOfTable = cmdJson.getString("dateOfTable");
					for (int i = 0; i < mainOrders.length(); i++) {
						JSONObject eachMainOrder = (JSONObject) mainOrders.get(i);
						BigDecimal amount = eachMainOrder.getBigDecimal("amount");
						int localId = eachMainOrder.getInt("localId");
						int mainId = eachMainOrder.getInt("mainId");
						int midId = eachMainOrder.getInt("midId");
						long minAuthId = eachMainOrder.getLong("minAuthId");
						long startPeriodNum = eachMainOrder.getLong("startPeriodNum");

						Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
						insertMap.put("amount", amount);
						insertMap.put("localId", localId);
						insertMap.put("mainId", mainId);
						insertMap.put("midId", midId);
						insertMap.put("minAuthId", minAuthId);
						insertMap.put("startPeriodNum", startPeriodNum);
						insertMap.put("dateOfTable", dateOfTable);
						baselineUpdateListWaitWriteInner.add(insertMap);
					}
				}
			}
			if(!baselineUpdateListWaitWriteInner.isEmpty()) {				
				return ((IMemberBetDao) dao).updateBetRatio_ExportFile(baselineUpdateListWaitWriteInner, fw);
			}			
			return false;
		} catch (Exception e) {
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		} finally {
		}
	}

}
