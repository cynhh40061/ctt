package tw.com.ctt.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IBaselineInfoDao;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class BaselineInfoDaoImpl extends BaseDao implements IBaselineInfoDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5393598519122647798L;
	private static final Logger LOG = LogManager.getLogger(BaselineInfoDaoImpl.class.getName());

	public String getNumByLen(int val, int len) {
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

	/**
	 * getBaseline (high frequency)
	 * and resort into map. by type_id, local_id, mid_id, min_id, prize_level
	 */
	@Override
	public Map<String, Object> getBaseline() {
		StringBuilder sb = null;
		HashMap<String, Object> mapAll = null;
		HashMap<String, Object> mapL0 = null;
		HashMap<String, Object> mapL1 = null;
		HashMap<String, Object> mapL2 = null;
		HashMap<String, Object> mapL3 = null;
		HashMap<String, Object> mapL4 = null;
		HashMap<String, String> mapL5 = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String level0_name = null;
		String level1_name = null;
		String level2_name = null;
		String level3_name = null;
		String level4_name = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return null;
			} else {
				sb = new StringBuilder();

				sb.append(
						" 	select A.date, A.local_id, A.lottery_type_id, A.lottery_mid_id, A.lottery_min_id, A.prize_level, B.dt_switch, B.dt_ratio, B.dt_bonus, A.baseline, B.base_bet from  	\n");
				sb.append(" 	( 	\n");
				sb.append(
						" 	select DATE_FORMAT(date,'%Y/%m/%d') as date, local_id, lottery_type_id, lottery_mid_id, lottery_min_id, prize_level, case when ratio_index = 0 then baseline_origin else case when ratio_index = 1 then baseline_level1 else baseline_level2 end end as baseline  	\n");
				sb.append(
						" 	from `ctt_manager`.`ctt_lottery_current_ratio` where DATE_FORMAT(date,'%Y/%m/%d') >= DATE_FORMAT(DATE_SUB(current_timestamp(),INTERVAL 1 DAY),'%Y/%m/%d')	\n");
				sb.append(" 	)A 	\n");
				sb.append(" 	LEFT JOIN 	\n");
				sb.append(" 	( 	\n");
				sb.append(
						" 	select lottery_local_id, lottery_type_id, lottery_mid_id, lottery_min_id, prize_level, dt_switch, dt_ratio, dt_bonus, base_bet from `ctt_manager`.`ctt_lottery_amount` 	\n");
				sb.append(" 	)B 	\n");
				sb.append(
						" 	ON A.local_id=B.lottery_local_id AND A.lottery_type_id=B.lottery_type_id AND A.lottery_mid_id=B.lottery_mid_id AND A.lottery_min_id=B.lottery_min_id AND A.prize_level=B.prize_level 	\n");
				sb.append(" 	LEFT JOIN	\n");
				sb.append(
						" 	(SELECT sort_level1_id as CL1, sort_level2_id as CL2, sort_level3_id as CL3, auth_id,lottery_title,level1_id,level2_id FROM `ctt_manager`.`ctt_lottery_auth` where auth_level_type =3 )C 	\n");
				sb.append(" 	ON A.lottery_min_id= C.auth_id	\n");
				sb.append(" 	order by A.date asc, A.local_id asc, C.CL2 asc, C.CL3 asc, A.prize_level asc ;	\n");

				ps = this.READ_CONN.prepareStatement(sb.toString());
				rs = ps.executeQuery();
				level0_name = null;
				level1_name = null;
				level2_name = null;
				level3_name = null;
				level4_name = null;

				mapAll = new HashMap<String, Object>();
				mapL0 = new HashMap<String, Object>();
				mapL1 = new HashMap<String, Object>();
				mapL2 = new HashMap<String, Object>();
				mapL3 = new HashMap<String, Object>();
				mapL4 = new HashMap<String, Object>();
				mapL5 = new HashMap<String, String>();

				while (rs.next()) {
					if (level0_name == null) {
						level0_name = rs.getString("date");
					}
					if (level1_name == null) {
						level1_name = rs.getString("lottery_type_id");
					}
					if (level2_name == null) {
						level2_name = rs.getString("local_id");
					}
					if (level3_name == null) {
						level3_name = rs.getString("lottery_mid_id");
					}
					if (level4_name == null) {
						level4_name = rs.getString("lottery_min_id");
					}

					if (!level4_name.equals(rs.getString("lottery_min_id"))) {
						if (mapL4 != null && !mapL4.isEmpty() && mapL4.size() > 0 && level4_name != null) {
							mapL3.put( level4_name, mapL4.clone());
						}
						level4_name = rs.getString("lottery_min_id");
//						mapL4 = null;
//						mapL4 = new HashMap<String, Object>();
						mapL4.clear();
					}
					if (!level3_name.equals(rs.getString("lottery_mid_id"))) {
						if (mapL4 != null && !mapL4.isEmpty() && mapL4.size() > 0 && level4_name != null) {
							mapL3.put(level4_name, mapL4.clone());
						}
						if (mapL3 != null && !mapL3.isEmpty() && mapL3.size() > 0 && level3_name != null) {
							mapL2.put(level3_name, mapL3.clone());
						}
						level3_name = rs.getString("lottery_mid_id");					
//						mapL3 = null;
//						mapL3 = new HashMap<String, Object>();
//						mapL4 = null;
//						mapL4 = new HashMap<String, Object>();
						mapL3.clear();
						mapL4.clear();
					}
					if (!level2_name.equals(rs.getString("local_id"))) {
						if (mapL4 != null && !mapL4.isEmpty() && mapL4.size() > 0 && level4_name != null) {
							mapL3.put(level4_name, mapL4.clone());
						}
						if (mapL3 != null && !mapL3.isEmpty() && mapL3.size() > 0 && level3_name != null) {
							mapL2.put(level3_name, mapL3.clone());
						}
						if (mapL2 != null && !mapL2.isEmpty() && mapL2.size() > 0 && level2_name != null) {
							mapL1.put(level2_name, mapL2.clone());
						}
						level2_name = rs.getString("local_id");						
//						mapL2 = null;
//						mapL2 = new HashMap<String, Object>();
//						mapL3 = null;
//						mapL3 = new HashMap<String, Object>();
//						mapL4 = null;
//						mapL4 = new HashMap<String, Object>();
						mapL2.clear();
						mapL3.clear();
						mapL4.clear();
					}
					if (!level1_name.equals(rs.getString("lottery_type_id"))) {
						if (mapL4 != null && !mapL4.isEmpty() && mapL4.size() > 0 && level4_name != null) {
							mapL3.put(level4_name, mapL4.clone());
						}
						if (mapL3 != null && !mapL3.isEmpty() && mapL3.size() > 0 && level3_name != null) {
							mapL2.put(level3_name, mapL3.clone());
						}
						if (mapL2 != null && !mapL2.isEmpty() && mapL2.size() > 0 && level2_name != null) {
							mapL1.put(level2_name, mapL2.clone());
						}
						if (mapL1 != null && !mapL1.isEmpty() && mapL1.size() > 0 && level1_name != null) {
							mapL0.put(level1_name, mapL1.clone());
						}
						level1_name = rs.getString("lottery_type_id");
//						mapL1 = null;
//						mapL1 = new HashMap<String, Object>();
//						mapL2 = null;
//						mapL2 = new HashMap<String, Object>();
//						mapL3 = null;
//						mapL3 = new HashMap<String, Object>();
//						mapL4 = null;
//						mapL4 = new HashMap<String, Object>();
						mapL1.clear();
						mapL2.clear();
						mapL3.clear();
						mapL4.clear();
					}
					if (!level0_name.equals(rs.getString("date"))) {
						if (mapL4 != null && !mapL4.isEmpty() && mapL4.size() > 0 && level4_name != null) {
							mapL3.put(level4_name, mapL4.clone());
						}
						if (mapL3 != null && !mapL3.isEmpty() && mapL3.size() > 0 && level3_name != null) {
							mapL2.put(level3_name, mapL3.clone());
						}
						if (mapL2 != null && !mapL2.isEmpty() && mapL2.size() > 0 && level2_name != null) {
							mapL1.put(level2_name, mapL2.clone());
						}
						if (mapL1 != null && !mapL1.isEmpty() && mapL1.size() > 0 && level1_name != null) {
							mapL0.put(level1_name, mapL1.clone());
						}
						if (level0_name != null) {
							mapAll.put(level0_name, mapL0.clone());
						}
						level0_name = rs.getString("date");
					
//						mapL0 = null;
//						mapL0 = new HashMap<String, Object>();
//						mapL1 = null;
//						mapL1 = new HashMap<String, Object>();
//						mapL2 = null;
//						mapL2 = new HashMap<String, Object>();
//						mapL3 = null;
//						mapL3 = new HashMap<String, Object>();
//						mapL4 = null;
//						mapL4 = new HashMap<String, Object>();
						mapL0.clear();
						mapL1.clear();
						mapL2.clear();
						mapL3.clear();
						mapL4.clear();
					}

//					mapL5 = null;
//					mapL5 = new ConcurrentHashMap<String, String>();
					
					mapL5.clear();

					mapL5.put("base_bet", rs.getString("base_bet")); // 最低下注金額
					mapL5.put("baseline", rs.getString("baseline")); // 賠率
					mapL5.put("dt_switch", rs.getString("dt_switch")); // 單挑開關
					mapL5.put("dt_ratio", rs.getString("dt_ratio")); // 單挑%數
					mapL5.put("dt_bonus", rs.getString("dt_bonus")); // 單挑獎金
					mapL4.put( rs.getString("prize_level"), mapL5.clone());
					
					
				}
				if (mapL4 != null && !mapL4.isEmpty() && mapL4.size() > 0 && level4_name != null) {
					mapL3.put(level4_name, mapL4.clone());
				}
				if (mapL3 != null && !mapL3.isEmpty() && mapL3.size() > 0 && level3_name != null) {
					mapL2.put(level3_name, mapL3.clone());
				}
				if (mapL2 != null && !mapL2.isEmpty() && mapL2.size() > 0 && level2_name != null) {
					mapL1.put(level2_name, mapL2.clone());
				}
				if (mapL1 != null && !mapL1.isEmpty() && mapL1.size() > 0 && level1_name != null) {
					mapL0.put(level1_name, mapL1.clone());
				}
				if (level0_name != null) {
					mapAll.put(level0_name, mapL0.clone());
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

	/**
	 * getBaselineLastUpdateTime
	 */
	@Override
	public long getBaselineLastUpdateTime() {
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
				sb.append(
						"SELECT UNIX_TIMESTAMP(ratio_update_time) AS t1 FROM `ctt_manager`.`ctt_lottery_current_ratio` ORDER BY ratio_update_time DESC limit 1;");
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

	/**
	 * getBaseline (low frequency)
	 * and resort into map. by type_id, local_id, mid_id, min_id, handicap, prize_level
	 */
	@Override
	public Map<String, Object> getBaselineLF() {
		StringBuilder sb = null;
		HashMap<String, Object> mapAll = null;
		HashMap<String, Object> mapL0 = null;
		HashMap<String, Object> mapL1 = null;
		HashMap<String, Object> mapL2 = null;
		HashMap<String, Object> mapL3 = null;
		HashMap<String, Object> mapL4 = null;
		HashMap<String, Object> mapL5 = null;
		HashMap<String, String> mapL6 = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String level0_name = null;
		String level1_name = null;
		String level2_name = null;
		String level3_name = null;
		String level4_name = null;
		String level5_name = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return null;
			} else {
				sb = new StringBuilder();
				sb.append(
						" 	select  A.date , A.local_id ,A.period_num, A.handicap, A.lottery_type_id, A.lottery_mid_id, A.lottery_min_id, A.prize_level, B.dt_switch, B.dt_ratio, B.dt_bonus, A.baseline, B.base_bet from\n");
				sb.append(" 	(\n");
				sb.append(
						" 	select DATE_FORMAT(date,'%Y/%m/%d') as date, lottery_local_id as local_id, lottery_type_id, lottery_mid_id, lottery_min_id, prize_level, now_baseline as baseline, period_num, handicap\n");
				sb.append(
						" 	from `ctt_manager`.`ctt_lottery_lowfreq_current_ratio` where DATE_FORMAT(date,'%Y/%m/%d') >= DATE_FORMAT(DATE_SUB(current_timestamp(),INTERVAL 1 DAY),'%Y/%m/%d')\n");
				sb.append(" 	)A\n");
				sb.append(" 	LEFT JOIN\n");
				sb.append(" 	(\n");
				sb.append(
						" 	select lottery_local_id, lottery_type_id, lottery_mid_id, lottery_min_id, prize_level, dt_switch, dt_ratio, dt_bonus, base_bet, handicap from `ctt_manager`.`ctt_lottery_lowfreq_amount`\n");
				sb.append(" 	)B\n");
				sb.append(
						" 	ON A.local_id=B.lottery_local_id AND A.lottery_type_id=B.lottery_type_id AND A.lottery_mid_id=B.lottery_mid_id AND A.lottery_min_id=B.lottery_min_id AND A.prize_level=B.prize_level AND A.handicap = B.handicap \n");
				sb.append(" 	LEFT JOIN\n");
				sb.append(" 	(\n");
				sb.append(
						" 	SELECT sort_level1_id as CL1, sort_level2_id as CL2, sort_level3_id as CL3, auth_id,lottery_title,level1_id,level2_id FROM `ctt_manager`.`ctt_lottery_auth` where auth_level_type =3\n");
				sb.append(" 	)C\n");
				sb.append(" 	ON A.lottery_min_id= C.auth_id\n");
				sb.append(
						" 	order by A.date asc, A.local_id asc, C.CL2 asc, C.CL3 asc, A.handicap asc, A.prize_level asc;\n");

				ps = this.READ_CONN.prepareStatement(sb.toString());
				rs = ps.executeQuery();
				level0_name = null;
				level1_name = null;
				level2_name = null;
				level3_name = null;
				level4_name = null;
				level5_name = null;
				mapAll = new HashMap<String, Object>();
				mapL0 = new HashMap<String, Object>();
				mapL1 = new HashMap<String, Object>();
				mapL2 = new HashMap<String, Object>();
				mapL3 = new HashMap<String, Object>();
				mapL4 = new HashMap<String, Object>();
				mapL5 = new HashMap<String, Object>();
				mapL6 = new HashMap<String, String>();
				while (rs.next()) {
					if (level0_name == null) {
						level0_name = rs.getString("date");
					}
					if (level1_name == null) {
						level1_name = rs.getString("lottery_type_id");
					}
					if (level2_name == null) {
						level2_name = rs.getString("local_id");
					}
					if (level3_name == null) {
						level3_name = rs.getString("lottery_mid_id");
					}
					if (level4_name == null) {
						level4_name = rs.getString("lottery_min_id");
					}
					if (level5_name == null) {
						level5_name = rs.getString("handicap");
					}
					if (!level5_name.equals(rs.getString("handicap"))) {
						if (mapL5 != null && !mapL5.isEmpty() && mapL5.size() > 0 && level5_name != null) {
							mapL4.put(level5_name, mapL5.clone());
						}
						level5_name = rs.getString("handicap");
//						mapL5 = null;
//						mapL5 = new HashMap<String, Object>();
						mapL5.clear();
					}
					if (!level4_name.equals(rs.getString("lottery_min_id"))) {
						if (mapL5 != null && !mapL5.isEmpty() && mapL5.size() > 0 && level5_name != null) {
							mapL4.put(level5_name, mapL5.clone());
						}
						if (mapL4 != null && !mapL4.isEmpty() && mapL4.size() > 0 && level4_name != null) {
							mapL3.put(level4_name, mapL4.clone());
						}
						level4_name = rs.getString("lottery_min_id");
//						mapL4 = null;
//						mapL4 = new HashMap<String, Object>();
//						mapL5 = null;
//						mapL5 = new HashMap<String, Object>();
						mapL4.clear();
						mapL5.clear();
					}
					if (!level3_name.equals(rs.getString("lottery_mid_id"))) {
						if (mapL5 != null && !mapL5.isEmpty() && mapL5.size() > 0 && level5_name != null) {
							mapL4.put(level5_name, mapL5.clone());
						}
						if (mapL4 != null && !mapL4.isEmpty() && mapL4.size() > 0 && level4_name != null) {
							mapL3.put(level4_name, mapL4.clone());
						}
						if (mapL3 != null && !mapL3.isEmpty() && mapL3.size() > 0 && level3_name != null) {
							mapL2.put(level3_name, mapL3.clone());
						}
						level3_name = rs.getString("lottery_mid_id");
//						mapL3 = null;
//						mapL3 = new HashMap<String, Object>();
//						mapL4 = null;
//						mapL4 = new HashMap<String, Object>();
//						mapL5 = null;
//						mapL5 = new HashMap<String, Object>();
						mapL3.clear();
						mapL4.clear();
						mapL5.clear();
					}
					if (!level2_name.equals(rs.getString("local_id"))) {
						if (mapL5 != null && !mapL5.isEmpty() && mapL5.size() > 0 && level5_name != null) {
							mapL4.put(level5_name, mapL5.clone());
						}
						if (mapL4 != null && !mapL4.isEmpty() && mapL4.size() > 0 && level4_name != null) {
							mapL3.put(level4_name, mapL4.clone());
						}
						if (mapL3 != null && !mapL3.isEmpty() && mapL3.size() > 0 && level3_name != null) {
							mapL2.put(level3_name, mapL3.clone());
						}
						if (mapL2 != null && !mapL2.isEmpty() && mapL2.size() > 0 && level2_name != null) {
							mapL1.put(level2_name, mapL2.clone());
						}
						level2_name = rs.getString("local_id");						
//						mapL2 = null;
//						mapL2 = new HashMap<String, Object>();
//						mapL3 = null;
//						mapL3 = new HashMap<String, Object>();
//						mapL4 = null;
//						mapL4 = new HashMap<String, Object>();
//						mapL5 = null;
//						mapL5 = new HashMap<String, Object>();
						mapL2.clear();
						mapL3.clear();
						mapL4.clear();
						mapL5.clear();
					}
					if (!level1_name.equals(rs.getString("lottery_type_id"))) {
						if (mapL5 != null && !mapL5.isEmpty() && mapL5.size() > 0 && level5_name != null) {
							mapL4.put(level5_name, mapL5.clone());
						}
						if (mapL4 != null && !mapL4.isEmpty() && mapL4.size() > 0 && level4_name != null) {
							mapL3.put(level4_name, mapL4.clone());
						}
						if (mapL3 != null && !mapL3.isEmpty() && mapL3.size() > 0 && level3_name != null) {
							mapL2.put(level3_name, mapL3.clone());
						}
						if (mapL2 != null && !mapL2.isEmpty() && mapL2.size() > 0 && level2_name != null) {
							mapL1.put(level2_name, mapL2.clone());
						}
						if (mapL1 != null && !mapL1.isEmpty() && mapL1.size() > 0 && level1_name != null) {
							mapL0.put(level1_name, mapL1.clone());
						}
						level1_name = rs.getString("lottery_type_id");
//						mapL1 = null;
//						mapL1 = new HashMap<String, Object>();
//						mapL2 = null;
//						mapL2 = new HashMap<String, Object>();
//						mapL3 = null;
//						mapL3 = new HashMap<String, Object>();
//						mapL4 = null;
//						mapL4 = new HashMap<String, Object>();
//						mapL5 = null;
//						mapL5 = new HashMap<String, Object>();
						mapL1.clear();
						mapL2.clear();
						mapL3.clear();
						mapL4.clear();
						mapL5.clear();
					}
					if (!level0_name.equals(rs.getString("date"))) {
						if (mapL5 != null && !mapL5.isEmpty() && mapL5.size() > 0 && level5_name != null) {
							mapL4.put(level5_name, mapL5.clone());
						}
						if (mapL4 != null && !mapL4.isEmpty() && mapL4.size() > 0 && level4_name != null) {
							mapL3.put(level4_name, mapL4.clone());
						}
						if (mapL3 != null && !mapL3.isEmpty() && mapL3.size() > 0 && level3_name != null) {
							mapL2.put(level3_name, mapL3.clone());
						}
						if (mapL2 != null && !mapL2.isEmpty() && mapL2.size() > 0 && level2_name != null) {
							mapL1.put(level2_name, mapL2.clone());
						}
						if (mapL1 != null && !mapL1.isEmpty() && mapL1.size() > 0 && level1_name != null) {
							mapL0.put(level1_name, mapL1.clone());
						}
						if (level0_name != null) {
							mapAll.put(level0_name, mapL0.clone());
						}
						level0_name = rs.getString("date");
//						mapL0 = null;
//						mapL0 = new HashMap<String, Object>();
//						mapL1 = null;
//						mapL1 = new HashMap<String, Object>();
//						mapL2 = null;
//						mapL2 = new HashMap<String, Object>();
//						mapL3 = null;
//						mapL3 = new HashMap<String, Object>();
//						mapL4 = null;
//						mapL4 = new HashMap<String, Object>();
//						mapL5 = null;
//						mapL5 = new HashMap<String, Object>();
						mapL0.clear();
						mapL1.clear();
						mapL2.clear();
						mapL3.clear();
						mapL4.clear();
						mapL5.clear();
					}
					mapL6.clear();
//					mapL6 = null;
//					mapL6 = new ConcurrentHashMap<String, String>();
					mapL6.put("period_num", rs.getString("period_num")); // 旗號
					mapL6.put("base_bet", rs.getString("base_bet")); // 最低下注金額
					mapL6.put("baseline", rs.getString("baseline")); // 賠率
					mapL6.put("dt_switch", rs.getString("dt_switch")); // 單挑開關
					mapL6.put("dt_ratio", rs.getString("dt_ratio")); // 單挑%數
					mapL6.put("dt_bonus", rs.getString("dt_bonus")); // 單挑獎金
					mapL5.put(rs.getString("prize_level"), mapL6.clone());
				}
				if (mapL5 != null && !mapL5.isEmpty() && mapL5.size() > 0 && level5_name != null) {
					mapL4.put(level5_name, mapL5.clone());
				}
				if (mapL4 != null && !mapL4.isEmpty() && mapL4.size() > 0 && level4_name != null) {
					mapL3.put(level4_name, mapL4.clone());
				}
				if (mapL3 != null && !mapL3.isEmpty() && mapL3.size() > 0 && level3_name != null) {
					mapL2.put(level3_name, mapL3.clone());
				}
				if (mapL2 != null && !mapL2.isEmpty() && mapL2.size() > 0 && level2_name != null) {
					mapL1.put(level2_name, mapL2.clone());
				}
				if (mapL1 != null && !mapL1.isEmpty() && mapL1.size() > 0 && level1_name != null) {
					mapL0.put(level1_name, mapL1.clone());
				}
				if (level0_name != null) {
					mapAll.put(level0_name, mapL0.clone());
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

	/**
	 * getBaselineLastUpdateTimeLF
	 */
	@Override
	public long getBaselineLastUpdateTimeLF() {
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
				sb.append(
						"SELECT UNIX_TIMESTAMP(MAX(ratio_update_time)) AS t1 FROM `ctt_manager`.`ctt_lottery_lowfreq_current_ratio`;");
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
}
