package tw.com.ctt.service;

import java.util.List;
import java.util.Map;

public interface ICheckPwdService {

	public Map<String, String> checkPwd(String userName, String pwd);

	public List<Map<String, Object>> getAuth(String accId);

	public boolean updateMemberLastLogin(int userId, String ip);

	public boolean updateManagerLastLogin(int userId, String ip);

	public boolean login(Map<String, Object> result, String userName, String pwd, String userIp);

	public boolean logout(long accId, String userName, String tokenId, String userIp);

}
