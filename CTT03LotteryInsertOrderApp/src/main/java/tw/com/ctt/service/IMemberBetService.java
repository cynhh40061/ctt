package tw.com.ctt.service;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import tw.com.ctt.dao.IOrderInfoOfBettingDao;

public interface IMemberBetService {	
	public boolean addMainOrder(List<String> cmdListFromRedis);
	
	public boolean cancleAllPeriodOrder(long accId, long periodNum, int localId);
	
	public boolean cancleMainOrder(long accId, JSONObject orderObject);
	
	public boolean cancleMidOrder(long accId, JSONObject orderObject);
	
	public Map<String, Object> getMemMoney(long accId);
	
	public int addMainOrderBetRatio(List<String> cmdListFromRedis);
	
	public boolean addMainOrder_ExportFile(List<String> cmdListFromRedis, FileWriter fw);
	
	public boolean addMainOrderBetRatio_ExportFile(List<String> cmdListFromRedis, FileWriter fw);
	
	public boolean checkRealTimeBaselinePeriodAndHandiCap(long accId, long periodNum, long minAuthId, int localId, String dateOfTable, int handiCap, Map<String, Map<String, String>> tmpRatios);
	
}
