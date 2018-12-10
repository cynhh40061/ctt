package tw.com.ctt.constant;

import java.util.Date;

public class LotteryUtilConstant {
	
	public static long CORRECTION_TIME = 0;
	
	public static Date getNowDate(long s) {
		return new Date(s);
	}
	public static Date getNowDate() {
		return getNowDate(new Date().getTime()+CORRECTION_TIME);
	}
	

}
