var isShowConsole = true;
var XHR_getLowerLevelAccTotal = null;
var jsonObj = null;
var level = ["Admin","公司","總監","大股東","股東","總代理","代理","代理1","代理2","代理3","代理4","代理5","代理6","代理7","代理8","代理9","代理10","會員"];
const defaultCount = "25";
XHR = null;

function showAMTable() {
	var tmpTH = '<div id = "searchTime"></div><table class="table-zebra tr-hover"><tr>' + getTH() + '</tr>';
	
	if(getEle("selectedSecMenu").value == ACC_LEVEL_MEM){
		tmpTH += getMemberTD() + '</table>';
	}
	else{
		tmpTH += getTD() + '</table>';
	}
	

	return tmpTH;
}

function getTH() {
	if (jsonObj != null && jsonObj.th != null) {
		tmpStr = '';
		for (var i = 0; i < jsonObj.th.length; i++) {
			tmpStr += '<th>' + jsonObj.th[i] + '</th>\n';
		}
		return tmpStr;
	} else {
		return "";
	}
}
function getTD() {
	var count = getEle("selectCount").value;
	var page = getEle("page").value;
	var maxCount = page*count;
	var minCount = maxCount-count;
	if (jsonObj != null && jsonObj.td != null) {
		tmpStr = '';
		for (var i = minCount; i < jsonObj.td.length && i < maxCount ; i++) {
			tmpStr += '<tr>';
			for (var j = 0; j < jsonObj.td[i].length; j++) {
				tmpStr += '<td>' + jsonObj.td[i][j] + '</td>';
			}
			tmpStr += '</tr>\n';
		}
		return tmpStr;
	} else {
		return "";
	}
}

function getMemberTD(){
	if (jsonObj != null && jsonObj.td != null) {
		tmpStr = '';
		for (var i = 0; i < jsonObj.td.length ; i++) {
			tmpStr += '<tr>';
			for (var j = 0; j < jsonObj.td[i].length; j++) {
				tmpStr += '<td>' + jsonObj.td[i][j] + '</td>';
			}
			tmpStr += '</tr>\n';
		}
		return tmpStr;
	} else {
		return "";
	}
}
function getAllTypes() {
	if (jsonObj != null && jsonObj.typeTitle != null) {
		tmpStr = '';
		try {
			for (var i = 0; i < jsonObj.typeTitle.length; i++) {
				if (document.getElementsByName("selectedSecMenu")[0].value == jsonObj.typeLevel[i]) {
					tmpStr += '<li onclick="showTypeOuter('
							+ jsonObj.typeLevel[i] + ');"><a id=secMenu'
							+ jsonObj.typeLevel[i]
							+ ' href="javascript:void(0);" class="text-left active">'
							+ jsonObj.typeTitle[i] + '<span>'
							+ jsonObj.typeRows[i] + '</span></a></li>\n';
				} else {
					tmpStr += '<li onclick="showTypeOuter('
							+ jsonObj.typeLevel[i] + ');"><a id=secMenu'
							+ jsonObj.typeLevel[i]
							+ ' href="javascript:void(0);" class="text-left">'
							+ jsonObj.typeTitle[i] + '<span>'
							+ jsonObj.typeRows[i] + '</span></a></li>\n';
				}
			}
		} catch (error) {
			console_Log("getAllTypes error:"+error.message);
		} finally {
			disableLoadingPage();
		}
		return tmpStr;
	}
}

function onClickFirstMenu(val,FirstauthId) {
	var SET_ACCOUNT_URL = 1;
	if (typeof(UPDATE_ACCOUNT_JS) === "undefined" && val == 0) {
		enableLoadingPage();
		loadScript("js/AccountManage/UpdateAccount.js?id="
				+ getNewTime(), function() {
			disableLoadingPage();
		});
	}
	document.getElementsByName("selectedSecMenu")[0].value = "";
	getEle("allTypes").innerHTML = "";
	getEle("mainContain").innerHTML = "";
	document.getElementsByName("selectedFirstMenu")[0].value = val;
	getEle("searchArea").innerHTML = "";
	for (var i = 0; i < document.getElementsByName("NumberOfFirstMenu")[0].value; i++) {
		try {
			var id = "firstMenu" + i;
			if (val == i) {
				document.getElementById(id).classList.add("active");
			} else {
				document.getElementById(id).classList.remove("active");
			}
		} catch (e) {
		}
	}
	document.getElementById("firstBreadcrumbLi").onclick = "onClickFirstMenu("
			+ val + ")";
	if (val == 0) {
		if(FirstauthId == SET_ACCOUNT_URL){
			onClickAccountManage(val);
		}else{
			arrangeSecondMenu(val);
		}
		document.getElementById("firstBreadcrumb").innerHTML = getFirstMenuName(val);
		document.getElementById("secondBreadcrumb").style.display = "block";
		document.getElementById("secondBreadcrumb").innerHTML = getFirstMenuName(val);
	} else if (val == 1) {
		arrangeSecondMenu(val);
		document.getElementById("firstBreadcrumb").innerHTML = getFirstMenuName(val);
		document.getElementById("secondBreadcrumb").style.display = "block";
		document.getElementById("secondBreadcrumb").innerHTML = "";
	} else if (val >= 2) {
		arrangeSecondMenu(val);
		document.getElementById("firstBreadcrumb").innerHTML = getFirstMenuName(val);
		document.getElementById("secondBreadcrumb").style.display = "block";
		document.getElementById("secondBreadcrumb").innerHTML = "";
	} 
//	disableLoadingPage();
}

function onClickAccountManage() {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&init=1";
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onreadystatechange = handleRequestAllData;
		XHR.open("POST", "./AccountManage!ajax.php?date="
				+ getNewTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();
	}
}
function clickEnterLogin(){
	if(event.keyCode == 13){//13是"ENTER"
		login();
	}
}
function login(){
	var userName = document.getElementById("account-login").value;
	var pwd = document.getElementById("password-login").value;
	if(pwd != "" && userName != ""){
		if(getEle("saveAccName").checked){
			console_Log(getEle("saveAccName").checked);
			setAccNameCookie(userName);
		}
		else{
			closeAccNameCookie();
		}
		loginAjax(userName,pwd);
	}
	else{
		onClickOpenModal("<div class='modal-content width-percent-20'><span class='close' onclick='onClickCloseModal();'>&times;</span><p class='login-error'>帳號跟密碼不能為空</p></div>");
	}
}

function loginAjax(userName,pwd) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "userName=" + userName + "&pwd=" + pwd;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onreadystatechange = handleLogin;
		XHR.open("POST", "./Login.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();
	}
}

function checkUsername() {
	getEle("account-login").value = checkAccount(getEle("account-login").value);
}
function checkPassWord() {
	getEle("password-login").value = checkInputPassWordVal(getEle("password-login").value);
}

function loginError() {
	onClickOpenModal("<div class='modal-content width-percent-20'><span class='close' onclick='onClickCloseModal();'>&times;</span><p class='login-error'>帳號或密碼輸入錯誤，請再試一次。</p></div>");
}
function handleLogin() {
	var cmd = '';
	var run = false;
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					getEle("loginInfo").value = XHR.responseText;
					var jsonObj = JSON.parse(XHR.responseText);
					var basicInfoObj = jsonObj.basic;
					var authInfoObj = jsonObj.auth;
					getEle("tokenId").value = jsonObj.tokenId;
					
					if (basicInfoObj.result == 'fail' || jsonObj.message == 'fail') {
						loginError();
					}
					if (basicInfoObj.result == 'true' && jsonObj.message == 'success') {
						getEle("loginDiv").style.display = "none";
						var showStr = "";
						if (basicInfoObj.acc_level_type == ACC_LEVEL_ADMIN) {
							showStr += "管理者 ";
						} else if (basicInfoObj.acc_level_type == ACC_LEVEL_COM) {
							showStr += "公司 ";
						} else if (basicInfoObj.acc_level_type == ACC_LEVEL_SC) {
							showStr += "總監 ";
						} else if (basicInfoObj.acc_level_type == ACC_LEVEL_BC) {
							showStr += "大股東 ";
						} else if (basicInfoObj.acc_level_type == ACC_LEVEL_CO) {
							showStr += "股東 ";
						} else if (basicInfoObj.acc_level_type == ACC_LEVEL_SA) {
							showStr += "總代理 ";
						} else if (basicInfoObj.acc_level_type == ACC_LEVEL_AG) {
							showStr += "代理 ";
						} else if (basicInfoObj.acc_level_type == ACC_LEVEL_AG1) {
							showStr += "代理 1";
						} else if (basicInfoObj.acc_level_type == ACC_LEVEL_AG2) {
							showStr += "代理 2";
						} else if (basicInfoObj.acc_level_type == ACC_LEVEL_AG3) {
							showStr += "代理 3";
						} else if (basicInfoObj.acc_level_type == ACC_LEVEL_AG4) {
							showStr += "代理 4";
						} else if (basicInfoObj.acc_level_type == ACC_LEVEL_AG5) {
							showStr += "代理 5";
						} else if (basicInfoObj.acc_level_type == ACC_LEVEL_AG6) {
							showStr += "代理 6";
						} else if (basicInfoObj.acc_level_type == ACC_LEVEL_AG7) {
							showStr += "代理 7";
						} else if (basicInfoObj.acc_level_type == ACC_LEVEL_AG8) {
							showStr += "代理 8";
						} else if (basicInfoObj.acc_level_type == ACC_LEVEL_AG9) {
							showStr += "代理 9";
						} else if (basicInfoObj.acc_level_type == ACC_LEVEL_AG10) {
							showStr += "代理 10";
						}
						
						showStr += basicInfoObj.acc_name;
						getEle("userNameInAccArea").innerHTML = "<i></i>" + showStr;
									
						arrangeFirstMenuByLoginInfo();
						getLowerLevelAccTotalAjax();
					}
					
				}
			} catch (error) {
				console_Log("handleLogin error:"+error.message);
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

function checkAccTypeLevel(val) {
	var jsonObj = JSON.parse(document.getElementsByName("loginInfo")[0].value);
	var authInfoObj = jsonObj.auth[0].list;
	for (var i = 0; i < authInfoObj.length; i++) {
		if ((authInfoObj[i].authId == '7' && val == ""+ACC)
				|| (authInfoObj[i].authId == '8' && val == '2')
				|| (authInfoObj[i].authId == '9' && val == '3')
				|| (authInfoObj[i].authId == '10' && val == '4')
				|| (authInfoObj[i].authId == '11' && val == '5')
				|| (authInfoObj[i].authId == '12' && val == '6')
				|| (authInfoObj[i].authId == '13' && val == '7')
				|| ( val == '8')) {
			return true;
		}
	}
	return false;
}

function getFirstMenuName(val) {
	var jsonObj = JSON.parse(document.getElementsByName("loginInfo")[0].value);
	return jsonObj.auth[val].authName;
}

function getFirstMenuAuthId(val) {
	var jsonObj = JSON.parse(document.getElementsByName("loginInfo")[0].value);
	return jsonObj.auth[val].authId;
}

function arrangeFirstMenuByLoginInfo() {
	var logoArray = {
		"1" : "manager-account",
		"2" : "manager-admin",
		"3" : "manager-operation",
		"4" : "manager-user",
		"5" : "game-report",
		"6" : "game-log"
	};
	var jsonObj = JSON.parse(document.getElementsByName("loginInfo")[0].value);
	var authInfoObj = jsonObj.auth;
	var tmpStr = "";
	var tmpStr0 = '<li  class="';
	var tmpStr1 = '" onclick="onClickFirstMenu(';
	var tmpStr2 = ');"><a  id="firstMenu';
	var tmpStr3 = '" href="javascript:void(0);"><span></span>';
	var tmpStr4 = '</a></li>';
	for (var i = 0; i < authInfoObj.length; i++) {
		tmpStr += (tmpStr0 + logoArray[getFirstMenuAuthId(i)] + tmpStr1 + i +','+authInfoObj[0].authId
				+ tmpStr2 + i + tmpStr3 + getFirstMenuName(i) + tmpStr4);
	}
	document.getElementsByName("NumberOfFirstMenu")[0].value = authInfoObj.length;
	document.getElementById("firstMenu").innerHTML = tmpStr;
}

function arrangeSecondMenu(selItem) {
	var jsonObj = JSON.parse(document.getElementsByName("loginInfo")[0].value);
	var authInfoObj = jsonObj.auth[selItem].list;
	var tmpStr = "";
	var tmpStr1 = '<li onclick="onClickSecondMenu(';
	var tmpStr2 = ' )"><a id=secMenu';
	var tmpStr3 = ' href="javascript:void(0);" class="text-left">';
	var tmpStr4 = '</a></li>';
	for (var i = 0; i < authInfoObj.length; i++) {
		var url = "" + authInfoObj[i].url;
		tmpStr += tmpStr1 + authInfoObj[i].authId + ", " + i + "," + "\'" + url
				+ "\'" + tmpStr2 + i + tmpStr3 + authInfoObj[i].authName
				+ tmpStr4
	}
	document.getElementsByName("NumberOfSecMenu")[0].value = authInfoObj.length;
	document.getElementById("allTypes").innerHTML = tmpStr;
}

function getAuthNameByAuthIdOfSecMenu(val) {
	var jsonObj = JSON.parse(document.getElementsByName("loginInfo")[0].value);
	for (var i = 0; i < jsonObj.auth.length; i++) {
		var authInfoObj = jsonObj.auth[i].list;
		for (var j = 0; j < authInfoObj.length; j++) {
			if (authInfoObj[j].authId == val) {
				return authInfoObj[j].authName;
			}
		}
	}
	return "";
}

function handleRequestAllData() {
	var count = 0
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try{
				if (isJSON(XHR.responseText)) {
					jsonObj = JSON.parse(XHR.responseText);
					if(checkTokenIdfail(jsonObj)){
					getEle("currentMemberListInfo").value = XHR.responseText;
					var tmpString;
					if(jsonObj.th != null){
						if(jsonObj.td != null){
							if(jsonObj.maxPage != null ){
								getEle("maxPage").value =   jsonObj.maxPage;
								getEle("displayMaxPage").innerHTML =  jsonObj.maxPage;
								if(jsonObj.nowPage != null ){
									getEle("page").value =  jsonObj.nowPage;
									getEle("displayNowPage").innerHTML = getEle("page").value;
								}
								}
								else{
									count = Object.keys(jsonObj.td).length;
									setMaxPage(count);
								}
							}
							tmpString = showAMTable();
						}
						if (tmpString != null && tmpString != undefined) {
							getEle("mainContain").innerHTML = tmpString;
							
						} else {
							getEle("mainContain").innerHTML = "";
						}
						tmpString = getAllTypes();
						if (tmpString != null && tmpString != undefined) {
							getEle("allTypes").innerHTML = tmpString;
						} else {
							getEle("allTypes").innerHTML = "";
						}
						
						if(jsonObj.searchTime != null){
							if(getEle("searchTime") != null && getEle("searchTime") != undefined){
								getEle("searchTime").innerHTML = jsonObj.searchTime;
							}
						}
						if(typeof getEle("iconArrow"+getEle("sort").value) != "undefined" && getEle("iconArrow"+getEle("sort").value) != null){
							if(getEle("asc").value == 1){
								if(getEle("iconArrow"+getEle("sort").value).classList.contains("icon-arrow-up")){
									getEle("iconArrow"+getEle("sort").value).classList.remove("icon-arrow-up");
								}
							}
							else{
								if(!getEle("iconArrow"+getEle("sort").value).classList.contains("icon-arrow-up")){
									getEle("iconArrow"+getEle("sort").value).classList.add("icon-arrow-up");
								}
							}
						}
					}
				}
			} catch (error) {
				console_Log("handleRequestAllData error:"+error.message);
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

function check_all(obj, cName) {
	var checkboxs = document.getElementsByName(cName);
	for (var i = 0; i < checkboxs.length; i++) {
		checkboxs[i].checked = obj.checked;
	}
}

function replaceOption(cName, row, text, value) {
	if (getEle(cName).options.length - 1 >= row) {
		getEle(cName).options[row] = new Option(text, value);
	}
}
function sort(idx, type) {
	if (getEle("sort").value != idx) {
		getEle("sort").value = idx;
		getEle("asc").value = 0;
		
	} else {
		if (getEle("asc").value == 0) {
			getEle("asc").value = 1;
		} else {
			getEle("asc").value = 0;
		}
	}
	
	updateSearchJsonValue("asc",getEle("asc").value);
	updateSearchJsonValue("sort",getEle("sort").value);
	renewSearchLastDataAjax();
	//searchAccData(type)
	// getSortData(type);
	//disableLoadingPage();
}

function getSortData(type) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
	var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&init=1&sort=" + getEle("sort").value + "&asc="
			+ getEle("asc").value + "&type=" + type;
	XHR.timeout = 10000;
	XHR.ontimeout = function() {
		disableLoadingPage();XHR.abort();
	}
	XHR.onerror = function() {
		disableLoadingPage();XHR.abort();
	}
	XHR.onreadystatechange = handleRequestAllData;
	XHR.open("POST", "./AccountManage!ajax.php?date=" + getNewTime(),true);
	XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	XHR.send(tmpstr);
	enableLoadingPage();
	}
}

function activeSideBar(val) {
	document.getElementsByName("selectedSecMenu")[0].value = val;
}

function showTypeOuter(val) {
	removeClassFix();
	getEle("searchStatus").value = "0";
	getEle("searchAccId").value = "0";
	
	var accId = "";
	var status = "";
	var accLevel = "";
	var accName = "";
	var memberType = "";
	var checkStatus = "true";
	var page = "1";
	
//	getEle("searchOrderInfo").value = "";
//	getEle("searchOrderAction").value = "";
//	getEle("handleName").value = "";
	
	getEle("searchPostJson").value = "";
	getEle("searchActionUrl").value = "";
	getEle("searchHandlFunction").value = "";
	
	
	getEle("selectCount").value = defaultCount;
	getEle("page").value = page;
	getEle("maxPage").value = page;
	
	getEle("sort").value = 3;
	getEle("asc").value = 1;
	
	getAccData(val,accId,0,status,accLevel,accName,memberType,checkStatus,defaultCount,page);
	getEle("secondBreadcrumb").innerHTML = getAccLevelNickname(val);
	
}

// --
function changeSearchDiv(val) {
	if(isJSON(getEle("loginInfo").value)){
		var userAccLevel = 0;
		var jsonObj = JSON.parse(getEle("loginInfo").value);
		var accLevel = val;
		
		if(!isNull(jsonObj.basic.acc_level_type)){
			userAccLevel = parseInt(jsonObj.basic.acc_level_type);
		}
		
		var str = [];
		str[0] = '<ul><li class = "break-line"><span class="title-manager"> '+ getAccLevelNickname(val) +'管理</span> \n';
		str[1] = '<select id = "selectMemberType" onchang="selectMemberTypeOnChang(this);"></select>  \n';
		str[2] = '<select id="selectUpperList" onchange="getUpperSelectName('+val+');" class="width-fix-7"></select>  \n';
		str[3] = '<select id="selectList" class="width-fix-6" onchange = "changeSelectAccName(this.value);"></select>  \n';
		str[4] = '<select id="selectStatusList" onchange="changeStatusSelect('+val+');" ></select>  \n';
		str[5] = '<select id = "changSelectAccDataCount"></select>  \n';
		str[6] = '<input id="selectText" type="text" class="margin-fix-1" onkeyup = "selectTextCheck();">  \n';
		str[7] = '<button onclick="searchAccData('+val+');">查詢</button></li>  \n';
		str[8] = '<li class="media-control">  \n';
		str[9] = '<a href="javascript:void(0);" onclick="firstPage('+val+');" class="backward"></a>  \n';
		str[10] = '<a href="javascript:void(0);"  onclick = "previousPage('+val+');" class="backward-fast"></a>  \n';
		str[11] = '<span>總頁數：<i id="displayNowPage">1</i><span>/</span><i id="displayMaxPage">1</i>頁</span>   \n';
		str[12] = '<a href="javascript:void(0);" onclick = "nextPage('+val+');" class="forward"></a>  \n';
		str[13] = '<a href="javascript:void(0);" onclick = "nextMaxPage('+val+');" class="forward-fast"></a></li>  \n';
		str[14] = '<li><button onclick="javascript:showAddAccount();">新增</button>   \n';
		str[15] = '<button onclick="confromShowOpsLog('+val+','+defaultCount+');">紀錄</button>   \n';
		str[16] = '</li><li class="media-control">  \n';
		str[17] = '<input id="changePageText" onKeyup = "checkPageText(this.value);" type="text" class="margin-fix-1">頁  \n';
		str[18] = '<button onclick = "changPageBnt('+val+');">跳轉</button></li></ul>   \n';

		var tmpStr ='';
		if(isCOM(val) || isSC(val)){
			tmpStr = tmpStr.concat(str[0],str[3],str[4],str[5],str[6],str[7],str[8]
			,str[9],str[10],str[11],str[12],str[13],str[14],str[15],str[16],str[17],str[18]);
		}else if(isLevelBcToAg(val)){
			tmpStr = tmpStr.concat(str[0],str[2],str[3],str[4],str[5],str[6],str[7],str[8]
			,str[9],str[10],str[11],str[12],str[13],str[14],str[15],str[16],str[17],str[18]);
		}
		else if(isMEM(val)){
			tmpStr = str.join("");
		}
		getEle("searchArea").innerHTML = tmpStr;
		
		if(isMEM(val)){
			// addOption("selectMemberType","會員類型",-1);
			addOption("selectMemberType","所有會員",GRNERAL_MEM);
			addOption("selectMemberType","一般會員",DIRECTLY_UNDER_MEM);
			for(var i = 0; i < MANAGER_LEVEL_TYPE_ARR.length; i++){
				if(MANAGER_LEVEL_TYPE_ARR[i] > userAccLevel){
					if(MANAGER_LEVEL_TYPE_ARR[i] != ACC_LEVEL_AG){
						addOption("selectMemberType",params.levelTypeArr[MANAGER_LEVEL_TYPE_ARR[i]]+"直屬會員",MANAGER_LEVEL_TYPE_ARR[i]);
					}
				}
			}
		}
		
		
		addOption("selectStatusList","啟用",STATUS_TYPE_ENABLED);
		addOption("selectStatusList","禁止登入",STATUS_TYPE_NOLOGIN);
		addOption("selectStatusList","停用",STATUS_TYPE_DISABLED);
		if(jsonObj.basic.acc_level_type == ACC_LEVEL_ADMIN){
			addOption("selectStatusList","刪除",STATUS_TYPE_DELETE);
		}
		
		selectItemByValue(getEle("selectStatusList"),getEle("searchStatus").value);
		
		addOption("changSelectAccDataCount","25筆",25);
		addOption("changSelectAccDataCount","50筆",50);
		addOption("changSelectAccDataCount","75筆",75);
		addOption("changSelectAccDataCount","100筆",100);
		
		if(val > ACC_LEVEL_SC && val <= ACC_LEVEL_MEM ){
			removeAllOption("selectUpperList");
			addOption("selectUpperList","管理層級",-1);
			if(isMEM(val)){
				for(var i = 0 ; i < MANAGER_LEVEL_TYPE_ARR.length ; i++){
					if(MANAGER_LEVEL_TYPE_ARR[i] > jsonObj.basic.acc_level_type){
						addOption("selectUpperList",getAccLevelNickname(MANAGER_LEVEL_TYPE_ARR[i]),MANAGER_LEVEL_TYPE_ARR[i]);
					}
				}
			}
			else if(isAG10(parseInt(jsonObj.basic.acc_level_type))){
				getEle("selectMemberType").style.display = "none"; 
				getEle("selectUpperList").style.display = "none";
				getEle("selectList").style.display = "none";
				
			}
			else if(val == jsonObj.basic.acc_level_type){
				addOption("selectUpperList",getAccLevelNickname(val),val);
			}
			else{
				var upLevel = getUpLevel(val);
				addOption("selectUpperList",getAccLevelNickname(upLevel),upLevel);
				addOption("selectUpperList",getAccLevelNickname(val),val);
			}
		}
		
		getUpperSelectName(val);
		if(isJSON(getEle("searchPostJson").value)){
			var json = JSON.parse(getEle("searchPostJson").value);
			if(!isNull(json.accLevel)){
				selectItemByValue(getEle("selectUpperList"), json.accLevel);
				getUpperSelectName(json.accLevel);
			}
			if(!isNull(json.upAccId) && json.upAccId != 0){
				selectItemByValue(getEle("selectList"), json.upAccId);
				changeSelectAccName(json.upAccId);
			}
			else if(!isNull(json.accId)){
				selectItemByValue(getEle("selectList"), json.accId);
				changeSelectAccName(json.accId);
			}
			if(!isNull(json.status)){
				selectItemByValue(getEle("selectList"), json.status);
			}
		}
		
	}
}
 
function selectMemberTypeOnChang(val){
	getEle('selectUpperList').value = val;
	
}
function onClickSecondMenu(val, selectedId, url) {
	document.getElementsByName("selectedSecMenu")[0].value = selectedId;
	
	
	try {
		for (var i = 0; i < document.getElementsByName("NumberOfSecMenu")[0].value; i++) {
			var id = "secMenu" + i;
			if (selectedId == i) {
				document.getElementById(id).classList.add("active");
			} else {
				document.getElementById(id).classList.remove("active");
			}
		}
	} catch (e) {
	}
	document.getElementById("secondBreadcrumb").innerHTML = getAuthNameByAuthIdOfSecMenu(val);
	// 根據val做些事～～

	authAjax(url);
}

function showType(val, id,status) {
	activeSideBar(val);
	changeSearchDiv(val);
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var obj = {};
		obj["tokenId"] = getEle("tokenId").value;
		obj["init"] = 0;
		obj["checkStatus"] = true;
		if(id != "" && typeOf(id) != "undefined"){
			obj["accId"] = id;
		}
		if(status != "" && typeOf(status) != "undefined"){
			obj["status"] = status;
		}
		
		var tmpstr = joinVars("&",obj,true);
		
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onreadystatechange = handleRequestAllData;
		XHR.open("POST", "./AccountManage!ajax.php?date=" + getNewTime(),
				true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();
		
		getEle("searchPostJson").value = JSON.stringify(obj);
		getEle("searchActionUrl").value = "./AccountManage!ajax.php";
		getEle("searchHandlFunction").value = "handleRequestAllData";
	}
}
function selectTextCheck() {

	getEle("selectText").value = checkAccount(getEle("selectText").value);
	var accName = getEle("selectText").value;
	
	var selectedSecMenu = getEle("selectedSecMenu").value;
	var searchStatus = getEle("selectStatusList").value
	
	if(isJSON(getEle("currentMemberListInfo").value) && isNotMEM(selectedSecMenu)){
		var json = JSON.parse(getEle("currentMemberListInfo").value);
		var tmpArr = json.serchAccData[selectedSecMenu][searchStatus];
		getEle("searchAccId").value = "0";
		if(!isCOM(selectedSecMenu) && !isSC(selectedSecMenu)){
			var searchUpLevel= getEle("selectUpperList").value;
		}
		for(var i = 0 ; i < tmpArr.length ; i++){
			if(toString(accName) == toString(tmpArr[i].accName)){
				getEle("searchAccId").value = tmpArr[i].accId;
				if(searchUpLevel == selectedSecMenu || isCOM(selectedSecMenu) || isSC(selectedSecMenu)){
					selectItemByValue(getEle("selectList"),tmpArr[i].accId);
				}
				break;
			}
		}
	}

	
}

function changeSelectAccName(val){
	
	var selectedSecMenu = getEle("selectedSecMenu").value;
	if(!isCOM(selectedSecMenu) && !isSC(selectedSecMenu)){
		var searchUpLevel= getEle("selectUpperList").value;
		if(searchUpLevel == selectedSecMenu){
			getEle("searchAccId").value = val;
			getEle("selectText").value = "";
		}
		else{
			selectTextCheck();
		}
	}
	else{
		getEle("searchAccId").value = val;
		getEle("selectText").value = "";
	}
	
}

function changeStatusSelect(val){
	selectTextCheck();
	getUpperSelectName(val);

}

function getUpperSelectName(val) {
	var accLevelName = ["Admin","公司","總監","大股東","股東","總代理","代理","代理1","代理2","代理3","代理4","代理5","代理6","代理7","代理8","代理9","代理10"];
	var selectStatus = getEle("selectStatusList").value;
	
	removeAllOption("selectList");
	
	var level = toInt(val);
	if(level > ACC_LEVEL_SC && level <= ACC_LEVEL_MEM){
		level = toInt(getEle("selectUpperList").value);
	}
	
	if(level == -1){
		getEle("selectList").style.display = "none";
	}
	else if(isJSON(getEle("currentMemberListInfo").value)){
		var json = JSON.parse(getEle("currentMemberListInfo").value);
		getEle("selectList").style.display = "";
		
		addOption("selectList","選擇帳號",0);
		
		var tmpArr = json.serchAccData[level][selectStatus];
		for(var i = 0 ; i < tmpArr.length ; i++){
			addOption("selectList",tmpArr[i].accName,tmpArr[i].accId);
		}
	}
	if(val > ACC_LEVEL_SC){
		selectItemByValue(getEle("selectUpperList"), val);
	}
}


function getUpper(id,status) {
	var jsonObj = JSON.parse(getEle("loginInfo").value);
	var basicInfoObj = jsonObj.basic;
	var accLevelType = basicInfoObj.acc_level_type;
	var accName = basicInfoObj.acc_name;

	var content = '<div class="modal-content width-percent-20"><span class="close" onclick="onClickCloseModal();">&times;</span><h3 class="text-center">上線結構</h3><table id="upperTable"></table></div>';
	onClickOpenModal(content);
	if (isJSON(getEle("currentMemberListInfo").value)) {
		jsonObj = JSON.parse(getEle("currentMemberListInfo").value);
		var tmpStr = "";
		if (jsonObj.up[id].scId != "0") {
			if (isSC(accLevelType)) {
				tmpStr += ('<tr><th>總監</th><td>' + accName + '</td></tr>');
			} else {
				tmpStr += ('<tr><th>總監</th><td><a onclick="getUpManagerData('+ACC_LEVEL_SC+','
						+ jsonObj.up[id].scId + ','+ jsonObj.up[id].scAccStatus +')">'
						+ jsonObj.up[id].scAccName + '</a></td></tr>');
			}
		}
		if (jsonObj.up[id].bcId != "0") {
			if (isBC(accLevelType)) {
				tmpStr += ('<tr><th>大股東</th><td>' + accName + '</td></tr>');
			} else {
				tmpStr += ('<tr><th>大股東</th><td><a onclick="getUpManagerData('+ACC_LEVEL_BC+','
						+ jsonObj.up[id].bcId + ','+ jsonObj.up[id].bcAccStatus +')">'
						+ jsonObj.up[id].bcAccName + '</a></td></tr>');
			}
		}
		if (jsonObj.up[id].coId != "0") {
			if (isCO(accLevelType)) {
				tmpStr += ('<tr><th>股東</th><td>' + accName + '</td></tr>');
			} else {
				tmpStr += ('<tr><th>股東</th><td><a onclick="getUpManagerData('+ACC_LEVEL_CO+','
						+ jsonObj.up[id].coId + ','+ jsonObj.up[id].coAccStatus +')">'
						+ jsonObj.up[id].coAccName + '</a></td></tr>');
			}
		}
		if (jsonObj.up[id].saId != "0") {
			if (isSA(accLevelType)) {
				tmpStr += ('<tr><th>總代理</th><td>' + accName + '</td></tr>');
			} else {
				tmpStr += ('<tr><th>總代理</th><td><a onclick="getUpManagerData('+ACC_LEVEL_SA+','
						+ jsonObj.up[id].saId + ','+ jsonObj.up[id].saAccStatus +')">'
						+ jsonObj.up[id].saAccName + '</a></td></tr>');
			}
		}
		if (jsonObj.up[id].agId != "0") {
			if (isAG(accLevelType)) {
				tmpStr += ('<tr><th>代理</th><td>' + accName + '</td></tr>');
			} else {
				tmpStr += ('<tr><th>代理</th><td><a onclick="getUpManagerData('+ACC_LEVEL_AG+','
						+ jsonObj.up[id].agId + ','+ jsonObj.up[id].agAccStatus +')">'
						+ jsonObj.up[id].agAccName + '</a></td></tr>');
			}
		}
		if (jsonObj.up[id].ag1Id != "0") {
			if (isAG1(accLevelType)) {
				tmpStr += ('<tr><th>代理1</th><td>' + accName + '</td></tr>');
			} else {
				tmpStr += ('<tr><th>代理1</th><td><a onclick="getUpManagerData('+ACC_LEVEL_AG1+','
						+ jsonObj.up[id].ag1Id + ','+ jsonObj.up[id].ag1AccStatus +')">'
						+ jsonObj.up[id].ag1AccName + '</a></td></tr>');
			}
		}
		if (jsonObj.up[id].ag2Id != "0") {
			if (isAG2(accLevelType)) {
				tmpStr += ('<tr><th>代理2</th><td>' + accName + '</td></tr>');
			} else {
				tmpStr += ('<tr><th>代理2</th><td><a onclick="getUpManagerData('+ACC_LEVEL_AG2+','
						+ jsonObj.up[id].ag2Id + ','+ jsonObj.up[id].ag2AccStatus +')">'
						+ jsonObj.up[id].ag2AccName + '</a></td></tr>');
			}
		}
		if (jsonObj.up[id].ag3Id != "0") {
			if (isAG3(accLevelType)) {
				tmpStr += ('<tr><th>代理3</th><td>' + accName + '</td></tr>');
			} else {
				tmpStr += ('<tr><th>代理3</th><td><a onclick="getUpManagerData('+ACC_LEVEL_AG3+','
						+ jsonObj.up[id].ag3Id + ','+ jsonObj.up[id].ag3AccStatus +')">'
						+ jsonObj.up[id].ag3AccName + '</a></td></tr>');
			}
		}
		if (jsonObj.up[id].ag4Id != "0") {
			if (isAG4(accLevelType)) {
				tmpStr += ('<tr><th>代理4</th><td>' + accName + '</td></tr>');
			} else {
				tmpStr += ('<tr><th>代理4</th><td><a onclick="getUpManagerData('+ACC_LEVEL_AG4+','
						+ jsonObj.up[id].ag4Id + ','+ jsonObj.up[id].ag4AccStatus +')">'
						+ jsonObj.up[id].ag4AccName + '</a></td></tr>');
			}
		}
		if (jsonObj.up[id].ag5Id != "0") {
			if (isAG5(accLevelType)) {
				tmpStr += ('<tr><th>代理5</th><td>' + accName + '</td></tr>');
			} else {
				tmpStr += ('<tr><th>代理5</th><td><a onclick="getUpManagerData('+ACC_LEVEL_AG5+','
						+ jsonObj.up[id].ag5Id + ','+ jsonObj.up[id].ag5AccStatus +')">'
						+ jsonObj.up[id].ag5AccName + '</a></td></tr>');
			}
		}
		if (jsonObj.up[id].ag6Id != "0") {
			if (isAG6(accLevelType)) {
				tmpStr += ('<tr><th>代理6</th><td>' + accName + '</td></tr>');
			} else {
				tmpStr += ('<tr><th>代理6</th><td><a onclick="getUpManagerData('+ACC_LEVEL_AG6+','
						+ jsonObj.up[id].ag6Id + ','+ jsonObj.up[id].ag6AccStatus +')">'
						+ jsonObj.up[id].ag6AccName + '</a></td></tr>');
			}
		}
		if (jsonObj.up[id].ag7Id != "0") {
			if (isAG7(accLevelType)) {
				tmpStr += ('<tr><th>代理7</th><td>' + accName + '</td></tr>');
			} else {
				tmpStr += ('<tr><th>代理7</th><td><a onclick="getUpManagerData('+ACC_LEVEL_AG7+','
						+ jsonObj.up[id].ag7Id + ','+ jsonObj.up[id].ag7AccStatus +')">'
						+ jsonObj.up[id].ag7AccName + '</a></td></tr>');
			}
		}
		if (jsonObj.up[id].ag8Id != "0") {
			if (isAG8(accLevelType)) {
				tmpStr += ('<tr><th>代理8</th><td>' + accName + '</td></tr>');
			} else {
				tmpStr += ('<tr><th>代理8</th><td><a onclick="getUpManagerData('+ACC_LEVEL_AG8+','
						+ jsonObj.up[id].ag8Id + ','+ jsonObj.up[id].ag8AccStatus +')">'
						+ jsonObj.up[id].ag8AccName + '</a></td></tr>');
			}
		}
		if (jsonObj.up[id].ag9Id != "0") {
			if (isAG9(accLevelType)) {
				tmpStr += ('<tr><th>代理9</th><td>' + accName + '</td></tr>');
			} else {
				tmpStr += ('<tr><th>代理9</th><td><a onclick="getUpManagerData('+ACC_LEVEL_AG9+','
						+ jsonObj.up[id].ag9Id + ','+ jsonObj.up[id].ag9AccStatus +')">'
						+ jsonObj.up[id].ag9AccName + '</a></td></tr>');
			}
		}
		if (jsonObj.up[id].ag10Id != "0") {
			if (isAG10(accLevelType)) {
				tmpStr += ('<tr><th>代理10</th><td>' + accName + '</td></tr>');
			} else {
				tmpStr += ('<tr><th>代理10</th><td><a onclick="getUpManagerData('+ACC_LEVEL_AG10+','
						+ jsonObj.up[id].ag10Id + ','+ jsonObj.up[id].ag10AccStatus +')">'
						+ jsonObj.up[id].ag10AccName + '</a></td></tr>');
			}
		}
		
		getEle("upperTable").innerHTML = tmpStr;
	}
}

function getUpManagerData(val, accId ,status) {
	onClickCloseModal();
	getEle("searchStatus").value = ""+status;
	

	var accLevel = val;
	var accName = "";
	var memberType = "";
	var checkStatus = "true";
	var page = "1";

	
	
	getEle("searchPostJson").value ="";
	getEle("searchActionUrl").value = "";
	getEle("searchHandlFunction").value = "";
	
	
	getEle("selectCount").value = defaultCount;
	getEle("page").value = page;
	getEle("maxPage").value = page;
	
	getAccData(val,0,accId,status,accLevel,accName,memberType,checkStatus,defaultCount,page);
}

function showMemberData(val , accId , status ){
	
	getEle("searchStatus").value = ""+status;
	
	var accLevel = "";
	var accName = "";
	var memberType = "";
	var checkStatus = "true";
	var page = "1";
	
	
	getEle("searchPostJson").value = "";
	getEle("searchActionUrl").value = "";
	getEle("searchHandlFunction").value = "";
	
	
	getEle("selectCount").value = defaultCount;
	getEle("page").value = page;
	getEle("maxPage").value = page;
	
	getAccData(val,0,accId,status,accLevel,accName,memberType,checkStatus,defaultCount,page);
}

function onClickChangeStatus(type, status, id, name) {
	if(isJSON(getEle("loginInfo").value)){
		var jsonObj = JSON.parse(getEle("loginInfo").value);
	}
	var content = '<div class="modal-content width-percent-20"><span class="close" onclick="onClickCloseModal();">&times;</span><h3 class="text-center" id="StatusChangeTitle"></h3><p class="text-center">帳號狀態<select id="StatusChangeSelect"></select></p><div class="btn-area"><button class="btn-lg btn-orange" id="StatusChangeConform">確定</button><button class="btn-lg btn-gray" onclick="onClickCloseModal();">取消</button></div></div>';
	onClickOpenModal(content);
	getEle("StatusChangeTitle").innerHTML = "更改" + name + "帳號狀態";
	if (isStatusEnabled(status)) { // 啟用
		if(jsonObj.basic.acc_level_type == ACC_LEVEL_ADMIN){
			getEle("StatusChangeSelect").innerHTML = "<option value='2'>禁止登入</option><option value='3'>停用</option><option value='4'>刪除</option>";
		}else{
			getEle("StatusChangeSelect").innerHTML = "<option value='2'>禁止登入</option><option value='3'>停用</option>";
		}
	} else if (isStatusNoLogin(status)) { // 禁止登入
		if(jsonObj.basic.acc_level_type == ACC_LEVEL_ADMIN){
			getEle("StatusChangeSelect").innerHTML = "<option selected value='1'>啟用</option><option value='4'>刪除</option>";
		}else{
			getEle("StatusChangeSelect").innerHTML = "<option selected value='1'>啟用</option>";
		}
	} else if (isStatusDisabled(status)) { // 停用
		if(jsonObj.basic.acc_level_type == ACC_LEVEL_ADMIN){
			getEle("StatusChangeSelect").innerHTML = "<option selected value='1'>啟用</option><option value='4'>刪除</option>";
		}else{
			getEle("StatusChangeSelect").innerHTML = "<option selected value='1'>啟用</option>";
		}
	} else if (isStatusDelete(status)) { // 刪除
		getEle("StatusChangeSelect").innerHTML = "";
	}
	// getEle("StatusChangeConform").onclick = "conformChangeStatus(" + type
	// + ", " + id + ")";

	getEle("StatusChangeConform").onclick = function() {
		conformChangeStatus(id, type);
	};
}

function onClickSetting(type, id) {
	var content = "StatusChange";
	onClickOpenModal(content);
}

function onClickDelete(type, id, name) {
	var content = '<div class="modal-content width-percent-20"><span class="close" onclick="onClickCloseModal();">&times;</span><h3 class="text-center" id="StatusChangeTitle"></h3><div class="btn-area"><button class="btn-lg btn-orange" id="StatusChangeConform">確定</button><button class="btn-lg btn-gray" onclick="onClickCloseModal();">取消</button></div></div>';
	onClickOpenModal(content);
	getEle("StatusChangeTitle").innerHTML = "確認刪除" + name;
	// getEle("StatusChangeConform").onclick = "conformDelete(" + type + ", " +
	// id
	// + ")";

	getEle("StatusChangeConform").onclick = function() {
		conformDelete(id, type);
	};
}

function conformDelete(id, type) {
	// onClickCloseModal();
	conformDeleteAjax(id, type);
}

function conformChangeStatus(id, type) {
	var status = getEle("StatusChangeSelect").value;
	conformChangeStatusAjax(id, type, status);
}

function onClickShowRatio(type, id, name) {
	var content = '<div class="modal-content width-percent-960"> <span class="close" onclick="onClickCloseModal();">&times;</span><h3 class="text-center"  id="showRatioTitle"></h3> <table id="ratioTable"></table></div>';
	onClickOpenModal(content);
	getEle("showRatioTitle").innerHTML = name + "佔成列表";
	var tmpStr = '<tr><th>管理層</th><th>彩票(本周/下周)</th><th>真人視訊(本周/下周)</th><th>運動球類(本周/下周)</th><th>電動(本周/下周)</th><th>遊戲(本周/下周)</th></tr>';
	if (isJSON(getEle("currentMemberListInfo").value)) {
		jsonObj = JSON.parse(getEle("currentMemberListInfo").value);
		var gameList = [ 1, 2, 3, 4, 5 ];
		var managerList = [ 'Com', 'Sc', 'Bc', 'Co', 'Sa', 'Ag', 'Ag1', 'Ag2', 'Ag3', 'Ag4', 'Ag5', 'Ag6', 'Ag7', 'Ag8', 'Ag9', 'Ag10' ];
		var managerNameList = [ '公司', '總監', '大股東', '股東', '總代理', '代理商','代理商1', '代理商2', '代理商3', '代理商4', '代理商5', '代理商6', '代理商7', '代理商8', '代理商9', '代理商10' ];

		for (var i = 0; i < managerList.length; i++) {
			var statusCount = 0;
			for (var j = 0; j < gameList.length; j++) {
				var tmpKey = 'g' + gameList[j] + managerList[i];
				if (jsonObj.ratio[id][tmpKey] != "-1") {
					statusCount++;
				}
			}
			if (statusCount == gameList.length) {
				tmpStr += ('<tr><td>' + managerNameList[i] + '</td>');
				for (var j = 0; j < gameList.length; j++) {
					var tmpKey = 'g' + gameList[j] + managerList[i];
					var tmpNextKey = 'nextG' + gameList[j] + managerList[i];
					tmpStr += ('<td>' + jsonObj.ratio[id][tmpKey] + '% / '+jsonObj.ratio[id][tmpNextKey]+'%</td>');
					console_Log('j=>><td>' + jsonObj.ratio[id][tmpKey] + '% / '+jsonObj.ratio[id][tmpNextKey]+'%</td>');
				}
				tmpStr += ('</tr>');
			}
		}
	}
	getEle("ratioTable").innerHTML = tmpStr;

}

function authAjax(url) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&url=" + url;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onreadystatechange = handleAuthData;
		XHR.open("POST", "./PortionAuth.php?date=" + getNewTime(),true);
		XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();
	}
}

function handleAuthData() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					removeClassFix();
					getEle("searchArea").innerHTML = "";
					getEle("mainContain").innerHTML = "";
					var json = JSON.parse(XHR.responseText);
				}
			} catch (error) {
				console_Log("handleAuthData error:"+error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
				if(checkTokenIdfail(json)){
					var jsonData = JSON.stringify(json);
					if(typeof json.extraJS != "undefined"){
						document.getElementById("extraJS").innerHTML = "";
						var tmpJS = document.createElement("script");
						tmpJS.id = "extraJSid";
						document.getElementById("extraJS").appendChild(tmpJS);
						document.getElementById("extraJSid").innerHTML = json.extraJS;
						delete tmpJS;
						tmpJS = null;
					}
					switch (json.Action) {
						case SET_AUTH_URL:
							setClassFix();
							if (typeof(MANAGER_AUTH_JS) == "undefined") {
								loadScript("js/AccountManage/ManagerAuth.js?id="
										+ getNewTime(), function() {
									getEle("authGroupJson").value = jsonData;
									showAuth();
								});
							} else {
								getEle("authGroupJson").value = jsonData;
								showAuth();
							}
							
							break;
						case SET_ORDER_URL:
							setClassFix();
							if (typeof(ORDER_JS) == "undefined") {
								loadScript("js/AccountManage/Order.js?id="
										+ getNewTime(), function() {
									getEle("orderAuthJson").value = jsonData;
//									showOrder();
								});
							} else {
								getEle("orderAuthJson").value = jsonData;
//								showOrder();
							}
							break;
							
						case SET_GAME_RECORDS_URL:
							setClassFix();
							if (typeof(GAME_RECORDS_JS) == "undefined") {
								loadScript("js/AccountManage/GameRecords.js?id="
										+ getNewTime(), function() {
									getEle("gameRecordsAuthJson").value = jsonData;
									showGameRecordsPage();
								});
							} else {
								getEle("gameRecordsAuthJson").value = jsonData;
								showGameRecordsPage();
							}
							break;
						case SET_AD_URL:
							setClassFix();
							if (typeof(AD_JS) == "undefined") {
								loadScript("js/AccountManage/ad.js?id="
										+ getNewTime(), function() {
									getEle("adAuthJson").value = jsonData;
									searchAdData();
								});
							} else {
								getEle("adAuthJson").value = jsonData;
								showSetAdPage();
								searchAdData();
							}
							break;
						case SET_PUNCHES_GAME_ROOM_URL:
							if (typeof(GAME_SERVER_JS) == "undefined") {
								loadScript("js/AccountManage/SetGameServer.js?id="
										+ getNewTime(), function() {
									getEle("gameServerAddAuthJson").value = jsonData;
									GameServer();
									getGameServerDataAjax();
								});
							} else {
								getEle("gameServerAddAuthJson").value = jsonData;
								GameServer();
								getGameServerDataAjax();
							}
							break;
						case SET_GAME_PARAM_URL:
							if (typeof(GAME_SERVER_JS) == "undefined") {
								loadScript("js/AccountManage/SetGameServer.js?id="
										+ getNewTime(), function() {
									getEle("setGameParamAuthJson").value = jsonData;
									showSetGameParamPageAjax();
								});
							} else {
								getEle("setGameParamAuthJson").value = jsonData;
								showSetGameParamPageAjax();
							}
							break;
						case SET_FINISH_RECHARGE_ORDER_URL:
							if (typeof(ORDER_JS) == "undefined") {
								loadScript("js/AccountManage/Order.js?id="
										+ getNewTime(), function() {
									getEle("orderAuthJson").value = jsonData;
									onClickOrderRechargeCarrOut();
								});
							} else {
								getEle("orderAuthJson").value = jsonData;
								onClickOrderRechargeCarrOut();
							}
							break;
						case SET_FINISH_WITHTHDRAWAL_ORDER_URL:
							if (typeof(ORDER_JS) == "undefined") {
								loadScript("js/AccountManage/Order.js?id="
										+ getNewTime(), function() {
									getEle("orderAuthJson").value = jsonData;
									onClickOrderWithdrawalCarryOut();
								});
							} else {
								getEle("orderAuthJson").value = jsonData;
								onClickOrderWithdrawalCarryOut();
							}
							break;
						case SET_AUDIT_RECHARGE_ORDER_URL:
							if (typeof(ORDER_JS) == "undefined") {
								loadScript("js/AccountManage/Order.js?id="
										+ getNewTime(), function() {
									getEle("orderAuthJson").value = jsonData;
									onClickOrderRechargeUndone();
								});
							} else {
								getEle("orderAuthJson").value = jsonData;
								onClickOrderRechargeUndone();
							}
							break;
						case SET_AUDIT_WITHDRAWAL_ORDER_URL:
							if (typeof(ORDER_JS) == "undefined") {
								loadScript("js/AccountManage/Order.js?id="
										+ getNewTime(), function() {
									getEle("orderAuthJson").value = jsonData;
									onClickOrderWithdrawalUndone();
								});
							} else {
								getEle("orderAuthJson").value = jsonData;
								onClickOrderWithdrawalUndone();
							}
							break;
						case SET_LAST_AUDIT_ORDER_URL:
							if (typeof(ORDER_JS) == "undefined") {
								loadScript("js/AccountManage/Order.js?id="
										+ getNewTime(), function() {
									getEle("orderAuthJson").value = jsonData;
									onClickOrderReview();
								});
							} else {
								getEle("orderAuthJson").value = jsonData;
								onClickOrderReview();
							}
							break;
						case SET_LOTTERY_SWITCH_SET_URL://彩種開關設定
							if (typeof(LOTTERY_SET_JS) == "undefined") {
								loadScript("js/AccountManage/LotterySet.js?id="
										+ getNewTime(), function() {
									getEle("orderAuthJson").value = jsonData;
									lotterySwitchSetAjax();
								});
							} else {
								getEle("orderAuthJson").value = jsonData;
								lotterySwitchSetAjax();
							}
							break;
						case SET_LOTTERY_AMOUNT_SET_URL://彩種金額設定
							if (typeof(LOTTERY_SET_JS) == "undefined") {
								loadScript("js/AccountManage/LotterySet.js?id="
										+ getNewTime(), function() {
									getEle("orderAuthJson").value = jsonData;
									showLotteryAmountSet(1,1);
								});
							} else {
								getEle("orderAuthJson").value = jsonData;
								showLotteryAmountSet(1,1);
							}
							break;
						case COMMING_SOON:
							showModePage("進行中!","");
							break;
						default:
							getEle("mainContain").innerHTML = "";
							showWebProcessing();
							break;
					}
				}
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}
function leftMenuOpen() {
	if(document.getElementById("leftNav").classList.contains("left-nav-close")){
		document.getElementById("leftNav").classList.remove("left-nav-close");
		document.getElementById("leftNavBtn").classList.remove("left-nav-rotate");
		document.getElementById("accountOnline").classList.remove("hidden");
		document.getElementById("containerSection").classList.remove("padding-fix-1");
		document.getElementById("breadCrumb").classList.remove("left-fix-1");
		document.getElementById("searchArea").classList.remove("left-fix-2");
		getEle("leftNav").onmouseout = function(){leftMenuClose();};
		getEle("leftNav").onmouseover = function(){};
	}
}
function leftMenuClose() {
	if(!document.getElementById("leftNav").classList.contains("left-nav-close")){
		document.getElementById("leftNav").classList.add("left-nav-close");
		document.getElementById("leftNavBtn").classList.add("left-nav-rotate");
		document.getElementById("accountOnline").classList.add("hidden");
		document.getElementById("containerSection").classList.add("padding-fix-1");
		document.getElementById("breadCrumb").classList.add("left-fix-1");
		document.getElementById("searchArea").classList.add("left-fix-2");
		
		getEle("leftNav").onmouseover = function (){leftMenuOpen();};
		getEle("leftNav").onmouseout = function(){};
	}
}


function leftNavClose() {
	document.getElementById("leftNav").classList.toggle("left-nav-close");
	document.getElementById("leftNavBtn").classList.toggle("left-nav-rotate");
	document.getElementById("accountOnline").classList.toggle("hidden");
	document.getElementById("containerSection").classList
			.toggle("padding-fix-1");
	document.getElementById("breadCrumb").classList.toggle("left-fix-1");
	document.getElementById("searchArea").classList.toggle("left-fix-2");
}
function leftNavOpen() {
	document.getElementById("leftNav").classList.toggle("left-side");
	// document.getElementById("leftNavBtn").classList.toggle("left-nav-rotate");
	document.getElementById("accountOnline").classList.toggle("account-online");
	document.getElementById("containerSection").classList.toggle("container");
	document.getElementById("breadCrumb").classList.toggle("breadcrumb");
	document.getElementById("searchArea").classList.toggle("search-area");
}

function conformChangeStatusAjax(id, type, status) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&accLevelType=" + type + "&accId=" + id + "&status="
				+ status;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onreadystatechange = handleConformChangeStatus;
		XHR.open("POST", "./AccountManage!updateAccStatus.php?date="+ getNewTime(), true);
		XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();
	}
}
function handleConformChangeStatus() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json=JSON.parse(XHR.responseText);
					if(checkTokenIdfail(json)){
						if (json.accStatusOpsType == STATUS_OP_FAIL) {
							onCheckModelPage2("修改失敗");
						} else if (json.accStatusOpsType >= STATUS_ENABLED && json.accStatusOpsType <= STATUS_DELETE) {
							onCheckModelPage2("修改成功");
							onClickCloseModal();
						} else if (json.accStatusOpsType == STATUS_ENABLED_UPDATE_FAIL) {
							onCheckModelPage2("此帳號上層仍未啟用!");
						} else if (json.accStatusOpsType == STATUS_NOLOGIN_UPDATE_FAIL) {
							onCheckModelPage2("修改失敗");
						} else if (json.accStatusOpsType == STATUS_DISABLED_UPDATE_FAIL) {
							onCheckModelPage2("修改失敗");
						} else if (json.accStatusOpsType == STATUS_DELETE_UPDATE_FAIL) {
							onCheckModelPage2("此帳號已有操作紀錄，無法刪除!");
						}
						
					}
					
				}
			} catch (error) {
				console_Log("handleConformChangeStatus error:"+error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
				if (json.message == "success") {
//					getSearchAccDataAjax(getEle("searchOrderInfo").value);
					renewSearchLastDataAjax();
				}
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function conformDeleteAjax(id, type) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&accLevelType=" + type + "&accId=" + id + "&status=4";
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onreadystatechange = handleConformDelete;
		XHR.open("POST", "./AccountManage!updateAccStatus.php?date="+ getNewTime(), true);
		XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();
	}
}

function handleConformDelete() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json=JSON.parse(XHR.responseText);
					if(checkTokenIdfail(json)){
						if (json.message == "fail") {
							onCheckModelPage2("刪除失敗");
						} else if (json.message == "success") {
							onCheckModelPage2("刪除成功");
							onClickCloseModal();
						}
//						getSearchAccDataAjax(getEle("searchOrderInfo").value);
						renewSearchLastDataAjax();
					}
					
				}
			} catch (error) {
				console_Log("handleConformDelete error:"+error.message);
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

function searchAccData(val){
	
	getEle("page").value = "1";
	getEle("displayNowPage").innerHTML = getEle("page").value;
	var count = getEle("changSelectAccDataCount").value;
	var status = getEle("selectStatusList").value;
	var memberType = 0;
	var accId = 0;
	var upAccId = 0;
	var accLevel = 0;
	var accName = "";
	
	getEle("selectCount").value = count;
	
	if(isNotMEM(val)){
		accId = getEle("searchAccId").value;
	}
	else if(isMEM(val)){
		accName = getEle("selectText").value;
		memberType = getEle("selectMemberType").value;
	}
	
	if(isCOM(val) || isSC(val)){
		accLevel = val;
	}
	else{
		accLevel = getEle("selectUpperList").value;
		if(accLevel != val){
			upAccId = getEle("selectList").value;
		}
	}
	
	getAccData(val,upAccId,accId,status,accLevel,accName,memberType,"true",count,getEle("page").value,false);
	
}
function nextPage(val){
	var nextPageNum = parseInt(getEle("page").value)+1;
	if(nextPageNum <= parseInt(getEle("maxPage").value)){
		page = nextPageNum;
	}
	else{
		page = getEle("maxPage").value;
	}
	
	if(isMEM(val)){
		if(parseInt(page) != parseInt(getEle("page").value)){
			var str = getEle("searchOrderInfo").value+"&nextPage="+page;
			
			updateSearchJsonValue("nextPage",page);
			
//			 getSearchAccDataAjax(str);
			 renewSearchLastDataAjax();
		}	
	}
	else if(isNotMEM(val)){
		getEle("page").value = page;
		getEle("displayNowPage").innerHTML = getEle("page").value;
		refreshTable();
	}
}
function nextMaxPage(val){
	var maxPage = getEle("maxPage").value;
	if(isMEM(val)){
		if(parseInt(maxPage) != parseInt(getEle("page").value)){
			 var str = getEle("searchOrderInfo").value+"&nextPage="+maxPage;
			 updateSearchJsonValue("nextPage",maxPage);
			 
//			 getSearchAccDataAjax(str);
			 renewSearchLastDataAjax();
		}
		
	}else if(isNotMEM(val)){
		getEle("page").value = maxPage;
		getEle("displayNowPage").innerHTML = getEle("page").value;
		refreshTable();
	}
}

function previousPage(val){
	var previousPage = parseInt(getEle("page").value)-1;
	var page = "1"
	if(previousPage > 0){
		page = previousPage;
	}
	
	
	if(isMEM(val)){
		if(parseInt(page) != parseInt(getEle("page").value)){
			var str = getEle("searchOrderInfo").value+"&nextPage="+page;
			updateSearchJsonValue("nextPage",page);
//			getSearchAccDataAjax(str);
			renewSearchLastDataAjax();
		}
		
	}else if(isNotMEM(val)){
		getEle("page").value = page;
		getEle("displayNowPage").innerHTML = getEle("page").value;
		
		refreshTable();
	}
}

function firstPage(val){
	if(isMEM(val)){
		if(parseInt(getEle("page").value) != 1){
			var str = getEle("searchOrderInfo").value+"&nextPage=1";
			updateSearchJsonValue("nextPage",1);
//			 getSearchAccDataAjax(str);
			renewSearchLastDataAjax();
		}
		
	}else if(isNotMEM(val)){
		getEle("page").value = "1";
		getEle("displayNowPage").innerHTML = getEle("page").value;
		refreshTable();
	}
}
function refreshTable(){
	var tmpString = showAMTable();
	if (tmpString != null && tmpString != undefined) {
		getEle("mainContain").innerHTML = tmpString;
		if(isJSON(getEle("currentMemberListInfo").value)){
			var jsonObj = JSON.parse(getEle("currentMemberListInfo").value);
			if(jsonObj.searchTime != null){
				if(getEle("searchTime") != null && getEle("searchTime") != undefined){
					getEle("searchTime").innerHTML = jsonObj.searchTime;
				}
			}
		}
	} else {
		getEle("mainContain").innerHTML = "";
	}
}
function setMaxPage(count){
	var searchCount =parseInt(getEle("changSelectAccDataCount").value);
	getEle("maxPage").value =  Math.ceil(count/searchCount) == 0 ? 1 : Math.ceil(count/searchCount);
	getEle("displayMaxPage").innerHTML = getEle("maxPage").value;
}

function changPageBnt(val){
	var pageNumber = getEle("changePageText").value;
	var maxPage = getEle("maxPage").value;
	if(isMEM(val)){
		if(pageNumber > 0 && pageNumber != "" && pageNumber != null && pageNumber <= maxPage){
			if(parseInt(getEle("page").value) != pageNumber){
				 var str = getEle("searchOrderInfo").value+"&nextPage="+pageNumber;
				 updateSearchJsonValue("nextPage",pageNumber);
//				 getSearchAccDataAjax(str);
				 renewSearchLastDataAjax();
			}
		}
	}else if(isNotMEM(val)){
		if(pageNumber > 0 && pageNumber != "" && pageNumber != null && pageNumber <= maxPage){
			getEle("page").value = pageNumber;
			getEle("displayNowPage").innerHTML = getEle("page").value;
			refreshTable();
			
		}
	}
}

function checkPageText(val){
	var maxPage = getEle("maxPage").value;
	if(toInt(val) <= toInt(maxPage) && toInt(val) > 0){
		getEle("changePageText").value = toInt(checkInputNumberVal(val));
	}
	else{
		getEle("changePageText").value = "";
	}
}

function getSearchAccDataAjax(str) {
	var val = getEle("selectedSecMenu").value;
	if(getEle("searchOrderAction").value == ""){
		showTypeOuter(val);
	}
	else{
		getUpperSelectName(val);
		XHR = checkXHR(XHR);
		if (typeof XHR != "undefined" && XHR != null) {
			var tmpStr = str;
			
			XHR.timeout = 10000;
			XHR.ontimeout = function() {
				disableLoadingPage();XHR.abort();
			}
			XHR.onerror = function() {
				disableLoadingPage();XHR.abort();
			}
			XHR.onreadystatechange = window[getEle("handleName").value];
			XHR.open("POST", getEle("searchOrderAction").value+"?date="
					+ getNewTime(), true);
			XHR.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			XHR.send(tmpStr);
			enableLoadingPage();
		}
	}
}

function renewSearchLastDataAjax() {
	var val = getEle("selectedSecMenu").value;
	if(getEle("searchActionUrl").value != "" && getEle("searchHandlFunction").value in window){
		try{
			enableLoadingPage();
			XHR = checkXHR(XHR);
			if (typeof XHR != "undefined" && XHR != null) {
				if(isJSON(getEle("searchPostJson").value) && getEle("searchActionUrl").value != "" && getEle("searchHandlFunction").value in window){
					var json = JSON.parse(getEle("searchPostJson").value); 
					json["tokenId"] = getEle("tokenId").value;
					var tmpStr = joinVars("&",json,true);

					XHR.timeout = 10000;
					XHR.ontimeout = function() {
						disableLoadingPage();XHR.abort();
					}
					XHR.onerror = function() {
						disableLoadingPage();XHR.abort();
					}
					XHR.onreadystatechange = window[getEle("searchHandlFunction").value];
					XHR.open("POST", getEle("searchActionUrl").value+"?date="+ getNewTime(), true);
					XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					XHR.send(tmpStr);
				}
			}
		}catch(error){
			console_Log("renewSearchLastDataAjax::"+error);
		}
	}
}

function getAccData(val,upAccId,accId,status,accLevel,accName,memberType,checkStatus,count,page,isChangeSrarchDiv=true) {
	enableLoadingPage();
	XHR = checkXHR(XHR);
	var obj = {};
	
	if (typeof XHR != "undefined" && XHR != null) {
		obj["tokenId"] = getEle("tokenId").value;
		obj["init"] = 0;
		if(val != ""){
			obj["type"] = val;
		}
		if(upAccId != ""){
			obj["upAccId"] = upAccId;
		}
		if(accId != ""){
			obj["accId"] = accId;
		}
		if(status != ""){
			obj["status"] = status;
		}
		if(accLevel != ""){
			obj["accLevel"] = accLevel;
		}
		if(accName != ""){
			obj["accName"] = accName;
		}
		if(memberType != ""){
			obj["memberType"] = memberType;
		}
		if(count != ""){
			obj["count"] = count;
		}
		if(checkStatus != ""){
			obj["checkStatus"] = checkStatus;
		}
		if(page != ""){
			obj["page"] = page;
		}
		if(getEle("asc").value != ""){
			obj["asc"] = getEle("asc").value;
		}
		if(getEle("sort").value != ""){
			obj["sort"] = getEle("sort").value;
		}
		
		var tmpstr = joinVars("&",obj,true);
	
		XHR = checkXHR(XHR);
		XHR.onreadystatechange = handleRequestAllData;
		XHR.open("POST", "./AccountManage!ajax.php?date=" + getNewTime(),true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();

		
		getEle("searchPostJson").value = JSON.stringify(obj);
		getEle("searchActionUrl").value = "./AccountManage!ajax.php";
		getEle("searchHandlFunction").value = "handleRequestAllData";
		
		if(isChangeSrarchDiv){
			activeSideBar(val);
			changeSearchDiv(val);
		}
	}
}
function showOpsLog() {
	var json = JSON.parse(getEle("getLogInfo").value);
	var accLevel = getEle("selectedSecMenu").value;
	var josnObject = json.showOpsLog;
	var count = getEle("logCount").value;
	var str = [];
	
	str[0]='<div class="modal-content width-percent-960 margin-fix-4">';
	str[1]='<span class="close" onclick="onClickCloseModalV2();">×</span>';
	str[2]='<h3>新增帳號與狀態修改</h3>';
	str[3]='<p class="media-control text-center">';
	str[4]='<a href="javascript:void(0);" onclick="firstLogPage(0,1,'+accLevel+','+count+');" class="backward"></a>';
	str[5]='<a href="javascript:void(0);" onclick="previousLogPage(0,1,'+accLevel+','+count+');" class="backward-fast"></a>';
	str[6]='<span>總頁數：<i id= "displayNowLogPage">1</i><span>/</span><i id = "displayNowLogMaxPage">1</i>頁</span>';
	str[7]='<a href="javascript:void(0);" onclick="nextLogPage(0,1,'+accLevel+','+count+');" class="forward"></a>';
	str[8]='<a href="javascript:void(0);" onclick="nextLogMaxPage(0,1,'+accLevel+','+count+');" class="forward-fast"></a></p>';
	str[9]='<div class="tab-content">';
	str[10]='<table class="table-zebra tr-hover">';
	str[11]='<tbody>';
	str[12]='<tr><th>編號</th><th>操作者</th><th>被操作者</th><th>日期</th><th>IP</th><th>內容</th></tr>';
	str[13]='';
	str[14]='</tbody>';
	str[15]='</table>';
	str[16]='</div>';
	str[17]='</div>';
	
	var showHtml = '';
	for (var i = 0; i < Object.keys(josnObject).length; i++) {
		var addAccData = showAddAccData(josnObject[i].action,josnObject[i].detail,accLevel);
		if (josnObject[i].opsName != "") {
			str[13] += '<tr><td>'+(i+1)+'</td><td>'+josnObject[i].opsAccName+'</td><td>'+josnObject[i].accName+'</td><td>'+josnObject[i].opsDatetime+'</td><td>'+josnObject[i].ip+'</td><td>'+addAccData+'</td></tr>';
		}
	}
	showHtml = str.join('');
	onClickOpenModalV2(showHtml);
}
function showAddAccData(action,detail,accLevel){
	var data = '';
	try{
	if(!isNull(action) && action != '' && !isNull(detail) && !isNull(accLevel) && accLevel != ''){
		if(isJSON(detail)){
			detail = JSON.parse(detail);
		}
		if(action == LOG_ACTION_ADD_ACC){
			if(!isNull(detail.nickname)){
				data = ACTION_JSON[action]+':'+LEVEL_NICKNAME[accLevel]+'帳號:'+detail.nickname;
			}
		}else if(action == LOG_ACTION_ENABLE_ACC){
				data = '啟用';
		}else if(action == LOG_ACTION_DISABLE_ACC){
				data = '停用';
		}else if(action == LOG_ACTION_DO_NOT_LOGIN_ACC){
				data = '禁止';
		}else if(action == LOG_ACTION_DELETE_ACC){
				data = '刪除';
		}else {
			data = '';
		}
	}else{
		data = '';
	}
	}catch(e){
		console_Log("showAddAccData error="+error.message);
	}
	return data;
}
function confromShowOpsLog(accLevel, count){
	var accLevel = getEle("selectedSecMenu").value;
	var logPage = getEle("logPage").value;
	var logNextPage = logPage;
	var count = getEle("logCount").value;
	
	if(accLevel >= ACC_LEVEL_COM && accLevel <=ACC_LEVEL_MEM){
		var str = "&updateAccId=0&actionUpperId=2&accLevel="+accLevel+"&count="+count+"&logPage="+logPage+"&logNextPage="+logNextPage;
		showOpsLogAjax(str);
	}
}

function showOpsLogAjax(str) {
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
		XHR.onreadystatechange = handleShowOpsLog;
		XHR.open("POST", "./AccountManage!showLog.php?date="
				+ getNewTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.send(tmpStr);
		enableLoadingPage();
	}
}
function handleShowOpsLog() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					getEle("getLogInfo").value = XHR.responseText;
					var json = JSON.parse(getEle("getLogInfo").value);
					if(checkTokenIdfail(json)){
					showOpsLog();
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
				console_Log("handleShowOpsLog error");
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

function showSettingLog() {
	var json = JSON.parse(getEle("getLogInfo").value);
	var accLevel = getEle("selectedSecMenu").value;
	var settingjson = JSON.parse(getEle("seetingAccountJson").value);
	if(accLevel >=ACC_LEVEL_BC && accLevel <= ACC_LEVEL_MEM){
		var updateAccId = settingjson.accData.accData.accId;
	}else if(accLevel <=ACC_LEVEL_SC && accLevel >= ACC_LEVEL_COM){
		var updateAccId = settingjson.accData.accId;
	}
	var josnObject = json.showOpsLog;
	var count = getEle("logCount").value;
	var nowActionUpperId = getEle("nowActionUpperId").value;
	
	var logTableName = ["0","1","狀態更動紀錄列表","一般設定","點數異動","佔成異動","佔滿異動","詳細資料更動紀錄列表","補單紀錄列表","提款水單審核紀錄列表","充值水單審核紀錄列表","複審紀錄列表","權限更動紀錄列表","GameServer更動紀錄列表"];
	var moneyType = ["0","新增帳號存入","新增帳號取出","設定帳號存入","設定帳號取出","水單充值","水單扣款"];
	var gameType = ["全部遊戲","彩票","真人視訊","運動","電動","遊戲"];
	var logTableTitle = ["0","1","狀態更動紀錄",
		"<tr><th>編號</th><th>帳號</th><th>日期</th><th>IP</th><th>內容</th></tr>",
		"<tr><th>編號</th><th>操作者</th><th>時間</th><th>金額</th><th>方式</th><th>項目</th><th>可用現金</th><th>上筆現金</th><th>IP</th></tr>",
		"<tr><th>編號</th><th>日期</th><th>玩法</th><th>修改前"+LEVEL_NICKNAME[accLevel]+"可佔</th><th>修改後"+LEVEL_NICKNAME[accLevel]+"可佔</th><th>修改人</th></tr>",
		"<tr><th>編號</th><th>日期</th><th>修改前</th><th>修改後</th><th>修改人</th></tr>","詳細資料更動紀錄","補單紀錄","提款水單審核紀錄","充值水單審核紀錄","複審紀錄","權限更動紀錄","GameServer更動紀錄"];
	
	var str = [];
	
	str[0]='<div class="modal-content width-percent-960 margin-fix-4">';
	str[1]='<span class="close" onclick="onClickCloseModalV2();">×</span>';
	str[2]='<h3>'+logTableName[nowActionUpperId]+'</h3>';
	str[3]='<p class="media-control text-center">';
	str[4]='<a href="javascript:void(0);" onclick="firstLogPage('+updateAccId+','+nowActionUpperId+','+accLevel+','+count+');" class="backward"></a>';
	str[5]='<a href="javascript:void(0);" onclick="previousLogPage('+updateAccId+','+nowActionUpperId+','+accLevel+','+count+');" class="backward-fast"></a>';
	str[6]='<span>總頁數：<i id= "displayNowLogPage">1</i><span>/</span><i id = "displayNowLogMaxPage">1</i>頁</span>';
	str[7]='<a href="javascript:void(0);" onclick="nextLogPage('+updateAccId+','+nowActionUpperId+','+accLevel+','+count+');" class="forward"></a>';
	str[8]='<a href="javascript:void(0);" onclick="nextLogMaxPage('+updateAccId+','+nowActionUpperId+','+accLevel+','+count+');" class="forward-fast"></a></p>';
	str[9]='<div class="tab-area">';
	str[10]='<button onclick="confromShowSettingLog(5);">佔成異動</button>\n';
	str[11]='<button onclick="confromShowSettingLog(6);">佔滿異動</button>\n';
	str[12]='<button onclick="confromShowSettingLog(3);">一般設定</button>\n';
	str[13]='<button onclick="confromShowSettingLog(4);">點數異動</button>\n';
	str[14]='</div>';
	str[15]='<div class="tab-content">';
	str[16]='<table class="table-zebra tr-hover">';
	str[17]='<tbody>';
	str[18]= logTableTitle[nowActionUpperId];
	str[19]='';
	str[20]='</tbody>';
	str[21]='</table>';
	str[22]='</div>';
	str[23]='</div>';
	
	var showHtml = '';
	for (var i = 0; i < Object.keys(josnObject).length; i++) {
		var oldFullRatio = showOldFullRatio(josnObject[i].action,josnObject[i].detail);
		var newFullRatio = showNewFullRatio(josnObject[i].action,josnObject[i].detail);
		var oldRatio = showOldLogRatio(josnObject[i].action,josnObject[i].detail);
		var newRatio = showNewLogRatio(josnObject[i].action,josnObject[i].detail);
		var accData = showSetAccData(josnObject[i].action,josnObject[i].detail);
		var moneyData = showMoneyData(josnObject[i].action,josnObject[i].detail);
		if(josnObject[i].action == LOG_ACTION_ADD_ACC_DEPOSIT_ADD || josnObject[i].action == LOG_ACTION_ADD_ACC_DEPOSIT_DEBIT || josnObject[i].action == LOG_ACTION_SET_ACC_DEPOSIT_ADD || josnObject[i].action == LOG_ACTION_SET_ACC_DEPOSIT_DEBIT || josnObject[i].action == LOG_ACTION_RECHARGE_ORDER_AUDIT_SUCCESS_ADD){
			var moneyType = "存款";
		}else if(josnObject[i].action == LOG_ACTION_SET_ACC_WITHDRAWAL_ADD || josnObject[i].action == LOG_ACTION_SET_ACC_WITHDRAWAL_DEBIT || josnObject[i].action == LOG_ACTION_WITHDRAWAL_ORDER_AUDIT_FAIL_ADD){
			var moneyType = "提款";
		}
		
		var logTableData = ["0","1","狀態更動紀錄",
			"<tr><td>"+(i+1)+"</td><td>"+josnObject[i].opsAccName+"</td><td>"+josnObject[i].opsDatetime+"</td><td>"+josnObject[i].ip+"</td><td>"+accData+"</td></tr>",
			"<tr><td>"+(i+1)+"</td><td>"+josnObject[i].opsAccName+"</td><td>"+josnObject[i].opsDatetime+"</td><td>"+moneyData.money+"</td><td>"+ACTION_JSON[josnObject[i].action]+"</td><td>"+moneyType+"</td><td>"+moneyData.newBalance+"</td><td>"+moneyData.oldBalance+"</td><td>"+josnObject[i].ip+"</td></tr>",
			"<tr><td>"+(i+1)+"</td><td>"+josnObject[i].opsDatetime+"</td><td>"+gameType[0]+"</td><td>"+oldRatio+"</td><td>"+newRatio+"</td><td>"+josnObject[i].opsAccName+"</td></tr>",
			"<tr><td>"+(i+1)+"</td><td>"+josnObject[i].opsDatetime+"</td><td>"+oldFullRatio+"</td><td>"+newFullRatio+"</td><td>"+josnObject[i].opsAccName+"</td></tr>",
			"詳細資料更動紀錄","補單紀錄","提款水單審核紀錄","充值水單審核紀錄","複審紀錄","權限更動紀錄","GameServer更動紀錄"];
		if (josnObject[i].opsName != "") {
			str[19] += logTableData[nowActionUpperId];
		}
	}
	showHtml = str.join('');
	onClickOpenModalV2(showHtml);
}
function showMoneyData(action,detail){
	try{
	if(typeof action != "undefined" && action != null && typeof detail != "undefined" && detail != null){
		if(isJSON(detail)){
			var detail = JSON.parse(detail);
		}
		if(action == LOG_ACTION_ADD_ACC_DEPOSIT_ADD || action == LOG_ACTION_ADD_ACC_DEPOSIT_DEBIT || action == LOG_ACTION_SET_ACC_DEPOSIT_ADD || action == LOG_ACTION_SET_ACC_DEPOSIT_DEBIT || action == LOG_ACTION_SET_ACC_WITHDRAWAL_ADD || action == LOG_ACTION_SET_ACC_WITHDRAWAL_DEBIT || action == LOG_ACTION_WITHDRAWAL_ORDER_AUDIT_FAIL_ADD || action == LOG_ACTION_RECHARGE_ORDER_AUDIT_SUCCESS_ADD){
			data = detail;
		}else {
			data = '';
		}
	}else{
		data = '';
	}
	}catch(e){
		console_Log("showMoneyData error="+error.message);
	}
	return data;
}
function showSetAccData(action,detail){
	var data = '';
	var json = '';
	try{
	if(typeof action != "undefined" && action != null && typeof detail != "undefined" && detail != null){
		if(isJSON(detail)){
			json = JSON.parse(detail);
		}
		if(action == LOG_ACTION_SET_PASSWORD){
			data = "修改密碼"
		}else if(action == LOG_ACTION_SET_WITHDRAWAL_PASSWORD){
			data = "修改取款密碼"
		}else if(action == LOG_ACTION_SET_NICKNAME){
			
			data = "修改名稱</br>舊名稱:"+json['nickname'][0]+"</br>新名稱:"+json['nickname'][1];
		}else{
			data = '';
		}
	}else{
		data = '';
	}
	}catch(e){
		console_Log("showSetAccData error="+error.message);
	}
	return data;
}
function showOldFullRatio(action,detail){
	try{
	if(typeof action != "undefined" && action != null && typeof detail != "undefined" && detail != null){
		if(action == LOG_ACTION_SET_FULL_RATIO){
			if(isJSON(detail)){
				var json = JSON.parse(detail);
			}
			var data = "";
				data = (json.nextFullRatio[0] == 1 ? "佔滿" : "不佔滿");
			delete json;
			delete detailKey;
		}else{
			data = '';
		}
	}else{
		data = '';
	}
	}catch(e){
		console_Log("showOldFullRatio error="+error.message);
	}
	return data;
}
function showNewFullRatio(action,detail){
	try{
	if(typeof action != "undefined" && action != null && typeof detail != "undefined" && detail != null){
		if(action == LOG_ACTION_SET_FULL_RATIO){
			if(isJSON(detail)){
				var json = JSON.parse(detail);
			}
			var data = "";
			data = (json.nextFullRatio[1] == 1 ? "佔滿" : "不佔滿");
			delete json;
			delete detailKey;
		}else{
			data = '';
		}
	}else{
		data = '';
	}
	}catch(e){
		console_Log("showNewFullRatio error="+error.message);
	}
	return data;
}
function showOldLogRatio(action,detail){
	var data = '';
	var json = '';
	var detailKey = '';
	var ratioKey = '';
	try{
	if(typeof action != 'undefined' && action != null && typeof detail != 'undefined' && detail != null){
		if(isJSON(detail)){
			json = JSON.parse(detail);
		}
		detailKey = Object.keys(json);
		ratioKey = Object.keys(RATIO_NAME_JSON);
		if(action == LOG_ACTION_SET_RATIO){
			for(var i = 0; i < ratioKey.length; i++){
				for(var j =0;j < detailKey.length;j++){
					if(detailKey[j] == ratioKey[i]){
						data = data.concat(RATIO_NAME_JSON[detailKey[j]], json[detailKey[j]][0],'</br>');
					}
				}
			}
		}else{
			data = '';
		}
	}else{
		data = '';
	}
	}catch(e){
		console_Log('showOldLogRatio error='+error.message);
	}finally{
		delete json;
		delete detailKey;
		delete ratioKey;
	}
	return data;
}
function showNewLogRatio(action,detail){
	var data = '';
	var json = '';
	var detailKey = '';
	var ratioKey = '';
	try{
	if(typeof action != 'undefined' && action != null && typeof detail != 'undefined' && detail != null){
		if(isJSON(detail)){
			json = JSON.parse(detail);
		}
		detailKey = Object.keys(json);
		ratioKey = Object.keys(RATIO_NAME_JSON);
		if(action == LOG_ACTION_SET_RATIO){
			for(var i = 0; i < ratioKey.length; i++){
				for(var j =0;j < detailKey.length;j++){
					if(detailKey[j] == ratioKey[i]){
						data = data.concat(RATIO_NAME_JSON[detailKey[j]], json[detailKey[j]][1],'</br>');
					}
				}
			}
		}else{
			data = '';
		}
	}else{
		data = '';
	}
	}catch(e){
		console_Log('showOldLogRatio error='+error.message);
	}finally{
		delete json;
		delete detailKey;
		delete ratioKey;
	}
	return data;
}

function confromShowSettingLog(actionUpperId){
	var accLevel = getEle("selectedSecMenu").value;
	var json = JSON.parse(getEle("seetingAccountJson").value);
	if(accLevel >= ACC_LEVEL_BC && accLevel <= ACC_LEVEL_MEM){
		var updateAccId = json.accData.accData.accId;
	}else if(accLevel <= ACC_LEVEL_SC && accLevel >= ACC_LEVEL_COM){
		var updateAccId = json.accData.accId;
	}
	getEle("nowActionUpperId").value = actionUpperId;
	var logPage = getEle("logPage").value;
	var logNextPage = logPage;
	var count = getEle("logCount").value;
	
	if(accLevel >= ACC_LEVEL_COM && accLevel <=ACC_LEVEL_MEM && updateAccId > 0){
		var str = "&updateAccId="+updateAccId+"&actionUpperId="+actionUpperId+"&accLevel="+accLevel+"&count="+count+"&logPage="+logPage+"&logNextPage="+logNextPage;
		showSettingLogAjax(str);
	}
}

function showSettingLogAjax(str) {
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
		XHR.onreadystatechange = handleShowSettingLogAjax;
		XHR.open("POST", "./AccountManage!showLog.php?date="
				+ getNewTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.send(tmpStr);
		enableLoadingPage();
	}
}
function handleShowSettingLogAjax() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					getEle("getLogInfo").value = XHR.responseText;
					var json = JSON.parse(getEle("getLogInfo").value);
					if(checkTokenIdfail(json)){
					showSettingLog();
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
				console_Log("handleShowSettingLogAjax error"+error.message);
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
function checkTokenIdfail(json){
	if(typeOf(json.tokenId) === "undefined" || json.tokenId == null || json.tokenId == "fail"){
		var logoutFunction = function (){ window.location.reload(true); onCheckCloseModelPage();};
		showModePage("閒置時間過長，請重新登入",logoutFunction);
		console_Log("Token ID fail :"+json.tokenId);
		return false;
	}
	else{
		return true;
	}
	
}

function getLowerLevelAccTotalAjax(){
	XHR_getLowerLevelAccTotal = checkXHR(XHR_getLowerLevelAccTotal);
	if (typeof XHR_getLowerLevelAccTotal != "undefined" && XHR_getLowerLevelAccTotal != null) {
		var tmpStr = "tokenId="+encodeURIComponent(getEle("tokenId").value);
		XHR_getLowerLevelAccTotal.onreadystatechange = handleGetLowerLevelAccTotalAjax;
		XHR_getLowerLevelAccTotal.open("POST", "./AccountManage!getLowerLevelAccTotal.php?date=" + new Date().getTime(),true);
		XHR_getLowerLevelAccTotal.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XHR_getLowerLevelAccTotal.send(tmpStr);
	}
}

function handleGetLowerLevelAccTotalAjax(){
	if (XHR_getLowerLevelAccTotal.readyState == 4) {
		if (XHR_getLowerLevelAccTotal.status == 200) {
			try {
				if (isJSON(XHR_getLowerLevelAccTotal.responseText)) {
					var json=JSON.parse(XHR_getLowerLevelAccTotal.responseText);
					if(checkTokenIdfail(json)){
						getEle("magTotal").innerHTML = json.onlineMagCount;
						getEle("memTotal").innerHTML= json.onlineMemCount;
					}
				}
			} catch (error) {
				console_Log("handleGetLowerLevelAccTotalAjax error:"+error.message);
			} finally {
				XHR_getLowerLevelAccTotal.abort();
				setTimeout(function(){getLowerLevelAccTotalAjax();},30000);
				
			}
		}
	}
}

function showPWDChangePage(){
	var str1 = '<div class="modal-content width-percent-460 margin-fix-5">';
	var str2 = '<span class="close" onclick="onClickCloseModal();">&times;</span>';
	var str3 = '<h3>更改密碼<h3>'
	var str4 = '<table class="first-th-fix-1">';
	var str5 = '<tr><th>請輸入舊密碼</th><td><input type="password" class="margin-fix-2" maxlength="20" id="oldPWD" placeholder="此欄位必填" onkeyup = "checkOldPWD();"><span id = "oldPWDSpan"></span></td></tr>';
	var str6 = '<tr><th>請輸入新密碼</th><td><input type="password" class="margin-fix-2" maxlength="20" id="newPWD" placeholder="此欄位必填" onkeyup = "checkNewPWD();"><span id = "newPWDIcon"></span><span id = "newPWDSpan"></td></tr>';
	var str7 = '<tr><th>請再次輸入新密碼</th><td><input type="password" class="margin-fix-2" maxlength="20" id="newPWDAgain" placeholder="此欄位必填" onkeyup = "checkPWDSame();"><span id = "newPWDAgainIcon"></span><span id = "newPWDAgainSpan"></td></tr>';
	var str8 = '</table><div class="btn-area">';
	var str9 = '<button class="btn-lg btn-orange" onclick="conformPWDChange();">確定</button><button class="btn-lg btn-gray" onclick="onClickCloseModal();">取消</button>';
	var str10 = '</div>';
	var PWDChangePage = "";
	PWDChangePage = PWDChangePage.concat(str1, str2, str3, str4, str5, str6, str7, str8, str9, str10);
	onClickOpenModal(PWDChangePage);
}
function checkOldPWD() {
	getEle("oldPWD").value = checkInputPassWordVal(getEle("oldPWD").value);
	if(getEle("oldPWD").value.length >= 4 && getEle("oldPWD").value.length <= 20){
		getEle("oldPWDSpan").innerHTML = "";
		return true;
	}else{
		getEle("oldPWDSpan").innerHTML = " 輸入長度不符";
	}
	return false;
}
function checkNewPWD(value){
	getEle("newPWD").value = checkInputPassWordVal(getEle("newPWD").value);
	if(getEle("newPWD").value.length >= 4 && getEle("newPWD").value.length <= 20){
			getEle("newPWDIcon").className  = "icon-pass";
			getEle("newPWDSpan").innerHTML = "";
			checkPWDSame();
			return true;
	}else{
		getEle("newPWDIcon").className  = "icon-fail";
		getEle("newPWDSpan").innerHTML = " 輸入長度不符";
	}
	return false;
}

function checkPWDSame(){
	getEle("newPWDAgain").value = checkInputPassWordVal(getEle("newPWDAgain").value);
	if(getEle("newPWDAgain").value.length >= 4 && getEle("newPWD").value.length <= 20){
		if(getEle("newPWDAgain").value.length >= 4 && getEle("newPWD").value.length <= 20){
			if(getEle("newPWDAgain").value == getEle("newPWD").value){
				getEle("newPWDAgainSpan").innerHTML = "";
				getEle("newPWDAgainIcon").className  = "icon-pass";
				return true;
			}
			else{
				getEle("newPWDAgainIcon").className  = "icon-fail";
				getEle("newPWDAgainSpan").innerHTML = " 跟密碼不一致";
				return false;
			}
		}
		else{
			getEle("newPWDAgainIcon").className  = "icon-fail";
			getEle("newPWDAgainSpan").innerHTML = " 輸入長度不符";
			return false;
		}
	}
	else{
		getEle("newPWDAgainIcon").className  = "icon-fail";
		getEle("newPWDAgainSpan").innerHTML = " 輸入長度不符";
		return false;
	}
	
}

function conformPWDChange(){
	var str = '';
	var oldPassWord = getEle("oldPWD").value;
	var newPassWord = getEle("newPWD").value;
	var checkNewPassWord = getEle("newPWDAgain").value;
	
	str += "oldPassWord=" + oldPassWord + "&newPassWord=" + newPassWord + "&checkNewPassWord=" + checkNewPassWord;
	if (oldPassWord != '' 
		&& newPassWord != '' 
		&& checkNewPassWord != ''
		&& getEle("newPWDIcon").className == "icon-pass"
		&& getEle("newPWDAgainIcon").className == "icon-pass"
		) {
		onCheckModelPage('確定送出', 'PWDChangeAjax(\''+str+'\')');
	} else {
		onCheckModelPage2("密碼錯誤或尚未輸入");
	}
}

function PWDChangeAjax(str) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onreadystatechange = handlePWDChangeAjax;
		XHR.open("POST", "./AccountManage!PWDChange.php?date="
				+ getNewTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.send(str+"&tokenId="+encodeURIComponent(getEle("tokenId").value));
		enableLoadingPage();
	}
}
function handlePWDChangeAjax() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json=JSON.parse(XHR.responseText);
					if(checkTokenIdfail(json)){
						if (json.message == "fail") {
							onCheckModelPage2("舊密碼輸入錯誤請重新確認!");
						} else if (json.message == "success") {
							onClickCloseModal();
							
							
						}
					}
				}
			} catch (error) {
				console_Log("handlePWDChangeAjax error:"+error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
				if(json.message == "success"){
					onCheckModelPage2("修改成功，並重新登入");
					logout();
				}
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}
function setAccNameCookie(accName){
	var now = new Date();
	var time = now.getTime();
	var expireTime = time + (24*60*60*1000);
	now.setTime(expireTime);
	
	document.cookie = "accName=" + accName + "; expires=" + now + '; path=/';;
}

function closeAccNameCookie(){
	var now = new Date();
	var now = new Date();
	var time = now.getTime();
	var expireTime = time;
	now.setTime(expireTime);
	document.cookie = "accName = ; expires=" + now + '; path=/';;
}

function getAccNameCookie(){
	var accName = getCookie("accName");
	if(accName != ""){
		getEle("account-login").value = accName;
		getEle("saveAccName").checked = true;
	}
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

function logout(){
	logoutAjax();
}

function logoutAjax(){
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value);
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onreadystatechange = handleLogoutAjax;
		XHR.open("POST", "./Login!logout.php?date="+ getNewTime(), true);
		XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();
	}
	
}

function handleLogoutAjax(){
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				console_Log(XHR.responseText);
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if(checkTokenIdfail(json)){
						if(json.message == "fail"){
							
						}
						else{
							window.location.reload(true);
						}
					}
				}
			} catch (error) {
				console_Log("handleLogoutAjax error:"+error.message);
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
function nextLogPage(updateAccId, actionUpperId, accLevel, count){
	var nextPageNum = parseInt(getEle("logPage").value)+1;
	if(nextPageNum <= parseInt(getEle("logMaxPage").value)){
		page = nextPageNum;
	}
	else{
		page = getEle("logMaxPage").value;
	}
	if(updateAccId == 0){
		if(parseInt(page) != parseInt(getEle("logPage").value)){
			var str = "&updateAccId=0&actionUpperId="+actionUpperId+"&accLevel="+accLevel+"&count="+count+"&nextPage="+page;
			showOpsLogAjax(str);
		}	
	}
	else {
		var nowActionUpperId = getEle("nowActionUpperId").value;
		var str = "&updateAccId="+updateAccId+"&actionUpperId="+nowActionUpperId+"&accLevel="+accLevel+"&count="+count+"&nextPage="+page;
		showSettingLogAjax(str);
	}
	
}
function nextLogMaxPage(updateAccId, actionUpperId, accLevel, count){
	var maxPage = getEle("logMaxPage").value;
	if(updateAccId == 0){
		if(parseInt(maxPage) != parseInt(getEle("logPage").value)){
			var str = "&updateAccId=0&actionUpperId="+actionUpperId+"&accLevel="+accLevel+"&count="+count+"&nextPage="+maxPage;
			showOpsLogAjax(str);
		}
	}else{
		var nowActionUpperId = getEle("nowActionUpperId").value;
		var str = "&updateAccId="+updateAccId+"&actionUpperId="+nowActionUpperId+"&accLevel="+accLevel+"&count="+count+"&nextPage="+maxPage;
		showSettingLogAjax(str);
	}
}

function previousLogPage(updateAccId, actionUpperId, accLevel, count){
	var previousPage = parseInt(getEle("logPage").value)-1;
	var page = "1"
	if(previousPage > 0){
		page = previousPage;
	}
	if(updateAccId == 0){
		if(parseInt(page) != parseInt(getEle("logPage").value)){
			var str = "&updateAccId=0&actionUpperId="+actionUpperId+"&accLevel="+accLevel+"&count="+count+"&nextPage="+page;
			showOpsLogAjax(str);
		}
	}else{
		var nowActionUpperId = getEle("nowActionUpperId").value;
		var str = "&updateAccId="+updateAccId+"&actionUpperId="+nowActionUpperId+"&accLevel="+accLevel+"&count="+count+"&nextPage="+page;
		showSettingLogAjax(str);
	}
}

function firstLogPage(updateAccId, actionUpperId, accLevel, count){
	if(updateAccId == 0){
		if(parseInt(getEle("logPage").value) != 1){
			var str = "&updateAccId=0&actionUpperId="+actionUpperId+"&accLevel="+accLevel+"&count="+count+"&nextPage=1";
			showOpsLogAjax(str);
		}
	}else{
		var nowActionUpperId = getEle("nowActionUpperId").value;
		var str = "&updateAccId="+updateAccId+"&actionUpperId="+nowActionUpperId+"&accLevel="+accLevel+"&count="+count+"&nextPage=1";
		showSettingLogAjax(str);
	}
}

function setClassFix(){
	if(!getEle("topAreaSection").classList.contains("top-area-fix")){
		getEle("topAreaSection").classList.add("top-area-fix");
	}
	if(!getEle("containerSection").classList.remove("container-fix")){
		getEle("containerSection").classList.add("container-fix");
	}
}

function removeClassFix(){
	if(getEle("topAreaSection").classList.contains("top-area-fix")){
		getEle("topAreaSection").classList.remove("top-area-fix");
	}
	if(getEle("containerSection").classList.remove("container-fix")){
		getEle("containerSection").classList.contains("container-fix");
	}
}

function setIconFail(id){
	if(typeof getEle(id) != "undefined" && getEle(id) != null){
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
	}
}
function showWebProcessing(){
	var logoutFunction = function (){ window.location.reload(true); onCheckCloseModelPage();};
		showModePage("無此權限!",logoutFunction);
}
function searchAccLowerAccData(val,upAccId,status){
	var upperLevel = getEle("selectedSecMenu").value;
	getEle("searchStatus").value = ""+status;
	
	var accId =0;
	var accName = "";
	var accLevel = 0;
	var memberType = "";
	var checkStatus = "true";
	var page = "1";

	getEle("searchPostJson").value = "";
	getEle("searchActionUrl").value = "";
	getEle("searchHandlFunction").value = "";
	
	
	getEle("selectCount").value = defaultCount;
	getEle("page").value = page;
	getEle("maxPage").value = page;
	
	getAccData(val,upAccId,accId,status,upperLevel,accName,memberType,checkStatus,defaultCount,page);
}

function updateSearchJsonValue(key,value){
	if(isNull(value) == false){
		if(isJSON(getEle("searchPostJson").value)){
			var json = JSON.parse(getEle("searchPostJson").value);
			json[key] = value;
			getEle("searchPostJson").value = JSON.stringify(json);
		}
		else{
			var obj = {};
			obj[key] = value;
			getEle("searchPostJson").value = JSON.stringify(obj);
		}
	}
}
