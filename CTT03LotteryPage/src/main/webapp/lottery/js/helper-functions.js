function js_helper_functions() {
	return true;
}

/**
 * @description 將輸入的字串轉換成 array
 * 
 * @param {string}
 *                bet 投注列表：'1,2,3|2,6,7'
 * @returns {array} 會是 [1,2,3,4] 或 [[1,2],[3,4]] 格式
 */

function transfer2Group(bet) {
	bet = bet.replace(/\s/g, '');
	var betString = bet;

	if (checkStringInput(betString)) {
		if (betString.indexOf('|') >= 0) {
			// 1. 輸入的格式是 '1,2|3,4'
			var groups = betString.split('|');
			return transfer2Numbers(groups); // 回傳
								// [[1,2],[3,4]]
		} else {
			// 2. 輸入的格式是 '1,2,3,4'
			return transfer2Numbers(bet); // 回傳 [1,2,3,4]
		}
	}
	// 其他情況
	throw new CustomError('ssc', 'transfer2Group', '輸入的內容格式有誤');
}

function transfer2Numbers(bet) {
	if (!bet) {
		throw new CustomError('ssc', 'transfer2Group', '輸入的內容格式有誤');
	}

	if (typeof bet === 'string') {
		// 如果輸入的格式不含 '|'，代表bet內容是 '1,2,3,4' 就直接切割 ','
		return bet.split(',');
	}

	// [ '1,2','3,4' ... ] 這種格式的跑下面迴圈
	var responseArray = [];
	if (bet instanceof Array) {
		for (var i = 0, l = bet.length; i < l; i++) {
			var group = bet[i];
			if (group === '') {
				responseArray.push([ -1 ]); // 輸入時可能是這種格式
								// '1|2,3||4|'
								// ex：任選二三
								// 直選複式有可能會是這種情況
				continue;
			}
			responseArray.push(group.split(','));
		}
	}
	return responseArray;
}

/**
 * @description 將兩個array的資料合併，並組合成 [ {}, {} ...] 回傳格式
 * 
 * @param {array}
 *                arr1 投注列表：["萬,千,百,十", "萬,千,百,個", "萬,千,十,個", "萬,百,十,個",
 *                "千,百,十,個"]
 * @param {array}
 *                arr2 投注列表：["1,4", "1,5", "4,5"]
 * @returns {array} 會像是 [{type: "萬,千,百,十", data: "1,4"},{type: "萬,千,百,十",
 *          data: "1,5"},{type: "萬,千,百,個", data: "1,4"}...]
 */

function twoGroupCombine(arr1, arr2) {

	if (arr1.length === 0 || arr2.length === 0) {
		throw new CustomError('ssc', 'transfer2Group', '輸入的內容格式有誤');
	}

	if (arr1 instanceof Array && arr2 instanceof Array) {
		var response = [];
		for (var i = 0, l1 = arr1.length; i < l1; i++) {
			for (var j = 0, l2 = arr2.length; j < l2; j++) {
				response.push({
					'type' : arr1[i],
					'data' : arr2[j]
				});
			}
		}
		return response;
	}

	throw new CustomError('ssc', 'transfer2Group', '輸入的內容格式有誤');
}

// '02467,1236' => ['02467', '1236'] => [ [0,2,4,6,7], [1,2,3,6] ]
function descarteCalculate(bet) {
	if (bet) {
		var betData = bet.split('|');
		if (betData.length === 0) {
			throw new CustomError('helper-functions', 'descarteCalculate', '傳入的值無法被切割');
		}
		var result = descartesAlgorithm.apply(null, betData.map(function(v) {
			return v.split(',');
		}));
		return result;
	} else {
		throw new CustomError('helper-functions', 'descarteCalculate', '輸入的內容有問題');
	}

}

function subOrderCalculateBeforeDescarte(bet) {
	if (bet) {
		var betData = bet.split('|');
		if (betData.length === 0) {
			throw new CustomError('helper-functions', 'descarteCalculate', '傳入的值無法被切割');
		}
		var numOfSubOrder = 1;
		for (var i = 0; i < betData.length; i++) {
			var num = betData[i].split(',');
			numOfSubOrder = numOfSubOrder * num.length;
		}
		return numOfSubOrder;
	} else {
		throw new CustomError('helper-functions', 'descarteCalculate', '輸入的內容有問題');
	}
}

/**
 * @description 笛卡尔乘积算法
 * 
 * @param 一个可变参数，原则上每个都是数组，但如果数组只有一个值是直接用这个值
 * 
 * useage: console.log(descartesAlgorithm([4,5], [6,0],[7,8,9]));
 */

function descartesAlgorithm() {

	var i, j, a = [], b = [], c = [];
	if (arguments.length == 1) {
		if (!Array.isArray(arguments[0])) {
			return [ arguments[0] ];
		} else {
			return arguments[0];
		}
	}

	if (arguments.length > 2) {
		for (i = 0; i < arguments.length - 1; i++)
			a[i] = arguments[i];
		b = arguments[i];

		return arguments.callee(arguments.callee.apply(null, a), b);
	}

	if (Array.isArray(arguments[0])) {
		a = arguments[0];
	} else {
		a = [ arguments[0] ];
	}
	if (Array.isArray(arguments[1])) {
		b = arguments[1];
	} else {
		b = [ arguments[1] ];
	}

	for (i = 0; i < a.length; i++) {
		for (j = 0; j < b.length; j++) {
			if (Array.isArray(a[i])) {
				var temp = a[i].concat(b[j]);
				c.push(temp.join(','));
			} else {
				var temp = a[i] + '|' + b[j];
				c.push(temp);
			}
		}
	}
	return c;
}

function descarteCalculateRemoveDuplicateNumber(bet) {
	if (bet) {
		var betData = bet.split('|');
		if (betData.length === 0) {
			throw new CustomError('helper-functions', 'descarteCalculateRemoveDuplicateNumber', '傳入的值無法被切割');
		}
		var result = [];
		var lengths = [];
		for (var i = 0; i < betData.length; i++) {
			var tmpBet = betData[i].split(',');
			lengths.push(tmpBet.length);
		}
		if (lengths.length == 3) {
			var tmpBet1 = betData[0].split(',');
			var tmpBet2 = betData[1].split(',');
			var tmpBet3 = betData[2].split(',');
			for (var i = 0; i < tmpBet1.length; i++) {
				var dataBet1 = tmpBet1[i];
				for (var j = 0; j < tmpBet2.length; j++) {
					var dataBet2 = tmpBet2[j];
					if (dataBet1 != dataBet2) {
						for (var k = 0; k < tmpBet3.length; k++) {
							var dataBet3 = tmpBet3[k];
							if (dataBet1 != dataBet3 && dataBet2 != dataBet3) {
								result.push(dataBet1 + "|" + dataBet2 + "|" + dataBet3);
							}
						}
					}

				}
			}
		} else if (lengths.length == 2) {
			var tmpBet1 = betData[0].split(',');
			var tmpBet2 = betData[1].split(',');
			for (var i = 0; i < tmpBet1.length; i++) {
				var dataBet1 = tmpBet1[i];
				for (var j = 0; j < tmpBet2.length; j++) {
					var dataBet2 = tmpBet2[j];
					if (dataBet1 != dataBet2) {
						result.push(dataBet1 + "|" + dataBet2);
					}
				}
			}
		}

		// var result =
		// descartesAlgorithmRemoveDuplicateNumber.apply(null,
		// betData.map(function (v) {
		// return v.split(',');
		// }));
		return result;
	} else {
		throw new CustomError('helper-functions', 'descarteCalculateRemoveDuplicateNumber', '輸入的內容有問題');
	}
}

/**
 * @description 笛卡尔乘积算法 （已經出現過的數字不重複出現）
 * 
 * @param 一个可变参数，原则上每个都是数组，但如果数组只有一个值是直接用这个值
 * 
 * useage: console.log(descartesAlgorithm([4,5], [6,0],[7,8,9]));
 */

/**
 * function descartesAlgorithmRemoveDuplicateNumber() {
 * 
 * var i, j, a = [], b = [], c = []; if (arguments.length == 1) { if
 * (!Array.isArray(arguments[0])) { return [arguments[0]]; } else { return
 * arguments[0]; } }
 * 
 * if (arguments.length > 2) { for (i = 0; i < arguments.length - 1; i++) a[i] =
 * arguments[i]; b = arguments[i];
 * 
 * return arguments.callee(arguments.callee.apply(null, a), b); }
 * 
 * if (Array.isArray(arguments[0])) { a = arguments[0]; } else { a =
 * [arguments[0]]; } if (Array.isArray(arguments[1])) { b = arguments[1]; } else {
 * b = [arguments[1]]; }
 * 
 * for (i = 0; i < a.length; i++) { for (j = 0; j < b.length; j++) { if
 * (Array.isArray(a[i])) { var temp = a[i].concat(b[j]); c.push(temp.join(',')); }
 * else { var num1 = a[i]; var num2 = b[j]; if (num1 == num2) { //數字重複就跳過
 * continue; } var temp = a[i] + '|' + b[j]; c.push(temp); } } } return c; }
 */

/**
 * @description 组合算法
 * 
 * @param {array}
 *                arr 备选数组 [1,2,3,4,5,6,7,8,9]
 * @param {number}
 *                num
 * 
 * @returns {array} 回傳格式 [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
 * 
 * useage: combine([1,2,3,4,5,6,7,8,9], 3);
 */
function combine(arr, num) {
	var r = [];
	(function f(t, a, n) {
		if (n == 0)
			return r.push(t);
		for (var i = 0, l = a.length; i <= l - n; i++) {
			f(t.concat(a[i]), a.slice(i + 1), n - 1);
		}
	})([], arr, num);
	return r;
}

/**
 * @description 组合算法
 * 
 * @param {array}
 *                arr 备选数组 [1,2,3,4,5,6,7,8,9]
 * @param {number}
 *                num
 * 
 * @returns {array} 回傳格式 ["1,2", "1,3", "1,4", "2,3", "2,4", "3,4"]
 * 
 * useage: combine([1,2,3,4,5,6,7,8,9], 3);
 */
function newCombine(arr, num) {
	var r = [];
	(function f(t, a, n) {
		if (n == 0) {
			t = t.join(',');
			return r.push(t);
		}
		for (var i = 0, l = a.length; i <= l - n; i++) {
			f(t.concat(a[i]), a.slice(i + 1), n - 1);
		}
	})([], arr, num);
	return r;
}

function newCombine2(arr, num) {
	var r = [];
	(function f(t, a, n) {
		if (n == 0) {
			t = t.join('|');
			return r.push(t);
		}
		for (var i = 0, l = a.length; i <= l - n; i++) {
			f(t.concat(a[i]), a.slice(i + 1), n - 1);
		}
	})([], arr, num);
	return r;
}

/**
 * @description 排列算法
 * @param {array}
 *                arr 备选数组 [1,2,3,4,5,6,7,8,9]
 * @param {number}
 *                num
 * @returns {array} 回傳格式 [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
 */
function permutation(arr, num) {
	var r = [];
	(function f(t, a, n) {
		if (n == 0)
			return r.push(t);
		for (var i = 0, l = a.length; i < l; i++) {
			f(t.concat(a[i]), a.slice(0, i).concat(a.slice(i + 1)), n - 1);
		}
	})([], arr, num);
	return r;
}

/**
 * ////// 目前沒用到 //////
 * 
 * @description 组合算法（含去除"整組"重複) （只要輸入裡有重複的數字組合出來的組合就會有重複的）
 * 
 * @param {array}
 *                arr 备选数组 [6, 5, 2, 6]
 * @param {number}
 *                num
 * 
 * @returns {array} 组合 ["6526", "6562", "6256", "6265"...] =>(被去除的內容如 ["6526",
 *          "6562", "6256",...])
 * 
 * useage: permutationAndRemoveDuplicate([6, 5, 2, 6], 4);
 */
function permutationAndRemoveDuplicate(arr, num) {

	var clean = [];
	(function f(t, a, n) {
		if (n == 0) {
			t = t.join('');
			if (clean.indexOf(t) === -1) {
				return clean.push(t);
			} else {
				return;
			}
		}
		for (var i = 0, l = a.length; i < l; i++) {
			f(t.concat(a[i]), a.slice(0, i).concat(a.slice(i + 1)), n - 1);
		}
	})([], arr, num);

	return clean;
}

/**
 * @description 组合算法（含去除"排序後"重複) （只要輸入裡有重複的數字組合出來的組合就會有重複的）
 * 
 * @param {array}
 *                arr 备选数组 [1,2,3]
 * @param {number}
 *                num
 * 
 * @returns {array} 组合 [12, 13, 23] => (被去除的內容如 [21, 31, 32])
 * 
 * useage: permutationReturnWithString([1, 2, 3], 2);
 */

function permutationWithReOrder(arr, num) {

	var clean = [];
	(function f(t, a, n) {
		if (n == 0) {
			t.sort();
			t = t.join('');
			if (clean.indexOf(t) === -1) {
				return clean.push(t);
			} else {
				return;
			}
		}
		for (var i = 0, l = a.length; i < l; i++) {
			f(t.concat(a[i]), a.slice(0, i).concat(a.slice(i + 1)), n - 1);
		}
	})([], arr, num);

	return clean;
}

/**
 * @description 將輸入的字串轉換成 array
 * 
 * @param {string}
 *                bet 投注列表：'1,2,3|2,6,7'
 * @returns {array} 會是 [1,2,3,4] 或 [[1,2],[3,4]] 格式
 */

function transfer2Group(bet) {
	bet = bet.replace(/\s/g, '');
	var betString = bet;

	if (checkStringInput(betString)) {
		if (betString.indexOf('|') >= 0) {
			// 1. 輸入的格式是 '1,2|3,4'
			var groups = betString.split('|');
			return transfer2Numbers(groups); // 回傳
								// [[1,2],[3,4]]
		} else {
			// 2. 輸入的格式是 '1,2,3,4'
			return transfer2Numbers(bet); // 回傳 [1,2,3,4]
		}
	}
	// 其他情況
	throw new CustomError('transfer2Group', '輸入的內容格式有誤');
}

function transfer2Numbers(bet) {
	if (!bet) {
		throw new CustomError('transfer2Group', '輸入的內容格式有誤');
	}

	if (typeof bet === 'string') {
		// 如果輸入的格式不含 '|'，代表bet內容是 '1,2,3,4' 就直接切割 ','
		return bet.split(',');
	}

	// [ '1,2','3,4' ... ] 這種格式的跑下面迴圈
	var responseArray = [];
	if (bet instanceof Array) {
		for (var i = 0, l = bet.length; i < l; i++) {
			var group = bet[i];
			if (group === '') {
				responseArray.push([]); // 輸入時可能是這種格式
							// '1|2,3||4|' ex：任選二三
							// 直選複式有可能會是這種情況
				continue;
			}
			responseArray.push(group.split(','));
		}
	}
	return responseArray;
}

/**
 * @description 將兩個array的資料合併，並組合成 [ {}, {} ...] 回傳格式
 * 
 * @param {array}
 *                arr1 投注列表：["萬,千,百,十", "萬,千,百,個", "萬,千,十,個", "萬,百,十,個",
 *                "千,百,十,個"]
 * @param {array}
 *                arr2 投注列表：["1,4", "1,5", "4,5"]
 * @returns {array} 會像是 [{type: "萬,千,百,十", data: "1,4"},{type: "萬,千,百,十",
 *          data: "1,5"},{type: "萬,千,百,個", data: "1,4"}...]
 */

function twoGroupCombine(arr1, arr2) {
	if (arr1.length === 0 || arr2.length === 0) {
		throw new CustomError('transfer2Group', '輸入的內容格式有誤');
	}

	if (arr1 instanceof Array && arr2 instanceof Array) {
		var response = [];
		for (var i = 0, l1 = arr1.length; i < l1; i++) {
			for (var j = 0, l2 = arr2.length; j < l2; j++) {
				response.push({
					type : arr1[i],
					data : arr2[j]
				});
			}
		}
		return response;
	}

	throw new CustomError('transfer2Group', '輸入的內容格式有誤');
}

/**
 * @description 轉換位數
 * @param {array}
 *                data 可以輸入array或string。array => ['萬', '千', '百'] ; string =>
 *                '萬,千,百'
 * @returns {array} 輸入array回傳就array，輸入string回傳就string。 [16, 8, 4] ; '16,8,4'
 */

function convertPosition(data) {

	var position = {
		'萬' : 16,
		'千' : 8,
		'百' : 4,
		'十' : 2,
		'個' : 1
	};

	// data = ['萬','千','百']
	if (data instanceof Array) {
		var response = [];
		for (var i = 0, l = data.length; i < l; i++) {
			var text = data[i];
			response.push(position[text]);
		}
		return response;
	}

	// data = '萬,千,百'
	if (typeof data === 'string') {
		data = data.split(',');
		var response = [];
		for (var i = 0, l = data.length; i < l; i++) {
			var text = data[i];
			response.push(position[text]);
		}
		return response.join(',');
	}
}

// console.log('​convertPosition', convertPosition('萬,千,百'));

/**
 * @description 轉換大小單雙選項
 * @param {array}
 *                data 可以輸入array或string。 array => ['大', '小', '單'] ; string =>
 *                '大,小,單'
 * @returns {array} 輸入array回傳就array，輸入string回傳就string。 [8, 4, 2] ; '8,4,2'
 */

function convertChoice(data) {

	var position = {
		'1' : 8,
		'2' : 4,
		'3' : 2,
		'4' : 1
	};

	// 1. 如果 data = ['大', '小', '單']
	if (data instanceof Array) {
		var response = [];
		for (var i = 0, l = data.length; i < l; i++) {
			var text = data[i];
			response.push(position[text]);
		}

		return response;
	}

	// 2. 如果 data = '大,小,單'
	if (typeof data === 'string') {
		data = data.split(',');
		var response = [];
		for (var i = 0, l = data.length; i < l; i++) {
			var text = data[i];
			response.push(position[text]);
		}
		return response.join(',');
	}
}

// 五星總和大小單雙
function convert5xbxds(data) {

	var position = {
		'1' : 8,
		'2' : 4,
		'3' : 2,
		'4' : 1
	};

	if (data instanceof Array) {
		var response = [];
		for (var i = 0, l = data.length; i < l; i++) {
			var text = data[i];
			response.push(position[text]);
		}

		return response;
	}

	if (typeof data === 'string') {
		data = data.split(',');
		var response = [];
		for (var i = 0, l = data.length; i < l; i++) {
			var text = data[i];
			response.push(position[text]);
		}
		return response.join(',');
	}
}

// 五星總和組合大小單雙
function convert5xbxdszh(data) {

	var position = {
		'1' : 10,
		'2' : 6,
		'3' : 9,
		'4' : 5
	};

	if (data instanceof Array) {
		var response = [];
		for (var i = 0, l = data.length; i < l; i++) {
			var text = data[i];
			response.push(position[text]);
		}
		return response;
	}

	if (typeof data === 'string') {
		data = data.split(',');
		var response = [];
		for (var i = 0, l = data.length; i < l; i++) {
			var text = data[i];
			response.push(position[text]);
		}
		return response.join(',');
	}
}

function getSum(data) {

	if (typeof data === 'string') {
		data = data.split(',');
	}

	var sum = 0;
	for (var i = 0, l = data.length; i < l; i++) { // [16,8,4]
		var text = data[i];
		sum += Number(text);
	}
	return sum;
}

/**
 * @description 將其他資訊組合起來 (舊的!應該不會再使用到)
 * @param {object}
 *                otherData 其他需組合的資訊
 * @param {array}
 *                data 原先function回傳的資料
 * @returns {array} 回傳array裡面一筆一筆資料object
 */

function generateScope(otherInfo, betData) {

	var response = [];
	for (var i = 0, l = betData.length; i < l; i++) {
		var bet = betData[i];
		var dataObj = {};

		for ( var key in otherInfo) {
			if (otherInfo.hasOwnProperty(key)) {
				var property = otherInfo[key];
				dataObj[key] = property;
			}
		}
		dataObj['data'] = bet;

		response.push(dataObj);
	}
	return response;
}

/*
 * var cc = ["7|0|2|8|8", "7|0|2|9|8", "7|1|2|8|8", "7|1|2|9|8", "9|0|2|8|8",
 * "9|0|2|9|8", "9|1|2|8|8", "9|1|2|9|8"]; var scope = { 'type': 'ssc5x',
 * 'test': '123' }; console.log('​generateScope -> generateScope',
 * generateScope(scope, cc));
 */

// 產生位置代號 (應該不會再使用到)
function getPositionCode(position, num) {
	var data = combine(position, num);
	// debugger;
	var positionCode = [];
	for (var i = 0, l = data.length; i < l; i++) {
		var sum = getSum(convertPosition(data[i]));
		positionCode.push(sum);
	}

	return positionCode;
}

/**
 * @description 取得 played id
 * @param {string}
 *                title 玩法對應的名稱 ex: 前四組選24 => sscr4z24_30
 * @returns {string} played id
 */
function getPlayedId(title) {
	// console.log("getPlayedId:"+title);
	var playedIdList = subOrderInfo;

	if (playedIdList.hasOwnProperty(title)) {
		return playedIdList[title];
	} else {
		throw new CustomError('getPlayedId', 'playId無法mapping');
	}
}

/**
 * @description 取得選擇到的號碼所對應的注數
 * @param {string}
 *                note 注數對照表的編號
 * @param {string}
 *                data 選項的值
 * @returns {string} played id
 */
function getNoOfBet(note, data) {
	var noOfBetList = subOrderInfoNoOfBet;
	var noOfBet = 0;
	if (noOfBetList.hasOwnProperty(note)) {
		if (note == 1 || note == 2 || note == 9 || note == 10) {
			return noOfBetList[note][0];
		} else {
			for ( var key in noOfBetList[note]) {
				if (key == data) {
					return noOfBetList[note][key];
				}
			}
		}
		return noOfBet;
	} else {
		throw new CustomError('getNoOfBet', '注數無法mapping');
	}
}

/**
 * @description 組合子單裡每一筆資料
 * @param {string}
 *                type 每個玩法的名稱
 * @param {string}
 *                value 選項的值
 * @param {object}
 *                info 其他資訊
 * @returns {object}
 */
function getDataObj(type, value, info) {
	var a = new Date().getTime();
	var moneyUnit = info['moneyUnit']; // 單位
	var bonusRatio = info['bonusRatio']; // 獎金組
	var bonusSetMax = info['bonusSetMax']; // 獎金組 最高
	var baselineObj = info['baselineObj']; // 賠率
	var relativeBaseline = info["relativeBaseline"]; // 盤口賠率
	var periodNum = info["periodNum"];
	var handicap = info["handicap"];
	var minAuthId = info["minAuthId"];
	var baseline;
	var dtSwitch;
	var dtRatio;
	var dtBonus;
	var baseBet;
	var dataObj = {};

	var playInfo = getPlayedId(type);
	var note = playInfo['note'];
	var playedId = playInfo['playedId'];
	var baselineIndex = playInfo['baselineIndex'];

	var noOfBet = getNoOfBet(note, value); // 注數

	var key = Object.keys(baselineObj).sort(function(a,b){return a-b;});

	var tmpKey;
	var tmpBaselineIndex = 0;
	if (key.length <= 0) {
		throw new CustomError('getDataObj', 'baselineObj 內容有誤');
	} else if (key.length > 0) {
		tmpBaselineIndex = parseInt(baselineIndex) - 1;
		tmpKey = key[tmpBaselineIndex];
	}

	baseline = baselineObj[tmpKey].baseline;
	dtSwitch = baselineObj[tmpKey].dt_switch;
	dtRatio = baselineObj[tmpKey].dt_ratio;
	dtBonus = baselineObj[tmpKey].dt_bonus;
	baseBet = baselineObj[tmpKey].base_bet;

	info['baseBet'] = baseBet;
	info['dtSwitch'] = dtSwitch;
	info['dtRatio'] = dtRatio;
	info['dtBonus'] = dtBonus;

	if (moneyUnit == "" || typeof moneyUnit === "undefined" || moneyUnit == null) {
		throw new CustomError('getDataObj', 'moneyUnit 內容有誤');
	}
	if (bonusRatio == "" || typeof bonusRatio === "undefined" || bonusRatio == null) {
		throw new CustomError('getDataObj', 'bonusRatio 內容有誤');
	}
	if (baseline == "" || typeof baseline === "undefined" || baseline == null) {
		throw new CustomError('getDataObj', 'baseline 內容有誤');
	}
	if (relativeBaseline == "" || typeof relativeBaseline === "undefined" || relativeBaseline == null) {
		throw new CustomError('getDataObj', 'relativeBaseline 內容有誤');
	}
	if (value == "" || typeof value === "undefined" || value == null) {
		throw new CustomError('getDataObj', 'value 內容有誤');
	}
	if (type == "" || typeof type === "undefined" || type == null) {
		throw new CustomError('getDataObj', 'type 內容有誤');
	}
	if (bonusSetMax == "" || typeof bonusSetMax === "undefined" || bonusSetMax == null) {
		throw new CustomError('getDataObj', 'bonusSetMax 內容有誤');
	}

	dataObj['betData'] = value; // 下注內容
	dataObj['playedId'] = playedId; // 子單id
	// 子單金額 = 單位*幾注 -先不乘-幾倍
	var amount = times(parseFloat(moneyUnit), parseFloat(noOfBet));
	dataObj['amount'] = amount; // 子單金額
	dataObj['noOfBet'] = noOfBet; // 子單注數
	dataObj['baseline'] = baseline; // 賠率
	// 子單中獎時給多少錢 = 單位*(賠率(%)/100)*(盤口賠率*獎金組/2000) -先不乘-幾倍
	var bonus = times(parseFloat(moneyUnit), parseFloat(baseline), divide(parseFloat(relativeBaseline), 100), divide(parseFloat(bonusRatio), 2000));
	dataObj['bonus'] = "" + bonus; // 子單中獎時給多少錢

	dataObj['moneyUnit'] = "" + moneyUnit;
	dataObj['relativeBaseline'] = "" + relativeBaseline;
	dataObj['bonusRatio'] = "" + bonusRatio;

	dataObj['baselineIndex'] = baselineIndex;

	delete moneyUnit, bonusRatio, baseline, relativeBaseline, playInfo, note, playedId, noOfBet, amount, bonus;
	moneyUnit = undefined;
	bonusRatio = undefined;
	baseline = undefined;
	relativeBaseline = undefined;
	playInfo = undefined;
	note = undefined;
	playedId = undefined;
	noOfBet = undefined;
	amount = undefined;
	bonus = undefined;
	// console.log(new Date().getTime()-a);
	return dataObj;
}

/**
 * 要根據 value 號碼 取出最小賠率
 * 
 * @param type
 * @param value
 *                Array
 * @param info
 * @returns
 */
function getDataObj2(type, value , info) {
	var minAuthIdArr = ["639","640","641","642","643","644","650","652","653","654","655","656","657","658","659","660"];
	if(Array.isArray(value) == false){
		throw new CustomError('getDataObj', 'value 內容有誤');
	}
	var a = new Date().getTime();
	var moneyUnit = info['moneyUnit']; // 單位
	var bonusRatio = info['bonusRatio']; // 獎金組
	var bonusSetMax = info['bonusSetMax']; // 獎金組 最高
	var baselineObj = info['baselineObj']; // 賠率
	var relativeBaseline = info["relativeBaseline"]; // 盤口賠率
	var periodNum = info["periodNum"];
	var handicap = info["handicap"];
	var minAuthId = info["minAuthId"];
	var baseline;
	var dtSwitch;
	var dtRatio;
	var dtBonus;
	var baseBet;
	var dataObj = {};
	
	//console.log(baselineObj);

	var playInfo = getPlayedId(type);
	var note = playInfo['note'];
	var playedId = playInfo['playedId'];
	var baselineIndex = playInfo['baselineIndex'];

	var noOfBet = getNoOfBet(note, value.join(",")); // 注數

	var key = Object.keys(baselineObj).sort(function(a,b){return a-b;});
	//var val; 
	
	for(var i = 0 ; i < value.length ; i++){
		var val = toInt(value[i]);
		if(typeof baselineObj[val] === "object"){
			if(typeof baseline !== "undefined" ? parseFloat(baseline) > parseFloat(baselineObj[val].baseline) : true){
				baseline = baselineObj[val].baseline;
				dtSwitch = baselineObj[val].dt_switch;
				dtRatio = baselineObj[val].dt_ratio;
				dtBonus = baselineObj[val].dt_bonus;
				baseBet = baselineObj[val].base_bet;
				
				baselineIndex = val;
				//val = val;
			}
		}
	}
	
	info['baseBet'] = baseBet;
	info['dtSwitch'] = dtSwitch;
	info['dtRatio'] = dtRatio;
	info['dtBonus'] = dtBonus;

	if (moneyUnit == "" || typeof moneyUnit === "undefined" || moneyUnit == null) {
		throw new CustomError('getDataObj', 'moneyUnit 內容有誤');
	}
	if (bonusRatio == "" || typeof bonusRatio === "undefined" || bonusRatio == null) {
		throw new CustomError('getDataObj', 'bonusRatio 內容有誤');
	}
	if (baseline == "" || typeof baseline === "undefined" || baseline == null) {
		throw new CustomError('getDataObj', 'baseline 內容有誤');
	}
	if (relativeBaseline == "" || typeof relativeBaseline === "undefined" || relativeBaseline == null) {
		throw new CustomError('getDataObj', 'relativeBaseline 內容有誤');
	}
	if (value == "" || typeof value === "undefined" || value == null) {
		throw new CustomError('getDataObj', 'value 內容有誤');
	}
	if (type == "" || typeof type === "undefined" || type == null) {
		throw new CustomError('getDataObj', 'type 內容有誤');
	}
	if (bonusSetMax == "" || typeof bonusSetMax === "undefined" || bonusSetMax == null) {
		throw new CustomError('getDataObj', 'bonusSetMax 內容有誤');
	}

	//dataObj['betData2'] = (val/10) >= 1 ? ""+val : "0"+val;
	if(minAuthIdArr.indexOf(minAuthId) >= 0){
		dataObj['betData'] = "1"; // 下注內容
	}
	else{
		for(var i = 0 ; i < value.length ; i++){
			if(value[i] > 49){
				value[i] = value[i]-49;
			}
		}
		dataObj['betData'] = value.join(","); // 下注內容
		
	}
	
	dataObj['playedId'] = playedId; // 子單id
	// 子單金額 = 單位*幾注 -先不乘-幾倍
	var amount = times(parseFloat(moneyUnit), parseFloat(noOfBet));
	dataObj['amount'] = amount; // 子單金額
	dataObj['noOfBet'] = noOfBet; // 子單注數
	dataObj['baseline'] = baseline; // 賠率
	// 子單中獎時給多少錢 = 單位*(賠率(%)/100)*(盤口賠率*獎金組/2000) -先不乘-幾倍
	var bonus = times(parseFloat(moneyUnit), parseFloat(baseline), divide(parseFloat(relativeBaseline), 100), divide(parseFloat(bonusRatio), 2000));
	dataObj['bonus'] = "" + bonus; // 子單中獎時給多少錢

	dataObj['moneyUnit'] = "" + moneyUnit;
	dataObj['relativeBaseline'] = "" + relativeBaseline;
	dataObj['bonusRatio'] = "" + bonusRatio;

	dataObj['baselineIndex'] = baselineIndex;

	delete moneyUnit, bonusRatio, baseline, relativeBaseline, playInfo, note, playedId, noOfBet, amount, bonus;
	moneyUnit = undefined;
	bonusRatio = undefined;
	baseline = undefined;
	relativeBaseline = undefined;
	playInfo = undefined;
	note = undefined;
	playedId = undefined;
	noOfBet = undefined;
	amount = undefined;
	bonus = undefined;
	// console.log(new Date().getTime()-a);
	return dataObj;
}


function responseFormat(status, content) {
	var response = {};

	response['status'] = status;
	response['content'] = content;

	return JSON.stringify(response);
}

// 判斷陣列是否有重複
function checkArrIsRepeat(arr) {
	if (Array.isArray(arr)) {
		var obj = {};
		for (var i = 0; i < arr.length; i++) {
			if (typeof obj[arr[i]] === "undefined") {
				obj[arr[i]] = 0;
			} else {
				return true;
			}

		}
		return false;
	}
	return true;
}
// 刪除 陣列中重複的值
function deleteArrRepeatVal(arr) {
	var result = [];
	if (Array.isArray(arr)) {
		var obj = {};
		for (var i = 0; i < arr.length; i++) {
			if (typeof obj[arr[i]] === "undefined") {
				obj[arr[i]] = 0;
				result.push(arr[i]);
			}
		}
	}
	return result;
}
// 刪除 陣列中空值
function deleteArrayNullVal(arr) {
	var result = [];
	for (var i = 0; i < arr.length; i++) {
		if (arr[i] != null && typeof arr[i] != "undefined") {
			arr[i] = arr[i].trim();
			if (arr[i].length > 0 && arr[i] != "") {
				result.push(arr[i]);
			}
		}
	}

	return result;
}
//Map
Array.prototype.map = function(fn){
	
	var T,A,k;
	
	if(this == null){
		throw new TypeError('this is null not default');
	}
	var O = Object(this);
	var len = O.length >>> 0;
	if(typeof fn !== "function"){
		throw new TypeError(fn+' is not a function');
	}
	if(arguments > 1){
		T = arguments[1];
	}
	
	A = new Array();
	
	k = 0;
	
	while(k < len){
		var kValue,mappedValue;
		
		if(k in O){
			kValue = O[k];
			mappedValue = fn.call(T,kValue,k,O);
			A[k] = mappedValue;
		}
		k++;
	}
	return A;
}

//注數C計算
function computXuanhaoZhushu(l1,l2){
	if(l1<l2){
		return 0;
	}
	else if(l1 == l2){
		return 1;
	}
	var k1 = 1;
	var k2 = 1;
	for(var i = 1 ; i <= l2 ; i++){
			
		if(l1 == k2){
			k1 *=1;
			k2 =1;
		}
		else{
			k1 *=l1;
			k2 *=i;
			
			if(k1%k2 == 0){
				k1 = k1/k2;
				k2 = 1;
			}
		}
		l1--;
	}
	return k1/k2;
}
