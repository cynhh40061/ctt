package tw.com.ctt.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import tw.com.ctt.model.LotteryNumBean;
import tw.com.ctt.model.LotterySettingBean;
import tw.com.ctt.model.LotteryTimeSetBean;
import tw.com.ctt.model.LotteryTypeBean;

public interface ILotteryDao {
	
	public boolean checkLotteryDate(Date date,int id) throws Exception;
	
	public List<LotteryNumBean> selectLotteryData(Date date,int id) throws Exception;
	
	public List<LotteryNumBean> selectLotteryData(Date startTime ,Date endTime , int id) throws Exception;
	
	public boolean addLotteryData(List<LotteryNumBean> bean) throws Exception;
	
	public boolean updateLotteryData(List<LotteryNumBean> bean) throws Exception;
	
	public boolean addSettingLottery(List<Map<String ,Object>> listMap) throws Exception;

	public List<LotterySettingBean> selectLotterySettingData(int id) throws Exception;
	
	public List<LotteryTypeBean> selectLotteryBlastType() throws Exception;
	
	public long selectMaxIssue(int id) throws Exception;
	
	public boolean addPairAward(String tableName , long periodNum , Map<String ,String> answerMap) throws Exception;
	
	public boolean checkIssueIsLottery (int id,long issue) throws Exception;
	
	public boolean callProcedure(long periodNum , int lotetryId , String tableName) throws Exception;
	
	public Date getLastBettingTime(int id) throws Exception;
	
	public boolean createMainOrderTable(String dataBase, String tableName) throws Exception;
	
	public boolean createMidOrderTable(String dataBase, String tableName) throws Exception;
	
	public boolean createBetOrderTable(String dataBase, String tableName) throws Exception;
	
	public boolean createWinningOrderTable(String dataBase, String tableName) throws Exception;
	
	public boolean checkTableIsExists(String dataBase, String tableName) throws Exception;
	
	public boolean checkLotteryCurrentRatio(Date date) throws Exception;
	
	public boolean insertLotteryCurrentRatio(Date date) throws Exception;
	
	public List<Map<String ,Object>> selectNowKjPeriodNum() throws Exception;
	
	public boolean goCheckLottery(int id,long periodNum) throws Exception; 
	
	public boolean createLotteryMonthAndWeekStatistical() throws Exception;
	
	public boolean isCreateLotteryStatistical() throws Exception;
	
	public boolean callMainOrderAfterGiveUp( int lotetryId,long periodNum) throws Exception;
	
	public List<Map<String, Object>> selectGiveUpPeriodNum() throws Exception;
	
	public boolean goGiveUpPeriodNum(int id , long periodNum) throws Exception;
	
	public List<LotteryNumBean> getLotteryData(int id) throws Exception;
	
	public long getTimeDiffFromDB() throws Exception;
	
	public boolean updateLotteryStatisticalReportData() throws Exception;
	
	public boolean checkWeekLotteryData(int id,int startWeek,int endWeek) throws Exception;

	public boolean checkCreatPeriodThisWeekData(int id) throws Exception;
	
	public List<LotterySettingBean> getNextInsertWeekPeriod(int id) throws Exception;
	
	public List<LotterySettingBean> getThisWeekInsertWeekPeriod(int id) throws Exception;
	
	public boolean deleteLotterySetting(int id);
	
	public boolean insertLotteryTime(List<LotteryTimeSetBean> lotteryTimeSetBeanList);
	
	public boolean updateLotteryType(int id , int createPeriodType);
	
	public boolean inserLlowfreqLotteryCurrentRatio(int id ,String periodNum , Date date) throws Exception;
	
	 
	
}
