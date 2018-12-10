var LotteryLowfreqAmount_XHR = null;

var lowfreqOldList = [];
var lowfreqNewList = [];
var lowfreqHandicapOldList = [];
var lowfreqSettingLog = [];

var lotteryLowfreqBaseInfo = [];
var lotteryLowfreqHandicapInfo = null;
var lotteryLowfreqAmountSetInfo = null;

const LOTTERY_LOWFREQ_PRIZE_LEVEL = {
		"0":"",
		"1":"(1)",
		"2":"(2)",
		"3":"(3)",
		"4":"(4)",
		"5":"(5)",
		"6":"(6)",
		"7":"(7)",
		"8":"(8)",
		"9":"(9)",
		"10":"(10)",
	};

const LOG_ACTION_LOTTERY_LOWFREQ_SET = 72;// log action 後台_賠率設定_低頻彩

const LOG_ACTION_LOTTERY_LOWFREQ_HANDICAP_SET = 73;// log action 低頻彩盤_口資料設定
const LOG_ACTION_LOTTERY_LOWFREQ_DESCRIPTION_SET = 74;// log action 低頻彩_描述修改

const LOG_ACTION_LOTTERY_LOWFREQ_FC3D_BASE_BASELINE_SET = 75;// log_action福彩3D基礎賠率設定
const LOG_ACTION_LOTTERY_LOWFREQ_PL5_BASE_BASELINE_SET = 76;// log_action排列35基礎賠率設定
const LOG_ACTION_LOTTERY_LOWFREQ_LHC_BASE_BASELINE_SET = 77;// log_action六合彩基礎賠率設定

const LOG_ACTION_LOTTERY_LOWFREQ_FC3D_NOW_BASELINE_SET = 78;// log_action福彩3D即時賠率設定
const LOG_ACTION_LOTTERY_LOWFREQ_PL5_NOW_BASELINE_SET = 79;// log_action排列35即時賠率設定
const LOG_ACTION_LOTTERY_LOWFREQ_LHC_NOW_BASELINE_SET = 80;// log_action六合彩即時賠率設定

const LOTTERY_LOWFREQ_FC3D_TYPE = 6;// type 福彩3D
const LOTTERY_LOWFREQ_PL5_TYPE = 7;// type 排列35
const LOTTERY_LOWFREQ_LHC_TYPE = 618;// type 六合彩

const LOTTERY_LOWFREQ_BASE_BASELINE = 1;// 基礎賠率
const LOTTERY_LOWFREQ_NOW_BASELINE = 2;// 即時賠率

const LOTTERY_LOWFREQ_LOG_TYPE_HANDICAP = 0;
const LOTTERY_LOWFREQ_LOG_TYPE_DESCRIPTION = 1;
const LOTTERY_LOWFREQ_LOG_TYPE_BASE_BASELINE = 2;
const LOTTERY_LOWFREQ_LOG_TYPE_NOW_BASELINE = 3;

function LotteryLowfreqAmountSet(){
	setClassFix();
	LotteryLowfreqAmountSet_into();
	getLotteryLowfreqBaseInfoAjax();
}

function LotteryLowfreqAmountSet_into(){
	var str = '';
	if(document.getElementsByName("lotteryLowfreqTypePage").length!=1){
		str += '\n<input type="hidden" name="lotteryLowfreqAmountSetHandicapPage" value=1>';// 盤口頁
		str += '\n<input type="hidden" name="lotteryLowfreqTypePage" value=6>';// 彩種頁
		str += '\n<input type="hidden" name="lotteryMark6PlayedPage" value=619>';// 六合彩玩法頁
		str += '\n<input type="hidden" name="lotteryMark6MidPage" value=631>';// 六合彩母單頁
		str += '\n<input type="hidden" name="lotteryLowfreqBaselineType" value=1>';// 賠率頁
		
		str += '\n<input type="hidden" name="pageCount" value="10">';// 以下logPage
		str += '\n<input type="hidden" name="nowPage" value="1">';
		str += '\n<input type="hidden" name="totalPage" value="1">';
		str += '\n<input type="hidden" name="nowFirstCount" value="0">';
		str += '\n<input type="hidden" name="nowLastCount" value="0">';
		str += '\n<input type="hidden" name="totalCount" value="0">';
		str += '\n<input type="hidden" name="nowLowfreqLogType" value="">';		
	}else{
		getEle('lotteryLowfreqAmountSetHandicapPage').value = 1;
		getEle('lotteryLowfreqTypePage').value = 6;
		getEle('lotteryMark6PlayedPage').value = 619;
		getEle('lotteryMark6MidPage').value = 631;
		getEle('lotteryLowfreqBaselineType').value = 1;
	}
	if(str!=''){
		document.getElementById("extraHidden").innerHTML = str;
	}
	delete str;
	str = undefined;
	if(document.getElementById("searchArea") != null){
		document.getElementById("searchArea").innerHTML = "";
	}
	if(document.getElementById("mainContain") != null){
		document.getElementById("mainContain").innerHTML = "";
	}
	if(document.getElementById("myModal") != null){
		document.getElementById("myModal").innerHTML = "";
	}
	if(document.getElementById("myModalV2") != null){
		document.getElementById("myModalV2").innerHTML = "";
	}
}

// tool開始//
function getLoginAccId(){
	var json;
	var basic;
	var accId;
	
	if(isJSON(getEle('loginInfo').value)){
		json = JSON.parse(getEle('loginInfo').value);
	}
	if(!isNull(json.basic)){
		basic = json.basic;
	}
	if(!isNull(basic.acc_id)){
		accId = basic.acc_id;
	}
	if(accId > 0){
		return accId;
	}
}

function lotteryOnCheckModelPage(str ,functionName){
	var fName = functionName;
	if(fName == null || typeof fName == "undefined" || fName == ""){
		fName = "onCheckCloseModelPage()";
	}
	var text = '<div class="modal-content width-percent-460 savesetting"> \n'+
				'<span class="close" onclick="onCheckCloseModelPage();">×</span> \n'+
			    '<h3 class="text-center">'+str+'</h3> \n'+
			    '<div class="btn-area">'+
			    	'<button onclick="onCheckCloseModelPage();">取消</button> \n'+
			    	'<button onclick = '+fName+'>保存</button> \n'+
			   ' </div></div> \n';
	getEle("myModalV2").innerHTML = text;
	getEle("myModalV2").style.display = "block";
}

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
// tool結束//

function getLotteryLowfreqBaseInfoAjax() {
	var str = '';
	LotteryLowfreqAmount_XHR = checkXHR(LotteryLowfreqAmount_XHR);
	if (typeof LotteryLowfreqAmount_XHR != "undefined" && LotteryLowfreqAmount_XHR != null) {
		str = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&accId=" + getLoginAccId();
		LotteryLowfreqAmount_XHR.timeout = 10000;
		LotteryLowfreqAmount_XHR.ontimeout = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onerror = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onreadystatechange = handleGetLotteryLowfreqBaseInfoAjax;
		LotteryLowfreqAmount_XHR.open("POST", "/CTT03BetRatioMaster/GetLotteryLowfreqInfo!getAmountBaseList.php?date=" + getNewTime(), true);
		LotteryLowfreqAmount_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LotteryLowfreqAmount_XHR.send(str);
		enableLoadingPage();
	}
}

function handleGetLotteryLowfreqBaseInfoAjax() {
	if (LotteryLowfreqAmount_XHR.readyState == 4) {
		if (LotteryLowfreqAmount_XHR.status == 200) {
			try {
				if (isJSON(LotteryLowfreqAmount_XHR.responseText)) {
					var tmpJ = isJSON2(LotteryLowfreqAmount_XHR.responseText);
					if (tmpJ) {
						if (checkTokenIdfail(tmpJ)) {
							if(!isNull(tmpJ.getHandicapInfo)){
								lotteryLowfreqHandicapInfo = tmpJ.getHandicapInfo;
							}else{
								lotteryLowfreqHandicapInfo = '';
							}
							if(!isNull(tmpJ.getLotteryAmountTypeList)){
								lotteryLowfreqBaseInfo.getLotteryAmountTypeList = tmpJ.getLotteryAmountTypeList;
							}else{
								lotteryLowfreqBaseInfo.getLotteryAmountTypeList = '';
							}
							if(!isNull(tmpJ.getLotteryAmountPlayedList)){
								lotteryLowfreqBaseInfo.getLotteryAmountPlayedList = tmpJ.getLotteryAmountPlayedList;
							}else{
								lotteryLowfreqBaseInfo.getLotteryAmountPlayedList = '';
							}
							if(!isNull(tmpJ.getLotteryMidList)){
								lotteryLowfreqBaseInfo.getLotteryMidList = tmpJ.getLotteryMidList;
							}else{
								lotteryLowfreqBaseInfo.getLotteryMidList = '';
							}
						}
					}
				}
			} catch (error) {
				lotteryLowfreqHandicapInfo = '';
				lotteryLowfreqBaseInfo.getLotteryAmountTypeList = '';
				lotteryLowfreqBaseInfo.getLotteryAmountPlayedList = '';
				lotteryLowfreqBaseInfo.getLotteryMidList = '';
				console_Log("handleGetLotteryLowfreqBaseInfoAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LotteryLowfreqAmount_XHR.abort();
				showLotteryLowfreqBaseInfoPage();
			}
		} else {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
	}
}

function showLotteryLowfreqBaseInfoPage(){
	var lotteryAmountTypeList = lotteryLowfreqBaseInfo.getLotteryAmountTypeList;
	var handicapInfo = lotteryLowfreqHandicapInfo;
	var handicapPage = getEle('lotteryLowfreqAmountSetHandicapPage').value;
	var typePage = getEle('lotteryLowfreqTypePage').value;
	var str = [];
	
	str.push('<div class="btn-area"> \n');
	
	str.push('</div> \n');
	str.push('<div class="contentDiv2-10"> \n');
	str.push('	<div class="tabarea"> \n');
	for(var a = 0 ; a < handicapInfo.length ; a++){
		if(!isNull(handicapInfo[a].handicapName) && !isNull(handicapInfo[a].handicapId)){
			str.push('<button id="handicapLowfreqBtn'+handicapInfo[a].handicapId+'" onclick="oclickLotteryLowfreqHandicapBtn('+handicapInfo[a].handicapId+');">'+handicapInfo[a].handicapName+'盤</button>\n');
		}
	}
	str.push('	</div>');
	str.push('	<div id="lotteryLowfreqHandicapTable" class="lotterymoneysetting posfix">');
	str.push('	</div>');
	str.push('	<div class="lotteryall">');
	for(var c = 0 ; c < lotteryAmountTypeList.length ; c++){
		if(!isNull(lotteryAmountTypeList[c].typeId) && !isNull(lotteryAmountTypeList[c].typeName)){
			str.push('<button id="lotteryLowfreqTypeBtn'+lotteryAmountTypeList[c].typeId+'" onclick="oclickLotteryLowfreqAmountBtn('+lotteryAmountTypeList[c].typeId+');">'+lotteryAmountTypeList[c].typeName+'</button>\n');
		}
	}
	str.push('	</div>');
	str.push('	<div class="lotteryall">');
	str.push('		<button id="lotteryLowfreqBaselineBtn" onclick="lotteryLowfreqBaselineBtn(1);">基礎賠率</button>\n');
	str.push('		<button id="lotteryLowfreqNowBaselineBtn" onclick="lotteryLowfreqBaselineBtn(2);">即時賠率</button>\n');
	str.push('	</div>');
	str.push('	<div id="lotteryLowfreqAmountSetDetail" class="lotteryabcd">');
	str.push('	</div>');
	str.push('	<div id="lotteryMark6SetDetail">');
	str.push('	</div>');
	str.push('</div>');

	getEle("mainContain").innerHTML = str.join("");
	
	oclickLotteryLowfreqHandicapBtn(handicapPage);
	oclickLotteryLowfreqAmountBtn(typePage);
}

function oclickLotteryLowfreqHandicapBtn(handicapPage){
	refreshLowfreqHandicapBtnAndData();
	
	getEle('lotteryLowfreqAmountSetHandicapPage').value = handicapPage;
	getEle('handicapLowfreqBtn'+handicapPage).classList.add('active');
	
	showLotteryLowfreqHandicapTable();
	oclickLotteryLowfreqAmountBtn(6);
}

function oclickLotteryLowfreqAmountBtn(typePage){
	refreshLowfreqTypeBtnAndData();
	
	getEle('lotteryLowfreqTypePage').value = typePage;
	getEle('lotteryLowfreqTypeBtn'+typePage).classList.add('active');
	
	lotteryLowfreqBaselineBtn(1);
}

function lotteryLowfreqBaselineBtn(type){
	refreshLowfreqBaselineTypeBtnAndData();
	
	getEle('lotteryLowfreqBaselineType').value = type;
	
	if(type == LOTTERY_LOWFREQ_BASE_BASELINE){
		getEle('lotteryLowfreqBaselineBtn').classList.add('active');
	}else if(type == LOTTERY_LOWFREQ_NOW_BASELINE){
		getEle('lotteryLowfreqNowBaselineBtn').classList.add('active');
	}
	
	if(getEle('lotteryLowfreqTypePage').value == LOTTERY_LOWFREQ_LHC_TYPE){
		showMark6BasePage();
	}else{
		conformLowfreqAmountInfo();
	}
}

function refreshLowfreqHandicapBtnAndData(){
	var type = lotteryLowfreqBaseInfo.getLotteryAmountTypeList;
	var handicap = lotteryLowfreqHandicapInfo;
	
	for(var i = 0 ; i < type.length ; i++){
		if(!isNull(type[i].typeId)){
			getEle('lotteryLowfreqTypeBtn'+type[i].typeId).classList.remove('active');
		}
	}
	
	for(var i = 0 ; i < handicap.length ; i++){
		if(!isNull(handicap[i].handicapId)){
			getEle('handicapLowfreqBtn'+handicap[i].handicapId).classList.remove('active');
		}
	}
	
	getEle('lotteryLowfreqAmountSetHandicapPage').value = 1;
	getEle('lotteryLowfreqTypePage').value = 6;
	getEle('lotteryLowfreqBaselineType').value = 1;
	
	getEle('lotteryLowfreqBaselineBtn').classList.remove('active');
	getEle('lotteryLowfreqNowBaselineBtn').classList.remove('active');
}

function refreshLowfreqTypeBtnAndData(){
	var type = lotteryLowfreqBaseInfo.getLotteryAmountTypeList;
	
	for(var i = 0 ; i < type.length ; i++){
		if(!isNull(type[i].typeId)){
			getEle('lotteryLowfreqTypeBtn'+type[i].typeId).classList.remove('active');
		}
	}
	
	getEle('lotteryLowfreqTypePage').value = 6;
	getEle('lotteryLowfreqBaselineType').value = 1;
	
	getEle('lotteryLowfreqBaselineBtn').classList.remove('active');
	getEle('lotteryLowfreqNowBaselineBtn').classList.remove('active');
}

function refreshLowfreqBaselineTypeBtnAndData(){
	getEle('lotteryLowfreqBaselineType').value = 1;
	
	getEle('lotteryLowfreqBaselineBtn').classList.remove('active');
	getEle('lotteryLowfreqNowBaselineBtn').classList.remove('active');
}

function showLotteryLowfreqHandicapTable(){
	var handicapInfo = lotteryLowfreqHandicapInfo;
	var handicapPage = getEle('lotteryLowfreqAmountSetHandicapPage').value;
	var str = [];
	
	if(!isNull(handicapInfo)){
		lowfreqHandicapOldList = handicapInfo;
	}
	
	str.push('<button class="btn-log-2" onclick="getLowfreqAmountSettingLogAjax();">操作紀錄</button> \n');
	for(var i = 0 ; i < handicapInfo.length ; i++){
		if(!isNull(handicapInfo[i].handicapId) && handicapInfo[i].handicapId == handicapPage){
			str.push('<p>獎金組：');
			str.push('最低:\n<input type="text" id="lowfreqBonusSetMin" onkeyup="checkLowfreqImputDecimal(this)" onchange="checkLowfreqBonusSize(this)" value="'+ checkNull(handicapInfo[i].bonusSetMin) +'">');
			str.push('最高:\n<input type="text" id="lowfreqBonusSetMax" onkeyup="checkLowfreqImputDecimal(this)" onchange="checkLowfreqBonusSize(this)" value="'+ checkNull(handicapInfo[i].bonusSetMax) +'">');
			str.push('最高中獎金額:\n<input type="text" id="lowfreqMaxWinBonus" onkeyup="checkLowfreqImputDecimal(this)" onchange="checklowfreqMaxWinBonusSize(this)" value="'+ checkNull(handicapInfo[i].maxWinBonus) +'">');
			str.push('<button onclick="conformLowfreqHandicap('+handicapInfo[i].handicapId+')">保存');
			str.push('</button>');
			str.push('</p>');
		}
	}

	getEle('lotteryLowfreqHandicapTable').innerHTML = str.join('');
}

/* getAmountAjax開始 */
function conformLowfreqAmountInfo(){
	var handicapPage = getEle('lotteryLowfreqAmountSetHandicapPage').value;// 盤口頁
	var typePage = getEle('lotteryLowfreqTypePage').value;// 彩種頁
	var mark6PlayedPage = getEle('lotteryMark6PlayedPage').value;// 六合彩玩法頁
	var mark6MidPage = getEle('lotteryMark6MidPage').value;// 六合彩母單頁
	var baselineType = getEle('lotteryLowfreqBaselineType').value;// 賠率頁
	var str = '';
	
	if(typePage == LOTTERY_LOWFREQ_FC3D_TYPE){
		if(baselineType == LOTTERY_LOWFREQ_BASE_BASELINE){
			str = '&handicapPage='+handicapPage+'&typePage='+typePage;
			getLowfreqBaseAmountAjax(str);
		}else if(baselineType == LOTTERY_LOWFREQ_NOW_BASELINE){
			str = '&handicapPage='+handicapPage+'&typePage='+typePage;
			getLowfreqNowAmountAjax(str);
		}
	}else if(typePage == LOTTERY_LOWFREQ_PL5_TYPE){
		if(baselineType == LOTTERY_LOWFREQ_BASE_BASELINE){
			str = '&handicapPage='+handicapPage+'&typePage='+typePage;
			getLowfreqBaseAmountAjax(str);
		}else if(baselineType == LOTTERY_LOWFREQ_NOW_BASELINE){
			str = '&handicapPage='+handicapPage+'&typePage='+typePage;
			getLowfreqNowAmountAjax(str);
		}
	}else if(typePage == LOTTERY_LOWFREQ_LHC_TYPE){
		if(baselineType == LOTTERY_LOWFREQ_BASE_BASELINE){
			str = '&handicapPage='+handicapPage+'&typePage='+typePage+'&mark6PlayedPage='+mark6PlayedPage+'&mark6MidPage='+mark6MidPage;
			getMark6BaseAmountAjax(str);
		}else if(baselineType == LOTTERY_LOWFREQ_NOW_BASELINE){
			str = '&handicapPage='+handicapPage+'&typePage='+typePage+'&mark6PlayedPage='+mark6PlayedPage+'&mark6MidPage='+mark6MidPage;
			getMark6NowAmountAjax(str);
		}
	}
}

function getLowfreqBaseAmountAjax(str) {
	var tmStr = '';
	LotteryLowfreqAmount_XHR = checkXHR(LotteryLowfreqAmount_XHR);
	if (typeof LotteryLowfreqAmount_XHR != "undefined" && LotteryLowfreqAmount_XHR != null) {
		tmStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&accId=" + getLoginAccId() + str;
		LotteryLowfreqAmount_XHR.timeout = 10000;
		LotteryLowfreqAmount_XHR.ontimeout = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onerror = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onreadystatechange = handleGetLowfreqBaseAmountAjax;
		LotteryLowfreqAmount_XHR.open("POST", "/CTT03BetRatioMaster/GetLotteryLowfreqInfo!getLowfreqBaseAmount.php?date=" + getNewTime(), true);
		LotteryLowfreqAmount_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LotteryLowfreqAmount_XHR.send(tmStr);
		enableLoadingPage();
	}
}

function handleGetLowfreqBaseAmountAjax() {
	if (LotteryLowfreqAmount_XHR.readyState == 4) {
		if (LotteryLowfreqAmount_XHR.status == 200) {
			try {
				if (isJSON(LotteryLowfreqAmount_XHR.responseText)) {
					var tmpJ = isJSON2(LotteryLowfreqAmount_XHR.responseText);
					if (tmpJ) {
						if (checkTokenIdfail(tmpJ)) {
							if(!isNull(tmpJ.getAmountInfo)){
								lotteryLowfreqAmountSetInfo = tmpJ.getAmountInfo;
								var temList = JSON.stringify(tmpJ.getAmountInfo);
								lowfreqOldList = JSON.parse(temList);
								lowfreqNewList = JSON.parse(temList);
							}else{
								lotteryLowfreqAmountSetInfo = '';
							}
							showLowfreqLotteryBaseAmountPage();
						}
					}
				}
			} catch (error) {
				lotteryLowfreqAmountSetInfo = '';
				console_Log("handleGetLowfreqBaseAmountAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LotteryLowfreqAmount_XHR.abort();
			}
		} else {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
	}
}

function getLowfreqNowAmountAjax(str) {
	var tmStr = '';
	LotteryLowfreqAmount_XHR = checkXHR(LotteryLowfreqAmount_XHR);
	if (typeof LotteryLowfreqAmount_XHR != "undefined" && LotteryLowfreqAmount_XHR != null) {
		tmStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&accId=" + getLoginAccId() + str;
		LotteryLowfreqAmount_XHR.timeout = 10000;
		LotteryLowfreqAmount_XHR.ontimeout = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onerror = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onreadystatechange = handleGetLowfreqNowAmountAjax;
		LotteryLowfreqAmount_XHR.open("POST", "/CTT03BetRatioMaster/GetLotteryLowfreqInfo!getLowfreqNowAmount.php?date=" + getNewTime(), true);
		LotteryLowfreqAmount_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LotteryLowfreqAmount_XHR.send(tmStr);
		enableLoadingPage();
	}
}

function handleGetLowfreqNowAmountAjax() {
	if (LotteryLowfreqAmount_XHR.readyState == 4) {
		if (LotteryLowfreqAmount_XHR.status == 200) {
			try {
				if (isJSON(LotteryLowfreqAmount_XHR.responseText)) {
					var tmpJ = isJSON2(LotteryLowfreqAmount_XHR.responseText);
					if (tmpJ) {
						if (checkTokenIdfail(tmpJ)) {
							if(!isNull(tmpJ.getAmountInfo)){
								lotteryLowfreqAmountSetInfo = tmpJ.getAmountInfo;
								var temList = JSON.stringify(tmpJ.getAmountInfo);
								lowfreqOldList = JSON.parse(temList);
								lowfreqNewList = JSON.parse(temList);
							}else{
								lotteryLowfreqAmountSetInfo = '';
							}
							showLotteryLowfreqNowAmountPage();
						}
					}
				}
			} catch (error) {
				lotteryLowfreqAmountSetInfo = '';
				console_Log("handleGetLowfreqNowAmountAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LotteryLowfreqAmount_XHR.abort();
			}
		} else {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
	}
}

function getMark6BaseAmountAjax(str) {
	var tmStr = '';
	LotteryLowfreqAmount_XHR = checkXHR(LotteryLowfreqAmount_XHR);
	if (typeof LotteryLowfreqAmount_XHR != "undefined" && LotteryLowfreqAmount_XHR != null) {
		tmStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&accId=" + getLoginAccId() + str;
		LotteryLowfreqAmount_XHR.timeout = 10000;
		LotteryLowfreqAmount_XHR.ontimeout = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onerror = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onreadystatechange = handleGetMark6BaseAmountAjax;
		LotteryLowfreqAmount_XHR.open("POST", "/CTT03BetRatioMaster/GetLotteryLowfreqInfo!getMark6BaseAmount.php?date=" + getNewTime(), true);
		LotteryLowfreqAmount_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LotteryLowfreqAmount_XHR.send(tmStr);
		enableLoadingPage();
	}
}

function handleGetMark6BaseAmountAjax() {
	if (LotteryLowfreqAmount_XHR.readyState == 4) {
		if (LotteryLowfreqAmount_XHR.status == 200) {
			try {
				if (isJSON(LotteryLowfreqAmount_XHR.responseText)) {
					var tmpJ = isJSON2(LotteryLowfreqAmount_XHR.responseText);
					if (tmpJ) {
						if (checkTokenIdfail(tmpJ)) {
							if(!isNull(tmpJ.getAmountInfo)){
								lotteryLowfreqAmountSetInfo = tmpJ.getAmountInfo;
								var temList = JSON.stringify(tmpJ.getAmountInfo);
								lowfreqOldList = JSON.parse(temList);
								lowfreqNewList = JSON.parse(temList);
							}else{
								lotteryLowfreqAmountSetInfo = '';
							}
							showMark6BaseAmountPage();
						}
					}
				}
			} catch (error) {
				lotteryLowfreqAmountSetInfo = '';
				console_Log("handleGetMark6BaseAmountAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LotteryLowfreqAmount_XHR.abort();
			}
		} else {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
	}
}

function getMark6NowAmountAjax(str) {
	var tmStr = '';
	LotteryLowfreqAmount_XHR = checkXHR(LotteryLowfreqAmount_XHR);
	if (typeof LotteryLowfreqAmount_XHR != "undefined" && LotteryLowfreqAmount_XHR != null) {
		tmStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&accId=" + getLoginAccId() + str;
		LotteryLowfreqAmount_XHR.timeout = 10000;
		LotteryLowfreqAmount_XHR.ontimeout = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onerror = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onreadystatechange = handleGetMark6NowAmountAjax;
		LotteryLowfreqAmount_XHR.open("POST", "/CTT03BetRatioMaster/GetLotteryLowfreqInfo!getMark6NowAmount.php?date=" + getNewTime(), true);
		LotteryLowfreqAmount_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LotteryLowfreqAmount_XHR.send(tmStr);
		enableLoadingPage();
	}
}

function handleGetMark6NowAmountAjax() {
	if (LotteryLowfreqAmount_XHR.readyState == 4) {
		if (LotteryLowfreqAmount_XHR.status == 200) {
			try {
				if (isJSON(LotteryLowfreqAmount_XHR.responseText)) {
					var tmpJ = isJSON2(LotteryLowfreqAmount_XHR.responseText);
					if (tmpJ) {
						if (checkTokenIdfail(tmpJ)) {
							if(!isNull(tmpJ.getAmountInfo)){
								lotteryLowfreqAmountSetInfo = tmpJ.getAmountInfo;
								var temList = JSON.stringify(tmpJ.getAmountInfo);
								lowfreqOldList = JSON.parse(temList);
								lowfreqNewList = JSON.parse(temList);
							}else{
								lotteryLowfreqAmountSetInfo = '';
							}
							showMark6NowAmountPage();
						}
					}
				}
			} catch (error) {
				lotteryLowfreqAmountSetInfo = '';
				console_Log("handleGetMark6NowAmountAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LotteryLowfreqAmount_XHR.abort();
			}
		} else {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
	}
}
/* getAmountAjax結束 */

/* showAmountTable開始 */
function showLowfreqLotteryBaseAmountPage(){
	getEle('lotteryLowfreqAmountSetDetail').style.display = 'block';
	getEle('lotteryMark6SetDetail').style.display = 'none';
	
	var playedList = lotteryLowfreqBaseInfo.getLotteryAmountPlayedList;
	var type = getEle('lotteryLowfreqTypePage').value;
	var info = lotteryLowfreqAmountSetInfo;
	
	var minStr = [];
	
	for(var i = 0 ; i < playedList.length ; i++){
		if(playedList[i].typeId == type){
			minStr.push('<div>');
			minStr.push('	<h5>'+playedList[i].playedName+'<button onclick="compareLowfreqAmount();">保存</button></h5>');
			minStr.push('	<table>');
			minStr.push('		<tbody>');
			minStr.push('			<tr><th>玩法</th><th>最低投注金額</th><th>基礎賠率</th><th>單挑</th><th>操作</th></tr>');
			for(var ii = 0 ; ii < info.length ; ii++){
				if(info[ii].midId == playedList[i].playedId){
					minStr.push('<tr>');
					minStr.push('	<td>'+info[ii].minName+LOTTERY_LOWFREQ_PRIZE_LEVEL[info[ii].prizeLevel]+'</td>');
					minStr.push('	<td><input type="text" maxlength="11" onkeyup="checkLowfreqImputDecimal (this)" onchange = "chkLowfreqChange(this.value,\'baseBet\','+ii+');" value="'+ Math.floor(checkNull(info[ii].baseBet)*100)/100 +'"></td>');
					minStr.push('	<td><input type="text" maxlength="11" onkeyup="checkLowfreqImputDecimal (this)" onchange = "chkLowfreqChange(this.value,\'baseline\','+ii+');" value="'+ Math.floor(checkNull(info[ii].baseline)*100)/100 +'"></td>');
					if(info[ii].dtSwitch == 1){
						minStr.push('<td>');
						minStr.push('	<input type="checkbox" checked="checked" onclick="setLowfreqDtSwitch(this,'+ii+');">');
						minStr.push('	<input type="text" onkeyup="checkLowfreqImputDecimal (this)" onchange = "chkLowfreqChange(this.value,\'dtRatio\','+ii+');" value="'+ Math.floor(checkNull(info[ii].dtRatio)*100)/100 +'">％／');
						minStr.push('	<input type="text" onkeyup="checkLowfreqImputDecimal (this)" onchange = "chkLowfreqChange(this.value,\'dtBonus\','+ii+');" value="'+ Math.floor(checkNull(info[ii].dtBonus)*100)/100 +'">金額');
					}else{
						minStr.push('<td class="canttype">');
						minStr.push('	<input type="checkbox" onclick="setLowfreqDtSwitch(this,'+ii+');">');
						minStr.push('	<input type="text" onkeyup="checkLowfreqImputDecimal (this)" onchange = "chkLowfreqChange(this.value,\'dtRatio\','+ii+');" value="'+ Math.floor(checkNull(info[ii].dtRatio)*100)/100 +'" readonly>％／');
						minStr.push('	<input type="text" onkeyup="checkLowfreqImputDecimal (this)" onchange = "chkLowfreqChange(this.value,\'dtBonus\','+ii+');" value="'+ Math.floor(checkNull(info[ii].dtBonus)*100)/100 +'" readonly>金額');
					}
					minStr.push('	</td>');
					minStr.push('	<td><a href="javascript:void(0);" onclick="openLowfreqTextSetDiv('+ii+');">描述修改</a></td>');
					minStr.push('</tr>');
				}
			}
			minStr.push('		</tbody>');
			minStr.push('	</table>');
			minStr.push('</div>');
		}
	}
	
	getEle('lotteryLowfreqAmountSetDetail').innerHTML = minStr.join('');
}

function showLotteryLowfreqNowAmountPage(){
	getEle('lotteryLowfreqAmountSetDetail').style.display = 'block';
	getEle('lotteryMark6SetDetail').style.display = 'none';
	
	var playedList = lotteryLowfreqBaseInfo.getLotteryAmountPlayedList;
	var type = getEle('lotteryLowfreqTypePage').value;
	var info = lotteryLowfreqAmountSetInfo;
	
	var minStr = [];
	
	for(var i = 0 ; i < playedList.length ; i++){
		if(playedList[i].typeId == type){
			minStr.push('<div>');
			minStr.push('	<h5>'+playedList[i].playedName+'<button onclick="compareLowfreqAmount();">保存</button></h5>');
			minStr.push('	<table>');
			minStr.push('		<tbody>');
			minStr.push('			<tr><th>玩法</th><th>最低投注金額</th><th>基礎賠率</th><th>當前累積投注金額</th><th>當前累積注單量</th><th>當前賠率</th></tr>');
			for(var ii = 0 ; ii < info.length ; ii++){
				if(info[ii].midId == playedList[i].playedId){
					minStr.push('<tr>');
					minStr.push('	<td>' + info[ii].minName+LOTTERY_LOWFREQ_PRIZE_LEVEL[info[ii].prizeLevel] + '</td>');
					minStr.push('	<td>' + Math.floor(checkNull(info[ii].baseBet)*100)/100+' </td>');
					minStr.push('	<td>' + Math.floor(checkNull(info[ii].baseline)*100)/100 + '</td>');
					minStr.push('	<td>' + Math.floor(checkNull(info[ii].totalBetAmount)*100)/100 + '</td>');
					minStr.push('	<td>' + Math.floor(checkNull(info[ii].totalNoOfBet)*100)/100 + '</td>');
					minStr.push('	<td><input type="text" maxlength="11" onkeyup="checkLowfreqImputDecimal (this)" onchange = "chkLowfreqChange(this.value,\'nowBaseline\','+ii+');" value="' + Math.floor(checkNull(info[ii].nowBaseline)*100)/100 + '"></td>');
					minStr.push('</tr>');
				}
			}
			minStr.push('		</tbody>');
			minStr.push('	</table>');
			minStr.push('</div>');
		}
	}
	
	getEle('lotteryLowfreqAmountSetDetail').innerHTML = minStr.join('');
}

function showMark6BasePage(){
	getEle('lotteryLowfreqAmountSetDetail').style.display = 'none';
	getEle('lotteryMark6SetDetail').style.display = 'block';
	
	var playedList = lotteryLowfreqBaseInfo.getLotteryAmountPlayedList;
	var midList = lotteryLowfreqBaseInfo.getLotteryMidList;
	
	var m6Str = [];
	
	m6Str.push('<div class="lotteryall">');
	m6Str.push('	<select id="mark6MidSelectList" onchange="oclickMark6MidSelectChange(this.value);">');
	for(var p = 0 ; p < playedList.length ; p++){
		for(var m = 0 ; m < midList.length ; m++){
			if(midList[m].typeId == LOTTERY_LOWFREQ_LHC_TYPE && midList[m].playedId == playedList[p].playedId){
				m6Str.push('<option value="' + midList[m].playedId + ',' + midList[m].midId + '">' + midList[m].midName + '</option>');
			}
		}
	}
	m6Str.push('	</select>');
	m6Str.push('	<button onclick="compareMark6Amount();">保存</button>');
	m6Str.push('</div>\n');
	
	m6Str.push('<div id="showMark6AmountPage">');
	m6Str.push('</div>');
	
	getEle('lotteryMark6SetDetail').innerHTML = m6Str.join('');
	oclickMark6MidSelectChange('619,631');
}

function oclickMark6MidSelectChange(str){
	getEle('lotteryMark6PlayedPage').value = str.split(',')[0];
	getEle('lotteryMark6MidPage').value = str.split(',')[1];
	
	conformLowfreqAmountInfo();
}

function refreshMark6MidBtn(){
	var midList = lotteryLowfreqBaseInfo.getLotteryMidList;
	
	for(var m = 0 ; m < midList.length ; m++){
		if(midList[m].typeId == LOTTERY_LOWFREQ_LHC_TYPE){
			getEle('mark6MidBtn'+midList[m].midId).classList.remove('active');
		}
	}
}

function showMark6BaseAmountPage(){
	getEle('lotteryLowfreqAmountSetDetail').style.display = 'none';
	getEle('lotteryMark6SetDetail').style.display = 'block';
	
	var info = lotteryLowfreqAmountSetInfo;
	var str = [];
	
	str.push('<table>');
	str.push('	<tbody>');
	str.push('		<tr><th>玩法</th><th>最低投注金額</th><th>基礎賠率</th><th>操作</th></tr>');
	for(var i = 0 ; i < info.length ; i++){
		str.push('<tr>')	;
		str.push('	<td>'+info[i].prizeName+'</td>')	;
		str.push('	<td><input type="text" maxlength="11" onkeyup="checkLowfreqImputDecimal (this)" onchange = "chkLowfreqChange(this.value,\'baseBet\','+i+');" value="'+ Math.floor(checkNull(info[i].baseBet)*100)/100 +'"></td>')	;
		str.push('	<td><input type="text" maxlength="11" onkeyup="checkLowfreqImputDecimal (this)" onchange = "chkLowfreqChange(this.value,\'baseline\','+i+');" value="'+ Math.floor(checkNull(info[i].baseline)*100)/100 +'"></td>')	;
		
		str.push('	<td><a href="javascript:void(0);" onclick="openLowfreqTextSetDiv('+i+');">描述修改</a></td>')	;
		
		str.push('</tr>');
	}
	str.push('	</tbody>');
	str.push('</table>');
	
	getEle('showMark6AmountPage').innerHTML = str.join('');
}

function showMark6NowAmountPage(){
	getEle('lotteryLowfreqAmountSetDetail').style.display = 'none';
	getEle('lotteryMark6SetDetail').style.display = 'block';
	
	var info = lotteryLowfreqAmountSetInfo;
	var str = [];
	
	str.push('<table>');
	str.push('	<tbody>');
	str.push('		<tr><th>玩法</th><th>最低投注金額</th><th>基礎賠率</th><th>當前累積投注金額</th><th>當前累積注單量</th><th>當前賠率</th></tr>');
	for(var i = 0 ; i < info.length ; i++){
		str.push('<tr>');
		str.push('	<td>' + info[i].prizeName + '</td>');
		str.push('	<td>' + Math.floor(checkNull(info[i].baseBet)*100)/100+' </td>');
		str.push('	<td>' + Math.floor(checkNull(info[i].baseline)*100)/100 + '</td>');
		str.push('	<td>' + Math.floor(checkNull(info[i].totalBetAmount)*100)/100 + '</td>');
		str.push('	<td>' + Math.floor(checkNull(info[i].totalNoOfBet)*100)/100 + '</td>');
		str.push('	<td><input type="text" maxlength="11" onkeyup="checkLowfreqImputDecimal (this)" onchange = "chkLowfreqChange(this.value,\'nowBaseline\','+i+');" value="' + Math.floor(checkNull(info[i].nowBaseline)*100)/100 + '"></td>');
		str.push('</tr>');
	}
	str.push('	</tbody>');
	str.push('</table>');
	str.push('</div>');
	
	getEle('showMark6AmountPage').innerHTML = str.join('');
}

function openLowfreqTextSetDiv(cou){
	var deta = lotteryLowfreqAmountSetInfo[cou];
	
	var str = [];
	
	str.push('<div class="modal-content modal-central width-percent-460 moneysetting">');
	str.push('	<span class="close" onclick="onClickCloseModal();">×</span>');
	str.push('	<h3>修改信息</h3>');
	str.push('	<div><span>玩法介紹：</span><textarea id="lowfreqPlayedText" onchange = "chkLowfreqChange(this.value,\'playedText\','+cou+');">'+ checkNull(deta.playedText) +'</textarea></div>');
	str.push('	<div><span>選號規則：</span><textarea id="lowfreqLotteryRule" onchange = "chkLowfreqChange(this.value,\'lotteryRule\','+cou+');">'+ checkNull(deta.lotteryRule) +'</textarea></div>');
	str.push('	<div><span>中獎說明：</span><textarea id="lowfreqLotteryExample" onchange = "chkLowfreqChange(this.value,\'lotteryExample\','+cou+');">'+ checkNull(deta.lotteryExample) +'</textarea></div>');
	str.push('	<div class="btn-area">');
	str.push('		<button onclick="onClickCloseModal();">取消</button>');
	str.push('		<button onclick="conformLowfreqDescription('+deta.minId+','+cou+');">修改</button>');
	str.push('	</div>');
	
	onClickOpenModal(str.join(''));
}
/* showAmountTable結束 */

/* check tool開始 */
function chkLowfreqChange(val,name,cou){
	if(!isNull(val) && !isNull(name) && !isNull(cou) && val != '' && name != '' && cou >= 0){
		lowfreqNewList[cou][name] = val;
	}
}

function setLowfreqDtSwitch(val,cou){
	if(!isNull(val) && !isNull(cou) && val != '' && cou >= 0){
		if(val.checked){
			val.parentNode.children[1].readOnly = '';
			val.parentNode.children[2].readOnly = '';
			val.parentNode.classList.remove('canttype');
			lowfreqNewList[cou]['dtSwitch'] = true;
		}else{
			val.parentNode.children[1].readOnly = 'readonly';
			val.parentNode.children[2].readOnly = 'readonly';
			val.parentNode.classList.add('canttype');
			lowfreqNewList[cou]['dtSwitch'] = false;
		}
	}
}

function conformLowfreqHandicap(handicapId){
	var handicapLog = new Object();
	var cou = parseInt(handicapId)-1;
	var todo = false;
	
	if(!isNull(lowfreqHandicapOldList[cou].bonusSetMin) && !isNull(getEle('lowfreqBonusSetMin').value)){
		if(lowfreqHandicapOldList[cou].bonusSetMin != getEle('lowfreqBonusSetMin').value){
			handicapLog.min = getEle('lowfreqBonusSetMin').value;
			todo = true;
		}else{
			handicapLog.min = '--';
		}
	}
	if(!isNull(lowfreqHandicapOldList[cou].bonusSetMax) && !isNull(getEle('lowfreqBonusSetMax').value)){
		if(lowfreqHandicapOldList[cou].bonusSetMax != getEle('lowfreqBonusSetMax').value){
			handicapLog.max = getEle('lowfreqBonusSetMax').value;
			todo = true;
		}else{
			handicapLog.max = '--';
		}
	}
	if(!isNull(lowfreqHandicapOldList[cou].maxWinBonus) && !isNull(getEle('lowfreqMaxWinBonus').value)){
		if(lowfreqHandicapOldList[cou].maxWinBonus != getEle('lowfreqMaxWinBonus').value){
			handicapLog.maxWin = getEle('lowfreqMaxWinBonus').value;
			todo = true;
		}else{
			handicapLog.maxWin = '--';
		}
	}
	if(!isNull(handicapId)){
		handicapLog.id = handicapId;
	}
	
	var str = '';
	
	if(todo){
		str = '&handicapId='+handicapId+'&bonusSetMin='+getEle('lowfreqBonusSetMin').value+'&bonusSetMax='+getEle('lowfreqBonusSetMax').value+'&maxWinBonus='+getEle('lowfreqMaxWinBonus').value+'&log='+encodeURIComponent(JSON.stringify(handicapLog));
		lotteryOnCheckModelPage('確定送出', 'upadteLowfreqHandicapAjax(\'' + str + '\')');
	}
}

function conformLowfreqDescription(minId,cou){
	var descriptionLog = new Object();
	var todo = false;
	
	if(!isNull(lowfreqOldList[cou].playedText) && !isNull(getEle('lowfreqPlayedText').value)){
		if(lowfreqOldList[cou].playedText != getEle('lowfreqPlayedText').value){
			descriptionLog.text = getEle('lowfreqPlayedText').value;
			todo = true;
		}else{
			descriptionLog.text = '--';
		}
	}
	if(!isNull(lowfreqOldList[cou].lotteryRule) && !isNull(getEle('lowfreqLotteryRule').value)){
		if(lowfreqOldList[cou].lotteryRule != getEle('lowfreqLotteryRule').value){
			descriptionLog.rule = getEle('lowfreqLotteryRule').value;
			todo = true;
		}else{
			descriptionLog.rule = '--';
		}
	}
	if(!isNull(lowfreqOldList[cou].lotteryExample) && !isNull(getEle('lowfreqLotteryExample').value)){
		if(lowfreqOldList[cou].lotteryExample != getEle('lowfreqLotteryExample').value){
			descriptionLog.exa = getEle('lowfreqLotteryExample').value;
			todo = true;
		}else{
			descriptionLog.exa = '--';
		}
	}
	if(!isNull(minId)){
		descriptionLog.minId = minId;
	}
	
	var str = '';
	
	if(todo){
		str = '&minId='+minId+
		'&playedText='+encodeURIComponent(getEle('lowfreqPlayedText').value)+
		'&lotteryRule='+encodeURIComponent(getEle('lowfreqLotteryRule').value)+
		'&lotteryExample='+encodeURIComponent(getEle('lowfreqLotteryExample').value)+
		'&log='+encodeURIComponent(JSON.stringify(descriptionLog));
		lotteryOnCheckModelPage('確定送出', 'upadteLowfreqDescriptionAjax(\'' + str + '\')');
	}
}

function compareLowfreqAmount() {
	var baselineType = getEle('lotteryLowfreqBaselineType').value;
	var updateList = new Array();
	var logList = new Array();
	var logObj = new Object();
	var todo = false;
	var str = '';

	if(baselineType == LOTTERY_LOWFREQ_BASE_BASELINE){
		for(var i=0;i<lowfreqOldList.length;i++){
			logObj = new Object();
			todo = false;
				
			if(lowfreqOldList[i].baseBet != lowfreqNewList[i].baseBet){
				logObj.baseBet = lowfreqNewList[i].baseBet;
				todo = true;
			}else{
				logObj.baseBet = '--';
			}
			if(lowfreqOldList[i].baseline != lowfreqNewList[i].baseline){
				logObj.baseline = lowfreqNewList[i].baseline;
				todo = true;
			}else{
				logObj.baseline = '--';
			}
			if(lowfreqOldList[i].dtBonus != lowfreqNewList[i].dtBonus){
				logObj.dtBonus = lowfreqNewList[i].dtBonus;
				todo = true;
			}else{
				logObj.dtBonus = '--';
			}
			if(lowfreqOldList[i].dtRatio != lowfreqNewList[i].dtRatio){
				logObj.dtRatio = lowfreqNewList[i].dtRatio;
				todo = true;
			}else{
				logObj.dtRatio = '--';
			}
			if(lowfreqOldList[i].dtSwitch != lowfreqNewList[i].dtSwitch){
				logObj.dtSwitch = lowfreqNewList[i].dtSwitch;
				todo = true;
			}else{
				logObj.dtSwitch = '--';
			}
			if(todo){
				updateList.push(lowfreqNewList[i]);
				
				logObj.handicap = lowfreqNewList[i].handicap;
				logObj.midId = lowfreqNewList[i].midId;
				logObj.minId = lowfreqNewList[i].minId;
				logObj.prizeLevel = lowfreqNewList[i].prizeLevel;
				
				logList.push(logObj);
			}
		}
		if(updateList.length > 0){
			str = '&updateList=' + encodeURIComponent(JSON.stringify(updateList)) + '&logList='+encodeURIComponent(JSON.stringify(logList));
			lotteryOnCheckModelPage('確定送出', 'updateLowfreqBaseAmountAjax(\'' + str + '\')');
		}
		
	}else if(baselineType == LOTTERY_LOWFREQ_NOW_BASELINE){
		for(var i=0;i<lowfreqOldList.length;i++){
			logObj = new Object();
			
			if(lowfreqOldList[i].nowBaseline != lowfreqNewList[i].nowBaseline){
				updateList.push(lowfreqNewList[i]);
				
				logObj.nowBaseline = lowfreqNewList[i].nowBaseline;
				logObj.handicap = lowfreqNewList[i].handicap;
				logObj.midId = lowfreqNewList[i].midId;
				logObj.minId = lowfreqNewList[i].minId;
				logObj.prizeLevel = lowfreqNewList[i].prizeLevel;
				
				logList.push(logObj);
			}
		}
		if(updateList.length > 0){
			str = '&updateList='+encodeURIComponent(JSON.stringify(updateList)) + '&logList='+encodeURIComponent(JSON.stringify(logList));
			lotteryOnCheckModelPage('確定送出', 'updateLowfreqNowAmountAjax(\'' + str + '\')');
		}
	}
}

function compareMark6Amount() {
	var baselineType = getEle('lotteryLowfreqBaselineType').value;
	var updateList = new Array();
	var logList = new Array();
	var logObj = new Object();
	var todo = false;
	var str = '';

	if(baselineType == LOTTERY_LOWFREQ_BASE_BASELINE){
		for(var i=0;i<lowfreqOldList.length;i++){
			logObj = new Object();
			todo = false;
				
			if(lowfreqOldList[i].baseBet != lowfreqNewList[i].baseBet){
				logObj.baseBet = lowfreqNewList[i].baseBet;
				todo = true;
			}else{
				logObj.baseBet = '--';
			}
			if(lowfreqOldList[i].baseline != lowfreqNewList[i].baseline){
				logObj.baseline = lowfreqNewList[i].baseline;
				todo = true;
			}else{
				logObj.baseline = '--';
			}
			if(todo){
				updateList.push(lowfreqNewList[i]);
				
				logObj.handicap = lowfreqNewList[i].handicap;
				logObj.midId = lowfreqNewList[i].midId;
				logObj.minId = lowfreqNewList[i].minId;
				logObj.prizeLevel = lowfreqNewList[i].prizeLevel;
				
				logList.push(logObj);
			}
		}
		if(updateList.length > 0){
			str = '&updateList=' + encodeURIComponent(JSON.stringify(updateList)) + '&logList='+encodeURIComponent(JSON.stringify(logList));
			lotteryOnCheckModelPage('確定送出', 'upadteMark6BaseAmountAjax(\'' + str + '\')');
		}
	}else if(baselineType == LOTTERY_LOWFREQ_NOW_BASELINE){
		for(var i=0;i<lowfreqOldList.length;i++){
			logObj = new Object();
			
			if(lowfreqOldList[i].nowBaseline != lowfreqNewList[i].nowBaseline){
				updateList.push(lowfreqNewList[i]);
				
				logObj.nowBaseline = lowfreqNewList[i].nowBaseline;
				logObj.handicap = lowfreqNewList[i].handicap;
				logObj.midId = lowfreqNewList[i].midId;
				logObj.minId = lowfreqNewList[i].minId;
				logObj.prizeLevel = lowfreqNewList[i].prizeLevel;
				
				logList.push(logObj);
			}
		}
		if(updateList.length > 0){
			str = '&updateList='+encodeURIComponent(JSON.stringify(updateList)) + '&logList='+encodeURIComponent(JSON.stringify(logList));
			lotteryOnCheckModelPage('確定送出', 'upadteMark6NowAmountAjax(\'' + str + '\')');
		}
	}
}

function checkLowfreqImputDecimal (val) {
	val.value = checkInputDecimalVal(val.value);// 小數點後N位由checkDecimalVal:regExp設定
}

function checkLowfreqBonusSize(val) {
	var Bonus = checkInputNumberVal(val.value);
	if(Bonus >= 1000 && Bonus <= 9999){
		val.value = Bonus;
	}else{
		val.value = 1700;
	}
}
function checklowfreqMaxWinBonusSize(val) {
	var maxWinBonus = checkInputNumberVal(val.value);
	if(maxWinBonus >= 400000 && maxWinBonus <= 100000000){
		val.value = maxWinBonus;
	}else{
		val.value = 400000;
	}
}
/* check tool結束 */

/* update Ajax開始 */
function upadteLowfreqHandicapAjax(str) {
	var tmpStr = '';
	LotteryLowfreqAmount_XHR = checkXHR(LotteryLowfreqAmount_XHR);
	if (typeof LotteryLowfreqAmount_XHR != "undefined" && LotteryLowfreqAmount_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&accId=" + getLoginAccId() + str;
		LotteryLowfreqAmount_XHR.timeout = 10000;
		LotteryLowfreqAmount_XHR.ontimeout = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onerror = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onreadystatechange = handleUpadteLowfreqHandicapAjax;
		LotteryLowfreqAmount_XHR.open("POST", "/CTT03BetRatioMaster/UpdateLotteryLowfreqInfo!upadteHandicap.php?date=" + getNewTime(), true);
		LotteryLowfreqAmount_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LotteryLowfreqAmount_XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handleUpadteLowfreqHandicapAjax() {
	if (LotteryLowfreqAmount_XHR.readyState == 4) {
		if (LotteryLowfreqAmount_XHR.status == 200) {
			try {
				if (isJSON(LotteryLowfreqAmount_XHR.responseText)) {
					var json = JSON.parse(LotteryLowfreqAmount_XHR.responseText);
					if (checkTokenIdfail(json)) {
						if(json.success){
							onCheckModelPage2('修改成功!');
						}else{
							onCheckModelPage2('失敗!');
						}
					}
				}
			} catch (error) {
				console_Log("handleUpadteLowfreqHandicapAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LotteryLowfreqAmount_XHR.abort();
				if(json.success){
					conformLowfreqAmountInfo();
				}
			}
		} else {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
	}
}

function upadteLowfreqDescriptionAjax(str) {
	var tmpStr = '';
	LotteryLowfreqAmount_XHR = checkXHR(LotteryLowfreqAmount_XHR);
	if (typeof LotteryLowfreqAmount_XHR != "undefined" && LotteryLowfreqAmount_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&accId=" + getLoginAccId() + str;
		LotteryLowfreqAmount_XHR.timeout = 10000;
		LotteryLowfreqAmount_XHR.ontimeout = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onerror = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onreadystatechange = handleUpadteLowfreqDescriptionAjax;
		LotteryLowfreqAmount_XHR.open("POST", "/CTT03BetRatioMaster/UpdateLotteryLowfreqInfo!upadteDescription.php?date=" + getNewTime(), true);
		LotteryLowfreqAmount_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LotteryLowfreqAmount_XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handleUpadteLowfreqDescriptionAjax() {
	if (LotteryLowfreqAmount_XHR.readyState == 4) {
		if (LotteryLowfreqAmount_XHR.status == 200) {
			try {
				if (isJSON(LotteryLowfreqAmount_XHR.responseText)) {
					var json = JSON.parse(LotteryLowfreqAmount_XHR.responseText);
					if (checkTokenIdfail(json)) {
						if(json.success){
							onClickCloseModal();
							onCheckModelPage2('修改成功!');
						}else{
							onCheckModelPage2('失敗!');
						}
					}
				}
			} catch (error) {
				console_Log("handleUpadteLowfreqDescriptionAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LotteryLowfreqAmount_XHR.abort();
				if(json.success){
					conformLowfreqAmountInfo();
				}
			}
		} else {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
	}
}

function updateLowfreqBaseAmountAjax(str) {
	var tmpStr = '';
	LotteryLowfreqAmount_XHR = checkXHR(LotteryLowfreqAmount_XHR);
	if (typeof LotteryLowfreqAmount_XHR != "undefined" && LotteryLowfreqAmount_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&accId=" + getLoginAccId() + str;
		LotteryLowfreqAmount_XHR.timeout = 10000;
		LotteryLowfreqAmount_XHR.ontimeout = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onerror = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onreadystatechange = handleUpdateLowfreqBaseAmountAjax;
		LotteryLowfreqAmount_XHR.open("POST", "/CTT03BetRatioMaster/UpdateLotteryLowfreqInfo!updateBaseAmount.php?date=" + getNewTime(), true);
		LotteryLowfreqAmount_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LotteryLowfreqAmount_XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handleUpdateLowfreqBaseAmountAjax() {
	if (LotteryLowfreqAmount_XHR.readyState == 4) {
		if (LotteryLowfreqAmount_XHR.status == 200) {
			try {
				if (isJSON(LotteryLowfreqAmount_XHR.responseText)) {
					var json = JSON.parse(LotteryLowfreqAmount_XHR.responseText);
					if (checkTokenIdfail(json)) {
						if(json.success){
							onCheckModelPage2('修改成功!');
						}else{
							onCheckModelPage2('失敗!');
						}
					}
				}
			} catch (error) {
				console_Log("handleUpdateLowfreqBaseAmountAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LotteryLowfreqAmount_XHR.abort();
				if(json.success){
					conformLowfreqAmountInfo();
				}
			}
		} else {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
	}
}

function updateLowfreqNowAmountAjax(str) {
	var tmpStr = '';
	LotteryLowfreqAmount_XHR = checkXHR(LotteryLowfreqAmount_XHR);
	if (typeof LotteryLowfreqAmount_XHR != "undefined" && LotteryLowfreqAmount_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&accId=" + getLoginAccId() + str;
		LotteryLowfreqAmount_XHR.timeout = 10000;
		LotteryLowfreqAmount_XHR.ontimeout = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onerror = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onreadystatechange = handleUpdateLowfreqNowAmountAjax;
		LotteryLowfreqAmount_XHR.open("POST", "/CTT03BetRatioMaster/UpdateLotteryLowfreqInfo!updateNowAmount.php?date=" + getNewTime(), true);
		LotteryLowfreqAmount_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LotteryLowfreqAmount_XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handleUpdateLowfreqNowAmountAjax() {
	if (LotteryLowfreqAmount_XHR.readyState == 4) {
		if (LotteryLowfreqAmount_XHR.status == 200) {
			try {
				if (isJSON(LotteryLowfreqAmount_XHR.responseText)) {
					var json = JSON.parse(LotteryLowfreqAmount_XHR.responseText);
					if (checkTokenIdfail(json)) {
						if(json.success){
							onCheckModelPage2('修改成功!');
						}else{
							onCheckModelPage2('失敗!');
						}
					}
				}
			} catch (error) {
				console_Log("handleUpdateLowfreqNowAmountAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LotteryLowfreqAmount_XHR.abort();
				if(json.success){
					conformLowfreqAmountInfo();
				}
			}
		} else {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
	}
}

function upadteMark6BaseAmountAjax(str) {
	var tmpStr = '';
	LotteryLowfreqAmount_XHR = checkXHR(LotteryLowfreqAmount_XHR);
	if (typeof LotteryLowfreqAmount_XHR != "undefined" && LotteryLowfreqAmount_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&accId=" + getLoginAccId() + str;
		LotteryLowfreqAmount_XHR.timeout = 10000;
		LotteryLowfreqAmount_XHR.ontimeout = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onerror = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onreadystatechange = handleUpadteMark6BaseAmountAjax;
		LotteryLowfreqAmount_XHR.open("POST", "/CTT03BetRatioMaster/UpdateLotteryLowfreqInfo!updateMark6BaseAmount.php?date=" + getNewTime(), true);
		LotteryLowfreqAmount_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LotteryLowfreqAmount_XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handleUpadteMark6BaseAmountAjax() {
	if (LotteryLowfreqAmount_XHR.readyState == 4) {
		if (LotteryLowfreqAmount_XHR.status == 200) {
			try {
				if (isJSON(LotteryLowfreqAmount_XHR.responseText)) {
					var json = JSON.parse(LotteryLowfreqAmount_XHR.responseText);
					if (checkTokenIdfail(json)) {
						if(json.success){
							onCheckModelPage2('修改成功!');
						}else{
							onCheckModelPage2('失敗!');
						}
					}
				}
			} catch (error) {
				console_Log("handleUpadteMark6BaseAmountAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LotteryLowfreqAmount_XHR.abort();
				if(json.success){
					conformLowfreqAmountInfo();
				}
			}
		} else {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
	}
}

function upadteMark6NowAmountAjax(str) {
	var tmpStr = '';
	LotteryLowfreqAmount_XHR = checkXHR(LotteryLowfreqAmount_XHR);
	if (typeof LotteryLowfreqAmount_XHR != "undefined" && LotteryLowfreqAmount_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&accId=" + getLoginAccId() + str;
		LotteryLowfreqAmount_XHR.timeout = 10000;
		LotteryLowfreqAmount_XHR.ontimeout = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onerror = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onreadystatechange = handleUpadteMark6NowAmountAjax;
		LotteryLowfreqAmount_XHR.open("POST", "/CTT03BetRatioMaster/UpdateLotteryLowfreqInfo!updateMark6NowAmount.php?date=" + getNewTime(), true);
		LotteryLowfreqAmount_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LotteryLowfreqAmount_XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handleUpadteMark6NowAmountAjax() {
	if (LotteryLowfreqAmount_XHR.readyState == 4) {
		if (LotteryLowfreqAmount_XHR.status == 200) {
			try {
				if (isJSON(LotteryLowfreqAmount_XHR.responseText)) {
					var json = JSON.parse(LotteryLowfreqAmount_XHR.responseText);
					if (checkTokenIdfail(json)) {
						if(json.success){
							onCheckModelPage2('修改成功!');
						}else{
							onCheckModelPage2('失敗!');
						}
					}
				}
			} catch (error) {
				console_Log("handleUpadteMark6NowAmountAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LotteryLowfreqAmount_XHR.abort();
				if(json.success){
					conformLowfreqAmountInfo();
				}
			}
		} else {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
	}
}
/* update Ajax結束 */

/* log開始 */
function getLowfreqAmountSettingLogAjax() {
	var tmStr = '';
	LotteryLowfreqAmount_XHR = checkXHR(LotteryLowfreqAmount_XHR);
	if (typeof LotteryLowfreqAmount_XHR != "undefined" && LotteryLowfreqAmount_XHR != null) {
		tmStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&accId=" + getLoginAccId();
		LotteryLowfreqAmount_XHR.timeout = 10000;
		LotteryLowfreqAmount_XHR.ontimeout = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onerror = function() {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
		LotteryLowfreqAmount_XHR.onreadystatechange = handleGetLowfreqAmountSettingLogAjax;
		LotteryLowfreqAmount_XHR.open("POST", "/CTT03BetRatioMaster/GetLotteryLowfreqInfo!getLowfreqAmountSettingLog.php?date=" + getNewTime(), true);
		LotteryLowfreqAmount_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		LotteryLowfreqAmount_XHR.send(tmStr);
		enableLoadingPage();
	}
}

function handleGetLowfreqAmountSettingLogAjax() {
	if (LotteryLowfreqAmount_XHR.readyState == 4) {
		if (LotteryLowfreqAmount_XHR.status == 200) {
			try {
				if (isJSON(LotteryLowfreqAmount_XHR.responseText)) {
					var tmpJ = isJSON2(LotteryLowfreqAmount_XHR.responseText);
					if (tmpJ) {
						if (checkTokenIdfail(tmpJ)) {
							if(!isNull(tmpJ.log)){
								lowfreqSettingLog = tmpJ.log;
							}else{
								lowfreqSettingLog = '';
							}
							showLowfreqLogPage();
						}
					}
				}
			} catch (error) {
				lowfreqSettingLog = '';
				console_Log("handleGetLowfreqAmountSettingLogAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				LotteryLowfreqAmount_XHR.abort();
			}
		} else {
			disableLoadingPage();
			LotteryLowfreqAmount_XHR.abort();
		}
	}
}

function showLowfreqLogPage(){
	var logStr=[];
	
	logStr.push('<div class="modal-content width-percent-960 margin-fix-4">');
	logStr.push('	<span class="close" onclick="onClickCloseModal();">×</span>');
	logStr.push('	<h3>低頻彩設定紀錄表</h3>');
	logStr.push('	<div class="tab-area">');
	logStr.push('		<button onclick="onclickLowfreqLogTopBtn(0);">盤口設定紀錄</button>\n');
	logStr.push('		<button onclick="onclickLowfreqLogTopBtn(1);">描述修改紀錄</button>\n');
	logStr.push('		<button onclick="onclickLowfreqLogTopBtn(2);">基礎賠率設定紀錄</button>\n');
	logStr.push('		<button onclick="onclickLowfreqLogTopBtn(3);">即時賠率設定紀錄</button>\n');
	logStr.push('	</div>');
	logStr.push('	<div class="tab-content">');
	logStr.push('		<table class="table-zebra tr-hover" id="lowfreqLogTable">');
	logStr.push('			<tbody>');
	logStr.push('			</tbody>');
	logStr.push('		</table>');
	logStr.push('		<p class="media-control text-center">');
	logStr.push('			<a href="javascript:void(0);" onclick="onClickFirstPage();" class="backward"></a>');
	logStr.push('			<a href="javascript:void(0);" onclick="onClickPreviousPage();" class="backward-fast"></a>');
	logStr.push('			<span>總頁數：<i id= "nowLowfreqLogPage">1</i><span>/</span><i id = "nowLowfreqLogMaxPage">1</i>頁</span>');
	logStr.push('			<a href="javascript:void(0);" onclick="onClickNextPage();" class="forward"></a>');
	logStr.push('			<a href="javascript:void(0);" onclick="onClickLastPage();" class="forward-fast"></a>');
	logStr.push('		</p>');
	logStr.push('	</div>');
	logStr.push('</div>');
	
	onClickOpenModal(logStr.join(''));
	onclickLowfreqLogTopBtn(0);
}

function onclickLowfreqLogTopBtn(num){
	var allBtn = document.getElementsByClassName('tab-area')[0].getElementsByTagName('button');
	for(var i = 0 ; i < allBtn.length ; i++){
		removeClass(allBtn[i], 'active');
	}
	addClass(allBtn[num],'active');
	getEle('nowLowfreqLogType').value = num;
	
	var logInfo = getLowfreqEachLogInfo(num);
	
	if(num == LOTTERY_LOWFREQ_LOG_TYPE_HANDICAP){
		changePage_init(onclickLowfreqHandicapLogBtn,logInfo.length,1,10,"nowLowfreqLogPage","nowLowfreqLogMaxPage");
	}else if(num == LOTTERY_LOWFREQ_LOG_TYPE_DESCRIPTION){
		changePage_init(onclickLowfreqDescriptionLogBtn,logInfo.length,1,10,"nowLowfreqLogPage","nowLowfreqLogMaxPage");
	}else if(num == LOTTERY_LOWFREQ_LOG_TYPE_BASE_BASELINE){
		changePage_init(onclickLowfreqBaseBaselineLogBtn,logInfo.length,1,10,"nowLowfreqLogPage","nowLowfreqLogMaxPage");
	}else if(num == LOTTERY_LOWFREQ_LOG_TYPE_NOW_BASELINE){
		changePage_init(onclickLowfreqNowBaselineLogBtn,logInfo.length,1,10,"nowLowfreqLogPage","nowLowfreqLogMaxPage");
	}
	
}

function getLowfreqEachLogInfo(num){
	var newInfoList = [];
	
	if(num == LOTTERY_LOWFREQ_LOG_TYPE_HANDICAP){
		for(var c0 = 0 ; c0 < lowfreqSettingLog.length ; c0++){
			if(!isNull(lowfreqSettingLog[c0].action)){
				if(lowfreqSettingLog[c0].action == LOG_ACTION_LOTTERY_LOWFREQ_HANDICAP_SET){
					newInfoList.push(lowfreqSettingLog[c0]);
				}
			}
		}
	}else if(num == LOTTERY_LOWFREQ_LOG_TYPE_DESCRIPTION){
		for(var c1 = 0 ; c1 < lowfreqSettingLog.length ; c1++){
			if(!isNull(lowfreqSettingLog[c1].action)){
				if(lowfreqSettingLog[c1].action == LOG_ACTION_LOTTERY_LOWFREQ_DESCRIPTION_SET){
					newInfoList.push(lowfreqSettingLog[c1]);
				}
			}
		}
	}else if(num == LOTTERY_LOWFREQ_LOG_TYPE_BASE_BASELINE){
		for(var c2 = 0 ; c2 < lowfreqSettingLog.length ; c2++){
			if(!isNull(lowfreqSettingLog[c2].action)){
				if(lowfreqSettingLog[c2].action >= LOG_ACTION_LOTTERY_LOWFREQ_FC3D_BASE_BASELINE_SET && lowfreqSettingLog[c2].action <= LOG_ACTION_LOTTERY_LOWFREQ_LHC_BASE_BASELINE_SET){
					newInfoList.push(lowfreqSettingLog[c2]);
				}
			}
		}
	}else if(num == LOTTERY_LOWFREQ_LOG_TYPE_NOW_BASELINE){
		for(var c3 = 0 ; c3 < lowfreqSettingLog.length ; c3++){
			if(!isNull(lowfreqSettingLog[c3].action)){
				if(lowfreqSettingLog[c3].action >= LOG_ACTION_LOTTERY_LOWFREQ_FC3D_NOW_BASELINE_SET && lowfreqSettingLog[c3].action <= LOG_ACTION_LOTTERY_LOWFREQ_LHC_NOW_BASELINE_SET){
					newInfoList.push(lowfreqSettingLog[c3]);
				}
			}
		}
	}
	
	return newInfoList;
}

function onclickLowfreqHandicapLogBtn(){
	var logInfo = getLowfreqEachLogInfo(0);
	var logDetail = '';
	var handiCapName = [ "", "A盤", "B盤", "C盤", "D盤", "E盤" ];
	var logTableStr=[];
	
	logTableStr.push('<tr><th>操作者</th><th>盤口</th><th>獎金組最低金額</th><th>獎金組最高金額</th><th>最高中獎金額</th><th>操作時間</th><th>操作IP</th></tr>');
	for(var cou = parseInt(getEle('startCount').value);cou < parseInt(getEle('endCount').value);cou++){
		logDetail = checkNull(logInfo[cou].detail);
		if(isJSON(logDetail)){
			logDetail = JSON.parse(logDetail);
		}
		logTableStr.push('<tr>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].opsAccName)+'</td>');
		logTableStr.push('	<td>'+handiCapName[checkNull(logDetail.id)]+'</td>');
		logTableStr.push('	<td>'+checkNull(logDetail.min)+'</td>');
		logTableStr.push('	<td>'+checkNull(logDetail.max)+'</td>');
		logTableStr.push('	<td>'+checkNull(logDetail.maxWin)+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].opsDatetime)+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].ip)+'</td>');
		logTableStr.push('</tr>');
	}
	getEle('lowfreqLogTable').innerHTML = logTableStr.join('');
}

function onclickLowfreqDescriptionLogBtn(){
	var logInfo = getLowfreqEachLogInfo(1);
	var logDetail = '';
	var logTableStr=[];
	
	logTableStr.push('<tr><th>操作者</th><th>玩法</th><th>操作時間</th><th>操作IP</th><th>內容</th></tr>');
	for(var cou = parseInt(getEle('startCount').value);cou < parseInt(getEle('endCount').value);cou++){
		logDetail = checkNull(logInfo[cou].detail);
		if(isJSON(logDetail)){
			logDetail = JSON.parse(logDetail);
		}
		logTableStr.push('<tr>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].opsAccName)+'</td>');
		logTableStr.push('	<td>'+showNameByBaseList(checkNull(logDetail.minId))+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].opsDatetime)+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].ip)+'</td>');
		logTableStr.push('	<td><a href="javascript:void(0);" onclick="showLotteryDescriptionDetailLog(' + cou + ');">詳細</a></td>');
		logTableStr.push('</tr>');
	}
	getEle('lowfreqLogTable').innerHTML = logTableStr.join('');
}

function onclickLowfreqBaseBaselineLogBtn(){
	var logInfo = getLowfreqEachLogInfo(2);
	var logTableStr=[];
	
	logTableStr.push('<tr><th>操作者</th><th>操作項目</th><th>操作時間</th><th>操作IP</th><th>內容</th></tr>');
	for(var cou = parseInt(getEle('startCount').value);cou < parseInt(getEle('endCount').value);cou++){
		logTableStr.push('<tr>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].opsAccName)+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].actionText)+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].opsDatetime)+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].ip)+'</td>');
		logTableStr.push('	<td><a href="javascript:void(0);" onclick="showLowfreqBaseBaselineDetailLog(\''+ checkNull(logInfo[cou].actionText)+'\',\''+ cou + '\');">詳細</a></td>');
		logTableStr.push('</tr>');
	}
	getEle('lowfreqLogTable').innerHTML = logTableStr.join('');
}

function onclickLowfreqNowBaselineLogBtn(){
	var logInfo = getLowfreqEachLogInfo(3);
	var logTableStr=[];
	
	logTableStr.push('<tr><th>操作者</th><th>操作項目</th><th>操作時間</th><th>操作IP</th><th>內容</th></tr>');
	for(var cou = parseInt(getEle('startCount').value);cou < parseInt(getEle('endCount').value);cou++){
		logTableStr.push('<tr>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].opsAccName)+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].actionText)+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].opsDatetime)+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].ip)+'</td>');
		logTableStr.push('	<td><a href="javascript:void(0);" onclick="showLowfreqNowBaselineDetailLog(\''+ checkNull(logInfo[cou].actionText)+'\',\''+ cou + '\');">詳細</a></td>');
		logTableStr.push('</tr>');
	}
	getEle('lowfreqLogTable').innerHTML = logTableStr.join('');
}

function showLowfreqBaseBaselineDetailLog(title,cou){
	var logInfo = getLowfreqEachLogInfo(2);
	var logDetail = '';
	var logDetailStr=[];
	var handiCapName = [ "", "A盤", "B盤", "C盤", "D盤", "E盤" ];
	
	logDetail = checkNull(logInfo[cou].detail);
	if(isJSON(logDetail)){
		logDetail = JSON.parse(logDetail);
	}
	
	logDetailStr.push('<div class="modal-content width-percent-60">');
	logDetailStr.push('	<span class="close" onclick="onClickCloseModalV2();">×</span>');
	logDetailStr.push('	<h3>'+title+'</h3>');
	logDetailStr.push('	<div class="tab-content">');
	logDetailStr.push('		<table class="table-zebra tr-hover">');
	logDetailStr.push('			<tbody>');
	logDetailStr.push('				<tr><th>盤口</th><th>玩法</th><th>獎等</th><th>內容</th></tr>');
	for(var i = 0 ; i < logDetail.length ; i++){
		logDetailStr.push('<tr>');
		logDetailStr.push('	<td>'+handiCapName[checkNull(logDetail[i].handicap)]+'</td>');
		logDetailStr.push('	<td>'+showNameByBaseList(checkNull(logDetail[i].minId))+'</td>');
		logDetailStr.push('	<td>'+(checkNull(logDetail[i].prizeLevel) == 0 ? '--' : logDetail[i].prizeLevel)+'</td>');
		logDetailStr.push('	<td>'+showLowfreqLogDetailDetail(logDetail[i])+'</td>');
		logDetailStr.push('</tr>');
	}
	logDetailStr.push('			</tbody>');
	logDetailStr.push('		</table>');
	logDetailStr.push('	</div>');
	logDetailStr.push('</div>');
	
	onClickOpenModalV2(logDetailStr.join(''));	
}
		
function showLowfreqNowBaselineDetailLog(title,cou){
	var logInfo = getLowfreqEachLogInfo(3);
	var logDetail = '';
	var logDetailStr=[];
	var handiCapName = [ "", "A盤", "B盤", "C盤", "D盤", "E盤" ];
	
	logDetail = checkNull(logInfo[cou].detail);
	if(isJSON(logDetail)){
		logDetail = JSON.parse(logDetail);
	}
	
	logDetailStr.push('<div class="modal-content width-percent-60">');
	logDetailStr.push('	<span class="close" onclick="onClickCloseModalV2();">×</span>');
	logDetailStr.push('	<h3>'+title+'</h3>');
	logDetailStr.push('	<div class="tab-content">');
	logDetailStr.push('		<table class="table-zebra tr-hover">');
	logDetailStr.push('			<tbody>');
	logDetailStr.push('				<tr><th>盤口</th><th>玩法</th><th>獎等</th><th>內容</th></tr>');
	for(var i = 0 ; i < logDetail.length ; i++){
		logDetailStr.push('<tr>');
		logDetailStr.push('	<td>'+handiCapName[checkNull(logDetail[i].handicap)]+'</td>');
		logDetailStr.push('	<td>'+showNameByBaseList(checkNull(logDetail[i].minId))+'</td>');
		logDetailStr.push('	<td>'+(checkNull(logDetail[i].prizeLevel) == 0 ? '--' : logDetail[i].prizeLevel)+'</td>');
		logDetailStr.push('	<td>'+showLowfreqLogDetailDetail(logDetail[i])+'</td>');
		logDetailStr.push('</tr>');
	}
	logDetailStr.push('			</tbody>');
	logDetailStr.push('		</table>');
	logDetailStr.push('	</div>');
	logDetailStr.push('</div>');
	
	onClickOpenModalV2(logDetailStr.join(''));	
}

function showLowfreqLogDetailDetail(data){
	var str = [];
	var key = ['baseBet','baseline','nowBaseline','dtSwitch','dtRatio','dtBonus'];
	const transKey = {'baseBet':'最低投注金額','baseline':'基礎賠率','nowBaseline':'當前賠率','dtSwitch':'單挑開關','dtRatio':'單挑%數','dtBonus':'單挑金額'};
	
	if(!isNull(data)){
		var ObjKey = Object.keys(data);
		for(var i = 0 ; i < key.length ; i++){
			for(var j = 0 ; j < ObjKey.length ; j++){
				if(ObjKey[j] == key[i]){
					if(ObjKey[j] == 'dtSwitch'){
						str.push('<span>'+transKey[ObjKey[j]]+':'+(data[ObjKey[j]] == true ? '開啟' : '關閉')+'</span>\n');
					}else{
						str.push('<span>'+transKey[ObjKey[j]]+':'+data[ObjKey[j]]+'</span>\n');
					}
				}
			}
		}
	}
	return str;
}

function showLotteryDescriptionDetailLog(cou){
	var logInfo = getLowfreqEachLogInfo(1);
	var logDetail = '';
	var logDetailStr=[];
	
	logDetail = checkNull(logInfo[cou].detail);
	if(isJSON(logDetail)){
		logDetail = JSON.parse(logDetail);
	}
	
	logDetailStr.push('<div class="modal-content width-percent-60">');
	logDetailStr.push('	<span class="close" onclick="onClickCloseModalV2();">×</span>');
	logDetailStr.push('	<h3>玩法:'+showNameByBaseList(checkNull(logDetail.minId))+'</h3>');
	logDetailStr.push('	<div class="tab-content">');
	logDetailStr.push('		<table class="table-zebra tr-hover">');
	logDetailStr.push('			<tbody>');
	logDetailStr.push('				<tr><th>玩法介紹</th><td>'+logDetail.text+'</td></tr>');
	logDetailStr.push('				<tr><th>選號規則</th><td>'+logDetail.rule+'</td></tr>');
	logDetailStr.push('				<tr><th>中獎說明</th><td>'+logDetail.exa+'</td></tr>');
	logDetailStr.push('			</tbody>');
	logDetailStr.push('		</table>');
	logDetailStr.push('	</div>');
	logDetailStr.push('</div>');
	
	onClickOpenModalV2(logDetailStr.join(''));
}

function showNameByBaseList(val){
	var midList = lotteryLowfreqBaseInfo.getLotteryMidList;
	var typeList = lotteryLowfreqBaseInfo.getLotteryAmountTypeList;
	
	if(!isNull(val)){
		for(var i = 0;i < midList.length;i++){
			if(!isNull(midList[i].midId) && !isNull(midList[i].typeId) && !isNull(midList[i].midName)){
				if(midList[i].midId == val){
					for(var t = 0;t < typeList.length;t++){
						if(!isNull(typeList[t].typeId) && !isNull(typeList[t].typeName)){
							if(midList[i].typeId == typeList[t].typeId){
								return typeList[t].typeName + ':' + midList[i].midName;
								break;
							}
						}
					}
				}
			}
		}
	}
	
	return'';
}

//function時間

var getStartTimeForData;
var getEndTimeForData;

function getTimeForData(){
	var st = new Date(getStartTimeForData);
	var et = new Date(getEndTimeForData);
	
	var sMs = st.getMilliseconds();
	var eMs = et.getMilliseconds();
	
	var diffT = new Date(eMs - sMs);
	
	console.log(diffT.getMilliseconds()+'  Millisec');
}
