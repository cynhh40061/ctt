package tw.com.ctt.service;

import java.util.List;
import java.util.Map;

public interface ILotteryRecordService {

	public List<Map<String, Object>> getLotteryLocalList();

	public int getLotteryRecordTotleCount(long accId, Map<String, Object> map);

	public List<Map<String, Object>> getLotteryRecord(long accId, Map<String, Object> map, int firstCount, int count);

}
