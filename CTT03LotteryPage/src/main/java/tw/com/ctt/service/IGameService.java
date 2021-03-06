package tw.com.ctt.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IGameService {

	public Map<String, String> checkPwd(String userName, String pwd);

	public List<Map<String, Object>> getPlayer(long accId);

	public boolean withdraw(long accId, BigDecimal money);

	public boolean deposit(long accId, BigDecimal money);
	
	public BigDecimal withdrawAllMoneyToGame(long accId);
	
	public boolean depositAllMoneyFromGame(long accId, BigDecimal money);
	
	public boolean updateRolePuncheGameAccInfo(long accId, String role);
	
	public List<Map<String, Object>> getPlayerRole(long accId);

}
