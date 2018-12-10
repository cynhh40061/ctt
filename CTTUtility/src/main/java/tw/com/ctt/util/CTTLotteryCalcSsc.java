package tw.com.ctt.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 彩票注數計算機
 * 
 * @author Quanto
 * @since 2018.05.03
 */
public class CTTLotteryCalcSsc extends CTTLotteryCalc {
	List<Integer> paramsOfBXDS;
	List<Integer> paramsOfBXDSZ;
	List<Integer> paramsOfSpecial;
	List<Integer> tmpPosition;

	public static final String LSSCDWD1X = "sscdwd1x"; // 一星定位膽

	public static final String LSSCR2 = "sscr2"; // 任二直選
	public static final String LSSCR2HZ = "sscr2hz"; // 任二直選和值
	public static final String LSSCR2KD = "sscr2kd"; // 任二跨度
	public static final String LSSCR2Z = "sscr2z"; // 任二組選
	public static final String LSSCR2ZHZ = "sscr2zhz"; // 任二組選和值 
	public static final String LSSCR2BD = "sscr2bd"; // 任二包膽

	public static final String LSSCR3 = "sscr3"; // 任三直選
	public static final String LSSCR3HZ = "sscr3hz"; // 任三直選和值
	public static final String LSSCR3KD = "sscr3kd"; // 任三跨度
	public static final String LSSCR3ZHZ = "sscr3zhz"; // 任三組選和值 
	public static final String LSSCR3Z3 = "sscr3z3"; // 任三組三
	public static final String LSSCR3Z6 = "sscr3z6"; // 任三組六

	public static final String LSSCR3Z3DS = "sscr3z3ds"; // 任三組三單式
	public static final String LSSCR3BD = "sscr3bd"; // 任三包膽
	public static final String LSSCR3HZWS = "sscr3hzws"; // 任三和值尾數
	public static final String LSSCR3SPECIAL = "sscr3special"; // 任三特殊

	public static final String LSSCR4 = "sscr4"; // 任四直選
	public static final String LSSCR4Z24 = "sscr4z24"; // 任四組選24
	public static final String LSSCR4Z12 = "sscr4z12"; // 任四組選12
	public static final String LSSCR4Z6 = "sscr4z6"; // 任四組選6
	public static final String LSSCR4Z4 = "sscr4z4"; // 任四組選4

	public static final String LSSCR2LH_T = "sscr2lh_t"; // 任二龍虎和_和
	public static final String LSSCR2LH_LH = "sscr2lh_lh"; // 任二龍虎和_龍虎

	// B為大,X為小,D為單,S為雙
	public static final String LSSCR2BXDS = "sscr2bxds"; // 任二大小單雙
	public static final String LSSCQ3BXDS = "sscq3bxds"; // 前三大小單雙
	public static final String LSSCH3BXDS = "ssch3bxds"; // 後三大小單雙

	public static final String LSSC5X = "ssc5x"; // 五星直選
	public static final String LSSC5XZ120 = "ssc5xz120"; // 五星組選120
	public static final String LSSC5XZ60 = "ssc5xz60"; // 五星組選60
	public static final String LSSC5XZ30 = "ssc5xz30"; // 五星組選30
	public static final String LSSC5XZ20 = "ssc5xz20"; // 五星組選20
	public static final String LSSC5XZ10 = "ssc5xz10"; // 五星組選10
	public static final String LSSC5XZ5 = "ssc5xz5"; // 五星組選5

	public static final String LSSC5XBXDS = "ssc5xbxds"; // 五星大小單雙
	public static final String LSSC5XBXDSZH = "ssc5xbxdszh"; // 五星大小單雙組合

	public static final String LSSCR3BDW1M = "sscr3bdw1m"; // 任3一碼不定位
	public static final String LSSCR3BDW2M = "sscr3bdw2m"; // 任3二碼不定位
	public static final String LSSCH4BDW1M = "ssch4bdw1m"; // 後4一碼不定位
	public static final String LSSCH4BDW2M = "ssch4bdw2m"; // 後4二碼不定位
	public static final String LSSC5XBDW1M = "ssc5xbdw1m"; // 5星一碼不定位
	public static final String LSSC5XBDW2M = "ssc5xbdw2m"; // 5星二碼不定位
	public static final String LSSC5XBDW3M = "ssc5xbdw3m"; // 5星三碼不定位

	public static final String LSSC5MQW3X = "ssc5mqw3x"; // 5碼趣味三星
	public static final String LSSC4MQW3X = "ssc4mqw3x"; // 4碼趣味三星
	public static final String LSSCH3QW2X = "ssch3qw2x"; // 後3趣味二星
	public static final String LSSCQ3QW2X = "sscq3qw2x"; // 前3趣味二星

	public static final String LSSC5MQC3X = "ssc5mqc3x"; // 5碼區間三星
	public static final String LSSC4MQC3X = "ssc4mqc3x"; // 4碼區間三星
	public static final String LSSCH3QC2X = "ssch3qc2x"; // 後3區間二星
	public static final String LSSCQ3QC2X = "sscq3qc2x"; // 前3區間二星

	public static final String LSSCYFFS = "sscyffs"; // 一帆風順
	public static final String LSSCHSCS = "sschscs"; // 好事成雙
	public static final String LSSCSXBX = "sscsxbx"; // 三星報喜
	public static final String LSSCSJFC = "sscsjfc"; // 四季發財

	public CTTLotteryCalcSsc() {

		paramsOfBXDS = new ArrayList<Integer>();
		paramsOfBXDS.add(8); // b 大
		paramsOfBXDS.add(4); // x 小
		paramsOfBXDS.add(2); // d 單
		paramsOfBXDS.add(1); // s 雙

		paramsOfBXDSZ = new ArrayList<Integer>();
		paramsOfBXDSZ.add(8 + 2); // bd 大單
		paramsOfBXDSZ.add(4 + 2); // xd 小單
		paramsOfBXDSZ.add(8 + 1); // bs 大雙
		paramsOfBXDSZ.add(4 + 1); // xs 小雙

		tmpPosition = new ArrayList<Integer>();
		tmpPosition.add(16); // 萬
		tmpPosition.add(8); // 千
		tmpPosition.add(4); // 百
		tmpPosition.add(2); // 十
		tmpPosition.add(1); // 個

		paramsOfSpecial = new ArrayList<Integer>();
		paramsOfSpecial.add(16); // 豹子
		paramsOfSpecial.add(8); // 順子
		paramsOfSpecial.add(4); // 對子
		paramsOfSpecial.add(2); // 半順
		paramsOfSpecial.add(1); // 雜六
	}

	@Override
	public Map<String, String> getResult(List<Integer> input) {
		lotteryResult = input;
		Map<String, String> result = new HashMap<String, String>();
		result.putAll(calLSSCDWD1X()); // 一星定位膽

		result.putAll(calLSSCR2()); // 任二直選
		result.putAll(calLSSCR2HZ()); // 任二直選和值
		result.putAll(calLSSCR2KD()); // 任二跨度
		result.putAll(calLSSCR2Z()); // 任二組選
		result.putAll(calLSSCR2ZHZ()); // 任二組選和值
		result.putAll(calLSSCR2BD()); // 任二包膽

		result.putAll(calLSSCR3()); // 任三直選
		result.putAll(calLSSCR3HZ()); // 任三直選和值
		result.putAll(calLSSCR3KD()); // 任三跨度
		result.putAll(calLSSCR3ZHZ()); // 任三組選和值
		result.putAll(calLSSCR3Z3()); // 任三組三
		result.putAll(calLSSCR3Z6()); // 任三組六

		result.putAll(calLSSCR3Z3DS()); // 任三組三單式
		result.putAll(calLSSCR3BD()); // 任三包膽
		result.putAll(calLSSCR3HZWS()); // 任三和值尾數
		result.putAll(calLSSCR3SPECIAL()); // 任三特殊

		result.putAll(calLSSCR4()); // 任四直選
		result.putAll(calLSSCR4Z24()); // 任四組24
		result.putAll(calLSSCR4Z12()); // 任四組12
		result.putAll(calLSSCR4Z6()); // 任四組6
		result.putAll(calLSSCR4Z4()); // 任四組4

		result.putAll(calLSSCR2LHWithTie()); // 任二龍虎和_和
		result.putAll(calLSSCR2LHNoTie()); // 任二龍虎和_龍虎

		result.putAll(calLSSCR2BXDS()); // 任二大小單雙
		result.putAll(calLSSCQ3H3BXDS(true)); // 前三大小單雙
		result.putAll(calLSSCQ3H3BXDS(false)); // 後三大小單雙

		result.putAll(calLSSC5X()); // 五星直選
		result.putAll(calLSSC5XZ120()); // 五星組選120
		result.putAll(calLSSC5XZ(1, 2, LSSC5XZ60)); // 五星組選60
		result.putAll(calLSSC5XZ(2, 2, LSSC5XZ30)); // 五星組選30
		result.putAll(calLSSC5XZ(1, 3, LSSC5XZ20)); // 五星組選20
		result.putAll(calLSSC5XZ10()); // 五星組選10
		result.putAll(calLSSC5XZ(1, 4, LSSC5XZ5)); // 五星組選5

		result.putAll(calLSSC5XBXDS()); // 五星大小單雙
		result.putAll(calLSSC5XBXDSZH()); // 五星大小單雙組合

		result.putAll(calLSSCR3BDW1N()); // 任3一碼不定位
		result.putAll(calLSSCR3BDW2N()); // 任3二碼不定位
		result.putAll(calLSSCH4BDW1N()); // 後4一碼不定位
		result.putAll(calLSSCH4BDW2N()); // 後4二碼不定位
		result.putAll(calLSSC5XBDW1N()); // 5星一碼不定位
		result.putAll(calLSSC5XBDW2N()); // 5星二碼不定位
		result.putAll(calLSSC5XBDW3N()); // 5星三碼不定位

		result.putAll(calLSSC5MQW3X()); // 5碼趣味三星
		result.putAll(calLSSC4MQW3X()); // 4碼趣味三星
		result.putAll(calLSSCH3QW2X()); // 後3趣味二星
		result.putAll(calLSSCQ3QW2X()); // 前3趣味二星

		result.putAll(calLSSC5MQC3X()); // 5碼區間三星
		result.putAll(calLSSC4MQC3X()); // 4碼區間三星
		result.putAll(calLSSCH3QC2X()); // 後3區間二星
		result.putAll(calLSSCQ3QC2X()); // 前3區間二星

		result.putAll(calLSSCFunSpecial(1, LSSCYFFS)); // 一帆風順
		result.putAll(calLSSCFunSpecial(2, LSSCHSCS)); // 好事成雙
		result.putAll(calLSSCFunSpecial(3, LSSCSXBX)); // 三星報喜
		result.putAll(calLSSCFunSpecial(4, LSSCSJFC)); // 四季發財

		return result;
	}

	private Map<String, String> calLSSCR2LHWithTie() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfR2Position = getR2PositionList();
		for (int i = 0; i < paramsOfR2Position.size(); i++) {
			String tmpKey = LSSCR2LH_T + "_" + paramsOfR2Position.get(i);
			List<Integer> ll = binToIndex(paramsOfR2Position.get(i));
			result.put(tmpKey, calEachLSSCR2LH(ll, true));
		}
		return result;
	}

	private Map<String, String> calLSSCR2LHNoTie() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfR2Position = getR2PositionList();
		for (int i = 0; i < paramsOfR2Position.size(); i++) {
			String tmpKey = LSSCR2LH_LH + "_" + paramsOfR2Position.get(i);
			List<Integer> ll = binToIndex(paramsOfR2Position.get(i));
			result.put(tmpKey, calEachLSSCR2LH(ll, false));
		}
		return result;
	}

	private String calEachLSSCR2LH(List<Integer> pos, Boolean isTie) {
		if (isTie) {
			if (lotteryResult.get(pos.get(0)) == lotteryResult.get(pos.get(1))) {
				return "1";
			}
		} else {
			if (lotteryResult.get(pos.get(0)) > lotteryResult.get(pos.get(1))) {
				return "L";
			}
			if (lotteryResult.get(pos.get(0)) < lotteryResult.get(pos.get(1))) {
				return "H";
			}
		}
		return "-";
	}

	private Map<String, String> calLSSCR2KD() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfR2Position = getR2PositionList();
		for (int i = 0; i < paramsOfR2Position.size(); i++) {
			String tmpKey = LSSCR2KD + "_" + paramsOfR2Position.get(i);
			List<Integer> ll = binToIndex(paramsOfR2Position.get(i));
			result.put(tmpKey, calEachLSSCRXKD(ll));
		}
		return result;
	}

	private Map<String, String> calLSSCR3KD() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfR3Position = getR3PositionList();
		for (int i = 0; i < paramsOfR3Position.size(); i++) {
			String tmpKey = LSSCR3KD + "_" + paramsOfR3Position.get(i);
			List<Integer> ll = binToIndex(paramsOfR3Position.get(i));
			result.put(tmpKey, calEachLSSCRXKD(ll));
		}
		return result;
	}

	private String calEachLSSCRXKD(List<Integer> pos) {
		Integer diff = 0;
		List<Integer> tmp = new ArrayList<Integer>();
		for (int i = 0; i < pos.size(); i++) {
			tmp.add(lotteryResult.get(pos.get(i)));
		}
		arraySort(tmp);
		if (tmp.size() > 0) {
			diff = tmp.get(tmp.size() - 1) - tmp.get(0);
		}
		return "" + diff;
	}

	private Map<String, String> calLSSC5X() {
		Map<String, String> result = new HashMap<String, String>();
		result.put(LSSC5X, lotteryResult.get(0) + "|" + lotteryResult.get(1) + "|" + lotteryResult.get(2) + "|"
				+ lotteryResult.get(3) + "|" + lotteryResult.get(4));
		return result;
	}

	private Map<String, String> calLSSC5XZ120() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> tmp = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			tmp.add(lotteryResult.get(i));
		}
		Set<Integer> tmpSet = new TreeSet<Integer>(tmp);
		if (tmpSet.size() == tmp.size()) {
			arraySort(tmp);
			result.put(LSSC5XZ120,
					tmp.get(0) + "," + tmp.get(1) + "," + tmp.get(2) + "," + tmp.get(3) + "," + tmp.get(4));
		} else {
			result.put(LSSC5XZ120, "-");
		}
		return result;
	}

	private Map<String, String> calLSSCFunSpecial(Integer specificCount, String func) {
		Map<Integer, Integer> tmp = new HashMap<Integer, Integer>();
		for (int i = 0; i < 5; i++) {
			if (tmp.containsKey(lotteryResult.get(i))) {
				Integer nowCount = tmp.get(lotteryResult.get(i));
				tmp.replace(lotteryResult.get(i), nowCount + 1);
			} else {
				tmp.put(lotteryResult.get(i), 1);
			}
		}
		List<Integer> tmpArray = new ArrayList<Integer>();
		for (Map.Entry<Integer, Integer> entry : tmp.entrySet()) {
			if (entry.getValue() >= specificCount) {
				tmpArray.add(entry.getKey());
			}
		}
		Map<String, String> result = new HashMap<String, String>();
		String answer = "";
		for (int i = 0; i < tmpArray.size(); i++) {
			if (i != tmpArray.size() - 1) {
				answer = answer + tmpArray.get(i) + "_";
			} else {
				answer = answer + tmpArray.get(i);
			}
		}
		if ("".equals(answer)) {
			answer = "-";
		}
		result.put(func, answer);
		return result;
	}

	private Map<String, String> calLSSCQ3QC2X() {
		Map<String, String> result = new HashMap<String, String>();
		String answer = "";
		answer = answer + lotteryResult.get(0) / 2 + "|";
		answer = answer + lotteryResult.get(1) + "|" + lotteryResult.get(2);
		result.put(LSSCQ3QC2X, answer);
		return result;
	}

	private Map<String, String> calLSSCQ3QW2X() {
		Map<String, String> result = new HashMap<String, String>();
		String answer = "";
		if (lotteryResult.get(0) > 4) {
			answer = answer + "1|";
		} else {
			answer = answer + "0|";
		}
		answer = answer + lotteryResult.get(1) + "|" + lotteryResult.get(2);
		result.put(LSSCQ3QW2X, answer);
		return result;
	}

	private Map<String, String> calLSSCH3QC2X() {
		Map<String, String> result = new HashMap<String, String>();
		String answer = "";
		answer = answer + lotteryResult.get(2) / 2 + "|";
		answer = answer + lotteryResult.get(3) + "|" + lotteryResult.get(4);
		result.put(LSSCH3QC2X, answer);
		return result;
	}

	private Map<String, String> calLSSCH3QW2X() {
		Map<String, String> result = new HashMap<String, String>();
		String answer = "";
		if (lotteryResult.get(2) > 4) {
			answer = answer + "1|";
		} else {
			answer = answer + "0|";
		}
		answer = answer + lotteryResult.get(3) + "|" + lotteryResult.get(4);
		result.put(LSSCH3QW2X, answer);
		return result;
	}

	private Map<String, String> calLSSC4MQC3X() {
		Map<String, String> result = new HashMap<String, String>();
		String answer = "";
		answer = answer + lotteryResult.get(1) / 2 + "|";
		answer = answer + lotteryResult.get(2) + "|" + lotteryResult.get(3) + "|" + lotteryResult.get(4);
		result.put(LSSC4MQC3X, answer);
		return result;
	}

	private Map<String, String> calLSSC4MQW3X() {
		Map<String, String> result = new HashMap<String, String>();
		String answer = "";
		if (lotteryResult.get(1) > 4) {
			answer = answer + "1|";
		} else {
			answer = answer + "0|";
		}
		answer = answer + lotteryResult.get(2) + "|" + lotteryResult.get(3) + "|" + lotteryResult.get(4);
		result.put(LSSC4MQW3X, answer);
		return result;
	}

	private Map<String, String> calLSSC5MQC3X() {
		Map<String, String> result = new HashMap<String, String>();
		String answer = "";
		answer = answer + lotteryResult.get(0) / 2 + "|";
		answer = answer + lotteryResult.get(1) / 2 + "|";
		answer = answer + lotteryResult.get(2) + "|" + lotteryResult.get(3) + "|" + lotteryResult.get(4);
		result.put(LSSC5MQC3X, answer);
		return result;
	}

	private Map<String, String> calLSSC5MQW3X() {
		Map<String, String> result = new HashMap<String, String>();
		String answer = "";
		if (lotteryResult.get(0) > 4) {
			answer = answer + "1|";
		} else {
			answer = answer + "0|";
		}
		if (lotteryResult.get(1) > 4) {
			answer = answer + "1|";
		} else {
			answer = answer + "0|";
		}
		answer = answer + lotteryResult.get(2) + "|" + lotteryResult.get(3) + "|" + lotteryResult.get(4);
		result.put(LSSC5MQW3X, answer);
		return result;
	}

	private Map<String, String> calLSSC5XBDW1N() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOf5XPosition = get5XPositionList();
		for (int i = 0; i < paramsOf5XPosition.size(); i++) {
			String tmpKey = LSSC5XBDW1M;
			List<Integer> ll = binToIndex(paramsOf5XPosition.get(i));
			result.put(tmpKey, calEachLSSCRXBDW1N(ll));
		}
		return result;
	}

	private Map<String, String> calLSSC5XBDW2N() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOf5XPosition = get5XPositionList();
		for (int i = 0; i < paramsOf5XPosition.size(); i++) {
			String tmpKey = LSSC5XBDW2M;
			List<Integer> ll = binToIndex(paramsOf5XPosition.get(i));
			result.put(tmpKey, calEachLSSCRXBDW2N(ll));
		}
		return result;
	}

	private Map<String, String> calLSSC5XBDW3N() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOf5XPosition = get5XPositionList();
		for (int i = 0; i < paramsOf5XPosition.size(); i++) {
			String tmpKey = LSSC5XBDW3M;
			List<Integer> ll = binToIndex(paramsOf5XPosition.get(i));
			result.put(tmpKey, calEachLSSCRXBDW3N(ll));
		}
		return result;
	}

	private Map<String, String> calLSSCH4BDW2N() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfH4Position = getH4PositionList();
		for (int i = 0; i < paramsOfH4Position.size(); i++) {
			String tmpKey = LSSCH4BDW2M + "_" + paramsOfH4Position.get(i);
			List<Integer> ll = binToIndex(paramsOfH4Position.get(i));
			result.put(tmpKey, calEachLSSCRXBDW2N(ll));
		}
		return result;
	}

	private Map<String, String> calLSSCH4BDW1N() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfH4Position = getH4PositionList();
		for (int i = 0; i < paramsOfH4Position.size(); i++) {
			String tmpKey = LSSCH4BDW1M + "_" + paramsOfH4Position.get(i);
			List<Integer> ll = binToIndex(paramsOfH4Position.get(i));
			result.put(tmpKey, calEachLSSCRXBDW1N(ll));
		}
		return result;
	}

	private Map<String, String> calLSSCR3BDW2N() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfR3Position = getR3PositionList();
		for (int i = 0; i < paramsOfR3Position.size(); i++) {
			String tmpKey = LSSCR3BDW2M + "_" + paramsOfR3Position.get(i);
			List<Integer> ll = binToIndex(paramsOfR3Position.get(i));
			result.put(tmpKey, calEachLSSCRXBDW2N(ll));
		}
		return result;
	}

	private Map<String, String> calLSSCR3BDW1N() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfR3Position = getR3PositionList();
		for (int i = 0; i < paramsOfR3Position.size(); i++) {
			String tmpKey = LSSCR3BDW1M + "_" + paramsOfR3Position.get(i);
			List<Integer> ll = binToIndex(paramsOfR3Position.get(i));
			result.put(tmpKey, calEachLSSCRXBDW1N(ll));
		}
		return result;
	}

	private String calEachLSSCRXBDW1N(List<Integer> pos) {
		List<Integer> tmp = new ArrayList<Integer>();
		for (int i = 0; i < pos.size(); i++) {
			tmp.add(lotteryResult.get(pos.get(i)));
		}
		Set<Integer> tmpSet = new TreeSet<Integer>(tmp);
		Object[] tmpArray = tmpSet.toArray();
		String result = "";
		for (int i = 0; i < tmpArray.length; i++) {
			if (i != tmpArray.length - 1) {
				result = result + tmpArray[i] + "_";
			} else {
				result = result + tmpArray[i];
			}
		}
		if ("".equals(result)) {
			result = "-";
		}
		return result;
	}

	private String calEachLSSCRXBDW3N(List<Integer> pos) {
		List<String> tmp = new ArrayList<String>();
		for (int i = 0; i < pos.size(); i++) {
			for (int j = i; j < pos.size(); j++) {
				if (j != i) {
					for (int k = j; k < pos.size(); k++) {
						if (k != j) {
							Integer num1 = lotteryResult.get(pos.get(i));
							Integer num2 = lotteryResult.get(pos.get(j));
							Integer num3 = lotteryResult.get(pos.get(k));
							ArrayList<Integer> tmp2 = new ArrayList<Integer>();
							tmp2.add(num1);
							tmp2.add(num2);
							tmp2.add(num3);
							Set<Integer> tmpSet2 = new TreeSet<Integer>(tmp2);

							if (tmpSet2.size() == tmp2.size()) {
								arraySort(tmp2);
								tmp.add(tmp2.get(0) + "," + tmp2.get(1) + "," + tmp2.get(2));
							}
						}
					}
				}
			}
		}
		Set<String> tmpSet = new TreeSet<String>(tmp);
		Object[] tmpArray = tmpSet.toArray();
		String result = "";
		for (int i = 0; i < tmpArray.length; i++) {
			if (i != tmpArray.length - 1) {
				result = result + tmpArray[i] + "_";
			} else {
				result = result + tmpArray[i];
			}
		}
		if ("".equals(result)) {
			result = "-";
		}
		return result;
	}

	private String calEachLSSCRXBDW2N(List<Integer> pos) {
		List<String> tmp = new ArrayList<String>();
		for (int i = 0; i < pos.size(); i++) {
			for (int j = i; j < pos.size(); j++) {
				if (j != i) {
					Integer num1 = lotteryResult.get(pos.get(i));
					Integer num2 = lotteryResult.get(pos.get(j));
					if (num1 < num2) {
						tmp.add(num1 + "," + num2);
					} else if (num1 > num2) {
						{
							tmp.add(num2 + "," + num1);
						}
					}
				}
			}
		}
		Set<String> tmpSet = new TreeSet<String>(tmp);
		Object[] tmpArray = tmpSet.toArray();
		String result = "";
		for (int i = 0; i < tmpArray.length; i++) {
			if (i != tmpArray.length - 1) {
				result = result + tmpArray[i] + "_";
			} else {
				result = result + tmpArray[i];
			}
		}
		if ("".equals(result)) {
			result = "-";
		}
		return result;
	}

	private Map<String, String> calLSSC5XBXDSZH() {
		Map<String, String> result = new HashMap<String, String>();
		Integer sum = 0;
		for (int i = 0; i < 5; i++) {
			sum += lotteryResult.get(i);
		}
		for (int i = 0; i < paramsOfBXDSZ.size(); i++) {
			String tmpKey = LSSC5XBXDSZH + "_" + paramsOfBXDSZ.get(i);
			String ans = "-";
			Integer count = 0;
			List<Integer> ll = sumToList(paramsOfBXDSZ.get(i));
			for (int j = 0; j < ll.size(); j++) {
				if ("b".equals(numToTextBXDS(ll.get(j)))) { // b 大
					if (sum > 22) {
						count++;
					}
				} else if ("x".equals(numToTextBXDS(ll.get(j)))) { // x 小
					if (sum < 23) {
						count++;
					}
				} else if ("d".equals(numToTextBXDS(ll.get(j)))) { // d 單
					if (sum % 2 == 1) {
						count++;
					}
				} else if ("s".equals(numToTextBXDS(ll.get(j)))) { // s 雙
					if (sum % 2 == 0) {
						count++;
					}
				}
			}
			if (count == ll.size()) {
				ans = "1";
			}
			result.put(tmpKey, ans);
		}
		return result;
	}

	private Map<String, String> calLSSC5XBXDS() {
		Map<String, String> result = new HashMap<String, String>();
		Integer sum = 0;
		for (int i = 0; i < 5; i++) {
			sum += lotteryResult.get(i);
		}
		for (int i = 0; i < paramsOfBXDS.size(); i++) {
			String tmpKey = LSSC5XBXDS + "_" + paramsOfBXDS.get(i);
			String ans = "-";
			if ("b".equals(numToTextBXDS(paramsOfBXDS.get(i)))) { // b 大
				if (sum > 22) {
					ans = "1";
				}
			} else if ("x".equals(numToTextBXDS(paramsOfBXDS.get(i)))) { // x 小
				if (sum < 23) {
					ans = "1";
				}
			} else if ("d".equals(numToTextBXDS(paramsOfBXDS.get(i)))) { // d 單
				if (sum % 2 == 1) {
					ans = "1";
				}
			} else if ("s".equals(numToTextBXDS(paramsOfBXDS.get(i)))) { // s 雙
				if (sum % 2 == 0) {
					ans = "1";
				}
			}
			result.put(tmpKey, ans);
		}
		return result;
	}

	private Map<String, String> calLSSC5XZ10() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> tmp = new ArrayList<Integer>();
		Map<Integer, Integer> dupicateNum = new HashMap<Integer, Integer>();
		String ans = "-";
		for (int i = 0; i < 5; i++) {
			Integer num = lotteryResult.get(i);
			if (tmp.contains(num)) {
				if (dupicateNum.containsKey(num)) {
					dupicateNum.put(num, dupicateNum.get(num) + 1);
				} else {
					dupicateNum.put(num, 1);
				}
			}
			tmp.add(num);
		}
		Set<Integer> tmpSet = new TreeSet<Integer>(tmp);
		if (tmpSet.size() == 2) {
			if (dupicateNum.size() == 2) {
				List<Integer> nonDupicateNum = new ArrayList<Integer>();
				for (int i = 0; i < tmp.size(); i++) {
					if (!dupicateNum.containsKey(tmp.get(i))) {
						nonDupicateNum.add(tmp.get(i));
					}
				}
				if (nonDupicateNum.size() == 0) {
					arraySort(tmp);
					ans = "" + tmp.get(2) + "|";
					if (tmp.get(2) == tmp.get(0)) {
						ans = ans + tmp.get(4);
					} else {
						ans = ans + tmp.get(0);
					}
				}
			}
		}
		result.put(LSSC5XZ10, ans);
		return result;
	}

	private Map<String, String> calLSSC5XZ(Integer noOfDupicate, Integer countOfDupicate, String key) {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> tmp = new ArrayList<Integer>();
		Map<Integer, Integer> dupicateNum = new HashMap<Integer, Integer>();
		String ans = "-";
		for (int i = 0; i < 5; i++) {
			Integer num = lotteryResult.get(i);
			if (tmp.contains(num)) {
				if (tmp.contains(num)) {
					if (dupicateNum.containsKey(num)) {
						dupicateNum.put(num, dupicateNum.get(num) + 1);
					} else {
						dupicateNum.put(num, 1);
					}
				}
			}
			tmp.add(num);
		}
		Set<Integer> tmpSet = new TreeSet<Integer>(tmp);
		if (tmpSet.size() == (tmp.size() - noOfDupicate * (countOfDupicate - 1))) {
			if (dupicateNum.size() == noOfDupicate) {
				List<Integer> nonDupicateNum = new ArrayList<Integer>();
				for (int i = 0; i < tmp.size(); i++) {
					if (!dupicateNum.containsKey(tmp.get(i))) {
						nonDupicateNum.add(tmp.get(i));
					}
				}
				arraySort(nonDupicateNum);

				ans = "";
				int tmpIndex = 0;
				for (Map.Entry<Integer, Integer> entry : dupicateNum.entrySet()) {
					if (countOfDupicate - 1 != entry.getValue()) {
						ans = "-";
					}
					if (tmpIndex != dupicateNum.size() - 1) {
						ans = ans + entry.getKey() + ",";
					} else {
						ans = ans + entry.getKey();
					}
					tmpIndex++;
				}

				for (int i = 0; i < nonDupicateNum.size(); i++) {
					if (i == 0) {
						ans = ans + "|";
					}
					if (i != nonDupicateNum.size() - 1) {
						ans = ans + nonDupicateNum.get(i) + ",";
					} else {
						ans = ans + nonDupicateNum.get(i);
					}
				}
			}
		}
		result.put(key, ans);
		return result;
	}

	private Map<String, String> calLSSCQ3H3BXDS(Boolean isQ3) {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfQ3H3Position;
		if (isQ3) {
			paramsOfQ3H3Position = getQ3PositionList();
		} else {
			paramsOfQ3H3Position = getH3PositionList();
		}
		List<List<Integer>> paramsOfBXDS3Pos = getBXDS3PositionList();
		for (int i = 0; i < paramsOfQ3H3Position.size(); i++) {
			for (int j = 0; j < paramsOfBXDS3Pos.size(); j++) {
				String tmpKey = "";
				if (isQ3) {
					tmpKey = LSSCQ3BXDS + "_" + paramsOfBXDS3Pos.get(j).get(0) + "_" + paramsOfBXDS3Pos.get(j).get(1)
							+ "_" + paramsOfBXDS3Pos.get(j).get(2);
				} else {
					tmpKey = LSSCH3BXDS + "_" + paramsOfBXDS3Pos.get(j).get(0) + "_" + paramsOfBXDS3Pos.get(j).get(1)
							+ "_" + paramsOfBXDS3Pos.get(j).get(2);
				}
				List<Integer> listOfPos = binToIndex(paramsOfQ3H3Position.get(i));
				result.put(tmpKey, calEachLSSCRXBXDS(listOfPos, paramsOfBXDS3Pos.get(j)));
			}
		}
		return result;
	}

	private Map<String, String> calLSSCR2BXDS() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfR2Position = getR2PositionList();
		List<List<Integer>> paramsOfBXDS2Pos = getBXDS2PositionList();
		for (int i = 0; i < paramsOfR2Position.size(); i++) {
			for (int j = 0; j < paramsOfBXDS2Pos.size(); j++) {
				String tmpKey = LSSCR2BXDS + "_" + paramsOfR2Position.get(i) + "_" + paramsOfBXDS2Pos.get(j).get(0)
						+ "_" + paramsOfBXDS2Pos.get(j).get(1);
				List<Integer> listOfPos = binToIndex(paramsOfR2Position.get(i));
				result.put(tmpKey, calEachLSSCRXBXDS(listOfPos, paramsOfBXDS2Pos.get(j)));
			}
		}
		return result;
	}

	private String calEachLSSCRXBXDS(List<Integer> pos, List<Integer> params) {
		Integer correctCount = 0;
		if (pos.size() == params.size()) {
			for (int i = 0; i < pos.size(); i++) {
				if ("b".equals(numToTextBXDS(params.get(i)))) { // b 大
					if (lotteryResult.get(pos.get(i)) > 4) {
						correctCount++;
					}
				} else if ("x".equals(numToTextBXDS(params.get(i)))) { // x 小
					if (lotteryResult.get(pos.get(i)) < 5) {
						correctCount++;
					}
				} else if ("d".equals(numToTextBXDS(params.get(i)))) { // d 單
					if (lotteryResult.get(pos.get(i)) % 2 == 1) {
						correctCount++;
					}
				} else if ("s".equals(numToTextBXDS(params.get(i)))) { // s 雙
					if (lotteryResult.get(pos.get(i)) % 2 == 0) {
						correctCount++;
					}
				}
			}
			if (correctCount == pos.size()) {
				return "1";
			}
		}
		return "-";
	}

	private Map<String, String> calLSSCR3HZ() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfR3Position = getR3PositionList();
		for (int i = 0; i < paramsOfR3Position.size(); i++) {
			String tmpKey = LSSCR3HZ + "_" + paramsOfR3Position.get(i);
			List<Integer> ll = binToIndex(paramsOfR3Position.get(i));
			result.put(tmpKey, calEachLSSCRXHZ(ll));
		}
		return result;
	}

	private Map<String, String> calLSSCR2HZ() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfR2Position = getR2PositionList();
		for (int i = 0; i < paramsOfR2Position.size(); i++) {
			String tmpKey = LSSCR2HZ + "_" + paramsOfR2Position.get(i);
			List<Integer> ll = binToIndex(paramsOfR2Position.get(i));
			result.put(tmpKey, calEachLSSCRXHZ(ll));
		}
		return result;
	}

	private String calEachLSSCRXHZ(List<Integer> pos) {
		Integer sum = 0;
		for (int i = 0; i < pos.size(); i++) {
			sum += lotteryResult.get(pos.get(i));
		}
		return "" + sum;
	}

	private Map<String, String> calLSSCR3ZHZ() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfR3Position = getR3PositionList();
		for (int i = 0; i < paramsOfR3Position.size(); i++) {
				String tmpKey = LSSCR3ZHZ + "_" + paramsOfR3Position.get(i);
				List<Integer> ll = binToIndex(paramsOfR3Position.get(i));
				if ("-".equals(calEachLSSCR3Z3(ll))) {
					result.put(tmpKey, calEachLSSCRXZHZ(ll) + "|-");
				} else {
					result.put(tmpKey, calEachLSSCRXZHZ(ll) + "|1");
				}
		}
		return result;
	}

	private Map<String, String> calLSSCR2BD() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfR2Position = getR2PositionList();
		for (int i = 0; i < paramsOfR2Position.size(); i++) {
			String tmpKey = LSSCR2BD + "_" + paramsOfR2Position.get(i);
			List<Integer> ll = binToIndex(paramsOfR2Position.get(i));
			result.put(tmpKey, calEachLSSCRXBD(ll));
		}
		return result;
	}

	private Map<String, String> calLSSCR2ZHZ() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfR2Position = getR2PositionList();
		for (int i = 0; i < paramsOfR2Position.size(); i++) {
				String tmpKey = LSSCR2ZHZ + "_" + paramsOfR2Position.get(i);
				List<Integer> ll = binToIndex(paramsOfR2Position.get(i));
				result.put(tmpKey, calEachLSSCRXZHZ(ll));
		}
		return result;
	}

	private String calEachLSSCRXZHZ(List<Integer> pos) {
		Integer sum = 0;
		List<Integer> tmp = new ArrayList<Integer>();
		for (int i = 0; i < pos.size(); i++) {
			tmp.add(lotteryResult.get(pos.get(i)));
			sum += lotteryResult.get(pos.get(i));
		}
		Set<Integer> tmpSet = new TreeSet<Integer>(tmp);
		if (tmpSet.size() != 1) {
			return ""+sum;
		}
		return "-";
	}

	private Map<String, String> calLSSCR4() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfR4Position = getR4PositionList();
		for (int i = 0; i < paramsOfR4Position.size(); i++) {
			String tmpKey = LSSCR4 + "_" + paramsOfR4Position.get(i);
			List<Integer> ll = binToIndex(paramsOfR4Position.get(i));
			result.put(tmpKey, calEachLSSCRX(ll));
		}
		return result;
	}

	private Map<String, String> calLSSCR3() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfR3Position = getR3PositionList();
		for (int i = 0; i < paramsOfR3Position.size(); i++) {
			String tmpKey = LSSCR3 + "_" + paramsOfR3Position.get(i);
			List<Integer> ll = binToIndex(paramsOfR3Position.get(i));
			result.put(tmpKey, calEachLSSCRX(ll));
		}
		return result;
	}

	private Map<String, String> calLSSCR2() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfR2Position = getR2PositionList();
		for (int i = 0; i < paramsOfR2Position.size(); i++) {
			String tmpKey = LSSCR2 + "_" + paramsOfR2Position.get(i);
			List<Integer> ll = binToIndex(paramsOfR2Position.get(i));
			result.put(tmpKey, calEachLSSCRX(ll));
		}
		return result;
	}

	private String calEachLSSCRX(List<Integer> pos) {
		List<Integer> tmp = new ArrayList<Integer>();
		String result = "";
		for (int i = 0; i < pos.size(); i++) {
			tmp.add(lotteryResult.get(pos.get(i)));
		}
		for (int i = 0; i < tmp.size(); i++) {
			if (i != tmp.size() - 1) {
				result = result + tmp.get(i) + "|";
			} else {
				result = result + tmp.get(i);
			}
		}
		return result;
	}

	private Map<String, String> calLSSCR4Z4() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> getR4PositionList = getR4PositionList();
		for (int i = 0; i < getR4PositionList.size(); i++) {
			String tmpKey = LSSCR4Z4 + "_" + getR4PositionList.get(i);
			List<Integer> ll = binToIndex(getR4PositionList.get(i));
			result.put(tmpKey, calEachLSSCR4ZX(ll, 1, 3));
		}
		return result;
	}

	private Map<String, String> calLSSCR4Z6() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> getR4PositionList = getR4PositionList();
		for (int i = 0; i < getR4PositionList.size(); i++) {
			String tmpKey = LSSCR4Z6 + "_" + getR4PositionList.get(i);
			List<Integer> ll = binToIndex(getR4PositionList.get(i));
			result.put(tmpKey, calEachLSSCR4ZX(ll, 2, 2));
		}
		return result;
	}

	private Map<String, String> calLSSCR4Z12() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> getR4PositionList = getR4PositionList();
		for (int i = 0; i < getR4PositionList.size(); i++) {
			String tmpKey = LSSCR4Z12 + "_" + getR4PositionList.get(i);
			List<Integer> ll = binToIndex(getR4PositionList.get(i));
			result.put(tmpKey, calEachLSSCR4ZX(ll, 1, 2));
		}
		return result;
	}

	private String calEachLSSCR4ZX(List<Integer> pos, Integer noOfDupicate, Integer countOfDupicate) {
		List<Integer> tmp = new ArrayList<Integer>();
		Map<Integer, Integer> dupicateNum = new HashMap<Integer, Integer>();
		String result = "";
		for (int i = 0; i < pos.size(); i++) {
			Integer num = lotteryResult.get(pos.get(i));
			if (tmp.contains(num)) {
				if (dupicateNum.containsKey(num)) {
					dupicateNum.put(num, dupicateNum.get(num) + 1);
				} else {
					dupicateNum.put(num, 1);
				}
			}
			tmp.add(num);
		}
		Set<Integer> tmpSet = new TreeSet<Integer>(tmp);
		if (tmpSet.size() == (tmp.size() - noOfDupicate * (countOfDupicate - 1))) {
			if (dupicateNum.size() == noOfDupicate) {
				List<Integer> nonDupicateNum = new ArrayList<Integer>();
				for (int i = 0; i < tmp.size(); i++) {
					if (!dupicateNum.containsKey(tmp.get(i))) {
						nonDupicateNum.add(tmp.get(i));
					}
				}
				arraySort(nonDupicateNum);

				result = "";
				int tmpIndex = 0;
				for (Map.Entry<Integer, Integer> entry : dupicateNum.entrySet()) {
					if (countOfDupicate - 1 != entry.getValue()) {
						return "-";
					}
					if (tmpIndex != dupicateNum.size() - 1) {
						result = result + entry.getKey() + ",";
					} else {
						result = result + entry.getKey();
					}
					tmpIndex++;
				}

				for (int i = 0; i < nonDupicateNum.size(); i++) {
					if (i == 0) {
						result = result + "|";
					}
					if (i != nonDupicateNum.size() - 1) {
						result = result + nonDupicateNum.get(i) + ",";
					} else {
						result = result + nonDupicateNum.get(i);
					}
				}
				return result;
			}
		}
		return "-";
	}

	private Map<String, String> calLSSCR4Z24() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> getR4PositionList = getR4PositionList();
		for (int i = 0; i < getR4PositionList.size(); i++) {
			String tmpKey = LSSCR4Z24 + "_" + getR4PositionList.get(i);
			List<Integer> ll = binToIndex(getR4PositionList.get(i));
			result.put(tmpKey, calEachLSSCRXZ(ll));
		}
		return result;
	}

	private Map<String, String> calLSSCR3Z3() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> getR3PositionList = getR3PositionList();
		for (int i = 0; i < getR3PositionList.size(); i++) {
			String tmpKey = LSSCR3Z3 + "_" + getR3PositionList.get(i);
			List<Integer> ll = binToIndex(getR3PositionList.get(i));
			result.put(tmpKey, calEachLSSCR3Z3(ll));
		}
		return result;
	}

	private Map<String, String> calLSSCR3SPECIAL() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> getR3PositionList = getR3PositionList();
		for (int i = 0; i < getR3PositionList.size(); i++) {
			for (int j = 0; j < paramsOfSpecial.size(); j++) {
				String tmpKey = LSSCR3SPECIAL + "_" + getR3PositionList.get(i) + "_" + paramsOfSpecial.get(j);
				List<Integer> ll = binToIndex(getR3PositionList.get(i));
				result.put(tmpKey, calEachLSSCR3SPECIAL(ll, paramsOfSpecial.get(j)));
			}
		}
		return result;
	}

	private String calEachLSSCR3SPECIAL(List<Integer> pos, Integer func) {
		List<Integer> tmp = new ArrayList<Integer>();
		for (int i = 0; i < pos.size(); i++) {
			tmp.add(lotteryResult.get(pos.get(i)));
		}
		Set<Integer> tmpSet = new TreeSet<Integer>(tmp);
		arraySort(tmp);
		Integer ans = 0;
		
		if (tmpSet.size() == 1) {
			ans = ans | 16; // 豹子
		} else if (tmpSet.size() == 2) {
			ans = ans | 4; // 對子
		} else {
			if (tmpSet.size() == 3) {
				if (((tmp.get(1) - tmp.get(0)) == 1) && ((tmp.get(2) - tmp.get(1)) == 1)) {
					ans = ans | 8; // 順子
				} else if (((tmp.get(1) - tmp.get(0)) == 1) || ((tmp.get(2) - tmp.get(1)) == 1)) {
					ans = ans | 2; // 半順
				}
			}
		}
	
		if (func == 16) { // 豹子
			if ((ans & func) != 0) {
				return "1";
			}
		} else if (func == 8) { // 順子
			if ((ans & func) != 0) {
				return "1";
			}
		} else if (func == 4) { // 對子
			if ((ans & func) != 0) {
				return "1";
			}
		} else if (func == 2) { // 半順
			if ((ans & func) != 0) {
				return "1";
			}
		} else if (func == 1) { // 雜六
			if (ans == 0) {
				return "1";
			}
		}
		return "-";
	}

	private Map<String, String> calLSSCR3HZWS() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> getR3PositionList = getR3PositionList();
		for (int i = 0; i < getR3PositionList.size(); i++) {
			String tmpKey = LSSCR3HZWS + "_" + getR3PositionList.get(i);
			List<Integer> ll = binToIndex(getR3PositionList.get(i));
			result.put(tmpKey, calEachLSSCR3HZWS(ll));
		}
		return result;
	}

	private String calEachLSSCR3HZWS(List<Integer> pos) {
		Integer sum = 0;
		for (int i = 0; i < pos.size(); i++) {
			sum = sum + lotteryResult.get(pos.get(i));
		}
		sum = sum % 10;
		return "" + sum;
	}

	private Map<String, String> calLSSCR3BD() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> getR3PositionList = getR3PositionList();
		for (int i = 0; i < getR3PositionList.size(); i++) {
			String tmpKey = LSSCR3BD + "_" + getR3PositionList.get(i);
			List<Integer> ll = binToIndex(getR3PositionList.get(i));
			if ("-".equals(calEachLSSCR3Z3(ll))) {
				result.put(tmpKey, calEachLSSCRXBD(ll) + "|-");
			} else {
				result.put(tmpKey, calEachLSSCRXBD(ll) + "|1");
			}
		}
		return result;
	}

	private String calEachLSSCRXBD(List<Integer> pos) {
		List<Integer> tmp = new ArrayList<Integer>();
		for (int i = 0; i < pos.size(); i++) {
			tmp.add(lotteryResult.get(pos.get(i)));
		}
		Set<Integer> tmpSet = new TreeSet<Integer>(tmp);
		if (tmpSet.size() > 1) {
			String result = "";
			Object[] tmpArray = tmpSet.toArray();
			for (int i = 0; i < tmpArray.length; i++) {
				if (i != tmpArray.length - 1) {
					result = result + tmpArray[i] + "_";
				} else {
					result = result + tmpArray[i];
				}
			}
			return result;
		}
		return "-";
	}

	private Map<String, String> calLSSCR3Z3DS() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> getR3PositionList = getR3PositionList();
		for (int i = 0; i < getR3PositionList.size(); i++) {
			String tmpKey = LSSCR3Z3DS + "_" + getR3PositionList.get(i);
			List<Integer> ll = binToIndex(getR3PositionList.get(i));
			result.put(tmpKey, calEachLSSCR3Z3DS(ll));
		}
		return result;
	}

	private String calEachLSSCR3Z3(List<Integer> pos) {
		List<Integer> tmp = new ArrayList<Integer>();
		for (int i = 0; i < pos.size(); i++) {
			tmp.add(lotteryResult.get(pos.get(i)));
		}
		Set<Integer> tmpSet = new TreeSet<Integer>(tmp);
		if (tmpSet.size() == (tmp.size() - 1)) {
			return "" + tmpSet.toArray()[0] + "," + tmpSet.toArray()[1];
		}
		return "-";
	}

	private String calEachLSSCR3Z3DS(List<Integer> pos) {
		List<Integer> tmp = new ArrayList<Integer>();
		String result = "";
		for (int i = 0; i < pos.size(); i++) {
			tmp.add(lotteryResult.get(pos.get(i)));
		}
		Set<Integer> tmpSet = new TreeSet<Integer>(tmp);
		if (tmpSet.size() == (tmp.size() - 1)) {
			arraySort(tmp);
			if (tmp.get(0) == tmp.get(1)) {
				result = "" + tmp.get(1) + "|" + tmp.get(2);
			} else {
				result = "" + tmp.get(1) + "|" + tmp.get(0);
			}
			return result;
		}
		return "-";
	}

	private Map<String, String> calLSSCR3Z6() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> getR3PositionList = getR3PositionList();
		for (int i = 0; i < getR3PositionList.size(); i++) {
			String tmpKey = LSSCR3Z6 + "_" + getR3PositionList.get(i);
			List<Integer> ll = binToIndex(getR3PositionList.get(i));
			result.put(tmpKey, calEachLSSCR3Z6(ll));
		}
		return result;
	}

	private String calEachLSSCR3Z6(List<Integer> pos) {
		List<Integer> tmp = new ArrayList<Integer>();
		String result = "";
		for (int i = 0; i < pos.size(); i++) {
			tmp.add(lotteryResult.get(pos.get(i)));
		}
		Set<Integer> tmpSet = new TreeSet<Integer>(tmp);
		if (tmpSet.size() == tmp.size()) {
			arraySort(tmp);
			for (int i = 0; i < tmp.size(); i++) {
				if (i != tmp.size() - 1) {
					result = result + tmp.get(i) + ",";
				} else {
					result = result + tmp.get(i);
				}
			}
			return result;
		}
		return "-";
	}

	private Map<String, String> calLSSCR2Z() {
		Map<String, String> result = new HashMap<String, String>();
		List<Integer> paramsOfR2Position = getR2PositionList();
		for (int i = 0; i < paramsOfR2Position.size(); i++) {
			String tmpKey = LSSCR2Z + "_" + paramsOfR2Position.get(i);
			List<Integer> ll = binToIndex(paramsOfR2Position.get(i));
			result.put(tmpKey, calEachLSSCRXZ(ll));
		}
		return result;
	}

	private String calEachLSSCRXZ(List<Integer> pos) {
		List<Integer> tmp = new ArrayList<Integer>();
		String result = "";
		for (int i = 0; i < pos.size(); i++) {
			tmp.add(lotteryResult.get(pos.get(i)));
		}
		Set<Integer> tmpSet = new TreeSet<Integer>(tmp);
		if (tmpSet.size() == tmp.size()) {
			arraySort(tmp);
			for (int i = 0; i < tmp.size(); i++) {
				if (i != tmp.size() - 1) {
					result = result + tmp.get(i) + ",";
				} else {
					result = result + tmp.get(i);
				}
			}
			return result;
		}
		return "-";
	}

	private List<List<Integer>> getBXDS2PositionList() {
		List<List<Integer>> paramsOfBXDS2Pos = new ArrayList<List<Integer>>();
		for (int i = 0; i < paramsOfBXDS.size(); i++) {
			for (int j = 0; j < paramsOfBXDS.size(); j++) {
				List<Integer> tmp = new ArrayList<Integer>();
				tmp.add(paramsOfBXDS.get(i));
				tmp.add(paramsOfBXDS.get(j));
				paramsOfBXDS2Pos.add(tmp);
			}
		}
		return paramsOfBXDS2Pos;

	}

	private List<List<Integer>> getBXDS3PositionList() {
		List<List<Integer>> paramsOfBXDS3Pos = new ArrayList<List<Integer>>();
		for (int i = 0; i < paramsOfBXDS.size(); i++) {
			for (int j = 0; j < paramsOfBXDS.size(); j++) {
				for (int k = 0; k < paramsOfBXDS.size(); k++) {
					List<Integer> tmp = new ArrayList<Integer>();
					tmp.add(paramsOfBXDS.get(i));
					tmp.add(paramsOfBXDS.get(j));
					tmp.add(paramsOfBXDS.get(k));
					paramsOfBXDS3Pos.add(tmp);
				}
			}
		}
		return paramsOfBXDS3Pos;
	}

	private List<Integer> getR4PositionList() {
		List<Integer> paramsOfR4Position = new ArrayList<Integer>();
		for (int i = 0; i < tmpPosition.size(); i++) {
			for (int j = i; j < tmpPosition.size(); j++) {
				if (i != j) {
					for (int k = j; k < tmpPosition.size(); k++) {
						if (k != j) {
							for (int z = k; z < tmpPosition.size(); z++) {
								if (z != k) {
									paramsOfR4Position.add(tmpPosition.get(i) + tmpPosition.get(j) + tmpPosition.get(k)
											+ tmpPosition.get(z));
								}
							}
						}
					}
				}
			}
		}
		return paramsOfR4Position;
	}

	private List<Integer> getQ3PositionList() {
		List<Integer> paramsOfR3Position = new ArrayList<Integer>();
		paramsOfR3Position.add(16 + 8 + 4);
		return paramsOfR3Position;
	}

	private List<Integer> getH3PositionList() {
		List<Integer> paramsOfR3Position = new ArrayList<Integer>();
		paramsOfR3Position.add(4 + 2 + 1);
		return paramsOfR3Position;
	}

	private List<Integer> getH4PositionList() {
		List<Integer> paramsOfH4Position = new ArrayList<Integer>();
		paramsOfH4Position.add(8 + 4 + 2 + 1);
		return paramsOfH4Position;
	}

	private List<Integer> get5XPositionList() {
		List<Integer> paramsOf5XPosition = new ArrayList<Integer>();
		paramsOf5XPosition.add(16 + 8 + 4 + 2 + 1);
		return paramsOf5XPosition;
	}

	private List<Integer> getR3PositionList() {
		List<Integer> paramsOfR3Position = new ArrayList<Integer>();
		for (int i = 0; i < tmpPosition.size(); i++) {
			for (int j = i; j < tmpPosition.size(); j++) {
				if (i != j) {
					for (int k = j; k < tmpPosition.size(); k++) {
						if (k != j) {
							paramsOfR3Position.add(tmpPosition.get(i) + tmpPosition.get(j) + tmpPosition.get(k));
						}
					}
				}
			}
		}
		return paramsOfR3Position;
	}

	private List<Integer> getR2PositionList() {
		List<Integer> paramsOfR2Position = new ArrayList<Integer>();
		for (int i = 0; i < tmpPosition.size(); i++) {
			for (int j = i; j < tmpPosition.size(); j++) {
				if (i != j) {
					paramsOfR2Position.add(tmpPosition.get(i) + tmpPosition.get(j));
				}
			}
		}
		return paramsOfR2Position;
	}

	private Map<String, String> calLSSCDWD1X() {
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < tmpPosition.size(); i++) {
			String tmpKey = LSSCDWD1X + "_" + tmpPosition.get(i);
			List<Integer> tmpPos = binToIndex(tmpPosition.get(i));
			if (tmpPos.size() == 1) {
				result.put(tmpKey, calEachLSSCDWD1X(binToIndex(tmpPosition.get(i)).get(0)));
			}
		}
		return result;
	}

	private String calEachLSSCDWD1X(int pos) {
		return "" + lotteryResult.get(pos);
	}

	private List<Integer> binToIndex(int binIndex) {
		List<Integer> indexList = new ArrayList<Integer>();
		if ((binIndex & 16) != 0) {
			indexList.add(0);
		}
		if ((binIndex & 8) != 0) {
			indexList.add(1);
		}
		if ((binIndex & 4) != 0) {
			indexList.add(2);
		}
		if ((binIndex & 2) != 0) {
			indexList.add(3);
		}
		if ((binIndex & 1) != 0) {
			indexList.add(4);
		}
		return indexList;
	}

	private List<Integer> sumToList(int sum) {
		List<Integer> indexList = new ArrayList<Integer>();
		if ((sum & 16) != 0) {
			indexList.add(16);
		}
		if ((sum & 8) != 0) {
			indexList.add(8);
		}
		if ((sum & 4) != 0) {
			indexList.add(4);
		}
		if ((sum & 2) != 0) {
			indexList.add(2);
		}
		if ((sum & 1) != 0) {
			indexList.add(1);
		}
		return indexList;
	}

	private String numToTextBXDS(int num) {
		if (num == 8) {
			return "b";
		}
		if (num == 4) {
			return "x";
		}
		if (num == 2) {
			return "d";
		}
		if (num == 1) {
			return "s";
		}
		return "";
	}

}