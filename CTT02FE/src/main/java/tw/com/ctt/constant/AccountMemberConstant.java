package tw.com.ctt.constant;

public final class AccountMemberConstant {
	public AccountMemberConstant() {
		
	}
	

	public static final int ACC_LEVEL_ADMIN = 0;
	public static final int ACC_LEVEL_COM = 1;
	public static final int ACC_LEVEL_SC = 2;
	public static final int ACC_LEVEL_BC = 3;
	public static final int ACC_LEVEL_CO = 4;
	public static final int ACC_LEVEL_SA = 5;
	public static final int ACC_LEVEL_AG = 6;
	public static final int ACC_LEVEL_AG1 = 7;
	public static final int ACC_LEVEL_AG2 = 8;
	public static final int ACC_LEVEL_AG3 = 9;
	public static final int ACC_LEVEL_AG4 = 10;
	public static final int ACC_LEVEL_AG5 = 11;
	public static final int ACC_LEVEL_AG6 = 12;
	public static final int ACC_LEVEL_AG7 = 13;
	public static final int ACC_LEVEL_AG8 = 14;
	public static final int ACC_LEVEL_AG9 = 15;
	public static final int ACC_LEVEL_AG10 = 16;
	public static final int ACC_LEVEL_MEM = 100;
	
	public static final String ACC_ID_KEY = "accId";
	public static final String ACC_TOKENID_KEY = "tokenId";
	public static final String ACC_NAME_KEY = "accName";
	public static final String ACC_BALANCE_KEY = "accBalance";
	public static final String ACC_NICKNAME_KEY = "accNickName";
	public static final String ACC_STATUS_KEY = "accStatus";

	public static final int ORDER_RECHARGE = 0;
	public static final int ORDER_WITHTHDRAWAL = 1;
	
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
	
	public final static String MAX_AMOUNT = "200000";
	public final static String MIN_AMOUNT = "100";
	
	public final static int BANK_CARD_1 = 1;
	public final static int BANK_CARD_2 = 2;
	
	public final static int CHECK_LENGTH_10 = 10;
	public final static int CHECK_LENGTH_20 = 20;
	
	
	public static final int DIRECTLY_UNDER_MEM = 0;
	public static final int GRNERAL_MEM = 1;
	
	
	
	public static final int LOTTERY = 1;
	public static final int LIVEVIDEO = 2;
	public static final int MOVEMENT = 3;
	public static final int VIDEOGAME = 4;
	public static final int GAME = 5;

	public static final int[] GAME_TYPE_ARR = { LOTTERY, LIVEVIDEO, MOVEMENT, VIDEOGAME, GAME };



}
