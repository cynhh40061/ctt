package tw.com.ctt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.ILotteryMemberRecordDao;
import tw.com.ctt.service.ILotteryMemberRecordService;
import tw.com.ctt.util.ShowLog;

public class LotteryMemberRecordServiceImpl extends BaseService implements ILotteryMemberRecordService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8079548795908282463L;
	private static final Logger LOG = LogManager.getLogger(LotteryMemberRecordServiceImpl.class.getName());

	@Override
	public List<Map<String, Object>> getLotteryLocalList() {
		return ((ILotteryMemberRecordDao) dao).getLotteryLocalList();
	}

	@Override
	public List<Map<String, Object>> searchMemberRecordByDate(long accId, int localId, String startTime, String endTime, int type) {
		List<Map<String, Object>> mapList = null;
		String tableName = null;
		try {
			mapList = new ArrayList<Map<String, Object>>();

			if (type == 1) {// selectByDay
				tableName = "manager_lottery_statistical_daily_report";
			} else if (type == 2) {// selectByWeek
				tableName = "manager_lottery_statistical_week_report";
			} else if (type == 3) {// selectByMonth
				tableName = "manager_lottery_statistical_month_report";
			}

			mapList = ((ILotteryMemberRecordDao) dao).searchMemberRecordByDate(accId, localId, startTime, endTime, tableName, type);

		} catch (Exception e) {
			mapList = null;
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return mapList;
	}

	@Override
	public List<Map<String, Object>> searchMemDayRecord(long accId, String accName, int localId, String startTime, String endTime, int type) {
		List<Map<String, Object>> mapList = null;
		String tableName = null;
		try {
			mapList = new ArrayList<Map<String, Object>>();

			if (type == 4) {// selectByDay
				tableName = "member_lottery_statistical_report";
			} else if (type == 5) {// selectByDaily
				tableName = "member_lottery_statistical_daily_report";
			}

			mapList = ((ILotteryMemberRecordDao) dao).searchMemDayRecord(accId, accName, localId, startTime, endTime, tableName);

		} catch (Exception e) {
			mapList = null;
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return mapList;
	}
}
