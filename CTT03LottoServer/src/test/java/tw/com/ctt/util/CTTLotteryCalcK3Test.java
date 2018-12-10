package tw.com.ctt.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CTTLotteryCalcK3Test {
	@Before
	public void before() {
		
	}
	
	@After
	public void after() {
		
	}
	
	@Test
	public void k3Test() {
		List<Integer> data = new ArrayList<Integer>();
		data.add(3);
		data.add(3);
		data.add(5);
		CTTLotteryCalcK3 test = new CTTLotteryCalcK3();
		Map<String , String> result = test.getResult(data);
		System.out.println(result);
		assertTrue("11".equals(result.get("k3hz"))); 	//和值
		assertTrue("-".equals(result.get("k33dx")));	//三同號
		assertTrue("-".equals(result.get("k33tx")));	//三同號通選
		assertTrue("3|5".equals(result.get("k22dx")));	//二同號單選
		assertTrue("33*".equals(result.get("k22fx")));	//二同號複選
		assertTrue("-".equals(result.get("k33x")));		//三不同號
		assertTrue("3,5".equals(result.get("k32x")));	//二不同號
		assertTrue("-".equals(result.get("k33ltx")));	//三連號通選
		assertTrue("1".equals(result.get("k3dx_d")));	//和值大
		assertTrue("-".equals(result.get("k3dx_x")));	//和值小
		assertTrue("1".equals(result.get("k3ds_d")));	//和值單
		assertTrue("-".equals(result.get("k3ds_s")));	//和值雙
		assertTrue("3,5".equals(result.get("k3czw")));	//猜必出
		assertTrue("-".equals(result.get("k3red")));	//猜紅色
		assertTrue("1".equals(result.get("k3black")));	//猜黑色

	}
}
