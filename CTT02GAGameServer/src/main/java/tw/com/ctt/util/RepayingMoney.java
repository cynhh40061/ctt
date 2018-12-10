package tw.com.ctt.util;

import static tw.com.ctt.constant.LogToDBConstant.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.impl.BaseDao;

/**
 * we use this class to repay money to player's account, when
 * the game server was shutdown un-normally.
 * 
 * because the money of player will be withdraw when join game,
 * but didnt repay to player when server died.
 * @author Quanto
 *
 */
public class RepayingMoney extends BaseDao{
	
	private static final Logger LOG = LogManager.getLogger(RepayingMoney.class.getName());
	
	public RepayingMoney() {
	}
	
	
	/**
	 * insert a log for Repaying Money
	 * @param serverId
	 */
	public void RepayingMoneyService(String serverId) {
		List<Map<String,Object>> listMap = null;
		Map<String,Object> map = null;
		boolean todo = false;
		boolean isCommit = false;
		try {
			listMap = checkGameServerRepayingMoneyDao(serverId);
			if(listMap.size() > 0) {
				isCommit = true;
				todo = RepayingMoneyDao(serverId);
				for(int i = 0 ; i < listMap.size() && todo == true ; i++) {
					if(listMap.get(i).containsKey("accId") && listMap.get(i).containsKey("punchGameBalance")) {
						map = new ConcurrentHashMap<String, Object>();
						map.put("serverId", serverId);
						map.put("punchGameBalance", listMap.get(i).get("punchGameBalance"));
						JSONObject json = new JSONObject(map);
						todo = setLogToDBNoCommit("ctt_manager.ctt_member_money_log",Long.parseLong(listMap.get(i).get("accId").toString())
								,Long.parseLong(listMap.get(i).get("accId").toString()),0, OPS_TYPE_MEMBER, ACC_TYPE_MEMBER
								, LOG_ACTION_SYSTEM_ROLL_BACK,json.toString(), "");
					}
				}
			}
			
		}catch(Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		}finally {
			if(listMap.size() > 0 && isCommit) {
				if(todo) {
					try {
						todo = commitDB();
					} catch (SQLException e) {
						LOG.info("SQLException, " + e.getMessage());
						ShowLog.err(LOG, e);
						try {
							todo = rollBackDB();
						} catch (SQLException e1) {
							LOG.info("SQLException, " + e.getMessage());
							ShowLog.err(LOG, e);
						}
					}
				}
				else {
					try {
						todo = rollBackDB();
					} catch (SQLException e) {
						LOG.info("SQLException, " + e.getMessage());
						ShowLog.err(LOG, e);
					}
				}
			}
			if(map != null) {
				map.clear();
				map = null;
			}
		}
	}
	
	public void closeRepayingMoney() {
		this.close();
	}
	
	/**
	 * repay money to account which was withdraw by specificed game server.
	 * @param serverId
	 */
	public boolean RepayingMoneyDao(String serverId) {
		StringBuilder sb = null;
		List<Object> updateObj =null;
		try {			
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb = new StringBuilder();
				updateObj = new ArrayList<Object>();
				
				sb.append("	UPDATE ctt_manager.ctt_member_acc a	\n");
				sb.append("	JOIN ctt_manager.ctt_member_punch_game_acc_info b on a.acc_id = b.acc_id	\n");
				sb.append("	set a.balance = a.balance+b.punch_game_balance , b.punch_game_balance = 0 	\n");
				sb.append("	where b.last_server_id = ?	\n");
				
				updateObj.add(serverId);
				
				int cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(),updateObj);
				
				if(cou > 0) {
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
			if(updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}
		}
		return false;
	}
	
	/**
	 * checkGameServerRepayingMoneyDao of specificed server.
	 * @param serverId
	 * @return
	 */
	public List<Map<String,Object>> checkGameServerRepayingMoneyDao(String serverId) {
		StringBuilder sb = null;
		List<Object> selectObj =null;
		List<Map<String,Object>> listMap = null;
		try {			
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return listMap;
			} else {
				sb = new StringBuilder();
				selectObj = new ArrayList<Object>();
				 
				sb.append("SELECT acc_id as accId ,punch_game_balance as punchGameBalance  \n");
				sb.append("FROM ctt_manager.ctt_member_punch_game_acc_info where last_server_id = ? and punch_game_balance > 0 \n");
				
				selectObj.add(serverId);
				
				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);
				
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
			listMap = null;
		} finally {
			if(sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if(selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
		}
		return listMap;
	}

}
