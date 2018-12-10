package tw.com.ctt.service.impl;

import static tw.com.ctt.constant.LotteryConstant.*;
import static tw.com.ctt.constant.LotteryLogToDBConstant.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import tw.com.ctt.dao.ISetLotteryNumDao;
import tw.com.ctt.service.ISetLotteryNumService;
import tw.com.ctt.util.CTTLotteryCalc;
import tw.com.ctt.util.CTTLotteryCalc115;
import tw.com.ctt.util.CTTLotteryCalc3d;
import tw.com.ctt.util.CTTLotteryCalcK3;
import tw.com.ctt.util.CTTLotteryCalcMark6;
import tw.com.ctt.util.CTTLotteryCalcPk10;
import tw.com.ctt.util.CTTLotteryCalcSsc;

public class SetLotteryNumServiceImpl extends BaseService implements ISetLotteryNumService {

	private static final long serialVersionUID = 5855274351289897836L;
	private static final Logger LOG = LogManager.getLogger(SetLotteryNumServiceImpl.class.getName());

	public SetLotteryNumServiceImpl() {

	}

	public SetLotteryNumServiceImpl(long userId, String userIp) {
		super(userId, userIp);
	}

	@Override
	public List<Map<String, Object>> getLotteryListData(int id, String searchDate) {

		return ((ISetLotteryNumDao) dao).getLotteryListData(id, searchDate);
	}
	

	@Override
	public boolean setLotteryNum(int id, String periodNum, String updateData) {
		boolean todo = false;
		JSONObject jsonLogObj = null;
		List<String> periodNumList = null;
		try {
			jsonLogObj = new JSONObject();
			periodNumList = new ArrayList<String>();
			periodNumList.add(periodNum);
			List<Map<String ,Object>> listMap = ((ISetLotteryNumDao) dao).selectPeriodNumData(id,periodNumList);
			
			if(listMap != null && listMap.size() == 1) {
				if(listMap.get(0).containsKey("id")) {
					id = Integer.parseInt(listMap.get(0).get("id").toString());
				}
				if(listMap.get(0).containsKey("periodNum")) {
					periodNum = listMap.get(0).get("periodNum").toString();
				}
				for(String key : listMap.get(0).keySet()) {
					jsonLogObj.put(key, listMap.get(0).get(key).toString());
				}
				jsonLogObj.put("updateData", ""+updateData);
				jsonLogObj.put("updateStatus", "2");
				todo = ((ISetLotteryNumDao) dao).setLotteryNum(id, periodNum, updateData);
				
				if(todo) {
					todo = dao.setLotteryLogToDBNoCommit(USERID,LOG_ACTION_LOTTERY_NUM_SET,jsonLogObj.toString(),USERIP);
				}
			}
			
//			if (todo) {
//				todo = ((ISetLotteryNumDao) dao).insertLotteryLog(id, periodNum, 2);
//			}
		} catch (Exception e) {
			todo = false;
		} finally {
			if (todo) {
				try {
					todo = dao.commitDB();
				} catch (SQLException e) {
					try {
						todo = dao.rollBackDB();
					} catch (SQLException e1) {
					}
				}
			} else {
				try {
					todo = dao.rollBackDB();
				} catch (SQLException e) {
				}
			}
		}
		return todo;

	}

	@Override
	public boolean setLotteryFali(int id, String periodNum, String updateData) {
		boolean todo = false;
		JSONObject jsonLogObj = null;
		List<String> periodNumList = null;
		try {
			jsonLogObj = new JSONObject();
			periodNumList = new ArrayList<String>();
			periodNumList.add(periodNum);
			List<Map<String ,Object>> listMap = ((ISetLotteryNumDao) dao).selectPeriodNumData(id,periodNumList);
			
			if(listMap != null && listMap.size() == 1) {
				if(listMap.get(0).containsKey("id")){
					id = Integer.parseInt(listMap.get(0).get("id").toString());
				}
				if(listMap.get(0).containsKey("periodNum")){
					periodNum = listMap.get(0).get("periodNum").toString();
				}
				
				for(String key : listMap.get(0).keySet()) {
					jsonLogObj.put(key, listMap.get(0).get(key).toString());
				}
				jsonLogObj.put("updateData", ""+updateData);
				jsonLogObj.put("updateStatus", "3");
				
				todo = ((ISetLotteryNumDao) dao).setLotteryFali(id, periodNum, updateData);
				
				if(todo) {
					todo = dao.setLotteryLogToDBNoCommit(USERID,LOG_ACTION_LOTTERY_NUM_FAIL,jsonLogObj.toString(),USERIP);
				}
				
			}
			
			
//			if (todo) {
//				todo = ((ISetLotteryNumDao) dao).insertLotteryLog(id, periodNum, 3);
//			}
		} catch (Exception e) {
			todo = false;
		} finally {
			if (todo) {
				try {
					todo = dao.commitDB();
				} catch (SQLException e) {
					try {
						todo = dao.rollBackDB();
					} catch (SQLException e1) {
					}
				}
			} else {
				try {
					todo = dao.rollBackDB();
				} catch (SQLException e) {
				}
			}
		}
		return todo;
	}

	@Override
	public boolean deletePeriodNum(int id, String periodNum) {
		boolean todo = false;
		JSONArray jsonLogArray = null;
		try {
			jsonLogArray = new JSONArray();
			
			List<String> periodNumList = ((ISetLotteryNumDao) dao).selectDeletePeriodNum(id, periodNum);
			if(periodNumList != null && periodNumList.size() > 0) {
				List<Map<String ,Object>> listMap = ((ISetLotteryNumDao) dao).selectPeriodNumData(id,periodNumList);
				if(listMap != null && listMap.size() > 0) {
					periodNumList.clear();
					for(int i = 0 ; i < listMap.size() ; i++) {
						JSONObject jsonLogObj = new JSONObject();
						for(String key : listMap.get(i).keySet()) {
							if("periodNum".equals(key)) {
								periodNumList.add(listMap.get(i).get(key).toString());
							}
							jsonLogObj.put(key, listMap.get(i).get(key).toString());
						}
						jsonLogArray.put(jsonLogObj);
					}
					
					if(periodNumList.size() > 0) {
						for (int i = 0; i < periodNumList.size(); i++) {
							((ISetLotteryNumDao) dao).cancleAllPeriodOrder(Long.parseLong(periodNumList.get(i)), id);
						}
						
						todo = ((ISetLotteryNumDao) dao).deletePeriodNum(id, periodNumList);
						
						if(todo) {
							todo= dao.setLotteryLogToDBNoCommit(USERID,LOG_ACTION_LOTTERY_DELETE_PERIOD_NUM,jsonLogArray.toString(),USERIP);
						}
						
						if (todo) {
							try {
								todo = dao.commitDB();
								
							} catch (SQLException e) {
								try {
									todo = dao.rollBackDB();
								} catch (SQLException e1) {
								}
							}
						} else {
							try {
								todo = dao.rollBackDB();
							} catch (SQLException e) {
							}
						}
					}
				}
			}

		} catch (Exception e) {
			todo = false;
		} 
		
		return todo;
	}

	@Override
	public boolean withdrawalPeriodNum(int id, String periodNum) {
		boolean todo = false;
		JSONObject jsonLogObj = null;
		List<String> periodNumList = null;
		try {
			jsonLogObj = new JSONObject();
			periodNumList = new ArrayList<String>();
			periodNumList.add(periodNum);
			List<Map<String ,Object>> listMap = ((ISetLotteryNumDao) dao).selectPeriodNumData(id,periodNumList);
			
			if(listMap.size() == 1) {
				if(listMap.get(0).containsKey("id")){
					id = Integer.parseInt(listMap.get(0).get("id").toString());
				}
				if(listMap.get(0).containsKey("periodNum")){
					periodNum = listMap.get(0).get("periodNum").toString();
				}
				for(String key : listMap.get(0).keySet()) {
					jsonLogObj.put(key, listMap.get(0).get(key).toString());
				}
				jsonLogObj.put("updateStatus", "4");
				
				todo = ((ISetLotteryNumDao) dao).withdrawalPeriodNum(id, periodNum);
				
				if(todo) {
					todo = dao.setLotteryLogToDBNoCommit(USERID,LOG_ACTION_LOTTERY_WITHDRAWAL_PERIOD_NUM,jsonLogObj.toString(),USERIP);
				}
				
				if (todo) {
					try {
						todo = dao.commitDB();
						((ISetLotteryNumDao) dao).cancleAllPeriodOrder(Long.parseLong(periodNum), id);
					} catch (SQLException e) {
						try {
							todo = dao.rollBackDB();
						} catch (SQLException e1) {
						}
					}
				} else {
					try {
						todo = dao.rollBackDB();
					} catch (SQLException e) {
					}
				}
			}
//			if (todo) {
//				todo = ((ISetLotteryNumDao) dao).insertLotteryLog(id, periodNum, 4);
//			}

		} catch (Exception e) {
			todo = false;
		} 
		
		return todo;
	}

	@Override
	public boolean callCheckTheLottery(int id, long periodNum) {
		boolean todo = false;
		String tableName = "";
		int zodiacType = 0;
		Map<String, String> result = null;

		CTTLotteryCalc calc = null;
		JSONObject jsonLogObj = null;

		List<String> periodNumList = null;
		List<Integer> intList = null;
		
		try {
			jsonLogObj = new JSONObject();
			intList = new ArrayList<Integer>();
			periodNumList = new ArrayList<String>();
			periodNumList.add(""+periodNum);
			List<Map<String ,Object>> listMap = ((ISetLotteryNumDao) dao).selectPeriodNumData(id,periodNumList);
			
			if(listMap.size() == 1) {

				if(listMap.get(0).containsKey("data")){
					String data = listMap.get(0).get("data").toString();
					String[] dataArray = data.split(",");
					for(int i = 0 ; i < dataArray.length ; i++) {
						intList.add(Integer.parseInt(dataArray[i]));
					}
				}
				
				if(listMap.get(0).containsKey("id")){
					id = Integer.parseInt(listMap.get(0).get("id").toString());
				}
				if(listMap.get(0).containsKey("periodNum")){
					periodNum = Long.parseLong(listMap.get(0).get("periodNum").toString());
				}
				
				if(listMap.get(0).containsKey("zodiacType")){
					zodiacType = Integer.parseInt(listMap.get(0).get("zodiacType").toString());
				}
				
				for(String key : listMap.get(0).keySet()) {
					jsonLogObj.put(key, listMap.get(0).get(key).toString());
				}
				
				todo = ((ISetLotteryNumDao) dao).goCheckLottery(id, periodNum);
				if (todo == true) {
					if (intList != null && intList.size() > 0) {
						switch (id) {
						case CQSSC:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalcSsc();
							}
							tableName = CQSSC_TABLE_NAME;
							break;
						case TJSSC:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalcSsc();
							}
							tableName = TJSSC_TABLE_NAME;
							break;
						case YNASSC:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalcSsc();
							}
							tableName = YNASSC_TABLE_NAME;
							break;
						case XJSSC:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalcSsc();
							}
							tableName = XJSSC_TABLE_NAME;
							break;
						case QQFFC:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalcSsc();
							}
							tableName = QQFFC_TABLE_NAME;
							break;
						case TXFFC:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalcSsc();
							}
							tableName = TXFFC_TABLE_NAME;
							break;
						case GD11X5:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalc115();
							}

							tableName = GD11X5_TABLE_NAME;
							break;
						case JX11X5:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalc115();
							}
							tableName = JX11X5_TABLE_NAME;
							break;
						case SH11X5:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalc115();
							}
							tableName = SH11X5_TABLE_NAME;
							break;
						case SD11X5:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalc115();
							}
							tableName = SD11X5_TABLE_NAME;
							break;
						case JS11X5:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalc115();
							}
							tableName = JS11X5_TABLE_NAME;
							break;
						case LN11X5:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalc115();
							}
							tableName = LN11X5_TABLE_NAME;
							break;
						case GX11X5:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalc115();
							}
							tableName = GX11X5_TABLE_NAME;
							break;
						case AF11X5:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalc115();
							}
							tableName = AF11X5_TABLE_NAME;
							break;
						case HLJ11X5:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalc115();
							}
							tableName = HLJ11X5_TABLE_NAME;
							break;
						case YN11X5:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalc115();
							}
							tableName = YN11X5_TABLE_NAME;
							break;
						case HBK3:
							if (intList.size() == 3) {
								calc = new CTTLotteryCalcK3();
							}

							tableName = HBK3_TABLE_NAME;
							break;
						case HLK3:
							if (intList.size() == 3) {
								calc = new CTTLotteryCalcK3();
							}
							tableName = HLK3_TABLE_NAME;
							break;
						case GXK3:
							if (intList.size() == 3) {
								calc = new CTTLotteryCalcK3();
							}
							tableName = GXK3_TABLE_NAME;
							break;
						case JSK3:
							if (intList.size() == 3) {
								calc = new CTTLotteryCalcK3();
							}
							tableName = JSK3_TABLE_NAME;
							break;
						case JLK3:
							if (intList.size() == 3) {
								calc = new CTTLotteryCalcK3();
							}
							tableName = JLK3_TABLE_NAME;
							break;
						case AHK3:
							if (intList.size() == 3) {
								calc = new CTTLotteryCalcK3();
							}
							tableName = AHK3_TABLE_NAME;
							break;
						case BJPK10:
							if (intList.size() == 10) {
								calc = new CTTLotteryCalcPk10();
							}

							tableName = BJPK10_TABLE_NAME;
							break;
						case FC3D:
							if (intList.size() == 3) {
								calc = new CTTLotteryCalc3d();
							}

							tableName = FC3D_TABLE_NAME;
							break;
						case PL5:
							if (intList.size() == 5) {
								calc = new CTTLotteryCalcSsc();
							}
							tableName = PL5_TABLE_NAME;
							break;
						case XGLHC:
							if(intList.size() == 7) {
								HashMap<String, String> zodiacNumMap = new HashMap<String, String>();
								for(int k = 1 ; k <= 49 ; k++) {
									zodiacNumMap.put(k+"", zodiacType+"");
									zodiacType--;
									if(zodiacType <= 0) {
										zodiacType = 12;
									}
								}
								calc = new CTTLotteryCalcMark6();
								
								((CTTLotteryCalcMark6) calc).setAnimals((Map<String, String>)zodiacNumMap.clone());
								
								tableName = XGLHC_TABLE_NAME;
								zodiacNumMap.clear();
								zodiacNumMap = null;
							}
							break;
						}
						result = calc.getResult(intList);
						
						
						todo = ((ISetLotteryNumDao) dao).addPairAward(tableName, periodNum, result);
						
						
						if(todo) {
							todo = dao.setLotteryLogToDBNoCommit(USERID,LOG_ACTION_LOTTERY_CHECK_THE_LOTTERY,jsonLogObj.toString(),USERIP);
						}
						
						if (todo) {
							try {
								todo = dao.commitDB();
								todo = ((ISetLotteryNumDao) dao).callCheckTheLottery(id, periodNum, tableName);
							} catch (SQLException e) {
								LOG.error(e.getMessage());
								try {
									todo = dao.rollBackDB();
								} catch (SQLException e1) {
									LOG.error(e1.getMessage());
								}
							}
						} else {
							try {
								todo = dao.rollBackDB();
							} catch (SQLException e) {
								LOG.error(e.getMessage());
							}
						}
					} 
				
			}

			
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			todo = false;
		}
		return todo;
	}
	
	
	@Override
	public List<Map<String, Object>> getLotteryNumSetLog(int logAction, String startTime, String endTime) {
		return ((ISetLotteryNumDao) dao).getLotteryNumSetLog(USERID,logAction, startTime, endTime);
	}

	
	@Override
	public boolean recoverPeriodNumWinningMoney(int gameId , long periodNum) {
		boolean todo = false;
		JSONObject jsonLogObj = null;
		List<String> periodNumList = null;
		List<Integer> intList = null;
		try {
			jsonLogObj = new JSONObject();
			intList = new ArrayList<Integer>();
			periodNumList = new ArrayList<String>();
			periodNumList.add(""+periodNum);
			List<Map<String ,Object>> listMap = ((ISetLotteryNumDao) dao).selectPeriodNumData(gameId,periodNumList);
			
			if(listMap.size() == 1) {
				if(listMap.get(0).containsKey("id")){
					gameId = Integer.parseInt(listMap.get(0).get("id").toString());
				}
				if(listMap.get(0).containsKey("periodNum")){
					periodNum = Long.parseLong(listMap.get(0).get("periodNum").toString());
				}
				
				for(String key : listMap.get(0).keySet()) {
					jsonLogObj.put(key, listMap.get(0).get(key).toString());
				}
				
				jsonLogObj.put("updateStatus", "5");
				
				todo = ((ISetLotteryNumDao) dao).refundWinningMoney(gameId,periodNum);
				
				if(todo) {
					todo = dao.setLotteryLogToDBNoCommit(USERID,LOG_ACTION_LOTTERY_REFUND,jsonLogObj.toString(),USERIP);
				}
				
				if (todo) {
					try {
						todo = dao.commitDB();
						todo = ((ISetLotteryNumDao) dao).callRecoverPeriodNumWinningMoney(gameId,periodNum);
					} catch (SQLException e) {
						LOG.error(e.getMessage());
						try {
							todo = dao.rollBackDB();
						} catch (SQLException e1) {
							LOG.error(e1.getMessage());
						}
					}
				} else {
					try {
						todo = dao.rollBackDB();
					} catch (SQLException e) {
						LOG.error(e.getMessage());
					}
				}
			}
		}
		catch(Exception e) {
			LOG.error(e.getMessage());
			todo = false;
		}
		
		return todo;
	}
	
	@Override
	public boolean renewCallCheckTheLottery(int id ,long periodNum) {
		boolean todo = false;
		String tableName = "";
		int zodiacType = 0;
		Map<String, String> result = null;

		CTTLotteryCalc calc = null;
		JSONObject jsonLogObj = null;

		List<String> periodNumList = null;
		List<Integer> intList = null;
		
		try {
			jsonLogObj = new JSONObject();
			intList = new ArrayList<Integer>();
			periodNumList = new ArrayList<String>();
			periodNumList.add(""+periodNum);
			List<Map<String ,Object>> listMap = ((ISetLotteryNumDao) dao).selectPeriodNumData(id,periodNumList);
			
			if(listMap.size() == 1) {
				String data = "";
				if(listMap.get(0).containsKey("actualData")){
					data = listMap.get(0).get("actualData").toString();
					String[] dataArray = data.split(",");
					for(int i = 0 ; i < dataArray.length ; i++) {
						intList.add(Integer.parseInt(dataArray[i]));
					}
				}
				
				if(listMap.get(0).containsKey("id")){
					id = Integer.parseInt(listMap.get(0).get("id").toString());
				}
				if(listMap.get(0).containsKey("periodNum")){
					periodNum = Long.parseLong(listMap.get(0).get("periodNum").toString());
				}
				
				if(listMap.get(0).containsKey("zodiacType")){
					zodiacType = Integer.parseInt(listMap.get(0).get("zodiacType").toString());
				}
				
				for(String key : listMap.get(0).keySet()) {
					jsonLogObj.put(key, listMap.get(0).get(key).toString());
				}
				jsonLogObj.put("updateData", data);
				jsonLogObj.put("updateStatus", "6");
				
				
				
				if (intList != null && intList.size() > 0) {
					todo = ((ISetLotteryNumDao) dao).renewGoCheckLottery(id, periodNum);
					
					switch (id) {
					case CQSSC:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalcSsc();
						}
						tableName = CQSSC_TABLE_NAME;
						break;
					case TJSSC:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalcSsc();
						}
						tableName = TJSSC_TABLE_NAME;
						break;
					case YNASSC:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalcSsc();
						}
						tableName = YNASSC_TABLE_NAME;
						break;
					case XJSSC:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalcSsc();
						}
						tableName = XJSSC_TABLE_NAME;
						break;
					case QQFFC:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalcSsc();
						}
						tableName = QQFFC_TABLE_NAME;
						break;
					case TXFFC:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalcSsc();
						}
						tableName = TXFFC_TABLE_NAME;
						break;
					case GD11X5:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalc115();
						}

						tableName = GD11X5_TABLE_NAME;
						break;
					case JX11X5:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalc115();
						}
						tableName = JX11X5_TABLE_NAME;
						break;
					case SH11X5:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalc115();
						}
						tableName = SH11X5_TABLE_NAME;
						break;
					case SD11X5:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalc115();
						}
						tableName = SD11X5_TABLE_NAME;
						break;
					case JS11X5:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalc115();
						}
						tableName = JS11X5_TABLE_NAME;
						break;
					case LN11X5:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalc115();
						}
						tableName = LN11X5_TABLE_NAME;
						break;
					case GX11X5:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalc115();
						}
						tableName = GX11X5_TABLE_NAME;
						break;
					case AF11X5:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalc115();
						}
						tableName = AF11X5_TABLE_NAME;
						break;
					case HLJ11X5:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalc115();
						}
						tableName = HLJ11X5_TABLE_NAME;
						break;
					case YN11X5:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalc115();
						}
						tableName = YN11X5_TABLE_NAME;
						break;
					case HBK3:
						if (intList.size() == 3) {
							calc = new CTTLotteryCalcK3();
						}

						tableName = HBK3_TABLE_NAME;
						break;
					case HLK3:
						if (intList.size() == 3) {
							calc = new CTTLotteryCalcK3();
						}
						tableName = HLK3_TABLE_NAME;
						break;
					case GXK3:
						if (intList.size() == 3) {
							calc = new CTTLotteryCalcK3();
						}
						tableName = GXK3_TABLE_NAME;
						break;
					case JSK3:
						if (intList.size() == 3) {
							calc = new CTTLotteryCalcK3();
						}
						tableName = JSK3_TABLE_NAME;
						break;
					case JLK3:
						if (intList.size() == 3) {
							calc = new CTTLotteryCalcK3();
						}
						tableName = JLK3_TABLE_NAME;
						break;
					case AHK3:
						if (intList.size() == 3) {
							calc = new CTTLotteryCalcK3();
						}
						tableName = AHK3_TABLE_NAME;
						break;
					case BJPK10:
						if (intList.size() == 10) {
							calc = new CTTLotteryCalcPk10();
						}

						tableName = BJPK10_TABLE_NAME;
						break;
					case FC3D:
						if (intList.size() == 3) {
							calc = new CTTLotteryCalc3d();
						}

						tableName = FC3D_TABLE_NAME;
						break;
					case PL5:
						if (intList.size() == 5) {
							calc = new CTTLotteryCalcSsc();
						}
						tableName = PL5_TABLE_NAME;
						break;
					case XGLHC:
						if(intList.size() == 7) {
							HashMap<String, String> zodiacNumMap = new HashMap<String, String>();
							for(int k = 1 ; k <= 49 ; k++) {
								zodiacNumMap.put(k+"", zodiacType+"");
								zodiacType--;
								if(zodiacType <= 0) {
									zodiacType = 12;
								}
							}
							calc = new CTTLotteryCalcMark6();
							
							((CTTLotteryCalcMark6) calc).setAnimals((Map<String, String>)zodiacNumMap.clone());
							
							tableName = XGLHC_TABLE_NAME;
							zodiacNumMap.clear();
							zodiacNumMap = null;
						}
						break;
					}
					result = calc.getResult(intList);
					
					if(todo) {
						todo = ((ISetLotteryNumDao) dao).addPairAward(tableName, periodNum, result);
					}
					if(todo) {
						todo = dao.setLotteryLogToDBNoCommit(USERID,LOG_ACTION_LOTTERY_CHECK_THE_LOTTERY,jsonLogObj.toString(),USERIP);
					}
					
					if (todo) {
						try {
							todo = dao.commitDB();
							todo = ((ISetLotteryNumDao) dao).callCheckTheLottery(id, periodNum, tableName);
						} catch (SQLException e) {
							LOG.error(e.getMessage());
							try {
								todo = dao.rollBackDB();
							} catch (SQLException e1) {
								LOG.error(e1.getMessage());
							}
						}
					} else {
						try {
							todo = dao.rollBackDB();
						} catch (SQLException e) {
							LOG.error(e.getMessage());
						}
					}
				} 
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			todo = false;
		}
		return todo;
	}
	
	
	@Override
	public Map<String, Object> getNextAndPreviousLotteryListData(int id) {
		return ((ISetLotteryNumDao) dao).getNextAndPreviousLotteryListData(id);
	}

}
