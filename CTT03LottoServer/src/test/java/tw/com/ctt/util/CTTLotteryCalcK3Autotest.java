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
public class CTTLotteryCalcK3Autotest {
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
		answer = new ArrayList<String>();
		//期望答案
		String[] ans = {"9","11","11","14","9","111","222","333","-","555","666","1","1","1","1","2|4","4|1","1|4","6|5","3|1","22*","44*","11*","66*","33*","1","1","1","1","1","-","1","-","-","1","-","1","-","1","1","-","1","1","-","-","-","1","-","1","1","1","-","3|5|6","3","1|2","1|4|6","2|3|6","1","1","1","-","-","1","1","-","-","-","-"};
		//
		//測試資料
		testCondition ="{\"calc\":[{\"rule\":\"k3hz_7\",\"kj\":\"2,3,4\"},"+"{\"rule\":\"k3hz_8\",\"kj\":\"1,4,6\"},"+"{\"rule\":\"k3hz_8\",\"kj\":\"2,5,4\"},"+"{\"rule\":\"k3hz_5\",\"kj\":\"4,5,5\"},"+"{\"rule\":\"k3hz_7\",\"kj\":\"3,3,3\"},"+"{\"rule\":\"k33dx\",\"kj\":\"1,1,1\"},"+"{\"rule\":\"k33dx\",\"kj\":\"2,2,2\"},"+"{\"rule\":\"k33dx\",\"kj\":\"3,3,3\"},"+"{\"rule\":\"k33dx\",\"kj\":\"4,5,6\"},"+"{\"rule\":\"k33dx\",\"kj\":\"5,5,5\"},"+"{\"rule\":\"k33dx\",\"kj\":\"6,6,6\"},"+"{\"rule\":\"k33tx\",\"kj\":\"1,1,1\"},"+"{\"rule\":\"k33tx\",\"kj\":\"3,3,3\"},"+"{\"rule\":\"k33tx\",\"kj\":\"5,5,5\"},"+"{\"rule\":\"k33tx\",\"kj\":\"6,6,6\"},"+"{\"rule\":\"k22dx\",\"kj\":\"2,4,2\"},"+"{\"rule\":\"k22dx\",\"kj\":\"4,1,4\"},"+"{\"rule\":\"k22dx\",\"kj\":\"4,1,1\"},"+"{\"rule\":\"k22dx\",\"kj\":\"6,6,5\"},"+"{\"rule\":\"k22dx\",\"kj\":\"3,3,1\"},"+"{\"rule\":\"k22fx\",\"kj\":\"2,4,2\"},"+"{\"rule\":\"k22fx\",\"kj\":\"4,1,4\"},"+"{\"rule\":\"k22fx\",\"kj\":\"4,1,1\"},"+"{\"rule\":\"k22fx\",\"kj\":\"6,6,5\"},"+"{\"rule\":\"k22fx\",\"kj\":\"3,3,1\"},"+"{\"rule\":\"k33ltx\",\"kj\":\"4,6,5\"},"+"{\"rule\":\"k33ltx\",\"kj\":\"3,1,2\"},"+"{\"rule\":\"k33ltx\",\"kj\":\"1,3,2\"},"+"{\"rule\":\"k33ltx\",\"kj\":\"3,5,4\"},"+"{\"rule\":\"k33ltx\",\"kj\":\"4,3,2\"},"+"{\"rule\":\"k3dx_d\",\"kj\":\"4,6,0\"},"+"{\"rule\":\"k3dx_d\",\"kj\":\"4,6,5\"},"+"{\"rule\":\"k3dx_d\",\"kj\":\"3,1,2\"},"+"{\"rule\":\"k3dx_d\",\"kj\":\"1,3,2\"},"+"{\"rule\":\"k3dx_d\",\"kj\":\"3,5,4\"},"+"{\"rule\":\"k3dx_d\",\"kj\":\"4,3,2\"},"+"{\"rule\":\"k3dx_x\",\"kj\":\"4,6,0\"},"+"{\"rule\":\"k3dx_x\",\"kj\":\"4,6,5\"},"+"{\"rule\":\"k3dx_x\",\"kj\":\"3,1,2\"},"+"{\"rule\":\"k3dx_x\",\"kj\":\"1,3,2\"},"+"{\"rule\":\"k3dx_x\",\"kj\":\"3,5,4\"},"+"{\"rule\":\"k3dx_x\",\"kj\":\"4,3,2\"},"+"{\"rule\":\"k3ds_d\",\"kj\":\"4,6,5\"},"+"{\"rule\":\"k3ds_d\",\"kj\":\"3,1,2\"},"+"{\"rule\":\"k3ds_d\",\"kj\":\"1,3,2\"},"+"{\"rule\":\"k3ds_d\",\"kj\":\"3,5,4\"},"+"{\"rule\":\"k3ds_d\",\"kj\":\"4,3,2\"},"+"{\"rule\":\"k3ds_s\",\"kj\":\"4,6,5\"},"+"{\"rule\":\"k3ds_s\",\"kj\":\"3,1,2\"},"+"{\"rule\":\"k3ds_s\",\"kj\":\"1,3,2\"},"+"{\"rule\":\"k3ds_s\",\"kj\":\"3,5,4\"},"+"{\"rule\":\"k3ds_s\",\"kj\":\"4,3,2\"},"+"{\"rule\":\"k3czw\",\"kj\":\"3,5,6\"},"+"{\"rule\":\"k3czw\",\"kj\":\"3,3,3\"},"+"{\"rule\":\"k3czw\",\"kj\":\"2,1,2\"},"+"{\"rule\":\"k3czw\",\"kj\":\"4,6,1\"},"+"{\"rule\":\"k3czw\",\"kj\":\"2,3,6\"},"+"{\"rule\":\"k3red\",\"kj\":\"1,1,4\"},"+"{\"rule\":\"k3red\",\"kj\":\"4,1,4\"},"+"{\"rule\":\"k3red\",\"kj\":\"4,4,1\"},"+"{\"rule\":\"k3red\",\"kj\":\"4,2,1\"},"+"{\"rule\":\"k3red\",\"kj\":\"4,5,5\"},"+"{\"rule\":\"k3black\",\"kj\":\"2,3,5\"},"+"{\"rule\":\"k3black\",\"kj\":\"3,3,5\"},"+"{\"rule\":\"k3black\",\"kj\":\"6,2,1\"},"+"{\"rule\":\"k3black\",\"kj\":\"6,6,1\"},"+"{\"rule\":\"k3black\",\"kj\":\"4,6,4\"},"+"{\"rule\":\"k3black\",\"kj\":\"4,6,4\"}]}";
		//
		for (int i = 0; i < ans.length; i++) {
			answer.add(ans[i]);
		}
		JSONObject obj = new JSONObject(testCondition);
		JSONArray arr = obj.getJSONArray("calc");
		if (arr.length() == answer.size()) {
			CTTLotteryCalcK3 k3test = new CTTLotteryCalcK3();
			for(int i =0;i<arr.length();i++) {
				JSONObject kjdata = (JSONObject) arr.get(i);
				String kj = kjdata.getString("kj");
				String rule = kjdata.getString("rule");
				List<String> kjArray = Arrays.asList(kj.split(","));
				List<Integer>listIntegers = new ArrayList<Integer>(kjArray.size());
				for(String current:kjArray) {
					listIntegers.add(Integer.parseInt(current));
				}
				Map<String , String> result = k3test.getResult(listIntegers);
				if (ans[i].equals(result.get(rule))) {
					assertTrue(ans[i].equals(result.get(rule)));
				}else {
					System.out.println(""+i+" "+ans[i]+" not equal"+" "+result.get(rule)+"");
				}
				
			}
			System.out.println("testCTTLotteryCalck3測試完成");
		}else {
			System.out.println("輸入錯誤");
		}
	}
}
