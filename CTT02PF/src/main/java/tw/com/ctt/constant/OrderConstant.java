package tw.com.ctt.constant;

public final class OrderConstant {
	public OrderConstant() {

	}

	public final static String rechargeCarryOutStatus = "5,6,7,8,9";

	public final static String rechargeUndoneStatus = "1,2,3,4";

	public final static String withdrawalCarryOutStatus = "5,6,7,8,9";

	public final static String withdrawalUndoneStatus = "1,2";

	public final static String ReviewStatus = "5,6";

	public final static int ORDER_RECHARGE = 0;
	public final static int ORDER_WITHDRAWAL = 1;

	public final static int NO_AUDIT_ACC_ID = 0;

	public final static int ORDER_STATUS_1_WAIT_1ST_AUDIT = 1; // 待初審核
	public final static int ORDER_STATUS_2_1ST_AUDITING = 2; // 初審審核中
	public final static int ORDER_STATUS_3_WAIT_2ND_AUDIT = 3; // 待二審
	public final static int ORDER_STATUS_4_2ND_AUDITING = 4; // 二審審核中
	public final static int ORDER_STATUS_5_WAIT_LAST_AUDIT = 5; // 待複查
	public final static int ORDER_STATUS_6_LAST_AUDIT = 6; // 複查中
	public final static int ORDER_STATUS_7_COMPLETE = 7; // 完成
	public final static int ORDER_STATUS_8_REFUSE = 8; // 拒絕
	public final static int ORDER_STATUS_9_ERROR = 9; // 異常
	public final static int ORDER_STATUS_10_API = 10; // API完成

	public final static String MAX_AMOUNT = "9999999999";

	public static final int DIRECTLY_UNDER_MEM = 0;
	public static final int GRNERAL_MEM = 1;
}
