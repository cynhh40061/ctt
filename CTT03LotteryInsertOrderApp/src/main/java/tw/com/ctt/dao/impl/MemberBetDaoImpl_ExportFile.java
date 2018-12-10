package tw.com.ctt.dao.impl;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.util.MyUtil;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

/**
 * This is the most fast way to insert orders.
 * it will write a file in harddisk, and then call a cmd to connect 
 * to mysql, and proc the file.
 * @author Quanto
 *
 */
public class MemberBetDaoImpl_ExportFile extends MemberBetDaoImpl {
	private static final long serialVersionUID = -5393598519122647798L;
	private static final Logger LOG = LogManager.getLogger(MemberBetDaoImpl_ExportFile.class.getName());

	@Override
	public boolean insertMainOrder_ExportFile(List<Map<String, Object>> mainOrderList, FileWriter fw) {
		StringBuilder sb = null;
		try {
			sb = new StringBuilder();
			int leaveCount = 0;
			sb.append("SET sql_mode = 'NO_UNSIGNED_SUBTRACTION'; \n");
			sb.append("INSERT IGNORE INTO ctt_manager.main_order_" + mainOrderList.get(0).get("dateOfTable")
					+ " (main_order_id, acc_id, start_period_num, stop_period_num, bet_data, no_of_period, game_id, played_id, action_time, amount, money_unit, order_type, order_status, bonus_set_ratio, handi_cap, bet_ratio, is_dt) \n ");
			for (int i = 0; i < mainOrderList.size(); i++) {
				leaveCount++;
				sb.append(" select ");
				sb.append("'" + ("" + mainOrderList.get(i).get("main_order_id")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + mainOrderList.get(i).get("acc_id")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + mainOrderList.get(i).get("start_period_num")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + mainOrderList.get(i).get("stop_period_num")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + mainOrderList.get(i).get("bet_data")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + mainOrderList.get(i).get("no_of_period")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + mainOrderList.get(i).get("game_id")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + mainOrderList.get(i).get("played_id")).replaceAll("'", "\\'") + "',");
				String tmpActionTime = MyUtil.dateFormat((Date) mainOrderList.get(i).get("action_time"),
						"yyyy/MM/dd HH:mm:ss");
				if (tmpActionTime == null && "".equals(tmpActionTime)) {
					tmpActionTime = MyUtil.dateFormat(new Date(), "yyyy/MM/dd HH:mm:ss");
				}
				sb.append("'" + tmpActionTime.replaceAll("'", "\\'") + "',");
				sb.append("'"
						+ ("" + ((BigDecimal) mainOrderList.get(i).get("amount")).setScale(2, BigDecimal.ROUND_DOWN))
								.replaceAll("'", "\\'")
						+ "',");
				sb.append("'" + (""
						+ ((BigDecimal) mainOrderList.get(i).get("money_unit")).setScale(2, BigDecimal.ROUND_DOWN))
								.replaceAll("'", "\\'")
						+ "',");
				sb.append("'" + ("" + mainOrderList.get(i).get("order_type")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + mainOrderList.get(i).get("order_status")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + mainOrderList.get(i).get("bonus_set_ratio")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + mainOrderList.get(i).get("handi_cap")).replaceAll("'", "\\'") + "',");
				sb.append("'"
						+ ("" + ((BigDecimal) mainOrderList.get(i).get("bet_ratio")).setScale(2, BigDecimal.ROUND_DOWN))
								.replaceAll("'", "\\'")
						+ "',");
				sb.append("" + ("" + mainOrderList.get(i).get("is_dt")).replaceAll("'", "\\'") + "");

				if ((i + 1) % 1000 == 0) {
					sb.append(" NOLOGGING; \n\n");
					String sql = sb.toString();
					fw.write(sql);					
					leaveCount = 0;
					sb = new StringBuilder();
					sb.append("INSERT IGNORE INTO ctt_manager.main_order_" + mainOrderList.get(0).get("dateOfTable")
							+ " (main_order_id, acc_id, start_period_num, stop_period_num, bet_data, no_of_period, game_id, played_id, action_time, amount, money_unit, order_type, order_status, bonus_set_ratio, handi_cap, bet_ratio, is_dt)  ");
				} else if (i == mainOrderList.size() - 1) {
					sb.append(" NOLOGGING; \n\n");
				} else {
					sb.append(" union \n");
				}
			}
			if (leaveCount != 0) {
				String sql = sb.toString();
				fw.write(sql);
				return true;
			} else {
				return true;
			}
		} catch (Exception e) {
			LOG.error("insertMidOrder_Exception===" + e);
			return false;
		} finally {
		}
	}

	@Override
	public boolean insertMidOrder_ExportFile(List<Map<String, Object>> midOrderList, FileWriter fw) {
		Map<String, Object> insertMap = new HashMap<String, Object>();
		StringBuilder sb = null;
		try {
			sb = new StringBuilder();
			int leaveCount = 0;
			sb.append("INSERT IGNORE INTO ctt_manager.mid_order_" + midOrderList.get(0).get("dateOfTable")
					+ " (main_order_id, mid_order_id, acc_id, period_num, game_id, played_id, amount, no_of_bet, max_bonus, fan_den, no_of_bet_times, order_status) \n");
			for (int i = 0; i < midOrderList.size(); i++) {
				leaveCount++;
				sb.append(" select ");
				sb.append("'" + ("" + midOrderList.get(i).get("main_order_id")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + midOrderList.get(i).get("mid_order_id")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + midOrderList.get(i).get("acc_id")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + midOrderList.get(i).get("period_num")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + midOrderList.get(i).get("game_id")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + midOrderList.get(i).get("played_id")).replaceAll("'", "\\'") + "',");
				sb.append(
						"'" + ("" + ((BigDecimal) midOrderList.get(i).get("amount")).setScale(2, BigDecimal.ROUND_DOWN))
								.replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + midOrderList.get(i).get("no_of_bet")).replaceAll("'", "\\'") + "',");
				sb.append("'"
						+ ("" + ((BigDecimal) midOrderList.get(i).get("max_bonus")).setScale(2, BigDecimal.ROUND_DOWN))
								.replaceAll("'", "\\'")
						+ "',");
				sb.append("'"
						+ ("" + ((BigDecimal) midOrderList.get(i).get("fan_den")).setScale(2, BigDecimal.ROUND_DOWN))
								.replaceAll("'", "\\'")
						+ "',");
				sb.append("'" + ("" + midOrderList.get(i).get("no_of_bet_times")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + midOrderList.get(i).get("order_status")).replaceAll("'", "\\'") + "'");
				if ((i + 1) % 1000 == 0) {
					sb.append(" NOLOGGING; \n\n");
					String sql = sb.toString();
					fw.write(sql);
					leaveCount = 0;
					sb = new StringBuilder();
					sb.append("INSERT IGNORE INTO ctt_manager.mid_order_" + midOrderList.get(0).get("dateOfTable")
							+ " (main_order_id, mid_order_id, acc_id, period_num, game_id, played_id, amount, no_of_bet, max_bonus, fan_den, no_of_bet_times, order_status)  ");
				} else if (i == midOrderList.size() - 1) {
					sb.append(" NOLOGGING; \n\n");
				} else {
					sb.append(" union \n");
				}
			}
			if (leaveCount != 0) {
				String sql = sb.toString();
				fw.write(sql);
				return true;
			} else {
				return true;
			}
		} catch (Exception e) {
			LOG.info("insertMidOrder_Exception===" + e);
			return false;
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
	}

	@Override
	public boolean insertBetOrder_ExportFile(List<Map<String, Object>> subOrderList, FileWriter fw) {
		LOG.debug("insertBetOrder");
		StringBuilder sb = null;
		try {
			sb = new StringBuilder();
			int leaveCount = 0;
			sb.append("INSERT IGNORE INTO ctt_manager.bet_order_" + subOrderList.get(0).get("dateOfTable")
					+ " (main_order_id, mid_order_id, sub_order_id, acc_id, period_num, bet_data, game_id, played_id, amount, no_of_bet, order_status, bonus, sub_action, baseline)  \n");
			for (int i = 0; i < subOrderList.size(); i++) {
				leaveCount++;
				sb.append(" select ");
				sb.append("'" + ("" + subOrderList.get(i).get("main_order_id")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + subOrderList.get(i).get("mid_order_id")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + subOrderList.get(i).get("sub_order_id")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + subOrderList.get(i).get("acc_id")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + subOrderList.get(i).get("period_num")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + subOrderList.get(i).get("bet_data")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + subOrderList.get(i).get("game_id")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + subOrderList.get(i).get("played_id")).replaceAll("'", "\\'") + "',");
				sb.append(
						"'" + ("" + ((BigDecimal) subOrderList.get(i).get("amount")).setScale(2, BigDecimal.ROUND_DOWN))
								.replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + subOrderList.get(i).get("no_of_bet")).replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + subOrderList.get(i).get("order_status")).replaceAll("'", "\\'") + "',");
				sb.append(
						"'" + ("" + ((BigDecimal) subOrderList.get(i).get("bonus")).setScale(2, BigDecimal.ROUND_DOWN))
								.replaceAll("'", "\\'") + "',");
				sb.append("'" + ("" + subOrderList.get(i).get("sub_action")).replaceAll("'", "\\'") + "',");
				sb.append("'"
						+ ("" + ((BigDecimal) subOrderList.get(i).get("baseline")).setScale(7, BigDecimal.ROUND_DOWN))
								.replaceAll("'", "\\'")
						+ "'");
				if ((i + 1) % 5000 == 0) {
					sb.append(" NOLOGGING; \n\n");
					String sql = sb.toString();
					fw.write(sql);
					leaveCount = 0;
					sb = new StringBuilder();
					sb.append("INSERT IGNORE INTO ctt_manager.bet_order_" + subOrderList.get(0).get("dateOfTable")
							+ " (main_order_id, mid_order_id, sub_order_id, acc_id, period_num, bet_data, game_id, played_id, amount, no_of_bet, order_status, bonus, sub_action, baseline)  ");
				} else if (i == subOrderList.size() - 1) {
					sb.append(" NOLOGGING; \n\n");
				} else {
					sb.append(" union \n");
				}
			}
			if (leaveCount != 0) {
				String sql = sb.toString();
				fw.write(sql);
				return true;
			} else {
				return true;
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
	}
	
	
	public long gettBetRatioLastUpdateTime() {
		StringBuilder sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return 0;
			} else {
				long t1 = 0;
				sql = new StringBuilder();
				sql.append(
						"SELECT UNIX_TIMESTAMP(ratio_update_time) AS t1 FROM `ctt_manager`.`ctt_lottery_current_ratio` ORDER BY ratio_update_time DESC limit 1");
				ps = this.READ_CONN.prepareStatement(sql.toString());
				rs = ps.executeQuery();
				if (rs.next()) {
					t1 = rs.getLong("t1");
				}
				sql.setLength(0);
				ps.clearParameters();
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				
				return t1;
			}
		} catch (Exception e) {
			LOG.error("checkCurrentBetRatio()_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			return 0;
		} finally {
			if (sql != null) {
				sql.setLength(0);
				sql = null;
			}
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
	public boolean updateBetRatio_ExportFile(List<Map<String, Object>> updateOrderList, FileWriter fw) {
		StringBuilder sb = null;
		try {
			long beforeUpdate = gettBetRatioLastUpdateTime();
			sb = new StringBuilder();
			int leaveCount = 0;
			for (int i = 0; i < updateOrderList.size(); i++) {
				leaveCount++;				
				sb.append(" UPDATE `ctt_manager`.`ctt_lottery_current_ratio`  \n");
				sb.append(" SET  \n");
				sb.append(
						" `ratio_update_time`=  case when " + updateOrderList.get(i).get("amount")+"+amount < baseline_threshold1 then case when ratio_index <> 0 then CURRENT_TIMESTAMP else ratio_update_time end else case when " + updateOrderList.get(i).get("amount")+"+amount < baseline_threshold2 then case when ratio_index <> 1 then CURRENT_TIMESTAMP else ratio_update_time end else case when ratio_index <> 2 then CURRENT_TIMESTAMP else ratio_update_time end end end, \n");
				sb.append(
						" `ratio_index`= case when " + updateOrderList.get(i).get("amount")+"+amount < baseline_threshold1 then 0 else case when " + updateOrderList.get(i).get("amount")+"+amount < baseline_threshold2 then 1 else 2 end end , \n");
				sb.append(" `amount` = amount +" + updateOrderList.get(i).get("amount")+"  \n");
				sb.append(" WHERE \n");
				sb.append(
						" local_id = "+ updateOrderList.get(i).get("localId")+" AND lottery_type_id = "+ updateOrderList.get(i).get("mainId")+" AND lottery_mid_id = "+ updateOrderList.get(i).get("midId")+" AND lottery_min_id = "+ updateOrderList.get(i).get("minAuthId")+" AND date = '"
								+ updateOrderList.get(i).get("dateOfTable") + "'; \n");
			}
			if (leaveCount != 0) {
				sb.append(" select case when (SELECT UNIX_TIMESTAMP(ratio_update_time) AS t1 FROM `ctt_manager`.`ctt_lottery_current_ratio` ORDER BY ratio_update_time DESC limit 1) > "+beforeUpdate+"  then http_post('http://192.168.1.125:8080/CTT03BetOrder/UpdateInfo!updateBaseline.php','') else 0 end as result; \n");
				sb.append(" select case when (SELECT UNIX_TIMESTAMP(ratio_update_time) AS t1 FROM `ctt_manager`.`ctt_lottery_current_ratio` ORDER BY ratio_update_time DESC limit 1) > "+beforeUpdate+"  then http_post('http://192.168.1.125:8080/CTT03QueryInfo/UpdateInfo!updateBaseline.php','') else 0 end as result; \n");
				sb.append("\n");
				
				String sql = sb.toString();
				fw.write(sql);
				return true;
			}
			return false;
		} catch (Exception e) {
			LOG.error("insertMidOrder_Exception===" + e);
			return false;
		} finally {
		}
	}

	@Override
	public void updateUserBalanceOfMainOrderBetting_ExportFile(List<Map<String, Object>> mainOrderList, FileWriter fw) {
		StringBuilder sb = null;
		try {
			sb = new StringBuilder();
			for (int i = 0; i < mainOrderList.size(); i++) {
				if (i < mainOrderList.size() - 1) {
					sb.append(("" + mainOrderList.get(i).get("main_order_id")).replaceAll("'", "\\'") + ",");
				} else {
					sb.append(("" + mainOrderList.get(i).get("main_order_id")).replaceAll("'", "\\'"));
				}
			}
			String allMainOrderId = sb.toString();

			sb = new StringBuilder();
			sb.append("UPDATE `ctt_manager`.`main_order_" + mainOrderList.get(0).get("dateOfTable")
					+ "` SET order_status = CASE main_order_id \n");
			for (int i = 0; i < mainOrderList.size(); i++) {
				sb.append("WHEN " + mainOrderList.get(i).get("main_order_id") + " THEN ( \n");
				sb.append("CASE WHEN ( ");
				sb.append(" (SELECT COUNT(1) FROM `ctt_manager`.`mid_order_" + mainOrderList.get(0).get("dateOfTable")
						+ "` WHERE main_order_id = " + mainOrderList.get(i).get("main_order_id") + ")= "
						+ mainOrderList.get(i).get("countOfMidOrders") + " ");
				sb.append("AND (SELECT COUNT(1) FROM `ctt_manager`.`bet_order_" + mainOrderList.get(0).get("dateOfTable")
						+ "` WHERE main_order_id = " + mainOrderList.get(i).get("main_order_id") + ")= "
						+ mainOrderList.get(i).get("countOfBetOrders") + ") THEN 1 ELSE 5 END ) \n");
			}
			sb.append("END WHERE main_order_id in (" + allMainOrderId + "); \n");
			String strCheckCountOfOrders = sb.toString();

			sb = new StringBuilder();
			for (int i = 0; i < mainOrderList.size(); i++) {
				sb.append("	UPDATE `ctt_manager`.`ctt_member_acc` A join `ctt_manager`.`main_order_" + mainOrderList.get(i).get("dateOfTable")
						+ "`  B on A.acc_id = B.acc_id SET  B.order_status = ");
				sb.append(" CASE WHEN A.balance >= " + mainOrderList.get(i).get("amount")
						+ " THEN B.order_status ELSE 5 END \n");
				sb.append(" , A.balance = CASE WHEN (A.balance >= " + mainOrderList.get(i).get("amount")
						+ ") THEN A.balance - " + mainOrderList.get(i).get("amount") + " ELSE A.balance END \n");
				sb.append("  WHERE A.acc_id = " + mainOrderList.get(i).get("acc_id") + " AND B.main_order_id = "
						+ mainOrderList.get(i).get("main_order_id") + " AND B.order_status = 1; \n\n");
			}
			String strUpdateMemberMoney = sb.toString();

			sb = new StringBuilder();
			sb.append(
					" INSERT INTO `ctt_manager`.`ctt_lottery_member_money_transfer_record`(acc_id,acc_name, balance, start_period_num, stop_period_num,local_id,played_id,record_order_type,money, period_total_count) \n");
			sb.append(
					" SELECT A.acc_id, A.acc_name, A.balance, B.start_period_num, B.stop_period_num ,B.game_id, B.played_id, CASE WHEN B.start_period_num <> B.stop_period_num THEN 2 ELSE 1 END, B.amount, B.no_of_period from `ctt_manager`.`main_order_"
							+ mainOrderList.get(0).get("dateOfTable")
							+ "` B left outer join `ctt_manager`.`ctt_member_acc`A on A.acc_id = B.acc_id WHERE B.main_order_id in("
							+ allMainOrderId + ") AND B.order_status = 1; \n\n");
			String strInsertTransferRecord = sb.toString();

			sb = new StringBuilder();
			sb.append("UPDATE `ctt_manager`.`mid_order_" + mainOrderList.get(0).get("dateOfTable") + "` A left outer join `ctt_manager`.`main_order_"
					+ mainOrderList.get(0).get("dateOfTable") + "` B on A.main_order_id = B.main_order_id ");
			sb.append("SET A.order_status = CASE B.order_status WHEN 5 THEN 3 ELSE A.order_status END ");
			sb.append("WHERE A.main_order_id in (" + allMainOrderId + "); \n\n");
			String strUpdateMidOrderStatus = sb.toString();

			sb = new StringBuilder();
			sb.append("UPDATE `ctt_manager`.`bet_order_" + mainOrderList.get(0).get("dateOfTable") + "` A left outer join `ctt_manager`.`main_order_"
					+ mainOrderList.get(0).get("dateOfTable") + "` B on A.main_order_id = B.main_order_id ");
			sb.append("SET A.order_status = CASE B.order_status WHEN 5 THEN 0 ELSE A.order_status END ");
			sb.append("WHERE A.main_order_id in (" + allMainOrderId + "); \n\n");
			String strUpdateBetOrderStatus = sb.toString();

			String sql = strCheckCountOfOrders + strUpdateMemberMoney + strInsertTransferRecord +strUpdateMidOrderStatus + strUpdateBetOrderStatus;
			fw.write(sql);
		} catch (Exception e) {
			LOG.info("insertMainOrder_Exception===" + e);
		} finally {
		}
	}

}
