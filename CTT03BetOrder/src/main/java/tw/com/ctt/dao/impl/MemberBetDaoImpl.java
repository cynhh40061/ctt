package tw.com.ctt.dao.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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

import tw.com.ctt.action.BaseAction;
import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.dao.IMemberBetDao;
import tw.com.ctt.model.MemBean;
import tw.com.ctt.model.PunchGameRecordsBean;
import tw.com.ctt.util.DataSource;
import tw.com.ctt.util.MyUtil;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class MemberBetDaoImpl extends BaseDao implements IMemberBetDao {
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

	/**
	 * cancle all orders of period.
	 */
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

/**
 * cancleMainOrder
 */
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

	/**
	 * getNextAutoIncrementId for main order as main order id.
	 */
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
	
	/**
	 * cancleMidOrder
	 */
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
	
	/**
	 * getMemMoney
	 */
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

	/**
	 * getAccDetailsForLog
	 */
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

	/**
	 * checkRealTimeBaselinePeriodAndHandiCap
	 * 
	 * will check if those data is the same as DB.
	 */
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
	
	/**
	 * checkRealTimeBaselinePeriodAndHandiCap(Low frequency)
	 * 
	 * will check if those data is the same as DB.
	 */
	@Override
	public boolean checkRealTimeBaselinePeriodAndHandiCapLF(long accId, long periodNum, long minAuthId, int localId,
			String dateOfTable, int handiCap, Map<String, Map<String, Map<String, String>>> tmpRatios) {
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
				sb.append(" select case when A.cou=1 AND A.is_order_can_be_delete = 1 then 1 else 0 end as is_order_can_be_delete, B.baseline as baseline, C.handicap as handicap, D.dt_switch, D.dt_ratio, D.dt_bonus, B.prize_level from(\n");
				sb.append(" (\n");
				sb.append(" 	select count(1) as cou, case when unix_timestamp(current_timestamp()) between unix_timestamp(start_betting_time) AND unix_timestamp(stop_betting_time) then 1 else 0 end as is_order_can_be_delete from ctt_manager.ctt_lottery where period_num = ? AND id = ?\n");
				sb.append(" 	)A\n");
				sb.append(" 	left outer join\n");
				sb.append(" 	(\n");
				sb.append(" 	select date, baseline, prize_level \n");
				sb.append(" 	from `ctt_manager`.`ctt_lottery_lowfreq_current_ratio` where lottery_local_id = ? AND lottery_min_id = ? AND handicap = ? AND DATE_FORMAT(date,'%Y/%m/%d') = DATE_FORMAT(?,'%Y/%m/%d')\n");
				sb.append(" 	)B\n");
				sb.append(" 	on 1\n");
				sb.append(" 	left outer join\n");
				sb.append(" 	(\n");
				sb.append(" 	select handicap from `ctt_manager`.`ctt_member_acc` where acc_id = ?\n");
				sb.append(" 	)C\n");
				sb.append(" 	on 1\n");
				sb.append(" 	left outer join\n");
				sb.append(" 	(\n");
				sb.append(" 	select dt_switch, dt_ratio, dt_bonus, prize_level from `ctt_manager`.`ctt_lottery_lowfreq_amount` where lottery_local_id = ? AND lottery_min_id = ? AND handicap = ?\n");
				sb.append(" 	)D\n");
				sb.append(" 	on B.prize_level = D.prize_level\n");
				sb.append(" );\n");				
				ps = this.READ_CONN.prepareStatement(sb.toString());
				ps.setLong(1, periodNum);
				ps.setInt(2, localId);
				ps.setInt(3, localId);
				ps.setLong(4, minAuthId);
				ps.setLong(5, handiCap);
				ps.setString(6, dateOfTable);
				ps.setLong(7, accId);
				ps.setInt(8, localId);
				ps.setLong(9, minAuthId);
				ps.setLong(10, handiCap);
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
						if (new BigDecimal(tmpRatios.get(""+handiCap).get(prizeLevel).get("baseline"))
								.compareTo(new BigDecimal(baseline)) != 0
								|| new BigDecimal(tmpRatios.get(""+handiCap).get(prizeLevel).get("dt_ratio"))
										.compareTo(new BigDecimal(dtRatio)) != 0
								|| new BigDecimal(tmpRatios.get(""+handiCap).get(prizeLevel).get("dt_bonus"))
										.compareTo(new BigDecimal(dtBonus)) != 0
								|| !dtSwitch.equals(tmpRatios.get(""+handiCap).get(prizeLevel).get("dt_switch"))) {
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

	/**
	 * sentURL
	 * 
	 */
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
				wr.writeBytes("data=" + BaseAction.encodeURL(data) + "&accId=" + BaseAction.encodeURL(accId) + "&tokenId="
						+ BaseAction.encodeURL(tokenId));
				wr.flush();
				wr.close();
				wr = null;
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				in.close();
				in = null;
				conn.disconnect();
				conn = null;
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
