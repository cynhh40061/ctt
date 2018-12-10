package tw.com.ctt.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.IGameRecordsDao;
import tw.com.ctt.model.PunchGameRecordsBean;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class GameRecordsDaoImpl extends BaseDao implements IGameRecordsDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4521901483699508996L;
	private static final Logger LOG = LogManager.getLogger(GameRecordsDaoImpl.class.getName());

	public GameRecordsDaoImpl() {
		super();
	}
	@Override
	public int searchRecordsTotleCount(long opsAccId, long gameId, String accName, String startTime, String endTime, int gameType) {
		StringBuilder sb = null;
		List<Object> params = null;
		List<Map<String, Object>> tmpMap = null;
		int totleCount = 0;
		try {
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkRead();

			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return totleCount;
			} else {
				sb.append("SELECT COUNT(1) AS count FROM ( \n");
				sb.append("SELECT p1_acc_id AS acc_id \n");
				sb.append("FROM \n");
				sb.append("ctt_manager.ctt_game_punche_records \n");
				sb.append("WHERE \n");
				if(gameId > 0) {
					sb.append("game_id = ?\n");
					params.add(gameId);
					sb.append("AND \n");
				}
				if(!"".equals(accName)) {	
					sb.append("(p1_acc_id = (SELECT acc_id FROM ctt_manager.ctt_member_acc WHERE acc_name = ?)) \n");
					params.add(accName);
					sb.append("AND \n");
				}
				sb.append("(start_time > ? && end_time < ?) \n");
				params.add(startTime);
				params.add(endTime);
				if(gameType > 0) {	
					sb.append("AND \n");
					sb.append("game_type = ?\n");
					params.add(gameType);
				}
				sb.append("AND \n");
				sb.append("p1_acc_id IN(SELECT A.acc_id FROM (SELECT acc_id FROM ctt_manager.ctt_member_acc_level WHERE \n");
				sb.append("(CASE(SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				params.add(opsAccId);
				sb.append("WHEN 0 THEN TRUE \n");
				sb.append("WHEN 1 THEN TRUE \n");
				sb.append("WHEN 2 THEN sc \n");
				sb.append("WHEN 3 THEN bc \n");
				sb.append("WHEN 4 THEN co \n");
				sb.append("WHEN 5 THEN sa \n");
				sb.append("WHEN 6 THEN ag \n");
				sb.append("WHEN 7 THEN ag1 \n");
				sb.append("WHEN 8 THEN ag2 \n");
				sb.append("WHEN 9 THEN ag3 \n");
				sb.append("WHEN 10 THEN ag4 \n");
				sb.append("WHEN 11 THEN ag5 \n");
				sb.append("WHEN 12 THEN ag6 \n");
				sb.append("WHEN 13 THEN ag7 \n");
				sb.append("WHEN 14 THEN ag8 \n");
				sb.append("WHEN 15 THEN ag9 \n");
				sb.append("WHEN 16 THEN ag10 \n");
				sb.append("END ) = ?)A)\n");
				params.add(opsAccId);
				sb.append("UNION ALL\n");
				sb.append("SELECT p2_acc_id AS acc_id \n");
				sb.append("FROM \n");
				sb.append("ctt_manager.ctt_game_punche_records \n");
				sb.append("WHERE \n");
				if(gameId > 0) {
					sb.append("game_id = ?\n");
					params.add(gameId);
					sb.append("AND \n");
				}
				if(!"".equals(accName)) {	
					sb.append("(p2_acc_id = (SELECT acc_id FROM ctt_manager.ctt_member_acc WHERE acc_name = ?)) \n");
					params.add(accName);
					sb.append("AND \n");
				}
				sb.append("(start_time > ? && end_time < ?) \n");
				params.add(startTime);
				params.add(endTime);
				if(gameType > 0) {	
					sb.append("AND \n");
					sb.append("game_type = ?\n");
					params.add(gameType);
				}
				sb.append("AND \n");
				sb.append("p2_acc_id IN(SELECT B.acc_id FROM (SELECT acc_id FROM ctt_manager.ctt_member_acc_level WHERE \n");
				sb.append("(CASE(SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				params.add(opsAccId);
				sb.append("WHEN 0 THEN TRUE \n");
				sb.append("WHEN 1 THEN TRUE \n");
				sb.append("WHEN 2 THEN sc \n");
				sb.append("WHEN 3 THEN bc \n");
				sb.append("WHEN 4 THEN co \n");
				sb.append("WHEN 5 THEN sa \n");
				sb.append("WHEN 6 THEN ag \n");
				sb.append("WHEN 7 THEN ag1 \n");
				sb.append("WHEN 8 THEN ag2 \n");
				sb.append("WHEN 9 THEN ag3 \n");
				sb.append("WHEN 10 THEN ag4 \n");
				sb.append("WHEN 11 THEN ag5 \n");
				sb.append("WHEN 12 THEN ag6 \n");
				sb.append("WHEN 13 THEN ag7 \n");
				sb.append("WHEN 14 THEN ag8 \n");
				sb.append("WHEN 15 THEN ag9 \n");
				sb.append("WHEN 16 THEN ag10 \n");
				sb.append("END ) = ?)B) \n");
				params.add(opsAccId);
				sb.append(") C \n");

				tmpMap = StmtUtil.queryToMap(READ_CONN, sb.toString(), params);
				if (tmpMap.size() == 1) {
					totleCount = Integer.parseInt(tmpMap.get(0).get("count").toString());
				}
			}
		} catch (Exception e) {
			totleCount = 0;
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
			if (tmpMap != null) {
				tmpMap.clear();
				tmpMap = null;
			}
		}
		return totleCount;
	}

	@Override
	public List<Map<String, Object>> searchRecords(long opsAccId, long gameId, String accName, String startTime, String endTime, int gameType, int firstCount,
			int count) {
		StringBuilder sb = null;
		List<Object> params = null;
		List<Object> list = null;
		Map<String, Object> tmpMap = null;
		List<Map<String, Object>> tmpMapList = null;
		try {
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkRead();
			tmpMapList = new ArrayList<Map<String, Object>>();

			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return null;
			} else {
				sb.append("SELECT game_id,server_id,p1_acc_id AS acc_id \n");
				sb.append(",(SELECT acc_name FROM ctt_manager.ctt_member_acc WHERE acc_id = p1_acc_id) AS acc_name \n");
				sb.append(",start_time,end_time,bet,p1_acc_start_balance AS acc_start_balance,p1_acc_win_goal AS acc_win_goal,game_process,game_type,result \n");
				sb.append("FROM \n");
				sb.append("ctt_manager.ctt_game_punche_records \n");
				sb.append("WHERE \n");
				if(gameId > 0) {
					sb.append("game_id = ?\n");
					params.add(gameId);
					sb.append("AND \n");
				}
				if(!"".equals(accName)) {	
					sb.append("(p1_acc_id = (SELECT acc_id FROM ctt_manager.ctt_member_acc WHERE acc_name = ?)) \n");
					params.add(accName);
					sb.append("AND \n");
				}
				sb.append("(start_time > ? && end_time < ?) \n");
				params.add(startTime);
				params.add(endTime);
				if(gameType > 0) {	
					sb.append("AND \n");
					sb.append("game_type = ?\n");
					params.add(gameType);
				}
				sb.append("AND \n");
				sb.append("p1_acc_id IN(SELECT A.acc_id FROM (SELECT acc_id FROM ctt_manager.ctt_member_acc_level WHERE \n");
				sb.append("(CASE(SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				params.add(opsAccId);
				sb.append("WHEN 0 THEN TRUE \n");
				sb.append("WHEN 1 THEN TRUE \n");
				sb.append("WHEN 2 THEN sc \n");
				sb.append("WHEN 3 THEN bc \n");
				sb.append("WHEN 4 THEN co \n");
				sb.append("WHEN 5 THEN sa \n");
				sb.append("WHEN 6 THEN ag \n");
				sb.append("WHEN 7 THEN ag1 \n");
				sb.append("WHEN 8 THEN ag2 \n");
				sb.append("WHEN 9 THEN ag3 \n");
				sb.append("WHEN 10 THEN ag4 \n");
				sb.append("WHEN 11 THEN ag5 \n");
				sb.append("WHEN 12 THEN ag6 \n");
				sb.append("WHEN 13 THEN ag7 \n");
				sb.append("WHEN 14 THEN ag8 \n");
				sb.append("WHEN 15 THEN ag9 \n");
				sb.append("WHEN 16 THEN ag10 \n");
				sb.append("END ) = ?)A)\n");
				params.add(opsAccId);
				sb.append("UNION \n");
				sb.append("SELECT game_id,server_id,p2_acc_id AS acc_id \n");
				sb.append(",(SELECT acc_name FROM ctt_manager.ctt_member_acc WHERE acc_id = p2_acc_id) AS acc_name \n");
				sb.append(",start_time,end_time,bet,p2_acc_start_balance AS acc_start_balance,p2_acc_win_goal AS acc_win_goal,game_process,game_type,result \n");
				sb.append("FROM \n");
				sb.append("ctt_manager.ctt_game_punche_records \n");
				sb.append("WHERE \n");
				if(gameId > 0) {
					sb.append("game_id = ?\n");
					params.add(gameId);
					sb.append("AND \n");
				}
				if(!"".equals(accName)) {	
					sb.append("(p2_acc_id = (SELECT acc_id FROM ctt_manager.ctt_member_acc WHERE acc_name = ?)) \n");
					params.add(accName);
					sb.append("AND \n");
				}
				sb.append("(start_time > ? && end_time < ?) \n");
				params.add(startTime);
				params.add(endTime);
				if(gameType > 0) {	
					sb.append("AND \n");
					sb.append("game_type = ?\n");
					params.add(gameType);
				}
				sb.append("AND \n");
				sb.append("p2_acc_id IN(SELECT B.acc_id FROM (SELECT acc_id FROM ctt_manager.ctt_member_acc_level WHERE \n");
				sb.append("(CASE(SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				params.add(opsAccId);
				sb.append("WHEN 0 THEN TRUE \n");
				sb.append("WHEN 1 THEN TRUE \n");
				sb.append("WHEN 2 THEN sc \n");
				sb.append("WHEN 3 THEN bc \n");
				sb.append("WHEN 4 THEN co \n");
				sb.append("WHEN 5 THEN sa \n");
				sb.append("WHEN 6 THEN ag \n");
				sb.append("WHEN 7 THEN ag1 \n");
				sb.append("WHEN 8 THEN ag2 \n");
				sb.append("WHEN 9 THEN ag3 \n");
				sb.append("WHEN 10 THEN ag4 \n");
				sb.append("WHEN 11 THEN ag5 \n");
				sb.append("WHEN 12 THEN ag6 \n");
				sb.append("WHEN 13 THEN ag7 \n");
				sb.append("WHEN 14 THEN ag8 \n");
				sb.append("WHEN 15 THEN ag9 \n");
				sb.append("WHEN 16 THEN ag10 \n");
				sb.append("END ) = ?)B) \n");
				params.add(opsAccId);
				sb.append("ORDER BY end_time DESC \n");
				sb.append("limit ?,? \n");
				params.add(firstCount);
				params.add(count);

				list = new ArrayList<Object>();
				list = StmtUtil.queryToBean(READ_CONN, sb.toString(), params, new PunchGameRecordsBean());
				if (list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						PunchGameRecordsBean bean = (PunchGameRecordsBean) list.get(i);
						JSONObject jsonMap = new JSONObject(bean.getGameProcess());
						String searchKey[] = { "gameName", "accName", "accId", "gameId", "gameType", "startTime", "endTime", "startBalance", "endBalance", "bet",
								"netAmount", "winGoal" , "fees", "gameTimes", "gameProcess" };
						Object searchByAccNameValue[] = { "三國猜拳王", "" + bean.getAccName(), Long.parseLong("" + bean.getAccId()),
								""+bean.getGameId(), Integer.parseInt("" + bean.getGameType()), bean.getStartTime(), bean.getEndTime(),
								new BigDecimal("" + bean.getAccStartBalance()),
								new BigDecimal("" + bean.getAccStartBalance()).add(new BigDecimal("" + bean.getAccWinGoal())),
								new BigDecimal("" + bean.getBet()), new BigDecimal("" + bean.getAccWinGoal()),
								new BigDecimal("" + bean.getBet()).add(new BigDecimal("" + bean.getAccWinGoal())), new BigDecimal("" + bean.getBet()).multiply(new BigDecimal("0.05")), jsonMap.get("gameTimes"),
								jsonMap.get("gameProcess") };
						tmpMap = new ConcurrentHashMap<String, Object>();
						for (int k = 0; k < searchKey.length; k++) {
							tmpMap.put(searchKey[k], searchByAccNameValue[k]);
						}
						tmpMapList.add(tmpMap);
						}
				}
			}
		} catch (Exception e) {
			tmpMapList = null;
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
			if (list != null) {
				list.clear();
				list = null;
			}
		}
		return tmpMapList;
	}

}
