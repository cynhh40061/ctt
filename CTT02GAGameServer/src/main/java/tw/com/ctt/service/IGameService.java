package tw.com.ctt.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tw.com.ctt.model.CommissionRawBean;
import tw.com.ctt.model.GamePuncheRecordsBean;

public interface IGameService {

	public Map<String, String> checkPwd(String userName, String pwd);

	public List<Map<String, Object>> getPlayer(long accId);

	public boolean withdraw(long accId, BigDecimal money);

	public boolean deposit(long accId, BigDecimal money);
	
	public BigDecimal withdrawAllMoneyToGame(long accId , String serverId , String  bet);
	
	public boolean depositAllMoneyFromGame(long accId, BigDecimal money , String serverId);
	
	public boolean updateMoneyPuncheGameAccInfo(long accId, BigDecimal money, String serverId);

	public List<Map<String , Object>> getMemberUpperAccRatio(long accId , int gameType);
	
	public boolean insertGamePuncheRecords(GamePuncheRecordsBean gamePuncheRecordsBean , List<CommissionRawBean> commissionRawBeanList);
	
	public int getPuncheGameLastSerialization(Date date , int serverId);
	
	public void addAccNowGameData(long accId , int serverId , String gameHall , String gameName);

	public void removeAccNowGameData(long accId , int serverId);

	public List<Map<String, Object>> getGameServerParam();
}
