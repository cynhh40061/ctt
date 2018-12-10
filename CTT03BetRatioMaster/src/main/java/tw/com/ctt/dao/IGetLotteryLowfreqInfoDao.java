package tw.com.ctt.dao;

import java.util.List;
import java.util.Map;

public interface IGetLotteryLowfreqInfoDao {

	public List<Map<String, Object>> getLotteryTypeList();

	public List<Map<String, Object>> getLotteryAmountPlayedList();

	public List<Map<String, Object>> getHandicapInfo();

	public List<Map<String, Object>> getLowfreqBaseAmount(int handicap, int typeId);

	public List<Map<String, Object>> getLowfreqNowAmount(int handicap, int typeId);

	public List<Map<String, Object>> getMark6BaseAmount(int handicap, int playedId, int midId);

	public List<Map<String, Object>> getMark6NowAmount(int handicap, int playedId, int midId);

	public List<Map<String, Object>> getLotteryMidList();

	public List<Map<String, Object>> getLowfreqAmountSettingLog(long opsAccId);

}
