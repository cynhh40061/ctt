var Lottery_XHR = null;
var amountUpdateList = '';

const LOTTERY_SET_JS = true;

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
		Lottery_XHR.onreadystatechange = handLotterySwitchSetAjax;
		Lottery_XHR.open("POST", "./LotterySet!getUpperSwitchInfo.php?date=" + getNewTime(), true);
		Lottery_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		Lottery_XHR.send(str);
		enableLoadingPage();
	}
}
function handLotterySwitchSetAjax() {
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
		str.push('<h6>'+lotterySwitchTypeList[a].typeName+'</h6>');
		str.push('<table class="table-zebra tr-hover">');
		str.push('<tbody> \n');
		str.push('<tr><th>名稱</th><th>開關</th><th>操作</th></tr>');
		for(var b = 0 ; b < jsonObject.length ; b++){
			if(jsonObject[b].type == lotterySwitchTypeList[a].typeId && !isNull(jsonObject[b].title) && !isNull(jsonObject[b].switch)){
				str.push('<tr><td>'+jsonObject[b].title+(jsonObject[b].switch == 1 ? '<td><span>開</span></td>' : '<td><span class="red">關</span></td>'));
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
		Lottery_XHR.onreadystatechange = handLotterySwitchDetailAjax;
		Lottery_XHR.open("POST", "./LotterySet!getSwitchDetail.php?date=" + getNewTime(), true);
		Lottery_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		Lottery_XHR.send(str);
		enableLoadingPage();
	}
}
function handLotterySwitchDetailAjax() {
	if (Lottery_XHR.readyState == 4) {
		if (Lottery_XHR.status == 200) {
			try {
				if (isJSON(Lottery_XHR.responseText)) {
					var json = JSON.parse(Lottery_XHR.responseText);
					if (checkTokenIdfail(json)) {
						getEle("lotterySwitchDetail").value = Lottery_XHR.responseText;
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
	var Info = JSON.parse(getEle('lotteryUpperSwitchInfo').value);
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
	if(!isNull(Info.getUpperSwitchInfo[titleId].switch)){
		totalSwitch = Info.getUpperSwitchInfo[titleId].switch;
	}
	
	str.push('<div class="modal-content modal-central width-percent-580 lotteryChange">');
	str.push('<span class="close" onclick="onClickCloseModal();">×</span>');
	if(!isNull(titleJson.getUpperSwitchInfo[titleId].title)){
		str.push('<h4>'+titleJson.getUpperSwitchInfo[titleId].title+'</h4>');
	}
	str.push('<div class="openclosearea">開/關');
	if(totalSwitch){
		str.push('<input type="checkbox" value="'+titleId+'" id="lotteryTotalSwitch'+titleId+'" name="lotteryTotalSwitch" checked onclick="lotteryAllCheckBox(this,\'lotterySwitch\');">');
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
		str.push('<h5>'+lotteryTitle+'<button class="savebtn" onclick="lotteryOnCheckModelPage(\'注意:相關內容以修改，是否保存調整後內容?\',\'lotterySwitchDetailSave()\');">保存設定</button></h5>');
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
					str.push('<td><input type="checkbox" value="'+dataMidId+'" id="lotterySwitch'+dataMidId+'" name="lotterySwitch" checked onclick="lotterySingleCheckBox(this,\'lotteryTotalSwitch\');"></td>');
				}else{
					str.push('<td><input type="checkbox" value="'+dataMidId+'" id="lotterySwitch'+dataMidId+'" name="lotterySwitch" onclick="lotterySingleCheckBox(this,\'lotteryTotalSwitch\');"></td>');
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
	}
}
function lotterySingleCheckBox(obj,totalCName){
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
	
}

function lotterySwitchDetailSave(){
	var lotterySwitch = document.getElementsByName("lotterySwitch");
	var str = "&lotterySwitchList=";
	var lotterySwitchId = "";
	for(var i = 0 , n =lotterySwitch.length ; i < n ; i++ ){
		if(lotterySwitch[i].checked == true){
			lotterySwitchId += lotterySwitch[i].value+",";
		}
	}
	if(lotterySwitchId.length>1){
		lotterySwitchId = lotterySwitchId.substring(0,lotterySwitchId.length-1);
	}
	str+=lotterySwitchId;
	updateLotterySwitchAjax(str);
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
		Lottery_XHR.onreadystatechange = handUpdateLotterySwitchAjax;
		Lottery_XHR.open("POST", "./LotterySet!updateLotterySwitch.php?date=" + getNewTime(), true);
		Lottery_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		Lottery_XHR.send(tmpStr);
		enableLoadingPage();
	}
}
function handUpdateLotterySwitchAjax() {
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
		Lottery_XHR.onreadystatechange = handlotteryAmountAjax;
		Lottery_XHR.open("POST", "./LotterySet!getLotteryAmountInfo.php?date=" + getNewTime(), true);
		Lottery_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		Lottery_XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handlotteryAmountAjax() {
	if (Lottery_XHR.readyState == 4) {
		if (Lottery_XHR.status == 200) {
			try {
				if (isJSON(Lottery_XHR.responseText)) {
					var json = JSON.parse(Lottery_XHR.responseText);
					if (checkTokenIdfail(json)) {
						addExtraHidden(['lotteryAmountInfo','handicapPage','typePage']);
						getEle('lotteryAmountInfo').value = Lottery_XHR.responseText;
						showLotteryAmountSet();
					}
				}
			} catch (error) {
				console_Log("handlotteryAmountAjax error:" + error.message);
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

function showLotteryAmountSet(handicap,type){
	getEle("searchArea").innerHTML = "";
	getEle("mainContain").innerHTML = "";
	
	var json = JSON.parse(getEle('lotteryAmountInfo').value);
	
	var lotteryAmountPlayedList = '';
	var handicapInfo = '';
	
	if(!isNull(json.getLotteryAmountPlayedList)){
		lotteryAmountPlayedList = json.getLotteryAmountPlayedList;
	}
	if(!isNull(json.getHandicapInfo)){
		handicapInfo = json.getHandicapInfo;
	}
	
	var str = [];
	var lotteryHandicapArray = ["","A盤","B盤","C盤","D盤","E盤"];
	var lotteryTypeArray = ["","時時彩","分分彩","11選5","快3","PK10","3D","排列三/五"];
	
	str.push('<div class="btn-area"> \n');
	for(var a = 1 ; a < handicapInfo.length ; a++){
		str.push('<button class="btn-lg btn-gray" onclick="lotterySwitchDetailAjax('+a+');">'+handicapInfo[a]+'</button>\n');
	}
	str.push('</div>');
	str.push('<div class="Div2-7-tablearea"> \n');
	str.push('<table class="table-zebra tr-hover">');
	str.push('<tbody> \n');
	str.push('<tr><th>獎金組:</th><td>最低:<input type="text" onkeyup = ""></td><td>最高:<input type="text" class="margin-fix-2" onkeyup = ""></td></tr>');
	str.push('</tbody> \n');
	str.push('</table> \n');
	str.push('</div>');
	str.push('<div class="btn-area"> \n');
	for(var b = 1 ; b < lotteryTypeArray.length ; b++){
		if(b == type){
			str.push('<button class="btn-lg btn-gray" onclick="lotterySwitchDetailAjax('+b+');">'+lotteryTypeArray[b]+'</button>\n');
		}else{
			str.push('<button class="btn-lg btn-orange" onclick="lotterySwitchDetailAjax('+b+');">'+lotteryTypeArray[b]+'</button>\n');
		}
	}
	str.push('</div>');
	str.push('<div class="Div2-7-tablearea"> \n');
	str.push('<table class="table-zebra tr-hover" id="lotteryAmountSetTable">');
	str.push('<tbody> \n');
	str.push('</tbody> \n');
	str.push('</table> \n');
	str.push('</div>');
	
	getEle("mainContain").innerHTML = str.join("");
	showLotteryAmountTable();
}
function oclickHandicapBtn(val){
	
}
function oclickAmountBtn(val){
	
}

function showLotteryAmountTable(){
	var json = JSON.parse(getEle('lotteryAmountInfo').value);
	
	var lotteryAmountPlayedList = '';
	var handicapInfo = '';
	var totalAmountInfo = '';
	
	var handicapPage = 1;
	var typePage = 1;
	
	var handicapStr = [];
	var midStr = [];
	
	if(!isNull(json.getLotteryAmountPlayedList)){
		lotteryAmountPlayedList = json.getLotteryAmountPlayedList;
	}
	if(!isNull(json.getHandicapInfo)){
		handicapInfo = json.getHandicapInfo;
	}
	if(!isNull(json.getTotalAmountInfo)){
		totalAmountInfo = json.getTotalAmountInfo;
		amountUpdateList = json.getTotalAmountInfo;
	}
	
	if(getEle('lotteryAmountInfo').value != ''){
		handicapPage = getEle('handicapPage').value;
	}
	if(getEle('lotteryAmountInfo').value != ''){
		typePage = getEle('typePage').value;
	}
	
	handicapStr.push('<tr><th>獎金組:</th><td>最低:<input type="text" onkeyup = ""></td><td>最高:<input type="text" class="margin-fix-2" onkeyup = ""></td></tr>');
	getEle('handicapTable').innerHTML = handicapStr.join('');
	
	for(var i = 0 ; i < lotteryAmountPlayedList.length ; i++){
		if(lotteryAmountPlayedList[i].typeId == typePage){
			midStr.push('<tr><th colspan="9">'+lotteryAmountPlayedList[i].playedName+'</th></tr>');
			midStr.push('<tr><th>玩法</th><th>最低投注金額</th><th>基礎賠率</th><th>投注金額1達</th><th>賠率下降為</th><th>投注金額2達</th><th>賠率下降為</th><th>單挑</th><th>openDiv2</th></tr>');
			for(var ii = 0 ; ii < totalAmountInfo.length ; ii++){
				if(totalAmountInfo[ii].typeId == typePage && lotteryAmountPlayedList[i].playedId == totalAmountInfo[ii].playedId){
					midStr.push('<tr>');
					midStr.push('<td>'+totalAmountInfo[ii].minName+'</td>');
					midStr.push('<td><input type="text" class="" maxlength="20" onchange = "getAmountUnchange(this.value,\'baseBet\','+ii+');" value="'+totalAmountInfo[ii].baseBet+'"></td>');
					midStr.push('<td><input type="text" class="" maxlength="20" onchange = "getAmountUnchange(this.value,\'baseLine\','+ii+');" value="'+totalAmountInfo[ii].baseLine+'"></td>');
					midStr.push('<td><input type="text" class="" maxlength="20" onchange = "getAmountUnchange(this.value,\'betLevel1\','+ii+');" value="'+totalAmountInfo[ii].betLevel1+'"></td>');
					midStr.push('<td><input type="text" class="" maxlength="20" onchange = "getAmountUnchange(this.value,\'lineLevel1\','+ii+');" value="'+totalAmountInfo[ii].lineLevel1+'"></td>');
					midStr.push('<td><input type="text" class="" maxlength="20" onchange = "getAmountUnchange(this.value,\'betLevel2\','+ii+');" value="'+totalAmountInfo[ii].betLevel2+'"></td>');
					midStr.push('<td><input type="text" class="" maxlength="20" onchange = "getAmountUnchange(this.value,\'lineLevel2\','+ii+');" value="'+totalAmountInfo[ii].lineLevel2+'"></td>');
					midStr.push('<td>on\off:'+totalAmountInfo[ii].dtSwtch+'|'+totalAmountInfo[ii].dtRatio+'%|$:'+totalAmountInfo[ii].dtBonus+'</td>');
					midStr.push('<td><button class="btn-gray" onclick="opndiv('+totalAmountInfo[ii].midId+');">描述修改</button></td>');
					midStr.push('</tr>');
				}
			}// end of for(totalAmountInfo)
		}
	}// end of for(lotteryAmountPlayedList)
	getEle('lotteryAmountSetTable').innerHTML = midStr.join('');
}

function getAmountUnchange(val,name,cou){
	if(!isNull(val) && !isNull(name) && !isNull(cou) && val != '' && name != '' && cou >= 0){
		console_Log('here');
		amountUpdateList[cou][name] = val;
	}
}


