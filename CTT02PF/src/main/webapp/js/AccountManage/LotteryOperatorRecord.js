var LOTTERY_OPERATOR_RECORD_XHR = null;

const LOTTERY_OPERATOR_RECORD_JS = true;

const LOTTERY_OPERATOR_RECORD_TODAY_JS = 1;
const LOTTERY_OPERATOR_RECORD_DAY_JS = 2;
const LOTTERY_OPERATOR_RECORD_WEEK_JS = 3;
const LOTTERY_OPERATOR_RECORD_MOUNTH_JS = 4;

function LotteryOperatorRecord() {
	LotteryOperatorRecord_into();
	LotteryOperatorRecordSearchArea();
	getOperatorRecordLocalListAjax();
}

function LotteryOperatorRecord_into() {
	var str = '';
	if (document.getElementsByName("Info").length != 1) {
		str += '\n<input type="hidden" name="LotteryLocalList" value="">';
		str += '\n<input type="hidden" name="LotteryOperatorRecordInfo" value="">';
		str += '\n<input type="hidden" name="scSearchInfo" value="">';
		str += '\n<input type="hidden" name="bcSearchInfo" value="">';
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

function LotteryOperatorRecordSearchArea() {
	var searchAreaStr = [];

	searchAreaStr.push('<div class="contentDiv2-16">');
	searchAreaStr.push('	<button onclick="onclickOperatorTopBtn(' + LOTTERY_OPERATOR_RECORD_TODAY_JS
			+ ')" id="todayTopBtn" class="active">統計概況</button>');
	searchAreaStr.push('	<button onclick="onclickOperatorTopBtn(' + LOTTERY_OPERATOR_RECORD_DAY_JS + ')" id="dayTopBtn" class="">廠商日盈虧報表</button>');
	searchAreaStr.push('	<button onclick="onclickOperatorTopBtn(' + LOTTERY_OPERATOR_RECORD_WEEK_JS + ')" id="weekTopBtn" class="">廠商週盈虧報表</button>');
	searchAreaStr.push('	<button onclick="onclickOperatorTopBtn(' + LOTTERY_OPERATOR_RECORD_MOUNTH_JS
			+ ')" id="mounthTopBtn" class="">廠商月盈虧報表</button>');
	searchAreaStr.push('	<div style="display: none;" id="todaySearchDiv"><h3>今日所有彩種玩法統計</h3></div>');
	searchAreaStr.push('	<div style="display: none;" id="dateSearchDiv">');
	searchAreaStr.push('		<ul>');
	searchAreaStr.push('			<li>平台商(總監)：<input type="text" id="dateText" onkeyup=""></li>');
	searchAreaStr.push('			<li>彩種：<select id="dateLocalList"></select></li>');
	searchAreaStr.push('			<li>時間：<select id="dateTimeList"></select></li>');
	searchAreaStr.push('			<li><button onclick="onclickOperatorSearchBtn(this.value)" id="operatorSearchBtn">查尋</button></li>');
	searchAreaStr.push('			<li><a href="javascript:void(0);" onclick="">導出EXCEL</a></li>');
	searchAreaStr.push('		</ul>');
	searchAreaStr.push('	</div>');
	searchAreaStr.push('</div>');

	searchAreaStr.join('');
	getEle('searchArea').innerHTML = searchAreaStr.join('');
}
function showTodayMainContain() {
	var localList = JSON.parse(getEle('LotteryLocalList').value);
	var info = JSON.parse(getEle("LotteryOperatorRecordInfo").value);
	var iObj = Object.keys(info);
	var mainContainStr = [];
	var totalAmount = 0;
	var typeId = 0;

	mainContainStr.push('<div id="todayMain" class="contentDiv2-16 contentDiv6-3 listA">');
	for (var localNum = 0; localNum < localList.length; localNum++) {
		if (!isNull(localList[localNum].typeId)) {
			if (typeId != localList[localNum].typeId) {
				if (mainContainStr.length != 1) {
					mainContainStr.push('			</tbody>');
					mainContainStr.push('		</table>');
					mainContainStr.push('	</div>');
				}
				mainContainStr.push('	<div>');
				mainContainStr.push('		<h4>' + localList[localNum].typeName + '</h4>');
				mainContainStr.push('		<table>');
				mainContainStr.push('			<tbody>');
			}

			mainContainStr.push('				<tr><th colspan="5" class="grey"><b>' + localList[localNum].localName + '</b></th></tr>');
			mainContainStr.push('				<tr><th>當前投注總額</th><th>中獎金額</th><th>返點</th><th>總投注數</th><th>盈虧金額</th></tr>');
			mainContainStr.push('				<tr>');
			if (!isNull(info[localNum].typeId)) {
				if (info[localNum].typeId == localList[localNum].typeId) {
					if (!isNull(info[localNum].totalBetAmount)) {
						mainContainStr.push('<td>' + info[localNum].totalBetAmount + '</td>');
					} else {
						mainContainStr.push('<td>0</td>');
					}
					if (!isNull(info[localNum].totalWinningBonus)) {
						mainContainStr.push('<td>' + info[localNum].totalWinningBonus + '</td>');
					} else {
						mainContainStr.push('<td>0</td>');
					}
					if (!isNull(info[localNum].totalFanDen)) {
						mainContainStr.push('<td>' + info[localNum].totalFanDen + '</td>');
					} else {
						mainContainStr.push('<td>0</td>');
					}
					if (!isNull(info[localNum].totalNoOfBet)) {
						mainContainStr.push('<td>' + info[localNum].totalNoOfBet + '</td>');
					} else {
						mainContainStr.push('<td>0</td>');
					}
					if (!isNull(info[localNum].totalWinningBonus) && !isNull(info[localNum].totalFanDen) && !isNull(info[localNum].totalBetAmount)) {
						totalAmount = Math.floor(info[localNum].totalWinningBonus * 100 + info[localNum].totalFanDen * 100
								- info[localNum].totalBetAmount * 100) / 100;
						if (totalAmount > 0) {
							mainContainStr.push('<td>' + totalAmount + '</td>');
						} else {
							mainContainStr.push('<td class="red">' + totalAmount + '</td>');
						}
					} else {
						mainContainStr.push('<td>0</td>');
					}
				}
			}
			mainContainStr.push('				</tr>');
		}
		typeId = localList[localNum].typeId;
	}
	mainContainStr.push('</div>');

	mainContainStr.join('');
	getEle('mainContain').innerHTML = mainContainStr.join('');
}
function showDateMainContain(type) {
	var json = JSON.parse(getEle('loginInfo').value);
	var levelType = json.basic.acc_level_type;
	var info = getEle("LotteryOperatorRecordInfo").value;
	var mainContainStr = [];
	var levelList = [ "平台商(總監)", "平台商(總監)", "平台商(總監)", "站點(大股東)" ];

	mainContainStr.push('<div class="listB-3" id="listB-1">');
	mainContainStr.push('	<ul id="mainTableUl" style="display: none;">');
	mainContainStr.push('		<li id="mainTableLevel"><li id="mainTableAccName"></li></li>');
	mainContainStr.push('		<li><a onclick="backSearch(this.value);" id="backSearchBtn">返回上一層</a></li>');
	mainContainStr.push('	</ul>');
	mainContainStr.push('	<table id="dateMain">');
	mainContainStr.push('		<tbody>');
	mainContainStr.push('			<tr><th>' + levelList[levelType]
			+ '</th><th>時間</th><th>彩種</th><th>投注總額</th><th>注單量</th><th>中獎金額</th><th>返點</th><th>總盈虧</th></tr>');
	mainContainStr.push('		</tbody>');
	mainContainStr.push('	</table>');
	mainContainStr.push('</div>');

	mainContainStr.join('');
	getEle('mainContain').innerHTML = mainContainStr.join('');
	onclickOperatorSearchBtn(getEle("operatorSearchBtn").value);
}
function showTableDate(type) {
	var info = JSON.parse(getEle("LotteryOperatorRecordInfo").value);
	var localList = JSON.parse(getEle('LotteryLocalList').value);
	var Obj = Object.keys(info);
	var totalAmount = '';

	var tableStr = [];

	var localName = [];

	localName.push('');
	for (var i = 0; i < localList.length; i++) {
		localName.push(localList[i].localName);
	}

	tableStr.push('<tr><th>平台商(總監)</th><th>時間</th><th>彩種</th><th>投注總額</th><th>注單量</th><th>中獎金額</th><th>返點</th><th>總盈虧</th></tr>');
	for (var i = 0; i < Obj.length; i++) {
		if (!isNull(info[Obj[i]].totalWinningBonus) && !isNull(info[Obj[i]].totalBetAmount) && !isNull(info[Obj[i]].totalFanDen)) {
			totalAmount = Math.floor(info[Obj[i]].totalWinningBonus * 100 + info[Obj[i]].totalFanDen * 100 - info[Obj[i]].totalBetAmount * 100) / 100;
		}

		tableStr.push('<tr>');
		tableStr.push('	<td>');
		tableStr.push('		<a href="javascript:void(0);" onclick="searchBCRecord(' + checkNull(info[Obj[i]].accId) + ',\''
				+ checkNull(info[Obj[i]].accName) + '\',' + checkNull(info[Obj[i]].gameId) + ',\'' + checkNull(info[Obj[i]].date) + '\')">'
				+ checkNull(info[Obj[i]].accName) + '</a>');
		tableStr.push('	</td>');
		tableStr.push('	<td>' + getEle('dateTimeList').value + '</td>');
		tableStr.push('	<td>' + checkNull(localName[info[Obj[i]].gameId]) + '</td>');
		tableStr.push('	<td>' + checkNull(info[Obj[i]].totalBetAmount) + '</td>');
		tableStr.push('	<td>' + checkNull(info[Obj[i]].totalNoOfBet) + '</td>');
		tableStr.push('	<td>' + checkNull(info[Obj[i]].totalWinningBonus) + '</td>');
		tableStr.push('	<td>' + checkNull(info[Obj[i]].totalFanDen) + '</td>');
		if (totalAmount >= 0) {
			tableStr.push('	<td>' + totalAmount + '</td>');
		} else {
			tableStr.push('	<td class="red">' + totalAmount + '</td>');
		}
		tableStr.push('</tr>');
	}

	getEle('dateMain').innerHTML = tableStr.join('');
}
function showBCTableDate(type) {
	var info = JSON.parse(getEle("LotteryOperatorRecordInfo").value);
	var localList = JSON.parse(getEle('LotteryLocalList').value);
	var Obj = Object.keys(info);
	var totalAmount = '';

	var tableStr = [];

	var localName = [];

	localName.push('');
	for (var i = 0; i < localList.length; i++) {
		localName.push(localList[i].localName);
	}

	tableStr.push('<tr><th>站點(大股東)</th><th>時間</th><th>彩種</th><th>投注總額</th><th>注單量</th><th>中獎金額</th><th>返點</th><th>總盈虧</th></tr>');
	for (var i = 0; i < Obj.length; i++) {
		if (!isNull(info[Obj[i]].totalWinningBonus) && !isNull(info[Obj[i]].totalBetAmount) && !isNull(info[Obj[i]].totalFanDen)) {
			totalAmount = Math.floor(info[Obj[i]].totalWinningBonus * 100 + info[Obj[i]].totalFanDen * 100 - info[Obj[i]].totalBetAmount * 100) / 100;
		}

		tableStr.push('<tr>');
		tableStr.push('	<td>');
		tableStr.push('		<a href="javascript:void(0);" onclick="searchMemRecord(' + checkNull(info[Obj[i]].accId) + ',\''
				+ checkNull(info[Obj[i]].accName) + '\',' + checkNull(info[Obj[i]].gameId) + ',\'' + checkNull(info[Obj[i]].date) + '\')">'
				+ checkNull(info[Obj[i]].accName) + '</a>');
		tableStr.push('	</td>');
		tableStr.push('	<td>' + getEle('dateTimeList').value + '</td>');
		tableStr.push('	<td>' + checkNull(localName[info[Obj[i]].gameId]) + '</td>');
		tableStr.push('	<td>' + checkNull(info[Obj[i]].totalBetAmount) + '</td>');
		tableStr.push('	<td>' + checkNull(info[Obj[i]].totalNoOfBet) + '</td>');
		tableStr.push('	<td>' + checkNull(info[Obj[i]].totalWinningBonus) + '</td>');
		tableStr.push('	<td>' + checkNull(info[Obj[i]].totalFanDen) + '</td>');
		if (totalAmount >= 0) {
			tableStr.push('	<td>' + totalAmount + '</td>');
		} else {
			tableStr.push('	<td class="red">' + totalAmount + '</td>');
		}
		tableStr.push('</tr>');
	}

	getEle('dateMain').innerHTML = tableStr.join('');
}
function showMemTableDate(type) {
	var info = JSON.parse(getEle("LotteryOperatorRecordInfo").value);
	var localList = JSON.parse(getEle('LotteryLocalList').value);
	var Obj = Object.keys(info);
	var totalAmount = '';

	var tableStr = [];

	var localName = [];

	localName.push('');
	for (var i = 0; i < localList.length; i++) {
		localName.push(localList[i].localName);
	}

	tableStr.push('<tr><th>玩家</th><th>時間</th><th>彩種</th><th>投注總額</th><th>注單量</th><th>中獎金額</th><th>返點</th><th>總盈虧</th></tr>');
	for (var i = 0; i < Obj.length; i++) {
		if (!isNull(info[Obj[i]].totalWinningBonus) && !isNull(info[Obj[i]].totalBetAmount) && !isNull(info[Obj[i]].totalFanDen)) {
			totalAmount = Math.floor(info[Obj[i]].totalWinningBonus * 100 + info[Obj[i]].totalFanDen * 100 - info[Obj[i]].totalBetAmount * 100) / 100;
		}

		tableStr.push('<tr>');
		tableStr.push('	<td>' + checkNull(info[Obj[i]].accName) + '</td>');
		tableStr.push('	<td>' + getEle('dateTimeList').value + '</td>');
		tableStr.push('	<td>' + checkNull(localName[info[Obj[i]].gameId]) + '</td>');
		tableStr.push('	<td>' + checkNull(info[Obj[i]].totalBetAmount) + '</td>');
		tableStr.push('	<td>' + checkNull(info[Obj[i]].totalNoOfBet) + '</td>');
		tableStr.push('	<td>' + checkNull(info[Obj[i]].totalWinningBonus) + '</td>');
		tableStr.push('	<td>' + checkNull(info[Obj[i]].totalFanDen) + '</td>');
		if (totalAmount >= 0) {
			tableStr.push('	<td>' + totalAmount + '</td>');
		} else {
			tableStr.push('	<td class="red">' + totalAmount + '</td>');
		}
		tableStr.push('</tr>');
	}

	getEle('dateMain').innerHTML = tableStr.join('');
}
function onclickOperatorTopBtn(val) {
	refreshOperatorSearchArea();
	var exportToExcelBtn = document.getElementById('dateSearchDiv').getElementsByTagName('a')[0];

	if (val == LOTTERY_OPERATOR_RECORD_TODAY_JS) {
		getEle('todayTopBtn').classList.add('active');
		getEle('todaySearchDiv').style.display = 'block';
		getTodayOperatorRecordAjax();
	} else if (val == LOTTERY_OPERATOR_RECORD_DAY_JS) {
		getEle('operatorSearchBtn').value = LOTTERY_OPERATOR_RECORD_DAY_JS;
		getEle('dayTopBtn').classList.add('active');
		getEle('dateSearchDiv').style.display = 'block';
		addLocalList('dateLocalList');
		addTimeList(30);
		showDateMainContain(LOTTERY_OPERATOR_RECORD_DAY_JS);
		exportToExcelBtn.onclick = function() {
			chkTableToExcel('dateMain', 'lotteryOperatorDaysRecord');
		};
	} else if (val == LOTTERY_OPERATOR_RECORD_WEEK_JS) {
		getEle('operatorSearchBtn').value = LOTTERY_OPERATOR_RECORD_WEEK_JS;
		getEle('weekTopBtn').classList.add('active');
		getEle('dateSearchDiv').style.display = 'block';
		addLocalList('dateLocalList');
		addTimeList(7);
		showDateMainContain(LOTTERY_OPERATOR_RECORD_WEEK_JS);
		exportToExcelBtn.onclick = function() {
			chkTableToExcel('dateMain', 'lotteryOperatorWeekRecord');
		};
	} else if (val == LOTTERY_OPERATOR_RECORD_MOUNTH_JS) {
		getEle('operatorSearchBtn').value = LOTTERY_OPERATOR_RECORD_MOUNTH_JS;
		getEle('mounthTopBtn').classList.add('active');
		getEle('dateSearchDiv').style.display = 'block';
		addLocalList('dateLocalList');
		addTimeList(6);
		showDateMainContain(LOTTERY_OPERATOR_RECORD_MOUNTH_JS);
		exportToExcelBtn.onclick = function() {
			chkTableToExcel('dateMain', 'lotteryOperatorMounthRecord');
		};
	}
}
function refreshOperatorSearchArea() {
	getEle('todayTopBtn').classList.remove('active');
	getEle('dayTopBtn').classList.remove('active');
	getEle('weekTopBtn').classList.remove('active');
	getEle('mounthTopBtn').classList.remove('active');

	getEle('operatorSearchBtn').value = '';

	getEle('todaySearchDiv').style.display = 'none';
	getEle('dateSearchDiv').style.display = 'none';

	getEle('dateText').value = '';

	document.getElementById('dateSearchDiv').getElementsByTagName('a')[0].onclick = '';
}
function addLocalList() {
	var localList = JSON.parse(getEle('LotteryLocalList').value);

	removeAllOption('dateLocalList');
	addOptionNoDup('dateLocalList', '所有彩種', 0);
	for (var i = 0; i < localList.length; i++) {
		addOptionNoDup('dateLocalList', localList[i].localName, localList[i].localId);
	}
}
function addTimeList(range) {
	var timeList = [];

	if (range == 30) {
		timeList = getDaysList(range);
	} else if (range == 7) {
		timeList = getWeekList(range);
	} else if (range == 6) {
		timeList = getMonthList(range);
	}

	removeAllOption('dateTimeList');
	for (var i = 0; i < timeList.length; i++) {
		addOptionNoDup('dateTimeList', timeList[i], timeList[i]);
	}
}
// 以下時間selectList
function getDaysList(range) {
	var nowDate = new Date();
	var dayAgo = [];

	nowDate.setDate(nowDate.getDate() - 1);

	for (var i = 0; i < range; i++) {
		dayAgo.push(nowDate.getFromFormat('yyyy/MM/dd'));
		nowDate.setDate(nowDate.getDate() - 1);

	}
	return dayAgo;
}
function getWeekList(range) {
	var nowDate = new Date();
	var nowDayOfWeek = '';
	var nowDay = '';
	var nowMounth = '';
	var nowYear = '';
	var start = '';
	var end = '';

	var weekAgo = [];

	for (var i = 0; i < range; i++) {

		nowDayOfWeek = nowDate.getDay();
		nowDay = nowDate.getDate();
		nowMounth = nowDate.getMonth();
		nowYear = nowDate.getYear();
		nowYear += (nowYear < 2000) ? 1900 : 0;

		start = new Date(nowYear, nowMounth, nowDay - nowDayOfWeek - 6);
		end = new Date(nowYear, nowMounth, nowDay - nowDayOfWeek);
		weekAgo.push(start.getFromFormat("yyyy/MM/dd") + '~' + end.getFromFormat("yyyy/MM/dd"));
		nowDate.setDate(nowDate.getDate() - 7);
	}
	return weekAgo;
}
function getMonthList(range) {
	var nowDate = new Date();
	var mounthAgo = [];

	for (var i = 0; i < range; i++) {
		nowDate.setDate(nowDate.getMonth() - nowDate.getMonth());
		mounthAgo.push(nowDate.getFromFormat('yyyy/MM/dd'));
	}
	return mounthAgo;
}
function onclickOperatorSearchBtn(type) {
	var accName = getEle('dateText').value;
	var localId = getEle('dateLocalList').value;
	var dateTime = getEle('dateTimeList').value;
	var str = '';

	str = '&accName=' + accName + '&localId=' + localId + '&dateTime=' + dateTime + '&type=' + type;
	searchOperatorRecordByDateAjax(str);
	getEle('scSearchInfo').value = str;
}
function searchBCRecord(accId, accName, localId, dateTime) {
	var type = getEle('operatorSearchBtn').value
	var str = '';

	getEle('mainTableLevel').innerHTML = '平台商(總監):';
	getEle('mainTableAccName').innerHTML = accName;

	str = '&accId=' + accId + '&localId=' + localId + '&dateTime=' + dateTime + '&type=' + type;
	searchBCRecordAjax(str);
	getEle('bcSearchInfo').value = str;
	getEle('backSearchBtn').value = 1;
}
function searchMemRecord(accId, accName, localId, dateTime) {
	var type = getEle('operatorSearchBtn').value
	var str = '';

	getEle('mainTableLevel').innerHTML = '大股東:';
	getEle('mainTableAccName').innerHTML = accName;

	str = '&accId=' + accId + '&localId=' + localId + '&dateTime=' + dateTime + '&type=' + type;
	searchMemRecordAjax(str);
	getEle('backSearchBtn').value = 2;
}
function backSearch(level) {
	var str = '';

	if (level == 1) {
		str = getEle('scSearchInfo').value;
		searchOperatorRecordByDateAjax(str);
		getEle('backSearchBtn').value = 0;
	} else if (level == 2) {
		str = getEle('bcSearchInfo').value;
		searchBCRecordAjax(str);
		getEle('backSearchBtn').value = 1;
	}
}
// 以下開始Ajax
function getOperatorRecordLocalListAjax() {
	var str = '';
	LOTTERY_OPERATOR_RECORD_XHR = checkXHR(LOTTERY_OPERATOR_RECORD_XHR);
	if (typeof LOTTERY_OPERATOR_RECORD_XHR != "undefined" && LOTTERY_OPERATOR_RECORD_XHR != null) {
		str = "tokenId=" + encodeURIComponent(getEle("tokenId").value);
		LOTTERY_OPERATOR_RECORD_XHR.timeout = 10000;
		LOTTERY_OPERATOR_RECORD_XHR.ontimeout = function() {
			disableLoadingPage();
			LOTTERY_OPERATOR_RECORD_XHR.abort();
		}
		LOTTERY_OPERATOR_RECORD_XHR.onerror = function() {
			disableLoadingPage();
			LOTTERY_OPERATOR_RECORD_XHR.abort();
		}
		LOTTERY_OPERATOR_RECORD_XHR.onreadystatechange = handleGetOperatorRecordLocalListAjax;
		LOTTERY_OPERATOR_RECORD_XHR.open("POST", "./LotteryOperatorRecord!getLotteryLocalList.php?date=" + getNewTime(), true);
		LOTTERY_OPERATOR_RECORD_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LOTTERY_OPERATOR_RECORD_XHR.send(str);
		enableLoadingPage();
	}
}
function handleGetOperatorRecordLocalListAjax() {
	if (LOTTERY_OPERATOR_RECORD_XHR.readyState == 4) {
		if (LOTTERY_OPERATOR_RECORD_XHR.status == 200) {
			try {
				if (isJSON(LOTTERY_OPERATOR_RECORD_XHR.responseText)) {
					var json = JSON.parse(LOTTERY_OPERATOR_RECORD_XHR.responseText);
					if (!isNull(json.LotteryLocalList)) {
						getEle('LotteryLocalList').value = JSON.stringify(json.LotteryLocalList);
					}
				}
			} catch (error) {
				console_Log("handleGetOperatorRecordLocalListAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LOTTERY_OPERATOR_RECORD_XHR.abort();
				onclickOperatorTopBtn(LOTTERY_OPERATOR_RECORD_TODAY_JS);
			}
		} else {
			disableLoadingPage();
			LOTTERY_OPERATOR_RECORD_XHR.abort();
		}
	}
}
function getTodayOperatorRecordAjax() {
	var json = JSON.parse(getEle('loginInfo').value);
	var levelType = json.basic.acc_level_type;
	var str = '';
	LOTTERY_OPERATOR_RECORD_XHR = checkXHR(LOTTERY_OPERATOR_RECORD_XHR);
	if (typeof LOTTERY_OPERATOR_RECORD_XHR != "undefined" && LOTTERY_OPERATOR_RECORD_XHR != null) {
		str = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&levelType=" + levelType;
		LOTTERY_OPERATOR_RECORD_XHR.timeout = 10000;
		LOTTERY_OPERATOR_RECORD_XHR.ontimeout = function() {
			disableLoadingPage();
			LOTTERY_OPERATOR_RECORD_XHR.abort();
		}
		LOTTERY_OPERATOR_RECORD_XHR.onerror = function() {
			disableLoadingPage();
			LOTTERY_OPERATOR_RECORD_XHR.abort();
		}
		LOTTERY_OPERATOR_RECORD_XHR.onreadystatechange = handleGetTodayOperatorRecordAjax;
		LOTTERY_OPERATOR_RECORD_XHR.open("POST", "./LotteryOperatorRecord!getTodayOperatorRecord.php?date=" + getNewTime(), true);
		LOTTERY_OPERATOR_RECORD_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LOTTERY_OPERATOR_RECORD_XHR.send(str);
		enableLoadingPage();
	}
}
function handleGetTodayOperatorRecordAjax() {
	if (LOTTERY_OPERATOR_RECORD_XHR.readyState == 4) {
		if (LOTTERY_OPERATOR_RECORD_XHR.status == 200) {
			try {
				if (isJSON(LOTTERY_OPERATOR_RECORD_XHR.responseText)) {
					var json = JSON.parse(LOTTERY_OPERATOR_RECORD_XHR.responseText);
					if (!isNull(json.TodayOperatorRecord)) {
						getEle('LotteryOperatorRecordInfo').value = JSON.stringify(json.TodayOperatorRecord);
					}
					showTodayMainContain();
				}
			} catch (error) {
				console_Log("handleGetTodayOperatorRecordAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LOTTERY_OPERATOR_RECORD_XHR.abort();
			}
		} else {
			disableLoadingPage();
			LOTTERY_OPERATOR_RECORD_XHR.abort();
		}
	}
}
function searchOperatorRecordByDateAjax(str) {
	var tmpStr = '';
	LOTTERY_OPERATOR_RECORD_XHR = checkXHR(LOTTERY_OPERATOR_RECORD_XHR);
	if (typeof LOTTERY_OPERATOR_RECORD_XHR != "undefined" && LOTTERY_OPERATOR_RECORD_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + str;
		LOTTERY_OPERATOR_RECORD_XHR.timeout = 10000;
		LOTTERY_OPERATOR_RECORD_XHR.ontimeout = function() {
			disableLoadingPage();
			LOTTERY_OPERATOR_RECORD_XHR.abort();
		}
		LOTTERY_OPERATOR_RECORD_XHR.onerror = function() {
			disableLoadingPage();
			LOTTERY_OPERATOR_RECORD_XHR.abort();
		}
		LOTTERY_OPERATOR_RECORD_XHR.onreadystatechange = handleSearchOperatorRecordByDateAjax;
		LOTTERY_OPERATOR_RECORD_XHR.open("POST", "./LotteryOperatorRecord!searchOperatorRecordByDate.php?date=" + getNewTime(), true);
		LOTTERY_OPERATOR_RECORD_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LOTTERY_OPERATOR_RECORD_XHR.send(tmpStr);
		enableLoadingPage();
	}
}
function handleSearchOperatorRecordByDateAjax() {
	if (LOTTERY_OPERATOR_RECORD_XHR.readyState == 4) {
		if (LOTTERY_OPERATOR_RECORD_XHR.status == 200) {
			try {
				if (isJSON(LOTTERY_OPERATOR_RECORD_XHR.responseText)) {
					var json = JSON.parse(LOTTERY_OPERATOR_RECORD_XHR.responseText);
					if (!isNull(json.searchOperatorRecordByDate)) {
						getEle('LotteryOperatorRecordInfo').value = JSON.stringify(json.searchOperatorRecordByDate);
					}
					if (!isNull(json.dateType)) {
						showTableDate(json.dateType);
					}
				}
			} catch (error) {
				console_Log("handleGetOperatorRecordLocalListAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LOTTERY_OPERATOR_RECORD_XHR.abort();
				getEle('mainTableUl').style.display = 'none';
			}
		} else {
			disableLoadingPage();
			LOTTERY_OPERATOR_RECORD_XHR.abort();
		}
	}
}
function searchBCRecordAjax(str) {
	var tmpStr = '';
	LOTTERY_OPERATOR_RECORD_XHR = checkXHR(LOTTERY_OPERATOR_RECORD_XHR);
	if (typeof LOTTERY_OPERATOR_RECORD_XHR != "undefined" && LOTTERY_OPERATOR_RECORD_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + str;
		LOTTERY_OPERATOR_RECORD_XHR.timeout = 10000;
		LOTTERY_OPERATOR_RECORD_XHR.ontimeout = function() {
			disableLoadingPage();
			LOTTERY_OPERATOR_RECORD_XHR.abort();
		}
		LOTTERY_OPERATOR_RECORD_XHR.onerror = function() {
			disableLoadingPage();
			LOTTERY_OPERATOR_RECORD_XHR.abort();
		}
		LOTTERY_OPERATOR_RECORD_XHR.onreadystatechange = handleSearchBCRecordAjax;
		LOTTERY_OPERATOR_RECORD_XHR.open("POST", "./LotteryOperatorRecord!searchBCRecord.php?date=" + getNewTime(), true);
		LOTTERY_OPERATOR_RECORD_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LOTTERY_OPERATOR_RECORD_XHR.send(tmpStr);
		enableLoadingPage();
	}
}
function handleSearchBCRecordAjax() {
	if (LOTTERY_OPERATOR_RECORD_XHR.readyState == 4) {
		if (LOTTERY_OPERATOR_RECORD_XHR.status == 200) {
			try {
				if (isJSON(LOTTERY_OPERATOR_RECORD_XHR.responseText)) {
					var json = JSON.parse(LOTTERY_OPERATOR_RECORD_XHR.responseText);
					if (!isNull(json.searchBCRecord)) {
						getEle('LotteryOperatorRecordInfo').value = JSON.stringify(json.searchBCRecord);
					}
					if (!isNull(json.dateType)) {
						showBCTableDate(json.dateType);
					}
				}
			} catch (error) {
				console_Log("handleSearchBCRecordAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LOTTERY_OPERATOR_RECORD_XHR.abort();
				getEle('mainTableUl').style.display = 'block';
			}
		} else {
			disableLoadingPage();
			LOTTERY_OPERATOR_RECORD_XHR.abort();
		}
	}
}
function searchMemRecordAjax(str) {
	var tmpStr = '';
	LOTTERY_OPERATOR_RECORD_XHR = checkXHR(LOTTERY_OPERATOR_RECORD_XHR);
	if (typeof LOTTERY_OPERATOR_RECORD_XHR != "undefined" && LOTTERY_OPERATOR_RECORD_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + str;
		LOTTERY_OPERATOR_RECORD_XHR.timeout = 10000;
		LOTTERY_OPERATOR_RECORD_XHR.ontimeout = function() {
			disableLoadingPage();
			LOTTERY_OPERATOR_RECORD_XHR.abort();
		}
		LOTTERY_OPERATOR_RECORD_XHR.onerror = function() {
			disableLoadingPage();
			LOTTERY_OPERATOR_RECORD_XHR.abort();
		}
		LOTTERY_OPERATOR_RECORD_XHR.onreadystatechange = handleSearchMemRecordAjax;
		LOTTERY_OPERATOR_RECORD_XHR.open("POST", "./LotteryOperatorRecord!searchMemRecord.php?date=" + getNewTime(), true);
		LOTTERY_OPERATOR_RECORD_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LOTTERY_OPERATOR_RECORD_XHR.send(tmpStr);
		enableLoadingPage();
	}
}
function handleSearchMemRecordAjax() {
	if (LOTTERY_OPERATOR_RECORD_XHR.readyState == 4) {
		if (LOTTERY_OPERATOR_RECORD_XHR.status == 200) {
			try {
				if (isJSON(LOTTERY_OPERATOR_RECORD_XHR.responseText)) {
					var json = JSON.parse(LOTTERY_OPERATOR_RECORD_XHR.responseText);
					if (!isNull(json.searchMemRecord)) {
						getEle('LotteryOperatorRecordInfo').value = JSON.stringify(json.searchMemRecord);
					}
					if (!isNull(json.dateType)) {
						showMemTableDate(json.dateType);
					}
				}
			} catch (error) {
				console_Log("handleSearchMemRecordAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LOTTERY_OPERATOR_RECORD_XHR.abort();
				getEle('mainTableUl').style.display = 'block';
			}
		} else {
			disableLoadingPage();
			LOTTERY_OPERATOR_RECORD_XHR.abort();
		}
	}
}