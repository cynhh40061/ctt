package tw.com.ctt.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CTTLotteryCalcPk10Test{
	@Before
	public void before() {
		
	}
	
	@After
	public void after() {
		
	}
	@Test
	public void pk10Test() {
		List<Integer> data = new ArrayList<Integer>();
		data.add(1);
		data.add(2);
		data.add(3);
		data.add(4);
		data.add(5);
		data.add(6);
		data.add(10);
		data.add(8);
		data.add(9);
		data.add(7);
		CTTLotteryCalcPk10 test = new CTTLotteryCalcPk10();
		Map<String , String> result = test.getResult(data);
		System.out.println(result);
		assertTrue("3".equals(result.get("pk10_gy2"))); 		//冠亞和值
		assertTrue("-".equals(result.get("pk10_gy2dds_1")));	//預測冠亞和值_大
		assertTrue("1".equals(result.get("pk10_gy2dds_2")));	//預測冠亞和值_小	
		assertTrue("1".equals(result.get("pk10_gy2dds_3")));	//預測冠亞和值_單
		assertTrue("-".equals(result.get("pk10_gy2dds_4")));	//預測冠亞和值_雙
		assertTrue("1".equals(result.get("pk10_sort1")));		//排名第1名
		assertTrue("2".equals(result.get("pk10_sort2")));		//排名第2名
		assertTrue("3".equals(result.get("pk10_sort3")));		//排名第3名
		assertTrue("4".equals(result.get("pk10_sort4")));		//排名第4名
		assertTrue("5".equals(result.get("pk10_sort5")));		//排名第5名
		assertTrue("6".equals(result.get("pk10_sort6")));		//排名第6名
		assertTrue("7".equals(result.get("pk10_sort7")));		//排名第7名
		assertTrue("8".equals(result.get("pk10_sort8")));		//排名第8名
		assertTrue("9".equals(result.get("pk10_sort9")));		//排名第9名
		assertTrue("10".equals(result.get("pk10_sort10")));		//排名第10名
		assertTrue("h".equals(result.get("pk10_lh_1_10")));		//龍虎_1:10
		assertTrue("h".equals(result.get("pk10_lh_2_9")));		//龍虎_2:9
		assertTrue("h".equals(result.get("pk10_lh_3_8")));		//龍虎_3:8
		assertTrue("h".equals(result.get("pk10_lh_4_7")));		//龍虎_4:7
		assertTrue("h".equals(result.get("pk10_lh_5_6")));		//龍虎_5:6
		assertTrue("1|2".equals(result.get("pk10_q2")));		//前2星
		assertTrue("9|10".equals(result.get("pk10_h2")));		//後2星
		assertTrue("1|2|3".equals(result.get("pk10_q3")));		//前3星
		assertTrue("8|9|10".equals(result.get("pk10_h3")));		//後3星
	}
}
