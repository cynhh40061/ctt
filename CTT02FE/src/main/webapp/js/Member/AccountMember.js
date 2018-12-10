var XHR = null;
var XHR_AccData = null;
const WS_IP = location.host;
const LOGIN_FAILED_COUNT = 10;
const LOGIN_FAILED_LAST_COUNT = 5;
const PUNCH_GAME_JS = 0;
const GAME_TYPE_JS = {0:"--",1:"快速戰",3:"三戰兩勝",5:"五戰三勝"};
const PUNCH_TYPE_JS = {0:"---",123:"布",125:"剪",127:"石"};
const ACC_NAME_MIN_LENGTH = 6;
const PASS_WORD_MIN_LENGTH = 6;
const ACC_NAME_MAX_LENGTH = 20;
const PASS_WORD_MAX_LENGTH = 20;

var gameMenu = ["gameG1","gameG2","gameG3","gameG4","gameG5","gameG6"];
var livevideoMenu = ["livevideoG1"];
var lotteryMenu = ["lotteryG1","lotteryG2","lotteryG3","lotteryG4","lotteryG5"];
var movementMenu = ["movementG1"];

var memberButton = ["accData","addAccBankCard","updateAccPwd","accCashData"];
var gameRecordButton = ["vedioGameRecord","lotteryGameRecord","realityGameRecord","sportGameRecord"];
var rechargeButton =["onlinePay","BankPay","AliPay","WeChatPay","QQPay"];
var firstMenuButton = ["firstMenu0","firstMenu1","firstMenu2","firstMenu3","firstMenu4","firstMenu5","firstMenu6"];


function Test(){
// getEle("accName").value = "mmmm99";
// getEle("pwd").value = "123456";
// getEle("verificationCode").value = getEle("verificationCodeText").innerHTML;
}

function getPlatformAd() {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "platformId="+encodeURIComponent(getEle("platformId").value);

		XHR.open("POST", "./Login!getPlatformAd.php?date=" + new Date().getTime(),
						true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			XHR.abort();
		}
		XHR.onerror = function() {
			XHR.abort();
		}
		XHR.onreadystatechange = handleGetPlatformAd;
		XHR.send(tmpStr);
	}
}

function handleGetPlatformAd() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				Test();// --test
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					
					if(!isNull(json.adData)){
						console_Log(json.adData);
						var adIdArr = ["adLeft","adLeftBottom","adRight","adRightBottom"];
						var jsonAdKey = ["leftBig","leftSmall","rightBig","rightSmall"];
						var adMidUrlArr = [];
						var adMidSrcArr = [];
						var adMinSrcTextArr = [];
						var gameAdUrlArr = [];
						var gameAdSrcArr = [];
						var gameAdSrcTextArr = [];
						var defaultGameAdSrcArr = ["./img/banner00.png","./img/banner01.png","./img/banner02.png","./img/banner03.png"];
						var defaultAdMidSrcArr = ["./img/bigpic.png","./img/bigpic1.png","./img/bigpic2.png"];
						
						for(var i = 0 ; i < json.adData.length && !isNull(json.adData[i].adPosition) ; i++){
							var adPosition = json.adData[i].adPosition;
							if(adPosition.substring(0,adPosition.length-1) == "gameAd"){
								gameAdUrlArr[adPosition.substring(adPosition.length-1,adPosition.length)] = checkString(json.adData[i].adUrl);
								gameAdSrcArr[adPosition.substring(adPosition.length-1,adPosition.length)] = checkString(json.adData[i].adSrc);
								
								gameAdSrcTextArr[adPosition.substring(adPosition.length-1,adPosition.length)] = "圖"+adPosition.substring(adPosition.length-1,adPosition.length);
							}
							else if(adPosition.substring(0,adPosition.length-1) == "banner"){
								adMidUrlArr[adPosition.substring(adPosition.length-1,adPosition.length)] = checkString(json.adData[i].adUrl);
								adMidSrcArr[adPosition.substring(adPosition.length-1,adPosition.length)] = checkString(json.adData[i].adSrc);
								
								adMinSrcTextArr[adPosition.substring(adPosition.length-1,adPosition.length)] = "圖"+adPosition.substring(adPosition.length-1,adPosition.length);
							}
							else{
								for(var k = 0 ; k <adIdArr.length ; k++){
									if(json.adData[i].adPosition == jsonAdKey[k]){
										var adSrc = checkString(json.adData[i].adSrc);
										var adUrl = checkString(json.adData[i].adUrl);
										if(validateURL(adSrc)){
											getEle(adIdArr[k]).style.backgroundImage = "url("+adSrc+")";
											if(validateURL(adUrl)){
												getEle(adIdArr[k]).onclick = function (){javascript:window.location.href=adUrl;};
											}
											getEle(adIdArr[k]).alt = "廣告"+k;
										}
										
										delete adSrc;
										delete adUrl;
									}
								}
							}
							delete adPosition;
						}
						
						var adMidStr = [];
						var cheeckAdMinPngCount = 0;
						for(var j = 0 ; j < adMidUrlArr.length ; j++){
							if(validateURL(adMidSrcArr[j])){
								cheeckAdMinPngCount++;
								adMidStr.push('<div class="mySlides fade"  style = "display:none">');

								if(validateURL(adMidUrlArr[j])){
									adMidStr.push('<a target="_blank" href="'+adMidUrlArr[j]+'">');
								}
								else{
									adMidStr.push('<a target="_blank" href="javascript:void(0);">');
								}
								adMidStr.push('<img src="'+adMidSrcArr[j]+'" alt = '+adMinSrcTextArr[j]+'></a></div>');
							}
						}
						if(cheeckAdMinPngCount == 0){
							for(var defaultAdMid = 0 ; defaultAdMid < defaultAdMidSrcArr.length ; defaultAdMid++){
								adMidStr.push('<div class="mySlides fade"  style = "display:none">');
								adMidStr.push('<img src="'+defaultAdMidSrcArr[defaultAdMid]+'"></div>');
							}
						}
						
						delete cheeckAdMinPngCount;
						delete defaultAdMidSrcArr;
						delete adMinSrcTextArr;
						delete adMidSrcArr;
						delete adMidUrlArr;
						
						adMidStr.push('<div style="text-align: center" class="dot-area">');
						adMidStr.push('<span class="dot" onclick="currentSlide(1)"></span> <span class="dot active" onclick="currentSlide(2)"></span> <span class="dot" onclick="currentSlide(3)"></span>');
						adMidStr.push('</div>');
						adMidStr.push('<a class="prev" onclick="plusSlides(-1)">❮</a> <a class="next" onclick="plusSlides(1)">❯</a>');
					
						getEle("slideshowDiv").innerHTML = adMidStr.join("");
						plusSlides(0);
						
						delete adMidStr;
						
						
						var gameAdStr = [];
						var cheeckGameAdPngCount = 0;
						for(var g = 0 ; g < adMidUrlArr.length ; g++){
							if(validateURL(gameAdSrcArr[g])){
								cheeckGameAdPngCount++;
								if(validateURL(gameAdUrlArr[g])){
									gameAdStr.push('<a target="_blank" href="'+gameAdUrlArr[g]+'">');
								}else{
									gameAdStr.push('<a target="_blank" href="javascript:void(0);">');
								}
								gameAdStr.push('<img src="'+gameAdSrcArr[g]+'" class="Slides" alt="'+gameAdSrcTextArr[g]+'" style="display: none;"></a>');
							}
							
						}
						
						if(cheeckGameAdPngCount == 0){
							for(var defaultGameAd = 0 ; defaultGameAd < defaultGameAdSrcArr.length ; defaultGameAd++){
								gameAdStr.push('<img src="'+defaultGameAdSrcArr[defaultGameAd]+'" class="Slides" style="display: none;"></a>');
							}
						}
						

						getEle("slideshow").innerHTML = gameAdStr.join("");
						
						delete gameAdStr;
						delete gameAdSrcArr;
						delete gameAdUrlArr;
						delete gameAdSrcTextArr;
						delete defaultGameAdSrcArr;
						
						
					}
				}
			} catch (error) {
				console_Log("handleGetPlatformAd error:"+error.message);
			} finally {
				XHR.abort();
			}
		}
	}
}



function checkLogin(){
	var accId = getEle("accId").value;
	var tokenId = getEle("tokenId").value;
	if(accId == "" || accId == 0 || tokenId == ""){
		webRefresh(true);
		return false;
	}
	return true;
}
// function webRefresh(todo = false){
function webRefresh(todo){
	if(!todo instanceof Boolean){
		todo = false;
	}
	if(todo){
		window.location.reload(true);
		return ;
	}
	return function (){window.location.reload(true)};
}

clickEnterLogin = function(event){
	var evt;
	var key;
	try{
		if(window.event){
			evt = window.event;
		} else if(event){
			evt = event;
		}
		if(evt){
			if(evt.keyCode){
				key = evt.keyCode;
			} else if(evt.which){
				key = evt.keyCode;
			} else if (evt.charCode){
				key = evt.charCode;
			} else {
				return false;
			}
			if(key ? key==13 : false){
				login();
			}
		} else {
			return false;
		}
	}catch(err){

	}finally{
		delete evt;
		evt = undefined;
		delete key;
		key = undefined;
	}
};
function login(){
	if(getEle("verificationCode").value == getEle("verificationCodeText").innerHTML){
		if(chackCookieLoginFailCount()){
				loginAjax(getEle("accName").value,getEle("pwd").value);
		}
		else{
			showModelPageType1("系統訊息","登入失敗5次，請5分鐘後再重新登入","確定",webRefresh());
		}
		
	}
	else{
		getEle("verificationCode").value = '';
		showVerificationCode();
		showModelPageType1("系統訊息","驗證錯誤","確定",webRefresh());
	}

}

function loginAjax(accName, pwd) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "accName=" + encodeURIComponent(accName) + "&pwd="
				+ encodeURIComponent(pwd)+"&platformId="+encodeURIComponent(getEle("platformId").value);

		XHR.open("POST", "./Login!login.php?date=" + new Date().getTime(),
						true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			XHR.abort();
		}
		XHR.onerror = function() {
			XHR.abort();
		}
		XHR.onreadystatechange = handleLoginAjax;
		XHR.send(tmpStr);
	}
}

function handleLoginAjax() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					getEle("tokenId").value = typeof json.tokenId != "undefined" ? json.tokenId : "";
					getEle("accId").value = typeof json.accId != "undefined" ? json.accId : "";
					setLoginFailCookie(false);
					if (json.isSuccess) {
						getEle("accName").value = '';
						getEle("pwd").value = '';
						getEle("verificationCode").value = '';
						showVerificationCode();
						
						
						if(json.isLoginFailed == false || json.isPwdWithdrawFailed == false){
							showModelPageType1("系統訊息","此帳號已被停用，請聯繫客服人員","點擊諮詢");
						}
						else if(json.isLogin == false){
							var loginLastCount = LOGIN_FAILED_COUNT-json.loginFailed;
							if(loginLastCount < LOGIN_FAILED_LAST_COUNT ){
								showModelPageType1("系統訊息","帳號密碼錯誤，還剩"+loginLastCount+"次機會","確定");
							}
							else{
								showModelPageType1("系統訊息","帳號密碼錯誤","確定",webRefresh());
							}
						}
						else if (json.pwdStatus == true) {
							showUpdatePwdDiv();
						} else {
							setLoginFailCookie(true);
							logIn();
							openHomePageDiv();
							balanceCountDown();
							if (json.pwdWithdrawStatus) {
								showUpdateWithdrawPwdDiv();
							}
						}
					} else {
						showModelPageType1("系統訊息","帳號密碼錯誤","確定",webRefresh(),true);
						
					}
				}
			} catch (error) {
				console_Log("handleLoginAjax error:"+error.message);
			} finally {
				XHR.abort();
				if (json.isSuccess && !json.pwdStatus && json.isLogin) {
					getAccDataAjax();
				}
			}
		}
	}
}
function logout() {
	if(checkLogin()){
		logoutAjax();
	}
}
function logoutAjax() {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var jsonObj = JSON.parse(getEle("accDataJson").value);
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)
				+ "&accId=" + encodeURIComponent(getEle("accId").value);
		XHR.open("POST", "./Login!logout.php?date=" + new Date().getTime(),
				true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			XHR.abort();
		}
		XHR.onerror = function() {
			XHR.abort();
		}
		XHR.onreadystatechange = handleLogoutAjax;
		XHR.send(tmpStr);
	}
}
function handleLogoutAjax() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {

					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (json.isSuccess) {
							getEle("balanceRefreshTime").value = -1;
							webRefresh(true);
						}
					}

				}
			} catch (error) {
				console_Log("handleLogoutAjax error:"+error.message);
			} finally {
				XHR.abort();
			}
		}
	}
}

function getAccDataAjax(){
	XHR_AccData = checkXHR(XHR_AccData);
	if (XHR_AccData != null && typeof XHR_AccData != "undefined") {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)+"&accId="+encodeURIComponent(getEle("accId").value);
				+ "&accId=" + encodeURIComponent(getEle("accId").value);
		XHR_AccData.open("POST", "./AccountMember!getMemAccData.php?date="
				+ new Date().getTime(), true);
		XHR_AccData.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR_AccData.timeout = 10000;
		XHR_AccData.ontimeout = function() {
			XHR_AccData.abort();
		}
		XHR_AccData.onerror = function() {
			XHR_AccData.abort();
		}
		XHR_AccData.onreadystatechange = handleGetAccDataAjax;
		XHR_AccData.send(tmpStr);
	}
}

function handleGetAccDataAjax() {
	if (XHR_AccData.readyState == 4) {
		if (XHR_AccData.status == 200) {
			try {
				if (isJSON(XHR_AccData.responseText)) {
					var json = JSON.parse(XHR_AccData.responseText);
					if (checkTokenIdfail(json)) {
						
						if(typeof json.accName != "undefined" && typeof json.balance != "undefined" && typeof json.nickname != "undefined"){
							
							getEle("accDataJson").value = XHR_AccData.responseText;
							getEle("accNameText").innerHTML = "帳號：" + json.accName;
							getEle("balanceText").innerHTML = json.balance;

							getEle("nickNameData").innerHTML = json.nickname;
							getEle("accNameData").innerHTML = json.accName;
							getEle("accBalanceData").innerHTML = json.balance;
						}
						
					}
				}
			} catch (error) {
				console_Log("handleGetAccDataAjax error:"+error.message);
			} finally {
				XHR_AccData.abort();
			}
		}
	}
}
function conformUpdateWithdrawPwd() {
	var withdrawPwd = getEle("withdrawPwd").value;
	var checkWithdrawPwd = getEle("checkWithdrawPwd").value;
	if(checkInputWithdrawPwd("withdrawPwd",withdrawPwd) && checkInputWithdrawPwd("checkWithdrawPwd",checkWithdrawPwd)){
		if(withdrawPwd == checkWithdrawPwd){
			
			setIconPass("withdrawPwd");
			setIconPass("checkWithdrawPwd");
			
			updateWithdrawPwdAjax(withdrawPwd);
		}
		else{
			setIconFail("withdrawPwd");
			setIconFail("checkWithdrawPwd");
		}
	}
}

function checkInputWithdrawPwd(id, val) {
	if(val.length >= PASS_WORD_MIN_LENGTH && val.length <= PASS_WORD_MAX_LENGTH){
		if(checkNumberVal(val)){
			setIconPass(id+"Warning");
			return true;
		}
		else{
			setIconFail(id+"Warning");
		}
	}
	else{
		setIconFail(id+"Warning");
	}
	return false;
	
}

function updateWithdrawPwdAjax(WithdrawPwd) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)
				+ "&accId=" + encodeURIComponent(getEle("accId").value)
				+ "&withdrawPwd=" + encodeURIComponent(WithdrawPwd);
		XHR.open("POST", "./AccountMember!updateWithdrawPwd.php?date="
				+ new Date().getTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			XHR.abort();
		}
		XHR.onerror = function() {
			XHR.abort();
		}
		XHR.onreadystatechange = handleUpdateWithdrawPwdAjax;
		XHR.send(tmpStr);
	}
}

function handleUpdateWithdrawPwdAjax() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (json.isSuccess) {
							setDisplayClose("updateWithdrawPwdDiv");
						}
					}
				}
			} catch (error) {
				console_Log("handleUpdateWithdrawPwdAjax error:"+error.message);
			} finally {
				XHR.abort();
			}
		}
	}
}

function conformUpdatePwd(){
	var oldPwdVal = getEle("oldPwd").value;
	var newPwdVal = getEle("newPwd").value;

	if (checkPwd("newPwd") && checkPwd("checkPwd") && checkPwd("oldPwd")
			&& comparisonPwd("newPwd", "checkPwd")) {
		updatePwdAjax(oldPwdVal, newPwdVal);
	}
}

function updatePwdAjax(oldPwd, newPwd) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)
				+ "&accId=" + encodeURIComponent(getEle("accId").value)
				+ "&oldPwd=" + encodeURIComponent(oldPwd) + "&newPwd="
				+ encodeURIComponent(newPwd);
		XHR.open("POST", "./AccountMember!updatePwd.php?date="
				+ new Date().getTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
		}
		XHR.onerror = function() {
			disableLoadingPage();
		}
		XHR.onreadystatechange = handleUpdatePwdAjax;
		XHR.send(tmpStr);
	}
}

function handleUpdatePwdAjax() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (typeof json.isSuccess != "undefined") {
							if (json.isSuccess) {
								setDisplayClose("updatePwdDiv");
							}

						}
					}
				}
			} catch (error) {
			} finally {
				XHR.abort();
			}
		}
	}
}

function updateMemPwdAjax(oldPwd, newPwd) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null ) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)
				+ "&accId=" + encodeURIComponent(getEle("accId").value)
				+ "&oldPwd=" + encodeURIComponent(oldPwd) + "&newPwd="
				+ encodeURIComponent(newPwd);
		XHR.open("POST", "./AccountMember!updatePwd.php?date="
				+ new Date().getTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			XHR.abort();
		}
		XHR.onerror = function() {
			XHR.abort();
		}
		XHR.onreadystatechange = handleUpdateMemPwdAjax;
		XHR.send(tmpStr);
	}
}

function handleUpdateMemPwdAjax() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (typeof json.isSuccess != "undefined") {
							if (json.isSuccess) {
								showModelPageType1("系統訊息","請重新登入","確定",webRefresh(),true);
							}

						}
					}
				}
			} catch (error) {
				console_Log("handleUpdatePwdAjax error:"+error.message);
			} finally {
				XHR.abort();
			}
		}
	}
}

function checkTokenIdfail(json) {
	if (json.tokenId != null && json.tokenId != undefined
			&& typeof json.tokenId != "undefined") {
		if (json.tokenId == "fail") {
			getEle("accId").value = "";
			getEle("tokenId").value = "";
			showModelPageType1("系統訊息","閒置時間過長，請重新登入","確定",webRefresh(),true);

			return false;
		}
		return true;
	} else {
		getEle("accId").value = "";
		getEle("tokenId").value = ""
		showModelPageType1("系統訊息","閒置時間過長，請重新登入","確定",webRefresh(),true);
		return false;
	}
}

function balanceRefreshButton() {
	getEle("balanceRefreshTime").value = 0;
}

function showRefreshTime() {
	getEle("refreshTime").innerHTML = getEle("balanceRefreshTime").value;
}

function balanceCountDown() {
	var timeout = parseInt(getEle("balanceRefreshTime").value);
	if (timeout < 0) {
		console_Log("Close Count Down");
	} else {
		setTimeout(function() {
			balanceCountDown();
		}, 1000);
		showRefreshTime();
		if (timeout > 1) {
			getEle("balanceRefreshTime").value = timeout - 1;
		} else if (timeout <= 1) {
			getEle("balanceRefreshTime").value = 60;
			getAccDataAjax();
		}
	}
}

function showVerificationCode() {
	getEle("verificationCodeText").innerHTML = getVerificationCode();
}

function getVerificationCode() {
	var n = "";
	for (var i = 0; i < 5; i++) {
		var maxNum = 9;
		var minNum = 0;
		n += Math.floor(Math.random() * (maxNum - minNum + 1)) + minNum;
	}
	return n;
}

function openHomePageDiv() {
	clearAllButtonLight();
	closeAllDiv();
	openAllAdDiv()
	openHomePageClass();
	setDisplayOpen("homePageDiv");
	marqueeDivAlignCenter();
}

function openRegisterDiv() {
	closeAllDiv();
	setDisplayOpen("registerDiv");
	marqueeDivAlignCenter();
	// getPlatformCashBcAjax();
	clearRegistered();
	
}
// function openGameDiv(leftMenu=-1,leftBranches=-1,game=-1){
function openGameDiv(leftMenu,leftBranches,game){
	if(typeof leftMenu != "number"){
		leftMenu = -1;
	}
	if(typeof leftBranches != "number"){
		leftBranches = -1;
	}
	if(typeof game != "number"){
		game = -1;
	}
	var gameListAreaDivArr = ["rightSide1","rightSide2","rightSide3","rightSide4","rightSide5","rightSide6"];
	for(var i = 0 ; i < gameListAreaDivArr.length ; i++){
		setDisplayClose(gameListAreaDivArr[i]);
	}
	setDisplayClose("firstMenu0Ul");
	closeAllDiv();
	setDisplayOpen("gameDiv");
	marqueeDivAlignRight();
	
	setFirstMenuLight(0);
	if(leftMenu >= 0){
		setButtonLight(leftMenu,gameMenu,"button-active");
		if(gameListAreaDivArr.length > leftMenu && typeOf(gameListAreaDivArr[leftMenu]) != "undefined"){
			setDisplayOpen(gameListAreaDivArr[leftMenu]);
		}
		
		if(typeOf(gameMenu) != "undefined"){
			setButtonLight(leftMenu,gameMenu,"button-active");
			if(gameListAreaDivArr.length > leftMenu && typeOf(gameListAreaDivArr[leftMenu]) != "undefined"){
				setDisplayOpen(gameListAreaDivArr[leftMenu]);
			}
			if(gameMenu.length > leftMenu  && typeOf(gameMenu[leftMenu]) != "undefined"){
				if(typeOf(getEle(gameMenu[leftMenu])) != "undefined"){
					var ele = getEle(gameMenu[leftMenu]).parentNode.children[1];
					if(ele.tagName == "UL"){
						if(leftBranches >= 0){
							Dropdown(ele).add();
						}
						else{
							Dropdown(ele).init();
						}
						Dropdown(ele).toggleSubmenuActive(leftBranches);
					}
				}
			}
		}
	}
	if(leftMenu == 2){
		if(game == 0){
			openGamePageDiv("http://"+WS_IP+"/CTT03LotteryPage/LotteryPage",leftBranches,game);
		}
	}
	if(leftMenu == 0){
		if(game == 1){
			openGamePageDiv("http://"+WS_IP+"/CTT02GamePage/GamePage",leftBranches,game);
		}
	}
}
// function openLivevideoDiv(leftMenu=-1,leftBranches=-1,game=-1){
function openLivevideoDiv(leftMenu,leftBranches,game){
	if(typeof leftMenu != "number"){
		leftMenu = -1;
	}
	if(typeof leftBranches != "number"){
		leftBranches = -1;
	}
	if(typeof game != "number"){
		game = -1;
	}
	var gameListAreaDivArr = ["livevideoGame1"];
	for(var i = 0 ; i < gameListAreaDivArr.length ; i++){
		setDisplayClose(gameListAreaDivArr[i]);
	}
	setDisplayClose("firstMenu1Ul");
	closeAllDiv();
	setDisplayOpen("livevideoDiv");
	marqueeDivAlignRight();
	setFirstMenuLight(1);
	if(leftMenu >= 0 && typeOf(livevideoMenu) != "undefined"){
		setButtonLight(leftMenu,livevideoMenu,"button-active");
		if(gameListAreaDivArr.length > leftMenu && typeOf(gameListAreaDivArr[leftMenu]) != "undefined"){
			setDisplayOpen(gameListAreaDivArr[leftMenu]);
		}
		if(livevideoMenu.length > leftMenu  && typeOf(livevideoMenu[leftMenu]) != "undefined"){
			if(typeOf(getEle(livevideoMenu[leftMenu])) != "undefined"){
				var ele = getEle(livevideoMenu[leftMenu]).parentNode.children[1];
				if(ele.tagName == "UL"){
					if(leftBranches >= 0){
						Dropdown(ele).add();
					}
					else{
						Dropdown(ele).init();
					}
					Dropdown(ele).toggleSubmenuActive(leftBranches);
					
				}
			}
		}
	}
}
// function openLotteryDiv(leftMenu=-1,leftBranches=-1,game=-1){
function openLotteryDiv(leftMenu=-1,leftBranches=-1,game=-1){
	if(typeof leftMenu != "number"){
		leftMenu = -1;
	}
	if(typeof leftBranches != "number"){
		leftBranches = -1;
	}
	if(typeof game != "number"){
		game = -1;
	}
	var gameListAreaDivArr = ["lotteryGame1","lotteryGame2","lotteryGame3","lotteryGame4","lotteryGame5"];
	for(var i = 0 ; i < gameListAreaDivArr.length ; i++){
		setDisplayClose(gameListAreaDivArr[i]);
	}
	setDisplayClose("firstMenu2Ul");
	closeAllDiv();
	setDisplayOpen("lotteryDiv");
	marqueeDivAlignRight();
	setFirstMenuLight(2);
	if(leftMenu >= 0 && typeOf(lotteryMenu) != "undefined"){
		setButtonLight(leftMenu,lotteryMenu,"button-active");
		if(gameListAreaDivArr.length > leftMenu && typeOf(gameListAreaDivArr[leftMenu]) != "undefined"){
			setDisplayOpen(gameListAreaDivArr[leftMenu]);
		}
		if(lotteryMenu.length > leftMenu  && typeOf(lotteryMenu[leftMenu]) != "undefined"){
			if(typeOf(getEle(lotteryMenu[leftMenu])) != "undefined"){
				var ele = getEle(lotteryMenu[leftMenu]).parentNode.children[1];
				if(ele.tagName == "UL"){
					if(leftBranches >= 0){
						Dropdown(ele).add();
					}
					else{
						Dropdown(ele).init();
					}
					Dropdown(ele).toggleSubmenuActive(leftBranches);
					
				}
			}
		}
	}
}
// function openMovementDiv(leftMenu=-1,leftBranches=-1,game=-1){
function openMovementDiv(leftMenu,leftBranches,game){
	if(typeof leftMenu != "number"){
		leftMenu = -1;
	}
	if(typeof leftBranches != "number"){
		leftBranches = -1;
	}
	if(typeof game != "number"){
		game = -1;
	}
	var gameListAreaDivArr = ["movementGame1"];
	for(var i = 0 ; i < gameListAreaDivArr.length ; i++){
		setDisplayClose(gameListAreaDivArr[i]);
	}
	setDisplayClose("firstMenu3Ul");
	closeAllDiv();
	setDisplayOpen("movementDiv");
	marqueeDivAlignRight();
	setFirstMenuLight(3);
	if(leftMenu >= 0 && typeOf(movementMenu) != "undefined"){
		setButtonLight(leftMenu,movementMenu,"button-active");
		if(gameListAreaDivArr.length > leftMenu && typeOf(gameListAreaDivArr[leftMenu]) != "undefined"){
			setDisplayOpen(gameListAreaDivArr[leftMenu]);
		}
		if(movementMenu.length > leftMenu  && typeOf(movementMenu[leftMenu]) != "undefined"){
			if(typeOf(getEle(movementMenu[leftMenu])) != "undefined"){
				var ele = getEle(movementMenu[leftMenu]).parentNode.children[1];
				if(ele.tagName == "UL"){
					if(leftBranches >= 0){
						Dropdown(ele).add();
					}
					else{
						Dropdown(ele).init();
					}
					Dropdown(ele).toggleSubmenuActive(leftBranches);
				}
			}
		}
	}
	
}

function setFirstMenuLight(val){
	if(isNumber(val)){
		for(var i = 0;i < firstMenuButton.length;i++){
				getEle(firstMenuButton[i]).classList.remove("menu-active");
		}
		getEle(firstMenuButton[val]).classList.add("menu-active");
	}
}
// url="",lotteryPageLevel1,lotteryPageLevel2
function openGamePageDiv(url,lotteryPageLevel1,lotteryPageLevel2) {
	if(typeof url != "string"){
		url = "";
	}
	if(checkLogin()){
		closeAllDiv();
		openGameArea();
		setDisplayOpen("gameDiv");
		setDisplayOpen("iframeArea");
		setDisplayOpen("gameArea");
		if (getEle("accId").value != null
				&& getEle("accId").value != undefined
				&& getEle("accId").value != ""
				&& getEle("tokenId").value != null
				&& getEle("tokenId").value != undefined
				&& getEle("tokenId").value != ""
				&& getEle("accDataJson").value != null
				&& getEle("accDataJson").value != undefined
				&& getEle("accDataJson").value != "") {
			var tmpAccName = JSON.parse(getEle("accDataJson").value);
			
			getEle("accIdGame").value = getEle("accId").value;
			getEle("tokenIdGame").value = getEle("tokenId").value;
			getEle("accNameGame").value = tmpAccName.accName;
			getEle("lotteryPageLevel1").value = lotteryPageLevel1;
			getEle("lotteryPageLevel2").value = lotteryPageLevel2;
	
			if(url != ""){
				getEle("startGameForm").action = url;
			}
			else{
				getEle("startGameForm").action = "http://"+WS_IP+"/CTT02GamePage/GamePage.php";
			}
			
			getEle("startGameForm").submit();
		}
		
	}
}

function marqueeDivAlignCenter() {
	if (!getEle("marqueeDiv").classList.contains("index")) {
		getEle("marqueeDiv").classList.add("index");
	}
}
function marqueeDivAlignRight() {
	if (getEle("marqueeDiv").classList.contains("index")) {
		getEle("marqueeDiv").classList.remove("index");
	}
}

function openNewsDiv() {
	clearAllButtonLight();
	closeAllDiv();
	setDisplayOpen("newsDiv");
	marqueeDivAlignCenter();
}
function openNewsPageDiv() {
	clearAllButtonLight();
	closeAllDiv();
	setDisplayOpen("newsPageDiv");
	marqueeDivAlignCenter();
}
function openRechargeDiv() {
	if(checkLogin()){
		closeAllDiv();
		setDisplayOpen("rechargeDiv");
		closeRechargeAllDiv();
		setDisplayOpen("bankRechargeDiv");
		setFirstMenuLight(4);
		openBankRechargeDiv();
		marqueeDivAlignRight();
		cleanRechargeInfo();
		setButtonLight(1,rechargeButton,"button-active");
	}
}
function openTakeOnlineDiv() {
	if(checkLogin()){
		clearAllButtonLight();
		closeAllDiv();
		setDisplayOpen("takeOnlineDiv");
		marqueeDivAlignCenter();
		getBankInfoAjax();
		showLockAmount();
	}
}
function openMemberDiv() {
	if(checkLogin()){
		clearAllButtonLight();
		closeAllDiv();
		setDisplayOpen("memberDiv");
		setButtonLight(0,memberButton,"button-active");
		marqueeDivAlignRight();
		openAccDataDiv();
	}

}
function closeAllDiv() {
	closeAllAdDiv();
	closeHomePageClass();
	setDisplayClose("homePageDiv");
	setDisplayClose("registerDiv");
	setDisplayClose("gameDiv");
	setDisplayClose("lotteryDiv");
	setDisplayClose("livevideoDiv");
	setDisplayClose("movementDiv");
	setDisplayClose("gameArea");
	setDisplayClose("newsDiv");
	setDisplayClose("newsPageDiv");
	setDisplayClose("rechargeDiv");
	setDisplayClose("takeOnlineDiv");
	setDisplayClose("memberDiv");
	setDisplayClose("gameRecordDiv");
	backToGameTotleRecordsDiv();
	getEle("gameTotleRecordstable").innerHTML = '';
	getEle("gameRecordstable").innerHTML = '';
	getEle('gameRecordsSearchInfo').value = '';
	getEle('gameRecordsData').value = '';
	getEle('gameRecordStartTime').value = '';
	getEle('gameRecordEndTime').value = '';
}


function setDisplayOpen(name) {
	if (getEle(name) != null && getEle(name) != undefined && typeof getEle(name) != "undefined") {
		if (getEle(name).classList.contains("hidden")) {
			getEle(name).classList.remove("hidden");
		}
	}
}

function setDisplayClose(name) {
	if (getEle(name) != null && getEle(name) != undefined && typeof getEle(name) != "undefined") {
		if (!getEle(name).classList.contains("hidden")) {
			getEle(name).classList.add("hidden");
		}
	}
}

function openHomePageClass() {
	if (getEle("containerDiv").classList.contains("container")) {
		getEle("containerDiv").classList.remove("container");
	}
	if (!getEle("containerDiv").classList.contains("container-index")) {
		getEle("containerDiv").classList.add("container-index");
	}
	if (!getEle("navDiv").classList.contains("index-hall")) {
		getEle("navDiv").classList.add("index-hall");
	}
}

function closeHomePageClass() {
	if (getEle("containerDiv").classList.contains("container-index")) {
		getEle("containerDiv").classList.remove("container-index");
	}
	if (getEle("navDiv").classList.contains("index-hall")) {
		getEle("navDiv").classList.remove("index-hall");
	}

	if (!getEle("containerDiv").classList.contains("container")) {
		getEle("containerDiv").classList.add("container");
	}
}

function openAllAdDiv() {
	setDisplayOpen("adRight");
	setDisplayOpen("adRightBottom");
	setDisplayOpen("adLeft");
	setDisplayOpen("adLeftBottom");
}
function closeAllAdDiv() {
	setDisplayClose("adRight");
	setDisplayClose("adRightBottom");
	setDisplayClose("adLeft");
	setDisplayClose("adLeftBottom");
}

function closeMemberAllDiv() {
	removeDivAllClass("rightSideDiv");
	setDisplayClose("accDataDiv");
	setDisplayClose("addAccBankCardDiv");
	setDisplayClose("updateAccPwdDiv");
	setDisplayClose("accCashDataDiv");
	setDisplayClose("accGameDataDiv");

}

function removeDivAllClass(name) {
	if (getEle(name) != null && getEle(name) != undefined) {
		while (getEle(name).classList.length > 0) {
			getEle(name).classList.remove(getEle(name).classList.item(0));
		}
	}
}

function openAccDataDiv() {
	if(checkLogin()){
		closeMemberAllDiv();
		getEle("rightSideDiv").classList.add("right-side");
		setDisplayOpen("accDataDiv");
		getAccDetailsAjax();
		setButtonLight(0,memberButton,"button-active");
	}
}

function openAddAccBankCardDiv() {
	if(checkLogin()){
		closeMemberAllDiv();
		getEle("rightSideDiv").classList.add("right-side");
		getEle("rightSideDiv").classList.add("bank");
		setDisplayOpen("addAccBankCardDiv");
		setDisplayClose("canAdd");
		setDisplayClose("donotAdd");
		getEle("CardDiv1").style = "display:none";
		getEle("CardDiv2").style = "display:none";
		getBankInfoAjax();
		setButtonLight(1,memberButton,"button-active");
	}
}

function openUpdateAccPwdDiv() {
	if(checkLogin()){
		closeMemberAllDiv();
		getEle("rightSideDiv").classList.add("right-side");
		getEle("rightSideDiv").classList.add("password");
		setDisplayOpen("updateAccPwdDiv");
		setButtonLight(2,memberButton,"button-active");
	}
}

function openAccCashDataDiv() {
	if(checkLogin()){
		closeMemberAllDiv();
		getEle("rightSideDiv").classList.add("right-side");
		getEle("rightSideDiv").classList.add("cash");
		setDisplayOpen("accCashDataDiv");
		setButtonLight(3,memberButton,"button-active");
	}
}

function openAccGameDataDiv() {
	if(checkLogin()){
		closeMemberAllDiv();
		getEle("rightSideDiv").classList.add("right-side");
		setDisplayOpen("accGameDataDiv");
	}
}

function closeRechargeAllDiv() {
	closeInfoDiv();
	setDisplayClose("bankRechargeDiv");
	setDisplayClose("onlineRechargeDiv");
	setDisplayClose("alipayRechargeDiv");
	setDisplayClose("weChatRechargeDiv");
	setDisplayClose("qqRechargeDiv");
}

function openBankRechargeDiv() {
	if(checkLogin()){
		closeRechargeAllDiv();
		setDisplayOpen("bankRechargeDiv");
		cleanRechargeInfo();
		setButtonLight(1,rechargeButton,"button-active");
	}
}

function openOnlineRechargeDiv() {
	if(checkLogin()){
		clearAllButtonLight();
		setFirstMenuLight(4);
		closeAllDiv();
		setDisplayOpen("rechargeDiv");
		closeRechargeAllDiv();
		setDisplayOpen("onlineRechargeDiv");
		setButtonLight(0,rechargeButton,"button-active");
	}
}

function openAlipayRechargeDiv() {
	if(checkLogin()){
		closeRechargeAllDiv();
		setDisplayOpen("alipayRechargeDiv");
		setButtonLight(2,rechargeButton,"button-active");
	}
}
function openWeChatRechargeDiv() {
	if(checkLogin()){
		closeRechargeAllDiv();
		setDisplayOpen("weChatRechargeDiv");
		setButtonLight(3,rechargeButton,"button-active");
	}
}
function openQqRechargeDiv() {
	if(checkLogin()){
		closeRechargeAllDiv();
		setDisplayOpen("qqRechargeDiv");
		setButtonLight(4,rechargeButton,"button-active");
	}
}

function closeUpdateWithdrawPwdDiv() {
	logout();
	setDisplayClose("updateWithdrawPwdDiv");
}
function closeUpdatePwdDiv() {
	getEle("tokenId").value = "";
	getEle("accId").value = "0";
	setDisplayClose("updatePwdDiv");
}

function checkOrderAmount() {
	getEle("rechargeAmount").value = checkInputNumberVal(getEle("rechargeAmount").value);
}

function conformRechargeInfo() {
	var bankSid = getEle("rechargeBankSid").value;
	var accName = JSON.parse(getEle("accDataJson").value).accName;
	var amount = getEle("rechargeAmount").value;
	var note = getEle("cashierNoteCheck").value;
	var bankAccName = getEle("rechargeBankAccName").value;
	var bankAcc = getEle("rechargeBankAcc").value;
	var bank = getEle("rechargeBank").value;
	var bankDateTime = getEle("rechargeBankDate").value;
	
	var chkRechargeBankSids = chkRechargeBankSid();
	var chkRechargeAccNames = chkRechargeAccName();
	var chkRechargeAmounts = chkRechargeAmount();
	var chkRechargeNotes = chkRechargeNote();
	var chkRechargeBankAccNames = chkRechargeBankAccName();
	var chkRechargeBankAccs = chkRechargeBankAcc();
	var chkRechargeBanks = chkRechargeBank();
	var chkRechargeBankDates = chkRechargeBankDate();
	
	var str = "&bankSid="+bankSid+"&accName="+accName+"&amount="+amount+"&note="+note+"&bankAccName="+bankAccName+"&bankAcc="+bankAcc+"&bank="+bank+"&bankDateTime="+bankDateTime;
	if(chkRechargeBankSids && chkRechargeAccNames && chkRechargeAmounts && chkRechargeNotes && chkRechargeBankAccNames && chkRechargeBankAccs && chkRechargeBanks && chkRechargeBankDates){
		addRechargeOrderAjax(str);
	}else{
		showModelPageType1("系統訊息","有欄位尚未填寫","確定");
	}
}
function chkRechargeBankSid() {
	if (getEle("rechargeBankSid").value != "") {
		setIconPass("rechargeBankSidIcon");
		return true;
	} else {
		setIconFail("rechargeBankSidIcon");
		return false;
	}
}
function chkRechargeAccName() {
	if (getEle("rechargeAccName").value != "") {
		setIconPass("rechargeAccNameIcon");
		return true;
	} else {
		setIconFail("rechargeAccNameIcon");
		return false;
	}
}
function chkRechargeAmount() {
	if (getEle("rechargeAmountCheck").value != "") {
		setIconPass("rechargeAmountCheckIcon");
		return true;
	} else {
		setIconFail("rechargeAmountCheckIcon");
		return false;
	}
}
function chkRechargeNote() {
	if (getEle("cashierNoteCheck").value != "") {
		setIconPass("cashierNoteCheckIcon");
		return true;
	} else {
		setIconFail("cashierNoteCheckIcon");
		return false;
	}
}
function chkRechargeBankAccName() {
	if (getEle("rechargeBankAccName").value != "") {
		setIconPass("rechargeBankAccNameIcon");
		return true;
	} else {
		setIconFail("rechargeBankAccNameIcon");
		return false;
	}
}
function chkRechargeBankAcc() {
	if (getEle("rechargeBankAcc").value != "") {
		setIconPass("rechargeBankAccIcon");
		return true;
	} else {
		setIconFail("rechargeBankAccIcon");
		return false;
	}
}
function chkRechargeBank() {
	if (getEle("rechargeBank").value != "") {
		setIconPass("rechargeBankIcon");
		return true;
	} else {
		setIconFail("rechargeBankIcon");
		return false;
	}
}
function chkRechargeBankDate() {
	if (getEle("rechargeBankDate").value != "") {
		setIconPass("rechargeBankDateIcon");
		return true;
	} else {
		setIconFail("rechargeBankDateIcon");
		return false;
	}
}

function addRechargeOrderAjax(str){
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)+"&accId=" + encodeURIComponent(getEle("accId").value)+str;
		XHR.open("POST", "./AccountMember!addRechargeOrder.php?date="
				+ new Date().getTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			XHR.abort();
		}
		XHR.onerror = function() {
			XHR.abort();
		}
		XHR.onreadystatechange = handleaddRechargeOrder;
		XHR.send(tmpStr);
	}
}

function handleaddRechargeOrder() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if(checkTokenIdfail(json)){
						if (json.message == "fail") {
							showModelPageType1("系統訊息","水單提交失敗","確定");
						} else if (json.message == "success") {
							showModelPageType1("系統訊息","水單提交成功","確定");
							cleanRechargeInfo();
							closeInfoDiv();
						}
					}
				}
			} catch (error) {
				console_Log("handleaddRechargeOrder error:"+error.message);
			} finally {
				XHR.abort();
			}
		}
	}
}

function conformWithdrawalInfo(){
	var accName = JSON.parse(getEle("accDataJson").value).accName;
	var withdrawalAmount = getEle("withdrawalAmount").value;
	var bankInfoList = getEle("bankInfoList").value;
	var orderWithdrawPwd = getEle("orderWithdrawPwd").value;
	
	var str = "&accName="+accName+"&withdrawalAmount="+withdrawalAmount+"&bankInfoList="+bankInfoList+"&orderWithdrawPwd="+orderWithdrawPwd;
	if(accName!='' && withdrawalAmount >= 100 && withdrawalAmount <= 1000000 && bankInfoList > 0 && orderWithdrawPwd!=''){
		addWithdrawalOrderAjax(str);
	}else if (accName!='' && (withdrawalAmount < 100 || withdrawalAmount > 1000000) && bankInfoList > 0 && orderWithdrawPwd!=''){
		showModelPageType1("系統訊息","請輸入正確金額","確定");
	}else{
		showModelPageType1("系統訊息","有欄位尚未輸入","確定");
	}
}
function addWithdrawalOrderAjax(str){
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)+"&accId=" + encodeURIComponent(getEle("accId").value)+str;
		XHR.open("POST", "./AccountMember!addWithdrawalOrder.php?date="
				+ new Date().getTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			XHR.abort();
		}
		XHR.onerror = function() {
			XHR.abort();
		}
		XHR.onreadystatechange = handleAddWithdrawalOrder;
		XHR.send(tmpStr);
	}
}

function handleAddWithdrawalOrder() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if(checkTokenIdfail(json)){
						if (json.message == "fail") {
							if(parseInt(json.failCount) < 5 && parseInt(json.failCount) >= 0){
								showModelPageType1("系統訊息","提款密碼錯誤，剩餘次數:"+(5-parseInt(json.failCount))+"次!","確定");
							}else if(parseInt(json.failCount) >= 5){
								showModelPageType1("系統訊息","此帳號已被鎖定，請聯繫客服人員!","確定");
								logoutAjax();
							}
						} else if (json.message == "success") {
							showModelPageType1("系統訊息","水單提交成功","確定");
							cleanWithdrawInfo();
						}
					}
				}
			} catch (error) {
				console_Log("handleAddWithdrawalOrder error:"+error.message);
			} finally {
				XHR.abort();
				if (json.message == "success") {
					balanceRefreshButton();
				}
			}
		}
	}
}

function getAccDetailsAjax() {
	closeUpdateAccDetails();
	balanceRefreshButton();
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)+"&accId=" + encodeURIComponent(getEle("accId").value);
		XHR.open("POST", "./AccountMember!getAccDetails.php?date="
				+ new Date().getTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			XHR.abort();
		}
		XHR.onerror = function() {
			XHR.abort();
		}
		XHR.onreadystatechange = handleGetAccDetailsAjax;
		XHR.send(tmpStr);
	}
}

function handleGetAccDetailsAjax() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						getEle("memberRealNameData").innerHTML = json.memberRealName;
						getEle("hiddenRealName").value = json.memberRealName;
						getEle("phoneNumberData").innerHTML = json.phoneNumber;
						getEle("qqAccountData").innerHTML = json.qqAcc;
						getEle("weChatAccountData").innerHTML = json.wechatAcc;
					}
				}
			} catch (error) {
				console_Log("handleGetAccDetailsAjax error:"+error.message);
			} finally {
				XHR.abort();
			}
		}
	}
}

function onClickUpdateAccDetails() {
	openSave();
	getEle("nickName").value = getEle("nickNameData").innerHTML;
	getEle("phoneNumber").value = getEle("phoneNumberData").innerHTML;
	getEle("qqAccount").value = getEle("qqAccountData").innerHTML;
	getEle("weChatAccount").value = getEle("weChatAccountData").innerHTML;
	
	 if(getEle("memberRealNameData").innerHTML == '' ){
		getEle("memberRealName").style.display = "inline-block";
		getEle("memberRealNameData").style.display = "none";
		getEle("memberRealName").value = getEle("memberRealNameData").innerHTML;
		
		 setIdDisplay("memberRealTextWarning","block");
		
	}
	 
	 setIdDisplay("weChatTextWarning","block"); 
	 setIdDisplay("qqTextWarning","block");
	 setIdDisplay("phoneNumberTextWarning","block");
	 setIdDisplay("nickNameTextWarning","block");
// setIdDisplay("memberRealTextWarning","block");
	 

}
function closeUpdateAccDetails() {
	closeSave();
	getEle("memberRealNameCaveat").innerHTML = "";
	getEle("qqAccountCaveat").innerHTML = "";
	getEle("weChatAccountCaveat").innerHTML = "";
	getEle("nickName").value = "";
	getEle("memberRealName").value = "";
	getEle("phoneNumber").value = "";
	getEle("qqAccount").value = "";
	getEle("weChatAccount").value = "";
	
	 setIdDisplay("weChatTextWarning","none"); 
	 setIdDisplay("qqTextWarning","none");
	 setIdDisplay("phoneNumberTextWarning","none");
	 setIdDisplay("nickNameTextWarning","none");
	 setIdDisplay("memberRealTextWarning","none");
	
	clearIcon("phoneNumberCaveat");
	clearIcon("qqAccountCaveat");
	clearIcon("weChatAccountCaveat");
	clearIcon("nickNameCaveat");
	clearIcon("memberRealNameCaveat");
	
   if(getEle("memberRealName").style.display ==  "inline-block"){
		getEle("memberRealName").style.display = "none";
		getEle("memberRealNameData").style.display = "inline-block";
	}
}

function checkPhoneNumber() {
	var val = "";
	if (getEle("phoneNumber").value != ""
			&& getEle("phoneNumber").value != null) {
		val = checkInputNumberVal(getEle("phoneNumber").value);
		if (val != "" && val != null) {
			getEle("phoneNumber").value = val;
// getEle("phoneNumberCaveat").innerHTML = "";
			setIconPass("phoneNumberCaveat");
			return true;
		} else {
			getEle("phoneNumber").value = "";
// getEle("phoneNumberCaveat").innerHTML = "此欄位必填";
			setIconFail("phoneNumberCaveat");
			return false;
		}
	} else {
		getEle("phoneNumber").value = "";
// getEle("phoneNumberCaveat").innerHTML = "此欄位必填";
		setIconFail("phoneNumberCaveat");
		return false;
	}

}

function checkQQAndWechat() {
	if (!checkInputTextData("weChatAccount", getEle("weChatAccount").value)
			&& !checkInputTextData("qqAccount", getEle("qqAccount").value)) {
// getEle("qqAccountCaveat").innerHTML = "QQ 或 微信 最少填寫一項";
// getEle("weChatAccountCaveat").innerHTML = "QQ 或 微信 最少填寫一項";
		setIconFail("qqAccountCaveat");
		setIconFail("weChatAccountCaveat");
		return false;
	} else {
// getEle("qqAccountCaveat").innerHTML = "";
// getEle("weChatAccountCaveat").innerHTML = "";
		setIconPass("qqAccountCaveat");
		setIconPass("weChatAccountCaveat");
		return true;
	}
}

function checkInputTextData(id, val) {
	if (val != "" && val != null) {
		val = checkInputTextVal(val);
		if (val != "" && val != null) {
			getEle(id).value = val;
			return true;
		} else {
			getEle(id).value = "";
			return false;
		}
	} else {
		getEle(id).value = "";
		return false;
	}
}

function checkNickname() {
	if (getEle("nickName").value != "") {
// getEle("nickNameCaveat").innerHTML = "";
		setIconPass("nickNameCaveat");
		return true;
	} else {
// getEle("nickNameCaveat").innerHTML = "此欄位必填";
		setIconFail("nickNameCaveat");
		return false;
	}
}

function checkMemberRealName() {
	if (getEle("memberRealNameData").innerHTML == '') {
		if (getEle("memberRealName").value != '') {
// getEle("memberRealNameCaveat").innerHTML = "";
			
			setIconPass("memberRealNameCaveat");
			return true;
		} else {
// getEle("memberRealNameCaveat").innerHTML = "此欄位必填";
			setIconFail("memberRealNameCaveat");
			return false;
		}
	} else {
// getEle("memberRealNameCaveat").innerHTML = "";
// setIconFail("memberRealNameCaveat");
		
		getEle("memberRealName").value = getEle("memberRealNameData").innerHTML;
		return true;
	}
}

function confomSaveDetails() {
	var memberRealName = getEle("memberRealNameData").innerHTML != '' ? getEle("memberRealNameData").innerHTML : getEle("memberRealName").value;
	var nickname = getEle("nickName").value;
	var phoneNumber = getEle("phoneNumber").value;
	var qqAcc = getEle("qqAccount").value;
	var wechatAcc = getEle("weChatAccount").value;
	
	var checkNicknames = checkNickname();
	var checkPhoneNumbers = checkPhoneNumber();
	var checkMemberRealNames = checkMemberRealName();
	var checkQQAndWechats = checkQQAndWechat();

	if (checkNicknames && checkPhoneNumbers && checkMemberRealNames && checkQQAndWechats) {
		updateAccDetailsAjax(memberRealName, phoneNumber, qqAcc, wechatAcc,
				nickname);
	}

}

function updateAccDetailsAjax(memberRealName, phoneNumber, qqAcc, wechatAcc,nickname){
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)+"&accId=" + encodeURIComponent(getEle("accId").value)
			+"&memberRealName="+memberRealName+"&phoneNumber="+phoneNumber+"&qqAcc="+qqAcc+"&wechatAcc="+wechatAcc+"&nickname="+nickname;
		
		XHR.open("POST", "./AccountMember!updateAccDetails.php?date="
				+ new Date().getTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			XHR.abort();
		}
		XHR.onerror = function() {
			XHR.abort();
		}
		XHR.onreadystatechange = handleUpdateAccDetailsAjax;
		XHR.send(tmpStr);
	}
}

function handleUpdateAccDetailsAjax() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json=JSON.parse(XHR.responseText);
				}
			} catch (error) {
				console_Log("handleUpdateAccDetailsAjax error:"+error.message);
			} finally {
				XHR.abort();
				if (checkTokenIdfail(json)) {
					if (json.isSuccess) {
						getAccDetailsAjax();
					}
				}
			}
		}
	}
}

function confromAddBankCard() {
	var bank = getEle("bank").value;
	var bankAccName = getEle("hiddenRealName").value;
	var bankAcc = getEle("bankAcc").value;
	var bankCardBranches = getEle("bankCardBranches").value;
	var area = getEle("area").value;
	var str = '';
	var chkBanks = chkBank();
	var chkBankAccNames = chkBankAccName();
	var chkBankAccs = chkBankAcc();
	var chkBankCardBranchess = chkBankCardBranches();
	var chkAreas = chkArea();
	
	if(chkBanks && chkBankAccNames && chkBankAccs && chkBankCardBranchess && chkAreas){
		str = "bank="+bank+"&bankAccName="+bankAccName+"&bankAcc="+bankAcc+"&bankCardBranches="+bankCardBranches+"&area="+area;
		if(str != ''){
			addBankCardAjax(str);
		}
	} else {
		showModelPageType1("系統訊息","所有欄位皆須填寫","確定");
	}
}
function chkBank() {
	if (getEle("bank").value != "") {
		setIconPass("bankIcon");
		return true;
	} else {
		setIconFail("bankIcon");
		return false;
	}
}
function chkBankAccName() {
	if (getEle("bankAccName").value != "") {
		setIconPass("bankAccNameIcon");
		return true;
	} else {
		setIconFail("bankAccNameIcon");
		return false;
	}
}
function chkBankAcc() {
	if (getEle("bankAcc").value != "") {
		setIconPass("bankAccIcon");
		return true;
	} else {
		setIconFail("bankAccIcon");
		return false;
	}
}
function chkBankCardBranches() {
	if (getEle("bankCardBranches").value != "") {
		setIconPass("bankCardBranchesIcon");
		return true;
	} else {
		setIconFail("bankCardBranchesIcon");
		return false;
	}
}
function chkArea() {
	if (getEle("area").value != "") {
		setIconPass("areaIcon");
		return true;
	} else {
		setIconFail("areaIcon");
		return false;
	}
}


function addBankCardAjax(str) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)+"&accId=" + encodeURIComponent(getEle("accId").value)+"&"+str;
		XHR.open("POST", "./AccountMember!addBankCard.php?date="
				+ new Date().getTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			XHR.abort();
		}
		XHR.onerror = function() {
			XHR.abort();
		}
		XHR.onreadystatechange = handleAddBankCard;
		XHR.send(tmpStr);
	}
}

function handleAddBankCard() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (json.isSuccess) {
							showModelPageType1("系統訊息","新增銀行卡成功","確定");
							closeBankCardDiv();
							cleanAddBankCardText();
						} else {
							showModelPageType1("系統訊息","新增銀行卡失敗","確定");
							cleanAddBankCardText();
						}
					}
				}
			} catch (error) {
				console_Log("handleAddBankCard error:"+error.message);
			} finally {
				XHR.abort();
				if (json.isSuccess) {
					openAddAccBankCardDiv();
				}
			}
		}
	}
}
function cleanAddBankCardText() {
	getEle("bank").value = '';
	getEle("bankAcc").value = '';
	getEle("bankCardBranches").value = '';
	getEle("area").value = '';
	clearIcon("bankIcon");
	clearIcon("bankAccNameIcon");
	clearIcon("bankAccIcon");
	clearIcon("bankCardBranchesIcon");
	clearIcon("areaIcon");
}
function cleanBankCardInfo() {
	getEle("Card1").innerHTML = '';
	getEle("Bank1").innerHTML = '';
	getEle("BankAcc1").innerHTML = '';
	getEle("BankCard1").innerHTML = '';
	getEle("BankBranches1").innerHTML = '';
	getEle("Card2").innerHTML = '';
	getEle("Bank2").innerHTML = '';
	getEle("BankAcc2").innerHTML = '';
	getEle("BankCard2").innerHTML = '';
	getEle("BankBranches2").innerHTML = '';
}

function getBankInfoAjax() {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)
				+ "&accId=" + encodeURIComponent(getEle("accId").value);
		XHR.open("POST", "./AccountMember!getBankCardInfo.php?date="
				+ new Date().getTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			XHR.abort();
		}
		XHR.onerror = function() {
			XHR.abort();
		}
		XHR.onreadystatechange = handleGetBankInfo;
		XHR.send(tmpStr);
	}
}

function handleGetBankInfo() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					getEle("bankCardInfo").value = XHR.responseText;
					cleanBankCardInfo();
					getBankCardInfo();
					cleanWithdrawInfo();
					
					setIdDisplay("canNotWithdrawDiv","none");
					setIdDisplay("canWithdrawDiv","none");
					var bankCardInfo = JSON.parse(getEle("bankCardInfo").value).bankCardInfo;
					if(bankCardInfo.length == 0){
						setIdDisplay("canNotWithdrawDiv","block");
					}else if(bankCardInfo.length == 1 || bankCardInfo.length == 2){
						setIdDisplay("canWithdrawDiv","block");
					}
					marqueeDivAlignCenter();
					showLockAmount();
					
				}
			} catch (error) {
				console_Log("handleGetBankInfo error:"+error.message);
			} finally {
				XHR.abort();
			}
		}
	}
}

function getBankCardInfo() {
	var bankCardInfo = JSON.parse(getEle("bankCardInfo").value).bankCardInfo;
	var realName = getEle("hiddenRealName").value;
	if(Object.keys(bankCardInfo).length == 0 && realName != ""){
		setDisplayOpen("canAdd");
		getEle("addBankCardSlogan").innerHTML = '可新增銀行卡，請點擊綁定！';
	}else if(Object.keys(bankCardInfo).length == 1 && realName != ""){
		setDisplayOpen("canAdd");
		getEle("addBankCardSlogan").innerHTML = '可新增銀行卡，請點擊綁定！';
		getEle("CardDiv1").style = "display:block";
	} else if (Object.keys(bankCardInfo).length == 2 && realName != "") {
		setDisplayOpen("donotAdd");
		getEle("addBankCardSlogan").innerHTML = '如需修改銀行卡資訊，請與客服人員聯繫!';
		getEle("CardDiv1").style = "display:block";
		getEle("CardDiv2").style = "display:block";
	}else if(Object.keys(bankCardInfo).length < 2 && realName == ""){
		setDisplayOpen("donotAdd");
		getEle("addBankCardSlogan").innerHTML = '請先設定帳戶資料!';
	}else {
//		alert("getBankCardInfo error");
	}
	for (var i = 0; i < Object.keys(bankCardInfo).length; i++) {
		if (bankCardInfo[i].bank != '' && bankCardInfo[i].bank != undefined
				&& typeof bankCardInfo[i].bank != 'undefined') {
			getEle("Card" + (i + 1)).innerHTML = "銀行卡";
			getEle("Bank" + (i + 1)).innerHTML = "銀行名稱：" + bankCardInfo[i].bank;
			getEle("BankAcc" + (i + 1)).innerHTML = "帳戶名稱："
					+ bankCardInfo[i].bankAccName;
			getEle("BankCard" + (i + 1)).innerHTML = "卡號："
					+ bankCardInfo[i].bankAcc;
			getEle("BankBranches" + (i + 1)).innerHTML = "開戶行："
					+ bankCardInfo[i].bankCardBranches;
		}
	}
}

function getPlatformCashBcAjax() {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "platformId="
				+ encodeURIComponent(getEle("platformId").value);
		XHR.open("POST", "./AccountMember!getPlatformCashBc.php?date="
				+ new Date().getTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			XHR.abort();
		}
		XHR.onerror = function() {
			XHR.abort();
		}
		XHR.onreadystatechange = handleGetPlatformCashBcAjax;
		XHR.send(tmpStr);
	}
}

function handleGetPlatformCashBcAjax() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					getEle("platformCashBC").value = json.cashBcName;
				}
			} catch (error) {
			} finally {
				XHR.abort();
			}
		}
	}
}

function checkPwd(id) {
	if (typeof getEle(id) != "undefined") {
		if (getEle(id).value.length >= PASS_WORD_MIN_LENGTH && getEle(id).value.length <= PASS_WORD_MAX_LENGTH) {
			if (checkPassWordVal(getEle(id).value)) {
				setIconPass(id + "Warning");
				return true;
			} else {
				setIconFail(id + "Warning");
			}
		} else {
			setIconFail(id + "Warning");
		}
	}
	return false;
}

function checkRegisteredAccName() {
	if (typeof getEle("registeredAccName") != "undefined") {
		var registeredAccName = checkAccount(getEle("registeredAccName").value);
		if (registeredAccName.length >= ACC_NAME_MIN_LENGTH && registeredAccName.length <=ACC_NAME_MAX_LENGTH) {
			checkMemberAccAjax(registeredAccName);
		}
	}
}

function basiCcheckRegisteredAccName(){
	if (typeof getEle("registeredAccName") != "undefined") {
		var registeredAccName = checkAccount(getEle("registeredAccName").value);
		if (registeredAccName.length >= ACC_NAME_MIN_LENGTH && registeredAccName.length <=ACC_NAME_MAX_LENGTH) {
			setIconPass("registeredAccNameWarning");
			return true;
		}
	}
	setIconFail("registeredAccNameWarning");
	return false;
}

function clearRegistered() {
	getEle("registeredAccName").value = "";
	getEle("registeredPwd").value = "";
	getEle("registeredCheckPwd").value = "";
	getEle("registeredVerification").value = "";

	clearIcon("registeredAccNameWarning");
	clearIcon("registeredPwdWarning");
	clearIcon("registeredCheckPwdWarning");
	clearIcon("registeredVerificationWarning");
	clearIcon("registeredCheckBoxWarning");
	
	getEle("registeredAccNameWarning").innerHTML = "";
	getEle("registeredPwdWarning").innerHTML = "";
	getEle("registeredCheckPwdWarning").innerHTML = "";
	getEle("registeredVerificationWarning").innerHTML = "";
	getEle("registeredCheckBoxWarning").innerHTML = "";

	showRegisteredVerificationText();
}

function showRegisteredVerificationText() {
	getEle("registeredVerificationText").innerHTML = getVerificationCode();
}

function onChangeRegisteredCheckBox() {
	if (getEle("registeredCheckBox").checked) {
		setIconPass("registeredCheckBoxWarning");
		// getEle("registeredConfom").disabled = false;
		return true;
	} else {
		setIconFail("registeredCheckBoxWarning");
		// getEle("registeredConfom").disabled = true;
		return false;
	}
}

function comparisonPwd(pwdId, checkPwdId) {
	if (checkPwd(pwdId) && checkPwd(checkPwdId)) {
		if (getEle(pwdId).value == getEle(checkPwdId).value) {
			setIconPass(pwdId + "Warning");
			setIconPass(checkPwdId + "Warning");
			return true;
		} else {
			setIconFail(pwdId + "Warning");
			setIconFail(checkPwdId + "Warning");
		}
	}
	return false;
}

function confomAddAccountMem() {
	var accName =getEle("registeredAccName").value;
	var pwd = getEle("registeredPwd").value;

	var checkRegisteredCheckPwd = checkPwd("registeredCheckPwd");
	var checkRegisteredPwd = checkPwd("registeredPwd");
	var comparisonRegisteredPwd = comparisonPwd("registeredCheckPwd",
			"registeredPwd");
	
	 var checkAccName = basiCcheckRegisteredAccName();
	
	var checkRegisteredCheckBox = onChangeRegisteredCheckBox();
	var checkRegisteredVerifications = checkRegisteredVerification();
	
	if (checkRegisteredCheckBox && checkRegisteredCheckPwd && checkRegisteredPwd 
			&& comparisonRegisteredPwd && checkRegisteredVerifications && checkAccName) {
		addCashMemAccAjax(accName, pwd);
	}

}

function checkMemberAccAjax(accName) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "accName=" + encodeURIComponent(accName);
		XHR.open("POST", "./AccountMember!checkMemberAcc.php?date="
				+ new Date().getTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			XHR.abort();
		}
		XHR.onerror = function() {
			XHR.abort();
		}
		XHR.onreadystatechange = handleCheckMemberAccAjax;
		XHR.send(tmpStr);
	}
}
function checkRechargeOrderAmount() {
	getEle("rechargeAmount").value = checkInputNumberVal(getEle("rechargeAmount").value);
}
function checkOpenRechargeInfoDiv() {
	var amount = getEle("rechargeAmount").value;
	var accName = JSON.parse(getEle("accDataJson").value).accName;
	var maxAmount = getEle("maxAmount").value;
	var minAmount = getEle("minAmount").value;
	var cashierId = 1;
	var selecthrList = "";
	
	if(amount >= 100 && amount <= 200000){
		getEle("rechargeAmountCheck").value = amount;
		getEle("rechargeAccName").value = accName;
		getEle("cashierNoteCheck").value = cashierNote;
		getCashierInfoAjax(cashierId);
		openInfoDiv();
	}else{
		showModelPageType1("系統訊息","請輸入正確金額","確定");
	}
}
function cleanRechargeInfo(){
	getEle("rechargeBankSid").value = '';
	getEle("rechargeBankAccName").value = '';
	getEle("rechargeBankAcc").value = '';
	getEle("rechargeBank").value = '';
	getEle("rechargeBankDate").value = '';
	
	getEle("rechargeAmount").value = '';
	
	clearIcon("rechargeBankSidIcon");
	clearIcon("rechargeAccNameIcon");
	clearIcon("rechargeAmountCheckIcon");
	clearIcon("cashierNoteCheckIcon");
	clearIcon("rechargeBankAccNameIcon");
	clearIcon("rechargeBankAccIcon");
	clearIcon("rechargeBankIcon");
	clearIcon("rechargeBankDateIcon");
}
function checkWithdrawalOrderAmount() {
	var accDataJson = JSON.parse(getEle("accDataJson").value);
	var balance = accDataJson.balance;
	if(balance <= 200000){
		if(getEle("withdrawalAmount").value <= balance){
			getEle("withdrawalAmount").value = checkInputNumberVal(getEle("withdrawalAmount").value);
		}else if(getEle("withdrawalAmount").value > balance){
			getEle("withdrawalAmount").value = balance;
		}
	}else if(balance > 200000){
		if(getEle("withdrawalAmount").value <= 200000){
			getEle("withdrawalAmount").value = checkInputNumberVal(getEle("withdrawalAmount").value);
		}else if(getEle("withdrawalAmount").value > 200000){
			getEle("withdrawalAmount").value = 200000;
		}
	}
}
function getBankInfoList() {
	var jsonObj = JSON.parse(getEle("bankCardInfo").value);
	var bankCardInfo = jsonObj.bankCardInfo;
	var tmpStr = "";
	removeAllOption('bankInfoList');
	for(var i = 0; i < Object.keys(bankCardInfo).length; i++){
		tmpStr = bankCardInfo[i].bank+' '+bankCardInfo[i].bankAccName;
		if(tmpStr != ""){
			addOptionNoDup('bankInfoList', (i+1)+". "+tmpStr, bankCardInfo[i].bankCardId);
		}
	}
}
function changeBankCard() {
	var bankCard = getEle("bankInfoList").value;
}
function cleanWithdrawInfo(){
	getEle("withdrawalAmount").value = '';
	getEle("orderWithdrawPwd").value = '';
	getBankInfoList();
}
function getCashierInfoAjax(cashierId){
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)+"&accId=" + encodeURIComponent(getEle("accId").value) + "&cashierId=" + cashierId;
		XHR.open("POST", "./AccountMember!getCashierInfo.php?date="
				+ new Date().getTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			XHR.abort();
		}
		XHR.onerror = function() {
			XHR.abort();
		}
		XHR.onreadystatechange = handleGetCashierInfo;
		XHR.send(tmpStr);
	}
}

function handleCheckMemberAccAjax() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if (typeof json.isCheckMemberAcc != "undefined"
							&& json.isCheckMemberAcc != null
							&& json.isCheckMemberAcc) {
						setIconPass("registeredAccNameWarning");
					} else {
						setIconFail("registeredAccNameWarning");
					}
				}
			} catch (error) {
			} finally {
				XHR.abort();
			}
		}
	}
}

function addCashMemAccAjax(accName, pwd) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "accName=" + encodeURIComponent(accName) + "&pwd="
				+ encodeURIComponent(pwd) + "&platformId="
				+ encodeURIComponent(getEle("platformId").value);
		XHR.open("POST", "./AccountMember!addCashMemAcc.php?date="
				+ new Date().getTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			XHR.abort();
		}
		XHR.onerror = function() {
			XHR.abort();
		}
		XHR.onreadystatechange = handleAddCashMemAccAjax;
		XHR.send(tmpStr);
	}
}

function handleAddCashMemAccAjax() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if (typeof json.isInsertAccMem != "undefined"
							&& json.isInsertAccMem != null
							&& json.isInsertAccMem) {
						clearRegistered();
						showModelPageType1("系統訊息","註冊成功","確定",webRefresh(),true);
						
					} else {
						
						getEle("registeredPwd").value = "";
						getEle("registeredCheckPwd").value = "";
						getEle("registeredVerification").value = "";

						getEle("registeredPwdWarning").innerHTML = "";
						getEle("registeredCheckPwdWarning").innerHTML = "";
						getEle("registeredVerificationWarning").innerHTML = "";
						getEle("registeredCheckBoxWarning").innerHTML = "";
						showRegisteredVerificationText();
						showModelPageType1("系統訊息","註冊失敗","確定");
					}

				}
			} catch (error) {
			} finally {
				XHR.abort();
			}
		}
	}
}

function updateMemPwd() {
	var oldPwd = getEle("memOldPwd").value;
	var newPwd = getEle("memNewPwd").value;

	if (checkPwd("memNewPwd") && checkPwd("memOldPwd")
			&& checkPwd("memCheckNewPwd")
			&& comparisonPwd("memNewPwd", "memCheckNewPwd")) {
		updateMemPwdAjax(oldPwd, newPwd)
	}
}

function clearUpdateMemPwd() {
	getEle("memOldPwd").value = "";
	getEle("memNewPwd").value = "";
	getEle("memCheckNewPwd").value = "";

	getEle("memOldPwdWarning").innerHTML = "";
	getEle("memNewPwdWarning").innerHTML = "";
	getEle("memCheckNewPwdWarning").innerHTML = "";

}

function addGameIframe(){
	var iframe = document.createElement('iframe');
	var gameArea = getEle("gameArea");
	iframe.name = "gameIframe";
	iframe.id = "gameIframe";
	iframe.className = "iframe-fix";
	gameArea.insertBefore(iframe,getEle("startGameForm").nextSibling);
}

function closeGameIframe(){
	var someIframe = window.parent.document.getElementById('gameIframe');
	someIframe.parentNode.removeChild(someIframe);
}
function handleGetCashierInfo(){
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					getEle("getCashierInfo").value = XHR.responseText;
					showCashierInfo();
				}
			} catch (error) {
				console_Log("handleGetCashierInfo error:"+error.message);
			} finally {
				XHR.abort();
			}
		}
	}
}

function showCashierInfo() {
	var cashierInfo = JSON.parse(getEle("getCashierInfo").value);
	var note = Math.random().toString(36).substring(7);
	
		getEle("cashierName").innerHTML = "收款帳戶名:"+cashierInfo.cashierName;
		getEle("cashierAcc").innerHTML = "收款帳號:"+cashierInfo.bankAcc;
		getEle("cashierBank").innerHTML = "收款銀行:"+cashierInfo.bank;
		getEle("cashierData").innerHTML = "說明:"+cashierInfo.data;
		getEle("cashierNote").innerHTML = "附言:"+note;
		
		getEle("cashierNoteCheck").value = note;
		getEle("cashierNoteCheck").innerHTML = note;
	
}
function copyCashierName(){
	var cashierInfo = JSON.parse(getEle("getCashierInfo").value);
	var cashierName = cashierInfo.cashierName;
	if(cashierName != ''){
		copyStr(cashierName);
	}
}
function copyCashierAcc(){
	var cashierInfo = JSON.parse(getEle("getCashierInfo").value);
	var bankAcc = cashierInfo.bankAcc;
	if(bankAcc != ''){
		copyStr(bankAcc);
	}
}
function copyCashierbank(){
	var cashierInfo = JSON.parse(getEle("getCashierInfo").value);
	var bank = cashierInfo.bank;
	if(bank != ''){
		copyStr(bank);
	}
}
function copyCashierNote(){
	var cashierNote = getEle("cashierNoteCheck").value;
	if(cashierNote != ''){
		copyStr(cashierNote);
	}
}
function chkInputBank() {
	getEle("bank").value = checkInputNameOutVal(getEle("bank").value);
}
function chkInputBankAccName() {
	getEle("bankAccName").value = checkInputNameOutVal(getEle("bankAccName").value);
}
function chkInputBankBranches() {
	getEle("bankCardBranches").value = checkInputNameOutVal(getEle("bankCardBranches").value);
}
function chkInputBankArea() {
	getEle("area").value = checkInputNameOutVal(getEle("area").value);
}

function chkInputRechargeBankSid() {
	getEle("rechargeBankSid").value = checkInputTextVal(getEle("rechargeBankSid").value);
}
function chkInputRechargeAccName() {
	getEle("rechargeBankAccName").value = checkInputNameOutVal(getEle("rechargeBankAccName").value);
}
function chkInputRechargeBankAcc() {
	getEle("rechargeBankAcc").value = checkBankAccInputNumberVal(getEle("rechargeBankAcc").value);
}
function chkInputRechargeBank() {
	getEle("rechargeBank").value = checkInputNameOutVal(getEle("rechargeBank").value);
}

function chkInputOrderWithdrawPwd() {
	getEle("orderWithdrawPwd").value = checkInputPassWordVal(getEle("orderWithdrawPwd").value);
}

function chkInputLoginPwd() {
	getEle("pwd").value = checkInputPassWordVal(getEle("pwd").value);
}
function chkInputVerificationCode() {
	getEle("verificationCode").value = checkInputNumberVal(getEle("verificationCode").value);
}

function chkInputRegisteredVerification() {
	getEle("registeredVerification").value = checkInputNumberVal(getEle("registeredVerification").value);
}
function checkRegisteredVerification(){
	var registeredVerification = getEle("registeredVerification").value;
	var registeredVerificationText = getEle("registeredVerificationText").innerHTML;
	
	if(registeredVerification == registeredVerificationText){
		setIconPass("registeredVerificationWarning");
		return true;
	}
	else{
		setIconFail("registeredVerificationWarning");
		return false;
	}
}

function showLockAmount() {
	var accDataJson = JSON.parse(getEle("accDataJson").value);
	var balance = accDataJson.balance;
	var str = "當前可提款金額:";
	var dayMax = "1000000";
	if(balance <= 200000){
		getEle("lockAmount").innerHTML = str+balance+" 當日提款上限:"+balance*5;
	}else{
		getEle("lockAmount").innerHTML = str+"200000"+" 當日提款上限:"+dayMax;
	}
	balance = null;
	delete balance;
	str = null;
	delete str;
}
function setIconFail(id){
	if(typeof getEle(id) != "undefined" && getEle(id) != null){
		if(getEle(id).classList.contains("icon-clear")){
			getEle(id).classList.remove("icon-clear");
		}
		if(getEle(id).classList.contains("icon-pass")){
			getEle(id).classList.remove("icon-pass");
		}
		if(!getEle(id).classList.contains("icon-fail")){
			getEle(id).classList.add("icon-fail");
		}
	}
}
function setIconPass(id){
	if(typeof getEle(id) != "undefined" && getEle(id) != null){
		if(getEle(id).classList.contains("icon-clear")){
			getEle(id).classList.remove("icon-clear");
		}
		if(getEle(id).classList.contains("icon-fail")){
			getEle(id).classList.remove("icon-fail");
		}
		if(!getEle(id).classList.contains("icon-pass")){
			getEle(id).classList.add("icon-pass");
		}
	}
}
function clearIcon(id){
	if(typeof getEle(id) != "undefined" && getEle(id) != null){
		if(getEle(id).classList.contains("icon-pass")){
			getEle(id).classList.remove("icon-pass");
		}
		if(getEle(id).classList.contains("icon-fail")){
			getEle(id).classList.remove("icon-fail");
		}
		if(!getEle(id).classList.contains("icon-clear")){
			getEle(id).classList.add("icon-clear");
		}
	}
}
function showUpdatePwdDiv(){
	setDisplayOpen("updatePwdDiv");
	setIdDisplay("updatePwdDiv", "block");
	getEle("oldPwd").value = "";
	getEle("newPwd").value = "";
	getEle("checkPwd").value = "";
	clearIcon("newPwdWarning");
	clearIcon("oldPwdWarning");
	clearIcon("checkPwdWarning");
	
}
function showUpdateWithdrawPwdDiv(){
	setDisplayOpen("updateWithdrawPwdDiv");
	setIdDisplay("updateWithdrawPwdDiv", "block");
	getEle("withdrawPwd").value = "";
	getEle("checkWithdrawPwd").value = "";
	clearIcon("withdrawPwdRemind");
	clearIcon("checkWithdrawPwdRemind");
}

function backGame(){
	closeGameArea();
    setDisplayClose("gameArea");
    closeGameIframe();
    addGameIframe();
    
    openHomePageDiv();
}

function setMetaTag(){
	
	var now = new Date();
	var time = now.getTime();
	var expireTime = time + (24*60*60*1000);
	now.setTime(expireTime);
	
	var metaTag = document.createElement("meta");
	metaTag.httpEquiv = "Pragme";
	metaTag.content = "no_cache";
	
	var metaTag2 = document.createElement("meta");
	metaTag2.name = "expired";
	metaTag2.content = now;
	
	document.getElementsByTagName("body")[0].appendChild(metaTag);
	document.getElementsByTagName("body")[0].appendChild(metaTag2);
}

function setCookie(){
	var date = new Date();
	var mTime = date.getTime();
	var expires = date.setTime(mTime + (10000));
	var json = {"errCount":"0"};
	if(getCookie("data") == ""){
		setCookieVal("data",JSON.stringify(json),expires)
	}
}

function setLoginFailCookie(isLogin=false){
	var date = new Date();
	var mTime = date.getTime();
	var expires = date.setTime(mTime + (10000));
	
	if(isLogin == false && getCookie("data") != ""){
		if(isJSON(getCookie("data"))){
			var json = JSON.parse(getCookie("data"));
			if(typeOf(json.errCount) != "undefined"){
				json.errCount = toInt(json.errCount)+1;
				setCookieVal("data",JSON.stringify(json),expires);
			}
		}
	}
	else if(getCookie("data") == ""){
		setCookie();
	}
}

function chackCookieLoginFailCount(){
	if(getCookie("data") != ""){
		if(isJSON(getCookie("data"))){
			var json = JSON.parse(getCookie("data"));
			if(typeOf(json.errCount) != "undefined" && json.errCount >= 5 ){
				return false;
			}
		}
	}
	else{
		setCookie();
	}
	
	return true;
}


function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function setCookieVal(key,val,expires){
	var expiresTime = 0;
	if(checkStrIsDate(expires)){
		expiresTime = new Date(expires).toGMTString(); 
	}	
	document.cookie = key+"="+decodeURIComponent(val)+";expires="+expiresTime+"; path=/";
}

function onClickPreviousPage(page){
	switch(page){
		case 0 :
			webRefresh(true);
			break;
		case 1 :
			openHomePageDiv();
			break;
		case 2 :
			break;
		case 3 :
			break;
		case 4 :
			break;
		case 5 :
			break;
		case 6 :
			break;
		case 7 :
			break;
		case 8 :
			break;
	}
}

function enableFirstMenu(){
	setDisplayOpen("firstMenu0Ul");
	setDisplayOpen("firstMenu1Ul");
	setDisplayOpen("firstMenu2Ul");
	setDisplayOpen("firstMenu3Ul");
	
}
function openGameRecordDiv(val) {
	getEle("gameTotleRecordstable").innerHTML = '<tr><th>遊戲名稱</th><th>總投注金額</th><th>總贏分</th><th>總淨分</th><th>詳細</th></tr>';
	getEle("gameRecordstable").innerHTML = '';
	getEle('gameRecordsSearchInfo').value = val;
	getEle('gameRecordsData').value = '';
	getEle('gameRecordStartTime').value = '';
	getEle('gameRecordEndTime').value = '';
	if(checkLogin()){
		clearAllButtonLight();
		closeAllDiv();
		setDisplayOpen("gameRecordDiv");
		setButtonLight(val,gameRecordButton,"button-active");
		marqueeDivAlignRight();
	}
	
	switch(val){
	case PUNCH_GAME_JS :
		confromSearchGmaeRecords()
		break;
	}
}
function confromSearchGmaeRecords(pageInfo){
	var json = '';
	var gameName = getEle('gameRecordsSearchInfo').value+1;
	var count = getEle('gameRecordCountList').value; 
	var str = '';
	var now = new Date();
	var obj = {};
	if(!isNull(getEle('accId').value)){
		obj["accId"] = getEle('accId').value;
	}
	if(isJSON(getEle('accDataJson').value)){
		json = JSON.parse(getEle('accDataJson').value);
		if(!isNull(json.accName)){
			obj["accName"] = json.accName;
		}
	}
	if(!isNull(getEle('gameRecordStartTime').value)){
		if(getEle('gameRecordStartTime').value != ''){
			obj["startTime"] = getEle('gameRecordStartTime').value;
		}else{
			obj["startTime"] = now.getFromFormat("yyyy/mm/dd") + ' 00:00:00';
			getEle('gameRecordStartTime').value = now.getFromFormat("yyyy/mm/dd") + ' 00:00:00';
		}
	}
	if(!isNull(getEle('gameRecordEndTime').value)){
		if(getEle('gameRecordEndTime').value != ''){
			obj["endTime"] = getEle('gameRecordEndTime').value;
		}else{
			obj["endTime"] = now.getFromFormat("yyyy/mm/dd") + ' 23:59:59';
			getEle('gameRecordEndTime').value = now.getFromFormat("yyyy/mm/dd") + ' 23:59:59';
		}
	}
	if(isNull(pageInfo)){
		pageInfo = '';
	}
	if(obj["accId"] != 0 && obj["accName"] != '' && obj["startTime"] != '' && obj["endTime"] != ''){
		obj["tokenId"] = getEle("tokenId").value;
		obj["gameName"] = gameName;
		obj["count"] = count;
		str = joinVars("&", obj, false);
		if(pageInfo != ''){
			str = str + pageInfo;
		}
	}
	if(str != '' && gameName == 1){
		searchGameRecordsAjax(str)
	}
}
var XHR_GameRecords = null;
function searchGameRecordsAjax(str){
	XHR_GameRecords = checkXHR(XHR_GameRecords);
	if (typeof XHR_GameRecords != "undefined" && XHR_GameRecords != null) {
		XHR_GameRecords.timeout = 10000;
		XHR_GameRecords.ontimeout = function() {
			XHR_GameRecords.abort();
		}
		XHR_GameRecords.onerror = function() {
			XHR_GameRecords.abort();
		}
		XHR_GameRecords.onreadystatechange = handlesearchGameRecordsAjax;
		XHR_GameRecords.open("POST", "./AccountMember!searchRecords.php?date="
				+ new Date().getTime(), true);
		XHR_GameRecords.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		console_Log(str);
		XHR_GameRecords.send(str);
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
// getEle("gameRecordsSearchTime").innerHTML = json.searchTime;
						}
						showGameTotleRecordstable();
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
				XHR_GameRecords.abort();
			}
		} else {
			XHR_GameRecords.abort();
		}
	}
}
function showGameTotleRecordstable(){
	var json = '';
	var showInfoTable = '<tr><th>遊戲名稱</th><th>總投注金額</th><th>總贏分</th><th>總淨分</th><th>詳細</th></tr>';
	
	var gameName = '';
	var totleBet = 0;
	var totleWinGoal = 0;
	var totleNetAmount = 0;
	
	if(isJSON(getEle("gameRecordsData").value) && !isNull(getEle("gameRecordsData").value)){
		json = JSON.parse(getEle("gameRecordsData").value);
		if(!isNull(json.gameName)){
			gameName = json.gameName;
		}
		if(!isNull(json.totleBet)){
			totleBet = json.totleBet;
		}
		if(!isNull(json.totleWinGoal)){
			totleWinGoal = json.totleWinGoal;
		}
		if(!isNull(json.totleNetAmount)){
			totleNetAmount = json.totleNetAmount;
		}
	}
	if(gameName != ''){
		showInfoTable += tableHTML([gameName,totleBet,totleWinGoal,totleNetAmount,'<button onclick="openEachGameRecordDiv();">查看</button>\n']);
	}
	getEle("gameTotleRecordstable").innerHTML = showInfoTable;
}
function openEachGameRecordDiv(){
	getEle("gameRecordSearch").style.display = 'none';
	getEle("eachGameRecord").style.display = 'block';
	getEle("backToGameTotleRecords").style.display = 'block';
}
function backToGameTotleRecordsDiv(){
	getEle("gameRecordSearch").style.display = 'block';
	getEle("eachGameRecord").style.display = 'none';
	getEle("backToGameTotleRecords").style.display = 'none';
}
function showGameRecordsTable(){
	var json = '';
	var josnObject = '';
	var showInfoTable = '<tr><th>遊戲名稱</th><th>帳號</th><th>局號</th><th>遊戲類型</th><th>開始時間</th><th>結束時間</th><th>開始金額</th><th>結束金額</th><th>投注金額</th><th>贏分</th><th>淨分</th><th>總局數</th><th>詳細</th></tr>';
	
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
		if(!isNull(josnObject[i].gameTimes)){
			gameTimes = josnObject[i].gameTimes;
		}
		if(!isNull(strToJson(josnObject[i].gameProcess))){
			gameProcess = josnObject[i].gameProcess;
		}
		showInfoTable += tableHTML([gameName,accName,gameId,GAME_TYPE_JS[gameType],startTime,endTime,startBalance,endBalance,bet,winGoal,netAmount,gameTimes,'<button onclick="openRecordDiv2('+i+');">查看</button>\n']);
	}
	getEle("gameRecordstable").innerHTML = showInfoTable;
}
// 遊戲紀錄查詢＿查詢
function openRecordDiv2(i){
    setIdDisplay("recordDiv2", "block");
    showGameRecordsDetailModalV2(i);
}
function closeRecord2Btn(){
    setIdDisplay("recordDiv2", "none");
    getEle("recordDiv2Table").innerHTML = '';
}
function showGameRecordsDetailModalV2(i){
	var accInfo = '';
	var accname = '';
	var json = '';
	var jsonObject = '';
	var gameId = '';
	var gameProcess = '';
	var p1Punch = 0;
	var p2Punch = 0;
	var winner = '---';
	var str = [];
	var data = '';
	try{
		if(isJSON(getEle("accDataJson").value) && !isNull(getEle("accDataJson").value)){
			accInfo = JSON.parse(getEle("accDataJson").value);
			if(!isNull(accInfo.accName)){
				accname = accInfo.accName;
			}
		}
		if(isJSON(getEle("gameRecordsData").value) && !isNull(getEle("gameRecordsData").value)){
			json = JSON.parse(getEle("gameRecordsData").value);
			if(!isNull(json.punchGameRecords)){
				josnObject = json.punchGameRecords;
			}
		}
		if(!isNull(josnObject[i].gameId)){
			gameId = josnObject[i].gameId;
		}
		if(!isNull(strToJson(josnObject[i].gameProcess))){
			gameProcess = josnObject[i].gameProcess;
		}
			data = '<tr><th>局號:'+gameId+'</th><th>贏家</th><th>拳種</th></tr>';
			for(var i = 0; i < gameProcess.length; i++){
				if(!isNull(gameProcess[i].p1)){
					p1Punch = gameProcess[i].p1;
				}
				if(!isNull(gameProcess[i].p2)){
					p2Punch = gameProcess[i].p2;
				}
				if(!isNull(gameProcess[i].winName)){
					winner = gameProcess[i].winName;
					if(winner != accname){
						winner = winner.slice(0,1)+'****'+winner.slice(5)
					}
				}
				const PUNCH_TYPE_JS = {0:"---",123:"布",125:"剪",127:"石"};
				switch(p1Punch,p2Punch){
				case p1Punch == 123 && p2Punch == 125:
					break;
				}
				
				data += tableHTML([(i+1),winner,PUNCH_TYPE_JS[p1Punch]+'/'+PUNCH_TYPE_JS[p2Punch]])
			}
			getEle("recordDiv2Table").innerHTML = data;
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
	}
}
function gameRecordChoseToday(){
	var now = new Date();
	getEle('gameRecordStartTime').value = now.getFromFormat("yyyy/mm/dd") + ' 00:00:00';	
	getEle('gameRecordEndTime').value = now.getFromFormat("yyyy/mm/dd") + ' 23:59:59';
}
function gameRecordChoseThreeDay(){
	var now = new Date();
	getEle('gameRecordStartTime').value = getNDaysAgo(3) + ' 00:00:00';	
	getEle('gameRecordEndTime').value = now.getFromFormat("yyyy/mm/dd") + ' 23:59:59';
}
function gameRecordChoseThisWeek(){
	var now = new Date();
	getEle('gameRecordStartTime').value = getNDaysAgo(7) + ' 00:00:00';	
	getEle('gameRecordEndTime').value = now.getFromFormat("yyyy/mm/dd") + ' 23:59:59';
}
function nextGameRecordsPage(){
	var count = getEle('gameRecordCountList').value;
	var nextPageNum = parseInt(getEle("gameRecordsPage").value)+1;
	var page = "1"
	if(nextPageNum <= parseInt(getEle("gameRecordsLastPage").value)){
		page = nextPageNum;
	}
	else{
		page = getEle("gameRecordsLastPage").value;
	}
	var pageInfo = "&count="+count+"&nextPage="+page;
	confromSearchGmaeRecords(pageInfo);
}
function lastGameRecordsPage(){
	var count = getEle('gameRecordCountList').value;
	var maxPage = getEle("gameRecordsLastPage").value;
	var pageInfo = "&count="+count+"&nextPage="+maxPage;
	confromSearchGmaeRecords(pageInfo);
}

function previousGameRecordsPage(){
	var count = getEle('gameRecordCountList').value;
	var previousPage = parseInt(getEle("gameRecordsPage").value)-1;
	var page = "1"
	if(previousPage > 0){
		page = previousPage;
	}
	var pageInfo = "&count="+count+"&nextPage="+page;
	confromSearchGmaeRecords(pageInfo);
}

function firstGameRecordsPage(){
	var count = getEle('gameRecordCountList').value;
	var pageInfo = "&count="+count+"&nextPage=1";
	confromSearchGmaeRecords(pageInfo);
}
