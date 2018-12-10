package tw.com.ctt.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tw.com.ctt.model.CommissionRawBean;
import tw.com.ctt.model.GamePuncheRecordsBean;

public interface IGameDao {

	public Map<String, String> checkPwd(String userName, String pwd);

	public List<Map<String, Object>> getPlayer(long accId);

	public boolean withdraw(long accId, BigDecimal money) throws SQLException;

	public boolean deposit(long accId, BigDecimal money) throws SQLException;

	public boolean updateMoneyPuncheGameAccInfo(long accId, BigDecimal money, String serverId) throws SQLException;

	public List<Map<String , Object>> getMemberUpperAccRatio(long accId, int gameType);

	public boolean insertGamePuncheRecords(GamePuncheRecordsBean gamePuncerecordsBean);
	
	public boolean insertCommissionRawData(CommissionRawBean commissionRawBean);
	
	public int getPuncheGameLastSerialization(Date date , int serverId);
	
	public boolean addAccNowGameData(long accId , int serverId , String gameHall , String gameName);

	public boolean removeAccNowGameData(long accId , int serverId);

	public List<Map<String, Object>> getGameServerParam();

}
