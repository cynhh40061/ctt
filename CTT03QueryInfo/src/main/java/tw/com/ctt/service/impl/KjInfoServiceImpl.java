package tw.com.ctt.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.action.QueryBetInfoAction;
import tw.com.ctt.dao.IKjInfoDao;
import tw.com.ctt.service.IKjInfoService;
import tw.com.ctt.util.ShowLog;

public class KjInfoServiceImpl extends BaseService implements IKjInfoService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7588251442765528152L;
	private static final Logger LOG = LogManager.getLogger(QueryBetInfoAction.class.getName());
	private static long USERID;
	private static String USERIP;

	public KjInfoServiceImpl() {
		this.USERID = 0;
		this.USERIP = "";
	}

	public KjInfoServiceImpl(long userId, String userIp) {
		this.USERID = userId;
		this.USERIP = userIp;
	}

	public KjInfoServiceImpl(String userIp) {
		this.USERIP = userIp;
	}
	
	/**
	 * getAllKjTimeStatus
	 */
	@Override
	public Map<String, Object> getAllKjTimeStatus() {
		return ((IKjInfoDao) dao).getAllKjTimeStatus();
	}
	
	/**
	 * get all of the kj data of 7 days before today.
	 */
	@Override
	public Map<String, Object> getKjHistoryTimeStatus() {
		return ((IKjInfoDao) dao).getKjHistoryTimeStatus();
	}
	
	/**
	 * getKjLastUpdateTime
	 */
	@Override
	public long getKjLastUpdateTime() {
		return ((IKjInfoDao) dao).getKjLastUpdateTime();
	}

}
