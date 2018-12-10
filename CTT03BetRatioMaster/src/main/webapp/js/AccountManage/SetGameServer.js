const GAME_SERVER_JS = true;
XHR_GameServer = null;
XHR_GameParam = null;

function GameServer() {
	var str = [];
	
	str[0]= '<div id="gameServerSerch">';
	str[1]= '<ul  class="Div2-7-searcharea">';
	str[2]= '</ul>';
	str[3]= '<div >';
	str[4]= '<button onclick = "showAddGameServer();">新增遊戲伺服器</button> \n';
	str[5]= '<button onclick = "confromShowGameSetLog('+LOG_ACTION_GAME_SERVER_CHANGE+');">操作紀錄</button> \n'
	str[6]= '</div>';
	str[7]= '<div id = "gameServerList"class="Div2-7-tablearea"></div> \n';

	var showHtml = '';
	showHtml = str.join('');
	getEle("searchArea").innerHTML = showHtml;
}

function showAddGameServer(){
	var str = [];
	
	str[0]='<div class="modal-content width-percent-360 margin-fix-5">';
	str[1]='<span class="close" onclick="onClickCloseModal();">×</span>';
	str[2]='<h3>新增遊戲伺服器</h3>';
	str[3]='<table class="addgameserver-table">';
	str[4]='<tr><th>遊戲伺服器Id</th><td><input type="text" class="margin-fix-2" id="gameServerId" onkeyup = "chkGameServerId();" maxlength="3"></td></tr>';
	str[5]='<tr><th>入場金額</th><td><input type="text" class="margin-fix-2" id="bet" onkeyup = "chkBet();" maxlength="6"></td></tr>';
	str[6]='<tr><th>最大遊戲人數</th><td><input type="text" class="margin-fix-2" id="maxPlayer" onkeyup = "chkMaxPlayer();" maxlength="3"></td></tr>';
	str[7]='<tr><th>局數設定</th><td><input type="text" class="margin-fix-2" id="gameTimesType" onkeyup = "chkGameTimesType();" maxlength="3"></td></tr>';
	str[8]='<tr><th>抽成比例%</th><td><input type="text" class="margin-fix-2" id="commission" onkeyup = "chkCommission();" maxlength="3"></td></tr>';
	str[9]='<tr><th>Thread</th><td><input type="text" class="margin-fix-2" id="thread" onkeyup = "chkThread();" maxlength="3"></td></tr>';
	str[10]='<tr><th>玩家心跳</th><td><input type="text" class="margin-fix-2" id="beatsTimeOut" onkeyup = "chkBeatsTimeOut();" maxlength="3"></td></tr>';
	str[11]='<tr><th>遊戲時間</th><td><input type="text" class="margin-fix-2" id="gameTimeOut" onkeyup = "chkGameTimeOut();" maxlength="3"></td></tr>';
	str[12]='<tr><th>電腦託管時間</th><td><input type="text" class="margin-fix-2" id="autoTimeStamp" onkeyup = "chkAutoTimeStamp();" maxlength="3"></td></tr>';
	str[13]='<tr><th>繼續遊戲時間</th><td><input type="text" class="margin-fix-2" id="waitContinueTimeOut" onkeyup = "chkWaitContinueTimeOut();" maxlength="3"></td></tr>';
	str[14]='<tr><th>同對手能繼續場次</th><td><input type="text" class="margin-fix-2" id="continueTimesType" onkeyup = "chkContinueTimesType();" maxlength="3"></td></tr>';
	str[15]='<tr><th>維修時間</th><td><input type="text" class="margin-fix-2" id="serviceTime" onclick="newCalendar(this, this.id,1)" readonly></td></tr>';
	str[16]='<tr><th>開關</th><td><select id="serverStatus"><option value = 1>開</option><option value = 0>關</option></td></tr>';
	str[17]='</table><div class="btn-area">';
	str[18]='<button class="btn-lg btn-orange" onclick="conformAddGameServer();">確定</button>\n<button class="btn-lg btn-gray" onclick="onClickCloseModal();">取消</button>';
	str[19]='</div>';
	
	var showHtml = '';
	showHtml = str.join('');
	onClickOpenModal(showHtml);
}
function chkGameServerId (){
	getEle("gameServerId").value = checkInputNumberVal(getEle("gameServerId").value);
}
function chkBet (){
	getEle("bet").value = checkInputNumberVal(getEle("bet").value);
}
function chkMaxPlayer (){
	getEle("maxPlayer").value = checkInputNumberVal(getEle("maxPlayer").value);
}
function chkGameTimesType (){
	getEle("gameTimesType").value = checkInputNumberVal(getEle("gameTimesType").value);
}
function chkCommission (){
	getEle("commission").value = checkInputNumberVal(getEle("commission").value);
}
function chkThread (){
	getEle("thread").value = checkInputNumberVal(getEle("thread").value);
}
function chkBeatsTimeOut (){
	getEle("beatsTimeOut").value = checkInputNumberVal(getEle("beatsTimeOut").value);
}
function chkGameTimeOut (){
	getEle("gameTimeOut").value = checkInputNumberVal(getEle("gameTimeOut").value);
}
function chkAutoTimeStamp (){
	getEle("autoTimeStamp").value = checkInputNumberVal(getEle("autoTimeStamp").value);
}
function chkWaitContinueTimeOut (){
	getEle("waitContinueTimeOut").value = checkInputNumberVal(getEle("waitContinueTimeOut").value);
}
function chkContinueTimesType (){
	getEle("continueTimesType").value = checkInputNumberVal(getEle("continueTimesType").value);
}

function conformAddGameServer(){
	var gameServerId = "" + getEle("gameServerId").value.toString();
	var bet = getEle("bet").value;
	var maxPlayer = getEle("maxPlayer").value;
	var gameTimesType = getEle("gameTimesType").value;
	var commission = getEle("commission").value;
	var thread = getEle("thread").value;
	var beatsTimeOut = getEle("beatsTimeOut").value;
	var gameTimeOut = getEle("gameTimeOut").value;
	var autoTimeStamp = getEle("autoTimeStamp").value;
	var waitContinueTimeOut = getEle("waitContinueTimeOut").value;
	var continueTimesType = getEle("continueTimesType").value;
	var serviceTime = getEle("serviceTime").value;
	var serverStatus = getEle("serverStatus").value;
	var str = [];
	var tmstr = "";
	if(gameServerId != "" && bet > 0 && maxPlayer != "" && gameTimesType != "" && 
			commission != "" && thread != "" && beatsTimeOut != "" && gameTimeOut != "" && 
			autoTimeStamp != "" && waitContinueTimeOut != "" && continueTimesType != "" &&
			serviceTime.split(" ")[0] != "" && serverStatus != ""){
		tmstr = joinVars("&",{gameServerId,bet,maxPlayer,gameTimesType,commission,thread,beatsTimeOut,gameTimeOut,autoTimeStamp,waitContinueTimeOut,continueTimesType,serviceTime,serverStatus},true);
	}else{
		onCheckModelPage2("有欄位尚未輸入");
	}
	if(tmstr != ""){
		onCheckModelPage('確定送出', 'AddGameServerAjax(\''+tmstr+'\')');
	}
}

function AddGameServerAjax(str) {
	XHR_GameServer = checkXHR(XHR_GameServer);
	if (typeof XHR_GameServer != "undefined" && XHR_GameServer != null) {
		XHR_GameServer.timeout = 10000;
		XHR_GameServer.ontimeout = function() {
			disableLoadingPage();XHR_GameServer.abort();
		}
		XHR_GameServer.onerror = function() {
			disableLoadingPage();XHR_GameServer.abort();
		}
		XHR_GameServer.onreadystatechange = handleAddGameServerAjax;
		XHR_GameServer.open("POST", "./AccountManage!addGameServer.php?date="
				+ getNewTime(), true);
		XHR_GameServer.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR_GameServer.send(str+"&tokenId="+encodeURIComponent(getEle("tokenId").value));
		enableLoadingPage();
	}
}
function handleAddGameServerAjax() {
	if (XHR_GameServer.readyState == 4) {
		if (XHR_GameServer.status == 200) {
			try {
				if (isJSON(XHR_GameServer.responseText)) {
					var json=JSON.parse(XHR_GameServer.responseText);
					if(checkTokenIdfail(json)){
						if (json.message == "fail") {
							onCheckModelPage2("遊戲伺服器Id重複"+(json.chkList == undefined ? "" : json.chkList));
						} else if (json.message == "success") {
							onClickCloseModal();
							onCheckModelPage2("遊戲伺服器建立成功");
						}
					}
				}
			} catch (error) {
				console_Log("handleAddGameServerAjax error:"+error.message);
			} finally {
				disableLoadingPage();
				XHR_GameServer.abort();
				if (json.message == "success"){
					getGameServerDataAjax();
				}
			}
		} else {
			disableLoadingPage();
			XHR_GameServer.abort();
		}
	}
}
function getGameServerDataAjax() {
	XHR_GameServer = checkXHR(XHR_GameServer);
	if (typeof XHR_GameServer != "undefined" && XHR_GameServer != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value);
		XHR_GameServer.timeout = 10000;
		XHR_GameServer.ontimeout = function() {
			disableLoadingPage();
			XHR_GameServer.abort();
		}
		XHR_GameServer.onerror = function() {
			disableLoadingPage();
			XHR_GameServer.abort();
		}
		XHR_GameServer.onreadystatechange = handleGetGameServerData;
		XHR_GameServer.open("POST", "./AccountManage!getGameServerData.php?date="
				+ getNewTime(), true);
		XHR_GameServer.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR_GameServer.send(tmpStr);
		enableLoadingPage();
	}
}

function handleGetGameServerData() {
	if (XHR_GameServer.readyState == 4) {
		if (XHR_GameServer.status == 200) {
			try {
				if (isJSON(XHR_GameServer.responseText)) {
					getEle("gameServerData").value = XHR_GameServer.responseText;
					var json = JSON.parse(XHR_GameServer.responseText);
					showGetGameServerTable();
				}
			} catch (error) {
				console_Log("handleGetGameServerData error:"+error.message);
			} finally {
				disableLoadingPage();
				XHR_GameServer.abort();
			}
		} else {
			disableLoadingPage();
			XHR_GameServer.abort();
		}
	}
}
function showGetGameServerTable(){
	var json = JSON.parse(getEle("gameServerData").value);
	var josnObject = json.GameServerData;
	var str = [];
	var showHtml = '';
	
	str[0]='<div id = "gameSetSearchTime">預留時間Div</div>';
	str[1]='';
	str[2]='<table class="table-zebra tr-hover">';
	str[3]='<tbody>';
	str[4]='<tr>';
	str[5]='<th>game server id</th>';
	str[6]='<th>入場金額</th>';
	str[7]='<th>最大遊戲人數</th>';
	str[8]='<th>局數設定</th>';
	str[9]='<th>抽成比例%</th>';
	str[10]='<th>Thread</th>';
	str[11]='<th>玩家TimeOut</th>';
	str[12]='<th>遊戲TimeOut</th>';
	str[13]='<th>電腦託管時間</th>';
	str[14]='<th>繼續遊戲TimeOut</th>';
	str[15]='<th>同一對手能繼續幾次</th>';
	str[16]='<th>維修時間</th>';
	str[17]='<th>啟用開關</th>';
	str[18]='<th>設定</th>';
	str[19]='</tr>';
	str[20]='';
	str[21]='</tbody>';
	str[22]='</table>';
	
	for (var i = 0; i < Object.keys(josnObject).length; i++) {
		str[20] += '<tr>'+
					'<td>'+josnObject[i].gameServerId+'</td>'+
					'<td>'+josnObject[i].bet+'/'+josnObject[i].nextBet+'</td>'+
					'<td>'+josnObject[i].maxPlayer+'/'+josnObject[i].nextMaxPlayer+'</td>'+
					'<td>'+josnObject[i].gameTimesType+'/'+josnObject[i].nextGameTimesType+'</td>'+
					'<td>'+josnObject[i].commission+'/'+josnObject[i].nextCommission+'</td>'+
					'<td>'+josnObject[i].thread+'/'+josnObject[i].nextThread+'</td>'+
					'<td>'+josnObject[i].beatsTimeOut+'/'+josnObject[i].nextBeatsTimeOut+'</td>'+
					'<td>'+josnObject[i].gameTimeOut+'/'+josnObject[i].nextGameTimeOut+'</td>'+
					'<td>'+josnObject[i].autoTimeStamp+'/'+josnObject[i].nextAutoTimeStamp+'</td>'+
					'<td>'+josnObject[i].waitContinueTimeOut+'/'+josnObject[i].nextWaitContinueTimeOut+'</td>'+
					'<td>'+josnObject[i].continueTimesType+'/'+josnObject[i].nextContinueTimesType+'</td>'+
					'<td>'+josnObject[i].serviceTime.split('.')[0]+'<br>'+(typeof josnObject[i].nextServiceTime === "undefined" ? "尚未修改" : josnObject[i].nextServiceTime.split('.')[0])+'</td>'+
					'<td>'+(josnObject[i].serverStatus == 1 ? "開" : "關")+'/'+(josnObject[i].nextServerStatus == 1 ? "開" : "關")+'</td>'+
					'<td><button onclick="showSetGameServer('+josnObject[i].sid+');">設定</button></td></tr>';
	}
	showHtml = str.join('');
	getEle("gameServerList").innerHTML = showHtml;
}
function showSetGameServer(sid){
	var json = JSON.parse(getEle("gameServerData").value);
	var josnObject = json.GameServerData;
	var gameServerId = josnObject[sid-1].gameServerId;
	var bet = josnObject[sid-1].bet;
	var maxPlayer = josnObject[sid-1].maxPlayer;
	var gameTimesType = josnObject[sid-1].gameTimesType;
	var commission = josnObject[sid-1].commission;
	var thread = josnObject[sid-1].thread;
	var beatsTimeOut = josnObject[sid-1].beatsTimeOut;
	var gameTimeOut = josnObject[sid-1].gameTimeOut;
	var autoTimeStamp = josnObject[sid-1].autoTimeStamp;
	var waitContinueTimeOut = josnObject[sid-1].waitContinueTimeOut;
	var continueTimesType = josnObject[sid-1].continueTimesType;
	var serviceTime = josnObject[sid-1].serviceTime.split('.')[0];
	var serverStatus = josnObject[sid-1].serverStatus == 1 ? "開" : "關";
	
	var nextBet = josnObject[sid-1].nextBet;
	var nextMaxPlayer = josnObject[sid-1].nextMaxPlayer;
	var nextGameTimesType = josnObject[sid-1].nextGameTimesType;
	var nextCommission = josnObject[sid-1].nextCommission;
	var nextThread = josnObject[sid-1].nextThread;
	var nextBeatsTimeOut = josnObject[sid-1].nextBeatsTimeOut;
	var nextGameTimeOut = josnObject[sid-1].nextGameTimeOut;
	var nextAutoTimeStamp = josnObject[sid-1].nextAutoTimeStamp;
	var nextWaitContinueTimeOut = josnObject[sid-1].nextWaitContinueTimeOut;
	var nextContinueTimesType = josnObject[sid-1].nextContinueTimesType;
	var nextServiceDate = (typeof josnObject[sid-1].nextServiceTime === "undefined") ? "尚未修改" : josnObject[sid-1].nextServiceTime.split(" ")[0];
	var nextServiceHr = (typeof josnObject[sid-1].nextServiceTime === "undefined") ? "00" : josnObject[sid-1].nextServiceTime.split(" ")[1].split(":")[0];
	var nextServiceMin = (typeof josnObject[sid-1].nextServiceTime === "undefined") ? "00" : josnObject[sid-1].nextServiceTime.split(" ")[1].split(":")[1];
	var nextServerStatus = josnObject[sid-1].nextServerStatus;
	if(nextServerStatus == 1){
		var switchopen = "selected";
		var switchclose = "";
	}else if(nextServerStatus == 0){
		var switchopen = "";
		var switchclose = "selected";
	}
	var str = [];
	
	str[0]='<div class="modal-content width-percent-420 margin-fix-5">';
	str[1]='<span class="close" onclick="onClickCloseModal();">×</span>';
	str[2]='<h3>GameServer設定 id:'+gameServerId+'</h3>';
	str[3]='<table class="gameserverset-table">';
	str[4]='<tr><th>入場金額</th><td>目前金額:'+bet+'</td><td><input type="text" id="bet" onkeyup = "chkBet();" maxlength="6" value="'+nextBet+'"></td></tr>';
	str[5]='<tr><th>最大遊戲人數</th><td>目前人數:'+maxPlayer+'</td><td><input type="text" id="maxPlayer" onkeyup = "chkMaxPlayer();" maxlength="3" value="'+nextMaxPlayer+'"></td></tr>';
	str[6]='<tr><th>局數設定</th><td>目前局數:'+gameTimesType+'</td><td><input type="text" id="gameTimesType" onkeyup = "chkGameTimesType();" maxlength="3" value="'+nextGameTimesType+'"></td></tr>';
	str[7]='<tr><th>抽成比例%</th><td>目前抽成:'+commission+'</td><td><input type="text" id="commission" onkeyup = "chkCommission();" maxlength="3" value="'+nextCommission+'"></td></tr>';
	str[8]='<tr><th>Thread</th><td>目前Thread:'+thread+'</td><td><input type="text" id="thread" onkeyup = "chkThread();" maxlength="3" value="'+nextThread+'"></td></tr>';
	str[9]='<tr><th>玩家心跳</th><td>目前玩家心跳:'+beatsTimeOut+'</td><td><input type="text" id="beatsTimeOut" onkeyup = "chkBeatsTimeOut();" maxlength="3" value="'+nextBeatsTimeOut+'"></td></tr>';
	str[10]='<tr><th>遊戲時間</th><td>目前遊戲時間:'+gameTimeOut+'</td><td><input type="text" id="gameTimeOut" onkeyup = "chkGameTimeOut();" maxlength="3" value="'+nextGameTimeOut+'"></td></tr>';
	str[11]='<tr><th>電腦託管時間</th><td>目前電腦託管:'+autoTimeStamp+'</td><td><input type="text" id="autoTimeStamp" onkeyup = "chkAutoTimeStamp();" maxlength="3" value="'+nextAutoTimeStamp+'"></td></tr>';
	str[12]='<tr><th>繼續遊戲時間</th><td>目前繼續遊戲:'+waitContinueTimeOut+'</td><td><input type="text" id="waitContinueTimeOut" onkeyup = "chkWaitContinueTimeOut();" maxlength="3" value="'+nextWaitContinueTimeOut+'"></td></tr>';
	str[13]='<tr><th>同對手能繼續場次</th><td>目前同對手場次:'+continueTimesType+'</td><td><input type="text" id="continueTimesType" onkeyup = "chkContinueTimesType();" maxlength="3" value="'+nextContinueTimesType+'"></td></tr>';
	str[14]='<tr><th>維修時間</th><td>目前維修時間:<span>'+serviceTime+'</span></td><td><input type="text" id="serviceTime" value="'+nextServiceDate+'" onclick="newCalendar(this, this.id,1)" readonly></td></tr>';
	str[15]='<tr><th>開關</th><td>目前開關:'+serverStatus+'</td><td><select id="serverStatus"><option value = 1 '+switchopen+'>開</option><option value = 0 '+switchclose+'>關</option></td></tr>';
	str[16]='</table><div class="btn-area">';
	str[17]='<button class="btn-lg btn-orange" onclick="conformSetGameServer('+sid+');">確定</button>\n<button class="btn-lg btn-gray" onclick="onClickCloseModal();">取消</button>';
	str[18]='</div></div>';
	
	var showHtml = '';
	showHtml = str.join('');
	onClickOpenModal(showHtml);
}
function conformSetGameServer(sid){
	var json = JSON.parse(getEle("gameServerData").value);
	var josnObject = json.GameServerData;
	var gameServerId = josnObject[sid-1].gameServerId;
	var nextBet = getEle("bet").value;
	var nextMaxPlayer = getEle("maxPlayer").value;
	var nextGameTimesType = getEle("gameTimesType").value;
	var nextCommission = getEle("commission").value;
	var nextThread = getEle("thread").value;
	var nextBeatsTimeOut = getEle("beatsTimeOut").value;
	var nextGameTimeOut = getEle("gameTimeOut").value;
	var nextAutoTimeStamp = getEle("autoTimeStamp").value;
	var nextWaitContinueTimeOut = getEle("waitContinueTimeOut").value;
	var nextContinueTimesType = getEle("continueTimesType").value;
	var nextServiceTime = getEle("serviceTime").value;
	var nextServerStatus = getEle("serverStatus").value;
	var str = "";
	if(sid > 0 && getEle("serviceTime").value != "尚未修改"){
		str = joinVars("&",{sid,gameServerId,nextBet,nextMaxPlayer,nextGameTimesType,nextCommission,nextThread,nextBeatsTimeOut,nextGameTimeOut,nextAutoTimeStamp,nextWaitContinueTimeOut,nextContinueTimesType,nextServiceTime,nextServerStatus},true);
	}else{
		onCheckModelPage2("請輸入時間");
	}
	if(str != ""){
		onCheckModelPage('確定送出', 'setGameServerAjax(\''+str+'\')');
	}
}
function setGameServerAjax(tmStr) {
	XHR_GameServer = checkXHR(XHR_GameServer);
	if (typeof XHR_GameServer != "undefined" && XHR_GameServer != null) {
		XHR_GameServer.timeout = 10000;
		XHR_GameServer.ontimeout = function() {
			disableLoadingPage();
			XHR_GameServer.abort();
		}
		XHR_GameServer.onerror = function() {
			disableLoadingPage();
			XHR_GameServer.abort();
		}
		XHR_GameServer.onreadystatechange = handleSetGameServerData;
		XHR_GameServer.open("POST", "./AccountManage!setGameServerData.php?date="
				+ getNewTime(), true);
		XHR_GameServer.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR_GameServer.send(tmStr+"&tokenId="+encodeURIComponent(getEle("tokenId").value));
		enableLoadingPage();
	}
}

function handleSetGameServerData() {
	if (XHR_GameServer.readyState == 4) {
		if (XHR_GameServer.status == 200) {
			try {
				if (isJSON(XHR_GameServer.responseText)) {
					var json = JSON.parse(XHR_GameServer.responseText);
					if (json.isSuccess){
						onCheckCloseModelPage();
						onClickCloseModal();
						onCheckModelPage2("遊戲伺服器資料修改成功");
					}else{
						onCheckModelPage2(json.message);
					}
				}
			} catch (error) {
				console_Log("handleSetGameServerData error:"+error.message);
			} finally {
				disableLoadingPage();
				XHR_GameServer.abort();
				if (json.isSuccess){
					getGameServerDataAjax();
				}
			}
		} else {
			disableLoadingPage();
			XHR_GameServer.abort();
		}
	}
}

function confromShowGameSetLog(upperAction){
	var logPage = getEle("logPage").value;
	var logNextPage = logPage;
	var count = getEle("logCount").value;
	if(upperAction == LOG_ACTION_GAME_SERVER_CHANGE){
		var str = "&upperAction="+upperAction+"&count="+count+"&logPage="+logPage+"&logNextPage="+logNextPage;
		getEle("nowActionUpperId").value = upperAction;
		showGameSetLogAjax(str);
	}
}
function showGameSetLogAjax(str) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+str;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onreadystatechange = handleShowGameSetLogAjax;
		XHR.open("POST", "./AccountManage!showGameSetLog.php?date="
				+ getNewTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.send(tmpStr);
		enableLoadingPage();
	}
}
function handleShowGameSetLogAjax() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					getEle("getLogInfo").value = XHR.responseText;
					var json = JSON.parse(getEle("getLogInfo").value);
					if(checkTokenIdfail(json)){
						showGameSetLog();
						if(typeof json.logPage != "undefined" && json.logPage != null){
							getEle("logPage").value = json.logPage;
							if(getEle("displayNowLogPage") != null && getEle("displayNowLogPage") != undefined){
								getEle("displayNowLogPage").innerHTML = json.logPage;
							}
						}
						if(typeof json.logMaxPage != "undefined" && json.logMaxPage != null){
							getEle("logMaxPage").value = json.logMaxPage;
							if(getEle("displayNowLogMaxPage") != null && getEle("displayNowLogMaxPage") != undefined){
								getEle("displayNowLogMaxPage").innerHTML = json.logMaxPage;
							}
						}
					}
				}
			} catch (error) {
				console_Log("handleShowGameSetLogAjax error"+error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}
function showGameSetLog() {
	var json = JSON.parse(getEle("getLogInfo").value);
	var josnObject = json.showGameSetLog;
	var count = getEle("logCount").value;
	var logTableName = "遊戲伺服器紀錄列表";
	
	var logTableTitle = logTableHTML(["編號","遊戲伺服器名稱","類型","操作者","日期","IP","內容"]);
	
	var action = '';
	var gameServerId = '';
	var opsAccName = '';
	var datetime = '';
	var ip = '';
	var strHTML = '';
	var detail ='';
	var data = '';
	var tmpDetail = '';
	
	var str = [];
	
	str[0]='<div class="modal-content width-percent-960 margin-fix-4">';
	str[1]='<span class="close" onclick="onClickCloseModal();">×</span>';
	str[2]='<h3>'+logTableName+'</h3>';
	str[3]='<p class="media-control text-center">';
	str[4]='<a href="#" onclick="firstGameSetLogPage('+count+');" class="backward"></a>';
	str[5]='<a href="#" onclick="previousGameSetLogPage('+count+');" class="backward-fast"></a>';
	str[6]='<span>總頁數：<i id= "displayNowLogPage">1</i><span>/</span><i id = "displayNowLogMaxPage">1</i>頁</span>';
	str[7]='<a href="#" onclick="nextGameSetLogPage('+count+');" class="forward"></a>';
	str[8]='<a href="#" onclick="nextGameSetLogMaxPage('+count+');" class="forward-fast"></a></p>';
	str[16]='<div class="tab-content">';
	str[17]='<table class="table-zebra tr-hover">';
	str[18]='<tbody>';
	str[19]= logTableTitle;
	str[20]='</tbody>';
	str[21]='</table>';
	str[22]='</div>';
	str[23]='</div>';
	
	var showHtml = '';
	for (var i = 0; i < Object.keys(josnObject).length; i++) {
		if(!isNull(strToJson(josnObject[i].detail))){
			detail = strToJson(josnObject[i].detail);
		}
		if(!isNull(josnObject[i].action)){
			action = josnObject[i].action;
		}
		if(!isNull(josnObject[i].accName)){
			gameServerId = josnObject[i].accName;
		}
		if(!isNull(josnObject[i].opsAccName)){
			opsAccName = josnObject[i].opsAccName;
		}
		if(!isNull(josnObject[i].opsDatetime)){
			datetime = josnObject[i].opsDatetime.split('.')[0];
		}
		if(!isNull(josnObject[i].ip)){
			ip = josnObject[i].ip;
		}
		tmpDetail = getDetailStr(action,detail);
		if(tmpDetail.length > 20){
			tmpDetail = tmpDetail.substring(0,20)+'...';
		}
		strHTML = logDetailTableHTML([(i+1),gameServerId,ACTION_JSON[action],opsAccName,datetime,ip,tmpDetail]);
		str[19] += strHTML;
	}
	showHtml = str.join('');
	onClickOpenModal(showHtml);
}
function logDetailTableHTML (obj = []){
	var strHTML = '<tr>';
	for(var i = 0;i < obj.length;i++){
		if(i == (obj.length-1)){
			strHTML = strHTML.concat('<td><a href="javascript:showDetailModal('+(parseInt(obj[0])-1)+');">');
			strHTML = strHTML.concat(obj[i].toString());
			strHTML = strHTML.concat('</a></td>');
		}else{
			strHTML = strHTML.concat('<td>');
			strHTML = strHTML.concat(obj[i].toString());
			strHTML = strHTML.concat('</td>');
		}
	}
	strHTML = strHTML.concat('</tr>');
	return strHTML;
}
function showDetailModal(i){
	var json = JSON.parse(getEle("getLogInfo").value);
	var josnObject = json.showGameSetLog;
	var detail = strToJson(josnObject[i].detail);
	var detailKey = Object.keys(detail);
	var str = [];
	var data = '';
	var oldValue = '';
	var newValue = '';
	var showHtml = '';
	var addKeys = Object.keys(ADD_GAME_SERVER_JSON);
	
	if(!isNull(josnObject[i].action)){
		var action = josnObject[i].action;
	}
	if(!isNull(josnObject[i].accName)){
		var gameServerId = josnObject[i].accName;
	}
	try{
		str[0]='<div class="modal-content width-percent-460">';
		str[1]='<span class="close" onclick="onClickCloseModalV2();">×</span>';
		str[2]='<h3>'+'遊戲伺服器名稱:'+gameServerId+' 詳細資料'+'</h3>';
		str[3]='<div class="tab-content">';
		str[4]='<table class="table-zebra tr-hover">';
		str[5]='<tbody>';
		str[6]= '';
		str[7]='</tbody>';
		str[8]='</table>';
		str[9]='</div>';
		str[10]='</div>';
		if(action == 50){
			for(var i = 0; i < addKeys.length; i++){
				for(var j =0;j < detailKey.length;j++){
					if(detailKey[j] == addKeys[i]){
						data = data.concat('<tr><td>',ADD_GAME_SERVER_JSON[detailKey[j]], '</td><td>', detail[detailKey[j]], '</td></tr>');
					}
				}
			}
			str[6] = data;
		}else if(action == 51){
			data = data.concat('<tr><td>修改前</td><td>修改後</td></tr>');
			for(var i = 0; i < detailKey.length; i++){
				if(detailKey[i] == 'nextServerStatus'){
					oldValue = (detail[detailKey[i]][0] == 0 ? '關' : '開');
					newValue = (detail[detailKey[i]][1] == 0 ? '關' : '開');
				}else{
					oldValue = detail[detailKey[i]][0];
					newValue = detail[detailKey[i]][1];
				}
				data = data.concat('<tr><td>', SET_GAME_SERVER_JSON[detailKey[i]], (isNull(oldValue) ? '尚未修改' : oldValue),'</td>');
				data = data.concat('<td>', SET_GAME_SERVER_JSON[detailKey[i]], (isNull(newValue) ? '尚未修改' : newValue),'</td></tr>');
			}
			str[6] = data;
		}
	}catch(error){
		data = '';
		console_Log("getGameSetDetail error="+error.message);
	}finally{
		if(data.length > 0){
			showHtml = str.join('');
			onClickOpenModalV2(showHtml);
		}
	}
}
function getDetailStr(action,detail){
	var data = '';
	var detailKey = Object.keys(detail);
	var addKeys = Object.keys(ADD_GAME_SERVER_JSON);
	try{
		if(action == 50){
			for(var i = 0; i < addKeys.length; i++){
				for(var j =0;j < detailKey.length;j++){
					if(detailKey[j] == addKeys[i]){
						data = data.concat(ADD_GAME_SERVER_JSON[detailKey[j]], detail[detailKey[j]],',');
					}
				}
			}
		}else if(action == 51){
			for(var i = 0; i < detailKey.length; i++){
				if(detailKey[i] == 'nextServerStatus'){
					oldValue = (detail[detailKey[i]][0] == 0 ? '關' : '開');
					newValue = (detail[detailKey[i]][1] == 0 ? '關' : '開');
				}else{
					oldValue = detail[detailKey[i]][0];
					newValue = detail[detailKey[i]][1];
				}
				data = data.concat(SET_GAME_SERVER_JSON[detailKey[i]], (isNull(oldValue) ? '尚未修改' : oldValue),',');
				data = data.concat(SET_GAME_SERVER_JSON[detailKey[i]], (isNull(newValue) ? '尚未修改' : newValue),',');
			}
		}else{
			data = '';
		}
	}catch(error){
		data = '';
		console_Log("getGameSetDetail error="+error.message);
	}finally{
		
	}
	return data
}
function nextGameSetLogPage(count){
	var nextPageNum = parseInt(getEle("logPage").value)+1;
	if(nextPageNum <= parseInt(getEle("logMaxPage").value)){
		page = nextPageNum;
	}
	else{
		page = getEle("logMaxPage").value;
	}
	var nowActionUpperId = getEle("nowActionUpperId").value;
	var str = "&upperAction="+nowActionUpperId+"&count="+count+"&nextPage="+page;
	showGameSetLogAjax(str);
}
function nextGameSetLogMaxPage(count){
	var maxPage = getEle("logMaxPage").value;
	var nowActionUpperId = getEle("nowActionUpperId").value;
	var str = "&upperAction="+nowActionUpperId+"&count="+count+"&nextPage="+maxPage;
	showGameSetLogAjax(str);
}

function previousGameSetLogPage(count){
	var previousPage = parseInt(getEle("logPage").value)-1;
	var page = "1"
	if(previousPage > 0){
		page = previousPage;
	}
	var nowActionUpperId = getEle("nowActionUpperId").value;
	var str = "&upperAction="+nowActionUpperId+"&count="+count+"&nextPage="+page;
	showGameSetLogAjax(str);
}

function firstGameSetLogPage(count){
	var nowActionUpperId = getEle("nowActionUpperId").value;
	var str = "&upperAction="+nowActionUpperId+"&count="+count+"&nextPage=1";
	showGameSetLogAjax(str);
}
function showSetGameParamPageAjax() {
	XHR_GameParam = checkXHR(XHR_GameParam);
	if (typeof XHR_GameParam != "undefined" && XHR_GameParam != null) {
		var tmpStr = "tokenId="+encodeURIComponent(getEle("tokenId").value);
		XHR_GameParam.timeout = 10000;
		XHR_GameParam.ontimeout = function() {
			disableLoadingPage();XHR_GameParam.abort();
		}
		XHR_GameParam.onerror = function() {
			disableLoadingPage();XHR_GameParam.abort();
		}
		XHR_GameParam.onreadystatechange = handleShowSetGameParamPageAjax;
		XHR_GameParam.open("POST", "./SetGame!getGameParamData.php?date="
				+ getNewTime(), true);
		XHR_GameParam.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR_GameParam.send(tmpStr);
		enableLoadingPage();
	}
}
function handleShowSetGameParamPageAjax() {
	if (XHR_GameParam.readyState == 4) {
		if (XHR_GameParam.status == 200) {
			try {
				if (isJSON(XHR_GameParam.responseText)) {
					getEle("setGameParamData").value = XHR_GameParam.responseText;
					var json = JSON.parse(getEle("setGameParamData").value);
					if(checkTokenIdfail(json)){
						showGameDataPage();
					}
				}
			} catch (error) {
				console_Log("handleShowSetGameParamPageAjax error"+error.message);
			} finally {
				disableLoadingPage();
				XHR_GameParam.abort();
			}
		} else {
			disableLoadingPage();
			XHR_GameParam.abort();
		}
	}
}
function showGameDataPage(){
	var str = [];
	var json = JSON.parse(getEle("setGameParamData").value)
	var gameParamData = '';
	if(!isNull(json.gameParamData)){
		gameParamData = json.gameParamData;
	}
	str.push('<div class="Div2-7-tablearea"> \n');
	str.push('<h3>三國猜拳王</h3> \n');
	str.push('<table class="table-zebra tr-hover">');
	str.push('<tbody>');
	str.push('<tr><th colspan="2">場館一</th><th colspan="2">場館二</th><th colspan="2">場館三</th></tr>');
	str.push('<tr><td>局數設定</td><td><input type="text" class="" id="gameTimesType1" readonly></td><td>局數設定</td><td><input type="text" class="" id="gameTimesType2" readonly></td><td>局數設定</td><td><input type="text" class="" id="gameTimesType3" readonly></td></tr>');
	str.push('<tr><td>入場金額一</td><td><input type="text" class="" id="bet1-1" readonly></td><td>入場金額一</td><td><input type="text" class="" id="bet2-1" readonly></td><td>入場金額一</td><td><input type="text" class="" id="bet3-1" readonly></td></tr>');
	str.push('<tr><td>入場金額二</td><td><input type="text" class="" id="bet1-2" readonly></td><td>入場金額二</td><td><input type="text" class="" id="bet2-2" readonly></td><td>入場金額二</td><td><input type="text" class="" id="bet3-2" readonly></td></tr>');
	str.push('<tr><td>入場金額三</td><td><input type="text" class="" id="bet1-3" readonly></td><td>入場金額三</td><td><input type="text" class="" id="bet2-3" readonly></td><td>入場金額三</td><td><input type="text" class="" id="bet3-3" readonly></td></tr>');
	str.push('<tr><td>出拳時間(秒)</td><td><input type="text" class="" id="gameTimeOut1" readonly></td><td>出拳時間(秒)</td><td><input type="text" class="" id="gameTimeOut2" readonly></td><td>出拳時間(秒)</td><td><input type="text" class="" id="gameTimeOut3" readonly></td></tr>');
	str.push('<tr><td>結束倒數(秒)</td><td><input type="text" class="" id="waitContinueTimeOut1" readonly></td><td>結束倒數(秒)</td><td><input type="text" class="" id="waitContinueTimeOut2" readonly></td><td>結束倒數(秒)</td><td><input type="text" class="" id="waitContinueTimeOut3" readonly></td></tr>');
	str.push('<tr><td>抽成比例%</td><td><input type="text" class="" id="commission1" readonly></td><td>抽成比例%</td><td><input type="text" class="" id="commission2" readonly></td><td>抽成比例%</td><td><input type="text" class="" id="commission3" readonly></td></tr>');
	str.push('<tr><td>同對手可連續局數</td><td><input type="text" class="" id="continueTimesType1" readonly></td><td>同對手可連續局數</td><td><input type="text" class="" id="continueTimesType2" readonly></td><td>同對手可連續局數</td><td><input type="text" class="" id="continueTimesType3" readonly></td></tr>');
	str.push('<tr><td colspan="6"><p class="txt-red">設定完將於下次維護結束後生效</p></td></tr>');
	str.push('<tr><td colspan="6"><button onclick = "showSetGameParamPage();" class="btn-xl btn-orange">設定</button></td></tr>');
	str.push('<tr><td colspan="6">1.局數設定:數字表示總場數，如輸入5，系統判斷為5戰3勝</td></tr>');
	str.push('</tbody>');
	str.push('</table> \n');
	str.push('</div>');
	getEle("searchArea").innerHTML = str.join("");
	
	var betArr = ["bet1-1","bet1-2","bet1-3","bet2-1","bet2-2","bet2-3","bet3-1","bet3-2","bet3-3"];
	for(var k = 0;k < Object.keys(gameParamData).length;k++){
		if(!isNull(gameParamData[k].bet)){
			getEle(betArr[k]).value = gameParamData[k].bet;
		}
	}
	var numArr = [0,3,6];
	var keyArr = ["gameTimesType","gameTimeOut","waitContinueTimeOut","commission","continueTimesType"];
	for(var a = 0;a < numArr.length;a++){
		for(var b = 0;b < keyArr.length;b++){
			if(!isNull(gameParamData[numArr[a]][keyArr[b]])){
				getEle(keyArr[b]+(a+1)).value = gameParamData[numArr[a]][keyArr[b]];
			}
		}
	}
}
function showSetGameParamPage(){
	var str = [];
	var json = JSON.parse(getEle("setGameParamData").value)
	var gameParamData = '';
	if(!isNull(json.gameParamData)){
		gameParamData = json.gameParamData;
	}
	str.push('<div class="modal-content width-percent-720 margin-fix-5"> \n');
	str.push('<span class="close" onclick="onClickCloseModal();">×</span> \n');
	str.push('<h3>三國猜拳王</h3> \n');
	str.push('<table class="table-zebra tr-hover">');
	str.push('<tbody>');
	str.push('<tr><th colspan="2">場館一</th><th colspan="2">場館二</th><th colspan="2">場館三</th></tr>');
	str.push('<tr><td>局數設定</td><td><input type="text" class="" id="gameTimesType1_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td><td>局數設定</td><td><input type="text" class="" id="gameTimesType2_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td><td>局數設定</td><td><input type="text" class="" id="gameTimesType3_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td></tr>');
	str.push('<tr><td>入場金額一</td><td><input type="text" class="" id="bet1-1_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td><td>入場金額一</td><td><input type="text" class="" id="bet2-1_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td><td>入場金額一</td><td><input type="text" class="" id="bet3-1_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td></tr>');
	str.push('<tr><td>入場金額二</td><td><input type="text" class="" id="bet1-2_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td><td>入場金額二</td><td><input type="text" class="" id="bet2-2_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td><td>入場金額二</td><td><input type="text" class="" id="bet3-2_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td></tr>');
	str.push('<tr><td>入場金額三</td><td><input type="text" class="" id="bet1-3_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td><td>入場金額三</td><td><input type="text" class="" id="bet2-3_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td><td>入場金額三</td><td><input type="text" class="" id="bet3-3_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td></tr>');
	str.push('<tr><td>出拳時間(秒)</td><td><input type="text" class="" id="gameTimeOut1_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td><td>出拳時間(秒)</td><td><input type="text" class="" id="gameTimeOut2_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td><td>出拳時間(秒)</td><td><input type="text" class="" id="gameTimeOut3_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td></tr>');
	str.push('<tr><td>結束倒數(秒)</td><td><input type="text" class="" id="waitContinueTimeOut1_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td><td>結束倒數(秒)</td><td><input type="text" class="" id="waitContinueTimeOut2_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td><td>結束倒數(秒)</td><td><input type="text" class="" id="waitContinueTimeOut3_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td></tr>');
	str.push('<tr><td>抽成比例%</td><td><input type="text" class="" id="commission1_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td><td>抽成比例%</td><td><input type="text" class="" id="commission2_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td><td>抽成比例%</td><td><input type="text" class="" id="commission3_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td></tr>');
	str.push('<tr><td>同對手可連續局數</td><td><input type="text" class="" id="continueTimesType1_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td><td>同對手可連續局數</td><td><input type="text" class="" id="continueTimesType2_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td><td>同對手可連續局數</td><td><input type="text" class="" id="continueTimesType3_1" onkeyup="this.value = checkInputNumberVal(this.value);"></td></tr>');
	str.push('<tr><td colspan="6"><p class="txt-red">設定完將於下次維護結束後生效</p></td></tr>');
	str.push('<tr><td colspan="6" class="padding-fix-2"><button onclick = "confromGameParamUpdate();"class="btn-xl btn-orange">保存</button> \<button onclick = "onClickCloseModal();"class="btn-xl btn-gray">取消</button></td></tr>');
	str.push('<tr><td colspan="6">1.局數設定:數字表示總場數，如輸入5，系統判斷為5戰3勝</td></tr>');
	str.push('</tbody>');
	str.push('</table> \n');
	str.push('</div>');
	onClickOpenModal(str.join(""));
	var betArr = ["bet1-1_1","bet1-2_1","bet1-3_1","bet2-1_1","bet2-2_1","bet2-3_1","bet3-1_1","bet3-2_1","bet3-3_1"];
	for(var k = 0;k < Object.keys(gameParamData).length;k++){
		if(!isNull(gameParamData[k].bet)){
			getEle(betArr[k]).value = gameParamData[k].bet;
		}
	}
	var numArr = [0,3,6];
	var keyArr = ["gameTimesType","gameTimeOut","waitContinueTimeOut","commission","continueTimesType"];
	for(var a = 0;a < numArr.length;a++){
		for(var b = 0;b < keyArr.length;b++){
			if(!isNull(gameParamData[numArr[a]][keyArr[b]])){
				getEle(keyArr[b]+(a+1)+"_1").value = gameParamData[numArr[a]][keyArr[b]];
			}
		}
	}
}

function confromGameParamUpdate(){
	var json = JSON.parse(getEle("setGameParamData").value)
	var gameParamData = '';
	if(!isNull(json.gameParamData)){
		gameParamData = json.gameParamData;
	}
	var str = {};
	var strList = {};
	var keyArr = ["sid","bet","gameTimesType","gameTimeOut","waitContinueTimeOut","commission","continueTimesType"];
	var betArr = ["bet1-1","bet1-2","bet1-3","bet2-1","bet2-2","bet2-3","bet3-1","bet3-2","bet3-3"];
	var gameTimesTypeArr = ["gameTimesType1","gameTimesType1","gameTimesType1","gameTimesType2","gameTimesType2","gameTimesType2","gameTimesType3","gameTimesType3","gameTimesType3"];
	var gameTimeOutArr = ["gameTimeOut1","gameTimeOut1","gameTimeOut1","gameTimeOut2","gameTimeOut2","gameTimeOut2","gameTimeOut3","gameTimeOut3","gameTimeOut3"];
	var waitContinueTimeOutArr = ["waitContinueTimeOut1","waitContinueTimeOut1","waitContinueTimeOut1","waitContinueTimeOut2","waitContinueTimeOut2","waitContinueTimeOut2","waitContinueTimeOut3","waitContinueTimeOut3","waitContinueTimeOut3"];
	var commissionArr = ["commission1","commission1","commission1","commission2","commission2","commission2","commission3","commission3","commission3"];
	var continueTimesTypeArr = ["continueTimesType1","continueTimesType1","continueTimesType1","continueTimesType2","continueTimesType2","continueTimesType2","continueTimesType3","continueTimesType3","continueTimesType3"];
	var valueArr = {};
	
	for(var a = 0;a < gameParamData.length;a++){
		var str = new Object();
		for(var b = 0;b < keyArr.length;b++){
			valueArr = [gameParamData[a].sid,getEle(betArr[a]+"_1").value,getEle(gameTimesTypeArr[a]+"_1").value,getEle(gameTimeOutArr[a]+"_1").value,getEle(waitContinueTimeOutArr[a]+"_1").value,getEle(commissionArr[a]+"_1").value,getEle(continueTimesTypeArr[a]+"_1").value];
			if(!isNull(valueArr[b])){
				str [keyArr[b]] = valueArr[b];
			}
		}
		strList [a] = str;
	}
	saveCheck(JSON.stringify(strList));
}
function saveCheck(strList) {
	onCheckModelPage('確定送出', 'gameParamUpdateAjax(\''+strList+'\')');
}
function gameParamUpdateAjax(strList) {
	XHR_GameParam = checkXHR(XHR_GameParam);
	if (typeof XHR_GameParam != "undefined" && XHR_GameParam != null) {
		var tmpStr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&updateObj="+strList;
		XHR_GameParam.timeout = 10000;
		XHR_GameParam.ontimeout = function() {
			disableLoadingPage();XHR_GameParam.abort();
		}
		XHR_GameParam.onerror = function() {
			disableLoadingPage();XHR_GameParam.abort();
		}
		XHR_GameParam.onreadystatechange = handleGameParamUpdateAjax;
		XHR_GameParam.open("POST", "./SetGame!updateGameParamData.php?date="
				+ getNewTime(), true);
		XHR_GameParam.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR_GameParam.send(tmpStr);
		enableLoadingPage();
	}
}
function handleGameParamUpdateAjax() {
	if (XHR_GameParam.readyState == 4) {
		if (XHR_GameParam.status == 200) {
			try {
				if (isJSON(XHR_GameParam.responseText)) {
					var json = JSON.parse(XHR_GameParam.responseText);
					if(checkTokenIdfail(json)){
						if (json.isSuccess){
							onCheckModelPage2("遊戲參數設定成功");
							onClickCloseModal();
						}else{
							onCheckModelPage2("設定失敗");
						}
					}
				}
			} catch (error) {
				onCheckModelPage2("設定失敗");
				console_Log("handleGameParamUpdateAjax error"+error.message);
			} finally {
				disableLoadingPage();
				XHR_GameParam.abort();
				if (json.isSuccess){
					showSetGameParamPageAjax();
				}
			}
		} else {
			disableLoadingPage();
			XHR_GameParam.abort();
		}
	}
}
function confromGameParamSearch(){
	
}
