package tw.com.ctt.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 彩票注數計算機
 * 
 * @author Quanto
 * @since 2018.05.03
 */
public class CTTLotteryCalc115 extends CTTLotteryCalc {
	
	public static final String L115Q3 = "115_q3";
	public static final String L115Q3Z = "115_q3z";
	public static final String L115Q2 = "115_q2";
	public static final String L115Q2Z = "115_q2z";
	public static final String L115Q3BDW = "115_q3bdw";
	public static final String L115DDS5 = "115_dds_5";
	public static final String L115DDS4 = "115_dds_4";
	public static final String L115DDS3 = "115_dds_3";
	public static final String L115DDS2 = "115_dds_2";
	public static final String L115DDS1 = "115_dds_1";
	public static final String L115DDS0 = "115_dds_0";
	public static final String L115CZW3 = "115_czw_3";
	public static final String L115CZW9 = "115_czw_9";
	public static final String L115CZW4 = "115_czw_4";
	public static final String L115CZW8 = "115_czw_8";
	public static final String L115CZW5 = "115_czw_5";
	public static final String L115CZW7 = "115_czw_7";
	public static final String L115CZW6 = "115_czw_6";
	public static final String L115DWD1 = "115_dwd_1";
	public static final String L115DWD2 = "115_dwd_2";
	public static final String L115DWD3 = "115_dwd_3";
	public static final String L115DWD4 = "115_dwd_4";
	public static final String L115DWD5 = "115_dwd_5";
	public static final String L115R1 = "115_r1";
	public static final String L115R2 = "115_r2";
	public static final String L115R3 = "115_r3";
	public static final String L115R4 = "115_r4";
	public static final String L115R5 = "115_r5";
	public static final String L115R6 = "115_r6";
	public static final String L115R7 = "115_r7";
	public static final String L115R8 = "115_r8";
	
	@Override
	public Map<String, String> getResult(List<Integer> input) {
		lotteryResult = input;
		Map<String, String> result = new HashMap<String, String>();
		result.put(L115Q3, "" + lotteryResult.get(0) + "|" + lotteryResult.get(1) + "|" + lotteryResult.get(2)); // 前三直選
		result.put(L115Q3Z, calL115Q3Z()); // 前三組選
		result.put(L115Q2, "" + lotteryResult.get(0) + "|" + lotteryResult.get(1)); // 前二直選
		result.put(L115Q2Z, calL115Q2Z()); // 前二組選
		result.put(L115Q3BDW, calL115Q3BDW()); // 前三不定位
		result.put(L115DDS5, calL115DDS(5)); // 定單雙_5
		result.put(L115DDS4, calL115DDS(4)); // 定單雙_4
		result.put(L115DDS3, calL115DDS(3)); // 定單雙_3
		result.put(L115DDS2, calL115DDS(2)); // 定單雙_2
		result.put(L115DDS1, calL115DDS(1)); // 定單雙_1
		result.put(L115DDS0, calL115DDS(0)); // 定單雙_0
		result.put(L115CZW3, calL115CZW(3)); // 猜中位_3
		result.put(L115CZW9, calL115CZW(9)); // 猜中位_9
		result.put(L115CZW4, calL115CZW(4)); // 猜中位_4
		result.put(L115CZW8, calL115CZW(8)); // 猜中位_8
		result.put(L115CZW5, calL115CZW(5)); // 猜中位_5
		result.put(L115CZW7, calL115CZW(7)); // 猜中位_7
		result.put(L115CZW6, calL115CZW(6)); // 猜中位_6
		result.put(L115DWD1, calL115DWD(0)); // 定位膽_1
		result.put(L115DWD2, calL115DWD(1)); // 定位膽_2
		result.put(L115DWD3, calL115DWD(2)); // 定位膽_3
		result.put(L115DWD4, calL115DWD(3)); // 定位膽_4
		result.put(L115DWD5, calL115DWD(4)); // 定位膽_5
		result.put(L115R1, calL115R()); // 任選1中1
		result.put(L115R2, calL115R()); // 任選2中2
		result.put(L115R3, calL115R()); // 任選3中3
		result.put(L115R4, calL115R()); // 任選4中4
		result.put(L115R5, calL115R()); // 任選5中5
		result.put(L115R6, calL115R()); // 任選6中5
		result.put(L115R7, calL115R()); // 任選7中5
		result.put(L115R8, calL115R()); // 任選8中5
		return result;
	}

	private String calL115Q3Z() {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		tmp.add(lotteryResult.get(0));
		tmp.add(lotteryResult.get(1));
		tmp.add(lotteryResult.get(2));
		arraySort(tmp);
		return "" + tmp.get(0) + "," + tmp.get(1) + "," + tmp.get(2);
	}

	private String calL115Q2Z() {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		tmp.add(lotteryResult.get(0));
		tmp.add(lotteryResult.get(1));
		arraySort(tmp);
		return "" + tmp.get(0) + "," + tmp.get(1);
	}

	private String calL115Q3BDW() {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		tmp.add(lotteryResult.get(0));
		tmp.add(lotteryResult.get(1));
		tmp.add(lotteryResult.get(2));
		arraySort(tmp);
		return "" + tmp.get(0) + "_" + tmp.get(1) + "_" + tmp.get(2);
	}

	private String calL115DDS(int numOfOdd) {
		int oddCount = 0;
		for (int i = 0; i < lotteryResult.size(); i++) {
			if (lotteryResult.get(i) % 2 == 1) {
				oddCount++;
			}
		}

		if (numOfOdd == oddCount) {
			return "1";
		}
		return "-";
	}

	private String calL115CZW(int numOfMid) {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		tmp.add(lotteryResult.get(0));
		tmp.add(lotteryResult.get(1));
		tmp.add(lotteryResult.get(2));
		tmp.add(lotteryResult.get(3));
		tmp.add(lotteryResult.get(4));
		arraySort(tmp);
		if (numOfMid == tmp.get(2)) {
			return "1";
		}
		return "-";
	}

	private String calL115DWD(int location) {
		return "" + lotteryResult.get(location);
	}

	private String calL115R() {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		tmp.add(lotteryResult.get(0));
		tmp.add(lotteryResult.get(1));
		tmp.add(lotteryResult.get(2));
		tmp.add(lotteryResult.get(3));
		tmp.add(lotteryResult.get(4));
		arraySort(tmp);
		return "" + tmp.get(0) + "|" + tmp.get(1) + "|" + tmp.get(2) + "|" + tmp.get(3) + "|" + tmp.get(4);
	}
}