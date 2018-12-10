package tw.com.ctt.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IOrderService {

	public List<Map<String, Object>> searchOrderRechargeCarryOutData(int status, String accName, String auditorAccName, String firstDate,
			String lastDate, int aes, int sort, int firstCount, int count);

	public List<Map<String, Object>> searchOrderRechargeUndoneData(int status, String accName, String auditorAccName, String firstDate,
			String lastDate, int aes, int sort, int firstCount, int count);

	public List<Map<String, Object>> searchOrderWithdrawalCarryOutData(int status, String accName, String auditorAccName, String firstDate,
			String lastDate, int aes, int sort, int firstCount, int count);

	public List<Map<String, Object>> searchOrderWithdrawalUndoneData(int status, String accName, String auditorAccName, String firstDate,
			String lastDate, int aes, int sort, int firstCount, int count);

	public List<Map<String, Object>> getOrderReviewData(int aes, int sort, int firstCount, int count);

	public List<Map<String, Object>> getOrderWithdrawalUndoneData(int firstCount, int count);

	public List<Map<String, Object>> getOrderWithdrawalCarryOutData(int firstCount, int count);

	public List<Map<String, Object>> getOrderRechargeUndoneData(int firstCount, int count);

	public List<Map<String, Object>> getOrderRechargeCarryOutData(int firstCount, int count);

	public Map<String, Object> checkOrderReview(long orderId);

	public Map<String, Object> getAuditRechargeOrderData(long orderId, int orderType);

	public Map<String, Object> getAuditWithdrawalOrderData(long orderId, int orderType);

	public Map<String, Object> checkRechargeCarryOutOrder(long orderId, int orderType);

	public Map<String, Object> checkWithdrawalCarryOutOrder(long orderId, int orderType);

	public boolean getOrderAudit(long memberAccId, long orderId, int orderType);

	public boolean audit(long memberAccId, int status, long orderId, int orderType, boolean allow, String data, String bankSid,
			BigDecimal bankCheckAmount, String bankDepositTime);

	public boolean addRechargeOrder(String accName, String bankAccName, String bank, String bankAcc, String bankSid, BigDecimal bankCheckAmount,
			String bankDepositDatetime, int orderType, int status, BigDecimal amount, int currency, String postscript, int backOrderStatus,
			String data);

	public int getOrderRechargeCarryOutCountTotal(int status, String accName, String auditorAccName, String firstDate, String lastDate);

	public int getOrderRechargeUndoneCountTotal(int status, String accName, String auditorAccName, String firstDate, String lastDate);

	public int getOrderWithdrawalCarryOutCountTotal(int status, String accName, String auditorAccName, String firstDate, String lastDate);

	public int getOrderWithdrawalUndoneCountTotal(int status, String accName, String auditorAccName, String firstDate, String lastDate);

	public int getOrderReviewCountTotal();
}
