package tw.com.ctt.dao.impl;

import static tw.com.ctt.constant.AccountManagerConstant.*;
import static tw.com.ctt.constant.LotteryLogToDBConstant.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.ISetLotteryTimeDao;
import tw.com.ctt.model.LotteryTimeSetBean;
import tw.com.ctt.util.ShowLog;
import tw.com.ctt.util.StmtUtil;

public class SetLotteryTimeDaoImpl extends BaseDao implements ISetLotteryTimeDao {

	private static final long serialVersionUID = -6743055222073093739L;
	private static final Logger LOG = LogManager.getLogger(CheckPwdDaoImpl.class.getName());

	public SetLotteryTimeDaoImpl() {
		super();
	}

	@Override
	public Map<String, Object> getLotteryType() {
		Map<String, Object> mapObj = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return mapObj;
			} else {
				mapObj = new ConcurrentHashMap<String, Object>();
				sb = new StringBuilder();
				
				sb.append("	select 	\n");
				sb.append("	(case when A.id < 10 then cast(CONCAT('00',A.id,'-',A.lottery_title) as char)  when A.id > 10 and A.id < 100 then cast(CONCAT('0',A.id,'-',A.lottery_title) as char) 	\n");
				sb.append("	else cast(CONCAT('0',A.id,'-',A.lottery_title) as char) end)  as lottery_title 	\n");
				sb.append("	, cast(GROUP_CONCAT(B.title,'|',B.id ) as char) as lotteryData from 		\n");
				sb.append("	( 		\n");
				sb.append("	select t1.auth_id,t2.id,t1.lottery_title, t1.sort_level1_id from 		\n");
				sb.append("	(select auth_id,lottery_title, sort_level1_id from ctt_manager.ctt_lottery_auth where auth_level_type = 1 ) t1		\n");
				sb.append("	inner join 		\n");
				sb.append("	(select id,auth_id from ctt_manager.ctt_lottery_auth_list ) t2		\n");
				sb.append("	on t1.auth_id = t2.auth_id 		\n");
				sb.append("	)A 		\n");
				sb.append("	inner join		\n");
				sb.append("	(select id , title , type , create_period_type from ctt_manager.ctt_lottery_type ) B		\n");
				sb.append("	on A.id = B.id 		\n");
				sb.append("	group by auth_id ASC	 order by B.type ASC ,A.id ASC ,A.sort_level1_id asc 	\n");

				List<Map<String,Object>> lotteryTypeList = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), new ArrayList<>());
				
				List<Map<String ,Object>> listMap = null;
				if(lotteryTypeList != null && lotteryTypeList.size() > 0) {
					for(int i = 0 ; i < lotteryTypeList.size() ; i++) {
						if(lotteryTypeList.get(i).containsKey("lotteryTitle") && lotteryTypeList.get(i).containsKey("lotteryData") 
								&& !"".equals(lotteryTypeList.get(i).get("lotteryTitle").toString()) && !"".equals(lotteryTypeList.get(i).get("lotteryData").toString())) {
							listMap = new ArrayList<Map<String,Object>>();
							String[] lotteryDataArr =lotteryTypeList.get(i).get("lotteryData").toString().split(",");
							
							for(int j = 0 ; j < lotteryDataArr.length ; j++) {
								if(!"".equals(lotteryDataArr[j])) {
									String[] strArr = lotteryDataArr[j].split("\\|");
									if(strArr.length == 2) {
										Map<String ,Object > tmpMap = new ConcurrentHashMap<String, Object>();
										tmpMap.put("title", strArr[0]); 
										tmpMap.put("id", strArr[1]);
										
										listMap.add(tmpMap);
									}
								}
							}
							if(listMap.size() > 0 && listMap != null) {
								mapObj.put(lotteryTypeList.get(i).get("lotteryTitle").toString(), listMap);
							}
							
						}
					}
				}
				
			}
		} catch (Exception e) {
			mapObj = null;
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}

		}
		return mapObj;
	}
	
	
	@Override
	public List<Map<String, Object>> getLotteryTimeList(int id) {
		List<Map<String, Object>> listMap = null;
		List<Object> selectList = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				selectList = new ArrayList<Object>();
				listMap = new ArrayList<Map<String, Object>>();
				sb = new StringBuilder();
				sb.append("	select A.*,B.create_period_type,B.zodiac_type from ctt_manager.ctt_lottery_setting as A	\n");
				sb.append("	inner join 	\n");
				sb.append("	(select id,create_period_type,zodiac_type from ctt_manager.ctt_lottery_type where id = ?) B	\n");
				sb.append("	on A.id = B.id	\n");
				selectList.add(id);

				listMap = StmtUtil.queryToMap(this.READ_CONN, sb.toString(), selectList);
			}
		} catch (Exception e) {
			LOG.debug("Exception, " + e.getMessage());
			ShowLog.err(LOG, e);
		} finally {
			if (sb != null) {
				sb.setLength(0);
				sb = null;
			}
			if(selectList != null) {
				selectList.clear();
				selectList = null;
			}

		}
		return listMap;
	}
	
	@Override
	public List<LotteryTimeSetBean> getLotteryTimeListToBean(int id){
		List<LotteryTimeSetBean> lotteryTimeSetList = null;
		List<Object> selectList = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return lotteryTimeSetList;
			} else {
				selectList = new ArrayList<Object>();
				lotteryTimeSetList = new ArrayList<LotteryTimeSetBean>();
				sb = new StringBuilder();
				
				sb.append("	select A.*,B.create_period_type,B.zodiac_type from ctt_manager.ctt_lottery_setting as A	\n");
				sb.append("	inner join 	\n");
				sb.append("	(select id,create_period_type,zodiac_type from ctt_manager.ctt_lottery_type where id = ?) B	\n");
				sb.append("	on A.id = B.id	\n");
				
				selectList.add(id);

				List<Object> listObj = StmtUtil.queryToBean(this.READ_CONN, sb.toString(), selectList, new LotteryTimeSetBean());
				
				for(int i = 0 ; i < listObj.size() ; i++) {
					lotteryTimeSetList.add((LotteryTimeSetBean) listObj.get(i));
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
			if(selectList != null) {
				selectList.clear();
				selectList = null;
			}

		}
		return lotteryTimeSetList;
	}
	
	@Override
	public int selectLotterySettingCount(int id) {
		List<Object> selectObj = null;
		StringBuilder sb = null;
		int count = 0;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return count;
			} else {
				selectObj = new ArrayList<Object>();
				sb = new StringBuilder();
				
				sb.append("	select count(1) as count from ctt_manager.ctt_lottery_setting where id = ?	\n");
				selectObj.add(id);
				
				List<Map<String ,Object>> listMap = StmtUtil.queryToMap(this.WRITE_CONN, sb.toString(), selectObj);
				
				if(listMap.size() == 1) {
					if(listMap.get(0).containsKey("count")) {
						return Integer.parseInt(listMap.get(0).get("count").toString());
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
			if(selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}

		}
		return count;
	}
	
	@Override
	public boolean deleteLotterySetting(int id) {
		List<Object> deleteObj = null;
		StringBuilder sb = null;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return false;
			} else {
				deleteObj = new ArrayList<Object>();
				sb = new StringBuilder();
				
				sb.append("	delete from ctt_manager.ctt_lottery_setting where id = ?	\n");
				deleteObj.add(id);
				
				int cout = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), deleteObj);
				
				if(cout != 0 ) {
					return true;
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
			if(deleteObj != null) {
				deleteObj.clear();
				deleteObj = null;
			}

		}
		return false;
	}
	
	@Override
	public boolean insertLotteryTime(List<LotteryTimeSetBean> lotteryTimeSetBeanList) {
		List<List<Object>> insertList = null;
		StringBuilder sb = null;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				
				insertList = new ArrayList<List<Object>>();
				sb = new StringBuilder();
				sb.append("	insert into ctt_manager.ctt_lottery_setting 	\n");
				sb.append(" (id,issue,kj_time,platform_kj_time,stop_betting_time,give_up_kj_time,jump_off_time,create_period_week_type,create_week_type,start_betting_week_type,create_period_time,start_betting_time)	\n");
				sb.append("	values (?,?,?,?,?,?,?,?,?,?,?,?)	\n");
				
				for(int i = 0 ; i < lotteryTimeSetBeanList.size() ; i++) {
					List<Object> insertObj = new ArrayList<Object>();
					
					LotteryTimeSetBean b1 = lotteryTimeSetBeanList.get(i);
					
					
					insertObj.add(b1.getId());
					insertObj.add(b1.getIssue());
					insertObj.add(b1.getKjTime());
					insertObj.add(b1.getPlatformKjTime());
					insertObj.add(b1.getStopBettingTime());
					insertObj.add(b1.getGiveUpKjTime());

					insertObj.add(b1.getJumpOffTime());
					insertObj.add(b1.getCreatePeriodWeekType());
					insertObj.add(b1.getCreateWeekType());
					
					insertObj.add(b1.getStartBettingWeekType());//start_betting_week_type
					insertObj.add(b1.getCreatePeriodTime());//create_period_time
					insertObj.add(b1.getStartBettingTime());//start_betting_time
					
					insertList.add(insertObj);
				}
				
				int cou[] = StmtUtil.updateBatchNoCommit(this.WRITE_CONN, sb.toString(), insertList);
				
				for(int i = 0 ; i < cou.length ; i++) {
					if(cou[i] != 0) {
						todo = true;
					}
					else {
						todo = false;
						break;
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
			if(insertList != null) {
				insertList.clear();
				insertList = null;
			}

		}
		return todo;
	}
	
	@Override
	public List<Map<String ,Object>> getLotteryTimeSetLog(long accId){
		List<Map<String ,Object>> listMap = null;
		List<Object> selectObj = null;
		StringBuilder sb = null;
		try {
			this.checkRead();
			if (this.READ_CONN == null || this.READ_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return listMap;
			} else {
				selectObj = new ArrayList<Object>();
				listMap = new ArrayList<Map<String ,Object>>();
				
				sb = new StringBuilder();
				
				sb.append("	 select A.action_id , A.action_text ,B.ops_acc_id , B.ops_acc_name , B.detail , B.ops_datetime , B.ip  from 	\n");
				sb.append("	(select action_id , action_text from ctt_manager.ctt_lottery_log_action  where upper_id = "+LOG_ACTION_LOTTERY_TIME+" ) A	\n");
				sb.append("	inner join	\n");
				sb.append("	(	\n");
				sb.append("		select ops_acc_id , ops_acc_name , action , detail , DATE_FORMAT(ops_datetime,'%Y/%m/%d %T') as ops_datetime , CONCAT(ip1,'.',ip2,'.',ip3,'.',ip4) as ip from ctt_manager.ctt_lottery_set_ops_log 	\n");
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
				sb.append("	) B	\n");
				sb.append("	on A.action_id = B.action order by B.ops_datetime DESC	\n");
				
				listMap = StmtUtil.queryToMap(this.WRITE_CONN, sb.toString(), selectObj);

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
			if(selectObj != null) {
				selectObj.clear();
				selectObj = null;
			}

		}
		return listMap;
	}
	
	@Override
	public boolean updateLotteryType(int id , int createPeriodType , int zodiacType) {
		List<Object> updateObj = null;
		StringBuilder sb = null;
		boolean todo = false;
		try {
			this.checkWrite();
			if (this.WRITE_CONN == null || this.WRITE_CONN.isClosed()) {
				LOG.debug("CONNECTION IS NULL");
				return todo;
			} else {
				
				updateObj = new ArrayList<Object>();
				sb = new StringBuilder();
				sb.append("	update ctt_manager.ctt_lottery_type set create_period_type = ? , zodiac_type = ? where id = ? 	\n");
					
				updateObj.add(createPeriodType);
				updateObj.add(zodiacType);
				updateObj.add(id);
				
				int cou = StmtUtil.updateNoCommit(this.WRITE_CONN, sb.toString(), updateObj);
				
				if(cou != 0) {
					todo = true;
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
			if(updateObj != null) {
				updateObj.clear();
				updateObj = null;
			}

		}
		return todo;
	}
	
	
}
