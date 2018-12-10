package tw.com.ctt.dao;

import java.util.List;
import java.util.Map;

public interface ILotteryMemberRecordDao {

	public List<Map<String, Object>> getLotteryLocalList();

	public List<Map<String, Object>> searchMemDayRecord(long accId, String accName, int localId, String startTime, String endTime, String tableName);

	public List<Map<String, Object>> searchMemberRecordByDate(long accId, int localId, String startTime, String endTime, String tableName, int type);

}
