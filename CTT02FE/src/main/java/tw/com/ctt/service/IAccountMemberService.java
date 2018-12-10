package tw.com.ctt.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IAccountMemberService {
	public Map<String , Object>  getMemAccData(String tokenId, long accId) ;
	
	public Map<String, Object> updatePwd(long accId,String tokenId , String oldPwd, String newPwd, String ip);
	
	public Map<String , Object> updateWithdrawPwd(long accId ,String withdrawPwd,String ip);

	public boolean addRechargeOrder(long accId, String accName, String bankAccName, String bank, String bankAcc, String bankSid,
			int orderType, int status, BigDecimal amount,
			int currency, String postscript, int backOrderStatus, String bankDateTime);

	public boolean addWithdrawalOrder(long accId, String accName, BigDecimal withdrawalAmount, int bankInfoList, String orderWithdrawPwd);
	
	public Map<String , Object> getAccDetails (long accId);
	
	public boolean updateAccDetails(long accId ,String memberRealName,String phoneNumber , String qqAcc , String wechatAcc, String nickname, String ip);

	public boolean addBankCard(long accId, String bank, String bankAcc, String bankAccName, String bankCardBranches,
			String area);

	public List<Map<String, Object>> getBankCardInfo(long accId);
	
	public Map<String,Object> getPlatformCashBc(int platformId);
	
	public boolean checkMemberAcc(String accName);
	
	public boolean addCashMemAcc(int platformId , String accName,String  pwd, String ip);

	public Map<String ,Object> getCashierInfo(int cashierId);

	public int getWithdrawPwdFailed(long accId);

	public Map<String, Object> searchRecordsTotleCount(String startTime, String endTime);

	public List<Map<String, Object>> searchRecords(String accName, String startTime, String endTime, int firstCount, int count);
}
