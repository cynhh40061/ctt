package tw.com.ctt.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 彩票注數計算機
 * 
 * @author Quanto
 * @since 2018.04.24
 */
public class CTTBetCountCalc {
	public static String invokeCalc(Object calc, String func, String bet) {
		try {
			Class<?> c = calc.getClass();
			Method m = c.getMethod(func, String.class);
			Object result = m.invoke(calc, bet.trim());
			return "" + result;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return "";
	}

	// 直选复式
	// 大小单双
	public static Integer fs(String bet) {
		String[] bets = bet.split(",");
		int ret = 1;
		for (int i = 0; i < bets.length; i++) {
			ret = ret * bets[i].length();
		}
		return ret;
	}

	// 直选单式
	// 二星直选组选单式
	public static Integer ds(String bet) {
		return bet.split("\\|").length;
	}

	public static int[] deleteSameNumber(String[] strAry) {
		int[] ary = new int[strAry.length];
		int[] temp = new int[strAry.length];

		for (int i = 0; i < strAry.length; i++) {
			ary[i] = Integer.parseInt(strAry[i]);
		}

		Arrays.sort(ary);
		int j = 0;
		for (int i = 0; i < ary.length; i++) {
			if (i != 0 && ary[i] == temp[j - 1]) {
				j--;
			}
			temp[j] = ary[i];
			j++;
		}
		int[] temp2 = new int[j];
		for (int k = 0; k < j; k++) {
			temp2[k] = temp[k];
		}
		return temp2;
	}

	// 五星复式
	public static Integer ssc5xfs(String bet) {
		String[] bets = bet.split(",");
		if (bets.length != 5) {
			return 0;
		}
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		int ret = 1;
		for (int i = 0; i < bets.length; i++) {
			String[] codes = bets[i].split("");
			int[] x = deleteSameNumber(codes);
			if ((codes.length != x.length) || (codes.length > 10) || (codes.length < 1)) {
				return 0;
			}
			for (int j = 0; j < codes.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(codes[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
			ret *= codes.length;
		}
		return ret;
	}

	// 五星单式
	public static Integer ssc5xds(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] a = bet.split("\\|");

		for (int i = 0; i < a.length; i++) {
			String[] c = a[i].split(",");
			if (c.length != 5) {
				return 0;
			}
			for (int j = 0; j < c.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(c[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
		}
		return a.length;
	}

	// 前/后四复式
	public static Integer sscqh4xfs(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(",");
		if (bets.length != 4) {
			return 0;
		}
		int ret = 1;
		for (int i = 0; i < bets.length; i++) {
			String[] codes = bets[i].split("");
			int[] x = deleteSameNumber(codes);
			if ((codes.length != x.length) || (codes.length > 10) || (codes.length < 1)) {
				return 0;
			}
			for (int j = 0; j < codes.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(codes[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
			ret *= codes.length;
		}
		return ret;
	}

	// 前/后四单式
	public static Integer qh4ds(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] a = bet.split("\\|");
		for (int i = 0; i < a.length; i++) {
			String[] c = a[i].split(",");
			if (c.length != 4) {
				return 0;
			}
			for (int j = 0; j < c.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(c[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
		}
		return a.length;
	}

	// 前/中/后三复式
	public static Integer sscqzh3xfs(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(",");
		if (bets.length != 3) {
			return 0;
		}
		int ret = 1;
		for (int i = 0; i < bets.length; i++) {
			String[] codes = bets[i].split("");
			int[] x = deleteSameNumber(codes);
			if ((codes.length != x.length) || (codes.length > 10) || (codes.length < 1)) {
				return 0;
			}
			for (int j = 0; j < codes.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(codes[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
			ret *= codes.length;
		}
		return ret;
	}

	// 前/中/后三单式
	public static Integer qzh3ds(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] a = bet.split("\\|");
		for (int i = 0; i < a.length; i++) {
			String[] c = a[i].split(",");
			if (c.length != 3) {
				return 0;
			}
			for (int j = 0; j < c.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(c[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
		}
		return a.length;
	}

	// 前/后二复式
	public static Integer sscqh2xfs(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(",");
		if (bets.length != 2) {
			return 0;
		}
		int ret = 1;
		for (int i = 0; i < bets.length; i++) {
			String[] codes = bets[i].split("");
			int[] x = deleteSameNumber(codes);
			if ((codes.length != x.length) || (codes.length > 10) || (codes.length < 1)) {
				return 0;
			}
			for (int j = 0; j < codes.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(codes[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
			ret *= codes.length;
		}
		return ret;
	}

	// 前/后二单式
	public static Integer qh2ds(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] a = bet.split("\\|");
		for (int i = 0; i < a.length; i++) {
			String[] c = a[i].split(",");
			if (c.length != 2) {
				return 0;
			}
			for (int j = 0; j < c.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(c[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
		}
		return a.length;
	}

	// 前/后二直选和值
	public static Integer sscqh2zhixhz(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
				"17", "18" };
		String[] bets = bet.split(",");
		int[] x = deleteSameNumber(bets);
		int bnum = 0;
		if (bets.length < 1 || bets.length > 19 || bets.length != x.length) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		for (int i = 0; i < bets.length; i++) {
			for (int j = 0; j < 10; j++) {
				for (int c = 0; c < 10; c++) {
					if (j + c - Integer.parseInt(bets[i]) == 0) {
						bnum++;
					}
				}
			}
		}
		return bnum;
	}

	// 前/后二组选和值
	public static Integer sscqh2zhuxhz(String bet) {
		String[] check = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
				"17" };
		String[] bets = bet.split(",");
		int[] x = deleteSameNumber(bets);
		int bnum = 0;
		if (bets.length < 1 || bets.length > 18 || bets.length != x.length) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		for (int i = 0; i < bets.length; i++) {
			for (int j = 0; j < 10; j++) {
				for (int c = j; c < 10; c++) {
					if (j - c != 0) {
						if (Integer.parseInt(bets[i]) - j - c == 0) {
							bnum++;
						}
					}
				}
			}
		}
		return bnum;
	}

	public static HashMap<String, Integer> countOfArray(ArrayList<String> data) {
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		for (int i = 0; i < data.size(); i++) {
			if (result.containsKey(data.get(i))) {
				result.put(data.get(i), (result.get(data.get(i)) + 1));
			} else {
				result.put(data.get(i), 1);
			}
		}
		return result;
	}

	// 任选二复式
	public static Integer rx2fs(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-" };
		String[] bets = bet.split(",");
		if (bets.length != 5) {
			return 0;
		}
		HashMap<String, Integer> e = countOfArray(new ArrayList<String>(Arrays.asList(bets)));
		if (e.containsKey("-")) {
			if (e.get("-") != 3) {
				return 0;
			}
		} else {
			return 0;
		}
		int ret = 1;
		for (int i = 0; i < bets.length; i++) {
			if (!"-".equals(bets[i])) {
				String[] codes = bets[i].split("");
				int[] count = deleteSameNumber(codes);
				if (codes.length != count.length || codes.length > 10 || codes.length < 1) {
					return 0;
				}
				for (int j = 0; j < codes.length; j++) {
					boolean inArray = false;
					for (int k = 0; k < check.length; k++) {
						if (check[k].equals(codes[j])) {
							inArray = true;
							break;
						}
					}
					if (!inArray) {
						return 0;
					}
				}
				ret = ret * codes.length;
			}
		}
		return ret;
	}

	// 任选二单式
	public static Integer rx2ds(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-" };
		String[] bets = bet.split("\\|");
		for (int i = 0; i < bets.length; i++) {
			String[] c = bets[i].split(",");
			if (c.length != 5) {
				return 0;
			}
			HashMap<String, Integer> e = countOfArray(new ArrayList<String>(Arrays.asList(c)));
			if (e.get("-") != 3) {
				return 0;
			}
			for (int j = 0; j < c.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(c[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
		}
		return bets.length;
	}

	// 任选三复式
	public static Integer rx3fs(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-" };
		String[] bets = bet.split(",");
		if (bets.length != 5) {
			return 0;
		}
		HashMap<String, Integer> e = countOfArray(new ArrayList<String>(Arrays.asList(bets)));
		if (e.containsKey("-")) {
			if (e.get("-") != 2) {
				return 0;
			}
		} else {
			return 0;
		}
		int ret = 1;
		for (int i = 0; i < bets.length; i++) {
			if(!"-".equals(bets[i])) {
			String[] codes = bets[i].split("");
			int[] count = deleteSameNumber(codes);
			if (codes.length != count.length || codes.length > 10 || codes.length < 1) {
				return 0;
			}
			for (int j = 0; j < codes.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(codes[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
			ret = ret * codes.length;
			}
		}
		return ret;
	}

	// 任选三单式
	public static Integer rx3ds(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-" };
		String[] bets = bet.split("\\|");
		for (int i = 0; i < bets.length; i++) {
			String[] c = bets[i].split(",");
			if (c.length != 5) {
				return 0;
			}
			HashMap<String, Integer> e = countOfArray(new ArrayList<String>(Arrays.asList(c)));
			if (e.get("-") != 2) {
				return 0;
			}
			for (int j = 0; j < c.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(c[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
		}
		return bets.length;
	}

	// 任选四复式
	public static Integer rx4fs(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-" };
		String[] bets = bet.split(",");
		if (bets.length != 5) {
			return 0;
		}
		HashMap<String, Integer> e = countOfArray(new ArrayList<String>(Arrays.asList(bets)));
		if (e.containsKey("-")) {
			if (e.get("-") != 1) {
				return 0;
			}
		} else {
			return 0;
		}
		int ret = 1;
		for (int i = 0; i < bets.length; i++) {
			if(!"-".equals(bets[i])) {
			String[] codes = bets[i].split("");
			int[] count = deleteSameNumber(codes);
			if (codes.length != count.length || codes.length > 10 || codes.length < 1) {
				return 0;
			}
			for (int j = 0; j < codes.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(codes[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
			ret = ret * codes.length;
			}
		}
		return ret;
	}

	// 任选四单式
	public static Integer rx4ds(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-" };
		String[] bets = bet.split("\\|");
		for (int i = 0; i < bets.length; i++) {
			String[] c = bets[i].split(",");
			if (c.length != 5) {
				return 0;
			}
			HashMap<String, Integer> e = countOfArray(new ArrayList<String>(Arrays.asList(c)));
			if (e.get("-") != 1) {
				return 0;
			}
			for (int j = 0; j < c.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(c[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
		}
		return bets.length;
	}

	// 前后三二码
	public static Integer r2ssc(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(" ");
		int[] bet1 = deleteSameNumber(bets);
		if (bets.length != bet1.length || bets.length < 2 || bets.length > 10) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return rx(bet, 2);
	}

	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static boolean checkDoubleSameNumberInStr(String s) {
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		String[] sData = s.split("");
		for (int i = 0; i < sData.length; i++) {
			if (isNumeric(sData[i])) {
				if (result.containsKey(sData[i])) {
					return true;
				} else {
					result.put(sData[i], 1);
				}
			}
		}
		return false;
	}

	// 任选三组三
	public static Integer rx3z3(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		if ((bet.indexOf(",") == -1) && !checkDoubleSameNumberInStr(bet)) {
			String[] bets = bet.split("");
			if (bets.length < 2 || bets.length > 10) {
				return 0;
			}
			for (int i = 0; i < bets.length; i++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(bets[i])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
			return A(bets.length, 2);
		} else {
			// 来自混合组选
			return bet.split(",").length;
		}
	}

	// 任选三组六
	public static Integer rx3z6(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		if ((bet.indexOf(",") == -1) && !checkDoubleSameNumberInStr(bet)) {
			String[] bets = bet.split("");
			if (bets.length < 3 || bets.length > 10) {
				return 0;
			}
			for (int i = 0; i < bets.length; i++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(bets[i])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
			return C(bets.length, 3);
		} else {
			// 来自混合组选
			return bet.split(",").length;
		}
	}

	// 组三
	public static Integer z3(String bet) {
		if ((bet.indexOf(",") == -1) && !checkDoubleSameNumberInStr(bet)) {
			String[] bets = bet.split("");
			return A(bets.length, 2);
		} else {
			// 来自混合组选
			String[] bets = bet.split(",");
			return bets.length;
		}
	}

	// 组六
	public static Integer z6(String bet) {
		if ((bet.indexOf(",") == -1) && !checkDoubleSameNumberInStr(bet)) {
			String[] bets = bet.split("");
			return C(bets.length, 3);
		} else {
			// 来自混合组选
			String[] bets = bet.split(",");
			return bets.length;
		}
	}

	// 组二
	public static Integer z2(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split("");
		int[] bet1 = deleteSameNumber(bets);
		if (bet1.length != bets.length) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(bets.length, 2);
	}

	// 五星定位胆
	// 三星定位胆
	// 五星定胆
	public static Integer dwd(String bet) {
		return bet.replaceAll(",", "").replaceAll("-", "").length();
	}

	public static Integer ssc5xdwd(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(",");
		int m = bets.length - 1;
		for (int i = 0; i < bets.length; i++) {
			if ("".equals(bets[i])) {
				return 0;
			}
		}
		String bet1 = bet.replaceAll("-", "");
		String[] a1 = bet1.split(",", 5);
		String[] a = bet1.split(",");
		HashMap<String, Integer> n = countOfArray(new ArrayList<String>(Arrays.asList(a)));
		if (a1.length != 5 || (a1.length - 1) != m || n.containsKey(" ")) {
			return 0;
		}
		for (int i = 0; i < a.length; i++) {
			String[] g = a[i].split("");
			int[] c = deleteSameNumber(g);
			if (g.length != c.length || g.length > 10 || g.length < 1) {
				return 0;
			}
		}
		String x = bet.replaceAll(",", "").replaceAll("-", "");
		String[] z = x.split("");
		for (int i = 0; i < z.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(z[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return x.length();
	}

	// 十星定胆
	public static Integer dwd10(String bet) {
		return bet.replaceAll(",", " ").replaceAll("-", " ").length() / 2;
	}

	// 前后二大小单双
	public static Integer qh2dxds(String bet) {
		bet = bet.replaceAll("大", "1").replaceAll("小", "2").replaceAll("单", "3").replaceAll("双", "4");
		String[] bets = bet.split(",");
		String[] a = bets[0].split("");
		String[] b = bets[1].split("");
		int[] a1 = deleteSameNumber(a);
		int[] b1 = deleteSameNumber(b);
		if (a.length < 1 || b.length < 1 || a.length > 4 || b.length > 4) {
			return 0;
		}
		if (a.length != a1.length || b.length != b1.length) {
			return 0;
		}
		return fs(bet);
	}

	// 前后三大小单双
	public static Integer qh3dxds(String bet) {
		bet = bet.replaceAll("大", "1").replaceAll("小", "2").replaceAll("单", "3").replaceAll("双", "4");
		String[] bets = bet.split(",");
		String[] a = bets[0].split("");
		String[] b = bets[1].split("");
		String[] c = bets[2].split("");
		int[] a1 = deleteSameNumber(a);
		int[] b1 = deleteSameNumber(b);
		int[] c1 = deleteSameNumber(c);
		if (a.length < 1 || b.length < 1 || c.length < 1 || a.length > 4 || b.length > 4 || c.length > 4) {
			return 0;
		}
		if (a.length != a1.length || b.length != b1.length || c.length != c1.length) {
			return 0;
		}
		return fs(bet);
	}

	// 任选大小单双
	public static Integer rxdxds(String bet) {
		bet = bet.replaceAll("大", "1").replaceAll("小", "2").replaceAll("单", "3").replaceAll("双", "4");
		String[] bets = bet.split(",");
		HashMap<String, Integer> e = countOfArray(new ArrayList<String>(Arrays.asList(bets)));
		if (e.containsKey("-")) {
			if (e.get("-") != 3 || bets.length != 5) {
				return 0;
			}
		} else {
			return 0;
		}
		int ret = 1;
		for (int i = 0; i < bets.length; i++) {
			if (!"-".equals(bets[i])) {
				String[] a = bets[i].split("");
				int[] a1 = deleteSameNumber(a);
				if (a.length < 1 || a.length > 4 || a.length != a1.length) {
					return 0;
				}
				ret = ret * a.length;
			}
		}
		return ret;
	}

	// 大小单双
	public static Integer dxds(String bet) {
		bet = bet.replaceAll("大", "1").replaceAll("小", "2").replaceAll("单", "3").replaceAll("双", "4");
		return fs(bet);
	}

	// 定单双
	public static Integer dds(String bet) {
		bet = bet.replaceAll("5单0双", "1").replaceAll("4单1双", "2").replaceAll("3单2双", "3").replaceAll("2单3双", "4")
				.replaceAll("1单4双", "5").replaceAll("0单5双", "6");
		return fs(bet);
	}

	// 龙虎
	public static Integer lh(String bet) {
		String[] check = { "1", "2" };
		bet = bet.replaceAll("龙", "1").replaceAll("虎", "2");
		String[] bets = bet.split("");
		int[] a1 = deleteSameNumber(bets);

		if (a1.length != bets.length || bets.length > 2 || bets.length == 0) {
			return 0;
		}
		return fs(bet);
	}

	// 龙虎
	public static Integer ssclh(String bet) {
		String[] check = { "1", "2", "3" };
		bet = bet.replaceAll("龙", "1").replaceAll("虎", "2").replaceAll("和", "3");
		String[] bets = bet.split("");
		int[] a1 = deleteSameNumber(bets);

		if (a1.length != bets.length || bets.length > 3 || bets.length == 0) {
			return 0;
		}
		return fs(bet);
	}

	public static Integer lhfs(String bet) {
		String[] bets = bet.split(",");
		int ret = 1;
		for (int i = 0; i < bets.length; i++) {
			ret = ret * bets[i].length();
		}
		return ret;
	}

	// 组选120
	public static Integer zx120(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(",");
		int[] a1 = deleteSameNumber(bets);
		if (bets.length != a1.length || bets.length > 10 || bets.length < 5) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(bets.length, 5);
	}

	// 组选60
	public static Integer zx60(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(",");
		String[] sele_count = { "0", "0", "0", "1", "4", "10", "20", "35", "56", "84" };
		int num = 0;
		int anum = 0;
		String[] dCode = bets[0].split("");
		String[] tCode = bets[1].split("");
		int[] a1 = deleteSameNumber(dCode);
		int[] b1 = deleteSameNumber(tCode);
		if (dCode.length < 1 || dCode.length > 10 || dCode.length != a1.length) {
			return 0;
		}
		if (tCode.length < 3 || tCode.length > 10 || tCode.length != b1.length) {
			return 0;
		}
		for (int i = 0; i < dCode.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(dCode[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		for (int i = 0; i < tCode.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(tCode[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		num = Sames(new ArrayList<String>(Arrays.asList(dCode)), new ArrayList<String>(Arrays.asList(tCode)));
		int c = 0;
		if (tCode.length - 1 >= 0) {
			c = tCode.length - 1;
		}
		if (num - 1 >= 0) {
			if (dCode.length - num == 0) {
				anum = Integer.parseInt(sele_count[c]) * dCode.length;
			}
			if (dCode.length - num > 0) {
				anum = Integer.parseInt(sele_count[tCode.length]) * (dCode.length - num)
						+ Integer.parseInt(sele_count[c]) * num;
			}
		} else {
			anum = Integer.parseInt(sele_count[tCode.length]) * dCode.length;
		}
		return anum;
	}

	// 组选30
	public static Integer zx30(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(",");
		int bnum = 0;
		String[] dCode = bets[0].split("");
		String[] tCode = bets[1].split("");
		int[] a1 = deleteSameNumber(dCode);
		int[] b1 = deleteSameNumber(tCode);
		if (dCode.length < 2 || dCode.length > 10 || dCode.length != a1.length) {
			return 0;
		}
		if (tCode.length < 1 || tCode.length > 10 || tCode.length != b1.length) {
			return 0;
		}
		for (int i = 0; i < dCode.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(dCode[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		for (int i = 0; i < tCode.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(tCode[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		for (int i = 0; i < dCode.length - 1; i++) {
			int d = i + 1;
			for (int j = d; j < dCode.length; j++) {
				for (int c = 0; c < tCode.length; c++) {
					if ((Integer.parseInt(dCode[i]) - Integer.parseInt(tCode[c]) != 0)
							&& (Integer.parseInt(dCode[j]) - Integer.parseInt(tCode[c]) != 0)) {
						bnum = bnum + 1;
					}
				}
			}
		}
		return bnum;
	}

	// 组选20
	public static Integer zx20(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(",");
		int bnum = 0;
		String[] dCode = bets[0].split("");
		String[] tCode = bets[1].split("");
		int[] a1 = deleteSameNumber(dCode);
		int[] b1 = deleteSameNumber(tCode);
		if (dCode.length < 1 || dCode.length > 10 || dCode.length != a1.length) {
			return 0;
		}
		if (tCode.length < 2 || tCode.length > 10 || tCode.length != b1.length) {
			return 0;
		}
		for (int i = 0; i < dCode.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(dCode[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		for (int i = 0; i < tCode.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(tCode[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		for (int i = 0; i < tCode.length - 1; i++) {
			int d = i + 1;
			for (int j = d; j < tCode.length; j++) {
				for (int c = 0; c < dCode.length; c++) {
					if ((Integer.parseInt(tCode[i]) - Integer.parseInt(dCode[c]) != 0)
							&& (Integer.parseInt(tCode[j]) - Integer.parseInt(dCode[c]) != 0)) {
						bnum = bnum + 1;
					}
				}
			}
		}
		return bnum;
	}

	// 组选10
	public static Integer zx10(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(",");
		int bnum = 0;
		String[] dCode = bets[0].split("");
		String[] tCode = bets[1].split("");
		int[] a1 = deleteSameNumber(dCode);
		int[] b1 = deleteSameNumber(tCode);
		if (dCode.length < 1 || dCode.length > 10 || dCode.length != a1.length) {
			return 0;
		}
		if (tCode.length < 1 || tCode.length > 10 || tCode.length != b1.length) {
			return 0;
		}
		for (int i = 0; i < dCode.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(dCode[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		for (int i = 0; i < tCode.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(tCode[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		for (int i = 0; i < dCode.length; i++) {
			for (int j = 0; j < tCode.length; j++) {
				if (Integer.parseInt(dCode[i]) - Integer.parseInt(tCode[j]) != 0) {
					bnum = bnum + 1;
				}
			}
		}
		return bnum;
	}

	// 组选5
	public static Integer zx5(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(",");
		int bnum = 0;
		String[] dCode = bets[0].split("");
		String[] tCode = bets[1].split("");
		int[] a1 = deleteSameNumber(dCode);
		int[] b1 = deleteSameNumber(tCode);
		if (dCode.length < 1 || dCode.length > 10 || dCode.length != a1.length) {
			return 0;
		}
		if (tCode.length < 1 || tCode.length > 10 || tCode.length != b1.length) {
			return 0;
		}
		for (int i = 0; i < dCode.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(dCode[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		for (int i = 0; i < tCode.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(tCode[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		for (int i = 0; i < dCode.length; i++) {
			for (int j = 0; j < tCode.length; j++) {
				if (Integer.parseInt(dCode[i]) - Integer.parseInt(tCode[j]) != 0) {
					bnum = bnum + 1;
				}
			}
		}
		return bnum;
	}

	// 组选24
	public static Integer zx24(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(",");
		String[] sele_count = { "0", "0", "0", "1", "5", "15", "35", "70", "126", "210" };
		int[] repet = deleteSameNumber(bets);
		if (bets.length < 4 || bets.length > 10 || repet.length != bets.length) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		int num = bets.length - 1;
		return Integer.parseInt(sele_count[num]);
	}

	// 组选12
	public static Integer zx12(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(",");
		String[] sele_count = { "0", "1", "3", "6", "10", "15", "21", "28", "36" };
		String[] dCode = bets[0].split("");
		String[] tCode = bets[1].split("");
		int[] a1 = deleteSameNumber(dCode);
		int[] b1 = deleteSameNumber(tCode);
		if (dCode.length < 1 || dCode.length > 10 || dCode.length != a1.length) {
			return 0;
		}
		if (tCode.length < 2 || tCode.length > 10 || tCode.length != b1.length) {
			return 0;
		}
		for (int i = 0; i < dCode.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(dCode[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		for (int i = 0; i < tCode.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(tCode[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		int anum = 0;
		int num = Sames(new ArrayList<String>(Arrays.asList(dCode)), new ArrayList<String>(Arrays.asList(tCode)));
		int c = 0;
		int d = 0;
		if (tCode.length - 1 > 0) {
			c = tCode.length - 1;
		}
		if (tCode.length - 2 > 0) {
			d = tCode.length - 2;
		}
		if (num - 1 >= 0) {
			if (dCode.length - num == 0) {
				c = tCode.length - 2;
				anum = Integer.parseInt(sele_count[c]) * dCode.length;
			}
			if (dCode.length - num > 0) {
				c = tCode.length - 2;
				anum = Integer.parseInt(sele_count[c]) * num;
				anum = anum + Integer.parseInt(sele_count[tCode.length - 1]) * (dCode.length - num);
			}
		} else {
			if (tCode.length - 1 >= 0) {
				c = tCode.length - 1;
			} else {
				c = 0;
			}
			anum = Integer.parseInt(sele_count[c]) * dCode.length;
		}
		return anum;
	}

	// 组选6
	public static Integer zx6(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(",");
		String[] sele_count = { "0", "0", "1", "3", "6", "10", "15", "21", "28", "36", "45" };
		int[] repet = deleteSameNumber(bets);
		if (bets.length < 2 || bets.length > 10 || repet.length != bets.length) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return Integer.parseInt(sele_count[bets.length]);
	}

	// 组选4
	public static Integer zx4(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(",");
		String[] dCode = bets[0].split("");
		String[] tCode = bets[1].split("");
		int[] a1 = deleteSameNumber(dCode);
		int[] b1 = deleteSameNumber(tCode);
		if (dCode.length < 1 || dCode.length > 10 || dCode.length != a1.length) {
			return 0;
		}
		if (tCode.length < 1 || tCode.length > 10 || tCode.length != b1.length) {
			return 0;
		}
		for (int i = 0; i < dCode.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(dCode[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		for (int i = 0; i < tCode.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(tCode[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		int num = 0;
		int bnum = 0;
		for (int e = 0; e < dCode.length; e++) {
			num = Integer.parseInt(dCode[e]);
			for (int i = 0; i < tCode.length; i++) {
				if (Integer.parseInt(tCode[i]) - num != 0) {
					bnum = bnum + 1;
				}
			}
		}
		return bnum;
	}

	// 前三直选跨度
	public static Integer sscq3zhixkd(String bet) {
		return ssch3zhixkd(bet);
	}

	public static Integer sscz3zhixkd(String bet) {
		return ssch3zhixkd(bet);
	}

	// 后三直选跨度
	public static Integer ssch3zhixkd(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] sele_count = { "10", "54", "96", "126", "144", "150", "144", "126", "96", "54" };
		String[] bets = bet.split(",");
		int[] repet = deleteSameNumber(bets);
		if (bets.length < 1 || bets.length > 10 || repet.length != bets.length) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		int endnum = 0;
		for (int i = 0; i < bets.length; i++) {
			int num = Integer.parseInt(bets[i]);
			if (num - 1 >= -1) {
				endnum = endnum + Integer.parseInt(sele_count[num]);
			}
		}
		return endnum;
	}

	public static Integer sscq3zxhz(String bet) {
		return ssch3zxhz(bet);
	}

	public static Integer sscz3zxhz(String bet) {
		return ssch3zxhz(bet);
	}

	// 后三组选和值
	public static Integer ssch3zxhz(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
				"17", "18", "19", "20", "21", "22", "23", "24", "25", "26" };
		String[] sele_count = { "1", "2", "2", "4", "5", "6", "8", "10", "11", "13", "14", "14", "15", "15", "14", "14",
				"13", "11", "10", "8", "6", "5", "4", "2", "2", "1" };
		String[] bets = bet.split(",");

		int endnum = 0;
		if (bets.length > 26 || bets.length < 1) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
			endnum += Integer.parseInt(sele_count[Integer.parseInt(bets[i]) - 1]);
		}
		return endnum;
	}

	public static Integer sscq3tshm(String bet) {
		return ssch3tshm(bet);
	}

	public static Integer sscz3tshm(String bet) {
		return ssch3tshm(bet);
	}

	// 后三特殊号码
	public static Integer ssch3tshm(String bet) {
		bet = bet.replaceAll("豹子", "1").replaceAll("顺子", "2").replaceAll("对子", "3");
		String[] bets = bet.split(",");
		int[] bet1 = deleteSameNumber(bets);
		if (bets.length < 1 || bets.length > 3 || bet1.length != bets.length) {
			return 0;
		}
		return bets.length;
	}

	// 前中后三一码
	public static Integer sscqzhr31m(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split("");
		int[] repet = deleteSameNumber(bets);
		if (bets.length < 1 || bets.length > 10 || repet.length != bets.length) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return bets.length;
	}

	// 四星一码
	public static Integer ssc4x1m(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(" ");
		int[] repet = deleteSameNumber(bets);
		if (bets.length < 1 || bets.length > 10 || repet.length != bets.length) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return bets.length;
	}

	// 五星三码
	public static Integer ssc5x3m(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(" ");
		int[] repet = deleteSameNumber(bets);
		if (bets.length < 3 || bets.length > 10 || repet.length != bets.length) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(bets.length, 3);
	}

	// 趣味玩法1-4
	public static Integer r1sscqw(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(" ");
		int[] repet = deleteSameNumber(bets);
		if (bets.length < 1 || bets.length > 10 || repet.length != bets.length) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return bets.length;
	}

	// 前后三趣味二星
	public static Integer qh3qw2x(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		bet = bet.replaceAll("小", "1").replaceAll("大", "2");
		String[] bets = bet.split(",");
		String[] a = bets[0].split("");
		String[] b = bets[1].split("");
		String[] c = bets[2].split("");
		int[] a1 = deleteSameNumber(a);
		int[] b1 = deleteSameNumber(b);
		int[] c1 = deleteSameNumber(c);
		if (a.length < 1 || b.length < 1 || c.length < 1 || a.length > 2 || b.length > 10 || c.length > 10) {
			return 0;
		}
		if (a.length != a1.length || b.length != b1.length || c.length != c1.length) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			String[] strK = bets[i].split("");
			for (int j = 0; j < strK.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(strK[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}

		}
		return (a.length * b.length * c.length);
	}

	// 四码趣味三星
	public static Integer ssc4mqw3x(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		bet = bet.replaceAll("小", "1").replaceAll("大", "2");
		String[] bets = bet.split(",");
		String[] a = bets[0].split("");
		String[] b = bets[1].split("");
		String[] c = bets[2].split("");
		String[] d = bets[3].split("");
		int[] a1 = deleteSameNumber(a);
		int[] b1 = deleteSameNumber(b);
		int[] c1 = deleteSameNumber(c);
		int[] d1 = deleteSameNumber(d);
		if (a.length < 1 || b.length < 1 || c.length < 1 || d.length < 1 || a.length > 2 || b.length > 10
				|| c.length > 10 || d.length > 10) {
			return 0;
		}
		if (a.length != a1.length || b.length != b1.length || c.length != c1.length || d.length != d1.length) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			String[] strK = bets[i].split("");
			for (int j = 0; j < strK.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(strK[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}

		}
		return (a.length * b.length * c.length * d.length);
	}

	// 前后三趣味二星
	public static Integer ssc5mqw3x(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		bet = bet.replaceAll("小", "1").replaceAll("大", "2");
		String[] bets = bet.split(",");
		String[] a = bets[0].split("");
		String[] b = bets[1].split("");
		String[] c = bets[2].split("");
		String[] d = bets[3].split("");
		String[] e = bets[4].split("");
		int[] a1 = deleteSameNumber(a);
		int[] b1 = deleteSameNumber(b);
		int[] c1 = deleteSameNumber(c);
		int[] d1 = deleteSameNumber(d);
		int[] e1 = deleteSameNumber(e);
		if (a.length < 1 || b.length < 1 || c.length < 1 || d.length < 1 || e.length < 1 || a.length > 2 || b.length > 2
				|| c.length > 10 || d.length > 10 || e.length > 10) {
			return 0;
		}
		if (a.length != a1.length || b.length != b1.length || c.length != c1.length || d.length != d1.length
				|| e.length != e1.length) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			String[] strK = bets[i].split("");
			for (int j = 0; j < strK.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(strK[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
		}
		return (a.length * b.length * c.length * d.length * e.length);
	}

	// {{{ 十一选五

	// 任选一
	public static Integer r111x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length > 11 || bets.length < 1) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return bets.length;
	}

	// 任选一单式
	public static Integer r1ds11x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		int x = bet.length();
		if (x % 2 != 0 || x != 2) {
			return 0;
		}
		String[] newBet = new String[x / 2];
		for (int i = 0; i < x / 2; i++) {
			newBet[i] = bet.substring(i, i * 2);
		}
		int[] newBetUnique = deleteSameNumber(newBet);
		if (newBet.length != newBetUnique.length) {
			return 0;
		}
		for (int i = 0; i < newBet.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(newBet[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(newBet.length, 1);
	}

	// 任选二
	// 前二组选
	public static Integer r211x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length > 11 || bets.length < 2) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return rx(bet, 2);
	}

	// 任选二单式
	public static Integer r2ds11x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		int x = bet.length();
		if (x % 4 != 0 || x != 4) {
			return 0;
		}
		String[] newBet = new String[x / 2];
		for (int i = 0; i < x / 2; i++) {
			newBet[i] = bet.substring(i, i * 2);
		}
		int[] newBetUnique = deleteSameNumber(newBet);
		if (newBet.length != newBetUnique.length) {
			return 0;
		}
		for (int i = 0; i < newBet.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(newBet[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(newBet.length, 2);
	}

	// 任选三
	// 前三组选
	public static Integer r311x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length > 11 || bets.length < 3) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return rx(bet, 3);
	}

	// 任选三单式
	public static Integer r3ds11x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		int x = bet.length();
		if (x % 6 != 0 || x != 6) {
			return 0;
		}
		String[] newBet = new String[x / 2];
		for (int i = 0; i < x / 2; i++) {
			newBet[i] = bet.substring(i, i * 2);
		}
		int[] newBetUnique = deleteSameNumber(newBet);
		if (newBet.length != newBetUnique.length) {
			return 0;
		}
		for (int i = 0; i < newBet.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(newBet[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(newBet.length, 3);
	}

	public static Integer r411x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length > 11 || bets.length < 4) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return rx(bet, 4);
	}

	// 任选四单式
	public static Integer r4ds11x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		int x = bet.length();
		if (x % 8 != 0 || x != 8) {
			return 0;
		}
		String[] newBet = new String[x / 2];
		for (int i = 0; i < x / 2; i++) {
			newBet[i] = bet.substring(i, i * 2);
		}
		int[] newBetUnique = deleteSameNumber(newBet);
		if (newBet.length != newBetUnique.length) {
			return 0;
		}
		for (int i = 0; i < newBet.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(newBet[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(newBet.length, 4);
	}

	public static Integer r511x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length > 11 || bets.length < 5) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return rx(bet, 5);
	}

	// 任选五单式
	public static Integer r5ds11x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		int x = bet.length();
		if (x % 10 != 0 || x != 10) {
			return 0;
		}
		String[] newBet = new String[x / 2];
		for (int i = 0; i < x / 2; i++) {
			newBet[i] = bet.substring(i, i * 2);
		}
		int[] newBetUnique = deleteSameNumber(newBet);
		if (newBet.length != newBetUnique.length) {
			return 0;
		}
		for (int i = 0; i < newBet.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(newBet[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(newBet.length, 5);
	}

	public static Integer r611x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length > 11 || bets.length < 6) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return rx(bet, 6);
	}

	// 任选六单式
	public static Integer r6ds11x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		int x = bet.length();
		if (x % 12 != 0 || x != 12) {
			return 0;
		}
		String[] newBet = new String[x / 2];
		for (int i = 0; i < x / 2; i++) {
			newBet[i] = bet.substring(i, i * 2);
		}
		int[] newBetUnique = deleteSameNumber(newBet);
		if (newBet.length != newBetUnique.length) {
			return 0;
		}
		for (int i = 0; i < newBet.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(newBet[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(newBet.length, 6);
	}

	public static Integer r711x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length > 11 || bets.length < 7) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return rx(bet, 7);
	}

	// 任选七单式
	public static Integer r7ds11x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		int x = bet.length();
		if (x % 14 != 0 || x != 14) {
			return 0;
		}
		String[] newBet = new String[x / 2];
		for (int i = 0; i < x / 2; i++) {
			newBet[i] = bet.substring(i, i * 2);
		}
		int[] newBetUnique = deleteSameNumber(newBet);
		if (newBet.length != newBetUnique.length) {
			return 0;
		}
		for (int i = 0; i < newBet.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(newBet[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(newBet.length, 7);
	}

	public static Integer r811x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length > 11 || bets.length < 8) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return rx(bet, 8);
	}

	// 任选八单式
	public static Integer r8ds11x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		int x = bet.length();
		if (x % 16 != 0 || x != 16) {
			return 0;
		}
		String[] newBet = new String[x / 2];
		for (int i = 0; i < x / 2; i++) {
			newBet[i] = bet.substring(i, i * 2);
		}
		int[] newBetUnique = deleteSameNumber(newBet);
		if (newBet.length != newBetUnique.length) {
			return 0;
		}
		for (int i = 0; i < newBet.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(newBet[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(newBet.length, 8);
	}

	// 11选5前一直选
	public static Integer q1zx11x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length > 11 || bets.length < 1) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return bets.length;
	}

	// 11选5定位胆
	public static Integer dwd11x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		String newBet = bet.replaceAll("-", "");
		String[] a = newBet.split(",");
		for (int i = 0; i < a.length; i++) {
			String[] g = a[i].split(" ");
			int[] c = deleteSameNumber(g);
			if (g.length != c.length || g.length > 11 || g.length < 1) {
				return 0;
			}
		}
		String newBet2 = newBet.replaceAll(",", "").replaceAll(" ", "");
		if (newBet2.length() < 2 || newBet2.length() % 2 != 0) {
			return 0;
		}
		int x = newBet2.length();
		String[] newBetData = new String[x / 2];
		for (int i = 0; i < x / 2; i++) {
			newBetData[i] = newBet2.substring(i, i * 2);
		}
		for (int i = 0; i < newBetData.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(newBetData[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return newBetData.length;
	}

	// 11选5不定位
	public static Integer bdw11x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length > 11 || bets.length < 1) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return bets.length;
	}

	// 任选二
	// 前二组选
	public static Integer r2(String bet) {
		return rx(bet, 2);
	}

	// 任选三
	// 前三组选
	public static Integer r3(String bet) {
		return rx(bet, 3);
	}

	public static Integer r4(String bet) {
		return rx(bet, 4);
	}

	public static Integer r5(String bet) {
		return rx(bet, 5);
	}

	public static Integer r6(String bet) {
		return rx(bet, 6);
	}

	public static Integer r7(String bet) {
		return rx(bet, 7);
	}

	public static Integer r8(String bet) {
		return rx(bet, 8);
	}

	public static Integer r9(String bet) {
		return rx(bet, 9);
	}

	public static Integer r10(String bet) {
		return rx(bet, 10);
	}

	// 十一选五直选
	public static Integer zx11(String bet) {
		String[] bets = bet.split(",");
		int ret = 1;
		for (int i = 0; i < bets.length; i++) {
			ret = ret * bets[i].split(" ").length;
		}
		return ret;
	}

	// 十一选五前/后二单式
	// 前/后二单式
	public static Integer qh2ds11x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		String[] bets = bet.split("\\|");
		for (int i = 0; i < bets.length; i++) {
			String[] c = bets[i].split(",");
			if (c.length != 2) {
				return 0;
			}
			for (int j = 0; j < c.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(c[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
		}
		return bets.length;
	}

	// 前/后三单式
	public static Integer qh3ds11x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		String[] bets = bet.split("\\|");
		for (int i = 0; i < bets.length; i++) {
			String[] c = bets[i].split(",");
			if (c.length != 3) {
				return 0;
			}
			for (int j = 0; j < c.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(c[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
		}
		return bets.length;
	}

	// K3和值
	public static Integer k3hz(String bet) {
		String[] check = { "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length > 16 || bets.length < 1) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return bets.length;
	}

	// K3三通号单选
	public static Integer k33tdx(String bet) {
		String[] check = { "111", "222", "333", "444", "555", "666" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length > 6 || bets.length < 1) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return bets.length;
	}

	// K3三通号通选
	public static Integer k33ttx(String bet) {
		String[] check = { "111", "222", "333", "444", "555", "666" };
		String[] bets = bet.split(",");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length != 6) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return 1;
	}

	// K3三连号通选
	public static Integer k33ltx(String bet) {
		String[] check = { "123", "234", "345", "456" };
		String[] bets = bet.split(",");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return 1;
	}

	// K3三不同号
	public static Integer k33bt(String bet) {
		String[] check = { "1", "2", "3", "4", "5", "6" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length > 6 || bets.length < 3) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(bets.length, 3);
	}

	// K3二不同号
	public static Integer k32bt(String bet) {
		String[] check = { "1", "2", "3", "4", "5", "6" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length > 6 || bets.length < 2) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(bets.length, 2);
	}

	// K3二同号复选
	public static Integer k32tfx(String bet) {
		String[] check = { "11*", "22*", "33*", "44*", "55*", "66*" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length > 6 || bets.length < 1) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return bets.length;
	}

	// K3二同号复选
	public static Integer k32tdx(String bet) {
		String[] check = { "11", "22", "33", "44", "55", "66" };
		String[] check2 = { "1", "2", "3", "4", "5", "6" };
		String[] bets = bet.split(" ");
		String[] a = bets[0].split(" ");
		String[] b = bets[1].split(" ");
		int[] a1 = deleteSameNumber(a);
		int[] b1 = deleteSameNumber(b);
		if (a1.length != a.length || b1.length != b.length || a.length > 6 || a.length < 1 || b.length > 6
				|| b.length < 1) {
			return 0;
		}
		for (int i = 0; i < a.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(a[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		for (int i = 0; i < b.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check2.length; k++) {
				if (check2[k].equals(b[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return a.length * b.length;
	}

	// PK10猜冠军
	public static Integer pk10cgj(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (a.length != bets.length || bets.length > 11 || bets.length < 1) {
			return 0;
		}

		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return bets.length;
	}

	// PK10猜冠亚军
	public static Integer pk10cgyj(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10" };
		String[] bets = bet.split(",");
		int ret = 1;
		for (int i = 0; i < bets.length; i++) {
			String[] codes = bets[i].split(" ");
			int[] m = deleteSameNumber(codes);
			if (codes.length != m.length || codes.length > 11 || codes.length < 1) {
				return 0;
			}
			for (int j = 0; j < codes.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(codes[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
			ret = ret * codes.length;
		}
		return ret;
	}

	// 任选一
	public static Integer r1(String bet) {
		return bet.split(" ").length;
	}

	// 冠亚季选一
	public static Integer gyjx1(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (a.length != bets.length || bets.length > 10 || bets.length < 1) {
			return 0;
		}

		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return bets.length;
	}

	// 六合彩特码
	public static Integer lhctm(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
				"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
				"33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (a.length != bets.length || bets.length > 49 || bets.length < 1) {
			return 0;
		}

		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return bets.length;
	}

	// 六合彩2中2
	public static Integer lhc2z2(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
				"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
				"33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (a.length != bets.length || bets.length > 49 || bets.length < 2) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(bets.length, 2);
	}

	// 六合彩3中3
	public static Integer lhc3z3(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
				"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
				"33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (a.length != bets.length || bets.length > 49 || bets.length < 3) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(bets.length, 3);
	}

	// 六合彩5不中
	public static Integer lhc5bz(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
				"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
				"33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (a.length != bets.length || bets.length != 5) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(bets.length, 5);
	}

	// 六合彩7不中
	public static Integer lhc7bz(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
				"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
				"33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (a.length != bets.length || bets.length != 7) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(bets.length, 7);
	}

	// 六合彩特码大小
	public static Integer lhctmdx(String bet) {
		bet = bet.replaceAll("大", "1").replaceAll("小", "2");
		if (bet.length() > 1)
			return 0;
		return bet.length();
	}

	// 六合彩总大小
	public static Integer lhczdx(String bet) {
		bet = bet.replaceAll("大", "1").replaceAll("小", "2");
		if (bet.length() > 1)
			return 0;
		return bet.length();
	}

	// 六合彩总单双
	public static Integer lhczds(String bet) {
		bet = bet.replaceAll("单", "1").replaceAll("双", "2");
		if (bet.length() > 1)
			return 0;
		return bet.length();
	}

	public static Integer fc3dhzq(String bet) {
		return fc3dhz(bet);
	}

	public static Integer fc3dhzz(String bet) {
		return fc3dhz(bet);
	}

	public static Integer fc3dhzh(String bet) {
		return fc3dhz(bet);
	}

	// 福彩3D直选和值
	public static Integer fc3dhz(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
				"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27" };
		String[] sele_count = { "1", "3", "6", "10", "15", "21", "28", "36", "45", "55", "63", "69", "73", "75", "75",
				"73", "69", "63", "55", "45", "36", "28", "21", "15", "10", "6", "3", "1" };
		String[] bets = bet.split(",");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length < 1 || bets.length > 28) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		int endnum = 0;
		for (int i = 0; i < bets.length; i++) {
			int num = Integer.parseInt(bets[i]);
			endnum = endnum + Integer.parseInt(sele_count[num]);
		}
		return endnum;
	}

	// 福彩3D组选和值
	public static Integer fc3dzxhz(String bet) {
		String[] check = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26" };
		String[] sele_count = { "1", "2", "2", "4", "5", "6", "8", "10", "11", "13", "14", "14", "15", "15", "14", "14",
				"13", "11", "10", "8", "6", "5", "4", "2", "2", "1" };
		String[] bets = bet.split(",");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length < 1 || bets.length > 27) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		int endnum = 0;
		for (int i = 0; i < bets.length; i++) {
			int num = Integer.parseInt(bets[i]) - 1;
			endnum = endnum + Integer.parseInt(sele_count[num]);
		}
		return endnum;
	}

	// 福彩3D一码不定位
	public static Integer fc3d1mbdw(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split("");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length < 1 || bets.length > 10) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return bets.length;
	}

	// 福彩3D二码不定位
	public static Integer fc3d2mbdw(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] bets = bet.split(" ");
		int[] a = deleteSameNumber(bets);
		if (bets.length != a.length || bets.length < 1 || bets.length > 10) {
			return 0;
		}
		for (int i = 0; i < bets.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(bets[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return C(bets.length, 2);
	}

	// 福彩3D三星定位
	public static Integer fc3d3xdw(String bet) {
		String[] check = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String newBet = bet.replaceAll("-", "");
		String[] bets = newBet.split(",");
		for (int i = 0; i < bets.length; i++) {
			String[] g = bets[i].split("");
			int[] c = deleteSameNumber(g);
			if (g.length != c.length || g.length > 10 || g.length < 1) {
				return 0;
			}
		}
		String newBet2 = newBet.replaceAll(",", "");
		String[] z = newBet2.split("");
		for (int i = 0; i < z.length; i++) {
			boolean inArray = false;
			for (int k = 0; k < check.length; k++) {
				if (check[k].equals(z[i])) {
					inArray = true;
					break;
				}
			}
			if (!inArray) {
				return 0;
			}
		}
		return newBet2.length();
	}

	// 百家乐
	public static Integer bjldx(String bet) { // 感覺有ERROR
		bet = bet.replaceAll("庄", "1").replaceAll("闲", "2").replaceAll("和", "3").replaceAll("庄对子", "4")
				.replaceAll("闲对子", "5").replaceAll("庄豹子", "6").replaceAll("闲豹子", "7").replaceAll("庄天王", "8")
				.replaceAll("闲天王", "9");
		if (bet.length() != 1) {
			return 0;
		}
		return bet.length();
	}

	// 排除对子 豹子
	public static Integer descar(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		String[] bets = bet.split(",");
		int ret = 1;
		for (int i = 0; i < bets.length; i++) {
			String[] codes = bets[i].split(" ");
			int[] m = deleteSameNumber(codes);
			if (codes.length != m.length || codes.length > 11 || codes.length < 1) {
				return 0;
			}
			for (int j = 0; j < codes.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(codes[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
			ret = ret * codes.length;
		}
		return ret;
	}

	// 11选5前二直选
	public static Integer q2zx11x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		String[] bets = bet.split(",");
		int ret = 1;
		int un = 0;
		String[] w = bets[0].split(" ");
		String[] v = bets[1].split(" ");

		for (int i = 0; i < bets.length; i++) {
			String[] codes = bets[i].split(" ");
			int[] m = deleteSameNumber(codes);
			if (codes.length != m.length || codes.length > 11 || codes.length < 1) {
				return 0;
			}
			for (int j = 0; j < codes.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(codes[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
			ret = ret * codes.length;
		}
		if (w.length >= v.length) {
			un = Sames(new ArrayList<String>(Arrays.deepHashCode(w)), new ArrayList<String>(Arrays.deepHashCode(v)));
		} else {
			un = Sames(new ArrayList<String>(Arrays.deepHashCode(v)), new ArrayList<String>(Arrays.deepHashCode(w)));
		}
		return ret - un;
	}

	// 11选5前三直选
	public static Integer q3zx11x5(String bet) {
		String[] check = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		String[] bets = bet.split(",");
		int ret = 1;
		for (int i = 0; i < bets.length; i++) {
			String[] codes = bets[i].split(" ");
			int[] m = deleteSameNumber(codes);
			if (codes.length != m.length || codes.length > 11 || codes.length < 1) {
				return 0;
			}
			for (int j = 0; j < codes.length; j++) {
				boolean inArray = false;
				for (int k = 0; k < check.length; k++) {
					if (check[k].equals(codes[j])) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					return 0;
				}
			}
			ret = ret * codes.length;
		}
		return ret;
	}

	// {{{ 常用算法
	// 排列
	public static Integer A(Integer n, Integer m) {
		if (n < m) {
			return 0;
		}
		Integer num = 1;
		for (int i = 0; i < m; i++) {
			num *= n - i;
		}
		return num;
	}

	// 组合
	public static Integer C(Integer n, Integer m) {
		if (n < m) {
			return 0;
		}
		return (A(n, m) / A(m, m));
	}

	// 十一选五任选
	public static Integer rx(String bet, Integer num) {
		int pos = bet.indexOf(")");
		String dm = "";
		String tm = "";
		int len = 0;
		int newNum = num;
		if (pos != -1) {
			dm = bet.substring(1, pos - 1);
			tm = bet.substring(pos + 1, bet.length());

			len = tm.split(" ").length;
			newNum = num - dm.split(" ").length;
		} else {
			len = bet.split(" ").length;
		}
		return C(len, newNum);
	}

	public static Integer Sames(ArrayList<String> a, ArrayList<String> b) {
		int num = 0;
		for (int i = 0; i < a.size(); i++) {
			int zt = 0;
			for (int j = 0; j < b.size(); j++) {
				if (a.get(i).equals(b.get(j))) {
					zt = 1;
				}
			}
			if (zt == 1) {
				num++;
			}
		}
		return num;
	}

}