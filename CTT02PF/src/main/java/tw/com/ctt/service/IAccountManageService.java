package tw.com.ctt.service;

import static tw.com.ctt.constant.AccountManagerConstant.ACC_LEVEL_SA;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import tw.com.ctt.dao.IAccountManageDao;
import tw.com.ctt.model.MgrBean;

public interface IAccountManageService {

	public int findLevel(long userId);

	public Map<String, String> findTypeTotal(long userId, int status);

	public List<Map<String, Object>> getUpAccountName(long accId, int accLevelType);

	public int checkAccName(String tableName, String accName);

	long addComData(Map<String, Object> map);

	long addScData(Map<String, Object> map);

	long addMgrData(Map<String, Object> map);

	long addMemData(Map<String, Object> map);

	public List<Map<String, Object>> createAuth(int accLevelType, long accId) throws SQLException;

	public MgrBean getSeetingComData(long accId) throws SQLException;

	public MgrBean getSeetingScData(long accId) throws SQLException;

	public Map<String, Object> getSeetingManagerData(long accId) throws SQLException;

	boolean updateMem(Map<String, Object> map);

	boolean updateCom(Map<String, Object> map);

	boolean updateMgr(Map<String, Object> map);

	public Map<String, Object> getSeetingMemberData(long accId) throws SQLException;

	public boolean updateMemberWithdrawPwd(long accId);

	public boolean updateMemberPwd(long accId, String pwd);

	public int updateAccStatus(long accId, int status, int accLevelType);

	public Map<String, Object> getAllAccData(long accId);
	

	public Map<String, Object> checkAccRatio(long accId, int g1CurrentRatio, int g2CurrentRatio, int g3CurrentRatio, int g4CurrentRatio,
			int g5CurrentRatio);

	public List<Map<String, Object>> showLog(long opsAccId, long updateAccId, int actionUpperId, int accLevel, int firstCount, int count);

	public Map<String, Object> getLowerLevelAccTotal(long accId, String tokenId);

	public Map<String, Object> getAccDetails(long accId);

	public boolean updateBankDefaultData(long accId, String bank, String bankAcc, String bankAccName, String bankCardBranches, String area);

	public boolean updateBankNoDefaultData(long accId, String bank, String bankAcc, String bankAccName, String bankCardBranches, String area);

	public long insertGameServer(String gameServerId, int bet, int maxPlayer, int gameTimesType, int commission, int thread, int beatsTimeOut,
			int gameTimeOut, int autoTimeStamp, int waitContinueTimeOut, int continueTimesType, String serviceTime, int serverStatus);

	public boolean disableBankCard(int bankCardId, long accId);

	public Map<String, Object> getGameServerData();

	public boolean setGameServerData(int sid, String gameServerId, int nextBet, int nextMaxPlayer, int nextGameTimesType, int nextCommission,
			int nextThread, int nextBeatsTimeOut, int nextGameTimeOut, int nextAutoTimeStamp, int nextWaitContinueTimeOut, int nextContinueTimesType,
			String nextServiceTime, int nextServerStatus);

	public int getLogTotalCount(long opsAccId, long updateAccId, int actionUpperId, int accLevel);

	public int getOrderLogTotalCount(int upperAction);

	public List<Map<String, Object>> showOrderLog(int upperAction, int firstCount, int count);

	public int getGameSetLogTotalCount(int upperAction);

	public List<Map<String, Object>> showGameSetLog(int upperAction, int firstCount, int count);
	//-
	public List<?> searchCOM(long userId , long accId , int status , int sort , int asc );
	
	public List<?> searchMAG(int levelType , long userId ,long upAccId , long accId , int status , int sort , int asc , boolean checkStatus);
	
	public List<?> searchMEM(int searchLevel,long userId ,long upAccId , String accName , int memType, int status , int sort , int asc , boolean checkStatus , int firstCount , int count);
	
	public int searchTotalCountMEM(int searchLevel , long userId ,long upAccId ,String accName , int memType , int status , boolean checkStatus);
	
	public boolean PWDChange(String oldPassWord, String newPassWord);

}
