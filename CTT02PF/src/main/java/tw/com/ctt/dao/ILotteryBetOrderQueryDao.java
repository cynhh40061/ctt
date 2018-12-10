package tw.com.ctt.dao;

import java.util.List;
import java.util.Map;

public interface ILotteryBetOrderQueryDao {
	public List<Map<String, Object>> getLotteryLocalList();

	public int getMidTotleCountBySingleTable(long accId, String tableDate, String orderId, String accName, int lotteryLocal, String periodNum,
			String startTime, String endTime);

	public int getMidTotleCountByUnionTable(long accId, List<String> tableDateList, String accName, int lotteryLocal, String startTime,
			String endTime);

	public int getMainTotleCountBySingleTable(long accId, String tableDate, String orderId, String accName, int lotteryLocal, String startTime,
			String endTime, int lotteryStatus);

	public int getMainTotleCountByUnionTable(long accId, List<String> tableDateList, String orderId, String accName, int lotteryLocal,
			String startTime, String endTime, int lotteryStatus);

	public Map<String, Object> searchMidBySingleTable(long accId, String tableDate, String orderId, String accName, int lotteryLocal,
			String periodNum, String startTime, String endTime, int firstCount, int count);

	public Map<String, Object> searchMidByUnionTable(long accId, List<String> tableDateList, String accName, int lotteryLocal, String startTime,
			String endTime, int firstCount, int count);

	public Map<String, Object> searchMainBySingleTable(long accId, String tableDate, String orderId, String accName, int lotteryLocal,
			String startTime, String endTime, int lotteryStatus, int firstCount, int count);

	public Map<String, Object> searchMainByUnionTable(long accId, List<String> tableDateList, String orderId, String accName, int lotteryLocal,
			String startTime, String endTime, int lotteryStatus, int firstCount, int count);

	public List<String> getTableDate(String database, String format);

	public String getNowDBTime();

}
