package tw.com.ctt.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface IOrderInfoOfBettingService {
	public Map<String , Object>  getMemAccData(String tokenId, long accId) ;
	
	public Map<String, Object> getSubOrderInfo();
	
	public Map<String, Object> getSubOrderInfoNoOfBet();
	
	public Map<String, Object> getAllLottery();
	
	public boolean checkRealTimeBaselinePeriodAndHandiCap(long accId, long periodNum, long minAuthId, int localId, String dateOfTable, int handiCap,  Map<String, Map<String, String>> tmpRatios);
	
	public boolean checkPeriodStatusIsNormal(long periodNum, int localId, String dateOfTable);
	
	public boolean checkMidOrderStatusIsNormal(long periodNum, int localId, long mainOrderId, String dateOfTable);
	
	public boolean checkMainOrderStatusIsNormal(long periodNum, int localId, long mainOrderId, String dateOfTable);
	
	public long getAllLotteryLastUpdateTime();
	
	public Map<String, Object> getAllHandicap();
}
