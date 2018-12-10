package tw.com.ctt.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.ctt.model.LotteryTimeSetBean;

public class SettingLottery {

	private int lotteryTypeId;

	private static final Logger LOG = LogManager.getLogger(SettingLottery.class.getName());

	private List<Map<String, Object>> baseicSetList;
	
	private int type ;
	
	private final int CREATE_TYPE_DAY = 0;
	private final int CREATE_TYPE_WEEK = 1;

	public SettingLottery(int lotteryTypeId) {
		this.baseicSetList = new CopyOnWriteArrayList<Map<String, Object>>();
		this.lotteryTypeId = lotteryTypeId;
		this.type = 0;
	}

	public void baseicSet( int type ,long startLotteryTime, long kjDelayTime, long intervals, int Issue, long giveUpKjTime ,long jumpOffTime ) {
		if(CREATE_TYPE_DAY == type) {
			Map<String, Object> map = new ConcurrentHashMap<String, Object>();
			if(this.type != CREATE_TYPE_DAY) {
				this.baseicSetList = new CopyOnWriteArrayList<Map<String, Object>>();
			}
			
			this.type = CREATE_TYPE_DAY;
			
			map.put("type", CREATE_TYPE_DAY);
			map.put("startLotteryTime", startLotteryTime);
			map.put("kjDelayTime", kjDelayTime);
			map.put("intervals", intervals);
			map.put("Issue", Issue);
			map.put("giveUpKjTime", giveUpKjTime);
			map.put("jumpOffTime", jumpOffTime);
			
			this.baseicSetList.add(map);
		}

	}
	
	public void baseicWeekSet( int type ,long startLotteryTime, long kjDelayTime, long giveUpKjTime ,long jumpOffTime , int startBettingWeekType, long startBettingTime, int createWeekType , int createPeriodWeekType,long createPeriodTime) {
		if(CREATE_TYPE_WEEK == type) {
			Map<String, Object> map = new ConcurrentHashMap<String, Object>();
			
			if(this.type != CREATE_TYPE_WEEK) {
				this.baseicSetList = new CopyOnWriteArrayList<Map<String, Object>>();
			}
			this.type = CREATE_TYPE_WEEK;
			
			map.put("type", CREATE_TYPE_WEEK);
			map.put("startLotteryTime", startLotteryTime);
			map.put("kjDelayTime", kjDelayTime);
			map.put("giveUpKjTime", giveUpKjTime);
			map.put("jumpOffTime", jumpOffTime);
			map.put("startBettingWeekType", startBettingWeekType);
			map.put("startBettingTime", startBettingTime);
			map.put("createWeekType", createWeekType);
			map.put("createPeriodWeekType", createPeriodWeekType);
			map.put("createPeriodTime",createPeriodTime);
			
			this.baseicSetList.add(map);
		}
	}
	

	public void close() {
		this.baseicSetList.clear();
		this.lotteryTypeId = 0;
		this.baseicSetList = null;
	}
	
	public List<LotteryTimeSetBean> getLotterySetting(){
		if(this.type == CREATE_TYPE_WEEK) {
			return getWeekLotterySetting();
		}
		else if(this.type == CREATE_TYPE_DAY){
			return getDayLotterySetting();
		}
		else {
			return null;
		}
	}
	
	public List<LotteryTimeSetBean> getWeekLotterySetting(){
		List<LotteryTimeSetBean> listMap = null;
		if(baseicSetList.size() > 0 && baseicSetList != null) {
			try {
				if(this.type == CREATE_TYPE_WEEK) {
					int j = 1;
					listMap = new ArrayList<LotteryTimeSetBean>();
					arraySortMap(this.baseicSetList);
					for (int i = 0; i < this.baseicSetList.size(); i++) {
						long startLotteryTime = toLong(getMapValue(baseicSetList.get(i),"startLotteryTime"));
						long kjDelayTime = toLong(getMapValue(baseicSetList.get(i),"kjDelayTime"));
						long giveUpKjTime = toLong(getMapValue(baseicSetList.get(i),"giveUpKjTime"));
						long jumpOffTime = toLong(getMapValue(baseicSetList.get(i),"jumpOffTime"));
						int startBettingWeekType = toInt(getMapValue(baseicSetList.get(i),"startBettingWeekType"));
						long startBettingTime = toLong(getMapValue(baseicSetList.get(i),"startBettingTime"));
						int createWeekType = toInt(getMapValue(baseicSetList.get(i),"createWeekType"));
						int createPeriodWeekType = toInt(getMapValue(baseicSetList.get(i),"createPeriodWeekType"));
						long createPeriodTime = toLong(getMapValue(baseicSetList.get(i),"createPeriodTime"));
						
						long giveUpLotteryTime = startLotteryTime + giveUpKjTime;
						long platformKjTime = startLotteryTime  - kjDelayTime;
						long stopBettingTime = kjDelayTime;
						long jumpOffLotteryTime = startLotteryTime  + jumpOffTime;
						
						LotteryTimeSetBean lotteryTimeSetBean = new LotteryTimeSetBean();

						lotteryTimeSetBean.setId(this.lotteryTypeId);
						lotteryTimeSetBean.setType( this.type);
						lotteryTimeSetBean.setIssue(j);
						lotteryTimeSetBean.setKjTime(startLotteryTime);
						lotteryTimeSetBean.setGiveUpKjTime(giveUpLotteryTime);
						lotteryTimeSetBean.setStopBettingTime(stopBettingTime);
						lotteryTimeSetBean.setPlatformKjTime(platformKjTime);
						lotteryTimeSetBean.setJumpOffTime(jumpOffLotteryTime);
						lotteryTimeSetBean.setCreateWeekType(createWeekType);
						lotteryTimeSetBean.setCreatePeriodTime(createPeriodTime);
						lotteryTimeSetBean.setCreatePeriodWeekType(createPeriodWeekType);
						lotteryTimeSetBean.setStartBettingWeekType(startBettingWeekType);
						lotteryTimeSetBean.setStartBettingTime(startBettingTime);
						
						
						
						
						listMap.add(lotteryTimeSetBean);
						j++;
						
					}
				}
			}
			catch (Exception e) {
				listMap = null;
				System.out.println("getLottery Exception" + e.getMessage());
				LOG.error(e.getMessage());
			}
		}
		return listMap;
	}

	public List<LotteryTimeSetBean> getDayLotterySetting() {
		List<LotteryTimeSetBean> listMap = null;
		if (baseicSetList.size() > 0 && baseicSetList != null) {
			try {
				listMap = new ArrayList<LotteryTimeSetBean>();
				int j = 1;
				arraySortMap(this.baseicSetList);
				if(this.type == CREATE_TYPE_DAY) {
					for (int i = 0; i < this.baseicSetList.size(); i++) {
						long startLotteryTime = toLong(getMapValue(baseicSetList.get(i),"startLotteryTime"));
						long kjDelayTime = toLong(getMapValue(baseicSetList.get(i),"kjDelayTime"));
						long intervals = toLong(getMapValue(baseicSetList.get(i),"intervals"));
						int Issue = toInt(getMapValue(baseicSetList.get(i),"Issue"));
						long giveUpKjTime = toLong(getMapValue(baseicSetList.get(i),"giveUpKjTime"));
						long jumpOffTime = toLong(getMapValue(baseicSetList.get(i),"jumpOffTime"));
						
						long intervalsNum = 0;
						
						while(Issue > 0) {
							long kjTime = startLotteryTime + (intervals * intervalsNum);
							long giveUpLotteryTime = startLotteryTime + (intervals * intervalsNum) + giveUpKjTime;
							long platformKjTime = startLotteryTime + (intervals * intervalsNum) - kjDelayTime;
							long stopBettingTime = kjDelayTime;
							long jumpOffLotteryTime = startLotteryTime + (intervals * intervalsNum) + jumpOffTime;
							
							
							LotteryTimeSetBean lotteryTimeSetBean = new LotteryTimeSetBean();
							
							lotteryTimeSetBean.setId(this.lotteryTypeId);
							
							lotteryTimeSetBean.setType( this.type);
							lotteryTimeSetBean.setIssue(j);
							lotteryTimeSetBean.setKjTime(kjTime);
							lotteryTimeSetBean.setGiveUpKjTime(giveUpLotteryTime);
							lotteryTimeSetBean.setStopBettingTime(stopBettingTime);
							lotteryTimeSetBean.setPlatformKjTime(platformKjTime);
							lotteryTimeSetBean.setJumpOffTime(jumpOffLotteryTime);
							
			
							listMap.add(lotteryTimeSetBean);
							intervalsNum++;
							j++;
							Issue--;
						}
					}
				}
			} catch (Exception e) {
				System.out.println("getLottery Exception" + e.getMessage());
				LOG.error(e.getMessage());
			}
		}
		return listMap;
	}

	public static String clearNum(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		} else {
			str = str.trim();
			String tmpStr = "";
			Pattern pattern = Pattern.compile("^\\d");
			for (int i = 0; i < str.length(); i++) {
				String tmpC = str.substring(i, i + 1);
				if (pattern.matcher(tmpC).find()) {
					tmpStr += tmpC;
				}
				tmpC = null;
				tmpC = "";
			}
			str = tmpStr;
			tmpStr = "";
			tmpStr = null;
			pattern = null;
			return str;
		}
	}

	public static long toLong(String str) {
		String tmpStr = "";
		if (str != null && str.length() > 0) {
			tmpStr = clearNum(str);
			if (tmpStr.length() > 0 && tmpStr != null && !"".equals(tmpStr)) {
				return Long.parseLong(tmpStr);
			}
		}
		return 0;
	}
	
	public static int toInt(String str) {
		String tmpStr = "";
		if (str != null && str.length() > 0) {
			tmpStr = clearNum(str);
			if (tmpStr.length() > 0 && tmpStr != null && !"".equals(tmpStr)) {
				return Integer.parseInt(tmpStr);
			}
		}
		return 0;
	}

	private void arraySortMap(List<Map<String ,Object>> in) {
		Collections.sort(in, new Comparator<Map<String ,Object>>() {
			@Override
			public int compare(Map<String ,Object> o1, Map<String ,Object> o2) {
				if (o1 == null || o2 == null) {
					return 0;
				}
				if (!((Map<String ,Object>) o1).containsKey("kjTime") || !((Map<String ,Object>) o2).containsKey("kjTime")) {
					return 0;
				}
				try {
					if (Integer.parseInt("" + ((Map<String ,Object>) o1).get("kjTime")) > Integer.parseInt("" + ((Map<String ,Object>) o2).get("kjTime"))) {
						return 1;
					} else if (Integer.parseInt("" + ((Map<String ,Object>) o1).get("kjTime")) < Integer.parseInt("" + ((Map<String ,Object>) o2).get("kjTime"))) {
						return -1;
					} else {
						return 0;
					}
				} catch (Exception e) {
					return 0;
				}
			}
		});
	}
	
	private String getMapValue(Map<String,Object> map , String key) {
		if(map.containsKey(key)) {
			return map.get(key).toString();
		}
		return null;
	}

}
