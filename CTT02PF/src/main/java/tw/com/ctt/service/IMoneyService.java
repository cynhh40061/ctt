package tw.com.ctt.service;

import java.math.BigDecimal;

public interface IMoneyService {
	public boolean Deposit(long accId, long upAccId, BigDecimal depositMoney, String tableName, int accDepositType);

	public boolean scDeposit(long accId, BigDecimal depositMoney, int accDepositType);

	public boolean withdrawal(long accId, BigDecimal depositMoney, String tableName, int accWithdrawalType);

	public boolean memOrderDeposit(long accId, BigDecimal depositMoney, int accDepositType);
}
