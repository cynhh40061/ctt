package tw.com.ctt.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ISetLotteryNumDao {
	public List<Map<String,Object>> getLotteryListData(int id , String searchDate);
	
	public boolean setLotteryNum(int id  ,String periodNum , String data);
	
	public boolean setLotteryFali(int id ,String periodNum , String data);
	
	public boolean deletePeriodNum (int id ,List<String> periodNumList);
	
	public boolean withdrawalPeriodNum (int id ,String periodNum);
	
	public boolean cancleAllPeriodOrder(long periodNum, int lotteryId) ;
	
	public boolean insertLotteryLog(int id ,String periodNum , int operatingStatus);
	
	public boolean callCheckTheLottery(int id ,long periodNum,String tableName);
	
	public List<Integer> selectLotteryNum(int id , long periodNum );
	
	public boolean goCheckLottery(int id,long periodNum);
	
	public boolean addPairAward(String tableName, long periodNum, Map<String, String> answerMap);
	
	public List<String> selectDeletePeriodNum(int id , String periodNum);
	
	public List<Map<String ,Object>> selectPeriodNumData(int id , List<String> periodNumList);
	
	public List<Map<String,Object>> getLotteryNumSetLog(long accId , int logAction , String startTime , String endTime);
	
	public boolean refundWinningMoney(int gameId , long periodNum);
	
	public boolean callRecoverPeriodNumWinningMoney(int gameId , long periodNum);
	
	public boolean renewCallCheckTheLottery( int lotetryId,long periodNum, String tableName);
	
	public boolean renewGoCheckLottery(int id,long periodNum);
	
	public Map<String, Object> getNextAndPreviousLotteryListData(int id);
	
}
