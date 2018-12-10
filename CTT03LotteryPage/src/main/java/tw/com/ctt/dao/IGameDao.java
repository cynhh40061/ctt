package tw.com.ctt.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IGameDao {

	public Map<String, String> checkPwd(String userName, String pwd);
	
	public List<Map<String, Object>> getPlayer(long accId);

	
	public boolean withdraw (long accId, BigDecimal money) throws SQLException;
	
	public boolean deposit (long accId, BigDecimal money) throws SQLException;

	public boolean updateRolePuncheGameAccInfo(long accId, String role) throws SQLException;
	
	public List<Map<String, Object>> getPlayerRole(long accId) throws SQLException;
		
}
