function js_lottery() {
	return true;
}
const XGLHC_ID = 26;
const ZODIAC_ARRAY = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ];
const ZODIAC_NAME_ARRAY = [ "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪" ];
const BALL_COLOUR_ARRAY = ["red","blue","green"];


if (typeof String.prototype.trim !== 'function') {
	String.prototype.trim = function() {
		return this.replace(/^\s+|\s+$/g, '');
	}
}

var getByClassName = function(classNames) {
	// return this.querySelectorAll("." +
	// classNames.toString().trim().replace(/\s+/,"."));
	if (document.querySelectorAll) { // IE8
		return this.querySelectorAll("." + classNames.toString().trim().replace(/\s+/, "."));
	}
	var d = document, elements, pattern, i, results = [];
	try {
		if (d.evaluate) { // IE6, IE7
			pattern = ".//*[contains(concat(' ', @class, ' '), ' " + classNames + " ')]";
			elements = d.evaluate(pattern, d, null, 0, null);
			while ((i = elements.iterateNext())) {
				results.push(i);
			}
		} else {
			elements = d.getElementsByTagName("*");
			pattern = new RegExp("(^|\\s)" + classNames + "(\\s|$)");
			for (i = 0; i < elements.length; i++) {
				if (pattern.test(elements[i].className)) {
					results.push(elements[i]);
				}
			}
		}
	} catch (err) {

	} finally {
		delete d;
		delete elements;
		delete pattern;
		delete i;
		d = undefined;
		elements = undefined;
		pattern = undefined;
		i = undefined;
	}
	return results;
};

if (!document.getElementsByClassName) {
	document.getElementsByClassName = getByClassName;
}

if (typeof Element != "undefined" ? !Element.getElementsByClassName : false) {
	Element.prototype.getElementsByClassName = getByClassName;
}

if (!Object.keys) {
	Object.keys = (function() {
		'use strict';
		var hasOwnProperty = Object.prototype.hasOwnProperty, hasDontEnumBug = !({
			toString : null
		}).propertyIsEnumerable('toString'), dontEnums = [ 'toString', 'toLocaleString', 'valueOf', 'hasOwnProperty', 'isPrototypeOf', 'propertyIsEnumerable', 'constructor' ], dontEnumsLength = dontEnums.length;

		return function(obj) {
			if (typeof obj !== 'object' && (typeof obj !== 'function' || obj === null)) {
				throw new TypeError('Object.keys called on non-object');
			}

			var result = [], prop, i;

			for (prop in obj) {
				if (hasOwnProperty.call(obj, prop)) {
					result.push(prop);
				}
			}

			if (hasDontEnumBug) {
				for (i = 0; i < dontEnumsLength; i++) {
					if (hasOwnProperty.call(obj, dontEnums[i])) {
						result.push(dontEnums[i]);
					}
				}
			}
			return result;
		};
	}());
}

if (!Array.prototype.indexOf) {
	Array.prototype.indexOf = function(elt /* , from */) {
		var len = this.length >>> 0;

		var from = Number(arguments[1]) || 0;
		from = (from < 0) ? Math.ceil(from) : Math.floor(from);
		if (from < 0)
			from += len;

		for (; from < len; from++) {
			if (from in this && this[from] === elt)
				return from;
		}
		return -1;
	};
}

/**
 * Method that checks whether cls is present in element object.
 * 
 * @param {Object}
 *                ele DOM element which needs to be checked
 * @param {Object}
 *                cls Classname is tested
 * @return {Boolean} True if cls is present, false otherwise.
 */
function hasClass(ele, cls) {
	if (!ele) {
		return false;
	} else if (ele.classList) {
		return ele.classList.contains(cls);
	} else if (ele.className) {
		return ele.className.toString().indexOf(cls) > -1;
	} else {
		ele.className = "";
		return false;
	}
	return false;
	// return ele.getAttribute('class').indexOf(cls) > -1;
}

/**
 * Method that adds a class to given element.
 * 
 * @param {Object}
 *                ele DOM element where class needs to be added
 * @param {Object}
 *                cls Classname which is to be added
 * @return {null} nothing is returned.
 */
function addClass(ele, cls) {
	if (!ele) {
		return false;
	}
	if (!hasClass(ele, cls)) {
		if (ele.classList) {
			ele.classList.add(cls);
		} else if (ele.className) {
			if (ele.className && ele.className.toString().trim() == "") {
				ele.className = cls;
			} else {
				ele.className = ele.className.toString().trim() + " " + cls;
			}
		} else {
			ele.className = cls;
		}
		return true;
	}
	return false;
}

/**
 * Method that does a check to ensure that class is removed from element.
 * 
 * @param {Object}
 *                ele DOM element where class needs to be removed
 * @param {Object}
 *                cls Classname which is to be removed
 * @return {null} Null nothing is returned.
 */
function removeClass(ele, cls) {
	if (!ele) {
		return false;
	}
	if (hasClass(ele, cls)) {
		if (ele.classList) {
			ele.classList.remove(cls);
		} else if (ele.className) {
			ele.className = ele.className.toString().trim().replace(" " + cls, "");
			ele.className = ele.className.toString().trim().replace(cls + " ", "");
			ele.className = ele.className.toString().trim().replace(cls, "");
			ele.className = ele.className.toString().trim();
		} else {
			ele.className = "";
		}
		return true;
	}
	return false;
}

if (!document.getElementsByClassName) {
	// Polyfill Array.prototype.indexOf
	Array.prototype.indexOf || (Array.prototype.indexOf = function(value, startIndex) {
		'use strict';
		if (this == null)
			throw new TypeError('array.prototype.indexOf called on null or undefined');
		var o = Object(this), l = o.length >>> 0;
		if (l === 0)
			return -1;
		var n = startIndex | 0, k = Math.max(n >= 0 ? n : l - Math.abs(n), 0) - 1;
		function sameOrNaN(a, b) {
			return a === b || (typeof a === 'number' && typeof b === 'number' && isNaN(a) && isNaN(b))
		}
		while (++k < l)
			if (k in o && sameOrNaN(o[k], value))
				return k;
		return -1
	});

	// adds classList support (as Array) to Element.prototype for IE8-9
	Object.defineProperty(Element.prototype, 'classList', {
		get : function() {
			var element = this, domTokenList = (element.getAttribute('class') || '').replace(/^\s+|\s$/g, '').split(/\s+/g);
			if (domTokenList[0] === '')
				domTokenList.splice(0, 1);
			function setClass() {
				if (domTokenList.length > 0)
					element.setAttribute('class', domTokenList.join(' '));
				else
					element.removeAttribute('class');
			}
			domTokenList.toggle = function(className, force) {
				if (force !== undefined) {
					if (force)
						domTokenList.add(className);
					else
						domTokenList.remove(className);
				} else {
					if (domTokenList.indexOf(className) !== -1)
						domTokenList.splice(domTokenList.indexOf(className), 1);
					else
						domTokenList.push(className);
				}
				setClass();
			};
			domTokenList.add = function() {
				var args = [].slice.call(arguments)
				for (var i = 0, l = args.length; i < l; i++) {
					if (domTokenList.indexOf(args[i]) === -1)
						domTokenList.push(args[i])
				}
				;
				setClass();
			};
			domTokenList.remove = function() {
				var args = [].slice.call(arguments)
				for (var i = 0, l = args.length; i < l; i++) {
					if (domTokenList.indexOf(args[i]) !== -1)
						domTokenList.splice(domTokenList.indexOf(args[i]), 1);
				}
				;
				setClass();
			};
			domTokenList.item = function(i) {
				return domTokenList[i];
			};
			domTokenList.contains = function(className) {
				return domTokenList.indexOf(className) !== -1;
			};
			domTokenList.replace = function(oldClass, newClass) {
				if (domTokenList.indexOf(oldClass) !== -1)
					domTokenList.splice(domTokenList.indexOf(oldClass), 1, newClass);
				setClass();
			};
			domTokenList.value = (element.getAttribute('class') || '');
			return domTokenList;
		}
	});

}

if (!document.getElementsByClassName) {
	document.getElementsByClassName = function(search) {
		var d = document, elements, pattern, i, results = [];
		if (d.querySelectorAll) { // IE8
			return d.querySelectorAll("." + search);
		}
		if (d.evaluate) { // IE6, IE7
			pattern = ".//*[contains(concat(' ', @class, ' '), ' " + search + " ')]";
			elements = d.evaluate(pattern, d, null, 0, null);
			while ((i = elements.iterateNext())) {
				results.push(i);
			}
		} else {
			elements = d.getElementsByTagName("*");
			pattern = new RegExp("(^|\\s)" + search + "(\\s|$)");
			for (i = 0; i < elements.length; i++) {
				if (pattern.test(elements[i].className)) {
					results.push(elements[i]);
				}
			}
		}
		return results;
	}
}

if (typeof JSON === 'undefined') {
	var JSON = JSON || {};
	// implement JSON.stringify serialization
	JSON.stringify = JSON.stringify || function(obj) {
		var t = typeof (obj);
		if (t != "object" || obj === null) {
			// simple data type
			if (t == "string")
				obj = '"' + obj + '"';
			return String(obj);
		} else {
			// recurse array or object
			var n, v, json = [], arr = (obj && obj.constructor == Array);
			for (n in obj) {
				v = obj[n];
				t = typeof (v);
				if (t == "string") {
					v = '"' + v + '"';
				} else if (t == "object" && v !== null) {
					v = JSON.stringify(v);
				}
				json.push((arr ? "" : '"' + n + '":') + String(v));
			}
			return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
		}
	};

	// implement JSON.parse de-serialization
	JSON.parse = JSON.parse || function(str) {
		if (str === "") {
			str = '""';
		}
		eval("var p=" + str + ";");
		return p;
	};
}

function createXMLHttpRequest() {
	var XHR = null;
	try {
		if (typeof XMLHttpRequest != "undefined") {
			if (window.XDomainRequest) {
				XHR = new XDomainRequest();
				console_Log("new XDomainRequest");
				return XHR;
			} else {
				XHR = new XMLHttpRequest();
				console_Log("new XMLHttpRequest");
				return XHR;
			}
		} else if (window.ActiveXObject) {
			var aVersions = [ "MSXML2.XMLHttp.5.0", "MSXML2.XMLHttp.4.0", "MSXML2.XMLHttp.3.0", "MSXML2.XMLHttp", "Microsoft.XMLHttp" ];
			for (var i = 0; i < aVersions.length; i++) {
				try {
					XHR = new ActiveXObject(aVersions[i]);
					console_Log("new ActiveXObject aVersions[i]");
					return XHR;
				} catch (oError) {
					throw new Error("XMLHttp object could be created.");
				}
			}
		}
		alert("NO AJAX, Please change your browser. ");
		throw new Error("XMLHttp object could be created.");
	} catch (e1) {
		alert("NO AJAX, Please change your browser. ");
		return;
	}
	if (!XHR) {
		alert("NO AJAX, Please change your browser. ");
		return;
	}
	return XHR;
}
function checkXHR(xhr) {
	if (xhr == null || typeof xhr === "undefined") {
		xhr = null;
		xhr = createXMLHttpRequest();
	}
	try {
		if (xhr.status == 0 && xhr.readyState == 0) {
			// console_Log("xhr.readyState:0,xhr.status:0");
			return xhr;
		}
	} catch (err) {

	}
	try {
		if (xhr.status == 200 && xhr.readyState == 4) {
			xhr.abort();
			// console_Log("xhr.readyState:4,xhr.status:200");
		}
	} catch (err) {

	}
	try {
		if (xhr.status == 0 && xhr.readyState == 4) {
			xhr.abort();
			// console_Log("xhr.readyState:4,xhr.status:0");
		}
	} catch (err) {

	}
	if (xhr.readyState && xhr.readyState == 1) {
		xhr.timeout = 1000;
		for (var i = 0; i < 10000000; i++) {
			if (xhr.readyState != 1 || xhr.status != 0) {
				break;
			}
		}
	}
	if (xhr.readyState && xhr.readyState == 1) {
		xhr.abort();
	}
	// console_Log("xhr.readyState:"+xhr.readyState);
	// console_Log("xhr.status:"+xhr.status);
	return xhr;
}
function getNewTime() {
	var str = Math.floor(Math.random() * 100) + "";
	if (str.length < 2) {
		str = "0" + str;
	}
	str = new Date().getTime() + str;
	return str;
}
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
function checkJS(functionName, jsName) {
	if (typeof functionName != "undefined" && functionName != null && typeof jsName != "undefined" && jsName != null) {
		try {
			eval(functionName + "()");
		} catch (error) {
			console_Log("checkJS error Message:" + error.message);
			console_Log("checkJS error Code:" + (error.number & 0xFFFF));
			console_Log("checkJS facility Code:" + (error.number >> 16 & 0x1FFF));
			console_Log("checkJS error Name:" + error.name);
			loadScript(JS_PATH + jsName + "?id=" + new Date().getTime(), function() {
			});
		} finally {
			delete functionName;
			delete jsName;
			functionName = undefined;
			jsName = undefined;
		}
	}
}
function loadScript(url, callback) {
	// adding the script tag to the head as suggested before
	var head = document.getElementsByTagName('head')[0];
	var script = document.createElement('script');
	script.type = 'text/javascript';
	script.charset = 'utf-8';
	script.src = url;
	if (!callback)
		callback = function() {
		}; // ★★★★★★★★★ JUST ADD THIS LINE!

	// bind the event to the callback function
	if (script.addEventListener) {
		script.addEventListener("load", callback, false); // IE9+,
		// Chrome,
		// Firefox
	} else if (script.readyState) {
		script.onreadystatechange = callback; // IE8
	}
	// fire the loading
	head.appendChild(script);
}
var isShowConsole = false;
var console_Log = function(msg) {
	if (isShowConsole == true) {
		if (window.console) {
			console.log(msg);
		}
	}
}
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

/* 20180615 Ason */
// Rangebar
// var r = document.querySelectorAll('input[type=range]'),
var r = document.getElementsByClassName("bottom-area")[0].getElementsByTagName("div")[0].getElementsByTagName("input");
prefs = [ 'webkit-slider-runnable', 'moz-range' ], styles = [], l = prefs.length, n = r.length;

var getTrackStyleStr = function(el, j) {
	var str = '', min = el.min || 1700, perc = (el.max) ? ~~(100 * (el.value - min) / (el.max - min)) : el.value, val = perc + '% 100%';

	el.previousElementSibling.textContent = el.value;

	for (var i = 0; i < l; i++) {
		str += "input[type=range][data-rangeId='" + j + "']::-" + prefs[i] + '-track{background-size:' + val + '} ';
	}
	return str;
};

var setDragStyleStr = function(evt) {
	var trackStyle = getTrackStyleStr(evt.target, this);
	styles[this].textContent = trackStyle;
	checkOuput();
};

for (var i = 0; i < n; i++) {
	var s = document.createElement('style');
	document.body.appendChild(s);
	styles.push(s);
	r[i].setAttribute('data-rangeId', i);
	r[i].addEventListener('input', setDragStyleStr.bind(i));
}

function checkOuput() {
	var tmpA = document.getElementsByClassName("bottom-area")[0].getElementsByTagName("output");
	tmpA[0].innerHTML = divide(minus(parseFloat(document.getElementsByName("bonusSetMax")[0].value), parseInt(tmpA[1].innerHTML)), 20) + "%";
}

// 倍數下拉選單
function multipleDropMenu() {
	if (hasClass(document.getElementById("multipleDropMenuContent"), "show")) {
		removeClass(document.getElementById("multipleDropMenuContent"), "show");
	} else {
		addClass(document.getElementById("multipleDropMenuContent"), "show");
	}
	// document.getElementById("multipleDropMenuContent").classList.toggle("show");
}

// 高級追號倍數下拉選單
function multipleDropMenu2() {
	document.getElementById("multipleDropMenuContent2").classList.toggle("show");
}

// 縮合選單
var Dropdown = function(el) {
	// console_Log(el);
	function init() {
		var elem = document.getElementsByClassName("submenuItems");
		for (var i = 0; i < elem.length; i++) {
			// console_Log(el);
			if (elem[i] != el) {
				if (!hasClass(elem[i], "hide")) {
					addClass(elem[i], "hide");
				}
				elem[i].style.height = "0px";
			} else {
				// console_Log(el.classList);
				// elem[i].classList.toggle("hide");
				if (!hasClass(elem[i], "hide")) {
					addClass(elem[i], "hide");
				} else {
					removeClass(elem[i], "hide");
				}
				elem[i].style.height = 32 * elem[i].children.length + "px";
				if (hasClass(elem[i], "hide")) {
					elem[i].style.height = "0px";
				} else {
					elem[i].style.height = 32 * elem[i].children.length + "px";
				}
				// submenu-active
				/*
				 * var tmpA = elem[i].getElementsByTagName("a");
				 * for(var i1=0; i1<tmpA.length; i1++){
				 * if(!hasClass(tmpA[i1],
				 * "submenu-a-"+i+"-"+i1)){ addClass(tmpA[i1],
				 * "submenu-a-"+i+"-"+i1); }
				 * if(hasClass(tmpA[i1], "submenu-active")){
				 * removeClass(tmpA[i1], "submenu-active"); } //
				 * console_Log("i"+i.toString()); }
				 */
				if (elem[i].style.height != "0px") {
					// addClass(tmpA[0], "submenu-active");
					// tmpA[0].onclick();
				}
			}
			removeClass(elem[i].parentNode, "open");
		}
		// console_Log(el.style.height);
		if (el.style.height == "0px") {
			removeClass(el.parentNode, "open");
		} else {
			addClass(el.parentNode, "open");
		}
		checkMenuTurnOff();
	}
	return {
		init : init
	}
};

function initDropdownlink() {
	var elem = document.getElementsByClassName("dropdownlink");
	var i = 1;
	if (i <= elem.length) {
		elem[0].onclick = function() {
			Dropdown(document.getElementsByClassName("submenuItems")[0]).init();
		};
	}
	i = 2;
	if (i <= elem.length) {
		elem[1].onclick = function() {
			Dropdown(document.getElementsByClassName("submenuItems")[1]).init();
		};
	}
	i = 3;
	if (i <= elem.length) {
		elem[2].onclick = function() {
			Dropdown(document.getElementsByClassName("submenuItems")[2]).init();
		};
	}
	i = 4;
	if (i <= elem.length) {
		elem[3].onclick = function() {
			Dropdown(document.getElementsByClassName("submenuItems")[3]).init();
		};
	}
	i = 5;
	if (i <= elem.length) {
		elem[4].onclick = function() {
			Dropdown(document.getElementsByClassName("submenuItems")[4]).init();
		};
	}
	i = 6;
	if (i <= elem.length) {
		elem[5].onclick = function() {
			Dropdown(document.getElementsByClassName("submenuItems")[5]).init();
		};
	}
	i = 7;
	if (i <= elem.length) {
		elem[6].onclick = function() {
			Dropdown(document.getElementsByClassName("submenuItems")[6]).init();
		};
	}
	i = 8;
	if (i <= elem.length) {
		elem[7].onclick = function() {
			Dropdown(document.getElementsByClassName("submenuItems")[7]).init();
		};
	}
	i = 9;
	if (i <= elem.length) {
		elem[8].onclick = function() {
			Dropdown(document.getElementsByClassName("submenuItems")[8]).init();
		};
	}
	i = 10;
	if (i <= elem.length) {
		elem[9].onclick = function() {
			Dropdown(document.getElementsByClassName("submenuItems")[9]).init();
		};
	}
}

// initDropdownlink();

// 設定區域顯示或是隱藏
function setIdDisplay(id, val) {
	var tmpID = document.getElementById(id);
	if (!tmpID || tmpID == null || typeof tmpID === "undefined") {
		tmpID = document.getElementsByName(id);
		if (tmpID && tmpID != null && typeof (tmpID) != "undefined") {
			tmpID = tmpID[tmpID.length - 1];
		}
	}
	if (tmpID && tmpID != null && typeof (tmpID) != "undefined") {
		tmpID.style.display = val;
		tmpID = null;
		return true;
	} else {
		tmpID = null;
		return false;
	}
}
function setIdVisibility(id, val) {
	var tmpID = document.getElementById(id);
	if (!tmpID || tmpID == null || typeof tmpID === "undefined") {
		tmpID = document.getElementsByName(id);
		if (tmpID && tmpID != null && typeof (tmpID) != "undefined") {
			tmpID = tmpID[tmpID.length - 1];
		}
	}
	if (tmpID && tmpID != null && typeof (tmpID) != "undefined") {
		tmpID.style.visibility = val;
		tmpID = null;
		return true;
	} else {
		tmpID = null;
		return false;
	}
}

// 彈窗

// 彈窗關閉
/*
 * function btnClose() { setIdDisplay("modalWrapper", "none"); }
 */
// 彈窗關閉 叉叉恢復
function btnClose() {
	setIdDisplay("modalWrapper", "none");
	setIdDisplay("CLSbtn", "block");
}
// 我的投注
function openＭyBet() {
	setIdDisplay("modalWrapper", "block");
	setIdDisplay("MyBetDiv", "block");
	setIdDisplay("MyBetDetailDiv", "none");
	setIdDisplay("MyAddDiv", "none");
	setIdDisplay("MyaddDetailDiv", "none");
	setIdDisplay("MyaddDetailDetailDiv", "none");
	setIdDisplay("BetAddDiv", "none");
	setIdDisplay("BetNowDiv", "none");
	setIdDisplay("ConfirmBetDiv", "none");
	setIdDisplay("RemindBetDiv", "none");
	setIdDisplay("RemindBetDiv2", "none");
	setIdDisplay("MybetRemoveDiv", "none");
	setIdDisplay("MyAddRemoveDiv", "none");
	setIdDisplay("MyaddDetailRemoveDiv", "none");
}

// 我的投注-詳情
function openＭyBetDetail() {
	setIdDisplay("modalWrapper", "block");
	setIdDisplay("MyBetDiv", "none");
	setIdDisplay("MyBetDetailDiv", "block");
	setIdDisplay("MyAddDiv", "none");
	setIdDisplay("MyaddDetailDiv", "none");
	setIdDisplay("MyaddDetailDetailDiv", "none");
	setIdDisplay("BetAddDiv", "none");
	setIdDisplay("BetNowDiv", "none");
	setIdDisplay("ConfirmBetDiv", "none");
	setIdDisplay("RemindBetDiv", "none");
	setIdDisplay("RemindBetDiv2", "none");
	setIdDisplay("MybetRemoveDiv", "none");
	setIdDisplay("MyAddRemoveDiv", "none");
	setIdDisplay("MyaddDetailRemoveDiv", "none");
}

// 我的追號
function openMyAdd() {
	setIdDisplay("modalWrapper", "block");
	setIdDisplay("MyBetDiv", "none");
	setIdDisplay("MyBetDetailDiv", "none");
	setIdDisplay("MyAddDiv", "block");
	setIdDisplay("MyaddDetailDiv", "none");
	setIdDisplay("MyaddDetailDetailDiv", "none");
	setIdDisplay("BetAddDiv", "none");
	setIdDisplay("ConfirmBetDiv", "none");
	setIdDisplay("RemindBetDiv", "none");
	setIdDisplay("RemindBetDiv2", "none");
	setIdDisplay("BetNowDiv", "none");
	setIdDisplay("MybetRemoveDiv", "none");
	setIdDisplay("MyAddRemoveDiv", "none");
	setIdDisplay("MyaddDetailRemoveDiv", "none");
}

// 我的追號-詳情
function openMyAddDetail() {
	setIdDisplay("modalWrapper", "block");
	setIdDisplay("MyBetDiv", "none");
	setIdDisplay("MyBetDetailDiv", "none");
	setIdDisplay("MyAddDiv", "none");
	setIdDisplay("MyaddDetailDiv", "block");
	setIdDisplay("MyaddDetailDetailDiv", "none");
	setIdDisplay("BetAddDiv", "none");
	setIdDisplay("MybetRemoveDiv", "none");
	setIdDisplay("MyAddRemoveDiv", "none");
	setIdDisplay("MyaddDetailRemoveDiv", "none");
}

// 我的追號-詳情-詳情
function openMyAddDetailDetail() {
	setIdDisplay("modalWrapper", "block");
	setIdDisplay("MyBetDiv", "none");
	setIdDisplay("MyBetDetailDiv", "none");
	setIdDisplay("MyAddDiv", "none");
	setIdDisplay("MyaddDetailDiv", "none");
	setIdDisplay("MyaddDetailDetailDiv", "block");
	setIdDisplay("BetAddDiv", "none");
	setIdDisplay("MybetRemoveDiv", "none");
	setIdDisplay("MyAddRemoveDiv", "none");
	setIdDisplay("MyaddDetailRemoveDiv", "none");
}

// 追號投注
function openBetAdd() {
	setIdDisplay("modalWrapper", "block");
	setIdDisplay("MyBetDiv", "none");
	setIdDisplay("MyBetDetailDiv", "none");
	setIdDisplay("MyAddDiv", "none");
	setIdDisplay("MyaddDetailDiv", "none");
	setIdDisplay("MyaddDetailDetailDiv", "none");
	setIdDisplay("BetAddDiv", "block");
	setIdDisplay("NormalAreaDiv", "block");
	setIdDisplay("AdvancedAreaDiv", "none");
	setIdDisplay("ConfirmBetDiv", "none");
	setIdDisplay("RemindBetDiv", "none");
	setIdDisplay("RemindBetDiv2", "none");
	setIdDisplay("BetNowDiv", "none");
	setIdDisplay("MybetRemoveDiv", "none");
	setIdDisplay("MyAddRemoveDiv", "none");
	setIdDisplay("MyaddDetailRemoveDiv", "none");
	addClass(document.getElementById("changeNormalBtn"), "active");
	removeClass(document.getElementById("changeAdvancedBtn"), "active");
}

// 追號投注-普通高級切換
function openTabNormalArea() {
	setIdDisplay("NormalAreaDiv", "block");
	setIdDisplay("AdvancedAreaDiv", "none");
	addClass(document.getElementById("changeNormalBtn"), "active");
	removeClass(document.getElementById("changeAdvancedBtn"), "active");
}
function openTabAdvancedArea() {
	setIdDisplay("NormalAreaDiv", "none");
	setIdDisplay("AdvancedAreaDiv", "block");
	addClass(document.getElementById("changeAdvancedBtn"), "active");
	removeClass(document.getElementById("changeNormalBtn"), "active");
}

// 立即投注
function openBetNow() {
	setIdDisplay("modalWrapper", "block");
	setIdDisplay("MyBetDiv", "none");
	setIdDisplay("MyBetDetailDiv", "none");
	setIdDisplay("MyAddDiv", "none");
	setIdDisplay("MyaddDetailDiv", "none");
	setIdDisplay("MyaddDetailDetailDiv", "none");
	setIdDisplay("BetAddDiv", "none");
	setIdDisplay("ConfirmBetDiv", "none");
	setIdDisplay("RemindBetDiv", "none");
	setIdDisplay("RemindBetDiv2", "none");
	setIdDisplay("BetNowDiv", "block");
	setIdDisplay("MybetRemoveDiv", "none");
	setIdDisplay("MyAddRemoveDiv", "none");
	setIdDisplay("MyaddDetailRemoveDiv", "none");
}

// 確認投注
function openConfirmBet() {
	setIdDisplay("modalWrapper", "block");
	setIdDisplay("MyBetDiv", "none");
	setIdDisplay("MyBetDetailDiv", "none");
	setIdDisplay("MyAddDiv", "none");
	setIdDisplay("MyaddDetailDiv", "none");
	setIdDisplay("MyaddDetailDetailDiv", "none");
	setIdDisplay("BetAddDiv", "none");
	setIdDisplay("ConfirmBetDiv", "block");
	setIdDisplay("RemindBetDiv", "none");
	setIdDisplay("RemindBetDiv2", "none");
	setIdDisplay("BetNowDiv", "none");
	setIdDisplay("MybetRemoveDiv", "none");
	setIdDisplay("MyAddRemoveDiv", "none");
	setIdDisplay("MyaddDetailRemoveDiv", "none");
}

// 溫馨提示
function openRemindBet() {
	setIdDisplay("modalWrapper", "block");
	setIdDisplay("MyBetDiv", "none");
	setIdDisplay("MyBetDetailDiv", "none");
	setIdDisplay("MyAddDiv", "none");
	setIdDisplay("MyaddDetailDiv", "none");
	setIdDisplay("MyaddDetailDetailDiv", "none");
	setIdDisplay("BetAddDiv", "none");
	setIdDisplay("ConfirmBetDiv", "none");
	setIdDisplay("RemindBetDiv", "block");
	setIdDisplay("RemindBetDiv2", "none");
	setIdDisplay("BetNowDiv", "none");
	setIdDisplay("MybetRemoveDiv", "none");
	setIdDisplay("MyAddRemoveDiv", "none");
	setIdDisplay("MyaddDetailRemoveDiv", "none");
}

// 溫馨提示
function openRemindBet2() {
	setIdDisplay("modalWrapper", "block");
	setIdDisplay("MyBetDiv", "none");
	setIdDisplay("MyBetDetailDiv", "none");
	setIdDisplay("MyAddDiv", "none");
	setIdDisplay("MyaddDetailDiv", "none");
	setIdDisplay("MyaddDetailDetailDiv", "none");
	setIdDisplay("BetAddDiv", "none");
	setIdDisplay("ConfirmBetDiv", "none");
	setIdDisplay("RemindBetDiv", "none");
	setIdDisplay("RemindBetDiv2", "block");
	setIdDisplay("BetNowDiv", "none");
	setIdDisplay("MybetRemoveDiv", "none");
	setIdDisplay("MyAddRemoveDiv", "none");
	setIdDisplay("MyaddDetailRemoveDiv", "none");
	setIdDisplay("CLSbtn", "none");
}

// 全螢幕
function openFullScreen() {
	// document.getElementById("LeftSideDiv").classList.toggle("invisible");
	// document.getElementById("RightSideDiv").classList.toggle("big");
	if (hasClass(document.getElementById("LeftSideDiv"), "invisible")) {
		removeClass(document.getElementById("LeftSideDiv"), "invisible");
	} else {
		addClass(document.getElementById("LeftSideDiv"), "invisible");
	}
	if (hasClass(document.getElementById("RightSideDiv"), "big")) {
		removeClass(document.getElementById("RightSideDiv"), "big");
	} else {
		addClass(document.getElementById("RightSideDiv"), "big");
	}
}

// / 顏色切換
function opendarkbtn() {
	removeClass(document.getElementById("RightSideDiv"), "light");
	removeClass(document.getElementById("RightSideDiv"), "lightgray");
	removeClass(document.getElementById("RightSideDiv"), "red");
	removeClass(document.getElementById("RightSideDiv"), "blue");
	removeClass(document.getElementById("RightSideDiv"), "green");
	removeClass(document.getElementById("ContainerLotteryDiv"), "light");
	removeClass(document.getElementById("ContainerLotteryDiv"), "lightgray");
	removeClass(document.getElementById("ContainerLotteryDiv"), "red");
	removeClass(document.getElementById("ContainerLotteryDiv"), "blue");
	removeClass(document.getElementById("ContainerLotteryDiv"), "green");
	removeClass(document.getElementById("ModalContentDiv"), "light");
	removeClass(document.getElementById("ModalContentDiv"), "lightgray");
	removeClass(document.getElementById("ModalContentDiv"), "red");
	removeClass(document.getElementById("ModalContentDiv"), "blue");
	removeClass(document.getElementById("ModalContentDiv"), "green");
	addClass(document.getElementById("ModalContentDiv"), "dark");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[0], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[1], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[2], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[3], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[4], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[5], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[6], "ani-animal1_dark");
}
function openlightbtn() {
	addClass(document.getElementById("RightSideDiv"), "light");
	removeClass(document.getElementById("RightSideDiv"), "lightgray");
	removeClass(document.getElementById("RightSideDiv"), "red");
	removeClass(document.getElementById("RightSideDiv"), "blue");
	removeClass(document.getElementById("RightSideDiv"), "green");
	addClass(document.getElementById("ContainerLotteryDiv"), "light");
	removeClass(document.getElementById("ContainerLotteryDiv"), "lightgray");
	removeClass(document.getElementById("ContainerLotteryDiv"), "red");
	removeClass(document.getElementById("ContainerLotteryDiv"), "blue");
	removeClass(document.getElementById("ContainerLotteryDiv"), "green");
	addClass(document.getElementById("ModalContentDiv"), "light");
	removeClass(document.getElementById("ModalContentDiv"), "lightgray");
	removeClass(document.getElementById("ModalContentDiv"), "red");
	removeClass(document.getElementById("ModalContentDiv"), "blue");
	removeClass(document.getElementById("ModalContentDiv"), "green");
	removeClass(document.getElementById("ModalContentDiv"), "dark");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal7_dark");
	addClass(document.getElementsByClassName("anicommon")[0], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal1_dark");
	addClass(document.getElementsByClassName("anicommon")[1], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal1_dark");
	addClass(document.getElementsByClassName("anicommon")[2], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal1_dark");
	addClass(document.getElementsByClassName("anicommon")[3], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal1_dark");
	addClass(document.getElementsByClassName("anicommon")[4], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal1_dark");
	addClass(document.getElementsByClassName("anicommon")[5], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal1_dark");
	addClass(document.getElementsByClassName("anicommon")[6], "ani-animal1_light");

}
function openlightgraybtn() {
	addClass(document.getElementById("RightSideDiv"), "light");
	addClass(document.getElementById("RightSideDiv"), "lightgray");
	removeClass(document.getElementById("RightSideDiv"), "red");
	removeClass(document.getElementById("RightSideDiv"), "blue");
	removeClass(document.getElementById("RightSideDiv"), "green");
	addClass(document.getElementById("ContainerLotteryDiv"), "light");
	addClass(document.getElementById("ContainerLotteryDiv"), "lightgray");
	removeClass(document.getElementById("ContainerLotteryDiv"), "red");
	removeClass(document.getElementById("ContainerLotteryDiv"), "blue");
	removeClass(document.getElementById("ContainerLotteryDiv"), "green");
	addClass(document.getElementById("ModalContentDiv"), "light");
	addClass(document.getElementById("ModalContentDiv"), "lightgray");
	removeClass(document.getElementById("ModalContentDiv"), "red");
	removeClass(document.getElementById("ModalContentDiv"), "blue");
	removeClass(document.getElementById("ModalContentDiv"), "green");
	removeClass(document.getElementById("ModalContentDiv"), "dark");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal1_dark");
	addClass(document.getElementsByClassName("anicommon")[0], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal1_dark");
	addClass(document.getElementsByClassName("anicommon")[1], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal1_dark");
	addClass(document.getElementsByClassName("anicommon")[2], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal1_dark");
	addClass(document.getElementsByClassName("anicommon")[3], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal1_dark");
	addClass(document.getElementsByClassName("anicommon")[4], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal1_dark");
	addClass(document.getElementsByClassName("anicommon")[5], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal1_dark");
	addClass(document.getElementsByClassName("anicommon")[6], "ani-animal7_light");

}
function openredbtn() {
	removeClass(document.getElementById("RightSideDiv"), "light");
	removeClass(document.getElementById("RightSideDiv"), "lightgray");
	addClass(document.getElementById("RightSideDiv"), "red");
	removeClass(document.getElementById("RightSideDiv"), "blue");
	removeClass(document.getElementById("RightSideDiv"), "green");
	removeClass(document.getElementById("ContainerLotteryDiv"), "light");
	removeClass(document.getElementById("ContainerLotteryDiv"), "lightgray");
	addClass(document.getElementById("ContainerLotteryDiv"), "red");
	removeClass(document.getElementById("ContainerLotteryDiv"), "blue");
	removeClass(document.getElementById("ContainerLotteryDiv"), "green");
	removeClass(document.getElementById("ModalContentDiv"), "light");
	removeClass(document.getElementById("ModalContentDiv"), "lightgray");
	addClass(document.getElementById("ModalContentDiv"), "red");
	removeClass(document.getElementById("ModalContentDiv"), "blue");
	removeClass(document.getElementById("ModalContentDiv"), "green");
	addClass(document.getElementById("ModalContentDiv"), "dark");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[0], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[1], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[2], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[3], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[4], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[5], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[6], "ani-animal1_dark");

}
function opengreenbtn() {
	removeClass(document.getElementById("RightSideDiv"), "light");
	removeClass(document.getElementById("RightSideDiv"), "lightgray");
	removeClass(document.getElementById("RightSideDiv"), "red");
	removeClass(document.getElementById("RightSideDiv"), "blue");
	addClass(document.getElementById("RightSideDiv"), "green");
	removeClass(document.getElementById("ContainerLotteryDiv"), "light");
	removeClass(document.getElementById("ContainerLotteryDiv"), "lightgray");
	removeClass(document.getElementById("ContainerLotteryDiv"), "red");
	removeClass(document.getElementById("ContainerLotteryDiv"), "blue");
	addClass(document.getElementById("ContainerLotteryDiv"), "green");
	removeClass(document.getElementById("ModalContentDiv"), "light");
	removeClass(document.getElementById("ModalContentDiv"), "lightgray");
	removeClass(document.getElementById("ModalContentDiv"), "red");
	removeClass(document.getElementById("ModalContentDiv"), "blue");
	addClass(document.getElementById("ModalContentDiv"), "green");
	addClass(document.getElementById("ModalContentDiv"), "dark");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[0], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[1], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[2], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[3], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[4], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[5], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[6], "ani-animal1_dark");
}
function openbluebtn() {
	removeClass(document.getElementById("RightSideDiv"), "light");
	removeClass(document.getElementById("RightSideDiv"), "lightgray");
	removeClass(document.getElementById("RightSideDiv"), "red");
	addClass(document.getElementById("RightSideDiv"), "blue");
	removeClass(document.getElementById("RightSideDiv"), "green");
	removeClass(document.getElementById("ContainerLotteryDiv"), "light");
	removeClass(document.getElementById("ContainerLotteryDiv"), "lightgray");
	removeClass(document.getElementById("ContainerLotteryDiv"), "red");
	addClass(document.getElementById("ContainerLotteryDiv"), "blue");
	removeClass(document.getElementById("ContainerLotteryDiv"), "green");
	removeClass(document.getElementById("ModalContentDiv"), "light");
	removeClass(document.getElementById("ModalContentDiv"), "lightgray");
	removeClass(document.getElementById("ModalContentDiv"), "red");
	addClass(document.getElementById("ModalContentDiv"), "blue");
	removeClass(document.getElementById("ModalContentDiv"), "green");
	addClass(document.getElementById("ModalContentDiv"), "dark");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[0], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[0], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[1], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[1], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[2], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[2], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[3], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[3], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[4], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[4], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[5], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[5], "ani-animal1_dark");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal1_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal2_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal3_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal4_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal5_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal6_light");
	removeClass(document.getElementsByClassName("anicommon")[6], "ani-animal7_light");
	addClass(document.getElementsByClassName("anicommon")[6], "ani-animal1_dark");
}

// 我的投注撤除著單
function openremove() {
	setIdDisplay("modalWrapper", "block");
	setIdDisplay("MyBetDiv", "none");
	setIdDisplay("MyBetDetailDiv", "none");
	setIdDisplay("MyAddDiv", "none");
	setIdDisplay("MyaddDetailDiv", "none");
	setIdDisplay("BetAddDiv", "none");
	setIdDisplay("BetNowDiv", "none");
	setIdDisplay("ConfirmBetDiv", "none");
	setIdDisplay("MybetRemoveDiv", "block");
	setIdDisplay("MyAddRemoveDiv", "none");
	setIdDisplay("MyaddDetailRemoveDiv", "none");
}

// 我的追好撤除著單
function openMyAddRemove() {
	setIdDisplay("modalWrapper", "block");
	setIdDisplay("MyBetDiv", "none");
	setIdDisplay("MyBetDetailDiv", "none");
	setIdDisplay("MyAddDiv", "none");
	setIdDisplay("MyaddDetailDiv", "none");
	setIdDisplay("BetAddDiv", "none");
	setIdDisplay("BetNowDiv", "none");
	setIdDisplay("ConfirmBetDiv", "none");
	setIdDisplay("MybetRemoveDiv", "none");
	setIdDisplay("MyAddRemoveDiv", "block");
	setIdDisplay("MyaddDetailRemoveDiv", "none");
}

// 我的追好詳情撤除著單
function openmyadddetailremove() {
	setIdDisplay("modalWrapper", "block");
	setIdDisplay("MyBetDiv", "none");
	setIdDisplay("MyBetDetailDiv", "none");
	setIdDisplay("MyAddDiv", "none");
	setIdDisplay("MyaddDetailDiv", "none");
	setIdDisplay("BetAddDiv", "none");
	setIdDisplay("BetNowDiv", "none");
	setIdDisplay("ConfirmBetDiv", "none");
	setIdDisplay("MybetRemoveDiv", "none");
	setIdDisplay("MyAddRemoveDiv", "none");
	setIdDisplay("MyaddDetailRemoveDiv", "block");
}
// 普通追號生成計畫
function opennormallistarea() {
	setIdDisplay("NormalListArea", "inline-table");
}
// 高級追號生成計畫
function openadvancelistarea() {
	setIdDisplay("AdvancedListArea", "inline-table");
}
// checkbox
function chk(input) {
	/*
	 * for(var i=0;i<document.form1.c1.length;i++) {
	 * document.form1.c1[i].checked = false; }
	 * 
	 * input.checked = true; return true;
	 */
}

// button disabled
function btnDisabled() {
	var xxx = document.querySelectorAll("#MyBetDiv div table tr td label input").checked;
	// document.getElementById("MyBetDiv").getElementsByTagName("div")[1].getElementsByTagName("table")[0].getElementsByTagName("tr")[0].getElementsByTagName("td")[0].getElementsByTagName("label")[0].getElementsByTagName("input")[0].checked;
	if (xxx = true) {
		document.getElementById("myBetBtn").style.opacity = "1";
	} else {
		document.getElementById("myBetBtn").style.opacity = "0.1";
	}
}

// 全彩種 選單 第一層(分類 main_title)
// 时时彩(SSC)
function tabSSC() {
	var clearName = [];
	var hiddenName = [ "sixlottery-list-area", "betlimit", "sixlotterybtn-area" ];
	var showName = [ "right-side", "tab-lottery-ssc", "five-balls", "kind-star5-ssc", "kind-star4f-ssc", "kind-star4b-ssc", "kind-star3f-ssc", "kind-star3m-ssc", "kind-star3b-ssc",
		"kind-star2f-ssc", "kind-star2b-ssc", "kind-star1-ssc", "kind-bs-ssc", "kind-noassign-ssc", "kind-funny-ssc", "kind-any-ssc", "kind-dtt-ssc", "lottery-list-area", "right",
		"moneyunit", "multiplearea", "highestdollar" ];
	initBall(clearName, hiddenName, showName, 0);
	removeClass(document.getElementsByClassName("bet-area")[0], "six-bet-area");
	removeClass(document.getElementsByClassName("lottery-number")[0], "six-lottery-number");
	removeClass(document.getElementsByClassName("list-area lottery-list-area")[0], "sixlottery-list-area");
}

// 分分彩(FFC)
function tabFFC() {
	var clearName = [];
	var hiddenName = [ "sixlottery-list-area", "betlimit", "sixlotterybtn-area" ];
	var showName = [ "right-side", "tab-lottery-ffc", "five-balls", "kind-star5-ffc", "kind-star4f-ffc", "kind-star4b-ffc", "kind-star3f-ffc", "kind-star3m-ffc", "kind-star3b-ffc",
		"kind-star2f-ffc", "kind-star2b-ffc", "kind-star1-ffc", "kind-bs-ffc", "kind-noassign-ffc", "kind-funny-ffc", "kind-any-ffc", "kind-dtt-ffc", "lottery-list-area", "right",
		"moneyunit", "multiplearea", "highestdollar" ];
	initBall(clearName, hiddenName, showName, 0);
	removeClass(document.getElementsByClassName("bet-area")[0], "six-bet-area");
	removeClass(document.getElementsByClassName("lottery-number")[0], "six-lottery-number");
	removeClass(document.getElementsByClassName("list-area lottery-list-area")[0], "sixlottery-list-area");
}

// 11选5(115)
function tab115() {
	var clearName = [];
	var hiddenName = [ "sixlottery-list-area", "betlimit", "sixlotterybtn-area" ];
	var showName = [ "right-side", "tab-lottery-115", "five-balls", "kind-star3-115", "kind-star2-115", "kind-noassign-115", "kind-sd-115", "kind-qm-115", "kind-assign-115", "kind-any-multi-115",
		"kind-any-single-115", "kind-any-promise-115", "lottery-list-area", "right", "moneyunit", "multiplearea", "highestdollar" ];
	initBall(clearName, hiddenName, showName, 0);
	removeClass(document.getElementsByClassName("bet-area")[0], "six-bet-area");
	removeClass(document.getElementsByClassName("lottery-number")[0], "six-lottery-number");
	removeClass(document.getElementsByClassName("list-area lottery-list-area")[0], "sixlottery-list-area");
}

// 快三(Q3)
function tabQuick3() {
	var clearName = [];
	var hiddenName = [ "sixlottery-list-area", "betlimit", "sixlotterybtn-area" ];
	var showName = [ "right-side", "tab-lottery-q3", "three-dices", "kind-total-q3", "kind-same3-q3", "kind-same2-q3", "kind-no3-q3", "kind-no2-q3", "kind-link3-q3", "kind-bs-q3", "kind-sd-q3",
		"kind-gr-q3", "kind-color-q3", "lottery-list-area", "right", "moneyunit", "multiplearea", "highestdollar" ];
	initBall(clearName, hiddenName, showName, 0);
	removeClass(document.getElementsByClassName("bet-area")[0], "six-bet-area");
	removeClass(document.getElementsByClassName("lottery-number")[0], "six-lottery-number");
	removeClass(document.getElementsByClassName("list-area lottery-list-area")[0], "sixlottery-list-area");

}

// 六合彩(Six)
function tabSix() {
	var clearName = [];

	var hiddenName = [ "lottery-list-area", "right", "moneyunit", "multiplearea", "highestdollar" ];
	var showName = [ "right-side", "tab-lottery-six", "seven-balls", "kind-six-1", "kind-six-2", "kind-six-3", "kind-six-4", "kind-six-5", "kind-six-6", "kind-six-7", "kind-six-8", "kind-six-9",
		"kind-six-10", "kind-six-11", "kind-six-12", "sixlottery-list-area", "betlimit", "sixlotterybtn-area", "six-bet-area" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("bet-area")[0], "six-bet-area");
	addClass(document.getElementsByClassName("lottery-number")[0], "six-lottery-number");
	addClass(document.getElementsByClassName("list-area lottery-list-area")[0], "sixlottery-list-area");

}

// 六合彩_版面切換_開
// function sixLotteryOn() {
// document.getElementsByClassName("right")[0].style.visibility = "hidden";
// document.getElementsByClassName("bet-area")[0].style.width = "134%";
// document.getElementsByClassName("sixlottery-list-area")[0].style.display =
// "block";
// document.getElementsByClassName("sixlotterybtn-area")[0].style.display =
// "inline-block";
// document.getElementsByClassName("list-area")[0].style.display = "none";
// document.getElementsByClassName("lottery-number")[0].style.width = 540+'px';
// document.getElementsByClassName("lottery-number")[0].style.top = 20+'px';
// document.getElementById("betLimit").style.display = "block";
// document.getElementById("MoneyUnit").style.display = "none";
// document.getElementById("MultiPle").style.display = "none";
// document.getElementById("HighestDollar").style.display = "none";

// }

// 六合彩_版面切換_關
// function sixLotteryOff() {
// document.getElementsByClassName("right")[0].style.visibility = "visible";
// document.getElementsByClassName("bet-area")[0].style.width = "101%";
// document.getElementsByClassName("sixlottery-list-area")[0].style.display =
// "none";
// document.getElementsByClassName("sixlotterybtn-area")[0].style.display =
// "none";
// document.getElementsByClassName("list-area")[0].style.display = "block";
// document.getElementsByClassName("lottery-number")[0].style.width = 504+'px';
// document.getElementsByClassName("lottery-number")[0].style.top = 24+'px';
// document.getElementById("betLimit").style.display = "none";
// document.getElementById("MoneyUnit").style.display = "block";
// document.getElementById("MultiPle").style.display = "block";
// document.getElementById("HighestDollar").display = "block";

// }

// 北京PK拾
function tabPK10() {
	var clearName = [];
	var hiddenName = [ "sixlottery-list-area", "betlimit", "sixlotterybtn-area" ];
	var showName = [ "right-side", "tab-lottery-pk10", "ten-balls", "kind-win-pk10", "kind-gr-pk10", "kind-dt-pk10", "kind-star3fb-pk10", "kind-star2fb-pk10", "lottery-list-area", "right",
		"moneyunit", "multiplearea", "highestdollar" ];
	initBall(clearName, hiddenName, showName, 0);
	removeClass(document.getElementsByClassName("bet-area")[0], "six-bet-area");
	removeClass(document.getElementsByClassName("lottery-number")[0], "six-lottery-number");
	removeClass(document.getElementsByClassName("list-area lottery-list-area")[0], "sixlottery-list-area");
	addClass(document.getElementsByClassName("sixlotterybtn-area")[0], "invisible");
}

// 排列三/五(35)
function tab35() {
	var clearName = [];
	var hiddenName = [ "sixlottery-list-area", "betlimit", "sixlotterybtn-area" ];
	var showName = [ "right-side", "tab-lottery-35", "five-balls", "kind-star5-35", "kind-star4f-35", "kind-star4b-35", "kind-star3f-35", "kind-star3m-35", "kind-star3b-35", "kind-star2f-35",
		"kind-star2b-35", "kind-star1-35", "kind-bs-35", "kind-noassign-35", "kind-funny-35", "kind-any-35", "kind-dtt-35", "lottery-list-area", "right", "moneyunit", "multiplearea",
		"highestdollar" ];
	initBall(clearName, hiddenName, showName, 0);
	removeClass(document.getElementsByClassName("bet-area")[0], "six-bet-area");
	removeClass(document.getElementsByClassName("lottery-number")[0], "six-lottery-number");
	removeClass(document.getElementsByClassName("list-area lottery-list-area")[0], "sixlottery-list-area");
	addClass(document.getElementsByClassName("sixlotterybtn-area")[0], "invisible");
}

// 福彩3D(3d)
function tab3D() {
	var clearName = [];
	var hiddenName = [ "sixlottery-list-area", "betlimit", "sixlotterybtn-area" ];
	var showName = [ "right-side", "tab-lottery-3d", "three-balls", "kind-star3-3d", "kind-star2f-3d", "kind-star2b-3d", "kind-star1-3d", "kind-noassign-3d", "kind-dtt-3d", "lottery-list-area",
		"right", "moneyunit", "multiplearea", "highestdollar" ];
	initBall(clearName, hiddenName, showName, 0);
	removeClass(document.getElementsByClassName("bet-area")[0], "six-bet-area");
	removeClass(document.getElementsByClassName("lottery-number")[0], "six-lottery-number");
	removeClass(document.getElementsByClassName("list-area lottery-list-area")[0], "sixlottery-list-area");
	addClass(document.getElementsByClassName("sixlotterybtn-area")[0], "invisible");

}

// 全彩種 選單 第二層(玩法 mid_title)
// 时时彩
function starFiveSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ssc", "pick-star5-direct-ssc", "pick-star5-direct-multi-ssc", "pick-star5-direct-single-ssc", "pick-star5-group-ssc", "pick-star5-group-120-ssc",
		"pick-star5-group-60-ssc", "pick-star5-group-30-ssc", "pick-star5-group-20-ssc", "pick-star5-group-10-ssc", "pick-star5-group-05-ssc", "pick-star5-other-ssc",
		"pick-star5-other-total-bs-ssc", "pick-star5-other-total-bsgroup-ssc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star5-ssc")[0], "kind-act");
}

// 分分彩
function starFiveFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ffc", "pick-star5-direct-ffc", "pick-star5-direct-multi-ffc", "pick-star5-direct-single-ffc", "pick-star5-group-ffc", "pick-star5-group-120-ffc",
		"pick-star5-group-60-ffc", "pick-star5-group-30-ffc", "pick-star5-group-20-ffc", "pick-star5-group-10-ffc", "pick-star5-group-05-ffc", "pick-star5-other-ffc",
		"pick-star5-other-total-bs-ffc", "pick-star5-other-total-bsgroup-ffc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star5-ffc")[0], "kind-act");
}

// 11选5
function starThree115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-115", "pick-star3-direct-115", "pick-star3-dir-f3-multi-115", "pick-star3-dir-f3-single-115", "pick-star3-group-115", "pick-star3-group-f3-multi-115",
		"pick-star3-group-f3-single-115", "pick-star3-group-f3-pull-115" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star3-115")[0], "kind-act");
}

// 排列三/五
function starFive35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-35", "pick-star5-direct-35", "pick-star5-direct-multi-35", "pick-star5-direct-single-35", "pick-star5-group-35", "pick-star5-group-120-35",
		"pick-star5-group-60-35", "pick-star5-group-30-35", "pick-star5-group-20-35", "pick-star5-group-10-35", "pick-star5-group-05-35", "pick-star5-other-35",
		"pick-star5-other-total-bs-35", "pick-star5-other-total-bsgroup-35" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("kind-star5-35")[0], "kind-act");
}

// 3D
function starThree3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-3d", "pick-star3-direct-3d", "pick-star3-direct-multi-3d", "pick-star3-direct-single-3d", "pick-star3-direct-total-3d", "pick-star3-group-3d",
		"pick-star3-group-03v2-3d", "pick-star3-group-03v2-single-3d", "pick-star3-group-06v2-3d", "pick-star3-group-06v2-single-3d", "pick-star3-group-mix-3d", "pick-star3-group-total-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star3-3d")[0], "kind-act");
}

// PK10
function winPK10() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-pk10", "pick-win-pk10", "pick-win-total-pk10", "pick-win-bs-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-win-pk10")[0], "kind-act");
}

// 六合彩
function winSix() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-six", "pick-win-six1", "pick-win-six1-1" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-six-1")[0], "kind-act");
}

// 快三
function TotalQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-q3", "pick-total-q3", "pick-total-q3-btn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-total-q3")[0], "kind-act");
}

// 時時彩 選單 第三層選單(投注 min_title)
// 时时彩 五星 直选复式
function starFiveDirectMultiSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-direct-multi-ssc")[0], "pick-act");
}

// 时时彩 五星 直选单式
function starFiveDirectSingleSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-direct-single-ssc")[0], "pick-act");
}

// 时时彩 五星 组选120
function starFiveGroup120SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star5-group120", "win-number-star5-group-120" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-group-120-ssc")[0], "pick-act");
}

// 时时彩 五星 组选60
function starFiveGroup60SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-single", "ball-star-group-double", "win-number-star5-group-60" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-group-60-ssc")[0], "pick-act");
}

// 时时彩 五星 组选30
function starFiveGroup30SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-single", "ball-star-group-double", "win-number-star5-group-30" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-group-30-ssc")[0], "pick-act");
}

// 时时彩 五星 组选20
function starFiveGroup20SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-single", "ball-star-group-triple", "win-number-star5-group-20" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-group-20-ssc")[0], "pick-act");
}

// 时时彩 五星 组选10
function starFiveGroup10SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-triple", "ball-star-group-double", "win-number-star5-group-10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-group-10-ssc")[0], "pick-act");
}

// 时时彩 五星 组选5
function starFiveGroup05SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-single", "ball-star-group-quadruple", "win-number-star5-group-05" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-group-05-ssc")[0], "pick-act");
}

// 时时彩 五星 其他 总和大小单双
function starFiveOtherTotalBSSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star5-other-total-bs", "win-number-star5-total-bs" ]; // ,"win-number-star5"];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-other-total-bs-ssc")[0], "pick-act");
}

// 时时彩 五星 其他 总和组合大小单双
function starFiveOtherTotalBSGroupSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star5-other-total-bsgroup", "win-number-star5-total-bs" ]; // ,"win-number-star5"];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-other-total-bsgroup-ssc")[0], "pick-act");
}

// 时时彩 前四(mid_title)
function starFourFrontSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ssc", "pick-star4f-direct-ssc", "pick-star4f-direct-multi-ssc", "pick-star4f-direct-single-ssc", "pick-star4f-group-ssc", "pick-star4f-group-24-ssc",
		"pick-star4f-group-12-ssc", "pick-star4f-group-06-ssc", "pick-star4f-group-04-ssc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star4f-ssc")[0], "kind-act");
}

// 时时彩 前四 直选复式
function starFourFrontDirectMultiSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "win-number-star4f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4f-direct-multi-ssc")[0], "pick-act");
}

// 时时彩 前四 直选单式
function starFourFrontDirectSingleSSC() {
	var clearName = [];
	var hiddenName = [ "import-checkbox-area" ];
	var showName = [ "import-area", "win-number-star4f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4f-direct-single-ssc")[0], "pick-act");
}

// 时时彩 前四 组24
function starFourFrontGroup24SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star4f-group-24" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4f-group-24-ssc")[0], "pick-act");
}

// 时时彩 前四 组12
function starFourFrontGroup12SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-double", "ball-star-group-single", "win-number-star4f-group-12" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4f-group-12-ssc")[0], "pick-act");
}

// 时时彩 前四 组06
function starFourFrontGroup06SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-double", "win-number-star4f-group-06" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4f-group-06-ssc")[0], "pick-act");
}

// 时时彩 前四 组04
function starFourFrontGroup04SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-triple", "ball-star-group-single", "win-number-star4f-group-04" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4f-group-04-ssc")[0], "pick-act");
}

// 时时彩 后四(mid_title)
function starFourBackSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ssc", "pick-star4b-direct-ssc", "pick-star4b-direct-multi-ssc", "pick-star4b-direct-single-ssc", "pick-star4b-group-ssc", "pick-star4b-group-24-ssc",
		"pick-star4b-group-12-ssc", "pick-star4b-group-06-ssc", "pick-star4b-group-04-ssc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star4b-ssc")[0], "kind-act");
}

// 时时彩 后四 直选复式
function starFourBackDirectMultiSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star4b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4b-direct-multi-ssc")[0], "pick-act");
}

// 时时彩 后四 直选单式
function starFourBackDirectSingleSSC() {
	var clearName = [];
	var hiddenName = [ "import-checkbox-area" ];
	var showName = [ "import-area", "win-number-star4b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4b-direct-single-ssc")[0], "pick-act");
}

// 时时彩 后四 组24
function starFourBackGroup24SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star4b-group-24" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4b-group-24-ssc")[0], "pick-act");
}

// 时时彩 后四 组12
function starFourBackGroup12SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-double", "ball-star-group-single", "win-number-star4b-group-12" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4b-group-12-ssc")[0], "pick-act");
}

// 时时彩 后四 组06
function starFourBackGroup06SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-double", "win-number-star4b-group-06" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4b-group-06-ssc")[0], "pick-act");
}

// 时时彩 后四 组04
function starFourBackGroup04SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-triple", "ball-star-group-single", "win-number-star4b-group-04" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4b-group-04-ssc")[0], "pick-act");
}

// 时时彩 前三(mid_title)
function starThreeFrontSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ssc", "pick-star3f-direct-ssc", "pick-star3f-direct-multi-ssc", "pick-star3f-direct-single-ssc", "pick-star3f-direct-total-ssc", "pick-star3f-direct-cross-ssc",
		"pick-star3f-group-ssc", "pick-star3f-group-03v2-ssc", "pick-star3f-group-03v2-single-ssc", "pick-star3f-group-06v2-ssc", "pick-star3f-group-06v2-single-ssc",
		"pick-star3f-group-mix-ssc", "pick-star3f-group-total-ssc", "pick-star3f-promise-ssc", "pick-star3f-other-ssc", "pick-star3f-other-total-num-ssc", "pick-star3f-other-jaguar-ssc",
		"pick-star3f-other-smooth-ssc", "pick-star3f-other-pair-ssc", "pick-star3f-other-half-ssc", "pick-star3f-other-mix6-ssc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star3f-ssc")[0], "kind-act");
}

// 时时彩 前三 直选复式
function starThreeFrontDirectMultiSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten-thousand", "ball-thousand", "ball-hundred", "win-number-star3f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-direct-multi-ssc")[0], "pick-act");
}

// 时时彩 前三 直选单式
function starThreeFrontDirectSingleSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-direct-single-ssc")[0], "pick-act");
}

// 时时彩 前三 直选和值
function starThreeFrontDirectTotalSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dt", "win-number-star3f-direct-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-direct-total-ssc")[0], "pick-act");
}

// 时时彩 前三 直选跨度
function starThreeFrontDirectCrossSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dc", "win-number-star3f-direct-cross" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-direct-cross-ssc")[0], "pick-act");
}

// 时时彩 前三 组三
function starThreeFrontGroup03v2SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-03", "win-number-star3f-group-03" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-group-03v2-ssc")[0], "pick-act");
}

// 时时彩 前三 组六
function starThreeFrontGroup06v2SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-06", "win-number-star3f-group-06" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-group-06v2-ssc")[0], "pick-act");
}

// 时时彩 前三 组三单式
function starThreeFrontGroup03v2singleSSC() {
	var clearName = [];
	var hiddenName = [ "import-checkbox-area" ];
	var showName = [ "import-area", "help-select", "win-number-star3f-group-03" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-group-03v2-single-ssc")[0], "pick-act");
}

// 时时彩 前三 组六单式
function starThreeFrontGroup06v2singleSSC() {
	var clearName = [];
	var hiddenName = [ "import-checkbox-area" ];
	var showName = [ "import-area", "help-select", "win-number-star3f-group-06" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-group-06v2-single-ssc")[0], "pick-act");
}

// 时时彩 前三 混合组选
function starThreeFrontGroupMixSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3f-group-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-group-mix-ssc")[0], "pick-act");
}

// 时时彩 前三 组选和值
function starThreeFrontGroupTotalSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-gt", "win-number-star3f-direct-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-group-total-ssc")[0], "pick-act");
}

// 时时彩 前三 包胆
function starThreeFrontPromiseSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star3f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-promise-ssc")[0], "pick-act");
}

// 时时彩 前三 其他 和值尾数
function starThreeFrontTotalNumSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-notitle", "win-number-star3f-other-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-other-total-num-ssc")[0], "pick-act");
}

// 时时彩 前三 其他 豹子
function starThreeFrontJaquarSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-jaguar", "win-number-star3f-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-other-jaguar-ssc")[0], "pick-act");
}

// 时时彩 前三 其他 顺子
function starThreeFrontSmoothSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-smooth", "win-number-star3f-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-other-smooth-ssc")[0], "pick-act");
}

// 时时彩 前三 其他 对子
function starThreeFrontPairSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-pair", "win-number-star3f-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-other-pair-ssc")[0], "pick-act");
}

// 时时彩 前三 其他 半顺
function starThreeFrontHalfSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-half", "win-number-star3f-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-other-half-ssc")[0], "pick-act");
}

// 时时彩 前三 其他 杂六
function starThreeFrontMix6SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-mix6", "win-number-star3f-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-other-mix6-ssc")[0], "pick-act");
}

// 时时彩 中三(mid_title)
function starThreeMiddleSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ssc", "pick-star3m-direct-ssc", "pick-star3m-direct-multi-ssc", "pick-star3m-direct-single-ssc", "pick-star3m-direct-total-ssc", "pick-star3m-direct-cross-ssc",
		"pick-star3m-group-ssc", "pick-star3m-group-03v2-ssc", "pick-star3m-group-03v2-single-ssc", "pick-star3m-group-06v2-ssc", "pick-star3m-group-06v2-single-ssc",
		"pick-star3m-group-mix-ssc", "pick-star3m-group-total-ssc", "pick-star3m-promise-ssc", "pick-star3m-other-ssc", "pick-star3m-other-total-num-ssc", "pick-star3m-other-jaguar-ssc",
		"pick-star3m-other-smooth-ssc", "pick-star3m-other-pair-ssc", "pick-star3m-other-half-ssc", "pick-star3m-other-mix6-ssc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star3m-ssc")[0], "kind-act");
}

// 时时彩 中三 直选复式
function starThreeMiddleDirectMultiSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-thousand", "ball-hundred", "ball-ten", "win-number-star3m" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-direct-multi-ssc")[0], "pick-act");
}

// 时时彩 中三 直选单式
function starThreeMiddleDirectSingleSSC() {
	var clearName = [];
	var hiddenName = [ "import-checkbox-area" ];
	var showName = [ "import-area", "help-select", "win-number-star3m" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-direct-single-ssc")[0], "pick-act");
}

// 时时彩 中三 直选和值
function starThreeMiddleDirectTotalSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dt", "win-number-star3m-direct-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-direct-total-ssc")[0], "pick-act");
}

// 时时彩 中三 直选跨度
function starThreeMiddleDirectCrossSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dc", "win-number-star3m-direct-cross" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-direct-cross-ssc")[0], "pick-act");
}

// 时时彩 中三 组三
function starThreeMiddleGroup03v2SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-03", "win-number-star3m-group-03" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-group-03v2-ssc")[0], "pick-act");
}

// 时时彩 中三 组六
function starThreeMiddleGroup06v2SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-06", "win-number-star3m-group-06" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-group-06v2-ssc")[0], "pick-act");
}

// 时时彩 中三 组三单式
function starThreeMiddleGroup03v2singleSSC() {
	var clearName = [];
	var hiddenName = [ "import-checkbox-area" ];
	var showName = [ "import-area", "help-select", "win-number-star3m-group-03" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-group-03v2-single-ssc")[0], "pick-act");
}

// 时时彩 中三 组六单式
function starThreeMiddleGroup06v2singleSSC() {
	var clearName = [];
	var hiddenName = [ "import-checkbox-area" ];
	var showName = [ "import-area", "help-select", "win-number-star3m-group-06" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-group-06v2-single-ssc")[0], "pick-act");
}

// 时时彩 中三 混合组选
function starThreeMiddleGroupMixSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3m-group-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-group-mix-ssc")[0], "pick-act");
}

// 时时彩 中三 组选和值
function starThreeMiddleGroupTotalSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-gt", "win-number-star3m-direct-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-group-total-ssc")[0], "pick-act");
}

// 时时彩 中三 包胆
function starThreeMiddlePromiseSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star3m" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-promise-ssc")[0], "pick-act");
}

// 时时彩 中三 其他 和值尾数
function starThreeMiddleTotalNumSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-notitle", "win-number-star3m-other-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-other-total-num-ssc")[0], "pick-act");
}

// 时时彩 中三 其他 豹子
function starThreeMiddleJaquarSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-jaguar", "win-number-star3m-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-other-jaguar-ssc")[0], "pick-act");
}

// 时时彩 中三 其他 顺子
function starThreeMiddleSmoothSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-smooth", "win-number-star3m-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-other-smooth-ssc")[0], "pick-act");
}

// 时时彩 中三 其他 对子
function starThreeMiddlePairSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-pair", "win-number-star3m-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-other-pair-ssc")[0], "pick-act");
}

// 时时彩 中三 其他 半顺
function starThreeMiddleHalfSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-half", "win-number-star3m-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-other-half-ssc")[0], "pick-act");
}

// 时时彩 中三 其他 杂六
function starThreeMiddleMix6SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-mix6", "win-number-star3m-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star3m-ssc")[0], "kind-act");
	addClass(document.getElementsByClassName("pick-star3m-other-mix6-ssc")[0], "pick-act");
}

// 时时彩 后三(mid_title)
function starThreeBackSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ssc", "pick-star3b-direct-ssc", "pick-star3b-direct-multi-ssc", "pick-star3b-direct-single-ssc", "pick-star3b-direct-total-ssc", "pick-star3b-direct-cross-ssc",
		"pick-star3b-group-ssc", "pick-star3b-group-03v2-ssc", "pick-star3b-group-03v2-single-ssc", "pick-star3b-group-06v2-ssc", "pick-star3b-group-06v2-single-ssc",
		"pick-star3b-group-mix-ssc", "pick-star3b-group-total-ssc", "pick-star3b-promise-ssc", "pick-star3b-other-ssc", "pick-star3b-other-total-num-ssc", "pick-star3b-other-jaguar-ssc",
		"pick-star3b-other-smooth-ssc", "pick-star3b-other-pair-ssc", "pick-star3b-other-half-ssc", "pick-star3b-other-mix6-ssc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star3b-ssc")[0], "kind-act");
}

// 时时彩 后三 直选复式
function starThreeBackDirectMultiSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-hundred", "ball-ten", "ball-one", "win-number-star3b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-direct-multi-ssc")[0], "pick-act");
}

// 时时彩 后三 直选单式
function starThreeBackDirectSingleSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-direct-single-ssc")[0], "pick-act");
}

// 时时彩 后三 直选和值
function starThreeBackDirectTotalSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dt", "win-number-star3b-direct-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-direct-total-ssc")[0], "pick-act");
}

// 时时彩 后三 直选跨度
function starThreeBackDirectCrossSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dc", "win-number-star3b-direct-cross" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-direct-cross-ssc")[0], "pick-act");
}

// 时时彩 后三 组三
function starThreeBackGroup03v2SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-03", "win-number-star3b-group-03" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-group-03v2-ssc")[0], "pick-act");
}

// 时时彩 后三 组六
function starThreeBackGroup06v2SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-06", "win-number-star3b-group-06" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-group-06v2-ssc")[0], "pick-act");
}

// 时时彩 后三 组三单式
function starThreeBackGroup03v2singleSSC() {
	var clearName = [];
	var hiddenName = [ "import-checkbox-area" ];
	var showName = [ "import-area", "help-select", "win-number-star3b-group-03" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-group-03v2-single-ssc")[0], "pick-act");
}

// 时时彩 后三 组六单式
function starThreeBackGroup06v2singleSSC() {
	var clearName = [];
	var hiddenName = [ "import-checkbox-area" ];
	var showName = [ "import-area", "help-select", "win-number-star3b-group-06" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-group-06v2-single-ssc")[0], "pick-act");
}

// 时时彩 后三 混合组选
function starThreeBackGroupMixSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3b-group-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-group-mix-ssc")[0], "pick-act");
}

// 时时彩 后三 组选和值
function starThreeBackGroupTotalSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-gt", "win-number-star3b-group-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-group-total-ssc")[0], "pick-act");
}

// 时时彩 后三 包胆
function starThreeBackPromiseSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star3b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-promise-ssc")[0], "pick-act");
}

// 时时彩 后三 其他 和值尾数
function starThreeBackTotalNumSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-notitle", "win-number-star3b-other-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-other-total-num-ssc")[0], "pick-act");
}

// 时时彩 后三 其他 豹子
function starThreeBackJaquarSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-jaguar", "win-number-star3b-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-other-jaguar-ssc")[0], "pick-act");
}

// 时时彩 后三 其他 顺子
function starThreeBackSmoothSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-smooth", "win-number-star3b-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-other-smooth-ssc")[0], "pick-act");
}

// 时时彩 后三 其他 对子
function starThreeBackPairSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-pair", "win-number-star3b-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-other-pair-ssc")[0], "pick-act");
}

// 时时彩 后三 其他 半顺
function starThreeBackHalfSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-half", "win-number-star3b-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-other-half-ssc")[0], "pick-act");
}

// 时时彩 后三 其他 杂六
function starThreeBackMix6SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-mix6", "win-number-star3b-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-other-mix6-ssc")[0], "pick-act");
}

// 时时彩 前二(mid_title)
function starTwoFrontSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ssc", "pick-star2f-direct-ssc", "pick-star2f-direct-multi-ssc", "pick-star2f-direct-single-ssc", "pick-star2f-direct-total-ssc", "pick-star2f-direct-cross-ssc",
		"pick-star2f-group-ssc", "pick-star2f-group-multi-ssc", "pick-star2f-group-single-ssc", "pick-star2f-group-total-ssc", "pick-star2f-group-promise-ssc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star2f-ssc")[0], "kind-act");
}

// 时时彩 前二 直选复式
function starTwoFrontDirectMultiSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten-thousand", "ball-thousand", "win-number-star2f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-direct-multi-ssc")[0], "pick-act");
}

// 时时彩 前二 直选单式
function starTwoFrontDirectSingleSSC() {
	var clearName = [];
	var hiddenName = [ "import-checkbox-area" ];
	var showName = [ "import-area", "win-number-star2f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-direct-single-ssc")[0], "pick-act");
}

// 时时彩 前二 直选和值
function starTwoFrontDirectTotalSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-19balls", "win-number-star2f-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-direct-total-ssc")[0], "pick-act");
}

// 时时彩 前二 直选跨度
function starTwoFrontDirectCrossSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star2f-cross" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-direct-cross-ssc")[0], "pick-act");
}

// 时时彩 前二 组选复式
function starTwoFrontGroupMultiSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group", "win-number-star2f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-group-multi-ssc")[0], "pick-act");
}

// 时时彩 前二 组选单式
function starTwoFrontGroupSingleSSC() {
	var clearName = [];
	var hiddenName = [ "import-checkbox-area" ];
	var showName = [ "import-area", "win-number-star2f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-group-single-ssc")[0], "pick-act");
}

// 时时彩 前二 组选和值
function starTwoFrontGroupTotalSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-17balls", "win-number-star2f-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-group-total-ssc")[0], "pick-act");
}

// 时时彩 前二 组选包胆
function starTwoFrontGroupPromiseSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star2f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-group-promise-ssc")[0], "pick-act");
}

// 时时彩 后二(mid_title)
function starTwoBackSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ssc", "pick-star2b-direct-ssc", "pick-star2b-direct-multi-ssc", "pick-star2b-direct-single-ssc", "pick-star2b-direct-total-ssc", "pick-star2b-direct-cross-ssc",
		"pick-star2b-group-ssc", "pick-star2b-group-multi-ssc", "pick-star2b-group-single-ssc", "pick-star2b-group-total-ssc", "pick-star2b-group-promise-ssc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star2b-ssc")[0], "kind-act");
}

// 时时彩 后二 直选复式
function starTwoBackDirectMultiSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten", "ball-one", "win-number-star2b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-direct-multi-ssc")[0], "pick-act");
}

// 时时彩 后二 直选单式
function starTwoBackDirectSingleSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star2b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-direct-single-ssc")[0], "pick-act");
}

// 时时彩 后二 直选和值
function starTwoBackDirectTotalSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-19balls", "win-number-star2b-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-direct-total-ssc")[0], "pick-act");
}

// 时时彩 后二 直选跨度
function starTwoBackDirectCrossSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star2b-cross" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-direct-cross-ssc")[0], "pick-act");
}

// 时时彩 后二 组选复式
function starTwoBackGroupMultiSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group", "win-number-star2b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-group-multi-ssc")[0], "pick-act");
}

// 时时彩 后二 组选单式
function starTwoBackGroupSingleSSC() {
	var clearName = [];
	var hiddenName = [ "import-checkbox-area" ];
	var showName = [ "import-area", "win-number-star2b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-group-single-ssc")[0], "pick-act");
}

// 时时彩 后二 组选和值
function starTwoBackGroupTotalSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-17balls", "win-number-star2b-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-group-total-ssc")[0], "pick-act");
}

// 时时彩 后二 组选包胆
function starTwoBackGroupPromiseSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star2b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-group-promise-ssc")[0], "pick-act");
}

// 时时彩 定位胆(mid_title)
function starSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ssc", "pick-star1-ssc", "pick-star1-ssc-btn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star1-ssc")[0], "kind-act");
}

// 时时彩 定位胆
function starOneSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star1-ssc-btn")[0], "pick-act");
}

// 时时彩 大小单双(mid_title)
function bigSmallSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ssc", "pick-bs-ssc", "pick-bigsmall-f2-ssc", "pick-bigsmall-b2-ssc", "pick-bigsmall-any2-ssc", "pick-bigsmall-f3-ssc", "pick-bigsmall-b3-ssc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-bs-ssc")[0], "kind-act");
}

// 时时彩 大小单双 前二大小单双
function bigSmallFrontTwoSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-bs-ten-thousand", "ball-star-bs-thousand", "win-number-bssd-front02" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-bigsmall-f2-ssc")[0], "pick-act");
}

// 时时彩 大小单双 后二大小单双
function bigSmallBackTwoSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-bs-ten", "ball-star-bs-one", "win-number-bs-back02" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-bigsmall-b2-ssc")[0], "pick-act");
}

// 时时彩 大小单双 任选二大小单双
function bigSmallAnyTwoSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-bs-ten-thousand", "ball-star-bs-thousand", , "ball-star-bs-hundred", "ball-star-bs-ten", "ball-star-bs-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-bigsmall-any2-ssc")[0], "pick-act");
}

// 时时彩 大小单双 前三大小单双
function bigSmallFrontThreeSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-bs-ten-thousand", "ball-star-bs-thousand", "ball-star-bs-hundred", "win-number-star3f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-bigsmall-f3-ssc")[0], "pick-act");
}

// 时时彩 大小单双 后三大小单双
function bigSmallBackThreeSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-bs-hundred", "ball-star-bs-ten", "ball-star-bs-one", "win-number-star3b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-bigsmall-b3-ssc")[0], "pick-act");
}

// 时时彩 不定位(mid_title)
function star3NoAssignSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ssc", "pick-star3-noassign-ssc", "pick-star3-noassign-f3n1-ssc", "pick-star3-noassign-b3n1-ssc", "pick-star3-noassign-f3n2-ssc", "pick-star3-noassign-b3n2-ssc",
		"pick-star3-noassign-any3n1-ssc", "pick-star4-noassign-ssc", "pick-star3-noassign-s4n1-ssc", "pick-star3-noassign-s4n2-ssc", "pick-star5-noassign-ssc", "pick-star5-noassign-s5n1-ssc",
		"pick-star5-noassign-s5n2-ssc", "pick-star5-noassign-s5n3-ssc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-noassign-ssc")[0], "kind-act");
}

// 时时彩 不定位 三星 前三一码不定位
function star3NoAssignFront3Num01SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-noassign-f3n1-ssc")[0], "pick-act");
}

// 时时彩 不定位 三星 后三一码不定位
function star3NoAssignBack3Num01SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-noassign-b3n1-ssc")[0], "pick-act");
}

// 时时彩 不定位 三星 前三二码不定位
function star3NoAssignFront3Num02SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-noassign-f3n2-ssc")[0], "pick-act");
}

// 时时彩 不定位 三星 后三二码不定位
function star3NoAssignBack3Num02SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-noassign-b3n2-ssc")[0], "pick-act");
}

// 时时彩 不定位 三星 任三一码不定位
function star3NoAssignAny3Num01SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-noassign-any3n1-ssc")[0], "pick-act");
}

// 时时彩 不定位 四星 四星一码不定位
function star4NoAssignNum01SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-noassign-s4n1-ssc")[0], "pick-act");
}

// 时时彩 不定位 四星 四星二码不定位
function star4NoAssignNum02SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-noassign-s4n2-ssc")[0], "pick-act");
}

// 时时彩 不定位 五星 五星一码不定位
function star5NoAssignNum01SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-noassign-s5n1-ssc")[0], "pick-act");
}

// 时时彩 不定位 五星 五星二码不定位
function star5NoAssignNum02SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-noassign-s5n2-ssc")[0], "pick-act");
}

// 时时彩 不定位 五星 五星三码不定位
function star5NoAssignNum03SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-noassign-s5n3-ssc")[0], "pick-act");
}

// 时时彩 趣味(mid_title)
function FunSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ssc", "pick-fun-ssc", "pick-fun-5star3-ssc", "pick-fun-4star3-ssc", "pick-fun-f3star2-ssc", "pick-fun-b3star2-ssc", "pick-section-ssc",
		"pick-section-5star3-ssc", "pick-section-4star3-ssc", "pick-section-f3star2-ssc", "pick-section-b3star2-ssc", "pick-special-ssc", "pick-special-one-ssc", "pick-special-two-ssc",
		"pick-special-three-ssc", "pick-special-four-ssc", ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-funny-ssc")[0], "kind-act");
}

// 时时彩 趣味 五码趣味三星
function Fun05star3SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-fun-ten-thousand", "ball-fun-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-fun-5star3-ssc")[0], "pick-act");
}

// 时时彩 趣味 四码趣味三星
function Fun04star3SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-fun-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-fun-4star3-ssc")[0], "pick-act");
}

// 时时彩 趣味 后三码趣味二星
function FunBack03star2SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-fun-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-fun-b3star2-ssc")[0], "pick-act");
}

// 时时彩 趣味 前三趣味二星
function FunFront03star2SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-fun-ten-thousand", "ball-thousand", "ball-hundred", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-fun-f3star2-ssc")[0], "pick-act");
}

// 时时彩 趣味 区间 五码区间三星
function star3SectionNum05SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-section-ten-thousand", "ball-section-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-section-5star3-ssc")[0], "pick-act");
}

// 时时彩 趣味 区间 四码区间三星
function star3SectionNum04SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-section-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-section-4star3-ssc")[0], "pick-act");
}

// 时时彩 趣味 区间 后三区间二星
function star2SectionBackNum03SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-section-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-section-b3star2-ssc")[0], "pick-act");
}

// 时时彩 趣味 区间 前三区间二星
function star2SectionFrontNum03SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-section-ten-thousand", "ball-thousand", "ball-hundred", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-section-f3star2-ssc")[0], "pick-act");
}

// 时时彩 趣味 特殊 一帆风顺
function specialOneSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-special-one-ssc")[0], "pick-act");
}

// 时时彩 趣味 特殊 好事成双
function specialTwoSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-special-two-ssc")[0], "pick-act");
}

// 时时彩 趣味 特殊 三星报喜
function specialThreeSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-special-three-ssc")[0], "pick-act");
}

// 时时彩 趣味 特殊 四季发财
function specialFourSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-special-four-ssc")[0], "pick-act");
}

// 时时彩 任选(mid_title)
function anySSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ssc", "pick-any2-ssc", "pick-any2-dir-multi-ssc", "pick-any2-dir-single-ssc", "pick-any2-dir-total-ssc", "pick-any2-dir-cross-ssc", "pick-any2-group-multi-ssc",
		"pick-any2-group-single-ssc", "pick-any2-group-total-ssc", "pick-any3-ssc", "pick-any3-dir-multi-ssc", "pick-any3-dir-single-ssc", "pick-any3-dir-total-ssc",
		"pick-any3-dir-cross-ssc", "pick-any3-total-ssc", "pick-any3-group-3-multi-ssc", "pick-any3-group-3-single-ssc", "pick-any3-group-6-multi-ssc", "pick-any3-group-6-multi-ssc",
		"pick-any3-group-6-single-ssc", "pick-any3-group-mix-ssc", "pick-any4-ssc", "pick-any4-dir-multi-ssc", "pick-any4-dir-single-ssc", "pick-any4-group-24-ssc", "pick-any4-group-12-ssc",
		"pick-any4-group-06-ssc", "pick-any4-group-04-ssc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-any-ssc")[0], "kind-act");
}

// 时时彩 任选二星 直选复式
function anyTwoDirectMultiSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any2-dir-multi-ssc")[0], "pick-act");
}

// 时时彩 任选二星 直选单式
function anyTwoDirectSingleSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any2-dir-single-ssc")[0], "pick-act");
}

// 时时彩 任选二星 直选和值
function anyTwoDirectTotalSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-star-19balls", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any2-dir-total-ssc")[0], "pick-act");
}

// 时时彩 任选二星 直选跨度
function anyTwoDirectCrossSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-star-10playballs", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any2-dir-cross-ssc")[0], "pick-act");
}

// 时时彩 任选二星 组选复式
function anyTwoGrouptMultiSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-star-group", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any2-group-multi-ssc")[0], "pick-act");
}

// 时时彩 任选二星 组选单式
function anyTwoGrouptSingleSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any2-group-single-ssc")[0], "pick-act");
}

// 时时彩 任选二星 组选和值
function anyTwoGrouptTotalSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-star-17balls", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any2-group-total-ssc")[0], "pick-act");
}

// 时时彩 任选二星 组选包胆
function anyTwoGrouptPromiseSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-star-10balls", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any2-group-promise-ssc")[0], "pick-act");
}

// 對照表沒看到
// 时时彩 任选三星 直选复式
function anyThreeDirectMultiSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-dir-multi-ssc")[0], "pick-act");
}

// 时时彩 任选三星 直选单式
function anyThreeDirectSingleSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-dir-single-ssc")[0], "pick-act");
}

// 时时彩 任选三星 直选和值
function anyThreeDirectTotalSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-star-dt", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-dir-total-ssc")[0], "pick-act");
}

// 时时彩 任选三星 直选跨度
function anyThreeDirectCrossSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-star-10playballs", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-dir-cross-ssc")[0], "pick-act");
}

// 时时彩 任选三星 组三复式
function anyThreeGroup03MultiSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-star-group", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-group-3-multi-ssc")[0], "pick-act");
}

// 时时彩 任选三星 组三单式
function anyThreeGroup03ingleSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-group-3-single-ssc")[0], "pick-act");
}

// 时时彩 任选三星 组六复式
function anyThreeGroup06MultiSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-star-group-06", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-group-6-multi-ssc")[0], "pick-act");
}

// 时时彩 任选三星 组六单式
function anyThreeGroup06SingleSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-group-6-single-ssc")[0], "pick-act");
}

// 时时彩 任选三星 混合组选
function anyThreeGroupMixSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-group-mix-ssc")[0], "pick-act");
}

// 對照表沒看到
// 时时彩 任选三星 组选和值
function anyThreeGroupTotalSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-star-group-total", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-total-ssc")[0], "pick-act");
}

// 對照表沒看到
// 时时彩 任选三星 组选包胆
function anyThreeGroupPromiseSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-star-10balls", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-promise-ssc")[0], "pick-act");
}

// 时时彩 任选四星 直选复式
function anyFourDirecttMultiSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any4-dir-multi-ssc")[0], "pick-act");
}

// 时时彩 任选四星 直选单式
function anyFourDirectSingleSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any4-dir-single-ssc")[0], "pick-act");
}

// 时时彩 任选四星 组24
function anyFourGroup24SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-notitle", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any4-group-24-ssc")[0], "pick-act");
}

// 时时彩 任选四星 组12
function anyFourGroup12SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-star-group-double", "ball-star-group-single", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area",
		"win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any4-group-12-ssc")[0], "pick-act");
}

// 时时彩 任选四星 组06
function anyFourGroup06SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-star-group-double", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any4-group-06-ssc")[0], "pick-act");
}

// 时时彩 任选四星 组04
function anyFourGroup04SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ssc", "pick-any3-ssc", "pick-any4-ssc", "ball-star-group-triple", "ball-star-group-single", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area",
		"win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any4-group-04-ssc")[0], "pick-act");
}

// 时时彩 龙虎和(mid_title)
function dttSSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ssc", "pick-dtt-ssc", "pick-dtt-tenthousand1000-ssc", "pick-dtt-tenthousand100-ssc", "pick-dtt-tenthousand10-ssc", "pick-dtt-tenthousand01-ssc",
		"pick-dtt-thousand100-ssc", "pick-dtt-thousand10-ssc", "pick-dtt-thousand01-ssc", "pick-dtt-hundred10-ssc", "pick-dtt-hundred01-ssc", "pick-dtt-ten01-ssc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-dtt-ssc")[0], "kind-act");
}

// 时时彩 龙虎和 万千
function dttTenThousand1000SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-tenthousand1000", "win-number-dtt-ten-thousand1000" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-tenthousand1000-ssc")[0], "pick-act");
}

// 时时彩 龙虎和 万百
function dttTenThousand100SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-tenthousand100", "win-number-dtt-ten-thousand100" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-tenthousand100-ssc")[0], "pick-act");
}

// 时时彩 龙虎和 万十
function dttTenThousand10SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-tenthousand10", "win-number-dtt-ten-thousand10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-tenthousand10-ssc")[0], "pick-act");
}

// 时时彩 龙虎和 万个
function dttTenThousand01SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-tenthousand01", "win-number-dtt-ten-thousand01" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-tenthousand01-ssc")[0], "pick-act");
}

// 时时彩 龙虎和 千百
function dttThousand100SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-thousand100", "win-number-dtt-thousand100" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-thousand100-ssc")[0], "pick-act");
}

// 时时彩 龙虎和 千十
function dttThousand10SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-thousand10", "win-number-dtt-thousand10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-thousand10-ssc")[0], "pick-act");
}

// 时时彩 龙虎和 千个
function dttThousand01SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-thousand01", "win-number-dtt-thousand01" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-thousand01-ssc")[0], "pick-act");
}

// 时时彩 龙虎和 百十
function dttHundred10SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-hundred10", "win-number-dtt-hundred10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-hundred10-ssc")[0], "pick-act");
}

// 时时彩 龙虎和 百个
function dttHundred01SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-hundred01", "win-number-dtt-hundred01" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-hundred01-ssc")[0], "pick-act");
}

// 时时彩 龙虎和 十个
function dttTen01SSC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-ten01", "win-number-dtt-ten01" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-ten01-ssc")[0], "pick-act");
}

// 分分彩 五星 直选复式
function starFiveDirectMultiFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-direct-multi-ffc")[0], "pick-act");
}

// 分分彩 五星 直选单式
function starFiveDirectSingleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-direct-single-ffc")[0], "pick-act");
}

// 分分彩 五星 组选120
function starFiveGroup120FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star5-group120", "win-number-star5-group-120" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-group-120-ffc")[0], "pick-act");
}

// 分分彩 五星 组选60
function starFiveGroup60FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-single", "ball-star-group-double", "win-number-star5-group-60" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-group-60-ffc")[0], "pick-act");
}

// 分分彩 五星 组选30
function starFiveGroup30FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-single", "ball-star-group-double", "win-number-star5-group-30" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-group-30-ffc")[0], "pick-act");
}

// 分分彩 五星 组选20
function starFiveGroup20FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-single", "ball-star-group-triple", "win-number-star5-group-20" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-group-20-ffc")[0], "pick-act");
}

// 分分彩 五星 组选10
function starFiveGroup10FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-triple", "ball-star-group-double", "win-number-star5-group-10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-group-10-ffc")[0], "pick-act");
}

// 分分彩 五星 组选5
function starFiveGroup05FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-single", "ball-star-group-quadruple", "win-number-star5-group-05" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-group-05-ffc")[0], "pick-act");
}

// 分分彩 五星 其他 总和大小单双
function starFiveOtherTotalBSFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star5-other-total-bs", "win-number-star5-total-bs" ]; // ,"win-number-star5"];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-other-total-bs-ffc")[0], "pick-act");
}

// 分分彩 五星 其他 总和组合大小单双
function starFiveOtherTotalBSGroupFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star5-other-total-bsgroup", "win-number-star5-total-bs" ]; // ,"win-number-star5"];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-other-total-bsgroup-ffc")[0], "pick-act");
}

// 分分彩 前四(mid_title)
function starFourFrontFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ffc", "pick-star4f-direct-ffc", "pick-star4f-direct-multi-ffc", "pick-star4f-direct-single-ffc", "pick-star4f-group-ffc", "pick-star4f-group-24-ffc",
		"pick-star4f-group-12-ffc", "pick-star4f-group-06-ffc", "pick-star4f-group-04-ffc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star4f-ffc")[0], "kind-act");
}

// 分分彩 前四 直选复式
function starFourFrontDirectMultiFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "win-number-star4f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4f-direct-multi-ffc")[0], "pick-act");
}

// 分分彩 前四 直选单式
function starFourFrontDirectSingleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star4f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4f-direct-single-ffc")[0], "pick-act");
}

// 分分彩 前四 组24
function starFourFrontGroup24FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star4f-group-24" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4f-group-24-ffc")[0], "pick-act");
}

// 分分彩 前四 组12
function starFourFrontGroup12FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-double", "ball-star-group-single", "win-number-star4f-group-12" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4f-group-12-ffc")[0], "pick-act");
}

// 分分彩 前四 组06
function starFourFrontGroup06FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-double", "win-number-star4f-group-06" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4f-group-06-ffc")[0], "pick-act");
}

// 分分彩 前四 组04
function starFourFrontGroup04FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-triple", "ball-star-group-single", "win-number-star4f-group-04" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4f-group-04-ffc")[0], "pick-act");
}

// 分分彩 后四(mid_title)
function starFourBackFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ffc", "pick-star4b-direct-ffc", "pick-star4b-direct-multi-ffc", "pick-star4b-direct-single-ffc", "pick-star4b-group-ffc", "pick-star4b-group-24-ffc",
		"pick-star4b-group-12-ffc", "pick-star4b-group-06-ffc", "pick-star4b-group-04-ffc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star4b-ffc")[0], "kind-act");
}

// 分分彩 后四 直选复式
function starFourBackDirectMultiFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star4b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4b-direct-multi-ffc")[0], "pick-act");
}

// 分分彩 后四 直选单式
function starFourBackDirectSingleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star4b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4b-direct-single-ffc")[0], "pick-act");
}

// 分分彩 后四 组24
function starFourBackGroup24FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star4b-group-24" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4b-group-24-ffc")[0], "pick-act");
}

// 分分彩 后四 组12
function starFourBackGroup12FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-double", "ball-star-group-single", "win-number-star4b-group-12" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4b-group-12-ffc")[0], "pick-act");
}

// 分分彩 后四 组06
function starFourBackGroup06FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-double", "win-number-star4b-group-06" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4b-group-06-ffc")[0], "pick-act");
}

// 分分彩 后四 组04
function starFourBackGroup04FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-triple", "ball-star-group-single", "win-number-star4b-group-04" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star4b-group-04-ffc")[0], "pick-act");
}

// 分分彩 前三(mid_title)
function starThreeFrontFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ffc", "pick-star3f-direct-ffc", "pick-star3f-direct-multi-ffc", "pick-star3f-direct-single-ffc", "pick-star3f-direct-total-ffc", "pick-star3f-direct-cross-ffc",
		"pick-star3f-group-ffc", "pick-star3f-group-03v2-ffc", "pick-star3f-group-03v2-single-ffc", "pick-star3f-group-06v2-ffc", "pick-star3f-group-06v2-single-ffc",
		"pick-star3f-group-mix-ffc", "pick-star3f-group-total-ffc", "pick-star3f-promise-ffc", "pick-star3f-other-ffc", "pick-star3f-other-total-num-ffc", "pick-star3f-other-jaguar-ffc",
		"pick-star3f-other-smooth-ffc", "pick-star3f-other-pair-ffc", "pick-star3f-other-half-ffc", "pick-star3f-other-mix6-ffc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star3f-ffc")[0], "kind-act");
}

// 分分彩 前三 直选复式
function starThreeFrontDirectMultiFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten-thousand", "ball-thousand", "ball-hundred", "win-number-star3f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-direct-multi-ffc")[0], "pick-act");
}

// 分分彩 前三 直选单式
function starThreeFrontDirectSingleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-direct-single-ffc")[0], "pick-act");
}

// 分分彩 前三 直选和值
function starThreeFrontDirectTotalFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dt", "win-number-star3f-direct-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-direct-total-ffc")[0], "pick-act");
}

// 分分彩 前三 直选跨度
function starThreeFrontDirectCrossFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dc", "win-number-star3f-direct-cross" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-direct-cross-ffc")[0], "pick-act");
}

// 分分彩 前三 组三
function starThreeFrontGroup03v2FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-03", "win-number-star3f-group-03" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-group-03v2-ffc")[0], "pick-act");
}

// 分分彩 前三 组六
function starThreeFrontGroup06v2FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-06", "win-number-star3f-group-06" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-group-06v2-ffc")[0], "pick-act");
}

// 分分彩 前三 组三单式
function starThreeFrontGroup03v2singleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3f-group-03" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-group-03v2-single-ffc")[0], "pick-act");
}

// 分分彩 前三 组六单式
function starThreeFrontGroup06v2singleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3f-group-06" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-group-06v2-single-ffc")[0], "pick-act");
}

// 分分彩 前三 混合组选
function starThreeFrontGroupMixFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3f-group-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-group-mix-ffc")[0], "pick-act");
}

// 分分彩 前三 组选和值
function starThreeFrontGroupTotalFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-gt", "win-number-star3f-direct-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-group-total-ffc")[0], "pick-act");
}

// 分分彩 前三 包胆
function starThreeFrontPromiseFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star3f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-promise-ffc")[0], "pick-act");
}

// 分分彩 前三 其他 和值尾数
function starThreeFrontTotalNumFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-notitle", "win-number-star3f-other-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-other-total-num-ffc")[0], "pick-act");
}

// 分分彩 前三 其他 豹子
function starThreeFrontJaquarFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-jaguar", "win-number-star3f-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-other-jaguar-ffc")[0], "pick-act");
}

// 分分彩 前三 其他 顺子
function starThreeFrontSmoothFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-smooth", "win-number-star3f-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-other-smooth-ffc")[0], "pick-act");
}

// 分分彩 前三 其他 对子
function starThreeFrontPairFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-pair", "win-number-star3f-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-other-pair-ffc")[0], "pick-act");
}

// 分分彩 前三 其他 半顺
function starThreeFrontHalfFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-half", "win-number-star3f-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-other-half-ffc")[0], "pick-act");
}

// 分分彩 前三 其他 杂六
function starThreeFrontMix6FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-mix6", "win-number-star3f-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-other-mix6-ffc")[0], "pick-act");
}

// 分分彩 中三(mid_title)
function starThreeMiddleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ffc", "pick-star3m-direct-ffc", "pick-star3m-direct-multi-ffc", "pick-star3m-direct-single-ffc", "pick-star3m-direct-total-ffc", "pick-star3m-direct-cross-ffc",
		"pick-star3m-group-ffc", "pick-star3m-group-03v2-ffc", "pick-star3m-group-03v2-single-ffc", "pick-star3m-group-06v2-ffc", "pick-star3m-group-06v2-single-ffc",
		"pick-star3m-group-mix-ffc", "pick-star3m-group-total-ffc", "pick-star3m-promise-ffc", "pick-star3m-other-ffc", "pick-star3m-other-total-num-ffc", "pick-star3m-other-jaguar-ffc",
		"pick-star3m-other-smooth-ffc", "pick-star3m-other-pair-ffc", "pick-star3m-other-half-ffc", "pick-star3m-other-mix6-ffc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star3m-ffc")[0], "kind-act");
}

// 分分彩 中三 直选复式
function starThreeMiddleDirectMultiFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-thousand", "ball-hundred", "ball-ten", "win-number-star3m" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-direct-multi-ffc")[0], "pick-act");
}

// 分分彩 中三 直选单式
function starThreeMiddleDirectSingleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3m" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-direct-single-ffc")[0], "pick-act");
}

// 分分彩 中三 直选和值
function starThreeMiddleDirectTotalFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dt", "win-number-star3m-direct-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-direct-total-ffc")[0], "pick-act");
}

// 分分彩 中三 直选跨度
function starThreeMiddleDirectCrossFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dc", "win-number-star3m-direct-cross" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-direct-cross-ffc")[0], "pick-act");
}

// 分分彩 中三 组三
function starThreeMiddleGroup03v2FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-03", "win-number-star3m-group-03" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-group-03v2-ffc")[0], "pick-act");
}

// 分分彩 中三 组六
function starThreeMiddleGroup06v2FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-06", "win-number-star3m-group-06" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-group-06v2-ffc")[0], "pick-act");
}

// 分分彩 中三 组三单式
function starThreeMiddleGroup03v2singleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3m-group-03" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-group-03v2-single-ffc")[0], "pick-act");
}

// 分分彩 中三 组六单式
function starThreeMiddleGroup06v2singleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3m-group-06" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-group-06v2-single-ffc")[0], "pick-act");
}

// 分分彩 中三 混合组选
function starThreeMiddleGroupMixFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3m-group-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-group-mix-ffc")[0], "pick-act");
}

// 分分彩 中三 组选和值
function starThreeMiddleGroupTotalFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-gt", "win-number-star3m-direct-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-group-total-ffc")[0], "pick-act");
}

// 分分彩 中三 包胆
function starThreeMiddlePromiseFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star3m" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-promise-ffc")[0], "pick-act");
}

// 分分彩 中三 其他 和值尾数
function starThreeMiddleTotalNumFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-notitle", "win-number-star3m-other-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-other-total-num-ffc")[0], "pick-act");
}

// 分分彩 中三 其他 豹子
function starThreeMiddleJaquarFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-jaguar", "win-number-star3m-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-other-jaguar-ffc")[0], "pick-act");
}

// 分分彩 中三 其他 顺子
function starThreeMiddleSmoothFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-smooth", "win-number-star3m-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-other-smooth-ffc")[0], "pick-act");
}

// 分分彩 中三 其他 对子
function starThreeMiddlePairFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-pair", "win-number-star3m-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-other-pair-ffc")[0], "pick-act");
}

// 分分彩 中三 其他 半顺
function starThreeMiddleHalfFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-half", "win-number-star3m-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3m-other-half-ffc")[0], "pick-act");
}

// 分分彩 中三 其他 杂六
function starThreeMiddleMix6FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-mix6", "win-number-star3m-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star3m-ffc")[0], "kind-act");
	addClass(document.getElementsByClassName("pick-star3m-other-mix6-ffc")[0], "pick-act");
}

// 分分彩 后三(mid_title)
function starThreeBackFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ffc", "pick-star3b-direct-ffc", "pick-star3b-direct-multi-ffc", "pick-star3b-direct-single-ffc", "pick-star3b-direct-total-ffc", "pick-star3b-direct-cross-ffc",
		"pick-star3b-group-ffc", "pick-star3b-group-03v2-ffc", "pick-star3b-group-03v2-single-ffc", "pick-star3b-group-06v2-ffc", "pick-star3b-group-06v2-single-ffc",
		"pick-star3b-group-mix-ffc", "pick-star3b-group-total-ffc", "pick-star3b-promise-ffc", "pick-star3b-other-ffc", "pick-star3b-other-total-num-ffc", "pick-star3b-other-jaguar-ffc",
		"pick-star3b-other-smooth-ffc", "pick-star3b-other-pair-ffc", "pick-star3b-other-half-ffc", "pick-star3b-other-mix6-ffc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star3b-ffc")[0], "kind-act");
}

// 分分彩 后三 直选复式
function starThreeBackDirectMultiFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-hundred", "ball-ten", "ball-one", "win-number-star3b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-direct-multi-ffc")[0], "pick-act");
}

// 分分彩 后三 直选单式
function starThreeBackDirectSingleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-direct-single-ffc")[0], "pick-act");
}

// 分分彩 后三 直选和值
function starThreeBackDirectTotalFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dt", "win-number-star3b-direct-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-direct-total-ffc")[0], "pick-act");
}

// 分分彩 后三 直选跨度
function starThreeBackDirectCrossFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dc", "win-number-star3b-direct-cross" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-direct-cross-ffc")[0], "pick-act");
}

// 分分彩 后三 组三
function starThreeBackGroup03v2FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-03", "win-number-star3b-group-03" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-group-03v2-ffc")[0], "pick-act");
}

// 分分彩 后三 组六
function starThreeBackGroup06v2FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-06", "win-number-star3b-group-06" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-group-06v2-ffc")[0], "pick-act");
}

// 分分彩 后三 组三单式
function starThreeBackGroup03v2singleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3b-group-03" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-group-03v2-single-ffc")[0], "pick-act");
}

// 分分彩 后三 组六单式
function starThreeBackGroup06v2singleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3b-group-06" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-group-06v2-single-ffc")[0], "pick-act");
}

// 分分彩 后三 混合组选
function starThreeBackGroupMixFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3b-group-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-group-mix-ffc")[0], "pick-act");
}

// 分分彩 后三 组选和值
function starThreeBackGroupTotalFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-gt", "win-number-star3b-group-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-group-total-ffc")[0], "pick-act");
}

// 分分彩 后三 包胆
function starThreeBackPromiseFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star3b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-promise-ffc")[0], "pick-act");
}

// 分分彩 后三 其他 和值尾数
function starThreeBackTotalNumFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-notitle", "win-number-star3b-other-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-other-total-num-ffc")[0], "pick-act");
}

// 分分彩 后三 其他 豹子
function starThreeBackJaquarFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-jaguar", "win-number-star3b-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-other-jaguar-ffc")[0], "pick-act");
}

// 分分彩 后三 其他 顺子
function starThreeBackSmoothFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-smooth", "win-number-star3b-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-other-smooth-ffc")[0], "pick-act");
}

// 分分彩 后三 其他 对子
function starThreeBackPairFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-pair", "win-number-star3b-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-other-pair-ffc")[0], "pick-act");
}

// 分分彩 后三 其他 半顺
function starThreeBackHalfFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-half", "win-number-star3b-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-other-half-ffc")[0], "pick-act");
}

// 分分彩 后三 其他 杂六
function starThreeBackMix6FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-mix6", "win-number-star3b-other-mix" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-other-mix6-ffc")[0], "pick-act");
}

// 分分彩 前二(mid_title)
function starTwoFrontFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ffc", "pick-star2f-direct-ffc", "pick-star2f-direct-multi-ffc", "pick-star2f-direct-single-ffc", "pick-star2f-direct-total-ffc", "pick-star2f-direct-cross-ffc",
		"pick-star2f-group-ffc", "pick-star2f-group-multi-ffc", "pick-star2f-group-single-ffc", "pick-star2f-group-total-ffc", "pick-star2f-group-promise-ffc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star2f-ffc")[0], "kind-act");
}

// 分分彩 前二 直选复式
function starTwoFrontDirectMultiFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten-thousand", "ball-thousand", "win-number-star2f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-direct-multi-ffc")[0], "pick-act");
}

// 分分彩 前二 直选单式
function starTwoFrontDirectSingleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star2f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-direct-single-ffc")[0], "pick-act");
}

// 分分彩 前二 直选和值
function starTwoFrontDirectTotalFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-19balls", "win-number-star2f-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-direct-total-ffc")[0], "pick-act");
}

// 分分彩 前二 直选跨度
function starTwoFrontDirectCrossFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star2f-cross" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-direct-cross-ffc")[0], "pick-act");
}

// 分分彩 前二 组选复式
function starTwoFrontGroupMultiFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group", "win-number-star2f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-group-multi-ffc")[0], "pick-act");
}

// 分分彩 前二 组选单式
function starTwoFrontGroupSingleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star2f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-group-single-ffc")[0], "pick-act");
}

// 分分彩 前二 组选和值
function starTwoFrontGroupTotalFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-17balls", "win-number-star2f-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-group-total-ffc")[0], "pick-act");
}

// 分分彩 前二 组选包胆
function starTwoFrontGroupPromiseFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star2f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-group-promise-ffc")[0], "pick-act");
}

// 分分彩 后二(mid_title)
function starTwoBackFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ffc", "pick-star2b-direct-ffc", "pick-star2b-direct-multi-ffc", "pick-star2b-direct-single-ffc", "pick-star2b-direct-total-ffc", "pick-star2b-direct-cross-ffc",
		"pick-star2b-group-ffc", "pick-star2b-group-multi-ffc", "pick-star2b-group-single-ffc", "pick-star2b-group-total-ffc", "pick-star2b-group-promise-ffc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star2b-ffc")[0], "kind-act");
}

// 分分彩 后二 直选复式
function starTwoBackDirectMultiFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten", "ball-one", "win-number-star2b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-direct-multi-ffc")[0], "pick-act");
}

// 分分彩 后二 直选单式
function starTwoBackDirectSingleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star2b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-direct-single-ffc")[0], "pick-act");
}

// 分分彩 后二 直选和值
function starTwoBackDirectTotalFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-19balls", "win-number-star2b-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-direct-total-ffc")[0], "pick-act");
}

// 分分彩 后二 直选跨度
function starTwoBackDirectCrossFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star2b-cross" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-direct-cross-ffc")[0], "pick-act");
}

// 分分彩 后二 组选复式
function starTwoBackGroupMultiFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group", "win-number-star2b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-group-multi-ffc")[0], "pick-act");
}

// 分分彩 后二 组选单式
function starTwoBackGroupSingleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star2b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-group-single-ffc")[0], "pick-act");
}

// 分分彩 后二 组选和值
function starTwoBackGroupTotalFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-17balls", "win-number-star2b-total" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-group-total-ffc")[0], "pick-act");
}

// 分分彩 后二 组选包胆
function starTwoBackGroupPromiseFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star2b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-group-promise-ffc")[0], "pick-act");
}

// 分分彩 定位胆(mid_title)
function starFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ffc", "pick-star1-ffc", "pick-star1-ffc-btn-ffc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star1-ffc")[0], "kind-act");
}

// 分分彩 定位胆
function starOneFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star1-ffc-btn-ffc")[0], "pick-act");
}

// 分分彩 大小单双(mid_title)
function bigSmallFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ffc", "pick-bs-ffc", "pick-bigsmall-f2-ffc", "pick-bigsmall-b2-ffc", "pick-bigsmall-any2-ffc", "pick-bigsmall-f3-ffc", "pick-bigsmall-b3-ffc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-bs-ffc")[0], "kind-act");
}

// 分分彩 大小单双 前二大小单双
function bigSmallFrontTwoFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-bs-ten-thousand", "ball-star-bs-thousand", "win-number-bssd-front02" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-bigsmall-f2-ffc")[0], "pick-act");
}

// 分分彩 大小单双 后二大小单双
function bigSmallBackTwoFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-bs-ten", "ball-star-bs-one", "win-number-bs-back02" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-bigsmall-b2-ffc")[0], "pick-act");
}

// 分分彩 大小单双 任选二大小单双
function bigSmallAnyTwoFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-bs-ten-thousand", "ball-star-bs-thousand", , "ball-star-bs-hundred", "ball-star-bs-ten", "ball-star-bs-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-bigsmall-any2-ffc")[0], "pick-act");
}

// 分分彩 大小单双 前三大小单双
function bigSmallFrontThreeFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-bs-ten-thousand", "ball-star-bs-thousand", "ball-star-bs-hundred", "win-number-star3f" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-bigsmall-f3-ffc")[0], "pick-act");
}

// 分分彩 大小单双 后三大小单双
function bigSmallBackThreeFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-bs-hundred", "ball-star-bs-ten", "ball-star-bs-one", "win-number-star3b" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-bigsmall-b3-ffc")[0], "pick-act");
}

// 分分彩 不定位(mid_title)
function NoAssignFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ffc", "pick-star3-noassign-ffc", "pick-star3-noassign-f3n1-ffc", "pick-star3-noassign-b3n1-ffc", "pick-star3-noassign-f3n2-ffc", "pick-star3-noassign-b3n2-ffc",
		"pick-star3-noassign-any3n1-ffc", "pick-star4-noassign-ffc", "pick-star3-noassign-s4n1-ffc", "pick-star3-noassign-s4n2-ffc", "pick-star5-noassign-ffc", "pick-star5-noassign-s5n1-ffc",
		"pick-star5-noassign-s5n2-ffc", "pick-star5-noassign-s5n3-ffc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-noassign-ffc")[0], "kind-act");
}

// 分分彩 不定位 三星 前三一码不定位
function star3NoAssignFront3Num01FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-noassign-f3n1-ffc")[0], "pick-act");
}

// 分分彩 不定位 三星 后三一码不定位
function star3NoAssignBack3Num01FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-noassign-b3n1-ffc")[0], "pick-act");
}

// 分分彩 不定位 三星 前三二码不定位
function star3NoAssignFront3Num02FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-noassign-f3n2-ffc")[0], "pick-act");
}

// 分分彩 不定位 三星 后三二码不定位
function star3NoAssignBack3Num02FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-noassign-b3n2-ffc")[0], "pick-act");
}

// 分分彩 不定位 三星 任三一码不定位
function star3NoAssignAny3Num01FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-noassign-any3n1-ffc")[0], "pick-act");
}

// 分分彩 不定位 四星 四星一码不定位
function star4NoAssignNum01FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-noassign-s4n1-ffc")[0], "pick-act");
}

// 分分彩 不定位 四星 四星二码不定位
function star4NoAssignNum02FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-noassign-s4n2-ffc")[0], "pick-act");
}

// 分分彩 不定位 五星 五星一码不定位
function star5NoAssignNum01FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-noassign-s5n1-ffc")[0], "pick-act");
}

// 分分彩 不定位 五星 五星二码不定位
function star5NoAssignNum02FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-noassign-s5n2-ffc")[0], "pick-act");
}

// 分分彩 不定位 五星 五星三码不定位
function star5NoAssignNum03FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star5-noassign-s5n3-ffc")[0], "pick-act");
}

// 分分彩 趣味(mid_title)
function FunFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ffc", "pick-fun-ffc", "pick-fun-5star3-ffc", "pick-fun-4star3-ffc", "pick-fun-f3star2-ffc", "pick-fun-b3star2-ffc", "pick-section-ffc",
		"pick-section-5star3-ffc", "pick-section-4star3-ffc", "pick-section-f3star2-ffc", "pick-section-b3star2-ffc", "pick-special-ffc", "pick-special-one-ffc", "pick-special-two-ffc",
		"pick-special-three-ffc", "pick-special-four-ffc", ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-funny-ffc")[0], "kind-act");
}

// 分分彩 趣味 五码趣味三星
function Fun05star3FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-fun-ten-thousand", "ball-fun-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-fun-5star3-ffc")[0], "pick-act");
}

// 分分彩 趣味 四码趣味三星
function Fun04star3FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-fun-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-fun-4star3-ffc")[0], "pick-act");
}

// 分分彩 趣味 后三码趣味二星
function FunBack03star2FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-fun-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-fun-b3star2-ffc")[0], "pick-act");
}

// 分分彩 趣味 前三趣味二星
function FunFront03star2FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-fun-ten-thousand", "ball-thousand", "ball-hundred", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-fun-f3star2-ffc")[0], "pick-act");
}

// 分分彩 趣味 区间 五码区间三星
function star3SectionNum05FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-section-ten-thousand", "ball-section-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-section-5star3-ffc")[0], "pick-act");
}

// 分分彩 趣味 区间 四码区间三星
function star3SectionNum04FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-section-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-section-4star3-ffc")[0], "pick-act");
}

// 分分彩 趣味 区间 后三区间二星
function star2SectionBackNum03FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-section-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-section-b3star2-ffc")[0], "pick-act");
}

// 分分彩 趣味 区间 前三区间二星
function star2SectionFrontNum03FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-section-ten-thousand", "ball-thousand", "ball-hundred", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-section-f3star2-ffc")[0], "pick-act");
}

// 分分彩 趣味 特殊 一帆风顺
function specialOneFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-special-one-ffc")[0], "pick-act");
}

// 分分彩 趣味 特殊 好事成双
function specialTwoFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-special-two-ffc")[0], "pick-act");
}

// 分分彩 趣味 特殊 三星报喜
function specialThreeFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-special-three-ffc")[0], "pick-act");
}

// 分分彩 趣味 特殊 四季发财
function specialFourFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-special-four-ffc")[0], "pick-act");
}

// 分分彩 任选(mid_title)
function anyFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ffc", "pick-any2-ffc", "pick-any2-dir-multi-ffc", "pick-any2-dir-single-ffc", "pick-any2-dir-total-ffc", "pick-any2-dir-cross-ffc", "pick-any2-group-multi-ffc",
		"pick-any2-group-single-ffc", "pick-any2-group-total-ffc", "pick-any3-ffc", "pick-any3-dir-multi-ffc", "pick-any3-dir-single-ffc", "pick-any3-dir-total-ffc",
		"pick-any3-dir-cross-ffc", "pick-any3-total-ffc", "pick-any3-group-3-multi-ffc", "pick-any3-group-3-single-ffc", "pick-any3-group-6-multi-ffc", "pick-any3-group-6-multi-ffc",
		"pick-any3-group-6-single-ffc", "pick-any3-group-mix-ffc", "pick-any4-ffc", "pick-any4-dir-multi-ffc", "pick-any4-dir-single-ffc", "pick-any4-group-24-ffc", "pick-any4-group-12-ffc",
		"pick-any4-group-06-ffc", "pick-any4-group-04-ffc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-any-ffc")[0], "kind-act");
}

// 分分彩 任选二星 直选复式
function anyTwoDirectMultiFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any2-dir-multi-ffc")[0], "pick-act");
}

// 分分彩 任选二星 直选单式
function anyTwoDirectSingleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any2-dir-single-ffc")[0], "pick-act");
}

// 分分彩 任选二星 直选和值
function anyTwoDirectTotalFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-star-19balls", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any2-dir-total-ffc")[0], "pick-act");
}

// 分分彩 任选二星 直选跨度
function anyTwoDirectCrossFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-star-10playballs", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any2-dir-cross-ffc")[0], "pick-act");
}

// 分分彩 任选二星 组选复式
function anyTwoGrouptMultiFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-star-group", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any2-group-multi-ffc")[0], "pick-act");
}

// 分分彩 任选二星 组选单式
function anyTwoGrouptSingleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any2-group-single-ffc")[0], "pick-act");
}

// 分分彩 任选二星 组选和值
function anyTwoGrouptTotalFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-star-17balls", "ball-checkbox", "ball-checkbox-area", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any2-group-total-ffc")[0], "pick-act");
}

// 分分彩 任选二星 组选包胆
function anyTwoGrouptPromiseFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-star-10balls", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any2-group-promise-ffc")[0], "pick-act");
}

// 對照表沒看到
// 分分彩 任选三星 直选复式
function anyThreeDirectMultiFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-dir-multi-ffc")[0], "pick-act");
}

// 分分彩 任选三星 直选单式
function anyThreeDirectSingleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-dir-single-ffc")[0], "pick-act");
}

// 分分彩 任选三星 直选和值
function anyThreeDirectTotalFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-star-dt", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-dir-total-ffc")[0], "pick-act");
}

// 分分彩 任选三星 直选跨度
function anyThreeDirectCrossFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-star-10playballs", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-dir-cross-ffc")[0], "pick-act");
}

// 分分彩 任选三星 组三复式
function anyThreeGroup03MultiFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-star-group", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-group-3-multi-ffc")[0], "pick-act");
}

// 分分彩 任选三星 组三单式
function anyThreeGroup03ingleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-group-3-single-ffc")[0], "pick-act");
}

// 分分彩 任选三星 组六复式
function anyThreeGroup06MultiFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-star-group-06", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-group-6-multi-ffc")[0], "pick-act");
}

// 分分彩 任选三星 组六单式
function anyThreeGroup06SingleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-group-6-single-ffc")[0], "pick-act");
}

// 分分彩 任选三星 混合组选
function anyThreeGroupMixFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-group-mix-ffc")[0], "pick-act");
}

// 對照表沒看到
// 分分彩 任选三星 组选和值
function anyThreeGroupTotalFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-star-group-total", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-total-ffc")[0], "pick-act");
}

// 對照表沒看到
// 分分彩 任选三星 组选包胆
function anyThreeGroupPromiseFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-star-10balls", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any3-promise-ffc")[0], "pick-act");
}

// 分分彩 任选四星 直选复式
function anyFourDirecttMultiFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any4-dir-multi-ffc")[0], "pick-act");
}

// 分分彩 任选四星 直选单式
function anyFourDirectSingleFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any4-dir-single-ffc")[0], "pick-act");
}

// 分分彩 任选四星 组24
function anyFourGroup24FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-notitle", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any4-group-24-ffc")[0], "pick-act");
}

// 分分彩 任选四星 组12
function anyFourGroup12FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-star-group-double", "ball-star-group-single", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any4-group-12-ffc")[0], "pick-act");
}

// 分分彩 任选四星 组06
function anyFourGroup06FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-star-group-double", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any4-group-06-ffc")[0], "pick-act");
}

// 分分彩 任选四星 组04
function anyFourGroup04FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-ffc", "pick-any3-ffc", "pick-any4-ffc", "ball-star-group-triple", "ball-star-group-single", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any4-group-04-ffc")[0], "pick-act");
}

// 分分彩 龙虎和(mid_title)
function dttFFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-ffc", "pick-dtt-ffc", "pick-dtt-tenthousand1000-ffc", "pick-dtt-tenthousand100-ffc", "pick-dtt-tenthousand10-ffc", "pick-dtt-tenthousand01-ffc",
		"pick-dtt-thousand100-ffc", "pick-dtt-thousand10-ffc", "pick-dtt-thousand01-ffc", "pick-dtt-hundred10-ffc", "pick-dtt-hundred01-ffc", "pick-dtt-ten01-ffc" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-dtt-ffc")[0], "kind-act");
}

// 分分彩 龙虎和 万千
function dttTenThousand1000FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-tenthousand1000", "win-number-dtt-ten-thousand1000" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-tenthousand1000-ffc")[0], "pick-act");
}

// 分分彩 龙虎和 万百
function dttTenThousand100FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-tenthousand100", "win-number-dtt-ten-thousand100" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-tenthousand100-ffc")[0], "pick-act");
}

// 分分彩 龙虎和 万十
function dttTenThousand10FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-tenthousand10", "win-number-dtt-ten-thousand10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-tenthousand10-ffc")[0], "pick-act");
}

// 分分彩 龙虎和 万个
function dttTenThousand01FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-tenthousand01", "win-number-dtt-ten-thousand01" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-tenthousand01-ffc")[0], "pick-act");
}

// 分分彩 龙虎和 千百
function dttThousand100FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-thousand100", "win-number-dtt-thousand100" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-thousand100-ffc")[0], "pick-act");
}

// 分分彩 龙虎和 千十
function dttThousand10FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-thousand10", "win-number-dtt-thousand10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-thousand10-ffc")[0], "pick-act");
}

// 分分彩 龙虎和 千个
function dttThousand01FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-thousand01", "win-number-dtt-thousand01" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-thousand01-ffc")[0], "pick-act");
}

// 分分彩 龙虎和 百十
function dttHundred10FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-hundred10", "win-number-dtt-hundred10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-hundred10-ffc")[0], "pick-act");
}

// 分分彩 龙虎和 百个
function dttHundred01FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-hundred01", "win-number-dtt-hundred01" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-hundred01-ffc")[0], "pick-act");
}

// 分分彩 龙虎和 十个
function dttTen01FFC() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-ten01", "win-number-dtt-ten01" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-ten01-ffc")[0], "pick-act");
}

// 11选5 三星 前三直选复式
function starThreeDirectFront3Multi115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-digit-01", "ball-digit-02", "ball-digit-03", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-dir-f3-multi-115")[0], "pick-act");
}

// 11选5 三星 前三直选单式
function starThreeDirectFront3Single115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-dir-f3-single-115")[0], "pick-act");
}

// 11选5 三星 前三组选复式
function starThreeGroupFront3Multi115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-front-03", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-group-f3-multi-115")[0], "pick-act");
}

// 11选5 三星 前三组选单式
function starThreeGroupFront3Single115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-group-f3-single-115")[0], "pick-act");
}

// 11选5 三星 前三组选胆托
function starThreeGroupFront3Pull115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-promise", "ball-pull", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-group-f3-pull-115")[0], "pick-act");
}

// 11选5 二星(mid_title)
function starTwo115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-115", "pick-star2-direct-115", "pick-star2-dir-f3-multi-115", "pick-star2-dir-f3-single-115", "pick-star2-group-115", "pick-star2-group-f3-multi-115",
		"pick-star2-group-f3-single-115", "pick-star2-group-f3-pull-115" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star2-115")[0], "kind-act");
}

// 11选5 二星 前二直选复式
function starTwoDirectFront3Multi115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-digit-01", "ball-digit-02", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2-dir-f3-multi-115")[0], "pick-act");
}

// 11选5 二星 前二直选单式
function starTwoDirectFront3Single115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2-dir-f3-single-115")[0], "pick-act");
}

// 11选5 二星 前二组选复式
function starTwoGroupFront3Multi115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-front-02", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2-group-f3-multi-115")[0], "pick-act");
}

// 11选5 二星 前二组选单式
function starTwoGroupFront3Single115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2-group-f3-single-115")[0], "pick-act");
}

// 11选5 二星 前二组选胆托
function starTwoGroupFront3Pull115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-promise", "ball-pull", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2-group-f3-pull-115")[0], "pick-act");
}

// 11选5 不定位(mid_title)
function NoAssign115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-115", "pick-star3f-noassign-115", "pick-star3f-noassign-115-btn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-noassign-115")[0], "kind-act");
}

// 11选5 不定位
function starThreeFrontNoAssign115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ , "ball-front-03", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-noassign-115-btn")[0], "pick-act");
}

// 11选5 定单双(mid_title)
function SD115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-115", "pick-sd-115", "pick-sd-115-btn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-sd-115")[0], "kind-act");
}

// 11选5 定单双
function singleDouble115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-sd", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-sd-115-btn")[0], "pick-act");
}

// 11选5 猜中位(mid_title)
function GM115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-115", "pick-guess-middle-115", "pick-guess-middle-115-btn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-qm-115")[0], "kind-act");
}

// 11选5 猜中位
function guessMiddle115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-guess-middle", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-guess-middle-115-btn")[0], "pick-act");
}

// 11选5 定位胆(mid_title)
function star115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-115", "pick-assign-115", "pick-assign-115-btn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-assign-115")[0], "kind-act");
}

// 11选5 定位胆
function Assign115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-digit-01", "ball-digit-02", "ball-digit-03", "ball-digit-04", "ball-digit-05", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-assign-115-btn")[0], "pick-act");
}

// 11选5 任选复式(mid_title)
function anyMulti115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-115", "pick-any-multi-115", "pick-any-11-multi-115", "pick-any-22-multi-115", "pick-any-33-multi-115", "pick-any-44-multi-115", "pick-any-55-multi-115",
		"pick-any-65-multi-115", "pick-any-75-multi-115", "pick-any-85-multi-115" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-any-multi-115")[0], "kind-act");
}

// 11选5 任选复式 任选一中一
function any11Multi115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-any-11", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-11-multi-115")[0], "pick-act");
}

// 11选5 任选复式 任选二中二
function any22Multi115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-any-22", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-22-multi-115")[0], "pick-act");
}

// 11选5 任选复式任选三中三
function any33Multi115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-any-33", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-33-multi-115")[0], "pick-act");
}

// 11选5 任选复式 任选四中四
function any44Multi115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-any-44", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-44-multi-115")[0], "pick-act");
}

// 11选5 任选复式 任选五中五
function any55Multi115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-any-55", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-55-multi-115")[0], "pick-act");
}

// 11选5 任选复式 任选六中五
function any65Multi115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-any-65", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-65-multi-115")[0], "pick-act");
}

// 11选5 任选复式 任选七中五
function any75Multi115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-any-75", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-75-multi-115")[0], "pick-act");
}

// 11选5 任选复式 任选八中五
function any85Multi115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-any-85", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-85-multi-115")[0], "pick-act");
}

// 11选5 任选单式(mid_title)
function anySingle115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-115", "pick-any-single-115", "pick-any-11-single-115", "pick-any-22-single-115", "pick-any-33-single-115", "pick-any-44-single-115", "pick-any-55-single-115",
		"pick-any-65-single-115", "pick-any-75-single-115", "pick-any-85-single-115" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-any-single-115")[0], "kind-act");
}

// 11选5 任选单式 任选一中一
function any11Single115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-11-single-115")[0], "pick-act");
}

// 11选5 任选单式 任选二中二
function any22Single115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-22-single-115")[0], "pick-act");
}

// 11选5 任选单式 任选三中三
function any33Single115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-33-single-115")[0], "pick-act");
}

// 11选5 任选单式 任选四中四
function any44Single115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-44-single-115")[0], "pick-act");
}

// 11选5 任选单式 任选五中五
function any55Single115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-55-single-115")[0], "pick-act");
}

// 11选5 任选单式 任选六中五
function any65Single115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-65-single-115")[0], "pick-act");
}

// 11选5 任选单式 任选七中五
function any75Single115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-75-single-115")[0], "pick-act");
}

// 11选5 任选单式 任选八中五
function any85Single115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-85-single-115")[0], "pick-act");
}

// 11选5 任选胆托(mid_title)
function anyPromise115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-115", "pick-any-promise-115", "pick-any-22-promise-115", "pick-any-33-promise-115", "pick-any-44-promise-115", "pick-any-55-promise-115",
		"pick-any-65-promise-115", "pick-any-75-promise-115", "pick-any-85-promise-115" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-any-promise-115")[0], "kind-act");
}

// 11选5 任选胆托 任选二中二
function any22Promise115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-promise", "ball-pull", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-22-promise-115")[0], "pick-act");
}

// 11选5 任选胆托 任选三中三
function any33Promise115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-promise", "ball-pull", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-33-promise-115")[0], "pick-act");
}

// 11选5 任选胆托 任选四中四
function any44Promise115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-promise", "ball-pull", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-44-promise-115")[0], "pick-act");
}

// 11选5 任选胆托 任选五中五
function any55Promise115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-promise", "ball-pull", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-55-promise-115")[0], "pick-act");
}

// 11选5 任选胆托 任选六中五
function any65Promise115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-promise", "ball-pull", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-65-promise-115")[0], "pick-act");
}

// 11选5 任选胆托 任选七中五
function any75Promise115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-promise", "ball-pull", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-75-promise-115")[0], "pick-act");
}

// 11选5 任选胆托 任选八中五
function any85Promise115() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-promise", "ball-pull", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-any-85-promise-115")[0], "pick-act");
}

// 排列三/五 五星 直选复式
function starFiveDirectMulti35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star5-direct-multi-35")[0], "pick-act");
}

// 排列三/五 五星 直选单式
function starFiveDirectSingle35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star5-direct-single-35")[0], "pick-act");
}

// 排列三/五 五星 组选120
function starFiveGroup12035() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star5-group120", "win-number-star5-group-120" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star5-group-120-35")[0], "pick-act");
}

// 排列三/五 五星 组选60
function starFiveGroup6035() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-single", "ball-star-group-double", "win-number-star5-group-60" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star5-group-60-35")[0], "pick-act");
}

// 排列三/五 五星 组选30
function starFiveGroup3035() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-single", "ball-star-group-double", "win-number-star5-group-30" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star5-group-30-35")[0], "pick-act");
}

// 排列三/五 五星 组选20
function starFiveGroup2035() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-single", "ball-star-group-triple", "win-number-star5-group-20" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star5-group-20-35")[0], "pick-act");
}

// 排列三/五 五星 组选10
function starFiveGroup1035() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-triple", "ball-star-group-double", "win-number-star5-group-10" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star5-group-10-35")[0], "pick-act");
}

// 排列三/五 五星 组选5
function starFiveGroup0535() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-single", "ball-star-group-quadruple", "win-number-star5-group-05" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star5-group-05-35")[0], "pick-act");
}

// 排列三/五 五星 其他 总和大小单双
function starFiveOtherTotalBS35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star5-other-total-bs", "win-number-star5-total-bs" ]; // ,"win-number-star5"];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star5-other-total-bs-35")[0], "pick-act");
}

// 排列三/五 五星 其他 总和组合大小单双
function starFiveOtherTotalBSGroup35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star5-other-total-bsgroup", "win-number-star5-total-bs" ]; // ,"win-number-star5"];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star5-other-total-bsgroup-35")[0], "pick-act");
}

// 排列三/五 前四(mid_title)
function starFourFront35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-35", "pick-star4f-direct-35", "pick-star4f-direct-multi-35", "pick-star4f-direct-single-35", "pick-star4f-group-35", "pick-star4f-group-24-35",
		"pick-star4f-group-12-35", "pick-star4f-group-06-35", "pick-star4f-group-04-35" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("kind-star4f-35")[0], "kind-act");
}

// 排列三/五 前四 直选复式
function starFourFrontDirectMulti35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "win-number-star4f" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star4f-direct-multi-35")[0], "pick-act");
}

// 排列三/五 前四 直选单式
function starFourFrontDirectSingle35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star4f" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star4f-direct-single-35")[0], "pick-act");
}

// 排列三/五 前四 组24
function starFourFrontGroup2435() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star4f-group-24" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star4f-group-24-35")[0], "pick-act");
}

// 排列三/五 前四 组12
function starFourFrontGroup1235() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-double", "ball-star-group-single", "win-number-star4f-group-12" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star4f-group-12-35")[0], "pick-act");
}

// 排列三/五 前四 组06
function starFourFrontGroup0635() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-double", "win-number-star4f-group-06" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star4f-group-06-35")[0], "pick-act");
}

// 排列三/五 前四 组04
function starFourFrontGroup0435() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-triple", "ball-star-group-single", "win-number-star4f-group-04" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star4f-group-04-35")[0], "pick-act");
}

// 排列三/五 后四(mid_title)
function starFourBack35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-35", "pick-star4b-direct-35", "pick-star4b-direct-multi-35", "pick-star4b-direct-single-35", "pick-star4b-group-35", "pick-star4b-group-24-35",
		"pick-star4b-group-12-35", "pick-star4b-group-06-35", "pick-star4b-group-04-35" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("kind-star4b-35")[0], "kind-act");
}

// 排列三/五 后四 直选复式
function starFourBackDirectMulti35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star4b" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star4b-direct-multi-35")[0], "pick-act");
}

// 排列三/五 后四 直选单式
function starFourBackDirectSingle35() {
	var clearName = [];
	var hiddenName = [ "import-checkbox-area" ];
	var showName = [ "import-area", "win-number-star4b" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star4b-direct-single-35")[0], "pick-act");
}

// 排列三/五 后四 组24
function starFourBackGroup2435() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star4b-group-24" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star4b-group-24-35")[0], "pick-act");
}

// 排列三/五 后四 组12
function starFourBackGroup1235() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-double", "ball-star-group-single", "win-number-star4b-group-12" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star4b-group-12-35")[0], "pick-act");
}

// 排列三/五 后四 组06
function starFourBackGroup0635() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-double", "win-number-star4b-group-06" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star4b-group-06-35")[0], "pick-act");
}

// 排列三/五 后四 组04
function starFourBackGroup0435() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-triple", "ball-star-group-single", "win-number-star4b-group-04" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star4b-group-04-35")[0], "pick-act");
}

// 排列三/五 前三(mid_title)
function starThreeFront35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-35", "pick-star3f-direct-35", "pick-star3f-direct-multi-35", "pick-star3f-direct-single-35", "pick-star3f-direct-total-35", "pick-star3f-direct-cross-35",
		"pick-star3f-group-35", "pick-star3f-group-03v2-35", "pick-star3f-group-03v2-single-35", "pick-star3f-group-06v2-35", "pick-star3f-group-06v2-single-35", "pick-star3f-group-mix-35",
		"pick-star3f-group-total-35", "pick-star3f-promise-35", "pick-star3f-other-35", "pick-star3f-other-total-num-35", "pick-star3f-other-jaguar-35", "pick-star3f-other-smooth-35",
		"pick-star3f-other-pair-35", "pick-star3f-other-half-35", "pick-star3f-other-mix6-35" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("kind-star3f-35")[0], "kind-act");
}

// 排列三/五 前三 直选复式
function starThreeFrontDirectMulti35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten-thousand", "ball-thousand", "ball-hundred", "win-number-star3f" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-direct-multi-35")[0], "pick-act");
}

// 排列三/五 前三 直选单式
function starThreeFrontDirectSingle35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3f" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-direct-single-35")[0], "pick-act");
}

// 排列三/五 前三 直选和值
function starThreeFrontDirectTotal35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dt", "win-number-star3f-direct-total" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-direct-total-35")[0], "pick-act");
}

// 排列三/五 前三 直选跨度
function starThreeFrontDirectCross35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dc", "win-number-star3f-direct-cross" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-direct-cross-35")[0], "pick-act");
}

// 排列三/五 前三 组三
function starThreeFrontGroup03v235() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-03", "win-number-star3f-group-03" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-group-03v2-35")[0], "pick-act");
}

// 排列三/五 前三 组六
function starThreeFrontGroup06v235() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-06", "win-number-star3f-group-06" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-group-06v2-35")[0], "pick-act");
}

// 排列三/五 前三 组三单式
function starThreeFrontGroup03v2single35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3f-group-03" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-group-03v2-single-35")[0], "pick-act");
}

// 排列三/五 前三 组六单式
function starThreeFrontGroup06v2single35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3f-group-06" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-group-06v2-single-35")[0], "pick-act");
}

// 排列三/五 前三 混合组选
function starThreeFrontGroupMix35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3f-group-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-group-mix-35")[0], "pick-act");
}

// 排列三/五 前三 组选和值
function starThreeFrontGroupTotal35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-gt", "win-number-star3f-direct-total" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-group-total-35")[0], "pick-act");
}

// 排列三/五 前三 包胆
function starThreeFrontPromise35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star3f" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-promise-35")[0], "pick-act");
}

// 排列三/五 前三 其他 和值尾数
function starThreeFrontTotalNum35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-notitle", "win-number-star3f-other-total" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-other-total-num-35")[0], "pick-act");
}

// 排列三/五 前三 其他 豹子
function starThreeFrontJaquar35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-jaguar", "win-number-star3f-other-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-other-jaguar-35")[0], "pick-act");
}

// 排列三/五 前三 其他 顺子
function starThreeFrontSmooth35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-smooth", "win-number-star3f-other-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-other-smooth-35")[0], "pick-act");
}

// 排列三/五 前三 其他 对子
function starThreeFrontPair35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-pair", "win-number-star3f-other-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-other-pair-35")[0], "pick-act");
}

// 排列三/五 前三 其他 半顺
function starThreeFrontHalf35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-half", "win-number-star3f-other-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-other-half-35")[0], "pick-act");
}

// 排列三/五 前三 其他 杂六
function starThreeFrontMix635() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-mix6", "win-number-star3f-other-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3f-other-mix6-35")[0], "pick-act");
}

// 排列三/五 中三(mid_title)
function starThreeMiddle35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-35", "pick-star3m-direct-35", "pick-star3m-direct-multi-35", "pick-star3m-direct-single-35", "pick-star3m-direct-total-35", "pick-star3m-direct-cross-35",
		"pick-star3m-group-35", "pick-star3m-group-03v2-35", "pick-star3m-group-03v2-single-35", "pick-star3m-group-06v2-35", "pick-star3m-group-06v2-single-35", "pick-star3m-group-mix-35",
		"pick-star3m-group-total-35", "pick-star3m-promise-35", "pick-star3m-other-35", "pick-star3m-other-total-num-35", "pick-star3m-other-jaguar-35", "pick-star3m-other-smooth-35",
		"pick-star3m-other-pair-35", "pick-star3m-other-half-35", "pick-star3m-other-mix6-35" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("kind-star3m-35")[0], "kind-act");
}

// 排列三/五 中三 直选复式
function starThreeMiddleDirectMulti35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-thousand", "ball-hundred", "ball-ten", "win-number-star3m" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3m-direct-multi-35")[0], "pick-act");
}

// 排列三/五 中三 直选单式
function starThreeMiddleDirectSingle35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3m" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3m-direct-single-35")[0], "pick-act");
}

// 排列三/五 中三 直选和值
function starThreeMiddleDirectTotal35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dt", "win-number-star3m-direct-total" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3m-direct-total-35")[0], "pick-act");
}

// 排列三/五 中三 直选跨度
function starThreeMiddleDirectCross35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dc", "win-number-star3m-direct-cross" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3m-direct-cross-35")[0], "pick-act");
}

// 排列三/五 中三 组三
function starThreeMiddleGroup03v235() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-03", "win-number-star3m-group-03" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3m-group-03v2-35")[0], "pick-act");
}

// 排列三/五 中三 组六
function starThreeMiddleGroup06v235() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-06", "win-number-star3m-group-06" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3m-group-06v2-35")[0], "pick-act");
}

// 排列三/五 中三 组三单式
function starThreeMiddleGroup03v2single35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3m-group-03" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3m-group-03v2-single-35")[0], "pick-act");
}

// 排列三/五 中三 组六单式
function starThreeMiddleGroup06v2single35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3m-group-06" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3m-group-06v2-single-35")[0], "pick-act");
}

// 排列三/五 中三 混合组选
function starThreeMiddleGroupMix35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3m-group-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3m-group-mix-35")[0], "pick-act");
}

// 排列三/五 中三 组选和值
function starThreeMiddleGroupTotal35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-gt", "win-number-star3m-direct-total" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3m-group-total-35")[0], "pick-act");
}

// 排列三/五 中三 包胆
function starThreeMiddlePromise35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star3m" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3m-promise-35")[0], "pick-act");
}

// 排列三/五 中三 其他 和值尾数
function starThreeMiddleTotalNum35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-notitle", "win-number-star3m-other-total" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3m-other-total-num-35")[0], "pick-act");
}

// 排列三/五 中三 其他 豹子
function starThreeMiddleJaquar35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-jaguar", "win-number-star3m-other-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3m-other-jaguar-35")[0], "pick-act");
}

// 排列三/五 中三 其他 顺子
function starThreeMiddleSmooth35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-smooth", "win-number-star3m-other-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3m-other-smooth-35")[0], "pick-act");
}

// 排列三/五 中三 其他 对子
function starThreeMiddlePair35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-pair", "win-number-star3m-other-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3m-other-pair-35")[0], "pick-act");
}

// 排列三/五 中三 其他 半顺
function starThreeMiddleHalf35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-half", "win-number-star3m-other-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3m-other-half-35")[0], "pick-act");
}

// 排列三/五 中三 其他 杂六
function starThreeMiddleMix635() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-mix6", "win-number-star3m-other-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("kind-star3m-35")[0], "kind-act");
	addClass(document.getElementsByClassName("pick-star3m-other-mix6-35")[0], "pick-act");
}

// 排列三/五 后三(mid_title)
function starThreeBack35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-35", "pick-star3b-direct-35", "pick-star3b-direct-multi-35", "pick-star3b-direct-single-35", "pick-star3b-direct-total-35", "pick-star3b-direct-cross-35",
		"pick-star3b-group-35", "pick-star3b-group-03v2-35", "pick-star3b-group-03v2-single-35", "pick-star3b-group-06v2-35", "pick-star3b-group-06v2-single-35", "pick-star3b-group-mix-35",
		"pick-star3b-group-total-35", "pick-star3b-promise-35", "pick-star3b-other-35", "pick-star3b-other-total-num-35", "pick-star3b-other-jaguar-35", "pick-star3b-other-smooth-35",
		"pick-star3b-other-pair-35", "pick-star3b-other-half-35", "pick-star3b-other-mix6-35" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("kind-star3b-35")[0], "kind-act");
}

// 排列三/五 后三 直选复式
function starThreeBackDirectMulti35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-hundred", "ball-ten", "ball-one", "win-number-star3b" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-direct-multi-35")[0], "pick-act");
}

// 排列三/五 后三 直选单式
function starThreeBackDirectSingle35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3b" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-direct-single-35")[0], "pick-act");
}

// 排列三/五 后三 直选和值
function starThreeBackDirectTotal35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dt", "win-number-star3b-direct-total" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-direct-total-35")[0], "pick-act");
}

// 排列三/五 后三 直选跨度
function starThreeBackDirectCross35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dc", "win-number-star3b-direct-cross" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-direct-cross-35")[0], "pick-act");
}

// 排列三/五 后三 组三
function starThreeBackGroup03v235() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-03", "win-number-star3b-group-03" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-group-03v2-35")[0], "pick-act");
}

// 排列三/五 后三 组六
function starThreeBackGroup06v235() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-06", "win-number-star3b-group-06" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-group-06v2-35")[0], "pick-act");
}

// 排列三/五 后三 组三单式
function starThreeBackGroup03v2single35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3b-group-03" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-group-03v2-single-35")[0], "pick-act");
}

// 排列三/五 后三 组六单式
function starThreeBackGroup06v2single35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3b-group-06" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-group-06v2-single-35")[0], "pick-act");
}

// 排列三/五 后三 混合组选
function starThreeBackGroupMix35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3b-group-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-group-mix-35")[0], "pick-act");
}

// 排列三/五 后三 组选和值
function starThreeBackGroupTotal35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-gt", "win-number-star3b-group-total" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-group-total-35")[0], "pick-act");
}

// 排列三/五 后三 包胆
function starThreeBackPromise35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star3b" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-promise-35")[0], "pick-act");
}

// 排列三/五 后三 其他 和值尾数
function starThreeBackTotalNum35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-notitle", "win-number-star3b-other-total" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-other-total-num-35")[0], "pick-act");
}

// 排列三/五 后三 其他 豹子
function starThreeBackJaquar35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-jaguar", "win-number-star3b-other-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-other-jaguar-35")[0], "pick-act");
}

// 排列三/五 后三 其他 顺子
function starThreeBackSmooth35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-smooth", "win-number-star3b-other-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-other-smooth-35")[0], "pick-act");
}

// 排列三/五 后三 其他 对子
function starThreeBackPair35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-pair", "win-number-star3b-other-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-other-pair-35")[0], "pick-act");
}

// 排列三/五 后三 其他 半顺
function starThreeBackHalf35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-half", "win-number-star3b-other-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-other-half-35")[0], "pick-act");
}

// 排列三/五 后三 其他 杂六
function starThreeBackMix635() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-mix6", "win-number-star3b-other-mix" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3b-other-mix6-35")[0], "pick-act");
}

// 排列三/五 前二(mid_title)
function starTwoFront35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-35", "pick-star2f-direct-35", "pick-star2f-direct-multi-35", "pick-star2f-direct-single-35", "pick-star2f-direct-total-35", "pick-star2f-direct-cross-35",
		"pick-star2f-group-35", "pick-star2f-group-multi-35", "pick-star2f-group-single-35", "pick-star2f-group-total-35", "pick-star2f-group-promise-35" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("kind-star2f-35")[0], "kind-act");
}

// 排列三/五 前二 直选复式
function starTwoFrontDirectMulti35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten-thousand", "ball-thousand", "win-number-star2f" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star2f-direct-multi-35")[0], "pick-act");
}

// 排列三/五 前二 直选单式
function starTwoFrontDirectSingle35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star2f" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star2f-direct-single-35")[0], "pick-act");
}

// 排列三/五 前二 直选和值
function starTwoFrontDirectTotal35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-19balls", "win-number-star2f-total" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star2f-direct-total-35")[0], "pick-act");
}

// 排列三/五 前二 直选跨度
function starTwoFrontDirectCross35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star2f-cross" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star2f-direct-cross-35")[0], "pick-act");
}

// 排列三/五 前二 组选复式
function starTwoFrontGroupMulti35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group", "win-number-star2f" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star2f-group-multi-35")[0], "pick-act");
}

// 排列三/五 前二 组选单式
function starTwoFrontGroupSingle35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star2f" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star2f-group-single-35")[0], "pick-act");
}

// 排列三/五 前二 组选和值
function starTwoFrontGroupTotal35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-17balls", "win-number-star2f-total" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star2f-group-total-35")[0], "pick-act");
}

// 排列三/五 前二 组选包胆
function starTwoFrontGroupPromise35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star2f" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star2f-group-promise-35")[0], "pick-act");
}

// 排列三/五 后二(mid_title)
function starTwoBack35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-35", "pick-star2b-direct-35", "pick-star2b-direct-multi-35", "pick-star2b-direct-single-35", "pick-star2b-direct-total-35", "pick-star2b-direct-cross-35",
		"pick-star2b-group-35", "pick-star2b-group-multi-35", "pick-star2b-group-single-35", "pick-star2b-group-total-35", "pick-star2b-group-promise-35" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("kind-star2b-35")[0], "kind-act");
}

// 排列三/五 后二 直选复式
function starTwoBackDirectMulti35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten", "ball-one", "win-number-star2b" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star2b-direct-multi-35")[0], "pick-act");
}

// 排列三/五 后二 直选单式
function starTwoBackDirectSingle35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star2b" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star2b-direct-single-35")[0], "pick-act");
}

// 排列三/五 后二 直选和值
function starTwoBackDirectTotal35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-19balls", "win-number-star2b-total" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star2b-direct-total-35")[0], "pick-act");
}

// 排列三/五 后二 直选跨度
function starTwoBackDirectCross35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star2b-cross" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star2b-direct-cross-35")[0], "pick-act");
}

// 排列三/五 后二 组选复式
function starTwoBackGroupMulti35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group", "win-number-star2b" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star2b-group-multi-35")[0], "pick-act");
}

// 排列三/五 后二 组选单式
function starTwoBackGroupSingle35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star2b" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star2b-group-single-35")[0], "pick-act");
}

// 排列三/五 后二 组选和值
function starTwoBackGroupTotal35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-17balls", "win-number-star2b-total" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star2b-group-total-35")[0], "pick-act");
}

// 排列三/五 后二 组选包胆
function starTwoBackGroupPromise35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star2b" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star2b-group-promise-35")[0], "pick-act");
}

// 排列三/五 定位胆(mid_title)
function star35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-35", "pick-star1-35", "pick-star1-35-btn" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("kind-star1-35")[0], "kind-act");
}

// 排列三/五 定位胆
function starOne35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star1-35-btn")[0], "pick-act");
}

// 排列三/五 大小单双(mid_title)
function bigSmall35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-35", "pick-bs-35", "pick-bigsmall-f2-35", "pick-bigsmall-b2-35", "pick-bigsmall-any2-35", "pick-bigsmall-f3-35", "pick-bigsmall-b3-35" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("kind-bs-35")[0], "kind-act");
}

// 排列三/五 大小单双 前二大小单双
function bigSmallFrontTwo35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-bs-ten-thousand", "ball-star-bs-thousand", "win-number-bssd-front02" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-bigsmall-f2-35")[0], "pick-act");
}

// 排列三/五 大小单双 后二大小单双
function bigSmallBackTwo35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-bs-ten", "ball-star-bs-one", "win-number-bs-back02" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-bigsmall-b2-35")[0], "pick-act");
}

// 排列三/五 大小单双 任选二大小单双
function bigSmallAnyTwo35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-bs-ten-thousand", "ball-star-bs-thousand", , "ball-star-bs-hundred", "ball-star-bs-ten", "ball-star-bs-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-bigsmall-any2-35")[0], "pick-act");
}

// 排列三/五 大小单双 前三大小单双
function bigSmallFrontThree35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-bs-ten-thousand", "ball-star-bs-thousand", "ball-star-bs-hundred", "win-number-star3f" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-bigsmall-f3-35")[0], "pick-act");
}

// 排列三/五 大小单双 后三大小单双
function bigSmallBackThree35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-bs-hundred", "ball-star-bs-ten", "ball-star-bs-one", "win-number-star3b" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-bigsmall-b3-35")[0], "pick-act");
}

// 排列三/五 不定位(mid_title)
function NoAssign35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-35", "pick-star3-noassign-35", "pick-star3-noassign-f3n1-35", "pick-star3-noassign-b3n1-35", "pick-star3-noassign-f3n2-35", "pick-star3-noassign-b3n2-35",
		"pick-star3-noassign-any3n1-35", "pick-star4-noassign-35", "pick-star3-noassign-s4n1-35", "pick-star3-noassign-s4n2-35", "pick-star5-noassign-35", "pick-star5-noassign-s5n1-35",
		"pick-star5-noassign-s5n2-35", "pick-star5-noassign-s5n3-35" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("kind-noassign-35")[0], "kind-act");
}

// 排列三/五 不定位 三星 前三一码不定位
function star3NoAssignFront3Num0135() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3-noassign-f3n1-35")[0], "pick-act");
}

// 排列三/五 不定位 三星 后三一码不定位
function star3NoAssignBack3Num0135() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3-noassign-b3n1-35")[0], "pick-act");
}

// 排列三/五 不定位 三星 前三二码不定位
function star3NoAssignFront3Num0235() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3-noassign-f3n2-35")[0], "pick-act");
}

// 排列三/五 不定位 三星 后三二码不定位
function star3NoAssignBack3Num0235() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3-noassign-b3n2-35")[0], "pick-act");
}

// 排列三/五 不定位 三星 任三一码不定位
function star3NoAssignAny3Num0135() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3-noassign-any3n1-35")[0], "pick-act");
}

// 排列三/五 不定位 四星 四星一码不定位
function star4NoAssignNum0135() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3-noassign-s4n1-35")[0], "pick-act");
}

// 排列三/五 不定位 四星 四星二码不定位
function star4NoAssignNum0235() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star3-noassign-s4n2-35")[0], "pick-act");
}

// 排列三/五 不定位 五星 五星一码不定位
function star5NoAssignNum0135() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star5-noassign-s5n1-35")[0], "pick-act");
}

// 排列三/五 不定位 五星 五星二码不定位
function star5NoAssignNum0235() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star5-noassign-s5n2-35")[0], "pick-act");
}

// 排列三/五 不定位 五星 五星三码不定位
function star5NoAssignNum0335() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-star5-noassign-s5n3-35")[0], "pick-act");
}

// 排列三/五 趣味(mid_title)
function Fun35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-35", "pick-fun-35", "pick-fun-5star3-35", "pick-fun-4star3-35", "pick-fun-f3star2-35", "pick-fun-b3star2-35", "pick-section-35", "pick-section-5star3-35",
		"pick-section-4star3-35", "pick-section-f3star2-35", "pick-section-b3star2-35", "pick-special-35", "pick-special-one-35", "pick-special-two-35", "pick-special-three-35",
		"pick-special-four-35", ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("kind-funny-35")[0], "kind-act");
}

// 排列三/五 趣味 五码趣味三星
function Fun05star335() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-fun-ten-thousand", "ball-fun-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-fun-5star3-35")[0], "pick-act");
}

// 排列三/五 趣味 四码趣味三星
function Fun04star335() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-fun-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-fun-4star3-35")[0], "pick-act");
}

// 排列三/五 趣味 后三码趣味二星
function FunBack03star235() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-fun-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-fun-b3star2-35")[0], "pick-act");
}

// 排列三/五 趣味 前三趣味二星
function FunFront03star235() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-fun-ten-thousand", "ball-thousand", "ball-hundred", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-fun-f3star2-35")[0], "pick-act");
}

// 排列三/五 趣味 区间 五码区间三星
function star3SectionNum0535() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-section-ten-thousand", "ball-section-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-section-5star3-35")[0], "pick-act");
}

// 排列三/五 趣味 区间 四码区间三星
function star3SectionNum0435() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-section-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-section-4star3-35")[0], "pick-act");
}

// 排列三/五 趣味 区间 后三区间二星
function star2SectionBackNum0335() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-section-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-section-b3star2-35")[0], "pick-act");
}

// 排列三/五 趣味 区间 前三区间二星
function star2SectionFrontNum0335() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-section-ten-thousand", "ball-thousand", "ball-hundred", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-section-f3star2-35")[0], "pick-act");
}

// 排列三/五 趣味 特殊 一帆风顺
function specialOne35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-special-one-35")[0], "pick-act");
}

// 排列三/五 趣味 特殊 好事成双
function specialTwo35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-special-two-35")[0], "pick-act");
}

// 排列三/五 趣味 特殊 三星报喜
function specialThree35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-special-three-35")[0], "pick-act");
}

// 排列三/五 趣味 特殊 四季发财
function specialFour35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10playballs", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-special-four-35")[0], "pick-act");
}

// 排列三/五 任选(mid_title)
function any35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-35", "pick-any2-35", "pick-any2-dir-multi-35", "pick-any2-dir-single-35", "pick-any2-dir-total-35", "pick-any2-dir-cross-35", "pick-any2-group-multi-35",
		"pick-any2-group-single-35", "pick-any2-group-total-35", "pick-any3-35", "pick-any3-dir-multi-35", "pick-any3-dir-single-35", "pick-any3-dir-total-35", "pick-any3-dir-cross-35",
		"pick-any3-total-35", "pick-any3-group-3-multi-35", "pick-any3-group-3-single-35", "pick-any3-group-6-multi-35", "pick-any3-group-6-multi-35", "pick-any3-group-6-single-35",
		"pick-any3-group-mix-35", "pick-any4-35", "pick-any4-dir-multi-35", "pick-any4-dir-single-35", "pick-any4-group-24-35", "pick-any4-group-12-35", "pick-any4-group-06-35",
		"pick-any4-group-04-35" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("kind-any-35")[0], "kind-act");
}

// 排列三/五 任选二星 直选复式
function anyTwoDirectMulti35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any2-dir-multi-35")[0], "pick-act");
}

// 排列三/五 任选二星 直选单式
function anyTwoDirectSingle35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any2-dir-single-35")[0], "pick-act");
}

// 排列三/五 任选二星 直选和值
function anyTwoDirectTotal35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-star-19balls", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any2-dir-total-35")[0], "pick-act");
}

// 排列三/五 任选二星 直选跨度
function anyTwoDirectCross35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-star-10playballs", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any2-dir-cross-35")[0], "pick-act");
}

// 排列三/五 任选二星 组选复式
function anyTwoGrouptMulti35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-star-group", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any2-group-multi-35")[0], "pick-act");
}

// 排列三/五 任选二星 组选单式
function anyTwoGrouptSingle35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any2-group-single-35")[0], "pick-act");
}

// 排列三/五 任选二星 组选和值
function anyTwoGrouptTotal35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-star-17balls", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any2-group-total-35")[0], "pick-act");
}

// 排列三/五 任选二星 组选包胆
function anyTwoGrouptPromise35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-star-10balls", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any2-group-promise-35")[0], "pick-act");
}

// 對照表沒看到
// 排列三/五 任选三星 直选复式
function anyThreeDirectMulti35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any3-dir-multi-35")[0], "pick-act");
}

// 排列三/五 任选三星 直选单式
function anyThreeDirectSingle35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any3-dir-single-35")[0], "pick-act");
}

// 排列三/五 任选三星 直选和值
function anyThreeDirectTotal35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-star-dt", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any3-dir-total-35")[0], "pick-act");
}

// 排列三/五 任选三星 直选跨度
function anyThreeDirectCross35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-star-10playballs", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any3-dir-cross-35")[0], "pick-act");
}

// 排列三/五 任选三星 组三复式
function anyThreeGroup03Multi35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-star-group", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any3-group-3-multi-35")[0], "pick-act");
}

// 排列三/五 任选三星 组三单式
function anyThreeGroup03ingle35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any3-group-3-single-35")[0], "pick-act");
}

// 排列三/五 任选三星 组六复式
function anyThreeGroup06Multi35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-star-group-06", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any3-group-6-multi-35")[0], "pick-act");
}

// 排列三/五 任选三星 组六单式
function anyThreeGroup06Single35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any3-group-6-single-35")[0], "pick-act");
}

// 排列三/五 任选三星 混合组选
function anyThreeGroupMix35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any3-group-mix-35")[0], "pick-act");
}

// 對照表沒看到
// 排列三/五 任选三星 组选和值
function anyThreeGroupTotal35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-star-group-total", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any3-total-35")[0], "pick-act");
}

// 對照表沒看到
// 排列三/五 任选三星 组选包胆
function anyThreeGroupPromise35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-star-10balls", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any3-promise-35")[0], "pick-act");
}

// 排列三/五 任选四星 直选复式
function anyFourDirecttMulti35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-ten-thousand", "ball-thousand", "ball-hundred", "ball-ten", "ball-one", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any4-dir-multi-35")[0], "pick-act");
}

// 排列三/五 任选四星 直选单式
function anyFourDirectSingle35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "import-area", "import-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any4-dir-single-35")[0], "pick-act");
}

// 排列三/五 任选四星 组24
function anyFourGroup2435() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-notitle", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any4-group-24-35")[0], "pick-act");
}

// 排列三/五 任选四星 组12
function anyFourGroup1235() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-star-group-double", "ball-star-group-single", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any4-group-12-35")[0], "pick-act");
}

// 排列三/五 任选四星 组06
function anyFourGroup0635() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-star-group-double", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any4-group-06-35")[0], "pick-act");
}

// 排列三/五 任选四星 组04
function anyFourGroup0435() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-any2-35", "pick-any3-35", "pick-any4-35", "ball-star-group-triple", "ball-star-group-single", "ball-checkbox", "ball-checkbox-area", "win-number-star5" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-any4-group-04-35")[0], "pick-act");
}

// 排列三/五 龙虎和(mid_title)
function dtt35() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-35", "pick-dtt-35", "pick-dtt-tenthousand1000-35", "pick-dtt-tenthousand100-35", "pick-dtt-tenthousand10-35", "pick-dtt-tenthousand01-35",
		"pick-dtt-thousand100-35", "pick-dtt-thousand10-35", "pick-dtt-thousand01-35", "pick-dtt-hundred10-35", "pick-dtt-hundred01-35", "pick-dtt-ten01-35" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("kind-dtt-35")[0], "kind-act");
}

// 排列三/五 龙虎和 万千
function dttTenThousand100035() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-tenthousand1000", "win-number-dtt-ten-thousand1000" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-dtt-tenthousand1000-35")[0], "pick-act");
}

// 排列三/五 龙虎和 万百
function dttTenThousand10035() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-tenthousand100", "win-number-dtt-ten-thousand100" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-dtt-tenthousand100-35")[0], "pick-act");
}

// 排列三/五 龙虎和 万十
function dttTenThousand1035() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-tenthousand10", "win-number-dtt-ten-thousand10" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-dtt-tenthousand10-35")[0], "pick-act");
}

// 排列三/五 龙虎和 万个
function dttTenThousand0135() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-tenthousand01", "win-number-dtt-ten-thousand01" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-dtt-tenthousand01-35")[0], "pick-act");
}

// 排列三/五 龙虎和 千百
function dttThousand10035() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-thousand100", "win-number-dtt-thousand100" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-dtt-thousand100-35")[0], "pick-act");
}

// 排列三/五 龙虎和 千十
function dttThousand1035() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-thousand10", "win-number-dtt-thousand10" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-dtt-thousand10-35")[0], "pick-act");
}

// 排列三/五 龙虎和 千个
function dttThousand0135() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-thousand01", "win-number-dtt-thousand01" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-dtt-thousand01-35")[0], "pick-act");
}

// 排列三/五 龙虎和 百十
function dttHundred1035() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-hundred10", "win-number-dtt-hundred10" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-dtt-hundred10-35")[0], "pick-act");
}

// 排列三/五 龙虎和 百个
function dttHundred0135() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-hundred01", "win-number-dtt-hundred01" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-dtt-hundred01-35")[0], "pick-act");
}

// 排列三/五 龙虎和 十个
function dttTen0135() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-ten01", "win-number-dtt-ten01" ];
	initBall(clearName, hiddenName, showName, 2);
	addClass(document.getElementsByClassName("pick-dtt-ten01-35")[0], "pick-act");
}

// 3D 三星 直选复式
function starThreeDirectMulti3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-hundred", "ball-ten", "ball-one", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-direct-multi-3d")[0], "pick-act");
}

// 3D 三星 直选单式
function starThreeDirectSingle3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-direct-single-3d")[0], "pick-act");
}

// 3D 三星 直选和值
function starThreeDirectTotal3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-dt", "win-number-star3-direct-total-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-direct-total-3d")[0], "pick-act");
}

// 3D 三星 组三
function starThreeGroup03v23D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-03", "win-number-star3-group-03-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-group-03v2-3d")[0], "pick-act");
}

// 3D 三星 组六
function starThreeGroup06v23D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group-06", "win-number-star3-group-06-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-group-06v2-3d")[0], "pick-act");
}

// 3D 三星 组三单式
function starThreeGroup03v2single3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3-group-03-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-group-03v2-single-3d")[0], "pick-act");
}

// 3D 三星 组六单式
function starThreeGroup06v2single3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3-group-06-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-group-06v2-single-3d")[0], "pick-act");
}

// 3D 三星 混合组选
function starThreeGroupMix3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "help-select", "win-number-star3-group-mix-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-group-mix-3d")[0], "pick-act");
}

// 3D 三星 组选和值
function starThreeGroupTotal3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-gt", "win-number-star3-direct-total-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-group-total-3d")[0], "pick-act");
}

// 對照表沒看到
// 3D 三星 包胆
function starThreePromise3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3-promise-3d")[0], "pick-act");
}

// 3D 前二(mid_title)
function starTwoFront3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-3d", "pick-star2f-direct-3d", "pick-star2f-direct-multi-3d", "pick-star2f-direct-single-3d", "pick-tab pick-star2f-group-3d", "pick-star2f-group-multi-3d",
		"pick-star2f-group-single-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star2f-3d")[0], "kind-act");
}

// 3D 前二 直选复式
function starTwoFrontDirectMulti3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-hundred", "ball-ten", "win-number-star3f-02-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-direct-multi-3d")[0], "pick-act");
}

// 3D 前二 直选单式
function starTwoFrontDirectSingle3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star3f-02-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-direct-single-3d")[0], "pick-act");
}

// 3D 前二 组选复式
function starTwoFrontGroupMulti3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group", "win-number-star3f-02-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-group-multi-3d")[0], "pick-act");
}

// 3D 前二 组选单式
function starTwoFrontGroupSingle3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star3f-02-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-group-single-3d")[0], "pick-act");
}

// 對照表沒看到
// 3D 前二 组选和值
function starTwoFrontGroupTotal3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-17balls" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-group-total-3d")[0], "pick-act");
}

// 對照表沒看到
// 3D 前二 组选包胆
function starTwoFrontGroupPromise3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-10balls" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-group-promise-3d")[0], "pick-act");
}

// 3D 前二(mid_title)
function starTwoBack3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-3d", "pick-star2b-direct-3d", "pick-star2b-direct-multi-3d", "pick-star2b-direct-single-3d", "pick-tab pick-star2b-group-3d", "pick-star2b-group-multi-3d",
		"pick-star2b-group-single-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star2b-3d")[0], "kind-act");
}

// 3D 后二 直选复式
function starTwoBackDirectMulti3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-ten", "ball-one", "win-number-star3b-02-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-direct-multi-3d")[0], "pick-act");
}

// 3D 后二 直选单式
function starTwoBackDirectSingle3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star3b-02-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-direct-single-3d")[0], "pick-act");
}

// 3D 后二 组选复式
function starTwoBackGroupMulti3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-star-group", "win-number-star3b-02-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-group-multi-3d")[0], "pick-act");
}

// 3D 后二 组选单式
function starTwoBackGroupSingle3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star3b-02-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-group-single-3d")[0], "pick-act");
}

// 3D 一星 定位胆(mid_title)
function star3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-3d", "pick-star1-3d", "pick-star1-3d-btn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star1-3d")[0], "kind-act");
}

// 3D 一星 定位胆
function starOne3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-hundred", "ball-ten", "ball-one", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star1-3d-btn")[0], "pick-act");
}

// 3D 不定位(mid_title)
function noAssign3d() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-3d", "pick-noassign-3d", "pick-noassign-fb3n1-3d", "pick-noassign-fb3n2-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-noassign-3d")[0], "kind-act");
}

// 3D 不定位 前后三一码不定位
function noAssign3dFb3n1() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign-3d", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-noassign-fb3n1-3d")[0], "pick-act");
}

// 3D 不定位 前后三二码不定位
function noAssign3dFb3n2() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-noassign-3d", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-noassign-fb3n2-3d")[0], "pick-act");
}

// 3D 龙虎和(mid_title)
function dtt3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-3d", "pick-dtt-3d", "pick-dtt-hundred10-3d", "pick-dtt-hundred01-3d", "pick-dtt-ten01-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-dtt-3d")[0], "kind-act");
}

// 3D 龙虎和 百十
function dttHundred103D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-hundred10", "win-number-dtt-hundred10-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-hundred10-3d")[0], "pick-act");
}

// 3D 龙虎和 百个
function dttHundred013D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-hundred01", "win-number-dtt-hundred01-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-hundred01-3d")[0], "pick-act");
}

// 3D 龙虎和 十个
function dttTen01S3D() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dtt-ten01", "win-number-dtt-ten01-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dtt-ten01-3d")[0], "pick-act");
}
// PK10 冠亚 和值
function winTotal() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-win-total-pk10", "win-number-winsecond-total-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-total-pk10")[0], "pick-act");
}

// PK10 冠亚 和值
function winTotal() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-win-total-pk10", "win-number-winsecond-total-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-total-pk10")[0], "pick-act");
}

// PK10 冠亚 大小单双
function winBigSmall() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-win-bs-pk10", "win-number-winsecond-total-bsds-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-bs-pk10")[0], "pick-act");
}

// PK10 猜名次(mid_title)
function guessPK10() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-pk10", "pick-gr-pk10", "pick-grall-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-gr-pk10")[0], "kind-act");
}

// PK10 猜名次 前五名
function guessRankF5() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-gr01-pk10", "ball-gr02-pk10", "ball-gr03-pk10", "ball-gr04-pk10", "ball-gr05-pk10", "win-number-guess-rank-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-grf5-pk10")[0], "pick-act");
}

// PK10 猜名次 后五名
function guessRankB5() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ , "ball-gr07-pk10", "ball-gr08-pk10", "ball-gr09-pk10", "ball-gr10-pk10", "win-number-guess-rank-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-grb5-pk10")[0], "pick-act");
}

// PK10 猜名次 全部名次
function guessRankAll() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-gr01-pk10", "ball-gr02-pk10", "ball-gr03-pk10", "ball-gr04-pk10", "ball-gr05-pk10", "ball-gr06-pk10", "ball-gr07-pk10", "ball-gr08-pk10", "ball-gr09-pk10",
		"ball-gr10-pk10", "win-number-guess-rank-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-grall-pk10")[0], "pick-act");
}

// PK10 龙虎(mid_title)
function dtPK10() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-pk10", "pick-dt-pk10", "pick-dt-pk10-btn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-dt-pk10")[0], "kind-act");
}

// PK10 龙虎
function dragonTigerPK10() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-dt1v10-pk10", "ball-dt2v9-pk10", "ball-dt3v8-pk10", "ball-dt4v7-pk10", "ball-dt5v6-pk10", "win-number-dragon-tiger-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-dt-pk10-btn")[0], "pick-act");
}

// PK10 前后三星(mid_title)
function starThreePK10() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-pk10", "pick-star3f-pk10", "pick-star3f-pk10-multi", "pick-star3f-pk10-single", "pick-star3b-pk10", "pick-star3b-pk10-multi", "pick-star3b-pk10-single" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star3fb-pk10")[0], "kind-act");
}

// PK10 前后三星 精确前三 单式
function starThreeFrontSinglePK10() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star3f-03-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-pk10-single")[0], "pick-act");
}

// PK10 前后三星 精确前三 复式
function starThreeFrontMultiPK10() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-champion-pk10", "ball-second-pk10", "ball-third-pk10", "win-number-star3f-03-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3f-pk10-multi")[0], "pick-act");
}

// PK10 前后三星 精确后三 单式
function starThreeBackSinglePK10() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star3b-03-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-pk10-single")[0], "pick-act");
}

// PK10 前后三星 精确后三 复式
function starThreeBackMultiPK10() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-eight-pk10", "ball-nine-pk10", "ball-ten-pk10", "win-number-star3b-03-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star3b-pk10-multi")[0], "pick-act");
}

// PK10 前后二星(mid_title)
function starTwoPK10() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-pk10", "pick-star2f-pk10", "pick-star2f-pk10-multi", "pick-star2f-pk10-single", "pick-star2b-pk10", "pick-star2b-pk10-multi", "pick-star2b-pk10-single" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-star2fb-pk10")[0], "kind-act");
}

// PK10 前后二星 精确前二 单式
function starTwoFrontSinglePK10() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star2f-02-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-pk10-single")[0], "pick-act");
}

// PK10 前后二星 精确前二 复式
function starTwoFrontMultiPK10() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-champion-pk10", "ball-second-pk10", "win-number-star2f-02-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2f-pk10-multi")[0], "pick-act");
}

// PK10 前后二星 精确后二 单式
function starTwoBackSinglePK10() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star2b-02-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-pk10-single")[0], "pick-act");
}

// PK10 前后二星 精确后二 复式
function starTwoBackMultiPK10() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-nine-pk10", "ball-ten-pk10", "win-number-star2b-02-pk10" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-star2b-pk10-multi")[0], "pick-act");
}
// 六合彩 特碼_特碼
function Sixtab1_1() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six1-1")[0], "pick-act");
}
// 六合彩 正碼
function Sixtab2() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-six", "pick-win-six2", "pick-win-six2-1" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-six-2")[0], "kind-act");
}
// 六合彩 正碼_正碼
function Sixtab2_1() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six2-1")[0], "pick-act");
}
// 六合彩 正碼特
function Sixtab3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-six", "pick-win-six3", "pick-win-six3-1", "pick-win-six3-2", "pick-win-six3-3", "pick-win-six3-4", "pick-win-six3-5", "pick-win-six3-6" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-six-3")[0], "kind-act");
}
// 六合彩 正碼特_正一特
function Sixtab3_1() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six3-1")[0], "pick-act");
}
// 六合彩 正碼特_正二特
function Sixtab3_2() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six3-2")[0], "pick-act");
}
// 六合彩 正碼特_正三特
function Sixtab3_3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six3-3")[0], "pick-act");
}
// 六合彩 正碼特_正四特
function Sixtab3_4() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six3-4")[0], "pick-act");
}
// 六合彩 正碼特_正五特
function Sixtab3_5() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six3-5")[0], "pick-act");
}
// 六合彩 正碼特_正六特
function Sixtab3_6() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six3-6")[0], "pick-act");
}
// 六合彩 正碼1~6
function Sixtab4() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-six", "pick-win-six4", "pick-win-six4-1", "pick-win-six4-2", "pick-win-six4-3", "pick-win-six4-4", "pick-win-six4-5", "pick-win-six4-6" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-six-4")[0], "kind-act");
}
// 六合彩 正碼1~6_正碼1
function Sixtab4_1() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_09ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six4-1")[0], "pick-act");
}
// 六合彩 正碼1~6_正碼2
function Sixtab4_2() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_09ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six4-2")[0], "pick-act");
}
// 六合彩 正碼1~6_正碼3
function Sixtab4_3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_09ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six4-3")[0], "pick-act");
}
// 六合彩 正碼1~6_正碼4
function Sixtab4_4() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_09ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six4-4")[0], "pick-act");
}
// 六合彩 正碼1~6_正碼5
function Sixtab4_5() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_09ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six4-5")[0], "pick-act");
}
// 六合彩 正碼1~6_正碼6
function Sixtab4_6() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_09ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six4-6")[0], "pick-act");
}
// 六合彩 連碼
function Sixtab5() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-six", "pick-win-six5", "pick-win-six5-1", "pick-win-six5-2", "pick-win-six5-3", "pick-win-six5-4", "pick-win-six5-5" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-six-5")[0], "kind-act");
}
// 六合彩 連碼 三全中
function Sixtab5_1() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball_nobtn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six5-1")[0], "pick-act");
}
// 六合彩 連碼 三中二
function Sixtab5_2() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball_nobtn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six5-2")[0], "pick-act");
}
// 六合彩 連碼 二全中
function Sixtab5_3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball_nobtn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six5-3")[0], "pick-act");
}
// 六合彩 連碼 二中特
function Sixtab5_4() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball_nobtn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six5-4")[0], "pick-act");
}
// 六合彩 連碼 特串
function Sixtab5_5() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball_nobtn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six5-5")[0], "pick-act");
}
// 六合彩 半波
function Sixtab6() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-six", "pick-win-six6", "pick-win-six6-1" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-six-6")[0], "kind-act");
}
// 六合彩 半波_半波
function Sixtab6_1() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_18ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six6-1")[0], "pick-act");
}
// 六合彩 特馬生效
function Sixtab7() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-six", "pick-win-six7", "pick-win-six7-1" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-six-7")[0], "kind-act");
}
// 六合彩 特馬生效_特馬生效
function Sixtab7_1() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_animalball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six7-1")[0], "pick-act");
}
// 六合彩 一肖
function Sixtab8() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-six", "pick-win-six8", "pick-win-six8-1" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-six-8")[0], "kind-act");
}
// 六合彩 一肖_一肖
function Sixtab8_1() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_animalball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six8-1")[0], "pick-act");
}
// 六合彩 生肖連
function Sixtab9() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-six", "pick-win-six9", "pick-win-six9-1", "pick-win-six9-2", "pick-win-six9-3", "pick-win-six9-4" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-six-9")[0], "kind-act");
}
// 六合彩 生肖連_二肖連中
function Sixtab9_1() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_animalball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six9-1")[0], "pick-act");
}
// 六合彩 生肖連_三肖連中
function Sixtab9_2() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_animalball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six9-2")[0], "pick-act");
}
// 六合彩 生肖連_四肖連中
function Sixtab9_3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_animalball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six9-3")[0], "pick-act");
}
// 六合彩 生肖連_五肖連中
function Sixtab9_4() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_animalball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six9-4")[0], "pick-act");
}
// 六合彩 尾數
function Sixtab10() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-six", "pick-win-six10", "pick-win-six10-1" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-six-10")[0], "kind-act");
}
// 六合彩 尾數_尾數
function Sixtab10_1() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_10ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six10-1")[0], "pick-act");
}
// 六合彩 尾數連
function Sixtab11() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-six", "pick-win-six11", "pick-win-six11-1", "pick-win-six11-2", "pick-win-six11-3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-six-11")[0], "kind-act");
}
// 六合彩 尾數連_二尾連中
function Sixtab11_1() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_10ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six11-1")[0], "pick-act");
}
// 六合彩 尾數連_三尾連中
function Sixtab11_2() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_10ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six11-2")[0], "pick-act");
}
// 六合彩 尾數連_二尾連中
function Sixtab11_3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_10ball" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six11-3")[0], "pick-act");
}
// 六合彩 不中
function Sixtab12() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-six", "pick-win-six12", "pick-win-six12-1", "pick-win-six12-2", "pick-win-six12-3", "pick-win-six12-4", "pick-win-six12-5", "pick-win-six12-6",
		"pick-win-six12-7", "pick-win-six12-8" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-six-12")[0], "kind-act");
}
// 六合彩 不中_五不中
function Sixtab12_1() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball_nobtn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six12-1")[0], "pick-act");
}
// 六合彩 不中_六不中
function Sixtab12_2() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball_nobtn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six12-2")[0], "pick-act");
}
// 六合彩 不中_七不中
function Sixtab12_3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball_nobtn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six12-3")[0], "pick-act");
}
// 六合彩 不中_八不中
function Sixtab12_4() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball_nobtn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six12-4")[0], "pick-act");
}
// 六合彩 不中_九不中
function Sixtab12_5() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball_nobtn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six12-5")[0], "pick-act");
}
// 六合彩 不中_十不中
function Sixtab12_6() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball_nobtn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six12-6")[0], "pick-act");
}
// 六合彩 不中_十ㄧ不中
function Sixtab12_7() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball_nobtn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six12-7")[0], "pick-act");
}
// 六合彩 不中_十二不中
function Sixtab12_8() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "marksix_49ball_nobtn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-win-six12-8")[0], "pick-act");
}
// 快三 和值_和職
function ballTotalQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-total-q3", "win-number-star3-direct-total-3d" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-total-q3-btn")[0], "pick-act");
}

// 快三 三同号(mid_title)
function sameThreeeQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-q3", "pick-same3-q3", "pick-same3-single-q3", "pick-same3-all-q3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-same3-q3")[0], "kind-act");
}

// 快三 三同号 单选
function sameThreeSingleQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-same3-single-q3", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-same3-single-q3")[0], "pick-act");
}

// 快三 三同号 通选
function sameThreeAllQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-same3-all-q3", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-same3-all-q3")[0], "pick-act");
}

// 快三 二同号(mid_title)
function sameTwoQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-q3", "pick-same2-q3", "pick-same2-single-q3", "pick-same2-multi-q3", "pick-same2-manual-q3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-same2-q3")[0], "kind-act");
}

// 快三 二同号 单选
function sameTwoSingleQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-same2-single-similar-q3", "ball-same2-single-dissimilar-q3", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-same2-single-q3")[0], "pick-act");
}

// 快三 二同号 复选
function sameTwoMultiQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-same2-multi-q3", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-same2-multi-q3")[0], "pick-act");
}

// 快三 二同号 单式
function sameTwoManualQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-same2-manual-q3")[0], "pick-act");
}

// 快三 三不同号(mid_title)
function differThreeQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-q3", "pick-no3-q3", "pick-no3-q3-btn", "pick-no3-q3-manual" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-no3-q3")[0], "kind-act");
}

// 快三 三不同号
function noThreeQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-no3-q3", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-no3-q3-btn")[0], "pick-act");
}

// 快三 三不同号 单式
function noThreeManualQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-no3-q3-manual")[0], "pick-act");
}

// 快三 二不同号(mid_title)
function differTwoQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-q3", "pick-no2-q3", "pick-no2-q3-btn", "pick-no2-q3-manual" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-no2-q3")[0], "kind-act");
}

// 快三 二不同号
function noTwoQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-no2-q3", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-no2-q3-btn")[0], "pick-act");
}

// 快三 二不同号 单式
function noTwoManualQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "import-area", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-no2-q3-manual")[0], "pick-act");
}

// 快三 三连号(mid_title)
function linkQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-q3", "pick-link3-q3", "pick-link3-q3-btn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-link3-q3")[0], "kind-act");
}

// 快三 三连号
function linkThreeQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-link3-q3", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-link3-q3-btn")[0], "pick-act");
}

// 快三 大小(mid_title)
function BSQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-q3", "pick-bs-q3", "pick-bs-q3-btn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-bs-q3")[0], "kind-act");
}

// 快三 大小
function bigSmallQuick3() {
	var clearName = [];
	var hiddenName = [ "" ];
	var showName = [ "ball-bs-q3", "win-number-bs-q3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-bs-q3-btn")[0], "pick-act");
}

// 快三 单双(mid_title)
function SDQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-q3", "pick-sd-q3", "pick-sd-q3-btn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-sd-q3")[0], "kind-act");
}

// 快三 单双
function singleDoubleQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-sd-q3", "win-number-sd-q3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-sd-q3-btn")[0], "pick-act");
}

// 快三 猜必出(mid_title)
function GRQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-q3", "pick-gr-q3", "pick-gr-q3-btn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-gr-q3")[0], "kind-act");
}

// 快三 猜必出
function guessRightQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-gr-q3", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-gr-q3-btn")[0], "pick-act");
}

// 快三 颜色(mid_title)
function colorQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "pick-lottery-q3", "pick-color-q3", "pick-color-red-btn", "pick-color-black-btn" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("kind-color-q3")[0], "kind-act");
}

// 快三 颜色_全红
function ballColorRedQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-color-red-q3", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-color-red-btn")[0], "pick-act");
}

// 快三 颜色_全黑
function ballColorBlackQuick3() {
	var clearName = [];
	var hiddenName = [];
	var showName = [ "ball-color-black-q3", "win-number-star3" ];
	initBall(clearName, hiddenName, showName, 0);
	addClass(document.getElementsByClassName("pick-color-black-btn")[0], "pick-act");
}

// no show
// ele=null, className=null
function addClasses(ele, className) {
	if (typeof (ele) === 'undefined' || ele == null || typeof (className) === 'undefined' || className == null || className == '' || typeof (ele.classList) === 'undefined') {
		return false;
	} else {
		if (hasClass(ele, className)) {
			return false;
		} else {
			addClass(ele, className);
			return true;
		}
	}
}

// show

// 刪除 act
// className=null
function removeAllClasses(className) {
	if (typeof (className) === 'undefined' || className == null || className == '') {
		return false;
	} else {
		var eles = document.getElementsByClassName(className);
		if (typeof (eles) === 'undefined' || eles == null || eles.length == 0) {
			delete eles;
			eles = undefined;
			return false;
		} else {
			var i = eles.length - 1;
			for (; i > -1; i--) {
				removeClass(eles[i], className);
			}
			delete eles;
			delete i;
			eles = undefined;
			i = undefined;
			return true;
		}
	}
}
var v_show_tab_fn_mapping = [
	[
		"tabSSC",
		[
			[
				"starFiveSSC",
				[ "starFiveDirectMultiSSC", "starFiveDirectSingleSSC", "starFiveGroup120SSC", "starFiveGroup60SSC", "starFiveGroup30SSC", "starFiveGroup20SSC", "starFiveGroup10SSC",
					"starFiveGroup05SSC", "starFiveOtherTotalBSSSC", "starFiveOtherTotalBSGroupSSC" ] ],
			[
				"starFourFrontSSC",
				[ "starFourFrontDirectMultiSSC", "starFourFrontDirectSingleSSC", "starFourFrontGroup24SSC", "starFourFrontGroup12SSC", "starFourFrontGroup06SSC",
					"starFourFrontGroup04SSC" ] ],
			[ "starFourBackSSC",
				[ "starFourBackDirectMultiSSC", "starFourBackDirectSingleSSC", "starFourBackGroup24SSC", "starFourBackGroup12SSC", "starFourBackGroup06SSC", "starFourBackGroup04SSC" ] ],
			[
				"starThreeFrontSSC",
				[ "starThreeFrontDirectMultiSSC", "starThreeFrontDirectSingleSSC", "starThreeFrontDirectTotalSSC", "starThreeFrontDirectCrossSSC", "starThreeFrontGroup03v2SSC",
					"starThreeFrontGroup03v2singleSSC", "starThreeFrontGroup06v2SSC", "starThreeFrontGroup06v2singleSSC", "starThreeFrontGroupMixSSC",
					"starThreeFrontGroupTotalSSC", "starThreeFrontPromiseSSC", "starThreeFrontTotalNumSSC", "starThreeFrontJaquarSSC", "starThreeFrontSmoothSSC",
					"starThreeFrontPairSSC", "starThreeFrontHalfSSC", "starThreeFrontMix6SSC" ] ],
			[
				"starThreeMiddleSSC",
				[ "starThreeMiddleDirectMultiSSC", "starThreeMiddleDirectSingleSSC", "starThreeMiddleDirectTotalSSC", "starThreeMiddleDirectCrossSSC", "starThreeMiddleGroup03v2SSC",
					"starThreeMiddleGroup03v2singleSSC", "starThreeMiddleGroup06v2SSC", "starThreeMiddleGroup06v2singleSSC", "starThreeMiddleGroupMixSSC",
					"starThreeMiddleGroupTotalSSC", "starThreeMiddlePromiseSSC", "starThreeMiddleTotalNumSSC", "starThreeMiddleJaquarSSC", "starThreeMiddleSmoothSSC",
					"starThreeMiddlePairSSC", "starThreeMiddleHalfSSC", "starThreeMiddleMix6SSC" ] ],
			[
				"starThreeBackSSC",
				[ "starThreeBackDirectMultiSSC", "starThreeBackDirectSingleSSC", "starThreeBackDirectTotalSSC", "starThreeBackDirectCrossSSC", "starThreeBackGroup03v2SSC",
					"starThreeBackGroup03v2singleSSC", "starThreeBackGroup06v2SSC", "starThreeBackGroup06v2singleSSC", "starThreeBackGroupMixSSC", "starThreeBackGroupTotalSSC",
					"starThreeBackPromiseSSC", "starThreeBackTotalNumSSC", "starThreeBackJaquarSSC", "starThreeBackSmoothSSC", "starThreeBackPairSSC", "starThreeBackHalfSSC",
					"starThreeBackMix6SSC" ] ],
			[
				"starTwoFrontSSC",
				[ "starTwoFrontDirectMultiSSC", "starTwoFrontDirectSingleSSC", "starTwoFrontDirectTotalSSC", "starTwoFrontDirectCrossSSC", "starTwoFrontGroupMultiSSC",
					"starTwoFrontGroupSingleSSC", "starTwoFrontGroupTotalSSC", "starTwoFrontGroupPromiseSSC" ] ],
			[
				"starTwoBackSSC",
				[ "starTwoBackDirectMultiSSC", "starTwoBackDirectSingleSSC", "starTwoBackDirectTotalSSC", "starTwoBackDirectCrossSSC", "starTwoBackGroupMultiSSC",
					"starTwoBackGroupSingleSSC", "starTwoBackGroupTotalSSC", "starTwoBackGroupPromiseSSC" ] ],
			[ "starSSC", [ "starOneSSC" ] ],
			[
				"star3NoAssignSSC",
				[ "star3NoAssignFront3Num01SSC", "star3NoAssignBack3Num01SSC", "star3NoAssignFront3Num02SSC", "star3NoAssignBack3Num02SSC", "star3NoAssignAny3Num01SSC",
					"star4NoAssignNum01SSC", "star4NoAssignNum02SSC", "star5NoAssignNum01SSC", "star5NoAssignNum02SSC", "star5NoAssignNum03SSC" ] ],
			[ "bigSmallSSC", [ "bigSmallFrontTwoSSC", "bigSmallBackTwoSSC", "bigSmallAnyTwoSSC", "bigSmallFrontThreeSSC", "bigSmallBackThreeSSC" ] ],
			[
				"FunSSC",
				[ "Fun05star3SSC", "Fun04star3SSC", "FunFront03star2SSC", "FunBack03star2SSC", "star3SectionNum05SSC", "star3SectionNum04SSC", "star2SectionFrontNum03SSC",
					"star2SectionBackNum03SSC", "specialOneSSC", "specialTwoSSC", "specialThreeSSC", "specialFourSSC" ] ],
			[
				"anySSC",
				[ "anyTwoDirectMultiSSC", "anyTwoDirectSingleSSC", "anyTwoDirectTotalSSC", "anyTwoDirectCrossSSC", "anyTwoGrouptMultiSSC", "anyTwoGrouptSingleSSC",
					"anyTwoGrouptTotalSSC", "anyThreeDirectMultiSSC", "anyThreeDirectSingleSSC", "anyThreeDirectTotalSSC", "anyThreeDirectCrossSSC", "anyThreeGroupTotalSSC",
					"anyThreeGroup03MultiSSC", "anyThreeGroup03ingleSSC", "anyThreeGroup06MultiSSC", "anyThreeGroup06SingleSSC", "anyThreeGroupMixSSC", "anyFourDirecttMultiSSC",
					"anyFourDirectSingleSSC", "anyFourGroup24SSC", "anyFourGroup12SSC", "anyFourGroup06SSC", "anyFourGroup04SSC" ] ],
			[
				"dttSSC",
				[ "dttTenThousand1000SSC", "dttTenThousand100SSC", "dttTenThousand10SSC", "dttTenThousand01SSC", "dttThousand100SSC", "dttThousand10SSC", "dttThousand01SSC",
					"dttHundred10SSC", "dttHundred01SSC", "dttTen01SSC" ] ] ] ],

	[
		"tabFFC",
		[
			[
				"starFiveFFC",
				[ "starFiveDirectMultiFFC", "starFiveDirectSingleFFC", "starFiveGroup120FFC", "starFiveGroup60FFC", "starFiveGroup30FFC", "starFiveGroup20FFC", "starFiveGroup10FFC",
					"starFiveGroup05FFC", "starFiveOtherTotalBSFFC", "starFiveOtherTotalBSGroupFFC" ] ],
			[
				"starFourFrontFFC",
				[ "starFourFrontDirectMultiFFC", "starFourFrontDirectSingleFFC", "starFourFrontGroup24FFC", "starFourFrontGroup12FFC", "starFourFrontGroup06FFC",
					"starFourFrontGroup04FFC" ] ],
			[ "starFourBackFFC",
				[ "starFourBackDirectMultiFFC", "starFourBackDirectSingleFFC", "starFourBackGroup24FFC", "starFourBackGroup12FFC", "starFourBackGroup06FFC", "starFourBackGroup04FFC" ] ],
			[
				"starThreeFrontFFC",
				[ "starThreeFrontDirectMultiFFC", "starThreeFrontDirectSingleFFC", "starThreeFrontDirectTotalFFC", "starThreeFrontDirectCrossFFC", "starThreeFrontGroup03v2FFC",
					"starThreeFrontGroup03v2singleFFC", "starThreeFrontGroup06v2FFC", "starThreeFrontGroup06v2singleFFC", "starThreeFrontGroupMixFFC",
					"starThreeFrontGroupTotalFFC", "starThreeFrontPromiseFFC", "starThreeFrontTotalNumFFC", "starThreeFrontJaquarFFC", "starThreeFrontSmoothFFC",
					"starThreeFrontPairFFC", "starThreeFrontHalfFFC", "starThreeFrontMix6FFC" ] ],
			[
				"starThreeMiddleFFC",
				[ "starThreeMiddleDirectMultiFFC", "starThreeMiddleDirectSingleFFC", "starThreeMiddleDirectTotalFFC", "starThreeMiddleDirectCrossFFC", "starThreeMiddleGroup03v2FFC",
					"starThreeMiddleGroup03v2singleFFC", "starThreeMiddleGroup06v2FFC", "starThreeMiddleGroup06v2singleFFC", "starThreeMiddleGroupMixFFC",
					"starThreeMiddleGroupTotalFFC", "starThreeMiddlePromiseFFC", "starThreeMiddleTotalNumFFC", "starThreeMiddleJaquarFFC", "starThreeMiddleSmoothFFC",
					"starThreeMiddlePairFFC", "starThreeMiddleHalfFFC", "starThreeMiddleMix6FFC" ] ],
			[
				"starThreeBackFFC",
				[ "starThreeBackDirectMultiFFC", "starThreeBackDirectSingleFFC", "starThreeBackDirectTotalFFC", "starThreeBackDirectCrossFFC", "starThreeBackGroup03v2FFC",
					"starThreeBackGroup03v2singleFFC", "starThreeBackGroup06v2FFC", "starThreeBackGroup06v2singleFFC", "starThreeBackGroupMixFFC", "starThreeBackGroupTotalFFC",
					"starThreeBackPromiseFFC", "starThreeBackTotalNumFFC", "starThreeBackJaquarFFC", "starThreeBackSmoothFFC", "starThreeBackPairFFC", "starThreeBackHalfFFC",
					"starThreeBackMix6FFC" ] ],
			[
				"starTwoFrontFFC",
				[ "starTwoFrontDirectMultiFFC", "starTwoFrontDirectSingleFFC", "starTwoFrontDirectTotalFFC", "starTwoFrontDirectCrossFFC", "starTwoFrontGroupMultiFFC",
					"starTwoFrontGroupSingleFFC", "starTwoFrontGroupTotalFFC", "starTwoFrontGroupPromiseFFC" ] ],
			[
				"starTwoBackFFC",
				[ "starTwoBackDirectMultiFFC", "starTwoBackDirectSingleFFC", "starTwoBackDirectTotalFFC", "starTwoBackDirectCrossFFC", "starTwoBackGroupMultiFFC",
					"starTwoBackGroupSingleFFC", "starTwoBackGroupTotalFFC", "starTwoBackGroupPromiseFFC" ] ],
			[ "starFFC", [ "starOneFFC" ] ],
			[
				"NoAssignFFC",
				[ "star3NoAssignFront3Num01FFC", "star3NoAssignBack3Num01FFC", "star3NoAssignFront3Num02FFC", "star3NoAssignBack3Num02FFC", "star3NoAssignAny3Num01FFC",
					"star4NoAssignNum01FFC", "star4NoAssignNum02FFC", "star5NoAssignNum01FFC", "star5NoAssignNum02FFC", "star5NoAssignNum03FFC" ] ],
			[ "bigSmallFFC", [ "bigSmallFrontTwoFFC", "bigSmallBackTwoFFC", "bigSmallAnyTwoFFC", "bigSmallFrontThreeFFC", "bigSmallBackThreeFFC" ] ],
			[
				"FunFFC",
				[ "Fun05star3FFC", "Fun04star3FFC", "FunFront03star2FFC", "FunBack03star2FFC", "star3SectionNum05FFC", "star3SectionNum04FFC", "star2SectionFrontNum03FFC",
					"star2SectionBackNum03FFC", "specialOneFFC", "specialTwoFFC", "specialThreeFFC", "specialFourFFC" ] ],
			[
				"anyFFC",
				[ "anyTwoDirectMultiFFC", "anyTwoDirectSingleFFC", "anyTwoDirectTotalFFC", "anyTwoDirectCrossFFC", "anyTwoGrouptMultiFFC", "anyTwoGrouptSingleFFC",
					"anyTwoGrouptTotalFFC", "anyThreeDirectMultiFFC", "anyThreeDirectSingleFFC", "anyThreeDirectTotalFFC", "anyThreeDirectCrossFFC", "anyThreeGroupTotalFFC",
					"anyThreeGroup03MultiFFC", "anyThreeGroup03ingleFFC", "anyThreeGroup06MultiFFC", "anyThreeGroup06SingleFFC", "anyThreeGroupMixFFC", "anyFourDirecttMultiFFC",
					"anyFourDirectSingleFFC", "anyFourGroup24FFC", "anyFourGroup12FFC", "anyFourGroup06FFC", "anyFourGroup04FFC" ] ],
			[
				"dttFFC",
				[ "dttTenThousand1000FFC", "dttTenThousand100FFC", "dttTenThousand10FFC", "dttTenThousand01FFC", "dttThousand100FFC", "dttThousand10FFC", "dttThousand01FFC",
					"dttHundred10FFC", "dttHundred01FFC", "dttTen01FFC" ] ] ] ],
	[
		"tab115",
		[
			[ "starThree115",
				[ "starThreeDirectFront3Multi115", "starThreeDirectFront3Single115", "starThreeGroupFront3Multi115", "starThreeGroupFront3Single115", "starThreeGroupFront3Pull115" ] ],
			[ "starTwo115", [ "starTwoDirectFront3Multi115", "starTwoDirectFront3Single115", "starTwoGroupFront3Multi115", "starTwoGroupFront3Single115", "starTwoGroupFront3Pull115" ] ],
			[ "NoAssign115", [ "starThreeFrontNoAssign115" ] ], [ "SD115", [ "singleDouble115" ] ], [ "GM115", [ "guessMiddle115" ] ], [ "star115", [ "Assign115" ] ],
			[ "anyMulti115", [ "any11Multi115", "any22Multi115", "any33Multi115", "any44Multi115", "any55Multi115", "any65Multi115", "any75Multi115", "any85Multi115" ] ],
			[ "anySingle115", [ "any11Single115", "any22Single115", "any33Single115", "any44Single115", "any55Single115", "any65Single115", "any75Single115", "any85Single115" ] ],
			[ "anyPromise115", [ "any22Promise115", "any33Promise115", "any44Promise115", "any55Promise115", "any65Promise115", "any75Promise115", "any85Promise115" ] ]

		] ],
	[
		"tabQuick3",
		[ [ "TotalQuick3", [ "ballTotalQuick3" ] ], [ "sameThreeeQuick3", [ "sameThreeSingleQuick3", "sameThreeAllQuick3" ] ],
			[ "sameTwoQuick3", [ "sameTwoSingleQuick3", "sameTwoMultiQuick3", "sameTwoManualQuick3" ] ], [ "differThreeQuick3", [ "noThreeQuick3", "noThreeManualQuick3" ] ],
			[ "differTwoQuick3", [ "noTwoQuick3", "noTwoManualQuick3" ] ], [ "linkQuick3", [ "linkThreeQuick3" ] ], [ "BSQuick3", [ "bigSmallQuick3" ] ],
			[ "SDQuick3", [ "singleDoubleQuick3" ] ], [ "GRQuick3", [ "guessRightQuick3" ] ], [ "colorQuick3", [ "ballColorRedQuick3", "ballColorBlackQuick3" ] ] ] ],
	[
		"tabPK10",
		[ [ "winPK10", [ "winTotal", "winBigSmall" ] ], [ "guessPK10", [ "guessRankAll" ] ], [ "dtPK10", [ "dragonTigerPK10" ] ],
			[ "starTwoPK10", [ "starTwoFrontMultiPK10", "starTwoFrontSinglePK10", "starTwoBackMultiPK10", "starTwoBackSinglePK10" ] ],
			[ "starThreePK10", [ "starThreeFrontMultiPK10", "starThreeFrontSinglePK10", "starThreeBackMultiPK10", "starThreeBackSinglePK10" ] ], ] ],
	[
		"tabSix",
		[ [ "winSix", [ "Sixtab1_1" ] ], [ "Sixtab2", [ "Sixtab2_1" ] ], [ "Sixtab3", [ "Sixtab3_1", "Sixtab3_2", "Sixtab3_3", "Sixtab3_4", "Sixtab3_5", "Sixtab3_6" ] ],
			[ "Sixtab4", [ "Sixtab4_1", "Sixtab4_2", "Sixtab4_3", "Sixtab4_4", "Sixtab4_5", "Sixtab4_6" ] ],
			[ "Sixtab5", [ "Sixtab5_1", "Sixtab5_2", "Sixtab5_3", "Sixtab5_4", "Sixtab5_5" ] ], [ "Sixtab6", [ "Sixtab6_1" ] ], [ "Sixtab7", [ "Sixtab7_1" ] ],
			[ "Sixtab8", [ "Sixtab8_1" ] ], [ "Sixtab9", [ "Sixtab9_1", "Sixtab9_2", "Sixtab9_3", "Sixtab9_4" ] ], [ "Sixtab10", [ "Sixtab10_1" ] ],
			[ "Sixtab11", [ "Sixtab11_1", "Sixtab11_2", "Sixtab11_3" ] ],
			[ "Sixtab12", [ "Sixtab12_1", "Sixtab12_2", "Sixtab12_3", "Sixtab12_4", "Sixtab12_5", "Sixtab12_6", "Sixtab12_7", "Sixtab12_8" ] ] ] ],
	[
		"tab3D",
		[
			[
				"starThree3D",
				[ "starThreeDirectMulti3D", "starThreeDirectSingle3D", "starThreeDirectTotal3D", "starThreeGroup03v23D", "starThreeGroup03v2single3D", "starThreeGroup06v23D",
					"starThreeGroup06v2single3D", "starThreeGroupMix3D", "starThreeGroupTotal3D" ] ],
			[ "starTwoFront3D", [ "starTwoFrontDirectMulti3D", "starTwoFrontDirectSingle3D", "starTwoFrontGroupMulti3D", "starTwoFrontGroupSingle3D" ] ],
			[ "starTwoBack3D", [ "starTwoBackDirectMulti3D", "starTwoBackDirectSingle3D", "starTwoBackGroupMulti3D", "starTwoBackGroupSingle3D" ] ], [ "star3D", [ "starOne3D" ] ],
			[ "noAssign3d", [ "noAssign3dFb3n1", "noAssign3dFb3n2" ] ], [ "dtt3D", [ "dttHundred103D", "dttHundred013D", "dttTen01S3D" ] ] ] ],
	[
		"tab35",
		[
			[
				"starFive35",
				[ "starFiveDirectMulti35", "starFiveDirectSingle35", "starFiveGroup12035", "starFiveGroup6035", "starFiveGroup3035", "starFiveGroup2035", "starFiveGroup1035",
					"starFiveGroup0535", "starFiveOtherTotalBS35", "starFiveOtherTotalBSGroup35" ] ],
			[ "starFourFront35",
				[ "starFourFrontDirectMulti35", "starFourFrontDirectSingle35", "starFourFrontGroup2435", "starFourFrontGroup1235", "starFourFrontGroup0635", "starFourFrontGroup0435" ] ],
			[ "starFourBack35",
				[ "starFourBackDirectMulti35", "starFourBackDirectSingle35", "starFourBackGroup2435", "starFourBackGroup1235", "starFourBackGroup0635", "starFourBackGroup0435" ] ],
			[
				"starThreeFront35",
				[ "starThreeFrontDirectMulti35", "starThreeFrontDirectSingle35", "starThreeFrontDirectTotal35", "starThreeFrontDirectCross35", "starThreeFrontGroup03v235",
					"starThreeFrontGroup03v2single35", "starThreeFrontGroup06v235", "starThreeFrontGroup06v2single35", "starThreeFrontGroupMix35", "starThreeFrontGroupTotal35",
					"starThreeFrontPromise35", "starThreeFrontTotalNum35", "starThreeFrontJaquar35", "starThreeFrontSmooth35", "starThreeFrontPair35", "starThreeFrontHalf35",
					"starThreeFrontMix635" ] ],
			[
				"starThreeMiddle35",
				[ "starThreeMiddleDirectMulti35", "starThreeMiddleDirectSingle35", "starThreeMiddleDirectTotal35", "starThreeMiddleDirectCross35", "starThreeMiddleGroup03v235",
					"starThreeMiddleGroup03v2single35", "starThreeMiddleGroup06v235", "starThreeMiddleGroup06v2single35", "starThreeMiddleGroupMix35",
					"starThreeMiddleGroupTotal35", "starThreeMiddlePromise35", "starThreeMiddleTotalNum35", "starThreeMiddleJaquar35", "starThreeMiddleSmooth35",
					"starThreeMiddlePair35", "starThreeMiddleHalf35", "starThreeMiddleMix635" ] ],
			[
				"starThreeBack35",
				[ "starThreeBackDirectMulti35", "starThreeBackDirectSingle35", "starThreeBackDirectTotal35", "starThreeBackDirectCross35", "starThreeBackGroup03v235",
					"starThreeBackGroup03v2single35", "starThreeBackGroup06v235", "starThreeBackGroup06v2single35", "starThreeBackGroupMix35", "starThreeBackGroupTotal35",
					"starThreeBackPromise35", "starThreeBackTotalNum35", "starThreeBackJaquar35", "starThreeBackSmooth35", "starThreeBackPair35", "starThreeBackHalf35",
					"starThreeBackMix635" ] ],
			[
				"starTwoFront35",
				[ "starTwoFrontDirectMulti35", "starTwoFrontDirectSingle35", "starTwoFrontDirectTotal35", "starTwoFrontDirectCross35", "starTwoFrontGroupMulti35",
					"starTwoFrontGroupSingle35", "starTwoFrontGroupTotal35", "starTwoFrontGroupPromise35" ] ],
			[
				"starTwoBack35",
				[ "starTwoBackDirectMulti35", "starTwoBackDirectSingle35", "starTwoBackDirectTotal35", "starTwoBackDirectCross35", "starTwoBackGroupMulti35",
					"starTwoBackGroupSingle35", "starTwoBackGroupTotal35", "starTwoBackGroupPromise35" ] ],
			[ "star35", [ "starOne35" ] ],
			[
				"NoAssign35",
				[ "star3NoAssignFront3Num0135", "star3NoAssignBack3Num0135", "star3NoAssignFront3Num0235", "star3NoAssignBack3Num0235", "star3NoAssignAny3Num0135",
					"star4NoAssignNum0135", "star4NoAssignNum0235", "star5NoAssignNum0135", "star5NoAssignNum0235", "star5NoAssignNum0335" ] ],
			[ "bigSmall35", [ "bigSmallFrontTwo35", "bigSmallBackTwo35", "bigSmallAnyTwo35", "bigSmallFrontThree35", "bigSmallBackThree35" ] ],
			[
				"Fun35",
				[ "Fun05star335", "Fun04star335", "FunFront03star235", "FunBack03star235", "star3SectionNum0535", "star3SectionNum0435", "star2SectionFrontNum0335",
					"star2SectionBackNum0335", "specialOne35", "specialTwo35", "specialThree35", "specialFour35" ] ],
			[
				"any35",
				[ "anyTwoDirectMulti35", "anyTwoDirectSingle35", "anyTwoDirectTotal35", "anyTwoDirectCross35", "anyTwoGrouptMulti35", "anyTwoGrouptSingle35", "anyTwoGrouptTotal35",
					"anyThreeDirectMulti35", "anyThreeDirectSingle35", "anyThreeDirectTotal35", "anyThreeDirectCross35", "anyThreeGroupTotal35", "anyThreeGroup03Multi35",
					"anyThreeGroup03ingle35", "anyThreeGroup06Multi35", "anyThreeGroup06Single35", "anyThreeGroupMix35", "anyFourDirecttMulti35", "anyFourDirectSingle35",
					"anyFourGroup2435", "anyFourGroup1235", "anyFourGroup0635", "anyFourGroup0435" ] ],
			[
				"dtt35",
				[ "dttTenThousand100035", "dttTenThousand10035", "dttTenThousand1035", "dttTenThousand0135", "dttThousand10035", "dttThousand1035", "dttThousand0135",
					"dttHundred1035", "dttHundred0135", "dttTen0135" ] ] ] ] ];

function checkMenuTurnOff() {
	var allLotteryTitle = isJSON2(Strings.decode(getEle("allLotteryTitle").value));
	var tmpJ = isJSON2(Strings.decode(getEle('lotterys').value));
	if ((allLotteryTitle && tmpJ) ? (typeof allLotteryTitle["AllLotteryTitle"] !== "undefined" && typeof tmpJ['AllLottery'] !== "undefined") : false) {
		var lotteryTitleObj = allLotteryTitle["AllLotteryTitle"];
		var tmpJ = tmpJ['AllLottery'];
		var k1 = Object.keys(lotteryTitleObj).sort();
		for (var i1 = 0; i1 < k1.length; i1++) {
			var key = k1[i1];
			var k2 = Object.keys(lotteryTitleObj[key]).sort();
			for (var i2 = 0; i2 < k2.length; i2++) {
				var key2 = k2[i2];
				if (typeof lotteryTitleObj[key][key2] === "object") {
					var obj = lotteryTitleObj[key][key2];
					if (typeof tmpJ[obj.type] !== "undefined" ? (typeof tmpJ[obj.type][obj.local_id] !== "undefined" ? typeof tmpJ[obj.type][obj.local_id]["0-switch1"] !== "undefined"
							: false)
							: false) {
						if (tmpJ[obj.type][obj.local_id]["0-switch1"] != "1") {
							addClass(document.getElementsByClassName("menu-lottery")[i1].getElementsByTagName("li")[i2], "turn-off");
							var tmpH = (parseInt((document.getElementsByClassName("menu-lottery")[i1].style.height + "").replace("px")) - 32) + 'px';
							document.getElementsByClassName("menu-lottery")[i1].style.height = tmpH;
							delete tmpH;
							tmpH = undefined;
						} else {
							removeClass(document.getElementsByClassName("menu-lottery")[i1].getElementsByTagName("li")[i2], "turn-off");
						}

					}
				}
			}
		}
	}
}

function setLev1(ele) {
	enableLoadingPage();
	f_kj_timer(0);
	addClass(document.getElementsByClassName("right-side")[0], "invisible");
	removeAllClasses('submenu-active');
	addClass(ele, 'submenu-active');
	var tmpA = document.getElementsByClassName("accordion-menu")[0].getElementsByTagName("a");
	for (var i = 0; i < tmpA.length; i++) {
		tmpA[i].disabled = false;
	}
	// ele.disabled = true;
	var type = 1;
	var clearName = [ "kind-act", "pick-act", "ball-act" ];
	for (var i = 0; i < clearName.length; i++) {
		removeAllClasses(clearName[i]);
	}
	// tab-lottery
	// pick-lottery
	// pick-tab
	// ball-tab
	tmpA = document.getElementsByClassName("tab-lottery");
	for (var i = 0; i < tmpA.length; i++) {
		addClass(tmpA[i], "invisible");
	}
	tmpA = document.getElementsByClassName("pick-lottery");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		addClass(tmpA[i1], "invisible");
		delete tmpB;
		tmpB = undefined;

	}
	tmpA = document.getElementsByClassName("pick-tab");
	for (var i = 0; i < tmpA.length; i++) {
		addClass(tmpA[i], "invisible");
		if (tmpA[i] && tmpA[i].getElementsByTagName("a")) {
			var tmpB = tmpA[i].getElementsByTagName("a");
			for (var i1 = 0; i1 < tmpB.length; i1++) {
				addClass(tmpB[i1], "invisible");
			}
			delete tmpB;
			tmpB = undefined;
		}
	}
	tmpA = document.getElementsByClassName("ball-tab");
	for (var i = 0; i < tmpA.length; i++) {
		addClass(tmpA[i], "invisible");
	}
	tmpA = document.getElementsByClassName("top-area")[0].getElementsByClassName("lottery-number")[0].getElementsByTagName("div");
	for (var i = 0; i < tmpA.length; i++) {
		addClass(tmpA[i], "invisible");
	}
	addClass(document.getElementsByClassName("import-checkbox-area")[0], "invisible");
	addClass(document.getElementsByClassName("ball-checkbox-area")[0], "invisible");
	tmpA = document.getElementsByClassName("accordion-menu")[0].children;
	if (ele.parentElement.parentElement.tagName == "DIV") {
		type = 2;
	}
	var idx = 0;
	document.getElementsByName("l1")[0].value = "";
	document.getElementsByName("l2")[0].value = "";
	document.getElementsByName("l3")[0].value = "";
	document.getElementsByName("l4")[0].value = "";
	for (var i = 0; i < tmpA.length; i++) {
		if (type == 1) {
			if (tmpA[i] == ele.parentElement.parentElement.parentElement) {
				idx = i;
				document.getElementsByName("l1")[0].value = idx;
				break;
			}
		} else if (type == 2) {
			if (tmpA[i] == ele.parentElement.parentElement.parentElement.parentElement) {
				idx = i;
				document.getElementsByName("l1")[0].value = idx;
				break;
			}
		}
	}
	tmpA = document.getElementsByClassName("accordion-menu")[0].children[idx].getElementsByTagName("li");
	for (var i = 0; i < tmpA.length; i++) {
		if (tmpA[i].getElementsByTagName("a")[0] == ele) {
			if (type == 2) {
				idx = idx + i;
				document.getElementsByName("l1")[0].value = idx;
				document.getElementsByName("l2")[0].value = 0;
			} else {
				document.getElementsByName("l2")[0].value = i;
			}
			break;
		}
	}

	removeAllClasses("kind-act");
	removeAllClasses("pick-act");
	removeAllClasses("ball-act");
	// removeAllClasses("turn-off");
	var hidden1 = [ "three-balls", "three-balls-ani", "five-balls", "five-balls-ani", "ten-balls", "ten-balls-ani", "three-dices" ];
	hidden1 = [ "three-balls", "five-balls", "ten-balls", "three-dices" ];
	for (var i = 0; i < hidden1.length; i++) {
		addClass(document.getElementsByClassName(hidden1[i])[0], "invisible");
	}
	delete hidden1;
	hidden1 = undefined;

	var lotteryFnName;
	var tmpK;
	var toRun = false;
	var allLotteryTitle = isJSON2(Strings.decode(getEle("allLotteryTitle").value));
	var tmpJ = isJSON2(Strings.decode(getEle('lotterys').value));
	if ((allLotteryTitle && tmpJ) ? (typeof allLotteryTitle["AllLotteryTitle"] !== "undefined" && typeof tmpJ['AllLottery'] !== "undefined") : false) {
		var lotteryTitleObj = allLotteryTitle["AllLotteryTitle"];
		tmpJ = tmpJ['AllLottery'];
		var k1 = Object.keys(lotteryTitleObj).sort();
		var key = k1[document.getElementsByName("l1")[0].value];
		var k2 = Object.keys(lotteryTitleObj[key]).sort();
		var key2 = k2[document.getElementsByName("l2")[0].value];
		if (typeof lotteryTitleObj[key][key2] === "object") {

			var obj = lotteryTitleObj[key][key2];
			if (typeof tmpJ[obj.type] !== "undefined" ? (typeof tmpJ[obj.type][obj.local_id] !== "undefined" ? typeof tmpJ[obj.type][obj.local_id]["0-switch1"] !== "undefined" : false)
					: false) {
				getEle("mainId").value = obj.type;
				getEle("localId").value = obj.local_id;
				lotteryFnName = typeof obj.lottery_fn_name != "undefined" ? obj.lottery_fn_name : "";

				tmpJ = tmpJ[obj.type][obj.local_id];
				tmpK = Object.keys(tmpJ).sort();
				if (tmpJ["0-switch1"] == "1") {
					toRun = true;
					checkTitle();

				} else {
					disableLoadingPage();
					return false;
				}
			}
		}
	}

	// var tmpJ = JSON.parse(Strings.decode(getEle('lotterys').value));
	// tmpJ = tmpJ['AllLottery'];
	// var tmpK = Object.keys(tmpJ).sort();
	// tmpJ =
	// tmpJ[tmpK[parseInt(document.getElementsByName("l1")[0].value)]];
	// tmpK = Object.keys(tmpJ).sort();
	// tmpJ =
	// tmpJ[tmpK[parseInt(document.getElementsByName("l2")[0].value)]];
	// tmpK = Object.keys(tmpJ).sort();
	// var toRun = false;
	// if(tmpJ["0-switch1"] ? tmpJ["0-switch1"]=="1" : false){
	// toRun = true;
	// checkTitle();
	// } else {
	// disableLoadingPage();
	// return false;
	// }

	var ls1 = -1;
	for (var k = 0; k < v_show_tab_fn_mapping.length; k++) {
		ls1 = k;
		if (v_show_tab_fn_mapping[k][0] == lotteryFnName) {
			break;
		}
	}
	if (ls1 < 0) {
		disableLoadingPage();
		return false;
	}

	document.getElementsByName("ls1")[0].value = ls1;

	// console_Log("l1:"+document.getElementsByName("l1")[0].value);
	// console_Log("l2:"+document.getElementsByName("l2")[0].value);
	// console_Log("l3:"+document.getElementsByName("l3")[0].value);
	// console_Log("l4:"+document.getElementsByName("l4")[0].value);
	// console_Log("idx:"+idx);
	var runTxt = v_show_tab_fn_mapping[ls1][0] + "()";
	if (toRun == true) {
		eval(runTxt);

		addClass(document.getElementsByClassName("right-side")[0], "invisible");
		document.getElementsByName("mainOrder")[0].value = "";
		document.getElementsByName("mainOrderList")[0].value = "";
		groupMainOrderObj = {};
		groupMainOrderListObj = {};
		document.getElementsByName("showedNo")[0].value = "";
		document.getElementsByName("nowNo")[0].value = "";
		document.getElementsByName("nowS")[0].value = "";
		document.getElementsByName("nowE")[0].value = "";
		document.getElementsByName("nowDate")[0].value = "";
		document.getElementsByName("oldNo")[0].value = "";
		document.getElementsByName("newNo")[0].value = "";
		document.getElementsByName("oldData")[0].value = "";
		document.getElementsByName("newData")[0].value = "";
		document.getElementsByClassName("lottery-number")[0].getElementsByClassName("winner")[0].getElementsByTagName("i")[0].innerHTML = "";
		document.getElementsByClassName("top-area")[0].getElementsByClassName("time-num")[0].innerHTML = "00<span>:</span>00<span>:</span>00";

		clearBallToInit();
		// ////disableLoadingPage();
		setTimeout('removeClass(document.getElementsByClassName("right-side")[0], "invisible");', 3000);

		removeClass(document.getElementsByClassName("pick-area")[0], "invisible");
		addClass(document.getElementsByClassName("rule")[0], "invisible");
		addClass(document.getElementsByClassName("note")[0], "invisible");
		addClass(document.getElementsByClassName("number-area")[0], "invisible");
		addClass(document.getElementsByClassName("bottom-area")[0], "invisible");
		addClass(document.getElementsByClassName("list-area")[0], "invisible");
		addClass(document.getElementsByClassName("right")[0], "invisible");

		var todo = true;
		var tmpA = document.getElementsByClassName("tab-lottery");
		for (var i1 = 0; i1 < tmpA.length; i1++) {
			if (todo == false) {
				break;
			}
			if (!hasClass(tmpA[i1], "invisible") ? !hasClass(tmpA[i1], "turn-off") : false) {
				var tmpB = tmpA[i1].getElementsByTagName("li");
				for (var i2 = 0; i2 < tmpB.length; i2++) {
					if (tmpJ[tmpK[i2 + 1]] ? tmpJ[tmpK[i2 + 1]]["0-switch2"] ? tmpJ[tmpK[i2 + 1]]["0-switch2"] == "1" : false : false) {
						removeClass(tmpB[i2], "turn-off");
					} else {
						addClass(tmpB[i2], "turn-off");
					}
				}
				for (var i2 = 0; i2 < tmpB.length; i2++) {
					if (todo == false) {
						break;
					}
					if (!hasClass(tmpB[i2], "invisible") ? !hasClass(tmpB[i2], "turn-off") : false) {
						tmpB[i2].getElementsByTagName("a")[0].onclick();
						document.getElementsByName("l3")[0].value = i2;
						console_Log("l3:" + document.getElementsByName("l3")[0].value);
						break;
					}
				}
			}
		}

	} else {
		console_Log("!!not run!!");
	}
	delete runTxt;
	runTxt = undefined;
	delete toRun;
	toRun = undefined;

	ele.disabled = true;
	setTimeout('removeClass(document.getElementsByClassName("right-side")[0],"invisible");', 3000);
	setTimeout("disableLoadingPage();", 1000);
}
function setLev2(ele) {
	enableLoadingPage();
	f_kj_timer(0);
	var clearName = [ "pick-act", "ball-act" ];
	for (var i = 0; i < clearName.length; i++) {
		removeAllClasses(clearName[i]);
	}
	// pick-lottery
	// pick-tab
	// ball-tab
	/*
	 * var tmpA = document.getElementsByClassName("pick-lottery"); for(var
	 * i1=0; i1<tmpA.length; i1++){ addClass(tmpA[i1], "invisible"); delete
	 * tmpB; tmpB = undefined; }
	 */
	var tmpA = document.getElementsByClassName("pick-tab");
	for (var i = 0; i < tmpA.length; i++) {
		addClass(tmpA[i], "invisible");
		if (tmpA[i] && tmpA[i].getElementsByTagName("a")) {
			var tmpB = tmpA[i].getElementsByTagName("a");
			for (var i1 = 0; i1 < tmpB.length; i1++) {
				addClass(tmpB[i1], "invisible");
			}
			delete tmpB;
			tmpB = undefined;
		}
	}
	tmpA = document.getElementsByClassName("ball-tab");
	for (var i = 0; i < tmpA.length; i++) {
		addClass(tmpA[i], "invisible");
	}
	addClass(document.getElementsByClassName("import-checkbox-area")[0], "invisible");
	addClass(document.getElementsByClassName("ball-checkbox-area")[0], "invisible");
	var idx = 0;
	tmpA = document.getElementsByClassName("tab-lottery");
	document.getElementsByName("l3")[0].value = "";
	document.getElementsByName("l4")[0].value = "";
	var todo = true;
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		if (todo == false) {
			break;
		}
		if (!hasClass(tmpA[i1], "invisible")) {
			var tmpB = tmpA[i1].getElementsByTagName("a");
			for (var i2 = 0; i2 < tmpB.length; i2++) {
				tmpB[i2].disabled = false;
			}
			// //ele.disabled = true;
			tmpB = tmpA[i1].getElementsByTagName("li");
			for (var i2 = 0; i2 < tmpB.length; i2++) {
				if (todo == false) {
					break;
				}
				if (!hasClass(tmpB[i2], "invisible")) {
					if (tmpB[i2].getElementsByTagName("a")[0] == ele) {
						document.getElementsByName("l3")[0].value = idx;
						todo = false;
						break;
					}
					idx++;
				}
			}
		}
	}
	removeAllClasses("kind-act");
	removeAllClasses("pick-act");
	removeAllClasses("ball-act");

	var tmpJ = JSON.parse(Strings.decode(getEle('lotterys').value));
	tmpJ = tmpJ['AllLottery'];
	var tmpK = Object.keys(tmpJ).sort();
	tmpJ = tmpJ[document.getElementsByName("mainId")[0].value];
	tmpK = Object.keys(tmpJ).sort();
	tmpJ = tmpJ[document.getElementsByName("localId")[0].value];
	// console_Log(tmpJ);
	tmpK = Object.keys(tmpJ).sort();
	var toRun = false;
	if (tmpJ["0-switch1"] ? tmpJ["0-switch1"] == "1" : false) {
		toRun = true;
		tmpJ = tmpJ[tmpK[(parseInt(document.getElementsByName("l3")[0].value) + 1)]];
		// console_Log(tmpJ);
		tmpK = Object.keys(tmpJ).sort();
		if (tmpJ["0-switch2"] ? tmpJ["0-switch2"] == "1" : false) {
			toRun = true;
		} else {
			toRun = false;
			disableLoadingPage();
			return false;
		}
	} else {
		disableLoadingPage();
		return false;
	}

	// console_Log("l1:"+document.getElementsByName("l1")[0].value);
	// console_Log("l2:"+document.getElementsByName("l2")[0].value);
	// console_Log("l3:"+document.getElementsByName("l3")[0].value);
	// console_Log("l4:"+document.getElementsByName("l4")[0].value);
	console_Log("idx:" + idx);

	// var l1 = parseInt(document.getElementsByName("l1")[0].value);
	var ls1 = document.getElementsByName("ls1")[0].value;

	var runTxt = v_show_tab_fn_mapping[ls1][1][idx][0] + "()";
	console_Log(runTxt);
	eval(runTxt);
	delete runTxt;
	runTxt = undefined;

	if (toRun == true) {
		var todo = true;

		document.getElementsByName("mainOrder")[0].value = "";
		groupMainOrderObj = {};
		clearBallToInit();

		removeClass(document.getElementsByClassName("pick-area")[0], "invisible");
		addClass(document.getElementsByClassName("rule")[0], "invisible");
		addClass(document.getElementsByClassName("note")[0], "invisible");
		addClass(document.getElementsByClassName("number-area")[0], "invisible");
		addClass(document.getElementsByClassName("bottom-area")[0], "invisible");
		addClass(document.getElementsByClassName("list-area")[0], "invisible");
		addClass(document.getElementsByClassName("right")[0], "invisible");
		// //ele.disabled = true;

		var tmpA = document.getElementsByClassName("pick-lottery");
		for (var i1 = 0; i1 < tmpA.length; i1++) {
			if (todo == false) {
				break;
			}

			if (!hasClass(tmpA[i1], "invisible") ? !hasClass(tmpA[i1], "turn-off") : false) {
				var tmpB = tmpA[i1].getElementsByClassName("pick-tab");
				var i4 = 0;
				for (var i2 = 0; i2 < tmpB.length; i2++) {
					if (!hasClass(tmpB[i2], "invisible")) {
						var tmpC = tmpB[i2].getElementsByTagName("a");
						for (var i3 = 0; i3 < tmpC.length; i3++) {
							// console_Log(tmpJ);
							i4++;
							removeClass(tmpC[i3], "turn-off");
							if (tmpJ[tmpK[i4]] ? tmpJ[tmpK[i4]]["0-switch3"] ? tmpJ[tmpK[i4]]["0-switch3"] != "1" : false : false) {
								addClass(tmpC[i3], "turn-off");
							}
						}
						// console_Log(tmpB[i2].innerHTML);
						tmpC = tmpB[i2].getElementsByTagName("a");
						var tmpDD = 0;
						for (var i3 = 0; i3 < tmpC.length; i3++) {
							if (!hasClass(tmpC[i3], "invisible") ? !hasClass(tmpC[i3], "turn-off") : false) {
								tmpDD++;
							}
						}
						// console_Log("tmpDD:"+tmpDD);
						removeClass(tmpB[i2], "turn-off");
						if (tmpDD == 0) {
							addClass(tmpB[i2], "turn-off");
						}
						
						delete tmpDD;
						tmpDD = undefined;
					}
				}
				var i4 = 0;
				tmpB = tmpA[i1].getElementsByClassName("pick-tab");
				for (var i2 = 0; i2 < tmpB.length; i2++) {
					if (todo == false) {
						break;
					}
					var tmpC = tmpB[i2].getElementsByTagName("a");
					if (!hasClass(tmpB[i2], "invisible")) {

						for (var i3 = 0; i3 < tmpC.length; i3++) {
							if (todo == false) {
								break;
							}
							if (!hasClass(tmpC[i3], "invisible") ? !hasClass(tmpC[i3], "turn-off") : false) {
								tmpC[i3].onclick();
								document.getElementsByName("l4")[0].value = i4;
								// console_Log("\n\n
								// #####
								// l3:"+document.getElementsByName("l3")[0].value);
								// console_Log("\n\n
								// #####
								// l4:"+document.getElementsByName("l4")[0].value);
								// console_Log("l1:"+document.getElementsByName("l1")[0].value);
								// console_Log("l2:"+document.getElementsByName("l2")[0].value);
								// console_Log("l3:"+document.getElementsByName("l3")[0].value);
								// console_Log("l4:"+document.getElementsByName("l4")[0].value);
								todo = false;
								break;
							}
							i4++;
						}
					}
				}
			}
		}
	}
	// getSetting();
	setTimeout("disableLoadingPage();", 100);
}
function setLev3(ele) {
	enableLoadingPage();
	f_kj_timer(0);
	// console_Log("\n
	// setLev3::::::"+document.getElementsByClassName("right")[0].className);

	var clearName = [ "ball-act" ];
	for (var i = 0; i < clearName.length; i++) {
		removeAllClasses(clearName[i]);
	}
	// ball-tab
	var tmpA = document.getElementsByClassName("ball-tab");
	for (var i = 0; i < tmpA.length; i++) {
		addClass(tmpA[i], "invisible");
	}
	addClass(document.getElementsByClassName("import-checkbox-area")[0], "invisible");
	addClass(document.getElementsByClassName("ball-checkbox-area")[0], "invisible");
	tmpA = document.getElementsByClassName("pick-lottery");
	document.getElementsByName("l4")[0].value = "";
	var idx = 0;
	var todo = true;
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		if (todo == false) {
			break;
		}
		// console_Log(tmpA[i1]);
		if (!hasClass(tmpA[i1], "invisible")) {
			// console_Log(tmpA[i1]);
			var tmpB = tmpA[i1].getElementsByClassName("pick-tab");
			for (var i2 = 0; i2 < tmpB.length; i2++) {
				if (todo == false) {
					break;
				}
				if (tmpB[i2] && !hasClass(tmpB[i2], "invisible") /*&& !hasClass(tmpB[i2], "turn-off") */) {
					var tmpC = tmpB[i2].getElementsByTagName("a");
					if(hasClass(tmpB[i2], "turn-off")){
						idx += tmpC.length;
					}
					else{
						for (var i3 = 0; i3 < tmpC.length; i3++) {
							tmpC[i3].disabled = false;
						}
						// //ele.disabled = true;
						for (var i3 = 0; i3 < tmpC.length; i3++) {
							if (todo == false) {
								break;
							}
							// console_Log(tmpC[i3]);
							// console_Log(ele);
							if (tmpC[i3] == ele) {
								// console_Log("setLev3
								// i3####"+i3);
								// console_Log("setLev3
								// idx####"+idx);
								document.getElementsByName("l4")[0].value = idx;
								todo = false;
								break;
							}
							idx++;
						}
					}
					
				}
			}
			break;
		} else {
			// console_Log(tmpA[i1].className);
		}
	}
	removeAllClasses("pick-act");
	removeAllClasses("ball-act");
	getSetting();

	// console_Log("l1:"+document.getElementsByName("l1")[0].value);
	// console_Log("l2:"+document.getElementsByName("l2")[0].value);
	// console_Log("l3:"+document.getElementsByName("l3")[0].value);
	// console_Log("l4:"+document.getElementsByName("l4")[0].value);
	console_Log("idx:" + idx);

	// var l1 = parseInt(document.getElementsByName("l1")[0].value);
	var l3 = parseInt(document.getElementsByName("l3")[0].value);
	var ls1 = document.getElementsByName("ls1")[0].value;
	var runTxt = v_show_tab_fn_mapping[ls1][1][l3][1][idx] + "()";
	console_Log(runTxt);

	if (document.getElementsByName("playedText")[0].value != "") {
		// playedText
		document.getElementsByClassName("rule")[0].innerHTML = document.getElementsByName("playedText")[0].value;
		document.getElementsByClassName("tooltiptext")[0].innerHTML = document.getElementsByName("lotteryRule")[0].value;
		document.getElementsByClassName("tooltiptext")[1].innerHTML = document.getElementsByName("lotteryExample")[0].value;
	}
	setTimeout('removeClass(document.getElementsByClassName("right-side")[0], "invisible");', 1000);
	document.getElementsByName("mainOrder")[0].value = "";
	groupMainOrderObj = {};

	eval(runTxt + ";clearBallToInit();");

	// setTimeout("clearBallToInit();", 100);

	showBallRatioBaseline();
	delete runTxt;
	runTxt = undefined;

	removeClass(document.getElementsByClassName("pick-area")[0], "invisible");
	removeClass(document.getElementsByClassName("rule")[0], "invisible");
	removeClass(document.getElementsByClassName("note")[0], "invisible");
	removeClass(document.getElementsByClassName("number-area")[0], "invisible");
	removeClass(document.getElementsByClassName("bottom-area")[0], "invisible");
	removeClass(document.getElementsByClassName("list-area")[0], "invisible");
	removeClass(document.getElementsByClassName("right")[0], "invisible");
	// ele.disabled = true;
	disableLoadingPage();
}

function checkTitle() {
	if (document.getElementsByClassName("kind-act").length == 1) {
		var tmpStr = document.getElementsByClassName("kind-act")[0].getElementsByTagName("a")[0].title;
		var tmpA = document.getElementsByClassName("pick-lottery");
		for (var i1 = 0; i1 < tmpA.length; i1++) {
			if (tmpA[i1] && !hasClass(tmpA[i1], "invisible")) { // &&
				// !hasClass(tmpA[i1],
				// "turn-off")){
				var tmpB = tmpA[i1].getElementsByClassName("pick-tab");
				for (var i2 = 0; i2 < tmpB.length; i2++) {
					if (tmpB[i2] && !hasClass(tmpB[i2], "invisible")) {
						var tmpC = tmpB[i2].getElementsByTagName("a");
						for (var i3 = 0; i3 < tmpC.length; i3++) {
							tmpC[i3].title = tmpStr + "-" + tmpC[i3].innerHTML;
						}
						tmpC = undefined;
					}
				}
				tmpB = undefined;
				break;
			}
		}
		tmpA = undefined;
	}
	// icon-lottery
	var v1 = parseInt(document.getElementsByName("l1")[0].value) + 1;
	var v2 = parseInt(document.getElementsByName("l2")[0].value) + 1;
	if (v1 < 10) {
		v1 = "0" + v1;
	}
	if (v2 < 10) {
		v2 = "0" + v2;
	}
	// <img src="img/lottery_logo/logo_01_01.png">
	document.getElementsByClassName("icon-lottery")[0].getElementsByTagName("img")[0].src = "lottery/img/lottery_logo/logo_" + v1 + "_" + v2 + ".png";
	lotteryBetChangeOrderBtn();
}

// clearName={}, hiddenName={}, showName={}, type=0
function initBall(clearName, hiddenName, showName, type) {
	enableLoadingPage();
	// removeAllClasses("kind-act");
	// removeAllClasses("pick-act");
	// removeAllClasses("ball-act");
	removeAllInputChecked();
	var tmpA = document.getElementsByClassName("win-history");
	for (var i = 0; i < tmpA.length; i++) {
		addClass(tmpA[i], "invisible");
	}
	delete tmpA;
	tmpA = undefined;

	// var hidden1 = ["three-balls","five-balls","ten-balls","three-dices"];
	var hidden1 = [];
	// var hidden2 =
	// ["pick-star5-direct-ssc","pick-star5-group-ssc","pick-star5-other-ssc","kind-star5-ssc","kind-star4f-ssc","kind-star4b-ssc","kind-star3f-ssc","kind-star3m-ssc","kind-star3b-ssc","kind-star2f-ssc","kind-star2b-ssc","kind-star1-ssc","kind-noassign-ssc","kind-bs-ssc","kind-funny-ssc","kind-any-ssc","kind-dtt-ssc","pick-star3-noassign-any3n1-ssc","pick-star3-noassign-ssc"];
	var hidden2 = [];
	var hidden3 = [ "pick-star5-direct-ffc", "pick-star5-group-ffc", "pick-star5-other-ffc", "kind-star5-ffc", "kind-star4f-ffc", "kind-star4b-ffc", "kind-star3f-ffc", "kind-star3m-ffc",
		"kind-star3b-ffc", "kind-star2f-ffc", "kind-star2b-ffc", "kind-star1-ffc", "kind-noassign-ffc", "kind-bs-ffc", "kind-funny-ffc", "kind-any-ffc", "kind-dtt-ffc" ];
	var hidden4 = [ "pick-star5-direct-35", "pick-star5-group-35", "pick-star5-other-35", "kind-star5-35", "kind-star4f-35", "kind-star4b-35", "kind-star3f-35", "kind-star3m-35",
		"kind-star3b-35", "kind-star2f-35", "kind-star2b-35", "kind-star1-35", "kind-noassign-35", "kind-bs-35", "kind-funny-35", "kind-any-35", "kind-dtt-35" ];
	var hidden5 = [ "pick-star3-direct-115", "pick-star3-group-115", "pick-star2-direct-115", "pick-star2-group-115", "pick-star3f-noassign-115", "pick-sd-115", "pick-guess-middle-115",
		"pick-assign-115", "pick-tab pick-any-multi-115", "pick-any-single-115", "pick-any-promise-115", "ball-digit-01", "ball-digit-02", "ball-digit-03", "kind-star3-115", "kind-star2-115",
		"kind-noassign-115", "kind-sd-115", "kind-qm-115", "kind-assign-115", "kind-any-multi-115", "kind-any-single-115", "kind-any-promise-115" ];
	var hidden6 = [ "pick-total-q3", "ball-total-q3", "kind-total-q3", "kind-same3-q3", "kind-same2-q3", "kind-no3-q3", "kind-no2-q3", "kind-link3-q3", "kind-bs-q3", "kind-sd-q3", "kind-gr-q3",
		"kind-color-q3" ];
	var hidden7 = [ "pick-win-pk10", "ball-win-total-pk10", "kind-win-pk10", "kind-gr-pk10", "kind-dt-pk10", "kind-star3fb-pk10", "kind-star2fb-pk10" ];
	var hidden8 = [ "pick-star3-direct-3d", "pick-star3-group-3d", "kind-star3-3d", "kind-star2f-3d", "kind-star2b-3d", "kind-star1-3d", "kind-noassign-3d", "kind-dtt-3d" ];
	var hidden9 = [ "win-number-star5", "win-number-star5-group-120", "win-number-star5-group-60", "win-number-star5-group-30", "win-number-star5-group-20", "win-number-star5-group-10",
		"win-number-star5-group-05", "win-number-star4f", "win-number-star4f-group-24", "win-number-star4f-group-12", "win-number-star4f-group-06", "win-number-star4f-group-04",
		"win-number-star4b", "win-number-star4b-group-24", "win-number-star4b-group-12", "win-number-star4b-group-06", "win-number-star4b-group-04", "win-number-star3f",
		"win-number-star3f-direct-total", "win-number-star3f-direct-cross", "win-number-star3f-group-03", "win-number-star3f-group-06", "win-number-star3f-group-mix",
		"win-number-star3f-group-total", "win-number-star3f-other-total", "win-number-star3f-other-mix", "win-number-star3m", "win-number-star3m-direct-total",
		"win-number-star3m-direct-cross", "win-number-star3m-group-03", "win-number-star3m-group-06", "win-number-star3m-group-mix", "win-number-star3m-group-total",
		"win-number-star3m-other-total", "win-number-star3m-other-mix", "win-number-star3b", "win-number-star3b-direct-total", "win-number-star3b-direct-cross", "win-number-star3b-group-03",
		"win-number-star3b-group-06", "win-number-star3b-group-mix", "win-number-star3b-group-total", "win-number-star3b-other-total", "win-number-star3b-other-mix", "win-number-star2f",
		"win-number-star2f-total", "win-number-star2f-cross", "win-number-star2b", "win-number-star2b-total", "win-number-star2b-cross", "win-number-bssd-front02", "win-number-bs-back02",
		"win-number-dtt-ten-thousand1000", "win-number-dtt-ten-thousand100", "win-number-dtt-ten-thousand10", "win-number-dtt-ten-thousand01", "win-number-dtt-thousand100",
		"win-number-dtt-thousand10", "win-number-dtt-thousand01", "win-number-dtt-hundred10", "win-number-dtt-hundred01", "win-number-dtt-ten01", "win-number-star3",
		"win-number-star3-direct-total-3d", "win-number-star3-group-03-3d", "win-number-star3-group-06-3d", "win-number-star3-group-mix-3d", "win-number-star3f-02-3d",
		"win-number-star3b-02-3d", "win-number-dtt-hundred10-3d", "win-number-dtt-hundred01-3d", "win-number-dtt-ten01-3d", "win-number-winsecond-total-pk10",
		"win-number-winsecond-total-bsds-pk10", "win-number-guess-rank-pk10", "win-number-dragon-tiger-pk10", "win-number-star2f-02-pk10", "win-number-star2b-02-pk10",
		"win-number-star3f-03-pk10", "win-number-star3b-03-pk10", "win-number-bs-q3", "win-number-sd-q3" ];

	/*
	 * for(var i=0; i < hiddenName.length; i++){
	 * addClass(document.getElementsByClassName(hiddenName[i])[0],
	 * "invisible"); }
	 */

	// ssc 0
	// 0:hidden2;
	// ffc 1
	// 1:hidden3;
	// 35 3
	// 2:hidden4;
	// 115 2
	// 3:hidden5
	// q3 6
	// 4:hidden6;
	// pk10 5
	// 5:hidden7;
	// 3d 4
	// 6:hidden8;
	/*
	 * // 第一層 隱藏 class加入kind-tab var l1 =
	 * document.getElementsByClassName("kind-tab");
	 * if(typeof(l1)!='undefined' ? l1.length>0 : false){ for(var i=0; i<l1.length;
	 * i++ ){ if(!hasClass(l1[i],"invisible")){ addClass(l1[i],
	 * "invisible"); } } } // 第二層 隱藏 class加入pick-tab var l2 =
	 * document.getElementsByClassName("pick-tab");
	 * if(typeof(l2)!='undefined' ? l2.length>0 : false){ for(var i=0; i<l2.length;
	 * i++ ){ if(!hasClass(l2[i], "invisible")){ addClass(l2[i],
	 * "invisible"); } } } // 第三層 隱藏 class加入ball-tab var l3 =
	 * document.getElementsByClassName("ball-tab");
	 * if(typeof(l3)!='undefined' ? l3.length>0 : false){ for(var i=0; i<l3.length;
	 * i++ ){ if(!hasClass(l3[i], "invisible")){ addClass(l3[i],
	 * "invisible"); } } } delete l1; delete l2; delete l3; l1 = undefined;
	 * l2 = undefined; l3 = undefined; if(type!=undefined ? type!=null :
	 * false){ var tmpA = document.getElementsByClassName("pick-lottery");
	 * if(type>=0 && type<=6){ for(var i=0; i<tmpA.length; i++){
	 * if(!hasClass(tmpA[i], "invisible")){ addClass(tmpA[i], "invisible"); } }
	 * var tmpLen = hidden1.length; for(var i=0; i < tmpLen; i++){
	 * addClass(document.getElementsByClassName(hidden1[i])[0],
	 * "invisible"); } tmpLen = hidden2.length; for(var i=0; i < tmpLen;
	 * i++){ addClass(document.getElementsByClassName(hidden2[i])[0],
	 * "invisible"); } tmpLen = hidden3.length; for(var i=0; i < tmpLen;
	 * i++){ addClass(document.getElementsByClassName(hidden3[i])[0],
	 * "invisible"); } tmpLen = hidden4.length; for(var i=0; i < tmpLen;
	 * i++){ addClass(document.getElementsByClassName(hidden4[i])[0],
	 * "invisible"); } tmpLen = hidden5.length; for(var i=0; i < tmpLen;
	 * i++){ addClass(document.getElementsByClassName(hidden5[i])[0],
	 * "invisible"); } tmpLen = hidden6.length; for(var i=0; i < tmpLen;
	 * i++){ addClass(document.getElementsByClassName(hidden6[i])[0],
	 * "invisible"); } tmpLen = hidden7.length; for(var i=0; i < tmpLen;
	 * i++){ addClass(document.getElementsByClassName(hidden7[i])[0],
	 * "invisible"); } tmpLen = hidden8.length; for(var i=0; i < tmpLen;
	 * i++){ addClass(document.getElementsByClassName(hidden8[i])[0],
	 * "invisible"); } tmpLen = hidden9.length; for(var i=0; i < tmpLen;
	 * i++){ addClass(document.getElementsByClassName(hidden9[i])[0],
	 * "invisible"); } delete tmpLen; tmpLen = undefined; } if(type==0){ var
	 * tmpLen = hidden2.length; for(var i=0; i < tmpLen; i++){
	 * removeClass(document.getElementsByClassName(hidden2[i])[0],
	 * "invisible"); } if(hasClass(tmpA[0], "invisible")){
	 * removeClass(tmpA[0], "invisible"); } delete tmpLen; tmpLen =
	 * undefined; } else if(type==1){ var tmpLen = hidden3.length; for(var
	 * i=0; i < tmpLen; i++){
	 * removeClass(document.getElementsByClassName(hidden3[i])[0],
	 * "invisible"); } if(hasClass(tmpA[1], "invisible")){
	 * removeClass(tmpA[1], "invisible"); } delete tmpLen; tmpLen =
	 * undefined; } else if(type==2){ var tmpLen = hidden4.length; for(var
	 * i=0; i < tmpLen; i++){
	 * removeClass(document.getElementsByClassName(hidden4[i])[0],
	 * "invisible"); } if(hasClass(tmpA[3], "invisible")){
	 * removeClass(tmpA[3], "invisible"); } delete tmpLen; tmpLen =
	 * undefined; } else if(type==3){ var tmpLen = hidden5.length; for(var
	 * i=0; i < tmpLen; i++){
	 * removeClass(document.getElementsByClassName(hidden5[i])[0],
	 * "invisible"); } if(hasClass(tmpA[2], "invisible")){
	 * removeClass(tmpA[2], "invisible"); } delete tmpLen; tmpLen =
	 * undefined; } else if(type==4){ var tmpLen = hidden6.length; for(var
	 * i=0; i < tmpLen; i++){
	 * removeClass(document.getElementsByClassName(hidden6[i])[0],
	 * "invisible"); } if(hasClass(tmpA[6], "invisible")){
	 * removeClass(tmpA[6], "invisible"); } delete tmpLen; tmpLen =
	 * undefined; } else if(type==5){ var tmpLen = hidden7.length; for(var
	 * i=0; i < tmpLen; i++){
	 * removeClass(document.getElementsByClassName(hidden7[i])[0],
	 * "invisible"); } if(hasClass(tmpA[5], "invisible")){
	 * removeClass(tmpA[5], "invisible"); } delete tmpLen; tmpLen =
	 * undefined; } else if(type==6){ var tmpLen = hidden8.length; for(var
	 * i=0; i < tmpLen; i++){
	 * removeClass(document.getElementsByClassName(hidden8[i])[0],
	 * "invisible"); } if(hasClass(tmpA[4], "invisible")){
	 * removeClass(tmpA[4], "invisible"); } delete tmpLen; tmpLen =
	 * undefined; } } delete hidden1; delete hidden2; hidden1 = undefined;
	 * hidden2 = undefined;
	 */

	if (typeof clearName != "undefined" ? clearName.length > 0 : false) {
		var tmpLen = clearName.length;
		for (var i = 0; i < tmpLen; i++) {
			removeAllClasses(clearName[i]);
		}
		delete tmpLen;
		tmpLen = undefined;
	}
	if (typeof hiddenName != "undefined" ? hiddenName.length > 0 : false) {
		var tmpLen = hiddenName.length;
		for (var i = 0; i < tmpLen; i++) {
			addClass(document.getElementsByClassName(hiddenName[i])[0], "invisible");
		}
		delete tmpLen;
		tmpLen = undefined;
	}
	if (typeof showName != "undefined" ? showName.length > 0 : false) {
		var tmpLen = showName.length;
		for (var i = 0; i < tmpLen; i++) {
			removeClass(document.getElementsByClassName(showName[i])[0], "invisible");
		}
		delete tmpLen;
		tmpLen = undefined;
	}

	ballInitOnclick();
	ballPlayInit();
	delete clearName;
	delete hiddenName;
	delete showName;
	clearName = undefined;
	hiddenName = undefined;
	showName = undefined;
	type = undefined;
	// addClass(getEle("RightSideDiv"), "hidden");
	// checkWinHistory();
	// checkTitle();
	disableLoadingPage();
}

function removeAllInputChecked() {
	var tmpA = document.getElementsByClassName("ball-tab");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		// console_Log(typeof tmpA[i1]);
		// console_Log(tmpA[i1].classList);
		// console_Log(tmpA[i1].getElementsByClassName("ball-number").length);

		if (tmpA[i1] ? tmpA[i1].getElementsByClassName : false) {
			var tmpB = tmpA[i1].getElementsByClassName("ball-number");
			for (var i2 = 0; i2 < tmpB.length; i2++) {
				tmpC = tmpB[i2].getElementsByTagName("input");
				for (var i3 = 0; i3 < tmpC.length; i3++) {
					tmpC[i3].onclick = function() {
						onclickBall(this);
					}
					if (tmpC[i3].checked) {
						tmpC[i3].checked = false;
					}
				}
				delete tmpC;
				tmpC = undefined;
			}
			if (tmpB.length == 0) {
				tmpB = tmpA[i1].getElementsByClassName("import-checkbox-area");
				for (var i2 = 0; i2 < tmpB.length; i2++) {
					tmpC = tmpB[i2].getElementsByTagName("input");
					for (var i3 = 0; i3 < tmpC.length; i3++) {
						tmpC[i3].onclick = function() {
							onclickBall(this);
						}
						if (tmpC[i3].checked) {
							tmpC[i3].checked = false;
						}
					}
					tmpC = tmpB[i2].parentElement.getElementsByTagName("textarea");
					if (tmpC.length == 1) {
						tmpC[0].value = "";
					}
					delete tmpC;
					tmpC = undefined;
				}
			}
			delete tmpB;
			tmpB = undefined;
		}
	}
	tmpA = document.getElementsByClassName("win-history");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		addClass(tmpA[i1], "hidden");
	}
	delete tmpA;
	tmpA = undefined;
}

var ballManager = (function() {
	var timers = [];
	return {
		addTimer : function(callback, timeout) {
			var timer, that = this;
			evalManager.removeAll();
			that.removeAll();
			timer = setTimeout(function() {
				that.removeTimer(timer);
				callback();
			}, timeout);
			timers.push(timer);
			return timer;
		},
		removeTimer : function(timer) {
			evalManager.removeAll();
			clearTimeout(timer);
			timers.splice(timers.indexOf(timer), 1);
		},
		removeAll : function() {
			var that = this;
			for ( var i in timers) {
				that.removeTimer(timers[i]);
			}
		},
		init : function() {
			var that = this;
			that.addTimer(function() {
				checkBallOnchange();
			}, 1);
		},
		getTimers : function() {
			return timers;
		}
	};
})();
var evalManager = (function() {
	var timers = [];
	return {
		addTimer : function(callback, timeout) {
			var timer, that = this;
			f_kj_timer(5000);
			that.removeAll();
			timer = setTimeout(function() {
				that.removeTimer(timer);
				callback();
			}, timeout);
			timers.push(timer);
			return timer;
		},
		removeTimer : function(timer) {
			clearTimeout(timer);
			timers.splice(timers.indexOf(timer), 1);
		},
		removeAll : function() {
			var that = this;
			for ( var i in timers) {
				that.removeTimer(timers[i]);
			}
		},
		getTimers : function() {
			return timers;
		}
	};
})();
function ballInitOnclick() {
	var tmpA = document.getElementsByClassName("ball-tab");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		if (!hasClass(tmpA[i1], "invisible")) {
			// console_Log(tmpA[i1].classList.toString().replace("ball-tab
			// ", ""));

			if (tmpA[i1] ? tmpA[i1].getElementsByClassName : false) {
				var tmpB = tmpA[i1].getElementsByClassName("ball-number");
				if (tmpB.length == 0) {
					tmpB = tmpA[i1].getElementsByClassName("import-checkbox-area");
				}
				// console_Log(b.length);
				for (var i2 = 0; i2 < tmpB.length; i2++) {
					// console_Log(tmpB[i2].innerHTML);
					var tmpC = tmpB[i2].getElementsByTagName("button");
					for (var i3 = 0; i3 < tmpC.length; i3++) {
						tmpC[i3].onclick = function() {
							onclickBall(this);
						};
						tmpC[i3].onclick();
					}
					if (tmpC.length == 0) {
						tmpC = tmpB[i2].getElementsByTagName("input");
						for (var i3 = 0; i3 < tmpC.length; i3++) {
							tmpC[i3].onclick = function() {
								onclickBall(this);
							};
							tmpC[i3].onclick();
						}
					}
					tmpC = tmpB[i2].getElementsByTagName("label");
					if (tmpC.length > 0) {
						for (var i3 = 0; i3 < tmpC.length; i3++) {
							if (tmpC[i3].getElementsByTagName("input").length == 1) {
								tmpC[i3].getElementsByTagName("input")[0].onclick = function() {
									onclickBall(this);
								};
								tmpC[i3].getElementsByTagName("input")[0].onclick();
							}
						}
						tmpC = tmpC[0].parentElement.parentElement.getElementsByTagName("textarea");
						if (tmpC.length == 1) {
							document.getElementsByName("areaContent")[0].value = "";
							tmpC[0].onchange = function() {
								onclickBall(this);
							};
							tmpC[0].onchange();
							tmpC[0].onproterychange = function() {
								onclickBall(this);
							};
							tmpC[0].onproterychange();
							tmpC[0].onkeyup = function() {
								onclickBall(this);
							};
							tmpC[0].onkeyup();
							tmpC[0].onfocus = function() {
								onclickBall(this);
							};
							tmpC[0].onfocus();
							tmpC[0].onblur = function() {
								onclickBall(this);
							};
							tmpC[0].onblur();

							tmpC[0].parentElement.onmousemove = function() {
								if (document.getElementsByName("areaContent")[0].value != this.getElementsByTagName("textarea")[0].value) {
									ballManager.init();
								}
							};
							tmpC[0].parentElement.onmousemove();

							tmpC[0].parentElement.onkeyup = function() {
								ballManager.addTimer(function() {
									checkBallOnchange()
								}, 1000);
							};
							tmpC[0].parentElement.onkeyup();
							// tmpC[0].onmouseout =
							// function(){onclickBall(this);
							// };
							// tmpC[0].onmouseout();
							// document.getElementsByClassName("import-area")[0].onblur
							// =
							// function(){checkBallOnchange();
							// };
							// document.getElementsByClassName("import-area")[0].onblur();
							// document.getElementsByClassName("import-checkbox-area")[0].onfocus
							// =
							// function(){checkBallOnchange();
							// };
							// document.getElementsByClassName("import-checkbox-area")[0].onfocus();
							// document.getElementsByClassName("btn-area-bottom")[0].onfocus
							// =
							// function(){checkBallOnchange();
							// };
							// document.getElementsByClassName("btn-area-bottom")[0].onfocus();
							// document.getElementsByClassName("import-area")[0].onfocus
							// =
							// function(){checkBallOnchange();
							// };
							// document.getElementsByClassName("import-area")[0].onfocus();
							// document.getElementsByClassName("import-checkbox-area")[0].onblur
							// =
							// function(){checkBallOnchange();
							// };
							// document.getElementsByClassName("import-checkbox-area")[0].onblur();
							// document.getElementsByClassName("btn-area-bottom")[0].onblur
							// =
							// function(){checkBallOnchange();
							// };
							// document.getElementsByClassName("btn-area-bottom")[0].onblur();
							// document.getElementsByClassName("import-area")[0].onblur
							// =
							// function(){checkBallOnchange();
							// };
							// document.getElementsByClassName("import-area")[0].onblur();

							// document.getElementsByClassName("bet-area")[0].onmousemove
							// =
							// function(){checkBallOnchange();
							// };
							// document.getElementsByClassName("bet-area")[0].onmousemove();

							// document.getElementsByClassName("bet-area")[0].onfocus
							// =
							// function(){checkBallOnchange();
							// };
							// document.getElementsByClassName("bet-area")[0].onfocus();
							// document.getElementsByClassName("bet-area")[0].onblur
							// =
							// function(){checkBallOnchange();
							// };
							// document.getElementsByClassName("bet-area")[0].onblur();
							// document.getElementsByClassName("right-side")[0].onmousemove
							// =
							// function(){checkBallOnchange();
							// };
							// document.getElementsByClassName("right-side")[0].onmousemove();
							// document.getElementsByClassName("right-side")[0].onblur
							// =
							// function(){checkBallOnchange();
							// };
							// document.getElementsByClassName("right-side")[0].onblur();
						}
					}
					delete tmpC;
					tmpC = undefined;
				}
				delete tmpB;
				tmpB = undefined;
			}
		}
	}
	delete tmpA;
	tmpA = undefined;
}

function onclickBall(ele) {
	ele.onclick = function() {
		// ele.classList.toggle("ball-act");
		if (document.getElementsByClassName("pick-act").length == 1 ? document.getElementsByClassName("pick-act")[0].innerHTML.indexOf("包胆") != -1 : false) {
			var tmpB = ele.parentElement.getElementsByTagName("button");
			for (var i = 0; i < tmpB.length; i++) {
				if (hasClass(tmpB[i], "ball-act")) {
					removeClass(tmpB[i], "ball-act");
				}
			}
		}
		var tmpList = [ , "前二组选胆托", "前三组选胆托", "任选二中二胆托", "任选三中三胆托", "任选四中四胆托", "任选五中五胆托", "任选六中五胆托", "任选七中五胆托", "任选八中五胆托", ];
		var tn = document.getElementsByClassName("bet-area")[0].getElementsByClassName("rule")[0].innerHTML;
		if (!hasClass(ele, "ball-act") && ("," + tmpList.toString() + ",").indexOf("," + tn + ",") != -1) {
			// ball-number ball-number-promise
			// ball-number ball-number-pull
			// //
			if (ele.parentElement.parentElement.parentElement.className.indexOf("ball-number-promise") != -1) {
				var tmpA = document.getElementsByClassName("ball-pull")[0].getElementsByClassName("ball-play-one")[0].getElementsByTagName("button");
				for (var i = 0; i < tmpA.length; i++) {
					removeClass(tmpA[i], "ball-act");
				}
			}
			delete tmpA;
			tmpA = undefined;
			// //
			var tmpV1 = -1;
			var tmpV2 = -1;
			var tmpCou1 = 0;
			var tmpCou2 = 0;
			if (ele.parentElement.parentElement.parentElement.className.indexOf("ball-number-promise") != -1) {
				tmpV1 = parseInt(ele.innerHTML) - 1;
			} else if (ele.parentElement.parentElement.parentElement.className.indexOf("ball-number-pull") != -1) {
				tmpV2 = parseInt(ele.innerHTML) - 1;
			}
			var tmpX = document.getElementsByClassName("ball-number-promise")[0].getElementsByTagName("button");
			for (var i = 0; i < tmpX.length; i++) {
				if (tmpX[i].className.indexOf("ball-act") != -1) {
					tmpCou1++;
				}
			}
			var tmpY = document.getElementsByClassName("ball-number-pull")[0].getElementsByTagName("button");
			for (var i = 0; i < tmpY.length; i++) {
				if (tmpY[i].className.indexOf("ball-act") != -1) {
					tmpCou2++;
				}
			}
			if (tn == "前二组选胆托" || tn == "任选二中二胆托") {
				if (tmpCou1 == 1 && tmpV1 != -1) {
					for (var i = 0; i < tmpX.length; i++) {
						if (hasClass(tmpX[i], "ball-act")) {
							removeClass(tmpX[i], "ball-act");
							break;
						}
					}
				}
			} else if (tn == "前三组选胆托" || tn == "任选三中三胆托") {
				if (tmpCou1 == 2 && tmpV1 != -1) {
					for (var i = 0; i < tmpX.length; i++) {
						if (hasClass(tmpX[i], "ball-act")) {
							removeClass(tmpX[i], "ball-act");
							break;
						}
					}
				}
			} else if (tn == "任选四中四胆托") {
				if (tmpCou1 == 3 && tmpV1 != -1) {
					for (var i = 0; i < tmpX.length; i++) {
						if (hasClass(tmpX[i], "ball-act")) {
							removeClass(tmpX[i], "ball-act");
							break;
						}
					}
				}
			} else if (tn == "任选五中五胆托") {
				if (tmpCou1 == 4 && tmpV1 != -1) {
					for (var i = 0; i < tmpX.length; i++) {
						if (hasClass(tmpX[i], "ball-act")) {
							removeClass(tmpX[i], "ball-act");
							break;
						}
					}
				}
			} else if (tn == "任选六中五胆托") {
				if (tmpCou1 == 5 && tmpV1 != -1) {
					for (var i = 0; i < tmpX.length; i++) {
						if (hasClass(tmpX[i], "ball-act")) {
							removeClass(tmpX[i], "ball-act");
							break;
						}
					}
				}
			} else if (tn == "任选七中五胆托") {
				if (tmpCou1 == 6 && tmpV1 != -1) {
					for (var i = 0; i < tmpX.length; i++) {
						if (hasClass(tmpX[i], "ball-act")) {
							removeClass(tmpX[i], "ball-act");
							break;
						}
					}
				}
			} else if (tn == "任选八中五胆托") {
				if (tmpCou1 == 7 && tmpV1 != -1) {
					for (var i = 0; i < tmpX.length; i++) {
						if (hasClass(tmpX[i], "ball-act")) {
							removeClass(tmpX[i], "ball-act");
							break;
						}
					}
				}
			}
			if (tmpV1 != -1 && hasClass(tmpY[tmpV1], "ball-act")) {
				removeClass(tmpY[tmpV1], "ball-act");
			}
			if (tmpV2 != -1 && hasClass(tmpX[tmpV2], "ball-act")) {
				removeClass(tmpX[tmpV2], "ball-act");
			}
			delete tmpV1;
			delete tmpV2;
			delete tmpCou1;
			delete tmpCou2;
			delete tmpX;
			delete tmpY;
			tmpV1 = undefined;
			tmpV2 = undefined;
			tmpCou1 = undefined;
			tmpCou2 = undefined;
			tmpX = undefined;
			tmpY = undefined;
		}
		if (hasClass(ele, "ball-act")) {
			removeClass(ele, "ball-act");
		} else {
			addClass(ele, "ball-act");
		}
		var tmpA = ele.parentElement.parentElement.parentElement.parentElement.getElementsByClassName("ball-play");
		if(tmpA.length == 0){
			tmpA = ele.parentElement.parentElement.parentElement.parentElement.parentElement.getElementsByClassName("ball-play");
		}
		// console_Log(tmpA);
		if (tmpA != undefined ? tmpA != null ? tmpA.length > 0 : false : false) {
			tmpA = tmpA[0].getElementsByTagName("button");
			for (var i1 = 0; i1 < tmpA.length; i1++) {
				if (hasClass(tmpA[i1], "ball-act")) {
					removeClass(tmpA[i1], "ball-act");
				}
			}
		}
		delete tmpA;
		tmpA = undefined;
		ballManager.init();
	}
}

function checkBallOnchange() {
	document.getElementsByClassName("bet")[0].getElementsByTagName("button")[0].disabled = true;
	document.getElementsByClassName("bet")[0].getElementsByTagName("button")[1].disabled = true;
	var tmpJson = {};
	var tmpA = document.getElementsByClassName("ball-tab");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		if (!hasClass(tmpA[i1], "invisible")) {
			var tmpB = tmpA[i1].getElementsByClassName("ball-number");
			if (tmpB.length == 0) {
				tmpB = tmpA[i1].getElementsByClassName("import-checkbox-area");
			}
			for (var i2 = 0; i2 < tmpB.length; i2++) {
				if (!hasClass(tmpB[i2], "invisible")) {
					var tmpStr = "";
					if (tmpA[i1].classList) {
						tmpStr = tmpA[i1].classList.toString().replace("ball-tab ", "");
					} else {
						tmpStr = tmpA[i1].className.toString().replace("ball-tab ", "");
					}
					var tmpC = tmpB[i2].getElementsByTagName("button");
					var tmpList = [];
					for (var i3 = 0; i3 < tmpC.length; i3++) {
						if (hasClass(tmpC[i3], "ball-act")) {
							tmpStr += ",[" + i3 + "]" + tmpC[i3].innerHTML;
							tmpList[i3] = 1;
						} else {
							tmpList[i3] = 0;
						}
					}
					if (tmpC.length == 0) {
						tmpC = tmpB[i2].getElementsByTagName("input");
						tmpList = [];
						for (var i3 = 0; i3 < tmpC.length; i3++) {
							if (tmpC[i3].checked) {
								tmpStr += ",[" + i3 + "]" + tmpC[i3].innerHTML;
								tmpList[i3] = 1;
							} else {
								tmpList[i3] = 0;
							}
						}
					}
					// console_Log(tmpStr);
					// tmpJson[tmpA[i1].classList.toString().replace("ball-tab
					// ", "")] = tmpList;
					if (tmpA[i1].classList) {
						tmpJson[tmpA[i1].classList.toString().replace("ball-tab ", "")] = tmpList;
					} else {
						tmpJson[tmpA[i1].className.toString().replace("ball-tab ", "")] = tmpList;
					}
					delete tmpStr;
					tmpStr = undefined;
					delete tmpC;
					tmpC = undefined;
					delete tmpList;
					tmpList = undefined;
					// console_Log(tmpJson);
				}
			}
			tmpB = tmpA[i1].getElementsByTagName("textarea");
			if (tmpB.length == 1) {
				tmpJson["textarea"] = [ tmpB[0].value ];
				document.getElementsByName("areaContent")[0].value = tmpB[0].value;
			}
			delete tmpB;
			tmpB = undefined;
		}
	}
	console_Log(tmpJson);
	tmpA = document.getElementById("game-choice");
	// getSetting();
	if (tmpA != undefined ? tmpA != null : false) {
		tmpA.value = JSON.stringify(tmpJson);
		document.getElementsByName("entrance")[0].value = document.getElementsByName("functionName")[0].value;
		var fn = "" + document.getElementsByName("functionName")[0].value + "();";
		console_Log(fn.toString());
		if (fn != "();") {
			evalManager.addTimer(function() {
				eval(fn.toString());
			}, 300);
		}
	}
	delete tmpA;
	tmpA = undefined;
	delete tmpJson;
	tmpJson = undefined;
}

function ballNumOptionAll(ele) {
	// parentElement.children
	var tmpA;
	tmpA = ele.parentElement.getElementsByTagName("button");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		removeClass(tmpA[i1], "ball-act");
	}
	addClass(ele, "ball-act");
	tmpA = ele.parentElement.parentElement.parentElement.getElementsByClassName("ball-number");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		var tmpB = tmpA[i1].getElementsByTagName("button");
		for (var i2 = 0; i2 < tmpB.length; i2++) {
			if (!hasClass(tmpB[i2], "ball-act")) {
				addClass(tmpB[i2], "ball-act");
			}
		}
		delete tmpB;
		tmpB = undefined;
	}
	delete tmpA;
	tmpA = undefined;
	var tmpList = [ , "前二组选胆托", "前三组选胆托", "任选二中二胆托", "任选三中三胆托", "任选四中四胆托", "任选五中五胆托", "任选六中五胆托", "任选七中五胆托", "任选八中五胆托", ];
	var tn = document.getElementsByClassName("bet-area")[0].getElementsByClassName("rule")[0].innerHTML;
	if (("," + tmpList.toString() + ",").indexOf("," + tn + ",") != -1) {
		var tmpX = document.getElementsByClassName("ball-number-promise")[0].getElementsByTagName("button");
		var tmpY = document.getElementsByClassName("ball-number-pull")[0].getElementsByTagName("button");
		for (var i = 0; i < tmpX.length; i++) {
			if (hasClass(tmpX[i], "ball-act")) {
				removeClass(tmpY[i], "ball-act");
			}
		}
		delete tmpX;
		tmpX = undefined;
		delete tmpY;
		tmpY = undefined;
	}
	delete tmpList;
	tmpList = undefined;
	delete tn;
	tn = undefined;
	ballManager.init();
}
function ballNumOptionClear(ele) {
	// parentElement.children
	var tmpA;
	if (getEle("localId").value == ("" + XGLHC_ID)) {
		tmpA = ele.parentElement.parentElement.getElementsByTagName("button");
	} else {
		tmpA = ele.parentElement.getElementsByTagName("button");
	}
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		removeClass(tmpA[i1], "ball-act");
	}
	addClass(ele, "ball-act");
	tmpA = ele.parentElement.parentElement.parentElement.getElementsByClassName("ball-number");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		var tmpB = tmpA[i1].getElementsByTagName("button");
		for (var i2 = 0; i2 < tmpB.length; i2++) {
			if (hasClass(tmpB[i2], "ball-act")) {
				removeClass(tmpB[i2], "ball-act");
			}
		}
		delete tmpB;
		tmpB = undefined;
	}
	delete tmpA;
	tmpA = undefined;
	ballManager.init();
	removeClass(ele, "ball-act");
}
function ballNumOptionBig(ele) {
	// parentElement.children
	var tmpA;
	tmpA = ele.parentElement.getElementsByTagName("button");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		removeClass(tmpA[i1], "ball-act");
	}
	addClass(ele, "ball-act");
	tmpA = ele.parentElement.parentElement.parentElement.getElementsByClassName("ball-number");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		var tmpB = tmpA[i1].getElementsByTagName("button");
		for (var i2 = 0; i2 < tmpB.length; i2++) {
			if (hasClass(tmpB[i2], "ball-act") && getEle("localId").value != ("" + XGLHC_ID)) {
				removeClass(tmpB[i2], "ball-act");
			}
			if (i2 < parseInt(tmpB.length / 2)) {

			} else {
				addClass(tmpB[i2], "ball-act");
			}
		}
		delete tmpB;
		tmpB = undefined;
	}
	delete tmpA;
	tmpA = undefined;
	var tmpList = [ , "前二组选胆托", "前三组选胆托", "任选二中二胆托", "任选三中三胆托", "任选四中四胆托", "任选五中五胆托", "任选六中五胆托", "任选七中五胆托", "任选八中五胆托", ];
	var tn = document.getElementsByClassName("bet-area")[0].getElementsByClassName("rule")[0].innerHTML;
	if (("," + tmpList.toString() + ",").indexOf("," + tn + ",") != -1) {
		var tmpX = document.getElementsByClassName("ball-number-promise")[0].getElementsByTagName("button");
		var tmpY = document.getElementsByClassName("ball-number-pull")[0].getElementsByTagName("button");
		for (var i = 0; i < tmpX.length; i++) {
			if (hasClass(tmpX[i], "ball-act")) {
				removeClass(tmpY[i], "ball-act");
			}
		}
		delete tmpX;
		tmpX = undefined;
		delete tmpY;
		tmpY = undefined;
	}
	delete tmpList;
	tmpList = undefined;
	delete tn;
	tn = undefined;
	ballManager.init();
}
function ballNumOptionSmall(ele) {
	// parentElement.children
	var tmpA;
	tmpA = ele.parentElement.getElementsByTagName("button");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		removeClass(tmpA[i1], "ball-act");
	}
	addClass(ele, "ball-act");
	tmpA = ele.parentElement.parentElement.parentElement.getElementsByClassName("ball-number");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		var tmpB = tmpA[i1].getElementsByTagName("button");
		for (var i2 = 0; i2 < tmpB.length; i2++) {
			if (hasClass(tmpB[i2], "ball-act") && getEle("localId").value != ("" + XGLHC_ID)) {
				removeClass(tmpB[i2], "ball-act");
			}
			if (i2 < parseInt(tmpB.length / 2)) {
				addClass(tmpB[i2], "ball-act");
			} else {

			}
		}
		delete tmpB;
		tmpB = undefined;
	}
	delete tmpA;
	tmpA = undefined;
	var tmpList = [ , "前二组选胆托", "前三组选胆托", "任选二中二胆托", "任选三中三胆托", "任选四中四胆托", "任选五中五胆托", "任选六中五胆托", "任选七中五胆托", "任选八中五胆托", ];
	var tn = document.getElementsByClassName("bet-area")[0].getElementsByClassName("rule")[0].innerHTML;
	if (("," + tmpList.toString() + ",").indexOf("," + tn + ",") != -1) {
		var tmpX = document.getElementsByClassName("ball-number-promise")[0].getElementsByTagName("button");
		var tmpY = document.getElementsByClassName("ball-number-pull")[0].getElementsByTagName("button");
		for (var i = 0; i < tmpX.length; i++) {
			if (hasClass(tmpX[i], "ball-act")) {
				removeClass(tmpY[i], "ball-act");
			}
		}
		delete tmpX;
		tmpX = undefined;
		delete tmpY;
		tmpY = undefined;
	}
	delete tmpList;
	tmpList = undefined;
	delete tn;
	tn = undefined;
	ballManager.init();
}
function ballNumOptionSign(ele) {
	// parentElement.children
	var tmpA;
	tmpA = ele.parentElement.getElementsByTagName("button");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		removeClass(tmpA[i1], "ball-act");
	}
	addClass(ele, "ball-act");
	tmpA = ele.parentElement.parentElement.parentElement.getElementsByClassName("ball-number");
	var add = 0;
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		var tmpB = tmpA[i1].getElementsByTagName("button");
		if (tmpB[0].innerHTML == "1") {
			add = 1;
		}
		for (var i2 = 0; i2 < tmpB.length; i2++) {
			if (hasClass(tmpB[i2], "ball-act")  && getEle("localId").value != ("" + XGLHC_ID)) {
				removeClass(tmpB[i2], "ball-act");
			}
			if ((i2 + add) % 2 == 1) {
				addClass(tmpB[i2], "ball-act");
			}
		}
		delete tmpB;
		tmpB = undefined;
	}
	delete tmpA;
	tmpA = undefined;
	var tmpList = [ , "前二组选胆托", "前三组选胆托", "任选二中二胆托", "任选三中三胆托", "任选四中四胆托", "任选五中五胆托", "任选六中五胆托", "任选七中五胆托", "任选八中五胆托", ];
	var tn = document.getElementsByClassName("bet-area")[0].getElementsByClassName("rule")[0].innerHTML;
	if (("," + tmpList.toString() + ",").indexOf("," + tn + ",") != -1) {
		var tmpX = document.getElementsByClassName("ball-number-promise")[0].getElementsByTagName("button");
		var tmpY = document.getElementsByClassName("ball-number-pull")[0].getElementsByTagName("button");
		for (var i = 0; i < tmpX.length; i++) {
			if (hasClass(tmpX[i], "ball-act")) {
				removeClass(tmpY[i], "ball-act");
			}
		}
		delete tmpX;
		tmpX = undefined;
		delete tmpY;
		tmpY = undefined;
	}
	delete tmpList;
	tmpList = undefined;
	delete tn;
	tn = undefined;
	ballManager.init();
}
function ballNumOptionDouble(ele) {
	// parentElement.children
	var tmpA;
	tmpA = ele.parentElement.getElementsByTagName("button");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		removeClass(tmpA[i1], "ball-act");
	}
	addClass(ele, "ball-act");
	tmpA = ele.parentElement.parentElement.parentElement.getElementsByClassName("ball-number");
	var add = 0;
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		var tmpB = tmpA[i1].getElementsByTagName("button");
		if (tmpB[0].innerHTML == "1") {
			add = 1;
		}
		for (var i2 = 0; i2 < tmpB.length; i2++) {
			if (hasClass(tmpB[i2], "ball-act") && getEle("localId").value != ("" + XGLHC_ID)) {
				removeClass(tmpB[i2], "ball-act");
			}
			if ((i2 + add) % 2 == 0) {
				addClass(tmpB[i2], "ball-act");
			}
		}
		delete tmpB;
		tmpB = undefined;
	}
	delete tmpA;
	tmpA = undefined;
	var tmpList = [ , "前二组选胆托", "前三组选胆托", "任选二中二胆托", "任选三中三胆托", "任选四中四胆托", "任选五中五胆托", "任选六中五胆托", "任选七中五胆托", "任选八中五胆托", ];
	var tn = document.getElementsByClassName("bet-area")[0].getElementsByClassName("rule")[0].innerHTML;
	if (("," + tmpList.toString() + ",").indexOf("," + tn + ",") != -1) {
		var tmpX = document.getElementsByClassName("ball-number-promise")[0].getElementsByTagName("button");
		var tmpY = document.getElementsByClassName("ball-number-pull")[0].getElementsByTagName("button");
		for (var i = 0; i < tmpX.length; i++) {
			if (hasClass(tmpX[i], "ball-act")) {
				removeClass(tmpY[i], "ball-act");
			}
		}
		delete tmpX;
		tmpX = undefined;
		delete tmpY;
		tmpY = undefined;
	}
	delete tmpList;
	tmpList = undefined;
	delete tn;
	tn = undefined;
	ballManager.init();
}

function ballNumOptionNumTotalBig(ele) {
	// parentElement.children
	var tmpA;
	tmpA = ele.parentElement.getElementsByTagName("button");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		removeClass(tmpA[i1], "ball-act");
	}
	addClass(ele, "ball-act");
	tmpA = ele.parentElement.parentElement.parentElement.getElementsByClassName("ball-number");
	var add = 0;
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		var tmpB = tmpA[i1].getElementsByTagName("button");
		if (tmpB[0].innerHTML == "1") {
			add = 1;
		}
		var ballDataArrayList = [];
		var maxNum = -1;
		for (var i2 = 0; i2 < tmpB.length; i2++) {
			var ballObj = {};
			var num = parseInt(tmpB[i2].innerHTML);
			num = (num / 10) + (num % 10);
			num = Math.floor(num);
			if (maxNum < num) {
				maxNum = num;
			}

			ballObj["ele"] = tmpB[i2];
			ballObj["val"] = "" + num;
			ballDataArrayList.push(ballObj);

			if (hasClass(tmpB[i2], "ball-act") && getEle("localId").value != ("" + XGLHC_ID)) {
				removeClass(tmpB[i2], "ball-act");
			}
		}
		for (var i3 = 0; i3 < ballDataArrayList.length; i3++) {

			var v = parseInt(ballDataArrayList[i3]["val"]);
			if (v >= Math.ceil(maxNum / 2)) {
				addClass(ballDataArrayList[i3]["ele"], "ball-act");
			}

		}
		delete tmpB;
		tmpB = undefined;
	}
	delete tmpA;
	tmpA = undefined;
	var tmpList = [ , "前二组选胆托", "前三组选胆托", "任选二中二胆托", "任选三中三胆托", "任选四中四胆托", "任选五中五胆托", "任选六中五胆托", "任选七中五胆托", "任选八中五胆托", ];
	var tn = document.getElementsByClassName("bet-area")[0].getElementsByClassName("rule")[0].innerHTML;
	if (("," + tmpList.toString() + ",").indexOf("," + tn + ",") != -1) {
		var tmpX = document.getElementsByClassName("ball-number-promise")[0].getElementsByTagName("button");
		var tmpY = document.getElementsByClassName("ball-number-pull")[0].getElementsByTagName("button");
		for (var i = 0; i < tmpX.length; i++) {
			if (hasClass(tmpX[i], "ball-act")) {
				removeClass(tmpY[i], "ball-act");
			}
		}
		delete tmpX;
		tmpX = undefined;
		delete tmpY;
		tmpY = undefined;
	}
	delete tmpList;
	tmpList = undefined;
	delete tn;
	tn = undefined;
	ballManager.init();
}

function ballNumOptionNumTotalSmall(ele) {
	// parentElement.children
	var tmpA;
	tmpA = ele.parentElement.getElementsByTagName("button");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		removeClass(tmpA[i1], "ball-act");
	}
	addClass(ele, "ball-act");
	tmpA = ele.parentElement.parentElement.parentElement.getElementsByClassName("ball-number");
	var add = 0;
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		var tmpB = tmpA[i1].getElementsByTagName("button");
		if (tmpB[0].innerHTML == "1") {
			add = 1;
		}
		var ballDataArrayList = [];
		var maxNum = -1;
		for (var i2 = 0; i2 < tmpB.length; i2++) {
			var ballObj = {};
			var num = parseInt(tmpB[i2].innerHTML);
			num = (num / 10) + (num % 10);
			num = Math.floor(num);
			if (maxNum < num) {
				maxNum = num;
			}

			ballObj["ele"] = tmpB[i2];
			ballObj["val"] = "" + num;
			ballDataArrayList.push(ballObj);

			if (hasClass(tmpB[i2], "ball-act") && getEle("localId").value != ("" + XGLHC_ID)) {
				removeClass(tmpB[i2], "ball-act");
			}
		}
		for (var i3 = 0; i3 < ballDataArrayList.length; i3++) {

			var v = parseInt(ballDataArrayList[i3]["val"]);
			if (v < Math.ceil(maxNum / 2)) {
				addClass(ballDataArrayList[i3]["ele"], "ball-act");
			}

		}
		delete tmpB;
		tmpB = undefined;
	}
	delete tmpA;
	tmpA = undefined;
	var tmpList = [ , "前二组选胆托", "前三组选胆托", "任选二中二胆托", "任选三中三胆托", "任选四中四胆托", "任选五中五胆托", "任选六中五胆托", "任选七中五胆托", "任选八中五胆托", ];
	var tn = document.getElementsByClassName("bet-area")[0].getElementsByClassName("rule")[0].innerHTML;
	if (("," + tmpList.toString() + ",").indexOf("," + tn + ",") != -1) {
		var tmpX = document.getElementsByClassName("ball-number-promise")[0].getElementsByTagName("button");
		var tmpY = document.getElementsByClassName("ball-number-pull")[0].getElementsByTagName("button");
		for (var i = 0; i < tmpX.length; i++) {
			if (hasClass(tmpX[i], "ball-act")) {
				removeClass(tmpY[i], "ball-act");
			}
		}
		delete tmpX;
		tmpX = undefined;
		delete tmpY;
		tmpY = undefined;
	}
	delete tmpList;
	tmpList = undefined;
	delete tn;
	tn = undefined;
	ballManager.init();
}

function ballNumOptionNumTotalSign(ele) {
	// parentElement.children
	var tmpA;
	tmpA = ele.parentElement.getElementsByTagName("button");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		removeClass(tmpA[i1], "ball-act");
	}
	addClass(ele, "ball-act");
	tmpA = ele.parentElement.parentElement.parentElement.getElementsByClassName("ball-number");
	var add = 0;
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		var tmpB = tmpA[i1].getElementsByTagName("button");
		if (tmpB[0].innerHTML == "1") {
			add = 1;
		}
		for (var i2 = 0; i2 < tmpB.length; i2++) {
			var num = parseInt(tmpB[i2].innerHTML);
			num = (num / 10) + (num % 10);
			num = Math.floor(num);
			if ((num % 2) == 1) {
				addClass(tmpB[i2], "ball-act");
			} 
			else if(getEle("localId").value != ("" + XGLHC_ID)){
				removeClass(tmpB[i2], "ball-act");
			}
		}
		delete tmpB;
		tmpB = undefined;
	}
	delete tmpA;
	tmpA = undefined;
	var tmpList = [ , "前二组选胆托", "前三组选胆托", "任选二中二胆托", "任选三中三胆托", "任选四中四胆托", "任选五中五胆托", "任选六中五胆托", "任选七中五胆托", "任选八中五胆托", ];
	var tn = document.getElementsByClassName("bet-area")[0].getElementsByClassName("rule")[0].innerHTML;
	if (("," + tmpList.toString() + ",").indexOf("," + tn + ",") != -1) {
		var tmpX = document.getElementsByClassName("ball-number-promise")[0].getElementsByTagName("button");
		var tmpY = document.getElementsByClassName("ball-number-pull")[0].getElementsByTagName("button");
		for (var i = 0; i < tmpX.length; i++) {
			if (hasClass(tmpX[i], "ball-act")) {
				removeClass(tmpY[i], "ball-act");
			}
		}
		delete tmpX;
		tmpX = undefined;
		delete tmpY;
		tmpY = undefined;
	}
	delete tmpList;
	tmpList = undefined;
	delete tn;
	tn = undefined;
	ballManager.init();
}

function ballNumOptionNumTotalDouble(ele) {
	// parentElement.children
	var tmpA;
	tmpA = ele.parentElement.getElementsByTagName("button");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		removeClass(tmpA[i1], "ball-act");
	}
	addClass(ele, "ball-act");
	tmpA = ele.parentElement.parentElement.parentElement.getElementsByClassName("ball-number");
	var add = 0;
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		var tmpB = tmpA[i1].getElementsByTagName("button");
		if (tmpB[0].innerHTML == "1") {
			add = 1;
		}
		for (var i2 = 0; i2 < tmpB.length; i2++) {
			var num = parseInt(tmpB[i2].innerHTML);
			num = (num / 10) + (num % 10);
			num = Math.floor(num);
			if ((num % 2) == 0) {
				addClass(tmpB[i2], "ball-act");
			} 
			else if(getEle("localId").value != ("" + XGLHC_ID)){
				removeClass(tmpB[i2], "ball-act");
			}
		}
		delete tmpB;
		tmpB = undefined;
	}
	delete tmpA;
	tmpA = undefined;
	var tmpList = [ , "前二组选胆托", "前三组选胆托", "任选二中二胆托", "任选三中三胆托", "任选四中四胆托", "任选五中五胆托", "任选六中五胆托", "任选七中五胆托", "任选八中五胆托", ];
	var tn = document.getElementsByClassName("bet-area")[0].getElementsByClassName("rule")[0].innerHTML;
	if (("," + tmpList.toString() + ",").indexOf("," + tn + ",") != -1) {
		var tmpX = document.getElementsByClassName("ball-number-promise")[0].getElementsByTagName("button");
		var tmpY = document.getElementsByClassName("ball-number-pull")[0].getElementsByTagName("button");
		for (var i = 0; i < tmpX.length; i++) {
			if (hasClass(tmpX[i], "ball-act")) {
				removeClass(tmpY[i], "ball-act");
			}
		}
		delete tmpX;
		tmpX = undefined;
		delete tmpY;
		tmpY = undefined;
	}
	delete tmpList;
	tmpList = undefined;
	delete tn;
	tn = undefined;
	ballManager.init();
}

function ballNumOptionNumZodiac(ele, zodiacType) {
	// parentElement.children
	var tmpA;
	tmpA = ele.parentElement.getElementsByTagName("button");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		removeClass(tmpA[i1], "ball-act");
	}
	addClass(ele, "ball-act");
	tmpA = ele.parentElement.parentElement.parentElement.getElementsByClassName("ball-number");
	var add = 0;
	var zodiacObj = getZodiac();
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		var tmpB = tmpA[i1].getElementsByTagName("button");
		if (tmpB[0].innerHTML == "1") {
			add = 1;
		}
		for (var i2 = 0; i2 < tmpB.length; i2++) {
			var num = parseInt(tmpB[i2].innerHTML);

			if (zodiacObj["" + num] == zodiacType) {
				addClass(tmpB[i2], "ball-act");
			} 
			else if(getEle("localId").value != ("" + XGLHC_ID)){
				removeClass(tmpB[i2], "ball-act");
			}
		}
		delete tmpB;
		tmpB = undefined;
	}
	delete tmpA;
	tmpA = undefined;
	var tmpList = [ , "前二组选胆托", "前三组选胆托", "任选二中二胆托", "任选三中三胆托", "任选四中四胆托", "任选五中五胆托", "任选六中五胆托", "任选七中五胆托", "任选八中五胆托", ];
	var tn = document.getElementsByClassName("bet-area")[0].getElementsByClassName("rule")[0].innerHTML;
	if (("," + tmpList.toString() + ",").indexOf("," + tn + ",") != -1) {
		var tmpX = document.getElementsByClassName("ball-number-promise")[0].getElementsByTagName("button");
		var tmpY = document.getElementsByClassName("ball-number-pull")[0].getElementsByTagName("button");
		for (var i = 0; i < tmpX.length; i++) {
			if (hasClass(tmpX[i], "ball-act")) {
				removeClass(tmpY[i], "ball-act");
			}
		}
		delete tmpX;
		tmpX = undefined;
		delete tmpY;
		tmpY = undefined;
	}
	delete tmpList;
	tmpList = undefined;
	delete tn;
	tn = undefined;
	ballManager.init();
}

function ballNumOptionNumColour(ele, colourType) {
	// red blue green
	// parentElement.children
	var tmpA;
	tmpA = ele.parentElement.getElementsByTagName("button");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		removeClass(tmpA[i1], "ball-act");
	}
	addClass(ele, "ball-act");
	tmpA = ele.parentElement.parentElement.parentElement.getElementsByClassName("ball-number");
	var add = 0;
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		var tmpB = tmpA[i1].getElementsByTagName("button");
		if (tmpB[0].innerHTML == "1") {
			add = 1;
		}
		for (var i2 = 0; i2 < tmpB.length; i2++) {
			if (hasClass(tmpB[i2], colourType)) {
				addClass(tmpB[i2], "ball-act");
			} 
			else if(getEle("localId").value != ("" + XGLHC_ID)){
				removeClass(tmpB[i2], "ball-act");
			}
		}
		delete tmpB;
		tmpB = undefined;
	}
	delete tmpA;
	tmpA = undefined;
	var tmpList = [ , "前二组选胆托", "前三组选胆托", "任选二中二胆托", "任选三中三胆托", "任选四中四胆托", "任选五中五胆托", "任选六中五胆托", "任选七中五胆托", "任选八中五胆托", ];
	var tn = document.getElementsByClassName("bet-area")[0].getElementsByClassName("rule")[0].innerHTML;
	if (("," + tmpList.toString() + ",").indexOf("," + tn + ",") != -1) {
		var tmpX = document.getElementsByClassName("ball-number-promise")[0].getElementsByTagName("button");
		var tmpY = document.getElementsByClassName("ball-number-pull")[0].getElementsByTagName("button");
		for (var i = 0; i < tmpX.length; i++) {
			if (hasClass(tmpX[i], "ball-act")) {
				removeClass(tmpY[i], "ball-act");
			}
		}
		delete tmpX;
		tmpX = undefined;
		delete tmpY;
		tmpY = undefined;
	}
	delete tmpList;
	tmpList = undefined;
	delete tn;
	tn = undefined;
	ballManager.init();
}

function ballNumOptionNumColourBig(ele, colourType) {
	// red blue green
	// parentElement.children
	var tmpA;
	tmpA = ele.parentElement.getElementsByTagName("button");
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		removeClass(tmpA[i1], "ball-act");
	}
	addClass(ele, "ball-act");
	tmpA = ele.parentElement.parentElement.parentElement.getElementsByClassName("ball-number");
	var add = 0;
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		var tmpB = tmpA[i1].getElementsByTagName("button");
		if (tmpB[0].innerHTML == "1") {
			add = 1;
		}
		var ballDataArrayList = [];
		var maxNum = -1;
		for (var i2 = 0; i2 < tmpB.length; i2++) {
			if (hasClass(tmpB[i2], colourType)) {
				var ballObj = {};
				var num = parseInt(tmpB[i2].innerHTML);

				if (maxNum < num) {
					maxNum = num;
				}

				ballObj["ele"] = tmpB[i2];
				ballObj["val"] = "" + num;
				ballDataArrayList.push(ballObj);
			}
			if (hasClass(tmpB[i2], "ball-act") && getEle("localId").value != ("" + XGLHC_ID)) {
				removeClass(tmpB[i2], "ball-act");
			}
		}
		for (var i3 = 0; i3 < ballDataArrayList.length; i3++) {

			var v = parseInt(ballDataArrayList[i3]["val"]);
			if (v >= Math.ceil(maxNum / 2)) {
				addClass(ballDataArrayList[i3]["ele"], "ball-act");
			}

		}
		delete tmpB;
		tmpB = undefined;
	}
	delete tmpA;
	tmpA = undefined;
	var tmpList = [ , "前二组选胆托", "前三组选胆托", "任选二中二胆托", "任选三中三胆托", "任选四中四胆托", "任选五中五胆托", "任选六中五胆托", "任选七中五胆托", "任选八中五胆托", ];
	var tn = document.getElementsByClassName("bet-area")[0].getElementsByClassName("rule")[0].innerHTML;
	if (("," + tmpList.toString() + ",").indexOf("," + tn + ",") != -1) {
		var tmpX = document.getElementsByClassName("ball-number-promise")[0].getElementsByTagName("button");
		var tmpY = document.getElementsByClassName("ball-number-pull")[0].getElementsByTagName("button");
		for (var i = 0; i < tmpX.length; i++) {
			if (hasClass(tmpX[i], "ball-act")) {
				removeClass(tmpY[i], "ball-act");
			}
		}
		delete tmpX;
		tmpX = undefined;
		delete tmpY;
		tmpY = undefined;
	}
	delete tmpList;
	tmpList = undefined;
	delete tn;
	tn = undefined;
	ballManager.init();
}

function ballNumOptionNumColourSmall(ele, colourType) {
	// red blue green
	// parentElement.children
	var tmpA;
	tmpA = ele.parentElement.getElementsByTagName("button");

	for (var i1 = 0; i1 < tmpA.length; i1++) {
		removeClass(tmpA[i1], "ball-act");
	}
	addClass(ele, "ball-act");
	tmpA = ele.parentElement.parentElement.parentElement.getElementsByClassName("ball-number");
	var add = 0;
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		var tmpB = tmpA[i1].getElementsByTagName("button");
		if (tmpB[0].innerHTML == "1") {
			add = 1;
		}
		var ballDataArrayList = [];
		var maxNum = -1;
		for (var i2 = 0; i2 < tmpB.length; i2++) {
			if (hasClass(tmpB[i2], colourType)) {
				var ballObj = {};
				var num = parseInt(tmpB[i2].innerHTML);

				if (maxNum < num) {
					maxNum = num;
				}

				ballObj["ele"] = tmpB[i2];
				ballObj["val"] = "" + num;
				ballDataArrayList.push(ballObj);
			}
			if (hasClass(tmpB[i2], "ball-act") && getEle("localId").value != ("" + XGLHC_ID)) {
				removeClass(tmpB[i2], "ball-act");
			}
		}
		for (var i3 = 0; i3 < ballDataArrayList.length; i3++) {

			var v = parseInt(ballDataArrayList[i3]["val"]);
			if (v < Math.ceil(maxNum / 2)) {
				addClass(ballDataArrayList[i3]["ele"], "ball-act");
			}

		}
		delete tmpB;
		tmpB = undefined;
	}
	delete tmpA;
	tmpA = undefined;
	var tmpList = [ , "前二组选胆托", "前三组选胆托", "任选二中二胆托", "任选三中三胆托", "任选四中四胆托", "任选五中五胆托", "任选六中五胆托", "任选七中五胆托", "任选八中五胆托", ];
	var tn = document.getElementsByClassName("bet-area")[0].getElementsByClassName("rule")[0].innerHTML;
	if (("," + tmpList.toString() + ",").indexOf("," + tn + ",") != -1) {
		var tmpX = document.getElementsByClassName("ball-number-promise")[0].getElementsByTagName("button");
		var tmpY = document.getElementsByClassName("ball-number-pull")[0].getElementsByTagName("button");
		for (var i = 0; i < tmpX.length; i++) {
			if (hasClass(tmpX[i], "ball-act")) {
				removeClass(tmpY[i], "ball-act");
			}
		}
		delete tmpX;
		tmpX = undefined;
		delete tmpY;
		tmpY = undefined;
	}
	delete tmpList;
	tmpList = undefined;
	delete tn;
	tn = undefined;
	ballManager.init();
}

function ballPlayInit() {
	// ball-play
	var tmpA = document.getElementsByClassName("ball-play");
	// console_Log(tmpA.length);

	for (var i1 = 0; i1 < tmpA.length; i1++) {
		var tmpB = tmpA[i1].getElementsByTagName("button");
		// console_Log(tmpB.length);
		for (var i2 = 0; i2 < tmpB.length; i2++) {
			if (tmpB[i2].innerHTML == '全') {
				tmpB[i2].onclick = function() {
					ballNumOptionAll(this);
				};
			}
			if (tmpB[i2].innerHTML == '清' || tmpB[i2].innerHTML == '清除所有选取') {
				tmpB[i2].onclick = function() {
					ballNumOptionClear(this);
				};
			}
			if (tmpB[i2].innerHTML == '大') {
				tmpB[i2].onclick = function() {
					ballNumOptionBig(this);
				};
			}
			if (tmpB[i2].innerHTML == '小') {
				tmpB[i2].onclick = function() {
					ballNumOptionSmall(this);
				};
			}
			if (tmpB[i2].innerHTML == '单') {
				tmpB[i2].onclick = function() {
					ballNumOptionSign(this);
				};
			}
			if (tmpB[i2].innerHTML == '双') {
				tmpB[i2].onclick = function() {
					ballNumOptionDouble(this);
				};
			}

			if (tmpB[i2].innerHTML == '合大') {
				tmpB[i2].onclick = function() {
					ballNumOptionNumTotalBig(this);
				};
			}
			if (tmpB[i2].innerHTML == '合小') {
				tmpB[i2].onclick = function() {
					ballNumOptionNumTotalSmall(this);
				};
			}
			if (tmpB[i2].innerHTML == '合单') {
				tmpB[i2].onclick = function() {
					ballNumOptionNumTotalSign(this);
				};
			}
			if (tmpB[i2].innerHTML == '合双') {
				tmpB[i2].onclick = function() {
					ballNumOptionNumTotalDouble(this);
				};
			}
			if (ZODIAC_NAME_ARRAY.indexOf(tmpB[i2].innerHTML) >= 0) {
				(function(tmpB,i2){
					tmpB[i2].onclick = function() {
						ballNumOptionNumZodiac(this, ZODIAC_ARRAY[ZODIAC_NAME_ARRAY.indexOf(tmpB[i2].innerHTML)]);
					};
				})(tmpB,i2);
			}
			if (tmpB[i2].innerHTML == '红波') {
				tmpB[i2].onclick = function() {
					ballNumOptionNumColour(this, "red");
				};
			}
			if (tmpB[i2].innerHTML == '红大') {
				tmpB[i2].onclick = function() {
					ballNumOptionNumColourBig(this, "red");
				};
			}
			if (tmpB[i2].innerHTML == '红小') {
				tmpB[i2].onclick = function() {
					ballNumOptionNumColourSmall(this, "red");
				};
			}
			if (tmpB[i2].innerHTML == '绿波') {
				tmpB[i2].onclick = function() {
					ballNumOptionNumColour(this, "green");
				};
			}
			if (tmpB[i2].innerHTML == '绿大') {
				tmpB[i2].onclick = function() {
					ballNumOptionNumColourBig(this, "green");
				};
			}
			if (tmpB[i2].innerHTML == '绿小') {
				tmpB[i2].onclick = function() {
					ballNumOptionNumColourSmall(this, "green");
				};
			}
			if (tmpB[i2].innerHTML == '蓝波') {
				tmpB[i2].onclick = function() {
					ballNumOptionNumColour(this, "blue");
				};
			}
			if (tmpB[i2].innerHTML == '蓝大') {
				tmpB[i2].onclick = function() {
					ballNumOptionNumColourBig(this, "blue");
				};
			}
			if (tmpB[i2].innerHTML == '蓝小') {
				tmpB[i2].onclick = function() {
					ballNumOptionNumColourSmall(this, "blue");
				};
			}

		}
		delete tmpB;
		tmpB = undefined;
	}
	delete tmpA;
	tmpA = undefined;
}

function getSetting() {
	enableLoadingPage();
	var tmpA = document.getElementsByName("lotterys");
	if (tmpA != undefined ? tmpA.length == 1 ? tmpA[0].value != "" : false : false) {
		var tmpJson = JSON.parse(Strings.decode(tmpA[0].value));
		var todo = 1;
		var l1 = document.getElementsByName("l1");
		var l2 = document.getElementsByName("l2");
		var l3 = document.getElementsByName("l3");
		var l4 = document.getElementsByName("l4");
		// console_Log("l1:"+l1[0].value);
		// console_Log("l2:"+l2[0].value);
		// console_Log("l3:"+l3[0].value);
		// console_Log("l4:"+l4[0].value);
		if (l3[0] && l3[0].value != "" && l4[0] && l4[0].value != "") {
			if (typeof l1[0].value === "undefined" || l1[0].value == "") {
				document.getElementsByName("l1")[0].value = 0;
				l1.value = 0;
			}
			if (typeof document.getElementsByName("l2")[0].value === "undefined" || document.getElementsByName("l2")[0].value == "") {
				document.getElementsByName("l2")[0].value = 0;
				l2.value = 0;
			}
		}
		// console_Log("xx l1:"+l1[0].value);
		// console_Log("x l2:"+l2[0].value);
		// console_Log("x l3:"+l3[0].value);
		// console_Log("xx l4:"+l4[0].value);
		if (l1 != undefined ? l1.length == 1 ? l1[0].value != "" : false : false) {
			l1 = parseInt(l1[0].value);
		} else {
			todo = 0;
		}
		if (l2 != undefined ? l2.length == 1 ? l2[0].value != "" : false : false) {
			l2 = parseInt(l2[0].value);
		} else {
			todo = 0;
		}
		if (l3 != undefined ? l3.length == 1 ? l3[0].value != "" : false : false) {
			l3 = parseInt(l3[0].value) + 1;
		} else {
			todo = 0;
		}
		if (l4 != undefined ? l4.length == 1 ? l4[0].value != "" : false : false) {
			l4 = parseInt(l4[0].value) + 1;
		} else {
			todo = 0;
		}

		// var lotteryTitleObj =
		// isJSON2(Strings.decode(getEle("allLotteryTitle").value));
		// if(lotteryTitleObj ? typeof
		// lotteryTitleObj["AllLotteryTitle"] !== "undefined" : false){
		// lotteryTitleObj = lotteryTitleObj["AllLotteryTitle"];
		// var k1 = Object.keys(lotteryTitleObj);
		// var k2 = Object.keys(lotteryTitleObj[k1[l1]]);
		//        	
		//        	
		// }
		//        
		if (todo == 1) {
			// console_Log("getSetting()");

			var lotteryLowfreq = 0;
			var zodiacType = 0;

			var obj1 = tmpJson["AllLottery"];
			// var key1 = Object.keys(obj1).sort();
			var obj2 = obj1[getEle("mainId").value];

			lotteryLowfreq = obj2["lottery_lowfreq"];
			zodiacType = obj2["zodiac_type"];
			// var key2 = Object.keys(obj2).sort();
			var obj3 = obj2[getEle("localId").value];
			var key3 = Object.keys(obj3).sort();
			var obj4 = obj3[key3[l3]];
			if (obj4) {
				// console_Log(" update functionName");
				var key4 = Object.keys(obj4).sort();
				var obj5 = obj4[key4[l4]];
				// console_Log(obj5["function_name"]);
				document.getElementsByName("functionName")[0].value = obj5["function_name"] ? obj5["function_name"] : "";
				document.getElementsByName("localId")[0].value = obj5["local_id"] ? obj5["local_id"] : "";
				document.getElementsByName("lotteryExample")[0].value = obj5["lottery_example"] ? obj5["lottery_example"] : "";
				document.getElementsByName("lotteryRule")[0].value = obj5["lottery_rule"] ? obj5["lottery_rule"] : "";
				document.getElementsByName("mainId")[0].value = obj5["main_id"] ? obj5["main_id"] : "";
				document.getElementsByName("midId")[0].value = obj5["mid_id"] ? obj5["mid_id"] : "";
				document.getElementsByName("minAuthId")[0].value = obj5["min_auth_id"] ? obj5["min_auth_id"] : "";
				document.getElementsByName("playedText")[0].value = obj5["played_text"] ? obj5["played_text"] : "";
				document.getElementsByName("totalNoOfBet")[0].value = obj5["total_no_of_bet"] ? obj5["total_no_of_bet"] : "";
				document.getElementsByName("playedId")[0].value = key4[l4] ? key4[l4].split("-")[1] : "";

				document.getElementsByName("lotteryLowfreq")[0].value = lotteryLowfreq;
				document.getElementsByName("zodiacType")[0].value = zodiacType;

				delete key4;
				delete obj5;
				key4 = undefined;
				obj5 = undefined;
				disableMainSetButton(lotteryLowfreq);

			} else {
				// console_Log("not update functionName");
			}
			delete obj1;
			delete key1;
			delete obj2;
			delete key2;
			delete obj3;
			delete key3;
			delete obj4;
			obj1 = undefined;
			key1 = undefined;
			obj2 = undefined;
			key2 = undefined;
			obj3 = undefined;
			key3 = undefined;
			obj4 = undefined;
		} else {
			// console_Log("not to do update functionName");
			// console_Log("l1:"+l1);
			// console_Log("l2:"+l2);
			// console_Log("l3:"+l3);
			// console_Log("l4:"+l4);
		}
		delete tmpJson;
		delete todo;
		delete l1;
		delete l2;
		delete l3;
		delete l4;
		tmpJson = undefined;
		todo = undefined;
		l1 = undefined;
		l2 = undefined;
		l3 = undefined;
		l4 = undefined;
	}
	delete tmpA;
	tmpA = undefined;
	disableLoadingPage();
}

function checkRatio() {
	var tmpA = document.getElementsByName("ratios");
	if (tmpA != undefined ? tmpA.length == 1 ? tmpA[0].value != "" : false : false) {
		var tmpJson = JSON.parse(Strings.decode(tmpA[0].value));
		var todo = 1;
		var l1 = document.getElementsByName("l1");
		var l2 = document.getElementsByName("l2");
		var l3 = document.getElementsByName("l3");
		var l4 = document.getElementsByName("l4");
		if (l1 != undefined ? l1.length == 1 ? l1[0].value != "" : false : false) {
			l1 = parseInt(l1[0].value);
		} else {
			todo = 0;
		}
		if (l2 != undefined ? l2.length == 1 ? l2[0].value != "" : false : false) {
			l2 = parseInt(l2[0].value);
		} else {
			todo = 0;
		}
		if (l3 != undefined ? l3.length == 1 ? l3[0].value != "" : false : false) {
			l3 = parseInt(l3[0].value);
		} else {
			todo = 0;
		}
		if (l4 != undefined ? l4.length == 1 ? l4[0].value != "" : false : false) {
			l4 = parseInt(l4[0].value);
		} else {
			todo = 0;
		}
		if (todo == 1) {
			var d = dateAddDay(0);
			var bd = dateAddDay(-1);
			var ad = dateAddDay(+1);
			var obj1 = tmpJson["CurrentBaseline"];
			var key1 = Object.keys(obj1).sort();
			var today = null;
			var before = null;
			var after = null;
			// console_Log("key1:"+key1.length);
			var tmpJ = {};
			for ( var key in key1) {
				// console_Log(key1[key]);
				tmpJ[key1[key].toString().replace("/", "").replace("/", "")] = obj1[key1[key]];
				if (key1[key].toString().indexOf("-" + d) != -1) {
					// console_Log("today");
					today = obj1[key1[key]];
				} else if (key1[key].toString().indexOf("-" + bd) != -1) {
					// console_Log("before");
					before = obj1[key1[key]];
				} else if (key1[key].toString().indexOf("-" + ad) != -1) {
					// console_Log("after");
					after = obj1[key1[key]];
				}
			}
			/*
			 * console_Log(bd); console_Log(d); console_Log(ad); var
			 * tmpJ = {}; if(today!=null){
			 * tmpJ[d.replace("/","").replace("/","")] = today; }
			 * if(before!=null){
			 * tmpJ[bd.replace("/","").replace("/","")] = before; }
			 * if(after!=null){
			 * tmpJ[ad.replace("/","").replace("/","")] = after; }
			 */
			var tmpKeys = Object.keys(tmpJ).sort();
			// console_Log("tmpKeys:"+tmpKeys.length);
			for ( var tmpkey in tmpKeys) {
				// console_Log(today);
				// /var obj2 = today!=null ? today :
				// before!=null ? before : after!=null ? after :
				// null;
				var obj2 = tmpJ[tmpKeys[tmpkey]];
				if (obj2 != null) {
					var key2 = Object.keys(obj2).sort();
					var obj3 = obj2[key2[l1]];
					var key3 = Object.keys(obj3).sort();
					var obj4 = obj3[key3[l2]];
					var key4 = Object.keys(obj4).sort();
					// console_Log(key4);
					// console_Log(l3);
					// console_Log("obj4:"+JSON.stringify(obj4));
					var obj5 = obj4[key4[l3]];
					// console_Log("obj5:"+JSON.stringify(obj5));
					if (obj5) {
						var key5 = Object.keys(obj5).sort();
						var obj6 = obj5[key5[l4]];
						// console_Log(obj6);
						// console_Log(obj6[Object.keys(obj6)[0]]);
						// document.getElementsByName("baseline")[0].value
						// =
						// obj6[Object.keys(obj6)[0]]["baseline"];
						// document.getElementsByName("dtBonus")[0].value
						// =
						// obj6[Object.keys(obj6)[0]]["dt_bonus"];
						// document.getElementsByName("dtRatio")[0].value
						// =
						// obj6[Object.keys(obj6)[0]]["dt_ratio"];
						// document.getElementsByName("dtSwitch")[0].value
						// =
						// obj6[Object.keys(obj6)[0]]["dt_swtch"];
						// //document.getElementsByName("baseline")[0].value
						// =
						// Strings.encode(JSON.stringify(obj6));
						tmpJ[tmpKeys[tmpkey]] = obj6;
						delete key5;
						delete obj6;
						key5 = undefined;
						obj6 = undefined;
					}
					delete key2;
					delete obj3;
					delete key3;
					delete obj4;
					delete key4;
					delete obj5;
					key2 = undefined;
					obj3 = undefined;
					key3 = undefined;
					obj4 = undefined;
					key4 = undefined;
					obj5 = undefined;
				}
			}
			document.getElementsByName("baseline")[0].value = Strings.encode(JSON.stringify(tmpJ));
			delete d;
			delete bd;
			delete ad;
			delete obj1;
			delete key1;
			delete today;
			delete before;
			delete after;
			delete obj2;
			d = undefined;
			bd = undefined;
			ad = undefined;
			obj1 = undefined;
			key1 = undefined;
			today = undefined;
			before = undefined;
			after = undefined;
			obj2 = undefined;
		}
		delete tmpJson;
		delete todo;
		delete l1;
		delete l2;
		delete l3;
		delete l4;
		tmpJson = undefined;
		todo = undefined;
		l1 = undefined;
		l2 = undefined;
		l3 = undefined;
		l4 = undefined;
	}
	delete tmpA;
	tmpA = undefined;
}

function clearBallToInit() {
	var tmpZ = document.getElementsByClassName("win-history");
	for (var i9 = 0; i9 < tmpZ.length; i9++) {
		addClass(tmpZ[i9], "hidden");
	}
	removeAllClasses("ball-act");
	var tmpA = document.getElementsByClassName("bet-area")[0].getElementsByTagName("textarea");
	for (var i = 0; i < tmpA.length; i++) {
		tmpA[i].value = "";
	}
	document.getElementsByName("mainOrder")[0].value = "";
	groupMainOrderObj = {};
	// document.getElementsByName("mainOrderList")[0].value = "";
	document.getElementsByName("areaContent")[0].value = "";
	document.getElementsByName("game-choice")[0].value = "";
	document.getElementsByName("entrance")[0].value = "";
	document.getElementsByName("middle-order")[0].value = "";
	var tmpA = document.getElementsByClassName("bet-area")[0].getElementsByTagName("input");
	for (var i = 0; i < tmpA.length; i++) {
		if (tmpA[i].type == "checkbox" && tmpA[i].checked) {
			tmpA[i].checked = false;
		}
	}
	delete tmpA;
	tmpA = undefined;
	document.getElementsByClassName('multiple-drop-menu')[0].getElementsByTagName('input')[0].value = '1';
	document.getElementsByClassName("bottom-area")[0].getElementsByTagName("select")[0].options[0].selected = true;
	document.getElementsByClassName("total")[0].getElementsByTagName("span")[0].innerHTML = "0";
	document.getElementsByClassName("total")[0].getElementsByTagName("span")[1].innerHTML = "0.00";
	document.getElementsByClassName("highlight")[0].innerHTML = "0.00";
	document.getElementsByClassName("bet")[0].getElementsByTagName("button")[0].disabled = true;
	document.getElementsByClassName("bet")[0].getElementsByTagName("button")[1].disabled = true;
	document.getElementsByClassName("bet")[0].getElementsByTagName("button")[0].title = "";
	document.getElementsByClassName("bet")[0].getElementsByTagName("button")[1].title = "";
	showMainOrderList();
	f_winHistory_timer(300);
}

function initHandicap() {
	if (document.getElementsByName("handicaps")[0].value != "") {
		var tmpJson = JSON.parse(Strings.decode(document.getElementsByName("handicaps")[0].value))["Handicap"];
		var tmpKey = Object.keys(tmpJson).sort().toString();
		var tmpA = document.getElementsByClassName("account")[0].getElementsByTagName("select")[0].getElementsByTagName("option");
		var st = -1;
		for (var i = 0; i < tmpA.length; i++) {
			if (tmpKey.indexOf(tmpA[i].value) == -1) {
				if (!hasClass(tmpA[i], "invisible")) {
					addClass(tmpA[i], "invisible");
				}
			} else {
				if (st == -1) {
					st = i;
				}
				if (hasClass(tmpA[i], "invisible")) {
					removeClass(tmpA[i], "invisible");
				}
			}
		}
		if (st != -1) {
			document.getElementsByClassName("account")[0].getElementsByTagName("select")[0].options[st].selected = true;
		}
		removeClass(document.getElementsByClassName("account")[0].getElementsByTagName("select")[0], "invisible");
	}
	document.getElementsByClassName("account")[0].getElementsByTagName("select")[0].onchange = function() {
		onchangeHandicap(this);
	};
	// document.getElementsByClassName("account")[0].getElementsByTagName("select")[0].onchange();
	// document.getElementsByClassName("account")[0].getElementsByTagName("select")[0].onchange();
	onchangeHandicap();
}

function onchangeHandicap(ele) {
	// console_Log(" in onchangeHandicap");
	if (document.getElementsByName("handicaps")[0].value != "") {
		if (ele == null || typeof ele === 'undefined' || !ele) {
			var tmpA = document.getElementsByClassName("account")[0].getElementsByTagName("select")[0].options;
			for (var i = 0; i < tmpA.length; i++) {
				if (!hasClass(tmpA[i], "invisible") && tmpA[i].selected) {
					ele = tmpA[i];
					break;
				}
			}
			if (ele == null || typeof ele === 'undefined' || !ele) {
				for (var i = 0; i < tmpA.length; i++) {
					if (!hasClass(tmpA[i], "invisible")) {
						tmpA[i].selected = true;
						ele = tmpA[i];
						break;
					}
				}
			}
		}
		var tmpJson = JSON.parse(Strings.decode(document.getElementsByName("handicaps")[0].value))["Handicap"];
		var tmpKey = Object.keys(tmpJson).sort();
		for (var i = 0; i < tmpKey.length; i++) {
			if (tmpKey[i].indexOf(ele.value) != -1) {
				document.getElementsByName("cap")[0].value = ele.value;
				document.getElementsByName("bonusSetMax")[0].value = tmpJson[tmpKey[i]]["bonus_set_max"];
				document.getElementsByName("bonusSetMin")[0].value = tmpJson[tmpKey[i]]["bonus_set_min"];
				document.getElementsByName("handicapId")[0].value = tmpJson[tmpKey[i]]["handicap_id"];
				document.getElementsByName("relativeBaseline")[0].value = tmpJson[tmpKey[i]]["relative_baseline"];

				document.getElementsByName("bonusSetMax")[0].value = parseInt(tmpJson[tmpKey[i]]["bonus_set_max"]);
				document.getElementsByName("bonusSetMin")[0].value = parseInt(tmpJson[tmpKey[i]]["bonus_set_min"]);
				document.getElementsByName("handicapName")[0].value = tmpJson[tmpKey[i]]["handicap_name"];
				document.getElementsByName("handicapId")[0].value = tmpJson[tmpKey[i]]["handicap_id"];
				document.getElementsByName("relativeBaseline")[0].value = tmpJson[tmpKey[i]]["relative_baseline"];
				document.getElementsByName("maxWinBonus")[0].value = tmpJson[tmpKey[i]]["max_win_bonus"];
				document.getElementsByClassName("bottom-area")[0].getElementsByTagName("output")[0].innerHTML = "0%";
				document.getElementsByClassName("bottom-area")[0].getElementsByTagName("output")[1].innerHTML = document.getElementsByName("bonusSetMax")[0].value;
				document.getElementsByClassName("bottom-area")[0].getElementsByTagName("input")[0].max = document.getElementsByName("bonusSetMax")[0].value;
				document.getElementsByClassName("bottom-area")[0].getElementsByTagName("input")[0].value = document.getElementsByName("bonusSetMax")[0].value;
				document.getElementsByClassName("bottom-area")[0].getElementsByTagName("input")[0].min = document.getElementsByName("bonusSetMin")[0].value;

				document.getElementsByClassName("lottery-number")[0].getElementsByClassName("winner")[0].getElementsByTagName("i")[0].innerHTML = "";
				document.getElementsByClassName("top-area")[0].getElementsByClassName("time-num")[0].innerHTML = "00<span>:</span>00<span>:</span>00";
				document.getElementsByName("mainOrder")[0].value = "";
				document.getElementsByName("mainOrderList")[0].value = "";
				groupMainOrderObj = {};
				groupMainOrderListObj = {};
				clearBallToInit();
				showMainOrderList();

				if (document.getElementsByClassName("lottery-number")[0].getElementsByClassName("winner")[0].getElementsByTagName("i")[0].innerHTML == "") {
					document.getElementsByClassName("lottery-number")[0].style.display = "none";
				} else {
					document.getElementsByClassName("lottery-number")[0].style.display = "block";
				}
				if (document.getElementsByClassName("top-area")[0].getElementsByClassName("record")[0].getElementsByTagName("span")[0].innerHTML == "") {
					document.getElementsByClassName("top-area")[0].getElementsByClassName("record")[0].style.display = "none";
					document.getElementsByClassName("top-area")[0].getElementsByClassName("bet-time")[0].style.display = "none";
					document.getElementsByClassName("top-area")[0].getElementsByClassName("time-num")[0].style.display = "none";
				} else {
					document.getElementsByClassName("top-area")[0].getElementsByClassName("record")[0].style.display = "block";
					document.getElementsByClassName("top-area")[0].getElementsByClassName("bet-time")[0].style.display = "block";
					document.getElementsByClassName("top-area")[0].getElementsByClassName("time-num")[0].style.display = "block";
				}
				// console_Log(tmpKey[i]);
				break;
			}
		}
	}
	document.getElementsByName("range001")[0].value = document.getElementsByName("range001")[0].max;
	document.getElementsByTagName("output")[0].innerHTML = "0%";
	document.getElementsByTagName("output")[1].innerHTML = document.getElementsByName("range001")[0].max;
	;
	var tmpStyle = document.createElement("style");
	tmpStyle.innerHTML = "input[type=range][data-rangeId='0']::-webkit-slider-runnable-track{background-size:100% 100%}"
	"+input[type=range][data-rangeId='0']::-moz-range-track{background-size:100% 100%}";
	tmpStyle.title = "style002";
	document.body.appendChild(tmpStyle);
	tmpStyle = document.getElementsByTagName("style");
	while (tmpStyle.length > 1) {
		document.body.removeChild(tmpStyle[1]);
		tmpStyle = document.getElementsByTagName("style");
	}
	delete tmpStyle;
	tmpStyle = undefined;
	styles = [], l = prefs.length, n = r.length;
	for (var i = 0; i < n; i++) {
		var s = document.createElement('style');
		document.body.appendChild(s);
		styles.push(s);
		r[i].setAttribute('data-rangeId', i);
		r[i].addEventListener('input', setDragStyleStr.bind(i));
	}
}

function checkHandicap(ele) {
	var cap = document.getElementsByName("cap")[0].value;
	if (ele) {
		ele.focus();
		ele.value = ele.options[selectedIndex].value;
		ele.options[selectedIndex].selected = true;
		cap = ele.value;
	}
	var tmpA = document.getElementsByName("handicaps")[0];
	if (tmpA != undefined ? tmpA.value != "" ? isJSON(Strings.decode(tmpA.value)) : false : false) {
		var tmpJson = JSON.parse(Strings.decode(tmpA.value));
		var todo = 1;
		// var cap = document.getElementsByName("cap")[0].value;
		/*
		 * if(cap!=undefined ? cap.length==1 ? cap[0].value!="" : false :
		 * false){ cap = parseInt(cap[0].value); console_Log(cap);
		 * }else{ todo=0; }
		 */
		if (todo == 1 && tmpJson["Handicap"]) {
			var obj1 = tmpJson["Handicap"];
			var obj2;
			var key1 = Object.keys(obj1).sort();
			for (var i = 0; i < key1.length; i++) {
				if (key1[i].indexOf(cap) != -1) {
					obj2 = obj1[key1[i]];
					document.getElementsByName("bonusSetMax")[0].value = parseInt(obj2["bonus_set_max"]);
					document.getElementsByName("bonusSetMin")[0].value = parseInt(obj2["bonus_set_min"]);
					document.getElementsByName("handicapName")[0].value = obj2["handicap_name"];
					document.getElementsByName("handicapId")[0].value = obj2["handicap_id"];
					document.getElementsByName("maxWinBonus")[0].value = obj2["max_win_bonus"];
					document.getElementsByName("relativeBaseline")[0].value = obj2["relative_baseline"];
					document.getElementsByClassName("bottom-area")[0].getElementsByTagName("output")[0].innerHTML = "0%";
					document.getElementsByClassName("bottom-area")[0].getElementsByTagName("output")[1].innerHTML = document.getElementsByName("bonusSetMax")[0].value;
					document.getElementsByClassName("bottom-area")[0].getElementsByTagName("input")[0].max = document.getElementsByName("bonusSetMax")[0].value;
					document.getElementsByClassName("bottom-area")[0].getElementsByTagName("input")[0].value = document.getElementsByName("bonusSetMax")[0].value;
					document.getElementsByClassName("bottom-area")[0].getElementsByTagName("input")[0].min = document.getElementsByName("bonusSetMin")[0].value;
					// console_Log(document.getElementsByClassName("bottom-area")[0].getElementsByTagName("input")[0]);

					break;
				}
			}
			// console_Log(obj2);
			// var obj2 = obj1[key1[cap]];

		}
		initHandicap();
	} else {
		setTimeout("f_lottory_Handicap();", 100);
	}
}

function getRanDomList(min, max, len) {
	var list = [];
	var len1 = (max - min + 1);
	if (typeof len != "number") {
		len = len1;
	}
	while (list.length < len) {
		var tt;
		if (min == 0) {
			tt = Math.floor(Math.random() * (max + 1));
		} else if (min == 1) {
			tt = Math.floor(Math.random() * max) + 1;
		}
		if (list.length >= len1 || ("," + list.toString() + ",").indexOf("," + tt + ",") == -1) {
			list[list.length] = tt;
		}
	}
	return list;
}

var showKjManager = (function() {
	var timers = [];
	return {
		addTimer : function(callback, timeout) {
			var timer, that = this;
			timer = setTimeout(function() {
				that.removeTimer(timer);
				callback();
			}, timeout);
			timers.push(timer);
			return timer;
		},
		removeTimer : function(timer) {
			clearTimeout(timer);
			timers.splice(timers.indexOf(timer), 1);
			if (timers.length == 0) {
				showKjWait();
			}
		},
		removeAll : function() {
			var that = this;
			for ( var i in timers) {
				that.removeTimer(timers[i]);
			}
			showKjWait();
		},
		getTimers : function() {
			return timers;
		}
	};
})();
function showKjTop() {
	if (document.getElementsByName("newData")[0].value.indexOf(",") != -1 ? document.getElementsByName("showedNo")[0].value != document.getElementsByName("newNo")[0].value : false) {
		// console_Log("nowNo:"+document.getElementsByName("nowNo")[0].value);
		// console_Log("nowS:"+document.getElementsByName("nowS")[0].value);
		// console_Log("nowE:"+document.getElementsByName("nowE")[0].value);
		// console_Log("oldNo:"+document.getElementsByName("oldNo")[0].value);
		// console_Log("oldData:"+document.getElementsByName("oldData")[0].value);
		// console_Log("newNo:"+document.getElementsByName("newNo")[0].value);
		// console_Log("newData:"+document.getElementsByName("newData")[0].value);
		// console_Log("showedNo:"+document.getElementsByName("showedNo")[0].value);

		document.getElementsByName("showedNo")[0].value = document.getElementsByName("newNo")[0].value;
		// console_Log("showedNo1:"+document.getElementsByName("showedNo")[0].value);
		var num = document.getElementsByName("newData")[0].value.split(",");
		var tmpA = document.getElementsByClassName("lottery-number")[0].getElementsByTagName("div");
		for (var i1 = 0; i1 < tmpA.length; i1++) {
			if (!hasClass(tmpA[i1], "invisible")) {
				document.getElementsByClassName("lottery-number")[0].getElementsByClassName("winner")[0].getElementsByTagName("i")[0].innerHTML = document.getElementsByName("newNo")[0].value;
				var cn = tmpA[i1].className;
				showKjManager.removeAll();
				if (hasClass(tmpA[i1], "three-dices")) {
					document.getElementsByClassName(cn)[0].innerHTML = '<span><i class="dice ani-dice ani-dice01"></i></span><span><i class="dice ani-dice ani-dice02"></i></span><span><i class="dice ani-dice ani-dice03"></i></span>';
					showKjManager.addTimer(function() {
						document.getElementsByClassName(cn)[0].getElementsByTagName("i")[0].className = "dice dice0" + parseInt(num[0]);
					}, 3000);
					showKjManager.addTimer(function() {
						document.getElementsByClassName(cn)[0].getElementsByTagName("i")[1].className = "dice dice0" + parseInt(num[1]);
					}, 3200);
					showKjManager.addTimer(function() {
						document.getElementsByClassName(cn)[0].getElementsByTagName("i")[2].className = "dice dice0" + parseInt(num[2]);
					}, 3000);
				} else {
					var tmpList = [];
					var tmpStr = "";
					if (num.length == 3) {
						tmpList = getRanDomList(1, 8, 3);
						for (var i = 0; i < 3; i++) {
							tmpStr += '<span class="ani-ball0' + tmpList[i] + '">&nbsp;</span>';
						}
						document.getElementsByClassName(cn)[0].innerHTML = tmpStr;
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[0].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[0].innerHTML = parseInt(num[0]);
						}, 3000);
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[1].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[1].innerHTML = parseInt(num[1]);
						}, 3200);
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[2].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[2].innerHTML = parseInt(num[2]);
						}, 3400);
					} else if (num.length == 5) {
						tmpList = getRanDomList(1, 8, 5);
						for (var i = 0; i < 5; i++) {
							tmpStr += '<span class="ani-ball0' + tmpList[i] + '">&nbsp;</span>';
						}
						document.getElementsByClassName(cn)[0].innerHTML = tmpStr;
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[0].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[0].innerHTML = parseInt(num[0]);
						}, 3000);
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[1].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[1].innerHTML = parseInt(num[1]);
						}, 3200);
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[2].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[2].innerHTML = parseInt(num[2]);
						}, 3400);
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[3].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[3].innerHTML = parseInt(num[3]);
						}, 3600);
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[4].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[4].innerHTML = parseInt(num[4]);
						}, 3800);
					} else if (num.length == 10) {
						tmpList = getRanDomList(1, 8, 10);
						for (var i = 0; i < 10; i++) {
							tmpStr += '<span class="ani-ball0' + tmpList[i] + '">&nbsp;</span>';
						}
						document.getElementsByClassName(cn)[0].innerHTML = tmpStr;
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[0].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[0].innerHTML = parseInt(num[0]);
						}, 3000);
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[1].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[1].innerHTML = parseInt(num[1]);
						}, 3100);
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[2].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[2].innerHTML = parseInt(num[2]);
						}, 3200);
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[3].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[3].innerHTML = parseInt(num[3]);
						}, 3300);
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[4].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[4].innerHTML = parseInt(num[4]);
						}, 3400);
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[5].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[5].innerHTML = parseInt(num[5]);
						}, 3500);
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[6].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[6].innerHTML = parseInt(num[6]);
						}, 3600);
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[7].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[7].innerHTML = parseInt(num[7]);
						}, 3700);
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[8].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[8].innerHTML = parseInt(num[8]);
						}, 3800);
						showKjManager.addTimer(function() {
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[9].className = "";
							document.getElementsByClassName(cn)[0].getElementsByTagName("span")[9].innerHTML = parseInt(num[9]);
						}, 3900);
					}
					else if (num.length == 7) {
						tmpList = getRanDomList(1, 7, 7);
						tmpStr += "<ul>";
						for (var i = 0; i < 7; i++) {
							if(i == 6){
								tmpStr += '<li><i class="plus">+</i></li>';
							}
							tmpStr += '<li><span class="blue ani-ballmarksix'+tmpList[i]+'">&nbsp;</span><p class="anicommon ani-animal'+tmpList[i]+'_dark">&nbsp;</p></li>';
						}
						tmpStr += "</ul>";
						document.getElementsByClassName(cn)[0].innerHTML = tmpStr;
						var zodiacNameObj = getZodiacName();
						var ballColourObj = getBallColour();
						showKjManager.addTimer(function() {
							var eleSpan = document.getElementsByClassName(cn)[0].getElementsByTagName("span")[0];
							var eleP = document.getElementsByClassName(cn)[0].getElementsByTagName("p")[0];
							eleSpan.className = ballColourObj[parseInt(num[0])];
							eleSpan.innerHTML = parseInt(num[0]);
							
							eleP.className = "";
							eleP.innerHTML = zodiacNameObj[num[0]];
						}, 3000);
						showKjManager.addTimer(function() {
							var eleSpan = document.getElementsByClassName(cn)[0].getElementsByTagName("span")[1];
							var eleP = document.getElementsByClassName(cn)[0].getElementsByTagName("p")[1];
							eleSpan.className = ballColourObj[parseInt(num[1])];
							eleSpan.innerHTML = parseInt(num[1]);
							
							eleP.className = "";
							eleP.innerHTML = zodiacNameObj[num[1]];
						}, 3200);
						showKjManager.addTimer(function() {
							var eleSpan = document.getElementsByClassName(cn)[0].getElementsByTagName("span")[2];
							var eleP = document.getElementsByClassName(cn)[0].getElementsByTagName("p")[2];
							eleSpan.className = ballColourObj[parseInt(num[2])];
							eleSpan.innerHTML = parseInt(num[2]);
							
							eleP.className = "";
							eleP.innerHTML = zodiacNameObj[num[2]];
						}, 3400);
						showKjManager.addTimer(function() {
							var eleSpan = document.getElementsByClassName(cn)[0].getElementsByTagName("span")[3];
							var eleP = document.getElementsByClassName(cn)[0].getElementsByTagName("p")[3];
							eleSpan.className = ballColourObj[parseInt(num[3])];
							eleSpan.innerHTML = parseInt(num[3]);
							
							eleP.className = "";
							eleP.innerHTML = zodiacNameObj[num[3]];
						}, 3600);
						showKjManager.addTimer(function() {
							var eleSpan = document.getElementsByClassName(cn)[0].getElementsByTagName("span")[4];
							var eleP = document.getElementsByClassName(cn)[0].getElementsByTagName("p")[4];
							eleSpan.className = ballColourObj[parseInt(num[4])];
							eleSpan.innerHTML = parseInt(num[4]);
							
							eleP.className = "";
							eleP.innerHTML = zodiacNameObj[num[4]];
						}, 3800);
						showKjManager.addTimer(function() {
							var eleSpan = document.getElementsByClassName(cn)[0].getElementsByTagName("span")[5];
							var eleP = document.getElementsByClassName(cn)[0].getElementsByTagName("p")[5];
							eleSpan.className = ballColourObj[parseInt(num[5])];
							eleSpan.innerHTML = parseInt(num[5]);
							
							eleP.className = "";
							eleP.innerHTML = zodiacNameObj[num[5]];
						}, 4000);
						showKjManager.addTimer(function() {
							var eleSpan = document.getElementsByClassName(cn)[0].getElementsByTagName("span")[6];
							var eleP = document.getElementsByClassName(cn)[0].getElementsByTagName("p")[6];
							eleSpan.className = ballColourObj[parseInt(num[6])];
							eleSpan.innerHTML = parseInt(num[6]);
							
							eleP.className = "";
							eleP.innerHTML = zodiacNameObj[num[6]];
						}, 4200);
						
					}
				}
				break;
			}
		}
	}
}

function showKjWait() {
	if (document.getElementsByName("newData")[0].value == "") {
		document.getElementsByName("newData")[0].value = "0";
		var tmpA = document.getElementsByClassName("lottery-number")[0].getElementsByTagName("div");
		for (var i1 = 0; i1 < tmpA.length; i1++) {
			if (!hasClass(tmpA[i1], "invisible")) {
				document.getElementsByClassName("lottery-number")[0].getElementsByClassName("winner")[0].getElementsByTagName("i")[0].innerHTML = document.getElementsByName("newNo")[0].value;
				var num = tmpA[i1].innerHTML.split("</span>");
				if (hasClass(tmpA[i1], "three-dices")) {
					tmpA[i1].innerHTML = '<span><i class="dice ani-dice ani-dice01"></i></span><span><i class="dice ani-dice ani-dice02"></i></span><span><i class="dice ani-dice ani-dice03"></i></span>';
				} else {
					var tmpList = [];
					var tmpStr = "";
					if (num.length == 4) {
						tmpList = getRanDomList(1, 8, 3);
						for (var i = 0; i < 3; i++) {
							tmpStr += '<span class="ani-ball0' + tmpList[i] + '">&nbsp;</span>';
						}
						tmpA[i1].innerHTML = tmpStr;
					} else if (num.length == 6) {
						tmpList = getRanDomList(1, 8, 5);
						for (var i = 0; i < 5; i++) {
							tmpStr += '<span class="ani-ball0' + tmpList[i] + '">&nbsp;</span>';
						}
						tmpA[i1].innerHTML = tmpStr;
					} else if (num.length == 11) {
						tmpList = getRanDomList(1, 8, 10);
						for (var i = 0; i < 10; i++) {
							tmpStr += '<span class="ani-ball0' + tmpList[i] + '">&nbsp;</span>';
						}
						tmpA[i1].innerHTML = tmpStr;
					}
				}
				break;
			}
		}
	}
}

var v_winHistory_timer = -1;
function f_winHistory_timer(ms) {
	if (typeof ms != "number") {
		ms = 30000;
	}
	clearTimeout(v_winHistory_timer);
	v_winHistory_timer = setTimeout("checkWinHistory();", ms);
}
function checkWinHistory() {

	/*
	 * if(getEle("subOrderInfos").value == ""){ if(v_lottory_xhr6!=null){
	 * v_lottory_xhr6.abort(); v_lottory_xhr6 = null; } f_lottory_SubInfo(); }
	 */
	// "win083"
	var json = {
		"1" : [ "win001", "win008", "win013", "win018", "win027", "win001", "win036", "win045", "win048" ],
		"2" : [ "win002", "win003", "win004", "win005", "win006", "win007", "win009", "win010", "win011", "win012", "win014", "win015", "win016", "win017", "win019", "win020", "win024",
			"win025", "win028", "win029", "win033", "win034", "win037", "win038", "win042", "win043", "win046", "win047", "win049", "win050", "win083" ],
		"3" : [ "win021", "win022", "win023", "win030", "win031", "win032", "win039", "win040", "win041" ],
		"4" : [ "win026", "win035", "win044" ],
		"5" : [ "win051", "win052" ],
		"6" : [ "win053", "win054", "win055", "win056", "win057", "win058", "win059", "win060", "win061", "win062" ],
		"7" : [ "win063", "win068", "win069" ], // 快三
		"8" : [ "win064", "win081", "win082" ], // 快三 和值
		"9" : [ "win065", "win066", "win067" ], // 福彩3D 组三 组六 混合组选
		"10" : [ "win070", "win071", "win072" ], // 福彩3D 龍虎和
		"11" : [ "win073" ], // PK10 冠亚 和值
		"12" : [ "win074" ], // PK10 冠亚 大小单双
		"13" : [ "win075", "win077", "win078", "win079", "win080" ], // PK10
		// 10
		"14" : [ "win076" ]
	// PK10 龙虎
	};
	var v1 = document.getElementsByName("l1")[0].value;
	var v2 = document.getElementsByName("l2")[0].value;
	if (v1 == "" || isNaN(v1) || v2 == "" || isNaN(v2) || document.getElementsByName("kjTimes")[0].value == "") {
		return false;
	}
	var tmpJ = JSON.parse(Strings.decode(document.getElementsByName("kjTimes")[0].value));
	if (!tmpJ || !tmpJ["KjTimeStatus"]) {
		f_kj_timer(0);
		return false;
	}
	tmpJ = tmpJ['KjTimeStatus'][getEle("localId").value];
	var key1 = Object.keys(tmpJ).sort();

	// tmpJ = tmpJ[key1[v1]];
	// key1 = Object.keys(tmpJ).sort();
	// tmpJ = tmpJ[key1[v2]];
	// key1 = Object.keys(tmpJ).sort();

	// console_Log(tmpJ);
	delete v1, v2;
	v1 = undefined;
	v2 = undefined;
	var kjNo = [];
	var kjData = [];
	var is1st = false;
	var j = 0;
	// console_Log("key1:"+key1);
	for (j; j < key1.length; j++) {
		var now = parseInt((new Date().getTime() - parseInt(document.getElementsByName("diffms")[0].value)) / 1000) + 3;
		// console_Log("now:"+now);
		if (parseInt(tmpJ[key1[j]]['e']) >= now && parseInt(tmpJ[key1[j]]['exp_s']) <= now) {
			is1st = true;
			// console_Log("now NO:"+tmpJ[key1[j]]['p_n']);
			// console_Log("now exp_s:"+tmpJ[key1[j]]['exp_s'] + " "
			// + new Date(parseInt(tmpJ[key1[j]]['exp_s'])*1000));
			// console_Log("now NO:"+new Date().getTime() + " " +
			// new Date());
			// console_Log("now e:"+tmpJ[key1[j]]['e'] + " " + new
			// Date(parseInt(tmpJ[key1[j]]['e'])*1000));
			// console_Log(tmpJ[key1[j]]);
			document.getElementsByName("nowNo")[0].value = tmpJ[key1[j]]['p_n'];
			document.getElementsByName("nowS")[0].value = tmpJ[key1[j]]['exp_s'];
			document.getElementsByName("nowE")[0].value = tmpJ[key1[j]]['e'];
			document.getElementsByName("nowDate")[0].value = tmpJ[key1[j]]['date'];
			document.getElementsByClassName("top-area")[0].getElementsByClassName("record")[0].getElementsByTagName("span")[0].innerHTML = document.getElementsByName("nowNo")[0].value;
			if ((j + 1) < key1.length) {
				document.getElementsByName("nextNo")[0].value = tmpJ[key1[j + 1]]['p_n'];
				document.getElementsByName("nextDate")[0].value = tmpJ[key1[j + 1]]['date'];
			} else {
				document.getElementsByName("nextNo")[0].value = "";
				document.getElementsByName("nextDate")[0].value = "";
			}
			j--;
			// console_Log(" j:"+ j);
			if (j >= 0) {
				if (document.getElementsByName("oldNo")[0].value == "" ? document.getElementsByName("newNo")[0].value == "" : false) {
					document.getElementsByName("newNo")[0].value = tmpJ[key1[j]]['p_n'];
					if (tmpJ[key1[j]]['data'] ? tmpJ[key1[j]]['data'].indexOf(",") != -1 : false) {
						document.getElementsByName("newData")[0].value = tmpJ[key1[j]]['data'];
						showKjTop();
					} else {
						document.getElementsByName("newData")[0].value = "";
						showKjWait();
					}
					// } else
					// if(document.getElementsByName("oldNo")[0].value
					// == "" ?
					// document.getElementsByName("newNo")[0].value
					// != "" : false){
				} else {
					if (document.getElementsByName("newNo")[0].value == tmpJ[key1[j]]['p_n']) {
						if (document.getElementsByName("newData")[0].value == "0" ? tmpJ[key1[j]]['data'].indexOf(",") != -1 : false) {
							document.getElementsByName("newData")[0].value = tmpJ[key1[j]]['data'];
							showKjTop();
						} else {
							if (document.getElementsByName("showedNo")[0].value != tmpJ[key1[j]]['p_n']) {
								document.getElementsByName("newData")[0].value = "";
								showKjWait();
							}
						}
					} else {
						document.getElementsByName("oldNo")[0].value = document.getElementsByName("newNo")[0].value;
						document.getElementsByName("oldData")[0].value = document.getElementsByName("newData")[0].value;
						document.getElementsByName("newNo")[0].value = tmpJ[key1[j]]['p_n'];
						if (tmpJ[key1[j]]['data'] ? tmpJ[key1[j]]['data'].indexOf(",") != -1 : false) {
							document.getElementsByName("newData")[0].value = tmpJ[key1[j]]['data'];
							showKjTop();
						} else {
							document.getElementsByName("newData")[0].value = "";
							if (document.getElementsByName("showedNo")[0].value != tmpJ[key1[j]]['p_n']) {
								document.getElementsByName("newData")[0].value = "";
								showKjWait();
							}
						}
					}
				}
			} else {
				document.getElementsByName("showedNo")[0].value = "";
				document.getElementsByName("oldNo")[0].value = "";
				document.getElementsByName("newNo")[0].value = "";
				document.getElementsByName("oldData")[0].value = "";
				document.getElementsByName("newData")[0].value = "";
				document.getElementsByClassName("lottery-number")[0].getElementsByClassName("winner")[0].getElementsByTagName("i")[0].innerHTML = "";
			}
			break;
		}
	}
	if (is1st == false) {
		document.getElementsByName("nowNo")[0].value = "";
		document.getElementsByName("nowS")[0].value = "";
		document.getElementsByName("nowE")[0].value = "";
		document.getElementsByName("nowDate")[0].value = "";
		document.getElementsByClassName("top-area")[0].getElementsByClassName("record")[0].getElementsByTagName("span")[0].innerHTML = "";
		document.getElementsByClassName("top-area")[0].getElementsByClassName("time-num")[0].innerHTML = "00<span>:</span>00<span>:</span>00";
	}
	if (document.getElementsByClassName("lottery-number")[0].getElementsByClassName("winner")[0].getElementsByTagName("i")[0].innerHTML == "") {
		document.getElementsByClassName("lottery-number")[0].style.display = "none";
	} else {
		document.getElementsByClassName("lottery-number")[0].style.display = "block";
	}
	if (document.getElementsByClassName("top-area")[0].getElementsByClassName("record")[0].getElementsByTagName("span")[0].innerHTML == "") {
		document.getElementsByClassName("top-area")[0].getElementsByClassName("record")[0].style.display = "none";
		document.getElementsByClassName("top-area")[0].getElementsByClassName("bet-time")[0].style.display = "none";
		document.getElementsByClassName("top-area")[0].getElementsByClassName("time-num")[0].style.display = "none";
	} else {
		document.getElementsByClassName("top-area")[0].getElementsByClassName("record")[0].style.display = "block";
		document.getElementsByClassName("top-area")[0].getElementsByClassName("bet-time")[0].style.display = "block";
		document.getElementsByClassName("top-area")[0].getElementsByClassName("time-num")[0].style.display = "block";
	}
	/*
	 * if(j<10 && key1.length>10){ j = 9; } if(key1.length<=10 ||
	 * (j+1)>key1.length){ j = key1.length-1; }
	 */
	for (j; j >= 0; j--) {
		var now = parseFloat((new Date().getTime()) / 1000);
		// console_Log("##############:"+key1[j]);
		// console_Log(tmpJ[key1[j]]);
		if (tmpJ[key1[j]] && typeof tmpJ[key1[j]]['p_n'] != "undefined") {
			kjNo[kjNo.length] = tmpJ[key1[j]]['p_n'];
		} else {
			kjNo[kjNo.length] = "";
		}
		// kjNo[kjNo.length] = tmpJ[key1[j]]['p_n'];
		// kjData[kjData.length] = tmpJ[key1[j]]['data'];
		if (tmpJ[key1[j]] && typeof tmpJ[key1[j]]['data'] != "undefined") {
			kjData[kjData.length] = tmpJ[key1[j]]['data'];
		} else {
			kjData[kjData.length] = "";
		}
		if (kjNo.length >= 10) {
			break;
		}
	}
	if (kjNo.length == 0) {
		// document.getElementsByName("nowNo")[0].value = "";
		// document.getElementsByName("nowS")[0].value = "";
		// document.getElementsByName("nowE")[0].value = "";
		for (var j = key1.length - 1; j >= 0; j--) {
			if (key1.length <= 10 || (key1.length > 10 && parseFloat(tmpJ[key1[j]]['e']) >= now) || ((j + 1) - kjNo.length) <= 10) {
				if (kjNo.length < 10) {
					kjNo[kjNo.length] = tmpJ[key1[j]]['p_n'];
					kjData[kjData.length] = tmpJ[key1[j]]['data'];
				} else if (kjNo.length >= 10) {
					break;
				}
			}
		}
	}
	delete is1st;
	is1st = undefined;
	// console_Log("kjNo.length:"+kjNo.length);
	var tmpA = document.getElementsByClassName("win-history");
	var tmpStr = "<tbody>";
	for (var i1 = 0; i1 < tmpA.length; i1++) {
		// console_Log(tmpA[i1].className);
		var cn = tmpA[i1].className.replace("win-history ", "").replace("hidden ", "").split(" ")[0];
		// console_Log(cn);
		if (!hasClass(tmpA[i1], "invisible")) {
			// console_Log(tmpA[i1].className);
			// console_Log(cn);
			for (var i2 = 1; i2 <= 17; i2++) {
				if (json['' + i2].toString().indexOf(cn) != -1) {
					// console_Log(i2+":"+json[''+i2].toString());
					var tmpB = tmpA[i1].getElementsByTagName("tr");
					var tn2 = document.getElementsByClassName("pick-area")[0].getElementsByClassName("pick-act")[0].innerHTML;
					var tn = document.getElementsByClassName("bet-area")[0].getElementsByClassName("rule")[0].innerHTML;
					// console_Log(tn);
					if (i2 == 1) {
						// var num = "54387".split("");
						tmpStr += tmpB[0].outerHTML;
						tmpStr += tmpB[1].outerHTML;
						tmpStr += tmpB[2].outerHTML;
						tmpStr += tmpB[3].outerHTML;
						// show number
						for (var i3 = 0; i3 < kjNo.length; i3++) {
							if (kjData[i3] ? kjData[i3] != "" ? kjData[i3] : false : false) {
								var num = kjData[i3].split(",");
								// console_Log(num);
								var tt = tmpB[3].outerHTML.split("</span>");
								tmpStr += (tt[0].substring(0, tt[0].indexOf("<td>") + 4) + kjNo[i3] + tt[0].substring(tt[0].indexOf("</td>"), tt[0].length - 1))
										.replace('class="invisible"', '')
										+ parseInt(num[0]) + "</span>";
								tmpStr += tt[1].substring(0, tt[1].length - 1) + parseInt(num[1]) + "</span>";
								tmpStr += tt[2].substring(0, tt[2].length - 1) + parseInt(num[2]) + "</span>";
								tmpStr += tt[3].substring(0, tt[3].length - 1) + parseInt(num[3]) + "</span>";
								tmpStr += tt[4].substring(0, tt[4].length - 1) + parseInt(num[4]) + "</span>";
								tmpStr += tt[5];
							} else {
								tmpStr += tmpB[2].outerHTML.replace('class="invisible"', '').replace('000', kjNo[i3]);
							}
						}
						if (kjNo.length < 10) {
							for (var i3 = 0; i3 < (10 - kjNo.length); i3++) {
								tmpStr += '<tr><td colspan="' + (tmpB[2].innerHTML.split("<td").length - 1) + '">&nbsp;</td></tr>';
							}
						}
						// tmpStr +=
						// tmpB[3].outerHTML.replace("invisible","");
						tmpStr += tmpB[tmpB.length - 1].outerHTML;
						tmpStr += "</tbody>";
						// tmpA[i1].innerHTML = tmpStr;
						tmpA[i1].outerHTML = '<table class="' + tmpA[i1].className + '">' + tmpStr + "</table>";
						tmpA[i1].getElementsByTagName("button")[0].onclick = function() {
							onclickMidBtn();
						};
						tmpA[i1].getElementsByTagName("button")[1].onclick = function() {
							onclickMainBtn();
						};
						break;
					} else if (i2 == 2) {
						// var num = "54387".split("");
						var todo = 0;
						var type = parseInt(tmpB[1].outerHTML.substring(tmpB[1].outerHTML.lastIndexOf("<td>") + 4, tmpB[1].outerHTML.lastIndexOf("</td>")));
						if (isNaN(type)) {
							type = tmpB[1].outerHTML.substring(tmpB[1].outerHTML.lastIndexOf("<td>") + 4, tmpB[1].outerHTML.lastIndexOf("</td>"));
						}
						var tmpList = {};
						var i3 = 0;
						var i3len = 5;
						tmpStr += tmpB[0].outerHTML;
						tmpStr += tmpB[1].outerHTML;
						tmpStr += tmpB[2].outerHTML;
						tmpStr += tmpB[3].outerHTML;
						for (var i5 = 0; i5 < kjNo.length; i5++) {
							tmpList = {};
							if (kjData[i5] ? kjData[i5] != "" ? kjData[i5] : false : false) {
								tmpList = {};
								i3 = 0;
								i3len = 5;
								todo = 0;
								var num = kjData[i5].split(",");
								// console_Log(num);
								for (i3; i3 < i3len; i3++) {
									if (!tmpList["" + num[i3]]) {
										tmpList["" + num[i3]] = 1;
									} else {
										tmpList["" + num[i3]] = tmpList["" + num[i3]] + 1;
									}
								}

								var keys = Object.keys(tmpList);
								if (type == "总和") {
									var lotteryNumTotal = parseInt(num[0]) + parseInt(num[1]) + parseInt(num[2]) + parseInt(num[3]) + parseInt(num[4]);
									var isBig = false;
									var isSingle = false;

									if (lotteryNumTotal >= 25) {
										isBig = true;
									}
									if ((lotteryNumTotal % 2) > 0) {
										isSingle = true;
									}

									if (isBig == true) {
										todo = "大";
									} else {
										todo = "小";
									}

									if (isSingle == true) {
										todo += "單";
									} else {
										todo += "雙";
									}

									delete lotteryNumTotal;
									delete isBig;
									delete isSingle;
									lotteryNumTotal = undefined;
									isBig = undefined;
									isSingle = undefined;
								} else if (type == 120 || type == 60 || type == 30 || type == 20 || type == 10 || type == 5) {
									if (type == 120 ? keys.length == 5 : false) {
										todo = 120;
									} else if (type == 60 ? keys.length == 4 : false) {
										todo = 60;
									} else if (type == 30 ? keys.length == 3 : false) {
										for ( var key in keys) {
											if (tmpList[keys[key]] == 2) {
												todo++;
											}
										}
										if (todo == 2) {
											todo = 30;
										} else {
											todo = 0;
										}
									} else if (type == 20 ? keys.length == 3 : false) {
										for ( var key in keys) {
											if (tmpList[keys[key]] == 3) {
												todo++;
											}
										}
										if (todo == 1) {
											todo = 20;
										} else {
											todo = 0;
										}
									} else if (type == 10 ? keys.length == 2 : false) {
										for ( var key in keys) {
											if (tmpList[keys[key]] == 3) {
												todo++;
											}
										}
										if (todo == 1) {
											todo = 10;
										} else {
											todo = 0;
										}
									} else if (type == 5 ? keys.length == 2 : false) {
										for ( var key in keys) {
											if (tmpList[keys[key]] == 4) {
												todo++;
											}
										}
										if (todo == 1) {
											todo = 5;
										} else {
											todo = 0;
										}
									}
								} else if (type == 24 || type == 12 || type == 6 || type == 4) {
									todo = 0;
									i3 = 0;
									i3len = 4;
									if (tmpB[3].outerHTML.split("</span>")[0].indexOf('<span class="active">') != -1) {
										i3 = 0;
									} else {
										i3 = 1;
										i3len++;
									}

									tmpList = {};
									for (i3; i3 < i3len; i3++) {
										if (!tmpList["" + num[i3]]) {
											tmpList["" + num[i3]] = 1;
										} else {
											tmpList["" + num[i3]] = tmpList["" + num[i3]] + 1;
										}
									}
									keys = Object.keys(tmpList);
									if (type == 24 ? keys.length == 4 : false) {
										todo = 24;
									} else if (type == 12 ? keys.length == 3 : false) {
										todo = 12;
									} else if (type == 6 ? keys.length == 2 : false) {
										for ( var key in keys) {
											if (tmpList[keys[key]] == 2) {
												todo++;
											}
										}
										if (todo == 2) {
											todo = 6;
										} else {
											todo = 0;
										}
									} else if (type == 4 ? keys.length == 2 : false) {
										for ( var key in keys) {
											if (tmpList[keys[key]] == 3) {
												todo++;
											}
										}
										if (todo == 1) {
											todo = 4;
										} else {
											todo = 0;
										}
									}
								} else if (type == "和值" || type == "和尾" || type == "跨度") {
									// console_Log("todo:和值:"+tn);
									todo = 0;
									i3 = 0;
									i3len = 0;
									var tmpL = tmpB[3].outerHTML.split('</span>');
									if (tmpB[3].outerHTML.split('<span class="active">').length == 3) {
										i3 = 0;
										i3len = 2;
										if (tmpL[0].indexOf('<span class="active">') != -1 && tmpL[1].indexOf('<span class="active">') != -1) {
											// console_Log("F2"+type);
											i3 = 0;
											i3len = 2;
											todo = parseInt(num[0]) + parseInt(num[1]);
											if (tn.indexOf("组选") != -1 && num[0] == num[1]) {
												todo = "-";
											} else if (type == "跨度") {
												var tmpNums = [];
												tmpNums[0] = num[0];
												tmpNums[1] = num[1];
												tmpNums.sort();
												todo = parseInt(tmpNums[1]) - parseInt(tmpNums[0]);
												delete tmpNums;
												tmpNums = undefined;
											}
										} else if (tmpL[3].indexOf('<span class="active">') != -1 && tmpL[4].indexOf('<span class="active">') != -1) {
											// console_Log("B2"+type);
											i3 = 3;
											i3len = 5;
											todo = parseInt(num[3]) + parseInt(num[4]);
											// console_Log("todo:"+todo);
											if (tn.indexOf("组选") != -1 && num[3] == num[4]) {
												todo = "-";
											} else if (type == "跨度") {
												var tmpNums = [];
												tmpNums[0] = num[3];
												tmpNums[1] = num[4];
												tmpNums.sort();
												todo = parseInt(tmpNums[1]) - parseInt(tmpNums[0]);
												delete tmpNums;
												tmpNums = undefined;
											}
										} else {
											break;
										}
									} else if (tmpB[3].outerHTML.split('<span class="active">').length == 4) {
										// console_Log("3燈"+type);
										i3 = 0;
										i3len = 3;
										if (tmpL[0].indexOf('<span class="active">') != -1 && tmpL[1].indexOf('<span class="active">') != -1
												&& tmpL[2].indexOf('<span class="active">') != -1) {
											// console_Log("F3"+type);
											i3 = 0;
											i3len = 3;
											todo = parseInt(num[0]) + parseInt(num[1]) + parseInt(num[2]);
											if (tn.indexOf("组选") != -1 && num[0] == num[1] && num[1] == num[2]) {
												todo = "-";
											} else if (type == "和值尾数") {
												todo = "" + todo;
												todo = todo.substring(todo.length - 1, todo.length);
											} else if (type == "跨度") {
												var tmpNums = [];
												tmpNums[0] = num[0];
												tmpNums[1] = num[1];
												tmpNums[2] = num[2];
												tmpNums.sort();
												todo = parseInt(tmpNums[2]) - parseInt(tmpNums[0]);
												delete tmpNums;
												tmpNums = undefined;
											}
										} else if (tmpL[1].indexOf('<span class="active">') != -1 && tmpL[2].indexOf('<span class="active">') != -1
												&& tmpL[3].indexOf('<span class="active">') != -1) {
											// console_Log("M3"+type);
											i3 = 1;
											i3len = 4;
											todo = parseInt(num[1]) + parseInt(num[2]) + parseInt(num[3]);
											if (tn.indexOf("组选") != -1 && num[1] == num[2] && num[2] == num[3]) {
												todo = "-";
											} else if (type == "和值尾数") {
												todo = "" + todo;
												todo = todo.substring(todo.length - 1, todo.length);
											} else if (type == "跨度") {
												var tmpNums = [];
												tmpNums[0] = num[1];
												tmpNums[1] = num[2];
												tmpNums[2] = num[3];
												tmpNums.sort();
												todo = parseInt(tmpNums[2]) - parseInt(tmpNums[0]);
												delete tmpNums;
												tmpNums = undefined;
											}
										} else if (tmpL[2].indexOf('<span class="active">') != -1 && tmpL[3].indexOf('<span class="active">') != -1
												&& tmpL[4].indexOf('<span class="active">') != -1) {
											// console_Log("B3"+type);
											i3 = 2;
											i3len = 5;
											todo = parseInt(num[2]) + parseInt(num[3]) + parseInt(num[4]);
											if (tn.indexOf("组选") != -1 && num[2] == num[3] && num[3] == num[4]) {
												todo = "-";
											} else if (type == "和值尾数") {
												todo = "" + todo;
												todo = todo.substring(todo.length - 1, todo.length);
											} else if (type == "跨度") {
												var tmpNums = [];
												tmpNums[0] = num[2];
												tmpNums[1] = num[3];
												tmpNums[2] = num[4];
												tmpNums.sort();
												todo = parseInt(tmpNums[2]) - parseInt(tmpNums[0]);
												delete tmpNums;
												tmpNums = undefined;
											}
										} else {
											break;
										}
									}
								}
								delete i3;
								i3 = undefined;
								delete i3len;
								i3len = undefined;
								// var keys =
								// Object.keys(tmpList);
								if (todo == 0 && tn.indexOf("直选跨度") == -1 && tn.indexOf("直选和值")) {
									todo = "-";
								}
								// console_Log("todo:"+todo);
								// show number
								var tt = tmpB[3].outerHTML.split("</span>");
								tmpStr += (tt[0].substring(0, tt[0].indexOf("<td>") + 4) + kjNo[i5] + tt[0].substring(tt[0].indexOf("</td>"), tt[0].length - 1))
										.replace('class="invisible"', '')
										+ parseInt(num[0]) + "</span>";
								tmpStr += tt[1].substring(0, tt[1].length - 1) + parseInt(num[1]) + "</span>";
								tmpStr += tt[2].substring(0, tt[2].length - 1) + parseInt(num[2]) + "</span>";
								tmpStr += tt[3].substring(0, tt[3].length - 1) + parseInt(num[3]) + "</span>";
								tmpStr += tt[4].substring(0, tt[4].length - 1) + parseInt(num[4]) + "</span>";
								if (tt.length == 7) {
									tmpStr += (tt[5].substring(0, tt[5].lastIndexOf(">") + 1) + todo + "</span>").replace('<span class="active">-</span>', "-")
											.replace("<span>-</span>", "-");
									tmpStr += tt[6];
								} else {
									tmpStr += tt[5].substring(0, tt[5].lastIndexOf("<td>") + 4) + todo + tt[5].substring(tt[5].lastIndexOf("</td>"), tt[5].length);
								}
								// tmpStr +=
								// tmpB[3].outerHTML.replace("invisible","");
							} else {
								tmpStr += tmpB[2].outerHTML.replace('class="invisible"', '').replace('000', kjNo[i5]);
							}
						}
						if (kjNo.length < 10) {
							for (var i5 = 0; i5 < (10 - kjNo.length); i5++) {
								tmpStr += '<tr><td colspan="' + (tmpB[2].innerHTML.split("<td").length - 1) + '">&nbsp;</td></tr>';
							}
						}
						tmpStr += tmpB[tmpB.length - 1].outerHTML;
						tmpStr += "</tbody>";
						// console_Log(tmpStr);
						// tmpA[i1].innerHTML = tmpStr;
						tmpA[i1].outerHTML = '<table class="' + tmpA[i1].className + '">' + tmpStr + "</table>";
						tmpA[i1].getElementsByTagName("button")[0].onclick = function() {
							onclickMidBtn();
						};
						tmpA[i1].getElementsByTagName("button")[1].onclick = function() {
							onclickMainBtn();
						};
						break;
					} else if (i2 == 3) {
						// 组三 组六 混合组选
						// console_Log(3);
						// var num = "54387".split("");
						tmpStr += tmpB[0].outerHTML;
						tmpStr += tmpB[1].outerHTML;
						tmpStr += tmpB[2].outerHTML;
						tmpStr += tmpB[3].outerHTML;
						// console_Log(tmpB[3].outerHTML);
						// show number
						for (var i3 = 0; i3 < kjNo.length; i3++) {
							if (kjData[i3] ? kjData[i3] != "" ? kjData[i3] : false : false) {
								var num = kjData[i3].split(",");
								// console_Log(num);
								var todo = 0;
								var checkTab = tmpB[3].outerHTML.split("</span>");
								var tmpList = [];
								for (var i4 = 0; i4 < 5; i4++) {
									if (checkTab[i4].indexOf('<span class="active">') != -1) {
										tmpList[tmpList.length] = parseInt(num[i4]);
									}
								}
								delete checkTab;
								checkTab = undefined;
								// console_Log("tmpList.length:"+tmpList.length);
								if (tmpList.length != 3) {
									todo = "-";
									break;
								} else {
									tmpList = tmpList.sort();
									if (tmpList[0] == tmpList[1] ? tmpList[1] == tmpList[2] : false) {
										if (tn.indexOf("组选") != -1) {
											todo = '豹子';
										} else {
											todo = "-"
										}
									} else if ((tmpList[0] == tmpList[1] && tmpList[1] != tmpList[2]) || (tmpList[0] != tmpList[1] && tmpList[1] == tmpList[2])) {
										todo = '组三';
									} else {
										todo = '组六';
									}
									if ((tn.indexOf("组三") != -1 || tn2.indexOf("组选3") != -1) && todo == "组三") {
										todo = '<span class="active">组三</span>';
									} else if ((tn.indexOf("组六") != -1 || tn2.indexOf("组选6") != -1) && todo == "组六") {
										todo = '<span class="active">组六</span>';
									}
								}
								delete tmpList;
								tmpList = undefined;
								var tt = tmpB[3].outerHTML.split("</span>");
								tmpStr += (tt[0].substring(0, tt[0].indexOf("<td>") + 4) + kjNo[i3] + tt[0].substring(tt[0].indexOf("</td>"), tt[0].length - 1))
										.replace('class="invisible"', '')
										+ parseInt(num[0]) + "</span>";
								tmpStr += tt[1].substring(0, tt[1].length - 1) + parseInt(num[1]) + "</span>";
								tmpStr += tt[2].substring(0, tt[2].length - 1) + parseInt(num[2]) + "</span>";
								tmpStr += tt[3].substring(0, tt[3].length - 1) + parseInt(num[3]) + "</span>";
								tmpStr += tt[4].substring(0, tt[4].length - 1) + parseInt(num[4]) + "</span>";
								if (tt.length == 7) {
									tmpStr += tt[5].substring(0, tt[5].lastIndexOf("<td>") + 4) + todo + tt[6];
								} else {
									tmpStr += tt[5].substring(0, tt[5].lastIndexOf("<td>") + 4) + todo + tt[5].substring(tt[5].lastIndexOf("</td>"), tt[5].length);
								}
							} else {
								tmpStr += tmpB[2].outerHTML.replace('class="invisible"', '').replace('000', kjNo[i3]);
							}
						}
						if (kjNo.length < 10) {
							for (var i3 = 0; i3 < (10 - kjNo.length); i3++) {
								tmpStr += '<tr><td colspan="' + (tmpB[2].innerHTML.split("<td").length - 1) + '">&nbsp;</td></tr>';
							}
						}
						tmpStr += tmpB[tmpB.length - 1].outerHTML;
						tmpStr += "</tbody>";
						// console_Log(tmpStr);
						// tmpA[i1].innerHTML = tmpStr;
						tmpA[i1].outerHTML = '<table class="' + tmpA[i1].className + '">' + tmpStr + "</table>";
						tmpA[i1].getElementsByTagName("button")[0].onclick = function() {
							onclickMidBtn();
						};
						tmpA[i1].getElementsByTagName("button")[1].onclick = function() {
							onclickMainBtn();
						};
						break;
					} else if (i2 == 4) {
						// console_Log(4);
						// var num = "54387".split("");
						tmpStr += tmpB[0].outerHTML;
						tmpStr += tmpB[1].outerHTML;
						tmpStr += tmpB[2].outerHTML;
						tmpStr += tmpB[3].outerHTML;
						// console_Log(tmpB[3].outerHTML);
						// show number
						for (var i3 = 0; i3 < kjNo.length; i3++) {
							if (kjData[i3] ? kjData[i3] != "" ? kjData[i3] : false : false) {
								var num = kjData[i3].split(",");
								// console_Log(num);
								var todo = 0;
								var checkTab = tmpB[3].outerHTML.split("</span>");
								var tmpList = [];
								for (var i4 = 0; i4 < checkTab.length; i4++) {
									if (checkTab[i4].indexOf('<span class="active">') != -1) {
										tmpList[tmpList.length] = parseInt(num[i4]);
									}
								}
								delete checkTab;
								checkTab = undefined;
								if (tmpList.length != 3) {
									todo = "-";
									break;
								} else {
									tmpList = tmpList.sort();
									if (tmpList[0] == tmpList[1] ? tmpList[1] == tmpList[2] : false) {
										todo = "豹子";
									} else if ((tmpList[0] + 1) == tmpList[1] && (tmpList[1] + 1) == tmpList[2]) {
										todo = "顺子";
									} else if ((tmpList[0] == tmpList[1] && tmpList[1] != tmpList[2]) || (tmpList[0] != tmpList[1] && tmpList[1] == tmpList[2])) {
										todo = "对子";
									} else if (((tmpList[0] + 1) == tmpList[1] && (tmpList[1] + 1) != tmpList[2])
											|| ((tmpList[0] + 1) != tmpList[1] && (tmpList[1] + 1) == tmpList[2])) {
										todo = "半顺";
									} else {
										todo = "杂六";
									}
								}
								var tt = tmpB[3].outerHTML.split("</span>");
								tmpStr += (tt[0].substring(0, tt[0].indexOf("<td>") + 4) + kjNo[i3] + tt[0].substring(tt[0].indexOf("</td>"), tt[0].length - 1))
										.replace('class="invisible"', '')
										+ parseInt(num[0]) + "</span>";
								tmpStr += tt[1].substring(0, tt[1].length - 1) + parseInt(num[1]) + "</span>";
								tmpStr += tt[2].substring(0, tt[2].length - 1) + parseInt(num[2]) + "</span>";
								tmpStr += tt[3].substring(0, tt[3].length - 1) + parseInt(num[3]) + "</span>";
								tmpStr += tt[4].substring(0, tt[4].length - 1) + parseInt(num[4]) + "</span>";
								tmpStr += tt[5].substring(0, tt[5].lastIndexOf("<td>") + 4) + todo + tt[5].substring(tt[5].lastIndexOf("</td>"), tt[5].length);
							} else {
								tmpStr += tmpB[2].outerHTML.replace('class="invisible"', '').replace('000', kjNo[i3]);
							}
						}
						if (kjNo.length < 10) {
							for (var i3 = 0; i3 < (10 - kjNo.length); i3++) {
								tmpStr += '<tr><td colspan="' + (tmpB[2].innerHTML.split("<td").length - 1) + '">&nbsp;</td></tr>';
							}
						}
						tmpStr += tmpB[tmpB.length - 1].outerHTML;
						tmpStr += "</tbody>";
						// console_Log(tmpStr);
						// tmpA[i1].innerHTML = tmpStr;
						tmpA[i1].outerHTML = '<table class="' + tmpA[i1].className + '">' + tmpStr + "</table>";
						tmpA[i1].getElementsByTagName("button")[0].onclick = function() {
							onclickMidBtn();
						};
						tmpA[i1].getElementsByTagName("button")[1].onclick = function() {
							onclickMainBtn();
						};
						delete repStr;
						repStr = undefined;
						break;
					} else if (i2 == 5) {
						// 前,后,二大小单双
						// console_Log(5);
						// var num = "54387".split("");
						tmpStr += tmpB[0].outerHTML;
						tmpStr += tmpB[1].outerHTML;
						tmpStr += tmpB[2].outerHTML;
						tmpStr += tmpB[3].outerHTML;
						// console_Log(tmpB[3].outerHTML);
						// show number
						for (var i3 = 0; i3 < kjNo.length; i3++) {
							if (kjData[i3] ? kjData[i3] != "" ? kjData[i3] : false : false) {
								var num = kjData[i3].split(",");
								// console_Log(num);
								var todo = 0;
								var todo1 = 0;
								var checkTab = tmpB[3].outerHTML.split("</span>");
								var tmpList = [];
								for (var i4 = 0; i4 < 5; i4++) {
									if (checkTab[i4].indexOf('<span class="active">') != -1) {
										tmpList[tmpList.length] = parseInt(num[i4]);
									}
								}
								delete checkTab;
								checkTab = undefined;
								// console_Log("tmpList.length:"+tmpList.length);
								if (tmpList.length != 2) {
									todo = "-";
									break;
								} else {
									if (tmpList[0] >= 0 ? tmpList[0] <= 4 : false) {
										todo = "小";
									} else if (tmpList[0] >= 5 ? tmpList[0] <= 9 : false) {
										todo = "大";
									} else {
										todo = "-";
									}
									if (tmpList[1] >= 0 ? tmpList[1] <= 4 : false) {
										todo += "小";
									} else if (tmpList[1] >= 5 ? tmpList[1] <= 9 : false) {
										todo += "大";
									} else {
										todo = "-";
									}
									if (tmpList[0] % 2 == 1) {
										todo1 = "单";
									} else if (tmpList[0] % 2 == 0) {
										todo1 = "双";
									} else {
										todo1 = "-";
									}
									if (tmpList[1] % 2 == 1) {
										todo1 += "单";
									} else if (tmpList[1] % 2 == 0) {
										todo1 += "双";
									} else {
										todo1 = "-";
									}
								}
								delete tmpList;
								tmpList = undefined;
								// console_Log(todo);
								// console_Log(todo1);
								var tt = tmpB[3].outerHTML.split("</span>");
								// console_Log(tt);
								tmpStr += (tt[0].substring(0, tt[0].indexOf("<td>") + 4) + kjNo[i3] + tt[0].substring(tt[0].indexOf("</td>"), tt[0].length - 1))
										.replace('class="invisible"', '')
										+ parseInt(num[0]) + "</span>";
								tmpStr += tt[1].substring(0, tt[1].length - 1) + parseInt(num[1]) + "</span>";
								tmpStr += tt[2].substring(0, tt[2].length - 1) + parseInt(num[2]) + "</span>";
								tmpStr += tt[3].substring(0, tt[3].length - 1) + parseInt(num[3]) + "</span>";
								tmpStr += tt[4].substring(0, tt[4].length - 1) + parseInt(num[4]) + "</span>";
								tmpStr += tt[5].replace("小大", todo).replace("双单", todo1);
							} else {
								tmpStr += tmpB[2].outerHTML.replace('class="invisible"', '').replace('000', kjNo[i3]);
							}
						}
						if (kjNo.length < 10) {
							for (var i3 = 0; i3 < (10 - kjNo.length); i3++) {
								tmpStr += '<tr><td colspan="' + (tmpB[2].innerHTML.split("<td").length - 1) + '">&nbsp;</td></tr>';
							}
						}
						tmpStr += tmpB[tmpB.length - 1].outerHTML;
						tmpStr += "</tbody>";
						// console_Log(tmpStr);
						// tmpA[i1].innerHTML = tmpStr;
						tmpA[i1].outerHTML = '<table class="' + tmpA[i1].className + '">' + tmpStr + "</table>";
						tmpA[i1].getElementsByTagName("button")[0].onclick = function() {
							onclickMidBtn();
						};
						tmpA[i1].getElementsByTagName("button")[1].onclick = function() {
							onclickMainBtn();
						};
						break;
					} else if (i2 == 6) {
						// console_Log(6);
						// var num = "54387".split("");
						tmpStr += tmpB[0].outerHTML;
						tmpStr += tmpB[1].outerHTML;
						tmpStr += tmpB[2].outerHTML;
						tmpStr += tmpB[3].outerHTML;
						// console_Log(tmpB[3].outerHTML);
						// show number
						for (var i3 = 0; i3 < kjNo.length; i3++) {
							if (kjData[i3] ? kjData[i3] != "" ? kjData[i3] : false : false) {
								var num = kjData[i3].split(",");
								// console_Log(num);
								var todo = -1;
								var checkTab = tmpB[3].outerHTML.split("</span>");
								for (var i4 = 0; i4 < checkTab.length; i4++) {
									if (checkTab[i4].indexOf('<span class="active">') != -1) {
										if (todo == -1) {
											todo = parseInt(num[i4]);
										} else {
											todo = todo - parseInt(num[i4]);
										}
									}
								}
								delete checkTab;
								checkTab = undefined;
								if (todo > 0) {
									todo = '<span class="active dragon-color">龙</span>';
								} else if (todo < 0) {
									todo = '<span class="active tiger-color">虎</span>';
								} else if (todo == 0) {
									todo = '<span class="active total-color">和</span>';
								} else {
									todo = "-";
								}
								var tt = tmpB[3].outerHTML.split("</span>");
								tmpStr += (tt[0].substring(0, tt[0].indexOf("<td>") + 4) + kjNo[i3] + tt[0].substring(tt[0].indexOf("</td>"), tt[0].length - 1))
										.replace('class="invisible"', '')
										+ parseInt(num[0]) + "</span>";
								tmpStr += tt[1].substring(0, tt[1].length - 1) + parseInt(num[1]) + "</span>";
								tmpStr += tt[2].substring(0, tt[2].length - 1) + parseInt(num[2]) + "</span>";
								tmpStr += tt[3].substring(0, tt[3].length - 1) + parseInt(num[3]) + "</span>";
								tmpStr += tt[4].substring(0, tt[4].length - 1) + parseInt(num[4]) + "</span>";
								tmpStr += tt[5].substring(0, tt[5].lastIndexOf("<td>") + 4) + todo;
								tmpStr += tt[6];
							} else {
								tmpStr += tmpB[2].outerHTML.replace('class="invisible"', '').replace('000', kjNo[i3]);
							}
						}
						if (kjNo.length < 10) {
							for (var i3 = 0; i3 < (10 - kjNo.length); i3++) {
								tmpStr += '<tr><td colspan="' + (tmpB[2].innerHTML.split("<td").length - 1) + '">&nbsp;</td></tr>';
							}
						}
						tmpStr += tmpB[tmpB.length - 1].outerHTML;
						tmpStr += "</tbody>";
						// console_Log(tmpStr);
						// tmpA[i1].innerHTML = tmpStr;
						tmpA[i1].outerHTML = '<table class="' + tmpA[i1].className + '">' + tmpStr + "</table>";
						tmpA[i1].getElementsByTagName("button")[0].onclick = function() {
							onclickMidBtn();
						};
						tmpA[i1].getElementsByTagName("button")[1].onclick = function() {
							onclickMainBtn();
						};
						break;
					} else if (i2 == 7) { // 3nums
						// console_Log(7);
						// var num = "543".split("");
						tmpStr += tmpB[0].outerHTML;
						tmpStr += tmpB[1].outerHTML;
						tmpStr += tmpB[2].outerHTML;
						tmpStr += tmpB[3].outerHTML;
						// console_Log(tmpB[3].outerHTML);
						// show number
						for (var i3 = 0; i3 < kjNo.length; i3++) {
							if (kjData[i3] ? kjData[i3] != "" ? kjData[i3] : false : false) {
								var num = kjData[i3].split(",");
								// console_Log(num);
								var tt = tmpB[3].outerHTML.split("</span>");
								tmpStr += (tt[0].substring(0, tt[0].indexOf("<td>") + 4) + kjNo[i3] + tt[0].substring(tt[0].indexOf("</td>"), tt[0].length - 1))
										.replace('class="invisible"', '')
										+ parseInt(num[0]) + "</span>";
								tmpStr += tt[1].substring(0, tt[1].length - 1) + parseInt(num[1]) + "</span>";
								tmpStr += tt[2].substring(0, tt[2].length - 1) + parseInt(num[2]) + "</span>";
								tmpStr += tt[3];
							} else {
								tmpStr += tmpB[2].outerHTML.replace('class="invisible"', '').replace('000', kjNo[i3]);
							}
						}
						if (kjNo.length < 10) {
							for (var i3 = 0; i3 < (10 - kjNo.length); i3++) {
								tmpStr += '<tr><td colspan="' + (tmpB[2].innerHTML.split("<td").length - 1) + '">&nbsp;</td></tr>';
							}
						}
						tmpStr += tmpB[tmpB.length - 1].outerHTML;
						tmpStr += "</tbody>";
						// console_Log(tmpStr);
						// tmpA[i1].innerHTML = tmpStr;
						tmpA[i1].outerHTML = '<table class="' + tmpA[i1].className + '">' + tmpStr + "</table>";
						tmpA[i1].getElementsByTagName("button")[0].onclick = function() {
							onclickMidBtn();
						};
						tmpA[i1].getElementsByTagName("button")[1].onclick = function() {
							onclickMainBtn();
						};
						break;
					} else if (i2 == 8) { // 3nums
						// console_Log(8);
						// var num = "543".split("");
						tmpStr += tmpB[0].outerHTML;
						tmpStr += tmpB[1].outerHTML;
						tmpStr += tmpB[2].outerHTML;
						tmpStr += tmpB[3].outerHTML;
						// show number
						for (var i3 = 0; i3 < kjNo.length; i3++) {
							if (kjData[i3] ? kjData[i3] != "" ? kjData[i3] : false : false) {
								var num = kjData[i3].split(",");
								// console_Log(num);
								var todo = "";
								var sum = parseInt(num[0]) + parseInt(num[1]) + parseInt(num[2]);
								if (tn.indexOf("和值大小") != -1) {
									if (sum >= 3 ? sum <= 10 : false) {
										todo = "小";
									} else if (sum >= 11 ? sum <= 18 : false) {
										todo = "大";
									} else {
										todo = "-";
									}
								} else if (tn.indexOf("和值单双") != -1) {
									if (sum % 2 == 1) {
										todo = "单";
									} else if (sum % 2 == 0) {
										todo = "双";
									} else {
										todo = "-";
									}
								} else if (tn.indexOf("和值") != -1) {
									todo = sum;
								} else {
									todo = "-";
								}
								var tt = tmpB[3].outerHTML.split("</span>");
								tmpStr += (tt[0].substring(0, tt[0].indexOf("<td>") + 4) + kjNo[i3] + tt[0].substring(tt[0].indexOf("</td>"), tt[0].length - 1))
										.replace('class="invisible"', '')
										+ parseInt(num[0]) + "</span>";
								tmpStr += tt[1].substring(0, tt[1].length - 1) + parseInt(num[1]) + "</span>";
								tmpStr += tt[2].substring(0, tt[2].length - 1) + parseInt(num[2]) + "</span>";
								tmpStr += tt[3].substring(0, tt[3].lastIndexOf("<td>") + 4) + todo + tt[3].substring(tt[3].lastIndexOf("</td>"), tt[3].length);
							} else {
								tmpStr += tmpB[2].outerHTML.replace('class="invisible"', '').replace('000', kjNo[i3]);
							}
						}
						if (kjNo.length < 10) {
							for (var i3 = 0; i3 < (10 - kjNo.length); i3++) {
								tmpStr += '<tr><td colspan="' + (tmpB[2].innerHTML.split("<td").length - 1) + '">&nbsp;</td></tr>';
							}
						}
						tmpStr += tmpB[tmpB.length - 1].outerHTML;
						tmpStr += "</tbody>";
						// console_Log(tmpStr);
						// tmpA[i1].innerHTML = tmpStr;
						tmpA[i1].outerHTML = '<table class="' + tmpA[i1].className + '">' + tmpStr + "</table>";
						tmpA[i1].getElementsByTagName("button")[0].onclick = function() {
							onclickMidBtn();
						};
						tmpA[i1].getElementsByTagName("button")[1].onclick = function() {
							onclickMainBtn();
						};
						break;
					} else if (i2 == 9) { // 3nums
						// 福彩3D 组三 组六 混合组选
						// console_Log(9);
						// var num = "543".split("");
						tmpStr += tmpB[0].outerHTML;
						tmpStr += tmpB[1].outerHTML;
						tmpStr += tmpB[2].outerHTML;
						tmpStr += tmpB[3].outerHTML;
						// show number
						for (var i3 = 0; i3 < kjNo.length; i3++) {
							// var num =
							// "05,04,03,01,02,09,08,07,06,10".split(",");
							// console_Log(kjNo[i3]);
							if (kjData[i3] ? kjData[i3] != "" ? kjData[i3] : false : false) {
								var num = kjData[i3].split(",");
								// console_Log(num);
								var todo = 0;
								var checkTab = tmpB[3].outerHTML.split("</span>");
								var tmpList = [];
								for (var i4 = 0; i4 < 3; i4++) {
									if (checkTab[i4].indexOf('<span class="active">') != -1) {
										tmpList[tmpList.length] = parseInt(num[i4]);
									}
								}
								delete checkTab;
								checkTab = undefined;
								// console_Log("tmpList.length:"+tmpList.length);
								if (tmpList.length != 3) {
									todo = "-";
									break;
								} else {
									tmpList = tmpList.sort();
									if (tmpList[0] == tmpList[1] ? tmpList[1] == tmpList[2] : false) {
										if (tn.indexOf("组选") != -1) {
											todo = '豹子';
										} else {
											todo = "-"
										}
									} else if ((tmpList[0] == tmpList[1] && tmpList[1] != tmpList[2]) || (tmpList[0] != tmpList[1] && tmpList[1] == tmpList[2])) {
										todo = '组三';
									} else {
										todo = '组六';
									}
									if (tn.indexOf("组三") != -1 && todo == "组三") {
										todo = '<span class="active">组三</span>';
									} else if (tn.indexOf("组六") != -1 && todo == "组六") {
										todo = '<span class="active">组六</span>';
									}
								}
								delete tmpList;
								tmpList = undefined;
								var tt = tmpB[3].outerHTML.split("</span>");
								tmpStr += (tt[0].substring(0, tt[0].indexOf("<td>") + 4) + kjNo[i3] + tt[0].substring(tt[0].indexOf("</td>"), tt[0].length - 1))
										.replace('class="invisible"', '')
										+ parseInt(num[0]) + "</span>";
								tmpStr += tt[1].substring(0, tt[1].length - 1) + parseInt(num[1]) + "</span>";
								tmpStr += tt[2].substring(0, tt[2].length - 1) + parseInt(num[2]) + "</span>";
								if (tt.length == 5) {
									tmpStr += tt[3].substring(0, tt[3].lastIndexOf("<td>") + 4) + todo + tt[4];
								} else {
									tmpStr += tt[3].substring(0, tt[3].lastIndexOf("<td>") + 4) + todo + tt[3].substring(tt[3].lastIndexOf("</td>"), tt[3].length);
								}

							} else {
								tmpStr += tmpB[2].outerHTML.replace('class="invisible"', '').replace('000', kjNo[i3]);
							}
						}
						if (kjNo.length < 10) {
							for (var i3 = 0; i3 < (10 - kjNo.length); i3++) {
								tmpStr += '<tr><td colspan="' + (tmpB[2].innerHTML.split("<td").length - 1) + '">&nbsp;</td></tr>';
							}
						}
						tmpStr += tmpB[tmpB.length - 1].outerHTML;
						tmpStr += "</tbody>";
						// console_Log(tmpStr);
						// tmpA[i1].innerHTML = tmpStr;
						tmpA[i1].outerHTML = '<table class="' + tmpA[i1].className + '">' + tmpStr + "</table>";
						tmpA[i1].getElementsByTagName("button")[0].onclick = function() {
							onclickMidBtn();
						};
						tmpA[i1].getElementsByTagName("button")[1].onclick = function() {
							onclickMainBtn();
						};
						break;
					} else if (i2 == 10) { // 3nums
						// 福彩3D 龍虎和
						// console_Log(10);
						// var num = "543".split("");
						tmpStr += tmpB[0].outerHTML;
						tmpStr += tmpB[1].outerHTML;
						tmpStr += tmpB[2].outerHTML;
						tmpStr += tmpB[3].outerHTML;
						// console_Log(tmpB[3].outerHTML);
						// show number
						for (var i3 = 0; i3 < kjNo.length; i3++) {
							// var num =
							// "05,04,03,01,02,09,08,07,06,10".split(",");
							// console_Log("kjNo:"+kjNo[i3]);
							if (kjData[i3] ? kjData[i3] != "" ? kjData[i3] : false : false) {
								var num = kjData[i3].split(",");
								// console_Log(num);
								var todo = -1;
								var checkTab = tmpB[3].outerHTML.split("</span>");
								for (var i4 = 0; i4 < checkTab.length; i4++) {
									if (checkTab[i4].indexOf('<span class="active">') != -1) {
										if (todo == -1) {
											todo = parseInt(num[i4]);
										} else {
											todo = todo - parseInt(num[i4]);
										}
									}
								}
								delete checkTab;
								checkTab = undefined;
								if (todo > 0) {
									todo = '<span class="active dragon-color">龙</span>';
								} else if (todo < 0) {
									todo = '<span class="active tiger-color">虎</span>';
								} else if (todo == 0) {
									todo = '<span class="active total-color">和</span>';
								} else {
									todo = "-";
								}
								var tt = tmpB[3].outerHTML.split("</span>");
								tmpStr += (tt[0].substring(0, tt[0].indexOf("<td>") + 4) + kjNo[i3] + tt[0].substring(tt[0].indexOf("</td>"), tt[0].length - 1))
										.replace('class="invisible"', '')
										+ parseInt(num[0]) + "</span>";
								tmpStr += tt[1].substring(0, tt[1].length - 1) + parseInt(num[1]) + "</span>";
								tmpStr += tt[2].substring(0, tt[2].length - 1) + parseInt(num[2]) + "</span>";
								if (tt.length == 5) {
									tmpStr += tt[3].substring(0, tt[3].lastIndexOf("<td>") + 4) + todo + tt[4];
								} else {
									tmpStr += tt[3].substring(0, tt[3].lastIndexOf("<td>") + 4) + todo + tt[3].substring(tt[3].lastIndexOf("</td>"), tt[3].length);
								}
							} else {
								tmpStr += tmpB[2].outerHTML.replace('class="invisible"', '').replace('000', kjNo[i3]);
							}
						}
						if (kjNo.length < 10) {
							for (var i3 = 0; i3 < (10 - kjNo.length); i3++) {
								tmpStr += '<tr><td colspan="' + (tmpB[2].innerHTML.split("<td").length - 1) + '">&nbsp;</td></tr>';
							}
						}
						tmpStr += tmpB[tmpB.length - 1].outerHTML;
						tmpStr += "</tbody>";
						// console_Log(tmpStr);
						// tmpA[i1].innerHTML = tmpStr;
						tmpA[i1].outerHTML = '<table class="' + tmpA[i1].className + '">' + tmpStr + "</table>";
						tmpA[i1].getElementsByTagName("button")[0].onclick = function() {
							onclickMidBtn();
						};
						tmpA[i1].getElementsByTagName("button")[1].onclick = function() {
							onclickMainBtn();
						};
						break;
					} else if (i2 == 11) { // PK10 冠亚 和值
						// console_Log(11);
						// var num =
						// "05,04,03,01,02,09,08,07,06,10".split(",");
						tmpStr += tmpB[0].outerHTML;
						tmpStr += tmpB[1].outerHTML;
						tmpStr += tmpB[2].outerHTML;
						tmpStr += tmpB[3].outerHTML;
						// console_Log(tmpB[3].outerHTML);
						// show number
						for (var i3 = 0; i3 < kjNo.length; i3++) {
							// var num =
							// "05,04,03,01,02,09,08,07,06,10".split(",");
							// console_Log(kjNo[i3]);
							if (kjData[i3] ? kjData[i3] != "" ? kjData[i3] : false : false) {
								var num = kjData[i3].split(",");
								// console_Log(num);
								var todo = 0;
								todo = parseInt(num[0]) + parseInt(num[1]);
								var tt = tmpB[3].outerHTML.split("</span>");
								tmpStr += (tt[0].substring(0, tt[0].indexOf("<td>") + 4) + kjNo[i3] + tt[0].substring(tt[0].indexOf("</td>"), tt[0].length - 1))
										.replace('class="invisible"', '')
										+ parseInt(num[0]) + "</span>";
								tmpStr += tt[1].substring(0, tt[1].length - 1) + parseInt(num[1]) + "</span>";
								tmpStr += tt[2].substring(0, tt[2].lastIndexOf("<td>") + 4) + todo + tt[2].substring(tt[2].lastIndexOf("</td>"), tt[2].length);
							} else {
								tmpStr += tmpB[2].outerHTML.replace('class="invisible"', '').replace('000', kjNo[i3]);
							}
						}
						if (kjNo.length < 10) {
							for (var i3 = 0; i3 < (10 - kjNo.length); i3++) {
								tmpStr += '<tr><td colspan="' + (tmpB[2].innerHTML.split("<td").length - 1) + '">&nbsp;</td></tr>';
							}
						}
						tmpStr += tmpB[tmpB.length - 1].outerHTML;
						tmpStr += "</tbody>";
						// console_Log(tmpStr);
						// tmpA[i1].innerHTML = tmpStr;
						tmpA[i1].outerHTML = '<table class="' + tmpA[i1].className + '">' + tmpStr + "</table>";
						tmpA[i1].getElementsByTagName("button")[0].onclick = function() {
							onclickMidBtn();
						};
						tmpA[i1].getElementsByTagName("button")[1].onclick = function() {
							onclickMainBtn();
						};
						break;
					} else if (i2 == 12) { // PK10 冠亚 大小单双
						// console_Log(12);
						// var num =
						// "05,04,03,01,02,09,08,07,06,10".split(",");
						tmpStr += tmpB[0].outerHTML;
						tmpStr += tmpB[1].outerHTML;
						tmpStr += tmpB[2].outerHTML;
						tmpStr += tmpB[3].outerHTML;
						// console_Log(tmpB[3].outerHTML);
						// show number
						for (var i3 = 0; i3 < kjNo.length; i3++) {
							// var num =
							// "05,04,03,01,02,09,08,07,06,10".split(",");
							// console_Log(kjNo[i3]);
							if (kjData[i3] ? kjData[i3] != "" ? kjData[i3] : false : false) {
								var num = kjData[i3].split(",");
								// console_Log(num);
								var todo = 0;
								var todo1 = "";
								var todo2 = "";
								todo = parseInt(num[0]) + parseInt(num[1]);
								if (todo >= 3 ? todo <= 10 : false) {
									todo1 = "小";
								} else if (todo >= 11 ? todo <= 19 : false) {
									todo1 = "大";
								} else {
									todo1 = "-";
								}
								if (todo % 2 == 1) {
									todo2 = "单";
								} else if (todo % 2 == 0) {
									todo2 = "双";
								} else {
									todo2 = "-";
								}
								var tt = tmpB[3].outerHTML.split("</span>");
								tmpStr += (tt[0].substring(0, tt[0].indexOf("<td>") + 4) + kjNo[i3] + tt[0].substring(tt[0].indexOf("</td>"), tt[0].length - 1))
										.replace('class="invisible"', '')
										+ parseInt(num[0]) + "</span>";
								tmpStr += tt[1].substring(0, tt[1].length - 1) + parseInt(num[1]) + "</span>";
								tmpStr += "<td>" + todo + "</td>";
								tmpStr += "<td>" + todo1 + "</td>";
								tmpStr += "<td>" + todo2 + "</td></tr>";
							} else {
								tmpStr += tmpB[2].outerHTML.replace('class="invisible"', '').replace('000', kjNo[i3]);
							}
						}
						if (kjNo.length < 10) {
							for (var i3 = 0; i3 < (10 - kjNo.length); i3++) {
								tmpStr += '<tr><td colspan="' + (tmpB[2].innerHTML.split("<td").length - 1) + '">&nbsp;</td></tr>';
							}
						}
						tmpStr += tmpB[tmpB.length - 1].outerHTML;
						tmpStr += "</tbody>";
						// console_Log(tmpStr);
						// tmpA[i1].innerHTML = tmpStr;
						tmpA[i1].outerHTML = '<table class="' + tmpA[i1].className + '">' + tmpStr + "</table>";
						tmpA[i1].getElementsByTagName("button")[0].onclick = function() {
							onclickMidBtn();
						};
						tmpA[i1].getElementsByTagName("button")[1].onclick = function() {
							onclickMainBtn();
						};
						break;
					} else if (i2 == 13) { // PK10 10
						// console_Log(13);
						// var num =
						// "05,04,03,01,02,09,08,07,06,10".split(",");
						tmpStr += tmpB[0].outerHTML;
						tmpStr += tmpB[1].outerHTML;
						tmpStr += tmpB[2].outerHTML;
						tmpStr += tmpB[3].outerHTML;
						for (var i3 = 0; i3 < kjNo.length; i3++) {
							// var num =
							// "05,04,03,01,02,09,08,07,06,10".split(",");
							// console_Log(kjNo[i3]);
							if (kjData[i3] ? kjData[i3] != "" ? kjData[i3] : false : false) {
								var num = kjData[i3].split(",");
								// console_Log(num);
								// console_Log(tmpB[3].outerHTML);
								// show number
								var tt = tmpB[3].outerHTML.split("</span>");
								tmpStr += (tt[0].substring(0, tt[0].indexOf("<td>") + 4) + kjNo[i3].substring(kjNo[i3].length - 3, kjNo[i3].length) + tt[0].substring(
										tt[0].indexOf("</td>"), tt[0].length - 1)).replace('class="invisible"', '')
										+ parseInt(num[0]) + "</span>";
								tmpStr += tt[1].substring(0, tt[1].length - 1) + parseInt(num[1]) + "</span>";
								tmpStr += tt[2].substring(0, tt[2].length - 1) + parseInt(num[2]) + "</span>";
								tmpStr += tt[3].substring(0, tt[3].length - 1) + parseInt(num[3]) + "</span>";
								tmpStr += tt[4].substring(0, tt[4].length - 1) + parseInt(num[4]) + "</span>";
								tmpStr += tt[5].substring(0, tt[5].length - 1) + parseInt(num[5]) + "</span>";
								tmpStr += tt[6].substring(0, tt[6].length - 1) + parseInt(num[6]) + "</span>";
								tmpStr += tt[7].substring(0, tt[7].length - 1) + parseInt(num[7]) + "</span>";
								tmpStr += tt[8].substring(0, tt[8].length - 1) + parseInt(num[8]) + "</span>";
								tmpStr += tt[9].substring(0, tt[9].length - 2) + parseInt(num[9]) + "</span>";
								tmpStr += tt[10];
							} else {
								tmpStr += tmpB[2].outerHTML.replace('class="invisible"', '').replace('000', kjNo[i3].substring(kjNo[i3].length - 3, kjNo[i3].length));
							}
						}
						if (kjNo.length < 10) {
							for (var i3 = 0; i3 < (10 - kjNo.length); i3++) {
								tmpStr += '<tr><td colspan="' + (tmpB[2].innerHTML.split("<td").length - 1) + '">&nbsp;</td></tr>';
							}
						}
						tmpStr += tmpB[tmpB.length - 1].outerHTML;
						tmpStr += "</tbody>";
						// console_Log(tmpStr);
						// tmpA[i1].innerHTML = tmpStr;
						tmpA[i1].outerHTML = '<table class="' + tmpA[i1].className + '">' + tmpStr + "</table>";
						tmpA[i1].getElementsByTagName("button")[0].onclick = function() {
							onclickMidBtn();
						};
						tmpA[i1].getElementsByTagName("button")[1].onclick = function() {
							onclickMainBtn();
						};
						break;
					} else if (i2 == 14) { // PK10 龙虎
						// console_Log(14);
						tmpStr += tmpB[0].outerHTML;
						tmpStr += tmpB[1].outerHTML;
						tmpStr += tmpB[2].outerHTML;
						tmpStr += tmpB[3].outerHTML;
						// console_Log(tmpB[3].outerHTML);
						// show number
						for (var i3 = 0; i3 < kjNo.length; i3++) {
							// var num =
							// "05,04,03,01,02,09,08,07,06,10".split(",");
							// console_Log(kjNo[i3]);
							if (kjData[i3] ? kjData[i3] != "" ? kjData[i3] : false : false) {
								var num = kjData[i3].split(",");
								// console_Log(num);
								var todo = "-";
								var todo1 = "-";
								var todo2 = "-";
								var todo3 = "-";
								var todo4 = "-";
								if (parseInt(num[0]) > parseInt(num[9])) {
									todo = '<span class="active dragon-color">龙</span>';
								} else if (parseInt(num[0]) < parseInt(num[9])) {
									todo = '<span class="active tiger-color">虎</span>';
								}
								if (parseInt(num[1]) > parseInt(num[8])) {
									todo1 = '<span class="active dragon-color">龙</span>';
								} else if (parseInt(num[1]) < parseInt(num[8])) {
									todo1 = '<span class="active tiger-color">虎</span>';
								}
								if (parseInt(num[2]) > parseInt(num[7])) {
									todo2 = '<span class="active dragon-color">龙</span>';
								} else if (parseInt(num[2]) < parseInt(num[7])) {
									todo2 = '<span class="active tiger-color">虎</span>';
								}
								if (parseInt(num[3]) > parseInt(num[6])) {
									todo3 = '<span class="active dragon-color">龙</span>';
								} else if (parseInt(num[3]) < parseInt(num[6])) {
									todo3 = '<span class="active tiger-color">虎</span>';
								}
								if (parseInt(num[4]) > parseInt(num[5])) {
									todo4 = '<span class="active dragon-color">龙</span>';
								} else if (parseInt(num[4]) < parseInt(num[5])) {
									todo4 = '<span class="active tiger-color">虎</span>';
								}

								tmpStr += "<tr><td>" + kjNo[i3] + "</td>";
								tmpStr += "<td>" + todo + "</td>";
								tmpStr += "<td>" + todo1 + "</td>";
								tmpStr += "<td>" + todo2 + "</td>";
								tmpStr += "<td>" + todo3 + "</td>";
								tmpStr += "<td>" + todo4 + "</td></tr>";
							} else {
								tmpStr += tmpB[2].outerHTML.replace('class="invisible"', '').replace('000', kjNo[i3]);
							}
						}
						if (kjNo.length < 10) {
							for (var i3 = 0; i3 < (10 - kjNo.length); i3++) {
								tmpStr += '<tr><td colspan="' + (tmpB[2].innerHTML.split("<td").length - 1) + '">&nbsp;</td></tr>';
							}
						}
						tmpStr += tmpB[tmpB.length - 1].outerHTML;
						tmpStr += "</tbody>";
						// console_Log(tmpStr);
						// tmpA[i1].innerHTML = tmpStr;
						tmpA[i1].outerHTML = '<table class="' + tmpA[i1].className + '">' + tmpStr + "</table>";
						tmpA[i1].getElementsByTagName("button")[0].onclick = function() {
							onclickMidBtn();
						};
						tmpA[i1].getElementsByTagName("button")[1].onclick = function() {
							onclickMainBtn();
						};
						break;
					}
				}
			}
			removeClass(tmpA[i1], "hidden");
			// ////disableLoadingPage();
			break;
		}
	}
	removeClass(getEle("RightSideDiv"), "hidden");
}

function dateToYMD(d1) {
	if (!(d1 instanceof Date)) {
		console_Log("input is not Date!" + d1);
		return "";
	}
	var y = null;
	var m = null;
	var d = null;
	var tmpStr = null;
	try {
		y = d1.getFullYear();
		m = d1.getMonth() + 1;
		d = d1.getDate();
		tmpStr = y + "/" + (m < 10 ? "0" + m : m) + "/" + (d < 10 ? "0" + d : d);
		console_Log(tmpStr.toString());
		return tmpStr.toString();
	} catch (err) {

	} finally {
		delete y;
		delete m;
		delete d;
		delete tmpStr;
		y = undefined;
		m = undefined;
		d = undefined;
		tmpStr = undefined;
	}
}

// add=0
function dateAddDay(add) {
	if (!(typeof add === "number")) {
		console_Log("input is not number!" + add);
		return "";
	}
	var today = null;
	var y = null;
	var m = null;
	var d = null;
	var tmpStr = null;
	try {
		today = new Date();
		today = new Date(today.setDate(today.getDate() + add));
		y = today.getFullYear();
		m = today.getMonth() + 1;
		d = today.getDate();
		tmpStr = y + "/" + (m < 10 ? "0" + m : m) + "/" + (d < 10 ? "0" + d : d);
		// console_Log(tmpStr.toString());
		return tmpStr.toString();
	} catch (err) {

	} finally {
		delete y;
		delete m;
		delete d;
		delete tmpStr;
		delete today;
		y = undefined;
		m = undefined;
		d = undefined;
		tmpStr = undefined;
		today = undefined;
	}
}

var Strings = {
	// private property
	_keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
	// public method for encoding
	encode : function(input) {
		var output = "";
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		var i = 0;
		input = Strings._utf8_encode(input);
		while (i < input.length) {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);
			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;
			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}
			output = output + this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) + this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);
		}
		return output;
	},
	// public method for decoding
	decode : function(input) {
		var output = "";
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0;
		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
		while (i < input.length) {
			enc1 = this._keyStr.indexOf(input.charAt(i++));
			enc2 = this._keyStr.indexOf(input.charAt(i++));
			enc3 = this._keyStr.indexOf(input.charAt(i++));
			enc4 = this._keyStr.indexOf(input.charAt(i++));
			chr1 = (enc1 << 2) | (enc2 >> 4);
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			chr3 = ((enc3 & 3) << 6) | enc4;
			output = output + String.fromCharCode(chr1);
			if (enc3 != 64) {
				output = output + String.fromCharCode(chr2);
			}
			if (enc4 != 64) {
				output = output + String.fromCharCode(chr3);
			}
		}
		output = Strings._utf8_decode(output);
		return output;
	},
	// private method for UTF-8 encoding
	_utf8_encode : function(string) {
		string = string.replace(/\r\n/g, "\n");
		var utftext = "";
		for (var n = 0; n < string.length; n++) {
			var c = string.charCodeAt(n);
			if (c < 128) {
				utftext += String.fromCharCode(c);
			} else if ((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			} else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}
		}
		return utftext;
	},
	// private method for UTF-8 decoding
	_utf8_decode : function(utftext) {
		var string = "";
		var i = 0;
		var c = c1 = c2 = 0;
		while (i < utftext.length) {
			c = utftext.charCodeAt(i);
			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			} else if ((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i + 1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			} else {
				c2 = utftext.charCodeAt(i + 1);
				c3 = utftext.charCodeAt(i + 2);
				string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}
		}
		return string;
	}
}

function checkDiffms(sec) {
	if (!isNaN(parseInt(sec))) {
		var tmpms = (new Date().getTime() - (parseInt(sec) * 1000) + 1500);
		document.getElementsByName("diffms")[0].value = "" + tmpms;
		var aa = new Date((new Date().getTime() - tmpms));
		var tmpStr = "";
		tmpStr += ((aa.getUTCHours() + 8) % 24) < 10 ? "0" + ((aa.getUTCHours() + 8) % 24) : ((aa.getUTCHours() + 8) % 24);
		tmpStr += "<span>:</span>";
		tmpStr += aa.getUTCMinutes() < 10 ? "0" + aa.getUTCMinutes() : aa.getUTCMinutes();
		tmpStr += "<span>:</span>";
		tmpStr += aa.getUTCSeconds() < 10 ? "0" + aa.getUTCSeconds() : aa.getUTCSeconds();
		document.getElementsByClassName("top-area")[0].getElementsByClassName("time-now")[0].innerHTML = tmpStr;
		delete tmpms;
		tmpms = undefined;
		delete aa;
		aa = undefined;
		delete tmpStr;
		tmpStr = undefined;
	}
}
var changePeriodTimeoutF;
function updateShowTime() {
	document.getElementsByClassName("top-area")[0].getElementsByClassName("record")[0].getElementsByTagName("span")[0].innerHTML = document.getElementsByName("nowNo")[0].value;
	if (!isNaN(parseInt(document.getElementsByName("diffms")[0].value))) {
		var tmpms = parseInt(document.getElementsByName("diffms")[0].value);
		var aa = new Date((new Date().getTime() - tmpms));
		var tmpStr = "";
		tmpStr += ((aa.getUTCHours() + 8) % 24) < 10 ? "0" + ((aa.getUTCHours() + 8) % 24) : ((aa.getUTCHours() + 8) % 24);
		tmpStr += "<span>:</span>";
		tmpStr += aa.getUTCMinutes() < 10 ? "0" + aa.getUTCMinutes() : aa.getUTCMinutes();
		tmpStr += "<span>:</span>";
		tmpStr += aa.getUTCSeconds() < 10 ? "0" + aa.getUTCSeconds() : aa.getUTCSeconds();
		document.getElementsByClassName("top-area")[0].getElementsByClassName("time-now")[0].innerHTML = tmpStr;
		delete tmpms;
		tmpms = undefined;
		delete aa;
		aa = undefined;
		delete tmpStr;
		tmpStr = undefined;
	}
	if (!isNaN(parseInt(document.getElementsByName("nowE")[0].value))) {
		var tmpms = parseInt(document.getElementsByName("diffms")[0].value);
		var bb = new Date().getTime() - tmpms;
		var aa = parseInt((parseInt(document.getElementsByName("nowE")[0].value) * 1000 - bb) / 1000);
		if (aa < 0) {
			aa = 0;
		}
		var tmpStr = "";
		if (aa > 0) {
			var a1 = parseInt(aa / (60 * 60));
			var a2 = parseInt(aa % (60 * 60));
			tmpStr += a1 < 10 ? "0" + a1 : a1;
			tmpStr += "<span>:</span>";
			if (a2 > 0) {
				a1 = parseInt(a2 / (60));
				a2 = parseInt(a2 % (60));
			} else {
				a1 = 0;
				a2 = 0;
			}
			tmpStr += a1 < 10 ? "0" + a1 : a1;
			tmpStr += "<span>:</span>";
			if (a2 > 0) {
				a1 = a2;
			} else {
				a1 = 0;
				a2 = 0;
			}
			tmpStr += a1 < 10 ? "0" + a1 : a1;
			if (tmpStr.indexOf("00<span>:</span>00<span>:</span>10") != -1 || tmpStr.indexOf("00<span>:</span>00<span>:</span>0") != -1) {
				if (a1 <= 5) {
					if (document.getElementsByName("nextNo")[0].value != "") {
						document.getElementsByClassName("count-down")[0].getElementsByClassName("number")[0].innerHTML = "<span>將进入<br/>第"
								+ document.getElementsByName("nextNo")[0].value + "期</span><p>" + a1 + "</p>";
					} else {
						document.getElementsByClassName("count-down")[0].getElementsByClassName("number")[0].innerHTML = "<span></span><p>" + a1 + "</p>";
					}
					document.getElementsByClassName("count-down")[0].style.display = "block";
					if (document.getElementsByName("nextNo")[0].value != "") {
						document.getElementsByClassName("top-area")[0].getElementsByClassName("record")[0].getElementsByTagName("span")[0].innerHTML = document
								.getElementsByName("nextNo")[0].value;
					}
					if (document.getElementsByName("nowNo")[0].value != "") {
						document.getElementsByClassName("lottery-number")[0].getElementsByClassName("winner")[0].getElementsByTagName("i")[0].innerHTML = document
								.getElementsByName("nowNo")[0].value;
					}
				}
				if (a1 == 2) {
					f_kj_timer(0);
					f_winHistory_timer(499);
					clearTimeout(changePeriodTimeoutF);
					changePeriodTimeoutF = setTimeout("changePeriodNum();", 3000);
				}
			} else {
				document.getElementsByClassName("count-down")[0].getElementsByClassName("number")[0].innerHTML = "";
				document.getElementsByClassName("count-down")[0].style.display = "none";
			}
			delete a1;
			delete a2;
			a1 = undefined;
			a2 = undefined;
		} else {
			document.getElementsByClassName("count-down")[0].getElementsByClassName("number")[0].innerHTML = "";
			document.getElementsByClassName("count-down")[0].style.display = "none";
			tmpStr = "00<span>:</span>00<span>:</span>00";
			// document.getElementsByName("nowNo")[0].value = "";
			// document.getElementsByName("nowS")[0].value = "";
			// document.getElementsByName("nowE")[0].value = "";
			// document.getElementsByName("oldNo")[0].value = "";
			// document.getElementsByName("newNo")[0].value = "";
			// document.getElementsByName("oldData")[0].value = "";
			// document.getElementsByName("newData")[0].value = "";
			// document.getElementsByClassName("lottery-number")[0].getElementsByClassName("winner")[0].getElementsByTagName("i")[0].innerHTML
			// = "";
			// document.getElementsByClassName("top-area")[0].getElementsByClassName("time-num")[0].innerHTML
			// = "00<span>:</span>00<span>:</span>00";
			f_kj_timer(0);
			f_winHistory_timer(499);
			setTimeout("changePeriodNum();", 3000);
		}
		document.getElementsByClassName("top-area")[0].getElementsByClassName("time-num")[0].innerHTML = tmpStr;
		document.getElementsByClassName("limit")[0].getElementsByClassName("time")[0].getElementsByTagName("span")[0].innerHTML = tmpStr;
		delete tmpms;
		tmpms = undefined;
		delete aa;
		aa = undefined;
		delete tmpStr;
		tmpStr = undefined;
	}
	if (document.getElementsByClassName("lottery-number")[0].getElementsByClassName("winner")[0].getElementsByTagName("i")[0].innerHTML == "") {
		document.getElementsByClassName("lottery-number")[0].style.display = "none";
	} else {
		document.getElementsByClassName("lottery-number")[0].style.display = "block";
	}
	if (document.getElementsByClassName("top-area")[0].getElementsByClassName("record")[0].getElementsByTagName("span")[0].innerHTML == "") {
		document.getElementsByClassName("top-area")[0].getElementsByClassName("record")[0].style.display = "none";
		document.getElementsByClassName("top-area")[0].getElementsByClassName("bet-time")[0].style.display = "none";
		document.getElementsByClassName("top-area")[0].getElementsByClassName("time-num")[0].style.display = "none";
	} else {
		document.getElementsByClassName("top-area")[0].getElementsByClassName("record")[0].style.display = "block";
		document.getElementsByClassName("top-area")[0].getElementsByClassName("bet-time")[0].style.display = "block";
		document.getElementsByClassName("top-area")[0].getElementsByClassName("time-num")[0].style.display = "block";
	}
}

var v_enable_load_page_count = 0;
var v_enable_load_page_time = new Date().getTime();
function disableLoadingPage() {
	v_enable_load_page_count--;
	// console_Log("disableLoadingPage
	// v_enable_load_page_count:"+v_enable_load_page_count);
	if (new Date().getTime() - v_enable_load_page_time >= 15000) {
		v_enable_load_page_count = 0;
	}
	if (v_enable_load_page_count <= 0) {
		v_enable_load_page_count = 0;
		document.getElementsByClassName("loader-page")[0].style.display = "none";
	}
	// document.getElementsByClassName("loader-page")[0].style.display =
	// "none";
}
function enableLoadingPage() {
	v_enable_load_page_count++;
	v_enable_load_page_time = new Date().getTime();
	// console_Log("enableLoadingPage
	// v_enable_load_page_count:"+v_enable_load_page_count);
	if (v_enable_load_page_count <= 0) {
		// v_enable_load_page_count = 1;
	}
	document.getElementsByClassName("loader-page")[0].style.display = "block";
}
function checkLoadingPage() {
	if (new Date().getTime() - v_enable_load_page_time >= 15000) {
		v_enable_load_page_count = 0;
		disableLoadingPage();
	}
}
setInterval(checkLoadingPage, 15000);

var v_lottory_xhr1_reload = 0;
var v_lottory_xhr1_used = 0;
var v_lottory_xhr1_time = 0;
function f_lottory_info() {
	console_Log("new f_lottory_info()!!!");
	if (v_lottory_xhr1_used != 0) {
		setTimeout("f_lottory_info();", 1000);
		return false;
	}
	v_lottory_xhr1_reload++;
	if (v_lottory_xhr1_reload >= 5) {
		if ((new Date().getTime() - v_lottory_xhr1_time) >= 15000) {
			v_lottory_xhr1_reload = 1;
		} else {
			console_Log("Too many error connetions of f_lottory_info()!");
			v_lottory_xhr1_used = 0;
			setTimeout("f_lottory_info();", 15000);
			return false;
		}
	}
	enableLoadingPage();
	v_lottory_xhr1_time = new Date().getTime();
	v_lottory_xhr1_used = 1;
	var v_lottory_xhr1 = null;
	v_lottory_xhr1 = checkXHR(null);
	var data = 1;
	var tokenId = document.getElementsByName("tokenId")[0].value;
	var accId = document.getElementsByName("accId")[0].value;
	if (typeof v_lottory_xhr1 != "undefined" && v_lottory_xhr1 != null) {
		if (v_lottory_xhr1.timeout) {
			v_lottory_xhr1.timeout = 10000;
		}
		v_lottory_xhr1.ontimeout = function() {
			if (v_lottory_xhr1 != null) {
				v_lottory_xhr1.abort();
				v_lottory_xhr1 = null;
			}
			delete v_lottory_xhr1;
			v_lottory_xhr1 = undefined;
			disableLoadingPage();
			v_lottory_xhr1_used = 0;
			setTimeout("f_lottory_info();", 1000);
			return false;
		};
		v_lottory_xhr1.onerror = function() {
			if (v_lottory_xhr1 != null) {
				v_lottory_xhr1.abort();
				v_lottory_xhr1 = null;
			}
			delete v_lottory_xhr1;
			v_lottory_xhr1 = undefined;
			disableLoadingPage();
			v_lottory_xhr1_used = 0;
			setTimeout("f_lottory_info();", 1000);
			return false;
		};
		var tmpStr = "data=" + encodeURIComponent(data) + "&tokenId=" + tokenId + "&accId=" + accId + "&LUT=" + getEle("lotterysLUT").value;
		var tmpURL = "/CTT03QueryInfo/BetInfo!queryAllLottery.php?date=" + new Date().getTime();
		if (typeof v_lottory_xhr1.onreadystatechange != "undefined" && typeof v_lottory_xhr1.readyState === "number") {
			v_lottory_xhr1.onreadystatechange = function() {
				if (v_lottory_xhr1.readyState == 4) {
					if ((v_lottory_xhr1.status >= 200 && v_lottory_xhr1.status < 300) || v_lottory_xhr1.status == 304) {
						try {
							if (!v_lottory_xhr1.responseText || v_lottory_xhr1.responseText == null || v_lottory_xhr1.responseText == "") {
								v_lottory_xhr1_used = 0;
								setTimeout("f_lottory_info();", 1000);
								return false;
							}
							if (isJSON(v_lottory_xhr1.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr1.responseText);
								if (tmpJ && tmpJ['AllLottery']) {
									getEle("lotterys").value = Strings.encode(v_lottory_xhr1.responseText);
								}
								// console_Log(tmpJ);
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								if (tmpJ && tmpJ["LUT"]) {
									getEle("lotterysLUT").value = tmpJ["LUT"];
								}
								delete tmpJ;
								tmpJ = undefined;
								if (getEle("lotterys").value == "") {
									setTimeout("f_lottory_info();", 1000);
									return false;
								} else {
									checkMenuTurnOff();
									setTimeout("f_lottory_Ratio1();", 1);
									// setTimeout("f_lottory_Handicap();",
									// 50);
									// setTimeout("f_lottory_Allkj();",
									// 100);
									// setTimeout("f_lottory_SubInfo()",
									// 300);
									v_lottory_xhr1_reload = 0;
									v_lottory_xhr1_used = 0;
									return true;
								}
							} else {
								v_lottory_xhr1_used = 0;
								setTimeout("f_lottory_info();", 1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_info error Message:" + error.message);
							console_Log("f_lottory_info error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_info facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_info error Name:" + error.name);
							v_lottory_xhr1_used = 0;
							setTimeout("f_lottory_info();", 1000);
							return false;
						} finally {
							if (v_lottory_xhr1 != null) {
								v_lottory_xhr1.abort();
								v_lottory_xhr1 = null;
							}
							delete v_lottory_xhr1;
							v_lottory_xhr1 = undefined;
							disableLoadingPage();
							v_lottory_xhr1_used = 0;
						}
						return false;
					}
				}
			};
		} else {
			v_lottory_xhr1.onload = function() {
				if (typeof v_lottory_xhr1.readyState != "number") {
					if (!v_lottory_xhr1.responseText || v_lottory_xhr1.responseText == null || v_lottory_xhr1.responseText == "") {
						if (v_lottory_xhr1 != null) {
							v_lottory_xhr1.abort();
							v_lottory_xhr1 = null;
						}
						delete v_lottory_xhr1;
						v_lottory_xhr1 = undefined;
						disableLoadingPage();
						v_lottory_xhr1_used = 0;
						setTimeout("f_lottory_info();", 1000);
						return false;
					} else {
						try {
							if (!v_lottory_xhr1.responseText || v_lottory_xhr1.responseText == null || v_lottory_xhr1.responseText == "") {
								v_lottory_xhr1_used = 0;
								setTimeout("f_lottory_info();", 1000);
								return false;
							}
							if (isJSON(v_lottory_xhr1.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr1.responseText);
								if (tmpJ && tmpJ['AllLottery']) {
									getEle("lotterys").value = Strings.encode(v_lottory_xhr1.responseText);
								}
								// console_Log(tmpJ);
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								if (tmpJ && tmpJ["LUT"]) {
									getEle("lotterysLUT").value = tmpJ["LUT"];
								}
								delete tmpJ;
								tmpJ = undefined;
								if (getEle("lotterys").value == "") {
									setTimeout("f_lottory_info();", 1000);
									return false;
								} else {
									checkMenuTurnOff();
									setTimeout("f_lottory_Ratio1();", 1);
									// setTimeout("f_lottory_Handicap();",
									// 50);
									// setTimeout("f_lottory_Allkj();",
									// 100);
									// setTimeout("f_lottory_SubInfo()",
									// 300);
									v_lottory_xhr1_reload = 0;
									v_lottory_xhr1_used = 0;
									return true;
								}
							} else {
								v_lottory_xhr1_used = 0;
								setTimeout("f_lottory_info();", 1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_info error Message:" + error.message);
							console_Log("f_lottory_info error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_info facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_info error Name:" + error.name);
							v_lottory_xhr1_used = 0;
							setTimeout("f_lottory_info();", 1000);
							return false;
						} finally {
							if (v_lottory_xhr1 != null) {
								v_lottory_xhr1.abort();
								v_lottory_xhr1 = null;
							}
							delete v_lottory_xhr1;
							v_lottory_xhr1 = undefined;
							disableLoadingPage();
							v_lottory_xhr1_used = 0;
						}
						return false;
					}
				} else if (v_lottory_xhr1.readyState == 4) {
					if ((v_lottory_xhr1.status >= 200 && v_lottory_xhr1.status < 300) || v_lottory_xhr1.status == 304) {
						try {
							if (!v_lottory_xhr1.responseText || v_lottory_xhr1.responseText == null || v_lottory_xhr1.responseText == "") {
								v_lottory_xhr1_used = 0;
								setTimeout("f_lottory_info();", 1000);
								return false;
							}
							if (isJSON(v_lottory_xhr1.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr1.responseText);
								if (tmpJ && tmpJ['AllLottery']) {
									getEle("lotterys").value = Strings.encode(v_lottory_xhr1.responseText);
								}
								// console_Log(tmpJ);
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								if (tmpJ && tmpJ["LUT"]) {
									getEle("lotterysLUT").value = tmpJ["LUT"];
								}
								delete tmpJ;
								tmpJ = undefined;
								if (getEle("lotterys").value == "") {
									setTimeout("f_lottory_info();", 1000);
									return false;
								} else {
									checkMenuTurnOff();
									setTimeout("f_lottory_Ratio1();", 1);
									// setTimeout("f_lottory_Handicap();",
									// 50);
									// setTimeout("f_lottory_Allkj();",
									// 100);
									// setTimeout("f_lottory_SubInfo()",
									// 300);
									v_lottory_xhr1_reload = 0;
									v_lottory_xhr1_used = 0;
									return true;
								}
							} else {
								v_lottory_xhr1_used = 0;
								setTimeout("f_lottory_info();", 1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_info error Message:" + error.message);
							console_Log("f_lottory_info error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_info facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_info error Name:" + error.name);
							v_lottory_xhr1_used = 0;
							setTimeout("f_lottory_info();", 1000);
							return false;
						} finally {
							if (v_lottory_xhr1 != null) {
								v_lottory_xhr1.abort();
								v_lottory_xhr1 = null;
							}
							delete v_lottory_xhr1;
							v_lottory_xhr1 = undefined;
							disableLoadingPage();
							v_lottory_xhr1_used = 0;
						}
						return false;
					}
				}
			};
		}
		v_lottory_xhr1.open("POST", tmpURL, true);
		if (v_lottory_xhr1.setRequestHeader) {
			v_lottory_xhr1.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		}
		v_lottory_xhr1.send(tmpStr.toString());
		delete tmpURL;
		delete tmpStr;
		tmpURL = undefined;
		tmpStr = undefined;
	}
}

var v_lottory_xhr2_reload = 0;
var v_lottory_xhr2_used = 0;
var v_lottory_xhr2_time = 0;
function f_lottory_Ratio1() {
	console_Log("new f_lottory_Ratio1()!!!");
	if (v_lottory_xhr2_used != 0) {
		setTimeout("f_lottory_Ratio1();", 1000);
		return false;
	}
	v_lottory_xhr2_reload++;
	if (v_lottory_xhr2_reload >= 5) {
		if ((new Date().getTime() - v_lottory_xhr2_time) >= 15000) {
			v_lottory_xhr2_reload = 1;
		} else {
			console_Log("Too many error connetions of f_lottory_Ratio1()!");
			v_lottory_xhr2_used = 0;
			setTimeout("f_lottory_Ratio1();", 15000);
			return false;
		}
	}
	enableLoadingPage();
	v_lottory_xhr2_time = new Date().getTime();
	v_lottory_xhr2_used = 1;
	var v_lottory_xhr2 = null;
	v_lottory_xhr2 = checkXHR(null);
	var data = 1;
	var tokenId = document.getElementsByName("tokenId")[0].value;
	var accId = document.getElementsByName("accId")[0].value;
	if (typeof v_lottory_xhr2 != "undefined" && v_lottory_xhr2 != null) {
		if (v_lottory_xhr2.timeout) {
			v_lottory_xhr2.timeout = 10000;
		}
		v_lottory_xhr2.ontimeout = function() {
			if (v_lottory_xhr2 != null) {
				v_lottory_xhr2.abort();
				v_lottory_xhr2 = null;
			}
			delete v_lottory_xhr2;
			v_lottory_xhr2 = undefined;
			disableLoadingPage();
			v_lottory_xhr2_used = 0;
			setTimeout("f_lottory_Ratio1();", 1000);
			return false;
		};
		v_lottory_xhr2.onerror = function() {
			if (v_lottory_xhr2 != null) {
				v_lottory_xhr2.abort();
				v_lottory_xhr2 = null;
			}
			delete v_lottory_xhr2;
			v_lottory_xhr2 = undefined;
			disableLoadingPage();
			v_lottory_xhr2_used = 0;
			setTimeout("f_lottory_Ratio1();", 1000);
			return false;
		};
		var tmpStr = "data=" + encodeURIComponent(data) + "&tokenId=" + tokenId + "&accId=" + accId;
		var tmpURL = "/CTT03QueryInfo/BaselineInfo!updateBaseline.php?date=" + new Date().getTime();
		if (typeof v_lottory_xhr2.onreadystatechange != "undefined" && typeof v_lottory_xhr2.readyState === "number") {
			v_lottory_xhr2.onreadystatechange = function() {
				if (v_lottory_xhr2.readyState == 4) {
					if ((v_lottory_xhr2.status >= 200 && v_lottory_xhr2.status < 300) || v_lottory_xhr2.status == 304) {
						try {
							f_ratio_timer(1);
							f_ratio_lf_timer(1);
							v_lottory_xhr2_reload = 0;
							v_lottory_xhr2_used = 0;
							return true;
						} catch (error) {
							console_Log("f_lottory_Ratio1 error Message:" + error.message);
							console_Log("f_lottory_Ratio1 error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_Ratio1 facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_Ratio1 error Name:" + error.name);
							v_lottory_xhr2_used = 0;
							setTimeout("f_lottory_Ratio1();", 1000);
							return false;
						} finally {
							if (v_lottory_xhr2 != null) {
								v_lottory_xhr2.abort();
								v_lottory_xhr2 = null;
							}
							delete v_lottory_xhr2;
							v_lottory_xhr2 = undefined;
							disableLoadingPage();
							v_lottory_xhr2_used = 0;
						}
						return false;
					}
				}
			};
		} else {
			v_lottory_xhr2.onload = function() {
				if (typeof v_lottory_xhr2.readyState != "number") {
					try {
						f_ratio_timer(1);
						f_ratio_lf_timer(1);
						v_lottory_xhr2_reload = 0;
						v_lottory_xhr2_used = 0;
						return true;
					} catch (error) {
						console_Log("f_lottory_Ratio1 error Message:" + error.message);
						console_Log("f_lottory_Ratio1 error Code:" + (error.number & 0xFFFF));
						console_Log("f_lottory_Ratio1 facility Code:" + (error.number >> 16 & 0x1FFF));
						console_Log("f_lottory_Ratio1 error Name:" + error.name);
						v_lottory_xhr2_used = 0;
						setTimeout("f_lottory_Ratio1();", 1000);
						return false;
					} finally {
						if (v_lottory_xhr2 != null) {
							v_lottory_xhr2.abort();
							v_lottory_xhr2 = null;
						}
						delete v_lottory_xhr2;
						v_lottory_xhr2 = undefined;
						disableLoadingPage();
						v_lottory_xhr2_used = 0;
					}
					return false;
				} else if (v_lottory_xhr2.readyState == 4) {
					if ((v_lottory_xhr2.status >= 200 && v_lottory_xhr2.status < 300) || v_lottory_xhr2.status == 304) {
						try {
							f_ratio_timer(1);
							f_ratio_lf_timer(1);
							v_lottory_xhr2_reload = 0;
							v_lottory_xhr2_used = 0;
							return true;
						} catch (error) {
							console_Log("f_lottory_Ratio1 error Message:" + error.message);
							console_Log("f_lottory_Ratio1 error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_Ratio1 facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_Ratio1 error Name:" + error.name);
							v_lottory_xhr2_used = 0;
							setTimeout("f_lottory_Ratio1();", 1000);
							return false;
						} finally {
							if (v_lottory_xhr2 != null) {
								v_lottory_xhr2.abort();
								v_lottory_xhr2 = null;
							}
							delete v_lottory_xhr2;
							v_lottory_xhr2 = undefined;
							disableLoadingPage();
							v_lottory_xhr2_used = 0;
						}
						return false;
					}
				}
			};
		}
		v_lottory_xhr2.open("POST", tmpURL, true);
		if (v_lottory_xhr2.setRequestHeader) {
			v_lottory_xhr2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		}
		v_lottory_xhr2.send(tmpStr.toString());
		delete tmpURL;
		delete tmpStr;
		tmpURL = undefined;
		tmpStr = undefined;
	}
}

var v_lottory_xhr3_reload = 0;
var v_lottory_xhr3_used = 0;
var v_lottory_xhr3_time = 0;
function f_lottory_Ratio2() {
	console_Log("new f_lottory_Ratio2()!!!");
	if (v_lottory_xhr3_used != 0) {
		f_ratio_timer(1000);
		return false;
	}
	v_lottory_xhr3_reload++;
	if (v_lottory_xhr3_reload >= 5) {
		if ((new Date().getTime() - v_lottory_xhr3_time) >= 15000) {
			v_lottory_xhr3_reload = 1;
		} else {
			console_Log("Too many error connetions of f_lottory_Ratio2()!");
			v_lottory_xhr3_used = 0;
			f_ratio_timer(15000);
			return false;
		}
	}
	// enableLoadingPage();
	v_lottory_xhr3_time = new Date().getTime();
	v_lottory_xhr3_used = 1;
	var v_lottory_xhr3 = null;
	v_lottory_xhr3 = checkXHR(null);
	var data = 1;
	var tokenId = document.getElementsByName("tokenId")[0].value;
	var accId = document.getElementsByName("accId")[0].value;
	if (typeof v_lottory_xhr3 != "undefined" && v_lottory_xhr3 != null) {
		if (v_lottory_xhr3.timeout) {
			v_lottory_xhr3.timeout = 10000;
		}
		v_lottory_xhr3.ontimeout = function(error) {
			if (v_lottory_xhr3 != null) {
				v_lottory_xhr3.abort();
				v_lottory_xhr3 = null;
			}
			delete v_lottory_xhr3;
			v_lottory_xhr3 = undefined;
			// disableLoadingPage();
			v_lottory_xhr3_used = 0;
			f_ratio_timer(1000);
			return false;
		};
		v_lottory_xhr3.onerror = function(error) {
			if (v_lottory_xhr3 != null) {
				v_lottory_xhr3.abort();
				v_lottory_xhr3 = null;
			}
			delete v_lottory_xhr3;
			v_lottory_xhr3 = undefined;
			// disableLoadingPage();
			v_lottory_xhr3_used = 0;
			f_ratio_timer(1000);
			return false;
		};
		var tmpStr = "data=" + encodeURIComponent(data) + "&tokenId=" + tokenId + "&accId=" + accId + "&LUT=" + getEle("ratiosLUT").value;
		var tmpURL = "/CTT03QueryInfo/BaselineInfo!queryBaseline.php?date=" + new Date().getTime();
		if (typeof v_lottory_xhr3.onreadystatechange != "undefined" && typeof v_lottory_xhr3.readyState === "number") {
			v_lottory_xhr3.onreadystatechange = function() {
				if (v_lottory_xhr3.readyState == 4) {
					if ((v_lottory_xhr3.status >= 200 && v_lottory_xhr3.status < 300) || v_lottory_xhr3.status == 304) {
						try {
							if (!v_lottory_xhr3.responseText || v_lottory_xhr3.responseText == null || v_lottory_xhr3.responseText == "") {
								v_lottory_xhr3_used = 0;
								f_ratio_timer(1000);
								return false;
							}
							if (isJSON(v_lottory_xhr3.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr3.responseText);
								// console_Log(tmpJ);
								if (tmpJ ? tmpJ["CurrentBaseline"] ? (getEle("ratios").value == "" || tmpJ["CurrentBaseline"] != Strings.decode(getEle("ratios").value))
										: false
										: false) {
									getEle("ratios").value = Strings.encode(v_lottory_xhr3.responseText);
									if (document.getElementsByName("l1")[0].value == "") {
										lotteryInit();
									}
									//setTimeout("checkRatio();", 50);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								if (tmpJ && tmpJ["LUT"]) {
									getEle("ratiosLUT").value = tmpJ["LUT"];
								}
								delete tmpJ;
								tmpJ = undefined;
								if (getEle("ratios").value == "") {
									f_ratio_timer(1000);
									v_lottory_xhr3_used = 0;
									return false;
								} else {
									v_lottory_xhr3_reload = 0;
									v_lottory_xhr3_used = 0;
									
									f_ratio_timer(60000);
									return true;
								}
							} else {
								v_lottory_xhr3_used = 0;
								f_ratio_timer(1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_Ratio2 error Message:" + error.message);
							console_Log("f_lottory_Ratio2 error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_Ratio2 facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_Ratio2 error Name:" + error.name);
							v_lottory_xhr3_used = 0;
							f_ratio_timer(1000);
							return false;
						} finally {
							if (v_lottory_xhr3 != null) {
								v_lottory_xhr3.abort();
								v_lottory_xhr3 = null;
							}
							delete v_lottory_xhr3;
							v_lottory_xhr3 = undefined;
							// disableLoadingPage();
							v_lottory_xhr3_used = 0;
						}
						return false;
					}
				}
			};
		} else {
			v_lottory_xhr3.onload = function() {
				if (typeof v_lottory_xhr3.readyState != "number") {
					if (!v_lottory_xhr3.responseText || v_lottory_xhr3.responseText == null || v_lottory_xhr3.responseText == "") {
						if (v_lottory_xhr3 != null) {
							v_lottory_xhr3.abort();
							v_lottory_xhr3 = null;
						}
						delete v_lottory_xhr3;
						v_lottory_xhr3 = undefined;
						// disableLoadingPage();
						v_lottory_xhr3_used = 0;
						f_ratio_timer(1000);
						return false;
					} else {
						try {
							if (isJSON(v_lottory_xhr3.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr3.responseText);
								// console_Log(tmpJ);
								if (tmpJ ? tmpJ["CurrentBaseline"] ? (getEle("ratios").value == "" || tmpJ["CurrentBaseline"] != Strings.decode(getEle("ratios").value))
										: false
										: false) {
									getEle("ratios").value = Strings.encode(v_lottory_xhr3.responseText);
									if (document.getElementsByName("l1")[0].value == "") {
										lotteryInit();
									}
									//setTimeout("checkRatio();", 50);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								if (tmpJ && tmpJ["LUT"]) {
									getEle("ratiosLUT").value = tmpJ["LUT"];
								}
								delete tmpJ;
								tmpJ = undefined;
								if (getEle("ratios").value == "") {
									f_ratio_timer(1000);
									v_lottory_xhr3_used = 0;
									return false;
								} else {
									v_lottory_xhr3_reload = 0;
									v_lottory_xhr3_used = 0;
									f_ratio_timer(60000);
									return true;
								}
							} else {
								v_lottory_xhr3_used = 0;
								f_ratio_timer(1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_Ratio2 error Message:" + error.message);
							console_Log("f_lottory_Ratio2 error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_Ratio2 facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_Ratio2 error Name:" + error.name);
							v_lottory_xhr3_used = 0;
							f_ratio_timer(1000);
							return false;
						} finally {
							if (v_lottory_xhr3 != null) {
								v_lottory_xhr3.abort();
								v_lottory_xhr3 = null;
							}
							delete v_lottory_xhr3;
							v_lottory_xhr3 = undefined;
							// disableLoadingPage();
							v_lottory_xhr3_used = 0;
						}
						return false;
					}
				} else if (v_lottory_xhr3.readyState == 4) {
					if ((v_lottory_xhr3.status >= 200 && v_lottory_xhr3.status < 300) || v_lottory_xhr3.status == 304) {
						try {
							if (!v_lottory_xhr3.responseText || v_lottory_xhr3.responseText == null || v_lottory_xhr3.responseText == "") {
								v_lottory_xhr3_used = 0;
								f_ratio_timer(1000);
								return false;
							}
							if (isJSON(v_lottory_xhr3.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr3.responseText);
								// console_Log(tmpJ);
								if (tmpJ ? tmpJ["CurrentBaseline"] ? (getEle("ratios").value == "" || tmpJ["CurrentBaseline"] != Strings.decode(getEle("ratios").value))
										: false
										: false) {
									getEle("ratios").value = Strings.encode(v_lottory_xhr3.responseText);
									if (document.getElementsByName("l1")[0].value == "") {
										lotteryInit();
									}
									//setTimeout("checkRatio();", 50);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								if (tmpJ && tmpJ["LUT"]) {
									getEle("ratiosLUT").value = tmpJ["LUT"];
								}
								delete tmpJ;
								tmpJ = undefined;
								if (getEle("ratios").value == "") {
									f_ratio_timer(1000);
									v_lottory_xhr3_used = 0;
									return false;
								} else {
									v_lottory_xhr3_reload = 0;
									v_lottory_xhr3_used = 0;
									f_ratio_timer(60000);
									return true;
								}
							} else {
								v_lottory_xhr3_used = 0;
								f_ratio_timer(1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_Ratio2 error Message:" + error.message);
							console_Log("f_lottory_Ratio2 error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_Ratio2 facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_Ratio2 error Name:" + error.name);
							v_lottory_xhr3_used = 0;
							f_ratio_timer(1000);
							return false;
						} finally {
							if (v_lottory_xhr3 != null) {
								v_lottory_xhr3.abort();
								v_lottory_xhr3 = null;
							}
							delete v_lottory_xhr3;
							v_lottory_xhr3 = undefined;
							// disableLoadingPage();
							v_lottory_xhr3_used = 0;
						}
						return false;
					}
				}
			};
		}
		v_lottory_xhr3.open("POST", tmpURL, true);
		if (v_lottory_xhr3.setRequestHeader) {
			v_lottory_xhr3.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			// v_lottory_xhr3.setRequestHeader("Accept",
			// "application/json, text/javascript, */*; q=0.01");
			// v_lottory_xhr3.setRequestHeader("X-Requested-With",
			// "XMLHttpRequest");
			// v_lottory_xhr3.setRequestHeader("Content-length",
			// tmpStr.length);
		}
		v_lottory_xhr3.send(tmpStr.toString());
		delete tmpURL;
		delete tmpStr;
		tmpURL = undefined;
		tmpStr = undefined;
	}
}

var v_lottory_xhr3_reload_lf = 0;
var v_lottory_xhr3_used_lf = 0;
var v_lottory_xhr3_time_lf = 0;
function f_lottory_Ratio2_LF() {
	console_Log("new f_lottory_Ratio2_LF()!!!");
	if (v_lottory_xhr3_used_lf != 0) {
		f_ratio_lf_timer(1000);
		return false;
	}
	v_lottory_xhr3_reload_lf++;
	if (v_lottory_xhr3_reload_lf >= 5) {
		if ((new Date().getTime() - v_lottory_xhr3_time_lf) >= 15000) {
			v_lottory_xhr3_reload_lf = 1;
		} else {
			console_Log("Too many error connetions of f_lottory_Ratio2_LF()!");
			v_lottory_xhr3_used_lf = 0;
			f_ratio_lf_timer(15000);
			return false;
		}
	}
	// enableLoadingPage();
	v_lottory_xhr3_time_lf = new Date().getTime();
	v_lottory_xhr3_used_lf = 1;
	var v_lottory_xhr3_lf = null;
	v_lottory_xhr3_lf = checkXHR(null);
	var data = 1;
	var tokenId = document.getElementsByName("tokenId")[0].value;
	var accId = document.getElementsByName("accId")[0].value;
	if (typeof v_lottory_xhr3_lf != "undefined" && v_lottory_xhr3_lf != null) {
		if (v_lottory_xhr3_lf.timeout) {
			v_lottory_xhr3_lf.timeout = 10000;
		}
		v_lottory_xhr3_lf.ontimeout = function(error) {
			if (v_lottory_xhr3_lf != null) {
				v_lottory_xhr3_lf.abort();
				v_lottory_xhr3_lf = null;
			}
			delete v_lottory_xhr3_lf;
			v_lottory_xhr3_lf = undefined;
			// disableLoadingPage();
			v_lottory_xhr3_used_lf = 0;
			f_ratio_lf_timer(1000);
			return false;
		};
		v_lottory_xhr3_lf.onerror = function(error) {
			if (v_lottory_xhr3_lf != null) {
				v_lottory_xhr3_lf.abort();
				v_lottory_xhr3_lf = null;
			}
			delete v_lottory_xhr3_lf;
			v_lottory_xhr3_lf = undefined;
			// disableLoadingPage();
			v_lottory_xhr3_used_lf = 0;
			f_ratio_lf_timer(1000);
			return false;
		};
		var tmpStr = "data=" + encodeURIComponent(data) + "&tokenId=" + tokenId + "&accId=" + accId + "&LUT=" + getEle("ratiosLFLUT").value;
		var tmpURL = "/CTT03QueryInfo/BaselineInfo!queryBaselineLF.php?date=" + new Date().getTime();
		if (typeof v_lottory_xhr3_lf.onreadystatechange != "undefined" && typeof v_lottory_xhr3_lf.readyState === "number") {
			v_lottory_xhr3_lf.onreadystatechange = function() {
				if (v_lottory_xhr3_lf.readyState == 4) {
					if ((v_lottory_xhr3_lf.status >= 200 && v_lottory_xhr3_lf.status < 300) || v_lottory_xhr3_lf.status == 304) {
						try {
							if (!v_lottory_xhr3_lf.responseText || v_lottory_xhr3_lf.responseText == null || v_lottory_xhr3_lf.responseText == "") {
								v_lottory_xhr3_used_lf = 0;
								f_ratio_lf_timer(1000);
								return false;
							}
							if (isJSON(v_lottory_xhr3_lf.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr3_lf.responseText);
								// console_Log(tmpJ);
								if (tmpJ ? tmpJ["CurrentBaselineLF"] ? (getEle("ratiosLF").value == "" || tmpJ["CurrentBaselineLF"] != Strings
										.decode(getEle("ratiosLF").value)) : false : false) {
									getEle("ratiosLF").value = Strings.encode(v_lottory_xhr3_lf.responseText);
									if (document.getElementsByName("l1")[0].value == "") {
										lotteryInit();
									}
									showBallRatioBaseline();
									//setTimeout("checkRatio();", 50);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								if (tmpJ && tmpJ["LUT"]) {
									getEle("ratiosLFLUT").value = tmpJ["LUT"];
								}
								delete tmpJ;
								tmpJ = undefined;
								if (getEle("ratiosLF").value == "") {
									f_ratio_lf_timer(1000);
									v_lottory_xhr3_used_lf = 0;
									return false;
								} else {
									v_lottory_xhr3_reload_lf = 0;
									v_lottory_xhr3_used_lf = 0;
									
									f_ratio_lf_timer(60000);
									return true;
								}
							} else {
								v_lottory_xhr3_used_lf = 0;
								f_ratio_lf_timer(1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_RatioLF2 error Message:" + error.message);
							console_Log("f_lottory_RatioLF2 error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_RatioLF2 facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_RatioLF2 error Name:" + error.name);
							v_lottory_xhr3_used_lf = 0;
							f_ratio_lf_timer(1000);
							return false;
						} finally {
							if (v_lottory_xhr3_lf != null) {
								v_lottory_xhr3_lf.abort();
								v_lottory_xhr3_lf = null;
							}
							delete v_lottory_xhr3_lf;
							v_lottory_xhr3_lf = undefined;
							// disableLoadingPage();
							v_lottory_xhr3_used_lf = 0;
						}
						return false;
					}
				}
			};
		} else {
			v_lottory_xhr3_lf.onload = function() {
				if (typeof v_lottory_xhr3_lf.readyState != "number") {
					if (!v_lottory_xhr3_lf.responseText || v_lottory_xhr3_lf.responseText == null || v_lottory_xhr3_lf.responseText == "") {
						if (v_lottory_xhr3_lf != null) {
							v_lottory_xhr3_lf.abort();
							v_lottory_xhr3_lf = null;
						}
						delete v_lottory_xhr3_lf;
						v_lottory_xhr3_lf = undefined;
						// disableLoadingPage();
						v_lottory_xhr3_used_lf = 0;
						f_ratio_lf_timer(1000);
						return false;
					} else {
						try {
							if (isJSON(v_lottory_xhr3_lf.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr3_lf.responseText);
								// console_Log(tmpJ);
								if (tmpJ ? tmpJ["CurrentBaselineLF"] ? (getEle("ratiosLF").value == "" || tmpJ["CurrentBaselineLF"] != Strings
										.decode(getEle("ratiosLF").value)) : false : false) {
									getEle("ratiosLF").value = Strings.encode(v_lottory_xhr3_lf.responseText);
									if (document.getElementsByName("l1")[0].value == "") {
										lotteryInit();
									}
									showBallRatioBaseline();
									//setTimeout("checkRatio();", 50);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								if (tmpJ && tmpJ["LUT"]) {
									getEle("ratiosLFLUT").value = tmpJ["LUT"];
								}
								delete tmpJ;
								tmpJ = undefined;
								if (getEle("ratiosLF").value == "") {
									f_ratio_lf_timer(1000);
									v_lottory_xhr3_used_lf = 0;
									return false;
								} else {
									v_lottory_xhr3_reload_lf = 0;
									v_lottory_xhr3_used_lf = 0;
									
									f_ratio_lf_timer(60000);
									return true;
								}
							} else {
								v_lottory_xhr3_used_lf = 0;
								f_ratio_lf_timer(1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_RatioLF2 error Message:" + error.message);
							console_Log("f_lottory_RatioLF2 error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_RatioLF2 facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_RatioLF2 error Name:" + error.name);
							v_lottory_xhr3_used_lf = 0;
							f_ratio_lf_timer(1000);
							return false;
						} finally {
							if (v_lottory_xhr3_lf != null) {
								v_lottory_xhr3_lf.abort();
								v_lottory_xhr3_lf = null;
							}
							delete v_lottory_xhr3_lf;
							v_lottory_xhr3_lf = undefined;
							// disableLoadingPage();
							v_lottory_xhr3_used_lf = 0;
						}
						return false;
					}
				} else if (v_lottory_xhr3_lf.readyState == 4) {
					if ((v_lottory_xhr3_lf.status >= 200 && v_lottory_xhr3_lf.status < 300) || v_lottory_xhr3_lf.status == 304) {
						try {
							if (isJSON(v_lottory_xhr3_lf.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr3_lf.responseText);
								// console_Log(tmpJ);
								if (tmpJ ? tmpJ["CurrentBaselineLF"] ? (getEle("ratiosLF").value == "" || tmpJ["CurrentBaselineLF"] != Strings
										.decode(getEle("ratiosLF").value)) : false : false) {
									getEle("ratiosLF").value = Strings.encode(v_lottory_xhr3_lf.responseText);
									if (document.getElementsByName("l1")[0].value == "") {
										lotteryInit();
									}
									showBallRatioBaseline();
									//setTimeout("checkRatio();", 50);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								if (tmpJ && tmpJ["LUT"]) {
									getEle("ratiosLFLUT").value = tmpJ["LUT"];
								}
								delete tmpJ;
								tmpJ = undefined;
								if (getEle("ratiosLF").value == "") {
									f_ratio_lf_timer(1000);
									v_lottory_xhr3_used_lf = 0;
									return false;
								} else {
									v_lottory_xhr3_reload_lf = 0;
									v_lottory_xhr3_used_lf = 0;
									
									f_ratio_lf_timer(60000);
									
									return true;
								}
							} else {
								v_lottory_xhr3_used_lf = 0;
								f_ratio_lf_timer(1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_RatioLF2 error Message:" + error.message);
							console_Log("f_lottory_RatioLF2 error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_RatioLF2 facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_RatioLF2 error Name:" + error.name);
							v_lottory_xhr3_used_lf = 0;
							f_ratio_lf_timer(1000);
							return false;
						} finally {
							if (v_lottory_xhr3_lf != null) {
								v_lottory_xhr3_lf.abort();
								v_lottory_xhr3_lf = null;
							}
							delete v_lottory_xhr3_lf;
							v_lottory_xhr3_lf = undefined;
							// disableLoadingPage();
							v_lottory_xhr3_used_lf = 0;
						}
						return false;
					}
				}
			};
		}
		v_lottory_xhr3_lf.open("POST", tmpURL, true);
		if (v_lottory_xhr3_lf.setRequestHeader) {
			v_lottory_xhr3_lf.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			// v_lottory_xhr3.setRequestHeader("Accept",
			// "application/json, text/javascript, */*; q=0.01");
			// v_lottory_xhr3.setRequestHeader("X-Requested-With",
			// "XMLHttpRequest");
			// v_lottory_xhr3.setRequestHeader("Content-length",
			// tmpStr.length);
		}
		v_lottory_xhr3_lf.send(tmpStr.toString());
		delete tmpURL;
		delete tmpStr;
		tmpURL = undefined;
		tmpStr = undefined;
	}
}

var v_lottory_xhr4_reload = 0;
var v_lottory_xhr4_used = 0;
var v_lottory_xhr4_time = 0;
function f_lottory_Handicap() {
	console_Log("new f_lottory_Handicap()!!!");
	if (v_lottory_xhr4_used != 0) {
		setTimeout("f_lottory_Handicap();", 1000);
		return false;
	}
	v_lottory_xhr4_reload++;
	if (v_lottory_xhr4_reload >= 5) {
		if ((new Date().getTime() - v_lottory_xhr4_time) >= 15000) {
			v_lottory_xhr4_reload = 1;
		} else {
			console_Log("Too many error connetions of f_lottory_Handicap()!");
			v_lottory_xhr4_used = 0;
			setTimeout("f_lottory_Handicap();", 15000);
			return false;
		}
	}
	enableLoadingPage();
	v_lottory_xhr4_time = new Date().getTime();
	v_lottory_xhr4_used = 1;
	var v_lottory_xhr4 = null;
	v_lottory_xhr4 = checkXHR(null);
	var data = 1;
	var tokenId = document.getElementsByName("tokenId")[0].value;
	var accId = document.getElementsByName("accId")[0].value;
	if (typeof v_lottory_xhr4 != "undefined" && v_lottory_xhr4 != null) {
		if (v_lottory_xhr4.timeout) {
			v_lottory_xhr4.timeout = 10000;
		}
		v_lottory_xhr4.ontimeout = function() {
			if (v_lottory_xhr4 != null) {
				v_lottory_xhr4.abort();
				v_lottory_xhr4 = null;
			}
			delete v_lottory_xhr4;
			v_lottory_xhr4 = undefined;
			disableLoadingPage();
			v_lottory_xhr4_used = 0;
			setTimeout("f_lottory_Handicap();", 1000);
			return false;
		};
		v_lottory_xhr4.onerror = function() {
			if (v_lottory_xhr4 != null) {
				v_lottory_xhr4.abort();
				v_lottory_xhr4 = null;
			}
			delete v_lottory_xhr4;
			v_lottory_xhr4 = undefined;
			disableLoadingPage();
			v_lottory_xhr4_used = 0;
			setTimeout("f_lottory_Handicap();", 1000);
			return false;
		};
		var tmpStr = "data=" + encodeURIComponent(data) + "&tokenId=" + tokenId + "&accId=" + accId;
		var tmpURL = "/CTT03QueryInfo/BetInfo!queryHandicap.php?date=" + new Date().getTime();
		if (typeof v_lottory_xhr4.onreadystatechange != "undefined" && typeof v_lottory_xhr4.readyState === "number") {
			v_lottory_xhr4.onreadystatechange = function() {
				if (v_lottory_xhr4.readyState == 4) {
					if ((v_lottory_xhr4.status >= 200 && v_lottory_xhr4.status < 300) || v_lottory_xhr4.status == 304) {
						try {
							if (!v_lottory_xhr4.responseText || v_lottory_xhr4.responseText == null || v_lottory_xhr4.responseText == "") {
								v_lottory_xhr4_used = 0;
								setTimeout("f_lottory_Handicap();", 1000);
								return false;
							}
							if (isJSON(v_lottory_xhr4.responseText)) {
								// console_Log("f_lottory_handleHandicap");
								// console_Log(JSON.parse(v_lottory_xhr4.responseText));
								var tmpJ = JSON.parse(v_lottory_xhr4.responseText);
								if (tmpJ && tmpJ["Handicap"]) {
									getEle("handicaps").value = Strings.encode(v_lottory_xhr4.responseText);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								// console_Log(JSON.parse(Strings.decode(getEle("handicaps").value)));
								// setTimeout("checkHandicap();",
								// 1);
								// if(
								// document.getElementsByName("l1")[0].value
								// == ""){
								// setTimeout("setLevel(0,0,0,0);",
								// 1);
								// setTimeout("tabSSC();",
								// 100)
								// setTimeout("checkRatio();",
								// 200);
								// }
								if (getEle("handicaps").value == "") {
									setTimeout("f_lottory_Handicap();", 1000);
									v_lottory_xhr4_used = 0;
									return false;
								} else {
									setTimeout("f_lottory_SubInfo()", 1);
									setTimeout("checkHandicap();", 1);
									v_lottory_xhr4_reload = 0;
									v_lottory_xhr4_used = 0;
									return true;
								}
							} else {
								v_lottory_xhr4_used = 0;
								setTimeout("f_lottory_Handicap();", 1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_Handicap error Message:" + error.message);
							console_Log("f_lottory_Handicap error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_Handicap facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_Handicap error Name:" + error.name);
							v_lottory_xhr4_used = 0;
							setTimeout("f_lottory_Handicap();", 1000);
							return false;
						} finally {
							if (v_lottory_xhr4 != null) {
								v_lottory_xhr4.abort();
								v_lottory_xhr4 = null;
							}
							delete v_lottory_xhr4;
							v_lottory_xhr4 = undefined;
							disableLoadingPage();
							v_lottory_xhr4_used = 0;
						}
						return false;
					}
				}
			};
		} else {
			v_lottory_xhr4.onload = function() {
				if (typeof v_lottory_xhr4.readyState != "number") {
					if (!v_lottory_xhr4.responseText || v_lottory_xhr4.responseText == null || v_lottory_xhr4.responseText == "") {
						if (v_lottory_xhr4 != null) {
							v_lottory_xhr4.abort();
							v_lottory_xhr4 = null;
						}
						delete v_lottory_xhr4;
						v_lottory_xhr4 = undefined;
						disableLoadingPage();
						v_lottory_xhr4_used = 0;
						setTimeout("f_lottory_Handicap();", 1000);
						return false;
					} else {
						try {
							if (!v_lottory_xhr4.responseText || v_lottory_xhr4.responseText == null || v_lottory_xhr4.responseText == "") {
								v_lottory_xhr4_used = 0;
								setTimeout("f_lottory_Handicap();", 1000);
								return false;
							}
							if (isJSON(v_lottory_xhr4.responseText)) {
								// console_Log("f_lottory_handleHandicap");
								// console_Log(JSON.parse(v_lottory_xhr4.responseText));
								var tmpJ = JSON.parse(v_lottory_xhr4.responseText);
								if (tmpJ && tmpJ["Handicap"]) {
									getEle("handicaps").value = Strings.encode(v_lottory_xhr4.responseText);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								// console_Log(JSON.parse(Strings.decode(getEle("handicaps").value)));
								// setTimeout("checkHandicap();",
								// 1);
								// if(
								// document.getElementsByName("l1")[0].value
								// == ""){
								// setTimeout("setLevel(0,0,0,0);",
								// 1);
								// setTimeout("tabSSC();",
								// 100)
								// setTimeout("checkRatio();",
								// 200);
								// }
								if (getEle("handicaps").value == "") {
									setTimeout("f_lottory_Handicap();", 1000);
									v_lottory_xhr4_used = 0;
									return false;
								} else {
									setTimeout("f_lottory_SubInfo()", 1);
									setTimeout("checkHandicap();", 1);
									v_lottory_xhr4_reload = 0;
									v_lottory_xhr4_used = 0;
									return true;
								}
							} else {
								v_lottory_xhr4_used = 0;
								setTimeout("f_lottory_Handicap();", 1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_Handicap error Message:" + error.message);
							console_Log("f_lottory_Handicap error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_Handicap facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_Handicap error Name:" + error.name);
							v_lottory_xhr4_used = 0;
							setTimeout("f_lottory_Handicap();", 1000);
							return false;
						} finally {
							if (v_lottory_xhr4 != null) {
								v_lottory_xhr4.abort();
								v_lottory_xhr4 = null;
							}
							delete v_lottory_xhr4;
							v_lottory_xhr4 = undefined;
							disableLoadingPage();
							v_lottory_xhr4_used = 0;
						}
						return false;
					}
				} else if (v_lottory_xhr4.readyState == 4) {
					if ((v_lottory_xhr4.status >= 200 && v_lottory_xhr4.status < 300) || v_lottory_xhr4.status == 304) {
						try {
							if (!v_lottory_xhr4.responseText || v_lottory_xhr4.responseText == null || v_lottory_xhr4.responseText == "") {
								v_lottory_xhr4_used = 0;
								setTimeout("f_lottory_Handicap();", 1000);
								return false;
							}
							if (isJSON(v_lottory_xhr4.responseText)) {
								// console_Log("f_lottory_handleHandicap");
								// console_Log(JSON.parse(v_lottory_xhr4.responseText));
								var tmpJ = JSON.parse(v_lottory_xhr4.responseText);
								if (tmpJ && tmpJ["Handicap"]) {
									getEle("handicaps").value = Strings.encode(v_lottory_xhr4.responseText);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								// console_Log(JSON.parse(Strings.decode(getEle("handicaps").value)));
								// setTimeout("checkHandicap();",
								// 1);
								// if(
								// document.getElementsByName("l1")[0].value
								// == ""){
								// setTimeout("setLevel(0,0,0,0);",
								// 1);
								// setTimeout("tabSSC();",
								// 100)
								// setTimeout("checkRatio();",
								// 200);
								// }
								if (getEle("handicaps").value == "") {
									setTimeout("f_lottory_Handicap();", 1000);
									v_lottory_xhr4_used = 0;
									return false;
								} else {
									setTimeout("f_lottory_SubInfo()", 1);
									setTimeout("checkHandicap();", 1);
									v_lottory_xhr4_reload = 0;
									v_lottory_xhr4_used = 0;
									return true;
								}
							} else {
								v_lottory_xhr4_used = 0;
								setTimeout("f_lottory_Handicap();", 1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_Handicap error Message:" + error.message);
							console_Log("f_lottory_Handicap error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_Handicap facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_Handicap error Name:" + error.name);
							v_lottory_xhr4_used = 0;
							setTimeout("f_lottory_Handicap();", 1000);
							return false;
						} finally {
							if (v_lottory_xhr4 != null) {
								v_lottory_xhr4.abort();
								v_lottory_xhr4 = null;
							}
							delete v_lottory_xhr4;
							v_lottory_xhr4 = undefined;
							disableLoadingPage();
							v_lottory_xhr4_used = 0;
						}
						return false;
					}
				}
			};
		}
		v_lottory_xhr4.open("POST", tmpURL, true);
		if (v_lottory_xhr4.setRequestHeader) {
			v_lottory_xhr4.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			// v_lottory_xhr4.setRequestHeader("Accept",
			// "application/json, text/javascript, */*; q=0.01");
			// v_lottory_xhr4.setRequestHeader("X-Requested-With",
			// "XMLHttpRequest");
			// v_lottory_xhr4.setRequestHeader("Content-length",
			// tmpStr.length);
		}
		v_lottory_xhr4.send(tmpStr.toString());
		delete tmpURL;
		delete tmpStr;
		tmpURL = undefined;
		tmpStr = undefined;
	}
}

var v_lottory_xhr5_reload = 0;
var v_lottory_xhr5_used = 0;
var v_lottory_xhr5_time = 0;
function f_lottory_Allkj() {
	console_Log("new f_lottory_Allkj()!!!");
	if (v_lottory_xhr5_used != 0) {
		f_kj_timer(1000);
		return false;
	}
	v_lottory_xhr5_reload++;
	if (v_lottory_xhr5_reload >= 5) {
		if ((new Date().getTime() - v_lottory_xhr5_time) >= 15000) {
			v_lottory_xhr5_reload = 1;
		} else {
			console_Log("Too many error connetions of f_lottory_Allkj()!");
			v_lottory_xhr5_used = 0;
			f_kj_timer(15000);
			return false;
		}
	}
	// enableLoadingPage();
	v_lottory_xhr5_time = new Date().getTime();
	v_lottory_xhr5_used = 1;
	var v_lottory_xhr5 = null;
	v_lottory_xhr5 = checkXHR(null);
	var data = 1;
	var tokenId = document.getElementsByName("tokenId")[0].value;
	var accId = document.getElementsByName("accId")[0].value;
	if (typeof v_lottory_xhr5 != "undefined" && v_lottory_xhr5 != null) {
		if (v_lottory_xhr5.timeout) {
			v_lottory_xhr5.timeout = 10000;
		}
		v_lottory_xhr5.ontimeout = function(error) {
			if (v_lottory_xhr5 != null) {
				v_lottory_xhr5.abort();
				v_lottory_xhr5 = null;
			}
			delete v_lottory_xhr5;
			v_lottory_xhr5 = undefined;
			// disableLoadingPage();
			v_lottory_xhr5_used = 0;
			f_kj_timer(1000);
			return false;
		};
		v_lottory_xhr5.onerror = function(error) {
			if (v_lottory_xhr5 != null) {
				v_lottory_xhr5.abort();
				v_lottory_xhr5 = null;
			}
			delete v_lottory_xhr5;
			v_lottory_xhr5 = undefined;
			// disableLoadingPage();
			v_lottory_xhr5_used = 0;
			f_kj_timer(1000);
			return false;
		};
		var tmpStr = "data=" + encodeURIComponent(data) + "&tokenId=" + tokenId + "&accId=" + accId + "&LUT=" + getEle("kjTimesLUT").value;
		var tmpURL = "/CTT03QueryInfo/KjInfo!queryKjTimeStatus.php?date=" + new Date().getTime();
		if (typeof v_lottory_xhr5.onreadystatechange != "undefined" && typeof v_lottory_xhr5.readyState === "number") {
			v_lottory_xhr5.onreadystatechange = function() {
				if (v_lottory_xhr5.readyState == 4) {
					if ((v_lottory_xhr5.status >= 200 && v_lottory_xhr5.status < 300) || v_lottory_xhr5.status == 304) {
						try {
							if (!v_lottory_xhr5.responseText || v_lottory_xhr5.responseText == null || v_lottory_xhr5.responseText == "") {
								v_lottory_xhr5_used = 0;
								f_kj_timer(1000);
								return false;
							}
							if (isJSON(v_lottory_xhr5.responseText)) {
								// console_Log(JSON.parse(v_lottory_xhr5.responseText));
								f_kj_timer(11000);
								var tmpJ = JSON.parse(v_lottory_xhr5.responseText);
								console_Log(tmpJ);
								if (tmpJ && tmpJ["KjTimeStatus"]) {
									if (getEle("kjTimes").value == "") {
										getEle("kjTimes").value = Strings.encode(v_lottory_xhr5.responseText);
										f_winHistory_timer(0);
									} else {
										tmpJJ = JSON.parse(Strings.decode(getEle("kjTimes").value));
										if (!tmpJJ || !tmpJJ["KjTimeStatus"] || tmpJJ["KjTimeStatus"] != tmpJ["KjTimeStatus"]) {
											getEle("kjTimes").value = Strings.encode(v_lottory_xhr5.responseText);
											f_winHistory_timer(0);
										}
									}
								}
								if (tmpJ ? tmpJ.balance : false) {
									document.getElementsByName("balance")[0].value = tmpJ.balance;
									document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = tmpJ.balance
											.substring(0, tmpJ.balance.indexOf(".") + 3);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								if (tmpJ && tmpJ["LUT"]) {
									getEle("kjTimesLUT").value = tmpJ["LUT"];
								}
								delete tmpJ;
								tmpJ = undefined;
								// setTimeout("checkRatio();",
								// 1);
								// if(
								// document.getElementsByName("l1")[0].value
								// == ""){
								// setTimeout("setLevel(0,0,0,0);",
								// 1);
								// setTimeout("tabSSC();",
								// 10)
								// setTimeout("checkRatio();",
								// 50);
								// }
								v_lottory_xhr5_reload = 0;
								v_lottory_xhr5_used = 0;
								return true;
							} else {
								v_lottory_xhr5_used = 0;
								f_kj_timer(1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_Allkj error Message:" + error.message);
							console_Log("f_lottory_Allkj error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_Allkj facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_Allkj error Name:" + error.name);
							v_lottory_xhr5_used = 0;
							f_kj_timer(1000);
							return false;
						} finally {
							if (v_lottory_xhr5 != null) {
								v_lottory_xhr5.abort();
								v_lottory_xhr5 = null;
							}
							delete v_lottory_xhr5;
							v_lottory_xhr5 = undefined;
							// disableLoadingPage();
							v_lottory_xhr5_used = 0;
						}
						return false;
					}
				}
			};
		} else {
			v_lottory_xhr5.onload = function() {
				if (typeof v_lottory_xhr5.readyState != "number") {
					if (!v_lottory_xhr5.responseText || v_lottory_xhr5.responseText == null || v_lottory_xhr5.responseText == "") {
						if (v_lottory_xhr5 != null) {
							v_lottory_xhr5.abort();
							v_lottory_xhr5 = null;
						}
						delete v_lottory_xhr5;
						v_lottory_xhr5 = undefined;
						// disableLoadingPage();
						v_lottory_xhr5_used = 0;
						f_kj_timer(1000);
						return false;
					} else {
						try {
							if (!v_lottory_xhr5.responseText || v_lottory_xhr5.responseText == null || v_lottory_xhr5.responseText == "") {
								v_lottory_xhr5_used = 0;
								f_kj_timer(1000);
								return false;
							}
							if (isJSON(v_lottory_xhr5.responseText)) {
								// console_Log(JSON.parse(v_lottory_xhr5.responseText));
								f_kj_timer(11000);
								var tmpJ = JSON.parse(v_lottory_xhr5.responseText);
								console_Log(tmpJ);
								if (tmpJ && tmpJ["KjTimeStatus"]) {
									if (getEle("kjTimes").value == "") {
										getEle("kjTimes").value = Strings.encode(v_lottory_xhr5.responseText);
										f_winHistory_timer(0);
									} else {
										tmpJJ = JSON.parse(Strings.decode(getEle("kjTimes").value));
										if (!tmpJJ || !tmpJJ["KjTimeStatus"] || tmpJJ["KjTimeStatus"] != tmpJ["KjTimeStatus"]) {
											getEle("kjTimes").value = Strings.encode(v_lottory_xhr5.responseText);
											f_winHistory_timer(0);
										}
									}
								}
								if (tmpJ ? tmpJ.balance : false) {
									document.getElementsByName("balance")[0].value = tmpJ.balance;
									document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = tmpJ.balance
											.substring(0, tmpJ.balance.indexOf(".") + 3);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								if (tmpJ && tmpJ["LUT"]) {
									getEle("kjTimesLUT").value = tmpJ["LUT"];
								}
								delete tmpJ;
								tmpJ = undefined;
								// setTimeout("checkRatio();",
								// 1);
								// if(
								// document.getElementsByName("l1")[0].value
								// == ""){
								// setTimeout("setLevel(0,0,0,0);",
								// 1);
								// setTimeout("tabSSC();",
								// 10)
								// setTimeout("checkRatio();",
								// 50);
								// }
								v_lottory_xhr5_reload = 0;
								v_lottory_xhr5_used = 0;
								return true;
							} else {
								v_lottory_xhr5_used = 0;
								f_kj_timer(1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_Allkj error Message:" + error.message);
							console_Log("f_lottory_Allkj error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_Allkj facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_Allkj error Name:" + error.name);
							v_lottory_xhr5_used = 0;
							f_kj_timer(1000);
							return false;
						} finally {
							if (v_lottory_xhr5 != null) {
								v_lottory_xhr5.abort();
								v_lottory_xhr5 = null;
							}
							delete v_lottory_xhr5;
							v_lottory_xhr5 = undefined;
							// disableLoadingPage();
							v_lottory_xhr5_used = 0;
						}
						return false;
					}
				} else if (v_lottory_xhr5.readyState == 4) {
					if ((v_lottory_xhr5.status >= 200 && v_lottory_xhr5.status < 300) || v_lottory_xhr5.status == 304) {
						try {
							if (!v_lottory_xhr5.responseText || v_lottory_xhr5.responseText == null || v_lottory_xhr5.responseText == "") {
								v_lottory_xhr5_used = 0;
								f_kj_timer(1000);
								return false;
							}
							if (isJSON(v_lottory_xhr5.responseText)) {
								// console_Log(JSON.parse(v_lottory_xhr5.responseText));
								f_kj_timer(11000);
								var tmpJ = JSON.parse(v_lottory_xhr5.responseText);
								console_Log(tmpJ);
								if (tmpJ && tmpJ["KjTimeStatus"]) {
									if (getEle("kjTimes").value == "") {
										getEle("kjTimes").value = Strings.encode(v_lottory_xhr5.responseText);
										f_winHistory_timer(0);
									} else {
										tmpJJ = JSON.parse(Strings.decode(getEle("kjTimes").value));
										if (!tmpJJ || !tmpJJ["KjTimeStatus"] || tmpJJ["KjTimeStatus"] != tmpJ["KjTimeStatus"]) {
											getEle("kjTimes").value = Strings.encode(v_lottory_xhr5.responseText);
											f_winHistory_timer(0);
										}
									}
								}
								if (tmpJ ? tmpJ.balance : false) {
									document.getElementsByName("balance")[0].value = tmpJ.balance;
									document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = tmpJ.balance
											.substring(0, tmpJ.balance.indexOf(".") + 3);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								if (tmpJ && tmpJ["LUT"]) {
									getEle("kjTimesLUT").value = tmpJ["LUT"];
								}
								delete tmpJ;
								tmpJ = undefined;
								// setTimeout("checkRatio();",
								// 1);
								// if(
								// document.getElementsByName("l1")[0].value
								// == ""){
								// setTimeout("setLevel(0,0,0,0);",
								// 1);
								// setTimeout("tabSSC();",
								// 10)
								// setTimeout("checkRatio();",
								// 50);
								// }
								v_lottory_xhr5_reload = 0;
								v_lottory_xhr5_used = 0;
								return true;
							} else {
								v_lottory_xhr5_used = 0;
								f_kj_timer(1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_Allkj error Message:" + error.message);
							console_Log("f_lottory_Allkj error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_Allkj facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_Allkj error Name:" + error.name);
							v_lottory_xhr5_used = 0;
							f_kj_timer(1000);
							return false;
						} finally {
							if (v_lottory_xhr5 != null) {
								v_lottory_xhr5.abort();
								v_lottory_xhr5 = null;
							}
							delete v_lottory_xhr5;
							v_lottory_xhr5 = undefined;
							// disableLoadingPage();
							v_lottory_xhr5_used = 0;
						}
						return false;
					}
				}
			};
		}
		v_lottory_xhr5.open("POST", tmpURL, true);
		if (v_lottory_xhr5.setRequestHeader) {
			v_lottory_xhr5.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		}
		v_lottory_xhr5.send(tmpStr.toString());
		delete tmpURL;
		delete tmpStr;
		tmpURL = undefined;
		tmpStr = undefined;
	}
}

var v_lottory_xhr6_reload = 0;
var v_lottory_xhr6_used = 0;
var v_lottory_xhr6_time = 0;
function f_lottory_SubInfo() {
	console_Log("new f_lottory_SubInfo()!!!");
	if (v_lottory_xhr6_used != 0) {
		setTimeout("f_lottory_SubInfo();", 1000);
		return false;
	}
	v_lottory_xhr6_reload++;
	if (v_lottory_xhr6_reload >= 5) {
		if ((new Date().getTime() - v_lottory_xhr6_time) >= 15000) {
			v_lottory_xhr6_reload = 1;
		} else {
			console_Log("Too many error connetions of f_lottory_SubInfo()!");
			v_lottory_xhr6_used = 0;
			setTimeout("f_lottory_SubInfo();", 15000);
			return false;
		}
	}
	enableLoadingPage();
	v_lottory_xhr6_time = new Date().getTime();
	v_lottory_xhr6_used = 1;
	var v_lottory_xhr6 = null;
	v_lottory_xhr6 = checkXHR(null);
	var data = 1;
	var tokenId = document.getElementsByName("tokenId")[0].value;
	var accId = document.getElementsByName("accId")[0].value;
	if (typeof v_lottory_xhr6 != "undefined" && v_lottory_xhr6 != null) {
		if (v_lottory_xhr6.timeout) {
			v_lottory_xhr6.timeout = 10000;
		}
		v_lottory_xhr6.ontimeout = function() {
			if (v_lottory_xhr6 != null) {
				v_lottory_xhr6.abort();
				v_lottory_xhr6 = null;
			}
			delete v_lottory_xhr6;
			v_lottory_xhr6 = undefined;
			disableLoadingPage();
			v_lottory_xhr6_used = 0;
			setTimeout("f_lottory_SubInfo();", 1000);
			return false;
		};
		v_lottory_xhr6.onerror = function() {
			if (v_lottory_xhr6 != null) {
				v_lottory_xhr6.abort();
				v_lottory_xhr6 = null;
			}
			delete v_lottory_xhr6;
			v_lottory_xhr6 = undefined;
			disableLoadingPage();
			setTimeout("f_lottory_SubInfo();", 1000);
			v_lottory_xhr6_used = 0;
			return false;
		};
		var tmpStr = "data=" + encodeURIComponent(data) + "&tokenId=" + tokenId + "&accId=" + accId;
		var tmpURL = "/CTT03QueryInfo/BetInfo!querySubOrderInfo.php?date=" + new Date().getTime();
		if (typeof v_lottory_xhr6.onreadystatechange != "undefined" && typeof v_lottory_xhr6.readyState === "number") {
			v_lottory_xhr6.onreadystatechange = function() {
				if (v_lottory_xhr6.readyState == 4) {
					if ((v_lottory_xhr6.status >= 200 && v_lottory_xhr6.status < 300) || v_lottory_xhr6.status == 304) {
						try {
							if (!v_lottory_xhr6.responseText || v_lottory_xhr6.responseText == null || v_lottory_xhr6.responseText == "") {
								v_lottory_xhr6_used = 0;
								setTimeout("f_lottory_SubInfo();", 1000);
								return false;
							}
							// getEle("subOrderInfos").value
							// = "";
							// console_Log(v_lottory_xhr6.responseText);
							if (isJSON(v_lottory_xhr6.responseText)) {
								// console_Log("f_lottory_handleSubInfo");
								// console_Log(JSON.parse(v_lottory_xhr6.responseText));
								var tmpJ = JSON.parse(v_lottory_xhr6.responseText);
								if (tmpJ && tmpJ["SubOrderInfo"]) {
									getEle("subOrderInfos").value = Strings.encode(v_lottory_xhr6.responseText);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								// setTimeout("checkRatio();",
								// 1);
								// if(
								// document.getElementsByName("l1")[0].value
								// == ""){
								// setTimeout("setLevel(0,0,0,0);",
								// 1);
								// setTimeout("tabSSC();",
								// 10)
								// setTimeout("checkRatio();",
								// 50);
								// }
								if (getEle("subOrderInfos").value == "") {
									setTimeout("f_lottory_SubInfo();", 1000);
									v_lottory_xhr6_used = 0;
									return false;
								} else {
									f_kj_timer(1);
									v_lottory_xhr6_reload = 0;
									v_lottory_xhr6_used = 0;
									return true;
								}
							} else {
								v_lottory_xhr6_used = 0;
								setTimeout("f_lottory_SubInfo();", 1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_SubInfo error Message:" + error.message);
							console_Log("f_lottory_SubInfo error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_SubInfo facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_SubInfo error Name:" + error.name);
							v_lottory_xhr6_used = 0;
							setTimeout("f_lottory_SubInfo();", 1000);
							return false;
						} finally {
							if (v_lottory_xhr6 != null) {
								v_lottory_xhr6.abort();
								v_lottory_xhr6 = null;
							}
							delete v_lottory_xhr6;
							v_lottory_xhr6 = undefined;
							disableLoadingPage();
							v_lottory_xhr6_used = 0;
						}
						return false;
					}
				}
			};
		} else {
			v_lottory_xhr6.onload = function() {
				if (typeof v_lottory_xhr6.readyState != "number") {
					if (!v_lottory_xhr6.responseText || v_lottory_xhr6.responseText == null || v_lottory_xhr6.responseText == "") {
						if (v_lottory_xhr6 != null) {
							v_lottory_xhr6.abort();
							v_lottory_xhr6 = null;
						}
						delete v_lottory_xhr6;
						v_lottory_xhr6 = undefined;
						disableLoadingPage();
						v_lottory_xhr6_used = 0;
						setTimeout("f_lottory_SubInfo();", 1000);
						return false;
					} else {
						try {
							if (!v_lottory_xhr6.responseText || v_lottory_xhr6.responseText == null || v_lottory_xhr6.responseText == "") {
								v_lottory_xhr6_used = 0;
								setTimeout("f_lottory_SubInfo();", 1000);
								return false;
							}
							// getEle("subOrderInfos").value
							// = "";
							// console_Log(v_lottory_xhr6.responseText);
							if (isJSON(v_lottory_xhr6.responseText)) {
								// console_Log("f_lottory_handleSubInfo");
								// console_Log(JSON.parse(v_lottory_xhr6.responseText));
								var tmpJ = JSON.parse(v_lottory_xhr6.responseText);
								if (tmpJ && tmpJ["SubOrderInfo"]) {
									getEle("subOrderInfos").value = Strings.encode(v_lottory_xhr6.responseText);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								// setTimeout("checkRatio();",
								// 1);
								// if(
								// document.getElementsByName("l1")[0].value
								// == ""){
								// setTimeout("setLevel(0,0,0,0);",
								// 1);
								// setTimeout("tabSSC();",
								// 10)
								// setTimeout("checkRatio();",
								// 50);
								// }
								if (getEle("subOrderInfos").value == "") {
									setTimeout("f_lottory_SubInfo();", 1000);
									v_lottory_xhr6_used = 0;
									return false;
								} else {
									f_kj_timer(1);
									v_lottory_xhr6_reload = 0;
									v_lottory_xhr6_used = 0;
									return true;
								}
							} else {
								v_lottory_xhr6_used = 0;
								setTimeout("f_lottory_SubInfo();", 1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_SubInfo error Message:" + error.message);
							console_Log("f_lottory_SubInfo error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_SubInfo facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_SubInfo error Name:" + error.name);
							v_lottory_xhr6_used = 0;
							setTimeout("f_lottory_SubInfo();", 1000);
							return false;
						} finally {
							if (v_lottory_xhr6 != null) {
								v_lottory_xhr6.abort();
								v_lottory_xhr6 = null;
							}
							delete v_lottory_xhr6;
							v_lottory_xhr6 = undefined;
							disableLoadingPage();
							v_lottory_xhr6_used = 0;
						}
						return false;
					}
				} else if (v_lottory_xhr6.readyState == 4) {
					if ((v_lottory_xhr6.status >= 200 && v_lottory_xhr6.status < 300) || v_lottory_xhr6.status == 304) {
						try {
							if (!v_lottory_xhr6.responseText || v_lottory_xhr6.responseText == null || v_lottory_xhr6.responseText == "") {
								v_lottory_xhr6_used = 0;
								setTimeout("f_lottory_SubInfo();", 1000);
								return false;
							}
							// getEle("subOrderInfos").value
							// = "";
							// console_Log(v_lottory_xhr6.responseText);
							if (isJSON(v_lottory_xhr6.responseText)) {
								// console_Log("f_lottory_handleSubInfo");
								// console_Log(JSON.parse(v_lottory_xhr6.responseText));
								var tmpJ = JSON.parse(v_lottory_xhr6.responseText);
								if (tmpJ && tmpJ["SubOrderInfo"]) {
									getEle("subOrderInfos").value = Strings.encode(v_lottory_xhr6.responseText);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								// setTimeout("checkRatio();",
								// 1);
								// if(
								// document.getElementsByName("l1")[0].value
								// == ""){
								// setTimeout("setLevel(0,0,0,0);",
								// 1);
								// setTimeout("tabSSC();",
								// 10)
								// setTimeout("checkRatio();",
								// 50);
								// }
								if (getEle("subOrderInfos").value == "") {
									setTimeout("f_lottory_SubInfo();", 1000);
									v_lottory_xhr6_used = 0;
									return false;
								} else {
									f_kj_timer(1);
									v_lottory_xhr6_reload = 0;
									v_lottory_xhr6_used = 0;
									return true;
								}
							} else {
								v_lottory_xhr6_used = 0;
								setTimeout("f_lottory_SubInfo();", 1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_SubInfo error Message:" + error.message);
							console_Log("f_lottory_SubInfo error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_SubInfo facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_SubInfo error Name:" + error.name);
							v_lottory_xhr6_used = 0;
							setTimeout("f_lottory_SubInfo();", 1000);
							return false;
						} finally {
							if (v_lottory_xhr6 != null) {
								v_lottory_xhr6.abort();
								v_lottory_xhr6 = null;
							}
							delete v_lottory_xhr6;
							v_lottory_xhr6 = undefined;
							disableLoadingPage();
							v_lottory_xhr6_used = 0;
						}
						return false;
					}
				}
			};
		}
		v_lottory_xhr6.open("POST", tmpURL, true);
		if (v_lottory_xhr6.setRequestHeader) {
			v_lottory_xhr6.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			// v_lottory_xhr6.setRequestHeader("Accept",
			// "application/json, text/javascript, */*; q=0.01");
			// v_lottory_xhr6.setRequestHeader("X-Requested-With",
			// "XMLHttpRequest");
			// v_lottory_xhr6.setRequestHeader("Content-length",
			// tmpStr.length);
		}
		v_lottory_xhr6.send(tmpStr.toString());
		delete tmpURL;
		delete tmpStr;
		tmpURL = undefined;
		tmpStr = undefined;
	}
}

var v_lottory_xhr7_reload = 0;
var v_lottory_xhr7_used = 0;
var v_lottory_xhr7_time = 0;
function f_lottory_queryAllInfo() {
	console_Log("new f_lottory_queryAllInfo()!!!");
	if (v_lottory_xhr7_used != 0) {
		setTimeout("f_lottory_queryAllInfo()", 1000);
		return false;
	}
	v_lottory_xhr7_reload++;
	if (v_lottory_xhr7_reload >= 5) {
		if ((new Date().getTime() - v_lottory_xhr7_time) >= 15000) {
			v_lottory_xhr7_reload = 1;
		} else {
			console_Log("Too many error connetions of f_lottory_queryAllInfo()!");
			v_lottory_xhr7_used = 0;
			setTimeout("f_lottory_queryAllInfo()", 15000);
			return false;
		}
	}
	enableLoadingPage();
	v_lottory_xhr7_time = new Date().getTime();
	v_lottory_xhr7_used = 1;
	var v_lottory_xhr7 = null;
	v_lottory_xhr7 = checkXHR(null);
	var tokenId = document.getElementsByName("tokenId")[0].value;
	var accId = document.getElementsByName("accId")[0].value;
	if (typeof v_lottory_xhr7 != "undefined" && v_lottory_xhr7 != null) {
		if (v_lottory_xhr7.timeout) {
			v_lottory_xhr7.timeout = 10000;
		}
		v_lottory_xhr7.ontimeout = function() {
			if (v_lottory_xhr7 != null) {
				v_lottory_xhr7.abort();
				v_lottory_xhr7 = null;
			}
			delete v_lottory_xhr7;
			v_lottory_xhr7 = undefined;
			disableLoadingPage();
			v_lottory_xhr7_used = 0;
			setTimeout("f_lottory_queryAllInfo()", 1000);
			return false;
		};
		v_lottory_xhr7.onerror = function() {
			if (v_lottory_xhr7 != null) {
				v_lottory_xhr7.abort();
				v_lottory_xhr7 = null;
			}
			delete v_lottory_xhr7;
			v_lottory_xhr7 = undefined;
			disableLoadingPage();
			v_lottory_xhr7_used = 0;
			setTimeout("f_lottory_queryAllInfo()", 1000);
			return false;
		};
		var tmpStr = "tokenId=" + tokenId + "&accId=" + accId;
		var tmpURL = "/CTT03QueryInfo/BetInfo!queryAllInfo.php?date=" + new Date().getTime();
		if (typeof v_lottory_xhr7.onreadystatechange != "undefined" && typeof v_lottory_xhr7.readyState === "number") {
			v_lottory_xhr7.onreadystatechange = function() {
				if (v_lottory_xhr7.readyState == 4) {
					if ((v_lottory_xhr7.status >= 200 && v_lottory_xhr7.status < 300) || v_lottory_xhr7.status == 304) {
						try {
							if (!v_lottory_xhr7.responseText || v_lottory_xhr7.responseText == null || v_lottory_xhr7.responseText == "") {
								v_lottory_xhr7_used = 0;
								setTimeout("f_lottory_queryAllInfo()", 1000);
								return false;
							}
							if (isJSON(v_lottory_xhr7.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr7.responseText);
								console_Log(tmpJ);
								if (tmpJ ? tmpJ["balance"] : false) {
									document.getElementsByName("balance")[0].value = tmpJ.balance;
									document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = tmpJ.balance
											.substring(0, tmpJ.balance.indexOf(".") + 3);
								}
								var tmpA = {};
								if (tmpJ["AllLottery"] ? tmpJ["AllLottery_LUT"] : false) {
									tmpA = {};
									tmpA["AllLottery"] = tmpJ["AllLottery"];
									getEle("lotterys").value = Strings.encode(JSON.stringify(tmpA));
									getEle("lotterysLUT").value = tmpJ["AllLottery_LUT"];
								}
								if (tmpJ["AllLotteryTitle"]) {
									tmpA = {};
									tmpA["AllLotteryTitle"] = tmpJ["AllLotteryTitle"];
									getEle("allLotteryTitle").value = Strings.encode(JSON.stringify(tmpA));
									setLotteryTitleHTML();
								}
								if (tmpJ["CurrentBaseline"] ? tmpJ["CurrentBaseline_LUT"] : false) {
									tmpA = {};
									tmpA["CurrentBaseline"] = tmpJ["CurrentBaseline"];
									getEle("ratios").value = Strings.encode(JSON.stringify(tmpA));
									getEle("ratiosLUT").value = tmpJ["CurrentBaseline_LUT"];
									//setTimeout("checkRatio();", 0);
								}

								if (tmpJ["CurrentBaselineLF"] ? tmpJ["CurrentBaselineLF_LUT"] : false) {
									tmpA = {};
									tmpA["CurrentBaselineLF"] = tmpJ["CurrentBaselineLF"];
									getEle("ratiosLF").value = Strings.encode(JSON.stringify(tmpA));
									getEle("ratiosLFLUT").value = tmpJ["CurrentBaselineLF_LUT"];
									//setTimeout("checkRatio();", 0);
								}
								if (tmpJ["SubOrderInfo"] ? tmpJ["SubOrderInfoNoOfBet"] : false) {
									tmpA = {};
									tmpA["SubOrderInfo"] = tmpJ["SubOrderInfo"];
									tmpA["SubOrderInfoNoOfBet"] = tmpJ["SubOrderInfoNoOfBet"];
									getEle("subOrderInfos").value = Strings.encode(JSON.stringify(tmpA));
								}
								if (tmpJ["Handicap"]) {
									tmpA = {};
									tmpA["Handicap"] = tmpJ["Handicap"];
									getEle("handicaps").value = Strings.encode(JSON.stringify(tmpA));
									setTimeout("lotteryInit();", 0);
									setTimeout("checkHandicap();", 0);
								}
								if (tmpJ["KjTimeStatus"] ? tmpJ["KjTimeStatus_LUT"] : false) {
									tmpA = {};
									tmpA["KjTimeStatus"] = tmpJ["KjTimeStatus"];
									getEle("kjTimes").value = Strings.encode(JSON.stringify(tmpA));
									getEle("kjTimesLUT").value = tmpJ["KjTimeStatus_LUT"];
								}

								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								delete tmpA;
								tmpA = undefined;
								v_lottory_xhr7_reload = 0;
								v_lottory_xhr7_used = 0;
								return true;
							} else {
								v_lottory_xhr7_used = 0;
								setTimeout("f_lottory_queryAllInfo()", 1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_queryAllInfo error Message:" + error.message);
							console_Log("f_lottory_queryAllInfo error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_queryAllInfo facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_queryAllInfo error Name:" + error.name);
							v_lottory_xhr7_used = 0;
							setTimeout("f_lottory_queryAllInfo()", 1000);
							return false;
						} finally {
							if (v_lottory_xhr7 != null) {
								v_lottory_xhr7.abort();
								v_lottory_xhr7 = null;
							}
							delete v_lottory_xhr7;
							v_lottory_xhr7 = undefined;
							disableLoadingPage();
							v_lottory_xhr7_used = 0;
						}
						return false;
					}
				}
			};
		} else {
			v_lottory_xhr7.onload = function() {
				if (typeof v_lottory_xhr7.readyState != "number") {
					if (!v_lottory_xhr7.responseText || v_lottory_xhr7.responseText == null || v_lottory_xhr7.responseText == "") {
						if (v_lottory_xhr7 != null) {
							v_lottory_xhr7.abort();
							v_lottory_xhr7 = null;
						}
						delete v_lottory_xhr7;
						v_lottory_xhr7 = undefined;
						disableLoadingPage();
						v_lottory_xhr7_used = 0;
						setTimeout("f_lottory_queryAllInfo()", 1000);
						return false;
					} else {
						try {
							if (!v_lottory_xhr7.responseText || v_lottory_xhr7.responseText == null || v_lottory_xhr7.responseText == "") {
								v_lottory_xhr7_used = 0;
								setTimeout("f_lottory_queryAllInfo()", 1000);
								return false;
							}
							if (isJSON(v_lottory_xhr7.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr7.responseText);
								console_Log(tmpJ);
								if (tmpJ ? tmpJ["balance"] : false) {
									document.getElementsByName("balance")[0].value = tmpJ.balance;
									document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = tmpJ.balance
											.substring(0, tmpJ.balance.indexOf(".") + 3);
								}
								var tmpA = {};
								if (tmpJ["AllLottery"] ? tmpJ["AllLottery_LUT"] : false) {
									tmpA = {};
									tmpA["AllLottery"] = tmpJ["AllLottery"];
									getEle("lotterys").value = Strings.encode(JSON.stringify(tmpA));
									getEle("lotterysLUT").value = tmpJ["AllLottery_LUT"];
								}
								if (tmpJ["AllLotteryTitle"]) {
									tmpA = {};
									tmpA["AllLotteryTitle"] = tmpJ["AllLotteryTitle"];
									getEle("allLotteryTitle").value = Strings.encode(JSON.stringify(tmpA));
									setLotteryTitleHTML();
								}
								if (tmpJ["CurrentBaseline"] ? tmpJ["CurrentBaseline_LUT"] : false) {
									tmpA = {};
									tmpA["CurrentBaseline"] = tmpJ["CurrentBaseline"];
									getEle("ratios").value = Strings.encode(JSON.stringify(tmpA));
									getEle("ratiosLUT").value = tmpJ["CurrentBaseline_LUT"];
									//setTimeout("checkRatio();", 0);
								}

								if (tmpJ["CurrentBaselineLF"] ? tmpJ["CurrentBaselineLF_LUT"] : false) {
									tmpA = {};
									tmpA["CurrentBaselineLF"] = tmpJ["CurrentBaselineLF"];
									getEle("ratiosLF").value = Strings.encode(JSON.stringify(tmpA));
									getEle("ratiosLFLUT").value = tmpJ["CurrentBaselineLF_LUT"];
									//setTimeout("checkRatio();", 0);
								}
								if (tmpJ["SubOrderInfo"] ? tmpJ["SubOrderInfoNoOfBet"] : false) {
									tmpA = {};
									tmpA["SubOrderInfo"] = tmpJ["SubOrderInfo"];
									tmpA["SubOrderInfoNoOfBet"] = tmpJ["SubOrderInfoNoOfBet"];
									getEle("subOrderInfos").value = Strings.encode(JSON.stringify(tmpA));
								}
								if (tmpJ["Handicap"]) {
									tmpA = {};
									tmpA["Handicap"] = tmpJ["Handicap"];
									getEle("handicaps").value = Strings.encode(JSON.stringify(tmpA));
									setTimeout("lotteryInit();", 0);
									setTimeout("checkHandicap();", 0);
								}
								if (tmpJ["KjTimeStatus"] ? tmpJ["KjTimeStatus_LUT"] : false) {
									tmpA = {};
									tmpA["KjTimeStatus"] = tmpJ["KjTimeStatus"];
									getEle("kjTimes").value = Strings.encode(JSON.stringify(tmpA));
									getEle("kjTimesLUT").value = tmpJ["KjTimeStatus_LUT"];
								}

								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								delete tmpA;
								tmpA = undefined;
								v_lottory_xhr7_reload = 0;
								v_lottory_xhr7_used = 0;
								return true;
							} else {
								v_lottory_xhr7_used = 0;
								setTimeout("f_lottory_queryAllInfo()", 1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_queryAllInfo error Message:" + error.message);
							console_Log("f_lottory_queryAllInfo error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_queryAllInfo facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_queryAllInfo error Name:" + error.name);
							v_lottory_xhr7_used = 0;
							setTimeout("f_lottory_queryAllInfo()", 1000);
							return false;
						} finally {
							if (v_lottory_xhr7 != null) {
								v_lottory_xhr7.abort();
								v_lottory_xhr7 = null;
							}
							delete v_lottory_xhr7;
							v_lottory_xhr7 = undefined;
							disableLoadingPage();
							v_lottory_xhr7_used = 0;
						}
						return false;
					}
				} else if (v_lottory_xhr7.readyState == 4) {
					if ((v_lottory_xhr7.status >= 200 && v_lottory_xhr7.status < 300) || v_lottory_xhr7.status == 304) {
						try {
							if (!v_lottory_xhr7.responseText || v_lottory_xhr7.responseText == null || v_lottory_xhr7.responseText == "") {
								v_lottory_xhr7_used = 0;
								setTimeout("f_lottory_queryAllInfo()", 1000);
								return false;
							}
							if (isJSON(v_lottory_xhr7.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr7.responseText);
								console_Log(tmpJ);
								if (tmpJ ? tmpJ["balance"] : false) {
									document.getElementsByName("balance")[0].value = tmpJ.balance;
									document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = tmpJ.balance
											.substring(0, tmpJ.balance.indexOf(".") + 3);
								}
								var tmpA = {};
								if (tmpJ["AllLottery"] ? tmpJ["AllLottery_LUT"] : false) {
									tmpA = {};
									tmpA["AllLottery"] = tmpJ["AllLottery"];
									getEle("lotterys").value = Strings.encode(JSON.stringify(tmpA));
									getEle("lotterysLUT").value = tmpJ["AllLottery_LUT"];
								}
								if (tmpJ["AllLotteryTitle"]) {
									tmpA = {};
									tmpA["AllLotteryTitle"] = tmpJ["AllLotteryTitle"];
									getEle("allLotteryTitle").value = Strings.encode(JSON.stringify(tmpA));
									setLotteryTitleHTML();
								}
								if (tmpJ["CurrentBaseline"] ? tmpJ["CurrentBaseline_LUT"] : false) {
									tmpA = {};
									tmpA["CurrentBaseline"] = tmpJ["CurrentBaseline"];
									getEle("ratios").value = Strings.encode(JSON.stringify(tmpA));
									getEle("ratiosLUT").value = tmpJ["CurrentBaseline_LUT"];
									//setTimeout("checkRatio();", 0);
								}

								if (tmpJ["CurrentBaselineLF"] ? tmpJ["CurrentBaselineLF_LUT"] : false) {
									tmpA = {};
									tmpA["CurrentBaselineLF"] = tmpJ["CurrentBaselineLF"];
									getEle("ratiosLF").value = Strings.encode(JSON.stringify(tmpA));
									getEle("ratiosLFLUT").value = tmpJ["CurrentBaselineLF_LUT"];
									//setTimeout("checkRatio();", 0);
								}
								if (tmpJ["SubOrderInfo"] ? tmpJ["SubOrderInfoNoOfBet"] : false) {
									tmpA = {};
									tmpA["SubOrderInfo"] = tmpJ["SubOrderInfo"];
									tmpA["SubOrderInfoNoOfBet"] = tmpJ["SubOrderInfoNoOfBet"];
									getEle("subOrderInfos").value = Strings.encode(JSON.stringify(tmpA));
								}
								if (tmpJ["Handicap"]) {
									tmpA = {};
									tmpA["Handicap"] = tmpJ["Handicap"];
									getEle("handicaps").value = Strings.encode(JSON.stringify(tmpA));
									setTimeout("lotteryInit();", 0);
									setTimeout("checkHandicap();", 0);
								}
								if (tmpJ["KjTimeStatus"] ? tmpJ["KjTimeStatus_LUT"] : false) {
									tmpA = {};
									tmpA["KjTimeStatus"] = tmpJ["KjTimeStatus"];
									getEle("kjTimes").value = Strings.encode(JSON.stringify(tmpA));
									getEle("kjTimesLUT").value = tmpJ["KjTimeStatus_LUT"];
								}

								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								delete tmpA;
								tmpA = undefined;
								v_lottory_xhr7_reload = 0;
								v_lottory_xhr7_used = 0;
								return true;
							} else {
								v_lottory_xhr7_used = 0;
								setTimeout("f_lottory_queryAllInfo()", 1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_queryAllInfo error Message:" + error.message);
							console_Log("f_lottory_queryAllInfo error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_queryAllInfo facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_queryAllInfo error Name:" + error.name);
							v_lottory_xhr7_used = 0;
							setTimeout("f_lottory_queryAllInfo()", 1000);
							return false;
						} finally {
							if (v_lottory_xhr7 != null) {
								v_lottory_xhr7.abort();
								v_lottory_xhr7 = null;
							}
							delete v_lottory_xhr7;
							v_lottory_xhr7 = undefined;
							disableLoadingPage();
							v_lottory_xhr7_used = 0;
						}
						return false;
					}
				}
			};
		}
		v_lottory_xhr7.open("POST", tmpURL, true);
		if (v_lottory_xhr7.setRequestHeader) {
			v_lottory_xhr7.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		}
		v_lottory_xhr7.send(tmpStr.toString());
		delete tmpURL;
		delete tmpStr;
		tmpURL = undefined;
		tmpStr = undefined;
	}
}

var v_lottory_xhr8_reload = 0;
var v_lottory_xhr8_used = 0;
var v_lottory_xhr8_time = 0;
function f_lottory_refreshBalence() {
	console_Log("new f_lottory_refreshBalence()!!!");
	if (v_lottory_xhr8_used != 0) {
		setTimeout("f_lottory_refreshBalence();", 1000);
		return false;
	}
	v_lottory_xhr8_reload++;
	if (v_lottory_xhr8_reload >= 5) {
		if ((new Date().getTime() - v_lottory_xhr8_time) >= 15000) {
			v_lottory_xhr8_reload = 1;
		} else {
			console_Log("Too many error connetions of f_lottory_refreshBalence()!");
			v_lottory_xhr8_used = 0;
			setTimeout("f_lottory_refreshBalence();", 15000);
			return false;
		}
	}
	addClass(document.getElementsByClassName("account")[0].getElementsByClassName("btn-refresh")[0], "invisible");
	v_lottory_xhr8_time = new Date().getTime();
	v_lottory_xhr8_used = 1;
	var v_lottory_xhr8 = null;
	v_lottory_xhr8 = checkXHR(null);
	var data = 1;
	var tokenId = document.getElementsByName("tokenId")[0].value;
	var accId = document.getElementsByName("accId")[0].value;
	if (typeof v_lottory_xhr8 != "undefined" && v_lottory_xhr8 != null) {
		if (v_lottory_xhr8.timeout) {
			v_lottory_xhr8.timeout = 10000;
		}
		v_lottory_xhr8.ontimeout = function() {
			if (v_lottory_xhr8 != null) {
				v_lottory_xhr8.abort();
				v_lottory_xhr8 = null;
			}
			delete v_lottory_xhr8;
			v_lottory_xhr8 = undefined;
			setTimeout('removeClass(document.getElementsByClassName("account")[0].getElementsByClassName("btn-refresh")[0], "invisible");', 500);
			v_lottory_xhr8_used = 0;
			setTimeout("f_lottory_refreshBalence();", 1000);
			return false;
		};
		v_lottory_xhr8.onerror = function() {
			if (v_lottory_xhr8 != null) {
				v_lottory_xhr8.abort();
				v_lottory_xhr8 = null;
			}
			delete v_lottory_xhr8;
			v_lottory_xhr8 = undefined;
			setTimeout('removeClass(document.getElementsByClassName("account")[0].getElementsByClassName("btn-refresh")[0], "invisible");', 500);
			setTimeout("f_lottory_refreshBalence();", 1000);
			v_lottory_xhr8_used = 0;
			return false;
		};
		var tmpStr = "data=" + encodeURIComponent(data) + "&tokenId=" + tokenId + "&accId=" + accId;
		var tmpURL = "/CTT03QueryInfo/BetInfo!queryBalance.php?date=" + new Date().getTime();
		if (typeof v_lottory_xhr8.onreadystatechange != "undefined" && typeof v_lottory_xhr8.readyState === "number") {
			v_lottory_xhr8.onreadystatechange = function() {
				if (v_lottory_xhr8.readyState == 4) {
					if ((v_lottory_xhr8.status >= 200 && v_lottory_xhr8.status < 300) || v_lottory_xhr8.status == 304) {
						try {
							if (!v_lottory_xhr8.responseText || v_lottory_xhr8.responseText == null || v_lottory_xhr8.responseText == "") {
								v_lottory_xhr8_used = 0;
								setTimeout("f_lottory_refreshBalence();", 1000);
								return false;
							}
							if (isJSON(v_lottory_xhr8.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr8.responseText);
								if (tmpJ ? tmpJ.balance : false) {
									document.getElementsByName("balance")[0].value = tmpJ.balance;
									document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = tmpJ.balance
											.substring(0, tmpJ.balance.indexOf(".") + 3);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								if (getEle("balance").value == "") {
									setTimeout("f_lottory_refreshBalence();", 1000);
									v_lottory_xhr8_used = 0;
									return false;
								} else {
									v_lottory_xhr8_reload = 0;
									v_lottory_xhr8_used = 0;
									return true;
								}
							} else {
								v_lottory_xhr8_used = 0;
								setTimeout("f_lottory_refreshBalence();", 1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_refreshBalence error Message:" + error.message);
							console_Log("f_lottory_refreshBalence error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_refreshBalence facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_refreshBalence error Name:" + error.name);
							v_lottory_xhr8_used = 0;
							setTimeout("f_lottory_refreshBalence();", 1000);
							return false;
						} finally {
							if (v_lottory_xhr8 != null) {
								v_lottory_xhr8.abort();
								v_lottory_xhr8 = null;
							}
							delete v_lottory_xhr8;
							v_lottory_xhr8 = undefined;
							setTimeout('removeClass(document.getElementsByClassName("account")[0].getElementsByClassName("btn-refresh")[0], "invisible");', 500);
							v_lottory_xhr8_used = 0;
						}
						return false;
					}
				}
			};
		} else {
			v_lottory_xhr8.onload = function() {
				if (typeof v_lottory_xhr8.readyState != "number") {
					if (!v_lottory_xhr8.responseText || v_lottory_xhr8.responseText == null || v_lottory_xhr8.responseText == "") {
						if (v_lottory_xhr8 != null) {
							v_lottory_xhr8.abort();
							v_lottory_xhr8 = null;
						}
						delete v_lottory_xhr8;
						v_lottory_xhr8 = undefined;
						setTimeout('removeClass(document.getElementsByClassName("account")[0].getElementsByClassName("btn-refresh")[0], "invisible");', 500);
						v_lottory_xhr8_used = 0;
						setTimeout("f_lottory_refreshBalence();", 1000);
						return false;
					} else {
						try {
							if (!v_lottory_xhr8.responseText || v_lottory_xhr8.responseText == null || v_lottory_xhr8.responseText == "") {
								v_lottory_xhr8_used = 0;
								setTimeout("f_lottory_refreshBalence();", 1000);
								return false;
							}
							if (isJSON(v_lottory_xhr8.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr8.responseText);
								if (tmpJ ? tmpJ.balance : false) {
									document.getElementsByName("balance")[0].value = tmpJ.balance;
									document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = tmpJ.balance
											.substring(0, tmpJ.balance.indexOf(".") + 3);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								if (getEle("balance").value == "") {
									setTimeout("f_lottory_refreshBalence();", 1000);
									v_lottory_xhr8_used = 0;
									return false;
								} else {
									v_lottory_xhr8_reload = 0;
									v_lottory_xhr8_used = 0;
									return true;
								}
							} else {
								v_lottory_xhr8_used = 0;
								setTimeout("f_lottory_refreshBalence();", 1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_refreshBalence error Message:" + error.message);
							console_Log("f_lottory_refreshBalence error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_refreshBalence facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_refreshBalence error Name:" + error.name);
							v_lottory_xhr8_used = 0;
							setTimeout("f_lottory_refreshBalence();", 1000);
							return false;
						} finally {
							if (v_lottory_xhr8 != null) {
								v_lottory_xhr8.abort();
								v_lottory_xhr8 = null;
							}
							delete v_lottory_xhr8;
							v_lottory_xhr8 = undefined;
							setTimeout('removeClass(document.getElementsByClassName("account")[0].getElementsByClassName("btn-refresh")[0], "invisible");', 500);
							v_lottory_xhr8_used = 0;
						}
						return false;
					}
				} else if (v_lottory_xhr8.readyState == 4) {
					if ((v_lottory_xhr8.status >= 200 && v_lottory_xhr8.status < 300) || v_lottory_xhr8.status == 304) {
						try {
							if (!v_lottory_xhr8.responseText || v_lottory_xhr8.responseText == null || v_lottory_xhr8.responseText == "") {
								v_lottory_xhr8_used = 0;
								setTimeout("f_lottory_refreshBalence();", 1000);
								return false;
							}
							if (isJSON(v_lottory_xhr8.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr8.responseText);
								if (tmpJ ? tmpJ.balance : false) {
									document.getElementsByName("balance")[0].value = tmpJ.balance;
									document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = tmpJ.balance
											.substring(0, tmpJ.balance.indexOf(".") + 3);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								if (getEle("balance").value == "") {
									setTimeout("f_lottory_refreshBalence();", 1000);
									v_lottory_xhr8_used = 0;
									return false;
								} else {
									v_lottory_xhr8_reload = 0;
									v_lottory_xhr8_used = 0;
									return true;
								}
							} else {
								v_lottory_xhr8_used = 0;
								setTimeout("f_lottory_refreshBalence();", 1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_refreshBalence error Message:" + error.message);
							console_Log("f_lottory_refreshBalence error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_refreshBalence facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_refreshBalence error Name:" + error.name);
							v_lottory_xhr8_used = 0;
							setTimeout("f_lottory_refreshBalence();", 1000);
							return false;
						} finally {
							if (v_lottory_xhr8 != null) {
								v_lottory_xhr8.abort();
								v_lottory_xhr8 = null;
							}
							delete v_lottory_xhr8;
							v_lottory_xhr8 = undefined;
							setTimeout('removeClass(document.getElementsByClassName("account")[0].getElementsByClassName("btn-refresh")[0], "invisible");', 500);
							v_lottory_xhr8_used = 0;
						}
						return false;
					}
				}
			};
		}
		v_lottory_xhr8.open("POST", tmpURL, true);
		if (v_lottory_xhr8.setRequestHeader) {
			v_lottory_xhr8.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		}
		v_lottory_xhr8.send(tmpStr.toString());
		delete tmpURL;
		delete tmpStr;
		tmpURL = undefined;
		tmpStr = undefined;
	}
}

var v_lottory_xhr9_reload = 0;
var v_lottory_xhr9_used = 0;
var v_lottory_xhr9_time = 0;
function f_lottory_getBalence() {
	console_Log("new f_lottory_getBalence()!!!");
	if (v_lottory_xhr9_used != 0) {
		f_balance_timer(1000);
		return false;
	}
	v_lottory_xhr9_reload++;
	if (v_lottory_xhr9_reload >= 5) {
		if ((new Date().getTime() - v_lottory_xhr9_time) >= 15000) {
			v_lottory_xhr9_reload = 1;
		} else {
			console_Log("Too many error connetions of f_lottory_getBalence()!");
			v_lottory_xhr9_used = 0;
			f_balance_timer(15000);
			return false;
		}
	}
	v_lottory_xhr9_time = new Date().getTime();
	v_lottory_xhr9_used = 1;
	var v_lottory_xhr9 = null;
	v_lottory_xhr9 = checkXHR(null);
	var data = 1;
	var tokenId = document.getElementsByName("tokenId")[0].value;
	var accId = document.getElementsByName("accId")[0].value;
	if (typeof v_lottory_xhr9 != "undefined" && v_lottory_xhr9 != null) {
		if (v_lottory_xhr9.timeout) {
			v_lottory_xhr9.timeout = 10000;
		}
		v_lottory_xhr9.ontimeout = function() {
			if (v_lottory_xhr9 != null) {
				v_lottory_xhr9.abort();
				v_lottory_xhr9 = null;
			}
			delete v_lottory_xhr9;
			v_lottory_xhr9 = undefined;
			v_lottory_xhr9_used = 0;
			f_balance_timer(1000);
			return false;
		};
		v_lottory_xhr9.onerror = function() {
			if (v_lottory_xhr9 != null) {
				v_lottory_xhr9.abort();
				v_lottory_xhr9 = null;
			}
			delete v_lottory_xhr9;
			v_lottory_xhr9 = undefined;
			f_balance_timer(1000);
			v_lottory_xhr9_used = 0;
			return false;
		};
		var tmpStr = "data=" + encodeURIComponent(data) + "&tokenId=" + tokenId + "&accId=" + accId;
		var tmpURL = "/CTT03QueryInfo/BetInfo!queryBalance.php?date=" + new Date().getTime();
		if (typeof v_lottory_xhr9.onreadystatechange != "undefined" && typeof v_lottory_xhr9.readyState === "number") {
			v_lottory_xhr9.onreadystatechange = function() {
				if (v_lottory_xhr9.readyState == 4) {
					if ((v_lottory_xhr9.status >= 200 && v_lottory_xhr9.status < 300) || v_lottory_xhr9.status == 304) {
						try {
							if (!v_lottory_xhr9.responseText || v_lottory_xhr9.responseText == null || v_lottory_xhr9.responseText == "") {
								v_lottory_xhr9_used = 0;
								f_balance_timer(1000);
								return false;
							}
							if (isJSON(v_lottory_xhr9.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr9.responseText);
								if (tmpJ ? tmpJ.balance : false) {
									document.getElementsByName("balance")[0].value = tmpJ.balance;
									document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = tmpJ.balance
											.substring(0, tmpJ.balance.indexOf(".") + 3);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								if (getEle("balance").value == "") {
									f_balance_timer(1000);
									v_lottory_xhr9_used = 0;
									return false;
								} else {
									v_lottory_xhr9_reload = 0;
									v_lottory_xhr9_used = 0;
									return true;
								}
							} else {
								v_lottory_xhr9_used = 0;
								f_balance_timer(1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_getBalence error Message:" + error.message);
							console_Log("f_lottory_getBalence error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_getBalence facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_getBalence error Name:" + error.name);
							v_lottory_xhr9_used = 0;
							f_balance_timer(1000);
							return false;
						} finally {
							if (v_lottory_xhr9 != null) {
								v_lottory_xhr9.abort();
								v_lottory_xhr9 = null;
							}
							delete v_lottory_xhr9;
							v_lottory_xhr9 = undefined;
							v_lottory_xhr9_used = 0;
						}
						return false;
					}
				}
			};
		} else {
			v_lottory_xhr9.onload = function() {
				if (typeof v_lottory_xhr9.readyState != "number") {
					if (!v_lottory_xhr9.responseText || v_lottory_xhr9.responseText == null || v_lottory_xhr9.responseText == "") {
						if (v_lottory_xhr9 != null) {
							v_lottory_xhr9.abort();
							v_lottory_xhr9 = null;
						}
						delete v_lottory_xhr9;
						v_lottory_xhr9 = undefined;
						v_lottory_xhr9_used = 0;
						f_balance_timer(1000);
						return false;
					} else {
						try {
							if (!v_lottory_xhr9.responseText || v_lottory_xhr9.responseText == null || v_lottory_xhr9.responseText == "") {
								v_lottory_xhr9_used = 0;
								f_balance_timer(1000);
								return false;
							}
							if (isJSON(v_lottory_xhr9.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr9.responseText);
								if (tmpJ ? tmpJ.balance : false) {
									document.getElementsByName("balance")[0].value = tmpJ.balance;
									document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = tmpJ.balance
											.substring(0, tmpJ.balance.indexOf(".") + 3);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								if (getEle("balance").value == "") {
									f_balance_timer(1000);
									v_lottory_xhr9_used = 0;
									return false;
								} else {
									v_lottory_xhr9_reload = 0;
									v_lottory_xhr9_used = 0;
									return true;
								}
							} else {
								v_lottory_xhr9_used = 0;
								f_balance_timer(1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_getBalence error Message:" + error.message);
							console_Log("f_lottory_getBalence error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_getBalence facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_getBalence error Name:" + error.name);
							v_lottory_xhr9_used = 0;
							f_balance_timer(1000);
							return false;
						} finally {
							if (v_lottory_xhr9 != null) {
								v_lottory_xhr9.abort();
								v_lottory_xhr9 = null;
							}
							delete v_lottory_xhr9;
							v_lottory_xhr9 = undefined;
							v_lottory_xhr9_used = 0;
						}
						return false;
					}
				} else if (v_lottory_xhr9.readyState == 4) {
					if ((v_lottory_xhr9.status >= 200 && v_lottory_xhr9.status < 300) || v_lottory_xhr9.status == 304) {
						try {
							if (!v_lottory_xhr9.responseText || v_lottory_xhr9.responseText == null || v_lottory_xhr9.responseText == "") {
								v_lottory_xhr9_used = 0;
								f_balance_timer(1000);
								return false;
							}
							if (isJSON(v_lottory_xhr9.responseText)) {
								var tmpJ = JSON.parse(v_lottory_xhr9.responseText);
								if (tmpJ ? tmpJ.balance : false) {
									document.getElementsByName("balance")[0].value = tmpJ.balance;
									document.getElementsByClassName("account")[0].getElementsByTagName("span")[1].getElementsByTagName("i")[0].innerHTML = tmpJ.balance
											.substring(0, tmpJ.balance.indexOf(".") + 3);
								}
								if (tmpJ && tmpJ["now"]) {
									checkDiffms(tmpJ["now"]);
								}
								delete tmpJ;
								tmpJ = undefined;
								if (getEle("balance").value == "") {
									f_balance_timer(1000);
									v_lottory_xhr9_used = 0;
									return false;
								} else {
									v_lottory_xhr9_reload = 0;
									v_lottory_xhr9_used = 0;
									return true;
								}
							} else {
								v_lottory_xhr9_used = 0;
								f_balance_timer(1000);
								return false;
							}
						} catch (error) {
							console_Log("f_lottory_getBalence error Message:" + error.message);
							console_Log("f_lottory_getBalence error Code:" + (error.number & 0xFFFF));
							console_Log("f_lottory_getBalence facility Code:" + (error.number >> 16 & 0x1FFF));
							console_Log("f_lottory_getBalence error Name:" + error.name);
							v_lottory_xhr9_used = 0;
							f_balance_timer(1000);
							return false;
						} finally {
							if (v_lottory_xhr9 != null) {
								v_lottory_xhr9.abort();
								v_lottory_xhr9 = null;
							}
							delete v_lottory_xhr9;
							v_lottory_xhr9 = undefined;
							v_lottory_xhr9_used = 0;
						}
						return false;
					}
				}
			};
		}
		v_lottory_xhr9.open("POST", tmpURL, true);
		if (v_lottory_xhr9.setRequestHeader) {
			v_lottory_xhr9.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		}
		v_lottory_xhr9.send(tmpStr.toString());
		delete tmpURL;
		delete tmpStr;
		tmpURL = undefined;
		tmpStr = undefined;
	}
}

function lotteryInit() {
	var v1 = parseInt(document.getElementsByName('defLev1')[0].value);
	var v2 = parseInt(document.getElementsByName('defLev2')[0].value);
	if (isNaN(v1)) {
		v1 = 0;
	}
	if (isNaN(v2)) {
		v2 = 0;
	}
	document.getElementsByName('l1')[0].value = v1;
	document.getElementsByName('l2')[0].value = v2;
	checkMenuTurnOff();
	// setTimeout('Dropdown(document.getElementsByClassName("submenuItems")['+document.getElementsByName('defLev1')[0].value+']).init();'
	// ,1);
	// setTimeout('document.getElementsByClassName("menu-lottery")['+document.getElementsByName('defLev1')[0].value+'].getElementsByTagName("a")['+document.getElementsByName('defLev2')[0].value+'].onclick();'
	// ,100);
	// Dropdown(document.getElementsByClassName("submenuItems")['+document.getElementsByName('defLev1')[0].value+']).init();
	if (v1 < 5) {
		while (hasClass(document.getElementsByClassName("menu-lottery")[v1], "turn-off") || hasClass(document.getElementsByClassName("menu-lottery")[v1], "invisible")) {
			v1++;
		}
	}
	if (v1 < 5) {
		while (hasClass(document.getElementsByClassName("menu-lottery")[v1].getElementsByTagName("li")[v2], "turn-off")
				|| hasClass(document.getElementsByClassName("menu-lottery")[v1].getElementsByTagName("li")[v2], "invisible")) {
			v2++;
		}
	}
	if (v1 < 5) {
		if (!hasClass(document.getElementsByClassName("accordion-menu")[0].children[v1], "open")) {
			document.getElementsByClassName('dropdownlink')[v1].onclick();
		}
		if (v2 < document.getElementsByClassName("menu-lottery")[v1].getElementsByTagName("li").length) {
			if (!hasClass(document.getElementsByClassName("menu-lottery")[v1].getElementsByTagName("li")[v2], "turn-off") ? !hasClass(document.getElementsByClassName("menu-lottery")[v1]
					.getElementsByTagName("li")[v2], "invisible") : false) {
				document.getElementsByClassName("menu-lottery")[v1].getElementsByTagName("a")[v2].onclick();
			}
		}
	} else {
		if (!hasClass(document.getElementsByClassName("accordion-menu")[0].children[6], "open")) {
			document.getElementsByClassName('dropdownlink')[6].onclick();
		}
		if ((v2 - 5) < document.getElementsByClassName("submenuItems")[6].getElementsByTagName("li").length) {
			if (!hasClass(document.getElementsByClassName("submenuItems")[6].getElementsByTagName("li")[v1 - 5], "turn-off") ? !hasClass(document.getElementsByClassName("submenuItems")[6]
					.getElementsByTagName("li")[v1 - 5], "invisible") : false) {
				document.getElementsByClassName('submenuItems')[6].getElementsByTagName("a")[v1 - 5].onclick();
			}
		}
	}
	delete v1;
	v1 = undefined;
	delete v2;
	v2 = undefined;
	f_winHistory_timer(0);
	f_kj_timer();
}

var v_ratio_timer = -1;
function f_ratio_timer(ms) {
	if (typeof ms != "number") {
		ms = 11000;
	}
	clearTimeout(v_ratio_timer);
	v_ratio_timer = setTimeout("f_lottory_Ratio2();", ms);
}

var v_kj_timer = -1;
function f_kj_timer(ms) {
	if (typeof ms != "number") {
		ms = 11000;
	}
	clearTimeout(v_kj_timer);
	v_kj_timer = setTimeout("f_lottory_Allkj();", ms);
	f_ratio_timer(ms);
	f_ratio_lf_timer(ms);
	f_balance_timer(3000);
}

var v_ratio_timer_lf = -1;
function f_ratio_lf_timer(ms) {
	if (typeof ms != "number") {
		ms = 11000;
	}
	clearTimeout(v_ratio_timer_lf);
	v_ratio_timer_lf = setTimeout("f_lottory_Ratio2_LF();", ms);
}

var v_balance_timer = -1;
function f_balance_timer(ms) {
	if (typeof ms != "number") {
		ms = 30000;
	}
	clearTimeout(v_balance_timer);
	v_balance_timer = setTimeout("f_lottory_getBalence();", ms);
}

// f_lottory_info();
f_lottory_queryAllInfo();
// //setTimeout('Dropdown(document.getElementsByClassName("submenuItems")[0]).init();'
// ,1);
// //setTimeout('document.getElementsByClassName("menu-lottery")[0].getElementsByTagName("a")[0].onclick();'
// ,500);
// setTimeout('Dropdown(document.getElementsByClassName("submenuItems")[0]).init();'
// ,800);
// setTimeout('setLevel(0,0,0,0);tabSSC();' ,500);

setInterval(updateShowTime, 500);

function disableMainSetButton(lotteryLowfreq) {
	if (lotteryLowfreq == "1") {
		document.getElementsByClassName("bottom")[0].getElementsByTagName("button")[2].style.display = "none";
	} else {
		document.getElementsByClassName("bottom")[0].getElementsByTagName("button")[2].style.display = "";
	}
}

function setLotteryTitleHTML() {
	var allLotteryTitle = isJSON2(Strings.decode(getEle("allLotteryTitle").value));
	if (allLotteryTitle ? (typeof allLotteryTitle["AllLotteryTitle"] != "undefined" && allLotteryTitle["AllLotteryTitle"] != null) : false) {
		var obj = allLotteryTitle["AllLotteryTitle"];
		var ele = document.getElementsByClassName("accordion-menu")[0];
		ele.innerHTML = "";

		var k1 = Object.keys(obj).sort();
		for (var i1 = 0; i1 < k1.length; i1++) {
			var key = k1[i1];

			var level1Url = "";
			var mainTitle = "";

			var li = document.createElement("li");
			var a = document.createElement("a");
			var img = document.createElement("img");
			if (typeof obj[key]["level1_url"] != "undefined" && obj[key]["level1_url"] != "") {
				level1Url = obj[key]["level1_url"];
			}
			img.src = level1Url;// obj[key]["level1_url"];
			a.href = "javaScript:void(0);";
			a.className = "dropdownlink";
			a.appendChild(img);
			li.appendChild(a);

			var ul = document.createElement("ul");
			ul.className = "submenuItems menu-lottery hide";

			var k2 = Object.keys(obj[key]).sort();
			for (var i2 = 0; i2 < k2.length; i2++) {
				(function(i2) {
					var key2 = k2[i2];
					if (typeof obj[key][key2] == "object") {
						var localTitle = "";
						var localId = "0";
						var lotteryFnName = "";
						if (typeof obj[key][key2]["local_title"] != "undefined") {
							localTitle = "" + obj[key][key2]["local_title"]
						}
						if (typeof obj[key][key2]["local_id"] != "undefined") {
							localId = "" + obj[key][key2]["local_id"];
						}
						if (typeof obj[key][key2]["lottery_fn_name"] != "undefined") {
							lotteryFnName = "" + obj[key][key2]["lottery_fn_name"];
						}
						var li2 = document.createElement("li");

						var fn = "setLev1(this)";

						var aHtml = '<a href="javaScript:void(0);" onclick = "' + fn.concat("") + '">' + localTitle.toString() + '</a>';
						li2.innerHTML = aHtml.toString();

						ul.appendChild(li2);

						delete lotteryFnName, li2;
						lotteryFnName = undefined;
						li2 = undefined;
					}
				})(i2);
			}
			li.appendChild(ul);
			ele.appendChild(li);

		}
	}
	initDropdownlink();
}

function showBallRatioBaseline() {
	var classNameArr = [ "marksix_49ball", "marksix_09ball", "marksix_49ball_nobtn", "marksix_18ball", "marksix_animalball", "marksix_10ball" ];
	var ballRatioObj = {
		"01" : "1",
		"02" : "2",
		"03" : "3",
		"04" : "4",
		"05" : "5",
		"06" : "6",
		"07" : "7",
		"08" : "8",
		"09" : "9",
		"10" : "10",
		"11" : "11",
		"12" : "12",
		"13" : "13",
		"14" : "14",
		"15" : "15",
		"16" : "16",
		"17" : "17",
		"18" : "18",
		"19" : "19",
		"20" : "20",
		"21" : "21",
		"22" : "22",
		"23" : "23",
		"24" : "24",
		"25" : "25",
		"26" : "26",
		"27" : "27",
		"28" : "28",
		"29" : "29",
		"30" : "30",
		"31" : "31",
		"32" : "32",
		"33" : "33",
		"34" : "34",
		"35" : "35",
		"36" : "36",
		"37" : "37",
		"38" : "38",
		"39" : "39",
		"40" : "40",
		"41" : "41",
		"42" : "42",
		"43" : "43",
		"44" : "44",
		"45" : "45",
		"46" : "46",
		"47" : "47",
		"48" : "48",
		"49" : "49",
		"大" : "1",
		"单" : "3",
		"合单" : "8",
		"小" : "2",
		"双" : "4",
		"合双" : "9",
		"红" : "5",
		"蓝" : "7",
		"绿" : "6",
		"红大" : "3",
		"绿大" : "9",
		"蓝大" : "15",
		"红小" : "4",
		"绿小" : "10",
		"蓝小" : "16",
		"红双" : "2",
		"绿双" : "8",
		"蓝双" : "14",
		"红单" : "1",
		"绿单" : "7",
		"蓝单" : "13",
		"红合单" : "5",
		"绿合单" : "11",
		"蓝合单" : "17",
		"红合双" : "6",
		"绿合双" : "12",
		"蓝合双" : "18",
		"鼠" : "1",
		"牛" : "2",
		"虎" : "3",
		"兔" : "4",
		"龙" : "5",
		"蛇" : "6",
		"马" : "7",
		"羊" : "8",
		"猴" : "9",
		"鸡" : "10",
		"狗" : "11",
		"猪" : "12",
		"0尾" : "1",
		"1尾" : "2",
		"2尾" : "3",
		"3尾" : "4",
		"4尾" : "5",
		"5尾" : "6",
		"6尾" : "7",
		"7尾" : "8",
		"8尾" : "9",
		"9尾" : "10"
	};
	
	
	var localId = getEle("localId").value;
	var nowZodiac = toInt(getEle("zodiacType").value);
	var zodiacObj = getZodiacAllType();
	var zodiacKeyArr = Object.keys(zodiacObj);
	
	var ballColourAllTypeObj = getBallColourAllType();
	
	
	if (localId == XGLHC_ID) {
		var periodObjList = getPeriodNum();
		if (typeof periodObjList !== "undefined" ? periodObjList.length > 0 : false) {
			var dateStr = getPeriodNum()[0].date;
			var mainId = getEle("mainId").value;
			var midId = getEle("midId").value;
			var minAuthId = getEle("minAuthId").value;
			var handicapId = getEle("handicapId").value;
			getRatiosLFJson();
			if (ratiosLFJson.CurrentBaselineLF != null && typeof ratiosLFJson.CurrentBaselineLF !== "undefined") {
				var ratiosObj = ratiosLFJson.CurrentBaselineLF;
				var baselineObj = {};
				for ( var key in ratiosObj) {
					if (key.replace(/\//g, '').indexOf("" + dateStr) >= 0) {
						// console.log(ratiosObj[key][mainId][localId][midId][minAuthId][handicapId]);
						baselineObj = ratiosObj[key][mainId][localId][midId][minAuthId][handicapId];
					}
				}
				if (Object.keys(baselineObj).length > 0) {
					for (var i = 0; i < classNameArr.length; i++) {
						var ele = document.getElementsByClassName(classNameArr[i])[0];
						if (!hasClass(ele, "invisible")) {
							var buttonEle = ele.getElementsByTagName("ul")[0].getElementsByTagName("button");
							if (Object.keys(baselineObj).length == buttonEle.length) {
								for (var j = 0; j < buttonEle.length; j++) {
									var bntText = buttonEle[j].innerHTML;
									if (buttonEle[j].parentNode.getElementsByClassName("num-single")[0] != null) {
										buttonEle[j].parentNode.getElementsByClassName("num-single")[0].style.display = "block";
										buttonEle[j].parentNode.getElementsByClassName("num-double")[0].style.display = "none";
									}
									var val = baselineObj[ballRatioObj[bntText]]["baseline"];
									buttonEle[j].parentNode.getElementsByTagName("i")[0].innerHTML = val.substr(0, val.indexOf(".") + 3);
									
									var tooltiptext1Ele = buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0];
									
									if(bntText == "红大"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["redBigList"].join(" ");
									}
									else if(bntText == "绿大"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["greenBigList"].join(" ");
									}
									else if(bntText == "蓝大"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["blueBigList"].join(" ");
									}
									else if(bntText == "红小"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["redSmallList"].join(" ");
									}
									else if(bntText == "绿小"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["greenSmallList"].join(" ");
									}
									else if(bntText == "蓝小"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["blueSmallList"].join(" ");
									}
									else if(bntText == "红双"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["redDoubleList"].join(" ");
									}
									else if(bntText == "绿双"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["greenDoubleList"].join(" ");
									}
									else if(bntText == "蓝双"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["blueDoubleList"].join(" ");
									}
									else if(bntText == "红单"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["redSignList"].join(" ");
									}
									else if(bntText == "绿单"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["greenSignList"].join(" ");
									}
									else if(bntText == "蓝单"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["blueSignList"].join(" ");
									}
									else if(bntText == "红合双"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["redTotalDoubleList"].join(" ");
									}
									else if(bntText == "绿合双"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["greenTotalDoubleList"].join(" ");
									}
									else if(bntText == "蓝合双"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["blueTotalDoubleList"].join(" ");
									}
									else if(bntText == "红合单"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["redTotalSignList"].join(" ");
									}
									else if(bntText == "绿合单"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["greenTotalSignList"].join(" ");
									}
									else if(bntText == "蓝合单"){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = "特:"+ballColourAllTypeObj["blueTotalSignList"].join(" ");
									}
									else if(zodiacKeyArr.indexOf(bntText) >= 0){
										buttonEle[j].parentNode.getElementsByClassName("tooltiptext1")[0].innerHTML = bntText+":"+zodiacObj[bntText].join(" ");
										if(hasClass(buttonEle[j],"gold")){
											removeClass(buttonEle[j],"gold");
										}
										
										if(nowZodiac == ballRatioObj[bntText]){
											addClass(buttonEle[j],"gold");
										}
									}
								}
							} else if (Object.keys(baselineObj).length > buttonEle.length) {
								for (var j = 0; j < buttonEle.length; j++) {
									if (buttonEle[j].parentNode.getElementsByClassName("num-single")[0] != null) {
										var val1 = baselineObj[ballRatioObj[buttonEle[j].innerHTML]]["baseline"];
										var val2 = baselineObj["" + (parseInt(ballRatioObj[buttonEle[j].innerHTML]) + 49)]["baseline"];

										buttonEle[j].parentNode.getElementsByClassName("num-single")[0].style.display = "none";
										buttonEle[j].parentNode.getElementsByClassName("num-double")[0].style.display = "block";

										buttonEle[j].parentNode.getElementsByClassName("num-double")[0].getElementsByClassName("num-top")[0].innerHTML = val1
												.substr(0, val1.indexOf(".") + 3);
										buttonEle[j].parentNode.getElementsByClassName("num-double")[0].getElementsByClassName("num-bottom")[0].innerHTML = val2
												.substr(0, val2.indexOf(".") + 3);
									}
								}
							}
							break;
						}
					}
				}

			}
		}
	}
}

function getZodiac() {
	var nowZodiac = toInt(getEle("zodiacType").value);
	var zociacObj = {};
	for (var i = 1; i <= 49; i++) {
		zociacObj[i] = nowZodiac;
		nowZodiac--;
		if (nowZodiac == 0) {
			nowZodiac = ZODIAC_ARRAY[ZODIAC_ARRAY.length-1];
		}
	}
	return zociacObj;
}

function getZodiacName() {
	var nowZodiac = toInt(getEle("zodiacType").value);
	var zociacObj = {};
	for (var i = 1; i <= 49; i++) {
		zociacObj[i] = ZODIAC_NAME_ARRAY[ZODIAC_ARRAY.indexOf(nowZodiac)];
		nowZodiac--;
		if (nowZodiac == 0) {
			nowZodiac = ZODIAC_ARRAY[ZODIAC_ARRAY.length-1];
		}
	}
	return zociacObj;
}


function getZodiacAllType() {
	var nowZodiac = toInt(getEle("zodiacType").value);
	var zociacObj = {};
	for (var i = 1; i <= 49; i++) {
		var t1 = Math.floor(i/10);
		var t2 = i%10;
		
		if(typeof zociacObj[ZODIAC_NAME_ARRAY[ZODIAC_ARRAY.indexOf(nowZodiac)]] === "undefined" ){
			zociacObj[ZODIAC_NAME_ARRAY[ZODIAC_ARRAY.indexOf(nowZodiac)]] = [];
		}
		zociacObj[ZODIAC_NAME_ARRAY[ZODIAC_ARRAY.indexOf(nowZodiac)]].push(t1+""+t2);
		
		nowZodiac--;
		if (nowZodiac == 0) {
			nowZodiac = ZODIAC_ARRAY[ZODIAC_ARRAY.length-1];
		}
	}
	return zociacObj;
}

function getBallColour(){
	var ballButtonEle =  document.getElementsByClassName("marksix_49ball")[0].getElementsByClassName("ball-number")[0].getElementsByTagName("button");
	var ballObj = {};
	hasClass(document.getElementsByClassName("marksix_49ball")[0].getElementsByClassName("ball-number")[0].getElementsByTagName("button")[0],"red")
	
	for(var i = 0 ; i < ballButtonEle.length ; i++){
		for(var j = 0 ; j < BALL_COLOUR_ARRAY.length ;j++){
			if(hasClass(ballButtonEle[i],BALL_COLOUR_ARRAY[j]) == true){
				ballObj[toInt(ballButtonEle[i].innerHTML)] = BALL_COLOUR_ARRAY[j];
			}
		}
	}
	return ballObj;
}


function getBallColourAllType(){
	var obj = {};
	
	var redList = [];
	var redBigList = [];
	var redSmallList = [];
	var redTotalBigList = [];
	var redTotalSmallList = [];
	var redSignList = [];
	var redDoubleList = [];
	var redTotalSignList = [];
	var redTotalDoubleList = [];
	
	var blueList = [];
	var blueBigList = [];
	var blueSmallList = [];
	var blueTotalBigList = [];
	var blueTotalSmallList = [];
	var blueSignList = [];
	var blueDoubleList = [];
	var blueTotalSignList = [];
	var blueTotalDoubleList = [];
	
	var greenList = [];
	var greenBigList = [];
	var greenSmallList = [];
	var greenTotalBigList = [];
	var greenTotalSmallList = [];
	var greenSignList = [];
	var greenDoubleList = [];
	var greenTotalSignList = [];
	var greenTotalDoubleList = [];
	
	var redMaxNum = 0;
	var blueMaxNum = 0;
	var greenMaxNum = 0;
	
	var ballButtonEle =  document.getElementsByClassName("marksix_49ball")[0].getElementsByClassName("ball-number")[0].getElementsByTagName("button");
	var ballColourObj = {};
	var maxColourBall = {};
	
	var maxNum = 0;
	
	for(var i = 0 ; i < ballButtonEle.length ; i++){
		for(var j = 0 ; j < BALL_COLOUR_ARRAY.length ;j++){
			if(hasClass(ballButtonEle[i],BALL_COLOUR_ARRAY[j]) == true){
				var num = toInt(ballButtonEle[i].innerHTML);
				if(typeof maxColourBall[BALL_COLOUR_ARRAY[j]] === "undefined"){
					maxColourBall[BALL_COLOUR_ARRAY[j]] = num;
				}
				else if(maxColourBall[BALL_COLOUR_ARRAY[j]] < num){
					maxColourBall[BALL_COLOUR_ARRAY[j]] = num;
				}
				if(maxNum < num){
					maxNum = num;
				}
				ballColourObj[num] = BALL_COLOUR_ARRAY[j];
			}
		}
	}
	
	var keyArr = Object.keys(ballColourObj);
	
	var redBig =  Math.ceil(toInt(maxColourBall["red"])/2); 
	var blueBig = Math.ceil(toInt(maxColourBall["blue"])/2); 
	var greenBig = Math.ceil(toInt(maxColourBall["green"])/2); 
	
	var maxNumT1 = Math.floor(maxNum/10);
	var maxNumT2 = maxNum%10;
	
	var maxNumTotal = maxNumT1+maxNumT2;
	
	var maxNumTotalBig = Math.ceil(toInt(maxNumTotal)/2);
	
	for(var i = 0 ; i < keyArr.length ; i++){
		var num = toInt(keyArr[i]);
		var t1 = Math.floor(num/10);
		var t2 = num%10;
		var totalNumBig = t1+t2;
		
		var numText = t1+""+t2;
		
		if(ballColourObj[keyArr[i]] == "red"){
			redList.push(numText);
			if(num%2 == 0){
				redDoubleList.push(numText);
			}
			else{
				redSignList.push(numText);
			}
			
			if(totalNumBig%2 == 0){
				redTotalDoubleList.push(numText);
			}
			else{
				redTotalSignList.push(numText);
			}
			
			if(num >= redBig){
				redBigList.push(numText);
			}
			else{
				redSmallList.push(numText);
			}
			
			if(totalNumBig >= maxNumTotalBig){
				redTotalBigList.push(numText);
			}
			else{
				redTotalSmallList.push(numText);
			}
			
		}
		else if(ballColourObj[keyArr[i]] == "blue"){
			blueList.push(numText);
			if(num%2 == 0){
				blueDoubleList.push(numText);
			}
			else{
				blueSignList.push(numText);
			}
			
			if(totalNumBig%2 == 0){
				blueTotalDoubleList.push(numText);
			}
			else{
				blueTotalSignList.push(numText);
			}
			
			if(num >= blueBig){
				blueBigList.push(numText);
			}
			else{
				blueSmallList.push(numText);
			}
			
			if(totalNumBig >= maxNumTotalBig){
				blueTotalBigList.push(numText);
			}
			else{
				blueTotalSmallList.push(numText);
			}
		}
		else if(ballColourObj[keyArr[i]] == "green"){
			greenList.push(numText);
			if(num%2 == 0){
				greenDoubleList.push(numText);
			}
			else{
				greenSignList.push(numText);
			}
			
			if(totalNumBig%2 == 0){
				greenTotalDoubleList.push(numText);
			}
			else{
				greenTotalSignList.push(numText);
			}
			
			if(num >= greenBig){
				greenBigList.push(numText);
			}
			else{
				greenSmallList.push(numText);
			}
			
			if(totalNumBig >= maxNumTotalBig){
				greenTotalBigList.push(numText);
			}
			else{
				greenTotalSmallList.push(numText);
			}
		}
	}
	
	obj["redList"] = redList; //紅
	obj["redBigList"] = redBigList; //紅大
	obj["redSmallList"] = redSmallList; //紅小
	obj["redTotalBigList"] = redTotalBigList; //紅合大
	obj["redTotalSmallList"] = redTotalSmallList; //紅合小
	obj["redSignList"] = redSignList; //紅單
	obj["redDoubleList"] = redDoubleList; //紅雙
	obj["redTotalSignList"] = redTotalSignList; //紅合單
	obj["redTotalDoubleList"] = redTotalDoubleList; //紅合雙
	obj["blueList"] = blueList; //藍
	obj["blueBigList"] = blueBigList;  //藍大
	obj["blueSmallList"] = blueSmallList; //藍小
	obj["blueTotalBigList"] = blueTotalBigList; //藍合大
	obj["blueTotalSmallList"] = blueTotalSmallList; //藍合小
	obj["blueSignList"] = blueSignList; //藍單
	obj["blueDoubleList"] = blueDoubleList; //藍雙
	obj["blueTotalSignList"] = blueTotalSignList; //藍合單
	obj["blueTotalDoubleList"] = blueTotalDoubleList; //藍合雙
	obj["greenList"] = greenList; //綠
	obj["greenBigList"] = greenBigList; //綠大
	obj["greenSmallList"] = greenSmallList; //綠小 
	obj["greenTotalBigList"] = greenTotalBigList; //綠合大
	obj["greenTotalSmallList"] = greenTotalSmallList; //綠合小
	obj["greenSignList"] = greenSignList; //綠單
	obj["greenDoubleList"] = greenDoubleList; //綠雙
	obj["greenTotalSignList"] = greenTotalSignList; //綠合單
	obj["greenTotalDoubleList"] = greenTotalDoubleList; //綠合雙

	return obj;
}

