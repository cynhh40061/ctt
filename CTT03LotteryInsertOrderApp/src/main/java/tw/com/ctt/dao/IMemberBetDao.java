package tw.com.ctt.dao;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import tw.com.ctt.model.MemBean;

public interface IMemberBetDao {
	
	public void writeCommit();
	
	public void writeRollback();
	
	public boolean cancleAllPeriodOrder(long accId, long periodNum, int localId);
	
	public boolean cancleMainOrderWhenCreateOrderFail(Map<String, Object> mainOrderList);
	
	public boolean cancleMainOrder(long accId, long periodNum, int localId, long mainOrderId);
	
	public boolean cancleMidOrder(long accId, long periodNum, int localId, long mainOrderId);
	
	public boolean setBettingStatistical(List<Map<String, Object>> mainOrderList);
	
	public void updateUserBalanceOfMainOrderBetting(List<Map<String, Object>> mainOrderList);
	
	public boolean insertMainOrder(List<Map<String, Object>> mainOrderList);
	
	public boolean insertMidOrder(List<Map<String, Object>> midOrderList);
	
	public boolean insertBetOrder(List<Map<String, Object>> subOrderList);
	
	public void updateUserBalanceOfMainOrderBetting_ExportFile(List<Map<String, Object>> mainOrderList, FileWriter fw);
	
	public boolean insertMainOrder_ExportFile(List<Map<String, Object>> mainOrderList, FileWriter fw);
	
	public boolean insertMidOrder_ExportFile(List<Map<String, Object>> midOrderList, FileWriter fw);
	
	public boolean insertBetOrder_ExportFile(List<Map<String, Object>> subOrderList, FileWriter fw);
	
	public boolean checkNumOfOrders(List<Map<String, Object>> mainOrderList);
	
	public MemBean getAccDetailsForLog(long accId);

	public Map<String, Object> getMemMoney(long accId);
	
	public int updateBetRatio(BigDecimal amount, int local_id, int lottery_type_id,int lottery_mid_id, long lottery_min_id, long period_num, String dateOfTable);
	
	public int updateBetRatioLF(BigDecimal amount, int local_id, int lottery_type_id,int lottery_mid_id, long lottery_min_id, long period_num, String dateOfTable, int noOfPeriod);
	
	public boolean updateBetRatio_ExportFile(List<Map<String, Object>> updateOrderList, FileWriter fw );
	
	public boolean checkCurrentBetRatio();
	
	public boolean checkRealTimeBaselinePeriodAndHandiCap(long accId, long periodNum, long minAuthId, int localId, String dateOfTable, int handiCap, Map<String, Map<String, String>> tmpRatios);

	public long getNextAutoIncrementId (String tableName, String dbName);
	
}
