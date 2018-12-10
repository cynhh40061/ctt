package tw.com.ctt.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IQueryAndCanclebetOrderDao;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class QueryAndCanclebetOrderDaoImpl extends BaseDao implements IQueryAndCanclebetOrderDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5393598519122647798L;
	private static final Logger LOG = LogManager.getLogger(QueryAndCanclebetOrderDaoImpl.class.getName());

	@Override
	public Map<String, Object> searchMidByUnionTable(long accId, List<String> tableDateList) {
		HashMap<String, Object> mapAll = null;
		HashMap<String, Object> mapL1 = null;
		HashMap<String, String> mapL2 = null;

		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String level1_name = null;
		List<Object> param = null;
		String firstTableDate = null;

		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return null;
			} else {
				sb = new StringBuilder();
				param = new ArrayList<Object>();
				firstTableDate = tableDateList.get(tableDateList.size() - 1);

				sb.append("SELECT \n");
				sb.append(
						"	A.main_order_id, D.id AS local_id,D.title, A.start_period_num, B.period_num, E.lottery_title, A.bet_data, B.no_of_bet_times, \n");
				sb.append(
						"	B.no_of_bet, A.money_unit, B.amount as mid_amount, A.no_of_period, A.action_time, B.bonus as mid_bonus, B.order_status as mid_order_status, \n");
				sb.append(
						"	B.fan_den as mid_fan_den, A.no_of_kj_period, A.no_of_winning_period, A.acc_id, A.game_id, A.played_id ,A.amount as main_amount, \n");
				sb.append(
						"	A.bonus as main_bonus,A.fan_den as main_fan_den, A.order_type as main_order_type, A.order_status as main_order_status, LPAD(B.mid_order_id ,10,0) AS mid_order_id, \n");
				sb.append(
						"	B.no_of_winning_bet, A.bonus_set_ratio, A.bet_ratio, order_time, F.data AS kj_data, B.max_bonus, F.kj_time, F.complete_time \n");
				sb.append(
						"	,(CASE WHEN DATE_FORMAT(F.stop_betting_time, '%Y/%m/%d %T') > DATE_FORMAT(CURRENT_TIMESTAMP, '%Y/%m/%d %T') THEN TRUE ELSE FALSE END) AS check_bet_time \n");
				sb.append("	,UNIX_TIMESTAMP(F.stop_betting_time) AS stop_betting_time,E.auth_id \n");
				sb.append("	,handi_cap \n");
				sb.append(
						"	,(CASE WHEN DATE_FORMAT(F.stop_betting_time, '%Y/%m/%d %T') < DATE_FORMAT(CURRENT_TIMESTAMP, '%Y/%m/%d %T') AND (F.complete_time = '' OR F.complete_time IS NULL) AND B.order_status <> 3 THEN 5 ELSE B.order_status END) AS check_mid_com_time \n");
				sb.append("	FROM( \n");
				for (int mid = 0; mid < tableDateList.size(); mid++) {
					sb.append("		SELECT CONCAT(" + tableDateList.get(mid)
							+ ",'-',LPAD(main_order_id,10,0)) as main_order_id,mid_order_id, game_id,period_num, no_of_bet, amount, bonus, order_status, fan_den, no_of_winning_bet, max_bonus, no_of_bet_times \n");
					sb.append("			FROM ctt_manager.mid_order_" + tableDateList.get(mid) + "\n");
					sb.append("			WHERE acc_id = ? \n");
					param.add(accId);
					sb.append("			AND mid_order_id = 0 \n");

					if (mid + 1 < tableDateList.size()) {
						sb.append("UNION  ALL ");
					}
				}
				sb.append(")B \n");
				sb.append("INNER JOIN \n");
				sb.append("( \n");
				for (int main = 0; main < tableDateList.size(); main++) {
					sb.append("SELECT CONCAT(" + tableDateList.get(main)
							+ ",'-',LPAD(main_order_id,10,0)) as main_order_id, order_time, start_period_num, bet_data, no_of_period, no_of_kj_period, no_of_winning_period, acc_id, game_id, played_id, \n");
					sb.append(
							"				action_time, amount, money_unit, bonus, fan_den, order_type, order_status, bonus_set_ratio, bet_ratio,handi_cap \n");
					sb.append("			FROM ctt_manager.main_order_" + tableDateList.get(main) + "\n");
					sb.append("			WHERE acc_id = ? \n");
					param.add(accId);
					sb.append("			AND no_of_period = 1 \n");
					if (main + 1 < tableDateList.size()) {
						sb.append("UNION  ALL ");
					}
				}
				sb.append(")A \n");
				sb.append("on A.main_order_id = B.main_order_id \n");
				sb.append("INNER JOIN \n");
				sb.append("	(select id, title from `ctt_manager`.`ctt_lottery_type`)D \n");
				sb.append("on D.id = A.game_id \n");
				sb.append("LEFT JOIN \n");
				sb.append("	(select auth_id, lottery_title from `ctt_manager`.`ctt_lottery_auth` WHERE auth_level_type = 3)E  \n");
				sb.append("on E.auth_id = A.played_id \n");
				sb.append("LEFT JOIN \n");
				sb.append("	(select \n");
				sb.append(
						"		id,period_num, (CASE WHEN DATE_FORMAT(CURRENT_TIMESTAMP,'%Y%m%d %T') > DATE_FORMAT(kj_time,'%Y%m%d %T') THEN data ELSE '' END) AS data, kj_time, complete_time, stop_betting_time \n");
				sb.append("		from `ctt_manager`.`ctt_lottery` \n");
				sb.append("		WHERE DATE_FORMAT(date,'%Y%m%d') >= DATE_FORMAT(" + firstTableDate
						+ ",'%Y%m%d') AND DATE_FORMAT(date,'%Y%m%d') <= DATE_FORMAT(" + tableDateList.get(0) + ",'%Y%m%d'))F \n");
				sb.append("on B.period_num = F.period_num AND A.game_id = F.id \n");
				sb.append("order by A.main_order_id DESC ,B.period_num ASC \n");

				LOG.debug(sb.toString());

				ps = this.READ_CONN.prepareStatement(sb.toString());

				if (param != null && !param.isEmpty() && param.size() > 0) {
					for (int i = 0; i < param.size(); i++) {
						if (param.get(i) == null) {
							ps.setString(i + 1, null);
						} else if (param.get(i).getClass() == String.class) {
							ps.setString(i + 1, param.get(i).toString());
						} else if (param.get(i).getClass() == Integer.class) {
							ps.setInt(i + 1, Integer.parseInt(param.get(i).toString()));
						} else {
							ps.setString(i + 1, param.get(i).toString());
						}
					}
				}

				rs = ps.executeQuery();

				mapAll = new HashMap<String, Object>();
				mapL1 = new HashMap<String, Object>();
				mapL2 = new HashMap<String, String>();

				level1_name = null;
				int L1Index = 0;

				while (rs.next()) {
					if (level1_name == null) {
						level1_name = rs.getString("main_order_id");
						mapL1.put("title", rs.getString("title"));
						mapL1.put("local_id", rs.getString("local_id"));
						mapL1.put("start_period_num", rs.getString("start_period_num"));
						mapL1.put("bet_data", rs.getString("bet_data"));
						mapL1.put("no_of_winning_period", rs.getString("no_of_winning_period"));
						mapL1.put("money_unit", rs.getString("money_unit"));
						mapL1.put("no_of_period", rs.getString("no_of_period"));
						mapL1.put("no_of_kj_period", rs.getString("no_of_kj_period"));
						mapL1.put("action_time", rs.getString("action_time"));
						mapL1.put("main_amount", rs.getString("main_amount"));
						mapL1.put("main_bonus", rs.getString("main_bonus"));
						mapL1.put("main_fan_den", rs.getString("main_fan_den"));
						mapL1.put("main_order_type", rs.getString("main_order_type"));
						mapL1.put("main_order_status", rs.getString("main_order_status"));
						mapL1.put("lottery_title", rs.getString("lottery_title"));
						mapL1.put("bonus_set_ratio", rs.getString("bonus_set_ratio"));
						mapL1.put("bet_ratio", rs.getString("bet_ratio"));
						mapL1.put("order_time", rs.getString("order_time"));
						mapL1.put("member_acc_id", rs.getString("acc_id"));
						mapL1.put("auth_id", rs.getString("auth_id"));
						mapL1.put("handi_cap", rs.getString("handi_cap"));
					}

					if (!level1_name.equals(rs.getString("main_order_id"))) {
						mapAll.put(getNumByLen(L1Index, 10) + "-" + level1_name, mapL1.clone());
						level1_name = rs.getString("main_order_id");
						mapL1.clear();
						// mapL1 = null;
						// mapL1 = new HashMap<String, Object>();
						// mapL2 = new HashMap<String, String>();
						mapL1.put("title", rs.getString("title"));
						mapL1.put("local_id", rs.getString("local_id"));
						mapL1.put("start_period_num", rs.getString("start_period_num"));
						mapL1.put("bet_data", rs.getString("bet_data"));
						mapL1.put("no_of_winning_period", rs.getString("no_of_winning_period"));
						mapL1.put("money_unit", rs.getString("money_unit"));
						mapL1.put("no_of_period", rs.getString("no_of_period"));
						mapL1.put("no_of_kj_period", rs.getString("no_of_kj_period"));
						mapL1.put("action_time", rs.getString("action_time"));
						mapL1.put("main_amount", rs.getString("main_amount"));
						mapL1.put("main_bonus", rs.getString("main_bonus"));
						mapL1.put("main_fan_den", rs.getString("main_fan_den"));
						mapL1.put("main_order_type", rs.getString("main_order_type"));
						mapL1.put("main_order_status", rs.getString("main_order_status"));
						mapL1.put("lottery_title", rs.getString("lottery_title"));
						mapL1.put("bonus_set_ratio", rs.getString("bonus_set_ratio"));
						mapL1.put("bet_ratio", rs.getString("bet_ratio"));
						mapL1.put("order_time", rs.getString("order_time"));
						mapL1.put("member_acc_id", rs.getString("acc_id"));
						mapL1.put("auth_id", rs.getString("auth_id"));
						mapL1.put("handi_cap", rs.getString("handi_cap"));
						L1Index++;
					}
					mapL2.clear();
					// mapL2 = null;
					// mapL2 = new HashMap<String, String>();
					mapL2.put("period_num", rs.getString("period_num"));
					mapL2.put("no_of_bet", rs.getString("no_of_bet"));
					mapL2.put("mid_amount", rs.getString("mid_amount"));
					mapL2.put("mid_bonus", rs.getString("mid_bonus"));
					mapL2.put("mid_order_status", rs.getString("mid_order_status"));
					mapL2.put("mid_fan_den", rs.getString("mid_fan_den"));
					mapL2.put("mid_bonus", rs.getString("mid_bonus"));
					mapL2.put("mid_order_id", rs.getString("mid_order_id"));
					mapL2.put("no_of_winning_bet", rs.getString("no_of_winning_bet"));
					mapL2.put("kj_data", rs.getString("kj_data"));
					mapL2.put("max_bonus", rs.getString("max_bonus"));
					mapL2.put("no_of_bet_times", rs.getString("no_of_bet_times"));
					mapL2.put("check_bet_time", rs.getString("check_bet_time"));
					mapL2.put("stop_betting_time", rs.getString("stop_betting_time"));
					mapL2.put("kj_time", rs.getString("kj_time"));
					mapL2.put("complete_time", rs.getString("complete_time"));
					mapL2.put("check_mid_com_time", rs.getString("check_mid_com_time"));
					mapL1.put(rs.getString("mid_order_id"), mapL2.clone());
				}
				if (level1_name != null) {
					mapAll.put(getNumByLen(L1Index, 10) + "-" + level1_name, mapL1.clone());
					L1Index++;
				}
				return mapAll;
			}
		} catch (Exception e) {
			LOG.error("test()_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			mapAll = null;
			return null;
		} finally {
			sb.setLength(0);
			sb = null;
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
				}
			}
			rs = null;
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
				}
			}
			ps = null;
			mapL1 = null;
			mapL2 = null;
			level1_name = "";
			level1_name = null;
		}
	}

	@Override
	public Map<String, Object> searchMainByUnionTable(long accId, List<String> tableDateList) {
		HashMap<String, Object> mapAll = null;
		HashMap<String, Object> mapL1 = null;
		HashMap<String, String> mapL2 = null;

		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String level1_name = null;
		List<Object> param = null;
		String firstTableDate = null;

		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return null;
			} else {
				sb = new StringBuilder();
				param = new ArrayList<Object>();
				firstTableDate = tableDateList.get(tableDateList.size() - 1);

				sb.append("SELECT \n");
				sb.append(
						"	A.main_order_id, D.id AS local_id, D.title, A.start_period_num, B.period_num, E.lottery_title, A.bet_data, B.no_of_bet_times, \n");
				sb.append(
						"	B.no_of_bet, A.money_unit, B.amount as mid_amount, A.no_of_period, A.action_time, B.bonus as mid_bonus, B.order_status as mid_order_status, \n");
				sb.append(
						"	B.fan_den as mid_fan_den, A.no_of_kj_period, A.no_of_winning_period, A.acc_id, A.game_id, A.played_id ,A.amount as main_amount, \n");
				sb.append(
						"	A.bonus as main_bonus,A.fan_den as main_fan_den, A.order_type as main_order_type, A.order_status as main_order_status, LPAD(B.mid_order_id ,10,0) AS mid_order_id, \n");
				sb.append(
						"	B.no_of_winning_bet, A.bonus_set_ratio, A.bet_ratio, order_time, F.data AS kj_data, B.max_bonus, F.kj_time, F.complete_time \n");
				sb.append(
						"	,(CASE WHEN DATE_FORMAT(F.stop_betting_time, '%Y/%m/%d %T') > DATE_FORMAT(CURRENT_TIMESTAMP, '%Y/%m/%d %T') THEN TRUE ELSE FALSE END) AS check_bet_time \n");
				sb.append("	,UNIX_TIMESTAMP(F.stop_betting_time) AS stop_betting_time,E.auth_id \n");
				sb.append("	,handi_cap \n");
				sb.append(
						"	,(CASE WHEN DATE_FORMAT(F.stop_betting_time, '%Y/%m/%d %T') < DATE_FORMAT(CURRENT_TIMESTAMP, '%Y/%m/%d %T') AND (F.complete_time = '' OR F.complete_time IS NULL) AND B.order_status <> 3 THEN 5 ELSE B.order_status END) AS check_mid_com_time \n");
				sb.append("	FROM( \n");
				for (int mid = 0; mid < tableDateList.size(); mid++) {
					sb.append("		SELECT CONCAT(" + tableDateList.get(mid)
							+ ",'-',LPAD(main_order_id,10,0)) as main_order_id,mid_order_id, game_id,period_num, no_of_bet, amount, bonus, order_status, fan_den, no_of_winning_bet, max_bonus, no_of_bet_times \n");
					sb.append("			FROM ctt_manager.mid_order_" + tableDateList.get(mid) + "\n");
					sb.append("			WHERE acc_id = ? \n");
					param.add(accId);
					if (mid + 1 < tableDateList.size()) {
						sb.append("UNION  ALL ");
					}
				}
				sb.append(")B \n");
				sb.append("INNER JOIN \n");
				sb.append("( \n");
				for (int main = 0; main < tableDateList.size(); main++) {
					sb.append("SELECT CONCAT(" + tableDateList.get(main)
							+ ",'-',LPAD(main_order_id,10,0)) as main_order_id, order_time, start_period_num, bet_data, no_of_period, no_of_kj_period, no_of_winning_period, acc_id, game_id, played_id, \n");
					sb.append(
							"				action_time, amount, money_unit, bonus, fan_den, order_type, order_status, bonus_set_ratio, bet_ratio,handi_cap \n");
					sb.append("			FROM ctt_manager.main_order_" + tableDateList.get(main) + "\n");
					sb.append("			WHERE acc_id = ? \n");
					param.add(accId);
					sb.append("			AND no_of_period > 1 \n");
					if (main + 1 < tableDateList.size()) {
						sb.append("UNION  ALL ");
					}
				}
				sb.append(")A \n");
				sb.append("on A.main_order_id = B.main_order_id \n");
				sb.append("INNER JOIN \n");
				sb.append("	(select id, title from `ctt_manager`.`ctt_lottery_type`)D \n");
				sb.append("on D.id = A.game_id \n");
				sb.append("LEFT JOIN \n");
				sb.append("	(select auth_id, lottery_title from `ctt_manager`.`ctt_lottery_auth` WHERE auth_level_type = 3)E  \n");
				sb.append("on E.auth_id = A.played_id \n");
				sb.append("LEFT JOIN \n");
				sb.append("	(select \n");
				sb.append(
						"		id,period_num, (CASE WHEN DATE_FORMAT(CURRENT_TIMESTAMP,'%Y%m%d %T') > DATE_FORMAT(kj_time,'%Y%m%d %T') THEN data ELSE '' END) AS data, kj_time, complete_time, stop_betting_time \n");
				sb.append("		from `ctt_manager`.`ctt_lottery`");
				sb.append("		WHERE DATE_FORMAT(date,'%Y%m%d') >= DATE_FORMAT(" + firstTableDate
						+ ",'%Y%m%d') AND DATE_FORMAT(date,'%Y%m%d') <= DATE_FORMAT(" + tableDateList.get(0) + ",'%Y%m%d'))F \n");
				sb.append("on B.period_num = F.period_num AND A.game_id = F.id \n");
				sb.append("order by A.main_order_id DESC ,B.period_num ASC \n");

				LOG.debug(sb.toString());

				ps = this.READ_CONN.prepareStatement(sb.toString());

				if (param != null && !param.isEmpty() && param.size() > 0) {
					for (int i = 0; i < param.size(); i++) {
						if (param.get(i) == null) {
							ps.setString(i + 1, null);
						} else if (param.get(i).getClass() == String.class) {
							ps.setString(i + 1, param.get(i).toString());
						} else if (param.get(i).getClass() == Integer.class) {
							ps.setInt(i + 1, Integer.parseInt(param.get(i).toString()));
						} else {
							ps.setString(i + 1, param.get(i).toString());
						}
					}
				}

				rs = ps.executeQuery();

				mapAll = new HashMap<String, Object>();
				mapL1 = new HashMap<String, Object>();
				mapL2 = new HashMap<String, String>();

				level1_name = null;
				int L1Index = 0;

				while (rs.next()) {
					if (level1_name == null) {
						level1_name = rs.getString("main_order_id");
						mapL1.put("title", rs.getString("title"));
						mapL1.put("local_id", rs.getString("local_id"));
						mapL1.put("start_period_num", rs.getString("start_period_num"));
						mapL1.put("bet_data", rs.getString("bet_data"));
						mapL1.put("no_of_winning_period", rs.getString("no_of_winning_period"));
						mapL1.put("money_unit", rs.getString("money_unit"));
						mapL1.put("no_of_period", rs.getString("no_of_period"));
						mapL1.put("no_of_kj_period", rs.getString("no_of_kj_period"));
						mapL1.put("action_time", rs.getString("action_time"));
						mapL1.put("main_amount", rs.getString("main_amount"));
						mapL1.put("main_bonus", rs.getString("main_bonus"));
						mapL1.put("main_fan_den", rs.getString("main_fan_den"));
						mapL1.put("main_order_type", rs.getString("main_order_type"));
						mapL1.put("main_order_status", rs.getString("main_order_status"));
						mapL1.put("lottery_title", rs.getString("lottery_title"));
						mapL1.put("bonus_set_ratio", rs.getString("bonus_set_ratio"));
						mapL1.put("bet_ratio", rs.getString("bet_ratio"));
						mapL1.put("order_time", rs.getString("order_time"));
						mapL1.put("member_acc_id", rs.getString("acc_id"));
						mapL1.put("auth_id", rs.getString("auth_id"));
						mapL1.put("handi_cap", rs.getString("handi_cap"));
					}

					if (!level1_name.equals(rs.getString("main_order_id"))) {
						mapAll.put(getNumByLen(L1Index, 10) + "-" + level1_name, mapL1.clone());
						level1_name = rs.getString("main_order_id");
						mapL1.clear();
						// mapL1 = null;
						// mapL1 = new HashMap<String, Object>();
						// mapL2 = new HashMap<String, String>();
						mapL1.put("title", rs.getString("title"));
						mapL1.put("local_id", rs.getString("local_id"));
						mapL1.put("start_period_num", rs.getString("start_period_num"));
						mapL1.put("bet_data", rs.getString("bet_data"));
						mapL1.put("no_of_winning_period", rs.getString("no_of_winning_period"));
						mapL1.put("money_unit", rs.getString("money_unit"));
						mapL1.put("no_of_period", rs.getString("no_of_period"));
						mapL1.put("no_of_kj_period", rs.getString("no_of_kj_period"));
						mapL1.put("action_time", rs.getString("action_time"));
						mapL1.put("main_amount", rs.getString("main_amount"));
						mapL1.put("main_bonus", rs.getString("main_bonus"));
						mapL1.put("main_fan_den", rs.getString("main_fan_den"));
						mapL1.put("main_order_type", rs.getString("main_order_type"));
						mapL1.put("main_order_status", rs.getString("main_order_status"));
						mapL1.put("lottery_title", rs.getString("lottery_title"));
						mapL1.put("bonus_set_ratio", rs.getString("bonus_set_ratio"));
						mapL1.put("bet_ratio", rs.getString("bet_ratio"));
						mapL1.put("order_time", rs.getString("order_time"));
						mapL1.put("member_acc_id", rs.getString("acc_id"));
						mapL1.put("auth_id", rs.getString("auth_id"));
						mapL1.put("handi_cap", rs.getString("handi_cap"));
						L1Index++;
					}
					mapL2.clear();
					// mapL2 = null;
					// mapL2 = new HashMap<String, String>();
					mapL2.put("period_num", rs.getString("period_num"));
					mapL2.put("no_of_bet", rs.getString("no_of_bet"));
					mapL2.put("mid_amount", rs.getString("mid_amount"));
					mapL2.put("mid_bonus", rs.getString("mid_bonus"));
					mapL2.put("mid_order_status", rs.getString("mid_order_status"));
					mapL2.put("mid_fan_den", rs.getString("mid_fan_den"));
					mapL2.put("mid_bonus", rs.getString("mid_bonus"));
					mapL2.put("mid_order_id", rs.getString("mid_order_id"));
					mapL2.put("no_of_winning_bet", rs.getString("no_of_winning_bet"));
					mapL2.put("kj_data", rs.getString("kj_data"));
					mapL2.put("max_bonus", rs.getString("max_bonus"));
					mapL2.put("no_of_bet_times", rs.getString("no_of_bet_times"));
					mapL2.put("check_bet_time", rs.getString("check_bet_time"));
					mapL2.put("stop_betting_time", rs.getString("stop_betting_time"));
					mapL2.put("kj_time", rs.getString("kj_time"));
					mapL2.put("complete_time", rs.getString("complete_time"));
					mapL2.put("check_mid_com_time", rs.getString("check_mid_com_time"));
					mapL1.put(rs.getString("mid_order_id"), mapL2.clone());
				}
				if (level1_name != null) {
					mapAll.put(getNumByLen(L1Index, 10) + "-" + level1_name, mapL1.clone());
					L1Index++;
				}
				return mapAll;
			}
		} catch (Exception e) {
			LOG.error("test()_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			mapAll = null;
			return null;
		} finally {
			sb.setLength(0);
			sb = null;
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
				}
			}
			rs = null;
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
				}
			}
			ps = null;
			mapL1 = null;
			mapL2 = null;
			level1_name = "";
			level1_name = null;
		}
	}

	@Override
	public List<String> getTableDate(String format) {
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		List<Object> array = null;
		List<String> tableDate = null;

		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return tableDate;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				sb = new StringBuilder();
				array = new ArrayList<Object>();
				tableDate = new ArrayList<String>();
				if (format != null && !format.isEmpty() && !"".trim().equals(format)) {
					format = format + "%";
				}

				sb.append("SELECT table_date FROM( \n");
				sb.append("	SELECT SUBSTRING(table_name,LENGTH(table_name)-7,LENGTH(table_name)) AS table_date FROM information_schema.TABLES  \n");
				sb.append("	WHERE table_schema = 'ctt_manager' AND table_name LIKE ? \n");
				array.add(format);
				sb.append(")A \n");
				sb.append("WHERE DATE_FORMAT(ADDDATE(CURRENT_DATE,INTERVAL 3 DAY),'%Y%m%d') >= DATE_FORMAT(A.table_date ,'%Y%m%d') \n");
				sb.append("ORDER BY table_date DESC \n");
				sb.append("LIMIT 0,11 \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), array);

				if (listMap.size() > 0) {
					for (int i = 0; i < listMap.size(); i++) {
						tableDate.add(listMap.get(i).get("tableDate").toString());
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
		}
		return tableDate;
	}

	private String getNumByLen(int val, int len) {
		String tmpStr = null;
		try {
			tmpStr = "" + val;
			while (tmpStr.length() < len) {
				tmpStr = "0" + tmpStr;
			}
			return tmpStr.toString();
		} catch (Exception e) {
			return tmpStr.toString();
		} finally {
			tmpStr = "";
			tmpStr = null;
		}
	}
}
