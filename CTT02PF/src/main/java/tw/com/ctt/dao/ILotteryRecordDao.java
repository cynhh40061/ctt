package tw.com.ctt.dao;

import java.util.List;
import java.util.Map;

public interface ILotteryRecordDao {

	public List<Map<String, Object>> getLotteryLocalList();

	public int getLotteryRecordTotleCount(long accId, String memberAccName, int recordType, boolean checkBox, int localId, String startTime,
			String endTime);

	public List<Map<String, Object>> getLotteryRecord(long accId, String memberAccName, int recordType, int localId, String startTime, String endTime,
			int firstCount, int count);

}
