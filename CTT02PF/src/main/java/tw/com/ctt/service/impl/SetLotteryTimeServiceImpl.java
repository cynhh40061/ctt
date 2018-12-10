package tw.com.ctt.service.impl;

import static tw.com.ctt.constant.LotteryLogToDBConstant.*;
import static tw.com.ctt.constant.LotteryConstant.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import tw.com.ctt.dao.ISetLotteryTimeDao;
import tw.com.ctt.model.LotteryTimeSetBean;
import tw.com.ctt.service.ISetLotteryTimeService;
import tw.com.ctt.util.CommandUtil;

public class SetLotteryTimeServiceImpl extends BaseService implements ISetLotteryTimeService {

	private static final long serialVersionUID = 5855274351289897836L;
	private static final Logger LOG = LogManager.getLogger(SetLotteryTimeServiceImpl.class.getName());

	public SetLotteryTimeServiceImpl() {

	}

	public SetLotteryTimeServiceImpl(long userId, String userIp) {
		super(userId, userIp);
	}

	@Override
	public Map<String, Object> getLotteryType() {

		return ((ISetLotteryTimeDao) dao).getLotteryType();
	}
	@Override
	public List<Map<String,Object>> getLotteryTimeList(int id){
		return ((ISetLotteryTimeDao) dao).getLotteryTimeList(id);
	}
	
	@Override
	public boolean saveLotteryTime(List<LotteryTimeSetBean> lotteryTimeSetBeanList ) {
		boolean todo = false;
		int id = 0;
		int createPeriodType = 0;
		JSONObject jsonLogObj = null;
		Map<String ,Object> mapLogData = null; 
		try {
			mapLogData = new ConcurrentHashMap<String ,Object>();
			if(lotteryTimeSetBeanList != null && lotteryTimeSetBeanList.size() > 0) {
				id = lotteryTimeSetBeanList.get(0).getId();
				createPeriodType = lotteryTimeSetBeanList.get(0).getCreatePeriodType();
				List<Integer> issueList = new ArrayList<Integer>();
				List<LotteryTimeSetBean> lotteryTimeSetBeanList2 = ((ISetLotteryTimeDao) dao).getLotteryTimeListToBean(id);
				int zodiacType = 0;
				if(id > 0) {					
					if(lotteryTimeSetBeanList2.size() >= lotteryTimeSetBeanList.size()) {
						zodiacType = lotteryTimeSetBeanList.get(0).getZodiacType();
						for(int i = 0 ; i < lotteryTimeSetBeanList2.size() ; i++) {
							boolean issueDoesIsExist = false;
							for(int j = 0 ; j < lotteryTimeSetBeanList.size() ; j++) {
								if(lotteryTimeSetBeanList2.get(i).getIssue() == lotteryTimeSetBeanList.get(j).getIssue()) {
									issueDoesIsExist = true;
									if(CommandUtil.compareBean(lotteryTimeSetBeanList.get(i),lotteryTimeSetBeanList2.get(j)) == false) {
										mapLogData.put("0_"+lotteryTimeSetBeanList2.get(i).getIssue(), CommandUtil.compareBeanDiff(lotteryTimeSetBeanList.get(i),lotteryTimeSetBeanList2.get(j)));
									}
									issueList.add(lotteryTimeSetBeanList2.get(i).getIssue());
								}
							}
							if(issueDoesIsExist == false) {
								mapLogData.put("1_"+lotteryTimeSetBeanList2.get(i).getIssue(),lotteryTimeSetBeanList2.get(i));
							}
						}

						for(int k = 0 ; k < lotteryTimeSetBeanList.size() ; k++) {
							boolean issueDoesIsExist = false;
							for(int s = 0 ; s < issueList.size() ; s++ ) {
								if(lotteryTimeSetBeanList.get(k).getIssue() == issueList.get(s)) {
									issueDoesIsExist = true;
									break;
								}
							}
							if(issueDoesIsExist == false) {
								mapLogData.put("2_"+lotteryTimeSetBeanList.get(k).getIssue(),lotteryTimeSetBeanList.get(k));
							}
						}
					}
					else if(lotteryTimeSetBeanList2.size() < lotteryTimeSetBeanList.size()){
						for(int i = 0 ; i < lotteryTimeSetBeanList.size() ; i++) {
							boolean issueDoesIsExist = false;
							for(int j = 0 ; j < lotteryTimeSetBeanList2.size() ; j++) {
								if(lotteryTimeSetBeanList2.get(j).getIssue() == lotteryTimeSetBeanList.get(i).getIssue()) {
									issueDoesIsExist = true;
									if(CommandUtil.compareBean(lotteryTimeSetBeanList.get(i),lotteryTimeSetBeanList2.get(j)) == false) {
										mapLogData.put("0_"+lotteryTimeSetBeanList2.get(i).getIssue(), CommandUtil.compareBeanDiff(lotteryTimeSetBeanList.get(i),lotteryTimeSetBeanList2.get(j)));
									}
									issueList.add(lotteryTimeSetBeanList2.get(i).getIssue());
								}
							}
							if(issueDoesIsExist == false) {
								mapLogData.put("2_"+lotteryTimeSetBeanList.get(i).getIssue(),lotteryTimeSetBeanList.get(i));
							}
						}
						
						for(int k = 0 ; k < lotteryTimeSetBeanList2.size() ; k++) {
							boolean issueDoesIsExist = false;
							for(int s = 0 ; s < issueList.size() ; s++ ) {
								if(lotteryTimeSetBeanList2.get(k).getIssue() == issueList.get(s)) {
									issueDoesIsExist = true;
									break;
								}
							}
							if(issueDoesIsExist == false) {
								mapLogData.put("1_"+lotteryTimeSetBeanList2.get(k).getIssue(),lotteryTimeSetBeanList2.get(k));
							}
						}
					}
					
		
					
					
					jsonLogObj = new JSONObject(mapLogData);
					LOG.debug(mapLogData.toString());
					
					todo = true;
					int cou = ((ISetLotteryTimeDao) dao).selectLotterySettingCount(id);
					if(cou > 0) {
						todo = ((ISetLotteryTimeDao) dao).deleteLotterySetting(id);
					}
					if(todo) {
						todo = ((ISetLotteryTimeDao) dao).insertLotteryTime(lotteryTimeSetBeanList);
					}
					if(todo) {
						todo = ((ISetLotteryTimeDao) dao).updateLotteryType(id , createPeriodType , zodiacType);
					}
					
					if(todo) {
						int actionId = 0;
						switch(id) {
							case CQSSC:
								actionId = LOG_ACTION_LOTTERY_CQSSC_TIME_SET;
							break;
							case TJSSC:
								actionId = LOG_ACTION_LOTTERY_TJSSC_TIME_SET;
							break;
							case YNASSC:
								actionId = LOG_ACTION_LOTTERY_YNSSC_TIME_SET;
							break;
							case XJSSC:
								actionId = LOG_ACTION_LOTTERY_XJSSC_TIME_SET;
							break;
							case QQFFC:
								actionId = LOG_ACTION_LOTTERY_QQFFC_TIME_SET;
							break;
							case TXFFC:
								actionId = LOG_ACTION_LOTTERY_TXFFC_TIME_SET;
							break;
							case GD11X5:
								actionId = LOG_ACTION_LOTTERY_GD11X5_TIME_SET;
							break;
							case JX11X5:
								actionId = LOG_ACTION_LOTTERY_JX11X5_TIME_SET;
							break;
							case SH11X5:
								actionId = LOG_ACTION_LOTTERY_SH11X5_TIME_SET;
							break;
							case SD11X5:
								actionId = LOG_ACTION_LOTTERY_SD11X5_TIME_SET;
							break;
							case JS11X5:
								actionId = LOG_ACTION_LOTTERY_JS11X5_TIME_SET;
							break;
							case LN11X5:
								actionId = LOG_ACTION_LOTTERY_LN11X5_TIME_SET;
							break;
							case GX11X5:
								actionId = LOG_ACTION_LOTTERY_GX11X5_TIME_SET;
							break;
							case AF11X5:
								actionId = LOG_ACTION_LOTTERY_AF11X5_TIME_SET;
							break;
							case HLJ11X5:
								actionId = LOG_ACTION_LOTTERY_HLJ11X5_TIME_SET;
							break;
							case YN11X5:
								actionId = LOG_ACTION_LOTTERY_YN11X5_TIME_SET;
							break;
							case HBK3:
								actionId = LOG_ACTION_LOTTERY_HBK3_TIME_SET;
							break;
							case HLK3:
								actionId = LOG_ACTION_LOTTERY_HLK3_TIME_SET;
							break;
							case GXK3:
								actionId = LOG_ACTION_LOTTERY_GXK3_TIME_SET;
							break;
							case JSK3:
								actionId = LOG_ACTION_LOTTERY_JSK3_TIME_SET;
							break;
							case JLK3:
								actionId = LOG_ACTION_LOTTERY_JLK3_TIME_SET;
							break;
							case AHK3:
								actionId = LOG_ACTION_LOTTERY_AHK3_TIME_SET;
							break;
							case BJPK10:
								actionId = LOG_ACTION_LOTTERY_BJPK10_TIME_SET;
							break;
							case FC3D:
								actionId = LOG_ACTION_LOTTERY_FC3D_TIME_SET;
							break;
							case PL5:
								actionId = LOG_ACTION_LOTTERY_PL5_TIME_SET;
							break;
							case XGLHC:
								actionId = LOG_ACTION_LOTTERY_XGLHC_TIME_SET;
							break;
						}
						todo = dao.setLotteryLogToDBNoCommit(USERID,actionId,jsonLogObj.toString(),USERIP);
					}
				}
				
			}
		}catch(Exception e1) {
			LOG.debug(e1.getMessage());
		}
		finally {
			if(todo) {
				try{
					todo = dao.commitDB();
				}catch(SQLException e) {
					try {
						todo = dao.rollBackDB();
					} catch (SQLException e1) {
						LOG.debug(e1.getMessage());
					}
				}
			}
			else {
				try {
					todo = dao.rollBackDB();
				} catch (SQLException e) {
					LOG.debug(e.getMessage());
				}
			}
		}
		return todo;
	}
	
	@Override
	public List<Map<String,Object>> getLotteryTimeSetLog() {
		return ((ISetLotteryTimeDao) dao).getLotteryTimeSetLog(USERID);
	}
	


}
