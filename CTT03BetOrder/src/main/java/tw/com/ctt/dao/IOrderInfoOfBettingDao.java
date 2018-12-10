package tw.com.ctt.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tw.com.ctt.model.MemBean;

public interface IOrderInfoOfBettingDao {
	
	public List<Map<String, Object>> getSubOrderInfo();
	
	public List<Map<String, Object>> getSubOrderInfoNoOfBet();
	
	public Map<String, Object> getAllLotteryNoFrontNumber();
	
	public boolean checkRealTimeBaselinePeriodAndHandiCap(long accId, long periodNum, long minAuthId, int localId, String dateOfTable, int handiCap, Map<String, Map<String, String>> tmpRatios);
	
	public boolean checkPeriodStatusIsNormal(long periodNum, int localId, String dateOfTable);
	
	public boolean checkMidOrderStatusIsNormal(long periodNum, int localId, long mainOrderId, String dateOfTable);
	
	public boolean checkMainOrderStatusIsNormal(long periodNum, int localId, long mainOrderId, String dateOfTable);
	
	public long getAllLotteryLastUpdateTime();
	
	public Map<String, Object> getAllHandicap();
}
