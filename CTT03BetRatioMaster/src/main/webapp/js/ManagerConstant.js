// PATH
const JS_PATH = "/CTT02PF/js/";
const IMG_PATH = "/CTT02PF/img/";

//URL
const SET_AUTH_URL = "auth";
const SET_ORDER_URL = "order";
const SET_GAME_RECORDS_URL = "gameRecords";
const SET_AD_URL = "ad";
const SET_PUNCHES_GAME_ROOM_URL = "gamePunchesRoom";
const SET_GAME_PARAM_URL = "setGameParam";
const COMMING_SOON = "comming soon";

const SET_FINISH_RECHARGE_ORDER_URL = "finishRechargeOrder";
const SET_FINISH_WITHTHDRAWAL_ORDER_URL = "finishWithdrawalOrder";
const SET_AUDIT_RECHARGE_ORDER_URL = "auditRechargeOrder";
const SET_AUDIT_WITHDRAWAL_ORDER_URL = "auditWithdrawalOrder";
const SET_LAST_AUDIT_ORDER_URL = "lastAuditOrder";

const SET_LOTTERY_SWITCH_SET_URL = "lotterySwitchSet";

// 總監充值上限
const SC_DEPOSIT_MAX = 100000000;
// 總監可佔成數上限
const SC_RATIO_MAX = 100;

// 新增/設定 => 帳號 密碼 暱稱 輸入框文字長度限制
const COM_ACC_NAME_LENGTH_MAX = 20;
const COM_ACC_NAME_LENGTH_MIN = 6;
const MANAGER_ACC_NAME_LENGTH_MAX = 20;
const MANAGER_ACC_NAME_LENGTH_MIN = 6;
const MEMBER_ACC_NAME_LENGTH_MAX = 20;
const MEMBER_ACC_NAME_LENGTH_MIN = 6;
const ACC_NICK_LENGTH_MAX = 20;
const ACC_NICK_LENGTH_MIN = 4;
const ACC_PASSWORD_LENGTH_MAX = 20;
const ACC_PASSWORD_LENGTH_MIN = 6;

// 佔滿
const FULL_RATIO_ENABLED = 1;
const FULL_RATIO_DISABLED = 0;

// 直屬/一般會員
const DIRECTLY_UNDER_MEM = 0;
const GRNERAL_MEM = 1;

const DIRECTLY_UNDER_MEM_TEXT = "上層充值";
const GRNERAL_MEM_TEXT = "會員充值";

const ACC_LEVEL_ADMIN = 0, ACC_LEVEL_COM = 1, ACC_LEVEL_SC = 2, ACC_LEVEL_BC = 3, ACC_LEVEL_CO = 4, ACC_LEVEL_SA = 5, ACC_LEVEL_AG = 6, ACC_LEVEL_AG1 = 7, ACC_LEVEL_AG2 = 8, ACC_LEVEL_AG3 = 9, ACC_LEVEL_AG4 = 10, ACC_LEVEL_AG5 = 11, ACC_LEVEL_AG6 = 12, ACC_LEVEL_AG7 = 13, ACC_LEVEL_AG8 = 14, ACC_LEVEL_AG9 = 15, ACC_LEVEL_AG10 = 16, ACC_LEVEL_MEM = 100;

const ACC_LEVEL_ADMIN_NICKNAME = "admin";
const ACC_LEVEL_COM_NICKNAME = "公司";
const ACC_LEVEL_SC_NICKNAME = "總監";
const ACC_LEVEL_BC_NICKNAME = "大股東";
const ACC_LEVEL_CO_NICKNAME = "股東";
const ACC_LEVEL_SA_NICKNAME = "總代理";
const ACC_LEVEL_AG_NICKNAME = "代理";
const ACC_LEVEL_AG1_NICKNAME = "代理1";
const ACC_LEVEL_AG2_NICKNAME = "代理2";
const ACC_LEVEL_AG3_NICKNAME = "代理3";
const ACC_LEVEL_AG4_NICKNAME = "代理4";
const ACC_LEVEL_AG5_NICKNAME = "代理5";
const ACC_LEVEL_AG6_NICKNAME = "代理6";
const ACC_LEVEL_AG7_NICKNAME = "代理7";
const ACC_LEVEL_AG8_NICKNAME = "代理8";
const ACC_LEVEL_AG9_NICKNAME = "代理9";
const ACC_LEVEL_AG10_NICKNAME = "代理10";
const ACC_LEVEL_MEM_NICKNAME = "會員";

const ACC_LEVEL_NICKNAME_ARR = [ ACC_LEVEL_ADMIN_NICKNAME, ACC_LEVEL_COM_NICKNAME, ACC_LEVEL_SC_NICKNAME, ACC_LEVEL_BC_NICKNAME,
		ACC_LEVEL_CO_NICKNAME, ACC_LEVEL_SA_NICKNAME, ACC_LEVEL_AG_NICKNAME, ACC_LEVEL_AG1_NICKNAME, ACC_LEVEL_AG2_NICKNAME, ACC_LEVEL_AG3_NICKNAME,
		ACC_LEVEL_AG4_NICKNAME, ACC_LEVEL_AG5_NICKNAME, ACC_LEVEL_AG6_NICKNAME, ACC_LEVEL_AG7_NICKNAME, ACC_LEVEL_AG8_NICKNAME,
		ACC_LEVEL_AG9_NICKNAME, ACC_LEVEL_AG10_NICKNAME ];

const ACC_LEVEL_TYPE_ARR = [ ACC_LEVEL_ADMIN, ACC_LEVEL_COM, ACC_LEVEL_SC, ACC_LEVEL_BC, ACC_LEVEL_CO, ACC_LEVEL_SA, ACC_LEVEL_AG, ACC_LEVEL_AG1,
		ACC_LEVEL_AG2, ACC_LEVEL_AG3, ACC_LEVEL_AG4, ACC_LEVEL_AG5, ACC_LEVEL_AG6, ACC_LEVEL_AG7, ACC_LEVEL_AG8, ACC_LEVEL_AG9, ACC_LEVEL_AG10 ];

const MANAGER_LEVEL_TYPE_ARR = [ ACC_LEVEL_SC, ACC_LEVEL_BC, ACC_LEVEL_CO, ACC_LEVEL_SA, ACC_LEVEL_AG, ACC_LEVEL_AG1, ACC_LEVEL_AG2, ACC_LEVEL_AG3,
		ACC_LEVEL_AG4, ACC_LEVEL_AG5, ACC_LEVEL_AG6, ACC_LEVEL_AG7, ACC_LEVEL_AG8, ACC_LEVEL_AG9, ACC_LEVEL_AG10 ];

const LOTTERY = 1;
const LIVEVIDEO = 2;
const MOVEMENT = 3;
const VIDEOGAME = 4;
const GAME = 5;

const GAME_TYPE_ARR = [ LOTTERY, LIVEVIDEO, MOVEMENT, VIDEOGAME, GAME ];

const STATUS_TYPE_ENABLED = 1, STATUS_TYPE_NOLOGIN = 2, STATUS_TYPE_DISABLED = 3, STATUS_TYPE_DELETE = 4;
function isCOM(val) {
	return toInt(val) == ACC_LEVEL_COM;
}
function isSC(val) {
	return toInt(val) == ACC_LEVEL_SC;
}
function isBC(val) {
	return toInt(val) == ACC_LEVEL_BC;
}
function isCO(val) {
	return toInt(val) == ACC_LEVEL_CO;
}
function isSA(val) {
	return toInt(val) == ACC_LEVEL_SA;
}
function isAG(val) {
	return toInt(val) == ACC_LEVEL_AG;
}
function isAG1(val) {
	return toInt(val) == ACC_LEVEL_AG1;
}
function isAG2(val) {
	return toInt(val) == ACC_LEVEL_AG2;
}
function isAG3(val) {
	return toInt(val) == ACC_LEVEL_AG3;
}
function isAG4(val) {
	return toInt(val) == ACC_LEVEL_AG4;
}
function isAG5(val) {
	return toInt(val) == ACC_LEVEL_AG5;
}
function isAG6(val) {
	return toInt(val) == ACC_LEVEL_AG6;
}
function isAG7(val) {
	return toInt(val) == ACC_LEVEL_AG7;
}
function isAG8(val) {
	return toInt(val) == ACC_LEVEL_AG8;
}
function isAG9(val) {
	return toInt(val) == ACC_LEVEL_AG9;
}
function isAG10(val) {
	return toInt(val) == ACC_LEVEL_AG10;
}

function isMEM(val) {
	return toInt(val) == ACC_LEVEL_MEM;
}
function isNotMEM(val) {
	return toInt(val) != ACC_LEVEL_MEM;
}

function getLowerLevel(val) {
	switch (toInt(val)) {
	case ACC_LEVEL_ADMIN:
		return ACC_LEVEL_COM;
		break;
	case ACC_LEVEL_COM:
		return ACC_LEVEL_SC;
		break;
	case ACC_LEVEL_SC:
		return ACC_LEVEL_BC;
		break;
	case ACC_LEVEL_BC:
		return ACC_LEVEL_CO;
		break;
	case ACC_LEVEL_CO:
		return ACC_LEVEL_SA;
		break;
	case ACC_LEVEL_SA:
		return ACC_LEVEL_AG;
		break;
	case ACC_LEVEL_AG:
		return ACC_LEVEL_AG1;
		break;
	case ACC_LEVEL_AG1:
		return ACC_LEVEL_AG2;
		break;
	case ACC_LEVEL_AG2:
		return ACC_LEVEL_AG3;
		break;
	case ACC_LEVEL_AG3:
		return ACC_LEVEL_AG4;
		break;
	case ACC_LEVEL_AG4:
		return ACC_LEVEL_AG5;
		break;
	case ACC_LEVEL_AG5:
		return ACC_LEVEL_AG6;
		break;
	case ACC_LEVEL_AG6:
		return ACC_LEVEL_AG7;
		block;
	case ACC_LEVEL_AG7:
		return ACC_LEVEL_AG8;
		break;
	case ACC_LEVEL_AG8:
		return ACC_LEVEL_AG9;
		break;
	case ACC_LEVEL_AG9:
		return ACC_LEVEL_AG10;
		break;
	case ACC_LEVEL_AG10:
		return ACC_LEVEL_MEM;
		break;
	}
	return -1;
}

function getUpLevel(val) {
	switch (toInt(val)) {
	case ACC_LEVEL_COM:
		return ACC_LEVEL_ADMIN;
		break;
	case ACC_LEVEL_SC:
		return ACC_LEVEL_COM;
		break;
	case ACC_LEVEL_BC:
		return ACC_LEVEL_SC;
		break;
	case ACC_LEVEL_CO:
		return ACC_LEVEL_BC;
		break;
	case ACC_LEVEL_SA:
		return ACC_LEVEL_CO;
		break;
	case ACC_LEVEL_AG:
		return ACC_LEVEL_SA;
		break;
	case ACC_LEVEL_AG1:
		return ACC_LEVEL_AG;
		break;
	case ACC_LEVEL_AG2:
		return ACC_LEVEL_AG1;
		break;
	case ACC_LEVEL_AG3:
		return ACC_LEVEL_AG2;
		break;
	case ACC_LEVEL_AG4:
		return ACC_LEVEL_AG3;
		break;
	case ACC_LEVEL_AG5:
		return ACC_LEVEL_AG4;
		break;
	case ACC_LEVEL_AG6:
		return ACC_LEVEL_AG5;
		break;
	case ACC_LEVEL_AG7:
		return ACC_LEVEL_AG6;
		break;
	case ACC_LEVEL_AG8:
		return ACC_LEVEL_AG7;
		block;
	case ACC_LEVEL_AG9:
		return ACC_LEVEL_AG8;
		break;
	case ACC_LEVEL_AG10:
		return ACC_LEVEL_AG9;
		break;
	}
	return -1;
}

function getAccLevelNickname(val) {
	for (var i = 0; i < ACC_LEVEL_TYPE_ARR.length; i++) {
		if (toInt(val) == ACC_LEVEL_TYPE_ARR[i]) {
			return ACC_LEVEL_NICKNAME_ARR[i];
		}
	}
	if (isMEM(val)) {
		return ACC_LEVEL_MEM_NICKNAME;
	}
	return "";
}

function isLevelBcToAg(val) {
	return toInt(val) >= ACC_LEVEL_BC && val < ACC_LEVEL_MEM;
}
function isManager(val) {
	return toInt(val) >= ACC_LEVEL_SC && val < ACC_LEVEL_MEM
}
function isStatusEnabled(status) {
	return toInt(status) == STATUS_TYPE_ENABLED
}
function isStatusNoLogin(status) {
	return toInt(status) == STATUS_TYPE_NOLOGIN
}
function isStatusDisabled(status) {
	return toInt(status) == STATUS_TYPE_DISABLED
}
function isStatusDelete(status) {
	return toInt(status) == STATUS_TYPE_DELETE
}
// action
const LOG_ACTION_LOGIN_AND_LOGOUT = 1;// log action 後台_登入登出紀錄(上層)
const LOG_ACTION_LOGIN = 20;// log action 後台登入
const LOG_ACTION_LOGOUT = 21;// log action 後台登出
const LOG_ACTION_STATUS_CHANGE = 2;// log action 後台_狀態更動紀錄(上層)
const LOG_ACTION_ADD_ACC = 22;// log action 後台新增帳號
const LOG_ACTION_ENABLE_ACC = 23;// log action 啟用帳號
const LOG_ACTION_DISABLE_ACC = 24;// log action 停用帳號
const LOG_ACTION_DO_NOT_LOGIN_ACC = 25;// log action 禁止登入
const LOG_ACTION_DELETE_ACC = 26;// log action 刪除帳號
const LOG_ACTION_OPRATIONAL = 3;// log action 後台_一般紀錄(上層)
const LOG_ACTION_SET_PASSWORD = 27;// log action 修改密碼
const LOG_ACTION_SET_WITHDRAWAL_PASSWORD = 28;// log action 修改取款密碼
const LOG_ACTION_SET_NICKNAME = 29;// log action 修改暱稱
const LOG_ACTION_ADD_ACC_MONEY = 4;// log action 後台_現金紀錄(上層)
const LOG_ACTION_DEPOSIT_ADD = 30;// log action 修改帳號存入增加
const LOG_ACTION_DEPOSIT_DEBIT = 31;// log action 修改帳號存入扣款
const LOG_ACTION_WITHDRAWAL_ADD = 32;// log action 修改帳號提出增加
const LOG_ACTION_WITHDRAWAL_DEBET = 33;// log action 修改帳號提出扣款
const LOG_ACTION_RATIO_CHANGE = 5;// log action 後台_佔成修改紀錄(上層)
const LOG_ACTION_SET_RATIO = 34;// log action 佔成修改
const LOG_ACTION_FULL_RATIO_CHANGE = 6;// log action 後台_佔成方式選擇紀錄(上層)
const LOG_ACTION_SET_FULL_RATIO = 35;// log action 佔成方式選擇
const LOG_ACTION_DETAIL_CHANGE = 7;// log action 後台_詳細資料更動紀錄(上層)
const LOG_ACTION_SET_DETAIL = 36;// log action 詳細資料更動
const LOG_ACTION_ADD_RECHARGE_ORDER_CHANGE = 8;// log action 後台_補單紀錄(上層)
const LOG_ACTION_ADD_RECHARGE_ORDER = 37;// log action 補單
const LOG_ACTION_AUDIT_WITHDRAWAL_ORDER = 9;// log action 後台_提款水單審核紀錄(上層)
const LOG_ACTION_AUDIT_WITHDRAWAL_ORDER_SUCCESS = 38;// log action 提款水單通過
const LOG_ACTION_AUDIT_WITHDRAWAL_ORDER_FAIL = 39;// log action 提款水單拒絕
const LOG_ACTION_AUDIT_RECHARGE_ORDER = 10;// log action 後台_充值水單審核紀錄(上層)
const LOG_ACTION_AUDIT_RECHARGE_ORDER_1ST_SUCCESS = 40;// log action 充值水單一審通過
const LOG_ACTION_AUDIT_RECHARGE_ORDER_1ST_FAIL = 41;// log action 充值水單一審拒絕
const LOG_ACTION_AUDIT_RECHARGE_ORDER_2ND_SUCCESS = 42;// log action 充值水單二審通過
const LOG_ACTION_AUDIT_RECHARGE_ORDER_2ND_FAIL = 43;// log action 充值水單二審拒絕
const LOG_ACTION_AUDIT_ORDER_REVIEW = 11;// log action 後台_複審紀錄(上層)
const LOG_ACTION_AUDIT_WITHDRAWAL_ORDER_REVIEW_SUCCESS = 44;// log action
// 提款水單複審通過
const LOG_ACTION_AUDIT_WITHDRAWAL_ORDER_REVIEW_FAIL = 45;// log action
// 提款水單複審異常
const LOG_ACTION_AUDIT_RECHARGE_ORDER_REVIEW_SUCCESS = 46;// log action
// 充值水單複審通過
const LOG_ACTION_AUDIT_RECHARGE_ORDER_REVIEW_FAIL = 47;// log action 充值水單複審異常
const LOG_ACTION_AUTH_CHANGE = 12;// log action 後台_權限更動紀錄(上層)
const LOG_ACTION_ADD_AUTH = 48;// log action 新增權限
const LOG_ACTION_SET_AUTH = 49;// log action 修改權限
const LOG_ACTION_GAME_SERVER_CHANGE = 13;// log action 後台_GameServer更動紀錄(上層)
const LOG_ACTION_ADD_GAME_SERVER = 50;// log action 新增GameServer
const LOG_ACTION_SET_GAME_SERVER = 51;// log action 修改GameServer
// //前台
// const LOG_ACTION_LOGIN_AND_LOGOUT = 14;//log action 前台_登入登出紀錄(上層)
// const LOG_ACTION_LOGIN = 52;//log action 前台登入
// const LOG_ACTION_LOGOUT = 53;//log action 前台登出
// const LOG_ACTION_ACC_CHANGE = 15;//log action 前台_狀態更動紀錄(上層)
// const LOG_ACTION_ADD_ACC = 54;//log action 前台新增帳號
const LOG_ACTION_ADD_ORDER = 16;// log action 前台_建立水單紀錄(上層)
const LOG_ACTION_ADD_WITHDRAWAL_ORDER = 55;// log action 前台建立提款水單
// const LOG_ACTION_ADD_RECHARGE_ORDER = 56;//log action 前台建立充值水單
// const LOG_ACTION_DETAIL_CHANGE = 17;//log action 前台_詳細資料更動紀錄(上層)
// const LOG_ACTION_SET_MEM_ACC = 57;//log action 前台修改會員資料
// const LOG_ACTION_SET_PASSWORD = 58;//log action 前台修改密碼
// const LOG_ACTION_SET_WITHDRAWAL_PASSWORD = 59;//log action 前台修改取款密碼
// const LOG_ACTION_ADD_BANK_CARD = 60;//log action 前台新增銀行卡

const LOG_ACTION_MONEY = 18;// log action 現金紀錄_後台(上層)
const LOG_ACTION_ADD_ACC_DEPOSIT_ADD = 61;// log action 後台新增帳號存入增加
const LOG_ACTION_ADD_ACC_DEPOSIT_DEBIT = 62;// log action 後台新增帳號存入扣款
const LOG_ACTION_SET_ACC_DEPOSIT_ADD = 63;// log action 後台修改帳號存入增加
const LOG_ACTION_SET_ACC_DEPOSIT_DEBIT = 64;// log action 後台修改帳號存入扣款
const LOG_ACTION_SET_ACC_WITHDRAWAL_ADD = 65;// log action 後台修改帳號提出增加
const LOG_ACTION_SET_ACC_WITHDRAWAL_DEBIT = 66;// log action 後台修改帳號提出扣款
const LOG_ACTION_WITHDRAWAL_ORDER_AUDIT_FAIL_ADD = 67;// log action 後台提款水單失敗增加
const LOG_ACTION_RECHARGE_ORDER_AUDIT_SUCCESS_ADD = 68;// log action 後台充值水單成功增加
// //前台
// const LOG_ACTION_MONEY = 19;//log action 現金紀錄_前台(上層)
// const LOG_ACTION_RECHARGE_ORDER_ADD_SUCCESS_DEBIT = 69;//log action
// 前台建立提款水單扣款
const ADD_GAME_SERVER_JSON = {
	"sid" : "gameServer編號:",
	"gameServerId" : "gameServer名稱:",
	"bet" : "入場金額:",
	"maxPlayer" : "最大遊戲人數:",
	"gameTimesType" : "局數設定:",
	"commission" : "抽成比例%:",
	"thread" : "Thread:",
	"beatsTimeOut" : "玩家心跳:",
	"gameTimeOut" : "遊戲時間:",
	"autoTimeStamp" : "電腦託管時間:",
	"waitContinueTimeOut" : "繼續遊戲時間:",
	"continueTimesType" : "同對手能繼續場次:",
	"serviceTime" : "維修時間:",
	"serverStatus" : "開關:"
};
const SET_GAME_SERVER_JSON = {
	"gameServerId" : "gameServerId:",
	"nextBet" : "入場金額:",
	"nextMaxPlayer" : "最大遊戲人數:",
	"nextGameTimesType" : "局數設定:",
	"nextCommission" : "抽成比例%:",
	"nextThread" : "Thread:",
	"nextBeatsTimeOut" : "玩家心跳:",
	"nextGameTimeOut" : "遊戲時間:",
	"nextAutoTimeStamp" : "電腦託管時間:",
	"nextWaitContinueTimeOut" : "繼續遊戲時間:",
	"nextContinueTimesType" : "同對手能繼續場次:",
	"nextServiceTime" : "維修時間:",
	"nextServerStatus" : "開關:"
};
const ORDER_KEYS_LOG_JSON = {
	"orderId" : "水單單號",
	"accName" : "會員帳號",
	"orderType" : "水單類型",
	"amount" : "金額",
	"bankAccName" : "帳戶名稱",
	"bankAcc" : "卡號",
	"bank" : "銀行",
	"postscript" : "附言",
	"bankSid" : "銀行單號",
	"bankCheckAmount" : "匯款金額",
	"bankDepositDatetime" : "匯款時間",
	"remark" : "備註",
	"currency" : "幣別",
	"status" : "水單狀態",
	"backOrderStatus" : "充值水單類型",// 補單、會員建單
	"rechargeType" : "帳單類型",// 網銀、API
	"firstAuditor" : "一審人員",
	"firstDatetime" : "一審時間",
	"secondAuditor" : "二審人員",
	"secondDatetime" : "二審時間",
	"lastAuditor" : "複審人員",
	"lastDatetime" : "複審時間"
};
const ORDER_VALUE_LOG_JSON = {
	"orderId" : "水單單號:",
	"accName" : "會員帳號:",
	"orderType" : {
		"0" : "充值",
		"1" : "提款"
	},
	"amount" : "金額:",
	"bankAccName" : "帳戶名稱:",
	"bankAcc" : "卡號:",
	"bank" : "銀行:",
	"postscript" : "附言:",
	"bankSid" : "銀行單號:",
	"bankCheckAmount" : "匯款金額:",
	"bankDepositDatetime" : "匯款時間:",
	"remark" : "備註:",
	"currency" : {
		"0" : "人民幣",
		"1" : "美金"
	},
	"status" : {
		"1" : "未審核",
		"2" : "初審審核中",
		"3" : "待二審",
		"4" : "二審審核中",
		"5" : "待複查",
		"6" : " 複查中",
		"7" : "完成",
		"8" : "拒絕",
		"9" : "異常",
		"10" : "API完成"
	},
	"backOrderStatus" : {
		"0" : "會員提交",
		"1" : "補單"
	},
	"rechargeType" : {
		"1" : "網銀",
		"2" : "API"
	},
	"firstAuditor" : "一審人員:",
	"firstDatetime" : "一審時間:",
	"secondAuditor" : "二審人員:",
	"secondDatetime" : "二審時間:",
	"lastAuditor" : "複審人員:",
	"lastDatetime" : "複審時間:"
};
const ORDER_TABLE_NAME_LOG_JSON = {
	"8" : "充值補單建立紀錄列表",
	"16" : "水單建立紀錄列表",
	"9" : "提款水單審核紀錄列表",
	"10" : "充值水單審核紀錄列表",
	"11" : "複審紀錄列表"
}
const ORDER_TABLE_TITLE_LOG_JSON = {
	"8" : "'編號','水單編號','金額','會員帳號','補單人員帳號','日期','IP','內容'",
	"16" : "'編號','水單編號','金額','會員帳號','日期','IP','內容'",
	"9" : "'編號','水單編號','金額','會員帳號','審核人員帳號','水單狀態','日期','IP','內容'",
	"10" : "'編號','水單編號','金額','會員帳號','審核人員帳號','水單狀態','日期','IP','內容'",
	"11" : "'編號','水單編號','水單類型','金額','會員帳號','審核人員帳號','日期','IP','內容'"
}
const ACTION_JSON = {
	1 : "後台_登入登出紀錄",
	20 : '後台登入',
	21 : '後台登出',
	2 : '後台_狀態更動紀錄',
	22 : '後台新增帳號',
	23 : '啟用帳號',
	24 : '禁止登入',
	25 : '停用',
	26 : '刪除帳號',
	3 : '後台_一般紀錄',
	27 : '修改密碼',
	28 : '修改取款密碼',
	29 : '修改暱稱',
	4 : '後台_現金紀錄',
	30 : '修改帳號存入增加',
	31 : '修改帳號存入扣款',
	32 : '修改帳號提出增加',
	33 : '修改帳號提出扣款',
	5 : '後台_佔成修改紀錄',
	34 : '佔成修改',
	6 : '後台_佔成方式選擇紀錄',
	35 : '佔成方式選擇',
	7 : '後台_詳細資料更動紀錄',
	36 : '詳細資料更動',
	8 : '後台_補單紀錄',
	37 : '補單',
	9 : '後台_提款水單審核紀錄',
	38 : '提款水單通過',
	39 : '提款水單拒絕',
	10 : '後台_充值水單審核紀錄',
	40 : '充值水單一審通過',
	41 : '充值水單一審拒絕',
	42 : '充值水單二審通過',
	43 : '充值水單二審拒絕',
	11 : '後台_複審紀錄',
	44 : '提款水單複審通過',
	45 : '提款水單複審異常',
	46 : '充值水單複審通過',
	47 : '充值水單複審異常',
	12 : '後台_權限更動紀錄',
	48 : '新增權限',
	49 : '修改權限',
	13 : '後台_GameServer更動紀錄',
	50 : '新增',
	51 : '修改',

	14 : '前台_登入登出紀錄',
	52 : '前台登入',
	53 : '前台登出',
	15 : '前台_狀態更動紀錄',
	54 : '前台新增帳號',
	16 : '前台_建立水單紀錄',
	55 : '前台建立提款水單',
	56 : '前台建立充值水單',
	17 : '前台_詳細資料更動紀錄',
	57 : '前台修改會員資料',
	58 : '前台修改密碼',
	59 : '前台修改取款密碼',
	60 : '前台新增銀行卡',

	18 : '現金紀錄_後台',
	61 : '後台新增帳號存入增加',
	62 : '後台新增帳號存入扣款',
	63 : '後台修改帳號存入增加',
	64 : '後台修改帳號存入扣款',
	65 : '後台修改帳號提出增加',
	66 : '後台修改帳號提出扣款',
	67 : '後台提款水單失敗增加',
	68 : '後台充值水單成功增加',
	19 : '現金紀錄_前台',
	69 : '前台建立提款水單扣款'
};

const RATIO_NAME_JSON = {
	"g1MaxRatioNext" : "彩票最大佔成:",
	"g2MaxRatioNext" : "真人視訊最大佔成:",
	"g3MaxRatioNext" : "運動球類最大佔成:",
	"g4MaxRatioNext" : "電動最大佔成:",
	"g5MaxRatioNext" : "遊戲最大佔成:",

	"g1MinRatioNext" : "彩票最小佔成:",
	"g2MinRatioNext" : "真人視訊最小佔成:",
	"g3MinRatioNext" : "運動球類最小佔成:",
	"g4MinRatioNext" : "電動最小佔成:",
	"g5MinRatioNext" : "遊戲最小佔成:",

	"g1CurrentMaxRatioNext" : "彩票可佔成:",
	"g2CurrentMaxRatioNext" : "真人視訊可佔成:",
	"g3CurrentMaxRatioNext" : "運動球類可佔成:",
	"g4CurrentMaxRatioNext" : "電動可佔成:",
	"g5CurrentMaxRatioNext" : "遊戲可佔成:"
};


const LEVEL_NICKNAME = {
		"0":"Admin",
		"1":"公司",
		"2":"總監",
		"3":"大股東",
		"4":"股東",
		"5":"總代理",
		"6":"代理",
		"7":"代理1",
		"8":"代理2",
		"9":"代理3",
		"10":"代理4",
		"11":"代理5",
		"12":"代理6",
		"13":"代理7",
		"14":"代理8",
		"15":"代理9",
		"16":"代理10",
		"100":"會員",
};
const STATUS_OP_FAIL = 0;
const STATUS_ENABLED = 1;
const STATUS_NOLOGIN = 2;
const STATUS_DISABLED = 3;
const STATUS_DELETE = 4;

const STATUS_ENABLED_UPDATE_FAIL = 10;
const STATUS_NOLOGIN_UPDATE_FAIL = 20;
const STATUS_DISABLED_UPDATE_FAIL = 30;
const STATUS_DELETE_UPDATE_FAIL = 40;

