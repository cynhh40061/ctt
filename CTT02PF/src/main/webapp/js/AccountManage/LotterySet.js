var Lottery_XHR = null;

var switchOldList = [];
var switchNewList = [];

var amountOldList = [];
var amountNewList = [];
var handicapOldList = [];
var descriptionOldList = [];

var getAmountInfo = null;

const LOTTERY_PRIZE_LEVEL = {
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

const LOTTERY_SET_JS = true;

function LotterySet_Amount(){
	setClassFix();
	LotterySet_into(1,1);
	lotteryAmountAjax();
}

function LotterySet_Switch(){
	setClassFix();
	LotterySet_into(1,1);
	lotterySwitchSetAjax();
}

function LotterySet_into(handicapPage,localPage){
	var str = '';
	if(document.getElementsByName("lotteryAmountInfo").length!=1){
		str += '\n<input type="hidden" name="lotteryUpperSwitchInfo" value="">';
		str += '\n<input type="hidden" name="lotterySwitchDetail" value="">';
		str += '\n<input type="hidden" name="lotterySwitchDetailTitle" value="">';
		str += '\n<input type="hidden" name="lotterySettingLog" value="">';
		str += '\n<input type="hidden" name="lotteryNowSettingSwitch" value="">';
		
		str += '\n<input type="hidden" name="lotteryAmountInfo" value="">';
		str += '\n<input type="hidden" name="handicapPage" value="'+handicapPage+'">';
		str += '\n<input type="hidden" name="localPage" value="'+localPage+'">';
		str += '\n<input type="hidden" name="switchList" value="">';
		str += '\n<input type="hidden" name="amountList" value="">';
		
		str += '\n<input type="hidden" name="pageCount" value="10">';
		str += '\n<input type="hidden" name="nowPage" value="1">';
		str += '\n<input type="hidden" name="totalPage" value="1">';
		str += '\n<input type="hidden" name="nowFirstCount" value="0">';
		str += '\n<input type="hidden" name="nowLastCount" value="0">';
		str += '\n<input type="hidden" name="totalCount" value="0">';
		str += '\n<input type="hidden" name="nowLotteryLogType" value="">';		
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


function lotterySwitchSetAjax() {
	var str = '';
	Lottery_XHR = checkXHR(Lottery_XHR);
	if (typeof Lottery_XHR != "undefined" && Lottery_XHR != null) {
		str = "tokenId=" + encodeURIComponent(getEle("tokenId").value);
		Lottery_XHR.timeout = 10000;
		Lottery_XHR.ontimeout = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onerror = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onreadystatechange = handleLotterySwitchSetAjax;
		Lottery_XHR.open("POST", "./LotterySet!getUpperSwitchInfo.php?date=" + getNewTime(), true);
		Lottery_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		Lottery_XHR.send(str);
		enableLoadingPage();
	}
}
function handleLotterySwitchSetAjax() {
	if (Lottery_XHR.readyState == 4) {
		if (Lottery_XHR.status == 200) {
			try {
				if (isJSON(Lottery_XHR.responseText)) {
					var json = JSON.parse(Lottery_XHR.responseText);
					if (checkTokenIdfail(json)) {
						getEle("lotteryUpperSwitchInfo").value = Lottery_XHR.responseText;
						showLotterySwitchSetPage();
					}
				}
			} catch (error) {
				console_Log("handLotterySwitchSetAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				Lottery_XHR.abort();
				if(!isNull(getEle('lotteryNowSettingSwitch').value) && isNumber(parseInt(getEle('lotteryNowSettingSwitch').value))){
					lotterySwitchDetailAjax(parseInt(getEle('lotteryNowSettingSwitch').value));
				}
			}
		} else {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
	}
}

function showLotterySwitchSetPage() {
	var str = [];
	var json = JSON.parse(getEle("lotteryUpperSwitchInfo").value);
	var jsonObject = '';
	var lotterySwitchTypeList = '';
	
	if (!isNull(json.getUpperSwitchInfo)) {
		jsonObject = json.getUpperSwitchInfo;
	}
	if (!isNull(json.getLotterySwitchTypeList)) {
		lotterySwitchTypeList = json.getLotterySwitchTypeList;
	}
	
	for(var a = 0 ; a < lotterySwitchTypeList.length ; a++){
		str.push('<div class="Div2-8-tablearea"> \n');
		if(a == 0){
			str.push('<h6 class="posfix">'+lotterySwitchTypeList[a].typeName+'<button class="btn-log-1" onclick="lotterySwitchSettingLogAjax();">操作紀錄</button></h6>');
		}else{
			str.push('<h6>'+lotterySwitchTypeList[a].typeName+'</h6>');
		}
		str.push('<table class="table-zebra tr-hover">');
		str.push('<tbody> \n');
		str.push('<tr><th>名稱</th><th>開關</th><th>操作</th></tr>');
		for(var b = 0 ; b < jsonObject.length ; b++){
			if(jsonObject[b].type == lotterySwitchTypeList[a].typeId && !isNull(jsonObject[b].title) && !isNull(jsonObject[b].switch)){
				str.push('<tr><td>'+jsonObject[b].title+'('+jsonObject[b].openSwitch+'/'+jsonObject[b].allSwitch+')');
				str.push(jsonObject[b].switch == 1 ? '<td><span>開</span></td>' : '<td><span class="red">關</span></td>');
				str.push('<td><a href="javascript:void(0);" id="lotterySwitchSetBtn" onclick="lotterySwitchDetailAjax('+b+');">修改</a></td></tr>');
			}
		}
		str.push('</tbody> \n');
		str.push('</table> \n');
		str.push('</div>');
	}
	getEle("mainContain").innerHTML = str.join("");
}
function lotterySwitchDetailAjax(val) {
	getEle('lotterySwitchDetailTitle').value = val;
	var str = '';
	Lottery_XHR = checkXHR(Lottery_XHR);
	if (typeof Lottery_XHR != "undefined" && Lottery_XHR != null) {
		str = "tokenId=" + encodeURIComponent(getEle("tokenId").value)+"&lotteryId="+(val+1);
		Lottery_XHR.timeout = 10000;
		Lottery_XHR.ontimeout = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onerror = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onreadystatechange = handleLotterySwitchDetailAjax;
		Lottery_XHR.open("POST", "./LotterySet!getSwitchDetail.php?date=" + getNewTime(), true);
		Lottery_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		Lottery_XHR.send(str);
		enableLoadingPage();
	}
}
function handleLotterySwitchDetailAjax() {
	if (Lottery_XHR.readyState == 4) {
		if (Lottery_XHR.status == 200) {
			try {
				if (isJSON(Lottery_XHR.responseText)) {
					var json = JSON.parse(Lottery_XHR.responseText);
					if (checkTokenIdfail(json)) {
						getEle("lotterySwitchDetail").value = Lottery_XHR.responseText;
						getEle("switchList").value = JSON.stringify(json.getSwitchDetail);
						switchOldList = JSON.parse(getEle("switchList").value);
						switchNewList = JSON.parse(getEle("switchList").value);
						showLotterySwitchDetail();
					}
				}
			} catch (error) {
				console_Log("handLotterySwitchDetailAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				Lottery_XHR.abort();
			}
		} else {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
	}
}
function showLotterySwitchDetail(){
	var json = JSON.parse(getEle("lotterySwitchDetail").value);
	var titleJson = JSON.parse(getEle("lotteryUpperSwitchInfo").value);
	var totalSwitch = '';
	var titleJsonObj = '';
	var titleId = getEle("lotterySwitchDetailTitle").value;
	var title = '';
	var data = '';
	var lotteryTitle = '';
	var titlePlayedId = '';
	var dataPlayedId = '';
	var dataMidId = '';
	var midTitle = '';
	var lotterySwitch = '';
	var str = [];
	var showHtml = '';
	
	if(!isNull(json.getSwitchDetailTitle)){
		title = json.getSwitchDetailTitle;
	}
	if(!isNull(json.getSwitchDetail)){
		data = json.getSwitchDetail;
	}
	if(!isNull(titleJson.getUpperSwitchInfo)){
		titleJsonObj = titleJson.getUpperSwitchInfo;
	}
	if(!isNull(titleJson.getUpperSwitchInfo[titleId].switch)){
		totalSwitch = titleJson.getUpperSwitchInfo[titleId].switch;
	}
	
	str.push('<div class="modal-content modal-central width-percent-580 lotteryChange">');
	str.push('<span class="close" onclick="onClickCloseModal();">×</span>');
	if(!isNull(titleJson.getUpperSwitchInfo[titleId].title)){
		str.push('<h4>'+titleJson.getUpperSwitchInfo[titleId].title+'</h4>');
	}
	str.push('<div class="openclosearea">開/關');
	if(totalSwitch){
		str.push('<input type="checkbox" value="'+titleId+'" id="lotteryTotalSwitch'+titleId+'" name="lotteryTotalSwitch" onclick="lotteryAllCheckBox(this,\'lotterySwitch\');" checked>');
	}else{
		str.push('<input type="checkbox" value="'+titleId+'" id="lotteryTotalSwitch'+titleId+'" name="lotteryTotalSwitch" onclick="lotteryAllCheckBox(this,\'lotterySwitch\');">');
	}
	str.push('</div>');
	
	for(var i = 0 ; i < title.length ; i++){
		if(!isNull(title[i].lotteryTitle)){
			lotteryTitle = title[i].lotteryTitle;
		}
		if(!isNull(title[i].playedId)){
			titlePlayedId = title[i].playedId;
		}
		str.push('<div class="lotterychange-table">');
		str.push('<h5>'+lotteryTitle+'<button class="savebtn" onclick="lotterySwitchDetailSave('+titleId+');">保存設定</button></h5>');
		str.push('<table>');
		str.push('<tbody>');
		str.push('<tr><th>玩法</th><th colspan="2">開關</th></tr>');
		for(var ii = 0 ; ii < data.length ; ii++){
			if(!isNull(data[ii].playedId)){
				dataPlayedId = data[ii].playedId;
			}
			if(!isNull(data[ii].midId)){
				dataMidId = data[ii].midId;
			}
			if(!isNull(data[ii].midTitle)){
				midTitle = data[ii].midTitle;
			}
			if(!isNull(data[ii].switch)){
				lotterySwitch = data[ii].switch;
			}
			if(titlePlayedId == dataPlayedId){
				str.push('<tr><td>'+midTitle+'</td>');
				if(lotterySwitch){
					str.push('<td><input type="checkbox" value="'+dataMidId+'" id="lotterySwitch'+dataMidId+'" name="lotterySwitch" onclick="lotterySingleCheckBox(this,\'lotteryTotalSwitch\','+ii+');" checked></td>');
				}else{
					str.push('<td><input type="checkbox" value="'+dataMidId+'" id="lotterySwitch'+dataMidId+'" name="lotterySwitch" onclick="lotterySingleCheckBox(this,\'lotteryTotalSwitch\','+ii+');"></td>');
				}
				str.push('</tr>');
			}
		}
		str.push('</tbody>');
		str.push('</table>');
		str.push('</div>');
	}
	str.push('</table><div class="btn-area">');
	str.push('</div>');
	
	showHtml = str.join('');
	onClickOpenModal(showHtml);
}
function lotteryAllCheckBox(obj,cName){
	var checkBox = document.getElementsByName(cName);
	for(var i = 0;i < checkBox.length;i++){
		checkBox[i].checked = obj.checked;
		switchNewList[i].switch = (obj.checked == true ? 1 : 0);
	}
}
function lotterySingleCheckBox(obj,totalCName,cou){
	var totalCheckBox = getEle(totalCName).checked;
	var singleCheckBox = obj.checked;
	var json = JSON.parse(getEle("lotterySwitchDetail").value);
	var data ='';
	var dataMidId = '';
	var checkAll = false;
	
	if(!isNull(json.getSwitchDetail)){
		data = json.getSwitchDetail;
	}
	if(singleCheckBox && !totalCheckBox){
		getEle(totalCName).checked = true;
	}
	if(!singleCheckBox){
		for(var i = 0;i < data.length;i++){
			if(!isNull(data[i].midId)){
				dataMidId = data[i].midId;
			}
			if(getEle('lotterySwitch'+dataMidId).checked){
				checkAll = true;
			}
		}
		if(!checkAll){
			getEle(totalCName).checked = false;
		}
	}
	switchNewList[cou].switch = (obj.checked == true ? 1 : 0);
}

function lotterySwitchDetailSave(titleId){
	var compareSwitchList = [];
	var str = '&lotterySwitchList=';
	var totalSwitch = document.getElementById('lotteryTotalSwitch'+titleId).checked == true ? 1 : 0;
	
	for(var i=0;i<switchOldList.length;i++){
		if(switchOldList[i].switch != switchNewList[i].switch){
			compareSwitchList.push(switchNewList[i]);
		}
	}
	if(compareSwitchList.length > 0){
		str += encodeURIComponent(JSON.stringify(compareSwitchList))+'&totalSwitch='+encodeURIComponent(totalSwitch);
		lotteryOnCheckModelPage('注意:相關內容以修改，是否保存調整後內容?','updateLotterySwitchAjax(\''+str+'\')');
		getEle('lotteryNowSettingSwitch').value = titleId;
	}
}
function updateLotterySwitchAjax(str) {
	var tmpStr = '';
	var lotteryId = parseInt(getEle('lotterySwitchDetailTitle').value)+1
	Lottery_XHR = checkXHR(Lottery_XHR);
	if (typeof Lottery_XHR != "undefined" && Lottery_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)+"&lotteryId="+lotteryId+str;
		Lottery_XHR.timeout = 10000;
		Lottery_XHR.ontimeout = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onerror = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onreadystatechange = handleUpdateLotterySwitchAjax;
		Lottery_XHR.open("POST", "./LotterySet!updateLotterySwitch.php?date=" + getNewTime(), true);
		Lottery_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		Lottery_XHR.send(tmpStr);
		enableLoadingPage();
	}
}
function handleUpdateLotterySwitchAjax() {
	if (Lottery_XHR.readyState == 4) {
		if (Lottery_XHR.status == 200) {
			try {
				if (isJSON(Lottery_XHR.responseText)) {
					var json = JSON.parse(Lottery_XHR.responseText);
					if (checkTokenIdfail(json)) {
						if(json.success){
							onCheckCloseModelPage();
							onCheckModelPage2('修改成功!');
							onClickCloseModal();
						}else{
							onCheckCloseModelPage();
							onCheckModelPage2('失敗!');
						}
					}
				}
			} catch (error) {
				console_Log("handLotterySwitchDetailAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				Lottery_XHR.abort();
				if(json.success){
					lotterySwitchSetAjax();
				}
			}
		} else {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
	}
}
// 開關設定結束
// 金額設定開始
function lotteryAmountAjax() {
	Lottery_XHR = checkXHR(Lottery_XHR);
	if (typeof Lottery_XHR != "undefined" && Lottery_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value);
		Lottery_XHR.timeout = 10000;
		Lottery_XHR.ontimeout = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onerror = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onreadystatechange = handleLotteryAmountAjax;
		Lottery_XHR.open("POST", "./LotterySet!getLotteryAmountInfo.php?date=" + getNewTime(), true);
		Lottery_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		Lottery_XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handleLotteryAmountAjax() {
	if (Lottery_XHR.readyState == 4) {
		if (Lottery_XHR.status == 200) {
			try {
				if (isJSON(Lottery_XHR.responseText)) {
					var tmpJ = isJSON2(Lottery_XHR.responseText);
					if (tmpJ) {
						if (checkTokenIdfail(tmpJ)) {
							getAmountInfo = tmpJ;
							showLotteryAmountSet();
						}
					}
				}
			} catch (error) {
				console_Log("handlotteryAmountAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				Lottery_XHR.abort();
			}
		} else {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
	}
}

// parseJSON開始//
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
// parseJSON結束//

function showLotteryAmountSet(){
	if(getAmountInfo == null || 
		!getAmountInfo['getHandicapInfo'] || 
		!getAmountInfo['getLotteryAmountLocalList'] || 
		!getAmountInfo['getLotteryAmountPlayedList'] || 
		!getAmountInfo['getLotteryAmountTypeList'] ||
		!getAmountInfo['getLotteryMidList'] ||
		!getAmountInfo['getTotalAmountInfo']){
		getAmountInfo = {'getHandicapInfo':'',
				'getLotteryAmountLocalList':'',
				'getLotteryAmountPlayedList':'',
				'getLotteryAmountTypeList':'',
				'getLotteryMidList':'',
				'getTotalAmountInfo':''};
	}
	
	var lotteryAmountLocalList = '';
	var handicapInfo = '';
	var handicap = getEle('handicapPage').value;
	var local = getEle('localPage').value;
	var str = [];
	
	if(!isNull(getAmountInfo.getHandicapInfo)){
		handicapInfo = getAmountInfo.getHandicapInfo;
	}
	if(!isNull(getAmountInfo.getLotteryAmountLocalList)){
		lotteryAmountLocalList = getAmountInfo.getLotteryAmountLocalList;
	}
	
	str.push('<div class="btn-area"> \n');
	
	str.push('</div> \n');
	str.push('<div class="contentDiv2-10"> \n');
	str.push('	<div class="tabarea"> \n');
	for(var a = 0 ; a < handicapInfo.length ; a++){
		if(!isNull(handicapInfo[a].handicapName) && !isNull(handicapInfo[a].handicapId)){
			str.push('<button id="handicapBtn'+handicapInfo[a].handicapId+'" onclick="oclickHandicapBtn('+handicapInfo[a].handicapId+');">'+handicapInfo[a].handicapName+'盤</button>\n');
		}
	}
	str.push('	</div>');
	str.push('	<div id="lotteryHandicapTable" class="lotterymoneysetting posfix">');
	str.push('	</div>');
	str.push('	<div class="lotteryall">');
	for(var c = 0 ; c < lotteryAmountLocalList.length ; c++){
		if(!isNull(lotteryAmountLocalList[c].id) && !isNull(lotteryAmountLocalList[c].type) && !isNull(lotteryAmountLocalList[c].title)){
			str.push('<button id="lotteryLocalBtn'+lotteryAmountLocalList[c].id+'" onclick="oclickAmountBtn('+lotteryAmountLocalList[c].id+');">'+lotteryAmountLocalList[c].title+'</button>\n');
		}
	}
	str.push('	</div>');
	str.push('	<div id="lotteryAmountSetDetail" class="lotteryabcd">');
	str.push('	</div>');
	str.push('</div>');

	getEle("mainContain").innerHTML = str.join("");
	oclickHandicapBtn(handicap);
	oclickAmountBtn(local);
}
function showLotteryHandicapTable(){
	if(getAmountInfo == null || 
			!getAmountInfo['getHandicapInfo'] || 
			!getAmountInfo['getLotteryAmountLocalList'] || 
			!getAmountInfo['getLotteryAmountPlayedList'] || 
			!getAmountInfo['getLotteryAmountTypeList'] ||
			!getAmountInfo['getLotteryMidList'] ||
			!getAmountInfo['getTotalAmountInfo']){
			getAmountInfo = {'getHandicapInfo':'',
					'getLotteryAmountLocalList':'',
					'getLotteryAmountPlayedList':'',
					'getLotteryAmountTypeList':'',
					'getLotteryMidList':'',
					'getTotalAmountInfo':''};
		}
	
	var handicapInfo = '';
	var handicap = getEle('handicapPage').value;
	var local = getEle('localPage').value;
	var str = [];
	
	if(!isNull(getAmountInfo.getHandicapInfo)){
		handicapInfo = getAmountInfo.getHandicapInfo;
		handicapOldList = handicapInfo;
	}
	
	str.push('<button class="btn-log-2" onclick="lotteryAmountSettingLogAjax();">操作紀錄</button> \n');
	for(var i = 0 ; i < handicapInfo.length ; i++){
		if(!isNull(handicapInfo[i].handicapId) && handicapInfo[i].handicapId == handicap){
			str.push('<p>獎金組：');
			str.push('最低:\n<input type="text" id="bonusSetMin" onkeyup="checkImputDecimal(this)" onchange="checkBonusSize(this)" value="'+ checkNull(handicapInfo[i].bonusSetMin) +'">');
			str.push('最高:\n<input type="text" id="bonusSetMax" onkeyup="checkImputDecimal(this)" onchange="checkBonusSize(this)" value="'+ checkNull(handicapInfo[i].bonusSetMax) +'">');
			str.push('盤口賠率調整:\n<input type="text" id="relativeBaseline" onkeyup="checkImputDecimal(this)" onchange="checkRelativeBaselineSize(this)" value="'+ checkNull(handicapInfo[i].relativeBaseline) +'">% \n');
			str.push('最高中獎金額:\n<input type="text" id="maxWinBonus" onkeyup="checkImputDecimal(this)" onchange="checkMaxWinBonusSize(this)" value="'+ checkNull(handicapInfo[i].maxWinBonus) +'">');
			str.push('<button onclick="conformHandicapInfo('+handicap+')">保存');
			str.push('</button>');
			str.push('</p>');
		}
	}

	getEle('lotteryHandicapTable').innerHTML = str.join('');
}

function showLotteryAmountTable(){
	if(getAmountInfo == null || 
			!getAmountInfo['getHandicapInfo'] || 
			!getAmountInfo['getLotteryAmountLocalList'] || 
			!getAmountInfo['getLotteryAmountPlayedList'] || 
			!getAmountInfo['getLotteryAmountTypeList'] ||
			!getAmountInfo['getLotteryMidList'] ||
			!getAmountInfo['getTotalAmountInfo']){
			getAmountInfo = {'getHandicapInfo':'',
					'getLotteryAmountLocalList':'',
					'getLotteryAmountPlayedList':'',
					'getLotteryAmountTypeList':'',
					'getLotteryMidList':'',
					'getTotalAmountInfo':''};
	}
	
	var lotteryAmountLocalList = '';
	var lotteryAmountPlayedList = '';
	var nowAmountPlayedList = [];
	var totalAmountInfo = '';
	var nowAmountInfo = [];
	var local = getEle('localPage').value;
	var minStr = [];
	
	if(!isNull(getAmountInfo.getLotteryAmountLocalList)){
		lotteryAmountLocalList = getAmountInfo.getLotteryAmountLocalList;
	}
	if(!isNull(getAmountInfo.getLotteryAmountPlayedList)){
		lotteryAmountPlayedList = getAmountInfo.getLotteryAmountPlayedList;
		for(var p = 0 ; p < lotteryAmountPlayedList.length ; p++){
			if(lotteryAmountPlayedList[p].typeId == lotteryAmountLocalList[local-1].type){
				nowAmountPlayedList.push(lotteryAmountPlayedList[p]);
			}
		}
	}
	if(!isNull(getAmountInfo.getTotalAmountInfo)){
		totalAmountInfo = getAmountInfo.getTotalAmountInfo;
		for(var n = 0 ; n < totalAmountInfo.length ; n++){
			if(totalAmountInfo[n].localId == local){
				nowAmountInfo.push(totalAmountInfo[n]);
			}
		}
	}
	
	for(var i = 0 ; i < nowAmountPlayedList.length ; i++){
		minStr.push('<div>');
		minStr.push('	<h5>'+nowAmountPlayedList[i].playedName+'<button onclick="updateAmountInfo();">保存</button></h5>');
		minStr.push('	<table>');
		minStr.push('		<tbody>');
		minStr.push('			<tr><th>玩法</th><th>最低投注金額</th><th>基礎賠率</th><th>投注金額1達</th><th>賠率下降為</th><th>投注金額2達</th><th>賠率下降為</th><th>單挑</th><th>操作</th></tr>');
		for(var ii = 0 ; ii < nowAmountInfo.length ; ii++){
			if(nowAmountPlayedList[i].playedId == nowAmountInfo[ii].midId){
				minStr.push('<tr>');
				minStr.push('	<td>'+nowAmountInfo[ii].minName+LOTTERY_PRIZE_LEVEL[nowAmountInfo[ii].prizeLevel]+'</td>');
				minStr.push('	<td><input type="text" class="" maxlength="20" onkeyup="checkImputDecimal(this)" onchange = "getAmountUnchange(this.value,\'baseBet\','+ii+');" value="'+ Math.floor(checkNull(nowAmountInfo[ii].baseBet)*100)/100 +'"></td>');
				minStr.push('	<td><input type="text" class="" maxlength="20" onkeyup="checkImputDecimal(this)" onchange = "getAmountUnchange(this.value,\'baseline\','+ii+');" value="'+ Math.floor(checkNull(nowAmountInfo[ii].baseline)*100)/100 +'"></td>');
				minStr.push('	<td><input type="text" class="" maxlength="20" onkeyup="checkImputDecimal(this)" onchange = "getAmountUnchange(this.value,\'betLevel1\','+ii+');" value="'+ Math.floor(checkNull(nowAmountInfo[ii].betLevel1)*100)/100 +'"></td>');
				minStr.push('	<td><input type="text" class="" maxlength="20" onkeyup="checkImputDecimal(this)" onchange = "getAmountUnchange(this.value,\'baselineLevel1\','+ii+');" value="'+ Math.floor(checkNull(nowAmountInfo[ii].baselineLevel1)*100)/100 +'"></td>');
				minStr.push('	<td><input type="text" class="" maxlength="20" onkeyup="checkImputDecimal(this)" onchange = "getAmountUnchange(this.value,\'betLevel2\','+ii+');" value="'+ Math.floor(checkNull(nowAmountInfo[ii].betLevel2)*100)/100 +'"></td>');
				minStr.push('	<td><input type="text" class="" maxlength="20" onkeyup="checkImputDecimal(this)" onchange = "getAmountUnchange(this.value,\'baselineLevel2\','+ii+');" value="'+ Math.floor(checkNull(nowAmountInfo[ii].baselineLevel2)*100)/100 +'"></td>');
				if(nowAmountInfo[ii].dtSwitch == 1){
					minStr.push('<td>');
					minStr.push('	<input type="checkbox" checked="checked" onclick="setDtSwitch(this,\'dtSwitch\','+ii+');">');
					minStr.push('	<input type="text" onkeyup="checkImputDecimal(this)" onchange = "getAmountUnchange(this.value,\'dtRatio\','+ii+');" value="'+ Math.floor(checkNull(nowAmountInfo[ii].dtRatio)*100)/100 +'">％／');
					minStr.push('	<input type="text" onkeyup="checkImputDecimal(this)" onchange = "getAmountUnchange(this.value,\'dtBonus\','+ii+');" value="'+ Math.floor(checkNull(nowAmountInfo[ii].dtBonus)*100)/100 +'">金額');
				}else{
					minStr.push('<td class="canttype">');
					minStr.push('	<input type="checkbox" onclick="setDtSwitch(this,\'dtSwitch\','+ii+');">');
					minStr.push('	<input type="text" onkeyup="checkImputDecimal(this)" onchange = "getAmountUnchange(this.value,\'dtRatio\','+ii+');" value="'+ Math.floor(checkNull(nowAmountInfo[ii].dtRatio)*100)/100 +'" readonly>％／');
					minStr.push('	<input type="text" onkeyup="checkImputDecimal(this)" onchange = "getAmountUnchange(this.value,\'dtBonus\','+ii+');" value="'+ Math.floor(checkNull(nowAmountInfo[ii].dtBonus)*100)/100 +'" readonly>金額');
				
				}
				minStr.push('	</td>');
				minStr.push('	<td><a href="javascript:void(0);" onclick="opnDescriptionSetDiv('+ii+');">描述修改</a></td>');
				minStr.push('</tr>');
			}
		}// end of for(totalAmountInfo)
		minStr.push('		</tbody>');
		minStr.push('	</table>');
		minStr.push('</div>');
	}// end of for(lotteryAmountPlayedList)
	getEle('lotteryAmountSetDetail').innerHTML = minStr.join('');
}

function getAmountUnchange(val,name,cou){
	if(!isNull(val) && !isNull(name) && !isNull(cou) && val != '' && name != '' && cou >= 0){
		amountNewList[cou][name] = val;
	}
}
function refreshLotteryHandicapData(){
	if(getAmountInfo == null || 
			!getAmountInfo['getHandicapInfo'] || 
			!getAmountInfo['getLotteryAmountLocalList'] || 
			!getAmountInfo['getLotteryAmountPlayedList'] || 
			!getAmountInfo['getLotteryAmountTypeList'] ||
			!getAmountInfo['getLotteryMidList'] ||
			!getAmountInfo['getTotalAmountInfo']){
			getAmountInfo = {'getHandicapInfo':'',
					'getLotteryAmountLocalList':'',
					'getLotteryAmountPlayedList':'',
					'getLotteryAmountTypeList':'',
					'getLotteryMidList':'',
					'getTotalAmountInfo':''};
		}
	
	var handicapInfo = '';
	if(!isNull(getAmountInfo.getHandicapInfo)){
		handicapInfo = getAmountInfo.getHandicapInfo;
	}
	for(var i = 0 ; i < handicapInfo.length ; i++){
		if(!isNull(handicapInfo[i].handicapId)){
			getEle('handicapBtn'+handicapInfo[i].handicapId).classList.remove('active');
		}
	}
}
function refreshLotteryLocalData(){
	if(getAmountInfo == null || 
			!getAmountInfo['getHandicapInfo'] || 
			!getAmountInfo['getLotteryAmountLocalList'] || 
			!getAmountInfo['getLotteryAmountPlayedList'] || 
			!getAmountInfo['getLotteryAmountTypeList'] ||
			!getAmountInfo['getLotteryMidList'] ||
			!getAmountInfo['getTotalAmountInfo']){
			getAmountInfo = {'getHandicapInfo':'',
					'getLotteryAmountLocalList':'',
					'getLotteryAmountPlayedList':'',
					'getLotteryAmountTypeList':'',
					'getLotteryMidList':'',
					'getTotalAmountInfo':''};
		}
	
	var lotteryAmountLocalList = '';
	if(!isNull(getAmountInfo.getLotteryAmountLocalList)){
		lotteryAmountLocalList = getAmountInfo.getLotteryAmountLocalList;
	}
	if(!isNull(getAmountInfo.getTotalAmountInfo)){
		getEle('amountList').value = JSON.stringify(getAmountInfo.getTotalAmountInfo);
		amountOldList = JSON.parse(getEle('amountList').value);
		amountNewList = JSON.parse(getEle('amountList').value);
	}
	for(var i = 0 ; i < lotteryAmountLocalList.length ; i++){
		if(!isNull(lotteryAmountLocalList[i].id)){
			getEle('lotteryLocalBtn'+lotteryAmountLocalList[i].id).classList.remove('active');
		}
	}
}
function oclickHandicapBtn(handicap){
	var local = getEle('localPage').value;
	getEle('handicapPage').value = handicap;
	
	refreshLotteryHandicapData();
	getEle('handicapBtn'+handicap).classList.add('active');
	showLotteryHandicapTable(handicap);
}
function oclickAmountBtn(local){
	var handicap = getEle('handicapPage').value;
	getEle('localPage').value = local;
	
	refreshLotteryLocalData();
	getEle('lotteryLocalBtn'+local).classList.add('active');
	showLotteryAmountTable();
}
function opnDescriptionSetDiv(cou){
	if(getAmountInfo == null || 
			!getAmountInfo['getHandicapInfo'] || 
			!getAmountInfo['getLotteryAmountLocalList'] || 
			!getAmountInfo['getLotteryAmountPlayedList'] || 
			!getAmountInfo['getLotteryAmountTypeList'] ||
			!getAmountInfo['getLotteryMidList'] ||
			!getAmountInfo['getTotalAmountInfo']){
			getAmountInfo = {'getHandicapInfo':'',
					'getLotteryAmountLocalList':'',
					'getLotteryAmountPlayedList':'',
					'getLotteryAmountTypeList':'',
					'getLotteryMidList':'',
					'getTotalAmountInfo':''};
		}
	
	var indu = '';
	var str = [];
	if(!isNull(getAmountInfo.getTotalAmountInfo[cou])){
		indu = getAmountInfo.getTotalAmountInfo[cou];
		descriptionOldList = indu;
	}
	str.push('<div class="modal-content modal-central width-percent-460 moneysetting">');
	str.push('	<span class="close" onclick="onClickCloseModal();">×</span>');
	str.push('	<h3>修改信息</h3>');
	str.push('	<div><span>玩法介紹：</span><textarea id="playedText" onchange = "getAmountUnchange(this.value,\'playedText\','+cou+');">'+ checkNull(indu.playedText) +'</textarea></div>');
	str.push('	<div><span>選號規則：</span><textarea id="lotteryRule" onchange = "getAmountUnchange(this.value,\'lotteryRule\','+cou+');">'+ checkNull(indu.lotteryRule) +'</textarea></div>');
	str.push('	<div><span>中獎說明：</span><textarea id="lotteryExample" onchange = "getAmountUnchange(this.value,\'lotteryExample\','+cou+');">'+ checkNull(indu.lotteryExample) +'</textarea></div>');
	str.push('	<div class="btn-area">');
	str.push('		<button onclick="onClickCloseModal();">取消</button>');
	str.push('		<button onclick="conformDescription('+indu.minId+','+cou+');">修改</button>');
	str.push('	</div>');
	str.push('</div>');
	onClickOpenModal(str.join(''));
}

function setDtSwitch(val,name,cou){
	if(!isNull(val) && !isNull(name) && !isNull(cou) && val != '' && name != '' && cou >= 0){
		if(val.checked){
			val.parentNode.children[1].readOnly = '';
			val.parentNode.children[2].readOnly = '';
			val.parentNode.classList.remove('canttype');
			amountNewList[cou][name] = true;
		}else{
			val.parentNode.children[1].readOnly = 'readonly';
			val.parentNode.children[2].readOnly = 'readonly';
			val.parentNode.classList.add('canttype');
			amountNewList[cou][name] = false;
		}
	}
}

function compareDescriptionInfo(minId){
	var differentObj = new Object();
	
	if(!isNull(descriptionOldList.playedText) && !isNull(getEle('playedText').value)){
		if(descriptionOldList.playedText != getEle('playedText').value){
			differentObj.playedText = getEle('playedText').value;
		}else{
			differentObj.playedText = '--';
		}
	}
	if(!isNull(descriptionOldList.lotteryRule) && !isNull(getEle('lotteryRule').value)){
		if(descriptionOldList.lotteryRule != getEle('lotteryRule').value){
			differentObj.lotteryRule = getEle('lotteryRule').value;
		}else{
			differentObj.lotteryRule = '--';
		}
	}
	if(!isNull(descriptionOldList.lotteryExample) && !isNull(getEle('lotteryExample').value)){
		if(descriptionOldList.lotteryExample != getEle('lotteryExample').value){
			differentObj.lotteryExample = getEle('lotteryExample').value;
		}else{
			differentObj.lotteryExample = '--';
		}
	}
	if(!isNull(minId)){
		differentObj.minId = minId;
	}else{
		differentObj.minId = 0;
	}
	
	return differentObj;
}

function conformDescription(minId,cou){
	var playedText = getEle('playedText').value;
	var lotteryRule = getEle('lotteryRule').value;
	var lotteryExample = getEle('lotteryExample').value;
	var descriptionLog = JSON.stringify(compareDescriptionInfo(minId));
	
	var str = '';
	
	str = '&minId='+encodeURIComponent(minId)+'&playedText='+encodeURIComponent(playedText)+'&lotteryRule='+encodeURIComponent(lotteryRule)+'&lotteryExample='+encodeURIComponent(lotteryExample)+'&descriptionLog='+encodeURIComponent(descriptionLog);
	
	if(amountOldList[cou].playedText != amountNewList[cou].playedText ||
		amountOldList[cou].lotteryRule != amountNewList[cou].lotteryRule ||
		amountOldList[cou].lotteryExample != amountNewList[cou].lotteryExample){
		lotteryOnCheckModelPage('確定送出', 'upadteDescriptionAjax(\'' + str + '\')');
	}
}
function upadteDescriptionAjax(str) {
	var tmpStr = '';
	Lottery_XHR = checkXHR(Lottery_XHR);
	if (typeof Lottery_XHR != "undefined" && Lottery_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)+str;
		Lottery_XHR.timeout = 10000;
		Lottery_XHR.ontimeout = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onerror = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onreadystatechange = handleUpadteDescriptionAjax;
		Lottery_XHR.open("POST", "./LotterySet!upadteDescription.php?date=" + getNewTime(), true);
		Lottery_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		Lottery_XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handleUpadteDescriptionAjax() {
	if (Lottery_XHR.readyState == 4) {
		if (Lottery_XHR.status == 200) {
			try {
				if (isJSON(Lottery_XHR.responseText)) {
					var json = JSON.parse(Lottery_XHR.responseText);
					if (checkTokenIdfail(json)) {
						if(json.success){
							onCheckCloseModelPage();
							onClickCloseModal();
							onCheckModelPage2('修改成功!');
						}else{
							onCheckCloseModelPage();
							onCheckModelPage2('失敗!');
						}
					}
				}
			} catch (error) {
				console_Log("handlotteryAmountAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				Lottery_XHR.abort();
				if(json.success){
					lotteryAmountAjax();
				}
			}
		} else {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
	}
}

function compareByList() {
	var compareList = [];
	var todo = false;

	for(var i=0;i<amountOldList.length;i++){
		differentObj = new Object();
		todo = false;
			
		if(amountOldList[i].baseBet != amountNewList[i].baseBet){
			todo = true;
		}
		if(amountOldList[i].baseline != amountNewList[i].baseline){
			todo = true;
		}
		if(amountOldList[i].baselineLevel1 != amountNewList[i].baselineLevel1){
			todo = true;
		}
		if(amountOldList[i].baselineLevel2 != amountNewList[i].baselineLevel2){
			todo = true;
		}
		if(amountOldList[i].betLevel1 != amountNewList[i].betLevel1){
			todo = true;
		}
		if(amountOldList[i].betLevel2 != amountNewList[i].betLevel2){
			todo = true;
		}
		if(amountOldList[i].dtBonus != amountNewList[i].dtBonus){
			todo = true;
		}
		if(amountOldList[i].dtRatio != amountNewList[i].dtRatio){
			todo = true;
		}
		if(amountOldList[i].dtSwitch != amountNewList[i].dtSwitch){
			todo = true;
		}
		if(todo){
			compareList.push(amountNewList[i]);
		}
	}
 return compareList;
}

function getAmountLogList() {
	var updateList = new Array();
	var differentObj = new Object();
	var todo = false;

	for(var i=0;i<amountOldList.length;i++){
		differentObj = new Object();
		todo = false;
			
		if(amountOldList[i].baseBet != amountNewList[i].baseBet){
			differentObj.baseBet = amountNewList[i].baseBet;
			todo = true;
		}
		if(amountOldList[i].baseline != amountNewList[i].baseline){
			differentObj.baseline = amountNewList[i].baseline;
			todo = true;
		}
		if(amountOldList[i].baselineLevel1 != amountNewList[i].baselineLevel1){
			differentObj.baselineLevel1 = amountNewList[i].baselineLevel1;
			todo = true;
		}
		if(amountOldList[i].baselineLevel2 != amountNewList[i].baselineLevel2){
			differentObj.baselineLevel2 = amountNewList[i].baselineLevel2;
			todo = true;
		}
		if(amountOldList[i].betLevel1 != amountNewList[i].betLevel1){
			differentObj.betLevel1 = amountNewList[i].betLevel1;
			todo = true;
		}
		if(amountOldList[i].betLevel2 != amountNewList[i].betLevel2){
			differentObj.betLevel2 = amountNewList[i].betLevel2;
			todo = true;
		}
		if(amountOldList[i].dtBonus != amountNewList[i].dtBonus){
			differentObj.dtBonus = amountNewList[i].dtBonus;
			todo = true;
		}
		if(amountOldList[i].dtRatio != amountNewList[i].dtRatio){
			differentObj.dtRatio = amountNewList[i].dtRatio;
			todo = true;
		}
		if(amountOldList[i].dtSwitch != amountNewList[i].dtSwitch){
			differentObj.dtSwitch = amountNewList[i].dtSwitch;
			todo = true;
		}
		if(todo){
			differentObj.midId = amountNewList[i].midId;
			differentObj.minId = amountNewList[i].minId;
			differentObj.prizeLevel = amountNewList[i].prizeLevel;
			
			updateList.push(differentObj);
		}
	}
 return updateList;
}
function updateAmountInfo() {
	var compareList = JSON.stringify(compareByList());
	var amountLogList = JSON.stringify(getAmountLogList());

	if(compareList.length > 2 && compareList != '[]' && amountLogList.length > 2 && amountLogList != '[]'){
		lotteryOnCheckModelPage('確定送出', 'updateAmountInfoAjax(\'' + encodeURIComponent(compareList) + '\',\'' +encodeURIComponent(amountLogList) + '\')');
	}
}
function updateAmountInfoAjax(compareList,amountLogList) {
	var tmpStr = '';
	Lottery_XHR = checkXHR(Lottery_XHR);
	if (typeof Lottery_XHR != "undefined" && Lottery_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)+'&amountInfoList='+compareList+'&amountLogList='+amountLogList;
		Lottery_XHR.timeout = 10000;
		Lottery_XHR.ontimeout = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onerror = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onreadystatechange = handleUpdateAmountInfoAjax;
		Lottery_XHR.open("POST", "./LotterySet!updateAmountInfo.php?date=" + getNewTime(), true);
		Lottery_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		Lottery_XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handleUpdateAmountInfoAjax() {
	if (Lottery_XHR.readyState == 4) {
		if (Lottery_XHR.status == 200) {
			try {
				if (isJSON(Lottery_XHR.responseText)) {
					var json = JSON.parse(Lottery_XHR.responseText);
					if (checkTokenIdfail(json)) {
						if(json.success){
							onCheckCloseModelPage();
							onCheckModelPage2('修改成功!');
						}else{
							onCheckCloseModelPage();
							onCheckModelPage2('失敗!');
						}
					}
				}
			} catch (error) {
				console_Log("handUpdateAmountInfoAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				Lottery_XHR.abort();
				if(json.success){
					lotteryAmountAjax();
				}
			}
		} else {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
	}
}

function compareHandicapInfo(id){
	var differentObj = new Object();
	var handicapId = parseInt(id)-1;
	
	if(!isNull(handicapOldList[handicapId].bonusSetMin) && !isNull(getEle('bonusSetMin').value)){
		if(handicapOldList[handicapId].bonusSetMin != getEle('bonusSetMin').value){
			differentObj.bonusSetMin = getEle('bonusSetMin').value;
		}else{
			differentObj.bonusSetMin = '--';
		}
	}
	if(!isNull(handicapOldList[handicapId].bonusSetMax) && !isNull(getEle('bonusSetMax').value)){
		if(handicapOldList[handicapId].bonusSetMax != getEle('bonusSetMax').value){
			differentObj.bonusSetMax = getEle('bonusSetMax').value;
		}else{
			differentObj.bonusSetMax = '--';
		}
	}
	if(!isNull(handicapOldList[handicapId].relativeBaseline) && !isNull(getEle('relativeBaseline').value)){
		if(handicapOldList[handicapId].relativeBaseline != getEle('relativeBaseline').value){
			differentObj.relativeBaseline = getEle('relativeBaseline').value;
		}else{
			differentObj.relativeBaseline = '--';
		}
	}
	if(!isNull(handicapOldList[handicapId].maxWinBonus) && !isNull(getEle('maxWinBonus').value)){
		if(handicapOldList[handicapId].maxWinBonus != getEle('maxWinBonus').value){
			differentObj.maxWinBonus = getEle('maxWinBonus').value;
		}else{
			differentObj.maxWinBonus = '--';
		}
	}
	if(!isNull(id)){
		differentObj.handicapId = id;
	}else{
		differentObj.handicapId = 0;
	}
	
	return differentObj;
}

function conformHandicapInfo(handicapId){
	var bonusSetMin = getEle('bonusSetMin').value;
	var bonusSetMax = getEle('bonusSetMax').value;
	var relativeBaseline = getEle('relativeBaseline').value;
	var maxWinBonus = getEle('maxWinBonus').value;
	var handicapLog =  JSON.stringify(compareHandicapInfo(handicapId));
	
	var str = '';
	
	if(bonusSetMin != '' && bonusSetMax != '' && relativeBaseline != ''){
		str = '&handicapId='+handicapId+'&bonusSetMin='+bonusSetMin+'&bonusSetMax='+bonusSetMax+'&relativeBaseline='+relativeBaseline+'&maxWinBonus='+maxWinBonus+'&handicapLog='+handicapLog;
		lotteryOnCheckModelPage('確定送出', 'upadteHandicapAjax(\'' + str + '\')');
	}
}
function upadteHandicapAjax(str) {
	var tmpStr = '';
	Lottery_XHR = checkXHR(Lottery_XHR);
	if (typeof Lottery_XHR != "undefined" && Lottery_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value)+str;
		Lottery_XHR.timeout = 10000;
		Lottery_XHR.ontimeout = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onerror = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onreadystatechange = handleUpadteHandicapAjax;
		Lottery_XHR.open("POST", "./LotterySet!upadteHandicap.php?date=" + getNewTime(), true);
		Lottery_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		Lottery_XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handleUpadteHandicapAjax() {
	if (Lottery_XHR.readyState == 4) {
		if (Lottery_XHR.status == 200) {
			try {
				if (isJSON(Lottery_XHR.responseText)) {
					var json = JSON.parse(Lottery_XHR.responseText);
					if (checkTokenIdfail(json)) {
						if(json.success){
							onCheckCloseModelPage();
							onCheckModelPage2('修改成功!');
						}else{
							onCheckCloseModelPage();
							onCheckModelPage2('失敗!');
						}
					}
				}
			} catch (error) {
				console_Log("handleUpadteHandicapAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				Lottery_XHR.abort();
				if(json.success){
					lotteryAmountAjax();
				}
			}
		} else {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
	}
}
function checkImputDecimal(val) {
	val.value = checkInputDecimalVal(val.value);// 小數點後N位由checkDecimalVal:regExp設定
}

function checkBonusSize(val) {
	var Bonus = checkInputNumberVal(val.value);
	if(Bonus >= 1000 && Bonus <= 9999){
		val.value = Bonus;
	}else{
		val.value = 1700;
	}
}
function checkRelativeBaselineSize(val) {
	var RelativeBaseline = checkInputNumberVal(val.value);
	if(RelativeBaseline >= 0 && RelativeBaseline <= 100){
		val.value = RelativeBaseline;
	}else{
		val.value = 100;
	}
}
function checkMaxWinBonusSize(val) {
	var maxWinBonus = checkInputNumberVal(val.value);
	if(maxWinBonus >= 0 && maxWinBonus <= 100000000){
		val.value = maxWinBonus;
	}else{
		val.value = 400000;
	}
}
// 以下log
function lotterySwitchSettingLogAjax() {
	Lottery_XHR = checkXHR(Lottery_XHR);
	if (typeof Lottery_XHR != "undefined" && Lottery_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value);
		Lottery_XHR.timeout = 10000;
		Lottery_XHR.ontimeout = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onerror = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onreadystatechange = handleLotterySwitchSettingLogAjax;
		Lottery_XHR.open("POST", "./LotterySet!getLotterySwitchSettingLog.php?date=" + getNewTime(), true);
		Lottery_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		Lottery_XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handleLotterySwitchSettingLogAjax() {
	if (Lottery_XHR.readyState == 4) {
		if (Lottery_XHR.status == 200) {
			try {
				if (isJSON(Lottery_XHR.responseText)) {
					var json = JSON.parse(Lottery_XHR.responseText);
					if (checkTokenIdfail(json)) {
						getEle("lotterySettingLog").value = Lottery_XHR.responseText;
						if(!isNull(json.lotterySettingLog)){
							changePage_init(showLotterySwitchSettingLog,json.lotterySettingLog.length,1,10,"nowLotterySwitchLogPage","nowLotterySwitchLogMaxPage");
						}
						
					}
				}
			} catch (error) {
				console_Log("handleLotterySwitchSettingLogAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				Lottery_XHR.abort();
			}
		} else {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
	}
}

function showLotterySwitchSettingLog(){
	var json = JSON.parse(getEle("lotterySettingLog").value);
	var logInfo = checkNull(json.lotterySettingLog);
	var logStr=[];
	
	logStr.push('<div class="modal-content width-percent-580 margin-fix-4">');
	logStr.push('	<span class="close" onclick="onClickCloseModal();">×</span>');
	logStr.push('	<h3>彩種開關設定紀錄表</h3>');
	logStr.push('	<div class="tab-content">');
	logStr.push('		<table class="table-zebra tr-hover">');
	logStr.push('			<tbody>');
	logStr.push('				<tr><th>操作者</th><th>操作項目</th><th>操作時間</th><th>操作IP</th><th>內容</th></tr>');
	for(var cou = parseInt(getEle('startCount').value);cou < parseInt(getEle('endCount').value);cou++){
		logStr.push('<tr>');
		logStr.push('	<td>'+checkNull(logInfo[cou].opsAccName)+'</td>');
		logStr.push('	<td>'+checkNull(logInfo[cou].actionText)+'</td>');
		logStr.push('	<td>'+checkNull(logInfo[cou].opsDatetime)+'</td>');
		logStr.push('	<td>'+checkNull(logInfo[cou].ip)+'</td>');
		logStr.push('	<td><a href="javascript:void(0);" onclick="showLotterySwitchDetailLog(' + cou + ');">詳細</a></td>');
		logStr.push('</tr>');
	}
	logStr.push('		</tbody>');
	logStr.push('		</table>');
	logStr.push('		<p class="media-control text-center">');
	logStr.push('			<a href="javascript:void(0);" onclick="onClickFirstPage();" class="backward"></a>');
	logStr.push('			<a href="javascript:void(0);" onclick="onClickPreviousPage();" class="backward-fast"></a>');
	logStr.push('			<span>總頁數：<i id= "nowLotterySwitchLogPage">1</i><span>/</span><i id = "nowLotterySwitchLogMaxPage">1</i>頁</span>');
	logStr.push('			<a href="javascript:void(0);" onclick="onClickNextPage();" class="forward"></a>');
	logStr.push('			<a href="javascript:void(0);" onclick="onClickLastPage();" class="forward-fast"></a>');
	logStr.push('		</p>');
	logStr.push('	</div>');
	logStr.push('</div>');
	
	onClickOpenModal(logStr.join(''));
}
function showLotterySwitchDetailLog(cou){
	var listJson = JSON.parse(getEle("lotteryUpperSwitchInfo").value);
	var json = JSON.parse(getEle("lotterySettingLog").value);
	var logInfo = checkNull(json.lotterySettingLog[cou]);
	
	var typeList = checkNull(listJson.getLotterySwitchTypeList);
	var localList = checkNull(listJson.getUpperSwitchInfo);
	var midList = checkNull(listJson.getLotteryMidList);
	
	var localData = checkNull(logInfo.detail);
	var logDetail = localData;
	
	var logDetailStr=[];
	
	if(isJSON(localData)){
		localData = JSON.parse(localData);
		if(!isNull(Object.keys(localData[0]))){
			localData = Object.keys(localData[0]);
		}
	}
	if(isJSON(logDetail)){
		logDetail = JSON.parse(logDetail);
			if(!isNull(logDetail[0])){
				var logDetailKey = Object.keys(logDetail[0]);
				logDetail = logDetail[0];
				if(!isNull(logDetail[logDetailKey])){
					logDetail = logDetail[logDetailKey];
			}
		}
	}
	
	logDetailStr.push('<div class="modal-content width-percent-20">');
	logDetailStr.push('	<span class="close" onclick="onClickCloseModalV2();">×</span>');
	logDetailStr.push('	<h3>彩種:'+showLotterySwitchTitleByList(localData,'getUpperSwitchInfo','id','title')+'</h3>');
	logDetailStr.push('	<div class="tab-content">');
	logDetailStr.push('		<table class="table-zebra tr-hover">');
	logDetailStr.push('			<tbody>');
	logDetailStr.push('				<tr><th>玩法</th><th>開關</th></tr>');
	for(var i = 0;i < Object.keys(logDetail).length;i++){
		logDetailStr.push('<tr>');
			logDetailStr.push('<td>'+showLotterySwitchTitleByList(checkNull(Object.keys(logDetail)[i]),'getLotteryMidList','midId','midName')+'</td>');
			logDetailStr.push('<td>'+(checkNull(logDetail[Object.keys(logDetail)[i]])==0?'關閉':'啟用')+'</td>');
		logDetailStr.push('</tr>');
	}
	logDetailStr.push('			</tbody>');
	logDetailStr.push('		</table>');
	logDetailStr.push('	</div>');
	logDetailStr.push('</div>');
	
	onClickOpenModalV2(logDetailStr.join(''));
}

function showLotterySwitchTitleByList(val,listName,idName,newName){
	var listJson = JSON.parse(getEle("lotteryUpperSwitchInfo").value);
	var newList = checkNull(listJson[listName]);
	
	for(var i = 0;i < newList.length;i++){
		if(!isNull(newList[i]) && !isNull(val)){
			if(!isNull(newList[i][idName])){
				if(newList[i][idName] == val){
					return newList[i][newName];
					break;
				}
			}
		}
	}
	return'';
}

function lotteryAmountSettingLogAjax() {
	Lottery_XHR = checkXHR(Lottery_XHR);
	if (typeof Lottery_XHR != "undefined" && Lottery_XHR != null) {
		tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value);
		Lottery_XHR.timeout = 10000;
		Lottery_XHR.ontimeout = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onerror = function() {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
		Lottery_XHR.onreadystatechange = handleLotteryAmountSettingLogAjax;
		Lottery_XHR.open("POST", "./LotterySet!getLotteryAmountSettingLog.php?date=" + getNewTime(), true);
		Lottery_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		Lottery_XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handleLotteryAmountSettingLogAjax() {
	if (Lottery_XHR.readyState == 4) {
		if (Lottery_XHR.status == 200) {
			try {
				if (isJSON(Lottery_XHR.responseText)) {
					var json = JSON.parse(Lottery_XHR.responseText);
					if (checkTokenIdfail(json)) {
						getEle("lotterySettingLog").value = Lottery_XHR.responseText;
					}
				}
			} catch (error) {
				console_Log("handleLotteryAmountSettingLogAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				Lottery_XHR.abort();
				showLotteryAmountSettingLog();
			}
		} else {
			disableLoadingPage();
			Lottery_XHR.abort();
		}
	}
}

function showLotteryAmountSettingLog(){
	var logStr=[];
	
	logStr.push('<div class="modal-content width-percent-960 margin-fix-4">');
	logStr.push('	<span class="close" onclick="onClickCloseModal();">×</span>');
	logStr.push('	<h3>彩種金額設定紀錄表</h3>');
	logStr.push('	<div class="tab-area">');
	logStr.push('		<button onclick="onclickLotteryAmountLogTopBtn(0);">盤口設定紀錄</button>\n');
	logStr.push('		<button onclick="onclickLotteryAmountLogTopBtn(1);">金額設定紀錄</button>\n');
	logStr.push('		<button onclick="onclickLotteryAmountLogTopBtn(2);">描述修改紀錄</button>\n');
	logStr.push('	</div>');
	logStr.push('	<div class="tab-content">');
	logStr.push('		<table class="table-zebra tr-hover" id="lotteryAmountSettingLogTable">');
	logStr.push('			<tbody>');
	logStr.push('			</tbody>');
	logStr.push('		</table>');
	logStr.push('		<p class="media-control text-center">');
	logStr.push('			<a href="javascript:void(0);" onclick="onClickFirstPage();" class="backward"></a>');
	logStr.push('			<a href="javascript:void(0);" onclick="onClickPreviousPage();" class="backward-fast"></a>');
	logStr.push('			<span>總頁數：<i id= "nowLotteryAmountLogPage">1</i><span>/</span><i id = "nowLotteryAmountLogMaxPage">1</i>頁</span>');
	logStr.push('			<a href="javascript:void(0);" onclick="onClickNextPage();" class="forward"></a>');
	logStr.push('			<a href="javascript:void(0);" onclick="onClickLastPage();" class="forward-fast"></a>');
	logStr.push('		</p>');
	logStr.push('	</div>');
	logStr.push('</div>');
	
	onClickOpenModal(logStr.join(''));
	onclickLotteryAmountLogTopBtn(0);
}

function onclickLotteryAmountLogTopBtn(num){
	var allBtn = document.getElementsByClassName('tab-area')[0].getElementsByTagName('button');
	for(var i = 0 ; i < allBtn.length ; i++){
		removeClass(allBtn[i], 'active');
	}
	addClass(allBtn[num],'active');
	getEle('nowLotteryLogType').value = num;
	
	var logInfo = getAmountEachLogInfo(num);
	
	if(num == 0){
		changePage_init(onclickLotteryHandicapSettingLogBtn,logInfo.length,1,10,"nowLotteryAmountLogPage","nowLotteryAmountLogMaxPage");
	}else if(num == 1){
		changePage_init(onclickLotteryAmountSettingLogBtn,logInfo.length,1,10,"nowLotteryAmountLogPage","nowLotteryAmountLogMaxPage");
	}else if(num == 2){
		changePage_init(onclickLotteryDescriptionSettingLogBtn,logInfo.length,1,10,"nowLotteryAmountLogPage","nowLotteryAmountLogMaxPage");
	}
	
}

function getAmountEachLogInfo(num){
	var json = JSON.parse(getEle("lotterySettingLog").value);
	var logInfo = checkNull(json.lotterySettingLog);
	var newInfoList = [];
	
	if(num == 0){
		for(var c0 = 0 ; c0 < logInfo.length ; c0++){
			if(!isNull(logInfo[c0].action)){
				if(logInfo[c0].action == LOG_ACTION_LOTTERY_HANDICAP_SET){
					newInfoList.push(logInfo[c0]);
				}
			}
		}
	}else if(num == 1){
		for(var c1 = 0 ; c1 < logInfo.length ; c1++){
			if(!isNull(logInfo[c1].action)){
				if(logInfo[c1].action >= LOG_ACTION_LOTTERY_AMOUNT_SET_CQSSC && logInfo[c1].action <= LOG_ACTION_LOTTERY_AMOUNT_SET_PL5){
					newInfoList.push(logInfo[c1]);
				}
			}
		}
	}else if(num == 2){
		for(var c2 = 0 ; c2 < logInfo.length ; c2++){
			if(!isNull(logInfo[c2].action)){
				if(logInfo[c2].action == LOG_ACTION_LOTTERY_DESCRIPTION_SET){
					newInfoList.push(logInfo[c2]);
				}
			}
		}
	}
	
	return newInfoList;
}

function onclickLotteryHandicapSettingLogBtn(){
	var logInfo = getAmountEachLogInfo(0);
	var logDetail = '';
	var handiCapName = [ "", "A盤", "B盤", "C盤", "D盤", "E盤" ];
	var logTableStr=[];
	
	logTableStr.push('<tr><th>操作者</th><th>盤口</th><th>獎金組最低金額</th><th>獎金組最高金額</th><th>盤口賠率調整</th><th>最高中獎金額</th><th>操作時間</th><th>操作IP</th></tr>');
	for(var cou = parseInt(getEle('startCount').value);cou < parseInt(getEle('endCount').value);cou++){
		logDetail = checkNull(logInfo[cou].detail);
		if(isJSON(logDetail)){
			logDetail = JSON.parse(logDetail);
		}
		logTableStr.push('<tr>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].opsAccName)+'</td>');
		logTableStr.push('	<td>'+handiCapName[checkNull(logDetail.handicapId)]+'</td>');
		logTableStr.push('	<td>'+checkNull(logDetail.bonusSetMin)+'</td>');
		logTableStr.push('	<td>'+checkNull(logDetail.bonusSetMax)+'</td>');
		logTableStr.push('	<td>'+checkNull(logDetail.relativeBaseline)+'</td>');
		logTableStr.push('	<td>'+checkNull(logDetail.maxWinBonus)+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].opsDatetime)+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].ip)+'</td>');
		logTableStr.push('</tr>');
	}
	getEle('lotteryAmountSettingLogTable').innerHTML = logTableStr.join('');
}

function onclickLotteryAmountSettingLogBtn(){
	var logInfo = getAmountEachLogInfo(1);
	var logTableStr=[];
	
	logTableStr.push('<tr><th>操作者</th><th>操作項目</th><th>操作時間</th><th>操作IP</th><th>內容</th></tr>');
	for(var cou = parseInt(getEle('startCount').value);cou < parseInt(getEle('endCount').value);cou++){
		logTableStr.push('<tr>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].opsAccName)+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].actionText)+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].opsDatetime)+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].ip)+'</td>');
		logTableStr.push('	<td><a href="javascript:void(0);" onclick="showLotteryAmountDetailLog(\''+ checkNull(logInfo[cou].actionText)+'\',\''+ cou + '\');">詳細</a></td>');
		logTableStr.push('</tr>');
	}
	getEle('lotteryAmountSettingLogTable').innerHTML = logTableStr.join('');
}

function onclickLotteryDescriptionSettingLogBtn(){
	var logInfo = getAmountEachLogInfo(2);
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
		logTableStr.push('	<td>'+showLotteryAmountTitleByList(checkNull(logDetail.minId),'getLotteryMidList','midId','midName')+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].opsDatetime)+'</td>');
		logTableStr.push('	<td>'+checkNull(logInfo[cou].ip)+'</td>');
		logTableStr.push('	<td><a href="javascript:void(0);" onclick="showLotteryDescriptionDetailLog(' + cou + ');">詳細</a></td>');
		logTableStr.push('</tr>');
	}
	getEle('lotteryAmountSettingLogTable').innerHTML = logTableStr.join('');
}

function showLotteryAmountDetailLog(title,cou){
	var logInfo = getAmountEachLogInfo(1);
	var logDetail = '';
	var logDetailStr=[];
	
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
	logDetailStr.push('				<tr><th>玩法</th><th>獎等</th><th>內容</th></tr>');
	for(var i = 0 ; i < logDetail.length ; i++){
		logDetailStr.push('<tr>');
		logDetailStr.push('	<td>'+showLotteryAmountTitleByList(checkNull(logDetail[i].minId),'getLotteryMidList','midId','midName')+'</td>');
		logDetailStr.push('	<td>'+(checkNull(logDetail[i].prizeLevel) == 0 ? '--' : logDetail[i].prizeLevel)+'</td>');
		logDetailStr.push('	<td>'+showLotteryAmountLogDetailDetail(logDetail[i])+'</td>');
		logDetailStr.push('</tr>');
	}
	logDetailStr.push('			</tbody>');
	logDetailStr.push('		</table>');
	logDetailStr.push('	</div>');
	logDetailStr.push('</div>');
	
	onClickOpenModalV2(logDetailStr.join(''));	
}

function showLotteryAmountLogDetailDetail(data){
	var str = [];
	var key = ['baseBet','baseline','betLevel1','baselineLevel1','betLevel2','baselineLevel2','dtSwitch','dtRatio','dtBonus'];
	const transKey = {'baseBet':'最低投注金額','baseline':'基礎賠率','betLevel1':'投注金額1達','baselineLevel1':'賠率1下降為	','betLevel2':'投注金額2達','baselineLevel2':'賠率2下降為','dtSwitch':'單挑開關','dtRatio':'單挑%數','dtBonus':'單挑金額'};
	
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
	var logInfo = getAmountEachLogInfo(2);
	var logDetail = '';
	var logDetailStr=[];
	
	logDetail = checkNull(logInfo[cou].detail);
	if(isJSON(logDetail)){
		logDetail = JSON.parse(logDetail);
	}
	
	logDetailStr.push('<div class="modal-content width-percent-60">');
	logDetailStr.push('	<span class="close" onclick="onClickCloseModalV2();">×</span>');
	logDetailStr.push('	<h3>玩法:'+showLotteryAmountTitleByList(checkNull(logDetail.minId),'getLotteryMidList','midId','midName')+'</h3>');
	logDetailStr.push('	<div class="tab-content">');
	logDetailStr.push('		<table class="table-zebra tr-hover">');
	logDetailStr.push('			<tbody>');
	logDetailStr.push('				<tr><th>玩法介紹</th><td>'+logDetail.playedText+'</td></tr>');
	logDetailStr.push('				<tr><th>選號規則</th><td>'+logDetail.lotteryRule+'</td></tr>');
	logDetailStr.push('				<tr><th>中獎說明</th><td>'+logDetail.lotteryExample+'</td></tr>');
	logDetailStr.push('			</tbody>');
	logDetailStr.push('		</table>');
	logDetailStr.push('	</div>');
	logDetailStr.push('</div>');
	
	onClickOpenModalV2(logDetailStr.join(''));
}

function showLotteryAmountTitleByList(val,listName,idName,newName){
	if(getAmountInfo == null || 
			!getAmountInfo['getHandicapInfo'] || 
			!getAmountInfo['getLotteryAmountLocalList'] || 
			!getAmountInfo['getLotteryAmountPlayedList'] || 
			!getAmountInfo['getLotteryAmountTypeList'] ||
			!getAmountInfo['getLotteryMidList'] ||
			!getAmountInfo['getTotalAmountInfo']){
			getAmountInfo = {'getHandicapInfo':'',
					'getLotteryAmountLocalList':'',
					'getLotteryAmountPlayedList':'',
					'getLotteryAmountTypeList':'',
					'getLotteryMidList':'',
					'getTotalAmountInfo':''};
	}
	
	var newList = checkNull(getAmountInfo[listName]);
	
	for(var i = 0;i < newList.length;i++){
		if(!isNull(newList[i]) && !isNull(val)){
			if(!isNull(newList[i][idName])){
				if(newList[i][idName] == val){
					return newList[i][newName];
					break;
				}
			}
		}
	}
	return'';
}

// function時間
function getTimeForData(start,end){
	var st = new Date(start);
	var et = new Date(end);
	
	var sMs = st.getMilliseconds();
	var eMs = et.getMilliseconds();
	
	var diffT = new Date(eMs - sMs);
	
	console.log(diffT.getMilliseconds()+'Millisec');
}

