var isShowConsole = true;
var isCloseWindow = false;
var timeOutMinute = 3;

var td=new Date();
var year=td.getFullYear();
var month=td.getMonth()+1;

// Ajax
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
		return false;
	}
	return XHR;
}

// Full Screen function
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

// Json
var isJSON = function(str) {
	if (typeof str == 'string' && str != "") {
		try {
			return JSON.parse(str);
		} catch (e) {
			console_Log(e);
			return false;
		}
	}else{
		return false;
	}
	console_Log('It is not a string!')
}

// IE

var isIE = function(ver) {
	var b = document.createElement('b')
	b.innerHTML = '<!--[if IE ' + ver + ']><i></i><![endif]-->'
	return b.getElementsByTagName('i').length === 1
}

var checkIEQQ = function() {
	var nAgt = navigator.userAgent;
	var browserName = navigator.appName;
	// console_Log("nAgt : " + nAgt + ", browserName : " + browserName);
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

// Log
var console_Log = function(msg) {
	if(isShowConsole == true){
		if (window.console) {
			console.log(msg);
		}
	}
}

// getElement
// getElementById && getElementsByName
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

// close window
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
	// console_Log("nAgt : " + nAgt + ", browserName : " + browserName);
	if(nAgt.toLowerCase().indexOf("chrome") != -1){
		return false;
	// }else if(nAgt.toLowerCase().indexOf("rv:11") != -1){
	// return false;
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
function checkAccount(str){
	console_Log(str);
	var patt = new RegExp("^[a-zA-Z][a-zA-Z0-9]*$");
	console_Log("str.length="+str.length);
	if(str.length == 1){
		patt = new RegExp("^[a-zA-Z]$");
	}
	if(patt.test(str)){
		console_Log("true");
		return str;
	}else{
		console_Log("false");
		return "";
	}
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
function checkInputDecimalVal(val) {
	var result = [];
	var decimalStr = "";
	val = toString(val);
	var numberArr = val.split(".");
	for(var i = 0 ; i < numberArr.length && i < 2 ; i++){
		for(var j = 0 ; j < numberArr[i].length ; j++){
			if (checkNumberVal(numberArr[i].substring(j, j + 1))) {
				if(result[i] == undefined && typeOf(result[i]) === "undefined"){
					result[i] = numberArr[i].substring(j, j + 1);
				}
				else {
					result[i] += numberArr[i].substring(j, j + 1);
				}
			}
			else{
				break;
			}
		}
	}
	if(result.length == 2){
		result[0] = toInt(result[0]);
		decimalStr = result.join(".");
		if(result[1].length > 2){
			while(checkDecimalVal(decimalStr) == false){
				decimalStr = decimalStr.substring(0,decimalStr.length-1);
			}
		}
	}
	else if(result.length == 1){
		if(numberArr.length >= 2){
			decimalStr = toInt(result[0])+".";
		}
		else{
			decimalStr = toInt(result[0]);
		}
	}
	return decimalStr;
}
function checkBankAccInputNumberVal(val){
	var result = "";
	for (var i = 0; i < val.length; i++) {
		if (checkBankAccVal(val.substring(i, i + 1))) {
			result += val.substring(i, i + 1);
		} else {
			break;
		}
	}
	return 	result;
}
function checkBankAccInputDateVal(val){
	var result = "";
	for (var i = 0; i < val.length; i++) {
		if (checkBankDateVal(val.substring(i, i + 1))) {
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
function checkBankAccVal(str) {
	var regExp = /^[0-9-]+$/;
	if (regExp.test(str))
		return true;
	else
		return false;
}
function checkBankDateVal(str) {
	var regExp = /^[0-9/-]+$/;
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

function selectItemByValue(elmnt, value){
	for(var i=0; i < elmnt.options.length; i++)
	{
	  if(elmnt.options[i].value == value)
	    elmnt.selectedIndex = i;
	}
}
function getHiddenInput(name){
	if(document.getElementsByName(name).length ==  0){
		return "";
	}
	return document.getElementsByName(name)[0].value;
}

function setHiddenInput(name, val){
	if(document.getElementsByName(name).length ==  0){
		var tmpInput = document.createElement("input");
		tmpInput.type = 'hidden';
		tmpInput.name= name;
		document.body.appendChild(tmpInput);
	}
	document.getElementsByName(name)[0].value = val;
}
function chkInputBankAcc(str) {
	if (str == null || str.trim().length == 0) {
		return "";
	} else {
		str = str.trim();
		var tmpStr = "";
		for (var i = 0; i < str.length; i++) {
			var tmpC = str.substring(i, i + 1);
			if (tmpStr != ""
					&& tmpStr.substring(tmpStr.length - 1, tmpStr.length) != "-"
					&& tmpC == "-") {
				tmpStr += tmpC;
			}
			if (/^\d/.test(tmpC)) {
				tmpStr += tmpC;
			}
		}
		return tmpStr;
	}
}

function onchgInputBankAcc(str) {
	str = chkInputBankAcc(str);
	if (str.substring(str.length - 1, str.length) == "-") {
		str = str.substring(0, str.length - 1);
	}
	return str;
}
function checkXHR(xhr){
	if (typeof xhr === "undefined" || xhr == null) {
		xhr = null;
		xhr = createXMLHttpRequest();
	}
	if(xhr.status == 0 && xhr.readyState == 0){
		return xhr;
	}
	if(xhr.status == 200 && xhr.readyState == 4){
		xhr.abort();
		return xhr;
	}
	if(xhr.readyState==1){
		xhr.timeout = 1000;
		for(var i=0; i<10000000;i++){
			if(xhr.readyState!=1 || xhr.status!=0){
				break;
			}
		}
	}
	if(xhr.readyState==1){
		xhr.abort();
	}
	return xhr;
}
function copyStr(s) {
	var clip_area = document.createElement('textarea');
	clip_area.textContent = s;
	document.body.appendChild(clip_area);
	clip_area.select();
	document.execCommand('copy');
	clip_area.remove();
	alert("複製成功");
}


function isSortType(val) {
    if (typeOf(val) === "number" || typeOf(val) === "boolean" || typeOf(val) === "string" || typeOf(val) === "date" || typeOf(val) === "object") {
        return true;
    } else {
        return false;
    }
}
function checkJsonKeys (obj = [], keys = []) {
    if (arguments.length == 0) {
        console.log("ERROR:checkJsonKeys: No arguments!");
        return [];
    } else if (arguments.length == 1) {
        keys = [];
    } else if (arguments.length > 2) {
        console.log("ERROR:checkJsonKeys: Too many arguments! " + arguments.length);
    }
    try {
        var tmpJson;
        if (typeOf(obj) === "null" || typeOf(obj) === "undefined") {
            console.log("ERROR:checkJsonKeys: It is " + typeOf(obj) + "!");
            return [];
        }
        tmpJson = obj.slice();
        if (typeOf(keys) === "null" || typeOf(keys) === "undefined" || keys == [] || Object.keys(keys).length == 0) {
            keys = [];
            for (var i = 0; i < tmpJson.length; i++) {
                if(i == 0){
                    keys = Object.keys(tmpJson[i]);
                } else {
                    var tmpKeys = Object.keys(tmpJson[i]);
                    for (var j = 0; j < tmpKeys.length; j++) {
                        if(keys.indexOf(tmpKeys[j]) == -1){
                            keys[keys.length] = tmpKeys[j];
                        }
                    }
                    tmpKeys = null;
                    delete tmpKeys;
                }
            }
        }
        if (typeOf(obj) === "array" && typeOf(keys) === "array") {
            for (var i=0; i < tmpJson.length; i++) {
                for (var j=0; j < keys.length; j++) {
                    if (typeOf(tmpJson[i][keys[j]]) === "undefined" || typeOf(tmpJson[i][keys[j]]) === "null") {
                        tmpJson[i][keys[j]] = null;
                    }
                }
            }
            return tmpJson;
        } else {
            console.log("ERROR:checkJsonKeys: type error!");
            return [];
        }
    } catch (error) {
        console.log("ERROR:checkJsonKeys:" + error.name + ":" + error.message);
        tmpJson = null;
        delete tmpJson;
        for (var i = 0; i < arguments.length; i++) {
            console.log((i + 1) + ": TYPE:" + typeOf(arguments[i]) + ",VALUE:" + arguments[i]);
        }
        return [];
    }
}
var jsonSortByProperty = function (property = 0, asc = true) {
    if (arguments.length == 0) {
        property = 0;
        asc = true;
    } else if (arguments.length == 1) {
        asc = true;
    } else if (arguments.length > 2) {
        console.log("ERROR:jsonSortByProperty: Too many arguments! " + arguments.length);
    }
    return function (x, y) {
        try {
            if (typeOf(asc) != "boolean") {
                if (typeOf(asc) === "string" || typeOf(asc) === "number") {
                    var tmpInt;
                    try {
                        if(("" + asc).toLowerCase() === "true") {
                            asc = 1;
                        } else if(("" + asc).toLowerCase() === "false") {
                            asc = 0;
                        }
                        var tmpInt = toInt(asc);
                        if(isNumber(tmpInt)){
                            if (tmpInt == 0) {
                                asc = false;
                            } else if (tmpInt == 1) {
                                asc = true;
                            } else {
                                console.log("ERROR:jsonSortByProperty:asc type error!\nTYPE:" + typeOf(asc) + ",VALUE:" + asc);
                                asc = true;
                            }
                        }
                    } catch (err) {
                        console.log("ERROR:jsonSortByProperty:parseBoolean:" + err.name + ":" + err.message);
                        return 0;
                    } finally {
                        tmpInt = 0;
                        tmpInt = null;
                        delete tmpInt;
                    }
                } else {
                    console.log("ERROR:jsonSortByProperty:asc type error!\nTYPE:" + typeOf(asc) + ",VALUE:" + asc);
                    asc = true;
                }
            }
            if (typeOf(property) === "number") {
                if (!isNumber(property) || isNaN(parseInt("" + property)) == true || !isFinite(parseInt("" + property))) {
                    console.log("ERROR:jsonSortByProperty: The index of key is not Integer!" + property);
                    return 0;
                } else {
                    property = parseInt(parseInt("" + property), 10);
                }
                var maxLen = 0;
                if(typeOf(x) === "map") {
                    maxLen = Object.keys(x).length;
                }
                if(typeOf(y) === "map" && Object.keys(y).length > maxLen) {
                    maxLen = Object.keys(y).length;
                }
                if(property >= maxLen) {
                    property = maxLen - 1;
                }
                maxLen = 0;
                maxLen = null;
                delete maxLen;
                if(property < 0) {
                    property = 0;
                }
                if( (typeOf(Object.keys(x)[property]) === "undefined" || typeOf(Object.keys(x)[property]) === "null") 
                        && (typeOf(Object.keys(y)[property]) === "undefined" || typeOf(Object.keys(y)[property]) === "null") ) {
                    return 0;
                } else if (typeOf(Object.keys(x)[property]) === "undefined" || typeOf(Object.keys(x)[property]) === "null") {
                    if (asc == true) {
                        return -1;
                    } else {
                        return 1;
                    }
                } else if (typeOf(Object.keys(y)[property]) === "undefined" || typeOf(Object.keys(y)[property]) === "null") {
                    if (asc == true) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else {
                    property = Object.keys(x)[property];
                }
            } else if(typeOf(property) === "string") {
                if (Object.keys(x).indexOf(property) == -1 && Object.keys(y).indexOf(property) == -1) {
                    return 0;
                } else if (Object.keys(x).indexOf(property) == -1) {
                    if (asc == true) {
                        return -1;
                    } else {
                        return 1;
                    }
                } else if (Object.keys(y).indexOf(property) == -1) {
                    if (asc == true) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            } else {
                return 0;
            }
            if (!isSortType(x[property]) && !isSortType(y[property])) {
                return 0;
            } else if(!isSortType(x[property])) {
                if (asc == true) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (!isSortType(y[property])) {
                if (asc == true) {
                    return 1;
                } else {
                    return -1;
                }
            }
            if ((typeOf(x[property]) === "string" && typeOf(y[property]) === "string") || typeOf(x[property]) != typeOf(y[property])) {
                if (asc == true) {
                    return ((x[property].toString() === y[property].toString()) ? 0 : (x[property].toString()).localeCompare(y[property].toString()));
                } else {
                    return ((x[property].toString() === y[property].toString()) ? 0 : (y[property].toString()).localeCompare(x[property].toString()));
                }
            } else {
                if (asc == true) {
                    return ((x[property] === y[property]) ? 0 : ((x[property] > y[property]) ? 1 : -1));
                } else {
                    return ((x[property] === y[property]) ? 0 : ((x[property] < y[property]) ? 1 : -1));
                }
            }
        } catch (error) {
            console.log("ERROR:jsonSortByProperty:" + error.name + ":" + error.message);
            for(var i = 0; i < arguments.length; i++) {
                console.log((i + 1) + ": TYPE:" + typeOf(arguments[i]) + ",VALUE:" + arguments[i]);
            }
            return 0;
        } finally {
            
        }
    };
};
function jsonDataSort (tmpJson = [], col = 0, asc = true) {
    if (arguments.length == 0) {
        console.log("ERROR:jsonDataSort: No arguments!");
        return [];
    } else if (arguments.length == 1) {
        col = 0;
        asc = true;
    } else if (arguments.length == 2) {
        asc = true;
    } else if (arguments.length > 3) {
        console.log("ERROR:jsonDataSort: Too many arguments! " + arguments.length);
    }
    var tmpJson1;
    try {
        if (typeOf(tmpJson) === "undefined" || typeOf(tmpJson) === "null") {
            console.log("ERROR:jsonDataSort: It is " + typeOf(tmpJson) + "!");
            throw (new Error("1"));
        }
        tmpJson1 = [];
        if (typeOf(tmpJson) === "string") {
            try {
                tmpJson1 = JSON.parse(tmpJson);
                tmpJson1 = checkJsonKeys(tmpJson1);
            } catch (err) {
                console.log("ERROR:jsonDataSort:" + err.name + ":" + err.message);
                console.log("ERROR:jsonDataSort: It is String , not Object!");
                throw (new Error("2"));
            }
            if (typeOf(tmpJson1) != "array") {
                console.log("ERROR:jsonDataSort: It is not a Object!");
                throw (new Error("3"));
            }
            if (Object.keys(tmpJson1).length == 0) {
                console.log("ERROR:jsonDataSort: The size of Object is 0!");
                throw (new Error("4"));
            }
        } else if (typeOf(tmpJson) != "array") {
            console.log("ERROR:jsonDataSort: It is not a Object!");
            throw (new Error("5"));
        } else if (typeOf(tmpJson) === "array") {
            if (Object.keys(tmpJson).length == 0) {
                console.log("ERROR:jsonDataSort: The size of Object is 0!");
                throw (new Error("6"));
            } else {
                if (Object.keys(tmpJson).length > 0) {
                    tmpJson1 = tmpJson.slice();
                    tmpJson1 = checkJsonKeys(tmpJson1);
                } else {
                    console.log("ERROR:jsonDataSort: The size of Object is 0!!");
                    throw (new Error("7"));
                }
            }
        }
        if (typeOf(col) != "number" && typeOf(col) != "string") {
            col = 0;
        }
        if (typeof col === "number") {
            if (isNumber(col) || isNaN(parseInt("" + col)) == true || !isFinite(parseInt("" + col))) {
                console.log("ERROR:jsonDataSort: The index of key is not Integer!" + col);
                throw (new Error("8"));
            } else {
                col = toInt(col);
            }
            if(col < 0){
                console.log("ERROR:jsonDataSort: col < 0!" + col);
                col = 0;
            } else if ( col > (Object.keys(tmpJson1[0]).length - 1)){
                console.log("ERROR:jsonDataSort: col >= key.length!"+ col);
                col = Object.keys(tmpJson1[0]).length - 1;
            }
            if (Object.keys(tmpJson1[0])[col] === "undefined") {
                console.log("ERROR:jsonDataSort: The index of key is not find! " + col);
                throw (new Error("9"));
            } else {
                col = Object.keys(tmpJson1[0])[col];
                if (typeof col === "undefined") {
                    console.log("ERROR:jsonDataSort: The index of key is out of range!");
                    throw (new Error("10"));
                }
            } 
        } else if(typeof col === "string") {
            if (Object.keys(tmpJson1[0]).indexOf(col) == -1) {
                console.log("ERROR:jsonDataSort: The key is not find! " + col);
                throw (new Error("11"));
            }
        } else {
            console.log("ERROR:jsonDataSort: The key is not string! " + col);
            throw (new Error("12"));
        }
        if (typeOf(col) === "undefined") {
            console.log("ERROR:jsonDataSort: The key is not find!");
            throw (new Error("13"));
        }
        if (typeOf(asc) != "boolean") {
            if (typeof asc === "string" || typeof asc === "number") {
                var tmpInt;
                try {
                    if(("" + asc).toLowerCase() === "true") {
                        asc = 1;
                    } else if(("" + asc).toLowerCase() === "false") {
                        asc = 0;
                    }
                    var tmpInt = parseInt("" + asc);
                    if(isNaN(tmpInt) == false){
                        if (tmpInt == 0) {
                            asc = false;
                        } else if (tmpInt == 1) {
                            asc = true;
                        }
                    } else {
                        console.log("ERROR:jsonDataSort:asc type error!\nTYPE:" + typeOf(asc) + ",VALUE:" + asc);
                        asc = true;
                    }
                } catch (err) {
                    console.log("ERROR:jsonDataSort:parseBoolean:" + err.name + ":" + err.message);
                    throw (new Error("14"));
                } finally {
                    tmpInt = 0;
                    tmpInt = null;
                    delete tmpInt;
                }
            } else {
                asc = true;
            }
        }
        if (typeOf(tmpJson1) === "array") {
            if (Object.keys(tmpJson1).length > 0) {
                tmpJson1 = tmpJson1.sort(jsonSortByProperty(col, asc));
            } else {
                console.log("ERROR:jsonDataSort: The size of Object is 0!!!");
                throw (new Error("15"));
            }
        } else {
            console.log("ERROR:jsonDataSort: It is not a Object!" + typeOf(tmpJson1));
            throw (new Error("16"));
        }
    } catch (error) {
        console.log("ERROR:jsonDataSort:" + error.name + ":" + error.message);
        tmpJson1 = null;
        tmpJson1 = [];
        for(var i = 0; i < arguments.length; i++) {
            console.log((i + 1) + ": TYPE:" + typeOf(arguments[i]) + ",VALUE:" + arguments[i]);
        }
    } finally {
        if (tmpJson1 == []) {
            tmpJson1 = null;
            delete tmpJson1;
            return [];
        } else {
            return tmpJson1;
        }
    }
}

function checkInputNameOutVal(val) {
	var result = "";
	for (var i = 0; i < val.length; i++) {
		if (checkNameOutVal(val.substring(i, i + 1))) {
			result += val.substring(i, i + 1);
		} else {
			break;
		}
	}
	return result;
}
function checkInputNameInVal(val) {
	var result = "";
	for (var i = 0; i < val.length; i++) {
		if (checkNameInVal(val.substring(i, i + 1))) {
			result += val.substring(i, i + 1);
		} else {
			break;
		}
	}
	return result;
}
function checkNameOutVal(str) {
	var regExp = /[^\x00-\x2C\x2E-\x2F\x3A-\x40\x5B-\x5E\x60\x7B-\x7F]/;
	if (regExp.test(str)) {
		return true;
	} else {
		return false;
	}
}
function checkNameInVal(str) {
	var regExp = /^[\u4e00-\u9fa5_-a-zA-Z0-9]+$/;
	if (regExp.test(str)) {
		return true;
	} else {
		return false;
	}
}
function typeOf(o) {
    var _toString = Object.prototype.toString;
    var _type ={
        "undefined" : "undefined",
        "number" : "number",
        "boolean" : "boolean",
        "string" : "string",
        "[object Function]" : "function",
        "[object RegExp]" : "regexp",
        "[object Array]" : "array",
        "[object Date]" : "date",
        "[object Error]" : "error"
    }
    return _type[typeof o] || _type[_toString.call(o)] || (o ? o.constructor === {}.constructor ? "map" : "object" : "null");
}
function isNull(val){
    if(typeOf(val) === "null" || typeOf(val) === "undefined" || val == null){
        return true;
    } else {
        return false;
    }
}
function checkString(val){
    var _type = typeOf(val);
    try{
        if(_type === "string"){
            return val;
        } else if(_type === "number"){
            return "" + val;
        } else if(_type === "boolean"){
            return "" + val;
        } else {
            console.log("checkString:ERROR:Type is not string!\nTYPE:" + _type + ",VALUE:" + val);
            return "";
        }
    } catch (err) {
        console.log("ERROR:checkString:" + err.name + ":" + err.message + "\nTYPE:" + _type + ",VALUE:" + val);
        return "";
    } finally {
        _type = "";
        _type = null;
        delete _type;
    }
}
var isNumber = function isNumber(val) {
    return typeOf(val) === "number" && isFinite(val);
}
var toInt = function toInt(val) {
    if (typeOf(val) === "null" || typeOf(val) === "undefined") {
        console.log("The value is " + typeOf(val) + "!");
        return -1;
    } else if(isNumber(val)) {
        return parseInt(val, 10);
    } else if(typeOf(val) === "string" && !isNaN(parseInt(val)) && isFinite(parseInt(val))) {
        return parseInt(parseInt(val), 10);
    } else if(typeOf(val) === "boolean") {
        if(val == true) {
            return 1;
        } else {
            return 0;
        }
    } else {
        console.log("The value is undefined!\nTYPE:" + typeOf(val) + ",VALUE:" + val);
        return -1;
    }
}

Date.prototype.getFromFormat = function(format) {
    var yyyy = this.getFullYear().toString();
    format = format.replace(/yyyy/g, yyyy)
    var mm = (this.getMonth()+1).toString(); 
    format = format.replace(/mm/g, (mm[1]?mm:"0"+mm[0]));
    var dd  = this.getDate().toString();
    format = format.replace(/dd/g, (dd[1]?dd:"0"+dd[0]));
    var hh = this.getHours().toString();
    format = format.replace(/hh/g, (hh[1]?hh:"0"+hh[0]));
    var ii = this.getMinutes().toString();
    format = format.replace(/ii/g, (ii[1]?ii:"0"+ii[0]));
    var ss  = this.getSeconds().toString();
    format = format.replace(/ss/g, (ss[1]?ss:"0"+ss[0]));
    return format;
}
function joinVars(str = "&", map = {}, encode = true){
    if (typeof str != "string") {
        console.log("argument 1 must be string! " + str);
        str = "&";
    }
    if (Object.prototype.toString.call(map) != "[object Object]") {
        console.log("argument 2 must be map)!(Ex. {a,b}) " + map);
        return "";
    }
    if (typeof encode != "boolean") {
        encode = true;
    }
    var arr = [];
    for (var i = 0; i < Object.keys(map).length; i++) {
        if (encode == true) {
            arr[i] = Object.keys(map)[i] + "=" + encodeURIComponent(map[Object.keys(map)[i]].toString()).replace(/\'/g, "%27");
        } else {
            arr[i] = Object.keys(map)[i] + "=" + map[Object.keys(map)[i]].toString().replace(/\'/g, "\\'").replace(/\"/g, '\\"').replace(/%/g, '%25').replace(/ /g, '%20').replace(/\+/g, '%2B').replace(/&/g, '%26').replace(/\r/g, '%0D').replace(/\n/g, '%0A').replace(/\t/g, '%09').replace(/=/g, '%3D').replace(/\\/g, '\\\\\\\\');
        }
    }
    return arr.join(arguments[0]);
}

function validateURL(textval) {
    var urlregex = new RegExp( "^(http|https|ftp)\://([a-zA-Z0-9\.\-]+(\:[a-zA-Z0-9\.&amp;%\$\-]+)*@)*((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0-9\-]+\.)*[a-zA-Z0-9\-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(\:[0-9]+)*(/($|[a-zA-Z0-9\.\,\?\'\\\+&amp;%\$#\=~_\-]+))*$");
    if(urlregex.test(textval)){
    	return true;
    }
    return false;
}
function checkWindow(){
	var obj = ["Object","JSON","Date","encodeURIComponent","isNaN","RegExp","Boolean","String","Function"
		,"Array","Number","Error","undefined","document","location","setTimeout"
		,"open","close" ,"alert" ,"confirm" ,"prompt" ,"clearTimeout" ,"setInterval" ,"clearInterval" ,"moveBy" ,"moveTo" ,"resizeBy" 
		,"resizeTo","scrollBy" ,"scrollTo" ,"find","stop" ,"print" ,"blur" ,"focus" ,"scroll","status","statusbar","defaultstatus","location","locationbar","self"
		,"name","closed","frames","length","document","history","innerHeight","innerWidth","menubar","opener","outerHeight"
		,"outerWidth","pageXOffset","pageYOffset","parent","personalbar","toolbar","top"];
	
	var nowObj = ["Object","JSON","Date","encodeURIComponent","isNaN","RegExp","Boolean","String","Function"
		,"Array","Number","Error","undefined","document","location","setTimeout"];
	
	var windowNotObj = [];
	for(var i = 0 ; i < obj.length ; i++){
		if(obj[i] in window == false){
			windowNotObj.push(obj[i]);
		}
	}
	var todo = createXMLHttpRequest();
	if(typeOf(todo) === "boolean"){
		if(!todo){
			windowNotObj.push("Ajax");
		}
	}
	
	return windowNotObj;
}
function strToJson(str){
	if(isJSON(str) && typeOf(str) === "string" && str.length > 0){
		var json = JSON.parse(str);
		if(Object.keys(json).length > 0){
			return json;
		}
	}
	return "";
}

function checkStrIsDate(str){
	var date = "";
	try{
		if(typeOf(str) === "Date" || typeOf(str) === "number" || (typeOf(str) === "string" && str != "")){
			date = new Date(str);
			if(date == "Invalid Date"){
				date = new Date(checkInputNumberVal(str));
				if(date != "Invalid Date"){
					return true;
				}
			}
			else{
				return true;
			}
		}
	}catch(err){
		console_Log(err);
	}
	return false;
}

function toString(val){
	if(typeOf(val) === "string"){
		return val;
	}
	return val.toString();
}

function setButtonLight(val,arrayName,className){
	if(isNumber(val) && typeOf(arrayName) === "array" && arrayName.length > 0 && typeOf(className) === "string" && className != ''){
		for(var i = 0;i < arrayName.length;i++){
			getEle(arrayName[i]).classList.remove(className);
		}
		getEle(arrayName[val]).classList.add(className);
	}
}
function clearAllButtonLight(){
	for(var i = 0;i < firstMenuButton.length;i++){
		if(getEle(firstMenuButton[i]) != null && typeOf(getEle(firstMenuButton[i])) != "undefined"){
			getEle(firstMenuButton[i]).classList.remove("menu-active");
		}
		
	}
	for(var i = 0;i < gameMenu.length;i++){
		if(getEle(gameMenu[i]) != null && typeOf(getEle(gameMenu[i])) != "undefined"){
			getEle(gameMenu[i]).classList.remove("button-active");
		}
	}
	for(var i = 0;i < livevideoMenu.length;i++){
		if(getEle(livevideoMenu[i]) != null && typeOf(getEle(livevideoMenu[i])) != "undefined"){
			getEle(livevideoMenu[i]).classList.remove("button-active");
		}
	}
	for(var i = 0;i < lotteryMenu.length;i++){
		if(getEle(lotteryMenu[i]) != null && typeOf(getEle(lotteryMenu[i])) != "undefined"){
			getEle(lotteryMenu[i]).classList.remove("button-active");
		}
	}
	for(var i = 0;i < movementMenu.length;i++){
		if(getEle(movementMenu[i]) != null && typeOf(getEle(movementMenu[i])) != "undefined"){
			getEle(movementMenu[i]).classList.remove("button-active");
		}
	}
	for(var i = 0;i < rechargeButton.length;i++){
		if(getEle(rechargeButton[i]) != null && typeOf(getEle(rechargeButton[i])) != "undefined"){
			getEle(rechargeButton[i]).classList.remove("button-active");
		}
	}
	for(var i = 0;i < memberButton.length;i++){
		if(getEle(memberButton[i]) != null && typeOf(getEle(memberButton[i])) != "undefined"){
			getEle(memberButton[i]).classList.remove("button-active");
		}
	}
	for(var i = 0;i < gameRecordButton.length;i++){
		if(getEle(gameRecordButton[i]) != null && typeOf(getEle(gameRecordButton[i])) != "undefined"){
			getEle(gameRecordButton[i]).classList.remove("button-active");
		}
	}
}

function tableHTML (obj = []){
	var strHTML = '<tr>';
	try{
		for(var i = 0;i < obj.length;i++){
			if(obj[i] < 0){
				strHTML = strHTML.concat('<td class="txt-red">');
				strHTML = strHTML.concat(obj[i]);
				strHTML = strHTML.concat('</td>');
			}else{
				strHTML = strHTML.concat('<td>');
				strHTML = strHTML.concat(obj[i]);
				strHTML = strHTML.concat('</td>');
			}
		}
		strHTML = strHTML.concat('</tr>');
	}catch(e){
		console_Log("tableHTML error:"+e.message);
	}
	return strHTML;
}



















function drawCal(inc, cname, sort)
{
	var yr = parseInt(getEle('calendarYearText').value);
	var mth = parseInt(getEle('calendarMonthText').value);
	if(inc == 1 || inc == -1){
		month+=inc;
	}else{
		year=yr;
		month=mth;
	}
  if(month == 0 )
  {
    month=12;
    year--;
  }
  else if( month == 13 )
       {
         month=1;
         year++;
       }
  console_Log(year);
  console_Log(month);
  calendar(year, month, cname, sort);
}
function calendar(yr, mth, cname, sort)
{
  var td=new Date();
  var today=new Date( td.getFullYear(), td.getMonth(), td.getDate());
  var s='<table class=cal><col>';
  s+='<tr><th onclick="drawCal(-1,\''+cname+'\','+sort+')"><<<th colspan=5>';
  s+='<input type="text" id="calendarYearText" name="calendarYear" value="'+yr+'" onkeyup="chkYear(0,\''+cname+'\','+sort+');"><span>年</span>';
  s+='<input type="text" id="calendarMonthText" name="calendarMonth" value="'+mth+'" onkeyup="chkMounth(0,\''+cname+'\','+sort+');"><span>月</span>';
  s+='<th onclick="drawCal(1,\''+cname+'\','+sort+')">>>';
  mth--;
  s+='<tr><th>日<th>一<th>二<th>三<th>四<th>五<th>六';
  var dt=new Date(yr, mth, 1);
  var wd=dt.getDay(); // week day
  var i;
  if(sort == 1){
		var hr = '00';
		var min = '00';
		var sec = '00';
	}else if (sort == 2){
		var hr = '23';
		var min = '59';
		var sec = '59.999';
	}
  if(sort == 1){
		getEle('calendarDate').value = td.getFromFormat("yyyy/mm/dd") + ' 00:00:00';
	}else if (sort == 2){
		getEle('calendarDate').value = td.getFromFormat("yyyy/mm/dd") + ' 23:59:59.999';
	}
  s+='<tr>';
  for( ; wd > 0 ; wd--)   s+='<td>';
  for( i=1; i<32; )
  {
    dt=new Date(yr, mth, i);
    if( dt.toString() == today.toString() )
      s+='<td class="activecalander" onclick="clickCalendar(\''+cname+'\', '+yr+', '+mth+', '+i+', this)">'+i;
    else
      s+='<td class="nonecalander" onclick="clickCalendar(\''+cname+'\', '+yr+', '+mth+', '+i+', this)">'+i;
    dt=new Date(yr, mth, ++i);
    if( dt.getMonth() != mth ) break;
    wd=dt.getDay();
    if( wd == 0 )
      s+='<tr>';
  }
  for( ; wd < 6; wd++)  s+='<td>';
  s+='<tr class="calendarLastTr"><td colspan = "7" class="calendarLastTd">';
  s+='<span>時間:</span><input type="text" size="2" maxlength="2" width="2" placeholder="'+hr+'" id="calendarHr" onkeyup="chkHr();">';
  s+='<span>:</span><input type="text" size="2" maxlength="2" width="2" placeholder="'+min+'" id="calendarMin" onkeyup="chkMin();">';
  s+='<span>:</span><input type="text" size="2" maxlength="2" width="2" placeholder="'+sec+'" id="calendarSec" onkeyup="chkSec();"><br>';
  s+='<button onclick="clearCalendar();">清空</button>&nbsp<button onclick="choseTodayCalendar(\''+cname+'\','+sort+');">今天</button>&nbsp<button onclick="calendarDone(\''+cname+'\','+sort+');">更新</button></td></tr></table>';
  document.getElementById('calendar').innerHTML=s;
}
function chkYear(inc, cname, sort){
	if(checkInputNumberVal(getEle('calendarYearText').value) >= 0 && checkInputNumberVal(getEle('calendarYearText').value) <= 2100){
		getEle('calendarYearText').value = checkInputNumberVal(getEle('calendarYearText').value);
	}else{
		getEle('calendarYearText').value = '';
	}
	if(!isNull(getEle('calendarYearText').value) && getEle('calendarYearText').value != '' && getEle('calendarYearText').value.length == 4){
		drawCal(inc, cname, sort);
	}
}
function chkMounth(inc, cname, sort){
	if(checkInputNumberVal(getEle('calendarMonthText').value) >= 1 && checkInputNumberVal(getEle('calendarMonthText').value) <= 12){
		getEle('calendarMonthText').value = checkInputNumberVal(getEle('calendarMonthText').value);
	}else{
		getEle('calendarMonthText').value = '';
	}
	if(!isNull(getEle('calendarMonthText').value) && getEle('calendarMonthText').value != ''){
		drawCal(inc, cname, sort);
	}
}
function chkHr(){
	if(checkInputNumberVal(getEle('calendarHr').value) >= 0 && checkInputNumberVal(getEle('calendarHr').value) <= 23){
		getEle('calendarHr').value = checkInputNumberVal(getEle('calendarHr').value);
	}else{
		getEle('calendarHr').value = '';
	}
}
function chkMin(){
	if(checkInputNumberVal(getEle('calendarMin').value) >= 0 && checkInputNumberVal(getEle('calendarMin').value) <= 59){
		getEle('calendarMin').value = checkInputNumberVal(getEle('calendarMin').value);
	}else{
		getEle('calendarMin').value = '';
	}
}
function chkSec(){
	if(checkInputNumberVal(getEle('calendarSec').value) >= 0 && checkInputNumberVal(getEle('calendarSec').value) <= 59){
		getEle('calendarSec').value = checkInputNumberVal(getEle('calendarSec').value);
	}else{
		getEle('calendarSec').value = '';
	}
}
function clearCalendar(){
	var act = document.getElementsByClassName('activecalander');
	getEle('calendarHr').value = '';
	getEle('calendarMin').value = '';
	getEle('calendarSec').value = '';
	for(var len = 0; len < act.length;len++){
		act[len].className = 'nonecalander';
	}
	getEle('calendarDate').value = '';
}
function choseTodayCalendar(cname, sort){
	var now = new Date();
	if(sort == 1){
		getEle(cname).value = now.getFromFormat("yyyy/mm/dd") + ' 00:00:00';
		getEle('calendarDate').value = now.getFromFormat("yyyy/mm/dd") + ' 00:00:00';
	}else if (sort == 2){
		getEle(cname).value = now.getFromFormat("yyyy/mm/dd") + ' 23:59:59.999';
		getEle('calendarDate').value = now.getFromFormat("yyyy/mm/dd") + ' 23:59:59.999';
	}
	document.getElementById('calendar').style.display="none";
}
function calendarDone(cname, sort){
	var tmpInput = document.getElementById(cname);
	if(sort == 1){
		var hr = '00';
		var min = '00';
		var sec = '00';
	}else if (sort == 2){
		var hr = '23';
		var min = '59';
		var sec = '59.999';
	}
	if(!isNull(getEle('calendarHr').value) && getEle('calendarHr').value != ''){
		if(getEle('calendarHr').value.length == 1){
			hr = '0'+getEle('calendarHr').value;
		}else if(getEle('calendarHr').value.length == 2){
			hr = getEle('calendarHr').value;
		}
	}
	if(!isNull(getEle('calendarMin').value) && getEle('calendarMin').value != ''){
		if(getEle('calendarMin').value.length == 1){
			min = '0'+getEle('calendarMin').value;
		}else if(getEle('calendarMin').value.length == 2){
			min = getEle('calendarMin').value;
		}
	}
	if(!isNull(getEle('calendarSec').value) && getEle('calendarSec').value != ''){
		if(getEle('calendarSec').value.length == 1){
			sec = '0'+getEle('calendarSec').value;
		}else if(getEle('calendarSec').value.length == 2){
			sec = getEle('calendarSec').value;
		}
	}
	if(getEle('calendarDate').value != ''){
		if(getEle('calendarDate').value.length == 10){
			tmpInput.value = getEle('calendarDate').value+' '+hr+':'+min+':'+sec;
		}else{
			tmpInput.value = getEle('calendarDate').value;
		}
		
		document.getElementById('calendar').style.display="none";
		document.getElementById('wrapperCalendar').style.display="none";
	}else{
		choseTodayCalendar(cname, sort);
		document.getElementById('calendar').style.display="none";
		document.getElementById('wrapperCalendar').style.display="none";
	}
}
function clickCalendar(cname, yr, mth, i, ele){
	var act = document.getElementsByClassName('activecalander');
	if(!isNull(getEle('calendarYearText').value) && getEle('calendarYearText').value != ''){
		yr = getEle('calendarYearText').value;
	}
	if(!isNull(getEle('calendarMonthText').value) && getEle('calendarMonthText').value != ''){
		mth = getEle('calendarMonthText').value;
	}
	for(var len = 0; len < act.length;len++){
		act[len].className = 'nonecalander';
	}
	ele.classList.add('activecalander');
	    if(mth<10){
	        mth = '0' + mth;
	    }
	    if(i<10){
	        i = '0' + i;
	    }
	    getEle('calendarDate').value = yr+'/'+mth+'/'+i;
}

var td=new Date();
var year=td.getFullYear();
var month=td.getMonth()+1;

function newCalendar(ele, cname, sort){
    document.getElementById("wrapperCalendar").style.display = "block";
    document.getElementById('calendar').style.display="none";
    td=new Date();
    year=td.getFullYear();
    month=td.getMonth()+1;
    calendar(year, month, cname, sort);
    document.getElementById('calendar').style.display="";
    var topC = document.getElementById('calendar').scrollHeight;
    var leftC = document.getElementById('calendar').scrollWidth;
    var topB = ele.getBoundingClientRect().top;
    var leftB = ele.getBoundingClientRect().left;
    if(topB>topC){
        document.getElementById('calendar').style.top = topB-topC+'px';
    }else{
        document.getElementById('calendar').style.top = '0px';
    }
    if(leftB>leftC){
        document.getElementById('calendar').style.left = leftB-leftC+'px';
    }else{
        document.getElementById('calendar').style.left = '0px';
    }
}

function closeCalendar(){
    document.getElementById('calendar').style.display ='none';
    document.getElementById('wrapperCalendar').style.display ='none';
}
function getNDaysAgo(n){
	var now = new Date();
	var date = new Date(now.getTime()-n*24*3600*1000);
	var yr = date.getFullYear();
	var mth = date.getMonth()+1 > 9 ? date.getMonth()+1 : '0'+(date.getMonth()+1);
	var day = date.getDate() > 9 ? date.getDate() : '0'+(date.getDate());
	var newDate = yr+'/'+mth+'/'+day;
return newDate;
}