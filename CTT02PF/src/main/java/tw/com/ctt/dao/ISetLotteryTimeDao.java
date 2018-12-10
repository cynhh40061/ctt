package tw.com.ctt.dao;

import java.util.List;
import java.util.Map;

import tw.com.ctt.model.LotteryTimeSetBean;

public interface ISetLotteryTimeDao {
	public Map<String, Object> getLotteryType();
	
	public List<Map<String,Object>> getLotteryTimeList(int id);
	
	public List<LotteryTimeSetBean> getLotteryTimeListToBean(int id);

	public int selectLotterySettingCount(int id);
	
	public boolean deleteLotterySetting(int id);
	
	public boolean insertLotteryTime(List<LotteryTimeSetBean> lotteryTimeSetBeanList) ;
	
	public List<Map<String ,Object>> getLotteryTimeSetLog(long accId);
	
	public boolean updateLotteryType(int id , int createPeriodType , int zodiacType) ;
	
	
}
