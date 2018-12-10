/**
 * @file puncheGame.js
 * @author Quanto Lin
 * @version 1.0
 */


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
	const  SISSOR = "125";
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
	
	const  PING = "201";
	const  PONG = "202";
	const  WAIT_OPPONENT_CONTINUE = "204";

	const SET_SHUTDOWN_TIME = "205";
	const GET_SHUTDOWN_TIME_OK = "206";
	const SET_SHUTDOWN_TIME_NOW = "207";
	const GET_SHUTDOWN_TIME_FAIL = "208";
	
	const PUNCHE_INFO = "210";
	
	
	var PLAYER_STATUS = "0";
	
	const NOT_PLAY_GAME = "0";
	const PLAY_GAME = "1";

	
	var ajaxCount = 282;
	var isFirstTimeLoadingImg = true;
	
	 /**
	   * The Tag definition.
	   *
	   * @param {Array} preloadImageArray -  Global array to store image there is many array for different animation.
	   * @param {Number} index - index of array to load image.
	   * @param {String} imgUrl - url of image.
	   */
	function imgLoad(preloadImageArray, index, imgUrl) {
		var test_url = window.URL || window.webkitURL;
		if (test_url != null && test_url != undefined) {
			test_url.createObjectURL;
			var XHR9 = createXMLHttpRequest();
			if (XHR9 != null && XHR9 != undefined) {
				XHR9.open('GET', imgUrl, true);
				XHR9.responseType = 'blob';
				XHR9.onreadystatechange = function() {
					if (XHR9.readyState == 4) {
						if (XHR9.status === 200) {												
							var url2 = window.URL || window.webkitURL;
							try {
								if(preloadImageArray[index][0] != true){
									var tmpImg = new Image();
									tmpImg.src = url2.createObjectURL(XHR9.response);
									preloadImageArray[index][2] = tmpImg;
									preloadImageArray[index][0] = true;
									ajaxCount = ajaxCount - 1;
									if(ajaxCount <= 0 ){
										getEle("alreadyLoadingPlayerRole").value = 1;
									}
								}
							} catch (e) {
							}
						}
					}
				};
				XHR9.send();
			}
		}
	}

	/**
	   * Sent heart beat to websocket server.
	   */
	function sentHeartbeat() {
		if (heartBeatEnable == true) {
			var obj = new Object;
			obj.From = wsClientId;
			obj.Cmd = HEART_BEAT;
			obj.AccId = getEle("accId").value;
			obj.TokenId = getEle("tokenId").value;
			obj.Data = "";
			gameServerWS.send(JSON.stringify(obj));
			setTimeout("sentHeartbeat()", 1000);
		}
	}

	/**
	   * The start point of hearbeat.
	   */
	function startHeartbeat() {
		if (heartBeatEnable == false) {
			heartBeatEnable = true;
			setTimeout("sentHeartbeat()", 5000);
		}
	}

	/**
	   * Stop heartbeat.
	   */
	function stopHeartbeat() {
		heartBeatEnable = false;
	}

	/**
	   * Config websocket and connect to websocket server.
	   */
	function openGameSocket() {
		if (gameServerWS !== undefined
				&& gameServerWS.readyState !== gameServerWS.CLOSED) {
			writeResponse("gameServerWS is already opened.");
			return;
		}
		var webSocketId = getEle("accId").value;
		wsClientId = "client" + webSocketId + new Date().getTime();
		writeResponse("開啟WS : " + wsClientId);
		gameServerWS = new WebSocket("ws://"
				//+ getEle("gameServerIP").value
				+ location.host
				+ "/CTT02WebSocketServer/gameWS?" + wsClientId);
		gameServerWS.onopen = function(event) {
			console_Log("gameServerWS onopen");
			setAccId();
			startHeartbeat();
		};
		gameServerWS.onmessage = function(event) {
			console_Log("gameServerWS onmessage");
			if (event.data === undefined)
				return;
			handleGameSocketMsg(event.data);
		};
		gameServerWS.onclose = function(event) {
			console_Log("gameServerWS onclose");
			writeResponse("gameWS closed");
			stopHeartbeat();
			if(getEle("isTokenTimeOut").value == "0"){
				restartWebSocket();
			}
		};
	}
	
	/**
	   * Handle all websocket msg here. the msg include all cmds of websocket server.
	   *
	   * @param {String} msg - a json string with a command form websocket server.
	   */
	function handleGameSocketMsg(msg) {		
		if (isJSON(msg)){
			var gameMsgObj = JSON.parse(msg);
			switch (gameMsgObj.Cmd) {
			case GET_KEEP_PLAY:			
				cleanGamePage();
				appear("playerRoleInGame");
				disAppear("opponentInfoDiv");
				appear("waitOpponentDiv");
				writeResponse("收到繼續玩請求 : " + gameMsgObj.Data);
				break;
			case GET_PUNCHE:
				appear("selectPuncheDiv"); 
				getEle("playerPunche").value = gameMsgObj.Data;
				var playerPunche = getEle("playerPunche").value;
				closeGamePuncheActive();
				if(playerPunche == PAPER){
					getEle("clickSelectPaper").classList.add("active");
				}else if(playerPunche == ROCK){
					getEle("clickSelectRock").classList.add("active");
				}else if(playerPunche == SISSOR){
					getEle("clickSelectSissor").classList.add("active");
				}			
				
				writeResponse("收到出拳請求 : " + gameMsgObj.Data);
				break;
			case WAIT_ROOM:
				appear("matchMsgDiv");
				appear("waitOpponentDiv");
				writeResponse("等待遊戲室 : " + gameMsgObj.Data);
				break;
			case IN_LOBBY:
				appear("matchMsgDiv");
				appear("waitOpponentDiv");
				writeResponse("正在遊戲大廳 : " + gameMsgObj.Data);
				break;
			case CONNECT_SUCCESS:
				var role = getEle("realRoleNumber").value;
				if(role != "0"){
					setRole(role);
				}
				writeResponse("連結遊戲大廳成功 : " + gameMsgObj.Data);
				break;
			case RECONNECT_SUCCESS:
				var role = getEle("realRoleNumber").value;
				if(role != "0"){
					setRole(role);
				}
				writeResponse("重新連結遊戲大廳成功 : " + gameMsgObj.Data);
				break;
			case WAIT_CONTINUE:
				getEle("countdownTitle").innerHTML = "是否繼續";
				getEle("countdownValue").innerHTML = gameMsgObj.Data;
				appear("countdownDiv");
				appear("gameRelust");
				writeResponse("是否繼續 : " + gameMsgObj.Data);
				break;
			case CMD_NO_WINNER:
				writeResponse("平手 ");
				var playerPunche = getEle("playerPunche").value;
				if(playerPunche == PAPER){
					getEle("playerFight").src = getEle("defaultPaper").src;
					getEle("opponentFight").src = getEle("defaultPaper").src;
				}else if(playerPunche == ROCK){
					getEle("playerFight").src = getEle("defaultRock").src;
					getEle("opponentFight").src = getEle("defaultRock").src;
				}else if(playerPunche == SISSOR){
					getEle("playerFight").src = getEle("defaultSissor").src;
					getEle("opponentFight").src = getEle("defaultSissor").src;
				}				
				appear("fightDiv");
				
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
				getEle("countdownTitle").innerHTML = "請出拳";
				getEle("countdownValue").innerHTML = gameMsgObj.Data;
				appear("countdownDiv");
				appear("selectPuncheDiv"); 
				disAppear("fightDiv");
				writeResponse("請出拳 : " + gameMsgObj.Data);
				break;
			case GAME_START:
				showComBtn();
				getEle("changeCash").style.display = "none";
				disAppear("matchMsgDiv");
				disAppear("waitOpponentDiv");
				disAppear("opponentBtnComputer");
				if(gameMsgObj.Data != 0){
					appear("RoundNumber");
					getEle("RoundNumber").innerHTML = "比賽開始";
				}else{
					getEle("RoundNumber").innerHTML = "";
					disAppear("RoundNumber");
				}
				writeResponse("遊戲開始 : " + gameMsgObj.Data);
				break;
			case ROUND:
				closeGamePuncheActive();
				var jsonObj = JSON.parse(gameMsgObj.Data);
				if(jsonObj.Timer != 0){
					appear("RoundNumber");
					getEle("RoundNumber").innerHTML = "ROUND" + jsonObj.Count;
				}else{
					getEle("RoundNumber").innerHTML = "";
					disAppear("RoundNumber");
				}
				writeResponse("ROUND  " + gameMsgObj.Data);
				break;	
			case REAL_PLAYTIMES:
				writeResponse("實際次數 : " + gameMsgObj.Data);
				break;				
			case INTO_ROOM:
				writeResponse("加入遊戲室 : " + gameMsgObj.Data);
				break;
			case GET_LEAVE_ROOM:
				if(getEle("roomSetStatus").value == "1"){
					getEle("roomSetStatus").value = "0";
					showNoOpponent();
				}
				writeResponse("收到離開遊戲室cmd : " + gameMsgObj.Data);
				break;
			case MONEY:
				getEle("playerCashDiv").innerHTML = checkInputDecimalVal(gameMsgObj.Data);
				writeResponse("剩餘金額 : " + gameMsgObj.Data);
				break;
			case SET_ACCID_OK:
				writeResponse("ID設定OK : " + gameMsgObj.Data);
				getGameServerList();
				break;
			case LAST_SERVER:
				if(gameMsgObj.Data != "NULL"){
					if(checkLastServerId( gameMsgObj.Data)){
						getEle("lastGameServer").value = gameMsgObj.Data;
						appear("noticgoBackeDiv");
						appear("maskId");
						checkWebSocket();
					}else{
						disAppear("noticgoBackeDiv");
						disAppear("maskId");
					}
				}
				if(isFirstTimeLoadingImg){
					loadImg();
				}
								
				writeResponse("最後一次SERVER: " + gameMsgObj.Data);
				break;
			case SERVER_LIST:
				writeResponse("LIST :　"+gameMsgObj.Data);	
				listGameServers(gameMsgObj.Data);
				getLastServer();
				break;
			case SERVER_FULL:
				writeResponse("該大廳滿了  " + gameMsgObj.Data);
				break;
			case PUNCHE_TIMEOUT:
				getEle("countdownTitle").innerHTML = "";
				getEle("countdownValue").innerHTML = "";
				disAppear("countdownDiv");			
				disAppear("selectPuncheDiv");		
				writeResponse("出拳時間結束  " + gameMsgObj.Data);
				break;
			case CONTINUE_TIMEOUT:
				getEle("countdownTitle").innerHTML = "";
				getEle("countdownValue").innerHTML = "";
				disAppear("countdownDiv");	
				disAppear("gameRelust");			
				writeResponse("等待繼續時間結束  " + gameMsgObj.Data);
				break;
			case VIDEO_TIMEOUT:
				disAppear("fightDiv");
				writeResponse("影片時間結束  " + gameMsgObj.Data);
				break;
			case CONNECT_FAIL:
				showMessageNotBntWindows("閒置時間過長，請重新登入");
				getEle("isTokenTimeOut").value = "1";
				closeGameSocket();
				writeResponse("ERROR =====" + msg);
				break;			
			case AUTO_PUNCHE:
				getEle("playerPunche").value = gameMsgObj.Data;
				writeResponse("自動出拳  : " + gameMsgObj.Data);
				break;		
			case GAME_OVER:
				closeGamePuncheActive();
				writeResponse("遊戲結束  : " + gameMsgObj.Data);
				break;
			case NO_MONEY:
				cleanIndexPage();
				cleanGamePage();	
				showBalanceInsufficient();
				writeResponse("餘額不足  : " + gameMsgObj.Data);
				break;
			case DISCONNECT_GAME_SERVER:
				cleanIndexPage();
				cleanGamePage();
				getEle("inGameing").value = 0;
				writeResponse("離開遊戲  : " + gameMsgObj.Data);
				break;
			case GET_LEAVE_GAME:
				writeResponse("收到離開遊戲  : " + gameMsgObj.Data);
				break;
			case GET_START_ROBOT:
				closeComDiv();
				writeResponse("開始代管  : " + gameMsgObj.Data);
				break;
			case GET_STOP_ROBOT:
				showComBtn();
				writeResponse("停止代管  : " + gameMsgObj.Data);
				break;
			case NAME:
				getEle("playerNameDiv").innerHTML = gameMsgObj.Data;
				writeResponse("我的帳號 : " + gameMsgObj.Data);
				break;
			case OPPONENT_NAME:
				appear("opponentInfoDiv");
				getEle("opponentNameDiv").innerHTML = gameMsgObj.Data;
				disAppear("opponentBtnComputer");
				writeResponse("對手帳號  : " + gameMsgObj.Data);
				break;
			case OPPONENT_MONEY:		
				appear("opponentInfoDiv");
				getEle("opponentCashDiv").innerHTML = checkInputDecimalVal(gameMsgObj.Data);
				writeResponse("對手餘額  : " + gameMsgObj.Data);
				break;
			case WIN_TIMES:
				handleLifePointPlayerWin(gameMsgObj.Data);
				writeResponse("自己贏幾次  : " + gameMsgObj.Data);
				break;
			case OPPONENT_WIN_TIMES:
				handleLifePointOpponentWin(gameMsgObj.Data);
				writeResponse("對手贏幾次  : " + gameMsgObj.Data);
				break;
			case MAX_LIVE_POINT:
				handleLifePoint(gameMsgObj.Data);
				appear("playerRoundArea");
				appear("opponentRoundArea");
				writeResponse("總共生命值  : " + gameMsgObj.Data);
				break;
			case OPPONENT_GET_START_ROBOT:			
				appear("opponentBtnComputer");
				writeResponse("對手開始代管  : " + gameMsgObj.Data);
				break;
			case OPPONENT_GET_STOP_ROBOT:
				disAppear("opponentBtnComputer");
				writeResponse("對手停止代管 : " + gameMsgObj.Data);
				break;
			case VIDEO_INFO:				
				var jsonObj = JSON.parse(gameMsgObj.Data);							
				initAni(jsonObj.videoSide);
				if(jsonObj.videoSide == 0){
					disAppear("fightDiv");	
					playAni(0, 0, jsonObj.RolePlayer);
				}else if(jsonObj.videoSide == 1){
					disAppear("fightDiv");	
					playAni(0, 1, jsonObj.RoleOpp);
				}				
				writeResponse("動畫資訊 : " + gameMsgObj.Data);
				break;
			case PUNCHE_INFO:
				var jsonObj = JSON.parse(gameMsgObj.Data);
				if(jsonObj.puncheSelf == PAPER){
					getEle("playerFight").src = getEle("defaultPaper").src;
				}else if(jsonObj.puncheSelf == ROCK){
					getEle("playerFight").src = getEle("defaultRock").src;
				}else if(jsonObj.puncheSelf == SISSOR){
					getEle("playerFight").src = getEle("defaultSissor").src;
				}				
				if(jsonObj.puncheOpp == PAPER){
					getEle("opponentFight").src = getEle("defaultPaper").src;
				}else if(jsonObj.puncheOpp == ROCK){
					getEle("opponentFight").src = getEle("defaultRock").src;
				}else if(jsonObj.puncheOpp == SISSOR){
					getEle("opponentFight").src = getEle("defaultSissor").src;
				}
				appear("fightDiv");
				writeResponse("出拳結果 : " + gameMsgObj.Data);
				break;
			case ADD_MONEY:
				getEle("showResultText").innerHTML = "贏<span>" + gameMsgObj.Data +"</span>元";
				getEle("changeCash").innerHTML = " + " +gameMsgObj.Data;
				writeResponse("+ : " + gameMsgObj.Data);			
				getEle("changeCash").style.display = "block";
				break;
			case SUB_MONEY:
				getEle("showResultText").innerHTML = "輸<span>" + gameMsgObj.Data +"</span>元";
				getEle("changeCash").innerHTML = " - " +gameMsgObj.Data;
				writeResponse("- : " + gameMsgObj.Data);
				getEle("changeCash").style.display = "block";
				break;
			case OPPONENT_ROLE:
				appear("opponentRoleInGame");
				var tmpId = "selectRole_" + gameMsgObj.Data;
				var tmpId2 = "selectRole_" + gameMsgObj.Data + "_2";
				getEle("opponentRoleImg").src = getEle(tmpId).src;
				getEle("opponentRoleDiv").src = getEle(tmpId2).src ;
				writeResponse("對手腳色 : " + gameMsgObj.Data);
				break;
			case GET_ROLE:
				matchRoom();
				writeResponse("設定腳色OK " + gameMsgObj.Data);
				break;
			case WAIT_OPPONENT_CONTINUE:
				writeResponse("等待對手選擇是否繼續 ");
				break;
			default:
				writeResponse("ERROR =====" + msg);
				break;
			}
		}else{
		    console_Log(msg);
		}		
	}
	
	var roleNum = ["11", "12", "13", "14", "15", "21", "22", "23", "24", "25", "31", "32", "33", "34", "35"];
	var direct = ["left", "right"];
	var playerVideo = new Array(15);
	var playerBack = new Array(3);
	var playerEff = new Array(15);
	var reloadCount = 0;
	
	/**
	   * When game page on load, it will loading image async by this function, to avoid too long to load all of files.
	   * there are many array for different animations.
	   */
	function loadImg(){		
		reloadCount = 5;
		for(var i = 0; i < 15; i++){
			playerVideo[roleNum[i]] = [];
			for(var j = 0; j< 2; j++){
				playerVideo[roleNum[i]][j] = [];
				playerVideo[roleNum[i]][j][0] = false;
//				playerVideo[roleNum[i]][j][1] = "./img/ani/role/"+roleNum[i]+"/"+direct[j]+"/role.png";
				playerVideo[roleNum[i]][j][1] = "./img/ani/role/"+roleNum[i]+"/"+direct[j]+"/1.png";
				imgLoad(playerVideo[roleNum[i]],j,playerVideo[roleNum[i]][j][1]);	
			}
		}		
		for(var i = 1; i <= 3; i++){
			playerBack[i] = [];
			for(var j = 0; j< 2; j++){
				playerBack[i][j] = [];
				for(var k = 1; k<3; k++){
					playerBack[i][j][k] = [];
					playerBack[i][j][k][0] = false;
					playerBack[i][j][k][1] = "./img/ani/bg/"+i+"/"+direct[j]+"/"+k+".png";
					imgLoad(playerBack[i][j],k,playerBack[i][j][k][1]);	
				}				
			}
		}		
		for(var i = 0; i < 15; i++){
			playerEff[roleNum[i]] = [];
			for(var j = 0; j< 2; j++){
				playerEff[roleNum[i]][j] = [];
				for(var k = 1; k<9; k++){
					playerEff[roleNum[i]][j][k] = [];
					playerEff[roleNum[i]][j][k][0] = false;
					playerEff[roleNum[i]][j][k][1] = "./img/ani/effect/"+roleNum[i]+"/"+direct[j]+"/"+k+".png";
					imgLoad(playerEff[roleNum[i]][j],k, playerEff[roleNum[i]][j][k][1]);	
				}				
			}
		}
		isFirstTimeLoadingImg = false;
		setTimeout(function(){
			reloadAniImg();
		},3000);
	}
	/**
	   * To reload images for which is load fail by "loadImg".
	   */
	function reloadAniImg(){
		if(getEle("alreadyLoadingPlayerRole").value != 1 && reloadCount > 0){
			for(var i = 0; i < 15; i++){
				playerVideo[roleNum[i]] = [];
				for(var j = 0; j< 2; j++){
					if(playerVideo[roleNum[i]][j][0] != true){
						imgLoad(playerVideo[roleNum[i]],j,playerVideo[roleNum[i]][j][1]);	
					}
				}
			}		
			for(var i = 1; i <= 3; i++){
				playerBack[i] = [];
				for(var j = 0; j< 2; j++){
					playerBack[i][j] = [];
					for(var k = 1; k<3; k++){
						if(playerBack[i][j][k][0] != true){
							imgLoad(playerBack[i][j],k,playerBack[i][j][k][1]);	
						}
					}				
				}
			}		
			for(var i = 0; i < 15; i++){
				playerEff[roleNum[i]] = [];
				for(var j = 0; j< 2; j++){
					playerEff[roleNum[i]][j] = [];
					for(var k = 1; k<9; k++){
						if(playerEff[roleNum[i]][j][k][0] != true){
							imgLoad(playerEff[roleNum[i]][j],k, playerEff[roleNum[i]][j][k][1]);	
						}
					}				
				}
			}
			
			reloadCount--;
			
			setTimeout(function(){
				reloadAniImg();
			},3000);
		}
		else{
			reloadCount = 0;
		}
	}
	
	var aniTime = 100;
	
	var playVideoArray = new Array(28);
	playVideoArray[0] = -600;
	playVideoArray[1] = -600;
	playVideoArray[2] = -190;
	playVideoArray[3] = -150;
	playVideoArray[4] = -130;
	playVideoArray[5] = -100;
	playVideoArray[6] = 0;
	playVideoArray[7] = 0;
	playVideoArray[8] = 0;
	playVideoArray[9] = 0;
	playVideoArray[10] = 0;
	playVideoArray[11] = 0;
	playVideoArray[12] = 0;
	playVideoArray[13] = 0;
	playVideoArray[14] = 0;
	playVideoArray[15] = 0;
	playVideoArray[16] = 0;
	playVideoArray[17] = 0;
	playVideoArray[18] = 0;
	playVideoArray[19] = 0;
	playVideoArray[20] = 0;
	playVideoArray[21] = 0;
	playVideoArray[22] = 0;
	playVideoArray[23] = 0;
	playVideoArray[24] = 0;
	playVideoArray[25] = 0;
	playVideoArray[26] = 0;
	playVideoArray[27] = 0;
	
	var opponentVideoArray = new Array(28);
	opponentVideoArray[0] = 600;
	opponentVideoArray[1] = 600;
	opponentVideoArray[2] = 190;
	opponentVideoArray[3] = 150;
	opponentVideoArray[4] = 130;
	opponentVideoArray[5] = 100;
	opponentVideoArray[6] = 0;
	opponentVideoArray[7] = 0;
	opponentVideoArray[8] = 0;
	opponentVideoArray[9] = 0;
	opponentVideoArray[10] = 0;
	opponentVideoArray[11] = 0;
	opponentVideoArray[12] = 0;
	opponentVideoArray[13] = 0;
	opponentVideoArray[14] = 0;
	opponentVideoArray[15] = 0;
	opponentVideoArray[16] = 0;
	opponentVideoArray[17] = 0;
	opponentVideoArray[18] = 0;
	opponentVideoArray[19] = 0;
	opponentVideoArray[20] = 0;
	opponentVideoArray[21] = 0;
	opponentVideoArray[22] = 0;
	opponentVideoArray[23] = 0;
	opponentVideoArray[24] = 0;
	opponentVideoArray[25] = 0;
	opponentVideoArray[26] = 0;
	opponentVideoArray[27] = 0;
	
	
	 /**
	   * Init the canvas location.
	   *
	   * @param {Number} side -  left or right of canvas (player or opponent).
	   */
	function initAni(side){
		if(side == 0){
			appear("playerAniDiv");
			var canvas = document.getElementById("aniPlayer");
			canvas.width = "550";
			canvas.height = "170";
		}else if(side == 1){
			appear("opponentAniDiv");
			var canvas = document.getElementById("aniGuest");
			canvas.width = "550";
			canvas.height = "170";
		}	
	}
	
	/**
	   * on click select role "next".
	   */
	function selectRoleTurnNext(){
		checkAccTokenTimeOutAjax();
		var now = parseInt(getEle("selectRoleNumber").value);
		if(now%10 < 5){
			getEle("selectRoleNumber").value = now +1;
		}else{
			getEle("selectRoleNumber").value = now -4;
		}
		showSelectRole();
	}
	
	/**
	   * on click select role "prev".
	   */
	function selectRoleTurnPrev(){
		checkAccTokenTimeOutAjax();
		var now = parseInt(getEle("selectRoleNumber").value);
		if(now%10 > 1){
			getEle("selectRoleNumber").value = now - 1;
		}else{
			getEle("selectRoleNumber").value = now + 4;
		}
		showSelectRole();
	}
	
	/**
	   * on click select role "confirm".
	   * and goto main page after 0.5sec.
	   */
	function selectRoleConfirm(){
		modifyRole();
		getEle("realRoleNumber").value = getEle("selectRoleNumber").value;
		setTimeout( "openIndex()", 500);
	}
	
	XHR1 = null;
	
	/**
	   * if player select a new role, game page sent a request to action "setInfo" for updating.
	   */
	function modifyRole() {
		if (XHR1 == null || XHR1 == undefined) {
			XHR1 = createXMLHttpRequest();
		}
		if (XHR1 != null && XHR1 != undefined) {
			var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&accId="+encodeURIComponent(getEle("accId").value)+"&role="+encodeURIComponent(getEle("selectRoleNumber").value);
			XHR1.timeout = 10000;
			XHR1.ontimeout = function() {
				XHR1.abort();
			}
			XHR1.onerror = function() {
				XHR1.abort();
			}
			XHR1.onreadystatechange = handleChangeInfo;
			XHR1.open("POST", "./GamePage!setInfo.php?date="
					+ new Date().getTime(), true);
			XHR1.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			XHR1.send(tmpstr);
		}
	}
	
	XHR2 = null;	
	
	/**
	   * to get basic info of player.
	   */
	function getPlayerBasicInfo(){
		if(getEle("inGameing").value != 1){
			if (XHR2 == null || XHR2 == undefined) {
				XHR2 = createXMLHttpRequest();
			}
			if (XHR2 != null && XHR2 != undefined) {
				var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&accId="+encodeURIComponent(getEle("accId").value);
				XHR2.timeout = 10000;
				XHR2.ontimeout = function() {
					XHR2.abort();
				}
				XHR2.onerror = function() {
					XHR2.abort();
				}
				XHR2.onreadystatechange = handleBasicInfo;
				XHR2.open("POST", "./GamePage!getInfo.php?date="
						+ new Date().getTime(), true);
				XHR2.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				XHR2.send(tmpstr);
			}
		}		
	}
	
	function handleChangeInfo(){
		if (XHR1.readyState == 4) {
			if (XHR1.status == 200) {
				try{
					if(isJSON(XHR1.responseText)){
						var json = JSON.parse(XHR2.responseText);
						checkTokenIdfail(json);
					}
				}catch(error){
				}
				finally{
					XHR1.abort();
				}
			}
			else{
				XHR1.abort();
			}
		}
	}
	
	function handleBasicInfo(){
		if (XHR2.readyState == 4) {
			if (XHR2.status == 200) {
				try{
					if(isJSON(XHR2.responseText)){
						var json = JSON.parse(XHR2.responseText);
						checkTokenIdfail(json);
						var dataCount = 0;
						if("role" in json){
							dataCount ++;
							getEle("realRoleNumber").value = json.role;
							var tmpId = "selectRole_" + json.role;
							var tmpId2 = "selectRole_" + json.role + "_2";
							if(document.getElementById("indexSection").style.display != "none"){	
								getEle("playerRoleImg").src = getEle(tmpId).src ;
								getEle("playerRoleDiv").src = getEle(tmpId2).src ;
							}							
						}
						if("accName" in json){
							dataCount ++;
							getEle("playerNameDiv").innerHTML = json.accName;		
						}
						if("balance" in json){	
							getEle("playerCashDiv").innerHTML = checkInputDecimalVal(json.balance);
							if(json.balance > 0){
								dataCount ++;
							}
						}						
						if(dataCount != 3){
							setTimeout("getPlayerBasicInfo()", 1000);
						}					
					}
				}catch(error){
				}
				finally{
					XHR2.abort();
				}
			}
			else{
				XHR2.abort();
			}
		}
	}
	
	/**
	   * onclick change country (魏,蜀,吳)
	   *
	   * @param {Number} country -  the country which is selected.
	   */
	function selectRoleCountry(country){
		checkAccTokenTimeOutAjax();
		var CoumtryBgArr = ["/CTT02GamePage/img/bg_role_wei.png","/CTT02GamePage/img/bg_role_su.png","/CTT02GamePage/img/bg_role_wu.png"];
		getEle("countryBgColor").style.backgroundImage= "url("+CoumtryBgArr[toInt(country)-1]+")";
		getEle("selectRoleNumber").value = country * 10 + 1;
		showSelectRole();
	}
	
	var roleTitle = new Array(15);
	roleTitle["11"] = "曹操";
	roleTitle["12"] = "典韋";
	roleTitle["13"] = "夏侯惇";
	roleTitle["14"] = "張遼";
	roleTitle["15"] = "司馬懿";
	roleTitle["21"] = "劉備";
	roleTitle["22"] = "張飛";
	roleTitle["23"] = "關羽";
	roleTitle["24"] = "諸葛亮";
	roleTitle["25"] = "趙雲";
	roleTitle["31"] = "孫權";
	roleTitle["32"] = "周瑜";
	roleTitle["33"] = "甘寧";
	roleTitle["34"] = "小喬";
	roleTitle["35"] = "太史慈";
	
	var roleText = new Array(15);
	roleText["11"] = "<p>	<span>寧教我負天下人，</span>	<span>休教天下人負我。</span>	</p>";
	roleText["12"] = "<p>	<span>驅虎過澗，</span>	<span>運戟如飛。</span>	</p>";
	roleText["13"] = "<p>	<span>拔矢去眸枯一目，</span>	<span>啖睛忿氣喚雙親。</span>	</p>";
	roleText["14"] = "<p>	<span>古之召虎今猶在，</span>	<span>奇襲威震逍遙津。</span>	</p>";
	roleText["15"] = "<p>	<span>鷹潛幽谷窺帝氣，</span>	<span>狼顧中原度君心。</span>	</p>";
	//roleText["21"] = "<p>	<span>備若有基本，</span>	<span>天下碌碌之輩，</span>	<span>誠不足慮也。</span>	</p>";
	roleText["21"] = "<p>	<span>天下碌碌之輩，</span>	<span>誠不足慮也。</span>	</p>";
	roleText["22"] = "<p>	<span>探囊取物萬人敵，</span>	<span>據水斷橋退雄兵。</span>	</p>";
	roleText["23"] = "<p>	<span>青龍偃月訴忠義，</span>	<span>聲震華夏鑄威名。</span>	</p>";
	roleText["24"] = "<p>	<span>隆中一對天下計，</span>	<span>茅廬三顧臥龍心。</span>	</p>";
	roleText["25"] = "<p>	<span>血染征袍透甲紅，</span>	<span>當陽誰敢與爭鋒。</span>	</p>";
	roleText["31"] = "<p>	<span>夫存不忘亡，</span>	<span>安必慮危。</span>	</p>";
	roleText["32"] = "<p>	<span>周郎談笑檣櫓滅，</span>	<span>羽扇輕揮赤壁煙。</span>	</p>";
	roleText["33"] = "<p>	<span>鵝翎插盔肝膽熱，</span>	<span>百騎劫營氣縱橫。</span>	</p>";
	roleText["34"] = "<p>	<span>獨立雲橋望烟雨，</span>	<span>覓得江東如意郎。</span>	</p>";
	roleText["35"] = "<p>	<span>北海突圍慕高義，</span>	<span>神亭酣戰展雄才。</span>	</p>";
	
	
	/**
	   * Show role.
	   *
	   */
	function showSelectRole(){
		var now = parseInt(getEle("selectRoleNumber").value);
		var tmpId= "selectRole_" + now;
		var tmpId2= "selectRole_" + now + "_2";
		getEle("selectRoleImgId").src = getEle(tmpId).src;
		getEle("playerRoleDiv").src = getEle(tmpId2).src ;
		getEle("selectRoleTitleId").innerHTML = roleTitle[now];
		getEle("selectRoleTextId").innerHTML = roleText[now];
		
	}
	
	/**
	   * play animation
	   *
	   * @param {Number} index -  the current index of video, and it will add 1 then go to next image of the animation.
	   * @param {Number} side -  left or right of canvas (player or opponent).
	   * @param {Number} role -  role index.
	   */
	function playAni(index, side, role){
		var effectStartIndex = toInt(getEle("effectStartIndex").value);
		if(side == 0){
			var canvas = document.getElementById("aniPlayer");
			var ctx = canvas.getContext("2d");			
			ctx.clearRect(0, 0, canvas.width, canvas.height);
			var bgIndex = parseInt(role / 10);
			if(index %2 == 0){
				ctx.drawImage(playerBack[bgIndex][side][1][2], 0, 0);
			}else{
				ctx.drawImage(playerBack[bgIndex][side][2][2], 0, 0);
			}
			ctx.drawImage(playerVideo[role][side][2], playVideoArray[index], 0);		
			if(index > effectStartIndex &&  index < (effectStartIndex+8)){
				ctx.drawImage(playerEff[role][side][index - effectStartIndex+1][2], 0, 0);			
			}
			index = index +1;
			if(index < 28){
				setTimeout("playAni("+index+","+side+",'"+role+"');",aniTime);
			}else{
				ctx.clearRect(0, 0, canvas.width, canvas.height);
				disAppear("playerAniDiv");
				disAppear("opponentAniDiv");
				
			}
		}else{
			var canvas = document.getElementById("aniGuest");
			var ctx = canvas.getContext("2d");
			ctx.clearRect(0, 0, canvas.width, canvas.height);
			var bgIndex = parseInt(role / 10);
			if(index %2 == 0){
				ctx.drawImage(playerBack[bgIndex][side][1][2], 0, 0);
			}else{
				ctx.drawImage(playerBack[bgIndex][side][2][2], 0, 0);
			}
			ctx.drawImage(playerVideo[role][side][2], opponentVideoArray[index], 0);		
			if(index > effectStartIndex &&  index < (effectStartIndex+8)){
				ctx.drawImage(playerEff[role][side][index - effectStartIndex+1][2], 0, 0);			
			}
			index = index +1;
			if(index < 28){
				setTimeout("playAni("+index+","+side+",'"+role+"');",aniTime);
			}else{
				ctx.clearRect(0, 0, canvas.width, canvas.height);
				disAppear("playerAniDiv");
				disAppear("opponentAniDiv");
			}
		}		
	}
	
	/**
	   * kill the server DON'T TOUCH!!!!
	   */
	function stopServerNow(){
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = SET_SHUTDOWN_TIME_NOW;
		obj.AccId = getEle("accId").value;
		obj.TokenId = getEle("tokenId").value;
		obj.Data = "";
		gameServerWS.send(JSON.stringify(obj));
	}
	
	function selectServerByValue(value){
		selectItemByValue("serverSelect",value);
	}
	
	/**
	   * get a list of game server, which has connect to websocket server.
	   */
	function getServerListToObject(){		
		if(getEle("severList").value != ""){
			var jsonObj = JSON.parse(getEle("severList").value);
			return jsonObj;
		}
		return [];
	}
	
	function handleLifePoint(val){
		getEle("lifePoint").value = val;
		showLifePoint();
	}
	
	function handleLifePointPlayerWin(val){
		getEle("lifePointPlayer").value = val;
		showLifePoint();
	}
	
	function handleLifePointOpponentWin(val){
		getEle("lifePointOpponent").value = val;
		showLifePoint();
	}

	/**
	   *  refresh LifePoint.
	   */
	function showLifePoint(){
		var widthOfLife = 100;		
		var percentNum = 0;
		
		if(toInt(getEle("lifePoint").value) > 0){
			percentNum = (100*1000000/toInt(getEle("lifePoint").value))/1000000;
		}
		
		for(var i = 0; i< parseInt(getEle("lifePointPlayer").value); i++){
			widthOfLife -= percentNum;
		}
		if(toInt(getEle("lifePointPlayer").value) == toInt(getEle("lifePoint").value)){
			widthOfLife = 0;
		}
		
		getEle("percentOfOpponent").style.width = widthOfLife +"%";
		
		widthOfLife = 100;
		for(var i = 0; i< parseInt(getEle("lifePointOpponent").value); i++){
			widthOfLife -= percentNum;
		}
		if(toInt(getEle("lifePointOpponent").value) == toInt(getEle("lifePoint").value)){
			widthOfLife = 0;
		}
		getEle("percentOfPlayer").style.width = widthOfLife +"%";
	}
	
	/**
	   * this function resort the server list of all game server to map
	   * and the return map will divied those servers by type and money.
	   *
	   * @param {Array} serverList -  the list from websocket server.
	   */
	function convertSeverListToObject(serverList){
		var gameServerMapOuter = [];		
		if (serverList.length > 0) {
			for (var i = 0; i < serverList.length; i++) {								
				var isInListOuter = false;
				for(var j = 0; j< gameServerMapOuter.length; j++){
					if(gameServerMapOuter[j].type == serverList[i].type){							
						isInListOuter = true;
					}
				}
				if(!isInListOuter){
					var gameServerMapInner = new Object();
					gameServerMapInner.type = serverList[i].type;
					gameServerMapInner.betList = [];
					gameServerMapOuter[gameServerMapOuter.length] = gameServerMapInner;
				}				
			}			
			for (var i = 0; i < serverList.length; i++) {
				for(var j = 0; j< gameServerMapOuter.length; j++){
					if(gameServerMapOuter[j].type == serverList[i].type){							
						var isInListInner = false;
						for(var k = 0; k< gameServerMapOuter[j].betList.length; k++){
							if(gameServerMapOuter[j].betList[k].bet == serverList[i].bet){
								isInListInner = true;
							}
						}
						if(!isInListInner){
							var gameServerBetItem = new Object();
							gameServerBetItem.bet = serverList[i].bet;
							gameServerBetItem.serverList = [];
							gameServerMapOuter[j].betList[gameServerMapOuter[j].betList.length] = gameServerBetItem;
						}
					}
				}				
			}			
			for (var i = 0; i < serverList.length; i++) {						
				for(var j = 0; j< gameServerMapOuter.length; j++){
					if(gameServerMapOuter[j].type == serverList[i].type){								
						for(var k = 0; k< gameServerMapOuter[j].betList.length; k++){
							if(gameServerMapOuter[j].betList[k].bet == serverList[i].bet){
								var gameServerItem = new Object();
								gameServerItem.current = serverList[i].current;
								gameServerItem.max = serverList[i].max;
								gameServerItem.name = serverList[i].name;
								gameServerMapOuter[j].betList[k].serverList[gameServerMapOuter[j].betList[k].serverList.length] = gameServerItem;
								break;
							}
						}
						break;
					}
				}
			}
			for(var j = 0; j< gameServerMapOuter.length; j++){
				gameServerMapOuter[j].betList.sort( function(a,b){
					return (a.bet - b.bet);
				});
			}
			gameServerMapOuter.sort( function(a,b){
				return (a.type - b.type);
			});
		}
		return gameServerMapOuter;
	}
	
	/**
	   * this function will show the servers on the page.
	   *
	   * @param {Array} serverList -  the list from websocket server.
	   */
	function listGameServers(serverList) {
		 var list = convertSeverListToObject(serverList);		 
		 console_Log(list);
		 var str_type1_0 = '<a href="javascript:onClickSelectGameType(';
//		 var str_type1_1 = ')">	<h2>快速戰</h2>	</a>';
		 var str_type1_1 = ')" class="hall-1"> </a>';
		 var str_type3_0 = '<a href="javascript:onClickSelectGameType(';
//		 var str_type3_1 = ')">	<h2>三戰兩勝</h2>	</a>';
		 var str_type3_1 = ')" class="hall-2"> </a>';
		 var str_type5_0 = '<a href="javascript:onClickSelectGameType(';
//		 var str_type5_1 = ')">	<h2>五戰三勝</h2>	</a>';
		 var str_type5_1 = ')" class="hall-3"> </a>';
		 var typeDivHTML = "";
		 for(var i = 0; i< list.length; i++){
			 if(list[i].type == "1"){
				 typeDivHTML += str_type1_0 + i + str_type1_1;
			 } else if(list[i].type == "3"){
				 typeDivHTML += str_type3_0 + i + str_type3_1;
			 } else if(list[i].type == "5"){
				 typeDivHTML += str_type5_0 + i + str_type5_1 ;
			 }
		 }
		 getEle("gameTypeDiv").innerHTML = typeDivHTML;		 
		 var serverListJson = JSON.stringify(list);
		 getEle("severList").value = serverListJson;
	}	
	
	/**
	   * start robot
	   */
	function startRobot(){
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = START_ROBOT;
		obj.AccId = getEle("accId").value;
		obj.TokenId = getEle("tokenId").value;
		obj.Data = "";
		gameServerWS.send(JSON.stringify(obj));
	}
	
	/**
	   * stop robot
	   */
	function stopRobot(){
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = STOP_ROBOT;
		obj.AccId = getEle("accId").value;
		obj.TokenId = getEle("tokenId").value;
		obj.Data = "";
		gameServerWS.send(JSON.stringify(obj));
	}
	
	function setAccId(){
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = SET_ACC_ID;
		obj.Data = getEle("accId").value;
		gameServerWS.send(JSON.stringify(obj));
	}
	
	function getLastServer(){
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = GET_LAST_SERVER;
		obj.Data = getEle("accId").value;
		gameServerWS.send(JSON.stringify(obj));
	}

	function getGameServerList() {
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = GET_SERVER_LIST;
		obj.AccId = getEle("accId").value;
		obj.TokenId = getEle("tokenId").value;
		obj.Data = "";
		gameServerWS.send(JSON.stringify(obj));
	}
	
	function setRole(roleValue) {
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = SET_ROLE;
		obj.AccId = getEle("accId").value;
		obj.TokenId = getEle("tokenId").value;
		obj.Data = roleValue;
		gameServerWS.send(JSON.stringify(obj));
	}

	function joinGame(gameServer) {
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = JOIN;
		obj.AccId = getEle("accId").value;
		obj.TokenId = getEle("tokenId").value;
		obj.Data = gameServer;
		gameServerWS.send(JSON.stringify(obj));
	}

	function matchRoom() {
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = MATCH;
		obj.AccId = getEle("accId").value;
		obj.TokenId = getEle("tokenId").value;
		obj.Data = "";
		gameServerWS.send(JSON.stringify(obj));
	}

	function leaveRoom() {
		console_Log("sent LEAVE");
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = LEAVE;
		obj.AccId = getEle("accId").value;
		obj.TokenId = getEle("tokenId").value;
		obj.Data = "";
		gameServerWS.send(JSON.stringify(obj));
	}
	
	function leaveGame() {
		getEle("roomSetStatus").value = "0";
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = LEAVE_GAME;
		obj.AccId = getEle("accId").value;
		obj.TokenId = getEle("tokenId").value;
		obj.Data = "";
		gameServerWS.send(JSON.stringify(obj));
	}

	function sendPaper() {
		if(checkGamePuncheActive()){
			getEle("clickSelectPaper").classList.add("active");
		}
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = PUNCHE;
		obj.AccId = getEle("accId").value;
		obj.TokenId = getEle("tokenId").value;
		obj.Data = PAPER;
		gameServerWS.send(JSON.stringify(obj));
	}

	function sendScissor() {
		if(checkGamePuncheActive()){
			getEle("clickSelectSissor").classList.add("active");
		}
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = PUNCHE;
		obj.AccId = getEle("accId").value;
		obj.TokenId = getEle("tokenId").value;
		obj.Data = SISSOR;
		gameServerWS.send(JSON.stringify(obj));
	}

	function sendRock() {
		if(checkGamePuncheActive()){
			getEle("clickSelectRock").classList.add("active");
		}
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = PUNCHE;
		obj.AccId = getEle("accId").value;
		obj.TokenId = getEle("tokenId").value;
		obj.Data = ROCK;
		gameServerWS.send(JSON.stringify(obj));
	}
	
	function closeGamePuncheActive(){
		if(getEle("clickSelectPaper").classList.contains("active")){
			getEle("clickSelectPaper").classList.remove("active");
		}
		if(getEle("clickSelectSissor").classList.contains("active")){
			getEle("clickSelectSissor").classList.remove("active");
		}
		if(getEle("clickSelectRock").classList.contains("active")){
			getEle("clickSelectRock").classList.remove("active");
		}
		
	}
	function checkGamePuncheActive(){
		if(getEle("clickSelectPaper").classList.contains("active")){
			return false;
		}
		if(getEle("clickSelectSissor").classList.contains("active")){
			return false;
		}
		if(getEle("clickSelectRock").classList.contains("active")){
			return false;
		}
		return true;
	}

	function sendKeepPlay(state) {
		var obj = new Object;
		obj.From = wsClientId;
		obj.Cmd = KEEP_PLAY;
		obj.AccId = getEle("accId").value;
		obj.TokenId = getEle("tokenId").value;
		if (state == 1) {
			obj.Data = "1";
		} else if (state == 3) {
			obj.Data = "3";
		} else {
			obj.Data = "2";
		}
		gameServerWS.send(JSON.stringify(obj));
	}

	function closeGameSocket() {
		gameServerWS.close();
	}

	function writeResponse(text) {
		console_Log(text);
	}
	
	function onClickSelectGameType(typeIndex){
		getGameServerList();
		var scrClassArr = ["bet01","bet02","bet03"];
		var scrArr = ["/CTT02GamePage/img/icon_bet_5.png","/CTT02GamePage/img/icon_bet_10.png","/CTT02GamePage/img/icon_bet_20.png"];
		getEle("gameType").value = typeIndex;
		var tmpList = getServerListToObject();
		var list = tmpList[typeIndex].betList;
		var str_bet0 = '<a href="javascript:onclickSelectBet(';
		var str_bet1 = ');" class="';
		var str_bet2 = '"><img src="';
		var str_bet3 = '"><p>';
		var str_bet4 = '</p></a>';		
		var typeDivHTML = ""; 		 
		for(var i = 0; i< list.length; i++){
			typeDivHTML += str_bet0 + i + "," + list[i].bet + str_bet1 + scrClassArr[i] + str_bet2 + scrArr[i] + str_bet3 + list[i].bet + str_bet4;
		}		
		getEle("gameSection").style.backgroundImage="url("+getPlayGameBgUrl(typeIndex)+")";
		
		getEle("selectBetDivInner").innerHTML = typeDivHTML;
		jumpToSelectBet();
		
		clearGameTypeTital();
		if(typeIndex == 0){
			getEle("fastTital").classList.add("active");
		}
		else if(typeIndex == 1){
			getEle("threeTital").classList.add("active");
		}
		else if(typeIndex == 2){
			getEle("fiveTital").classList.add("active");
		}
	}
	
	function clearGameTypeTital(){
		if(getEle("fastTital").classList.contains("active")){
			getEle("fastTital").classList.remove("active");
		}
		if(getEle("threeTital").classList.contains("active")){
			getEle("threeTital").classList.remove("active");
		}
		if(getEle("fiveTital").classList.contains("active")){
			getEle("fiveTital").classList.remove("active");
		}
	}
	
	function jumpToSelectBet(){
		appear("selectBetDivOuter");
		openGame();
	}
	
	function startGame(bet){
		getEle("gameBet").value = bet;
		appear("selectBetDivOuter");
		var serverId = getServerIdByBet(bet);
		if(serverId == ""){
			showNotice();
			getEle("restartGameVal").value = "1";
			getGameServerList();
			restartGame(bet);
		}else{
			handleDivAndjoinGame(serverId);
		}
	}
	
	function onclickSelectBet(bet,betMin){
		if(toInt(getEle("playerCashDiv").innerHTML) < betMin){
			showBalanceInsufficient();
		}
		else{
			checkAccTokenTimeOutAjax();
			getEle("showLoadingPage").value = 1;
			getEle("roomSetStatus").value = "1";
			checkImgWhenClickBet(bet);
		}
		
	}
	
	function checkImgWhenClickBet(bet){
		if(getEle("alreadyLoadingPlayerRole").value == 1){
			disAppear("maskId");
			disAppear("loadingId");
			startGame(bet);
		}else if(getEle("showLoadingPage").value == 0){
			disAppear("maskId");
			disAppear("loadingId");
		}else{
			appear("maskId");
			appear("loadingId");
			setTimeout("checkImgWhenClickBet("+bet+");",100);
		}
	}
	
	function onclickGoBack(id){
		if(getEle("alreadyLoadingPlayerRole").value == 1){
			disAppear("maskId");
			disAppear("loadingId");
			handleDivAndjoinGame(id);
			
		}else{
			appear("maskId");
			appear("loadingId");
			setTimeout("onclickGoBack("+id+");",100);
		}
	}

	function handleDivAndjoinGame(id){
		cleanGamePage();
		openGame();
		appear("playerRoleInGame");
		disAppear("opponentInfoDiv");
		getEle("goBackAllBtn").innerHTML = '<a href="javascript:leaveGame()" class="back"  title = "返回"></a>';
		joinGame(id);
		getEle("inGameing").value = 1;
	}
	
	function getServerIdByBet(bet){
		var tmpList = getServerListToObject();
		var serverList = tmpList[getEle("gameType").value].betList[bet].serverList;
		var serverId = "";
		if((parseInt(getEle("gameType").value) < tmpList.length) && (tmpList.length > 0)){
			serverList = jsonDataSort(serverList,"max",false);
			for(var i = 0; i <serverList.length; i++){
				if(parseInt(serverList[i].max) > 0){
					var serverMaxPlayerPercentage = (parseInt(serverList[i].current)*100)/parseInt(serverList[i].max);
					if(parseInt(serverList[i].max) > parseInt(serverList[i].current) && serverMaxPlayerPercentage <= 95){
						serverId = serverList[i].name;
						break;
					}
					else if(parseInt(serverList[i].max)-parseInt(serverList[i].current) > 2){
						serverId = serverList[i].name;
						break;
					}
				}
			}
		}		
		return serverId;
	}
	
	function checkLastServerId(id){
		var gameServerMapOuter = getServerListToObject();
		for(var j = 0; j< gameServerMapOuter.length; j++){						
			for(var k = 0; k< gameServerMapOuter[j].betList.length; k++){
				for(var m = 0; m< gameServerMapOuter[j].betList[k].serverList.length; m++){
					if(gameServerMapOuter[j].betList[k].serverList[m].name == id){
						return true;
					}
				}
			}
		}	
		return false;
	}
	
	function goBackLastGame(){		
		var serverId = getEle("lastGameServer").value;
		disAppear("noticgoBackeDiv");
		disAppear("maskId");
		if(checkLastServerId(serverId)){
			var typeIndex = getServerGameType(serverId);
			if(typeIndex > 0){
				getEle("gameSection").style.backgroundImage="url("+getPlayGameBgUrl(typeIndex)+")";
			}
			onclickGoBack(serverId);
		}
	}
	
	function closeBackNotice(){
		disAppear("noticgoBackeDiv");
		disAppear("maskId");
	}
	
	function cleanGamePage(){
		closeGamePuncheActive();
		disAppear("matchMsgDiv");
		disAppear("waitOpponentDiv");
		disAppear("selectBetDivOuter");
		disAppear("playerRoleInGame");
		disAppear("opponentRoleInGame");
		disAppear("RoundNumber");
		disAppear("playerRoundArea");
		disAppear("opponentRoundArea");
		disAppear("countdownDiv");
		disAppear("selectPuncheDiv");
		disAppear("fightDiv");
		disAppear("playerAniDiv");
		disAppear("opponentAniDiv");
		disAppear("gameRelust");
		disAppear("btnComputer");
		disAppear("computerAgency");
	}
	
	function cleanIndexPage(){
		disAppear("opponentInfoDiv");
		disAppear("maskId");
		disAppear("loadingId");
		disAppear("btnComputer");
		disAppear("computerAgency");
		setTimeout(function(){ openIndex(); }, 500);		
	}
	
	var XHR5 = null;
	function checkAccTokenTimeOutAjax(){
		var accId = getEle("accId").value;
		var tokenId = getEle("tokenId").value;
		XHR5 = checkXHR(XHR5);
		if (typeof XHR5 != "undefined" && XHR5 != null) {
			var tmpStr = "tokenId=" + encodeURIComponent(tokenId)+"&accId=" + encodeURIComponent(accId);
			XHR5.open("POST", "./GamePage!checkAccTokenTimeOut.php?date="
					+ new Date().getTime(), true);
			XHR5.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			XHR5.timeout = 10000;
			XHR5.ontimeout = function() {
				XHR5.abort();
			}
			XHR5.onerror = function() {
				XHR5.abort();
			}
			XHR5.onreadystatechange = handleCheckAccTokenTimeOutAjax;
			XHR5.send(tmpStr);
		}
	}
	function handleCheckAccTokenTimeOutAjax(){
		if (XHR5.readyState == 4) {
			if (XHR5.status == 200) {
				try {
					if (isJSON(XHR5.responseText)) {
						var json = JSON.parse(XHR5.responseText);
						checkTokenIdfail(json);
					}
				} catch (error) {
					console_Log("handleCheckAccTokenTimeOutAjax error:"+error.message);
				} finally {
					XHR5.abort();
				}
			}
		}
	}
	function checkTokenIdfail(json) {
		if (json.tokenId != null && json.tokenId != undefined
				&& typeof json.tokenId != "undefined") {
			if (json.tokenId == "fail") {
				showMessageNotBntWindows("閒置時間過長，請重新登入");
				return false;
			}
			return true;
		} else {
			showMessageNotBntWindows("閒置時間過長，請重新登入");
			console_Log("tokenId is null");
			return false;
		}
	}
	
	function restartGame(bet){
		checkAccTokenTimeOutAjax();
		if(getEle("restartGameVal").value == "1"){
			var serverId = getServerIdByBet(bet);
			if(serverId == ""){
				getGameServerList();
				setTimeout(function(){restartGame(bet);},1000);
			}
			else{
				exitNotice();
				handleDivAndjoinGame(serverId);
			}
			
		}
	}
	function onclickRestartGameExit(){
		getEle("restartGameVal").value = "0";
		exitNotice();
	}
	
	function getServerGameType(id){
		var gameServerMapOuter = getServerListToObject();
		for(var j = 0; j< gameServerMapOuter.length; j++){						
			for(var k = 0; k< gameServerMapOuter[j].betList.length; k++){
				for(var m = 0; m< gameServerMapOuter[j].betList[k].serverList.length; m++){
					if(gameServerMapOuter[j].betList[k].serverList[m].name == id){
						console_Log(gameServerMapOuter[j]);
						console_Log(gameServerMapOuter[j].type);
						return j;
					}
				}
			}
		}	
		return 0;
	}
	
	function getPlayGameBgUrl(type){
		if(type == 0){
			return getEle("defaultPlayGameBg1").src;
		}
		else if(type == 1){
			return getEle("defaultPlayGameBg2").src;
		}
		else if(type == 2){
			return getEle("defaultPlayGameBg3").src;
		}
		return "";
	}
	
	function restartWebSocket(){
		getEle("webSocketClose").value = "1";
		openGameSocket();
		showInConnection();
	}
	
	function checkWebSocket(){
		if(getEle("webSocketClose").value == "1"){
			goBackLastGame();
			
		}
		closeInConnection();
	}
	
	function showInConnection(){
		showMessageNotBntWindows("連線中請稍候・・・");
	}
	
	function closeInConnection(){
		closeMessageNotBntWindows();
	}
	
	function showBalanceInsufficient(){
		showMessageWindows("當前餘額不足","確定");
	}

	function showNoOpponent(){
		showMessageWindows("尚未配對成功，請重新進入","確定");
	}
	
	function showMessageNotBntWindows(text){
		appear("messageWindowsNotBnt");
		document.getElementById("messageWindowsNotBnt").style.display = "block";
		if(text.length > 0 && text != null && typeOf(text) != "undefined"){
			document.getElementById("messageWindowsNotBntText").innerHTML = text;
		}
		else{
			document.getElementById("messageWindowsNotBntText").innerHTML = "";
		}
	}
	
	function closeMessageNotBntWindows(){
		disAppear("messageWindowsNotBnt");
		document.getElementById("messageWindowsNotBnt").style.display = "none";
		document.getElementById("messageWindowsNotBntText").innerHTML = "";
	}
	
	function showMessageWindows(text,bntText,clickFunction=null){
		
		var funName = function (){
			closeMessageWindows();
		};
		if(clickFunction != null && clickFunction != undefined && typeOf(clickFunction) != "undefined"){
			funName = function (){
				clickFunction();
				closeMessageWindows();
			};
		}
		appear("messageWindows");
		document.getElementById("messageWindows").style.display = "block";
		if(text.length > 0 && text != null && typeOf(text) != "undefined"){
			document.getElementById("messageWindowsText").innerHTML = text;
		}
		else{
			document.getElementById("messageWindowsText").innerHTML = "";
		}
		if(bntText.length > 0 && typeOf(bntText) != "undefined"){
			documentCreateButton(bntText,"messageWindowsBnt","messageWindowsText");
		}
		else{
			documentCreateButton("","messageWindowsBnt","messageWindowsText");
		}
		if(typeOf(document.getElementById("messageWindowsBnt")) != "undefined" && document.getElementById("messageWindowsBnt") != null){
			document.getElementById("messageWindowsBnt").onclick = function (){funName();};
		}
	}
	
	function closeMessageWindows(){
		disAppear("messageWindows");
		document.getElementById("messageWindows").style.display = "none";
		document.getElementById("messageWindowsText").innerHTML = "";
	}
	
	