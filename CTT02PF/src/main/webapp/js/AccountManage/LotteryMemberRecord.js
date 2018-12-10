var LOTTERY_MEMBER_RECORD_XHR = null;

const LOTTERY_MEMBER_RECORD_JS = true;

const LOTTERY_DAY_RECORD_JS = 1;
const LOTTERY_WEEK_RECORD_JS = 2;
const LOTTERY_MOUNTH_RECORD_JS = 3;
const LOTTERY_MEMBER_DAY_RECORD_JS = 4;
const LOTTERY_MEMBER_DAILY_RECORD_JS = 5;

function LotteryMemberRecord() {
	LotteryMemberRecord_into();
	LotteryMemberRecordSearchArea();
	LotteryMemberRecordMainContain();
	getMemberRecordLocalListAjax();
}

function LotteryMemberRecord_into() {
	var str = '';
	if (document.getElementsByName("Info").length != 1) {
		str += '\n<input type="hidden" name="LotteryLocalList" value="">';
		str += '\n<input type="hidden" name="LotteryMemberRecordInfo" value="">';
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

function LotteryMemberRecordSearchArea() {
	var searchAreaStr = [];

	searchAreaStr.push('<div class="contentDiv2-17">');
	searchAreaStr.push('	<button onclick="memberRecordTopBtn(' + LOTTERY_DAY_RECORD_JS + ')" id="dayRecordTopBtn">日彩種盈虧報表</button>');
	searchAreaStr.push('	<button onclick="memberRecordTopBtn(' + LOTTERY_WEEK_RECORD_JS + ')" id="weekRecordTopBtn">週彩種盈虧報表</button>');
	searchAreaStr.push('	<button onclick="memberRecordTopBtn(' + LOTTERY_MOUNTH_RECORD_JS + ')" id="mounthRecordTopBtn">月彩種盈虧報表</button>');
	searchAreaStr.push('	<button onclick="memberRecordTopBtn(' + LOTTERY_MEMBER_DAY_RECORD_JS + ')" id="memberDayTopBtn">用戶當前盈虧報表</button>');
	searchAreaStr.push('	<button onclick="memberRecordTopBtn(' + LOTTERY_MEMBER_DAILY_RECORD_JS + ')" id="memberDailyTopBtn">用戶日盈虧報表</button>');
	searchAreaStr.push('		<ul>');
	searchAreaStr.push('			<li style="display: none;" id="memberRecordTextLi">帳號：<input type="text" id="memberRecordText"></li>');
	searchAreaStr.push('			<li>彩種：<select id="memberRecordLocalList"></select></li>');
	searchAreaStr.push('			<li class="time">時間：');
	searchAreaStr.push('				<input type="text" onclick="newCalendar(this,this.id,1);" id="memberRecordStartTime" readonly>到');
	searchAreaStr.push('				<input type="text" onclick="newCalendar(this,this.id,2);" id="memberRecordEndTime" readonly>');
	searchAreaStr.push('			</li>');
	searchAreaStr.push('			<li><button onclick="memberRecordSearch(this.value)" id="memberRecordSearchBtn">查尋</button></li>');
	searchAreaStr.push('		</ul>');
	searchAreaStr.push('</div>');

	searchAreaStr.join('');
	getEle('searchArea').innerHTML = searchAreaStr.join('');
}
function LotteryMemberRecordMainContain() {
	var mainContainStr = [];

	mainContainStr.push('		<table id="memberRecordTable">');
	mainContainStr.push('			<tbody>');
	mainContainStr.push('			</tbody>');
	mainContainStr.push('		</table>');

	mainContainStr.join('');
	getEle('mainContain').innerHTML = mainContainStr.join('');
}

function memberRecordTopBtn(val) {
	refreshMemberSearchArea(val);

	if (val == LOTTERY_DAY_RECORD_JS) {
		getEle('dayRecordTopBtn').classList.add('active');
		getEle('memberRecordSearchBtn').value = LOTTERY_DAY_RECORD_JS;
		memberRecordSearch(LOTTERY_DAY_RECORD_JS);
	} else if (val == LOTTERY_WEEK_RECORD_JS) {
		getEle('weekRecordTopBtn').classList.add('active');
		getEle('memberRecordSearchBtn').value = LOTTERY_WEEK_RECORD_JS;
		memberRecordSearch(LOTTERY_WEEK_RECORD_JS);
	} else if (val == LOTTERY_MOUNTH_RECORD_JS) {
		getEle('mounthRecordTopBtn').classList.add('active');
		getEle('memberRecordSearchBtn').value = LOTTERY_MOUNTH_RECORD_JS;
		memberRecordSearch(LOTTERY_MOUNTH_RECORD_JS);
	} else if (val == LOTTERY_MEMBER_DAY_RECORD_JS) {
		getEle('memberDayTopBtn').classList.add('active');
		getEle('memberRecordTextLi').style.display = 'block';
		getEle('memberRecordSearchBtn').value = LOTTERY_MEMBER_DAY_RECORD_JS;
	} else if (val == LOTTERY_MEMBER_DAILY_RECORD_JS) {
		getEle('memberDailyTopBtn').classList.add('active');
		getEle('memberRecordTextLi').style.display = 'block';
		getEle('memberRecordSearchBtn').value = LOTTERY_MEMBER_DAILY_RECORD_JS;
	}
}
function innertTableTitle(type) {
	var tableTitleStr = [];

	if (type == LOTTERY_MEMBER_DAY_RECORD_JS) {
		tableTitleStr.push('		<tr><th>帳號</th><th>日期</th><th>彩種</th><th>投注單數</th><th>撤單數</th><th>投注金額</th><th>中獎金額</th><th>返點</th><th>盈虧</th></tr>');
	} else if (type == LOTTERY_MEMBER_DAILY_RECORD_JS) {
		tableTitleStr.push('		<tr><th>帳號</th><th>日期</th><th>彩種</th><th>投注單數</th><th>撤單數</th><th>投注金額</th><th>中獎金額</th><th>返點</th><th>盈虧</th></tr>');
	} else {
		tableTitleStr.push('		<tr><th>日期</th></tr>');
		tableTitleStr.push('		<tr><th>彩種</th></tr>');
		tableTitleStr.push('		<tr><th>投注總額</th></tr>');
		tableTitleStr.push('		<tr><th>注單量</th></tr>');
		tableTitleStr.push('		<tr><th>中獎金額</th></tr>');
		tableTitleStr.push('		<tr><th>返點</th></tr>');
		tableTitleStr.push('		<tr><th>淨利</th></tr>');
	}
	getEle('memberRecordTable').innerHTML = tableTitleStr.join('');
}
function showMemDayRecord() {
	var info = JSON.parse(getEle("LotteryMemberRecordInfo").value);
	var localList = JSON.parse(getEle('LotteryLocalList').value);
	var Obj = Object.keys(info);
	var totalAmount = '';

	var memDayRecordTableStr = [];

	var localName = [];

	localName.push('');
	for (var i = 0; i < localList.length; i++) {
		localName.push(checkNull(localList[i].title));
	}

	memDayRecordTableStr.push('<tr><th>帳號</th><th>日期</th><th>彩種</th><th>投注單數</th><th>撤單數</th><th>投注金額</th><th>中獎金額</th><th>返點</th><th>盈虧</th></tr>');
	for (var i = 0; i < Obj.length; i++) {
		if (!isNull(info[Obj[i]].totalWinningBonus) && !isNull(info[Obj[i]].totalBetAmount) && !isNull(info[Obj[i]].totalFanDen)) {
			totalAmount = Math.floor(info[Obj[i]].totalWinningBonus * 100 + info[Obj[i]].totalFanDen * 100 - info[Obj[i]].totalBetAmount * 100) / 100;
		}

		memDayRecordTableStr.push('<tr>');
		memDayRecordTableStr.push('	<td>' + checkNull(info[Obj[i]].accName) + '</td>');
		memDayRecordTableStr.push('	<td>' + checkNull(info[Obj[i]].date) + '</td>');
		memDayRecordTableStr.push('	<td>' + checkNull(localName[info[Obj[i]].gameId]) + '</td>');
		memDayRecordTableStr.push('	<td>' + checkNull(info[Obj[i]].totalNoOfBet) + '</td>');
		memDayRecordTableStr.push('	<td>' + checkNull(info[Obj[i]].totalWithdrawal) + '</td>');
		memDayRecordTableStr.push('	<td>' + checkNull(info[Obj[i]].totalBetAmount) + '</td>');
		memDayRecordTableStr.push('	<td>' + checkNull(info[Obj[i]].totalWinningBonus) + '</td>');
		memDayRecordTableStr.push('	<td>' + checkNull(info[Obj[i]].totalFanDen) + '</td>');
		if (totalAmount >= 0) {
			memDayRecordTableStr.push('	<td>' + totalAmount + '</td>');
		} else {
			memDayRecordTableStr.push('	<td class="red">' + totalAmount + '</td>');
		}
		memDayRecordTableStr.push('</tr>');
	}

	getEle('memberRecordTable').innerHTML = memDayRecordTableStr.join('');

}
function showMemRecordByDate(dateType, localId) {
	var info = JSON.parse(getEle("LotteryMemberRecordInfo").value);
	var localList = JSON.parse(getEle('LotteryLocalList').value);
	var Obj = Object.keys(info);
	var totalAmount = '';

	var memRecordByDateTableStr = [];
	var titleList = [ '日期', '彩種', '投注總額', '注單量', '中獎金額', '返點', '淨利' ];
	var valueList = [ 'date', 'localId', 'totalBetAmount', 'totalNoOfBet', 'totalWinningBonus', 'totalFanDen', 'totalAmount' ];

	var localName = [];

	localName.push('所有彩種');
	for (var i = 0; i < localList.length; i++) {
		localName.push(checkNull(localList[i].title));
	}
	for (var title = 0; title < titleList.length; title++) {
		memRecordByDateTableStr.push('<tr><th>' + titleList[title] + '</th>');
		for (var value = 0; value < Obj.length; value++) {
			if (valueList[title] == 'localId') {
				memRecordByDateTableStr.push('<td>' + checkNull(localName[localId]) + '</td>');
			} else if (valueList[title] == 'totalAmount') {
				if (!isNull(info[Obj[value]].totalWinningBonus) && !isNull(info[Obj[value]].totalBetAmount) && !isNull(info[Obj[value]].totalFanDen)) {
					totalAmount = Math.floor(info[Obj[value]].totalWinningBonus * 100 + info[Obj[value]].totalFanDen * 100
							- info[Obj[value]].totalBetAmount * 100) / 100;
				}
				if (totalAmount >= 0) {
					memRecordByDateTableStr.push('<td>' + totalAmount + '</td>');
				} else {
					memRecordByDateTableStr.push('<td class="red">' + totalAmount + '</td>');
				}
			} else if (valueList[title] == 'date') {
				if (dateType == LOTTERY_WEEK_RECORD_JS) {
					memRecordByDateTableStr.push('<td>' + getWeekAgo(checkNull(info[Obj[value]].date)) + '</td>');
				} else {
					memRecordByDateTableStr.push('<td>' + checkNull(info[Obj[value]].date) + '</td>');
				}
			} else {
				memRecordByDateTableStr.push('<td>' + checkNull(info[Obj[value]][valueList[title]]) + '</td>');
			}
		}
		memRecordByDateTableStr.push('</tr>');
	}
	getEle('memberRecordTable').innerHTML = memRecordByDateTableStr.join('');
}

function showMemRecordByDay(localId) {
	var info = insertBySameDate();
	var localList = JSON.parse(getEle('LotteryLocalList').value);
	var Obj = Object.keys(info);
	var totalAmount = '';

	var memRecordByDateTableStr = [];
	var titleList = [ '日期', '彩種', '投注總額', '注單量', '中獎金額', '返點', '淨利' ];
	var valueList = [ 'date', 'localId', 'totalBetAmount', 'totalNoOfBet', 'totalWinningBonus', 'totalFanDen', 'totalAmount' ];

	var localName = [];
	var sevenDaysAgoList = getDaysAgoList();
	var num;

	localName.push('所有彩種');
	for (var i = 0; i < localList.length; i++) {
		localName.push(checkNull(localList[i].title));
	}
	for (var title = 0; title < titleList.length; title++) {
		num = 0;
		memRecordByDateTableStr.push('<tr><th>' + titleList[title] + '</th>');
		for (var value = 0; value < sevenDaysAgoList.length; value++) {
			if (valueList[title] == 'date') {
				memRecordByDateTableStr.push('<td>' + sevenDaysAgoList[value] + '</td>');
			} else if (valueList[title] == 'localId') {
				memRecordByDateTableStr.push('<td>' + checkNull(localName[localId]) + '</td>');
			} else if (valueList[title] == 'totalAmount') {
				if (!isNull(info[Obj[num]])) {
					if (info[Obj[num]].date == sevenDaysAgoList[value]) {
						if (!isNull(info[Obj[num]].totalWinningBonus) && !isNull(info[Obj[num]].totalBetAmount)
								&& !isNull(info[Obj[num]].totalFanDen)) {
							totalAmount = Math.floor(info[Obj[num]].totalWinningBonus * 100 + info[Obj[num]].totalFanDen * 100
									- info[Obj[num]].totalBetAmount * 100) / 100;
						}
						if (totalAmount >= 0) {
							memRecordByDateTableStr.push('<td>' + totalAmount + '</td>');
						} else {
							memRecordByDateTableStr.push('<td class="red">' + totalAmount + '</td>');
						}
					}
				} else {
					memRecordByDateTableStr.push('<td>0</td>');
				}
			} else {
				if (!isNull(info[Obj[num]])) {
					if (info[Obj[num]].date == sevenDaysAgoList[value]) {
						memRecordByDateTableStr.push('<td>' + checkNull(info[Obj[num]][valueList[title]]) + '</td>');
					}
				} else {
					memRecordByDateTableStr.push('<td>0</td>');
				}
			}
			num++;
		}
		memRecordByDateTableStr.push('</tr>');
	}
	getEle('memberRecordTable').innerHTML = memRecordByDateTableStr.join('');
}
function insertBySameDate() {
	var info = JSON.parse(getEle("LotteryMemberRecordInfo").value);
	var Obj = Object.keys(info);
	var daysAgoList = getDaysAgoList();
	var newInfoJson = new Array();

	for (var d = 0; d < daysAgoList.length; d++) {
		var newDetailJson = new Object();
		if (!isNull(daysAgoList[d])) {
			if (Obj.length == 0) {
				newDetailJson.date = daysAgoList[d];
				newDetailJson.totalBetAmount = 0;
				newDetailJson.totalFanDen = 0;
				newDetailJson.totalNoOfBet = 0;
				newDetailJson.totalWinningBonus = 0;
			} else {
				for (var i = 0; i < Obj.length; i++) {
					if (!isNull(info[Obj[i]])) {
						if (!isNull(info[Obj[i]].date)) {
							if (daysAgoList[d] == info[Obj[i]].date) {
								newDetailJson.date = info[Obj[i]].date;
								newDetailJson.totalBetAmount = info[Obj[i]].totalBetAmount;
								newDetailJson.totalFanDen = info[Obj[i]].totalFanDen;
								newDetailJson.totalNoOfBet = info[Obj[i]].totalNoOfBet;
								newDetailJson.totalWinningBonus = info[Obj[i]].totalWinningBonus;
								break;
							} else {
								newDetailJson.date = daysAgoList[d];
								newDetailJson.totalBetAmount = 0;
								newDetailJson.totalFanDen = 0;
								newDetailJson.totalNoOfBet = 0;
								newDetailJson.totalWinningBonus = 0;
							}
						}
					}
				}
			}
			newInfoJson.push(newDetailJson);
		}
	}
	if (newInfoJson.length == 1) {
		if (Object.keys(newInfoJson[0]).length == 0) {
			newDetailJson.date = daysAgoList[0];
			newDetailJson.totalBetAmount = 0;
			newDetailJson.totalFanDen = 0;
			newDetailJson.totalNoOfBet = 0;
			newDetailJson.totalWinningBonus = 0;
			newInfoJson.push(newDetailJson);
		}
	}
	return newInfoJson;
}
function refreshMemberSearchArea(type) {
	var today = new Date();
	var nowDate = new Date();

	nowDate.setDate(nowDate.getDate() - 1);

	refreshMemberLocalList();
	innertTableTitle(type);

	getEle('dayRecordTopBtn').classList.remove('active');
	getEle('weekRecordTopBtn').classList.remove('active');
	getEle('mounthRecordTopBtn').classList.remove('active');
	getEle('memberDayTopBtn').classList.remove('active');
	getEle('memberDailyTopBtn').classList.remove('active');

	getEle('memberRecordText').value = '';
	getEle('memberRecordTextLi').style.display = 'none';

	if (type == LOTTERY_MEMBER_DAY_RECORD_JS) {
		getEle('memberRecordStartTime').value = today.getFromFormat("yyyy/MM/dd") + ' 00:00:00';
		;
		getEle('memberRecordEndTime').value = today.getFromFormat("yyyy/MM/dd") + ' 23:59:59';
		;
	} else {
		getEle('memberRecordStartTime').value = nowDate.getFromFormat("yyyy/MM/dd") + ' 00:00:00';
		;
		getEle('memberRecordEndTime').value = nowDate.getFromFormat("yyyy/MM/dd") + ' 23:59:59';
		;
	}
}
function refreshMemberLocalList() {
	var localList = JSON.parse(getEle('LotteryLocalList').value);

	removeAllOption('memberRecordLocalList');
	addOptionNoDup('memberRecordLocalList', '所有彩種', 0);
	for (var i = 0; i < localList.length; i++) {
		addOptionNoDup('memberRecordLocalList', localList[i].title, localList[i].id);
	}
}
function memberRecordSearch(type) {
	var accName = getEle('memberRecordText').value;
	var localId = getEle('memberRecordLocalList').value;
	if (type == LOTTERY_DAY_RECORD_JS) {
		var dateList = getDaysAgoList();
		var endTime = dateList[0];
		var startTime = dateList[parseInt(dateList.length) - 1];
	} else {
		var startTime = getEle('memberRecordStartTime').value;
		var endTime = getEle('memberRecordEndTime').value;
	}
	var str = '';

	if (type == LOTTERY_MEMBER_DAY_RECORD_JS) {
		str = '&accName=' + accName + '&localId=' + localId + '&startTime=' + startTime + '&endTime=' + endTime + '&type=' + type;
		searchMemDayRecordAjax(str);
	} else if (type == LOTTERY_MEMBER_DAILY_RECORD_JS) {
		str = '&accName=' + accName + '&localId=' + localId + '&startTime=' + startTime + '&endTime=' + endTime + '&type=' + type;
		searchMemDayRecordAjax(str);
	} else {
		str = '&localId=' + localId + '&startTime=' + startTime + '&endTime=' + endTime + '&type=' + type;
		searchMemberRecordByDateAjax(str);
	}
}
function getWeekAgo(date) {
	var nowDate = new Date(date);
	var nowDayOfWeek = '';
	var nowDay = '';
	var nowMounth = '';
	var nowYear = '';
	var start = '';
	var end = '';

	var weekAgo = [];

	nowDayOfWeek = nowDate.getDay();
	nowDay = nowDate.getDate();
	nowMounth = nowDate.getMonth();
	nowYear = nowDate.getYear();
	nowYear += (nowYear < 2000) ? 1900 : 0;

	start = new Date(nowYear, nowMounth, nowDay - nowDayOfWeek - 6);
	end = new Date(nowYear, nowMounth, nowDay - nowDayOfWeek);
	weekAgo.push(start.getFromFormat("yyyy/MM/dd") + '~' + end.getFromFormat("yyyy/MM/dd"));
	return weekAgo;
}
// 以下開始Ajax
function getMemberRecordLocalListAjax() {
	var str = '';
	LOTTERY_MEMBER_RECORD_XHR = checkXHR(LOTTERY_MEMBER_RECORD_XHR);
	if (typeof LOTTERY_MEMBER_RECORD_XHR != "undefined" && LOTTERY_MEMBER_RECORD_XHR != null) {
		str = "tokenId=" + encodeURIComponent(getEle("tokenId").value);
		LOTTERY_MEMBER_RECORD_XHR.timeout = 10000;
		LOTTERY_MEMBER_RECORD_XHR.ontimeout = function() {
			disableLoadingPage();
			LOTTERY_MEMBER_RECORD_XHR.abort();
		}
		LOTTERY_MEMBER_RECORD_XHR.onerror = function() {
			disableLoadingPage();
			LOTTERY_MEMBER_RECORD_XHR.abort();
		}
		LOTTERY_MEMBER_RECORD_XHR.onreadystatechange = handleGetMemberRecordLocalListAjax;
		LOTTERY_MEMBER_RECORD_XHR.open("POST", "./LotteryMemberRecord!getLotteryLocalList.php?date=" + getNewTime(), true);
		LOTTERY_MEMBER_RECORD_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LOTTERY_MEMBER_RECORD_XHR.send(str);
		enableLoadingPage();
	}
}
function handleGetMemberRecordLocalListAjax() {
	if (LOTTERY_MEMBER_RECORD_XHR.readyState == 4) {
		if (LOTTERY_MEMBER_RECORD_XHR.status == 200) {
			try {
				if (isJSON(LOTTERY_MEMBER_RECORD_XHR.responseText)) {
					var json = JSON.parse(LOTTERY_MEMBER_RECORD_XHR.responseText);
					if (!isNull(json.LotteryLocalList)) {
						getEle('LotteryLocalList').value = JSON.stringify(json.LotteryLocalList);
					}
				}
			} catch (error) {
				console_Log("handleGetMemberRecordLocalListAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LOTTERY_MEMBER_RECORD_XHR.abort();
				memberRecordTopBtn(LOTTERY_DAY_RECORD_JS);
			}
		} else {
			disableLoadingPage();
			LOTTERY_MEMBER_RECORD_XHR.abort();
		}
	}
}
function searchMemDayRecordAjax(str) {
	var tmpStr = '';
	LOTTERY_MEMBER_RECORD_XHR = checkXHR(LOTTERY_MEMBER_RECORD_XHR);
	if (typeof LOTTERY_MEMBER_RECORD_XHR != "undefined" && LOTTERY_MEMBER_RECORD_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + str;
		LOTTERY_MEMBER_RECORD_XHR.timeout = 10000;
		LOTTERY_MEMBER_RECORD_XHR.ontimeout = function() {
			disableLoadingPage();
			LOTTERY_MEMBER_RECORD_XHR.abort();
		}
		LOTTERY_MEMBER_RECORD_XHR.onerror = function() {
			disableLoadingPage();
			LOTTERY_MEMBER_RECORD_XHR.abort();
		}
		LOTTERY_MEMBER_RECORD_XHR.onreadystatechange = handleSearchMemDayRecordAjax;
		LOTTERY_MEMBER_RECORD_XHR.open("POST", "./LotteryMemberRecord!searchMemDayRecord.php?date=" + getNewTime(), true);
		LOTTERY_MEMBER_RECORD_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LOTTERY_MEMBER_RECORD_XHR.send(tmpStr);
		enableLoadingPage();
	}
}
function handleSearchMemDayRecordAjax() {
	if (LOTTERY_MEMBER_RECORD_XHR.readyState == 4) {
		if (LOTTERY_MEMBER_RECORD_XHR.status == 200) {
			try {
				if (isJSON(LOTTERY_MEMBER_RECORD_XHR.responseText)) {
					var json = JSON.parse(LOTTERY_MEMBER_RECORD_XHR.responseText);
					if (!isNull(json.searchMemDayRecord)) {
						getEle('LotteryMemberRecordInfo').value = JSON.stringify(json.searchMemDayRecord);
						showMemDayRecord();
					}
				}
			} catch (error) {
				console_Log("handleSearchMemDayRecordAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LOTTERY_MEMBER_RECORD_XHR.abort();
			}
		} else {
			disableLoadingPage();
			LOTTERY_MEMBER_RECORD_XHR.abort();
		}
	}
}
function searchMemberRecordByDateAjax(str) {
	var tmpStr = '';
	LOTTERY_MEMBER_RECORD_XHR = checkXHR(LOTTERY_MEMBER_RECORD_XHR);
	if (typeof LOTTERY_MEMBER_RECORD_XHR != "undefined" && LOTTERY_MEMBER_RECORD_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + str;
		LOTTERY_MEMBER_RECORD_XHR.timeout = 10000;
		LOTTERY_MEMBER_RECORD_XHR.ontimeout = function() {
			disableLoadingPage();
			LOTTERY_MEMBER_RECORD_XHR.abort();
		}
		LOTTERY_MEMBER_RECORD_XHR.onerror = function() {
			disableLoadingPage();
			LOTTERY_MEMBER_RECORD_XHR.abort();
		}
		LOTTERY_MEMBER_RECORD_XHR.onreadystatechange = handleSearchMemberRecordByDateAjax;
		LOTTERY_MEMBER_RECORD_XHR.open("POST", "./LotteryMemberRecord!searchMemberRecordByDate.php?date=" + getNewTime(), true);
		LOTTERY_MEMBER_RECORD_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LOTTERY_MEMBER_RECORD_XHR.send(tmpStr);
		enableLoadingPage();
	}
}
function handleSearchMemberRecordByDateAjax() {
	if (LOTTERY_MEMBER_RECORD_XHR.readyState == 4) {
		if (LOTTERY_MEMBER_RECORD_XHR.status == 200) {
			try {
				if (isJSON(LOTTERY_MEMBER_RECORD_XHR.responseText)) {
					var json = JSON.parse(LOTTERY_MEMBER_RECORD_XHR.responseText);
					if (!isNull(json.searchMemberRecordByDate) && !isNull(json.dateType) && !isNull(json.localId)) {
						getEle('LotteryMemberRecordInfo').value = JSON.stringify(json.searchMemberRecordByDate);
						if (json.dateType == LOTTERY_DAY_RECORD_JS) {
							showMemRecordByDay(json.localId);
						} else {
							showMemRecordByDate(json.dateType, json.localId);
						}
					}
				}
			} catch (error) {
				console_Log("handleSearchMemberRecordByDateAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LOTTERY_MEMBER_RECORD_XHR.abort();
			}
		} else {
			disableLoadingPage();
			LOTTERY_MEMBER_RECORD_XHR.abort();
		}
	}
}
// 抓7天工具
function getDaysAgoList() {
	var nowDate = new Date();
	var maxDate = new Date();
	var dayAgo = [];
	var startDate = new Date(getEle('memberRecordStartTime').value);
	var endDate = new Date(getEle('memberRecordEndTime').value);

	maxDate.setDate(nowDate.getDate() - 1);

	if (endDate <= maxDate) {
		endDate = new Date(getEle('memberRecordEndTime').value);
	} else {
		endDate = maxDate;
	}

	nowDate.setDate(nowDate.getDate() - 7);
	var minDate = nowDate;

	for (var i = 1; startDate < endDate; endDate.setDate(endDate.getDate() - 1)) {
		if (endDate >= minDate) {
			dayAgo.push(endDate.getFromFormat('yyyy/MM/dd'));
		}
	}
	return dayAgo;
}
