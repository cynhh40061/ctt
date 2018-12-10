package tw.com.ctt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.ILotteryRecordDao;
import tw.com.ctt.service.ILotteryRecordService;
import tw.com.ctt.util.ShowLog;

public class LotteryRecordServiceImpl extends BaseService implements ILotteryRecordService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5696970854208188703L;
	private static final Logger LOG = LogManager.getLogger(LotteryRecordServiceImpl.class.getName());

	@Override
	public List<Map<String, Object>> getLotteryLocalList() {
		return ((ILotteryRecordDao) dao).getLotteryLocalList();
	}

	@Override
	public int getLotteryRecordTotleCount(long accId, Map<String, Object> map) {
		int totalPage = 0;
		String startTime = null;
		String endTime = null;
		try {
			if ("".equals(map.get("startTime").toString())) {
				startTime = getNowDate() + " 00:00:00";
			} else {
				startTime = map.get("startTime").toString();
			}
			if ("".equals(map.get("endTime").toString())) {
				endTime = getNowDate() + " 23:59:59.999";
			} else {
				endTime = map.get("endTime").toString();
			}
			totalPage = ((ILotteryRecordDao) dao).getLotteryRecordTotleCount(accId, map.get("memberAccName").toString(),
					Integer.parseInt(map.get("recordType").toString()), Boolean.parseBoolean(map.get("checkBox").toString()),
					Integer.parseInt(map.get("localId").toString()), startTime, endTime);
		} catch (Exception e) {
			totalPage = 0;
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return totalPage;
	}

	@Override
	public List<Map<String, Object>> getLotteryRecord(long accId, Map<String, Object> map, int firstCount, int count) {
		List<Map<String, Object>> RecordMapList = null;
		String startTime = null;
		String endTime = null;
		try {
			RecordMapList = new ArrayList<Map<String, Object>>();
			if ("".equals(map.get("startTime").toString())) {
				startTime = getNowDate() + " 00:00:00";
			} else {
				startTime = map.get("startTime").toString();
			}
			if ("".equals(map.get("endTime").toString())) {
				endTime = getNowDate() + " 23:59:59.999";
			} else {
				endTime = map.get("endTime").toString();
			}
			if (Boolean.parseBoolean(map.get("checkBox").toString())) {

			} else {
				RecordMapList = ((ILotteryRecordDao) dao).getLotteryRecord(accId, map.get("memberAccName").toString(),
						Integer.parseInt(map.get("recordType").toString()), Integer.parseInt(map.get("localId").toString()), startTime, endTime,
						firstCount, count);
			}
		} catch (Exception e) {
			RecordMapList = null;
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return RecordMapList;
	}

	private String getNowDate() {
		SimpleDateFormat getdate = null;
		String nowDate = null;
		try {
			getdate = new SimpleDateFormat("yyyy/MM/dd");
			nowDate = getdate.format(new Date());
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return nowDate;
	}
}
