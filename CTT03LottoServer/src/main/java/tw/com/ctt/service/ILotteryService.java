package tw.com.ctt.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import tw.com.ctt.model.LotteryNumBean;
import tw.com.ctt.model.LotterySettingBean;
import tw.com.ctt.model.LotteryTimeSetBean;
import tw.com.ctt.model.LotteryTypeBean;

public interface ILotteryService {
	
	public boolean checkLotteryData(Date date,int id);
	
	public List<LotteryNumBean> selectLotteryData(Date date,int id);
	
	public List<LotteryNumBean> selectLotteryData(Date startTime ,Date endTime , int id);
	
	public void addLotteryData(List<LotteryNumBean> beanList);
	
	public void updateLotteryData(List<LotteryNumBean> beanList);
	
	public void addSettingLottery(List<Map<String ,Object>> listMap);

	public List<LotteryTypeBean> selectLotteryBlastType();
	
	public long selectMaxIssue(int id);
	
	public List<LotterySettingBean> selectLotterySettingData(int id);
	
	public boolean checkIssueIsLottery (int id,long issue);
	
	public boolean addPairAward(int id ,long periodNum ,Map<String ,String> answerMap);
	
	public Date getLastBettingTime(int id);
	
	public boolean createOrderTable(Date date);
	
	public boolean checkLotteryCurrentRatio(Date date);
	
	public boolean insertLotteryCurrentRatio(Date date);
	
	public List<Map<String ,Object>> selectNowKjPeriodNum();
	
	public boolean createLotteryMonthAndWeekStatistical();
	
	public boolean isCreateLotteryStatistical();
	
	public boolean callMainOrderAfterGiveUp(int lotetryId , long periodNum) ;
	
	public List<Map<String, Object>> selectGiveUpPeriodNum();
	
	public List<LotteryNumBean> getLotteryData(int id);
	
	public long getTimeDiffFromDB();
	
	public boolean updateLotteryStatisticalReport();
	
	public boolean checkThisWeekLotteryData(int id);
	
	public boolean checkNextWeekLotteryData(int id);
	
	public boolean checkCreatPeriodThisWeekData(int id);
	
	public List<LotterySettingBean> getNextInsertWeekPeriod(int id);
	
	public List<LotterySettingBean> getThisWeekInsertWeekPeriod(int id);
	
	public boolean saveLotteryTime(List<LotteryTimeSetBean> lotteryTimeSetBeanList);
	
	public boolean inserLlowfreqLotteryCurrentRatio(int id ,String periodNum , Date date);
	
}
