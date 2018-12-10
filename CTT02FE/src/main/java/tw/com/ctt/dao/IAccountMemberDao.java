package tw.com.ctt.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import tw.com.ctt.model.MemBean;

public interface IAccountMemberDao {

	public Map<String, Object> getMemAccData(String tokenId, long accId);

	public boolean updatePwd(long accId, String oldPwd, String newPwd);

	public boolean updateWithdrawPwd(long accId, String withdrawPwd);

	public long insertWithdrawal(long accId, String accName, String bankAccName, String bank, String bankAcc,
			int status, BigDecimal amount, int currency);

	public long insertRecharge(long accId, String accName, String bankAccName, String bank, String bankAcc,
			String bankSid, int rechargeType, int status, BigDecimal amount, int currency, String note,
			int backOrderStatus, String bankDateTime);

	public Map<String, Object> getAccDetails(long accId);

	public boolean updateAccDetails(long accId, String memberRealName, String phoneNumber, String qqAcc,
			String wechatAcc);

	public boolean updateAccNickname(long accId, String nickname);

	public boolean addBankCard(long accId, String bank, String bankAcc, String bankAccName, String bankCardBranches,
			String area);

	public List<Map<String, Object>> getBankCardInfo(long accId);

	public Map<String, Object> getPlatformCashBc(int platformId);

	public boolean checkMemberAcc(String accName);

	public long addMember(String accName, String pwd , int handicap) throws SQLException;

	public boolean addMemberLevel(long accId, long upAccId);

	public boolean addCreate(long accId, String ip);

	public boolean addLastLogin(long accId);

	public boolean addLastUpdate(long accId, String ip);

	public boolean addAccDeails(long accId);

	public boolean addPunchGameAccInfo(long accId);

	public Map<String, Object> addWithdrawalOrderGetBankCardInfo(long accId, String withdrawalAmount, int bankInfoList);

	public int checkMemType(long accId);

	public Map<String, Object> getMemAccInfo(long accId);

	public boolean charge(long accId, BigDecimal withdrawalAmount);

	public Map<String, Object> getCashierInfo(int cashierId);

	public boolean updateWithdrawPwdFailed(long accId);

	public int getWithdrawPwdFailed(long accId);

	public MemBean getAccDetailsForLog(long accId);

	public Map<String, Object> searchRecordsTotle(long accId, String startTime, String endTime);

	public List<Map<String, Object>> searchRecords(long accId, String accName, String startTime, String endTime,
			int firstCount, int count);
	
	public List<Integer> selectGameRatioMem(long upAccId, int memGameUpperMin, int gameType);
	
	public boolean memberRatioTotal(long memberId, int gameType, List<Integer> ratio) throws SQLException ;
	
	public boolean addMemberRatio(long accId, int gameType, int upperMinRatio) throws SQLException;

}
