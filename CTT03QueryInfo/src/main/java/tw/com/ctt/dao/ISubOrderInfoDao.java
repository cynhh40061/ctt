package tw.com.ctt.dao;

import java.util.List;
import java.util.Map;


public interface ISubOrderInfoDao {
	
	public List<Map<String, Object>> getSubOrderInfo();
	
	public List<Map<String, Object>> getSubOrderInfoNoOfBet();
	
	public Map<String,Object> getAllLottery();
	
	public Map<String, Map<String, String>> getHandicap(long accId);
	
	public long getAllLotteryLastUpdateTime();
	
	public Map<String,Object> getTitleAuth();

}
