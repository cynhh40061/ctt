package tw.com.ctt.server;

import static tw.com.ctt.constant.LotteryConstant.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.dao.impl.LotteryDaoImpl;
import tw.com.ctt.model.LotteryTypeBean;
import tw.com.ctt.service.impl.LotteryServiceImpl;
import tw.com.ctt.util.CTTLotteryCalc;
import tw.com.ctt.util.CTTLotteryCalc115;
import tw.com.ctt.util.CTTLotteryCalc3d;
import tw.com.ctt.util.CTTLotteryCalcK3;
import tw.com.ctt.util.CTTLotteryCalcMark6;
import tw.com.ctt.util.CTTLotteryCalcPk10;
import tw.com.ctt.util.CTTLotteryCalcSsc;
import tw.com.ctt.util.MyUtil;

public class KjRunnable extends Thread{

	public boolean isStatus = true;
	
	private static final Logger LOG = LogManager.getLogger(KjRunnable.class.getName());
	
	private LotteryServiceImpl lotteryService;
	
	public KjRunnable() {
		this.isStatus = true;
		this.lotteryService = new LotteryServiceImpl();

	}
	
	@Override
	public void run() {
		try {
			CTTLotteryCalcSsc sscCal = new CTTLotteryCalcSsc();
			CTTLotteryCalc115 x5Cal = new CTTLotteryCalc115();
			CTTLotteryCalcK3 k3Cal = new CTTLotteryCalcK3();
			CTTLotteryCalcPk10 pk10Cal = new CTTLotteryCalcPk10();
			CTTLotteryCalc3d d3Cal = new CTTLotteryCalc3d();
			CTTLotteryCalcMark6 mark6Cal = new  CTTLotteryCalcMark6();
			
			Map<String ,String> zodiacNumMap = null;
			Map<String ,String> result = null;
			List<Map<String ,Object>> listMap = null;
			List<Map<String ,Object>> listMap2 = null;
			while(true) {
				//System.out.println("thread KjRunnable : ");
				
				listMap = this.lotteryService.selectNowKjPeriodNum();
				if(listMap != null) {
					for(int i = 0 ; i < listMap.size() ; i++) {
						if(listMap.get(i).containsKey("id") && listMap.get(i).containsKey("periodNum") && listMap.get(i).containsKey("data") 
								&& listMap.get(i).get("periodNum").toString() != "" && listMap.get(i).get("data").toString() != "" && listMap.get(i).get("id").toString() != ""
								&& listMap.get(i).containsKey("zodiacType") && !"".equals(listMap.get(i).get("zodiacType").toString())) {
							
							int id = MyUtil.toInt(listMap.get(i).get("id").toString());
							
							if(MyUtil.checkLotteryNum(id,listMap.get(i).get("data").toString())) {
								
								List<Integer> intList = new ArrayList<Integer>();
								String[] intArr  = listMap.get(i).get("data").toString().split(",");
								for(String num : intArr) {
									if(num != "") {
										int numInt = MyUtil.toInt(num);
										intList.add(numInt);
									}
								}
								
								switch(id) {
									case CQSSC:
									case TJSSC:
									case YNASSC:
									case XJSSC:
									case QQFFC:
									case TXFFC:
									case PL5:
										if(intList.size() == 5) {
											result = sscCal.getResult(intList);
										}
										break;
									case GD11X5:
									case JX11X5:
									case SH11X5:
									case SD11X5:
									case JS11X5:
									case LN11X5:
									case GX11X5:
									case AF11X5:
									case HLJ11X5:
									case YN11X5:
										if(intList.size() == 5) {
											result = x5Cal.getResult(intList);
										}
										break;
									case HBK3:
									case HLK3:
									case GXK3:
									case JSK3:
									case JLK3:
									case AHK3:
										if(intList.size() == 3) {
											result = k3Cal.getResult(intList);
										}
										break;
									case BJPK10:
										if(intList.size() == 10) {
											result = pk10Cal.getResult(intList);
										}
										break;
									case FC3D:
										if(intList.size() == 3) {
											result = d3Cal.getResult(intList);
										}
										break;
									case XGLHC:
										if(intList.size() == 7) {
											int zodiacType = Integer.parseInt(listMap.get(i).get("zodiacType").toString());
											zodiacNumMap = new ConcurrentHashMap<String, String>();
											for(int k = 1 ; k <= 49 ; k++) {
												zodiacNumMap.put(k+"", zodiacType+"");
												zodiacType--;
												if(zodiacType <= 0) {
													zodiacType = 12;
												}
											}
											
											mark6Cal.setAnimals(zodiacNumMap);
											
											result = mark6Cal.getResult(intList);
											
											LOG.error(result.toString());
//											
											zodiacNumMap.clear();
											zodiacNumMap = null;
										}
										break;
								}
								if(result != null ) {
									System.out.println(id);
									this.lotteryService.addPairAward(id,Long.parseLong(listMap.get(i).get("periodNum").toString()),result);
									
									result.clear();
									result = null;
								}
							}
						}
					}
					if(listMap != null) {
						listMap.clear();
						listMap = null;
					}
				}
				
				listMap2 = this.lotteryService.selectGiveUpPeriodNum();
				if(listMap2 != null && listMap2.size() > 0) {
					for(int i = 0 ; i < listMap2.size() ; i++) {
						if(listMap2.get(i).containsKey("id") && listMap2.get(i).containsKey("periodNum") && listMap2.get(i).get("periodNum").toString() != "" 
								&& listMap2.get(i).get("id").toString() != "") {
							int lotetryId = MyUtil.toInt(listMap2.get(i).get("id").toString());
							long periodNum= MyUtil.toLong(listMap2.get(i).get("periodNum").toString());
							
							try {
								this.lotteryService.callMainOrderAfterGiveUp(lotetryId, periodNum);
							}
							catch(Exception e) {
								LOG.error(e.getMessage());
							}
							
						}
					}
					listMap2.clear();
					listMap2 = null;
				}
				Thread.sleep(6000);
			}
		} catch (InterruptedException e) {
			LOG.error(e);
			isStatus = false;
		}
		catch(Exception e) {
			isStatus = false;
			LOG.error(e);
			this.interrupt();
		}
	}
}
