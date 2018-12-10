var XHR_SetLotteryTime = null;
var weekArray = ["X","周一","周二","周三","周四","周五","周六","周日","下周一","下周二","下周三","下周四","下周五","下周六","下周日"];
var typeArray = ["日","周"];
var zodiacArray = ["無","鼠","牛","虎","兔","龍","蛇","馬","羊","猴","雞","狗","豬"];

function SetLotteryTime() {
	SetLotteryTime_into();
	getLotteryTypeAjax();
}

function SetLotteryTime_into() {
	var str = '';
	if (document.getElementsByName("lotteryTimeJson").length != 1) {
		str += '\n<input type="hidden" name="lotteryTypeJson" value = "">';
		str += '\n<input type="hidden" name="lotteryTimeJson" value = "">';
		str += '\n<input type="hidden" name="copyLotteryTimeJson" value = "">';

		str += '\n<input type="hidden" name="lotteryType" value = 0>';
		str += '\n<input type="hidden" name="lotteryTimeList" value = 0>';
		str += '\n<input type="hidden" name="lotteryTimeSetLogList" value = "">';
		str += '\n<input type="hidden" name="lotteryDetailData" value = "">';
		str += '\n<input type="hidden" name="type" value = 0>';
		
		str += '\n<input type="hidden" name="createPeriodType" value = 0>';
		
		str += '\n<input type="hidden" name="zodiac" value = 0>';
		

	}
	if (str != '') {
		document.getElementById("extraHidden").innerHTML = str;
	}
	delete str;
	str = undefined;
	if (document.getElementById("mainContain") != null) {
		document.getElementById("mainContain").innerHTML = "";
	}
	if (document.getElementById("searchArea") != null) {
		document.getElementById("searchArea").innerHTML = "";
	}
	if (document.getElementById("myModal") != null) {
		document.getElementById("myModal").innerHTML = "";
	}
	if (document.getElementById("myModalV2") != null) {
		document.getElementById("myModalV2").innerHTML = "";
	}
}

function getLotteryTypeAjax() {
	XHR_SetLotteryTime = checkXHR(XHR_SetLotteryTime);
	if (typeof XHR_SetLotteryTime != "undefined" && XHR_SetLotteryTime != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value);
		XHR_SetLotteryTime.timeout = 10000;
		XHR_SetLotteryTime.ontimeout = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onerror = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onreadystatechange = handleGetLotteryTypeAjax;
		XHR_SetLotteryTime.open("POST", "./SetLotteryTime!getLotteryType.php?date=" + getNewTime(), true);
		XHR_SetLotteryTime.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR_SetLotteryTime.send(tmpStr);
		enableLoadingPage();
	}
}

function handleGetLotteryTypeAjax() {
	if (XHR_SetLotteryTime.readyState == 4) {
		if (XHR_SetLotteryTime.status == 200) {
			try {
				if (isJSON(XHR_SetLotteryTime.responseText)) {
					var json = JSON.parse(XHR_SetLotteryTime.responseText);
					if (checkTokenIdfail(json)) {
						getEle("lotteryTypeJson").value = XHR_SetLotteryTime.responseText;
						showLotteryTypeTable();
					}
				}
			} catch (error) {
				console_Log("handleGetLotteryTypeAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR_SetLotteryTime.abort();
			}
		} else {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
	}
}


function showLotteryTypeTable() {
	var lotteryTypeJson = getEle("lotteryTypeJson").value;
	var arrObj = [];
	var str = [];
	if (isJSON(lotteryTypeJson)) {
		var lotteryTypeObj = JSON.parse(lotteryTypeJson).lotteryType;
		str.push('	<div class="contentDiv2-11">	\n');
		str.push('	<button class="" onclick="lotteryTimeSetLogAjax();">操作紀錄</button>	\n');
		str.push('		<div id = "lotteryList">	\n');
		str.push('		</div>	\n');
		str.push('	</div>	\n');

		getEle("mainContain").innerHTML = str.join('');

		var lotteryTypeObjKey = Object.keys(JSON.parse(getEle("lotteryTypeJson").value).lotteryType).sort();
		var ele = getEle("lotteryList");
		for (var k = 0; k < lotteryTypeObjKey.length; k++) {

			var lotteryTypeTitle = document.createElement("h6");

			lotteryTypeTitle.innerHTML = lotteryTypeObjKey[k].substring(4, lotteryTypeObjKey[k].length);
			var table = document.createElement("table");

			var num = 0;
			var x = 4;
			var y = lotteryTypeObj[lotteryTypeObjKey[k]].length / x;
			
			lotteryTypeObj[lotteryTypeObjKey[k]] = jsonDataSort(lotteryTypeObj[lotteryTypeObjKey[k]] , "id" , true);

			arrObj[0] = {};
			arrObj[0].type = "tbody";
			arrObj[0].html = [];
			for (var i = 0; i < y; i++) {
				arrObj[0].html[i] = {};
				arrObj[0].html[i].type = "tr";
				arrObj[0].html[i].html = [];
				for (var j = 0; j < x; j++) {
					if (lotteryTypeObj[lotteryTypeObjKey[k]].length > num) {
						arrObj[0].html[i].html[j] = {};
						arrObj[0].html[i].html[j].type = "td";
						arrObj[0].html[i].html[j].text = '<a href = "javaScript:void(0);" onclick="getLotteryTimeListAjax(\'' + lotteryTypeObj[lotteryTypeObjKey[k]][num]["id"]
								+ '\')">' + lotteryTypeObj[lotteryTypeObjKey[k]][num]["title"] + '</a>';
						num++;
					} else {
						arrObj[0].html[i].html[j] = {};
						arrObj[0].html[i].html[j].type = "td";
					}
				}
			}

			eleInsertTableEle(table, arrObj);
			lotteryTypeTitle.insertBefore(table, null);
			getEle("lotteryList").insertBefore(lotteryTypeTitle, null);

		}
	}
}


function getLotteryTimeListAjax(id) {
	getEle("lotteryTimeJson").value = "";
	getEle("copyLotteryTimeJson").value = "";
	
	getEle("lotteryType").value = id;
	XHR_SetLotteryTime = checkXHR(XHR_SetLotteryTime);
	if (typeof XHR_SetLotteryTime != "undefined" && XHR_SetLotteryTime != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&id=" + id;
		XHR_SetLotteryTime.timeout = 10000;
		XHR_SetLotteryTime.ontimeout = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onerror = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onreadystatechange = handleGetLotteryTimeListAjax;
		XHR_SetLotteryTime.open("POST", "./SetLotteryTime!getLotteryTimeList.php?date=" + getNewTime(), true);
		XHR_SetLotteryTime.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR_SetLotteryTime.send(tmpStr);
		enableLoadingPage();
	}
}

function handleGetLotteryTimeListAjax() {
	if (XHR_SetLotteryTime.readyState == 4) {
		if (XHR_SetLotteryTime.status == 200) {
			try {
				if (isJSON(XHR_SetLotteryTime.responseText)) {
					var json = JSON.parse(XHR_SetLotteryTime.responseText);
					if (checkTokenIdfail(json)) {
						getEle("lotteryTimeJson").value = XHR_SetLotteryTime.responseText;
						getEle("copyLotteryTimeJson").value = XHR_SetLotteryTime.responseText;
						showLotteryTimeList(getEle("copyLotteryTimeJson").value);
					}
				}
			} catch (error) {
				console_Log("handleGetLotteryTypeAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR_SetLotteryTime.abort();
			}
		} else {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
	}
}

function showLotteryTimeList(valJson) {
	var lotteryTableTitleArr = [ "期數", "官方開獎時間", "截止投注時間(秒)", "平台開獎時間","跳開時間", "停止對獎時間"];
	var lotteryTableNameArr = [ "issue", "kjTime", "stopBettingTime", "platformKjTime","jumpOffTime", "giveUpKjTime"];
	var zodiac = 0;
	if (isJSON(valJson)) {
		var timeList = JSON.parse(valJson).lotteryTimeList;
		if(timeList.length > 0){
			if(typeof timeList[0]["createPeriodType"] !== "undefind" && timeList[0]["createPeriodType"] != null){
				if(timeList[0]["createPeriodType"] == 0){
					lotteryTableTitleArr = [ "期數", "官方開獎時間", "截止投注時間(秒)", "平台開獎時間","跳開時間", "停止對獎時間"];
					lotteryTableNameArr = [ "issue", "kjTime", "stopBettingTime", "platformKjTime","jumpOffTime", "giveUpKjTime"];
				}else if(timeList[0]["createPeriodType"] == 1){
					lotteryTableTitleArr = [ "期數", "官方開獎時間", "截止投注時間(秒)", "平台開獎時間","跳開時間", "停止對獎時間" , "建立日期(周)","開始下注日期(周)","開始下注時間","建立期號日期(周)"/*,"建立期號時間"*/];
					lotteryTableNameArr = [ "issue", "kjTime", "stopBettingTime", "platformKjTime","jumpOffTime", "giveUpKjTime","createWeekType","startBettingWeekType","startBettingTime","createPeriodWeekType"/*,"createPeriodTime"*/];
				}
			}
			
			if(typeof timeList[0]["zodiacType"] !== "undefind" && timeList[0]["zodiacType"] != null){
				zodiac =timeList[0]["zodiacType"];
			}
		}
		
		var arrObj = [];
		var str = [];
		var lotteryTypeName = "";
		if(isJSON(getEle("lotteryTypeJson").value)){
			var lotteryTypeJson = JSON.parse(getEle("lotteryTypeJson").value).lotteryType;
			for(var  key in lotteryTypeJson){
				for(var i = 0 ; i < lotteryTypeJson[key].length ; i++){
					if(toInt(lotteryTypeJson[key][i].id) == toInt(getEle("lotteryType").value)){
						lotteryTypeName = lotteryTypeJson[key][i].title;
						break;
					}
				}
			}
			
		}
		
		str.push('<div class="modal-content modal-central width-percent-960 timesetting">		');
		str.push('	<span class="close" onclick="onClickCloseModal();">×</span>		');
		str.push('	<p>'+lotteryTypeName+'</p>		');
		str.push('	<a href="javascript:void(0);" onclick="setLotteryTimeToolsTableToDay();">填時輔助工具</a>		');
		str.push('	     生肖：<select id = "zodiacSelect"></select>		');
		str.push('	<button onclick="showCheckSaveLotteryTime();">保存修改</button>		');
		str.push('	<table id = "lotteryTimeTable" class = "tr-hover"></table></div>	');
		onClickOpenModal(str.join(''));
		
		
		removeAllOption("zodiacSelect");
		for(var i = 0 ; i < zodiacArray.length ; i++){
			addOptionNoDup("zodiacSelect", zodiacArray[i], i);
		}
		
		selectItemByValue(getEle("zodiacSelect"),zodiac);
		
		getEle("zodiac").value = zodiac;
		
		
		

		arrObj[0] = {};
		arrObj[0].type = "tbody";
		arrObj[0].html = [];
		for (var i = 0; i < timeList.length+1; i++) {
			arrObj[0].html[i] = {};
			arrObj[0].html[i].type = "tr";
			arrObj[0].html[i].html = [];

			for (var j = 0; j < lotteryTableTitleArr.length; j++) {
				if (i == 0) {
					arrObj[0].html[i].html[j] = {};
					arrObj[0].html[i].html[j].type = "th";
					arrObj[0].html[i].html[j].text = lotteryTableTitleArr[j];
				} else if (i > 0){
					var k = i-1;
					if(lotteryTableNameArr[j] == "issue"){
						arrObj[0].html[i].html[j] = {};
						arrObj[0].html[i].html[j].type = "td";
						arrObj[0].html[i].html[j].text = '<input type="text" onchange = "onChangeLotteryTimeData(\''+timeList[k]["id"]+'\',\''+timeList[k]["issue"]+'\' , this);"  id = "'+lotteryTableNameArr[j]+timeList[k]["issue"]+'"  name = "' + lotteryTableNameArr[j] + '" value = "' + timeList[k]["issue"] + '">';
					}
					else if (lotteryTableNameArr[j] == "stopBettingTime") {
						var stopBettingTime = 0;
						var stopBettingSec = 0
						if (timeList[k]["stopBettingTime"] != null && typeOf(timeList[k]["stopBettingTime"]) != "undefined") {
							stopBettingTime = timeList[k]["stopBettingTime"];
						}
						stopBettingSec = stopBettingTime;

						arrObj[0].html[i].html[j] = {};
						arrObj[0].html[i].html[j].type = "td";
						arrObj[0].html[i].html[j].text = '<input type="text" onchange = "onChangeLotteryTimeData(\''+timeList[k]["id"]+'\',\''+timeList[k]["issue"]+'\' , this);" id = "'+lotteryTableNameArr[j]+timeList[k]["issue"]+'" name = "' + lotteryTableNameArr[j] + '" value = "' + stopBettingSec + '">';

					}
					else if(lotteryTableNameArr[j] == "createPeriodWeekType"){
						arrObj[0].html[i].html[j] = {};
						arrObj[0].html[i].html[j].type = "td";
						var str = [];
						str.push('<select onchange = "onChangeLotteryTimeData(\''+timeList[k]["id"]+'\',\''+timeList[k]["issue"]+'\' , this);" id = "'+lotteryTableNameArr[j]+timeList[k]["issue"]+'" name = "' + lotteryTableNameArr[j] + '">');
						for(var l = 1 ; l <= 7 ; l++){
							if(timeList[k][lotteryTableNameArr[j]] == l){
								str.push('<option value = '+l+' selected>'+weekArray[l]+'</option>');
							}
							else{
								str.push('<option value = '+l+' >'+weekArray[l]+'</option>');
							}
							
						}
						str.push('</select>');
						arrObj[0].html[i].html[j].text = str.join('');

					}
					else if(lotteryTableNameArr[j] == "createWeekType" || lotteryTableNameArr[j] == "startBettingWeekType" ){
						arrObj[0].html[i].html[j] = {};
						arrObj[0].html[i].html[j].type = "td";
						var str = [];
						str.push('<select onchange = "onChangeLotteryTimeData(\''+timeList[k]["id"]+'\',\''+timeList[k]["issue"]+'\' , this);" id = "'+lotteryTableNameArr[j]+timeList[k]["issue"]+'" name = "' + lotteryTableNameArr[j] + '">');
						for(var l = 1 ; l <= 14 ; l++){
							if(timeList[k][lotteryTableNameArr[j]] == l){
								str.push('<option value = '+l+' selected>'+weekArray[l]+'</option>');
							}
							else{
								str.push('<option value = '+l+' >'+weekArray[l]+'</option>');
							}
							
						}
						str.push('</select>');
						arrObj[0].html[i].html[j].text = str.join('');
					}
					else if (timeList[k][lotteryTableNameArr[j]] != null && typeOf(timeList[k][lotteryTableNameArr[j]]) != "undefined") {
						var sec = toInt(timeList[k][lotteryTableNameArr[j]]);
						arrObj[0].html[i].html[j] = {};
						arrObj[0].html[i].html[j].type = "td";
						arrObj[0].html[i].html[j].text = '<input type="text" onchange = "onChangeLotteryTimeData(\''+timeList[k]["id"]+'\',\''+timeList[k]["issue"]+'\' , this);" id = "'+lotteryTableNameArr[j]+timeList[k]["issue"]+'" name = "' + lotteryTableNameArr[j] + '" value = "' + toTime(sec) + '">';
					}
				}
			}
		}
		insertTableEle("lotteryTimeTable", arrObj);
	}
}

function onChangeLotteryTimeData(id ,issue , ele){
	var sjson = {};
	var copyLotteryTimeJson = getEle("copyLotteryTimeJson").value;
	if(isJSON(copyLotteryTimeJson)){
		var json = JSON.parse(copyLotteryTimeJson).lotteryTimeList;
		for(var i = 0 ; i < Object.keys(json).length ; i++){
			if(json[i]["issue"] == issue && json[i]["id"] == id){
				if(json[i][ele.name] != null && typeOf(json[i][ele.name]) != "undefined"){
					if(ele.name == "giveUpKjTime" || ele.name == "kjTime" || ele.name == "platformKjTime" || ele.name == "jumpOffTime" || ele.name == "createPeriodTime" || ele.name == "startBettingTime" ){
						var val = checkTime(ele.value); 
						if(val != ""){
							var timeArr = val.split(":");
							if(timeArr.length == 1){
								var h = toInt(timeArr[0])*60*60;
								json[i][ele.name] = toInt(h);
							}
							else if (timeArr.length == 2){
								var h = toInt(timeArr[0])*60*60;
								var m = toInt(timeArr[1])*60;
								json[i][ele.name] = toInt(h)+toInt(m);
							}
							else if (timeArr.length == 3){
								var h = toInt(timeArr[0])*60*60;	
								var m = toInt(timeArr[1])*60;
								var s = toInt(timeArr[2]);
								json[i][ele.name] = toInt(h)+toInt(m)+toInt(s);
							}
							
							
							if(ele.name == "platformKjTime"){
								if(typeof json[i]["kjTime"] != "undefined"){
									var stopBettingTimeSec = toInt(json[i]["kjTime"]) - toInt(json[i][ele.name]);
									
									json[i]["stopBettingTime"] = stopBettingTimeSec;
								}
							}
						}
						else{
							ele.value = "";
						}
					
					}
					else{
						json[i][ele.name] = toInt(ele.value);
						
						if(ele.name == "stopBettingTime"){
							if(typeof json[i]["kjTime"] != "undefined"){
								var platformKjTimeSec = toInt(json[i]["kjTime"]) - toInt(json[i][ele.name]);
								
								json[i]["platformKjTime"] = toInt(platformKjTimeSec);
							}
						}
						
					}
				}
			}
		}
		sjson["lotteryTimeList"] = json;
		
		showLotteryTimeList(JSON.stringify(sjson));
		getEle("copyLotteryTimeJson").value = JSON.stringify(sjson);
	}
}

function showSetLotteryTimeTools(lotteryToolsTitleArr,lotteryToolsNameArr) {
	var timeToolsArrObj = [];
	var str = [];
	
	str.push('<div class="modal-content modal-central width-percent-720 timesettingv2" id = "showSetLotteryTimeToolsDiv">		');
	str.push('	<span class="close" onclick="onClickCloseModalV2();">×</span>		');
	str.push('<button onclick = "setLotteryTimeToolsTableToDay();">日</button> ');
	str.push('<button onclick = "setLotteryTimeToolsTableToWeek();">周</button>');
	str.push('	<table id = "lotteryTimeToolsTable" class = "tr-hover"></table>		');
	str.push('	<div class="btn-area">		');
	str.push('		<button onclick="onClickCloseModalV2();">取消</button>		');
	str.push('		<button onclick="insertLotterTimeyListAjax();">生成</button>		');
	str.push('	</div>		');
	str.push('</div>		');
	onClickOpenModalV2(str.join(''));

	
	getEle("lotteryTimeToolsTable").innerHTML = "";
	
	timeToolsArrObj[0] = {};
	timeToolsArrObj[0].type = "tbody";
	timeToolsArrObj[0].html = [];
	for (var i = 0; i < 6; i++) {
		timeToolsArrObj[0].html[i] = {};
		timeToolsArrObj[0].html[i].type = "tr";
		timeToolsArrObj[0].html[i].html = [];
		for (var j = 0; j < lotteryToolsTitleArr.length; j++) {
			timeToolsArrObj[0].html[i].html[j] = {};
			if (i == 0) {
				timeToolsArrObj[0].html[i].html[j].type = "th";
				timeToolsArrObj[0].html[i].html[j].text = lotteryToolsTitleArr[j];
			} else {
				if(lotteryToolsNameArr[j] == "kjTime" || lotteryToolsNameArr[j] == "startBettingTime"){
					timeToolsArrObj[0].html[i].html[j].type = "td";
					timeToolsArrObj[0].html[i].html[j].text = '<input type="text" onchange = "this.value = checkTime(this.value);" name = "' + lotteryToolsNameArr[j] + '"></td>';
				}
//				else if(lotteryToolsNameArr[j] == "createWeekType"){
//					timeToolsArrObj[0].html[i].html[j].type = "td";
//					timeToolsArrObj[0].html[i].html[j].text = '<input type="checkbox" name="' + lotteryToolsNameArr[j] + '" value="1">一'
//										+'<input type="checkbox" name="' + lotteryToolsNameArr[j] + '" value="2">二'
//										+'<input type="checkbox" name="' + lotteryToolsNameArr[j] + '" value="4">三'
//										+'<input type="checkbox" name="' + lotteryToolsNameArr[j] + '" value="8">四'
//										+'<input type="checkbox" name="' + lotteryToolsNameArr[j] + '" value="16">五'
//										+'<input type="checkbox" name="' + lotteryToolsNameArr[j] + '" value="32">六'
//										+'<input type="checkbox" name="' + lotteryToolsNameArr[j] + '" value="64">日'
//										+'</td>';
//				}
				else if(lotteryToolsNameArr[j] == "createPeriodWeekType"){
					timeToolsArrObj[0].html[i].html[j].type = "td";
					var str = [];
					str.push('<select name = "'+lotteryToolsNameArr[j]+'">');
					for(var l = 1 ; l <= 7 ; l++){
						str.push('<option value = '+l+' >'+weekArray[l]+'</option>');
					}
					str.push('</select>');
					timeToolsArrObj[0].html[i].html[j].text = str.join('');
					
					
					
					//timeToolsArrObj[0].html[i].html[j].text = '<select name = "'+lotteryToolsNameArr[j]+'"><option value = 1>周一</option><option value = 2>周二</option><option value = 3>周三</option><option value = 4>周四</option><option value = 5>周五</option><option value = 6>周六</option><option value = 7>周日</option></select></td>';
				}
				else if(lotteryToolsNameArr[j] == "startBettingWeekType" || lotteryToolsNameArr[j] == "createWeekType"){
					timeToolsArrObj[0].html[i].html[j].type = "td";
					timeToolsArrObj[0].html[i].html[j].type = "td";
					var str = [];
					str.push('<select name = "'+lotteryToolsNameArr[j]+'">');
					for(var l = 1 ; l <= 14 ; l++){
						str.push('<option value = '+l+' >'+weekArray[l]+'</option>');
					}
					str.push('</select>');
					timeToolsArrObj[0].html[i].html[j].text = str.join('');
					
					//timeToolsArrObj[0].html[i].html[j].text = '<select name = "'+lotteryToolsNameArr[j]+'"><option value = 1>周一</option><option value = 2>周二</option><option value = 3>周三</option><option value = 4>周四</option><option value = 5>周五</option><option value = 6>周六</option><option value = 7>周日</option></select></td>';
				}
				else{
					timeToolsArrObj[0].html[i].html[j].type = "td";
					timeToolsArrObj[0].html[i].html[j].text = '<input type="text" name = "' + lotteryToolsNameArr[j] + '"></td>';
				}
			}
		}
	}
	insertTableEle("lotteryTimeToolsTable", timeToolsArrObj);
}

function setLotteryTimeToolsTableToDay(){
	getEle("createPeriodType").value = 0;
	var lotteryToolsTitleArr = [ "官方開獎時間", "每期間隔時間(分)", "截止投注時間(秒）","跳開時間(分)", "停止對獎時間(分)", "期數" ];
	var lotteryToolsNameArr = [ "kjTime", "intervals", "stopBettingTime","jumpOffTime" , "giveUpKjTime", "issue" ];
	showSetLotteryTimeTools(lotteryToolsTitleArr,lotteryToolsNameArr);
}

function setLotteryTimeToolsTableToWeek(){
	getEle("createPeriodType").value = 1;
	var lotteryToolsTitleArr = [ "官方開獎時間", "截止投注時間(秒）","跳開時間(分)", "停止對獎時間(分)" ,"開獎日期(周)","開始下注日期(周)","開始下注時間","建立期號日期(周)"/*,"建立期號時間"*/];
	var lotteryToolsNameArr = [ "kjTime", "stopBettingTime","jumpOffTime", "giveUpKjTime","createWeekType","startBettingWeekType","startBettingTime","createPeriodWeekType"/*,"createPeriodTime"*/];
	showSetLotteryTimeTools(lotteryToolsTitleArr,lotteryToolsNameArr);
}

function insertLotterTimeyListAjax() {

	var lotteryToolsNameArr = [ "kjTime", "intervals", "stopBettingTime", "giveUpKjTime", "issue" , "createWeekType","createPeriodWeekType","createPeriodTime","jumpOffTime","startBettingWeekType","startBettingTime" ];
	var eleTr = getEle("lotteryTimeToolsTable").getElementsByTagName("tr");
	var jsonArr = [];
	for(var i = 0 ; i < eleTr.length ; i++){
		var eleInput = eleTr[i].getElementsByTagName("input");
		var obj = {};
		for(var j = 0 ; j < eleInput.length ; j++){
			if(lotteryToolsNameArr.indexOf(eleInput[j].name) >= 0){
				if(eleInput[j].type == "text"){
					if(eleInput[j].name == "intervals" || eleInput[j].name == "giveUpKjTime" || eleInput[j].name == "jumpOffTime"){
						var num = toInt(eleInput[j].value);
						if(num > 0){
							obj[eleInput[j].name] = num*60;
						}
						else{
							eleInput[j].value = "";
							obj = {};
							break;
						}
					}
					else if(eleInput[j].name == "kjTime" || eleInput[j].name == "createPeriodTime" || eleInput[j].name == "startBettingTime"){
						var val = checkTime(eleInput[j].value); 
						if(val != ""){
							var kjTimeArr = val.split(":");
							if (kjTimeArr.length == 3) {
								var h = toInt(kjTimeArr[0]) * 60 * 60;
								var m = toInt(kjTimeArr[1]) * 60;
								var s = toInt(kjTimeArr[2]) ;
								obj[eleInput[j].name] = h + m + s;
							}
							else if (kjTimeArr.length == 2) {
								var h = toInt(kjTimeArr[0]) * 60 * 60;
								var m = toInt(kjTimeArr[1]) * 60;
								obj[eleInput[j].name] = h + m;
							} else {
								var h = toInt(kjTimeArr[0]) * 60 * 60;
								obj[eleInput[j].name] = h;
							}
						}
						else{
							eleInput[j].value = "";
							obj = {};
							break;
						}
					}
					else{
						if(eleInput[j].value != "" ){
							obj[eleInput[j].name] = eleInput[j].value;
						}
						else{
							obj = {};
							break;
						}
						
					}
					
				}
				else if(eleInput[j].type == "checkbox"){
					if(eleInput[j].checked == true){
						if(typeof obj[eleInput[j].name] === "undefined" || obj[eleInput[j].name] == null){
							obj[eleInput[j].name] = 0;
						}
						obj[eleInput[j].name] = toInt(obj[eleInput[j].name]) + toInt(eleInput[j].value);
					}
				}
				else{
					if(eleInput[j].value != "" ){
						obj[eleInput[j].name] = eleInput[j].value;
					}
					else{
						obj = {};
						break;
					}
				}
			}
		}
		if(Object.keys(obj).length > 0){
			var eleSelect = eleTr[i].getElementsByTagName("select");
			for(var j2 = 0 ; j2 < eleSelect.length ; j2++){
				if(lotteryToolsNameArr.indexOf(eleSelect[j2].name) >= 0){
					obj[eleSelect[j2].name] = eleSelect[j2].value;
				}
			}
		
			jsonArr.push(obj);
		}
		
	}
	

	XHR_SetLotteryTime = checkXHR(XHR_SetLotteryTime);
	if (typeof XHR_SetLotteryTime != "undefined" && XHR_SetLotteryTime != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&data=" + JSON.stringify(jsonArr) + "&id=" + getEle("lotteryType").value + "&createPeriodType="+getEle("createPeriodType").value;
		XHR_SetLotteryTime.timeout = 10000;
		XHR_SetLotteryTime.ontimeout = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onerror = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onreadystatechange =  handleInsertLotterTimeyListAjax;
		XHR_SetLotteryTime.open("POST", "./SetLotteryTime!insertLotterTimeyList.php?date=" + getNewTime(), true);
		XHR_SetLotteryTime.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR_SetLotteryTime.send(tmpStr);
		enableLoadingPage();
	}
}

function handleInsertLotterTimeyListAjax() {
	if (XHR_SetLotteryTime.readyState == 4) {
		if (XHR_SetLotteryTime.status == 200) {
			try {
				if (isJSON(XHR_SetLotteryTime.responseText)) {
					var json = JSON.parse(XHR_SetLotteryTime.responseText);

				}
			} catch (error) {
				console_Log("handleGetLotteryTypeAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR_SetLotteryTime.abort();

				if (checkTokenIdfail(json)) {
					getEle("copyLotteryTimeJson").value = JSON.stringify(json);
					onClickCloseModalV2();
					showLotteryTimeList(getEle("copyLotteryTimeJson").value);
				}

			}
		} else {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
	}
}

function showCheckSaveLotteryTime() {
	if(getEle("lotteryTimeTable").getElementsByTagName("td").length == 0 || getEle("copyLotteryTimeJson").value == getEle("lotteryTimeJson").value){
		if(getEle("zodiac").value == getEle("zodiacSelect").value){
			return ;
		}
	}
	var str = [];
	str.push('<div class="modal-content width-percent-580 savesetting">		');
	str.push('	<span class="close" onclick="onClickCloseModalV2();">×</span>		');
	str.push('	<p>注意：相關內容已經修改，是否保存調整後內容？</p>		');
	str.push('	<div class="btn-area">		');
	str.push('		<button onclick="onClickCloseModalV2();">取消</button>		');
	str.push('		<button onclick="saveLotteryTimeAjax();">保存</button>		');
	str.push('	</div>		');
	str.push('</div>		');
	onClickOpenModalV2(str.join(''));
	
}

function saveLotteryTimeAjax(){
	onClickCloseModalV2();
	var zodiacType = getEle("zodiacSelect").value;
	var copyLotteryTimeJson = getEle("copyLotteryTimeJson").value;
	
	XHR_SetLotteryTime = checkXHR(XHR_SetLotteryTime);
	if (typeof XHR_SetLotteryTime != "undefined" && XHR_SetLotteryTime != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&lotteryTimeJson=" + copyLotteryTimeJson+"&zodiacType="+zodiacType;
		XHR_SetLotteryTime.timeout = 10000;
		XHR_SetLotteryTime.ontimeout = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onerror = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onreadystatechange =  handleSaveLotteryTimeAjax;
		XHR_SetLotteryTime.open("POST", "./SetLotteryTime!saveLotteryTime.php?date=" + getNewTime(), true);
		XHR_SetLotteryTime.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR_SetLotteryTime.send(tmpStr);
		enableLoadingPage();
	}
	
}

function handleSaveLotteryTimeAjax(){
	if (XHR_SetLotteryTime.readyState == 4) {
		if (XHR_SetLotteryTime.status == 200) {
			try {
				if (isJSON(XHR_SetLotteryTime.responseText)) {
					var json = JSON.parse(XHR_SetLotteryTime.responseText);
					if (checkTokenIdfail(json)) {
						if(json.result != null && typeof json.result != "undefined"){
							if(json.result == true){
								onCheckModelPage2("保存成功");
								onClickCloseModal();
							}
							else if(json.result == false){
								onCheckModelPage2("保存失敗!");
							}
						}
					}
				}
			} catch (error) {
				console_Log("handleGetLotteryTypeAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR_SetLotteryTime.abort();
			}
		} else {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
	}
}


function lotteryTimeSetLogAjax(){
	XHR_SetLotteryTime = checkXHR(XHR_SetLotteryTime);
	if (typeof XHR_SetLotteryTime != "undefined" && XHR_SetLotteryTime != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value);
		XHR_SetLotteryTime.timeout = 10000;
		XHR_SetLotteryTime.ontimeout = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onerror = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onreadystatechange = handleLotteryTimeSetLogAjax;
		XHR_SetLotteryTime.open("POST", "./SetLotteryTime!getLotteryTimeSetLog.php?date=" + getNewTime(), true);
		XHR_SetLotteryTime.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR_SetLotteryTime.send(tmpStr);
		enableLoadingPage();
	}
}


function handleLotteryTimeSetLogAjax(){
	if (XHR_SetLotteryTime.readyState == 4) {
		if (XHR_SetLotteryTime.status == 200) {
			try {
				if (isJSON(XHR_SetLotteryTime.responseText)) {
					var json = JSON.parse(XHR_SetLotteryTime.responseText);
					if (checkTokenIdfail(json)) {
						getEle("lotteryTimeSetLogList").value = XHR_SetLotteryTime.responseText;
						var lotteryTimeSetLogListStr = getEle("lotteryTimeSetLogList").value;
						if(isJSON(lotteryTimeSetLogListStr) && lotteryTimeSetLogListStr != ""){
							var lotteryTimeSetLogListJson = JSON.parse(lotteryTimeSetLogListStr).lotteryTimeSetLog;
							changePage_init(showLotteryTimeSetLog,lotteryTimeSetLogListJson.length,1,25,"LotteryTimeSetLogNowPage","LotteryTimeSetLogTotalPage");
							
//							showLotteryTimeSetLog();
						}
					}
				}
			} catch (error) {
				console_Log("handleGetLotteryTypeAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR_SetLotteryTime.abort();
			}
		} else {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
	}
}

function showLotteryTimeSetLog(){
	var lotteryTimeSetLogListStr = getEle("lotteryTimeSetLogList").value;
	if(isJSON(lotteryTimeSetLogListStr) && lotteryTimeSetLogListStr != ""){
		var lotteryTimeSetLogListJson = JSON.parse(getEle("lotteryTimeSetLogList").value).lotteryTimeSetLog;
		var titleArr = [ "操作者","操作內容", "操作時間", "操作IP", "詳細" ];
		var dataKeyArr = [ "opsAccName", "actionText", "opsDatetime", "ip", "actionId" ];
		if(getEle("lotteryTimeSetLogPage") == null){
			var str = [];
			
			str.push('<div class="modal-content width-percent-960 margin-fix-4" id = "lotteryTimeSetLogPage">		');
			str.push('<span class="close" onclick="onClickCloseModal();">×</span>		');
			str.push('<h3>彩票錄號操作日志</h3>		');
			str.push('	<div class="contentDiv2-12">	\n');
			str.push('		<table id = "lotteryOpsLogTable" class = "tr-hover"></table>	\n');
			str.push('	<p class="media-control text-center">	\n');
			str.push('	<span>一頁	\n');
			str.push('	<select id="selectLotteryOpsLogCount" onchange = "onChangeDataCountLimitSize(this.value);">	\n');
			str.push('	<option value="25">25筆</option>	\n');
			str.push('	<option value="50">50筆</option>	\n');
			str.push('	<option value="75">75筆</option>	\n');
			str.push('	<option value="100">100筆</option>	\n');
			str.push('	</select>	\n');
			str.push('	</span>	\n');
			str.push('	<a href="javascript:void(0);" onclick="onClickFirstPage();" class="backward"></a>	\n');
			str.push('	<a href="javascript:void(0);" onclick="onClickPreviousPage();" class="backward-fast"></a>	\n');
			str.push('	<span>總頁數：<i id="LotteryTimeSetLogNowPage">1</i></span><span>/</span>	\n');
			str.push('	<i id="LotteryTimeSetLogTotalPage">1</i>頁<a href="javascript:void(0);" onclick="onClickNextPage();" class="forward"></a>	\n');
			str.push('	<a href="javascript:void(0);" onclick="onClickLastPage();" class="forward-fast"></a></p>	\n');
	
			str.push('	</div>	\n');
			str.push('	</div>	\n');
			
			onClickOpenModal(str.join(''));
		}
		
		getEle("lotteryOpsLogTable").innerHTML = "";
		
		var tableObj = [];

		
		var x = titleArr.length;
		var startCount = toInt(getEle("startCount").value);
		var endCount = toInt(getEle("endCount").value);
		
		
		var obj = {};
		tableObj[0] = {};
		tableObj[0].type = "tbody";
		tableObj[0].html = [];
		for (var i = startCount; i < endCount+1; i++) {
			tableObj[0].html[i] = {};
			tableObj[0].html[i].type = "tr";
			tableObj[0].html[i].html = [];
			for (var j = 0; j < x; j++) {
				tableObj[0].html[i].html[j] = {};
				if (i == startCount) {
					tableObj[0].html[i].html[j].type = "th";
					tableObj[0].html[i].html[j].text = titleArr[j];
				} else {
					if(typeof lotteryTimeSetLogListJson[i-1][dataKeyArr[j]] === "undefined" || lotteryTimeSetLogListJson[i-1][dataKeyArr[j]] == null){
						tableObj[0].html[i].html[j].type = "td";
						tableObj[0].html[i].html[j].text = "";
					}
					else if(dataKeyArr[j] == "actionId"){
						var actionText = typeof lotteryTimeSetLogListJson[i-1]["actionText"] !== "undefined" ? lotteryTimeSetLogListJson[i-1]["actionText"] : "";
						tableObj[0].html[i].html[j].type = "td";
						tableObj[0].html[i].html[j].text = '<a href = "javascript:void(0);" onclick = "showLotteryTimeSetDetailed(\''+actionText+'\',\''+i+'\')">詳細</a>';
						
						obj[i] = lotteryTimeSetLogListJson[i-1].detail.toString();
					}
					else{
						tableObj[0].html[i].html[j].type = "td";
						tableObj[0].html[i].html[j].text = lotteryTimeSetLogListJson[i-1][dataKeyArr[j]];
					}
				}
			}
		}
		getEle("lotteryDetailData").value = JSON.stringify(obj);
		insertTableEle("lotteryOpsLogTable", tableObj);
		
	}
}

function showLotteryTimeSetDetailed(actionText,opsId){
	var lotteryDetailData = getEle("lotteryDetailData").value;
	if(isJSON(lotteryDetailData)){
		var lotteryDetailDataJSON = JSON.parse(lotteryDetailData);
		if(typeof lotteryDetailDataJSON[opsId] != "undefined" && isJSON(lotteryDetailDataJSON[opsId])){
			var json = JSON.parse(lotteryDetailDataJSON[opsId]);
			var titleArr = ["期號","官方開獎時間","停止下注時間(秒)","平台開獎時間","放棄開獎時間","跳開時間","開獎日期(周)","建立期號日期(周)","建立期號時間","開始注日期(周)","開始下注時間","生肖","期號類型","操作類型"];
			var dataKeyArr = [ "issue", "kjTime", "stopBettingTime", "platformKjTime", "giveUpKjTime","jumpOffTime","createWeekType","createPeriodWeekType","createPeriodWeekTime","startBettingWeekType","startBettingTime","zodiacType","type","types" ];
			var lotteryTimeSetLogTypeArr = ["修改時間設定","減少期數","增加期數"];
			var tableObj = [];
			var str = [];
			str.push('<div class="modal-content width-percent-60">');
			str.push('	<span class="close" onclick="onClickCloseModalV2();">×</span>');
			str.push('	<h3>'+actionText+'</h3>');
			str.push('	<table id = "lotteryLogTable" class = "tr-hover"></table>');
			str.push('</div>');
			
			onClickOpenModalV2(str.join(''));
			
			
			var jsonDataKey = sortLotteryTimeSetJSONKey(Object.keys(json));
			
			var x = titleArr.length;
			var endCount = jsonDataKey.length;
			
			var obj = {};
			tableObj[0] = {};
			tableObj[0].type = "tbody";
			tableObj[0].html = [];
			for (var i = 0; i < endCount+1; i++) {
				tableObj[0].html[i] = {};
				tableObj[0].html[i].type = "tr";
				tableObj[0].html[i].html = [];
				
				for (var j = 0; j < x; j++) {
					tableObj[0].html[i].html[j] = {};
					if(i == 0){
						tableObj[0].html[i].html[j].type = "th";
						tableObj[0].html[i].html[j].text = titleArr[j];
					}
					else{
						if(jsonDataKey[i-1].indexOf("_") >= 0){
							var keyArr = jsonDataKey[i-1].split("_");
							if(keyArr.length == 2){
								var typeNum = toInt(keyArr[0]);
								if(dataKeyArr[j] == "types"){
									tableObj[0].html[i].html[j].type = "td";
									tableObj[0].html[i].html[j].text = lotteryTimeSetLogTypeArr[typeNum];
								}
								else if(dataKeyArr[j] == "issue"){
									tableObj[0].html[i].html[j].type = "td";
									tableObj[0].html[i].html[j].text = toInt(keyArr[1]);
								}
								else if(typeof json[jsonDataKey[i-1]][dataKeyArr[j]] !== "undefined"){
									if(dataKeyArr[j] == "stopBettingTime" || dataKeyArr[j] == "issue"){
										if(typeNum == 0 && typeof json[jsonDataKey[i-1]][dataKeyArr[j]]["0"] != "undefined" && typeof json[jsonDataKey[i-1]][dataKeyArr[j]]["1"] != "undefined" ){
											tableObj[0].html[i].html[j].type = "td";
											tableObj[0].html[i].html[j].text = json[jsonDataKey[i-1]][dataKeyArr[j]]["1"]+" => "+json[jsonDataKey[i-1]][dataKeyArr[j]]["0"];
										}
										else{
											tableObj[0].html[i].html[j].type = "td";
											tableObj[0].html[i].html[j].text = json[jsonDataKey[i-1]][dataKeyArr[j]];
										}
									}
									else if(dataKeyArr[j] == "createWeekType" || dataKeyArr[j] =="createPeriodWeekType" || dataKeyArr[j] == "startBettingWeekType"){
										if(typeNum == 0 && typeof json[jsonDataKey[i-1]][dataKeyArr[j]]["0"] != "undefined" && typeof json[jsonDataKey[i-1]][dataKeyArr[j]]["1"] != "undefined" ){
											tableObj[0].html[i].html[j].type = "td";
											tableObj[0].html[i].html[j].text = weekArray[toInt(json[jsonDataKey[i-1]][dataKeyArr[j]]["1"])]+" => "+weekArray[toInt(json[jsonDataKey[i-1]][dataKeyArr[j]]["0"])];
										}
										else{
											tableObj[0].html[i].html[j].type = "td";
											tableObj[0].html[i].html[j].text = weekArray[toInt(json[jsonDataKey[i-1]][dataKeyArr[j]])];
										}
									}
									else if(dataKeyArr[j] == "type"){
										if(typeNum == 0 && typeof json[jsonDataKey[i-1]][dataKeyArr[j]]["0"] != "undefined" && typeof json[jsonDataKey[i-1]][dataKeyArr[j]]["1"] != "undefined" ){
											tableObj[0].html[i].html[j].type = "td";
											tableObj[0].html[i].html[j].text = typeArray[toInt(json[jsonDataKey[i-1]][dataKeyArr[j]]["1"])]+" => "+typeArray[toInt(json[jsonDataKey[i-1]][dataKeyArr[j]]["0"])];
										}
										else{
											tableObj[0].html[i].html[j].type = "td";
											tableObj[0].html[i].html[j].text = typeArray[toInt(json[jsonDataKey[i-1]][dataKeyArr[j]])];
										}
									}
									else if(dataKeyArr[j] == "zodiacType"){
										if(typeNum == 0 && typeof json[jsonDataKey[i-1]][dataKeyArr[j]]["0"] != "undefined" && typeof json[jsonDataKey[i-1]][dataKeyArr[j]]["1"] != "undefined" ){
											tableObj[0].html[i].html[j].type = "td";
											tableObj[0].html[i].html[j].text = zodiacArray[toInt(json[jsonDataKey[i-1]][dataKeyArr[j]]["1"])]+" => "+zodiacArray[toInt(json[jsonDataKey[i-1]][dataKeyArr[j]]["0"])];
										}
										else{
											tableObj[0].html[i].html[j].type = "td";
											tableObj[0].html[i].html[j].text = zodiacArray[toInt(json[jsonDataKey[i-1]][dataKeyArr[j]])];
										}
									}
									else{
										if(typeNum == 0 && typeof json[jsonDataKey[i-1]][dataKeyArr[j]]["0"] != "undefined" && typeof json[jsonDataKey[i-1]][dataKeyArr[j]]["1"] != "undefined" ){
											tableObj[0].html[i].html[j].type = "td";
											tableObj[0].html[i].html[j].text = toTime(json[jsonDataKey[i-1]][dataKeyArr[j]]["1"])+" => "+toTime(json[jsonDataKey[i-1]][dataKeyArr[j]]["0"]);
										}
										else{
											tableObj[0].html[i].html[j].type = "td";
											tableObj[0].html[i].html[j].text = toTime(json[jsonDataKey[i-1]][dataKeyArr[j]]);
										}
									}
								}
								else{
									tableObj[0].html[i].html[j].type = "td";
									tableObj[0].html[i].html[j].text = "--";
								}
							}
							else{
								tableObj[0].html[i].html[j].type = "td";
								tableObj[0].html[i].html[j].text = "--";
							}
						}
					}
				}
			}
			
			insertTableEle("lotteryLogTable", tableObj);
		}
	}
}

function sortLotteryTimeSetJSONKey(keyArr){
	var arr = [];
	var obj = {};
	for(var val in keyArr){
		if(keyArr[val].indexOf("_") >= 0){
			var dataArr = keyArr[val].split("_");
			if(dataArr.length == 2){
				arr.push(toInt(dataArr[1]));
				obj[toInt(dataArr[1])] = keyArr[val];
			}
		}		
	}
	arr = arr.sort(function(a,b){return a-b;});
	var newArr = [];
	for(var v in arr){
		newArr.push(obj[arr[v]]);
	}
	return newArr;
}


