package tw.com.ctt.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tw.com.ctt.model.MemBean;

public interface IMemberBetDao {
	
	public void writeCommit();
	
	public void writeRollback();
	
	public boolean cancleAllPeriodOrder(long accId, long periodNum, int localId);
	
	public boolean cancleMainOrder(long accId, long periodNum, int localId, long mainOrderId);
	
	public boolean cancleMidOrder(long accId, long periodNum, int localId, long mainOrderId);
	
	public long insertMainOrder(long accId, long startPeriodNum, long stopPeriodNum, String betData, long noOfPeriod, int localId, long minAuthId, Date actionTime,BigDecimal amount,BigDecimal moneyUnit, int orderType,int handiCap,int bonusSetRatio, int noOfWinningPeriod, int orderStatus,BigDecimal betRatio, boolean isDt, String dateOfTable, long totalNoOfBet);	
	
	public boolean insertMidOrder(long mainId, long midId, long accId, long periodNum, int localId, long minAuthId, BigDecimal amount,BigDecimal fanDen, int orderType, long noOfBet, int orderStatus, String dateOfTable,BigDecimal maxBonus, long noOfBetTimes);
	
	public boolean insertBetOrder(List<Map<String, Object>> subOrderList, String dateOfTable);
	
	public MemBean getAccDetailsForLog(long accId);

	public Map<String, Object> getMemMoney(long accId);
	
	public Map<String, Object> searchRecordsTotle(long accId, String startTime, String endTime);

	public List<Map<String, Object>> searchRecords(long accId, String accName, String startTime, String endTime,
			int firstCount, int count);

}
