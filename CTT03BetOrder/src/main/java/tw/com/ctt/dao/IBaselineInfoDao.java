package tw.com.ctt.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface IBaselineInfoDao {
	public Map<String,Object> getBaselineNoFrontNumber();
	
	public long getBaselineLastUpdateTime();
	
public Map<String,Object> getBaselineNoFrontNumberLF();
	
	public long getBaselineLastUpdateTimeLF();
	
}
