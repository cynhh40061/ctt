package tw.com.ctt.service;

import java.util.List;
import java.util.Map;

public interface ILotteryOperatorRecordService {

	public List<Map<String, Object>> getLotteryLocalList();

	public List<Map<String, Object>> getTodayOperatorRecord(long accId, int level);

	public List<Map<String, Object>> searchOperatorRecordByDate(long accId, String accName, int localId, String dateTime, int type);

	public List<Map<String, Object>> searchBCRecord(long accId, int localId, String dateTime, int type);

	public List<Map<String, Object>> searchMemRecord(long accId, int localId, String dateTime, int type);

}
