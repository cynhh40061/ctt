package tw.com.ctt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.ILotteryOperatorRecordDao;
import tw.com.ctt.service.ILotteryOperatorRecordService;
import tw.com.ctt.util.ShowLog;

public class LotteryOperatorRecordServiceImpl extends BaseService implements ILotteryOperatorRecordService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8079548795908282463L;
	private static final Logger LOG = LogManager.getLogger(LotteryOperatorRecordServiceImpl.class.getName());

	@Override
	public List<Map<String, Object>> getLotteryLocalList() {
		return ((ILotteryOperatorRecordDao) dao).getLotteryLocalList();
	}

	@Override
	public List<Map<String, Object>> getTodayOperatorRecord(long accId, int level) {
		List<Map<String, Object>> mapList = null;
		try {
			mapList = new ArrayList<Map<String, Object>>();

			if (level >= 2 && level <= 3) {
				mapList = ((ILotteryOperatorRecordDao) dao).getTodayOperatorRecord(accId);
			} else if (level <= 1 && level >= 0) {
				mapList = ((ILotteryOperatorRecordDao) dao).getTodayTotalOperatorRecord();
			}

		} catch (Exception e) {
			mapList = null;
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return mapList;
	}

	@Override
	public List<Map<String, Object>> searchOperatorRecordByDate(long accId, String accName, int localId, String dateTime, int type) {
		List<Map<String, Object>> mapList = null;
		String tableName = null;
		try {
			mapList = new ArrayList<Map<String, Object>>();

			if (type == 2) {// selectByDay
				tableName = "manager_lottery_statistical_daily_report";
			} else if (type == 3) {// selectByWeek
				tableName = "manager_lottery_statistical_week_report";
				dateTime = getDate(dateTime);
			} else if (type == 4) {// selectByMonth
				tableName = "manager_lottery_statistical_month_report";
			}

			mapList = ((ILotteryOperatorRecordDao) dao).searchOperatorRecordByDate(accId, accName, localId, dateTime, tableName);

		} catch (Exception e) {
			mapList = null;
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return mapList;
	}

	@Override
	public List<Map<String, Object>> searchBCRecord(long accId, int localId, String dateTime, int type) {
		List<Map<String, Object>> mapList = null;
		String tableName = null;
		try {
			mapList = new ArrayList<Map<String, Object>>();

			if (type == 2) {// selectByDay
				tableName = "manager_lottery_statistical_daily_report";
			} else if (type == 3) {// selectByWeek
				tableName = "manager_lottery_statistical_week_report";
				dateTime = getDate(dateTime);
			} else if (type == 4) {// selectByMonth
				tableName = "manager_lottery_statistical_month_report";
			}

			mapList = ((ILotteryOperatorRecordDao) dao).searchBCRecord(accId, localId, dateTime, tableName);

		} catch (Exception e) {
			mapList = null;
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return mapList;
	}

	@Override
	public List<Map<String, Object>> searchMemRecord(long accId, int localId, String dateTime, int type) {
		List<Map<String, Object>> mapList = null;
		String tableName = null;
		try {
			mapList = new ArrayList<Map<String, Object>>();

			if (type == 2) {// selectByDay
				tableName = "member_lottery_statistical_daily_report";
			} else if (type == 3) {// selectByWeek
				tableName = "member_lottery_statistical_week_report";
				dateTime = getDate(dateTime);
			} else if (type == 4) {// selectByMonth
				tableName = "member_lottery_statistical_month_report";
			}

			mapList = ((ILotteryOperatorRecordDao) dao).searchMemRecord(accId, localId, dateTime, tableName);

		} catch (Exception e) {
			mapList = null;
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return mapList;
	}

	private String getDate(String dateTime) {
		String splitDateTime = null;
		String newDateTime = null;

		Calendar opsDateTime = null;
		SimpleDateFormat formatDateTime = null;
		Date tranDateTime = null;

		try {
			opsDateTime = Calendar.getInstance();
			formatDateTime = new SimpleDateFormat("yyyy/MM/dd");

			splitDateTime = dateTime.split("~")[1];

			tranDateTime = formatDateTime.parse(splitDateTime);

			opsDateTime.setTime(tranDateTime);

//			opsDateTime.add(Calendar.DAY_OF_MONTH, 1);

			newDateTime = formatDateTime.format(opsDateTime.getTime());
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			splitDateTime = null;
			opsDateTime = null;
			formatDateTime = null;
			tranDateTime = null;
		}
		return newDateTime;
	}
}
