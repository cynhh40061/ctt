package tw.com.ctt.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CTTLottreyCalc3dTest {
	@Before
	public void before() {
		
	}
	
	@After
	public void after() {
		
	}
	
	@Test
	public void L3dTest() {
		ArrayList<Integer> data = new ArrayList<Integer>();
		data.add(2);
		data.add(2);
		data.add(4);
		CTTLotteryCalc3d test = new CTTLotteryCalc3d();
		Map<String , String> result = test.getResult(data);
		System.out.println(result);
		assertTrue("2".equals(result.get("3ddw1")));		//定位膽百位
		assertTrue("8".equals(result.get("3ddw2")));		//定位膽十位	
		assertTrue("4".equals(result.get("3ddw3")));		//定位膽個位
		assertTrue("2|8".equals(result.get("3dq2")));		//前二直選
		assertTrue("2,8".equals(result.get("3dzq2")));		//前二組選
		assertTrue("8|4".equals(result.get("3dh2")));		//後二直選
		assertTrue("4,8".equals(result.get("3dzh2")));		//後二組選
		assertTrue("2|8|4".equals(result.get("3d3x")));		//3星直選
		assertTrue("14".equals(result.get("3d3xhz")));		//3星直選和值
		assertTrue("-".equals(result.get("3dz3d")));		//組三單式
		assertTrue("-".equals(result.get("3dz3f")));		//組三複式
		assertTrue("14|-".equals(result.get("3dzxhz")));	//3星組選和值
		assertTrue("2,4,8".equals(result.get("3dz6")));		//組六
		assertTrue("2|4|8".equals(result.get("3dbdd")));	//一碼不定位
		assertTrue("2,4|4,8|2,8".equals(result.get("3dbdwq32")));//二碼不定位
		assertTrue("-".equals(result.get("3dlhh1")));		//龍虎和百十_和
		assertTrue("-".equals(result.get("3dlhh2")));		//龍虎和百個_和
		assertTrue("-".equals(result.get("3dlhh3")));		//龍虎十個_和
		assertTrue("h".equals(result.get("3dlh1")));		//龍虎和百十_龍虎
		assertTrue("h".equals(result.get("3dlh2")));		//龍虎和百個_龍虎
		assertTrue("l".equals(result.get("3dlh3")));		//龍虎和十個_龍虎
	}
}
