var isShowConsole = true;
var isCloseWindow = false;
var timeOutMinute = 3;

var td = new Date();
var year = td.getFullYear();
var month = td.getMonth() + 1;

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
		return;
	}
	return XHR;
}

// Full Screen function
var cancelFullScreen = function(el) {
	requestMemAction("zoomOut", "");
	if (checkIE()) {
		if (window['ActiveXObject'] ? true : "ActiveXObject" in window ? true
				: false) {
			// IEQQ.
			var wscript = new ActiveXObject("WScript.Shell");
			if (wscript !== null) {
				wscript.SendKeys("{F11}");
			}
		}
	} else {
		var requestMethod = el.cancelFullScreen || el.webkitCancelFullScreen
				|| el.mozCancelFullScreen || el.exitFullscreen;
		if (requestMethod) { // cancel full screen.
			requestMethod.call(el);
		} else if (window['ActiveXObject'] ? true
				: "ActiveXObject" in window ? true : false) {
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
		if (window['ActiveXObject'] ? true : "ActiveXObject" in window ? true
				: false) {
			// IEQQ.
			var wscript = new ActiveXObject("WScript.Shell");
			if (wscript !== null) {
				wscript.SendKeys("{F11}");
			}
		}
	} else {
		var requestMethod = el.requestFullScreen || el.webkitRequestFullScreen
				|| el.mozRequestFullScreen || el.msRequestFullscreen;
		if (requestMethod) { // Native full screen.
			requestMethod.call(el);
		} else if (window['ActiveXObject'] ? true
				: "ActiveXObject" in window ? true : false) {
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
	var isInFullScreen = (document.fullScreenElement && document.fullScreenElement !== null)
			|| (document.mozFullScreen || document.webkitIsFullScreen);
	if ((screen.availHeight <= document.documentElement.clientHeight)
			|| (screen.height - 30 <= document.documentElement.clientHeight)) {
		isInFullScreen = true;
	}
	if (isInFullScreen) {
		document.getElementById("fullScreenId").title = "全螢幕";
		cancelFullScreen(document);
	} else {
		document.getElementById("fullScreenId").title = "縮小視窗，按ESC也可縮小視窗";
		if (checkIE()) {
			requestFullScreen(document);
		} else {
			requestFullScreen(elem);
		}

	}
	return false;
}

// Json
var isJSON = function(str) {
	if (typeof str == 'string' && str != "") {
		try {
			JSON.parse(str);
			return true;
		} catch (e) {
			console_Log(e.message);
			return false;
		}
	} else {
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
	if (browserName.toLowerCase().indexOf("netscape") != -1
			&& nAgt.toLowerCase().indexOf("qqbrowser") != -1) {
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
	if (isShowConsole == true) {
		if (window.console) {
			console.log(msg);
		}
	}
}

// getElement
// getElementById && getElementsByName
var getEle = function(name, row) {
	ele = document.getElementById(name);
	if (ele == null || (row != undefined && row != null && !isNaN(Number(row)))) {
		if (ele != null && document.getElementsByName(name).length == 0) {
			return ele;
		}
		ele = document.getElementsByName(name);
		if (row == undefined || row == null || isNaN(Number(row))) {
			row = 0;
		} else {
			row = Number(row);
		}
		if (ele.length > 0) {
			if (row >= ele.length) {
				row = ele.length - 1;
			}
			if (row < 0) {
				row = 0;
			}
			return ele[row];
		} else {
			return null;
		}
	} else {
		if (ele == undefined || ele == null) {
			return null;
		}
		return ele;
	}
	return null;
}
// getElementsByClassName
var getEleCN = function(name, row, element) {
	ele = document.getElementsByClassName(name);
	if (element != undefined && element != null) {
		try {
			ele = element.getElementsByClassName(name);
		} catch (err) {
			return null;
		}
		if (ele == undefined || ele == null) {
			return null;
		}
	}
	if (ele.length > 0) {
		if (row == undefined || row == null || isNaN(Number(row))) {
			row = 0;
		} else {
			row = Number(row);
		}
		if (row >= ele.length) {
			row = ele.length - 1;
		}
		if (row < 0) {
			row = 0;
		}
		return ele[row];
	} else {
		return null;
	}
	return null;
}
// getElementsByTagName
var getEleTN = function(name, row, xmlDoc) {
	ele = document.getElementsByTagName(name);
	if (xmlDoc != undefined && xmlDoc != null) {
		try {
			ele = xmlDoc.getElementsByTagName(name);
		} catch (err) {
			return null;
		}
		if (ele == undefined || ele == null) {
			return null;
		}
	}
	if (ele.length > 0) {
		if (row == undefined || row == null || isNaN(Number(row))) {
			row = 0;
		} else {
			row = Number(row);
		}
		if (row >= ele.length) {
			row = ele.length - 1;
		}
		if (row < 0) {
			row = 0;
		}
		return ele[row];
	} else {
		return null;
	}
	return null;
}

// close window
function closeSelf() {
	if (isCloseWindow == true) {
		window.opener = null;
		window.close();
	}
}

function checkTimeOut(lastActionTime) {
	var now = new Date();
	if (now.getTime() - lastActionTime > timeOutMinute * 60 * 1000) {
		return true;
	}
	return false;
}

var checkIE = function() {
	var nAgt = navigator.userAgent;
	var browserName = navigator.appName;
	if (nAgt.toLowerCase().indexOf("chrome") != -1) {
		return false;
	} else if (browserName.toLowerCase().indexOf("netscape") != -1
			&& nAgt.toLowerCase().indexOf("qqbrowser") != -1) {
		return true;
	} else if ((nAgt.toLowerCase().indexOf("trident") != -1)
			&& (nAgt.toLowerCase().indexOf("rv:11") == -1)) {
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

function checkInputTextVal(val) {
	var result = "";
	val = toString(val);
	for (var i = 0; i < val.length; i++) {
		if (checkTextVal(val.substring(i, i + 1))) {
			result += val.substring(i, i + 1);
		} else {
			break;
		}
	}
	return result;
}
function checkInputAccountVal(val) {
	var result = "";
	val = toString(val);
	for (var i = 0; i < val.length; i++) {
		if (checkAccount(val.substring(i, i + 1))) {
			result += val.substring(i, i + 1);
		} else {
			break;
		}
	}
	return result;
}
function checkInputPassWordVal(val) {
	var result = "";
	val = toString(val);
	for (var i = 0; i < val.length; i++) {
		if (checkPassWordVal(val.substring(i, i + 1))) {
			result += val.substring(i, i + 1);
		} else {
			break;
		}
	}
	return result;
}
function checkInputNumberVal(val) {
	var result = "";
	val = toString(val);
	for (var i = 0; i < val.length; i++) {
		if (checkNumberVal(val.substring(i, i + 1))) {
			result += val.substring(i, i + 1);
		} else {
			break;
		}
	}
	return result;
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
function checkBankAccInputNumberVal(val) {
	var result = "";
	val = toString(val);
	for (var i = 0; i < val.length; i++) {
		if (checkBankAccVal(val.substring(i, i + 1))) {
			result += val.substring(i, i + 1);
		} else {
			break;
		}
	}
	return result;
}
function checkInputNameOutVal(val) {
	var result = "";
	val = toString(val);
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
	val = toString(val);
	for (var i = 0; i < val.length; i++) {
		if (checkNameInVal(val.substring(i, i + 1))) {
			result += val.substring(i, i + 1);
		} else {
			break;
		}
	}
	return result;
}
function checkTextVal(str) {
	var regExp = /^[\d|a-zA-Z]+$/;
	if (regExp.test(str)) {
		return true;
	} else {
		return false;
	}
}
function checkAccount(str){
	var patt = new RegExp("^[a-zA-Z][a-zA-Z0-9]*$");
	if(str.length == 1){
		patt = new RegExp("^[a-zA-Z]$");
	}
	if(patt.test(str)){
		return str;
	}else{
		return "";
	}
}
function checkPassWordVal(str) {
	var regExp = /^[\x21-\x7E]+$/;
	if (regExp.test(str)) {
		return true;
	} else {
		return false;
	}
}
function checkNumberVal(str) {
	var regExp = /^[\d]+$/;
	if (regExp.test(str)) {
		return true;
	} else {
		return false;
	}
}
function checkEnglishVal(str) {
	var regExp = /^[a-zA-Z]+$/;
	if (regExp.test(str)) {
		return true;
	} else {
		return false;
	}
}
function checkDecimalVal(str) {
	var regExp = /^[0-9]+(\.[0-9]{1,2})?$/;
	if (regExp.test(str)) {
		return true;
	} else {
		return false;
	}
}
function checkBankAccVal(str) {
	var regExp = /^[0-9-]+$/;
	if (regExp.test(str)) {
		return true;
	} else {
		return false;
	}
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

function selectItemByValue(elmnt, value) {
	for (var i = 0; i < elmnt.options.length; i++) {
		if (elmnt.options[i].value == value) {
			elmnt.selectedIndex = i;
		}
	}
}

function getHiddenInput(name) {
	if (document.getElementsByName(name).length == 0) {
		return "";
	}
	return document.getElementsByName(name)[0].value;
}

function setHiddenInput(name, val) {
	if (document.getElementsByName(name).length == 0) {
		var tmpInput = document.createElement("input");
		tmpInput.type = 'hidden';
		tmpInput.name = name;
		document.body.appendChild(tmpInput);
	}
	document.getElementsByName(name)[0].value = val;
}
function clearNum(str) {
	if (str == null || str.trim().length == 0) {
		return "";
	} else {
		str = str.trim();
		var tmpStr = "";
		for (var i = 0; i < str.length; i++) {
			var tmpC = str.substring(i, i + 1);
			if (/^\d/.test(tmpC)) {
				tmpStr += tmpC;
			}
		}
		return tmpStr;
	}
}

function chkInputBankNo(str) {
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

function onchgInputBankNo(str) {
	str = chkInputBankNo(str);
	if (str.substring(str.length - 1, str.length) == "-") {
		str = str.substring(0, str.length - 1);
	}
	return str;
}
function getNewTime(){
	var str = Math.floor(Math.random() * 100) + "";
	if(str.length < 2){
		str = "0" + str;
	}
	str = new Date().getTime() + str;
	return str;
}
function checkXHR(xhr){
	if (xhr == null || typeof xhr === "undefined") {
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
function ocjectIsNull(obj){
	if(obj != null && typeof obj != "undefined" && obj != undefined){
		return true;
	}
	else{
		return false;
	}
}
function checkObjectIsNotNull(obj){
	if(obj != null && typeof obj != "undefined" && obj != undefined){
		return obj;
	}
	else{
		return "";
	}
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
        	arr[i] = Object.keys(map)[i] + "=" + map[Object.keys(map)[i]].toString().replace(/\'/g, "\\'").replace(/\"/g, '\\"').replace(/%/g, '%25').replace(/ /g, '%20').replace(/\+/g, '%2B').replace(/&/g, '%26').replace(/=/g, '%3D').replace(/\\/g, '\\\\\\\\');
        }
    }
    return arr.join(arguments[0]);
}
/*
 * yyyyMMddhhmmss
 */
Date.prototype.getFromFormat = function(format) {
	if(typeof format === "string" && !isNull(format) && format != ''){
		var str = ['-','/',';',' ','y','M','d','h','m','s'];
		for(var i = 0;i < format.length;i++){
			if(!format[i] in str){
				console_Log("getFromFormat error in"+(i+1));
				return '';
			}
		}
		var nowDateTime = new Date();
	    var yyyy = nowDateTime.getFullYear().toString();
	    format = format.replace(/yyyy/g, yyyy)
	    var MM = (nowDateTime.getMonth()+1).toString(); 
	    format = format.replace(/MM/g, (MM[1]?MM:"0"+MM[0]));
	    var dd  = nowDateTime.getDate().toString();
	    format = format.replace(/dd/g, (dd[1]?dd:"0"+dd[0]));
	    var hh = nowDateTime.getHours().toString();
	    format = format.replace(/hh/g, (hh[1]?hh:"0"+hh[0]));
	    var mm = nowDateTime.getMinutes().toString();
	    format = format.replace(/mm/g, (mm[1]?mm:"0"+mm[0]));
	    var ss  = nowDateTime.getSeconds().toString();
	    format = format.replace(/ss/g, (ss[1]?ss:"0"+ss[0]));
	    
	    delete str;
	    delete nowDateTime;
	    delete yyyy;
	    delete MM;
	    delete dd;
	    delete hh;
	    delete mm;
	    delete ss;
	    
	    return format;
	}else{
		console_Log("getFromFormat is null!");
		return '';
	}
    return '';
};

function insertTableTrTh(tableName , tdArr){
	if(typeOf(getEle(tableName)) != "undefined"){
		var table = getEle(tableName);
		var tr = table.insertRow(table.rows.length);
		for(var i = 0 ; i < tdArr.length ; i++){
			var th = document.createElement("TH");
			
			if(typeOf(tdArr[i]) === "undefined" || tdArr[i] == null){
				th.innerHTML = "";
				tr.appendChild(th);
			}
			else{
				th.innerHTML = tdArr[i];
				tr.appendChild(th);
			}
			
		}
	}
}

function insertTableTrTd(tableName , tdArr){
	if(typeOf(getEle(tableName)) != "undefined"){
		var table = getEle(tableName);
		var tr = table.insertRow(table.rows.length);
		for(var i = 0 ; i < tdArr.length ; i++){
			var td = tr.insertCell(i);
			if(typeOf(tdArr[i]) === "undefined" || tdArr[i] == null){
				td.innerHTML = "";
			}
			else{
				td.innerHTML = tdArr[i];
			}
			
		}
	}
}

function deleteAllTableTr(tableName){
	if(typeOf(getEle(tableName)) != "undefined"){
		var table = getEle(tableName);
		for(var i = table.rows.length ; i > 1 ; i--){
			table.deleteRow(-1);
		}
	}
}


function validateURL(textval) {
    var urlregex = new RegExp( "^(http|https|ftp)\://([a-zA-Z0-9\.\-]+(\:[a-zA-Z0-9\.&amp;%\$\-]+)*@)*((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0-9\-]+\.)*[a-zA-Z0-9\-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(\:[0-9]+)*(/($|[a-zA-Z0-9\.\,\?\'\\\+&amp;%\$#\=~_\-]+))*$");
    if(urlregex.test(textval)){
    	return true;
    }
    return false;
}

function toString(val){
	if(typeOf(val) === "string"){
		return val;
	}
	return val.toString();
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
function logTableHTML (obj = []){
	var strHTML = '<tr>';
	for(var i = 0;i < obj.length;i++){
		strHTML = strHTML.concat('<td>');
		strHTML = strHTML.concat(obj[i].toString());
		strHTML = strHTML.concat('</td>');
	}
	strHTML = strHTML.concat('</tr>');
	return strHTML;
}
function checkJS(functionName, jsName){
	if(typeof functionName != "undefined" && functionName!=null && typeof jsName != "undefined" && jsName!=null){
		try{
			eval(functionName+"()");
		} catch (error) {
			console_Log("checkJS error Message:"+error.message);
			console_Log("checkJS error Code:"+(error.number & 0xFFFF));
			console_Log("checkJS error Name:"+error.name);
			loadScript(JS_PATH + jsName + "?id=" + new Date().getTime(), function() {});
		} finally {
			delete functionName;
			delete jsName;
			functionName = undefined;
			jsName = undefined;
		}
	}
}
