package tw.com.ctt.service.impl;

import java.math.BigDecimal;
import static tw.com.ctt.constant.AccountMemberConstant.*;

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

import static tw.com.ctt.constant.LogToDBConstant.*;

import tw.com.ctt.action.MemberBetAction;
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
	private static final Logger LOG = LogManager.getLogger(MemberBetAction.class.getName());
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

	/**
	 * checkRealTimeBaselinePeriodAndHandiCap
	 */
	@Override
	public boolean checkRealTimeBaselinePeriodAndHandiCap(long accId, long periodNum, long minAuthId, int localId,
			String dateOfTable, int handiCap, Map<String, Map<String, String>> tmpRatios) {
		return ((IMemberBetDao) dao).checkRealTimeBaselinePeriodAndHandiCap(accId, periodNum, minAuthId, localId,
				dateOfTable, handiCap, tmpRatios);
	}
	
	/**
	 * checkRealTimeBaselinePeriodAndHandiCap (Low freqyency)
	 */
	@Override
	public boolean checkRealTimeBaselinePeriodAndHandiCapLF(long accId, long periodNum, long minAuthId, int localId,
			String dateOfTable, int handiCap,Map<String, Map<String, Map<String, String>>> tmpRatios) {
		return ((IMemberBetDao) dao).checkRealTimeBaselinePeriodAndHandiCapLF(accId, periodNum, minAuthId, localId,
				dateOfTable, handiCap, tmpRatios);
	}

	/**
	 * cancle All orders of specific period.
	 */
	@Override
	public boolean cancleAllPeriodOrder(long accId, long periodNum, int localId) {
		return ((IMemberBetDao) dao).cancleAllPeriodOrder(accId, periodNum, localId);
	}

	/**
	 * cancle specific main order and its mid order and sub orders.
	 */
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

	/**
	 * cancle specific mid order and its sub orders.
	 */
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

	/**
	 * getMemMoney
	 */
	@Override
	public Map<String, Object> getMemMoney(long accId) {
		return ((IMemberBetDao) dao).getMemMoney(accId);
	}
}
