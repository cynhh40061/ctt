package tw.com.ctt.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface ISubOrderInfoService {
	public Map<String, Object> getSubOrderInfo();
	
	public Map<String, Object> getSubOrderInfoNoOfBet();
	
	public Map<String,Object> getAllLottery();
	
	public  Map<String, Map<String, String>> getHandicap(long accId);
	
	public long getAllLotteryLastUpdateTime();
	
	public Map<String,Object> getTitleAuth();
	
}
