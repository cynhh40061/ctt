package tw.com.ctt.service;

import java.util.List;
import java.util.Map;

public interface ILotteryBetOrderQueryService {
	public List<Map<String, Object>> getLotteryLocalList();

	public String getNowDBTime();

	public int getMidTotleCount(long accId, Map<String, Object> map);

	public int getMainTotleCount(long accId, Map<String, Object> map);

	public Map<String, Object> searchMidBetOrder(long accId, Map<String, Object> map, int firstCount, int count);

	public Map<String, Object> searchMainBetOrder(long accId, Map<String, Object> map, int firstCount, int count);

}
