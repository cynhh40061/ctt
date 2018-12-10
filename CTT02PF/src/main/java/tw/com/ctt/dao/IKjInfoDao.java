package tw.com.ctt.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface IKjInfoDao {
	
	public Map<String, Object> getAllKjTimeStatusNoFrontNumber();
	
	public boolean checkKjTimeStatus(long periodNum, int localId);
	
	public long getKjLastUpdateTime();
	
}
