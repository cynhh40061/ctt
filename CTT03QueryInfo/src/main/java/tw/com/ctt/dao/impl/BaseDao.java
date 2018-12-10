package tw.com.ctt.dao.impl;

import static tw.com.ctt.constant.LogToDBConstant.ACC_TYPE_MANAGER;
import static tw.com.ctt.constant.LogToDBConstant.ACC_TYPE_MEMBER;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.constant.CommandConstant;
import tw.com.ctt.dao.IBaseDao;
import tw.com.ctt.util.AESUtil;
import tw.com.ctt.util.DataSource;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class BaseDao implements Serializable, IBaseDao {

	private static final long serialVersionUID = 8876277291887765588L;
	private static final Logger LOG = LogManager.getLogger(BaseDao.class.getName());
	private static final int TOKEN_ONLINE_TIME_MINUTE = 30;
	private static final int TOKEN_FORCED_LOGOUT_TIME_DAY = 1;
	public final long USERID;
	public final String USERIP;
	protected Connection READ_CONN;
	protected Connection WRITE_CONN;

	public BaseDao() {
		super();
		this.USERID = 0;
		this.USERIP = "";
		checkRead();
		checkWrite();
	}

	public BaseDao(long userId, String userIp) {
		super();
		this.USERID = userId;
		this.USERIP = userIp;
		checkRead();
		checkWrite();
	}

	/**
	 * get read connection
	 */
	public void checkRead() {
		try {
			while (READ_CONN == null || READ_CONN.isClosed()) {
				try {
					READ_CONN = DataSource.getReadConnection();
				} catch (Exception e) {
					closeRead();
				}
				Thread.sleep(100);
			}
			if (READ_CONN.getTransactionIsolation() != Connection.TRANSACTION_READ_COMMITTED) {
				READ_CONN.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			}
		} catch (SQLException e) {
			ShowLog.err(LOG, e);
			LOG.error("checkWriteConn(): no connection");
		} catch (Exception e) {
			ShowLog.err(LOG, e);
			LOG.error("Exception");
		}
	}

	/**
	 * get write connection
	 */
	public void checkWrite() {
		try {
			while (WRITE_CONN == null || WRITE_CONN.isClosed()) {
				try {
					WRITE_CONN = DataSource.getWriteConnection();
				} catch (Exception e) {
					closeWrite();
				}
				Thread.sleep(100);
			}
			if (WRITE_CONN.getTransactionIsolation() != Connection.TRANSACTION_READ_COMMITTED) {
				WRITE_CONN.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			}

		} catch (SQLException e) {
			ShowLog.err(LOG, e);
			LOG.error("checkWriteConn(): no connection");
		} catch (Exception e) {
			ShowLog.err(LOG, e);
			LOG.error("Exception");
		}
	}

	/**
	 * return to read connection pool
	 */
	public void closeRead() {
		if (READ_CONN != null) {
			try {
				if (!READ_CONN.getAutoCommit())
					READ_CONN.setAutoCommit(true);
				if (!READ_CONN.isClosed()) {
					DataSource.returnReadConnection(READ_CONN);
				}
			} catch (SQLException e) {
				ShowLog.err(LOG, e);
				LOG.error("SQLException:" + e.getMessage());
			} finally {
				READ_CONN = null;
			}
		}
	}

	/**
	 * return to write connection pool
	 */
	public void closeWrite() {
		if (WRITE_CONN != null) {
			try {
				if (!WRITE_CONN.getAutoCommit())
					WRITE_CONN.setAutoCommit(true);
				if (!WRITE_CONN.isClosed()) {
					DataSource.returnWriteConnection(WRITE_CONN);
				}
			} catch (SQLException e) {
				ShowLog.err(LOG, e);
				LOG.error("SQLException:" + e.getMessage());
			} finally {
				WRITE_CONN = null;
			}
		}
	}

	/**
	 * return to read & write connection pool
	 */
	public void close() {
		closeRead();
		closeWrite();
	}

	@Override
	public boolean setLogToDBNoCommit(String tableName, long opsId, long accId, long dataId, int opsType, int accType,
			int action, String data, String ip) {
		String[] ipArray = null;
		StringBuilder sb = null;
		List<Object> param = null;
		long count = 0;
		boolean todo = false;
		String opsTableName = null;
		String accTableName = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("log no connect");
				return todo;
			} else {
				ipArray = toIP(ip);
				sb = new StringBuilder();
				param = new ArrayList<Object>();

				if (opsType == ACC_TYPE_MANAGER) {
					opsTableName = "manager";
				} else if (opsType == ACC_TYPE_MEMBER) {
					opsTableName = "member";
				}

				if (accType == ACC_TYPE_MANAGER) {
					accTableName = "manager";
				} else if (accType == ACC_TYPE_MEMBER) {
					accTableName = "member";
				}

				sb.append("INSERT INTO " + tableName + " \n");
				sb.append("(ops_acc_id,acc_id, \n");
				sb.append("ops_acc_name, \n");
				sb.append("acc_name, \n");
				sb.append("data_id,ops_type,action,detail,ip1,ip2,ip3,ip4,ops_datetime) \n");
				sb.append("VALUES \n");
				sb.append("(?,?, \n");
				param.add(opsId);
				param.add(accId);
				sb.append("(SELECT acc_name FROM ctt_manager.ctt_" + opsTableName + "_acc WHERE acc_id = ?), \n");
				param.add(opsId);
				if (accId == dataId && dataId > 0) {
					sb.append("(SELECT game_server_id FROM ctt_manager.ctt_manager_game_set WHERE sid = ?), \n");
					param.add(dataId);
				} else {
					sb.append("(SELECT acc_name FROM ctt_manager.ctt_" + accTableName + "_acc WHERE acc_id = ?), \n");
					param.add(accId);
				}
				sb.append("?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP) \n");
				param.add(dataId);
				param.add(opsType);
				param.add(action);
				param.add(data);
				param.add(Integer.parseInt(ipArray[0]));
				param.add(Integer.parseInt(ipArray[1]));
				param.add(Integer.parseInt(ipArray[2]));
				param.add(Integer.parseInt(ipArray[3]));

				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), param);
				if (count != 0) {
					todo = true;
				}
				LOG.debug(count);
			}
		} catch (SQLException e) {
			todo = false;
			LOG.debug("SQLException, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			ipArray = null;
			count = 0;
			accTableName = "";
			accTableName = null;
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (param != null) {
				param.clear();
				param = null;
			}
		}
		return todo;
	}

	@Override
	public boolean addToken(String tableName, long accId) {
		StringBuilder sb = null;
		List<Object> insertObj = null;
		boolean resuleBoolean = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("NO CONN");
				return resuleBoolean;
			} else {
				sb = new StringBuilder();
				insertObj = new ArrayList<Object>();

				sb.append("INSERT into " + tableName
						+ "(acc_id , token_id, online_datetime, forced_logout_datetime) VALUES(?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)");
				insertObj.add(accId);
				insertObj.add(produceTokenId(accId));

				long cou = StmtUtil.insertNoCommit(this.WRITE_CONN, sb.toString(), insertObj);

				if (cou != 0) {
					resuleBoolean = true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			resuleBoolean = false;
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertObj != null) {
				insertObj.clear();
				insertObj = null;
			}

		}

		return resuleBoolean;
	}

	@Override
	public String getNewTokenId(String tableName, long accId) {
		StringBuilder sb = new StringBuilder();
		List<Object> insertObj = new ArrayList<Object>();
		String tokenId = "";
		String result = "";
		long updateCount = 0;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("NO CONN");
				return result;
			} else {
				sb.append("update " + tableName
						+ " set token_id = ?, online_datetime = DATE_ADD(CURRENT_TIMESTAMP,INTERVAL "
						+ TOKEN_ONLINE_TIME_MINUTE + " MINUTE)");
				sb.append(", forced_logout_datetime = DATE_ADD(CURRENT_TIMESTAMP,INTERVAL "
						+ TOKEN_FORCED_LOGOUT_TIME_DAY + " DAY)  where acc_id = ?");

				tokenId = produceTokenId(accId);
				insertObj.add(tokenId);
				insertObj.add(accId);
				updateCount = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), insertObj);

				if (updateCount > 0) {
					result = tokenId;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			result = "";
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertObj != null) {
				insertObj.clear();
				insertObj = null;
			}

		}

		return result;
	}

	@Override
	public boolean checkTokenTimeOut(String tableName, String tokenId, long accId) {
		LOG.debug("checkTokenTimeOut");
		StringBuilder sb = new StringBuilder();
		List<Object> selectObj = new ArrayList<Object>();
		boolean result = false;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("NO CONN");
				return result;
			} else {
				sb.append("select count(A.acc_id) as count from(select * from " + tableName + " where acc_id = ? )A ");
				sb.append(
						"where A.online_datetime > CURRENT_TIMESTAMP and A.forced_logout_datetime > CURRENT_TIMESTAMP and A.token_id= ?");

				selectObj.add(accId);
				selectObj.add(tokenId);
				List<Map<String, Object>> listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);

				LOG.debug("updateTokenId" + listMap.toString());

				if (listMap.size() == 1) {
					if (listMap.get(0).containsKey("count")) {
						if (Integer.parseInt(listMap.get(0).get("count").toString()) > 0) {
							result = true;
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			result = false;
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return result;
	}

	@Override
	public Map<String, String> checkTokenTimeOutMember(String tableName, String tokenId, long accId) {
		LOG.debug("checkTokenTimeOut");
		StringBuilder sb = new StringBuilder();
		List<Object> selectObj = new ArrayList<Object>();
		Map<String, String> result = new HashMap<String, String>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("NO CONN");
				return null;
			} else {
				sb.append(
						" 	select unix_timestamp(now()) as now_time, count(A.acc_id) as count, B.balance as balance from(	\n");
				sb.append(" 	select * from ctt_manager.ctt_member_token where acc_id = ? 	\n");
				sb.append(" 	)A	\n");
				sb.append(" 	left join(	\n");
				sb.append(" 	select balance, acc_id from ctt_manager.ctt_member_acc where acc_id = ? 	\n");
				sb.append(" 	)B	\n");
				sb.append(" 	on A.acc_id = B.acc_id	\n");
				sb.append(" 	where 1	\n");
				// sb.append(" where A.online_datetime > CURRENT_TIMESTAMP and
				// A.forced_logout_datetime > CURRENT_TIMESTAMP and A.token_id= ? \n");

				selectObj.add(accId);
				selectObj.add(accId);
				// selectObj.add(tokenId);
				List<Map<String, Object>> listMap = StmtUtil.queryToMap(this.WRITE_CONN, sb.toString(), selectObj);
				LOG.debug("updateTokenId" + listMap.toString());
				if (listMap.size() == 1) {
					result.put("balance", "" + listMap.get(0).get("balance"));
					result.put("tokenIdCheck", "" + listMap.get(0).get("count"));
					result.put("now", "" + listMap.get(0).get("nowTime"));

					if (result.get("balance") == null) {
						LOG.error(" " + tableName + " " + tokenId + " " + accId);
					}
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			result = null;
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return result;
	}

	@Override
	public boolean updateTokenTime(String tableName, String tokenId, long accId) {
		StringBuilder sb = new StringBuilder();
		List<Object> updateObj = new ArrayList<Object>();
		boolean result = false;
		long coun = 0;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("NO CONN");
				return result;
			} else {
				sb.append("update " + tableName
						+ " set online_datetime =case when token_id = ? then  DATE_ADD(CURRENT_TIMESTAMP,INTERVAL "
						+ TOKEN_ONLINE_TIME_MINUTE + " MINUTE) else online_datetime end ");
				sb.append(" where acc_id = ? ");
				updateObj.add(tokenId);
				updateObj.add(accId);

				coun = StmtUtil.update(this.WRITE_CONN, sb.toString(), updateObj);

				LOG.debug("updateTokenId=>" + coun);

				if (coun > 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			result = false;
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}

		}

		return result;
	}

	@Override
	public boolean colosMemTokenId(long accId, String tokenId) {
		StringBuilder sb = new StringBuilder();
		List<Object> updateObj = new ArrayList<Object>();
		int coun = 0;
		boolean result = false;
		try {

			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("NO CONN");
				return result;
			} else {
				sb.append("UPDATE ctt_manager.ctt_member_token SET \n");
				sb.append(
						" online_datetime = case when token_id = ? then (SELECT COLUMN_DEFAULT FROM information_schema.columns WHERE TABLE_SCHEMA = 'ctt_manager' AND TABLE_NAME = 'ctt_member_token' AND COLUMN_NAME = 'online_datetime') else online_datetime end  \n");
				sb.append(
						",forced_logout_datetime = case when token_id = ? then (SELECT COLUMN_DEFAULT FROM information_schema.columns where TABLE_SCHEMA = 'ctt_manager' AND TABLE_NAME = 'ctt_member_token' AND COLUMN_NAME = 'forced_logout_datetime') else forced_logout_datetime end \n");
				sb.append("WHERE acc_id = ? \n");
				updateObj.add(tokenId);
				updateObj.add(tokenId);
				updateObj.add(accId);

				coun = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);
				LOG.debug(coun);
				if (coun > 0) {
					result = true;
				}

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			result = false;
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}
		}
		return result;
	}

	@Override
	public boolean colosMagTokenId(long accId, String tokenId) {
		StringBuilder sb = new StringBuilder();
		List<Object> updateObj = new ArrayList<Object>();
		boolean result = false;
		try {

			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("NO CONN");
				return result;
			} else {
				sb.append("UPDATE ctt_manager.ctt_manager_token SET \n");
				sb.append(
						"online_datetime = case when token_id = ? then (SELECT COLUMN_DEFAULT FROM information_schema.columns WHERE TABLE_SCHEMA = 'ctt_manager' AND TABLE_NAME = 'ctt_manager_token' AND COLUMN_NAME = 'online_datetime') else online_datetime end \n");
				sb.append(
						", forced_logout_datetime = case when token_id = ? then (SELECT COLUMN_DEFAULT FROM information_schema.columns where TABLE_SCHEMA = 'ctt_manager' AND TABLE_NAME = 'ctt_manager_token' AND COLUMN_NAME = 'forced_logout_datetime') else forced_logout_datetime end \n");
				sb.append("WHERE acc_id = ?  \n");
				updateObj.add(tokenId);
				updateObj.add(tokenId);
				updateObj.add(accId);

				int con = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);
				LOG.debug(con);
				if (con == 1) {
					result = true;
				}

			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			result = false;
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}
		}
		return result;
	}

	@Override
	public boolean getAuthFunction(long accId, String url) {
		StringBuilder sb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		LOG.debug("getAuthFunction" + accId + url);
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("ON CONN");
				return false;
			} else {
				sb = new StringBuilder();

				sb.append(
						"select case when A.acc_level_type = 0 then true when count(C.auth_id) > 0 then true else false end as checkAuth from \n");
				sb.append("(\n");
				sb.append("select a1.acc_id , a1.auth_group , a2.acc_level_type from \n");
				sb.append("(select acc_id ,auth_group from ctt_manager.ctt_manager_acc where acc_id = ?) a1\n");
				sb.append("inner join \n");
				sb.append("(select acc_id , acc_level_type from ctt_manager.ctt_manager_acc_level) a2\n");
				sb.append("on a1.acc_id = a2.acc_id\n");
				sb.append(") A\n");
				sb.append("left join \n");
				sb.append("(select auth_id,group_id from ctt_manager.ctt_auth_list ) B\n");
				sb.append("on A.auth_group = B.group_id \n");
				sb.append("left join \n");
				sb.append("(select auth_id from ctt_manager.ctt_auth where url = ?) C  \n");
				sb.append("on B.auth_id = C.auth_id\n");

				ps = this.READ_CONN.prepareStatement(sb.toString());
				ps.setLong(1, accId);
				ps.setString(2, url);

				LOG.debug(ps.toString());

				rs = ps.executeQuery();

				if (rs.next()) {
					return rs.getBoolean("checkAuth");
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

	private String produceTokenId(long accId) {
		StringBuilder sb = new StringBuilder();
		String accIdStr = accId + "";
		for (int i = accIdStr.length(); i < 10; i++) {
			accIdStr = "0" + accIdStr;
		}
		sb.append(accIdStr);
		sb.append((int) ((Math.random() * 90) + 10));
		String str = "" + System.currentTimeMillis();
		sb.append(str);

		return AESUtil.encrypt_CBC(sb.toString(),
				sb.toString().substring(sb.toString().length() - 16, sb.toString().length()));
	}

	@Override
	public boolean commitDB() throws SQLException {
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				this.WRITE_CONN.commit();
				return true;
			}
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			throw new SQLException(e.getMessage());
		} finally {
			if (this.WRITE_CONN != null) {
				try {
					if (!this.WRITE_CONN.getAutoCommit()) {
						this.WRITE_CONN.setAutoCommit(true);
					}
				} catch (SQLException e) {
					LOG.debug("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				}
			}
		}
	}

	@Override
	public boolean rollBackDB() throws SQLException {
		LOG.debug("===rollBackCreate===");
		try {
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				this.WRITE_CONN.rollback();
				return false;
			}
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				this.WRITE_CONN.rollback();
			} catch (SQLException e1) {
				LOG.debug("SQLException:" + e1.getMessage());
				LOG.error("\nERROR SQL rollback:");
				ShowLog.err(LOG, e1);
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (this.WRITE_CONN != null) {
				try {
					if (!this.WRITE_CONN.getAutoCommit()) {
						this.WRITE_CONN.setAutoCommit(true);
					}
				} catch (SQLException e) {
					LOG.debug("SQLException:" + e.getMessage());
					ShowLog.err(LOG, e);
					throw e;
				}
			}
		}
	}

	public String[] toIP(String ip) {
		if (ip != null && !ip.isEmpty() && !"".equals(ip.trim())) {
			String[] ipArr = ip.split("\\.");
			if (ipArr.length == 4) {
				return ipArr;
			}
		}
		return new String[] { "0", "0", "0", "0" };
	}

	public boolean checkBlackWhiteIPs() {
		StringBuilder sql = null;
		CopyOnWriteArraySet<String> whiteList = null;
		CopyOnWriteArraySet<String> blackList = null;
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
						"SELECT UNIX_TIMESTAMP(update_datetime) AS t1 FROM `ctt_manager`.`ctt_black_white_list` ORDER BY update_datetime DESC");
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
				CommandConstant.CHECK_BLACK_WHITE_TIME = new Date().getTime();
				if (t1 > CommandConstant.LAST_UPDATE_BLACK_WHITE_TIME) {
					sql.append(
							"SELECT CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) AS ip,type FROM ctt_manager.ctt_black_white_list");
					ps = this.READ_CONN.prepareStatement(sql.toString());
					rs = ps.executeQuery();
					whiteList = new CopyOnWriteArraySet<String>();
					blackList = new CopyOnWriteArraySet<String>();
					while (rs.next()) {
						if (rs.getInt("type") == 0) {
							if (rs.getString("ip").endsWith(".0")) {
								blackList.add(rs.getString("ip").substring(0, rs.getString("ip").lastIndexOf(".")));
							} else {
								blackList.add(rs.getString("ip"));
							}
						} else if (rs.getInt("type") == 1) {
							if (rs.getString("ip").endsWith(".0")) {
								whiteList.add(rs.getString("ip").substring(0, rs.getString("ip").lastIndexOf(".")));
							} else {
								whiteList.add(rs.getString("ip"));
							}
						}
					}
					CommandConstant.BLACK_LIST = blackList;
					CommandConstant.WHITE_LIST = whiteList;
					Properties props = new Properties();
					InputStream inputStream = null;
					String[] ip;
					try {
						inputStream = getClass().getClassLoader().getResourceAsStream("baseconfig.properties");
						props.load(inputStream);
						ip = ((String) props.get("BLACK_LIST")).split(",");
						blackList.addAll(new CopyOnWriteArraySet<String>(Arrays.asList(ip)));
						CommandConstant.BLACK_LIST = blackList;
						ip = null;
						ip = ((String) props.get("WHITE_LIST")).split(",");
						whiteList.addAll(new CopyOnWriteArraySet<String>(Arrays.asList(ip)));
						CommandConstant.WHITE_LIST = whiteList;
					} catch (Exception e) {

					} finally {
						ip = null;
						if (inputStream != null) {
							inputStream.close();
						}
						inputStream = null;
					}

					CommandConstant.LAST_UPDATE_BLACK_WHITE_TIME = t1;
					ps.clearParameters();
					sql.setLength(0);
					LOG.info("Force update CommandConstant.BLACK_WHITE_LIST!!!");
					if (CommandConstant.SERVER_IP == null) {
						CommandConstant.SERVER_IP = java.net.InetAddress.getLocalHost().getHostAddress();
					}
					// if (CommandConstant.SERVERS_IP_LIST != null &&
					// !CommandConstant.SERVERS_IP_LIST.isEmpty()
					// && CommandConstant.SERVERS_IP_LIST.size() > 0) {
					// String[] chkProjectName = { "CTT03BetOrder", "CTT03BetRatioMaster",
					// "CTT03LotteryPage", "CTT03QueryInfo" };
					// for (String str : CommandConstant.SERVERS_IP_LIST) {
					// for (String str1 : chkProjectName) {
					// if (CommandConstant.SERVER_IP.equals(str)) {
					// str = "127.0.0.1";
					// }
					// if (!str.equals("127.0.0.1") && !str1.equals(CommandConstant.SERVER_CONTEXT))
					// {
					// touchURL("http://" + str + ":" + CommandConstant.SERVER_PORT + "/" + str1 +
					// "/BlackWhite!checkIPs.php?date="
					// + new Date().getTime());
					// }
					// }
					// }
					// }
					callServer("Default!checkIPs", CommandConstant.NOT_RUN_LIST);
					return true;
				}
				return false;
			}
		} catch (Exception e) {
			LOG.error("checkBlackWhiteIPs()_Exception===" + e.getMessage());
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
			whiteList = null;
			blackList = null;
		}
	}

	public void touchURL(String url) {
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
				wr.writeBytes("");
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

	public boolean initServerIPs() {
		StringBuilder sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int cou = 0;
		try {
			this.checkRead();
			this.checkWrite();
			if (this.READ_CONN == null || this.READ_CONN.isClosed() || this.WRITE_CONN == null
					|| this.WRITE_CONN.isClosed()) {
				LOG.error("no connection");
				return false;
			} else {
				sql = new StringBuilder();
				sql.append("INSERT INTO `ctt_manager`.`ctt_server` (name, context, ip, port, status) ");
				sql.append(
						"SELECT ?,?,?,?,false FROM DUAL WHERE (SELECT COUNT(1) FROM `ctt_manager`.`ctt_server` WHERE ip = ? AND context=? AND port=?)=0");
				ps = this.WRITE_CONN.prepareStatement(sql.toString());
				ps.setString(1, CommandConstant.SERVER_NAME);
				ps.setString(2, CommandConstant.SERVER_CONTEXT);
				ps.setString(3, CommandConstant.SERVER_IP);
				ps.setString(4, CommandConstant.SERVER_PORT);
				ps.setString(5, CommandConstant.SERVER_IP);
				ps.setString(6, CommandConstant.SERVER_CONTEXT);
				ps.setString(7, CommandConstant.SERVER_PORT);
				cou = ps.executeUpdate();
				sql.setLength(0);
				ps.clearParameters();
				ps.close();
				ps = null;
				if (cou == 0) {
					sql.append(
							"UPDATE `ctt_manager`.`ctt_server` SET status=false WHERE ip=? AND context=? AND port=?");
					ps = this.WRITE_CONN.prepareStatement(sql.toString());
					ps.setString(1, CommandConstant.SERVER_IP);
					ps.setString(2, CommandConstant.SERVER_CONTEXT);
					ps.setString(3, CommandConstant.SERVER_PORT);
					cou = ps.executeUpdate();
					sql.setLength(0);
					ps.clearParameters();
					ps.close();
					ps = null;
				}
				sql.append("UPDATE `ctt_manager`.`ctt_server` SET status=true WHERE ip=? AND context=? AND port=?");
				ps = this.WRITE_CONN.prepareStatement(sql.toString());
				ps.setString(1, CommandConstant.SERVER_IP);
				ps.setString(2, CommandConstant.SERVER_CONTEXT);
				ps.setString(3, CommandConstant.SERVER_PORT);
				cou = ps.executeUpdate();
				if (cou == 1) {
					LOG.debug("server IP:" + CommandConstant.SERVER_IP + " is started!");
				}
				sql.setLength(0);
				ps.clearParameters();
				ps.close();
				ps = null;
				if (checkUpdateTime()) {
					callServer("Default!checkServerIPs", CommandConstant.NOT_RUN_LIST);
				}
				return true;
			}
		} catch (Exception e) {
			LOG.error("initServerIPs()_Exception===" + e.getMessage());
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
			cou = 0;
		}
	}

	public boolean checkServerIPs() {
		try {
			if (checkUpdateTime()) {
				callServer("Default!checkServerIPs", CommandConstant.NOT_RUN_LIST);
			}
			return true;
		} catch (Exception e) {
			LOG.error("checkServerIPs()_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			return false;
		} finally {
		}
	}

	private boolean checkUpdateTime() {
		StringBuilder sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<List<String>> list = null;
		long t1 = 0;

		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return false;
			} else {
				sql = new StringBuilder();

				sql.append("SELECT UNIX_TIMESTAMP(date) AS t1 FROM `ctt_manager`.`ctt_server` ORDER BY date DESC");
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

				CommandConstant.CHECK_SERVERS_IP_TIME = new Date().getTime();
				if (t1 > CommandConstant.LAST_UPDATE_SERVERS_IP_TIME) {
					sql.append("SELECT ip,context,port FROM `ctt_manager`.`ctt_server` WHERE status=1");
					ps = this.READ_CONN.prepareStatement(sql.toString());
					rs = ps.executeQuery();
					list = new ArrayList<List<String>>();
					while (rs.next()) {
						ArrayList<String> list2 = new ArrayList<String>();
						list2.add(rs.getString("ip"));
						list2.add(rs.getString("context"));
						list2.add(rs.getString("port"));
						list.add((ArrayList<String>) list2.clone());
					}
					CommandConstant.SERVERS_IP_LIST = list;
					CommandConstant.LAST_UPDATE_SERVERS_IP_TIME = t1;
					ps.clearParameters();
					sql.setLength(0);
					LOG.info("Force update CommandConstant.SERVERS_IP_LIST!!!");
					if (CommandConstant.SERVER_IP == null) {
						CommandConstant.SERVER_IP = java.net.InetAddress.getLocalHost().getHostAddress();
					}
					return true;
				}
				return false;
			}
		} catch (Exception e) {
			LOG.error("checkServerIPs()_Exception===" + e.getMessage());
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
	public void callServer(String url, List<String> notRunList) {
		try {
			if (CommandConstant.SERVERS_IP_LIST != null && !CommandConstant.SERVERS_IP_LIST.isEmpty()
					&& CommandConstant.SERVERS_IP_LIST.size() > 0) {
				String str = "127.0.0.1";
				for (List<String> ll : CommandConstant.SERVERS_IP_LIST) {
					if (notRunList.indexOf(ll.get(1)) == -1) {
						if (CommandConstant.SERVER_IP.equals(ll.get(0))
								&& CommandConstant.SERVER_CONTEXT.equals(ll.get(1))
								&& CommandConstant.SERVER_PORT.equals(ll.get(2))) {
							str = "127.0.0.1";
						} else {
							str = CommandConstant.SERVER_IP.equals(ll.get(0)) ? "127.0.0.1" : ll.get(0);
							LOG.info(
									"http://" + str + ":" + ll.get(2) + ("".equals(ll.get(1)) ? "/" : (ll.get(1) + "/"))
											+ url + ".php?date=" + new Date().getTime());
							touchURL(
									"http://" + str + ":" + ll.get(2) + ("".equals(ll.get(1)) ? "/" : (ll.get(1) + "/"))
											+ url + ".php?date=" + new Date().getTime());
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.error("callServer()_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
	}

	@Override
	public boolean setLotteryLogToDBNoCommit(long opsId, int action, String data, String ip) {
		String[] ipArray = null;
		StringBuilder sb = null;
		List<Object> param = null;
		long count = 0;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("log no connect");
				return todo;
			} else {
				ipArray = toIP(ip);
				sb = new StringBuilder();
				param = new ArrayList<Object>();

				sb.append("INSERT INTO `ctt_manager`.`ctt_lottery_set_ops_log` \n");
				sb.append("(ops_acc_id, ops_acc_name, action, detail, ip1, ip2, ip3, ip4, ops_datetime) \n");
				sb.append("VALUES \n");
				sb.append("(?, \n");
				param.add(opsId);
				sb.append("(SELECT acc_name FROM ctt_manager.ctt_manager_acc WHERE acc_id = ?), \n");
				param.add(opsId);
				sb.append("?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP) \n");
				param.add(action);
				param.add(data);
				param.add(Integer.parseInt(ipArray[0]));
				param.add(Integer.parseInt(ipArray[1]));
				param.add(Integer.parseInt(ipArray[2]));
				param.add(Integer.parseInt(ipArray[3]));

				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), param);
				if (count != 0) {
					todo = true;
				}
				LOG.debug(count);
			}
		} catch (SQLException e) {
			todo = false;
			LOG.debug("SQLException, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			ipArray = null;
			count = 0;
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (param != null) {
				param.clear();
				param = null;
			}
		}
		return todo;
	}

	@Override
	public boolean setActionLogToDBNoCommit(String tableName, long opsAccId, String actionUrl, int opsType, int urlType,
			String detail, String ip) {
		String[] ipArray = null;
		StringBuilder sb = null;
		List<Object> param = null;
		long count = 0;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("log no connect");
				return todo;
			} else {
				ipArray = toIP(ip);
				sb = new StringBuilder();
				param = new ArrayList<Object>();

				sb.append("INSERT INTO " + tableName + " \n");
				sb.append("(ops_acc_id,action_url,ops_type,url_type,detail,ip1,ip2,ip3,ip4) \n");
				sb.append("VALUES \n");
				sb.append("(?,?,?,?,?,?,?,?,?) \n");
				param.add(opsAccId);
				param.add(actionUrl);
				param.add(opsType);
				param.add(urlType);
				param.add(detail);
				param.add(Integer.parseInt(ipArray[0]));
				param.add(Integer.parseInt(ipArray[1]));
				param.add(Integer.parseInt(ipArray[2]));
				param.add(Integer.parseInt(ipArray[3]));

				count = StmtUtil.update(this.WRITE_CONN, sb.toString(), param);

				if (count == 1) {
					todo = true;
				}
			}
		} catch (SQLException e) {
			todo = false;
			LOG.debug("SQLException, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			ipArray = null;
			count = 0;
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (param != null) {
				param.clear();
				param = null;
			}
		}
		return todo;
	}

	@Override
	public void updateOrderIdDate() {
		StringBuilder sb = null;
		try {
			sb = new StringBuilder();
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
			} else {
				WRITE_CONN.setAutoCommit(false);
				sb.append("CALL ctt_manager.update_auto_increment('ctt_manager', 'ctt_recharge_order', 1000001); \n");
				StmtUtil.callStored(this.WRITE_CONN, sb.toString(), new ArrayList<Object>());
				WRITE_CONN.setAutoCommit(true);

				WRITE_CONN.setAutoCommit(false);
				sb.setLength(0);
				sb.append("CALL ctt_manager.update_auto_increment('ctt_manager', 'ctt_withdrawal_order', 2000001); \n");
				StmtUtil.update(WRITE_CONN, sb.toString(), new ArrayList<Object>());
				WRITE_CONN.setAutoCommit(true);
			}
		} catch (Exception e) {
			LOG.info("updateOrderIdDate_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
		}
	}
}
