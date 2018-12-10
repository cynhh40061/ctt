var LOTTERY_BET_ORDER_XHR = null;

const MID_BET_ORDER = 1;
const MAIN_BET_ORDER = 2;

const LOTTERY_BET_ORDER_QUERY_JS = true;

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

var mainOrderStatus = [ "", "進行中", "已終止(系統)", "已終止(手動)", "已完成", "下注失敗" ];// 追號(進行中/手動終止/跳開終止(錄號撤單=系統撤單=error)/中獎終止/已完成)
var midOrderStatus = [ "", "未開獎", "未中獎", "已撤銷", "已中獎", "開獎中" ];// 一搬(未開獎/已撤銷(手動or錄號撤單=系統撤單=error)/開獎中/系統撤銷(錄號撤單=系統撤單=error)/未中獎or中獎$)
var mainOrdertype = [ "中獎不停", "中獎即停", "跳開停止", "中獎即停" ];
var handiCapName = [ "", "A盤", "B盤", "C盤", "D盤", "E盤" ];

function LotteryBetOrderQuery() {
	LotteryBetOrderQuery_into();
	getBetLocalListAjax();
}

function LotteryBetOrderQuery_into() {
	var str = '';
	if (document.getElementsByName("LotteryBetOrderInfo").length != 1) {
		str += '\n<input type="hidden" name="LotteryLocalList" value="">';
		str += '\n<input type="hidden" name="lotteryBetOrderInfo" value="">';

		str += '\n<input type="hidden" name="midCount" value="25">';
		str += '\n<input type="hidden" name="midPage" value="1">';
		str += '\n<input type="hidden" name="midLastPage" value="1">';

		str += '\n<input type="hidden" name="mainCount" value="25">';
		str += '\n<input type="hidden" name="mainPage" value="1">';
		str += '\n<input type="hidden" name="mainLastPage" value="1">';

		str += '\n<input type="hidden" name="nowDBTime" value="">';
	}
	if (str != '') {
		document.getElementById("extraHidden").innerHTML = str;
	}
	delete str;
	str = undefined;
	if (document.getElementById("searchArea") != null) {
		document.getElementById("searchArea").innerHTML = "";
	}
	if (document.getElementById("mainContain") != null) {
		document.getElementById("mainContain").innerHTML = "";
	}
	if (document.getElementById("myModal") != null) {
		document.getElementById("myModal").innerHTML = "";
	}
	if (document.getElementById("myModalV2") != null) {
		document.getElementById("myModalV2").innerHTML = "";
	}
}
function getBetLocalListAjax() {
	var str = '';
	LOTTERY_BET_ORDER_XHR = checkXHR(LOTTERY_BET_ORDER_XHR);
	if (typeof LOTTERY_BET_ORDER_XHR != "undefined" && LOTTERY_BET_ORDER_XHR != null) {
		str = "tokenId=" + encodeURIComponent(getEle("tokenId").value);
		LOTTERY_BET_ORDER_XHR.timeout = 10000;
		LOTTERY_BET_ORDER_XHR.ontimeout = function() {
			disableLoadingPage();
			LOTTERY_BET_ORDER_XHR.abort();
		}
		LOTTERY_BET_ORDER_XHR.onerror = function() {
			disableLoadingPage();
			LOTTERY_BET_ORDER_XHR.abort();
		}
		LOTTERY_BET_ORDER_XHR.onreadystatechange = handleGetBetLocalListAjax;
		LOTTERY_BET_ORDER_XHR.open("POST", "./LotteryBetOrderQuery!getLotteryLocalList.php?date=" + getNewTime(), true);
		LOTTERY_BET_ORDER_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LOTTERY_BET_ORDER_XHR.send(str);
		enableLoadingPage();
	}
}
function handleGetBetLocalListAjax() {
	if (LOTTERY_BET_ORDER_XHR.readyState == 4) {
		if (LOTTERY_BET_ORDER_XHR.status == 200) {
			try {
				if (isJSON(LOTTERY_BET_ORDER_XHR.responseText)) {
					getEle('LotteryLocalList').value = LOTTERY_BET_ORDER_XHR.responseText;
					showBetQuery(MID_BET_ORDER);
					showBetTable(MID_BET_ORDER);
					onclickTopBtn(MID_BET_ORDER);
				}
			} catch (error) {
				console_Log("handleGetBetLocalListAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LOTTERY_BET_ORDER_XHR.abort();
			}
		} else {
			disableLoadingPage();
			LOTTERY_BET_ORDER_XHR.abort();
		}
	}
}
function showBetQuery() {
	var searchAreaStr = [];

	searchAreaStr.push('<div class="contentDiv2-14">');
	searchAreaStr.push('	<button onclick="onclickTopBtn(' + MID_BET_ORDER + ');" id="midBetBtn">一般注單</button> \n');
	searchAreaStr.push('	<button onclick="onclickTopBtn(' + MAIN_BET_ORDER + ');" id="mainBetBtn">追號注單</button>');
	searchAreaStr.push('	<div class="normal" id="midBetSearchDiv" style="display: none;">');
	searchAreaStr.push('		<ul>');
	searchAreaStr.push('			<li>單號：<input type="text" name="midOrderId" onkeyup="checkImputOrderId();" maxlength="15"></li>');
	searchAreaStr.push('			<li>帳號：<input type="text" name="midAccName" onkeyup="checkImputMemberAccName();" maxlength="20"></li>');
	searchAreaStr.push('			<li>彩種：<select name="midlotteryLocal" onchange="checkMidlotteryLocal(this.value);"></select></li>');
	searchAreaStr
			.push('			<li>期號：<input type="text" name="midPeriodNum" onkeyup="checkImputPeriodNum();" maxlength="15" style="background: rgb(238, 238, 238);" readonly></li>');
	searchAreaStr
			.push('			<li class="time">時間：<input type="text" id="midStartTime" onclick="newCalendar(this,this.id,1);" readonly>到<input type="text" id="midEndTime" onclick="newCalendar(this,this.id,2);" readonly></li>');
	searchAreaStr.push('			<li><button onclick="conformBetOrderQuery(' + MID_BET_ORDER + ');">查詢</button></li>');
	searchAreaStr.push('		</ul>');
	searchAreaStr.push('	</div>');
	searchAreaStr.push('	<div class="unnormal" id="mainBetSearchDiv" style="display: none;">');
	searchAreaStr.push('		<ul>');
	searchAreaStr.push('			<li>追號單號：<input type="text" name="mainOrderId" onkeyup="checkImputOrderId();" maxlength="15"></li>');
	searchAreaStr.push('			<li>帳號：<input type="text" name="mainAccName" onkeyup="checkImputMemberAccName();" maxlength="20"></li>');
	searchAreaStr.push('			<li>彩種：<select name="mainlotteryLocal"></select></li>');
	searchAreaStr
			.push('			<li>狀態：<select name="lotteryStatus"><option value="0">請選擇</option><option value="1">進行中</option><option value="2">已終止(系統)</option><option value="3">已終止(手動)</option><option value="4">已完成</option></select></li>');
	searchAreaStr
			.push('			<li class="time">時間：<input type="text" id="mainStartTime" onclick="newCalendar(this,this.id,1);" readonly>到<input type="text" id="mainEndTime" onclick="newCalendar(this,this.id,2);" readonly></li>');
	searchAreaStr.push('			<li><button onclick="conformBetOrderQuery(' + MAIN_BET_ORDER + ');">查詢</button></li>');
	searchAreaStr.push('		</ul>');
	searchAreaStr.push('	</div>');
	searchAreaStr.push('</div>');

	searchAreaStr.join('');
	getEle('searchArea').innerHTML = searchAreaStr.join('');
}
function showBetTable() {
	var mainContainStr = [];

	mainContainStr.push('<div style="display: none;" id="midTableDiv">');
	mainContainStr.push('	<table id="midBetTable" class="width-percent-910 tr-hover">');
	mainContainStr.push('		<tbody>');
	mainContainStr.push('			<tr><th>單號</th><th>帳號</th><th>彩種</th><th>期號</th><th>玩法</th><th>盤口</th><th>投注號碼</th><th>倍數</th><th>注數</th><th>模式</th><th>投注金額</th><th>投注時間</th><th>中獎金額</th><th>返點</th><th>操作</th></tr>');
	mainContainStr.push('		</tbody>');
	mainContainStr.push('	</table>');
	mainContainStr.push('<p class="media-control text-center">');
	mainContainStr.push('	<span>一頁');
	mainContainStr.push('		<select id="setMidCount" onchange="setMidCountChange(this.value);">');
	mainContainStr.push('			<option value="25">25筆</option>');
	mainContainStr.push('			<option value="50">50筆</option>');
	mainContainStr.push('			<option value="100">100筆</option>');
	mainContainStr.push('		</select>');
	mainContainStr.push('	</span>');
	mainContainStr.push('	<a href="javascript:void(0);" onclick="firstMidPage();" class="backward"></a>');
	mainContainStr.push('	<a href="javascript:void(0);" onclick="previousMidPage();" class="backward-fast"></a>');
	mainContainStr.push('	<span>總頁數：<i id="midNowPage">1</i></span>');
	mainContainStr.push('	<span>/</span>');
	mainContainStr.push('		<i id="midTotlePage">1</i>頁');
	mainContainStr.push('	</span>');
	mainContainStr.push('	<a href="javascript:void(0);" onclick="nextMidPage();" class="forward"></a>');
	mainContainStr.push('	<a href="javascript:void(0);" onclick="lastMidPage();" class="forward-fast"></a>');
	mainContainStr.push('</p>');
	mainContainStr.push('</div>');

	mainContainStr.push('<div style="display: none;" id="mainTableDiv">');
	mainContainStr.push('	<table id="mainBetTable" class="width-percent-910 tr-hover">');
	mainContainStr.push('		<tbody>');
	mainContainStr
			.push('			<tr><th>追號單號</th><th>帳號</th><th>彩種</th><th>追號期數</th><th>剩餘期號</th><th>玩法</th><th>盤口</th><th>中獎期數</th><th>模式</th><th>投注號碼</th><th>追號總金額</th><th>累計中獎金額</th><th>投注時間</th><th>狀態</th><th>追號類型</th><th>返點</th><th>操作</th></tr>');
	mainContainStr.push('		</tbody>');
	mainContainStr.push('	</table>');
	mainContainStr.push('<p class="media-control text-center">');
	mainContainStr.push('	<span>一頁');
	mainContainStr.push('		<select id="setMainCount" onchange="setMainCountChange(this.value);">');
	mainContainStr.push('			<option value="25">25筆</option>');
	mainContainStr.push('			<option value="50">50筆</option>');
	mainContainStr.push('			<option value="100">100筆</option>');
	mainContainStr.push('		</select>');
	mainContainStr.push('	</span>');
	mainContainStr.push('	<a href="javascript:void(0);" onclick="firstMainPage();" class="backward"></a>');
	mainContainStr.push('	<a href="javascript:void(0);" onclick="previousMainPage();" class="backward-fast"></a>');
	mainContainStr.push('	<span>總頁數：<i id="mainNowPage">1</i></span>');
	mainContainStr.push('	<span>/</span>');
	mainContainStr.push('		<i id="mainTotlePage">1</i>頁');
	mainContainStr.push('	</span>');
	mainContainStr.push('	<a href="javascript:void(0);" onclick="nextMainPage();" class="forward"></a>');
	mainContainStr.push('	<a href="javascript:void(0);" onclick="lastMainPage();" class="forward-fast"></a>');
	mainContainStr.push('</p>');
	mainContainStr.push('<div>');

	mainContainStr.join('');
	getEle('mainContain').innerHTML = mainContainStr.join('');
}
function showMidTable() {
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	var mainTableInfo = '';
	var mainObjKey = [];
	var midTableInfo = '';
	var midObjKey = [];
	var midTableStr = [];

	if (!isNull(betObjWhenGetNewBets.BetOrderInfo)) {
		mainTableInfo = betObjWhenGetNewBets.BetOrderInfo;
		mainObjKey = Object.keys(mainTableInfo).sort();
	}

	midTableStr
			.push('<tr><th>單號</th><th>帳號</th><th>彩種</th><th>期號</th><th>玩法</th><th>盤口</th><th>投注號碼</th><th>倍數</th><th>注數</th><th>模式</th><th>投注金額</th><th>投注時間</th><th>中獎金額</th><th>返點</th><th>操作</th></tr>');
	for (var i = 0; i < mainObjKey.length; i++) {
		if (!isNull(mainTableInfo[mainObjKey[i]])) {
			midTableInfo = mainTableInfo[mainObjKey[i]];
			midObjKey = Object.keys(midTableInfo).sort();
			for (var j = 0; j < midObjKey.length; j++) {
				if (isNumber(parseInt(midObjKey[j]))) {
					midTableStr.push('<tr>');
					midTableStr.push('<td>' + checkNull(mainObjKey[i].slice(5)) + '-' + checkNull(midTableInfo[midObjKey[j]].mid_order_id) + '</td>');
					midTableStr.push('<td>' + checkNull(mainTableInfo[mainObjKey[i]].acc_name) + '</td>');
					midTableStr.push('<td>' + checkNull(mainTableInfo[mainObjKey[i]].title) + '</td>');
					midTableStr.push('<td>' + checkNull(midTableInfo[midObjKey[j]].period_num) + '</td>');
					midTableStr.push('<td>' + checkNull(mainTableInfo[mainObjKey[i]].lottery_title) + '</td>');
					midTableStr.push('<td>' + checkNull(handiCapName[mainTableInfo[mainObjKey[i]].handi_cap]) + '</td>');
					midTableStr.push('<td>' + cutBetData(mainTableInfo[mainObjKey[i]].bet_data) + '</td>');
					midTableStr.push('<td>' + checkNull(midTableInfo[midObjKey[j]].no_of_bet_times) + '</td>');
					midTableStr.push('<td>' + checkNull(midTableInfo[midObjKey[j]].no_of_bet) + '</td>');
					midTableStr.push('<td>' + moneyUnitType(mainTableInfo[mainObjKey[i]].money_unit) + '</td>');
					midTableStr.push('<td class="red">-' + checkNull(Math.floor(midTableInfo[midObjKey[j]].mid_amount*100)/100) + '</td>');
					midTableStr.push('<td>' + checkNull(mainTableInfo[mainObjKey[i]].order_time) + '</td>');
					if (!isNull(midTableInfo[midObjKey[j]].mid_order_status) && midTableInfo[midObjKey[j]].mid_order_status == 4) {
						midTableStr.push('<td>' + checkNull(Math.floor(midTableInfo[midObjKey[j]].mid_bonus*100)/100) + '</td>');
					} else {
						midTableStr.push('<td>' + midOrderStatus[checkNull(midTableInfo[midObjKey[j]].check_mid_com_time)] + '</td>');
					}
					midTableStr.push('<td>' + checkNull(Math.floor(midTableInfo[midObjKey[j]].mid_fan_den*100)/100) + '</td>');
					if (checkLastMid(mainObjKey[i])) {
						midTableStr.push('<td>');
						midTableStr.push('<a href="javascript:void(0);" onclick="showMidDetailDiv(\''
								+ mainObjKey[i] + '\',\'' 
								+ midObjKey[j] + '\',\'' 
								+ MID_BET_ORDER + '\')">詳細</a>／');
						midTableStr.push('<a href="javascript:void(0);" onclick="conformCancleMidOrder(\''
								+ mainTableInfo[mainObjKey[i]].local_id + '\',\''
								+ mainObjKey[i].split('-')[2] + '\',\''
								+ midTableInfo[midObjKey[j]].period_num + '\',\''
								+ mainTableInfo[mainObjKey[i]].member_acc_id + '\',\''
								+ midTableInfo[midObjKey[j]].stop_betting_time + '\');">撤單</a>');
						midTableStr.push('</td>');
					} else {
						midTableStr.push('<td><a href="javascript:void(0);" onclick="showMidDetailDiv(\'' 
								+ mainObjKey[i] + '\',\'' 
								+ midObjKey[j] + '\',\'' 
								+ MID_BET_ORDER + '\')">詳細</a></td>');
					}
					midTableStr.push('<tr>');
				}
			}
		}
	}

	getEle('midBetTable').innerHTML = midTableStr.join('');
}

function showMainTable() {
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	var mainTableInfo = '';
	var mainObjKey = [];
	var midTableInfo = '';
	var mainTableStr = [];

	if (!isNull(betObjWhenGetNewBets.BetOrderInfo)) {
		mainTableInfo = betObjWhenGetNewBets.BetOrderInfo;
		mainObjKey = Object.keys(mainTableInfo).sort();
	}

	mainTableStr
			.push('<tr><th>追號單號</th><th>帳號</th><th>彩種</th><th>追號期數</th><th>剩餘期號</th><th>玩法</th><th>盤口</th><th>中獎期數</th><th>模式</th><th>投注號碼</th><th>追號總金額</th><th>累計中獎金額</th><th>投注時間</th><th>狀態</th><th>追號類型</th><th>返點</th><th>操作</th></tr>');
	for (var i = 0; i < mainObjKey.length; i++) {
		if (!isNull(mainTableInfo[mainObjKey[i]])) {
			mainTableStr.push('<tr>');
			mainTableStr.push('<td>' + checkNull(mainObjKey[i].slice(5)) + '</td>');// 追號單號
			mainTableStr.push('<td>' + checkNull(mainTableInfo[mainObjKey[i]].acc_name) + '</td>');// 帳號
			mainTableStr.push('<td>' + checkNull(mainTableInfo[mainObjKey[i]].title) + '</td>');// 彩種
			mainTableStr.push('<td>' + checkNull(mainTableInfo[mainObjKey[i]].no_of_period) + '</td>');// 追號期數
			mainTableStr.push('<td>' + checkMidComplete(mainObjKey[i]) + '</td>');// 剩餘期號
			mainTableStr.push('<td>' + checkNull(mainTableInfo[mainObjKey[i]].lottery_title) + '</td>');// 玩法
			mainTableStr.push('<td>' + checkNull(handiCapName[mainTableInfo[mainObjKey[i]].handi_cap]) + '</td>');// 盤口
			mainTableStr.push('<td>' + checkNull(mainTableInfo[mainObjKey[i]].no_of_winning_period) + '</td>');// 中獎期數
			mainTableStr.push('<td>' + moneyUnitType(mainTableInfo[mainObjKey[i]].money_unit) + '</td>');// 模式
			mainTableStr.push('<td>' + cutBetData(mainTableInfo[mainObjKey[i]].bet_data) + '</td>');// 投注號碼
			mainTableStr.push('<td class="red">-' + checkNull(Math.floor(mainTableInfo[mainObjKey[i]].main_amount*100)/100) + '</td>');// 追號總金額
			mainTableStr.push('<td>' + Math.floor(checkNull(mainTableInfo[mainObjKey[i]].main_bonus)*100)/100 + '</td>');// 累計中獎金額
			mainTableStr.push('<td>' + checkNull(mainTableInfo[mainObjKey[i]].order_time) + '</td>');// 投注時間
			mainTableStr.push('<td>' + checkNull(mainOrderStatus[mainTableInfo[mainObjKey[i]].main_order_status]) + '</td>');// 狀態
			mainTableStr.push('<td>' + checkNull(mainOrdertype[mainTableInfo[mainObjKey[i]].main_order_type]) + '</td>');// 追號類型
			mainTableStr.push('<td>' + checkNull(Math.floor(mainTableInfo[mainObjKey[i]].main_fan_den)) + '</td>');// 返點
			if (checkLastMid(mainObjKey[i])) {
				mainTableStr.push('<td>');
				mainTableStr.push('	<a href="javascript:void(0);" onclick="showMidTableDiv(\'' + mainObjKey[i] + '\')">詳細</a>／');
				mainTableStr.push('	<a href="javascript:void(0);" onclick="conformCancleMainOrder(\''
						+ mainTableInfo[mainObjKey[i]].local_id + '\',\'' 
						+ mainObjKey[i].split('-')[2] + '\',\'' 
						+ mainTableInfo[mainObjKey[i]].start_period_num + '\',\''
						+ mainTableInfo[mainObjKey[i]].member_acc_id + '\',\'' 
						+ mainObjKey[i] + '\');">撤單</a>');
				mainTableStr.push('</td>');
			} else {
				mainTableStr.push('<td><a href="javascript:void(0);" onclick="showMidTableDiv(\'' + mainObjKey[i] + '\')">詳細</a></td>');
			}
			mainTableStr.push('<tr>');
		}
	}

	getEle('mainBetTable').innerHTML = mainTableStr.join('');
}
function showMidDetailDiv(main, mid, type) {
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	var mainDetailInfo = '';
	var midDetailInfo = '';
	var totalAmount = '';
	var midDetailStr = [];

	if (!isNull(betObjWhenGetNewBets.BetOrderInfo[main])) {
		mainDetailInfo = betObjWhenGetNewBets.BetOrderInfo[main];
	}
	if (!isNull(mainDetailInfo[mid])) {
		midDetailInfo = mainDetailInfo[mid];
	}
	if (!isNull(Math.floor(midDetailInfo.mid_amount*100)/100) && !isNull(Math.floor(midDetailInfo.mid_bonus*100)/100)
			&& !isNull(Math.floor(midDetailInfo.mid_fan_den*100)/100)) {
		totalAmount = Math.floor(midDetailInfo.mid_bonus * 100 - midDetailInfo.mid_amount * 100 + midDetailInfo.mid_fan_den * 100) / 100;
	}

	midDetailStr.push('<div class="modal-content modal-central detail">');
	midDetailStr.push('	<span class="close" onclick="onClickCloseModalV2();">×</span>');
	midDetailStr.push('	<p>注單詳細內容</p>');
	midDetailStr.push('	<ul><li><span>號碼：</span><div>' + checkNull(mainDetailInfo.bet_data) + '</div></li></ul>');
	midDetailStr.push('	<table>');
	midDetailStr.push('		<tbody>');
	midDetailStr.push('			<tr><th>單號：</th><td>' + checkNull(main.slice(5)) + '-' + checkNull(midDetailInfo.mid_order_id) + '</td></tr>');
	midDetailStr.push('			<tr><th>帳號：</th><td>' + checkNull(mainDetailInfo.acc_name) + '</td></tr>');
	midDetailStr.push('			<tr><th>倍數：</th><td>' + checkNull(midDetailInfo.no_of_bet_times) + '</td></tr>');
	midDetailStr.push('			<tr><th>注數：</th><td>' + checkNull(midDetailInfo.no_of_bet) + '</td></tr>');
	midDetailStr.push('			<tr><th>模式：</th><td>' + moneyUnitType(mainDetailInfo.money_unit) + '</td></tr>');
	midDetailStr.push('			<tr><th>單注最高獎金：</th><td>' + checkNull(Math.floor(midDetailInfo.max_bonus*100)/100) + '</td></tr>');
	if (type == MID_BET_ORDER) {
		midDetailStr.push('			<tr><th>撤單時間：</th><td>' + checkNull(midDetailInfo.mid_cancle_time) + '</td></tr>');
	} else {
		midDetailStr.push('			<tr><th>撤單時間：</th><td>' + checkNull(mainDetailInfo.main_cancle_time) + '</td></tr>');
	}
	midDetailStr.push('			<tr><th>開獎結果：</th><td>' + checkNull(midDetailInfo.kj_data) + '</td></tr>');
	midDetailStr.push('			<tr><th>投注比例：</th><td>' + checkNull(parseFloat(mainDetailInfo.bet_ratio*100))+ '%</td></tr>');
	midDetailStr.push('			<tr><th>期號：</th><td>' + checkNull(midDetailInfo.period_num) + '</td></tr>');
	midDetailStr.push('			<tr><th>彩種：</th><td>' + checkNull(mainDetailInfo.title) + '</td></tr>');
	midDetailStr.push('			<tr><th>玩法：</th><td>' + checkNull(mainDetailInfo.lottery_title) + '</td></tr>');
	midDetailStr.push('			<tr><th>盤口：</th><td>' + checkNull(handiCapName[mainDetailInfo.handi_cap]) + '</td></tr>');
	midDetailStr.push('			<tr><th>投注時間：</th><td>' + checkNull(mainDetailInfo.order_time) + '</td></tr>');
	midDetailStr.push('			<tr><th>開獎時間：</th><td>' + checkNull(midDetailInfo.actual_kj_time) + '</td></tr>');
	midDetailStr.push('			<tr><th>派彩時間：</th><td>' + checkNull(midDetailInfo.complete_time) + '</td></tr>');
	midDetailStr.push('			<tr><th>投注金額：</th><td class="red">-' + checkNull(Math.floor(midDetailInfo.mid_amount*100)/100) + '</td></tr>');
	if (!isNull(midDetailInfo.mid_order_status) && midDetailInfo.mid_order_status == 4) {
		midDetailStr.push('<tr><th>中獎金額：</th><td>' + checkNull(Math.floor(midDetailInfo.mid_bonus*100)/100) + '</td></tr>');
	} else {
		midDetailStr.push('<tr><th>中獎金額：</th><td>' + midOrderStatus[checkNull(midDetailInfo.check_mid_com_time)] + '</td></tr>');
	}
	midDetailStr.push('			<tr><th>返點：</th><td>' + checkNull(Math.floor(midDetailInfo.mid_fan_den*100)/100)+ '</td></tr>');
	if (totalAmount > 0) {
		midDetailStr.push('<tr><th>盈虧：</th><td>' + totalAmount + '</td></tr>');
	} else {
		midDetailStr.push('<tr><th>盈虧：</th><td class="red">' + totalAmount + '</td></tr>');
	}
	midDetailStr.push('		</tbody>');
	midDetailStr.push('	</table>');
	midDetailStr.push('	<div class="btn-area">');
	midDetailStr.push('		<button onclick="onClickCloseModalV2();">關閉</button>');
	if (type == MID_BET_ORDER) {
		if (checkLastMid(main)) {
			midDetailStr.push('<button onclick="conformCancleMidOrder(\'' 
					+ mainDetailInfo.local_id + '\',\'' 
					+ main.split('-')[2] + '\',\'' 
					+ midDetailInfo.period_num + '\',\'' 
					+ mainDetailInfo.member_acc_id + '\',\'' 
					+ main + '\');">撤單</button>');
		} else {
			midDetailStr.push('<button class="btn-gray">撤單</button>');
		}
	}
	midDetailStr.push('	</div>');
	midDetailStr.push('</div>');

	onClickOpenModalV2(midDetailStr.join(''));
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

function showMidTableDiv(main) {
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	var mainTableInfo = '';
	var mainObjKey = [];
	var midTableInfo = '';
	var midTableStr = [];

	if (!isNull(betObjWhenGetNewBets.BetOrderInfo)) {
		mainTableInfo = betObjWhenGetNewBets.BetOrderInfo[main];
		mainObjKey = Object.keys(mainTableInfo).sort();
	}

	midTableStr.push('<div class="modal-content modal-central detail2">');
	midTableStr.push('	<span class="close" onclick="onClickCloseModal();">×</span>');
	midTableStr.push('	<p>追號注單</p>');
	midTableStr.push('	<table>');
	midTableStr.push('		<tbody>');
	midTableStr
			.push('			<tr><th>單號</th><th>帳號</th><th>彩種</th><th>期號</th><th>玩法</th><th>盤口</th><th>投注號碼</th><th>倍數</th><th>注數</th><th>模式</th><th>投注金額</th><th>投注時間</th><th>中獎金額</th><th>返點</th><th>操作</th></tr>');

	for (var j = 0; j < mainObjKey.length; j++) {
		if (isNumber(parseInt(mainObjKey[j]))) {
			if (!isNull(mainTableInfo[mainObjKey[j]])) {
				midTableInfo = mainTableInfo[mainObjKey[j]];
			}
			midTableStr.push('<tr>');
			midTableStr.push('<td>' + checkNull(main.slice(5)) + '-' + checkNull(midTableInfo.mid_order_id) + '</td>');
			midTableStr.push('<td>' + checkNull(mainTableInfo.acc_name) + '</td>');
			midTableStr.push('<td>' + checkNull(mainTableInfo.title) + '</td>');
			midTableStr.push('<td>' + checkNull(midTableInfo.period_num) + '</td>');
			midTableStr.push('<td>' + checkNull(mainTableInfo.lottery_title) + '</td>');
			midTableStr.push('<td>' + checkNull(handiCapName[mainTableInfo.handi_cap]) + '</td>');
			midTableStr.push('<td>' + cutBetData(mainTableInfo.bet_data) + '</td>');
			midTableStr.push('<td>' + checkNull(midTableInfo.no_of_bet_times) + '</td>');
			midTableStr.push('<td>' + checkNull(midTableInfo.no_of_bet) + '</td>');
			midTableStr.push('<td>' + moneyUnitType(mainTableInfo.money_unit) + '</td>');
			midTableStr.push('<td class="red">-' + checkNull(Math.floor(midTableInfo.mid_amount*100)/100) + '</td>');
			midTableStr.push('<td>' + checkNull(mainTableInfo.order_time) + '</td>');
			if (!isNull(midTableInfo.mid_order_status) && midTableInfo.mid_order_status == 4) {
				midTableStr.push('<td>' + checkNull(Math.floor(midTableInfo.mid_bonus*100)/100) + '</td>');
			} else {
				midTableStr.push('<td>' + midOrderStatus[checkNull(midTableInfo.check_mid_com_time)] + '</td>');
			}
			midTableStr.push('<td>' + checkNull(Math.floor(midTableInfo.mid_fan_den*100)/100) + '</td>');
			midTableStr.push('<td><a href="javascript:void(0);" onclick="showMidDetailDiv(\'' + main + '\',\'' + mainObjKey[j]
					+ '\',' + MAIN_BET_ORDER + ')">詳細</a></td>');
			midTableStr.push('<tr>');
		}
	}
	midTableStr.push('		</tbody>');
	midTableStr.push('	</table>');
	midTableStr.push('</div>');

	onClickOpenModal(midTableStr.join(''));
}
function onclickTopBtn(type) {
	clearAreaAndBtn();
	var LotteryLocalList = JSON.parse(getEle('LotteryLocalList').value);
	var startTime = new Date().getFromFormat("yyyy/MM/dd") + ' 00:00:00';
	var endTime = new Date().getFromFormat("yyyy/MM/dd") + ' 23:59:59';

	if (!isNull(LotteryLocalList.LotteryLocalList)) {
		LotteryLocalList = LotteryLocalList.LotteryLocalList;
	}

	if (type == MID_BET_ORDER) {
		getEle('midBetBtn').classList.add('active');
		getEle('midBetSearchDiv').style.display = 'block';
		getEle('midTableDiv').style.display = 'block';
		addOptionNoDup('midlotteryLocal', '所有彩種', 0);
		for (var i = 0; i < LotteryLocalList.length; i++) {
			addOptionNoDup('midlotteryLocal', LotteryLocalList[i].title, LotteryLocalList[i].id);
		}
		getEle('midStartTime').value = startTime;
		getEle('midEndTime').value = endTime;
	} else if (type == MAIN_BET_ORDER) {
		getEle('mainBetBtn').classList.add('active');
		getEle('mainBetSearchDiv').style.display = 'block';
		getEle('mainTableDiv').style.display = 'block';
		addOptionNoDup('mainlotteryLocal', '所有彩種', 0);
		for (var i = 0; i < LotteryLocalList.length; i++) {
			addOptionNoDup('mainlotteryLocal', LotteryLocalList[i].title, LotteryLocalList[i].id);
		}
		getEle('mainStartTime').value = startTime;
		getEle('mainEndTime').value = endTime;
	}
}
function clearAreaAndBtn() {
	getEle('midBetSearchDiv').style.display = 'none';
	getEle('mainBetSearchDiv').style.display = 'none';
	getEle('midBetBtn').classList.remove('active');

	getEle('midTableDiv').style.display = 'none';
	getEle('mainTableDiv').style.display = 'none';
	getEle('mainBetBtn').classList.remove('active');

	getEle('midOrderId').value = '';
	getEle('midAccName').value = '';
	removeAllOption('midlotteryLocal');
	getEle('midPeriodNum').value = '';
	getEle('midStartTime').value = '';
	getEle('midEndTime').value = '';

	getEle('mainOrderId').value = '';
	getEle('mainAccName').value = '';
	removeAllOption('mainlotteryLocal');
	getEle('mainStartTime').value = '';
	getEle('mainEndTime').value = '';

	getEle("midNowPage").innerHTML = 1;
	getEle("midTotlePage").innerHTML = 1;
	getEle("mainNowPage").innerHTML = 1;
	getEle("mainTotlePage").innerHTML = 1;

	getEle('midBetTable').innerHTML = '<tr><th>單號</th><th>帳號</th><th>彩種</th><th>期號</th><th>玩法</th><th>盤口</th><th>投注號碼</th><th>倍數</th><th>注數</th><th>模式</th><th>投注金額</th><th>投注時間</th><th>中獎金額</th><th>返點</th><th>操作</th></tr>';
	getEle('mainBetTable').innerHTML = '<tr><th>追號單號</th><th>帳號</th><th>彩種</th><th>追號期數</th><th>剩餘期號</th><th>玩法</th><th>盤口</th><th>中獎期數</th><th>模式</th><th>投注號碼</th><th>追號總金額</th><th>累計中獎金額</th><th>投注時間</th><th>狀態</th><th>追號類型</th><th>返點</th><th>操作</th></tr>';
}
function checkImputMemberAccName() {
	getEle('midAccName').value = checkAccount(getEle('midAccName').value);
	getEle('mainAccName').value = checkAccount(getEle('mainAccName').value);
}
function checkImputOrderId() {
	getEle('midOrderId').value = checkBankAccInputNumberVal(getEle('midOrderId').value);
	getEle('mainOrderId').value = checkBankAccInputNumberVal(getEle('mainOrderId').value);
}
function checkImputPeriodNum() {
	getEle('midPeriodNum').value = checkInputNumberVal(getEle('midPeriodNum').value);
}
function conformBetOrderQuery(type, pageInfo) {
	var midOrderId = getEle('midOrderId').value;
	var midAccName = getEle('midAccName').value;
	var midlotteryLocal = getEle('midlotteryLocal').value;
	var midPeriodNum = getEle('midPeriodNum').value;
	var midStartTime = getEle('midStartTime').value;
	var midEndTime = getEle('midEndTime').value;

	var mainOrderId = getEle('mainOrderId').value;
	var mainAccName = getEle('mainAccName').value;
	var mainlotteryLocal = getEle('mainlotteryLocal').value;
	var lotteryStatus = getEle('lotteryStatus').value;
	var mainStartTime = getEle('mainStartTime').value;
	var mainEndTime = getEle('mainEndTime').value;
	var str = '';
	var count = 25;

	if (isNull(pageInfo)) {
		pageInfo = '';
	}

	if (type == MID_BET_ORDER) {
		count = getEle('midCount').value;
		str = '&midOrderId=' + midOrderId + '&midAccName=' + midAccName + '&midlotteryLocal=' + midlotteryLocal + '&midPeriodNum=' + midPeriodNum
				+ '&midStartTime=' + midStartTime + '&midEndTime=' + midEndTime + '&type=' + type + "&count=" + count + pageInfo;
	} else if (type == MAIN_BET_ORDER) {
		count = getEle('mainCount').value;
		str = '&mainOrderId=' + mainOrderId + '&mainAccName=' + mainAccName + '&mainlotteryLocal=' + mainlotteryLocal + '&lotteryStatus='
				+ lotteryStatus + '&mainStartTime=' + mainStartTime + '&mainEndTime=' + mainEndTime + '&type=' + type + "&count=" + count + pageInfo;
	}
	if (str != '') {
		lotteryQueryOrdersAjax(str);
	}
}
function lotteryQueryOrdersAjax(str) {
	var tmpStr = '';
	LOTTERY_BET_ORDER_XHR = checkXHR(LOTTERY_BET_ORDER_XHR);
	if (typeof LOTTERY_BET_ORDER_XHR != "undefined" && LOTTERY_BET_ORDER_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + str;
		LOTTERY_BET_ORDER_XHR.timeout = 10000;
		LOTTERY_BET_ORDER_XHR.ontimeout = function() {
			disableLoadingPage();
			LOTTERY_BET_ORDER_XHR.abort();
		}
		LOTTERY_BET_ORDER_XHR.onerror = function() {
			disableLoadingPage();
			LOTTERY_BET_ORDER_XHR.abort();
		}
		LOTTERY_BET_ORDER_XHR.onreadystatechange = handleLotteryQueryOrdersAjax;
		LOTTERY_BET_ORDER_XHR.open("POST", "./LotteryBetOrderQuery!queryOrders.php?date=" + getNewTime(), true);
		LOTTERY_BET_ORDER_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LOTTERY_BET_ORDER_XHR.send(tmpStr);
		enableLoadingPage();
	}
}
function handleLotteryQueryOrdersAjax() {
	if (LOTTERY_BET_ORDER_XHR.readyState == 4) {
		if (LOTTERY_BET_ORDER_XHR.status == 200) {
			try {
				var tmpJ = isJSON2(LOTTERY_BET_ORDER_XHR.responseText);
				if (tmpJ) {
					betObjWhenGetNewBets = tmpJ;
					if (!isNull(betObjWhenGetNewBets.nowDBTime)) {
						getEle('nowDBTime').value = betObjWhenGetNewBets.nowDBTime;
					}
					if (betObjWhenGetNewBets.type == 1) {
						showMidTable();
						if (typeof betObjWhenGetNewBets.midPage != "undefined" && betObjWhenGetNewBets.midPage != null) {
							getEle("midPage").value = betObjWhenGetNewBets.midPage;
							if (getEle("midNowPage") != null && getEle("midNowPage") != undefined) {
								getEle("midNowPage").innerHTML = betObjWhenGetNewBets.midPage;
							}
						}
						if (typeof betObjWhenGetNewBets.midLastPage != "undefined" && betObjWhenGetNewBets.midLastPage != null) {
							getEle("midLastPage").value = betObjWhenGetNewBets.midLastPage;
							if (getEle("midTotlePage") != null && getEle("midTotlePage") != undefined) {
								getEle("midTotlePage").innerHTML = betObjWhenGetNewBets.midLastPage;
							}
						}
					} else if (betObjWhenGetNewBets.type == 2) {
						showMainTable();
						if (typeof betObjWhenGetNewBets.mainPage != "undefined" && betObjWhenGetNewBets.mainPage != null) {
							getEle("mainPage").value = betObjWhenGetNewBets.mainPage;
							if (getEle("mainNowPage") != null && getEle("mainNowPage") != undefined) {
								getEle("mainNowPage").innerHTML = betObjWhenGetNewBets.mainPage;
							}
						}
						if (typeof betObjWhenGetNewBets.mainLastPage != "undefined" && betObjWhenGetNewBets.mainLastPage != null) {
							getEle("mainLastPage").value = betObjWhenGetNewBets.mainLastPage;
							if (getEle("mainTotlePage") != null && getEle("mainTotlePage") != undefined) {
								getEle("mainTotlePage").innerHTML = betObjWhenGetNewBets.mainLastPage;
							}
						}
					}
				}
			} catch (error) {
				console_Log("handleLotteryQueryOrdersAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LOTTERY_BET_ORDER_XHR.abort();
			}
		} else {
			disableLoadingPage();
			LOTTERY_BET_ORDER_XHR.abort();
		}
	}
}
// 以下撤單
function conformCancleMidOrder(localId, mainOrderId, periodNum, memberAccId, main) {
	var allOrder = new Object();
	var mainOrder = new Object();

	if (checkLastCancleTime(main)) {
		onCheckModelPage('注意：資料已過期，將重新刷新!', 'conformBetOrderQuery(' + MID_BET_ORDER + ')');
	} else {
		if (!isNull(localId) && localId != '' && localId != 0 && !isNull(mainOrderId) && mainOrderId != '' && mainOrderId != 0 && !isNull(periodNum)
				&& periodNum != '' && periodNum != 0 && !isNull(memberAccId) && memberAccId != '' && memberAccId != 0) {
			mainOrder.localId = localId;
			mainOrder.mainOrderId = mainOrderId;
			mainOrder.periodNum = periodNum;
			mainOrder.memberAccId = memberAccId;
			allOrder.orders = new Array();
			allOrder.orders.push(mainOrder);
		}
		if (allOrder.orders.length > 0) {
			onCheckModelPage('注意：即將進行撤單，是否確定撤單？', 'cancleMidOrderAjax(' + JSON.stringify(allOrder) + ')');
		}
	}
}
function conformCancleMainOrder(localId, mainOrderId, periodNum, memberAccId, main) {
	var allOrder = new Object();
	var mainOrder = new Object();

	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	var mainInfo = '';
	var midInfo = '';
	var periodNum = 0;

	if (!isNull(betObjWhenGetNewBets.BetOrderInfo[main])) {
		mainInfo = betObjWhenGetNewBets.BetOrderInfo[main];
	}

	for (var m = 0; m < Object.keys(mainInfo).length; m++) {
		if (!isNull(mainInfo[Object.keys(mainInfo).sort()[m]])) {
			if (isNumber(parseInt(Object.keys(mainInfo).sort()[m]))) {
				midInfo = mainInfo[Object.keys(mainInfo).sort()[m]];
				if (!isNull(midInfo.period_num)) {
					periodNum = midInfo.period_num;
				}
			}
		}
	}

	if (checkLastCancleTime(main)) {
		onCheckModelPage('注意：資料已過期，將重新刷新!', 'conformBetOrderQuery(' + MAIN_BET_ORDER + ')');
	} else {
		if (!isNull(localId) && localId != '' && localId != 0 && !isNull(mainOrderId) && mainOrderId != '' && mainOrderId != 0 && !isNull(periodNum)
				&& periodNum != '' && periodNum != 0 && !isNull(memberAccId) && memberAccId != '' && memberAccId != 0) {
			mainOrder.localId = localId;
			mainOrder.mainOrderId = mainOrderId;
			mainOrder.periodNum = periodNum;
			mainOrder.memberAccId = memberAccId;
			allOrder.orders = new Array();
			allOrder.orders.push(mainOrder);
		}
		if (allOrder.orders.length > 0) {
			onCheckModelPage('注意：即將進行撤單，是否確定撤單？', 'cancleMainOrderAjax(' + JSON.stringify(allOrder) + ')');
		}
	}
}
function cancleMidOrderAjax(str) {
	var str = JSON.stringify(str);
	var tmpStr = '';
	LOTTERY_BET_ORDER_XHR = checkXHR(LOTTERY_BET_ORDER_XHR);
	if (typeof LOTTERY_BET_ORDER_XHR != "undefined" && LOTTERY_BET_ORDER_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + '&data=' + str;
		LOTTERY_BET_ORDER_XHR.timeout = 10000;
		LOTTERY_BET_ORDER_XHR.ontimeout = function() {
			disableLoadingPage();
			LOTTERY_BET_ORDER_XHR.abort();
		}
		LOTTERY_BET_ORDER_XHR.onerror = function() {
			disableLoadingPage();
			LOTTERY_BET_ORDER_XHR.abort();
		}
		LOTTERY_BET_ORDER_XHR.onreadystatechange = handleCancleMidOrderAjax;
		LOTTERY_BET_ORDER_XHR.open("POST", "./LotteryBetOrderQuery!cancleMidOrder.php?date=" + getNewTime(), true);
		LOTTERY_BET_ORDER_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LOTTERY_BET_ORDER_XHR.send(tmpStr);
		enableLoadingPage();
	}
}
function handleCancleMidOrderAjax() {
	if (LOTTERY_BET_ORDER_XHR.readyState == 4) {
		if (LOTTERY_BET_ORDER_XHR.status == 200) {
			try {
				if (isJSON(LOTTERY_BET_ORDER_XHR.responseText)) {
					var json = JSON.parse(LOTTERY_BET_ORDER_XHR.responseText);
					if (json.operateStatus == 'success') {
						onCheckModelPage('成功!', '');
					} else if (json.operateStatus == 'fail') {
						onCheckModelPage('失敗', '');
					} else {
						onCheckModelPage('error', '');
					}
				}
			} catch (error) {
				console_Log("handleCancleMidOrderAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LOTTERY_BET_ORDER_XHR.abort();
				conformBetOrderQuery(MID_BET_ORDER);
			}
		} else {
			disableLoadingPage();
			LOTTERY_BET_ORDER_XHR.abort();
		}
	}
}
function cancleMainOrderAjax(str) {
	var str = JSON.stringify(str);
	var tmpStr = '';
	LOTTERY_BET_ORDER_XHR = checkXHR(LOTTERY_BET_ORDER_XHR);
	if (typeof LOTTERY_BET_ORDER_XHR != "undefined" && LOTTERY_BET_ORDER_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + '&data=' + str;
		LOTTERY_BET_ORDER_XHR.timeout = 10000;
		LOTTERY_BET_ORDER_XHR.ontimeout = function() {
			disableLoadingPage();
			LOTTERY_BET_ORDER_XHR.abort();
		}
		LOTTERY_BET_ORDER_XHR.onerror = function() {
			disableLoadingPage();
			LOTTERY_BET_ORDER_XHR.abort();
		}
		LOTTERY_BET_ORDER_XHR.onreadystatechange = handleCancleMainOrderAjax;
		LOTTERY_BET_ORDER_XHR.open("POST", "./LotteryBetOrderQuery!cancleMainOrder.php?date=" + getNewTime(), true);
		LOTTERY_BET_ORDER_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LOTTERY_BET_ORDER_XHR.send(tmpStr);
		enableLoadingPage();
	}
}
function handleCancleMainOrderAjax() {
	if (LOTTERY_BET_ORDER_XHR.readyState == 4) {
		if (LOTTERY_BET_ORDER_XHR.status == 200) {
			try {
				if (isJSON(LOTTERY_BET_ORDER_XHR.responseText)) {
					var json = JSON.parse(LOTTERY_BET_ORDER_XHR.responseText);
					if (json.operateStatus == 'success') {
						onCheckModelPage('成功!', '');
					} else if (json.operateStatus == 'fail') {
						onCheckModelPage('失敗', '');
					} else {
						onCheckModelPage('error', '');
					}
				}
			} catch (error) {
				console_Log("handleCancleMainOrderAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LOTTERY_BET_ORDER_XHR.abort();
				conformBetOrderQuery(MAIN_BET_ORDER);
			}
		} else {
			disableLoadingPage();
			LOTTERY_BET_ORDER_XHR.abort();
		}
	}
}
function checkMidlotteryLocal(val) {
	if (!isNull(val) && val != '' && val > 0) {
		getEle('midPeriodNum').readOnly = false;
		getEle('midPeriodNum').style.background = '';
	} else {
		getEle('midPeriodNum').readOnly = true;
		getEle('midPeriodNum').style.background = '#eee';
		getEle('midPeriodNum').value = '';
	}
}
// 以下mid換頁
function setMidCountChange() {
	getEle("midCount").value = getEle("setMidCount").value
}
function nextMidPage() {
	var nextPageNum = parseInt(getEle("midPage").value) + 1;
	if (nextPageNum <= parseInt(getEle("midLastPage").value)) {
		page = nextPageNum;
	} else {
		page = getEle("midLastPage").value;
	}
	var pageInfo = "&count=" + getEle('midCount').value + "&nextPage=" + page;
	conformBetOrderQuery(MID_BET_ORDER, pageInfo);
}
function lastMidPage() {
	var maxPage = getEle("midLastPage").value;
	var pageInfo = "&count=" + getEle('midCount').value + "&nextPage=" + maxPage;
	conformBetOrderQuery(MID_BET_ORDER, pageInfo);
}
function previousMidPage() {
	var previousPage = parseInt(getEle("midPage").value) - 1;
	var page = "1"
	if (previousPage > 0) {
		page = previousPage;
	}
	var pageInfo = "&count=" + getEle('midCount').value + "&nextPage=" + page;
	conformBetOrderQuery(MID_BET_ORDER, pageInfo);
}
function firstMidPage() {
	var pageInfo = "&count=" + getEle('midCount').value + "&nextPage=1";
	conformBetOrderQuery(MID_BET_ORDER, pageInfo);
}
// 以下main換頁
function setMainCountChange() {
	getEle("mainCount").value = getEle("setMainCount").value
}
function nextMainPage() {
	var nextPageNum = parseInt(getEle("mainPage").value) + 1;
	if (nextPageNum <= parseInt(getEle("mainLastPage").value)) {
		page = nextPageNum;
	} else {
		page = getEle("mainLastPage").value;
	}
	var pageInfo = "&count=" + getEle('mainCount').value + "&nextPage=" + page;
	conformBetOrderQuery(MAIN_BET_ORDER, pageInfo);
}
function lastMainPage() {
	var maxPage = getEle("mainLastPage").value;
	var pageInfo = "&count=" + getEle('mainCount').value + "&nextPage=" + maxPage;
	conformBetOrderQuery(MAIN_BET_ORDER, pageInfo);
}

function previousMainPage() {
	var previousPage = parseInt(getEle("mainPage").value) - 1;
	var page = "1"
	if (previousPage > 0) {
		page = previousPage;
	}
	var pageInfo = "&count=" + getEle('mainCount').value + "&nextPage=" + page;
	conformBetOrderQuery(MAIN_BET_ORDER, pageInfo);
}
function firstMainPage() {
	var pageInfo = "&count=" + getEle('mainCount').value + "&nextPage=1";
	conformBetOrderQuery(MAIN_BET_ORDER, pageInfo);
}
function checkLastCancleTime(main) {
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	var mainInfo = '';
	var midInfo = '';
	var nowTime = new Date(getEle('nowDBTime').value);
	var chkTime = false;

	if (!isNull(betObjWhenGetNewBets.BetOrderInfo[main])) {
		mainInfo = betObjWhenGetNewBets.BetOrderInfo[main];
	}

	for (var m = 0; m < Object.keys(mainInfo).length; m++) {
		if (!isNull(mainInfo[Object.keys(mainInfo).sort()[m]])) {
			if (isNumber(parseInt(Object.keys(mainInfo).sort()[m]))) {
				midInfo = mainInfo[Object.keys(mainInfo).sort()[m]];
				if (!isNull(midInfo.stop_betting_time)) {
					if (nowTime < midInfo.stop_betting_time) {
						chkTime = true;
					} else {
						chkTime = false;
					}
				}
			}
		}
	}
	return chkTime;
}
function checkLastMid(main) {
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	
	var mainInfo = '';
	var mainObjKey = [];
	var midInfo = '';
	var midCheckOrderStatus = 0;
	var midCheckBetTime = 0;

	if (!isNull(betObjWhenGetNewBets.BetOrderInfo[main])) {
		mainInfo = betObjWhenGetNewBets.BetOrderInfo[main];
		mainObjKey = Object.keys(mainInfo).sort();
	}
	if (!isNull(mainInfo.main_order_status)) {
		midCheckOrderStatus = mainInfo.main_order_status;
	}

	for (var m = 0; m < mainObjKey.length; m++) {
		if (!isNull(mainInfo[mainObjKey[m]])) {
			if (isNumber(parseInt(mainObjKey[m]))) {
				midInfo = mainInfo[mainObjKey[m]];
				if (!isNull(midInfo.check_bet_time)) {
					if(midInfo.check_bet_time == 1){
						midCheckBetTime++;
					}
				}
			}
		}
	}
	if (midCheckOrderStatus == 1 && midCheckBetTime >= 1) {
		return true;
	} else {
		return false;
	}
}
function checkMidComplete(main){
	if(betObjWhenGetNewBets == null || !betObjWhenGetNewBets['type'] || !betObjWhenGetNewBets['BetOrderInfo']){
		betObjWhenGetNewBets = {'type':'','BetOrderInfo':''};
	}
	
	var mainInfo = '';
	var mainObjKey = [];
	var midInfo = '';
	var nowTime = new Date(getEle('nowDBTime').value);
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
					if(!isNull(midInfo.stop_betting_time)){
						if(nowTime < parseInt(midInfo.stop_betting_time)*1000){
							midCheckBetTime++;
						}
					}
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
				}
			}
		}
	}
	return midCheckBetTime;
}
