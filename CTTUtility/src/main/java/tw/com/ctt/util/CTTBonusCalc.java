package tw.com.ctt.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * 時時彩對獎機
 * 
 * @author Quanto
 * @since 2018.04.11
 */
public class CTTBonusCalc {
	public static String invokeCalc(Object calc, String func, String bet, String kj) {
		try {
			Class<?> c = calc.getClass();
			Method m = c.getMethod(func, String.class, String.class);
			Object result = m.invoke(calc, bet.trim(), kj.trim());
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

	public static String invokeCalc(Object calc, String func, String bet, String kj, Integer w) {
		try {
			Class<?> c = calc.getClass();
			Method m = c.getMethod(func, String.class, String.class, Integer.class);
			Object result = m.invoke(calc, bet.trim(), kj.trim(), w);
			return "" + result;
		} catch (NoSuchMethodException e) {
			Object result = invokeCalc(calc, func, bet, kj);
			return "" + result;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String removeFromList(String in, String c, ArrayList<Integer> nums) {
		String[] arr = in.split(c);
		ArrayList<Boolean> arrFlag = new ArrayList<Boolean>();
		String result = "";
		for (int i = 0; i < arr.length; i++) {
			arrFlag.add(true);
		}
		for (int i = 0; i < nums.size(); i++) {
			arrFlag.set(nums.get(i) - 1, false);
		}
		boolean delLast = false;
		for (int i = 0; i < arr.length; i++) {
			if (arrFlag.get(i)) {
				result = result + arr[i] + c;
				delLast = true;
			}
		}
		if (delLast) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static String replaceList(String in, String c, int i, String s) {
		if (s == null || s.isEmpty() || "".equals(s.trim())) {
			s = ",";
		}
		String[] ret = in.split(s);
		ret[i - 1] = c;
		String result = "";
		boolean delLast = false;
		for (int j = 0; j < ret.length; j++) {
			result = result + ret[j] + s;
			delLast = true;
		}
		if (delLast) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static String reverse(String in) {
		return new StringBuilder(in).reverse().toString();
	}

	// 时时彩
	// 多星玩法

	// 五星单式
	public static int dxwf5d(String betData, String kjData) {
		return ds(betData, kjData);
	}

	// 五星复式
	public static int dxwf5f(String betData, String kjData) {
		return fs(betData, kjData);
	}

	// 组选120
	public static int dxwf5z120(String bet, String kj) {
		String[] betData = bet.split(",");
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		for (int i = 0; i < kjData.size() - 1; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			if (kj_0 == kj_1) {
				return 0;
			}
		}
		if (Sames(new ArrayList<String>(Arrays.asList(betData)), kjData) == 5) {
			return 1;
		} else {
			return 0;
		}
	}

	// 组选60
	public static int dxwf5z60(String bet, String kj) {
		String[] betData = bet.split(",");
		String kjs = "";
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		for (int i = 0; i < kjData.size() - 2; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			int kj_2 = Integer.parseInt(kjData.get(i + 2));
			if (kj_0 == kj_1 && kj_0 == kj_2) {
				return 0;
			}
		}
		String[] bet0 = betData[0].split("");
		String[] bet1 = betData[1].split("");
		for (int i = 0; i < kjData.size() - 1; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			if (kj_0 == kj_1) {
				kjs += kjData.get(i);
				for (int j = 0; j < 2; j++) {
					kjData.remove(i);
				}
				break;
			}
		}
		if (Sames(new ArrayList<String>(Arrays.asList(bet0)),
				new ArrayList<String>(Arrays.asList(kjs.split("")))) == 1) {
			if (Sames(new ArrayList<String>(Arrays.asList(bet1)), kjData) == 3) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// 组选30
	public static int dxwf5z30(String bet, String kj) {
		String[] betData = bet.split(",");
		String kjs = "";
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		for (int i = 0; i < kjData.size() - 2; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			int kj_2 = Integer.parseInt(kjData.get(i + 2));
			if (kj_0 == kj_1 && kj_0 == kj_2 && kj_0 == kj_2) {
				return 0;
			}
		}
		String[] bet0 = betData[0].split("");
		String[] bet1 = betData[1].split("");
		for (int i = 0; i < kjData.size() - 1; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			if (kj_0 == kj_1) {
				kjs += kjData.get(i);
				for (int j = 0; j < 2; j++) {
					kjData.remove(i);
				}
				break;
			}
		}
		for (int i = 0; i < kjData.size() - 1; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			if (kj_0 == kj_1) {
				kjs += kjData.get(i);
				for (int j = 0; j < 2; j++) {
					kjData.remove(i);
				}
				break;
			}
		}
		if (Sames(new ArrayList<String>(Arrays.asList(bet1)), kjData) == 1) {
			if (Sames(new ArrayList<String>(Arrays.asList(bet0)),
					new ArrayList<String>(Arrays.asList(kjs.split("")))) == 2) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// 组选20
	public static int dxwf5z20(String bet, String kj) {
		String[] betData = bet.split(",");
		String kjs = "";
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		for (int i = 0; i < kjData.size() - 3; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			int kj_2 = Integer.parseInt(kjData.get(i + 2));
			int kj_3 = Integer.parseInt(kjData.get(i + 3));
			if (kj_0 == kj_1 && kj_0 == kj_2 && kj_0 == kj_3) {
				return 0;
			}
		}
		String[] bet0 = betData[0].split("");
		String[] bet1 = betData[1].split("");
		for (int i = 0; i < kjData.size() - 2; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			int kj_2 = Integer.parseInt(kjData.get(i + 2));
			if (kj_0 == kj_1 && kj_1 == kj_2) {
				kjs += kjData.get(i);
				for (int j = 0; j < 3; j++) {
					kjData.remove(i);
				}
				break;
			}
		}
		if (Sames(new ArrayList<String>(Arrays.asList(bet0)),
				new ArrayList<String>(Arrays.asList(kjs.split("")))) == 1) {
			if (Sames(new ArrayList<String>(Arrays.asList(bet1)), kjData) == 2) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// 组选10
	public static int dxwf5z10(String bet, String kj) {
		String[] betData = bet.split(",");
		String kjs = "";
		String kjs2 = "";
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});

		for (int i = 0; i < kjData.size() - 3; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			int kj_2 = Integer.parseInt(kjData.get(i + 2));
			int kj_3 = Integer.parseInt(kjData.get(i + 3));
			if (kj_0 == kj_1 && kj_0 == kj_2 && kj_0 == kj_3) {
				return 0;
			}
		}

		String[] bet0 = betData[0].split("");
		String[] bet1 = betData[1].split("");
		for (int i = 0; i < kjData.size() - 2; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			int kj_2 = Integer.parseInt(kjData.get(i + 2));
			if (kj_0 == kj_1 && kj_1 == kj_2) {
				kjs += kjData.get(i);
				for (int j = 0; j < 2; j++) {
					kjData.remove(i);
				}
				break;
			}
		}
		for (int i = 0; i < kjData.size() - 1; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			if (kj_0 == kj_1) {
				kjs2 += kjData.get(i);
				for (int j = 0; j < 2; j++) {
					kjData.remove(i);
				}
				break;
			}
		}
		if (Sames(new ArrayList<String>(Arrays.asList(bet0)),
				new ArrayList<String>(Arrays.asList(kjs.split("")))) == 1) {
			if (Sames(new ArrayList<String>(Arrays.asList(bet1)),
					new ArrayList<String>(Arrays.asList(kjs2.split("")))) == 1) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// 组选5
	public static int dxwf5z5(String bet, String kj) {
		String[] betData = bet.split(",");
		String kjs = "";
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		for (int i = 0; i < kjData.size() - 4; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			int kj_2 = Integer.parseInt(kjData.get(i + 2));
			int kj_3 = Integer.parseInt(kjData.get(i + 3));
			int kj_4 = Integer.parseInt(kjData.get(i + 4));
			if (kj_0 == kj_1 && kj_0 == kj_2 && kj_0 == kj_3 && kj_0 == kj_4) {
				return 0;
			}
		}

		String[] bet0 = betData[0].split("");
		String[] bet1 = betData[1].split("");

		for (int i = 0; i < kjData.size() - 3; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			int kj_2 = Integer.parseInt(kjData.get(i + 2));
			int kj_3 = Integer.parseInt(kjData.get(i + 3));
			if (kj_0 == kj_1 && kj_1 == kj_2 && kj_2 == kj_3) {
				kjs += kjData.get(i);
				for (int j = 0; j < 4; j++) {
					kjData.remove(i);
				}
				break;
			}
		}
		if (Sames(new ArrayList<String>(Arrays.asList(bet0)),
				new ArrayList<String>(Arrays.asList(kjs.split("")))) == 1) {
			if (Sames(new ArrayList<String>(Arrays.asList(bet1)), kjData) == 1) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// 前4复式
	public static int dxwfQ4f(String betData, String kjData) {
		return fs(betData, removeFromList(kjData, ",", new ArrayList<Integer>(Arrays.asList(5))));
	}

	// 前4单式
	public static int dxwfQ4d(String betData, String kjData) {
		return ds(betData, removeFromList(kjData, ",", new ArrayList<Integer>(Arrays.asList(5))));
	}

	// 后4复式
	public static int dxwfH4f(String bet, String kj) {
		return fs(bet, removeFromList(kj, ",", new ArrayList<Integer>(Arrays.asList(1))));
	}

	// 后4单式
	public static int dxwfH4d(String bet, String kj) {
		return ds(bet, removeFromList(kj, ",", new ArrayList<Integer>(Arrays.asList(1))));
	}

	// 任选4复式
	public static int dxwfR4f(String bet, String kj) {
		String newBet = bet.replaceAll(",", "");
		int w = newBet.indexOf("-") + 1;
		String newKj = replaceList(kj, "-", w, null);
		return fs(bet, newKj);
	}

	// 任选4单式
	public static int dxwfR4d(String bet, String kj) {
		String newBet = bet.substring(0, 9).replaceAll(",", "");
		int w = newBet.indexOf("-") + 1;
		String newKj = replaceList(kj, "-", w, null);
		return ds(bet, newKj);
	}

	// 组选24
	public static int dxwf4z24(String bet, String kj) {
		String newKj = kj.substring(2, 9);
		String[] betData = bet.split(",");
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(newKj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		for (int i = 0; i < kjData.size() - 1; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			if (kj_0 == kj_1) {
				return 0;
			}
		}
		if (Sames(new ArrayList<String>(Arrays.asList(betData)), kjData) == 4) {
			return 1;
		} else {
			return 0;
		}
	}

	// 组选12
	public static int dxwf4z12(String bet, String kj) {
		String newKj = kj.substring(2, 9);
		String[] betData = bet.split(",");
		String kjs = "";
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(newKj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		for (int i = 0; i < kjData.size() - 2; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			int kj_2 = Integer.parseInt(kjData.get(i + 2));
			if (kj_0 == kj_1 && kj_0 == kj_2) {
				return 0;
			}
		}
		String[] bet0 = betData[0].split("");
		String[] bet1 = betData[1].split("");

		for (int i = 0; i < kjData.size() - 1; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			if (kj_0 == kj_1) {
				kjs += kjData.get(i);
				for (int j = 0; j < 2; j++) {
					kjData.remove(i);
				}
				break;
			}
		}
		if (Sames(new ArrayList<String>(Arrays.asList(bet0)),
				new ArrayList<String>(Arrays.asList(kjs.split("")))) == 1) {
			if (Sames(new ArrayList<String>(Arrays.asList(bet1)), kjData) == 2) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// 组选6
	public static int dxwf4z6(String bet, String kj) {
		String newKj = kj.substring(2, 9);
		String[] betData = bet.split(",");
		String kjs = "";
		String kjs2 = "";
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(newKj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		for (int i = 0; i < kjData.size() - 2; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			int kj_2 = Integer.parseInt(kjData.get(i + 2));
			if (kj_0 == kj_1 && kj_0 == kj_2) {
				return 0;
			}
		}
		for (int i = 0; i < kjData.size() - 1; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			if (kj_0 == kj_1) {
				kjs += kjData.get(i);
				for (int j = 0; j < 2; j++) {
					kjData.remove(i);
				}
				break;
			}
		}
		for (int i = 0; i < kjData.size() - 1; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			if (kj_0 == kj_1) {
				kjs2 += kjData.get(i);
				for (int j = 0; j < 2; j++) {
					kjData.remove(i);
				}
				break;
			}
		}
		if (Sames(new ArrayList<String>(Arrays.asList(betData)),
				new ArrayList<String>(Arrays.asList(kjs.split("")))) == 1) {
			if (Sames(new ArrayList<String>(Arrays.asList(betData)),
					new ArrayList<String>(Arrays.asList(kjs2.split("")))) == 1) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// 组选4
	public static int dxwf4z4(String bet, String kj) {
		String newKj = kj.substring(2, 9);
		String[] betData = bet.split(",");
		String kjs = "";
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(newKj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		for (int i = 0; i < kjData.size() - 3; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			int kj_2 = Integer.parseInt(kjData.get(i + 2));
			int kj_3 = Integer.parseInt(kjData.get(i + 3));
			if (kj_0 == kj_1 && kj_0 == kj_2 && kj_0 == kj_3) {
				return 0;
			}
		}
		String[] bet0 = betData[0].split("");
		String[] bet1 = betData[1].split("");

		for (int i = 0; i < kjData.size() - 2; i++) {
			int kj_0 = Integer.parseInt(kjData.get(i));
			int kj_1 = Integer.parseInt(kjData.get(i + 1));
			int kj_2 = Integer.parseInt(kjData.get(i + 2));
			if (kj_0 == kj_1 && kj_1 == kj_2) {
				kjs += kjData.get(i);
				for (int j = 0; j < 3; j++) {
					kjData.remove(i);
				}
				break;
			}
		}
		if (Sames(new ArrayList<String>(Arrays.asList(bet0)),
				new ArrayList<String>(Arrays.asList(kjs.split("")))) == 1) {
			if (Sames(new ArrayList<String>(Arrays.asList(bet1)), kjData) == 1) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// {{{ 三星玩法

	// 前三复式
	public static int sxwfQ3f(String bet, String kj) {
		return fs(bet, removeFromList(kj, ",", new ArrayList<Integer>(Arrays.asList(4, 5))));
	}

	// 前三单式
	public static int sxwfQ3d(String bet, String kj) {
		return ds(bet, removeFromList(kj, ",", new ArrayList<Integer>(Arrays.asList(4, 5))));
	}

	// 中三直選复式
	public static int sxwfz3fs(String bet, String kj) {
		return fs(bet, kj.substring(2, 7));
	}

	// 中三直選单式
	public static int sxwfz3ds(String bet, String kj) {
		return ds(bet, kj.substring(2, 7));
	}

	// 后三复式
	public static int sxwfH3f(String bet, String kj) {
		return fs(bet, removeFromList(kj, ",", new ArrayList<Integer>(Arrays.asList(1, 2))));
	}

	// 后三单式
	public static int sxwfH3d(String bet, String kj) {
		return ds(bet, removeFromList(kj, ",", new ArrayList<Integer>(Arrays.asList(1, 2))));
	}

	// 任选三复式
	public static int sxwfR3f(String bet, String kj) {
		String[] betData = bet.split(",");
		String newKj = kj;
		for (int i = 0; i < betData.length; i++) {
			if ("-".equals(betData[i])) {
				newKj = replaceList(newKj, "-", i + 1, null);
			}
		}
		return fs(bet, newKj);
	}

	// 任选三单式
	public static int sxwfR3d(String bet, String kj) {
		String[] betData = bet.substring(0, 9).split(",");
		String newKj = kj;
		for (int i = 0; i < betData.length; i++) {
			if ("-".equals(betData[i])) {
				newKj = replaceList(newKj, "-", i + 1, null);
			}
		}
		return ds(bet, newKj);
	}

	// 前三和值尾数
	public static int sxq3hzws(String bet, String kj) {
		String[] kjData = kj.substring(0, 5).split(",");
		String[] betData = bet.trim().split(" ");
		String bet2 = "";
		int m = Integer.parseInt(kjData[0]) + Integer.parseInt(kjData[1]) + Integer.parseInt(kjData[2]);
		int g;
		if (m < 10) {
			g = m;
		} else {
			g = m % 10;
		}
		for (int i = 0; i < betData.length; i++) {
			if (g == Integer.parseInt(betData[i])) {
				bet2 = bet2 + betData[i];
				break;
			}
		}
		return bet2.length();
	}

	// 后三和值尾数
	public static int sxh3hzws(String bet, String kj) {
		String[] kjData = kj.substring(4, 9).split(",");
		String[] betData = bet.split(" ");
		String bet2 = "";
		int m = Integer.parseInt(kjData[0]) + Integer.parseInt(kjData[1]) + Integer.parseInt(kjData[2]);
		int g;
		if (m < 10) {
			g = m;
		} else {
			g = m % 10;
		}

		for (int i = 0; i < betData.length; i++) {
			if (g == Integer.parseInt(betData[i])) {
				bet2 = bet2 + betData[i];
				break;
			}
		}
		return bet2.length();
	}

	// 中三和值尾数
	public static int sxz3hzws(String bet, String kj) {
		String[] kjData = kj.substring(2, 7).split(",");
		String[] betData = bet.split(" ");
		String bet2 = "";
		int m = Integer.parseInt(kjData[0]) + Integer.parseInt(kjData[1]) + Integer.parseInt(kjData[2]);
		int g;
		if (m < 10) {
			g = m;
		} else {
			g = m % 10;
		}

		for (int i = 0; i < betData.length; i++) {
			if (g == Integer.parseInt(betData[i])) {
				bet2 = bet2 + betData[i];
				break;
			}
		}
		return bet2.length();
	}

	// {{{ 三星组选

	// 前三组三
	public static int sxzxQ3z3(String bet, String kj) {
		return z3(bet, kj.substring(0, 5));
	}

	// 前三组六
	public static int sxzxQ3z6(String bet, String kj) {
		return z6(bet, kj.substring(0, 5));
	}

	// 中三组三
	public static int sxzxz3z3(String bet, String kj) {
		return z3(bet, kj.substring(2, 7));
	}

	// 中三组六
	public static int sxzxz3z6(String bet, String kj) {
		return z6(bet, kj.substring(2, 7));
	}

	// 中三混合组选
	public static int sxzxZ3h(String bet, String kj) {
		return 0;
	}

	// 前三混合组选
	public static int sxzxQ3h(String bet, String kj) {
		return 0;
	}

	// 后三组三
	public static int sxzxH3z3(String bet, String kj) {
		return z3(bet, kj.substring(4, 9));
	}

	// 后三组六
	public static int sxzxH3z6(String bet, String kj) {
		return z6(bet, kj.substring(4, 9));
	}

	// 后三混合组选
	public static int sxzxH3h(String bet, String kj) {
		return 0;
	}

	// 任三组三
	public static int sxzxR3z3(String bet, String kj, Integer w) {
		String[] kjData = kj.split(",");
		String newKj = "";

		int[] matchData = { 16, 8, 4, 2, 1 };
		boolean delLast = false;
		for (int i = 0; i < matchData.length; i++) {
			if ((w & matchData[i]) == 0) {
				kjData[i] = "del";
			}
		}
		for (int i = 0; i < kjData.length; i++) {
			if (!"del".equals(kjData[i])) {
				newKj += kjData[i] + ",";
				delLast = true;
			}
		}
		if (delLast) {
			newKj = newKj.substring(0, newKj.length() - 1);
		}
		return z3(bet, newKj);
	}

	// 任三组六
	public static int sxzxR3z6(String bet, String kj, Integer w) {
		String[] kjData = kj.split(",");
		String newKj = "";

		int[] matchData = { 16, 8, 4, 2, 1 };
		boolean delLast = false;
		for (int i = 0; i < matchData.length; i++) {
			if ((w & matchData[i]) == 0) {
				kjData[i] = "del";
			}
		}
		for (int i = 0; i < kjData.length; i++) {
			if (!"del".equals(kjData[i])) {
				newKj += kjData[i] + ",";
				delLast = true;
			}
		}
		if (delLast) {
			newKj = newKj.substring(0, newKj.length() - 1);
		}
		return z6(bet, newKj);
	}

	// 任三混合组
	public static int sxzxR3h(String bet, String kj, Integer w) {
		return 0;
	}

	// 后三组选和值
	public static int sxzxH3hz(String bet, String kj) {
		String newKj = kj.substring(4, 9);
		String[] kjData = newKj.split(",");
		String[] betData = bet.split(",");

		String bz = "";
		for (int i = 0; i < kjData.length; i++) {
			bz = bz + kjData[i];
		}

		// String reg = "/(\\d),\\1,\\1/";
		// if (bz.matches(reg)) {
		// return 0;
		// }

		String[] bzData = bz.split(",");
		for (int i = 0; i < bzData.length - 2; i++) {
			int kj_0 = Integer.parseInt(bzData[i]);
			int kj_1 = Integer.parseInt(bzData[i + 1]);
			int kj_2 = Integer.parseInt(bzData[i + 2]);
			if (kj_0 == kj_1 && kj_0 == kj_2) {
				return 0;
			}
		}
		int m = Integer.parseInt(kjData[0]) + Integer.parseInt(kjData[1]) + Integer.parseInt(kjData[2]);

		for (int i = 0; i < betData.length; i++) {
			int tmpBet = Integer.parseInt(betData[i]);
			if (m == tmpBet) {
				if (isRepeat(new ArrayList<String>(Arrays.asList(kjData)))) {
					return 2;
				} else {
					return 1;
				}
			}
		}
		return 0;
	}

	// 中三组选和值
	public static int sxzxz3hz(String bet, String kj) {
		String newKj = kj.substring(2, 7);
		String[] kjData = newKj.split(",");
		String[] betData = bet.split(",");

		String bz = "";
		for (int i = 0; i < kjData.length; i++) {
			bz = bz + kjData[i];
		}

		// String reg = "/(\\d),\\1,\\1/";
		// if (bz.matches(reg)) {
		// return 0;
		// }
		String[] bzData = bz.split(",");
		for (int i = 0; i < bzData.length - 2; i++) {
			int kj_0 = Integer.parseInt(bzData[i]);
			int kj_1 = Integer.parseInt(bzData[i + 1]);
			int kj_2 = Integer.parseInt(bzData[i + 2]);
			if (kj_0 == kj_1 && kj_0 == kj_2) {
				return 0;
			}
		}
		int m = Integer.parseInt(kjData[0]) + Integer.parseInt(kjData[1]) + Integer.parseInt(kjData[2]);

		for (int i = 0; i < betData.length; i++) {
			int tmpBet = Integer.parseInt(betData[i]);
			if (m == tmpBet) {
				if (isRepeat(new ArrayList<String>(Arrays.asList(kjData)))) {
					return 2;
				} else {
					return 1;
				}
			}
		}
		return 0;
	}

	// 前三组选和值
	public static int sxzxq3hz(String bet, String kj) {
		String newKj = kj.substring(0, 5);
		String[] kjData = newKj.split(",");
		String[] betData = bet.split(",");

		String bz = "";
		for (int i = 0; i < kjData.length; i++) {
			bz = bz + kjData[i];
		}

		// String reg = "/(\\d),\\1,\\1/";
		// if (bz.matches(reg)) {
		// return 0;
		// }
		String[] bzData = bz.split(",");
		for (int i = 0; i < bzData.length - 2; i++) {
			int kj_0 = Integer.parseInt(bzData[i]);
			int kj_1 = Integer.parseInt(bzData[i + 1]);
			int kj_2 = Integer.parseInt(bzData[i + 2]);
			if (kj_0 == kj_1 && kj_0 == kj_2) {
				return 0;
			}
		}
		int m = Integer.parseInt(kjData[0]) + Integer.parseInt(kjData[1]) + Integer.parseInt(kjData[2]);

		for (int i = 0; i < betData.length; i++) {
			int tmpBet = Integer.parseInt(betData[i]);
			if (m == tmpBet) {
				if (isRepeat(new ArrayList<String>(Arrays.asList(kjData)))) {
					return 2;
				} else {
					return 1;
				}
			}
		}
		return 0;
	}

	// 中三特殊号码
	public static int sxzxz3ts(String bet, String kj) {
		String newKj = kj.substring(2, 7);
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(newKj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		String[] betData = bet.split(",");
		int count = 0;
		for (int i = 0; i < kjData.size() - 2; i++) {
			int ki_0 = Integer.parseInt(kjData.get(i));
			int ki_1 = Integer.parseInt(kjData.get(i + 1));
			int ki_2 = Integer.parseInt(kjData.get(i + 2));
			for (int j = 0; j < betData.length; j++) {
				if ((ki_0 == ki_1) && (ki_1 == ki_2)) {
					if (betData[j].indexOf("豹子") != -1) {
						count += 27;
					}
				}
				if ((ki_0 == ki_1) || (ki_0 == ki_2) || (ki_2 == ki_1)) {
					if (betData[j].indexOf("对子") != -1) {
						count += 1;
					}
				}
				if ((ki_0 == ki_1 - 1) && (ki_1 == ki_2 - 1)) {
					if (betData[j].indexOf("顺子") != -1) {
						count += 4;
					}
				}
			}
		}
		return count;
	}

	// 后三特殊号码
	public static int sxzxH3ts(String bet, String kj) {
		String newKj = kj.substring(4, 9);
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(newKj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		String[] betData = bet.split(",");
		int count = 0;
		for (int i = 0; i < kjData.size() - 2; i++) {
			int ki_0 = Integer.parseInt(kjData.get(i));
			int ki_1 = Integer.parseInt(kjData.get(i + 1));
			int ki_2 = Integer.parseInt(kjData.get(i + 2));
			for (int j = 0; j < betData.length; j++) {
				if ((ki_0 == ki_1) && (ki_1 == ki_2)) {
					if (betData[j].indexOf("豹子") != -1) {
						count += 27;
					}
				}
				if ((ki_0 == ki_1) || (ki_0 == ki_2) || (ki_2 == ki_1)) {
					if (betData[j].indexOf("对子") != -1) {
						count += 1;
					}
				}
				if ((ki_0 == ki_1 - 1) && (ki_1 == ki_2 - 1)) {
					if (betData[j].indexOf("顺子") != -1) {
						count += 4;
					}
				}
			}
		}
		return count;
	}

	// 前三特殊号码
	public static int sxzxq3ts(String bet, String kj) {
		String newKj = kj.substring(0, 5);
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(newKj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		String[] betData = bet.split(",");
		int count = 0;
		for (int i = 0; i < kjData.size() - 2; i++) {
			int ki_0 = Integer.parseInt(kjData.get(i));
			int ki_1 = Integer.parseInt(kjData.get(i + 1));
			int ki_2 = Integer.parseInt(kjData.get(i + 2));
			for (int j = 0; j < betData.length; j++) {
				if ((ki_0 == ki_1) && (ki_1 == ki_2)) {
					if (betData[j].indexOf("豹子") != -1) {
						count += 27;
					}
				}
				if ((ki_0 == ki_1) || (ki_0 == ki_2) || (ki_2 == ki_1)) {
					if (betData[j].indexOf("对子") != -1) {
						count += 1;
					}
				}
				if ((ki_0 == ki_1 - 1) && (ki_1 == ki_2 - 1)) {
					if (betData[j].indexOf("顺子") != -1) {
						count += 4;
					}
				}
			}
		}
		return count;
	}

	// 前三直选跨度
	public static int sxzxq3kd(String bet, String kj) {
		String newKj = kj.substring(0, 5);
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(newKj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		String[] betData = bet.split(",");
		int m = Integer.parseInt(kjData.get(2)) - Integer.parseInt(kjData.get(0));
		for (int i = 0; i < betData.length; i++) {
			if (Integer.parseInt(betData[i]) == m) {
				return 1;
			}
		}
		return 0;
	}

	// 中三直选跨度
	public static int sxzxz3kd(String bet, String kj) {
		String newKj = kj.substring(2, 7);
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(newKj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		String[] betData = bet.split(",");
		int m = Integer.parseInt(kjData.get(2)) - Integer.parseInt(kjData.get(0));
		for (int i = 0; i < betData.length; i++) {
			if (Integer.parseInt(betData[i]) == m) {
				return 1;
			}
		}
		return 0;
	}

	// 后三直选跨度
	public static int sxzxH3kd(String bet, String kj) {
		String newKj = kj.substring(4, 9);
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(newKj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		String[] betData = bet.split(",");
		int m = Integer.parseInt(kjData.get(2)) - Integer.parseInt(kjData.get(0));
		for (int i = 0; i < betData.length; i++) {
			if (Integer.parseInt(betData[i]) == m) {
				return 1;
			}
		}
		return 0;
	}

	// 新增
	public static int rxwf11R2d(String bet, String kj) {
		String[] bets = bet.substring(0, 14).split(",");
		String newKj = "";
		for (int i = 0; i < bets.length; i++) {
			if ("-".equals(bets[i])) {
				newKj = replaceList(kj, "-", i + 1, null);
			}
		}
		return ds(bet, newKj);
	}

	// {{{ 二星直选

	// 前二复式
	public static int rxwfQ2f(String bet, String kj) {
		return fs(bet, kj.substring(0, 3));
	}

	// 前二单式
	public static int rxwfQ2d(String bet, String kj) {
		return ds(bet, kj.substring(0, 3));
	}

	// 后二复式
	public static int rxwfH2f(String bet, String kj) {
		return fs(bet, kj.substring(6, 9));
	}

	// 后二单式
	public static int rxwfH2d(String bet, String kj) {
		return ds(bet, kj.substring(6, 9));
	}

	// 后二和值
	public static int ssch2zhixhz(String bet, String kj) {
		int count = 0;
		String[] kjData = kj.split(",");
		String[] betData = bet.split(",");
		int m = Integer.parseInt(kjData[3]) + Integer.parseInt(kjData[4]);
		for (int i = 0; i < betData.length; i++) {
			if (("" + m).equals(betData[i])) {
				count++;
			}
		}
		return count;
	}

	// 前二和值
	public static int sscq2zhixhz(String bet, String kj) {
		int count = 0;
		String[] kjData = kj.split(",");
		String[] betData = bet.split(",");
		int m = Integer.parseInt(kjData[0]) + Integer.parseInt(kjData[1]);
		for (int i = 0; i < betData.length; i++) {
			if (("" + m).equals(betData[i])) {
				count++;
			}
		}
		return count;
	}

	// 前二组选和值
	public static int sscq2zhuxhz(String bet, String kj) {
		int count = 0;
		String[] kjData = kj.split(",");
		String[] betData = bet.split(",");

		if (kjData[0].equals(kjData[1])) {
			return 0;
		}
		int m = Integer.parseInt(kjData[0]) + Integer.parseInt(kjData[1]);

		for (int i = 0; i < betData.length; i++) {
			if (("" + m).equals(betData[i])) {
				count++;
			}
		}
		return count;
	}

	// 后二组选和值
	public static int ssch2zhuxhz(String bet, String kj) {
		int count = 0;
		String[] kjData = kj.split(",");
		String[] betData = bet.split(",");

		if (kjData[3].equals(kjData[4])) {
			return 0;
		}
		int m = Integer.parseInt(kjData[3]) + Integer.parseInt(kjData[4]);

		for (int i = 0; i < betData.length; i++) {
			if (("" + m).equals(betData[i])) {
				count++;
			}
		}
		return count;
	}

	// 任选二复式
	public static int rxwfR2f(String bet, String kj) {
		return sxwfR3f(bet, kj);
	}

	// 任选二单式
	public static int rxwfR2d(String bet, String kj) {
		return sxwfR3d(bet, kj);
	}

	// {{{ 二星组选

	// 前二组复式
	public static int rxzxQ2f(String bet, String kj) {
		return z2f(bet, kj.substring(0, 3));
	}

	// 前二组单式
	public static int rxzxQ2d(String bet, String kj) {
		return z2d(bet, kj.substring(0, 3));
	}

	// 后二组复式
	public static int rxzxH2f(String bet, String kj) {
		return z2f(bet, kj.substring(6, 9));
	}

	// 后二组单式
	public static int rxzxH2d(String bet, String kj) {
		return z2d(bet, kj.substring(6, 9));
	}

	// 任选二组选复式
	public static int rxzxR2f(String bet, String kj, Integer w) {
		String[] kjData = kj.split(",");
		String newKj = "";

		int[] matchData = { 16, 8, 4, 2, 1 };
		boolean delLast = false;
		for (int i = 0; i < matchData.length; i++) {
			if ((w & matchData[i]) == 0) {
				kjData[i] = "del";
			}
		}
		for (int i = 0; i < kjData.length; i++) {
			if (!"del".equals(kjData[i])) {
				newKj += kjData[i] + ",";
				delLast = true;
			}
		}
		if (delLast) {
			newKj = newKj.substring(0, newKj.length() - 1);
		}

		return z2f(bet, newKj);
	}

	// 任选二组选单式
	public static int rxzxR2d(String bet, String kj, Integer w) {
		String[] kjData = kj.split(",");
		String newKj = "";

		int[] matchData = { 16, 8, 4, 2, 1 };
		boolean delLast = false;
		for (int i = 0; i < matchData.length; i++) {
			if ((w & matchData[i]) == 0) {
				kjData[i] = "del";
			}
		}
		for (int i = 0; i < kjData.length; i++) {
			if (!"del".equals(kjData[i])) {
				newKj += kjData[i] + ",";
				delLast = true;
			}
		}
		if (delLast) {
			newKj = newKj.substring(0, newKj.length() - 1);
		}

		String[] betData = bet.split("\\|");
		String newBet = "";
		boolean delLast_newBet = false;
		for (int i = 0; i < betData.length; i++) {
			String[] tmpBet = betData[i].split(",");
			String newtmpBet = "";
			for (int j = 0; j < matchData.length; j++) {
				if ((w & matchData[j]) == 0) {
					tmpBet[j] = "del";
				}
			}
			boolean delLast_tmpBel = false;
			for (int j = 0; j < tmpBet.length; j++) {
				if (!"del".equals(tmpBet[j])) {
					newtmpBet += tmpBet[j] + ",";
					delLast_tmpBel = true;
				}
			}
			if (delLast_tmpBel) {
				newtmpBet = newtmpBet.substring(0, newtmpBet.length() - 1);
				newBet = newBet + newtmpBet + "|";
				delLast_newBet = true;
			}

		}
		if (delLast_newBet) {
			newBet = newBet.substring(0, newBet.length() - 1);
		}
		return z2d(newBet, newKj);
	}

	// 前二组包胆
	public static int rxzxQ2bd(String bet, String kj) {
		String newKj = kj.substring(0, 3);
		String[] kjData = newKj.split(",");
		if (!kjData[0].equals(kjData[1])) {
			if (newKj.indexOf(bet) != -1) {
				return 9;
			}
		}
		return 0;
	}

	// 后二组包胆
	public static int rxzxH2bd(String bet, String kj) {
		String newKj = kj.substring(6, 9);
		String[] kjData = newKj.split(",");
		if (!kjData[0].equals(kjData[1])) {
			if (newKj.indexOf(bet) != -1) {
				return 9;
			}
		}
		return 0;
	}

	// {{{ 五星定位胆
	public static int dwd5x(String bet, String kj) {
		String[] kjData = kj.split(",");
		String[] betData = bet.split(",");
		int count = 0;
		for (int i = 0; i < betData.length; i++) {
			if (betData[i].length() > 1) {
				String[] tmpData = betData[i].split("");
				for (int j = 0; j < tmpData.length; j++) {
					if (tmpData[j].equals(kjData[i])) {
						count++;
					}
				}
			} else {
				if (betData[i].equals(kjData[i])) {
					count++;
				}
			}
		}
		return count;
	}

	// {{{ 十星定位胆
	public static int dwd10x(String bet, String kj) {
		String[] kjData = kj.split(",");
		String[] betData = bet.split(",");
		int count = 0;
		for (int i = 0; i < betData.length; i++) {
			if (betData[i].length() > 2) {
				String[] tmpData = betData[i].split(" ");
				for (int j = 0; j < tmpData.length; j++) {
					if (tmpData[j].equals(kjData[i])) {
						count++;
					}
				}
			} else {
				if (betData[i].equals(kjData[i])) {
					count++;
				}
			}
		}
		return count;
	}

	// {{{ 不定胆

	// 后三不定胆
	public static int bddH3(String bet, String kj) {
		String newKj = kj.substring(4, 9);
		String[] betData = bet.split("");
		int count = 0;
		for (int i = 0; i < betData.length; i++) {
			if (newKj.indexOf(betData[i]) != -1) {
				count++;
			}
		}
		return count;
	}

	// 前三不定胆
	public static int bddQ3(String bet, String kj) {
		String newKj = kj.substring(0, 5);
		String[] betData = bet.split("");
		int count = 0;
		for (int i = 0; i < betData.length; i++) {
			if (newKj.indexOf(betData[i]) != -1) {
				count++;
			}
		}
		return count;
	}

	// 中三不定胆
	public static int bddZ3(String bet, String kj) {
		String newKj = kj.substring(2, 7);
		String[] betData = bet.split("");
		int count = 0;
		for (int i = 0; i < betData.length; i++) {
			if (newKj.indexOf(betData[i]) != -1) {
				count++;
			}
		}
		return count;
	}

	// 任选三不定胆
	public static int bddR3(String bet, String kj, Integer w) {
		String[] kjData = kj.split(",");
		String newKj = "";

		int[] matchData = { 16, 8, 4, 2, 1 };
		boolean delLast = false;
		for (int i = 0; i < matchData.length; i++) {
			if ((w & matchData[i]) == 0) {
				kjData[i] = "del";
			}
		}
		for (int i = 0; i < kjData.length; i++) {
			if (!"del".equals(kjData[i])) {
				newKj += kjData[i] + ",";
				delLast = true;
			}
		}
		if (delLast) {
			newKj = newKj.substring(0, newKj.length() - 1);
		}

		int count = 0;
		String[] betData = bet.split("");
		for (int i = 0; i < betData.length; i++) {
			if (newKj.indexOf(betData[i]) != -1) {
				count++;
			}
		}
		return count;
	}

	// 前三二码 二码不定位
	public static int bdwQ32(String bet, String kj) {
		ArrayList<String> newKj = filterArray(new ArrayList<String>(Arrays.asList((kj.substring(0, 5).split(",")))));
		ArrayList<String> betData = new ArrayList<String>(Arrays.asList(bet.split(" ")));
		if (betData.size() < newKj.size()) {
			int t = Sames(newKj, betData);
			if (t >= 2) {
				return Combination(t, 2);
			}
		} else {
			int t = Sames(betData, newKj);
			if (t >= 2) {
				return Combination(t, 2);
			}
		}
		return 0;
	}

	// 后三二码
	public static int bdwH32(String bet, String kj) {
		ArrayList<String> newKj = filterArray(new ArrayList<String>(Arrays.asList((kj.substring(4, 9).split(",")))));
		ArrayList<String> betData = new ArrayList<String>(Arrays.asList(bet.split(" ")));
		if (betData.size() < newKj.size()) {
			int t = Sames(newKj, betData);
			if (t >= 2) {
				return Combination(t, 2);
			}
		} else {
			int t = Sames(betData, newKj);
			if (t >= 2) {
				return Combination(t, 2);
			}
		}
		return 0;
	}

	// 五星三码
	public static int bdw5x3m(String bet, String kj) {
		ArrayList<String> newKj = filterArray(new ArrayList<String>(Arrays.asList((kj.split(",")))));
		ArrayList<String> betData = new ArrayList<String>(Arrays.asList(bet.split(" ")));
		if (betData.size() < newKj.size()) {
			int t = Sames(newKj, betData);
			if (t >= 3) {
				return Combination(t, 3);
			}
		} else {
			int t = Sames(betData, newKj);
			if (t >= 3) {
				return Combination(t, 3);
			}
		}
		return 0;
	}

	// 五星二码
	public static int bdw5x2m(String bet, String kj) {
		ArrayList<String> newKj = filterArray(new ArrayList<String>(Arrays.asList((kj.split(",")))));
		ArrayList<String> betData = new ArrayList<String>(Arrays.asList(bet.split(" ")));

		if (betData.size() < newKj.size()) {
			int t = Sames(newKj, betData);
			if (t >= 2) {
				return Combination(t, 2);
			}
		} else {
			int t = Sames(betData, newKj);
			if (t >= 2) {
				return Combination(t, 2);
			}
		}
		return 0;
	}

	// 四星二码
	public static int bdw4x2m(String bet, String kj) {
		ArrayList<String> newKj = filterArray(new ArrayList<String>(Arrays.asList((kj.substring(2, 9).split(",")))));
		ArrayList<String> betData = new ArrayList<String>(Arrays.asList(bet.split(" ")));

		if (betData.size() < newKj.size()) {
			int t = Sames(newKj, betData);
			if (t >= 2) {
				return Combination(t, 2);
			}
		} else {
			int t = Sames(betData, newKj);
			if (t >= 2) {
				return Combination(t, 2);
			}
		}
		return 0;
	}

	// 四星一码
	public static int bdw4x1m(String bet, String kj) {
		ArrayList<String> newKj = filterArray(new ArrayList<String>(Arrays.asList((kj.substring(2, 9).split(",")))));
		ArrayList<String> betData = new ArrayList<String>(Arrays.asList(bet.split(" ")));

		if (betData.size() < newKj.size()) {
			int t = Sames(newKj, betData);
			if (t >= 1) {
				return t;
			}
		} else {
			int t = Sames(betData, newKj);
			if (t >= 1) {
				return t;
			}
		}
		return 0;
	}

	// 快三大小单双
	public static int fcsdxds(String bet, String kj) {
		return sdxds(bet, kj);
	}

	public static int sdxds(String bet, String data) {
		String[] kjData = data.split(",");
		int sum = 0;
		for (int i = 0; i < kjData.length; i++) {
			sum = sum + Integer.parseInt(kjData[i]);
		}
		// 判断单双
		if (sum % 2 == 0) {
			if (bet.indexOf("双") != -1)
				return 1;
		} else {
			if (bet.indexOf("单") != -1)
				return 1;
		}

		// 小于10是小
		if (sum <= 10) {
			// 只是小
			if (bet.indexOf("小") != -1)
				return 1;
		} else {
			if (bet.indexOf("大") != -1)
				return 1;
		}
		return 0;
	}

	// 大小单双

	// 前二大小单双
	public static int dsQ2(String bet, String kj) {
		return dxds(bet, kj.substring(0, 3));
	}

	// 前三大小单双
	public static int dsQ3(String bet, String kj) {
		return dxds(bet, kj.substring(0, 5));
	}

	// 后二大小单双
	public static int dsH2(String bet, String kj) {
		return dxds(bet, kj.substring(6, 9));
	}

	// 后三大小单双
	public static int dsH3(String bet, String kj) {
		return dxds(bet, kj.substring(4, 9));
	}

	// 任选二大小单双
	public static int dsR2(String bet, String kj, Integer w) {
		String[] kjData = kj.split(",");
		String[] betData = bet.split(",");

		String newBet = "";
		String newKj = "";

		boolean delLast = false;
		for (int i = 0; i < betData.length; i++) {
			if ("-".equals(betData[i])) {
				kjData[i] = "del";
			} else {
				newBet += betData[i] + ",";
				delLast = true;
			}
		}
		if (delLast) {
			newBet = newBet.substring(0, newBet.length() - 1);
		}

		delLast = false;
		for (int i = 0; i < kjData.length; i++) {
			if ("del".equals(kjData[i])) {
			} else {
				newKj += kjData[i] + ",";
				delLast = true;
			}
		}
		if (delLast) {
			newKj = newKj.substring(0, newKj.length() - 1);
		}
		return dxds(bet, kj);
	}

	// 时时彩结束

	// 福彩3D

	// 三星直选－复式
	public static int fc3dFs(String bet, String kj) {
		return fs(bet, kj);
	}

	// 三星直选－单式
	public static int fc3dDs(String bet, String kj) {
		return ds(bet, kj);
	}

	public static int fc3dhzq(String bet, String kj) {
		String newKj = kj.substring(0, 5);
		return fc3dhz(bet, newKj);
	}

	public static int fc3dhzz(String bet, String kj) {
		String newKj = kj.substring(2, 7);
		return fc3dhz(bet, newKj);
	}

	public static int fc3dhzh(String bet, String kj) {
		String newKj = kj.substring(4, 9);
		return fc3dhz(bet, newKj);
	}

	// 三星直选和值
	public static int fc3dhz(String bet, String kj) {
		String[] betData = bet.split(",");
		String[] kjData = kj.split(",");
		int a = Integer.parseInt(kjData[0]) + Integer.parseInt(kjData[1]) + Integer.parseInt(kjData[2]);
		for (int i = 0; i < betData.length; i++) {
			if (betData[i].equals("" + a)) {
				return 1;
			}
		}
		return 0;
	}

	// 三星组选－组三
	public static int fc3dZ3(String bet, String kj) {
		return z3(bet, kj);
	}

	// 三星组选－组六
	public static int fc3dZ6(String bet, String kj) {
		return z6(bet, kj);
	}

	// 三星组选和值
	public static int fc3d_zxhz(String bet, String kj) {
		String[] kjData = kj.split(",");
		// String kkjj = kj;
		// String reg = "/(\\d),\\1,\\1/";
		// if (kkjj.matches(reg)) {
		// return 0;
		// }

		for (int i = 0; i < kjData.length - 2; i++) {
			int kj_0 = Integer.parseInt(kjData[i]);
			int kj_1 = Integer.parseInt(kjData[i + 1]);
			int kj_2 = Integer.parseInt(kjData[i + 2]);
			if (kj_0 == kj_1 && kj_0 == kj_2) {
				return 0;
			}
		}

		int a = Integer.parseInt(kjData[0]) + Integer.parseInt(kjData[1]) + Integer.parseInt(kjData[2]);

		if (bet.indexOf("" + a) != -1) {
			if (isRepeat(new ArrayList<String>(Arrays.asList(kjData)))) {
				return 2;
			} else {
				return 1;
			}
		}
		return 0;
	}

	// 二星直选－前二单式
	public static int fc3dQ2d(String bet, String kj) {
		return rxwfQ2d(bet, kj);
	}

	// 二星直选－前二复式
	public static int fc3dQ2f(String bet, String kj) {
		return rxwfQ2f(bet, kj);
	}

	// 二星直选－后二单式
	public static int fc3dH2d(String bet, String kj) {
		return ds(bet, kj.substring(2, 5));
	}

	// 二星直选－后二复式
	public static int fc3dH2f(String bet, String kj) {
		return fs(bet, kj.substring(2, 5));
	}

	// 二星组选－前二组选单式
	public static int fc3dZQ2d(String bet, String kj) {
		return rxzxQ2d(bet, kj);
	}

	// 二星组选－前二组选复式
	public static int fc3dZQ2f(String bet, String kj) {
		return rxzxQ2f(bet, kj);
	}

	// 二星组选－后二组选单式
	public static int fc3dZH2d(String bet, String kj) {
		return z2d(bet, kj.substring(2, 5));
	}

	// 二星组选－后二组选复式
	public static int fc3dZH2f(String bet, String kj) {
		return z2f(bet, kj.substring(2, 5));
	}

	// 三星定位胆
	public static int fc3d3xdw(String bet, String kj) {
		return dwd5x(bet, kj);
	}

	// 不定胆
	public static int fc3dbdd(String bet, String kj) {
		return bddQ3(bet, kj);
	}

	// 后二大小单双
	public static int fc3dH2dxds(String bet, String kj) {
		return dxds(bet, kj.substring(2, 5));
	}

	// 任选二大小单双
	public static int fc3dR2dxds(String bet, String kj, Integer w) { // w 為 1. 2. 4. 8 等等的bit 位數
		String[] kjData = kj.split(",");
		String newKj = "";
		int[] matchData = { 4, 2, 1 };
		boolean delLast = false;
		for (int i = 0; i < matchData.length; i++) {
			if ((w & matchData[i]) == 0) {
				kjData[i] = "del";
			}
		}
		for (int i = 0; i < kjData.length; i++) {
			if (!"del".equals(kjData[i])) {
				newKj += kjData[i] + ",";
				delLast = true;
			}
		}
		if (delLast) {
			newKj = newKj.substring(0, newKj.length() - 1);
		}
		return dxds(bet, newKj);
	}

	// 趣味玩法 一帆风顺
	public static int qwwfyffs(String bet, String kj) {
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		ArrayList<String> betData = new ArrayList<String>(Arrays.asList(bet.split(" ")));

		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		kjData = filterArray(kjData);
		if (kjData.size() >= betData.size()) {
			int t = Sames(kjData, betData);
			if (t >= 1) {
				return t;
			} else {
				return 0;
			}
		} else {
			int t = Sames(betData, kjData);
			if (t >= 1) {
				return t;
			} else {
				return 0;
			}
		}
	}

	// 趣味玩法 好事成双
	public static int qwwfhscs(String bet, String kj) {
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		ArrayList<String> betData = new ArrayList<String>(Arrays.asList(bet.split(" ")));

		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});

		String kjs = "";
		for (int i = 0; i < kjData.size() - 1; i++) {
			String nowStr = kjData.get(i);
			if (nowStr.equals(kjData.get(i + 1))) {
				kjs += nowStr;
			}
		}
		kjData = new ArrayList<String>(Arrays.asList(kjs.split("")));
		if (Sames(betData, kjData) >= 1) {
			return 1;
		} else {
			return 0;
		}
	}

	// 趣味玩法 三星报喜
	public static int qwwfsxbx(String bet, String kj) {
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		ArrayList<String> betData = new ArrayList<String>(Arrays.asList(bet.split(" ")));

		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});

		String kjs = "";
		for (int i = 0; i < kjData.size() - 2; i++) {
			String nowStr = kjData.get(i);
			if (nowStr.equals(kjData.get(i + 1)) && nowStr.equals(kjData.get(i + 2))) {
				kjs += nowStr;
				break;
			}
		}
		kjData = new ArrayList<String>(Arrays.asList(kjs.split("")));
		if (Sames(betData, kjData) == 1) {
			return 1;
		}
		return 0;
	}

	// 趣味玩法 四季发财
	public static int qwwfsjfc(String bet, String kj) {
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		ArrayList<String> betData = new ArrayList<String>(Arrays.asList(bet.split(" ")));

		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});

		String kjs = "";
		for (int i = 0; i < kjData.size() - 3; i++) {
			String nowStr = kjData.get(i);
			if (nowStr.equals(kjData.get(i + 1)) && nowStr.equals(kjData.get(i + 2))
					&& nowStr.equals(kjData.get(i + 3))) {
				kjs += nowStr;
				break;
			}
		}
		kjData = new ArrayList<String>(Arrays.asList(kjs.split("")));
		if (Sames(betData, kjData) == 1) {
			return 1;
		}
		return 0;
	}

	// 趣味玩法 前三趣味二星
	public static int qwwfq3qw2x(String bet, String kj) {
		String newKj = kj.substring(0, 5);
		String[] kjData = newKj.split(",");
		String[] betData = bet.split(",");

		if (betData[1].indexOf(kjData[1]) != -1) {
			if (betData[2].indexOf(kjData[2]) != -1) {
				if (Integer.parseInt(kjData[0]) < 5) {
					if (betData[0].indexOf("小") != -1) {
						return 2;
					} else {
						return 1;
					}
				} else {
					if (betData[0].indexOf("大") != -1) {
						return 2;
					} else {
						return 1;
					}
				}
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// 趣味玩法 后三趣味二星
	public static int qwwfh3qw2x(String bet, String kj) {
		String newKj = kj.substring(4, 9);
		String[] kjData = newKj.split(",");
		String[] betData = bet.split(",");

		if (betData[1].indexOf(kjData[1]) != -1) {
			if (betData[2].indexOf(kjData[2]) != -1) {
				if (Integer.parseInt(kjData[0]) < 5) {
					if (betData[0].indexOf("小") != -1) {
						return 2;
					} else {
						return 1;
					}
				} else {
					if (betData[0].indexOf("大") != -1) {
						return 2;
					} else {
						return 1;
					}
				}
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// 趣味玩法 四码趣味三星
	public static int qwwf4mqw3x(String bet, String kj) {
		String newKj = kj.substring(2, 9);
		String[] kjData = newKj.split(",");
		String[] betData = bet.split(",");

		if (betData[1].indexOf(kjData[1]) != -1) {
			if (betData[2].indexOf(kjData[2]) != -1) {
				if (betData[3].indexOf(kjData[3]) != -1) {
					if (Integer.parseInt(kjData[0]) < 5) {
						if (betData[0].indexOf("小") != -1) {
							return 2;
						} else {
							return 1;
						}
					} else {
						if (betData[0].indexOf("大") != -1) {
							return 2;
						} else {
							return 1;
						}
					}
				} else {
					return 0;
				}
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// 趣味玩法 五码趣味三星
	public static int qwwf5mqw3x(String bet, String kj) {
		String[] kjData = kj.split(",");
		String[] betData = bet.split(",");

		if (betData[2].indexOf(kjData[2]) != -1) {
			if (betData[3].indexOf(kjData[3]) != -1) {
				if (betData[4].indexOf(kjData[4]) != -1) {
					if (Integer.parseInt(kjData[0]) < 5) {
						if (Integer.parseInt(kjData[1]) < 5) {
							if (betData[0].indexOf("小") != -1) {
								if (betData[1].indexOf("小") != -1) {
									return 8;
								} else {
									return 1;
								}
							} else {
								return 1;
							}
						}
					}
					if (Integer.parseInt(kjData[0]) >= 5) {
						if (Integer.parseInt(kjData[1]) >= 5) {
							if (betData[0].indexOf("大") != -1) {
								if (betData[1].indexOf("大") != -1) {
									return 8;
								} else {
									return 1;
								}
							} else {
								return 1;
							}
						}
					}
					if (Integer.parseInt(kjData[0]) >= 5) {
						if (Integer.parseInt(kjData[1]) < 5) {
							if (betData[0].indexOf("大") != -1) {
								if (betData[1].indexOf("小") != -1) {
									return 8;
								} else {
									return 1;
								}
							} else {
								return 1;
							}
						}
					}
					if (Integer.parseInt(kjData[0]) < 5) {
						if (Integer.parseInt(kjData[1]) >= 5) {
							if (betData[0].indexOf("小") != -1) {
								if (betData[1].indexOf("大") != -1) {
									return 8;
								} else {
									return 1;
								}
							} else {
								return 1;
							}
						}
					}
				} else {
					return 0;
				}
			} else {
				return 0;
			}
		} else {
			return 0;
		}
		return 0;
	}

	// {{{ 十一选五玩法
	// 任选一
	public static int gd11x5R1(String bet, String kj) {
		ArrayList<String> betData = new ArrayList<String>(Arrays.asList(bet.split(" ")));
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		if (betData.size() > kjData.size()) {
			return Sames(betData, kjData);
		} else {
			return Sames(kjData, betData);
		}
	}

	// 任选一单式
	public static int gd11x5R1ds(String bet, String kj) {
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		for (int i = 0; i < kjData.size(); i++) {
			if (kjData.get(i).indexOf(bet) != -1) {
				return 1;
			}
		}
		return 0;
	}

	public static int gd11x5R2(String bet, String kj) {
		return rx(bet, kj, 2);
	}

	// 任选二单式
	public static int gd11x5R2ds(String bet, String kj) {
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		ArrayList<String> newBet = strCut(bet, 2);
		if (Sames(newBet, kjData) == 2)
			return 1;
		return 0;
	}

	public static int gd11x5R3(String bet, String kj) {
		return rx(bet, kj, 3);
	}

	// 任选三单式
	public static int gd11x5R3ds(String bet, String kj) {
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		ArrayList<String> newBet = strCut(bet, 2);
		if (Sames(newBet, kjData) == 3)
			return 1;
		return 0;
	}

	public static int gd11x5R4(String bet, String kj) {
		return rx(bet, kj, 4);
	}

	// 任选四单式
	public static int gd11x5R4ds(String bet, String kj) {
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		ArrayList<String> newBet = strCut(bet, 2);
		if (Sames(newBet, kjData) == 4)
			return 1;
		return 0;
	}

	public static int gd11x5R5(String bet, String kj) {
		return rx(bet, kj, 5);
	}

	// 任选五单式
	public static int gd11x5R5ds(String bet, String kj) {
		return gd11x5R8ds(bet, kj);
	}

	public static int gd11x5R6(String bet, String kj) {
		return rx(bet, kj, 6);
	}

	// 任选六单式
	public static int gd11x5R6ds(String bet, String kj) {
		return gd11x5R8ds(bet, kj);
	}

	public static int gd11x5R7(String bet, String kj) {
		return rx(bet, kj, 7);
	}

	// 任选七单式
	public static int gd11x5R7ds(String bet, String kj) {
		return gd11x5R8ds(bet, kj);
	}

	public static int gd11x5R8(String bet, String kj) {
		return rx(bet, kj, 8);
	}

	// 任选八单式
	public static int gd11x5R8ds(String bet, String kj) {
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		ArrayList<String> newBet = strCut(bet, 2);
		if (Sames(newBet, kjData) == 5)
			return 1;
		return 0;
	}

	public static int gd11x5R9(String bet, String kj) {
		return rx(bet, kj, 9);
	}

	public static int gd11x5R10(String bet, String kj) {
		return rx(bet, kj, 10);
	}

	// 前一直选
	public static int gd11x5Q1(String bet, String kj) {
		String[] kjData = kj.split(",");
		String[] betData = bet.split(" ");
		for (int i = 0; i < betData.length; i++) {
			if (Integer.parseInt(kjData[0]) == Integer.parseInt(betData[i])) {
				return 1;
			}
		}
		return 0;
	}

	// 前二直选
	public static int gd11x5Q2(String bet, String kj) {
		return qs(bet, kj, 2);
	}

	// 前二组选
	public static int gd11x5Q2z(String bet, String kj) {
		return zx(bet, kj.substring(0, 5));
	}

	// 前三直选
	public static int gd11x5Q3(String bet, String kj) {
		return qs(bet, kj, 3);
	}

	// 前三组选
	public static int gd11x5Q3z(String bet, String kj) {
		return zx(bet, kj.substring(0, 8));
	}

	// 前四组选
	public static int gd11x5Q4z(String bet, String kj) {
		return zx(bet, kj.substring(0, 11));
	}

	// 定位胆
	public static int gd11x5dwd(String bet, String kj) {
		String newKj = kj.substring(0, 8);
		String[] kjData = newKj.split(",");
		String[] betData = bet.split(",");
		String bets = "";

		if (betData[0].split(" ")[0].indexOf(kjData[0]) != -1)
			bets += kjData[0];
		if (betData[1].split(" ")[0].indexOf(kjData[1]) != -1)
			bets += kjData[1];
		if (betData[2].split(" ")[0].indexOf(kjData[2]) != -1)
			bets += kjData[2];
		return bets.length() / 2;
	}

	// 不定位
	public static int gd11x5bdw(String bet, String kj) {
		String newKj = kj.substring(0, 8);
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(newKj.split(",")));
		ArrayList<String> betData = new ArrayList<String>(Arrays.asList(bet.split(" ")));
		return Sames(kjData, betData);
	}

	// 趣味_猜中位
	public static int qwwfczw(String bet, String kj) {
		String[] betData = bet.split(" ");
		String zs = "";
		ArrayList<String> kjData = new ArrayList<String>(Arrays.asList(kj.split(",")));
		Collections.sort(kjData, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return (Integer.parseInt(o1) - Integer.parseInt(o2));
			}
		});
		for (int i = 0; i < betData.length; i++) {
			if (kjData.get(2).equals(betData[i])) {
				zs += betData[i];
			}
		}
		return zs.length() / 2;
	}

	// 趣味_定单双
	public static int qwwfdds(String bet, String kj) {
		String ds = "";
		String ss = "";
		String zs = "";
		String reg = "/[^0-9]/ig";
		String num = bet.replaceAll(reg, "");
		ArrayList<String> h = strCut(num, 2);

		String[] kjData = kj.split(",");

		for (int i = 0; i < kjData.length; i++) {
			if (Integer.parseInt(kjData[i]) % 2 == 0) {
				ss += kjData[i];
			} else {
				ds += kjData[i];
			}
		}
		double ds_num = ds.length();
		ds_num /= 2;
		ds = "" + ds_num;
		double ss_num = ss.length();
		ss_num /= 2;
		ss = "" + ss_num;

		int m = Integer.parseInt(ds + ss);

		for (int i = 0; i < h.size(); i++) {
			if (m == Integer.parseInt(h.get(i))) {
				zs += h.get(i);
			}
		}
		return zs.length() / 2;
	}

	// {{{ 快乐十分玩法
	// 任选一 选一数投
	public static int klsfR1B(String bet, String kj) {
		String[] betData = bet.split(" ");
		String newKj = kj.substring(0, 2);
		int count = 0;
		for (int i = 0; i < betData.length; i++) {
			if (newKj.indexOf(betData[i]) != -1) {
				count++;
			}
		}
		return count;
	}

	// 任选一 选一红投
	public static int klsfR1R(String bet, String kj) {
		return klsfR1B(bet, kj);
	}

	public static int klsfR2(String bet, String kj) {
		return rx(bet, kj, 2);
	}

	public static int klsfR3(String bet, String kj) {
		return rx(bet, kj, 3);
	}

	public static int klsfR4(String bet, String kj) {
		return rx(bet, kj, 4);
	}

	public static int klsfR5(String bet, String kj) {
		return rx(bet, kj, 5);
	}

	// 前二直选
	public static int klsfQ2(String bet, String kj) {
		return qs(bet, kj, 2);
	}

	// 前二组选
	public static int klsfQ2z(String bet, String kj) {
		return zx(bet, kj.substring(0, 5));
	}

	// 前三直选
	public static int klsfQ3(String bet, String kj) {
		return qs(bet, kj, 3);
	}

	// 前三组选
	public static int klsfQ3z(String bet, String kj) {
		return zx(bet, kj.substring(0, 8));
	}

	// {{{ 北京PK10玩法 1至10位开奖

	// 冠军
	public static int kjq1(String betData, String kjData) {
		return qs(betData, kjData, 1);
	}

	// 冠亚军
	public static int kjq2(String betData, String kjData) {
		return qs(betData, kjData, 2);
	}

	// 前三
	public static int kjq3(String betData, String kjData) {
		return qs(betData, kjData, 3);
	}

	// 定位胆 exports.dwd10x
	//
	public static int pk10lmdxds1(String betData, String kjData) {
		return dxds2(betData, kjData.substring(0, 2));
	}

	public static int pk10lmdxds2(String betData, String kjData) {
		return dxds2(betData, kjData.substring(3, 5));
	}

	public static int pk10lmdxds3(String betData, String kjData) {
		return dxds2(betData, kjData.substring(6, 8));
	}

	public static int pk10lmdxds4(String betData, String kjData) {
		return dxds2(betData, kjData.substring(9, 11));
	}

	public static int pk10lmdxds5(String betData, String kjData) {
		return dxds2(betData, kjData.substring(12, 14));
	}

	public static int pk10lmdxds6(String betData, String kjData) {
		return dxds2(betData, kjData.substring(15, 17));
	}

	public static int pk10lmdxds7(String betData, String kjData) {
		return dxds2(betData, kjData.substring(18, 20));
	}

	public static int pk10lmdxds8(String betData, String kjData) {
		return dxds2(betData, kjData.substring(21, 23));
	}

	public static int pk10lmdxds9(String betData, String kjData) {
		return dxds2(betData, kjData.substring(24, 26));
	}

	public static int pk10lmdxds10(String betData, String kjData) {
		return dxds2(betData, kjData.substring(27, 29));
	}

	public static int pk10lmdxds22(String bet, String kj) {
		String[] kjData = kj.split(",");
		String[] betData = bet.split("");
		int val = Integer.parseInt(kjData[0]) + Integer.parseInt(kjData[1]);
		int count = 0;

		for (int i = 0, l = betData.length; i < l; i++) {
			if ("大".equals(betData[i])) {
				if (val > 11 && val < 20)
					count += 1;
			} else if ("小".equals(betData[i])) {
				if (val > 2 && val < 12)
					count += 1;
			} else if ("单".equals(betData[i])) {
				if (val % 2 != 0)
					count += 1;
			} else if ("双".equals(betData[i])) {
				if (val % 2 == 0)
					count += 1;
			}
		}
		return count;
	}

	public static int pk10lmdxds33(String bet, String kj) {
		String[] kjData = kj.split(",");
		String[] betData = bet.split(",");
		int gyzh = Integer.parseInt(kjData[0]) + Integer.parseInt(kjData[1]) + Integer.parseInt(kjData[2]);

		ArrayList<ArrayList<String>> tmpData = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < betData.length; i++) {
			String[] subNums = betData[i].split("");
			ArrayList<String> subArray = new ArrayList<String>();
			for (int j = 0; j < subNums.length; j++) {
				subArray.add(subNums[j]);
			}
			tmpData.add(subArray);
		}
		// 笛卡尔乘取得所投的号码
		ArrayList<ArrayList<String>> allNums = DescartesAlgorithm(tmpData);
		HashMap<String, String> o = new HashMap<String, String>();
		o.put("大", "17,18,19,20,21,22,23,24,25,26,27");
		o.put("小", "6,7,8,9,10,11,12,13,14,15,16");
		o.put("单", "7,9,11,13,15,17,19,21,23,25,27");
		o.put("双", "6,8,10,12,14,16,18,20,22,24,26");

		int count = 0;
		for (int i = 0; i < allNums.size(); i++) {
			if ((o.get(allNums.get(i).get(0)).indexOf(gyzh) != -1)) {
				count++;
			}
		}
		return count;
	}

	// 冠亚季选一
	public static int pk10r123(String bet, String kj) {
		return rx(bet, kj.substring(0, 8), 1);
	}

	// 冠亚总和
	public static int pk10gy2(String bet, String kj) {
		String[] kjData = kj.split(",");
		String[] betData = bet.split(" ");
		int val = Integer.parseInt(kjData[0]) + Integer.parseInt(kjData[1]);
		int count = 0;
		for (int i = 0; i < betData.length; i++) {
			if (Integer.parseInt(betData[i]) == val) {
				count++;
			}
		}
		return count;
	}

	// 冠亚组合
	public static int pk10gyzh(String bet, String kj) {
		String[] kjData = kj.split(",");
		String[] betData = bet.split(" ");
		int val1 = Integer.parseInt(kjData[0]);
		int val2 = Integer.parseInt(kjData[1]);
		String str1 = val1 + "-" + val2;
		String str2 = val2 + "-" + val1;
		int count = 0;
		for (int i = 0; i < betData.length; i++) {
			if (betData[i].equals(str1) || betData[i].equals(str2)) {
				count++;
			}
		}
		return count;
	}

	// 龙虎
	public static BigDecimal pk10lh1(String bet, String kj) {
		return pk10lh(bet, kj, 1);
	}

	public static BigDecimal pk10lh2(String bet, String kj) {
		return pk10lh(bet, kj, 2);
	}

	public static BigDecimal pk10lh3(String bet, String kj) {
		return pk10lh(bet, kj, 3);
	}

	public static BigDecimal pk10lh4(String bet, String kj) {
		return pk10lh(bet, kj, 4);
	}

	public static BigDecimal pk10lh5(String bet, String kj) {
		return pk10lh(bet, kj, 5);
	}

	public static int pk10lh12(String bet, String kj) {
		String[] kjData = kj.split(",");
		String[] betData = bet.split("");
		int val1 = Integer.parseInt(kjData[0]) + Integer.parseInt(kjData[1]);
		int val2 = Integer.parseInt(kjData[9]) + Integer.parseInt(kjData[8]);
		int count = 0;

		for (int i = 0; i < betData.length; i++) {
			if ("龙".equals(betData[i])) {
				if (val1 > val2) {
					count++;
				}
			} else if ("虎".equals(betData[i])) {
				if (val1 < val2) {
					count++;
				}
			}
		}
		return count;
	}

	public static int pk10lh123(String bet, String kj) {
		String[] kjData = kj.split(",");
		String[] betData = bet.split("");
		int val1 = Integer.parseInt(kjData[0]) + Integer.parseInt(kjData[1]) + Integer.parseInt(kjData[2]);
		int val2 = Integer.parseInt(kjData[9]) + Integer.parseInt(kjData[8]) + Integer.parseInt(kjData[7]);
		int count = 0;

		for (int i = 0; i < betData.length; i++) {
			if ("龙".equals(betData[i])) {
				if (val1 > val2) {
					count++;
				}
			} else if ("虎".equals(betData[i])) {
				if (val1 < val2) {
					count++;
				}
			}
		}
		return count;
	}

	// 前二组选
	public static int kjzx2(String bet, String kj) {
		return zx(bet, kj.substring(0, 5));
	}

	// 前三组选
	public static int kjzx3(String bet, String kj) {
		return zx(bet, kj.substring(0, 8));
	}

	// 北京快乐8
	public static int k8R1(String bet, String kj) {
		return rx(bet, kj.split("\\|")[0], 1);
	}

	public static int k8R2(String bet, String kj) {
		return rx(bet, kj.split("\\|")[0], 2);
	}

	public static int k8R3(String bet, String kj) {
		return rx(bet, kj.split("\\|")[0], 3);
	}

	public static int k8R4(String bet, String kj) {
		return rx(bet, kj.split("\\|")[0], 4);
	}

	public static int k8R5(String bet, String kj) {
		return rx(bet, kj.split("\\|")[0], 5);
	}

	public static int k8R6(String bet, String kj) {
		return rx(bet, kj.split("\\|")[0], 6);
	}

	public static int k8R7(String bet, String kj) {
		return rx(bet, kj.split("\\|")[0], 7);
	}

	// 快3
	// 和值
	public static int k3hz(String bet, String kj) {
		String[] betData = bet.split(" ");
		String[] kjData = kj.split(",");
		int val = Integer.parseInt(kjData[0]) + Integer.parseInt(kjData[1]) + Integer.parseInt(kjData[2]);
		int count = 0;
		for (int i = 0; i < betData.length; i++) {
			if (Integer.parseInt(betData[i]) == val) {
				count++;
			}
		}
		return count;
	}

	// 三同号通选
	public static int k33tx(String bet, String kj) {
		String reg = "/\\,/g";
		String newKj = kj.replaceAll(reg, "");
		int count = 0;
		if (bet.indexOf(newKj) != -1)
			count = 1;
		return count;
	}

	// 三连号通选
	public static int k33ltx(String bet, String kj) {
		return k33tx(bet, kj);
	}

	// 三同号单选
	public static int k33dx(String bet, String kj) {
		String reg = "/\\*/g";
		String newBet = bet.replaceAll(reg, "");
		String[] betData = newBet.split(",");
		String[] kjData = kj.split(",");

		String kj1 = kjData[0] + kjData[1];
		String kj2 = kjData[2];
		String newKj = kj1 + "," + kj2;

		kjData = newKj.split(",");
		for (int i = 0; i < kjData.length; i++) {
			if (betData[i].indexOf(kjData[i]) == -1) {
				return 0;
			}
		}
		return 1;
	}

	// 三不同号
	public static int k33x(String bet, String kj) {
		return zx(bet, kj);
	}

	// 二不同号
	public static int k32x(String bet, String kj) {
		return k33x(bet, kj);
	}

	// 二同号复选
	public static int k32fx(String bet, String kj) {
		String reg = "/\\*/g";
		String newBet = bet.replaceAll(reg, "");
		reg = "/\\ /g";
		newBet = newBet.replaceAll(reg, "");

		String[] betData = newBet.split("");
		String[] kjData = kj.split(",");

		if (Sames(new ArrayList<String>(Arrays.asList(betData)), new ArrayList<String>(Arrays.asList(kjData))) == 2) {
			return 1;
		}
		return 0;
	}

	// 二同号单选
	public static int k32dx(String bet, String kj) {
		String[] betData = bet.split(" ");
		String reg = "/\\,/g";
		String newKj = kj.replaceAll(reg, "");
		int count = 0;
		for (int i = 0, l = betData.length; i < l; i++) {
			if (betData[i].indexOf(newKj) != -1)
				count = 1;
		}
		return count;
	}

	/* 特别号 */
	public static int SP(String bet, String kj) {
		int count = 0;
		String[] kjData = kj.split(",");
		String kjtm = kjData[6];
		if (bet.equals(kjtm))
			count = 1;
		return count;
	}

	public static int SPBSOE(String bet, String kj) {
		int count = 0;
		String[] kjData = kj.split(",");
		String kjtm = kjData[6];
		int kjtm_num = Integer.parseInt(kjtm);
		switch (bet) {
		case "特大":
			if (kjtm_num > 24 && kjtm_num != 49)
				count = 1;
			break;

		case "特小":
			if (kjtm_num < 25)
				count = 1;
			break;

		case "特单":
			if (kjtm_num % 2 != 0 && kjtm_num != 49)
				count = 1;
			break;

		case "特双":
			if (kjtm_num % 2 == 0)
				count = 1;
			break;
		}

		return count;
	}

	public static int SPTBSOE(String bet, String kj) {
		int count = 0;
		String[] kjData = kj.split(",");
		String kjtm = kjData[6];
		int kjtm_num = Integer.parseInt(kjtm);
		String[] kjtmh = kjtm.split("");
		int val = Integer.parseInt(kjtmh[0]) + Integer.parseInt(kjtmh[1]);
		switch (bet) {
		case "合大":
			if (val > 6 && kjtm_num != 49)
				count = 1;
			break;

		case "合小":
			if (val < 7 && kjtm_num != 49)
				count = 1;
			break;

		case "合单":
			if (val % 2 != 0 && kjtm_num != 49)
				count = 1;
			break;

		case "合双":
			if (val % 2 == 0 && kjtm_num != 49)
				count = 1;
			break;
		}

		return count;
	}

	public static int SPSBS(String bet, String kj) {
		int count = 0;
		String[] kjData = kj.split(",");
		String kjtm = kjData[6];
		int kjtm_num = Integer.parseInt(kjtm);
		String[] kjtmh = kjtm.split("");
		int val = Integer.parseInt(kjtmh[1]);
		switch (bet) {
		case "特尾大":
			if (val > 4 && kjtm_num != 49)
				count = 1;
			break;

		case "特尾小":
			if (val < 5 && kjtm_num != 49)
				count = 1;
			break;
		}

		return count;
	}

	public static int SPH2(String bet, String kj) {
		int count = 0;
		String[] kjData = kj.split(",");
		String kjtm = kjData[6];
		int kjtm_num = Integer.parseInt(kjtm);
		switch (bet) {
		case "大单":
			if (kjtm_num > 24 && kjtm_num % 2 != 0)
				count = 1;
			break;
		case "大双":
			if (kjtm_num > 24 && kjtm_num % 2 == 0)
				count = 1;
			break;
		case "小单":
			if (kjtm_num < 25 && kjtm_num % 2 != 0)
				count = 1;
			break;
		case "小双":
			if (kjtm_num < 25 && kjtm_num % 2 == 0)
				count = 1;
			break;
		}
		return count;
	}

	/* 生肖头尾 */
	public static int SPANM(String bet, String kj) {
		int count = 0;
		String[] kjData = kj.split(",");
		String kjtm = kjData[6];
		if (CheckANM(kjtm, bet))
			count = 1;
		return count;
	}

	public static int SPTD(String bet, String kj) {
		String reg = "/头/g";
		String newBet = bet.replaceAll(reg, "");
		int count = 0;
		String[] kjData = kj.split(",");
		String kjtm = kjData[6];
		String[] kjtmh = kjtm.split("");
		int val = Integer.parseInt(kjtmh[0]);
		if (val == Integer.parseInt(newBet))
			count = 1;
		return count;
	}

	public static int SPSD(String bet, String kj) {
		String reg = "/尾/g";
		String newBet = bet.replaceAll(reg, "");
		int count = 0;
		String[] kjData = kj.split(",");
		String kjtm = kjData[6];
		String[] kjtmh = kjtm.split("");
		int val = Integer.parseInt(kjtmh[1]);
		if (val == Integer.parseInt(newBet))
			count = 1;
		return count;
	}

	/* 波色 */
	public static int SPCLR(String bet, String kj) {
		int count = 0;
		String[] kjData = kj.split(",");
		String kjtm = kjData[6];
		if (CheckCLR(kjtm, bet))
			count = 1;
		return count;
	}

	public static int SPHC(String bet, String kj) {
		int count = 0;
		String[] kjData = kj.split(",");
		String kjtm = kjData[6];
		int kjtm_num = Integer.parseInt(kjtm);
		switch (bet) {
		case "红大":
			if (kjtm_num > 24 && CheckCLR(kjtm, "红波"))
				count = 1;
			break;

		case "红小":
			if (kjtm_num < 25 && CheckCLR(kjtm, "红波"))
				count = 1;
			break;

		case "红单":
			if (kjtm_num % 2 != 0 && CheckCLR(kjtm, "红波"))
				count = 1;
			break;

		case "红双":
			if (kjtm_num % 2 == 0 && CheckCLR(kjtm, "红波"))
				count = 1;
			break;

		case "蓝大":
			if (kjtm_num > 24 && CheckCLR(kjtm, "蓝波"))
				count = 1;
			break;

		case "蓝小":
			if (kjtm_num < 25 && CheckCLR(kjtm, "蓝波"))
				count = 1;
			break;

		case "蓝单":
			if (kjtm_num % 2 != 0 && CheckCLR(kjtm, "蓝波"))
				count = 1;
			break;

		case "蓝双":
			if (kjtm_num % 2 == 0 && CheckCLR(kjtm, "蓝波"))
				count = 1;
			break;

		case "绿大":
			if (kjtm_num > 24 && CheckCLR(kjtm, "绿波"))
				count = 1;
			break;

		case "绿小":
			if (kjtm_num < 25 && CheckCLR(kjtm, "绿波"))
				count = 1;
			break;

		case "绿单":
			if (kjtm_num % 2 != 0 && CheckCLR(kjtm, "绿波"))
				count = 1;
			break;

		case "绿双":
			if (kjtm_num % 2 == 0 && CheckCLR(kjtm, "绿波"))
				count = 1;
			break;
		}
		return count;
	}

	public static int SPHHC(String bet, String kj) {
		int count = 0;
		String[] kjData = kj.split(",");
		String kjtm = kjData[6];
		int kjtm_num = Integer.parseInt(kjtm);
		switch (bet) {
		case "红大单":
			if (kjtm_num > 24 && kjtm_num % 2 != 0 && CheckCLR(kjtm, "红波"))
				count = 1;
			break;

		case "红大双":
			if (kjtm_num > 24 && kjtm_num % 2 == 0 && CheckCLR(kjtm, "红波"))
				count = 1;
			break;

		case "红小单":
			if (kjtm_num < 25 && kjtm_num % 2 != 0 && CheckCLR(kjtm, "红波"))
				count = 1;
			break;

		case "红小双":
			if (kjtm_num < 25 && kjtm_num % 2 == 0 && CheckCLR(kjtm, "红波"))
				count = 1;
			break;

		case "蓝大单":
			if (kjtm_num > 24 && kjtm_num % 2 != 0 && CheckCLR(kjtm, "蓝波"))
				count = 1;
			break;

		case "蓝大双":
			if (kjtm_num > 24 && kjtm_num % 2 == 0 && CheckCLR(kjtm, "蓝波"))
				count = 1;
			break;

		case "蓝小单":
			if (kjtm_num < 25 && kjtm_num % 2 != 0 && CheckCLR(kjtm, "蓝波"))
				count = 1;
			break;

		case "蓝小双":
			if (kjtm_num < 25 && kjtm_num % 2 == 0 && CheckCLR(kjtm, "蓝波"))
				count = 1;
			break;

		case "绿大单":
			if (kjtm_num > 24 && kjtm_num % 2 != 0 && CheckCLR(kjtm, "绿波"))
				count = 1;
			break;

		case "绿大双":
			if (kjtm_num > 24 && kjtm_num % 2 == 0 && CheckCLR(kjtm, "绿波"))
				count = 1;
			break;

		case "绿小单":
			if (kjtm_num < 25 && kjtm_num % 2 != 0 && CheckCLR(kjtm, "绿波"))
				count = 1;
			break;

		case "绿小双":
			if (kjtm_num < 25 && kjtm_num % 2 == 0 && CheckCLR(kjtm, "绿波"))
				count = 1;
			break;
		}
		return count;
	}

	/* 正码平码 */
	public static int LOTTO(String bet, String kj) {
		int count = 0;
		String newKj = removeFromList(kj, ",", new ArrayList<Integer>(Arrays.asList(6)));
		if (newKj.indexOf(bet) != -1)
			count = 1;
		return count;
	}

	public static int LTTBSOE(String bet, String kj) {
		int count = 0;
		String[] kjData = kj.split(",");
		int val = 0;
		for (int i = 0; i < kjData.length; i++) {
			val += Integer.parseInt(kjData[i]);
		}
		switch (bet) {
		case "总大":
			if (val > 174)
				count = 1;
			break;

		case "总小":
			if (val < 175)
				count = 1;
			break;

		case "总单":
			if (val % 2 != 0)
				count = 1;
			break;

		case "总双":
			if (val % 2 == 0)
				count = 1;
			break;
		}
		return count;
	}

	/* 平特肖尾 一肖 正特尾数 */
	public static int LTTBP(String bet, String kj) {
		String newBet = GetANM(bet);
		String[] betData = newBet.split(",");
		int count = 0;
		for (int i = 0; i < betData.length; i++) {
			if (kj.indexOf(betData[i]) != -1) {
				count++;
			}
		}
		return count;
	}

	public static int LTTSD(String bet, String kj) {
		int count = 0;
		String reg = "/尾/g";
		String newBet = bet.replaceAll(reg, "");

		String[] kjData = kj.split(",");
		String newKj = "";
		boolean delLast = false;
		for (int i = 0; i < kjData.length; i++) {
			String t1 = kjData[i].split("")[1];
			String[] t2Arr = t1.split("");
			for (int j = 0; j < t2Arr.length; j++) {
				newKj = newKj + t2Arr[j] + ",";
				delLast |= true;
			}
		}
		if (delLast) {
			newKj.substring(0, newKj.length() - 1);
		}
		if (newKj.indexOf(newBet) != -1) {
			count = 1;
		}
		return count;
	}

	/* 连肖 */
	public static int SNBP2(String bet, String kj) {
		return SNBP(bet, kj, 2);
	}

	public static int SNBP3(String bet, String kj) {
		return SNBP(bet, kj, 3);
	}

	public static int SNBP4(String bet, String kj) {
		return SNBP(bet, kj, 4);
	}

	public static int SNBP5(String bet, String kj) {
		return SNBP(bet, kj, 5);
	}

	// 连肖函数
	public static int SNBP(String bet, String kj, int num) {
		String hbet = "";
		ArrayList<ArrayList<String>> numbers = combine(new ArrayList<String>(Arrays.asList(bet.split(" "))), num);
		int count = 0;
		for (int i = 0; i < numbers.size(); i++) {
			boolean result = true;
			for (int j = 0; j < numbers.size(); j++) {
				hbet = GetANM(numbers.get(i).get(j));
				String[] anms = hbet.split(",");
				boolean getAnm = false;
				for (int k = 0; k < anms.length; k++) {
					if (kj.indexOf(anms[k]) != -1) {
						getAnm = true;
						break;
					}
				}
				if (!getAnm) {
					result = false;
				}
			}
			if (result) {
				count++;
			}
		}
		return count;
	}

	/* 连尾 */
	public static int SNSD2(String bet, String kj) {
		return SNSD(bet, kj, 2);
	}

	public static int SNSD3(String bet, String kj) {
		return SNSD(bet, kj, 3);
	}

	public static int SNSD4(String bet, String kj) {
		return SNSD(bet, kj, 4);
	}

	public static int SNSD5(String bet, String kj) {
		return SNSD(bet, kj, 5);
	}

	// 连尾函数
	public static int SNSD(String bet, String kj, int num) {
		String reg = "/尾/g";
		String newBet = bet.replaceAll(reg, "");
		String[] kjData = kj.split(",");
		String newKj = "";
		boolean delLast = false;
		for (int i = 0; i < kjData.length; i++) {
			String t1 = kjData[i].split("")[1];
			String[] t2Arr = t1.split("");
			for (int j = 0; j < t2Arr.length; j++) {
				newKj = newKj + t2Arr[j] + ",";
				delLast |= true;
			}
		}
		if (delLast) {
			newKj.substring(0, newKj.length() - 1);
		}
		ArrayList<ArrayList<String>> numbers = combine(new ArrayList<String>(Arrays.asList(newBet.split(" "))), num);
		int count = 0;
		for (int i = 0; i < numbers.size(); i++) {
			for (int j = 0; j < numbers.get(i).size(); j++) {
				if (newKj.indexOf(numbers.get(i).get(j)) != -1) {
					count++;
					break;
				}
			}
		}

		return count;
	}

	/* 连码 */
	public static int LM4OF4(String bet, String kj) {
		return rx(bet, removeFromList(kj, ",", new ArrayList<Integer>(Arrays.asList(6))), 4);
	}

	public static int LM3OF3(String bet, String kj) {
		return rx(bet, removeFromList(kj, ",", new ArrayList<Integer>(Arrays.asList(6))), 3);
	}

	public static int LM2OF2(String bet, String kj) {
		return rx(bet, removeFromList(kj, ",", new ArrayList<Integer>(Arrays.asList(6))), 2);
	}

	// 三中二
	public static int LM2OF3(String bet, String kj) {
		String newkj = removeFromList(kj, ",", new ArrayList<Integer>(Arrays.asList(6)));
		ArrayList<ArrayList<String>> numbers = combine(new ArrayList<String>(Arrays.asList(bet.split(" "))), 3);
		int count = 0;
		for (int i = 0; i < numbers.size(); i++) {
			int num = 0;
			for (int j = 0; j < numbers.get(i).size(); j++) {
				if (kj.indexOf(numbers.get(i).get(j)) != -1) {
					num++;
				}
			}
			if (num == 2) {
				count++;
			}
		}
		if (count < 1) {
			count = LM3OF3(bet, kj);
		}
		return count;
	}

	// 二中特
	public static int LMSPOF2(String bet, String kj) {
		int count = LM2OF2(bet, kj);
		if (count < 1) {
			count = LMSPOF(bet, kj);
		}
		return count;
	}

	// 特串
	public static int LMSPOF(String bet, String kj) {
		String kj2 = removeFromList(kj, ",", new ArrayList<Integer>(Arrays.asList(6)));
		String kjtm = kj.split(",")[6];
		ArrayList<ArrayList<String>> numbers = combine(new ArrayList<String>(Arrays.asList(bet.split(" "))), 2);
		int count = 0;

		for (int i = 0; i < numbers.size(); i++) {
			if (((kj2.indexOf(numbers.get(i).get(0))) != -1) && ((kjtm.indexOf(numbers.get(i).get(1))) != -1)
					&& ((kj2.indexOf(numbers.get(i).get(1))) != -1) && ((kjtm.indexOf(numbers.get(i).get(0))) != -1)) {
				count++;
			}
		}
		return count;
	}

	/* 自选不中 */
	public static int NOHIT5(String bet, String kj, int num) {
		return NOHIT(bet, kj, 5);
	}

	public static int NOHIT6(String bet, String kj, int num) {
		return NOHIT(bet, kj, 6);
	}

	public static int NOHIT7(String bet, String kj, int num) {
		return NOHIT(bet, kj, 7);
	}

	public static int NOHIT8(String bet, String kj, int num) {
		return NOHIT(bet, kj, 8);
	}

	public static int NOHIT9(String bet, String kj, int num) {
		return NOHIT(bet, kj, 9);
	}

	public static int NOHIT10(String bet, String kj, int num) {
		return NOHIT(bet, kj, 10);
	}

	public static int NOHIT11(String bet, String kj, int num) {
		return NOHIT(bet, kj, 11);
	}

	public static int NOHIT12(String bet, String kj, int num) {
		return NOHIT(bet, kj, 12);
	}

	public static BigDecimal ssclhAll(int kjIndex1, int kjIndex2, String bet, String kj) {
		String[] kjData = kj.split(",");
		int val1 = Integer.parseInt(kjData[kjIndex1]);
		int val2 = Integer.parseInt(kjData[kjIndex2]);
		bet = bet.replace(",", "");
		String[] betData = bet.split("");
		BigDecimal count = BigDecimal.ZERO;
		for (int i = 0, l = betData.length; i < l; i++) {
			if ("龙".equals(betData[i])) {
				if (val1 > val2)
					count = count.add(new BigDecimal("1"));
			} else if ("虎".equals(betData[i])) {
				if (val1 < val2)
					count = count.add(new BigDecimal("1"));
			} else {
				if (val1 == val2)
					count = count.add(new BigDecimal("4.5"));
			}
		}
		return count;
	}

	public static BigDecimal ssclh1(String bet, String kj) {
		return ssclhAll(0, 1, bet, kj);
	}

	public static BigDecimal ssclh2(String bet, String kj) {
		return ssclhAll(0, 2, bet, kj);
	}

	public static BigDecimal ssclh3(String bet, String kj) {
		return ssclhAll(0, 3, bet, kj);
	}

	public static BigDecimal ssclh4(String bet, String kj) {
		return ssclhAll(0, 4, bet, kj);
	}

	public static BigDecimal ssclh5(String bet, String kj) {
		return ssclhAll(1, 2, bet, kj);
	}

	public static BigDecimal ssclh6(String bet, String kj) {
		return ssclhAll(1, 3, bet, kj);
	}

	public static BigDecimal ssclh7(String bet, String kj) {
		return ssclhAll(1, 4, bet, kj);
	}

	public static BigDecimal ssclh8(String bet, String kj) {
		return ssclhAll(2, 3, bet, kj);
	}

	public static BigDecimal ssclh9(String bet, String kj) {
		return ssclhAll(2, 4, bet, kj);
	}

	public static BigDecimal ssclh10(String bet, String kj) {
		return ssclhAll(3, 4, bet, kj);
	}

	/**
	 * 
	 * 自选不中函数
	 */
	public static int subNOHIT(ArrayList<ArrayList<String>> allCombine, String kj, int num) {
		int count = 0;
		for (ArrayList<String> object : allCombine) {
			for (String s : object) {
				if (kj.indexOf(s) != -1) {
					count++;
					break;
				}
			}
		}
		return allCombine.size() - count;
	}

	public static int NOHIT(String bet, String kj, int num) {
		ArrayList<String> tmp = new ArrayList<String>(Arrays.asList(bet.split(" ")));
		ArrayList<ArrayList<String>> allCombine = combine(tmp, num);
		return subNOHIT(allCombine, kj, num);
	}

	/**
	 * 
	 * 判断生肖
	 */
	public static boolean CheckANM(String bet, String xs) {
		String xsbet;
		boolean flag = false;
		switch (xs) {
		case "鼠":
			xsbet = "08,20,32,44";
			if (xsbet.indexOf(bet) != -1)
				flag = true;
			break;

		case "牛":
			xsbet = "07,19,31,43";
			if (xsbet.indexOf(bet) != -1)
				flag = true;
			break;

		case "虎":
			xsbet = "06,18,30,42";
			if (xsbet.indexOf(bet) != -1)
				flag = true;
			break;

		case "兔":
			xsbet = "05,17,29,41";
			if (xsbet.indexOf(bet) != -1)
				flag = true;
			break;

		case "龙":
			xsbet = "04,16,28,40";
			if (xsbet.indexOf(bet) != -1)
				flag = true;
			break;

		case "蛇":
			xsbet = "03,15,27,39";
			if (xsbet.indexOf(bet) != -1)
				flag = true;
			break;

		case "马":
			xsbet = "02,14,26,38";
			if (xsbet.indexOf(bet) != -1)
				flag = true;
			break;

		case "羊":
			xsbet = "01,13,25,37,49";
			if (xsbet.indexOf(bet) != -1)
				flag = true;
			break;

		case "猴":
			xsbet = "12,24,36,48";
			if (xsbet.indexOf(bet) != -1)
				flag = true;
			break;

		case "鸡":
			xsbet = "11,23,35,47";
			if (xsbet.indexOf(bet) != -1)
				flag = true;
			break;

		case "狗":
			xsbet = "10,22,34,46";
			if (xsbet.indexOf(bet) != -1)
				flag = true;
			break;

		case "猪":
			xsbet = "09,21,33,45";
			if (xsbet.indexOf(bet) != -1)
				flag = true;
			break;

		}
		return flag;
	}

	/**
	 * 
	 *
	 */
	public static String GetANM(String xs) {
		String xsbet = "";
		switch (xs) {
		case "鼠":
			xsbet = "08,20,32,44";
			break;

		case "牛":
			xsbet = "07,19,31,43";
			break;

		case "虎":
			xsbet = "06,18,30,42";
			break;

		case "兔":
			xsbet = "05,17,29,41";
			break;

		case "龙":
			xsbet = "04,16,28,40";
			break;

		case "蛇":
			xsbet = "03,15,27,39";
			break;

		case "马":
			xsbet = "02,14,26,38";
			break;

		case "羊":
			xsbet = "01,13,25,37,49";
			break;

		case "猴":
			xsbet = "12,24,36,48";
			break;

		case "鸡":
			xsbet = "11,23,35,47";
			break;

		case "狗":
			xsbet = "10,22,34,46";
			break;

		case "猪":
			xsbet = "09,21,33,45";
			break;

		}
		return xsbet;
	}

	/**
	 * 判断波色
	 *
	 */
	public static boolean CheckCLR(String bet, String xs) {
		String xsbet;
		boolean flag = false;
		switch (xs) {
		case "红波":
			xsbet = "01,02,12,13,23,24,34,35,45,46,07,08,18,19,29,30,40";
			if (xsbet.indexOf(bet) != -1)
				flag = true;
			break;

		case "蓝波":
			xsbet = "31,41,42,03,04,14,15,25,26,36,37,47,48,09,10,20";
			if (xsbet.indexOf(bet) != -1)
				flag = true;
			break;

		case "绿波":
			xsbet = "11,21,22,32,33,43,44,05,06,16,17,27,28,38,39,49";
			if (xsbet.indexOf(bet) != -1)
				flag = true;
			break;

		}
		return flag;
	}

	/**
	 * 常用前选算法
	 *
	 * @params bet 投注列表：01 02 03,04 05
	 * @params data 开奖所需的那几个：04,05
	 * @params weizhu 开奖前几位数
	 *
	 * @return 返回中奖注数
	 */
	public static int qs(String bet, String data, Integer weizhu) {
		String[] betData = bet.split(",");
		String tmp = data.substring(0, weizhu * 3 - 1);
		String[] kjData = tmp.split(",");
		for (int i = 0; i < kjData.length; i++) {
			if (betData[i].indexOf(kjData[i]) == -1) {
				return 0;
			}
		}
		return 1;
	}

	/**
	 * 常用复式算法
	 *
	 * @params bet 投注列表：123,45,2,59
	 * @params data 开奖所需的那几个：4,5,0,8
	 *
	 * @return 返回中奖注数
	 */
	public static int fs(String bet, String data) {
		String[] betData = bet.split(",");
		ArrayList<ArrayList<String>> tmpData = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < betData.length; i++) {
			String[] subNums = betData[i].split("");
			ArrayList<String> subArray = new ArrayList<String>();
			for (int j = 0; j < subNums.length; j++) {
				subArray.add(subNums[j]);
			}
			tmpData.add(subArray);
		}
		// 笛卡尔乘取得所投的号码
		ArrayList<ArrayList<String>> allNums = DescartesAlgorithm(tmpData);
		int count = 0;
		for (int i = 0; i < allNums.size(); i++) {
			// String[] subNums = allNums.get(i).split("");
			String subNum = "";
			for (int j = 0; j < allNums.get(i).size(); j++) {
				subNum = subNum + allNums.get(i).get(j) + ","; // 把号码由数组变成字符串，以便比较
			}
			subNum = subNum.substring(0, subNum.length() - 1);
			if (subNum.equals(data)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 单式算法
	 *
	 * @params bet 投注列表：1,5,2,9|3,2,4,6
	 * @params data 开奖所需的那几位号码：4,5,3,6
	 *
	 * @return 返回中奖注数
	 */
	public static int ds(String bet, String data) {
		String[] betData = bet.split("\\|");
		int count = 0;
		for (int i = 0; i < betData.length; i++) {
			if (betData[i].equals(data)) {
				count++;
			}
		}
		return count;
	}

	public static boolean checkZ3(String s) {
		if (s.length() == 3) {
			String[] t = s.split("");
			if (t[0].equals(t[1]) || t[0].equals(t[2]) || t[1].equals(t[2])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 组三
	 *
	 * @params bet 投注列表：135687或112,334
	 * @params data 开奖所需的那几位号码：4,5,3
	 *
	 * @return 返回中奖注数
	 */
	public static int z3(String bet, String data) {
		// 豹子不算中奖
		String[] tmpkjData = data.split(",");
		for (int i = 0; i < tmpkjData.length - 2; i++) {
			int kj_0 = Integer.parseInt(tmpkjData[i]);
			int kj_1 = Integer.parseInt(tmpkjData[i + 1]);
			int kj_2 = Integer.parseInt(tmpkjData[i + 2]);
			if (kj_0 == kj_1 && kj_0 == kj_2) {
				return 0;
			}
		}
		// reg = "/(\\d)\\d?\\1/";
		if (checkZ3(bet) || bet.indexOf(",") != -1) {
			// 单选处理
			String[] betData = bet.split(",");
			String[] t = data.split(",");

			if (!t[0].equals(t[1]) && !t[0].equals(t[2]) && !t[2].equals(t[1])) {
				return 0;
			}
			String m = "";
			String s = "";
			if (t[0].equals(t[1]) || t[0].equals(t[2])) {
				m = t[0];
				if (t[0].equals(t[1])) {
					s = t[2];
				} else {
					s = t[1];
				}

			} else {
				m = t[1];
				s = t[0];
			}
			int count = 0;
			for (int i = 0; i < betData.length; i++) {
				if ((betData[i].replaceAll(m, "")).equals(s)) {
					count++;
				}
			}
			return count;
		} else {
			// 复选处理
			ArrayList<ArrayList<String>> numbers = combine(new ArrayList<String>(Arrays.asList(bet.split(""))), 2);
			ArrayList<String> mergeNums = new ArrayList<String>();
			for (int i = 0; i < numbers.size(); i++) {
				String tmp = "";
				for (int j = 0; j < numbers.get(i).size(); j++) {
					tmp += numbers.get(i).get(j);
				}
				mergeNums.add(tmp);
			}
			String[] kjData = data.split(",");
			int count = 0;
			for (int i = 0; i < mergeNums.size(); i++) {
				int j = 0;
				for (j = 0; j < kjData.length; j++) {
					if (mergeNums.get(i).indexOf(kjData[j]) == -1) {
						break;
					}
				}
				if (j == kjData.length) {
					count++;
				}
			}
			return count;
		}
	}

	/**
	 * 组六
	 *
	 * @params bet 投注列表：135687
	 * @params data 开奖所需的那几位号码：4,5,3
	 *
	 * @return 返回中奖注数
	 */
	public static int z6(String bet, String data) {
		// 豹子不算中奖
		String[] kjData = data.split(",");
		for (int i = 0; i < kjData.length - 2; i++) {
			int kj_0 = Integer.parseInt(kjData[i]);
			int kj_1 = Integer.parseInt(kjData[i + 1]);
			int kj_2 = Integer.parseInt(kjData[i + 2]);
			if (kj_0 == kj_1 && kj_0 == kj_2) {
				return 0;
			}
		}
		ArrayList<ArrayList<String>> numbers = permutation(new ArrayList<String>(Arrays.asList(data.split(","))), 3);
		ArrayList<String> mergeNums = new ArrayList<String>();
		for (int i = 0; i < numbers.size(); i++) {
			String tmp = "";
			for (int j = 0; j < numbers.get(i).size(); j++) {
				tmp += numbers.get(i).get(j);
			}
			mergeNums.add(tmp);
		}
		if (bet.indexOf(",") != -1) {
			// 录入式投注
			String[] betData;
			betData = bet.split(",");
			int count = 0;
			for (int i = 0; i < betData.length; i++) {
				if (mergeNums.contains(betData[i])) {
					count++;
				}
			}
			return count;
		} else {
			// 点击按钮投注
			ArrayList<String> tmp = new ArrayList<String>(Arrays.asList(bet.split("")));
			ArrayList<ArrayList<String>> betNumbers = combine(tmp, 3);
			int count = 0;
			for (int i = 0; i < betNumbers.size(); i++) {
				String mergeNum = "";
				for (int j = 0; j < betNumbers.get(i).size(); j++) {
					mergeNum += betNumbers.get(i).get(j);
				}
				for (int j = 0; j < mergeNums.size(); j++) {
					if (mergeNums.get(j).equals(mergeNum)) {
						count++;
					}
				}
			}
			return count;
		}

	}

	/**
	 * 组二复式
	 *
	 * @params bet 投注列表：135687
	 * @params data 开奖所需的那几位号码：4,5
	 *
	 * @return 返回中奖注数
	 */
	public static int z2f(String bet, String data) {
		// 对子不算中奖
		String[] kjData = data.split(",");
		for (int i = 0; i < kjData.length - 1; i++) {
			int kj_0 = Integer.parseInt(kjData[i]);
			int kj_1 = Integer.parseInt(kjData[i + 1]);
			if (kj_0 == kj_1) {
				return 0;
			}
		}
		String data0 = "";
		String data1 = "";

		for (int i = 0; i < kjData.length; i++) {
			data1 += kjData[i];
		}
		for (int i = kjData.length - 1; i >= 0; i--) {
			data0 += kjData[i];
		}
		ArrayList<String> tmp = new ArrayList<String>(Arrays.asList(bet.split("")));
		ArrayList<ArrayList<String>> numbers = combine(tmp, 2);
		int count = 0;
		for (int i = 0; i < numbers.size(); i++) {
			String mergeNum = "";
			for (int j = 0; j < numbers.get(i).size(); j++) {
				mergeNum += numbers.get(i).get(j);
			}
			if (mergeNum.equals(data0) || mergeNum.equals(data1)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 组二单式
	 *
	 * @params bet 投注列表：1,3|5,6|8,7
	 * @params data 开奖所需的那几位号码：4,5
	 *
	 * @return 返回中奖注数
	 */
	public static int z2d(String bet, String data) {
		// 对子不算中奖
		String[] kjData = data.split(",");
		for (int i = 0; i < kjData.length - 1; i++) {
			int kj_0 = Integer.parseInt(kjData[i]);
			int kj_1 = Integer.parseInt(kjData[i + 1]);
			if (kj_0 == kj_1) {
				return 0;
			}
		}
		String data1 = new StringBuilder(data).reverse().toString();
		String[] betData = bet.split("\\|");
		int count = 0;
		for (int i = 0; i < betData.length; i++) {
			if (data.equals(betData[i]) || data1.equals(betData[i])) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 大小单双
	 *
	 * @params bet 投注列表：大单,小单
	 * @params data 开奖所需的那几位号码：4,5
	 *
	 * @return 返回中奖注数
	 */

	public static int dxds(String bet, String data) {
		String[] kjData = data.split(",");
		String[] betData = bet.split(",");
		ArrayList<ArrayList<String>> tmpData = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < betData.length; i++) {
			String[] subNums = betData[i].split("");
			ArrayList<String> subArray = new ArrayList<String>();
			for (int j = 0; j < subNums.length; j++) {
				subArray.add(subNums[j]);
			}
			tmpData.add(subArray);
		}
		ArrayList<ArrayList<String>> allNums = DescartesAlgorithm(tmpData);
		HashMap<String, String> o = new HashMap<String, String>();
		o.put("大", "56789");
		o.put("小", "01234");
		o.put("单", "13579");
		o.put("双", "02468");
		int count = 0;
		for (int i = 0; i < allNums.size(); i++) {
			if ((o.get(allNums.get(i).get(0)).indexOf(kjData[0]) != -1)
					&& (o.get(allNums.get(i).get(1)).indexOf(kjData[1]) != -1)) {
				count++;
			}
		}
		return count;
	}

	public static int dxds2(String bet, String data) {
		String[] kjData = data.split(",");
		String[] betData = bet.split(",");
		ArrayList<ArrayList<String>> tmpData = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < betData.length; i++) {
			String[] subNums = betData[i].split("");
			ArrayList<String> subArray = new ArrayList<String>();
			for (int j = 0; j < subNums.length; j++) {
				subArray.add(subNums[j]);
			}
			tmpData.add(subArray);
		}
		ArrayList<ArrayList<String>> allNums = DescartesAlgorithm(tmpData);
		HashMap<String, String> o = new HashMap<String, String>();
		o.put("大", "06,07,08,09,10");
		o.put("小", "01,02,03,04,05");
		o.put("单", "01,03,05,07,09");
		o.put("双", "02,04,06,08,10");

		int count = 0;
		for (int i = 0; i < allNums.size(); i++) {
			if (o.get(allNums.get(i).get(0)).indexOf(kjData[0]) != -1) {
				count++;
			}
		}
		return count;
	}

	// 龙虎
	public static BigDecimal pk10lh(String bet, String kj, int num) {
		String[] kjData = kj.split(",");
		int val1 = Integer.parseInt(kjData[num - 1]);
		int val2 = Integer.parseInt(kjData[10 - num]);
		String[] betData = bet.split("");
		BigDecimal count = BigDecimal.ZERO;

		for (int i = 0, l = bet.length(); i < l; i++) {
			if ("龙".equals(betData[i])) {
				if (val1 > val2)
					count = count.add(new BigDecimal("1"));
			} else if ("虎".equals(betData[i])) {
				if (val1 < val2)
					count = count.add(new BigDecimal("1"));
			}
		}
		return count;
	}

	public static int rx(String bet, String kj, int num) {
		String reg = "/^(([\\d ]+))([\\d ]+)$/";
		if (bet.matches(reg)) {
			// 胆拖投注
			String[] m = bet.split(reg);
			String[] d = m[1].split(" ");

			for (int i = 0; i < d.length; i++) {
				if (kj.indexOf(d[i]) == -1) {
					return 0;
				}
			}

			ArrayList<String> tmp = new ArrayList<String>(Arrays.asList(m[2].split(" ")));
			ArrayList<ArrayList<String>> numbers = combine(tmp, num - d.length);
			int count = 0;
			for (int i = 0; i < numbers.size(); i++) {
				ArrayList<String> tmpNum = numbers.get(i);
				if (num < 5) {
					int j = 0;
					for (j = 0; j < tmpNum.size(); j++) {
						if (kj.indexOf(tmpNum.get(j)) == -1) {
							break;
						}
					}
					if (j == tmpNum.size() - 1) {
						count++;
					}

				} else {
					tmpNum.addAll(new ArrayList<String>(Arrays.asList(d)));
					String[] kjData = kj.split(",");
					int j = 0;
					for (j = 0; j < tmpNum.size(); j++) {
						for (int k = 0; k < kjData.length; k++) {
							if (tmpNum.get(j).indexOf(kjData[k]) == -1) {
								break;
							}
						}
					}
					if (j == tmpNum.size() - 1) {
						count++;
					}
				}
			}
			return count;
		} else {
			// 普通投注
			ArrayList<String> tmp = new ArrayList<String>(Arrays.asList(bet.split(" ")));
			ArrayList<ArrayList<String>> numbers = combine(tmp, num);
			int count = 0;
			for (int i = 0; i < numbers.size(); i++) {
				ArrayList<String> tmpNum = numbers.get(i);
				if (num < 5) {
					int j = 0;
					for (j = 0; j < tmpNum.size(); j++) {
						if (kj.indexOf(tmpNum.get(j)) == -1) {
							break;
						}
					}
					if (j == tmpNum.size() - 1) {
						count++;
					}
				} else {
					String[] kjData = kj.split(",");
					int j = 0;
					for (j = 0; j < tmpNum.size(); j++) {
						for (int k = 0; k < kjData.length; k++) {
							if (tmpNum.get(j).indexOf(kjData[k]) == -1) {
								break;
							}
						}
					}
					if (j == tmpNum.size() - 1) {
						count++;
					}
				}
			}
			return count;
		}
	}

	public static int zx(String bet, String kj) {
		String reg = "/^(([\\d ]+))([\\d ]+)$/";
		String[] kjData = kj.split(",");
		if (bet.matches(reg)) {
			// 胆拖投注
			String[] m = bet.split(reg);
			String[] d = m[1].split(" ");
			for (int i = 0; i < d.length; i++) {
				if (kj.indexOf(d[i]) == -1) {
					return 0;
				}
			}
			ArrayList<String> tmp = new ArrayList<String>(Arrays.asList(m[2].split(" ")));
			ArrayList<ArrayList<String>> numbers = combine(tmp, (kjData.length - tmp.size()));
			int count = 0;
			for (int i = 0; i < numbers.size(); i++) {
				ArrayList<String> tmpNum = numbers.get(i);
				int j = 0;
				for (j = 0; j < tmpNum.size(); j++) {
					if (kj.indexOf(tmpNum.get(j)) == -1) {
						break;
					}
				}
				if (j == tmpNum.size() - 1) {
					count++;
				}
			}
			return count;
		} else {
			// 普通投注
			ArrayList<String> tmp = new ArrayList<String>(Arrays.asList(bet.split(" ")));
			ArrayList<ArrayList<String>> numbers = combine(tmp, kjData.length);
			int count = 0;
			for (int i = 0; i < numbers.size(); i++) {
				ArrayList<String> tmpNum = numbers.get(i);
				int j = 0;
				for (j = 0; j < tmpNum.size(); j++) {
					if (kj.indexOf(tmpNum.get(j)) == -1) {
						break;
					}
				}
				if (j == tmpNum.size() - 1) {
					count++;
				}
			}
			return count;
		}
	}

	public static ArrayList DescartesAlgorithm(ArrayList al0) {
		ArrayList a0 = (ArrayList) al0.get(0);// l1
		ArrayList result = new ArrayList();// 组合的结果
		for (int i = 1; i < al0.size(); i++) {
			ArrayList a1 = (ArrayList) al0.get(i);
			ArrayList temp = new ArrayList();
			// 每次先计算两个集合的笛卡尔积，然后用其结果再与下一个计算
			for (int j = 0; j < a0.size(); j++) {
				for (int k = 0; k < a1.size(); k++) {
					ArrayList cut = new ArrayList();

					if (a0.get(j) instanceof ArrayList) {
						cut.addAll((ArrayList) a0.get(j));
					} else {
						cut.add(a0.get(j));
					}
					if (a1.get(k) instanceof ArrayList) {
						cut.addAll((ArrayList) a1.get(k));
					} else {
						cut.add(a1.get(k));
					}
					temp.add(cut);
				}
			}
			a0 = temp;
			if (i == al0.size() - 1) {
				result = temp;
			}
		}
		return result;
	}

	/**
	 * 组合算法
	 *
	 * @params Array arr 备选数组
	 * @params Int num
	 *
	 * @return Array 组合
	 *
	 *         useage: combine([1,2,3,4,5,6,7,8,9], 3);
	 */

	public static void subCombine(ArrayList<ArrayList<String>> r, ArrayList<String> t, ArrayList<String> a, int n) {
		if (n == 0) {
			r.add(new ArrayList<String>(t));
		} else {
			for (int i = 0, l = a.size(); i <= l - n; i++) {
				ArrayList<String> subT = new ArrayList<String>(t);
				subT.add(a.get(i));
				subCombine(r, subT, new ArrayList<String>(a.subList(i + 1, a.size())), n - 1);
			}
		}
	}

	public static ArrayList<ArrayList<String>> combine(ArrayList<String> arr, int num) {
		ArrayList<ArrayList<String>> r = new ArrayList<ArrayList<String>>();
		ArrayList<String> tmp = new ArrayList<String>();
		subCombine(r, tmp, arr, num);
		return r;
	}

	/**
	 * 排列算法
	 */
	public static void subPermutation(ArrayList<ArrayList<String>> r, ArrayList<String> t, ArrayList<String> a, int n) {
		if (n == 0) {
			r.add(new ArrayList<String>(t));
		} else {
			for (int i = 0, l = a.size(); i < l; i++) {
				ArrayList<String> subT = new ArrayList<String>(t);
				subT.add(a.get(i));
				ArrayList<String> subA = new ArrayList<String>(a.subList(0, i));
				subA.addAll(a.subList(i + 1, a.size()));
				subPermutation(r, subT, subA, n - 1);
			}
		}
	}

	public static ArrayList<ArrayList<String>> permutation(ArrayList<String> arr, int num) {
		ArrayList<ArrayList<String>> r = new ArrayList<ArrayList<String>>();
		ArrayList<String> tmp = new ArrayList<String>();
		subPermutation(r, tmp, arr, num);
		return r;
	}

	/**
	 * 分割字符串
	 *
	 * @params str 字符串
	 * @params len 长度
	 */
	public static ArrayList<String> strCut(String str, int len) {
		int strlen = str.length();
		if (strlen == 0)
			return null;
		double d = strlen;
		d /= len;
		int j = (int) Math.ceil(d);
		ArrayList<String> arr = new ArrayList<String>();

		for (int i = 0; i < j; i++) {
			arr.add(str.substring(i * len, ((i + 1) * len < strlen ? (i + 1) * len : strlen)));
		}
		return arr;
	}

	/**
	 * 两个数组，返回包含相同数字的个数
	 */
	public static int Sames(ArrayList<String> a, ArrayList<String> b) {
		int num = 0;
		for (int i = 0; i < a.size(); i++) {
			int zt = 0;
			for (int j = 0; j < b.size(); j++) {
				if (a.get(i).equals(b.get(j))) {
					zt = 1;
				}
			}
			if (zt == 1) {
				num += 1;
			}
		}
		return num;
	}

	/**
	 * 
	 */
	public static Integer Combination(int c, int b) {
		if (b < 0 || c < 0) {
			return null;
		}
		if (b == 0 || c == 0) {
			return 1;
		}
		if (b > c) {
			return 0;
		}
		if (b > c / 2) {
			b = c - b;
		}
		double a = 0;
		for (int i = c; i >= (c - b + 1); i--) {
			a += Math.log(i);
		}
		for (int i = b; i >= 1; i--) {
			a -= Math.log(i);
		}
		a = Math.exp(a);
		return (int) Math.round(a);
	}

	/**
	 * 过滤重复的数组
	 */
	public static ArrayList<String> filterArray(ArrayList<String> arrs) {
		int n = arrs.size();
		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (arrs.get(i).equals(arrs.get(j))) {
					arrs.set(i, null);
					break;
				}
			}
		}
		for (int i = 0; i < n; i++) {
			if (arrs.get(i) != null) {
				arr.add(arrs.get(i));
			}
		}
		return arr;
	}

	/**
	 * 是否有重复值
	 */
	public static boolean isRepeat(ArrayList<String> arr) {
		HashMap<String, Boolean> hash = new HashMap<String, Boolean>();
		for (int i = 0; i < arr.size(); i++) {
			if (hash.containsKey(arr.get(i)))
				return true;
			hash.put(arr.get(i), true);
		}
		return false;
	}
}