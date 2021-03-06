package tw.com.ctt.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.ICheckPwdDao;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

import tw.com.ctt.model.EachAuthInfoBean;
import static tw.com.ctt.constant.AccountManagerConstant.*;

public class CheckPwdDaoImpl extends BaseDao implements ICheckPwdDao {

	private static final long serialVersionUID = -4046186702438639069L;
	private static final Logger LOG = LogManager.getLogger(CheckPwdDaoImpl.class.getName());
	public static final String[] orderBy = { "A.acc_id", "C.nickname", "C.acc_name", "D.create_time", "E.ip",
			"E.last_login_time" };// 1-5,default:0
	public static final String[] ASC = { "ASC", "DESC" };// 0-1
	public static final int timeoutMin = 30;

	public CheckPwdDaoImpl() {
		super();
	}
	@Override
	public Map<String, String> checkPwd(String userName, String pwd) {
		Map<String, String> result = new ConcurrentHashMap<String, String>();
		result.put("result", "fail");
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sb.append("SELECT COUNT(*) AS count, A.acc_name, A.acc_id, A.nickname, B.acc_level_type FROM ( \n");
			sb.append("(SELECT  acc_name, acc_id, nickname FROM ctt_manager.ctt_manager_acc WHERE acc_name = ? AND pwd = ?)A \n");
			sb.append("LEFT JOIN \n");
			sb.append("(SELECT acc_id, acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE (acc_status = "+STATUS_ENABLED+" OR acc_status = "+STATUS_DISABLED+"))B \n");
			sb.append("ON A.acc_id=B.acc_id \n");
			sb.append(") \n");

			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return result;
			} else {
				LOG.debug("sql:\n" + sb.toString());
				LOG.debug("param:\n" + userName + ", " + pwd);
				ps = this.READ_CONN.prepareStatement(sb.toString());
				ps.setString(1, userName);
				ps.setString(2, pwd);
				rs = ps.executeQuery();
				if (rs.next() && rs.getInt("count") > 0) {
					result.put("result", "true");
					result.put("count", "" + rs.getInt("count"));
					result.put("acc_name", rs.getString("acc_name"));
					result.put("acc_id", "" + rs.getInt("acc_id"));
					result.put("nickname", rs.getString("nickname"));
					if(rs.getString("acc_level_type") != null) {
						result.put("acc_level_type", rs.getString("acc_level_type"));
					}else {
						result.put("acc_level_type", "");
					}
					
				}
				return result;
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException, " + e.getMessage());
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
					LOG.info("SQLException, " + e.getMessage());
					ShowLog.err(LOG, e);
				}
				rs = null;
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getAuth(String accId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// new ConcurrentHashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			sb.append(" select auth_id,auth_name,auth_level_type,url from( \n");
			sb.append(
					" SELECT sort_id*65536 AS sort_id,auth_id,auth_name,auth_remark,level1_id,level2_id,auth_level_type,url FROM `ctt_manager`.`ctt_auth`  \n");
			sb.append(
					"  WHERE auth_level_type=1  AND (auth_id IN (select auth_id from `ctt_manager`.`ctt_auth_list` where (group_id = (select auth_group from `ctt_manager`.`ctt_manager_acc` where acc_id = ? ))) || (select case acc_level_type when 0 then true else false end from `ctt_manager`.`ctt_manager_acc_level` where acc_id = ?) ) \n");
			sb.append("   UNION ALL      \n");
			sb.append(
					"  select sort_id_T*256 + (SELECT sort_id*65536 from `ctt_manager`.`ctt_auth` where auth_id=B.level1_id_T) as sort_id , auth_id,auth_name,auth_remark,level1_id_T as level1_id ,level2_id_T as level2_id ,auth_level_type,url  \n");
			sb.append(
					"  from(SELECT sort_id AS sort_id_T,auth_id,auth_name,auth_remark,level1_id as level1_id_T,level2_id as level2_id_T,auth_level_type,url FROM `ctt_manager`.`ctt_auth`  \n");
			sb.append(
					" 	WHERE auth_level_type=2 AND (auth_id IN (select auth_id from `ctt_manager`.`ctt_auth_list` where (group_id = (select auth_group from `ctt_manager`.`ctt_manager_acc` where acc_id = ? ))) || (select case acc_level_type when 0 then true else false end from `ctt_manager`.`ctt_manager_acc_level` where acc_id = ?) ) \n");
			sb.append(" ) B     \n");
			sb.append(" )D  order by sort_id ; \n");

			this.checkRead();

			List<Object> list = new ArrayList<Object>();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return result;
			} else {
				LOG.debug("sql:\n" + sb.toString());
				List<Object> params = new ArrayList<Object>();
				params.add(accId);
				params.add(accId);
				params.add(accId);
				params.add(accId);
				list = StmtUtil.queryToBean(READ_CONN, sb.toString(), params, new EachAuthInfoBean());
				int firstLayerCount = 0;
				for (int i = 0; i < list.size(); i++) {
					EachAuthInfoBean tmpAuthInfoBean = (EachAuthInfoBean) list.get(i);
					if (tmpAuthInfoBean.getAuthLevelType() == 1) {
						Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
						tmpMap.put("authId", tmpAuthInfoBean.getAuthId());
						tmpMap.put("authLevelType", tmpAuthInfoBean.getAuthLevelType());
						tmpMap.put("authName", tmpAuthInfoBean.getAuthName());
						tmpMap.put("url", tmpAuthInfoBean.getUrl());
						List<Map<String, Object>> innerList = new ArrayList<Map<String, Object>>();
						tmpMap.put("list", innerList);
						result.add(tmpMap);
						firstLayerCount++;
					} else if (((EachAuthInfoBean) list.get(i)).getAuthLevelType() == 2) {
						List<Map<String, Object>> firstLayerList = (List<Map<String, Object>>) (result
								.get(firstLayerCount - 1).get("list"));
						Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
						tmpMap.put("authId", tmpAuthInfoBean.getAuthId());
						tmpMap.put("authLevelType", tmpAuthInfoBean.getAuthLevelType());
						tmpMap.put("authName", tmpAuthInfoBean.getAuthName());
						tmpMap.put("url", tmpAuthInfoBean.getUrl());
						firstLayerList.add(tmpMap);
					}
				}
				return result;
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (rs != null) {
				try {
					if (!rs.isClosed()) {
						rs.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException, " + e.getMessage());
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
					LOG.info("SQLException, " + e.getMessage());
					ShowLog.err(LOG, e);
				}
				rs = null;
			}
		}
		return result;
	}

	@Override
	public boolean updateLastLogin(String tableName, int userId, String ip) {
		List<Object> lisObj = null;
		StringBuilder sb =null;
		int count = 0;
		ip = (ip == null || ip.isEmpty() || "".equals(ip.trim())) ? "0.0.0.0" : ip;
		String[] ipArray = ip.split("\\.");
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				lisObj = new ArrayList<Object>();
				 sb = new StringBuilder();
				sb.append("UPDATE " + tableName
						+ " SET last_login_datetime = CURRENT_TIMESTAMP,ip1=?,ip2=?,ip3=?,ip4=? WHERE acc_id=?");
		
				lisObj.add(Integer.parseInt(ipArray[0]));
				lisObj.add(Integer.parseInt(ipArray[1]));
				lisObj.add(Integer.parseInt(ipArray[2]));
				lisObj.add(Integer.parseInt(ipArray[3]));
				lisObj.add(userId);
				
				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), lisObj);
				if (count != 0) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if(sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if(lisObj != null) {
				lisObj.clear();
				lisObj = null;
			}
		}
		return false;
	}

}
