package tw.com.ctt.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 彩票注數計算機
 * 
 * @author Quanto
 * @since 2018.05.03
 */
public class CTTLotteryCalcMain {
	public static void main(String[] argvs) {
		
	}
	
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
	
//	public static HashMap<String,ArrayList<String>> calc_ssc(String bet) {
//		HashMap<String,ArrayList<String>> result = new HashMap<String,ArrayList<String>>();
//		return result;
//	}
//	
//	public static HashMap<String,ArrayList<String>> calc_115(String bet) {
//		HashMap<String,ArrayList<String>> result = new HashMap<String,ArrayList<String>>();
//		return result;
//	}
//	
//	public static HashMap<String,ArrayList<String>> calc_k3(String bet) {
//		HashMap<String,ArrayList<String>> result = new HashMap<String,ArrayList<String>>();
//		return result;
//	}
//	
//	public static HashMap<String,ArrayList<String>> calc_3d(String bet) {
//		HashMap<String,ArrayList<String>> result = new HashMap<String,ArrayList<String>>();
//		return result;
//	}
//	
//	public static HashMap<String,ArrayList<String>> calc_p3p5(String bet) {
//		HashMap<String,ArrayList<String>> result = new HashMap<String,ArrayList<String>>();
//		return result;
//	}
//	
//	public static HashMap<String,ArrayList<String>> calc_pk10(String bet) {
//		HashMap<String,ArrayList<String>> result = new HashMap<String,ArrayList<String>>();
//		return result;
//	}

}