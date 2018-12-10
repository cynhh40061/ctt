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
function isJSON (str) {
	if (typeof str != 'string'){
		return false;
	}

	  str = str.replace(/\s/g, '').replace(/\n|\r/, '');

	  if (/^\{(.*?)\}$/.test(str))
	    return /"(.*?)":(.*?)/g.test(str);

	  if (/^\[(.*?)\]$/.test(str)) {
	    return str.replace(/^\[/, '')
	      .replace(/\]$/, '')
	      .replace(/},{/g, '}\n{')
	      .split(/\n/)
	      .map(function (s) { return isJSON(s); })
	      .reduce(function (prev, curr) { return !!curr; });
	  }

	  return false;
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


function appear(divId){
//	console_Log("appear  " + divId);
	getEle(divId).classList.remove("hidden");
}

function disAppear(divId){
//	console_Log("disAppear  " + divId);
	getEle(divId).classList.add("hidden");
}
var isNumber = function isNumber(val) {
    return typeOf(val) === "number" && isFinite(val);
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
    if(typeOf(val) === "null" || typeOf(val) === "undefined"){
        return true;
    } else {
        return false;
    }
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
function toString(val){
	if(typeOf(val) === "string"){
		return val;
	}
	return val.toString();
}

//
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
function isSortType(val) {
    if (typeOf(val) === "number" || typeOf(val) === "boolean" || typeOf(val) === "string" || typeOf(val) === "date" || typeOf(val) === "object") {
        return true;
    } else {
        return false;
    }
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

function documentCreateButton(bntText,bntId,position=null){
	if(bntId.length > 0 && typeOf(bntId) != "undefined"){
		var bnt = document.createElement('BUTTON');
		var t = document.createTextNode(bntText);
		bnt.id = bntId;
		bnt.appendChild(t);
		if(getEle(position) != null && typeOf(getEle(position)) != "undefined"){
			getEle(position).appendChild(bnt);
		}
		else{
			document.body.appendChild(bnt);
		}
	}
}

