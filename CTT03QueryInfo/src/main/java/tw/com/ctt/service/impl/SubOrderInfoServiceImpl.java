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

import tw.com.ctt.action.QueryBetInfoAction;
import tw.com.ctt.dao.ISubOrderInfoDao;
import tw.com.ctt.service.ISubOrderInfoService;
import tw.com.ctt.util.CommandUtil;
import tw.com.ctt.util.ShowLog;

public class SubOrderInfoServiceImpl extends BaseService implements ISubOrderInfoService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7588251442765528152L;
	private static final Logger LOG = LogManager.getLogger(QueryBetInfoAction.class.getName());
	private static long USERID;
	private static String USERIP;

	public SubOrderInfoServiceImpl() {
		this.USERID = 0;
		this.USERIP = "";
	}

	public SubOrderInfoServiceImpl(long userId, String userIp) {
		this.USERID = userId;
		this.USERIP = userIp;
	}

	public SubOrderInfoServiceImpl(String userIp) {
		this.USERIP = userIp;
	}
	
	@Override
	public Map<String, Object> getSubOrderInfo() {
		List<Map<String, Object>> ll = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new  ConcurrentHashMap<String, Object>();
		ll = ((ISubOrderInfoDao) dao).getSubOrderInfo();
		
		for(int i = 0; i< ll.size(); i++) {
			Map<String, String> tmpMap = new  ConcurrentHashMap<String, String>();
			tmpMap.put("playedId", ""+ll.get(i).get("playedId"));
			tmpMap.put("note", ""+ll.get(i).get("note"));
			tmpMap.put("baselineIndex", ""+ll.get(i).get("baselineIndex"));
			map.put(""+ll.get(i).get("playedName"), tmpMap);
		}
		return map;
	}
	
	/**
	 * getSubOrderInfoNoOfBet and arrange to map
	 */
	@Override
	public Map<String, Object> getSubOrderInfoNoOfBet() {
		List<Map<String, Object>> ll = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new  ConcurrentHashMap<String,Object>();
		ll = ((ISubOrderInfoDao) dao).getSubOrderInfoNoOfBet();
		
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
	public Map<String,Object> getAllLottery() {
		return ((ISubOrderInfoDao) dao).getAllLottery();
	}
	
	/**
	 * getTitleAuth
	 */
	@Override
	public Map<String,Object> getTitleAuth() {
		return ((ISubOrderInfoDao) dao).getTitleAuth();
	}
	
	/**
	 * getHandicap of specific player
	 */
	@Override
	public Map<String, Map<String, String>> getHandicap(long accId){
		return ((ISubOrderInfoDao) dao).getHandicap(accId);
	}
	
	/**
	 * getAllLotteryLastUpdateTime
	 */
	public long getAllLotteryLastUpdateTime() {
		return ((ISubOrderInfoDao) dao).getAllLotteryLastUpdateTime();
	}

}
