package tw.com.ctt.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.ISetLotteryNumDao;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

import static tw.com.ctt.constant.AccountManagerConstant.*;
import static tw.com.ctt.constant.LotteryLogToDBConstant.*;

public class SetLotteryNumDaoImpl extends BaseDao implements ISetLotteryNumDao {

	private static final long serialVersionUID = -6743055222073093739L;
	private static final Logger LOG = LogManager.getLogger(CheckPwdDaoImpl.class.getName());

	public SetLotteryNumDaoImpl() {
		super();
	}

	@Override
	public List<Map<String, Object>> getLotteryListData(int id, String searchDate) {
		List<Map<String, Object>> listMap = null;
		List<Object> selectObj = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				selectObj = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append(
						"SELECT A.id , A.title ,A.is_official, B.period_num , B.data , B.actual_data , B.date  , B.kj_time , B.actual_kj_time , B.give_up_kj_time , B.stop_betting_time  , B.complete_time , B.total_bet , B.total_bonus , B.total_acc , B.total_mid_order , B.status  	\n");
				sb.append(" from 				\n");
				sb.append("(select id,title,is_official from ctt_manager.ctt_lottery_type where id = ?) A				\n");
				sb.append("inner join 				\n");
				sb.append(
						"(select id,period_num , data , actual_data , DATE_FORMAT(date,'%Y/%m/%d') as date  ,DATE_FORMAT(kj_time,'%Y/%m/%d %T') as  kj_time , DATE_FORMAT(actual_kj_time,'%Y/%m/%d %T') as actual_kj_time , DATE_FORMAT(give_up_kj_time,'%Y/%m/%d %T') as give_up_kj_time  , DATE_FORMAT(stop_betting_time,'%Y/%m/%d %T') as stop_betting_time  , complete_time , FORMAT(total_bet,2) as total_bet , FORMAT(total_bonus,2) as total_bonus , total_acc , total_mid_order , status 	\n");
				sb.append("from ctt_manager.ctt_lottery 			\n");
				sb.append(
						"where date = (case when DATE_FORMAT(?,'%Y/%m/%d') is not null then DATE_FORMAT(?,'%Y/%m/%d') else DATE_FORMAT(now(),'%Y/%m/%d') end)			\n");
				sb.append(") B				\n");
				sb.append("on A.id = B.id	order by period_num ASC	\n");

				selectObj.add(id);
				selectObj.add(searchDate);
				selectObj.add(searchDate);

				List<Map<String, Object>> result = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);
				LOG.debug(result.toString());
				if (result != null && result.size() > 0) {
					for (int i = 0; i < result.size(); i++) {
						Map<String, Object> mapData = new ConcurrentHashMap<String, Object>();
						Map<String, Object> map = result.get(i);

						if (map.containsKey("isOfficial")) {
							//if (Boolean.parseBoolean(map.get("isOfficial").toString()) == false) {
								int status = 0;
								String data = "";
								if (map.containsKey("title")) {
									mapData.put("title", map.get("title").toString());
								}
								if (map.containsKey("periodNum")) {
									mapData.put("periodNum", map.get("periodNum").toString());
								}
								if (map.containsKey("date")) {
									mapData.put("date", map.get("date").toString());
								}
								if (map.containsKey("data")) {
									data = map.get("data").toString();
									mapData.put("data", map.get("data").toString());
								}
								if (map.containsKey("totalMidOrder")) {
									mapData.put("totalMidOrder", map.get("totalMidOrder").toString());
								}
								if (map.containsKey("totalAcc")) {
									mapData.put("totalAcc", map.get("totalAcc").toString());
								}
								if (map.containsKey("status")) {
									status = Integer.parseInt(map.get("status").toString());
								}
								if (map.containsKey("kjTime")) {
									mapData.put("kjTime", map.get("kjTime").toString());
								}
								if (map.containsKey("actualKjTime")) {
									mapData.put("actualKjTime", map.get("actualKjTime").toString());
								}
								if (map.containsKey("totalBet")) {
									mapData.put("totalBet", map.get("totalBet").toString());
								}
								if (map.containsKey("totalBonus")) {
									mapData.put("totalBonus", map.get("totalBonus").toString());
								}
								if (map.containsKey("actualData")) {
									mapData.put("actualData", map.get("actualData").toString());
								}
								if (map.containsKey("stopBettingTime") && map.containsKey("giveUpKjTime")
										&& !"".equals(map.get("stopBettingTime").toString()) && !"".equals(map.get("giveUpKjTime").toString())) {
									// 0 - 1撤單 2 手動錄號 4 對獎 8 已撤除 16 錄號錯誤 32退款 對獎 64
									int featuresType = 0;
									Date now = new Date();

									Date stopBettingTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(map.get("stopBettingTime").toString());
									Date giveUpKjTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(map.get("giveUpKjTime").toString());
									String completeTime = map.containsKey("completeTime") ? map.get("completeTime").toString() : "";
									if (status == 0 && "".equals(data) && "".equals(completeTime)) {
										featuresType += 1;
									}
									if (Boolean.parseBoolean(map.get("isOfficial").toString()) == true) {
										if ((status == 0 || status == 2) && giveUpKjTime.getTime() < now.getTime() && "".equals(completeTime)) {
											featuresType += 2;
										}
									} else if (Boolean.parseBoolean(map.get("isOfficial").toString()) == false) {
										if ((status == 0 || status == 2) && "".equals(completeTime)) {
											featuresType += 2;
										}
									}
									if ((status == 1 || status == 2) && giveUpKjTime.getTime() < now.getTime() && !"".equals(data)
											&& "".equals(completeTime)) {
										featuresType += 4;
									}
									if ((status == 1 || status == 2 || status == 3 || status == 6) && !"".equals(data) && !"".equals(completeTime)) {
										featuresType += 16;
									}
									if (status == 4) {
										featuresType += 8;
									}
									if (status == 3 && !"".equals(data) && !"".equals(completeTime)) {
										featuresType += 32;
									}
									if (status == 5 && !"".equals(data) && !"".equals(completeTime)) {
										featuresType += 64;
									}

									if (status == 0 && stopBettingTime.getTime() < now.getTime() && "".equals(data) && "".equals(completeTime)) {
										status = 7;
									}
									mapData.put("status", status);

									mapData.put("featuresType", featuresType);

									listMap.add(mapData);

								}

							//}
						}
					}
				}

				LOG.debug(listMap.toString());

			}
		} catch (Exception e) {
			listMap = null;
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
	public boolean setLotteryNum(int id, String periodNum, String data) {
		boolean todo = false;
		List<Object> selectObj = null;
		StringBuilder sb = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				selectObj = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("update ctt_manager.ctt_lottery set data = ? , actual_kj_time = now()  , status = 2  	\n");
				sb.append(
						"where period_num = ? and id = ?  and (case (select is_official from ctt_manager.ctt_lottery_type where id = ? ) when true then (give_up_kj_time < now() and complete_time is null and status = 0 ) or (give_up_kj_time < now() and complete_time is null and status = 2)  else 	\n");
				sb.append("  complete_time is null  and status IN (0,2) end ) \n");
				selectObj.add(data);
				selectObj.add(periodNum);
				selectObj.add(id);
				selectObj.add(id);

				int cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), selectObj);

				if (cou > 0) {
					todo = true;
				}

			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}

		}
		return todo;
	}

	@Override
	public boolean setLotteryFali(int id, String periodNum, String data) {
		boolean todo = false;
		List<Object> selectObj = null;
		StringBuilder sb = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				selectObj = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("update ctt_manager.ctt_lottery set actual_data = ? , status =  3 	\n");
				sb.append("where period_num = ? and id = ? and  data <> '' and complete_time is not null and status IN (1,2,3,5,6) 	\n");

				selectObj.add(data);
				selectObj.add(periodNum);
				selectObj.add(id);

				int cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), selectObj);

				if (cou > 0) {
					todo = true;
				}

			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}

		}
		return todo;
	}

	@Override
	public boolean deletePeriodNum(int id, List<String> periodNumList) {
		boolean todo = false;
		List<List<Object>> updateListObj = null;
		List<Object> updateObj = null;
		StringBuilder sb = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				updateListObj = new ArrayList<List<Object>>();
				sb = new StringBuilder();
				sb.append("delete from ctt_manager.ctt_lottery where id = ? and period_num = ? and data = '' and  complete_time is null \n");

				for (int i = 0; i < periodNumList.size(); i++) {
					updateObj = new ArrayList<Object>();
					updateObj.add(id);
					updateObj.add(periodNumList.get(i));
					updateListObj.add(updateObj);
				}

				int[] cou = StmtUtil.updateBatchNoCommit(this.WRITE_CONN, sb.toString(), updateListObj);

				for (int i = 0; i < cou.length; i++) {
					if (cou[i] != 0) {
						todo = true;
					} else {
						return false;
					}
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}

		}
		return todo;
	}

	@Override
	public boolean withdrawalPeriodNum(int id, String periodNum) {
		boolean todo = false;
		List<Object> updateObj = null;
		StringBuilder sb = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				updateObj = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append(
						"update ctt_manager.ctt_lottery set status = 4 where id = ? and period_num = ? and data = '' and  complete_time is null and status = 0 \n");
				updateObj.add(id);
				updateObj.add(periodNum);

				int cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);

				if (cou != 0) {
					todo = true;
				}

			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}

		}
		return todo;
	}

	@Override
	public boolean cancleAllPeriodOrder(long periodNum, int lotteryId) {
		Map<String, Object> insertMap = new ConcurrentHashMap<String, Object>();
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("no connection");
				return false;
			} else {

				List<Object> selectObj = new ArrayList<Object>();
				String sql = "CALL `ctt_manager`.`cancle_bet_by_period`(? ,?)";
				selectObj.add(periodNum);
				selectObj.add(lotteryId);
				StmtUtil.callStored(this.WRITE_CONN, sql, selectObj);
				selectObj.clear();
				return true;
			}
		} catch (Exception e) {
			LOG.info("cancleAllPeriodOrder_Exception===" + e);
			return false;
		} finally {
			if (insertMap != null) {
				insertMap.clear();
				insertMap = null;
			}
		}
	}

	@Override
	public boolean insertLotteryLog(int id, String periodNum, int operatingStatus) {
		boolean todo = false;
		List<Object> selectObj = null;
		StringBuilder sb = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				selectObj = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("insert into ctt_manager.ctt_lottery_ops_log (id,period_num,data,actual_data,actual_kj_time,operating_status,status)	\n");
				sb.append(
						"(select id,period_num,data,actual_data,actual_kj_time,? as operating_status , status from ctt_manager.ctt_lottery where id = ? and period_num = ? )	\n");
				selectObj.add(operatingStatus);
				selectObj.add(id);
				selectObj.add(periodNum);

				int cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), selectObj);

				if (cou != 0) {
					todo = true;
				}

			}
		} catch (Exception e) {
			todo = false;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}

		}
		return todo;
	}

	@Override
	public boolean callCheckTheLottery(int lotetryId, long periodNum, String tableName) {
		boolean todo = false;
		StringBuilder sb = null;
		List<Object> listStr = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				sb = new StringBuilder();
				listStr = new ArrayList<Object>();
				this.WRITE_CONN.setAutoCommit(false);

				sb.append("call ctt_manager.ssc_kj_proc(?,?,?) \n");

				listStr.add(periodNum);
				listStr.add(lotetryId);
				listStr.add(tableName);

				StmtUtil.callStored(this.WRITE_CONN, sb.toString(), listStr);

				LOG.debug("call ctt_manager.ssc_kj_proc(" + periodNum + "," + lotetryId + "," + tableName + ")");

				todo = true;

			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (listStr != null) {
				listStr.clear();
				listStr = null;
			}
		}
		return todo;
	}

	@Override
	public List<Integer> selectLotteryNum(int id, long periodNum) {
		List<Integer> dataList = null;
		List<Map<String, Object>> listMap = null;
		List<Object> selectObj = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return dataList;
			} else {
				dataList = new ArrayList<Integer>();
				listMap = new ArrayList<Map<String, Object>>();
				selectObj = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("select data from ctt_manager.ctt_lottery where id = ? and period_num = ? \n");
				selectObj.add(id);
				selectObj.add(periodNum);
				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);
				if (listMap != null && listMap.size() == 1) {
					if (listMap.get(0).containsKey("data") && !"".equals(listMap.get(0).get("data").toString())) {
						String dataStrArr[] = listMap.get(0).get("data").toString().split(",");
						if (dataStrArr.length > 0) {
							for (String num : dataStrArr) {
								int numInt = Integer.parseInt(num);
								dataList.add(numInt);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			listMap = null;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}

		}
		return dataList;
	}

	@Override
	public boolean goCheckLottery(int id, long periodNum) {

		StringBuilder sb = null;
		List<Object> updateObj = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb = new StringBuilder();
				updateObj = new ArrayList<Object>();
				sb.append(
						"update ctt_manager.ctt_lottery set is_check_lottery = true , start_check_lottery_time = now()  where id = ? and period_num = ? and (is_check_lottery = false OR complete_time IS NULL) \n");
				updateObj.add(id);
				updateObj.add(periodNum);

				long cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);
				if (cou != 0) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}
		}
		return false;
	}

	@Override
	public boolean addPairAward(String tableName, long periodNum, Map<String, String> answerMap) {
		StringBuilder sb = null;
		List<List<Object>> insertObj = null;
		boolean todo = true;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb = new StringBuilder();
				insertObj = new ArrayList<List<Object>>();

				sb.append("insert into " + tableName + " (period_num , played_id , answer)	\n");
				sb.append("       select ? as p_n,a.played_id as p_i  , ? as an  from	\n");
				sb.append("        (select played_id  from  `ctt_manager`.`ctt_lottery_sub_played` where played_name = ?) a	\n");
				sb.append("  where (	\n");
				sb.append("  select case when c2.c2c=0 then true else false end from (	\n");
				sb.append("      select count(1) as c2c from		\n");
				sb.append("        (select played_id  from  `ctt_manager`.`ctt_lottery_sub_played` where played_name = ?) a2		\n");
				sb.append("        inner join		\n");
				sb.append("        (select played_id from " + tableName + "  where period_num = ?) b2 		\n");
				sb.append("        on a2.played_id = b2.played_id	\n");
				sb.append("      )c2		\n");
				sb.append("  )  	\n");

				for (String key : answerMap.keySet()) {
					List<Object> listObj = new ArrayList<Object>();

					listObj.add(periodNum);
					listObj.add(answerMap.get(key).toString());
					listObj.add(key);
					listObj.add(key);
					listObj.add(periodNum);

					insertObj.add(listObj);
				}

				int[] cou = StmtUtil.updateBatchNoCommit(this.WRITE_CONN, sb.toString(), insertObj);
				for (int i = 0; i < cou.length && todo == false; i++) {
					if (cou[i] != 0) {
						todo = true;
					} else {
						todo = false;
					}
				}
			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (insertObj != null) {
				insertObj.clear();
				insertObj = null;
			}
		}
		return todo;
	}

	@Override
	public List<String> selectDeletePeriodNum(int id, String periodNum) {
		List<Map<String, Object>> listMap = null;
		List<String> periodNumList = null;
		List<Object> selectObj = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return periodNumList;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				periodNumList = new ArrayList<String>();
				selectObj = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append(
						"select period_num from ctt_manager.ctt_lottery where id = ? and period_num >= ? and data = '' and  complete_time is null\n");
				selectObj.add(id);
				selectObj.add(periodNum);
				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);
				for (int i = 0; i < listMap.size(); i++) {
					if (listMap.get(i).containsKey("periodNum")) {
						periodNumList.add(listMap.get(i).get("periodNum").toString());
					}
				}
			}
		} catch (Exception e) {
			periodNumList = null;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}

		}
		return periodNumList;
	}

	@Override
	public List<Map<String, Object>> selectPeriodNumData(int id, List<String> periodNumList) {
		List<Map<String, Object>> listMap = null;
		List<Object> selectObj = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				selectObj = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("	select A.id,A.period_num,A.data,A.actual_data,A.kj_time,A.actual_kj_time,A.status,B.title,A.zodiac_type from 	\n");
				sb.append(
						"	(select id,period_num,data,actual_data,kj_time,actual_kj_time,status,zodiac_type from ctt_manager.ctt_lottery where id = ? and period_num IN ( \n");
				selectObj.add(id);
				for (int i = 0; i < periodNumList.size(); i++) {
					if (i < periodNumList.size() - 1) {
						sb.append(" ?, \n");
					} else {
						sb.append(" ?) \n");
					}
					selectObj.add(periodNumList.get(i));
				}
				sb.append("	) A	\n");
				sb.append("	inner join	\n");
				sb.append("	(select id,title from ctt_manager.ctt_lottery_type) B	\n");
				sb.append("	on A.id = B.id	\n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);

			}
		} catch (Exception e) {
			listMap = null;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}

		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> getLotteryNumSetLog(long accId, int logAction, String startTime, String endTime) {
		List<Map<String, Object>> listMap = null;
		List<Object> selectObj = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				listMap = new ArrayList<Map<String, Object>>();
				selectObj = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("	 select A.action_id , A.action_text ,B.ops_acc_id , B.ops_acc_name , B.detail , B.ops_datetime , B.ip  from 	\n");
				sb.append("	(select action_id , action_text from ctt_manager.ctt_lottery_log_action  where upper_id = " + LOG_ACTION_LOTTERY_NUM
						+ " ) A	\n");
				sb.append("	inner join	\n");
				sb.append("	(	\n");
				sb.append(
						"		select ops_acc_id , ops_acc_name , action , detail ,DATE_FORMAT(ops_datetime,'%Y/%m/%d %T')  as  ops_datetime , CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) as ip from ctt_manager.ctt_lottery_set_ops_log 	\n");
				sb.append("		where ops_acc_id IN (select acc_id from ctt_manager.ctt_manager_acc_level where 	\n");
				sb.append("		case (select acc_level_type from ctt_manager.ctt_manager_acc_level where acc_id = ?)	\n");
				selectObj.add(accId);
				sb.append("		when " + ACC_LEVEL_ADMIN + " then TRUE	\n");
				sb.append("		when " + ACC_LEVEL_COM + " then TRUE	\n");
				for (int i = 0; i < ACC_LEVEL_MAG.length; i++) {
					if (!"".equals(ACC_LEVEL_MAG_TABLE_NAME[i]) && ACC_LEVEL_MAG_TABLE_NAME[i] != null) {
						sb.append("	WHEN " + ACC_LEVEL_MAG[i] + " THEN " + ACC_LEVEL_MAG_TABLE_NAME[i] + " =? \n");
						selectObj.add(accId);
					}
				}
				sb.append("		else FALSE end)	\n");

				sb.append(
						"and ops_datetime >= (case when DATE_FORMAT(?,'%Y/%m/%d %T') is not null then DATE_FORMAT(?,'%Y/%m/%d %T') else DATE_FORMAT(CONCAT(cast(DATE_FORMAT(CURRENT_TIMESTAMP,'%Y/%m/%d') as char),' 00:00:00'),'%Y/%m/%d %T')  end)	\n");
				selectObj.add(startTime);
				selectObj.add(startTime);
				sb.append(
						"and ops_datetime <= (case when DATE_FORMAT(?,'%Y/%m/%d %T') is not null then DATE_FORMAT(?,'%Y/%m/%d %T') else DATE_FORMAT(CONCAT(cast(DATE_FORMAT(CURRENT_TIMESTAMP,'%Y/%m/%d') as char),' 23:59:59'),'%Y/%m/%d %T')  end)	\n");
				selectObj.add(endTime);
				selectObj.add(endTime);
				sb.append("and (case when ? is not null and ? > 0 then action = ? else true end )	\n");
				selectObj.add(logAction);
				selectObj.add(logAction);
				selectObj.add(logAction);
				sb.append("	) B	\n");
				sb.append("	on A.action_id = B.action order by B.ops_datetime DESC	\n");

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);

				LOG.debug(listMap.toString());

			}
		} catch (Exception e) {
			listMap = null;
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
	public boolean callRecoverPeriodNumWinningMoney(int gameId, long periodNum) {
		boolean todo = false;
		StringBuilder sb = null;
		List<Object> listStr = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				sb = new StringBuilder();
				listStr = new ArrayList<Object>();
				this.WRITE_CONN.setAutoCommit(false);

				sb.append("call ctt_manager.recover_mid_order_winning_money(?,?) \n");

				listStr.add(periodNum);
				listStr.add(gameId);

				StmtUtil.callStored(this.WRITE_CONN, sb.toString(), listStr);

				LOG.debug("call ctt_manager.recover_mid_order_winning_money(" + periodNum + "," + gameId + ")");

				todo = true;

			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (listStr != null) {
				listStr.clear();
				listStr = null;
			}
		}
		return todo;
	}

	@Override
	public boolean renewCallCheckTheLottery(int lotetryId, long periodNum, String tableName) {
		boolean todo = false;
		StringBuilder sb = null;
		List<Object> listStr = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return todo;
			} else {
				sb = new StringBuilder();
				listStr = new ArrayList<Object>();
				this.WRITE_CONN.setAutoCommit(false);

				sb.append("call ctt_manager.ssc_kj_proc2(?,?,?) \n");

				listStr.add(periodNum);
				listStr.add(lotetryId);
				listStr.add(tableName);

				StmtUtil.callStored(this.WRITE_CONN, sb.toString(), listStr);

				LOG.debug("call ctt_manager.ssc_kj_proc2(" + periodNum + "," + lotetryId + "," + tableName + ")");

				todo = true;

			}
		} catch (Exception e) {
			todo = false;
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (listStr != null) {
				listStr.clear();
				listStr = null;
			}
		}
		return todo;
	}

	@Override
	public boolean refundWinningMoney(int gameId, long periodNum) {
		StringBuilder sb = null;
		List<Object> updateObj = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb = new StringBuilder();
				updateObj = new ArrayList<Object>();
				sb.append("update ctt_manager.ctt_lottery set status = 5  where id = ? and period_num = ? and  status = 3 \n");
				updateObj.add(gameId);
				updateObj.add(periodNum);

				long cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);
				if (cou != 0) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}
		}
		return false;
	}

	@Override
	public boolean renewGoCheckLottery(int id, long periodNum) {
		StringBuilder sb = null;
		List<Object> updateObj = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				// no connection
				return false;
			} else {
				sb = new StringBuilder();
				updateObj = new ArrayList<Object>();
				sb.append(
						"update ctt_manager.ctt_lottery set is_check_lottery = true , start_check_lottery_time = now() ,data = actual_data , status = 6  where id = ? and period_num = ? and status = 5 and actual_data <> '' \n");
				updateObj.add(id);
				updateObj.add(periodNum);

				long cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);
				if (cou != 0) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.info("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if (updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}
		}
		return false;
	}
	
	@Override
	public Map<String, Object> getNextAndPreviousLotteryListData(int id) {
		HashMap<String, Object> tmpMap = null;
		List<HashMap<String, Object>> listMap = null;
		List<HashMap<String, Object>> listMap2 = null;
		List<Object> selectObj = null;
		StringBuilder sb = null;
		String dateStr1 = "";
		String dateStr2 = "";
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return tmpMap;
			} else {
				tmpMap = new HashMap<String, Object>();
				listMap = new ArrayList<HashMap<String, Object>>();
				listMap2 = new ArrayList<HashMap<String, Object>>();
				selectObj = new ArrayList<Object>();
				sb = new StringBuilder();

				sb.append("	select A.id,A.period_num , A.data , A.actual_data , DATE_FORMAT(A.date,'%Y/%m/%d') as date  ,DATE_FORMAT(A.kj_time,'%Y/%m/%d %T') as  kj_time , DATE_FORMAT(A.actual_kj_time,'%Y/%m/%d %T') as actual_kj_time 	\n");
				sb.append("	, DATE_FORMAT(A.give_up_kj_time,'%Y/%m/%d %T') as give_up_kj_time  , DATE_FORMAT(A.stop_betting_time,'%Y/%m/%d %T') as stop_betting_time  , A.complete_time , FORMAT(A.total_bet,2) as total_bet , FORMAT(A.total_bonus,2) as total_bonus 	\n");
				sb.append("	, A.total_acc , A.total_mid_order , A.status ,C.title,C.is_official		\n");
				sb.append("	from ctt_manager.ctt_lottery  as A	\n");
				sb.append("	inner join 	\n");
				sb.append("	( select id,date from	\n");
				sb.append("	(select id,date from ctt_manager.ctt_lottery  where id =? and date >= DATE_SUB(CURDATE(),INTERVAL 3 DAY) and date <= DATE_SUB(CURDATE(),INTERVAL -3 DAY) and date < CURDATE() group by date desc limit 1 ) a1	\n");
				sb.append("	union all	\n");
				sb.append("	select id,date from	\n");
				sb.append("	(select id,date from ctt_manager.ctt_lottery  where id =? and date >= DATE_SUB(CURDATE(),INTERVAL 3 DAY) and date <= DATE_SUB(CURDATE(),INTERVAL -3 DAY) and date >= CURDATE() group by date asc limit 1 ) b1 )B	\n");
				sb.append("	on A.date = B.date and A.id = B.id	\n");
				sb.append("	left join 	\n");
				sb.append("	(select id,title,is_official from ctt_manager.ctt_lottery_type) C	\n");
				sb.append("	on B.id = C.id 	\n");

				selectObj.add(id);
				selectObj.add(id);

				long nowL = new Date().getTime();
				LOG.debug(nowL);
				List<Map<String, Object>> result = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectObj);
				
				LOG.debug(new Date().getTime() - nowL);
				//LOG.debug(result.size());
				if (result != null && result.size() > 0) {
					HashMap<String, Object> mapData = new HashMap<String, Object>();
					for (int i = 0; i < result.size(); i++) {
						 mapData.clear();
						Map<String, Object> map = result.get(i);
						if (map.containsKey("isOfficial")) {
							int status = 0;
							String data = "";
							if (map.containsKey("title")) {
								mapData.put("title", map.get("title").toString());
							}
							if (map.containsKey("periodNum")) {
								mapData.put("periodNum", map.get("periodNum").toString());
							}
							if (map.containsKey("date")) {
								mapData.put("date", map.get("date").toString());
							}
							if (map.containsKey("data")) {
								data = map.get("data").toString();
								mapData.put("data", map.get("data").toString());
							}
							if (map.containsKey("totalMidOrder")) {
								mapData.put("totalMidOrder", map.get("totalMidOrder").toString());
							}
							if (map.containsKey("totalAcc")) {
								mapData.put("totalAcc", map.get("totalAcc").toString());
							}
							if (map.containsKey("status")) {
								status = Integer.parseInt(map.get("status").toString());
							}
							if (map.containsKey("kjTime")) {
								mapData.put("kjTime", map.get("kjTime").toString());
							}
							if (map.containsKey("actualKjTime")) {
								mapData.put("actualKjTime", map.get("actualKjTime").toString());
							}
							if (map.containsKey("totalBet")) {
								mapData.put("totalBet", map.get("totalBet").toString());
							}
							if (map.containsKey("totalBonus")) {
								mapData.put("totalBonus", map.get("totalBonus").toString());
							}
							if (map.containsKey("actualData")) {
								mapData.put("actualData", map.get("actualData").toString());
							}
							if (map.containsKey("stopBettingTime") && map.containsKey("giveUpKjTime")
									&& !"".equals(map.get("stopBettingTime").toString()) && !"".equals(map.get("giveUpKjTime").toString())) {
								// 0 - 1撤單 2 手動錄號 4 對獎 8 已撤除 16 錄號錯誤 32退款 對獎 64
								int featuresType = 0;
								Date now = new Date();

								Date stopBettingTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(map.get("stopBettingTime").toString());
								Date giveUpKjTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(map.get("giveUpKjTime").toString());
								String completeTime = map.containsKey("completeTime") ? map.get("completeTime").toString() : "";
								if (status == 0 && "".equals(data) && "".equals(completeTime)) {
									featuresType += 1;
								}
								if (Boolean.parseBoolean(map.get("isOfficial").toString()) == true) {
									if ((status == 0 || status == 2) && giveUpKjTime.getTime() < now.getTime() && "".equals(completeTime)) {
										featuresType += 2;
									}
								} else if (Boolean.parseBoolean(map.get("isOfficial").toString()) == false) {
									if ((status == 0 || status == 2) && "".equals(completeTime)) {
										featuresType += 2;
									}
								}
								if ((status == 1 || status == 2) && giveUpKjTime.getTime() < now.getTime() && !"".equals(data)
										&& "".equals(completeTime)) {
									featuresType += 4;
								}
								if ((status == 1 || status == 2 || status == 3 || status == 6) && !"".equals(data) && !"".equals(completeTime)) {
									featuresType += 16;
								}
								if (status == 4) {
									featuresType += 8;
								}
								if (status == 3 && !"".equals(data) && !"".equals(completeTime)) {
									featuresType += 32;
								}
								if (status == 5 && !"".equals(data) && !"".equals(completeTime)) {
									featuresType += 64;
								}

								if (status == 0 && stopBettingTime.getTime() < now.getTime() && "".equals(data) && "".equals(completeTime)) {
									status = 7;
								}
								mapData.put("status", status);

								mapData.put("featuresType", featuresType);
								
								
								if(mapData.containsKey("date")) {
									if(!"".equals(dateStr1) ? dateStr1.equals(mapData.get("date").toString()) : true) {
										dateStr1 = mapData.get("date").toString();
										listMap.add((HashMap<String ,Object>)mapData.clone());
									}
									else {
										dateStr2 = mapData.get("date").toString();
										listMap2.add((HashMap<String ,Object>)mapData.clone());
									}
								}
							}
						}
					}
				
					if(listMap.size() > 0) {
						tmpMap.put(dateStr1, listMap);
					}
					if(listMap2.size() > 0) {
						tmpMap.put(dateStr2, listMap2);
					}
				
					
				}


			}
		} catch (Exception e) {
			tmpMap = null;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}

		}
		return tmpMap;
	}


}
