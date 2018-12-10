var LOTTERY_RECORD_XHR = null;

const LOTTERY_RECORD_JS = true;

var LotteryRecordType = [ "所有流水紀錄", "投注扣款", "追號扣款", "撤單還款", "獎金派發", "返點", "活動禮金", "錄號錯誤", "8", "9" ];

function LotteryRecord() {
	LotteryRecord_into();
	getRecordLocalListAjax();
}

function LotteryRecord_into() {
	var str = '';
	if (document.getElementsByName("lotteryRecordInfo").length != 1) {
		str += '\n<input type="hidden" name="lotteryLocalList" value="">';
		str += '\n<input type="hidden" name="lotteryRecordInfo" value="">';

		str += '\n<input type="hidden" name="lotteryRecordCount" value="25">';
		str += '\n<input type="hidden" name="lotteryRecordPage" value="1">';
		str += '\n<input type="hidden" name="lotteryRecordLastPage" value="1">';
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
function getRecordLocalListAjax() {
	var str = '';
	LOTTERY_RECORD_XHR = checkXHR(LOTTERY_RECORD_XHR);
	if (typeof LOTTERY_RECORD_XHR != "undefined" && LOTTERY_RECORD_XHR != null) {
		str = "tokenId=" + encodeURIComponent(getEle("tokenId").value);
		LOTTERY_RECORD_XHR.timeout = 10000;
		LOTTERY_RECORD_XHR.ontimeout = function() {
			disableLoadingPage();
			LOTTERY_RECORD_XHR.abort();
		}
		LOTTERY_RECORD_XHR.onerror = function() {
			disableLoadingPage();
			LOTTERY_RECORD_XHR.abort();
		}
		LOTTERY_RECORD_XHR.onreadystatechange = handleGetRecordLocalListAjax;
		LOTTERY_RECORD_XHR.open("POST", "./LotteryRecord!getLotteryLocalList.php?date=" + getNewTime(), true);
		LOTTERY_RECORD_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LOTTERY_RECORD_XHR.send(str);
		enableLoadingPage();
	}
}
function handleGetRecordLocalListAjax() {
	if (LOTTERY_RECORD_XHR.readyState == 4) {
		if (LOTTERY_RECORD_XHR.status == 200) {
			try {
				if (isJSON(LOTTERY_RECORD_XHR.responseText)) {
					getEle('lotteryLocalList').value = LOTTERY_RECORD_XHR.responseText;
					LotteryRecordSearchArea();
					LotteryRecordMainContain();
					refreshSearchArea();
				}
			} catch (error) {
				console_Log("handleGetRecordLocalListAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LOTTERY_RECORD_XHR.abort();
			}
		} else {
			disableLoadingPage();
			LOTTERY_RECORD_XHR.abort();
		}
	}
}
function LotteryRecordSearchArea() {
	var searchAreaStr = [];

	searchAreaStr.push('<div class="contentDiv2-15">');
	searchAreaStr.push('	<br>');
	searchAreaStr.push('	<ul>');
	searchAreaStr.push('		<li>帳號：<input type="text" name="memberAccName" onkeyup="checkLotteryRecordImputMemberAccName();" maxlength="20"></li>');
	searchAreaStr.push('		<li>類型：<select name="lotteryRecordType"></select></li>');
	searchAreaStr.push('		<li><input type="checkbox" id="typeCheckBox">非遊戲類賬變</li> \n');
	searchAreaStr.push('		<li>彩種：<select name="lotteryLocal"></select></li>');
	searchAreaStr.push('		<li class="time">時間：');
	searchAreaStr.push('			<input type="text" id="startTime" onclick="newCalendar(this,this.id,1);" readonly>到');
	searchAreaStr.push('			<input type="text" id="endTime" onclick="newCalendar(this,this.id,2);" readonly>');
	searchAreaStr.push('		</li>');
	searchAreaStr.push('		<li><button onclick="conformLotteryRecord();">查詢</button></li> \n');
	searchAreaStr
			.push('		<li><a href="javascript:void(0);" onclick="chkTableToExcel(\'lotteryRecordTable\',\'lotteryRecord\')">導出EXCEL</a></li>');
	searchAreaStr.push('	</ul>');
	searchAreaStr.push('</div>');

	searchAreaStr.join('');
	getEle('searchArea').innerHTML = searchAreaStr.join('');
}
function LotteryRecordMainContain() {
	var count = getEle("lotteryRecordCount").value;
	var mainContainStr = [];

	mainContainStr.push('<table id="lotteryRecordTable" class="width-percent-960 tr-hover">');
	mainContainStr.push('	<tbody>');
	mainContainStr.push('		<tr><th>帳號</th><th>時間</th><th>賬變類型</th><th>單號</th><th>彩種</th><th>玩法</th><th>期號</th><th>期數</th><th>金額</th><th>賬上餘額</th></tr>');
	mainContainStr.push('	</tbody>');
	mainContainStr.push('</table>');
	mainContainStr.push('<p class="media-control text-center">');
	mainContainStr.push('	<span>一頁');
	mainContainStr.push('		<select id="setLotteryRecordCount" onchange="setLotteryRecordCountChange(this.value);">');
	mainContainStr.push('			<option value="25">25筆</option>');
	mainContainStr.push('			<option value="50">50筆</option>');
	mainContainStr.push('			<option value="75">75筆</option>');
	mainContainStr.push('			<option value="100">100筆</option>');
	mainContainStr.push('		</select>');
	mainContainStr.push('	</span>');
	mainContainStr.push('	<a href="javascript:void(0);" onclick="firstLotteryRecordPage();" class="backward"></a>');
	mainContainStr.push('	<a href="javascript:void(0);" onclick="previousLotteryRecordPage();" class="backward-fast"></a>');
	mainContainStr.push('	<span>總頁數：<i id="lotteryRecordNowPage">1</i></span>');
	mainContainStr.push('	<span>/</span>');
	mainContainStr.push('		<i id="lotteryRecordTotlePage">1</i>頁');
	mainContainStr.push('	</span>');
	mainContainStr.push('	<a href="javascript:void(0);" onclick="nextLotteryRecordPage();" class="forward"></a>');
	mainContainStr.push('	<a href="javascript:void(0);" onclick="lastLotteryRecordPage();" class="forward-fast"></a>');
	mainContainStr.push('</p>');

	mainContainStr.join('');
	getEle('mainContain').innerHTML = mainContainStr.join('');
}
function showRecordTable() {
	var json = JSON.parse(getEle('lotteryRecordInfo').value);
	var recordInfo = '';
	var recordTableStr = [];

	if (!isNull(json.recordInfo)) {
		recordInfo = json.recordInfo;
	}

	recordTableStr.push('<tr><th>帳號</th><th>時間</th><th>賬變類型</th><th>單號</th><th>彩種</th><th>玩法</th><th>期號</th><th>期數</th><th>金額</th><th>賬上餘額</th></tr>');
	for (var i = 0; i < recordInfo.length; i++) {
		if (!isNull(recordInfo[i])) {
			recordTableStr.push('<tr>');
			recordTableStr.push('	<td>' + checkNull(recordInfo[i].accName) + '</td>');
			recordTableStr.push('	<td>' + checkNull(recordInfo[i].createTime) + '</td>');
			recordTableStr.push('	<td>' + checkNull(LotteryRecordType[recordInfo[i].recordOrderType]) + '</td>');
			recordTableStr.push('	<td>' + checkNull(recordInfo[i].mainOrderId) + '</td>');
			recordTableStr.push('	<td>' + checkNull(recordInfo[i].localName) + '</td>');
			recordTableStr.push('	<td>' + checkNull(recordInfo[i].playedName) + '</td>');
			if (!isNull(recordInfo[i].startPeriodNum) && !isNull(recordInfo[i].stopPeriodNum)
					&& recordInfo[i].startPeriodNum != recordInfo[i].stopPeriodNum) {
				recordTableStr.push('	<td>' + checkNull(recordInfo[i].startPeriodNum) + '-');
				recordTableStr.push(checkNull(recordInfo[i].stopPeriodNum) + '</td>');
			} else {
				recordTableStr.push('	<td>' + checkNull(recordInfo[i].startPeriodNum) + '</td>');
			}
			recordTableStr.push('	<td>' + checkNull(recordInfo[i].periodTotalCount) + '</td>');
			if (!isNull(recordInfo[i].recordOrderType)) {
				if (recordInfo[i].recordOrderType == 1 || recordInfo[i].recordOrderType == 2 || recordInfo[i].recordOrderType == 7) {
					recordTableStr.push('	<td class="red">-' + checkNull(recordInfo[i].money) + '</td>');
				} else {
					recordTableStr.push('	<td>' + checkNull(recordInfo[i].money) + '</td>');
				}
			} else {
				recordTableStr.push('	<td>--</td>');
			}
			recordTableStr.push('	<td>' + checkNull(recordInfo[i].balance) + '</td>');
			recordTableStr.push('</tr>');
		}
	}

	getEle('lotteryRecordTable').innerHTML = recordTableStr.join('');
}
function refreshSearchArea() {
	clearSearchArea();
	var lotteryLocalList = JSON.parse(getEle('lotteryLocalList').value);
	getEle('startTime').value = new Date().getFromFormat("yyyy/MM/dd") + ' 00:00:00';
	getEle('endTime').value = new Date().getFromFormat("yyyy/MM/dd") + ' 23:59:59';
	if (!isNull(lotteryLocalList.LotteryLocalList)) {
		lotteryLocalList = lotteryLocalList.LotteryLocalList;
	}
	addOptionNoDup('lotteryLocal', '所有彩種', 0);
	for (var i = 0; i < lotteryLocalList.length; i++) {
		addOptionNoDup('lotteryLocal', lotteryLocalList[i].title, lotteryLocalList[i].id);
	}
	for (var j = 0; j < LotteryRecordType.length; j++) {
		addOptionNoDup('lotteryRecordType', LotteryRecordType[j], j);
	}

}
function clearSearchArea() {
	getEle('memberAccName').value = '';
	removeAllOption('lotteryRecordType');
	getEle('typeCheckBox').checked = false;
	removeAllOption('lotteryLocal');
	getEle('startTime').value = '';
	getEle('endTime').value = '';
}
function checkLotteryRecordImputMemberAccName() {
	getEle('memberAccName').value = checkAccount(getEle('memberAccName').value);
}
function conformLotteryRecord(pageInfo) {
	var memberAccName = getEle('memberAccName').value;
	var recordType = getEle('lotteryRecordType').value;
	var checkBox = getEle('typeCheckBox');
	var localId = getEle('lotteryLocal').value;
	var startTime = getEle('startTime').value;
	var endTime = getEle('endTime').value;
	var count = getEle("lotteryRecordCount").value;
	var str = '';
	if (isNull(pageInfo)) {
		pageInfo = '';
	}

	if (!isNull(memberAccName) && !isNull(recordType) && !isNull(checkBox) && !isNull(localId) && !isNull(startTime) && !isNull(endTime)) {
		str += '&memberAccName=' + memberAccName;
		str += '&recordType=' + recordType;
		str += '&checkBox=' + checkBox.checked;
		str += '&localId=' + localId;
		str += '&startTime=' + startTime;
		str += '&endTime=' + endTime;
		str += '&count=' + count;
		str += pageInfo;
	}
	if (str != '') {
		getLotteryRecordAjax(str);
	}
}
function getLotteryRecordAjax(str) {
	var tmpStr = '';
	LOTTERY_RECORD_XHR = checkXHR(LOTTERY_RECORD_XHR);
	if (typeof LOTTERY_RECORD_XHR != "undefined" && LOTTERY_RECORD_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + str;
		LOTTERY_RECORD_XHR.timeout = 10000;
		LOTTERY_RECORD_XHR.ontimeout = function() {
			disableLoadingPage();
			LOTTERY_RECORD_XHR.abort();
		}
		LOTTERY_RECORD_XHR.onerror = function() {
			disableLoadingPage();
			LOTTERY_RECORD_XHR.abort();
		}
		LOTTERY_RECORD_XHR.onreadystatechange = handleGetLotteryRecordAjax;
		LOTTERY_RECORD_XHR.open("POST", "./LotteryRecord!getLotteryRecord.php?date=" + getNewTime(), true);
		LOTTERY_RECORD_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LOTTERY_RECORD_XHR.send(tmpStr);
		enableLoadingPage();
	}
}
function handleGetLotteryRecordAjax() {
	if (LOTTERY_RECORD_XHR.readyState == 4) {
		if (LOTTERY_RECORD_XHR.status == 200) {
			try {
				if (isJSON(LOTTERY_RECORD_XHR.responseText)) {
					var json = JSON.parse(LOTTERY_RECORD_XHR.responseText);
					getEle('lotteryRecordInfo').value = LOTTERY_RECORD_XHR.responseText;
					showRecordTable();
					if (typeof json.lotteryRecordPage != "undefined" && json.lotteryRecordPage != null) {
						getEle("lotteryRecordPage").value = json.lotteryRecordPage;
						if (getEle("lotteryRecordNowPage") != null && getEle("lotteryRecordNowPage") != undefined) {
							getEle("lotteryRecordNowPage").innerHTML = json.lotteryRecordPage;
						}
					}
					if (typeof json.lotteryRecordLastPage != "undefined" && json.lotteryRecordLastPage != null) {
						getEle("lotteryRecordLastPage").value = json.lotteryRecordLastPage;
						if (getEle("lotteryRecordTotlePage") != null && getEle("lotteryRecordTotlePage") != undefined) {
							getEle("lotteryRecordTotlePage").innerHTML = json.lotteryRecordLastPage;
						}
					}
				}
			} catch (error) {
				console_Log("handleGetLotteryRecordAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LOTTERY_RECORD_XHR.abort();
			}
		} else {
			disableLoadingPage();
			LOTTERY_RECORD_XHR.abort();
		}
	}
}
// 以下換頁
function setLotteryRecordCountChange() {
	getEle("lotteryRecordCount").value = getEle("setLotteryRecordCount").value
}
function nextLotteryRecordPage() {
	var nextPageNum = parseInt(getEle("lotteryRecordPage").value) + 1;
	if (nextPageNum <= parseInt(getEle("lotteryRecordLastPage").value)) {
		page = nextPageNum;
	} else {
		page = getEle("lotteryRecordLastPage").value;
	}
	var pageInfo = "&count=" + getEle("lotteryRecordCount").value + "&nextPage=" + page;
	conformLotteryRecord(pageInfo);
}
function lastLotteryRecordPage() {
	var maxPage = getEle("lotteryRecordLastPage").value;
	var pageInfo = "&count=" + getEle("lotteryRecordCount").value + "&nextPage=" + maxPage;
	conformLotteryRecord(pageInfo);
}

function previousLotteryRecordPage() {
	var previousPage = parseInt(getEle("lotteryRecordPage").value) - 1;
	var page = "1"
	if (previousPage > 0) {
		page = previousPage;
	}
	var pageInfo = "&count=" + getEle("lotteryRecordCount").value + "&nextPage=" + page;
	conformLotteryRecord(pageInfo);
}

function firstLotteryRecordPage() {
	var pageInfo = "&count=" + getEle("lotteryRecordCount").value + "&nextPage=1";
	conformLotteryRecord(pageInfo);
}
