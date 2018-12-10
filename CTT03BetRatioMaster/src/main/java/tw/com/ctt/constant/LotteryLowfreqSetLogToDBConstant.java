package tw.com.ctt.constant;

public final class LotteryLowfreqSetLogToDBConstant {
	private LotteryLowfreqSetLogToDBConstant() {
	}

	public static final String LOG_NO_DATA = "no data";

	public static final int LOG_NUM_ZERO = 0;

	public static final int LOG_ACTION_LOTTERY_LOWFREQ_SET = 72;// log action 後台_賠率設定_低頻彩

	public static final int LOG_ACTION_LOTTERY_LOWFREQ_HANDICAP_SET = 73;// log action 低頻彩盤_口資料設定
	public static final int LOG_ACTION_LOTTERY_LOWFREQ_DESCRIPTION_SET = 74;// log action 低頻彩_描述修改

	public static final int LOG_ACTION_LOTTERY_LOWFREQ_FC3D_BASE_BASELINE_SET = 75;// log action 福彩3D基礎賠率設定
	public static final int LOG_ACTION_LOTTERY_LOWFREQ_PL5_BASE_BASELINE_SET = 76;// log action 排列35基礎賠率設定
	public static final int LOG_ACTION_LOTTERY_LOWFREQ_LHC_BASE_BASELINE_SET = 77;// log action 六合彩基礎賠率設定

	public static final int LOG_ACTION_LOTTERY_LOWFREQ_FC3D_NOW_BASELINE_SET = 78;// log action 福彩3D即時賠率設定
	public static final int LOG_ACTION_LOTTERY_LOWFREQ_PL5_NOW_BASELINE_SET = 79;// log action '排列35即時賠率設定
	public static final int LOG_ACTION_LOTTERY_LOWFREQ_LHC_NOW_BASELINE_SET = 80;// log action 六合彩即時賠率設定

	public static final int LOTTERY_LOWFREQ_FC3D_TYPE = 6;// type 福彩3D
	public static final int LOTTERY_LOWFREQ_PL5_TYPE = 7;// type '排列35
	public static final int LOTTERY_LOWFREQ_LHC_TYPE = 618;// type 六合彩

}
