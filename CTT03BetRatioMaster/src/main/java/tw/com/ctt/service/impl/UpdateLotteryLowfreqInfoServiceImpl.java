package tw.com.ctt.service.impl;

import static tw.com.ctt.constant.LotteryLowfreqSetLogToDBConstant.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.IUpdateLotteryLowfreqInfoDao;
import tw.com.ctt.service.IUpdateLotteryLowfreqInfoSetService;
import tw.com.ctt.util.ShowLog;

public class UpdateLotteryLowfreqInfoServiceImpl extends BaseService implements IUpdateLotteryLowfreqInfoSetService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3634814106667966908L;
	private static final Logger LOG = LogManager.getLogger(UpdateLotteryLowfreqInfoServiceImpl.class.getName());

	public UpdateLotteryLowfreqInfoServiceImpl() {

	}

	public UpdateLotteryLowfreqInfoServiceImpl(long userId, String userIp) {
		super(userId, userIp);
	}

	@Override
	public Boolean upadteHandicap(int handicapId, BigDecimal bonusSetMin, BigDecimal bonusSetMax, int maxWinBonus, String log) {
		boolean todo = false;
		try {
			todo = ((IUpdateLotteryLowfreqInfoDao) dao).upadteHandicap(handicapId, bonusSetMin, bonusSetMax, maxWinBonus);
			if (todo) {
				todo = dao.setLotteryLogToDBNoCommit(USERID, LOG_ACTION_LOTTERY_LOWFREQ_HANDICAP_SET, log, USERIP);
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (Exception e) {
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
		}
		return todo;
	}

	@Override
	public Boolean upadteDescription(int minId, String playedText, String lotteryRule, String lotteryExample, String log) {
		boolean todo = false;
		try {
			todo = ((IUpdateLotteryLowfreqInfoDao) dao).upadteDescription(minId, playedText, lotteryRule, lotteryExample);
			if (todo) {
				((IUpdateLotteryLowfreqInfoDao) dao).upadteZero();
			}
			if (todo) {
				todo = dao.setLotteryLogToDBNoCommit(USERID, LOG_ACTION_LOTTERY_LOWFREQ_DESCRIPTION_SET, log, USERIP);
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (Exception e) {
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
		}
		return todo;
	}

	@Override
	public Boolean updateBaseAmount(List<Map<String, Object>> mapList, String amountLogList) {
		boolean todo = false;
		try {
			for (int i = 0; i < mapList.size(); i++) {
				todo = ((IUpdateLotteryLowfreqInfoDao) dao).updateBaseAmount(Integer.parseInt(mapList.get(i).get("localId").toString()),
						Integer.parseInt(mapList.get(i).get("typeId").toString()), Integer.parseInt(mapList.get(i).get("minId").toString()),
						new BigDecimal(mapList.get(i).get("baseBet").toString()), new BigDecimal(mapList.get(i).get("baseline").toString()),
						Boolean.parseBoolean(mapList.get(i).get("dtSwitch").toString()), new BigDecimal(mapList.get(i).get("dtRatio").toString()),
						new BigDecimal(mapList.get(i).get("dtBonus").toString()), Integer.parseInt(mapList.get(i).get("prizeLevel").toString()),
						Integer.parseInt(mapList.get(i).get("handicap").toString()));
			}
			if (todo) {
				if (Integer.parseInt(mapList.get(0).get("typeId").toString()) == LOTTERY_LOWFREQ_FC3D_TYPE) {
					todo = dao.setLotteryLogToDBNoCommit(USERID, LOG_ACTION_LOTTERY_LOWFREQ_FC3D_BASE_BASELINE_SET, amountLogList, USERIP);
				} else if (Integer.parseInt(mapList.get(0).get("typeId").toString()) == LOTTERY_LOWFREQ_PL5_TYPE) {
					todo = dao.setLotteryLogToDBNoCommit(USERID, LOG_ACTION_LOTTERY_LOWFREQ_PL5_BASE_BASELINE_SET, amountLogList, USERIP);
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (Exception e) {
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
		}
		return todo;
	}

	@Override
	public Boolean updateNowAmount(List<Map<String, Object>> mapList, String logList) {
		boolean todo = false;
		try {
			for (int i = 0; i < mapList.size(); i++) {
				todo = ((IUpdateLotteryLowfreqInfoDao) dao).updateNowAmount(Integer.parseInt(mapList.get(i).get("localId").toString()),
						Integer.parseInt(mapList.get(i).get("typeId").toString()), Integer.parseInt(mapList.get(i).get("minId").toString()),
						new BigDecimal(mapList.get(i).get("nowBaseline").toString()), Integer.parseInt(mapList.get(i).get("prizeLevel").toString()),
						Integer.parseInt(mapList.get(i).get("handicap").toString()));
			}
			if (todo) {
				if (Integer.parseInt(mapList.get(0).get("typeId").toString()) == LOTTERY_LOWFREQ_FC3D_TYPE) {
					todo = dao.setLotteryLogToDBNoCommit(USERID, LOG_ACTION_LOTTERY_LOWFREQ_FC3D_NOW_BASELINE_SET, logList, USERIP);
				} else if (Integer.parseInt(mapList.get(0).get("typeId").toString()) == LOTTERY_LOWFREQ_PL5_TYPE) {
					todo = dao.setLotteryLogToDBNoCommit(USERID, LOG_ACTION_LOTTERY_LOWFREQ_PL5_NOW_BASELINE_SET, logList, USERIP);
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (Exception e) {
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
		}
		return todo;
	}

	@Override
	public Boolean updateMark6BaseAmount(List<Map<String, Object>> mapList, String amountLogList) {
		boolean todo = false;
		try {
			for (int i = 0; i < mapList.size(); i++) {
				todo = ((IUpdateLotteryLowfreqInfoDao) dao).updateMark6BaseAmount(Integer.parseInt(mapList.get(i).get("localId").toString()),
						Integer.parseInt(mapList.get(i).get("typeId").toString()), Integer.parseInt(mapList.get(i).get("minId").toString()),
						new BigDecimal(mapList.get(i).get("baseBet").toString()), new BigDecimal(mapList.get(i).get("baseline").toString()),
						Integer.parseInt(mapList.get(i).get("prizeLevel").toString()), Integer.parseInt(mapList.get(i).get("handicap").toString()));
			}
			if (todo) {
				todo = dao.setLotteryLogToDBNoCommit(USERID, LOG_ACTION_LOTTERY_LOWFREQ_LHC_BASE_BASELINE_SET, amountLogList, USERIP);
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (Exception e) {
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
		}
		return todo;
	}

	@Override
	public Boolean updateMark6NowAmount(List<Map<String, Object>> mapList, String logList) {
		boolean todo = false;
		try {
			for (int i = 0; i < mapList.size(); i++) {
				todo = ((IUpdateLotteryLowfreqInfoDao) dao).updateMark6NowAmount(Integer.parseInt(mapList.get(i).get("localId").toString()),
						Integer.parseInt(mapList.get(i).get("typeId").toString()), Integer.parseInt(mapList.get(i).get("minId").toString()),
						new BigDecimal(mapList.get(i).get("nowBaseline").toString()), Integer.parseInt(mapList.get(i).get("prizeLevel").toString()),
						Integer.parseInt(mapList.get(i).get("handicap").toString()));
			}
			if (todo) {
				todo = dao.setLotteryLogToDBNoCommit(USERID, LOG_ACTION_LOTTERY_LOWFREQ_LHC_NOW_BASELINE_SET, logList, USERIP);
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug(e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			try {
				if (todo) {
					todo = dao.commitDB();
				} else {
					todo = dao.rollBackDB();
				}
			} catch (Exception e) {
				LOG.debug(e.getMessage());
				ShowLog.err(LOG, e);
			}
		}
		return todo;
	}
}
