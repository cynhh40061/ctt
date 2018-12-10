package tw.com.ctt.dao.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
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
import tw.com.ctt.util.DataSource;
import tw.com.ctt.util.MyUtil;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class MemberBetDaoImpl extends BaseDao implements IMemberBetDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5393598519122647798L;
	private static final Logger LOG = LogManager.getLogger(MemberBetDaoImpl.class.getName());

	public void writeCommit() {
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("no connection");
			} else {
				if (!this.WRITE_CONN.getAutoCommit()) {
					this.WRITE_CONN.commit();
					this.WRITE_CONN.setAutoCommit(true);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeRollback() {
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("no connection");
			} else {
				this.WRITE_CONN.rollback();
				this.WRITE_CONN.setAutoCommit(true);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean cancleAllPeriodOrder(long accId, long periodNum, int localId) {
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("no connection");
				return false;
			} else {
				this.WRITE_CONN.setAutoCommit(false);
				List<Object> selectObj = new ArrayList<Object>();
				String sql = "CALL `ctt_manager`.`cancle_bet_by_period`(? ,?)";
				selectObj.add(periodNum);
				selectObj.add(localId);
				StmtUtil.callStored(this.WRITE_CONN, sql, selectObj);
				selectObj.clear();
				this.WRITE_CONN.setAutoCommit(true);
				return true;
			}
		} catch (Exception e) {
			LOG.info("cancleAllPeriodOrder_Exception===" + e);
			return false;
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
	}

	@Override
	public boolean setBettingStatistical(List<Map<String, Object>> mainOrderList) {
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("no connection");
				return false;
			} else {
				this.WRITE_CONN.setAutoCommit(false);

				for (int i = 0; i < mainOrderList.size(); i++) {
					List<Object> selectObj = new ArrayList<Object>();
					String sql = "call `ctt_manager`.`set_lottery_statistical_report_betting`(? ,?, ?, ?,1);";
					selectObj.add(mainOrderList.get(i).get("acc_id"));
					selectObj.add(mainOrderList.get(i).get("start_period_num"));
					selectObj.add(mainOrderList.get(i).get("game_id"));
					selectObj.add(mainOrderList.get(i).get("amount"));
					StmtUtil.callStored(this.WRITE_CONN, sql, selectObj);
					selectObj.clear();
				}
				this.WRITE_CONN.setAutoCommit(true);
				return true;
			}
		} catch (Exception e) {
			LOG.info("setBettingStatistical===" + e);
			return false;
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
	}

	@Override
	public boolean cancleMainOrder(long accId, long periodNum, int localId, long mainOrderId) {
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("no connection");
				return false;
			} else {
				this.WRITE_CONN.setAutoCommit(false);
				List<Object> selectObj = new ArrayList<Object>();
				String sql = "CALL `ctt_manager`.`cancle_bet_by_main_order`(? ,? ,?,'3')";
				selectObj.add(periodNum);
				selectObj.add(localId);
				selectObj.add(mainOrderId);
				StmtUtil.callStored(this.WRITE_CONN, sql, selectObj);
				selectObj.clear();
				this.WRITE_CONN.setAutoCommit(true);
				return true;
			}
		} catch (Exception e) {
			LOG.info("cancleMainOrder_Exception===" + e);
			return false;
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
	}

	@Override
	public long getNextAutoIncrementId(String tableName, String dbName) {
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
				sql.append("SELECT ctt_manager.nextval('msgSeq') as t1;");
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
			LOG.error("getNextAutoIncrementId()_Exception===" + e.getMessage());
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

	public static int callStoredForGetAutoIncrement(Connection conn, String sql, List<Object> params)
			throws SQLException {
		boolean toClose = false;
		if (conn == null) {
			toClose = true;
		}
		CallableStatement cs = null;
		int newAi;
		try {
			if (conn == null || conn.isClosed()) {
				conn = null;
				conn = DataSource.getWriteConnection();
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
			List<Integer> tmpList = new ArrayList<Integer>();
			LOG.debug("\ncall stored procedure SQL:" + sql);
			if (params != null) {
				LOG.debug("\nparams:" + params.toString());
			}
			cs = conn.prepareCall(sql);
			if (params != null && !params.isEmpty() && params.size() > 0) {
				for (int i = 0; i < params.size(); i++) {
					if (params.get(i).getClass().getName().startsWith("java.sql.Types.")) {
						cs.registerOutParameter(i + 1, (int) params.get(i));
						tmpList.add(i);
					} else {
						if (params.get(i) == null) {
							cs.setString(i + 1, null);
						} else if (params.get(i).getClass() == String.class) {
							cs.setString(i + 1, params.get(i).toString());
						} else if (params.get(i).getClass() == Short.class) {
							cs.setShort(i + 1, (short) params.get(i));
						} else if (params.get(i).getClass() == Integer.class) {
							cs.setInt(i + 1, Integer.parseInt(params.get(i).toString()));
						} else if (params.get(i).getClass() == Long.class) {
							cs.setLong(i + 1, (Long) params.get(i));
						} else if (params.get(i).getClass() == Float.class) {
							cs.setFloat(i + 1, (Float) params.get(i));
						} else if (params.get(i).getClass() == Double.class) {
							cs.setDouble(i + 1, (Double) params.get(i));
						} else if (params.get(i).getClass() == Boolean.class) {
							cs.setBoolean(i + 1, (Boolean) params.get(i));
						} else if (params.get(i).getClass() == Byte.class) {
							cs.setByte(i + 1, (Byte) params.get(i));
						} else if (params.get(i).getClass() == BigDecimal.class) {
							cs.setBigDecimal(i + 1, (BigDecimal) params.get(i));
						} else if (params.get(i).getClass() == Timestamp.class) {
							cs.setTimestamp(i + 1, (Timestamp) params.get(i));
						} else if (params.get(i).getClass() == java.sql.Date.class) {
							cs.setDate(i + 1, (java.sql.Date) params.get(i));
						} else if (params.get(i).getClass() == java.util.Date.class) {
							cs.setTimestamp(i + 1, new Timestamp(((java.util.Date) params.get(i)).getTime()));
						} else {
							cs.setString(i + 1, params.get(i).toString());
						}
					}
				}
			} else {

			}
			cs.executeUpdate();
			newAi = cs.getInt(3);
			conn.commit();
		} catch (Exception e) {
			LOG.info("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LOG.info("SQLException:" + e1.getMessage());
				ShowLog.err(LOG, e1);
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					if (!conn.getAutoCommit()) {
						conn.setAutoCommit(true);
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				}
			}
			if (cs != null) {
				try {
					if (!cs.isClosed()) {
						cs.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				} finally {
					cs = null;
				}
			}
			if (params != null) {
				params.clear();
			}
			params = null;
			sql = "";
			sql = null;
			if (toClose) {
				DataSource.returnWriteConnection(conn);
			}
			System.gc();
		}
		return newAi;
	}

	@Override
	public boolean cancleMainOrderWhenCreateOrderFail(Map<String, Object> mainOrderList) {
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("no connection");
				return false;
			} else {
				System.out.println("CANCLE ORDER "+mainOrderList.get("main_order_id"));
				
//				this.WRITE_CONN.setAutoCommit(false);
				List<Object> selectObj = new ArrayList<Object>();
				String sql = "CALL `ctt_manager`.`cancle_bet_by_main_order_for_betting_fail`(? ,? ,?,'5')";
				selectObj.add(mainOrderList.get("acc_id"));
				selectObj.add(mainOrderList.get("game_id"));
				selectObj.add(mainOrderList.get("main_order_id"));
				StmtUtil.callStored(this.WRITE_CONN, sql, selectObj);
				selectObj.clear();
//				this.WRITE_CONN.setAutoCommit(true);
				return true;
			}
		} catch (Exception e) {
			LOG.info("cancleMainOrder_Exception===" + e);
			return false;
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
	}

	@Override
	public boolean cancleMidOrder(long accId, long periodNum, int localId, long mainOrderId) {
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("no connection");
				return false;
			} else {
				this.WRITE_CONN.setAutoCommit(false);
				List<Object> selectObj = new ArrayList<Object>();
				String sql = "CALL `ctt_manager`.`cancle_bet_by_mid_order`(? ,? ,?)";
				selectObj.add(periodNum);
				selectObj.add(localId);
				selectObj.add(mainOrderId);
				StmtUtil.callStored(this.WRITE_CONN, sql, selectObj);
				selectObj.clear();
				this.WRITE_CONN.setAutoCommit(true);
				return true;
			}
		} catch (Exception e) {
			LOG.info("cancleMidOrder_Exception===" + e);
			return false;
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
	}

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

				// sb.append("INSERT INTO ctt_manager.main_order_" +
				// mainOrderList.get(0).get("dateOfTable")
				// + " (main_order_id, acc_id, start_period_num, stop_period_num, bet_data,
				// no_of_period, game_id, played_id, action_time, amount, money_unit,
				// order_type, order_status, bonus_set_ratio, handi_cap, bet_ratio, is_dt)
				// VALUES");
				for (int i = 0; i < mainOrderList.size(); i++) {
					leaveCount++;
					// sb.append("(");
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
						// sb.append(") ");
						stat = this.WRITE_CONN.createStatement();
						String sql = sb.toString();
						int res = stat.executeUpdate(sql);
						if (res != leaveCount) {
							return false;
						}

						leaveCount = 0;
						sb = new StringBuilder();
						// sb.append("INSERT INTO ctt_manager.main_order_" +
						// mainOrderList.get(0).get("dateOfTable")
						// + " (main_order_id, acc_id, start_period_num, stop_period_num, bet_data,
						// no_of_period, game_id, played_id, action_time, amount, money_unit,
						// order_type, order_status, bonus_set_ratio, handi_cap, bet_ratio, is_dt)
						// VALUES");

						sb.append("INSERT INTO ctt_manager.main_order_" + mainOrderList.get(0).get("dateOfTable")
								+ " (main_order_id, acc_id, start_period_num, stop_period_num, bet_data, no_of_period, game_id, played_id, action_time, amount, money_unit, order_type, order_status, bonus_set_ratio, handi_cap, bet_ratio, is_dt)  ");

					} else if (i == mainOrderList.size() - 1) {
						// sb.append(")");
						sb.append(" NOLOGGING; \n");
					} else {
						sb.append(" union ");
						// sb.append("),");
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

					// String sql = sb.toString();
					// String fileName = "c:\\work\\fN_main" + new Date().getTime() + (int)
					// (Math.random() * 1000)
					// + (int) (Math.random() * 10);
					// FileWriter fw = new FileWriter(fileName);
					// fw.write(sql);
					// fw.close();
					// String[] tmpAry = { "cmd", "/C",
					// "c:\\work\\tool\\mysql.exe -uCTT_AP -p1qazxsw2 -h192.168.1.14 ctt_manager < "
					// + fileName
					// + " && del " + fileName };
					// Process proc = Runtime.getRuntime().exec(tmpAry);
					// return true;
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
	public void updateUserBalanceOfMainOrderBetting(List<Map<String, Object>> mainOrderList) {
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("no connection");
			} else {
				this.WRITE_CONN.setAutoCommit(true);
				for (int i = 0; i < mainOrderList.size(); i++) {
					List<Object> selectObj = new ArrayList<Object>();
					selectObj = new ArrayList<Object>();

					String sql = "update `ctt_manager`.`ctt_member_acc` set balance = balance - ?  where acc_id = ? and balance >= ? and (select count(1) from `ctt_manager`.`main_order_"
							+ mainOrderList.get(i).get("dateOfTable")
							+ "` where main_order_id = ? ) = 1 and (select count(1) from `ctt_manager`.`mid_order_"
							+ mainOrderList.get(i).get("dateOfTable")
							+ "` where main_order_id = ?) = ? and (select count(1)  from `ctt_manager`.`bet_order_"
							+ mainOrderList.get(i).get("dateOfTable") + "` where main_order_id = ?) = ?";
					selectObj.add(mainOrderList.get(i).get("amount"));
					selectObj.add(mainOrderList.get(i).get("acc_id"));
					selectObj.add(mainOrderList.get(i).get("amount"));
					selectObj.add(mainOrderList.get(i).get("main_order_id"));
					selectObj.add(mainOrderList.get(i).get("main_order_id"));
					selectObj.add(mainOrderList.get(i).get("countOfMidOrders"));
					selectObj.add(mainOrderList.get(i).get("main_order_id"));
					selectObj.add(mainOrderList.get(i).get("countOfBetOrders"));

					int result = StmtUtil.update(this.WRITE_CONN, sql, selectObj);

					if (result == 0) {
						cancleMainOrderWhenCreateOrderFail(mainOrderList.get(i));
					}else {
						selectObj = null;
						selectObj = new ArrayList<Object>();
						sql = "INSERT INTO `ctt_manager`.`ctt_lottery_member_money_transfer_record`(acc_id,acc_name, balance, start_period_num, stop_period_num,local_id,played_id,record_order_type,money, period_total_count)(SELECT acc_id, acc_name, balance, ?, ? ,?, ?, ?, ?, ? from `ctt_manager`.`ctt_member_acc` where acc_id=?)";
						selectObj.add(mainOrderList.get(i).get("start_period_num"));
						selectObj.add(mainOrderList.get(i).get("stop_period_num"));
						selectObj.add(mainOrderList.get(i).get("game_id"));
						selectObj.add(mainOrderList.get(i).get("played_id"));
						if (mainOrderList.get(i).get("start_period_num") != mainOrderList.get(i).get("stop_period_num")) {
							selectObj.add("2"); // 追號扣款
						} else {
							selectObj.add("1"); // 投注扣款
						}
						selectObj.add(mainOrderList.get(i).get("amount"));
						selectObj.add(mainOrderList.get(i).get("no_of_period"));
						selectObj.add(mainOrderList.get(i).get("acc_id"));
						StmtUtil.insertNoCommit(this.WRITE_CONN, sql, selectObj);
					}
				}
//				this.WRITE_CONN.commit();
//				this.WRITE_CONN.setAutoCommit(true);
			}
		} catch (Exception e) {
			LOG.info("insertMainOrder_Exception===" + e);
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
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
				// sb.append("INSERT INTO ctt_manager.mid_order_" +
				// midOrderList.get(0).get("dateOfTable")
				// + " (main_order_id, mid_order_id, acc_id, period_num, game_id, played_id,
				// amount, no_of_bet, max_bonus, fan_den, no_of_bet_times, order_status)
				// VALUES");
				sb.append("INSERT INTO ctt_manager.mid_order_" + midOrderList.get(0).get("dateOfTable")
						+ " (main_order_id, mid_order_id, acc_id, period_num, game_id, played_id, amount, no_of_bet, max_bonus, fan_den, no_of_bet_times, order_status) ");
				for (int i = 0; i < midOrderList.size(); i++) {
					leaveCount++;
					// sb.append("(");
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
						// sb.append(")");
						sb.append(" NOLOGGING");
						stat = this.WRITE_CONN.createStatement();
						String sql = sb.toString();
						int res = stat.executeUpdate(sql);
						if (res != leaveCount) {
							return false;
						}

						// String fileName = "c:\\work\\fN_mid" + new Date().getTime() + (int)
						// (Math.random() * 1000)
						// + (int) (Math.random() * 10);
						// FileWriter fw = new FileWriter(fileName);
						// fw.write(sql);
						// fw.close();
						// String[] tmpAry = { "cmd", "/C",
						// "c:\\work\\tool\\mysql.exe -uCTT_AP -p1qazxsw2 -h192.168.1.14 ctt_manager < "
						// + fileName
						// + " && del " + fileName };
						// Process proc = Runtime.getRuntime().exec(tmpAry);

						leaveCount = 0;
						sb = new StringBuilder();
						// sb.append("INSERT INTO ctt_manager.mid_order_" +
						// midOrderList.get(0).get("dateOfTable")
						// + " (main_order_id, mid_order_id, acc_id, period_num, game_id, played_id,
						// amount, no_of_bet, max_bonus, fan_den, no_of_bet_times, order_status)
						// VALUES");

						sb.append("INSERT INTO ctt_manager.mid_order_" + midOrderList.get(0).get("dateOfTable")
								+ " (main_order_id, mid_order_id, acc_id, period_num, game_id, played_id, amount, no_of_bet, max_bonus, fan_den, no_of_bet_times, order_status)  ");

					} else if (i == midOrderList.size() - 1) {
						// sb.append(")");
						sb.append(" NOLOGGING");
					} else {
						// sb.append("),");
						sb.append(" union");
					}
				}
				if (leaveCount != 0) {
					// String sql = sb.toString();
					// String fileName = "c:\\work\\fN_mid" + new Date().getTime() + (int)
					// (Math.random() * 1000)
					// + (int) (Math.random() * 10);
					// FileWriter fw = new FileWriter(fileName);
					// fw.write(sql);
					// fw.close();
					// String[] tmpAry = { "cmd", "/C",
					// "c:\\work\\tool\\mysql.exe -uCTT_AP -p1qazxsw2 -h192.168.1.14 ctt_manager < "
					// + fileName
					// + " && del " + fileName };
					// Process proc = Runtime.getRuntime().exec(tmpAry);
					// return true;

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
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				if (this.WRITE_CONN.getAutoCommit()) {
					this.WRITE_CONN.setAutoCommit(false);
				}
				int leaveCount = 0;
				// sb.append("INSERT INTO ctt_manager.bet_order_" +
				// subOrderList.get(0).get("dateOfTable")
				// + " (main_order_id, mid_order_id, sub_order_id, acc_id, period_num, bet_data,
				// game_id, played_id, amount, no_of_bet, order_status, bonus, sub_action,
				// baseline) VALUES");

				sb.append("INSERT INTO ctt_manager.bet_order_" + subOrderList.get(0).get("dateOfTable")
						+ " (main_order_id, mid_order_id, sub_order_id, acc_id, period_num, bet_data, game_id, played_id, amount, no_of_bet, order_status, bonus, sub_action, baseline)  ");

				for (int i = 0; i < subOrderList.size(); i++) {
					leaveCount++;
					// sb.append("(");
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
						// sb.append(")");
						sb.append(" NOLOGGING ");
						stat = this.WRITE_CONN.createStatement();
						String sql = sb.toString();
						int res = stat.executeUpdate(sql);
						if (res != leaveCount) {
							return false;
						}

						// String sql = sb.toString();
						// String fileName = "c:\\work\\fN_bet" + new Date().getTime() + (int)
						// (Math.random() * 1000)
						// + (int) (Math.random() * 10);
						// FileWriter fw = new FileWriter(fileName);
						// fw.write(sql);
						// fw.close();
						//
						// String[] tmpAry = { "cmd", "/C",
						// "c:\\work\\tool\\mysql.exe -uCTT_AP -p1qazxsw2 -h192.168.1.14 ctt_manager < "
						// + fileName
						// + " && del " + fileName };
						// Process proc = Runtime.getRuntime().exec(tmpAry);

						leaveCount = 0;
						sb = new StringBuilder();
						sb.append("INSERT INTO ctt_manager.bet_order_" + subOrderList.get(0).get("dateOfTable")
								+ " (main_order_id, mid_order_id, sub_order_id, acc_id, period_num, bet_data, game_id, played_id, amount, no_of_bet, order_status, bonus, sub_action, baseline)  ");

						// sb.append("INSERT INTO ctt_manager.bet_order_" +
						// subOrderList.get(0).get("dateOfTable")
						// + " (main_order_id, mid_order_id, sub_order_id, acc_id, period_num, bet_data,
						// game_id, played_id, amount, no_of_bet, order_status, bonus, sub_action,
						// baseline) VALUES");

					} else if (i == subOrderList.size() - 1) {
						// sb.append(")");
						sb.append(" NOLOGGING ");
					} else {
						// sb.append("),");
						sb.append(" union ");
					}
				}
				if (leaveCount != 0) {

					// String sql = sb.toString();
					// String fileName = "c:\\work\\fN_bet" + new Date().getTime() + (int)
					// (Math.random() * 1000)
					// + (int) (Math.random() * 10);
					// FileWriter fw = new FileWriter(fileName);
					// fw.write(sql);
					// fw.close();
					// String[] tmpAry = { "cmd", "/C",
					// "c:\\work\\tool\\mysql.exe -uCTT_AP -p1qazxsw2 -h192.168.1.14 ctt_manager < "
					// + fileName
					// + " && del " + fileName };
					// Process proc = Runtime.getRuntime().exec(tmpAry);
					//
					// return true;

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

	public boolean checkNumOfOrders(List<Map<String, Object>> mainOrderList) {
		StringBuilder sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return false;
			} else {
				long t1 = 0;
				sql = new StringBuilder();
				sql.append(
						"SELECT UNIX_TIMESTAMP(ratio_update_time) AS t1 FROM `ctt_manager`.`ctt_lottery_current_ratio` ORDER BY ratio_update_time DESC");
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
				CommandConstant.CHECK_UPDATE_BASELINE_TIME = new Date().getTime();
				if (CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE < t1) {
					CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE = t1;
					return true;
				}
				return false;
			}
		} catch (Exception e) {
			LOG.error("checkCurrentBetRatio()_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
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
	public Map<String, Object> getMemMoney(long accId) {
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
				sb.append("acc_id,acc_name, balance \n");
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
	public boolean checkRealTimeBaselinePeriodAndHandiCap(long accId, long periodNum, long minAuthId, int localId,
			String dateOfTable, int handiCap, Map<String, Map<String, String>> tmpRatios) {
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
				sb.append(" 	) 	\n");
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
					String resultA = rs.getString("is_order_can_be_delete") != null
							? rs.getString("is_order_can_be_delete")
							: "0";
					String baseline = rs.getString("baseline") != null ? rs.getString("baseline") : "0";
					String resultC = rs.getString("handicap") != null ? rs.getString("handicap") : "0";
					String dtSwitch = rs.getString("dt_switch") != null ? rs.getString("dt_switch") : "0";
					String dtRatio = rs.getString("dt_ratio") != null ? rs.getString("dt_ratio") : "0";
					String dtBonus = rs.getString("dt_bonus") != null ? rs.getString("dt_bonus") : "0";
					String prizeLevel = rs.getString("prize_level") != null ? rs.getString("prize_level") : "0";
					if ("0".equals(resultA)
							|| (Integer.parseInt(resultC) & (1 << (handiCap > 0 ? handiCap - 1 : 0))) == 0) {
						return false;
					}
					try {
						if (new BigDecimal(tmpRatios.get(prizeLevel).get("baseline"))
								.compareTo(new BigDecimal(baseline)) != 0
								|| new BigDecimal(tmpRatios.get(prizeLevel).get("dt_ratio"))
										.compareTo(new BigDecimal(dtRatio)) != 0
								|| new BigDecimal(tmpRatios.get(prizeLevel).get("dt_bonus"))
										.compareTo(new BigDecimal(dtBonus)) != 0
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

	public void sentURL(String url, String data, String accId, String tokenId) {
		if (url != null && !url.isEmpty() && !"".equals(url.trim())) {
			try {
				HttpURLConnection conn = (HttpURLConnection) (new URL(url).openConnection());
				conn.setRequestMethod("POST");
				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("User-Agent", "Mozilla/5.0");
				conn.setRequestProperty("Accept-Language", "utf-8");
				conn.setDoOutput(true);
				conn.setConnectTimeout(2000);
				conn.setReadTimeout(1500);
				conn.connect();
				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				wr.writeBytes("data=" + java.net.URLEncoder.encode(data, "UTF-8") + "&accId=" + accId + "&tokenId="
						+ java.net.URLEncoder.encode(tokenId, "UTF-8"));
				wr.flush();
				wr.close();
				wr = null;
				// int resCode = conn.getResponseCode();
				// LOG.info("Response Code:" + resCode);
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				// String inputLine;
				// StringBuilder sb = new StringBuilder();
				// while ((inputLine = in.readLine()) != null) {
				// sb.append(inputLine);
				// }
				in.close();
				in = null;
				conn.disconnect();
				conn = null;
				// resCode = 0;
				// inputLine = "";
				// inputLine = null;
				// LOG.info("Response Content:\n" + sb.toString());
				// if (sb != null) {
				// sb.setLength(0);
				// sb = null;
				// }
			} catch (java.net.SocketTimeoutException e) {
				// e.printStackTrace();
			} catch (MalformedURLException e) {
				// e.printStackTrace();
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
	}

	public boolean checkCurrentBetRatio() {
		StringBuilder sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return false;
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
				CommandConstant.CHECK_UPDATE_BASELINE_TIME = new Date().getTime();
				if (CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE < t1) {
					CommandConstant.DB_LAST_UPDATE_TIME_OF_BASELINE = t1;
					return true;
				}
				return false;
			}
		} catch (Exception e) {
			LOG.error("checkCurrentBetRatio()_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
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
	public boolean updateBetRatio_ExportFile(List<Map<String, Object>> updateOrderList, FileWriter fw ) {
		return true;
	}

	@Override
	public int updateBetRatio(BigDecimal amount, int local_id, int lottery_type_id, int lottery_mid_id,
			long lottery_min_id, long period_num, String dateOfTable) {
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.error("no connection");
				return 0;
			} else {
				sb = new StringBuilder();
				sb.append(" UPDATE `ctt_manager`.`ctt_lottery_current_ratio`  \n");
				sb.append(" SET  \n");
				sb.append(
						" `ratio_update_time`=  case when ?+amount < baseline_threshold1 then case when ratio_index <> 0 then CURRENT_TIMESTAMP else ratio_update_time end else case when ?+amount < baseline_threshold2 then case when ratio_index <> 1 then CURRENT_TIMESTAMP else ratio_update_time end else case when ratio_index <> 2 then CURRENT_TIMESTAMP else ratio_update_time end end end, \n");
				sb.append(
						" `ratio_index`= case when ?+amount < baseline_threshold1 then 0 else case when ?+amount < baseline_threshold2 then 1 else 2 end end , \n");
				sb.append(" `amount` = amount +?  \n");
				sb.append(" WHERE \n");
				sb.append(
						" local_id = ? AND lottery_type_id = ? AND lottery_mid_id = ? AND lottery_min_id = ? AND date = '"
								+ dateOfTable + "'; \n");
				ps = this.WRITE_CONN.prepareStatement(sb.toString());
				ps.setBigDecimal(1, amount);
				ps.setBigDecimal(2, amount);
				ps.setBigDecimal(3, amount);
				ps.setBigDecimal(4, amount);
				ps.setBigDecimal(5, amount);
				ps.setInt(6, local_id);
				ps.setInt(7, lottery_type_id);
				ps.setInt(8, lottery_mid_id);
				ps.setLong(9, lottery_min_id);
				ps.execute();
				return 1;
			}
		} catch (Exception e) {
			LOG.error("test()_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			return 0;
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
	public int updateBetRatioLF(BigDecimal amount, int local_id, int lottery_type_id, int lottery_mid_id,
			long lottery_min_id, long period_num, String dateOfTable, int noOfPeriod) {
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.error("no connection");
				return 0;
			} else {
				sb = new StringBuilder();
				sb.append(" UPDATE `ctt_manager`.`ctt_lottery_lowfreq_current_ratio`  \n");
				sb.append(" SET  \n");
				sb.append(" `total_no_of_bet` = total_no_of_bet +? ,\n");
				sb.append(" `total_bet_amount` = total_bet_amount +?   \n");
				sb.append(" WHERE \n");
				sb.append(" lottery_local_id = ? AND lottery_type_id = ? AND lottery_mid_id = ? AND lottery_min_id = ? AND date = '"
								+ dateOfTable + "'; \n");
				ps = this.WRITE_CONN.prepareStatement(sb.toString());
				ps.setInt(1, noOfPeriod);
				ps.setBigDecimal(2, amount);
				ps.setInt(3, local_id);
				ps.setInt(4, lottery_type_id);
				ps.setInt(5, lottery_mid_id);
				ps.setLong(6, lottery_min_id);
				ps.execute();
				return 1;
			}
		} catch (Exception e) {
			LOG.error("test()_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			return 0;
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
	public boolean insertMainOrder_ExportFile(List<Map<String, Object>> mainOrderList, FileWriter fw) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertMidOrder_ExportFile(List<Map<String, Object>> midOrderList, FileWriter fw) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertBetOrder_ExportFile(List<Map<String, Object>> subOrderList, FileWriter fw) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateUserBalanceOfMainOrderBetting_ExportFile(List<Map<String, Object>> mainOrderList, FileWriter fw) {
		// TODO Auto-generated method stub
		
	}
}
