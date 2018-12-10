var isShowConsole = true;
var isCloseWindow = false;
var timeOutMinute = 3;

//Ajax
var createXMLHttpRequest = function() {
	var XHR = null;
	try {
		XHR = new XMLHttpRequest();
	} catch (e1) {
		try {
			if (!XHR) {
				XHR = new ActiveXObject("Microsoft.XMLHTTP");
			}
		} catch (e2) {
			try {
				if (!XHR) {
					XHR = new ActiveXObject("Msxml2.XMLHTTP");
				}
			} catch (e3) {
			}
		}
	}
	if (!XHR) {
		alert("NO AJAX, Please change your browser. ");
		return;
	}
	return XHR;
}

//Full Screen function
var cancelFullScreen = function(el) {
	requestMemAction("zoomOut", "");
	if (checkIE()) {
		if (window['ActiveXObject'] ? true : "ActiveXObject" in window ? true : false) {
			// IEQQ.
			var wscript = new ActiveXObject("WScript.Shell");
			if (wscript !== null) {
				wscript.SendKeys("{F11}");
			}
		}
	} else {
		var requestMethod = el.cancelFullScreen || el.webkitCancelFullScreen || el.mozCancelFullScreen || el.exitFullscreen;
		if (requestMethod) { // cancel full screen.
			requestMethod.call(el);
		} else if (window['ActiveXObject'] ? true : "ActiveXObject" in window ? true : false) {
			// } else if(typeof window.ActiveXObject !== "undefined"){ // Older
			// IE.
			var wscript = new ActiveXObject("WScript.Shell");
			if (wscript !== null) {
				wscript.SendKeys("{F11}");
			}
		}
	}
}

var requestFullScreen = function(el) {
	requestMemAction("zoomIn", "");
	if (checkIE()) {
		if (window['ActiveXObject'] ? true : "ActiveXObject" in window ? true : false) {
			// IEQQ.
			var wscript = new ActiveXObject("WScript.Shell");
			if (wscript !== null) {
				wscript.SendKeys("{F11}");
			}
		}
	} else {
		var requestMethod = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullscreen;
		if (requestMethod) { // Native full screen.
			requestMethod.call(el);
		} else if (window['ActiveXObject'] ? true : "ActiveXObject" in window ? true : false) {
			// } else if (typeof window.ActiveXObject !== "undefined") { //
			// Older
			// IE.
			var wscript = new ActiveXObject("WScript.Shell");
			if (wscript !== null) {
				wscript.SendKeys("{F11}");
			}
		}
	}
	return false;
}

var toggleFull = function() {
	var elem = document.body; // Make the body go full screen.
	var isInFullScreen = (document.fullScreenElement && document.fullScreenElement !== null) || (document.mozFullScreen || document.webkitIsFullScreen);
	if ((screen.availHeight <= document.documentElement.clientHeight) || (screen.height - 30 <= document.documentElement.clientHeight)) {
		isInFullScreen = true;
	}
	if (isInFullScreen) {
		document.getElementById("fullScreenId").title = "全螢幕";
		cancelFullScreen(document);
	} else {
		document.getElementById("fullScreenId").title = "縮小視窗，按ESC也可縮小視窗";
		if(checkIE()){
            requestFullScreen(document);
		}
		else{
            requestFullScreen(elem);
		}

	}
	return false;
}

//Json
var isJSON = function(str) {
	if (typeof str == 'string') {
		try {
			JSON.parse(str);
			return true;
		} catch (e) {
			console_Log(e);
			return false;
		}
	}
	console_Log('It is not a string!')
}

//IE

var isIE = function(ver) {
	var b = document.createElement('b')
	b.innerHTML = '<!--[if IE ' + ver + ']><i></i><![endif]-->'
	return b.getElementsByTagName('i').length === 1
}

var checkIEQQ = function() {
	var nAgt = navigator.userAgent;
	var browserName = navigator.appName;
	//console_Log("nAgt : " + nAgt + ", browserName : " + browserName);
	if (browserName.toLowerCase().indexOf("netscape") != -1 && nAgt.toLowerCase().indexOf("qqbrowser") != -1) {
		return true;
	}
	return false;
}

var DetectIsIE = function() {
	var ua = window.navigator.userAgent;

	var msie = ua.indexOf('MSIE ');
	if (msie > 0) {
		// 回傳版本 <=10 的版本
		return parseInt(ua.substring(msie + 5, ua.indexOf('.', msie)), 10);
	}

	var trident = ua.indexOf('Trident/');
	if (trident > 0) {
		// 回傳版本 >=11 的版本
		var rv = ua.indexOf('rv:');
		return parseInt(ua.substring(rv + 3, ua.indexOf('.', rv)), 10);
	}

	var edge = ua.indexOf('Edge/');
	if (edge > 0) {
		// 判斷Edge
		return "Edge";
		// return parseInt(ua.substring(edge + 5, ua.indexOf('.', edge)), 10);
	}

	// other browser
	return false;
}

//Log
var console_Log = function(msg) {
	if(isShowConsole == true){
		if (window.console) {
			console.log(msg);
		}
	}
}

//getElement
//getElementById && getElementsByName
var getEle = function(name, row){
	ele = document.getElementById(name);
	if(ele==null || (row!=undefined && row!=null && !isNaN(Number(row)))){
		if(ele!=null && document.getElementsByName(name).length==0){
			return ele;
		}
		ele = document.getElementsByName(name);
		if(row==undefined || row==null || isNaN(Number(row))){
			row = 0;
		}else{
			row = Number(row);
		}
		if(ele.length>0){
			if(row>=ele.length){
				row = ele.length-1;
			}
			if(row<0){
				row = 0;
			}
			return ele[row];
		}else{
			return null;
		}
	}else{
		if(ele==undefined || ele==null){
			return null;
		}
		return ele;
	}
	return null;
}
// getElementsByClassName
var getEleCN = function(name, row, element){
	ele = document.getElementsByClassName(name);
	if(element!=undefined && element!=null){
		try{
			ele = element.getElementsByClassName(name);
		}catch(err){
			return null;
		}
		if(ele==undefined || ele==null){
			return null;
		}
	}
	if(ele.length>0){
		if(row==undefined || row==null || isNaN(Number(row))){
			row = 0;
		}else{
			row = Number(row);
		}
		if(row>=ele.length){
			row = ele.length-1;
		}
		if(row<0){
			row = 0;
		}
		return ele[row];
	}else{
		return null;
	}
	return null;
}
// getElementsByTagName
var getEleTN = function(name, row, xmlDoc){
	ele = document.getElementsByTagName(name);
	if(xmlDoc!=undefined && xmlDoc!=null){
		try{
			ele = xmlDoc.getElementsByTagName(name);
		}catch(err){
			return null;
		}
		if(ele==undefined || ele==null){
			return null;
		}
	}
	if(ele.length>0){
		if(row==undefined || row==null || isNaN(Number(row))){
			row = 0;
		}else{
			row = Number(row);
		}
		if(row>=ele.length){
			row = ele.length-1;
		}
		if(row<0){
			row = 0;
		}
		return ele[row];
	}else{
		return null;
	}
	return null;
}

//close window
function closeSelf(){
	if(isCloseWindow == true){
		window.opener = null;
		window.close();
	}
}

function checkTimeOut(lastActionTime){
	var now = new Date();
	if(now.getTime() - lastActionTime > timeOutMinute*60*1000){
		return true;
	}
	return false;
}

var checkIE = function() {
	var nAgt = navigator.userAgent;
	var browserName = navigator.appName;
	//console_Log("nAgt : " + nAgt + ", browserName : " + browserName);
	if(nAgt.toLowerCase().indexOf("chrome") != -1){
		return false;
	//}else if(nAgt.toLowerCase().indexOf("rv:11") != -1){
	//	return false;
	}else if (browserName.toLowerCase().indexOf("netscape") != -1 && nAgt.toLowerCase().indexOf("qqbrowser") != -1) {
		return true;
	}else if ((nAgt.toLowerCase().indexOf("trident") != -1) && (nAgt.toLowerCase().indexOf("rv:11") == -1)){
		return true;
	}
	return false;
}


function removeAllOption(cName) {
	while (getEle(cName).options.length > 0) {
		getEle(cName).remove(0);
	}
}
function removeOption(cName, row) {// 0-n
	while (getEle(cName).options.length - 1 >= row) {
		getEle(cName).remove(row);
	}
}
function addOption(cName, text, value) {
	getEle(cName).options[getEle(cName).options.length] = new Option(text,
			value);
}
function addOptionNoDup(cName, text, value) {
	if (getEle(cName) != null) {
		for (var i = 0; i < getEle(cName).options.length; i++) {
			if (getEle(cName).options[i].text == text
					|| getEle(cName).options[i].value == value) {
				return false;
			}
		}
	}
	getEle(cName).options[getEle(cName).options.length] = new Option(text,
			value);
	return true;
}

function checkInputTextVal(val){
	var result = "";
	for (var i = 0; i < val.length; i++) {
		if (checkTextVal(val.substring(i, i + 1))) {
			result += val.substring(i, i + 1);
		} else {
			break;
		}
	}
	return 	result;
}
function checkInputPassWordVal(val){
	var result = "";
	for (var i = 0; i < val.length; i++) {
		if (checkPassWordVal(val.substring(i, i + 1))) {
			result += val.substring(i, i + 1);
		} else {
			break;
		}
	}
	return 	result;
}
function checkInputNumberVal(val){
	var result = "";
	for (var i = 0; i < val.length; i++) {
		if (checkNumberVal(val.substring(i, i + 1))) {
			result += val.substring(i, i + 1);
		} else {
			break;
		}
	}
	return 	result;
}
function checkInputDecimalVal(val){
	var result = "";
	for (var i = 0; i < val.length; i++) {
		if (checkDecimalVal(val.substring(i, i + 1))) {
			result += val.substring(i, i + 1);
		} else {
			break;
		}
	}
	return 	result;
}
function checkTextVal(str) {
	var regExp = /^[\d|a-zA-Z]+$/;
	if (regExp.test(str))
		return true;
	else
		return false;
}
function checkPassWordVal(str) {
	var regExp = /^[\x21-\x7E]+$/;
	if (regExp.test(str))
		return true;
	else
		return false;
}
function checkNumberVal(str) {
	var regExp = /^[\d]+$/;
	if (regExp.test(str))
		return true;
	else
		return false;
}
function checkEnglishVal(str) {
	var regExp = /^[a-zA-Z]+$/;
	if (regExp.test(str))
		return true;
	else
		return false;
}
function checkDecimalVal(str){
	var regExp =  /^[0-9]+(\.[0-9]{1,2})?$/;
	if (regExp.test(str))
		return true;
	else
		return false;
}

function onCheckModelPage(str ,functionName){
	var fName = functionName;
	if(fName == null || fName == undefined || fName == ""){
		fName = "onCheckCloseModelPage()";
	}
	var text = '<div class="modal-content width-percent-20">'+
				'<span class="close" onclick="onCheckCloseModelPage();">×</span>'+
			    '<h3 class="text-center">'+str+'</h3>'+
			    '<div class="btn-area">'+
			    	'<button class="btn-lg btn-orange"onclick = '+fName+'>確定</button>'+
			    	'<button class="btn-lg btn-gray" onclick="onCheckCloseModelPage();">取消</button>'+
			   ' </div></div>';
	getEle("myModalV2").innerHTML = text;
	getEle("myModalV2").style.display = "block";
}

function onCheckCloseModelPage(){
	getEle("myModalV2").innerHTML = "";
	getEle("myModalV2").style.display = "none";
}

function onCheckModelPage2(str){
	var text = '<div class="modal-content width-percent-20">'+
				'<span class="close" onclick="onCheckCloseModelPage();">×</span>'+
			    '<h3 class="text-center">'+str+'</h3>'+
			    '<div class="btn-area">'+
			    	'<button class="btn-lg btn-orange"onclick = "onCheckCloseModelPage();">確定</button>'+
			   ' </div></div>';
	getEle("myModalV2").innerHTML = text;
	getEle("myModalV2").style.display = "block";
}

function selectItemByValue(cName, value){
	elmnt = getEle(cName);
	for(var i=0; i < elmnt.options.length; i++)
	{
	  if(elmnt.options[i].value == value)
	    elmnt.selectedIndex = i;
	}
}