package tw.com.ctt.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import tw.com.ctt.model.MemBean;
import tw.com.ctt.model.MgrBean;
import tw.com.ctt.model.SetGameServerBean;

public interface IAccountManageDao {

	public int findLevel(long userId);

	public Map<String, String> findTypeTotal(long userId, int status);

	public List<Map<String, Object>> getUpAccountName(long accId, int accLevelType);

	public long addManager(String accName, String pwd, String nickName, int authGroup, int fullRatio, int handicap) throws SQLException;

	public boolean addComScLevel(long accId, int accLevelType) throws SQLException;

	public boolean addLevel(long accId, long upAccId) throws SQLException;

	public boolean addMemberLevel(long accId, long upAccId) throws SQLException;

	public boolean addCreate(String createTable, long accId, long updateaccId, String ip) throws SQLException;

	public boolean addLastLogin(String lastLoginTable, long accId, String ip) throws SQLException;

	public boolean addLastUpdate(String lastUpdateTable, long accId, long updateaccId, String ip) throws SQLException;

	public int checkAccName(String tableName, String accName);

	public long addCom(String accName, String pwd, String nickName, int authGroup) throws SQLException;

	public long addMember(String accName, String pwd, String nickName, int memberType,int handicap) throws SQLException;

	public boolean addManagerRatio(long accId, int gameType, int upperMinRatio, int upperMaxRatio, int currentMaxRatio) throws SQLException;

	public boolean addMemberRatio(long accId, int gameType, int upperMinRatio) throws SQLException;

	public boolean memberRatioTotal(long memberId, int gameType, List<Integer> ratio) throws SQLException;

	public List<Map<String, Object>> createAuth(int level, long accId) throws SQLException;

	public MgrBean getSeetingComData(long accId) throws SQLException;

	
	public MgrBean getSeetingMagData(long accId);
	
	public MemBean getSeetingMemData(long accId) throws SQLException;
	
	public Map<String, Object> getSeetingMagUpData(long accId);
	
	public Map<String, Object> getSeetingMemUpData(long accId);

	public boolean updateMgrAcc(long accId, String pwd, String nickName, int authGroup, int fullRatio , int handicap) throws SQLException;

	public boolean updateMemAcc(long accId, String pwd, String nickName , int handicap) throws SQLException;

	public boolean updateLastUpdate(long accId, long updateaccId, String ip, String lastUpdatetable) throws SQLException;

	public boolean updateMgrRatio(long accId, int gameType, int upperMinRatio, int upperMaxRatio, int currentMaxRatio) throws SQLException;

	public boolean updateMemRatio(long accId, int gameType, int upperMinRatio) throws SQLException;

	public boolean updateMemberRatioTotal(long memberId, int gameType, List<Integer> ratio) throws SQLException;

	public List<Long> findTotalRatioAccId(long accId);

	public List<Map<String, Object>> findFullRatioMgrAccId(long accId);

	public List<Map<String, Object>> findFullRatioMemAccId(long accId);

	public List<Map<String, Object>> checkAccRatio(long accId, int g1CurrentRatio, int g2CurrentRatio, int g3CurrentRatio, int g4CurrentRatio,
			int g5CurrentRatio);

	public boolean updateMemberWithdrawPwd(long accId);

	public boolean updateMemberPwd(long accId, String pwd);

	public boolean updateStatusEnable(long accId, int accLevelType);

	public boolean updateStatusDisable(long accId, int accLevelType);

	public boolean updateStatusCantLogin(long accId, int accLevelType);

	public boolean updateStatusDelete(long accId, int accLevelType);

	public BigDecimal getLowerLevelAccBalance(long accId, int status);

	public Map<String, Object> getAllAccData(long accId);

	public List<Map<String, Object>> checkMemRatio(long accId, int g1CurrentRatio, int g2CurrentRatio, int g3CurrentRatio, int g4CurrentRatio,
			int g5CurrentRatio);

	public Map<String, Object> getLowerLevelAccTotal(long accId);

	public List<Map<String, Object>> showLog(long opsAccId, long updateAccId, int actionUpperId, int accLevel, int firstCount, int count);

	public boolean PWDChange(long USERID, String oldPassWord, String newPassWord);

	public Map<String, Object> getAccDetails(long accId);

	public boolean addAccDeails(long accId);

	public boolean updateBankDefaultData(long accId, String bank, String bankAcc, String bankAccName, String bankCardBranches, String area);

	public boolean updateBankNoDefaultData(long accId, String bank, String bankAcc, String bankAccName, String bankCardBranches, String area);

	public int getLogTotalCount(long opsAccId, long updateAccId, int actionUpperId, int accLevel);

	public boolean addPunchGameAccInfo(long accId) throws SQLException;

	public boolean addPlatform(long platformScId, long cashBcId, long trialPlayBcId);

	public Map<String, Object> getBankCardData(long accId);

	public boolean disableBankCard(int bankCardId, long accId);

	public long insertGameServer(String gameServerId, int bet, int maxPlayer, int gameTimesType, int commission, int thread, int beatsTimeOut,
			int gameTimeOut, int autoTimeStamp, int waitContinueTimeOut, int continueTimesType, String serviceTime, int serverStatus);

	public Map<String, Object> getGameServerData();

	public boolean updateGameServer(int sid, String gameServerId, int nextBet, int nextMaxPlayer, int nextGameTimesType, int nextCommission,
			int nextThread, int nextBeatsTimeOut, int nextGameTimeOut, int nextAutoTimeStamp, int nextWaitContinueTimeOut, int nextContinueTimesType,
			String nextServiceTime, int nextServerStatus);

	public SetGameServerBean getGameServerDataForLog(int sid);

	public int getOrderLogTotalCount(int upperAction);

	public List<Map<String, Object>> showOrderLog(int upperAction, int firstCount, int count);

	public int getGameSetLogTotalCount(int upperAction);

	public List<Map<String, Object>> showGameSetLog(int upperAction, int firstCount, int count);

	public List<Integer> selectGameRatioMem(long upAccId, int memGameUpperMin, int gameType);

	public List<Integer> selectGameRatio(long memberId, int gameType, long upAccId, int minRatio, int maxRatio, int currentRatio,
			boolean isFullRatio);

	public boolean updateLowerLevelMagRatio(long accId, int currentRatio, int gameType);

	public boolean updateLowerLevelMemRatio(long accId, int currentRatio, int gameType);

	public List<Integer> selectGameRatioUpdateMem(long memberId, int gameType, int minRatio);
	
	public List<?> searchCOM(long userId, long accId, int status, int sort, int asc);
	
	public List<?> searchMAG(int levelType, long userId,long upAccId, long accId, int status, int sort, int asc, boolean checkStatus);
	
	public List<?> searchMEM(int searchLevel,long userId ,long upAccId , String accName , int memType, int status 
			, int sort , int asc , boolean checkStatus , int firstCount , int count);

	public int searchTotalCountMEM(int searchLevel , long userId ,long upAccId ,String accName , int memType , int status , boolean checkStatus);
	
	public boolean updateMagHandicap(long accId , int handicap);
	
	public boolean updateMemHandicap(long accId , int handicap);
	
}
