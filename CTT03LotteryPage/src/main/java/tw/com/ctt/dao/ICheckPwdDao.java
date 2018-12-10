package tw.com.ctt.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ICheckPwdDao {

	public Map<String, String> checkPwd(String userName, String pwd);
	
	public List<Map<String, Object>> getAuth(String accId);

	public boolean updateLastLogin (int userId, String ip) throws SQLException;
}
