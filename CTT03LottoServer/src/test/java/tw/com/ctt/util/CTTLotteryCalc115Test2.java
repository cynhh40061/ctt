package tw.com.ctt.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CTTLotteryCalc115Test2 {
	@Before
	public void before() {
		
	}
	
	@After
	public void after() {
		
	}
	@Test
	public void q2q3() {
		List<Integer> data = new ArrayList<Integer>();
		data.add(5);
		data.add(9);
		data.add(4);
		data.add(10);
		data.add(7);
		CTTLotteryCalc115 test = new CTTLotteryCalc115();
		Map<String , String> result = test.getResult(data);
		System.out.println(result);
		
		assertTrue("5|9|4".equals(result.get("115_q3")));		//前三直選
		assertTrue("4,5,9".equals(result.get("115_q3z")));		//前三組選
		assertTrue("5|9".equals(result.get("115_q2")));			//前二直選
		assertTrue("5,9".equals(result.get("115_q2z")));		//前二組選
		assertTrue("4|5|9".equals(result.get("115_q3bdw")));	//前3不定位
	}
	@Test
	public void dxd() {
		List<Integer> data = new ArrayList<Integer>();
		data.add(5);
		data.add(9);
		data.add(4);
		data.add(10);
		data.add(7);
		CTTLotteryCalc115 test = new CTTLotteryCalc115();
		Map<String , String> result = test.getResult(data);
		//System.out.println(result);
		assertTrue("-".equals(result.get("115_dds_5")));		//定單雙 5單0雙
		assertTrue("-".equals(result.get("115_dds_4")));		//定單雙 4單1雙
		assertTrue("1".equals(result.get("115_dds_3")));		//定單雙 3單2雙
		assertTrue("-".equals(result.get("115_dds_2")));		//定單雙 2單3雙
		assertTrue("-".equals(result.get("115_dds_1")));		//定單雙 1單4雙
		assertTrue("-".equals(result.get("115_dds_0")));		//定單雙 0單5雙
	}
	@Test
	public void czw() {
		List<Integer> data = new ArrayList<Integer>();
		data.add(5);
		data.add(9);
		data.add(4);
		data.add(10);
		data.add(7);
		CTTLotteryCalc115 test = new CTTLotteryCalc115();
		Map<String , String> result = test.getResult(data);
		//System.out.println(result);
		assertTrue("-".equals(result.get("115_czw_3")));		//猜中位 3
		assertTrue("-".equals(result.get("115_czw_9")));		//猜中位 9
		assertTrue("-".equals(result.get("115_czw_4")));		//猜中位 4
		assertTrue("-".equals(result.get("115_czw_8")));		//猜中位 8
		assertTrue("-".equals(result.get("115_czw_5")));		//猜中位 5
		assertTrue("1".equals(result.get("115_czw_7")));		//猜中位 7
		assertTrue("-".equals(result.get("115_czw_6")));		//猜中位 6
	}
	@Test
	public void dwd() {
		List<Integer> data = new ArrayList<Integer>();
		data.add(5);
		data.add(9);
		data.add(4);
		data.add(10);
		data.add(7);
		CTTLotteryCalc115 test = new CTTLotteryCalc115();
		Map<String , String> result = test.getResult(data);
		//System.out.println(result);
		assertTrue("5".equals(result.get("115_dwd_1")));		//定位膽1位
		assertTrue("9".equals(result.get("115_dwd_2")));		//定位膽2位
		assertTrue("4".equals(result.get("115_dwd_3")));		//定位膽3位
		assertTrue("10".equals(result.get("115_dwd_4")));		//定位膽4位
		assertTrue("7".equals(result.get("115_dwd_5")));		//定位膽5位
	}
	@Test
	public void rxzx() {
		List<Integer> data = new ArrayList<Integer>();
		data.add(5);
		data.add(9);
		data.add(4);
		data.add(10);
		data.add(7);
		CTTLotteryCalc115 test = new CTTLotteryCalc115();
		Map<String , String> result = test.getResult(data);
		//System.out.println(result);
		assertTrue("4,5,7,9,10".equals(result.get("115_r1")));	//任1中1
		assertTrue("4,5,7,9,10".equals(result.get("115_r2")));	//任2中2
		assertTrue("4,5,7,9,10".equals(result.get("115_r3")));	//任3中3
		assertTrue("4,5,7,9,10".equals(result.get("115_r4")));	//任4中4
		assertTrue("4,5,7,9,10".equals(result.get("115_r5")));	//任5中5
		assertTrue("4,5,7,9,10".equals(result.get("115_r6")));	//任6中5
		assertTrue("4,5,7,9,10".equals(result.get("115_r7")));	//任7中5
		assertTrue("4,5,7,9,10".equals(result.get("115_r8")));	//任8中5
	}
	
	
}
