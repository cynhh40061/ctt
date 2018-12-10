package tw.com.ctt.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.ILotteryRecordDao;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class LotteryRecordDaoImpl extends BaseDao implements ILotteryRecordDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7747915879657996280L;
	private static final Logger LOG = LogManager.getLogger(LotteryRecordDaoImpl.class.getName());

	@Override
	public List<Map<String, Object>> getLotteryLocalList() {
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				sb = new StringBuilder();
				sb.append("SELECT id,title FROM `ctt_manager`.`ctt_lottery_type` \n");
				sb.append("ORDER BY id ASC \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), new ArrayList<>());
				LOG.debug(listMap.toString());
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return listMap;
	}

	@Override
	public int getLotteryRecordTotleCount(long accId, String memberAccName, int recordType, boolean checkBox, int localId, String startTime,
			String endTime) {
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		List<Object> param = null;
		int totleCount = 0;

		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return totleCount;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				sb = new StringBuilder();
				param = new ArrayList<Object>();

				sb.append("SELECT \n");
				sb.append("COUNT(1) AS totleCount \n");
				sb.append("FROM \n");
				sb.append("	`ctt_manager`.`ctt_lottery_member_money_transfer_record` \n");
				sb.append("WHERE \n");
				sb.append("	acc_id IN (SELECT acc_id FROM (SELECT acc_id FROM `ctt_manager`.`ctt_member_acc_level` \n");
				sb.append("		WHERE(CASE(SELECT acc_level_type FROM `ctt_manager`.`ctt_manager_acc_level` WHERE acc_id = ? ) \n");
				param.add(accId);
				sb.append("				WHEN 0 THEN TRUE \n");
				sb.append("				WHEN 1 THEN TRUE \n");
				sb.append("				WHEN 2 THEN sc \n");
				sb.append("				WHEN 3 THEN bc \n");
				sb.append("				WHEN 4 THEN co \n");
				sb.append("				WHEN 5 THEN sa \n");
				sb.append("				WHEN 6 THEN ag \n");
				sb.append("				WHEN 7 THEN ag1 \n");
				sb.append("				WHEN 8 THEN ag2 \n");
				sb.append("				WHEN 9 THEN ag3 \n");
				sb.append("				WHEN 10 THEN ag4 \n");
				sb.append("				WHEN 11 THEN ag5 \n");
				sb.append("				WHEN 12 THEN ag6 \n");
				sb.append("				WHEN 13 THEN ag7 \n");
				sb.append("				WHEN 14 THEN ag8 \n");
				sb.append("				WHEN 15 THEN ag9 \n");
				sb.append("				WHEN 16 THEN ag10 \n");
				sb.append("														END) = \n");
				sb.append(
						"														(CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				param.add(accId);
				sb.append("																WHEN 0 THEN TRUE \n");
				sb.append("																WHEN 1 THEN TRUE \n");
				sb.append("																ELSE ? \n");
				param.add(accId);
				sb.append("																END) \n");
				sb.append("														)chkMember) \n");
				sb.append("AND acc_name = ? \n");
				param.add(memberAccName);
				if (recordType > 0) {
					sb.append("AND record_order_type = ? \n");
					param.add(recordType);
				}
				if (localId > 0) {
					sb.append("AND local_id = ? \n");
					param.add(localId);
				}
				sb.append(
						"AND DATE_FORMAT(create_time, '%Y/%m/%d %T') >= DATE_FORMAT(?, '%Y/%m/%d %T') AND DATE_FORMAT(create_time, '%Y/%m/%d %T') <= DATE_FORMAT(?, '%Y/%m/%d %T') \n");
				param.add(startTime);
				param.add(endTime);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), param);
				if (listMap.size() == 1) {
					totleCount = Integer.parseInt(listMap.get(0).get("totleCount").toString());
				}
				LOG.debug(listMap.toString());
			}
		} catch (Exception e) {
			totleCount = 0;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (param != null) {
				param.clear();
				param = null;
			}
		}
		return totleCount;
	}

	@Override
	public List<Map<String, Object>> getLotteryRecord(long accId, String memberAccName, int recordType, int localId, String startTime, String endTime,
			int firstCount, int count) {
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		List<Object> param = null;

		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				sb = new StringBuilder();
				param = new ArrayList<Object>();

				sb.append("SELECT \n");
				sb.append("record_order_id,acc_id,acc_name,create_time,start_period_num, \n");
				sb.append("stop_period_num,local_name,played_name,record_order_type,money,balance, \n");
				sb.append("period_total_count,CONCAT(DATE_FORMAT(period_date, '%Y%m%d'),'-',LPAD(main_order_id,4,0)) as main_order_id \n");
				sb.append("FROM \n");
				sb.append("(SELECT \n");
				sb.append("	record_order_id,acc_id,acc_name,create_time,start_period_num,stop_period_num, \n");
				sb.append("	local_id,played_id,record_order_type,money,balance,period_total_count,period_date,main_order_id \n");
				sb.append("FROM \n");
				sb.append("	`ctt_manager`.`ctt_lottery_member_money_transfer_record` \n");
				sb.append("WHERE \n");
				sb.append("	acc_id IN (SELECT acc_id FROM (SELECT acc_id FROM `ctt_manager`.`ctt_member_acc_level` \n");
				sb.append("		WHERE(CASE(SELECT acc_level_type FROM `ctt_manager`.`ctt_manager_acc_level` WHERE acc_id = ? ) \n");
				param.add(accId);
				sb.append("																WHEN 0 THEN TRUE \n");
				sb.append("																WHEN 1 THEN TRUE \n");
				sb.append("																WHEN 2 THEN sc = ? \n");
				param.add(accId);
				sb.append("																WHEN 3 THEN bc = ? \n");
				param.add(accId);
				sb.append("																WHEN 4 THEN co = ? \n");
				param.add(accId);
				sb.append("																WHEN 5 THEN sa = ? \n");
				param.add(accId);
				sb.append("																WHEN 6 THEN ag = ? \n");
				param.add(accId);
				sb.append("																WHEN 7 THEN ag1 = ? \n");
				param.add(accId);
				sb.append("																WHEN 8 THEN ag2 = ? \n");
				param.add(accId);
				sb.append("																WHEN 9 THEN ag3 = ? \n");
				param.add(accId);
				sb.append("																WHEN 10 THEN ag4 = ? \n");
				param.add(accId);
				sb.append("																WHEN 11 THEN ag5 = ? \n");
				param.add(accId);
				sb.append("																WHEN 12 THEN ag6 = ? \n");
				param.add(accId);
				sb.append("																WHEN 13 THEN ag7 = ? \n");
				param.add(accId);
				sb.append("																WHEN 14 THEN ag8 = ? \n");
				param.add(accId);
				sb.append("																WHEN 15 THEN ag9 = ? \n");
				param.add(accId);
				sb.append("																WHEN 16 THEN ag10 = ? ELSE FALSE END) \n");
				param.add(accId);
				sb.append("				)chkMember) \n");
				sb.append("AND acc_name = ? \n");
				param.add(memberAccName);
				if (recordType > 0) {
					sb.append("AND record_order_type = ? \n");
					param.add(recordType);
				}
				if (localId > 0) {
					sb.append("AND local_id = ? \n");
					param.add(localId);
				}
				sb.append(
						"AND DATE_FORMAT(create_time, '%Y/%m/%d %T') >= DATE_FORMAT(?, '%Y/%m/%d %T') AND DATE_FORMAT(create_time, '%Y/%m/%d %T') <= DATE_FORMAT(?, '%Y/%m/%d %T') \n");
				param.add(startTime);
				param.add(endTime);
				sb.append(")record \n");
				sb.append("LEFT JOIN \n");
				sb.append("(SELECT id, title AS local_name FROM `ctt_manager`.`ctt_lottery_type`) local \n");
				sb.append("ON record.local_id = local.id \n");
				sb.append("LEFT JOIN \n");
				sb.append("(SELECT auth_id, lottery_title AS played_name FROM `ctt_manager`.`ctt_lottery_auth` WHERE auth_level_type = 3) played \n");
				sb.append("ON record.played_id = played.auth_id \n");

				sb.append("ORDER BY create_time DESC,record_order_id DESC \n");

				sb.append("limit ?,? \n");
				param.add(firstCount);
				param.add(count);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), param);
				LOG.debug(listMap.toString());
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (param != null) {
				param.clear();
				param = null;
			}
		}
		return listMap;
	}

}
