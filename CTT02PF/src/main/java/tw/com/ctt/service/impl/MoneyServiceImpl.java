package tw.com.ctt.service.impl;

import static tw.com.ctt.constant.LogToDBConstant.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.impl.MoneyDaoImpl;
import tw.com.ctt.service.IMoneyService;
import tw.com.ctt.util.ShowLog;

public class MoneyServiceImpl extends BaseService implements IMoneyService {

	private static final long serialVersionUID = 8877609338231189664L;
	private static final Logger LOG = LogManager.getLogger(MoneyServiceImpl.class.getName());

	public MoneyServiceImpl() {

	}

	public MoneyServiceImpl(long userId, String userIp) {
		super(userId, userIp);
	}

	@Override
	public boolean Deposit(long accId, long upAccId, BigDecimal depositMoney, String tableName, int accDepositType) {
		boolean todo = false;
		MoneyDaoImpl todoDao = null;
		int logAdd = 0;
		int logDebit = 0;
		String LogTableType = null;
		BigDecimal oldBalance = null;
		BigDecimal oldUpperBalance = null;
		JSONObject responseJSONObject = null;
		Map<String, Object> logDataMap = null;
		Map<String, Object> upperLogDataMap = null;
		int accType = 0;
		try {
			todoDao = (MoneyDaoImpl) dao;
			logDataMap = new ConcurrentHashMap<String, Object>();
			upperLogDataMap = new ConcurrentHashMap<String, Object>();

			if (accDepositType == LOG_ACTION_ADD_ACC_DEPOSIT_ADD) {
				logAdd = LOG_ACTION_ADD_ACC_DEPOSIT_ADD;
				logDebit = LOG_ACTION_ADD_ACC_DEPOSIT_DEBIT;
			} else if (accDepositType == LOG_ACTION_SET_ACC_DEPOSIT_ADD) {
				logAdd = LOG_ACTION_SET_ACC_DEPOSIT_ADD;
				logDebit = LOG_ACTION_SET_ACC_DEPOSIT_DEBIT;

			}
			oldBalance = todoDao.checkBalance(accId, tableName);
			oldUpperBalance = todoDao.checkBalance(upAccId, "ctt_manager.ctt_manager_acc");
			if ("ctt_manager.ctt_manager_acc".equals(tableName)) {
				LogTableType = "manager";
				accType = ACC_TYPE_MANAGER;
			} else {
				LogTableType = "member";
				accType = ACC_TYPE_MEMBER;
			}

			todo = todoDao.upAccDeposit(accId, upAccId, depositMoney, tableName, accDepositType);
			if (todo) {
				if ("ctt_manager.ctt_member_acc".equals(tableName)) {
					todo = todoDao.depositDetails(accId, depositMoney);
				}
			}
			if (todo) {
				logDataMap.put("oldBalance", oldBalance);
				logDataMap.put("newBalance", oldBalance.add(depositMoney));
				logDataMap.put("money", depositMoney);
				logDataMap.put("accId", upAccId);

				upperLogDataMap.put("oldBalance", oldUpperBalance);
				upperLogDataMap.put("newBalance", oldUpperBalance.subtract(depositMoney));
				upperLogDataMap.put("money", depositMoney);
				upperLogDataMap.put("accId", accId);
				responseJSONObject = new JSONObject(logDataMap);
				todo = dao.setLogToDBNoCommit("ctt_manager.ctt_" + LogTableType + "_money_log", USERID, accId, 0, OPS_TYPE_MANAGER, accType,
						logAdd, responseJSONObject.toString(), USERIP);
				if (todo) {
					responseJSONObject = new JSONObject(upperLogDataMap);
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_money_log", USERID, upAccId, 0, OPS_TYPE_MANAGER, accType, logDebit,
							responseJSONObject.toString(), USERIP);
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e);
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (SQLException e) {
				todo = false;
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
			todoDao = null;
			logAdd = 0;
			logDebit = 0;
			LogTableType = null;
			oldBalance = null;
			oldUpperBalance = null;
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
			if (upperLogDataMap != null) {
				upperLogDataMap.clear();
				upperLogDataMap = null;
			}
		}
		return todo;
	}

	@Override
	public boolean scDeposit(long accId, BigDecimal depositMoney, int accDepositType) {
		boolean todo = false;
		int logAdd = 0;
		MoneyDaoImpl todoDao = null;
		BigDecimal oldBalance = null;
		JSONObject responseJSONObject = null;
		Map<String, Object> logDataMap = null;
		try {
			todoDao = (MoneyDaoImpl) dao;
			logDataMap = new ConcurrentHashMap<String, Object>();

			if (accDepositType == LOG_ACTION_ADD_ACC_DEPOSIT_ADD) {
				logAdd = LOG_ACTION_ADD_ACC_DEPOSIT_ADD;
			} else if (accDepositType == LOG_ACTION_SET_ACC_DEPOSIT_ADD) {
				logAdd = LOG_ACTION_SET_ACC_DEPOSIT_ADD;
			}
			oldBalance = todoDao.checkBalance(accId, "ctt_manager.ctt_manager_acc");

			todo = todoDao.scDeposit(accId, depositMoney, accDepositType);
			if (todo) {
				logDataMap.put("oldBalance", oldBalance);
				logDataMap.put("newBalance", oldBalance.add(depositMoney));
				logDataMap.put("money", depositMoney);
				responseJSONObject = new JSONObject(logDataMap);
				todo = dao.setLogToDBNoCommit("ctt_manager.ctt_manager_money_log", USERID, accId, 0, ACC_TYPE_MANAGER, ACC_TYPE_MANAGER, logAdd,
						responseJSONObject.toString(), USERIP);
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e);
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (SQLException e) {
				todo = false;
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
			logAdd = 0;
			todoDao = null;
			oldBalance = null;
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
		return todo;
	}

	@Override
	public boolean withdrawal(long accId, BigDecimal withdrawalMoney, String tableName, int accWithdrawalType) {
		boolean todo = false;
		MoneyDaoImpl todoDao = null;
		String LogTableType = null;
		BigDecimal oldBalance = null;
		JSONObject responseJSONObject = null;
		Map<String, Object> logDataMap = null;
		int accType = 0;
		try {
			todoDao = (MoneyDaoImpl) dao;
			logDataMap = new ConcurrentHashMap<String, Object>();

			if ("ctt_manager.ctt_manager_acc".equals(tableName)) {
				LogTableType = "manager";
				accType = ACC_TYPE_MANAGER;
			} else {
				LogTableType = "member";
				accType = ACC_TYPE_MEMBER;
			}
			oldBalance = todoDao.checkBalance(accId, tableName);

			if (oldBalance.compareTo(withdrawalMoney) >= 0) {
				todo = todoDao.withdrawal(accId, withdrawalMoney, tableName, accWithdrawalType);
			}
			if ("ctt_manager.ctt_member_acc".equals(tableName)) {
				if (todo) {
					todo = todoDao.withdrawalDetails(accId, withdrawalMoney);
				}
			}
			if (todo) {
				logDataMap.put("oldBalance", oldBalance);
				logDataMap.put("newBalance", oldBalance.subtract(withdrawalMoney));
				logDataMap.put("money", withdrawalMoney);
				responseJSONObject = new JSONObject(logDataMap);
				todo = dao.setLogToDBNoCommit("ctt_manager.ctt_" + LogTableType + "_money_log", USERID, accId, 0, OPS_TYPE_MANAGER, accType,
						LOG_ACTION_SET_ACC_WITHDRAWAL_DEBIT, responseJSONObject.toString(), USERIP);
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (SQLException e) {
				todo = false;
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
			todoDao = null;
			LogTableType = null;
			oldBalance = null;
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
		return todo;
	}

	@Override
	public boolean memOrderDeposit(long accId, BigDecimal depositMoney, int accDepositType) {
		boolean todo = false;
		int logAdd = 0;
		MoneyDaoImpl todoDao = null;
		BigDecimal oldBalance = null;
		JSONObject responseJSONObject = null;
		Map<String, Object> logDataMap = null;
		try {
			todoDao = (MoneyDaoImpl) dao;
			logDataMap = new ConcurrentHashMap<String, Object>();

			if (accDepositType == LOG_ACTION_WITHDRAWAL_ORDER_AUDIT_FAIL_ADD) {
				logAdd = LOG_ACTION_WITHDRAWAL_ORDER_AUDIT_FAIL_ADD;
			} else if (accDepositType == LOG_ACTION_RECHARGE_ORDER_AUDIT_SUCCESS_ADD) {
				logAdd = LOG_ACTION_RECHARGE_ORDER_AUDIT_SUCCESS_ADD;
			}
			oldBalance = todoDao.checkBalance(accId, "ctt_manager.ctt_member_acc");
			todo = todoDao.memOrderDeposit(accId, depositMoney, accDepositType);

			if (todo) {
				logDataMap.put("oldBalance", oldBalance);
				logDataMap.put("newBalance", oldBalance.add(depositMoney));
				logDataMap.put("money", depositMoney);
				responseJSONObject = new JSONObject(logDataMap);

				todo = dao.setLogToDBNoCommit("ctt_manager.ctt_member_money_log", USERID, accId, 0, ACC_TYPE_MANAGER, ACC_TYPE_MEMBER, logAdd,
						responseJSONObject.toString(), USERIP);
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (SQLException e) {
				todo = false;
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
			todoDao = null;
			logAdd = 0;
			oldBalance = null;
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
		return todo;
	}
}
