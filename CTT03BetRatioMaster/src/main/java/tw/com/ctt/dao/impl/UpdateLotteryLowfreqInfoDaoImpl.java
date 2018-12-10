package tw.com.ctt.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IUpdateLotteryLowfreqInfoDao;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class UpdateLotteryLowfreqInfoDaoImpl extends BaseDao implements IUpdateLotteryLowfreqInfoDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8819284642674753499L;
	private static final Logger LOG = LogManager.getLogger(UpdateLotteryLowfreqInfoDaoImpl.class.getName());

	@Override
	public boolean upadteHandicap(int handicapId, BigDecimal bonusSetMin, BigDecimal bonusSetMax, int maxWinBonus) {
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
			LOG.debug("upadteHandicap_Exception===" + e.getMessage());
			ShowLog.err(LOG, e);
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
	public boolean updateBaseAmount(int localId, int typeId, int minId, BigDecimal baseBet, BigDecimal baseline, boolean dtSwitch, BigDecimal dtRatio,
			BigDecimal dtBonus, int prizeLevel, int handicap) {
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

				sb.append("UPDATE ctt_manager.ctt_lottery_lowfreq_amount SET \n");
				sb.append("base_bet = ? , \n");
				params.add(baseBet);

				sb.append("baseline = ? , \n");
				params.add(baseline);

				sb.append("dt_switch = ? , dt_ratio = ? , dt_bonus = ?\n");
				params.add(dtSwitch);
				params.add(dtRatio);
				params.add(dtBonus);

				sb.append("WHERE lottery_local_id = ? AND lottery_type_id = ? AND lottery_min_id = ? AND prize_level = ? AND handicap = ? \n");
				params.add(localId);
				params.add(typeId);
				params.add(minId);
				params.add(prizeLevel);
				params.add(handicap);

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
	public boolean updateNowAmount(int localId, int typeId, int minId, BigDecimal nowBaseline, int prizeLevel, int handicap) {
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

				sb.append("UPDATE ctt_manager.ctt_lottery_lowfreq_current_ratio SET \n");
				sb.append("now_baseline = ? \n");
				params.add(nowBaseline);
				sb.append("WHERE lottery_local_id = ? AND lottery_type_id = ? AND lottery_min_id = ? AND prize_level = ? AND handicap = ? AND \n");
				params.add(localId);
				params.add(typeId);
				params.add(minId);
				params.add(prizeLevel);
				params.add(handicap);
				sb.append("	period_num = ( \n");
				sb.append("		SELECT period_num FROM `ctt_manager`.`ctt_lottery` \n");
				sb.append("			WHERE id = ? AND \n");
				params.add(localId);
				sb.append("			DATE_FORMAT(CURRENT_TIMESTAMP, '%Y/%m/%d %T') BETWEEN \n");
				sb.append("			DATE_FORMAT(start_betting_time, '%Y/%m/%d %T') AND DATE_FORMAT(stop_betting_time, '%Y/%m/%d %T')) \n");

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
	public boolean updateMark6BaseAmount(int localId, int typeId, int minId, BigDecimal baseBet, BigDecimal baseline, int prizeLevel, int handicap) {
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

				sb.append("UPDATE ctt_manager.ctt_lottery_lowfreq_amount SET \n");
				sb.append("base_bet = ? , \n");
				params.add(baseBet);

				sb.append("baseline = ? \n");
				params.add(baseline);

				sb.append("WHERE lottery_local_id = ? AND lottery_type_id = ? AND lottery_min_id = ? AND prize_level = ? AND handicap = ? \n");
				params.add(localId);
				params.add(typeId);
				params.add(minId);
				params.add(prizeLevel);
				params.add(handicap);

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
	public boolean updateMark6NowAmount(int localId, int typeId, int minId, BigDecimal nowBaseline, int prizeLevel, int handicap) {
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

				sb.append("UPDATE ctt_manager.ctt_lottery_lowfreq_current_ratio SET \n");
				sb.append("now_baseline = ? \n");
				params.add(nowBaseline);
				sb.append("WHERE lottery_local_id = ? AND lottery_type_id = ? AND lottery_min_id = ? AND prize_level = ? AND handicap = ? AND \n");
				params.add(localId);
				params.add(typeId);
				params.add(minId);
				params.add(prizeLevel);
				params.add(handicap);
				sb.append("	period_num = ( \n");
				sb.append("		SELECT period_num FROM `ctt_manager`.`ctt_lottery` \n");
				sb.append("			WHERE id = ? AND \n");
				params.add(localId);
				sb.append("			DATE_FORMAT(CURRENT_TIMESTAMP, '%Y/%m/%d %T') BETWEEN \n");
				sb.append("			DATE_FORMAT(start_betting_time, '%Y/%m/%d %T') AND DATE_FORMAT(stop_betting_time, '%Y/%m/%d %T')) \n");

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
}
