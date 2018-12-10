package tw.com.ctt.dao;

import java.util.List;
import java.util.Map;

public interface ILotteryOperatorRecordDao {

	public List<Map<String, Object>> getLotteryLocalList();

	public List<Map<String, Object>> getTodayOperatorRecord(long accId);

	public List<Map<String, Object>> getTodayTotalOperatorRecord();

	public List<Map<String, Object>> searchOperatorRecordByDate(long accId, String accName, int localId, String dateTime, String tableName);

	public List<Map<String, Object>> searchBCRecord(long accId, int localId, String dateTime, String tableName);

	public List<Map<String, Object>> searchMemRecord(long accId, int localId, String dateTime, String tableName);

}
