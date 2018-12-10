package tw.com.ctt.util;

import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

/**
 * 彩票注數計算機
 * 
 * @author Quanto
 * @since 2018.05.03
 */
public class CTTLotteryCalc {
	
	List<Integer> lotteryResult;
		
	protected void arraySort(List<Integer> in) {
		Collections.sort(in, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 == null || o2 == null)
					return 0;
				if (o1 > o2)
					return 1;
				else if (o1 < o2)
					return -1;
				else
					return 0;
			}
		});
	}
	
	
	protected void arraySortMap(List<Map> in) {
		Collections.sort(in, new Comparator<Map>() {
			@Override
			public int compare(Map o1, Map o2) {
				if (o1 == null || o2 == null) {
					return 0;
				}if( !((Map)o1).containsKey("kjTime")|| !((Map)o2).containsKey("kjTime")) {
					return 0;
				}				
				try {
					if( Integer.parseInt(""+((Map)o1).get("kjTime")) > Integer.parseInt(""+((Map)o2).get("kjTime"))) {
						return 1;
					}else if( Integer.parseInt(""+((Map)o1).get("kjTime")) < Integer.parseInt(""+((Map)o2).get("kjTime"))) {
						return -1;
					}else {
						return 0;
					}
				}catch(Exception e) {
					return 0;
				}
			}
		});
	}
	
	
	public Map<String, String> getResult(List<Integer> input) {
		return null;
	}
}