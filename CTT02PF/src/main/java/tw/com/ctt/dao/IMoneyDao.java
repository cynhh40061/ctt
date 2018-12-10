package tw.com.ctt.dao;

import java.math.BigDecimal;

public interface IMoneyDao {

	public boolean upAccDeposit(long accId, long upaccId, BigDecimal money, String tableName, int accDepositType);

	public boolean withdrawal(long accId, BigDecimal money, String tableName, int accWithdrawalType);

	public boolean scDeposit(long accId, BigDecimal depositMoney, int accDepositType);

	public boolean withdrawalDetails(long accId, BigDecimal withdrawalMoney);

	public boolean depositDetails(long accId, BigDecimal depositMoney);

	public BigDecimal checkBalance(long accId, String tableName);

	public boolean memOrderDeposit(long accId, BigDecimal depositMoney, int accDepositType);

}
