package tw.com.ctt.dao;

import java.util.List;
import java.util.Map;

public interface ILoginDao {
	
	public Map<String ,Object> checkPwd(String accName , String pwd , int platformId);
	
	public boolean updateLastLogin(String tableName , long userId, String ip);
	
	public boolean addLoginFailCount(long accId , int platformId,boolean isLogin);
	
	public List<Map<String ,Object>> getPlatformAd(int platformId);
	
}
