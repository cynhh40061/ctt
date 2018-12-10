function js_game() {
	return true;
}

/**
 * @description 位數名稱列表
 * @type {array}
 */

/**
 * 
 * @description 不用特別轉換的
 * @param {string}
 *                bet 投注列表：'1,4,7,8'。
 * @param {number}
 *                stars
 * @param {object}
 *                info
 * @returns {array}
 */

function directTransfer2OutputBdw(bet, stars, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var responseArray = [];
		var betData = bet.split(",");
		betData.forEach(function(value) {
			var dataObj = getDataObj(type, '' + value, info);
			responseArray.push(dataObj);
		});
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function directTransfer2OutputDds(bet, stars, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var responseArray = [];
		var betData = bet.split(",");
		betData.forEach(function(value) {
			var realType = type + (6 - parseInt(value));
			var dataObj = getDataObj(realType, '1', info);
			responseArray.push(dataObj);
		});
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function directTransfer2OutputCzw(bet, stars, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var responseArray = [];
		var betData = bet.split(",");
		betData.forEach(function(value) {
			var realType = type + value;
			var dataObj = getDataObj(realType, '1', info);
			responseArray.push(dataObj);
		});
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function c11x5zx(bet, stars, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		if (betData.length < stars) {
			return responseFormat('error', '最小長度不符合');
		} else {
			var responseArray = [];
			var data = newCombine2(betData, stars);
			data.forEach(function(value) {
				var dataObj = getDataObj(type, value, info);
				responseArray.push(dataObj);
			});
			return responseArray;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_115dwdlx(bet, type, info) { // dwd

	var typePrefix = type;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		var responseArray = [];

		for (var i = 0, l = betData.length; i < l; i++) {

			var data = betData[i];
			for (var j = 0, len = data.length; j < len; j++) {

				var value = data[j];
				var type = typePrefix + (i + 1);
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);

				delete value, type, dataObj;
				value = undefined;
				type = undefined;
				dataObj = undefined;
			}
			delete data;
			data = undefined;
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();

				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	} finally {
		delete typePrefix, betData;
		typePrefix = undefined;
		betData = undefined;
	}
}

// [3D]
// 直選和值
// 組選和值

// [11選5]
// 前三不定位膽 '01,02,03'
// 趣味型 訂單雙 '5單0雙,4單1雙,3單2雙,2單3雙,1單4雙,0單5雙'
// 趣味型 猜中位 '3,4,5,6,7,8,8,9'

// [快3]
// 和值 '3,4,5,6,7'
// 三同號單選 '111,222,333'
// 二同號複選 '11*,22*,33*'
// 大小 '大,小'
// 單雙 '單,雙'
// 全紅 '全紅'
// 全黑 '全黑'

// [PK10]
// 和值 '3,4,5,6,7'
// 大小單雙 '大,小,單,雙'

// 組選三和值
function ctt_sschr3zhz(bet, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		return generateScope(info, descarteCalculate(bet));
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 一帆風順
function ctt_sscyffs(bet, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		// return betData;
		var responseArray = [];
		betData.forEach(function(value) {
			var dataObj = getDataObj(type, value, info);

			responseArray.push(dataObj);
		});

		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 好事成雙
function ctt_sschscs(bet, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var responseArray = [];
		betData.forEach(function(value) {
			var dataObj = getDataObj(type, value, info);

			responseArray.push(dataObj);
		});

		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 三星報喜
function ctt_sscsxbx(bet, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var responseArray = [];
		betData.forEach(function(value) {
			var dataObj = getDataObj(type, value, info);

			responseArray.push(dataObj);
		});

		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 四季發財
function ctt_sscsjfc(bet, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var responseArray = [];
		betData.forEach(function(value) {
			var dataObj = getDataObj(type, value, info);

			responseArray.push(dataObj);
		});

		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 三星一碼不定位
// 三星一碼不定位
function ctt_f3dr3bdw1m(bet, type, position, info) {

	var typePrefix = type;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var data = transfer2Group(bet);

		if (data.length < 1) {
			return responseFormat('error', '輸入的數量錯誤');
		}
		var responseArray = [];
		data.forEach(function(value) {
			var dataObj = getDataObj(type, value, info);

			responseArray.push(dataObj);
		});

		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_sscr3bdw1m(bet, type, position, info) {

	var typePrefix = type;
	selectionQty = 3;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var data = transfer2Group(bet);
		var keys = transfer2Group(position);
		if (keys.length < selectionQty) {
			return responseFormat('error', '輸入的數量錯誤');
		}
		if (data.length < 1) {
			return responseFormat('error', '輸入的數量錯誤');
		}

		var responseArray = [];

		var keysCombined = combine(keys, selectionQty);

		data.forEach(function(value) {
			for (var i = 0; i < keysCombined.length; i++) {
				var type = typePrefix + getSum(convertPosition(keysCombined[i].join(",")));
				var dataObj = getDataObj(type, value, info);
				responseArray.push(dataObj);
			}

		});
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 四星一碼不定位
function ctt_sscr4bdw1m(bet, type, position, info) {
	var typePrefix = type;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var data = transfer2Group(bet);

		if (data.length < 1) {
			return responseFormat('error', '輸入的數量錯誤');
		}

		var responseArray = [];

		data.forEach(function(value) {
			var type = typePrefix + getSum(convertPosition(position));
			var dataObj = getDataObj(type, value, info);

			responseArray.push(dataObj);
		});

		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_sscr5bdw1m(bet, type, position, info) {
	var typePrefix = type;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var data = transfer2Group(bet);

		if (data.length < 1) {
			return responseFormat('error', '輸入的數量錯誤');
		}

		var responseArray = [];

		data.forEach(function(value) {
			var dataObj = getDataObj(type, value, info);

			responseArray.push(dataObj);
		});

		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * @description
 * @param {string}
 *                bet 投注列表：123,45,2,59
 * @param {number}
 *                stars 幾星or選幾個號碼
 * @param {object}
 *                info 玩法種類的代碼
 * @returns {array}
 */
function ctt_dxwf(bet, stars, info) {

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {
			if (bet.split('|').length !== stars) {
				return responseFormat('error', '輸入內容的數量有錯誤');
			}
			return generateScope(info, descarteCalculate(bet));

		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 3D 二星直選複式 '7,8,9|0,3,4', 2
// 3D 三星直選複式 '1,2,4|4|0,1', 3
// 四星直選複式 '7,9|0,4|2|8,9', 4
// 五星直選複式 '7,9|0,1|2|8,9|8', 5

// 二大小單雙 '大,小,單|大,小,雙', 2
// 三大小單雙 '大,小|小,單|單,雙', 3

// 五碼趣味三星 '小|小,大|0,1|1,2|3', 5
// 四碼趣味三星 '小,大|0,1|1,2|3', 4
// 前後三碼趣味二星 '小,大|0,1|1,2', 3

// 五碼區間三星 '三區,四區|一區|0,1|1,3|3', 5
// 四碼區間三星 '三區,四區|0,2|2,3|3', 4
// 前後三碼區間三星 '三區,四區|0,2|2,3', 3

// 任選三大小單雙 '大,小,單,雙|大,小,單,雙|大,小,單,雙', 3
// 任選二大小單雙 '大,小,單,雙|大,小,單,雙', 2

function ctt_ssc5x(bet, type, info) {

	var stars = 5;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {
			if (bet.split('|').filter(function(v) {
				return v != ''
			}).length !== stars) {
				return responseFormat('error', '輸入內容的數量有錯誤');
			}
			var responseArray = [];
			var noOfSubOrders = subOrderCalculateBeforeDescarte(bet);
			if (noOfSubOrders < 0) {
				var data = descarteCalculate(bet);
				data.forEach(function(value) {
					var dataObj = getDataObj(type, value, info);
					responseArray.push(dataObj);
				});
			} else {
				var dataObj = getDataObj(type, '1', info);
				dataObj.amount = dataObj.amount * noOfSubOrders;
				dataObj.noOfBet = dataObj.noOfBet * noOfSubOrders;
				responseArray.push(dataObj);
			}
			return responseArray;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * 
 * @description 不含重複號碼類型的組選 => 五星組選120、四星組選24、二星組選複試 =>
 *              前三後三兩碼不定位、四星兩碼不定位、五星三碼不定位
 * @param {string}
 *                bet 投注列表：'0, 1, 2, 3, 4'
 * @param {number}
 *                stars 選幾個號碼
 * @param {object}
 *                info
 * @returns {array}
 */

function ssczx(bet, stars, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		if (betData.length < stars) {
			return responseFormat('error', '最小長度不符合');
		} else {
			var responseArray = [];
			var data = newCombine(betData, stars);
			data.forEach(function(value) {
				var dataObj = getDataObj(type, value, info);
				responseArray.push(dataObj);
			});
			return responseArray;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// [3D]
// 前後二星組選複式 '2,3,4,6', 2
// 二碼不定位

// [11選5]
// 前二組選 '02,03,04,07', 2
// 前三組選 '02,03,04,06,07', 3

// 任選一中一 '02,03,04,07', 1
// 任選二中二 '02,03,04,07', 2
// 任選三中三 '02,03,04,07', 3
// 任選四中四 '02,03,04,05,06', 4
// 任選五中五 '02,03,04,05,06,07', 5

// 任選六中五 '02,03,04,05,06,07,08', 6
// 任選七中五 '02,03,04,05,06,07,09,10', 7
// 任選八中五 '02,03,04,05,06,07,09,10,11', 8

// [快3]
// 3不同號 '2,3,4,5', 3
// 2不同號 '2,3,4,5', 2

// 二星組選複式
function ctt_f3dr2z(bet, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		if (betData.length < 2) {
			return responseFormat('error', '最小長度不符合');
		}
		var data = newCombine(betData, 2);

		var responseArray = [];
		data.forEach(function(value) {
			var dataObj = getDataObj(type, value, info);
			responseArray.push(dataObj);
		});
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 二星組選複式
function ctt_sscr2z(bet, type, position, info) {
	var typePrefix = type;
	var selectionQty = 2;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		if (betData.length < selectionQty) {
			return responseFormat('error', '輸入的數量過少');
		}
		if (position.split(",").length < 2) {
			return responseFormat('error', '輸入的位數數量過少');
		}

		var data = newCombine(betData, selectionQty);
		var positionData = transfer2Group(position);
		var keysCombined = combine(positionData, 2);

		var responseArray = [];

		for (var i = 0, l = data.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {
				var sum = getSum(convertPosition(keysCombined[j]));
				var value = data[i];

				var type = typePrefix + sum;
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			}
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_ssc5xz120(bet, type, info) {
	var stars = 5;

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		if (betData.length < stars) {
			return responseFormat('error', '最小長度不符合');
		} else {

			var responseArray = [];
			var data = newCombine(betData, stars);

			data.forEach(function(value) {
				var dataObj = getDataObj(type, value, info);
				responseArray.push(dataObj);
			});

			return responseArray;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_ssc5xz60(bet, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}

		var single_num_min_limited_qty = 3;

		var betData = transfer2Group(bet);
		var duplicateData = betData[0];
		var restData = betData[1];

		if (restData.length < single_num_min_limited_qty) {
			return responseFormat('error', '單號輸入的數量不正確');
		}

		var responseArray = [];
		for (var i = 0, l = duplicateData.length; i < l; i++) {
			var singleNumbers = Array.from(restData);

			var index = singleNumbers.indexOf(duplicateData[i]);

			if (index >= 0) {
				singleNumbers.splice(index, 1);
			}
			if (singleNumbers.length < single_num_min_limited_qty) {
				continue;
			}

			var restCombine = newCombine(singleNumbers, single_num_min_limited_qty);
			for (var j = 0, s = restCombine.length; j < s; j++) {
				var value = duplicateData[i];
				value = value + '|' + restCombine[j];
				var dataObj = getDataObj(type, value, info);
				responseArray.push(dataObj);
			}
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * 
 * @description 5星組選10 三重＋二重
 * @param {string}
 *                bet 投注列表：'1,4|4,7,8'。 前面三重,後面二重
 * @param {number}
 *                stars 共選Ｎ個號碼（Ｎ星）
 * @returns {array}
 */

function ctt_ssc5xz10(bet, type, info) {
	var stars = 5;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var firstNumbers = betData[0];
		var secondNumbers = betData[1];

		if (betData.length != 2) {
			return responseFormat('error', '輸入的數量不正確');
		}

		if (firstNumbers.length < 1) {
			return responseFormat('error', '三重號輸入的數量不正確');
		}

		if (secondNumbers.length < 1) {
			return responseFormat('error', '二重號輸入的數量不正確');
		}

		var responseArray = [];

		for (var i = 0, l = firstNumbers.length; i < l; i++) {
			var secondNumbersArray = Array.from(secondNumbers);

			var index = secondNumbersArray.indexOf(firstNumbers[i]);

			if (index >= 0) {
				secondNumbersArray.splice(index, 1);
			}

			for (var j = 0, len = secondNumbersArray.length; j < len; j++) {
				var combinedString = firstNumbers[i] + '|' + secondNumbersArray[j];

				var dataObj = getDataObj(type, combinedString, info);
				responseArray.push(dataObj);
			}
		}
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_ssc5xz20(bet, type, info) {

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var stars = 5;
		var single_num_min_limited_qty = 2;

		var betData = transfer2Group(bet);
		var duplicateData = betData[0];
		var restData = betData[1];

		if (betData.length != 2) {
			return responseFormat('error', '輸入的數量不正確');
		}

		if (duplicateData.length < 1) {
			return responseFormat('error', '三重號輸入的數量不正確');
		}

		if (restData.length < 2) {
			return responseFormat('error', '單號輸入的數量不正確');
		}

		var responseArray = [];

		for (var i = 0, l = duplicateData.length; i < l; i++) {
			var singleNumbers = Array.from(restData);

			var index = singleNumbers.indexOf(duplicateData[i]);

			if (index >= 0) {
				singleNumbers.splice(index, 1);
			}

			if (singleNumbers.length < single_num_min_limited_qty) {
				continue;
			}

			var restCombine = newCombine(singleNumbers, single_num_min_limited_qty);

			for (var j = 0, s = restCombine.length; j < s; j++) {
				var value = duplicateData[i];
				value = value + '|' + restCombine[j];

				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			}
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_ssc5xz5(bet, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var stars = 5;
		var single_num_min_limited_qty = 1;

		var betData = transfer2Group(bet);
		var duplicateData = betData[0];
		var restData = betData[1];

		if (betData.length != 2) {
			return responseFormat('error', '輸入的數量不正確');
		}

		if (duplicateData.length < 1) {
			return responseFormat('error', '四重號輸入的數量不正確');
		}

		if (restData.length < 1) {
			return responseFormat('error', '單號輸入的數量不正確');
		}

		var responseArray = [];

		for (var i = 0, l = duplicateData.length; i < l; i++) {
			var singleNumbers = Array.from(restData);

			var index = singleNumbers.indexOf(duplicateData[i]);

			if (index >= 0) {
				singleNumbers.splice(index, 1);
			}

			if (singleNumbers.length < single_num_min_limited_qty) {
				continue;
			}

			var restCombine = newCombine(singleNumbers, single_num_min_limited_qty);

			for (var j = 0, s = restCombine.length; j < s; j++) {
				var value = duplicateData[i];
				value = value + '|' + restCombine[j];

				var dataObj = getDataObj(type, value, info);
				responseArray.push(dataObj);
			}
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * 
 * @description 五星總和大小單雙
 * @param {string}
 *                bet '大,小,單,雙'
 * @param {number}
 *                stars
 * @param {object}
 *                info
 * @returns {array}
 */

function ctt_ssc5xbxds(bet, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		if (betData.length < 1) {
			return responseFormat('error', '沒有輸入資料');
		}

		var responseArray = [];
		for (var i = 0, l = betData.length; i < l; i++) {

			var value = betData[i];
			var sum = getSum(convert5xbxds(value));
			var title = type + sum;

			var dataObj = getDataObj(title, value, info);
			responseArray.push(dataObj);

		}

		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * 
 * @description 五星總和組合大小單雙
 * @param {string}
 *                bet '大單,小單,大雙,小雙'
 * @param {number}
 *                stars
 * @param {object}
 *                info
 * @returns {array}
 */

function ctt_ssc5xbxdszh(bet, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		if (betData.length < 1) {
			return responseFormat('error', '沒有輸入資料');
		}

		var responseArray = [];
		for (var i = 0, l = betData.length; i < l; i++) {
			var value = betData[i];
			var sum = getSum(convert5xbxdszh(value));

			var title = type + sum;

			var dataObj = getDataObj(title, value, info);
			responseArray.push(dataObj);
		}

		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * 
 * @description Ｎ星 [二重號、三重號]＋[單號/重號]的組合 => 五星組選60、五星組選30、五星組選20、五星組選5 =>
 *              四星組選12、四星組選6、四星組選4
 * 
 * @param {string}
 *                bet 投注列表：'6,7|5,2'。第一組為重號，後面一組皆為單號
 * @param {number}
 *                stars 共選Ｎ個號碼（Ｎ星）
 * @param {number}
 *                firstDuplicateQty 第一組重複的號碼有Ｘ個(Ｘ重)
 * @param {number}
 *                secondDuplicateQty 第2組重複的號碼有Y個(Y重) => 預設0：單號
 * @returns {array}
 */

function ctt_duplicateNumbers(bet, stars, firstDuplicateQty, secondDuplicateQty) {
	try {

		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var duplicateData = betData[0];
		var restData = betData[1];

		var response = [];

		for (var i = 0, l = duplicateData.length; i < l; i++) {
			var singleNumbers = Array.from(restData);

			var index = singleNumbers.indexOf(duplicateData[i]);

			if (index >= 0) {
				singleNumbers.splice(index, 1);
			}

			if (secondDuplicateQty > 0) {
				// 後面那組是2重號或3重
				if (singleNumbers.length === 1) {
					var reCombineData = [];
					reCombineData.push(duplicateData[i]);
					reCombineData.push(singleNumbers[0]);
					response.push(reCombineData);
				}

				if (singleNumbers.length > 1) {
					for (var j = 0, s = singleNumbers.length; j < s; j++) {
						var reCombineData = [];
						reCombineData.push(duplicateData[i]);
						reCombineData.push(singleNumbers[j]);
						response.push(reCombineData);
					}
				}
			}

			if (secondDuplicateQty === 0) {
				// 第二組是單號

				if (restData.length < stars - firstDuplicateQty) {
					// ex: 五星二重號，但剩下的數字小於3
					return responseFormat('error', '輸入的數量不正確');
				}

				// 被扣掉與重號重複的號碼後的「長度」如果因此造成不夠數量補足後面的位數，則跳過這次迴圈
				// 例如： 5星 [1,2] [2,3,4] => [2,2,3,4]
				// 這樣會因為重複的數字被扣掉少一個位數
				if (stars - firstDuplicateQty > singleNumbers.length) {
					continue;
				}

				if (stars - firstDuplicateQty === singleNumbers.length) {
					// 剩下號碼個數剛好等於蘿蔔坑數

					var reCombineData = [];

					var tempString = duplicateData[i];
					for (var j = 0, s1 = singleNumbers.length; j < s1; j++) {
						reCombineData.push(singleNumbers[j]);
					}
					tempString = tempString + '|' + reCombineData.join(',');
					response.push(tempString);
				}

				// 剩下號碼個數大於蘿蔔坑數
				if (stars - firstDuplicateQty < singleNumbers.length) {
					var restCombine = newCombine(singleNumbers, stars - firstDuplicateQty);

					for (var j = 0, s2 = restCombine.length; j < s2; j++) {
						var temp = duplicateData[i];
						temp = temp + '|' + restCombine[j];

						response.push(temp);
					}
				}
			}
		}
		return generateScope(info, response);
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 五星 1個二重號＋3個單號（組選60）: '1,8|2,6,4',5, 2, 0
// 四星 一個二重號＋2個單號（組選12）: '1,2,4|4,6,7', 4, 2, 0
// 四星 2個二重號（四星組選6）: '1,2,7', 4, 2, 2
// 五星 2個二重號＋一單號（五星組選30）: '1,2,3|3,5', 5, 2, 2

/**
 * @description 5星 2*2+1 （五星選30）兩組一樣的2重號 + 1單號
 * @param {string}
 *                bet 投注列表：123,245
 * @param {number}
 *                stars 共選Ｎ個號碼（Ｎ星）
 * @param {object}
 *                info
 * @returns {array}
 */

function ctt_ssc5xz30(bet, type, info) {
	var stars = 5;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var duplicateData = betData[0];
		var restData = betData[1];

		if (betData.length != 2) {
			return responseFormat('error', '輸入的數量不正確');
		}

		if (duplicateData.length < 2) {
			return responseFormat('error', '二重號輸入的數量不正確');
		}

		if (restData.length < 1) {
			return responseFormat('error', '單號輸入的數量不正確');
		}

		var permutatedData = permutationWithReOrder(duplicateData, 2); // 兩對重複的組合

		var responseArray = [];
		for (var i = 0, l = permutatedData.length; i < l; i++) {
			var currentPermutation = permutatedData[i];
			var checkData = currentPermutation.split('');

			for (var j = 0, rl = restData.length; j < rl; j++) {
				if (checkData.indexOf(restData[j]) === -1) {
					// 單號沒有跟前面號碼重複
					var value = checkData.join(',') + '|' + restData[j];

					var dataObj = getDataObj(type, value, info);
					responseArray.push(dataObj);
				} else {
					continue;
				}
			}
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * 
 * @description 3星組3
 * @param {string}
 *                bet 投注列表：'1,4,7,8'。
 * @returns {array}
 */

function ctt_f3dr3z3(bet, type, position, info) { // 原function名稱 sxzxQ3z

	var typePrefix = type;
	var selectionQty = 2;

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		if (betData.length < selectionQty) {
			return responseFormat('error', '輸入的數量過少');
		}
		if (position.split(",").length < 3) {
			return responseFormat('error', '輸入的位數數量過少');
		}

		var data = newCombine(betData, selectionQty);
		var positionData = transfer2Group(position);
		var keysCombined = combine(positionData, 3);

		var responseArray = [];

		for (var i = 0, l = data.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {
				var sum = getSum(convertPosition(keysCombined[j]));
				var value = data[i];
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			}
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_sscr3z3(bet, type, position, info) { // 原function名稱 sxzxQ3z

	var typePrefix = type;
	var selectionQty = 2;

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		if (betData.length < selectionQty) {
			return responseFormat('error', '輸入的數量過少');
		}
		if (position.split(",").length < 3) {
			return responseFormat('error', '輸入的位數數量過少');
		}

		var data = newCombine(betData, selectionQty);
		var positionData = transfer2Group(position);
		var keysCombined = combine(positionData, 3);

		var responseArray = [];

		for (var i = 0, l = data.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {
				var sum = getSum(convertPosition(keysCombined[j]));
				var value = data[i];

				var type = typePrefix + sum;
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			}
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// [時時彩][3D] 3星組3、組6

/**
 * 
 * @description 3星組6
 * @param {string}
 *                bet 投注列表：'1,4,7,8'。
 * @returns {array}
 */
function ctt_f3dr3z6(bet, type, position, info) { // 原function名稱 sxzxQ3z
	var selectionQty = 3; // 選幾個號碼

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		if (betData.length < selectionQty) {
			return responseFormat('error', '輸入的數量過少');
		}
		if (position.split(",").length < selectionQty) {
			return responseFormat('error', '輸入的位數數量過少');
		}

		var data = newCombine(betData, selectionQty);
		var positionData = transfer2Group(position);
		var keysCombined = combine(positionData, selectionQty);

		var responseArray = [];

		for (var i = 0, l = data.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {
				var sum = getSum(convertPosition(keysCombined[j]));
				var value = data[i];
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			}
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_sscr3z6(bet, type, position, info) { // 原function名稱 sxzxQ3z

	var typePrefix = type;
	var selectionQty = 3; // 選幾個號碼

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		if (betData.length < selectionQty) {
			return responseFormat('error', '輸入的數量過少');
		}
		if (position.split(",").length < selectionQty) {
			return responseFormat('error', '輸入的位數數量過少');
		}

		var data = newCombine(betData, selectionQty);
		var positionData = transfer2Group(position);
		var keysCombined = combine(positionData, selectionQty);

		var responseArray = [];

		for (var i = 0, l = data.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {
				var sum = getSum(convertPosition(keysCombined[j]));
				var value = data[i];

				var type = typePrefix + sum;
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			}
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// [時時彩][3D] 3星組3、組6

/**
 * @description 任選二、三、四 直選複式
 * @param {string}
 *                bet 投注列表：1,2|2|7,8||4,5'
 * @param {number}
 *                electionQty 任選幾個
 * @returns {array}
 */
function ctt_sxwfR(bet, selectionQty, position, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		for (var i = (betData.length - 1); i >= 0; i--) {
			if (betData[i].length === 0) {
				betData.splice(i, 1);
			}
		}

		var keys = transfer2Group(position);

		var obj = {};
		for (var i = 0, l = betData.length; i < l; i++) {
			var newKey = keys[i];
			obj[newKey] = betData[i];
		}

		// 組出key（萬千百十個）可能的組合
		var keysCombined = combine(keys, selectionQty);

		var responseArray = [];

		for (var j = 0, l = keysCombined.length; j < l; j++) {
			var currentPosition = keysCombined[j];
			var newGroup = [];
			for (var k = 0, len = currentPosition.length; k < len; k++) {
				var key = currentPosition[k];
				newGroup.push(obj[key]);
			}

			var data = descartesAlgorithm.apply(null, newGroup);

			data.forEach(function(value) {
				var dataObj = {};
				dataObj['type'] = getSum(convertPosition(currentPosition));
				dataObj['data'] = value;
				for ( var key in info) {
					if (info.hasOwnProperty(key)) {
						var property = info[key];
						dataObj[key] = property;
					}
				}
				responseArray.push(dataObj);
			});
		}
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_f3dr3(bet, type, position, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var selectionQty = 3;
		var betData = transfer2Group(bet);
		var datas = betData;
		for (var i = (datas.length - 1); i >= 0; i--) {
			if (datas[i].length === 0) {
				datas.splice(i, 1);
			}
		}

		if (datas.length < selectionQty) {
			return responseFormat('error', '輸入內容的數量有錯誤');
		}

		var keys = transfer2Group(position);

		var obj = {};
		for (var i = 0, l = betData.length; i < l; i++) {
			var newKey = keys[i];
			obj[newKey] = betData[i];
		}
		// 組出key（萬千百十個）可能的組合
		var keysCombined = combine(keys, selectionQty);
		var responseArray = [];
		var newtype = '';
		for (var j = 0, l = keysCombined.length; j < l; j++) {
			var currentPosition = keysCombined[j];
			var newGroup = [];
			for (var k = 0, len = currentPosition.length; k < len; k++) {
				var key = currentPosition[k];
				newGroup.push(obj[key]);
			}
			var data = descartesAlgorithm.apply(null, newGroup);

			data.forEach(function(value) {
				var dataObj = getDataObj(type, value, info);
				responseArray.push(dataObj);
			});
		}
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_f3dr2(bet, type, position, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var selectionQty = 2;
		var betData = transfer2Group(bet);
		var datas = betData;
		for (var i = (datas.length - 1); i >= 0; i--) {
			if (datas[i].length === 0) {
				datas.splice(i, 1);
			}
		}

		if (datas.length < selectionQty) {
			return responseFormat('error', '輸入內容的數量有錯誤');
		}

		var keys = transfer2Group(position);

		var obj = {};
		for (var i = 0, l = betData.length; i < l; i++) {
			var newKey = keys[i];
			obj[newKey] = betData[i];
		}
		// 組出key（萬千百十個）可能的組合
		var keysCombined = combine(keys, selectionQty);
		var responseArray = [];
		var newtype = '';
		for (var j = 0, l = keysCombined.length; j < l; j++) {
			var currentPosition = keysCombined[j];
			var newGroup = [];
			for (var k = 0, len = currentPosition.length; k < len; k++) {
				var key = currentPosition[k];
				newGroup.push(obj[key]);
			}
			var data = descartesAlgorithm.apply(null, newGroup);
			data.forEach(function(value) {
				var dataObj = getDataObj(type, value, info);
				responseArray.push(dataObj);
			});
		}
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 任選二直選複式
function ctt_sscr2(bet, type, position, info) {

	var typePrefix = type;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var selectionQty = 2;

		var betData = transfer2Group(bet);
		var datas = betData;
		for (var i = (datas.length - 1); i >= 0; i--) {
			if (datas[i].length === 0) {
				datas.splice(i, 1);
			}
		}

		if (datas.length < selectionQty) {
			return responseFormat('error', '輸入內容的數量有錯誤');
		}

		var keys = transfer2Group(position);

		var obj = {};
		for (var i = 0, l = betData.length; i < l; i++) {
			var newKey = keys[i];
			obj[newKey] = betData[i];
		}

		// 組出key（萬千百十個）可能的組合
		var keysCombined = combine(keys, selectionQty);

		var responseArray = [];
		var newtype = '';
		var noOfSubOrders = 0;
		for (var j = 0, l = keysCombined.length; j < l; j++) {
			var currentPosition = keysCombined[j];
			var newGroup = [];
			var tmpNoOfSubOrders = 1;
			for (var k = 0, len = currentPosition.length; k < len; k++) {
				var key = currentPosition[k];
				newGroup.push(obj[key]);
				tmpNoOfSubOrders = tmpNoOfSubOrders * obj[key].length;
			}
			noOfSubOrders = noOfSubOrders + tmpNoOfSubOrders;
			newtype = typePrefix + getSum(convertPosition(currentPosition));
		}
		if (noOfSubOrders < 0) {
			for (var j = 0, l = keysCombined.length; j < l; j++) {
				var currentPosition = keysCombined[j];
				var newGroup = [];
				for (var k = 0, len = currentPosition.length; k < len; k++) {
					var key = currentPosition[k];
					newGroup.push(obj[key]);
				}
				var data = descartesAlgorithm.apply(null, newGroup);
				data.forEach(function(value) {
					var type = typePrefix + getSum(convertPosition(currentPosition));
					var dataObj = getDataObj(type, value, info);
					responseArray.push(dataObj);
				});
			}
		} else {
			var dataObj = getDataObj(newtype, '1', info);
			dataObj.amount = dataObj.amount * noOfSubOrders;
			dataObj.noOfBet = dataObj.noOfBet * noOfSubOrders;
			responseArray.push(dataObj);
		}
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 任選三直選複式
function ctt_sscr3(bet, type, position, info) {

	var typePrefix = type;
	var selectionQty = 3;

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}

		var betData = transfer2Group(bet);

		for (var i = (betData.length - 1); i >= 0; i--) {
			if (betData[i].length === 0) {
				betData.splice(i, 1);
			}
		}

		if (betData.length < selectionQty) {
			return responseFormat('error', '輸入內容的數量有錯誤');
		}

		var keys = transfer2Group(position);

		var obj = {};
		for (var i = 0, l = betData.length; i < l; i++) {
			var newKey = keys[i];
			obj[newKey] = betData[i];
		}

		// 組出key（萬千百十個）可能的組合
		var keysCombined = combine(keys, selectionQty);

		var responseArray = [];

		var newtype = '';
		var noOfSubOrders = 0;
		for (var j = 0, l = keysCombined.length; j < l; j++) {
			var currentPosition = keysCombined[j];
			var newGroup = [];
			var tmpNoOfSubOrders = 1;
			for (var k = 0, len = currentPosition.length; k < len; k++) {
				var key = currentPosition[k];
				newGroup.push(obj[key]);
				tmpNoOfSubOrders = tmpNoOfSubOrders * obj[key].length;
			}
			noOfSubOrders = noOfSubOrders + tmpNoOfSubOrders;
			newtype = typePrefix + getSum(convertPosition(currentPosition));
		}
		if (noOfSubOrders < 0) {
			for (var j = 0, l = keysCombined.length; j < l; j++) {
				var currentPosition = keysCombined[j];
				var newGroup = [];
				for (var k = 0, len = currentPosition.length; k < len; k++) {
					var key = currentPosition[k];
					newGroup.push(obj[key]);
				}
				var data = descartesAlgorithm.apply(null, newGroup);
				data.forEach(function(value) {
					var type = typePrefix + getSum(convertPosition(currentPosition));
					var dataObj = getDataObj(type, value, info);
					responseArray.push(dataObj);
				});
			}
		} else {
			var dataObj = getDataObj(newtype, '1', info);
			dataObj.amount = dataObj.amount * noOfSubOrders;
			dataObj.noOfBet = dataObj.noOfBet * noOfSubOrders;
			responseArray.push(dataObj);
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 任選二大小單雙
function ctt_sscr2bxds(bet, type, position, info) {
	var typePrefix = type;
	var selectionQty = 2
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {
			var betData = transfer2Group(bet);

			for (var i = (betData.length - 1); i >= 0; i--) {
				if (betData[i].length === 0) {
					betData.splice(i, 1);
				}
			}

			if (betData.length < selectionQty) {
				return responseFormat('error', '輸入內容的數量有錯誤');
			}

			var keys = transfer2Group(position);

			var obj = {};
			for (var i = 0, l = betData.length; i < l; i++) {
				var newKey = keys[i];
				obj[newKey] = betData[i];
			}

			// 組出key（萬千百十個）可能的組合
			var keysCombined = combine(keys, selectionQty);

			var responseArray = [];
			for (var j = 0, l = keysCombined.length; j < l; j++) {
				var currentPosition = keysCombined[j];
				var newGroup = [];
				for (var k = 0, len = currentPosition.length; k < len; k++) {
					var key = currentPosition[k];
					newGroup.push(obj[key]);
				}

				var data = descartesAlgorithm.apply(null, newGroup);

				data.forEach(function(value) {
					var numberStyleChoice = value.split('|');
					var newCombined = convertChoice(numberStyleChoice);
					type = typePrefix + getSum(convertPosition(currentPosition)) + '_' + newCombined[0] + '_' + newCombined[1];
					var dataObj = getDataObj(type, value, info);
					responseArray.push(dataObj);
				});
			}

			return responseArray;

		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 前三大小單雙
function ctt_sscq3bxds(bet, type, position, info) {
	// bet = '大,小|單,雙|大,小';
	var typePrefix = type;
	var selectionQty = 3;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {

			var betData = transfer2Group(bet);

			for (var i = (betData.length - 1); i >= 0; i--) {
				if (betData[i].length === 0) {
					betData.splice(i, 1);
				}
			}

			if (betData.length < selectionQty) {
				return responseFormat('error', '輸入內容的數量有錯誤');
			}

			var data = descartesAlgorithm.apply(null, betData); // ES6寫法，把
										// betData
										// 展開當參數

			var response = [];

			data.forEach(function(value) {
				var dataObj = {};
				var numberStyleChoice = value.split('|');
				var newCombined = convertChoice(numberStyleChoice);
				type = typePrefix + newCombined[0] + '_' + newCombined[1] + '_' + newCombined[2];
				dataObj = getDataObj(type, value, info);
				response.push(dataObj);
			});
			return response;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 後三大小單雙
function ctt_ssch3bxds(bet, type, position, info) {
	var typePrefix = type;
	var selectionQty = 3;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {

			var betData = transfer2Group(bet);

			for (var i = (betData.length - 1); i >= 0; i--) {
				if (betData[i].length === 0) {
					betData.splice(i, 1);
				}
			}

			if (betData.length < selectionQty) {
				return responseFormat('error', '輸入內容的數量有錯誤');
			}

			var data = descartesAlgorithm.apply(null, betData); // ES6寫法，把
										// betData
										// 展開當參數

			var response = [];

			data.forEach(function(value) {
				var dataObj = {};
				var numberStyleChoice = value.split('|');
				var newCombined = convertChoice(numberStyleChoice);
				type = typePrefix + newCombined[0] + '_' + newCombined[1] + '_' + newCombined[2];
				dataObj = getDataObj(type, value, info);
				response.push(dataObj);
			});
			return response;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 任選二和值
function ctt_sscr2hz(bet, type, position, info) {

	var typePrefix = type;
	var selectionQty = 2;

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var positionData = transfer2Group(position);

		if (betData.length < 1) {
			return responseFormat('error', '輸入的數量錯誤');
		}

		if (positionData.length < selectionQty) {
			return responseFormat('error', '位數輸入的數量錯誤');
		}

		var keysCombined = combine(positionData, selectionQty);

		var responseArray = [];

		for (var i = 0, l = betData.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {

				var sum = getSum(convertPosition(keysCombined[j]));
				var value = betData[i];
				var type = typePrefix + sum;
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			}
		}

		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 任選二跨度
function ctt_sscr2kd(bet, type, position, info) {

	var typePrefix = type;
	var selectionQty = 2;

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var positionData = transfer2Group(position);

		if (betData.length < 1) {
			return responseFormat('error', '輸入的數量錯誤');
		}

		if (positionData.length < selectionQty) {
			return responseFormat('error', '位數輸入的數量錯誤');
		}

		var keysCombined = combine(positionData, selectionQty);

		var responseArray = [];

		for (var i = 0, l = betData.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {

				var sum = getSum(convertPosition(keysCombined[j]));
				var value = betData[i];
				var type = typePrefix + sum;
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			}
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 任選二組選和值
function ctt_sscr2zhz(bet, type, position, info) {

	var typePrefix = type;
	var selectionQty = 2;

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var positionData = transfer2Group(position);

		if (betData.length < 1) {
			return responseFormat('error', '輸入的數量錯誤');
		}

		if (positionData.length < selectionQty) {
			return responseFormat('error', '位數輸入的數量錯誤');
		}

		var keysCombined = combine(positionData, selectionQty);

		var responseArray = [];

		for (var i = 0, l = betData.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {

				var sum = getSum(convertPosition(keysCombined[j]));
				var value = betData[i];
				var type = typePrefix + sum;
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			}
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 任選三和值
// 任選三和值
function ctt_f3dr3hz(bet, type, position, info) {
	var selectionQty = 3;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var positionData = transfer2Group(position);

		if (betData.length < 1) {
			return responseFormat('error', '輸入的數量錯誤');
		}

		if (positionData.length < selectionQty) {
			return responseFormat('error', '位數輸入的數量錯誤');
		}

		var keysCombined = combine(positionData, selectionQty);

		var responseArray = [];

		for (var i = 0, l = betData.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {
				var sum = getSum(convertPosition(keysCombined[j]));
				var value = betData[i];
				var dataObj = getDataObj(type, value, info);
				responseArray.push(dataObj);
			}
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_sscr3hz(bet, type, position, info) {

	var typePrefix = type;
	var selectionQty = 3;

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var positionData = transfer2Group(position);

		if (betData.length < 1) {
			return responseFormat('error', '輸入的數量錯誤');
		}

		if (positionData.length < selectionQty) {
			return responseFormat('error', '位數輸入的數量錯誤');
		}

		var keysCombined = combine(positionData, selectionQty);

		var responseArray = [];

		for (var i = 0, l = betData.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {

				var sum = getSum(convertPosition(keysCombined[j]));
				var value = betData[i];
				var type = typePrefix + sum;
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);

			}
		}

		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 任選三跨度
function ctt_sscr3kd(bet, type, position, info) {

	var typePrefix = type;
	var selectionQty = 3;

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var positionData = transfer2Group(position);

		if (betData.length < 1) {
			return responseFormat('error', '輸入的數量錯誤');
		}

		if (positionData.length < selectionQty) {
			return responseFormat('error', '位數輸入的數量錯誤');
		}

		var keysCombined = combine(positionData, selectionQty);

		var responseArray = [];

		for (var i = 0, l = betData.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {

				var sum = getSum(convertPosition(keysCombined[j]));
				var value = betData[i];
				var type = typePrefix + sum;
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			}
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 任選三組選和值

function ctt_f3dr3zhz(bet, type, position, info) {
	var selectionQty = 3;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var positionData = transfer2Group(position);

		if (betData.length < 1) {
			return responseFormat('error', '輸入的數量錯誤');
		}

		if (positionData.length < selectionQty) {
			return responseFormat('error', '位數輸入的數量錯誤');
		}

		var keysCombined = combine(positionData, selectionQty);

		var responseArray = [];

		for (var i = 0, l = betData.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {

				var sum = getSum(convertPosition(keysCombined[j]));
				var value = betData[i];
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			}
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_sscr3zhz(bet, type, position, info) {

	var typePrefix = type;
	var selectionQty = 3;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var positionData = transfer2Group(position);

		if (betData.length < 1) {
			return responseFormat('error', '輸入的數量錯誤');
		}

		if (positionData.length < selectionQty) {
			return responseFormat('error', '位數輸入的數量錯誤');
		}

		var keysCombined = combine(positionData, selectionQty);

		var responseArray = [];

		for (var i = 0, l = betData.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {

				var sum = getSum(convertPosition(keysCombined[j]));
				var value = betData[i];
				var type = typePrefix + sum;
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			}
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * @description 任選三 組三組六
 * 
 * @param {string}
 *                bet 投注列表：'1,4,5,6|萬,千,百,十'
 * @param {number}
 *                selectionQty 選出幾個數字，組三=2 組六=3
 * @returns {array}
 */
function ctt_r3z3z6(bet, selectionQty) { // 原名稱 sxzxR3z3z6
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split('|');

		var numbersData = betData[0];
		var position = betData[1];
		var data = z3z6(numbersData, selectionQty);

		var type = [ position ];

		if (type.length > 3) {
			// 選3個位置以上的話，也要再排列出可能的組合
			type = newCombine(type, 3);
		}

		var responseArray = twoGroupCombine(type, data);

		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * @description 任選二三 直選和值、直選跨度
 * 
 * @param {string}
 *                bet 投注列表：'1,4,5,6|萬,千,百,十' => '1,4,5,6|16,8,4,2'
 * @param {number}
 *                selectionQty 任選 N (2或3)
 * @returns {array}
 */

function ctt_sxzx(bet, selectionQty) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split('|');

		var numbersData = betData[0];
		var position = betData[1];

		var data = transfer2Group(numbersData);
		var typeData = transfer2Group(position);
		var type = newCombine(typeData, selectionQty);

		var responseArray = twoGroupCombine(type, data);

		responseArray.forEach(function(record) {
			var type = record.type; // 16,8,4
			record.type = getSum(convertPosition(type));
		});

		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// [11選5]
// 任選二直選和值跨度 '1,2,3|萬,千,百,十', 2
// 任選三直選和值跨度 '1,2,3|萬,千,百,十', 3
// 任三一碼不定位 '1|萬,千,百,十,個', 3

// 任選四 直選複式
function ctt_sscr4(bet, type, position, info) {

	var typePrefix = type;
	var selectionQty = 4;

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		for (var i = (betData.length - 1); i >= 0; i--) {
			if (betData[i].length === 0) {
				betData.splice(i, 1);
			}
		}

		if (betData.length < selectionQty) {
			return responseFormat('error', '輸入內容的數量有錯誤');
		}

		var keys = transfer2Group(position);
		var obj = {};
		for (var i = 0, l = betData.length; i < l; i++) {
			var newKey = keys[i];
			obj[newKey] = betData[i];
		}
		// 組出key（萬千百十個）可能的組合
		var keysCombined = combine(keys, selectionQty);
		var responseArray = [];
		var newtype = '';
		var noOfSubOrders = 0;
		for (var j = 0, l = keysCombined.length; j < l; j++) {
			var currentPosition = keysCombined[j];
			var newGroup = [];
			var tmpNoOfSubOrders = 1;
			for (var k = 0, len = currentPosition.length; k < len; k++) {
				var key = currentPosition[k];
				newGroup.push(obj[key]);
				tmpNoOfSubOrders = tmpNoOfSubOrders * obj[key].length;
			}
			noOfSubOrders = noOfSubOrders + tmpNoOfSubOrders;
			newtype = typePrefix + getSum(convertPosition(currentPosition));
		}
		if (noOfSubOrders < 0) {
			for (var j = 0, l = keysCombined.length; j < l; j++) {
				var currentPosition = keysCombined[j];
				var newGroup = [];
				for (var k = 0, len = currentPosition.length; k < len; k++) {
					var key = currentPosition[k];
					newGroup.push(obj[key]);
				}
				var data = descartesAlgorithm.apply(null, newGroup);
				data.forEach(function(value) {
					var type = typePrefix + getSum(convertPosition(currentPosition));
					var dataObj = getDataObj(type, value, info);
					responseArray.push(dataObj);
				});
			}
		} else {
			var dataObj = getDataObj(newtype, '1', info);
			dataObj.amount = dataObj.amount * noOfSubOrders;
			dataObj.noOfBet = dataObj.noOfBet * noOfSubOrders;
			responseArray.push(dataObj);
		}
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * @description 任選四 組選24
 * 
 * @param {string}
 *                bet 投注列表：'1,4,5,6|萬,千,百,十'
 * @param {number}
 *                limitQty 至少要選 N 個號碼
 * @returns {array}
 */

function ctt_sscr4z24(bet, type, position, info) { // sxzxR4z24
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var typePrefix = type;
		var selectionQty = 4;

		var betData = transfer2Group(bet);

		if (betData.length < 4) {
			return responseFormat('error', '輸入內容的數量有錯誤');
		}

		if (position.split(",").length < 4) {
			return responseFormat('error', '輸入位數的數量有錯誤');
		}

		var data = newCombine(betData, selectionQty);
		var positionData = transfer2Group(position);
		var keysCombined = combine(positionData, selectionQty);

		var responseArray = [];

		for (var i = 0, l = data.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {
				var sum = getSum(convertPosition(keysCombined[j]));
				var value = data[i];

				var type = typePrefix + sum;
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			}
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function getR4z12(bet, stars) {
	if(bet.trim().length == 0){
		return responseFormat('error', '輸入的組合內容有誤');
	}

	var single_num_min_limited_qty = 2;

	var betData = transfer2Group(bet);
	var duplicateData = betData[0];
	var restData = betData[1];

	if (betData.length != 2) {
		throw new CustomError('getR4z12', '輸入的數量不正確');
		return;
	}

	if (duplicateData.length < 1) {
		throw new CustomError('getR4z12', '二重號輸入的數量不正確');
		return;
	}

	if (restData.length < 2) {
		throw new CustomError('getR4z12', '單號輸入的數量不正確');
		return;
	}

	var response = [];

	for (var i = 0, l = duplicateData.length; i < l; i++) {
		var singleNumbers = Array.from(restData);

		var index = singleNumbers.indexOf(duplicateData[i]);

		if (index >= 0) {
			singleNumbers.splice(index, 1);
		}

		if (singleNumbers.length < single_num_min_limited_qty) {
			continue;
		}

		var restCombine = newCombine(singleNumbers, single_num_min_limited_qty);

		for (var j = 0, s = restCombine.length; j < s; j++) {
			var temp = duplicateData[i];
			temp = temp + '|' + restCombine[j];

			response.push(temp);
		}
	}
	return response;
}

function getR4z6(bet) {
	if(bet.trim().length == 0){
		return responseFormat('error', '輸入的組合內容有誤');
	}

	bet = bet.replace(/\s/g, '');
	if (checkStringInput(bet)) {
		var betData = bet.split(',');

		if (betData.length < 2) {
			throw new CustomError('getR4z6', '輸入的數量不正確');
			return;

		}

		var firstGroup = betData;
		var secondGroup = betData;

		var response = {};

		for (var i = 0, l = firstGroup.length; i < l; i++) {
			var secondNumbers = Array.from(secondGroup);

			var index = secondNumbers.indexOf(firstGroup[i]);

			if (index >= 0) {
				secondNumbers.splice(index, 1);
			}

			for (var j = 0, s = secondNumbers.length; j < s; j++) {
				var reCombineData = [], idCheck = [];
				reCombineData.push(firstGroup[i]);
				reCombineData.push(secondNumbers[j]);

				// 製作 object key 讓相同的組合不會重複產生 ex: 2,3 = 3,2
				idCheck.push(firstGroup[i]);
				idCheck.push(secondNumbers[j]);
				idCheck.sort();

				var key = idCheck.join(''); // 產生id

				if (!response.hasOwnProperty(key)) {
					response[key] = reCombineData.join(',');
				}
			}
		}

		// 為了保持格式一致為 Array 所以再轉換一次
		var responseArray = Object.values(response).map(function(data) {
			return data;
		});

		return responseArray;
	}
}

function getR4z4(bet, stars) {

	var single_num_min_limited_qty = 1;
	if(bet.trim().length == 0){
		return responseFormat('error', '輸入的組合內容有誤');
	}

	var betData = transfer2Group(bet);
	var duplicateData = betData[0];
	var restData = betData[1];

	var response = [];

	for (var i = 0, l = duplicateData.length; i < l; i++) {
		var singleNumbers = Array.from(restData);

		var index = singleNumbers.indexOf(duplicateData[i]);

		if (index >= 0) {
			singleNumbers.splice(index, 1);
		}

		if (singleNumbers.length < single_num_min_limited_qty) {
			continue;
		}

		var restCombine = newCombine(singleNumbers, single_num_min_limited_qty);

		for (var j = 0, s = restCombine.length; j < s; j++) {
			var temp = duplicateData[i];
			temp = temp + '|' + restCombine[j];

			response.push(temp);
		}
	}
	return response;

}

/**
 * @description 任選四 組選12
 * 
 * @param {string}
 *                bet 投注列表：'1,4,5,6|萬,千,百,十'
 * @returns {array}
 */
function ctt_sscr4z12(bet, type, position, info) { // sxzxR4z12z4
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var typePrefix = type;
		var selectionQty = 4;

		var data = getR4z12(bet, 4);

		if (position.split(",").length < 4) {
			return responseFormat('error', '輸入位數的數量有錯誤');
		}

		var positionData = transfer2Group(position);
		var keysCombined = combine(positionData, selectionQty);

		var responseArray = [];

		for (var i = 0, l = data.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {

				var sum = getSum(convertPosition(keysCombined[j]));
				var value = data[i];

				var type = typePrefix + sum;
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			}
		}
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * @description 任選四 組選6
 * 
 * @param {string}
 *                bet 投注列表：'1,4,5,6|萬,千,百,十'
 * @param {number}
 *                limitPositionQty 至少要選 N 個位數
 * @returns {array}
 */

function ctt_sscr4z6(bet, type, position, info) { // sxzxR4z6
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var selectionQty = 4;
		bet = bet.replace(/\s/g, '');

		var data = getR4z6(bet);

		if (position.split(",").length < 4) {
			return responseFormat('error', '輸入位數的數量有錯誤');
		}

		var positionData = transfer2Group(position);
		var keysCombined = combine(positionData, selectionQty);

		var responseArray = [];

		for (var i = 0, l = data.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {
				var sum = getSum(convertPosition(keysCombined[j]));
				var title = type + sum;

				var value = data[i];
				var dataObj = getDataObj(title, value, info);
				responseArray.push(dataObj);
			}
		}

		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_sscr4z4(bet, type, position, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}

		var selectionQty = 4;

		var data = getR4z4(bet, 4);

		if (position.split(",").length < 4) {
			return responseFormat('error', '輸入位數的數量有錯誤');
		}

		var typeData = transfer2Group(position);

		var positionData = transfer2Group(position);
		var keysCombined = combine(positionData, selectionQty);

		var responseArray = [];

		for (var i = 0, l = data.length; i < l; i++) {
			for (var j = 0, len = keysCombined.length; j < len; j++) {
				var sum = getSum(convertPosition(keysCombined[j]));
				var title = type + sum;

				var value = data[i];
				var dataObj = getDataObj(title, value, info);
				responseArray.push(dataObj);
			}
		}
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * @description 定位膽 (一個蘿蔔一個坑) => 時時彩一星定位膽、3D一星定位膽、11選5定位膽、PK10全部名次
 * @param {string}
 *                bet 投注列表：'1,2|2|7,8||4,5'
 * @returns {array}
 */
function ctt_f3ddwdlx(bet, type, position, info) { // dwd

	var typePrefix = type;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var responseArray = [];

		for (var i = 0, l = betData.length; i < l; i++) {

			var data = betData[i];
			for (var j = 0, len = data.length; j < len; j++) {

				var value = data[j];
				var type = typePrefix + (i + 1);
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);

				delete value, type, dataObj;
				value = undefined;
				type = undefined;
				dataObj = undefined;
			}
			delete data;
			data = undefined;
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();

				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	} finally {
		delete typePrefix, betData;
		typePrefix = undefined;
		betData = undefined;
	}
}

function ctt_sscdwdlx(bet, type, position, info) { // dwd

	var typePrefix = type;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var positionData = transfer2Group(position);

		var responseArray = [];

		for (var i = 0, l = betData.length; i < l; i++) {

			var data = betData[i];
			for (var j = 0, len = data.length; j < len; j++) {

				var value = data[j];
				var type = typePrefix + convertPosition(positionData[i]);
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);

				delete value, type, dataObj;
				value = undefined;
				type = undefined;
				dataObj = undefined;
			}
			delete data;
			data = undefined;
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();

				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	} finally {
		delete typePrefix, betData, positionData;
		typePrefix = undefined;
		betData = undefined;
		positionData = undefined;
	}
}

// [時時彩] 一星定位膽
// [3D] 一星定位膽
// [11選5] 定位膽
// [PK10] 全部名次

// 任二龍虎和
function ctt_f3dr2lh(bet, type, position, posIndex, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var typePrefix = type;
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {
			var betData = transfer2Group(bet);
			if (betData.length === 0) {
				return responseFormat('error', '選號數量不足');
			}
			var response = [];
			betData.forEach(function(value) {
				var type = "";
				if (value === '和') {
					type = typePrefix + 'h' + posIndex;
				} else {
					type = typePrefix + posIndex;
				}

				if (value == '龍') {
					value = 'l';
				} else if (value == '虎') {
					value = 'h';
				} else if (value == '和') {
					value = '1';
				}
				var dataObj = getDataObj(type, value, info);
				response.push(dataObj);
			});

			return response;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_sscr2lh(bet, type, position, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var typePrefix = type;
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {

			var betData = transfer2Group(bet);

			if (betData.length === 0) {
				return responseFormat('error', '選號數量不足');
			}
			var response = [];
			betData.forEach(function(value) {
				var type = "";
				if (value === '和') {
					type = typePrefix + '_t_' + getSum(convertPosition(position));
				} else {
					type = typePrefix + '_lh_' + getSum(convertPosition(position));
				}

				var dataObj = getDataObj(type, value, info);
				response.push(dataObj);
			});

			return response;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// ====================
// 11選5
// ====================

/**
 * 
 * @description 二三直選複式
 * @param {string}
 *                bet 投注列表：'01,04|04,07|02,05,07'
 * @param {number}
 *                stars 共選Ｎ個號碼（Ｎ星）
 * @returns {array}
 */

function gd11x5Q2Q3(bet, stars, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {
			if (bet.split('|').filter(function(v) {
				return v != ''
			}).length !== stars) {
				return responseFormat('error', '輸入內容的數量有錯誤');
			}
			var responseArray = [];
			var data = descarteCalculateRemoveDuplicateNumber(bet);
			data.forEach(function(value) {
				var dataObj = getDataObj(type, value, info);
				responseArray.push(dataObj);
			});

			return responseArray;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// [11選5]
// 前二直選複式 '01,04,05|04,05,07', 2
// 前三直選複式 '01,04,05|04,05,07|06', 3

// [PK10]
// 前後二複式 '1,4,5|4,5,7', 2
// 前後三複式 '1,4,5|4,5,7|6', 3

/**
 * 
 * @description 11選5 任選膽拖
 * @param {string}
 *                bet 投注列表：'01,04|04,07|02,05,07'
 * @param {number}
 *                selectionQty 任選選Ｎ個號碼
 * @returns {array}
 */

function ctt_dt(bet, selectionQty, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var dNumbers = betData[0];
		var tNumbers = betData[1];
		var dNumbersQty = dNumbers.length;
		var dNumbersMaxQty = selectionQty - 1;
		var lastTNumbersQty = selectionQty - dNumbersQty;
		var response = [];

		if (dNumbers.length < 1) {
			return responseFormat('error', '沒有膽號');
		}

		if (dNumbers.length > dNumbersMaxQty) {
			return responseFormat('error', '膽號選項過多');
		}

		if (tNumbers.length < 1) {
			return responseFormat('error', '沒有拖號');
		}

		if ((dNumbers.length + tNumbers.length) < selectionQty) {
			return responseFormat('error', '號碼數量太少');
		}

		for (var i = 0; i < dNumbers.length; i++) {
			for (var j = 0; j < tNumbers.length; j++) {
				if (dNumbers[i] == tNumbers[j]) {
					return responseFormat('error', '號碼重複');
				}
			}
		}
		var combinedData = newCombine(tNumbers, lastTNumbersQty);

		for (var i = 0, l = combinedData.length; i < l; i++) {
			var reCombineData = [];
			reCombineData.push(dNumbers.join(','));
			reCombineData.push(combinedData[i]);
			reCombineData = reCombineData.join(',');
			reCombineData = reCombineData.split(",");
			var sortList = [];
			for (var j = 0; j < reCombineData.length; j++) {
				sortList.push(parseInt(reCombineData[j]));
			}
			sortList.sort(function(a, b) {
				return a - b;
			});
			reCombineData = [];
			reCombineData.push(sortList.join('|'));
			response.push(reCombineData);
		}
		var responseArray = [];
		response.forEach(function(value) {
			var dataObj = getDataObj(type, "" + value, info);
			responseArray.push(dataObj);
		});
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_dt3x2x(bet, selectionQty, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		var dNumbers = betData[0];
		var tNumbers = betData[1];

		var dNumbersQty = dNumbers.length;
		var dNumbersMaxQty = selectionQty - 1;
		var lastTNumbersQty = selectionQty - dNumbersQty;
		var response = [];

		if (dNumbers.length < 1) {
			return responseFormat('error', '沒有膽號');
		}

		if (dNumbers.length > dNumbersMaxQty) {
			return responseFormat('error', '膽號選項過多');
		}

		if (tNumbers.length < 1) {
			return responseFormat('error', '沒有拖號');
		}

		if ((dNumbers.length + tNumbers.length) < selectionQty) {
			return responseFormat('error', '號碼數量太少');
		}

		for (var i = 0; i < dNumbers.length; i++) {
			for (var j = 0; j < tNumbers.length; j++) {
				if (dNumbers[i] == tNumbers[j]) {
					return responseFormat('error', '號碼重複');
				}
			}
		}
		var combinedData = newCombine(tNumbers, lastTNumbersQty);

		for (var i = 0, l = combinedData.length; i < l; i++) {
			var reCombineData = [];
			reCombineData.push(dNumbers.join(','));
			reCombineData.push(combinedData[i]);
			reCombineData = reCombineData.join(',');
			reCombineData = reCombineData.split(",");
			var sortList = [];
			for (var j = 0; j < reCombineData.length; j++) {
				sortList.push(parseInt(reCombineData[j]));
			}
			sortList.sort(function(a, b) {
				return a - b;
			});
			reCombineData = [];
			reCombineData.push(sortList.join(','));
			response.push(reCombineData);
		}
		var responseArray = [];
		response.forEach(function(value) {
			var dataObj = getDataObj(type, "" + value, info);
			responseArray.push(dataObj);
		});
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// ====================
// K3
// ====================

function directTransfer2OutputK3Czw(bet, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var responseArray = [];
		var betData = bet.split(",");
		betData.forEach(function(value) {
			var dataObj = getDataObj(type, value, info);
			responseArray.push(dataObj);
		});
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function directTransfer2OutputK3Ds(bet, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var responseArray = [];
		var betData = bet.split(",");
		betData.forEach(function(value) {
			var realType = type;
			if (value == '1') {
				realType = realType + 'd';
			} else if (value == '2') {
				realType = realType + 's';
			}
			var dataObj = getDataObj(realType, '1', info);
			responseArray.push(dataObj);
		});
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function directTransfer2OutputK3Bx(bet, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var responseArray = [];
		var betData = bet.split(",");
		betData.forEach(function(value) {
			var realType = type;
			if (value == '1') {
				realType = realType + 'd';
			} else if (value == '2') {
				realType = realType + 'x';
			}
			var dataObj = getDataObj(realType, '1', info);
			responseArray.push(dataObj);
		});
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function directTransfer2OutputK3Hz(bet, stars, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var responseArray = [];
		var betData = bet.split(",");
		betData.forEach(function(value) {
			var realType = type;
			if (value == '3' || value == '18') {
				realType = realType + 1;
			} else if (value == '4' || value == '17') {
				realType = realType + 2;
			} else if (value == '5' || value == '16') {
				realType = realType + 3;
			} else if (value == '6' || value == '15') {
				realType = realType + 4;
			} else if (value == '7' || value == '14') {
				realType = realType + 5;
			} else if (value == '8' || value == '13') {
				realType = realType + 6;
			} else if (value == '9' || value == '12') {
				realType = realType + 7;
			} else if (value == '10' || value == '11') {
				realType = realType + 8;
			}
			var dataObj = getDataObj(realType, value, info);
			responseArray.push(dataObj);
		});
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * 
 * @description 三同號通選
 * @returns {array}
 */

function directTransfer2OutputK22Fx(bet, stars, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var responseArray = [];
		var betData = bet.split(",");
		betData.forEach(function(value) {
			var dataObj = getDataObj(type, value, info);
			responseArray.push(dataObj);
		});
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function directTransfer2OutputK33Dx(bet, stars, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var responseArray = [];
		var betData = bet.split(",");
		betData.forEach(function(value) {
			var dataObj = getDataObj(type, value, info);
			responseArray.push(dataObj);
		});
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function directTransfer2OutputK33Tx(bet, stars, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var responseArray = [];
		var betData = bet.split(",");
		betData.forEach(function(value) {
			var dataObj = getDataObj(type, '1', info);
			responseArray.push(dataObj);
		});
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function thtx() {
	try {
		var THTX = [ '111,222,333,444,555,666' ];

		return THTX;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * 
 * @description 三連號通選
 * @returns {array}
 */

function lhtx(bet, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var responseArray = [];
		var dataObj = getDataObj(type, bet, info);
		responseArray.push(dataObj);
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * 
 * @description 二同號單選
 * @param {string}
 *                bet 投注列表：'11,22|2,3'。
 * @returns {array}
 */

function k32dxCalc(bet, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);
		// [['11','22'], ['2','3']]
		var sameNumbers = betData[0];
		var differentNumbers = betData[1];

		var response = [];
		for (var i = 0, l = sameNumbers.length; i < l; i++) {
			for (var j = 0, len = differentNumbers.length; j < len; j++) {
				if (sameNumbers[i].indexOf(differentNumbers[j]) >= 0) {
					continue;
				}
				var data = [];
				data.push(sameNumbers[i]);
				data.push(differentNumbers[j]);
				var dataObj = getDataObj(type, data.join('|'), info);
				response.push(dataObj);
			}
		}
		return response;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// ====================
// PK10
// ====================

/**
 * @description 猜名次 => 跟時時彩一星定位膽一樣，差在常數陣列的內容不同
 * @param {string}
 *                bet 投注列表：'1,2|2|7,8||4,5|||5|7|5,8'
 * @returns {array}
 */

function pk10dwd(bet, type, info) { // dwd

	var typePrefix = type;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		var responseArray = [];

		for (var i = 0, l = betData.length; i < l; i++) {

			var data = betData[i];
			for (var j = 0, len = data.length; j < len; j++) {

				var value = data[j];
				var type = typePrefix + (i + 1);
				var dataObj = getDataObj(type, '' + value, info);

				responseArray.push(dataObj);

				delete value, type, dataObj;
				value = undefined;
				type = undefined;
				dataObj = undefined;
			}
			delete data;
			data = undefined;
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();

				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	} finally {
		delete typePrefix, betData;
		typePrefix = undefined;
		betData = undefined;
	}
}

function directTransfer2OutputGy(bet, stars, type, info) {
	try {
		var responseArray = [];
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(",");
		betData.forEach(function(value) {
			var realType = type;
			if (value == '3' || value == '4' || value == '18' || value == '19') {
				realType = realType + 1;
			} else if (value == '5' || value == '6' || value == '16' || value == '17') {
				realType = realType + 2;
			} else if (value == '7' || value == '8' || value == '14' || value == '15') {
				realType = realType + 3;
			} else if (value == '9' || value == '10' || value == '12' || value == '13') {
				realType = realType + 4;
			} else if (value == '11') {
				realType = realType + 5;
			}
			var dataObj = getDataObj(realType, value, info);
			responseArray.push(dataObj);
		});
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function directTransfer2OutputGyBXDS(bet, stars, type, info) {
	try {
		var responseArray = [];
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(",");
		betData.forEach(function(value) {
			var realType = type + value;
			var dataObj = getDataObj(realType, '1', info);
			responseArray.push(dataObj);
		});
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * @description 龍虎 => 跟時時彩一星定位膽一樣，差在常數陣列的內容不同
 * @param {string}
 *                bet 投注列表：'龍,虎|龍,虎|龍|虎|龍'
 * @returns {array}
 */
function pk10lhcal(bet, type, info) { // lh

	var typePrefix = type;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		var responseArray = [];

		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i];
			for (var j = 0, len = data.length; j < len; j++) {

				var value = data[j];
				if (i == 0) {
					type = typePrefix + "1_10";
				} else if (i == 1) {
					type = typePrefix + "2_9";
				} else if (i == 2) {
					type = typePrefix + "3_8";
				} else if (i == 3) {
					type = typePrefix + "4_7";
				} else if (i == 4) {
					type = typePrefix + "5_6";
				}
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);

				delete value, type, dataObj;
				value = undefined;
				type = undefined;
				dataObj = undefined;
			}
			delete data;
			data = undefined;
		}
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();

				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	} finally {
		delete typePrefix, betData;
		typePrefix = undefined;
		betData = undefined;
	}
}

// ====================
// 單式輸入驗證輸入
// ====================

// 確認輸入的內容，如果都沒錯誤才可以允許submit
function ctt_checkInput() {
	var content = document.querySelector('#txarea').value;
	var characterArray = content.split('');

	characterArray.forEach(function(data) {

		var rule = /[&\\\*^%$#@\-'"!/]/;
		if (rule.test(data)) { // 如果找到不合法的字元
			var index = content.indexOf(data); // 找到字串的位置
			console.error('Error in ' + index);
		}
	});
}

// ====================
// 單式輸入
// ====================

/**
 * @description 直選 單式輸入轉換
 * @param {string}
 *                bet
 * @returns {string}
 */

function ctt_dzInput(bet) {
	// 換行 \n 、空格、逗號、分號、樹杠 |

	if(bet.trim().length == 0){
		return responseFormat('error', '輸入的組合內容有誤');
	}
	var betData = bet.split(/[\n\s,;:|]/);

	var reFormatedData = [];

	for (var i = 0, l = betData.length; i < l; i++) {
		var data = betData[i]; // 12379 => 12375 => 12789

		var rule = /\d+/;

		// 先檢查是否為數字組成的字串
		if (rule.test(data)) {
			// 符合條件
			var group = data.split('');

			// [1,2,3,7,9] => [1,2,3,7,5] => [1,2,7,8,9]
			reFormatedData.push(group.join(','));

		} else {
			// 不符合條件
			return responseFormat('error', '輸入的組合內容有誤');
		}
	}

	var responseArray = [];
	reFormatedData.forEach(function(value) {
		var type = typePrefix + getSum(convertPosition(position));
		var dataObj = getDataObj(type, value, info)
		responseArray.push(dataObj);
	});

	return responseArray;

}

// 二星直選單式 '24|35'
// 三星直選 '245|345|456'
// 四星直選單式 '1234|1237|1278|5678'
// 五星直選單式 '12379|12375|12789|56789'

// 任二單式
function ctt_sscr2ds(bet, type, position, info) {
	// 換行 \n 、空格、逗號、分號、樹杠 |
	var typePrefix = type;
	var selectionQty = 2;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(/[\n\s,;:|]/);
		var responseArray = [];

		if (position.split(",").length < selectionQty) {
			return responseFormat('error', '位數數量過少');
		}

		var keys = transfer2Group(position);

		betData = deleteArrRepeatVal(betData);
		var dataArray = [];
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 12379 => 12375 => 12789

			var rule = /^\d{2}$/;

			// 先檢查是否為數字組成的字串
			if (rule.test(data)) {
				var group = data.split('');
				dataArray.push(group.join("|"));

			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}

		var keysCombined = combine(keys, selectionQty);
		dataArray = deleteArrRepeatVal(dataArray);

		dataArray.forEach(function(value) {
			info["betData"] += value.split("|").join("") + ",";
			for (var j = 0, l = keysCombined.length; j < l; j++) {
				var currentPosition = keysCombined[j];
				var type = typePrefix + getSum(convertPosition(currentPosition.join(",")));
				var dataObj = getDataObj(type, value, info)
				responseArray.push(dataObj);
			}
		});

		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 三星直選單式
function ctt_sscr3ds(bet, type, position, info) {
	// 換行 \n 、空格、逗號、分號、樹杠 |

	var typePrefix = type;
	var selectionQty = 3;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(/[\n\s,;:|]/);
		var responseArray = [];

		if (position.split(",").length < selectionQty) {
			return responseFormat('error', '位數數量過少');
		}

		var keys = transfer2Group(position);

		betData = deleteArrRepeatVal(betData);
		var dataArray = [];
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 12379 => 12375 => 12789

			var rule = /^\d{3}$/;

			// 先檢查是否為數字組成的字串
			if (rule.test(data)) {
				var group = data.split('');
				dataArray.push(group.join("|"));

			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}

		var keysCombined = combine(keys, selectionQty);
		dataArray = deleteArrRepeatVal(dataArray);
		dataArray.forEach(function(value) {
			info["betData"] += value.split("|").join("") + ",";
			for (var j = 0, l = keysCombined.length; j < l; j++) {
				var currentPosition = keysCombined[j];
				var type = typePrefix + getSum(convertPosition(currentPosition.join(",")));
				var dataObj = getDataObj(type, value, info)
				responseArray.push(dataObj);
			}
		});

		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 四星直選單式
function ctt_sscr4ds(bet, type, position, info) {
	// 換行 \n 、空格、逗號、分號、樹杠 |
	var typePrefix = type;
	var selectionQty = 4;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(/[\n\s,;:|]/);
		var responseArray = [];

		if (position.split(",").length < selectionQty) {
			return responseFormat('error', '位數數量過少');
		}

		var keys = transfer2Group(position);

		betData = deleteArrRepeatVal(betData);
		var dataArray = [];
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 12379 => 12375 => 12789

			var rule = /^\d{4}$/;

			// 先檢查是否為數字組成的字串
			if (rule.test(data)) {
				var group = data.split('');
				dataArray.push(group.join("|"));

			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}

		var keysCombined = combine(keys, selectionQty);
		dataArray = deleteArrRepeatVal(dataArray);
		dataArray.forEach(function(value) {
			info["betData"] += value.split("|").join("") + ",";
			for (var j = 0, l = keysCombined.length; j < l; j++) {
				var currentPosition = keysCombined[j];
				var type = typePrefix + getSum(convertPosition(currentPosition.join(",")));
				var dataObj = getDataObj(type, value, info)
				responseArray.push(dataObj);
			}
		});

		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 五星直選單式
function ctt_sscr5ds(bet, type, position, info) {
	// 換行 \n 、空格、逗號、分號、樹杠 |
	var typePrefix = type;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(/[\n\s,;:|]/);

		var reFormatedData = [];
		betData = deleteArrRepeatVal(betData);
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 12379 => 12375 => 12789

			var rule = /^\d{5}$/;
			// 先檢查是否為數字組成的字串
			if (rule.test(data)) {
				info["betData"] += data + ",";
				// 符合條件
				var group = data.split('');
				// [1,2,3,7,9] => [1,2,3,7,5] => [1,2,7,8,9]
				reFormatedData.push(group.join('|'));

			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}
		var responseArray = [];
		reFormatedData.forEach(function(value) {
			var dataObj = getDataObj(type, value, info)
			responseArray.push(dataObj);
		});

		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 任二組選單式
function ctt_sscr2zds(bet, type, position, info) {
	// 換行 \n 、空格、逗號、分號、樹杠 |
	var typePrefix = type;
	var selectionQty = 2;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(/[\n\s,;:|]/);

		var responseArray = [];

		if (position.split(",").length < selectionQty) {
			return responseFormat('error', '位數數量過少');
		}
		betData = deleteArrRepeatVal(betData);
		var keys = transfer2Group(position);
		var dataArray = [];
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 12379 => 12375 => 12789
			var rule = /^\d{2}$/;

			// 先檢查是否為數字組成的字串
			if (rule.test(data) && checkArrIsRepeat(data.split("")) == false) {
				// 符合條件
				var group = data.split('');
				group = group.sort(function(a, b) {
					return a - b;
				});

				dataArray.push(group.join("|"));

			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}
		dataArray = deleteArrRepeatVal(dataArray);
		var keysCombined = combine(keys, selectionQty);

		dataArray.forEach(function(value) {
			info["betData"] += value.split("|").join("") + ",";
			for (var j = 0, l = keysCombined.length; j < l; j++) {
				var c = keysCombined[j];
				var type = typePrefix + getSum(convertPosition(c.join(",")));
				var dataObj = getDataObj(type, value, info)
				responseArray.push(dataObj);
			}
		});

		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 混合單式
function ctt_sscr3hhds(bet, type, position, info) {
	// 換行 \n 、空格、逗號、分號、樹杠 |

	var typePrefix = type;
	var selectionQty = 3;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(/[\n\s,;:|]/);

		var responseArray = [];

		if (position.split(",").length < selectionQty) {
			return responseFormat('error', '位數數量過少');
		}

		var keys = transfer2Group(position);
		betData = deleteArrRepeatVal(betData);
		var dataArray = [];
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 112 => 113 => 122 => 123

			var rule = /^\d{3}$/;

			if (rule.test(data)) {
				var group = data.split("");
				group = group.sort(function(a, b) {
					return a - b;
				});
				if (group[0] == group[1] && group[0] == group[2] && group[1] == group[2]) {
					responseFormat('error', '輸入的組合內容有誤');
					continue;
				} else if (checkArrIsRepeat(group)) {
					var repeatVal = group[1];
					group = deleteArrRepeatVal(group);
					if (group.length != 2) {
						responseFormat('error', '輸入的組合內容有誤');
						continue;
					}
					if (group[1] == repeatVal) {
						group[1] = group[0];
						group[0] = repeatVal;
					}
					dataArray.push(group.join("|"));
				} else {
					dataArray.push(group.join(","));
				}
			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}
		var keysCombined = newCombine(keys, selectionQty);
		dataArray = deleteArrRepeatVal(dataArray);
		var tmpInfoZ3 = JSON.parse(JSON.stringify(info));
		for ( var key in tmpInfoZ3["baselineObj"]) {
			if (typeof tmpInfoZ3["baselineObj"][key]["baseline"] != "undefined") {
				tmpInfoZ3["baselineObj"][key]["baseline"] = "" + tmpInfoZ3["baselineObj"][key]["baseline"] * 2;
			}
		}
		dataArray.forEach(function(value) {
			if (value.indexOf("|") >= 0) {
				var val = value.split("|");
			} else if (value.indexOf(",") >= 0) {
				var val = value.split(",");
			}
			if (val.length == 2) {
				info["betData"] += val[0] + val[0] + val[1] + ",";
			} else if (val.length == 3) {
				info["betData"] += val.join("") + ",";
			}
			var type = "";
			for (var j = 0, l = keysCombined.length; j < l; j++) {
				var dataObj;
				if (val.length == 2) {
					type = typePrefix + 'r3z3ds_' + getSum(convertPosition(keysCombined[j]));
					dataObj = getDataObj(type, value, tmpInfoZ3);
				} else if (val.length == 3) {
					type = typePrefix + 'r3z6_' + getSum(convertPosition(keysCombined[j]));
					dataObj = getDataObj(type, value, info);
				}

				responseArray.push(dataObj);

			}

		});

		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * @description 三星混合組選（組三）單式輸入轉換
 * @param {string}
 *                bet
 * @returns {string}
 */
function ctt_sscr3z3ds(bet, type, position, info) {
	// 換行 \n 、空格、逗號、分號、樹杠 |

	var typePrefix = type;
	var selectionQty = 3;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(/[\n\s,;:|]/);

		var responseArray = [];

		if (position.split(",").length < selectionQty) {
			return responseFormat('error', '位數數量過少');
		}

		var keys = transfer2Group(position);
		betData = deleteArrRepeatVal(betData);
		var dataArray = [];
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 112 => 113 => 122 => 123

			var rule = /^\d{3}$/;
			// 先檢查是否為數字組成的字串
			if (rule.test(data)) {
				var group = data.split('');
				group = group.sort(function(a, b) {
					return a - b;
				});

				if (group[0] == group[1] && group[0] == group[2] && group[1] == group[2]) {
					responseFormat('error', '輸入的組合內容有誤');
					continue;
				} else if (checkArrIsRepeat(group) == false) {
					responseFormat('error', '輸入的組合內容有誤');
					continue;
				} else {
					var repeatVal = group[1];
					group = deleteArrRepeatVal(group);
					if (group.length != 2) {
						responseFormat('error', '輸入的組合內容有誤');
						continue;
					}
					if (group[1] == repeatVal) {
						group[1] = group[0];
						group[0] = repeatVal;
					}
					dataArray.push(group.join('|'));
				}
			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}
		var keysCombined = newCombine(keys, selectionQty);

		dataArray = deleteArrRepeatVal(dataArray);
		dataArray.forEach(function(value) {
			var val = value.split("|");

			info["betData"] += val[0] + val[0] + val[1] + ",";
			for (var j = 0, l = keysCombined.length; j < l; j++) {
				var type = typePrefix + getSum(convertPosition(keysCombined[j]));
				var dataObj = getDataObj(type, value, info)
				responseArray.push(dataObj);
			}
		});

		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * @description 三星混合組選（組六）單式輸入轉換
 * @param {string}
 *                bet
 * @returns {string}
 */

function ctt_sscr3z6ds(bet, type, position, info) {
	// 換行 \n 、空格、逗號、分號、樹杠 |

	var typePrefix = type;
	var selectionQty = 3;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(/[\n\s,;:|]/);

		var responseArray = [];

		if (position.split(",").length < selectionQty) {
			return responseFormat('error', '位數數量過少');
		}

		var keys = transfer2Group(position);
		var dataArray = [];
		betData = deleteArrRepeatVal(betData);
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 112 => 113 => 122 => 123

			var rule = /^\d{3}$/;
			// 先檢查是否為數字組成的字串
			if (rule.test(data) && checkArrIsRepeat(data.split("")) == false) {
				var group = data.split('');
				group = group.sort(function(a, b) {
					return a - b;
				});
				dataArray.push(group.join(","));
			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}
		var keysCombined = newCombine(keys, selectionQty);

		dataArray = deleteArrRepeatVal(dataArray);

		dataArray.forEach(function(value) {
			info["betData"] += value.split(",").join("") + ",";
			for (var j = 0, l = keysCombined.length; j < l; j++) {
				var type = typePrefix + getSum(convertPosition(keysCombined[j]));
				var dataObj = getDataObj(type, value, info)
				responseArray.push(dataObj);
			}
		});

		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

/**
 * @description 11選5 單式輸入轉換
 * @param {string}
 *                bet
 * @returns {string}
 */
function ctt_11x5q3ds(bet, type, info) {
	// 換行 \n 、逗號、分號、樹杠 | (沒有空格)
	if(bet.trim().length == 0){
		return responseFormat('error', '輸入的組合內容有誤');
	}
	var betData = bet.split(/[\n,;:|]/);

	var reFormatedData = [];
	info["betData"] = "";
	betData = deleteArrRepeatVal(betData);
	var responseArray = [];
	for (var i = 0, l = betData.length; i < l; i++) {
		var data = betData[i].trim();

		var rule = /^[0-1]{0,1}[0-9]{1}[\s]{1,}[0-1]{0,1}[0-9]{1}[\s]{1,}[0-1]{0,1}[0-9]{1}$/;
		// 先檢查是否為數字組成的字串
		if (rule.test(data)) {
			// 符合條件
			var group = deleteArrayNullVal(data.split(' ')); // 11選5是以空格區隔每
			for (var s = 0; s < group.length; s++) {
				if (parseInt(group[s]) > 0 && parseInt(group[s]) < 12) {
					group[s] = parseInt(group[s]);
				} else {
					responseFormat('error', '輸入的組合內容有誤');
					continue;
				}
			}
			if (checkArrIsRepeat(group) || group.length != 3) {
				responseFormat('error', '輸入的組合內容重複');
				continue;
			}
			info["betData"] += group.join(" ") + ",";
			var dataObj = getDataObj(type, group.join("|"), info);
			responseArray.push(dataObj);

		} else {
			responseFormat('error', '輸入的組合內容有誤');
			continue;
			// 不符合條件
			// return responseFormat('error', '輸入的組合內容有誤');
		}
	}

	info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
	return responseArray;

}

function ctt_11x5q3zds(bet, type, info) {
	// 換行 \n 、逗號、分號、樹杠 | (沒有空格)
	if(bet.trim().length == 0){
		return responseFormat('error', '輸入的組合內容有誤');
	}
	var betData = bet.split(/[\n,;:|]/);

	var reFormatedData = [];
	var stars = 3;
	betData = deleteArrRepeatVal(betData);
	var responseArray = [];
	info["betData"] = "";
	for (var i = 0, l = betData.length; i < l; i++) {
		var data = betData[i].trim();

		var rule = /^[0-1]{0,1}[0-9]{1}[\s]{1,}[0-1]{0,1}[0-9]{1}[\s]{1,}[0-1]{0,1}[0-9]{1}$/;

		// 先檢查是否為數字組成的字串
		if (rule.test(data)) {
			// 符合條件

			var group = deleteArrayNullVal(data.split(' ')); // 11選5是以空格區隔每

			for (var s = 0; s < group.length; s++) {
				if (parseInt(group[s]) > 0 && parseInt(group[s]) < 12) {
					group[s] = parseInt(group[s]);
				} else {
					responseFormat('error', '輸入的組合內容有誤');
					continue;
				}
			}

			if (checkArrIsRepeat(group) || group.length != 3) {
				responseFormat('error', '輸入的組合內容重複');
				continue;
			}

			info["betData"] += group.join(" ") + ",";
			var dataObj = getDataObj(type, group.join(","), info);
			responseArray.push(dataObj);

			// var arr = newCombine(group, stars);

			// arr.forEach(function(value){
			// var dataObj = getDataObj(type, value, info);
			// responseArray.push(dataObj);
			// });

		} else {
			responseFormat('error', '輸入的組合內容有誤');
			continue;
		}
	}

	info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
	return responseArray;

}

function ctt_11x5q2ds(bet, type, info) {
	// 換行 \n 、逗號、分號、樹杠 | (沒有空格)
	if(bet.trim().length == 0){
		return responseFormat('error', '輸入的組合內容有誤');
	}
	var betData = bet.split(/[\n,;:|]/);

	var reFormatedData = [];
	info["betData"] = "";
	betData = deleteArrRepeatVal(betData);
	var responseArray = [];
	for (var i = 0, l = betData.length; i < l; i++) {
		var data = betData[i].trim();

		var rule = /^[0-1]{0,1}[0-9]{1}[\s]{1,}[0-1]{0,1}[0-9]{1}$/;
		// 先檢查是否為數字組成的字串
		if (rule.test(data)) {
			// 符合條件
			var group = deleteArrayNullVal(data.split(' ')); // 11選5是以空格區隔每
			for (var s = 0; s < group.length; s++) {
				if (parseInt(group[s]) > 0 && parseInt(group[s]) < 12) {
					group[s] = parseInt(group[s]);
				} else {
					responseFormat('error', '輸入的組合內容有誤');
					continue;
				}
			}

			if (checkArrIsRepeat(group) || group.length != 2) {
				responseFormat('error', '輸入的組合內容重複');
				continue;
			}

			info["betData"] += group.join(" ") + ",";
			var dataObj = getDataObj(type, group.join("|"), info);
			responseArray.push(dataObj);

		} else {
			responseFormat('error', '輸入的組合內容有誤');
			continue;
		}
	}

	info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
	return responseArray;

}

function ctt_11x5q2zds(bet, type, info) {
	// 換行 \n 、逗號、分號、樹杠 | (沒有空格)

	var stars = 2;
	if(bet.trim().length == 0){
		return responseFormat('error', '輸入的組合內容有誤');
	}
	var betData = bet.split(/[\n,;:|]/);

	var reFormatedData = [];

	betData = deleteArrRepeatVal(betData);
	info["betData"] = "";
	var responseArray = [];
	for (var i = 0, l = betData.length; i < l; i++) {
		var data = betData[i].trim();

		var rule = /^[0-1]{0,1}[0-9]{1}[\s]{1,}[0-1]{0,1}[0-9]{1}$/;

		// 先檢查是否為數字組成的字串
		if (rule.test(data)) {
			// 符合條件
			var group = deleteArrayNullVal(data.split(' ')); // 11選5是以空格區隔每
			for (var s = 0; s < group.length; s++) {
				if (parseInt(group[s]) > 0 && parseInt(group[s]) < 12) {
					group[s] = parseInt(group[s]);
				} else {
					responseFormat('error', '輸入的組合內容有誤');
					continue;
				}
			}
			if (checkArrIsRepeat(group) || group.length != 2) {
				responseFormat('error', '輸入的組合內容重複');
				continue;
			}

			info["betData"] += group.join(" ") + ",";
			var dataObj = getDataObj(type, group.join(","), info);
			responseArray.push(dataObj);

			// var arr = newCombine(group, stars);
			// arr.forEach(function(value){
			// var dataObj = getDataObj(type, value, info);
			// responseArray.push(dataObj);
			// });

		} else {
			responseFormat('error', '輸入的組合內容有誤');
			continue;
		}
	}

	info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
	return responseArray;

}

function ctt_11x5rx(bet, stat, type, info) {
	// 換行 \n 、逗號、分號、樹杠 | (沒有空格)

	var stars = 2;
	if(bet.trim().length == 0){
		return responseFormat('error', '輸入的組合內容有誤');
	}
	var betData = bet.split(/[\n,;:|]/);

	var reFormatedData = [];

	betData = deleteArrRepeatVal(betData);

	var dataArray = [];
	var responseArray = [];
	info["betData"] = "";
	for (var i = 0, l = betData.length; i < l; i++) {
		var data = betData[i].split(" ");
		var numArr = [];
		var rule2 = /^[0-1]{0,1}[0-9]{1}$/
		for (var j = 0; j < data.length; j++) {
			if (rule2.test(data[j])) {
				if (parseInt(data[j]) > 0 && parseInt(data[j]) < 12) {
					data[j] = parseInt(data[j]);
					numArr.push(parseInt(data[j]));
				} else {
					responseFormat('error', '輸入的組合內容有誤');
					continue;
				}
			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}
		numArr = numArr.sort(function(a, b) {
			return a - b;
		});
		numArr = deleteArrRepeatVal(numArr);
		if (numArr.length != stat) {
			responseFormat('error', '輸入的組合內容重複');
			continue;
		}
		info["betData"] += numArr.join(" ") + ","
		dataArray.push(numArr.join("|"));
	}
	dataArray = deleteArrRepeatVal(dataArray);

	dataArray.forEach(function(value) {

		var dataObj = getDataObj(type, value, info);
		responseArray.push(dataObj);
	});

	info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
	return responseArray;

}

// 任選一中一 '01|02|06'
// 任選二中二 '01 02|02 03|06 07'
// 任選三中三 '01 02 03|02 03 04'
// 任選四中四 '01 02 03 04|03 04 05 07'
// 任選五中五 '01 02 03 04 05|01 03 04 05 07'
// 任選六中五 '01 02 03 04 05 06|01 03 04 05 06 07'
// 任選七中五 '01 02 03 04 05 06 09|01 03 04 05 06 07 09'
// 任選八中五 '01 02 03 04 05 06 09 11|01 03 04 05 06 07 09 11'

/**
 * @description 快三 二同號 單式輸入轉換
 * @param {string}
 *                bet
 * @returns {string}
 */

function dzInputK3(bet) {
	// 換行 \n 、空格、逗號、分號、樹杠 |
	if(bet.trim().length == 0){
		return responseFormat('error', '輸入的組合內容有誤');
	}
	var betData = bet.split(/[\n\s,;:|]/);

	var reFormatedData = [];

	for (var i = 0, l = betData.length; i < l; i++) {
		var data = betData[i];

		var rule = /[1-6]{3}/;
		// 先檢查是否為數字組成的字串
		if (rule.test(data)) {
			// 符合條件
			var group = data.split('');
			reFormatedData.push(group.join(','));

		} else {
			// 不符合條件
			return responseFormat('error', '輸入的組合內容有誤');
		}
	}
	var responseArray = [];
	reFormatedData.forEach(function(value) {
		var type = typePrefix + getSum(convertPosition(position));
		var dataObj = getDataObj(type, value, info)
		responseArray.push(dataObj);
	});

	return responseArray;

}

// 五碼趣味三星
function ctt_ssc5mqw3x(bet, type, info) {
	try {
		var limitQty = 5;
		var response = [];
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {
			if (bet.split('|').filter(function(v) {
				return v != ''
			}).length !== limitQty) {
				return responseFormat('error', '輸入內容的數量有錯誤');
			}
			var noOfSubOrders = subOrderCalculateBeforeDescarte(bet);
			if (noOfSubOrders < 0) {
				var betData = descarteCalculate(bet);
				for (var j = 0; j < betData.length; j++) {
					var data = betData[j].split("|");
					for (var i = (data.length - 1); i >= 0; i--) {
						if (data[i].length === 0) {
							data.splice(i, 1);
						}
					}
					if (data.length !== limitQty) {
						return responseFormat('error', '輸入內容的數量有錯誤');
					}
					var dataObj = getDataObj(type, betData[j], info);
					response.push(dataObj);
				}
			} else {
				var dataObj = getDataObj(type, '1', info);
				dataObj.amount = dataObj.amount * noOfSubOrders;
				dataObj.noOfBet = dataObj.noOfBet * noOfSubOrders;
				response.push(dataObj);
			}
		}
		return response;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 四碼趣味三星
function ctt_ssc4mqw3x(bet, type, info) {
	try {
		var limitQty = 4;
		var response = [];
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {
			if (bet.split('|').filter(function(v) {
				return v != ''
			}).length !== limitQty) {
				return responseFormat('error', '輸入內容的數量有錯誤');
			}
			var noOfSubOrders = subOrderCalculateBeforeDescarte(bet);
			if (noOfSubOrders < 0) {
				var betData = descarteCalculate(bet);
				for (var j = 0; j < betData.length; j++) {
					var data = betData[j].split("|");
					for (var i = (data.length - 1); i >= 0; i--) {
						if (data[i].length === 0) {
							data.splice(i, 1);
						}
					}
					if (data.length !== limitQty) {
						return responseFormat('error', '輸入內容的數量有錯誤');
					}
					var dataObj = getDataObj(type, betData[j], info);
					response.push(dataObj);
				}
			} else {
				var dataObj = getDataObj(type, '1', info);
				dataObj.amount = dataObj.amount * noOfSubOrders;
				dataObj.noOfBet = dataObj.noOfBet * noOfSubOrders;
				response.push(dataObj);
			}
		}
		return response;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 三碼趣味二星
function ctt_sscq3qw2x(bet, type, info) {

	try {
		var limitQty = 3;
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {

			var betData = descarteCalculate(bet);
			var response = [];
			for (var j = 0; j < betData.length; j++) {
				var data = betData[j].split("|")

				for (var i = (data.length - 1); i >= 0; i--) {
					if (data[i].length === 0) {
						data.splice(i, 1);
					}
				}
				if (data.length !== limitQty) {
					return responseFormat('error', '輸入內容的數量有錯誤');
				}

				var dataObj = getDataObj(type, betData[j], info);
				response.push(dataObj);
			}

			return response;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 三碼趣味二星
function ctt_ssch3qw2x(bet, type, info) {

	try {
		var limitQty = 3;
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {

			var betData = descarteCalculate(bet);
			var response = [];
			for (var j = 0; j < betData.length; j++) {
				var data = betData[j].split("|")

				for (var i = (data.length - 1); i >= 0; i--) {
					if (data[i].length === 0) {
						data.splice(i, 1);
					}
				}
				if (data.length !== limitQty) {
					return responseFormat('error', '輸入內容的數量有錯誤');
				}

				var dataObj = getDataObj(type, betData[j], info);
				response.push(dataObj);
			}

			return response;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 五碼區間三星
function ctt_ssc5mqc3x(bet, type, info) {
	try {
		var limitQty = 5;
		var response = [];
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {
			if (bet.split('|').filter(function(v) {
				return v != ''
			}).length !== limitQty) {
				return responseFormat('error', '輸入內容的數量有錯誤');
			}
			var noOfSubOrders = subOrderCalculateBeforeDescarte(bet);
			if (noOfSubOrders < 0) {
				var betData = descarteCalculate(bet);
				for (var j = 0; j < betData.length; j++) {
					var data = betData[j].split("|");
					for (var i = (data.length - 1); i >= 0; i--) {
						if (data[i].length === 0) {
							data.splice(i, 1);
						}
					}
					if (data.length !== limitQty) {
						return responseFormat('error', '輸入內容的數量有錯誤');
					}
					var dataObj = getDataObj(type, betData[j], info);
					response.push(dataObj);
				}
			} else {
				var dataObj = getDataObj(type, '1', info);
				dataObj.amount = dataObj.amount * noOfSubOrders;
				dataObj.noOfBet = dataObj.noOfBet * noOfSubOrders;
				response.push(dataObj);
			}
		}
		return response;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 四碼區間三星
function ctt_ssc4mqc3x(bet, type, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var limitQty = 4;
		var response = [];
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {
			if (bet.split('|').filter(function(v) {
				return v != ''
			}).length !== limitQty) {
				return responseFormat('error', '輸入內容的數量有錯誤');
			}
			var noOfSubOrders = subOrderCalculateBeforeDescarte(bet);
			if (noOfSubOrders < 0) {
				var betData = descarteCalculate(bet);
				for (var j = 0; j < betData.length; j++) {
					var data = betData[j].split("|");
					for (var i = (data.length - 1); i >= 0; i--) {
						if (data[i].length === 0) {
							data.splice(i, 1);
						}
					}
					if (data.length !== limitQty) {
						return responseFormat('error', '輸入內容的數量有錯誤');
					}
					var dataObj = getDataObj(type, betData[j], info);
					response.push(dataObj);
				}
			} else {
				var dataObj = getDataObj(type, '1', info);
				dataObj.amount = dataObj.amount * noOfSubOrders;
				dataObj.noOfBet = dataObj.noOfBet * noOfSubOrders;
				response.push(dataObj);
			}
		}
		return response;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 三碼區間二星
function ctt_sscq3qc2x(bet, type, info) {
	try {
		var limitQty = 3;
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {

			var betData = descarteCalculate(bet);
			var response = [];
			for (var j = 0; j < betData.length; j++) {
				var data = betData[j].split("|")

				for (var i = (data.length - 1); i >= 0; i--) {
					if (data[i].length === 0) {
						data.splice(i, 1);
					}
				}
				if (data.length !== limitQty) {
					return responseFormat('error', '輸入內容的數量有錯誤');
				}

				var dataObj = getDataObj(type, betData[j], info);
				response.push(dataObj);
			}

			return response;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 三碼區間二星
function ctt_ssch3qc2x(bet, type, info) {
	try {
		var limitQty = 3;
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {

			var betData = descarteCalculate(bet);
			var response = [];
			for (var j = 0; j < betData.length; j++) {
				var data = betData[j].split("|")

				for (var i = (data.length - 1); i >= 0; i--) {
					if (data[i].length === 0) {
						data.splice(i, 1);
					}
				}
				if (data.length !== limitQty) {
					return responseFormat('error', '輸入內容的數量有錯誤');
				}

				var dataObj = getDataObj(type, betData[j], info);
				response.push(dataObj);
			}

			return response;
			// return generateScope(info, descarteCalculate(bet));
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 前中後三包膽
function ctt_sscr3bd(bet, type, position, info) {

	var typePrefix = type;

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {

			var data = transfer2Group(bet);

			if (data.length != 1) {
				return responseFormat('error', '輸入的數量錯誤');
			}

			var responseArray = [];
			data.forEach(function(value) {
				var type = typePrefix + getSum(convertPosition(position));
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			});

			return responseArray;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 前中後三和值尾數
function ctt_sscr3hzws(bet, type, position, info) {

	var typePrefix = type;

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		bet = bet.replace(/\s/g, '');

		if (checkStringInput(bet)) {
			var data = transfer2Group(bet);

			if (data.length < 1) {
				return responseFormat('error', '輸入的數量錯誤');
			}

			var responseArray = [];
			data.forEach(function(value) {
				var type = typePrefix + getSum(convertPosition(position));
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			});

			return responseArray;
		}

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 前中後三特殊號碼 （豹子、順子、對子、半順、雜六）
function ctt_sscr3special(bet, type, position, info) {
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var typePrefix = type;
		var value = bet.replace(/\s/g, '');

		var pos = getSum(convertPosition(position));

		var special = {
			'1' : 16,
			'2' : 8,
			'3' : 4,
			'4' : 2,
			'5' : 1
		};

		type = typePrefix + pos + '_' + special[value];

		var dataCheck = [ '1', '2', '3', '4', '5' ];

		if (dataCheck.indexOf(value) != -1) {
			value = 1;
		} else {
			throw new CustomError('sscr3special', '輸入內容格式有錯');
		}

		var responseArray = [];

		var dataObj = getDataObj(type, bet, info);

		responseArray.push(dataObj);

		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 前後二包膽
function ctt_sscr2bd(bet, type, position, info) {

	var typePrefix = type;

	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		bet = bet.replace(/\s/g, '');
		if (checkStringInput(bet)) {

			var data = transfer2Group(bet);

			if (data.length != 1) {
				return responseFormat('error', '輸入的數量錯誤');
			}

			var responseArray = [];
			data.forEach(function(value) {
				var type = typePrefix + getSum(convertPosition(position));
				var dataObj = getDataObj(type, value, info);
				responseArray.push(dataObj);
			});
			return responseArray;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_f3dr3bdw2m(bet, type, position, info) {
	var stars = 2;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		if (betData.length < stars) {
			return responseFormat('error', '最小長度不符合');
		} else {
			var responseArray = [];
			var data = newCombine(betData, stars);

			data.forEach(function(value) {
				var dataObj = getDataObj(type, value, info);
				responseArray.push(dataObj);
			});
			return responseArray;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_sscr3bdw2m(bet, type, position, info) {
	var stars = 2;
	var typePrefix = type;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		if (betData.length < stars) {
			return responseFormat('error', '最小長度不符合');
		} else {
			var responseArray = [];
			var data = newCombine(betData, stars);

			data.forEach(function(value) {
				var type = typePrefix + getSum(convertPosition(position));
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			});
			return responseArray;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_sscr4bdw2m(bet, type, position, info) {
	var stars = 2;
	var typePrefix = type;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		if (betData.length < stars) {
			return responseFormat('error', '最小長度不符合');
		} else {

			var responseArray = [];
			var data = newCombine(betData, stars);

			data.forEach(function(value) {
				var type = typePrefix + getSum(convertPosition(position));
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			});
			return responseArray;
		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_sscr5bdw2m(bet, type, position, info) {
	var stars = 2;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		if (betData.length < stars) {
			return responseFormat('error', '最小長度不符合');
		} else {

			var responseArray = [];
			var data = newCombine(betData, stars);

			data.forEach(function(value) {
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			});
			return responseArray;

		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_sscr5bdw3m(bet, type, position, info) {
	var stars = 3;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = transfer2Group(bet);

		if (betData.length < stars) {
			return responseFormat('error', '最小長度不符合');
		} else {

			var responseArray = [];
			var data = newCombine(betData, stars);

			data.forEach(function(value) {
				var dataObj = getDataObj(type, value, info);

				responseArray.push(dataObj);
			});
			return responseArray;

		}
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// PK10

function ctt_pk10q3ds(bet, type, info) {
	// 換行 \n 、逗號、分號、樹杠 | (沒有空格)
	if(bet.trim().length == 0){
		return responseFormat('error', '輸入的組合內容有誤');
	}
	var betData = bet.split(/[\n,;:|]/);

	var reFormatedData = [];
	info["betData"] = "";
	betData = deleteArrRepeatVal(betData);
	var responseArray = [];
	for (var i = 0, l = betData.length; i < l; i++) {
		var data = betData[i].trim();

		var rule = /^([0-9]{1}|[1][0]{1})[\s]{1,}([0-9]{1}|[1][0]{1})[\s]{1,}([0-9]{1}|[1][0]{1})$/;

		// var rule = /^[0-9]|10{1}[\s][0-9]|10{1}[\s][0-9]|10{1}$/;
		// 先檢查是否為數字組成的字串
		if (rule.test(data)) {
			// 符合條件

			var group = deleteArrayNullVal(data.split(' ')); // 11選5是以空格區隔每
			for (var s = 0; s < group.length; s++) {
				if (parseInt(group[s]) > 0 && parseInt(group[s]) < 11) {
					group[s] = parseInt(group[s]);
				} else {
					responseFormat('error', '輸入的組合內容有誤');
					continue;
				}
			}
			if (checkArrIsRepeat(group) || group.length != 3) {
				responseFormat('error', '輸入的組合內容重複');
				continue;
			}

			info["betData"] += group.join(" ") + ",";
			var dataObj = getDataObj(type, group.join("|"), info);
			responseArray.push(dataObj);

		} else {
			responseFormat('error', '輸入的組合內容有誤');
			continue;
		}
	}

	info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
	return responseArray;

}

function ctt_pk10q2ds(bet, type, info) {
	// 換行 \n 、逗號、分號、樹杠 | (沒有空格)

	if(bet.trim().length == 0){
		return responseFormat('error', '輸入的組合內容有誤');
	}
	var betData = bet.split(/[\n,;:|]/);

	var reFormatedData = [];
	info["betData"] = "";
	betData = deleteArrRepeatVal(betData);
	var responseArray = [];
	for (var i = 0, l = betData.length; i < l; i++) {
		var data = betData[i].trim();

		var rule = /^([0-9]{1}|[1][0]{1})[\s]{1,}([0-9]{1}|[1][0]{1})$/;
		// 先檢查是否為數字組成的字串
		if (rule.test(data)) {
			// 符合條件
			var group = deleteArrayNullVal(data.split(' ')); // 11選5是以空格區隔每

			for (var s = 0; s < group.length; s++) {
				if (parseInt(group[s]) > 0 && parseInt(group[s]) < 11) {
					group[s] = parseInt(group[s]);
				} else {
					responseFormat('error', '輸入的組合內容有誤');
					continue;
				}
			}

			if (checkArrIsRepeat(group) || group.length != 2) {
				responseFormat('error', '輸入的組合內容重複');
				continue;
			}

			info["betData"] += group.join(" ") + ",";
			var dataObj = getDataObj(type, group.join("|"), info);
			responseArray.push(dataObj);

		} else {
			responseFormat('error', '輸入的組合內容有誤');
			continue;
		}
	}

	info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
	return responseArray;

}

// k3

function ctt_k33xds(bet, type, info) {
	// 換行 \n 、空格、逗號、分號、樹杠 |
	var typePrefix = type;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(/[\n\s,;:|]/);

		var reFormatedData = [];
		betData = deleteArrRepeatVal(betData);
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 12379 => 12375 => 12789

			var d = data.split('');
			if (d.length != 3) {
				responseFormat('error', '輸入的組合內容數量有誤');
				continue;
			}

			if (d[0] == d[1] || d[0] == d[2] || d[1] == d[2]) {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}

			var rule = /^[1-6]{3}$/;
			// 先檢查是否為數字組成的字串
			if (rule.test(data)) {

				// 符合條件
				var group = data.split('');
				group = group.sort(function(a, b) {
					return a - b;
				});
				// [1,2,3,7,9] => [1,2,3,7,5] => [1,2,7,8,9]
				reFormatedData.push(group.join('|'));

			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}
		var responseArray = [];
		reFormatedData = deleteArrRepeatVal(reFormatedData);

		reFormatedData.forEach(function(value) {
			info["betData"] += value.split("|").join("") + ",";
			var dataObj = getDataObj(type, value, info)
			responseArray.push(dataObj);
		});

		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}
function ctt_k32xds(bet, type, info) {
	// 換行 \n 、空格、逗號、分號、樹杠 |
	var typePrefix = type;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(/[\n\s,;:|]/);

		var reFormatedData = [];
		betData = deleteArrRepeatVal(betData);
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 12379 => 12375 => 12789

			var d = data.split('');
			if (d.length != 2) {
				responseFormat('error', '輸入的組合內容數量有誤');
				continue;
			}

			if (d[0] == d[1]) {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}

			var rule = /^[1-6]{2}$/;
			// 先檢查是否為數字組成的字串
			if (rule.test(data)) {

				// 符合條件
				var group = data.split('');
				group = group.sort(function(a, b) {
					return a - b;
				});
				// [1,2,3,7,9] => [1,2,3,7,5] => [1,2,7,8,9]
				reFormatedData.push(group.join('|'));

			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}
		var responseArray = [];
		reFormatedData = deleteArrRepeatVal(reFormatedData);
		reFormatedData.forEach(function(value) {
			info["betData"] += value.split("|").join("") + ",";
			var dataObj = getDataObj(type, value, info)
			responseArray.push(dataObj);
		});

		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_k32ds(bet, type, info) {
	var typePrefix = type;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(/[\n\s,;:|]/);

		var reFormatedData = [];
		betData = deleteArrRepeatVal(betData);
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 12379 => 12375 => 12789

			var d = data.split('');
			if (d.length != 3) {
				responseFormat('error', '輸入的組合內容數量有誤');
				continue;
			}

			if (d[0] == d[1] && d[0] == d[2] && d[1] == d[2]) {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
			var rule = /^[1-6]{3}$/;
			// 先檢查是否為數字組成的字串
			if (rule.test(data)) {

				// 符合條件
				var group = data.split('');
				group.sort(function(a, b) {
					return a - b;
				});
				var repeatNum = group[1];
				group = deleteArrRepeatVal(group);

				if (group[1] == repeatNum) {
					group[1] = group[0];
					group[0] = repeatNum;
				}

				// [1,2,3,7,9] => [1,2,3,7,5] => [1,2,7,8,9]
				reFormatedData.push(group.join('|'));

			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}
		var responseArray = [];
		reFormatedData.forEach(function(value) {
			var val = value.split("|");
			info["betData"] += val[0] + val[0] + val[1] + ",";
			var dataObj = getDataObj(type, value, info)
			responseArray.push(dataObj);
		});

		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

// 3D

// 任二單式
function ctt_3dr2ds(bet, type, info) {
	// 換行 \n 、空格、逗號、分號、樹杠 |
	var typePrefix = type;
	var selectionQty = 2;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(/[\n\s,;:|]/);
		var responseArray = [];

		betData = deleteArrRepeatVal(betData);
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 12379 => 12375 => 12789

			var rule = /^\d{2}$/;

			// 先檢查是否為數字組成的字串
			if (rule.test(data)) {
				info["betData"] += data + ",";

				var group = data.split('');

				var dataObj = getDataObj(type, group.join("|"), info)
				responseArray.push(dataObj);

			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}
		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

//
function ctt_3dr2zds(bet, type, info) {
	// 換行 \n 、空格、逗號、分號、樹杠 |
	var typePrefix = type;
	var selectionQty = 2;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(/[\n\s,;:|]/);
		var responseArray = [];

		var dataArray = [];
		betData = deleteArrRepeatVal(betData);
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 12379 => 12375 => 12789

			var rule = /^\d{2}$/;

			// 先檢查是否為數字組成的字串
			if (rule.test(data)) {

				var group = data.split('');

				if (checkArrIsRepeat(group)) {
					responseFormat('error', '輸入的組合內容重複有誤');
					continue;
				}

				group = group.sort(function(a, b) {
					return a - b;
				});

				dataArray.push(group.join("|"));

			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}
		dataArray = deleteArrRepeatVal(dataArray);
		dataArray.forEach(function(value) {
			info["betData"] += value.split("|").join("") + ",";
			var dataObj = getDataObj(type, value, info)
			responseArray.push(dataObj);
		});

		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;

	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_3d3xds(bet, type, info) {
	// 換行 \n 、空格、逗號、分號、樹杠 |

	var typePrefix = type;
	var selectionQty = 3;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(/[\n\s,;:|]/);

		var responseArray = [];

		betData = deleteArrRepeatVal(betData);
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 12379 => 12375 => 12789
			var rule = /^\d{3}$/;

			// 先檢查是否為數字組成的字串
			if (rule.test(data)) {
				// 符合條件
				info["betData"] += data + ",";
				var group = data.split('');

				var dataObj = getDataObj(type, group.join("|"), info)
				responseArray.push(dataObj);

			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}

		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_3dz3d(bet, type, info) {
	// 換行 \n 、空格、逗號、分號、樹杠 |

	var typePrefix = type;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(/[\n\s,;:|]/);

		var reFormatedData = [];
		betData = deleteArrRepeatVal(betData);
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 12379 => 12375 => 12789

			var rule = /^\d{3}$/;
			if (rule.test(data)) {

				// 符合條件
				var group = data.split('');
				group.sort(function(a, b) {
					return a - b;
				});
				if (group[0] == group[1] && group[0] == group[2] && group[1] == group[2]) {
					responseFormat('error', '輸入的組合內容有誤');
					continue;
				} else if (checkArrIsRepeat(group) == false) {
					responseFormat('error', '輸入的組合內容有誤');
					continue;
				} else {
					var repeatVal = group[1];
					group = deleteArrRepeatVal(group);
					if (group[1] == repeatVal) {
						group[1] = group[0];
						group[0] = repeatVal;
					}
					reFormatedData.push(group.join('|'));
				}

			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}

		reFormatedData = deleteArrRepeatVal(reFormatedData);
		var responseArray = [];
		reFormatedData.forEach(function(value) {
			var val = value.split("|");
			info["betData"] += val[0] + val[0] + val[1] + ",";
			var dataObj = getDataObj(type, value, info)
			responseArray.push(dataObj);
		});

		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}

}

function ctt_3dz6d(bet, type, info) {
	// 換行 \n 、空格、逗號、分號、樹杠 |

	var typePrefix = type;
	var selectionQty = 3;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		
		var betData = bet.split(/[\n\s,;:|]/);

		var responseArray = [];
		var dataArray = [];

		betData = deleteArrRepeatVal(betData);
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 112 => 113 => 122 => 123

			var rule = /^\d{3}$/;
			// 先檢查是否為數字組成的字串
			if (rule.test(data) && checkArrIsRepeat(data.split("")) == false) {

				var group = data.split('');

				group = group.sort(function(a, b) {
					return a - b;
				});

				dataArray.push(group.join('|'));

			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}
		dataArray = deleteArrRepeatVal(dataArray);
		dataArray.forEach(function(value) {
			info["betData"] += value.split("|").join('') + ",";
			var dataObj = getDataObj(type, value, info)
			responseArray.push(dataObj);
		});

		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}

function ctt_3dr3mix(bet, info) {
	// 換行 \n 、空格、逗號、分號、樹杠 |

	var selectionQty = 3;
	try {
		if(bet.trim().length == 0){
			return responseFormat('error', '輸入的組合內容有誤');
		}
		var betData = bet.split(/[\n\s,;:|]/);

		var responseArray = [];
		var dataArray = [];

		betData = deleteArrRepeatVal(betData);
		info["betData"] = "";
		for (var i = 0, l = betData.length; i < l; i++) {
			var data = betData[i]; // 112 => 113 => 122 => 123

			var rule = /^\d{3}$/;

			if (rule.test(data)) {
				var group = data.split('');

				group = group.sort(function(a, b) {
					return a - b;
				});

				if (group[0] == group[1] && group[0] == [ 2 ] && group[1] == group[2]) {
					responseFormat('error', '輸入的組合內容有誤');
					continue;
				} else if (checkArrIsRepeat(group) == true) {
					var repeatVal = group[1];
					group = deleteArrRepeatVal(group);
					if (group[1] == repeatVal) {
						group[1] = group[0];
						group[0] = repeatVal;
					}
					dataArray.push(group.join('|'));
				} else {
					dataArray.push(group.join('|'));
				}
			} else {
				responseFormat('error', '輸入的組合內容有誤');
				continue;
			}
		}
		dataArray = deleteArrRepeatVal(dataArray);
		dataArray.forEach(function(value) {
			var val = value.split("|");
			if (val.length == 2) {
				info["betData"] += val[0] + val[0] + val[1] + ",";
				var dataObj = getDataObj("3dz3d", value, info)
				responseArray.push(dataObj);
			} else if (val.length == 3) {
				info["betData"] += val.join("") + ",";
				var dataObj = getDataObj("3dz6", value, info)
				responseArray.push(dataObj);
			}

		});

		info["betData"] = info["betData"].substr(0, info["betData"].length - 1);
		return responseArray;
	} catch (e) {
		if (e) {
			if (e.hasOwnProperty('printMessage')) {
				e.printMessage();
				return responseFormat('error', e.message);
			} else {
				console.error(e.stack);
			}
		} else {
			console.error('Error物件不存在');
		}
	}
}
