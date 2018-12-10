package tw.com.ctt.service;

import java.util.List;
import java.util.Map;

public interface IPortionAuthService {
	public boolean getAuthFunction(long accId , String url , int acc_level_type);
	
	public List<Map<String ,Object>> getLevel3Auth(String url);
}
