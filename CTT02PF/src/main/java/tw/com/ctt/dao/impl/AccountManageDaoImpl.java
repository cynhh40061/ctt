package tw.com.ctt.dao.impl;

import static tw.com.ctt.constant.AccountManagerConstant.*;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IAccountManageDao;
import tw.com.ctt.model.AddAccountBean;
import tw.com.ctt.model.ManagerAcciunyInfos;
import tw.com.ctt.model.ManagerRatioBean;
import tw.com.ctt.model.MemBean;
import tw.com.ctt.model.MgrBean;
import tw.com.ctt.model.SetGameServerBean;
import tw.com.ctt.util.CalculateMemberRatio;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class AccountManageDaoImpl extends BaseDao implements IAccountManageDao {

	private static final long serialVersionUID = -4046186702438639069L;
	private static final Logger LOG = LogManager.getLogger(AccountManageDaoImpl.class.getName());
	public static final String[] orderBy = { "A.acc_id", "C.nickname", "C.acc_name", "D.create_time", "E.ip", "E.last_login_time" };// 1-5,default:0
	public static final String[] ASC = { "ASC", "DESC" };// 0-1
	public static final int timeoutMin = 30;

	private static final String LEVEL_TYPE_MEM = "" + ACC_LEVEL_MEM;

	public AccountManageDaoImpl() {
		super();
	}
	@Override
	public int findLevel(long userId) {
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sb.append("SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id=" + userId + " \n");
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return 8;
			} else {
				LOG.debug("sql:\n" + sb.toString());
				ps = this.READ_CONN.prepareStatement(sb.toString());
				rs = ps.executeQuery();
				if (rs.next()) {
					return rs.getInt("acc_level_type");
				}
				return 8;
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					LOG.debug("SQLException, " + e.getMessage());
					ShowLog.err(LOG, e);
				}
				rs = null;
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.debug("SQLException, " + e.getMessage());
					ShowLog.err(LOG, e);
				}
				rs = null;
			}
		}
		return 8;
	}

	@Override
	public Map<String, String> findTypeTotal(long userId, int status) {
		StringBuilder sb = new StringBuilder();
		List<Object> selectObj = new ArrayList<Object>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, String> map = new HashMap<String, String>();
		status = 1;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return map;
			} else {
				sb.append("SELECT DISTINCT(acc_id) \n");
				sb.append(",SUM(CASE WHEN acc_level_type=1 THEN 1 ELSE 0 END) AS 'com' \n");
				sb.append(",SUM(CASE WHEN acc_level_type=2 THEN 1 ELSE 0 END) AS 'sc' \n");
				sb.append(",SUM(CASE WHEN acc_level_type=3 THEN 1 ELSE 0 END) AS 'bc' \n");
				sb.append(",SUM(CASE WHEN acc_level_type=4 THEN 1 ELSE 0 END) AS 'co' \n");
				sb.append(",SUM(CASE WHEN acc_level_type=5 THEN 1 ELSE 0 END) AS 'sa' \n");
				sb.append(",SUM(CASE WHEN acc_level_type=6 THEN 1 ELSE 0 END) AS 'ag' \n");
				sb.append(",SUM(CASE WHEN acc_level_type=7 THEN 1 ELSE 0 END) AS 'ag1' \n");
				sb.append(",SUM(CASE WHEN acc_level_type=8 THEN 1 ELSE 0 END) AS 'ag2' \n");
				sb.append(",SUM(CASE WHEN acc_level_type=9 THEN 1 ELSE 0 END) AS 'ag3' \n");
				sb.append(",SUM(CASE WHEN acc_level_type=10 THEN 1 ELSE 0 END) AS 'ag4' \n");
				sb.append(",SUM(CASE WHEN acc_level_type=11 THEN 1 ELSE 0 END) AS 'ag5' \n");
				sb.append(",SUM(CASE WHEN acc_level_type=12 THEN 1 ELSE 0 END) AS 'ag6' \n");
				sb.append(",SUM(CASE WHEN acc_level_type=13 THEN 1 ELSE 0 END) AS 'ag7' \n");
				sb.append(",SUM(CASE WHEN acc_level_type=14 THEN 1 ELSE 0 END) AS 'ag8' \n");
				sb.append(",SUM(CASE WHEN acc_level_type=15 THEN 1 ELSE 0 END) AS 'ag9' \n");
				sb.append(",SUM(CASE WHEN acc_level_type=16 THEN 1 ELSE 0 END) AS 'ag10' \n");
				sb.append(",SUM(CASE WHEN acc_level_type=" + LEVEL_TYPE_MEM + " THEN 1 ELSE 0 END) AS 'mem' \n");
				sb.append("FROM \n");
				sb.append("( \n");
				sb.append(
						"SELECT ? AS acc_id,acc_level_type,sc,bc,co,sa,ag,ag1,ag2,ag3,ag4,ag5,ag6,ag7,ag8,ag9,ag10 FROM ctt_manager.ctt_manager_acc_level \n");
				selectObj.add(userId);
				sb.append("WHERE (CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id=?) \n");
				selectObj.add(userId);
				sb.append("WHEN (2) THEN (CASE WHEN sc=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (3) THEN (CASE WHEN bc=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (4) THEN (CASE WHEN co=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (5) THEN (CASE WHEN sa=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (6) THEN (CASE WHEN ag=? THEN TRUE ELSE FALSE END)\n");
				selectObj.add(userId);
				sb.append("WHEN (7) THEN (CASE WHEN ag1=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (8) THEN (CASE WHEN ag2=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (9) THEN (CASE WHEN ag3=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (10) THEN (CASE WHEN ag4=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (11) THEN (CASE WHEN ag5=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (12) THEN (CASE WHEN ag6=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (13) THEN (CASE WHEN ag7=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (14) THEN (CASE WHEN ag8=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (15) THEN (CASE WHEN ag9=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (16) THEN (CASE WHEN ag10=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (1) THEN TRUE WHEN (0) THEN TRUE ELSE FALSE END ) \n");
				sb.append("AND acc_level_type>(SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id=?) AND acc_status=? \n");
				selectObj.add(userId);
				selectObj.add(status);
				sb.append("UNION ALL \n");
				sb.append("SELECT ? AS acc_id," + LEVEL_TYPE_MEM
						+ " AS acc_level_type,sc,bc,co,sa,ag,ag1,ag2,ag3,ag4,ag5,ag6,ag7,ag8,ag9,ag10 FROM ctt_manager.ctt_member_acc_level \n");
				selectObj.add(userId);
				sb.append("WHERE (CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id=?) \n");
				selectObj.add(userId);
				sb.append("WHEN (2) THEN (CASE WHEN sc=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (3) THEN (CASE WHEN bc=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (4) THEN (CASE WHEN co=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (5) THEN (CASE WHEN sa=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (6) THEN (CASE WHEN ag=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (7) THEN (CASE WHEN ag1=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (8) THEN (CASE WHEN ag2=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (9) THEN (CASE WHEN ag3=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (10) THEN (CASE WHEN ag4=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (11) THEN (CASE WHEN ag5=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (12) THEN (CASE WHEN ag6=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (13) THEN (CASE WHEN ag7=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (14) THEN (CASE WHEN ag8=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (15) THEN (CASE WHEN ag9=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (16) THEN (CASE WHEN ag10=? THEN TRUE ELSE FALSE END) \n");
				selectObj.add(userId);
				sb.append("WHEN (1) THEN TRUE WHEN (0) THEN TRUE ELSE FALSE END ) \n");
				sb.append("AND acc_status=? \n");
				selectObj.add(status);
				sb.append(") A GROUP BY acc_id \n");

				List<Map<String, Object>> listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);

				for (int i = 0; i < listMap.size(); i++) {
					for (String key : listMap.get(i).keySet()) {
						if (listMap.get(i).containsKey(key) && !"".equals(listMap.get(i).get(key).toString())) {
							map.put(key, listMap.get(i).get(key).toString());
						} else {
							map.put(key, "0");
						}

					}
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					LOG.debug("SQLException, " + e.getMessage());
					ShowLog.err(LOG, e);
				}
				rs = null;
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.debug("SQLException, " + e.getMessage());
					ShowLog.err(LOG, e);
				}
				rs = null;
			}
		}
		return map;
	}

	private String getSelect(int type) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(" A.acc_id \n");
			sb.append(",G.onlines AS onlines ");
			sb.append("    ,C.nickname,C.acc_name \n");
			sb.append("    ,D.create_time,A.acc_status,E.ip,E.last_login_time \n");
			if (type == ACC_LEVEL_COM) {
				sb.append("    ,J.group_text \n");
				sb.append("    ,0 AS 'com' \n");
				sb.append("    ,0 AS 'sc' \n");
				sb.append("    ,0 AS 'bc' \n");
				sb.append("    ,0 AS 'co' \n");
				sb.append("    ,0 AS 'sa' \n");
				sb.append("    ,0 AS 'ag' \n");
				sb.append("    ,0 AS 'ag1' \n");
				sb.append("    ,0 AS 'ag2' \n");
				sb.append("    ,0 AS 'ag3' \n");
				sb.append("    ,0 AS 'ag4' \n");
				sb.append("    ,0 AS 'ag5' \n");
				sb.append("    ,0 AS 'ag6' \n");
				sb.append("    ,0 AS 'ag7' \n");
				sb.append("    ,0 AS 'ag8' \n");
				sb.append("    ,0 AS 'ag9' \n");
				sb.append("    ,0 AS 'ag10' \n");
				sb.append("    ,0 AS 'mem' \n");
				sb.append("    ,'公司' AS acc_type \n");
				sb.append("    ,'' AS sc_id \n");
				sb.append("    ,'' AS sc_nickname \n");
				sb.append("    ,'' AS sc_acc_name \n");
				sb.append("    ,'' AS sc_acc_status \n");
				sb.append("    ,'' AS bc_id \n");
				sb.append("    ,'' AS bc_nickname \n");
				sb.append("    ,'' AS bc_acc_name \n");
				sb.append("    ,'' AS bc_acc_status \n");
				sb.append("    ,'' AS co_id \n");
				sb.append("    ,'' AS co_nickname \n");
				sb.append("    ,'' AS co_acc_name \n");
				sb.append("    ,'' AS co_acc_status \n");
				sb.append("    ,'' AS sa_id \n");
				sb.append("    ,'' AS sa_nickname \n");
				sb.append("    ,'' AS sa_acc_name \n");
				sb.append("    ,'' AS sa_acc_status \n");
				sb.append("    ,'' AS ag_id \n");
				sb.append("    ,'' AS ag_nickname \n");
				sb.append("    ,'' AS ag_acc_name \n");
				sb.append("    ,'' AS ag_acc_status \n");

				sb.append(",'' AS ag1_id \n");
				sb.append(",'' AS ag1_nickname \n");
				sb.append(",'' AS ag1_acc_name \n");
				sb.append(",'' AS ag1_acc_status \n");
				sb.append(",'' AS ag2_id \n");
				sb.append(",'' AS ag2_nickname \n");
				sb.append(",'' AS ag2_acc_name \n");
				sb.append(",'' AS ag2_acc_status \n");
				sb.append(",'' AS ag3_id \n");
				sb.append(",'' AS ag3_nickname \n");
				sb.append(",'' AS ag3_acc_name \n");
				sb.append(",'' AS ag3_acc_status\n");
				sb.append(",'' AS ag4_id \n");
				sb.append(",'' AS ag4_nickname \n");
				sb.append(",'' AS ag4_acc_name \n");
				sb.append(",'' AS ag4_acc_status \n");
				sb.append(",'' AS ag5_id \n");
				sb.append(",'' AS ag5_nickname \n");
				sb.append(",'' AS ag5_acc_name \n");
				sb.append(",'' AS ag5_acc_status \n");
				sb.append(",'' AS ag6_id \n");
				sb.append(",'' AS ag6_nickname \n");
				sb.append(",'' AS ag6_acc_name \n");
				sb.append(",'' AS ag6_acc_status \n");
				sb.append(",'' AS ag7_id \n");
				sb.append(",'' AS ag7_nickname \n");
				sb.append(",'' AS ag7_acc_name \n");
				sb.append(",'' AS ag7_acc_status \n");
				sb.append(",'' AS ag8_id \n");
				sb.append(",'' AS ag8_nickname \n");
				sb.append(",'' AS ag8_acc_name \n");
				sb.append(",'' AS ag8_acc_status \n");
				sb.append(",'' AS ag9_id \n");
				sb.append(",'' AS ag9_nickname \n");
				sb.append(",'' AS ag9_acc_name \n");
				sb.append(",'' AS ag9_acc_status \n");
				sb.append(",'' AS ag10_id \n");
				sb.append(",'' AS ag10_nickname \n");
				sb.append(",'' AS ag10_acc_name \n");
				sb.append(",'' AS ag10_acc_status \n");

				sb.append("    ,-1 AS g1_com \n");
				sb.append("    ,-1 AS g1_sc \n");
				sb.append("    ,-1 AS g1_bc \n");
				sb.append("    ,-1 AS g1_co \n");
				sb.append("    ,-1 AS g1_sa \n");
				sb.append("    ,-1 AS g1_ag \n");
				sb.append("    ,-1 AS g1_ag1 \n");
				sb.append("    ,-1 AS g1_ag2 \n");
				sb.append("    ,-1 AS g1_ag3 \n");
				sb.append("    ,-1 AS g1_ag4 \n");
				sb.append("    ,-1 AS g1_ag5 \n");
				sb.append("    ,-1 AS g1_ag6 \n");
				sb.append("    ,-1 AS g1_ag7 \n");
				sb.append("    ,-1 AS g1_ag8 \n");
				sb.append("    ,-1 AS g1_ag9 \n");
				sb.append("    ,-1 AS g1_ag10 \n");
				sb.append("    ,-1 AS g2_com \n");
				sb.append("    ,-1 AS g2_sc \n");
				sb.append("    ,-1 AS g2_bc \n");
				sb.append("    ,-1 AS g2_co \n");
				sb.append("    ,-1 AS g2_sa \n");
				sb.append("    ,-1 AS g2_ag \n");
				sb.append("    ,-1 AS g2_ag1 \n");
				sb.append("    ,-1 AS g2_ag2 \n");
				sb.append("    ,-1 AS g2_ag3 \n");
				sb.append("    ,-1 AS g2_ag4 \n");
				sb.append("    ,-1 AS g2_ag5 \n");
				sb.append("    ,-1 AS g2_ag6 \n");
				sb.append("    ,-1 AS g2_ag7 \n");
				sb.append("    ,-1 AS g2_ag8 \n");
				sb.append("    ,-1 AS g2_ag9 \n");
				sb.append("    ,-1 AS g2_ag10 \n");
				sb.append("    ,-1 AS g3_com \n");
				sb.append("    ,-1 AS g3_sc \n");
				sb.append("    ,-1 AS g3_bc \n");
				sb.append("    ,-1 AS g3_co \n");
				sb.append("    ,-1 AS g3_sa \n");
				sb.append("    ,-1 AS g3_ag \n");
				sb.append("    ,-1 AS g3_ag1 \n");
				sb.append("    ,-1 AS g3_ag2 \n");
				sb.append("    ,-1 AS g3_ag3 \n");
				sb.append("    ,-1 AS g3_ag4 \n");
				sb.append("    ,-1 AS g3_ag5 \n");
				sb.append("    ,-1 AS g3_ag6 \n");
				sb.append("    ,-1 AS g3_ag7 \n");
				sb.append("    ,-1 AS g3_ag8 \n");
				sb.append("    ,-1 AS g3_ag9 \n");
				sb.append("    ,-1 AS g3_ag10 \n");
				sb.append("    ,-1 AS g4_com \n");
				sb.append("    ,-1 AS g4_sc \n");
				sb.append("    ,-1 AS g4_bc \n");
				sb.append("    ,-1 AS g4_co \n");
				sb.append("    ,-1 AS g4_sa \n");
				sb.append("    ,-1 AS g4_ag \n");
				sb.append("    ,-1 AS g4_ag1 \n");
				sb.append("    ,-1 AS g4_ag2 \n");
				sb.append("    ,-1 AS g4_ag3 \n");
				sb.append("    ,-1 AS g4_ag4 \n");
				sb.append("    ,-1 AS g4_ag5 \n");
				sb.append("    ,-1 AS g4_ag6 \n");
				sb.append("    ,-1 AS g4_ag7 \n");
				sb.append("    ,-1 AS g4_ag8 \n");
				sb.append("    ,-1 AS g4_ag9 \n");
				sb.append("    ,-1 AS g4_ag10 \n");
				sb.append("    ,-1 AS g5_com \n");
				sb.append("    ,-1 AS g5_sc \n");
				sb.append("    ,-1 AS g5_bc \n");
				sb.append("    ,-1 AS g5_co \n");
				sb.append("    ,-1 AS g5_sa \n");
				sb.append("    ,-1 AS g5_ag \n");
				sb.append("    ,-1 AS g5_ag1 \n");
				sb.append("    ,-1 AS g5_ag2 \n");
				sb.append("    ,-1 AS g5_ag3 \n");
				sb.append("    ,-1 AS g5_ag4 \n");
				sb.append("    ,-1 AS g5_ag5 \n");
				sb.append("    ,-1 AS g5_ag6 \n");
				sb.append("    ,-1 AS g5_ag7 \n");
				sb.append("    ,-1 AS g5_ag8 \n");
				sb.append("    ,-1 AS g5_ag9 \n");
				sb.append("    ,-1 AS g5_ag10 \n");

				sb.append("    ,-1 AS next_g1_com \n");
				sb.append("    ,-1 AS next_g1_sc \n");
				sb.append("    ,-1 AS next_g1_bc \n");
				sb.append("    ,-1 AS next_g1_co \n");
				sb.append("    ,-1 AS next_g1_sa \n");
				sb.append("    ,-1 AS next_g1_ag \n");
				sb.append("    ,-1 AS next_g1_ag1 \n");
				sb.append("    ,-1 AS next_g1_ag2 \n");
				sb.append("    ,-1 AS next_g1_ag3 \n");
				sb.append("    ,-1 AS next_g1_ag4 \n");
				sb.append("    ,-1 AS next_g1_ag5 \n");
				sb.append("    ,-1 AS next_g1_ag6 \n");
				sb.append("    ,-1 AS next_g1_ag7 \n");
				sb.append("    ,-1 AS next_g1_ag8 \n");
				sb.append("    ,-1 AS next_g1_ag9 \n");
				sb.append("    ,-1 AS next_g1_ag10 \n");
				sb.append("    ,-1 AS next_g2_com \n");
				sb.append("    ,-1 AS next_g2_sc \n");
				sb.append("    ,-1 AS next_g2_bc \n");
				sb.append("    ,-1 AS next_g2_co \n");
				sb.append("    ,-1 AS next_g2_sa \n");
				sb.append("    ,-1 AS next_g2_ag \n");
				sb.append("    ,-1 AS next_g2_ag1 \n");
				sb.append("    ,-1 AS next_g2_ag2 \n");
				sb.append("    ,-1 AS next_g2_ag3 \n");
				sb.append("    ,-1 AS next_g2_ag4 \n");
				sb.append("    ,-1 AS next_g2_ag5 \n");
				sb.append("    ,-1 AS next_g2_ag6 \n");
				sb.append("    ,-1 AS next_g2_ag7 \n");
				sb.append("    ,-1 AS next_g2_ag8 \n");
				sb.append("    ,-1 AS next_g2_ag9 \n");
				sb.append("    ,-1 AS next_g2_ag10 \n");
				sb.append("    ,-1 AS next_g3_com \n");
				sb.append("    ,-1 AS next_g3_sc \n");
				sb.append("    ,-1 AS next_g3_bc \n");
				sb.append("    ,-1 AS next_g3_co \n");
				sb.append("    ,-1 AS next_g3_sa \n");
				sb.append("    ,-1 AS next_g3_ag \n");
				sb.append("    ,-1 AS next_g3_ag1 \n");
				sb.append("    ,-1 AS next_g3_ag2 \n");
				sb.append("    ,-1 AS next_g3_ag3 \n");
				sb.append("    ,-1 AS next_g3_ag4 \n");
				sb.append("    ,-1 AS next_g3_ag5 \n");
				sb.append("    ,-1 AS next_g3_ag6 \n");
				sb.append("    ,-1 AS next_g3_ag7 \n");
				sb.append("    ,-1 AS next_g3_ag8 \n");
				sb.append("    ,-1 AS next_g3_ag9 \n");
				sb.append("    ,-1 AS next_g3_ag10 \n");
				sb.append("    ,-1 AS next_g4_com \n");
				sb.append("    ,-1 AS next_g4_sc \n");
				sb.append("    ,-1 AS next_g4_bc \n");
				sb.append("    ,-1 AS next_g4_co \n");
				sb.append("    ,-1 AS next_g4_sa \n");
				sb.append("    ,-1 AS next_g4_ag \n");
				sb.append("    ,-1 AS next_g4_ag1 \n");
				sb.append("    ,-1 AS next_g4_ag2 \n");
				sb.append("    ,-1 AS next_g4_ag3 \n");
				sb.append("    ,-1 AS next_g4_ag4 \n");
				sb.append("    ,-1 AS next_g4_ag5 \n");
				sb.append("    ,-1 AS next_g4_ag6 \n");
				sb.append("    ,-1 AS next_g4_ag7 \n");
				sb.append("    ,-1 AS next_g4_ag8 \n");
				sb.append("    ,-1 AS next_g4_ag9 \n");
				sb.append("    ,-1 AS next_g4_ag10 \n");
				sb.append("    ,-1 AS next_g5_com \n");
				sb.append("    ,-1 AS next_g5_sc \n");
				sb.append("    ,-1 AS next_g5_bc \n");
				sb.append("    ,-1 AS next_g5_co \n");
				sb.append("    ,-1 AS next_g5_sa \n");
				sb.append("    ,-1 AS next_g5_ag \n");
				sb.append("    ,-1 AS next_g5_ag1 \n");
				sb.append("    ,-1 AS next_g5_ag2 \n");
				sb.append("    ,-1 AS next_g5_ag3 \n");
				sb.append("    ,-1 AS next_g5_ag4 \n");
				sb.append("    ,-1 AS next_g5_ag5 \n");
				sb.append("    ,-1 AS next_g5_ag6 \n");
				sb.append("    ,-1 AS next_g5_ag7 \n");
				sb.append("    ,-1 AS next_g5_ag8 \n");
				sb.append("    ,-1 AS next_g5_ag9 \n");
				sb.append("    ,-1 AS next_g5_ag10 \n");
				sb.append("    ,TRUE as update_status  \n");
			} else if (type == ACC_LEVEL_SC) {
				sb.append("	,J.group_text  \n");
				sb.append("	,CASE WHEN B.com IS NULL THEN 0 ELSE B.com END AS 'com'  \n");
				sb.append("	,CASE WHEN B.sc IS NULL THEN 0 ELSE B.sc END AS 'sc'  \n");
				sb.append("	,CASE WHEN B.bc IS NULL THEN 0 ELSE B.bc END AS 'bc'  \n");
				sb.append("	,CASE WHEN B.co IS NULL THEN 0 ELSE B.co END AS 'co'  \n");
				sb.append("	,CASE WHEN B.sa IS NULL THEN 0 ELSE B.sa END AS 'sa'  \n");
				sb.append("	,CASE WHEN B.ag IS NULL THEN 0 ELSE B.ag END AS 'ag'  \n");
				sb.append("	,CASE WHEN B.ag1 IS NULL THEN 0 ELSE B.ag1 END AS 'ag1'  \n");
				sb.append("	,CASE WHEN B.ag2 IS NULL THEN 0 ELSE B.ag2 END AS 'ag2'  \n");
				sb.append("	,CASE WHEN B.ag3 IS NULL THEN 0 ELSE B.ag3 END AS 'ag3'  \n");
				sb.append("	,CASE WHEN B.ag4 IS NULL THEN 0 ELSE B.ag4 END AS 'ag4'  \n");
				sb.append("	,CASE WHEN B.ag5 IS NULL THEN 0 ELSE B.ag5 END AS 'ag5'  \n");
				sb.append("	,CASE WHEN B.ag6 IS NULL THEN 0 ELSE B.ag6 END AS 'ag6'  \n");
				sb.append("	,CASE WHEN B.ag7 IS NULL THEN 0 ELSE B.ag7 END AS 'ag7'  \n");
				sb.append("	,CASE WHEN B.ag8 IS NULL THEN 0 ELSE B.ag8 END AS 'ag8' \n");
				sb.append("	,CASE WHEN B.ag9 IS NULL THEN 0 ELSE B.ag9 END AS 'ag9'  \n");
				sb.append("	,CASE WHEN B.ag10 IS NULL THEN 0 ELSE B.ag10 END AS 'ag10'   \n");
				sb.append("	,CASE WHEN B.mem IS NULL THEN 0 ELSE B.mem END AS 'mem'  \n");
				sb.append("	,'總監' AS acc_type  \n");
				sb.append("	,'' AS sc_id  \n");
				sb.append("	,'' AS sc_nickname  \n");
				sb.append("	,'' AS sc_acc_name  \n");
				sb.append("	,'' AS sc_acc_status  \n");
				sb.append("	,'' AS bc_id  \n");
				sb.append("	,'' AS bc_nickname  \n");
				sb.append("	,'' AS bc_acc_name  \n");
				sb.append("	,'' AS bc_acc_status  \n");
				sb.append("	,'' AS co_id  \n");
				sb.append("	,'' AS co_nickname  \n");
				sb.append("	,'' AS co_acc_name  \n");
				sb.append("	,'' AS co_acc_status  \n");
				sb.append("	,'' AS sa_id  \n");
				sb.append("	,'' AS sa_nickname  \n");
				sb.append("	,'' AS sa_acc_name  \n");
				sb.append("	,'' AS sa_acc_status  \n");
				sb.append("	,'' AS ag_id  \n");
				sb.append("	,'' AS ag_nickname  \n");
				sb.append("	,'' AS ag_acc_name  \n");
				sb.append("	,'' AS ag_acc_status  \n");
				sb.append("	,'' AS ag1_id  \n");
				sb.append("	,'' AS ag1_nickname  \n");
				sb.append("	,'' AS ag1_acc_name  \n");
				sb.append("	,'' AS ag1_acc_status  \n");
				sb.append("	,'' AS ag2_id  \n");
				sb.append("	,'' AS ag2_nickname  \n");
				sb.append("	,'' AS ag2_acc_name  \n");
				sb.append("	,'' AS ag2_acc_status  \n");
				sb.append("	,'' AS ag3_id  \n");
				sb.append("	,'' AS ag3_nickname  \n");
				sb.append("	,'' AS ag3_acc_name  \n");
				sb.append("	,'' AS ag3_acc_status \n");
				sb.append("	,'' AS ag4_id  \n");
				sb.append("	,'' AS ag4_nickname  \n");
				sb.append("	,'' AS ag4_acc_name  \n");
				sb.append("	,'' AS ag4_acc_status  \n");
				sb.append("	,'' AS ag5_id  \n");
				sb.append("	,'' AS ag5_nickname  \n");
				sb.append("	,'' AS ag5_acc_name  \n");
				sb.append("	,'' AS ag5_acc_status  \n");
				sb.append("	,'' AS ag6_id  \n");
				sb.append("	,'' AS ag6_nickname  \n");
				sb.append("	,'' AS ag6_acc_name  \n");
				sb.append("	,'' AS ag6_acc_status  \n");
				sb.append("	,'' AS ag7_id  \n");
				sb.append("	,'' AS ag7_nickname  \n");
				sb.append("	,'' AS ag7_acc_name  \n");
				sb.append("	,'' AS ag7_acc_status  \n");
				sb.append("	,'' AS ag8_id  \n");
				sb.append("	,'' AS ag8_nickname  \n");
				sb.append("	,'' AS ag8_acc_name  \n");
				sb.append("	,'' AS ag8_acc_status  \n");
				sb.append("	,'' AS ag9_id  \n");
				sb.append("	,'' AS ag9_nickname  \n");
				sb.append("	,'' AS ag9_acc_name  \n");
				sb.append("	,'' AS ag9_acc_status  \n");
				sb.append("	,'' AS ag10_id  \n");
				sb.append("	,'' AS ag10_nickname  \n");
				sb.append("	,'' AS ag10_acc_name  \n");
				sb.append("	,'' AS ag10_acc_status  \n");
				sb.append("	,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g1_com  \n");
				sb.append(
						"	,CASE WHEN A.acc_level_type=2 THEN I1.current_max_ratio WHEN A.acc_level_type=3 THEN I1.upper_min_ratio ELSE -1 END AS g1_sc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=4 THEN I1.upper_min_ratio ELSE -1 END AS g1_bc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=5 THEN I1.upper_min_ratio ELSE -1 END AS g1_co  \n");
				sb.append("	,CASE WHEN A.acc_level_type=6 THEN I1.upper_min_ratio ELSE -1 END AS g1_sa  \n");
				sb.append("	,CASE WHEN A.acc_level_type=7 THEN I1.upper_min_ratio ELSE -1 END AS g1_ag  \n");
				sb.append("	,CASE WHEN A.acc_level_type=8 THEN I1.upper_min_ratio ELSE -1 END AS g1_ag1  \n");
				sb.append("	,CASE WHEN A.acc_level_type=9 THEN I1.upper_min_ratio ELSE -1 END AS g1_ag2 \n");
				sb.append("	,CASE WHEN A.acc_level_type=10 THEN I1.upper_min_ratio ELSE -1 END AS g1_ag3  \n");
				sb.append("	,CASE WHEN A.acc_level_type=11 THEN I1.upper_min_ratio ELSE -1 END AS g1_ag4  \n");
				sb.append("	,CASE WHEN A.acc_level_type=12 THEN I1.upper_min_ratio ELSE -1 END AS g1_ag5  \n");
				sb.append("	,CASE WHEN A.acc_level_type=13 THEN I1.upper_min_ratio ELSE -1 END AS g1_ag6  \n");
				sb.append("	,CASE WHEN A.acc_level_type=14 THEN I1.upper_min_ratio ELSE -1 END AS g1_ag7  \n");
				sb.append("	,CASE WHEN A.acc_level_type=15 THEN I1.upper_min_ratio ELSE -1 END AS g1_ag8  \n");
				sb.append("	,CASE WHEN A.acc_level_type=16 THEN I1.upper_min_ratio ELSE -1 END AS g1_ag9  \n");
				sb.append("	,-1 AS g1_ag10  \n");
				sb.append("	,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g2_com  \n");
				sb.append(
						"	,CASE WHEN A.acc_level_type=2 THEN I2.current_max_ratio WHEN A.acc_level_type=3 THEN I2.upper_min_ratio ELSE -1 END AS g2_sc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=4 THEN I2.upper_min_ratio ELSE -1 END AS g2_bc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=5 THEN I2.upper_min_ratio ELSE -1 END AS g2_co  \n");
				sb.append("	,CASE WHEN A.acc_level_type=6 THEN I2.upper_min_ratio ELSE -1 END AS g2_sa  \n");
				sb.append("	,CASE WHEN A.acc_level_type=7 THEN I1.upper_min_ratio ELSE -1 END AS g2_ag  \n");
				sb.append("	,CASE WHEN A.acc_level_type=8 THEN I1.upper_min_ratio ELSE -1 END AS g2_ag1  \n");
				sb.append("	,CASE WHEN A.acc_level_type=9 THEN I1.upper_min_ratio ELSE -1 END AS g2_ag2 \n");
				sb.append("	,CASE WHEN A.acc_level_type=10 THEN I1.upper_min_ratio ELSE -1 END AS g2_ag3  \n");
				sb.append("	,CASE WHEN A.acc_level_type=11 THEN I1.upper_min_ratio ELSE -1 END AS g2_ag4  \n");
				sb.append("	,CASE WHEN A.acc_level_type=12 THEN I1.upper_min_ratio ELSE -1 END AS g2_ag5  \n");
				sb.append("	,CASE WHEN A.acc_level_type=13 THEN I1.upper_min_ratio ELSE -1 END AS g2_ag6  \n");
				sb.append("	,CASE WHEN A.acc_level_type=14 THEN I1.upper_min_ratio ELSE -1 END AS g2_ag7  \n");
				sb.append("	,CASE WHEN A.acc_level_type=15 THEN I1.upper_min_ratio ELSE -1 END AS g2_ag8  \n");
				sb.append("	,CASE WHEN A.acc_level_type=16 THEN I1.upper_min_ratio ELSE -1 END AS g2_ag9  \n");
				sb.append("	,-1 AS g2_ag10  \n");
				sb.append("	,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g3_com  \n");
				sb.append(
						"	,CASE WHEN A.acc_level_type=2 THEN I3.current_max_ratio WHEN A.acc_level_type=3 THEN I3.upper_min_ratio ELSE -1 END AS g3_sc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=4 THEN I3.upper_min_ratio ELSE -1 END AS g3_bc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=5 THEN I3.upper_min_ratio ELSE -1 END AS g3_co  \n");
				sb.append("	,CASE WHEN A.acc_level_type=6 THEN I3.upper_min_ratio ELSE -1 END AS g3_sa  \n");
				sb.append("	,CASE WHEN A.acc_level_type=7 THEN I1.upper_min_ratio ELSE -1 END AS g3_ag  \n");
				sb.append("	,CASE WHEN A.acc_level_type=8 THEN I1.upper_min_ratio ELSE -1 END AS g3_ag1  \n");
				sb.append("	,CASE WHEN A.acc_level_type=9 THEN I1.upper_min_ratio ELSE -1 END AS g3_ag2 \n");
				sb.append("	,CASE WHEN A.acc_level_type=10 THEN I1.upper_min_ratio ELSE -1 END AS g3_ag3  \n");
				sb.append("	,CASE WHEN A.acc_level_type=11 THEN I1.upper_min_ratio ELSE -1 END AS g3_ag4  \n");
				sb.append("	,CASE WHEN A.acc_level_type=12 THEN I1.upper_min_ratio ELSE -1 END AS g3_ag5  \n");
				sb.append("	,CASE WHEN A.acc_level_type=13 THEN I1.upper_min_ratio ELSE -1 END AS g3_ag6  \n");
				sb.append("	,CASE WHEN A.acc_level_type=14 THEN I1.upper_min_ratio ELSE -1 END AS g3_ag7  \n");
				sb.append("	,CASE WHEN A.acc_level_type=15 THEN I1.upper_min_ratio ELSE -1 END AS g3_ag8  \n");
				sb.append("	,CASE WHEN A.acc_level_type=16 THEN I1.upper_min_ratio ELSE -1 END AS g3_ag9  \n");
				sb.append("	,-1 AS g3_ag10  \n");
				sb.append("	,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g4_com  \n");
				sb.append(
						"	,CASE WHEN A.acc_level_type=2 THEN I4.current_max_ratio WHEN A.acc_level_type=3 THEN I4.upper_min_ratio ELSE -1 END AS g4_sc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=4 THEN I4.upper_min_ratio ELSE -1 END AS g4_bc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=5 THEN I4.upper_min_ratio ELSE -1 END AS g4_co  \n");
				sb.append("	,CASE WHEN A.acc_level_type=6 THEN I4.upper_min_ratio ELSE -1 END AS g4_sa  \n");
				sb.append("	,CASE WHEN A.acc_level_type=7 THEN I1.upper_min_ratio ELSE -1 END AS g4_ag  \n");
				sb.append("	,CASE WHEN A.acc_level_type=8 THEN I1.upper_min_ratio ELSE -1 END AS g4_ag1  \n");
				sb.append("	,CASE WHEN A.acc_level_type=9 THEN I1.upper_min_ratio ELSE -1 END AS g4_ag2 \n");
				sb.append("	,CASE WHEN A.acc_level_type=10 THEN I1.upper_min_ratio ELSE -1 END AS g4_ag3  \n");
				sb.append("	,CASE WHEN A.acc_level_type=11 THEN I1.upper_min_ratio ELSE -1 END AS g4_ag4  \n");
				sb.append("	,CASE WHEN A.acc_level_type=12 THEN I1.upper_min_ratio ELSE -1 END AS g4_ag5  \n");
				sb.append("	,CASE WHEN A.acc_level_type=13 THEN I1.upper_min_ratio ELSE -1 END AS g4_ag6  \n");
				sb.append("	,CASE WHEN A.acc_level_type=14 THEN I1.upper_min_ratio ELSE -1 END AS g4_ag7  \n");
				sb.append("	,CASE WHEN A.acc_level_type=15 THEN I1.upper_min_ratio ELSE -1 END AS g4_ag8  \n");
				sb.append("	,CASE WHEN A.acc_level_type=16 THEN I1.upper_min_ratio ELSE -1 END AS g4_ag9  \n");
				sb.append("	,-1 AS g4_ag10  \n");
				sb.append("	,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g5_com  \n");
				sb.append(
						"	,CASE WHEN A.acc_level_type=2 THEN I5.current_max_ratio WHEN A.acc_level_type=3 THEN I5.upper_min_ratio ELSE -1 END AS g5_sc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=4 THEN I5.upper_min_ratio ELSE -1 END AS g5_bc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=5 THEN I5.upper_min_ratio ELSE -1 END AS g5_co  \n");
				sb.append("	,CASE WHEN A.acc_level_type=6 THEN I5.upper_min_ratio ELSE -1 END AS g5_sa \n");
				sb.append("	,CASE WHEN A.acc_level_type=7 THEN I1.upper_min_ratio ELSE -1 END AS g5_ag  \n");
				sb.append("	,CASE WHEN A.acc_level_type=8 THEN I1.upper_min_ratio ELSE -1 END AS g5_ag1  \n");
				sb.append("	,CASE WHEN A.acc_level_type=9 THEN I1.upper_min_ratio ELSE -1 END AS g5_ag2 \n");
				sb.append("	,CASE WHEN A.acc_level_type=10 THEN I1.upper_min_ratio ELSE -1 END AS g5_ag3  \n");
				sb.append("	,CASE WHEN A.acc_level_type=11 THEN I1.upper_min_ratio ELSE -1 END AS g5_ag4  \n");
				sb.append("	,CASE WHEN A.acc_level_type=12 THEN I1.upper_min_ratio ELSE -1 END AS g5_ag5  \n");
				sb.append("	,CASE WHEN A.acc_level_type=13 THEN I1.upper_min_ratio ELSE -1 END AS g5_ag6  \n");
				sb.append("	,CASE WHEN A.acc_level_type=14 THEN I1.upper_min_ratio ELSE -1 END AS g5_ag7  \n");
				sb.append("	,CASE WHEN A.acc_level_type=15 THEN I1.upper_min_ratio ELSE -1 END AS g5_ag8  \n");
				sb.append("	,CASE WHEN A.acc_level_type=16 THEN I1.upper_min_ratio ELSE -1 END AS g5_ag9   \n");
				sb.append("	,-1 AS g5_ag10  \n");
				sb.append("	,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g1_com  \n");
				sb.append(
						"	,CASE WHEN A.acc_level_type=2 THEN I1.next_current_max_ratio WHEN A.acc_level_type=3 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_sc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=4 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_bc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=5 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_co  \n");
				sb.append("	,CASE WHEN A.acc_level_type=6 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_sa \n");
				sb.append("	,CASE WHEN A.acc_level_type=7 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag  \n");
				sb.append("	,CASE WHEN A.acc_level_type=8 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag1  \n");
				sb.append("	,CASE WHEN A.acc_level_type=9 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag2 \n");
				sb.append("	,CASE WHEN A.acc_level_type=10 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag3  \n");
				sb.append("	,CASE WHEN A.acc_level_type=11 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag4  \n");
				sb.append("	,CASE WHEN A.acc_level_type=12 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag5  \n");
				sb.append("	,CASE WHEN A.acc_level_type=13 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag6  \n");
				sb.append("	,CASE WHEN A.acc_level_type=14 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag7  \n");
				sb.append("	,CASE WHEN A.acc_level_type=15 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag8  \n");
				sb.append("	,CASE WHEN A.acc_level_type=16 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag9    \n");
				sb.append("	,-1 as next_g1_ag10  \n");
				sb.append("	,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g2_com  \n");
				sb.append(
						"	,CASE WHEN A.acc_level_type=2 THEN I2.next_current_max_ratio WHEN A.acc_level_type=3 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_sc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=4 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_bc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=5 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_co  \n");
				sb.append("	,CASE WHEN A.acc_level_type=6 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_sa \n");
				sb.append("	,CASE WHEN A.acc_level_type=7 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag  \n");
				sb.append("	,CASE WHEN A.acc_level_type=8 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag1  \n");
				sb.append("	,CASE WHEN A.acc_level_type=9 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag2 \n");
				sb.append("	,CASE WHEN A.acc_level_type=10 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag3  \n");
				sb.append("	,CASE WHEN A.acc_level_type=11 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag4  \n");
				sb.append("	,CASE WHEN A.acc_level_type=12 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag5  \n");
				sb.append("	,CASE WHEN A.acc_level_type=13 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag6  \n");
				sb.append("	,CASE WHEN A.acc_level_type=14 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag7  \n");
				sb.append("	,CASE WHEN A.acc_level_type=15 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag8  \n");
				sb.append("	,CASE WHEN A.acc_level_type=16 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag9     \n");
				sb.append("	,-1 as next_g2_ag10  \n");
				sb.append("	,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g3_com  \n");
				sb.append(
						"	,CASE WHEN A.acc_level_type=2 THEN I3.next_current_max_ratio WHEN A.acc_level_type=3 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_sc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=4 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_bc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=5 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_co  \n");
				sb.append("	,CASE WHEN A.acc_level_type=6 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_sa  \n");
				sb.append("	,CASE WHEN A.acc_level_type=7 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag  \n");
				sb.append("	,CASE WHEN A.acc_level_type=8 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag1  \n");
				sb.append("	,CASE WHEN A.acc_level_type=9 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag2 \n");
				sb.append("	,CASE WHEN A.acc_level_type=10 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag3  \n");
				sb.append("	,CASE WHEN A.acc_level_type=11 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag4  \n");
				sb.append("	,CASE WHEN A.acc_level_type=12 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag5  \n");
				sb.append("	,CASE WHEN A.acc_level_type=13 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag6  \n");
				sb.append("	,CASE WHEN A.acc_level_type=14 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag7  \n");
				sb.append("	,CASE WHEN A.acc_level_type=15 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag8  \n");
				sb.append("	,CASE WHEN A.acc_level_type=16 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag9    \n");
				sb.append("	,-1 as next_g3_ag10  \n");
				sb.append("	,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g4_com  \n");
				sb.append(
						"	,CASE WHEN A.acc_level_type=2 THEN I4.next_current_max_ratio WHEN A.acc_level_type=3 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_sc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=4 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_bc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=5 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_co  \n");
				sb.append("	,CASE WHEN A.acc_level_type=6 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_sa \n");
				sb.append("	,CASE WHEN A.acc_level_type=7 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag  \n");
				sb.append("	,CASE WHEN A.acc_level_type=8 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag1  \n");
				sb.append("	,CASE WHEN A.acc_level_type=9 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag2 \n");
				sb.append("	,CASE WHEN A.acc_level_type=10 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag3  \n");
				sb.append("	,CASE WHEN A.acc_level_type=11 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag4  \n");
				sb.append("	,CASE WHEN A.acc_level_type=12 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag5  \n");
				sb.append("	,CASE WHEN A.acc_level_type=13 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag6  \n");
				sb.append("	,CASE WHEN A.acc_level_type=14 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag7  \n");
				sb.append("	,CASE WHEN A.acc_level_type=15 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag8  \n");
				sb.append("	,CASE WHEN A.acc_level_type=16 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag9     \n");
				sb.append("	,-1 as next_g4_ag10  \n");
				sb.append("	,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g5_com  \n");
				sb.append(
						"	,CASE WHEN A.acc_level_type=2 THEN I5.next_current_max_ratio WHEN A.acc_level_type=3 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_sc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=4 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_bc  \n");
				sb.append("	,CASE WHEN A.acc_level_type=5 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_co  \n");
				sb.append("	,CASE WHEN A.acc_level_type=6 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_sa \n");
				sb.append("	,CASE WHEN A.acc_level_type=7 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag  \n");
				sb.append("	,CASE WHEN A.acc_level_type=8 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag1  \n");
				sb.append("	,CASE WHEN A.acc_level_type=9 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag2 \n");
				sb.append("	,CASE WHEN A.acc_level_type=10 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag3  \n");
				sb.append("	,CASE WHEN A.acc_level_type=11 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag4  \n");
				sb.append("	,CASE WHEN A.acc_level_type=12 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag5  \n");
				sb.append("	,CASE WHEN A.acc_level_type=13 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag6  \n");
				sb.append("	,CASE WHEN A.acc_level_type=14 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag7  \n");
				sb.append("	,CASE WHEN A.acc_level_type=15 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag8  \n");
				sb.append("	,CASE WHEN A.acc_level_type=16 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag9     \n");
				sb.append("	,-1 as next_g5_ag10 \n");
				sb.append("	, K.update_status  \n");
			} else if (type == ACC_LEVEL_BC) {
				sb.append("    ,J.group_text \n");
				sb.append("    ,CASE WHEN B.com IS NULL THEN 0 ELSE B.com END AS 'com' \n");
				sb.append("    ,CASE WHEN B.sc IS NULL THEN 0 ELSE B.sc END AS 'sc' \n");
				sb.append("    ,CASE WHEN B.bc IS NULL THEN 0 ELSE B.bc END AS 'bc' \n");
				sb.append("    ,CASE WHEN B.co IS NULL THEN 0 ELSE B.co END AS 'co' \n");
				sb.append("    ,CASE WHEN B.sa IS NULL THEN 0 ELSE B.sa END AS 'sa' \n");
				sb.append("    ,CASE WHEN B.ag IS NULL THEN 0 ELSE B.ag END AS 'ag' \n");
				sb.append("    ,CASE WHEN B.mem IS NULL THEN 0 ELSE B.mem END AS 'mem' \n");

				sb.append(",'大股東' AS acc_type \n");
				sb.append(",CASE WHEN level_type<=2 THEN sc_id ELSE '' END AS sc_id \n");
				sb.append(
						",CASE WHEN level_type<=2 THEN (CASE WHEN TRIM(sc_nickname)='' THEN sc_acc_name ELSE sc_nickname END) ELSE '' END AS sc_nickname \n");
				sb.append(",CASE WHEN level_type<2 THEN sc_acc_name ELSE '' END AS sc_acc_name \n");
				sb.append(",CASE WHEN level_type<2 THEN sc_acc_status ELSE '' END AS sc_acc_status \n");
				sb.append(",'' AS bc_id \n");
				sb.append(",'' AS bc_nickname \n");
				sb.append(",'' AS bc_acc_name \n");
				sb.append(",'' AS bc_acc_status \n");
				sb.append(",'' AS co_id \n");
				sb.append(",'' AS co_nickname \n");
				sb.append(",'' AS co_acc_name \n");
				sb.append(",'' AS co_acc_status\n");
				sb.append(",'' AS sa_id \n");
				sb.append(",'' AS sa_nickname \n");
				sb.append(",'' AS sa_acc_name \n");
				sb.append(",'' AS sa_acc_status\n");
				sb.append(",'' AS ag_id \n");
				sb.append(",'' AS ag_nickname \n");
				sb.append(",'' AS ag_acc_name \n");
				sb.append(",'' AS ag_acc_status\n");

				sb.append(",'' AS ag1_id \n");
				sb.append(",'' AS ag1_nickname \n");
				sb.append(",'' AS ag1_acc_name \n");
				sb.append(",'' AS ag1_acc_status \n");
				sb.append(",'' AS ag2_id \n");
				sb.append(",'' AS ag2_nickname \n");
				sb.append(",'' AS ag2_acc_name \n");
				sb.append(",'' AS ag2_acc_status \n");
				sb.append(",'' AS ag3_id \n");
				sb.append(",'' AS ag3_nickname \n");
				sb.append(",'' AS ag3_acc_name \n");
				sb.append(",'' AS ag3_acc_status\n");
				sb.append(",'' AS ag4_id \n");
				sb.append(",'' AS ag4_nickname \n");
				sb.append(",'' AS ag4_acc_name \n");
				sb.append(",'' AS ag4_acc_status \n");
				sb.append(",'' AS ag5_id \n");
				sb.append(",'' AS ag5_nickname \n");
				sb.append(",'' AS ag5_acc_name \n");
				sb.append(",'' AS ag5_acc_status \n");
				sb.append(",'' AS ag6_id \n");
				sb.append(",'' AS ag6_nickname \n");
				sb.append(",'' AS ag6_acc_name \n");
				sb.append(",'' AS ag6_acc_status \n");
				sb.append(",'' AS ag7_id \n");
				sb.append(",'' AS ag7_nickname \n");
				sb.append(",'' AS ag7_acc_name \n");
				sb.append(",'' AS ag7_acc_status \n");
				sb.append(",'' AS ag8_id \n");
				sb.append(",'' AS ag8_nickname \n");
				sb.append(",'' AS ag8_acc_name \n");
				sb.append(",'' AS ag8_acc_status \n");
				sb.append(",'' AS ag9_id \n");
				sb.append(",'' AS ag9_nickname \n");
				sb.append(",'' AS ag9_acc_name \n");
				sb.append(",'' AS ag9_acc_status \n");
				sb.append(",'' AS ag10_id \n");
				sb.append(",'' AS ag10_nickname \n");
				sb.append(",'' AS ag10_acc_name \n");
				sb.append(",'' AS ag10_acc_status \n");

				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g1_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I1.current_max_ratio WHEN A.acc_level_type=3 THEN I1.upper_min_ratio ELSE -1 END AS g1_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I1.upper_min_ratio ELSE -1 END AS g1_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I1.upper_min_ratio ELSE -1 END AS g1_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I1.upper_min_ratio ELSE -1 END AS g1_sa \n");
				sb.append("    ,-1 AS g1_ag \n");
				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g2_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I2.current_max_ratio WHEN A.acc_level_type=3 THEN I2.upper_min_ratio ELSE -1 END AS g2_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I2.upper_min_ratio ELSE -1 END AS g2_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I2.upper_min_ratio ELSE -1 END AS g2_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I2.upper_min_ratio ELSE -1 END AS g2_sa \n");
				sb.append("    ,-1 AS g2_ag \n");
				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g3_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I3.current_max_ratio WHEN A.acc_level_type=3 THEN I3.upper_min_ratio ELSE -1 END AS g3_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I3.upper_min_ratio ELSE -1 END AS g3_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I3.upper_min_ratio ELSE -1 END AS g3_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I3.upper_min_ratio ELSE -1 END AS g3_sa \n");
				sb.append("    ,-1 AS g3_ag \n");
				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g4_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I4.current_max_ratio WHEN A.acc_level_type=3 THEN I4.upper_min_ratio ELSE -1 END AS g4_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I4.upper_min_ratio ELSE -1 END AS g4_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I4.upper_min_ratio ELSE -1 END AS g4_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I4.upper_min_ratio ELSE -1 END AS g4_sa \n");
				sb.append("    ,-1 AS g4_ag \n");
				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g5_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I5.current_max_ratio WHEN A.acc_level_type=3 THEN I5.upper_min_ratio ELSE -1 END AS g5_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I5.upper_min_ratio ELSE -1 END AS g5_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I5.upper_min_ratio ELSE -1 END AS g5_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I5.upper_min_ratio ELSE -1 END AS g5_sa \n");
				sb.append("    ,-1 AS g5_ag \n");

				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g1_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I1.next_current_max_ratio WHEN A.acc_level_type=3 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_sa \n");
				sb.append(",-1 as next_g1_ag \n");
				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g2_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I2.next_current_max_ratio WHEN A.acc_level_type=3 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_sa \n");
				sb.append(",-1 as next_g2_ag \n");
				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g3_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I3.next_current_max_ratio WHEN A.acc_level_type=3 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_sa \n");
				sb.append(",-1 as next_g3_ag \n");
				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g4_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I4.next_current_max_ratio WHEN A.acc_level_type=3 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_sa \n");
				sb.append(",-1 as next_g4_ag \n");
				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g5_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I5.next_current_max_ratio WHEN A.acc_level_type=3 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_sa \n");
				sb.append(",-1 as next_g5_ag \n");

				sb.append("    , K.update_status \n");
			} else if (type == ACC_LEVEL_CO) {
				sb.append("    ,J.group_text \n");
				sb.append("    ,CASE WHEN B.com IS NULL THEN 0 ELSE B.com END AS 'com' \n");
				sb.append("    ,CASE WHEN B.sc IS NULL THEN 0 ELSE B.sc END AS 'sc' \n");
				sb.append("    ,CASE WHEN B.bc IS NULL THEN 0 ELSE B.bc END AS 'bc' \n");
				sb.append("    ,CASE WHEN B.co IS NULL THEN 0 ELSE B.co END AS 'co' \n");
				sb.append("    ,CASE WHEN B.sa IS NULL THEN 0 ELSE B.sa END AS 'sa' \n");
				sb.append("    ,CASE WHEN B.ag IS NULL THEN 0 ELSE B.ag END AS 'ag' \n");
				sb.append("    ,CASE WHEN B.mem IS NULL THEN 0 ELSE B.mem END AS 'mem' \n");
				sb.append("    ,'股東' AS acc_type \n");
				sb.append("    ,CASE WHEN level_type<=2 THEN sc_id ELSE '' END AS sc_id \n");
				sb.append(
						"    ,CASE WHEN level_type<=2 THEN (CASE WHEN TRIM(sc_nickname)='' THEN sc_acc_name ELSE sc_nickname END) ELSE '' END AS sc_nickname \n");
				sb.append("    ,CASE WHEN level_type<2 THEN sc_acc_name ELSE '' END AS sc_acc_name \n");
				sb.append(",CASE WHEN level_type<2 THEN sc_acc_status ELSE '' END AS sc_acc_status \n");
				sb.append("    ,CASE WHEN level_type<=3 THEN bc_id ELSE '' END AS bc_id \n");
				sb.append(
						"    ,CASE WHEN level_type<=3 THEN (CASE WHEN TRIM(bc_nickname)='' THEN bc_acc_name ELSE bc_nickname END) ELSE '' END AS bc_nickname \n");
				sb.append("    ,CASE WHEN level_type<3 THEN bc_acc_name ELSE '' END AS bc_acc_name \n");
				sb.append(",CASE WHEN level_type<3 THEN bc_acc_status ELSE '' END AS bc_acc_status \n");
				sb.append(",'' AS co_id \n");
				sb.append(",'' AS co_nickname \n");
				sb.append(",'' AS co_acc_name \n");
				sb.append(",'' AS co_acc_status\n");
				sb.append(",'' AS sa_id \n");
				sb.append(",'' AS sa_nickname \n");
				sb.append(",'' AS sa_acc_name \n");
				sb.append(",'' AS sa_acc_status\n");
				sb.append(",'' AS ag_id \n");
				sb.append(",'' AS ag_nickname \n");
				sb.append(",'' AS ag_acc_name \n");
				sb.append(",'' AS ag_acc_status\n");

				sb.append(",'' AS ag1_id \n");
				sb.append(",'' AS ag1_nickname \n");
				sb.append(",'' AS ag1_acc_name \n");
				sb.append(",'' AS ag1_acc_status \n");
				sb.append(",'' AS ag2_id \n");
				sb.append(",'' AS ag2_nickname \n");
				sb.append(",'' AS ag2_acc_name \n");
				sb.append(",'' AS ag2_acc_status \n");
				sb.append(",'' AS ag3_id \n");
				sb.append(",'' AS ag3_nickname \n");
				sb.append(",'' AS ag3_acc_name \n");
				sb.append(",'' AS ag3_acc_status\n");
				sb.append(",'' AS ag4_id \n");
				sb.append(",'' AS ag4_nickname \n");
				sb.append(",'' AS ag4_acc_name \n");
				sb.append(",'' AS ag4_acc_status \n");
				sb.append(",'' AS ag5_id \n");
				sb.append(",'' AS ag5_nickname \n");
				sb.append(",'' AS ag5_acc_name \n");
				sb.append(",'' AS ag5_acc_status \n");
				sb.append(",'' AS ag6_id \n");
				sb.append(",'' AS ag6_nickname \n");
				sb.append(",'' AS ag6_acc_name \n");
				sb.append(",'' AS ag6_acc_status \n");
				sb.append(",'' AS ag7_id \n");
				sb.append(",'' AS ag7_nickname \n");
				sb.append(",'' AS ag7_acc_name \n");
				sb.append(",'' AS ag7_acc_status \n");
				sb.append(",'' AS ag8_id \n");
				sb.append(",'' AS ag8_nickname \n");
				sb.append(",'' AS ag8_acc_name \n");
				sb.append(",'' AS ag8_acc_status \n");
				sb.append(",'' AS ag9_id \n");
				sb.append(",'' AS ag9_nickname \n");
				sb.append(",'' AS ag9_acc_name \n");
				sb.append(",'' AS ag9_acc_status \n");
				sb.append(",'' AS ag10_id \n");
				sb.append(",'' AS ag10_nickname \n");
				sb.append(",'' AS ag10_acc_name \n");
				sb.append(",'' AS ag10_acc_status \n");

				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g1_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I1.current_max_ratio WHEN A.acc_level_type=3 THEN I1.upper_min_ratio ELSE -1 END AS g1_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I1.upper_min_ratio ELSE -1 END AS g1_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I1.upper_min_ratio ELSE -1 END AS g1_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I1.upper_min_ratio ELSE -1 END AS g1_sa \n");
				sb.append("    ,-1 AS g1_ag \n");
				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g2_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I2.current_max_ratio WHEN A.acc_level_type=3 THEN I2.upper_min_ratio ELSE -1 END AS g2_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I2.upper_min_ratio ELSE -1 END AS g2_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I2.upper_min_ratio ELSE -1 END AS g2_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I2.upper_min_ratio ELSE -1 END AS g2_sa \n");
				sb.append("    ,-1 AS g2_ag \n");
				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g3_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I3.current_max_ratio WHEN A.acc_level_type=3 THEN I3.upper_min_ratio ELSE -1 END AS g3_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I3.upper_min_ratio ELSE -1 END AS g3_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I3.upper_min_ratio ELSE -1 END AS g3_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I3.upper_min_ratio ELSE -1 END AS g3_sa \n");
				sb.append("    ,-1 AS g3_ag \n");
				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g4_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I4.current_max_ratio WHEN A.acc_level_type=3 THEN I4.upper_min_ratio ELSE -1 END AS g4_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I4.upper_min_ratio ELSE -1 END AS g4_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I4.upper_min_ratio ELSE -1 END AS g4_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I4.upper_min_ratio ELSE -1 END AS g4_sa \n");
				sb.append("    ,-1 AS g4_ag \n");
				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g5_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I5.current_max_ratio WHEN A.acc_level_type=3 THEN I5.upper_min_ratio ELSE -1 END AS g5_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I5.upper_min_ratio ELSE -1 END AS g5_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I5.upper_min_ratio ELSE -1 END AS g5_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I5.upper_min_ratio ELSE -1 END AS g5_sa \n");
				sb.append("    ,-1 AS g5_ag \n");

				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g1_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I1.next_current_max_ratio WHEN A.acc_level_type=3 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_sa \n");
				sb.append(",-1 as next_g1_ag \n");
				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g2_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I2.next_current_max_ratio WHEN A.acc_level_type=3 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_sa \n");
				sb.append(",-1 as next_g2_ag \n");
				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g3_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I3.next_current_max_ratio WHEN A.acc_level_type=3 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_sa \n");
				sb.append(",-1 as next_g3_ag \n");
				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g4_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I4.next_current_max_ratio WHEN A.acc_level_type=3 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_sa \n");
				sb.append(",-1 as next_g4_ag \n");
				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g5_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I5.next_current_max_ratio WHEN A.acc_level_type=3 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_sa \n");
				sb.append(",-1 as next_g5_ag \n");

				sb.append("    , K.update_status \n");
			} else if (type == ACC_LEVEL_SA) {
				sb.append("    ,J.group_text \n");
				sb.append("    ,CASE WHEN B.com IS NULL THEN 0 ELSE B.com END AS 'com' \n");
				sb.append("    ,CASE WHEN B.sc IS NULL THEN 0 ELSE B.sc END AS 'sc' \n");
				sb.append("    ,CASE WHEN B.bc IS NULL THEN 0 ELSE B.bc END AS 'bc' \n");
				sb.append("    ,CASE WHEN B.co IS NULL THEN 0 ELSE B.co END AS 'co' \n");
				sb.append("    ,CASE WHEN B.sa IS NULL THEN 0 ELSE B.sa END AS 'sa' \n");
				sb.append("    ,CASE WHEN B.ag IS NULL THEN 0 ELSE B.ag END AS 'ag' \n");
				sb.append("    ,CASE WHEN B.mem IS NULL THEN 0 ELSE B.mem END AS 'mem' \n");
				sb.append("    ,'總代理' AS acc_type \n");
				sb.append("    ,CASE WHEN level_type<=2 THEN sc_id ELSE '' END AS sc_id \n");
				sb.append(
						"    ,CASE WHEN level_type<=2 THEN (CASE WHEN TRIM(sc_nickname)='' THEN sc_acc_name ELSE sc_nickname END) ELSE '' END AS sc_nickname \n");
				sb.append("    ,CASE WHEN level_type<2 THEN sc_acc_name ELSE '' END AS sc_acc_name \n");
				sb.append(",CASE WHEN level_type<2 THEN sc_acc_status ELSE '' END AS sc_acc_status \n");
				sb.append("    ,CASE WHEN level_type<=3 THEN bc_id ELSE '' END AS bc_id \n");
				sb.append(
						"    ,CASE WHEN level_type<=3 THEN (CASE WHEN TRIM(bc_nickname)='' THEN bc_acc_name ELSE bc_nickname END) ELSE '' END AS bc_nickname \n");
				sb.append("    ,CASE WHEN level_type<3 THEN bc_acc_name ELSE '' END AS bc_acc_name \n");
				sb.append(",CASE WHEN level_type<3 THEN bc_acc_status ELSE '' END AS bc_acc_status \n");
				sb.append("    ,CASE WHEN level_type<=4 THEN co_id ELSE '' END AS co_id \n");
				sb.append(
						"    ,CASE WHEN level_type<=4 THEN (CASE WHEN TRIM(co_nickname)='' THEN co_acc_name ELSE co_nickname END) ELSE '' END AS co_nickname \n");
				sb.append("    ,CASE WHEN level_type<4 THEN co_acc_name ELSE '' END AS co_acc_name \n");
				sb.append(",CASE WHEN level_type<4 THEN co_acc_status ELSE '' END AS co_acc_status \n");
				sb.append(",'' AS sa_id \n");
				sb.append(",'' AS sa_nickname \n");
				sb.append(",'' AS sa_acc_name \n");
				sb.append(",'' AS sa_acc_status\n");
				sb.append(",'' AS ag_id \n");
				sb.append(",'' AS ag_nickname \n");
				sb.append(",'' AS ag_acc_name \n");
				sb.append(",'' AS ag_acc_status\n");

				sb.append(",'' AS ag1_id \n");
				sb.append(",'' AS ag1_nickname \n");
				sb.append(",'' AS ag1_acc_name \n");
				sb.append(",'' AS ag1_acc_status \n");
				sb.append(",'' AS ag2_id \n");
				sb.append(",'' AS ag2_nickname \n");
				sb.append(",'' AS ag2_acc_name \n");
				sb.append(",'' AS ag2_acc_status \n");
				sb.append(",'' AS ag3_id \n");
				sb.append(",'' AS ag3_nickname \n");
				sb.append(",'' AS ag3_acc_name \n");
				sb.append(",'' AS ag3_acc_status\n");
				sb.append(",'' AS ag4_id \n");
				sb.append(",'' AS ag4_nickname \n");
				sb.append(",'' AS ag4_acc_name \n");
				sb.append(",'' AS ag4_acc_status \n");
				sb.append(",'' AS ag5_id \n");
				sb.append(",'' AS ag5_nickname \n");
				sb.append(",'' AS ag5_acc_name \n");
				sb.append(",'' AS ag5_acc_status \n");
				sb.append(",'' AS ag6_id \n");
				sb.append(",'' AS ag6_nickname \n");
				sb.append(",'' AS ag6_acc_name \n");
				sb.append(",'' AS ag6_acc_status \n");
				sb.append(",'' AS ag7_id \n");
				sb.append(",'' AS ag7_nickname \n");
				sb.append(",'' AS ag7_acc_name \n");
				sb.append(",'' AS ag7_acc_status \n");
				sb.append(",'' AS ag8_id \n");
				sb.append(",'' AS ag8_nickname \n");
				sb.append(",'' AS ag8_acc_name \n");
				sb.append(",'' AS ag8_acc_status \n");
				sb.append(",'' AS ag9_id \n");
				sb.append(",'' AS ag9_nickname \n");
				sb.append(",'' AS ag9_acc_name \n");
				sb.append(",'' AS ag9_acc_status \n");
				sb.append(",'' AS ag10_id \n");
				sb.append(",'' AS ag10_nickname \n");
				sb.append(",'' AS ag10_acc_name \n");
				sb.append(",'' AS ag10_acc_status \n");

				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g1_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I1.current_max_ratio WHEN A.acc_level_type=3 THEN I1.upper_min_ratio ELSE -1 END AS g1_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I1.upper_min_ratio ELSE -1 END AS g1_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I1.upper_min_ratio ELSE -1 END AS g1_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I1.upper_min_ratio ELSE -1 END AS g1_sa \n");
				sb.append("    ,-1 AS g1_ag \n");
				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g2_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I2.current_max_ratio WHEN A.acc_level_type=3 THEN I2.upper_min_ratio ELSE -1 END AS g2_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I2.upper_min_ratio ELSE -1 END AS g2_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I2.upper_min_ratio ELSE -1 END AS g2_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I2.upper_min_ratio ELSE -1 END AS g2_sa \n");
				sb.append("    ,-1 AS g2_ag \n");
				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g3_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I3.current_max_ratio WHEN A.acc_level_type=3 THEN I3.upper_min_ratio ELSE -1 END AS g3_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I3.upper_min_ratio ELSE -1 END AS g3_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I3.upper_min_ratio ELSE -1 END AS g3_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I3.upper_min_ratio ELSE -1 END AS g3_sa \n");
				sb.append("    ,-1 AS g3_ag \n");
				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g4_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I4.current_max_ratio WHEN A.acc_level_type=3 THEN I4.upper_min_ratio ELSE -1 END AS g4_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I4.upper_min_ratio ELSE -1 END AS g4_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I4.upper_min_ratio ELSE -1 END AS g4_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I4.upper_min_ratio ELSE -1 END AS g4_sa \n");
				sb.append("    ,-1 AS g4_ag \n");
				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g5_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I5.current_max_ratio WHEN A.acc_level_type=3 THEN I5.upper_min_ratio ELSE -1 END AS g5_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I5.upper_min_ratio ELSE -1 END AS g5_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I5.upper_min_ratio ELSE -1 END AS g5_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I5.upper_min_ratio ELSE -1 END AS g5_sa \n");
				sb.append("    ,-1 AS g5_ag \n");

				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g1_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I1.next_current_max_ratio WHEN A.acc_level_type=3 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_sa \n");
				sb.append(",-1 as next_g1_ag \n");
				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g2_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I2.next_current_max_ratio WHEN A.acc_level_type=3 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_sa \n");
				sb.append(",-1 as next_g2_ag \n");
				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g3_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I3.next_current_max_ratio WHEN A.acc_level_type=3 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_sa \n");
				sb.append(",-1 as next_g3_ag \n");
				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g4_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I4.next_current_max_ratio WHEN A.acc_level_type=3 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_sa \n");
				sb.append(",-1 as next_g4_ag \n");
				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g5_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I5.next_current_max_ratio WHEN A.acc_level_type=3 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_sa \n");
				sb.append(",-1 as next_g5_ag \n");

				sb.append("    , K.update_status \n");
			} else if (type == ACC_LEVEL_AG) {
				sb.append("    ,J.group_text \n");
				sb.append("    ,CASE WHEN B.com IS NULL THEN 0 ELSE B.com END AS 'com' \n");
				sb.append("    ,CASE WHEN B.sc IS NULL THEN 0 ELSE B.sc END AS 'sc' \n");
				sb.append("    ,CASE WHEN B.bc IS NULL THEN 0 ELSE B.bc END AS 'bc' \n");
				sb.append("    ,CASE WHEN B.co IS NULL THEN 0 ELSE B.co END AS 'co' \n");
				sb.append("    ,CASE WHEN B.sa IS NULL THEN 0 ELSE B.sa END AS 'sa' \n");
				sb.append("    ,CASE WHEN B.ag IS NULL THEN 0 ELSE B.ag END AS 'ag' \n");
				sb.append("    ,CASE WHEN B.mem IS NULL THEN 0 ELSE B.mem END AS 'mem' \n");
				sb.append("    ,'代理' AS acc_type \n");
				sb.append("    ,CASE WHEN level_type<=2 THEN sc_id ELSE '' END AS sc_id \n");
				sb.append(
						"    ,CASE WHEN level_type<=2 THEN (CASE WHEN TRIM(sc_nickname)='' THEN sc_acc_name ELSE sc_nickname END) ELSE '' END AS sc_nickname \n");
				sb.append("    ,CASE WHEN level_type<2 THEN sc_acc_name ELSE '' END AS sc_acc_name \n");
				sb.append(",CASE WHEN level_type<2 THEN sc_acc_status ELSE '' END AS sc_acc_status \n");
				sb.append("    ,CASE WHEN level_type<=3 THEN bc_id ELSE '' END AS bc_id \n");
				sb.append(
						"    ,CASE WHEN level_type<=3 THEN (CASE WHEN TRIM(bc_nickname)='' THEN bc_acc_name ELSE bc_nickname END) ELSE '' END AS bc_nickname \n");
				sb.append("    ,CASE WHEN level_type<3 THEN bc_acc_name ELSE '' END AS bc_acc_name \n");
				sb.append(",CASE WHEN level_type<3 THEN bc_acc_status ELSE '' END AS bc_acc_status \n");
				sb.append("    ,CASE WHEN level_type<=4 THEN co_id ELSE '' END AS co_id \n");
				sb.append(
						"    ,CASE WHEN level_type<=4 THEN (CASE WHEN TRIM(co_nickname)='' THEN co_acc_name ELSE co_nickname END) ELSE '' END AS co_nickname \n");
				sb.append("    ,CASE WHEN level_type<4 THEN co_acc_name ELSE '' END AS co_acc_name \n");
				sb.append(",CASE WHEN level_type<4 THEN co_acc_status ELSE '' END AS co_acc_status \n");
				sb.append("    ,CASE WHEN level_type<=5 THEN sa_id ELSE '' END AS sa_id \n");
				sb.append(
						"    ,CASE WHEN level_type<=5 THEN (CASE WHEN TRIM(sa_nickname)='' THEN sa_acc_name ELSE sa_nickname END)ELSE '' END AS sa_nickname \n");
				sb.append("    ,CASE WHEN level_type<5 THEN sa_acc_name ELSE '' END AS sa_acc_name \n");
				sb.append(",CASE WHEN level_type<5 THEN sa_acc_status ELSE '' END AS sa_acc_status \n");
				sb.append(",'' AS ag_id \n");
				sb.append(",'' AS ag_nickname \n");
				sb.append(",'' AS ag_acc_name \n");
				sb.append(",'' AS ag_acc_status\n");

				sb.append(",'' AS ag1_id \n");
				sb.append(",'' AS ag1_nickname \n");
				sb.append(",'' AS ag1_acc_name \n");
				sb.append(",'' AS ag1_acc_status \n");
				sb.append(",'' AS ag2_id \n");
				sb.append(",'' AS ag2_nickname \n");
				sb.append(",'' AS ag2_acc_name \n");
				sb.append(",'' AS ag2_acc_status \n");
				sb.append(",'' AS ag3_id \n");
				sb.append(",'' AS ag3_nickname \n");
				sb.append(",'' AS ag3_acc_name \n");
				sb.append(",'' AS ag3_acc_status\n");
				sb.append(",'' AS ag4_id \n");
				sb.append(",'' AS ag4_nickname \n");
				sb.append(",'' AS ag4_acc_name \n");
				sb.append(",'' AS ag4_acc_status \n");
				sb.append(",'' AS ag5_id \n");
				sb.append(",'' AS ag5_nickname \n");
				sb.append(",'' AS ag5_acc_name \n");
				sb.append(",'' AS ag5_acc_status \n");
				sb.append(",'' AS ag6_id \n");
				sb.append(",'' AS ag6_nickname \n");
				sb.append(",'' AS ag6_acc_name \n");
				sb.append(",'' AS ag6_acc_status \n");
				sb.append(",'' AS ag7_id \n");
				sb.append(",'' AS ag7_nickname \n");
				sb.append(",'' AS ag7_acc_name \n");
				sb.append(",'' AS ag7_acc_status \n");
				sb.append(",'' AS ag8_id \n");
				sb.append(",'' AS ag8_nickname \n");
				sb.append(",'' AS ag8_acc_name \n");
				sb.append(",'' AS ag8_acc_status \n");
				sb.append(",'' AS ag9_id \n");
				sb.append(",'' AS ag9_nickname \n");
				sb.append(",'' AS ag9_acc_name \n");
				sb.append(",'' AS ag9_acc_status \n");
				sb.append(",'' AS ag10_id \n");
				sb.append(",'' AS ag10_nickname \n");
				sb.append(",'' AS ag10_acc_name \n");
				sb.append(",'' AS ag10_acc_status \n");

				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g1_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I1.current_max_ratio WHEN A.acc_level_type=3 THEN I1.upper_min_ratio ELSE -1 END AS g1_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I1.upper_min_ratio ELSE -1 END AS g1_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I1.upper_min_ratio ELSE -1 END AS g1_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I1.upper_min_ratio ELSE -1 END AS g1_sa \n");
				sb.append("    ,-1 as g1_ag \n");
				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g2_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I2.current_max_ratio WHEN A.acc_level_type=3 THEN I2.upper_min_ratio ELSE -1 END AS g2_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I2.upper_min_ratio ELSE -1 END AS g2_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I2.upper_min_ratio ELSE -1 END AS g2_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I2.upper_min_ratio ELSE -1 END AS g2_sa \n");
				sb.append("    ,-1 as g2_ag \n");
				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g3_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I3.current_max_ratio WHEN A.acc_level_type=3 THEN I3.upper_min_ratio ELSE -1 END AS g3_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I3.upper_min_ratio ELSE -1 END AS g3_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I3.upper_min_ratio ELSE -1 END AS g3_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I3.upper_min_ratio ELSE -1 END AS g3_sa \n");
				sb.append("    ,-1 as g3_ag \n");
				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g4_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I4.current_max_ratio WHEN A.acc_level_type=3 THEN I4.upper_min_ratio ELSE -1 END AS g4_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I4.upper_min_ratio ELSE -1 END AS g4_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I4.upper_min_ratio ELSE -1 END AS g4_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I4.upper_min_ratio ELSE -1 END AS g4_sa \n");
				sb.append("    ,-1 as g4_ag \n");
				sb.append("    ,CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS g5_com \n");
				sb.append(
						"    ,CASE WHEN A.acc_level_type=2 THEN I5.current_max_ratio WHEN A.acc_level_type=3 THEN I5.upper_min_ratio ELSE -1 END AS g5_sc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=4 THEN I5.upper_min_ratio ELSE -1 END AS g5_bc \n");
				sb.append("    ,CASE WHEN A.acc_level_type=5 THEN I5.upper_min_ratio ELSE -1 END AS g5_co \n");
				sb.append("    ,CASE WHEN A.acc_level_type=6 THEN I5.upper_min_ratio ELSE -1 END AS g5_sa \n");
				sb.append("    ,-1 as g5_ag \n");

				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g1_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I1.next_current_max_ratio WHEN A.acc_level_type=3 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_sa \n");
				sb.append(",-1 as next_g1_ag \n");
				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g2_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I2.next_current_max_ratio WHEN A.acc_level_type=3 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_sa \n");
				sb.append(",-1 as next_g2_ag \n");
				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g3_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I3.next_current_max_ratio WHEN A.acc_level_type=3 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_sa \n");
				sb.append(",-1 as next_g3_ag \n");
				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g4_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I4.next_current_max_ratio WHEN A.acc_level_type=3 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_sa \n");
				sb.append(",-1 as next_g4_ag \n");
				sb.append(",CASE WHEN A.acc_level_type=1 THEN -1 ELSE -1 END AS next_g5_com \n");
				sb.append(
						",CASE WHEN A.acc_level_type=2 THEN I5.next_current_max_ratio WHEN A.acc_level_type=3 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_sc \n");
				sb.append(",CASE WHEN A.acc_level_type=4 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_bc \n");
				sb.append(",CASE WHEN A.acc_level_type=5 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_co \n");
				sb.append(",CASE WHEN A.acc_level_type=6 THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_sa \n");
				sb.append(",-1 as next_g5_ag \n");

				sb.append("    , K.update_status \n");
			} else if (type == ACC_LEVEL_MEM) {
				sb.append(",'' AS group_text  \n");
				sb.append(",0 AS 'com'  \n");
				sb.append(",0 AS 'sc'  \n");
				sb.append(",0 AS 'bc'  \n");
				sb.append(",0 AS 'co'  \n");
				sb.append(",0 AS 'sa'  \n");
				sb.append(",0 AS 'ag' \n");
				sb.append(",0 AS 'ag1' \n");
				sb.append(",0 AS 'ag2' \n");
				sb.append(",0 AS 'ag3' \n");
				sb.append(",0 AS 'ag4' \n");
				sb.append(",0 AS 'ag5' \n");
				sb.append(",0 AS 'ag6' \n");
				sb.append(",0 AS 'ag7' \n");
				sb.append(",0 AS 'ag8' \n");
				sb.append(",0 AS 'ag9' \n");
				sb.append(",0 AS 'ag10'  \n");
				sb.append(",0 AS 'mem'  \n");
				sb.append(", A.level_type_text AS acc_type \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SC + " THEN sc_id ELSE '' END AS sc_id  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SC
						+ " THEN (CASE WHEN TRIM(sc_nickname)='' THEN sc_acc_name ELSE sc_nickname END) ELSE '' END AS sc_nickname  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_SC + " THEN sc_acc_name ELSE '' END AS sc_acc_name  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_SC + " THEN sc_acc_status ELSE '' END AS sc_acc_status  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_BC + " THEN bc_id ELSE '' END AS bc_id  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_BC
						+ " THEN (CASE WHEN TRIM(bc_nickname)='' THEN bc_acc_name ELSE bc_nickname END) ELSE '' END AS bc_nickname  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_BC + " THEN bc_acc_name ELSE '' END AS bc_acc_name  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_BC + " THEN bc_acc_status ELSE '' END AS bc_acc_status  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_CO + " THEN co_id ELSE '' END AS co_id  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_CO
						+ " THEN (CASE WHEN TRIM(co_nickname)='' THEN co_acc_name ELSE co_nickname END) ELSE '' END AS co_nickname  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_CO + " THEN co_acc_name ELSE '' END AS co_acc_name  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_CO + " THEN co_acc_status ELSE '' END AS co_acc_status  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SA + " THEN sa_id ELSE '' END AS sa_id  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SA
						+ " THEN (CASE WHEN TRIM(sa_nickname)='' THEN sa_acc_name ELSE sa_nickname END)ELSE '' END AS sa_nickname  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_SA + " THEN sa_acc_name ELSE '' END AS sa_acc_name  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_SA + " THEN sa_acc_status ELSE '' END AS sa_acc_status  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG + " THEN ag_id ELSE '' END AS ag_id  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG
						+ " THEN (CASE WHEN TRIM(ag_nickname)='' THEN ag_acc_name ELSE ag_nickname END) ELSE '' END AS ag_nickname  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG + " THEN ag_acc_name ELSE '' END AS ag_acc_name \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG + " THEN ag_acc_status ELSE '' END AS ag_acc_status  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG1 + " THEN ag1_id ELSE '' END AS ag1_id  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG1
						+ " THEN (CASE WHEN TRIM(ag1_nickname)='' THEN ag1_acc_name ELSE ag1_nickname END) ELSE '' END AS ag1_nickname  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG1 + " THEN ag1_acc_name ELSE '' END AS ag1_acc_name \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG1 + " THEN ag1_acc_status ELSE '' END AS ag1_acc_status        \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG2 + " THEN ag2_id ELSE '' END AS ag2_id  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG2
						+ " THEN (CASE WHEN TRIM(ag2_nickname)='' THEN ag2_acc_name ELSE ag2_nickname END) ELSE '' END AS ag2_nickname  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG2 + " THEN ag2_acc_name ELSE '' END AS ag2_acc_name \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG2 + " THEN ag2_acc_status ELSE '' END AS ag2_acc_status   \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG3 + " THEN ag3_id ELSE '' END AS ag3_id  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG3
						+ " THEN (CASE WHEN TRIM(ag3_nickname)='' THEN ag3_acc_name ELSE ag3_nickname END) ELSE '' END AS ag3_nickname  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG3 + " THEN ag3_acc_name ELSE '' END AS ag3_acc_name \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG3 + " THEN ag3_acc_status ELSE '' END AS ag3_acc_status   \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG4 + " THEN ag4_id ELSE '' END AS ag4_id  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG4
						+ " THEN (CASE WHEN TRIM(ag4_nickname)='' THEN ag4_acc_name ELSE ag4_nickname END) ELSE '' END AS ag4_nickname  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG4 + " THEN ag4_acc_name ELSE '' END AS ag4_acc_name \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG4 + " THEN ag4_acc_status ELSE '' END AS ag4_acc_status   \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG5 + " THEN ag5_id ELSE '' END AS ag5_id  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG5
						+ " THEN (CASE WHEN TRIM(ag5_nickname)='' THEN ag5_acc_name ELSE ag5_nickname END) ELSE '' END AS ag5_nickname  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG5 + " THEN ag5_acc_name ELSE '' END AS ag5_acc_name \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG5 + " THEN ag5_acc_status ELSE '' END AS ag5_acc_status   \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG6 + " THEN ag6_id ELSE '' END AS ag6_id  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG6
						+ " THEN (CASE WHEN TRIM(ag6_nickname)='' THEN ag6_acc_name ELSE ag6_nickname END) ELSE '' END AS ag6_nickname  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG6 + " THEN ag6_acc_name ELSE '' END AS ag6_acc_name \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG6 + " THEN ag6_acc_status ELSE '' END AS ag6_acc_status   \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG7 + " THEN ag7_id ELSE '' END AS ag7_id  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG7
						+ " THEN (CASE WHEN TRIM(ag_nickname)='' THEN ag7_acc_name ELSE ag7_nickname END) ELSE '' END AS ag7_nickname  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG7 + " THEN ag7_acc_name ELSE '' END AS ag7_acc_name \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG7 + " THEN ag7_acc_status ELSE '' END AS ag7_acc_status   \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG8 + " THEN ag8_id ELSE '' END AS ag8_id  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG8
						+ " THEN (CASE WHEN TRIM(ag8_nickname)='' THEN ag8_acc_name ELSE ag8_nickname END) ELSE '' END AS ag8_nickname  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG8 + " THEN ag8_acc_name ELSE '' END AS ag8_acc_name \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG8 + " THEN ag8_acc_status ELSE '' END AS ag8_acc_status \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG9 + " THEN ag9_id ELSE '' END AS ag9_id  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG9
						+ " THEN (CASE WHEN TRIM(ag9_nickname)='' THEN ag9_acc_name ELSE ag9_nickname END) ELSE '' END AS ag9_nickname  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG9 + " THEN ag9_acc_name ELSE '' END AS ag9_acc_name \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG9 + " THEN ag9_acc_status ELSE '' END AS ag9_acc_status  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG10 + " THEN ag10_id ELSE '' END AS ag10_id  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG10
						+ " THEN (CASE WHEN TRIM(ag10_nickname)='' THEN ag10_acc_name ELSE ag10_nickname END) ELSE '' END AS ag10_nickname  \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG10 + " THEN ag10_acc_name ELSE '' END AS ag10_acc_name \n");
				sb.append(",CASE WHEN level_type<" + ACC_LEVEL_AG10 + " THEN ag10_acc_status ELSE '' END AS ag10_acc_status     \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_COM + " AND A.upper_acc_level_type >= " + ACC_LEVEL_COM
						+ " THEN I1.com_ratio ELSE -1 END AS g1_com  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SC
						+ " THEN I1.sc_ratio ELSE -1 END AS g1_sc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_BC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_BC
						+ " THEN I1.bc_ratio ELSE -1 END AS g1_bc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_CO + " AND A.upper_acc_level_type >= " + ACC_LEVEL_CO
						+ " THEN I1.co_ratio ELSE -1 END AS g1_co  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SA + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SA
						+ " THEN I1.sa_ratio ELSE -1 END AS g1_sa  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG
						+ " THEN I1.ag_ratio ELSE -1 END AS g1_ag  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG1 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG1
						+ " THEN I1.ag1_ratio ELSE -1 END AS g1_ag1  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG2 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG2
						+ " THEN I1.ag2_ratio ELSE -1 END AS g1_ag2  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG3 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG3
						+ " THEN I1.ag3_ratio ELSE -1 END AS g1_ag3  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG4 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG4
						+ " THEN I1.ag4_ratio ELSE -1 END AS g1_ag4  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG5 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG5
						+ " THEN I1.ag5_ratio ELSE -1 END AS g1_ag5  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG6 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG6
						+ " THEN I1.ag6_ratio ELSE -1 END AS g1_ag6  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG7 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG7
						+ " THEN I1.ag7_ratio ELSE -1 END AS g1_ag7  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG8 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG8
						+ " THEN I1.ag8_ratio ELSE -1 END AS g1_ag8  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG9 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG9
						+ " THEN I1.ag9_ratio ELSE -1 END AS g1_ag9  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG10 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG10
						+ " THEN I1.ag10_ratio ELSE -1 END AS g1_ag10  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_COM + " AND A.upper_acc_level_type >= " + ACC_LEVEL_COM
						+ " THEN I2.com_ratio ELSE -1 END AS g2_com  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SC
						+ " THEN I2.sc_ratio ELSE -1 END AS g2_sc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_BC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_BC
						+ " THEN I2.bc_ratio ELSE -1 END AS g2_bc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_CO + " AND A.upper_acc_level_type >= " + ACC_LEVEL_CO
						+ " THEN I2.co_ratio ELSE -1 END AS g2_co  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SA + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SA
						+ " THEN I2.sa_ratio ELSE -1 END AS g2_sa  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG
						+ " THEN I2.ag_ratio ELSE -1 END AS g2_ag  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG1 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG1
						+ " THEN I2.ag1_ratio ELSE -1 END AS g2_ag1  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG2 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG2
						+ " THEN I2.ag2_ratio ELSE -1 END AS g2_ag2  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG3 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG3
						+ " THEN I2.ag3_ratio ELSE -1 END AS g2_ag3  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG4 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG4
						+ " THEN I2.ag4_ratio ELSE -1 END AS g2_ag4  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG5 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG5
						+ " THEN I2.ag5_ratio ELSE -1 END AS g2_ag5  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG6 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG6
						+ " THEN I2.ag6_ratio ELSE -1 END AS g2_ag6  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG7 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG7
						+ " THEN I2.ag7_ratio ELSE -1 END AS g2_ag7  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG8 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG8
						+ " THEN I2.ag8_ratio ELSE -1 END AS g2_ag8  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG9 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG9
						+ " THEN I2.ag9_ratio ELSE -1 END AS g2_ag9  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG10 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG10
						+ " THEN I2.ag10_ratio ELSE -1 END AS g2_ag10  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_COM + " AND A.upper_acc_level_type >= " + ACC_LEVEL_COM
						+ " THEN I3.com_ratio ELSE -1 END AS g3_com  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SC
						+ " THEN I3.sc_ratio ELSE -1 END AS g3_sc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_BC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_BC
						+ " THEN I3.bc_ratio ELSE -1 END AS g3_bc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_CO + " AND A.upper_acc_level_type >= " + ACC_LEVEL_CO
						+ " THEN I3.co_ratio ELSE -1 END AS g3_co  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SA + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SA
						+ " THEN I3.sa_ratio ELSE -1 END AS g3_sa  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG
						+ " THEN I3.ag_ratio ELSE -1 END AS g3_ag  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG1 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG1
						+ " THEN I3.ag1_ratio ELSE -1 END AS g3_ag1  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG2 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG2
						+ " THEN I3.ag2_ratio ELSE -1 END AS g3_ag2  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG3 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG3
						+ " THEN I3.ag3_ratio ELSE -1 END AS g3_ag3  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG4 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG4
						+ " THEN I3.ag4_ratio ELSE -1 END AS g3_ag4  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG5 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG5
						+ " THEN I3.ag5_ratio ELSE -1 END AS g3_ag5  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG6 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG6
						+ " THEN I3.ag6_ratio ELSE -1 END AS g3_ag6  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG7 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG7
						+ " THEN I3.ag7_ratio ELSE -1 END AS g3_ag7  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG8 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG8
						+ " THEN I3.ag8_ratio ELSE -1 END AS g3_ag8  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG9 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG9
						+ " THEN I3.ag9_ratio ELSE -1 END AS g3_ag9  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG10 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG10
						+ " THEN I3.ag10_ratio ELSE -1 END AS g3_ag10  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_COM + " AND A.upper_acc_level_type >= " + ACC_LEVEL_COM
						+ " THEN I4.com_ratio ELSE -1 END AS g4_com  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SC
						+ " THEN I4.sc_ratio ELSE -1 END AS g4_sc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_BC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_BC
						+ " THEN I4.bc_ratio ELSE -1 END AS g4_bc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_CO + " AND A.upper_acc_level_type >= " + ACC_LEVEL_CO
						+ " THEN I4.co_ratio ELSE -1 END AS g4_co  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SA + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SA
						+ " THEN I4.sa_ratio ELSE -1 END AS g4_sa  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG
						+ " THEN I4.ag_ratio ELSE -1 END AS g4_ag  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG1 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG1
						+ " THEN I4.ag1_ratio ELSE -1 END AS g4_ag1  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG2 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG2
						+ " THEN I4.ag2_ratio ELSE -1 END AS g4_ag2  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG3 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG3
						+ " THEN I4.ag3_ratio ELSE -1 END AS g4_ag3  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG4 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG4
						+ " THEN I4.ag4_ratio ELSE -1 END AS g4_ag4  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG5 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG5
						+ " THEN I4.ag5_ratio ELSE -1 END AS g4_ag5  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG6 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG6
						+ " THEN I4.ag6_ratio ELSE -1 END AS g4_ag6  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG7 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG7
						+ " THEN I4.ag7_ratio ELSE -1 END AS g4_ag7  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG8 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG8
						+ " THEN I4.ag8_ratio ELSE -1 END AS g4_ag8  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG9 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG9
						+ " THEN I4.ag9_ratio ELSE -1 END AS g4_ag9  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG10 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG10
						+ " THEN I4.ag10_ratio ELSE -1 END AS g4_ag10  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_COM + " AND A.upper_acc_level_type >= " + ACC_LEVEL_COM
						+ " THEN I5.com_ratio ELSE -1 END AS g5_com  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SC
						+ " THEN I5.sc_ratio ELSE -1 END AS g5_sc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_BC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_BC
						+ " THEN I5.bc_ratio ELSE -1 END AS g5_bc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_CO + " AND A.upper_acc_level_type >= " + ACC_LEVEL_CO
						+ " THEN I5.co_ratio ELSE -1 END AS g5_co  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SA + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SA
						+ " THEN I5.sa_ratio ELSE -1 END AS g5_sa  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG
						+ " THEN I5.ag_ratio ELSE -1 END AS g5_ag  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG1 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG1
						+ " THEN I5.ag1_ratio ELSE -1 END AS g5_ag1  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG2 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG2
						+ " THEN I5.ag2_ratio ELSE -1 END AS g5_ag2  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG3 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG3
						+ " THEN I5.ag3_ratio ELSE -1 END AS g5_ag3  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG4 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG4
						+ " THEN I5.ag4_ratio ELSE -1 END AS g5_ag4  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG5 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG5
						+ " THEN I5.ag5_ratio ELSE -1 END AS g5_ag5  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG6 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG6
						+ " THEN I5.ag6_ratio ELSE -1 END AS g5_ag6  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG7 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG7
						+ " THEN I5.ag7_ratio ELSE -1 END AS g5_ag7  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG8 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG8
						+ " THEN I5.ag8_ratio ELSE -1 END AS g5_ag8  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG9 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG9
						+ " THEN I5.ag9_ratio ELSE -1 END AS g5_ag9  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG10 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG10
						+ " THEN I5.ag10_ratio ELSE -1 END AS g5_ag10  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_COM + " AND A.upper_acc_level_type >= " + ACC_LEVEL_COM
						+ " THEN I1.next_com_ratio ELSE -1 END AS next_g1_com  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SC
						+ " THEN I1.next_sc_ratio ELSE -1 END AS next_g1_sc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_BC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_BC
						+ " THEN I1.next_bc_ratio ELSE -1 END AS next_g1_bc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_CO + " AND A.upper_acc_level_type >= " + ACC_LEVEL_CO
						+ " THEN I1.next_co_ratio ELSE -1 END AS next_g1_co  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SA + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SA
						+ " THEN I1.next_sa_ratio ELSE -1 END AS next_g1_sa  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG
						+ " THEN I1.next_ag_ratio ELSE -1 END AS next_g1_ag  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG1 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG1
						+ " THEN I1.next_ag1_ratio ELSE -1 END AS next_g1_ag1  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG2 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG2
						+ " THEN I1.next_ag2_ratio ELSE -1 END AS next_g1_ag2  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG3 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG3
						+ " THEN I1.next_ag3_ratio ELSE -1 END AS next_g1_ag3  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG4 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG4
						+ " THEN I1.next_ag4_ratio ELSE -1 END AS next_g1_ag4  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG5 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG5
						+ " THEN I1.next_ag5_ratio ELSE -1 END AS next_g1_ag5  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG6 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG6
						+ " THEN I1.next_ag6_ratio ELSE -1 END AS next_g1_ag6  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG7 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG7
						+ " THEN I1.next_ag7_ratio ELSE -1 END AS next_g1_ag7  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG8 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG8
						+ " THEN I1.next_ag8_ratio ELSE -1 END AS next_g1_ag8  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG9 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG9
						+ " THEN I1.next_ag9_ratio ELSE -1 END AS next_g1_ag9  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG10 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG10
						+ " THEN I1.next_ag10_ratio ELSE -1 END AS next_g1_ag10 \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_COM + " AND A.upper_acc_level_type >= " + ACC_LEVEL_COM
						+ " THEN I2.next_com_ratio ELSE -1 END AS next_g2_com  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SC
						+ " THEN I2.next_sc_ratio ELSE -1 END AS next_g2_sc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_BC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_BC
						+ " THEN I2.next_bc_ratio ELSE -1 END AS next_g2_bc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_CO + " AND A.upper_acc_level_type >= " + ACC_LEVEL_CO
						+ " THEN I2.next_co_ratio ELSE -1 END AS next_g2_co  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SA + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SA
						+ " THEN I2.next_sa_ratio ELSE -1 END AS next_g2_sa  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG
						+ " THEN I2.next_ag_ratio ELSE -1 END AS next_g2_ag  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG1 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG1
						+ " THEN I2.next_ag1_ratio ELSE -1 END AS next_g2_ag1  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG2 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG2
						+ " THEN I2.next_ag2_ratio ELSE -1 END AS next_g2_ag2  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG3 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG3
						+ " THEN I2.next_ag3_ratio ELSE -1 END AS next_g2_ag3  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG4 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG4
						+ " THEN I2.next_ag4_ratio ELSE -1 END AS next_g2_ag4  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG5 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG5
						+ " THEN I2.next_ag5_ratio ELSE -1 END AS next_g2_ag5  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG6 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG6
						+ " THEN I2.next_ag6_ratio ELSE -1 END AS next_g2_ag6  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG7 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG7
						+ " THEN I2.next_ag7_ratio ELSE -1 END AS next_g2_ag7  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG8 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG8
						+ " THEN I2.next_ag8_ratio ELSE -1 END AS next_g2_ag8  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG9 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG9
						+ " THEN I2.next_ag9_ratio ELSE -1 END AS next_g2_ag9  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG10 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG10
						+ " THEN I2.next_ag10_ratio ELSE -1 END AS next_g2_ag10 \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_COM + " AND A.upper_acc_level_type >= " + ACC_LEVEL_COM
						+ " THEN I3.next_com_ratio ELSE -1 END AS next_g3_com  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SC
						+ " THEN I3.next_sc_ratio ELSE -1 END AS next_g3_sc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_BC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_BC
						+ " THEN I3.next_bc_ratio ELSE -1 END AS next_g3_bc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_CO + " AND A.upper_acc_level_type >= " + ACC_LEVEL_CO
						+ " THEN I3.next_co_ratio ELSE -1 END AS next_g3_co  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SA + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SA
						+ " THEN I3.next_sa_ratio ELSE -1 END AS next_g3_sa  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG
						+ " THEN I3.next_ag_ratio ELSE -1 END AS next_g3_ag \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG1 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG1
						+ " THEN I3.next_ag1_ratio ELSE -1 END AS next_g3_ag1  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG2 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG2
						+ " THEN I3.next_ag2_ratio ELSE -1 END AS next_g3_ag2  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG3 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG3
						+ " THEN I3.next_ag3_ratio ELSE -1 END AS next_g3_ag3  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG4 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG4
						+ " THEN I3.next_ag4_ratio ELSE -1 END AS next_g3_ag4  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG5 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG5
						+ " THEN I3.next_ag5_ratio ELSE -1 END AS next_g3_ag5  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG6 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG6
						+ " THEN I3.next_ag6_ratio ELSE -1 END AS next_g3_ag6  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG7 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG7
						+ " THEN I3.next_ag7_ratio ELSE -1 END AS next_g3_ag7  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG8 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG8
						+ " THEN I3.next_ag8_ratio ELSE -1 END AS next_g3_ag8  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG9 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG9
						+ " THEN I3.next_ag9_ratio ELSE -1 END AS next_g3_ag9  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG10 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG10
						+ " THEN I3.next_ag10_ratio ELSE -1 END AS next_g3_ag10 \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_COM + " AND A.upper_acc_level_type >= " + ACC_LEVEL_COM
						+ " THEN I4.next_com_ratio ELSE -1 END AS next_g4_com  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SC
						+ " THEN I4.next_sc_ratio ELSE -1 END AS next_g4_sc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_BC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_BC
						+ " THEN I4.next_bc_ratio ELSE -1 END AS next_g4_bc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_CO + " AND A.upper_acc_level_type >= " + ACC_LEVEL_CO
						+ " THEN I4.next_co_ratio ELSE -1 END AS next_g4_co  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SA + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SA
						+ " THEN I4.next_sa_ratio ELSE -1 END AS next_g4_sa  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG
						+ " THEN I4.next_ag_ratio ELSE -1 END AS next_g4_ag \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG1 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG1
						+ " THEN I4.next_ag1_ratio ELSE -1 END AS next_g4_ag1  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG2 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG2
						+ " THEN I4.next_ag2_ratio ELSE -1 END AS next_g4_ag2  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG3 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG3
						+ " THEN I4.next_ag3_ratio ELSE -1 END AS next_g4_ag3  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG4 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG4
						+ " THEN I4.next_ag4_ratio ELSE -1 END AS next_g4_ag4  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG5 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG5
						+ " THEN I4.next_ag5_ratio ELSE -1 END AS next_g4_ag5  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG6 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG6
						+ " THEN I4.next_ag6_ratio ELSE -1 END AS next_g4_ag6  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG7 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG7
						+ " THEN I4.next_ag7_ratio ELSE -1 END AS next_g4_ag7  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG8 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG8
						+ " THEN I4.next_ag8_ratio ELSE -1 END AS next_g4_ag8  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG9 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG9
						+ " THEN I4.next_ag9_ratio ELSE -1 END AS next_g4_ag9  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG10 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG10
						+ " THEN I4.next_ag10_ratio ELSE -1 END AS next_g4_ag10 \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_COM + " AND A.upper_acc_level_type >= " + ACC_LEVEL_COM
						+ " THEN I5.next_com_ratio ELSE -1 END AS next_g5_com  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SC
						+ " THEN I5.next_sc_ratio ELSE -1 END AS next_g5_sc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_BC + " AND A.upper_acc_level_type >= " + ACC_LEVEL_BC
						+ " THEN I5.next_bc_ratio ELSE -1 END AS next_g5_bc  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_CO + " AND A.upper_acc_level_type >= " + ACC_LEVEL_CO
						+ " THEN I5.next_co_ratio ELSE -1 END AS next_g5_co  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_SA + " AND A.upper_acc_level_type >= " + ACC_LEVEL_SA
						+ " THEN I5.next_sa_ratio ELSE -1 END AS next_g5_sa  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG
						+ " THEN I5.next_ag_ratio ELSE -1 END AS next_g5_ag \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG1 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG1
						+ " THEN I5.next_ag1_ratio ELSE -1 END AS next_g5_ag1  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG2 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG2
						+ " THEN I5.next_ag2_ratio ELSE -1 END AS next_g5_ag2  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG3 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG3
						+ " THEN I5.next_ag3_ratio ELSE -1 END AS next_g5_ag3  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG4 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG4
						+ " THEN I5.next_ag4_ratio ELSE -1 END AS next_g5_ag4  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG5 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG5
						+ " THEN I5.next_ag5_ratio ELSE -1 END AS next_g5_ag5  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG6 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG6
						+ " THEN I5.next_ag6_ratio ELSE -1 END AS next_g5_ag6  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG7 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG7
						+ " THEN I5.next_ag7_ratio ELSE -1 END AS next_g5_ag7  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG8 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG8
						+ " THEN I5.next_ag8_ratio ELSE -1 END AS next_g5_ag8  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG9 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG9
						+ " THEN I5.next_ag9_ratio ELSE -1 END AS next_g5_ag9  \n");
				sb.append(",CASE WHEN level_type<=" + ACC_LEVEL_AG10 + " AND A.upper_acc_level_type >= " + ACC_LEVEL_AG10
						+ " THEN I5.next_ag10_ratio ELSE -1 END AS next_g5_ag10   \n");
				sb.append(", K.update_status  \n");
			} else {
				return "";
			}
			return sb.toString();

		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			return "";
		} finally {
			sb.setLength(0);
			sb = null;
		}
	}

	/**
	 * getUpAccountName for showAddAccount
	 */
	@Override
	public List<Map<String, Object>> getUpAccountName(long accId, int accLevelType) {
		LOG.debug("getUpAccountName");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		Map<String, Object> map = null;
		int level = 0;
		try {
			if (accLevelType == ACC_LEVEL_BC) {
				level = ACC_LEVEL_SC;
			} else if (accLevelType == ACC_LEVEL_CO) {
				level = ACC_LEVEL_BC;
			} else if (accLevelType == ACC_LEVEL_SA) {
				level = ACC_LEVEL_CO;
			} else if (accLevelType == ACC_LEVEL_AG) {
				level = ACC_LEVEL_SA;
			} else if (accLevelType == ACC_LEVEL_AG1) {
				level = ACC_LEVEL_AG;
			} else if (accLevelType == ACC_LEVEL_AG2) {
				level = ACC_LEVEL_AG1;
			} else if (accLevelType == ACC_LEVEL_AG3) {
				level = ACC_LEVEL_AG2;
			} else if (accLevelType == ACC_LEVEL_AG4) {
				level = ACC_LEVEL_AG3;
			} else if (accLevelType == ACC_LEVEL_AG5) {
				level = ACC_LEVEL_AG4;
			} else if (accLevelType == ACC_LEVEL_AG6) {
				level = ACC_LEVEL_AG5;
			} else if (accLevelType == ACC_LEVEL_AG7) {
				level = ACC_LEVEL_AG6;
			} else if (accLevelType == ACC_LEVEL_AG8) {
				level = ACC_LEVEL_AG7;
			} else if (accLevelType == ACC_LEVEL_AG9) {
				level = ACC_LEVEL_AG8;
			} else if (accLevelType == ACC_LEVEL_AG10) {
				level = ACC_LEVEL_AG9;
			} else if (accLevelType == ACC_LEVEL_MEM) {
				level = ACC_LEVEL_MEM;
			}

			if (level > ACC_LEVEL_COM) {
				sb.append("select A.acc_id, A.acc_level_type, B.balance, B.acc_name, B.nickname, B.next_full_ratio as full_ratio , B.handicap \n");
				sb.append(
						", C1.next_upper_max_ratio as g1_max_ratio, C1.next_upper_min_ratio as g1_min_ratio, C1.next_current_max_ratio as g1_current_ratio \n");
				sb.append(
						", C2.next_upper_max_ratio as g2_max_ratio, C2.next_upper_min_ratio as g2_min_ratio, C2.next_current_max_ratio as g2_current_ratio \n");
				sb.append(
						" ,C3.next_upper_max_ratio as g3_max_ratio, C3.next_upper_min_ratio as g3_min_ratio, C3.next_current_max_ratio as g3_current_ratio \n");
				sb.append(
						", C4.next_upper_max_ratio as g4_max_ratio, C4.next_upper_min_ratio as g4_min_ratio, C4.next_current_max_ratio as g4_current_ratio \n");
				sb.append(
						", C5.next_upper_max_ratio as g5_max_ratio, C5.next_upper_min_ratio as g5_min_ratio, C5.next_current_max_ratio as g5_current_ratio \n");
				sb.append("from (SELECT acc_id,acc_level_type FROM `ctt_manager`.`ctt_manager_acc_level`WHERE CASE WHEN ? = " + ACC_LEVEL_MEM
						+ " THEN acc_level_type > " + ACC_LEVEL_COM + " ELSE acc_level_type= ? END \n");
				params.add(level);
				params.add(level);
				sb.append("AND( CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				params.add(accId);
				sb.append("WHEN " + ACC_LEVEL_ADMIN + " THEN TRUE \n");
				sb.append("WHEN " + ACC_LEVEL_COM + " THEN TRUE \n");
				sb.append("WHEN " + ACC_LEVEL_SC + " THEN sc = ? \n");
				params.add(accId);
				sb.append("WHEN " + ACC_LEVEL_BC + " THEN bc = ? \n");
				params.add(accId);
				sb.append("WHEN " + ACC_LEVEL_CO + " THEN co = ? \n");
				params.add(accId);
				sb.append("WHEN " + ACC_LEVEL_SA + " THEN sa = ? \n");
				params.add(accId);
				sb.append("WHEN " + ACC_LEVEL_AG + " THEN ag = ? \n");
				params.add(accId);
				sb.append("WHEN " + ACC_LEVEL_AG1 + " THEN ag1 = ? \n");
				params.add(accId);
				sb.append("WHEN " + ACC_LEVEL_AG2 + " THEN ag2 = ? \n");
				params.add(accId);
				sb.append("WHEN " + ACC_LEVEL_AG3 + " THEN ag3 = ? \n");
				params.add(accId);
				sb.append("WHEN " + ACC_LEVEL_AG4 + " THEN ag4 = ? \n");
				params.add(accId);
				sb.append("WHEN " + ACC_LEVEL_AG5 + " THEN ag5 = ? \n");
				params.add(accId);
				sb.append("WHEN " + ACC_LEVEL_AG6 + " THEN ag6 = ? \n");
				params.add(accId);
				sb.append("WHEN " + ACC_LEVEL_AG7 + " THEN ag7 = ? \n");
				params.add(accId);
				sb.append("WHEN " + ACC_LEVEL_AG8 + " THEN ag8 = ? \n");
				params.add(accId);
				sb.append("WHEN " + ACC_LEVEL_AG9 + " THEN ag9 = ? \n");
				params.add(accId);
				sb.append("WHEN " + ACC_LEVEL_AG10 + " THEN ag10 = ? \n");
				params.add(accId);
				sb.append("ELSE FALSE END) \n");

				sb.append(
						"or (acc_id = ? and (select case when count(1) > 0 then true else false end from ctt_manager.ctt_manager_acc_level where acc_id = ? and acc_level_type > "
								+ ACC_LEVEL_COM + " and acc_level_type < ? ) ) \n");
				params.add(accId);
				params.add(accId);
				params.add(level);
				sb.append(") A\n");
				sb.append("inner join\n");
				sb.append("(select acc_id, acc_name, nickname, balance, next_full_ratio , handicap from `ctt_manager`.`ctt_manager_acc`) B \n");
				sb.append("on B.acc_id= A.acc_id\n");
				sb.append("left join\n");
				sb.append(
						"(select acc_id, next_upper_max_ratio, next_upper_min_ratio, next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type="
								+ LOTTERY + ") C1 \n");
				sb.append("on C1.acc_id= A.acc_id\n");
				sb.append("left join\n");
				sb.append(
						"(select acc_id, next_upper_max_ratio, next_upper_min_ratio, next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type="
								+ LIVEVIDEO + ") C2\n");
				sb.append("on C2.acc_id= A.acc_id\n");
				sb.append("left join\n");
				sb.append(
						"(select acc_id, next_upper_max_ratio, next_upper_min_ratio, next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type="
								+ MOVEMENT + ") C3\n");
				sb.append("on C3.acc_id= A.acc_id \n");
				sb.append("left join \n");
				sb.append(
						"(select acc_id, next_upper_max_ratio, next_upper_min_ratio, next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type="
								+ VIDEOGAME + ") C4 \n");
				sb.append("on C4.acc_id= A.acc_id \n");
				sb.append("left join \n");
				sb.append(
						"(select acc_id, next_upper_max_ratio, next_upper_min_ratio, next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type="
								+ GAME + ") C5 \n");
				sb.append("on C5.acc_id= A.acc_id\n");

				this.checkRead();
				if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
					LOG.debug("CONNECTION IS NULL");
					return result;
				} else {
					List<Object> list = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), params, new AddAccountBean());
					for (int i = 0; i < list.size(); i++) {

						AddAccountBean addAccountBean = (AddAccountBean) list.get(i);
						map = new ConcurrentHashMap<String, Object>();
						map.put("accId", addAccountBean.getAccId());
						map.put("accName", addAccountBean.getAccName());
						map.put("balance", addAccountBean.getBalance());
						map.put("fullRatio", addAccountBean.getFullRatio());
						map.put("accLevelType", addAccountBean.getAccLevelType());
						map.put("g1CurrentRatio", addAccountBean.getG1CurrentRatio());
						map.put("g2CurrentRatio", addAccountBean.getG2CurrentRatio());
						map.put("g3CurrentRatio", addAccountBean.getG3CurrentRatio());
						map.put("g4CurrentRatio", addAccountBean.getG4CurrentRatio());
						map.put("g5CurrentRatio", addAccountBean.getG5CurrentRatio());
						map.put("handicap", addAccountBean.getHandicap());
						
						result.add(map);
					}
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params.clear();
			}
			params = null;
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
		}

		return result;
	}

	/**
	 * addManageraccountinfo to ctt_manager_acc by insertNoCommitByMap(no
	 * this.WRITE_CONN.commit())
	 */
	@Override
	public long addManager(String accName, String pwd, String nickname, int authGroup, int fullRatio, int handicap) throws SQLException {
		long result = 0;
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		LOG.debug("addManager");
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {
				insertMap.put("acc_name", accName);
				insertMap.put("pwd", pwd);
				insertMap.put("nickname", nickname);
				insertMap.put("auth_group", authGroup);
				insertMap.put("full_ratio", fullRatio);
				insertMap.put("next_full_ratio", fullRatio);
				insertMap.put("handicap", handicap);
				

				long count = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_manager_acc", insertMap);
				LOG.debug("addManager===" + count);
				if (count > 0) {
					return count;
				}
			}
		} catch (Exception e) {
			result = 0;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				this.WRITE_CONN.rollback();
			} catch (SQLException e1) {
				LOG.debug("SQLException:" + e1.getMessage());
				LOG.error("\nERROR SQL rollback:" + (result + 1));
				ShowLog.err(LOG, e1);
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return result;
	}

	@Override
	public int checkAccName(String tableName, String accName) {
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return -1;
			} else {
				sb.append("select COUNT(*) as count from " + tableName + " where acc_name Like ?");
				ps = this.READ_CONN.prepareStatement(sb.toString());

				ps.setString(1, accName);

				LOG.debug(ps.toString());
				rs = ps.executeQuery();

				if (rs.next()) {
					LOG.debug(rs.getInt("count"));
					return rs.getInt("count");
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			return -1;
		} finally {
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					LOG.debug("SQLException, " + e.getMessage());
					ShowLog.err(LOG, e);
				}
				rs = null;
			}
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.debug("SQLException, " + e.getMessage());
					ShowLog.err(LOG, e);
				}
				rs = null;
			}
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
		}
		return -1;
	}

	/**
	 * addcreateinfo to ctt_manager_create by insertNoCommitByMap(no
	 * this.WRITE_CONN.commit())
	 */
	@Override
	public boolean addCreate(String createTable, long accId, long updateaccId, String ip) throws SQLException {
		String[] ipArray = null;
		StringBuilder sb = null;
		List<Object> insertList = null;
		try {
			if (ip != null && !ip.isEmpty() && !"".equals(ip.trim())) {
				ipArray = ip.split("\\.");
				ip = (ipArray == null || ipArray.length != 4) ? "0.0.0.0" : ip;
			}
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				sb = new StringBuilder();
				insertList = new ArrayList<Object>();

				sb.append("INSERT INTO "+createTable+" (acc_id,update_acc_id,ip1,ip2,ip3,ip4,create_datetime) VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP) \n");
				insertList.add(accId);
				insertList.add(updateaccId);
				insertList.add(Integer.parseInt(ipArray[0]));
				insertList.add(Integer.parseInt(ipArray[1]));
				insertList.add(Integer.parseInt(ipArray[2]));
				insertList.add(Integer.parseInt(ipArray[3]));

				long count = StmtUtil.insertNoCommit(this.WRITE_CONN, sb.toString(), insertList);
				LOG.debug("addCreate===" + count);
				if (count == -1) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertList != null) {
				insertList.clear();
				insertList = null;
			}
			ipArray = null;
		}
		return false;
	}

	/**
	 * addlast_logininfo to ctt_manager_last_login by insertNoCommitByMap(no
	 * this.WRITE_CONN.commit())
	 */
	@Override
	public boolean addLastLogin(String lastLoginTable, long accId, String ip) throws SQLException {
		String[] ipArray = null;
		StringBuilder sb = null;
		List<Object> insertList = null;
		try {
			if (ip != null && !ip.isEmpty() && !"".equals(ip.trim())) {
				ipArray = ip.split("\\.");
				ip = (ipArray == null || ipArray.length != 4) ? "0.0.0.0" : ip;
			}
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				sb = new StringBuilder();
				insertList = new ArrayList<Object>();

				if (ipArray == null || ipArray.length != 4) {
					ipArray[0] = "0";
					ipArray[1] = "0";
					ipArray[2] = "0";
					ipArray[3] = "0";
				}
				sb.append("INSERT INTO "+lastLoginTable+" (acc_id,ip1,ip2,ip3,ip4,last_login_datetime) VALUES (?,?,?,?,?,CURRENT_TIMESTAMP) \n");
				insertList.add(accId);
				insertList.add(ipArray[0]);
				insertList.add(ipArray[1]);
				insertList.add(ipArray[2]);
				insertList.add(ipArray[3]);

				long count = StmtUtil.insertNoCommit(this.WRITE_CONN, sb.toString(), insertList);
				LOG.debug("addLastLogin===" + count);
				if (count == -1) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertList != null) {
				insertList.clear();
				insertList = null;
			}
			ipArray = null;
		}
		return false;
	}

	/**
	 * addlast_updateinfo to ctt_manager_last_update by insertNoCommitByMap(no
	 * this.WRITE_CONN.commit())
	 */
	@Override
	public boolean addLastUpdate(String lastUpdateTable, long accId, long updateAccId, String ip) throws SQLException {
		String[] ipArray = null;
		StringBuilder sb = null;
		List<Object> insertList = null;
		try {
			if (ip != null && !ip.isEmpty() && !"".equals(ip.trim())) {
				ipArray = ip.split("\\.");
				ip = (ipArray == null || ipArray.length != 4) ? "0.0.0.0" : ip;
			}
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				sb = new StringBuilder();
				insertList = new ArrayList<Object>();
				sb.append("INSERT INTO "+lastUpdateTable+" (acc_id,update_acc_id,ip1,ip2,ip3,ip4,update_datetime) VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP) \n");
				
				insertList.add(accId);
				insertList.add(updateAccId);
				insertList.add(Integer.parseInt(ipArray[0]));
				insertList.add(Integer.parseInt(ipArray[1]));
				insertList.add(Integer.parseInt(ipArray[2]));
				insertList.add(Integer.parseInt(ipArray[3]));
				
				long count = StmtUtil.insertNoCommit(this.WRITE_CONN, sb.toString(), insertList);
				if (count == -1) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertList != null) {
				insertList.clear();
				insertList = null;
			}
			ipArray = null;
		}
		return false;
	}

	/**
	 * addComScLevel to ctt_manager_acc_level by insertNoCommitByMap(no
	 * this.WRITE_CONN.commit())
	 */
	@Override
	public boolean addComScLevel(long accId, int accLevelType) throws SQLException {
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				insertMap.put("acc_id", accId);
				insertMap.put("acc_level_type", accLevelType);
				insertMap.put("upper_acc_level_type", 0);
				insertMap.put("sc", 0);
				insertMap.put("bc", 0);
				insertMap.put("co", 0);
				insertMap.put("sa", 0);
				insertMap.put("ag", 0);

				long count = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_manager_acc_level", insertMap);
				LOG.debug("addComScLevel===" + count);
				if (count == -1) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return false;
	}

	/**
	 * addLevel(manager) to ct_level_table by insertNoCommitByMap(no
	 * this.WRITE_CONN.commit())
	 */
	@Override
	public boolean addLevel(long accId, long upAccId) throws SQLException {
		long count = 0;
		StringBuilder sb = null;
		List<Object> insertList = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				sb = new StringBuilder();
				insertList = new ArrayList<Object>();
				sb.append(
						"	INSERT INTO ctt_manager.ctt_manager_acc_level(acc_id,acc_level_type,upper_acc_level_type,sc,bc,co,sa,ag,ag1,ag2,ag3,ag4,ag5,ag6,ag7,ag8,ag9,ag10)	\n");
				sb.append("	(SELECT ? as acc_id, acc_level_type+1 as acc_level_type,acc_level_type as upper_acc_level_type,	\n");
				insertList.add(accId);
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN acc_id ELSE sc END AS sc ,	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN acc_id ELSE bc END AS bc,	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN acc_id ELSE co END AS co,	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN acc_id ELSE sa END AS sa,	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 WHEN " + ACC_LEVEL_AG + " THEN acc_id ELSE ag END AS ag, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1
						+ " THEN acc_id ELSE ag1 END AS ag1, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG2 + " THEN acc_id ELSE ag2 END AS ag2, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG2 + " THEN 0 WHEN " + ACC_LEVEL_AG3 + " THEN acc_id ELSE ag3 END AS ag3, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG2 + " THEN 0 WHEN " + ACC_LEVEL_AG3 + " THEN 0 WHEN " + ACC_LEVEL_AG4
						+ " THEN acc_id ELSE ag4 END AS ag4, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG2 + " THEN 0 WHEN " + ACC_LEVEL_AG3 + " THEN 0 WHEN " + ACC_LEVEL_AG4 + " THEN 0 WHEN " + ACC_LEVEL_AG5
						+ " THEN acc_id ELSE ag5 END AS ag5, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG2 + " THEN 0 WHEN " + ACC_LEVEL_AG3 + " THEN 0 WHEN " + ACC_LEVEL_AG4 + " THEN 0 WHEN " + ACC_LEVEL_AG5
						+ " THEN 0 WHEN " + ACC_LEVEL_AG6 + " THEN acc_id ELSE ag6 END AS ag6, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG2 + " THEN 0 WHEN " + ACC_LEVEL_AG3 + " THEN 0 WHEN " + ACC_LEVEL_AG4 + " THEN 0 WHEN " + ACC_LEVEL_AG5
						+ " THEN 0 WHEN " + ACC_LEVEL_AG6 + " THEN 0 WHEN " + ACC_LEVEL_AG7 + " THEN acc_id ELSE ag7 END AS ag7, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG2 + " THEN 0 WHEN " + ACC_LEVEL_AG3 + " THEN 0 WHEN " + ACC_LEVEL_AG4 + " THEN 0 WHEN " + ACC_LEVEL_AG5
						+ " THEN 0 WHEN " + ACC_LEVEL_AG6 + " THEN 0 WHEN " + ACC_LEVEL_AG7 + " THEN 0 WHEN " + ACC_LEVEL_AG8
						+ " THEN acc_id ELSE ag8 END AS ag8, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG2 + " THEN 0 WHEN " + ACC_LEVEL_AG3 + " THEN 0 WHEN " + ACC_LEVEL_AG4 + " THEN 0 WHEN " + ACC_LEVEL_AG5
						+ " THEN 0 WHEN " + ACC_LEVEL_AG6 + " THEN 0 WHEN " + ACC_LEVEL_AG7 + " THEN 0 WHEN " + ACC_LEVEL_AG8 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG9 + " THEN acc_id ELSE ag9 END AS ag9, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG2 + " THEN 0 WHEN " + ACC_LEVEL_AG3 + " THEN 0 WHEN " + ACC_LEVEL_AG4 + " THEN 0 WHEN " + ACC_LEVEL_AG5
						+ " THEN 0 WHEN " + ACC_LEVEL_AG6 + " THEN 0 WHEN " + ACC_LEVEL_AG7 + " THEN 0 WHEN " + ACC_LEVEL_AG8 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG9 + " THEN 0 WHEN " + ACC_LEVEL_AG10 + " THEN acc_id ELSE ag10 END AS ag10 	\n");
				sb.append("	FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ? AND  acc_level_type>" + ACC_LEVEL_COM + " AND acc_level_type<"
						+ ACC_LEVEL_MEM + ")	\n");
				insertList.add(upAccId);

				count = StmtUtil.insertNoCommit(this.WRITE_CONN, sb.toString(), insertList);

				if (count != 0) {
					return true;
				}
			}
		} catch (Exception e) {
			count = 0;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertList != null) {
				insertList.clear();
				insertList = null;
			}

		}
		return false;
	}

	/**
	 * addLevel(member) to ct_level_table by insertNoCommitByMap(no
	 * this.WRITE_CONN.commit())
	 */
	@Override
	public boolean addMemberLevel(long accId, long upAccId) throws SQLException {
		long count = 0;
		StringBuilder sb = null;
		List<Object> insertList = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				sb = new StringBuilder();
				insertList = new ArrayList<Object>();

				sb.append(
						"	INSERT INTO ctt_manager.ctt_member_acc_level(acc_id,upper_acc_level_type,sc,bc,co,sa,ag,ag1,ag2,ag3,ag4,ag5,ag6,ag7,ag8,ag9,ag10)	\n");
				sb.append("	(SELECT ? as acc_id, acc_level_type as upper_acc_level_type,	\n");
				insertList.add(accId);
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN acc_id ELSE sc END AS sc ,	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN acc_id ELSE bc END AS bc,	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN acc_id ELSE co END AS co,	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN acc_id ELSE sa END AS sa,	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN acc_id ELSE ag END AS ag, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN acc_id ELSE ag1 END AS ag1, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN " + ACC_LEVEL_AG2
						+ " THEN acc_id ELSE ag2 END AS ag2, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN " + ACC_LEVEL_AG2 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG3 + " THEN acc_id ELSE ag3 END AS ag3, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN " + ACC_LEVEL_AG2 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG3 + " THEN 0 WHEN " + ACC_LEVEL_AG4 + " THEN acc_id ELSE ag4 END AS ag4, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN " + ACC_LEVEL_AG2 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG3 + " THEN 0 WHEN " + ACC_LEVEL_AG4 + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN acc_id ELSE ag5 END AS ag5, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN " + ACC_LEVEL_AG2 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG3 + " THEN 0 WHEN " + ACC_LEVEL_AG4 + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN 0 WHEN " + ACC_LEVEL_AG6 + " THEN acc_id ELSE ag6 END AS ag6, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN " + ACC_LEVEL_AG2 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG3 + " THEN 0 WHEN " + ACC_LEVEL_AG4 + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN 0 WHEN " + ACC_LEVEL_AG6 + " THEN 0 WHEN " + ACC_LEVEL_AG7
						+ " THEN acc_id ELSE ag7 END AS ag7, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN " + ACC_LEVEL_AG2 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG3 + " THEN 0 WHEN " + ACC_LEVEL_AG4 + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN 0 WHEN " + ACC_LEVEL_AG6 + " THEN 0 WHEN " + ACC_LEVEL_AG7 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG8 + " THEN acc_id ELSE ag8 END AS ag8, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN " + ACC_LEVEL_AG2 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG3 + " THEN 0 WHEN " + ACC_LEVEL_AG4 + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN 0 WHEN " + ACC_LEVEL_AG6 + " THEN 0 WHEN " + ACC_LEVEL_AG7 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG8 + " THEN 0 WHEN " + ACC_LEVEL_AG9 + " THEN acc_id ELSE ag9 END AS ag9, 	\n");
				sb.append("	CASE acc_level_type WHEN " + ACC_LEVEL_SC + " THEN 0 WHEN " + ACC_LEVEL_BC + " THEN 0 WHEN " + ACC_LEVEL_CO
						+ " THEN 0 WHEN " + ACC_LEVEL_SA + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN 0 WHEN " + ACC_LEVEL_AG1 + " THEN 0 WHEN " + ACC_LEVEL_AG2 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG3 + " THEN 0 WHEN " + ACC_LEVEL_AG4 + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN 0 WHEN " + ACC_LEVEL_AG6 + " THEN 0 WHEN " + ACC_LEVEL_AG7 + " THEN 0 WHEN "
						+ ACC_LEVEL_AG8 + " THEN 0 WHEN " + ACC_LEVEL_AG9 + " THEN 0 	\n");
				sb.append("	WHEN " + ACC_LEVEL_AG10 + " THEN acc_id ELSE ag10 END AS ag10 	\n");
				sb.append("	FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ? AND  acc_level_type>" + ACC_LEVEL_COM + " AND acc_level_type<"
						+ ACC_LEVEL_MEM + ")	\n");
				insertList.add(upAccId);

				count = StmtUtil.insertNoCommit(this.WRITE_CONN, sb.toString(), insertList);

				if (count != 0) {
					return true;
				}
			}
		} catch (Exception e) {
			count = 0;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertList != null) {
				insertList.clear();
				insertList = null;
			}
		}
		return false;

	}

	/**
	 * addComaccountinfo to ctt_manager_acc by insertNoCommitByMap(no
	 * this.WRITE_CONN.commit())
	 */
	@Override
	public long addCom(String accName, String pwd, String nickname, int authGroup) throws SQLException {
		LOG.debug("addCom");
		long result = 0;
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {
				insertMap.put("acc_name", accName);
				insertMap.put("pwd", pwd);
				insertMap.put("nickname", nickname);
				insertMap.put("auth_group", authGroup);

				long count = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_manager_acc", insertMap);
				LOG.debug("addCom===" + count);
				if (count > 0) {
					return count;
				}
			}
		} catch (Exception e) {
			result = 0;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return result;
	}

	/**
	 * addMemberaccountinfo to ctt_member_acc by insertNoCommitByMap(no
	 * this.WRITE_CONN.commit())
	 */
	@Override
	public long addMember(String accName, String pwd, String nickname, int memberType,int handicap) throws SQLException {
		LOG.debug("addMember");
		long count = 0;
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return count;
			} else {
				insertMap.put("acc_name", accName);
				insertMap.put("pwd", pwd);
				insertMap.put("nickname", nickname);
				insertMap.put("member_type", memberType);
				insertMap.put("pwd_status", 1);
				if (memberType == GRNERAL_MEM) {
					insertMap.put("pwd_withdraw_status", 1);
				} else if (memberType == DIRECTLY_UNDER_MEM) {
					insertMap.put("pwd_withdraw_status", 0);
				}
				insertMap.put("pwd_withdraw", "");
				insertMap.put("handicap", handicap);
				

				count = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_member_acc", insertMap);
				LOG.debug("addMember===" + count);
				if (count > 0) {
					return count;
				}
			}
		} catch (Exception e) {
			count = 0;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return count;
	}

	/**
	 * addaddManagerRatioinfo to ctt_manager_ratio by insertNoCommitByMap(no
	 * this.WRITE_CONN.commit())
	 */
	@Override
	public boolean addManagerRatio(long accId, int gameType, int upperMinRatio, int upperMaxRatio, int currentMaxRatio) throws SQLException {
		Map<String, Object> insertMap = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				insertMap = new ConcurrentHashMap<String, Object>();
				LOG.debug("addManagerRatio DAO ==>" + accId + "," + gameType + "," + upperMinRatio + "," + upperMaxRatio + "," + currentMaxRatio);
				insertMap.put("acc_id", accId);
				insertMap.put("game_type", gameType);
				insertMap.put("upper_min_ratio", upperMinRatio);
				insertMap.put("upper_max_ratio", upperMaxRatio);
				insertMap.put("current_max_ratio", currentMaxRatio);
				insertMap.put("next_upper_min_ratio", upperMinRatio);
				insertMap.put("next_upper_max_ratio", upperMaxRatio);
				insertMap.put("next_current_max_ratio", currentMaxRatio);
				long count = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_manager_ratio", insertMap);
				if (count == -1) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return false;
	}

	/**
	 * addMemberRatioinfo to ctt_member_ratio by insertNoCommitByMap(no
	 * this.WRITE_CONN.commit())
	 */
	@Override
	public boolean addMemberRatio(long accId, int gameType, int upperMinRatio) throws SQLException {
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				LOG.debug(accId + "," + gameType + "," + upperMinRatio);
				insertMap.put("acc_id", accId);
				insertMap.put("game_type", Byte.parseByte("" + gameType));
				insertMap.put("upper_min_ratio", Byte.parseByte("" + upperMinRatio));
				insertMap.put("next_upper_min_ratio", Byte.parseByte("" + upperMinRatio));

				long count = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_member_ratio", insertMap);
				LOG.debug("addMemberRatio===" + count);
				if (count != 0) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return false;
	}

	@Override
	public boolean memberRatioTotal(long memberId, int gameType, List<Integer> ratio) throws SQLException {
		StringBuilder sb = null;
		Map<String, Object> insertMap = null;

		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				sb = new StringBuilder();
				insertMap = new ConcurrentHashMap<String, Object>();
				LOG.debug("ratio:" + ratio);
				insertMap.put("acc_id", memberId);
				insertMap.put("game_type", gameType);

				String[] managerRatioArr = { "com_ratio", "sc_ratio", "bc_ratio", "co_ratio", "sa_ratio", "ag_ratio", "ag1_ratio", "ag2_ratio",
						"ag3_ratio", "ag4_ratio", "ag5_ratio", "ag6_ratio", "ag7_ratio", "ag8_ratio", "ag9_ratio", "ag10_ratio" };
				String[] nextManagerRatioArr = { "next_com_ratio", "next_sc_ratio", "next_bc_ratio", "next_co_ratio", "next_sa_ratio",
						"next_ag_ratio", "next_ag1_ratio", "next_ag2_ratio", "next_ag3_ratio", "next_ag4_ratio", "next_ag5_ratio", "next_ag6_ratio",
						"next_ag7_ratio", "next_ag8_ratio", "next_ag9_ratio", "next_ag10_ratio" };

				for (int i = 0; i < managerRatioArr.length; i++) {
					if (i < ratio.size()) {
						insertMap.put(managerRatioArr[i], ratio.get(i));
						insertMap.put(nextManagerRatioArr[i], ratio.get(i));
					} else {
						insertMap.put(managerRatioArr[i], 0);
						insertMap.put(nextManagerRatioArr[i], 0);
					}
				}

				long cou = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_member_total_ratio", insertMap);

				if (cou != 0) {
					return true;
				}

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> createAuth(int accLevelType, long accId) throws SQLException {
		LOG.debug("createAuth");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		try {
			sb.append("select  \n");
			sb.append("case when A.acc_level_type=0 then 	B.group_id  else C.group_id end as groupId, \n");
			sb.append("case when A.acc_level_type=0 then 	B.group_text  else C.group_text end as groupText \n");
			sb.append("from  \n");
			sb.append("( \n");
			sb.append("select acc_id,acc_level_type from `ctt_manager`.`ctt_manager_acc_level` where acc_id=?  \n");
			sb.append(") A \n");
			sb.append("left join( \n");
			sb.append("select group_id ,group_text,create_datetime,update_datetime  from `ctt_manager`.`ctt_auth_group` \n");
			sb.append(") B \n");
			sb.append("on A.acc_level_type=0 \n");
			sb.append("left join \n");
			sb.append("( \n");
			sb.append("select C1.group_id, C1.group_text from \n");
			sb.append("(select * from `ctt_manager`.`acclevel_init_auth` where acc_level_type=?) C2 \n");
			sb.append("left join  \n");
			sb.append("( \n");
			sb.append("select * from `ctt_manager`.`ctt_auth_group` \n");
			sb.append(")C1 \n");
			sb.append("on C1.group_id=C2.group_id \n");
			sb.append(")C \n");
			sb.append("on A.acc_level_type!=0 \n");

			params.add(accId);
			params.add(accLevelType);
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {
				result = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				if (result != null) {
					return result;
				}
			}
		} catch (Exception e) {
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
		}
		return result;
	}

	private String getMapString(Map<String, Object> map, String key) {
		if (!map.keySet().contains(key) || map.get(key) == null) {
			return "";
		} else {
			return "" + map.get(key);
		}
	}

	@Override
	public MgrBean getSeetingComData(long accId) throws SQLException {
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		MgrBean bean = new MgrBean();
		List<Object> list = new ArrayList<Object>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				sb.append("	select A.acc_id , A.acc_name , A.nickname,A.auth_group,B.group_text from	\n");
				sb.append("	(select acc_id,acc_name,nickname,auth_group from ctt_manager.ctt_manager_acc where acc_id = ?)A	\n");
				sb.append("	inner join 	\n");
				sb.append("	(select group_id , group_text from ctt_manager.ctt_auth_group)B	\n");
				sb.append("	on A.auth_group = B.group_id	\n");
				params.add(accId);
				list = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), params, bean);
				if (list.size() == 1) {
					bean = (MgrBean) list.get(0);
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params = new ArrayList<Object>();
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
		return bean;
	}

	@Override
	public MgrBean getSeetingMagData(long accId) {
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		MgrBean bean = new MgrBean();
		List<Object> list = new ArrayList<Object>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				sb.append("	select A.acc_id,A.acc_name,A.nickname,A.auth_group,A.handicap,C.group_text,A.full_ratio,A.next_full_ratio,A.balance, 	\n");
				sb.append("	B1.upper_min_ratio as g1MinRatio, 	\n");
				sb.append("	B1.upper_max_ratio as g1MaxRatio, 	\n");
				sb.append("	B1.current_max_ratio as g1CurrentMaxRatio, 	\n");
				sb.append("	B2.upper_min_ratio as g2MinRatio, 	\n");
				sb.append("	B2.upper_max_ratio as g2MaxRatio, 	\n");
				sb.append("	B2.current_max_ratio as g2CurrentMaxRatio, 	\n");
				sb.append("	B3.upper_min_ratio as g3MinRatio, 	\n");
				sb.append("	B3.upper_max_ratio as g3MaxRatio, 	\n");
				sb.append("	B3.current_max_ratio as g3CurrentMaxRatio, 	\n");
				sb.append("	B4.upper_min_ratio as g4MinRatio, 	\n");
				sb.append("	B4.upper_max_ratio as g4MaxRatio, 	\n");
				sb.append("	B4.current_max_ratio as g4CurrentMaxRatio, 	\n");
				sb.append("	B5.upper_min_ratio as g5MinRatio, 	\n");
				sb.append("	B5.upper_max_ratio as g5MaxRatio, 	\n");
				sb.append("	B5.current_max_ratio as g5CurrentMaxRatio, 	\n");
				sb.append("	B1.next_upper_min_ratio as g1MinRatioNext, 	\n");
				sb.append("	B1.next_upper_max_ratio as g1MaxRatioNext, 	\n");
				sb.append("	B1.next_current_max_ratio as g1CurrentMaxRatioNext, 	\n");
				sb.append("	B2.next_upper_min_ratio as g2MinRatioNext, 	\n");
				sb.append("	B2.next_upper_max_ratio as g2MaxRatioNext, 	\n");
				sb.append("	B2.next_current_max_ratio as g2CurrentMaxRatioNext, 	\n");
				sb.append("	B3.next_upper_min_ratio as g3MinRatioNext, 	\n");
				sb.append("	B3.next_upper_max_ratio as g3MaxRatioNext, 	\n");
				sb.append("	B3.next_current_max_ratio as g3CurrentMaxRatioNext, 	\n");
				sb.append("	B4.next_upper_min_ratio as g4MinRatioNext, 	\n");
				sb.append("	B4.next_upper_max_ratio as g4MaxRatioNext, 	\n");
				sb.append("	B4.next_current_max_ratio as g4CurrentMaxRatioNext, 	\n");
				sb.append("	B5.next_upper_min_ratio as g5MinRatioNext, 	\n");
				sb.append("	B5.next_upper_max_ratio as g5MaxRatioNext, 	\n");
				sb.append("	B5.next_current_max_ratio as g5CurrentMaxRatioNext 	\n");
				sb.append("	from ( 	\n");
				sb.append(
						"	(select acc_id,acc_name,nickname,auth_group,balance,full_ratio,next_full_ratio,handicap from ctt_manager.ctt_manager_acc where acc_id = ?) A 	\n");
				params.add(accId);
				sb.append("	inner join  	\n");
				sb.append("	(select * from ctt_manager.ctt_manager_ratio where game_type = " + LOTTERY + ") B1 	\n");
				sb.append("	on A.acc_id = B1.acc_id  	\n");
				sb.append("	inner join  	\n");
				sb.append("	(select * from ctt_manager.ctt_manager_ratio where game_type = " + LIVEVIDEO + ") B2 	\n");
				sb.append("	on A.acc_id = B2.acc_id  	\n");
				sb.append("	inner join  	\n");
				sb.append("	(select * from ctt_manager.ctt_manager_ratio where game_type = " + MOVEMENT + ") B3 	\n");
				sb.append("	on A.acc_id = B3.acc_id  	\n");
				sb.append("	inner join  	\n");
				sb.append("	(select * from ctt_manager.ctt_manager_ratio where game_type = " + VIDEOGAME + ") B4 	\n");
				sb.append("	on A.acc_id = B4.acc_id  	\n");
				sb.append("	inner join  	\n");
				sb.append("	(select * from ctt_manager.ctt_manager_ratio where game_type = " + GAME + ") B5 	\n");
				sb.append("	on A.acc_id = B5.acc_id	\n");
				sb.append("	inner join	\n");
				sb.append("	(select group_id,group_text from ctt_manager.ctt_auth_group) C	\n");
				sb.append("	on A.auth_group = C.group_id 	\n");
				sb.append("	) 	\n");

				list = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), params, bean);
				if (list.size() == 1) {
					bean = (MgrBean) list.get(0);
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params = new ArrayList<Object>();
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
		return bean;
	}

	@Override
	public MemBean getSeetingMemData(long accId) throws SQLException {
		MemBean bean = null;
		StringBuilder sb = null;
		List<Object> params = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return bean;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();

				sb.append("select A.acc_id,A.acc_name,A.nickname,A.member_type ,A.balance,A.handicap,\n");
				sb.append("B1.upper_min_ratio as g1MinRatio, \n");
				sb.append("B2.upper_min_ratio as g2MinRatio, \n");
				sb.append("B3.upper_min_ratio as g3MinRatio, \n");
				sb.append("B4.upper_min_ratio as g4MinRatio, \n");
				sb.append("B5.upper_min_ratio as g5MinRatio, \n");
				sb.append("B1.next_upper_min_ratio as g1MinRatioNext, \n");
				sb.append("B2.next_upper_min_ratio as g2MinRatioNext, \n");
				sb.append("B3.next_upper_min_ratio as g3MinRatioNext, \n");
				sb.append("B4.next_upper_min_ratio as g4MinRatioNext, \n");
				sb.append("B5.next_upper_min_ratio as g5MinRatioNext,C.upper_acc_level_type \n");
				sb.append("from ( \n");
				sb.append("(select acc_id,acc_name,balance,nickname,member_type,handicap from ctt_manager.ctt_member_acc where acc_id = ?) A \n");
				params.add(accId);
				sb.append("inner join  \n");
				sb.append("(select * from ctt_manager.ctt_member_ratio where game_type = " + LOTTERY + ") B1 \n");
				sb.append("on A.acc_id = B1.acc_id            \n");
				sb.append("inner join  \n");
				sb.append("(select * from ctt_manager.ctt_member_ratio where game_type = " + LIVEVIDEO + ") B2 \n");
				sb.append("on A.acc_id = B2.acc_id \n");
				sb.append("inner join \n");
				sb.append("(select * from ctt_manager.ctt_member_ratio where game_type = " + MOVEMENT + ") B3 \n");
				sb.append("on A.acc_id = B3.acc_id      \n");
				sb.append("inner join \n");
				sb.append("(select * from ctt_manager.ctt_member_ratio where game_type = " + VIDEOGAME + ") B4\n");
				sb.append("on A.acc_id = B4.acc_id                                         \n");
				sb.append("inner join \n");
				sb.append("(select * from ctt_manager.ctt_member_ratio where game_type = " + GAME + ") B5\n");
				sb.append("on A.acc_id = B5.acc_id                                             \n");
				sb.append("inner join \n");
				sb.append("(select acc_id , upper_acc_level_type from ctt_manager.ctt_member_acc_level) C\n");
				sb.append("on A.acc_id = C.acc_id\n");
				sb.append(")\n");

				List<Object> list = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), params, new MemBean());
				if (list.size() == 1) {
					bean = (MemBean) list.get(0);
				}

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params.clear();
			}
			params = null;
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}

		return bean;
	}

	@Override
	public Map<String, Object> getSeetingMagUpData(long accId) {
		Map<String, Object> result = null;
		StringBuilder sb = null;
		List<Object> params = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {
				result = new ConcurrentHashMap<String, Object>();
				params = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("	select A.acc_id as upAccId, A.acc_name as upAccName, A.balance as upBalance, A.next_full_ratio as upNextFullRatio, A.handicap 	\n");
				sb.append("	, B1.next_current_max_ratio as upG1CurrentRatioNext 	\n");
				sb.append("	,  B2.next_current_max_ratio as upG2CurrentRatioNext 	\n");
				sb.append("	,  B3.next_current_max_ratio as upG3CurrentRatioNext 	\n");
				sb.append("	,  B4.next_current_max_ratio as upG4CurrentRatioNext 	\n");
				sb.append("	,  B5.next_current_max_ratio as upG5CurrentRatioNext  	\n");
				sb.append("	, B1.current_max_ratio as upG1CurrentRatio 	\n");
				sb.append("	,  B2.current_max_ratio as upG2CurrentRatio 	\n");
				sb.append("	,  B3.current_max_ratio as upG3CurrentRatio 	\n");
				sb.append("	,  B4.current_max_ratio as upG4CurrentRatio 	\n");
				sb.append("	,  B5.current_max_ratio as upG5CurrentRatio  	\n");
				sb.append("	from( 	\n");
				sb.append("	( 	\n");
				sb.append("	select acc_id, acc_name, nickname, balance, next_full_ratio , handicap from `ctt_manager`.`ctt_manager_acc` where acc_id =  \n");
				sb.append("	( 	\n");
				sb.append("	select  	\n");
				sb.append("	case 	\n");
				sb.append("	when acc_level_type = " + ACC_LEVEL_BC + " then sc	\n");
				sb.append("	when acc_level_type = " + ACC_LEVEL_CO + " then bc	\n");
				sb.append("	when acc_level_type = " + ACC_LEVEL_SA + " then co	\n");
				sb.append("	when acc_level_type = " + ACC_LEVEL_AG + " then sa	\n");
				sb.append("	when acc_level_type = " + ACC_LEVEL_AG1 + " then ag	\n");
				sb.append("	when acc_level_type = " + ACC_LEVEL_AG2 + " then ag1	\n");
				sb.append("	when acc_level_type = " + ACC_LEVEL_AG3 + " then ag2	\n");
				sb.append("	when acc_level_type = " + ACC_LEVEL_AG4 + " then ag3	\n");
				sb.append("	when acc_level_type = " + ACC_LEVEL_AG5 + " then ag4	\n");
				sb.append("	when acc_level_type = " + ACC_LEVEL_AG6 + " then ag5	\n");
				sb.append("	when acc_level_type = " + ACC_LEVEL_AG7 + " then ag6	\n");
				sb.append("	when acc_level_type = " + ACC_LEVEL_AG8 + " then ag7	\n");
				sb.append("	when acc_level_type = " + ACC_LEVEL_AG9 + " then ag8	\n");
				sb.append("	when acc_level_type = " + ACC_LEVEL_AG10 + " then ag9	\n");
				sb.append("	else 0 end  as upper_acc_id 	\n");
				sb.append("	from `ctt_manager`.`ctt_manager_acc_level` where acc_id=? 	\n");
				sb.append("	) ) A 	\n");
				sb.append("	inner join 	\n");
				sb.append("	(select  acc_id, next_current_max_ratio,current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type="
						+ LOTTERY + ")B1 	\n");
				sb.append("	on B1.acc_id = A.acc_id 	\n");
				sb.append("	inner join 	\n");
				sb.append("	(select  acc_id, next_current_max_ratio,current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type="
						+ LIVEVIDEO + ")B2 	\n");
				sb.append("	on B2.acc_id = A.acc_id 	\n");
				sb.append("	inner join 	\n");
				sb.append("	(select  acc_id, next_current_max_ratio,current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type="
						+ MOVEMENT + ")B3 	\n");
				sb.append("	on B3.acc_id = A.acc_id 	\n");
				sb.append("	inner join 	\n");
				sb.append("	(select  acc_id, next_current_max_ratio,current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type="
						+ VIDEOGAME + ")B4 	\n");
				sb.append("	on B4.acc_id = A.acc_id 	\n");
				sb.append("	inner join 	\n");
				sb.append("	(select  acc_id, next_current_max_ratio,current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=" + GAME
						+ ")B5 	\n");
				sb.append("	on B5.acc_id = A.acc_id 	\n");
				sb.append("	) 	\n");

				params.add(accId);

				List<Map<String, Object>> listMapUpAccData = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);

				for (int i = 0; i < listMapUpAccData.size(); i++) {
					for (Object key : listMapUpAccData.get(i).keySet()) {
						result.put(key.toString(), listMapUpAccData.get(i).get(key.toString()));
					}
				}

			}
		} catch (Exception e) {
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
		}
		return result;
	}

	@Override
	public Map<String, Object> getSeetingMemUpData(long accId) {
		Map<String, Object> result = null;
		StringBuilder sb = null;
		List<Object> params = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {
				result = new ConcurrentHashMap<String, Object>();
				params = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append(
						"	select A.acc_id as upAccId, A.acc_name as upAccName, A.balance as upBalance, A.next_full_ratio as upNextFullRatio,A.handicap 		\n");
				sb.append("	, B1.next_current_max_ratio as upG1CurrentRatioNext 		\n");
				sb.append("	,  B2.next_current_max_ratio as upG2CurrentRatioNext 		\n");
				sb.append("	,  B3.next_current_max_ratio as upG3CurrentRatioNext 		\n");
				sb.append("	,  B4.next_current_max_ratio as upG4CurrentRatioNext 		\n");
				sb.append("	,  B5.next_current_max_ratio as upG5CurrentRatioNext  		\n");
				sb.append("	, B1.current_max_ratio as upG1CurrentRatio 		\n");
				sb.append("	,  B2.current_max_ratio as upG2CurrentRatio 		\n");
				sb.append("	,  B3.current_max_ratio as upG3CurrentRatio 		\n");
				sb.append("	,  B4.current_max_ratio as upG4CurrentRatio 		\n");
				sb.append("	,  B5.current_max_ratio as upG5CurrentRatio  		\n");
				sb.append("	from( 		\n");
				sb.append("	( 		\n");
				sb.append("	select acc_id, acc_name, nickname, balance, next_full_ratio,handicap from `ctt_manager`.`ctt_manager_acc` where acc_id =  	\n");
				sb.append("	( 		\n");
				sb.append("	select  		\n");
				sb.append("	case 	\n");
				sb.append("	when upper_acc_level_type = " + ACC_LEVEL_SC + " then sc			\n");
				sb.append("	when upper_acc_level_type = " + ACC_LEVEL_BC + " then bc		\n");
				sb.append("	when upper_acc_level_type = " + ACC_LEVEL_CO + " then co		\n");
				sb.append("	when upper_acc_level_type = " + ACC_LEVEL_SA + " then sa		\n");
				sb.append("	when upper_acc_level_type = " + ACC_LEVEL_AG + " then ag		\n");
				sb.append("	when upper_acc_level_type = " + ACC_LEVEL_AG1 + " then ag1		\n");
				sb.append("	when upper_acc_level_type = " + ACC_LEVEL_AG2 + " then ag2		\n");
				sb.append("	when upper_acc_level_type = " + ACC_LEVEL_AG3 + " then ag3		\n");
				sb.append("	when upper_acc_level_type = " + ACC_LEVEL_AG4 + " then ag4		\n");
				sb.append("	when upper_acc_level_type = " + ACC_LEVEL_AG5 + " then ag5		\n");
				sb.append("	when upper_acc_level_type = " + ACC_LEVEL_AG6 + " then ag6		\n");
				sb.append("	when upper_acc_level_type = " + ACC_LEVEL_AG7 + " then ag7		\n");
				sb.append("	when upper_acc_level_type = " + ACC_LEVEL_AG8 + " then ag8		\n");
				sb.append("	when upper_acc_level_type = " + ACC_LEVEL_AG9 + " then ag9		\n");
				sb.append("	when upper_acc_level_type = " + ACC_LEVEL_AG10 + " then ag10		\n");
				sb.append("	else 0 end  as upper_acc_id 		\n");
				sb.append("	from `ctt_manager`.`ctt_member_acc_level` where acc_id=? 		\n");
				params.add(accId);
				sb.append("	) ) A 		\n");
				sb.append("	inner join 		\n");
				sb.append(
						"	(select  acc_id, next_current_max_ratio,current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=1)B1 		\n");
				sb.append("	on B1.acc_id = A.acc_id 		\n");
				sb.append("	inner join 		\n");
				sb.append(
						"	(select  acc_id, next_current_max_ratio,current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=2)B2 		\n");
				sb.append("	on B2.acc_id = A.acc_id 		\n");
				sb.append("	inner join 		\n");
				sb.append(
						"	(select  acc_id, next_current_max_ratio,current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=3)B3 		\n");
				sb.append("	on B3.acc_id = A.acc_id 		\n");
				sb.append("	inner join 		\n");
				sb.append(
						"	(select  acc_id, next_current_max_ratio,current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=4)B4 		\n");
				sb.append("	on B4.acc_id = A.acc_id 		\n");
				sb.append("	inner join 		\n");
				sb.append(
						"	(select  acc_id, next_current_max_ratio,current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=5)B5 		\n");
				sb.append("	on B5.acc_id = A.acc_id 		\n");
				sb.append("	) 		\n");
				sb.append("		\n");

				List<Map<String, Object>> listMapUpAccData = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);

				for (int i = 0; i < listMapUpAccData.size(); i++) {
					for (Object key : listMapUpAccData.get(i).keySet()) {
						result.put(key.toString(), listMapUpAccData.get(i).get(key.toString()));
					}
				}

			}
		} catch (Exception e) {
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
		}
		return result;
	}

	@Override
	public boolean updateMgrAcc(long accId, String pwd, String nickname, int authGroup, int fullRatio , int handicap) throws SQLException {
		Map<String, Object> updateMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				updateMap.put("acc_id", accId);
				if (pwd != null && !"".equals(pwd)) {
					updateMap.put("pwd", pwd);
				}
				if (nickname != null && !"".equals(nickname)) {
					updateMap.put("nickname", nickname);
				}
				if (authGroup > 0) {
					updateMap.put("auth_group", authGroup);
				}
				if (fullRatio >= 0) {
					updateMap.put("next_full_ratio", fullRatio);
				}
				updateMap.put("handicap", handicap);
				
				long count = StmtUtil.updateNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_manager_acc", updateMap, "acc_id");
				LOG.debug("updateComAcc===" + count);
				if (count != 0) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (updateMap != null) {
				updateMap.clear();
				updateMap = null;
			}
		}
		return false;
	}

	@Override
	public boolean updateMemAcc(long accId, String pwd, String nickname , int handicap) throws SQLException {
		Map<String, Object> updateMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				updateMap.put("acc_id", accId);

				if (nickname != null && !"".equals(nickname)) {
					updateMap.put("nickname", nickname);
				}
				updateMap.put("handicap", handicap);
				long count = StmtUtil.updateNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_member_acc", updateMap, "acc_id");
				LOG.debug("updateMemAcc===" + count);
				if (count != 0) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (updateMap != null) {
				updateMap.clear();
				updateMap = null;
			}
		}
		return false;
	}

	@Override
	public boolean updateLastUpdate(long accId, long updateaccId, String ip, String lastUpdatetable) throws SQLException {
		ip = (ip == null || ip.isEmpty() || "".equals(ip.trim())) ? "0.0.0.0" : ip;
		String[] ipArray = ip.split("\\.");
		Map<String, Object> updateMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				updateMap.put("acc_id", accId);
				if (updateaccId > 0) {
					updateMap.put("update_acc_id", updateaccId);
				}
				if (ip != "0.0.0.0") {
					updateMap.put("ip1", Integer.parseInt(ipArray[0]));
					updateMap.put("ip2", Integer.parseInt(ipArray[1]));
					updateMap.put("ip3", Integer.parseInt(ipArray[2]));
					updateMap.put("ip4", Integer.parseInt(ipArray[3]));
				}
				long count = StmtUtil.updateNoCommitByMap(this.WRITE_CONN, lastUpdatetable, updateMap, "acc_id");
				LOG.debug("updateLastUpdate===" + count);
				if (count == 1) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (updateMap != null) {
				updateMap.clear();
				updateMap = null;
			}
		}
		return false;
	}

	@Override
	public boolean updateMgrRatio(long accId, int gameType, int upperMinRatio, int upperMaxRatio, int currentMaxRatio) throws SQLException {
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		int count = 0;
		int psParamsIndex = 1;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				sb.append("update ctt_manager.ctt_manager_ratio \n");
				sb.append("set \n");
				if (upperMinRatio >= 0) {
					sb.append("next_upper_min_ratio=?\n");
				}
				if (upperMinRatio >= 0 && upperMaxRatio >= 0) {
					sb.append(",\n");
				}
				if (upperMaxRatio >= 0) {
					sb.append("next_upper_max_ratio=?\n");
				}
				if (upperMinRatio >= 0 && currentMaxRatio >= 0) {
					sb.append(",\n");
				}
				if (currentMaxRatio >= 0) {
					sb.append("next_current_max_ratio=?\n");
				}
				sb.append("where acc_id = ? and game_type=? \n");
				if (this.WRITE_CONN.getAutoCommit()) {
					this.WRITE_CONN.setAutoCommit(false);
				}
				ps = this.WRITE_CONN.prepareStatement(sb.toString());
				if (upperMinRatio >= 0) {
					ps.setInt(psParamsIndex, upperMinRatio);
					psParamsIndex++;
				}
				if (upperMaxRatio >= 0) {
					ps.setInt(psParamsIndex, upperMaxRatio);
					psParamsIndex++;
				}
				if (currentMaxRatio >= 0) {
					ps.setInt(psParamsIndex, currentMaxRatio);
					psParamsIndex++;
				}
				ps.setLong(psParamsIndex, accId);
				psParamsIndex++;
				ps.setInt(psParamsIndex, gameType);
				count = ps.executeUpdate();
				LOG.debug("updateMgrRatio===" + count);
				return true;
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		} finally {
			sb = null;
			ps = null;
		}

	}

	@Override
	public boolean updateMemRatio(long accId, int gameType, int upperMinRatio) throws SQLException {
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		int count = 0;
		int psParamsIndex = 1;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				sb.append("update ctt_manager.ctt_member_ratio \n");
				sb.append("set \n");
				if (upperMinRatio >= 0) {
					sb.append("next_upper_min_ratio=?\n");
				}
				sb.append("where acc_id = ? and game_type=? \n");
				if (this.WRITE_CONN.getAutoCommit()) {
					this.WRITE_CONN.setAutoCommit(false);
				}
				ps = this.WRITE_CONN.prepareStatement(sb.toString());
				if (upperMinRatio >= 0) {
					ps.setInt(psParamsIndex, upperMinRatio);
					psParamsIndex++;
				}
				ps.setLong(psParamsIndex, accId);
				psParamsIndex++;
				ps.setInt(psParamsIndex, gameType);
				psParamsIndex++;
				count = ps.executeUpdate();
				LOG.debug("updateMgrRatio===" + count);
				return true;
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		} finally {
			sb = null;
			ps = null;
			count = 0;
			psParamsIndex = 0;
		}

	}

	public boolean updateMemberRatioTotal(long memberId, int gameType, List<Integer> ratio) throws SQLException {
		StringBuilder sb = null;
		List<Object> updateList = null;
		Map<String, Object> insertMap = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				sb = new StringBuilder();
				updateList = new ArrayList<Object>();
				insertMap = new ConcurrentHashMap<String, Object>();
				LOG.debug("ratio:" + ratio);

				insertMap.put("acc_id", memberId);
				insertMap.put("game_type", gameType);

				String[] managerRatioArr = { "com_ratio", "sc_ratio", "bc_ratio", "co_ratio", "sa_ratio", "ag_ratio", "ag1_ratio", "ag2_ratio",
						"ag3_ratio", "ag4_ratio", "ag5_ratio", "ag6_ratio", "ag7_ratio", "ag8_ratio", "ag9_ratio", "ag10_ratio" };
				String[] nextManagerRatioArr = { "next_com_ratio", "next_sc_ratio", "next_bc_ratio", "next_co_ratio", "next_sa_ratio",
						"next_ag_ratio", "next_ag1_ratio", "next_ag2_ratio", "next_ag3_ratio", "next_ag4_ratio", "next_ag5_ratio", "next_ag6_ratio",
						"next_ag7_ratio", "next_ag8_ratio", "next_ag9_ratio", "next_ag10_ratio" };

				for (int i = 0; i < managerRatioArr.length; i++) {
					if (i < ratio.size()) {
						insertMap.put(managerRatioArr[i], ratio.get(i));
						insertMap.put(nextManagerRatioArr[i], ratio.get(i));
					} else {
						insertMap.put(managerRatioArr[i], 0);
						insertMap.put(nextManagerRatioArr[i], 0);
					}
				}

				sb.append("update ctt_manager.ctt_member_total_ratio set \n");

				for (int i = 0; i < nextManagerRatioArr.length; i++) {
					if (i == (nextManagerRatioArr.length - 1)) {
						sb.append(nextManagerRatioArr[i] + " = ? \n");
						updateList.add(Integer.parseInt("" + insertMap.get(nextManagerRatioArr[i])));
					} else {
						sb.append(nextManagerRatioArr[i] + " = ? ,\n");
						updateList.add(Integer.parseInt("" + insertMap.get(nextManagerRatioArr[i])));
					}
				}
				sb.append("where acc_id = ? and game_type = ? \n");
				updateList.add(memberId);
				updateList.add(gameType);

				int count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateList);

				if (count != 0) {
					return true;
				}
				
			}

		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (updateList != null) {
				updateList.clear();
				updateList = null;
			}
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> findFullRatioMemAccId(long accId) {
		StringBuilder sb = new StringBuilder();
		List<Object> queryList = new ArrayList<Object>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				sb.append("SELECT \n");
				sb.append("acc_id \n");
				sb.append("FROM \r\n");
				sb.append("ctt_manager.ctt_member_acc_level \n");
				sb.append("WHERE \n");
				sb.append("(CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ? ) \n");
				queryList.add(accId);
				sb.append("WHEN 0 THEN FALSE \n");
				sb.append("WHEN 1 THEN FALSE \n");
				sb.append("WHEN 2 THEN sc \n");
				sb.append("WHEN 3 THEN bc \n");
				sb.append("WHEN 4 THEN co \n");
				sb.append("WHEN 5 THEN sa \n");
				sb.append("WHEN 6 THEN ag \n");
				sb.append("ELSE FALSE END)= ? \n");
				queryList.add(accId);
				sb.append("AND \n");
				sb.append("upper_acc_level_type = (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ? )");
				queryList.add(accId);
				LOG.debug("sql:\n" + sb.toString());

				List<Map<String, Object>> accIdMapList = StmtUtil.queryToMap(READ_CONN, sb.toString(), queryList);
				LOG.debug("accIdMapList===" + accIdMapList);
				return accIdMapList;
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
			queryList.clear();
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> findFullRatioMgrAccId(long accId) {
		StringBuilder sb = new StringBuilder();
		List<Object> queryList = new ArrayList<Object>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				sb.append("SELECT \n");
				sb.append("acc_id \n");
				sb.append("FROM \r\n");
				sb.append("ctt_manager.ctt_manager_acc_level \n");
				sb.append("WHERE \n");
				sb.append("(CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ? ) \n");
				queryList.add(accId);
				sb.append("WHEN " + ACC_LEVEL_ADMIN + " THEN FALSE \n");
				sb.append("WHEN " + ACC_LEVEL_COM + " THEN FALSE \n");
				sb.append("WHEN " + ACC_LEVEL_SC + " THEN sc \n");
				sb.append("WHEN " + ACC_LEVEL_BC + " THEN bc \n");
				sb.append("WHEN " + ACC_LEVEL_CO + " THEN co \n");
				sb.append("WHEN " + ACC_LEVEL_SA + " THEN sa \n");
				sb.append("WHEN " + ACC_LEVEL_AG + " THEN ag \n");
				sb.append("WHEN " + ACC_LEVEL_AG1 + " THEN ag1 \n");
				sb.append("WHEN " + ACC_LEVEL_AG2 + " THEN ag2 \n");
				sb.append("WHEN " + ACC_LEVEL_AG3 + " THEN ag3 \n");
				sb.append("WHEN " + ACC_LEVEL_AG4 + " THEN ag4 \n");
				sb.append("WHEN " + ACC_LEVEL_AG5 + " THEN ag5 \n");
				sb.append("WHEN " + ACC_LEVEL_AG6 + " THEN ag6 \n");
				sb.append("WHEN " + ACC_LEVEL_AG7 + " THEN ag7 \n");
				sb.append("WHEN " + ACC_LEVEL_AG8 + " THEN ag8 \n");
				sb.append("WHEN " + ACC_LEVEL_AG9 + " THEN ag9 \n");
				sb.append("WHEN " + ACC_LEVEL_AG10 + " THEN ag10 \n");
				sb.append("ELSE FALSE END)= ? \n");
				queryList.add(accId);
				sb.append("AND \n");
				sb.append("acc_level_type = (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ? )+1");
				queryList.add(accId);

				List<Map<String, Object>> accIdMapList = StmtUtil.queryToMap(READ_CONN, sb.toString(), queryList);
				LOG.debug("accIdMapList===" + accIdMapList);
				return accIdMapList;
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
			queryList.clear();
		}
		return null;
	}

	@Override
	public List<Long> findTotalRatioAccId(long accId) {
		StringBuilder sb = null;
		List<Object> selectList = null;
		List<Long> listLong = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listLong;
			} else {
				listLong = new ArrayList<Long>();
				selectList = new ArrayList<Object>();
				sb = new StringBuilder();
				sb.append("	select acc_id from ctt_manager.ctt_member_acc_level where	\n");
				sb.append("	CASE (select acc_level_type from ctt_manager.ctt_manager_acc_level where acc_id = ?)	\n");
				selectList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_SC + " THEN  sc = ? 	\n");
				selectList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_BC + " THEN  bc = ? 	\n");
				selectList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_CO + " THEN  co = ?     \n");
				selectList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_SA + " THEN  sa = ? 	\n");
				selectList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN  ag = ? 	\n");
				selectList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG1 + " THEN  ag1 = ? 	\n");
				selectList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG2 + " THEN  ag2 = ? 	\n");
				selectList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG3 + " THEN  ag3 = ? 	\n");
				selectList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG4 + " THEN  ag4 = ? 	\n");
				selectList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN  ag5 = ? 	\n");
				selectList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG6 + " THEN  ag6 = ? 	\n");
				selectList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG7 + " THEN  ag7 = ? 	\n");
				selectList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG8 + " THEN  ag8 = ?	\n");
				selectList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG9 + " THEN  ag9 = ?	\n");
				selectList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG10 + " THEN  ag10 = ? 	\n");
				selectList.add(accId);
				sb.append("	ELSE FALSE END	\n");

				List<Map<String, Object>> accIdMapList = StmtUtil.queryToMap(READ_CONN, sb.toString(), selectList);

				for (int i = 0; i < accIdMapList.size(); i++) {
					for (String key : accIdMapList.get(i).keySet()) {
						if (!accIdMapList.get(i).get(key).equals("")) {
							listLong.add(Long.parseLong(accIdMapList.get(i).get(key).toString()));
						}
					}
				}

				return listLong;
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectList != null) {
				selectList.clear();
				selectList = null;
			}
		}
		return listLong;
	}

	@Override
	public List<Map<String, Object>> checkAccRatio(long accId, int g1CurrentRatio, int g2CurrentRatio, int g3CurrentRatio, int g4CurrentRatio,
			int g5CurrentRatio) {
		LOG.debug("checkAccRatio");
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {

				sb.append("select G1.g1_over_acc, G2.g2_over_acc, G3.g3_over_acc, G4.g4_over_acc, G5.g5_over_acc from\n");
				sb.append("(   \n");
				sb.append("select 1 as id ,cast(group_concat(D1.acc_level_type,D1.acc_name) as char) as g1_over_acc\n");
				sb.append("from \n");
				sb.append("( \n");
				sb.append("select A.acc_id,A.acc_level_type, C.acc_name, B1.threshold as g1_threshold from \n");
				sb.append("(select acc_id,acc_level_type from `ctt_manager`.`ctt_manager_acc_level` where   \n");
				sb.append(" CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				sb.append("WHEN 0 THEN TRUE \n");
				sb.append("WHEN 1 THEN TRUE \n");
				sb.append("WHEN 2 THEN sc =? \n");
				sb.append("WHEN 3 THEN bc =?\n");
				sb.append("WHEN 4 THEN co =?  \n");
				sb.append("WHEN 5 THEN sa =? \n");
				sb.append("WHEN 6 THEN ag =? ELSE FALSE END \n");
				sb.append(")A \n");
				sb.append("left join \n");
				sb.append(
						"(select  acc_id, (next_upper_min_ratio+next_current_max_ratio )as threshold  from `ctt_manager`.`ctt_manager_ratio` where game_type = 1)B1 \n");
				sb.append("on B1.acc_id=A.acc_id \n");
				sb.append("left join \n");
				sb.append("(select acc_id, acc_name from `ctt_manager`.`ctt_manager_acc`)C \n");
				sb.append("on C.acc_id=A.acc_id \n");
				sb.append(")D1 \n");
				sb.append("where D1.g1_threshold > ? \n");
				sb.append(")G1 \n");
				sb.append("left  join \n");
				sb.append("( \n");
				sb.append("select 1 as id ,cast(group_concat(D1.acc_level_type,D1.acc_name) as char) as g2_over_acc\n");
				sb.append(" from \n");
				sb.append("( \n");
				sb.append("select A.acc_id,A.acc_level_type, C.acc_name,B2.threshold as g2_threshold from \n");
				sb.append("(select acc_id,acc_level_type from `ctt_manager`.`ctt_manager_acc_level` where   \n");
				sb.append(" CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?)  \n");
				sb.append("WHEN 0 THEN TRUE  \n");
				sb.append("WHEN 1 THEN TRUE  \n");
				sb.append("WHEN 2 THEN sc =? \n");
				sb.append("WHEN 3 THEN bc =? \n");
				sb.append("WHEN 4 THEN co =? \n");
				sb.append("WHEN 5 THEN sa =?  \n");
				sb.append("WHEN 6 THEN ag =? ELSE FALSE END \n");
				sb.append(")A \n");
				sb.append("left join\n");
				sb.append(
						"(select  acc_id, (next_upper_min_ratio+next_current_max_ratio )as threshold  from `ctt_manager`.`ctt_manager_ratio` where game_type = 2)B2 \n");
				sb.append("on B2.acc_id=A.acc_id \n");
				sb.append("left join \n");
				sb.append("(select acc_id, acc_name from `ctt_manager`.`ctt_manager_acc`)C \n");
				sb.append("on C.acc_id=A.acc_id \n");
				sb.append(")D1  \n");
				sb.append("where D1.g2_threshold > ? \n");
				sb.append(")G2\n");
				sb.append("on G2.id=G1.id \n");
				sb.append("left  join \n");
				sb.append("( \n");
				sb.append("select 1 as id ,cast(group_concat(D1.acc_level_type,D1.acc_name) as char) as g3_over_acc\n");
				sb.append("from \n");
				sb.append("( \n");
				sb.append("select A.acc_id,A.acc_level_type, C.acc_name,B3.threshold as g3_threshold from \n");
				sb.append("(select acc_id,acc_level_type from `ctt_manager`.`ctt_manager_acc_level` where \n");
				sb.append(" CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				sb.append("WHEN 0 THEN TRUE  \n");
				sb.append("WHEN 1 THEN TRUE  \n");
				sb.append("WHEN 2 THEN sc =? \n");
				sb.append("WHEN 3 THEN bc =? \n");
				sb.append("WHEN 4 THEN co =?\n");
				sb.append("WHEN 5 THEN sa =?\n");
				sb.append("WHEN 6 THEN ag =? ELSE FALSE END\n");
				sb.append(")A\n");
				sb.append("left join\n");
				sb.append(
						"(select  acc_id, (next_upper_min_ratio+next_current_max_ratio )as threshold  from `ctt_manager`.`ctt_manager_ratio` where game_type = 3)B3\n");
				sb.append("on B3.acc_id=A.acc_id\n");
				sb.append("left join\n");
				sb.append("(select acc_id, acc_name from `ctt_manager`.`ctt_manager_acc`)C\n");
				sb.append("on C.acc_id=A.acc_id\n");
				sb.append(")D1\n");
				sb.append("where D1.g3_threshold > ?\n");
				sb.append(")G3\n");
				sb.append("on G3.id=G1.id\n");
				sb.append("\n");
				sb.append("left  join\n");
				sb.append("(\n");
				sb.append("select 1 as id ,cast(group_concat(D1.acc_level_type,D1.acc_name) as char) as g4_over_acc\n");
				sb.append("from \n");
				sb.append("(\n");
				sb.append("select A.acc_id,A.acc_level_type, C.acc_name, B4.threshold as g4_threshold from\n");
				sb.append("(select acc_id,acc_level_type from `ctt_manager`.`ctt_manager_acc_level` where \n");
				sb.append(" CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				sb.append("WHEN 0 THEN TRUE \n");
				sb.append("WHEN 1 THEN TRUE \n");
				sb.append("WHEN 2 THEN sc =?\n");
				sb.append("WHEN 3 THEN bc =?\n");
				sb.append("WHEN 4 THEN co =?\n");
				sb.append("WHEN 5 THEN sa =?\n");
				sb.append("WHEN 6 THEN ag =? ELSE FALSE END\n");
				sb.append(")A\n");
				sb.append("left join\n");
				sb.append(
						"(select  acc_id, (next_upper_min_ratio+next_current_max_ratio )as threshold  from `ctt_manager`.`ctt_manager_ratio` where game_type = 4)B4\n");
				sb.append("on B4.acc_id=A.acc_id\n");
				sb.append("left join\n");
				sb.append("(select acc_id, acc_name from `ctt_manager`.`ctt_manager_acc`)C\n");
				sb.append("on C.acc_id=A.acc_id\n");
				sb.append(")D1\n");
				sb.append("where D1.g4_threshold > ?\n");
				sb.append(")G4\n");
				sb.append("on G4.id=G1.id\n");
				sb.append("\n");
				sb.append("left  join\n");
				sb.append("(\n");
				sb.append("select 1 as id ,cast(group_concat(D1.acc_level_type,D1.acc_name) as char) as g5_over_acc\n");
				sb.append("from \n");
				sb.append("(\n");
				sb.append("select A.acc_id,A.acc_level_type, C.acc_name, B5.threshold as g5_threshold from\n");
				sb.append("(select acc_id,acc_level_type from `ctt_manager`.`ctt_manager_acc_level` where\n");
				sb.append(" CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				sb.append("WHEN 0 THEN TRUE \n");
				sb.append("WHEN 1 THEN TRUE \n");
				sb.append("WHEN 2 THEN sc =?\n");
				sb.append("WHEN 3 THEN bc =?\n");
				sb.append("WHEN 4 THEN co =?\n");
				sb.append("WHEN 5 THEN sa =?\n");
				sb.append("WHEN 6 THEN ag =? ELSE FALSE END\n");
				sb.append(")A\n");
				sb.append("left join\n");
				sb.append(
						"(select  acc_id, (next_upper_min_ratio+next_current_max_ratio )as threshold  from `ctt_manager`.`ctt_manager_ratio` where game_type = 5)B5\n");
				sb.append("on B5.acc_id=A.acc_id\n");
				sb.append("left join\n");
				sb.append("(select acc_id, acc_name from `ctt_manager`.`ctt_manager_acc`)C\n");
				sb.append("on C.acc_id=A.acc_id\n");
				sb.append(")D1\n");
				sb.append("where D1.g5_threshold > ?\n");
				sb.append(")G5\n");
				sb.append("on G5.id=G1.id \n");

				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(g1CurrentRatio);

				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(g2CurrentRatio);

				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(g3CurrentRatio);

				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(g4CurrentRatio);

				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(g5CurrentRatio);

				result = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				LOG.debug(result);
				return result;
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return result;
	}

	@Override
	public boolean updateMemberPwd(long accId, String pwd) {
		List<Object> updateList = null;
		StringBuilder sb = null;
		boolean todo = false;

		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				updateList = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("update ctt_manager.ctt_member_acc set pwd_status = 1, pwd =?  , login_failed = 0 where acc_id = ?");
				updateList.add(pwd);
				updateList.add(accId);
				int cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateList);
				LOG.debug("cou" + cou);
				if (cou == 1) {
					todo = true;
				}
			}
		} catch (Exception e) {
			todo = false;

			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateList != null) {
				updateList.clear();
				updateList = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return todo;
	}

	@Override
	public boolean updateMemberWithdrawPwd(long accId) {
		Map<String, Object> updateMap = null;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				updateMap = new ConcurrentHashMap<String, Object>();

				updateMap.put("pwd_withdraw_status", 1);
				updateMap.put("pwd_withdraw", "");
				updateMap.put("pwd_withdraw_failed", 0);

				updateMap.put("acc_id", accId);
				int cou = StmtUtil.updateNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_member_acc", updateMap, "acc_id");
				LOG.debug("cou" + cou);
				if (cou == 1) {
					todo = true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateMap != null) {
				updateMap = new ConcurrentHashMap<String, Object>();
				updateMap = null;
			}
		}
		return todo;
	}

	@Override
	public boolean updateStatusEnable(long accId, int accLevelType) {
		List<Object> updateObj = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		boolean tool = false;
		String tableName = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return tool;
			} else {
				if (accLevelType == ACC_LEVEL_MEM) {
					tableName = "ctt_member_acc_level";
				} else if (accLevelType >= ACC_LEVEL_COM && accLevelType <= ACC_LEVEL_AG10) {
					tableName = "ctt_manager_acc_level";
				}
				sb.append("UPDATE ctt_manager." + tableName + "  SET acc_status = " + STATUS_ENABLED + " \n");
				sb.append("WHERE \n");
				sb.append("(SELECT A.acc_status FROM (SELECT acc_status FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = \n");
				sb.append("(SELECT(CASE(SELECT upper_acc_level_type FROM ctt_manager." + tableName + " WHERE acc_id = ?) \n");
				updateObj.add(accId);
				sb.append("WHEN 0 THEN TRUE \n");
				sb.append("WHEN 1 THEN TRUE \n");
				for (int i = 0; i < ACC_LEVEL_MAG.length; i++) {
					sb.append("WHEN " + ACC_LEVEL_MAG[i] + " THEN " + ACC_LEVEL_MAG_TABLE_NAME[i] + " \n");
				}
				sb.append("END )AS upper_acc_id FROM ctt_manager." + tableName + " WHERE acc_id = ?))A)= 1 \n");
				updateObj.add(accId);
				sb.append("AND \n");
				sb.append("(SELECT B.acc_status FROM (SELECT acc_status FROM ctt_manager." + tableName + " WHERE acc_id = ?)B) IN (" + STATUS_NOLOGIN
						+ "," + STATUS_DISABLED + ") \n");
				updateObj.add(accId);
				sb.append("AND \n");
				sb.append("acc_id = ? \n");
				updateObj.add(accId);

				int cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);
				LOG.debug("cou===" + cou);
				if (cou > 0) {
					tool = true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateObj != null) {
				updateObj = new ArrayList<Object>();
				updateObj = null;
			}
		}
		return tool;
	}

	@Override
	public boolean updateStatusDisable(long accId, int accLevelType) {
		List<Object> updateObj = new ArrayList<Object>();
		List<Object> updateObj2 = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		int cou = 0;
		boolean tool = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return tool;
			} else {
				if (accLevelType == ACC_LEVEL_MEM) {
					sb.append("UPDATE ctt_manager.ctt_member_acc_level SET acc_status = " + STATUS_DISABLED + " WHERE acc_status < " + STATUS_DISABLED
							+ " AND acc_id = ? ");
					updateObj.add(accId);

					cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);

					if (cou > 0) {
						tool = true;
					}
				} else {
					sb.append("UPDATE ctt_manager.ctt_manager_acc_level SET acc_status = " + STATUS_DISABLED + " \n");
					sb.append("WHERE acc_status < " + STATUS_DISABLED + " AND \n");
					sb.append("acc_id IN ( \n");
					sb.append("SELECT A.acc_id FROM (SELECT acc_id FROM ctt_manager.ctt_manager_acc_level WHERE \n");
					sb.append("(CASE(SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
					updateObj.add(accId);
					sb.append("WHEN 0 THEN TRUE \n");
					sb.append("WHEN 1 THEN TRUE \n");
					for (int i = 0; i < ACC_LEVEL_MAG.length; i++) {
						sb.append("WHEN " + ACC_LEVEL_MAG[i] + " THEN " + ACC_LEVEL_MAG_TABLE_NAME[i] + " \n");
					}
					sb.append("END ) = ?)A) \n");
					updateObj.add(accId);
					sb.append("OR acc_id = ? \n");
					updateObj.add(accId);

					cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);
					if (cou > 0) {
						sb2.append("UPDATE ctt_manager.ctt_member_acc_level SET acc_status = " + STATUS_DISABLED + " \n");
						sb2.append("WHERE acc_status < " + STATUS_DISABLED + " AND \n");
						sb2.append("acc_id IN ( \n");
						sb2.append("SELECT A.acc_id FROM (SELECT acc_id FROM ctt_manager.ctt_member_acc_level WHERE \n");
						sb2.append("(CASE(SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
						updateObj2.add(accId);
						sb2.append("WHEN 0 THEN TRUE \n");
						sb2.append("WHEN 1 THEN TRUE \n");
						for (int i = 0; i < ACC_LEVEL_MAG.length; i++) {
							sb2.append("WHEN " + ACC_LEVEL_MAG[i] + " THEN " + ACC_LEVEL_MAG_TABLE_NAME[i] + " \n");
						}
						sb2.append("END ) = ?)A) \n");
						updateObj2.add(accId);
						sb.append("OR acc_id = ? \n");
						updateObj.add(accId);

						cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb2.toString(), updateObj2);
						tool = true;
					}
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateObj != null) {
				updateObj = new ArrayList<Object>();
				;
				updateObj = null;
			}
			if (updateObj2 != null) {
				updateObj2 = new ArrayList<Object>();
				;
				updateObj2 = null;
			}
			if (sb != null) {
				sb = new StringBuilder();
				sb = null;
			}
			if (sb2 != null) {
				sb2 = new StringBuilder();
				sb2 = null;
			}
			cou = 0;
		}
		return tool;
	}

	@Override
	public boolean updateStatusCantLogin(long accId, int accLevelType) {
		List<Object> updateObj = new ArrayList<Object>();
		List<List<Object>> updateList = new ArrayList<List<Object>>();
		StringBuilder sb = new StringBuilder();
		boolean tool = false;
		String tableName = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				if (accLevelType == ACC_LEVEL_MEM) {
					tableName = "ctt_member_acc_level";
				} else if (accLevelType >= ACC_LEVEL_COM && accLevelType <= ACC_LEVEL_AG10) {
					tableName = "ctt_manager_acc_level";
				}
				sb.append("UPDATE ctt_manager." + tableName + " SET acc_status = " + STATUS_NOLOGIN + " \n");
				sb.append("WHERE acc_status < " + STATUS_DISABLED + " AND acc_id = ? ");
				updateObj.add(accId);
				updateList.add(updateObj);

				int cou[] = StmtUtil.updateBatchNoCommit(this.WRITE_CONN, sb.toString(), updateList);
				LOG.debug("cou" + cou);
				if (cou[0] > 0) {
					tool = true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateObj != null) {
				updateObj = new ArrayList<Object>();
				;
				updateObj = null;
			}
			if (updateList != null) {
				updateList = new ArrayList<List<Object>>();
				updateList = null;
			}
		}
		return tool;
	}

	@Override
	public boolean updateStatusDelete(long accId, int accLevelType) {
		List<Object> updateObj = new ArrayList<Object>();
		List<List<Object>> updateList = new ArrayList<List<Object>>();
		StringBuilder sb = new StringBuilder();
		boolean tool = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				if (accLevelType == ACC_LEVEL_MEM) {
					sb.append("UPDATE ctt_manager.ctt_member_acc_level SET acc_status = " + STATUS_DELETE + " \n");
					sb.append("WHERE acc_id = ? \n");
					updateObj.add(accId);
					sb.append("AND (SELECT balance FROM ctt_manager.ctt_member_acc where acc_id = ? ) = 0 \n");
					updateObj.add(accId);
					sb.append(
							"AND (SELECT last_login_datetime FROM ctt_manager.ctt_member_last_login WHERE acc_id = ? ) = (SELECT COLUMN_DEFAULT \n");
					updateObj.add(accId);
					sb.append(
							"FROM information_schema.columns where TABLE_SCHEMA = 'ctt_manager' AND TABLE_NAME = 'ctt_member_last_login' AND COLUMN_NAME = 'last_login_datetime') ");
				} else {
					sb.append("UPDATE ctt_manager.ctt_manager_acc_level SET acc_status = " + STATUS_DELETE + " \n");
					sb.append("WHERE acc_id = ? \n");
					updateObj.add(accId);
					sb.append("AND (SELECT balance FROM ctt_manager.ctt_manager_acc where acc_id = ? ) = 0 \n");
					updateObj.add(accId);
					sb.append(
							"AND (SELECT last_login_datetime FROM ctt_manager.ctt_manager_last_login WHERE acc_id = ? ) = (SELECT COLUMN_DEFAULT \n");
					updateObj.add(accId);
					sb.append(
							"FROM information_schema.columns where TABLE_SCHEMA = 'ctt_manager' AND TABLE_NAME = 'ctt_manager_last_login' AND COLUMN_NAME = 'last_login_datetime') \n");
					sb.append("AND (SELECT COUNT(acc_id) FROM\r\n");
					sb.append("(select acc_id from ctt_manager.ctt_manager_acc_level \r\n");
					sb.append("where CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id=? ) \n");
					updateObj.add(accId);
					sb.append("WHEN " + ACC_LEVEL_COM + " THEN acc_id = ? \n");
					updateObj.add(accId);
					for (int i = 0; i < ACC_LEVEL_MAG.length; i++) {
						if (i == ACC_LEVEL_MAG.length - 1) {
							sb.append("WHEN " + ACC_LEVEL_MAG[i] + " THEN (CASE WHEN " + ACC_LEVEL_MAG_TABLE_NAME[i]
									+ " = ? THEN TRUE ELSE FALSE END) ELSE FALSE END \n");
							updateObj.add(accId);
						} else {
							sb.append("WHEN " + ACC_LEVEL_MAG[i] + " THEN (CASE WHEN " + ACC_LEVEL_MAG_TABLE_NAME[i]
									+ " = ? THEN TRUE ELSE FALSE END) \n");
							updateObj.add(accId);
						}
					}
					sb.append("UNION ALL \n");
					sb.append("select acc_id from ctt_manager.ctt_member_acc_level \n");
					sb.append("where CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id= ? ) \n");
					updateObj.add(accId);
					for (int i = 0; i < ACC_LEVEL_MAG.length; i++) {
						if (i == ACC_LEVEL_MAG.length - 1) {
							sb.append("WHEN " + ACC_LEVEL_MAG[i] + " THEN (CASE WHEN " + ACC_LEVEL_MAG_TABLE_NAME[i]
									+ " = ? THEN TRUE ELSE FALSE END) ELSE FALSE END) t1) = 0 \n");
							updateObj.add(accId);
						} else {
							sb.append("WHEN " + ACC_LEVEL_MAG[i] + " THEN (CASE WHEN " + ACC_LEVEL_MAG_TABLE_NAME[i]
									+ " = ? THEN TRUE ELSE FALSE END) \n");
							updateObj.add(accId);
						}
					}
				}
				updateList.add(updateObj);
				int cou[] = StmtUtil.updateBatchNoCommit(this.WRITE_CONN, sb.toString(), updateList);
				if (cou[0] > 0) {
					tool = true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateObj != null) {
				updateObj = new ArrayList<Object>();
				;
				updateObj = null;
			}
			if (updateList != null) {
				updateList = new ArrayList<List<Object>>();
				updateList = null;
			}
		}
		return tool;
	}

	@Override
	public BigDecimal getLowerLevelAccBalance(long accId, int status) {
		StringBuilder sb = null;
		List<Object> params = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BigDecimal balanceTotal = null;
		List<Object> objs = null;

		try {
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			objs = new ArrayList<Object>();

			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return balanceTotal;

			} else {
				sb.append("select SUM(balance) as balanceTotal from(\n");
				sb.append("select balance from ctt_manager.ctt_manager_acc where acc_id IN(\n");
				sb.append("SELECT acc_id FROM ctt_manager.ctt_manager_acc_level \n");
				sb.append("WHERE (CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id=?) \n");
				objs.add(accId);
				sb.append("WHEN (2) THEN (CASE WHEN sc=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (3) THEN (CASE WHEN bc=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (4) THEN (CASE WHEN co=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (5) THEN (CASE WHEN sa=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (6) THEN (CASE WHEN ag=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (7) THEN (CASE WHEN ag1=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (8) THEN (CASE WHEN ag2=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (9) THEN (CASE WHEN ag3=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (10) THEN (CASE WHEN ag4=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (11) THEN (CASE WHEN ag5=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (12) THEN (CASE WHEN ag6=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (13) THEN (CASE WHEN ag7=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (14) THEN (CASE WHEN ag8=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (15) THEN (CASE WHEN ag9=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (16) THEN (CASE WHEN ag10=? THEN TRUE ELSE FALSE END)  \n");
				objs.add(accId);
				sb.append("WHEN (1) THEN TRUE WHEN (0) THEN TRUE ELSE FALSE END ) \n");
				sb.append("AND acc_level_type>(SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id=?) AND acc_status=? )   \n");
				objs.add(accId);
				objs.add(status);
				sb.append("UNION ALL \n");
				sb.append("select balance from ctt_manager.ctt_member_acc where acc_id IN(\n");
				sb.append("SELECT acc_id FROM ctt_manager.ctt_member_acc_level \n");
				sb.append("WHERE (CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id=?) \n");
				objs.add(accId);
				sb.append("WHEN (2) THEN (CASE WHEN sc=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (3) THEN (CASE WHEN bc=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (4) THEN (CASE WHEN co=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (5) THEN (CASE WHEN sa=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (6) THEN (CASE WHEN ag=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (7) THEN (CASE WHEN ag1=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (8) THEN (CASE WHEN ag2=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (9) THEN (CASE WHEN ag3=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (10) THEN (CASE WHEN ag4=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (11) THEN (CASE WHEN ag5=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (12) THEN (CASE WHEN ag6=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (13) THEN (CASE WHEN ag7=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (14) THEN (CASE WHEN ag8=? THEN TRUE ELSE FALSE END) \n");
				objs.add(accId);
				sb.append("WHEN (15) THEN (CASE WHEN ag9=? THEN TRUE ELSE FALSE END)  \n");
				objs.add(accId);
				sb.append("WHEN (16) THEN (CASE WHEN ag10=? THEN TRUE ELSE FALSE END)  \n");
				objs.add(accId);
				sb.append("WHEN (1) THEN TRUE WHEN (0) THEN TRUE ELSE FALSE END ) \n");
				sb.append("AND acc_status=? ) \n");
				objs.add(status);
				sb.append(")  A\n");
				ps = this.READ_CONN.prepareStatement(sb.toString());
				for (int i = 0; i < objs.size(); i++) {
					ps.setObject((i + 1), objs.get(i));
				}
				rs = ps.executeQuery();
				LOG.debug(ps.toString());

				if (rs.next()) {
					if (rs.getObject("balanceTotal") != null && !"".equals(rs.getObject("balanceTotal"))) {
						balanceTotal = new BigDecimal("" + rs.getObject("balanceTotal"));
						LOG.debug(balanceTotal);
					} else {
						balanceTotal = BigDecimal.ZERO;
					}
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
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
					LOG.debug("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
				} finally {
					ps = null;
				}
			}
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					LOG.debug("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
				} finally {
					rs = null;
				}
			}
			if (objs != null) {
				objs.clear();
				objs = null;
			}
		}
		return balanceTotal;

	}

	@Override
	public Map<String, Object> getAllAccData(long accId) {
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		Map<String, Object> result = new ConcurrentHashMap<String, Object>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return result;

			} else {
				sb.append("SELECT A.acc_name , A.acc_id,A.acc_level_type ,A.acc_status from( \n");
				sb.append(
						"SELECT a2.acc_name , a1.acc_id,a1.acc_level_type ,a1.acc_status FROM  ((SELECT acc_id,acc_level_type,acc_status FROM ctt_manager.ctt_manager_acc_level \n");
				sb.append("WHERE (CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id=?) \n");
				params.add(accId);
				sb.append("WHEN (" + ACC_LEVEL_SC + ") THEN (CASE WHEN sc=? THEN TRUE ELSE FALSE END) \n");
				params.add(accId);
				sb.append("WHEN (" + ACC_LEVEL_BC + ") THEN (CASE WHEN bc=? THEN TRUE ELSE FALSE END) \n");
				params.add(accId);
				sb.append("WHEN (" + ACC_LEVEL_CO + ") THEN (CASE WHEN co=? THEN TRUE ELSE FALSE END) \n");
				params.add(accId);
				sb.append("WHEN (" + ACC_LEVEL_SA + ") THEN (CASE WHEN sa=? THEN TRUE ELSE FALSE END) \n");
				params.add(accId);
				sb.append("WHEN (" + ACC_LEVEL_AG + ") THEN (CASE WHEN ag=? THEN TRUE ELSE FALSE END) \n");
				params.add(accId);
				sb.append("WHEN (" + ACC_LEVEL_AG1 + ") THEN (CASE WHEN ag1=? THEN TRUE ELSE FALSE END) \n");
				params.add(accId);
				sb.append("WHEN (" + ACC_LEVEL_AG2 + ") THEN (CASE WHEN ag2=? THEN TRUE ELSE FALSE END) \n");
				params.add(accId);
				sb.append("WHEN (" + ACC_LEVEL_AG3 + ") THEN (CASE WHEN ag3=? THEN TRUE ELSE FALSE END) \n");
				params.add(accId);
				sb.append("WHEN (" + ACC_LEVEL_AG4 + ") THEN (CASE WHEN ag4=? THEN TRUE ELSE FALSE END) \n");
				params.add(accId);
				sb.append("WHEN (" + ACC_LEVEL_AG5 + ") THEN (CASE WHEN ag5=? THEN TRUE ELSE FALSE END) \n");
				params.add(accId);
				sb.append("WHEN (" + ACC_LEVEL_AG6 + ") THEN (CASE WHEN ag6=? THEN TRUE ELSE FALSE END) \n");
				params.add(accId);
				sb.append("WHEN (" + ACC_LEVEL_AG7 + ") THEN (CASE WHEN ag7=? THEN TRUE ELSE FALSE END) \n");
				params.add(accId);
				sb.append("WHEN (" + ACC_LEVEL_AG8 + ") THEN (CASE WHEN ag8=? THEN TRUE ELSE FALSE END) \n");
				params.add(accId);
				sb.append("WHEN (" + ACC_LEVEL_AG9 + ") THEN (CASE WHEN ag9=? THEN TRUE ELSE FALSE END) \n");
				params.add(accId);
				sb.append("WHEN (" + ACC_LEVEL_AG10 + ") THEN (CASE WHEN ag10=? THEN TRUE ELSE FALSE END) \n");
				params.add(accId);
				sb.append("WHEN (" + ACC_LEVEL_COM + ") THEN TRUE WHEN (" + ACC_LEVEL_ADMIN + ") THEN TRUE ELSE FALSE END ) \n");
				sb.append("AND acc_level_type>(SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id=?)) a1 \n");
				params.add(accId);
				sb.append("inner join \n");
				sb.append("(select acc_id , acc_name from ctt_manager.ctt_manager_acc) a2 \n");
				sb.append("on a1.acc_id = a2.acc_id \n");
				sb.append(") ) A \n");

				List<Map<String, Object>> listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				LOG.debug(listMap.toString());

				for (int i = 1; i <= 16; i++) {
					Map<String, Object> mapStatus = new ConcurrentHashMap<String, Object>();
					mapStatus.put("" + STATUS_ENABLED, new ArrayList<Map<String, Object>>());
					mapStatus.put("" + STATUS_DISABLED, new ArrayList<Map<String, Object>>());
					mapStatus.put("" + STATUS_NOLOGIN, new ArrayList<Map<String, Object>>());
					mapStatus.put("" + STATUS_DELETE, new ArrayList<Map<String, Object>>());
					result.put("" + i, mapStatus);

				}
				for (int i = 0; i < listMap.size(); i++) {
					Map<String, Object> mapManager = new ConcurrentHashMap<String, Object>();
					long accid = Long.parseLong("" + listMap.get(i).get("accId"));
					String accName = "" + listMap.get(i).get("accName");
					int accStatus = Integer.parseInt("" + listMap.get(i).get("accStatus"));
					int accLevelType = Integer.parseInt("" + listMap.get(i).get("accLevelType"));

					mapManager.put("accId", accid);
					mapManager.put("accName", accName);
					mapManager.put("accStatus", accStatus);
					mapManager.put("accLevelType", accLevelType);

					Map<String, Object> map1 = (Map<String, Object>) result.get("" + accLevelType);
					List<Map<String, Object>> listMap1 = (List<Map<String, Object>>) map1.get("" + accStatus);
					listMap1.add(mapManager);
				}
				LOG.debug(result.toString());

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> checkMemRatio(long accId, int g1CurrentRatio, int g2CurrentRatio, int g3CurrentRatio, int g4CurrentRatio,
			int g5CurrentRatio) {

		LOG.debug("checkMemRatio");
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {
				sb.append("		\n");
				sb.append("	select G1.g1_over_acc, G2.g2_over_acc, G3.g3_over_acc, G4.g4_over_acc, G5.g5_over_acc from	\n");
				sb.append("	(   	\n");
				sb.append("	select 1 as id ,cast(group_concat(D1.acc_level_type,D1.acc_name) as char) as g1_over_acc	\n");
				sb.append("	from 	\n");
				sb.append("	( 	\n");
				sb.append("	select 1 as id , " + ACC_LEVEL_MEM + " as acc_level_type , C.acc_name ,B1.threshold as g1_threshold  from 	\n");
				sb.append("	(select acc_id from `ctt_manager`.`ctt_member_acc_level` where   	\n");
				sb.append("	CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_ADMIN + " THEN TRUE 	\n");
				sb.append("	WHEN " + ACC_LEVEL_COM + " THEN TRUE 	\n");
				sb.append("	WHEN " + ACC_LEVEL_SC + " THEN sc =? 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_BC + " THEN bc =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_CO + " THEN co =? 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_SA + " THEN sa =? 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN ag =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG1 + " THEN ag1 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG2 + " THEN ag2 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG3 + " THEN ag3 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG4 + " THEN ag4 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN ag5 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG6 + " THEN ag6 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG7 + " THEN ag7 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG8 + " THEN ag8 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG9 + " THEN ag9 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG10 + " THEN ag10 =? ELSE FALSE END 	\n");
				params.add(accId);
				sb.append("	)A                 	\n");
				sb.append("	left join 	\n");
				sb.append("		\n");
				sb.append("	(select acc_id , next_upper_min_ratio as threshold from `ctt_manager`.`ctt_member_ratio` where game_type = " + LOTTERY
						+ ")B1 	\n");
				sb.append("	on B1.acc_id=A.acc_id   	\n");
				sb.append("	left join 	\n");
				sb.append("	(select acc_id, acc_name from `ctt_manager`.`ctt_member_acc`)C 	\n");
				sb.append("	on C.acc_id=A.acc_id 	\n");
				sb.append("	)D1 	\n");
				sb.append("	where D1.g1_threshold > ? 	\n");
				params.add(g1CurrentRatio);
				sb.append("	)G1 	\n");
				sb.append("	left  join 	\n");
				sb.append("	( 	\n");
				sb.append("	select 1 as id ,cast(group_concat(D1.acc_level_type,D1.acc_name) as char) as g2_over_acc	\n");
				sb.append("	from 	\n");
				sb.append("	( 	\n");
				sb.append("	select A.acc_id, " + ACC_LEVEL_MEM + " as acc_level_type, C.acc_name,B2.threshold as g2_threshold from 	\n");
				sb.append("	(select acc_id from `ctt_manager`.`ctt_member_acc_level` where   	\n");
				sb.append("	CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?)  	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_ADMIN + " THEN TRUE 	\n");
				sb.append("	WHEN " + ACC_LEVEL_COM + " THEN TRUE 	\n");
				sb.append("	WHEN " + ACC_LEVEL_SC + " THEN sc =? 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_BC + " THEN bc =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_CO + " THEN co =? 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_SA + " THEN sa =? 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN ag =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG1 + " THEN ag1 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG2 + " THEN ag2 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG3 + " THEN ag3 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG4 + " THEN ag4 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN ag5 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG6 + " THEN ag6 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG7 + " THEN ag7 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG8 + " THEN ag8 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG9 + " THEN ag9 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG10 + " THEN ag10 =? ELSE FALSE END 	\n");
				params.add(accId);
				sb.append("	)A 	\n");
				sb.append("	left join	\n");
				sb.append("		\n");
				sb.append("	(select acc_id , next_upper_min_ratio as threshold from `ctt_manager`.`ctt_member_ratio` where game_type = " + LIVEVIDEO
						+ ")B2 	\n");
				sb.append("	on B2.acc_id=A.acc_id 	\n");
				sb.append("	left join 	\n");
				sb.append("	(select acc_id, acc_name from `ctt_manager`.`ctt_member_acc`)C 	\n");
				sb.append("	on C.acc_id=A.acc_id 	\n");
				sb.append("	)D1  	\n");
				sb.append("	where D1.g2_threshold > ? 	\n");
				params.add(g2CurrentRatio);
				sb.append("	)G2	\n");
				sb.append("	on G2.id=G1.id 	\n");
				sb.append("	left  join 	\n");
				sb.append("	( 	\n");
				sb.append("	select 1 as id ,cast(group_concat(D1.acc_level_type,D1.acc_name) as char) as g3_over_acc	\n");
				sb.append("	from 	\n");
				sb.append("	( 	\n");
				sb.append("	select A.acc_id, " + ACC_LEVEL_MEM + " as acc_level_type, C.acc_name,B3.threshold as g3_threshold from 	\n");
				sb.append("	(select acc_id from `ctt_manager`.`ctt_member_acc_level` where 	\n");
				sb.append("	CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_ADMIN + " THEN TRUE 	\n");
				sb.append("	WHEN " + ACC_LEVEL_COM + " THEN TRUE 	\n");
				sb.append("	WHEN " + ACC_LEVEL_SC + " THEN sc =? 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_BC + " THEN bc =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_CO + " THEN co =? 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_SA + " THEN sa =? 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN ag =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG1 + " THEN ag1 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG2 + " THEN ag2 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG3 + " THEN ag3 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG4 + " THEN ag4 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN ag5 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG6 + " THEN ag6 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG7 + " THEN ag7 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG8 + " THEN ag8 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG9 + " THEN ag9 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG10 + " THEN ag10 =? ELSE FALSE END 	\n");
				params.add(accId);
				sb.append("	)A	\n");
				sb.append("	left join	\n");
				sb.append("		\n");
				sb.append("	(select acc_id , next_upper_min_ratio as threshold from `ctt_manager`.`ctt_member_ratio` where game_type = " + MOVEMENT
						+ ")B3	\n");
				sb.append("	on B3.acc_id=A.acc_id	\n");
				sb.append("	left join	\n");
				sb.append("	(select acc_id, acc_name from `ctt_manager`.`ctt_member_acc`)C	\n");
				sb.append("	on C.acc_id=A.acc_id	\n");
				sb.append("	)D1	\n");
				sb.append("	where D1.g3_threshold > ?	\n");
				params.add(g3CurrentRatio);
				sb.append("	)G3	\n");
				sb.append("	on G3.id=G1.id	\n");
				sb.append("		\n");
				sb.append("	left  join	\n");
				sb.append("	(	\n");
				sb.append("	select 1 as id ,cast(group_concat(D1.acc_level_type,D1.acc_name) as char) as g4_over_acc	\n");
				sb.append("	from 	\n");
				sb.append("	(	\n");
				sb.append("	select A.acc_id, " + ACC_LEVEL_MEM + " as acc_level_type, C.acc_name, B4.threshold as g4_threshold from	\n");
				sb.append("	(select acc_id from `ctt_manager`.`ctt_member_acc_level` where 	\n");
				sb.append("	CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_ADMIN + " THEN TRUE 	\n");
				sb.append("	WHEN " + ACC_LEVEL_COM + " THEN TRUE 	\n");
				sb.append("	WHEN " + ACC_LEVEL_SC + " THEN sc =? 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_BC + " THEN bc =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_CO + " THEN co =? 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_SA + " THEN sa =? 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN ag =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG1 + " THEN ag1 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG2 + " THEN ag2 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG3 + " THEN ag3 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG4 + " THEN ag4 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN ag5 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG6 + " THEN ag6 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG7 + " THEN ag7 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG8 + " THEN ag8 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG9 + " THEN ag9 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG10 + " THEN ag10 =? ELSE FALSE END 	\n");
				params.add(accId);
				sb.append("	)A	\n");
				sb.append("	left join	\n");
				sb.append("		\n");
				sb.append("	(select acc_id , next_upper_min_ratio as threshold from `ctt_manager`.`ctt_member_ratio` where game_type = " + VIDEOGAME
						+ ")B4	\n");
				sb.append("	on B4.acc_id=A.acc_id	\n");
				sb.append("	left join	\n");
				sb.append("	(select acc_id, acc_name from `ctt_manager`.`ctt_member_acc`)C	\n");
				sb.append("	on C.acc_id=A.acc_id	\n");
				sb.append("	)D1	\n");
				sb.append("	where D1.g4_threshold > ?	\n");
				params.add(g4CurrentRatio);
				sb.append("	)G4	\n");
				sb.append("	on G4.id=G1.id	\n");
				sb.append("		\n");
				sb.append("	left  join	\n");
				sb.append("	(	\n");
				sb.append("	select 1 as id ,cast(group_concat(D1.acc_level_type,D1.acc_name) as char) as g5_over_acc	\n");
				sb.append("	from 	\n");
				sb.append("	(	\n");
				sb.append("	select A.acc_id, " + ACC_LEVEL_MEM + " as acc_level_type, C.acc_name, B5.threshold as g5_threshold from	\n");
				sb.append("	(select acc_id from `ctt_manager`.`ctt_member_acc_level` where	\n");
				sb.append("	CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_ADMIN + " THEN TRUE 	\n");
				sb.append("	WHEN " + ACC_LEVEL_COM + " THEN TRUE 	\n");
				sb.append("	WHEN " + ACC_LEVEL_SC + " THEN sc =? 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_BC + " THEN bc =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_CO + " THEN co =? 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_SA + " THEN sa =? 	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN ag =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG1 + " THEN ag1 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG2 + " THEN ag2 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG3 + " THEN ag3 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG4 + " THEN ag4 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN ag5 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG6 + " THEN ag6 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG7 + " THEN ag7 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG8 + " THEN ag8 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG9 + " THEN ag9 =?	\n");
				params.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG10 + " THEN ag10 =? ELSE FALSE END 	\n");
				params.add(accId);
				sb.append("	)A	\n");
				sb.append("	left join	\n");
				sb.append("		\n");
				sb.append("	(select acc_id , next_upper_min_ratio as threshold from `ctt_manager`.`ctt_member_ratio` where game_type = " + GAME
						+ ")B5	\n");
				sb.append("	on B5.acc_id=A.acc_id	\n");
				sb.append("	left join	\n");
				sb.append("	(select acc_id, acc_name from `ctt_manager`.`ctt_member_acc`)C	\n");
				sb.append("	on C.acc_id=A.acc_id	\n");
				sb.append("	)D1	\n");
				sb.append("	where D1.g5_threshold > ?	\n");
				params.add(g5CurrentRatio);
				sb.append("	)G5	\n");
				sb.append("	on G5.id=G1.id 	\n");

				result = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				LOG.debug(result);
				return result;
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> getLowerLevelAccTotal(long accId) {
		LOG.debug("getLowerLevelAccTotal");
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		Map<String, Object> result = new ConcurrentHashMap<String, Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {

				sb.append(
						"select case when A.onlineMagCount <> '' then A.onlineMagCount else 0 end as onlineMagCount ,case when B.onlineMemCount <> '' then B.onlineMemCount else 0 end  as onlineMemCount from ( \n");
				sb.append(
						"(select '1' as id, SUM(CASE WHEN online_datetime > CURRENT_TIMESTAMP and  forced_logout_datetime > CURRENT_TIMESTAMP THEN 1 ELSE 0 END) AS 'onlineMagCount'  from \n");
				sb.append(
						"(select acc_id from ctt_manager.ctt_manager_acc_level where CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?)\n");
				params.add(accId);
				sb.append("WHEN 0 THEN TRUE \n");
				sb.append("WHEN 1 THEN TRUE \n");
				sb.append("WHEN 2 THEN sc =?\n");
				params.add(accId);
				sb.append("WHEN 3 THEN bc =?\n");
				params.add(accId);
				sb.append("WHEN 4 THEN co =?\n");
				params.add(accId);
				sb.append("WHEN 5 THEN sa =?\n");
				params.add(accId);
				sb.append("WHEN 6 THEN ag =?\n");
				params.add(accId);
				sb.append("WHEN 7 THEN ag1 =?\n");
				params.add(accId);
				sb.append("WHEN 8 THEN ag2 =?\n");
				params.add(accId);
				sb.append("WHEN 9 THEN ag3 =?\n");
				params.add(accId);
				sb.append("WHEN 10 THEN ag4 =?\n");
				params.add(accId);
				sb.append("WHEN 11 THEN ag5 =?\n");
				params.add(accId);
				sb.append("WHEN 12 THEN ag6 =?\n");
				params.add(accId);
				sb.append("WHEN 13 THEN ag7 =?\n");
				params.add(accId);
				sb.append("WHEN 14 THEN ag8 =?\n");
				params.add(accId);
				sb.append("WHEN 15 THEN ag9 =?\n");
				params.add(accId);
				sb.append("WHEN 16 THEN ag10 =? ELSE FALSE END) a1\n");
				params.add(accId);
				sb.append("inner join \n");
				sb.append("(select acc_id,online_datetime,forced_logout_datetime from ctt_manager.ctt_manager_token )a2\n");
				sb.append("on a1.acc_id = a2.acc_id\n");
				sb.append(") A\n");
				sb.append("inner join \n");
				sb.append(
						"(select '1' as id,  SUM(CASE WHEN online_datetime > CURRENT_TIMESTAMP and  forced_logout_datetime > CURRENT_TIMESTAMP THEN 1 ELSE 0 END) AS 'onlineMemCount'  from \n");
				sb.append(
						"(select acc_id from ctt_manager.ctt_member_acc_level where CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				params.add(accId);
				sb.append("WHEN 0 THEN TRUE \n");
				sb.append("WHEN 1 THEN TRUE \n");
				sb.append("WHEN 2 THEN sc =?\n");
				params.add(accId);
				sb.append("WHEN 3 THEN bc =?\n");
				params.add(accId);
				sb.append("WHEN 4 THEN co =?\n");
				params.add(accId);
				sb.append("WHEN 5 THEN sa =?\n");
				params.add(accId);
				sb.append("WHEN 6 THEN ag =?\n");
				params.add(accId);
				sb.append("WHEN 7 THEN ag1 =?\n");
				params.add(accId);
				sb.append("WHEN 8 THEN ag2 =?\n");
				params.add(accId);
				sb.append("WHEN 9 THEN ag3 =?\n");
				params.add(accId);
				sb.append("WHEN 10 THEN ag4 =?\n");
				params.add(accId);
				sb.append("WHEN 11 THEN ag5 =?\n");
				params.add(accId);
				sb.append("WHEN 12 THEN ag6 =?\n");
				params.add(accId);
				sb.append("WHEN 13 THEN ag7 =?\n");
				params.add(accId);
				sb.append("WHEN 14 THEN ag8 =?\n");
				params.add(accId);
				sb.append("WHEN 15 THEN ag9 =?\n");
				params.add(accId);
				sb.append("WHEN 16 THEN ag10 =? ELSE FALSE END) b1\n");
				params.add(accId);
				sb.append("inner join \n");
				sb.append("(select acc_id,online_datetime,forced_logout_datetime from ctt_manager.ctt_member_token )b2\n");
				sb.append("on b1.acc_id = b2.acc_id\n");
				sb.append(") B\n");
				sb.append("on A.id = B.id\n");
				sb.append(")\n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);

				for (int i = 0; i < listMap.size(); i++) {

					for (Object key : listMap.get(i).keySet()) {
						result.put(key.toString(), listMap.get(i).get(key.toString()));
					}
				}

				LOG.debug(result.toString());
				return result;
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return result;
	}

	@Override
	public boolean PWDChange(long USERID, String oldPassWord, String newPassWord) {
		StringBuilder sb = null;
		List<Object> params = null;
		int cou = 0;
		try {
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				sb.append(
						"UPDATE ctt_manager.ctt_manager_acc SET pwd=(SELECT ? AS PASS FROM (SELECT acc_id,pwd FROM ctt_manager.ctt_manager_acc WHERE acc_id =?) A WHERE A.pwd=?) WHERE acc_id =? \n");
				params.add(newPassWord);
				params.add(USERID);
				params.add(oldPassWord);
				params.add(USERID);

				cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);
				if (cou == 1) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.debug("showOpsLog_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				rollBackDB();
			} catch (SQLException e1) {
				LOG.debug("rollBackDB_Exception===" + e.getMessage());
				ShowLog.err(LOG, e);
			}
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
		return false;
	}

	@Override
	public Map<String, Object> getAccDetails(long accId) {
		Map<String, Object> result = null;
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		List<Object> params = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();
				listMap = new ArrayList<Map<String, Object>>();
				result = new ConcurrentHashMap<String, Object>();

				sb.append("SELECT \n");
				sb.append("A.acc_id,\n");
				sb.append("B.acc_name,\n");
				sb.append("B.nickname,\n");
				sb.append("B.balance,\n");
				sb.append("C.upper_acc_level_type,\n");
				sb.append("C.acc_status,\n");
				sb.append("D.recharge_cash_total,\n");
				sb.append("D.withdraw_cash_total,\n");
				sb.append("D.reward_cash_total,\n");
				sb.append("D.currency,\n");
				sb.append("D.first_withdraw_time,\n");
				sb.append("D.first_recharge_time,\n");
				sb.append("D.last_withdraw_time,\n");
				sb.append("D.last_recharge_time,\n");
				sb.append("D.withdraw_total , \n");
				sb.append("D.recharge_total , \n");
				sb.append("D.game_hall ,\n");
				sb.append("D.now_game , \n");
				sb.append("D.member_real_name , \n");
				sb.append("D.qq_acc,\n");
				sb.append("D.wechat_acc , \n");
				sb.append("D.phone_number , \n");
				sb.append("E.last_login_time,\n");
				sb.append("E.last_ip,\n");
				sb.append("F.create_time,\n");
				sb.append("F.create_ip,\n");
				sb.append("G.onlines AS onlines ,\n");
				sb.append("H.sc_acc_name,\n");
				sb.append("H.bc_acc_name,\n");
				sb.append("H.co_acc_name,\n");
				sb.append("H.sa_acc_name,\n");
				sb.append("H.ag_acc_name\n");
				sb.append(",CONCAT((CASE WHEN h1.handicap_name <> '' THEN h1.handicap_name ELSE '' END)\n");
				sb.append(",(CASE WHEN h2.handicap_name <> '' THEN h2.handicap_name ELSE '' END)\n");
				sb.append(",(CASE WHEN h3.handicap_name <> '' THEN h3.handicap_name ELSE '' END)\n");
				sb.append(",(CASE WHEN h4.handicap_name <> '' THEN h4.handicap_name ELSE '' END)\n");
				sb.append(",(CASE WHEN h5.handicap_name <> '' THEN h5.handicap_name ELSE '' END)) as handicap \n");
				sb.append("FROM\n");
				sb.append("(\n");
				sb.append(
						"select acc_id from ctt_manager.ctt_member_acc where acc_id = ? AND acc_id IN (SELECT acc_id FROM ctt_manager.ctt_member_acc_level where acc_status < 4 and acc_id = ?)\n");
				sb.append(") A \n");
				sb.append("INNER JOIN  \n");
				sb.append("(\n");
				sb.append("select acc_id, acc_name,nickname,balance,handicap from ctt_manager.ctt_member_acc\n");
				sb.append(") B  \n");
				sb.append("ON A.acc_id = B.acc_id\n");
				sb.append("INNER JOIN\n");
				sb.append("(\n");
				sb.append(
						"select acc_id ,case upper_acc_level_type when 2 then '總監直屬會員' when 3 then '大股東直屬會員' when 4 then '股東直屬會員' when 5 then '總代理直屬會員' when 6 then '一般會員' else '公司直屬會員' END as upper_acc_level_type ,CASE acc_status WHEN  1 THEN '啟用' ELSE '停用' END as acc_status \n");
				sb.append("from ctt_manager.ctt_member_acc_level \n");
				sb.append(")C\n");
				sb.append("ON A.acc_id = C.acc_id\n");
				sb.append("INNER JOIN\n");
				sb.append("(\n");
				sb.append("SELECT acc_id , \n");
				sb.append("recharge_cash_total , \n");
				sb.append("withdraw_cash_total , \n");
				sb.append("reward_cash_total , \n");
				sb.append("CASE currency WHEN 1 THEN  '美金' WHEN 2 THEN '日幣' ELSE '人民幣' END as currency , \n");
				sb.append("DATE_FORMAT(first_withdraw_datetime, '%Y-%m-%d %T') as first_withdraw_time  , \n");
				sb.append("DATE_FORMAT(first_recharge_datetime, '%Y-%m-%d %T') as first_recharge_time  , \n");
				sb.append("DATE_FORMAT(last_withdraw_datetime, '%Y-%m-%d %T') as last_withdraw_time   ,\n");
				sb.append("DATE_FORMAT(last_recharge_datetime, '%Y-%m-%d %T') as last_recharge_time  ,\n");
				sb.append("withdraw_total , \n");
				sb.append("recharge_total ,\n");
				sb.append("game_hall,\n");
				sb.append("now_game , \n");
				sb.append("member_real_name , \n");
				sb.append("qq_acc,wechat_acc , \n");
				sb.append("phone_number  \n");
				sb.append("FROM ctt_manager.ctt_member_basic_setting \n");
				sb.append(") D\n");
				sb.append("ON A.acc_id = D.acc_id\n");
				sb.append("INNER JOIN \n");
				sb.append("(\n");
				sb.append(
						"SELECT acc_id,DATE_FORMAT(last_login_datetime, '%Y-%m-%d %T') as last_login_time,CAST(CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) AS char) last_ip FROM ctt_manager.ctt_member_last_login\n");
				sb.append(") E \n");
				sb.append("ON A.acc_id=E.acc_id \n");
				sb.append("INNER JOIN \n");
				sb.append("(\n");
				sb.append(
						"SELECT acc_id,DATE_FORMAT(create_datetime, '%Y-%m-%d %T') as create_time,CAST(CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) AS char) create_ip FROM ctt_manager.ctt_member_create\n");
				sb.append(") F \n");
				sb.append("ON A.acc_id=F.acc_id \n");
				sb.append("INNER JOIN \n");
				sb.append("(\n");
				sb.append(
						"SELECT acc_id , case when online_datetime > CURRENT_TIMESTAMP and forced_logout_datetime > CURRENT_TIMESTAMP then 'Y' else 'N' end as onlines  FROM ctt_manager.ctt_member_token\n");
				sb.append(") G \n");
				sb.append("ON A.acc_id=G.acc_id \n");
				sb.append("INNER JOIN \n");
				sb.append("(\n");
				sb.append(
						"SELECT acc_id,FORMAT(SUM(sc_id),0) AS sc_id,GROUP_CONCAT(sc_nickname SEPARATOR'') AS sc_nickname,GROUP_CONCAT(sc_acc_name SEPARATOR'') AS sc_acc_name \n");
				sb.append(
						",FORMAT(SUM(bc_id),0) AS bc_id,GROUP_CONCAT(bc_nickname SEPARATOR'') AS bc_nickname,GROUP_CONCAT(bc_acc_name SEPARATOR'') AS bc_acc_name \n");
				sb.append(
						",FORMAT(SUM(co_id),0) AS co_id,GROUP_CONCAT(co_nickname SEPARATOR'') AS co_nickname,GROUP_CONCAT(co_acc_name SEPARATOR'') AS co_acc_name \n");
				sb.append(
						",FORMAT(SUM(sa_id),0) AS sa_id,GROUP_CONCAT(sa_nickname SEPARATOR'') AS sa_nickname,GROUP_CONCAT(sa_acc_name SEPARATOR'') AS sa_acc_name \n");
				sb.append(
						",FORMAT(SUM(ag_id),0) AS ag_id,GROUP_CONCAT(ag_nickname SEPARATOR'') AS ag_nickname,GROUP_CONCAT(ag_acc_name SEPARATOR'') AS ag_acc_name \n");
				sb.append("FROM \n");
				sb.append("( \n");
				sb.append("SELECT \n");
				sb.append("acc_id \n");
				sb.append(",CASE WHEN sc=acc_id1 THEN acc_id1 ELSE '' END AS sc_id \n");
				sb.append(",CASE WHEN sc=acc_id1 THEN nickname ELSE '' END AS sc_nickname \n");
				sb.append(",CASE WHEN sc=acc_id1 THEN acc_name ELSE '' END AS sc_acc_name \n");
				sb.append(",CASE WHEN bc=acc_id1 THEN acc_id1 ELSE '' END AS bc_id \n");
				sb.append(",CASE WHEN bc=acc_id1 THEN nickname ELSE '' END AS bc_nickname \n");
				sb.append(",CASE WHEN bc=acc_id1 THEN acc_name ELSE '' END AS bc_acc_name \n");
				sb.append(",CASE WHEN co=acc_id1 THEN acc_id1 ELSE '' END AS co_id \n");
				sb.append(",CASE WHEN co=acc_id1 THEN nickname ELSE '' END AS co_nickname \n");
				sb.append(",CASE WHEN co=acc_id1 THEN acc_name ELSE '' END AS co_acc_name \n");
				sb.append(",CASE WHEN sa=acc_id1 THEN acc_id1 ELSE '' END AS sa_id \n");
				sb.append(",CASE WHEN sa=acc_id1 THEN nickname ELSE '' END AS sa_nickname \n");
				sb.append(",CASE WHEN sa=acc_id1 THEN acc_name ELSE '' END AS sa_acc_name \n");
				sb.append(",CASE WHEN ag=acc_id1 THEN acc_id1 ELSE '' END AS ag_id \n");
				sb.append(",CASE WHEN ag=acc_id1 THEN nickname ELSE '' END AS ag_nickname \n");
				sb.append(",CASE WHEN ag=acc_id1 THEN acc_name ELSE '' END AS ag_acc_name \n");
				sb.append("FROM (SELECT acc_id,7 AS acc_level_type,sc,bc,co,sa,ag,acc_status FROM ctt_manager.ctt_member_acc_level )T2 \n");
				sb.append("LEFT OUTER JOIN (SELECT acc_id as acc_id1 ,nickname,acc_name FROM ctt_manager.ctt_manager_acc) T3 \n");
				sb.append("ON T2.sc=T3.acc_id1 \n");
				sb.append("OR T2.bc=T3.acc_id1 \n");
				sb.append("OR T2.co=T3.acc_id1 \n");
				sb.append("OR T2.sa=T3.acc_id1 \n");
				sb.append("OR T2.ag=T3.acc_id1 \n");
				sb.append(") T4 GROUP BY acc_id \n");
				sb.append(") H\n");
				sb.append("ON A.acc_id=H.acc_id \n");
				sb.append("LEFT JOIN \n");
				sb.append("(SELECT index_of_account,handicap_name FROM ctt_manager.ctt_lottery_handicap WHERE handicap_id = 1)h1 \n");
				sb.append("on (B.handicap)&(h1.index_of_account) >  0 \n");
				sb.append("LEFT JOIN \n");
				sb.append("(SELECT index_of_account,handicap_name FROM ctt_manager.ctt_lottery_handicap WHERE handicap_id = 2)h2 \n");
				sb.append("on (B.handicap)&(h2.index_of_account) >  0 \n");
				sb.append("LEFT JOIN \n");
				sb.append("(SELECT index_of_account,handicap_name FROM ctt_manager.ctt_lottery_handicap WHERE handicap_id = 3)h3 \n");
				sb.append("on (B.handicap)&(h3.index_of_account) >  0 \n");
				sb.append("LEFT JOIN \n");
				sb.append("(SELECT index_of_account,handicap_name FROM ctt_manager.ctt_lottery_handicap WHERE handicap_id = 4)h4 \n");
				sb.append("on (B.handicap)&(h4.index_of_account) >  0 \n");
				sb.append("LEFT JOIN \n");
				sb.append("(SELECT index_of_account,handicap_name FROM ctt_manager.ctt_lottery_handicap WHERE handicap_id = 5)h5 \n");
				sb.append("on (B.handicap)&(h5.index_of_account) >  0 \n");
				
			

				params.add(accId);
				params.add(accId);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				LOG.debug(listMap.toString());

				for (int i = 0; i < listMap.size(); i++) {
					for (Object key : listMap.get(i).keySet()) {
						result.put(key.toString(), listMap.get(i).get(key.toString()));
					}
				}
			}
		} catch (Exception e) {
			LOG.debug("showOpsLog_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				rollBackDB();
			} catch (SQLException e1) {
				LOG.debug("rollBackDB_Exception===" + e.getMessage());
				ShowLog.err(LOG, e);
			}
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (params != null) {
				params.clear();
				params = null;
			}

			if (listMap != null) {
				listMap.clear();
				listMap = null;
			}
		}
		return result;

	}

	@Override
	public boolean addAccDeails(long accId) {
		boolean boole = false;

		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return boole;
			} else {
				insertMap.put("acc_id", accId);

				long count = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_member_basic_setting", insertMap);
				if (count == -1) {
					boole = true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return boole;
	}

	@Override
	public boolean updateBankDefaultData(long accId, String bank, String bankAcc, String bankAccName, String bankCardBranches, String area) {
		boolean boole = false;
		StringBuilder sb = null;
		List<Object> updateObj = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return boole;
			} else {
				updateObj = new ArrayList<Object>();
				sb = new StringBuilder();
				sb.append(
						"update ctt_manager.ctt_member_basic_setting set bank1 = ? ,  bank_acc1= ? , bank_acc_name1 = ? , bank_card_branches1 = ? , area1 = ? where acc_id = ? ");
				updateObj.add(bank);
				updateObj.add(bankAcc);
				updateObj.add(bankAccName);
				updateObj.add(bankCardBranches);
				updateObj.add(area);
				updateObj.add(accId);
				long count = StmtUtil.update(this.WRITE_CONN, sb.toString(), updateObj);
				if (count > 0) {
					boole = true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return boole;
	}

	@Override
	public boolean updateBankNoDefaultData(long accId, String bank, String bankAcc, String bankAccName, String bankCardBranches, String area) {
		boolean boole = false;
		StringBuilder sb = null;
		List<Object> updateObj = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return boole;
			} else {
				updateObj = new ArrayList<Object>();
				sb = new StringBuilder();
				sb.append(
						"update ctt_manager.ctt_member_basic_setting set bank2 = ? , bank_acc2= ? , bank_acc_name2 = ? , bank_card_branches2 = ? , area2 = ? where acc_id = ? ");
				updateObj.add(bank);
				updateObj.add(bankAcc);
				updateObj.add(bankAccName);
				updateObj.add(bankCardBranches);
				updateObj.add(area);
				updateObj.add(accId);
				long count = StmtUtil.update(this.WRITE_CONN, sb.toString(), updateObj);
				if (count > 0) {
					boole = true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return boole;
	}

	@Override
	public boolean addPunchGameAccInfo(long accId) throws SQLException {
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				insertMap.put("acc_id", accId);

				long count = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_member_punch_game_acc_info", insertMap);
				LOG.debug("addPunchGameAccInfo===" + count);
				if (count == -1) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return false;
	}

	@Override
	public boolean addPlatform(long platformScId, long cashBcId, long trialPlayBcId) {
		boolean boole = false;

		Map<String, Object> insertMap = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return boole;
			} else {
				insertMap = new ConcurrentHashMap<String, Object>();
				insertMap.put("platform_sc_id", platformScId);
				insertMap.put("cash_bc_id", cashBcId);
				insertMap.put("trialPlay_bc_id", trialPlayBcId);
				long count = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_platform", insertMap);
				LOG.debug(count);
				if (count > 0) {
					boole = true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {

		}
		return boole;
	}

	@Override
	public long insertGameServer(String gameServerId, int bet, int maxPlayer, int gameTimesType, int commission, int thread, int beatsTimeOut,
			int gameTimeOut, int autoTimeStamp, int waitContinueTimeOut, int continueTimesType, String serviceTime, int serverStatus) {

		LOG.debug("insertGameServer");
		Map<String, Object> insertMap = null;
		long sid = 0;
		StringBuilder sb = null;
		try {
			sb = new StringBuilder();
			insertMap = new ConcurrentHashMap<String, Object>();
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("no connection");
				return sid;
			} else {
				insertMap.put("game_server_id", gameServerId);
				insertMap.put("bet", bet);
				insertMap.put("max_player", maxPlayer);
				insertMap.put("game_times_type", gameTimesType);
				insertMap.put("commission", commission);
				insertMap.put("thread", thread);
				insertMap.put("beats_time_out", beatsTimeOut);
				insertMap.put("game_time_out", gameTimeOut);
				insertMap.put("auto_time_stamp", autoTimeStamp);
				insertMap.put("wait_continue_time_out", waitContinueTimeOut);
				insertMap.put("continue_times_type", continueTimesType);
				insertMap.put("service_time", serviceTime);
				insertMap.put("server_status", serverStatus);
				sid = StmtUtil.insertNoCommitByMap(this.WRITE_CONN, "ctt_manager.ctt_manager_game_set", insertMap);
				LOG.debug("insertGameServer_sid===" + sid);
			}
		} catch (Exception e) {
			sid = 0;
			LOG.debug("insertGameServer_Exception===" + e);
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return sid;
	}

	@Override
	public Map<String, Object> getBankCardData(long accId) {
		Map<String, Object> result = null;
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		List<Object> params = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return result;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();
				listMap = new ArrayList<Map<String, Object>>();
				result = new ConcurrentHashMap<String, Object>();

				sb.append(
						"select bank_card_id , acc_id , card_number , bank , bank_acc_name , bank_acc , bank_card_branches , area from ctt_manager.ctt_member_bank_card where bank_card_status = 1 and acc_id = ? \n");

				params.add(accId);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				LOG.debug(listMap.toString());

				for (int i = 0; i < listMap.size(); i++) {
					for (Object key : listMap.get(i).keySet()) {
						int con = i + 1;
						result.put(key.toString() + con, listMap.get(i).get(key.toString()));
					}
				}

				LOG.debug(result.toString());
			}
		} catch (Exception e) {
			LOG.debug("showOpsLog_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				rollBackDB();
			} catch (SQLException e1) {
				LOG.debug("rollBackDB_Exception===" + e.getMessage());
				ShowLog.err(LOG, e);
			}
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (params != null) {
				params.clear();
				params = null;
			}

			if (listMap != null) {
				listMap.clear();
				listMap = null;
			}
		}
		return result;
	}

	@Override
	public boolean disableBankCard(int bankCardId, long accId) {
		boolean todo = false;
		StringBuilder sb = null;
		List<Object> updateObj = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				updateObj = new ArrayList<Object>();
				sb = new StringBuilder();
				sb.append("update ctt_manager.ctt_member_bank_card set bank_card_status = 0 where bank_card_id = ? and  acc_id = ?");
				updateObj.add(bankCardId);
				updateObj.add(accId);
				int count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);
				if (count > 0) {
					todo = true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return todo;
	}

	@Override
	public Map<String, Object> getGameServerData() {
		StringBuilder sb = null;
		List<Object> params = null;
		List<Map<String, Object>> mapList = null;
		Map<String, Object> map = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();
				mapList = new ArrayList<Map<String, Object>>();
				map = new ConcurrentHashMap<String, Object>();

				sb.append("select * from ctt_manager.ctt_manager_game_set \n");
				mapList = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				for (int i = 0; i < mapList.size(); i++) {
					map.put("" + i, mapList.get(i));
				}
			}
		} catch (Exception e) {
			mapList = null;
			LOG.debug("getGameServerData_Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params.clear();
			}
			params = null;
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (mapList != null) {
				mapList.clear();
				mapList = null;
			}
		}
		return map;
	}

	@Override
	public boolean updateGameServer(int sid, String gameServerId, int nextBet, int nextMaxPlayer, int nextGameTimesType, int nextCommission,
			int nextThread, int nextBeatsTimeOut, int nextGameTimeOut, int nextAutoTimeStamp, int nextWaitContinueTimeOut, int nextContinueTimesType,
			String nextServiceTime, int nextServerStatus) {
		List<Object> updateList = null;
		StringBuilder sb = null;
		boolean todo = false;

		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				updateList = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("update ctt_manager.ctt_manager_game_set \n");
				sb.append("set \n");
				sb.append("next_bet = ? \n");
				sb.append(",next_max_player = ? \n");
				sb.append(",next_game_times_type = ? \n");
				sb.append(",next_commission = ? \n");
				sb.append(",next_thread = ? \n");
				sb.append(",next_beats_time_out = ? \n");
				sb.append(",next_game_time_out = ? \n");
				sb.append(",next_auto_time_stamp = ? \n");
				sb.append(",next_wait_continue_time_out = ? \n");
				sb.append(",next_continue_times_type = ? \n");
				sb.append(",next_service_time = ? \n");
				sb.append(",next_server_status = ? \n");
				sb.append("where \n");
				sb.append("sid = ? AND game_server_id = ? \n");
				updateList.add(nextBet);
				updateList.add(nextMaxPlayer);
				updateList.add(nextGameTimesType);
				updateList.add(nextCommission);
				updateList.add(nextThread);
				updateList.add(nextBeatsTimeOut);
				updateList.add(nextGameTimeOut);
				updateList.add(nextAutoTimeStamp);
				updateList.add(nextWaitContinueTimeOut);
				updateList.add(nextContinueTimesType);
				updateList.add(nextServiceTime);
				updateList.add(nextServerStatus);
				updateList.add(sid);
				updateList.add(gameServerId);

				int cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateList);
				LOG.debug("cou" + cou);
				if (cou == 1) {
					todo = true;
					if (todo) {
						todo = commitDB();
					}
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("updateGameServer_Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateList != null) {
				updateList.clear();
				updateList = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return todo;
	}

	private Map<String, Object> selectMagSql(String selectA, List<Object> selectObj, int levelType, long userId, long accId, boolean isStatus,
			int status, int sort, int asc) {
		Map<String, Object> map = null;
		StringBuilder sb = null;
		try {
			map = new ConcurrentHashMap<String, Object>();
			sb = new StringBuilder();
			sb.append("	SELECT  A.acc_id  \n");
			sb.append("	,G.onlines AS onlines     ,C.nickname,C.acc_name  \n");
			sb.append("	,D.create_time,A.acc_status,E.ip,E.last_login_time  \n");
			sb.append("	,J.group_text  \n");
			sb.append("	,CASE WHEN B.com IS NULL THEN 0 ELSE B.com END AS 'com'   \n");
			sb.append("	,CASE WHEN B.sc IS NULL THEN 0 ELSE B.sc END AS 'sc'   \n");
			sb.append("	,CASE WHEN B.bc IS NULL THEN 0 ELSE B.bc END AS 'bc'   \n");
			sb.append("	,CASE WHEN B.co IS NULL THEN 0 ELSE B.co END AS 'co'   \n");
			sb.append("	,CASE WHEN B.sa IS NULL THEN 0 ELSE B.sa END AS 'sa'   \n");
			sb.append("	,CASE WHEN B.ag IS NULL THEN 0 ELSE B.ag END AS 'ag'   \n");
			sb.append("	,CASE WHEN B.ag1 IS NULL THEN 0 ELSE B.ag1 END AS 'ag1'   \n");
			sb.append("	,CASE WHEN B.ag2 IS NULL THEN 0 ELSE B.ag2 END AS 'ag2'   \n");
			sb.append("	,CASE WHEN B.ag3 IS NULL THEN 0 ELSE B.ag3 END AS 'ag3'   \n");
			sb.append("	,CASE WHEN B.ag4 IS NULL THEN 0 ELSE B.ag4 END AS 'ag4'   \n");
			sb.append("	,CASE WHEN B.ag5 IS NULL THEN 0 ELSE B.ag5 END AS 'ag5'   \n");
			sb.append("	,CASE WHEN B.ag6 IS NULL THEN 0 ELSE B.ag6 END AS 'ag6'   \n");
			sb.append("	,CASE WHEN B.ag7 IS NULL THEN 0 ELSE B.ag7 END AS 'ag7'   \n");
			sb.append("	,CASE WHEN B.ag8 IS NULL THEN 0 ELSE B.ag8 END AS 'ag8'  \n");
			sb.append("	,CASE WHEN B.ag9 IS NULL THEN 0 ELSE B.ag9 END AS 'ag9'   \n");
			sb.append("	,CASE WHEN B.ag10 IS NULL THEN 0 ELSE B.ag10 END AS 'ag10'    \n");
			sb.append("	,CASE WHEN B.mem IS NULL THEN 0 ELSE B.mem END AS 'mem'   \n");
			sb.append("	,'" + ACC_LEVEL_NICKNAME_ARR[levelType] + "' AS acc_type  \n");
			for (int i = 0; i < ACC_LEVEL_MAG.length; i++) {
				if (levelType > ACC_LEVEL_MAG[i]) {
					sb.append("	,CASE WHEN level_type<=" + ACC_LEVEL_SC + " THEN " + ACC_LEVEL_MAG_TABLE_NAME[i] + "_id ELSE '' END AS "
							+ ACC_LEVEL_MAG_TABLE_NAME[i] + "_id  \n");
					sb.append("	,CASE WHEN level_type<=" + ACC_LEVEL_SC + " THEN (CASE WHEN TRIM(sc_nickname)='' THEN " + ACC_LEVEL_MAG_TABLE_NAME[i]
							+ "_acc_name ELSE " + ACC_LEVEL_MAG_TABLE_NAME[i] + "_nickname END)" + " ELSE '' END AS " + ACC_LEVEL_MAG_TABLE_NAME[i]
							+ "_nickname  \n");
					sb.append("	,CASE WHEN level_type<=" + ACC_LEVEL_SC + " THEN " + ACC_LEVEL_MAG_TABLE_NAME[i] + "_acc_name ELSE '' END AS "
							+ ACC_LEVEL_MAG_TABLE_NAME[i] + "_acc_name  \n");
					sb.append("	,CASE WHEN level_type<=" + ACC_LEVEL_SC + " THEN " + ACC_LEVEL_MAG_TABLE_NAME[i] + "_acc_status ELSE '' END AS "
							+ ACC_LEVEL_MAG_TABLE_NAME[i] + "_acc_status  \n");
				} else {
					sb.append("	,'' AS " + ACC_LEVEL_MAG_TABLE_NAME[i] + "_id" + ",'' AS " + ACC_LEVEL_MAG_TABLE_NAME[i] + "_nickname" + ",'' AS "
							+ ACC_LEVEL_MAG_TABLE_NAME[i] + "_acc_name " + ", '' AS " + ACC_LEVEL_MAG_TABLE_NAME[i] + "_acc_status \n");
				}

			}

			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_COM + " THEN -1 ELSE -1 END AS g1_com   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SC + " THEN I1.current_max_ratio WHEN A.acc_level_type=" + ACC_LEVEL_BC
					+ " THEN I1.upper_min_ratio ELSE -1 END AS g1_sc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_CO + " THEN I1.upper_min_ratio ELSE -1 END AS g1_bc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SA + " THEN I1.upper_min_ratio ELSE -1 END AS g1_co   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG + " THEN I1.upper_min_ratio ELSE -1 END AS g1_sa   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG1 + " THEN I1.upper_min_ratio ELSE -1 END AS g1_ag   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG2 + " THEN I1.upper_min_ratio ELSE -1 END AS g1_ag1   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG3 + " THEN I1.upper_min_ratio ELSE -1 END AS g1_ag2  \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG4 + " THEN I1.upper_min_ratio ELSE -1 END AS g1_ag3   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG5 + " THEN I1.upper_min_ratio ELSE -1 END AS g1_ag4   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG6 + " THEN I1.upper_min_ratio ELSE -1 END AS g1_ag5   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG7 + " THEN I1.upper_min_ratio ELSE -1 END AS g1_ag6   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG8 + " THEN I1.upper_min_ratio ELSE -1 END AS g1_ag7   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG9 + " THEN I1.upper_min_ratio ELSE -1 END AS g1_ag8   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG10 + " THEN I1.upper_min_ratio ELSE -1 END AS g1_ag9   \n");
			sb.append("	,-1 AS g1_ag10   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_COM + " THEN -1 ELSE -1 END AS g2_com   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SC + " THEN I2.current_max_ratio WHEN A.acc_level_type=" + ACC_LEVEL_BC
					+ " THEN I2.upper_min_ratio ELSE -1 END AS g2_sc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_CO + " THEN I2.upper_min_ratio ELSE -1 END AS g2_bc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SA + " THEN I2.upper_min_ratio ELSE -1 END AS g2_co   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG + " THEN I2.upper_min_ratio ELSE -1 END AS g2_sa   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG1 + " THEN I1.upper_min_ratio ELSE -1 END AS g2_ag   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG2 + " THEN I1.upper_min_ratio ELSE -1 END AS g2_ag1   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG3 + " THEN I1.upper_min_ratio ELSE -1 END AS g2_ag2  \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG4 + " THEN I1.upper_min_ratio ELSE -1 END AS g2_ag3   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG5 + " THEN I1.upper_min_ratio ELSE -1 END AS g2_ag4   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG6 + " THEN I1.upper_min_ratio ELSE -1 END AS g2_ag5   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG7 + " THEN I1.upper_min_ratio ELSE -1 END AS g2_ag6   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG8 + " THEN I1.upper_min_ratio ELSE -1 END AS g2_ag7   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG9 + " THEN I1.upper_min_ratio ELSE -1 END AS g2_ag8   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG10 + " THEN I1.upper_min_ratio ELSE -1 END AS g2_ag9   \n");
			sb.append("	,-1 AS g2_ag10   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_COM + " THEN -1 ELSE -1 END AS g3_com   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SC + " THEN I3.current_max_ratio WHEN A.acc_level_type=" + ACC_LEVEL_BC
					+ " THEN I3.upper_min_ratio ELSE -1 END AS g3_sc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_CO + " THEN I3.upper_min_ratio ELSE -1 END AS g3_bc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SA + " THEN I3.upper_min_ratio ELSE -1 END AS g3_co   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG + " THEN I3.upper_min_ratio ELSE -1 END AS g3_sa   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG1 + " THEN I1.upper_min_ratio ELSE -1 END AS g3_ag   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG2 + " THEN I1.upper_min_ratio ELSE -1 END AS g3_ag1   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG3 + " THEN I1.upper_min_ratio ELSE -1 END AS g3_ag2  \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG4 + " THEN I1.upper_min_ratio ELSE -1 END AS g3_ag3   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG5 + " THEN I1.upper_min_ratio ELSE -1 END AS g3_ag4   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG6 + " THEN I1.upper_min_ratio ELSE -1 END AS g3_ag5   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG7 + " THEN I1.upper_min_ratio ELSE -1 END AS g3_ag6   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG8 + " THEN I1.upper_min_ratio ELSE -1 END AS g3_ag7   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG9 + " THEN I1.upper_min_ratio ELSE -1 END AS g3_ag8   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG10 + " THEN I1.upper_min_ratio ELSE -1 END AS g3_ag9   \n");
			sb.append("	,-1 AS g3_ag10   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_COM + " THEN -1 ELSE -1 END AS g4_com   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SC + " THEN I4.current_max_ratio WHEN A.acc_level_type=" + ACC_LEVEL_BC
					+ " THEN I4.upper_min_ratio ELSE -1 END AS g4_sc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_CO + " THEN I4.upper_min_ratio ELSE -1 END AS g4_bc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SA + " THEN I4.upper_min_ratio ELSE -1 END AS g4_co   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG + " THEN I4.upper_min_ratio ELSE -1 END AS g4_sa   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG1 + " THEN I1.upper_min_ratio ELSE -1 END AS g4_ag   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG2 + " THEN I1.upper_min_ratio ELSE -1 END AS g4_ag1   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG3 + " THEN I1.upper_min_ratio ELSE -1 END AS g4_ag2  \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG4 + " THEN I1.upper_min_ratio ELSE -1 END AS g4_ag3   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG5 + " THEN I1.upper_min_ratio ELSE -1 END AS g4_ag4   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG6 + " THEN I1.upper_min_ratio ELSE -1 END AS g4_ag5   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG7 + " THEN I1.upper_min_ratio ELSE -1 END AS g4_ag6   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG8 + " THEN I1.upper_min_ratio ELSE -1 END AS g4_ag7   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG9 + " THEN I1.upper_min_ratio ELSE -1 END AS g4_ag8   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG10 + " THEN I1.upper_min_ratio ELSE -1 END AS g4_ag9   \n");
			sb.append("	,-1 AS g4_ag10   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_COM + " THEN -1 ELSE -1 END AS g5_com   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SC + " THEN I5.current_max_ratio WHEN A.acc_level_type=" + ACC_LEVEL_BC
					+ " THEN I5.upper_min_ratio ELSE -1 END AS g5_sc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_CO + " THEN I5.upper_min_ratio ELSE -1 END AS g5_bc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SA + " THEN I5.upper_min_ratio ELSE -1 END AS g5_co   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG + " THEN I5.upper_min_ratio ELSE -1 END AS g5_sa  \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG1 + " THEN I1.upper_min_ratio ELSE -1 END AS g5_ag   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG2 + " THEN I1.upper_min_ratio ELSE -1 END AS g5_ag1   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG3 + " THEN I1.upper_min_ratio ELSE -1 END AS g5_ag2  \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG4 + " THEN I1.upper_min_ratio ELSE -1 END AS g5_ag3   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG5 + " THEN I1.upper_min_ratio ELSE -1 END AS g5_ag4   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG6 + " THEN I1.upper_min_ratio ELSE -1 END AS g5_ag5   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG7 + " THEN I1.upper_min_ratio ELSE -1 END AS g5_ag6   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG8 + " THEN I1.upper_min_ratio ELSE -1 END AS g5_ag7   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG9 + " THEN I1.upper_min_ratio ELSE -1 END AS g5_ag8   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG10 + " THEN I1.upper_min_ratio ELSE -1 END AS g5_ag9    \n");
			sb.append("	,-1 AS g5_ag10   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_COM + " THEN -1 ELSE -1 END AS next_g1_com   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SC + " THEN I1.next_current_max_ratio WHEN A.acc_level_type=" + ACC_LEVEL_BC
					+ " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_sc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_CO + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_bc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SA + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_co   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_sa  \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG1 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG2 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag1   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG3 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag2  \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG4 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag3   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG5 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag4   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG6 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag5   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG7 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag6   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG8 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag7   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG9 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag8   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG10 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g1_ag9     \n");
			sb.append("	,-1 as next_g1_ag10   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_COM + " THEN -1 ELSE -1 END AS next_g2_com   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SC + " THEN I2.next_current_max_ratio WHEN A.acc_level_type=" + ACC_LEVEL_BC
					+ " THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_sc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_CO + " THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_bc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SA + " THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_co   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG + " THEN I2.next_upper_min_ratio ELSE -1 END AS next_g2_sa  \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG1 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG2 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag1   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG3 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag2  \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG4 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag3   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG5 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag4   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG6 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag5   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG7 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag6   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG8 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag7   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG9 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag8   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG10 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g2_ag9      \n");
			sb.append("	,-1 as next_g2_ag10   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_COM + " THEN -1 ELSE -1 END AS next_g3_com   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SC + " THEN I3.next_current_max_ratio WHEN A.acc_level_type=" + ACC_LEVEL_BC
					+ " THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_sc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_CO + " THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_bc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SA + " THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_co   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG + " THEN I3.next_upper_min_ratio ELSE -1 END AS next_g3_sa   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG1 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG2 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag1   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG3 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag2  \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG4 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag3   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG5 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag4   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG6 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag5   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG7 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag6   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG8 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag7   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG9 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag8   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG10 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g3_ag9     \n");
			sb.append("	,-1 as next_g3_ag10   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_COM + " THEN -1 ELSE -1 END AS next_g4_com   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SC + " THEN I4.next_current_max_ratio WHEN A.acc_level_type=" + ACC_LEVEL_BC
					+ " THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_sc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_CO + " THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_bc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SA + " THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_co   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG + " THEN I4.next_upper_min_ratio ELSE -1 END AS next_g4_sa  \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG1 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG2 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag1   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG3 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag2  \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG4 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag3   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG5 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag4   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG6 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag5   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG7 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag6   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG8 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag7   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG9 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag8   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG10 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g4_ag9      \n");
			sb.append("	,-1 as next_g4_ag10   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_COM + " THEN -1 ELSE -1 END AS next_g5_com   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SC + " THEN I5.next_current_max_ratio WHEN A.acc_level_type=" + ACC_LEVEL_BC
					+ " THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_sc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_CO + " THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_bc   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_SA + " THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_co   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG + " THEN I5.next_upper_min_ratio ELSE -1 END AS next_g5_sa  \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG1 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG2 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag1   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG3 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag2  \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG4 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag3   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG5 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag4   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG6 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag5   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG7 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag6   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG8 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag7   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG9 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag8   \n");
			sb.append("	,CASE WHEN A.acc_level_type=" + ACC_LEVEL_AG10 + " THEN I1.next_upper_min_ratio ELSE -1 END AS next_g5_ag9      \n");
			sb.append("	,-1 as next_g5_ag10  \n");
			sb.append("	, K.update_status   \n");
			sb.append("	FROM  \n");
			sb.append("	(  \n");
			sb.append("" + selectA + " \n");
			sb.append("	) A  \n");
			sb.append("	LEFT OUTER JOIN  \n");
			sb.append("	(  \n");
			sb.append("	SELECT DISTINCT(acc_id)  \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + ACC_LEVEL_COM + " THEN 1 ELSE 0 END) AS 'com'  \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + ACC_LEVEL_SC + " THEN 1 ELSE 0 END) AS 'sc'  \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + ACC_LEVEL_BC + " THEN 1 ELSE 0 END) AS 'bc'  \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + ACC_LEVEL_CO + " THEN 1 ELSE 0 END) AS 'co'  \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + ACC_LEVEL_SA + " THEN 1 ELSE 0 END) AS 'sa'  \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + ACC_LEVEL_AG + " THEN 1 ELSE 0 END) AS 'ag'   \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + ACC_LEVEL_AG1 + " THEN 1 ELSE 0 END) AS 'ag1'  \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + ACC_LEVEL_AG2 + " THEN 1 ELSE 0 END) AS 'ag2'  \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + ACC_LEVEL_AG3 + " THEN 1 ELSE 0 END) AS 'ag3'  \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + ACC_LEVEL_AG4 + " THEN 1 ELSE 0 END) AS 'ag4'  \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + ACC_LEVEL_AG5 + " THEN 1 ELSE 0 END) AS 'ag5'  \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + ACC_LEVEL_AG6 + " THEN 1 ELSE 0 END) AS 'ag6'  \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + ACC_LEVEL_AG7 + " THEN 1 ELSE 0 END) AS 'ag7'  \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + ACC_LEVEL_AG8 + " THEN 1 ELSE 0 END) AS 'ag8'  \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + ACC_LEVEL_AG9 + " THEN 1 ELSE 0 END) AS 'ag9'  \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + ACC_LEVEL_AG10 + " THEN 1 ELSE 0 END) AS 'ag10'  \n");
			sb.append("	,SUM(CASE WHEN acc_level_type=" + LEVEL_TYPE_MEM + " THEN 1 ELSE 0 END) AS 'mem'   \n");
			sb.append("	FROM  \n");
			sb.append("	(  \n");
			sb.append("	SELECT " + ACC_LEVEL_MAG_TABLE_NAME[levelType - 2]
					+ " AS acc_id,acc_level_type,sc,bc,co,sa,ag,ag1,ag2,ag3,ag4,ag5,ag6,ag7,ag8,ag9,ag10 FROM ctt_manager.ctt_manager_acc_level  \n");
			sb.append("	WHERE (CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?)  \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_ADMIN + " THEN TRUE  \n");
			sb.append("	WHEN " + ACC_LEVEL_COM + " THEN TRUE  \n");
			sb.append("	WHEN " + ACC_LEVEL_SC + " THEN sc =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_BC + " THEN bc =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_CO + " THEN co =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_SA + " THEN sa =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG + " THEN ag =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG1 + " THEN ag1 =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG2 + " THEN ag2 =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG3 + " THEN ag3 = ? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG4 + " THEN ag4 =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN ag5 =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG6 + " THEN ag6 =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG7 + " THEN ag7 =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG8 + " THEN ag8 =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG9 + " THEN ag9 =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG10 + " THEN ag10 =? ELSE FALSE END) AND acc_level_type>?  \n");
			selectObj.add(accId);
			selectObj.add(levelType);
			if (isStatus) {
				sb.append("	AND acc_status=?  \n");
				selectObj.add(status);
			}
			sb.append("	UNION ALL  \n");
			sb.append("	SELECT " + ACC_LEVEL_MAG_TABLE_NAME[levelType - 2] + " AS acc_id," + LEVEL_TYPE_MEM
					+ " AS acc_level_type,sc,bc,co,sa,ag,ag1,ag2,ag3,ag4,ag5,ag6,ag7,ag8,ag9,ag10 FROM ctt_manager.ctt_member_acc_level  \n");
			sb.append("	WHERE (CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?)  \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_ADMIN + " THEN TRUE  \n");
			sb.append("	WHEN " + ACC_LEVEL_COM + " THEN TRUE  \n");
			sb.append("	WHEN " + ACC_LEVEL_SC + " THEN sc =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_BC + " THEN bc =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_CO + " THEN co =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_SA + " THEN sa =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG + " THEN ag =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG1 + " THEN ag1 =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG2 + " THEN ag2 =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG3 + " THEN ag3 = ? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG4 + " THEN ag4 =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN ag5 =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG6 + " THEN ag6 =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG7 + " THEN ag7 =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG8 + " THEN ag8 =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG9 + " THEN ag9 =? \n");
			selectObj.add(accId);
			sb.append("	WHEN " + ACC_LEVEL_AG10 + " THEN ag10 =? ELSE FALSE END)  \n");
			selectObj.add(accId);
			if (isStatus) {
				sb.append("	AND acc_status=?  \n");
				selectObj.add(status);
			}
			sb.append("	) T1 GROUP BY acc_id  \n");
			sb.append("	) B ON A.acc_id=B.acc_id  \n");
			sb.append("	INNER JOIN  \n");
			sb.append("	(SELECT acc_id,acc_name,nickname,auth_group FROM ctt_manager.ctt_manager_acc WHERE acc_id IN  \n");
			sb.append("	(SELECT acc_id FROM ctt_manager.ctt_manager_acc_level WHERE  acc_level_type=?)  \n");
			selectObj.add(levelType);
			sb.append("	) C  \n");
			sb.append("	ON A.acc_id=C.acc_id  \n");
			sb.append("	INNER JOIN  \n");
			sb.append("	(SELECT acc_id,DATE_FORMAT(create_datetime, '%Y-%m-%d %T') as create_time FROM ctt_manager.ctt_manager_create) D  \n");
			sb.append("	ON A.acc_id=D.acc_id  \n");
			sb.append("	INNER JOIN   \n");
			sb.append("	(SELECT acc_id \n");
			sb.append(
					",(CASE WHEN DATE_FORMAT(last_login_datetime, '%Y-%m-%d %T') = DATE_FORMAT((SELECT COLUMN_DEFAULT FROM information_schema.columns where TABLE_SCHEMA = 'ctt_manager' AND TABLE_NAME = 'ctt_manager_last_login' AND COLUMN_NAME = 'last_login_datetime'), '%Y-%m-%d %T') THEN '0000-00-00 00:00:00' ELSE DATE_FORMAT(last_login_datetime, '%Y-%m-%d %T') END) as last_login_time \n");
			sb.append(",CAST(CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) AS char) ip FROM ctt_manager.ctt_manager_last_login) E \n");
			sb.append("	ON A.acc_id=E.acc_id  \n");
			sb.append("	INNER JOIN  \n");
			sb.append("	(  \n");
			sb.append("	SELECT acc_id \n");
			for (int i = 0; i < ACC_LEVEL_MAG.length; i++) {
				if (levelType > ACC_LEVEL_MAG[i]) {
					sb.append("	,FORMAT(SUM(" + ACC_LEVEL_MAG_TABLE_NAME[i] + "_id),0) AS " + ACC_LEVEL_MAG_TABLE_NAME[i] + "_id" + ",GROUP_CONCAT("
							+ ACC_LEVEL_MAG_TABLE_NAME[i] + "_nickname SEPARATOR'') AS " + ACC_LEVEL_MAG_TABLE_NAME[i] + "_nickname"
							+ ",GROUP_CONCAT(" + ACC_LEVEL_MAG_TABLE_NAME[i] + "_acc_name SEPARATOR'') AS " + ACC_LEVEL_MAG_TABLE_NAME[i]
							+ "_acc_name " + ", FORMAT(SUM(" + ACC_LEVEL_MAG_TABLE_NAME[i] + "_acc_status),0) AS " + ACC_LEVEL_MAG_TABLE_NAME[i]
							+ "_acc_status \n");
				} else {
					sb.append("	,'' AS " + ACC_LEVEL_MAG_TABLE_NAME[i] + "_id" + ",'' AS " + ACC_LEVEL_MAG_TABLE_NAME[i] + "_nickname" + ",'' AS "
							+ ACC_LEVEL_MAG_TABLE_NAME[i] + "_acc_name " + ", '' AS " + ACC_LEVEL_MAG_TABLE_NAME[i] + "_acc_status \n");
				}

			}
			sb.append("	FROM  \n");
			sb.append("	(  \n");
			sb.append("	SELECT  \n");
			sb.append("	acc_id  \n");
			for (int i = 0; i < ACC_LEVEL_MAG.length; i++) {
				if (levelType > ACC_LEVEL_MAG[i]) {
					sb.append("	,CASE WHEN " + ACC_LEVEL_MAG_TABLE_NAME[i] + "=acc_id1 THEN acc_id1 ELSE '' END AS " + ACC_LEVEL_MAG_TABLE_NAME[i]
							+ "_id  \n");
					sb.append("	,CASE WHEN " + ACC_LEVEL_MAG_TABLE_NAME[i] + "=acc_id1 THEN nickname ELSE '' END AS " + ACC_LEVEL_MAG_TABLE_NAME[i]
							+ "_nickname  \n");
					sb.append("	,CASE WHEN " + ACC_LEVEL_MAG_TABLE_NAME[i] + "=acc_id1 THEN acc_name ELSE '' END AS " + ACC_LEVEL_MAG_TABLE_NAME[i]
							+ "_acc_name  \n");
					sb.append("	,CASE WHEN " + ACC_LEVEL_MAG_TABLE_NAME[i] + "=acc_id1 THEN acc_status ELSE '' END AS " + ACC_LEVEL_MAG_TABLE_NAME[i]
							+ "_acc_status  \n");
				} else {
					sb.append("	,'' AS " + ACC_LEVEL_MAG_TABLE_NAME[i] + "_id  \n");
					sb.append("	,'' AS " + ACC_LEVEL_MAG_TABLE_NAME[i] + "_nickname  \n");
					sb.append("	,'' AS " + ACC_LEVEL_MAG_TABLE_NAME[i] + "_acc_name  \n");
					sb.append("	,'' AS " + ACC_LEVEL_MAG_TABLE_NAME[i] + "_acc_status  \n");
				}

			}
			sb.append(
					"	FROM (SELECT acc_id,acc_level_type,sc,bc,co,sa,ag,ag1,ag2,ag3,ag4,ag5,ag6,ag7,ag8,ag9,ag10 FROM ctt_manager.ctt_manager_acc_level  \n");
			sb.append("	WHERE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id= ?)<" + ACC_LEVEL_MEM + "  \n");
			selectObj.add(userId);
			sb.append("	AND (CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?)  \n");
			selectObj.add(userId);
			sb.append("	WHEN " + ACC_LEVEL_ADMIN + " THEN TRUE  \n");
			sb.append("	WHEN " + ACC_LEVEL_COM + " THEN TRUE  \n");
			sb.append("	WHEN " + ACC_LEVEL_SC + " THEN sc = ? \n");
			selectObj.add(userId);
			sb.append("	WHEN " + ACC_LEVEL_BC + " THEN bc = ? \n");
			selectObj.add(userId);
			sb.append("	WHEN " + ACC_LEVEL_CO + " THEN co = ? \n");
			selectObj.add(userId);
			sb.append("	WHEN " + ACC_LEVEL_SA + " THEN sa = ? \n");
			selectObj.add(userId);
			sb.append("	WHEN " + ACC_LEVEL_AG + " THEN ag = ? \n");
			selectObj.add(userId);
			sb.append("	WHEN " + ACC_LEVEL_AG1 + " THEN ag1 = ? \n");
			selectObj.add(userId);
			sb.append("	WHEN " + ACC_LEVEL_AG2 + " THEN ag2 = ? \n");
			selectObj.add(userId);
			sb.append("	WHEN " + ACC_LEVEL_AG3 + " THEN ag3 = ? \n");
			selectObj.add(userId);
			sb.append("	WHEN " + ACC_LEVEL_AG4 + " THEN ag4 = ? \n");
			selectObj.add(userId);
			sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN ag5 = ? \n");
			selectObj.add(userId);
			sb.append("	WHEN " + ACC_LEVEL_AG6 + " THEN ag6 = ? \n");
			selectObj.add(userId);
			sb.append("	WHEN " + ACC_LEVEL_AG7 + " THEN ag7 = ? \n");
			selectObj.add(userId);
			sb.append("	WHEN " + ACC_LEVEL_AG8 + " THEN ag8 = ? \n");
			selectObj.add(userId);
			sb.append("	WHEN " + ACC_LEVEL_AG9 + " THEN ag9 = ? \n");
			selectObj.add(userId);
			sb.append("	WHEN " + ACC_LEVEL_AG10 + " THEN ag10 = ? ELSE FALSE END)  \n");
			selectObj.add(userId);
			sb.append("	AND acc_level_type=?   \n");
			selectObj.add(levelType);
			sb.append("	)T2      \n");
			sb.append("	LEFT OUTER JOIN ( \n");
			sb.append("	SELECT  t1.acc_id as acc_id1 ,t1.nickname,t1.acc_name,t2.acc_status from \n");
			sb.append("	(SELECT acc_id ,nickname,acc_name FROM ctt_manager.ctt_manager_acc ) t1 \n");
			sb.append("	inner join  \n");
			sb.append("	(SELECT acc_id , acc_status FROM ctt_manager.ctt_manager_acc_level) t2 \n");
			sb.append("	on t1.acc_id = t2.acc_id \n");
			sb.append("	) T3  \n");
			sb.append("	ON T2.sc=T3.acc_id1  \n");
			sb.append("	OR T2.bc=T3.acc_id1  \n");
			sb.append("	OR T2.co=T3.acc_id1  \n");
			sb.append("	OR T2.sa=T3.acc_id1  \n");
			sb.append("	OR T2.ag=T3.acc_id1 \n");
			sb.append("	OR T2.ag1=T3.acc_id1  \n");
			sb.append("	OR T2.ag2=T3.acc_id1  \n");
			sb.append("	OR T2.ag3=T3.acc_id1  \n");
			sb.append("	OR T2.ag4=T3.acc_id1  \n");
			sb.append("	OR T2.ag5=T3.acc_id1  \n");
			sb.append("	OR T2.ag6=T3.acc_id1  \n");
			sb.append("	OR T2.ag7=T3.acc_id1  \n");
			sb.append("	OR T2.ag8=T3.acc_id1  \n");
			sb.append("	OR T2.ag9=T3.acc_id1 \n");
			sb.append("	OR T2.ag10=T3.acc_id1    \n");
			sb.append("	) T4 GROUP BY acc_id  \n");
			sb.append("	) F  \n");
			sb.append("	on A.acc_id=F.acc_id  \n");
			sb.append("	LEFT OUTER JOIN  \n");
			sb.append("	(  \n");
			sb.append(
					"	SELECT acc_id , case when online_datetime > CURRENT_TIMESTAMP and forced_logout_datetime > CURRENT_TIMESTAMP then 'Y' else 'N' end as onlines  FROM ctt_manager.ctt_manager_token) G  \n");
			sb.append("	on A.acc_id=G.acc_id  \n");
			sb.append("	LEFT OUTER JOIN   \n");
			sb.append("	(SELECT (acc_level_type*1) AS level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id=?) H  \n");
			selectObj.add(userId);
			sb.append("	on A.acc_id>0  \n");
			sb.append("	LEFT OUTER JOIN  \n");
			sb.append(
					"	(SELECT acc_id,upper_min_ratio, current_max_ratio,next_upper_min_ratio,next_current_max_ratio FROM ctt_manager.ctt_manager_ratio WHERE game_type="
							+ LOTTERY + ") I1  \n");
			sb.append("	on A.acc_id=I1.acc_id  \n");
			sb.append("	LEFT OUTER JOIN  \n");
			sb.append(
					"	(SELECT acc_id,upper_min_ratio, current_max_ratio,next_upper_min_ratio,next_current_max_ratio FROM ctt_manager.ctt_manager_ratio WHERE game_type="
							+ LIVEVIDEO + ") I2  \n");
			sb.append("	on A.acc_id=I2.acc_id  \n");
			sb.append("	LEFT OUTER JOIN  \n");
			sb.append(
					"	(SELECT acc_id,upper_min_ratio, current_max_ratio,next_upper_min_ratio,next_current_max_ratio FROM ctt_manager.ctt_manager_ratio WHERE game_type="
							+ MOVEMENT + ") I3  \n");
			sb.append("	on A.acc_id=I3.acc_id  \n");
			sb.append("	LEFT OUTER JOIN  \n");
			sb.append(
					"	(SELECT acc_id,upper_min_ratio, current_max_ratio,next_upper_min_ratio,next_current_max_ratio FROM ctt_manager.ctt_manager_ratio WHERE game_type="
							+ VIDEOGAME + ") I4  \n");
			sb.append("	on A.acc_id=I4.acc_id  \n");
			sb.append("	LEFT OUTER JOIN  \n");
			sb.append(
					"	(SELECT acc_id,upper_min_ratio, current_max_ratio,next_upper_min_ratio,next_current_max_ratio FROM ctt_manager.ctt_manager_ratio WHERE game_type="
							+ GAME + ") I5  \n");
			sb.append("	on A.acc_id=I5.acc_id  \n");
			sb.append("	LEFT OUTER JOIN   \n");
			sb.append("	ctt_manager.ctt_auth_group J  \n");
			sb.append("	ON C.auth_group=J.group_id  \n");
			sb.append("	LEFT JOIN  \n");
			sb.append("	( select (CASE (select acc_level_type from ctt_manager.ctt_manager_acc_level s2 where s2.acc_id = s1.acc_id)  \n");
			sb.append("	WHEN " + ACC_LEVEL_ADMIN + " THEN TRUE  \n");
			sb.append("	WHEN " + ACC_LEVEL_COM + " THEN TRUE  \n");
			sb.append("	WHEN " + ACC_LEVEL_SC + " THEN  TRUE  \n");
			sb.append("	WHEN " + ACC_LEVEL_BC + " THEN  IF((select acc_status from ctt_manager.ctt_manager_acc_level where acc_id = s1.sc) < "
					+ STATUS_DISABLED + ",TRUE,FALSE) \n");
			sb.append("	WHEN " + ACC_LEVEL_CO + " THEN  IF((select acc_status from ctt_manager.ctt_manager_acc_level where acc_id = s1.bc) < "
					+ STATUS_DISABLED + " ,TRUE,FALSE) \n");
			sb.append("	WHEN " + ACC_LEVEL_SA + " THEN  IF((select acc_status from ctt_manager.ctt_manager_acc_level where acc_id = s1.co) < "
					+ STATUS_DISABLED + ",TRUE,FALSE) \n");
			sb.append("	WHEN " + ACC_LEVEL_AG + " THEN  IF((select acc_status from ctt_manager.ctt_manager_acc_level where acc_id = s1.sa) < "
					+ STATUS_DISABLED + " ,TRUE,FALSE) \n");
			sb.append("	WHEN " + ACC_LEVEL_AG1 + " THEN  IF((select acc_status from ctt_manager.ctt_manager_acc_level where acc_id = s1.ag) < "
					+ STATUS_DISABLED + " ,TRUE,FALSE) \n");
			sb.append("	WHEN " + ACC_LEVEL_AG2 + " THEN  IF((select acc_status from ctt_manager.ctt_manager_acc_level where acc_id = s1.ag1) <"
					+ STATUS_DISABLED + " ,TRUE,FALSE) \n");
			sb.append("	WHEN " + ACC_LEVEL_AG3 + " THEN  IF((select acc_status from ctt_manager.ctt_manager_acc_level where acc_id = s1.ag2) < "
					+ STATUS_DISABLED + " ,TRUE,FALSE) \n");
			sb.append("	WHEN " + ACC_LEVEL_AG4 + " THEN  IF((select acc_status from ctt_manager.ctt_manager_acc_level where acc_id = s1.ag3) < "
					+ STATUS_DISABLED + " ,TRUE,FALSE) \n");
			sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN  IF((select acc_status from ctt_manager.ctt_manager_acc_level where acc_id = s1.ag4) < "
					+ STATUS_DISABLED + " ,TRUE,FALSE) \n");
			sb.append("	WHEN " + ACC_LEVEL_AG6 + " THEN  IF((select acc_status from ctt_manager.ctt_manager_acc_level where acc_id = s1.ag5) < "
					+ STATUS_DISABLED + " ,TRUE,FALSE) \n");
			sb.append("	WHEN " + ACC_LEVEL_AG7 + " THEN  IF((select acc_status from ctt_manager.ctt_manager_acc_level where acc_id = s1.ag6) < "
					+ STATUS_DISABLED + " ,TRUE,FALSE) \n");
			sb.append("	WHEN " + ACC_LEVEL_AG8 + " THEN  IF((select acc_status from ctt_manager.ctt_manager_acc_level where acc_id = s1.ag7) < "
					+ STATUS_DISABLED + " ,TRUE,FALSE) \n");
			sb.append("	WHEN " + ACC_LEVEL_AG9 + " THEN  IF((select acc_status from ctt_manager.ctt_manager_acc_level where acc_id = s1.ag8) < "
					+ STATUS_DISABLED + " ,TRUE,FALSE) \n");
			sb.append("	WHEN " + ACC_LEVEL_AG10 + " THEN  IF((select acc_status from ctt_manager.ctt_manager_acc_level where acc_id = s1.ag9) < "
					+ STATUS_DISABLED + " ,TRUE,FALSE)ELSE FALSE END) as update_status \n");
			sb.append("	, s1.acc_id from  ctt_manager.ctt_manager_acc_level s1) K \n");
			sb.append("	ON K.acc_id = A.acc_id \n");
			sb.append("ORDER BY " + orderBy[sort] + " " + ASC[asc] + " \n");

			map.put("sql", sb.toString());
			map.put("list", selectObj);

		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}

		return map;
	}

	@Override
	public List<Integer> selectGameRatioMem(long upAccId, int memGameUpperMin, int gameType) {
		StringBuilder sb = null;
		List<Object> selectList = null;
		List<Object> listObj = null;
		List<Integer> listIntRatio = null;
		List<Map<String, Integer>> managerRatio = null;
		Map<String, Integer> layer1 = null;
		Map<String, Integer> layer2 = null;
		Map<String, Integer> layer3 = null;
		Map<String, Integer> layer4 = null;
		Map<String, Integer> layer5 = null;
		Map<String, Integer> layer6 = null;
		Map<String, Integer> layer7 = null;
		Map<String, Integer> layer8 = null;
		Map<String, Integer> layer9 = null;
		Map<String, Integer> layer10 = null;
		Map<String, Integer> layer11 = null;
		Map<String, Integer> layer12 = null;
		Map<String, Integer> layer13 = null;
		Map<String, Integer> layer14 = null;
		Map<String, Integer> layer15 = null;
		Map<String, Integer> layer16 = null;

		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listIntRatio;
			} else {

				sb = new StringBuilder();
				selectList = new ArrayList<Object>();
				listObj = new ArrayList<Object>();

				sb.append("	select     			\n");
				sb.append("	case when A.sc =0 then 0 else Bsc.next_upper_min_ratio end as sc_game_upper_min,		\n");
				sb.append("	case when A.sc =0 then 0 else Bsc.next_upper_max_ratio end as sc_game_upper_max,		\n");
				sb.append("	case when A.sc =0 then 0 else Bsc.next_current_max_ratio end as sc_game_current_max,		\n");
				sb.append("	case when A.bc =0 then 0 else Bbc.next_upper_min_ratio end as bc_game_upper_min,		\n");
				sb.append("	case when A.bc =0 then 0 else Bbc.next_upper_max_ratio end as bc_game_upper_max,		\n");
				sb.append("	case when A.bc =0 then 0 else Bbc.next_current_max_ratio end as bc_game_current_max,		\n");
				sb.append("	case when A.co =0 then 0 else Bco.next_upper_min_ratio end as co_game_upper_min,		\n");
				sb.append("	case when A.co =0 then 0 else Bco.next_upper_max_ratio end as co_game_upper_max,		\n");
				sb.append("	case when A.co =0 then 0 else Bco.next_current_max_ratio end as co_game_current_max,		\n");
				sb.append("	case when A.sa =0 then 0 else Bsa.next_upper_min_ratio end as sa_game_upper_min,		\n");
				sb.append("	case when A.sa =0 then 0 else Bsa.next_upper_max_ratio end as sa_game_upper_max,		\n");
				sb.append("	case when A.sa =0 then 0 else Bsa.next_current_max_ratio end as sa_game_current_max,		\n");
				sb.append("	case when A.ag =0 then 0 else Bag.next_upper_min_ratio end as ag_game_upper_min,		\n");
				sb.append("	case when A.ag =0 then 0 else Bag.next_upper_max_ratio end as ag_game_upper_max,		\n");
				sb.append("	case when A.ag =0 then 0 else Bag.next_current_max_ratio end as ag_game_current_max,		\n");
				sb.append("	case when A.ag1 =0 then 0 else Bag1.next_upper_min_ratio end as ag1_game_upper_min,		\n");
				sb.append("	case when A.ag1 =0 then 0 else Bag1.next_upper_max_ratio end as ag1_game_upper_max,		\n");
				sb.append("	case when A.ag1 =0 then 0 else Bag1.next_current_max_ratio end as ag1_game_current_max,		\n");
				sb.append("	case when A.ag2 =0 then 0 else Bag2.next_upper_min_ratio end as ag2_game_upper_min,		\n");
				sb.append("	case when A.ag2 =0 then 0 else Bag2.next_upper_max_ratio end as ag2_game_upper_max,		\n");
				sb.append("	case when A.ag2 =0 then 0 else Bag2.next_current_max_ratio end as ag2_game_current_max,		\n");
				sb.append("	case when A.ag3 =0 then 0 else Bag3.next_upper_min_ratio end as ag3_game_upper_min,		\n");
				sb.append("	case when A.ag3 =0 then 0 else Bag3.next_upper_max_ratio end as ag3_game_upper_max,		\n");
				sb.append("	case when A.ag3 =0 then 0 else Bag3.next_current_max_ratio end as ag3_game_current_max,		\n");
				sb.append("	case when A.ag4 =0 then 0 else Bag4.next_upper_min_ratio end as ag4_game_upper_min,		\n");
				sb.append("	case when A.ag4 =0 then 0 else Bag4.next_upper_max_ratio end as ag4_game_upper_max,		\n");
				sb.append("	case when A.ag4 =0 then 0 else Bag4.next_current_max_ratio end as ag4_game_current_max,		\n");
				sb.append("	case when A.ag5 =0 then 0 else Bag5.next_upper_min_ratio end as ag5_game_upper_min,		\n");
				sb.append("	case when A.ag5 =0 then 0 else Bag5.next_upper_max_ratio end as ag5_game_upper_max,		\n");
				sb.append("	case when A.ag5 =0 then 0 else Bag5.next_current_max_ratio end as ag5_game_current_max,		\n");
				sb.append("	case when A.ag6 =0 then 0 else Bag6.next_upper_min_ratio end as ag6_game_upper_min,		\n");
				sb.append("	case when A.ag6 =0 then 0 else Bag6.next_upper_max_ratio end as ag6_game_upper_max,		\n");
				sb.append("	case when A.ag6 =0 then 0 else Bag6.next_current_max_ratio end as ag6_game_current_max,		\n");
				sb.append("	case when A.ag7 =0 then 0 else Bag7.next_upper_min_ratio end as ag7_game_upper_min,		\n");
				sb.append("	case when A.ag7 =0 then 0 else Bag7.next_upper_max_ratio end as ag7_game_upper_max,		\n");
				sb.append("	case when A.ag7 =0 then 0 else Bag7.next_current_max_ratio end as ag7_game_current_max,		\n");
				sb.append("	case when A.ag8 =0 then 0 else Bag8.next_upper_min_ratio end as ag8_game_upper_min,		\n");
				sb.append("	case when A.ag8 =0 then 0 else Bag8.next_upper_max_ratio end as ag8_game_upper_max,		\n");
				sb.append("	case when A.ag8 =0 then 0 else Bag8.next_current_max_ratio end as ag8_game_current_max,		\n");
				sb.append("	case when A.ag9 =0 then 0 else Bag9.next_upper_min_ratio end as ag9_game_upper_min,		\n");
				sb.append("	case when A.ag9 =0 then 0 else Bag9.next_upper_max_ratio end as ag9_game_upper_max,		\n");
				sb.append("	case when A.ag9 =0 then 0 else Bag9.next_current_max_ratio end as ag9_game_current_max,		\n");
				sb.append("	case when A.ag10 =0 then 0 else Bag10.next_upper_min_ratio end as ag10_game_upper_min,		\n");
				sb.append("	case when A.ag10 =0 then 0 else Bag10.next_upper_max_ratio end as ag10_game_upper_max,		\n");
				sb.append("	case when A.ag10 =0 then 0 else Bag10.next_current_max_ratio end as ag10_game_current_max,		\n");
				sb.append("	? as mem_game_upper_min,		\n");
				selectList.add(memGameUpperMin);
				sb.append("	A.upper_acc_level_type as upperAccLevelType 		\n");
				sb.append("	from 		\n");
				sb.append("	(	\n");
				sb.append("	SELECT acc_level_type as upper_acc_level_type,		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN acc_id ELSE sc END AS sc ,		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN acc_id ELSE bc END AS bc,		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN acc_id ELSE co END AS co,		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN acc_id ELSE sa END AS sa,		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN acc_id ELSE ag END AS ag, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN acc_id ELSE ag1 END AS ag1, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN acc_id ELSE ag2 END AS ag2, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN 0 WHEN 9 THEN acc_id ELSE ag3 END AS ag3, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN 0 WHEN 9 THEN 0 WHEN 10 THEN acc_id ELSE ag4 END AS ag4, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN 0 WHEN 9 THEN 0 WHEN 10 THEN 0 		\n");
				sb.append("	WHEN 11 THEN acc_id ELSE ag5 END AS ag5, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN 0 WHEN 9 THEN 0 WHEN 10 THEN 0 		\n");
				sb.append("	WHEN 11 THEN 0 WHEN 12 THEN acc_id ELSE ag6 END AS ag6, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN 0 WHEN 9 THEN 0 WHEN 10 THEN 0 		\n");
				sb.append("	WHEN 11 THEN 0 WHEN 12 THEN 0 WHEN 13 THEN acc_id ELSE ag7 END AS ag7, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN 0 WHEN 9 THEN 0 WHEN 10 THEN 0 		\n");
				sb.append("	WHEN 11 THEN 0 WHEN 12 THEN 0 WHEN 13 THEN 0 WHEN 14 THEN acc_id ELSE ag8 END AS ag8, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN 0 WHEN 9 THEN 0 WHEN 10 THEN 0 		\n");
				sb.append("	WHEN 11 THEN 0 WHEN 12 THEN 0 WHEN 13 THEN 0 WHEN 14 THEN 0 WHEN 15 THEN acc_id ELSE ag9 END AS ag9, 		\n");
				sb.append("	CASE acc_level_type WHEN 2 THEN 0 WHEN 3 THEN 0 WHEN 4 THEN 0 WHEN 5 THEN 0 		\n");
				sb.append("	WHEN 6 THEN 0 WHEN 7 THEN 0 WHEN 8 THEN 0 WHEN 9 THEN 0 WHEN 10 THEN 0 		\n");
				sb.append("	WHEN 11 THEN 0 WHEN 12 THEN 0 WHEN 13 THEN 0 WHEN 14 THEN 0 WHEN 15 THEN 0 		\n");
				sb.append("	WHEN 16 THEN acc_id ELSE ag10 END AS ag10 		\n");
				sb.append("	FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ? AND  acc_level_type>" + ACC_LEVEL_COM + " AND acc_level_type<"
						+ ACC_LEVEL_MEM + "	\n");
				selectList.add(upAccId);
				sb.append("	) A 		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bsc		\n");
				selectList.add(gameType);
				sb.append("	on Bsc.acc_id = A.sc		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bbc		\n");
				selectList.add(gameType);
				sb.append("	on Bbc.acc_id = A.bc		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bco		\n");
				selectList.add(gameType);
				sb.append("	on Bco.acc_id = A.co		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bsa		\n");
				selectList.add(gameType);
				sb.append("	on Bsa.acc_id = A.sa		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag		\n");
				selectList.add(gameType);
				sb.append("	on Bag.acc_id = A.ag		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag1		\n");
				selectList.add(gameType);
				sb.append("	on Bag1.acc_id = A.ag1		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag2		\n");
				selectList.add(gameType);
				sb.append("	on Bag2.acc_id = A.ag2		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag3		\n");
				selectList.add(gameType);
				sb.append("	on Bag3.acc_id = A.ag3		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag4		\n");
				selectList.add(gameType);
				sb.append("	on Bag4.acc_id = A.ag4		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag5		\n");
				selectList.add(gameType);
				sb.append("	on Bag5.acc_id = A.ag5		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag6		\n");
				selectList.add(gameType);
				sb.append("	on Bag6.acc_id = A.ag6		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag7		\n");
				selectList.add(gameType);
				sb.append("	on Bag7.acc_id = A.ag7		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag8		\n");
				selectList.add(gameType);
				sb.append("	on Bag8.acc_id = A.ag8		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag9		\n");
				selectList.add(gameType);
				sb.append("	on Bag9.acc_id = A.ag9		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag10		\n");
				selectList.add(gameType);
				sb.append("	on Bag10.acc_id = A.ag10			\n");

				listObj = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), selectList, new ManagerRatioBean());

				if (listObj.size() == 1) {

					ManagerRatioBean managerRatioBean = (ManagerRatioBean) listObj.get(0);

					LOG.debug(managerRatioBean.toString());

					int levelType = managerRatioBean.getUpperAccLevelType();
					if (levelType > ACC_LEVEL_COM) {
						managerRatio = new ArrayList<Map<String, Integer>>();

						if (levelType >= ACC_LEVEL_SC) {
							layer1 = new HashMap<String, Integer>();
							layer1.put("upperMin", managerRatioBean.getScGameUpperMin());
							layer1.put("upperMax", managerRatioBean.getScGameUpperMax());
							layer1.put("currentMax", managerRatioBean.getScGameCurrentMax());
							managerRatio.add(layer1);
						}
						if (levelType >= ACC_LEVEL_BC) {
							layer2 = new HashMap<String, Integer>();
							layer2.put("upperMin", managerRatioBean.getBcGameUpperMin());
							layer2.put("upperMax", managerRatioBean.getBcGameUpperMax());
							layer2.put("currentMax", managerRatioBean.getBcGameCurrentMax());
							managerRatio.add(layer2);
						}
						if (levelType >= ACC_LEVEL_CO) {
							layer3 = new HashMap<String, Integer>();
							layer3.put("upperMin", managerRatioBean.getCoGameUpperMin());
							layer3.put("upperMax", managerRatioBean.getCoGameUpperMax());
							layer3.put("currentMax", managerRatioBean.getCoGameCurrentMax());
							managerRatio.add(layer3);
						}
						if (levelType >= ACC_LEVEL_SA) {
							layer4 = new HashMap<String, Integer>();
							layer4.put("upperMin", managerRatioBean.getSaGameUpperMin());
							layer4.put("upperMax", managerRatioBean.getSaGameUpperMax());
							layer4.put("currentMax", managerRatioBean.getSaGameCurrentMax());
							managerRatio.add(layer4);
						}
						if (levelType >= ACC_LEVEL_AG) {
							layer5 = new HashMap<String, Integer>();
							layer5.put("upperMin", managerRatioBean.getAgGameUpperMin());
							layer5.put("upperMax", managerRatioBean.getAgGameUpperMax());
							layer5.put("currentMax", managerRatioBean.getAgGameCurrentMax());
							managerRatio.add(layer5);
						}
						if (levelType >= ACC_LEVEL_AG1) {
							layer6 = new HashMap<String, Integer>();
							layer6.put("upperMin", managerRatioBean.getAg1GameUpperMin());
							layer6.put("upperMax", managerRatioBean.getAg1GameUpperMax());
							layer6.put("currentMax", managerRatioBean.getAg1GameCurrentMax());
							managerRatio.add(layer6);
						}
						if (levelType >= ACC_LEVEL_AG2) {
							layer7 = new HashMap<String, Integer>();
							layer7.put("upperMin", managerRatioBean.getAg2GameUpperMin());
							layer7.put("upperMax", managerRatioBean.getAg2GameUpperMax());
							layer7.put("currentMax", managerRatioBean.getAg2GameCurrentMax());
							managerRatio.add(layer7);
						}
						if (levelType >= ACC_LEVEL_AG3) {
							layer8 = new HashMap<String, Integer>();
							layer8.put("upperMin", managerRatioBean.getAg3GameUpperMin());
							layer8.put("upperMax", managerRatioBean.getAg3GameUpperMax());
							layer8.put("currentMax", managerRatioBean.getAg3GameCurrentMax());
							managerRatio.add(layer8);
						}
						if (levelType >= ACC_LEVEL_AG4) {
							layer9 = new HashMap<String, Integer>();
							layer9.put("upperMin", managerRatioBean.getAg4GameUpperMin());
							layer9.put("upperMax", managerRatioBean.getAg4GameUpperMax());
							layer9.put("currentMax", managerRatioBean.getAg4GameCurrentMax());
							managerRatio.add(layer9);
						}
						if (levelType >= ACC_LEVEL_AG5) {
							layer10 = new HashMap<String, Integer>();
							layer10.put("upperMin", managerRatioBean.getAg5GameUpperMin());
							layer10.put("upperMax", managerRatioBean.getAg5GameUpperMax());
							layer10.put("currentMax", managerRatioBean.getAg5GameCurrentMax());
							managerRatio.add(layer10);
						}
						if (levelType >= ACC_LEVEL_AG6) {
							layer11 = new HashMap<String, Integer>();
							layer11.put("upperMin", managerRatioBean.getAg6GameUpperMin());
							layer11.put("upperMax", managerRatioBean.getAg6GameUpperMax());
							layer11.put("currentMax", managerRatioBean.getAg6GameCurrentMax());
							managerRatio.add(layer11);
						}
						if (levelType >= ACC_LEVEL_AG7) {
							layer12 = new HashMap<String, Integer>();
							layer12.put("upperMin", managerRatioBean.getAg7GameUpperMin());
							layer12.put("upperMax", managerRatioBean.getAg7GameUpperMax());
							layer12.put("currentMax", managerRatioBean.getAg7GameCurrentMax());
							managerRatio.add(layer12);
							// layer.clear();
						}
						if (levelType >= ACC_LEVEL_AG8) {
							layer13 = new HashMap<String, Integer>();
							layer13.put("upperMin", managerRatioBean.getAg8GameUpperMin());
							layer13.put("upperMax", managerRatioBean.getAg8GameUpperMax());
							layer13.put("currentMax", managerRatioBean.getAg8GameCurrentMax());
							managerRatio.add(layer13);
						}
						if (levelType >= ACC_LEVEL_AG9) {
							layer14 = new HashMap<String, Integer>();
							layer14.put("upperMin", managerRatioBean.getAg9GameUpperMin());
							layer14.put("upperMax", managerRatioBean.getAg9GameUpperMax());
							layer14.put("currentMax", managerRatioBean.getAg9GameCurrentMax());
							managerRatio.add(layer14);
							// layer.clear();
						}
						if (levelType >= ACC_LEVEL_AG10) {
							layer15 = new HashMap<String, Integer>();
							layer15.put("upperMin", managerRatioBean.getAg10GameUpperMin());
							layer15.put("upperMax", managerRatioBean.getAg10GameUpperMax());
							layer15.put("currentMax", managerRatioBean.getAg10GameCurrentMax());
							managerRatio.add(layer15);
						}
						layer16 = new HashMap<String, Integer>();
						layer16.put("upperMin", managerRatioBean.getMemGameUpperMin());
						layer16.put("upperMax", managerRatioBean.getMemGameUpperMin());
						managerRatio.add(layer16);

						listIntRatio = new ArrayList<Integer>();
						LOG.debug("managerRatio:" + managerRatio);

						listIntRatio = CalculateMemberRatio.getRatio(managerRatio);
						LOG.debug("listIntRatio:" + listIntRatio);

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
			if (selectList != null) {
				selectList.clear();
				selectList = null;
			}
			if (listObj != null) {
				listObj.clear();
				listObj = null;
			}
			if (layer1 != null) {
				layer1.clear();
				layer1 = null;
			}
			if (layer2 != null) {
				layer2.clear();
				layer2 = null;
			}
			if (layer3 != null) {
				layer3.clear();
				layer3 = null;
			}
			if (layer4 != null) {
				layer4.clear();
				layer4 = null;
			}
			if (layer5 != null) {
				layer5.clear();
				layer5 = null;
			}
			if (layer6 != null) {
				layer6.clear();
				layer6 = null;
			}
			if (layer7 != null) {
				layer7.clear();
				layer7 = null;
			}
			if (layer8 != null) {
				layer8.clear();
				layer8 = null;
			}
			if (layer9 != null) {
				layer9.clear();
				layer9 = null;
			}
			if (layer10 != null) {
				layer10.clear();
				layer10 = null;
			}
			if (layer11 != null) {
				layer11.clear();
				layer11 = null;
			}
			if (layer12 != null) {
				layer12.clear();
				layer12 = null;
			}
			if (layer13 != null) {
				layer13.clear();
				layer13 = null;
			}
			if (layer14 != null) {
				layer14.clear();
				layer14 = null;
			}
			if (layer15 != null) {
				layer15.clear();
				layer15 = null;
			}
			if (layer16 != null) {
				layer16.clear();
				layer16 = null;
			}

		}
		return listIntRatio;
	}

	@Override
	public List<Integer> selectGameRatioUpdateMem(long memberId, int gameType, int minRatio) {
		StringBuilder sb = null;
		List<Object> selectList = null;
		List<Object> listObj = null;
		List<Integer> listIntRatio = null;
		List<Map<String, Integer>> managerRatio = null;
		Map<String, Integer> layer1 = null;
		Map<String, Integer> layer2 = null;
		Map<String, Integer> layer3 = null;
		Map<String, Integer> layer4 = null;
		Map<String, Integer> layer5 = null;
		Map<String, Integer> layer6 = null;
		Map<String, Integer> layer7 = null;
		Map<String, Integer> layer8 = null;
		Map<String, Integer> layer9 = null;
		Map<String, Integer> layer10 = null;
		Map<String, Integer> layer11 = null;
		Map<String, Integer> layer12 = null;
		Map<String, Integer> layer13 = null;
		Map<String, Integer> layer14 = null;
		Map<String, Integer> layer15 = null;
		Map<String, Integer> layer16 = null;

		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listIntRatio;
			} else {

				sb = new StringBuilder();
				selectList = new ArrayList<Object>();
				listObj = new ArrayList<Object>();

				sb.append("	select     	\n");
				sb.append("	case when A.sc =0 then 0 else Bsc.next_upper_min_ratio end as sc_game_upper_min,	\n");
				sb.append("	case when A.sc =0 then 0 else Bsc.next_upper_max_ratio end as sc_game_upper_max,	\n");
				sb.append("	case when A.sc =0 then 0 else Bsc.next_current_max_ratio end as sc_game_current_max,	\n");
				sb.append("	case when A.bc =0 then 0 else Bbc.next_upper_min_ratio end as bc_game_upper_min,	\n");
				sb.append("	case when A.bc =0 then 0 else Bbc.next_upper_max_ratio end as bc_game_upper_max,	\n");
				sb.append("	case when A.bc =0 then 0 else Bbc.next_current_max_ratio end as bc_game_current_max,	\n");
				sb.append("	case when A.co =0 then 0 else Bco.next_upper_min_ratio end as co_game_upper_min,	\n");
				sb.append("	case when A.co =0 then 0 else Bco.next_upper_max_ratio end as co_game_upper_max,	\n");
				sb.append("	case when A.co =0 then 0 else Bco.next_current_max_ratio end as co_game_current_max,	\n");
				sb.append("	case when A.sa =0 then 0 else Bsa.next_upper_min_ratio end as sa_game_upper_min,	\n");
				sb.append("	case when A.sa =0 then 0 else Bsa.next_upper_max_ratio end as sa_game_upper_max,	\n");
				sb.append("	case when A.sa =0 then 0 else Bsa.next_current_max_ratio end as sa_game_current_max,	\n");
				sb.append("	case when A.ag =0 then 0 else Bag.next_upper_min_ratio end as ag_game_upper_min,	\n");
				sb.append("	case when A.ag =0 then 0 else Bag.next_upper_max_ratio end as ag_game_upper_max,	\n");
				sb.append("	case when A.ag =0 then 0 else Bag.next_current_max_ratio end as ag_game_current_max,	\n");
				sb.append("	case when A.ag1 =0 then 0 else Bag1.next_upper_min_ratio end as ag1_game_upper_min,	\n");
				sb.append("	case when A.ag1 =0 then 0 else Bag1.next_upper_max_ratio end as ag1_game_upper_max,	\n");
				sb.append("	case when A.ag1 =0 then 0 else Bag1.next_current_max_ratio end as ag1_game_current_max,	\n");
				sb.append("	case when A.ag2 =0 then 0 else Bag2.next_upper_min_ratio end as ag2_game_upper_min,	\n");
				sb.append("	case when A.ag2 =0 then 0 else Bag2.next_upper_max_ratio end as ag2_game_upper_max,	\n");
				sb.append("	case when A.ag2 =0 then 0 else Bag2.next_current_max_ratio end as ag2_game_current_max,	\n");
				sb.append("	case when A.ag3 =0 then 0 else Bag3.next_upper_min_ratio end as ag3_game_upper_min,	\n");
				sb.append("	case when A.ag3 =0 then 0 else Bag3.next_upper_max_ratio end as ag3_game_upper_max,	\n");
				sb.append("	case when A.ag3 =0 then 0 else Bag3.next_current_max_ratio end as ag3_game_current_max,	\n");
				sb.append("	case when A.ag4 =0 then 0 else Bag4.next_upper_min_ratio end as ag4_game_upper_min,	\n");
				sb.append("	case when A.ag4 =0 then 0 else Bag4.next_upper_max_ratio end as ag4_game_upper_max,	\n");
				sb.append("	case when A.ag4 =0 then 0 else Bag4.next_current_max_ratio end as ag4_game_current_max,	\n");
				sb.append("	case when A.ag5 =0 then 0 else Bag5.next_upper_min_ratio end as ag5_game_upper_min,	\n");
				sb.append("	case when A.ag5 =0 then 0 else Bag5.next_upper_max_ratio end as ag5_game_upper_max,	\n");
				sb.append("	case when A.ag5 =0 then 0 else Bag5.next_current_max_ratio end as ag5_game_current_max,	\n");
				sb.append("	case when A.ag6 =0 then 0 else Bag6.next_upper_min_ratio end as ag6_game_upper_min,	\n");
				sb.append("	case when A.ag6 =0 then 0 else Bag6.next_upper_max_ratio end as ag6_game_upper_max,	\n");
				sb.append("	case when A.ag6 =0 then 0 else Bag6.next_current_max_ratio end as ag6_game_current_max,	\n");
				sb.append("	case when A.ag7 =0 then 0 else Bag7.next_upper_min_ratio end as ag7_game_upper_min,	\n");
				sb.append("	case when A.ag7 =0 then 0 else Bag7.next_upper_max_ratio end as ag7_game_upper_max,	\n");
				sb.append("	case when A.ag7 =0 then 0 else Bag7.next_current_max_ratio end as ag7_game_current_max,	\n");
				sb.append("	case when A.ag8 =0 then 0 else Bag8.next_upper_min_ratio end as ag8_game_upper_min,	\n");
				sb.append("	case when A.ag8 =0 then 0 else Bag8.next_upper_max_ratio end as ag8_game_upper_max,	\n");
				sb.append("	case when A.ag8 =0 then 0 else Bag8.next_current_max_ratio end as ag8_game_current_max,	\n");
				sb.append("	case when A.ag9 =0 then 0 else Bag9.next_upper_min_ratio end as ag9_game_upper_min,	\n");
				sb.append("	case when A.ag9 =0 then 0 else Bag9.next_upper_max_ratio end as ag9_game_upper_max,	\n");
				sb.append("	case when A.ag9 =0 then 0 else Bag9.next_current_max_ratio end as ag9_game_current_max,	\n");
				sb.append("	case when A.ag10 =0 then 0 else Bag10.next_upper_min_ratio end as ag10_game_upper_min,	\n");
				sb.append("	case when A.ag10 =0 then 0 else Bag10.next_upper_max_ratio end as ag10_game_upper_max,	\n");
				sb.append("	case when A.ag10 =0 then 0 else Bag10.next_current_max_ratio end as ag10_game_current_max,	\n");
				sb.append("	? as mem_game_upper_min,	\n");
				selectList.add(minRatio);
				sb.append("	A.upper_acc_level_type as upperAccLevelType 	\n");
				sb.append("	from 	\n");
				sb.append("	(select * from `ctt_manager`.`ctt_member_acc_level` where acc_id= ?) A 	\n");
				selectList.add(memberId);
				sb.append("	left join	\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bsc	\n");
				selectList.add(gameType);
				sb.append("	on Bsc.acc_id = A.sc	\n");
				sb.append("	left join	\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bbc	\n");
				selectList.add(gameType);
				sb.append("	on Bbc.acc_id = A.bc	\n");
				sb.append("	left join	\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bco	\n");
				selectList.add(gameType);
				sb.append("	on Bco.acc_id = A.co	\n");
				sb.append("	left join	\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bsa	\n");
				selectList.add(gameType);
				sb.append("	on Bsa.acc_id = A.sa	\n");
				sb.append("	left join	\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag	\n");
				selectList.add(gameType);
				sb.append("	on Bag.acc_id = A.ag	\n");
				sb.append("	left join	\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag1	\n");
				selectList.add(gameType);
				sb.append("	on Bag1.acc_id = A.ag1	\n");
				sb.append("	left join	\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag2	\n");
				selectList.add(gameType);
				sb.append("	on Bag2.acc_id = A.ag2	\n");
				sb.append("	left join	\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag3	\n");
				selectList.add(gameType);
				sb.append("	on Bag3.acc_id = A.ag3	\n");
				sb.append("	left join	\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag4	\n");
				selectList.add(gameType);
				sb.append("	on Bag4.acc_id = A.ag4	\n");
				sb.append("	left join	\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag5	\n");
				selectList.add(gameType);
				sb.append("	on Bag5.acc_id = A.ag5	\n");
				sb.append("	left join	\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag6	\n");
				selectList.add(gameType);
				sb.append("	on Bag6.acc_id = A.ag6	\n");
				sb.append("	left join	\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag7	\n");
				selectList.add(gameType);
				sb.append("	on Bag7.acc_id = A.ag7	\n");
				sb.append("	left join	\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag8	\n");
				selectList.add(gameType);
				sb.append("	on Bag8.acc_id = A.ag8	\n");
				sb.append("	left join	\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag9	\n");
				selectList.add(gameType);
				sb.append("	on Bag9.acc_id = A.ag9	\n");
				sb.append("	left join	\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag10	\n");
				selectList.add(gameType);
				sb.append("	on Bag10.acc_id = A.ag10	\n");
				sb.append("	group by A.acc_id 	\n");

				listObj = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), selectList, new ManagerRatioBean());

				if (listObj.size() == 1) {

					ManagerRatioBean managerRatioBean = (ManagerRatioBean) listObj.get(0);

					int levelType = managerRatioBean.getUpperAccLevelType();
					if (levelType > ACC_LEVEL_COM) {
						managerRatio = new ArrayList<Map<String, Integer>>();

						if (levelType >= ACC_LEVEL_SC) {
							layer1 = new HashMap<String, Integer>();
							layer1.put("upperMin", managerRatioBean.getScGameUpperMin());
							layer1.put("upperMax", managerRatioBean.getScGameUpperMax());
							layer1.put("currentMax", managerRatioBean.getScGameCurrentMax());
							managerRatio.add(layer1);
						}
						if (levelType >= ACC_LEVEL_BC) {
							layer2 = new HashMap<String, Integer>();
							layer2.put("upperMin", managerRatioBean.getBcGameUpperMin());
							layer2.put("upperMax", managerRatioBean.getBcGameUpperMax());
							layer2.put("currentMax", managerRatioBean.getBcGameCurrentMax());
							managerRatio.add(layer2);
						}
						if (levelType >= ACC_LEVEL_CO) {
							layer3 = new HashMap<String, Integer>();
							layer3.put("upperMin", managerRatioBean.getCoGameUpperMin());
							layer3.put("upperMax", managerRatioBean.getCoGameUpperMax());
							layer3.put("currentMax", managerRatioBean.getCoGameCurrentMax());
							managerRatio.add(layer3);
						}
						if (levelType >= ACC_LEVEL_SA) {
							layer4 = new HashMap<String, Integer>();
							layer4.put("upperMin", managerRatioBean.getSaGameUpperMin());
							layer4.put("upperMax", managerRatioBean.getSaGameUpperMax());
							layer4.put("currentMax", managerRatioBean.getSaGameCurrentMax());
							managerRatio.add(layer4);
						}
						if (levelType >= ACC_LEVEL_AG) {
							layer5 = new HashMap<String, Integer>();
							layer5.put("upperMin", managerRatioBean.getAgGameUpperMin());
							layer5.put("upperMax", managerRatioBean.getAgGameUpperMax());
							layer5.put("currentMax", managerRatioBean.getAgGameCurrentMax());
							managerRatio.add(layer5);
						}
						if (levelType >= ACC_LEVEL_AG1) {
							layer6 = new HashMap<String, Integer>();
							layer6.put("upperMin", managerRatioBean.getAg1GameUpperMin());
							layer6.put("upperMax", managerRatioBean.getAg1GameUpperMax());
							layer6.put("currentMax", managerRatioBean.getAg1GameCurrentMax());
							managerRatio.add(layer6);
						}
						if (levelType >= ACC_LEVEL_AG2) {
							layer7 = new HashMap<String, Integer>();
							layer7.put("upperMin", managerRatioBean.getAg2GameUpperMin());
							layer7.put("upperMax", managerRatioBean.getAg2GameUpperMax());
							layer7.put("currentMax", managerRatioBean.getAg2GameCurrentMax());
							managerRatio.add(layer7);
						}
						if (levelType >= ACC_LEVEL_AG3) {
							layer8 = new HashMap<String, Integer>();
							layer8.put("upperMin", managerRatioBean.getAg3GameUpperMin());
							layer8.put("upperMax", managerRatioBean.getAg3GameUpperMax());
							layer8.put("currentMax", managerRatioBean.getAg3GameCurrentMax());
							managerRatio.add(layer8);
						}
						if (levelType >= ACC_LEVEL_AG4) {
							layer9 = new HashMap<String, Integer>();
							layer9.put("upperMin", managerRatioBean.getAg4GameUpperMin());
							layer9.put("upperMax", managerRatioBean.getAg4GameUpperMax());
							layer9.put("currentMax", managerRatioBean.getAg4GameCurrentMax());
							managerRatio.add(layer9);
						}
						if (levelType >= ACC_LEVEL_AG5) {
							layer10 = new HashMap<String, Integer>();
							layer10.put("upperMin", managerRatioBean.getAg5GameUpperMin());
							layer10.put("upperMax", managerRatioBean.getAg5GameUpperMax());
							layer10.put("currentMax", managerRatioBean.getAg5GameCurrentMax());
							managerRatio.add(layer10);
						}
						if (levelType >= ACC_LEVEL_AG6) {
							layer11 = new HashMap<String, Integer>();
							layer11.put("upperMin", managerRatioBean.getAg6GameUpperMin());
							layer11.put("upperMax", managerRatioBean.getAg6GameUpperMax());
							layer11.put("currentMax", managerRatioBean.getAg6GameCurrentMax());
							managerRatio.add(layer11);
						}
						if (levelType >= ACC_LEVEL_AG7) {
							layer12 = new HashMap<String, Integer>();
							layer12.put("upperMin", managerRatioBean.getAg7GameUpperMin());
							layer12.put("upperMax", managerRatioBean.getAg7GameUpperMax());
							layer12.put("currentMax", managerRatioBean.getAg7GameCurrentMax());
							managerRatio.add(layer12);
							// layer.clear();
						}
						if (levelType >= ACC_LEVEL_AG8) {
							layer13 = new HashMap<String, Integer>();
							layer13.put("upperMin", managerRatioBean.getAg8GameUpperMin());
							layer13.put("upperMax", managerRatioBean.getAg8GameUpperMax());
							layer13.put("currentMax", managerRatioBean.getAg8GameCurrentMax());
							managerRatio.add(layer13);
						}
						if (levelType >= ACC_LEVEL_AG9) {
							layer14 = new HashMap<String, Integer>();
							layer14.put("upperMin", managerRatioBean.getAg9GameUpperMin());
							layer14.put("upperMax", managerRatioBean.getAg9GameUpperMax());
							layer14.put("currentMax", managerRatioBean.getAg9GameCurrentMax());
							managerRatio.add(layer14);
							// layer.clear();
						}
						if (levelType >= ACC_LEVEL_AG10) {
							layer15 = new HashMap<String, Integer>();
							layer15.put("upperMin", managerRatioBean.getAg10GameUpperMin());
							layer15.put("upperMax", managerRatioBean.getAg10GameUpperMax());
							layer15.put("currentMax", managerRatioBean.getAg10GameCurrentMax());
							managerRatio.add(layer15);
						}
						layer16 = new HashMap<String, Integer>();
						layer16.put("upperMin", managerRatioBean.getMemGameUpperMin());
						layer16.put("upperMax", managerRatioBean.getMemGameUpperMin());
						managerRatio.add(layer16);

						listIntRatio = new ArrayList<Integer>();

						LOG.debug("managerRatio:" + managerRatio);

						listIntRatio = CalculateMemberRatio.getRatio(managerRatio);

						LOG.debug("listIntRatio:" + listIntRatio);

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
			if (selectList != null) {
				selectList.clear();
				selectList = null;
			}
			if (listObj != null) {
				listObj.clear();
				listObj = null;
			}
			if (layer1 != null) {
				layer1.clear();
				layer1 = null;
			}
			if (layer2 != null) {
				layer2.clear();
				layer2 = null;
			}
			if (layer3 != null) {
				layer3.clear();
				layer3 = null;
			}
			if (layer4 != null) {
				layer4.clear();
				layer4 = null;
			}
			if (layer5 != null) {
				layer5.clear();
				layer5 = null;
			}
			if (layer6 != null) {
				layer6.clear();
				layer6 = null;
			}
			if (layer7 != null) {
				layer7.clear();
				layer7 = null;
			}
			if (layer8 != null) {
				layer8.clear();
				layer8 = null;
			}
			if (layer9 != null) {
				layer9.clear();
				layer9 = null;
			}
			if (layer10 != null) {
				layer10.clear();
				layer10 = null;
			}
			if (layer11 != null) {
				layer11.clear();
				layer11 = null;
			}
			if (layer12 != null) {
				layer12.clear();
				layer12 = null;
			}
			if (layer13 != null) {
				layer13.clear();
				layer13 = null;
			}
			if (layer14 != null) {
				layer14.clear();
				layer14 = null;
			}
			if (layer15 != null) {
				layer15.clear();
				layer15 = null;
			}
			if (layer16 != null) {
				layer16.clear();
				layer16 = null;
			}

		}
		return listIntRatio;
	}

	@Override
	public List<Integer> selectGameRatio(long memberId, int gameType, long upAccId, int minRatio, int maxRatio, int currentRatio,
			boolean isFullRatio) {
		StringBuilder sb = null;
		List<Object> selectList = null;
		List<Object> listObj = null;
		List<Integer> listIntRatio = null;
		List<Map<String, Integer>> managerRatio = null;
		Map<String, Integer> layer1 = null;
		Map<String, Integer> layer2 = null;
		Map<String, Integer> layer3 = null;
		Map<String, Integer> layer4 = null;
		Map<String, Integer> layer5 = null;
		Map<String, Integer> layer6 = null;
		Map<String, Integer> layer7 = null;
		Map<String, Integer> layer8 = null;
		Map<String, Integer> layer9 = null;
		Map<String, Integer> layer10 = null;
		Map<String, Integer> layer11 = null;
		Map<String, Integer> layer12 = null;
		Map<String, Integer> layer13 = null;
		Map<String, Integer> layer14 = null;
		Map<String, Integer> layer15 = null;
		Map<String, Integer> layer16 = null;

		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listIntRatio;
			} else {

				sb = new StringBuilder();
				selectList = new ArrayList<Object>();
				listObj = new ArrayList<Object>();

				sb.append("	select     		\n");
				sb.append(
						"	case when A.sc =0 then 0 else (CASE WHEN sc = ? THEN ? else Bsc.next_upper_min_ratio end) end as sc_game_upper_min,		\n");
				selectList.add(upAccId);
				selectList.add(minRatio);
				sb.append(
						"	case when A.sc =0 then 0 else (CASE WHEN sc = ? THEN ? else Bsc.next_upper_max_ratio end) end as sc_game_upper_max,		\n");
				selectList.add(upAccId);
				selectList.add(maxRatio);
				sb.append(
						"	case when A.sc =0 then 0 else (CASE WHEN sc = ? THEN ? else Bsc.next_current_max_ratio end) end as sc_game_current_max,		\n");
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.bc =0 then 0 else (CASE WHEN bc = ? THEN ? else Bbc.next_upper_min_ratio end) end as bc_game_upper_min,		\n");
				selectList.add(upAccId);
				selectList.add(minRatio);
				sb.append("	case when A.bc =0 then 0 else (CASE WHEN bc = ? THEN ? else (CASE WHEN sc = ? and " + isFullRatio
						+ " THEN ? ELSE Bbc.next_upper_max_ratio end) end) end as bc_game_upper_max,		\n");
				selectList.add(upAccId);
				selectList.add(maxRatio);
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.bc =0 then 0 else (CASE WHEN bc = ? THEN ? else Bbc.next_current_max_ratio end) end as bc_game_current_max,		\n");
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.co =0 then 0 else (CASE WHEN co = ? THEN ? else Bco.next_upper_min_ratio end) end as co_game_upper_min,		\n");
				selectList.add(upAccId);
				selectList.add(minRatio);
				sb.append("	case when A.co =0 then 0 else (CASE WHEN co = ? THEN ? else (CASE WHEN bc = ? and " + isFullRatio
						+ " THEN ? ELSE Bco.next_upper_max_ratio end) end) end as co_game_upper_max,		\n");
				selectList.add(upAccId);
				selectList.add(maxRatio);
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.co =0 then 0 else (CASE WHEN co = ? THEN ? else Bco.next_current_max_ratio end) end as co_game_current_max,		\n");
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.sa =0 then 0 else (CASE WHEN sa = ? THEN ? else Bsa.next_upper_min_ratio end) end as sa_game_upper_min,		\n");
				selectList.add(upAccId);
				selectList.add(minRatio);
				sb.append("	case when A.sa =0 then 0 else (CASE WHEN sa = ? THEN ? else (CASE WHEN co = ? and " + isFullRatio
						+ " THEN ? ELSE Bsa.next_upper_max_ratio end) end) end as sa_game_upper_max,		\n");
				selectList.add(upAccId);
				selectList.add(maxRatio);
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.sa =0 then 0 else (CASE WHEN sa = ? THEN ? else Bsa.next_current_max_ratio end) end as sa_game_current_max,		\n");
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag =0 then 0 else (CASE WHEN ag = ? THEN ? else Bag.next_upper_min_ratio end) end as ag_game_upper_min,		\n");
				selectList.add(upAccId);
				selectList.add(minRatio);
				sb.append("	case when A.ag =0 then 0 else (CASE WHEN ag = ? THEN ? else (CASE WHEN sa = ? and " + isFullRatio
						+ " THEN ? ELSE Bag.next_upper_max_ratio end) end) end as ag_game_upper_max,		\n");
				selectList.add(upAccId);
				selectList.add(maxRatio);
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag =0 then 0 else (CASE WHEN ag = ? THEN ? else Bag.next_current_max_ratio end) end as ag_game_current_max,		\n");
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag1 =0 then 0 else (CASE WHEN ag1 = ? THEN ? else Bag1.next_upper_min_ratio end) end as ag1_game_upper_min,		\n");
				selectList.add(upAccId);
				selectList.add(minRatio);
				sb.append("	case when A.ag1 =0 then 0 else (CASE WHEN ag1 = ? THEN ? else (CASE WHEN ag = ? and " + isFullRatio
						+ " THEN ? ELSE Bag1.next_upper_max_ratio end) end) end as ag1_game_upper_max,		\n");
				selectList.add(upAccId);
				selectList.add(maxRatio);
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag1 =0 then 0 else (CASE WHEN ag1 = ? THEN ? else Bag1.next_current_max_ratio end) end as ag1_game_current_max,		\n");
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag2 =0 then 0 else (CASE WHEN ag2 = ? THEN ? else Bag2.next_upper_min_ratio end) end as ag2_game_upper_min,		\n");
				selectList.add(upAccId);
				selectList.add(minRatio);
				sb.append("	case when A.ag2 =0 then 0 else (CASE WHEN ag2 = ? THEN ? else (CASE WHEN ag1 = ? and " + isFullRatio
						+ " THEN ? ELSE Bag2.next_upper_max_ratio end) end) end as ag2_game_upper_max,		\n");
				selectList.add(upAccId);
				selectList.add(maxRatio);
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag2 =0 then 0 else (CASE WHEN ag2 = ? THEN ? else Bag2.next_current_max_ratio end) end as ag2_game_current_max,		\n");
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag3 =0 then 0 else (CASE WHEN ag3 = ? THEN ? else Bag3.next_upper_min_ratio end) end as ag3_game_upper_min,		\n");
				selectList.add(upAccId);
				selectList.add(minRatio);
				sb.append("	case when A.ag3 =0 then 0 else (CASE WHEN ag3 = ? THEN ? else (CASE WHEN ag2 = ? and " + isFullRatio
						+ " THEN ? ELSE Bag3.next_upper_max_ratio end) end) end as ag3_game_upper_max,		\n");
				selectList.add(upAccId);
				selectList.add(maxRatio);
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag3 =0 then 0 else (CASE WHEN ag3 = ? THEN ? else Bag3.next_current_max_ratio end) end as ag3_game_current_max,		\n");
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag4 =0 then 0 else (CASE WHEN ag4 = ? THEN ? else Bag4.next_upper_min_ratio end) end as ag4_game_upper_min,		\n");
				selectList.add(upAccId);
				selectList.add(minRatio);
				sb.append("	case when A.ag4 =0 then 0 else (CASE WHEN ag4 = ? THEN ? else (CASE WHEN ag3 = ? and " + isFullRatio
						+ " THEN ? ELSE Bag4.next_upper_max_ratio end) end) end as ag4_game_upper_max,		\n");
				selectList.add(upAccId);
				selectList.add(maxRatio);
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag4 =0 then 0 else (CASE WHEN ag4 = ? THEN ? else Bag4.next_current_max_ratio end) end as ag4_game_current_max,		\n");
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag5 =0 then 0 else (CASE WHEN ag5 = ? THEN ? else Bag5.next_upper_min_ratio end) end as ag5_game_upper_min,		\n");
				selectList.add(upAccId);
				selectList.add(minRatio);
				sb.append("	case when A.ag5 =0 then 0 else (CASE WHEN ag5 = ? THEN ? else (CASE WHEN ag4 = ? and " + isFullRatio
						+ " THEN ? ELSE Bag5.next_upper_max_ratio end) end) end as ag5_game_upper_max,		\n");
				selectList.add(upAccId);
				selectList.add(maxRatio);
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag5 =0 then 0 else (CASE WHEN ag5 = ? THEN ? else Bag5.next_current_max_ratio end) end as ag5_game_current_max,		\n");
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag6 =0 then 0 else (CASE WHEN ag6 = ? THEN ? else Bag6.next_upper_min_ratio end) end as ag6_game_upper_min,		\n");
				selectList.add(upAccId);
				selectList.add(minRatio);
				sb.append("	case when A.ag6 =0 then 0 else (CASE WHEN ag6 = ? THEN ? else (CASE WHEN ag5 = ? and " + isFullRatio
						+ " THEN ? ELSE Bag6.next_upper_max_ratio end) end) end as ag6_game_upper_max,		\n");
				selectList.add(upAccId);
				selectList.add(maxRatio);
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag6 =0 then 0 else (CASE WHEN ag6 = ? THEN ? else Bag6.next_current_max_ratio end) end as ag6_game_current_max,		\n");
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag7 =0 then 0 else (CASE WHEN ag7 = ? THEN ? else Bag7.next_upper_min_ratio end) end as ag7_game_upper_min,		\n");
				selectList.add(upAccId);
				selectList.add(minRatio);
				sb.append("	case when A.ag7 =0 then 0 else (CASE WHEN ag7 = ? THEN ? else (CASE WHEN ag6 = ? and " + isFullRatio
						+ " THEN ? ELSE Bag7.next_upper_max_ratio end) end) end as ag7_game_upper_max,		\n");
				selectList.add(upAccId);
				selectList.add(maxRatio);
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag7 =0 then 0 else (CASE WHEN ag7 = ? THEN ? else Bag7.next_current_max_ratio end) end as ag7_game_current_max,		\n");
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag8 =0 then 0 else (CASE WHEN ag8 = ? THEN ? else Bag8.next_upper_min_ratio end) end as ag8_game_upper_min,		\n");
				selectList.add(upAccId);
				selectList.add(minRatio);
				sb.append("	case when A.ag8 =0 then 0 else (CASE WHEN ag8 = ? THEN ? else (CASE WHEN ag7 = ? and " + isFullRatio
						+ " THEN ? ELSE Bag8.next_upper_max_ratio end) end) end as ag8_game_upper_max,		\n");
				selectList.add(upAccId);
				selectList.add(maxRatio);
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag8 =0 then 0 else (CASE WHEN ag8 = ? THEN ? else Bag8.next_current_max_ratio end) end as ag8_game_current_max,		\n");
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag9 =0 then 0 else (CASE WHEN ag9 = ? THEN ? else Bag9.next_upper_min_ratio end) end as ag9_game_upper_min,		\n");
				selectList.add(upAccId);
				selectList.add(minRatio);
				sb.append("	case when A.ag9 =0 then 0 else (CASE WHEN ag9 = ? THEN ? else (CASE WHEN ag8 = ? and " + isFullRatio
						+ " THEN ? ELSE Bag9.next_upper_max_ratio end) end) end as ag9_game_upper_max,		\n");
				selectList.add(upAccId);
				selectList.add(maxRatio);
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag9 =0 then 0 else (CASE WHEN ag9 = ? THEN ? else Bag9.next_current_max_ratio end) end as ag9_game_current_max,		\n");
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag10 =0 then 0 else (CASE WHEN ag10 = ? THEN ? else Bag10.next_upper_min_ratio end) end as ag10_game_upper_min,		\n");
				selectList.add(upAccId);
				selectList.add(minRatio);
				sb.append("	case when A.ag10 =0 then 0 else (CASE WHEN ag10 = ? THEN ? else (CASE WHEN ag9 = ? and " + isFullRatio
						+ " THEN ? ELSE Bag10.next_upper_max_ratio end) end) end as ag10_game_upper_max,		\n");
				selectList.add(upAccId);
				selectList.add(maxRatio);
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append(
						"	case when A.ag10 =0 then 0 else (CASE WHEN ag10 = ? THEN ? else Bag10.next_current_max_ratio end) end as ag10_game_current_max,		\n");
				selectList.add(upAccId);
				selectList.add(currentRatio);
				sb.append("	Bmem.next_upper_min_ratio as mem_game_upper_min,		\n");
				sb.append("	A.upper_acc_level_type as upperAccLevelType 		\n");
				sb.append("	from 		\n");
				sb.append("	(select * from `ctt_manager`.`ctt_member_acc_level` where acc_id= ?) A 		\n");
				selectList.add(memberId);
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bsc		\n");
				selectList.add(gameType);
				sb.append("	on Bsc.acc_id = A.sc		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bbc		\n");
				selectList.add(gameType);
				sb.append("	on Bbc.acc_id = A.bc		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bco		\n");
				selectList.add(gameType);
				sb.append("	on Bco.acc_id = A.co		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bsa		\n");
				selectList.add(gameType);
				sb.append("	on Bsa.acc_id = A.sa		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag		\n");
				selectList.add(gameType);
				sb.append("	on Bag.acc_id = A.ag		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag1		\n");
				selectList.add(gameType);
				sb.append("	on Bag1.acc_id = A.ag1		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag2		\n");
				selectList.add(gameType);
				sb.append("	on Bag2.acc_id = A.ag2		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag3		\n");
				selectList.add(gameType);
				sb.append("	on Bag3.acc_id = A.ag3		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag4		\n");
				selectList.add(gameType);
				sb.append("	on Bag4.acc_id = A.ag4		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag5		\n");
				selectList.add(gameType);
				sb.append("	on Bag5.acc_id = A.ag5		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag6		\n");
				selectList.add(gameType);
				sb.append("	on Bag6.acc_id = A.ag6		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag7		\n");
				selectList.add(gameType);
				sb.append("	on Bag7.acc_id = A.ag7		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag8		\n");
				selectList.add(gameType);
				sb.append("	on Bag8.acc_id = A.ag8		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag9		\n");
				selectList.add(gameType);
				sb.append("	on Bag9.acc_id = A.ag9		\n");
				sb.append("	left join		\n");
				sb.append(
						"	(select acc_id,next_upper_min_ratio,next_upper_max_ratio,next_current_max_ratio from `ctt_manager`.`ctt_manager_ratio` where game_type=?) Bag10		\n");
				selectList.add(gameType);
				sb.append("	on Bag10.acc_id = A.ag10		\n");
				sb.append("	inner join		\n");
				sb.append("	(select acc_id,next_upper_min_ratio from `ctt_manager`.`ctt_member_ratio` where game_type=?) Bmem		\n");
				selectList.add(gameType);
				sb.append("	on Bmem.acc_id = A.acc_id		\n");
				sb.append("	group by A.acc_id 	\n");

				listObj = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), selectList, new ManagerRatioBean());

				if (listObj.size() == 1) {

					ManagerRatioBean managerRatioBean = (ManagerRatioBean) listObj.get(0);

					int levelType = managerRatioBean.getUpperAccLevelType();
					if (levelType > ACC_LEVEL_COM) {
						managerRatio = new ArrayList<Map<String, Integer>>();

						if (levelType >= ACC_LEVEL_SC) {
							layer1 = new HashMap<String, Integer>();
							layer1.put("upperMin", managerRatioBean.getScGameUpperMin());
							layer1.put("upperMax", managerRatioBean.getScGameUpperMax());
							layer1.put("currentMax", managerRatioBean.getScGameCurrentMax());
							managerRatio.add(layer1);
						}
						if (levelType >= ACC_LEVEL_BC) {
							layer2 = new HashMap<String, Integer>();
							layer2.put("upperMin", managerRatioBean.getBcGameUpperMin());
							layer2.put("upperMax", managerRatioBean.getBcGameUpperMax());
							layer2.put("currentMax", managerRatioBean.getBcGameCurrentMax());
							managerRatio.add(layer2);
						}
						if (levelType >= ACC_LEVEL_CO) {
							layer3 = new HashMap<String, Integer>();
							layer3.put("upperMin", managerRatioBean.getCoGameUpperMin());
							layer3.put("upperMax", managerRatioBean.getCoGameUpperMax());
							layer3.put("currentMax", managerRatioBean.getCoGameCurrentMax());
							managerRatio.add(layer3);
						}
						if (levelType >= ACC_LEVEL_SA) {
							layer4 = new HashMap<String, Integer>();
							layer4.put("upperMin", managerRatioBean.getSaGameUpperMin());
							layer4.put("upperMax", managerRatioBean.getSaGameUpperMax());
							layer4.put("currentMax", managerRatioBean.getSaGameCurrentMax());
							managerRatio.add(layer4);
						}
						if (levelType >= ACC_LEVEL_AG) {
							layer5 = new HashMap<String, Integer>();
							layer5.put("upperMin", managerRatioBean.getAgGameUpperMin());
							layer5.put("upperMax", managerRatioBean.getAgGameUpperMax());
							layer5.put("currentMax", managerRatioBean.getAgGameCurrentMax());
							managerRatio.add(layer5);
						}
						if (levelType >= ACC_LEVEL_AG1) {
							layer6 = new HashMap<String, Integer>();
							layer6.put("upperMin", managerRatioBean.getAg1GameUpperMin());
							layer6.put("upperMax", managerRatioBean.getAg1GameUpperMax());
							layer6.put("currentMax", managerRatioBean.getAg1GameCurrentMax());
							managerRatio.add(layer6);
						}
						if (levelType >= ACC_LEVEL_AG2) {
							layer7 = new HashMap<String, Integer>();
							layer7.put("upperMin", managerRatioBean.getAg2GameUpperMin());
							layer7.put("upperMax", managerRatioBean.getAg2GameUpperMax());
							layer7.put("currentMax", managerRatioBean.getAg2GameCurrentMax());
							managerRatio.add(layer7);
						}
						if (levelType >= ACC_LEVEL_AG3) {
							layer8 = new HashMap<String, Integer>();
							layer8.put("upperMin", managerRatioBean.getAg3GameUpperMin());
							layer8.put("upperMax", managerRatioBean.getAg3GameUpperMax());
							layer8.put("currentMax", managerRatioBean.getAg3GameCurrentMax());
							managerRatio.add(layer8);
						}
						if (levelType >= ACC_LEVEL_AG4) {
							layer9 = new HashMap<String, Integer>();
							layer9.put("upperMin", managerRatioBean.getAg4GameUpperMin());
							layer9.put("upperMax", managerRatioBean.getAg4GameUpperMax());
							layer9.put("currentMax", managerRatioBean.getAg4GameCurrentMax());
							managerRatio.add(layer9);
						}
						if (levelType >= ACC_LEVEL_AG5) {
							layer10 = new HashMap<String, Integer>();
							layer10.put("upperMin", managerRatioBean.getAg5GameUpperMin());
							layer10.put("upperMax", managerRatioBean.getAg5GameUpperMax());
							layer10.put("currentMax", managerRatioBean.getAg5GameCurrentMax());
							managerRatio.add(layer10);
						}
						if (levelType >= ACC_LEVEL_AG6) {
							layer11 = new HashMap<String, Integer>();
							layer11.put("upperMin", managerRatioBean.getAg6GameUpperMin());
							layer11.put("upperMax", managerRatioBean.getAg6GameUpperMax());
							layer11.put("currentMax", managerRatioBean.getAg6GameCurrentMax());
							managerRatio.add(layer11);
						}
						if (levelType >= ACC_LEVEL_AG7) {
							layer12 = new HashMap<String, Integer>();
							layer12.put("upperMin", managerRatioBean.getAg7GameUpperMin());
							layer12.put("upperMax", managerRatioBean.getAg7GameUpperMax());
							layer12.put("currentMax", managerRatioBean.getAg7GameCurrentMax());
							managerRatio.add(layer12);
							// layer.clear();
						}
						if (levelType >= ACC_LEVEL_AG8) {
							layer13 = new HashMap<String, Integer>();
							layer13.put("upperMin", managerRatioBean.getAg8GameUpperMin());
							layer13.put("upperMax", managerRatioBean.getAg8GameUpperMax());
							layer13.put("currentMax", managerRatioBean.getAg8GameCurrentMax());
							managerRatio.add(layer13);
						}
						if (levelType >= ACC_LEVEL_AG9) {
							layer14 = new HashMap<String, Integer>();
							layer14.put("upperMin", managerRatioBean.getAg9GameUpperMin());
							layer14.put("upperMax", managerRatioBean.getAg9GameUpperMax());
							layer14.put("currentMax", managerRatioBean.getAg9GameCurrentMax());
							managerRatio.add(layer14);
							// layer.clear();
						}
						if (levelType >= ACC_LEVEL_AG10) {
							layer15 = new HashMap<String, Integer>();
							layer15.put("upperMin", managerRatioBean.getAg10GameUpperMin());
							layer15.put("upperMax", managerRatioBean.getAg10GameUpperMax());
							layer15.put("currentMax", managerRatioBean.getAg10GameCurrentMax());
							managerRatio.add(layer15);
						}
						layer16 = new HashMap<String, Integer>();
						layer16.put("upperMin", managerRatioBean.getMemGameUpperMin());
						layer16.put("upperMax", managerRatioBean.getMemGameUpperMin());
						managerRatio.add(layer16);

						listIntRatio = new ArrayList<Integer>();
						LOG.debug("managerRatio:" + managerRatio);

						listIntRatio = CalculateMemberRatio.getRatio(managerRatio);
						LOG.debug("listIntRatio:" + listIntRatio);

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
			if (selectList != null) {
				selectList.clear();
				selectList = null;
			}
			if (listObj != null) {
				listObj.clear();
				listObj = null;
			}
			if (layer1 != null) {
				layer1.clear();
				layer1 = null;
			}
			if (layer2 != null) {
				layer2.clear();
				layer2 = null;
			}
			if (layer3 != null) {
				layer3.clear();
				layer3 = null;
			}
			if (layer4 != null) {
				layer4.clear();
				layer4 = null;
			}
			if (layer5 != null) {
				layer5.clear();
				layer5 = null;
			}
			if (layer6 != null) {
				layer6.clear();
				layer6 = null;
			}
			if (layer7 != null) {
				layer7.clear();
				layer7 = null;
			}
			if (layer8 != null) {
				layer8.clear();
				layer8 = null;
			}
			if (layer9 != null) {
				layer9.clear();
				layer9 = null;
			}
			if (layer10 != null) {
				layer10.clear();
				layer10 = null;
			}
			if (layer11 != null) {
				layer11.clear();
				layer11 = null;
			}
			if (layer12 != null) {
				layer12.clear();
				layer12 = null;
			}
			if (layer13 != null) {
				layer13.clear();
				layer13 = null;
			}
			if (layer14 != null) {
				layer14.clear();
				layer14 = null;
			}
			if (layer15 != null) {
				layer15.clear();
				layer15 = null;
			}
			if (layer16 != null) {
				layer16.clear();
				layer16 = null;
			}

		}
		return listIntRatio;
	}

	// 修改下層管理員 的 最大佔成數
	@Override
	public boolean updateLowerLevelMagRatio(long accId, int currentRatio, int gameType) {
		boolean todo = false;
		List<Object> updateList = null;
		StringBuilder sb = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("updateLowerLevelMagRatio:NO WRITE_CONN");
				return todo;
			} else {
				sb = new StringBuilder();
				updateList = new ArrayList<Object>();

				sb.append("	update ctt_manager.ctt_manager_ratio set next_upper_max_ratio = ? where game_type = ? 	\n");
				updateList.add(currentRatio);
				updateList.add(gameType);
				sb.append("	and acc_id IN(	\n");
				sb.append("	select acc_id from ctt_manager.ctt_manager_acc_level where	\n");
				sb.append("	CASE (select acc_level_type from ctt_manager.ctt_manager_acc_level where acc_id = ?)	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_SC + " THEN  sc = ? and acc_level_type = " + ACC_LEVEL_BC + " \n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_BC + " THEN  bc = ? and acc_level_type = " + ACC_LEVEL_CO + " \n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_CO + " THEN  co = ? and acc_level_type = " + ACC_LEVEL_SA + " \n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_SA + " THEN  sa = ? and acc_level_type = " + ACC_LEVEL_AG + " \n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN  ag = ? and acc_level_type = " + ACC_LEVEL_AG1 + " \n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG1 + " THEN  ag1 = ? and acc_level_type = " + ACC_LEVEL_AG2 + " \n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG2 + " THEN  ag2 = ? and acc_level_type = " + ACC_LEVEL_AG3 + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG3 + " THEN  ag3 = ? and acc_level_type = " + ACC_LEVEL_AG4 + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG4 + " THEN  ag4 = ? and acc_level_type = " + ACC_LEVEL_AG5 + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN  ag5 = ? and acc_level_type = " + ACC_LEVEL_AG6 + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG6 + " THEN  ag6 = ? and acc_level_type = " + ACC_LEVEL_AG7 + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG7 + " THEN  ag7 = ? and acc_level_type = " + ACC_LEVEL_AG8 + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG8 + " THEN  ag8 = ? and acc_level_type = " + ACC_LEVEL_AG9 + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG9 + " THEN  ag9 = ? and acc_level_type = " + ACC_LEVEL_AG10 + "	\n");
				updateList.add(accId);
				sb.append("	ELSE FALSE END )	\n");

				StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateList);
				todo = true;
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateList != null) {
				updateList.clear();
				updateList = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return todo;
	}

	// 修改下層直屬會員 的 最小佔成數
	@Override
	public boolean updateLowerLevelMemRatio(long accId, int currentRatio, int gameType) {
		boolean todo = false;
		List<Object> updateList = null;
		StringBuilder sb = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("updateLowerLevelMagRatio:NO WRITE_CONN");
				return todo;
			} else {
				sb = new StringBuilder();
				updateList = new ArrayList<Object>();

				sb.append("	update ctt_manager.ctt_member_ratio set next_upper_min_ratio = ? where game_type = ? 	\n");
				updateList.add(currentRatio);
				updateList.add(gameType);
				sb.append("	and acc_id IN(	\n");
				sb.append("	select acc_id from ctt_manager.ctt_member_acc_level where	\n");
				sb.append("	CASE (select acc_level_type from ctt_manager.ctt_manager_acc_level where acc_id = ?)	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_SC + " THEN  sc = ? and upper_acc_level_type = " + ACC_LEVEL_SC + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_BC + " THEN  bc = ? and upper_acc_level_type = " + ACC_LEVEL_BC + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_CO + " THEN  co = ? and upper_acc_level_type = " + ACC_LEVEL_CO + "   \n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_SA + " THEN  sa = ? and upper_acc_level_type = " + ACC_LEVEL_SA + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG + " THEN  ag = ? and upper_acc_level_type = " + ACC_LEVEL_AG + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG1 + " THEN  ag1 = ? and upper_acc_level_type = " + ACC_LEVEL_AG1 + " 	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG2 + " THEN  ag2 = ? and upper_acc_level_type = " + ACC_LEVEL_AG2 + " 	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG3 + " THEN  ag3 = ? and upper_acc_level_type = " + ACC_LEVEL_AG3 + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG4 + " THEN  ag4 = ? and upper_acc_level_type = " + ACC_LEVEL_AG4 + " 	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG5 + " THEN  ag5 = ? and upper_acc_level_type = " + ACC_LEVEL_AG5 + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG6 + " THEN  ag6 = ? and upper_acc_level_type = " + ACC_LEVEL_AG6 + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG7 + " THEN  ag7 = ? and upper_acc_level_type = " + ACC_LEVEL_AG7 + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG8 + " THEN  ag8 = ? and upper_acc_level_type = " + ACC_LEVEL_AG8 + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG9 + " THEN  ag9 = ? and upper_acc_level_type = " + ACC_LEVEL_AG9 + "	\n");
				updateList.add(accId);
				sb.append("	WHEN " + ACC_LEVEL_AG10 + " THEN  ag10 = ? and upper_acc_level_type = " + ACC_LEVEL_AG10 + "	\n");
				updateList.add(accId);
				sb.append("	ELSE FALSE END )	\n");

				StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateList);
				todo = true;
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateList != null) {
				updateList.clear();
				updateList = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return todo;
	}

	@Override
	public List<Map<String, Object>> showLog(long opsAccId, long updateAccId, int actionUpperId, int accLevel, int firstCount, int count) {
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		List<Object> params = null;
		String logTable = null;
		String selectAccTable = null;
		int opsType = 0;
		String levelType = null;

		try {
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				if (accLevel == ACC_LEVEL_MEM && actionUpperId != 4) {
					logTable = "ctt_member_update_log";
					selectAccTable = "member";
					opsType = 2;
					levelType = "upper_acc_level_type";
				} else if (accLevel >= ACC_LEVEL_COM && accLevel <= ACC_LEVEL_AG10 && actionUpperId != 4) {
					logTable = "ctt_manager_update_log";
					selectAccTable = "manager";
					opsType = 1;
					levelType = "acc_level_type";
				} else if (accLevel == ACC_LEVEL_MEM && actionUpperId == 4) {
					logTable = "ctt_member_money_log";
					selectAccTable = "member";
					opsType = 2;
					levelType = "upper_acc_level_type";
				} else if (accLevel >= ACC_LEVEL_COM && accLevel <= ACC_LEVEL_AG10 && actionUpperId == 4) {
					logTable = "ctt_manager_money_log";
					selectAccTable = "manager";
					opsType = 1;
					levelType = "acc_level_type";
				}

				sb.append("select \n");
				sb.append("ops_acc_id \n");
				sb.append(",ops_acc_name \n");
				sb.append(",acc_name \n");
				sb.append(",acc_id \n");
				sb.append(",ops_type \n");
				sb.append(",action \n");
				sb.append(",detail \n");
				sb.append(",ops_datetime \n");
				sb.append(",CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) AS ip \n");
				sb.append("from  \n");
				sb.append("(SELECT * FROM ctt_manager." + logTable + " \n");
				sb.append("WHERE \n");
				sb.append("ops_acc_id = ? \n");
				params.add(opsAccId);
				if (updateAccId > 0) {
					sb.append("AND acc_id =? \n");
					params.add(updateAccId);
				} else if (updateAccId == 0 && accLevel >= ACC_LEVEL_COM && accLevel <= ACC_LEVEL_AG10) {
					sb.append("AND acc_id IN (SELECT acc_id FROM ctt_manager.ctt_" + selectAccTable + "_acc_level WHERE " + levelType + " = ?) \n");
					params.add(accLevel);
				}
				sb.append("AND ops_type = 1 \n");
				sb.append("AND action IN (SELECT action_id FROM ctt_manager.ctt_action WHERE upper_id = ?) \n");
				params.add(actionUpperId);
				sb.append("AND ? > (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				params.add(accLevel);
				params.add(opsAccId);
				sb.append("UNION \n");
				sb.append("SELECT * FROM ctt_manager." + logTable + " \n");
				sb.append("WHERE \n");
				sb.append("ops_acc_id IN \n");
				sb.append("(SELECT acc_id FROM ctt_manager.ctt_" + selectAccTable + "_acc_level \n");
				sb.append("WHERE(CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				params.add(opsAccId);
				sb.append("WHEN 0 THEN TRUE \n");
				sb.append("WHEN 1 THEN TRUE \n");
				sb.append("WHEN 2 THEN sc = ? \n");
				params.add(opsAccId);
				sb.append("WHEN 3 THEN bc = ? \n");
				params.add(opsAccId);
				sb.append("WHEN 4 THEN co = ? \n");
				params.add(opsAccId);
				sb.append("WHEN 5 THEN sa = ? \n");
				params.add(opsAccId);
				sb.append("WHEN 6 THEN ag = ? \n");
				params.add(opsAccId);
				sb.append("WHEN 7 THEN ag1 = ? \n");
				params.add(opsAccId);
				sb.append("WHEN 8 THEN ag2 = ? \n");
				params.add(opsAccId);
				sb.append("WHEN 9 THEN ag3 = ? \n");
				params.add(opsAccId);
				sb.append("WHEN 10 THEN ag4 = ? \n");
				params.add(opsAccId);
				sb.append("WHEN 11 THEN ag5 = ? \n");
				params.add(opsAccId);
				sb.append("WHEN 12 THEN ag6 = ? \n");
				params.add(opsAccId);
				sb.append("WHEN 13 THEN ag7 = ? \n");
				params.add(opsAccId);
				sb.append("WHEN 14 THEN ag8 = ? \n");
				params.add(opsAccId);
				sb.append("WHEN 15 THEN ag9 = ? \n");
				params.add(opsAccId);
				sb.append("WHEN 16 THEN ag10 = ? ELSE FALSE END)) \n");
				params.add(opsAccId);
				if (updateAccId > 0) {
					sb.append("AND acc_id =? \n");
					params.add(updateAccId);
				} else if (updateAccId == 0 && accLevel >= ACC_LEVEL_COM && accLevel <= ACC_LEVEL_AG10) {
					sb.append("AND acc_id IN (SELECT acc_id FROM ctt_manager.ctt_" + selectAccTable + "_acc_level WHERE " + levelType + " = ?) \n");
					params.add(accLevel);
				}
				sb.append("AND ops_type = ? \n");
				params.add(opsType);
				sb.append("AND action IN (SELECT action_id FROM ctt_manager.ctt_action WHERE upper_id = ?) \n");
				params.add(actionUpperId);
				sb.append(") A \n");
				sb.append("ORDER BY ops_datetime DESC \n");
				sb.append("limit ?,? \n");
				params.add(firstCount);
				params.add(count);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				if (listMap == null) {
					LOG.debug("showOpsLog_listMap is null");
				} else {
					LOG.debug("showOpsLog_listMap_size===" + listMap.size());
				}
			}
		} catch (Exception e) {
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
			logTable = null;
			selectAccTable = null;
		}
		return listMap;
	}

	@Override
	public int getLogTotalCount(long opsAccId, long updateAccId, int actionUpperId, int accLevel) {
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		String logTable = null;
		String selectAccTable = null;
		String levelType = null;
		int opsType = 0;
		List<Object> objs = null;
		try {
			sb = new StringBuilder();
			objs = new ArrayList<Object>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("no connection");
				return count;
			} else {
				if (accLevel == ACC_LEVEL_MEM && actionUpperId != 4) {
					logTable = "ctt_member_update_log";
				} else if (accLevel >= ACC_LEVEL_COM && accLevel <= ACC_LEVEL_AG10 && actionUpperId != 4) {
					logTable = "ctt_manager_update_log";
				} else if (accLevel == ACC_LEVEL_MEM && actionUpperId == 4) {
					logTable = "ctt_member_money_log";
				} else if (accLevel >= ACC_LEVEL_COM && accLevel <= ACC_LEVEL_AG10 && actionUpperId == 4) {
					logTable = "ctt_manager_money_log";
				}
				if (accLevel == ACC_LEVEL_MEM) {
					selectAccTable = "member";
					opsType = 2;
					levelType = "upper_acc_level_type";
				} else if (accLevel >= ACC_LEVEL_COM && accLevel < ACC_LEVEL_MEM) {
					selectAccTable = "manager";
					opsType = 1;
					levelType = "acc_level_type";
				}

				sb.append("select \n");
				sb.append("COUNT(1) as COUNT \n");
				sb.append("from  \n");
				sb.append("(SELECT * FROM ctt_manager." + logTable + " \n");
				sb.append("WHERE \n");
				sb.append("ops_acc_id = ? \n");
				objs.add(opsAccId);
				if (updateAccId > 0) {
					sb.append("AND acc_id =" + updateAccId + " \n");
				} else if (updateAccId == 0 && accLevel >= ACC_LEVEL_COM && accLevel <= ACC_LEVEL_AG10) {
					sb.append("AND acc_id IN (SELECT acc_id FROM ctt_manager.ctt_" + selectAccTable + "_acc_level WHERE " + levelType + " = ?) \n");
					objs.add(accLevel);
				}
				sb.append("AND ops_type = 1 \n");
				sb.append("AND action IN (SELECT action_id FROM ctt_manager.ctt_action WHERE upper_id = ?) \n");
				objs.add(actionUpperId);
				sb.append("union \n");
				sb.append("SELECT * FROM ctt_manager." + logTable + " \n");
				sb.append("WHERE \n");
				sb.append("ops_acc_id IN \n");
				sb.append("(SELECT acc_id FROM ctt_manager.ctt_" + selectAccTable + "_acc_level \n");
				sb.append("WHERE(CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				objs.add(opsAccId);
				sb.append("WHEN 0 THEN TRUE \n");
				sb.append("WHEN 1 THEN TRUE \n");
				sb.append("WHEN 2 THEN sc = ? \n");
				objs.add(opsAccId);
				sb.append("WHEN 3 THEN bc = ? \n");
				objs.add(opsAccId);
				sb.append("WHEN 4 THEN co = ? \n");
				objs.add(opsAccId);
				sb.append("WHEN 5 THEN sa = ? \n");
				objs.add(opsAccId);
				sb.append("WHEN 6 THEN ag = ? \n");
				objs.add(opsAccId);
				sb.append("WHEN 7 THEN ag1 = ? \n");
				objs.add(opsAccId);
				sb.append("WHEN 8 THEN ag2 = ? \n");
				objs.add(opsAccId);
				sb.append("WHEN 9 THEN ag3 = ? \n");
				objs.add(opsAccId);
				sb.append("WHEN 10 THEN ag4 = ? \n");
				objs.add(opsAccId);
				sb.append("WHEN 11 THEN ag5 = ? \n");
				objs.add(opsAccId);
				sb.append("WHEN 12 THEN ag6 = ? \n");
				objs.add(opsAccId);
				sb.append("WHEN 13 THEN ag7 = ? \n");
				objs.add(opsAccId);
				sb.append("WHEN 14 THEN ag8 = ? \n");
				objs.add(opsAccId);
				sb.append("WHEN 15 THEN ag9 = ? \n");
				objs.add(opsAccId);
				sb.append("WHEN 16 THEN ag10 = ? ELSE FALSE END)) \n");
				objs.add(opsAccId);
				if (updateAccId > 0) {
					sb.append("AND acc_id = " + updateAccId + " \n");
				} else if (updateAccId == 0 && accLevel >= ACC_LEVEL_COM && accLevel <= ACC_LEVEL_AG10) {
					sb.append("AND acc_id IN (SELECT acc_id FROM ctt_manager.ctt_" + selectAccTable + "_acc_level WHERE " + levelType + " = ?) \n");
					objs.add(accLevel);
				}
				sb.append("AND ops_type = " + opsType + " \n");
				sb.append("AND action IN (SELECT action_id FROM ctt_manager.ctt_action WHERE upper_id = ?) \n");
				objs.add(actionUpperId);
				sb.append(") A \n");

				LOG.debug("SQL===\n" + sb.toString());
				ps = this.READ_CONN.prepareStatement(sb.toString());
				for (int i = 0; i < objs.size(); i++) {
					ps.setObject((i + 1), objs.get(i));
				}
				rs = ps.executeQuery();
				if (rs.next()) {
					count = rs.getInt("count");
				}
				LOG.debug("count===" + count);
			}
		} catch (Exception e) {
			LOG.debug("getLogTotalCount_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (sb != null) {
					sb.setLength(0);
					sb = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
				logTable = null;
				opsType = 0;
				selectAccTable = null;
			} catch (Exception e) {
				LOG.debug("getLogTotalCount_Exception===" + e.getMessage());
				ShowLog.err(LOG, e);
			}
		}
		return count;
	}

	@Override
	public int getOrderLogTotalCount(int upperAction) {
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try {
			sb = new StringBuilder();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("no connection");
				return count;
			} else {
				sb.append("SELECT COUNT(1) as COUNT FROM ctt_manager.ctt_data_ops_log \n");
				sb.append("WHERE \n");
				sb.append("action IN (SELECT action_id FROM ctt_manager.ctt_action WHERE upper_id = ?) \n");

				LOG.debug("SQL===\n" + sb.toString());
				ps = this.READ_CONN.prepareStatement(sb.toString());
				ps.setLong(1, upperAction);

				rs = ps.executeQuery();
				if (rs.next()) {
					count = rs.getInt("count");
				}
				LOG.debug("count===" + count);
			}
		} catch (Exception e) {
			count = 0;
			LOG.debug("getLogTotalCount_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (sb != null) {
					sb.setLength(0);
					sb = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (Exception e) {
				count = 0;
				LOG.debug("getLogTotalCount_Exception===" + e.getMessage());
				ShowLog.err(LOG, e);
			}
		}
		return count;
	}

	@Override
	public List<Map<String, Object>> showOrderLog(int upperAction, int firstCount, int count) {
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
				sb.append("ops_acc_id \n");
				sb.append(",ops_acc_name \n");
				sb.append(",acc_name \n");
				sb.append(",data_id \n");
				sb.append(",ops_type \n");
				sb.append(",action \n");
				sb.append(",detail \n");
				sb.append(",ops_datetime \n");
				sb.append(",CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) AS ip \n");
				sb.append("FROM ctt_manager.ctt_data_ops_log \n");
				sb.append("WHERE \n");
				sb.append("action IN (SELECT action_id FROM ctt_manager.ctt_action WHERE upper_id = ?) \n");
				params.add(upperAction);
				sb.append("ORDER BY ops_datetime DESC \n");
				sb.append("limit ?,? \n");
				params.add(firstCount);
				params.add(count);

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

	@Override
	public SetGameServerBean getGameServerDataForLog(int sid) {
		StringBuilder sb = null;
		List<Object> params = null;
		SetGameServerBean bean = null;
		List<Object> list = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();
				bean = new SetGameServerBean();
				list = new ArrayList<Object>();

				sb.append("SELECT * FROM ctt_manager.ctt_manager_game_set WHERE sid = ? \n");
				params.add(sid);
				list = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), params, bean);
				if (list.size() == 1) {
					bean = (SetGameServerBean) list.get(0);
				}
			}
		} catch (Exception e) {
			bean = null;
			LOG.debug("getGameServerData_Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params = new ArrayList<Object>();
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
		return bean;
	}

	@Override
	public int getGameSetLogTotalCount(int upperAction) {
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try {
			sb = new StringBuilder();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return count;
			} else {
				sb.append("SELECT COUNT(1) as COUNT FROM ctt_manager.ctt_data_ops_log \n");
				sb.append("WHERE \n");
				sb.append("action IN (SELECT action_id FROM ctt_manager.ctt_action WHERE upper_id = ?) \n");

				LOG.debug("SQL===\n" + sb.toString());
				ps = this.READ_CONN.prepareStatement(sb.toString());
				ps.setLong(1, upperAction);

				rs = ps.executeQuery();
				if (rs.next()) {
					count = rs.getInt("count");
				}
				LOG.debug("count===" + count);
			}
		} catch (Exception e) {
			count = 0;
			LOG.debug("getLogTotalCount_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (sb != null) {
					sb.setLength(0);
					sb = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (Exception e) {
				count = 0;
				LOG.debug("getLogTotalCount_Exception===" + e.getMessage());
				ShowLog.err(LOG, e);
			}
		}
		return count;
	}

	@Override
	public List<Map<String, Object>> showGameSetLog(int upperAction, int firstCount, int count) {
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
				sb.append("ops_acc_id \n");
				sb.append(",ops_acc_name \n");
				sb.append(",acc_name \n");
				sb.append(",data_id \n");
				sb.append(",ops_type \n");
				sb.append(",action \n");
				sb.append(",detail \n");
				sb.append(",ops_datetime \n");
				sb.append(",CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) AS ip \n");
				sb.append("FROM ctt_manager.ctt_data_ops_log \n");
				sb.append("WHERE \n");
				sb.append("action IN (SELECT action_id FROM ctt_manager.ctt_action WHERE upper_id = ?) \n");
				params.add(upperAction);
				sb.append("ORDER BY ops_datetime DESC \n");
				sb.append("limit ?,? \n");
				params.add(firstCount);
				params.add(count);

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

	@Override
	public List<?> searchCOM(long userId, long accId, int status, int sort, int asc) {
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		List<Object> list = new ArrayList<Object>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return list;
			} else {
				if (accId <= 0) {
					accId = userId;
				}
				sb.append("SELECT " + getSelect(1));
				sb.append("	FROM  \n");
				sb.append("	(  \n");
				sb.append("	SELECT acc_id,acc_level_type,sc,acc_status FROM ctt_manager.ctt_manager_acc_level  \n");
				sb.append("	WHERE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id=?)<" + ACC_LEVEL_COM + "  \n");
				params.add(userId);
				sb.append("	AND (CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?)   \n");
				params.add(accId);
				sb.append("	WHEN 0 THEN TRUE   \n");
				sb.append("	WHEN 1 THEN acc_id =? \n");
				params.add(accId);
				sb.append("	ELSE FALSE END ) \n");
				sb.append("	AND acc_level_type=" + ACC_LEVEL_COM + " AND acc_status=? \n");
				params.add(status);
				sb.append("	) A  \n");
				sb.append("	INNER JOIN  \n");
				sb.append("	(SELECT acc_id,acc_name,nickname,auth_group FROM ctt_manager.ctt_manager_acc WHERE acc_id IN  \n");
				sb.append("	(SELECT acc_id FROM ctt_manager.ctt_manager_acc_level WHERE acc_level_type=" + ACC_LEVEL_COM + " AND acc_status=?)  \n");
				params.add(status);
				sb.append("	) C  \n");
				sb.append("	ON A.acc_id=C.acc_id  \n");
				sb.append("	INNER JOIN  \n");
				sb.append("	(SELECT acc_id,DATE_FORMAT(create_datetime, '%Y-%m-%d %T') as create_time FROM ctt_manager.ctt_manager_create) D  \n");
				sb.append("	ON A.acc_id=D.acc_id  \n");
				sb.append("	INNER JOIN  \n");
				sb.append(
						"	(SELECT acc_id,DATE_FORMAT(last_login_datetime, '%Y-%m-%d %T') as last_login_time,CAST(CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) AS char) ip FROM ctt_manager.ctt_manager_last_login) E  \n");
				sb.append("	ON A.acc_id=E.acc_id  \n");
				sb.append("	LEFT OUTER JOIN  \n");
				sb.append("	(  \n");
				sb.append(
						"	SELECT acc_id , case when online_datetime > CURRENT_TIMESTAMP and forced_logout_datetime > CURRENT_TIMESTAMP then 'Y' else 'N' end as onlines  FROM ctt_manager.ctt_manager_token) G  \n");
				sb.append("	on A.acc_id=G.acc_id  \n");
				sb.append("	LEFT OUTER JOIN  \n");
				sb.append("	ctt_manager.ctt_auth_group J  \n");
				sb.append("	ON C.auth_group=J.group_id  \n");
				sb.append("ORDER BY " + orderBy[sort] + " " + ASC[asc] + " \n");

				list = StmtUtil.queryToBean(READ_CONN, sb.toString(), params, new ManagerAcciunyInfos());

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return list;
	}

	@Override
	public List<?> searchMAG(int levelType, long userId, long upAccId, long accId, int status, int sort, int asc, boolean checkStatus) {
		StringBuilder sb = null;
		List<Object> params = null;
		List<Object> list = null;
		long searchId = 0;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return list;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();
				list = new ArrayList<Object>();
				if (upAccId <= 0) {
					upAccId = userId;
				}
				sb.append("	SELECT acc_id,acc_level_type,sc,acc_status FROM ctt_manager.ctt_manager_acc_level  \n");
				sb.append("	WHERE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id =?)<" + levelType + "  \n");
				params.add(userId);
				sb.append("	AND CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?)  \n");
				params.add(upAccId);

				sb.append("	WHEN " + ACC_LEVEL_ADMIN + " THEN TRUE  \n");
				sb.append("	WHEN " + ACC_LEVEL_COM + " THEN TRUE  \n");

				for (int i = 0; i < ACC_LEVEL_MAG.length; i++) {
					if (levelType > ACC_LEVEL_MAG[i]) {
						if (!"".equals(ACC_LEVEL_MAG_TABLE_NAME[i]) && ACC_LEVEL_MAG_TABLE_NAME[i] != null) {
							sb.append("	WHEN " + ACC_LEVEL_MAG[i] + " THEN " + ACC_LEVEL_MAG_TABLE_NAME[i] + " =? \n");
							params.add(upAccId);
						}
					}
				}
				sb.append("ELSE FALSE END \n");
				sb.append(
						"	AND (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) <=  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?)  \n");
				params.add(userId);
				params.add(upAccId);
				if (accId > 0) {
					sb.append("	AND acc_id= ? \n");
					params.add(accId);
				}
				sb.append("	AND acc_level_type=" + levelType + " \n");
				if (checkStatus) {
					sb.append("	AND acc_status=? \n");
					params.add(status);
				}
				if (accId > 0) {
					searchId = accId;
				} else {
					searchId = upAccId;
				}
				Map<String, Object> map = selectMagSql(sb.toString(), params, levelType, userId, searchId, checkStatus, status, sort, asc);
				if (map.containsKey("sql") && map.containsKey("list")) {
					List<Object> selectObj = (List<Object>) map.get("list");
					list = StmtUtil.queryToBean(READ_CONN, map.get("sql").toString(), selectObj, new ManagerAcciunyInfos());
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return list;
	}

	@Override
	public List<?> searchMEM(int searchLevel, long userId, long upAccId, String accName, int memType, int status, int sort, int asc,
			boolean checkStatus, int firstCount, int count) {

		StringBuilder sb = null;
		List<Object> params = null;
		List<Object> list = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				LOG.debug("start----->>>>>"+new Date().getTime());
				sb = new StringBuilder();
				params = new ArrayList<Object>();
				list = new ArrayList<Object>();

				if (upAccId <= 0) {
					upAccId = userId;
				}
				sb.append("SELECT " + getSelect(ACC_LEVEL_MEM));
				sb.append("FROM  \n");
				sb.append("(  \n");
				sb.append(
						"SELECT s2.acc_id,s2.acc_level_type,s2.sc,s2.bc,s2.co,s2.sa,s2.ag,s2.acc_status,s2.upper_acc_level_type,s2.level_type_text FROM \n");
				sb.append("((SELECT acc_id FROM ctt_manager.ctt_member_acc_level  \n");
				sb.append("WHERE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?)<" + ACC_LEVEL_MEM + "  \n");
				params.add(userId);
				sb.append("AND CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?)  \n");
				params.add(upAccId);
				sb.append("WHEN " + ACC_LEVEL_ADMIN + " THEN TRUE  \n");
				sb.append("WHEN " + ACC_LEVEL_COM + " THEN TRUE  \n");

				for (int i = 0; i < ACC_LEVEL_MAG.length; i++) {
					sb.append("WHEN " + ACC_LEVEL_MAG[i] + " THEN " + ACC_LEVEL_MAG_TABLE_NAME[i] + " =? \n");
					params.add(upAccId);
					if (memType == DIRECTLY_UNDER_MEM) {
						sb.append("AND upper_acc_level_type = " + ACC_LEVEL_MAG[i] + " \n");
					}
				}
				sb.append("ELSE FALSE END \n");
				sb.append(
						"AND (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) <=  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				params.add(userId);
				params.add(upAccId);
				if (!"".equals(accName)) {
					sb.append("AND acc_id = (select acc_id from ctt_manager.ctt_member_acc where acc_name = ? ) \n");
					params.add(accName);
				}
				if (searchLevel > ACC_LEVEL_COM) {
					if (memType == DIRECTLY_UNDER_MEM) {
						sb.append("AND upper_acc_level_type = ?  \n");
						params.add(searchLevel);
					} else {
						sb.append("AND upper_acc_level_type >= ?  \n");
						params.add(searchLevel);
					}

				}
				if (checkStatus) {
					sb.append("AND acc_status=?  \n");
					params.add(status);
				}
				sb.append("LIMIT ?,?  \n");
				params.add(firstCount);
				params.add(count);
				sb.append(") s1 \n");
				sb.append("INNER JOIN  \n");
				sb.append("(SELECT acc_id," + ACC_LEVEL_MEM + " AS acc_level_type,sc,bc,co,sa,ag,acc_status,upper_acc_level_type  \n");
				sb.append(",(CASE WHEN upper_acc_level_type = 2 THEN '總監直屬會員' \n");
				sb.append("WHEN upper_acc_level_type = " + ACC_LEVEL_BC + " THEN '大股東直屬會員' \n");
				sb.append("WHEN upper_acc_level_type = " + ACC_LEVEL_CO + " THEN '股東直屬會員' \n");
				sb.append("WHEN upper_acc_level_type = " + ACC_LEVEL_SA + " THEN '總代理直屬會員' \n");
				sb.append("WHEN upper_acc_level_type = " + ACC_LEVEL_AG + " THEN '一般會員' \n");
				sb.append("WHEN upper_acc_level_type = " + ACC_LEVEL_AG1 + " THEN '代理1直屬會員' \n");
				sb.append("WHEN upper_acc_level_type = " + ACC_LEVEL_AG2 + " THEN '代理2直屬會員' \n");
				sb.append("WHEN upper_acc_level_type = " + ACC_LEVEL_AG3 + " THEN '代理3直屬會員' \n");
				sb.append("WHEN upper_acc_level_type = " + ACC_LEVEL_AG4 + " THEN '代理4直屬會員' \n");
				sb.append("WHEN upper_acc_level_type = " + ACC_LEVEL_AG5 + " THEN '代理5直屬會員' \n");
				sb.append("WHEN upper_acc_level_type = " + ACC_LEVEL_AG6 + " THEN '代理6直屬會員' \n");
				sb.append("WHEN upper_acc_level_type = " + ACC_LEVEL_AG7 + " THEN '代理7直屬會員' \n");
				sb.append("WHEN upper_acc_level_type = " + ACC_LEVEL_AG8 + " THEN '代理8直屬會員' \n");
				sb.append("WHEN upper_acc_level_type = " + ACC_LEVEL_AG9 + " THEN '代理9直屬會員' \n");
				sb.append("WHEN upper_acc_level_type = " + ACC_LEVEL_AG10
						+ " THEN '代理10直屬會員' ELSE '' END) as level_type_text FROM ctt_manager.ctt_member_acc_level ) s2 \n");
				sb.append("ON s1.acc_id = s2.acc_id) \n");
				sb.append(") A  \n");
				sb.append("INNER JOIN   \n");
				sb.append("(  \n");
				sb.append("SELECT acc_id,acc_name,nickname,0 as auth_group FROM ctt_manager.ctt_member_acc  \n");
				sb.append(") C  \n");
				sb.append("ON A.acc_id=C.acc_id  \n");
				sb.append("INNER JOIN  \n");
				sb.append("(SELECT acc_id,DATE_FORMAT(create_datetime, '%Y-%m-%d %T') as create_time FROM ctt_manager.ctt_member_create) D  \n");
				sb.append("ON A.acc_id=D.acc_id  \n");
				sb.append("INNER JOIN   \n");
				sb.append(
						"(SELECT acc_id, (CASE WHEN DATE_FORMAT(last_login_datetime, '%Y-%m-%d %T') = DATE_FORMAT((SELECT COLUMN_DEFAULT FROM information_schema.columns where TABLE_SCHEMA = 'ctt_manager' AND TABLE_NAME = 'ctt_member_last_login' AND COLUMN_NAME = 'last_login_datetime'), '%Y-%m-%d %T') THEN '0000-00-00 00:00:00' ELSE DATE_FORMAT(last_login_datetime, '%Y-%m-%d %T') END) as last_login_time, (CAST(CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) AS char))as ip  FROM ctt_manager.ctt_member_last_login) E  \n");
				sb.append("ON A.acc_id=E.acc_id  \n");
				sb.append("INNER JOIN  \n");
				sb.append("(  \n");
				sb.append(
						"SELECT acc_id,FORMAT(SUM(sc_id),0) AS sc_id,GROUP_CONCAT(sc_nickname SEPARATOR'') AS sc_nickname,GROUP_CONCAT(sc_acc_name SEPARATOR'') AS sc_acc_name ,FORMAT(SUM(sc_acc_status),0) AS sc_acc_status \n");
				sb.append(
						",FORMAT(SUM(bc_id),0) AS bc_id,GROUP_CONCAT(bc_nickname SEPARATOR'') AS bc_nickname,GROUP_CONCAT(bc_acc_name SEPARATOR'') AS bc_acc_name ,FORMAT(SUM(bc_acc_status),0) AS bc_acc_status  \n");
				sb.append(
						",FORMAT(SUM(co_id),0) AS co_id,GROUP_CONCAT(co_nickname SEPARATOR'') AS co_nickname,GROUP_CONCAT(co_acc_name SEPARATOR'') AS co_acc_name ,FORMAT(SUM(co_acc_status),0) AS co_acc_status  \n");
				sb.append(
						",FORMAT(SUM(sa_id),0) AS sa_id,GROUP_CONCAT(sa_nickname SEPARATOR'') AS sa_nickname,GROUP_CONCAT(sa_acc_name SEPARATOR'') AS sa_acc_name,FORMAT(SUM(sa_acc_status),0) AS sa_acc_status   \n");
				sb.append(
						",FORMAT(SUM(ag_id),0) AS ag_id,GROUP_CONCAT(ag_nickname SEPARATOR'') AS ag_nickname,GROUP_CONCAT(ag_acc_name SEPARATOR'') AS ag_acc_name  ,FORMAT(SUM(ag_acc_status),0) AS ag_acc_status \n");
				sb.append(
						",FORMAT(SUM(ag1_id),0) AS ag1_id,GROUP_CONCAT(ag1_nickname SEPARATOR'') AS ag1_nickname,GROUP_CONCAT(ag1_acc_name SEPARATOR'') AS ag1_acc_name , FORMAT(SUM(ag1_acc_status),0) AS ag1_acc_status \n");
				sb.append(
						",FORMAT(SUM(ag2_id),0) AS ag2_id,GROUP_CONCAT(ag2_nickname SEPARATOR'') AS ag2_nickname,GROUP_CONCAT(ag2_acc_name SEPARATOR'') AS ag2_acc_name , FORMAT(SUM(ag2_acc_status),0) AS ag2_acc_status \n");
				sb.append(
						",FORMAT(SUM(ag3_id),0) AS ag3_id,GROUP_CONCAT(ag3_nickname SEPARATOR'') AS ag3_nickname,GROUP_CONCAT(ag3_acc_name SEPARATOR'') AS ag3_acc_name , FORMAT(SUM(ag3_acc_status),0) AS ag3_acc_status \n");
				sb.append(
						",FORMAT(SUM(ag4_id),0) AS ag4_id,GROUP_CONCAT(ag4_nickname SEPARATOR'') AS ag4_nickname,GROUP_CONCAT(ag4_acc_name SEPARATOR'') AS ag4_acc_name , FORMAT(SUM(ag4_acc_status),0) AS ag4_acc_status \n");
				sb.append(
						",FORMAT(SUM(ag5_id),0) AS ag5_id,GROUP_CONCAT(ag5_nickname SEPARATOR'') AS ag5_nickname,GROUP_CONCAT(ag5_acc_name SEPARATOR'') AS ag5_acc_name , FORMAT(SUM(ag5_acc_status),0) AS ag5_acc_status \n");
				sb.append(
						",FORMAT(SUM(ag6_id),0) AS ag6_id,GROUP_CONCAT(ag6_nickname SEPARATOR'') AS ag6_nickname,GROUP_CONCAT(ag6_acc_name SEPARATOR'') AS ag6_acc_name , FORMAT(SUM(ag6_acc_status),0) AS ag6_acc_status \n");
				sb.append(
						",FORMAT(SUM(ag7_id),0) AS ag7_id,GROUP_CONCAT(ag7_nickname SEPARATOR'') AS ag7_nickname,GROUP_CONCAT(ag7_acc_name SEPARATOR'') AS ag7_acc_name , FORMAT(SUM(ag7_acc_status),0) AS ag7_acc_status \n");
				sb.append(
						",FORMAT(SUM(ag8_id),0) AS ag8_id,GROUP_CONCAT(ag8_nickname SEPARATOR'') AS ag8_nickname,GROUP_CONCAT(ag8_acc_name SEPARATOR'') AS ag8_acc_name , FORMAT(SUM(ag8_acc_status),0) AS ag8_acc_status \n");
				sb.append(
						",FORMAT(SUM(ag9_id),0) AS ag9_id,GROUP_CONCAT(ag9_nickname SEPARATOR'') AS ag9_nickname,GROUP_CONCAT(ag9_acc_name SEPARATOR'') AS ag9_acc_name , FORMAT(SUM(ag9_acc_status),0) AS ag9_acc_status \n");
				sb.append(
						",FORMAT(SUM(ag10_id),0) AS ag10_id,GROUP_CONCAT(ag10_nickname SEPARATOR'') AS ag10_nickname,GROUP_CONCAT(ag10_acc_name SEPARATOR'') AS ag10_acc_name , FORMAT(SUM(ag10_acc_status),0) AS ag10_acc_status \n");
				sb.append("FROM  \n");
				sb.append("(  \n");
				sb.append("SELECT  \n");
				sb.append("acc_id  \n");
				sb.append(",CASE WHEN sc=acc_id1 THEN acc_id1 ELSE '' END AS sc_id  \n");
				sb.append(",CASE WHEN sc=acc_id1 THEN nickname ELSE '' END AS sc_nickname  \n");
				sb.append(",CASE WHEN sc=acc_id1 THEN acc_name ELSE '' END AS sc_acc_name  \n");
				sb.append(",CASE WHEN sc=acc_id1 THEN acc_status ELSE '' END AS sc_acc_status  \n");
				sb.append(",CASE WHEN bc=acc_id1 THEN acc_id1 ELSE '' END AS bc_id  \n");
				sb.append(",CASE WHEN bc=acc_id1 THEN nickname ELSE '' END AS bc_nickname  \n");
				sb.append(",CASE WHEN bc=acc_id1 THEN acc_name ELSE '' END AS bc_acc_name  \n");
				sb.append(",CASE WHEN bc=acc_id1 THEN acc_status ELSE '' END AS bc_acc_status  \n");
				sb.append(",CASE WHEN co=acc_id1 THEN acc_id1 ELSE '' END AS co_id  \n");
				sb.append(",CASE WHEN co=acc_id1 THEN nickname ELSE '' END AS co_nickname  \n");
				sb.append(",CASE WHEN co=acc_id1 THEN acc_name ELSE '' END AS co_acc_name  \n");
				sb.append(",CASE WHEN co=acc_id1 THEN acc_status ELSE '' END AS co_acc_status  \n");
				sb.append(",CASE WHEN sa=acc_id1 THEN acc_id1 ELSE '' END AS sa_id  \n");
				sb.append(",CASE WHEN sa=acc_id1 THEN nickname ELSE '' END AS sa_nickname \n");
				sb.append(",CASE WHEN sa=acc_id1 THEN acc_name ELSE '' END AS sa_acc_name \n");
				sb.append(",CASE WHEN sa=acc_id1 THEN acc_status ELSE '' END AS sa_acc_status  \n");
				sb.append(",CASE WHEN ag=acc_id1 THEN acc_id1 ELSE '' END AS ag_id  \n");
				sb.append(",CASE WHEN ag=acc_id1 THEN nickname ELSE '' END AS ag_nickname  \n");
				sb.append(",CASE WHEN ag=acc_id1 THEN acc_name ELSE '' END AS ag_acc_name  \n");
				sb.append(",CASE WHEN ag=acc_id1 THEN acc_status ELSE '' END AS ag_acc_status  \n");
				sb.append(",CASE WHEN ag1=acc_id1 THEN acc_id1 ELSE '' END AS ag1_id  \n");
				sb.append(",CASE WHEN ag1=acc_id1 THEN nickname ELSE '' END AS ag1_nickname  \n");
				sb.append(",CASE WHEN ag1=acc_id1 THEN acc_name ELSE '' END AS ag1_acc_name  \n");
				sb.append(",CASE WHEN ag1=acc_id1 THEN acc_status ELSE '' END AS ag1_acc_status  \n");
				sb.append(",CASE WHEN ag2=acc_id1 THEN acc_id1 ELSE '' END AS ag2_id  \n");
				sb.append(",CASE WHEN ag2=acc_id1 THEN nickname ELSE '' END AS ag2_nickname  \n");
				sb.append(",CASE WHEN ag2=acc_id1 THEN acc_name ELSE '' END AS ag2_acc_name  \n");
				sb.append(",CASE WHEN ag2=acc_id1 THEN acc_status ELSE '' END AS ag2_acc_status  \n");
				sb.append(",CASE WHEN ag3=acc_id1 THEN acc_id1 ELSE '' END AS ag3_id  \n");
				sb.append(",CASE WHEN ag3=acc_id1 THEN nickname ELSE '' END AS ag3_nickname  \n");
				sb.append(",CASE WHEN ag3=acc_id1 THEN acc_name ELSE '' END AS ag3_acc_name  \n");
				sb.append(",CASE WHEN ag3=acc_id1 THEN acc_status ELSE '' END AS ag3_acc_status  \n");
				sb.append(",CASE WHEN ag4=acc_id1 THEN acc_id1 ELSE '' END AS ag4_id  \n");
				sb.append(",CASE WHEN ag4=acc_id1 THEN nickname ELSE '' END AS ag4_nickname  \n");
				sb.append(",CASE WHEN ag4=acc_id1 THEN acc_name ELSE '' END AS ag4_acc_name  \n");
				sb.append(",CASE WHEN ag4=acc_id1 THEN acc_status ELSE '' END AS ag4_acc_status  \n");
				sb.append(",CASE WHEN ag5=acc_id1 THEN acc_id1 ELSE '' END AS ag5_id  \n");
				sb.append(",CASE WHEN ag5=acc_id1 THEN nickname ELSE '' END AS ag5_nickname  \n");
				sb.append(",CASE WHEN ag5=acc_id1 THEN acc_name ELSE '' END AS ag5_acc_name  \n");
				sb.append(",CASE WHEN ag5=acc_id1 THEN acc_status ELSE '' END AS ag5_acc_status  \n");
				sb.append(",CASE WHEN ag6=acc_id1 THEN acc_id1 ELSE '' END AS ag6_id  \n");
				sb.append(",CASE WHEN ag6=acc_id1 THEN nickname ELSE '' END AS ag6_nickname  \n");
				sb.append(",CASE WHEN ag6=acc_id1 THEN acc_name ELSE '' END AS ag6_acc_name  \n");
				sb.append(",CASE WHEN ag6=acc_id1 THEN acc_status ELSE '' END AS ag6_acc_status  \n");
				sb.append(",CASE WHEN ag7=acc_id1 THEN acc_id1 ELSE '' END AS ag7_id  \n");
				sb.append(",CASE WHEN ag7=acc_id1 THEN nickname ELSE '' END AS ag7_nickname  \n");
				sb.append(",CASE WHEN ag7=acc_id1 THEN acc_name ELSE '' END AS ag7_acc_name  \n");
				sb.append(",CASE WHEN ag7=acc_id1 THEN acc_status ELSE '' END AS ag7_acc_status  \n");
				sb.append(",CASE WHEN ag8=acc_id1 THEN acc_id1 ELSE '' END AS ag8_id  \n");
				sb.append(",CASE WHEN ag8=acc_id1 THEN nickname ELSE '' END AS ag8_nickname  \n");
				sb.append(",CASE WHEN ag8=acc_id1 THEN acc_name ELSE '' END AS ag8_acc_name  \n");
				sb.append(",CASE WHEN ag8=acc_id1 THEN acc_status ELSE '' END AS ag8_acc_status  \n");
				sb.append(",CASE WHEN ag9=acc_id1 THEN acc_id1 ELSE '' END AS ag9_id  \n");
				sb.append(",CASE WHEN ag9=acc_id1 THEN nickname ELSE '' END AS ag9_nickname  \n");
				sb.append(",CASE WHEN ag9=acc_id1 THEN acc_name ELSE '' END AS ag9_acc_name  \n");
				sb.append(",CASE WHEN ag9=acc_id1 THEN acc_status ELSE '' END AS ag9_acc_status  \n");
				sb.append(",CASE WHEN ag10=acc_id1 THEN acc_id1 ELSE '' END AS ag10_id  \n");
				sb.append(",CASE WHEN ag10=acc_id1 THEN nickname ELSE '' END AS ag10_nickname  \n");
				sb.append(",CASE WHEN ag10=acc_id1 THEN acc_name ELSE '' END AS ag10_acc_name  \n");
				sb.append(",CASE WHEN ag10=acc_id1 THEN acc_status ELSE '' END AS ag10_acc_status  \n");
				sb.append("FROM (SELECT acc_id," + ACC_LEVEL_MEM
						+ " AS acc_level_type,sc,bc,co,sa,ag,ag1,ag2,ag3,ag4,ag5,ag6,ag7,ag8,ag9,ag10 FROM ctt_manager.ctt_member_acc_level  \n");
				sb.append("WHERE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id=?)<" + ACC_LEVEL_MEM + "  \n");
				params.add(userId);
				sb.append("AND (CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id =?)  \n");
				params.add(userId);
				sb.append("WHEN " + ACC_LEVEL_ADMIN + " THEN TRUE  \n");
				sb.append("WHEN " + ACC_LEVEL_COM + " THEN TRUE  \n");
				sb.append("WHEN " + ACC_LEVEL_SC + " THEN sc =? \n");
				params.add(userId);
				sb.append("WHEN " + ACC_LEVEL_BC + " THEN bc =? \n");
				params.add(userId);
				sb.append("WHEN " + ACC_LEVEL_CO + " THEN co =? \n");
				params.add(userId);
				sb.append("WHEN " + ACC_LEVEL_SA + " THEN sa =? \n");
				params.add(userId);
				sb.append("WHEN " + ACC_LEVEL_AG + " THEN ag =?  \n");
				params.add(userId);
				sb.append("WHEN " + ACC_LEVEL_AG1 + " THEN ag1 =? \n");
				params.add(userId);
				sb.append("WHEN " + ACC_LEVEL_AG2 + " THEN ag2 =? \n");
				params.add(userId);
				sb.append("WHEN " + ACC_LEVEL_AG3 + " THEN ag3 =? \n");
				params.add(userId);
				sb.append("WHEN " + ACC_LEVEL_AG4 + " THEN ag4 =? \n");
				params.add(userId);
				sb.append("WHEN " + ACC_LEVEL_AG5 + " THEN ag5 =? \n");
				params.add(userId);
				sb.append("WHEN " + ACC_LEVEL_AG6 + " THEN ag6 =? \n");
				params.add(userId);
				sb.append("WHEN " + ACC_LEVEL_AG7 + " THEN ag7 =? \n");
				params.add(userId);
				sb.append("WHEN " + ACC_LEVEL_AG8 + " THEN ag8 =? \n");
				params.add(userId);
				sb.append("WHEN " + ACC_LEVEL_AG9 + " THEN ag9 =? \n");
				params.add(userId);
				sb.append("WHEN " + ACC_LEVEL_AG10 + " THEN ag10 =? ELSE FALSE END)  \n");
				params.add(userId);
				sb.append(")T2    \n");
				sb.append("LEFT OUTER JOIN ( \n");
				sb.append("SELECT  t1.acc_id as acc_id1 ,t1.nickname,t1.acc_name,t2.acc_status from \n");
				sb.append("(SELECT acc_id ,nickname,acc_name FROM ctt_manager.ctt_manager_acc ) t1 \n");
				sb.append("inner join  \n");
				sb.append("(SELECT acc_id , acc_status FROM ctt_manager.ctt_manager_acc_level) t2 \n");
				sb.append("on t1.acc_id = t2.acc_id \n");
				sb.append(") T3  \n");
				sb.append("ON T2.sc=T3.acc_id1  \n");
				sb.append("OR T2.bc=T3.acc_id1  \n");
				sb.append("OR T2.co=T3.acc_id1  \n");
				sb.append("OR T2.sa=T3.acc_id1  \n");
				sb.append("OR T2.ag=T3.acc_id1 \n");
				sb.append("OR T2.ag1=T3.acc_id1  \n");
				sb.append("OR T2.ag2=T3.acc_id1  \n");
				sb.append("OR T2.ag3=T3.acc_id1  \n");
				sb.append("OR T2.ag4=T3.acc_id1  \n");
				sb.append("OR T2.ag5=T3.acc_id1  \n");
				sb.append("OR T2.ag6=T3.acc_id1  \n");
				sb.append("OR T2.ag7=T3.acc_id1  \n");
				sb.append("OR T2.ag8=T3.acc_id1  \n");
				sb.append("OR T2.ag9=T3.acc_id1 \n");
				sb.append("OR T2.ag10=T3.acc_id1    \n");
				sb.append(") T4 GROUP BY acc_id  \n");
				sb.append(") F  \n");
				sb.append("on A.acc_id=F.acc_id   \n");
				sb.append("LEFT OUTER JOIN \n");
				sb.append("(  \n");
				sb.append(
						"SELECT acc_id , case when online_datetime > CURRENT_TIMESTAMP and forced_logout_datetime > CURRENT_TIMESTAMP then 'Y' else 'N' end as onlines  FROM ctt_manager.ctt_member_token) G  \n");
				sb.append("on A.acc_id=G.acc_id  \n");
				sb.append("LEFT OUTER JOIN  \n");
				sb.append("(SELECT (acc_level_type*1) AS level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id=?) H  \n");
				params.add(userId);
				sb.append("on A.acc_id>0  \n");
				sb.append("LEFT OUTER JOIN   \n");
				sb.append(
						"(SELECT acc_id, com_ratio, sc_ratio, bc_ratio, co_ratio, sa_ratio ,ag_ratio,ag1_ratio,ag2_ratio,ag3_ratio,ag4_ratio,ag5_ratio,ag6_ratio,ag7_ratio,ag8_ratio,ag9_ratio,ag10_ratio \n");
				sb.append(
						", next_com_ratio, next_sc_ratio, next_bc_ratio, next_co_ratio, next_sa_ratio ,next_ag_ratio,next_ag1_ratio,next_ag2_ratio,next_ag3_ratio,next_ag4_ratio,next_ag5_ratio,next_ag6_ratio \n");
				sb.append(",next_ag7_ratio ,next_ag8_ratio,next_ag9_ratio,next_ag10_ratio FROM ctt_manager.ctt_member_total_ratio where game_type ="
						+ LOTTERY + ") I1  \n");
				sb.append("on A.acc_id=I1.acc_id  \n");
				sb.append("LEFT OUTER JOIN  \n");
				sb.append(
						"(SELECT acc_id, com_ratio, sc_ratio, bc_ratio, co_ratio, sa_ratio ,ag_ratio,ag1_ratio,ag2_ratio,ag3_ratio,ag4_ratio,ag5_ratio,ag6_ratio,ag7_ratio,ag8_ratio,ag9_ratio,ag10_ratio \n");
				sb.append(
						", next_com_ratio, next_sc_ratio, next_bc_ratio, next_co_ratio, next_sa_ratio ,next_ag_ratio,next_ag1_ratio,next_ag2_ratio,next_ag3_ratio,next_ag4_ratio,next_ag5_ratio,next_ag6_ratio \n");
				sb.append(",next_ag7_ratio ,next_ag8_ratio,next_ag9_ratio,next_ag10_ratio FROM ctt_manager.ctt_member_total_ratio where game_type ="
						+ LIVEVIDEO + ") I2  \n");
				sb.append("on A.acc_id=I2.acc_id  \n");
				sb.append("LEFT OUTER JOIN  \n");
				sb.append(
						"(SELECT acc_id, com_ratio, sc_ratio, bc_ratio, co_ratio, sa_ratio ,ag_ratio,ag1_ratio,ag2_ratio,ag3_ratio,ag4_ratio,ag5_ratio,ag6_ratio,ag7_ratio,ag8_ratio,ag9_ratio,ag10_ratio \n");
				sb.append(
						", next_com_ratio, next_sc_ratio, next_bc_ratio, next_co_ratio, next_sa_ratio ,next_ag_ratio,next_ag1_ratio,next_ag2_ratio,next_ag3_ratio,next_ag4_ratio,next_ag5_ratio,next_ag6_ratio \n");
				sb.append(",next_ag7_ratio ,next_ag8_ratio,next_ag9_ratio,next_ag10_ratio FROM ctt_manager.ctt_member_total_ratio where game_type ="
						+ MOVEMENT + ") I3  \n");
				sb.append("on A.acc_id=I3.acc_id  \n");
				sb.append("LEFT OUTER JOIN  \n");
				sb.append(
						"(SELECT acc_id, com_ratio, sc_ratio, bc_ratio, co_ratio, sa_ratio ,ag_ratio,ag1_ratio,ag2_ratio,ag3_ratio,ag4_ratio,ag5_ratio,ag6_ratio,ag7_ratio,ag8_ratio,ag9_ratio,ag10_ratio \n");
				sb.append(
						", next_com_ratio, next_sc_ratio, next_bc_ratio, next_co_ratio, next_sa_ratio ,next_ag_ratio,next_ag1_ratio,next_ag2_ratio,next_ag3_ratio,next_ag4_ratio,next_ag5_ratio,next_ag6_ratio \n");
				sb.append(",next_ag7_ratio ,next_ag8_ratio,next_ag9_ratio,next_ag10_ratio FROM ctt_manager.ctt_member_total_ratio where game_type ="
						+ VIDEOGAME + ") I4  \n");
				sb.append("on A.acc_id=I4.acc_id  \n");
				sb.append("LEFT OUTER JOIN  \n");
				sb.append(
						"(SELECT acc_id, com_ratio, sc_ratio, bc_ratio, co_ratio, sa_ratio ,ag_ratio,ag1_ratio,ag2_ratio,ag3_ratio,ag4_ratio,ag5_ratio,ag6_ratio,ag7_ratio,ag8_ratio,ag9_ratio,ag10_ratio \n");
				sb.append(
						", next_com_ratio, next_sc_ratio, next_bc_ratio, next_co_ratio, next_sa_ratio ,next_ag_ratio,next_ag1_ratio,next_ag2_ratio,next_ag3_ratio,next_ag4_ratio,next_ag5_ratio,next_ag6_ratio \n");
				sb.append(",next_ag7_ratio ,next_ag8_ratio,next_ag9_ratio,next_ag10_ratio FROM ctt_manager.ctt_member_total_ratio where game_type ="
						+ GAME + ") I5  \n");
				sb.append("on A.acc_id=I5.acc_id  \n");
				sb.append("LEFT JOIN  \n");
				sb.append("(select s1.acc_id ,(case when (select acc_status from ctt_manager.ctt_manager_acc_level  \n");
				sb.append(
						"where acc_id = (case s1.upper_acc_level_type when " + ACC_LEVEL_SC + " then s1.sc when " + ACC_LEVEL_BC + " then s1.bc when "
								+ ACC_LEVEL_CO + " then s1.co when " + ACC_LEVEL_SA + " then s1.sa when " + ACC_LEVEL_AG + " then s1.ag when "
								+ ACC_LEVEL_AG1 + " then s1.ag1 when " + ACC_LEVEL_AG2 + " then s1.ag2 when " + ACC_LEVEL_AG3 + " then s1.ag3  \n");
				sb.append("when " + ACC_LEVEL_AG4 + " then s1.ag4 when " + ACC_LEVEL_AG5 + " then s1.ag5 when " + ACC_LEVEL_AG6 + " then s1.ag6 when "
						+ ACC_LEVEL_AG7 + " then s1.ag7  when " + ACC_LEVEL_AG8 + " then s1.ag8 when " + ACC_LEVEL_AG9 + " then s1.ag9 when "
						+ ACC_LEVEL_AG10 + " then s1.ag10 else 0 end)) < 3 then true else false end) as update_status , s1.acc_status   \n");
				sb.append("from ctt_manager.ctt_member_acc_level s1) K   \n");
				sb.append("ON A.acc_id = K.acc_id \n");
				sb.append("ORDER BY " + orderBy[sort] + " " + ASC[asc] + " \n");
				
				LOG.debug("end1----->>>>>"+new Date().getTime());

				list = StmtUtil.queryToBean(READ_CONN, sb.toString(), params, new ManagerAcciunyInfos());
				
				LOG.debug("end2----->>>>>"+new Date().getTime());
				return list;
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return null;
	}

	@Override
	public int searchTotalCountMEM(int searchLevel, long userId, long upAccId, String accName, int memType, int status, boolean checkStatus) {
		StringBuilder sb = null;
		List<Object> params = null;
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		int totalCount = 0;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("NO CONN");
				return totalCount;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();
				if (upAccId <= 0) {
					upAccId = userId;
				}
				sb.append("SELECT COUNT(acc_id) as count FROM ctt_manager.ctt_member_acc_level  \n");
				sb.append("WHERE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id=?)<" + ACC_LEVEL_MEM + "  \n");
				params.add(userId);
				sb.append(
						"AND (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) <=  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				params.add(userId);
				params.add(upAccId);
				sb.append("AND (CASE  (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?)  \n");
				params.add(upAccId);
				sb.append("WHEN 0 THEN TRUE  \n");
				sb.append("WHEN 1 THEN TRUE  \n");
				for (int i = 0; i < ACC_LEVEL_MAG.length; i++) {
					sb.append("WHEN " + ACC_LEVEL_MAG[i] + " THEN " + ACC_LEVEL_MAG_TABLE_NAME[i] + " =? \n");
					params.add(upAccId);
					if (memType == DIRECTLY_UNDER_MEM) {
						sb.append("AND upper_acc_level_type = " + ACC_LEVEL_MAG[i] + " \n");
					}
				}

				sb.append("ELSE FALSE END) \n");
				if (!"".equals(accName)) {
					sb.append("AND acc_id = (select acc_id from ctt_manager.ctt_member_acc where acc_name = ? ) \n");
					params.add(accName);
				}
				if (searchLevel > ACC_LEVEL_COM) {
					if (memType == DIRECTLY_UNDER_MEM) {
						sb.append("AND upper_acc_level_type = ?  \n");
						params.add(searchLevel);
					} else {
						sb.append("AND upper_acc_level_type >= ?  \n");
						params.add(searchLevel);
					}

				}
				if (checkStatus) {
					sb.append("AND acc_status=?  \n");
					params.add(status);
				}

				listMap = StmtUtil.queryToMap(READ_CONN, sb.toString(), params);

				if (listMap.size() == 1) {
					if (listMap.get(0).containsKey("count")) {
						totalCount = Integer.parseInt(listMap.get(0).get("count").toString());
					}
				}
				LOG.debug(totalCount);

				return totalCount;
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}

		}

		return totalCount;
	}
	
	
	@Override
	public boolean updateMagHandicap(long accId , int handicap) {
		List<Object> updateList = null;
		StringBuilder sb = null;
		boolean todo = false;

		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				updateList = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("	 update ctt_manager.ctt_manager_acc set handicap = handicap&? where acc_id IN (select acc_id from ctt_manager.ctt_manager_acc_level 	\n");
				updateList.add(handicap);
				sb.append("	 where case (select acc_level_type from ctt_manager.ctt_manager_acc_level where acc_id = ?)	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_SC+" then sc=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_BC+" then bc=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_CO+" then co=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_SA+" then sa=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG+" then ag=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG1+" then ag1=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG2+" then ag2=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG3+" then ag3=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG4+" then ag4=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG5+" then ag5=? 	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG6+" then ag6=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG7+" then ag7=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG8+" then ag8=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG9+" then ag9=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG10+" then ag10=? else false end) \n");
				updateList.add(accId);

				int cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateList);
				LOG.debug("cou" + cou);
				
				todo = true;
				
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("updateGameServer_Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateList != null) {
				updateList.clear();
				updateList = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return todo;
	}
	
	@Override
	public boolean updateMemHandicap(long accId , int handicap) {
		List<Object> updateList = null;
		StringBuilder sb = null;
		boolean todo = false;

		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				updateList = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("	 update ctt_manager.ctt_member_acc set handicap = handicap&? where acc_id IN (select acc_id from ctt_manager.ctt_member_acc_level 	\n");
				updateList.add(handicap);
				sb.append("	 where case (select acc_level_type from ctt_manager.ctt_manager_acc_level where acc_id = ?)	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_SC+" then sc=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_BC+" then bc=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_CO+" then co=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_SA+" then sa=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG+" then ag=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG1+" then ag1=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG2+" then ag2=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG3+" then ag3=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG4+" then ag4=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG5+" then ag5=? 	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG6+" then ag6=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG7+" then ag7=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG8+" then ag8=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG9+" then ag9=?	\n");
				updateList.add(accId);
				sb.append("	 when "+ACC_LEVEL_AG10+" then ag10=? else false end) 	\n");
				updateList.add(accId);


				int cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateList);
				LOG.debug("cou" + cou);
				
				todo = true;
				
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("updateGameServer_Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (updateList != null) {
				updateList.clear();
				updateList = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return todo;
	}

}
