package tw.com.ctt.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.runners.JUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnit4.class)
public class CTTLotteryCalcSSCTest3 {
	@Before
	public void before() {
		
	}
	
	@After
	public void after() {
		
	}

	@Test
	public void SSCTest() {
		List<Integer> data = new ArrayList<Integer>();
		data.add(1);
		data.add(2);
		data.add(3);
		data.add(4);
		data.add(5);

		CTTLotteryCalcSsc test = new CTTLotteryCalcSsc();
		Map<String , String> result = test.getResult(data);
		System.out.println(result);
		assertTrue("1".equals(result.get("sscdwd1x_16")));	//定位膽 萬
		assertTrue("2".equals(result.get("sscdwd1x_8")));	//定位膽 千
		assertTrue("3".equals(result.get("sscdwd1x_4")));	//定位膽 百
		assertTrue("4".equals(result.get("sscdwd1x_2")));	//定位膽 十
		assertTrue("5".equals(result.get("sscdwd1x_1")));	//定位膽 個
		assertTrue("1|2".equals(result.get("sscr2_24")));	//前二直選
		assertTrue("3".equals(result.get("sscr2hz_24")));	//前二和值
		assertTrue("1".equals(result.get("sscr2kd_24")));	//前二跨度
		assertTrue("1,2".equals(result.get("sscr2z_24")));	//前二組選
		assertTrue("3".equals(result.get("sscr2zhz_24")));	//前二組選和值
		assertTrue("1_2".equals(result.get("sscr2bd_24")));	//前二包膽	
		assertTrue("4|5".equals(result.get("sscr2_3")));	//後二直選
		assertTrue("9".equals(result.get("sscr2hz_3")));	//後二和值
		assertTrue("1".equals(result.get("sscr2kd_3")));	//後二跨度
		assertTrue("4,5".equals(result.get("sscr2z_3")));	//後二組選
		assertTrue("9".equals(result.get("sscr2zhz_3")));	//後二組選和值
		assertTrue("4_5".equals(result.get("sscr2bd_3")));	//後二包膽
		assertTrue("1|2|3".equals(result.get("sscr3_28")));			//前三直選
		assertTrue("6".equals(result.get("sscr3hz_28")));			//前三合值
		assertTrue("2".equals(result.get("sscr3kd_28")));			//前三跨度
		assertTrue("-".equals(result.get("sscr3z3_28")));			//前三組三複式
		assertTrue("-".equals(result.get("sscr3z3ds_28")));			//前三組三單式
		assertTrue("1,2,3".equals(result.get("sscr3z6_28")));		//前三組六
		assertTrue("6|-".equals(result.get("sscr3zhz_28")));		//前三組選和值
		assertTrue("6".equals(result.get("sscr3hzws_28")));			//前三和值尾數
		assertTrue("-".equals(result.get("sscr3special_28_16")));	//前三豹子
		assertTrue("1".equals(result.get("sscr3special_28_8")));	//前三順子
		assertTrue("-".equals(result.get("sscr3special_28_4")));	//前三對子
		assertTrue("-".equals(result.get("sscr3special_28_2")));	//前三半順
		assertTrue("-".equals(result.get("sscr3special_28_1")));	//前三雜六
		assertTrue("1_2_3|-".equals(result.get("sscr3bd_28")));		//前三包膽
		assertTrue("2|3|4".equals(result.get("sscr3_14")));			//中三直選
		assertTrue("9".equals(result.get("sscr3hz_14")));			//中三合值
		assertTrue("2".equals(result.get("sscr3kd_14")));			//中三跨度
		assertTrue("-".equals(result.get("sscr3z3_14")));			//中三組三複式
		assertTrue("-".equals(result.get("sscr3z3ds_14")));			//中三組三單式
		assertTrue("2,3,4".equals(result.get("sscr3z6_14")));		//中三組六
		assertTrue("9|-".equals(result.get("sscr3zhz_14")));		//中三組選和值
		assertTrue("9".equals(result.get("sscr3hzws_14")));			//中三和值尾數
		assertTrue("-".equals(result.get("sscr3special_14_16")));	//中三豹子
		assertTrue("1".equals(result.get("sscr3special_14_8")));	//中三順子
		assertTrue("-".equals(result.get("sscr3special_14_4")));	//中三對子
		assertTrue("-".equals(result.get("sscr3special_14_2")));	//中三半順
		assertTrue("-".equals(result.get("sscr3special_14_1")));	//中三雜六
		assertTrue("2_3_4|-".equals(result.get("sscr3bd_14")));		//中三包膽
		assertTrue("3|4|5".equals(result.get("sscr3_7")));			//後三直選
		assertTrue("12".equals(result.get("sscr3hz_7")));			//後三合值
		assertTrue("2".equals(result.get("sscr3kd_7")));			//後三跨度
		assertTrue("-".equals(result.get("sscr3z3_7")));			//後三組三複式
		assertTrue("-".equals(result.get("sscr3z3ds_7")));			//後三組三單式
		assertTrue("3,4,5".equals(result.get("sscr3z6_7")));		//後三組六
		assertTrue("12|-".equals(result.get("sscr3zhz_7")));		//後三組選和值
		assertTrue("2".equals(result.get("sscr3hzws_7")));			//後三和值尾數
		assertTrue("-".equals(result.get("sscr3special_7_16")));	//後三豹子
		assertTrue("1".equals(result.get("sscr3special_7_8")));		//後三順子
		assertTrue("-".equals(result.get("sscr3special_7_4")));		//後三對子
		assertTrue("-".equals(result.get("sscr3special_7_2")));		//後三半順
		assertTrue("-".equals(result.get("sscr3special_7_1")));		//後三雜六
		assertTrue("3_4_5|-".equals(result.get("sscr3bd_7")));		//後三包膽
		assertTrue("1|2|3|4".equals(result.get("sscr4_30")));		//前四直選
		assertTrue("1,2,3,4".equals(result.get("sscr4z24_30")));	//前四組選24
		assertTrue("-".equals(result.get("sscr4z12_30")));			//前四組選12
		assertTrue("-".equals(result.get("sscr4z6_30")));			//前四組選6
		assertTrue("-".equals(result.get("sscr4z4_30")));			//前四組選4
		assertTrue("2|3|4|5".equals(result.get("sscr4_15")));		//後四直選
		assertTrue("2,3,4,5".equals(result.get("sscr4z24_15")));	//後四組選24
		assertTrue("-".equals(result.get("sscr4z12_15")));			//後四組選12
		assertTrue("-".equals(result.get("sscr4z6_15")));			//後四組選6
		assertTrue("-".equals(result.get("sscr4z4_15")));			//後四組選4
		assertTrue("1,2,3,4,5".equals(result.get("ssc5x")));		//五星直選
		assertTrue("1,2,3,4,5".equals(result.get("ssc5xz120")));	//五星組選120
		assertTrue("-".equals(result.get("ssc5xz60")));				//五星組選60
		assertTrue("-".equals(result.get("ssc5xz30")));				//五星組選30
		assertTrue("-".equals(result.get("ssc5xz20")));				//五星組選20
		assertTrue("-".equals(result.get("ssc5xz10")));				//五星組選10
		assertTrue("-".equals(result.get("ssc5xz5")));				//五星組選5
		assertTrue("-".equals(result.get("ssc5xbxds_8")));			//五星總和 大
		assertTrue("1".equals(result.get("ssc5xbxds_4")));			//五星總和 小
		assertTrue("1".equals(result.get("ssc5xbxds_2")));			//五星總和 單
		assertTrue("-".equals(result.get("ssc5xbxds_1")));			//五星總和 雙
		assertTrue("-".equals(result.get("ssc5xbxdszh_10")));			//五星總和組合 大單
		assertTrue("1".equals(result.get("ssc5xbxdszh_6")));			//五星總和組合 小單
		assertTrue("-".equals(result.get("ssc5xbxdszh_9")));			//五星總和組合 大雙
		assertTrue("-".equals(result.get("ssc5xbxdszh_5")));			//五星總和組合 小雙
		assertTrue("1_2_3".equals(result.get("sscr3bdw1m_28")));		//前三一碼不定位
		assertTrue("3_4_5".equals(result.get("sscr3bdw1m_7")));			//後三一碼不定位
		assertTrue("1,2_1,3_2,3".equals(result.get("sscr3bdw2m_28")));		//前三二碼不定位
		assertTrue("3,4_3,5_4,5".equals(result.get("sscr3bdw2m_7")));		//後三二碼不定位
		assertTrue("1_2_4".equals(result.get("sscr3bdw1m_26")));		//任三一碼不定位
		assertTrue("1_2_5".equals(result.get("sscr3bdw1m_25")));		//任三一碼不定位
		assertTrue("1_3_4".equals(result.get("sscr3bdw1m_22")));		//任三一碼不定位
		assertTrue("1_3_5".equals(result.get("sscr3bdw1m_21")));		//任三一碼不定位
		assertTrue("1_4_5".equals(result.get("sscr3bdw1m_19")));		//任三一碼不定位
		assertTrue("2_3_4".equals(result.get("sscr3bdw1m_14")));		//任三一碼不定位
		assertTrue("2_3_5".equals(result.get("sscr3bdw1m_13")));		//任三一碼不定位
		assertTrue("2_3_4_5".equals(result.get("ssch4bdw1m_15")));			//四星一碼不定位
		assertTrue("2,3_2,4_2,5_3,4_3,5_4,5".equals(result.get("ssch4bdw2m_15")));			//四星二碼不定位
		assertTrue("1_2_3_4_5".equals(result.get("ssc5xbdw1m")));		//五一碼不定位
		assertTrue("1,2_1,3_1,4_1,5_2,3_2,4_2,5_3,4_3,5_4,5".equals(result.get("ssc5xbdw2m")));		//五二碼不定位
		assertTrue("1,2,3_1,2,4_1,2,5_1,3,4_1,3,5_1,4,5_2,3,4_2,3,5_2,4,5_3,4,5".equals(result.get("ssc5xbdw3m")));		//五三碼不定位
		assertTrue("-".equals(result.get("sscr2bxds_24_8_8")));		//前二大大
		assertTrue("-".equals(result.get("sscr2bxds_20_8_8")));		//任二大大
		assertTrue("-".equals(result.get("sscr2bxds_18_8_8")));		//任二大大
		assertTrue("-".equals(result.get("sscr2bxds_17_8_8")));		//任二大大
		assertTrue("-".equals(result.get("sscr2bxds_12_8_8")));		//任二大大
		assertTrue("-".equals(result.get("sscr2bxds_10_8_8")));		//任二大大
		assertTrue("-".equals(result.get("sscr2bxds_9_8_8")));		//任二大大
		assertTrue("-".equals(result.get("sscr2bxds_6_8_8")));		//任二大大
		assertTrue("-".equals(result.get("sscr2bxds_5_8_8")));		//任二大大
		assertTrue("-".equals(result.get("sscr2bxds_3_8_8")));		//後二大大
		assertTrue("-".equals(result.get("sscr2bxds_24_8_4")));		//前二大小
		assertTrue("-".equals(result.get("sscr2bxds_20_8_4")));		//任二大小
		assertTrue("-".equals(result.get("sscr2bxds_18_8_4")));		//任二大小
		assertTrue("-".equals(result.get("sscr2bxds_17_8_4")));		//任二大小
		assertTrue("-".equals(result.get("sscr2bxds_12_8_4")));		//任二大小
		assertTrue("-".equals(result.get("sscr2bxds_10_8_4")));		//任二大小
		assertTrue("-".equals(result.get("sscr2bxds_9_8_4")));		//任二大小
		assertTrue("-".equals(result.get("sscr2bxds_6_8_4")));		//任二大小
		assertTrue("-".equals(result.get("sscr2bxds_5_8_4")));		//任二大小
		assertTrue("-".equals(result.get("sscr2bxds_3_8_4")));		//後二大小
		assertTrue("-".equals(result.get("sscr2bxds_24_8_2")));		//前二大單
		assertTrue("-".equals(result.get("sscr2bxds_20_8_2")));		//任二大單
		assertTrue("-".equals(result.get("sscr2bxds_18_8_2")));		//任二大單
		assertTrue("-".equals(result.get("sscr2bxds_17_8_2")));		//任二大單
		assertTrue("-".equals(result.get("sscr2bxds_12_8_2")));		//任二大單
		assertTrue("-".equals(result.get("sscr2bxds_10_8_2")));		//任二大單
		assertTrue("-".equals(result.get("sscr2bxds_9_8_2")));		//任二大單
		assertTrue("-".equals(result.get("sscr2bxds_6_8_2")));		//任二大單
		assertTrue("-".equals(result.get("sscr2bxds_5_8_2")));		//任二大單
		assertTrue("-".equals(result.get("sscr2bxds_3_8_2")));		//後二大單
		assertTrue("-".equals(result.get("sscr2bxds_24_8_1")));		//前二大雙
		assertTrue("-".equals(result.get("sscr2bxds_20_8_1")));		//任二大雙
		assertTrue("-".equals(result.get("sscr2bxds_18_8_1")));		//任二大雙
		assertTrue("-".equals(result.get("sscr2bxds_17_8_1")));		//任二大雙
		assertTrue("-".equals(result.get("sscr2bxds_12_8_1")));		//任二大雙
		assertTrue("-".equals(result.get("sscr2bxds_10_8_1")));		//任二大雙
		assertTrue("-".equals(result.get("sscr2bxds_9_8_1")));		//任二大雙
		assertTrue("-".equals(result.get("sscr2bxds_6_8_1")));		//任二大雙
		assertTrue("-".equals(result.get("sscr2bxds_5_8_1")));		//任二大雙
		assertTrue("-".equals(result.get("sscr2bxds_3_8_1")));		//後二大雙
		assertTrue("-".equals(result.get("sscr2bxds_24_4_8")));		//前二小大
		assertTrue("-".equals(result.get("sscr2bxds_20_4_8")));		//任二小大
		assertTrue("-".equals(result.get("sscr2bxds_18_4_8")));		//任二小大
		assertTrue("1".equals(result.get("sscr2bxds_17_4_8")));		//任二小大
		assertTrue("-".equals(result.get("sscr2bxds_12_4_8")));		//任二小大
		assertTrue("-".equals(result.get("sscr2bxds_10_4_8")));		//任二小大
		assertTrue("1".equals(result.get("sscr2bxds_9_4_8")));		//任二小大
		assertTrue("-".equals(result.get("sscr2bxds_6_4_8")));		//任二小大
		assertTrue("1".equals(result.get("sscr2bxds_5_4_8")));		//任二小大
		assertTrue("1".equals(result.get("sscr2bxds_3_4_8")));		//後二小大
		assertTrue("1".equals(result.get("sscr2bxds_24_4_4")));		//前二小小
		assertTrue("1".equals(result.get("sscr2bxds_20_4_4")));		//任二小小
		assertTrue("1".equals(result.get("sscr2bxds_18_4_4")));		//任二小小
		assertTrue("-".equals(result.get("sscr2bxds_17_4_4")));		//任二小小
		assertTrue("1".equals(result.get("sscr2bxds_12_4_4")));		//任二小小
		assertTrue("1".equals(result.get("sscr2bxds_10_4_4")));		//任二小小
		assertTrue("-".equals(result.get("sscr2bxds_9_4_4")));		//任二小小
		assertTrue("1".equals(result.get("sscr2bxds_6_4_4")));		//任二小小
		assertTrue("-".equals(result.get("sscr2bxds_5_4_4")));		//任二小小
		assertTrue("-".equals(result.get("sscr2bxds_3_4_4")));		//後二小小
		assertTrue("-".equals(result.get("sscr2bxds_24_4_2")));		//前二小單
		assertTrue("1".equals(result.get("sscr2bxds_20_4_2")));		//任二小單
		assertTrue("-".equals(result.get("sscr2bxds_18_4_2")));		//任二小單
		assertTrue("1".equals(result.get("sscr2bxds_17_4_2")));		//任二小單
		assertTrue("1".equals(result.get("sscr2bxds_12_4_2")));		//任二小單
		assertTrue("-".equals(result.get("sscr2bxds_10_4_2")));		//任二小單
		assertTrue("1".equals(result.get("sscr2bxds_9_4_2")));		//任二小單
		assertTrue("-".equals(result.get("sscr2bxds_6_4_2")));		//任二小單
		assertTrue("1".equals(result.get("sscr2bxds_5_4_2")));		//任二小單
		assertTrue("1".equals(result.get("sscr2bxds_3_4_2")));		//後二小單
		assertTrue("1".equals(result.get("sscr2bxds_24_4_1")));		//前二小雙
		assertTrue("-".equals(result.get("sscr2bxds_20_4_1")));		//任二小雙
		assertTrue("1".equals(result.get("sscr2bxds_18_4_1")));		//任二小雙
		assertTrue("-".equals(result.get("sscr2bxds_17_4_1")));		//任二小雙
		assertTrue("-".equals(result.get("sscr2bxds_12_4_1")));		//任二小雙
		assertTrue("1".equals(result.get("sscr2bxds_10_4_1")));		//任二小雙
		assertTrue("-".equals(result.get("sscr2bxds_9_4_1")));		//任二小雙
		assertTrue("1".equals(result.get("sscr2bxds_6_4_1")));		//任二小雙
		assertTrue("-".equals(result.get("sscr2bxds_5_4_1")));		//任二小雙
		assertTrue("-".equals(result.get("sscr2bxds_3_4_1")));		//後二小雙
		assertTrue("-".equals(result.get("sscr2bxds_24_2_8")));		//前二單大
		assertTrue("-".equals(result.get("sscr2bxds_20_2_8")));		//任二單大
		assertTrue("-".equals(result.get("sscr2bxds_18_2_8")));		//任二單大
		assertTrue("1".equals(result.get("sscr2bxds_17_2_8")));		//任二單大
		assertTrue("-".equals(result.get("sscr2bxds_12_2_8")));		//任二單大
		assertTrue("-".equals(result.get("sscr2bxds_10_2_8")));		//任二單大
		assertTrue("-".equals(result.get("sscr2bxds_9_2_8")));		//任二單大
		assertTrue("-".equals(result.get("sscr2bxds_6_2_8")));		//任二單大
		assertTrue("1".equals(result.get("sscr2bxds_5_2_8")));		//任二單大
		assertTrue("-".equals(result.get("sscr2bxds_3_2_8")));		//後二單大
		assertTrue("1".equals(result.get("sscr2bxds_24_2_4")));		//前二單小
		assertTrue("1".equals(result.get("sscr2bxds_20_2_4")));		//任二單小
		assertTrue("1".equals(result.get("sscr2bxds_18_2_4")));		//任二單小
		assertTrue("-".equals(result.get("sscr2bxds_17_2_4")));		//任二單小
		assertTrue("-".equals(result.get("sscr2bxds_12_2_4")));		//任二單小
		assertTrue("-".equals(result.get("sscr2bxds_10_2_4")));		//任二單小
		assertTrue("-".equals(result.get("sscr2bxds_9_2_4")));		//任二單小
		assertTrue("1".equals(result.get("sscr2bxds_6_2_4")));		//任二單小
		assertTrue("-".equals(result.get("sscr2bxds_5_2_4")));		//任二單小
		assertTrue("-".equals(result.get("sscr2bxds_3_2_4")));		//後二單小
		assertTrue("-".equals(result.get("sscr2bxds_24_2_2")));		//前二單單
		assertTrue("1".equals(result.get("sscr2bxds_20_2_2")));		//任二單單
		assertTrue("-".equals(result.get("sscr2bxds_18_2_2")));		//任二單單
		assertTrue("1".equals(result.get("sscr2bxds_17_2_2")));		//任二單單
		assertTrue("-".equals(result.get("sscr2bxds_12_2_2")));		//任二單單
		assertTrue("-".equals(result.get("sscr2bxds_10_2_2")));		//任二單單
		assertTrue("-".equals(result.get("sscr2bxds_9_2_2")));		//任二單單
		assertTrue("-".equals(result.get("sscr2bxds_6_2_2")));		//任二單單
		assertTrue("1".equals(result.get("sscr2bxds_5_2_2")));		//任二單單
		assertTrue("-".equals(result.get("sscr2bxds_3_2_2")));		//後二單單
		assertTrue("1".equals(result.get("sscr2bxds_24_2_1")));		//前二單雙
		assertTrue("-".equals(result.get("sscr2bxds_20_2_1")));		//任二單雙
		assertTrue("1".equals(result.get("sscr2bxds_18_2_1")));		//任二單雙
		assertTrue("-".equals(result.get("sscr2bxds_17_2_1")));		//任二單雙
		assertTrue("-".equals(result.get("sscr2bxds_12_2_1")));		//任二單雙
		assertTrue("-".equals(result.get("sscr2bxds_10_2_1")));		//任二單雙
		assertTrue("-".equals(result.get("sscr2bxds_9_2_1")));		//任二單雙
		assertTrue("1".equals(result.get("sscr2bxds_6_2_1")));		//任二單雙
		assertTrue("-".equals(result.get("sscr2bxds_5_2_1")));		//任二單雙
		assertTrue("-".equals(result.get("sscr2bxds_3_2_1")));		//後二單雙
		assertTrue("-".equals(result.get("sscr2bxds_24_1_8")));		//前二雙大
		assertTrue("-".equals(result.get("sscr2bxds_20_1_8")));		//任二雙大
		assertTrue("-".equals(result.get("sscr2bxds_18_1_8")));		//任二雙大
		assertTrue("-".equals(result.get("sscr2bxds_17_1_8")));		//任二雙大
		assertTrue("-".equals(result.get("sscr2bxds_12_1_8")));		//任二雙大
		assertTrue("-".equals(result.get("sscr2bxds_10_1_8")));		//任二雙大
		assertTrue("1".equals(result.get("sscr2bxds_9_1_8")));		//任二雙大
		assertTrue("-".equals(result.get("sscr2bxds_6_1_8")));		//任二雙大
		assertTrue("-".equals(result.get("sscr2bxds_5_1_8")));		//任二雙大
		assertTrue("1".equals(result.get("sscr2bxds_3_1_8")));		//後二雙大
		assertTrue("-".equals(result.get("sscr2bxds_24_1_4")));		//前二雙小
		assertTrue("-".equals(result.get("sscr2bxds_20_1_4")));		//任二雙小
		assertTrue("-".equals(result.get("sscr2bxds_18_1_4")));		//任二雙小
		assertTrue("-".equals(result.get("sscr2bxds_17_1_4")));		//任二雙小
		assertTrue("1".equals(result.get("sscr2bxds_12_1_4")));		//任二雙小
		assertTrue("1".equals(result.get("sscr2bxds_10_1_4")));		//任二雙小
		assertTrue("-".equals(result.get("sscr2bxds_9_1_4")));		//任二雙小
		assertTrue("-".equals(result.get("sscr2bxds_6_1_4")));		//任二雙小
		assertTrue("-".equals(result.get("sscr2bxds_5_1_4")));		//任二雙小
		assertTrue("-".equals(result.get("sscr2bxds_3_1_4")));		//後二雙小
		assertTrue("-".equals(result.get("sscr2bxds_24_1_2")));		//前二雙單
		assertTrue("-".equals(result.get("sscr2bxds_20_1_2")));		//任二雙單
		assertTrue("-".equals(result.get("sscr2bxds_18_1_2")));		//任二雙單
		assertTrue("-".equals(result.get("sscr2bxds_17_1_2")));		//任二雙單
		assertTrue("1".equals(result.get("sscr2bxds_12_1_2")));		//任二雙單
		assertTrue("-".equals(result.get("sscr2bxds_10_1_2")));		//任二雙單
		assertTrue("1".equals(result.get("sscr2bxds_9_1_2")));		//任二雙單
		assertTrue("-".equals(result.get("sscr2bxds_6_1_2")));		//任二雙單
		assertTrue("-".equals(result.get("sscr2bxds_5_1_2")));		//任二雙單
		assertTrue("1".equals(result.get("sscr2bxds_3_1_2")));		//後二雙單
		assertTrue("-".equals(result.get("sscr2bxds_24_1_1")));		//前二雙雙
		assertTrue("-".equals(result.get("sscr2bxds_20_1_1")));		//任二雙雙
		assertTrue("-".equals(result.get("sscr2bxds_18_1_1")));		//任二雙雙
		assertTrue("-".equals(result.get("sscr2bxds_17_1_1")));		//任二雙雙
		assertTrue("-".equals(result.get("sscr2bxds_12_1_1")));		//任二雙雙
		assertTrue("1".equals(result.get("sscr2bxds_10_1_1")));		//任二雙雙
		assertTrue("-".equals(result.get("sscr2bxds_9_1_1")));		//任二雙雙
		assertTrue("-".equals(result.get("sscr2bxds_6_1_1")));		//任二雙雙
		assertTrue("-".equals(result.get("sscr2bxds_5_1_1")));		//任二雙雙
		assertTrue("-".equals(result.get("sscr2bxds_3_1_1")));		//後二雙雙	
		assertTrue("-".equals(result.get("lsscq3bxds_8_8_8")));		//前三大大大
		assertTrue("-".equals(result.get("lsscq3bxds_8_8_4")));		//前三大大小
		assertTrue("-".equals(result.get("lsscq3bxds_8_8_2")));		//前三大大單
		assertTrue("-".equals(result.get("lsscq3bxds_8_8_1")));		//前三大大雙
		assertTrue("-".equals(result.get("lsscq3bxds_8_4_8")));		//前三大小大
		assertTrue("-".equals(result.get("lsscq3bxds_8_4_4")));		//前三大小小
		assertTrue("-".equals(result.get("lsscq3bxds_8_4_2")));		//前三大小單
		assertTrue("-".equals(result.get("lsscq3bxds_8_4_1")));		//前三大小雙
		assertTrue("-".equals(result.get("lsscq3bxds_8_2_8")));		//前三大單大
		assertTrue("-".equals(result.get("lsscq3bxds_8_2_4")));		//前三大單小
		assertTrue("-".equals(result.get("lsscq3bxds_8_2_2")));		//前三大單單
		assertTrue("-".equals(result.get("lsscq3bxds_8_2_1")));		//前三大單雙
		assertTrue("-".equals(result.get("lsscq3bxds_8_1_8")));		//前三大雙大
		assertTrue("-".equals(result.get("lsscq3bxds_8_1_4")));		//前三大雙小
		assertTrue("-".equals(result.get("lsscq3bxds_8_1_2")));		//前三大雙單
		assertTrue("-".equals(result.get("lsscq3bxds_8_1_1")));		//前三大雙雙
		assertTrue("-".equals(result.get("lsscq3bxds_4_8_8")));		//前三小大大
		assertTrue("-".equals(result.get("lsscq3bxds_4_8_4")));		//前三小大小
		assertTrue("-".equals(result.get("lsscq3bxds_4_8_2")));		//前三小大單
		assertTrue("-".equals(result.get("lsscq3bxds_4_8_1")));		//前三小大雙
		assertTrue("-".equals(result.get("lsscq3bxds_4_4_8")));		//前三小小大
		assertTrue("1".equals(result.get("lsscq3bxds_4_4_4")));		//前三小小小
		assertTrue("1".equals(result.get("lsscq3bxds_4_4_2")));		//前三小小單
		assertTrue("-".equals(result.get("lsscq3bxds_4_4_1")));		//前三小小雙
		assertTrue("-".equals(result.get("lsscq3bxds_4_2_8")));		//前三小單大
		assertTrue("-".equals(result.get("lsscq3bxds_4_2_4")));		//前三小單小
		assertTrue("-".equals(result.get("lsscq3bxds_4_2_2")));		//前三小單單
		assertTrue("-".equals(result.get("lsscq3bxds_4_2_1")));		//前三小單雙
		assertTrue("-".equals(result.get("lsscq3bxds_4_1_8")));		//前三小雙大
		assertTrue("1".equals(result.get("lsscq3bxds_4_1_4")));		//前三小雙小
		assertTrue("1".equals(result.get("lsscq3bxds_4_1_2")));		//前三小雙單
		assertTrue("-".equals(result.get("lsscq3bxds_4_1_1")));		//前三小雙雙
		assertTrue("-".equals(result.get("lsscq3bxds_2_8_8")));		//前三單大大
		assertTrue("-".equals(result.get("lsscq3bxds_2_8_4")));		//前三單大小
		assertTrue("-".equals(result.get("lsscq3bxds_2_8_2")));		//前三單大單
		assertTrue("-".equals(result.get("lsscq3bxds_2_8_1")));		//前三單大雙
		assertTrue("-".equals(result.get("lsscq3bxds_2_4_8")));		//前三單小大
		assertTrue("1".equals(result.get("lsscq3bxds_2_4_4")));		//前三單小小
		assertTrue("1".equals(result.get("lsscq3bxds_2_4_2")));		//前三單小單
		assertTrue("-".equals(result.get("lsscq3bxds_2_4_1")));		//前三單小雙
		assertTrue("-".equals(result.get("lsscq3bxds_2_2_8")));		//前三單單大
		assertTrue("-".equals(result.get("lsscq3bxds_2_2_4")));		//前三單單小
		assertTrue("-".equals(result.get("lsscq3bxds_2_2_2")));		//前三單單單
		assertTrue("-".equals(result.get("lsscq3bxds_2_2_1")));		//前三單單雙
		assertTrue("-".equals(result.get("lsscq3bxds_2_1_8")));		//前三單雙大
		assertTrue("1".equals(result.get("lsscq3bxds_2_1_4")));		//前三單雙小
		assertTrue("1".equals(result.get("lsscq3bxds_2_1_2")));		//前三單雙單
		assertTrue("-".equals(result.get("lsscq3bxds_2_1_1")));		//前三單雙雙
		assertTrue("-".equals(result.get("lsscq3bxds_1_8_8")));		//前三雙大大
		assertTrue("-".equals(result.get("lsscq3bxds_1_8_4")));		//前三雙大小
		assertTrue("-".equals(result.get("lsscq3bxds_1_8_2")));		//前三雙大單
		assertTrue("-".equals(result.get("lsscq3bxds_1_8_1")));		//前三雙大雙
		assertTrue("-".equals(result.get("lsscq3bxds_1_4_8")));		//前三雙小大
		assertTrue("-".equals(result.get("lsscq3bxds_1_4_4")));		//前三雙小小
		assertTrue("-".equals(result.get("lsscq3bxds_1_4_2")));		//前三雙小單
		assertTrue("-".equals(result.get("lsscq3bxds_1_4_1")));		//前三雙小雙
		assertTrue("-".equals(result.get("lsscq3bxds_1_2_8")));		//前三雙單大
		assertTrue("-".equals(result.get("lsscq3bxds_1_2_4")));		//前三雙單小
		assertTrue("-".equals(result.get("lsscq3bxds_1_2_2")));		//前三雙單單
		assertTrue("-".equals(result.get("lsscq3bxds_1_2_1")));		//前三雙單雙
		assertTrue("-".equals(result.get("lsscq3bxds_1_1_8")));		//前三雙雙大
		assertTrue("-".equals(result.get("lsscq3bxds_1_1_4")));		//前三雙雙小
		assertTrue("-".equals(result.get("lsscq3bxds_1_1_2")));		//前三雙雙單
		assertTrue("-".equals(result.get("lsscq3bxds_1_1_1")));		//前三雙雙雙
		assertTrue("-".equals(result.get("lssch3bxds_8_8_8")));		//後三大大大
		assertTrue("-".equals(result.get("lssch3bxds_8_8_4")));		//後三大大小
		assertTrue("-".equals(result.get("lssch3bxds_8_8_2")));		//後三大大單
		assertTrue("-".equals(result.get("lssch3bxds_8_8_1")));		//後三大大雙
		assertTrue("-".equals(result.get("lssch3bxds_8_4_8")));		//後三大小大
		assertTrue("-".equals(result.get("lssch3bxds_8_4_4")));		//後三大小小
		assertTrue("-".equals(result.get("lssch3bxds_8_4_2")));		//後三大小單
		assertTrue("-".equals(result.get("lssch3bxds_8_4_1")));		//後三大小雙
		assertTrue("-".equals(result.get("lssch3bxds_8_2_8")));		//後三大單大
		assertTrue("-".equals(result.get("lssch3bxds_8_2_4")));		//後三大單小
		assertTrue("-".equals(result.get("lssch3bxds_8_2_2")));		//後三大單單
		assertTrue("-".equals(result.get("lssch3bxds_8_2_1")));		//後三大單雙
		assertTrue("-".equals(result.get("lssch3bxds_8_1_8")));		//後三大雙大
		assertTrue("-".equals(result.get("lssch3bxds_8_1_4")));		//後三大雙小
		assertTrue("-".equals(result.get("lssch3bxds_8_1_2")));		//後三大雙單
		assertTrue("-".equals(result.get("lssch3bxds_8_1_1")));		//後三大雙雙
		assertTrue("-".equals(result.get("lssch3bxds_4_8_8")));		//後三小大大
		assertTrue("-".equals(result.get("lssch3bxds_4_8_4")));		//後三小大小
		assertTrue("-".equals(result.get("lssch3bxds_4_8_2")));		//後三小大單
		assertTrue("-".equals(result.get("lssch3bxds_4_8_1")));		//後三小大雙
		assertTrue("1".equals(result.get("lssch3bxds_4_4_8")));		//後三小小大
		assertTrue("-".equals(result.get("lssch3bxds_4_4_4")));		//後三小小小
		assertTrue("1".equals(result.get("lssch3bxds_4_4_2")));		//後三小小單
		assertTrue("-".equals(result.get("lssch3bxds_4_4_1")));		//後三小小雙
		assertTrue("-".equals(result.get("lssch3bxds_4_2_8")));		//後三小單大
		assertTrue("-".equals(result.get("lssch3bxds_4_2_4")));		//後三小單小
		assertTrue("-".equals(result.get("lssch3bxds_4_2_2")));		//後三小單單
		assertTrue("-".equals(result.get("lssch3bxds_4_2_1")));		//後三小單雙
		assertTrue("1".equals(result.get("lssch3bxds_4_1_8")));		//後三小雙大
		assertTrue("-".equals(result.get("lssch3bxds_4_1_4")));		//後三小雙小
		assertTrue("1".equals(result.get("lssch3bxds_4_1_2")));		//後三小雙單
		assertTrue("-".equals(result.get("lssch3bxds_4_1_1")));		//後三小雙雙
		assertTrue("-".equals(result.get("lssch3bxds_2_8_8")));		//後三單大大
		assertTrue("-".equals(result.get("lssch3bxds_2_8_4")));		//後三單大小
		assertTrue("-".equals(result.get("lssch3bxds_2_8_2")));		//後三單大單
		assertTrue("-".equals(result.get("lssch3bxds_2_8_1")));		//後三單大雙
		assertTrue("1".equals(result.get("lssch3bxds_2_4_8")));		//後三單小大
		assertTrue("-".equals(result.get("lssch3bxds_2_4_4")));		//後三單小小
		assertTrue("1".equals(result.get("lssch3bxds_2_4_2")));		//後三單小單
		assertTrue("-".equals(result.get("lssch3bxds_2_4_1")));		//後三單小雙
		assertTrue("-".equals(result.get("lssch3bxds_2_2_8")));		//後三單單大
		assertTrue("-".equals(result.get("lssch3bxds_2_2_4")));		//後三單單小
		assertTrue("-".equals(result.get("lssch3bxds_2_2_2")));		//後三單單單
		assertTrue("-".equals(result.get("lssch3bxds_2_2_1")));		//後三單單雙
		assertTrue("1".equals(result.get("lssch3bxds_2_1_8")));		//後三單雙大
		assertTrue("-".equals(result.get("lssch3bxds_2_1_4")));		//後三單雙小
		assertTrue("1".equals(result.get("lssch3bxds_2_1_2")));		//後三單雙單
		assertTrue("-".equals(result.get("lssch3bxds_2_1_1")));		//後三單雙雙
		assertTrue("-".equals(result.get("lssch3bxds_1_8_8")));		//後三雙大大
		assertTrue("-".equals(result.get("lssch3bxds_1_8_4")));		//後三雙大小
		assertTrue("-".equals(result.get("lssch3bxds_1_8_2")));		//後三雙大單
		assertTrue("-".equals(result.get("lssch3bxds_1_8_1")));		//後三雙大雙
		assertTrue("-".equals(result.get("lssch3bxds_1_4_8")));		//後三雙小大
		assertTrue("-".equals(result.get("lssch3bxds_1_4_4")));		//後三雙小小
		assertTrue("-".equals(result.get("lssch3bxds_1_4_2")));		//後三雙小單
		assertTrue("-".equals(result.get("lssch3bxds_1_4_1")));		//後三雙小雙
		assertTrue("-".equals(result.get("lssch3bxds_1_2_8")));		//後三雙單大
		assertTrue("-".equals(result.get("lssch3bxds_1_2_4")));		//後三雙單小
		assertTrue("-".equals(result.get("lssch3bxds_1_2_2")));		//後三雙單單
		assertTrue("-".equals(result.get("lssch3bxds_1_2_1")));		//後三雙單雙
		assertTrue("-".equals(result.get("lssch3bxds_1_1_8")));		//後三雙雙大
		assertTrue("-".equals(result.get("lssch3bxds_1_1_4")));		//後三雙雙小
		assertTrue("-".equals(result.get("lssch3bxds_1_1_2")));		//後三雙雙單
		assertTrue("-".equals(result.get("lssch3bxds_1_1_1")));		//後三雙雙雙
		assertTrue("0,0,3,4,5".equals(result.get("ssc5mqw3x")));		// 5碼趣味三星
		assertTrue("0,3,4,5".equals(result.get("ssc4mqw3x")));		// 4碼趣味三星
		assertTrue("0,2,3".equals(result.get("sscq3qw2x")));		// 前3趣味二星
		assertTrue("0,4,5".equals(result.get("ssch3qw2x")));		// 後3趣味二星
		assertTrue("0,1,3,4,5".equals(result.get("ssc5mqc3x")));		// 5碼區間三星
		assertTrue("1,3,4,5".equals(result.get("ssc4mqc3x")));		// 4碼區間三星
		assertTrue("0,2,3".equals(result.get("sscq3qc2x")));		// 前3區間二星
		assertTrue("1,4,5".equals(result.get("ssch3qc2x")));		// 後3區間二星
		assertTrue("1_2_3_4_5".equals(result.get("sscyffs")));		//一帆風順
		assertTrue("-".equals(result.get("sschscs")));		// 好事成雙
		assertTrue("-".equals(result.get("sscsxbx")));		// 三星報喜
		assertTrue("-".equals(result.get("sscsjfc")));		// 四季發財
		assertTrue("1|3".equals(result.get("sscr2_20")));		//任二複式
		assertTrue("4".equals(result.get("sscr2hz_20")));	//任二直選和值
		assertTrue("2".equals(result.get("sscr2kd_20")));	//任二跨度
		assertTrue("1,3".equals(result.get("sscr2z_20")));		//任二組選
		assertTrue("4".equals(result.get("sscr2zhz_20")));	//任二組選和值
		assertTrue("1|4".equals(result.get("sscr2_18")));		//任二複式
		assertTrue("5".equals(result.get("sscr2hz_18")));	//任二直選和值
		assertTrue("3".equals(result.get("sscr2kd_18")));	//任二跨度
		assertTrue("1,4".equals(result.get("sscr2z_18")));		//任二組選
		assertTrue("5".equals(result.get("sscr2zhz_18")));	//任二組選和值
		assertTrue("1|5".equals(result.get("sscr2_17")));		//任二複式
		assertTrue("6".equals(result.get("sscr2hz_17")));	//任二直選和值
		assertTrue("4".equals(result.get("sscr2kd_17")));	//任二跨度
		assertTrue("1,5".equals(result.get("sscr2z_17")));		//任二組選
		assertTrue("6".equals(result.get("sscr2zhz_17")));	//任二組選和值
		assertTrue("2|3".equals(result.get("sscr2_12")));		//任二複式
		assertTrue("5".equals(result.get("sscr2hz_12")));	//任二直選和值
		assertTrue("1".equals(result.get("sscr2kd_12")));	//任二跨度
		assertTrue("2,3".equals(result.get("sscr2z_12")));		//任二組選
		assertTrue("5".equals(result.get("sscr2zhz_12")));	//任二組選和值
		assertTrue("2|4".equals(result.get("sscr2_10")));		//任二複式
		assertTrue("6".equals(result.get("sscr2hz_10")));	//任二直選和值
		assertTrue("2".equals(result.get("sscr2kd_10")));	//任二跨度
		assertTrue("2,4".equals(result.get("sscr2z_10")));		//任二組選
		assertTrue("6".equals(result.get("sscr2zhz_10")));	//任二組選和值
		assertTrue("2|5".equals(result.get("sscr2_9")));		//任二複式
		assertTrue("7".equals(result.get("sscr2hz_9")));		//任二直選和值
		assertTrue("3".equals(result.get("sscr2kd_9")));		//任二跨度
		assertTrue("2,5".equals(result.get("sscr2z_9")));		//任二組選
		assertTrue("7".equals(result.get("sscr2zhz_9")));	//任二組選和值
		assertTrue("3|4".equals(result.get("sscr2_6")));		//任二複式
		assertTrue("7".equals(result.get("sscr2hz_6")));		//任二直選和值
		assertTrue("1".equals(result.get("sscr2kd_6")));		//任二跨度
		assertTrue("3,4".equals(result.get("sscr2z_6")));		//任二組選
		assertTrue("7".equals(result.get("sscr2zhz_6")));	//任二組選和值
		assertTrue("3|5".equals(result.get("sscr2_5")));		//任二複式
		assertTrue("8".equals(result.get("sscr2hz_5")));		//任二直選和值
		assertTrue("2".equals(result.get("sscr2kd_5")));		//任二跨度
		assertTrue("3,5".equals(result.get("sscr2z_5")));		//任二組選
		assertTrue("8".equals(result.get("sscr2zhz_5")));	//任二組選和值
		assertTrue("1|2|4".equals(result.get("sscr3_26")));			//任三直選
		assertTrue("7".equals(result.get("sscr3hz_26")));		//任三直選和值
		assertTrue("3".equals(result.get("sscr3kd_26")));		//任三跨度
		assertTrue("7|-".equals(result.get("sscr3zhz_26")));		//任三組選和值
		assertTrue("-".equals(result.get("sscr3z3_26")));		//任三組三
		assertTrue("1,2,4".equals(result.get("sscr3z6_26")));		//任三組六
		assertTrue("-".equals(result.get("sscr3z3ds_26")));		//任三組三單式
		assertTrue("1|2|5".equals(result.get("sscr3_25")));			//任三直選
		assertTrue("8".equals(result.get("sscr3hz_25")));		//任三直選和值
		assertTrue("4".equals(result.get("sscr3kd_25")));		//任三跨度
		assertTrue("8|-".equals(result.get("sscr3zhz_25")));		//任三組選和值
		assertTrue("-".equals(result.get("sscr3z3_25")));		//任三組三
		assertTrue("1,2,5".equals(result.get("sscr3z6_25")));		//任三組六
		assertTrue("-".equals(result.get("sscr3z3ds_25")));		//任三組三單式
		assertTrue("1|3|4".equals(result.get("sscr3_22")));			//任三直選
		assertTrue("8".equals(result.get("sscr3hz_22")));		//任三直選和值
		assertTrue("3".equals(result.get("sscr3kd_22")));		//任三跨度
		assertTrue("8|-".equals(result.get("sscr3zhz_22")));		//任三組選和值
		assertTrue("-".equals(result.get("sscr3z3_22")));		//任三組三
		assertTrue("1,3,4".equals(result.get("sscr3z6_22")));		//任三組六
		assertTrue("-".equals(result.get("sscr3z3ds_22")));		//任三組三單式
		assertTrue("1|3|5".equals(result.get("sscr3_21")));			//任三直選
		assertTrue("9".equals(result.get("sscr3hz_21")));		//任三直選和值
		assertTrue("4".equals(result.get("sscr3kd_21")));		//任三跨度
		assertTrue("9|-".equals(result.get("sscr3zhz_21")));		//任三組選和值
		assertTrue("-".equals(result.get("sscr3z3_21")));		//任三組三
		assertTrue("1,3,5".equals(result.get("sscr3z6_21")));		//任三組六
		assertTrue("-".equals(result.get("sscr3z3ds_21")));		//任三組三單式
		assertTrue("1|4|5".equals(result.get("sscr3_19")));			//任三直選
		assertTrue("10".equals(result.get("sscr3hz_19")));		//任三直選和值
		assertTrue("4".equals(result.get("sscr3kd_19")));		//任三跨度
		assertTrue("10|-".equals(result.get("sscr3zhz_19")));		//任三組選和值
		assertTrue("-".equals(result.get("sscr3z3_19")));		//任三組三
		assertTrue("1,4,5".equals(result.get("sscr3z6_19")));		//任三組六
		assertTrue("-".equals(result.get("sscr3z3ds_19")));		//任三組三單式
		assertTrue("2|3|5".equals(result.get("sscr3_13")));			//任三直選
		assertTrue("10".equals(result.get("sscr3hz_13")));		//任三直選和值
		assertTrue("3".equals(result.get("sscr3kd_13")));		//任三跨度
		assertTrue("10|-".equals(result.get("sscr3zhz_13")));		//任三組選和值
		assertTrue("-".equals(result.get("sscr3z3_13")));		//任三組三
		assertTrue("2,3,5".equals(result.get("sscr3z6_13")));		//任三組六
		assertTrue("-".equals(result.get("sscr3z3ds_13")));		//任三組三單式
		assertTrue("2|4|5".equals(result.get("sscr3_11")));			//任三直選
		assertTrue("11".equals(result.get("sscr3hz_11")));		//任三直選和值
		assertTrue("3".equals(result.get("sscr3kd_11")));		//任三跨度
		assertTrue("11|-".equals(result.get("sscr3zhz_11")));		//任三組選和值
		assertTrue("-".equals(result.get("sscr3z3_11")));		//任三組三
		assertTrue("2,4,5".equals(result.get("sscr3z6_11")));		//任三組六
		assertTrue("-".equals(result.get("sscr3z3ds_11")));		//任三組三單式
		assertTrue("1|2|3|5".equals(result.get("sscr4_29")));		// 任四直選
		assertTrue("1,2,3,5".equals(result.get("sscr4z24_29")));	// 任四組選24
		assertTrue("-".equals(result.get("sscr4z12_29")));	// 任四組選12
		assertTrue("-".equals(result.get("sscr4z6_29")));	// 任四組選6
		assertTrue("-".equals(result.get("sscr4z4_29")));	// 任四組選4
		assertTrue("1|2|4|5".equals(result.get("sscr4_27")));		// 任四直選
		assertTrue("1,2,4,5".equals(result.get("sscr4z24_27")));	// 任四組選24
		assertTrue("-".equals(result.get("sscr4z12_27")));	// 任四組選12
		assertTrue("-".equals(result.get("sscr4z6_27")));	// 任四組選6
		assertTrue("-".equals(result.get("sscr4z4_27")));	// 任四組選4
		assertTrue("1|3|4|5".equals(result.get("sscr4_23")));		// 任四直選
		assertTrue("1,3,4,5".equals(result.get("sscr4z24_23")));	// 任四組選24
		assertTrue("-".equals(result.get("sscr4z12_23")));	// 任四組選12
		assertTrue("-".equals(result.get("sscr4z6_23")));	// 任四組選6
		assertTrue("-".equals(result.get("sscr4z4_23")));	// 任四組選4
		assertTrue("-".equals(result.get("sscr2lh_t_24")));	// 任二龍虎和_和
		assertTrue("-".equals(result.get("sscr2lh_t_20")));	// 任二龍虎和_和
		assertTrue("-".equals(result.get("sscr2lh_t_18")));	// 任二龍虎和_和
		assertTrue("-".equals(result.get("sscr2lh_t_17")));	// 任二龍虎和_和
		assertTrue("-".equals(result.get("sscr2lh_t_12")));	// 任二龍虎和_和
		assertTrue("-".equals(result.get("sscr2lh_t_10")));	// 任二龍虎和_和
		assertTrue("-".equals(result.get("sscr2lh_t_9")));	// 任二龍虎和_和
		assertTrue("-".equals(result.get("sscr2lh_t_6")));	// 任二龍虎和_和
		assertTrue("-".equals(result.get("sscr2lh_t_5")));	// 任二龍虎和_和
		assertTrue("-".equals(result.get("sscr2lh_t_3")));	// 任二龍虎和_和
		assertTrue("H".equals(result.get("sscr2lh_lh_24")));	//任二龍虎和_龍虎
		assertTrue("H".equals(result.get("sscr2lh_lh_20")));	//任二龍虎和_龍虎
		assertTrue("H".equals(result.get("sscr2lh_lh_18")));	//任二龍虎和_龍虎
		assertTrue("H".equals(result.get("sscr2lh_lh_17")));	//任二龍虎和_龍虎
		assertTrue("H".equals(result.get("sscr2lh_lh_12")));	//任二龍虎和_龍虎
		assertTrue("H".equals(result.get("sscr2lh_lh_10")));	//任二龍虎和_龍虎
		assertTrue("H".equals(result.get("sscr2lh_lh_9")));	//任二龍虎和_龍虎
		assertTrue("H".equals(result.get("sscr2lh_lh_6")));	//任二龍虎和_龍虎
		assertTrue("H".equals(result.get("sscr2lh_lh_5")));	//任二龍虎和_龍虎
		assertTrue("H".equals(result.get("sscr2lh_lh_3")));	//任二龍虎和_龍虎
	}
}
