package tw.com.ctt.service;

import java.util.List;
import java.util.Map;

public interface ILoginService {
	public Map<String,Object> login(String accName , String pwd , String ip , int platformId);
	
	public boolean logout(String tokenId , long accId , String ip);
	
	public List<Map<String ,Object>> getPlatformAd(int platformId);
}
