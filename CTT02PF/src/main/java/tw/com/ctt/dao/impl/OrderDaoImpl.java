package tw.com.ctt.dao.impl;

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

import tw.com.ctt.dao.IOrderDao;
import tw.com.ctt.model.OrderBean;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;
import static tw.com.ctt.constant.OrderConstant.*;

public class OrderDaoImpl extends BaseDao implements IOrderDao {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2282218859364987660L;
	private static final Logger LOG = LogManager.getLogger(OrderDaoImpl.class.getName());

	private static final String[] orderBy = { "B.order_id", "B.amount", "B.create_datetime", "B.first_datetime" };// 1-5,default:0
	private static final String[] ASC = { "ASC", "DESC" };// 0-1

	public OrderDaoImpl() {
		super();
	}

	// 充值會員accId
	@Override
	public long getInsertRechargeOrderAccId(String accName) {
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long accId = 0;
		try {
			sb = new StringBuilder();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.info("no connection");
				return accId;
			} else {
				sb.append("select acc_id from ctt_manager.ctt_member_acc where acc_name = '" + accName + "'");
				ps = this.READ_CONN.prepareStatement(sb.toString());
				rs = ps.executeQuery();
				if (rs.next()) {
					accId = rs.getLong("acc_id");
				}
			}
		} catch (Exception e) {
			accId = 0;
			LOG.info("getInsertRechargeOrderAccId_Exception===" + e);
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (sb != null) {
					sb.setLength(0);
				}
				sb = null;
				if (ps != null) {
					ps.close();
				}
				rs = null;
			} catch (Exception e) {
				accId = 0;
				LOG.info("getInsertRechargeOrderAccId_final_Exception===" + e);
				ShowLog.err(LOG, e);
			}
		}
		return accId;
	}

	// 充值
	@Override
	public long insertRecharge(long accId, String accName, String bankAccName, String bank, String bankAcc, String bankSid,
			BigDecimal bankCheckAmount, String bankDepositDatetime, int rechargeType, int status, BigDecimal amount, int currency, String note,
			int backOrderStatus) {
		LOG.debug("insertRecharge");
		StringBuilder sb = null;
		List<Object> insertList = null;
		long orderId = 0;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.info("no connection");
				return orderId;
			} else {
				sb = new StringBuilder();
				insertList = new ArrayList<Object>();

				sb.append("INSERT INTO ctt_manager.ctt_recharge_order  \n");
				sb.append("(acc_id \n");
				sb.append(",acc_name \n");
				sb.append(",bank_acc_name \n");
				sb.append(",bank \n");
				sb.append(",bank_acc \n");
				sb.append(",bank_sid \n");
				sb.append(",bank_check_amount \n");
				if (!"".equals(bankDepositDatetime)) {
					sb.append(",bank_deposit_datetime \n");
				}
				sb.append(",recharge_type \n");
				sb.append(",status \n");
				sb.append(",amount \n");
				sb.append(",currency \n");
				sb.append(",note \n");
				sb.append(",back_order_status \n");
				sb.append(",create_datetime) VALUES (?,?,?,?,?,?,? \n");
				if (!"".equals(bankDepositDatetime)) {
					sb.append(",? \n");
				}
				sb.append(",?,?,?,?,?,?,CURRENT_TIMESTAMP) \n");
				insertList.add(accId);
				insertList.add(accName);
				insertList.add(bankAccName);
				insertList.add(bank);
				insertList.add(bankAcc);
				insertList.add(bankSid);
				insertList.add(bankCheckAmount);
				if (!"".equals(bankDepositDatetime)) {
					insertList.add(bankDepositDatetime);
				}
				insertList.add(rechargeType);
				insertList.add(status);
				insertList.add(amount);
				insertList.add(currency);
				insertList.add(note);
				insertList.add(backOrderStatus);

				orderId = StmtUtil.insertNoCommit(this.WRITE_CONN, sb.toString(), insertList);
				LOG.info("insertRecharge_orderId===" + orderId);
			}
		} catch (Exception e) {
			LOG.info("insertRecharge_Exception===" + e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertList != null) {
				insertList.clear();
				insertList = null;
			}
		}
		return orderId;
	}

	@Override
	public boolean insertRemark(long orderId, boolean isRechange, String remark) {
		LOG.debug("insertRemark");
		Map<String, Object> insertMap = null;
		StringBuilder sb = null;
		int count = 0;
		List<Object> params = null;
		String table = "ctt_manager.ctt_recharge_order";
		try {
			insertMap = new ConcurrentHashMap<String, Object>();
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			if (!isRechange) {
				table = "ctt_manager.ctt_withdrawal_order";
			}
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.info("no connection");
				return false;
			} else {
				sb.append("INSERT INTO ctt_manager.ctt_order_remark (order_id, status, remark) \n");
				sb.append("SELECT ? AS order_id, status, ? AS remark FROM " + table + " WHERE order_id = ? \n");
				params.add(orderId);
				params.add(remark);
				params.add(orderId);

				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);
				LOG.info("insertRemark_count===" + count);
				if (count == 1) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.info("insertRemark_Exception===" + e);
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> searchOrderRechargeData(String rechargeStatus, int status, String accName, String auditorAccName,
			String firstDate, String lastDate, int aes, int sort, int firstCount, int count) {
		LOG.debug("searchOrderRechargeData");
		List<Map<String, Object>> result = null;
		StringBuilder sb = null;
		List<Object> params = null;
		String firstDatetimeStr = "";
		String lastDatetimeStr = "";
		try {
			result = new ArrayList<Map<String, Object>>();
			sb = new StringBuilder();
			params = new ArrayList<Object>();

			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.info("no connection");
				return result;
			} else {
				LOG.debug(lastDate);
				LOG.debug(firstDate);
				if (!"".equals(firstDate) && !"".equals(lastDate)) {
					firstDatetimeStr = firstDate + " 00:00:00";
					lastDatetimeStr = lastDate + " 23:59:59";
				} else {
					firstDatetimeStr = "";
					lastDatetimeStr = "";
				}

				sb.append("select B.order_id as orderId , B.acc_name as accName ,B.amount , B.bank , B.create_datetime as createDatetime,\n");
				sb.append(
						"B.status , B.note , B.first_auditor as firstAuditor , B.first_datetime as firstDatetime , B.second_auditor as secondAuditor , \n");
				sb.append(
						"B.second_datetime as secondDatetime ,case when B.back_order_status = 1 then '補單' else '' end as backOrderStatus , B.recharge_type as rechargeType , C.acc_name as firstAccName ,D.acc_name as secondAccName , '0' as orderType\n");
				sb.append("from (\n");
				sb.append(
						"(select order_id,first_auditor,second_auditor from ctt_manager.ctt_recharge_order where case when ? <> '' and ? is not null then first_auditor =(select acc_id from ctt_manager.ctt_manager_acc where acc_name = ?) or second_auditor = (select acc_id  from ctt_manager.ctt_manager_acc where acc_name = ?)\n");
				params.add(auditorAccName);
				params.add(auditorAccName);
				params.add(auditorAccName);
				params.add(auditorAccName);
				sb.append(
						" else true end and case when ? <> '' and ? is not null then acc_name = ? else true end and case when ? <> '' and  ? is not null and ? <> '' and ? is not null then create_datetime BETWEEN ? and ? else true end and \n");
				params.add(accName);
				params.add(accName);
				params.add(accName);

				params.add(firstDatetimeStr);
				params.add(firstDatetimeStr);
				params.add(lastDatetimeStr);
				params.add(lastDatetimeStr);

				params.add(firstDatetimeStr);
				params.add(lastDatetimeStr);
				sb.append("case ? when 0 then status IN (" + rechargeStatus
						+ ") when 1 then status = 1 when 2 then status = 2 when 3 then status = 3 when 4 then status = 4 when 5 then status = 5 when 6 then status = 6 when 7 then status = 7 when 8 then status = 8 when 9 then status = 9 else false end\n");
				params.add(status);
				if (rechargeStatus == rechargeCarryOutStatus) {
					sb.append(
							"and create_datetime BETWEEN DATE_FORMAT(concat(DATE_FORMAT(date_sub(CURRENT_TIMESTAMP,interval 30 day),'%Y/%m/%d'),' 00:00:00'),'%Y/%m/%d %H:%i:%s') and DATE_FORMAT(concat(DATE_FORMAT(CURRENT_TIMESTAMP,'%Y/%m/%d'),' 23:59:59'),'%Y/%m/%d %H:%i:%s')");
				}
				sb.append(" ) A\n");
				sb.append(" inner join \n");
				sb.append(" (select * from ctt_manager.ctt_recharge_order ) B\n");
				sb.append(" on A.order_id = B.order_id\n");
				sb.append(" left join \n");
				sb.append(" (select acc_id , acc_name from ctt_manager.ctt_manager_acc ) C\n");
				sb.append(" on  C.acc_id = A.first_auditor\n");
				sb.append("  left join \n");
				sb.append("  (select acc_id , acc_name from ctt_manager.ctt_manager_acc ) D\n");
				sb.append(" on  D.acc_id = A.second_auditor \n");
				sb.append(")\n");
				sb.append("ORDER BY " + orderBy[sort] + " " + ASC[aes] + " \n");
				sb.append(" LIMIT ?,? \n");
				params.add(firstCount);
				params.add(count);

				result = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				LOG.debug(result.toString());
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> searchOrderWithdrawalData(String withdrawalStatus, int status, String accName, String auditorAccName,
			String firstDate, String lastDate, int aes, int sort, int firstCount, int count) {
		LOG.debug("searchOrderWithdrawalData");
		List<Map<String, Object>> result = null;
		StringBuilder sb = null;
		String firstDatetimeStr = "";
		String lastDatetimeStr = "";
		List<Object> params = null;
		try {
			result = new ArrayList<Map<String, Object>>();
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {

				if (!"".equals(firstDate) && !"".equals(lastDate)) {
					firstDatetimeStr = firstDate + " 00:00:00";
					lastDatetimeStr = lastDate + " 23:59:59";
				} else {
					firstDatetimeStr = "";
					lastDatetimeStr = "";
				}

				sb.append(
						"select B.order_id as orderId , B.acc_name as accName ,B.amount , B.bank , B.create_datetime as createDatetime,B.bank_acc_name as bankAccName , B.bank_acc as bankAcc , \n");
				sb.append(
						"B.status , B.first_auditor as firstAuditor , B.first_datetime as firstDatetime , case when amount >= 1000 then '大額'  when amount < 1000 then '小額' else '' end as type  ,  C.acc_name as firstAccName , '1' as orderType\n");
				sb.append("from ( \n");
				sb.append(
						"(select order_id,first_auditor from ctt_manager.ctt_withdrawal_order where case when ? <> '' and ? is not null then first_auditor =(select acc_id from ctt_manager.ctt_manager_acc where acc_name = ?) else true end \n");
				params.add(auditorAccName);
				params.add(auditorAccName);
				params.add(auditorAccName);
				sb.append(
						"and case when ? <> '' and ? is not null then acc_name = ? else true end and case when ? <> '' and  ? is not null and ? <> '' and ? is not null then create_datetime BETWEEN ? and ? else true end and \n");
				params.add(accName);
				params.add(accName);
				params.add(accName);

				params.add(firstDatetimeStr);
				params.add(firstDatetimeStr);
				params.add(lastDatetimeStr);
				params.add(lastDatetimeStr);

				params.add(firstDatetimeStr);
				params.add(lastDatetimeStr);
				sb.append("case ? when 0 then status IN (" + withdrawalStatus
						+ ") when 1 then status = 1 when 2 then status = 2 when 3 then status = 3 when 4 then status = 4 when 5 then status = 5 when 6 then status = 6 when 7 then status = 7 when 8 then status = 8 when 9 then status = 9 else false end\n");
				params.add(status);
				if (withdrawalStatus == withdrawalCarryOutStatus) {
					sb.append(
							" and create_datetime BETWEEN DATE_FORMAT(concat(DATE_FORMAT(date_sub(CURRENT_TIMESTAMP,interval 30 day),'%Y/%m/%d'),' 00:00:00'),'%Y/%m/%d %H:%i:%s') and DATE_FORMAT(concat(DATE_FORMAT(CURRENT_TIMESTAMP,'%Y/%m/%d'),' 23:59:59'),'%Y/%m/%d %H:%i:%s')");
				}
				sb.append(" ) A\n");
				sb.append(" inner join \n");
				sb.append(" (select * from ctt_manager.ctt_withdrawal_order ) B\n");
				sb.append(" on A.order_id = B.order_id\n");
				sb.append(" left join \n");
				sb.append(" (select acc_id , acc_name from ctt_manager.ctt_manager_acc ) C\n");
				sb.append(" on  C.acc_id = A.first_auditor\n");
				sb.append(")\n");
				sb.append("ORDER BY " + orderBy[sort] + " " + ASC[aes] + " \n");
				sb.append(" LIMIT ?,? \n");
				params.add(firstCount);
				params.add(count);

				result = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				LOG.debug(result.toString());
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getOrderReviewData(String rechargeStatus, String withdrawalStatus, int aes, int sort, int firstCount,
			int count) {
		LOG.debug("getOrderReviewData");
		List<Map<String, Object>> result = null;
		StringBuilder sb = null;
		List<Object> params = null;
		try {
			result = new ArrayList<Map<String, Object>>();
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {
				sb.append("select B.order_id as orderId , B.acc_name as accName ,B.amount , B.bank , B.create_datetime as createDatetime,\n");
				sb.append(
						"B.status , B.first_auditor as firstAuditor , B.first_datetime as firstDatetime , B.second_auditor as secondAuditor , B.order_type as orderType ,\n");
				sb.append(
						"B.second_datetime as secondDatetime , B.back_order_status as backOrderStatus , B.recharge_type as rechargeType , C.acc_name as firstAccName ,D.acc_name as secondAccName \n");
				sb.append(" from (\n");
				sb.append("(select * from(\n");
				sb.append(
						"select order_id,acc_name,amount,bank,create_datetime,status,first_auditor,first_datetime,second_auditor,second_datetime,case when back_order_status = 1 then '補單' else '' end as back_order_status ,recharge_type, '0' as order_type  \n");
				sb.append("from ctt_manager.ctt_recharge_order  where status IN (" + rechargeStatus + ") \n");
				sb.append("UNION ALL\n");
				sb.append(
						"select order_id,acc_name,amount,bank,create_datetime,status ,first_auditor,first_datetime, 0 as second_auditor, '' as second_datetime , '' as recharge_type ,'' as back_order_status , '1' as order_type \n");
				sb.append("from ctt_manager.ctt_withdrawal_order where status IN (" + withdrawalStatus + ") ) A1 ) B\n");
				sb.append("left join \n");
				sb.append("(select acc_id , acc_name from ctt_manager.ctt_manager_acc ) C\n");
				sb.append("on  C.acc_id = B.first_auditor \n");
				sb.append("left join \n");
				sb.append("(select acc_id , acc_name from ctt_manager.ctt_manager_acc ) D\n");
				sb.append("on  D.acc_id = B.second_auditor \n");
				sb.append(" )\n");
				sb.append("ORDER BY " + orderBy[sort] + " " + ASC[aes] + " \n");
				sb.append(" LIMIT ?,? \n");
				params.add(firstCount);
				params.add(count);

				result = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				LOG.debug(result.toString());
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}

			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getOrderRechargeData(String status, int firstCount, int count) {
		LOG.debug("getOrderRechargeData");
		List<Map<String, Object>> result = null;
		StringBuilder sb = null;
		List<Object> params = null;
		try {
			result = new ArrayList<Map<String, Object>>();
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {

				sb.append("select B.order_id as orderId , B.acc_name as accName ,B.amount , B.bank , B.create_datetime as createDatetime,\n");
				sb.append(
						"B.status , B.note , B.first_auditor as firstAuditor , B.first_datetime as firstDatetime , B.second_auditor as secondAuditor , \n");
				sb.append(
						"B.second_datetime as secondDatetime ,case when B.back_order_status = 1 then '補單' else '' end as backOrderStatus , B.recharge_type as rechargeType , C.acc_name as firstAccName ,D.acc_name as secondAccName , '0' as orderType\n");
				sb.append("from (\n");
				sb.append("(select order_id,first_auditor,second_auditor from ctt_manager.ctt_recharge_order where status IN(" + status + ")\n");
				if (status == rechargeCarryOutStatus) {
					sb.append(
							" and create_datetime BETWEEN DATE_FORMAT(concat(DATE_FORMAT(date_sub(CURRENT_TIMESTAMP,interval 30 day),'%Y/%m/%d'),' 00:00:00'),'%Y/%m/%d %H:%i:%s') and DATE_FORMAT(concat(DATE_FORMAT(CURRENT_TIMESTAMP,'%Y/%m/%d'),' 23:59:59'),'%Y/%m/%d %H:%i:%s')");
				}
				sb.append(") A \n");
				sb.append(" inner join \n");
				sb.append(" (select * from ctt_manager.ctt_recharge_order ) B\n");
				sb.append(" on A.order_id = B.order_id\n");
				sb.append(" left join \n");
				sb.append(" (select acc_id , acc_name from ctt_manager.ctt_manager_acc ) C\n");
				sb.append(" on  C.acc_id = A.first_auditor\n");
				sb.append("  left join \n");
				sb.append("  (select acc_id , acc_name from ctt_manager.ctt_manager_acc ) D\n");
				sb.append(" on  D.acc_id = A.second_auditor \n");
				sb.append(")\n");
				sb.append("ORDER BY " + orderBy[0] + " " + ASC[1] + " \n");
				sb.append(" LIMIT ?,? \n");
				params.add(firstCount);
				params.add(count);

				result = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				LOG.debug(result.toString());
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}

			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getOrderWithdrawalData(String status, int firstCount, int count) {
		LOG.debug("getOrderWithdrawalData");
		List<Map<String, Object>> result = null;
		StringBuilder sb = null;
		List<Object> params = null;
		try {
			result = new ArrayList<Map<String, Object>>();
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {
				sb.append(
						"select B.order_id as orderId , B.acc_name as accName ,B.amount , B.bank , B.create_datetime as createDatetime,B.bank_acc_name as bankAccName , B.bank_acc as bankAcc , \n");
				sb.append(
						"B.status , B.first_auditor as firstAuditor , B.first_datetime as firstDatetime , case when amount >= 1000 then '大額'  when amount < 1000 then '小額' else '' end as type  ,  C.acc_name as firstAccName , '1' as orderType\n");
				sb.append("from ( \n");
				sb.append("(select order_id,first_auditor from ctt_manager.ctt_withdrawal_order where status IN (" + status + ")");
				if (status == withdrawalCarryOutStatus) {
					sb.append(
							" and create_datetime BETWEEN DATE_FORMAT(concat(DATE_FORMAT(date_sub(CURRENT_TIMESTAMP,interval 30 day),'%Y/%m/%d'),' 00:00:00'),'%Y/%m/%d %H:%i:%s') and DATE_FORMAT(concat(DATE_FORMAT(CURRENT_TIMESTAMP,'%Y/%m/%d'),' 23:59:59'),'%Y/%m/%d %H:%i:%s')");
				}
				sb.append(") A \n");
				sb.append(" inner join \n");
				sb.append(" (select * from ctt_manager.ctt_withdrawal_order ) B\n");
				sb.append(" on A.order_id = B.order_id\n");
				sb.append(" left join \n");
				sb.append(" (select acc_id , acc_name from ctt_manager.ctt_manager_acc ) C\n");
				sb.append(" on  C.acc_id = A.first_auditor\n");
				sb.append(")\n");
				sb.append("ORDER BY " + orderBy[0] + " " + ASC[1] + " \n");
				sb.append(" LIMIT ?,? \n");
				params.add(firstCount);
				params.add(count);

				result = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				LOG.debug(result.toString());
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}

			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
		}
		return result;
	}

	@Override
	public boolean autitAuth(long USERID, long orderId, int orderType) {
		LOG.debug("autitAuth");
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try {
			sb = new StringBuilder();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				if (orderType == ORDER_RECHARGE) {
					sb.append("select count(order_id) AS count from `ctt_recharge_order` \n");
					sb.append("where order_id = ? AND \n");
					sb.append("((first_auditor = 0 AND first_datetime IS NULL AND status = 1) or \n");
					sb.append("(first_auditor = ? AND first_datetime IS NULL AND status = 2) or \n");
					sb.append(
							"(first_auditor <> ? AND first_datetime IS NOT NULL AND second_auditor = 0 AND second_datetime IS NULL AND status = 3)or \n");
					sb.append(
							"(first_auditor <> ? AND first_datetime IS NOT NULL AND second_auditor = ? AND second_datetime IS NULL AND status = 4)or \n");
					sb.append("status = 5)");
					ps = this.READ_CONN.prepareStatement(sb.toString());
					ps.setLong(1, orderId);
					ps.setLong(2, USERID);
					ps.setLong(3, USERID);
					ps.setLong(4, USERID);
					ps.setLong(5, USERID);
				} else {
					sb.append("select count(order_id) AS count from `ctt_withdrawal_order` \n");
					sb.append("where order_id = ? AND \n");
					sb.append("((first_auditor = 0 AND first_datetime IS NULL AND status = 1) or \n");
					sb.append("(first_auditor = ? AND first_datetime IS NULL AND status = 2) or \n");
					sb.append("status = 5)");
					ps = this.READ_CONN.prepareStatement(sb.toString());
					ps.setLong(1, orderId);
					ps.setLong(2, USERID);
				}
				rs = ps.executeQuery();
				if (rs.next()) {
					count = rs.getInt("count");
				}
				LOG.info("count===" + count);
				if (count > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.info("insertRemark_Exception===" + e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
		}
		return false;
	}

	@Override
	public boolean updateRechargeOrderStatus(long USERID, long orderId) {
		List<Object> params = null;
		StringBuilder sb = null;
		int count = 0;
		try {
			params = new ArrayList<Object>();
			sb = new StringBuilder();
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb.append("UPDATE ctt_manager.ctt_recharge_order SET \n");
				sb.append("status = (CASE \n");
				sb.append("WHEN first_auditor = 0 THEN ? \n");
				params.add(ORDER_STATUS_2_1ST_AUDITING);
				sb.append("WHEN (first_datetime IS NOT NULL AND second_auditor = 0) THEN ? \n");
				params.add(ORDER_STATUS_4_2ND_AUDITING);
				sb.append("WHEN (second_datetime IS NOT NULL AND last_auditor = 0) THEN ? ELSE status END) \n");
				params.add(ORDER_STATUS_6_LAST_AUDIT);
				sb.append(", first_auditor = (CASE WHEN first_auditor = 0 THEN ? ELSE first_auditor END) \n");
				params.add(USERID);
				sb.append(", second_auditor = (CASE WHEN (first_datetime IS NOT NULL AND second_auditor = 0) THEN ? ELSE second_auditor END) \n");
				params.add(USERID);
				sb.append(", last_auditor = (CASE WHEN (second_datetime IS NOT NULL AND last_auditor = 0) THEN ? ELSE last_auditor END) \n");
				params.add(USERID);
				sb.append("WHERE order_id = ? AND status IN (?,?,?) AND ( \n");
				params.add(orderId);
				params.add(ORDER_STATUS_1_WAIT_1ST_AUDIT);
				params.add(ORDER_STATUS_3_WAIT_2ND_AUDIT);
				params.add(ORDER_STATUS_5_WAIT_LAST_AUDIT);
				sb.append("(first_auditor = 0) OR \n");
				sb.append("(first_auditor<>? AND first_datetime IS NOT NULL AND second_auditor = 0) OR \n");
				params.add(USERID);
				sb.append("(second_datetime IS NOT NULL AND last_auditor = 0) ) \n");

				LOG.info("updateRechargeOrderStatus_SQL===" + sb.toString());
				LOG.info("updateRechargeOrderStatus_params===" + params);
				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);
				LOG.info("updateRechargeOrderStatus_count===" + count);
				if (count == 1) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			LOG.info("updateRechargeOrderStatus_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				rollBackDB();
			} catch (SQLException e1) {
				LOG.info("rollBackDB_Exception===" + e.getMessage());
				ShowLog.err(LOG, e);
			}
			return false;
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
			if (params != null) {
				params.clear();
				params = null;
			}
			count = 0;
		}
	}

	@Override
	public boolean updateWithdrawalOrderStatus(long USERID, long orderId) {
		List<Object> params = null;
		StringBuilder sb = null;
		int count = 0;
		try {
			params = new ArrayList<Object>();
			sb = new StringBuilder();
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb.append("UPDATE ctt_manager.ctt_withdrawal_order SET \n");
				sb.append("status = (CASE \n");
				sb.append("WHEN (first_auditor = 0 AND first_datetime IS NULL) THEN ? \n");
				params.add(ORDER_STATUS_2_1ST_AUDITING);
				sb.append("WHEN (first_datetime IS NOT NULL AND last_auditor = 0) THEN ? ELSE status END), \n");
				params.add(ORDER_STATUS_6_LAST_AUDIT);
				sb.append("first_auditor = (CASE WHEN first_auditor = 0 THEN ? ELSE first_auditor END), \n");
				params.add(USERID);
				sb.append("last_auditor = (CASE WHEN (first_datetime IS NOT NULL AND last_auditor = 0) THEN ? ELSE last_auditor END) \n");
				params.add(USERID);
				sb.append("WHERE order_id = ? AND status IN (?,?) AND ( \n");
				params.add(orderId);
				params.add(ORDER_STATUS_1_WAIT_1ST_AUDIT);
				params.add(ORDER_STATUS_5_WAIT_LAST_AUDIT);
				sb.append("(first_auditor = 0) OR \n");
				sb.append("(first_datetime IS NOT NULL AND last_auditor = 0)) \n");

				LOG.info("updateWithdrawalOrderStatus_SQL===" + sb.toString());
				LOG.info("updateWithdrawalOrderStatus_params===" + params);
				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);
				LOG.info("updateWithdrawalOrderStatus_count===" + count);
				if (count == 1) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			LOG.info("updateWithdrawalOrderStatus_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				rollBackDB();
			} catch (SQLException e1) {
				LOG.info("rollBackDB_Exception===" + e.getMessage());
				ShowLog.err(LOG, e);
			}
			return false;
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
			if (params != null) {
				params.clear();
				params = null;
			}
			count = 0;
		}
	}

	@Override
	public boolean updateRechargeAuditOrder(long USERID, long orderId, boolean allow) {
		List<Object> params = null;
		StringBuilder sb = null;
		int count = 0;
		try {
			params = new ArrayList<Object>();
			sb = new StringBuilder();
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb.append("UPDATE ctt_manager.ctt_recharge_order SET \n");
				sb.append("status = (CASE \n");
				sb.append(
						"WHEN ? = FALSE THEN (CASE WHEN (last_auditor > 0 AND last_datetime IS NULL) THEN ? ELSE ? END) ELSE (CASE WHEN (first_auditor > 0 AND first_datetime IS NULL) THEN ? \n");
				params.add(allow);
				params.add(ORDER_STATUS_9_ERROR);
				params.add(ORDER_STATUS_8_REFUSE);
				params.add(ORDER_STATUS_3_WAIT_2ND_AUDIT);
				sb.append("WHEN (second_auditor > 0 AND second_datetime IS NULL) THEN ? \n");
				params.add(ORDER_STATUS_5_WAIT_LAST_AUDIT);
				sb.append("WHEN (last_auditor > 0 AND last_datetime IS NULL) THEN ? ELSE status END) END), \n");
				params.add(ORDER_STATUS_7_COMPLETE);
				sb.append(
						"first_datetime = (CASE WHEN (first_auditor > 0 AND first_datetime IS NULL) THEN CURRENT_TIMESTAMP ELSE first_datetime END), \n");
				sb.append(
						"second_datetime = (CASE WHEN (second_auditor > 0 AND second_datetime IS NULL) THEN CURRENT_TIMESTAMP ELSE second_datetime END), \n");
				sb.append(
						"last_datetime = (CASE WHEN (last_auditor > 0 AND last_datetime IS NULL) THEN CURRENT_TIMESTAMP ELSE last_datetime END) \n");
				sb.append("WHERE order_id = ? AND status IN (?,?,?) AND ( \n");
				params.add(orderId);
				params.add(ORDER_STATUS_2_1ST_AUDITING);
				params.add(ORDER_STATUS_4_2ND_AUDITING);
				params.add(ORDER_STATUS_6_LAST_AUDIT);
				sb.append("(first_auditor = ? AND first_datetime IS NULL) OR \n");
				params.add(USERID);
				sb.append("(first_auditor <> ? AND first_datetime IS NOT NULL AND second_auditor = ? AND second_datetime IS NULL) OR \n");
				params.add(USERID);
				params.add(USERID);
				sb.append("(second_datetime IS NOT NULL AND last_auditor = ? AND last_datetime IS NULL)) \n");
				params.add(USERID);

				LOG.info("updateRechargeAuditOrder_SQL===" + sb.toString());
				LOG.info("updateRechargeAuditOrder_params===" + params);
				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);
				LOG.info("updateRechargeAuditOrder_count===" + count);
				if (count == 1) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			LOG.info("updateRechargeAuditOrder_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				rollBackDB();
			} catch (SQLException e1) {
				LOG.info("rollBackDB_Exception===" + e.getMessage());
				ShowLog.err(LOG, e);
			}
			return false;
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
			if (params != null) {
				params.clear();
				params = null;
			}
			count = 0;
		}
	}

	@Override
	public boolean updateWithdrawalAuditOrder(long USERID, long orderId, boolean allow, String bankSid, BigDecimal bankCheckAmount,
			String bankDepositTime) {
		List<Object> params = null;
		StringBuilder sb = null;
		int count = 0;
		try {
			params = new ArrayList<Object>();
			sb = new StringBuilder();
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb.append("UPDATE ctt_manager.ctt_withdrawal_order SET status = ( \n");
				sb.append("        CASE WHEN ? =FALSE THEN ( \n");
				params.add(allow);
				sb.append("            CASE WHEN (last_auditor > 0 AND last_datetime IS NULL) THEN ? \n");
				params.add(ORDER_STATUS_9_ERROR);
				sb.append("                ELSE ? END ) \n");
				params.add(ORDER_STATUS_8_REFUSE);
				sb.append("        ELSE (CASE WHEN (first_auditor > 0 AND first_datetime IS NULL) THEN ? \n");
				params.add(ORDER_STATUS_5_WAIT_LAST_AUDIT);
				sb.append("            WHEN (last_auditor > 0 AND last_datetime IS NULL) THEN ? \n");
				params.add(ORDER_STATUS_7_COMPLETE);
				sb.append("            ELSE status END) \n");
				sb.append("        END) \n");
				sb.append(",bank_sid =(CASE WHEN(? AND first_auditor > 0 AND first_datetime IS NULL) THEN ? ELSE bank_sid END) \n");
				params.add(allow);
				params.add(bankSid);
				sb.append(
						",bank_check_amount = (CASE WHEN(? AND first_auditor > 0 AND first_datetime IS NULL) THEN ? ELSE bank_check_amount END) \n");
				params.add(allow);
				params.add(bankCheckAmount);
				sb.append(
						",bank_deposit_datetime = (CASE WHEN(? AND first_auditor > 0 AND first_datetime IS NULL) THEN ? ELSE bank_deposit_datetime END) \n");
				params.add(allow);
				params.add(bankDepositTime);
				sb.append("    , first_datetime = ( \n");
				sb.append("        CASE WHEN (first_auditor > 0 AND first_datetime IS NULL) THEN CURRENT_TIMESTAMP ELSE first_datetime END) \n");
				sb.append("    , last_datetime = ( \n");
				sb.append("        CASE WHEN (last_auditor > 0 AND last_datetime IS NULL) THEN CURRENT_TIMESTAMP ELSE last_datetime END) \n");
				sb.append("    WHERE order_id = ? AND status IN (?,?) AND ( \n");
				params.add(orderId);
				params.add(ORDER_STATUS_2_1ST_AUDITING);
				params.add(ORDER_STATUS_6_LAST_AUDIT);
				sb.append("        (first_auditor = ? AND first_datetime IS NULL) \n");
				params.add(USERID);
				sb.append("            OR (first_datetime IS NOT NULL AND last_auditor =? AND last_datetime IS NULL)) \n");
				params.add(USERID);

				LOG.info("updateWithdrawalAuditOrder_SQL===" + sb.toString());
				LOG.info("updateWithdrawalAuditOrder_params===" + params);
				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);
				LOG.info("updateWithdrawalAuditOrder_count===" + count);
				if (count == 1) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			LOG.info("updateWithdrawalAuditOrder_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				rollBackDB();
			} catch (SQLException e1) {
				LOG.info("rollBackDB_Exception===" + e.getMessage());
				ShowLog.err(LOG, e);
			}
			return false;
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
			if (params != null) {
				params.clear();
				params = null;
			}
			count = 0;
		}
	}

	@Override
	public Map<String, Object> checkOrderReview(long orderId) {
		List<Map<String, Object>> listMap = null;
		Map<String, Object> result = null;
		StringBuilder sb = null;
		List<Object> params = null;
		try {
			listMap = new ArrayList<Map<String, Object>>();
			result = new ConcurrentHashMap<String, Object>();
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {

				sb.append("\n");
				sb.append(
						"select B.acc_id as acc_id , B.order_id as orderId , B.acc_name as accName ,B.amount , B.bank , B.status , B.orderType , B.bank_acc_name as bankAccName  ,B.bank_acc as bankAcc ,B.bank_sid as bankSid  , B.bank_deposit_datetime as bankDepositDatetime , B.bank_check_amount as bankChekAmount\n");
				sb.append(",C.remark , case when B.back_order_status = 1 then '補單' else '' end as backOrderStatus\n");
				sb.append(" from (\n");
				sb.append("(select * from(\n");
				sb.append(
						"select acc_id , order_id , acc_name , amount , bank , status , bank_acc_name  ,bank_acc , bank_sid , bank_deposit_datetime , bank_check_amount , '0' as orderType , back_order_status  \n");
				sb.append("from ctt_manager.ctt_recharge_order  where (status = 5 OR status = 6) and order_id = ?  \n");
				params.add(orderId);
				sb.append("UNION ALL\n");
				sb.append(
						"select acc_id , order_id , acc_name , amount , bank , status , bank_acc_name  ,bank_acc , bank_sid , bank_deposit_datetime , bank_check_amount , '1' as orderType , 0 as back_order_status  \n");
				sb.append("from ctt_manager.ctt_withdrawal_order where (status = 5 OR status = 6) and order_id = ?  ) A1 ) B \n");
				params.add(orderId);
				sb.append(" left join\n");
				sb.append(
						"(select order_id ,GROUP_CONCAT(remark SEPARATOR '<br><br>') AS remark from ctt_manager.ctt_order_remark where order_id = ? GROUP BY order_id)C\n");
				params.add(orderId);
				sb.append("on B.order_id = C.order_id\n");
				sb.append(" ) \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);

				for (int i = 0; i < listMap.size(); i++) {

					for (Object key : listMap.get(i).keySet()) {
						result.put(key.toString(), listMap.get(i).get(key).toString());
					}
				}

				LOG.debug(result.toString());

			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (listMap != null) {
				listMap = new ArrayList<Map<String, Object>>();
				listMap = null;
			}
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
		}
		return result;

	}

	@Override
	public Map<String, Object> checkCarryOutOrder(long orderId, int orderType) {
		List<Map<String, Object>> listMap = null;
		Map<String, Object> result = null;
		StringBuilder sb = null;
		List<Object> params = null;
		try {
			listMap = new ArrayList<Map<String, Object>>();
			result = new ConcurrentHashMap<String, Object>();
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {
				sb.append("select A.order_id,A.acc_name,A.amount,A.status,A.bank_acc_name,A.bank_acc,A.bank \n");
				if (orderType == ORDER_RECHARGE) {
					sb.append(",A.note \n");
				}
				sb.append(",A.bank_sid,A.bank_check_amount,A.bank_deposit_datetime , '1' as orderType , B.remark \n");
				sb.append("from \n");
				sb.append("((select order_id,acc_name,status,amount,bank_acc_name,bank_acc,bank \n");
				if (orderType == ORDER_RECHARGE) {
					sb.append(",note \n");
				}
				sb.append(",bank_sid \n");
				if (orderType == ORDER_RECHARGE) {
					sb.append(",bank_check_amount,bank_deposit_datetime from  ctt_manager.ctt_recharge_order where order_id = ?) A \n");
				} else if (orderType == ORDER_WITHDRAWAL) {
					sb.append(
							",(CASE WHEN bank_check_amount = 0 THEN '' ELSE bank_check_amount END)AS bank_check_amount,CAST((CASE WHEN bank_deposit_datetime='0000-00-00 00:00:00' THEN '' ELSE bank_deposit_datetime END) AS CHAR) AS bank_deposit_datetime from  ctt_manager.ctt_withdrawal_order where order_id = ?) A \n");
				}
				params.add(orderId);
				sb.append("left join \n");
				sb.append(
						"(SELECT order_id,GROUP_CONCAT(remark SEPARATOR '<br><br>') AS remark FROM ctt_manager.ctt_order_remark WHERE order_id = ? GROUP BY order_id)B \n");
				params.add(orderId);
				sb.append("on A.order_id = B.order_id) \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);

				for (int i = 0; i < listMap.size(); i++) {

					for (Object key : listMap.get(i).keySet()) {
						result.put(key.toString(), listMap.get(i).get(key).toString());
					}
				}
				LOG.debug(result.toString());
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (listMap != null) {
				listMap = new ArrayList<Map<String, Object>>();
				listMap = null;
			}
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> getRechargeOrderData(long USERID, long orderId) {
		List<Map<String, Object>> listMap = null;
		Map<String, Object> result = null;
		StringBuilder sb = null;
		List<Object> params = null;
		try {
			listMap = new ArrayList<Map<String, Object>>();
			result = new ConcurrentHashMap<String, Object>();
			sb = new StringBuilder();
			params = new ArrayList<Object>();

			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {
				sb.append("select * from \n");
				sb.append(
						"(select acc_id, order_id,acc_name,amount,status,bank_acc_name,bank_acc,bank,note,bank_sid,bank_check_amount,bank_deposit_datetime , '0' as orderType \n");
				sb.append(
						",CASE WHEN (first_auditor=0 AND first_datetime IS NULL) THEN 1 WHEN (first_auditor=? AND first_datetime IS NULL) THEN 1 \n");
				params.add(USERID);
				sb.append("WHEN (first_auditor<>? AND first_datetime IS NOT NULL AND second_auditor=? AND second_datetime IS NULL) THEN 1 \n");
				params.add(USERID);
				params.add(USERID);
				sb.append("WHEN (first_datetime IS NOT NULL AND second_datetime IS NOT NULL AND last_auditor=? AND last_datetime IS NULL) THEN 1 \n");
				params.add(USERID);
				sb.append("ELSE 0 END AS enable \n");
				sb.append("from \n");
				sb.append("ctt_manager.ctt_recharge_order \n");
				sb.append("where \n");
				sb.append("order_id = ?) A \n");
				params.add(orderId);
				sb.append("left outer join \n");
				sb.append("(select order_id,status,remark from ctt_manager.ctt_order_remark where status = 1) B \n");
				sb.append("on  A.order_id = B.order_id \n");

				LOG.debug("getRechargeOrderData_SQL===" + sb.toString());
				LOG.debug("getRechargeOrderData_params===" + params.toString());
				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				if (listMap == null) {
					LOG.debug("getRechargeOrderData_listMap is null");
					result = null;
				} else {
					if (listMap.size() == 1) {
						result = listMap.get(0);
					} else {
						result = new HashMap<String, Object>();
					}
					LOG.debug("getRechargeOrderData_listMap_size===" + listMap.size());
				}
			}
		} catch (Exception e) {
			LOG.info("getRechargeOrderData_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				rollBackDB();
			} catch (SQLException e1) {
				LOG.info("rollBackDB_Exception===" + e.getMessage());
				ShowLog.err(LOG, e);
			}
			result = null;
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (params != null) {
				params.clear();
				params = null;
			}
			if (listMap != null) {
				listMap.clear();
				listMap = null;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> getWithdrawalOrderData(long USERID, long orderId) {
		List<Map<String, Object>> listMap = null;
		Map<String, Object> result = null;
		StringBuilder sb = null;
		List<Object> params = null;
		try {
			listMap = new ArrayList<Map<String, Object>>();
			result = new ConcurrentHashMap<String, Object>();
			sb = new StringBuilder();
			params = new ArrayList<Object>();

			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {
				sb.append(
						"SELECT order_id,acc_id,acc_name,bank_acc_name,bank,bank_acc,bank_sid,bank_check_amount,bank_deposit_datetime,status,amount, '1' as orderType  \n");
				sb.append(",first_auditor,currency,create_datetime,first_datetime,last_auditor,last_datetime  \n");
				sb.append(
						",CASE WHEN (first_auditor=0 AND first_datetime IS NULL) THEN 1 WHEN (first_auditor=? AND first_datetime IS NULL) THEN 1  \n");
				params.add(USERID);
				sb.append("WHEN (first_auditor<>? AND first_datetime IS NOT NULL) THEN 1  \n");
				params.add(USERID);
				sb.append("WHEN (first_datetime IS NOT NULL AND last_auditor=? AND last_datetime IS NULL) THEN 1  \n");
				params.add(USERID);
				sb.append("ELSE 0 END AS enable FROM ctt_manager.ctt_withdrawal_order WHERE order_id = ? \n");
				params.add(orderId);

				LOG.debug("getRechargeOrderData_SQL===" + sb.toString());
				LOG.debug("getRechargeOrderData_params===" + params.toString());
				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				if (listMap == null) {
					LOG.debug("getWithdrawalOrderData_listMap is null");
					result = null;
				} else {
					if (listMap.size() == 1) {
						result = listMap.get(0);
					} else {
						result = new HashMap<String, Object>();
					}
					LOG.debug("getWithdrawalOrderData_listMap_size===" + listMap.size());
				}
			}
		} catch (Exception e) {
			LOG.info("getWithdrawalOrderData_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				rollBackDB();
			} catch (SQLException e1) {
				LOG.info("rollBackDB_Exception===" + e.getMessage());
				ShowLog.err(LOG, e);
			}
			result = null;
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (params != null) {
				params.clear();
				params = null;
			}
			if (listMap != null) {
				listMap.clear();
				listMap = null;
			}
		}
		return result;
	}

	@Override
	public boolean checkMemberAccName(String accName) {
		LOG.debug("checkMemberAccName");
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb.append("SELECT acc_name FROM ctt_manager.ctt_member_acc WHERE acc_name = ?");
				ps = this.READ_CONN.prepareStatement(sb.toString());
				ps.setString(1, accName);
				rs = ps.executeQuery();
				if (rs.next()) {
					rs.getString("acc_name");
				}

				LOG.info("checkMemberAccName===" + rs.getString("acc_name"));
				if (rs.getString("acc_name").equals(accName)) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.info("checkMemberAccName_Exception===" + e);
			return false;
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
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
		return false;
	}

	@Override
	public int getOrderRechargeCountTotal(int status, String accName, String auditorAccName, String firstDate, String lastDate, String orderStatus) {
		int result = 0;
		StringBuilder sb = null;
		List<Object> params = null;
		String firstDatetimeStr = "";
		String lastDatetimeStr = "";
		List<Map<String, Object>> listMap = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.info("no connection");
				return result;
			} else {

				sb = new StringBuilder();
				params = new ArrayList<Object>();
				listMap = new ArrayList<Map<String, Object>>();

				if (!"".equals(firstDate) && !"".equals(lastDate)) {
					firstDatetimeStr = firstDate + " 00:00:00";
					lastDatetimeStr = lastDate + " 23:59:59";
				} else {
					firstDatetimeStr = "";
					lastDatetimeStr = "";
				}

				sb.append(
						"select count(order_id) as count from ctt_manager.ctt_recharge_order where case when ? <> '' and ? is not null then first_auditor =(select acc_id from ctt_manager.ctt_manager_acc where acc_name = ?) or second_auditor = (select acc_id  from ctt_manager.ctt_manager_acc where acc_name = ?)\n");
				params.add(auditorAccName);
				params.add(auditorAccName);
				params.add(auditorAccName);
				params.add(auditorAccName);
				sb.append(
						" else true end and case when ? <> '' and ? is not null then acc_name = ? else true end and case when ? <> '' and  ? is not null and ? <> '' and ? is not null then create_datetime BETWEEN ? and ? else true end and \n");
				params.add(accName);
				params.add(accName);
				params.add(accName);

				params.add(firstDatetimeStr);
				params.add(firstDatetimeStr);
				params.add(lastDatetimeStr);
				params.add(lastDatetimeStr);

				params.add(firstDatetimeStr);
				params.add(lastDatetimeStr);
				sb.append("case ? when 0 then status IN (" + orderStatus
						+ ") when 1 then status = 1 when 2 then status = 2 when 3 then status = 3 when 4 then status = 4 when 5 then status = 5 when 6 then status = 6 when 7 then status = 7 when 8 then status = 8 when 9 then status = 9 else false end\n");
				params.add(status);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				for (int i = 0; i < listMap.size(); i++) {
					for (Object key : listMap.get(i).keySet()) {
						LOG.debug(key.toString());
						result = Integer.parseInt(listMap.get(i).get(key.toString()).toString());
					}
				}
				LOG.debug(result);
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (params != null) {
				params.clear();
				params = null;
			}
			if (listMap != null) {
				listMap.clear();
				listMap = null;
			}
			firstDatetimeStr = null;
			lastDatetimeStr = null;
		}
		return result;
	}

	@Override
	public int getOrderWithdrawalCountTotal(int status, String accName, String auditorAccName, String firstDate, String lastDate,
			String orderStatus) {
		int result = 0;
		StringBuilder sb = null;
		List<Object> params = null;
		String firstDatetimeStr = "";
		String lastDatetimeStr = "";
		List<Map<String, Object>> listMap = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.info("no connection");
				return result;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();
				listMap = new ArrayList<Map<String, Object>>();

				if (!"".equals(firstDate) && !"".equals(lastDate)) {
					firstDatetimeStr = firstDate + " 00:00:00";
					lastDatetimeStr = lastDate + " 23:59:59";
				} else {
					firstDatetimeStr = "";
					lastDatetimeStr = "";
				}

				sb.append(
						"select count(order_id) as count from ctt_manager.ctt_withdrawal_order where case when ? <> '' and ? is not null then first_auditor =(select acc_id from ctt_manager.ctt_manager_acc where acc_name = ?) \n");
				params.add(auditorAccName);
				params.add(auditorAccName);
				params.add(auditorAccName);
				sb.append(
						" else true end and case when ? <> '' and ? is not null then acc_name = ? else true end and case when ? <> '' and  ? is not null and ? <> '' and ? is not null then create_datetime BETWEEN ? and ? else true end and \n");
				params.add(accName);
				params.add(accName);
				params.add(accName);

				params.add(firstDatetimeStr);
				params.add(firstDatetimeStr);
				params.add(lastDatetimeStr);
				params.add(lastDatetimeStr);

				params.add(firstDatetimeStr);
				params.add(lastDatetimeStr);
				sb.append("case ? when 0 then status IN (" + orderStatus
						+ ") when 1 then status = 1 when 2 then status = 2 when 3 then status = 3 when 4 then status = 4 when 5 then status = 5 when 6 then status = 6 when 7 then status = 7 when 8 then status = 8 when 9 then status = 9 else false end\n");
				params.add(status);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				for (int i = 0; i < listMap.size(); i++) {
					for (Object key : listMap.get(i).keySet()) {
						LOG.debug(key.toString());
						result = Integer.parseInt(listMap.get(i).get(key.toString()).toString());
					}
				}
				LOG.debug(result);
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (params != null) {
				params.clear();
				params = null;
			}
			if (listMap != null) {
				listMap.clear();
				listMap = null;
			}
			firstDatetimeStr = null;
			lastDatetimeStr = null;
		}
		return result;
	}

	@Override
	public int getOrderReviewCountTotal(String rechargeStatus, String withdrawalStatus) {
		int result = 0;
		StringBuilder sb = null;
		List<Object> params = null;
		List<Map<String, Object>> listMap = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.info("no connection");
				return result;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();
				listMap = new ArrayList<Map<String, Object>>();
				sb.append("select count(order_id) as count\n");
				sb.append(" from (\n");
				sb.append("(select * from(\n");
				sb.append("select order_id   \n");
				sb.append("from ctt_manager.ctt_recharge_order  where status IN (" + rechargeStatus + ") \n");
				sb.append("UNION ALL\n");
				sb.append("select order_id  \n");
				sb.append("from ctt_manager.ctt_withdrawal_order where status IN (" + withdrawalStatus + ") ) A1 ) B\n");
				sb.append(" )\n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				for (int i = 0; i < listMap.size(); i++) {
					for (Object key : listMap.get(i).keySet()) {
						LOG.debug(key.toString());
						result = Integer.parseInt(listMap.get(i).get(key.toString()).toString());
					}
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
			if (params != null) {
				params.clear();
				params = null;
			}
			if (listMap != null) {
				listMap.clear();
				listMap = null;
			}
		}
		return result;
	}

	@Override
	public int checkMemType(String accName) {
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
				sb.append("SELECT member_type FROM ctt_manager.ctt_member_acc WHERE acc_name = ? \n");
				ps = this.READ_CONN.prepareStatement(sb.toString());
				ps.setString(1, accName);
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
	public OrderBean getOrderInfo(long orderId, int orderType) {
		StringBuilder sb = null;
		List<Object> params = null;
		String tableName = null;
		OrderBean bean = null;
		List<Object> list = null;
		try {
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			bean = new OrderBean();
			list = new ArrayList<Object>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				if (orderType == ORDER_RECHARGE) {
					tableName = "recharge";
				} else if (orderType == ORDER_WITHDRAWAL) {
					tableName = "withdrawal";
				}
				sb.append("SELECT * \n");
				sb.append("FROM \n");
				sb.append("(SELECT * FROM ctt_manager.ctt_" + tableName + "_order WHERE order_id = ?) A \n");
				params.add(orderId);
				sb.append("LEFT JOIN \n");
				sb.append("(SELECT * FROM ctt_manager.ctt_order_remark) B \n");
				sb.append("ON A.order_id = B.order_id AND A.status = B.status \n");
				sb.append(" \n");

				list = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), params, bean);
				if (list.size() == 1) {
					bean = (OrderBean) list.get(0);
				}
			}
		} catch (Exception e) {
			bean = null;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (list != null) {
				list.clear();
				list = null;
			}
			if (params != null) {
				params.clear();
				params = null;
			}
			tableName = null;
		}
		return bean;
	}
}
