package tw.com.ctt.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.runners.JUnit4;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnit4.class)
public class CTTLotteryCalc3dAutotest {
	ArrayList<String> answer;
	String testCondition;
	@Before
	public void before() {
		
	}
	
	@After
	public void after() {
		answer = null;
		testCondition = null;
	}

	@Test
	public void	testssc() {
		List<Integer> data = new ArrayList<Integer>();
		answer = new ArrayList<String>();
		//期望答案
		String[] ans = {"3","6","9","4","5","6","7","8","9","6","8","9","2","1","3","3|3","6|2","3|1","6|4","0|0","-","2,6","1,3","4,6","-","3|6","2|6","1|2","4|8","0|0","3,6","2,6","1,2","4,8","-","3|3|3","3|6|9","0|0|3","3|0|0","9|6|3","9","18","3","0","3","1,6","1,6","1,2","1,2","1,2","1|6","6|1","1|2","1|2","2|1","-|-","5|1","6|-","5|1","5|-","3,6,9","4,5,6","7,8,9","6,8,9","1,2,3","3_6_9","4_5_6","7_8_9","6_8_9","1_2_3","3,6_6,9_3,9","4,5_5,6_4,6","7,8_8,9_7,9","6,9","-","-","-","1","1","-","-","-","1","-","1","-","-","-","-","1","h","l","-","-","l","h","h","-","l","-","h","h","l","l","-"};
		//
		//測試資料
		testCondition ="{\"calc\":[{\"rule\":\"3ddw1\",\"kj\":\"3,6,9\"},"+"{\"rule\":\"3ddw2\",\"kj\":\"3,6,9\"},"+"{\"rule\":\"3ddw3\",\"kj\":\"3,6,9\"},"+"{\"rule\":\"3ddw1\",\"kj\":\"4,5,6\"},"+"{\"rule\":\"3ddw2\",\"kj\":\"4,5,6\"},"+"{\"rule\":\"3ddw3\",\"kj\":\"4,5,6\"},"+"{\"rule\":\"3ddw1\",\"kj\":\"7,8,9\"},"+"{\"rule\":\"3ddw2\",\"kj\":\"7,8,9\"},"+"{\"rule\":\"3ddw3\",\"kj\":\"7,8,9\"},"+"{\"rule\":\"3ddw1\",\"kj\":\"6,8,9\"},"+"{\"rule\":\"3ddw2\",\"kj\":\"6,8,9\"},"+"{\"rule\":\"3ddw3\",\"kj\":\"6,8,9\"},"+"{\"rule\":\"3ddw1\",\"kj\":\"2,1,3\"},"+"{\"rule\":\"3ddw2\",\"kj\":\"2,1,3\"},"+"{\"rule\":\"3ddw3\",\"kj\":\"2,1,3\"},"+"{\"rule\":\"3dq2\",\"kj\":\"3,3,6\"},"+"{\"rule\":\"3dq2\",\"kj\":\"6,2,6\"},"+"{\"rule\":\"3dq2\",\"kj\":\"3,1,2\"},"+"{\"rule\":\"3dq2\",\"kj\":\"6,4,8\"},"+"{\"rule\":\"3dq2\",\"kj\":\"0,0,0\"},"+"{\"rule\":\"3dzq2\",\"kj\":\"3,3,6\"},"+"{\"rule\":\"3dzq2\",\"kj\":\"6,2,6\"},"+"{\"rule\":\"3dzq2\",\"kj\":\"3,1,2\"},"+"{\"rule\":\"3dzq2\",\"kj\":\"6,4,8\"},"+"{\"rule\":\"3dzq2\",\"kj\":\"0,0,0\"},"+"{\"rule\":\"3dh2\",\"kj\":\"3,3,6\"},"+"{\"rule\":\"3dh2\",\"kj\":\"6,2,6\"},"+"{\"rule\":\"3dh2\",\"kj\":\"3,1,2\"},"+"{\"rule\":\"3dh2\",\"kj\":\"6,4,8\"},"+"{\"rule\":\"3dh2\",\"kj\":\"0,0,0\"},"+"{\"rule\":\"3dzh2\",\"kj\":\"3,3,6\"},"+"{\"rule\":\"3dzh2\",\"kj\":\"6,2,6\"},"+"{\"rule\":\"3dzh2\",\"kj\":\"3,1,2\"},"+"{\"rule\":\"3dzh2\",\"kj\":\"6,4,8\"},"+"{\"rule\":\"3dzh2\",\"kj\":\"0,0,0\"},"+"{\"rule\":\"3d3x\",\"kj\":\"3,3,3\"},"+"{\"rule\":\"3d3x\",\"kj\":\"3,6,9\"},"+"{\"rule\":\"3d3x\",\"kj\":\"0,0,3\"},"+"{\"rule\":\"3d3x\",\"kj\":\"3,0,0\"},"+"{\"rule\":\"3d3x\",\"kj\":\"9,6,3\"},"+"{\"rule\":\"3d3xhz\",\"kj\":\"3,3,3\"},"+"{\"rule\":\"3d3xhz\",\"kj\":\"3,6,9\"},"+"{\"rule\":\"3d3xhz\",\"kj\":\"1,1,1\"},"+"{\"rule\":\"3d3xhz\",\"kj\":\"0,0,0\"},"+"{\"rule\":\"3d3xhz\",\"kj\":\"0,0,3\"},"+"{\"rule\":\"3dz3f\",\"kj\":\"1,1,6\"},"+"{\"rule\":\"3dz3f\",\"kj\":\"6,6,1\"},"+"{\"rule\":\"3dz3f\",\"kj\":\"1,2,1\"},"+"{\"rule\":\"3dz3f\",\"kj\":\"2,1,1\"},"+"{\"rule\":\"3dz3f\",\"kj\":\"1,2,2\"},"+"{\"rule\":\"3dz3d\",\"kj\":\"1,1,6\"},"+"{\"rule\":\"3dz3d\",\"kj\":\"6,6,1\"},"+"{\"rule\":\"3dz3d\",\"kj\":\"1,2,1\"},"+"{\"rule\":\"3dz3d\",\"kj\":\"2,1,1\"},"+"{\"rule\":\"3dz3d\",\"kj\":\"1,2,2\"},"+"{\"rule\":\"3dzxhz\",\"kj\":\"0,0,0\"},"+"{\"rule\":\"3dzxhz\",\"kj\":\"2,2,1\"},"+"{\"rule\":\"3dzxhz\",\"kj\":\"3,2,1\"},"+"{\"rule\":\"3dzxhz\",\"kj\":\"0,0,5\"},"+"{\"rule\":\"3dzxhz\",\"kj\":\"4,0,1\"},"+"{\"rule\":\"3dz6\",\"kj\":\"3,6,9\"},"+"{\"rule\":\"3dz6\",\"kj\":\"4,5,6\"},"+"{\"rule\":\"3dz6\",\"kj\":\"7,8,9\"},"+"{\"rule\":\"3dz6\",\"kj\":\"6,8,9\"},"+"{\"rule\":\"3dz6\",\"kj\":\"2,1,3\"},"+"{\"rule\":\"3dbdd\",\"kj\":\"3,6,9\"},"+"{\"rule\":\"3dbdd\",\"kj\":\"4,5,6\"},"+"{\"rule\":\"3dbdd\",\"kj\":\"7,8,9\"},"+"{\"rule\":\"3dbdd\",\"kj\":\"6,8,9\"},"+"{\"rule\":\"3dbdd\",\"kj\":\"2,1,3\"},"+"{\"rule\":\"3dbdwq32\",\"kj\":\"3,6,9\"},"+"{\"rule\":\"3dbdwq32\",\"kj\":\"4,5,6\"},"+"{\"rule\":\"3dbdwq32\",\"kj\":\"7,8,9\"},"+"{\"rule\":\"3dbdwq32\",\"kj\":\"6,6,9\"},"+"{\"rule\":\"3dbdwq32\",\"kj\":\"1,1,1\"},"+"{\"rule\":\"3dlhh1\",\"kj\":\"3,6,9\"},"+"{\"rule\":\"3dlhh1\",\"kj\":\"2,1,3\"},"+"{\"rule\":\"3dlhh1\",\"kj\":\"1,1,6\"},"+"{\"rule\":\"3dlhh1\",\"kj\":\"0,0,9\"},"+"{\"rule\":\"3dlhh1\",\"kj\":\"3,0,0\"},"+"{\"rule\":\"3dlhh2\",\"kj\":\"3,6,9\"},"+"{\"rule\":\"3dlhh2\",\"kj\":\"4,5,6\"},"+"{\"rule\":\"3dlhh2\",\"kj\":\"7,8,7\"},"+"{\"rule\":\"3dlhh2\",\"kj\":\"9,8,6\"},"+"{\"rule\":\"3dlhh2\",\"kj\":\"1,1,1\"},"+"{\"rule\":\"3dlhh3\",\"kj\":\"3,6,9\"},"+"{\"rule\":\"3dlhh3\",\"kj\":\"4,5,6\"},"+"{\"rule\":\"3dlhh3\",\"kj\":\"7,8,7\"},"+"{\"rule\":\"3dlhh3\",\"kj\":\"9,8,6\"},"+"{\"rule\":\"3dlhh3\",\"kj\":\"1,1,1\"},"+"{\"rule\":\"3dlh1\",\"kj\":\"3,6,9\"},"+"{\"rule\":\"3dlh1\",\"kj\":\"2,1,3\"},"+"{\"rule\":\"3dlh1\",\"kj\":\"1,1,6\"},"+"{\"rule\":\"3dlh1\",\"kj\":\"0,0,9\"},"+"{\"rule\":\"3dlh1\",\"kj\":\"3,0,0\"},"+"{\"rule\":\"3dlh2\",\"kj\":\"3,6,9\"},"+"{\"rule\":\"3dlh2\",\"kj\":\"4,5,6\"},"+"{\"rule\":\"3dlh2\",\"kj\":\"7,8,7\"},"+"{\"rule\":\"3dlh2\",\"kj\":\"9,8,6\"},"+"{\"rule\":\"3dlh2\",\"kj\":\"1,1,1\"},"+"{\"rule\":\"3dlh3\",\"kj\":\"3,6,9\"},"+"{\"rule\":\"3dlh3\",\"kj\":\"4,5,6\"},"+"{\"rule\":\"3dlh3\",\"kj\":\"7,8,7\"},"+"{\"rule\":\"3dlh3\",\"kj\":\"9,8,6\"},"+"{\"rule\":\"3dlh3\",\"kj\":\"1,1,1\"}]}";
		//
		for (int i = 0; i < ans.length; i++) {
			answer.add(ans[i]);
		}
		//System.out.println(data.getClass().getName());
		//System.out.println(data);
		JSONObject obj = new JSONObject(testCondition);
		JSONArray arr = obj.getJSONArray("calc");
		//System.out.println();
		if (arr.length() == answer.size()) {
			CTTLotteryCalc3d fc3dtest = new CTTLotteryCalc3d();
			for(int i =0;i<arr.length();i++) {
				JSONObject kjdata = (JSONObject) arr.get(i);
				String kj = kjdata.getString("kj");
				String rule = kjdata.getString("rule");
				List<String> kjArray = Arrays.asList(kj.split(","));
				List<Integer>listIntegers = new ArrayList<Integer>(kjArray.size());
				for(String current:kjArray) {
					listIntegers.add(Integer.parseInt(current));
				}
				//System.out.println(kjArray.getClass().getName());
				//ystem.out.println(ans[1]);
				Map<String , String> result = fc3dtest.getResult(listIntegers);
				
				//System.out.println(ans[i].equals(result.get(rule)));
				//System.out.println(.getClass().getName());
				
				if (ans[i].equals(result.get(rule))) {
					assertTrue(ans[i].equals(result.get(rule)));
					
				}else {
					System.out.println(""+i+" "+ans[i]+" not equal"+" "+result.get(rule)+"");
				}
				
			}
			System.out.println("testCTTLotteryCalc3d測試完成");
		}else {
			System.out.println("輸入錯誤");
		}
	}
}
