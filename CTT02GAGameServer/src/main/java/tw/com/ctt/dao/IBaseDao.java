package tw.com.ctt.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IBaseDao {

	public void checkRead();

	public void checkWrite();

	public void closeRead();

	public void closeWrite();

	public void close();

	public boolean setLogToDBNoCommit(String tableName, long opsId, long accId, long dataId, int opsType, int accType, int action, String data,
			String ip);

	public boolean checkTokenTimeOut(String tableName, String tokenId, long accId);

	public boolean updateTokenTime(String tableName, String tokenId, long accId);

	public boolean commitDB() throws SQLException;

	public boolean rollBackDB() throws SQLException;

	public boolean addToken(String tableName, long accId);

	public String getNewTokenId(String tableName, long accId);

	public Map<String, String> checkTokenTimeOutMember(String tableName, String tokenId, long accId);

	public boolean colosMemTokenId(long accId, String tokenId);

	public boolean colosMagTokenId(long accId, String tokenId);

	public boolean getAuthFunction(long accId, String url);

	public boolean setLotteryLogToDBNoCommit(long opsId, int action, String data, String ip);

	public boolean setActionLogToDBNoCommit(String tableName, long opsAccId, String actionUrl, int opsType, int urlType, String detail, String ip);

	public void callServer(String url, List<String> notRunList);

	public void updateOrderIdDate();

}
