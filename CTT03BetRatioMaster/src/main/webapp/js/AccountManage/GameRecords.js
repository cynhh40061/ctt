const GAME_RECORDS_JS = true;
var XHR_GameRecords = null;
const GAME_TYPE_JS = {0:"--",1:"快速戰",3:"三戰兩勝",5:"五戰三勝"};
const PUNCH_TYPE_JS = {123:"布",125:"剪",127:"石"};

function tableHTML (obj = []){
	var strHTML = '<tr>';
	try{
		for(var i = 0;i < obj.length;i++){
			strHTML = strHTML.concat('<td>');
			strHTML = strHTML.concat(obj[i]);
			strHTML = strHTML.concat('</td>');
		}
		strHTML = strHTML.concat('</tr>');
	}catch(e){
		console_Log("tableHTML error:"+e.message);
	}
	return strHTML;
}

function showGameRecordsPage(){
	var str = [];
	var now = new Date();
	var count = getEle("gameRecordsCount").value;
	
	str.push('<ul class="Div2-7-searcharea"> \n');
	str.push('<li> \n');
	str.push('<span>遊戲名稱：</span> \n');
	str.push('<select id = "gameName"> \n');
	str.push('<option value = "0">請選擇</option> \n');
	str.push('<option value = "1">三國猜拳王</option> \n');
	str.push('<option value = "2">Game2</option> \n');
	str.push('<option value = "3">Game3</option> \n');
	str.push('</select> \n');
	str.push('</li> \n');
	str.push('<li> \n');
	str.push('<span>帳號：</span> \n');
	str.push('<input type="text" id="accName" onkeyup="checkAccName(this.value);"> \n');
	str.push('</li> \n');
	str.push('<li> \n');
	str.push('<span>局號：</span> \n');
	str.push('<input type="text" id="gameId" onkeyup="checkGameId(this.value);"> \n');
	str.push('</li> \n');
	str.push('<li> \n');
	str.push('<span>時間：</span> \n');
	str.push('<input type="text" class="margin-fix-2" id="gameRecordsStartTime" onclick="newCalendar(this, this.id, 1)" readonly> \n');
	str.push('- \n');
	str.push('<input type="text" class="margin-fix-2" id="gameRecordsEndTime" onclick="newCalendar(this, this.id, 2)" readonly> \n');
	str.push('</li> \n');
	str.push('<li> \n');
	str.push('<span>遊戲類型：</span> \n');
	str.push('<select id="punchGameType"> \n');
	str.push('<option value = "0">請選擇</option> \n');
	str.push('<option value = "1">快速戰</option> \n');
	str.push('<option value = "2">三戰兩勝</option> \n');
	str.push('<option value = "3">五戰三勝</option> \n');
	str.push('</select> \n');
	str.push('</li> \n');
	str.push('<li> \n');
	str.push('<button onclick = "confromSearchGmaeRecords();">搜尋</button> \n');
	str.push('<button onclick = "reSetSearch();">重置</button> \n');
	str.push('</li> \n');
	str.push('</ul> \n');
	str.push('<div id = "gameRecordsSearchTime"></div>');
	str.push('<div class="Div2-7-tablearea"> \n');
	str.push('<table class="table-zebra tr-hover">');
	str.push('<tbody id = "gameRecordstable">');
	str.push('<tr><th>遊戲名稱</th><th>帳號</th><th>局號</th><th>遊戲類型</th><th>開始時間</th><th>結束時間</th><th>開始金額</th><th>結束金額</th><th>投注金額</th><th>贏分</th><th>淨分</th><th>手續費</th><th>總局數</th><th>詳細</th></tr>');
	str.push('</tbody>');
	str.push('</table> \n');
	str.push('<p class="media-control text-center"> \n');
	str.push('<span>一頁 \n');
	str.push('<select id="searchCount" onchange="countChange(this.value);"><option value = "25">25筆</option><option value = "50">50筆</option><option value = "75">75筆</option><option value = "100">100筆</option></select>\n');
	str.push('</span>  \n');
	str.push(' <a href="#" onclick="firstGameRecordsPage('+count+');" class="backward"></a>');
	str.push('<a href="#" onclick="previousGameRecordsPage('+count+');" class="backward-fast"></a>');
	str.push('<span>總頁數：<i id="gameRecordsNowPage">1</i><span>/</span><i id="gameRecordsTotlePage">1</i>頁</span>');
	str.push('<a href="#" onclick="nextGameRecordsPage('+count+');" class="forward"></a>');
	str.push('<a href="#" onclick="lastGameRecordsPage('+count+');" class="forward-fast"></a>');
	str.push('</p>');
	str.push('</div>');
	
	getEle("mainContain").innerHTML =  str.join("");
	getEle("gameRecordsStartTime").value = now.getFromFormat("yyyy-MM-dd");
	getEle("gameRecordsEndTime").value = now.getFromFormat("yyyy-MM-dd");
}
function reSetSearch(){
	var now = new Date();
	getEle("gameName").value = 0;
	getEle("accName").value = '';
	getEle("gameId").value = '';
	getEle("gameRecordsStartTime").value = now.getFromFormat("yyyy-MM-dd");
	getEle("gameRecordsEndTime").value = now.getFromFormat("yyyy-MM-dd");
	getEle("startTime").value = ' 00:00:00';
	getEle("endTime").value = ' 23:59:59';
	getEle("punchGameType").value = 0;
}

function checkAccName(){
	getEle("accName").value = checkInputTextVal(getEle("accName").value);
}
function checkGameId(){
	getEle("gameId").value = checkInputNumberVal(getEle("gameId").value);
}
function countChange(){
	getEle("gameRecordsCount").value = getEle("searchCount").value
}

function confromSearchGmaeRecords(pageInfo){
	var gameName = 0;
	var gameId = 0;
	var accName = '';
	var startTime = '';
	var endTime = '';
	var gamePuncheType = 0;
	var count = getEle("gameRecordsCount").value;
	var str = '';
	if(isNull(pageInfo)){
		pageInfo = '';
	}
	try{
		if(!isNull(getEle("gameName").value)){
			gameName = getEle("gameName").value > 0 ? getEle("gameName").value : 0;
		}
		if(!isNull(getEle("gameId").value)){
			gameId = getEle("gameId").value > 0 ? getEle("gameId").value : 0;
		}
		if(!isNull(getEle("accName").value)){
			accName = getEle("accName").value;
		}
		if(!isNull(getEle("gameRecordsStartTime").value)){
			startTime = getEle("gameRecordsStartTime").value == '' ? now.getFromFormat("yyyy-MM-dd")+' 00:00:00' : getEle("gameRecordsStartTime").value;
		}
		if(!isNull(getEle("gameRecordsEndTime").value)){
			endTime = getEle("gameRecordsEndTime").value == '' ? now.getFromFormat("yyyy-MM-dd")+' 23:59:59' : getEle("gameRecordsEndTime").value;
		}
		if(!isNull(getEle("punchGameType").value)){
			gameType = getEle("punchGameType").value > 0 ? getEle("punchGameType").value : 0;
		}
		str = 'tokenId='+encodeURIComponent(getEle("tokenId").value)+'&gameName='+gameName+'&gameId='+gameId+'&accName='+accName+'&startTime='+startTime+'&endTime='+endTime+'&gameType='+gameType+"&count="+count+pageInfo;
		getEle("gameRecordsSearchInfo").value = str;
		if(comparStartEndTime(startTime,endTime)){
			searchGameRecordsAjax(str);
		}
	}catch(error){
		console_Log('confromSearchGmaeRecords error'+error.message);
	}finally{
		delete gameName ;
		delete gameId ;
		delete accName ;
		delete startTime ;
		delete endTime ;
		delete gamePuncheType ;
		delete str;
	}
}

function searchGameRecordsAjax(str){
	XHR_GameRecords = checkXHR(XHR_GameRecords);
	if (typeof XHR_GameRecords != "undefined" && XHR_GameRecords != null) {
		XHR_GameRecords.timeout = 10000;
		XHR_GameRecords.ontimeout = function() {
			disableLoadingPage();XHR_GameRecords.abort();
		}
		XHR_GameRecords.onerror = function() {
			disableLoadingPage();XHR_GameRecords.abort();
		}
		XHR_GameRecords.onreadystatechange = handlesearchGameRecordsAjax;
		XHR_GameRecords.open("POST", "./GameRecords!searchRecords.php?date="
				+ getNewTime(), true);
		XHR_GameRecords.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR_GameRecords.send(str);
		enableLoadingPage();
	}
}
function handlesearchGameRecordsAjax(){
	if (XHR_GameRecords.readyState == 4) {
		if (XHR_GameRecords.status == 200) {
			try {
				if (isJSON(XHR_GameRecords.responseText)) {
					var json=JSON.parse(XHR_GameRecords.responseText);
					if(checkTokenIdfail(json)){
						getEle("gameRecordsData").value = XHR_GameRecords.responseText;
						if(!isNull(json.searchTime)){
							getEle("gameRecordsSearchTime").innerHTML = json.searchTime;
						}
						showGameRecordsTable();
						if(typeof json.gameRecordsPage != "undefined" && json.gameRecordsPage != null){
							getEle("gameRecordsPage").value = json.gameRecordsPage;
							if(getEle("gameRecordsNowPage") != null && getEle("gameRecordsNowPage") != undefined){
								getEle("gameRecordsNowPage").innerHTML = json.gameRecordsPage;
							}
						}
						if(typeof json.gameRecordsLastPage != "undefined" && json.gameRecordsLastPage != null){
							getEle("gameRecordsLastPage").value = json.gameRecordsLastPage;
							if(getEle("gameRecordsTotlePage") != null && getEle("gameRecordsTotlePage") != undefined){
								getEle("gameRecordsTotlePage").innerHTML = json.gameRecordsLastPage;
							}
						}
					}
				}
			} catch (error) {
				console_Log("handlesearchGameRecordsAjax error:"+error.message);
			} finally {
				disableLoadingPage();
				XHR_GameRecords.abort();
			}
		} else {
			disableLoadingPage();
			XHR_GameRecords.abort();
		}
	}
}

function showGameRecordsTable(){
	var json = '';
	var josnObject = '';
	var showInfoTable = '<tr><th>遊戲名稱</th><th>帳號</th><th>局號</th><th>遊戲類型</th><th>開始時間</th><th>結束時間</th><th>開始金額</th><th>結束金額</th><th>投注金額</th><th>贏分</th><th>淨分</th><th>手續費</th><th>總局數</th><th>詳細</th></tr>';
	
	var accId = '';
	var gameName = '';
	var accName = '';
	var gameId = '';
	var gameType = '';
	var startTime = '';
	var endTime = '';
	var startBalance = '';
	var endBalance = '';
	var bet = 0;
	var winGoal = 0;
	var netAmount = 0;
	var fees = 0;
	var gameTimes = '';
	var gameProcess = '';
	
	if(isJSON(getEle("gameRecordsData").value) && !isNull(getEle("gameRecordsData").value)){
		json = JSON.parse(getEle("gameRecordsData").value);
		if(!isNull(json.punchGameRecords)){
			josnObject = json.punchGameRecords;
		}
	}
	
	for (var i = 0; i < Object.keys(josnObject).length; i++) {
		if(!isNull(josnObject[i].accId)){
			accId = josnObject[i].accId;
		}
		if(!isNull(josnObject[i].gameName)){
			gameName = josnObject[i].gameName;
		}
		if(!isNull(josnObject[i].accName)){
			accName = josnObject[i].accName;
		}
		if(!isNull(josnObject[i].gameId)){
			gameId = josnObject[i].gameId;
		}
		if(!isNull(josnObject[i].gameType)){
			gameType = josnObject[i].gameType;
		}
		if(!isNull(josnObject[i].startTime)){
			startTime = josnObject[i].startTime.split('.')[0];
		}
		if(!isNull(josnObject[i].endTime)){
			endTime = josnObject[i].endTime.split('.')[0];
		}
		if(!isNull(josnObject[i].startBalance)){
			startBalance = josnObject[i].startBalance;
		}
		if(!isNull(josnObject[i].endBalance)){
			endBalance = josnObject[i].endBalance;
		}
		if(!isNull(josnObject[i].bet)){
			bet = josnObject[i].bet;
		}
		if(!isNull(josnObject[i].winGoal)){
			winGoal = josnObject[i].winGoal;
		}
		if(!isNull(josnObject[i].netAmount)){
			netAmount = josnObject[i].netAmount;
		}
		if(!isNull(josnObject[i].fees) && !isNull(josnObject[i].winGoal) && josnObject[i].winGoal > 0){
			fees = josnObject[i].fees;
		}else{
			fees = 0;
		}
		if(!isNull(josnObject[i].gameTimes)){
			gameTimes = josnObject[i].gameTimes;
		}
		if(!isNull(strToJson(josnObject[i].gameProcess))){
			gameProcess = josnObject[i].gameProcess;
		}
		showInfoTable += tableHTML([gameName,accName,gameId,GAME_TYPE_JS[gameType],startTime,endTime,startBalance,endBalance,bet,winGoal,netAmount,fees,gameTimes,'<button onclick="showGameRecordsDetailModalV2('+i+');">查看</button>\n']);
	}
	getEle("gameRecordstable").innerHTML = showInfoTable;
}
function showGameRecordsDetailModalV2(i){
	var json = '';
	var jsonObject = '';
	var gameId = '';
	var gameProcess = '';
	var p1Punch = '';
	var p2Punch = '';
	var winner = '';
	var str = [];
	var data = '';
	try{
		if(isJSON(getEle("gameRecordsData").value) && !isNull(getEle("gameRecordsData").value)){
			json = JSON.parse(getEle("gameRecordsData").value);
			josnObject = json.punchGameRecords;
		}
		if(!isNull(josnObject[i].gameId)){
			gameId = josnObject[i].gameId;
		}
		if(!isNull(strToJson(josnObject[i].gameProcess))){
			gameProcess = josnObject[i].gameProcess;
		}
		str[0]='<div class="modal-content width-percent-20">';
		str[1]='<span class="close" onclick="onClickCloseModalV2();">×</span>';
		str[2]='<h3>猜拳勝負場次</h3>';
		str[3]='<div class="tab-content">';
		str[4]='<table class="table-zebra tr-hover">';
		str[5]='<tbody>';
		str[6]= '';
		str[7]='</tbody>';
		str[8]='</table>';
		str[9]='</div>';
		str[10]='</div>';
			data = tableHTML(['局號:'+gameId,'贏家','拳種'])
			for(var i = 0; i < gameProcess.length; i++){
				if(!isNull(gameProcess[i].p1)){
					p1Punch = gameProcess[i].p1;
				}
				if(!isNull(gameProcess[i].p2)){
					p2Punch = gameProcess[i].p2;
				}
				if(!isNull(gameProcess[i].winName)){
					winner = gameProcess[i].winName;
				}
				data += tableHTML([(i+1),winner,PUNCH_TYPE_JS[p1Punch]+'/'+PUNCH_TYPE_JS[p2Punch]])
			}
			str[6] = data;
	}catch(error){
		data = '';
		console_Log("showGameRecordsDetailModalV2 error="+error.message);
	}finally{
		delete json;
		delete josnObject;
		delete detail;
		delete detailKey;
		delete addOrderKeys;
		delete action;
		if(data.length > 0){
			showHtml = str.join('');
			onClickOpenModalV2(showHtml);
		}
	}
}
function nextGameRecordsPage(count){
	var nextPageNum = parseInt(getEle("gameRecordsPage").value)+1;
	if(nextPageNum <= parseInt(getEle("gameRecordsLastPage").value)){
		page = nextPageNum;
	}
	else{
		page = getEle("gameRecordsLastPage").value;
	}
	var pageInfo = "&count="+count+"&nextPage="+page;
	confromSearchGmaeRecords(pageInfo);
}
function lastGameRecordsPage(count){
	var maxPage = getEle("gameRecordsLastPage").value;
	var pageInfo = "&count="+count+"&nextPage="+maxPage;
	confromSearchGmaeRecords(pageInfo);
}

function previousGameRecordsPage(count){
	var previousPage = parseInt(getEle("gameRecordsPage").value)-1;
	var page = "1"
	if(previousPage > 0){
		page = previousPage;
	}
	var pageInfo = "&count="+count+"&nextPage="+page;
	confromSearchGmaeRecords(pageInfo);
}

function firstGameRecordsPage(count){
	var pageInfo = "&count="+count+"&nextPage=1";
	confromSearchGmaeRecords(pageInfo);
}
function comparStartEndTime(start,end){
	var nowDate = new Date();
	var MonthAgoDate = new Date(nowDate.getTime()-(60*60*24*30*1000)).getFromFormat('yyyy/MM/dd hh:mm:ss');
	var formattedDate = nowDate.getFromFormat('yyyy/MM/dd hh:mm:ss');
	var startToTimeTormat = "";
	var endToTimeTormat = "";
	if(start != null && start!= undefined && start != ""){
		startToTimeTormat = new Date(start).getFromFormat('yyyy/MM/dd hh:mm:ss');
	}
	
	if(end != null && end!= undefined && end != ""){
		endToTimeTormat = new Date(end).getFromFormat('yyyy/MM/dd hh:mm:ss');
	}
	
	if(new Date(startToTimeTormat).getTime() > new Date(endToTimeTormat).getTime()){
		return false;
	}
	else{
		return true;
	}
	
}