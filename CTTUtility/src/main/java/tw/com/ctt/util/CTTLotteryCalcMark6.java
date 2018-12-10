package tw.com.ctt.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

/**
 * 彩票注數計算機
 * 
 * @author Quanto
 * @since 2018.05.03
 */
public class CTTLotteryCalcMark6 extends CTTLotteryCalc {
	Map<String, String> animalSetting;
	Map<String, List<List<String>>> paramsOfAnimals;
	Map<String, List<List<String>>> paramsOfWs;
	Map<String, Set<String>> numsOfColor;
	Set<Integer> lotteryResultSet;
	Set<Integer> lotteryWsResultSet;
	Set<Integer> lotteryAnimalResultSet;
	public static final String LMARK6ZM = "mark6zm"; // 正碼

	public static final String LMARK6TM = "mark6tm"; // 特碼

	public static final String LMARK6ZMT = "mark6zmt"; // 正碼特

	// B為大,X為小,D為單,S為雙
	public static final String LMARK6ZMBX = "mark6zmbx"; // 正碼大小
	public static final String LMARK6ZMDS = "mark6zmds"; // 正碼單雙
	public static final String LMARK6ZMRGB = "mark6zmrgb"; // 正碼紅綠藍波
	public static final String LMARK6ZMHDS = "mark6zmhds"; // 正碼合單雙

	public static final String LMARK6LM3C3 = "mark6lm3c3"; // 連碼三中三
	public static final String LMARK6LM3C2 = "mark6lm3c2"; // 連碼三中二
	public static final String LMARK6LM2C2 = "mark6lm2c2"; // 連碼二中二
	public static final String LMARK6LM2CT = "mark6lm2ct"; // 連碼二中特
	public static final String LMARK6LMTC = "mark6lmtc"; // 連碼特串

	public static final String LMARK6BB = "mark6bb"; // 半波

	public static final String LMARK6ANIMALTM = "mark6animaltm"; // 特碼生肖
	public static final String LMARK6ANIMAL1 = "mark6animal1"; // 一肖
	public static final String LMARK6ANIMAL2 = "mark6animal2"; // 二肖連中
	public static final String LMARK6ANIMAL3 = "mark6animal3"; // 三肖連中
	public static final String LMARK6ANIMAL4 = "mark6animal4"; // 四肖連中
	public static final String LMARK6ANIMAL5 = "mark6animal5"; // 五肖連中

	public static final String LMARK6WS1 = "mark6ws"; // 尾數
	public static final String LMARK6WS2 = "mark6ws2"; // 二尾連中
	public static final String LMARK6WS3 = "mark6ws3"; // 三尾連中
	public static final String LMARK6WS4 = "mark6ws4"; // 四尾連中

	public static final String LMARK6BC = "mark6bc"; // 不中

	public CTTLotteryCalcMark6() {
		paramsOfWs = new HashMap<String, List<List<String>>>();
		paramsOfAnimals = new HashMap<String, List<List<String>>>();
		lotteryResultSet = new HashSet<Integer>();
		lotteryWsResultSet = new HashSet<Integer>();
		lotteryAnimalResultSet = new HashSet<Integer>();
		numsOfColor = new HashMap<String, Set<String>>();

		Set<String> nums = new HashSet<String>();
		nums.add("1");
		nums.add("2");
		nums.add("7");
		nums.add("8");
		nums.add("12");
		nums.add("13");
		nums.add("18");
		nums.add("19");
		nums.add("23");
		nums.add("24");
		nums.add("29");
		nums.add("30");
		nums.add("34");
		nums.add("35");
		nums.add("40");
		nums.add("45");
		nums.add("46");
		numsOfColor.put("r", nums);
		nums = new HashSet<String>();
		nums.add("3");
		nums.add("4");
		nums.add("9");
		nums.add("10");
		nums.add("14");
		nums.add("15");
		nums.add("20");
		nums.add("25");
		nums.add("26");
		nums.add("31");
		nums.add("36");
		nums.add("37");
		nums.add("41");
		nums.add("42");
		nums.add("47");
		nums.add("48");
		numsOfColor.put("b", nums);
		nums = new HashSet<String>();
		nums.add("5");
		nums.add("6");
		nums.add("11");
		nums.add("16");
		nums.add("17");
		nums.add("21");
		nums.add("22");
		nums.add("27");
		nums.add("28");
		nums.add("32");
		nums.add("33");
		nums.add("38");
		nums.add("39");
		nums.add("43");
		nums.add("44");
		nums.add("49");
		numsOfColor.put("g", nums);
		List<String> allAnimal = new ArrayList<String>();
		allAnimal.add("1");
		allAnimal.add("2");
		allAnimal.add("3");
		allAnimal.add("4");
		allAnimal.add("5");
		allAnimal.add("6");
		allAnimal.add("7");
		allAnimal.add("8");
		allAnimal.add("9");
		allAnimal.add("10");
		allAnimal.add("11");
		allAnimal.add("12");

		List<String> allWs = new ArrayList<String>();
		allWs.add("0");
		allWs.add("1");
		allWs.add("2");
		allWs.add("3");
		allWs.add("4");
		allWs.add("5");
		allWs.add("6");
		allWs.add("7");
		allWs.add("8");
		allWs.add("9");

		paramsOfAnimals.put(LMARK6ANIMAL2, combine(allAnimal, 2));
		paramsOfAnimals.put(LMARK6ANIMAL3, combine(allAnimal, 3));
		paramsOfAnimals.put(LMARK6ANIMAL4, combine(allAnimal, 4));
		paramsOfAnimals.put(LMARK6ANIMAL5, combine(allAnimal, 5));
		paramsOfWs.put(LMARK6WS2, combine(allWs, 2));
		paramsOfWs.put(LMARK6WS3, combine(allWs, 3));
		paramsOfWs.put(LMARK6WS4, combine(allWs, 4));

	}

	public static void main(String[] argvs) {
		CTTLotteryCalcMark6 cal = new CTTLotteryCalcMark6();
		Map<String, String> animal = new HashMap<String, String>();
		animal.put("1", "11");
		animal.put("2", "10");
		animal.put("3", "9");
		animal.put("4", "8");
		animal.put("5", "7");
		animal.put("6", "6");
		animal.put("7", "5");
		animal.put("8", "4");
		animal.put("9", "3");
		animal.put("10", "2");
		animal.put("11", "1");
		animal.put("12", "12");
		animal.put("13", "11");
		animal.put("14", "10");
		animal.put("15", "9");
		animal.put("16", "8");
		animal.put("17", "7");
		animal.put("18", "6");
		animal.put("19", "5");
		animal.put("20", "4");
		animal.put("21", "3");
		animal.put("22", "2");
		animal.put("23", "1");
		animal.put("24", "12");
		animal.put("25", "11");
		animal.put("26", "10");
		animal.put("27", "9");
		animal.put("28", "8");
		animal.put("29", "7");
		animal.put("30", "6");
		animal.put("31", "5");
		animal.put("32", "4");
		animal.put("33", "3");
		animal.put("34", "2");
		animal.put("35", "1");
		animal.put("36", "12");
		animal.put("37", "11");
		animal.put("38", "10");
		animal.put("39", "9");
		animal.put("40", "8");
		animal.put("41", "7");
		animal.put("42", "6");
		animal.put("43", "5");
		animal.put("44", "4");
		animal.put("45", "3");
		animal.put("46", "2");
		animal.put("47", "1");
		animal.put("48", "12");
		animal.put("49", "11");
		
		cal.setAnimals(animal);
		
		List<Integer> nums = new ArrayList<Integer>();
		nums.add(1);
		nums.add(2);
		nums.add(3);
		nums.add(4);
		nums.add(5);
		nums.add(6);
		nums.add(7);
		JSONObject jj = new JSONObject(cal.getResult(nums));
		System.out.println(jj.toString());
	}

	public static void subCombine(List<List<String>> r, List<String> t, List<String> a, int n) {
		if (n == 0) {
			r.add(new ArrayList<String>(t));
		} else {
			for (int i = 0, l = a.size(); i <= l - n; i++) {
				List<String> subT = new ArrayList<String>(t);
				subT.add(a.get(i));
				subCombine(r, subT, new ArrayList<String>(a.subList(i + 1, a.size())), n - 1);
			}
		}
	}

	public static List<List<String>> combine(List<String> arr, int num) {
		List<List<String>> r = new ArrayList<List<String>>();
		List<String> tmp = new ArrayList<String>();
		subCombine(r, tmp, arr, num);
		return r;
	}

	@Override
	public Map<String, String> getResult(List<Integer> input) {
		lotteryResult = input;
		for (int i = 0; i < lotteryResult.size(); i++) {
			lotteryResultSet.add(lotteryResult.get(i));
			
			lotteryWsResultSet.add(lotteryResult.get(i)%10);
			
			lotteryAnimalResultSet.add((Integer.parseInt(animalSetting.get(""+ lotteryResult.get(i)))));
		}
		Map<String, String> result = new HashMap<String, String>();

		result.putAll(calLMARK6ZM()); // 正碼
		result.putAll(calLMARK6TM()); // 特碼
		result.putAll(calLMARK6ZMT()); // 正碼特
		result.putAll(calLMARK6ZMBX()); // 正碼大小
		result.putAll(calLMARK6ZMDS()); // 正碼單雙
		result.putAll(calLMARK6ZMRGB()); // 正碼紅綠藍波
		result.putAll(calLMARK6ZMHDS()); // 正碼合單雙
		result.putAll(calLMARK6LM3C3()); // 連碼三中三
		result.putAll(calLMARK6LM3C2()); // 連碼三中二
		result.putAll(calLMARK6LM2C2()); // 連碼二中二
		result.putAll(calLMARK6LM2CT()); // 連碼二中特
		result.putAll(calLMARK6LMTC()); // 連碼特串
		result.putAll(calLMARK6BB()); // 半波
		result.putAll(calLMARK6ANIMALTM()); // 特碼生肖
		result.putAll(calLMARK6ANIMAL1()); // 一肖
		result.putAll(calLMARK6ANIMAL_MULTI(LMARK6ANIMAL2)); // 二肖連中
		result.putAll(calLMARK6ANIMAL_MULTI(LMARK6ANIMAL3)); // 三肖連中
		result.putAll(calLMARK6ANIMAL_MULTI(LMARK6ANIMAL4)); // 四肖連中
		result.putAll(calLMARK6ANIMAL_MULTI(LMARK6ANIMAL5)); // 五肖連中
		
		result.putAll(calLMARK6WS1()); // 尾數
		result.putAll(calLMARK6WS_MULTI(LMARK6WS2)); // 二尾連中
		result.putAll(calLMARK6WS_MULTI(LMARK6WS3)); // 三尾連中
		result.putAll(calLMARK6WS_MULTI(LMARK6WS4)); // 四尾連中
		
		result.putAll(calLMARK6BC()); // 不中
		
		return result;
	}

	public void setAnimals(Map<String, String> input) {
		animalSetting = input;
	}

	private Map<String, String> calLMARK6ZM() {
		Map<String, String> result = new HashMap<String, String>();
		int specialNum = lotteryResult.get(6);
		for (int i = 1; i <= 49; i++) {
			String tmpKey = LMARK6ZM + "_" + i;
			
			if (lotteryResultSet.contains(i) && specialNum != i) {
				result.put(tmpKey, "1");
			} else {
				result.put(tmpKey, "0");
			}
		}
		return result;
	}

	private Map<String, String> calLMARK6TM() {
		Map<String, String> result = new HashMap<String, String>();
		String tmpKey = LMARK6TM;
		result.put(tmpKey, "" + lotteryResult.get(6));
		return result;
	}

	private Map<String, String> calLMARK6ZMT() {
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < 6; i++) {
			String tmpKey = LMARK6ZMT + (i + 1);
			result.put(tmpKey, "" + lotteryResult.get(i));
		}
		return result;
	}

	private Map<String, String> calLMARK6ZMBX() {
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < 6; i++) {
			String tmpKeyB = LMARK6ZMBX + (i + 1) + "_b";
			String tmpKeyX = LMARK6ZMBX + (i + 1) + "_x";
			if (lotteryResult.get(i) >= 25) {
				result.put(tmpKeyB, "1");
				result.put(tmpKeyX, "0");
			} else {
				result.put(tmpKeyB, "0");
				result.put(tmpKeyX, "1");
			}
		}
		return result;
	}

	private Map<String, String> calLMARK6ZMDS() {
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < 6; i++) {
			String tmpKeyD = LMARK6ZMDS + (i + 1) + "_d";
			String tmpKeyS = LMARK6ZMDS + (i + 1) + "_s";
			if (lotteryResult.get(i) % 2 == 1) {
				result.put(tmpKeyD, "1");
				result.put(tmpKeyS, "0");
			} else {
				result.put(tmpKeyD, "0");
				result.put(tmpKeyS, "1");
			}
		}
		return result;
	}

	private Map<String, String> calLMARK6ZMRGB() {
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < 6; i++) {
			String tmpKeyR = LMARK6ZMRGB + (i + 1) + "_r";
			String tmpKeyG = LMARK6ZMRGB + (i + 1) + "_g";
			String tmpKeyB = LMARK6ZMRGB + (i + 1) + "_b";
			if (numsOfColor.get("r").contains("" + lotteryResult.get(i))) {
				result.put(tmpKeyR, "1");
				result.put(tmpKeyG, "0");
				result.put(tmpKeyB, "0");
			} else if (numsOfColor.get("g").contains("" + lotteryResult.get(i))) {
				result.put(tmpKeyR, "0");
				result.put(tmpKeyG, "1");
				result.put(tmpKeyB, "0");
			} else {
				result.put(tmpKeyR, "0");
				result.put(tmpKeyG, "0");
				result.put(tmpKeyB, "1");
			}
		}
		return result;
	}

	private Map<String, String> calLMARK6ZMHDS() {
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < 6; i++) {
			String tmpKeyD = LMARK6ZMHDS + (i + 1) + "_d";
			String tmpKeyS = LMARK6ZMHDS + (i + 1) + "_s";
			int num_1 = lotteryResult.get(i) / 10;
			int num_2 = lotteryResult.get(i) % 10;
			int sum = num_1 + num_2;
			if (sum % 2 == 1) {
				result.put(tmpKeyD, "1");
				result.put(tmpKeyS, "0");
			} else {
				result.put(tmpKeyD, "0");
				result.put(tmpKeyS, "1");
			}
		}
		return result;
	}

	private Map<String, String> calLMARK6LM3C3() {
		Map<String, String> result = new HashMap<String, String>();
		result.put(LMARK6LM3C3, "" + lotteryResult.get(0) + "," + lotteryResult.get(1) + "," + lotteryResult.get(2)
				+ "," + lotteryResult.get(3) + "," + lotteryResult.get(4) + "," + lotteryResult.get(5));
		return result;
	}

	private Map<String, String> calLMARK6LM3C2() {
		Map<String, String> result = new HashMap<String, String>();
		result.put(LMARK6LM3C2, "" + lotteryResult.get(0) + "," + lotteryResult.get(1) + "," + lotteryResult.get(2)
				+ "," + lotteryResult.get(3) + "," + lotteryResult.get(4) + "," + lotteryResult.get(5));
		return result;
	}

	private Map<String, String> calLMARK6LM2C2() {
		Map<String, String> result = new HashMap<String, String>();
		result.put(LMARK6LM2C2, "" + lotteryResult.get(0) + "," + lotteryResult.get(1) + "," + lotteryResult.get(2)
				+ "," + lotteryResult.get(3) + "," + lotteryResult.get(4) + "," + lotteryResult.get(5));
		return result;
	}

	private Map<String, String> calLMARK6LM2CT() {
		Map<String, String> result = new HashMap<String, String>();
		result.put(LMARK6LM2CT,
				"" + lotteryResult.get(0) + "," + lotteryResult.get(1) + "," + lotteryResult.get(2) + ","
						+ lotteryResult.get(3) + "," + lotteryResult.get(4) + "," + lotteryResult.get(5) + "|"
						+ lotteryResult.get(6));
		return result;
	}

	private Map<String, String> calLMARK6LMTC() {
		Map<String, String> result = new HashMap<String, String>();
		result.put(LMARK6LMTC,
				"" + lotteryResult.get(0) + "," + lotteryResult.get(1) + "," + lotteryResult.get(2) + ","
						+ lotteryResult.get(3) + "," + lotteryResult.get(4) + "," + lotteryResult.get(5) + "|"
						+ lotteryResult.get(6));
		return result;
	}

	private Map<String, String> calLMARK6BB() {
		Map<String, String> result = new HashMap<String, String>();
		String tmpKeyR = LMARK6BB + "_r";
		String tmpKeyG = LMARK6BB + "_g";
		String tmpKeyB = LMARK6BB + "_b";
		
		result.put(tmpKeyR + "b", "0");
		result.put(tmpKeyG + "b", "0");
		result.put(tmpKeyB + "b", "0");
		result.put(tmpKeyR + "x", "0");
		result.put(tmpKeyG + "x", "0");
		result.put(tmpKeyB + "x", "0");
		result.put(tmpKeyR + "d", "0");
		result.put(tmpKeyG + "d", "0");
		result.put(tmpKeyB + "d", "0");
		result.put(tmpKeyR + "s", "0");
		result.put(tmpKeyG + "s", "0");
		result.put(tmpKeyB + "s", "0");
		
		result.put(tmpKeyR + "hd", "0");
		result.put(tmpKeyG + "hd", "0");
		result.put(tmpKeyB + "hd", "0");
		result.put(tmpKeyR + "hs", "0");
		result.put(tmpKeyG + "hs", "0");
		result.put(tmpKeyB + "hs", "0");

		
		int n1 = lotteryResult.get(6)/10;
		int n2 = (int) Math.floor(lotteryResult.get(6)%10);
		int num = n1+n2;

		if (numsOfColor.get("r").contains("" + lotteryResult.get(6))) {
			if (lotteryResult.get(6) >= 25) {
				result.put(tmpKeyR + "b", "1");
			} else {
				result.put(tmpKeyR + "x", "1");
			}
			if (lotteryResult.get(6) % 2 == 1) {
				result.put(tmpKeyR + "d", "1");
			} else {
				result.put(tmpKeyR + "s", "1");
			}
			if(num % 2 == 1) {
				result.put(tmpKeyR + "hd", "1");
			}
			else {
				result.put(tmpKeyR + "hs", "1");
			}
		} else if (numsOfColor.get("g").contains("" + lotteryResult.get(6))) {
			if (lotteryResult.get(6) >= 25) {
				result.put(tmpKeyG + "b", "1");
			} else {
				result.put(tmpKeyG + "x", "1");
			}
			if (lotteryResult.get(6) % 2 == 1) {
				result.put(tmpKeyG + "d", "1");
			} else {
				result.put(tmpKeyG + "s", "1");
			}
			if(num % 2 == 1) {
				result.put(tmpKeyG + "hd", "1");
			}
			else {
				result.put(tmpKeyG + "hs", "1");
			}
		} else {
			if (lotteryResult.get(6) >= 25) {
				result.put(tmpKeyB + "b", "1");
			} else {
				result.put(tmpKeyB + "x", "1");
			}
			if (lotteryResult.get(6) % 2 == 1) {
				result.put(tmpKeyB + "d", "1");
			} else {
				result.put(tmpKeyB + "s", "1");
			}
			if(num % 2 == 1) {
				result.put(tmpKeyB + "hd", "1");
			}
			else {
				result.put(tmpKeyB + "hs", "1");
			}
		}
		return result;
	}

	private Map<String, String> calLMARK6ANIMALTM() {
		Map<String, String> result = new HashMap<String, String>();
		result.put(LMARK6ANIMALTM, animalSetting.get("" + lotteryResult.get(6)));
		return result;
	}

	private Map<String, String> calLMARK6ANIMAL1() {
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < 12; i++) {
			result.put(LMARK6ANIMAL1 + "_" + (i + 1), "0");
		}
		for (int i = 0; i < 7; i++) {
			result.put(LMARK6ANIMAL1 + "_"+animalSetting.get("" + lotteryResult.get(i)), "1");
		}
		return result;
	}

	private Map<String, String> calLMARK6ANIMAL_MULTI(String Key) {
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < paramsOfAnimals.get(Key).size(); i++) {
			String tmpStr = "";
			boolean ans = true;
			for (int j = 0; j < paramsOfAnimals.get(Key).get(i).size(); j++) {
				if (!lotteryAnimalResultSet.contains(Integer.parseInt(paramsOfAnimals.get(Key).get(i).get(j)))) {
					ans &= false;
				}
				if (j < paramsOfAnimals.get(Key).get(i).size() - 1) {
					tmpStr += paramsOfAnimals.get(Key).get(i).get(j) + "_";
				} else {
					tmpStr += paramsOfAnimals.get(Key).get(i).get(j);
				}
			}
			if (ans) {
				result.put(Key + "_" + tmpStr, "1");
			} else {
				result.put(Key + "_" + tmpStr, "0");
			}
		}
		return result;
	}
	
	private Map<String, String> calLMARK6WS1() {
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < 10; i++) {
			result.put(LMARK6WS1 + "_" + i, "0");
			
		}
		for (int i = 0; i < 7; i++) {
			result.put(LMARK6WS1 + "_"+lotteryResult.get(i)%10, "1");
		}
		return result;
	}
	//paramsOfWs.put(LMARK6WS2
	private Map<String, String> calLMARK6WS_MULTI(String Key) {
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < paramsOfWs.get(Key).size(); i++) {
			String tmpStr = "";
			boolean ans = true;
			for (int j = 0; j < paramsOfWs.get(Key).get(i).size(); j++) {
				if (!lotteryWsResultSet.contains(Integer.parseInt(paramsOfWs.get(Key).get(i).get(j)))) {
					ans &= false;
				}
				if (j < paramsOfWs.get(Key).get(i).size() - 1) {
					tmpStr += paramsOfWs.get(Key).get(i).get(j) + "_";
				} else {
					tmpStr += paramsOfWs.get(Key).get(i).get(j);
				}
			}
			if (ans) {
				result.put(Key + "_" + tmpStr, "1");
			} else {
				result.put(Key + "_" + tmpStr, "0");
			}
		}
		return result;
	}
	
	private Map<String, String> calLMARK6BC() {
		Map<String, String> result = new HashMap<String, String>();
		String tmpStr = "";
		for(int i = 0; i < lotteryResult.size(); i++) {
			if( i < lotteryResult.size() -1 ) {
				tmpStr += lotteryResult.get(i)+",";
			}else{
				tmpStr += lotteryResult.get(i);
			}
			
		}
		result.put(LMARK6BC, tmpStr);
		return result;
	}

	
}