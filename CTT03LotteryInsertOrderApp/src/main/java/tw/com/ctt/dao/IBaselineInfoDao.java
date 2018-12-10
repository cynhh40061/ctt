package tw.com.ctt.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface IBaselineInfoDao {
	public Map<String,Map<String,Map<String,Map<String, Map<String, Map<String, Map<String, String>>>>>>> getBaselineNoFrontNumber();
	
	public long getBaselineLastUpdateTime();
	
}
