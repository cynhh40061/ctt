package tw.com.ctt.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IGetLotteryLowfreqInfoDao;
import tw.com.ctt.service.IGetLotteryLowfreqInfoSetService;

public class GetLotteryLowfreqInfoServiceImpl extends BaseService implements IGetLotteryLowfreqInfoSetService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4023228447140859781L;
	private static final Logger LOG = LogManager.getLogger(GetLotteryLowfreqInfoServiceImpl.class.getName());

	public GetLotteryLowfreqInfoServiceImpl() {

	}

	public GetLotteryLowfreqInfoServiceImpl(long userId, String userIp) {
		super(userId, userIp);
	}

	@Override
	public List<Map<String, Object>> getLotteryTypeList() {
		return ((IGetLotteryLowfreqInfoDao) dao).getLotteryTypeList();
	}

	@Override
	public List<Map<String, Object>> getLotteryMidList() {
		return ((IGetLotteryLowfreqInfoDao) dao).getLotteryMidList();
	}

	@Override
	public List<Map<String, Object>> getLotteryAmountPlayedList() {
		return ((IGetLotteryLowfreqInfoDao) dao).getLotteryAmountPlayedList();
	}

	@Override
	public List<Map<String, Object>> getHandicapInfo() {
		return ((IGetLotteryLowfreqInfoDao) dao).getHandicapInfo();
	}

	@Override
	public List<Map<String, Object>> getLowfreqBaseAmount(int handicap, int typeId) {
		return ((IGetLotteryLowfreqInfoDao) dao).getLowfreqBaseAmount(handicap, typeId);
	}

	@Override
	public List<Map<String, Object>> getLowfreqNowAmount(int handicap, int typeId) {
		return ((IGetLotteryLowfreqInfoDao) dao).getLowfreqNowAmount(handicap, typeId);
	}

	@Override
	public List<Map<String, Object>> getMark6BaseAmount(int handicap, int playedId, int midId) {
		return ((IGetLotteryLowfreqInfoDao) dao).getMark6BaseAmount(handicap, playedId, midId);
	}

	@Override
	public List<Map<String, Object>> getMark6NowAmount(int handicap, int playedId, int midId) {
		return ((IGetLotteryLowfreqInfoDao) dao).getMark6NowAmount(handicap, playedId, midId);
	}

	@Override
	public List<Map<String, Object>> getLowfreqAmountSettingLog() {
		return ((IGetLotteryLowfreqInfoDao) dao).getLowfreqAmountSettingLog(USERID);
	}
}
