package tw.com.ctt.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IGameDao;
import tw.com.ctt.model.ManageAccountInfo;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

import tw.com.ctt.model.PlayerInfoBean;

public class GameDaoImpl extends BaseDao implements IGameDao {
	
	private static final long serialVersionUID = -4046186702438639069L;
	private static final Logger LOG = LogManager.getLogger(GameDaoImpl.class.getName());
	public static final String[] orderBy = { "A.acc_id", "C.nickname", "C.acc_name", "D.create_time", "E.ip",
			"E.last_login_time" };// 1-5,default:0
	public static final String[] ASC = { "ASC", "DESC" };// 0-1
	public static final int timeoutMin = 30;

	/**
	 * check player's pwd.
	 */
	@Override
	public Map<String, String> checkPwd(String userName, String pwd) {
		Map<String, String> result = new ConcurrentHashMap<String, String>();
		result.put("result", "fail");
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {			
			sb.append("select COUNT(*) as count, A.acc_name, A.acc_id, A.nickname, B.acc_level_type from ( \n");
			sb.append("( \n");
			sb.append("SELECT  acc_name, acc_id, nickname FROM ctt_manager.ctt_manager_acc WHERE LOWER(acc_name) = LOWER( ? ) AND pwd = ? \n");
			sb.append(")A \n");
			sb.append("left join \n");
			sb.append(" ( \n");
			sb.append(" select acc_id, acc_level_type from ctt_manager.ctt_manager_acc_level \n");
			sb.append(" )B  \n");
			sb.append(" on A.acc_id=B.acc_id \n");
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
				if (rs.next() && rs.getInt("count")>0) {
					result.put("result", "true");
					result.put("count", "" + rs.getInt("count"));
					result.put("acc_name", rs.getString("acc_name"));
					result.put("acc_id", "" + rs.getInt("acc_id"));
					result.put("nickname", rs.getString("nickname"));
					result.put("acc_level_type", rs.getString("acc_level_type"));
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

	/**
	 * get basic info of player.
	 */
	@Override
	public List<Map<String, Object>> getPlayer(long accId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {			
			sb.append(" select acc_id, acc_name, nickname, member_type, balance from `ctt_manager`.`ctt_member_acc`  where acc_id= ?  \n");

			this.checkRead();
			
			List<Object> list = new ArrayList<Object>();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return result;
			} else {
//				LOG.debug("sql:\n" + sb.toString());
				List<Object> params = new ArrayList<Object>();
				params.add(accId);			
				list = StmtUtil.queryToBean(READ_CONN, sb.toString(), params, new PlayerInfoBean());
				for(int i =0;i < list.size();i++){
					PlayerInfoBean tmpAuthInfoBean = (PlayerInfoBean)list.get(i);
						Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
						tmpMap.put("accId", tmpAuthInfoBean.getAccId());
						tmpMap.put("nickname", tmpAuthInfoBean.getNickname());
						tmpMap.put("accName", tmpAuthInfoBean.getAccName());
						tmpMap.put("balance", tmpAuthInfoBean.getBalance());
						List<Map<String, Object>> innerList = new ArrayList<Map<String, Object>>();
						tmpMap.put("list", innerList);
						result.add(tmpMap);
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
	
	/**
	 * update role info, when the player select another role.
	 */
	@Override
	public boolean updateRolePuncheGameAccInfo(long accId, String role)  throws SQLException {
		StringBuilder sb = new StringBuilder();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {				
				List<Object> insertMap = new ArrayList<Object>();
				sb.append("update `ctt_manager`.`ctt_member_punch_game_acc_info` set role = ? where acc_id = ?");
//				LOG.debug("6");
				insertMap.add(role);
				insertMap.add(accId);
				
//				LOG.debug("7" +insertMap.toString());

				try {
					if (this.WRITE_CONN.getAutoCommit()) {
						this.WRITE_CONN.setAutoCommit(false);
					}
					
					StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), insertMap);
					
					this.WRITE_CONN.commit();
					if (!this.WRITE_CONN.getAutoCommit()) {
						this.WRITE_CONN.setAutoCommit(true);
					}
					return true;
				} catch (SQLException e) {
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} 
		return false;
	}
	
	/**
	 * getPlayerRole
	 */
	@Override
	public List<Map<String, Object>> getPlayerRole(long accId)  throws SQLException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {			
			sb.append(" select role from `ctt_manager`.`ctt_member_punch_game_acc_info`  where acc_id= ?  \n");

			this.checkRead();
			
			List<Object> list = new ArrayList<Object>();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return result;
			} else {
				ps = this.READ_CONN.prepareStatement(sb.toString());
				ps.setLong(1, (Long) accId);
				rs = ps.executeQuery();
				
				while (rs.next()) {					
					Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
					tmpMap.put("role",rs.getString(1) );
					result.add(tmpMap);
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

}
