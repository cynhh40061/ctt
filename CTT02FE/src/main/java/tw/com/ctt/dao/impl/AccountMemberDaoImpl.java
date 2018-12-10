package tw.com.ctt.dao.impl;

import static tw.com.ctt.constant.AccountMemberConstant.*;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.IAccountMemberDao;
import tw.com.ctt.model.ManagerRatioBean;
import tw.com.ctt.model.MemBean;
import tw.com.ctt.model.PunchGameRecordsBean;
import tw.com.ctt.util.CalculateMemberRatio;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class AccountMemberDaoImpl extends BaseDao implements IAccountMemberDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5393598519122647798L;
	private static final Logger LOG = LogManager.getLogger(AccountMemberDaoImpl.class.getName());

	@Override
	public Map<String, Object> getMemAccData(String tokenId, long accId) {
		StringBuilder sb = new StringBuilder();
		List<Object> selectObj = new ArrayList<Object>();
		Map<String, Object> result = new ConcurrentHashMap<String, Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return result;

			} else {
				sb.append("SELECT B.acc_name , B.balance, B.nickname, B.withdraw_amount, B.locked_amount FROM \n");
				sb.append("(SELECT acc_id from ctt_manager.ctt_member_token WHERE token_id = ? and acc_id = ?)A\n");
				sb.append("INNER JOIN \n");
				sb.append(
						"(SELECT acc_id,acc_name,balance,nickname,(balance - locked_amount) AS withdraw_amount,locked_amount FROM ctt_manager.ctt_member_acc )B\n");
				sb.append("on A.acc_id = B.acc_id;\n");

				selectObj.add(tokenId);
				selectObj.add(accId);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);

				for (int i = 0; i < listMap.size(); i++) {
					for (Object key : listMap.get(i).keySet()) {
						result.put(key.toString(), listMap.get(i).get(key.toString()));
					}
				}
				LOG.debug(result.toString());

			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (listMap != null) {
				listMap.clear();
				listMap = null;

			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}

			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return result;
	}

	@Override
	public boolean updatePwd(long accId, String oldPwd, String newPwd) {
		StringBuilder sb = new StringBuilder();
		List<Object> updateObj = new ArrayList<Object>();
		boolean result = false;
		int updateCount = 0;

		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return result;

			} else {
				sb.append("update ctt_manager.ctt_member_acc set pwd = ? , pwd_status = 0 where acc_id = ? and pwd = ?");

				updateObj.add(newPwd);
				updateObj.add(accId);
				updateObj.add(oldPwd);

				updateCount = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);

				if (updateCount > 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}

			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return result;
	}

	@Override
	public boolean updateWithdrawPwd(long accId, String withdrawPwd) {
		StringBuilder sb = new StringBuilder();
		List<Object> updateObj = new ArrayList<Object>();
		boolean result = false;
		int updateCount = 0;

		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return result;

			} else {
				sb.append("update ctt_manager.ctt_member_acc set pwd_withdraw = ? , pwd_withdraw_status = 0 where acc_id = ?");

				updateObj.add(withdrawPwd);
				updateObj.add(accId);

				updateCount = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);

				if (updateCount > 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}

			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return result;
	}

	@Override
	public long insertWithdrawal(long accId, String accName, String bankAccName, String bank, String bankAcc, int status, BigDecimal amount,
			int currency) {

		Map<String, Object> insertMap = null;
		StringBuilder sb = null;
		List<Object> updateObj = null;
		long orderId = 0;

		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.info("no connection");
				return 0;
			} else {
				insertMap = new ConcurrentHashMap<String, Object>();
				sb = new StringBuilder();
				updateObj = new ArrayList<Object>();

				sb.append("INSERT INTO ctt_manager.ctt_withdrawal_order ");
				sb.append("(acc_id,acc_name,bank_acc_name,bank,bank_acc,status,amount,currency,create_datetime) ");
				sb.append("VALUES ");
				sb.append("(?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP);");
				updateObj.add(accId);
				updateObj.add(accName);
				updateObj.add(bankAccName);
				updateObj.add(bank);
				updateObj.add(bankAcc);
				updateObj.add(status);
				updateObj.add(amount);
				updateObj.add(currency);

				orderId = StmtUtil.insertNoCommit(this.WRITE_CONN, sb.toString(), updateObj);
			}
		} catch (Exception e) {
			orderId = 0;
			LOG.info("insertWithdrawal_Exception===" + e);
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}
		}
		return orderId;
	}

	// 充值
	@Override
	public long insertRecharge(long accId, String accName, String bankAccName, String bank, String bankAcc, String bankSid, int rechargeType,
			int status, BigDecimal amount, int currency, String note, int backOrderStatus, String bankDateTime) {
		Map<String, Object> insertMap = null;
		StringBuilder sb = null;
		List<Object> updateObj = null;
		long orderId = 0;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("no connection");
				return 0;
			} else {
				insertMap = new ConcurrentHashMap<String, Object>();
				sb = new StringBuilder();
				updateObj = new ArrayList<Object>();

				sb.append("INSERT INTO ctt_manager.ctt_recharge_order ");
				sb.append(
						"(acc_id,acc_name,bank_acc_name,bank,bank_acc,bank_sid,recharge_type,status,amount,bank_check_amount,currency,note,back_order_status,bank_deposit_datetime,create_datetime) ");
				sb.append("VALUES ");
				sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP);");
				updateObj.add(accId);
				updateObj.add(accName);
				updateObj.add(bankAccName);
				updateObj.add(bank);
				updateObj.add(bankAcc);
				updateObj.add(bankSid);
				updateObj.add(rechargeType);
				updateObj.add(status);
				updateObj.add(amount);
				updateObj.add(amount);
				updateObj.add(currency);
				updateObj.add(note);
				updateObj.add(backOrderStatus);
				updateObj.add(bankDateTime);

				orderId = StmtUtil.insertNoCommit(this.WRITE_CONN, sb.toString(), updateObj);
			}
		} catch (Exception e) {
			orderId = 0;
			LOG.info("insertRecharge_Exception===" + e);
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return orderId;
	}

	@Override
	public Map<String, Object> getAccDetails(long accId) {
		StringBuilder sb = new StringBuilder();
		List<Object> selectObj = new ArrayList<Object>();
		Map<String, Object> result = null;
		List<Map<String, Object>> listMap = null;

		try {
			result = new ConcurrentHashMap<String, Object>();
			listMap = new ArrayList<Map<String, Object>>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return result;

			} else {
				sb.append(
						"select member_real_name , phone_number ,qq_acc , wechat_acc from ctt_manager.ctt_member_basic_setting where  acc_id = ? \n");

				selectObj.add(accId);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);

				for (int i = 0; i < listMap.size(); i++) {
					for (Object key : listMap.get(i).keySet()) {
						result.put(key.toString(), listMap.get(i).get(key.toString()));
					}
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}

			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return result;
	}

	@Override
	public boolean updateAccDetails(long accId, String memberRealName, String phoneNumber, String qqAcc, String wechatAcc) {
		StringBuilder sb = new StringBuilder();
		List<Object> updateObj = new ArrayList<Object>();
		boolean todo = false;
		int count = 0;

		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return todo;

			} else {
				sb.append(
						"update ctt_manager.ctt_member_basic_setting set member_real_name = (case when member_real_name <> '' and member_real_name is not null then member_real_name else ? end)");
				sb.append(" , phone_number = (case when phone_number = ? then phone_number else ? end) \n");
				sb.append(" , qq_acc = (case when qq_acc = ? then qq_acc else ? end)\n");
				sb.append(" , wechat_acc = (case when wechat_acc = ? then wechat_acc else ? end) \n");
				sb.append(" where  acc_id = ? \n");

				updateObj.add(memberRealName);
				updateObj.add(phoneNumber);
				updateObj.add(phoneNumber);
				updateObj.add(qqAcc);
				updateObj.add(qqAcc);
				updateObj.add(wechatAcc);
				updateObj.add(wechatAcc);
				updateObj.add(accId);

				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);
				LOG.debug(count);

				if (count > 0) {
					todo = true;
				}

			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}

			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return todo;
	}

	@Override
	public boolean updateAccNickname(long accId, String nickname) {
		StringBuilder sb = new StringBuilder();
		List<Object> updateObj = new ArrayList<Object>();
		boolean todo = false;
		int count = 0;

		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return todo;

			} else {
				sb.append("update ctt_manager.ctt_member_acc set nickname = (case when nickname = ? then nickname else ? end) \n");
				sb.append(" where  acc_id = ? \n");

				updateObj.add(nickname);
				updateObj.add(nickname);
				updateObj.add(accId);

				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);
				LOG.debug(count);

				if (count > 0) {
					todo = true;
				}

			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}

			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return todo;
	}

	@Override
	public boolean addBankCard(long accId, String bank, String bankAcc, String bankAccName, String bankCardBranches, String area) {
		boolean boole = false;
		StringBuilder sb = null;
		List<Object> params = null;
		int count = 0;
		try {
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return boole;
			} else {
				sb.append(
						"INSERT INTO ctt_manager.ctt_member_bank_card (card_number,acc_id,bank,bank_acc_name,bank_acc,bank_card_branches,area,create_datetime) \n");
				sb.append("SELECT \n");
				sb.append("card_number,?,?,?,?,?,?,CURRENT_TIMESTAMP \n");
				params.add(accId);
				params.add(bank);
				params.add(bankAccName);
				params.add(bankAcc);
				params.add(bankCardBranches);
				params.add(area);
				sb.append("FROM \n");
				sb.append("(SELECT MAX(card_number) AS card_number FROM \n");
				sb.append("(SELECT MAX(card_number)+1 AS card_number FROM ctt_manager.ctt_member_bank_card WHERE acc_id = ? \n");
				params.add(accId);
				sb.append("UNION \n");
				sb.append("SELECT 1 AS card_number FROM DUAL) A \n");
				sb.append(") MAX \n");
				sb.append("WHERE \n");
				sb.append("(SELECT COUNT(acc_id) FROM ctt_manager.ctt_member_bank_card WHERE acc_id = ? AND bank_card_status = TRUE) < 2 \n");
				params.add(accId);
				sb.append("AND \n");
				sb.append("(SELECT member_real_name FROM ctt_manager.ctt_member_basic_setting WHERE acc_id = ?) = ? \n");
				params.add(accId);
				params.add(bankAccName);

				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);
				LOG.debug("count=" + count);
				if (count == 1) {
					boole = true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params.clear();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			count = 0;
		}
		return boole;
	}

	@Override
	public List<Map<String, Object>> getBankCardInfo(long accId) {
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return listMap;

			} else {
				sb.append("SELECT \n");
				sb.append(
						"bank_card_id,acc_id ,bank ,bank_acc_name ,bank_acc ,bank_card_branches ,area,bank_card_status ,create_datetime ,disable_datetime \n");
				sb.append("FROM \n");
				sb.append("ctt_manager.ctt_member_bank_card \n");
				sb.append("WHERE \n");
				sb.append("acc_id = ? AND bank_card_status = TRUE \n");
				params.add(accId);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				LOG.debug("listMap=" + listMap);
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params.clear();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return listMap;
	}

	@Override
	public Map<String, Object> getPlatformCashBc(int platformId) {
		StringBuilder sb = new StringBuilder();
		List<Object> selectObj = new ArrayList<Object>();
		Map<String, Object> result = null;
		List<Map<String, Object>> listMap = null;

		try {
			result = new ConcurrentHashMap<String, Object>();
			listMap = new ArrayList<Map<String, Object>>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return result;

			} else {

				sb.append("select A.cash_bc_id as cashBcId , B.acc_name as cashBcName , B.handicap  from \n");
				sb.append("(select cash_bc_id from ctt_manager.ctt_platform where platform_id = ?)A \n");
				sb.append("inner join \n");
				sb.append("(select acc_id,acc_name,handicap from ctt_manager.ctt_manager_acc ) B \n");
				sb.append("on A.cash_bc_id = B.acc_id \n");

				selectObj.add(platformId);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);

				for (int i = 0; i < listMap.size(); i++) {
					for (Object key : listMap.get(i).keySet()) {
						result.put(key.toString(), listMap.get(i).get(key.toString()));
					}
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}

			if (listMap != null) {
				listMap.clear();
				listMap = null;
			}

			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return result;
	}

	@Override
	public boolean checkMemberAcc(String accName) {
		StringBuilder sb = null;
		List<Object> selectObj = null;
		boolean todo = false;
		List<Map<String, Object>> listMap = null;

		try {

			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return todo;

			} else {
				listMap = new ArrayList<Map<String, Object>>();
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();

				sb.append("select count(acc_id) as count from ctt_manager.ctt_member_acc where acc_name Like ? \n");

				selectObj.add(accName);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);

				for (int i = 0; i < listMap.size(); i++) {
					if (Integer.parseInt(listMap.get(i).get("count").toString()) == 0) {
						todo = true;
					} else {
						todo = false;
					}
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}

			if (listMap != null) {
				listMap.clear();
				listMap = null;
			}

			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return todo;
	}

	@Override
	public long addMember(String accName, String pwd, int handicap) throws SQLException {
		List<Object> insertObj = null;
		StringBuilder sb = null;
		long accId = 0;
		try {

			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("NO CONN");
				return accId;
			} else {
				insertObj = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append(
						"  insert into ctt_manager.ctt_member_acc (acc_name , nickname , member_type , pwd ,pwd_status , pwd_withdraw_status , handicap) (select ? ,'' , "
								+ GRNERAL_MEM + " , ? ,0, ? , index_of_account&?  from ctt_manager.ctt_lottery_handicap where handicap_id = 1) ");

				insertObj.add(accName);
				insertObj.add(pwd);
				insertObj.add(1);
				insertObj.add(handicap);

				accId = StmtUtil.insertNoCommit(this.WRITE_CONN, sb.toString(), insertObj);

			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);

		} finally {
			if (insertObj != null) {
				insertObj.clear();
				insertObj = null;
			}

			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return accId;
	}

	@Override
	public boolean addMemberLevel(long accId, long upAccId) {
		boolean todo = false;
		StringBuilder sb = null;
		List<Object> insertObje = null;
		try {

			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				sb = new StringBuilder();
				insertObje = new ArrayList<Object>();

				sb.append("INSERT INTO ctt_manager.ctt_member_acc_level (acc_id,upper_acc_level_type,sc,bc,co,sa,ag)");
				sb.append("(SELECT ? as acc_id, acc_level_type as upper_acc_level_type,");
				sb.append("CASE acc_level_type WHEN 2 THEN acc_id ELSE sc END AS sc ,");
				sb.append("CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN acc_id ELSE bc END AS bc,");
				sb.append("CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN acc_id ELSE co END AS co,");
				sb.append("CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN acc_id ELSE sa END AS sa,");
				sb.append("CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 WHEN 6 THEN acc_id ELSE ag END AS ag ");
				sb.append("FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ? AND  acc_level_type>1 AND acc_level_type<7)");

				insertObje.add(accId);
				insertObje.add(upAccId);

				long cou = StmtUtil.insertNoCommit(this.WRITE_CONN, sb.toString(), insertObje);

				if (cou != 0) {
					todo = true;
				} else {
					todo = false;
				}

			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertObje != null) {
				insertObje.clear();
				insertObje = null;
			}

		}
		return todo;
	}

	@Override
	public boolean addCreate(long accId, String ip) {
		String[] ipArray = null;
		boolean todo = false;
		Map<String, Object> insertMap = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				insertMap = new ConcurrentHashMap<String, Object>();
				if (ip != null && !ip.isEmpty() && !"".equals(ip.trim())) {
					ipArray = ip.split("\\.");
					ip = (ipArray == null || ipArray.length != 4) ? "0.0.0.0" : ip;
				}

				insertMap.put("acc_id", accId);
				insertMap.put("update_acc_id", accId);
				insertMap.put("ip1", Integer.parseInt(ipArray[0]));
				insertMap.put("ip2", Integer.parseInt(ipArray[1]));
				insertMap.put("ip3", Integer.parseInt(ipArray[2]));
				insertMap.put("ip4", Integer.parseInt(ipArray[3]));

				long cou = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_member_create", insertMap);
				if (cou == -1) {
					todo = true;
				} else {
					todo = false;
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return todo;
	}

	@Override
	public boolean addLastLogin(long accId) {
		boolean todo = false;
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				insertMap.put("acc_id", accId);
				insertMap.put("ip1", 0);
				insertMap.put("ip2", 0);
				insertMap.put("ip3", 0);
				insertMap.put("ip4", 0);

				long cou = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_member_last_login", insertMap);
				if (cou == -1) {
					todo = true;
				} else {
					todo = false;
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return todo;
	}

	@Override
	public boolean addLastUpdate(long accId, String ip) {
		boolean todo = false;
		String[] ipArray = null;
		Map<String, Object> insertMap = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				insertMap = new ConcurrentHashMap<String, Object>();
				if (ip != null && !ip.isEmpty() && !"".equals(ip.trim())) {
					ipArray = ip.split("\\.");
					ip = (ipArray == null || ipArray.length != 4) ? "0.0.0.0" : ip;
				}

				insertMap.put("acc_id", accId);
				insertMap.put("update_acc_id", accId);
				insertMap.put("ip1", Integer.parseInt(ipArray[0]));
				insertMap.put("ip2", Integer.parseInt(ipArray[1]));
				insertMap.put("ip3", Integer.parseInt(ipArray[2]));
				insertMap.put("ip4", Integer.parseInt(ipArray[3]));

				long cou = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_member_last_update", insertMap);
				if (cou == -1) {
					todo = true;
				} else {
					todo = false;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return todo;
	}

	public boolean addAccDeails(long accId) {
		boolean todo = false;
		Map<String, Object> insertMap = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				insertMap = new ConcurrentHashMap<String, Object>();
				insertMap.put("acc_id", accId);

				long count = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_member_basic_setting", insertMap);
				if (count == -1) {
					todo = true;
				} else {
					todo = false;
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return todo;
	}

	public boolean addPunchGameAccInfo(long accId) {
		boolean todo = false;
		Map<String, Object> insertMap = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				insertMap = new ConcurrentHashMap<String, Object>();

				insertMap.put("acc_id", accId);

				long count = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_member_punch_game_acc_info", insertMap);

				if (count == -1) {
					todo = true;
				} else {
					todo = false;
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return todo;
	}

	private String getMapString(Map<String, Object> map, String key) {
		if (!map.keySet().contains(key) || map.get(key) == null) {
			return "";
		} else {
			return "" + map.get(key);
		}
	}

	@Override
	public Map<String, Object> addWithdrawalOrderGetBankCardInfo(long accId, String orderWithdrawPwd, int bankInfoList) {
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		Map<String, Object> result = new ConcurrentHashMap<String, Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return result;

			} else {
				sb.append("SELECT \n");
				sb.append("A.acc_id \n");
				sb.append(",B.bank AS bank \n");
				sb.append(",B.bank_acc_name AS bank_acc_name \n");
				sb.append(",B.bank_acc AS bank_acc \n");
				sb.append("FROM \n");
				sb.append("(SELECT acc_id FROM ctt_manager.ctt_member_acc WHERE acc_id = ? AND pwd_withdraw = ?) A \n");
				params.add(accId);
				params.add(orderWithdrawPwd);
				sb.append("inner join \n");
				sb.append(
						"(SELECT acc_id,bank,bank_acc_name,bank_acc FROM ctt_manager.ctt_member_bank_card WHERE acc_id = ? AND bank_card_id = ? AND bank_card_status = 1) B \n");
				params.add(accId);
				params.add(bankInfoList);
				sb.append("ON A.acc_id = B.acc_id \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				LOG.debug("listMap=" + listMap);
				for (int i = 0; i < listMap.size(); i++) {
					for (Object key : listMap.get(i).keySet()) {
						result.put(key.toString(), listMap.get(i).get(key.toString()));
					}
				}
				LOG.debug("result=" + result);
			}
		} catch (Exception e) {
			result = null;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (listMap != null) {
				listMap.clear();
				listMap = null;
			}
			if (params != null) {
				params.clear();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return result;
	}

	@Override
	public int checkMemType(long accId) {
		LOG.debug("checkMemType");
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int memberType = -1;
		try {
			sb = new StringBuilder();

			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return -1;
			} else {
				sb.append("SELECT member_type FROM ctt_manager.ctt_member_acc WHERE acc_id = ? \n");
				if (this.READ_CONN.getAutoCommit()) {
					this.READ_CONN.setAutoCommit(false);
				}
				ps = this.READ_CONN.prepareStatement(sb.toString());
				ps.setLong(1, accId);
				rs = ps.executeQuery();
				if (rs.next()) {
					memberType = rs.getInt("member_type");
				}
				LOG.info("memberType===" + memberType);
			}
		} catch (Exception e) {
			memberType = -1;
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
		return memberType;
	}

	@Override
	public Map<String, Object> getMemAccInfo(long accId) {
		StringBuilder sb = null;
		List<Object> selectObj = null;
		Map<String, Object> result = null;
		List<Map<String, Object>> listMap = null;
		try {
			sb = new StringBuilder();
			selectObj = new ArrayList<Object>();
			result = new ConcurrentHashMap<String, Object>();
			listMap = new ArrayList<Map<String, Object>>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {
				sb.append("SELECT \n");
				sb.append(
						"acc_id,acc_name,pwd_status,pwd_withdraw_status,nickname,member_type,locked_amount,balance,(balance - locked_amount) AS withdraw_amount \n");
				sb.append("FROM \n");
				sb.append("ctt_manager.ctt_member_acc WHERE acc_id = ? \n");

				selectObj.add(accId);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);

				for (int i = 0; i < listMap.size(); i++) {
					for (Object key : listMap.get(i).keySet()) {
						result.put(key.toString(), listMap.get(i).get(key.toString()));
					}
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (listMap != null) {
				listMap.clear();
				listMap = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return result;
	}

	@Override
	public boolean charge(long accId, BigDecimal withdrawalAmount) {
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
				sb.append("update ctt_manager.ctt_member_acc set balance = balance - ? where acc_id = ?");
				insertMap.add(withdrawalAmount);
				insertMap.add(accId);
				cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), insertMap);
				LOG.debug("cou=======" + cou);
				if (cou == 1) {
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
	public Map<String, Object> getCashierInfo(int cashierId) {
		StringBuilder sb = null;
		List<Object> selectObj = null;
		List<Map<String, Object>> listMap = null;
		Map<String, Object> result = null;
		try {
			sb = new StringBuilder();
			selectObj = new ArrayList<Object>();
			listMap = new ArrayList<Map<String, Object>>();
			result = new ConcurrentHashMap<String, Object>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {
				sb.append("SELECT cashier_id,cashier_name,bank,bank_acc,data FROM ctt_manager.ctt_cashier_info WHERE cashier_id = ? \n");
				selectObj.add(cashierId);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);
				LOG.debug("\n\n\nlistMap=" + listMap);
				for (int i = 0; i < listMap.size(); i++) {
					for (Object key : listMap.get(i).keySet()) {
						result.put(key.toString(), listMap.get(i).get(key.toString()));
					}
				}
				LOG.debug("\n\n\nresult=" + result);
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (listMap != null) {
				listMap.clear();
				listMap = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return result;
	}

	@Override
	public boolean updateWithdrawPwdFailed(long accId) {
		StringBuilder sb = new StringBuilder();
		List<Object> updateObj = new ArrayList<Object>();
		boolean todo = false;
		int count = 0;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				sb.append("update ctt_manager.ctt_member_acc set pwd_withdraw_failed = \n");
				sb.append("pwd_withdraw_failed+1 \n");
				sb.append("WHERE acc_id = ? \n");
				updateObj.add(accId);

				count = StmtUtil.update(this.WRITE_CONN, sb.toString(), updateObj);
				LOG.debug("updateWithdrawPwdFailed=====" + count);

				if (count == 1) {
					todo = true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return todo;
	}

	@Override
	public int getWithdrawPwdFailed(long accId) {
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int cou = 0;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return cou;
			} else {
				sb = new StringBuilder();
				sb.append("SELECT pwd_withdraw_failed FROM ctt_manager.ctt_member_acc WHERE acc_id = ? \n");
				ps = this.WRITE_CONN.prepareStatement(sb.toString());
				ps.setLong(1, accId);
				rs = ps.executeQuery();
				if (rs.next()) {
					cou = rs.getInt("pwd_withdraw_failed");
				}
			}
		} catch (Exception e) {
			cou = 0;
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
		return cou;
	}

	@Override
	public MemBean getAccDetailsForLog(long accId) {
		StringBuilder sb = null;
		List<Object> param = null;
		List<Object> list = null;
		MemBean bean = new MemBean();

		try {
			sb = new StringBuilder();
			param = new ArrayList<Object>();
			list = new ArrayList<Object>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				sb.append("SELECT \n");
				sb.append("member_real_name \n");
				sb.append(",phone_number \n");
				sb.append(",qq_acc \n");
				sb.append(",wechat_acc \n");
				sb.append(",(SELECT nickname FROM ctt_manager.ctt_member_acc WHERE acc_id = ?) AS nickname \n");
				param.add(accId);
				sb.append("FROM \n");
				sb.append("ctt_manager.ctt_member_basic_setting \n");
				sb.append("WHERE \n");
				sb.append("acc_id = ? \n");
				param.add(accId);

				list = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), param, bean);
				if (list.size() == 1) {
					bean = (MemBean) list.get(0);
				}
			}
		} catch (Exception e) {
			bean = null;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (param != null) {
				param = new ArrayList<Object>();
				param = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (list != null) {
				list.clear();
				list = null;
			}
		}
		return bean;
	}

	@Override
	public Map<String, Object> searchRecordsTotle(long accId, String startTime, String endTime) {
		StringBuilder sb = null;
		List<Object> params = null;
		List<Map<String, Object>> tmpMap = null;
		Map<String, Object> map = null;
		try {
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkRead();

			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return map;
			} else {
				sb.append("SELECT \n");
				sb.append("COUNT(1) AS count \n");
				sb.append(",SUM(A.bet) AS totleBet \n");
				sb.append(",SUM(A.winGoal) AS totleWinGoal \n");
				sb.append(",(SUM(A.bet)+SUM(A.winGoal)) AS totleNetAmount \n");
				sb.append("FROM \n");
				sb.append("( \n");
				sb.append("SELECT \n");
				sb.append("  p1_acc_id AS acc_id \n");
				sb.append("  ,bet \n");
				sb.append("  ,p1_acc_win_goal AS winGoal \n");
				sb.append("FROM \n");
				sb.append("  ctt_manager.ctt_game_punche_records \n");
				sb.append("WHERE \n");
				sb.append("  (start_time > DATE_FORMAT(?,'%Y/%m/%d %T.%f') && start_time < DATE_FORMAT(?,'%Y/%m/%d %T.%f')) \n");
				params.add(startTime);
				params.add(endTime);
				sb.append(" AND p1_acc_id = ? \n");
				params.add(accId);
				sb.append("UNION ALL \n");
				sb.append("SELECT \n");
				sb.append("  p2_acc_id AS acc_id \n");
				sb.append("  ,bet \n");
				sb.append("  ,p2_acc_win_goal AS winGoal \n");
				sb.append("FROM \n");
				sb.append("  ctt_manager.ctt_game_punche_records \n");
				sb.append("WHERE \n");
				sb.append("  (start_time > DATE_FORMAT(?,'%Y/%m/%d %T.%f') && start_time < DATE_FORMAT(?,'%Y/%m/%d %T.%f')) \n");
				params.add(startTime);
				params.add(endTime);
				sb.append(" AND p2_acc_id = ? \n");
				params.add(accId);
				sb.append(")A \n");

				tmpMap = StmtUtil.queryToMap(READ_CONN, sb.toString(), params);
				if (tmpMap.size() == 1 && Integer.parseInt(tmpMap.get(0).get("count").toString()) > 0) {
					map = tmpMap.get(0);
				}
			}
		} catch (Exception e) {
			tmpMap = null;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params.clear();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> searchRecords(long accId, String accName, String startTime, String endTime, int firstCount, int count) {
		StringBuilder sb = null;
		List<Object> params = null;
		List<Object> list = null;
		Map<String, Object> tmpMap = null;
		List<Map<String, Object>> tmpMapList = null;
		try {
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkRead();
			tmpMapList = new ArrayList<Map<String, Object>>();

			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				sb.append("SELECT \n");
				sb.append("  game_id,server_id \n");
				sb.append("  ,p1_acc_id AS acc_id \n");
				sb.append("  ,start_time,end_time \n");
				sb.append("  ,bet,p1_acc_start_balance AS acc_start_balance \n");
				sb.append("  ,p1_acc_win_goal AS acc_win_goal \n");
				sb.append("  ,game_process \n");
				sb.append("  ,game_type, \n");
				sb.append("  CASE WHEN result = 0 THEN TRUE ELSE FALSE END AS result \n");
				sb.append("FROM  \n");
				sb.append("  ctt_manager.ctt_game_punche_records \n");
				sb.append("WHERE  \n");
				sb.append("  (start_time > DATE_FORMAT(?,'%Y/%m/%d %T.%f') && start_time < DATE_FORMAT(?,'%Y/%m/%d %T.%f')) \n");
				params.add(startTime);
				params.add(endTime);
				sb.append(" AND p1_acc_id = ? \n");
				params.add(accId);
				sb.append("UNION \n");
				sb.append("SELECT \n");
				sb.append("  game_id \n");
				sb.append("  ,server_id \n");
				sb.append("  ,p2_acc_id AS acc_id \n");
				sb.append("  ,start_time,end_time,bet \n");
				sb.append("  ,p2_acc_start_balance AS acc_start_balance \n");
				sb.append("  ,p2_acc_win_goal AS acc_win_goal \n");
				sb.append("  ,game_process \n");
				sb.append("  ,game_type, \n");
				sb.append("  CASE WHEN result = 1 THEN TRUE ELSE FALSE END AS result \n");
				sb.append("FROM  \n");
				sb.append("  ctt_manager.ctt_game_punche_records \n");
				sb.append("WHERE  \n");
				sb.append("  (start_time > DATE_FORMAT(?,'%Y/%m/%d %T.%f') && start_time < DATE_FORMAT(?,'%Y/%m/%d %T.%f')) \n");
				params.add(startTime);
				params.add(endTime);
				sb.append(" AND p2_acc_id = ? \n");
				params.add(accId);
				sb.append("ORDER BY end_time DESC \n");
				sb.append("limit ?,? \n");
				params.add(firstCount);
				params.add(count);

				list = new ArrayList<Object>();
				list = StmtUtil.queryToBean(READ_CONN, sb.toString(), params, new PunchGameRecordsBean());
				if (list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						PunchGameRecordsBean bean = (PunchGameRecordsBean) list.get(i);
						JSONObject jsonMap = new JSONObject(bean.getGameProcess());
						String searchKey[] = { "gameName", "accName", "accId", "gameId", "gameType", "startTime", "endTime", "startBalance",
								"endBalance", "bet", "netAmount", "winGoal", "gameTimes", "gameProcess" };
						Object searchByAccNameValue[] = { "三國猜拳王", accName, Long.parseLong("" + bean.getAccId()), "" + bean.getGameId(),
								Integer.parseInt("" + bean.getGameType()), bean.getStartTime(), bean.getEndTime(),
								new BigDecimal("" + bean.getAccStartBalance()),
								new BigDecimal("" + bean.getAccStartBalance()).add(new BigDecimal("" + bean.getAccWinGoal())),
								new BigDecimal("" + bean.getBet()), new BigDecimal("" + bean.getAccWinGoal()),
								new BigDecimal("" + bean.getBet()).add(new BigDecimal("" + bean.getAccWinGoal())), jsonMap.get("gameTimes"),
								jsonMap.get("gameProcess") };
						tmpMap = new ConcurrentHashMap<String, Object>();
						for (int k = 0; k < searchKey.length; k++) {
							tmpMap.put(searchKey[k], searchByAccNameValue[k]);
						}
						tmpMapList.add(tmpMap);
					}
				}
			}
		} catch (Exception e) {
			tmpMapList = null;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params.clear();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (list != null) {
				list.clear();
				list = null;
			}
		}
		return tmpMapList;
	}

	/**
	 * addMemberRatioinfo to ctt_member_ratio by insertNoCommitByMap(no
	 * this.WRITE_CONN.commit())
	 */
	@Override
	public boolean addMemberRatio(long accId, int gameType, int upperMinRatio) throws SQLException {
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				LOG.debug(accId + "," + gameType + "," + upperMinRatio);
				insertMap.put("acc_id", accId);
				insertMap.put("game_type", Byte.parseByte("" + gameType));
				insertMap.put("upper_min_ratio", Byte.parseByte("" + upperMinRatio));
				insertMap.put("next_upper_min_ratio", Byte.parseByte("" + upperMinRatio));

				long count = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_member_ratio", insertMap);
				LOG.debug("addMemberRatio===" + count);
				if (count != 0) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return false;
	}

	@Override
	public List<Integer> selectGameRatioMem(long upAccId, int memGameUpperMin, int gameType) {
		StringBuilder sb = null;
		List<Object> selectList = null;
		List<Object> listObj = null;
		List<Integer> listIntRatio = null;
		List<Map<String, Integer>> managerRatio = null;
		Map<String, Integer> layer1 = null;
		Map<String, Integer> layer2 = null;
		Map<String, Integer> layer3 = null;
		Map<String, Integer> layer4 = null;
		Map<String, Integer> layer5 = null;
		Map<String, Integer> layer6 = null;
		Map<String, Integer> layer7 = null;
		Map<String, Integer> layer8 = null;
		Map<String, Integer> layer9 = null;
		Map<String, Integer> layer10 = null;
		Map<String, Integer> layer11 = null;
		Map<String, Integer> layer12 = null;
		Map<String, Integer> layer13 = null;
		Map<String, Integer> layer14 = null;
		Map<String, Integer> layer15 = null;
		Map<String, Integer> layer16 = null;

		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listIntRatio;
			} else {

				sb = new StringBuilder();
				selectList = new ArrayList<Object>();
				listObj = new ArrayList<Object>();

				sb.append("	select     			\n");
				sb.append("	case when A.sc =0 then 0 else Bsc.next_upper_min_ratio end as sc_game_upper_min,		\n");
				sb.append("	case when A.sc =0 then 0 else Bsc.next_upper_max_ratio end as sc_game_upper_max,		\n");
				sb.append("	case when A.sc =0 then 0 else Bsc.next_current_max_ratio end as sc_game_current_max,		\n");
				sb.append("	case when A.bc =0 then 0 else Bbc.next_upper_min_ratio end as bc_game_upper_min,		\n");
				sb.append("	case when A.bc =0 then 0 else Bbc.next_upper_max_ratio end as bc_game_upper_max,		\n");
				sb.append("	case when A.bc =0 then 0 else Bbc.next_current_max_ratio end as bc_game_current_max,		\n");
				sb.append("	case when A.co =0 then 0 else Bco.next_upper_min_ratio end as co_game_upper_min,		\n");
				sb.append("	case when A.co =0 then 0 else Bco.next_upper_max_ratio end as co_game_upper_max,		\n");
				sb.append("	case when A.co =0 then 0 else Bco.next_current_max_ratio end as co_game_current_max,		\n");
				sb.append("	case when A.sa =0 then 0 else Bsa.next_upper_min_ratio end as sa_game_upper_min,		\n");
				sb.append("	case when A.sa =0 then 0 else Bsa.next_upper_max_ratio end as sa_game_upper_max,		\n");
				sb.append("	case when A.sa =0 then 0 else Bsa.next_current_max_ratio end as sa_game_current_max,		\n");
				sb.append("	case when A.ag =0 then 0 else Bag.next_upper_min_ratio end as ag_game_upper_min,		\n");
				sb.append("	case when A.ag =0 then 0 else Bag.next_upper_max_ratio end as ag_game_upper_max,		\n");
				sb.append("	case when A.ag =0 then 0 else Bag.next_current_max_ratio end as ag_game_current_max,		\n");
				sb.append("	case when A.ag1 =0 then 0 else Bag1.next_upper_min_ratio end as ag1_game_upper_min,		\n");
				sb.append("	case when A.ag1 =0 then 0 else Bag1.next_upper_max_ratio end as ag1_game_upper_max,		\n");
				sb.append("	case when A.ag1 =0 then 0 else Bag1.next_current_max_ratio end as ag1_game_current_max,		\n");
				sb.append("	case when A.ag2 =0 then 0 else Bag2.next_upper_min_ratio end as ag2_game_upper_min,		\n");
				sb.append("	case when A.ag2 =0 then 0 else Bag2.next_upper_max_ratio end as ag2_game_upper_max,		\n");
				sb.append("	case when A.ag2 =0 then 0 else Bag2.next_current_max_ratio end as ag2_game_current_max,		\n");
				sb.append("	case when A.ag3 =0 then 0 else Bag3.next_upper_min_ratio end as ag3_game_upper_min,		\n");
				sb.append("	case when A.ag3 =0 then 0 else Bag3.next_upper_max_ratio end as ag3_game_upper_max,		\n");
				sb.append("	case when A.ag3 =0 then 0 else Bag3.next_current_max_ratio end as ag3_game_current_max,		\n");
				sb.append("	case when A.ag4 =0 then 0 else Bag4.next_upper_min_ratio end as ag4_game_upper_min,		\n");
				sb.append("	case when A.ag4 =0 then 0 else Bag4.next_upper_max_ratio end as ag4_game_upper_max,		\n");
				sb.append("	case when A.ag4 =0 then 0 else Bag4.next_current_max_ratio end as ag4_game_current_max,		\n");
				sb.append("	case when A.ag5 =0 then 0 else Bag5.next_upper_min_ratio end as ag5_game_upper_min,		\n");
				sb.append("	case when A.ag5 =0 then 0 else Bag5.next_upper_max_ratio end as ag5_game_upper_max,		\n");
				sb.append("	case when A.ag5 =0 then 0 else Bag5.next_current_max_ratio end as ag5_game_current_max,		\n");
				sb.append("	case when A.ag6 =0 then 0 else Bag6.next_upper_min_ratio end as ag6_game_upper_min,		\n");
				sb.append("	case when A.ag6 =0 then 0 else Bag6.next_upper_max_ratio end as ag6_game_upper_max,		\n");
				sb.append("	case when A.ag6 =0 then 0 else Bag6.next_current_max_ratio end as ag6_game_current_max,		\n");
				sb.append("	case when A.ag7 =0 then 0 else Bag7.next_upper_min_ratio end as ag7_game_upper_min,		\n");
				sb.append("	case when A.ag7 =0 then 0 else Bag7.next_upper_max_ratio end as ag7_game_upper_max,		\n");
				sb.append("	case when A.ag7 =0 then 0 else Bag7.next_current_max_ratio end as ag7_game_current_max,		\n");
				sb.append("	case when A.ag8 =0 then 0 else Bag8.next_upper_min_ratio end as ag8_game_upper_min,		\n");
				sb.append("	case when A.ag8 =0 then 0 else Bag8.next_upper_max_ratio end as ag8_game_upper_max,		\n");
				sb.append("	case when A.ag8 =0 then 0 else Bag8.next_current_max_ratio end as ag8_game_current_max,		\n");
				sb.append("	case when A.ag9 =0 then 0 else Bag9.next_upper_min_ratio end as ag9_game_upper_min,		\n");
				sb.append("	case when A.ag9 =0 then 0 else Bag9.next_upper_max_ratio end as ag9_game_upper_max,		\n");
				sb.append("	case when A.ag9 =0 then 0 else Bag9.next_current_max_ratio end as ag9_game_current_max,		\n");
				sb.append("	case when A.ag10 =0 then 0 else Bag10.next_upper_min_ratio end as ag10_game_upper_min,		\n");
				sb.append("	case when A.ag10 =0 then 0 else Bag10.next_upper_max_ratio end as ag10_game_upper_max,		\n");
				sb.append("	case when A.ag10 =0 then 0 else Bag10.next_current_max_ratio end as ag10_game_current_max,		\n");
				sb.append("	? as mem_game_upper_min,		\n");
				selectList.add(memGameUpperMin);
				sb.append("	A.upper_acc_level_type as upperAccLevelType 		\n");
				sb.append("	from 		\n");
				sb.append("	(	\n");
				sb.append("	SELECT acc_level_type as upper_acc_level_type,		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN acc_id ELSE sc END AS sc ,		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN acc_id ELSE bc END AS bc,		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN acc_id ELSE co END AS co,		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN acc_id ELSE sa END AS sa,		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN acc_id ELSE ag END AS ag, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN acc_id ELSE ag1 END AS ag1, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN acc_id ELSE ag2 END AS ag2, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN 0 WHEN 9 THEN acc_id ELSE ag3 END AS ag3, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN 0 WHEN 9 THEN 0 WHEN 10 THEN acc_id ELSE ag4 END AS ag4, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN 0 WHEN 9 THEN 0 WHEN 10 THEN 0 		\n");
				sb.append("	WHEN 11 THEN acc_id ELSE ag5 END AS ag5, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN 0 WHEN 9 THEN 0 WHEN 10 THEN 0 		\n");
				sb.append("	WHEN 11 THEN 0 WHEN 12 THEN acc_id ELSE ag6 END AS ag6, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN 0 WHEN 9 THEN 0 WHEN 10 THEN 0 		\n");
				sb.append("	WHEN 11 THEN 0 WHEN 12 THEN 0 WHEN 13 THEN acc_id ELSE ag7 END AS ag7, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN 0 WHEN 9 THEN 0 WHEN 10 THEN 0 		\n");
				sb.append("	WHEN 11 THEN 0 WHEN 12 THEN 0 WHEN 13 THEN 0 WHEN 14 THEN acc_id ELSE ag8 END AS ag8, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN 0 WHEN 9 THEN 0 WHEN 10 THEN 0 		\n");
				sb.append("	WHEN 11 THEN 0 WHEN 12 THEN 0 WHEN 13 THEN 0 WHEN 14 THEN 0 WHEN 15 THEN acc_id ELSE ag9 END AS ag9, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN 0 WHEN 9 THEN 0 WHEN 10 THEN 0 		\n");
				sb.append("	WHEN 11 THEN 0 WHEN 12 THEN 0 WHEN 13 THEN 0 WHEN 14 THEN 0 WHEN 15 THEN 0 		\n");
				sb.append("	WHEN 16 THEN acc_id ELSE ag10 END AS ag10 		\n");
				sb.append("	FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ? AND  acc_level_type>" + ACC_LEVEL_COM + " AND acc_level_type<"
						+ ACC_LEVEL_MEM + "	\n");
				selectList.add(upAccId);
				sb.append("	) A 		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bsc		\n");
				selectList.add(gameType);
				sb.append("	on Bsc.acc_id = A.sc		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bbc		\n");
				selectList.add(gameType);
				sb.append("	on Bbc.acc_id = A.bc		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bco		\n");
				selectList.add(gameType);
				sb.append("	on Bco.acc_id = A.co		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bsa		\n");
				selectList.add(gameType);
				sb.append("	on Bsa.acc_id = A.sa		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag		\n");
				selectList.add(gameType);
				sb.append("	on Bag.acc_id = A.ag		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag1		\n");
				selectList.add(gameType);
				sb.append("	on Bag1.acc_id = A.ag1		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag2		\n");
				selectList.add(gameType);
				sb.append("	on Bag2.acc_id = A.ag2		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag3		\n");
				selectList.add(gameType);
				sb.append("	on Bag3.acc_id = A.ag3		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag4		\n");
				selectList.add(gameType);
				sb.append("	on Bag4.acc_id = A.ag4		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag5		\n");
				selectList.add(gameType);
				sb.append("	on Bag5.acc_id = A.ag5		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag6		\n");
				selectList.add(gameType);
				sb.append("	on Bag6.acc_id = A.ag6		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag7		\n");
				selectList.add(gameType);
				sb.append("	on Bag7.acc_id = A.ag7		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag8		\n");
				selectList.add(gameType);
				sb.append("	on Bag8.acc_id = A.ag8		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag9		\n");
				selectList.add(gameType);
				sb.append("	on Bag9.acc_id = A.ag9		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag10		\n");
				selectList.add(gameType);
				sb.append("	on Bag10.acc_id = A.ag10			\n");

				listObj = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), selectList, new ManagerRatioBean());

				if (listObj.size() == 1) {

					ManagerRatioBean managerRatioBean = (ManagerRatioBean) listObj.get(0);

					LOG.debug(managerRatioBean.toString());

					int levelType = managerRatioBean.getUpperAccLevelType();
					if (levelType > ACC_LEVEL_COM) {
						managerRatio = new ArrayList<Map<String, Integer>>();

						if (levelType >= ACC_LEVEL_SC) {
							layer1 = new HashMap<String, Integer>();
							layer1.put("upperMin", managerRatioBean.getScGameUpperMin());
							layer1.put("upperMax", managerRatioBean.getScGameUpperMax());
							layer1.put("currentMax", managerRatioBean.getScGameCurrentMax());
							managerRatio.add(layer1);
						}
						if (levelType >= ACC_LEVEL_BC) {
							layer2 = new HashMap<String, Integer>();
							layer2.put("upperMin", managerRatioBean.getBcGameUpperMin());
							layer2.put("upperMax", managerRatioBean.getBcGameUpperMax());
							layer2.put("currentMax", managerRatioBean.getBcGameCurrentMax());
							managerRatio.add(layer2);
						}
						if (levelType >= ACC_LEVEL_CO) {
							layer3 = new HashMap<String, Integer>();
							layer3.put("upperMin", managerRatioBean.getCoGameUpperMin());
							layer3.put("upperMax", managerRatioBean.getCoGameUpperMax());
							layer3.put("currentMax", managerRatioBean.getCoGameCurrentMax());
							managerRatio.add(layer3);
						}
						if (levelType >= ACC_LEVEL_SA) {
							layer4 = new HashMap<String, Integer>();
							layer4.put("upperMin", managerRatioBean.getSaGameUpperMin());
							layer4.put("upperMax", managerRatioBean.getSaGameUpperMax());
							layer4.put("currentMax", managerRatioBean.getSaGameCurrentMax());
							managerRatio.add(layer4);
						}
						if (levelType >= ACC_LEVEL_AG) {
							layer5 = new HashMap<String, Integer>();
							layer5.put("upperMin", managerRatioBean.getAgGameUpperMin());
							layer5.put("upperMax", managerRatioBean.getAgGameUpperMax());
							layer5.put("currentMax", managerRatioBean.getAgGameCurrentMax());
							managerRatio.add(layer5);
						}
						if (levelType >= ACC_LEVEL_AG1) {
							layer6 = new HashMap<String, Integer>();
							layer6.put("upperMin", managerRatioBean.getAg1GameUpperMin());
							layer6.put("upperMax", managerRatioBean.getAg1GameUpperMax());
							layer6.put("currentMax", managerRatioBean.getAg1GameCurrentMax());
							managerRatio.add(layer6);
						}
						if (levelType >= ACC_LEVEL_AG2) {
							layer7 = new HashMap<String, Integer>();
							layer7.put("upperMin", managerRatioBean.getAg2GameUpperMin());
							layer7.put("upperMax", managerRatioBean.getAg2GameUpperMax());
							layer7.put("currentMax", managerRatioBean.getAg2GameCurrentMax());
							managerRatio.add(layer7);
						}
						if (levelType >= ACC_LEVEL_AG3) {
							layer8 = new HashMap<String, Integer>();
							layer8.put("upperMin", managerRatioBean.getAg3GameUpperMin());
							layer8.put("upperMax", managerRatioBean.getAg3GameUpperMax());
							layer8.put("currentMax", managerRatioBean.getAg3GameCurrentMax());
							managerRatio.add(layer8);
						}
						if (levelType >= ACC_LEVEL_AG4) {
							layer9 = new HashMap<String, Integer>();
							layer9.put("upperMin", managerRatioBean.getAg4GameUpperMin());
							layer9.put("upperMax", managerRatioBean.getAg4GameUpperMax());
							layer9.put("currentMax", managerRatioBean.getAg4GameCurrentMax());
							managerRatio.add(layer9);
						}
						if (levelType >= ACC_LEVEL_AG5) {
							layer10 = new HashMap<String, Integer>();
							layer10.put("upperMin", managerRatioBean.getAg5GameUpperMin());
							layer10.put("upperMax", managerRatioBean.getAg5GameUpperMax());
							layer10.put("currentMax", managerRatioBean.getAg5GameCurrentMax());
							managerRatio.add(layer10);
						}
						if (levelType >= ACC_LEVEL_AG6) {
							layer11 = new HashMap<String, Integer>();
							layer11.put("upperMin", managerRatioBean.getAg6GameUpperMin());
							layer11.put("upperMax", managerRatioBean.getAg6GameUpperMax());
							layer11.put("currentMax", managerRatioBean.getAg6GameCurrentMax());
							managerRatio.add(layer11);
						}
						if (levelType >= ACC_LEVEL_AG7) {
							layer12 = new HashMap<String, Integer>();
							layer12.put("upperMin", managerRatioBean.getAg7GameUpperMin());
							layer12.put("upperMax", managerRatioBean.getAg7GameUpperMax());
							layer12.put("currentMax", managerRatioBean.getAg7GameCurrentMax());
							managerRatio.add(layer12);
							// layer.clear();
						}
						if (levelType >= ACC_LEVEL_AG8) {
							layer13 = new HashMap<String, Integer>();
							layer13.put("upperMin", managerRatioBean.getAg8GameUpperMin());
							layer13.put("upperMax", managerRatioBean.getAg8GameUpperMax());
							layer13.put("currentMax", managerRatioBean.getAg8GameCurrentMax());
							managerRatio.add(layer13);
						}
						if (levelType >= ACC_LEVEL_AG9) {
							layer14 = new HashMap<String, Integer>();
							layer14.put("upperMin", managerRatioBean.getAg9GameUpperMin());
							layer14.put("upperMax", managerRatioBean.getAg9GameUpperMax());
							layer14.put("currentMax", managerRatioBean.getAg9GameCurrentMax());
							managerRatio.add(layer14);
							// layer.clear();
						}
						if (levelType >= ACC_LEVEL_AG10) {
							layer15 = new HashMap<String, Integer>();
							layer15.put("upperMin", managerRatioBean.getAg10GameUpperMin());
							layer15.put("upperMax", managerRatioBean.getAg10GameUpperMax());
							layer15.put("currentMax", managerRatioBean.getAg10GameCurrentMax());
							managerRatio.add(layer15);
						}
						layer16 = new HashMap<String, Integer>();
						layer16.put("upperMin", managerRatioBean.getMemGameUpperMin());
						layer16.put("upperMax", managerRatioBean.getMemGameUpperMin());
						managerRatio.add(layer16);

						listIntRatio = new ArrayList<Integer>();
						LOG.debug("managerRatio:" + managerRatio);

						listIntRatio = CalculateMemberRatio.getRatio(managerRatio);
						LOG.debug("listIntRatio:" + listIntRatio);

					}

				}

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectList != null) {
				selectList.clear();
				selectList = null;
			}
			if (listObj != null) {
				listObj.clear();
				listObj = null;
			}
			if (layer1 != null) {
				layer1.clear();
				layer1 = null;
			}
			if (layer2 != null) {
				layer2.clear();
				layer2 = null;
			}
			if (layer3 != null) {
				layer3.clear();
				layer3 = null;
			}
			if (layer4 != null) {
				layer4.clear();
				layer4 = null;
			}
			if (layer5 != null) {
				layer5.clear();
				layer5 = null;
			}
			if (layer6 != null) {
				layer6.clear();
				layer6 = null;
			}
			if (layer7 != null) {
				layer7.clear();
				layer7 = null;
			}
			if (layer8 != null) {
				layer8.clear();
				layer8 = null;
			}
			if (layer9 != null) {
				layer9.clear();
				layer9 = null;
			}
			if (layer10 != null) {
				layer10.clear();
				layer10 = null;
			}
			if (layer11 != null) {
				layer11.clear();
				layer11 = null;
			}
			if (layer12 != null) {
				layer12.clear();
				layer12 = null;
			}
			if (layer13 != null) {
				layer13.clear();
				layer13 = null;
			}
			if (layer14 != null) {
				layer14.clear();
				layer14 = null;
			}
			if (layer15 != null) {
				layer15.clear();
				layer15 = null;
			}
			if (layer16 != null) {
				layer16.clear();
				layer16 = null;
			}

		}
		return listIntRatio;
	}

	@Override
	public boolean memberRatioTotal(long memberId, int gameType, List<Integer> ratio) throws SQLException {
		StringBuilder sb = null;
		Map<String, Object> insertMap = null;

		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				sb = new StringBuilder();
				insertMap = new ConcurrentHashMap<String, Object>();
				LOG.debug("ratio:" + ratio);
				insertMap.put("acc_id", memberId);
				insertMap.put("game_type", gameType);

				String[] managerRatioArr = { "com_ratio", "sc_ratio", "bc_ratio", "co_ratio", "sa_ratio", "ag_ratio", "ag1_ratio", "ag2_ratio",
						"ag3_ratio", "ag4_ratio", "ag5_ratio", "ag6_ratio", "ag7_ratio", "ag8_ratio", "ag9_ratio", "ag10_ratio" };
				String[] nextManagerRatioArr = { "next_com_ratio", "next_sc_ratio", "next_bc_ratio", "next_co_ratio", "next_sa_ratio",
						"next_ag_ratio", "next_ag1_ratio", "next_ag2_ratio", "next_ag3_ratio", "next_ag4_ratio", "next_ag5_ratio", "next_ag6_ratio",
						"next_ag7_ratio", "next_ag8_ratio", "next_ag9_ratio", "next_ag10_ratio" };

				for (int i = 0; i < managerRatioArr.length; i++) {
					if (i < ratio.size()) {
						insertMap.put(managerRatioArr[i], ratio.get(i));
						insertMap.put(nextManagerRatioArr[i], ratio.get(i));
					} else {
						insertMap.put(managerRatioArr[i], 0);
						insertMap.put(nextManagerRatioArr[i], 0);
					}
				}

				long cou = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_member_total_ratio", insertMap);

				if (cou != 0) {
					return true;
				}

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return false;
	}

}
