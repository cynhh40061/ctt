package tw.com.ctt.constant;

public final class AccountManagerConstant {

	public AccountManagerConstant() {

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

	public static final String ACC_LEVEL_COM_TABLE_NAME = "com";
	public static final String ACC_LEVEL_SC_TABLE_NAME = "sc";
	public static final String ACC_LEVEL_BC_TABLE_NAME = "bc";
	public static final String ACC_LEVEL_CO_TABLE_NAME = "co";
	public static final String ACC_LEVEL_SA_TABLE_NAME = "sa";
	public static final String ACC_LEVEL_AG_TABLE_NAME = "ag";
	public static final String ACC_LEVEL_AG1_TABLE_NAME = "ag1";
	public static final String ACC_LEVEL_AG2_TABLE_NAME = "ag2";
	public static final String ACC_LEVEL_AG3_TABLE_NAME = "ag3";
	public static final String ACC_LEVEL_AG4_TABLE_NAME = "ag4";
	public static final String ACC_LEVEL_AG5_TABLE_NAME = "ag5";
	public static final String ACC_LEVEL_AG6_TABLE_NAME = "ag6";
	public static final String ACC_LEVEL_AG7_TABLE_NAME = "ag7";
	public static final String ACC_LEVEL_AG8_TABLE_NAME = "ag8";
	public static final String ACC_LEVEL_AG9_TABLE_NAME = "ag9";
	public static final String ACC_LEVEL_AG10_TABLE_NAME = "ag10";

	public static final String ACC_LEVEL_ADMIN_NICKNAME = "Admin";
	public static final String ACC_LEVEL_COM_NICKNAME = "公司";
	public static final String ACC_LEVEL_SC_NICKNAME = "總監";
	public static final String ACC_LEVEL_BC_NICKNAME = "大股東";
	public static final String ACC_LEVEL_CO_NICKNAME = "股東";
	public static final String ACC_LEVEL_SA_NICKNAME = "總代理";
	public static final String ACC_LEVEL_AG_NICKNAME = "代理";
	public static final String ACC_LEVEL_AG1_NICKNAME = "代理1";
	public static final String ACC_LEVEL_AG2_NICKNAME = "代理2";
	public static final String ACC_LEVEL_AG3_NICKNAME = "代理3";
	public static final String ACC_LEVEL_AG4_NICKNAME = "代理4";
	public static final String ACC_LEVEL_AG5_NICKNAME = "代理5";
	public static final String ACC_LEVEL_AG6_NICKNAME = "代理6";
	public static final String ACC_LEVEL_AG7_NICKNAME = "代理7";
	public static final String ACC_LEVEL_AG8_NICKNAME = "代理8";
	public static final String ACC_LEVEL_AG9_NICKNAME = "代理9";
	public static final String ACC_LEVEL_AG10_NICKNAME = "代理10";
	public static final String ACC_LEVEL_MEM_NICKNAME = "會員";

	public static final int[] ACC_LEVEL_MAG = { ACC_LEVEL_SC, ACC_LEVEL_BC, ACC_LEVEL_CO, ACC_LEVEL_SA, ACC_LEVEL_AG, ACC_LEVEL_AG1, ACC_LEVEL_AG2,
			ACC_LEVEL_AG3, ACC_LEVEL_AG4, ACC_LEVEL_AG5, ACC_LEVEL_AG6, ACC_LEVEL_AG7, ACC_LEVEL_AG8, ACC_LEVEL_AG9, ACC_LEVEL_AG10 };

	public static final String[] ACC_LEVEL_MAG_TABLE_NAME = { ACC_LEVEL_SC_TABLE_NAME, ACC_LEVEL_BC_TABLE_NAME, ACC_LEVEL_CO_TABLE_NAME,
			ACC_LEVEL_SA_TABLE_NAME, ACC_LEVEL_AG_TABLE_NAME, ACC_LEVEL_AG1_TABLE_NAME, ACC_LEVEL_AG2_TABLE_NAME, ACC_LEVEL_AG3_TABLE_NAME,
			ACC_LEVEL_AG4_TABLE_NAME, ACC_LEVEL_AG5_TABLE_NAME, ACC_LEVEL_AG6_TABLE_NAME, ACC_LEVEL_AG7_TABLE_NAME, ACC_LEVEL_AG8_TABLE_NAME,
			ACC_LEVEL_AG9_TABLE_NAME, ACC_LEVEL_AG10_TABLE_NAME };

	public static final String[] ACC_LEVEL_NICKNAME_ARR = { ACC_LEVEL_ADMIN_NICKNAME, ACC_LEVEL_COM_NICKNAME, ACC_LEVEL_SC_NICKNAME,
			ACC_LEVEL_BC_NICKNAME, ACC_LEVEL_CO_NICKNAME, ACC_LEVEL_SA_NICKNAME, ACC_LEVEL_AG_NICKNAME, ACC_LEVEL_AG1_NICKNAME,
			ACC_LEVEL_AG2_NICKNAME, ACC_LEVEL_AG3_NICKNAME, ACC_LEVEL_AG4_NICKNAME, ACC_LEVEL_AG5_NICKNAME, ACC_LEVEL_AG6_NICKNAME,
			ACC_LEVEL_AG7_NICKNAME, ACC_LEVEL_AG8_NICKNAME, ACC_LEVEL_AG9_NICKNAME, ACC_LEVEL_AG10_NICKNAME };
	
	public static final int[] ACC_LEVEL_ARR = { ACC_LEVEL_COM,ACC_LEVEL_SC, ACC_LEVEL_BC, ACC_LEVEL_CO, ACC_LEVEL_SA, ACC_LEVEL_AG, ACC_LEVEL_AG1, ACC_LEVEL_AG2,
			ACC_LEVEL_AG3, ACC_LEVEL_AG4, ACC_LEVEL_AG5, ACC_LEVEL_AG6, ACC_LEVEL_AG7, ACC_LEVEL_AG8, ACC_LEVEL_AG9, ACC_LEVEL_AG10 };
	
	public static final String[] ACC_LEVE_TABLE_NAME_ARR = { ACC_LEVEL_COM_TABLE_NAME,ACC_LEVEL_SC_TABLE_NAME, ACC_LEVEL_BC_TABLE_NAME, ACC_LEVEL_CO_TABLE_NAME,
			ACC_LEVEL_SA_TABLE_NAME, ACC_LEVEL_AG_TABLE_NAME, ACC_LEVEL_AG1_TABLE_NAME, ACC_LEVEL_AG2_TABLE_NAME, ACC_LEVEL_AG3_TABLE_NAME,
			ACC_LEVEL_AG4_TABLE_NAME, ACC_LEVEL_AG5_TABLE_NAME, ACC_LEVEL_AG6_TABLE_NAME, ACC_LEVEL_AG7_TABLE_NAME, ACC_LEVEL_AG8_TABLE_NAME,
			ACC_LEVEL_AG9_TABLE_NAME, ACC_LEVEL_AG10_TABLE_NAME };
	

	public static final String ACC_LEVEL_NICKNAME_MEM = "會員";

	public static final int DIRECTLY_UNDER_MEM = 0;
	public static final int GRNERAL_MEM = 1;

	public static final int STATUS_ENABLED = 1;
	public static final int STATUS_NOLOGIN = 2;
	public static final int STATUS_DISABLED = 3;
	public static final int STATUS_DELETE = 4;
	
	public static final int STATUS_ENABLED_UPDATE_FAIL = 10;
	public static final int STATUS_NOLOGIN_UPDATE_FAIL = 20;
	public static final int STATUS_DISABLED_UPDATE_FAIL = 30;
	public static final int STATUS_DELETE_UPDATE_FAIL = 40;

	public static final int LOTTERY = 1;
	public static final int LIVEVIDEO = 2;
	public static final int MOVEMENT = 3;
	public static final int VIDEOGAME = 4;
	public static final int GAME = 5;

	public static final int[] GAME_TYPE_ARR = { LOTTERY, LIVEVIDEO, MOVEMENT, VIDEOGAME, GAME };

	public static final int FULL_RATIO_ENABLED = 1;

	public static final int FULL_RATIO_DISABLED = 0;

}
