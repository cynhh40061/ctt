package tw.com.ctt.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IPortionAuthDao;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class PortionAuthDaoImpl extends BaseDao implements IPortionAuthDao{

	private static final long serialVersionUID = -2957661922178019489L;
	private static final Logger LOG = LogManager.getLogger(CheckPwdDaoImpl.class.getName());

	
	@Override
	public boolean getAuthFunction(long accId, String url ,int acc_level_type) {
		StringBuilder sb = new StringBuilder();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("ON CONN");
				return false;
			} else {
				if(acc_level_type == 0) {
					sb.append("select COUNT(1) as cou from ctt_manager.ctt_auth where url = ? and (select acc_level_type from ctt_manager.ctt_manager_acc_level where acc_id = ? ) = 0 \n");
					ps = this.READ_CONN.prepareStatement(sb.toString());
					ps.setString(1, url);
					ps.setLong(2, accId);
				}
				else {
					sb.append("select COUNT(1) as cou from \n");
					sb.append("(select auth_id from ctt_manager.ctt_auth_list  \n");
					sb.append("where group_id = (select auth_group from `ctt_manager`.`ctt_manager_acc` where acc_id = ? ) and url = ?) A \n");
					sb.append("inner join \n");
					sb.append("(select auth_id from ctt_manager.ctt_auth) B \n");
					sb.append("on A.auth_id = B.auth_id \n");
					
					ps = this.READ_CONN.prepareStatement(sb.toString());
					ps.setLong(1, accId);
					ps.setString(2, url);
				}
				
				
				LOG.debug(accId+","+url);
				
				LOG.debug(ps.toString());
				
				rs = ps.executeQuery();
				
				if (rs.next()) {
					if(rs.getInt("cou") == 1) {
						return true;
					}
				}
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
		return false;
	}
	
	
	@Override
	public List<Map<String ,Object>> getLevel3Auth(String url){
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("ON CONN");
				return result;
			} else {
				sb.append("select B.url,B.auth_name  from \n" );
				sb.append("(select auth_id from ctt_manager.ctt_auth  where url = ? and auth_level_type = 2) A \n" ); 
				sb.append("inner join  \n" ); 
				sb.append("(select auth_level_type,level2_id,url,auth_name from ctt_manager.ctt_auth ) B \n");
				sb.append("on A.auth_id = B.level2_id  and B.auth_level_type = 3");
				params.add(url);
				result = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				LOG.debug(result);
			}
		}catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			this.closeRead();
			if(sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if(params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
			
		}
		return result;
	}

}
