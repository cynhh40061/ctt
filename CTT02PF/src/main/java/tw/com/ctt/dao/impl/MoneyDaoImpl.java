package tw.com.ctt.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IMoneyDao;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class MoneyDaoImpl extends BaseDao implements IMoneyDao {

	private static final long serialVersionUID = 467520563998229196L;
	private static final Logger LOG = LogManager.getLogger(CheckPwdDaoImpl.class.getName());

	public MoneyDaoImpl() {
		super();
	}
	@Override
	public boolean upAccDeposit(long accId, long upaccId, BigDecimal money, String tableName, int accDepositType) {
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int cou = 0;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				List<Object> insertMap = new ArrayList<Object>();
				sb.append("update " + tableName + "  set balance = balance +" + money + " where acc_id = ?");
				insertMap.add(accId);

				List<Object> insertMap2 = new ArrayList<Object>();
				sb2.append("update ctt_manager.ctt_manager_acc  set balance = balance -" + money + " where acc_id = ?");
				insertMap2.add(upaccId);
				cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), insertMap);
				LOG.debug("cou======="+cou);
				if(cou == 1) {
					cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb2.toString(), insertMap2);
				}
				LOG.debug("cou======="+cou);
				if(cou == 1) {
					todo=true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException, " + e.getMessage());
					ShowLog.err(LOG, e);
				}
				rs = null;
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException, " + e.getMessage());
					ShowLog.err(LOG, e);
				}
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (sb2 != null) {
				sb2.setLength(0);
				sb2 = null;
			}
		}
		return todo;
	}

	@Override
	public boolean scDeposit(long accId, BigDecimal depositMoney, int accDepositType) {
		StringBuilder sb = new StringBuilder();
		int cou = 0;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				List<Object> insertMap = new ArrayList<Object>();
				sb.append("update ctt_manager.ctt_manager_acc set balance = balance +" + depositMoney
						+ " where acc_id = ?");
				insertMap.add(accId);
				cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), insertMap);
				LOG.debug("cou======="+cou);
				if(cou == 1) {
					todo = true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			cou = 0;
		}
		return todo;
	}

	@Override
	public boolean withdrawal(long accId, BigDecimal withdrawalMoney, String tableName, int accWithdrawalType) {
		StringBuilder sb = new StringBuilder();
		int cou = 0;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				List<Object> insertMap = new ArrayList<Object>();
				sb.append("update " + tableName + " set balance = balance -" + withdrawalMoney + " where acc_id = ?");
				insertMap.add(accId);
				cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), insertMap);
				LOG.debug("cou======="+cou);
				if(cou == 1) {
					todo = true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return todo;
	}
	@Override
	public boolean depositDetails(long accId, BigDecimal depositMoney) {
		StringBuilder sb = new StringBuilder();
		List<Object> params = null;
		int cou = 0;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				return todo;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();
				
				sb.append("update \n");
				sb.append("ctt_manager.ctt_member_basic_setting \n");
				sb.append("set \n");
				sb.append("recharge_cash_total = recharge_cash_total+? \n");
				params.add(depositMoney);
				sb.append(",first_recharge_datetime = (CASE WHEN first_recharge_datetime <> '' AND first_recharge_datetime IS NOT NULL THEN first_recharge_datetime ELSE CURRENT_TIMESTAMP END) \n");
				sb.append(",last_recharge_datetime = CURRENT_TIMESTAMP \n");
				sb.append(",recharge_total = recharge_total+1 \n");
				sb.append("where \n");
				sb.append("acc_id = ? \n");
				params.add(accId);
				
				cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);
				LOG.debug("cou======="+cou);
				if(cou == 1) {
					todo = true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if(params != null) {
				params.clear();
				params=null;
			}
			cou = 0;
		}
		return todo;
	}
	@Override
	public boolean withdrawalDetails(long accId, BigDecimal withdrawalMoney) {
		StringBuilder sb = new StringBuilder();
		List<Object> params = null;
		int cou = 0;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				return todo;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();
				
				sb.append("update \n");
				sb.append("ctt_manager.ctt_member_basic_setting \n");
				sb.append("set \n");
				sb.append("withdraw_cash_total = withdraw_cash_total+? \n");
				params.add(withdrawalMoney);
				sb.append(",first_withdraw_datetime = (CASE WHEN first_withdraw_datetime <> '' AND first_withdraw_datetime IS NOT NULL THEN first_withdraw_datetime ELSE CURRENT_TIMESTAMP END) \n");
				sb.append(",last_withdraw_datetime = CURRENT_TIMESTAMP \n");
				sb.append(",withdraw_total = withdraw_total+1 \n");
				sb.append("where \n");
				sb.append("acc_id = ? \n");
				params.add(accId);
				
				cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);
				LOG.debug("cou======="+cou);
				if(cou == 1) {
					todo = true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if(params != null) {
				params.clear();
				params=null;
			}
			cou = 0;
		}
		return todo;
	}
	@Override
	public BigDecimal checkBalance(long accId,String tableName){
		LOG.debug("checkBalance");
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BigDecimal balance = null;
		try {
			sb = new StringBuilder();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return balance;
			} else {
				sb.append("SELECT acc_id,balance FROM "+tableName+" WHERE acc_id = ? \n");
				ps = this.READ_CONN.prepareStatement(sb.toString());
				ps.setLong(1, accId);
				rs = ps.executeQuery();
				if (rs.next()) {
					balance = rs.getBigDecimal("balance");
				}
				LOG.info("balance===" + balance);
			}
		} catch (Exception e) {
			balance = null;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException, " + e.getMessage());
					ShowLog.err(LOG, e);
				}
				rs = null;
			}
		}
		return balance;
	}
	@Override
	public boolean memOrderDeposit(long accId, BigDecimal depositMoney, int accDepositType) {
		StringBuilder sb = new StringBuilder();
		int cou = 0;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				List<Object> insertMap = new ArrayList<Object>();
				sb.append("update ctt_manager.ctt_member_acc set balance = balance +" + depositMoney
						+ " where acc_id = ?");
				insertMap.add(accId);
				cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), insertMap);
				LOG.debug("cou======="+cou);
				if(cou == 1) {
					todo = true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			cou = 0;
		}
		return todo;
	}
}
