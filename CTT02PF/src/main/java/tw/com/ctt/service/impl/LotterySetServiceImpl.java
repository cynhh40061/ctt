package tw.com.ctt.service.impl;

import static tw.com.ctt.constant.LotteryLogToDBConstant.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import tw.com.ctt.dao.ILotterySetDao;
import tw.com.ctt.service.ILotterySetService;
import tw.com.ctt.util.ShowLog;

public class LotterySetServiceImpl extends BaseService implements ILotterySetService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4023228447140859781L;
	private static final Logger LOG = LogManager.getLogger(LotterySetServiceImpl.class.getName());

	public LotterySetServiceImpl() {

	}

	public LotterySetServiceImpl(long userId, String userIp) {
		super(userId, userIp);
	}

	@Override
	public List<Map<String, Object>> getLotteryTypeList() {
		return ((ILotterySetDao) dao).getLotteryTypeList();
	}

	@Override
	public List<Map<String, Object>> getLotteryMidList() {
		return ((ILotterySetDao) dao).getLotteryMidList();
	}

	@Override
	public List<Map<String, Object>> getUpperSwitchInfo() {
		return ((ILotterySetDao) dao).getUpperSwitchInfo();
	}

	@Override
	public List<Map<String, Object>> getSwitchDetailTitle(int lotteryId) {
		return ((ILotterySetDao) dao).getSwitchDetailTitle(lotteryId);
	}

	@Override
	public List<Map<String, Object>> getSwitchDetail(int lotteryId) {
		return ((ILotterySetDao) dao).getSwitchDetail(lotteryId);
	}

	@Override
	public Boolean updateLotterySwitch(int lotteryId, List<Map<String, Object>> mapList, int totalSwitch) {
		boolean todo = false;
		List<Map<String, Object>> getMidSwitchMapList = null;
		JSONArray responseJSONObject = null;
		Map<String, Object> logDataMap = null;
		Map<String, Object> logDataMapTitle = null;

		try {
			responseJSONObject = new JSONArray();
			logDataMap = new ConcurrentHashMap<String, Object>();
			logDataMapTitle = new ConcurrentHashMap<String, Object>();

			for (int i = 0; i < mapList.size(); i++) {
				todo = ((ILotterySetDao) dao).updateLotterySwitch(lotteryId, Integer.parseInt(mapList.get(i).get("midId").toString()),
						Integer.parseInt(mapList.get(i).get("switch").toString()));
				if (todo) {
					logDataMap.put(mapList.get(i).get("midId").toString(), Integer.parseInt(mapList.get(i).get("switch").toString()));
				}
			}
			if (todo) {
				logDataMapTitle.put("" + lotteryId, logDataMap);
				responseJSONObject.put(logDataMapTitle);
				todo = dao.setLotteryLogToDBNoCommit(USERID, LOTTERY_SWITCH_PLAYED_ARR[lotteryId], responseJSONObject.toString(), USERIP);
			}
			if (todo) {
				getMidSwitchMapList = ((ILotterySetDao) dao).getMidSwitchInfo(lotteryId);
				if (getMidSwitchMapList.size() > 0) {
					for (int j = 0; j < getMidSwitchMapList.size(); j++) {
						todo = ((ILotterySetDao) dao).updateMidSwitch(Integer.parseInt(getMidSwitchMapList.get(j).get("localId").toString()),
								Integer.parseInt(getMidSwitchMapList.get(j).get("midId").toString()),
								Integer.parseInt(getMidSwitchMapList.get(j).get("switch").toString()));
					}
				}
			}
			if (todo) {
				todo = ((ILotterySetDao) dao).updateTotalSwitch(lotteryId, totalSwitch);
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
			if (getMidSwitchMapList != null) {
				getMidSwitchMapList.clear();
				getMidSwitchMapList = null;
			}
		}
		return todo;
	}

	@Override
	public List<Map<String, Object>> getLotteryAmountLocalList() {
		return ((ILotterySetDao) dao).getLotteryAmountLocalList();
	}

	@Override
	public List<Map<String, Object>> getLotteryAmountPlayedList() {
		return ((ILotterySetDao) dao).getLotteryAmountPlayedList();
	}

	@Override
	public List<Map<String, Object>> getHandicapInfo() {
		return ((ILotterySetDao) dao).getHandicapInfo();
	}

	@Override
	public List<Map<String, Object>> getTotalAmountInfo() {
		return ((ILotterySetDao) dao).getTotalAmountInfo();
	}

	@Override
	public Boolean upadteDescription(int minId, String playedText, String lotteryRule, String lotteryExample, String descriptionLog) {
		boolean todo = false;
		try {
			todo = ((ILotterySetDao) dao).upadteDescription(minId, playedText, lotteryRule, lotteryExample);
			if (todo) {
				((ILotterySetDao) dao).upadteZero();
			}
			if (todo) {
				todo = dao.setLotteryLogToDBNoCommit(USERID, LOG_ACTION_LOTTERY_DESCRIPTION_SET, descriptionLog, USERIP);
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
	public Boolean updateAmountInfo(List<Map<String, Object>> mapList, String amountLogList) {
		boolean todo = false;

		try {
			for (int i = 0; i < mapList.size(); i++) {
				todo = ((ILotterySetDao) dao).updateAmountInfo(Integer.parseInt(mapList.get(i).get("localId").toString()),
						Integer.parseInt(mapList.get(i).get("typeId").toString()), Integer.parseInt(mapList.get(i).get("minId").toString()),

						new BigDecimal(mapList.get(i).get("baseBet").toString()), new BigDecimal(mapList.get(i).get("betLevel1").toString()),
						new BigDecimal(mapList.get(i).get("betLevel2").toString()),

						new BigDecimal(mapList.get(i).get("baseline").toString()), new BigDecimal(mapList.get(i).get("baselineLevel1").toString()),
						new BigDecimal(mapList.get(i).get("baselineLevel2").toString()),

						Boolean.parseBoolean(mapList.get(i).get("dtSwitch").toString()), new BigDecimal(mapList.get(i).get("dtRatio").toString()),
						new BigDecimal(mapList.get(i).get("dtBonus").toString()),

						Integer.parseInt(mapList.get(i).get("prizeLevel").toString()));
			}
			if (todo) {
				todo = dao.setLotteryLogToDBNoCommit(USERID, LOTTERY_AMOUNT_LOCAL_ARR[Integer.parseInt(mapList.get(0).get("localId").toString())],
						amountLogList, USERIP);
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
	public Boolean upadteHandicap(int handicapId, BigDecimal bonusSetMin, BigDecimal bonusSetMax, int relativeBaseline, int maxWinBonus,
			String handicapLog) {
		boolean todo = false;
		try {
			todo = ((ILotterySetDao) dao).upadteHandicap(handicapId, bonusSetMin, bonusSetMax, relativeBaseline, maxWinBonus);
			if (todo) {
				todo = dao.setLotteryLogToDBNoCommit(USERID, LOG_ACTION_LOTTERY_HANDICAP_SET, handicapLog, USERIP);
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
	public List<Map<String, Object>> getLotterySwitchSettingLog() {
		return ((ILotterySetDao) dao).getLotterySwitchSettingLog(USERID, LOG_ACTION_LOTTERY_SWITCH);
	}

	@Override
	public List<Map<String, Object>> getLotteryAmountSettingLog() {
		return ((ILotterySetDao) dao).getLotteryAmountSettingLog(USERID, LOG_ACTION_LOTTERY_AMOUNT);
	}
}
