package tw.com.ctt.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface IKjInfoDao {
	public Map<String, Object> getAllKjTimeStatus();
	public Map<String, Object> getKjHistoryTimeStatus();
	public long getKjLastUpdateTime();	
}
