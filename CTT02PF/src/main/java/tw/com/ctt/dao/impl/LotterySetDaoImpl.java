package tw.com.ctt.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.ILotterySetDao;
import tw.com.ctt.model.LotteryAmountSetBean;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class LotterySetDaoImpl extends BaseDao implements ILotterySetDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5634853422852503648L;
	private static final Logger LOG = LogManager.getLogger(LotterySetDaoImpl.class.getName());

	@Override
	public List<Map<String, Object>> getLotteryTypeList() {
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				sb = new StringBuilder();
				sb.append("SELECT auth_id AS type_id,lottery_title AS type_name FROM `ctt_manager`.`ctt_lottery_auth` WHERE auth_level_type = 1 \n");
				sb.append("ORDER BY auth_id ASC \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), new ArrayList<>());
				LOG.debug(listMap.toString());
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> getLotteryMidList() {
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				sb = new StringBuilder();
				sb.append("SELECT auth_id AS mid_id,lottery_title AS mid_name FROM `ctt_manager`.`ctt_lottery_auth` WHERE auth_level_type = 3 \n");
				sb.append("ORDER BY auth_id ASC \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), new ArrayList<>());
				LOG.debug(listMap.toString());
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> getUpperSwitchInfo() {
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				sb = new StringBuilder();
				sb.append("SELECT A.id,type,A.title,switch,open_switch,all_switch FROM  \n");
				sb.append("(SELECT id,type,title FROM `ctt_manager`.`ctt_lottery_type`) A \n");
				sb.append("INNER JOIN \n");
				sb.append(
						"(SELECT B.id,C.type_id,(CASE WHEN (SUM((CASE WHEN (B.switch) > 0 THEN 1 ELSE 0 END))) > 0 THEN TRUE ELSE FALSE END) AS switch,SUM(switch) as open_switch,COUNT(switch) as all_switch FROM \n");
				sb.append("	(SELECT id,auth_id,switch FROM `ctt_manager`.`ctt_lottery_auth_list`) B \n");
				sb.append("	INNER JOIN \n");
				sb.append("	(SELECT level1_id AS type_id,auth_id FROM `ctt_manager`.`ctt_lottery_auth` WHERE auth_level_type = 3) C \n");
				sb.append("	ON B.auth_id = C.auth_id GROUP BY id \n");
				sb.append(") D \n");
				sb.append("ON A.id = D.id \n");
				sb.append("ORDER BY A.id asc,type asc \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), new ArrayList<>());
				LOG.debug(listMap.toString());
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> getSwitchDetailTitle(int lotteryId) {
		List<Map<String, Object>> listMap = null;
		List<Object> array = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				array = new ArrayList<Object>();
				sb = new StringBuilder();
				sb.append("SELECT auth_id AS played_id,lottery_title \n");
				sb.append("FROM  \n");
				sb.append("`ctt_manager`.`ctt_lottery_auth` \n");
				sb.append("WHERE \n");
				sb.append("level1_id = (SELECT type FROM `ctt_manager`.`ctt_lottery_type` WHERE id = ?) \n");
				array.add(lotteryId);
				sb.append("AND \n");
				sb.append("auth_level_type = 2 \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), array);
				LOG.debug(listMap.toString());
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> getSwitchDetail(int lotteryId) {
		List<Map<String, Object>> listMap = null;
		List<Object> array = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				array = new ArrayList<Object>();
				sb = new StringBuilder();
				sb.append("SELECT F.played_id,F.mid_id,F.mid_title,switch \n");
				sb.append("FROM \n");
				sb.append("(SELECT level2_id AS played_id,auth_id AS mid_id,lottery_title AS mid_title \n");
				sb.append("FROM \n");
				sb.append("`ctt_manager`.`ctt_lottery_auth` \n");
				sb.append("WHERE  \n");
				sb.append("level2_id IN(SELECT auth_id FROM `ctt_manager`.`ctt_lottery_auth` WHERE \n");
				sb.append("  level1_id = (SELECT type FROM `ctt_manager`.`ctt_lottery_type` WHERE id = ?) AND auth_level_type = 2) \n");
				array.add(lotteryId);
				sb.append("  AND auth_level_type = 3) F \n");
				sb.append("INNER JOIN \n");
				sb.append("(SELECT A.auth_id,switch FROM \n");
				sb.append("(SELECT auth_id,switch FROM `ctt_manager`.`ctt_lottery_auth_list` WHERE id = ?) A \n");
				array.add(lotteryId);
				sb.append("INNER JOIN \n");
				sb.append("(SELECT auth_id FROM `ctt_manager`.`ctt_lottery_auth` WHERE auth_level_type = 3) B \n");
				sb.append("ON A.auth_id = B.auth_id) C \n");
				sb.append("ON F.mid_id = C.auth_id \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), array);
				LOG.debug(listMap.toString());
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return listMap;
	}

	@Override
	public boolean updateLotterySwitch(int lotteryId, int midId, int lotterySwitch) {
		List<Object> params = null;
		StringBuilder sb = null;
		int count = 0;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				params = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("UPDATE ctt_manager.ctt_lottery_auth_list SET \n");
				sb.append("switch = ? \n");
				params.add(lotterySwitch);

				sb.append("WHERE id = ? AND auth_id = ? \n");
				params.add(lotteryId);
				params.add(midId);

				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);

				if (count == 1) {
					todo = true;
				} else {
					todo = false;
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (params != null) {
				params = new ArrayList<Object>();
				params = null;
			}
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return todo;
	}

	@Override
	public List<Map<String, Object>> getLotteryAmountLocalList() {
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				sb = new StringBuilder();
				sb.append("SELECT id,type,title FROM `ctt_manager`.`ctt_lottery_type` \n");
				sb.append("ORDER BY id ASC,type ASC \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), new ArrayList<>());
				LOG.debug(listMap.toString());
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> getLotteryAmountPlayedList() {
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				sb = new StringBuilder();
				sb.append(
						"SELECT level1_id AS type_id,auth_id AS played_id,lottery_title AS played_name,sort_level1_id,sort_level2_id,sort_level3_id FROM `ctt_manager`.`ctt_lottery_auth`  \n");
				sb.append("WHERE \n");
				sb.append("level1_id IN ( \n");
				sb.append(" SELECT auth_id FROM `ctt_manager`.`ctt_lottery_auth` WHERE auth_level_type = 1) \n");
				sb.append("AND auth_level_type = 2 \n");
				sb.append("ORDER BY sort_level1_id ASC , sort_level2_id ASC \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), new ArrayList<>());
				LOG.debug(listMap.toString());
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> getHandicapInfo() {
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				sb = new StringBuilder();
				sb.append(
						"SELECT handicap_id,handicap_name,bonus_set_max,bonus_set_min,relative_baseline,index_of_account,max_win_bonus FROM `ctt_manager`.`ctt_lottery_handicap` \n");
				sb.append("ORDER BY handicap_id ASC \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), new ArrayList<>());
				LOG.debug(listMap.toString());
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> getTotalAmountInfo() {
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
				this.checkRead();
				tmpMapList = new ArrayList<Map<String, Object>>();

				sb.append("SELECT  \n");
				sb.append(
						"lottery_local_id AS local_id,lottery_type_id AS type_id,lottery_mid_id AS mid_id,lottery_min_id AS min_id,min_name,base_bet,bet_level1,bet_level2,baseline,baseline_level1,baseline_level2,dt_switch,dt_ratio,dt_bonus,played_text,lottery_rule,lottery_example,prize_level \n");
				sb.append("FROM \n");
				sb.append(
						"(SELECT lottery_local_id,lottery_type_id,lottery_mid_id,lottery_min_id,base_bet,bet_level1,bet_level2,baseline,baseline_level1,baseline_level2,dt_switch,dt_ratio,dt_bonus,prize_level FROM `ctt_manager`.`ctt_lottery_amount`) A \n");
				sb.append("LEFT JOIN  \n");
				sb.append(
						"(SELECT level1_id AS type_id,level2_id AS mid_id,auth_id AS min_id,lottery_title AS min_name,played_text,lottery_rule,lottery_example FROM `ctt_manager`.`ctt_lottery_auth` \n");
				sb.append("WHERE auth_level_type = 3) B \n");
				sb.append("ON A.lottery_type_id = B.type_id AND lottery_type_id = type_id AND lottery_min_id = min_id \n");
				sb.append("ORDER BY local_id ASC,type_id ASC,mid_id ASC,min_id ASC,prize_level ASC \n");

				list = new ArrayList<Object>();
				list = StmtUtil.queryToBean(READ_CONN, sb.toString(), params, new LotteryAmountSetBean());

				if (list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						LotteryAmountSetBean bean = (LotteryAmountSetBean) list.get(i);
						String key[] = { "localId", "typeId", "midId", "minId", "minName", "baseBet", "betLevel1", "betLevel2", "baseline",
								"baselineLevel1", "baselineLevel2", "dtSwitch", "dtRatio", "dtBonus", "playedText", "lotteryRule", "lotteryExample",
								"prizeLevel" };
						Object value[] = { Integer.parseInt("" + bean.getLocalId()), Integer.parseInt("" + bean.getTypeId()),
								Integer.parseInt("" + bean.getMidId()), Integer.parseInt("" + bean.getMinId()), "" + bean.getMinName(),
								new BigDecimal("" + bean.getBaseBet()), new BigDecimal("" + bean.getBetLevel1()),
								new BigDecimal("" + bean.getBetLevel2()), new BigDecimal("" + bean.getBaseline()),
								new BigDecimal("" + bean.getBaselineLevel1()), new BigDecimal("" + bean.getBaselineLevel2()),
								Boolean.parseBoolean("" + bean.isDtSwitch()), new BigDecimal("" + bean.getDtRatio()),
								new BigDecimal("" + bean.getDtBonus()), "" + bean.getPlayedText(), "" + bean.getLotteryRule(),
								"" + bean.getLotteryExample(), Integer.parseInt("" + bean.getPrizeLevel()) };
						tmpMap = new ConcurrentHashMap<String, Object>();
						for (int k = 0; k < key.length; k++) {
							tmpMap.put(key[k], value[k]);
						}
						tmpMapList.add(tmpMap);
					}
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
		}
		return tmpMapList;
	}

	@Override
	public boolean upadteDescription(int minId, String playedText, String lotteryRule, String lotteryExample) {
		List<Object> params = null;
		StringBuilder sb = null;
		int count = 0;
		try {
			params = new ArrayList<Object>();
			sb = new StringBuilder();
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb.append("UPDATE ctt_manager.ctt_lottery_auth SET \n");
				sb.append("played_text = ? , \n");
				params.add(playedText);
				sb.append("lottery_rule = ? , \n");
				params.add(lotteryRule);
				sb.append("lottery_example = ? \n");
				params.add(lotteryExample);
				sb.append("WHERE auth_id = ? \n");
				params.add(minId);

				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);
				if (count == 1) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			LOG.debug("updateRechargeAuditOrder_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				rollBackDB();
			} catch (SQLException e1) {
				LOG.debug("rollBackDB_Exception===" + e.getMessage());
				ShowLog.err(LOG, e);
			}
			return false;
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
			if (params != null) {
				params.clear();
				params = null;
			}
			count = 0;
		}
	}

	@Override
	public boolean updateAmountInfo(int localId, int typeId, int minId, BigDecimal baseBet, BigDecimal betLevel1, BigDecimal betLevel2,
			BigDecimal baseline, BigDecimal baselineLevel1, BigDecimal baselineLevel2, boolean dtSwitch, BigDecimal dtRatio, BigDecimal dtBonus,
			int prizeLevel) {
		List<Object> params = null;
		StringBuilder sb = null;
		int count = 0;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				params = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("UPDATE ctt_manager.ctt_lottery_amount SET \n");
				sb.append("base_bet = ? , bet_level1 = ? , bet_level2 = ? , \n");
				params.add(baseBet);
				params.add(betLevel1);
				params.add(betLevel2);

				sb.append("baseline = ? , baseline_level1 = ? , baseline_level2 = ? , \n");
				params.add(baseline);
				params.add(baselineLevel1);
				params.add(baselineLevel2);

				sb.append("dt_switch = ? , dt_ratio = ? , dt_bonus = ?\n");
				params.add(dtSwitch);
				params.add(dtRatio);
				params.add(dtBonus);

				sb.append("WHERE lottery_local_id = ? AND lottery_type_id = ? AND lottery_min_id = ? AND prize_level = ? \n");
				params.add(localId);
				params.add(typeId);
				params.add(minId);
				params.add(prizeLevel);

				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);

				if (count == 1) {
					todo = true;
				} else {
					todo = false;
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("rollBackDB_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
			if (params != null) {
				params.clear();
				params = null;
			}
			count = 0;
		}
		return todo;
	}

	@Override
	public boolean upadteHandicap(int handicapId, BigDecimal bonusSetMin, BigDecimal bonusSetMax, int relativeBaseline, int maxWinBonus) {
		List<Object> params = null;
		StringBuilder sb = null;
		int count = 0;
		try {
			params = new ArrayList<Object>();
			sb = new StringBuilder();
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb.append("UPDATE ctt_manager.ctt_lottery_handicap SET \n");
				sb.append("bonus_set_min = ? , \n");
				params.add(bonusSetMin);
				sb.append("bonus_set_max = ? , \n");
				params.add(bonusSetMax);
				sb.append("relative_baseline = ? , \n");
				params.add(relativeBaseline);
				sb.append("max_win_bonus = ? \n");
				params.add(maxWinBonus);
				sb.append("WHERE handicap_id = ? \n");
				params.add(handicapId);

				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);
				if (count == 1) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			LOG.debug("updateRechargeAuditOrder_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
			try {
				rollBackDB();
			} catch (SQLException e1) {
				LOG.debug("rollBackDB_Exception===" + e.getMessage());
				ShowLog.err(LOG, e);
			}
			return false;
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
			if (params != null) {
				params.clear();
				params = null;
			}
			count = 0;
		}
	}

	@Override
	public List<Map<String, Object>> getMidSwitchInfo(int localId) {
		List<Map<String, Object>> listMap = null;
		List<Object> array = null;
		StringBuilder sb = null;
		try {
			this.checkWrite();// 未commit，要用write
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				array = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("SELECT id AS local_id,mid_id,(CASE WHEN (SUM(A.switch)) > 0 THEN TRUE ELSE FALSE END) AS switch FROM \n");
				sb.append("(SELECT id,auth_id,switch FROM `ctt_manager`.`ctt_lottery_auth_list` WHERE id = ?) A \n");
				array.add(localId);
				sb.append("INNER JOIN \n");
				sb.append(
						"(SELECT level1_id AS type_id,level2_id AS mid_id,auth_id AS min_id FROM `ctt_manager`.`ctt_lottery_auth` WHERE auth_level_type = 3) B \n");
				sb.append("ON A.auth_id = B.min_id GROUP BY mid_id \n");

				listMap = StmtUtil.queryToMap(this.WRITE_CONN, sb.toString(), array);
				LOG.debug(listMap.toString());
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (array != null) {
				array.clear();
				array = null;
			}
		}
		return listMap;
	}

	@Override
	public boolean updateMidSwitch(int localId, int midId, int midSwitch) {
		List<Object> params = null;
		StringBuilder sb = null;
		int count = 0;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				params = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("UPDATE ctt_manager.ctt_lottery_auth_list SET \n");
				sb.append("switch = ? \n");
				params.add(midSwitch);

				sb.append("WHERE id = ? AND auth_id = ? \n");
				params.add(localId);
				params.add(midId);

				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);

				if (count == 1) {
					todo = true;
				} else {
					todo = false;
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("rollBackDB_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
			if (params != null) {
				params.clear();
				params = null;
			}
			count = 0;
		}
		return todo;
	}

	@Override
	public boolean updateTotalSwitch(int localId, int totalSwitch) {
		List<Object> params = null;
		StringBuilder sb = null;
		int count = 0;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				params = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("UPDATE `ctt_manager`.`ctt_lottery_auth_list` SET \n");
				sb.append("switch = ? \n");
				params.add(totalSwitch);

				sb.append("WHERE id = ? AND auth_id = (SELECT type FROM `ctt_manager`.`ctt_lottery_type` WHERE id = ?) \n");
				params.add(localId);
				params.add(localId);

				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);

				if (count == 1) {
					todo = true;
				} else {
					todo = false;
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("rollBackDB_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
			if (params != null) {
				params.clear();
				params = null;
			}
			count = 0;
		}
		return todo;
	}

	@Override
	public boolean upadteZero() {
		List<Object> params = null;
		StringBuilder sb = null;
		int count = 0;
		boolean todo = false;

		try {
			params = new ArrayList<Object>();
			sb = new StringBuilder();
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb.append("UPDATE ctt_manager.ctt_lottery_auth_list SET \n");
				sb.append(" switch = CASE WHEN switch=1 THEN 0 ELSE 1 END \n");
				sb.append("WHERE id = 0 AND auth_id = 0 \n");

				count = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), params);
				if (count == 1) {
					todo = true;
				} else {
					todo = false;
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("upadteZero_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
			}
			sb = null;
			if (params != null) {
				params.clear();
				params = null;
			}
			count = 0;
		}
		return todo;
	}

	@Override
	public List<Map<String, Object>> getLotterySwitchSettingLog(long opsAccId, int upperAction) {
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		List<Object> params = null;
		try {
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				sb.append("SELECT \n");
				sb.append("ops_acc_id ,ops_acc_name ,action ,action_text ,detail ,ops_datetime ,CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) AS ip \n");
				sb.append("FROM \n");
				sb.append("	(SELECT \n");
				sb.append("	ops_acc_id ,ops_acc_name ,action ,detail ,ops_datetime ,ip1 ,ip2 ,ip3 ,ip4 \n");
				sb.append("	FROM \n");
				sb.append("		( \n");
				sb.append(
						"		SELECT ops_acc_id ,ops_acc_name ,action ,detail ,ops_datetime ,ip1 ,ip2 ,ip3 ,ip4 FROM `ctt_manager`.`ctt_lottery_set_ops_log` \n");
				sb.append("		WHERE \n");
				sb.append("		ops_acc_id IN \n");
				sb.append("			(SELECT acc_id FROM ctt_manager.ctt_manager_acc_level \n");
				sb.append("				WHERE(CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				params.add(opsAccId);
				sb.append("				WHEN 0 THEN TRUE \n");
				sb.append("				WHEN 1 THEN TRUE \n");
				sb.append("				WHEN 2 THEN sc = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 3 THEN bc = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 4 THEN co = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 5 THEN sa = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 6 THEN ag = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 7 THEN ag1 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 8 THEN ag2 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 9 THEN ag3 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 10 THEN ag4 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 11 THEN ag5 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 12 THEN ag6 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 13 THEN ag7 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 14 THEN ag8 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 15 THEN ag9 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 16 THEN ag10 = ? ELSE FALSE END)) \n");
				params.add(opsAccId);
				sb.append("		AND action IN (SELECT action_id FROM `ctt_manager`.`ctt_lottery_log_action` WHERE upper_id = ?) \n");
				params.add(upperAction);
				sb.append("		) A \n");
				sb.append("	)detail \n");
				sb.append("LEFT JOIN \n");
				sb.append("(SELECT action_id ,action_text ,upper_id FROM `ctt_manager`.`ctt_lottery_log_action`) act \n");
				sb.append("ON detail.action = act.action_id \n");
				sb.append("ORDER BY ops_datetime DESC \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				if (listMap == null) {
					LOG.debug("showOpsLog_listMap is null");
				} else {
					LOG.debug("showOpsLog_listMap_size===" + listMap.size());
				}
			}
		} catch (Exception e) {
			listMap = null;
			LOG.debug("showOpsLog_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (params != null) {
				params.clear();
				params = null;
			}
		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> getLotteryAmountSettingLog(long opsAccId, int upperAction) {
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = null;
		List<Object> params = null;
		try {
			sb = new StringBuilder();
			params = new ArrayList<Object>();
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				sb.append("SELECT \n");
				sb.append("ops_acc_id ,ops_acc_name ,action ,action_text ,detail ,ops_datetime ,CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) AS ip \n");
				sb.append("FROM \n");
				sb.append("	(SELECT \n");
				sb.append("	ops_acc_id ,ops_acc_name ,action ,detail ,ops_datetime ,ip1 ,ip2 ,ip3 ,ip4 \n");
				sb.append("	FROM \n");
				sb.append("		( \n");
				sb.append(
						"		SELECT ops_acc_id ,ops_acc_name ,action ,detail ,ops_datetime ,ip1 ,ip2 ,ip3 ,ip4 FROM `ctt_manager`.`ctt_lottery_set_ops_log` \n");
				sb.append("		WHERE \n");
				sb.append("		ops_acc_id IN \n");
				sb.append("			(SELECT acc_id FROM ctt_manager.ctt_manager_acc_level \n");
				sb.append("				WHERE(CASE (SELECT acc_level_type FROM ctt_manager.ctt_manager_acc_level WHERE acc_id = ?) \n");
				params.add(opsAccId);
				sb.append("				WHEN 0 THEN TRUE \n");
				sb.append("				WHEN 1 THEN TRUE \n");
				sb.append("				WHEN 2 THEN sc = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 3 THEN bc = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 4 THEN co = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 5 THEN sa = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 6 THEN ag = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 7 THEN ag1 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 8 THEN ag2 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 9 THEN ag3 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 10 THEN ag4 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 11 THEN ag5 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 12 THEN ag6 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 13 THEN ag7 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 14 THEN ag8 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 15 THEN ag9 = ? \n");
				params.add(opsAccId);
				sb.append("				WHEN 16 THEN ag10 = ? ELSE FALSE END)) \n");
				params.add(opsAccId);
				sb.append("		AND action IN (SELECT action_id FROM `ctt_manager`.`ctt_lottery_log_action` WHERE upper_id = ?) \n");
				params.add(upperAction);
				sb.append("		) A \n");
				sb.append("	)detail \n");
				sb.append("LEFT JOIN \n");
				sb.append("(SELECT action_id ,action_text ,upper_id FROM `ctt_manager`.`ctt_lottery_log_action`) act \n");
				sb.append("ON detail.action = act.action_id \n");
				sb.append("ORDER BY ops_datetime DESC \n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), params);
				if (listMap == null) {
					LOG.debug("showOpsLog_listMap is null");
				} else {
					LOG.debug("showOpsLog_listMap_size===" + listMap.size());
				}
			}
		} catch (Exception e) {
			listMap = null;
			LOG.debug("showOpsLog_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (params != null) {
				params.clear();
				params = null;
			}
		}
		return listMap;
	}
}
