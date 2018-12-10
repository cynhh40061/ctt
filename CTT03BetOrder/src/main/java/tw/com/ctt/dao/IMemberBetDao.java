package tw.com.ctt.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import tw.com.ctt.model.MemBean;

public interface IMemberBetDao {
	
	public void writeCommit();
	
	public void writeRollback();
	
	public boolean cancleAllPeriodOrder(long accId, long periodNum, int localId);
	
	public boolean cancleMainOrder(long accId, long periodNum, int localId, long mainOrderId);
	
	public boolean cancleMidOrder(long accId, long periodNum, int localId, long mainOrderId);
	
	public MemBean getAccDetailsForLog(long accId);

	public Map<String, Object> getMemMoney(long accId);

	public boolean checkRealTimeBaselinePeriodAndHandiCap(long accId, long periodNum, long minAuthId, int localId, String dateOfTable, int handiCap, Map<String, Map<String, String>> tmpRatios);
	
	public boolean checkRealTimeBaselinePeriodAndHandiCapLF(long accId, long periodNum, long minAuthId, int localId, String dateOfTable, int handiCap, Map<String, Map<String, Map<String, String>>> tmpRatios);

	public long getNextAutoIncrementId (String tableName, String dbName);
	
}
