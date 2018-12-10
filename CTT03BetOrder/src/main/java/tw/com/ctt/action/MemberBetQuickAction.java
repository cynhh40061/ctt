package tw.com.ctt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import redis.clients.jedis.Jedis;
import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.dao.impl.BaselineInfoDaoImpl;
import tw.com.ctt.dao.impl.KjInfoDaoImpl;
import tw.com.ctt.dao.impl.MemberBetDaoImpl;
import tw.com.ctt.dao.impl.OrderInfoOfBettingDaoImpl;
import tw.com.ctt.service.impl.BaseService;
import tw.com.ctt.service.impl.BaselineInfoServiceImpl;
import tw.com.ctt.service.impl.KjInfoServiceImpl;
import tw.com.ctt.service.impl.MemberBetServiceImpl;
import tw.com.ctt.service.impl.OrderInfoOfBettingServiceImpl;
import tw.com.ctt.util.ShowLog;

@WebServlet(name = "QuickBet", urlPatterns = { "/QuickBet" })
/**
 * <h1>MemberBetAction</h1> This action will check all infos of order.
 *
 * @author Quanto Lin
 * @version 1.0
 * @since 2018-08-30
 */
public class MemberBetQuickAction extends BaseAction {

	private static final long serialVersionUID = -5390951620012326545L;
	private static final Logger LOG = LogManager.getLogger(MemberBetQuickAction.class.getName());

	private String[] loginURL = {};
	private String[] loginCheckURL = { "/QuickBet!bettingOrder" };
	private String[] loginCheckNoUpdateURL = {};
	private String[] extraURL = {};
	private String[] authURL = {};

	public MemberBetQuickAction() {
		super();
		super.loginURL = StringArrayAdd(super.loginURL, loginURL);
		super.loginCheckURL = StringArrayAdd(super.loginCheckURL, loginCheckURL);
		super.loginCheckNoUpdateURL = StringArrayAdd(super.loginCheckNoUpdateURL, loginCheckNoUpdateURL);
		super.extraURL = StringArrayAdd(super.extraURL, extraURL);
		super.authURL = StringArrayAdd(super.authURL, authURL);
		initURLs();
		loadCheckDataFromDB();
		if (CommandConstant.subTypeOf5xQwPos == null) {
			CommandConstant.subTypeOf5xQwPos = new ArrayList<Map<String, Object>>();
			Map<String, Object> tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 31);
			tmpMap.put("playedId", 389);
			tmpMap.put("text", "5碼趣味三星");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf5xQwPos.add(tmpMap);
			CommandConstant.subTypeOf4xQwPos = new ArrayList<Map<String, Object>>();
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 31);
			tmpMap.put("playedId", 390);
			tmpMap.put("text", "4碼趣味三星");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf4xQwPos.add(tmpMap);
			CommandConstant.subTypeOf5xQzPos = new ArrayList<Map<String, Object>>();
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 31);
			tmpMap.put("playedId", 393);
			tmpMap.put("text", "5碼區間三星");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf5xQzPos.add(tmpMap);
			CommandConstant.subTypeOf4xQzPos = new ArrayList<Map<String, Object>>();
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 31);
			tmpMap.put("playedId", 394);
			tmpMap.put("text", "4碼區間三星");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf4xQzPos.add(tmpMap);

			CommandConstant.subTypeOf5xPos = new ArrayList<Map<String, Object>>();
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 31);
			tmpMap.put("playedId", 70);
			tmpMap.put("text", "五星直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf5xPos.add(tmpMap);
			CommandConstant.subTypeOf4xPos = new ArrayList<Map<String, Object>>();
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 30);
			tmpMap.put("playedId", 60);
			tmpMap.put("text", "前四直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf4xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 29);
			tmpMap.put("playedId", 490);
			tmpMap.put("text", "萬千百個直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf4xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 27);
			tmpMap.put("playedId", 495);
			tmpMap.put("text", "萬千十個直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf4xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 23);
			tmpMap.put("playedId", 500);
			tmpMap.put("text", "萬百十個直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf4xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 15);
			tmpMap.put("playedId", 65);
			tmpMap.put("text", "後四直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf4xPos.add(tmpMap);
			CommandConstant.subTypeOf3xPos = new ArrayList<Map<String, Object>>();
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 28);
			tmpMap.put("playedId", 18);
			tmpMap.put("text", "前三直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf3xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 26);
			tmpMap.put("playedId", 441);
			tmpMap.put("text", "萬千十直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf3xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 25);
			tmpMap.put("playedId", 448);
			tmpMap.put("text", "萬千個直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf3xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 22);
			tmpMap.put("playedId", 455);
			tmpMap.put("text", "萬百十直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf3xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 21);
			tmpMap.put("playedId", 462);
			tmpMap.put("text", "萬百個直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf3xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 19);
			tmpMap.put("playedId", 469);
			tmpMap.put("text", "萬十個直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf3xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 14);
			tmpMap.put("playedId", 32);
			tmpMap.put("text", "中三直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf3xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 13);
			tmpMap.put("playedId", 476);
			tmpMap.put("text", "千百個直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf3xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 11);
			tmpMap.put("playedId", 483);
			tmpMap.put("text", "百十個直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf3xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 7);
			tmpMap.put("playedId", 46);
			tmpMap.put("text", "後三直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf3xPos.add(tmpMap);
			CommandConstant.subTypeOf2xPos = new ArrayList<Map<String, Object>>();
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 24);
			tmpMap.put("playedId", 6);
			tmpMap.put("text", "前二直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf2xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 20);
			tmpMap.put("playedId", 401);
			tmpMap.put("text", "萬百複式");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf2xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 18);
			tmpMap.put("playedId", 406);
			tmpMap.put("text", "萬十複式");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf2xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 17);
			tmpMap.put("playedId", 411);
			tmpMap.put("text", "萬個複式");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf2xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 12);
			tmpMap.put("playedId", 416);
			tmpMap.put("text", "千百複式");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf2xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 10);
			tmpMap.put("playedId", 421);
			tmpMap.put("text", "千十複式");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf2xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 9);
			tmpMap.put("playedId", 426);
			tmpMap.put("text", "千個複式");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf2xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 6);
			tmpMap.put("playedId", 431);
			tmpMap.put("text", "百十複式");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf2xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 5);
			tmpMap.put("playedId", 436);
			tmpMap.put("text", "百個複式");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf2xPos.add(tmpMap);
			tmpMap = new HashMap<String, Object>();
			tmpMap.put("posValue", 3);
			tmpMap.put("playedId", 12);
			tmpMap.put("text", "後二直選");
			tmpMap.put("noOfBet", 1);
			tmpMap.put("prizeLevel", 0);
			CommandConstant.subTypeOf2xPos.add(tmpMap);
		}

	}

	public boolean checkLogin() {
		return true;
	}

	/**
	 * check player's tokenId is valid or not.
	 * 
	 * @param service
	 * @param accId
	 * @param tokenId
	 * @return
	 */
	public Map<String, String> checkLoginMember(BaseService service, long accId, String tokenId) {
		Map<String, String> isLogin = null;
		if (accId > 0 && tokenId != null && !"".equals(tokenId)) {
			isLogin = service.checkMemberTokenTimeOut2(tokenId, accId);
		}
		return isLogin;
	}

	/**
	 * betting order will call this func. if the order is valied, it will put into
	 * redis array, and pass to next step(insert into DB)
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	public void bettingOrder(HttpServletRequest req, HttpServletResponse res) throws IOException {
		LOG.info("bettingOrder");
		Map<String, Object> tmpMap = null;
		String tokenId = null;
		String data = null;
		long accId = 0;
		MemberBetServiceImpl service = null;
		MemberBetDaoImpl dao = null;
		boolean todo = true;
		Map<String, String> accData = null;
		List<Integer> baselineIndexList = null;
		try {
			tmpMap = new ConcurrentHashMap<String, Object>();

			tokenId = getReqValue(req, "tokenId");
			if ("".equals(tokenId)) {
				tmpMap.put("tokenId", "fail");
			}

			String tmpAccId = getReqValue(req, "accId");
			if ("".equals(tmpAccId)) {
				tmpMap.put("accId", "fail");
			} else {
				accId = accIdToLong(tmpAccId);
			}

			data = getReqValue(req, "data");
			if ("".equals(data)) {
				tmpMap.put("data", "fail");
			}

			if (tokenId != null && data != null && accId != 0) {
				service = new MemberBetServiceImpl(accId, getIpAddr(req));
				dao = new MemberBetDaoImpl();
				service.setDao(dao);
				accData = checkLoginMember(service, accId, tokenId);
				tmpMap.putAll(accData);
				JSONObject orderObject = null;
				try {
					orderObject = new JSONObject(data);
				} catch (Exception e) {
					LOG.error("JSON Exception:" + e.getMessage() + " " + data.toString());
				}
				String dateOfTable = getDateOfTable(CommandConstant.KJ_TIME_INFO, orderObject);
				if (todo) {
					boolean result = !"".equals(dateOfTable);
					todo &= result;
					if (!result) {
						tmpMap.put("periodNum", "fail");
						dao.sentURL("http://127.0.0.1:8080/CTT03BetOrder/UpdateInfo!updateKjTimeInfo.php?date=" + new Date().getTime(), "", "", "");
					}
				}
				// 玩法開關
				if (todo) {
					boolean result = checkLocalIdAndMidIdAndMinIdStatus(orderObject, CommandConstant.ALL_LOTTERY_INFO);
					todo &= result;
					if (!result) {
						tmpMap.put("playedSwitch", "fail");
						dao.sentURL("http://127.0.0.1:8080/CTT03BetOrder/UpdateInfo!updateAllLotteryInfo.php?date=" + new Date().getTime(), "", "",
								"");
					}
				}
				
				Map<String, Object> realBalacne = null;
				Object[] keys = CommandConstant.CURRENT_BET_RATIO.keySet().toArray();
				String realKey = null;
				for (int i = 0; i < keys.length; i++) {
					String tmpKey = "" + keys[i];
					tmpKey = tmpKey.replaceAll("/", "");
					if (tmpKey.equals(dateOfTable)) {
						realKey = "" + keys[i];
						realBalacne = ((Map) CommandConstant.CURRENT_BET_RATIO.get(realKey));
					}
				}
				Map<String, Object> realBalacneLF = null;
				keys = CommandConstant.CURRENT_BET_RATIO_LF.keySet().toArray();
				String realKeyLF = null;
				for (int i = 0; i < keys.length; i++) {
					String tmpKey = "" + keys[i];
					tmpKey = tmpKey.replaceAll("/", "");
					if (tmpKey.equals(dateOfTable)) {
						realKeyLF = "" + keys[i];
						realBalacneLF = ((Map) CommandConstant.CURRENT_BET_RATIO_LF.get(realKeyLF));
					}
				}
				// LF Ratio 賠率 
				if (todo) {
					boolean result = checkIsNoBaselineIndexRatio(orderObject, realBalacne, realBalacneLF);
					todo &= result;
					if (!result) {
						tmpMap.put("realLFTimeInfo", "fail");
						dao.sentURL("http://127.0.0.1:8080/CTT03BetOrder/UpdateInfo!updateBaseline.php?date=" + new Date().getTime(), "", "", "");
					}
				}

				// 填補缺少注單 ex. 五星複式, 四星複式, 任三複式, 任二複式
				boolean needCheckMoney = true;
				if (todo) {
					try {
						int result = addSubOrdersForFs(orderObject, CommandConstant.ALL_HANDICAP_INFO, realBalacne, realBalacneLF,
								CommandConstant.ALL_LOTTERY_INFO, dateOfTable);
						if (result == 0) {
							todo = false;
							tmpMap.put("addSubOrder", "fail");
						} else if (result == 1) {
							todo = true;
						} else {
							todo = true;
							needCheckMoney = false;
						}
					} catch (CheckOrderException e) {
						tmpMap.put("addSubOrder", "fail " + e.getMessage());
						todo = false;
					}
				}
				// 注數 & 金額 & 獎金組
				if (todo && needCheckMoney) {
					try {
						boolean result = checkMoneyInOrderJson(orderObject, CommandConstant.NO_OF_BET_INFO_FOR_SUB_ORDER,
								CommandConstant.BASIC_INFO_OF_SUB_ORDER, CommandConstant.ALL_HANDICAP_INFO, realBalacne, realBalacneLF,
								CommandConstant.ALL_LOTTERY_INFO, dateOfTable, baselineIndexList);
						todo &= result;
						if (!result) {
							tmpMap.put("orderStruct", "fail");
						}
					} catch (CheckOrderException e) {
						tmpMap.put("orderStruct", "fail " + e.getMessage());
						todo = false;
					}
				}
				// 玩家餘額
				if (todo) {
					BigDecimal balance = new BigDecimal("" + accData.get("balance"));
					boolean result = checkBalance(orderObject, balance);
					todo &= result;
					if (!result) {
						tmpMap.put("balence", "fail");
					}
				}
				
				// 賠率 & 期號 & 盤口
				if (todo) {
					boolean result = checkBetRatio(service, accId, orderObject, dateOfTable, realBalacne, realBalacneLF);
					todo &= result;
					if (!result) {
						tmpMap.put("realTimeInfo", "fail");
						dao.sentURL("http://127.0.0.1:8080/CTT03BetOrder/UpdateInfo!updateBaseline.php?date=" + new Date().getTime(), "", "", "");
					}
				}
				
				if (todo) {
					//setBaselineLF(orderObject,realBalacneLF);
					
					JSONObject jedisJson = new JSONObject();
					jedisJson.put("data", orderObject);
					jedisJson.put("tokenId", tokenId);
					jedisJson.put("accId", accId);
					jedisJson.put("dateOfTable", dateOfTable);

					Jedis jedis = new Jedis("localhost");
					LOG.debug("redeis_llen======================"+jedis.llen("LottOrder").toString());
					jedis.lpush("LottOrder", jedisJson.toString());
					LOG.debug("redeis_llen======================"+jedis.llen("LottOrder").toString());
					jedis.close();

					if (orderObject.has("orders")) {
						JSONArray ordersArray = orderObject.getJSONArray("orders");
						JSONObject jsonObj2 = ordersArray.getJSONObject(0);
						if (jsonObj2.has("noOfPeriod")) {
							int noOfPeriod = Integer.parseInt(jsonObj2.get("noOfPeriod").toString());
							if (noOfPeriod == 1) {
								tmpMap.put("type", "1");
							} else if (noOfPeriod > 1) {
								tmpMap.put("type", "2");
							}
						}
					}
					tmpMap.put("operateStatus", "success");
					tmpMap.put("tokenId", "success");
				}
			}
		} catch (Exception e) {
			tmpMap.put("status", "Exception" + e.getMessage());
			LOG.error("Exception:" + e.getMessage() + " \n " + accData.toString() + " \n " + accId + "  \n " + tokenId);
			ShowLog.err(LOG, e);
		} finally {
			try {
				res.setContentType("application/json;charset=UTF-8");
				JSONObject responseJSONObject = new JSONObject(tmpMap);
				PrintWriter out = res.getWriter();
				out.println(responseJSONObject);
				out.flush();
				out.close();
				out = null;
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
				res.flushBuffer();
			} catch (Exception e) {
				return;
			}

		}
	}

	/**
	 * get the date info of order,
	 * 
	 * @param mapOfKjInfo
	 *            all periods of all lotteries.
	 * @param orderObject
	 *            the orders.
	 * @return a string of date
	 */
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

	/**
	 * check if the specified lottery switch is on, and add lottery basic type to
	 * order.
	 * 
	 * @param orderObject
	 * @param allLotteryInfos
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private boolean checkLocalIdAndMidIdAndMinIdStatus(JSONObject orderObject, Map<String, Object> allLotteryInfos) {
		JSONArray mainOrders = orderObject.getJSONArray("orders");
		for (int i = 0; i < mainOrders.length(); i++) {
			JSONObject eachMainOrder = (JSONObject) mainOrders.get(i);
			int mainId = eachMainOrder.getInt("mainId");
			int localId = eachMainOrder.getInt("localId");
			int midId = eachMainOrder.getInt("midId");
			long minAuthId = eachMainOrder.getLong("minAuthId");
			try {
				if ("0".equals(((Map) ((Map) ((Map) ((Map) allLotteryInfos.get("" + mainId)).get("" + localId)).get("" + midId)).get("" + minAuthId))
						.get("switch"))) {
					return false;
				}
				eachMainOrder.put("zodiacType", ((Map) ((Map) allLotteryInfos.get("" + mainId)).get("otherInfo")).get("zodiacType"));
				eachMainOrder.put("lotteryLowfreq", ((Map) ((Map) allLotteryInfos.get("" + mainId)).get("otherInfo")).get("lotteryLowfreq"));
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * check player balance is enought to bet the order
	 * 
	 * @param orderObject
	 * @param balance
	 * @return
	 */
	private boolean checkBalance(JSONObject orderObject, BigDecimal balance) {
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

	/**
	 * Exception of this class to throws error msg.
	 * 
	 * @author Quanto
	 *
	 */
	@SuppressWarnings("serial")
	public class CheckOrderException extends Exception {
		public CheckOrderException(String message) {
			super(message);
		}
	}

	/**
	 * some order may contain too many sub orders, so we will add the sub order
	 * here, and the web page doesnt need to transate a big order json.
	 * 
	 * @param orderObject
	 * @param mapOfHandiCap
	 * @param currentBetRatio
	 * @param currentBetRatioLF
	 * @param allLotteryInfos
	 * @param dateOfTable
	 * @return
	 * @throws CheckOrderException
	 */
	@SuppressWarnings("rawtypes")
	private int addSubOrdersForFs(JSONObject orderObject, Map<String, Object> mapOfHandiCap, Map<String, Object> currentBetRatio,
			Map<String, Object> currentBetRatioLF, Map<String, Object> allLotteryInfos, String dateOfTable) throws CheckOrderException {
		boolean hasAddOrder = false;
		JSONArray mainOrders = orderObject.getJSONArray("orders");
		for (int i = 0; i < mainOrders.length(); i++) {
			JSONObject eachMainOrder = (JSONObject) mainOrders.get(i);
			int mainId = eachMainOrder.getInt("mainId");
			int localId = eachMainOrder.getInt("localId");
			int midId = eachMainOrder.getInt("midId");
			long minAuthId = eachMainOrder.getLong("minAuthId");
			if (isNeedAddSubOrder(minAuthId)) {
				String betData = eachMainOrder.getString("betData");
				String handiCap = eachMainOrder.getString("handiCap");
				BigDecimal moneyUnit = eachMainOrder.getBigDecimal("moneyUnit");
				BigDecimal bonusSetRatio = eachMainOrder.getBigDecimal("bonusSetRatio");
				BigDecimal betRatio = eachMainOrder.getBigDecimal("betRatio").setScale(7, BigDecimal.ROUND_DOWN);
				String isDt = eachMainOrder.getString("isDt");
				BigDecimal bonusSetMax = new BigDecimal((String) ((Map) mapOfHandiCap.get(handiCap)).get("bonus_set_max"));
				BigDecimal bonusSetMin = new BigDecimal((String) ((Map) mapOfHandiCap.get(handiCap)).get("bonus_set_min"));
				BigDecimal maxWinBonus = new BigDecimal((String) ((Map) mapOfHandiCap.get(handiCap)).get("max_win_bonus"));
				int noOfPeriod = eachMainOrder.getInt("noOfPeriod");
				BigDecimal totalNoOfBet = new BigDecimal(
						"" + ((Map) ((Map) ((Map) ((Map) allLotteryInfos.get("" + mainId)).get("" + localId)).get("" + midId)).get("" + minAuthId))
								.get("totalNoOfBet"));
				if (bonusSetMax.compareTo(bonusSetRatio) == -1) {
					throw new CheckOrderException("bonusSetMax");
				}
				if (bonusSetRatio.compareTo(bonusSetMin) == -1) {
					throw new CheckOrderException("bonusSetMin");
				}
				JSONArray midOrders = eachMainOrder.getJSONArray("midOrders");
				if (noOfPeriod != midOrders.length()) {
					throw new CheckOrderException("noOfPeriod");
				}
				BigDecimal amountOfMainOrder = eachMainOrder.getBigDecimal("amount");
				BigDecimal amountOfAllMidOrder = new BigDecimal(0);
				for (int j = 0; j < midOrders.length(); j++) {
					JSONObject eachMidOrder = (JSONObject) midOrders.get(j);
					JSONArray betOrders = eachMidOrder.getJSONArray("betOrders");
					BigDecimal amountOfMidOrder = eachMidOrder.getBigDecimal("amount");
					BigDecimal fanDenOfMidOrder = eachMidOrder.getBigDecimal("fanDen");
					BigDecimal tmpFanDen = amountOfMidOrder.multiply((bonusSetMax.subtract(bonusSetRatio)).divide(new BigDecimal(2000))).setScale(2,
							BigDecimal.ROUND_DOWN);
					if (fanDenOfMidOrder.compareTo(tmpFanDen) != 0) {
						throw new CheckOrderException("fanDenOfMidOrder");
					}
					long noOfBetMidOrder = eachMidOrder.getLong("noOfBet");
					if (betRatio.compareTo(new BigDecimal(noOfBetMidOrder).divide(totalNoOfBet, 7, BigDecimal.ROUND_DOWN)) != 0) {
						throw new CheckOrderException("betRatio");
					}
					int calculateNoOfBets = 0;
					Map tmpPrizeSet = null;
					Boolean isLowFreq = false;
					if ("1".equals(eachMainOrder.getString("lotteryLowfreq"))) {
						isLowFreq = true;
						tmpPrizeSet = ((Map) ((Map) ((Map) ((Map) ((Map) ((Map) currentBetRatioLF.get("" + mainId)).get("" + localId))
								.get("" + midId)).get("" + minAuthId)).get(handiCap)).get("0"));
					} else {
						tmpPrizeSet = ((Map) ((Map) ((Map) ((Map) ((Map) currentBetRatio.get("" + mainId)).get("" + localId)).get("" + midId))
								.get("" + minAuthId)).get("0"));
					}

					if (amountOfMainOrder.compareTo(new BigDecimal("" + tmpPrizeSet.get("base_bet"))) == -1) {
						throw new CheckOrderException("amountOfMainOrder");
					}
					if (betRatio.multiply(new BigDecimal(100)).compareTo(new BigDecimal("" + tmpPrizeSet.get("dt_ratio"))) == -1
							&& "1".equals(tmpPrizeSet.get("dt_switch"))) { // 實際有單挑
						if ("0".equals(isDt)) {
							throw new CheckOrderException("isDt");
						}
					}

					if (betOrders.length() == 0) {
						BigDecimal noOfBetTimes = eachMidOrder.getBigDecimal("noOfBetTimes");
						if (minAuthId == 160 || minAuthId == 548 || minAuthId == 310) { // 五星
							int tmpBets = noOfBetsCalculate(betData);
							calculateNoOfBets += tmpBets;
							eachMidOrder.put("betOrders",
									betOrdersGeneratorForFs(mainId, localId, midId, minAuthId, handiCap, betData, maxWinBonus, noOfBetTimes,
											moneyUnit, bonusSetRatio, isDt, CommandConstant.subTypeOf5xPos, 0, mapOfHandiCap, currentBetRatio,
											currentBetRatioLF, isLowFreq, tmpBets));
						} else if (minAuthId == 148 || minAuthId == 536 || minAuthId == 298) { // 前四
							int indexOf4x = 0;
							int tmpBets = noOfBetsCalculate(betData);
							calculateNoOfBets += tmpBets;
							eachMidOrder.put("betOrders",
									betOrdersGeneratorForFs(mainId, localId, midId, minAuthId, handiCap, betData, maxWinBonus, noOfBetTimes,
											moneyUnit, bonusSetRatio, isDt, CommandConstant.subTypeOf4xPos, indexOf4x, mapOfHandiCap, currentBetRatio,
											currentBetRatioLF, isLowFreq, tmpBets));
						} else if (minAuthId == 154 || minAuthId == 542 || minAuthId == 304) { // 後四
							int indexOf4x = 4;
							int tmpBets = noOfBetsCalculate(betData);
							calculateNoOfBets += tmpBets;
							eachMidOrder.put("betOrders",
									betOrdersGeneratorForFs(mainId, localId, midId, minAuthId, handiCap, betData, maxWinBonus, noOfBetTimes,
											moneyUnit, bonusSetRatio, isDt, CommandConstant.subTypeOf4xPos, indexOf4x, mapOfHandiCap, currentBetRatio,
											currentBetRatioLF, isLowFreq, tmpBets));
						} else if (minAuthId == 214 || minAuthId == 602 || minAuthId == 364) { // 任四
							String[] betDataArray = betData.split("\\|");
							int posOfBet = 0;
							for (int k = 0; k < betDataArray.length; k++) {
								if (!"".equals(betDataArray[k])) {
									int posIndex = 4 - k;
									posOfBet += 1 << posIndex;
								}
							}
							JSONArray insertArray = new JSONArray();
							for (int k = 0; k < CommandConstant.subTypeOf4xPos.size(); k++) {
								if ((((int) CommandConstant.subTypeOf4xPos.get(k).get("posValue"))
										& posOfBet) == ((int) CommandConstant.subTypeOf4xPos.get(k).get("posValue"))) {
									String tmpBetData = "";
									for (int z = 0; z < betDataArray.length; z++) {
										int posIndex = 4 - z;
										if (((1 << posIndex) & ((int) CommandConstant.subTypeOf4xPos.get(k).get("posValue"))) != 0) {
											tmpBetData = tmpBetData + betDataArray[z] + "|";
										}
									}
									tmpBetData = tmpBetData.substring(0, tmpBetData.length() - 1);
									int tmpBets = noOfBetsCalculate(tmpBetData);
									calculateNoOfBets += tmpBets;
									JSONArray tmpArray = betOrdersGeneratorForFs(mainId, localId, midId, minAuthId, handiCap, tmpBetData, maxWinBonus,
											noOfBetTimes, moneyUnit, bonusSetRatio, isDt, CommandConstant.subTypeOf4xPos, k, mapOfHandiCap,
											currentBetRatio, currentBetRatioLF, isLowFreq, tmpBets);
									for (int z = 0; z < tmpArray.length(); z++) {
										insertArray.put(tmpArray.get(z));
									}
								}
							}
							eachMidOrder.put("betOrders", insertArray);
						} else if (minAuthId == 97 || minAuthId == 247 || minAuthId == 485) { // 前三
							int indexOf3x = 0;
							int tmpBets = noOfBetsCalculate(betData);
							calculateNoOfBets += tmpBets;
							eachMidOrder.put("betOrders",
									betOrdersGeneratorForFs(mainId, localId, midId, minAuthId, handiCap, betData, maxWinBonus, noOfBetTimes,
											moneyUnit, bonusSetRatio, isDt, CommandConstant.subTypeOf3xPos, indexOf3x, mapOfHandiCap, currentBetRatio,
											currentBetRatioLF, isLowFreq, tmpBets));
						} else if (minAuthId == 114 || minAuthId == 264 || minAuthId == 502) { // 中三
							int indexOf3x = 6;
							int tmpBets = noOfBetsCalculate(betData);
							calculateNoOfBets += tmpBets;
							eachMidOrder.put("betOrders",
									betOrdersGeneratorForFs(mainId, localId, midId, minAuthId, handiCap, betData, maxWinBonus, noOfBetTimes,
											moneyUnit, bonusSetRatio, isDt, CommandConstant.subTypeOf3xPos, indexOf3x, mapOfHandiCap, currentBetRatio,
											currentBetRatioLF, isLowFreq, tmpBets));
						} else if (minAuthId == 131 || minAuthId == 281 || minAuthId == 519) { // 後三
							int indexOf3x = 9;
							int tmpBets = noOfBetsCalculate(betData);
							calculateNoOfBets += tmpBets;
							eachMidOrder.put("betOrders",
									betOrdersGeneratorForFs(mainId, localId, midId, minAuthId, handiCap, betData, maxWinBonus, noOfBetTimes,
											moneyUnit, bonusSetRatio, isDt, CommandConstant.subTypeOf3xPos, indexOf3x, mapOfHandiCap, currentBetRatio,
											currentBetRatioLF, isLowFreq, tmpBets));
						} else if (minAuthId == 204 || minAuthId == 592 || minAuthId == 354) { // 任三
							String[] betDataArray = betData.split("\\|");
							int posOfBet = 0;
							for (int k = 0; k < betDataArray.length; k++) {
								if (!"".equals(betDataArray[k])) {
									int posIndex = 4 - k;
									posOfBet += 1 << posIndex;
								}
							}
							JSONArray insertArray = new JSONArray();
							for (int k = 0; k < CommandConstant.subTypeOf3xPos.size(); k++) {
								if ((((int) CommandConstant.subTypeOf3xPos.get(k).get("posValue"))
										& posOfBet) == ((int) CommandConstant.subTypeOf3xPos.get(k).get("posValue"))) {
									String tmpBetData = "";
									for (int z = 0; z < betDataArray.length; z++) {
										int posIndex = 4 - z;
										if (((1 << posIndex) & ((int) CommandConstant.subTypeOf3xPos.get(k).get("posValue"))) != 0) {
											tmpBetData = tmpBetData + betDataArray[z] + "|";
										}
									}
									tmpBetData = tmpBetData.substring(0, tmpBetData.length() - 1);
									int tmpBets = noOfBetsCalculate(tmpBetData);
									calculateNoOfBets += tmpBets;
									JSONArray tmpArray = betOrdersGeneratorForFs(mainId, localId, midId, minAuthId, handiCap, tmpBetData, maxWinBonus,
											noOfBetTimes, moneyUnit, bonusSetRatio, isDt, CommandConstant.subTypeOf3xPos, k, mapOfHandiCap,
											currentBetRatio, currentBetRatioLF, isLowFreq, tmpBets);
									for (int z = 0; z < tmpArray.length(); z++) {
										insertArray.put(tmpArray.get(z));
									}
								}
							}
							eachMidOrder.put("betOrders", insertArray);
						} else if (minAuthId == 81 || minAuthId == 231 || minAuthId == 469) { // 前二
							int indexOf2x = 0;
							int tmpBets = noOfBetsCalculate(betData);
							calculateNoOfBets += tmpBets;
							eachMidOrder.put("betOrders",
									betOrdersGeneratorForFs(mainId, localId, midId, minAuthId, handiCap, betData, maxWinBonus, noOfBetTimes,
											moneyUnit, bonusSetRatio, isDt, CommandConstant.subTypeOf2xPos, indexOf2x, mapOfHandiCap, currentBetRatio,
											currentBetRatioLF, isLowFreq, tmpBets));
						} else if (minAuthId == 89 || minAuthId == 239 || minAuthId == 477) { // 後二
							int indexOf2x = 9;
							int tmpBets = noOfBetsCalculate(betData);
							calculateNoOfBets += tmpBets;
							eachMidOrder.put("betOrders",
									betOrdersGeneratorForFs(mainId, localId, midId, minAuthId, handiCap, betData, maxWinBonus, noOfBetTimes,
											moneyUnit, bonusSetRatio, isDt, CommandConstant.subTypeOf2xPos, indexOf2x, mapOfHandiCap, currentBetRatio,
											currentBetRatioLF, isLowFreq, tmpBets));
						} else if (minAuthId == 197 || minAuthId == 585 || minAuthId == 347) { // 任二
							String[] betDataArray = betData.split("\\|");
							int posOfBet = 0;
							for (int k = 0; k < betDataArray.length; k++) {
								if (!"".equals(betDataArray[k])) {
									int posIndex = 4 - k;
									posOfBet += 1 << posIndex;
								}
							}
							JSONArray insertArray = new JSONArray();
							for (int k = 0; k < CommandConstant.subTypeOf2xPos.size(); k++) {
								if ((((int) CommandConstant.subTypeOf2xPos.get(k).get("posValue"))
										& posOfBet) == ((int) CommandConstant.subTypeOf2xPos.get(k).get("posValue"))) {
									String tmpBetData = "";
									for (int z = 0; z < betDataArray.length; z++) {
										int posIndex = 4 - z;
										if (((1 << posIndex) & ((int) CommandConstant.subTypeOf2xPos.get(k).get("posValue"))) != 0) {
											tmpBetData = tmpBetData + betDataArray[z] + "|";
										}
									}
									tmpBetData = tmpBetData.substring(0, tmpBetData.length() - 1);
									int tmpBets = noOfBetsCalculate(tmpBetData);
									calculateNoOfBets += tmpBets;
									JSONArray tmpArray = betOrdersGeneratorForFs(mainId, localId, midId, minAuthId, handiCap, tmpBetData, maxWinBonus,
											noOfBetTimes, moneyUnit, bonusSetRatio, isDt, CommandConstant.subTypeOf2xPos, k, mapOfHandiCap,
											currentBetRatio, currentBetRatioLF, isLowFreq, tmpBets);
									for (int z = 0; z < tmpArray.length(); z++) {
										insertArray.put(tmpArray.get(z));
									}
								}
							}
							eachMidOrder.put("betOrders", insertArray);
						} else if (minAuthId == 185 || minAuthId == 573 || minAuthId == 335) { // 五星趣味
							int tmpBets = noOfBetsCalculate(betData);
							calculateNoOfBets += tmpBets;
							eachMidOrder.put("betOrders",
									betOrdersGeneratorForFs(mainId, localId, midId, minAuthId, handiCap, betData, maxWinBonus, noOfBetTimes,
											moneyUnit, bonusSetRatio, isDt, CommandConstant.subTypeOf5xQwPos, 0, mapOfHandiCap, currentBetRatio,
											currentBetRatioLF, isLowFreq, tmpBets));
						} else if (minAuthId == 186 || minAuthId == 574 || minAuthId == 336) { // 四星趣味
							int tmpBets = noOfBetsCalculate(betData);
							calculateNoOfBets += tmpBets;
							eachMidOrder.put("betOrders",
									betOrdersGeneratorForFs(mainId, localId, midId, minAuthId, handiCap, betData, maxWinBonus, noOfBetTimes,
											moneyUnit, bonusSetRatio, isDt, CommandConstant.subTypeOf4xQwPos, 0, mapOfHandiCap, currentBetRatio,
											currentBetRatioLF, isLowFreq, tmpBets));
						} else if (minAuthId == 189 || minAuthId == 577 || minAuthId == 339) { // 五星區間
							int tmpBets = noOfBetsCalculate(betData);
							calculateNoOfBets += tmpBets;
							eachMidOrder.put("betOrders",
									betOrdersGeneratorForFs(mainId, localId, midId, minAuthId, handiCap, betData, maxWinBonus, noOfBetTimes,
											moneyUnit, bonusSetRatio, isDt, CommandConstant.subTypeOf5xQzPos, 0, mapOfHandiCap, currentBetRatio,
											currentBetRatioLF, isLowFreq, tmpBets));
						} else if (minAuthId == 190 || minAuthId == 578 || minAuthId == 340) { // 四星區間
							int tmpBets = noOfBetsCalculate(betData);
							calculateNoOfBets += tmpBets;
							eachMidOrder.put("betOrders",
									betOrdersGeneratorForFs(mainId, localId, midId, minAuthId, handiCap, betData, maxWinBonus, noOfBetTimes,
											moneyUnit, bonusSetRatio, isDt, CommandConstant.subTypeOf4xQzPos, 0, mapOfHandiCap, currentBetRatio,
											currentBetRatioLF, isLowFreq, tmpBets));
						}
//						else if (minAuthId == 645 || minAuthId == 646 || minAuthId == 647 || minAuthId == 648 || minAuthId == 649) { // 連號
//							return true;
//						}else if (minAuthId == 661 || minAuthId == 662 || minAuthId == 663 || minAuthId == 664 || minAuthId == 665 || minAuthId == 666 || minAuthId == 667 || minAuthId == 668) { // 不中
//							return true;
//						}
						hasAddOrder = true;

						eachMidOrder.put("maxBonus", ((JSONObject) ((JSONArray) eachMidOrder.get("betOrders")).get(0)).get("oneTimesBonus"));
					}

					if (noOfBetMidOrder != calculateNoOfBets && hasAddOrder) {
						throw new CheckOrderException("noOfBetMidOrder");
					}

					BigDecimal amountOfAllBetOrder = new BigDecimal(0);
					betOrders = eachMidOrder.getJSONArray("betOrders");
					for (int k = 0; k < betOrders.length(); k++) {
						amountOfAllBetOrder = amountOfAllBetOrder.add(((JSONObject) betOrders.get(k)).getBigDecimal("amount"));
					}
					if (hasAddOrder && amountOfMidOrder.compareTo(amountOfAllBetOrder) != 0) {
						throw new CheckOrderException("amountOfMidOrder");
					}
					amountOfAllMidOrder = amountOfAllMidOrder.add(amountOfMidOrder);
				}
				if (hasAddOrder && amountOfMainOrder.compareTo(amountOfAllMidOrder) != 0) {
					throw new CheckOrderException("amountOfMainOrder");
				}
			}
		}
		if (hasAddOrder) {
			return 2;
		} else {
			return 1;
		}
	}

	/**
	 * check about money and bonus etc in the order, if there is any error, return
	 * false.
	 * 
	 * @param orderObject
	 * @param mapOfBetNo
	 * @param mapOfSubBet
	 * @param mapOfHandiCap
	 * @param currentBetRatio
	 * @param currentBetRatioLF
	 * @param allLotteryInfos
	 * @param dateOfTable
	 * @return
	 * @throws CheckOrderException
	 */
	@SuppressWarnings("rawtypes")
	private boolean checkMoneyInOrderJson(JSONObject orderObject, Map<String, Object> mapOfBetNo, Map<String, Object> mapOfSubBet,
			Map<String, Object> mapOfHandiCap, Map<String, Object> currentBetRatio, Map<String, Object> currentBetRatioLF,
			Map<String, Object> allLotteryInfos, String dateOfTable, List<Integer> baselineIndexList) throws CheckOrderException {

		JSONArray mainOrders = orderObject.getJSONArray("orders");
		for (int i = 0; i < mainOrders.length(); i++) {
			JSONObject eachMainOrder = (JSONObject) mainOrders.get(i);
			BigDecimal moneyUnit = eachMainOrder.getBigDecimal("moneyUnit");
			BigDecimal bonusSetRatio = eachMainOrder.getBigDecimal("bonusSetRatio");
			String handiCap = eachMainOrder.getString("handiCap");
			BigDecimal maxWinBonus = new BigDecimal((String) ((Map) mapOfHandiCap.get(handiCap)).get("max_win_bonus"));
			int mainId = eachMainOrder.getInt("mainId");
			int localId = eachMainOrder.getInt("localId");
			int midId = eachMainOrder.getInt("midId");
			long minAuthId = eachMainOrder.getLong("minAuthId");
			BigDecimal betRatio = eachMainOrder.getBigDecimal("betRatio").setScale(7, BigDecimal.ROUND_DOWN);
			BigDecimal relativeBaseline = new BigDecimal((String) ((Map) mapOfHandiCap.get(handiCap)).get("relative_baseline"));
			BigDecimal amountOfMainOrder = eachMainOrder.getBigDecimal("amount");
			BigDecimal amountOfAllMidOrder = new BigDecimal(0);
			JSONArray midOrders = eachMainOrder.getJSONArray("midOrders");
			for (int j = 0; j < midOrders.length(); j++) {
				JSONObject eachMidOrder = (JSONObject) midOrders.get(j);
				JSONArray betOrders = eachMidOrder.getJSONArray("betOrders");
				BigDecimal noOfBetTimes = eachMidOrder.getBigDecimal("noOfBetTimes");
				BigDecimal amountOfMidOrder = eachMidOrder.getBigDecimal("amount");
				BigDecimal amountOfAllBetOrder = new BigDecimal(0);
				long noOfBetMidOrder = eachMidOrder.getLong("noOfBet");
				long noOfBetMidOrderOfAllBetOrders = 0;
				for (int k = 0; k < betOrders.length(); k++) {
					JSONObject eachBetOrder = (JSONObject) betOrders.get(k);
					long playedId = eachBetOrder.getLong("playedId");
					long noOfBet = eachBetOrder.getLong("noOfBet");
					int tmpNumOfBet = Integer.parseInt("" + ((Map) mapOfSubBet.get("" + playedId)).get("note"));
					if (tmpNumOfBet == 1 || tmpNumOfBet == 2 || tmpNumOfBet == 9 || tmpNumOfBet == 10) {
						tmpNumOfBet = Integer.parseInt("" + ((Map) mapOfBetNo.get("" + tmpNumOfBet)).get("0"));
					} else {
						tmpNumOfBet = Integer.parseInt("" + ((Map) mapOfBetNo.get("" + tmpNumOfBet)).get(eachBetOrder.getString("betData")));
					}
					if (noOfBet != tmpNumOfBet ? (isTwoBaselineIndex(minAuthId) == false) : false ) {
						throw new CheckOrderException("noOfBet");
					}

					if (isTwoBaselineIndex(minAuthId) == true) {
						noOfBetMidOrderOfAllBetOrders = 1;
					} else {
						noOfBetMidOrderOfAllBetOrders += tmpNumOfBet;
					}

					int baselineIndex = 0;
					if (isBetDataToBaselineIndex(minAuthId)) {
						baselineIndex = eachBetOrder.getInt("baselineIndex");
					} else {
						baselineIndex = Integer.parseInt("" + ((Map) mapOfSubBet.get("" + playedId)).get("baselineIndex"));
					}

					Map tmpPrizeSet = null;

					if ("1".equals(eachMainOrder.getString("lotteryLowfreq"))) {
						tmpPrizeSet = ((Map) ((Map) ((Map) ((Map) ((Map) ((Map) currentBetRatioLF.get("" + mainId)).get("" + localId))
								.get("" + midId)).get("" + minAuthId)).get(handiCap)).get("" + baselineIndex));
						if (tmpPrizeSet == null) {
							tmpPrizeSet = ((Map) ((Map) ((Map) ((Map) ((Map) ((Map) currentBetRatioLF.get("" + mainId)).get("" + localId))
									.get("" + midId)).get("" + minAuthId)).get(handiCap)).get("0"));
						}

					} else {
						tmpPrizeSet = ((Map) ((Map) ((Map) ((Map) ((Map) currentBetRatio.get("" + mainId)).get("" + localId)).get("" + midId))
								.get("" + minAuthId)).get("" + baselineIndex));
						if (tmpPrizeSet == null) {
							tmpPrizeSet = ((Map) ((Map) ((Map) ((Map) ((Map) currentBetRatio.get("" + mainId)).get("" + localId)).get("" + midId))
									.get("" + minAuthId)).get("0"));
						}

					}

					BigDecimal baseline = eachBetOrder.getBigDecimal("baseline");
					BigDecimal baselineInDB = new BigDecimal("" + tmpPrizeSet.get("baseline"));
					if ((minAuthId == 105 || minAuthId == 122 || minAuthId == 139 || minAuthId == 213 || minAuthId == 493 || minAuthId == 510
							|| minAuthId == 527 || minAuthId == 601 || minAuthId == 255 || minAuthId == 272 || minAuthId == 289 || minAuthId == 363
							|| minAuthId == 396)
							&& (playedId == 22 || playedId == 36 || playedId == 50 || playedId == 448 || playedId == 455 || playedId == 462
									|| playedId == 469 || playedId == 476 || playedId == 483 || playedId == 490 || playedId == 535)) {
						baselineInDB = baselineInDB.multiply(new BigDecimal(2));
					}
					if (baseline.compareTo(baselineInDB) != 0) {
						throw new CheckOrderException("baseline");
					}
					BigDecimal bonus = eachBetOrder.getBigDecimal("bonus");
					if (bonus.compareTo(maxWinBonus) == 1) {
						throw new CheckOrderException("maxWinBonus");
					}
					if (betRatio.multiply(new BigDecimal(100)).compareTo(new BigDecimal("" + tmpPrizeSet.get("dt_ratio"))) == -1
							&& "1".equals(tmpPrizeSet.get("dt_switch"))) { // 實際有單挑
						if (bonus.compareTo(new BigDecimal("" + tmpPrizeSet.get("dt_bonus"))) == 1) {
							throw new CheckOrderException("dt_bonus");
						}
					} else {
						BigDecimal tmpBonus = moneyUnit.multiply(noOfBetTimes).multiply(baseline)
								.multiply(relativeBaseline.divide(new BigDecimal(100))).multiply(bonusSetRatio).divide(new BigDecimal(2000))
								.setScale(2, BigDecimal.ROUND_DOWN); // bonus不算注數
						if (bonus.compareTo(tmpBonus) != 0) {
							throw new CheckOrderException("bonus");
						}
					}
					BigDecimal amount = eachBetOrder.getBigDecimal("amount");
					BigDecimal tmpAmount = moneyUnit.multiply(new BigDecimal(noOfBet)).multiply(noOfBetTimes).setScale(2, BigDecimal.ROUND_DOWN);
					if (amount.compareTo(tmpAmount) != 0) {
						throw new CheckOrderException("amount");
					}
					amountOfAllBetOrder = amountOfAllBetOrder.add(amount);
				}
				if (noOfBetMidOrder != noOfBetMidOrderOfAllBetOrders) {
					throw new CheckOrderException("noOfBetMidOrder");
				}
				if (amountOfMidOrder.compareTo(amountOfAllBetOrder) != 0) {
					throw new CheckOrderException("amountOfMidOrder");
				}
				amountOfAllMidOrder = amountOfAllMidOrder.add(amountOfMidOrder);
			}
			if (amountOfMainOrder.compareTo(amountOfAllMidOrder) != 0) {
				throw new CheckOrderException("amountOfMainOrder");
			}
		}
		return true;
	}

	/**
	 * init all necessery data before checking order
	 */
	public void loadCheckDataFromDB() {
		LOG.debug(CommandConstant.NO_OF_BET_INFO_FOR_SUB_ORDER.toString());
		LOG.debug(CommandConstant.BASIC_INFO_OF_SUB_ORDER.toString());
		LOG.debug(CommandConstant.ALL_HANDICAP_INFO.toString());
		LOG.debug(CommandConstant.ALL_LOTTERY_INFO.toString());
		LOG.debug(CommandConstant.KJ_TIME_INFO.toString());
		LOG.debug(CommandConstant.CURRENT_BET_RATIO.toString());
		if (CommandConstant.NO_OF_BET_INFO_FOR_SUB_ORDER == null || CommandConstant.BASIC_INFO_OF_SUB_ORDER == null
				|| CommandConstant.ALL_HANDICAP_INFO == null) {
			getAllLotteryInfoWhenInit();
		}
		if (CommandConstant.ALL_LOTTERY_INFO == null) {
			getBasicInfoOfSubOrderWhenInit();
		}
		if (CommandConstant.KJ_TIME_INFO == null) {
			getKjTimeInfoWhenInit();
		}
		if (CommandConstant.CURRENT_BET_RATIO == null) {
			getBaselineWhenInit();
		}

	}

	/**
	 * get all lottery switch and basic info
	 */
	public void getAllLotteryInfoWhenInit() {
		LOG.debug("getAllLotteryInfoWhenInit");
		OrderInfoOfBettingServiceImpl service = null;
		OrderInfoOfBettingDaoImpl dao = null;
		try {
			service = new OrderInfoOfBettingServiceImpl();
			dao = new OrderInfoOfBettingDaoImpl();
			service.setDao(dao);
			CommandConstant.ALL_LOTTERY_INFO = service.getAllLottery();
			CommandConstant.DB_LAST_UPDATE_TIME_OF_ALL_LOTTERY_INFO = service.getAllLotteryLastUpdateTime();
			CommandConstant.CHECK_UPDATE_ALL_LOTTERY_TIME = new Date().getTime();
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

	/**
	 * get all sub order infos
	 */
	public void getBasicInfoOfSubOrderWhenInit() {
		LOG.debug("getBasicInfoOfSubOrderWhenInit");
		OrderInfoOfBettingServiceImpl service = null;
		OrderInfoOfBettingDaoImpl dao = null;
		try {
			service = new OrderInfoOfBettingServiceImpl();
			dao = new OrderInfoOfBettingDaoImpl();
			service.setDao(dao);
			CommandConstant.NO_OF_BET_INFO_FOR_SUB_ORDER = service.getSubOrderInfoNoOfBet();
			CommandConstant.BASIC_INFO_OF_SUB_ORDER = service.getSubOrderInfo();
			CommandConstant.ALL_HANDICAP_INFO = service.getAllHandicap();
			CommandConstant.CHECK_UPDATE_SUB_ORDER_TIME = new Date().getTime();
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

	/**
	 * get all period of all lotteries.
	 */
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

	/**
	 * get all baseline infos.
	 */
	public void getBaselineWhenInit() {
		LOG.debug("getBaselineWhenInit");
		BaselineInfoServiceImpl service = null;
		BaselineInfoDaoImpl dao = null;
		try {
			service = new BaselineInfoServiceImpl();
			dao = new BaselineInfoDaoImpl();
			service.setDao(dao);
			CommandConstant.CURRENT_BET_RATIO = service.getBaseline();
			CommandConstant.CURRENT_BET_RATIO_LF = service.getBaselineLF();
			CommandConstant.CHECK_UPDATE_BASELINE_TIME = new Date().getTime();
			CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE = service.getBaselineLastUpdateTime();
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

	/**
	 * to calculate the no of bets
	 * 
	 * @param bet
	 *            all combination of bets.
	 * @return
	 */
	private int noOfBetsCalculate(String bet) {
		int result = 1;
		String[] betData = bet.split("\\|");
		for (int i = 0; i < betData.length; i++) {
			if (!"".equals(betData[i])) {
				String[] subNums = betData[i].split(",");
				if (subNums.length != 0) {
					result = result * subNums.length;
				}
			}
		}
		return result;
	}

	/**
	 * check if the order need to add suborders.
	 * 
	 * @param minAuthId
	 * @return
	 */
	private boolean isNeedAddSubOrder(long minAuthId) {
		if (minAuthId == 160 || minAuthId == 548 || minAuthId == 310) { // 五星
			return true;
		} else if (minAuthId == 148 || minAuthId == 536 || minAuthId == 298) { // 前四
			return true;
		} else if (minAuthId == 154 || minAuthId == 542 || minAuthId == 304) { // 後四
			return true;
		} else if (minAuthId == 214 || minAuthId == 602 || minAuthId == 364) { // 任四
			return true;
		} else if (minAuthId == 97 || minAuthId == 247 || minAuthId == 485) { // 前三
			return true;
		} else if (minAuthId == 114 || minAuthId == 264 || minAuthId == 502) { // 中三
			return true;
		} else if (minAuthId == 131 || minAuthId == 281 || minAuthId == 519) { // 後三
			return true;
		} else if (minAuthId == 204 || minAuthId == 592 || minAuthId == 354) { // 任三
			return true;
		} else if (minAuthId == 81 || minAuthId == 231 || minAuthId == 469) { // 前二
			return true;
		} else if (minAuthId == 89 || minAuthId == 239 || minAuthId == 477) { // 後二
			return true;
		} else if (minAuthId == 197 || minAuthId == 585 || minAuthId == 347) { // 任二
			return true;
		} else if (minAuthId == 185 || minAuthId == 573 || minAuthId == 335) { // 五星趣味
			return true;
		} else if (minAuthId == 186 || minAuthId == 574 || minAuthId == 336) { // 四星趣味
			return true;
		} else if (minAuthId == 189 || minAuthId == 577 || minAuthId == 339) { // 五星區間
			return true;
		} else if (minAuthId == 190 || minAuthId == 578 || minAuthId == 340) { // 四星區間
			return true;
//		}else if (minAuthId == 645 || minAuthId == 646 || minAuthId == 647 || minAuthId == 648 || minAuthId == 649) { // 連號
//			return true;
//		}else if (minAuthId == 661 || minAuthId == 662 || minAuthId == 663 || minAuthId == 664 || minAuthId == 665 || minAuthId == 666 || minAuthId == 667 || minAuthId == 668) { // 不中
//			return true;
		}
		
		
		return false;
	}

	/**
	 * generate sub orders.
	 * 
	 * @param mainId
	 * @param localId
	 * @param midId
	 * @param minAuthId
	 * @param handiCap
	 * @param betData
	 * @param maxWinBonus
	 * @param noOfBetTimes
	 * @param moneyUnit
	 * @param bonusSetRatio
	 * @param isDt
	 * @param subTypeInfo
	 * @param index
	 * @param mapOfHandiCap
	 * @param currentBetRatio
	 * @param currentBetRatioLF
	 * @param isLowFreq
	 * @param noOfSubOrders
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private JSONArray betOrdersGeneratorForFs(int mainId, int localId, int midId, long minAuthId, String handiCap, String betData,
			BigDecimal maxWinBonus, BigDecimal noOfBetTimes, BigDecimal moneyUnit, BigDecimal bonusSetRatio, String isDt,
			List<Map<String, Object>> subTypeInfo, int index, Map<String, Object> mapOfHandiCap, Map<String, Object> currentBetRatio,
			Map<String, Object> currentBetRatioLF, Boolean isLowFreq, int noOfSubOrders) {
		JSONArray betOrdersNew = new JSONArray();
		Map tmpPrizeSet = null;
		if (isLowFreq) {
			tmpPrizeSet = ((Map) ((Map) ((Map) ((Map) ((Map) ((Map) currentBetRatioLF.get("" + mainId)).get("" + localId)).get("" + midId))
					.get("" + minAuthId)).get(handiCap)).get("" + subTypeInfo.get(index).get("prizeLevel")));
		} else {
			tmpPrizeSet = ((Map) ((Map) ((Map) ((Map) ((Map) currentBetRatio.get("" + mainId)).get("" + localId)).get("" + midId))
					.get("" + minAuthId)).get("" + subTypeInfo.get(index).get("prizeLevel")));
		}

		BigDecimal baseline = new BigDecimal("" + tmpPrizeSet.get("baseline"));
		BigDecimal relativeBaseline = new BigDecimal((String) ((Map) mapOfHandiCap.get(handiCap)).get("relative_baseline"));

		JSONObject eachBetOrder = new JSONObject();
		eachBetOrder.put("betData", betData);
		eachBetOrder.put("playedId", "" + subTypeInfo.get(index).get("playedId"));
		eachBetOrder.put("amount", "" + noOfBetTimes.multiply(moneyUnit).multiply(new BigDecimal("" + subTypeInfo.get(index).get("noOfBet")))
				.multiply(new BigDecimal(noOfSubOrders)).setScale(2, BigDecimal.ROUND_DOWN));
		BigDecimal tmpBonus = noOfBetTimes.multiply(moneyUnit).multiply(relativeBaseline).multiply(bonusSetRatio).multiply(baseline)
				.divide(new BigDecimal(200000)).setScale(2, BigDecimal.ROUND_DOWN);
		BigDecimal oneTimesBonus = moneyUnit.multiply(relativeBaseline).multiply(bonusSetRatio).multiply(baseline).divide(new BigDecimal(200000))
				.setScale(2, BigDecimal.ROUND_DOWN);
		if ("1".equals(isDt)) {
			if (tmpBonus.compareTo(new BigDecimal("" + tmpPrizeSet.get("dt_bonus"))) == 1) {
				tmpBonus = new BigDecimal("" + tmpPrizeSet.get("dt_bonus")).setScale(2, BigDecimal.ROUND_DOWN);
			}
		}

		if (maxWinBonus.compareTo(tmpBonus) == -1) {
			tmpBonus = maxWinBonus;
		}
		eachBetOrder.put("oneTimesBonus", "" + oneTimesBonus);
		eachBetOrder.put("bonus", "" + tmpBonus);
		eachBetOrder.put("noOfBet", "" + subTypeInfo.get(index).get("noOfBet"));
		eachBetOrder.put("baseline", "" + tmpPrizeSet.get("baseline"));
		eachBetOrder.put("subAction", "1");
		betOrdersNew.put(eachBetOrder);

		return betOrdersNew;
	}

	/**
	 * check baseline of order
	 * 
	 * @param service
	 * @param accId
	 * @param orderObject
	 * @param dateOfTable
	 * @param currentBetRatio
	 * @param currentBetRatioLF
	 * @return
	 */
	private boolean checkBetRatio(MemberBetServiceImpl service, long accId, JSONObject orderObject, String dateOfTable,
			Map<String, Object> currentBetRatio, Map<String, Object> currentBetRatioLF) {
		try {
			JSONArray mainOrders = orderObject.getJSONArray("orders");
			for (int i = 0; i < mainOrders.length(); i++) {
				JSONObject eachMainOrder = (JSONObject) mainOrders.get(i);
				long periodNum = eachMainOrder.getLong("startPeriodNum");
				int handiCap = eachMainOrder.getInt("handiCap");
				int mainId = eachMainOrder.getInt("mainId");
				int localId = eachMainOrder.getInt("localId");
				int midId = eachMainOrder.getInt("midId");
				long minAuthId = eachMainOrder.getLong("minAuthId");

				if ("1".equals(eachMainOrder.getString("lotteryLowfreq"))) {
					Map<String, Map<String, Map<String, String>>> tmpRatios = ((Map) ((Map) ((Map) ((Map) currentBetRatioLF.get("" + mainId))
							.get("" + localId)).get("" + midId)).get("" + minAuthId));
					if (!service.checkRealTimeBaselinePeriodAndHandiCapLF(accId, periodNum, minAuthId, localId, dateOfTable, handiCap, tmpRatios)) {
						return false;
					}
				} else {
					Map<String, Map<String, String>> tmpRatios = ((Map) ((Map) ((Map) ((Map) currentBetRatio.get("" + mainId)).get("" + localId))
							.get("" + midId)).get("" + minAuthId));
					if (!service.checkRealTimeBaselinePeriodAndHandiCap(accId, periodNum, minAuthId, localId, dateOfTable, handiCap, tmpRatios)) {
						return false;
					}
				}
			}
			return true;
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		} finally {
		}
	}
	
	/**
	 * 每個選號都有各自賠率
	 * @param minAuthId
	 * @return
	 */
	public boolean isBetDataToBaselineIndex(long minAuthId) {
		if (minAuthId == 631) { // 特碼
			return true;
		} else if (minAuthId == 632) { // 正碼
			return true;
		} else if (minAuthId == 633 || minAuthId == 634 || minAuthId == 635 || minAuthId == 636 || minAuthId == 637 || minAuthId == 638) { // 正碼特
			return true;
		} else if (minAuthId == 645 || minAuthId == 646 || minAuthId == 647 || minAuthId == 648 || minAuthId == 649) { // 連碼
			return true;
		} else if (minAuthId == 651) { // 特碼生肖
			return true;
		} else if (minAuthId == 653 || minAuthId == 654 || minAuthId == 655 || minAuthId == 656) { // 生肖連
			return true;
		} else if (minAuthId == 658 || minAuthId == 659 || minAuthId == 660) { // 尾數連
			return true;
		} else if (minAuthId == 661 || minAuthId == 662 || minAuthId == 663 || minAuthId == 664 || minAuthId == 665 || minAuthId == 666
				|| minAuthId == 667 || minAuthId == 668) { // 不中
			return true;
		}
		return false;
	}
	
	/**
	 * 有兩種中獎方式 賠率
	 * @param minAuthId
	 * @return
	 */
	public boolean isTwoBaselineIndex(long minAuthId) {
		if (minAuthId == 646) { // 三中二
			return true;
		} else if (minAuthId == 648) { // 二中特
			return true;
		}
		return false;
	}
	
	/**
	 * 特殊兌獎方式
	 * @param minAuthId
	 * @return
	 */
	public boolean isSpecialCheckLottery(long minAuthId) {
		if (minAuthId == 646) { // 三中二
			return true;
		} else if (minAuthId == 648) { // 二中特
			return true;
		}else if (minAuthId == 661 || minAuthId == 662 || minAuthId == 663 || minAuthId == 664 || minAuthId == 665 || minAuthId == 666
				|| minAuthId == 667 || minAuthId == 668) { // 不中
			return true;
		}
		return false;
	}
	
	/**
	 * 判斷 賠率是否為選號中最低 
	 * 
	 * @param orderObject
	 * @param currentBetRatio
	 * @param currentBetRatioLF
	 * @return
	 */
	public boolean checkIsNoBaselineIndexRatio(JSONObject orderObject,Map<String, Object> currentBetRatio, Map<String, Object> currentBetRatioLF) {
		try {
			JSONArray mainOrders = orderObject.getJSONArray("orders");
			for (int i = 0; i < mainOrders.length(); i++) {
				JSONObject eachMainOrder = (JSONObject) mainOrders.get(i);
				long periodNum = eachMainOrder.getLong("startPeriodNum");
				int handiCap = eachMainOrder.getInt("handiCap");
				int mainId = eachMainOrder.getInt("mainId");
				int localId = eachMainOrder.getInt("localId");
				int midId = eachMainOrder.getInt("midId");
				long minAuthId = eachMainOrder.getLong("minAuthId");
				String[] betData = eachMainOrder.getString("betData").split(",");

				if (isBetDataToBaselineIndex(minAuthId) ? "1".equals(eachMainOrder.getString("lotteryLowfreq")) ? (betData.length > 1) : false
						: false) {
					JSONArray midOrders = eachMainOrder.getJSONArray("midOrders");
					Map<String, Map<String, String>> tmpRatios = ((Map) ((Map) ((Map) ((Map) ((Map) currentBetRatioLF.get("" + mainId))
							.get("" + localId)).get("" + midId)).get("" + minAuthId)).get("" + handiCap));

					LOG.error(tmpRatios.toString());
					BigDecimal baseline = BigDecimal.ZERO.setScale(5, BigDecimal.ROUND_DOWN);
					BigDecimal baseline2 = BigDecimal.ZERO.setScale(5, BigDecimal.ROUND_DOWN);
					for (int k = 0; k < betData.length; k++) {
						String betDataStr = ""+Integer.parseInt(betData[k]);
						if (tmpRatios.containsKey(betDataStr) == true) {
							Map<String, String> mapStr = (Map) tmpRatios.get(betDataStr);
							if (mapStr.containsKey("baseline")) {
								if(baseline.compareTo(BigDecimal.ZERO.setScale(5, BigDecimal.ROUND_DOWN)) == 0) {
									baseline = new BigDecimal(mapStr.get("baseline")).setScale(5, BigDecimal.ROUND_DOWN);
								}
								else {
									if (((new BigDecimal(mapStr.get("baseline")).setScale(5, BigDecimal.ROUND_DOWN).compareTo(baseline))) == -1) {
										baseline = new BigDecimal(mapStr.get("baseline")).setScale(5, BigDecimal.ROUND_DOWN);
									}
								}
							}
							if (isTwoBaselineIndex(minAuthId)) {
								if (tmpRatios.containsKey("" + (Integer.parseInt(betDataStr) + 49)) == true) {
									Map<String, String> mapStr2 = (Map) tmpRatios.get("" + (Integer.parseInt(betDataStr) + 49));
									if (mapStr2.containsKey("baseline")) {
										if(baseline2.compareTo(BigDecimal.ZERO.setScale(5, BigDecimal.ROUND_DOWN)) == 0) {
											baseline2 = new BigDecimal(mapStr2.get("baseline")).setScale(5, BigDecimal.ROUND_DOWN);
										}
										else {
											if (((new BigDecimal(mapStr2.get("baseline")).setScale(5, BigDecimal.ROUND_DOWN)
													.compareTo(baseline2))) == -1) {
												baseline2 = new BigDecimal(mapStr2.get("baseline")).setScale(5, BigDecimal.ROUND_DOWN);
											}
										}
										
										
									}
								}
							}

						}
					}

					for (int j1 = 0; j1 < midOrders.length(); j1++) {
						JSONArray betOrders = midOrders.getJSONObject(j1).getJSONArray("betOrders");
						for (int j2 = 0; j2 < betOrders.length(); j2++) {
							BigDecimal betOrderBaseline = betOrders.getJSONObject(j2).getBigDecimal("baseline").setScale(5, BigDecimal.ROUND_DOWN);
							if (betOrderBaseline.compareTo(baseline) != 0 && betOrderBaseline.compareTo(baseline2) != 0) {
								return false;
							}
						}                                                                                                                                                                                                                            

					}
				}
			}
			return true;
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		}
	}
	
	private void setBaselineLF(JSONObject orderObject, Map<String, Object> realBalacneLF) {
		JSONArray mainOrders = orderObject.getJSONArray("orders");
		for (int i = 0; i < mainOrders.length(); i++) {
			JSONObject eachMainOrder = (JSONObject) mainOrders.get(i);
			long minAuthId = eachMainOrder.getLong("minAuthId");
			if ("1".equals(eachMainOrder.getString("lotteryLowfreq")) ? isSpecialCheckLottery(minAuthId) : false) {
				
				LOG.debug(realBalacneLF.toString());
				LOG.debug(orderObject.toString());
				
				//eachMainOrder.put("baselineList", );
			}
		}
	}

}
