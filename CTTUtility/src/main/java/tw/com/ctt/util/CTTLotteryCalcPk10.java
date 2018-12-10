package tw.com.ctt.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;


/**
 * 彩票注數計算機
 * 
 * @author Quanto
 * @since 2018.05.03
 */
public class CTTLotteryCalcPk10 extends CTTLotteryCalc {
	
	Map<Integer, List<Integer>> paramsOfLPK10GY2;
	
	private static final Logger LOG = LogManager.getLogger(CTTLotteryCalcPk10.class.getName());
	
	public static final String LPK10GY2 = "pk10_gy2";
	public static final String LPK10GY2DDS1 = "pk10_gy2dds_1";
	public static final String LPK10GY2DDS2 = "pk10_gy2dds_2";
	public static final String LPK10GY2DDS3 = "pk10_gy2dds_3";
	public static final String LPK10GY2DDS4 = "pk10_gy2dds_4";
	public static final String LPK10SORT1 = "pk10_sort1";
	public static final String LPK10SORT2 = "pk10_sort2";
	public static final String LPK10SORT3 = "pk10_sort3";
	public static final String LPK10SORT4 = "pk10_sort4";
	public static final String LPK10SORT5 = "pk10_sort5";
	public static final String LPK10SORT6 = "pk10_sort6";
	public static final String LPK10SORT7 = "pk10_sort7";
	public static final String LPK10SORT8 = "pk10_sort8";
	public static final String LPK10SORT9 = "pk10_sort9";
	public static final String LPK10SORT10 = "pk10_sort10";
	public static final String LPK10LH_1_10 = "pk10_lh_1_10";
	public static final String LPK10LH_2_9 = "pk10_lh_2_9";
	public static final String LPK10LH_3_8 = "pk10_lh_3_8";
	public static final String LPK10LH_4_7 = "pk10_lh_4_7";
	public static final String LPK10LH_5_6 = "pk10_lh_5_6";
	public static final String LPK10Q2 = "pk10_q2";
	public static final String LPK10H2 = "pk10_h2";
	public static final String LPK10Q3 = "pk10_q3";
	public static final String LPK10H3 = "pk10_h3";

	public CTTLotteryCalcPk10() {
		paramsOfLPK10GY2 = new HashMap<Integer, List<Integer>>();
		List<Integer> tmp = new ArrayList<Integer>();
		tmp.add(3);
		tmp.add(4);
		tmp.add(18);
		tmp.add(19);
		paramsOfLPK10GY2.put(1,	tmp);
		tmp = new ArrayList<Integer>();
		tmp.add(5);
		tmp.add(6);
		tmp.add(16);
		tmp.add(17);
		paramsOfLPK10GY2.put(2,	tmp);
		tmp = new ArrayList<Integer>();
		tmp.add(7);
		tmp.add(8);
		tmp.add(14);
		tmp.add(15);
		paramsOfLPK10GY2.put(3,	tmp);
		tmp = new ArrayList<Integer>();
		tmp.add(9);
		tmp.add(10);
		tmp.add(12);
		tmp.add(13);
		paramsOfLPK10GY2.put(4,	tmp);
		tmp = new ArrayList<Integer>();
		tmp.add(11);
		paramsOfLPK10GY2.put(5,	tmp);
	}
	@Override
	public Map<String, String> getResult(List<Integer> input){
		lotteryResult = input;
		Map<String , String> result = new HashMap<String, String>();
				
		
		result.putAll(calLPK10GY2()); //冠亞和值
		List<String> tmp0 = calLPK10GY2DDS();
		result.put(LPK10GY2DDS1, tmp0.get(0));	//預測冠亞_大
		result.put(LPK10GY2DDS2, tmp0.get(1));	//預測冠亞_小
		result.put(LPK10GY2DDS3, tmp0.get(2));	//預測冠亞_單	
		result.put(LPK10GY2DDS4, tmp0.get(3));	//預測冠亞_雙	
		result.put(LPK10SORT1, "" + lotteryResult.get(0));	//排名第一名
		result.put(LPK10SORT2, "" + lotteryResult.get(1));	//排名第二名
		result.put(LPK10SORT3, "" + lotteryResult.get(2));	//排名第三名
		result.put(LPK10SORT4, "" + lotteryResult.get(3));	//排名第四名
		result.put(LPK10SORT5, "" + lotteryResult.get(4));	//排名第五名
		result.put(LPK10SORT6, "" + lotteryResult.get(5));	//排名第六名
		result.put(LPK10SORT7, "" + lotteryResult.get(6));	//排名第七名
		result.put(LPK10SORT8, "" + lotteryResult.get(7));	//排名第八名
		result.put(LPK10SORT9, "" + lotteryResult.get(8));	//排名第九名
		result.put(LPK10SORT10, "" + lotteryResult.get(9)); //排名第十名
		List<String> tmp1 = calLPK10LH();
		result.put(LPK10LH_1_10, tmp1.get(0)); //龍虎:第一名比第十名
		result.put(LPK10LH_2_9, tmp1.get(1)); //龍虎:第二名比第九名
		result.put(LPK10LH_3_8, tmp1.get(2)); //龍虎:第三名比第八名
		result.put(LPK10LH_4_7, tmp1.get(3)); //龍虎:第四名比第七名
		result.put(LPK10LH_5_6, tmp1.get(4)); //龍虎:第五名比第六名
		result.put(LPK10Q2, "" + lotteryResult.get(0) + "|" +lotteryResult.get(1)); //前2星
		result.put(LPK10H2, "" + lotteryResult.get(8) + "|" +lotteryResult.get(9)); //後2星
		result.put(LPK10Q3, "" + lotteryResult.get(0) + "|" +lotteryResult.get(1)+ "|" +lotteryResult.get(2)); //前3星
		result.put(LPK10H3, "" + lotteryResult.get(7) + "|" +lotteryResult.get(8)+ "|" +lotteryResult.get(9)); //後3星
		//
//		for(Map.Entry<String, String>entry:result.entrySet()) {
//			LOG.debug(entry.getKey()+":"+entry.getValue());
//		}
		return result;
	}
	
	private Map<String, String> calLPK10GY2() {
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 1; i <= paramsOfLPK10GY2.size(); i++) {
			String tmpKey = LPK10GY2 + "_" + i;
			result.put(tmpKey, calLEachPK10GY2(paramsOfLPK10GY2.get(i)));
		}
		return result;
	}

	private String calLEachPK10GY2(List<Integer> ans) {
		Integer sum = lotteryResult.get(0) + lotteryResult.get(1);		
		for(int i =0; i< ans.size(); i++) {
			if(ans.get(i) == sum) {
				return ""+sum;
			}
		}
		return "-";
	}

	private List<String> calLPK10GY2DDS(){	
		List<String> result = new ArrayList<String>();
		int i = lotteryResult.get(0) + lotteryResult.get(1);
		// 冠亞和值比較大小,單雙，比較結果"1"為中獎，"-"為沒中獎
		if(i>10) {
			result.add("1");
			result.add("-");
		}else {
			result.add("-");
			result.add("1");
		}
		//
		if(i%2 != 0) {
			result.add("1");
			result.add("-");
		}else {
			result.add("-");
			result.add("1");
		}
//		LOG.debug(result);
		return result;
	}
	//龍虎
	private List<String> calLPK10LH(){	
		List<String> result = new ArrayList<String>();
		// "l"代表開獎結果為"龍"，"h"代表開獎結果為"虎"
		for(int i = 0; i<=4; i++) {
			int opponent = 9-i;
			if(lotteryResult.get(i) >lotteryResult.get(opponent)) {
				result.add("l");
			}else {
				result.add("h");
			}
		}
		return result;
	}

}