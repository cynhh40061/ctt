package tw.com.ctt.server;

import static tw.com.ctt.constant.LotteryConstant.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import tw.com.ctt.dao.impl.LotteryDaoImpl;
import tw.com.ctt.model.LotteryNumBean;
import tw.com.ctt.model.LotteryTimeSetBean;
import tw.com.ctt.service.impl.LotteryServiceImpl;
import tw.com.ctt.util.CTTLotteryCalc115;
import tw.com.ctt.util.CTTLotteryCalc3d;
import tw.com.ctt.util.CTTLotteryCalcK3;
import tw.com.ctt.util.CTTLotteryCalcMark6;
import tw.com.ctt.util.CTTLotteryCalcPk10;
import tw.com.ctt.util.CTTLotteryCalcSsc;
import tw.com.ctt.util.DataSource;
import tw.com.ctt.util.MyUtil;
import tw.com.ctt.util.SettingLottery;

public class LotteryMain {

	String lotteryType = "CQSSC";
	static String dataStartTime = "2018/05/30 00:00:00";
	static String dataEndTime = "2018/05/30 00:00:00";
	int intervals = 10 * 60 * 1000;
	int maxIssue = 120;
	static long day = 24 * 60 * 60 * 1;
	static long hour = 1 * 60 * 60 * 1;
	static long minute = 1 * 60 * 1;
	static long second = 1;

	public static List<LotteryTimeSetBean> LotteryTimeSetBeanList = null;

	public static void main(String[] sWs) {

		LotteryMain lotteryMain = new LotteryMain();
		lotteryMain.contextInitialized();

		lotteryMain.settingLotteryTime();

		 LotteryRunnable r1 = new LotteryRunnable();
		 Thread t1 = new Thread(r1);
		 t1.start();

//		int id = 26;
//		String periodNum = "2018003";
//		String data = "1,2,3,4,5,6,7";
//		lotteryMain.test(id, periodNum, data);

	}

	private void settingLotteryTime() {
		LotteryServiceImpl serice = new LotteryServiceImpl();
		LotteryDaoImpl dao = new LotteryDaoImpl();
		serice.setDao(dao);

		long[] startTime = { minute * 5, hour * 10, hour * 22 + minute * 5 };
		long[] intervals = { minute * 5, minute * 10, minute * 5 };
		long[] kjTimeDelay = { second * 30, second * 30, second * 30 };
		long[] jumpOffTime = { minute * 4, minute * 9, minute * 4 };
		long[] giveUpTime = { minute * 60, minute * 60, minute * 60 };
		int[] maxIssue = { 23, 73, 24 };

		serice.saveLotteryTime(
				settingLottery(1, startTime, intervals, kjTimeDelay, giveUpTime, maxIssue, 0, jumpOffTime));
		serice.saveLotteryTime(
				settingLottery(2, hour * 9 + minute * 10, minute * 10, second * 30, minute * 60, 84, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(3, hour * 9 + minute * 42, minute * 10, second * 30, minute * 60, 72, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(4, hour * 10 + minute * 10, minute * 10, second * 30, minute * 60, 96, 0, minute * 9));

		serice.saveLotteryTime(
				settingLottery(5, hour * 0 + minute * 1, minute * 1, second * 30, minute * 10, 1440, 0, minute * 1));
		serice.saveLotteryTime(
				settingLottery(6, hour * 0 + minute * 1, minute * 1, second * 30, minute * 10, 1440, 0, minute * 1));

		serice.saveLotteryTime(
				settingLottery(7, hour * 9 + minute * 10, minute * 10, second * 30, minute * 60, 84, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(8, hour * 9 + minute * 10, minute * 10, second * 30, minute * 60, 84, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(9, hour * 9 + minute * 0, minute * 10, second * 30, minute * 60, 90, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(10, hour * 8 + minute * 35, minute * 10, second * 30, minute * 60, 87, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(11, hour * 8 + minute * 35, minute * 10, second * 30, minute * 60, 82, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(12, hour * 8 + minute * 48, minute * 10, second * 30, minute * 60, 83, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(13, hour * 9 + minute * 0, minute * 10, second * 30, minute * 60, 90, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(14, hour * 8 + minute * 40, minute * 10, second * 30, minute * 60, 81, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(15, hour * 8 + minute * 5, minute * 10, second * 30, minute * 60, 88, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(16, hour * 9 + minute * 0, minute * 10, second * 30, minute * 60, 85, 0, minute * 9));

		serice.saveLotteryTime(
				settingLottery(17, hour * 9 + minute * 10, minute * 10, second * 30, minute * 60, 78, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(18, hour * 8 + minute * 45, minute * 10, second * 30, minute * 60, 84, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(19, hour * 9 + minute * 37, minute * 10, second * 30, minute * 60, 78, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(20, hour * 8 + minute * 40, minute * 10, second * 30, minute * 60, 82, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(21, hour * 8 + minute * 30, minute * 10, second * 30, minute * 60, 87, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(22, hour * 8 + minute * 48, minute * 10, second * 30, minute * 60, 80, 0, minute * 9));
		serice.saveLotteryTime(
				settingLottery(23, hour * 9 + minute * 7, minute * 5, second * 30, minute * 60, 179, 0, minute * 4));

		serice.saveLotteryTime(
				settingLottery(24, hour * 21 + minute * 20, hour * 24, second * 30, hour * 5, 1, 0, hour * 23));
		serice.saveLotteryTime(
				settingLottery(25, hour * 20 + minute * 20, hour * 24, second * 30, hour * 5, 1, 0, hour * 23));

		long[] startLotteryTime = { hour * 22, hour * 22, hour * 22 };
		long[] kjDelayTime = { minute * 10, minute * 10, minute * 10 };
		long[] giveUpKjTime = { minute * 20, minute * 20, minute * 20 };
		long[] jumpOffTime2 = { minute * 20, minute * 20, minute * 20 };
		int[] createWeekType = { 9, 11, 13 };
		int[] createPeriodWeekType = { 2, 4, 6 };
		long[] createPeropdTime = { hour * 22, hour * 22, hour * 22 };
		int[] startBettingWeekType = { 6, 9, 11 };
		long[] startBettingTime = { hour * 22, hour * 22, hour * 22 };

		serice.saveLotteryTime(settingWeekLottery(26, 1, startLotteryTime, kjDelayTime, giveUpKjTime, jumpOffTime,
				startBettingWeekType, startBettingTime, createWeekType, createPeriodWeekType, createPeropdTime));

	}

	private void test(int id, String periodNum, String data) {

		List<LotteryNumBean> beanList = new ArrayList<LotteryNumBean>();
		LotteryNumBean bean = new LotteryNumBean();
		bean.setId(id);
		bean.setPeriodNum(Long.parseLong(periodNum));
		bean.setData(data);
		bean.setApiKjTime(new Date());
		beanList.add(bean);

		LotteryDaoImpl dao = new LotteryDaoImpl();
		LotteryServiceImpl lotteryService = new LotteryServiceImpl();
		lotteryService.setDao(dao);

		CTTLotteryCalcSsc sscCal = new CTTLotteryCalcSsc();
		CTTLotteryCalc115 x5Cal = new CTTLotteryCalc115();
		CTTLotteryCalcK3 k3Cal = new CTTLotteryCalcK3();
		CTTLotteryCalcPk10 pk10Cal = new CTTLotteryCalcPk10();
		CTTLotteryCalc3d d3Cal = new CTTLotteryCalc3d();
		CTTLotteryCalcMark6 mark6Cal = new CTTLotteryCalcMark6();
		Map<String, String> result = null;
		List<Integer> intList = new ArrayList<Integer>();
		String[] intArr = data.split(",");
		for (String num : intArr) {
			int numInt = MyUtil.toInt(num);
			intList.add(numInt);
		}
		switch (id) {
		case CQSSC:
		case TJSSC:
		case YNASSC:
		case XJSSC:
		case QQFFC:
		case TXFFC:
		case PL5:
			result = sscCal.getResult(intList);
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
			result = x5Cal.getResult(intList);
			break;
		case HBK3:
		case HLK3:
		case GXK3:
		case JSK3:
		case JLK3:
		case AHK3:
			result = k3Cal.getResult(intList);
			break;
		case BJPK10:
			result = pk10Cal.getResult(intList);
			break;
		case FC3D:
			result = d3Cal.getResult(intList);
			break;
		case XGLHC:
			if (intList.size() == 7) {
				int zodiacType = 1;
				Map<String, String> zodiacNumMap = new ConcurrentHashMap<String, String>();
				for (int k = 1; k <= 49; k++) {
					zodiacNumMap.put(k + "", zodiacType + "");
					System.out.println(k + "=>" + zodiacType);
					zodiacType--;
					if (zodiacType <= 0) {
						zodiacType = 12;
					}
				}

				mark6Cal.setAnimals(zodiacNumMap);

				result = mark6Cal.getResult(intList);

				zodiacNumMap.clear();
				zodiacNumMap = null;
			}
			break;
		}
		for (String key : result.keySet()) {
			System.out.println(key + "=>" + result.get(key).toString());
		}
		if (result != null) {

			lotteryService.updateLotteryData(beanList);
			lotteryService.addPairAward(id, Long.parseLong(periodNum), result);
		}
		result.clear();
	}

	private static List<LotteryTimeSetBean> settingLottery(int id, long[] startTime, long[] intervals,
			long[] kjTimeDelay, long[] giveUpTime, int[] maxIssue, int type, long[] jumpOffTime) {

		SettingLottery settingLottery = new SettingLottery(id);
		for (int i = 0; i < startTime.length; i++) {
			settingLottery.baseicSet(type, startTime[i], kjTimeDelay[i], intervals[i], maxIssue[i], giveUpTime[i],
					jumpOffTime[i]);
		}

		return settingLottery.getLotterySetting();
	}

	private static List<LotteryTimeSetBean> settingLottery(int id, long startTime, long intervals, long kjTimeDelay,
			long giveUpTime, int maxIssue, int type, long jumpOffTime) {
		SettingLottery settingLottery = new SettingLottery(id);
		settingLottery.baseicSet(type, startTime, kjTimeDelay, intervals, maxIssue, giveUpTime, jumpOffTime);

		return settingLottery.getLotterySetting();
	}

	private static List<LotteryTimeSetBean> settingWeekLottery(int id, int type, long[] startLotteryTime,
			long[] kjDelayTime, long[] giveUpKjTime, long[] jumpOffTime, int[] startBettingWeekType,
			long[] startBettingTime, int[] createWeekType, int[] createPeriodWeekType, long[] createPeropdTime) {

		SettingLottery settingLottery = new SettingLottery(id);
		for (int i = 0; i < startLotteryTime.length; i++) {
			settingLottery.baseicWeekSet(type, startLotteryTime[i], kjDelayTime[i], giveUpKjTime[i], jumpOffTime[i],
					startBettingWeekType[i], startBettingTime[i], createWeekType[i], createPeriodWeekType[i],
					createPeropdTime[i]);
		}

		return settingLottery.getLotterySetting();
	}

	public void contextInitialized() {
		Connection connR = null;
		Connection connW = null;
		try {
			connR = DataSource.getReadConnection();
			DataSource.returnReadConnection(connR);
			connW = DataSource.getWriteConnection();
			DataSource.returnWriteConnection(connW);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		}
	}
}
