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

import tw.com.ctt.dao.IOrderInfoOfBettingDao;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class OrderInfoOfBettingDaoImpl extends BaseDao implements IOrderInfoOfBettingDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5393598519122647798L;
	private static final Logger LOG = LogManager.getLogger(OrderInfoOfBettingDaoImpl.class.getName());

	@Override
	public Map<String, Object> getAllLotteryNoFrontNumber() {
		StringBuilder sb = null;
		Map<String, Object> mapAll = null;
		Map<String, Map<String, Map<String, Map<String, String>>>> mapL1 = null;
		Map<String, Map<String, Map<String, String>>> mapL2 = null;
		Map<String, Map<String, String>> mapL3 = null;
		Map<String, String> mapL4 = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String level1_name = null;
		String level2_name = null;
		String level3_name = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return null;
			} else {
				sb = new StringBuilder();
				sb.append(
						"select A.auth_id as main_id, A.lottery_title as main_title,D.title as local_title, B.lottery_title as mid_title, B.auth_id as mid_id, C.lottery_title as min_title, D.name as local_name,D.id as local_id, F.switch as switch, C.total_no_of_bet as total_no_of_bet, C.auth_id as min_auth_id, C.lottery_rule, C.played_text, C.lottery_example, C.function_name from \n");
				sb.append("(SELECT auth_id,lottery_title,auth_level_type FROM `ctt_manager`.`ctt_lottery_auth` where auth_level_type =1) A \n");
				sb.append("left outer join \n");
				sb.append("(select `id`, `title` , `name`, `type` from `ctt_manager`.`ctt_lottery_type` where 1) D \n");
				sb.append("on A.auth_id=D.`type` \n");
				sb.append("left outer join \n");
				sb.append(
						"(SELECT auth_id,lottery_title,level1_id,auth_level_type FROM `ctt_manager`.`ctt_lottery_auth` where auth_level_type =2) B \n");
				sb.append("on A.auth_id=B.level1_id \n");
				sb.append("left outer join \n");
				sb.append(
						"(SELECT sort_level1_id as CL1, sort_level2_id as CL2, sort_level3_id as CL3, auth_id,lottery_title,level1_id,level2_id,auth_level_type,lottery_rule , played_text, lottery_example, function_name, total_no_of_bet FROM `ctt_manager`.`ctt_lottery_auth` where auth_level_type =3 )C \n");
				sb.append("on B.auth_id= C.level2_id \n");
				sb.append("left outer join \n");
				sb.append("(select id, auth_id, switch from `ctt_manager`.`ctt_lottery_auth_list`) F \n");
				sb.append("on F.`auth_id`=C.`auth_id` AND F.`id`=D.`id` \n");
				sb.append("where F.`id` is not null \n");
				sb.append("order by CL1 asc,D.`id` asc ,CL2 asc,CL3 asc; \n");

				ps = this.READ_CONN.prepareStatement(sb.toString());
				rs = ps.executeQuery();
				level1_name = null;
				level2_name = null;
				level3_name = null;
				mapAll = new HashMap<String, Object>();
				mapL1 = new HashMap<String, Map<String, Map<String, Map<String, String>>>>();
				mapL2 = new HashMap<String, Map<String, Map<String, String>>>();
				mapL3 = new HashMap<String, Map<String, String>>();
				mapL4 = new HashMap<String, String>();

				while (rs.next()) {
					if (level1_name == null) {
						level1_name = rs.getString("main_id");
					}
					if (level2_name == null) {
						level2_name = rs.getString("local_id");
					}
					if (level3_name == null) {
						level3_name = rs.getString("mid_id");
					}

					if (!level3_name.equals(rs.getString("mid_id"))) {
						mapL2.put(level3_name, mapL3);
						level3_name = rs.getString("mid_id");
						mapL3 = null;
						mapL3 = new HashMap<String, Map<String, String>>();
					}
					if (!level2_name.equals(rs.getString("local_id"))) {
						mapL1.put(level2_name, mapL2);
						level2_name = rs.getString("local_id");
						mapL2 = null;
						mapL2 = new HashMap<String, Map<String, Map<String, String>>>();
					}
					if (!level1_name.equals(rs.getString("main_id"))) {
						mapAll.put(level1_name, mapL1);
						level1_name = rs.getString("main_id");
						mapL1 = null;
						mapL1 = new HashMap<String, Map<String, Map<String, Map<String, String>>>>();
					}
					mapL4 = null;
					mapL4 = new HashMap<String, String>();
					mapL4.put("switch", rs.getString("switch"));
					mapL4.put("totalNoOfBet", rs.getString("total_no_of_bet"));
					mapL3.put(rs.getString("min_auth_id"), mapL4);
				}
				if (mapL3 != null && !mapL3.isEmpty() && mapL3.size() > 0 && level3_name != null) {
					mapL2.put(level3_name, mapL3);
				}
				if (mapL2 != null && !mapL2.isEmpty() && mapL2.size() > 0 && level2_name != null) {
					mapL1.put(level2_name, mapL2);
				}
				if (level1_name != null) {
					mapAll.put(level1_name, mapL1);
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
			mapL3 = null;
			mapL4 = null;
			level1_name = "";
			level2_name = "";
			level3_name = "";
			level1_name = null;
			level2_name = null;
			level3_name = null;
		}
	}

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
	public List<Map<String, Object>> getSubOrderInfo() {
		StringBuilder sb = new StringBuilder();
		List<Object> selectObj = new ArrayList<Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				sb.append(
						"select `played_id`, `played_name`, `played_text`, `note`, `baseline_index` from `ctt_manager`.`ctt_lottery_sub_played` where 1;");
				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);
				return listMap;
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
		return listMap;
	}

	@Override
	public List<Map<String, Object>> getSubOrderInfoNoOfBet() {
		StringBuilder sb = new StringBuilder();
		List<Object> selectObj = new ArrayList<Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return listMap;

			} else {
				sb.append("select `type`, `key`, `value`, `text` from `ctt_manager`.`ctt_lottery_betnum` where 1;");
				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);
				return listMap;
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
		return listMap;
	}

	@Override
	public boolean checkRealTimeBaselinePeriodAndHandiCap(long accId, long periodNum, long minAuthId, int localId, String dateOfTable, int handiCap,
			Map<String, Map<String, String>> tmpRatios) {
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return false;
			} else {
				sb = new StringBuilder();

				sb.append(
						" 	select case when A.cou=1 AND A.is_order_can_be_delete = 1 then 1 else 0 end as is_order_can_be_delete, B.baseline as baseline, C.handicap as handicap, D.dt_switch, D.dt_ratio, D.dt_bonus, B.prize_level from(  	\n");
				sb.append(" 	(  	\n");
				sb.append(
						" 	select count(1) as cou, case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_order_can_be_delete from ctt_manager.ctt_lottery where period_num = ? AND id = ?  	\n");
				sb.append(" 	)A  	\n");
				sb.append(" 	left outer join  	\n");
				sb.append(" 	(  	\n");
				sb.append(
						" 	select date, case when ratio_index = 0 then baseline_origin else case when ratio_index = 1 then baseline_level1 else baseline_level2 end end as baseline , prize_level  	\n");
				sb.append(
						" 	from `ctt_manager`.`ctt_lottery_current_ratio` where local_id = ? AND lottery_min_id = ? AND DATE_FORMAT(date,'%Y/%m/%d') = DATE_FORMAT(?,'%Y/%m/%d') 	\n");
				sb.append(" 	)B  	\n");
				sb.append(" 	on 1  	\n");
				sb.append(" 	left outer join  	\n");
				sb.append(" 	(  	\n");
				sb.append(" 	select handicap from `ctt_manager`.`ctt_member_acc` where acc_id = ?   	\n");
				sb.append(" 	)C  	\n");
				sb.append(" 	on 1 	\n");
				sb.append(" 	left outer join  	\n");
				sb.append(" 	(  	\n");
				sb.append(
						" 	select dt_switch, dt_ratio, dt_bonus, prize_level from `ctt_manager`.`ctt_lottery_amount` where lottery_local_id = ? AND lottery_min_id = ? \n");
				sb.append(" 	)D  	\n");
				sb.append(" 	on B.prize_level = D.prize_level    	\n");
				sb.append(" 	); 	\n");

				ps = this.READ_CONN.prepareStatement(sb.toString());

				ps.setLong(1, periodNum);
				ps.setInt(2, localId);
				ps.setInt(3, localId);
				ps.setLong(4, minAuthId);
				ps.setString(5, dateOfTable);
				ps.setLong(6, accId);
				ps.setInt(7, localId);
				ps.setLong(8, minAuthId);

				rs = ps.executeQuery();
				while (rs.next()) {
					String resultA = rs.getString("is_order_can_be_delete") != null ? rs.getString("is_order_can_be_delete") : "0";
					String baseline = rs.getString("baseline") != null ? rs.getString("baseline") : "0";
					String resultC = rs.getString("handicap") != null ? rs.getString("handicap") : "0";
					String dtSwitch = rs.getString("dt_switch") != null ? rs.getString("dt_switch") : "0";
					String dtRatio = rs.getString("dt_ratio") != null ? rs.getString("dt_ratio") : "0";
					String dtBonus = rs.getString("dt_bonus") != null ? rs.getString("dt_bonus") : "0";
					String prizeLevel = rs.getString("prize_level") != null ? rs.getString("prize_level") : "0";
					if ("0".equals(resultA) || (Integer.parseInt(resultC) & (1 << (handiCap > 0 ? handiCap - 1 : 0))) == 0) {
						return false;
					}
					try {
						if (new BigDecimal(tmpRatios.get(prizeLevel).get("baseline")).compareTo(new BigDecimal(baseline)) != 0
								|| new BigDecimal(tmpRatios.get(prizeLevel).get("dt_ratio")).compareTo(new BigDecimal(dtRatio)) != 0
								|| new BigDecimal(tmpRatios.get(prizeLevel).get("dt_bonus")).compareTo(new BigDecimal(dtBonus)) != 0
								|| !dtSwitch.equals(tmpRatios.get(prizeLevel).get("dt_switch"))) {
							return false;
						}
					} catch (Exception e) {
						return false;
					}
				}
				return true;
			}
		} catch (Exception e) {
			LOG.error("checkRealTimeBaseline_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
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
		}
	}

	@Override
	public boolean checkMidOrderStatusIsNormal(long periodNum, int localId, long mainOrderId, String dateOfTable) {
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return false;
			} else {
				sb = new StringBuilder();

				sb.append(" select case when A.cou=1 AND A.is_order_can_be_delete = 1 then 1 else 0 end as resultA, B.result as resultB from( \n");
				sb.append("    ( \n");
				sb.append(
						"        select count(1) as cou, case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_order_can_be_delete from ctt_manager.ctt_lottery where period_num = ? AND id = ?  \n");
				sb.append("    )A \n");
				sb.append("    left outer join \n");
				sb.append("    ( \n");
				sb.append("        select case when order_status = 1 then 1 else 0 end as result from ctt_manager.mid_order_" + dateOfTable
						+ " where main_order_id = ?  and period_num = ? \n");
				sb.append("    )B \n");
				sb.append("    on 1 \n");
				sb.append(" ); \n");

				ps = this.READ_CONN.prepareStatement(sb.toString());

				ps.setLong(1, periodNum);
				ps.setInt(2, localId);
				ps.setLong(3, mainOrderId);
				ps.setLong(4, periodNum);

				rs = ps.executeQuery();
				while (rs.next()) {
					String resultB = rs.getString("resultB") != null ? rs.getString("resultB") : "0";
					String resultA = rs.getString("resultA") != null ? rs.getString("resultA") : "0";
					if ("1".equals(resultB) && "1".equals(resultA)) {
						return true;
					}
					return false;
				}
			}
		} catch (Exception e) {
			LOG.error("checkMinOrderStatusIsNormal_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
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
		}
		return false;
	}

	@Override
	public boolean checkMainOrderStatusIsNormal(long periodNum, int localId, long mainOrderId, String dateOfTable) {
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return false;
			} else {
				sb = new StringBuilder();
				sb.append(" select case when A.cou=1 AND A.is_order_can_be_delete = 1 then 1 else 0 end as resultA, B.result as resultB from( \n");
				sb.append("    ( \n");
				sb.append(
						"        select count(1) as cou, case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_order_can_be_delete from ctt_manager.ctt_lottery where period_num = ? AND id = ?  \n");
				sb.append("    )A \n");
				sb.append("    left outer join \n");
				sb.append("    ( \n");
				sb.append("        select case when order_status = 1 then 1 else 0 end as result from ctt_manager.main_order_" + dateOfTable
						+ " where main_order_id = ?  \n");
				sb.append("    )B \n");
				sb.append("    on 1 \n");
				sb.append(" ); \n");
				ps = this.READ_CONN.prepareStatement(sb.toString());
				ps.setLong(1, periodNum);
				ps.setInt(2, localId);
				ps.setLong(3, mainOrderId);
				rs = ps.executeQuery();
				while (rs.next()) {
					String resultA = rs.getString("resultA") != null ? rs.getString("resultA") : "0";
					String resultB = rs.getString("resultB") != null ? rs.getString("resultB") : "0";
					if ("1".equals(resultA) && "1".equals(resultB)) {
						return true;
					}
					return false;
				}
			}
		} catch (Exception e) {
			LOG.error("checkMainOrderStatusIsNormal_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
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
		}
		return false;
	}

	@Override
	public boolean checkPeriodStatusIsNormal(long periodNum, int localId, String dateOfTable) {
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return false;
			} else {
				sb = new StringBuilder();
				sb.append(" select case when A.cou=1 AND A.is_order_can_be_delete = 1 then 1 else 0 end as resultA from( \n");
				sb.append("    ( \n");
				sb.append(
						"        select count(1) as cou, case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_order_can_be_delete from ctt_manager.ctt_lottery where period_num = ? AND id = ?  \n");
				sb.append("    )A \n");
				sb.append(" ); \n");
				ps = this.READ_CONN.prepareStatement(sb.toString());
				ps.setLong(1, periodNum);
				ps.setInt(2, localId);
				rs = ps.executeQuery();
				while (rs.next()) {
					String resultA = rs.getString("resultA") != null ? rs.getString("resultA") : "0";
					if ("1".equals(resultA)) {
						return true;
					}
					return false;
				}
			}
		} catch (Exception e) {
			LOG.error("checkMainOrderStatusIsNormal_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
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
		}
		return false;
	}

	@Override
	public long getAllLotteryLastUpdateTime() {
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		long t1 = 0;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return 0;
			} else {
				sb.append("SELECT UNIX_TIMESTAMP(create_date_time) AS t1 FROM `ctt_manager`.`ctt_lottery_auth_list` ORDER BY create_date_time DESC");
				ps = this.READ_CONN.prepareStatement(sb.toString());
				rs = ps.executeQuery();
				if (rs.next()) {
					t1 = rs.getLong("t1");
				}
				sb.setLength(0);
				ps.clearParameters();
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				return t1;
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
		return t1;
	}

	@Override
	public Map<String, Object> getAllHandicap() {
		Map<String, Object> mapAll = null;
		Map<String, String> mapL4 = null;
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return null;

			} else {
				sb.append(
						" select handicap_id, handicap_name, bonus_set_max, bonus_set_min, relative_baseline, index_of_account from `ctt_manager`.`ctt_lottery_handicap`  order by handicap_id asc \n");
				ps = this.READ_CONN.prepareStatement(sb.toString());
				rs = ps.executeQuery();
				mapAll = new HashMap<String, Object>();
				mapL4 = new HashMap<String, String>();
				while (rs.next()) {
					mapL4 = null;
					mapL4 = new HashMap<String, String>();
					mapL4.put("handicap_name", rs.getString("handicap_name"));
					mapL4.put("handicap_id", rs.getString("handicap_id"));
					mapL4.put("bonus_set_max", rs.getString("bonus_set_max"));
					mapL4.put("bonus_set_min", rs.getString("bonus_set_min"));
					mapL4.put("relative_baseline", rs.getString("relative_baseline"));
					mapL4.put("index_of_account", rs.getString("index_of_account"));
					mapAll.put(rs.getString("handicap_id"), mapL4);
				}
				return mapAll;
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
		return mapAll;
	}
}
