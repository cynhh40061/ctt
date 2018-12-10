package tw.com.ctt.service.impl;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.action.QueryBetInfoAction;
import tw.com.ctt.dao.IBaselineInfoDao;
import tw.com.ctt.service.IBaselineInfoService;
import tw.com.ctt.util.ShowLog;

public class BaselineInfoServiceImpl extends BaseService implements IBaselineInfoService {
	private static final long serialVersionUID = 7588251442765528152L;
	private static final Logger LOG = LogManager.getLogger(QueryBetInfoAction.class.getName());
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
	
	/**
	 * getBaseline (high frequency)
	 */
	@Override
	public Map<String,Object> getBaseline(){
		 return ((IBaselineInfoDao) dao).getBaseline();
	}
	
	/**
	 * getBaselineLF (low frequency)
	 */
	@Override
	public Map<String,Object> getBaselineLF(){
		 return ((IBaselineInfoDao) dao).getBaselineLF();
	}
	
	/**
	 * getBaselineLastUpdateTime (high frequency)
	 */
	@Override
	public long getBaselineLastUpdateTime() {
		return ((IBaselineInfoDao) dao).getBaselineLastUpdateTime();
	}
	
	/**
	 * getBaselineLastUpdateTimeLF (low frequency)
	 */
	@Override
	public long getBaselineLastUpdateTimeLF() {
		return ((IBaselineInfoDao) dao).getBaselineLastUpdateTimeLF();
	}
}
