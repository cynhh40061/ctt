package tw.com.ctt.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface IMemberBetService {	
	public boolean addMainOrder(long accId, JSONObject orderObject, String dateOfTable);
	
	public Map<String, Object> searchRecordsTotleCount(String startTime, String endTime);

	public List<Map<String, Object>> searchRecords(String accName, String startTime, String endTime, int firstCount, int count);
	
	public boolean cancleAllPeriodOrder(long accId, long periodNum, int localId);
	
	public boolean cancleMainOrder(long accId, JSONObject orderObject);
	
	public boolean cancleMidOrder(long accId, JSONObject orderObject);
	
	public Map<String, Object> getMemMoney(long accId);
}
