package tw.com.ctt.dao.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.IMemberBetDao;
import tw.com.ctt.model.MemBean;
import tw.com.ctt.model.PunchGameRecordsBean;
import tw.com.ctt.util.CalculateMemberRatio;
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
				this.WRITE_CONN.commit();
				this.WRITE_CONN.setAutoCommit(true);
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
	public long insertMainOrder(long accId, long startPeriodNum, long stopPeriodNum, String betData, long noOfPeriod, int localId, long minAuthId,
			Date actionTime, BigDecimal amount, BigDecimal moneyUnit, int orderType, int handiCap, int bonusSetRatio, int noOfWinningPeriod,
			int orderStatus, BigDecimal betRatio, boolean isDt, String dateOfTable, long totalNoOfBet) {
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		long orderId = 0;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("no connection");
				return 0;
			} else {
				this.WRITE_CONN.setAutoCommit(false);
				insertMap.put("acc_id", accId);
				insertMap.put("start_period_num", startPeriodNum);
				insertMap.put("stop_period_num", stopPeriodNum);
				insertMap.put("bet_data", betData);
				insertMap.put("no_of_period", noOfPeriod);
				insertMap.put("game_id", localId);
				insertMap.put("played_id", minAuthId);
				insertMap.put("action_time", actionTime);
				insertMap.put("amount", amount);
				insertMap.put("money_unit", moneyUnit);
				insertMap.put("order_type", orderType);
				insertMap.put("order_status", orderStatus);
				insertMap.put("bonus_set_ratio", bonusSetRatio);
				insertMap.put("handi_cap", handiCap);
				insertMap.put("bet_ratio", betRatio);
				insertMap.put("is_dt", isDt);
				orderId = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.main_order_" + dateOfTable, insertMap);
				if (orderId > 0) {
					List<Object> selectObj = new ArrayList<Object>();
					selectObj = new ArrayList<Object>();
					String sql = "update `ctt_manager`.`ctt_member_acc` set balance = balance - ?  where acc_id = ?";
					selectObj.add(amount);
					selectObj.add(accId);
					StmtUtil.update(this.WRITE_CONN, sql, selectObj);
					selectObj = null;
					selectObj = new ArrayList<Object>();
					sql = "INSERT INTO `ctt_manager`.`ctt_lottery_member_money_transfer_record`(acc_id,acc_name, balance, start_period_num, stop_period_num,local_id,played_id,record_order_type,money)(SELECT acc_id, acc_name, balance, ?, ? ,?, ?, ?, ? from `ctt_manager`.`ctt_member_acc` where acc_id=?)";
					selectObj.add(startPeriodNum);
					selectObj.add(stopPeriodNum);
					selectObj.add(localId);
					selectObj.add(minAuthId);
					if (startPeriodNum != stopPeriodNum) {
						selectObj.add("2"); // 追�???��
					} else {
						selectObj.add("1"); // ?�注??��
					}
					selectObj.add(amount);
					selectObj.add(accId);
					StmtUtil.insertNoCommit(this.WRITE_CONN, sql, selectObj);
					this.WRITE_CONN.commit();
				} else {
					this.WRITE_CONN.rollback();
				}
				this.WRITE_CONN.setAutoCommit(true);
			}
			return orderId;
		} catch (Exception e) {
			orderId = 0;
			LOG.info("insertMainOrder_Exception===" + e);
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return orderId;
	}

	@Override
	public boolean insertMidOrder(long mainId, long midId, long accId, long periodNum, int localId, long minAuthId, BigDecimal amount,
			BigDecimal fanDen, int orderType, long noOfBet, int orderStatus, String dateOfTable, BigDecimal maxBonus, long noOfBetTimes) {
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("no connection");
				return false;
			} else {
				this.WRITE_CONN.setAutoCommit(false);
				insertMap.put("main_order_id", mainId);
				insertMap.put("mid_order_id", midId);
				insertMap.put("acc_id", accId);
				insertMap.put("period_num", periodNum);
				insertMap.put("game_id", localId);
				insertMap.put("played_id", minAuthId);
				insertMap.put("amount", amount);
				insertMap.put("no_of_bet", noOfBet);
				insertMap.put("max_bonus", maxBonus);
				insertMap.put("no_of_bet_times", noOfBetTimes);
				insertMap.put("order_status", orderStatus);
				long count = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.mid_order_" + dateOfTable, insertMap);
				if (count == -1) {
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
		return false;
	}

	@Override
	public boolean insertBetOrder(List<Map<String, Object>> subOrderList, String dateOfTable) {
		LOG.debug("checkMemType");
		StringBuilder sb = null;
		PreparedStatement ps = null;
		try {
			sb = new StringBuilder();
			this.checkRead();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				sb.append("INSERT INTO ctt_manager.bet_order_" + dateOfTable
						+ " (main_order_id, mid_order_id, sub_order_id, acc_id, period_num, bet_data, game_id, played_id, amount, no_of_bet, order_status, bonus, baseline) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				if (this.WRITE_CONN.getAutoCommit()) {
					this.WRITE_CONN.setAutoCommit(false);
				}
				ps = this.WRITE_CONN.prepareStatement(sb.toString());
				int leaveCount = 0;
				for (int i = 0; i < subOrderList.size(); i++) {
					leaveCount++;
					ps.setLong(1, (long) subOrderList.get(i).get("main_order_id"));
					ps.setInt(2, (int) subOrderList.get(i).get("mid_order_id"));
					ps.setInt(3, (int) subOrderList.get(i).get("sub_order_id"));
					ps.setLong(4, (long) subOrderList.get(i).get("acc_id"));
					ps.setLong(5, (long) subOrderList.get(i).get("period_num"));
					ps.setString(6, (String) subOrderList.get(i).get("bet_data"));
					ps.setInt(7, (int) subOrderList.get(i).get("game_id"));
					ps.setLong(8, (long) subOrderList.get(i).get("played_id"));
					ps.setBigDecimal(9, ((BigDecimal) subOrderList.get(i).get("amount")).setScale(2, BigDecimal.ROUND_DOWN));
					ps.setLong(10, (long) subOrderList.get(i).get("no_of_bet"));
					ps.setInt(11, (int) subOrderList.get(i).get("order_status"));
					ps.setBigDecimal(12, ((BigDecimal) subOrderList.get(i).get("bonus")).setScale(2, BigDecimal.ROUND_DOWN));
					ps.setBigDecimal(13, ((BigDecimal) subOrderList.get(i).get("baseline")).setScale(7, BigDecimal.ROUND_DOWN));
					ps.addBatch();
					if ((i + 1) % 500 == 0) {
						leaveCount = 0;
						int[] result2 = ps.executeBatch();
						int cou2 = 0;
						for (int j = 0; j < result2.length; j++) {
							cou2 += result2[j];
						}
						if (result2.length != cou2) {
							return false;
						}
					}
				}
				if (leaveCount != 0) {
					int[] result = ps.executeBatch();
					int cou = 0;
					for (int i = 0; i < result.length; i++) {
						cou += result[i];
					}
					if (result.length == cou) {
						return true;
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
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException, " + e.getMessage());
					ShowLog.err(LOG, e);
				}
			}
		}
		return false;
	}

	private String getMapString(Map<String, Object> map, String key) {
		if (!map.keySet().contains(key) || map.get(key) == null) {
			return "";
		} else {
			return "" + map.get(key);
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
	public Map<String, Object> searchRecordsTotle(long accId, String startTime, String endTime) {
		StringBuilder sb = null;
		List<Object> params = null;
		List<Map<String, Object>> tmpMap = null;
		Map<String, Object> map = null;
		try {
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkRead();

			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return map;
			} else {
				sb.append("SELECT \n");
				sb.append("COUNT(1) AS count \n");
				sb.append(",SUM(A.bet) AS totleBet \n");
				sb.append(",SUM(A.winGoal) AS totleWinGoal \n");
				sb.append(",(SUM(A.bet)+SUM(A.winGoal)) AS totleNetAmount \n");
				sb.append("FROM \n");
				sb.append("( \n");
				sb.append("SELECT \n");
				sb.append("  p1_acc_id AS acc_id \n");
				sb.append("  ,bet \n");
				sb.append("  ,p1_acc_win_goal AS winGoal \n");
				sb.append("FROM \n");
				sb.append("  ctt_manager.ctt_game_punche_records \n");
				sb.append("WHERE \n");
				sb.append("  (start_time > DATE_FORMAT(?,'%Y/%m/%d %T.%f') && start_time < DATE_FORMAT(?,'%Y/%m/%d %T.%f')) \n");
				params.add(startTime);
				params.add(endTime);
				sb.append(" AND p1_acc_id = ? \n");
				params.add(accId);
				sb.append("UNION ALL \n");
				sb.append("SELECT \n");
				sb.append("  p2_acc_id AS acc_id \n");
				sb.append("  ,bet \n");
				sb.append("  ,p2_acc_win_goal AS winGoal \n");
				sb.append("FROM \n");
				sb.append("  ctt_manager.ctt_game_punche_records \n");
				sb.append("WHERE \n");
				sb.append("  (start_time > DATE_FORMAT(?,'%Y/%m/%d %T.%f') && start_time < DATE_FORMAT(?,'%Y/%m/%d %T.%f')) \n");
				params.add(startTime);
				params.add(endTime);
				sb.append(" AND p2_acc_id = ? \n");
				params.add(accId);
				sb.append(")A \n");

				tmpMap = StmtUtil.queryToMap(READ_CONN, sb.toString(), params);
				if (tmpMap.size() == 1 && Integer.parseInt(tmpMap.get(0).get("count").toString()) > 0) {
					map = tmpMap.get(0);
				}
			}
		} catch (Exception e) {
			tmpMap = null;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params.clear();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> searchRecords(long accId, String accName, String startTime, String endTime, int firstCount, int count) {
		StringBuilder sb = null;
		List<Object> params = null;
		List<Object> list = null;
		Map<String, Object> tmpMap = null;
		List<Map<String, Object>> tmpMapList = null;
		try {
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkRead();
			tmpMapList = new ArrayList<Map<String, Object>>();

			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				sb.append("SELECT \n");
				sb.append("  game_id,server_id \n");
				sb.append("  ,p1_acc_id AS acc_id \n");
				sb.append("  ,start_time,end_time \n");
				sb.append("  ,bet,p1_acc_start_balance AS acc_start_balance \n");
				sb.append("  ,p1_acc_win_goal AS acc_win_goal \n");
				sb.append("  ,game_process \n");
				sb.append("  ,game_type, \n");
				sb.append("  CASE WHEN result = 0 THEN TRUE ELSE FALSE END AS result \n");
				sb.append("FROM  \n");
				sb.append("  ctt_manager.ctt_game_punche_records \n");
				sb.append("WHERE  \n");
				sb.append("  (start_time > DATE_FORMAT(?,'%Y/%m/%d %T.%f') && start_time < DATE_FORMAT(?,'%Y/%m/%d %T.%f')) \n");
				params.add(startTime);
				params.add(endTime);
				sb.append(" AND p1_acc_id = ? \n");
				params.add(accId);
				sb.append("UNION \n");
				sb.append("SELECT \n");
				sb.append("  game_id \n");
				sb.append("  ,server_id \n");
				sb.append("  ,p2_acc_id AS acc_id \n");
				sb.append("  ,start_time,end_time,bet \n");
				sb.append("  ,p2_acc_start_balance AS acc_start_balance \n");
				sb.append("  ,p2_acc_win_goal AS acc_win_goal \n");
				sb.append("  ,game_process \n");
				sb.append("  ,game_type, \n");
				sb.append("  CASE WHEN result = 1 THEN TRUE ELSE FALSE END AS result \n");
				sb.append("FROM  \n");
				sb.append("  ctt_manager.ctt_game_punche_records \n");
				sb.append("WHERE  \n");
				sb.append("  (start_time > DATE_FORMAT(?,'%Y/%m/%d %T.%f') && start_time < DATE_FORMAT(?,'%Y/%m/%d %T.%f')) \n");
				params.add(startTime);
				params.add(endTime);
				sb.append(" AND p2_acc_id = ? \n");
				params.add(accId);
				sb.append("ORDER BY end_time DESC \n");
				sb.append("limit ?,? \n");
				params.add(firstCount);
				params.add(count);

				list = new ArrayList<Object>();
				list = StmtUtil.queryToBean(READ_CONN, sb.toString(), params, new PunchGameRecordsBean());
				if (list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						PunchGameRecordsBean bean = (PunchGameRecordsBean) list.get(i);
						JSONObject jsonMap = new JSONObject(bean.getGameProcess());
						String searchKey[] = { "gameName", "accName", "accId", "gameId", "gameType", "startTime", "endTime", "startBalance",
								"endBalance", "bet", "netAmount", "winGoal", "gameTimes", "gameProcess" };
						Object searchByAccNameValue[] = { "三國猜拳王", accName, Long.parseLong("" + bean.getAccId()), "" + bean.getGameId(),
								Integer.parseInt("" + bean.getGameType()), bean.getStartTime(), bean.getEndTime(),
								new BigDecimal("" + bean.getAccStartBalance()),
								new BigDecimal("" + bean.getAccStartBalance()).add(new BigDecimal("" + bean.getAccWinGoal())),
								new BigDecimal("" + bean.getBet()), new BigDecimal("" + bean.getAccWinGoal()),
								new BigDecimal("" + bean.getBet()).add(new BigDecimal("" + bean.getAccWinGoal())), jsonMap.get("gameTimes"),
								jsonMap.get("gameProcess") };
						tmpMap = new ConcurrentHashMap<String, Object>();
						for (int k = 0; k < searchKey.length; k++) {
							tmpMap.put(searchKey[k], searchByAccNameValue[k]);
						}
						tmpMapList.add(tmpMap);
					}
				}
			}
		} catch (Exception e) {
			tmpMapList = null;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params.clear();
				params = null;
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
		return tmpMapList;
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
				conn.setConnectTimeout(20);
				conn.setReadTimeout(15);
				conn.connect();
				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				wr.writeBytes("data=" + data + "&accId=" + accId + "&tokenId=" + tokenId);
				wr.flush();
				wr.close();
				wr = null;
				int resCode = conn.getResponseCode();
				LOG.info("Response Code:" + resCode);
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuilder sb = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					sb.append(inputLine);
				}
				in.close();
				in = null;
				conn.disconnect();
				conn = null;
				resCode = 0;
				inputLine = "";
				inputLine = null;
				LOG.info("Response Content:\n" + sb.toString());
				if (sb != null) {
					sb.setLength(0);
					sb = null;
				}
			} catch (java.net.SocketTimeoutException e) {
				// e.printStackTrace();
			} catch (MalformedURLException e) {
				// e.printStackTrace();
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
	}
}
