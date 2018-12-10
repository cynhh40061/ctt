package tw.com.ctt.constant;

public final class LogToDBConstant {
	private LogToDBConstant() {
	}

	public static final String LOG_NO_DATA = "no data";
	
	public static final int OPS_TYPE_MANAGER = 1;
	
	public static final int OPS_TYPE_MEMBER = 2;
	
	public static final int ACC_TYPE_MANAGER = 1;
	
	public static final int ACC_TYPE_MEMBER = 2;
	
	
	
	public static final int LOG_ACTION_LOGIN_AND_LOGOUT = 1;//log action 後台_登入登出紀錄(上層)
	
	public static final int LOG_ACTION_LOGIN = 20;//log action 後台登入
	
	public static final int LOG_ACTION_LOGOUT = 21;//log action 後台登出
	
	public static final int LOG_ACTION_STATUS_CHANGE = 2;//log action 後台_狀態更動紀錄(上層)
	
	public static final int LOG_ACTION_ADD_ACC = 22;//log action 後台新增帳號
	
	public static final int LOG_ACTION_ENABLE_ACC = 23;//log action 啟用帳號
	
	public static final int LOG_ACTION_DISABLE_ACC = 24;//log action 停用帳號
	
	public static final int LOG_ACTION_DO_NOT_LOGIN_ACC = 25;//log action 禁止登入
	
	public static final int LOG_ACTION_DELETE_ACC = 26;//log action 刪除帳號
	
	public static final int LOG_ACTION_OPRATIONAL = 3;//log action 後台_一般紀錄(上層)
	
	public static final int LOG_ACTION_SET_PASSWORD = 27;//log action 修改密碼
	
	public static final int LOG_ACTION_SET_WITHDRAWAL_PASSWORD = 28;//log action 修改取款密碼
	
	public static final int LOG_ACTION_SET_NICKNAME = 29;//log action 修改暱稱
	
	public static final int LOG_ACTION_ADD_ACC_MONEY = 4;//log action 後台_現金紀錄(上層)
	
	public static final int LOG_ACTION_DEPOSIT_ADD = 30;//log action 修改帳號存入增加
	
	public static final int LOG_ACTION_DEPOSIT_DEBIT = 31;//log action 修改帳號存入扣款
	
	public static final int LOG_ACTION_WITHDRAWAL_ADD = 32;//log action 修改帳號提出增加
	
	public static final int LOG_ACTION_WITHDRAWAL_DEBET = 33;//log action 修改帳號提出扣款
	
	public static final int LOG_ACTION_RATIO_CHANGE = 5;//log action 後台_佔成修改紀錄(上層)
	
	public static final int LOG_ACTION_SET_RATIO = 34;//log action 佔成修改
	
	public static final int LOG_ACTION_FULL_RATIO_CHANGE = 6;//log action 後台_佔成方式選擇紀錄(上層)
	
	public static final int LOG_ACTION_SET_FULL_RATIO = 35;//log action 佔成方式選擇
	
	public static final int LOG_ACTION_DETAIL_CHANGE = 7;//log action 後台_詳細資料更動紀錄(上層)
	
	public static final int LOG_ACTION_SET_DETAIL = 36;//log action 詳細資料更動
	
	public static final int LOG_ACTION_ADD_RECHARGE_ORDER_CHANGE = 8;//log action 後台_補單紀錄(上層)
	
	public static final int LOG_ACTION_ADD_RECHARGE_ORDER = 37;//log action 補單
	
	public static final int LOG_ACTION_AUDIT_WITHDRAWAL_ORDER = 9;//log action 後台_提款水單審核紀錄(上層)
	
	public static final int LOG_ACTION_AUDIT_WITHDRAWAL_ORDER_SUCCESS = 38;//log action 提款水單通過
	
	public static final int LOG_ACTION_AUDIT_WITHDRAWAL_ORDER_FAIL = 39;//log action 提款水單拒絕
	
	public static final int LOG_ACTION_AUDIT_RECHARGE_ORDER = 10;//log action 後台_充值水單審核紀錄(上層)
	
	public static final int LOG_ACTION_AUDIT_RECHARGE_ORDER_1ST_SUCCESS = 40;//log action 充值水單一審通過
	
	public static final int LOG_ACTION_AUDIT_RECHARGE_ORDER_1ST_FAIL = 41;//log action 充值水單一審拒絕
	
	public static final int LOG_ACTION_AUDIT_RECHARGE_ORDER_2ND_SUCCESS = 42;//log action 充值水單二審通過
	
	public static final int LOG_ACTION_AUDIT_RECHARGE_ORDER_2ND_FAIL = 43;//log action 充值水單二審拒絕
	
	public static final int LOG_ACTION_AUDIT_ORDER_REVIEW = 11;//log action 後台_複審紀錄(上層)
	
	public static final int LOG_ACTION_AUDIT_WITHDRAWAL_ORDER_REVIEW_SUCCESS = 44;//log action 提款水單複審通過
	
	public static final int LOG_ACTION_AUDIT_WITHDRAWAL_ORDER_REVIEW_FAIL = 45;//log action 提款水單複審異常
	
	public static final int LOG_ACTION_AUDIT_RECHARGE_ORDER_REVIEW_SUCCESS = 46;//log action 充值水單複審通過
	
	public static final int LOG_ACTION_AUDIT_RECHARGE_ORDER_REVIEW_FAIL = 47;//log action 充值水單複審異常
	
	public static final int LOG_ACTION_AUTH_CHANGE = 12;//log action 後台_權限更動紀錄(上層)
	
	public static final int LOG_ACTION_ADD_AUTH = 48;//log action 新增權限
	
	public static final int LOG_ACTION_SET_AUTH = 49;//log action 修改權限
	
	public static final int LOG_ACTION_GAME_SERVER_CHANGE = 13;//log action 後台_GameServer更動紀錄(上層)
	
	public static final int LOG_ACTION_ADD_GAME_SERVER = 50;//log action 新增GameServer
	
	public static final int LOG_ACTION_SET_GAME_SERVER = 51;//log action 修改GameServer
	
	
	
	public static final int LOG_ACTION_MONEY = 18;//log action 現金紀錄_後台(上層)
	
	public static final int LOG_ACTION_ADD_ACC_DEPOSIT_ADD = 61;//log action 後台新增帳號存入增加
	
	public static final int LOG_ACTION_ADD_ACC_DEPOSIT_DEBIT = 62;//log action 後台新增帳號存入扣款
	
	public static final int LOG_ACTION_SET_ACC_DEPOSIT_ADD = 63;//log action 後台修改帳號存入增加
	
	public static final int LOG_ACTION_SET_ACC_DEPOSIT_DEBIT = 64;//log action 後台修改帳號存入扣款
	
	public static final int LOG_ACTION_SET_ACC_WITHDRAWAL_ADD = 65;//log action 後台修改帳號提出增加
	
	public static final int LOG_ACTION_SET_ACC_WITHDRAWAL_DEBIT = 66;//log action 後台修改帳號提出扣款
	
	public static final int LOG_ACTION_WITHDRAWAL_ORDER_AUDIT_FAIL_ADD = 67;//log action 後台提款水單失敗增加
	
	public static final int LOG_ACTION_RECHARGE_ORDER_AUDIT_SUCCESS_ADD = 68;//log action 後台充值水單成功增加
	
}
