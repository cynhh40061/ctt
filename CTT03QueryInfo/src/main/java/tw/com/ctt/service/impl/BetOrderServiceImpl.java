package tw.com.ctt.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.action.QueryBetInfoAction;
import tw.com.ctt.dao.IBetOrderDao;
import tw.com.ctt.service.IBetOrderService;
import tw.com.ctt.service.IKjInfoService;
import tw.com.ctt.util.ShowLog;

public class BetOrderServiceImpl extends BaseService implements IBetOrderService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7588251442765528152L;
	private static final Logger LOG = LogManager.getLogger(QueryBetInfoAction.class.getName());
	private static long USERID;
	private static String USERIP;

	public BetOrderServiceImpl() {
		this.USERID = 0;
		this.USERIP = "";
	}

	public BetOrderServiceImpl(long userId, String userIp) {
		this.USERID = userId;
		this.USERIP = userIp;
	}

	public BetOrderServiceImpl(String userIp) {
		this.USERIP = userIp;
	}
	
	@Override
	public Map<String,Object> getBetOrderInfo(long accId) {
		return ((IBetOrderDao) dao).getBetOrderInfo(accId);
	}

}
