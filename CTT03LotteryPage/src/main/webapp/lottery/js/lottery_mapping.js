function js_lottery_mapping() {
	return true;
}
var groupMaxNoOfBetTimes = 9999;
var groupMainOrderObj;
var groupMainOrderListObj;
var ratiosJson;
var ratiosLFJson;
var kjTimeJson;
var subOrderInfos = '', orderInfo = '', subOrderInfo = '', subOrderInfoNoOfBet = '';

// ----------------------------------ajax---------------------------------------

var v_bet_xhr_reload = 0;
var v_bet_xhr_used = 0;
var v_bet_xhr_time = 0;
function sendBettingAjax(data) {
	// console.log(new Date().getTime());
	if (typeof data === "undefined") {
		return;
	}
	console_Log("new sendBettingAjax()!!!");
	if (v_bet_xhr_used != 0) {
		setTimeout("sendBettingAjax(" + data + ")", 1000);
		return false;
	}
	v_bet_xhr_reload++;
	if (v_bet_xhr_reload >= 5) {
		if ((new Date().getTime() - v_bet_xhr_time) >= 15000) {
			v_bet_xhr_reload = 1;
		} else {
			console_Log("Too many error connetions of sendBettingAjax()!");
			v_bet_xhr_used = 0;
			setTimeout("sendBettingAjax(" + data + ")", 15000);
			return false;
		}
	}
	enableLoadingPage();
	v_bet_xhr_time = new Date().getTime();
	v_bet_xhr_used = 1;
	var v_bet_xhr = null;
	v_bet_xhr = checkXHR(null);
	var tokenId = document.getElementsByName("tokenId")[0].value;
	var accId = document.getElementsByName("accId")[0].value;
	if (typeof v_bet_xhr != "undefined" && v_bet_xhr != null) {
		if (v_bet_xhr.timeout) {
			v_bet_xhr.timeout = 60000;
		}
		v_bet_xhr.ontimeout = function() {
			if (v_bet_xhr != null) {
				v_bet_xhr.abort();
				v_bet_xhr = null;
			}
			delete v_bet_xhr;
			v_bet_xhr = undefined;
			v_bet_xhr_used = 0;
			showRemindBetDiv("投注失敗");
			disableLoadingPage();
			return false;
		};
		v_bet_xhr.onerror = function() {
			if (v_bet_xhr != null) {
				v_bet_xhr.abort();
				v_bet_xhr = null;
			}
			delete v_bet_xhr;
			v_bet_xhr = undefined;
			showRemindBetDiv("投注失敗");
			v_bet_xhr_used = 0;
			disableLoadingPage();
			return false;
		};
		var tmpStr = "data=" + encodeURIComponent(data) + "&tokenId=" + encodeURIComponent(tokenId) + "&accId=" + encodeURIComponent("" + accId);
		var tmpURL = "/CTT03BetOrder/QuickBet!bettingOrder.php?date=" + new Date().getTime();
		if (typeof v_bet_xhr.onreadystatechange != "undefined" && typeof v_bet_xhr.readyState === "number") {
			v_bet_xhr.onreadystatechange = function() {

				if (v_bet_xhr.readyState == 4) {
					if ((v_bet_xhr.status >= 200 && v_bet_xhr.status < 300) || v_bet_xhr.status == 304) {
						console_Log(v_bet_xhr.responseText);
						try {
							if (!v_bet_xhr.responseText || v_bet_xhr.responseText == null || v_bet_xhr.responseText == "") {
								v_bet_xhr_used = 0;

								getEle("BetNowDiv").getElementsByTagName("h4")[0].innerHTML = "投注成功";
								if (!isNull(json.type)) {
									openBetNow();
									if (parseInt(json.type) == 1) {
										openMidBetNow();
									} else if (parseInt(json.type) == 2) {
										openMainBetNow();
									}
								}
								clearMianOrder();

								disableLoadingPage();
								return false;
							}
							if (isJSON(v_bet_xhr.responseText)) {
								var json = JSON.parse(v_bet_xhr.responseText);
								console_Log(json);
								if (Object.keys(json).length > 0) {
									for ( var key in json) {
										if (json[key].indexOf("fail") >= 0) {
											betDataFail(key);
											disableLoadingPage();
											return false;
										}
									}
									getEle("BetNowDiv").getElementsByTagName("h4")[0].innerHTML = "投注成功";
									if (!isNull(json.type)) {
										openBetNow();
										if (parseInt(json.type) == 1) {
											openMidBetNow();
										} else if (parseInt(json.type) == 2) {
											openMainBetNow();
										}
									}
									clearMianOrder();
									disableLoadingPage();
									v_bet_xhr_reload = 0;
									return true;
								}
								getEle("BetNowDiv").getElementsByTagName("h4")[0].innerHTML = "投注成功";
								if (!isNull(json.type)) {
									openBetNow();
									if (parseInt(json.type) == 1) {
										openMidBetNow();
									} else if (parseInt(json.type) == 2) {
										openMainBetNow();
									}
								}
								clearMianOrder();
								return false;
							} else {
								v_bet_xhr_used = 0;
								getEle("BetNowDiv").getElementsByTagName("h4")[0].innerHTML = "投注成功";
								if (!isNull(json.type)) {
									openBetNow();
									if (parseInt(json.type) == 1) {
										openMidBetNow();
									} else if (parseInt(json.type) == 2) {
										openMainBetNow();
									}
								}
								clearMianOrder();
								disableLoadingPage();
								return false;
							}
						} catch (error) {
							console_Log("sendBettingAjax error Message:" + error.message);
							console_Log("sendBettingAjax error Code:" + (error.number & 0xFFFF));
							console_Log("sendBettingAjax facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("sendBettingAjax error Name:" + error.name);
							v_bet_xhr_used = 0;
							disableLoadingPage();
							return false;
						} finally {
							if (v_bet_xhr != null) {
								v_bet_xhr.abort();
								v_bet_xhr = null;
							}
							delete v_bet_xhr;
							v_bet_xhr = undefined;
							v_bet_xhr_used = 0;
						}
						disableLoadingPage();
						return false;
					}
				}
			};
		} else {
			v_bet_xhr.onload = function() {
				if (typeof v_bet_xhr.readyState != "number") {
					if (!v_bet_xhr.responseText || v_bet_xhr.responseText == null || v_bet_xhr.responseText == "") {
						if (v_bet_xhr != null) {
							v_bet_xhr.abort();
							v_bet_xhr = null;
						}
						delete v_bet_xhr;
						v_bet_xhr = undefined;
						v_bet_xhr_used = 0;
						getEle("BetNowDiv").getElementsByTagName("h4")[0].innerHTML = "投注成功";
						if (!isNull(json.type)) {
							openBetNow();
							if (parseInt(json.type) == 1) {
								openMidBetNow();
							} else if (parseInt(json.type) == 2) {
								openMainBetNow();
							}
						}
						clearMianOrder();
						disableLoadingPage();
						return false;
					} else {
						try {
							if (!v_bet_xhr.responseText || v_bet_xhr.responseText == null || v_bet_xhr.responseText == "") {
								v_bet_xhr_used = 0;
								disableLoadingPage();
								return false;
							}
							if (isJSON(v_bet_xhr.responseText)) {
								var json = JSON.parse(v_bet_xhr.responseText);
								console_Log(json);
								if (Object.keys(json).length > 0) {
									for ( var key in json) {
										if (json[key].indexOf("fail") >= 0) {
											betDataFail(key);
											disableLoadingPage();
											return false;
										}
									}
									getEle("BetNowDiv").getElementsByTagName("h4")[0].innerHTML = "投注成功";
									if (!isNull(json.type)) {
										openBetNow();
										if (parseInt(json.type) == 1) {
											openMidBetNow();
										} else if (parseInt(json.type) == 2) {
											openMainBetNow();
										}
									}
									clearMianOrder();
									disableLoadingPage();
									v_bet_xhr_reload = 0;
									return true;
								}
								// showRemindBetDiv("投注失敗");

								getEle("BetNowDiv").getElementsByTagName("h4")[0].innerHTML = "投注成功";
								if (!isNull(json.type)) {
									openBetNow();
									if (parseInt(json.type) == 1) {
										openMidBetNow();
									} else if (parseInt(json.type) == 2) {
										openMainBetNow();
									}
								}
								clearMianOrder();
								return false;
							} else {
								v_bet_xhr_used = 0;
								getEle("BetNowDiv").getElementsByTagName("h4")[0].innerHTML = "投注成功";
								if (!isNull(json.type)) {
									openBetNow();
									if (parseInt(json.type) == 1) {
										openMidBetNow();
									} else if (parseInt(json.type) == 2) {
										openMainBetNow();
									}
								}
								clearMianOrder();
								disableLoadingPage();
								return false;
							}
						} catch (error) {
							console_Log("sendBettingAjax error Message:" + error.message);
							console_Log("sendBettingAjax error Code:" + (error.number & 0xFFFF));
							console_Log("sendBettingAjax facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("sendBettingAjax error Name:" + error.name);
							v_bet_xhr_used = 0;
							disableLoadingPage();
							return false;
						} finally {
							if (v_bet_xhr != null) {
								v_bet_xhr.abort();
								v_bet_xhr = null;
							}
							delete v_bet_xhr;
							v_bet_xhr = undefined;
							v_bet_xhr_used = 0;
						}
						disableLoadingPage();
						return false;
					}
				} else if (v_bet_xhr.readyState == 4) {
					if ((v_bet_xhr.status >= 200 && v_bet_xhr.status < 300) || v_bet_xhr.status == 304) {
						try {
							if (!v_bet_xhr.responseText || v_bet_xhr.responseText == null || v_bet_xhr.responseText == "") {
								v_bet_xhr_used = 0;
								getEle("BetNowDiv").getElementsByTagName("h4")[0].innerHTML = "投注成功";
								if (!isNull(json.type)) {
									openBetNow();
									if (parseInt(json.type) == 1) {
										openMidBetNow();
									} else if (parseInt(json.type) == 2) {
										openMainBetNow();
									}
								}
								clearMianOrder();
								disableLoadingPage();
								return false;
							}
							if (isJSON(v_bet_xhr.responseText)) {
								var json = JSON.parse(v_bet_xhr.responseText);
								console_Log(json);
								if (Object.keys(json).length > 0) {
									for ( var key in json) {
										if (json[key].indexOf("fail") >= 0) {
											betDataFail(key);
											disableLoadingPage();
											return false;
										}
									}
									getEle("BetNowDiv").getElementsByTagName("h4")[0].innerHTML = "投注成功";
									if (!isNull(json.type)) {
										openBetNow();
										if (parseInt(json.type) == 1) {
											openMidBetNow();
										} else if (parseInt(json.type) == 2) {
											openMainBetNow();
										}
									}
									clearMianOrder();
									disableLoadingPage();
									v_bet_xhr_reload = 0;
									return true;
								}
								getEle("BetNowDiv").getElementsByTagName("h4")[0].innerHTML = "投注成功";
								if (!isNull(json.type)) {
									openBetNow();
									if (parseInt(json.type) == 1) {
										openMidBetNow();
									} else if (parseInt(json.type) == 2) {
										openMainBetNow();
									}
								}
								clearMianOrder();
								return false;
							} else {
								v_bet_xhr_used = 0;
								getEle("BetNowDiv").getElementsByTagName("h4")[0].innerHTML = "投注成功";
								if (!isNull(json.type)) {
									openBetNow();
									if (parseInt(json.type) == 1) {
										openMidBetNow();
									} else if (parseInt(json.type) == 2) {
										openMainBetNow();
									}
								}
								clearMianOrder();
								disableLoadingPage();
								return false;
							}
						} catch (error) {
							console_Log("sendBettingAjax error Message:" + error.message);
							console_Log("sendBettingAjax error Code:" + (error.number & 0xFFFF));
							console_Log("sendBettingAjax facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("sendBettingAjax error Name:" + error.name);
							v_bet_xhr_used = 0;
							disableLoadingPage();
							return false;
						} finally {
							if (v_bet_xhr != null) {
								v_bet_xhr.abort();
								v_bet_xhr = null;
							}
							delete v_bet_xhr;
							v_bet_xhr = undefined;
							v_bet_xhr_used = 0;
						}
						disableLoadingPage();
						return false;
					}
				}
			};
		}
		v_bet_xhr.open("POST", tmpURL, true);
		if (v_bet_xhr.setRequestHeader) {
			v_bet_xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		}
		v_bet_xhr.send(tmpStr.toString());
		delete tmpURL;
		delete tmpStr;
		tmpURL = undefined;
		tmpStr = undefined;
	}
}

function betDataFail(key){
	if(key == "tokenId"){
		break;
	}
	else if(key == "accId"){
		break;
	}
	else if(key == "data"){
		break;	
	}
	else if(key == "tokenId"){
		break;
	}
	else if(key == "periodNum"){
		f_kj_timer(1);
		break;
	}
	else if(key == "playedSwitch"){
		setTimeout("f_lottory_queryAllInfo()", 1);
		break;
	}
	else if(key == "balence"){
		break;	
	}
	else if(key == "realTimeInfo"){
		f_ratio_timer(1);
		break;
	}
	else if(key == "realLFTimeInfo"){
		f_ratio_lf_timer(1);
		break;
	}
	else if(key == "addSubOrder"){
		break;
	}
	else if(key == "orderStruct"){
		break;
	}
	showRemindBetDiv("投注失敗");
	
}

// -------------------------------------------------------------------------
var v_mapping_wan = 'ball-ten-thousand';
var v_mapping_qian = "ball-thousand";
var v_mapping_bai = "ball-hundred";
var v_mapping_shi = "ball-ten";
var v_mapping_ge = "ball-one";

var v_mapping_5xz120 = 'ball-star5-group120';
var v_mapping_ssc5xzh = 'ball-star5-other-total-bs';
var v_mapping_ssc5xbxdszh = 'ball-star5-other-total-bsgroup';
var v_mapping_sscr4z24 = 'ball-star-10playballs';
var v_mapping_sscr3z3 = 'ball-star-group-03';
var v_mapping_sscr3z6 = 'ball-star-group-06';

var v_mapping_sscr3hz = 'ball-star-dt';
var v_mapping_sscr3kd = 'ball-star-dc';
var v_mapping_sscr3bd = 'ball-star-10balls';
// var v_mapping_bz = 'ball-star-bz'; // 豹子
// var v_mapping_sz = 'ball-star-sz'; // 順子
// var v_mapping_dz = 'ball-star-dz'; // 對子
// var v_mapping_bs = 'ball-star-bs'; // 半順
// var v_mapping_zl = 'ball-star-zl'; // 雜六
var v_mapping_bz = 'ball-jaguar'; // 豹子
var v_mapping_sz = 'ball-smooth' // 順子
var v_mapping_dz = 'ball-pair'; // 對子
var v_mapping_bs = 'ball-half'; // 半順
var v_mapping_zl = 'ball-mix6'; // 雜六
var v_mapping_sscr2hz = 'ball-star-cr2hz';
var v_mapping_sscr2zhz = 'ball-star-cr2zhz'; // 二星組選和值

var v_mapping_sscr2hzh = 'ball-star-19balls';

var v_mapping_sscr3bdw1m = 'ball-noassign';

var v_mapping_quadruple = 'ball-star-group-quadruple';
var v_mapping_single = 'ball-star-group-single';

var v_mapping_gt = 'ball-star-gt';
var v_mapping_notitle = 'ball-notitle';

var v_mapping_group = 'ball-star-group';
var v_mapping_17balls = 'ball-star-17balls';

var v_mapping_bs_ten_thousand = 'ball-star-bs-ten-thousand';
var v_mapping_bs_thousand = 'ball-star-bs-thousand'

var v_mapping_bs_hundred = 'ball-star-bs-hundred';
var v_mapping_bs_one = 'ball-star-bs-one';
var v_mapping_bs_ten = 'ball-star-bs-ten';

var v_mapping_fun_ten_thousand = 'ball-fun-ten-thousand';
var v_mapping_fun_thousand = 'ball-fun-thousand';

var v_mapping_fun_hundred = 'ball-fun-hundred';

var v_mapping_ten_thousand = 'ball-section-ten-thousand';
var v_mapping_thousand = 'ball-section-thousand';
var v_mapping_hundred = 'ball-section-hundred';

var v_mapping_10playballs = 'ball-star-10playballs';

var v_mapping_tenthousand1000 = 'ball-dtt-tenthousand1000';
var v_mapping_tenthousand100 = 'ball-dtt-tenthousand100';
var v_mapping_tenthousand10 = 'ball-dtt-tenthousand10';
var v_mapping_tenthousand01 = 'ball-dtt-tenthousand01';

var v_mapping_thousand10 = 'ball-dtt-thousand10';
var v_mapping_thousand01 = 'ball-dtt-thousand01';
var v_mapping_thousand100 = 'ball-dtt-thousand100';

var v_mapping_hundred10 = 'ball-dtt-hundred10';
var v_mapping_hundred01 = 'ball-dtt-hundred01';
var v_mapping_ten01 = 'ball-dtt-ten01';

var v_mapping_checkbox = 'ball-checkbox';

var v_mapping_textarea = 'textarea';

var v_mapping_import_area = 'import-area';

var v_mapping_group_total = 'ball-star-group-total';

var ballMapping = {};
ballMapping[v_mapping_wan] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];
ballMapping[v_mapping_qian] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];
ballMapping[v_mapping_bai] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];
ballMapping[v_mapping_shi] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];
ballMapping[v_mapping_ge] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];

// 五星組選
ballMapping[v_mapping_5xz120] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];
ballMapping['ball-star-group-triple'] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];
ballMapping['ball-star-group-double'] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];
ballMapping['ball-star-group-single'] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];
ballMapping[v_mapping_ssc5xzh] = [ 1, 2, 3, 4 ];
ballMapping[v_mapping_ssc5xbxdszh] = [ 1, 2, 3, 4 ];

// ballMapping[v_mapping_ssc5xzh] = [0, 1, 2, 3];
// ballMapping[v_mapping_ssc5xbxdszh] = [0, 1, 2, 3];

ballMapping[v_mapping_quadruple] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];
ballMapping[v_mapping_single] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];

// 四星
ballMapping[v_mapping_sscr4z24] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];

// 三星
ballMapping[v_mapping_sscr3z3] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];
ballMapping[v_mapping_sscr3z6] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];
ballMapping[v_mapping_sscr3hz] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27 ]; // 直選和值
ballMapping[v_mapping_sscr3kd] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]; // 直選跨度
ballMapping[v_mapping_sscr3bd] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];

ballMapping[v_mapping_bz] = [ 1 ];
ballMapping[v_mapping_sz] = [ 2 ];
ballMapping[v_mapping_dz] = [ 3 ];
ballMapping[v_mapping_bs] = [ 4 ];
ballMapping[v_mapping_zl] = [ 5 ];

// ballMapping[v_mapping_bz] = [0];
// ballMapping[v_mapping_sz] = [0];
// ballMapping[v_mapping_dz] = [0];
// ballMapping[v_mapping_bs] = [0];
// ballMapping[v_mapping_zl] = [0];

ballMapping[v_mapping_sscr2hz] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 ]; // 直選和值
ballMapping[v_mapping_sscr2zhz] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 ]; // 組選和值

ballMapping[v_mapping_sscr2hzh] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 ]; // 直選和值

ballMapping[v_mapping_gt] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26 ];
ballMapping[v_mapping_group_total] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26 ];

ballMapping[v_mapping_notitle] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];

// 二星組選和值

ballMapping[v_mapping_group] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];

ballMapping[v_mapping_17balls] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 ];

// 不定位

ballMapping[v_mapping_sscr3bdw1m] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];

// 大小單雙

ballMapping[v_mapping_bs_ten_thousand] = [ 1, 2, 3, 4 ];
ballMapping[v_mapping_bs_thousand] = [ 1, 2, 3, 4 ];

ballMapping[v_mapping_bs_hundred] = [ 1, 2, 3, 4 ];
ballMapping[v_mapping_bs_one] = [ 1, 2, 3, 4 ];
ballMapping[v_mapping_bs_ten] = [ 1, 2, 3, 4 ];

// ballMapping[v_mapping_bs_ten_thousand] = [0,1,2,3];
// ballMapping[v_mapping_bs_thousand] = [0,1,2,3];

// ballMapping[v_mapping_bs_hundred] = [0,1,2,3];
// ballMapping[v_mapping_bs_one] = [0,1,2,3];
// ballMapping[v_mapping_bs_ten] = [0,1,2,3];

// 趣味

ballMapping[v_mapping_fun_ten_thousand] = [ 0, 1 ];
ballMapping[v_mapping_fun_thousand] = [ 0, 1 ];
ballMapping[v_mapping_fun_hundred] = [ 0, 1 ];

ballMapping[v_mapping_ten_thousand] = [ 0, 1, 2, 3, 4 ];
ballMapping[v_mapping_thousand] = [ 0, 1, 2, 3, 4 ];
ballMapping[v_mapping_hundred] = [ 0, 1, 2, 3, 4 ];

// ballMapping[v_mapping_fun_ten_thousand] = [0,1];
// ballMapping[v_mapping_fun_thousand] = [0,1];
// ballMapping[v_mapping_fun_hundred] = [0,1];

// ballMapping[v_mapping_ten_thousand] = [0,1,2,3,4];
// ballMapping[v_mapping_thousand] = [0,1,2,3,4];
// ballMapping[v_mapping_hundred] = [0,1,2,3,4];

ballMapping[v_mapping_10playballs] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];

// 任

ballMapping[v_mapping_checkbox] = [ '萬', '千', '百', '十', '個' ];
ballMapping[v_mapping_import_area] = [ '萬', '千', '百', '十', '個' ];

// ballMapping[v_mapping_checkbox] = [0,1,2,3,4];

// 龍虎和

ballMapping[v_mapping_tenthousand1000] = [ 'L', 'H', '1' ];
ballMapping[v_mapping_tenthousand100] = [ 'L', 'H', '1' ];
ballMapping[v_mapping_tenthousand10] = [ 'L', 'H', '1' ];
ballMapping[v_mapping_tenthousand01] = [ 'L', 'H', '1' ];
ballMapping[v_mapping_thousand10] = [ 'L', 'H', '1' ];
ballMapping[v_mapping_thousand01] = [ 'L', 'H', '1' ];
ballMapping[v_mapping_thousand100] = [ 'L', 'H', '1' ];
ballMapping[v_mapping_hundred10] = [ 'L', 'H', '1' ];
ballMapping[v_mapping_hundred01] = [ 'L', 'H', '1' ];
ballMapping[v_mapping_ten01] = [ 'L', 'H', '1' ];

// ballMapping[v_mapping_hundred10] = [0,1,2];

ballMapping['position'] = [];
ballMapping['position'][v_mapping_wan] = '萬';
ballMapping['position'][v_mapping_qian] = '千';
ballMapping['position'][v_mapping_bai] = '百';
ballMapping['position'][v_mapping_shi] = '十';
ballMapping['position'][v_mapping_ge] = '個';

ballMapping['ball-digit-01'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];
ballMapping['ball-digit-02'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];
ballMapping['ball-digit-03'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];
ballMapping['ball-digit-04'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];
ballMapping['ball-digit-05'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];

ballMapping['ball-front-03'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];
ballMapping['ball-front-02'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];

ballMapping['ball-promise'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];
ballMapping['ball-pull'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];

ballMapping['ball-sd'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];

ballMapping['ball-guess-middle'] = [ 3, 4, 5, 6, 7, 8, 9 ];

ballMapping['ball-any-11'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];
ballMapping['ball-any-22'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];
ballMapping['ball-any-33'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];
ballMapping['ball-any-44'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];
ballMapping['ball-any-55'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];
ballMapping['ball-any-65'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];
ballMapping['ball-any-75'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];
ballMapping['ball-any-85'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ];

ballMapping['ball-win-total-pk10'] = [ 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 ];
ballMapping['ball-win-bs-pk10'] = [ 1, 2, 3, 4 ];

ballMapping['ball-gr01-pk10'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
ballMapping['ball-gr02-pk10'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
ballMapping['ball-gr03-pk10'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
ballMapping['ball-gr04-pk10'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
ballMapping['ball-gr05-pk10'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
ballMapping['ball-gr06-pk10'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
ballMapping['ball-gr07-pk10'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
ballMapping['ball-gr08-pk10'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
ballMapping['ball-gr09-pk10'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
ballMapping['ball-gr10-pk10'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];

ballMapping['ball-dt1v10-pk10'] = [ "l", "h" ];
ballMapping['ball-dt2v9-pk10'] = [ "l", "h" ];
ballMapping['ball-dt3v8-pk10'] = [ "l", "h" ];
ballMapping['ball-dt4v7-pk10'] = [ "l", "h" ];
ballMapping['ball-dt5v6-pk10'] = [ "l", "h" ];

ballMapping['ball-champion-pk10'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
ballMapping['ball-second-pk10'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
ballMapping['ball-third-pk10'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
ballMapping['ball-eight-pk10'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
ballMapping['ball-nine-pk10'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
ballMapping['ball-ten-pk10'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];

ballMapping['ball-total-q3'] = [ 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 ];
ballMapping['ball-same3-single-q3'] = [ 111, 222, 333, 444, 555, 666 ];
ballMapping['ball-same3-all-q3'] = [ 1 ];
ballMapping['ball-same2-single-similar-q3'] = [ 1, 2, 3, 4, 5, 6 ];
ballMapping['ball-same2-single-dissimilar-q3'] = [ 1, 2, 3, 4, 5, 6 ];
ballMapping['ball-same2-multi-q3'] = [ '11*', '22*', '33*', '44*', '55*', '66*' ];
ballMapping['ball-no3-q3'] = [ 1, 2, 3, 4, 5, 6 ];
ballMapping['ball-no2-q3'] = [ 1, 2, 3, 4, 5, 6 ];
ballMapping['ball-link3-q3'] = [ 1 ];
ballMapping['ball-bs-q3'] = [ 1, 2 ];
ballMapping['ball-sd-q3'] = [ 1, 2 ];
ballMapping['ball-gr-q3'] = [ 1, 2, 3, 4, 5, 6 ];
ballMapping['ball-color-red-q3'] = [ 1 ];
ballMapping['ball-color-black-q3'] = [ 1 ];

ballMapping['ball-noassign-3d'] = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ];

// 六合彩
ballMapping['marksix_49ball'] = ["01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20"
	,"21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49"];

ballMapping['marksix_49ball_nobtn'] = ["01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20"
	,"21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49"];

ballMapping['marksix_09ball'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9 ];
ballMapping['marksix_18ball'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 ];
ballMapping['marksix_animalball'] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ];

ballMapping['marksix_10ball'] = [ 1, 6, 2, 7, 3, 8, 4, 9, 5, 10 ];

// --------------------------------------------------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------------------------------------------------

function checkBalance(bet) {
	var balance = getEle("balance").value;
	var newBalance = minus(parseFloat(balance), parseFloat(bet));

	if (newBalance >= 0) {
		return true;
	}
	showRemindBetDiv("餘額不足");
	return false;
}
function deductMoney(bet) {
	var balance = getEle("balance").value;
	var newBalance = getFloatToString(minus(parseFloat(balance), parseFloat(bet)), 5);
	getEle("balance").value = "" + newBalance;

	document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = getEle("balance").value.substring(0, getEle("balance").value
			.indexOf(".") + 3);
}

function getBaselineByPlayedId(mainId, localId, midId, handiCap, minAuthId, baselineObj, data, baselineIndex, lotteryLowfreq) {
	var baselineDateKey = "";
	for ( var key in baselineObj) {
		if (key.replace(/\//g, '').indexOf(data) >= 0) {
			baselineDateKey = "" + key;
			break;
		}
	}

	var baseline = 0;
	var dt_bonus = 0;
	var dt_ratio = 0;
	var dt_switch = 0;

	var tmpObj = {};

	if (lotteryLowfreq == "1") {
		tmpObj = baselineObj[baselineDateKey][mainId][localId][midId][minAuthId][handiCap];
	} else {
		tmpObj = baselineObj[baselineDateKey][mainId][localId][midId][minAuthId];
	}

	var key5 = Object.keys(tmpObj).sort(function(a,b){return a-b;});

	if (key5.length == 1) {
		baseline = tmpObj[key5[0]]["baseline"];
		dt_bonus = tmpObj[key5[0]]["dt_bonus"];
		dt_ratio = tmpObj[key5[0]]["dt_ratio"];
		dt_switch = tmpObj[key5[0]]["dt_switch"];
	} else {

		baseline = tmpObj[key5[parseInt(baselineIndex) - 1]]["baseline"];
		dt_bonus = tmpObj[key5[parseInt(baselineIndex) - 1]]["dt_bonus"];
		dt_ratio = tmpObj[key5[parseInt(baselineIndex) - 1]]["dt_ratio"];
		dt_switch = tmpObj[key5[parseInt(baselineIndex) - 1]]["dt_switch"];
		
//		baseline = tmpObj[baselineIndex]["baseline"];
//		dt_bonus = tmpObj[baselineIndex]["dt_bonus"];
//		dt_ratio = tmpObj[baselineIndex]["dt_ratio"];
//		dt_switch = tmpObj[baselineIndex]["dt_switch"];
	}

	var baselineInfos = new Object();
	baselineInfos.baseline = baseline;
	baselineInfos.dtBonus = dt_bonus;
	baselineInfos.dtRatio = dt_ratio;
	baselineInfos.dtSwitch = dt_switch;

	return baselineInfos;
}

function getOtherInfo() {
	
	getEle("mainOrder").value = "";
	groupMainOrderObj = {};
	
	var otherInfo = {};
	var per = getPeriodNum();

	var l1 = getEle("l1").value
	var l2 = getEle("l2").value
	var l3 = getEle("l3").value
	var l4 = getEle("l4").value

	var lotteryLowfreq = document.getElementsByName("lotteryLowfreq")[0].value

	var baselineDateKey = "";
	var baselineObj = {};
	if (lotteryLowfreq == "1") {
		getRatiosLFJson();
		if (ratiosLFJson.CurrentBaselineLF != null && typeof ratiosLFJson.CurrentBaselineLF != 'undefined') {
			var obj = ratiosLFJson.CurrentBaselineLF;
			for ( var key in obj) {
				baselineDateKey = "" + key;
				if (baselineDateKey.replace(/\//g, '').indexOf(per[0]["date"]) >= 0) {
					baselineObj = obj[key][getEle("mainId").value][getEle("localId").value][getEle("midId").value][getEle("minAuthId").value][getEle("handicapId").value];
					break;
				}
			}
		}
	} else {
		getRatiosJson();
		if (ratiosJson.CurrentBaseline != null && typeof ratiosJson.CurrentBaseline != 'undefined') {
			var obj = ratiosJson.CurrentBaseline;
			for ( var key in obj) {
				baselineDateKey = "" + key;
				if (baselineDateKey.replace(/\//g, '').indexOf(per[0]["date"]) >= 0) {
					baselineObj = obj[key][getEle("mainId").value][getEle("localId").value][getEle("midId").value][getEle("minAuthId").value];
					break;
				}
			}
		}
	}

	var handicap = getEle("handicapId").value;
	var bonusSetMax = getEle("bonusSetMax").value;
	var relativeBaseline = getEle("relativeBaseline").value;
	var bonusSetRatio = document.getElementsByClassName("bottom-area")[0].getElementsByTagName("output")[1].innerHTML; // 獎金組
	var moneyUnit = document.getElementsByClassName("bottom-area")[0].getElementsByTagName("select")[0].value; // 金錢單位
	var noOfBetTimes = document.getElementsByClassName("bottom-area")[0].getElementsByClassName("multiple-drop-menu")[0].getElementsByTagName("input")[0].value; // 倍數

	var mainId = getEle("mainId").value;
	var localId = getEle("localId").value;
	var midId = getEle("midId").value;
	var minAuthId = getEle("minAuthId").value;
	var totalNoOfBet = getEle("totalNoOfBet").value; // 玩法Max注數

	otherInfo.lotteryLowfreq = lotteryLowfreq;
	otherInfo.l1 = l1;
	otherInfo.l2 = l2;
	otherInfo.l3 = l3;
	otherInfo.l4 = l4;
	otherInfo.baselineObj = baselineObj;
	// 對應注數的表
	otherInfo.handicap = handicap;
	otherInfo.moneyUnit = moneyUnit;
	otherInfo.noOfBetTimes = noOfBetTimes;
	otherInfo.bonusRatio = bonusSetRatio;
	otherInfo.bonusSetMax = bonusSetMax;
	otherInfo.relativeBaseline = relativeBaseline;
	otherInfo.periodNum = per["periodNum"];
	otherInfo.date = per["date"];

	otherInfo.accId = getEle("accId").value;
	otherInfo.mainId = mainId;
	otherInfo.localId = localId;
	otherInfo.midId = midId;
	otherInfo.minAuthId = minAuthId;
	otherInfo.totalNoOfBet = totalNoOfBet;

	return otherInfo;
}

function getOtherInfo2() {
	getEle("mainOrder").value = "";
	groupMainOrderObj = {};
	
	var otherInfo = {};
	var per = getPeriodNum();

	var l1 = getEle("l1").value
	var l2 = getEle("l2").value
	var l3 = getEle("l3").value
	var l4 = getEle("l4").value

	var lotteryLowfreq = document.getElementsByName("lotteryLowfreq")[0].value

	var baselineDateKey = "";
	var baselineObj = {};
	if (lotteryLowfreq == "1") {
		getRatiosLFJson();
		if (ratiosLFJson.CurrentBaselineLF != null && typeof ratiosLFJson.CurrentBaselineLF != 'undefined') {
			var obj = ratiosLFJson.CurrentBaselineLF;
			for ( var key in obj) {
				baselineDateKey = "" + key;
				if (baselineDateKey.replace(/\//g, '').indexOf(per[0]["date"]) >= 0) {
					baselineObj = obj[key][getEle("mainId").value][getEle("localId").value][getEle("midId").value][getEle("minAuthId").value][getEle("handicapId").value];
					break;
				}
			}
		}
	} else {
		getRatiosJson();
		if (ratiosJson.CurrentBaseline != null && typeof ratiosJson.CurrentBaseline != 'undefined') {
			var obj = ratiosJson.CurrentBaseline;
			for ( var key in obj) {
				baselineDateKey = "" + key;
				if (baselineDateKey.replace(/\//g, '').indexOf(per[0]["date"]) >= 0) {
					baselineObj = obj[key][getEle("mainId").value][getEle("localId").value][getEle("midId").value][getEle("minAuthId").value];
					break;
				}
			}
		}
	}

	var handicap = getEle("handicapId").value;
	var bonusSetMax = getEle("bonusSetMax").value;
	var relativeBaseline = getEle("relativeBaseline").value;
	var bonusSetRatio = document.getElementsByClassName("bottom-area")[0].getElementsByTagName("output")[1].innerHTML; // 獎金組
	var moneyUnit = document.getElementsByClassName("betlimit")[0].getElementsByTagName("input")[0].value; // 金錢單位
	var noOfBetTimes = 1; // 倍數

	var mainId = getEle("mainId").value;
	var localId = getEle("localId").value;
	var midId = getEle("midId").value;
	var minAuthId = getEle("minAuthId").value;
	var totalNoOfBet = getEle("totalNoOfBet").value; // 玩法Max注數

	otherInfo.lotteryLowfreq = lotteryLowfreq;
	otherInfo.l1 = l1;
	otherInfo.l2 = l2;
	otherInfo.l3 = l3;
	otherInfo.l4 = l4;
	otherInfo.baselineObj = baselineObj;
	// 對應注數的表
	otherInfo.handicap = handicap;
	otherInfo.moneyUnit = moneyUnit;
	otherInfo.noOfBetTimes = noOfBetTimes;
	otherInfo.bonusRatio = bonusSetRatio;
	otherInfo.bonusSetMax = bonusSetMax;
	otherInfo.relativeBaseline = relativeBaseline;
	otherInfo.periodNum = per["periodNum"];
	otherInfo.date = per["date"];

	otherInfo.accId = getEle("accId").value;
	otherInfo.mainId = mainId;
	otherInfo.localId = localId;
	otherInfo.midId = midId;
	otherInfo.minAuthId = minAuthId;
	otherInfo.totalNoOfBet = totalNoOfBet;

	return otherInfo;
}

function checkNowPlayGameNotNull() {
	return true;
}

/**
 * @description 前往下注
 */

function goBet() {
	try {
		enableLoadingPage();
		setTimeout('goBet2()', 10);
	} catch (e) {
		console_Log(e);
	} finally {
		setTimeout('disableLoadingPage()', 10);
	}
}

function goBet2() {
	var bet = document.getElementsByClassName("bet-area")[0].getElementsByClassName("bottom-area")[0].getElementsByClassName("total")[0].getElementsByTagName("span")[1].innerHTML;
	var periodNumList = getPeriodNum();

	var mainOrderListJSON = isJSON2(getEle("mainOrder").value);
	if (mainOrderListJSON ? (checkBalance(bet) ? typeof periodNumList[0].date !== "undefined" : false) : false) {
		try {
			var allOrder = {};
			allOrder['orders'] = [];
			if (Array.isArray(mainOrderListJSON) == true) {
				for (var k = 0; k < mainOrderListJSON.length; k++) {
					allOrder['orders'].push(mainOrderListJSON[k]);
				}
			} else {
				allOrder['orders'].push(mainOrderListJSON);
			}

			var resultMainOrder = checkMainOrderListTimes(allOrder, periodNumList[0].date);

			if (typeof resultMainOrder === "boolean") {
				if (resultMainOrder == true) {
					var allOrder2 = {};
					allOrder2['orders'] = [];
					for (var k = 0; k < allOrder['orders'].length; k++) {
						var mainOrder = mergeOrder(allOrder['orders'][k], periodNumList, '0', 0, false);
						if (mainOrder == false || mainOrder == null) {
							showRemindBetDiv("投注失敗");
							return false;
						}
						allOrder2['orders'].push(mainOrder)
					}
					sendBettingAjax(JSON.stringify(allOrder2)); // 送出下注資料

				} else if (resultMainOrder == false) {
					showRemindBetDiv("投注失敗");
				}
			} else if (typeof resultMainOrder === "object") {
				showRemindBetDiv2("已超出中獎最高金額，是否以最高倍數下注", function() {
					var allOrder2 = {};
					allOrder2['orders'] = [];
					for (var k = 0; k < allOrder['orders'].length; k++) {
						var mainOrder = mergeOrder(allOrder['orders'][k], periodNumList, '0', 0, true);
						if (mainOrder == false || mainOrder == null) {
							showRemindBetDiv("投注失敗");
							return false;
						}
						allOrder2['orders'].push(mainOrder);
					}
					sendBettingAjax(JSON.stringify(allOrder2)); // 送出下注資料

				});
			}

		} catch (e) {
			console_Log(e);
		}
	} else {
		showRemindBetDiv("投注失敗");
	}
}

function mergeOrder(mainOrder, periods, orderType, needChangeTimes, isMaxNoOfBetTimes) {
	
	var noBetOrderAuthId = ['160','548','310','148','536','298','154','542','304','214','602','364','204'
		,'592','354','197','585','347','185','573','335','186','574','336','189','577','339','190','578'
		,'340','97','247','485','114','264','502','131','281','519','81','231','469','89','239','477'];
		//,'645','646','647','648','649','661','662','663','664','665','666','667','668'];
	
	var z3AuthId = ['105','122','139','213','493','510','527','601','225','272','289','363','396'];
	var z3PlayedId = [22,36,50,448,455,462,469,476,483,490,535];
	
	var a = new Date().getTime();

	mainOrder.startPeriodNum = periods[0].periodNum;
	mainOrder.stopPeriodNum = periods[periods.length - 1].periodNum;
	mainOrder.noOfPeriod = "" + periods.length;


	var midOrderObj = JSON.parse(JSON.stringify(mainOrder.midOrders[0]));
	var amountOfAllMidOrder = 0;
	var maxWinBonus = divide(Math.floor(times(parseFloat(getEle("maxWinBonus").value), 100)), 100);
	var baselineInfos = {};
	var baselineIndex = -1;

	var mainId = mainOrder.mainId;
	var localId = mainOrder.localId;
	var midId = mainOrder.midId;
	var handiCap = mainOrder.handiCap;
	var lotteryLowfreq = mainOrder.lotteryLowfreq;
	var minAuthId = mainOrder.minAuthId;
	var periodDate = "";

	getRatiosJson();
	getRatiosLFJson();

	if (typeof periods != 'undefined' ? periods != null : false ) {
		for (var j = 0; j < periods.length; j++) {
			if (j != 0) {
				var tmpMidOrderObj = JSON.parse(JSON.stringify(midOrderObj));

				mainOrder.midOrders.push(tmpMidOrderObj);
			}
			if (needChangeTimes == 1) {
				mainOrder.midOrders[j].noOfBetTimes = periods[j].noOfBetTimes;
			}
			var maxBonus = 0;
			for (var i = 0; i < mainOrder.midOrders[j].betOrders.length; i++) {
				var playedId = parseInt(mainOrder.midOrders[j].betOrders[i].playedId);
				if (baselineIndex != mainOrder.midOrders[j].betOrders[i].baselineIndex || periodDate != "" || periodDate != periods[j].date) {
					baselineIndex = mainOrder.midOrders[j].betOrders[i].baselineIndex;
					periodDate = periods[j].date;
					var currentObj;
					if (mainOrder.lotteryLowfreq == "1") {
						currentObj = ratiosLFJson.CurrentBaselineLF;
					} else {
						currentObj = ratiosJson.CurrentBaseline;
					}
					if (typeof currentObj === 'undefined' || currentObj == null) {
						return null;
					}
					baselineInfos = getBaselineByPlayedId(mainId, localId, midId, handiCap, minAuthId, currentObj, periodDate, baselineIndex, lotteryLowfreq);
				}

				if(z3AuthId.includes(mainOrder.minAuthId) ? z3PlayedId.includes(playedId) : false){
					mainOrder.midOrders[j].betOrders[i].baseline = "" + parseFloat(baselineInfos.baseline) * 2;
				}
				else{
					mainOrder.midOrders[j].betOrders[i].baseline = baselineInfos.baseline;
				}
				
				// 子單中獎時給多少錢 = 單位*(賠率(%)/100)*(盤口賠率*獎金組/2000)
				// -先不乘-幾倍
				if (typeof mainOrder.midOrders[j].betOrders[i].moneyUnit != "undefined" && typeof mainOrder.midOrders[j].betOrders[i].relativeBaseline != "undefined"
						&& typeof mainOrder.midOrders[j].betOrders[i].bonusRatio != "undefined") {
					mainOrder.midOrders[j].betOrders[i].bonus = ""
							+ times(parseFloat(mainOrder.midOrders[j].betOrders[i].moneyUnit), parseFloat(mainOrder.midOrders[j].betOrders[i].baseline), divide(
									parseFloat(mainOrder.midOrders[j].betOrders[i].relativeBaseline), 100), divide(
									parseFloat(mainOrder.midOrders[j].betOrders[i].bonusRatio), 2000));
				}

				var bonusA = "" + divide(Math.floor(times(parseFloat(mainOrder.midOrders[j].betOrders[i].bonus), parseFloat(mainOrder.midOrders[j].noOfBetTimes), 100)), 100);

				var bonusB = "" + divide(Math.floor(times(parseFloat(mainOrder.midOrders[j].betOrders[i].bonus), 100)), 100);

				if (isMaxNoOfBetTimes == true) {
					if (bonusA > maxWinBonus) {
						mainOrder.midOrders[j].noOfBetTimes = divide(Math.floor(times(divide(maxWinBonus, bonusB), 100)), 100);
						;
						bonusA = ""
								+ divide(
										Math.floor(times(parseFloat(mainOrder.midOrders[j].betOrders[i].bonus),
												parseFloat(mainOrder.midOrders[j].noOfBetTimes), 100)), 100);
					}
				}
				if (mainOrder.isDt == 1) {
					if (bonusA > parseFloat(baselineInfos.dtBonus)) {
						bonusA = baselineInfos.dtBonus;
					}
				}
				if (maxBonus < bonusB) {
					maxBonus = divide(Math.floor(times(bonusB, 100)), 100);
				}
				mainOrder.midOrders[j].betOrders[i].bonus = "" + divide(Math.floor(times(bonusA, 100)), 100);
				mainOrder.midOrders[j].betOrders[i].amount = ""
						+ divide(Math.floor(times(parseFloat(mainOrder.midOrders[j].betOrders[i].amount), parseFloat(mainOrder.midOrders[j].noOfBetTimes), 100)), 100);

				delete mainOrder.midOrders[j].betOrders[i].moneyUnit;
				delete mainOrder.midOrders[j].betOrders[i].relativeBaseline;
				delete mainOrder.midOrders[j].betOrders[i].bonusRatio;

			}
			mainOrder.midOrders[j].amount = "" + divide(Math.floor(times(parseFloat(mainOrder.midOrders[j].amount), parseFloat(mainOrder.midOrders[j].noOfBetTimes), 100)), 100);
			mainOrder.midOrders[j].fanDen = "" + divide(Math.floor(times(mainOrder.midOrders[j].fanDen, parseFloat(mainOrder.midOrders[j].noOfBetTimes), 100)), 100);

			mainOrder.midOrders[j].maxBonus = maxBonus;
			mainOrder.midOrders[j].periodNum = periods[j].periodNum;

			amountOfAllMidOrder = plus(parseFloat(amountOfAllMidOrder), parseFloat(mainOrder.midOrders[j].amount));

			delete mainOrder.midOrders[j].l1;
			delete mainOrder.midOrders[j].l2;
			delete mainOrder.midOrders[j].l3;
			delete mainOrder.midOrders[j].l4;
			delete mainOrder.playedText;

			
			if(noBetOrderAuthId.includes(mainOrder.minAuthId)){
				mainOrder.midOrders[j].betOrders = [];
			}

		}
		mainOrder.amount = "" + amountOfAllMidOrder;

		mainOrder.orderType = orderType;
		mainOrder.actionTime = "" + new Date().getTime();

		deductMoney(amountOfAllMidOrder);

		console.log(mainOrder);
		return mainOrder;

	} else {
		return null;
	}
}

/**
 * @description 取得畫面資料轉換成可處理的字串格式
 * @returns {string} 例如: 三星直選 => 1,2|4|5,6,7
 */
function getContent() {

	subOrderInfos = document.querySelector('[name="subOrderInfos"]').value;
	subOrderInfos = Strings.decode(subOrderInfos);
	if (!subOrderInfos || subOrderInfos === '') {
		return responseFormat('error', 'input hidden subOrderInfos 資料是空的或不存在');
	}
	orderInfo = JSON.parse(subOrderInfos);
	subOrderInfo = orderInfo['SubOrderInfo']; // 對應playedId的表
	subOrderInfoNoOfBet = orderInfo['SubOrderInfoNoOfBet']; // 對應注數的表

	var data = document.querySelector('#game-choice').value;

	if (!data || data == '') {
		return responseFormat('error', '按鈕資料是空的或不存在');
	}

	var bet = JSON.parse(data);

	var posArray = [];
	var valueArray = [];
	for ( var key in bet) { // 萬 > 千 > 百 > 十 > 個
		if (bet.hasOwnProperty(key)) {
			var betData = bet[key];
			var valueString = '';
			var text = "";
			if (key == v_mapping_textarea) {
				if (betData.length == 1) {
					text = betData[0];
				}
			} else {
				for (var i = 0; i < betData.length; i++) {
					if (betData[i] == 1) {
						valueString += ballMapping[key][i] + ",";
					}
				}
				valueString = valueString.slice(0, -1);
			}

			if (key == v_mapping_checkbox || key == v_mapping_import_area) {
				posArray.push(valueString);
			} else if (key == v_mapping_textarea) {
				valueArray.push(text);
			} else {
				valueArray.push(valueString);
			}
			delete betData, valueString;
			betData = undefined;
			valueString = undefined;
		}
	}

	var dataObj = {};
	dataObj.data = valueArray.join('|');
	dataObj.position = posArray.join(',');
	if(typeof dataObj.data === "undefined"){
		dataObj.data = "";
	}
	if(typeof dataObj.position === "undefined"){
		dataObj.position = "";
	}
	var value = JSON.stringify(dataObj);

	getEle('middle-order').value = value; // 將組合出來的值存起來
	delete bet, posArray, valueArray;
	bet = undefined;
	posArray = undefined;
	valueArray = undefined;
	return value;

}

/**
 * @description 取得目前點選的所有子單注數總和，要顯示在畫面的共X注用
 * @param {array}
 *                betOrder
 * @returns {number} 例如: 2
 */
function getCurrentBetTotal(betOrder) {
	var midOrderNoOfBet = 0;
	for ( var key in betOrder) {
		var bet = betOrder[key];
		if (bet['noOfBet'] != null && typeof bet['noOfBet'] != undefined) {
			midOrderNoOfBet += Number(bet['noOfBet']);
		}
		delete bet;
		bet = undefined;
	}
	return midOrderNoOfBet;
}

function getPeriodNum(index) {
	var arr = [];
	var l1 = getEle("l1").value;
	var l2 = getEle("l2").value;
	var kjTimes;
	if (typeof kjTimeJson === "undefined" || kjTimeJson == "" || kjTimeJson == null) {
		kjTimeJson = JSON.parse(Strings.decode(getEle("kjTimes").value));
		kjTimes = kjTimeJson;

	} else {
		kjTimes = kjTimeJson;
	}
	var keys = Object.keys(kjTimes["KjTimeStatus"]).sort();
	var keys2 = Object.keys(kjTimes["KjTimeStatus"][keys[l1]]).sort();
	var periodList = kjTimes["KjTimeStatus"][getEle("localId").value];
	var das = Object.keys(periodList).sort();
	var date = new Date().getTime().toString();
	date = parseInt(date.substring(0, date.length - 3));
	if (typeof index === "undefined" && getEle("nowNo").value != "") {
		var obj = {};
		obj["date"] = getEle("nowDate").value;
		obj["periodNum"] = getEle("nowNo").value;
		obj["expS"] = "" + parseInt(getEle("nowS").value) * 1000;
		obj["e"] = "" + parseInt(getEle("nowE").value) * 1000;
		arr.push(obj);
		return arr;
	} else {
		for ( var da in das) {
			var d = periodList[das[da]];
			var obj = {};
			if (typeof index === "undefined" && date > parseInt(d["exp_s"]) && date < parseInt(d["e"]) && d["stat"] != 4) {
				obj["date"] = d["date"];
				obj["periodNum"] = "" + d["p_n"];
				obj["expS"] = "" + parseInt(d["exp_s"]) * 1000;
				obj["e"] = "" + parseInt(d["e"]) * 1000;
				arr.push(obj);
				return arr;
			} else if ((typeof index == "number" && parseInt(d["p_n"]) >= parseInt(getEle("nowNo").value) && parseInt(index) > 0) && date < parseInt(d["e"]) && d["stat"] != 4) {
				obj["date"] = d["date"];
				obj["periodNum"] = "" + d["p_n"];
				obj["expS"] = "" + parseInt(d["exp_s"]) * 1000;
				obj["e"] = "" + parseInt(d["e"]) * 1000;
				arr.push(obj);
				index--;
				if (index == 0) {
					break;
				}
			}
		}
	}
	return arr;
}

function getPeriodNum2(startPeriodNum, index) {
	var arr = [];

	var l1 = getEle("l1").value;
	var l2 = getEle("l2").value;
	var kjTimes
	if (typeof kjTimeJson === "undefined" || kjTimeJson == "" || kjTimeJson == null) {
		kjTimeJson = JSON.parse(Strings.decode(getEle("kjTimes").value));
		kjTimes = kjTimeJson;

	} else {
		kjTimes = kjTimeJson;
	}

	var keys = Object.keys(kjTimes["KjTimeStatus"]).sort();
	var keys2 = Object.keys(kjTimes["KjTimeStatus"][keys[l1]]).sort();
	var periodList = kjTimes["KjTimeStatus"][getEle("localId").value];
	var das = Object.keys(periodList).sort();
	var date = new Date().getTime().toString();
	date = parseInt(date.substring(0, date.length - 3));
	if (typeof startPeriodNum === "undefined" && typeof index === "undefined" && getEle("nowNo").value != "") {
		var obj = {};
		obj["date"] = getEle("nowDate").value;
		obj["periodNum"] = getEle("nowNo").value;
		obj["expS"] = "" + parseInt(getEle("nowS").value) * 1000;
		obj["e"] = "" + parseInt(getEle("nowE").value) * 1000;
		arr.push(obj);
		return arr;
	} else {
		for ( var da in das) {
			var d = periodList[das[da]];
			var obj = {};
			if (typeof index === "undefined" && parseInt(d["p_n"]) == parseInt(startPeriodNum) && date > parseInt(d["exp_s"]) && date < parseInt(d["e"]) && d["stat"] != 4) {
				obj["date"] = d["date"];
				obj["periodNum"] = "" + d["p_n"];
				obj["expS"] = "" + parseInt(d["exp_s"]) * 1000;
				obj["e"] = "" + parseInt(d["e"]) * 1000;
				arr.push(obj);
				return arr;
			} else if ((typeof index == "number" && parseInt(d["p_n"]) >= parseInt(getEle("nowNo").value) && parseInt(d["p_n"]) >= parseInt(startPeriodNum) && parseInt(index) > 0)
					&& date < parseInt(d["e"]) && d["stat"] != 4) {
				obj["date"] = d["date"];
				obj["periodNum"] = "" + d["p_n"];
				obj["expS"] = "" + parseInt(d["exp_s"]) * 1000;
				obj["e"] = "" + parseInt(d["e"]) * 1000;
				arr.push(obj);
				index--;
				if (index == 0) {
					break;
				}
			}
		}
	}

	return arr;
}

// ======================================================================================================================================
// ======================================================================================================================================
// ======================================================================================================================================
// ======================================================================================================================================

/**
 * 解決浮點運算問題，避免小數點產生多位數和計算精度損失。 範例：2.3 + 2.4 = 4.699999999999999，1.0 - 0.9 =
 * 0.09999999999999998
 */
/**
 * 把錯誤的數據轉正 strip(0.09999999999999998)=0.1
 */
function strip(num, precision) {
	if (precision === void 0) {
		precision = 12;
	}
	return +parseFloat(num.toPrecision(precision));
}
/**
 * Return digits length of a number
 * 
 * @param {*number}
 *                num Input number
 */
function digitLength(num) {
	// Get digit length of e
	var eSplit = num.toString().split(/[eE]/);
	var len = (eSplit[0].split('.')[1] || '').length - (+(eSplit[1] || 0));
	return len > 0 ? len : 0;
}
/**
 * 把小數轉成整數，支持科學計算法。如果是小數則放大成整數
 * 
 * @param {*number}
 *                num 傳入值
 */
function float2Fixed(num) {
	if (num.toString().indexOf('e') === -1) {
		return Number(num.toString().replace('.', ''));
	}
	var dLen = digitLength(num);
	return dLen > 0 ? num * Math.pow(10, dLen) : num;
}
/**
 * 檢測數字是否越界，如果越界給出提示
 * 
 * @param {*number}
 *                num 傳入值
 */
function checkBoundary(num) {
	if (num > Number.MAX_SAFE_INTEGER || num < Number.MIN_SAFE_INTEGER) {
		// console.warn(num + " is beyond boundary when transfer to
		// integer, the results may not be accurate");
	}
}
/**
 * 精確乘法
 */
function times(num1, num2) {
	var others = [];
	for (var _i = 2; _i < arguments.length; _i++) {
		others[_i - 2] = arguments[_i];
	}
	if (others.length > 0) {
		return times.apply(void 0, [ times(num1, num2), others[0] ].concat(others.slice(1)));
	}
	var num1Changed = float2Fixed(num1);
	var num2Changed = float2Fixed(num2);
	var baseNum = digitLength(num1) + digitLength(num2);
	var leftValue = num1Changed * num2Changed;
	checkBoundary(leftValue);
	return leftValue / Math.pow(10, baseNum);
}
/**
 * 精確加法
 */
function plus(num1, num2) {
	var others = [];
	for (var _i = 2; _i < arguments.length; _i++) {
		others[_i - 2] = arguments[_i];
	}
	if (others.length > 0) {
		return plus.apply(void 0, [ plus(num1, num2), others[0] ].concat(others.slice(1)));
	}
	var baseNum = Math.pow(10, Math.max(digitLength(num1), digitLength(num2)));
	return (times(num1, baseNum) + times(num2, baseNum)) / baseNum;
}
/**
 * 精確減法
 */
function minus(num1, num2) {
	var others = [];
	for (var _i = 2; _i < arguments.length; _i++) {
		others[_i - 2] = arguments[_i];
	}
	if (others.length > 0) {
		return minus.apply(void 0, [ minus(num1, num2), others[0] ].concat(others.slice(1)));
	}
	var baseNum = Math.pow(10, Math.max(digitLength(num1), digitLength(num2)));
	return (times(num1, baseNum) - times(num2, baseNum)) / baseNum;
}
/**
 * 精確除法
 */
function divide(num1, num2) {
	var others = [];
	for (var _i = 2; _i < arguments.length; _i++) {
		others[_i - 2] = arguments[_i];
	}
	if (others.length > 0) {
		return divide.apply(void 0, [ divide(num1, num2), others[0] ].concat(others.slice(1)));
	}
	var num1Changed = float2Fixed(num1);
	var num2Changed = float2Fixed(num2);
	checkBoundary(num1Changed);
	checkBoundary(num2Changed);
	return times((num1Changed / num2Changed), Math.pow(10, digitLength(num2) - digitLength(num1)));
}
/**
 * 四捨五入
 */
function round(num, ratio) {
	var base = Math.pow(10, ratio);
	return divide(Math.round(times(num, base)), base);
}

function getFloatToString(f, num) {
	if (!num || num == null || isNaN(parseInt(num))) {
		num = 1;
	}
	if (!f || f == null || isNaN(parseFloat("" + f))) {
		f = "0.";
	} else {
		f = "" + f;
		if (f.indexOf(".") == -1) {
			f += "."
		}
	}
	while ((f.length - f.indexOf(".")) < (num + 1)) {
		f += "0";
	}
	if ((f.length - f.indexOf(".")) > (num)) {
		f = f.substring(0, (f.indexOf(".") + num + 1));
	}
	return f;
}

// 建立 追號單
function getMainOrders(betOrderList, otherInfo) {
	otherInfo["playedText"] = "";
	getEle("mainOrder").value = "";
	groupMainOrderObj = {};
	if (typeof betOrderList === "string") {
		var betOrderJson = JSON.parse(betOrderList);
		if (betOrderJson.status == "error") {
			document.getElementsByClassName("bet")[0].childNodes[0].disabled = true;
			document.getElementsByClassName("bet")[0].childNodes[1].disabled = true;
			document.getElementsByClassName("bet")[0].childNodes[0].title = betOrderJson.content;
			document.getElementsByClassName("bet")[0].childNodes[1].title = betOrderJson.content;

			document.getElementsByClassName("total")[0].getElementsByTagName("span")[0].innerHTML = "0"; // 注數
			document.getElementsByClassName("total")[0].getElementsByTagName("span")[1].innerHTML = "0.00"; // 金額
			document.getElementsByClassName("highlight")[0].innerHTML = "0.00"; // 最高中獎金額
		} else {
			document.getElementsByClassName("bet")[0].getElementsByTagName("button")[0].disabled = true;
			document.getElementsByClassName("bet")[0].getElementsByTagName("button")[1].disabled = true;
			document.getElementsByClassName("bet")[0].childNodes[0].title = "";
			document.getElementsByClassName("bet")[0].childNodes[1].title = "";

			document.getElementsByClassName("total")[0].getElementsByTagName("span")[0].innerHTML = "0"; // 注數
			document.getElementsByClassName("total")[0].getElementsByTagName("span")[1].innerHTML = "0.00"; // 金額
			document.getElementsByClassName("highlight")[0].innerHTML = "0.00"; // 最高中獎金額

		}
	}

	if (Array.isArray(betOrderList) == true) {
		if (betOrderList.length == 0) {
			document.getElementsByClassName("bet")[0].getElementsByTagName("button")[0].disabled = true;
			document.getElementsByClassName("bet")[0].getElementsByTagName("button")[1].disabled = true;
			document.getElementsByClassName("bet")[0].childNodes[0].title = "";
			document.getElementsByClassName("bet")[0].childNodes[1].title = "";

			document.getElementsByClassName("total")[0].getElementsByTagName("span")[0].innerHTML = "0"; // 注數
			document.getElementsByClassName("total")[0].getElementsByTagName("span")[1].innerHTML = "0.00"; // 金額
			document.getElementsByClassName("highlight")[0].innerHTML = "0.00"; // 最高中獎金額

			return

			

		}
		document.getElementsByClassName("bet")[0].getElementsByTagName("button")[0].disabled = false;
		document.getElementsByClassName("bet")[0].getElementsByTagName("button")[1].disabled = false;
		document.getElementsByClassName("bet")[0].childNodes[0].title = "";
		document.getElementsByClassName("bet")[0].childNodes[1].title = "";

		var midOrderArr = new Array();
		var midOrder = new Object();
		var midOrderAmount = 0;
		var midOrderNoOfBet = 0;
		var maxBouns = 0;
		var fanDen = 0;

		for ( var key in betOrderList) {
			var bet = betOrderList[key];
			midOrderAmount = plus(midOrderAmount, parseFloat(bet['amount']));
			midOrderNoOfBet += parseInt(bet['noOfBet']);
			if (parseFloat(bet['bonus']) > maxBouns) {
				maxBouns = divide(Math.floor(times(parseFloat(bet['bonus']), 100)), 100);
			}

			delete bet;
			bet = undefined;
		}
		var bonusRatioF = minus(parseInt(otherInfo.bonusSetMax), parseInt(otherInfo.bonusRatio));
		var bonusRatioS = divide(bonusRatioF, 2000);
		fanDen = times(midOrderAmount, bonusRatioS);

		midOrder.periodNum = ""; // 期號
		midOrder.amount = "" + midOrderAmount; // 下注總額(母單)
		midOrder.maxBonus = "" + maxBouns; // 母單最多可贏多少
		midOrder.fanDen = "" + fanDen; // 這期對獎完畢時反點的金額
		midOrder.noOfBet = "" + midOrderNoOfBet; // 整個母單的注數
		midOrder.betOrders = betOrderList;

		midOrder.noOfBetTimes = otherInfo['noOfBetTimes']; // 倍數

		midOrder.l1 = otherInfo.l1;
		midOrder.l2 = otherInfo.l2;
		midOrder.l3 = otherInfo.l3;
		midOrder.l4 = otherInfo.l4;

		midOrderArr.push(midOrder);

		var mainOrder = new Object();
		var betRatio = 0;
		var isDt = 0;

		if (midOrder["noOfBet"] != null) {
			betRatio = divide(parseInt(midOrder["noOfBet"]), parseInt(otherInfo["totalNoOfBet"]));
			if (otherInfo["dtSwitch"] == '0') {
				isDt = 0;
			} else {
				var betRatioA = times(betRatio, 100)
				if (betRatioA < otherInfo["dtRatio"]) {
					isDt = 1;
				} else {
					isDt = 0;
				}
			}
		}

		mainOrder.startPeriodNum = ""; // 開始期號
		mainOrder.stopPeriodNum = ""; // 停止期號
		if (typeof otherInfo["position"] != "undefined" && otherInfo["position"] != null && otherInfo["position"] != "") {
			mainOrder.betData = "" + otherInfo["betData"] + "|" + otherInfo["position"];
		} else {
			mainOrder.betData = "" + otherInfo["betData"]; // 母單的下注資訊
		}
		mainOrder.betData2 = "" + otherInfo["betData"];
		mainOrder.noOfPeriod = ""; // 共幾期
		mainOrder.accId = "" + otherInfo["accId"]; // 帳號

		mainOrder.mainId = "" + otherInfo["mainId"]; // 時時彩
		mainOrder.localId = "" + otherInfo["localId"]; // 重慶時時彩
		mainOrder.midId = "" + otherInfo["midId"]; // 五星
		mainOrder.minAuthId = "" + otherInfo["minAuthId"]; // 五星直選

		mainOrder.actionTime = ""; // 下注時間

		mainOrder.amount = "" + midOrderAmount; // 下注總額(追號單)
		mainOrder.moneyUnit = "" + otherInfo["moneyUnit"];
		mainOrder.orderType = '0'; // 是否 中獎不追等等 //待補
		mainOrder.handiCap = "" + otherInfo["handicap"];
		mainOrder.bonusSetRatio = "" + otherInfo["bonusRatio"]; // 獎金組

		mainOrder.lotteryLowfreq = "" + otherInfo["lotteryLowfreq"]; // 是否為低頻彩
		// mainOrder.noOfBetTimes = ""+otherInfo["noOfBetTimes"]; // 倍數

		mainOrder.playedText = getEle("playedId").value;

		mainOrder.baseBet = otherInfo.baseBet; // 最低投注額

		mainOrder.betRatio = "" + betRatio; // 投注比例

		mainOrder.isDt = "" + isDt;

		mainOrder['midOrders'] = midOrderArr;

		document.getElementsByClassName("total")[0].getElementsByTagName("span")[0].innerHTML = midOrder.noOfBet; // 注數
		document.getElementsByClassName("total")[0].getElementsByTagName("span")[1].innerHTML = divide(Math.floor(times(parseFloat(midOrder.amount), parseFloat(midOrder.noOfBetTimes), 100)),
				100); // 金額
		document.getElementsByClassName("highlight")[0].innerHTML = "" + maxBouns; // 最高中獎金額

		if (divide(Math.floor(times(parseFloat(midOrder.amount), parseFloat(midOrder.noOfBetTimes), 100)), 100) < parseFloat(otherInfo.baseBet)) {
			document.getElementsByClassName("bet")[0].getElementsByTagName("button")[0].disabled = true;
			document.getElementsByClassName("bet")[0].getElementsByTagName("button")[1].disabled = true;
			document.getElementsByClassName("bet")[0].childNodes[0].title = "投注金額不足";
			document.getElementsByClassName("bet")[0].childNodes[1].title = "投注金額不足";
		} else if (divide(Math.floor(times(parseFloat(midOrder.amount), parseFloat(midOrder.noOfBetTimes), 100)), 100) > parseFloat(getEle("balance").value.substring(0,
				getEle("balance").value.indexOf(".") + 3))) {
			document.getElementsByClassName("bet")[0].getElementsByTagName("button")[0].disabled = true;
			document.getElementsByClassName("bet")[0].getElementsByTagName("button")[1].disabled = true;
			document.getElementsByClassName("bet")[0].childNodes[0].title = "餘額不足";
			document.getElementsByClassName("bet")[0].childNodes[1].title = "餘額不足";
		}
		console_Log(mainOrder);

		getEle("mainOrder").value = JSON.stringify(mainOrder);
		groupMainOrderObj = mainOrder;
	}
}

// check添加追號單
function checkBetDataIsRepeat() {
	var moneyUnit = document.getElementsByClassName("bottom-area")[0].getElementsByTagName("select")[0].value; // 金錢單位
	var noOfBetTimes = document.getElementsByClassName("bottom-area")[0].getElementsByClassName("multiple-drop-menu")[0].getElementsByTagName("input")[0].value; // 倍數

	var bet = document.getElementsByClassName("bet-area")[0].getElementsByClassName("bottom-area")[0].getElementsByClassName("total")[0].getElementsByTagName("span")[1].innerHTML;
	// var mainOrder = getEle("mainOrder").value;
	// var mainOrderList = getEle("mainOrderList").value;
	getMainOrderListJSON();
	getMainOrderJSON();
	if (Object.keys(groupMainOrderListObj).length > 0 && checkBalance(bet)) {
		mainOrderListJson = groupMainOrderListObj;
		if (Array.isArray(groupMainOrderObj) == true) {
			var isRepeat = false;
			if (mainOrderListJson.orders != null && typeof mainOrderListJson.orders != "undefined") {
				for (var i = 0; i < mainOrderListJson.orders.length; i++) {
					for (var k = 0; k < groupMainOrderObj.length; k++) {
						mainOrderJson = groupMainOrderObj[k];
						if (mainOrderListJson.orders[i].betData != null && typeof mainOrderListJson.orders[i].betData != "undefined"
								&& mainOrderListJson.orders[i].betData == mainOrderJson.betData && mainOrderListJson.orders[i].moneyUnit == mainOrderJson.moneyUnit
								&& mainOrderListJson.orders[i].bonusSetRatio == mainOrderJson.bonusSetRatio
								&& mainOrderListJson.orders[i].mainId == mainOrderJson.mainId && mainOrderListJson.orders[i].localId == mainOrderJson.localId
								&& mainOrderListJson.orders[i].midId == mainOrderJson.midId && mainOrderListJson.orders[i].minAuthId == mainOrderJson.minAuthId) {
							groupMainOrderObj.splice(k,1);
							isRepeat = true;
						}
					}
				}
			}
			addMainOrder();
			if(isRepeat == true){
				showRemindBetDiv("你選擇的號碼已重複，系統自動放棄重複號碼");
			}
			
			
		} else {
			mainOrderJson = groupMainOrderObj;
			if (mainOrderListJson.orders != null && typeof mainOrderListJson.orders != "undefined") {
				for (var i = 0; i < mainOrderListJson.orders.length; i++) {
					if (mainOrderListJson.orders[i].betData != null && typeof mainOrderListJson.orders[i].betData != "undefined"
							&& mainOrderListJson.orders[i].betData == mainOrderJson.betData && mainOrderListJson.orders[i].moneyUnit == mainOrderJson.moneyUnit
							&& mainOrderListJson.orders[i].bonusSetRatio == mainOrderJson.bonusSetRatio && mainOrderListJson.orders[i].mainId == mainOrderJson.mainId
							&& mainOrderListJson.orders[i].localId == mainOrderJson.localId && mainOrderListJson.orders[i].midId == mainOrderJson.midId
							&& mainOrderListJson.orders[i].minAuthId == mainOrderJson.minAuthId) {
						// 彈窗
						var fun = function() {
							mainOrderListJson.orders[i].midOrders[0].noOfBetTimes = (parseInt(mainOrderListJson.orders[i].midOrders[0].noOfBetTimes) + parseInt(mainOrderJson.midOrders[0].noOfBetTimes))
									+ "";
							getEle("mainOrderList").value = JSON.stringify(mainOrderListJson);

							refreshMainOrderData();

							document.getElementsByClassName("bottom-area")[0].getElementsByTagName("select")[0].value = moneyUnit;
							document.getElementsByClassName("bottom-area")[0].getElementsByClassName("multiple-drop-menu")[0].getElementsByTagName("input")[0].value = noOfBetTimes;
						};
						showRemindBetDiv2("你選擇的號碼已存在，將直接進行倍數累加", fun);

						return;
					}
				}
			}
			addMainOrder();
			document.getElementsByClassName("bottom-area")[0].getElementsByTagName("select")[0].value = moneyUnit;
			document.getElementsByClassName("bottom-area")[0].getElementsByClassName("multiple-drop-menu")[0].getElementsByTagName("input")[0].value = noOfBetTimes;
			return;
		}
	} else {
		addMainOrder();
		document.getElementsByClassName("bottom-area")[0].getElementsByTagName("select")[0].value = moneyUnit;
		document.getElementsByClassName("bottom-area")[0].getElementsByClassName("multiple-drop-menu")[0].getElementsByTagName("input")[0].value = noOfBetTimes;
		return;
	}
}

// 添加追號單
function addMainOrder() {
	getMainOrderListJSON();
	if (Object.keys(groupMainOrderListObj).length == 0) {
		var allOrder = {};
		allOrder['orders'] = [];
		if (Array.isArray(groupMainOrderObj) == true) {
			for (var i = 0; i < groupMainOrderObj.length; i++) {
				allOrder['orders'].push(groupMainOrderObj[i]);
			}
		} else {
			allOrder['orders'].push(groupMainOrderObj);
		}
		getEle("mainOrderList").value = JSON.stringify(allOrder);
		groupMainOrderListObj = allOrder;
	} else {
		if (Array.isArray(groupMainOrderObj) == true) {
			for (var i = 0; i < groupMainOrderObj.length; i++) {
				groupMainOrderListObj['orders'].push(groupMainOrderObj[i]);
			}
		} else {
			groupMainOrderListObj['orders'].push(groupMainOrderObj);
		}

		getEle("mainOrderList").value = JSON.stringify(groupMainOrderListObj);
	}

	refreshMainOrderData();

}

// 是否投注追號單 跟多筆追號單
function checkGoBetArr() {
	var periodNumList = getPeriodNum();
	var mainOrderListJson = isJSON2(getEle("mainOrderList").value);
	if (mainOrderListJson && typeof periodNumList[0].date !== "undefined") {
		try {
			var resultMainOrder = checkMainOrderListTimes(mainOrderListJson, periodNumList[0].date);

			if (typeof resultMainOrder === "boolean") {
				if (resultMainOrder == true) {
					if (!openConfirmBet() && typeof mainOrderListJson.orders !== "undefined") {
						var text = "";
						var totalAmountMoney = 0;
						for (var i = 0; i < mainOrderListJson.orders.length; i++) {
							var amount = mainOrderListJson.orders[i].amount;
							var noOfBetTimes = mainOrderListJson.orders[i].midOrders[0].noOfBetTimes;
							var betData = mainOrderListJson.orders[i].betData;

							var minAuthId = mainOrderListJson.orders[i].minAuthId;
							var played_text = mainOrderListJson.orders[i].playedText;

							totalAmountMoney += times(parseFloat(amount), parseFloat(noOfBetTimes));

							text += "[" + played_text + "] ";
							if (betData.length > 20) {
								text += "<br> " + changeLotteryNumToText(minAuthId, betData) + "<br>";
							} else {
								text += changeLotteryNumToText(minAuthId, betData) + "<br>";
							}
						}

						getEle("ConfirmBetDiv").getElementsByClassName("btn-area")[0].getElementsByTagName("button")[0].onclick = function() {
							goBetArr();
						};
						getEle("ConfirmBetDiv").getElementsByClassName("way")[0].getElementsByTagName("span")[0].innerHTML = text;
						getEle("ConfirmBetDiv").getElementsByClassName("total")[0].getElementsByTagName("span")[0].innerHTML = divide(Math.floor(times(totalAmountMoney, 100)),
								100);
					}
				} else if (resultMainOrder == false) {
					showRemindBetDiv("投注失敗");
				}
			} else if (typeof resultMainOrder === "object") {
				showRemindBetDiv2("已超出中獎最高金額，是否以最高倍數下注", function() {
					groupMainOrderListObj = resultMainOrder;
					getEle("mainOrderList").value = JSON.stringify(resultMainOrder);
					refreshMainOrderData();
					setTimeout("checkGoBetArr()", 0);

				});
			}

		} catch (e) {
			console_Log(e);
		}
	} else {
		showRemindBetDiv("投注失敗");
	}
}

// 確認投注
function goBetArr() {
	try {
		enableLoadingPage();
		setTimeout('goBetArr2()', 10);
	} catch (e) {
		console_Log(e);
	} finally {
		setTimeout('disableLoadingPage()', 10);
	}
}

function goBetArr2() {
	var periodNumList = getPeriodNum();
	var mainOrderListStr = getEle("mainOrderList").value;
	if (mainOrderListStr != '' && isJSON(mainOrderListStr)) {
		var mainOrderListJson = JSON.parse(mainOrderListStr);
		if (mainOrderListJson.orders != null && typeof mainOrderListJson.orders != "undefined"
				&& checkBalance(document.getElementsByClassName("bottom")[0].getElementsByTagName("span")[2].innerHTML)) {
			try {

				var allOrder = {};
				allOrder['orders'] = [];
				for (var i = 0; i < mainOrderListJson.orders.length; i++) {
					var mainOrder = mergeOrder(mainOrderListJson.orders[i], periodNumList, '0', 0, false);
					if (mainOrder == false || mainOrder == null) {
						showRemindBetDiv("投注失敗");
						return false;
					}
					allOrder['orders'].push(mainOrder);
				}
				sendBettingAjax(JSON.stringify(allOrder)); // 送出下注資料
			} catch (e) {
				console_Log(e);
			}
		}
	}
}

// 是否投注追號單 多期
function checkMaioGoBetArr() {
	var tableName = "";
	if (getEle("NormalListArea").getElementsByTagName("tr").length <= 1 && getEle("AdvancedListArea").getElementsByTagName("tr").length <= 1) {
		return;
	}

	if (getEle("NormalListArea").getElementsByTagName("tr").length > 0) {
		tableName = "NormalListArea";
	}
	if (getEle("AdvancedListArea").getElementsByTagName("tr").length > 0) {
		tableName = "AdvancedListArea";
	}

	var orderType = 0;
	if (document.getElementsByName("c1")[0].checked == true) {
		orderType += 2;
	}
	if (document.getElementsByName("c1")[1].checked == true) {
		orderType += 1;
	}

	var mainOrderListJson = isJSON2(getEle("mainOrderList").value);
	var periodNumList = isJSON2(getEle("periodNumList").value);

	if (mainOrderListJson && periodNumList) {
		try {
			var resultperiodNum = checkMainOrderMaxNoOfTimes(mainOrderListJson, periodNumList);

			if (typeof resultperiodNum === "boolean") {
				if (resultperiodNum == true) {
					if (!openConfirmBet() && typeof mainOrderListJson.orders != "undefined" && periodNumList.length > 0) {
						var text = "";
						var totalAmountMoney = 0;
						for (var i = 0; i < mainOrderListJson.orders.length; i++) {

							var betData = changeLotteryNumToText(mainOrderListJson.orders[i].minAuthId, mainOrderListJson.orders[i].betData);

							var played_text = mainOrderListJson.orders[i].playedText;

							text += "[" + played_text + "] ";
							if (betData.length > 20) {
								text += "<br> " + betData + "<br>";
							} else {
								text += betData + "<br>";
							}
						}

						for (var j = 0; j < periodNumList.length; j++) {
							totalAmountMoney += times(parseFloat(periodNumList[j].totalAmount), parseFloat(periodNumList[j].noOfBetTimes));
						}

						getEle("ConfirmBetDiv").getElementsByClassName("btn-area")[0].getElementsByTagName("button")[0].onclick = function() {
							multiPeriodNum(orderType);
						};
						getEle("ConfirmBetDiv").getElementsByClassName("way")[0].getElementsByTagName("span")[0].innerHTML = text;
						getEle("ConfirmBetDiv").getElementsByClassName("total")[0].getElementsByTagName("span")[0].innerHTML = divide(Math.floor(times(totalAmountMoney, 100)),
								100);
					}
				} else if (resultperiodNum == false) {
					showRemindBetDiv("投注失敗");
				}
			} else if (typeof resultperiodNum === "object") {
				showRemindBetDiv2(
						"已超出中獎最高金額，是否以最高倍數下注",
						function() {
							setTimeout(function() {
								enableLoadingPage();
							}, 0);
							getEle("periodNumList").value = JSON.stringify(resultperiodNum);

							var noOfBetTimesAtt = [];
							var objList = [];
							if (tableName == "AdvancedListArea") {
								var periodList = getPeriodNum2(advancedSettingData["startPeriodNum"], resultperiodNum.length);
								for (var i = 0; i < periodList.length; i++) {
									var obj = {};
									obj.periodNum = periodList[i]["periodNum"];
									obj.date = periodList[i]["date"];
									obj.expS = periodList[i]["expS"];
									obj.noOfBetTimes = resultperiodNum[i]["noOfBetTimes"];
									objList.push(obj);
								}
							} else {
								for (var i = 0; i < resultperiodNum.length; i++) {
									noOfBetTimesAtt.push(resultperiodNum[i].noOfBetTimes);
								}
							}
							setTimeout("showSetMainOrderInto()", 0);

							setTimeout(function() {
								if ((orderType & 1) > 0) {
									document.getElementsByName("c1")[1].checked = true;
								}
								if ((orderType & 2) > 0) {
									document.getElementsByName("c1")[0].checked = true;
								}
							}, 0);
							if (tableName == "AdvancedListArea") {
								setTimeout(
										function() {
											var buttonEleList = getEle("AdvancedAreaDiv").getElementsByTagName("div")[4]
													.getElementsByClassName("tab-area-2")[0].getElementsByTagName("button");
											var startPeriodNum = getPeriodNum2(advancedSettingData["startPeriodNum"], 1);
											startPeriodNum = startPeriodNum.length == 1 ? typeof startPeriodNum[0].periodNum !== "undeinfed" ? startPeriodNum[0].periodNum
													: advancedSettingData["startPeriodNum"]
													: advancedSettingData["startPeriodNum"];

											showOpenTabAdvancedArea();
											AdvancedMainOrderPeriodNumList = objList;
											refreshSetAdvancedMainOrderTable();

											var settingTypecheckBoxList2 = document.getElementsByName("settingTypecheckBox");
											if (typeof advancedSettingData.type != "undefined" && settingTypecheckBoxList2.length == 2) {
												getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("select")[0].value = startPeriodNum;
												getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("p")[1]
														.getElementsByTagName("input")[0].value = toInt(advancedSettingData["dPeriodNumLength"]);
												getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("p")[2]
														.getElementsByTagName("input")[0].value = toInt(advancedSettingData["dNoOfBetTimes"]);
												advancedSettingData["startPeriodNum"] = startPeriodNum;
												settingTypecheckBoxList2[0].checked = false;
												settingTypecheckBoxList2[1].checked = false;
												switch (advancedSettingData.type) {
												case "1":
													buttonEleList[0].click();
													settingTypecheckBoxList2[0].checked = true;
													settingTypecheckBoxList2[0].parentNode.getElementsByTagName("input")[1].value = toInt(advancedSettingData["setDefaultPeruidNumSize"]);
													settingTypecheckBoxList2[0].parentNode.getElementsByTagName("input")[2].value = toInt(advancedSettingData["setDefaultNoOfBetTimes"]);
													settingTypecheckBoxList2[0].parentNode.getElementsByTagName("select")[0].value = advancedSettingData["formulaType"];

													return;
												case "2":
													buttonEleList[0].click();
													settingTypecheckBoxList2[1].checked = true;
													settingTypecheckBoxList2[1].parentNode.getElementsByTagName("input")[1].value = toInt(advancedSettingData["setDefaultPeruidNumSizeToDefaultNoOfBetTimes"]);
													settingTypecheckBoxList2[1].parentNode.getElementsByTagName("input")[2].value = toInt(advancedSettingData["setLastNoOfBetTimes"]);

													return;
												case "3":
													buttonEleList[1].click();
													settingTypecheckBoxList2[0].checked = true;
													settingTypecheckBoxList2[0].parentNode.getElementsByTagName("input")[1].value = toInt(advancedSettingData["setDefaultwinningMoney"]);

													return;
												case "4":
													buttonEleList[1].click();
													settingTypecheckBoxList2[1].checked = true;
													settingTypecheckBoxList2[1].parentNode.getElementsByTagName("input")[1].value = toInt(advancedSettingData["setFalstPeruidNumSizeToDefaultWinningMoney"]);
													settingTypecheckBoxList2[1].parentNode.getElementsByTagName("input")[2].value = toInt(advancedSettingData["setDefaultWinningMoney"]);
													settingTypecheckBoxList2[1].parentNode.getElementsByTagName("input")[3].value = toInt(advancedSettingData["setLastWinningMoney"]);

													return;
												case "5":
													buttonEleList[2].click();
													settingTypecheckBoxList2[0].checked = true;
													settingTypecheckBoxList2[0].parentNode.getElementsByTagName("input")[1].value = toInt(advancedSettingData["setDefaultwinningPercentage"]);

													return;
												case "6":
													buttonEleList[2].click();
													settingTypecheckBoxList2[1].checked = true;
													settingTypecheckBoxList2[1].parentNode.getElementsByTagName("input")[1].value = toInt(advancedSettingData["setFalstPeruidNumSizeToDefaultWinningPercentage"]);
													settingTypecheckBoxList2[1].parentNode.getElementsByTagName("input")[2].value = toInt(advancedSettingData["setDefaultWinningPercentage"]);
													settingTypecheckBoxList2[1].parentNode.getElementsByTagName("input")[3].value = toInt(advancedSettingData["setLastWinningPercentage"]);

													return;
												default:
													return;

												}
											}
										}, 0);

								setTimeout(function() {
									disableLoadingPage();
								}, 0);

							} else {
								setTimeout(function() {
									;
									refreshSetMainOrderTable(noOfBetTimesAtt.length, noOfBetTimesAtt);
								}, 0);
								setTimeout(function() {
									disableLoadingPage();
								}, 0);
							}

						});
			}

		} catch (e) {
			console_Log(e);
		}

	}
}

// 追號投注
function multiPeriodNum(orderType) {
	try {
		enableLoadingPage();
		setTimeout(function() {
			multiPeriodNum2(orderType);
		}, 10);
	} catch (e) {
		console_Log(e);
	} finally {
		setTimeout('disableLoadingPage()', 10);
	}
}

function multiPeriodNum2(orderType) {
	// console.log(new Date().getTime());
	var periodNumIndex = 5;
	var periodNumList = JSON.parse(getEle("periodNumList").value);
	var mainOrderListStr = getEle("mainOrderList").value;
	if (mainOrderListStr != '' && isJSON(mainOrderListStr)) {
		var mainOrderListJson = JSON.parse(mainOrderListStr);
		if (mainOrderListJson.orders != null && typeof mainOrderListJson.orders != "undefined"
				&& checkBalance(getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[2].getElementsByTagName("span")[0].innerHTML)) {
			try {
				var objList = [];
				for (var i = 0; i < periodNumList.length; i++) {
					if (periodNumList[i].noOfBetTimes > 0) {
						objList.push(periodNumList[i]);
					}
				}

				var allOrder = {};
				allOrder['orders'] = [];
				for (var i = 0; i < mainOrderListJson.orders.length; i++) {
					var a = new Date().getTime();
					var mainOrder = mergeOrder(mainOrderListJson.orders[i], objList, "" + orderType, 1, false);
					if (mainOrder == false || mainOrder == null) {
						showRemindBetDiv("投注失敗");
						return false;
					}
					allOrder['orders'].push(mainOrder);

				}
				sendBettingAjax(JSON.stringify(allOrder)); // 送出下注資料

			} catch (e) {
				console_Log(e);
			}
		}
	}
}

// 顯示追號單Table
function showMainOrderList() {
	document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[0].disabled = true;
	var tableStr = "";
	var tableTh;
	setInnerHTML(document.getElementsByClassName("left")[0].getElementsByClassName("list-area")[0].getElementsByTagName("tbody")[0], "");
	if (getEle("localId").value == "26") {
		tableTh = '<tr><th>玩法与投注号码</th><th>奖金组</th><th>注数</th><th>金额</th><th>操作</th></tr>';
	}
	else{
		tableTh = '<tr><th>玩法与投注号码</th><th>奖金组</th><th>资金模式</th><th>注数</th><th>倍数</th><th>金额</th><th>操作</th></tr>';
	}
	tableStr += "" + tableTh;

	getMainOrderListJSON();
	if (Object.keys(groupMainOrderListObj).length > 0) {
		var mainOrderListJson = groupMainOrderListObj;
		var totalAmount = 0;
		var totalNoOfBet = 0;
		var moneyTypeList = document.getElementsByClassName("bottom-area")[0].getElementsByTagName("option");
		var lotterysJson = JSON.parse(Strings.decode(getEle("lotterys").value)).AllLottery;
		var isMainSetting = true;
		//document.getElementsByClassName("left")[0].getElementsByClassName("list-area")[0].getElementsByTagName("tbody")[0].appendChild(tableTh);
		
		setInnerHTML(document.getElementsByClassName("left")[0].getElementsByClassName("list-area")[0].getElementsByTagName("tbody")[0], tableStr);
		

		var moneyTypeObj = {};
		for (var i = 0; i < moneyTypeList.length; i++) {
			moneyTypeObj[moneyTypeList[i].value] = moneyTypeList[i].innerHTML;
		}

		for (var i = 0; i < mainOrderListJson.orders.length; i++) {

			var betData = changeLotteryNumToText(mainOrderListJson.orders[i].minAuthId, mainOrderListJson.orders[i].betData);
			var moneyUnit = mainOrderListJson.orders[i].moneyUnit;
			var bonusSetRatio = mainOrderListJson.orders[i].bonusSetRatio;
			var amount = mainOrderListJson.orders[i].amount;
			var noOfBet = mainOrderListJson.orders[i].midOrders[0].noOfBet;
			var noOfBetTimes = mainOrderListJson.orders[i].midOrders[0].noOfBetTimes;
			var amountMoney = divide(Math.floor(times(parseFloat(amount), parseFloat(noOfBetTimes), 100)), 100);

			var played_text = mainOrderListJson.orders[i].playedText;
			if (betData.length > 15) {
				betData = betData.substring(0, 15) + "...";
			}

			if (getEle("localId").value == "26") {
				tableStr += '<tr class=""><td>[' + played_text + '] ' + betData + '</td><td>' + bonusSetRatio + '</td><td>' + noOfBet + '注</td>'
						+'<td>' + amountMoney + '元</td><td><a href="javascript:void(0);" onclick="deleteMainOrder(' + i + ');">删除</a></td></tr>';

			} else {
				tableStr += '<tr class=""><td>[' + played_text + '] ' + betData + '</td><td>' + bonusSetRatio + '</td><td>' + moneyTypeObj[moneyUnit] + '</td><td>' + noOfBet
						+ '注</td><td>' + noOfBetTimes + '倍</td><td>' + amountMoney + '元</td><td><a href="javascript:void(0);" onclick="deleteMainOrder(' + i
						+ ');">删除</a></td></tr>';

			}

			totalAmount = plus(totalAmount, amountMoney);
			totalNoOfBet = plus(totalNoOfBet, parseInt(noOfBet));

			if (parseFloat(amount) < parseFloat(mainOrderListJson.orders[i].baseBet)) {
				isMainSetting = false;
			}
		}

		setInnerHTML(document.getElementsByClassName("left")[0].getElementsByClassName("list-area")[0].getElementsByTagName("tbody")[0], tableStr);

		document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[1].disabled = false;
		if (isMainSetting == true) {
			document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[2].disabled = false;
		} else {
			document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[2].disabled = true;
		}
		document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[1].title = "";
		document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[2].title = "";

		document.getElementsByClassName("bottom")[0].getElementsByTagName("span")[0].innerHTML = "" + mainOrderListJson.orders.length;
		document.getElementsByClassName("bottom")[0].getElementsByTagName("span")[1].innerHTML = "" + totalNoOfBet;
		document.getElementsByClassName("bottom")[0].getElementsByTagName("span")[2].innerHTML = "" + totalAmount;
		document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[0].disabled = false;
		if (totalAmount > parseFloat(getEle("balance").value.substring(0, getEle("balance").value.indexOf(".") + 3))) {
			document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[1].disabled = true;
			document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[2].disabled = true;
			document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[1].title = "餘額不足";
			document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[2].title = "餘額不足";
		}

	} else {
		document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[0].disabled = true;
		document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[1].disabled = true;
		document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[2].disabled = true;
		document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[0].title = "請至少選擇一注投注號碼!";
		document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[1].title = "請至少選擇一注投注號碼!";
		document.getElementsByClassName("bottom")[0].getElementsByClassName("btn-area")[0].getElementsByTagName("button")[2].title = "請至少選擇一注投注號碼!";

		setInnerHTML(document.getElementsByClassName("left")[0].getElementsByClassName("list-area")[0].getElementsByTagName("tbody")[0], tableStr);

		document.getElementsByClassName("bottom")[0].getElementsByTagName("span")[0].innerHTML = "0";
		document.getElementsByClassName("bottom")[0].getElementsByTagName("span")[1].innerHTML = "0";
		document.getElementsByClassName("bottom")[0].getElementsByTagName("span")[2].innerHTML = "0";
	}
}

// 清空資料 追號單 跟畫面
function clearMianOrder() {
	getEle("mainOrderList").value = "";
	getEle("mainOrder").value = "";
	getEle("periodNumList").value = "";

	groupMainOrderObj = {};
	groupMainOrderListObj = {};

	clearBallToInit();
}

function refreshMainOrderData() {
	showMainOrderList();
	clearBallToInit();
}

// 刪除追號單
function deleteMainOrder(index) {
	getMainOrderListJSON();
	var mainOrderListJson = groupMainOrderListObj;
	if (mainOrderListJson.orders[index] != null && typeof mainOrderListJson.orders[index] != "undeinfed") {
		if (mainOrderListJson.orders.length == 1) {
			getEle("mainOrderList").value = "";
			groupMainOrderListObj = {};
		} else {
			mainOrderListJson.orders.splice(index, 1);
			// mainOrderListJson.orders =
			// mainOrderListJson.orders.splice(index,1);
			getEle("mainOrderList").value = JSON.stringify(mainOrderListJson);
		}
		showMainOrderList();
	}

}
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------
// 顯示追號設定
function showSetMainOrderInto() {
	noOfBetTimesArrayList = [];
	getMainOrderListJSON();
	if (!openBetAdd() && Object.keys(groupMainOrderListObj).length > 0) {
		document.getElementsByName("c1")[1].onclick = function() {
			if (this.checked == true) {
				if (document.getElementsByName("c1")[1] != null && document.getElementsByName("c1")[0] != null) {
					document.getElementsByName("c1")[1].checked = true;
					document.getElementsByName("c1")[0].checked = true;
				}
			}

		};
		document.getElementsByName("c1")[0].onclick = function() {
			if (this.checked == false && document.getElementsByName("c1")[1] != null) {
				document.getElementsByName("c1")[1].checked = false;
			}
		};

		document.getElementsByName("c1")[0].checked = false;
		document.getElementsByName("c1")[1].checked = false;

		getEle("changeNormalBtn").onclick = function() {
			showOpenTabNormalArea();
		};

		getEle("changeAdvancedBtn").onclick = function() {
			showOpenTabAdvancedArea();
		};

		showOpenTabNormalArea();
	}
}

// 普通追號
function showOpenTabNormalArea() {
	getMainOrderListJSON();
	if (!openTabNormalArea() && Object.keys(groupMainOrderListObj).length > 0) {
		getEle("periodNumList").value = "";
		document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = getEle("balance").value.substring(0, getEle("balance").value
				.indexOf(".") + 3);
		getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[3].getElementsByTagName("span")[0].innerHTML = getEle("balance").value.substring(0,
				getEle("balance").value.indexOf(".") + 3);

		getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[0].getElementsByTagName("span")[0].innerHTML = 0; // 期數
		getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[1].getElementsByTagName("span")[0].innerHTML = 0;// 注數
		getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[2].getElementsByTagName("span")[0].innerHTML = 0; // 總下注金額

		getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[0].className = "";
		getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[1].className = "";
		getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[2].className = "";
		getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[3].className = "";
		getEle("NormalAreaDiv").getElementsByTagName("span")[1].getElementsByTagName("input")[0].value = "";

		getEle("NormalAreaDiv").getElementsByClassName("multiple")[0].getElementsByTagName("input")[0].value = 1;

		getEle("BetAddDiv").getElementsByClassName("btn-add-confirm")[0].disabled = true;

		getEle("NormalListArea").display = "none";
		setInnerHTML(getEle("NormalListArea").getElementsByTagName("tbody")[0], "");
		setInnerHTML(getEle("AdvancedListArea").getElementsByTagName("tbody")[0], "");
		getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[0].onclick = function() {
			refreshSetMainOrderTableChecked(5, true);
		};
		getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[1].onclick = function() {
			refreshSetMainOrderTableChecked(10, true);
		};
		getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[2].onclick = function() {
			refreshSetMainOrderTableChecked(15, true);
		};
		getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[3].onclick = function() {
			refreshSetMainOrderTableChecked(20, true);
		};
		getEle("NormalAreaDiv").getElementsByTagName("span")[1].getElementsByTagName("input")[0].onchange = function() {
			var val = toInt(this.value);
			if (val <= 0) {
				this.value = 1;
			}
			refreshSetMainOrderTableChecked(this.value, true);
		};
		getEle("NormalAreaDiv").getElementsByClassName("multiple")[0].getElementsByTagName("input")[0].onchange = function() {
			var val = toInt(this.value);
			if (val <= 0) {
				this.value = 1;
			} else if (val > groupMaxNoOfBetTimes) {
				this.value = groupMaxNoOfBetTimes;
			} else {
				this.value = val;
			}
			updateNOOfBetTimes(this.value);
		};

		getEle("NormalAreaDiv").getElementsByClassName("multiple")[0].getElementsByTagName("input")[0].onkeyup = function() {

			document.getElementById("multipleDropMenuContent2").classList.remove("show");
			var val = toInt(this.value);
			if (val > groupMaxNoOfBetTimes) {
				this.value = groupMaxNoOfBetTimes;
				updateNOOfBetTimes(this.value);
			} else if (val > 0) {
				this.value = val;
				updateNOOfBetTimes(this.value);
			}
		};

		var liList = getEle("NormalAreaDiv").getElementsByClassName("multiple-drop-content2")[0].getElementsByTagName("li");
		for (var p = 0; p < liList.length; p++) {
			liList[p].onclick = function() {
				getEle("NormalAreaDiv").getElementsByClassName("multiple")[0].getElementsByTagName("input")[0].value = this.innerHTML;
				updateNOOfBetTimes(this.innerHTML);
				multipleDropMenu2();
			};
		}
	}

	getEle("NormalAreaDiv").getElementsByClassName("multiple")[0].onmouseover = function() {
		document.getElementById("multipleDropMenuContent2").classList.add("show");
	}

	getEle("NormalAreaDiv").getElementsByClassName("multiple")[0].onmouseout = function() {
		document.getElementById("multipleDropMenuContent2").classList.remove("show");
	}

	getEle("multipleDropMenuContent2").onmouseover = function() {
		document.getElementById("multipleDropMenuContent2").classList.add("show");
	}

	getEle("multipleDropMenuContent2").onmouseout = function() {
		document.getElementById("multipleDropMenuContent2").classList.remove("show");
	}

}
var AdvancedMainOrderPeriodNumList = [];
var advancedSettingData = {};
// 高級追號
function showOpenTabAdvancedArea() {
	AdvancedMainOrderPeriodNumList = [];
	getMainOrderListJSON();
	if (!openTabAdvancedArea() && Object.keys(groupMainOrderListObj).length > 0) {
		getEle("periodNumList").value = "";
		document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = getEle("balance").value.substring(0, getEle("balance").value
				.indexOf(".") + 3);
		getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[3].getElementsByTagName("span")[0].innerHTML = getEle("balance").value.substring(0,
				getEle("balance").value.indexOf(".") + 3);

		getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[0].getElementsByTagName("span")[0].innerHTML = 0; // 期數
		getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[1].getElementsByTagName("span")[0].innerHTML = 0;// 注數
		getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[2].getElementsByTagName("span")[0].innerHTML = 0; // 總下注金額

		getEle("BetAddDiv").getElementsByClassName("btn-add-confirm")[0].disabled = true;

		getEle("AdvancedAreaDiv").display = "none";
		setInnerHTML(getEle("NormalListArea").getElementsByTagName("tbody")[0], "");
		setInnerHTML(getEle("AdvancedListArea").getElementsByTagName("tbody")[0], "");

		var periodNumSelect = getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("select")[0];

		periodNumSelect.innerHTML = "";

		var periodNumList = getPeriodNum(10000);
		for (var i = 0; i < periodNumList.length; i++) {
			if (typeof periodNumList[i].periodNum !== "undefined" && periodNumList[i].periodNum != null) {
				if (parseInt(periodNumList[i].periodNum) == parseInt(getEle("nowNo").value)) {
					periodNumSelect.options[i] = new Option(periodNumList[i].periodNum + " 當前期號", periodNumList[i].periodNum);
				} else {
					periodNumSelect.options[i] = new Option(periodNumList[i].periodNum, periodNumList[i].periodNum);
				}

			}
		}
		advancedSettingData["startPeriodNum"] = periodNumSelect.value;

		periodNumSelect.onchange = function() {
			advancedSettingData["startPeriodNum"] = this.value;
		}

		getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("p")[1].innerHTML = '<span>追号期数：</span><input type="text" value="" >期(最多可以追' + periodNumList.length
				+ '期）';

		getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("p")[1].getElementsByTagName("input")[0].onkeyup = function() {
			var val = toInt(this.value);
			if (val > 0) {
				if (val > periodNumList.length) {
					this.value = periodNumList.length;
					advancedSettingData["dPeriodNumLength"] = this.value;
				} else {
					this.value = val;
					advancedSettingData["dPeriodNumLength"] = this.value;
				}

			}
		}
		getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("p")[1].getElementsByTagName("input")[0].onchange = function() {
			var val = toInt(this.value);

			if (val > periodNumList.length) {
				this.value = periodNumList.length;
			} else {
				this.value = val;
			}
			advancedSettingData["dPeriodNumLength"] = this.value;

		}
		getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("p")[2].getElementsByTagName("input")[0].value = "";
		getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("p")[2].getElementsByTagName("input")[0].onkeyup = function() {
			var val = toInt(this.value);
			if (val > groupMaxNoOfBetTimes) {
				this.value = groupMaxNoOfBetTimes;
				advancedSettingData["dNoOfBetTimes"] = this.value;
			} else if (val > 0) {
				this.value = val;
				advancedSettingData["dNoOfBetTimes"] = this.value;
			}
		}
		getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("p")[2].getElementsByTagName("input")[0].onchange = function() {
			var val = toInt(this.value);
			if (val > groupMaxNoOfBetTimes) {
				val = groupMaxNoOfBetTimes;
			}
			this.value = val;
			advancedSettingData["dNoOfBetTimes"] = this.value;
		}

		var buttonEleList = getEle("AdvancedAreaDiv").getElementsByTagName("div")[4].getElementsByClassName("tab-area-2")[0].getElementsByTagName("button");

		buttonEleList[0].onclick = function() {
			var buttonEleList = getEle("AdvancedAreaDiv").getElementsByTagName("div")[4].getElementsByClassName("tab-area-2")[0].getElementsByTagName("button");
			for (var j = 0; j < buttonEleList.length; j++) {
				buttonEleList[j].classList.remove("active");
			}
			this.classList.add("active");

			getEle("AdvancedAreaDiv").getElementsByTagName("div")[4].getElementsByClassName("tab-content")[0].getElementsByTagName("p")[0].innerHTML = '<input type="checkbox" name = "settingTypecheckBox" value = 1> 每隔<input type="text" value = "0" onkeyup="this.value = toInt(this.value);">期 倍數<select><option value = "0">加</option><option value = "1">乘</option></select><input type="text" value = "0" onkeyup="this.value = toInt(this.value);">';
			getEle("AdvancedAreaDiv").getElementsByTagName("div")[4].getElementsByClassName("tab-content")[0].getElementsByTagName("p")[1].innerHTML = '<input type="checkbox" name = "settingTypecheckBox" value = 2> 前<input type="text" value = "0" onkeyup="this.value = toInt(this.value);">期 倍數 = 起始倍數, 之后倍數 = <input type="text" value = "0" onkeyup="this.value = toInt(this.value);if(this.value > groupMaxNoOfBetTimes){this.value = groupMaxNoOfBetTimes;}">';

			var settingTypecheckBoxList = document.getElementsByName("settingTypecheckBox");

			for (var k = 0; k < settingTypecheckBoxList.length; k++) {
				settingTypecheckBoxList[k].onclick = function() {
					var settingTypecheckBoxList2 = document.getElementsByName("settingTypecheckBox");
					for (var j = 0; j < settingTypecheckBoxList2.length; j++) {
						settingTypecheckBoxList2[j].checked = false;
					}
					this.checked = true;
				}
			}

		}
		buttonEleList[1].onclick = function() {
			var buttonEleList = getEle("AdvancedAreaDiv").getElementsByTagName("div")[4].getElementsByClassName("tab-area-2")[0].getElementsByTagName("button");
			for (var j = 0; j < buttonEleList.length; j++) {
				buttonEleList[j].classList.remove("active");
			}
			this.classList.add("active");

			getEle("AdvancedAreaDiv").getElementsByTagName("div")[4].getElementsByClassName("tab-content")[0].getElementsByTagName("p")[0].innerHTML = '<input type="checkbox" name = "settingTypecheckBox" value = 3> 預期盈利金額 >= <input type="text" class = "width-70" value = "0" onkeyup="this.value = toInt(this.value);">元';
			getEle("AdvancedAreaDiv").getElementsByTagName("div")[4].getElementsByClassName("tab-content")[0].getElementsByTagName("p")[1].innerHTML = '<input type="checkbox" name = "settingTypecheckBox" value = 4> 前<input type="text" value = "0" onkeyup="this.value = toInt(this.value);">期 預期盈利金額 >= <input type="text" class = "width-70" value = "0" onkeyup="this.value = toInt(this.value);">元, 之后 >= <input type="text" class = "width-70" value = "0" onkeyup="this.value = toInt(this.value);">元';

			var settingTypecheckBoxList = document.getElementsByName("settingTypecheckBox");

			for (var k = 0; k < settingTypecheckBoxList.length; k++) {
				settingTypecheckBoxList[k].onclick = function() {
					var settingTypecheckBoxList2 = document.getElementsByName("settingTypecheckBox");
					for (var j = 0; j < settingTypecheckBoxList2.length; j++) {
						settingTypecheckBoxList2[j].checked = false;
					}
					this.checked = true;
				}
			}
		}
		buttonEleList[2].onclick = function() {
			var buttonEleList = getEle("AdvancedAreaDiv").getElementsByTagName("div")[4].getElementsByClassName("tab-area-2")[0].getElementsByTagName("button");
			for (var j = 0; j < buttonEleList.length; j++) {
				buttonEleList[j].classList.remove("active");
			}
			this.classList.add("active");

			getEle("AdvancedAreaDiv").getElementsByTagName("div")[4].getElementsByClassName("tab-content")[0].getElementsByTagName("p")[0].innerHTML = '<input type="checkbox" name = "settingTypecheckBox" value = 5> 預期盈利利率 >= <input type="text" value = "0" onkeyup="this.value = toInt(this.value);">％';
			getEle("AdvancedAreaDiv").getElementsByTagName("div")[4].getElementsByClassName("tab-content")[0].getElementsByTagName("p")[1].innerHTML = '<input type="checkbox" name = "settingTypecheckBox" value = 6> 前<input type="text" value = "0" onkeyup="this.value = toInt(this.value);">期 預期盈利利率 >= <input type="text" value = "0" onkeyup="this.value = toInt(this.value);">％, 之后 >= <input type="text" value = "0" onkeyup="this.value = toInt(this.value);">％';

			var settingTypecheckBoxList = document.getElementsByName("settingTypecheckBox");

			for (var k = 0; k < settingTypecheckBoxList.length; k++) {
				settingTypecheckBoxList[k].onclick = function() {
					var settingTypecheckBoxList2 = document.getElementsByName("settingTypecheckBox");
					for (var j = 0; j < settingTypecheckBoxList2.length; j++) {
						settingTypecheckBoxList2[j].checked = false;
					}
					this.checked = true;
				}
			}
		}

		buttonEleList[0].click();

		getEle("AdvancedAreaDiv").getElementsByTagName("div")[0].getElementsByTagName("button")[3].onclick = function() {
			var start
			var startPeriodNum = toInt(getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("p")[0].getElementsByTagName("select")[0].value);
			var periodNumLength = toInt(getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("p")[1].getElementsByTagName("input")[0].value);
			var defaultNoOfBetTimes = toInt(getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("p")[2].getElementsByTagName("input")[0].value);
			if (periodNumLength > 0 && defaultNoOfBetTimes > 0) {
				var settingTypecheckBoxList2 = document.getElementsByName("settingTypecheckBox");
				for (var i = 0; i < settingTypecheckBoxList2.length; i++) {
					if (settingTypecheckBoxList2[i].checked == true) {
						switch (settingTypecheckBoxList2[i].value) {
						case "1":
							var setDefaultPeruidNumSize = toInt(settingTypecheckBoxList2[i].parentNode.getElementsByTagName("input")[1].value);
							var setDefaultNoOfBetTimes = toInt(settingTypecheckBoxList2[i].parentNode.getElementsByTagName("input")[2].value);
							var formulaType = toInt(settingTypecheckBoxList2[i].parentNode.getElementsByTagName("select")[0].value);
							advancedSettingData["type"] = "1";
							advancedSettingData["setDefaultPeruidNumSize"] = setDefaultPeruidNumSize;
							advancedSettingData["setDefaultNoOfBetTimes"] = setDefaultNoOfBetTimes;
							advancedSettingData["formulaType"] = formulaType;

							AdvancedMainOrderPeriodNumList = betTimesByDoubleType1(groupMainOrderListObj.orders[0], startPeriodNum, periodNumLength, defaultNoOfBetTimes,
									setDefaultPeruidNumSize, formulaType, setDefaultNoOfBetTimes);
							var resultperiodNum = checkMainOrderMaxNoOfTimes(groupMainOrderListObj, AdvancedMainOrderPeriodNumList);
							if (typeof resultperiodNum === "object") {
								AdvancedMainOrderPeriodNumList = resultperiodNum;
							}
							refreshSetAdvancedMainOrderTable();
							return;
						case "2":
							var setDefaultPeruidNumSizeToDefaultNoOfBetTimes = toInt(settingTypecheckBoxList2[i].parentNode.getElementsByTagName("input")[1].value);
							var setLastNoOfBetTimes = toInt(settingTypecheckBoxList2[i].parentNode.getElementsByTagName("input")[2].value);

							advancedSettingData["type"] = "2";
							advancedSettingData["setDefaultPeruidNumSizeToDefaultNoOfBetTimes"] = setDefaultPeruidNumSizeToDefaultNoOfBetTimes;
							advancedSettingData["setLastNoOfBetTimes"] = setLastNoOfBetTimes;

							AdvancedMainOrderPeriodNumList = betTimesByDoubleType2(groupMainOrderListObj.orders[0], startPeriodNum, periodNumLength, defaultNoOfBetTimes,
									setDefaultPeruidNumSizeToDefaultNoOfBetTimes, setLastNoOfBetTimes);
							var resultperiodNum = checkMainOrderMaxNoOfTimes(groupMainOrderListObj, AdvancedMainOrderPeriodNumList);
							if (typeof resultperiodNum === "object") {
								AdvancedMainOrderPeriodNumList = resultperiodNum;
							}
							refreshSetAdvancedMainOrderTable();
							return;
						case "3":
							var setDefaultwinningMoney = toInt(settingTypecheckBoxList2[i].parentNode.getElementsByTagName("input")[1].value);

							advancedSettingData["type"] = "3";
							advancedSettingData["setDefaultwinningMoney"] = setDefaultwinningMoney;

							AdvancedMainOrderPeriodNumList = betTimesByWinMoneyType1(groupMainOrderListObj.orders[0], startPeriodNum, periodNumLength, defaultNoOfBetTimes,
									setDefaultwinningMoney);
							var resultperiodNum = checkMainOrderMaxNoOfTimes(groupMainOrderListObj, AdvancedMainOrderPeriodNumList);
							if (typeof resultperiodNum === "object") {
								AdvancedMainOrderPeriodNumList = resultperiodNum;
							}
							refreshSetAdvancedMainOrderTable();
							return;
						case "4":
							var setFalstPeruidNumSizeToDefaultWinningMoney = toInt(settingTypecheckBoxList2[i].parentNode.getElementsByTagName("input")[1].value);
							var setDefaultWinningMoney = toInt(settingTypecheckBoxList2[i].parentNode.getElementsByTagName("input")[2].value);
							var setLastWinningMoney = toInt(settingTypecheckBoxList2[i].parentNode.getElementsByTagName("input")[3].value);

							advancedSettingData["type"] = "4";
							advancedSettingData["setFalstPeruidNumSizeToDefaultWinningMoney"] = setFalstPeruidNumSizeToDefaultWinningMoney;
							advancedSettingData["setDefaultWinningMoney"] = setDefaultWinningMoney;
							advancedSettingData["setLastWinningMoney"] = setLastWinningMoney;

							AdvancedMainOrderPeriodNumList = betTimesByWinMoneyType2(groupMainOrderListObj.orders[0], startPeriodNum, periodNumLength, defaultNoOfBetTimes,
									setFalstPeruidNumSizeToDefaultWinningMoney, setDefaultWinningMoney, setLastWinningMoney);
							var resultperiodNum = checkMainOrderMaxNoOfTimes(groupMainOrderListObj, AdvancedMainOrderPeriodNumList);
							if (typeof resultperiodNum === "object") {
								AdvancedMainOrderPeriodNumList = resultperiodNum;
							}
							refreshSetAdvancedMainOrderTable();
							return;
						case "5":
							var setDefaultwinningPercentage = toInt(settingTypecheckBoxList2[i].parentNode.getElementsByTagName("input")[1].value);

							advancedSettingData["type"] = "5";
							advancedSettingData["setDefaultwinningPercentage"] = setDefaultwinningPercentage;

							AdvancedMainOrderPeriodNumList = betTimesByWinRatioType1(groupMainOrderListObj.orders[0], startPeriodNum, periodNumLength, defaultNoOfBetTimes,
									setDefaultwinningPercentage);
							var resultperiodNum = checkMainOrderMaxNoOfTimes(groupMainOrderListObj, AdvancedMainOrderPeriodNumList);
							if (typeof resultperiodNum === "object") {
								AdvancedMainOrderPeriodNumList = resultperiodNum;
							}
							refreshSetAdvancedMainOrderTable();
							return;
						case "6":
							var setFalstPeruidNumSizeToDefaultWinningPercentage = toInt(settingTypecheckBoxList2[i].parentNode.getElementsByTagName("input")[1].value);
							var setDefaultWinningPercentage = toInt(settingTypecheckBoxList2[i].parentNode.getElementsByTagName("input")[2].value);
							var setLastWinningPercentage = toInt(settingTypecheckBoxList2[i].parentNode.getElementsByTagName("input")[3].value);

							advancedSettingData["type"] = "6";
							advancedSettingData["setFalstPeruidNumSizeToDefaultWinningPercentage"] = setFalstPeruidNumSizeToDefaultWinningPercentage;
							advancedSettingData["setDefaultWinningPercentage"] = setDefaultWinningPercentage;
							advancedSettingData["setLastWinningPercentage"] = setLastWinningPercentage;

							AdvancedMainOrderPeriodNumList = betTimesByWinRatioType2(groupMainOrderListObj.orders[0], startPeriodNum, periodNumLength, defaultNoOfBetTimes,
									setFalstPeruidNumSizeToDefaultWinningPercentage, setDefaultWinningPercentage, setLastWinningPercentage);
							var resultperiodNum = checkMainOrderMaxNoOfTimes(groupMainOrderListObj, AdvancedMainOrderPeriodNumList);
							if (typeof resultperiodNum === "object") {
								AdvancedMainOrderPeriodNumList = resultperiodNum;
							}
							refreshSetAdvancedMainOrderTable();
							return;
						default:
							return;
						}
					}
				}
			} else {
				return;
			}

		};

	}
}

function updateNOOfBetTimes(noOfBetTimes) {
	var noOfBetTimesArr = [];
	var eleInputArr = getEle("NormalListArea").getElementsByTagName("input");
	for (var i = 0; i < eleInputArr.length; i++) {
		if (eleInputArr[i].type == "checkbox" && isJSON(eleInputArr[i].value)) {
			var json = JSON.parse(eleInputArr[i].value);
			if (eleInputArr[i].checked == true) {
				noOfBetTimesArr.push(noOfBetTimes);
			} else {
				noOfBetTimesArr.push(0);
			}
		}
	}
	refreshSetMainOrderTable(noOfBetTimesArr.length, noOfBetTimesArr)
}

// 顯示追號設定 Table
function refreshSetAdvancedMainOrderTable() {
	setIdDisplay("AdvancedListArea", "inline-table");
	getEle("AdvancedListArea").display = "";
	// var noOfBetTimes1 =
	// getEle("NormalAreaDiv").getElementsByClassName("multiple")[0].getElementsByTagName("input")[0].value;
	var periodNumList = AdvancedMainOrderPeriodNumList;
	var allChecked = true;
	getMainOrderListJSON();
	if (Object.keys(groupMainOrderListObj).length > 0) {
		setInnerHTML(getEle("AdvancedListArea").getElementsByTagName("tbody")[0], "");
		var mainOrderListJson = groupMainOrderListObj;
		if (mainOrderListJson.orders != null) {
			var periodNumListArr = [];
			// +JSON.stringify(periodNumList)+
			var tableTrtd = '<tr><th>序号</th><th><input type="checkbox" onchange = "refreshSetAdvancedMainOrderTableChecked(this.checked);" >追号期次</th><th>倍数</th><th>金额</th><th>预计开奖时间</th></tr>';

			// var tableTrtd = '<tr><th>序号</th><th><input
			// type="checkbox" onchange =
			// "refreshSetMainOrderTable(\''+index+'\',[],this.checked)">追号期次</th><th>倍数</th><th>金额</th><th>预计开奖时间</th></tr>';
			var totalAmount = 0;
			var totalNoBet = 0;
			var totalPeriodNumListAmount = 0;
			var totalPeriodNumListNoBet = 0;
			var totalPeriodCount = 0;
			for (var i = 0; i < mainOrderListJson.orders.length; i++) {
				totalAmount += parseFloat(mainOrderListJson.orders[i].midOrders[0].amount);
				totalNoBet += parseInt(mainOrderListJson.orders[i].midOrders[0].noOfBet);
			}

			totalAmount = divide(Math.floor(times(parseFloat(totalAmount), 100)), 100);

			for (var i = 0; i < periodNumList.length; i++) {
				var obj = {};
				var noOfBetTimes = 0;
				obj.periodNum = periodNumList[i]["periodNum"];
				obj.date = periodNumList[i]["date"];
				obj.totalAmount = totalAmount;
				obj.totalNoBet = totalNoBet;
				obj.expS = periodNumList[i]["expS"];
				obj.noOfBetTimes = periodNumList[i]["noOfBetTimes"];
				noOfBetTimes = periodNumList[i]["noOfBetTimes"];

				var ids = i + 1;

				var periodBetMoney = 0;
				var checked = "";
				if (noOfBetTimes > 0) {
					checked = "checked";
					periodBetMoney = divide(Math.floor(times(parseFloat(totalAmount), parseFloat(noOfBetTimes), 100)), 100);
					totalPeriodCount++;
				} else {
					allChecked = false;
				}

				var kjTime = new Date(parseInt(periodNumList[i]["expS"])).getFromFormat('yyyy/MM/dd hh:mm:ss');
				var tableTrtdStr = "";
				if (parseInt(periodNumList[i]["periodNum"]) == parseInt(getEle("nowNo").value)) {
					tableTrtdStr = '<tr><td>' + ids + '</td><td><input type="checkbox" onchange = "setMainOrderNoOfBetTimesChecked(\'AdvancedListArea\',\''
							+ periodNumList[i]["periodNum"] + '\');" value = ' + JSON.stringify(obj) + ' ' + checked + '>' + periodNumList[i]["periodNum"]
							+ '<span>当前期</span></td><td ><input type="text" value = "' + noOfBetTimes + '" onchange = "setMainOrderNoOfBetTimes(\'AdvancedListArea\',\''
							+ periodNumList[i]["periodNum"] + '\',this.value)" >倍</td><td>' + periodBetMoney + ' 元</td><td>' + kjTime.toString() + '</td></tr>';
				} else {
					tableTrtdStr = '<tr><td>' + ids + '</td><td><input type="checkbox" onchange = "setMainOrderNoOfBetTimesChecked(\'AdvancedListArea\',\''
							+ periodNumList[i]["periodNum"] + '\');" value = ' + JSON.stringify(obj) + ' ' + checked + '>' + periodNumList[i]["periodNum"]
							+ '</td><td ><input type="text" value = "' + noOfBetTimes + '" onchange = "setMainOrderNoOfBetTimes(\'AdvancedListArea\',\''
							+ periodNumList[i]["periodNum"] + '\',this.value)" >倍</td><td>' + periodBetMoney + ' 元</td><td>' + kjTime.toString() + '</td></tr>';
				}

				tableTrtd += tableTrtdStr.toString();

				periodNumListArr.push(obj);

				if (checked == "checked") {
					totalPeriodNumListNoBet += totalNoBet;
					totalPeriodNumListAmount += (times(parseFloat(totalAmount), parseFloat(noOfBetTimes)));
				}
			}

			if (periodNumListArr.length > 0) {
				getEle("periodNumList").value = JSON.stringify(periodNumListArr);
				getEle("BetAddDiv").getElementsByClassName("btn-add-confirm")[0].disabled = false;
			} else {
				getEle("periodNumList").value = "";
				getEle("BetAddDiv").getElementsByClassName("btn-add-confirm")[0].disabled = true;
				allChecked = false;
			}

			setInnerHTML(getEle("AdvancedListArea").getElementsByTagName("tbody")[0], tableTrtd);

			getEle("AdvancedListArea").getElementsByTagName("tr")[0].getElementsByTagName("th")[1].getElementsByTagName("input")[0].checked = allChecked;

			getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[0].getElementsByTagName("span")[0].innerHTML = totalPeriodCount; // 期數
			getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[1].getElementsByTagName("span")[0].innerHTML = totalPeriodNumListNoBet;// 注數
			getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[2].getElementsByTagName("span")[0].innerHTML = divide(Math.floor(times(
					totalPeriodNumListAmount, 100)), 100); // 總下注金額
			// document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].innerHTML
			// = getEle("balance").value.substring(0,
			// getEle("balance").value.indexOf(".")+3);
			document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = getEle("balance").value.substring(0,
					getEle("balance").value.indexOf(".") + 3);

		}
	}
}
//
function refreshSetAdvancedMainOrderTableChecked(checked) {
	setIdDisplay("AdvancedListArea", "inline-table");
	getEle("AdvancedListArea").display = "";
	// var noOfBetTimes1 =
	// getEle("NormalAreaDiv").getElementsByClassName("multiple")[0].getElementsByTagName("input")[0].value;
	var allChecked = true;
	getMainOrderListJSON();

	var periodNumList = AdvancedMainOrderPeriodNumList;

	if (Object.keys(groupMainOrderListObj).length > 0) {
		setInnerHTML(getEle("AdvancedListArea").getElementsByTagName("tbody")[0], "");
		var mainOrderListJson = groupMainOrderListObj;
		if (mainOrderListJson.orders != null) {
			var periodNumListArr = [];

			if (typeof checked != "undefined") {
				if (checked == false) {
					var thChecked = "";
				} else {
					thChecked = "checked";
				}
			} else {
				thChecked = "checked";
			}
			// +JSON.stringify(periodNumList)+
			var tableTrtd = '<tr><th>序号</th><th><input type="checkbox" onchange = "refreshSetAdvancedMainOrderTableChecked(this.checked);" ' + thChecked
					+ '>追号期次</th><th>倍数</th><th>金额</th><th>预计开奖时间</th></tr>';

			// var tableTrtd = '<tr><th>序号</th><th><input
			// type="checkbox" onchange =
			// "refreshSetMainOrderTable(\''+index+'\',[],this.checked)">追号期次</th><th>倍数</th><th>金额</th><th>预计开奖时间</th></tr>';
			var totalAmount = 0;
			var totalNoBet = 0;
			var totalPeriodNumListAmount = 0;
			var totalPeriodNumListNoBet = 0;
			var totalPeriodCount = 0;
			for (var i = 0; i < mainOrderListJson.orders.length; i++) {
				totalAmount += parseFloat(mainOrderListJson.orders[i].midOrders[0].amount);
				totalNoBet += parseInt(mainOrderListJson.orders[i].midOrders[0].noOfBet);
			}

			totalAmount = divide(Math.floor(times(parseFloat(totalAmount), 100)), 100);
			for (var i = 0; i < periodNumList.length; i++) {
				var obj = {};
				var noOfBetTimes = 0;
				obj.periodNum = periodNumList[i]["periodNum"];
				obj.date = periodNumList[i]["date"];
				obj.expS = periodNumList[i]["expS"];
				obj.totalAmount = totalAmount;
				obj.noOfBetTimes = periodNumList[i]["noOfBetTimes"];
				obj.totalNoBet = totalNoBet;

				noOfBetTimes = periodNumList[i]["noOfBetTimes"];

				var ids = i + 1;

				var periodBetMoney = 0;
				if (typeof checked != "undefined") {
					if (checked == false) {
						checked = "";
						obj.noOfBetTimes = 0;
						noOfBetTimes = 0;
						allChecked = false;
					} else {
						if (parseInt(periodNumList[i]["noOfBetTimes"]) == 0) {
							periodNumList[i]["noOfBetTimes"] = 1;
							obj.noOfBetTimes = periodNumList[i]["noOfBetTimes"];
							noOfBetTimes = periodNumList[i]["noOfBetTimes"];
						}

						totalPeriodCount++;
						checked = "checked";
						periodBetMoney = divide(Math.floor(times(parseFloat(totalAmount), parseFloat(noOfBetTimes), 100)), 100);
					}
				} else {
					totalPeriodCount++;
					checked = "checked";
					periodBetMoney = divide(Math.floor(times(parseFloat(totalAmount), parseFloat(noOfBetTimes), 100)), 100);
				}
				var kjTime = new Date(parseInt(periodNumList[i]["expS"])).getFromFormat('yyyy/MM/dd hh:mm:ss');
				var tableTrtdStr = "";
				if (parseInt(periodNumList[i]["periodNum"]) == parseInt(getEle("nowNo").value)) {
					tableTrtdStr = '<tr><td>' + ids + '</td><td><input type="checkbox" onchange = "setMainOrderNoOfBetTimesChecked(\'AdvancedListArea\',\''
							+ periodNumList[i]["periodNum"] + '\');" value = ' + JSON.stringify(obj) + ' ' + checked + '>' + periodNumList[i]["periodNum"]
							+ '<span>当前期</span></td><td ><input type="text" value = "' + noOfBetTimes + '" onchange = "setMainOrderNoOfBetTimes(\'AdvancedListArea\',\''
							+ periodNumList[i]["periodNum"] + '\',this.value)" >倍</td><td>' + periodBetMoney + ' 元</td><td>' + kjTime.toString() + '</td></tr>';
				} else {
					tableTrtdStr = '<tr><td>' + ids + '</td><td><input type="checkbox" onchange = "setMainOrderNoOfBetTimesChecked(\'AdvancedListArea\',\''
							+ periodNumList[i]["periodNum"] + '\');" value = ' + JSON.stringify(obj) + ' ' + checked + '>' + periodNumList[i]["periodNum"]
							+ '</td><td ><input type="text" value = "' + noOfBetTimes + '" onchange = "setMainOrderNoOfBetTimes(\'AdvancedListArea\',\''
							+ periodNumList[i]["periodNum"] + '\',this.value)" >倍</td><td>' + periodBetMoney + ' 元</td><td>' + kjTime.toString() + '</td></tr>';
				}

				tableTrtd += tableTrtdStr.toString();

				periodNumListArr.push(obj);

				if (checked == "checked") {

					totalPeriodNumListNoBet += totalNoBet;
					totalPeriodNumListAmount += (times(parseFloat(totalAmount), parseFloat(noOfBetTimes)));
				}

			}

			if (periodNumListArr.length > 0) {
				getEle("periodNumList").value = JSON.stringify(periodNumListArr);
				getEle("BetAddDiv").getElementsByClassName("btn-add-confirm")[0].disabled = false;
			} else {
				getEle("periodNumList").value = "";
				getEle("BetAddDiv").getElementsByClassName("btn-add-confirm")[0].disabled = true;
				allChecked = false;
			}

			setInnerHTML(getEle("AdvancedListArea").getElementsByTagName("tbody")[0], tableTrtd);

			getEle("AdvancedListArea").getElementsByTagName("tr")[0].getElementsByTagName("th")[1].getElementsByTagName("input")[0].checked = allChecked;

			getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[0].getElementsByTagName("span")[0].innerHTML = totalPeriodCount; // 期數
			getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[1].getElementsByTagName("span")[0].innerHTML = totalPeriodNumListNoBet;// 注數
			getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[2].getElementsByTagName("span")[0].innerHTML = divide(Math.floor(times(
					totalPeriodNumListAmount, 100)), 100); // 總下注金額
			// document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].innerHTML
			// = getEle("balance").value.substring(0,
			// getEle("balance").value.indexOf(".")+3);
			document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = getEle("balance").value.substring(0,
					getEle("balance").value.indexOf(".") + 3);

		}
	}
}

// 顯示追號設定 Table
function refreshSetMainOrderTable(index, noOfBetTimesArr) {

	setIdDisplay("NormalListArea", "inline-table");
	getEle("NormalListArea").display = "";
	var noOfBetTimes1 = getEle("NormalAreaDiv").getElementsByClassName("multiple")[0].getElementsByTagName("input")[0].value;

	var allChecked = true;
	getMainOrderListJSON();
	if (Object.keys(groupMainOrderListObj).length > 0) {
		setInnerHTML(getEle("NormalListArea").getElementsByTagName("tbody")[0], "");
		var mainOrderListJson = groupMainOrderListObj;
		if (mainOrderListJson.orders != null) {
			var periodNumListArr = [];

			var tableTrtd = '<tr><th>序号</th><th><input type="checkbox" onchange = "refreshSetMainOrderTableChecked(\'' + index
					+ '\',this.checked);" >追号期次</th><th>倍数</th><th>金额</th><th>预计开奖时间</th></tr>';

			// var tableTrtd = '<tr><th>序号</th><th><input
			// type="checkbox" onchange =
			// "refreshSetMainOrderTable(\''+index+'\',[],this.checked)">追号期次</th><th>倍数</th><th>金额</th><th>预计开奖时间</th></tr>';
			var totalAmount = 0;
			var totalNoBet = 0;
			var totalPeriodNumListAmount = 0;
			var totalPeriodNumListNoBet = 0;
			var totalPeriodCount = 0;
			for (var i = 0; i < mainOrderListJson.orders.length; i++) {
				totalAmount += parseFloat(mainOrderListJson.orders[i].midOrders[0].amount);
				totalNoBet += parseInt(mainOrderListJson.orders[i].midOrders[0].noOfBet);
			}
			totalAmount = divide(Math.floor(times(parseFloat(totalAmount), 100)), 100);

			var periodNumList = getPeriodNum(parseInt(index));
			for (var i = 0; i < periodNumList.length; i++) {
				var obj = {};
				var noOfBetTimes = 0;
				obj.periodNum = periodNumList[i]["periodNum"];
				obj.date = periodNumList[i]["date"];
				obj.totalAmount = totalAmount;
				obj.totalNoBet = totalNoBet;
				obj.expS = periodNumList[i]["expS"];

				var ids = i + 1;
				var noOfBetTimes2 = -1;
				if (typeof noOfBetTimesArr != "undefined" && Array.isArray(noOfBetTimesArr) == true) {
					if (noOfBetTimesArr[i] != null && typeof noOfBetTimesArr[i] != "undefined") {
						noOfBetTimes2 = parseInt(noOfBetTimesArr[i]);
						obj.noOfBetTimes = noOfBetTimes2;
						noOfBetTimes = noOfBetTimes2;
					}
				}

				if (noOfBetTimes2 < 0) {
					obj.noOfBetTimes = 0;
					noOfBetTimes = 0;
				}

				var periodBetMoney = 0;
				var checked = "";
				if (noOfBetTimes > 0) {
					checked = "checked";
					periodBetMoney = divide(Math.floor(times(parseFloat(totalAmount), parseFloat(noOfBetTimes), 100)), 100);
					totalPeriodCount++;
				} else {
					allChecked = false;
				}

				var kjTime = new Date(parseInt(periodNumList[i]["expS"])).getFromFormat('yyyy/MM/dd hh:mm:ss');
				var tableTrtdStr = "";
				if (parseInt(periodNumList[i]["periodNum"]) == parseInt(getEle("nowNo").value)) {
					tableTrtdStr = '<tr><td>' + ids + '</td><td><input type="checkbox" onchange = "setMainOrderNoOfBetTimesChecked(\'NormalAreaDiv\',\''
							+ periodNumList[i]["periodNum"] + '\');" value = ' + JSON.stringify(obj) + ' ' + checked + '>' + periodNumList[i]["periodNum"]
							+ '<span>当前期</span></td><td ><input type="text" value = "' + noOfBetTimes + '" onchange = "setMainOrderNoOfBetTimes(\'NormalAreaDiv\',\''
							+ periodNumList[i]["periodNum"] + '\',this.value)" >倍</td><td>' + periodBetMoney + ' 元</td><td>' + kjTime.toString() + '</td></tr>';
				} else {
					tableTrtdStr = '<tr><td>' + ids + '</td><td><input type="checkbox" onchange = "setMainOrderNoOfBetTimesChecked(\'NormalAreaDiv\',\''
							+ periodNumList[i]["periodNum"] + '\');" value = ' + JSON.stringify(obj) + ' ' + checked + '>' + periodNumList[i]["periodNum"]
							+ '</td><td ><input type="text" value = "' + noOfBetTimes + '" onchange = "setMainOrderNoOfBetTimes(\'NormalAreaDiv\',\''
							+ periodNumList[i]["periodNum"] + '\',this.value)" >倍</td><td>' + periodBetMoney + ' 元</td><td>' + kjTime.toString() + '</td></tr>';
				}

				tableTrtd += tableTrtdStr.toString();

				periodNumListArr.push(obj);

				if (checked == "checked") {

					totalPeriodNumListNoBet += totalNoBet;
					totalPeriodNumListAmount += (times(parseFloat(totalAmount), parseFloat(noOfBetTimes)));
				}
			}
			if (periodNumListArr.length > 0) {
				getEle("periodNumList").value = JSON.stringify(periodNumListArr);
				getEle("BetAddDiv").getElementsByClassName("btn-add-confirm")[0].disabled = false;
			} else {
				getEle("periodNumList").value = "";
				getEle("BetAddDiv").getElementsByClassName("btn-add-confirm")[0].disabled = true;
				allChecked = false;
			}
			setInnerHTML(getEle("NormalListArea").getElementsByTagName("tbody")[0], tableTrtd);
			getEle("NormalListArea").getElementsByTagName("tr")[0].getElementsByTagName("th")[1].getElementsByTagName("input")[0].checked = allChecked;

			getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[0].getElementsByTagName("span")[0].innerHTML = totalPeriodCount; // 期數
			getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[1].getElementsByTagName("span")[0].innerHTML = totalPeriodNumListNoBet;// 注數
			getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[2].getElementsByTagName("span")[0].innerHTML = divide(Math.floor(times(
					totalPeriodNumListAmount, 100)), 100); // 總下注金額
			// document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].innerHTML
			// = getEle("balance").value.substring(0,
			// getEle("balance").value.indexOf(".")+3);
			document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = getEle("balance").value.substring(0,
					getEle("balance").value.indexOf(".") + 3);

			getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[0].className = "";
			getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[1].className = "";
			getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[2].className = "";
			getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[3].className = "";

			index = parseInt(index)

			if (index == 5) {
				getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[0].className = "active";
			} else if (index == 10) {
				getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[1].className = "active";
			} else if (index == 15) {
				getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[2].className = "active";
			} else if (index == 20) {
				getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[3].className = "active";
			}
			getEle("NormalAreaDiv").getElementsByTagName("span")[1].getElementsByTagName("input")[0].value = "" + index;
		}
	}
}

function refreshSetMainOrderTableChecked(index, checked) {
	var a = new Date().getTime();
	setIdDisplay("NormalListArea", "inline-table");
	getEle("NormalListArea").display = "";
	var noOfBetTimes1 = getEle("NormalAreaDiv").getElementsByClassName("multiple")[0].getElementsByTagName("input")[0].value;
	var allChecked = true;
	getMainOrderListJSON();
	if (Object.keys(groupMainOrderListObj).length > 0) {
		setInnerHTML(getEle("NormalListArea").getElementsByTagName("tbody")[0], "");
		var mainOrderListJson = groupMainOrderListObj;
		if (mainOrderListJson.orders != null) {
			var periodNumListArr = [];

			if (typeof checked != "undefined") {
				if (checked == false) {
					var thChecked = "";
				} else {
					thChecked = "checked";
				}
			} else {
				thChecked = "checked";
			}
			var tableTrtd = '<tr><th>序号</th><th><input type="checkbox" onchange = "refreshSetMainOrderTableChecked(\'' + index + '\',this.checked);" ' + thChecked
					+ '>追号期次</th><th>倍数</th><th>金额</th><th>预计开奖时间</th></tr>';

			// var tableTrtd = '<tr><th>序号</th><th><input
			// type="checkbox" onchange =
			// "refreshSetMainOrderTable(\''+index+'\',[],this.checked)">追号期次</th><th>倍数</th><th>金额</th><th>预计开奖时间</th></tr>';
			var totalAmount = 0;
			var totalNoBet = 0;
			var totalPeriodNumListAmount = 0;
			var totalPeriodNumListNoBet = 0;
			var totalPeriodCount = 0;
			for (var i = 0; i < mainOrderListJson.orders.length; i++) {
				totalAmount += parseFloat(mainOrderListJson.orders[i].midOrders[0].amount);
				totalNoBet += parseInt(mainOrderListJson.orders[i].midOrders[0].noOfBet);
			}

			totalAmount = divide(Math.floor(times(parseFloat(totalAmount), 100)), 100);
			var periodNumList = getPeriodNum(parseInt(index));

			for (var i = 0; i < periodNumList.length; i++) {
				var obj = {};
				var noOfBetTimes = 0;
				obj.periodNum = periodNumList[i]["periodNum"];
				obj.date = periodNumList[i]["date"];
				obj.totalAmount = totalAmount;
				obj.noOfBetTimes = noOfBetTimes1;
				obj.totalNoBet = totalNoBet;
				obj.expS = periodNumList[i]["expS"];

				noOfBetTimes = noOfBetTimes1;

				var ids = i + 1;

				var periodBetMoney = 0;
				if (typeof checked != "undefined") {
					if (checked == false) {
						checked = "";
						obj.noOfBetTimes = 0;
						noOfBetTimes = 0;
						allChecked = false;
					} else {
						totalPeriodCount++;
						checked = "checked";
						periodBetMoney = divide(Math.floor(times(parseFloat(totalAmount), parseFloat(noOfBetTimes), 100)), 100);
					}
				} else {
					totalPeriodCount++;
					checked = "checked";
					periodBetMoney = divide(Math.floor(times(parseFloat(totalAmount), parseFloat(noOfBetTimes), 100)), 100);
				}
				var kjTime = new Date(parseInt(periodNumList[i]["expS"])).getFromFormat('yyyy/MM/dd hh:mm:ss');
				var tableTrtdStr = "";
				if (parseInt(periodNumList[i]["periodNum"]) == parseInt(getEle("nowNo").value)) {
					tableTrtdStr = '<tr><td>' + ids + '</td><td><input type="checkbox" onchange = "setMainOrderNoOfBetTimesChecked(\'NormalAreaDiv\',\''
							+ periodNumList[i]["periodNum"] + '\');" value = ' + JSON.stringify(obj) + ' ' + checked + '>' + periodNumList[i]["periodNum"]
							+ '<span>当前期</span></td><td ><input type="text" value = "' + noOfBetTimes + '" onchange = "setMainOrderNoOfBetTimes(\'NormalAreaDiv\',\''
							+ periodNumList[i]["periodNum"] + '\',this.value)" >倍</td><td>' + periodBetMoney + ' 元</td><td>' + kjTime.toString() + '</td></tr>';
				} else {
					tableTrtdStr = '<tr><td>' + ids + '</td><td><input type="checkbox" onchange = "setMainOrderNoOfBetTimesChecked(\'NormalAreaDiv\',\''
							+ periodNumList[i]["periodNum"] + '\');" value = ' + JSON.stringify(obj) + ' ' + checked + '>' + periodNumList[i]["periodNum"]
							+ '</td><td ><input type="text" value = "' + noOfBetTimes + '" onchange = "setMainOrderNoOfBetTimes(\'NormalAreaDiv\',\''
							+ periodNumList[i]["periodNum"] + '\',this.value)" >倍</td><td>' + periodBetMoney + ' 元</td><td>' + kjTime.toString() + '</td></tr>';
				}

				tableTrtd += tableTrtdStr.toString();

				periodNumListArr.push(obj);
				if (checked == "checked") {

					totalPeriodNumListNoBet += totalNoBet;
					totalPeriodNumListAmount += (times(parseFloat(totalAmount), parseFloat(noOfBetTimes)));
				}

			}

			if (periodNumListArr.length > 0) {
				getEle("periodNumList").value = JSON.stringify(periodNumListArr);
				getEle("BetAddDiv").getElementsByClassName("btn-add-confirm")[0].disabled = false;
			} else {
				getEle("periodNumList").value = "";
				getEle("BetAddDiv").getElementsByClassName("btn-add-confirm")[0].disabled = true;
				allChecked = false;
			}

			setInnerHTML(getEle("NormalListArea").getElementsByTagName("tbody")[0], tableTrtd);

			getEle("NormalListArea").getElementsByTagName("tr")[0].getElementsByTagName("th")[1].getElementsByTagName("input")[0].checked = allChecked;

			getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[0].getElementsByTagName("span")[0].innerHTML = totalPeriodCount; // 期數
			getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[1].getElementsByTagName("span")[0].innerHTML = totalPeriodNumListNoBet;// 注數
			getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[2].getElementsByTagName("span")[0].innerHTML = divide(Math.floor(times(
					totalPeriodNumListAmount, 100)), 100); // 總下注金額
			// document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].innerHTML
			// = getEle("balance").value.substring(0,
			// getEle("balance").value.indexOf(".")+3);
			document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = getEle("balance").value.substring(0,
					getEle("balance").value.indexOf(".") + 3);

			getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[0].className = "";
			getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[1].className = "";
			getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[2].className = "";
			getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[3].className = "";

			index = parseInt(index)

			if (index == 5) {
				getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[0].className = "active";
			} else if (index == 10) {
				getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[1].className = "active";
			} else if (index == 15) {
				getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[2].className = "active";
			} else if (index == 20) {
				getEle("NormalAreaDiv").getElementsByClassName("period")[0].getElementsByTagName("button")[3].className = "active";
			}
			getEle("NormalAreaDiv").getElementsByTagName("span")[1].getElementsByTagName("input")[0].value = "" + index;
		}
	}

}

// 設定追號單倍數
function setMainOrderNoOfBetTimes(tableName, periodNum, noOfBetTimes) {
	var eleTrArr = getEle(tableName).getElementsByTagName("tr");
	var totalPeriodBetMoney = 0;
	var totalPeriodNumListNoBet = 0;
	var totalPeriodCount = 0;
	var periodNumListArr = [];
	var checkedIsFlase = false;
	for (var i = 0; i < eleTrArr.length; i++) {
		if (eleTrArr[i].getElementsByTagName("td").length > 0) {
			var checkBoxEle = eleTrArr[i].getElementsByTagName("td")[1].getElementsByTagName("input")[0];
			if (checkBoxEle.type == "checkbox" && isJSON(checkBoxEle.value)) {
				var obj = {};
				var json = JSON.parse(checkBoxEle.value);
				if (json.periodNum == periodNum) {
					if (noOfBetTimes == 0 || noOfBetTimes == "") {
						checkBoxEle.checked = false;
						json.noOfBetTimes = 0;
						checkBoxEle.value = JSON.stringify(json);
						checkBoxEle.parentNode.parentNode.getElementsByTagName("td")[2].getElementsByTagName("input")[0].value = "0";
						setInnerHTML(checkBoxEle.parentNode.parentNode.getElementsByTagName("td")[3], "0元");
						checkedIsFlase = true;
					} else {
						checkBoxEle.parentNode.parentNode.getElementsByTagName("td")[2].getElementsByTagName("input")[0].value = parseInt(noOfBetTimes);

						checkBoxEle.checked = true;
						// var noOfBetTimes =
						// eleInputArr[i].parentNode.parentNode.getElementsByClassName("canttype")[0].getElementsByTagName("input")[0].value;
						var periodBetMoney = divide(Math.floor(times(parseFloat(json.totalAmount), parseFloat(noOfBetTimes), 100)), 100);
						setInnerHTML(checkBoxEle.parentNode.parentNode.getElementsByTagName("td")[3], periodBetMoney + "元");
						totalPeriodBetMoney += periodBetMoney;

						json.noOfBetTimes = noOfBetTimes;
						checkBoxEle.value = JSON.stringify(json);
						totalPeriodNumListNoBet += json.totalNoBet;

						totalPeriodCount++;
					}
				} else {
					if (checkBoxEle.checked == true) {
						var periodBetMoney = divide(Math.floor(times(parseFloat(json.totalAmount), parseFloat(json.noOfBetTimes), 100)), 100);
						totalPeriodBetMoney += periodBetMoney;
						totalPeriodNumListNoBet += json.totalNoBet;

						totalPeriodCount++;
					} else {
						checkedIsFlase = true;
					}
				}

				periodNumListArr.push(json);
			}
		}
	}
	if (checkedIsFlase == true) {
		getEle(tableName).getElementsByTagName("th")[1].getElementsByTagName("input")[0].checked = false;
	} else {
		getEle(tableName).getElementsByTagName("th")[1].getElementsByTagName("input")[0].checked = true;
	}

	if (periodNumListArr.length > 0) {
		getEle("BetAddDiv").getElementsByClassName("btn-add-confirm")[0].disabled = false;
		getEle("periodNumList").value = JSON.stringify(periodNumListArr);
	} else {
		getEle("BetAddDiv").getElementsByClassName("btn-add-confirm")[0].disabled = true;
		getEle("periodNumList").value = "";
	}
	getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[2].getElementsByTagName("span")[0].innerHTML = "" + divide(Math.floor(times(totalPeriodBetMoney, 100)), 100);
	getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[1].getElementsByTagName("span")[0].innerHTML = "" + totalPeriodNumListNoBet;
	getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[0].getElementsByTagName("span")[0].innerHTML = "" + totalPeriodCount;

}

function setMainOrderNoOfBetTimesChecked(tableName, periodNum) {
	var eleTrArr = getEle(tableName).getElementsByTagName("tr");
	var totalPeriodBetMoney = 0;
	var totalPeriodNumListNoBet = 0;
	var totalPeriodCount = 0;
	var periodNumListArr = [];
	var checkedIsFlase = false;
	for (var i = 0; i < eleTrArr.length; i++) {
		if (eleTrArr[i].getElementsByTagName("td").length > 0) {
			var checkBoxEle = eleTrArr[i].getElementsByTagName("td")[1].getElementsByTagName("input")[0];
			if (checkBoxEle.type == "checkbox" && isJSON(checkBoxEle.value)) {
				var obj = {};
				var json = JSON.parse(checkBoxEle.value);
				if (json.periodNum == periodNum) {
					if (checkBoxEle.checked == false) {
						checkBoxEle.checked = false;
						json.noOfBetTimes = 0;
						checkBoxEle.value = JSON.stringify(json);
						checkBoxEle.parentNode.parentNode.getElementsByTagName("td")[2].getElementsByTagName("input")[0].value = "0";
						setInnerHTML(checkBoxEle.parentNode.parentNode.getElementsByTagName("td")[3], "0元");
						checkedIsFlase = true;
					} else {
						if (json.noOfBetTimes == 0 || json.noOfBetTimes == "") {
							noOfBetTimes = 1
						}
						checkBoxEle.parentNode.parentNode.getElementsByTagName("td")[2].getElementsByTagName("input")[0].value = parseInt(noOfBetTimes);

						checkBoxEle.checked = true;
						// var noOfBetTimes =
						// eleInputArr[i].parentNode.parentNode.getElementsByClassName("canttype")[0].getElementsByTagName("input")[0].value;
						var periodBetMoney = divide(Math.floor(times(parseFloat(json.totalAmount), parseFloat(noOfBetTimes), 100)), 100);
						setInnerHTML(checkBoxEle.parentNode.parentNode.getElementsByTagName("td")[3], periodBetMoney + "元");
						totalPeriodBetMoney += periodBetMoney;

						json.noOfBetTimes = noOfBetTimes;
						checkBoxEle.value = JSON.stringify(json);
						totalPeriodNumListNoBet += json.totalNoBet;

						totalPeriodCount++;
					}

					if (tableName == "AdvancedListArea") {
						for (var s = 0; s < AdvancedMainOrderPeriodNumList.length; s++) {
							if (parseInt(AdvancedMainOrderPeriodNumList[s].periodNum) == parseInt(periodNum)) {
								AdvancedMainOrderPeriodNumList[s].noOfBetTimes = json.noOfBetTimes;
							}
						}
					}
				} else {
					if (checkBoxEle.checked == true) {
						var periodBetMoney = divide(Math.floor(times(parseFloat(json.totalAmount), parseFloat(json.noOfBetTimes), 100)), 100);
						totalPeriodBetMoney += periodBetMoney;
						totalPeriodNumListNoBet += json.totalNoBet;

						totalPeriodCount++;
					} else {
						checkedIsFlase = true;
					}
				}
				periodNumListArr.push(json);
			}
		}
	}

	if (checkedIsFlase == true) {
		getEle(tableName).getElementsByTagName("th")[1].getElementsByTagName("input")[0].checked = false;
	} else {
		getEle(tableName).getElementsByTagName("th")[1].getElementsByTagName("input")[0].checked = true;
	}

	if (periodNumListArr.length > 0) {
		getEle("BetAddDiv").getElementsByClassName("btn-add-confirm")[0].disabled = false;
		getEle("periodNumList").value = JSON.stringify(periodNumListArr);
	} else {
		getEle("BetAddDiv").getElementsByClassName("btn-add-confirm")[0].disabled = true;
		getEle("periodNumList").value = "";
	}
	getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[2].getElementsByTagName("span")[0].innerHTML = "" + divide(Math.floor(times(totalPeriodBetMoney, 100)), 100);
	getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[1].getElementsByTagName("span")[0].innerHTML = "" + totalPeriodNumListNoBet;
	getEle("BetAddDiv").getElementsByClassName("limit")[0].getElementsByTagName("td")[0].getElementsByTagName("span")[0].innerHTML = "" + totalPeriodCount;

}

// 換期追號設定更新
function changePeriodNum() {
	var tr = getEle("NormalListArea").getElementsByTagName("tr");
	var tr2 = getEle("AdvancedListArea").getElementsByTagName("tr");
	var noOfBetTimesArray = [];
	if (tr.length >= 1 && !hasClass(tr[1], "invisible")) {
		for (var i = 1; i < tr.length; i++) {
			if (isJSON(tr[i].getElementsByTagName("td")[1].getElementsByTagName("input")[0].value)) {
				var obj = JSON.parse(tr[i].getElementsByTagName("td")[1].getElementsByTagName("input")[0].value);
				noOfBetTimesArray.push(obj.noOfBetTimes);
			}
		}
		if (noOfBetTimesArray.length > 0) {
			refreshSetMainOrderTable(noOfBetTimesArray.length, noOfBetTimesArray);
		}
	} else if (tr2.length >= 1 && !hasClass(tr2[1], "invisible")) {

		var periodNumSelect = getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("select")[0];
		var periodNumList = getPeriodNum(10000);
		for (var i = 0; i < periodNumList.length; i++) {
			if (typeof periodNumList[i].periodNum !== "undefined" && periodNumList[i].periodNum != null) {
				if (parseInt(periodNumList[i].periodNum) == parseInt(getEle("nowNo").value)) {
					periodNumSelect.options[i] = new Option(periodNumList[i].periodNum + " 當前期號", periodNumList[i].periodNum);
				} else {
					periodNumSelect.options[i] = new Option(periodNumList[i].periodNum, periodNumList[i].periodNum);
				}

			}
		}
		var objList = [];
		var periodList = getPeriodNum2(advancedSettingData["startPeriodNum"], AdvancedMainOrderPeriodNumList.length);
		if (periodList != null && typeof periodList != "undefined" && periodList.length > 0) {
			periodNumSelect.value = periodList[0]["periodNum"];
			for (var i = 0; i < periodList.length; i++) {
				AdvancedMainOrderPeriodNumList[i].periodNum = periodList[i]["periodNum"];
				AdvancedMainOrderPeriodNumList[i].date = periodList[i]["date"];
				AdvancedMainOrderPeriodNumList[i].expS = periodList[i]["expS"];
			}

			getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("p")[1].innerHTML = '<span>追号期数：</span><input type="text" value="' + periodList.length
					+ '" >期(最多可以追' + periodNumList.length + '期）';

			getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("p")[1].getElementsByTagName("input")[0].onkeyup = function() {
				var val = toInt(this.value);
				if (val > 0) {
					if (val > periodNumList.length) {
						this.value = periodNumList.length;
						advancedSettingData["dPeriodNumLength"] = this.value;
					} else {
						this.value = val;
						advancedSettingData["dPeriodNumLength"] = this.value;
					}

				}
			}
			getEle("AdvancedAreaDiv").getElementsByTagName("div")[2].getElementsByTagName("p")[2].getElementsByTagName("input")[0].onchange = function() {
				var val = toInt(this.value);

				if (val > periodNumList.length) {
					this.value = periodNumList.length;
				} else {
					this.value = val;
				}
				advancedSettingData["dPeriodNumLength"] = this.value;

			}

			refreshSetAdvancedMainOrderTable();
		}
	}
}

// 單式功能

// 清理錯誤
function deleteNoBetNum() {
	getMainOrderJSON();
	if (Object.keys(groupMainOrderObj).length > 0) {
		if (Array.isArray(groupMainOrderObj) == true) {
			document.getElementsByClassName("ball-tab import-area")[0].getElementsByTagName("textarea")[0].value = "" + groupMainOrderObj[0].betData2;
		} else {
			document.getElementsByClassName("ball-tab import-area")[0].getElementsByTagName("textarea")[0].value = "" + groupMainOrderObj.betData2;
		}
	} else {
		document.getElementsByClassName("ball-tab import-area")[0].getElementsByTagName("textarea")[0].value = "";
	}
	showRemindBetDiv('已刪除錯誤項');
}
// 清理重複
function deleteRepeatNoBetNum() {
	getMainOrderJSON();
	if (Object.keys(groupMainOrderObj).length > 0) {
		if (Array.isArray(groupMainOrderObj) == true) {
			document.getElementsByClassName("ball-tab import-area")[0].getElementsByTagName("textarea")[0].value = "" + groupMainOrderObj[0].betData2;
		} else {
			document.getElementsByClassName("ball-tab import-area")[0].getElementsByTagName("textarea")[0].value = "" + groupMainOrderObj.betData2;
		}

	} else {
		document.getElementsByClassName("ball-tab import-area")[0].getElementsByTagName("textarea")[0].value = "";
	}
	showRemindBetDiv('已刪除重複項');
}

// 清理
function clearDsText() {
	showRemindBetDiv('已清空文本框');
	document.getElementsByClassName("ball-tab import-area")[0].getElementsByTagName("textarea")[0].value = "";
}

// 溫馨提示
function showRemindBetDiv(text, fun) {
	if (typeof fun != "undefined" && typeof fun == "function") {
		getEle("RemindBetDiv").getElementsByTagName("button")[0].onclick = function() {
			fun();
			btnClose();
		};
	} else {
		getEle("RemindBetDiv").getElementsByTagName("button")[0].onclick = function() {
			btnClose();
		}
	}
	if (!openRemindBet()) {
		getEle("RemindBetDiv").getElementsByClassName("content")[0].getElementsByTagName("p")[0].innerHTML = "" + text;
	}
}

// 溫馨提示2
function showRemindBetDiv2(text, fun) {
	if (typeof fun != "undefined" && typeof fun == "function") {
		getEle("RemindBetDiv2").getElementsByTagName("button")[0].onclick = function() {
			fun();
			btnClose();
		};
	} else {
		getEle("RemindBetDiv2").getElementsByTagName("button")[0].onclick = function() {
			btnClose();
		}
	}
	getEle("RemindBetDiv2").getElementsByTagName("button")[1].onclick = function() {
		btnClose();
	}

	if (!openRemindBet2()) {
		getEle("RemindBetDiv2").getElementsByClassName("content")[0].getElementsByTagName("p")[0].innerHTML = "" + text;
	}
}

Date.prototype.getFromFormat = function(format) {
	if (typeof format === "string" && typeof format != "undefined" && format != null && format != '') {
		var str = [ '-', '/', ';', ' ', 'y', 'M', 'd', 'h', 'm', 's' ];
		for (var i = 0; i < format.length; i++) {
			if (!format[i] in str) {
				console_Log("getFromFormat error in" + (i + 1));
				return '';
			}
		}
		var nowDateTime = this;
		var yyyy = nowDateTime.getFullYear().toString();
		format = format.replace(/yyyy/g, yyyy);
		var MM = (nowDateTime.getMonth() + 1).toString();
		format = format.replace(/MM/g, (MM[1] ? MM : "0" + MM[0]));
		var dd = nowDateTime.getDate().toString();
		format = format.replace(/dd/g, (dd[1] ? dd : "0" + dd[0]));
		var hh = nowDateTime.getHours().toString();
		format = format.replace(/hh/g, (hh[1] ? hh : "0" + hh[0]));
		var mm = nowDateTime.getMinutes().toString();
		format = format.replace(/mm/g, (mm[1] ? mm : "0" + mm[0]));
		var ss = nowDateTime.getSeconds().toString();
		format = format.replace(/ss/g, (ss[1] ? ss : "0" + ss[0]));

		delete str;
		delete nowDateTime;
		delete yyyy;
		delete MM;
		delete dd;
		delete hh;
		delete mm;
		delete ss;

		return format;
	} else {
		console_Log("getFromFormat is null!");
		return '';
	}
	return '';
};

// -----------------------------------------------------------------------------------

function changeLotteryNumToText(authId, data) {
	authId = parseInt(authId);
	
	var lhcMark6zm1_6 = [639,640,641,642,643,644]; //正碼1-6
	var z5xbxdszh = [169,557,319]; //五星總和組和大小單雙
	var z5zxh = [168,556,318]; //五星總和大小單雙
	var zllh = [220,221,222,223,224,225,226,227,228,229,608,609,610,611,612,613,614,615,616,617,370,371,372,373,374,375,376,377,378,379,400,401,402]; //龍虎和

	var specialq_1 = [109,126,143,497,514,531,259,276,293]; //豹子
	var specialq_2 = [110,127,144,498,515,532,260,277,294]; //順子
	var specialq_3 = [111,128,145,499,516,533,261,278,295]; //对子
	var specialq_4 = [112,129,146,500,517,534,262,279,296]; //半順
	var specialq_5 = [113,130,147,501,518,535,263,280,297]; //杂六
	
	var lh = [459]//龍虎
	var krhs = [457,180,181,182,183,184,568,569,570,571,572,330,331,332,333,334]; //預測冠亞和值_大小單雙
	
	var allBlock = [418]; //全黑
	var allRed = [417]; //全黑
	
	var hds = [415]; // 和值單雙
	
	var hbs = [414]; //和值大小
	
	var tls = [413,405]; //通選
	
	var dds = [430];//定單雙 
	
	var z5mqc3x = [189,339,577];//5碼區間三星
	
	var z4mqc3x = [190,191,192,340,341,342,578,579,580];//4碼星區間三星
	
	var z5mqw3x = [185,335,573]; //5碼趣味三星
	
	var z4mqw3x = [186,187,188,336,337,338,574,575,576]; //4碼趣味三星
	
	var bb = [650]; // 半波
	
	var zodiac = [651,652,653,654,655,656]; // 生肖
	
	var ws = [657,658,659,660]; //尾數
	
	
	if (z5mqw3x.includes(authId)) {
		var gor = data.split("|");

		gor[0] = gor[0].replace(/1/g, "大");
		gor[0] = gor[0].replace(/0/g, "小");

		gor[1] = gor[1].replace(/1/g, "大");
		gor[1] = gor[1].replace(/0/g, "小");

		return gor.join("|");
	} else if (z4mqw3x.includes(authId)) {
		var gor = data.split("|");
		gor[0] = gor[0].replace(/1/g, "大");
		gor[0] = gor[0].replace(/0/g, "小");

//		gor[1] = gor[1].replace(/1/g, "大");
//		gor[1] = gor[1].replace(/0/g, "小");

		return gor.join("|");
	} else if (z5mqc3x.includes(authId)) {
		var gor = data.split("|");
		gor[0] = gor[0].replace(/0/g, "一区");
		gor[0] = gor[0].replace(/1/g, "二区");
		gor[0] = gor[0].replace(/2/g, "三区");
		gor[0] = gor[0].replace(/3/g, "四区");
		gor[0] = gor[0].replace(/4/g, "五区");

		gor[1] = gor[1].replace(/0/g, "一区");
		gor[1] = gor[1].replace(/1/g, "二区");
		gor[1] = gor[1].replace(/2/g, "三区");
		gor[1] = gor[1].replace(/3/g, "四区");
		gor[1] = gor[1].replace(/4/g, "五区");

		return gor.join("|");
	} else if (z4mqc3x.includes(authId)) {

		var gor = data.split("|");
		gor[0] = gor[0].replace(/0/g, "一区");
		gor[0] = gor[0].replace(/1/g, "二区");
		gor[0] = gor[0].replace(/2/g, "三区");
		gor[0] = gor[0].replace(/3/g, "四区");
		gor[0] = gor[0].replace(/4/g, "五区");
		return gor.join("|");

	} else if (dds.includes(authId)) {
		data = data.replace(/1/g, "5单0双");
		data = data.replace(/2/g, "4单1双");
		data = data.replace(/3/g, "3单2双");
		data = data.replace(/4/g, "2单3双");
		data = data.replace(/5/g, "1单4双");
		data = data.replace(/6/g, "0单5双");
	} else if (tls.includes(authId)) {
		data = data.replace(/1/g, "通选");
	} else if (hbs.includes(authId)) {
		data = data.replace(/1/g, "大");
		data = data.replace(/2/g, "小");
	} else if (hds.includes(authId)) {
		data = data.replace(/1/g, "单");
		data = data.replace(/2/g, "双");
	} else if (allRed.includes(authId)) {
		data = data.replace(/1/g, "全紅");
	} else if (allBlock.includes(authId)) {
		data = data.replace(/1/g, "全黑");
	} else if (krhs.includes(authId)) {
		data = data.replace(/1/g, "大");
		data = data.replace(/2/g, "小");
		data = data.replace(/3/g, "单");
		data = data.replace(/4/g, "双");
	} else if (lh.includes(authId)) {
		data = data.replace(/l/g, "龙");
		data = data.replace(/h/g, "虎");
	} else if (specialq_1.includes(authId)) {
		data = data.replace(/1/g, "豹子");
	} else if (specialq_2.includes(authId)) {
		data = data.replace(/2/g, "順子");
	} else if (specialq_3.includes(authId)) {
		data = data.replace(/3/g, "对子");
	} else if (specialq_4.includes(authId)) {
		data = data.replace(/4/g, "半順");
	} else if (specialq_5.includes(authId)) {
		data = data.replace(/5/g, "杂六");
	} else if (zllh.includes(authId)) {
		data = data.replace(/L/g, "龙");
		data = data.replace(/H/g, "虎");
		data = data.replace(/1/g, "和");
	} else if (z5zxh.includes(authId)) {
		data = data.replace(/1/g, "总和大");
		data = data.replace(/2/g, "总和小");
		data = data.replace(/3/g, "总和单");
		data = data.replace(/4/g, "总和双");
		
	} else if (z5xbxdszh.includes(authId)) {
		data = data.replace(/1/g, "总和大单");
		data = data.replace(/2/g, "总和小单");
		data = data.replace(/3/g, "总和大双");
		data = data.replace(/4/g, "总和小双");
	}else if (lhcMark6zm1_6.includes(authId)) {
		data = data.replace(/1/g, "大");
		data = data.replace(/2/g, "单");
		data = data.replace(/3/g, "合单");
		
		data = data.replace(/4/g, "小");
		data = data.replace(/5/g, "双");
		data = data.replace(/6/g, "合双");
		
		data = data.replace(/7/g, "红");
		data = data.replace(/8/g, "蓝");
		data = data.replace(/9/g, "绿");
	}
	else if(bb.includes(authId)){
		data = data.replace(/16/g, "红合双");
		data = data.replace(/17/g, "绿合双");
		data = data.replace(/18/g, "蓝合双");
		
		data = data.replace(/13/g, "红合单");
		data = data.replace(/14/g, "绿合单");
		data = data.replace(/15/g, "蓝合单");
		
		data = data.replace(/10/g, "红单");
		data = data.replace(/11/g, "绿单");
		data = data.replace(/12/g, "蓝单");
		
		data = data.replace(/7/g, "红双");
		data = data.replace(/8/g, "绿双");
		data = data.replace(/9/g, "蓝双");
		
		data = data.replace(/4/g, "红小");
		data = data.replace(/5/g, "绿小");
		data = data.replace(/6/g, "蓝小");
		
		data = data.replace(/1/g, "红大");
		data = data.replace(/2/g, "绿大");
		data = data.replace(/3/g, "蓝大");
	}
	else if(zodiac.includes(authId)){ 
		
		data = data.replace(/10/g, "鸡");
		data = data.replace(/11/g, "狗");
		data = data.replace(/12/g, "猪");
		
		data = data.replace(/7/g, "马");
		data = data.replace(/8/g, "羊");
		data = data.replace(/9/g, "猴");
		
		data = data.replace(/4/g, "兔");
		data = data.replace(/5/g, "龙");
		data = data.replace(/6/g, "蛇");
		
		data = data.replace(/1/g, "鼠");
		data = data.replace(/2/g, "牛");
		data = data.replace(/3/g, "虎");
	}
	else if(ws.includes(authId)){ 
		var dataArr = data.split(",");
		for(var i = 0 ; i < dataArr.length ; i++){
			var num = toInt(dataArr[i]);
			if(num == 1){
				dataArr[i] = dataArr[i].replace(/1/g, "0尾");
			}
			else if(num == 2){
				dataArr[i] = dataArr[i].replace(/2/g, "1尾");
			}
			else if(num == 3){
				dataArr[i] = dataArr[i].replace(/3/g, "2尾");			
			}
			else if(num == 4){
				dataArr[i] = dataArr[i].replace(/4/g, "3尾");
			}
			else if(num == 5){
				dataArr[i] = dataArr[i].replace(/5/g, "4尾");
			}
			else if(num == 6){
				dataArr[i] = dataArr[i].replace(/6/g, "5尾");
			}
			else if(num == 7){
				dataArr[i] = dataArr[i].replace(/7/g, "6尾");
			}
			else if(num == 8){
				dataArr[i] = dataArr[i].replace(/8/g, "7尾");		
			}
			else if(num == 9){
				dataArr[i] = dataArr[i].replace(/9/g, "8尾");
			}
			else if(num == 10){
				dataArr[i] = dataArr[i].replace(/10/g, "9尾");
			}
		}
		data = dataArr.join(",");
	}
	return data;
}

function getMainOrderListJSON() {
	if (typeof groupMainOrderListObj === "undefined" || groupMainOrderListObj == null || groupMainOrderListObj == "" || Object.keys(groupMainOrderListObj).length == 0) {
		groupMainOrderListObj = isJSON2(getEle("mainOrderList").value);
		if (!groupMainOrderListObj) {
			groupMainOrderListObj = {};
		}
	}
}

function getMainOrderJSON() {
	if (typeof groupMainOrderObj === "undefined" || groupMainOrderObj == null || groupMainOrderObj == "" || Object.keys(groupMainOrderObj).length == 0) {
		groupMainOrderObj = isJSON2(getEle("mainOrder").value);
		if (!groupMainOrderObj) {
			groupMainOrderObj = {};
		}
	}
}

function getRatiosJson() {
	if (typeof ratiosJson === "undefined" || ratiosJson == null || ratiosJson == "" || Object.keys(ratiosJson).length == 0) {
		ratiosJson = isJSON2(Strings.decode(getEle("ratios").value));
		if (!ratiosJson) {
			ratiosJson = {};
		}
	}
}

function getRatiosLFJson() {
	if (typeof ratiosLFJson === "undefined" || ratiosLFJson == null || ratiosLFJson == "" || Object.keys(ratiosLFJson).length == 0) {
		ratiosLFJson = isJSON2(Strings.decode(getEle("ratiosLF").value));
		if (!ratiosLFJson) {
			ratiosLFJson = {};
		}
	}
}

function getTempNum(len) {
	if (typeof len != "number") {
		len = 1;
	}
	if (len == 1) {
		return Math.floor(Math.random() * (10));
	} else if (len > 1) {
		var tmpN = 10;
		for (var i = 1; i < len; i++) {
			tmpN = tmpN * 10;
		}
		tmpN = Math.floor(Math.random() * (tmpN + 1)) + "";
		while (tmpN.length < len) {
			tmpN = "0" + tmpN;
		}
		return tmpN;
	}
}
function getReplaceId() {
	return "j" + getTempNum(1) + getTempNum(3) + getTempNum(5) + getTempNum(2) + getTempNum(4) + getTempNum(6);
}

function setInnerHTML(ele, html) {
	if (ele) {
		var output = html.toString().trim();
		var sEleTag = ele.tagName.toLowerCase();
		if (output.indexOf(">") == -1) {
			var eleText = ele.outerHTML.substring(0, ele.outerHTML.indexOf(">") + 1) + output + "</" + sEleTag + ">";
			if (sEleTag != "tbody") {
				ele.outerHTML = eleText.toString();
			} else {
				eleText = ele.parentElement.innerHTML.replace(ele.outerHTML, eleText);
				var p = ele.parentElement;
				while (p.firstChild) {
					p.removeChild(p.firstChild);
				}
				p.outerHTML = p.outerHTML.substring(0, p.outerHTML.indexOf(">") + 1) + eleText + "</" + p.tagName.toLowerCase() + ">";
				delete p;
				p = undefined;
			}
			delete output;
			output = undefined;
			delete sEleTag;
			sEleTag = undefined;
			delete eleText;
			eleText = undefined;
			return;
		}
		var FMT = [ "onsubmit", "onclick", "ondbclick", "onchange", "onkeyup", "onkeydown", "onkeypress", "onmousedown", "onmouseover", "onmouseout" ];
		var tmpId = [];
		var tmpType = [];
		var tmpValue = [];
		for (var i1 = 0; i1 < FMT.length; i1++) {
			if (output.indexOf(" " + FMT[i1] + "=") != -1 || output.indexOf(" " + FMT[i1] + " ") != -1) {
				var tmpList = output.split(" " + FMT[i1]);
				var tmpStr = "";
				for (var i2 = 0; i2 < tmpList.length; i2++) {
					if (i2 == 0) {
						tmpStr = tmpList[i2];
					} else {
						var tmpStr1 = tmpList[i2].substring(tmpList[i2].indexOf('"') + 1);
						var tmpStr2 = tmpStr1.substring(tmpStr1.indexOf('"') + 1);
						tmpStr1 = tmpStr1.substring(0, tmpStr1.indexOf('"'));
						tmpId[tmpId.length] = getReplaceId();
						tmpType[tmpType.length] = FMT[i1];
						tmpValue[tmpValue.length] = tmpStr1;
						tmpStr = tmpStr + ' class="' + tmpId[tmpId.length - 1] + '" ' + tmpStr2;
						delete tmpStr1;
						tmpStr1 = undefined;
						delete tmpStr2;
						tmpStr2 = undefined;
					}
				}
				output = tmpStr;
				delete tmpList;
				tmpList = undefined;
				delete tmpStr;
				tmpStr = undefined;
			}
		}
		delete FMT;
		FMT = undefined;
		var tmpList = output.split(">");
		var tmpStr = "";
		for (var i1 = 0; i1 < tmpList.length; i1++) {
			if (tmpList[i1].split(" class=").length > 2) {
				var tmpStr1 = "";
				var tmpStr2 = "";
				var ttList = tmpList[i1].split(" class=");
				for (var i2 = 0; i2 < ttList.length; i2++) {
					if (i2 == 0) {
						tmpStr1 = ttList[i2];
					} else {
						var tmpStr3 = ttList[i2].substring(tmpList[i2].indexOf('"') + 2);
						var tmpStr4 = tmpStr3.substring(0, tmpStr3.indexOf('"'));
						tmpStr3 = tmpStr3.substring(tmpStr3.indexOf('"') + 1);
						tmpStr1 = tmpStr1 + tmpStr3;
						tmpStr4 = tmpStr4.trim();
						tmpStr2 += " " + tmpStr4;
						delete tmpStr3;
						tmpStr3 = undefined;
						delete tmpStr4;
						tmpStr4 = undefined;
					}
				}
				tmpStr = tmpStr + tmpStr1 + ' class="' + tmpStr2.substring(1) + '" >';
				delete tmpStr1;
				tmpStr1 = undefined;
				delete tmpStr2;
				tmpStr2 = undefined;
				delete ttList;
				ttList = undefined;
			} else {
				if (tmpList[i1] != "") {
					tmpStr = tmpStr + tmpList[i1] + ">";
				}
			}
		}
		output = tmpStr;
		delete tmpList;
		tmpList = undefined;
		delete tmpStr;
		tmpStr = undefined;
		var eleText = ele.outerHTML.substring(0, ele.outerHTML.indexOf(">") + 1) + output + "</" + sEleTag + ">";
		if (sEleTag != "tbody") {
			ele.outerHTML = eleText;
		} else {
			eleText = ele.parentElement.innerHTML.replace(ele.outerHTML, eleText);
			var p = ele.parentElement;
			while (p.firstChild) {
				p.removeChild(p.firstChild);
			}
			p.outerHTML = p.outerHTML.substring(0, p.outerHTML.indexOf(">") + 1) + eleText + "</" + p.tagName.toLowerCase() + ">";
			delete p;
			p = undefined;
		}
		delete sEleTag;
		sEleTag = undefined;
		delete eleText;
		eleText = undefined;
		var onClickFunctionArr = [];
		for (var i = 0; i < tmpId.length; i++) {
			var toRun = tmpValue[i];
			if (!toRun) {
				toRun = "";
			}
			toRun = toRun.trim();
			if (toRun == "") {
				delete toRun;
				toRun = undefined;
				delete tmpId;
				tmpId = undefined;
				delete tmpType;
				tmpType = undefined;
				delete tmpValue;
				tmpValue = undefined;
				return;
			} else if (toRun.endsWith("}")) {

			} else if (!toRun.endsWith(";")) {
				toRun = toRun + ";"
			}
			onClickFunctionArr.push('document.getElementsByClassName("' + tmpId[i] + '")[0].' + tmpType[i] + ' = function(){' + toRun + '};');
			delete toRun;
			toRun = undefined;
		}
		// setTimeout(function(){eval(onClickFunctionArr.join(""));},0);
		eval(onClickFunctionArr.join(""));
		delete tmpId;
		tmpId = undefined;
		delete tmpType;
		tmpType = undefined;
		delete tmpValue;
		tmpValue = undefined;
	}
	return;
}

function setEval(str) {
	str = "" + str;
	return new Function(str)();
}

function getEval(str) {
	str = "" + str;
	return new Function("return " + str)();
}

function checkMainOrderListTimes(mainOrderList, date) {
	// var mainOrderList = isJSON2(getEle("mainOrderList").value);
	var maxWinBonus = parseFloat(getEle("maxWinBonus").value);
	var todo = true;

	getRatiosJson();
	getRatiosLFJson();
	if (typeof mainOrderList !== "undefined") {
		if (typeof mainOrderList.orders !== "undefined") {
			for (var i = 0; i < mainOrderList.orders.length; i++) {
				var mainOrder = mainOrderList.orders[i];
				var noOfBetTimes = mainOrder.midOrders[0].noOfBetTimes;

				var l1 = mainOrder.midOrders[0].l1;
				var l2 = mainOrder.midOrders[0].l2;
				var l3 = mainOrder.midOrders[0].l3;
				var l4 = mainOrder.midOrders[0].l4;

				var baselineIndex = -1;
				var baselineInfos = {};

				var mainId = mainOrder.mainId;
				var localId = mainOrder.localId;
				var midId = mainOrder.midId;
				var handiCap = mainOrder.handiCap;
				var lotteryLowfreq = mainOrder.lotteryLowfreq;
				var minAuthId = mainOrder.minAuthId;
				var periodDate = "";

				var minAuthId = mainOrder.minAuthId;
				for (var j = 0; j < mainOrder.midOrders[0].betOrders.length; j++) {
					var playedId = parseInt(mainOrder.midOrders[0].betOrders[j].playedId);
					// var baselineIndex =
					// mainOrder.midOrders[0].betOrders[j].baselineIndex
					// var baselineInfos =
					// getBaselineByPlayedId(l1,l2,l3,l4,playedId,baselineIndex,currentBaseline,date);
					if (baselineIndex != mainOrder.midOrders[0].betOrders[j].baselineIndex || periodDate != "" || periodDate != date) {
						baselineIndex = mainOrder.midOrders[0].betOrders[j].baselineIndex;
						periodDate = date;
						var currentObj;
						if (mainOrder.lotteryLowfreq == "1") {
							currentObj = ratiosLFJson.CurrentBaselineLF;
						} else {
							currentObj = ratiosJson.CurrentBaseline;
						}
						if (typeof currentObj === 'undefined' || currentObj == null) {
							return false;
						}
						baselineInfos = getBaselineByPlayedId(mainId, localId, midId, handiCap, minAuthId, currentObj, periodDate, baselineIndex, lotteryLowfreq);
					}

					if (minAuthId == '105' || minAuthId == '122' || minAuthId == '139' || minAuthId == '213' || minAuthId == '493' || minAuthId == '510' || minAuthId == '527'
							|| minAuthId == '601' || minAuthId == '225' || minAuthId == '272' || minAuthId == '289' || minAuthId == '363' || minAuthId == '396') {

						if (playedId == 22 || playedId == 36 || playedId == 50 || playedId == 448 || playedId == 455 || playedId == 462 || playedId == 469 || playedId == 476
								|| playedId == 483 || playedId == 490 || playedId == 535) {
							mainOrder.midOrders[0].betOrders[j].baseline = "" + parseFloat(baselineInfos.baseline) * 2;
						} else {
							mainOrder.midOrders[0].betOrders[j].baseline = baselineInfos.baseline;
						}
					} else {
						mainOrder.midOrders[0].betOrders[j].baseline = baselineInfos.baseline;
					}

					var bonus = 0;
					if (typeof mainOrder.midOrders[0].betOrders[j].moneyUnit != "undefined" && typeof mainOrder.midOrders[0].betOrders[j].relativeBaseline != "undefined"
							&& typeof mainOrder.midOrders[0].betOrders[j].bonusRatio != "undefined") {
						bonus = times(parseFloat(mainOrder.midOrders[0].betOrders[j].moneyUnit), parseFloat(mainOrder.midOrders[0].betOrders[j].baseline), divide(
								parseFloat(mainOrder.midOrders[0].betOrders[j].relativeBaseline), 100), divide(
								parseFloat(mainOrder.midOrders[0].betOrders[j].bonusRatio), 2000));
					}

					var maxNoOfBetTimes = Math.floor(divide(parseFloat(maxWinBonus), parseFloat(bonus)));
					if (noOfBetTimes > maxNoOfBetTimes) {
						mainOrder.midOrders[0].noOfBetTimes = maxNoOfBetTimes;
						todo = false;
					}
				}

			}

			if (todo == false) {
				return mainOrderList;
			} else {
				return true;
			}
		}
	}
	return false;
}

function checkMainOrderMaxNoOfTimes(mainOrderList, periods) {
	var maxBonus = 0;
	var maxWinBonus = parseFloat(getEle("maxWinBonus").value);
	var ratiosJson = isJSON2(Strings.decode(getEle("ratios").value));
	var todo = true;
	getRatiosJson();
	getRatiosLFJson();
	if (typeof mainOrderList !== "undefined" && typeof periods !== "undefined") {
		if (typeof mainOrderList.orders !== "undefined") {
			var currentBaseline = ratiosJson.CurrentBaseline
			for (var s = 0; s < mainOrderList.orders.length; s++) {
				var mainOrder = mainOrderList.orders[s];

				var l1 = mainOrder.midOrders[0].l1;
				var l2 = mainOrder.midOrders[0].l2;
				var l3 = mainOrder.midOrders[0].l3;
				var l4 = mainOrder.midOrders[0].l4;
				var minAuthId = mainOrder.minAuthId;

				var baselineInfos = {};
				var baselineIndex = -1;

				var mainId = mainOrder.mainId;
				var localId = mainOrder.localId;
				var midId = mainOrder.midId;
				var handiCap = mainOrder.handiCap;
				var lotteryLowfreq = mainOrder.lotteryLowfreq;
				var minAuthId = mainOrder.minAuthId;
				var periodDate = "";
				for (var i = 0; i < periods.length; i++) {
					if (typeof periods[i].noOfBetTimes != "undefined" && periods[i].noOfBetTimes != null) {
						mainOrder.midOrders[0].noOfBetTimes = periods[i].noOfBetTimes;
					}
					var noOfBetTimes = parseFloat(mainOrder.midOrders[0].noOfBetTimes);
					var minNoOfBetTimes = parseFloat(mainOrder.midOrders[0].noOfBetTimes);

					for (var j = 0; j < mainOrder.midOrders[0].betOrders.length; j++) {
						var playedId = parseInt(mainOrder.midOrders[0].betOrders[j].playedId);
						// var baselineIndex =
						// mainOrder.midOrders[0].betOrders[j].baselineIndex
						// var baselineInfos =
						// getBaselineByPlayedId(l1,l2,l3,l4,playedId,baselineIndex,currentBaseline,periods[i].date);

						if (baselineIndex != mainOrder.midOrders[0].betOrders[j].baselineIndex || periodDate != "" || periodDate != periods[i].date) {
							baselineIndex = mainOrder.midOrders[0].betOrders[j].baselineIndex;
							periodDate = periods[i].date;
							var currentObj;
							if (mainOrder.lotteryLowfreq == "1") {
								currentObj = ratiosLFJson.CurrentBaselineLF;
							} else {
								currentObj = ratiosJson.CurrentBaseline;
							}
							if (typeof currentObj === 'undefined' || currentObj == null) {
								return false;
							}
							baselineInfos = getBaselineByPlayedId(mainId, localId, midId, handiCap, minAuthId, currentObj, periodDate, baselineIndex, lotteryLowfreq);
						}

						if (minAuthId == '105' || minAuthId == '122' || minAuthId == '139' || minAuthId == '213' || minAuthId == '493' || minAuthId == '510'
								|| minAuthId == '527' || minAuthId == '601' || minAuthId == '225' || minAuthId == '272' || minAuthId == '289' || minAuthId == '363'
								|| minAuthId == '396') {

							if (playedId == 22 || playedId == 36 || playedId == 50 || playedId == 448 || playedId == 455 || playedId == 462 || playedId == 469
									|| playedId == 476 || playedId == 483 || playedId == 490 || playedId == 535) {
								mainOrder.midOrders[0].betOrders[j].baseline = "" + parseFloat(baselineInfos.baseline) * 2;
							} else {
								mainOrder.midOrders[0].betOrders[j].baseline = baselineInfos.baseline;
							}
						} else {
							mainOrder.midOrders[0].betOrders[j].baseline = baselineInfos.baseline;
						}

						var bonus = 0;
						if (typeof mainOrder.midOrders[0].betOrders[j].moneyUnit != "undefined" && typeof mainOrder.midOrders[0].betOrders[j].relativeBaseline != "undefined"
								&& typeof mainOrder.midOrders[0].betOrders[j].bonusRatio != "undefined") {
							bonus = times(parseFloat(mainOrder.midOrders[0].betOrders[j].moneyUnit), parseFloat(mainOrder.midOrders[0].betOrders[j].baseline), divide(
									parseFloat(mainOrder.midOrders[0].betOrders[j].relativeBaseline), 100), divide(
									parseFloat(mainOrder.midOrders[0].betOrders[j].bonusRatio), 2000));
						}
						var maxNoOfBetTimes = Math.floor(divide(parseFloat(maxWinBonus), parseFloat(bonus)));

						if (minNoOfBetTimes > maxNoOfBetTimes) {
							minNoOfBetTimes = maxNoOfBetTimes;
						}
					}
					if (noOfBetTimes > minNoOfBetTimes) {
						periods[i].noOfBetTimes = minNoOfBetTimes;

						todo = false;
					}
				}
			}
			if (todo == false) {
				return periods;
			}
			return todo;
		}
	}
	return false;
}

function toInt(val) {
	val = "" + val;
	var reul = /^\d$/;
	var valArr = val.split("");
	var result = [];
	try {
		for (var i = 0; i < valArr.length; i++) {
			if (reul.test(valArr[i])) {
				result.push(valArr[i]);
			} else {
				if (result.join('').trim().length > 0) {
					return parseInt(result.join(''));
				}
			}
		}
		if (result.length > 0) {
			return parseInt(result.join(''));
		}
	} catch (e) {
		console_Log(e);
	}
	return 0;
}

function toFloat(val) {
	val = "" + val;
	
	var tmpA = val.split(".");
	var valArr = [];
	var result = [];
	var reul = /^\d$/;
	try {
		if(tmpA.length > 0 && tmpA.length <= 2){
			var tmpASize = tmpA.length;
			for(var i = 0 ; i < tmpASize ; i++){
				valArr = valArr.concat(tmpA[i].split(""));
			}
			
			for (var i = 0; i < valArr.length; i++) {
				if (reul.test(valArr[i])) {
					result.push(valArr[i]);
				} else {
					break;
				}
			}
			
			if (result.join('').trim().length > 0) {
				if(tmpASize == 2 ? result.length > tmpA[0].length : false){
					var t = result.length - tmpA[0].length;
					var n = parseInt(result.join(''));
					var s = 1;
					for(var j = 0 ; j < t ; j++){
						s = s*10;
					}
					return n/s;
				}
				else{
					return parseInt(result.join(''));
				}
			}
			
		}
	} catch (e) {
		console_Log(e);
	}
	return 0;
}

function betTimesByDoubleType1(mainOrder, startPeriod, nums, startTimes, param1, param2, param3) {
	var periodArray = new Array();
	var nowTimes = startTimes;
	var tmpPeriodNum = getPeriodNum2(startPeriod, nums);
	for (var i = 0; i < tmpPeriodNum.length; i++) {
		var tmpObj = new Object();
		tmpObj.periodNum = tmpPeriodNum[i].periodNum;
		tmpObj.noOfBetTimes = nowTimes;
		tmpObj.expS = tmpPeriodNum[i].expS;
		tmpObj.date = tmpPeriodNum[i].date;
		periodArray.push(tmpObj);
		if ((i + 1) % param1 == 0) {
			if (param2 == 0) {
				nowTimes = nowTimes + param3;
			} else {
				nowTimes = nowTimes * param3;
			}
		}
	}
	return periodArray;
}

function betTimesByDoubleType2(mainOrder, startPeriod, nums, startTimes, param1, param2) {
	var periodArray = new Array();
	var nowTimes = startTimes;
	var tmpPeriodNum = getPeriodNum2(startPeriod, nums);
	for (var i = 0; i < tmpPeriodNum.length; i++) {
		var tmpObj = new Object();
		tmpObj.periodNum = tmpPeriodNum[i].periodNum;
		tmpObj.noOfBetTimes = nowTimes;
		tmpObj.expS = tmpPeriodNum[i].expS;
		tmpObj.date = tmpPeriodNum[i].date;
		periodArray.push(tmpObj);
		if ((i + 1) == param1) {
			nowTimes = param2;
		}
	}
	return periodArray;
}

function betTimesByWinMoneyType1(mainOrder, startPeriod, nums, startTimes, param1) {
	var midOrderObj = JSON.parse(JSON.stringify(mainOrder.midOrders[0]));
	var winning = parseFloat(midOrderObj.maxBonus) - parseFloat(midOrderObj.amount);
	var totalCost = 0;
	var periodArray = new Array();
	var tmpPeriodNum = getPeriodNum2(startPeriod, nums);
	for (var i = 0; i < tmpPeriodNum.length; i++) {
		var tmpObj = new Object();
		var tmpTimes = Math.ceil((param1 + totalCost) / winning);
		if (tmpTimes < startTimes) {
			tmpTimes = startTimes;
		}
		if (groupMaxNoOfBetTimes < tmpTimes) {
			tmpTimes = groupMaxNoOfBetTimes;
		}
		tmpObj.periodNum = tmpPeriodNum[i].periodNum;
		tmpObj.noOfBetTimes = tmpTimes;
		tmpObj.expS = tmpPeriodNum[i].expS;
		tmpObj.date = tmpPeriodNum[i].date;
		periodArray.push(tmpObj);
		totalCost = totalCost + tmpTimes * midOrderObj.amount;
	}
	return periodArray;
}

function betTimesByWinMoneyType2(mainOrder, startPeriod, nums, startTimes, param1, param2, param3) {
	var midOrderObj = JSON.parse(JSON.stringify(mainOrder.midOrders[0]));
	var winning = parseFloat(midOrderObj.maxBonus) - parseFloat(midOrderObj.amount);
	var totalCost = 0;
	var periodArray = new Array();
	var tmpPeriodNum = getPeriodNum2(startPeriod, nums);
	for (var i = 0; i < tmpPeriodNum.length; i++) {
		var tmpGoal = param2;
		if (i >= param1) {
			tmpGoal = param3;
		}
		var tmpObj = new Object();
		var tmpTimes = Math.ceil((tmpGoal + totalCost) / winning);
		if (tmpTimes < startTimes) {
			tmpTimes = startTimes;
		}
		if (groupMaxNoOfBetTimes < tmpTimes) {
			tmpTimes = groupMaxNoOfBetTimes;
		}
		tmpObj.periodNum = tmpPeriodNum[i].periodNum;
		tmpObj.noOfBetTimes = tmpTimes;
		tmpObj.expS = tmpPeriodNum[i].expS;
		tmpObj.date = tmpPeriodNum[i].date;
		periodArray.push(tmpObj);
		totalCost = totalCost + tmpTimes * midOrderObj.amount;
	}
	return periodArray;
}

function betTimesByWinRatioType1(mainOrder, startPeriod, nums, startTimes, param1) {
	var midOrderObj = JSON.parse(JSON.stringify(mainOrder.midOrders[0]));
	var winning = parseFloat(midOrderObj.maxBonus) - parseFloat(midOrderObj.amount);
	var totalCost = 0;
	var periodArray = new Array();
	var tmpPeriodNum = getPeriodNum2(startPeriod, nums);
	for (var i = 0; i < tmpPeriodNum.length; i++) {
		var tmpObj = new Object();
		var tmpTimes = Math.ceil((param1 * (totalCost + midOrderObj.amount)) / winning);
		if (tmpTimes < startTimes) {
			tmpTimes = startTimes;
		}
		if (groupMaxNoOfBetTimes < tmpTimes) {
			tmpTimes = groupMaxNoOfBetTimes;
		}
		tmpObj.periodNum = tmpPeriodNum[i].periodNum;
		tmpObj.noOfBetTimes = tmpTimes;
		tmpObj.expS = tmpPeriodNum[i].expS;
		periodArray.push(tmpObj);
		tmpObj.date = tmpPeriodNum[i].date;
		totalCost = totalCost + tmpTimes * midOrderObj.amount;
	}
	return periodArray;
}

function betTimesByWinRatioType2(mainOrder, startPeriod, nums, startTimes, param1, param2, param3) {
	var midOrderObj = JSON.parse(JSON.stringify(mainOrder.midOrders[0]));
	var winning = parseFloat(midOrderObj.maxBonus) - parseFloat(midOrderObj.amount);
	var totalCost = 0;
	var periodArray = new Array();
	var tmpPeriodNum = getPeriodNum2(startPeriod, nums);
	for (var i = 0; i < tmpPeriodNum.length; i++) {
		var tmpGoal = param2;
		if (i >= param1) {
			tmpGoal = param3;
		}
		var tmpObj = new Object();
		var tmpTimes = Math.ceil((tmpGoal * (totalCost + midOrderObj.amount)) / winning);
		if (tmpTimes < startTimes) {
			tmpTimes = startTimes;
		}
		if (groupMaxNoOfBetTimes < tmpTimes) {
			tmpTimes = groupMaxNoOfBetTimes;
		}

		tmpObj.periodNum = tmpPeriodNum[i].periodNum;
		tmpObj.noOfBetTimes = tmpTimes;
		tmpObj.expS = tmpPeriodNum[i].expS;
		tmpObj.date = tmpPeriodNum[i].date;

		periodArray.push(tmpObj);
		totalCost = totalCost + tmpTimes * midOrderObj.amount;
	}
	return periodArray;
}

// /////////////////////////

document.getElementsByClassName("bottom-area")[0].getElementsByClassName("multiple-drop-menu")[0].getElementsByTagName("input")[0].onchange = function() {
	document.getElementById("multipleDropMenuContent").classList.remove("show");
	var val = toInt(this.value);
	if (val <= 0) {
		this.value = 1;
	} else if (val > groupMaxNoOfBetTimes) {
		this.value = groupMaxNoOfBetTimes;
	} else {
		this.value = val;
	}
	checkBallOnchange();
}
document.getElementsByClassName("bottom-area")[0].getElementsByClassName("multiple-drop-menu")[0].getElementsByTagName("input")[0].onkeyup = function() {
	document.getElementById("multipleDropMenuContent").classList.remove("show");
	var val = toInt(this.value);
	if (val > groupMaxNoOfBetTimes) {
		this.value = groupMaxNoOfBetTimes;
	} else if (val > 0) {
		this.value = val;
	}
}

document.getElementsByClassName("betlimit")[0].getElementsByTagName("input")[0].onchange = function() {
	var val = toInt(this.value);
	if (val > 0) {
		this.value = val;
	} else {
		this.value = 1;
	}
	checkBallOnchange();
}
document.getElementsByClassName("betlimit")[0].getElementsByTagName("input")[0].onkeyup = function() {
	var val = toInt(this.value);
	if (val > 0) {
		this.value = val;
	}
}

document.getElementsByClassName("bottom-area")[0].getElementsByClassName("multiple-drop-menu")[0].onmouseover = function() {
	document.getElementById("multipleDropMenuContent").classList.add("show");
}

document.getElementsByClassName("bottom-area")[0].getElementsByClassName("multiple-drop-menu")[0].onmouseout = function() {
	document.getElementById("multipleDropMenuContent").classList.remove("show");
}

getEle("multipleDropMenuContent").onmouseover = function() {
	document.getElementById("multipleDropMenuContent").classList.add("show");
}

getEle("multipleDropMenuContent").onmouseout = function() {
	document.getElementById("multipleDropMenuContent").classList.remove("show");
}

function clearMainOrderList() {
	clearMianOrder();
	refreshMainOrderData();
}
//顯示 下注資訊
function showBetData(){

	document.getElementsByClassName("bet")[0].getElementsByTagName("button")[0].disabled = true;
	document.getElementsByClassName("bet")[0].getElementsByTagName("button")[1].disabled = true;
	document.getElementsByClassName("bet")[0].childNodes[0].title = "";
	document.getElementsByClassName("bet")[0].childNodes[1].title = "";

	document.getElementsByClassName("total")[0].getElementsByTagName("span")[0].innerHTML = "0"; // 注數
	document.getElementsByClassName("total")[0].getElementsByTagName("span")[1].innerHTML = "0.00"; // 金額
	document.getElementsByClassName("highlight")[0].innerHTML = "0.00"; // 最高中獎金額
	
	getMainOrderJSON();
	var totalAmount = 0;
	var maxBonus = 0;
	var totoalNoOfBet = 0;
	var baseBet = 0;
	var isBetAmountNotOverBaseBet = true;
	
	if(Object.keys(groupMainOrderObj).length > 0 ){
		if(Array.isArray(groupMainOrderObj) == true){
			for(var i = 0 ; i < groupMainOrderObj.length ; i++){
				var mainOrder = groupMainOrderObj[i];
				
				if(typeof mainOrder["baseBet"] != "undefined"){
					baseBet = toFloat(mainOrder["baseBet"]);
				}
				
				if(typeof mainOrder["midOrders"] != "undefined" ? Array.isArray(mainOrder["midOrders"]) ? mainOrder["midOrders"].length == 1 : false : false){
					if(typeof mainOrder["midOrders"][0]["maxBonus"] != "undefined"){
						if(maxBonus < toFloat(mainOrder["midOrders"][0]["maxBonus"])){
							maxBonus = toFloat(mainOrder["midOrders"][0]["maxBonus"]);
						}
						
						if(typeof mainOrder["midOrders"][0]["amount"] !== "undefined" && typeof mainOrder["midOrders"][0]["noOfBetTimes"] !== "undefined"){
							var amount = toFloat(mainOrder["midOrders"][0]["amount"]);
							var noOfBetTimes = toFloat(mainOrder["midOrders"][0]["noOfBetTimes"]);
							totalAmount += times(amount,noOfBetTimes);
							
							if(baseBet > (times(amount,noOfBetTimes))){
								isBetAmountNotOverBaseBet = false;
							}
						}
						if(typeof mainOrder["midOrders"][0]["noOfBet"] !== "undefined"){
							totoalNoOfBet += toInt(mainOrder["midOrders"][0]["noOfBet"]);
						}
					}
				}
				
			}
		}
		else{
			var mainOrder = groupMainOrderObj;
			if(typeof mainOrder["midOrders"] != "undefined" ? Array.isArray(mainOrder["midOrders"]) ? mainOrder["midOrders"].length == 1 : false : false){
				if(typeof mainOrder["midOrders"][0]["maxBonus"] != "undefined"){
					if(maxBonus < toFloat(mainOrder["midOrders"][0]["maxBonus"])){
						maxBonus = toFloat(mainOrder["midOrders"][0]["maxBonus"]);
					}
					
					if(typeof mainOrder["midOrders"][0]["amount"] !== "undefined" && typeof mainOrder["midOrders"][0]["noOfBetTimes"] !== "undefined"){
						var amount = toFloat(mainOrder["midOrders"][0]["amount"]);
						var noOfBetTimes = toFloat(mainOrder["midOrders"][0]["noOfBetTimes"]);
						totalAmount += times(amount,noOfBetTimes);
						
						if(baseBet > (times(amount,noOfBetTimes))){
							isBetAmountNotOverBaseBet = false;
						}
					}
					if(typeof mainOrder["midOrders"][0]["noOfBet"] !== "undefined"){
						totoalNoOfBet += toInt(mainOrder["midOrders"][0]["noOfBet"]);
					}
				}
			}
		}
		
		document.getElementsByClassName("total")[0].getElementsByTagName("span")[0].innerHTML = totoalNoOfBet; // 注數
		document.getElementsByClassName("total")[0].getElementsByTagName("span")[1].innerHTML = divide(Math.floor(times(totalAmount, 100)),
				100); // 金額
		document.getElementsByClassName("highlight")[0].innerHTML = "" +  divide(Math.floor(times(maxBonus, 100)),100); // 最高中獎金額
		
		if (isBetAmountNotOverBaseBet == false) {
			document.getElementsByClassName("bet")[0].getElementsByTagName("button")[0].disabled = true;
			document.getElementsByClassName("bet")[0].getElementsByTagName("button")[1].disabled = true;
			document.getElementsByClassName("bet")[0].childNodes[0].title = "投注金額不足";
			document.getElementsByClassName("bet")[0].childNodes[1].title = "投注金額不足";
		} else if (divide(Math.floor(times(totalAmount, 100)), 100) > parseFloat(getEle("balance").value.substring(0,
				getEle("balance").value.indexOf(".") + 3))) {
			document.getElementsByClassName("bet")[0].getElementsByTagName("button")[0].disabled = true;
			document.getElementsByClassName("bet")[0].getElementsByTagName("button")[1].disabled = true;
			document.getElementsByClassName("bet")[0].childNodes[0].title = "餘額不足";
			document.getElementsByClassName("bet")[0].childNodes[1].title = "餘額不足";
		}
		else{
			
			document.getElementsByClassName("bet")[0].getElementsByTagName("button")[0].disabled = false;
			document.getElementsByClassName("bet")[0].getElementsByTagName("button")[1].disabled = false;
			
			
		}
		
	}
	
}

