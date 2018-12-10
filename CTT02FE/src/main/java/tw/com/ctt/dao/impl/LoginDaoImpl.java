package tw.com.ctt.dao.impl;

import static tw.com.ctt.constant.AccountMemberConstant.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.ILoginDao;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class LoginDaoImpl extends BaseDao implements ILoginDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4681956633190416152L;
	private static final Logger LOG = LogManager.getLogger(LoginDaoImpl.class.getName());
	private static final int LOGIN_FAILED_COUNT = 10;
	private static final int PWD_WITHDRAW_FAILED_COUNT = 5;

	@Override
	public Map<String, Object> checkPwd(String accName, String pwd, int platformId) {
		StringBuilder sb = null;
		Map<String, Object> map = null;
		List<Object> selectList = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return map;
			} else {
				
				map = new ConcurrentHashMap<String, Object>();
				selectList = new ArrayList<Object>();
				sb = new StringBuilder();
				

				sb.append("SELECT A.acc_id , A.pwd_status , CASE WHEN B.member_type = "+GRNERAL_MEM+" THEN A.pwd_withdraw_status ELSE FALSE END as pwd_withdraw_status , A.isLoginFailed , A.login_failed , A.isPwdWithdrawFailed , B.isLogin FROM \n");
				sb.append("(SELECT acc_id ,pwd_status , pwd_withdraw_status , case when login_failed <= "+LOGIN_FAILED_COUNT+" then true else false end as isLoginFailed , login_failed \n");
				sb.append(",case when pwd_withdraw_failed <= "+PWD_WITHDRAW_FAILED_COUNT+" then true else false end as isPwdWithdrawFailed \n");
				sb.append("FROM \n");
				sb.append(" ctt_manager.ctt_member_acc WHERE acc_name = ? AND acc_id IN (SELECT acc_id from ctt_manager.ctt_member_acc_level \n");
				sb.append("WHERE sc = (select platform_sc_id from ctt_manager.ctt_platform WHERE platform_id = ?))) A \n");
				sb.append("INNER JOIN \n");
				sb.append("(SELECT acc_id ,member_type , CASE WHEN pwd = ? THEN TRUE ELSE FALSE end AS isLogin FROM ctt_manager.ctt_member_acc ) B \n");
				sb.append("ON A.acc_id = B.acc_id \n");
				
				selectList.add(accName);
				selectList.add(platformId);
				selectList.add(pwd);
				
				List<Map<String ,Object>> listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectList);
				
				for(int i = 0 ; i < listMap.size() ; i++) {
					for(Object key : listMap.get(i).keySet()) {
						map.put(key.toString(), listMap.get(i).get(key.toString()));
					}
				}
				
				LOG.debug(map.toString());

				return map;
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			
			if(selectList != null) {
				selectList.clear();
				selectList = null;
			}

		}

		return map;
	}

	@Override
	public boolean addLoginFailCount(long accId, int platformId,boolean isLogin) {
		List<Object> updateList = null;
		StringBuilder sb = null;
		boolean todo = false;
		int count = 0;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				updateList = new ArrayList<Object>();
				sb = new StringBuilder();

	
				sb.append("update ctt_manager.ctt_member_acc set \n");
				if(isLogin) {
					sb.append(" login_failed = 0 ");
				}
				else {
					sb.append(" login_failed = login_failed+1 ");
				}
				sb.append(
						"where acc_id = (select acc_id from (select acc_id from ctt_manager.ctt_member_acc_level WHERE sc = (select platform_sc_id from ctt_manager.ctt_platform WHERE platform_id = ?) \n");
				sb.append(
						"and  acc_id = ? )A)\n");

				updateList.add(platformId);
				updateList.add(accId);
				
				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateList);
				LOG.debug(count);
				
				todo = true;
				
			}
		} catch (Exception e) {
			todo = false;

			LOG.info("Exception, " + e.getMessage());
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
	public boolean updateLastLogin(String tableName, long userId, String ip) {
		PreparedStatement ps = null;
		ip = (ip == null || ip.isEmpty() || "".equals(ip.trim())) ? "0.0.0.0" : ip;
		String[] ipArray = ip.split("\\.");
		int count = 0;
		StringBuilder sb = new StringBuilder();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb.append("UPDATE " + tableName
						+ " SET last_login_datetime = now(),ip1=?,ip2=?,ip3=?,ip4=? WHERE acc_id=?");
				this.WRITE_CONN.setAutoCommit(false);
				ps = this.WRITE_CONN.prepareStatement(sb.toString());
				ps.setInt(1, Integer.parseInt(ipArray[0]));
				ps.setInt(2, Integer.parseInt(ipArray[1]));
				ps.setInt(3, Integer.parseInt(ipArray[2]));
				ps.setInt(4, Integer.parseInt(ipArray[3]));
				ps.setLong(5, userId);

				count = ps.executeUpdate();
				if (count != 0) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (ps != null) {
				try {
					if (!ps.isClosed()) {
						ps.close();
					}
				} catch (SQLException e) {
					LOG.info("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
				} finally {
					ps = null;
				}
			}
		}
		return false;
	}
	
	public List<Map<String ,Object>> getPlatformAd(int platformId){
		StringBuilder sb = null;
		List<Map<String ,Object>> listMap = null;
		List<Object> selectList = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String , Object>>();
				selectList = new ArrayList<Object>();
				sb = new StringBuilder();
				
				sb.append("SELECT ad_position_code AS adPosition , ad_src as adSrc , ad_url AS adUrl FROM ctt_manager.ctt_advertising where platform_id = ?");
				
				selectList.add(platformId);
				
				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectList);
				
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			
			if(selectList != null) {
				selectList.clear();
				selectList = null;
			}

		}
		return listMap;
	}

}
