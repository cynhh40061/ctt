package tw.com.ctt.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.ISubOrderInfoDao;
import tw.com.ctt.model.EachLotteryAuthInfoBean;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class SubOrderInfoDaoImpl extends BaseDao implements ISubOrderInfoDao {
	private static final long serialVersionUID = -5393598519122647798L;
	private static final Logger LOG = LogManager.getLogger(SubOrderInfoDaoImpl.class.getName());
	
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
	 * getAllLottery include all lottery's switch,and basic infos...
	 * will resort into map.
	 */
	@Override
	public Map<String,Object> getAllLottery() {
		StringBuilder sb = null;
		HashMap<String,Object> mapAll = null;
		HashMap<String,Object> mapL1 = null;
		HashMap<String,Object> mapL2 = null;
		HashMap<String,Object> mapL3 = null;
		HashMap<String, String> mapL4 = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String level1_name = null;
		String level2_name = null;
		String level3_name = null;
		
		String level1_id = null;
		String level2_id = null;
		String level3_id = null;
		
		String zodiac_type = null;
		String lottery_lowfreq = null;
		
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return null;
			} else {
				sb = new StringBuilder();
				
				sb.append(" select A.auth_id as main_id, A.lottery_title as main_title,D.title as local_title, B.lottery_title as mid_title, B.auth_id as mid_id,  \n");
				sb.append(" C.lottery_title as min_title, D.name as local_name,D.id as local_id,D.zodiac_type as zodiac_type,D.lottery_lowfreq as lottery_lowfreq, F1.switch as switch1, F2.switch as switch2, F3.switch as switch3, C.auth_id as min_auth_id, C.lottery_rule, C.played_text, C.lottery_example, C.function_name, C.total_no_of_bet from   \n");
				sb.append(" (SELECT auth_id,lottery_title,auth_level_type FROM `ctt_manager`.`ctt_lottery_auth` where auth_level_type =1) A   \n");
				sb.append(" left outer join   \n");
				sb.append(" (select `id`, `title` , `name`, `type`, `zodiac_type`, `lottery_lowfreq` from `ctt_manager`.`ctt_lottery_type`) D   \n");
				sb.append(" on A.auth_id=D.`type`   \n");
				sb.append(" left outer join   \n");
				sb.append(" (SELECT auth_id,lottery_title,level1_id,auth_level_type FROM `ctt_manager`.`ctt_lottery_auth` where auth_level_type =2) B   \n");
				sb.append(" on A.auth_id=B.level1_id   \n");
				sb.append(" left outer join   \n");
				sb.append(" (SELECT sort_level1_id as CL1, sort_level2_id as CL2, sort_level3_id as CL3, auth_id,lottery_title,level1_id,level2_id,auth_level_type,lottery_rule , played_text, lottery_example, function_name, total_no_of_bet FROM `ctt_manager`.`ctt_lottery_auth` where auth_level_type =3 )C   \n");
				sb.append(" on B.auth_id= C.level2_id   \n");
				sb.append(" left outer join   \n");
				sb.append(" (select id, auth_id, switch from `ctt_manager`.`ctt_lottery_auth_list`) F3   \n");
				sb.append(" on F3.`auth_id`=C.`auth_id` AND F3.`id`=D.`id`   \n");
				sb.append(" left outer join  \n");
				sb.append(" (select id, auth_id, switch from `ctt_manager`.`ctt_lottery_auth_list`) F2  \n");
				sb.append(" on F2.`auth_id`=B.`auth_id` AND F2.`id`=D.`id`  \n");
				sb.append(" left outer join  \n");
				sb.append(" (select id, auth_id, switch from `ctt_manager`.`ctt_lottery_auth_list`) F1   \n");
				sb.append(" on F1.`auth_id`=A.`auth_id` AND F1.`id`=D.`id`  \n");
				sb.append(" where F3.`id` is not null   \n");
				sb.append(" order by CL1 asc,D.`id` asc ,CL2 asc,CL3 asc;  \n");
				
				ps = this.READ_CONN.prepareStatement(sb.toString());
				rs = ps.executeQuery();
				level1_name = null;
				level2_name = null;
				level3_name = null;
				
				level1_id = null;
				level2_id = null;
				level3_id = null;
				
				mapAll = new HashMap<String,Object>();
				mapL1 = new HashMap<String,Object>();
				mapL2 = new HashMap<String,Object>();
				mapL3 = new HashMap<String,Object>();
				mapL4 = new HashMap<String, String>();

				int L3Index = 1;
				int L4Index = 1;
				
				while (rs.next()) {
					if (level1_id == null) {
						level1_id = rs.getString("main_id");
						level1_name = rs.getString("main_title");
						zodiac_type = rs.getString("zodiac_type");
						lottery_lowfreq =  rs.getString("lottery_lowfreq");
						
					}
					if (level2_id == null) {
						level2_id = rs.getString("local_id");
						level2_name = rs.getString("local_title");
					}
					if (level3_id == null) {
						level3_id = rs.getString("mid_id");
						level3_name = rs.getString("mid_title");
					}
					
					if (!level3_id.equals(rs.getString("mid_id"))) {
						
						level3_id = rs.getString("mid_id");
						level3_name = rs.getString("mid_title");
						L3Index ++;
						L4Index = 1;
						mapL3.clear();
//						mapL3 = null;
//						mapL3 = new HashMap<String,Object>();
					}
					if (!level2_id.equals(rs.getString("local_id"))) {
						mapL1.put(level2_id, mapL2.clone());
						level2_id = rs.getString("local_id");
						level2_name = rs.getString("local_title");
						L3Index = 1;
						L4Index = 1;
						mapL2.clear();
						mapL3.clear();
//						mapL2 = null;
//						mapL2 = new HashMap<String,Object>();
//						mapL3 = null;
//						mapL3 = new HashMap<String,Object>();
					}
					if (!level1_id.equals(rs.getString("main_id"))) {
						mapL1.put("zodiac_type", zodiac_type);
						mapL1.put("lottery_lowfreq", lottery_lowfreq);
						mapAll.put(level1_id, mapL1.clone());
						level1_id = rs.getString("main_id");
						level1_name = rs.getString("main_title");
						
						zodiac_type = rs.getString("zodiac_type");
						lottery_lowfreq =  rs.getString("lottery_lowfreq");
						L3Index = 1;
						L4Index = 1;
//						mapL1 = null;
//						mapL1 = new HashMap<String,Object>();
//						mapL2 = null;
//						mapL2 = new HashMap<String,Object>();
//						mapL3 = null;
//						mapL3 = new HashMap<String,Object>();
						mapL1.clear();
						mapL2.clear();
						mapL3.clear();
					}
					mapL4.clear();
//					mapL4 = null;
//					mapL4 = new HashMap<String, String>();
					//mapL4.put("main_title", rs.getString("main_title"));				//時時彩
					mapL4.put("main_id", rs.getString("main_id")!=null?rs.getString("main_id"):"");						//1(時時彩id)
					//mapL4.put("local_title", rs.getString("local_title"));			//重慶時時彩
					mapL4.put("local_id", rs.getString("local_id")!=null?rs.getString("local_id"):"");					//1 (重慶時時彩的id)
					//mapL4.put("local_name", rs.getString("local_name"));				//cqssc
					mapL4.put("mid_id", rs.getString("mid_id")!=null?rs.getString("mid_id"):"");						//玩法id(五星)
					//mapL4.put("mid_title", rs.getString("mid_title"));					//玩法(五星)
					//mapL4.put("min_title", rs.getString("min_title"));					//五星直選(母單名稱)
					mapL4.put("min_auth_id",rs.getString("min_auth_id")!=null?rs.getString("min_auth_id"):"");				//155(母單權限id)
					mapL4.put("lottery_rule",rs.getString("lottery_rule")!=null?rs.getString("lottery_rule"):"");
					mapL4.put("played_text",rs.getString("played_text")!=null?rs.getString("played_text"):"");
					mapL4.put("lottery_example",rs.getString("lottery_example")!=null?rs.getString("lottery_example"):"");
					mapL4.put("function_name",rs.getString("function_name")!=null?rs.getString("function_name"):"");
					mapL4.put("total_no_of_bet",rs.getString("total_no_of_bet")!=null?rs.getString("total_no_of_bet"):"");					
					mapL4.put("0-switch3",rs.getString("switch3")!=null?rs.getString("switch3"):"");
					mapL3.put(getNumByLen(L4Index,3)+"-"+rs.getString("min_title"), mapL4.clone());
					mapL3.put("0-switch2",rs.getString("switch2")!=null?rs.getString("switch2"):"");
					mapL2.put(getNumByLen(L3Index,3)+"-"+level3_name, mapL3.clone());
					mapL2.put("0-switch1",rs.getString("switch1")!=null?rs.getString("switch1"):"");
					L4Index ++;
				}
				if (mapL3 != null && !mapL3.isEmpty() && mapL3.size() > 0 && level3_name != null) {
					mapL2.put(getNumByLen(L3Index,3)+"-"+level3_name, mapL3.clone());
				}
				if (mapL2 != null && !mapL2.isEmpty() && mapL2.size() > 0 && level2_name != null) {
					mapL1.put(level2_id, mapL2.clone());
				}
				if (level1_name != null) {
					mapL1.put("zodiac_type", zodiac_type);
					mapL1.put("lottery_lowfreq", lottery_lowfreq);
					mapAll.put(level1_id, mapL1.clone());
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
	 * title auth is for website to show.
	 * it is not the real type, just for display.
	 * include the relationship to show, and each lottery's real type and local_id.
	 */
	@Override
	public Map<String,Object> getTitleAuth() {
		StringBuilder sb = null;
		HashMap<String,Object> mapAll = null;
		HashMap<String,Object> mapL1 = null;
		HashMap<String, String> mapL4 = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String level1_name = null;
		String level1_id = null;
		String level1_url = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.error("no connection");
				return null;
			} else {
				sb = new StringBuilder();				
				sb.append(" select B.lottery_fn_name, A.auth_id as main_id, A.lottery_title as main_title,B.lottery_title as local_title,B.local_id as local_id , A.lottery_img_url as img_url, C.type from  ( \n");
				sb.append(" ( \n");
				sb.append(" SELECT sort_level1_id as CL1, sort_level2_id as CL2, auth_id,lottery_title,upper_level_id,auth_level_type,local_id,lottery_fn_name FROM `ctt_manager`.`ctt_lottery_title_auth` where auth_level_type =2  \n");
				sb.append(" )B  \n");
				sb.append(" left join   \n");
				sb.append(" ( \n");
				sb.append(" SELECT auth_id,lottery_title,auth_level_type, lottery_img_url FROM `ctt_manager`.`ctt_lottery_title_auth` where auth_level_type =1 \n");
				sb.append(" ) A \n");
				sb.append(" on A.auth_id=B.upper_level_id \n");
				sb.append(" left join   \n");
				sb.append(" ( \n");
				sb.append(" SELECT type, id FROM `ctt_manager`.`ctt_lottery_type`\n");
				sb.append(" ) C \n");
				sb.append(" on B.local_id=C.id \n");
				sb.append(" ) \n");
				sb.append(" order by CL1 asc,CL2 asc;   \n");
				
				ps = this.READ_CONN.prepareStatement(sb.toString());
				rs = ps.executeQuery();
				level1_name = null;				
				level1_id = null;				
				mapAll = new HashMap<String,Object>();
				mapL1 = new HashMap<String,Object>();
				mapL4 = new HashMap<String, String>();
				int L1Index = 1;
				int L4Index = 1;				
				while (rs.next()) {
					if (level1_id == null) {
						level1_id = rs.getString("main_id");
						level1_name = rs.getString("main_title");
						level1_url =  rs.getString("img_url");						
					}					
					if (!level1_id.equals(rs.getString("main_id"))) {
						mapL1.put("main_title", level1_name);
						mapL1.put("level1_url", level1_url);
						mapAll.put(getNumByLen(L1Index,3), mapL1.clone());
						level1_id = rs.getString("main_id");
						level1_name = rs.getString("main_title");
						level1_url =  rs.getString("img_url");
						L1Index ++;
						L4Index = 1;
						mapL1.clear();
//						mapL1 = null;
//						mapL1 = new HashMap<String,Object>();					
					}
					mapL4.clear();
//					mapL4 = null;
//					mapL4 = new HashMap<String, String>();					
					mapL4.put("local_title", rs.getString("local_title"));
					mapL4.put("local_id", rs.getString("local_id")!=null?rs.getString("local_id"):"");
					mapL4.put("type", rs.getString("type")!=null?rs.getString("type"):"");
					mapL4.put("lottery_fn_name", rs.getString("lottery_fn_name")!=null?rs.getString("lottery_fn_name"):"");
					mapL1.put(getNumByLen(L4Index,3), mapL4.clone());
					L4Index ++;
				}				
				if (level1_name != null) {
					mapL1.put("main_title", level1_name);
					mapL1.put("level1_url", level1_url);
					mapAll.put(getNumByLen(L1Index,3), mapL1.clone());
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
			mapL4 = null;
			level1_name = "";
			level1_name = null;
		}
	}
	
	/**
	 * get handicap of the specified account.
	 */
	@Override
	public Map<String, Map<String, String>> getHandicap(long accId) {
		HashMap<String, Map<String, String>> mapAll = null;
		HashMap<String, String> mapL4 = null;
		StringBuilder sb = new StringBuilder();
		List<Object> selectObj = new ArrayList<Object>();
		Map<String, Object> result = new ConcurrentHashMap<String, Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				sb.append(" select handicap_id, handicap_name, bonus_set_max, bonus_set_min, relative_baseline, max_win_bonus from `ctt_manager`.`ctt_lottery_handicap`  \n");
				sb.append(" where (index_of_account & (select handicap from `ctt_manager`.`ctt_member_acc` where acc_id = ?))=index_of_account order by handicap_id asc; \n");
				
				ps = this.READ_CONN.prepareStatement(sb.toString());				
				ps.setLong(1, accId);
				rs = ps.executeQuery();
				mapAll = new HashMap<String, Map<String, String>>();
				mapL4 = new HashMap<String, String>();
				int L4Index = 1;				
				while (rs.next()) {
					mapL4.clear();
//					mapL4 = null;
//					mapL4 = new HashMap<String, String>();
					mapL4.put("handicap_name", rs.getString("handicap_name"));
					mapL4.put("handicap_id", rs.getString("handicap_id"));
					mapL4.put("bonus_set_max", rs.getString("bonus_set_max"));
					mapL4.put("bonus_set_min", rs.getString("bonus_set_min"));
					mapL4.put("max_win_bonus", rs.getString("max_win_bonus"));
					mapL4.put("relative_baseline", rs.getString("relative_baseline"));
					mapAll.put(""+L4Index+"-"+rs.getString("handicap_name"), (HashMap<String, String>) mapL4.clone());
					L4Index ++;
				}
				return mapAll;
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
		return mapAll;
	}

	/**
	 * getAllLotteryLastUpdateTime
	 */
	@Override
	public long getAllLotteryLastUpdateTime() {
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
				sb.append("SELECT UNIX_TIMESTAMP(create_date_time) AS t1 FROM `ctt_manager`.`ctt_lottery_auth_list` ORDER BY create_date_time DESC limit 1; ");
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
	 * getSubOrderInfo
	 */
	@Override
	public List<Map<String, Object>> getSubOrderInfo() {
		StringBuilder sb = new StringBuilder();
		List<Object> selectObj = new ArrayList<Object>();
		Map<String, Object> result = new ConcurrentHashMap<String, Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				sb.append("select `played_id`, `played_name`, `played_text`, `note`, `baseline_index` from `ctt_manager`.`ctt_lottery_sub_played` where 1;");
				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);
				LOG.debug(result.toString());
				LOG.debug(result.toString());
				return listMap;
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return listMap;
	}
	
	/**
	 * getSubOrderInfoNoOfBet
	 */
	@Override
	public List<Map<String, Object>> getSubOrderInfoNoOfBet() {
		StringBuilder sb = new StringBuilder();
		List<Object> selectObj = new ArrayList<Object>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				// no connection
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				sb.append("select `type`, `key`, `value`, `text` from `ctt_manager`.`ctt_lottery_betnum` where 1;");
				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);
				LOG.debug(listMap.toString());
				return listMap;
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return listMap;
	}
}
