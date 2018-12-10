package tw.com.ctt.service;

import java.util.List;
import java.util.Map;

public interface ILotteryMemberRecordService {

	public List<Map<String, Object>> getLotteryLocalList();

	public List<Map<String, Object>> searchMemberRecordByDate(long accId, int localId, String startTime, String endTime, int type);

	public List<Map<String, Object>> searchMemDayRecord(long accId, String accName, int localId, String startTime, String endTime, int type);

}
