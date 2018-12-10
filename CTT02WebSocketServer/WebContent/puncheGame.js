WEB_SOCKET_SWF_LOCATION = "WebSocketMain.swf";
	var wsClientId = "";
	var gameServerWS;
	var heartBeatEnable = false;
		
	const  SET_ACC_ID = "101";
	const  SET_ACCID_OK = "102";
	
	const  GET_SERVER_LIST = "103";
	const  SERVER_LIST = "104";
	
	const  GET_LAST_SERVER = "105";
	const  LAST_SERVER = "106";
	
	const  JOIN = "107";
	const  CONNECT_SUCCESS = "108";
	const  RECONNECT_SUCCESS = "110";
	const  IN_LOBBY = "112";
	const  SERVER_FULL = "114";
	
	const  MATCH = "115";
	const  WAIT_ROOM = "116";
	const  INTO_ROOM = "118";
	
	const  LEAVE = "119";
	const  GET_LEAVE_ROOM = "120";
	
	const  PUNCHE = "121";
	const  GET_PUNCHE = "122";
	const  PAPER = "123";
	const  SCISSOR = "125";
	const  ROCK = "127";
	
	const  AUTO_PUNCHE = "128";
	
	const  KEEP_PLAY = "129";
	const  GET_KEEP_PLAY = "130";
	
	const  CMD_NO_WINNER = "132";
	const  WINNER = "134";
	const  LOSER = "136";	
	const  FINAL_WINNER = "138";
	const  FINAL_LOSER = "140";
	
	const  WAIT_CONTINUE = "142";
	const  WAIT_VIDEO = "144";
	const  WAIT_PUNCHE = "146";
	const  GAME_START = "148";
	const  ROUND = "150";
	const  REAL_PLAYTIMES = "152";
	const  MONEY = "154";
	
	const  PUNCHE_TIMEOUT = "156";
	const  CONTINUE_TIMEOUT = "158";
	const  VIDEO_TIMEOUT = "160";
	const  CONNECT_FAIL = "162";
	
	const  HEART_BEAT = "163";
	
	const  GAME_OVER = "164";
	const  NO_MONEY = "166";
	const  DISCONNECT_GAME_SERVER = "168";
	
	const  LEAVE_GAME = "169";
	const  GET_LEAVE_GAME = "170";
	
	const  START_ROBOT = "171";
	const  GET_START_ROBOT = "172";
	const  STOP_ROBOT = "173";
	const  GET_STOP_ROBOT = "174";
	
	const  NAME = "176";
	const  OPPONENT_NAME = "178";
	const  OPPONENT_MONEY = "180";

	const  WIN_TIMES = "182";
	const  OPPONENT_WIN_TIMES = "184";
	const  MAX_LIVE_POINT = "186";
	
	const  OPPONENT_GET_START_ROBOT = "188";
	const  OPPONENT_GET_STOP_ROBOT = "190";
	
	const  VIDEO_INFO = "192";
	
	const  ADD_MONEY = "194";
	const  SUB_MONEY = "196";
	
	const  SET_ROLE = "197";
	const  GET_ROLE = "198";
	const  OPPONENT_ROLE = "200";
	
	
	function sentHeartbeat() {
		if (heartBeatEnable == true) {
			var obj = new Object;
			obj.From = wsClientId;
			obj.Cmd = HEART_BEAT;
			obj.AccId = document.getElementById("accIdInput").value;
			obj.Data = "";
			gameServerWS.send(JSON.stringify(obj));
			setTimeout("sentHeartbeat()", 5000);
		}
	}

	function startHeartbeat() {
		if (heartBeatEnable == false) {
			heartBeatEnable = true;
			setTimeout("sentHeartbeat()", 5000);
		}
	}

	function stopHeartbeat() {
		heartBeatEnable = false;
	}

	function openGameSocket() {
		if (gameServerWS !== undefined
				&& gameServerWS.readyState !== gameServerWS.CLOSED) {
			writeResponse("gameServerWS is already opened.");
			return;
		}
		var webSocketId = document.getElementById("accIdInput").value;
		wsClientId = "client" + webSocketId + new Date().getTime();
		writeResponse("開啟WS : " + wsClientId);
		gameServerWS = new WebSocket("ws://"
				+ document.getElementById("gameServerIP").value
				+ ":8080/CTT02WebSocketServer/gameWS?" + wsClientId);
		gameServerWS.onopen = function(event) {
			startHeartbeat();
		};
		gameServerWS.onmessage = function(event) {
			if (event.data === undefined)
				return;
			handleGameSocketMsg(event.data);
		};
		gameServerWS.onclose = function(event) {
			writeResponse("gameWS closed");
			stopHeartbeat();
		};
	}
	
	function handleGameSocketMsg(msg) {
		var gameMsgObj = JSON.parse(msg);		
		if(document.getElementsByName("SAVE_"+gameMsgObj.Cmd).length ==  0){
			var tmpInput = document.createElement("input");
			tmpInput.type = 'hidden';
			tmpInput.name="SAVE_"+gameMsgObj.Cmd;
			document.body.appendChild(tmpInput);
		}		
		document.getElementsByName("SAVE_"+gameMsgObj.Cmd)[0].value = ""+gameMsgObj.Data;
		switch (gameMsgObj.Cmd) {
		case GET_KEEP_PLAY:			
			writeResponse("收到繼續玩請求 : " + gameMsgObj.Data);
			break;
		case GET_PUNCHE:
			writeResponse("收到出拳請求 : " + gameMsgObj.Data);
			break;
		case WAIT_ROOM:
			writeResponse("等待遊戲室 : " + gameMsgObj.Data);
			break;
		case IN_LOBBY:
			writeResponse("正在遊戲大廳 : " + gameMsgObj.Data);
			break;
		case CONNECT_SUCCESS:
			writeResponse("連結遊戲大廳成功 : " + gameMsgObj.Data);
			break;
		case RECONNECT_SUCCESS:
			writeResponse("重新連結遊戲大廳成功 : " + gameMsgObj.Data);
			break;
		case WAIT_CONTINUE:
			writeResponse("是否繼續 : " + gameMsgObj.Data);
			break;
		case CMD_NO_WINNER:
			writeResponse("平手 ");
			break;
		case WINNER:
			writeResponse("這把勝 : " + gameMsgObj.Data);
			break;
		case LOSER:
			writeResponse("這把負 : " + gameMsgObj.Data);
			break;
		case FINAL_WINNER:
			writeResponse("此局勝 : " + gameMsgObj.Data);
			break;
		case FINAL_LOSER:
			writeResponse("此局負 : " + gameMsgObj.Data);
			break;			
		case WAIT_VIDEO:
			writeResponse("影片時間 : " + gameMsgObj.Data);
			break;
		case WAIT_PUNCHE:
			writeResponse("請出拳 : " + gameMsgObj.Data);
			break;
		case GAME_START:
			writeResponse("遊戲開始 : " + gameMsgObj.Data);
			break;
		case ROUND:
			writeResponse("ROUND  " + gameMsgObj.Data);
			break;	
		case REAL_PLAYTIMES:
			writeResponse("實際次數 : " + gameMsgObj.Data);
			break;				
		case INTO_ROOM:
			writeResponse("加入遊戲室 : " + gameMsgObj.Data);
			break;
		case GET_LEAVE_ROOM:
			writeResponse("收到離開遊戲室cmd : " + gameMsgObj.Data);
			break;
		case MONEY:
			writeResponse("剩餘金額 : " + gameMsgObj.Data);
			break;
		case SET_ACCID_OK:
			writeResponse("ID設定OK : " + gameMsgObj.Data);
			break;
		case LAST_SERVER:
			selectServerByValue(""+gameMsgObj.Data);
			writeResponse("最後一次SERVER: " + gameMsgObj.Data);
			break;
		case SERVER_LIST:
			listGameServers(gameMsgObj.Data);
			break;
		case SERVER_FULL:
			writeResponse("該大廳滿了  " + gameMsgObj.Data);
			break;
		case PUNCHE_TIMEOUT:
			writeResponse("出拳時間結束  " + gameMsgObj.Data);
			break;
		case CONTINUE_TIMEOUT:
			writeResponse("等待繼續時間結束  " + gameMsgObj.Data);
			break;
		case VIDEO_TIMEOUT:
			writeResponse("影片時間結束  " + gameMsgObj.Data);
			break;
		case CONNECT_FAIL:
			writeResponse("ERROR =====" + msg);
			break;			
		case AUTO_PUNCHE:
			writeResponse("自動出拳  : " + gameMsgObj.Data);
			break;		
		case GAME_OVER:
			writeResponse("遊戲結束  : " + gameMsgObj.Data);
			break;
		case NO_MONEY:
			writeResponse("餘額不足  : " + gameMsgObj.Data);
			break;
		case DISCONNECT_GAME_SERVER:
			writeResponse("離開遊戲  : " + gameMsgObj.Data);
			break;
		case GET_LEAVE_GAME:
			writeResponse("收到離開遊戲  : " + gameMsgObj.Data);
			break;
		case GET_START_ROBOT:
			writeResponse("開始代管  : " + gameMsgObj.Data);
			break;
		case GET_STOP_ROBOT:
			writeResponse("停止代管  : " + gameMsgObj.Data);
			break;
		case NAME:
			writeResponse("我的帳號 : " + gameMsgObj.Data);
			break;
		case OPPONENT_NAME:
			writeResponse("對手帳號  : " + gameMsgObj.Data);
			break;
		case OPPONENT_MONEY:
			writeResponse("對手餘額  : " + gameMsgObj.Data);
			break;
		case WIN_TIMES:
			writeResponse("自己贏幾次  : " + gameMsgObj.Data);
			break;
		case OPPONENT_WIN_TIMES:
			writeResponse("對手贏幾次  : " + gameMsgObj.Data);
			break;
		case MAX_LIVE_POINT:
			writeResponse("總共生命值  : " + gameMsgObj.Data);
			break;
		case OPPONENT_GET_START_ROBOT:
			writeResponse("對手開始代管  : " + gameMsgObj.Data);
			break;
		case OPPONENT_GET_STOP_ROBOT:
			writeResponse("對手停止代管 : " + gameMsgObj.Data);
			break;
		case VIDEO_INFO:
			writeResponse("出拳結果 : " + gameMsgObj.Data);
			break;
		case ADD_MONEY:
			writeResponse("+ : " + gameMsgObj.Data);
			break;
		case SUB_MONEY:
			writeResponse("- : " + gameMsgObj.Data);
			break;
		case OPPONENT_ROLE:
			writeResponse("對手腳色 : " + gameMsgObj.Data);
			break;
		case GET_ROLE:
			writeResponse("設定腳色OK " + gameMsgObj.Data);
			break;
			
		default:
			writeResponse("ERROR =====" + msg);
			break;
		}
	}
	
	function selectServerByValue(value){
		selectItemByValue("serverSelect",value);
	}
	
	function listGameServers(serverList) {
		if (serverList.length > 0) {
			removeAllOption("serverSelect");
			addOptionNoDup("serverSelect", "請選擇 ", "" );
			for (var i = 0; i < serverList.length; i++) {				
				var text = serverList[i].name + '_ ( '+ serverList[i].current+ ' / '+	serverList[i].max + ' ) _ 金額  : ' + serverList[i].bet +' _類型 : '+ serverList[i].type ;
				addOptionNoDup("serverSelect", text, serverList[i].name );
			}
		}
	}	
	
	function startRobot(){
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = START_ROBOT;
		obj.AccId = document.getElementById("accIdInput").value;
		obj.Data = "";
		gameServerWS.send(JSON.stringify(obj));
	}
	
	function stopRobot(){
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = STOP_ROBOT;
		obj.AccId = document.getElementById("accIdInput").value;
		obj.Data = "";
		gameServerWS.send(JSON.stringify(obj));
	}
	
	function setAccId(){
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = SET_ACC_ID;
		obj.Data = document.getElementById("accIdInput").value;
		gameServerWS.send(JSON.stringify(obj));
	}
	
	function getLastServer(){
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = GET_LAST_SERVER;
		obj.Data = document.getElementById("accIdInput").value;

		gameServerWS.send(JSON.stringify(obj));
	}

	function getGameServerList() {
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = GET_SERVER_LIST;
		obj.AccId = document.getElementById("accIdInput").value;
		obj.Data = "";
		gameServerWS.send(JSON.stringify(obj));
	}
	
	function setRole() {
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = SET_ROLE;
		obj.AccId = document.getElementById("accIdInput").value;
		var select = document.getElementById("roleSelect");
		selectValue = select.options[select.selectedIndex].value;
		obj.Data = selectValue;
		gameServerWS.send(JSON.stringify(obj));
	}

	function joinGame() {
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = JOIN;
		obj.AccId = document.getElementById("accIdInput").value;
		var select = document.getElementById("serverSelect");
		selectValue = select.options[select.selectedIndex].value;
		obj.Data = selectValue;
		gameServerWS.send(JSON.stringify(obj));
	}

	function matchRoom() {
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = MATCH;
		obj.AccId = document.getElementById("accIdInput").value;
		obj.Data = "";
		gameServerWS.send(JSON.stringify(obj));
	}

	function leaveRoom() {
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = LEAVE;
		obj.AccId = document.getElementById("accIdInput").value;
		obj.Data = "";
		gameServerWS.send(JSON.stringify(obj));
	}
	
	function leaveGame() {
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = LEAVE_GAME;
		obj.AccId = document.getElementById("accIdInput").value;
		obj.Data = "";
		gameServerWS.send(JSON.stringify(obj));
	}

	function sendPaper() {
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = PUNCHE;
		obj.AccId = document.getElementById("accIdInput").value;
		obj.Data = PAPER;
		gameServerWS.send(JSON.stringify(obj));
	}

	function sendScissor() {
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = PUNCHE;
		obj.AccId = document.getElementById("accIdInput").value;
		obj.Data = SCISSOR;
		gameServerWS.send(JSON.stringify(obj));
	}

	function sendRock() {
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = PUNCHE;
		obj.AccId = document.getElementById("accIdInput").value;
		obj.Data = ROCK;
		gameServerWS.send(JSON.stringify(obj));
	}

	function sendKeepPlay(state) {
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = KEEP_PLAY;
		obj.AccId = document.getElementById("accIdInput").value;
		if (state == 1) {
			obj.Data = "1";
		} else {
			obj.Data = "2";
		}
		gameServerWS.send(JSON.stringify(obj));
	}

	function closeGameSocket() {
		gameServerWS.close();
	}

	function writeResponse(text) {
		var messages = document.getElementById("messages");
		messages.innerHTML += "<br/>" + text;
		messages.scrollTop = 9999999;
	}