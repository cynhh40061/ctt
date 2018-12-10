package tw.com.ctt.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.IOrderDao;
import tw.com.ctt.model.OrderBean;
import tw.com.ctt.service.IOrderService;
import tw.com.ctt.util.ShowLog;

import static tw.com.ctt.constant.LogToDBConstant.*;
import static tw.com.ctt.constant.OrderConstant.*;

public class OrderServiceImpl extends BaseService implements IOrderService {
	/**
	 * 
	 */
	private static final long serialVersionUID = -461167661678117286L;
	private static final Logger LOG = LogManager.getLogger(OrderServiceImpl.class.getName());

	public OrderServiceImpl() {

	}

	public OrderServiceImpl(long userId, String userIp) {
		super(userId, userIp);
	}

	@Override
	public boolean addRechargeOrder(String accName, String bankAccName, String bank, String bankAcc, String bankSid, BigDecimal bankCheckAmount,
			String bankDepositDatetime, int rechargeType, int status, BigDecimal amount, int currency, String note, int backOrderStatus,
			String remark) {
		boolean todo = false;
		int memtype = 0;
		JSONObject responseJSONObject = null;
		Map<String, Object> logDataMap = null;
		long accId = 0;
		long orderId = 0;
		try {
			memtype = ((IOrderDao) dao).checkMemType(accName);
			logDataMap = new ConcurrentHashMap<String, Object>();

			if (memtype == GRNERAL_MEM) {
				accId = ((IOrderDao) dao).getInsertRechargeOrderAccId(accName);
				if (accId > 0) {
					orderId = ((IOrderDao) dao).insertRecharge(accId, accName, bankAccName, bank, bankAcc, bankSid, bankCheckAmount,
							bankDepositDatetime, rechargeType, status, amount, currency, note, backOrderStatus);
				}
				if (orderId > 0) {
					todo = ((IOrderDao) dao).insertRemark(orderId, true, remark);
				}
				if (todo) {
					logDataMap.put("bankAccName", "" + bankAccName);
					logDataMap.put("bank", bank);
					logDataMap.put("bankAcc", bankAcc);
					logDataMap.put("bankSid", bankSid);
					logDataMap.put("bankCheckAmount", bankCheckAmount);
					logDataMap.put("bankDepositDatetime", bankDepositDatetime);
					logDataMap.put("rechargeType", rechargeType);
					logDataMap.put("status", status);
					logDataMap.put("amount", amount);
					logDataMap.put("currency", currency);
					logDataMap.put("note", note);
					logDataMap.put("backOrderStatus", backOrderStatus);
					logDataMap.put("remark", remark);
					responseJSONObject = new JSONObject(logDataMap);
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_data_ops_log", USERID, accId, orderId, OPS_TYPE_MANAGER, ACC_TYPE_MEMBER,
							LOG_ACTION_ADD_RECHARGE_ORDER, responseJSONObject.toString(), USERIP);
				}
			}else {
				todo = true;
				LOG.debug("is directly under member!");
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
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
			memtype = 0;
			accId = 0;
			orderId = 0;
		}
		return todo;
	}

	@Override
	public List<Map<String, Object>> searchOrderRechargeCarryOutData(int status, String accName, String auditorAccName, String firstDate,
			String lastDate, int aes, int sort, int firstCount, int count) {
		return ((IOrderDao) dao).searchOrderRechargeData(rechargeCarryOutStatus, status, accName, auditorAccName, firstDate, lastDate, aes, sort,
				firstCount, count);
	}

	@Override
	public List<Map<String, Object>> searchOrderRechargeUndoneData(int status, String accName, String auditorAccName, String firstDate,
			String lastDate, int aes, int sort, int firstCount, int count) {
		return ((IOrderDao) dao).searchOrderRechargeData(rechargeUndoneStatus, status, accName, auditorAccName, firstDate, lastDate, aes, sort,
				firstCount, count);
	}

	@Override
	public List<Map<String, Object>> searchOrderWithdrawalCarryOutData(int status, String accName, String auditorAccName, String firstDate,
			String lastDate, int aes, int sort, int firstCount, int count) {
		return ((IOrderDao) dao).searchOrderWithdrawalData(withdrawalCarryOutStatus, status, accName, auditorAccName, firstDate, lastDate, aes, sort,
				firstCount, count);
	}

	@Override
	public List<Map<String, Object>> searchOrderWithdrawalUndoneData(int status, String accName, String auditorAccName, String firstDate,
			String lastDate, int aes, int sort, int firstCount, int count) {
		return ((IOrderDao) dao).searchOrderWithdrawalData(withdrawalUndoneStatus, status, accName, auditorAccName, firstDate, lastDate, aes, sort,
				firstCount, count);
	}

	@Override
	public List<Map<String, Object>> getOrderReviewData(int aes, int sort, int firstCount, int count) {
		return ((IOrderDao) dao).getOrderReviewData(ReviewStatus, ReviewStatus, aes, sort, firstCount, count);
	}

	@Override
	public List<Map<String, Object>> getOrderWithdrawalUndoneData(int firstCount, int count) {
		return ((IOrderDao) dao).getOrderWithdrawalData(withdrawalUndoneStatus, firstCount, count);
	}

	@Override
	public List<Map<String, Object>> getOrderWithdrawalCarryOutData(int firstCount, int count) {
		return ((IOrderDao) dao).getOrderWithdrawalData(withdrawalCarryOutStatus, firstCount, count);
	}

	@Override
	public List<Map<String, Object>> getOrderRechargeUndoneData(int firstCount, int count) {
		return ((IOrderDao) dao).getOrderRechargeData(rechargeUndoneStatus, firstCount, count);
	}

	@Override
	public List<Map<String, Object>> getOrderRechargeCarryOutData(int firstCount, int count) {
		return ((IOrderDao) dao).getOrderRechargeData(rechargeCarryOutStatus, firstCount, count);
	}

	@Override
	public boolean getOrderAudit(long memberAccId, long orderId, int orderType) {
		boolean todo = false;
		JSONObject responseJSONObject = null;
		Map<String, Object> logDataMap = null;
		OrderBean Bean = null;
		try {
			Bean = ((IOrderDao) dao).getOrderInfo(orderId, orderType);
			logDataMap = new ConcurrentHashMap<String, Object>();
			if (orderType == ORDER_RECHARGE) {
				todo = ((IOrderDao) dao).updateRechargeOrderStatus(USERID, orderId);
				if (todo) {
					if (Bean.getFirstAuditor() == NO_AUDIT_ACC_ID && Bean.getFirstDatetime() == null
							&& Bean.getStatus() == ORDER_STATUS_1_WAIT_1ST_AUDIT) {
						logDataMap.put("firstAuditor", USERID);
						logDataMap.put("status", ORDER_STATUS_2_1ST_AUDITING);
					} else if (Bean.getFirstAuditor() > NO_AUDIT_ACC_ID && Bean.getFirstDatetime() != null
							&& Bean.getSecondAuditor() == NO_AUDIT_ACC_ID && Bean.getSecondDatetime() == null
							&& Bean.getStatus() == ORDER_STATUS_3_WAIT_2ND_AUDIT) {
						logDataMap.put("secondAuditor", USERID);
						logDataMap.put("status", ORDER_STATUS_4_2ND_AUDITING);
					} else if (Bean.getFirstAuditor() > NO_AUDIT_ACC_ID && Bean.getFirstDatetime() != null
							&& Bean.getSecondAuditor() > NO_AUDIT_ACC_ID && Bean.getSecondDatetime() != null && Bean.getLastAuditor() == 0
							&& Bean.getStatus() == ORDER_STATUS_5_WAIT_LAST_AUDIT) {
						logDataMap.put("lastAuditor", USERID);
						logDataMap.put("status", ORDER_STATUS_6_LAST_AUDIT);
					}
					logDataMap.put("amount", Bean.getAmount());
					logDataMap.put("orderType", orderType);
					responseJSONObject = new JSONObject(logDataMap);
				}
				if (todo) {
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_data_ops_log", USERID, memberAccId, orderId, OPS_TYPE_MANAGER, ACC_TYPE_MEMBER,
							LOG_ACTION_AUDIT_RECHARGE_ORDER, responseJSONObject.toString(), USERIP);
				}
			} else if (orderType == ORDER_WITHDRAWAL) {
				todo = ((IOrderDao) dao).updateWithdrawalOrderStatus(USERID, orderId);
				if (todo) {
					if (Bean.getFirstAuditor() == NO_AUDIT_ACC_ID && Bean.getFirstDatetime() == null
							&& Bean.getStatus() == ORDER_STATUS_1_WAIT_1ST_AUDIT) {
						logDataMap.put("firstAuditor", USERID);
						logDataMap.put("status", ORDER_STATUS_2_1ST_AUDITING);
					} else if (Bean.getFirstAuditor() > NO_AUDIT_ACC_ID && Bean.getFirstDatetime() != null
							&& Bean.getSecondAuditor() > NO_AUDIT_ACC_ID && Bean.getSecondDatetime() != null && Bean.getLastAuditor() == 0
							&& Bean.getStatus() == ORDER_STATUS_5_WAIT_LAST_AUDIT) {
						logDataMap.put("lastAuditor", USERID);
						logDataMap.put("status", ORDER_STATUS_6_LAST_AUDIT);
					}
					logDataMap.put("amount", Bean.getAmount());
					logDataMap.put("orderType", orderType);
					responseJSONObject = new JSONObject(logDataMap);
				}
				if (todo) {
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_data_ops_log", USERID, memberAccId, orderId, OPS_TYPE_MANAGER, 2,
							LOG_ACTION_AUDIT_WITHDRAWAL_ORDER, responseJSONObject.toString(), USERIP);
				}
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
			Bean = null;
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
		return todo;
	}

	@Override
	public boolean audit(long memberAccId, int status, long orderId, int orderType, boolean allow, String remark, String bankSid,
			BigDecimal bankCheckAmount, String bankDepositTime) {
		boolean todo = false;
		JSONObject responseJSONObject = null;
		Map<String, Object> logDataMap = null;
		OrderBean Bean = null;
		int action = 0;
		try {
			Bean = ((IOrderDao) dao).getOrderInfo(orderId, orderType);
			logDataMap = new ConcurrentHashMap<String, Object>();
			if (orderType == ORDER_RECHARGE) {
				todo = ((IOrderDao) dao).updateRechargeAuditOrder(USERID, orderId, allow);
				if (todo && remark != null && !remark.isEmpty() && !"".equals(remark.trim())) {
					todo = ((IOrderDao) dao).insertRemark(orderId, true, remark);
				}
				if (todo) {
					if (allow && status == ORDER_STATUS_2_1ST_AUDITING) {
						logDataMap.put("status", ORDER_STATUS_3_WAIT_2ND_AUDIT);
						action = LOG_ACTION_AUDIT_RECHARGE_ORDER_1ST_SUCCESS;
					} else if (!allow && status == ORDER_STATUS_2_1ST_AUDITING) {
						logDataMap.put("status", ORDER_STATUS_8_REFUSE);
						action = LOG_ACTION_AUDIT_RECHARGE_ORDER_1ST_FAIL;
					} else if (allow && status == ORDER_STATUS_4_2ND_AUDITING) {
						logDataMap.put("status", ORDER_STATUS_5_WAIT_LAST_AUDIT);
						action = LOG_ACTION_AUDIT_RECHARGE_ORDER_2ND_SUCCESS;
					} else if (!allow && status == ORDER_STATUS_4_2ND_AUDITING) {
						logDataMap.put("status", ORDER_STATUS_8_REFUSE);
						action = LOG_ACTION_AUDIT_RECHARGE_ORDER_2ND_FAIL;
					} else if (allow && status == ORDER_STATUS_6_LAST_AUDIT) {
						logDataMap.put("status", ORDER_STATUS_7_COMPLETE);
						action = LOG_ACTION_AUDIT_RECHARGE_ORDER_REVIEW_SUCCESS;
					} else if (!allow && status == ORDER_STATUS_6_LAST_AUDIT) {
						logDataMap.put("status", ORDER_STATUS_8_REFUSE);
						action = LOG_ACTION_AUDIT_RECHARGE_ORDER_REVIEW_FAIL;
					}
					if (remark != null && !remark.isEmpty() && !"".equals(remark.trim())) {
						logDataMap.put("remark", remark);
					}
					logDataMap.put("amount", Bean.getAmount());
					logDataMap.put("orderType", orderType);
					responseJSONObject = new JSONObject(logDataMap);
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_data_ops_log", USERID, memberAccId, orderId, OPS_TYPE_MANAGER, ACC_TYPE_MEMBER, action,
							responseJSONObject.toString(), USERIP);
				}
			} else {
				todo = ((IOrderDao) dao).updateWithdrawalAuditOrder(USERID, orderId, allow, bankSid, bankCheckAmount, bankDepositTime);
				if (todo && remark != null && !remark.isEmpty() && !"".equals(remark.trim())) {
					todo = ((IOrderDao) dao).insertRemark(orderId, false, remark);
				}
				if (todo) {
					if (allow && status == ORDER_STATUS_2_1ST_AUDITING) {
						logDataMap.put("bankSid", bankSid);
						logDataMap.put("bankCheckAmount", bankCheckAmount);
						logDataMap.put("bankDepositTime", bankDepositTime);
						logDataMap.put("status", ORDER_STATUS_3_WAIT_2ND_AUDIT);
						action = LOG_ACTION_AUDIT_WITHDRAWAL_ORDER_SUCCESS;
					} else if (!allow && status == ORDER_STATUS_2_1ST_AUDITING) {
						logDataMap.put("status", ORDER_STATUS_8_REFUSE);
						action = LOG_ACTION_AUDIT_WITHDRAWAL_ORDER_FAIL;
					} else if (allow && status == ORDER_STATUS_6_LAST_AUDIT) {
						logDataMap.put("status", ORDER_STATUS_7_COMPLETE);
						action = LOG_ACTION_AUDIT_WITHDRAWAL_ORDER_REVIEW_SUCCESS;
					} else if (!allow && status == ORDER_STATUS_6_LAST_AUDIT) {
						logDataMap.put("status", ORDER_STATUS_8_REFUSE);
						action = LOG_ACTION_AUDIT_WITHDRAWAL_ORDER_REVIEW_FAIL;
					}
					if (remark != null && !remark.isEmpty() && !"".equals(remark.trim())) {
						logDataMap.put("remark", remark);
					}
					logDataMap.put("amount", Bean.getAmount());
					logDataMap.put("orderType", orderType);
					responseJSONObject = new JSONObject(logDataMap);
					todo = dao.setLogToDBNoCommit("ctt_manager.ctt_data_ops_log", USERID, memberAccId, orderId, OPS_TYPE_MANAGER, 2, action,
							responseJSONObject.toString(), USERIP);
				}
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
			Bean = null;
			responseJSONObject = null;
			if (logDataMap != null) {
				logDataMap.clear();
				logDataMap = null;
			}
		}
		return todo;
	}

	@Override
	public Map<String, Object> checkOrderReview(long orderId) {
		return ((IOrderDao) dao).checkOrderReview(orderId);
	}

	@Override
	public Map<String, Object> getAuditRechargeOrderData(long orderId, int orderType) {
		return ((IOrderDao) dao).getRechargeOrderData(USERID, orderId);
	}

	@Override
	public Map<String, Object> getAuditWithdrawalOrderData(long orderId, int orderType) {
		return ((IOrderDao) dao).getWithdrawalOrderData(USERID, orderId);
	}

	@Override
	public Map<String, Object> checkRechargeCarryOutOrder(long orderId, int orderType) {
		return ((IOrderDao) dao).checkCarryOutOrder(orderId, ORDER_RECHARGE);
	}

	@Override
	public Map<String, Object> checkWithdrawalCarryOutOrder(long orderId, int orderType) {
		return ((IOrderDao) dao).checkCarryOutOrder(orderId, ORDER_WITHDRAWAL);
	}

	public boolean checkMemberAccName(String accName) {
		return ((IOrderDao) dao).checkMemberAccName(accName);
	}

	@Override
	public int getOrderRechargeCarryOutCountTotal(int status, String accName, String auditorAccName, String firstDate, String lastDate) {
		return ((IOrderDao) dao).getOrderRechargeCountTotal(status, accName, auditorAccName, firstDate, lastDate, rechargeCarryOutStatus);
	}

	@Override
	public int getOrderRechargeUndoneCountTotal(int status, String accName, String auditorAccName, String firstDate, String lastDate) {
		return ((IOrderDao) dao).getOrderRechargeCountTotal(status, accName, auditorAccName, firstDate, lastDate, rechargeUndoneStatus);
	}

	@Override
	public int getOrderWithdrawalCarryOutCountTotal(int status, String accName, String auditorAccName, String firstDate, String lastDate) {
		return ((IOrderDao) dao).getOrderWithdrawalCountTotal(status, accName, auditorAccName, firstDate, lastDate, withdrawalCarryOutStatus);
	}

	@Override
	public int getOrderWithdrawalUndoneCountTotal(int status, String accName, String auditorAccName, String firstDate, String lastDate) {
		return ((IOrderDao) dao).getOrderWithdrawalCountTotal(status, accName, auditorAccName, firstDate, lastDate, withdrawalUndoneStatus);
	}

	@Override
	public int getOrderReviewCountTotal() {
		return ((IOrderDao) dao).getOrderReviewCountTotal(ReviewStatus, ReviewStatus);
	}

}
