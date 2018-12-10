package tw.com.ctt.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 彩票注數計算機
 * 
 * @author Quanto
 * @since 2018.05.03
 */
public class CTTLotteryCalcK3 extends CTTLotteryCalc {
	Map<Integer, List<Integer>> paramsOfLK3HZ;

	public static final String LK3HZ = "k3hz";
	public static final String LK33DX = "k33dx";
	public static final String LK33TX = "k33tx";
	public static final String LK22DX = "k22dx";
	public static final String LK22FX = "k22fx";
	// public static final String LK22X = "k22x";//手動?
	public static final String LK33X = "k33x";
	public static final String LK32X = "k32x";
	public static final String LK33lTX = "k33ltx";
	public static final String LK3DX_D = "k3dx_d";
	public static final String LK3DX_X = "k3dx_x";
	public static final String LK3DS_D = "k3ds_d";
	public static final String LK3DS_S = "k3ds_s";
	public static final String LK3CZW = "k3czw";
	public static final String LK3RED = "k3red";
	public static final String LK3BLACK = "k3black";

	public CTTLotteryCalcK3() {
		paramsOfLK3HZ = new HashMap<Integer, List<Integer>>();
		List<Integer> tmp = new ArrayList<Integer>();
		tmp.add(3);
		tmp.add(18);
		paramsOfLK3HZ.put(1, tmp);
		tmp = new ArrayList<Integer>();
		tmp.add(4);
		tmp.add(17);
		paramsOfLK3HZ.put(2, tmp);
		tmp = new ArrayList<Integer>();
		tmp.add(5);
		tmp.add(16);
		paramsOfLK3HZ.put(3, tmp);
		tmp = new ArrayList<Integer>();
		tmp.add(6);
		tmp.add(15);
		paramsOfLK3HZ.put(4, tmp);
		tmp = new ArrayList<Integer>();
		tmp.add(7);
		tmp.add(14);
		paramsOfLK3HZ.put(5, tmp);
		tmp = new ArrayList<Integer>();
		tmp.add(8);
		tmp.add(13);
		paramsOfLK3HZ.put(6, tmp);
		tmp = new ArrayList<Integer>();
		tmp.add(9);
		tmp.add(12);
		paramsOfLK3HZ.put(7, tmp);
		tmp = new ArrayList<Integer>();
		tmp.add(10);
		tmp.add(11);
		paramsOfLK3HZ.put(8, tmp);
	}

	@Override
	public Map<String, String> getResult(List<Integer> input) {
		lotteryResult = input;
		int firstNumber = lotteryResult.get(0);
		int secondNumber = lotteryResult.get(1);
		int thirdNumber = lotteryResult.get(2);

		Map<String, String> result = new HashMap<String, String>();
		
		result.putAll(calLK3HZ()); // 和值
		//result.put(LK3HZ, "" + (lotteryResult.get(0) + lotteryResult.get(1) + lotteryResult.get(2))); // 和值
		result.put(LK33DX, calLK33(firstNumber, secondNumber, thirdNumber, false)); // 三同號
		result.put(LK33TX, calLK33(firstNumber, secondNumber, thirdNumber, true)); // 三同號通選
		result.put(LK22DX, calLK22(firstNumber, secondNumber, thirdNumber)); // 二同號單選,手動
		result.put(LK22FX, calLK22fx(firstNumber, secondNumber, thirdNumber)); // 二同號複選
		result.put(LK33X, calLK33x(firstNumber, secondNumber, thirdNumber)); // 三不同號
		result.put(LK32X, calLK32x(firstNumber, secondNumber, thirdNumber)); // 二不同號
		result.put(LK33lTX, calLK33ltx()); // 三連號通選
		result.put(LK3DX_D, calLK3dx(firstNumber, secondNumber, thirdNumber, true)); // 大
		result.put(LK3DX_X, calLK3dx(firstNumber, secondNumber, thirdNumber, false)); // 小
		result.put(LK3DS_D, calLK3ds(firstNumber, secondNumber, thirdNumber, true)); // 單
		result.put(LK3DS_S, calLK3ds(firstNumber, secondNumber, thirdNumber, false)); // 雙
		result.put(LK3CZW, calLK3czw(firstNumber, secondNumber, thirdNumber)); // 猜必出
		result.put(LK3RED, calK3red(firstNumber, secondNumber, thirdNumber)); // 猜紅色
		result.put(LK3BLACK, calK3black(firstNumber, secondNumber, thirdNumber)); // 猜黑色
		return result;
	}
	
	
	
	private Map<String, String> calLK3HZ() {
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 1; i <= paramsOfLK3HZ.size(); i++) {
			String tmpKey = LK3HZ + "_" + i;
			result.put(tmpKey, calEachLK3HZ(paramsOfLK3HZ.get(i)));
		}
		return result;
	}

	private String calEachLK3HZ(List<Integer> ans) {
		Integer sum = lotteryResult.get(0) + lotteryResult.get(1) + lotteryResult.get(2);
		for(int i =0; i< ans.size(); i++) {
			if(ans.get(i) == sum) {
				return ""+sum;
			}
		}
		return "-";
	}

	private String calLK33(int num1, int num2, int num3, boolean isLK33TX) {
		if (num1 == num2 && num2 == num3) {
			if (isLK33TX) {
				return "1";
			} else {
				return "" + num1 + num2 + num3;
			}
		}
		return "-";
	}

	private String calLK22(int num1, int num2, int num3) {
		if (num1 == num2) {
			if (num2 == num3) {
				return "-";
			}
		} else {
			if (num2 != num3) {
				if (num1 != num3) {
					return "-";
				}
			}
		}

		if (num1 == num2) {
			return "" + num1 + "|" + num3;
		} else {
			if (num2 == num3) {
				return "" + num2 + "|" + num1;
			} else {
				return "" + num1 + "|" + num2; // 不可能會跳號 可不用
			}
		}

	}

	private String calLK22fx(int num1, int num2, int num3) {
		if (num1 != num2) {
			if (num2 == num3) {
				return "" + num2 + num3 + "*";
			} else {
				if (num1 == num3) {
					return "" + num1 + num3 + "*";
				}
			}
		} else {
			if (num2 != num3) {
				return "" + num1 + num2 + "*";
			}
		}
		return "-";
	}

	private String calLK33x(int num1, int num2, int num3) {
		if (num1 != num2) {
			if (num2 != num3) {
				if (num1 != num3) {
					int[] arr = { num1, num2, num3 };
					Arrays.sort(arr);
					return "" + arr[0] + "," + arr[1] + "," + arr[2];
				}
			}
		}
		return "-";
	}

	private String calLK32x(int num1, int num2, int num3) {
		TreeSet<Integer> set = new TreeSet<Integer>();
		set.add(num1);
		set.add(num2);
		set.add(num3);

		if (set.size() == 1) {
			return "-";
		} else {
			List<Integer> tmp = new ArrayList<Integer>();
			Iterator<Integer> iterator = set.iterator();
			while (iterator.hasNext()) {
				tmp.add(iterator.next());
			}
			arraySort(tmp);
			if (tmp.size() == 3) {
				return "" + tmp.get(0) + "," + tmp.get(1) + "|" + tmp.get(1) + "," + tmp.get(2) + "|" + tmp.get(0) + ","
						+ tmp.get(2);
			} else {
				return "" + tmp.get(0) + "," + tmp.get(1);
			}
		}
	}

	//
	private String calLK33ltx() {
		List<Integer> tmp = new ArrayList<Integer>();
		tmp.add(lotteryResult.get(0));
		tmp.add(lotteryResult.get(1));
		tmp.add(lotteryResult.get(2));
		arraySort(tmp);
		if (tmp.get(2) - tmp.get(1) != 0) {
			if ((tmp.get(2) - tmp.get(1)) == (tmp.get(1) - tmp.get(0))) {
				return "1";
			}
		}
		return "-";

	}

	// "1"為中獎,"-"為沒中
	private String calLK3dx(int num1, int num2, int num3, boolean isLK3DX) {
		int add = lotteryResult.get(0) + lotteryResult.get(1) + lotteryResult.get(2);
		if (isLK3DX) {
			if (add > 10) {
				return "1";
			}
			return "-";
		} else {
			if (add <= 10) {
				return "1";
			}
			return "-";
		}

	}

	// "1"為中獎,"-"為沒中
	private String calLK3ds(int num1, int num2, int num3, boolean isLK3DS) {
		int add = lotteryResult.get(0) + lotteryResult.get(1) + lotteryResult.get(2);

		if (isLK3DS) {
			if (add % 2 == 0) {
				return "-";
			}
			return "1";
		} else {
			if (add % 2 == 0) {
				return "1";
			}
			return "-";
		}
	}

	private String calLK3czw(int num1, int num2, int num3) {
		Set<Integer> set = new TreeSet<Integer>();
		set.add(num1);
		set.add(num2);
		set.add(num3);

		List<Integer> tmp = new ArrayList<Integer>();
		Iterator<Integer> iterator = set.iterator();
		while (iterator.hasNext()) {
			tmp.add(iterator.next());
		}
		arraySort(tmp);
		if (tmp.size() == 3) {
			return "" + tmp.get(0) + "_" + tmp.get(1) + "_" + tmp.get(2);
		} else if (tmp.size() == 2) {
			return "" + tmp.get(0) + "_" + tmp.get(1);
		} else {
			return "" + tmp.get(0);
		}

	}

	private String calK3red(int num1, int num2, int num3) {
		Set<Integer> set = new TreeSet<Integer>();
		set.add(num1);
		set.add(num2);
		set.add(num3);

		List<Integer> tmp = new ArrayList<Integer>();
		Iterator<Integer> iterator = set.iterator();
		while (iterator.hasNext()) {
			tmp.add(iterator.next());
		}

		String result = "1";
		for (int i : tmp) {
			if (i == 4 || i == 1) {
				result = "1";
			} else {
				result = "-";
				break;
			}
		}
		return result;
	}

	private String calK3black(int num1, int num2, int num3) {
		Set<Integer> set = new TreeSet<Integer>();
		set.add(num1);
		set.add(num2);
		set.add(num3);

		List<Integer> tmp = new ArrayList<Integer>();
		Iterator<Integer> iterator = set.iterator();
		while (iterator.hasNext()) {
			tmp.add(iterator.next());
		}

		String result = "1";
		for (int i : tmp) {
			if (i == 4 || i == 1) {
				result = "-";
				break;
			} else {
				result = "1";
			}
		}
		return result;
	}

}