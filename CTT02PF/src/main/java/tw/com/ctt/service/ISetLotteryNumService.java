package tw.com.ctt.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ISetLotteryNumService {

	public List<Map<String,Object>> getLotteryListData(int id , String searchDate);
	
	public boolean setLotteryNum(int id  ,String periodNum , String data);
	
	public boolean setLotteryFali(int id ,String periodNum , String data);
	
	public boolean deletePeriodNum(int id, String periodNum);
	
	public boolean withdrawalPeriodNum(int id, String periodNum);
	
	public boolean callCheckTheLottery(int id ,long periodNum);
	
	public List<Map<String, Object>> getLotteryNumSetLog(int logAction, String startTime, String endTime);
	
	public boolean recoverPeriodNumWinningMoney(int gameId , long periodNum);
	
	public boolean renewCallCheckTheLottery(int id ,long periodNum);
	
	public Map<String, Object> getNextAndPreviousLotteryListData(int id);
}
