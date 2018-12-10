package tw.com.ctt.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import tw.com.ctt.model.OrderBean;

public interface IOrderDao {

	public List<Map<String, Object>> searchOrderRechargeData(String rechargeStatus, int status, String accName, String auditorAccName,
			String firstDate, String lastDate, int aes, int sort, int firstCount, int count);

	public List<Map<String, Object>> searchOrderWithdrawalData(String withdrawalStatus, int status, String accName, String auditorAccName,
			String firstDate, String lastDate, int aes, int sort, int firstCount, int count);

	public boolean insertRemark(long orderId, boolean isRechange, String data);

	public List<Map<String, Object>> getOrderReviewData(String rechargeStatus, String withdrawalStatus, int aes, int sort, int firstCount, int count);

	public List<Map<String, Object>> getOrderWithdrawalData(String status, int firstCount, int count);

	public List<Map<String, Object>> getOrderRechargeData(String status, int firstCount, int count);

	public long insertRecharge(long accId, String accName, String bankAccName, String bank, String bankAcc, String bankSid,
			BigDecimal bankCheckAmount, String bankDepositDatetime, int orderType, int status, BigDecimal amount, int currency, String note,
			int backOrderStatus);

	public boolean autitAuth(long USERID, long orderId, int order_type);

	public Map<String, Object> checkOrderReview(long orderId);

	public Map<String, Object> checkCarryOutOrder(long orderId, int orderType);

	public Map<String, Object> getRechargeOrderData(long USERID, long orderId);

	public Map<String, Object> getWithdrawalOrderData(long USERID, long orderId);

	public boolean checkMemberAccName(String accName);

	public boolean updateRechargeOrderStatus(long USERID, long orderId);

	public boolean updateWithdrawalOrderStatus(long USERID, long orderId);

	public boolean updateRechargeAuditOrder(long USERID, long orderId, boolean allow);

	public boolean updateWithdrawalAuditOrder(long USERID, long orderId, boolean allow, String bankSid, BigDecimal bankCheckAmount,
			String bankDepositTime);

	public int getOrderRechargeCountTotal(int status, String accName, String auditorAccName, String firstDate, String lastDate, String orderStatus);

	public int getOrderWithdrawalCountTotal(int status, String accName, String auditorAccName, String firstDate, String lastDate, String orderStatus);

	public int getOrderReviewCountTotal(String rechargeStatus, String withdrawalStatus);

	public int checkMemType(String accName);

	public OrderBean getOrderInfo(long orderId, int orderType);

	public long getInsertRechargeOrderAccId(String accName);

}
