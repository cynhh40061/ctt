package tw.com.ctt.service.impl;

import java.math.BigDecimal;
import static tw.com.ctt.constant.AccountMemberConstant.*;

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

import static tw.com.ctt.constant.LogToDBConstant.*;

import tw.com.ctt.dao.IOrderInfoOfBettingDao;
import tw.com.ctt.model.MemBean;
import tw.com.ctt.service.IOrderInfoOfBettingService;
import tw.com.ctt.util.CommandUtil;
import tw.com.ctt.util.ShowLog;

public class OrderInfoOfBettingServiceImpl extends BaseService implements IOrderInfoOfBettingService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7588251442765528152L;
	private static final Logger LOG = LogManager.getLogger(OrderInfoOfBettingServiceImpl.class.getName());
	private static long USERID;
	private static String USERIP;

	public OrderInfoOfBettingServiceImpl() {
		this.USERID = 0;
		this.USERIP = "";
	}

	public OrderInfoOfBettingServiceImpl(long userId, String userIp) {
		this.USERID = userId;
		this.USERIP = userIp;
	}

	public OrderInfoOfBettingServiceImpl(String userIp) {
		this.USERIP = userIp;
	}
	
	/**
	 * getSubOrderInfo and resort the infos into map.
	 */
	@Override
	public Map<String, Object> getSubOrderInfo() {
		List<Map<String, Object>> ll = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new  ConcurrentHashMap<String, Object>();
		ll = ((IOrderInfoOfBettingDao) dao).getSubOrderInfo();
		for(int i = 0; i< ll.size(); i++) {			
			Map<String, String> tmpMap = new  ConcurrentHashMap<String, String>();
			tmpMap.put("note", ""+ll.get(i).get("note"));
			tmpMap.put("baselineIndex", ""+ll.get(i).get("baselineIndex"));
			map.put(""+ll.get(i).get("playedId"), tmpMap);
		}
		return map;
	}
	
	/**
	 * getSubOrderInfoNoOfBet and resort into map
	 */
	@Override
	public Map<String, Object> getSubOrderInfoNoOfBet() {
		List<Map<String, Object>> ll = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new  ConcurrentHashMap<String,Object>();
		ll = ((IOrderInfoOfBettingDao) dao).getSubOrderInfoNoOfBet();
		
		for(int i = 0; i< ll.size(); i++) {
			if(map.containsKey(""+ll.get(i).get("type"))) {
				((Map<String, String>)map.get(""+ll.get(i).get("type"))).put(""+ll.get(i).get("key"), ""+ll.get(i).get("value"));
			}else {
				Map<String, String> tmpMap = new  ConcurrentHashMap<String, String>();
				tmpMap.put(""+ll.get(i).get("key"), ""+ll.get(i).get("value"));
				map.put(""+ll.get(i).get("type"), tmpMap);
			}
		}
		return map;
	}
	
	/**
	 * getAllLottery
	 */
	@Override
	public Map<String, Object> getAllLottery() {
		return ((IOrderInfoOfBettingDao) dao).getAllLotteryNoFrontNumber();
	}
	
	/**
	 * checkRealTimeBaselinePeriodAndHandiCap
	 */
	@Override
	public boolean checkRealTimeBaselinePeriodAndHandiCap(long accId, long periodNum, long minAuthId, int localId, String dateOfTable, int handiCap, Map<String, Map<String, String>> tmpRatios) {
		return ((IOrderInfoOfBettingDao) dao).checkRealTimeBaselinePeriodAndHandiCap(accId, periodNum, minAuthId, localId, dateOfTable, handiCap, tmpRatios);
	}

	/**
	 * checkMidOrderStatusIsNormal
	 */
	@Override
	public boolean checkMidOrderStatusIsNormal(long periodNum, int localId, long mainOrderId, String dateOfTable) {
		return ((IOrderInfoOfBettingDao) dao).checkMidOrderStatusIsNormal(periodNum, localId, mainOrderId, dateOfTable);
	}

	/**
	 * checkMainOrderStatusIsNormal
	 */
	@Override
	public boolean checkMainOrderStatusIsNormal(long periodNum, int localId, long mainOrderId, String dateOfTable) {
		return ((IOrderInfoOfBettingDao) dao).checkMainOrderStatusIsNormal(periodNum, localId, mainOrderId, dateOfTable);
	}
	
	/**
	 * checkPeriodStatusIsNormal
	 */
	@Override
	public boolean checkPeriodStatusIsNormal(long periodNum, int localId, String dateOfTable) {
		return ((IOrderInfoOfBettingDao) dao).checkPeriodStatusIsNormal(periodNum, localId, dateOfTable);
	}
	
	/**
	 * getAllLotteryLastUpdateTime
	 */
	@Override
	public long getAllLotteryLastUpdateTime() {
		return ((IOrderInfoOfBettingDao) dao).getAllLotteryLastUpdateTime();
	}
	
	/**
	 * getAllHandicap
	 */
	@Override
	public Map<String, Object> getAllHandicap(){
		return ((IOrderInfoOfBettingDao) dao).getAllHandicap();
	}
}
