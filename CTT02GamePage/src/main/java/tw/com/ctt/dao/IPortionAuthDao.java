package tw.com.ctt.dao;

import java.util.List;
import java.util.Map;

public interface IPortionAuthDao {
	public boolean getAuthFunction(long accId , String url  ,int acc_level_type);
	
	public List<Map<String ,Object>> getLevel3Auth(String url);
	
}
