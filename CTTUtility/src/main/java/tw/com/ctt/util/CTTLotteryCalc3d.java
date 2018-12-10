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
public class CTTLotteryCalc3d extends CTTLotteryCalc {

	public static final String L3DDW1 = "3ddw1"; // 定位膽百位
	public static final String L3DDW2 = "3ddw2"; // 定位膽十位
	public static final String L3DDW3 = "3ddw3"; // 定位膽個位
	public static final String L3DQ2 = "3dq2"; // 前2直選
	public static final String L3DZQ2 = "3dzq2"; // 前2組選
	public static final String L3DH2 = "3dh2"; // 後2直選
	public static final String L3DZH2 = "3dzh2"; // 後2組選
	public static final String L3D3X = "3d3x"; // 3星直選
	public static final String L3D3XHZ = "3d3xhz"; // 3星直選和值
	public static final String L3DZ3D = "3dz3d"; // 組三單式
	public static final String L3DZ3F = "3dz3f"; // 組三複式
	public static final String L3DZXHZ = "3dzxhz"; // 三星組選和值
	public static final String L3DZ6 = "3dz6"; // 組六
	public static final String L3DBDD = "3dbdd"; // 一碼不定位
	public static final String L3DBDWQ32 = "3dbdwq32"; // 二碼不定位
	public static final String L3DLHH1 = "3dlhh1"; // 龍虎和百十(和)
	public static final String L3DLHH2 = "3dlhh2"; // 龍虎和百個(和)
	public static final String L3DLHH3 = "3dlhh3"; // 龍虎和個十(和)
	public static final String L3DLH1 = "3dlh1"; // 龍虎和百十(龍虎)
	public static final String L3DLH2 = "3dlh2"; // 龍虎和百個(龍虎)
	public static final String L3DLH3 = "3dlh3"; // 龍虎和個十(龍虎)

	@Override
	public Map<String, String> getResult(List<Integer> input) {
		lotteryResult = input;
		Map<String, String> result = new HashMap<String, String>();
		result.put(L3DDW1, "" + lotteryResult.get(0)); // 定位膽百位
		result.put(L3DDW2, "" + lotteryResult.get(1)); // 定位膽十位
		result.put(L3DDW3, "" + lotteryResult.get(2)); // 定位膽個位
		result.put(L3DQ2, "" + lotteryResult.get(0) + "|" + lotteryResult.get(1)); // 前2直選
		result.put(L3DZQ2, "" + calL3DZ2(0, 1)); // 前2組選
		result.put(L3DH2, "" + lotteryResult.get(1) + "|" + lotteryResult.get(2)); // 後2直選
		result.put(L3DZH2, "" + calL3DZ2(1, 2)); // 後2組選
		result.put(L3D3X, "" + lotteryResult.get(0) + "|" + lotteryResult.get(1) + "|" + lotteryResult.get(2)); // 3星直選
		// result.put(L3D3XHZ, "" +(lotteryResult.get(0) + lotteryResult.get(1)+
		// lotteryResult.get(2))); //3星直選和值
		result.putAll(calL3D3XHZ()); // 3星直選和值
		result.put(L3DZ3D, "" + calL3DZ3D()); // 組三單式
		result.put(L3DZ3F, "" + calL3DZ3F()); // 組三複式
		// result.put(L3DZXHZ, "" + calL3DZXHZ()); //三星組選和值
		result.putAll(calL3DZXHZ()); //三星組選和值
		result.put(L3DZ6, "" + calL3DZ6()); // 組六
		result.put(L3DBDD, "" + calL3BDD()); // 一碼不定位
		result.put(L3DBDWQ32, "" + calL3DBDWQ32()); // 二碼不定位
		result.put(L3DLHH1, "" + calL3DLHH(0, 1)); // 龍虎和百十(和)
		result.put(L3DLHH2, "" + calL3DLHH(0, 2)); // 龍虎和百個(和)
		result.put(L3DLHH3, "" + calL3DLHH(1, 2)); // 龍虎和十個(和)
		result.put(L3DLH1, "" + calL3DLH(0, 1)); // 龍虎和百十(龍虎)
		result.put(L3DLH2, "" + calL3DLH(0, 2)); // 龍虎和百個(龍虎)
		result.put(L3DLH3, "" + calL3DLH(1, 2)); // 龍虎和十個(龍虎)
		return result;
	}

	private Map<String, String> calL3DZXHZ() {
		Map<String, String> result = new HashMap<String, String>();
			String tmpKey = L3DZXHZ;
			if ("-".equals(calL3DZ3F())) {
				result.put(tmpKey, calEachL3DZXHZ() + "|-");
			} else {
				result.put(tmpKey, calEachL3DZXHZ() + "|1");
			}
		return result;
	}

	private String calEachL3DZXHZ() {
		Integer sum = 0;
		List<Integer> tmp = new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			tmp.add(lotteryResult.get(i));
			sum += lotteryResult.get(i);
		}
		Set<Integer> tmpSet = new TreeSet<Integer>(tmp);
		if (tmpSet.size() != 1) {
			return ""+sum;
		}
		return "-";
	}

	private Map<String, String> calL3D3XHZ() {
		Map<String, String> result = new HashMap<String, String>();
		String tmpKey = L3D3XHZ;
		result.put(tmpKey, calEachL3D3XHZ());
		return result;
	}

	private String calEachL3D3XHZ() {
		Integer sum = 0;
		for (int i = 0; i < 3; i++) {
			sum += lotteryResult.get(i);
		}
		return ""+sum;
	}

	private String calL3DZ2(int index1, int index2) {
		if (lotteryResult.get(index1) < lotteryResult.get(index2)) {
			return "" + lotteryResult.get(index1) + "," + lotteryResult.get(index2);
		} else if (lotteryResult.get(index1) > lotteryResult.get(index2)) {
			return "" + lotteryResult.get(index2) + "," + lotteryResult.get(index1);
		} else {
			return "-";
		}
	}

	private String calL3DZ3D() {
		int num1 = lotteryResult.get(0);
		int num2 = lotteryResult.get(1);
		int num3 = lotteryResult.get(2);

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
				return "" + num1 + "|" + num2;
			}
		}

	}

	private String calL3DZ3F() {
		Set<Integer> set = new TreeSet<Integer>();
		set.add(lotteryResult.get(0));
		set.add(lotteryResult.get(1));
		set.add(lotteryResult.get(2));

		if (set.size() == 2) {
			List<Integer> tmp = new ArrayList<Integer>();
			Iterator<Integer> iterator = set.iterator();
			while (iterator.hasNext()) {
				tmp.add(iterator.next());
			}
			arraySort(tmp);
			return "" + tmp.get(0) + "," + tmp.get(1);
		}
		return "-";
	}

	// private String calL3DZXHZ() {
	// int num1 = lotteryResult.get(0);
	// int num2 = lotteryResult.get(1);
	// int num3 = lotteryResult.get(2);
	//
	// if((num1 == num2) && (num2 == num3)) {
	// return "-|-";
	// }else {
	// TreeSet<Integer> tmp = new TreeSet<Integer>();
	// tmp.add(num1);
	// tmp.add(num2);
	// tmp.add(num3);
	// // "1"是開獎號碼是組三，"-"是開獎號碼是組六
	// if(tmp.size()==2) {
	// return "" + (num1 + num2 + num3) + "|" + "1";
	// }else {
	// return "" + (num1 + num2 + num3) + "|" + "-";
	// }
	// }
	// }

	private String calL3DZ6() {
		int num1 = lotteryResult.get(0);
		int num2 = lotteryResult.get(1);
		int num3 = lotteryResult.get(2);

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

	private List<Integer> sortSet() {
		Set<Integer> set = new TreeSet<Integer>();
		set.add(lotteryResult.get(0));
		set.add(lotteryResult.get(1));
		set.add(lotteryResult.get(2));

		List<Integer> tmp = new ArrayList<Integer>();
		Iterator<Integer> iterator = set.iterator();
		while (iterator.hasNext()) {
			tmp.add(iterator.next());
		}
		arraySort(tmp);
		return tmp;
	}

	private String calL3BDD() {
		List<Integer> result = sortSet();
		if (result.size() == 3) {
			return "" + result.get(0) + "_" + result.get(1) + "_" + result.get(2);
		} else if (result.size() == 2) {
			return "" + result.get(0) + "_" + result.get(1);
		} else {
			return "" + result.get(0);
		}
	}

	private String calL3DBDWQ32() {
		List<Integer> result = sortSet();
		if (result.size() == 3) {
			return "" + result.get(0) + "," + result.get(1) + "_" + result.get(1) + "," + result.get(2) + "_"
					+ result.get(0) + "," + result.get(2);
		} else if (result.size() == 2) {
			return "" + result.get(0) + "," + result.get(1);
		} else {
			return "-";
		}
	}

	// "1"為中獎，"-"為沒中
	private String calL3DLHH(int index1, int index2) {
		if (lotteryResult.get(index1) == lotteryResult.get(index2)) {
			return "1";
		} else {
			return "-";
		}
	}

	// "l"代表開獎結果為"龍"，"h"代表開獎結果為"虎"，"a"代表開獎結果為"和"
	private String calL3DLH(int index1, int index2) {
		if (lotteryResult.get(index1) > lotteryResult.get(index2)) {
			return "l";
		} else if (lotteryResult.get(index1) < lotteryResult.get(index2)) {
			return "h";
		} else {
			return "-";
		}
	}

}