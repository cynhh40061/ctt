package tw.com.ctt.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IGameDao;
import tw.com.ctt.model.CommissionRawBean;
import tw.com.ctt.model.GamePuncheRecordsBean;
import tw.com.ctt.model.ManageAccountInfo;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

import tw.com.ctt.model.PlayerInfoBean;

/**
 * Handle all player's account operation of punche game
 * @author Quanto
 *
 */
public class GameDaoImpl extends BaseDao implements IGameDao {
	private static final long serialVersionUID = -4046186702438639069L;
	private static final Logger LOG = LogManager.getLogger(GameDaoImpl.class.getName());
	public static final String[] orderBy = { "A.acc_id", "C.nickname", "C.acc_name", "D.create_time", "E.ip",
			"E.last_login_time" };// 1-5,default:0
	public static final String[] ASC = { "ASC", "DESC" };// 0-1
	public static final int timeoutMin = 30;

	/**
	 * checkPwd for player
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
	 * getPlayer Basic Info
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
	 * withdraw money from player account.
	 */
	@Override
	public boolean withdraw(long accId, BigDecimal money) throws SQLException {
		StringBuilder sb = null;
		List<Object> insertMap = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb = new StringBuilder();
				insertMap = new ArrayList<Object>();
				
				sb.append("update `ctt_manager`.`ctt_member_acc` set balance = balance -" + money + " where acc_id = ?");
				insertMap.add(accId);
				
				int count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), insertMap);
				
				if(count != 0) {
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
			if(insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return false;
	}

	/**
	 * deposit money to player's account
	 */
	@Override
	public boolean deposit(long accId, BigDecimal money) throws SQLException {
		StringBuilder sb = null;
		List<Object> insertMap = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {				
				insertMap = new ArrayList<Object>();
				sb = new StringBuilder();
				
				
				sb.append("update `ctt_manager`.`ctt_member_acc`  set balance = balance +" + money + " where acc_id = ?");
				insertMap.add(accId);
				
				int count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), insertMap);

				if(count != 0) {
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
			if(insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return false;
	}
	
	/**
	 * To write a log, when update player's money.
	 */
	@Override
	public boolean updateMoneyPuncheGameAccInfo(long accId, BigDecimal money, String serverId) throws SQLException {
		StringBuilder sb = null;
		List<Object> insertMap = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {			
				sb = new StringBuilder();
				insertMap = new ArrayList<Object>();
				
				sb.append("update `ctt_manager`.`ctt_member_punch_game_acc_info` set punch_game_balance = ? , last_server_id = ? where acc_id = ?");
				
				insertMap.add(money);
				insertMap.add(serverId);
				insertMap.add(accId);
				
				int count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), insertMap);
				
				if(count != 0) {
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
			if(insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
		return false;
	}
	
	/**
	 * getMemberUpperAccRatio
	 */
	@Override
	public List<Map<String , Object>> getMemberUpperAccRatio(long accId , int gameType){
		StringBuilder sb = new StringBuilder();
		Map<String , Object> map = null;
		List<Object> selectList = null;
		
		String [] upAccIdArr = {"com","sc","bc","co","sa","ag","ag1","ag2","ag3","ag4","ag5","ag6","ag7","ag8","ag9","ag10"};
		String [] upAccRatioArr = {"comRatio","scRatio","bcRatio","coRatio","saRatio","agRatio","ag1Ratio","ag2Ratio","ag3Ratio","ag4Ratio","ag5Ratio","ag6Ratio","ag7Ratio","ag8Ratio","ag9Ratio","ag10Ratio"};
		int [] upAccLevelArr = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
		
		List<Map<String,Object>> result = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return result;
			} else {
				selectList = new ArrayList<Object>();
				result= new ArrayList<Map<String ,Object>>();
				
				sb.append("	select 	\n");
				sb.append("	 '0' as com 	\n");
				sb.append("	,com_ratio as comRatio 	\n");
				sb.append("	,case when B.sc > 0 then B.sc else -1 end as sc 	\n");
				sb.append("	,case when B.sc > 0 then A.sc_ratio else -1 end as scRatio 	\n");
				sb.append("	,case when B.bc > 0 then B.bc else -1 end as bc 	\n");
				sb.append("	,case when B.bc > 0 then A.bc_ratio else -1 end as bcRatio 	\n");
				sb.append("	,case when B.co > 0 then B.co else -1 end as co 	\n");
				sb.append("	,case when B.co > 0 then A.co_ratio else -1 end as coRatio 	\n");
				sb.append("	,case when B.sa > 0 then B.sa else -1 end as sa 	\n");
				sb.append("	,case when B.sa > 0 then A.sa_ratio else -1 end as saRatio 	\n");
				sb.append("	,case when B.ag > 0 then B.ag else -1 end as ag 	\n");
				sb.append("	,case when B.ag > 0 then A.ag_ratio else -1 end as agRatio 	\n");
				sb.append("	,case when B.ag1 > 0 then B.ag1 else -1 end as ag1 	\n");
				sb.append("	,case when B.ag1 > 0 then A.ag1_ratio else -1 end as ag1Ratio 	\n");
				sb.append("	,case when B.ag2 > 0 then B.ag2 else -1 end as ag2 	\n");
				sb.append("	,case when B.ag2 > 0 then A.ag2_ratio else -1 end as ag2Ratio 	\n");
				sb.append("	,case when B.ag3 > 0 then B.ag3 else -1 end as ag3 	\n");
				sb.append("	,case when B.ag3 > 0 then A.ag3_ratio else -1 end as ag3Ratio 	\n");
				sb.append("	,case when B.ag4 > 0 then B.ag4 else -1 end as ag 	\n");
				sb.append("	,case when B.ag4 > 0 then A.ag4_ratio else -1 end as ag4Ratio 	\n");
				sb.append("	,case when B.ag5 > 0 then B.ag5 else -1 end as ag5 	\n");
				sb.append("	,case when B.ag5 > 0 then A.ag5_ratio else -1 end as ag5Ratio 	\n");
				sb.append("	,case when B.ag6 > 0 then B.ag6 else -1 end as ag6 	\n");
				sb.append("	,case when B.ag6 > 0 then A.ag6_ratio else -1 end as ag6Ratio 	\n");
				sb.append("	,case when B.ag7 > 0 then B.ag7 else -1 end as ag7 	\n");
				sb.append("	,case when B.ag7 > 0 then A.ag7_ratio else -1 end as ag7Ratio 	\n");
				sb.append("	,case when B.ag8 > 0 then B.ag8 else -1 end as ag8 	\n");
				sb.append("	,case when B.ag8 > 0 then A.ag8_ratio else -1 end as ag8Ratio 	\n");
				sb.append("	,case when B.ag9 > 0 then B.ag9 else -1 end as ag9 	\n");
				sb.append("	,case when B.ag9 > 0 then A.ag9_ratio else -1 end as ag9Ratio 	\n");
				sb.append("	,case when B.ag10 > 0 then B.ag10 else -1 end as ag10 	\n");
				sb.append("	,case when B.ag10 > 0 then A.ag10_ratio else -1 end as ag10Ratio 	\n");
				sb.append("	from  	\n");
				sb.append("	(select acc_id , com_ratio , sc_ratio , bc_ratio , co_ratio , sa_ratio , ag_ratio, ag1_ratio	\n");
				sb.append("	, ag2_ratio, ag3_ratio, ag4_ratio, ag5_ratio, ag6_ratio, ag7_ratio, ag8_ratio, ag9_ratio	\n");
				sb.append("	, ag10_ratio  from ctt_manager.ctt_member_total_ratio where acc_id = ? and game_type = ?)A 	\n");
				sb.append("	left join 	\n");
				sb.append("	(select acc_id , sc,bc,co,sa,ag,ag1,ag2,ag3,ag4,ag5,ag6,ag7,ag8,ag9,ag10 from ctt_manager.ctt_member_acc_level ) B 	\n");
				sb.append("	on A.acc_id = B.acc_id 	\n");

				selectList.add(accId);
				selectList.add(gameType);
				
				List<Map<String , Object>> listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectList);
				for(int i = 0 ; i < listMap.size() ; i++) {
					
					for(int j = 0 ; j < upAccIdArr.length ; j++) { 
						map = new ConcurrentHashMap<String, Object>();
						String upAccId = upAccIdArr[j];
						String upAccRatio = upAccRatioArr[j];
						int upAccLevel = upAccLevelArr[j];
						if(listMap.get(i).containsKey(upAccId) && listMap.get(i).containsKey(upAccRatio)) {
							if(!"-1".equals(listMap.get(i).get(upAccId)+"")) {
								map.put("accId", listMap.get(i).get(upAccId));
								map.put("ratio", listMap.get(i).get(upAccRatio));
								map.put("accLevelType", upAccLevel);
								
								result.add(map);
							}
							
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if(sb != null ) {
				sb.setLength(0);
				sb = null;
			}
			if(selectList != null) {
				selectList.clear();
				selectList = null;
			}
		}
		return result;
	}
	
	/**
	 * insertGamePuncheRecords
	 */
	@Override
	public boolean insertGamePuncheRecords(GamePuncheRecordsBean gamePuncerecordsBean) {
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {				
				long count = StmtUtil.insertNoCommitByBean(this.WRITE_CONN, "ctt_manager.ctt_game_punche_records", gamePuncerecordsBean);
				
				if(count != 0) {
					todo = true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} 
		
		return todo;
	}
	
	/**
	 * insertCommissionRawData
	 */
	@Override
	public boolean insertCommissionRawData(CommissionRawBean commissionRawBean) {
		
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {				
				
				long count = StmtUtil.insertNoCommitByBean(this.WRITE_CONN, "ctt_manager.ctt_punche_commission_raw", commissionRawBean);
				
				if(count != 0) {
					todo = true;
				}
				
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		}
		return todo;
	}
	
	/**
	 * getPuncheGameLastSerialization when restart, server should get the last id of today.
	 */
	public int getPuncheGameLastSerialization(Date date , int serverId) {
		StringBuilder sb = null;
		List<Object> selectList = null;
		int gameIdSerialization = 0;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return gameIdSerialization;
			} else {
				sb = new StringBuilder();
				selectList = new ArrayList<Object>();
				
				sb.append("SELECT MAX(game_id) as gameId FROM ctt_manager.ctt_game_punche_records WHERE DATE_FORMAT(start_time,'%Y/%m/%d') = DATE_FORMAT(?,'%Y/%m/%d') AND server_id = ? \n");

				selectList.add(date);
				selectList.add(serverId);
				
				List<Map<String , Object>> listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectList);
				if(listMap.size() == 1) {
					Map<String,Object> tmpMap = (Map<String,Object>) listMap.get(0);
					if(tmpMap.containsKey("gameId")) {
						if(!"".equals(tmpMap.get("gameId").toString())) {
							long gameId = Long.parseLong(tmpMap.get("gameId").toString());
							gameIdSerialization = Integer.parseInt(""+gameId%100000);
							
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if(sb != null ) {
				sb.setLength(0);
				sb = null;
			}
			if(selectList != null) {
				selectList.clear();
				selectList = null;
			}
		}
		return gameIdSerialization;
	}
	
	/**
	 * update what game to play now.
	 */
	@Override
	public boolean addAccNowGameData(long accId , int serverId , String gameHall , String gameName) {
		StringBuilder sb = null;
		List<Object> updateList = null;
		boolean todo = false;
		
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {		
				sb = new StringBuilder();
				updateList = new ArrayList<Object>();
				
				sb.append("UPDATE ctt_manager.ctt_member_basic_setting SET server_id = ? , game_hall = ? , now_game = ?  WHERE acc_id = ?");
				
				updateList.add(serverId);
				updateList.add(gameHall);
				updateList.add(gameName);
				updateList.add(accId);

				long count = StmtUtil.update(this.WRITE_CONN, sb.toString(), updateList);
				
				if(count != 0) {
					todo = true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		}
		finally {
			if(updateList != null) {
				updateList.clear();
				updateList = null;
			}
			if(sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return todo;
	}
	/**
	 * when player leave the game , should remove the account.
	 */
	@Override
	public boolean removeAccNowGameData(long accId , int serverId ) {
		StringBuilder sb = null;
		List<Object> updateList = null;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {				
				sb = new StringBuilder();
				updateList = new ArrayList<Object>();
				
				sb.append("UPDATE ctt_manager.ctt_member_basic_setting SET server_id = 0 , game_hall = '' , now_game = ''  WHERE acc_id = ? AND server_id = ?");
				
				updateList.add(accId);
				updateList.add(serverId);
				
				long count = StmtUtil.update(this.WRITE_CONN, sb.toString(), updateList);
				
				if(count != 0) {
					todo = true;
				}
				
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		}
		finally {
			if(updateList != null) {
				updateList.clear();
				updateList = null;
			}
			if(sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return todo;
	}
	
	/**
	 * getGameServerParam of all game server.
	 */
	@Override
	public List<Map<String, Object>> getGameServerParam() {
		StringBuilder sb = null;
		List<Object> selectList = null;
		List<Map<String, Object>> list = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				return list;
			} else {
				sb = new StringBuilder();
				selectList = new ArrayList<Object>();
				list = new ArrayList<Map<String, Object>>();
				
				sb.append("SELECT \n");
				sb.append("sid,game_server_id,bet,max_player,game_times_type,commission,thread,beats_time_out,player_beats_time_out,game_time_out,auto_time_stamp,wait_continue_time_out,continue_times_type,service_time,server_status \n");
				sb.append("FROM \n");
				sb.append("`ctt_manager`.`ctt_manager_game_set` \n");
				sb.append("WHERE \n");
				sb.append("server_status = 1 \n");

				list = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectList);
			}
		} catch (Exception e) {
			list = null;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if(sb != null ) {
				sb.setLength(0);
				sb = null;
			}
			if(selectList != null) {
				selectList.clear();
				selectList = null;
			}
		}
		return list;
	}
}
