package tw.com.ctt.service;

import java.util.List;
import java.util.Map;

public interface IAuthService {

	public List<Map<String, Object>> getAuthGroup(long accId, int accLevelType);

	public List<Map<String, Object>> getAuthList();

	public List<Map<String, Object>> getGroupAuthList(int groupId);

	public List<Map<String, Object>> getUpLevelAuth();

	public int addAuth(int authLevelType, int level1, int level2, String authName, String authRemark, String url, int adminAuth);

	public boolean updateAuthGroup(int groupId, String[] authList);

	public boolean createAuthGroup(String groupText, String[] authList);

	public boolean checkAuthGroupName(String groupName);

	public List<Map<String, Object>> portopnGroupToAccLevel(int accLevelType);

	public boolean updateLevelTypeGroup(int levelType, String[] groupIdList);

	public boolean checkAuthRemarkText(String AuthRemarkText);
}
