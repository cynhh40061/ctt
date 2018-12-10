package tw.com.ctt.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.ISetGameDao;
import tw.com.ctt.model.SetGameParamBean;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class SetGameDaoImpl extends BaseDao implements ISetGameDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7974622100218940980L;
	private static final Logger LOG = LogManager.getLogger(SetGameDaoImpl.class.getName());
	
	public SetGameDaoImpl() {
		super();
	}
	@Override
	public List<Map<String, Object>> getGameParamData(long accId) {
		StringBuilder sb = null;
		List<Object> params = null;
		List<Object> list = null;
		Map<String, Object> tmpMap = null;
		List<Map<String, Object>> tmpMapList = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return tmpMapList;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();
				list = new ArrayList<Object>();
				tmpMapList = new ArrayList<Map<String, Object>>();

				sb.append("SELECT  \n");
				sb.append("sid \n");
				sb.append(",game_server_id \n");
				sb.append(",platform_id \n");
				sb.append(",game_times_type \n");
				sb.append(",next_game_times_type \n");
				sb.append(",bet \n");
				sb.append(",next_bet \n");
				sb.append(",game_time_out \n");
				sb.append(",next_game_time_out \n");
				sb.append(",wait_continue_time_out \n");
				sb.append(",next_wait_continue_time_out \n");
				sb.append(",commission \n");
				sb.append(",next_commission \n");
				sb.append(",continue_times_type \n");
				sb.append(",next_continue_times_type \n");
				sb.append("FROM `ctt_manager`.`ctt_manager_game_set` \n");
				sb.append("WHERE \n");
				sb.append("(SELECT acc_level_type FROM `ctt_manager`.`ctt_manager_acc_level` WHERE acc_id = ?) = 0 \n");
				params.add(accId);

				list = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), params, new SetGameParamBean());
				if (list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						SetGameParamBean bean = (SetGameParamBean) list.get(i);
						tmpMap = new ConcurrentHashMap<String, Object>();
						String searchKey[] = { "sid", "gameServerId", "platformId", "gameTimesType", "nextGameTimesType", "bet", "nextBet",
								"gameTimeOut", "nextGameTimeOut", "waitContinueTimeOut", "nextWaitContinueTimeOut", "commission",
								"nextCommission", "continueTimesType", "nextContinueTimesType" };
						Object searchByAccNameValue[] = { Integer.parseInt("" + bean.getSid()), "" + bean.getGameServerId(),
								Integer.parseInt("" + bean.getPlatformId()), Integer.parseInt("" + bean.getGameTimesType()),
								Integer.parseInt("" + bean.getNextGameTimesType()), Integer.parseInt("" + bean.getBet()),
								Integer.parseInt("" + bean.getNextBet()), Integer.parseInt("" + bean.getGameTimeOut()),
								Integer.parseInt("" + bean.getNextGameTimeOut()), Integer.parseInt("" + bean.getWaitContinueTimeOut()),
								Integer.parseInt("" + bean.getNextWaitContinueTimeOut()), Integer.parseInt("" + bean.getCommission()),
								Integer.parseInt("" + bean.getNextCommission()), Integer.parseInt("" + bean.getContinueTimesType()),
								Integer.parseInt("" + bean.getNextContinueTimesType()) };
						for (int k = 0; k < searchKey.length; k++) {
							tmpMap.put(searchKey[k], searchByAccNameValue[k]);
						}
						tmpMapList.add(tmpMap);
					}
				}
			}
		} catch (Exception e) {
			tmpMapList = null;
			LOG.debug("getGameParamData_Exception, " + e.getMessage());
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
	@Override
	public boolean updateGameParamData(int sid, int bet, int gameTimesType, int gameTimeOut, int waitContinueTimeOut, int commission, int continueTimesType, long accId) {
		StringBuilder sb = null;
		List<Object> params = null;
		boolean todo = false;
		int cou = 0;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				sb = new StringBuilder();
				params = new ArrayList<Object>();

				sb.append("UPDATE `ctt_manager`.`ctt_manager_game_set` \n");
				sb.append("SET \n");
				sb.append("bet=? \n");
				params.add(bet);
				sb.append(",game_time_out=? \n");
				params.add(gameTimeOut);
				sb.append(",wait_continue_time_out=? \n");
				params.add(waitContinueTimeOut);
				sb.append(",continue_times_type=? \n");
				params.add(continueTimesType);
				sb.append(",commission=? \n");
				params.add(commission);
				sb.append(",game_times_type=? \n");
				params.add(gameTimesType);
				sb.append("WHERE \n");
				sb.append("sid=? \n");
				params.add(sid);
				sb.append("AND \n");
				sb.append("(SELECT acc_level_type FROM `ctt_manager`.`ctt_manager_acc_level` WHERE acc_id = ?) = 0 \n");
				params.add(accId);

				cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);
				if(cou == 1) {
					todo = true;
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("getGameParamData_Exception, " + e.getMessage());
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
			cou = 0;
		}
		return todo;
	}
}
