function js_queryAndCanclebetOrder(){return true;}

var v_lottory_betOrder_again;
var MID_BET_ORDER = 1;
var MAIN_BET_ORDER = 2;
var CANCLE_MID_BET_ORDER = 3;
var CANCLE_MAIN_BET_ORDER = 4;

var mainOrderStatus = [ "", "進行中", "已終止(系統)", "已終止(手動)", "已完成" ];// 追號(進行中/手動終止/跳開終止(錄號撤單=系統撤單=error)/中奖終止/已完成)
var midOrderStatus = [ "", "未開奖", "未中奖", "已撤銷", "已中奖", "開奖中" ];// 一搬(未開奖/已撤銷(手動or錄號撤單=系統撤單=error)/開奖中/系統撤銷(錄號撤單=系統撤單=error)/未中奖or中奖$)
var mainOrdertype = [ "中奖不停", "中奖即停", "跳開停止", "中奖即停" ];
var handiCapName = ["","A盤","B盤","C盤","D盤","E盤"];

var pageCount = 10;
var nowPage = 1;
var totalPage = 1;
var nowFirstCount = 0;
var nowLastCount = 0;
var totalCount = 0;

var secMidPageCount = 5;
var secMidNowPage = 1;
var secMidTotalPage = 1;
var secMidNowFirstCount = 0;
var secMidNowLastCount = 0;
var secMidTotalCount = 0;

var betObjWhenGetNewBets = null;

var isJSON2 = function(str) {
    if (typeof str == 'string' && str != "") {
        try {
            var jsonObj = JSON.parse(str);
			return jsonObj;
        } catch (e) {
            console_Log(e.message);
            return false;
        }
    } else {
        return false;
    }
    console_Log('It is not a string!')
}

function getBetOrder(pageType) {
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type']){
		betObjWhenGetNewBets = {'type':''};
	}
	
		var type = '';
		if(!isNull(betObjWhenGetNewBets.type)){
			type = betObjWhenGetNewBets.type;
		}
		if(type == MID_BET_ORDER && pageType == 0){
			showMidTable();
			changeMidPageFun();
			openＭyBet();
			myBetOrderGetPage();
		}else if (type == MAIN_BET_ORDER && pageType == 0){
			showMainTable();
			changeMainPageFun();
			openMyAdd();
			myBetOrderGetPage();
		}else if (type == CANCLE_MID_BET_ORDER && pageType == 0){
			btnClose();
			lotteryBetOrderAjax_again(MID_BET_ORDER);
		}else if (type == CANCLE_MAIN_BET_ORDER && pageType == 0){
			btnClose();
			lotteryBetOrderAjax_again(MAIN_BET_ORDER);
		}else if(type == MID_BET_ORDER && pageType == 1){
			showMidTable();
			changeMidPageFun();
			openＭyBet();
			refreshPageByCancleOrder();
		}else if(type == MAIN_BET_ORDER && pageType == 1){
			showMainTable();
			changeMainPageFun();
			openMyAdd();
			refreshPageByCancleOrder();
		}
	}

function openMidBetNow(){
	var betOrderSearchBtn = document.getElementsByClassName('bet-now')[0].getElementsByTagName("p")[0]
	betOrderSearchBtn.innerHTML = '您可以通过<a href="javascript:void(0);" onclick="onclickMidBtn()">我的投注</a>查询记录！';
}

function openMainBetNow(){
	var betOrderSearchBtn = document.getElementsByClassName('bet-now')[0].getElementsByTagName("p")[0]
	betOrderSearchBtn.innerHTML = '您可以通过<a href="javascript:void(0);" onclick="onclickMainBtn()">我的追号</a>查询记录！';
}
function checkMidComplete(main){
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	
	var mainInfo = '';
	var mainObjKey = [];
	var midInfo = '';
	var nowTime = (new Date().getTime() - (parseInt(getEle('diffms').value)) + 1500);
	var midCheckBetTime = 0;
	
	if (!isNull(betObjWhenGetNewBets.BetOrderInfo[main])) {
		mainInfo = betObjWhenGetNewBets.BetOrderInfo[main];
		mainObjKey = Object.keys(mainInfo).sort();
	}
	
	if(mainInfo.main_order_status == 1){// 一般
		for (var m = 0; m < mainObjKey.length; m++) {
			if (!isNull(mainInfo[mainObjKey[m]])) {
				if (isNumber(parseInt(mainObjKey[m]))) {
					midInfo = mainInfo[mainObjKey[m]];
					if(!isNull(midInfo.stop_betting_time) && !isNull(midInfo.mid_order_status)){
						if(nowTime < parseInt(midInfo.stop_betting_time)*1000){
							midCheckBetTime++;
						}else if(parseInt(midInfo.mid_order_status) == 3){
							midCheckBetTime++;
						}
					}
				}else{
					break;
				}
			}
		}
	}else if(mainInfo.main_order_status == 3){// 撤除
		for (var m = 0; m < mainObjKey.length; m++) {
			if (!isNull(mainInfo[mainObjKey[m]])) {
				if (isNumber(parseInt(mainObjKey[m]))) {
					midInfo = mainInfo[mainObjKey[m]];
					if(!isNull(midInfo.mid_order_status)){
						if(midInfo.mid_order_status == 3){// 撤除
							midCheckBetTime++;
						}
					}
				}else{
					break;
				}
			}
		}
	}
	
	return midCheckBetTime;
}

function checkLastMid(main){
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	
	var mainInfo = '';
	var midKey = [];
	var midInfo = '';
	var mainOrderStatus = 0;
	var midCheckBetTime = false;
	var nowTime = (new Date().getTime() - (parseInt(getEle('diffms').value)) + 1500);

	if (!isNull(betObjWhenGetNewBets.BetOrderInfo[main])) {
		mainInfo = betObjWhenGetNewBets.BetOrderInfo[main];
		midKey = Object.keys(mainInfo).sort();
	}
	if (!isNull(mainInfo.main_order_status)) {
		mainOrderStatus = mainInfo.main_order_status;
	}

	for (var m in midKey) {
		if (!isNull(mainInfo[midKey[m]]) ? (isNumber(parseInt(midKey[m])) ? !isNull(mainInfo[midKey[m]].check_bet_time ? (mainInfo[midKey[m]].check_bet_time == 1 && nowTime >= parseInt(mainInfo[midKey[m]].stop_betting_time)*1000) : false) : false ) : false) {
			midCheckBetTime = true;
			break;
		}
	}
	if(mainOrderStatus == 1 && midCheckBetTime){
		return true;
	}else{
		return false;
	}
}
// mid
function showMidTable() {
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	
	var mainTableInfo = '';
	var mainObjKey = [];
	var midTableInfo = '';
	var midObjKey = [];
	var midTable = getEle('MyBetDiv').getElementsByTagName("table")[0];
	var midTableStr = [];
	
	getEle('MyBetDiv').getElementsByTagName("button")[0].disabled = true;

	if (!isNull(betObjWhenGetNewBets.BetOrderInfo)) {
		mainTableInfo = betObjWhenGetNewBets.BetOrderInfo;
		mainObjKey = Object.keys(mainTableInfo).sort();
	}
	midTableStr
			.push('<tr><th></th><th>游戏</th><th>玩法</th><th>盤口</th><th>投注時間</th><th>期号</th><th>开奖号</th><th>投注内容</th><th>投注金额</th><th>奖金</th><th>状态</th><th>操作</th></tr>');
	for (var i = nowFirstCount; i < nowLastCount; i++) {
		if (!isNull(mainTableInfo[mainObjKey[i]])) {
			midTableInfo = mainTableInfo[mainObjKey[i]];
			midObjKey = Object.keys(midTableInfo).sort();
			for (var j = 0; j < midObjKey.length; j++) {
				if (isNumber(parseInt(midObjKey[j]))) {
					midTableStr.push('<tr>');
					midTableStr.push('	<td>');
					midTableStr.push('		<label class="container-radio">');
					if(checkLastMid(mainObjKey[i])){
						midTableStr.push('			<input type="checkbox" name="radio" id="radio' + i + '" onchange="openCancleChk('+MID_BET_ORDER+')" value="'+mainObjKey[i]+'">');
					}else{
						midTableStr.push('			<input type="checkbox" name="radio" id="radio' + i + '" disabled>');
					}
					midTableStr.push('			<span class="checkmark"></span>');
					midTableStr.push('		</label>');
					midTableStr.push('	</td>');
					midTableStr.push('<td>' + checkNull(mainTableInfo[mainObjKey[i]].title) + '</td>');
					midTableStr.push('<td>' + checkNull(mainTableInfo[mainObjKey[i]].lottery_title) + '</td>');
					midTableStr.push('<td>' + handiCapName[checkNull(mainTableInfo[mainObjKey[i]].handi_cap)] + '</td>');
					midTableStr.push('<td>' + checkNull(mainTableInfo[mainObjKey[i]].order_time) + '</td>');
					midTableStr.push('<td>' + checkNull(midTableInfo[midObjKey[j]].period_num) + '</td>');
					midTableStr.push('<td>' + cutBetData(midTableInfo[midObjKey[j]].kj_data) + '</td>');
					midTableStr.push('<td>' + cutBetData(changeLotteryNumToText(mainTableInfo[mainObjKey[i]].auth_id , mainTableInfo[mainObjKey[i]].bet_data)) + '</td>');
					midTableStr.push('<td>' + Math.floor(checkNull(midTableInfo[midObjKey[j]].mid_amount)*100)/100 + '</td>');
					midTableStr.push('<td>' + Math.floor(checkNull(midTableInfo[midObjKey[j]].mid_bonus)*100)/100 + '</td>');
					midTableStr.push('<td>' + midOrderStatus[checkNull(midTableInfo[midObjKey[j]].check_mid_com_time)] + '</td>');
					midTableStr.push('<td>');
					midTableStr.push('	<a href="javascript:void(0);" onclick="showMidDetailDiv(');
					midTableStr.push('	\'' + checkNull(mainObjKey[i]) + '\',');
					midTableStr.push('	\'' + midObjKey[j] + '\',');
					midTableStr.push('	\'' + MID_BET_ORDER + '\'');
					midTableStr.push('	)">詳細</a>');
					if (checkLastMid(mainObjKey[i])) {
						midTableStr.push('<a href="javascript:void(0);" onclick="conformCancleOrder(\'' + mainObjKey[i] + '\');" style="display: inline-block;">撤單</a>');
						midTableStr.push('</td>');
					} else {
						midTableStr.push('<a href="javascript:void(0);" onclick="conformCancleOrder(\'' + mainObjKey[i] + '\');" style="visibility:hidden;">撤單</a>');
						midTableStr.push('</td>');
					}
					midTableStr.push('</tr>');
				}else{
					break;
				}
			}
		}
	}
	
	setInnerHTML(midTable, midTableStr.join(''));
}
function showMidDetailDiv(main, mid,type) {
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	
	if(type == MID_BET_ORDER){
		document.getElementsByClassName('mybet')[0].style.display = 'none';
		document.getElementsByClassName('mybet-detail')[0].style.display = 'block';
	}else if (type == MAIN_BET_ORDER){
		document.getElementsByClassName('myadd-detail')[0].style.display = 'none';
		document.getElementsByClassName('mybet-detail')[1].style.display = 'block';
	}
	
	var mainDetailInfo = '';
	var mainObjKey = [];
	var midDetailInfo = '';
	var midObjKey = [];
	var totalAmount = '';
	var midDetailStr = [];

	if (!isNull(betObjWhenGetNewBets.BetOrderInfo[main])) {
		mainDetailInfo = betObjWhenGetNewBets.BetOrderInfo[main];
	}
	if (!isNull(mainDetailInfo[mid])) {
		midDetailInfo = mainDetailInfo[mid];
	}
	if (!isNull(midDetailInfo.mid_amount) && !isNull(midDetailInfo.mid_bonus) && !isNull(midDetailInfo.mid_fan_den)) {
		totalAmount = Math.floor(midDetailInfo.mid_bonus*100 - midDetailInfo.mid_amount*100 + midDetailInfo.mid_fan_den*100)/100;
	}
	if(type == MID_BET_ORDER){
		midDetailStr.push('<button onclick="openＭyBet()" id="btn-close-back">╳</button>');
	}else if (type == MAIN_BET_ORDER){
		midDetailStr.push('<button onclick="openMyAddDetail()" id="btn-close-back">╳</button>');
	}
	midDetailStr.push('<div class="record-area">');
	midDetailStr.push('<h6><span>游戏玩法</span></h6>');
	midDetailStr.push('<table class="kind">');
	midDetailStr.push('	<tbody>');
	midDetailStr.push('		<tr>');
	midDetailStr.push('			<td>' + checkNull(mainDetailInfo.title) + '</td>');// local
	midDetailStr.push('			<td>' + checkNull(midDetailInfo.period_num) + '</td>');// 期號
	midDetailStr.push('			<td>' + checkNull(mainDetailInfo.lottery_title) + '</td>');// played
	midDetailStr.push('			<td>' + handiCapName[checkNull(mainDetailInfo.handi_cap)] + '</td>');// handi_cap
	if (!isNull(midDetailInfo.kj_data) && midDetailInfo.kj_data != '') {
		midDetailStr.push('<td>');
		for (var kj = 0; kj < midDetailInfo.kj_data.split(',').length; kj++) {
			midDetailStr.push('<span>' + midDetailInfo.kj_data.split(',')[kj] + '</span>');
		}
		midDetailStr.push('</td>');
	} else {
		midDetailStr.push('<td>未開奖</td>');
	}
	midDetailStr.push('		</tr>');
	midDetailStr.push('	</tbody>');
	midDetailStr.push('</table>');
	midDetailStr.push('<h6>投注信息</h6>');
	midDetailStr.push('<table class="bet-info">');
	midDetailStr.push('	<tbody>');
	midDetailStr.push('		<tr>');
	midDetailStr.push('			<td colspan="11">');
	midDetailStr.push(				'<span>注单详情：(');
	midDetailStr.push(checkNull(main.split('-')[1]) + '-' + checkNull(main.split('-')[2]) + '-' + checkNull(mid));
	midDetailStr.push('				)</span>');
	midDetailStr.push('			</td>');
	midDetailStr.push('		</tr>');
	midDetailStr.push('		<tr>');
	midDetailStr.push('		<tr>');
	midDetailStr
			.push('			<th>奖金组</th><th>单注单倍最高奖金</th><th>投注金额</th><th>返点</th><th>奖金</th><th>盈亏</th><th>倍数</th><th>注数</th><th>模式</th><th>投注时间</th><th>状态</th>');
	midDetailStr.push('		</tr>');
	midDetailStr.push('		<tr>');
	midDetailStr.push('			<td>' + checkNull(mainDetailInfo.bonus_set_ratio) + '</td>');// 奖金组
	midDetailStr.push('			<td>' + checkNull(Math.floor(midDetailInfo.max_bonus*100)/100) + '</td>');// 单注单倍最高奖金
	midDetailStr.push('			<td>' + checkNull(Math.floor(midDetailInfo.mid_amount*100)/100) + '</td>');// 投注金额
	midDetailStr.push('			<td>' + checkNull(Math.floor(midDetailInfo.mid_fan_den*100)/100) + '</td>');// 返点
	midDetailStr.push('			<td>' + checkNull(Math.floor(midDetailInfo.mid_bonus*100)/100) + '</td>');// 奖金
	midDetailStr.push('			<td>' + totalAmount + '</td>');// 盈亏
	midDetailStr.push('			<td>' + checkNull(midDetailInfo.no_of_bet_times) + '倍</td>');// 倍数
	midDetailStr.push('			<td>' + checkNull(midDetailInfo.no_of_bet) + '</td>');// 注数
	midDetailStr.push('			<td>' + moneyUnitType(mainDetailInfo.money_unit) + '</td>');// 模式
	midDetailStr.push('			<td>' + checkNull(mainDetailInfo.order_time) + '</td>');// 投注时间
	midDetailStr.push('			<td>' + midOrderStatus[checkNull(midDetailInfo.check_mid_com_time)] + '</td>');// 状态
	midDetailStr.push('		</tr>');
	midDetailStr.push('	</tbody>');
	midDetailStr.push('</table>');
	if (type == MAIN_BET_ORDER){
		midDetailStr.push('<h6>追号管理</h6>');
		midDetailStr.push('<table class="bet-info">');
		midDetailStr.push('	<tbody>');
		midDetailStr.push('		<tr><td colspan="11"><span>追号信息</span></td></tr>');
		midDetailStr.push('		<tr><th>是否追号:</th><th>追号设置:</th></tr>');
		midDetailStr.push('		<tr>');
		if (parseInt(mainDetailInfo.main_order_type) == 1 || parseInt(mainDetailInfo.main_order_type) == 3) {// 1=中奖即停
																												// 3=兩者都停
			midDetailStr.push('<td>是</td>');// 中奖即停
		} else if (parseInt(mainDetailInfo.main_order_type) == 0 || parseInt(mainDetailInfo.main_order_type) == 2) {// 0=中奖不停
																													// 2=跳停
			midDetailStr.push('<td>否</td>');// 中奖即停
		} else {
			midDetailStr.push('<td>error</td>');// 中奖即停
		}
		midDetailStr.push('			<td>'+mainOrdertype[checkNull(mainDetailInfo.main_order_type)]+'</td>');
		midDetailStr.push('</tr>');
		midDetailStr.push('	</tbody>');
		midDetailStr.push('</table>');
	}
	midDetailStr.push('<h6>注单信息</h6>');
	midDetailStr.push('<table class="bill-info">');
	midDetailStr.push('	<tbody>');
	midDetailStr.push('		<tr>');
	midDetailStr.push('			<td>' + changeLotteryNumToText(mainDetailInfo.auth_id , mainDetailInfo.bet_data) + '</td>');
	midDetailStr.push('		</tr>');
	midDetailStr.push('	</tbody>');
	midDetailStr.push('</table>');
	midDetailStr.push('</div>');
	midDetailStr.push('<div class="btn-area">');
	if(type == MID_BET_ORDER){
		midDetailStr.push('	<button onclick="openＭyBet()">回上页</button>');
	}else if (type == MAIN_BET_ORDER){
		midDetailStr.push('	<button onclick="openMyAddDetail()">回上页</button>');
	}
	midDetailStr.push('</div>');
	
	if(type == MID_BET_ORDER){
		document.getElementsByClassName('mybet-detail')[0].innerHTML = midDetailStr.join('');
	}else if (type == MAIN_BET_ORDER){
		document.getElementsByClassName('mybet-detail')[1].innerHTML = midDetailStr.join('');
	}
}
// main
function showMainTable() {
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	var mainTableInfo = '';
	var mainObjKey = [];
	var mainTable = getEle('MyAddDiv').getElementsByTagName("table")[0];
	var mainTableStr = [];
	
	getEle('MyAddDiv').getElementsByTagName("button")[0].disabled = true;

	if (!isNull(betObjWhenGetNewBets.BetOrderInfo)) {
		mainTableInfo = betObjWhenGetNewBets.BetOrderInfo;
		mainObjKey = (Object.keys(mainTableInfo)).sort();
	}
	mainTableStr
			.push('<tr><th></th><th>游戏</th><th>玩法</th><th>盤口</th><th>起始奖期</th><th>剩余期数</th><th>总追号金额</th><th>已中奖金额</th><th>中奖即停</th><th>状态</th><th>操作</th></tr>');
	for (var i = nowFirstCount; i < nowLastCount; i++) {
		if (!isNull(mainTableInfo[mainObjKey[i]])) {
			mainTableStr.push('<tr>');
			mainTableStr.push('	<td>');
			mainTableStr.push('		<label class="container-radio">');
			if(checkLastMid(mainObjKey[i])){
				mainTableStr.push('			<input type="checkbox" name="mainRadio" id="mainRadio' + i + '" onchange="openCancleChk('+MAIN_BET_ORDER+')" value="'+mainObjKey[i]+'">');
			}else{
				mainTableStr.push('			<input type="checkbox" name="mainRadio" id="mainRadio' + i + '" disabled>');
			}
			mainTableStr.push('			<span class="checkmark"></span>');
			mainTableStr.push('		</label>');
			mainTableStr.push('	</td>');
			mainTableStr.push('<td>' + checkNull(mainTableInfo[mainObjKey[i]].title) + '</td>');// 游戏
			mainTableStr.push('<td>' + checkNull(mainTableInfo[mainObjKey[i]].lottery_title) + '</td>');// 玩法
			mainTableStr.push('<td>' + handiCapName[checkNull(mainTableInfo[mainObjKey[i]].handi_cap)] + '</td>');// 盤口
			mainTableStr.push('<td>' + checkNull(mainTableInfo[mainObjKey[i]].start_period_num) + '</td>');// 起始奖期
			mainTableStr.push('<td>' + checkMidComplete(mainObjKey[i]) + '/');// 剩余期数分子
			mainTableStr.push(checkNull(mainTableInfo[mainObjKey[i]].no_of_period) + '</td>');// 剩余期数分母
			mainTableStr.push('<td>' + Math.floor(checkNull(mainTableInfo[mainObjKey[i]].main_amount)*100)/100 + '</td>');// 总追号金额
			mainTableStr.push('<td>' + Math.floor(checkNull(mainTableInfo[mainObjKey[i]].main_bonus)*100)/100 + '</td>');// 已中奖金额
			if (!isNull(mainTableInfo[mainObjKey[i]].main_order_type)) {
				var orderType = parseInt(mainTableInfo[mainObjKey[i]].main_order_type);
				if (orderType == 1 || orderType == 3) {// 1=中奖即停 3=兩者都停
					mainTableStr.push('<td>是</td>');// 中奖即停
				} else if (orderType == 0 || orderType == 2) {// 0=中奖不停 2=跳停
					mainTableStr.push('<td>否</td>');// 中奖即停
				} else {
					mainTableStr.push('<td>error</td>');// 中奖即停
				}
			} else {
				mainTableStr.push('<td>error</td>');// 中奖即停
			}
			if(!isNull(mainTableInfo[mainObjKey[i]].main_order_status)){
				if(checkNull(mainTableInfo[mainObjKey[i]].main_order_status) == 1 && checkMidComplete(mainObjKey[i]) == 0){
					mainTableStr.push('<td>已完成</td>');// 状态
				}else{
					mainTableStr.push('<td>' + mainOrderStatus[checkNull(mainTableInfo[mainObjKey[i]].main_order_status)] + '</td>');// 状态
				}
			}
			mainTableStr.push('<td>');
			mainTableStr.push('<a href="javascript:void(0);" onclick="showMainDetailDiv(');
			mainTableStr.push('\'' + checkNull(mainObjKey[i]) + '\',');
			mainTableStr.push('\'' + i + '\'');
			mainTableStr.push(')">詳細</a>');
			if (checkLastMid(mainObjKey[i])) {
				mainTableStr.push('<a href="javascript:void(0);" onclick="conformCancleOrder(\'' + mainObjKey[i] + '\');" style="display: inline-block;">撤單</a>');
				mainTableStr.push('</td>');
			} else {
				mainTableStr.push('<a href="javascript:void(0);" onclick="conformCancleOrder(\'' + mainObjKey[i] + '\');" style="visibility:hidden;">撤單</a>');
				mainTableStr.push('</td>');
			}
			mainTableStr.push('</tr>');
		}
	}
	
	setInnerHTML(mainTable, mainTableStr.join(''));
}
function showMainDetailDiv(main,i) {
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	
	document.getElementsByClassName('myadd')[0].style.display = 'none';
	document.getElementsByClassName('myadd-detail')[0].style.display = 'block';

	var mainDetailInfo = '';
	var mainObjKey = [];
	var midDetailInfo = [];
	var mainDetailStr = [];
	var midCount = 0;

	if (!isNull(betObjWhenGetNewBets.BetOrderInfo[main])) {
		mainDetailInfo = betObjWhenGetNewBets.BetOrderInfo[main];
		mainObjKey = Object.keys(mainDetailInfo).sort();
	}

	for (var mid = 0; mid < mainObjKey.length; mid++) {
		if (isNumber(parseInt(mainObjKey[mid]))) {
			midCount++;
		}else{
			break;
		}
	}
	secMidGetPage(midCount);

	mainDetailStr.push('<button onclick="openMyAdd()" id="btn-close-back">╳</button>');
	mainDetailStr.push('<div class="record-area">');
	mainDetailStr.push('	<div class="content">');
	mainDetailStr.push('		<h6>追号纪录</h6>');
	mainDetailStr.push('		<p class="kind">');
	mainDetailStr.push('			<span>' + checkNull(mainDetailInfo.title) + '</span>');// title
	mainDetailStr.push('			<span>' + checkNull(mainDetailInfo.lottery_title) + '</span>');// l_title
	mainDetailStr.push('		</p>');
	mainDetailStr.push('		<h6>投注信息</h6>');
	mainDetailStr.push('		<table class="detail">');
	mainDetailStr.push('			<tbody>');
	mainDetailStr.push('				<tr><td colspan="8">注单详情：(' + checkNull(main.split('-')[1]) + '-' + checkNull(main.split('-')[2]) + ')</td></tr>');// main_order_id
	mainDetailStr.push('				<tr><th>奖金组</th><th>单注单倍最高奖金</th><th>投注金额</th><th>追号类型</th><th>注数</th><th>模式</th><th>开始时间</th><th>状态</th></tr>');
	mainDetailStr.push('				<tr>');
	mainDetailStr.push('					<td>' + Math.floor(checkNull(mainDetailInfo.bonus_set_ratio)*100)/100 + '</td>');// 奖金组
	mainDetailStr.push('					<td>' + Math.floor(checkNull(mainDetailInfo[mainObjKey[0]].max_bonus)*100)/100 + '</td>');// 单注单倍最高奖金
	mainDetailStr.push('					<td>' + Math.floor(checkNull(mainDetailInfo.main_amount)*100)/100 + '</td>');// 投注金额
	mainDetailStr.push('					<td>' + mainOrdertype[checkNull(mainDetailInfo.main_order_type)] + '</td>');// 追号类型
	mainDetailStr.push('					<td>' + parseInt(checkNull(mainDetailInfo[mainObjKey[0]].no_of_bet) * checkNull(mainDetailInfo.no_of_period)) + '</td>');// 注数(追號期數)
	mainDetailStr.push('					<td>' + moneyUnitType(mainDetailInfo.money_unit) + '</td>');// 模式
	mainDetailStr.push('					<td>' + checkNull(mainDetailInfo.order_time) + '</td>');// 开始时间
	if(!isNull(mainDetailInfo.main_order_status)){
		if(checkNull(mainDetailInfo.main_order_status) == 1 && checkMidComplete(main) == 0){
			mainDetailStr.push('<td>已完成');// 状态
		}else{
			mainDetailStr.push('<td>' + mainOrderStatus[checkNull(mainDetailInfo.main_order_status)]);// 状态
		}
	}
	if(checkLastMid(main)){// 1=未開奖+未過期
		mainDetailStr.push('<button onclick="conformCancleOrder(\'' + main + '\');">终止追号</button>');
	}
	mainDetailStr.push('					</td>');
	mainDetailStr.push('				</tr>');
	mainDetailStr.push('			</tbody>');
	mainDetailStr.push('		</table>');
	mainDetailStr.push('		<h6>追号号码</h6>');
	mainDetailStr.push('		<div class="add-num"><p>' + changeLotteryNumToText(mainDetailInfo.auth_id , mainDetailInfo.bet_data) + '</p></div>');
	mainDetailStr.push('		<h6>追号清单</h6>');
	mainDetailStr.push('		<div class="list-area">');
	mainDetailStr.push('			<table id="secMidTable">');
	mainDetailStr.push('				<tbody>');
	mainDetailStr.push('				</tbody>');
	mainDetailStr.push('			</table>');
	mainDetailStr.push('			<div class="pagination">');
	mainDetailStr.push('				<ul>');
	mainDetailStr.push('					<li>');
	mainDetailStr.push('						<button onclick="previousDetailPage(\'' + main + '\');">&lt;</button>');
	mainDetailStr.push('						<span>第：<i id="secMidNowPage">1</i><i>/</i><i>'+Math.ceil(midCount / secMidPageCount)+'</i>頁,共<i>'+midCount+'</i><i>条</i></span>');
	mainDetailStr.push('						<button onclick="nextDetailPage(\'' + main + '\');">&gt;</button>');
	mainDetailStr.push('					</li>');
	mainDetailStr.push('				</ul>');
	mainDetailStr.push('			</div>');
	mainDetailStr.push('		</div>');
	mainDetailStr.push('	</div>');
	mainDetailStr.push('</div>');
	mainDetailStr.push('<div class="btn-area">');
	mainDetailStr.push('	<button onclick="openMyAdd()">回上页</button>');
	mainDetailStr.push('</div>');

	document.getElementsByClassName('myadd-detail')[0].innerHTML = mainDetailStr.join('');
	showSecMidTable(main);
}
function showSecMidTable(main) {
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	var mainDetailInfo = '';
	var mainObjKey = [];
	var midDetailInfo = [];
	var secMidStr = [];

	if (!isNull(betObjWhenGetNewBets.BetOrderInfo[main])) {
		mainDetailInfo = betObjWhenGetNewBets.BetOrderInfo[main];
		mainObjKey = Object.keys(mainDetailInfo).sort();
	}

	for (var mid = secMidNowFirstCount; mid < secMidNowLastCount; mid++) {
		if (isNumber(parseInt(mainObjKey[mid]))) {
			midDetailInfo.push(mainDetailInfo[mainObjKey[mid]]);
		}else{
			break;
		}
	}
	
	secMidStr.push('<tr><th>奖期</th><th>倍数</th><th>金额</th><th>奖金</th><th>状态</th><th>注单详情</th></tr>');
	var countNum = secMidNowFirstCount;
	for (var m = 0; m < midDetailInfo.length; m++) {
		secMidStr.push('<tr>');
		secMidStr.push('	<td>' + checkNull(midDetailInfo[m].period_num) + '</td>');
		secMidStr.push('	<td>' + Math.floor(checkNull(midDetailInfo[m].no_of_bet_times)*100)/100 + '</td>');
		secMidStr.push('	<td>' + Math.floor(checkNull(midDetailInfo[m].mid_amount)*100)/100 + '</td>');
		secMidStr.push('	<td>' + Math.floor(checkNull(midDetailInfo[m].mid_bonus)*100)/100 + '</td>');
		secMidStr.push('	<td>' + midOrderStatus[checkNull(midDetailInfo[m].check_mid_com_time)] + '</td>');
		secMidStr.push('	<td>');
		secMidStr.push('		<a href="javascript:void(0);" onclick="showMidDetailDiv(\'' + checkNull(main) + '\',\'' + mainObjKey[countNum] + '\',\''+MAIN_BET_ORDER+'\')" class="detail">详情</a>');
		secMidStr.push('	</td>');
		secMidStr.push('</tr>');
		countNum++;
	}
	setInnerHTML(getEle('secMidTable'), secMidStr.join(''));
}
// ////////內頁換頁
function secMidGetPage(val) {
	secMidNowPage = 1;
	secMidTotalPage = 1;
	secMidNowFirstCount = 0;
	secMidNowLastCount = 0;
	secMidTotalCount = 0;
	
	if (!isNull(val)) {
		secMidTotalCount = val;
	}

	if(Math.ceil(val / secMidPageCount) >= 1){
		secMidTotalPage = Math.ceil(val / secMidPageCount);
	}
		
	if ((secMidNowFirstCount + secMidPageCount) <= val) {
		secMidNowLastCount = secMidNowFirstCount + secMidPageCount;
	} else {
		secMidNowLastCount = val;
	}
}
function nextDetailPage(main) {
	if ((secMidNowPage + 1) <= secMidTotalPage) {
		getEle('secMidNowPage').innerHTML = secMidNowPage + 1;
		secMidNowPage = secMidNowPage + 1;
	} else {
		getEle('secMidNowPage').innerHTML = secMidTotalPage;
		secMidNowPage = secMidTotalPage;
	}

	if ((secMidNowFirstCount + secMidPageCount) <= secMidTotalCount) {
		secMidNowFirstCount = (secMidNowPage - 1) * secMidPageCount;
	}

	if ((secMidNowLastCount + secMidPageCount) <= secMidTotalCount) {
		secMidNowLastCount = (secMidNowPage * secMidPageCount);
	} else {
		secMidNowLastCount = secMidTotalCount;
	}

	showSecMidTable(main);
}
function lastMainPage(main) {
	getEle('secMidNowPage').innerHTML = secMidTotalCount;
	secMidNowPage = secMidTotalCount;
	secMidNowFirstCount = (secMidTotalCount - 1) * secMidPageCount;
	secMidNowLastCount = secMidTotalCount;

	showSecMidTable(main);
}
function previousDetailPage(main) {
	if ((secMidNowPage - 1) > 0) {
		getEle('secMidNowPage').innerHTML = secMidNowPage - 1;
		secMidNowPage = secMidNowPage - 1;
	} else {
		getEle('secMidNowPage').innerHTML = 1;
		secMidNowPage = 1;
	}

	if ((secMidNowFirstCount - secMidPageCount) >= 0) {
		secMidNowFirstCount = (secMidNowPage - 1) * secMidPageCount;
	} else {
		secMidNowFirstCount = 0;
	}

	if ((secMidNowLastCount - secMidPageCount) >= secMidPageCount) {
		secMidNowLastCount = (secMidNowPage * secMidPageCount);
	} else {
		secMidNowLastCount = secMidNowFirstCount + secMidPageCount;
	}

	showSecMidTable(main);
}
function firstMainPage(main) {
	getEle('secMidNowPage').innerHTML = 1;
	secMidNowPage = 1;
	secMidNowFirstCount = 0;
	secMidNowLastCount = secMidNowFirstCount + secMidPageCount;

	showSecMidTable(main);
}
// myToolFunction
function checkNull(val) {
	if (typeOf(val) === "null" || typeOf(val) === "undefined" || val == null) {
		return '';
	} else {
		return val;
	}
}
function cutBetData(val) {
	var BetData = '';
	if (!isNull(val) && val != '') {
		BetData = val;
		if (BetData.length > 10) {
			BetData = BetData.slice(0, 10) + '...';
		}
	}
	return BetData;
}
function moneyUnitType(val) {
	var moneyUnit = '';
	if (!isNull(val) && val != '') {
		moneyUnit = val;
		if (moneyUnit >= 1 && moneyUnit <= 2) {
			moneyUnit = moneyUnit.slice(0, 1) + '元';
		} else if (moneyUnit >= 0.1 && moneyUnit <= 0.2) {
			moneyUnit = moneyUnit.slice(2, 3) + '角';
		} else if (moneyUnit >= 0.01 && moneyUnit <= 0.02) {
			moneyUnit = moneyUnit.slice(3, 4) + '分';
		} else if (moneyUnit >= 0.001 && moneyUnit <= 0.002) {
			moneyUnit = moneyUnit.slice(4, 5) + '厘';
		} else {
			moneyUnit = '';
		}
	}
	return moneyUnit;
}
// publicToolFunction
function isNull(val) {
	if (typeOf(val) === "null" || typeOf(val) === "undefined" || val == null) {
		return true;
	} else {
		return false;
	}
}
function typeOf(o) {
	var _toString = Object.prototype.toString;
	var _type = {
		"undefined" : "undefined",
		"number" : "number",
		"boolean" : "boolean",
		"string" : "string",
		"[object Function]" : "function",
		"[object RegExp]" : "regexp",
		"[object Array]" : "array",
		"[object Date]" : "date",
		"[object Error]" : "error"
	}
	return _type[typeof o] || _type[_toString.call(o)] || (o ? o.constructor === {}.constructor ? "map" : "object" : "null");
}
var isNumber = function isNumber(val) {
	return typeOf(val) === "number" && isFinite(val);
}
function myBetOrderGetPage() {
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	
	nowPage = 1;
	totalPage = 1;
	nowFirstCount = 0;
	nowLastCount = 0;
	totalCount = 0;

	secMidNowPage = 1;
	secMidTotalPage = 1;
	secMidNowFirstCount = 0;
	secMidNowLastCount = 0;
	secMidTotalCount = 0;

	if (!isNull(betObjWhenGetNewBets.BetOrderInfo)) {
		totalCount = Object.keys(betObjWhenGetNewBets.BetOrderInfo).length;
	}
	
	if(Math.ceil(totalCount / pageCount) >= 1){
		totalPage = Math.ceil(totalCount / pageCount);
	}
		
	if ((nowFirstCount + pageCount) <= totalCount) {
		nowLastCount = nowFirstCount + pageCount;
	} else {
		nowLastCount = totalCount;
	}
}
function refreshPageByCancleOrder() {
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	
	totalPage = 1;

	if (!isNull(betObjWhenGetNewBets.BetOrderInfo)) {
		totalCount = Object.keys(betObjWhenGetNewBets.BetOrderInfo).length;
	}
	
	if(Math.ceil(totalCount / pageCount) >= 1){
		totalPage = Math.ceil(totalCount / pageCount);
	}
		
	if ((nowFirstCount + pageCount) <= totalCount) {
		nowLastCount = nowFirstCount + pageCount;
	} else {
		nowLastCount = totalCount;
	}
}
// 以下mid換頁
function nextMidPage() {
	if ((nowPage + 1) <= totalPage) {
		getEle('nowMidPage').innerHTML = nowPage + 1;
		nowPage = nowPage + 1;
	} else {
		getEle('nowMidPage').innerHTML = totalPage;
		nowPage = totalPage;
	}

	if ((nowFirstCount + pageCount) <= totalCount) {
		nowFirstCount = (nowPage - 1) * pageCount;
	}

	if ((nowLastCount + pageCount) <= totalCount) {
		nowLastCount = (nowPage * pageCount);
	} else {
		nowLastCount = totalCount;
	}

	showMidTable();
}
function lastMidPage() {
	getEle('nowMidPage').innerHTML = totalPage;
	nowPage = totalPage;
	nowFirstCount = (totalPage - 1) * pageCount;
	nowLastCount = totalCount;

	showMidTable();
}
function previousMidPage() {
	if ((nowPage - 1) > 0) {
		getEle('nowMidPage').innerHTML = nowPage - 1;
		nowPage = nowPage - 1;
	} else {
		getEle('nowMidPage').innerHTML = 1;
		nowPage = 1;
	}

	if ((nowFirstCount - pageCount) >= 0) {
		nowFirstCount = (nowPage - 1) * pageCount;
	} else {
		nowFirstCount = 0;
	}

	if ((nowLastCount - pageCount) >= pageCount) {
		nowLastCount = (nowPage * pageCount);
	} else {
		nowLastCount = nowFirstCount + pageCount;
	}

	showMidTable();
}
function firstMidPage() {
	getEle('nowMidPage').innerHTML = 1;
	nowPage = 1;
	nowFirstCount = 0;
	nowLastCount = nowFirstCount + pageCount;

	showMidTable();
}
// 以下Main換頁
function nextMainPage() {
	if ((nowPage + 1) <= totalPage) {
		getEle('nowMainPage').innerHTML = nowPage + 1;
		nowPage = nowPage + 1;
	} else {
		getEle('nowMainPage').innerHTML = totalPage;
		nowPage = totalPage;
	}

	if ((nowFirstCount + pageCount) <= totalCount) {
		nowFirstCount = (nowPage - 1) * pageCount;
	}

	if ((nowLastCount + pageCount) <= totalCount) {
		nowLastCount = (nowPage * pageCount);
	} else {
		nowLastCount = totalCount;
	}

	showMainTable();
}
function lastMainPage() {
	getEle('nowMainPage').innerHTML = totalPage;
	nowPage = totalPage;
	nowFirstCount = (totalPage - 1) * pageCount;
	nowLastCount = totalCount;

	showMainTable();
}
function previousMainPage() {
	if ((nowPage - 1) > 0) {
		getEle('nowMainPage').innerHTML = nowPage - 1;
		nowPage = nowPage - 1;
	} else {
		getEle('nowMainPage').innerHTML = 1;
		nowPage = 1;
	}

	if ((nowFirstCount - pageCount) >= 0) {
		nowFirstCount = (nowPage - 1) * pageCount;
	} else {
		nowFirstCount = 0;
	}

	if ((nowLastCount - pageCount) >= pageCount) {
		nowLastCount = (nowPage * pageCount);
	} else {
		nowLastCount = nowFirstCount + pageCount;
	}

	showMainTable();
}
function firstMainPage() {
	getEle('nowMainPage').innerHTML = 1;
	nowPage = 1;
	nowFirstCount = 0;
	nowLastCount = nowFirstCount + pageCount;

	showMainTable();
}
// 撤單
function conformCancleOrder(main) {
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	
	var mainInfo = '';
	var midInfo = '';
	var mainObjKey = '';

	var allOrder = new Object();
	var mainOrder = new Object();
	
	var showCancleKey = [];

	allOrder.orders = new Array();
	
	if(!isNull(betObjWhenGetNewBets.type)){
		var type = betObjWhenGetNewBets.type;
	}
	
	if(type == MID_BET_ORDER){
		var radio = 'radio';
	}else if(type == MAIN_BET_ORDER){
		var radio = 'mainRadio';
	}

	if (!isNull(main)) {
		if (!isNull(betObjWhenGetNewBets.BetOrderInfo)) {
			mainInfo = betObjWhenGetNewBets.BetOrderInfo;
			if(!isNull(mainInfo[main])){
				mainInfo = mainInfo[main];
			}
		}

		if(type == MID_BET_ORDER){
			if (!isNull(mainInfo[Object.keys(mainInfo).sort()[0]])) {
				midInfo = mainInfo[Object.keys(mainInfo).sort()[0]];
			}
		}else if(type == MAIN_BET_ORDER){
			if(!isNull(mainInfo.no_of_period)){
				if (!isNull(mainInfo[Object.keys(mainInfo).sort()[parseInt(mainInfo.no_of_period)-1]])) {
					midInfo = mainInfo[Object.keys(mainInfo).sort()[parseInt(mainInfo.no_of_period)-1]];
				}
			}
		}
		
		if (!isNull(mainInfo.local_id) && mainInfo.local_id != '' && mainInfo.local_id != 0 && !isNull(main.split('-')[2])
				&& main.split('-')[2] != '' && main.split('-')[2] != 0 && !isNull(midInfo.period_num) && midInfo.period_num != ''
				&& midInfo.period_num != 0 && !isNull(mainInfo.member_acc_id) && mainInfo.member_acc_id != '' && mainInfo.member_acc_id != 0) {

			mainOrder.localId = mainInfo.local_id;
			mainOrder.mainOrderId = main.split('-')[2];
			mainOrder.periodNum = midInfo.period_num;
			mainOrder.memberAccId = mainInfo.member_acc_id;

			allOrder.orders.push(mainOrder);
			showCancleKey.push(main);
		}
	} else {
		for (var cou = nowFirstCount; cou < nowLastCount; cou++) {
			if (getEle(radio + cou).checked) {
				if (!isNull(betObjWhenGetNewBets.BetOrderInfo)) {
					mainInfo = betObjWhenGetNewBets.BetOrderInfo;
				}
				mainObjKey = getEle(radio + cou).value;
				mainInfo = mainInfo[mainObjKey];
				
				if(type == MID_BET_ORDER){
					if (!isNull(mainInfo[Object.keys(mainInfo).sort()[0]])) {
						midInfo = mainInfo[Object.keys(mainInfo).sort()[0]];
					}
				}else if(type == MAIN_BET_ORDER){
					if(!isNull(mainInfo.no_of_period)){
						if (!isNull(mainInfo[Object.keys(mainInfo).sort()[parseInt(mainInfo.no_of_period)-1]])) {
							midInfo = mainInfo[Object.keys(mainInfo).sort()[parseInt(mainInfo.no_of_period)-1]];
						}
					}
				}
				
				if (!isNull(mainInfo.local_id) && mainInfo.local_id != '' && mainInfo.local_id != 0 && !isNull(mainObjKey.split('-')[2])
						&& mainObjKey.split('-')[2] != '' && mainObjKey.split('-')[2] != 0 && !isNull(midInfo.period_num) && midInfo.period_num != ''
						&& midInfo.period_num != 0 && !isNull(mainInfo.member_acc_id) && mainInfo.member_acc_id != '' && mainInfo.member_acc_id != 0) {
					mainOrder = new Object();

					mainOrder.localId = mainInfo.local_id;
					mainOrder.mainOrderId = mainObjKey.split('-')[2];
					mainOrder.periodNum = midInfo.period_num;
					mainOrder.memberAccId = mainInfo.member_acc_id;

					allOrder.orders.push(mainOrder);
					showCancleKey.push(mainObjKey);
				}
			}
		}
	}
	if (allOrder.orders.length > 0) {
		getEle('cancleInfo').value = JSON.stringify(allOrder);
		
		if(type == MID_BET_ORDER){
			showCancleDetail(showCancleKey,CANCLE_MID_BET_ORDER);
		}else if(type == MAIN_BET_ORDER){
			showCancleDetail(showCancleKey,CANCLE_MAIN_BET_ORDER);
		}
	}
}

function showCancleDetail(cancleKey,type){
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	
	var mainInfo = '';
	var mainObjKey = [];
	var midInfo = '';
	var midObjKey = [];
	
	if(type == CANCLE_MID_BET_ORDER){
		document.getElementsByClassName('mybet')[0].style.display = 'none';
		document.getElementsByClassName('mybet')[1].style.display = 'block';
		
		var midStr=[];
		
		midStr.push('<tr><th></th><th>游戏</th><th>玩法</th><th>盤口</th><th>投注時間</th><th>期号</th><th>投注内容</th><th>投注金额</th><th>奖金</th><th>状态</th></tr>');
		for(var mid = 0 ; mid < cancleKey.length ; mid++){
			if (!isNull(betObjWhenGetNewBets.BetOrderInfo)) {
				mainInfo = betObjWhenGetNewBets.BetOrderInfo;
				mainObjKey = cancleKey[mid];
			}
			if (!isNull(mainInfo[mainObjKey])) {
				midObjKey = Object.keys(mainInfo[mainObjKey]);
					if (!isNull(mainInfo[mainObjKey])) {
						mainInfo = mainInfo[mainObjKey];
						if(!isNull(mainInfo[midObjKey[0]])){
							midInfo = mainInfo[midObjKey[0]];
						}
						midStr.push('<tr>');
						midStr.push('	<td></td>');
						midStr.push('	<td>'+checkNull(mainInfo.title)+'</td>');
						midStr.push('	<td>'+checkNull(mainInfo.lottery_title)+'</td>');
						midStr.push('	<td>'+handiCapName[checkNull(mainInfo.handi_cap)]+'</td>');
						midStr.push('	<td>'+checkNull(mainInfo.order_time)+'</td>');
						midStr.push('	<td>'+checkNull(midInfo.period_num)+'</td>');
						midStr.push('	<td>'+cutBetData(changeLotteryNumToText(mainInfo.auth_id , mainInfo.bet_data))+'</td>');
						midStr.push('	<td>'+Math.floor(checkNull(midInfo.mid_amount)*100)/100+'</td>');
						midStr.push('	<td>'+Math.floor(checkNull(midInfo.mid_bonus)*100)/100+'</td>');
						midStr.push('	<td>'+midOrderStatus[checkNull(midInfo.check_mid_com_time)]+'</td>');
						midStr.push('</tr>');
					}
				}
			}
		
		var cantableStr = document.getElementsByClassName('mybet')[1].getElementsByTagName("table")[0];
		setInnerHTML(cantableStr, midStr.join(''));
	}else if(type == CANCLE_MAIN_BET_ORDER){
		document.getElementsByClassName('myadd')[0].style.display = 'none';
		document.getElementsByClassName('myadd')[1].style.display = 'block';
		document.getElementsByClassName('myadd-detail')[0].style.display = 'none';
		
		var mainStr=[];
		
		mainStr.push('<tr><th></th><th>游戏</th><th>玩法</th><th>盤口</th><th>起始奖期</th><th>剩余期数</th><th>总追号金额</th><th>已中奖金额</th><th>中奖即停</th><th>状态</th></tr>');
		for(var main = 0 ; main < cancleKey.length ; main++){
			if (!isNull(betObjWhenGetNewBets.BetOrderInfo)) {
				mainInfo = betObjWhenGetNewBets.BetOrderInfo;
				mainObjKey = cancleKey[main];
			}
			if (!isNull(mainInfo[mainObjKey])) {
				mainInfo = mainInfo[mainObjKey];
						
						mainStr.push('<tr>');
						mainStr.push('	<td></td>');
						mainStr.push('	<td>'+checkNull(mainInfo.title)+'</td>');
						mainStr.push('	<td>'+checkNull(mainInfo.lottery_title)+'</td>');
						mainStr.push('	<td>'+handiCapName[checkNull(mainInfo.handi_cap)]+'</td>');
						mainStr.push('	<td>'+checkNull(mainInfo.start_period_num)+'</td>');
						mainStr.push('	<td>'+checkMidComplete(mainObjKey)+'/'+checkNull(mainInfo.no_of_period)+'</td>');
						mainStr.push('	<td>'+checkNull(mainInfo.main_amount)+'</td>');
						mainStr.push('	<td>'+checkNull(mainInfo.main_bonus)+'</td>');
						if (!isNull(mainInfo.main_order_type)) {
							if (mainInfo.main_order_type == 1 || mainInfo.main_order_type == 3) {// 1=中奖即停
																									// 3=兩者都停
								mainStr.push('<td>是</td>');// 中奖即停
							} else if (mainInfo.main_order_type == 0 || mainInfo.main_order_type == 2) {// 0=中奖不停
																										// 2=跳停
								mainStr.push('<td>否</td>');// 中奖即停
							} else {
								mainStr.push('<td>error</td>');// 中奖即停
							}
						} else {
							mainStr.push('<td>error</td>');// 中奖即停
						}
						if(!isNull(mainInfo.main_order_status)){
							if(checkNull(mainInfo.main_order_status) == 1 && checkMidComplete(mainObjKey) == 0){
								mainStr.push('<td>已完成</td>');// 状态
							}else{
								mainStr.push('<td>' + mainOrderStatus[checkNull(mainInfo.main_order_status)] + '</td>');// 状态
							}
						}
						mainStr.push('</tr>');
					
				}
			}
		
		var cantableStr = document.getElementsByClassName('myadd')[1].getElementsByTagName("table")[0];
		setInnerHTML(cantableStr, mainStr.join(''));
	}
}

// changeBtnFunction
function lotteryBetChangeOrderBtn(){
	var cancleAllMidBtn = getEle('MyBetDiv').getElementsByTagName("button")[0];
	var cancleAllMainBtn = getEle('MyAddDiv').getElementsByTagName("button")[0];
	var chkCancleAllMidBtn = document.getElementsByClassName('mybet')[1].getElementsByTagName("div")[1];
	var chkCancleAllMainBtn = document.getElementsByClassName('myadd')[1].getElementsByTagName("div")[1];
	
	cancleAllMidBtn.onclick = function(){conformCancleOrder();};
	cancleAllMainBtn.onclick = function(){conformCancleOrder();};
	
	cancleAllMidBtn.disabled = true;
	cancleAllMainBtn.disabled = true;
	
	chkCancleAllMidBtn.innerHTML = '<button class="confirm" onclick="lotteryBetOrderAjax_cancle('+CANCLE_MID_BET_ORDER+')">确认</button><button class="cancel" onclick="openＭyBet()">取消</button>';// 3撤注單
	chkCancleAllMainBtn.innerHTML = '<button class="confirm" onclick="lotteryBetOrderAjax_cancle('+CANCLE_MAIN_BET_ORDER+')">确认</button><button class="cancel" onclick="openMyAdd()">取消</button>';// 4撤追單
	
	getEle('MyBetDiv').getElementsByTagName("div")[0].getElementsByTagName("button")[1].onclick = function(){onclickMidBtn();};
	getEle('MyAddDiv').getElementsByTagName("div")[0].getElementsByTagName("button")[1].onclick = function(){onclickMainBtn();};
	
	document.getElementsByClassName("sixlotterybtn-area")[0].getElementsByTagName("button")[0].onclick = function(){onclickMidBtn();}; // 我的投注
	document.getElementsByClassName("sixlotterybtn-area")[0].getElementsByTagName("button")[1].onclick = function(){onclickMainBtn();}; // 我的追號
	
}
function onclickMidBtn(){
	betObjWhenGetNewBets = null;
	lotteryBetOrderAjax(1);
	showMidTable();
	changeMidPageFun();
	myBetOrderGetPage();
	openＭyBet();
}
function onclickMainBtn(){
	betObjWhenGetNewBets = null;
	lotteryBetOrderAjax(2);
	showMainTable();
	changeMainPageFun();
	myBetOrderGetPage();
	openMyAdd();
}
function changeMidPageFun() {
	var pageFun = getEle('MyBetDiv').getElementsByTagName("div")[2];
	var str = [];

	str.push('<a href="javascript:void(0);" onclick="firstMidPage();">«</a>');
	str.push('<a href="javascript:void(0);" onclick="previousMidPage();">&lt;</a>');
	str.push('<span>總頁數：<i id="nowMidPage">' + nowPage + '</i><span>/</span><i id="nowMidTotalPage">' + totalPage + '</i>頁</span>');
	str.push('<a href="javascript:void(0);" onclick="nextMidPage();">&gt;</a>');
	str.push('<a href="javascript:void(0);" onclick="lastMidPage();">»</a>');

	pageFun.innerHTML = str.join('');
}
function changeMainPageFun() {
	var pageFun = getEle('MyAddDiv').getElementsByTagName("div")[2];
	var str = [];

	str.push('<a href="javascript:void(0);" onclick="firstMainPage();">«</a>');
	str.push('<a href="javascript:void(0);" onclick="previousMainPage();">&lt;</a>');
	str.push('<span>總頁數：<i id="nowMainPage">' + nowPage + '</i><span>/</span><i id="nowMainTotalPage">' + totalPage + '</i>頁</span>');
	str.push('<a href="javascript:void(0);" onclick="nextMainPage();">&gt;</a>');
	str.push('<a href="javascript:void(0);" onclick="lastMainPage();">»</a>');

	pageFun.innerHTML = str.join('');
}

var v_lottory_betOrder_again_reload = 0;
var v_lottory_betOrder_again_used = 0;
var v_lottory_betOrder_again_time = 0;
function lotteryBetOrderAjax_again(type){
	console_Log("new lotteryBetOrderAjax_again("+type+")!!!");
	if(v_lottory_betOrder_again_used!=0){
		setTimeout("lotteryBetOrderAjax_again("+type+")", 1000);
		return false;
	}
	v_lottory_betOrder_again_reload ++;
	if(v_lottory_betOrder_again_reload>=5){
		if((new Date().getTime() - v_lottory_betOrder_again_time) >= 15000){
			v_lottory_betOrder_again_reload  = 1;
		} else {
			console_Log("Too many error connetions of lotteryBetOrderAjax_again("+type+")!");
			v_lottory_betOrder_again_used = 0;
			setTimeout("lotteryBetOrderAjax_again("+type+")", 15000);
			return false;
		}
	}
	enableLoadingPage();
	v_lottory_betOrder_again_time = new Date().getTime();
	v_lottory_betOrder_again_used = 1;
	var v_lottory_betOrder_again = null;
	v_lottory_betOrder_again = checkXHR(null);
	var data = getEle('cancleInfo').value;
	var tokenId = document.getElementsByName("tokenId")[0].value;
	var accId = document.getElementsByName("accId")[0].value;
	if (typeof v_lottory_betOrder_again != "undefined" && v_lottory_betOrder_again != null) {
		if(v_lottory_betOrder_again.timeout){
			v_lottory_betOrder_again.timeout = 10000;
		}
		v_lottory_betOrder_again.ontimeout = function() {
			if(v_lottory_betOrder_again != null){
				v_lottory_betOrder_again.abort();
				v_lottory_betOrder_again = null;
			}
			delete v_lottory_betOrder_again;
			v_lottory_betOrder_again = undefined;
			v_lottory_betOrder_again_used = 0;
			setTimeout("lotteryBetOrderAjax_again("+type+")", 1000);
			disableLoadingPage();
			return false;
		};
		v_lottory_betOrder_again.onerror = function() {
			if(v_lottory_betOrder_again != null){
				v_lottory_betOrder_again.abort();
				v_lottory_betOrder_again = null;
			}
			delete v_lottory_betOrder_again;
			v_lottory_betOrder_again = undefined;
			setTimeout("lotteryBetOrderAjax_again("+type+")", 1000);
			v_lottory_betOrder_again_used = 0;
			disableLoadingPage();
			return false;
		};
		var tmpStr = "type="+type+"&data="+encodeURIComponent(data)+"&tokenId="+tokenId+ "&accId="+accId;
		var tmpURL = "/CTT03QueryInfo/BetOrderQuery!queryOrders.php?date=" + new Date().getTime();
		if(typeof v_lottory_betOrder_again.onreadystatechange != "undefined" && typeof v_lottory_betOrder_again.readyState === "number"){
			v_lottory_betOrder_again.onreadystatechange = function(){
				if (v_lottory_betOrder_again.readyState == 4) {
					if ((v_lottory_betOrder_again.status>=200 && v_lottory_betOrder_again.status<300) || v_lottory_betOrder_again.status==304) {
						try {
							if(!v_lottory_betOrder_again.responseText || v_lottory_betOrder_again.responseText==null || v_lottory_betOrder_again.responseText==""){
								v_lottory_betOrder_again_used = 0;
								setTimeout("lotteryBetOrderAjax_again("+type+")", 1000);
								disableLoadingPage();
								return false;
							}
							var tmpJ = isJSON2(v_lottory_betOrder_again.responseText);
							if (tmpJ) {
								betObjWhenGetNewBets = tmpJ;
								getBetOrder(1);// 1=撤單刷新
								if(tmpJ && tmpJ["now"]){
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
							} else {
								betObjWhenGetNewBets = null;
								getBetOrder(1);// 1=撤單刷新
								v_lottory_betOrder_again_used = 0;
								v_lottory_betOrder_again_reload = 0;
								disableLoadingPage();
								return true;
							}
						} catch (error) {
							console_Log("lotteryBetOrderAjax_again error Message:"+error.message);
							console_Log("lotteryBetOrderAjax_again error Code:"+(error.number & 0xFFFF));
							console_Log("lotteryBetOrderAjax_again facility Code:"+(error.number>>16 & 0x1FFF));
							console_Log("lotteryBetOrderAjax_again error Name:"+error.name);
							v_lottory_betOrder_again_used = 0;
							setTimeout("lotteryBetOrderAjax_again("+type+")", 1000);
							disableLoadingPage();
							return false;
						} finally {
							if(v_lottory_betOrder_again!=null){
								v_lottory_betOrder_again.abort();
								v_lottory_betOrder_again = null;
							}
							delete v_lottory_betOrder_again;
							v_lottory_betOrder_again = undefined;
							v_lottory_betOrder_again_used = 0;
						}
						disableLoadingPage();
						return false;
					}
				}
			};
		} else {
			v_lottory_betOrder_again.onload = function(){
				if(typeof v_lottory_betOrder_again.readyState != "number"){
					if(!v_lottory_betOrder_again.responseText || v_lottory_betOrder_again.responseText==null || v_lottory_betOrder_again.responseText==""){
						if(v_lottory_betOrder_again!=null){
							v_lottory_betOrder_again.abort();
							v_lottory_betOrder_again = null;
						}
						delete v_lottory_betOrder_again;
						v_lottory_betOrder_again = undefined;
						v_lottory_betOrder_again_used = 0;
						setTimeout("lotteryBetOrderAjax_again("+type+")", 1000);
						disableLoadingPage();
						return false;
					} else {
						try {
							if(!v_lottory_betOrder_again.responseText || v_lottory_betOrder_again.responseText==null || v_lottory_betOrder_again.responseText==""){
								v_lottory_betOrder_again_used = 0;
								setTimeout("lotteryBetOrderAjax_again("+type+")", 1000);
								disableLoadingPage();
								return false;
							}
							var tmpJ = isJSON2(v_lottory_betOrder_again.responseText);
							if (tmpJ) {
								betObjWhenGetNewBets = tmpJ;
								getBetOrder(1);// 1=撤單刷新
								if(tmpJ && tmpJ["now"]){
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
							} else {
								betObjWhenGetNewBets = null;
								getBetOrder(1);// 1=撤單刷新
								v_lottory_betOrder_again_used = 0;
								v_lottory_betOrder_again_reload = 0;
								disableLoadingPage();
								return true;
							}
						} catch (error) {
							console_Log("lotteryBetOrderAjax_again error Message:"+error.message);
							console_Log("lotteryBetOrderAjax_again error Code:"+(error.number & 0xFFFF));
							console_Log("lotteryBetOrderAjax_again facility Code:"+(error.number>>16 & 0x1FFF));
							console_Log("lotteryBetOrderAjax_again error Name:"+error.name);
							v_lottory_betOrder_again_used = 0;
							setTimeout("lotteryBetOrderAjax_again("+type+")", 1000);
							disableLoadingPage();
							return false;
						} finally {
							if(v_lottory_betOrder_again!=null){
								v_lottory_betOrder_again.abort();
								v_lottory_betOrder_again = null;
							}
							delete v_lottory_betOrder_again;
							v_lottory_betOrder_again = undefined;
							v_lottory_betOrder_again_used = 0;
						}
						disableLoadingPage();
						return false;
					}
				} else if (v_lottory_betOrder_again.readyState == 4) {
					if ((v_lottory_betOrder_again.status>=200 && v_lottory_betOrder_again.status<300) || v_lottory_betOrder_again.status==304) {
						try {
							if(!v_lottory_betOrder_again.responseText || v_lottory_betOrder_again.responseText==null || v_lottory_betOrder_again.responseText==""){
								v_lottory_betOrder_again_used = 0;
								setTimeout("lotteryBetOrderAjax_again("+type+")", 1000);
								disableLoadingPage();
								return false;
							}
							var tmpJ = isJSON2(v_lottory_betOrder_again.responseText);
							if (tmpJ) {
								betObjWhenGetNewBets = tmpJ;
								getBetOrder(1);// 1=撤單刷新
								if(tmpJ && tmpJ["now"]){
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
							} else {
								betObjWhenGetNewBets = null;
								getBetOrder(1);// 1=撤單刷新
								v_lottory_betOrder_again_used = 0;
								v_lottory_betOrder_again_reload = 0;
								disableLoadingPage();
								return true;
							}
						} catch (error) {
							console_Log("lotteryBetOrderAjax_again error Message:"+error.message);
							console_Log("lotteryBetOrderAjax_again error Code:"+(error.number & 0xFFFF));
							console_Log("lotteryBetOrderAjax_again facility Code:"+(error.number>>16 & 0x1FFF));
							console_Log("lotteryBetOrderAjax_again error Name:"+error.name);
							v_lottory_betOrder_again_used = 0;
							setTimeout("lotteryBetOrderAjax_again("+type+")", 1000);
							disableLoadingPage();
							return false;
						} finally {
							if(v_lottory_betOrder_again!=null){
								v_lottory_betOrder_again.abort();
								v_lottory_betOrder_again = null;
							}
							delete v_lottory_betOrder_again;
							v_lottory_betOrder_again = undefined;
							v_lottory_betOrder_again_used = 0;
						}
						disableLoadingPage();
						return false;
					}
				}
			};
		}
		v_lottory_betOrder_again.open("POST", tmpURL, true);
		if(v_lottory_betOrder_again.setRequestHeader){
			v_lottory_betOrder_again.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		}
		v_lottory_betOrder_again.send(tmpStr.toString());
		delete tmpURL;
		delete tmpStr;
		tmpURL = undefined;
		tmpStr = undefined;
	}
}

var v_lottory_betOrder_cancle_reload = 0;
var v_lottory_betOrder_cancle_used = 0;
var v_lottory_betOrder_cancle_time = 0;
function lotteryBetOrderAjax_cancle(type){
	console_Log("new lotteryBetOrderAjax_cancle("+type+")!!!");
	if(v_lottory_betOrder_cancle_used!=0){
		setTimeout("lotteryBetOrderAjax_cancle("+type+")", 1000);
		return false;
	}
	v_lottory_betOrder_cancle_reload ++;
	if(v_lottory_betOrder_cancle_reload>=5){
		if((new Date().getTime() - v_lottory_betOrder_cancle_time) >= 15000){
			v_lottory_betOrder_cancle_reload  = 1;
		} else {
			console_Log("Too many error connetions of lotteryBetOrderAjax_cancle("+type+")!");
			v_lottory_betOrder_cancle_used = 0;
			setTimeout("lotteryBetOrderAjax_cancle("+type+")", 15000);
			return false;
		}
	}
	enableLoadingPage();
	v_lottory_betOrder_cancle_time = new Date().getTime();
	v_lottory_betOrder_cancle_used = 1;
	var v_lottory_betOrder_cancle = null;
	v_lottory_betOrder_cancle = checkXHR(null);
	var data = getEle('cancleInfo').value;
	var tokenId = document.getElementsByName("tokenId")[0].value;
	var accId = document.getElementsByName("accId")[0].value;
	if (typeof v_lottory_betOrder_cancle != "undefined" && v_lottory_betOrder_cancle != null) {
		if(v_lottory_betOrder_cancle.timeout){
			v_lottory_betOrder_cancle.timeout = 10000;
		}
		v_lottory_betOrder_cancle.ontimeout = function() {
			if(v_lottory_betOrder_cancle != null){
				v_lottory_betOrder_cancle.abort();
				v_lottory_betOrder_cancle = null;
			}
			delete v_lottory_betOrder_cancle;
			v_lottory_betOrder_cancle = undefined;
			v_lottory_betOrder_cancle_used = 0;
			setTimeout("lotteryBetOrderAjax_cancle("+type+")", 1000);
			disableLoadingPage();
			return false;
		};
		v_lottory_betOrder_cancle.onerror = function() {
			if(v_lottory_betOrder_cancle != null){
				v_lottory_betOrder_cancle.abort();
				v_lottory_betOrder_cancle = null;
			}
			delete v_lottory_betOrder_cancle;
			v_lottory_betOrder_cancle = undefined;
			setTimeout("lotteryBetOrderAjax_cancle("+type+")", 1000);
			v_lottory_betOrder_cancle_used = 0;
			disableLoadingPage();
			return false;
		};
		var tmpStr = "type="+type+"&data="+encodeURIComponent(data)+"&tokenId="+tokenId+ "&accId="+accId;
		var tmpURL = "/CTT03BetOrder/MemberBet!cancleBetOrder.php?date=" + new Date().getTime();
		if(typeof v_lottory_betOrder_cancle.onreadystatechange != "undefined" && typeof v_lottory_betOrder_cancle.readyState === "number"){
			v_lottory_betOrder_cancle.onreadystatechange = function(){
				if (v_lottory_betOrder_cancle.readyState == 4) {
					if ((v_lottory_betOrder_cancle.status>=200 && v_lottory_betOrder_cancle.status<300) || v_lottory_betOrder_cancle.status==304) {
						try {
							if(!v_lottory_betOrder_cancle.responseText || v_lottory_betOrder_cancle.responseText==null || v_lottory_betOrder_cancle.responseText==""){
								v_lottory_betOrder_cancle_used = 0;
								setTimeout("lotteryBetOrderAjax_cancle("+type+")", 1000);
								disableLoadingPage();
								return false;
							}
							var tmpJ = isJSON2(v_lottory_betOrder_cancle.responseText);
							if (tmpJ) {
								betObjWhenGetNewBets = tmpJ;
								getBetOrder(0);// 0=一般刷新
								if(tmpJ && tmpJ["now"]){
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
							} else {
								betObjWhenGetNewBets = null;
								getBetOrder(0);// 0=一般刷新
								v_lottory_betOrder_cancle_used = 0;
								v_lottory_betOrder_cancle_reload = 0;
								disableLoadingPage();
								return true;
							}
						} catch (error) {
							console_Log("lotteryBetOrderAjax_cancle error Message:"+error.message);
							console_Log("lotteryBetOrderAjax_cancle error Code:"+(error.number & 0xFFFF));
							console_Log("lotteryBetOrderAjax_cancle facility Code:"+(error.number>>16 & 0x1FFF));
							console_Log("lotteryBetOrderAjax_cancle error Name:"+error.name);
							v_lottory_betOrder_cancle_used = 0;
							setTimeout("lotteryBetOrderAjax_cancle("+type+")", 1000);
							disableLoadingPage();
							return false;
						} finally {
							if(v_lottory_betOrder_cancle!=null){
								v_lottory_betOrder_cancle.abort();
								v_lottory_betOrder_cancle = null;
							}
							delete v_lottory_betOrder_cancle;
							v_lottory_betOrder_cancle = undefined;
							v_lottory_betOrder_cancle_used = 0;
						}
						disableLoadingPage();
						return false;
					}
				}
			};
		} else {
			v_lottory_betOrder_cancle.onload = function(){
				if(typeof v_lottory_betOrder_cancle.readyState != "number"){
					if(!v_lottory_betOrder_cancle.responseText || v_lottory_betOrder_cancle.responseText==null || v_lottory_betOrder_cancle.responseText==""){
						if(v_lottory_betOrder_cancle!=null){
							v_lottory_betOrder_cancle.abort();
							v_lottory_betOrder_cancle = null;
						}
						delete v_lottory_betOrder_cancle;
						v_lottory_betOrder_cancle = undefined;
						v_lottory_betOrder_cancle_used = 0;
						setTimeout("lotteryBetOrderAjax_cancle("+type+")", 1000);
						disableLoadingPage();
						return false;
					} else {
						try {
							if(!v_lottory_betOrder_cancle.responseText || v_lottory_betOrder_cancle.responseText==null || v_lottory_betOrder_cancle.responseText==""){
								v_lottory_betOrder_cancle_used = 0;
								setTimeout("lotteryBetOrderAjax_cancle("+type+")", 1000);
								disableLoadingPage();
								return false;
							}
							var tmpJ = isJSON2(v_lottory_betOrder_cancle.responseText);
							if (tmpJ) {
								betObjWhenGetNewBets = tmpJ;
								getBetOrder(0);// 0=一般刷新
								if(tmpJ && tmpJ["now"]){
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
							} else {
								betObjWhenGetNewBets = null;
								getBetOrder(0);// 0=一般刷新
								v_lottory_betOrder_cancle_used = 0;
								v_lottory_betOrder_cancle_reload = 0;
								disableLoadingPage();
								return true;
							}
						} catch (error) {
							console_Log("lotteryBetOrderAjax_cancle error Message:"+error.message);
							console_Log("lotteryBetOrderAjax_cancle error Code:"+(error.number & 0xFFFF));
							console_Log("lotteryBetOrderAjax_cancle facility Code:"+(error.number>>16 & 0x1FFF));
							console_Log("lotteryBetOrderAjax_cancle error Name:"+error.name);
							v_lottory_betOrder_cancle_used = 0;
							setTimeout("lotteryBetOrderAjax_cancle("+type+")", 1000);
							disableLoadingPage();
							return false;
						} finally {
							if(v_lottory_betOrder_cancle!=null){
								v_lottory_betOrder_cancle.abort();
								v_lottory_betOrder_cancle = null;
							}
							delete v_lottory_betOrder_cancle;
							v_lottory_betOrder_cancle = undefined;
							v_lottory_betOrder_cancle_used = 0;
						}
						disableLoadingPage();
						return false;
					}
				} else if (v_lottory_betOrder_cancle.readyState == 4) {
					if ((v_lottory_betOrder_cancle.status>=200 && v_lottory_betOrder_cancle.status<300) || v_lottory_betOrder_cancle.status==304) {
						try {
							if(!v_lottory_betOrder_cancle.responseText || v_lottory_betOrder_cancle.responseText==null || v_lottory_betOrder_cancle.responseText==""){
								v_lottory_betOrder_cancle_used = 0;
								setTimeout("lotteryBetOrderAjax_cancle("+type+")", 1000);
								disableLoadingPage();
								return false;
							}
							var tmpJ = isJSON2(v_lottory_betOrder_cancle.responseText);
							if (tmpJ) {
								betObjWhenGetNewBets = tmpJ;
								getBetOrder(0);// 0=一般刷新
								if(tmpJ && tmpJ["now"]){
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
							} else {
								betObjWhenGetNewBets = null;
								getBetOrder(0);// 0=一般刷新
								v_lottory_betOrder_cancle_used = 0;
								v_lottory_betOrder_cancle_reload = 0;
								disableLoadingPage();
								return true;
							}
						} catch (error) {
							console_Log("lotteryBetOrderAjax_cancle error Message:"+error.message);
							console_Log("lotteryBetOrderAjax_cancle error Code:"+(error.number & 0xFFFF));
							console_Log("lotteryBetOrderAjax_cancle facility Code:"+(error.number>>16 & 0x1FFF));
							console_Log("lotteryBetOrderAjax_cancle error Name:"+error.name);
							v_lottory_betOrder_cancle_used = 0;
							setTimeout("lotteryBetOrderAjax_cancle("+type+")", 1000);
							disableLoadingPage();
							return false;
						} finally {
							if(v_lottory_betOrder_cancle!=null){
								v_lottory_betOrder_cancle.abort();
								v_lottory_betOrder_cancle = null;
							}
							delete v_lottory_betOrder_cancle;
							v_lottory_betOrder_cancle = undefined;
							v_lottory_betOrder_cancle_used = 0;
						}
						disableLoadingPage();
						return false;
					}
				}
			};
		}
		v_lottory_betOrder_cancle.open("POST", tmpURL, true);
		if(v_lottory_betOrder_cancle.setRequestHeader){
			v_lottory_betOrder_cancle.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		}
		v_lottory_betOrder_cancle.send(tmpStr.toString());
		delete tmpURL;
		delete tmpStr;
		tmpURL = undefined;
		tmpStr = undefined;
	}
}
var v_lottory_betOrder_reload = 0;
var v_lottory_betOrder_used = 0;
var v_lottory_betOrder_time = 0;

function lotteryBetOrderAjax(type){
	console_Log("new lotteryBetOrderAjax("+type+")!!!");
	if(v_lottory_betOrder_used!=0){
		setTimeout("lotteryBetOrderAjax("+type+")", 1000);
		return false;
	}
	v_lottory_betOrder_reload ++;
	if(v_lottory_betOrder_reload>=5){
		if((new Date().getTime() - v_lottory_betOrder_time) >= 15000){
			v_lottory_betOrder_reload  = 1;
		} else {
			console_Log("Too many error connetions of lotteryBetOrderAjax("+type+")!");
			v_lottory_betOrder_used = 0;
			setTimeout("lotteryBetOrderAjax("+type+")", 15000);
			return false;
		}
	}
	enableLoadingPage();
	v_lottory_betOrder_time = new Date().getTime();
	v_lottory_betOrder_used = 1;
	var v_lottory_betOrder = null;
	v_lottory_betOrder = checkXHR(null);
	var data = getEle('cancleInfo').value;
	var tokenId = document.getElementsByName("tokenId")[0].value;
	var accId = document.getElementsByName("accId")[0].value;
	if (typeof v_lottory_betOrder != "undefined" && v_lottory_betOrder != null) {
		if(v_lottory_betOrder.timeout){
			v_lottory_betOrder.timeout = 10000;
		}
		v_lottory_betOrder.ontimeout = function() {
			if(v_lottory_betOrder != null){
				v_lottory_betOrder.abort();
				v_lottory_betOrder = null;
			}
			delete v_lottory_betOrder;
			v_lottory_betOrder = undefined;
			v_lottory_betOrder_used = 0;
			setTimeout("lotteryBetOrderAjax("+type+")", 1000);
			disableLoadingPage();
			return false;
		};
		v_lottory_betOrder.onerror = function() {
			if(v_lottory_betOrder != null){
				v_lottory_betOrder.abort();
				v_lottory_betOrder = null;
			}
			delete v_lottory_betOrder;
			v_lottory_betOrder = undefined;
			setTimeout("lotteryBetOrderAjax("+type+")", 1000);
			v_lottory_betOrder_used = 0;
			disableLoadingPage();
			return false;
		};
		var tmpStr = "type="+type+"&data="+encodeURIComponent(data)+"&tokenId="+tokenId+ "&accId="+accId;
		var tmpURL = "/CTT03QueryInfo/BetOrderQuery!queryOrders.php?date=" + new Date().getTime();
		if(typeof v_lottory_betOrder.onreadystatechange != "undefined" && typeof v_lottory_betOrder.readyState === "number"){
			v_lottory_betOrder.onreadystatechange = function(){
				if (v_lottory_betOrder.readyState == 4) {
					if ((v_lottory_betOrder.status>=200 && v_lottory_betOrder.status<300) || v_lottory_betOrder.status==304) {
						try {
							if(!v_lottory_betOrder.responseText || v_lottory_betOrder.responseText==null || v_lottory_betOrder.responseText==""){
								v_lottory_betOrder_used = 0;
								setTimeout("lotteryBetOrderAjax("+type+")", 1000);
								disableLoadingPage();
								return false;
							}
							var tmpJ = isJSON2(v_lottory_betOrder.responseText);
							if (tmpJ) {
								betObjWhenGetNewBets = tmpJ;
								getBetOrder(0);// 0=一般刷新
								if(tmpJ && tmpJ["now"]){
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								getBetOrder(0);// 0=一般刷新
								v_lottory_betOrder_reload = 0;
								v_lottory_betOrder_used = 0;
								disableLoadingPage();
								return true;
							} else {
								betObjWhenGetNewBets = null;
								v_lottory_betOrder_used = 0;
								disableLoadingPage();
								return false;
							}
						} catch (error) {
							console_Log("lotteryBetOrderAjax error Message:"+error.message);
							console_Log("lotteryBetOrderAjax error Code:"+(error.number & 0xFFFF));
							console_Log("lotteryBetOrderAjax facility Code:"+(error.number>>16 & 0x1FFF));
							console_Log("lotteryBetOrderAjax error Name:"+error.name);
							v_lottory_betOrder_used = 0;
							setTimeout("lotteryBetOrderAjax("+type+")", 1000);
							disableLoadingPage();
							return false;
						} finally {
							if(v_lottory_betOrder!=null){
								v_lottory_betOrder.abort();
								v_lottory_betOrder = null;
							}
							delete v_lottory_betOrder;
							v_lottory_betOrder = undefined;
							v_lottory_betOrder_used = 0;
						}
						disableLoadingPage();
						return false;
					}
				}
			};
		} else {
			v_lottory_betOrder.onload = function(){
				if(typeof v_lottory_betOrder.readyState != "number"){
					if(!v_lottory_betOrder.responseText || v_lottory_betOrder.responseText==null || v_lottory_betOrder.responseText==""){
						if(v_lottory_betOrder!=null){
							v_lottory_betOrder.abort();
							v_lottory_betOrder = null;
						}
						delete v_lottory_betOrder;
						v_lottory_betOrder = undefined;
						v_lottory_betOrder_used = 0;
						setTimeout("lotteryBetOrderAjax("+type+")", 1000);
						disableLoadingPage();
						return false;
					} else {
						try {
							if(!v_lottory_betOrder.responseText || v_lottory_betOrder.responseText==null || v_lottory_betOrder.responseText==""){
								v_lottory_betOrder_used = 0;
								setTimeout("lotteryBetOrderAjax("+type+")", 1000);
								disableLoadingPage();
								return false;
							}
							var tmpJ = isJSON2(v_lottory_betOrder.responseText);
							if (tmpJ) {
								betObjWhenGetNewBets = tmpJ;
								getBetOrder(0);// 0=一般刷新
								if(tmpJ && tmpJ["now"]){
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								getBetOrder(0);// 0=一般刷新
								v_lottory_betOrder_reload = 0;
								v_lottory_betOrder_used = 0;
								disableLoadingPage();
								return true;
							} else {
								betObjWhenGetNewBets = null;
								v_lottory_betOrder_used = 0;
								disableLoadingPage();
								return false;
							}
						} catch (error) {
							console_Log("lotteryBetOrderAjax error Message:"+error.message);
							console_Log("lotteryBetOrderAjax error Code:"+(error.number & 0xFFFF));
							console_Log("lotteryBetOrderAjax facility Code:"+(error.number>>16 & 0x1FFF));
							console_Log("lotteryBetOrderAjax error Name:"+error.name);
							v_lottory_betOrder_used = 0;
							setTimeout("lotteryBetOrderAjax("+type+")", 1000);
							disableLoadingPage();
							return false;
						} finally {
							if(v_lottory_betOrder!=null){
								v_lottory_betOrder.abort();
								v_lottory_betOrder = null;
							}
							delete v_lottory_betOrder;
							v_lottory_betOrder = undefined;
							v_lottory_betOrder_used = 0;
						}
						disableLoadingPage();
						return false;
					}
				} else if (v_lottory_betOrder.readyState == 4) {
					if ((v_lottory_betOrder.status>=200 && v_lottory_betOrder.status<300) || v_lottory_betOrder.status==304) {
						try {
							if(!v_lottory_betOrder.responseText || v_lottory_betOrder.responseText==null || v_lottory_betOrder.responseText==""){
								v_lottory_betOrder_used = 0;
								setTimeout("lotteryBetOrderAjax("+type+")", 1000);
								disableLoadingPage();
								return false;
							}
							var tmpJ = isJSON2(v_lottory_betOrder.responseText);
							if (tmpJ) {
								betObjWhenGetNewBets = tmpJ;
								getBetOrder(0);// 0=一般刷新
								if(tmpJ && tmpJ["now"]){
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								getBetOrder(0);// 0=一般刷新
								v_lottory_betOrder_reload = 0;
								v_lottory_betOrder_used = 0;
								disableLoadingPage();
								return true;
							} else {
								betObjWhenGetNewBets = null;
								v_lottory_betOrder_used = 0;
								disableLoadingPage();
								return false;
							}
						} catch (error) {
							console_Log("lotteryBetOrderAjax error Message:"+error.message);
							console_Log("lotteryBetOrderAjax error Code:"+(error.number & 0xFFFF));
							console_Log("lotteryBetOrderAjax facility Code:"+(error.number>>16 & 0x1FFF));
							console_Log("lotteryBetOrderAjax error Name:"+error.name);
							v_lottory_betOrder_used = 0;
							setTimeout("lotteryBetOrderAjax("+type+")", 1000);
							disableLoadingPage();
							return false;
						} finally {
							if(v_lottory_betOrder!=null){
								v_lottory_betOrder.abort();
								v_lottory_betOrder = null;
							}
							delete v_lottory_betOrder;
							v_lottory_betOrder = undefined;
							v_lottory_betOrder_used = 0;
						}
						disableLoadingPage();
						return false;
					}
				}
			};
		}
		v_lottory_betOrder.open("POST", tmpURL, true);
		if(v_lottory_betOrder.setRequestHeader){
			v_lottory_betOrder.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		}
		v_lottory_betOrder.send(tmpStr.toString());
		delete tmpURL;
		delete tmpStr;
		tmpURL = undefined;
		tmpStr = undefined;
	}
}

function sortLotteryOrder(a, b){
	var tmp1 = "";
	var tmp2 = "";
	try{
		tmp1 = a.split('-')[2];
		tmp2 = b.split('-')[2];
		while(tmp1.length<10){
			tmp1 = "0" + tmp1;
		}
		while(tmp2.length<10){
			tmp2 = "0" + tmp2;
		}
		tmp1 = a.split('-')[1] + tmp1;
		tmp2 = b.split('-')[1] + tmp2;
		return (tmp1.toString() === tmp2.toString()) ? 0 : (tmp2.toString()).localeCompare(tmp1.toString());
	} catch(err) {
		return 0;
	} finally {
		delete tmp1;
		tmp1 = undefined;
		delete tmp2;
		tmp2 = undefined;
	}
}

function openCancleChk(type){
	var chkCount = 0;
	var checkBox = '';
	
	if(type == MID_BET_ORDER){
		chkCount = 0;
		checkBox = document.getElementsByName('radio');
		
		for(var mid = 0 ; mid < checkBox.length ; mid++){
			if(!isNull(getEle('radio'+mid))){
				if(getEle('radio'+mid).checked == true){
					chkCount++;
				}	
			}
		}
		if(chkCount >= 2){
			getEle('MyBetDiv').getElementsByTagName("button")[0].disabled = false;
		}else{
			getEle('MyBetDiv').getElementsByTagName("button")[0].disabled = true;
		}
	}else if(type == MAIN_BET_ORDER){
		checkBox = document.getElementsByName('mainRadio');
		chkCount = 0;
		
		for(var main = nowFirstCount ; main < nowLastCount ; main++){
			if(!isNull(getEle('mainRadio'+main))){
				if(getEle('mainRadio'+main).checked == true){
					chkCount++;
				}
			}
		}
		if(chkCount >= 2){
			getEle('MyAddDiv').getElementsByTagName("button")[0].disabled = false;
		}else{
			getEle('MyAddDiv').getElementsByTagName("button")[0].disabled = true;
		}
	}
}