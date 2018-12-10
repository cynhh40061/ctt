package tw.com.ctt.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.ILotteryMemberRecordDao;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class LotteryMemberRecordDaoImpl extends BaseDao implements ILotteryMemberRecordDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5189146190043456303L;
	private static final Logger LOG = LogManager.getLogger(LotteryMemberRecordDaoImpl.class.getName());

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
	public List<Map<String, Object>> searchMemDayRecord(long accId, String accName, int localId, String startTime, String endTime, String tableName) {
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
				sb.append(
						"	report.acc_id,acc_name,game_id,total_bet_amount,total_winning_bonus,total_no_of_bet,total_fan_den,date,total_withdrawal \n");
				sb.append("FROM \n");
				sb.append("	(SELECT \n");
				sb.append("		acc_id,game_id,total_bet_amount,total_winning_bonus,total_no_of_bet,total_fan_den,date,total_withdrawal \n");
				sb.append("	FROM \n");
				sb.append("		`ctt_manager`.`" + tableName + "` \n");
				sb.append(
						"	WHERE DATE_FORMAT(date, '%Y/%m/%d %T') >= DATE_FORMAT(?, '%Y/%m/%d %T') AND DATE_FORMAT(date, '%Y/%m/%d %T') <= DATE_FORMAT(?, '%Y/%m/%d %T') \n");
				param.add(startTime);
				param.add(endTime);
				if (localId > 0) {
					sb.append("		AND game_id = ? \n");
					param.add(localId);
				}
				sb.append("	) report \n");
				sb.append("INNER JOIN \n");
				sb.append("(SELECT acc_id,acc_name FROM `ctt_manager`.`ctt_member_acc` \n");
				sb.append("	WHERE acc_id IN (\n");
				sb.append("		SELECT acc_id FROM (SELECT acc_id FROM ctt_manager.ctt_member_acc_level \n");
				sb.append("			WHERE(CASE(SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ? ) \n");
				param.add(accId);
				sb.append("			WHEN 0 THEN TRUE \n");
				sb.append("			WHEN 1 THEN TRUE \n");
				sb.append("			WHEN 2 THEN sc \n");
				sb.append("			WHEN 3 THEN bc \n");
				sb.append("			WHEN 4 THEN co \n");
				sb.append("			WHEN 5 THEN sa \n");
				sb.append("			WHEN 6 THEN ag \n");
				sb.append("			WHEN 7 THEN ag1 \n");
				sb.append("			WHEN 8 THEN ag2 \n");
				sb.append("			WHEN 9 THEN ag3 \n");
				sb.append("			WHEN 10 THEN ag4 \n");
				sb.append("			WHEN 11 THEN ag5 \n");
				sb.append("			WHEN 12 THEN ag6 \n");
				sb.append("			WHEN 13 THEN ag7 \n");
				sb.append("			WHEN 14 THEN ag8 \n");
				sb.append("			WHEN 15 THEN ag9 \n");
				sb.append("			WHEN 16 THEN ag10 \n");
				sb.append("			END) = \n");
				sb.append("			(CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				param.add(accId);
				sb.append("				WHEN 0 THEN TRUE \n");
				sb.append("				WHEN 1 THEN TRUE \n");
				sb.append("				ELSE ? \n");
				param.add(accId);
				sb.append("				END) \n");
				sb.append("			)chkMember \n");
				sb.append("			)\n");
				if (!"".equals(accName) && !accName.isEmpty()) {
					sb.append("		AND acc_name = ?\n");
					param.add(accName);
				}
				sb.append("	) manager \n");
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
	public List<Map<String, Object>> searchMemberRecordByDate(long accId, int localId, String startTime, String endTime, String tableName, int type) {
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
				sb.append("	SUM(total_bet_amount) AS total_bet_amount, \n");
				sb.append("	SUM(total_winning_bonus) AS total_winning_bonus, \n");
				sb.append("	SUM(total_no_of_bet) AS total_no_of_bet, \n");
				sb.append("	SUM(total_fan_den) AS total_fan_den, \n");
				sb.append("	DATE_FORMAT(date, '%Y/%m/%d') AS date  \n");
				sb.append("	FROM \n");
				sb.append("	(SELECT \n");
				sb.append("		acc_id,game_id,total_bet_amount,total_winning_bonus,total_no_of_bet,total_fan_den,date \n");
				sb.append("	FROM \n");
				sb.append("	`ctt_manager`.`" + tableName + "` \n");
				sb.append(
						"	WHERE DATE_FORMAT(date, '%Y/%m/%d %T') >= DATE_FORMAT(?, '%Y/%m/%d %T') AND DATE_FORMAT(date, '%Y/%m/%d %T') <= DATE_FORMAT(?, '%Y/%m/%d %T')\n");
				param.add(startTime);
				param.add(endTime);
				if (localId > 0) {
					sb.append("	AND game_id = ?\n");
					param.add(localId);
				}
				sb.append("	)report \n");
				sb.append("LEFT JOIN \n");
				sb.append("(SELECT id AS local_id,type AS type_id FROM `ctt_manager`.`ctt_lottery_type`)local \n");
				sb.append("ON report.game_id = local.local_id \n");
				sb.append("LEFT JOIN \n");
				sb.append("(SELECT acc_id,acc_level_type FROM `ctt_manager`.`ctt_manager_acc_level`)level \n");
				sb.append("ON report.acc_id = level.acc_id \n");
				sb.append("WHERE acc_level_type = 2 \n");
				sb.append("GROUP BY date ORDER BY date DESC \n");
				if (type == 1) {
					sb.append("LIMIT 0,7 \n");
				}

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
