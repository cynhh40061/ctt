package tw.com.ctt.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.ILotteryOperatorRecordDao;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class LotteryOperatorRecordDaoImpl extends BaseDao implements ILotteryOperatorRecordDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5189146190043456303L;
	private static final Logger LOG = LogManager.getLogger(LotteryOperatorRecordDaoImpl.class.getName());

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
				sb.append("SELECT \n");
				sb.append("local_id,local_name,type_id,type_name \n");
				sb.append("FROM \n");
				sb.append("(SELECT id AS local_id,type,title AS local_name FROM `ctt_manager`.`ctt_lottery_type`)local \n");
				sb.append("LEFT JOIN \n");
				sb.append(
						"(SELECT auth_id AS type_id,lottery_title AS type_name FROM `ctt_manager`.`ctt_lottery_auth` WHERE auth_level_type = 1)type \n");
				sb.append("ON local.type = type.type_id \n");
				sb.append("ORDER BY local_id ASC,type_id ASC \n");

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
	public List<Map<String, Object>> getTodayOperatorRecord(long accId) {
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
				sb.append("local_id,type_id,total_bet_amount,total_winning_bonus,total_no_of_bet,total_fan_den \n");
				sb.append("FROM \n");
				sb.append("(SELECT id AS local_id,type AS type_id FROM `ctt_manager`.`ctt_lottery_type`)local \n");
				sb.append("LEFT JOIN \n");
				sb.append("(SELECT \n");
				sb.append("	acc_id,game_id,total_bet_amount,total_winning_bonus,total_no_of_bet,total_fan_den,date \n");
				sb.append("	FROM \n");
				sb.append("	`ctt_manager`.`manager_lottery_statistical_report` \n");
				sb.append("	WHERE \n");
				sb.append("	acc_id = ? \n");
				param.add(accId);
				sb.append("	AND DATE_FORMAT(date, '%Y/%m/%d') = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y/%m/%d'))report \n");
				sb.append("ON report.game_id = local.local_id \n");
				sb.append("ORDER BY local_id ASC \n");

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
		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> getTodayTotalOperatorRecord() {
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

				sb.append("SELECT \n");
				sb.append("	local_id, \n");
				sb.append("	type_id, \n");
				sb.append("	SUM(total_bet_amount) AS total_bet_amount, \n");
				sb.append("	SUM(total_winning_bonus) AS total_winning_bonus, \n");
				sb.append("	SUM(total_no_of_bet) AS total_no_of_bet, \n");
				sb.append("	SUM(total_fan_den) AS total_fan_den \n");
				sb.append("	FROM \n");
				sb.append("	(SELECT id AS local_id,type AS type_id FROM `ctt_manager`.`ctt_lottery_type`)local \n");
				sb.append("LEFT JOIN \n");
				sb.append("(SELECT \n");
				sb.append("	report.acc_id,game_id,total_bet_amount,total_winning_bonus,total_no_of_bet,total_fan_den,date \n");
				sb.append("	FROM \n");
				sb.append("	( \n");
				sb.append("	SELECT acc_id,game_id,total_bet_amount,total_winning_bonus,total_no_of_bet,total_fan_den,date \n");
				sb.append("	FROM `ctt_manager`.`manager_lottery_statistical_report` \n");
				sb.append("	WHERE DATE_FORMAT(date, '%Y/%m/%d') = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y/%m/%d') \n");
				sb.append("	)report \n");
				sb.append("	INNER JOIN \n");
				sb.append("	(SELECT acc_id,acc_level_type FROM `ctt_manager`.`ctt_manager_acc_level` WHERE acc_level_type = 2 )level \n");
				sb.append("	ON report.acc_id = level.acc_id \n");
				sb.append(")detail \n");
				sb.append("ON detail.game_id = local.local_id \n");
				sb.append("GROUP BY local_id \n");
				sb.append("ORDER BY local_id ASC \n");

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
	public List<Map<String, Object>> searchOperatorRecordByDate(long accId, String accName, int localId, String dateTime, String tableName) {
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
				sb.append("report.acc_id,acc_name,game_id,total_bet_amount,total_winning_bonus,total_no_of_bet,total_fan_den,date \n");
				sb.append("FROM \n");
				sb.append("(SELECT \n");
				sb.append("acc_id,game_id,total_bet_amount,total_winning_bonus,total_no_of_bet,total_fan_den,date \n");
				sb.append("FROM \n");
				sb.append("`ctt_manager`.`" + tableName + "` \n");
				sb.append("WHERE \n");
				sb.append("DATE_FORMAT(date, '%Y/%m/%d') = DATE_FORMAT(?, '%Y/%m/%d') \n");
				param.add(dateTime);
				if (localId > 0) {
					sb.append("AND game_id = ? \n");
					param.add(localId);
				}
				sb.append(") report \n");
				sb.append("INNER JOIN \n");
				sb.append("(SELECT acc_id,acc_name FROM `ctt_manager`.`ctt_manager_acc` \n");
				if (!"".equals(accName) && !accName.isEmpty()) {
					sb.append("WHERE acc_name = ? \n");
					param.add(accName);
				}
				sb.append(") manager \n");
				sb.append("ON report.acc_id = manager.acc_id \n");
				sb.append("INNER JOIN \n");
				sb.append("(SELECT acc_id,acc_level_type FROM `ctt_manager`.`ctt_manager_acc_level` WHERE acc_level_type = 2) level \n");
				sb.append("ON report.acc_id = level.acc_id \n");
				sb.append("ORDER BY date DESC, game_id ASC, report.acc_id ASC \n");

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
		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> searchBCRecord(long accId, int localId, String dateTime, String tableName) {
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
				sb.append("report.acc_id,acc_name,game_id,total_bet_amount,total_winning_bonus,total_no_of_bet,total_fan_den,date \n");
				sb.append("FROM \n");
				sb.append("(SELECT \n");
				sb.append("acc_id,game_id,total_bet_amount,total_winning_bonus,total_no_of_bet,total_fan_den,date \n");
				sb.append("FROM \n");
				sb.append("`ctt_manager`.`" + tableName + "` \n");
				sb.append("WHERE \n");
				sb.append("DATE_FORMAT(date, '%Y/%m/%d') = DATE_FORMAT(?, '%Y/%m/%d') \n");
				param.add(dateTime);
				if (localId > 0) {
					sb.append("AND game_id = ? \n");
					param.add(localId);
				}
				sb.append(") report \n");
				sb.append("INNER JOIN \n");
				sb.append("(SELECT acc_id,acc_name FROM `ctt_manager`.`ctt_manager_acc` \n");
				sb.append("WHERE acc_id IN (SELECT acc_id FROM ctt_manager.ctt_manager_acc_level WHERE sc = ? AND acc_level_type = 3) \n");
				param.add(accId);
				sb.append(") manager \n");
				sb.append("ON report.acc_id = manager.acc_id \n");
				sb.append("ORDER BY date DESC, game_id ASC, report.acc_id ASC \n");

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
		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> searchMemRecord(long accId, int localId, String dateTime, String tableName) {
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
				sb.append("report.acc_id,acc_name,game_id,total_bet_amount,total_winning_bonus,total_no_of_bet,total_fan_den,date \n");
				sb.append("FROM \n");
				sb.append("(SELECT \n");
				sb.append("acc_id,game_id,total_bet_amount,total_winning_bonus,total_no_of_bet,total_fan_den,date \n");
				sb.append("FROM \n");
				sb.append("`ctt_manager`.`member_lottery_statistical_report` \n");
				sb.append("WHERE \n");
				sb.append("DATE_FORMAT(date, '%Y/%m/%d') = DATE_FORMAT(?, '%Y/%m/%d') \n");
				param.add(dateTime);
				if (localId > 0) {
					sb.append("AND game_id = ? \n");
					param.add(localId);
				}
				sb.append(") report \n");
				sb.append("INNER JOIN \n");
				sb.append("(SELECT acc_id,acc_name FROM `ctt_manager`.`ctt_member_acc` \n");
				sb.append("WHERE acc_id IN (SELECT acc_id FROM ctt_manager.ctt_member_acc_level WHERE bc = ? AND upper_acc_level_type = 3) \n");
				param.add(accId);
				sb.append(") manager \n");
				sb.append("ON report.acc_id = manager.acc_id \n");
				sb.append("ORDER BY date DESC, game_id ASC, report.acc_id ASC \n");

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
		}
		return listMap;
	}
}
