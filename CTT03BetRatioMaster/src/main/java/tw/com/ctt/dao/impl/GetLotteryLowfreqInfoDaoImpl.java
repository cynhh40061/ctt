package tw.com.ctt.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IGetLotteryLowfreqInfoDao;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

import static tw.com.ctt.constant.LotteryLowfreqSetLogToDBConstant.*;

public class GetLotteryLowfreqInfoDaoImpl extends BaseDao implements IGetLotteryLowfreqInfoDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5634853422852503648L;
	private static final Logger LOG = LogManager.getLogger(GetLotteryLowfreqInfoDaoImpl.class.getName());

	@Override
	public List<Map<String, Object>> getLotteryTypeList() {
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
				sb.append("SELECT auth_id AS type_id,lottery_title AS type_name FROM `ctt_manager`.`ctt_lottery_auth` WHERE auth_id IN (6,7,618) \n");
				sb.append("ORDER BY auth_id ASC \n");

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
	public List<Map<String, Object>> getLotteryMidList() {
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
				sb.append(
						"SELECT level1_id AS type_id,level2_id AS played_id,auth_id AS mid_id,lottery_title AS mid_name FROM `ctt_manager`.`ctt_lottery_auth` WHERE auth_level_type = 3 AND level1_id IN (6,7,618) \n");
				sb.append("ORDER BY auth_id ASC \n");

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
	public List<Map<String, Object>> getLotteryAmountPlayedList() {
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
				sb.append("SELECT level1_id AS type_id,auth_id AS played_id,lottery_title AS played_name,sort_level1_id,sort_level2_id \n");
				sb.append("FROM `ctt_manager`.`ctt_lottery_auth` \n");
				sb.append("WHERE \n");
				sb.append("level1_id IN (6,7,618) \n");
				sb.append("AND auth_level_type = 2 \n");
				sb.append("ORDER BY sort_level1_id ASC , sort_level2_id ASC \n");

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
	public List<Map<String, Object>> getHandicapInfo() {
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
				sb.append("SELECT handicap_id,handicap_name,bonus_set_max,bonus_set_min,max_win_bonus FROM `ctt_manager`.`ctt_lottery_handicap` \n");
				sb.append("ORDER BY handicap_id ASC \n");

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
	public List<Map<String, Object>> getLowfreqBaseAmount(int handicap, int typeId) {
		StringBuilder sb = null;
		List<Object> params = null;
		List<Map<String, Object>> tmpMapList = null;

		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return tmpMapList;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();
				tmpMapList = new ArrayList<Map<String, Object>>();

				sb.append("SELECT \n");
				sb.append("	lottery_local_id AS local_id,lottery_type_id AS type_id,lottery_mid_id AS mid_id,lottery_min_id AS min_id, \n");
				sb.append(
						"	min_name,base_bet,baseline,played_text,lottery_rule,lottery_example,prize_level,handicap,dt_switch,dt_ratio,dt_bonus  \n");
				sb.append("FROM \n");
				sb.append("( \n");
				sb.append("	SELECT  \n");
				sb.append("		lottery_local_id,lottery_type_id,lottery_mid_id,lottery_min_id, \n");
				sb.append("		base_bet,baseline,prize_level,handicap,dt_switch,dt_ratio,dt_bonus  \n");
				sb.append("		FROM `ctt_manager`.`ctt_lottery_lowfreq_amount` WHERE handicap = ? AND lottery_type_id = ? \n");
				params.add(handicap);
				params.add(typeId);
				sb.append(") A \n");
				sb.append("LEFT JOIN \n");
				sb.append("( \n");
				sb.append("	SELECT  \n");
				sb.append("		level1_id AS type_id,level2_id AS mid_id,auth_id AS min_id,lottery_title AS min_name, \n");
				sb.append("		played_text,lottery_rule,lottery_example  \n");
				sb.append("		FROM `ctt_manager`.`ctt_lottery_auth` WHERE auth_level_type = 3 AND level1_id = ? \n");
				params.add(typeId);
				sb.append(") B \n");
				sb.append("ON A.lottery_type_id = B.type_id AND A.lottery_min_id = B.min_id  \n");
				sb.append("ORDER BY local_id ASC,type_id ASC,mid_id ASC,min_id ASC,prize_level ASC,handicap ASC; \n");

				tmpMapList = StmtUtil.queryToMap(READ_CONN, sb.toString(), params);
			}
		} catch (Exception e) {
			tmpMapList = null;
			LOG.debug("Exception, " + e.getMessage());
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
		}
		return tmpMapList;
	}

	@Override
	public List<Map<String, Object>> getLowfreqNowAmount(int handicap, int typeId) {
		StringBuilder sb = null;
		List<Object> params = null;
		List<Map<String, Object>> tmpMapList = null;

		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return tmpMapList;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();
				this.checkRead();
				tmpMapList = new ArrayList<Map<String, Object>>();

				sb.append("SELECT \n");
				sb.append("	lottery_local_id AS local_id,lottery_type_id AS type_id,lottery_mid_id AS mid_id,lottery_min_id AS min_id, \n");
				sb.append("	min_name,base_bet,baseline,prize_level,handicap, \n");
				sb.append("	total_no_of_bet,total_bet_amount,period_num,date,now_baseline \n");
				sb.append("FROM \n");
				sb.append("( \n");
				sb.append("	SELECT  \n");
				sb.append("		lottery_local_id,lottery_type_id,lottery_mid_id,lottery_min_id, \n");
				sb.append("		base_bet,baseline,prize_level,handicap, \n");
				sb.append("		total_no_of_bet,total_bet_amount,period_num,date,now_baseline \n");
				sb.append("		FROM `ctt_manager`.`ctt_lottery_lowfreq_current_ratio`  \n");
				sb.append("		WHERE  \n");
				sb.append("		handicap = ? AND lottery_type_id = ? AND \n");
				params.add(handicap);
				params.add(typeId);
				sb.append("		period_num IN ( \n");
				sb.append("						SELECT period_num FROM `ctt_manager`.`ctt_lottery`  \n");
				sb.append("							WHERE id = (SELECT id FROM `ctt_manager`.`ctt_lottery_type` WHERE type = ?) AND \n");
				params.add(typeId);
				sb.append("							DATE_FORMAT(CURRENT_TIMESTAMP, '%Y/%m/%d %T') BETWEEN  \n");
				sb.append("							DATE_FORMAT(start_betting_time, '%Y/%m/%d %T') AND  \n");
				sb.append("							DATE_FORMAT(stop_betting_time, '%Y/%m/%d %T') \n");
				sb.append("					) \n");
				sb.append(") A \n");
				sb.append("LEFT JOIN \n");
				sb.append("( \n");
				sb.append("	SELECT  \n");
				sb.append("		level1_id AS type_id,level2_id AS mid_id,auth_id AS min_id,lottery_title AS min_name  \n");
				sb.append("		FROM `ctt_manager`.`ctt_lottery_auth` WHERE auth_level_type = 3 AND level1_id = ? \n");
				params.add(typeId);
				sb.append(") B \n");
				sb.append("ON A.lottery_type_id = B.type_id AND A.lottery_min_id = B.min_id  \n");
				sb.append("ORDER BY local_id ASC,type_id ASC,mid_id ASC,min_id ASC,prize_level ASC,handicap ASC; \n");

				tmpMapList = StmtUtil.queryToMap(READ_CONN, sb.toString(), params);
				LOG.debug(tmpMapList.size());
				LOG.debug(tmpMapList);
			}
		} catch (Exception e) {
			tmpMapList = null;
			LOG.debug("Exception, " + e.getMessage());
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
		}
		return tmpMapList;
	}

	@Override
	public List<Map<String, Object>> getMark6BaseAmount(int handicap, int playedId, int midId) {
		StringBuilder sb = null;
		List<Object> params = null;
		List<Map<String, Object>> tmpMapList = null;

		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return tmpMapList;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();
				this.checkRead();
				tmpMapList = new ArrayList<Map<String, Object>>();

				sb.append("SELECT \n");
				sb.append("	lottery_local_id AS local_id,lottery_type_id AS type_id,lottery_mid_id AS mid_id,lottery_min_id AS min_id, \n");
				sb.append(
						"	min_name,base_bet,baseline,played_text,lottery_rule,lottery_example,prize_level,handicap,dt_switch,dt_ratio,dt_bonus,prize_name \n");
				sb.append("FROM \n");
				sb.append("( \n");
				sb.append("	SELECT \n");
				sb.append("		lottery_local_id,lottery_type_id,lottery_mid_id,lottery_min_id, \n");
				sb.append("		base_bet,baseline,prize_level,handicap,dt_switch,dt_ratio,dt_bonus,prize_name \n");
				sb.append("		FROM `ctt_manager`.`ctt_lottery_lowfreq_amount` \n");
				sb.append("		WHERE  \n");
				sb.append("		handicap = ? AND lottery_type_id = 618 AND lottery_mid_id = ? AND lottery_min_id = ?  \n");
				params.add(handicap);
				params.add(playedId);
				params.add(midId);
				sb.append(") A \n");
				sb.append("LEFT JOIN \n");
				sb.append("( \n");
				sb.append("	SELECT  \n");
				sb.append("		level1_id AS type_id,level2_id AS mid_id,auth_id AS min_id,lottery_title AS min_name, \n");
				sb.append("		played_text,lottery_rule,lottery_example  \n");
				sb.append("		FROM `ctt_manager`.`ctt_lottery_auth`  \n");
				sb.append("		WHERE auth_level_type = 3 AND level1_id = 618 AND level2_id = ? AND auth_id = ? \n");
				params.add(playedId);
				params.add(midId);
				sb.append(") B \n");
				sb.append("ON A.lottery_type_id = B.type_id AND A.lottery_min_id = B.min_id  \n");
				sb.append("ORDER BY local_id ASC,type_id ASC,mid_id ASC,min_id ASC,prize_level ASC,handicap ASC; \n");

				tmpMapList = StmtUtil.queryToMap(READ_CONN, sb.toString(), params);
			}
		} catch (Exception e) {
			tmpMapList = null;
			LOG.debug("Exception, " + e.getMessage());
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
		}
		return tmpMapList;
	}

	@Override
	public List<Map<String, Object>> getMark6NowAmount(int handicap, int playedId, int midId) {
		StringBuilder sb = null;
		List<Object> params = null;
		List<Map<String, Object>> tmpMapList = null;

		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return tmpMapList;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();
				this.checkRead();
				tmpMapList = new ArrayList<Map<String, Object>>();

				sb.append("SELECT \n");
				sb.append("	lottery_local_id AS local_id,lottery_type_id AS type_id,lottery_mid_id AS mid_id,lottery_min_id AS min_id, \n");
				sb.append("	min_name,base_bet,baseline,prize_level,handicap,prize_name, \n");
				sb.append("	total_no_of_bet,total_bet_amount,period_num,date,now_baseline \n");
				sb.append("FROM \n");
				sb.append("( \n");
				sb.append("	SELECT  \n");
				sb.append("		lottery_local_id,lottery_type_id,lottery_mid_id,lottery_min_id, \n");
				sb.append("		base_bet,baseline,prize_level,handicap,prize_name, \n");
				sb.append("		total_no_of_bet,total_bet_amount,period_num,date,now_baseline \n");
				sb.append("		FROM `ctt_manager`.`ctt_lottery_lowfreq_current_ratio` \n");
				sb.append("		WHERE  \n");
				sb.append("		handicap = ? AND lottery_type_id = 618 AND lottery_mid_id = ? AND lottery_min_id = ? AND \n");
				params.add(handicap);
				params.add(playedId);
				params.add(midId);
				sb.append("		period_num IN ( \n");
				sb.append("						SELECT period_num FROM `ctt_manager`.`ctt_lottery`  \n");
				sb.append("							WHERE id = 26 AND \n");
				sb.append("							DATE_FORMAT(CURRENT_TIMESTAMP, '%Y/%m/%d %T') BETWEEN  \n");
				sb.append("							DATE_FORMAT(start_betting_time, '%Y/%m/%d %T') AND  \n");
				sb.append("							DATE_FORMAT(stop_betting_time, '%Y/%m/%d %T') \n");
				sb.append("					) \n");
				sb.append(") A \n");
				sb.append("LEFT JOIN \n");
				sb.append("( \n");
				sb.append("	SELECT  \n");
				sb.append("		level1_id AS type_id,level2_id AS mid_id,auth_id AS min_id,lottery_title AS min_name \n");
				sb.append("		FROM `ctt_manager`.`ctt_lottery_auth`  \n");
				sb.append("		 \n");
				sb.append("		WHERE auth_level_type = 3 AND level1_id = 618 AND level2_id = ? AND auth_id = ? \n");
				params.add(playedId);
				params.add(midId);
				sb.append(") B \n");
				sb.append("ON A.lottery_type_id = B.type_id AND A.lottery_min_id = B.min_id  \n");
				sb.append("ORDER BY local_id ASC,type_id ASC,mid_id ASC,min_id ASC,prize_level ASC,handicap ASC; \n");

				tmpMapList = StmtUtil.queryToMap(READ_CONN, sb.toString(), params);
			}
		} catch (Exception e) {
			tmpMapList = null;
			LOG.debug("Exception, " + e.getMessage());
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
		}
		return tmpMapList;
	}

	@Override
	public List<Map<String, Object>> getLowfreqAmountSettingLog(long opsAccId) {
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		List<Object> params = null;
		try {
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				sb.append("SELECT \n");
				sb.append("ops_acc_id ,ops_acc_name ,action ,action_text ,detail ,ops_datetime ,CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) AS ip \n");
				sb.append("FROM \n");
				sb.append("	(SELECT \n");
				sb.append("	ops_acc_id ,ops_acc_name ,action ,detail ,ops_datetime ,ip1 ,ip2 ,ip3 ,ip4 \n");
				sb.append("	FROM \n");
				sb.append("		( \n");
				sb.append(
						"		SELECT ops_acc_id ,ops_acc_name ,action ,detail ,ops_datetime ,ip1 ,ip2 ,ip3 ,ip4 FROM `ctt_manager`.`ctt_lottery_set_ops_log` \n");
				sb.append("		WHERE \n");
				sb.append("		ops_acc_id IN \n");
				sb.append("			(SELECT acc_id FROM ctt_manager.ctt_manager_acc_level \n");
				sb.append("				WHERE(CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				params.add(opsAccId);
				sb.append("				WHEN 0 THEN TRUE \n");
				sb.append("				WHEN 1 THEN TRUE \n");
				sb.append("				WHEN 2 THEN sc = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 3 THEN bc = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 4 THEN co = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 5 THEN sa = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 6 THEN ag = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 7 THEN ag1 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 8 THEN ag2 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 9 THEN ag3 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 10 THEN ag4 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 11 THEN ag5 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 12 THEN ag6 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 13 THEN ag7 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 14 THEN ag8 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 15 THEN ag9 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 16 THEN ag10 = ? ELSE FALSE END)) \n");
				params.add(opsAccId);
				sb.append("		AND action IN (SELECT action_id FROM `ctt_manager`.`ctt_lottery_log_action` WHERE upper_id = ?) \n");
				params.add(LOG_ACTION_LOTTERY_LOWFREQ_SET);
				sb.append("		) A \n");
				sb.append("	)detail \n");
				sb.append("LEFT JOIN \n");
				sb.append("(SELECT action_id ,action_text ,upper_id FROM `ctt_manager`.`ctt_lottery_log_action`) act \n");
				sb.append("ON detail.action = act.action_id \n");
				sb.append("ORDER BY ops_datetime DESC \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				if (listMap == null) {
					LOG.debug("showOpsLog_listMap is null");
				} else {
					LOG.debug("showOpsLog_listMap_size===" + listMap.size());
				}
			}
		} catch (Exception e) {
			listMap = null;
			LOG.debug("showOpsLog_Exception===" + e.getMessage());
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
		}
		return listMap;
	}
}
