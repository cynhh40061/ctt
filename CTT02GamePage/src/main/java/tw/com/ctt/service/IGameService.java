package tw.com.ctt.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IGameService {

	public Map<String, String> checkPwd(String userName, String pwd);

	public List<Map<String, Object>> getPlayer(long accId);
	
	public boolean updateRolePuncheGameAccInfo(long accId, String role);
	
	public List<Map<String, Object>> getPlayerRole(long accId);

}
