package tw.com.ctt.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IGameRecordsDao;
import tw.com.ctt.service.IGameRecordsService;

public class GameRecordsServiceImpl extends BaseService implements IGameRecordsService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8874331767732753006L;
	private static final Logger LOG = LogManager.getLogger(GameRecordsServiceImpl.class.getName());

	public GameRecordsServiceImpl() {

	}

	public GameRecordsServiceImpl(long userId, String userIp) {
		super(userId, userIp);
	}

	@Override
	public int searchRecordsTotleCount(long gameId,String accName, String startTime, String endTime, int gameType) {
		return ((IGameRecordsDao) dao).searchRecordsTotleCount(USERID, gameId, accName, startTime, endTime, gameType);
	}
	
	@Override
	public List<Map<String, Object>> searchRecords(long gameId,String accName, String startTime, String endTime, int gameType, int firstCount, int count) {
		return ((IGameRecordsDao) dao).searchRecords(USERID, gameId, accName, startTime, endTime, gameType, firstCount, count);
	}
	
}
