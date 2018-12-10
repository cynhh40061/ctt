package tw.com.ctt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.ILotteryBetOrderQueryDao;
import tw.com.ctt.service.ILotteryBetOrderQueryService;
import tw.com.ctt.util.ShowLog;

public class LotteryBetOrderQueryServiceImpl extends BaseService implements ILotteryBetOrderQueryService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2884217079875882502L;
	private static final Logger LOG = LogManager.getLogger(LotteryBetOrderQueryServiceImpl.class.getName());

	@Override
	public List<Map<String, Object>> getLotteryLocalList() {
		return ((ILotteryBetOrderQueryDao) dao).getLotteryLocalList();
	}

	@Override
	public String getNowDBTime() {
		return ((ILotteryBetOrderQueryDao) dao).getNowDBTime();
	}

	@Override
	public int getMidTotleCount(long accId, Map<String, Object> map) {
		int totleCount = 0;
		String tableDate = null;
		List<String> tableDateList = null;
		try {
			if (!"".equals(map.get("midOrderId").toString().trim())
					|| !"".equals(map.get("midPeriodNum").toString().trim()) && Integer.parseInt(map.get("midlotteryLocal").toString()) > 0) {
				if (!"".equals(map.get("midOrderId").toString().trim())) {
					tableDate = map.get("midOrderId").toString().trim().substring(0, 8);
				} else if (!"".equals(map.get("midPeriodNum").toString().trim()) && Integer.parseInt(map.get("midlotteryLocal").toString()) > 0) {
					tableDate = map.get("midPeriodNum").toString().trim().substring(0, 8);
				} else if (!"".equals(map.get("midOrderId").toString().trim()) && !"".equals(map.get("midPeriodNum").toString().trim())
						&& Integer.parseInt(map.get("midlotteryLocal").toString()) > 0) {
					tableDate = map.get("midOrderId").toString().trim().substring(0, 8);
				}
				if (tableDate != null && !"".equals(tableDate.trim())) {
					totleCount = ((ILotteryBetOrderQueryDao) dao).getMidTotleCountBySingleTable(accId, tableDate, map.get("midOrderId").toString(),
							map.get("midAccName").toString(), Integer.parseInt(map.get("midlotteryLocal").toString()),
							map.get("midPeriodNum").toString(), map.get("midStartTime").toString(), map.get("midEndTime").toString());
				}
			} else {
				if (!"".equals(map.get("midStartTime").toString().trim()) && !"".equals(map.get("midEndTime").toString().trim())
						&& getTableDate(map.get("midStartTime").toString(), map.get("midEndTime").toString()).size() == 1) {
					tableDate = map.get("midStartTime").toString().toString().replaceAll("/", "").replaceAll(":", "").trim().substring(0, 8);
					if (tableDate != null && !"".equals(tableDate.trim())) {
						totleCount = ((ILotteryBetOrderQueryDao) dao).getMidTotleCountBySingleTable(accId, tableDate, null,
								map.get("midAccName").toString(), Integer.parseInt(map.get("midlotteryLocal").toString()), null,
								map.get("midStartTime").toString(), map.get("midEndTime").toString());
					}
				} else {
					if (!"".equals(map.get("midStartTime").toString().trim()) || !"".equals(map.get("midEndTime").toString().trim())) {
						tableDateList = checkTableDate(getTableDate(map.get("midStartTime").toString(), map.get("midEndTime").toString()));
						if (tableDateList.size() > 0) {
							totleCount = ((ILotteryBetOrderQueryDao) dao).getMidTotleCountByUnionTable(accId, tableDateList,
									map.get("midAccName").toString().trim(), Integer.parseInt(map.get("midlotteryLocal").toString()),
									map.get("midStartTime").toString(), map.get("midEndTime").toString());
						}
					} else {
						tableDate = getNowDate();
						if (tableDate != null && !"".equals(tableDate.trim())) {
							totleCount = ((ILotteryBetOrderQueryDao) dao).getMidTotleCountBySingleTable(accId, tableDate, null,
									map.get("midAccName").toString().trim(), Integer.parseInt(map.get("midlotteryLocal").toString()), null, null,
									null);
						}
					}
				}
			}
		} catch (Exception e) {
			totleCount = 0;
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return totleCount;
	}

	@Override
	public int getMainTotleCount(long accId, Map<String, Object> map) {
		int totleCount = 0;
		String tableDate = null;
		List<String> tableDateList = null;
		try {
			if (!"".equals(map.get("mainOrderId").toString().trim())) {
				tableDate = map.get("mainOrderId").toString().trim().substring(0, 8);
				if (tableDate != null && !"".equals(tableDate.trim())) {
					totleCount = ((ILotteryBetOrderQueryDao) dao).getMainTotleCountBySingleTable(accId, tableDate, map.get("mainOrderId").toString(),
							map.get("mainAccName").toString(), Integer.parseInt(map.get("mainlotteryLocal").toString()),
							map.get("mainStartTime").toString(), map.get("mainEndTime").toString(),
							Integer.parseInt(map.get("lotteryStatus").toString()));
				}
			} else {
				if (!"".equals(map.get("mainStartTime").toString().trim()) && !"".equals(map.get("mainEndTime").toString().trim())
						&& getTableDate(map.get("mainStartTime").toString(), map.get("mainEndTime").toString()).size() == 1) {
					tableDate = map.get("mainStartTime").toString().toString().replaceAll("/", "").replaceAll(":", "").trim().substring(0, 8);
					if (tableDate != null && !"".equals(tableDate.trim())) {
						totleCount = ((ILotteryBetOrderQueryDao) dao).getMainTotleCountBySingleTable(accId, tableDate, null,
								map.get("mainAccName").toString(), Integer.parseInt(map.get("mainlotteryLocal").toString()),
								map.get("mainStartTime").toString(), map.get("mainEndTime").toString(),
								Integer.parseInt(map.get("lotteryStatus").toString()));
					}
				} else {
					if (!"".equals(map.get("mainStartTime").toString().trim()) || !"".equals(map.get("mainEndTime").toString().trim())) {
						tableDateList = checkTableDate(getTableDate(map.get("mainStartTime").toString(), map.get("mainEndTime").toString()));
						if (tableDateList.size() > 0) {
							totleCount = ((ILotteryBetOrderQueryDao) dao).getMainTotleCountByUnionTable(accId, tableDateList, null,
									map.get("mainAccName").toString(), Integer.parseInt(map.get("mainlotteryLocal").toString()),
									map.get("mainStartTime").toString(), map.get("mainEndTime").toString(),
									Integer.parseInt(map.get("lotteryStatus").toString()));
						}
					} else {
						tableDate = getNowDate();
						if (tableDate != null && !"".equals(tableDate.trim())) {
							totleCount = ((ILotteryBetOrderQueryDao) dao).getMainTotleCountBySingleTable(accId, tableDate, null,
									map.get("mainAccName").toString().trim(), Integer.parseInt(map.get("mainlotteryLocal").toString()), null, null,
									Integer.parseInt(map.get("lotteryStatus").toString()));
						}
					}
				}
			}
		} catch (Exception e) {
			totleCount = 0;
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return totleCount;
	}

	@Override
	public Map<String, Object> searchMidBetOrder(long accId, Map<String, Object> map, int firstCount, int count) {
		Map<String, Object> returnMap = new ConcurrentHashMap<String, Object>();
		String tableDate = null;
		List<String> tableDateList = null;
		try {
			if (!"".equals(map.get("midOrderId").toString().trim())
					|| !"".equals(map.get("midPeriodNum").toString().trim()) && Integer.parseInt(map.get("midlotteryLocal").toString()) > 0) {
				if (!"".equals(map.get("midOrderId").toString().trim())) {
					tableDate = map.get("midOrderId").toString().trim().substring(0, 8);
				} else if (!"".equals(map.get("midPeriodNum").toString().trim()) && Integer.parseInt(map.get("midlotteryLocal").toString()) > 0) {
					tableDate = map.get("midPeriodNum").toString().trim().substring(0, 8);
				} else if (!"".equals(map.get("midOrderId").toString().trim()) && !"".equals(map.get("midPeriodNum").toString().trim())
						&& Integer.parseInt(map.get("midlotteryLocal").toString()) > 0) {
					tableDate = map.get("midOrderId").toString().trim().substring(0, 8);
				}
				if (tableDate != null && !"".equals(tableDate.trim())) {
					returnMap = ((ILotteryBetOrderQueryDao) dao).searchMidBySingleTable(accId, tableDate, map.get("midOrderId").toString(),
							map.get("midAccName").toString(), Integer.parseInt(map.get("midlotteryLocal").toString()),
							map.get("midPeriodNum").toString(), map.get("midStartTime").toString(), map.get("midEndTime").toString(), firstCount,
							count);
				}
			} else {
				if (!"".equals(map.get("midStartTime").toString().trim()) && !"".equals(map.get("midEndTime").toString().trim())
						&& getTableDate(map.get("midStartTime").toString(), map.get("midEndTime").toString()).size() == 1) {
					tableDate = map.get("midStartTime").toString().toString().replaceAll("/", "").replaceAll(":", "").trim().substring(0, 8);
					if (tableDate != null && !"".equals(tableDate.trim())) {
						returnMap = ((ILotteryBetOrderQueryDao) dao).searchMidBySingleTable(accId, tableDate, null, map.get("midAccName").toString(),
								Integer.parseInt(map.get("midlotteryLocal").toString()), null, map.get("midStartTime").toString(),
								map.get("midEndTime").toString(), firstCount, count);
					}
				} else {
					if (!"".equals(map.get("midStartTime").toString().trim()) || !"".equals(map.get("midEndTime").toString().trim())) {
						tableDateList = checkTableDate(getTableDate(map.get("midStartTime").toString(), map.get("midEndTime").toString()));
						if (tableDateList.size() > 0) {
							returnMap = ((ILotteryBetOrderQueryDao) dao).searchMidByUnionTable(accId, tableDateList,
									map.get("midAccName").toString().trim(), Integer.parseInt(map.get("midlotteryLocal").toString()),
									map.get("midStartTime").toString(), map.get("midEndTime").toString(), firstCount, count);
						}

					} else {
						tableDate = getNowDate();
						if (tableDate != null && !"".equals(tableDate.trim())) {
							returnMap = ((ILotteryBetOrderQueryDao) dao).searchMidBySingleTable(accId, tableDate, null,
									map.get("midAccName").toString().trim(), Integer.parseInt(map.get("midlotteryLocal").toString()), null, null,
									null, firstCount, count);
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> searchMainBetOrder(long accId, Map<String, Object> map, int firstCount, int count) {
		Map<String, Object> returnMap = new ConcurrentHashMap<String, Object>();
		String tableDate = null;
		List<String> tableDateList = null;
		try {
			if (!"".equals(map.get("mainOrderId").toString().trim())) {
				tableDate = map.get("mainOrderId").toString().trim().substring(0, 8);
				if (tableDate != null && !"".equals(tableDate.trim())) {
					returnMap = ((ILotteryBetOrderQueryDao) dao).searchMainBySingleTable(accId, tableDate, map.get("mainOrderId").toString(),
							map.get("mainAccName").toString(), Integer.parseInt(map.get("mainlotteryLocal").toString()),
							map.get("mainStartTime").toString(), map.get("mainEndTime").toString(),
							Integer.parseInt(map.get("lotteryStatus").toString()), firstCount, count);
				}
			} else {
				if (!"".equals(map.get("mainStartTime").toString().trim()) && !"".equals(map.get("mainEndTime").toString().trim())
						&& getTableDate(map.get("mainStartTime").toString(), map.get("mainEndTime").toString()).size() == 1) {
					tableDate = map.get("mainStartTime").toString().toString().replaceAll("/", "").replaceAll(":", "").trim().substring(0, 8);
					if (tableDate != null && !"".equals(tableDate.trim())) {
						returnMap = ((ILotteryBetOrderQueryDao) dao).searchMainBySingleTable(accId, tableDate, null,
								map.get("mainAccName").toString(), Integer.parseInt(map.get("mainlotteryLocal").toString()),
								map.get("mainStartTime").toString(), map.get("mainEndTime").toString(),
								Integer.parseInt(map.get("lotteryStatus").toString()), firstCount, count);
					}
				} else {
					if (!"".equals(map.get("mainStartTime").toString().trim()) || !"".equals(map.get("mainEndTime").toString().trim())) {
						tableDateList = checkTableDate(getTableDate(map.get("mainStartTime").toString(), map.get("mainEndTime").toString()));
						if (tableDateList.size() > 0) {
							returnMap = ((ILotteryBetOrderQueryDao) dao).searchMainByUnionTable(accId, tableDateList, null,
									map.get("mainAccName").toString(), Integer.parseInt(map.get("mainlotteryLocal").toString()),
									map.get("mainStartTime").toString(), map.get("mainEndTime").toString(),
									Integer.parseInt(map.get("lotteryStatus").toString()), firstCount, count);
						}
					} else {
						tableDate = getNowDate();
						if (tableDate != null && !"".equals(tableDate.trim())) {
							returnMap = ((ILotteryBetOrderQueryDao) dao).searchMainBySingleTable(accId, tableDate, null,
									map.get("mainAccName").toString().trim(), Integer.parseInt(map.get("mainlotteryLocal").toString()), null, null,
									Integer.parseInt(map.get("lotteryStatus").toString()), firstCount, count);
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return returnMap;
	}

	private String getNowDate() {
		SimpleDateFormat getdate = null;
		String nowDate = null;
		try {
			getdate = new SimpleDateFormat("yyyyMMdd");
			nowDate = getdate.format(new Date());
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return nowDate;
	}

	private List<String> getTableDate(String startTime, String endTime) {
		SimpleDateFormat dateFormat = null;
		SimpleDateFormat getdate = null;
		Calendar dateTime = null;
		Date startDate = null;
		Date endDate = null;
		Date nowDate = null;
		Date minDate = null;
		List<String> tableDate = null;

		try {
			dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			getdate = new SimpleDateFormat("yyyyMMdd");

			dateTime = Calendar.getInstance();

			startDate = dateFormat.parse(startTime);
			endDate = dateFormat.parse(endTime);

			nowDate = dateFormat.parse(dateFormat.format(new Date()));
			dateTime.setTime(nowDate);
			dateTime.add(Calendar.DAY_OF_MONTH, -30);
			minDate = dateTime.getTime();

			tableDate = new ArrayList<String>();

			dateTime.setTime(startDate);

			for (dateTime.getTime(); endDate.after(dateTime.getTime()); dateTime.add(Calendar.DAY_OF_MONTH, 1)) {
				if (nowDate.after(dateTime.getTime()) && minDate.before(dateTime.getTime())) {
					tableDate.add(getdate.format(dateTime.getTime()).toString());
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return tableDate;
	}

	private List<String> checkTableDate(List<String> dateList) {
		List<String> newDateList = null;
		List<String> mainList = null;
		List<String> midList = null;

		try {
			newDateList = new ArrayList<String>();
			mainList = ((ILotteryBetOrderQueryDao) dao).getTableDate("ctt_manager", "main");
			midList = ((ILotteryBetOrderQueryDao) dao).getTableDate("ctt_manager", "mid");

			for (int i = 0; i < mainList.size(); i++) {
				if (mainList.get(i).toString().equals(midList.get(i).toString())) {
					for (int j = 0; j < dateList.size(); j++) {
						if (mainList.get(i).toString().equals(dateList.get(j).toString())) {
							newDateList.add(dateList.get(j));
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
		}
		return newDateList;
	}
}
