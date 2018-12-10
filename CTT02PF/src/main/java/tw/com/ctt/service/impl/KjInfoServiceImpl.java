package tw.com.ctt.service.impl;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IKjInfoDao;
import tw.com.ctt.service.IKjInfoService;

public class KjInfoServiceImpl extends BaseService implements IKjInfoService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7588251442765528152L;
	private static final Logger LOG = LogManager.getLogger(KjInfoServiceImpl.class.getName());
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

	@Override
	public Map<String, Object> getAllKjTimeStatus() {
		return ((IKjInfoDao) dao).getAllKjTimeStatusNoFrontNumber();
	}

	@Override
	public boolean checkKjTimeStatus(long periodNum, int localId) {
		return ((IKjInfoDao) dao).checkKjTimeStatus(periodNum, localId);
	}

	@Override
	public long getKjLastUpdateTime() {
		return ((IKjInfoDao) dao).getKjLastUpdateTime();
	}

}
