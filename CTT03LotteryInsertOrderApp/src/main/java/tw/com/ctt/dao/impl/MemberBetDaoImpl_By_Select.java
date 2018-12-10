package tw.com.ctt.dao.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.dao.IMemberBetDao;
import tw.com.ctt.model.MemBean;
import tw.com.ctt.service.impl.BaselineInfoServiceImpl;
import tw.com.ctt.util.CalculateMemberRatio;
import tw.com.ctt.util.DataSource;
import tw.com.ctt.util.MyUtil;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class MemberBetDaoImpl_By_Select extends MemberBetDaoImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5393598519122647798L;
	private static final Logger LOG = LogManager.getLogger(MemberBetDaoImpl_By_Select.class.getName());

	@Override
	public boolean insertMainOrder(List<Map<String, Object>> mainOrderList) {
		StringBuilder sb = null;
		Statement stat = null;
		try {
			sb = new StringBuilder();
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("no connection");
				return false;
			} else {
				if (this.WRITE_CONN.getAutoCommit()) {
					this.WRITE_CONN.setAutoCommit(false);
				}
				int leaveCount = 0;
				sb.append("INSERT INTO ctt_manager.main_order_" + mainOrderList.get(0).get("dateOfTable")
						+ " (main_order_id, acc_id, start_period_num, stop_period_num, bet_data, no_of_period, game_id, played_id, action_time, amount, money_unit, order_type, order_status, bonus_set_ratio, handi_cap, bet_ratio, is_dt)  ");
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
					sb.append("'" + (""
							+ ((BigDecimal) mainOrderList.get(i).get("amount")).setScale(2, BigDecimal.ROUND_DOWN))
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
					sb.append("'" + (""
							+ ((BigDecimal) mainOrderList.get(i).get("bet_ratio")).setScale(2, BigDecimal.ROUND_DOWN))
									.replaceAll("'", "\\'")
							+ "',");
					sb.append("" + ("" + mainOrderList.get(i).get("is_dt")).replaceAll("'", "\\'") + "");

					if ((i + 1) % 1000 == 0) {
						sb.append(" NOLOGGING; \n");
						stat = this.WRITE_CONN.createStatement();
						String sql = sb.toString();
						int res = stat.executeUpdate(sql);
						if (res != leaveCount) {
							return false;
						}
						leaveCount = 0;
						sb = new StringBuilder();
						sb.append("INSERT INTO ctt_manager.main_order_" + mainOrderList.get(0).get("dateOfTable")
								+ " (main_order_id, acc_id, start_period_num, stop_period_num, bet_data, no_of_period, game_id, played_id, action_time, amount, money_unit, order_type, order_status, bonus_set_ratio, handi_cap, bet_ratio, is_dt)  ");

					} else if (i == mainOrderList.size() - 1) {
						sb.append(" NOLOGGING; \n");
					} else {
						sb.append(" union ");
					}
				}
				if (leaveCount != 0) {
					stat = this.WRITE_CONN.createStatement();
					String sql = sb.toString();
					int res = stat.executeUpdate(sql);
					if (res == leaveCount) {
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.error("insertMidOrder_Exception===" + e);
			return false;
		} finally {
		}
	}

	@Override
	public boolean insertMidOrder(List<Map<String, Object>> midOrderList) {
		Map<String, Object> insertMap = new HashMap<String, Object>();
		StringBuilder sb = null;
		Statement stat = null;
		try {
			sb = new StringBuilder();
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("no connection");
				return false;
			} else {
				if (this.WRITE_CONN.getAutoCommit()) {
					this.WRITE_CONN.setAutoCommit(false);
				}
				int leaveCount = 0;
				sb.append("INSERT INTO ctt_manager.mid_order_" + midOrderList.get(0).get("dateOfTable")
						+ " (main_order_id, mid_order_id, acc_id, period_num, game_id, played_id, amount, no_of_bet, max_bonus, fan_den, no_of_bet_times, order_status) ");
				for (int i = 0; i < midOrderList.size(); i++) {
					leaveCount++;
					sb.append(" select ");
					sb.append("'" + ("" + midOrderList.get(i).get("main_order_id")).replaceAll("'", "\\'") + "',");
					sb.append("'" + ("" + midOrderList.get(i).get("mid_order_id")).replaceAll("'", "\\'") + "',");
					sb.append("'" + ("" + midOrderList.get(i).get("acc_id")).replaceAll("'", "\\'") + "',");
					sb.append("'" + ("" + midOrderList.get(i).get("period_num")).replaceAll("'", "\\'") + "',");
					sb.append("'" + ("" + midOrderList.get(i).get("game_id")).replaceAll("'", "\\'") + "',");
					sb.append("'" + ("" + midOrderList.get(i).get("played_id")).replaceAll("'", "\\'") + "',");
					sb.append("'"
							+ ("" + ((BigDecimal) midOrderList.get(i).get("amount")).setScale(2, BigDecimal.ROUND_DOWN))
									.replaceAll("'", "\\'")
							+ "',");
					sb.append("'" + ("" + midOrderList.get(i).get("no_of_bet")).replaceAll("'", "\\'") + "',");
					sb.append("'" + (""
							+ ((BigDecimal) midOrderList.get(i).get("max_bonus")).setScale(2, BigDecimal.ROUND_DOWN))
									.replaceAll("'", "\\'")
							+ "',");
					sb.append("'" + (""
							+ ((BigDecimal) midOrderList.get(i).get("fan_den")).setScale(2, BigDecimal.ROUND_DOWN))
									.replaceAll("'", "\\'")
							+ "',");
					sb.append("'" + ("" + midOrderList.get(i).get("no_of_bet_times")).replaceAll("'", "\\'") + "',");
					sb.append("'" + ("" + midOrderList.get(i).get("order_status")).replaceAll("'", "\\'") + "'");
					if ((i + 1) % 1000 == 0) {
						sb.append(" NOLOGGING");
						stat = this.WRITE_CONN.createStatement();
						String sql = sb.toString();
						int res = stat.executeUpdate(sql);
						if (res != leaveCount) {
							return false;
						}
						leaveCount = 0;
						sb = new StringBuilder();
						sb.append("INSERT INTO ctt_manager.mid_order_" + midOrderList.get(0).get("dateOfTable")
								+ " (main_order_id, mid_order_id, acc_id, period_num, game_id, played_id, amount, no_of_bet, max_bonus, fan_den, no_of_bet_times, order_status)  ");

					} else if (i == midOrderList.size() - 1) {
						sb.append(" NOLOGGING");
					} else {
						sb.append(" union");
					}
				}
				if (leaveCount != 0) {
					stat = this.WRITE_CONN.createStatement();
					String sql = sb.toString();
					int res = stat.executeUpdate(sql);
					if (res == leaveCount) {
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}
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
	public boolean insertBetOrder(List<Map<String, Object>> subOrderList) {
		LOG.debug("insertBetOrder");
		StringBuilder sb = null;
		Statement stat = null;
		try {
			sb = new StringBuilder();
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				if (this.WRITE_CONN.getAutoCommit()) {
					this.WRITE_CONN.setAutoCommit(false);
				}
				int leaveCount = 0;
				sb.append("INSERT INTO ctt_manager.bet_order_" + subOrderList.get(0).get("dateOfTable")
						+ " (main_order_id, mid_order_id, sub_order_id, acc_id, period_num, bet_data, game_id, played_id, amount, no_of_bet, order_status, bonus, sub_action, baseline)  ");
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
					sb.append("'"
							+ ("" + ((BigDecimal) subOrderList.get(i).get("amount")).setScale(2, BigDecimal.ROUND_DOWN))
									.replaceAll("'", "\\'")
							+ "',");
					sb.append("'" + ("" + subOrderList.get(i).get("no_of_bet")).replaceAll("'", "\\'") + "',");
					sb.append("'" + ("" + subOrderList.get(i).get("order_status")).replaceAll("'", "\\'") + "',");
					sb.append("'"
							+ ("" + ((BigDecimal) subOrderList.get(i).get("bonus")).setScale(2, BigDecimal.ROUND_DOWN))
									.replaceAll("'", "\\'")
							+ "',");
					sb.append("'" + ("" + subOrderList.get(i).get("sub_action")).replaceAll("'", "\\'") + "',");
					sb.append("'" + (""
							+ ((BigDecimal) subOrderList.get(i).get("baseline")).setScale(7, BigDecimal.ROUND_DOWN))
									.replaceAll("'", "\\'")
							+ "'");
					if ((i + 1) % 5000 == 0) {
						sb.append(" NOLOGGING ");
						stat = this.WRITE_CONN.createStatement();
						String sql = sb.toString();
						int res = stat.executeUpdate(sql);
						if (res != leaveCount) {
							return false;
						}
						leaveCount = 0;
						sb = new StringBuilder();
						sb.append("INSERT INTO ctt_manager.bet_order_" + subOrderList.get(0).get("dateOfTable")
								+ " (main_order_id, mid_order_id, sub_order_id, acc_id, period_num, bet_data, game_id, played_id, amount, no_of_bet, order_status, bonus, sub_action, baseline)  ");
					} else if (i == subOrderList.size() - 1) {
						sb.append(" NOLOGGING ");
					} else {
						sb.append(" union ");
					}
				}
				if (leaveCount != 0) {
					stat = this.WRITE_CONN.createStatement();
					String sql = sb.toString();
					int res = stat.executeUpdate(sql);
					if (res == leaveCount) {
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}
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
}
