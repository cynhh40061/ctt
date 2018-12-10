package tw.com.ctt.constant;

public final class LogToDBConstant {
	private LogToDBConstant() {
	}
	public static final String LOG_NO_DATA = "no data";
	
public static final int OPS_TYPE_MANAGER = 1;
	
	public static final int OPS_TYPE_MEMBER = 2;
	
	public static final int ACC_TYPE_MANAGER = 1;
	
	public static final int ACC_TYPE_MEMBER = 2;
	
	public static final int NO_TYPE = 0;
	
	public static final int NO_ID = 0;
	
	
	public static final int LOG_ACTION_LOGIN_AND_LOGOUT = 14;//log action 前台_登入登出紀錄(上層)
	
	public static final int LOG_ACTION_LOGIN = 52;//log action 前台登入
	
	public static final int LOG_ACTION_LOGOUT = 53;//log action 前台登出
	
	public static final int LOG_ACTION_ACC_CHANGE = 15;//log action 前台_狀態更動紀錄(上層)
	
	public static final int LOG_ACTION_ADD_ACC = 54;//log action 前台新增帳號
	
	public static final int LOG_ACTION_ADD_ORDER = 16;//log action 前台_建立水單紀錄(上層)
	
	public static final int LOG_ACTION_ADD_WITHDRAWAL_ORDER = 55;//log action 前台建立提款水單
	
	public static final int LOG_ACTION_ADD_RECHARGE_ORDER = 56;//log action 前台建立充值水單
	
	public static final int LOG_ACTION_DETAIL_CHANGE = 17;//log action 前台_詳細資料更動紀錄(上層)
	
	public static final int LOG_ACTION_SET_MEM_ACC = 57;//log action 前台修改會員資料
	
	public static final int LOG_ACTION_SET_PASSWORD = 58;//log action 前台修改密碼
	
	public static final int LOG_ACTION_SET_WITHDRAWAL_PASSWORD = 59;//log action 前台修改取款密碼
	
	public static final int LOG_ACTION_ADD_BANK_CARD = 60;//log action 前台新增銀行卡
	
	
	public static final int LOG_ACTION_MONEY = 19;//log action 現金紀錄_前台(上層)
	
	public static final int LOG_ACTION_RECHARGE_ORDER_ADD_SUCCESS_DEBIT = 69;//log action 前台建立提款水單扣款
}
