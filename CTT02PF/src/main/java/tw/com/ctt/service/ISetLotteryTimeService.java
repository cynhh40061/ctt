package tw.com.ctt.service;

import java.util.List;
import java.util.Map;

import tw.com.ctt.model.LotteryTimeSetBean;

public interface ISetLotteryTimeService {

	public Map<String, Object> getLotteryType();
	
	public List<Map<String,Object>> getLotteryTimeList(int id);
	
	public boolean saveLotteryTime(List<LotteryTimeSetBean> lotteryTimeSetBeanList );
	
	public List<Map<String,Object>> getLotteryTimeSetLog() ;
	

}
