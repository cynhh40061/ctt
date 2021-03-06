package tw.com.ctt.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IBaselineInfoDao;
import tw.com.ctt.service.IBaselineInfoService;
import tw.com.ctt.util.ShowLog;

public class BaselineInfoServiceImpl extends BaseService implements IBaselineInfoService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7588251442765528152L;
	private static final Logger LOG = LogManager.getLogger("BaselineInfoServiceImpl");
	private static long USERID;
	private static String USERIP;

	public BaselineInfoServiceImpl() {
		this.USERID = 0;
		this.USERIP = "";
	}

	public BaselineInfoServiceImpl(long userId, String userIp) {
		this.USERID = userId;
		this.USERIP = userIp;
	}

	public BaselineInfoServiceImpl(String userIp) {
		this.USERIP = userIp;
	}
	
	@Override
	public Map<String,Map<String,Map<String,Map<String, Map<String, Map<String, Map<String, String>>>>>>> getBaseline(){
		 return ((IBaselineInfoDao) dao).getBaselineNoFrontNumber();
	}
	
	@Override
	public long getBaselineLastUpdateTime() {
		return ((IBaselineInfoDao) dao).getBaselineLastUpdateTime();
	}
}
