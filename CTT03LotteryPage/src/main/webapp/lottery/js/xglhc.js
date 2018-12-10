function js_xglhc() {
	return true;
}

// 建立 追號單
function getMainOrders2(betOrderList, otherInfo) {
	otherInfo["playedText"] = "";
	getEle("mainOrder").value = "";
	groupMainOrderObj = {};
	if (Array.isArray(betOrderList) == true ? betOrderList.length > 0 : false) {
		document.getElementsByClassName("bet")[0].getElementsByTagName("button")[0].disabled = false;
		document.getElementsByClassName("bet")[0].getElementsByTagName("button")[1].disabled = false;
		document.getElementsByClassName("bet")[0].childNodes[0].title = "";
		document.getElementsByClassName("bet")[0].childNodes[1].title = "";

		var mainOrderArray = new Array();
		var totalNoOfBet = 0;
		var totalAmount = 0;
		var maxBouns = 0;
		

		var bonusRatioF = minus(parseInt(otherInfo.bonusSetMax), parseInt(otherInfo.bonusRatio));
		var bonusRatioS = divide(bonusRatioF, 2000);

		var midOrder = new Object();

		var totalNoOfBet = 0;
		var totalAmount = 0;

		for (var i = 0; i < betOrderList.length; i++) {
			if (maxBouns < parseFloat(betOrderList[i]['bonus'])) {
				maxBouns = parseFloat(betOrderList[i]['bonus']);
			}
			totalNoOfBet += toFloat(betOrderList[i]['noOfBet']);
			totalAmount += toFloat(betOrderList[i]['amount']);
		}
		
		midOrder.periodNum = "";
		midOrder.amount = "" + totalAmount;
		midOrder.maxBonus = "" + betOrderList[0]['bonus'];
		midOrder.fanDen = times(parseFloat(betOrderList[0]['amount']), bonusRatioS);
		midOrder.noOfBet = ""+totalNoOfBet;
		midOrder.noOfBetTimes = otherInfo['noOfBetTimes']; // 倍數
		midOrder.betOrders = betOrderList;
		
		
		midOrder.l1 = otherInfo.l1;
		midOrder.l2 = otherInfo.l2;
		midOrder.l3 = otherInfo.l3;
		midOrder.l4 = otherInfo.l4;

		var midOrderArr = new Array();

		midOrderArr.push(midOrder);

		var mainOrder = new Object();

		mainOrder.startPeriodNum = ""; // 開始期號
		mainOrder.stopPeriodNum = ""; // 停止期號
		if (typeof otherInfo["position"] != "undefined" && otherInfo["position"] != null && otherInfo["position"] != "") {
			mainOrder.betData = "" + otherInfo["betData"] + "|" + otherInfo["position"];
		} else {
			mainOrder.betData = "" + otherInfo["betData"]; // 母單的下注資訊
		}
		mainOrder.betData2 = "" + otherInfo["betData"];
		mainOrder.noOfPeriod = ""; // 共幾期
		mainOrder.accId = "" + otherInfo["accId"]; // 帳號

		mainOrder.mainId = "" + otherInfo["mainId"]; // 時時彩
		mainOrder.localId = "" + otherInfo["localId"]; // 重慶時時彩
		mainOrder.midId = "" + otherInfo["midId"]; // 五星
		mainOrder.minAuthId = "" + otherInfo["minAuthId"]; // 五星直選

		mainOrder.actionTime = ""; // 下注時間

		mainOrder.amount = "" + midOrder["amount"]; // 下注總額(追號單)
		mainOrder.moneyUnit = "" + otherInfo["moneyUnit"];
		mainOrder.orderType = '0'; // 是否 中獎不追等等 //待補
		mainOrder.handiCap = "" + otherInfo["handicap"];
		mainOrder.bonusSetRatio = "" + otherInfo["bonusRatio"]; // 獎金組

		mainOrder.playedText = getEle("playedId").value;

		mainOrder.baseBet = otherInfo.baseBet; // 最低投注額

		mainOrder.betRatio = "" + (divide(parseInt(midOrder["noOfBet"]), parseInt(otherInfo["totalNoOfBet"]))); // 投注比例

		mainOrder.isDt = "" + 0;

		mainOrder.lotteryLowfreq = "" + otherInfo["lotteryLowfreq"]; // 是否為低頻彩

		totalNoOfBet = totalNoOfBet + parseInt(midOrder.noOfBet);
		totalAmount = totalAmount + times(parseFloat(midOrder.amount), parseFloat(midOrder.noOfBetTimes));

		mainOrder['midOrders'] = midOrderArr;

		
		console.log(mainOrder);

		return mainOrder;
	}
	return null;
	
}

// 正碼
function ctt_mark6zms(bet, type, info) {
	try {
		
		if (bet.trim().length == 0) {
			return responseFormat('error', '最小長度不符合');
		}
		var responseArray = [];
		var betData = bet.split(",");
		if (betData.length == 0) {
			return responseFormat('error', '最小長度不符合');
		}

		for (var i = 0; i < betData.length; i++) {
			info["betData"] = betData[i];
			var realType = type + toInt(betData[i]);
			var dataObj = getDataObj(realType, "1", info);
			var mainOrder = getMainOrders2([dataObj],info);
			if(mainOrder != null){
				responseArray.push(mainOrder);
			}
		}
		groupMainOrderObj = responseArray;
		getEle("mainOrder").value = JSON.stringify(groupMainOrderObj);
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
// 正一~六特 and 特碼
function ctt_mark6tms(bet, type, info) {
	try {
		if (bet.trim().length == 0) {
			return responseFormat('error', '最小長度不符合');
		}
		var responseArray = [];
		var betData = bet.split(",");
		if (betData.length == 0) {
			return responseFormat('error', '最小長度不符合');
		}

		for (var i = 0; i < betData.length; i++) {
			var betArr = new Array();
			betArr.push(toInt(betData[i]));
			info["betData"] = betData[i];
			var realType = type;
			var dataObj = getDataObj2(realType, betArr, info);
			var mainOrder = getMainOrders2([dataObj],info);
			if(mainOrder != null){
				responseArray.push(mainOrder);
			}
		}
		groupMainOrderObj = responseArray;
		getEle("mainOrder").value = JSON.stringify(groupMainOrderObj);
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
// 正碼1-6
function ctt_mark6zmR(bet, mark6zmType, info) {
	try {
		if (bet.trim().length == 0) {
			return responseFormat('error', '最小長度不符合');
		}

		var mark6zmArr = [ "mark6zmbx" + mark6zmType + "_b", "mark6zmds" + mark6zmType + "_d", "mark6zmhds" + mark6zmType + "_d", "mark6zmbx" + mark6zmType + "_x",
			"mark6zmds" + mark6zmType + "_s", "mark6zmhds" + mark6zmType + "_s", "mark6zmrgb" + mark6zmType + "_r", "mark6zmrgb" + mark6zmType + "_g", "mark6zmrgb" + mark6zmType + "_b" ];

		var responseArray = [];
		var betData = bet.split(",");
		if (betData.length == 0) {
			return responseFormat('error', '最小長度不符合');
		}

		for (var i = 0; i < betData.length; i++) {
			info["betData"] = betData[i];
			var realType = mark6zmArr[toInt(betData[i]) - 1];
			var dataObj = getDataObj(realType, "1", info);
			var mainOrder = getMainOrders2([dataObj],info);
			if(mainOrder != null){
				responseArray.push(mainOrder);
			}
		}
		groupMainOrderObj = responseArray;
		getEle("mainOrder").value = JSON.stringify(groupMainOrderObj);
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
// 半波
function ctt_mark6bb(bet, info) {
	try {
		if (bet.trim().length == 0) {
			return responseFormat('error', '最小長度不符合');
		}

		var mark6bbArr = [ "mark6bb_rb", "mark6bb_gb", "mark6bb_bb", "mark6bb_rx", "mark6bb_gx", "mark6bb_bx", "mark6bb_rd", "mark6bb_gd", "mark6bb_bd", "mark6bb_rs", "mark6bb_gs",
			"mark6bb_bs", "mark6bb_rhd", "mark6bb_ghd", "mark6bb_bhd", "mark6bb_rhs", "mark6bb_ghs", "mark6bb_bhs" ];

		var responseArray = [];
		var betData = bet.split(",");
		if (betData.length == 0) {
			return responseFormat('error', '最小長度不符合');
		}

		for (var i = 0; i < betData.length; i++) {
			info["betData"] = betData[i];
			var realType = mark6bbArr[toInt(betData[i]) - 1];
			var dataObj = getDataObj(realType, "1", info);
			var mainOrder = getMainOrders2([dataObj],info);
			if(mainOrder != null){
				responseArray.push(mainOrder);
			}
		}
		groupMainOrderObj = responseArray;
		getEle("mainOrder").value = JSON.stringify(groupMainOrderObj);
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
// 特碼生肖
function ctt_mark6animaltm(bet, type, info) {
	try {
		if (bet.trim().length == 0) {
			return responseFormat('error', '最小長度不符合');
		}
		var responseArray = [];
		var betData = bet.split(",");
		if (betData.length == 0) {
			return responseFormat('error', '最小長度不符合');
		}

		for (var i = 0; i < betData.length; i++) {
			info["betData"] = betData[i];
			var realType = type;
			var dataObj = getDataObj(realType, betData[i], info);
			var mainOrder = getMainOrders2([dataObj],info);
			if(mainOrder != null){
				responseArray.push(mainOrder);
			}
		}
		groupMainOrderObj = responseArray;
		getEle("mainOrder").value = JSON.stringify(groupMainOrderObj);
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
// 一肖
function ctt_mark6animal(bet, type, info) {
	try {

		if (bet.trim().length == 0) {
			return responseFormat('error', '最小長度不符合');
		}
		var responseArray = [];
		var betData = bet.split(",");
		if (betData.length < 1) {
			return responseFormat('error', '最小長度不符合');
		}

		for (var i = 0; i < betData.length; i++) {
			info["betData"] = betData[i];
			var realType = type + betData[i];
			var dataObj = getDataObj(realType, "1", info);
			var mainOrder = getMainOrders2([dataObj],info);
			if(mainOrder != null){
				responseArray.push(mainOrder);
			}
		}
		groupMainOrderObj = responseArray;
		getEle("mainOrder").value = JSON.stringify(groupMainOrderObj);
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
// 二~五肖連中
function ctt_mark6animals(bet, stars, type, info) {
	try {

		if (bet.trim().length == 0) {
			return responseFormat('error', '最小長度不符合');
		}
		var responseArray = [];
		var betData = bet.split(",");
		if (betData.length < stars) {
			return responseFormat('error', '最小長度不符合');
		}
		var data = newCombine(betData, stars);

		for (var i = 0; i < data.length; i++) {
			var dataArr = data[i].split(",");
			if (dataArr.length == stars) {
				dataArr.sort(function(a, b) {
					return a - b;
				});
				info["betData"] = data[i];
				var realType = type + dataArr.join("_");
				var dataObj = getDataObj2(realType, dataArr, info);
				var mainOrder = getMainOrders2([dataObj],info);
				if(mainOrder != null){
					responseArray.push(mainOrder);
				}
			}	
		}
		groupMainOrderObj = responseArray;
		getEle("mainOrder").value = JSON.stringify(groupMainOrderObj);
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
// 尾數
function ctt_mark6ws(bet, type, info) {
	try {
		var wsArr = [];
		if (bet.trim().length == 0) {
			return responseFormat('error', '最小長度不符合');
		}
		var responseArray = [];
		var betData = bet.split(",");
		if (betData.length < 1) {
			return responseFormat('error', '最小長度不符合');
		}
		for (var i = 0; i < betData.length; i++) {
			info["betData"] = "" + betData[i];
			var realType = type + (parseInt(betData[i]) - 1);
			var dataObj = getDataObj(realType, "1", info);
			var mainOrder = getMainOrders2([dataObj],info);
			if(mainOrder != null){
				responseArray.push(mainOrder);
			}
		}
		groupMainOrderObj = responseArray;
		getEle("mainOrder").value = JSON.stringify(groupMainOrderObj);
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

// 二~四尾連中
function ctt_mark6wss(bet, stars, type, info) {
	try {
		if (bet.trim().length == 0) {
			return responseFormat('error', '最小長度不符合');
		}
		var responseArray = [];
		var betData = bet.split(",");
		if (betData.length < stars) {
			return responseFormat('error', '最小長度不符合');
		}
		var data = newCombine(betData, stars);

		for (var i = 0; i < data.length; i++) {
			var dataArr = data[i].split(",");
			if (dataArr.length == stars) {
				dataArr.sort(function(a, b) {
					return a - b;
				});
				var textArr = dataArr.map(function(a) {
					return "" + (parseInt(a) - 1);
				});
				info["betData"] = data[i];
				var realType = type + textArr.join("_");
				var dataObj = getDataObj2(realType, dataArr, info);
				var mainOrder = getMainOrders2([dataObj],info);
				if(mainOrder != null){
					responseArray.push(mainOrder);
				}
			}
		}
		groupMainOrderObj = responseArray;
		getEle("mainOrder").value = JSON.stringify(groupMainOrderObj);
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
// 五~十二不中
function ctt_mark6bc(bet, stars, type, info) {
	try {
		if (bet.trim().length == 0) {
			return responseFormat('error', '最小長度不符合');
		}
		var responseArray = [];
		var betData = bet.split(",");
		if (betData.length < stars) {
			return responseFormat('error', '最小長度不符合');
		}
//		
//		var noOfBet = toInt(computXuanhaoZhushu(betData.length , stars));
//		if(noOfBet > 1000){
//			var realType = type;
//			var dataObj = getDataObj2(realType, betData, info);
//			var amount = 0;
//			amount = toFloat(dataObj["amount"])*noOfBet;
//			dataObj["noOfBet"] = noOfBet;
//			dataObj["amount"] = amount;
//			
////			var mainOrder = getMainOrders2([dataObj],info);
////			if(mainOrder != null){
////				responseArray.push(mainOrder);
////			}
//			responseArray.push(dataObj);
//		}
//		else{
//			
//		}
		
		var data = newCombine(betData, stars);

		for (var i = 0; i < data.length; i++) {
			var dataArr = data[i].split(",");
			for(var j = 0 ; j < dataArr.length ; j++){
				dataArr[j] = toInt(dataArr[j]);
			}
			if (dataArr.length == stars) {
				dataArr.sort(function(a, b) {
					return a - b;
				});
				info["betData"] = data[i];
				var realType = type;
				var dataObj = getDataObj2(realType, dataArr, info);
				var mainOrder = getMainOrders2([dataObj],info);
				if(mainOrder != null){
					responseArray.push(mainOrder);
				}
			}
		}
		
		groupMainOrderObj = responseArray;
		getEle("mainOrder").value = JSON.stringify(groupMainOrderObj);
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

// 三全中
function ctt_mark6lm3c3(bet, type, info) {
	var stars = 3;
	try {
		if (bet.trim().length == 0) {
			return responseFormat('error', '最小長度不符合');
		}
		var responseArray = [];
		var betData = bet.split(",");
		if (betData.length < stars) {
			return responseFormat('error', '最小長度不符合');
		}
		
//		var realType = type;
//		var dataObj = getDataObj2(realType, betData, info);
//		
//		var amount = 0;
//		var noOfBet = toInt(computXuanhaoZhushu(betData.length , stars));
//		amount = toFloat(dataObj["amount"])*noOfBet;
//		dataObj["noOfBet"] = noOfBet;
//		dataObj["amount"] = amount;
//		
//		var mainOrder = getMainOrders2([dataObj],info);
//		if(mainOrder != null){
//			responseArray.push(mainOrder);
//		}
		
		var data = newCombine(betData, stars);

		for (var i = 0; i < data.length; i++) {
			var dataArr = data[i].split(",");
			for(var j = 0 ; j < dataArr.length ; j++){
				dataArr[j] = toInt(dataArr[j]);
			}
			if (dataArr.length == stars) {
				dataArr.sort(function(a, b) {
					return a - b;
				});
				info["betData"] = data[i];
				var realType = type;
				var dataObj = getDataObj2(realType, dataArr, info);
				var mainOrder = getMainOrders2([dataObj],info);
				if(mainOrder != null){
					responseArray.push(mainOrder);
				}
			}
		}
		groupMainOrderObj = responseArray;
		getEle("mainOrder").value = JSON.stringify(groupMainOrderObj);
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

// 三中二
function ctt_mark6lm3c2(bet, typeArr, info) {
	var stars = 3;
	try {
		if (Array.isArray(typeArr) == false) {
			return responseFormat('error', ' type 不符合');
		}
		if (bet.trim().length == 0) {
			return responseFormat('error', '最小長度不符合');
		}
		var responseArray = [];
		var betData = bet.split(",");
		if (betData.length < stars) {
			return responseFormat('error', '最小長度不符合');
		}
		
//		var betArray = [];
//		
//		for (var j = 0; j < typeArr.length; j++) {
//			var realType = typeArr[j];
//			if (realType == "mark6lm3c3") {
//				var dataObj = getDataObj2(realType, betData = betData.map(function(a) {
//					return "" + (parseInt(a) + 49);
//				}), info);
//				dataObj.amount = "0";
//				dataObj.noOfBet = "0";
//				betArray.push(dataObj);
//			} else {
//				var dataObj = getDataObj2(realType, betData, info);
//				
//				var amount = 0;
//				var noOfBet = toInt(computXuanhaoZhushu(betData.length , stars));
//				amount = toFloat(dataObj["amount"])*noOfBet;
//				dataObj["noOfBet"] = noOfBet;
//				dataObj["amount"] = amount;
//				
//				betArray.push(dataObj);
//			}
//		}
//		var mainOrder = getMainOrders2(betArray,info);
//		
//		if(mainOrder != null){
//			responseArray.push(mainOrder);
//		}
//		
		var data = newCombine(betData, stars);

		for (var i = 0; i < data.length; i++) {
			var dataArr = data[i].split(",");
			for(var j = 0 ; j < dataArr.length ; j++){
				dataArr[j] = toInt(dataArr[j]);
			}
			if (dataArr.length == stars) {
				dataArr.sort(function(a, b) {
					return a - b;
				});
				info["betData"] = data[i];
				var betArray = [];
				for (var j = 0; j < typeArr.length; j++) {
					var realType = typeArr[j];
					if (j == 0) {
						var dataObj = getDataObj2(realType, dataArr = dataArr.map(function(a) {
							return "" + (parseInt(a) + 49);
						}), info);
						
						dataObj.amount = "0";
						dataObj.noOfBet = "0";
						dataObj.subAction = "2";
						
						
						betArray.push(dataObj);
					} else {
						var dataObj = getDataObj2(realType, dataArr, info);
						
						dataObj.subAction = "1";
						betArray.push(dataObj);
					}
				}
				var mainOrder = getMainOrders2(betArray,info);
				if(mainOrder != null){
					responseArray.push(mainOrder);
				}

			}
		}
		groupMainOrderObj = responseArray;
		getEle("mainOrder").value = JSON.stringify(groupMainOrderObj);
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

// 二全中 特串
function ctt_mark6lm2c2(bet, type, info) {
	var stars = 2;
	try {
		if (bet.trim().length == 0) {
			return responseFormat('error', '最小長度不符合');
		}
		var responseArray = [];
		var betData = bet.split(",");
		if (betData.length < stars) {
			return responseFormat('error', '最小長度不符合');
		}
		
//		var realType = type;
//		var dataObj = getDataObj2(realType, betData, info);
//		
//		var amount = 0;
//		var noOfBet = toInt(computXuanhaoZhushu(betData.length , stars));
//		amount = toFloat(dataObj["amount"])*noOfBet;
//		dataObj["noOfBet"] = noOfBet;
//		dataObj["amount"] = amount;
//		
//		var mainOrder = getMainOrders2([dataObj],info);
//		if(mainOrder != null){
//			responseArray.push(mainOrder);
//		}
		
		var data = newCombine(betData, stars);

		for (var i = 0; i < data.length; i++) {
			var dataArr = data[i].split(",");
			for(var j = 0 ; j < dataArr.length ; j++){
				dataArr[j] = toInt(dataArr[j]);
			}
			if (dataArr.length == stars) {
				dataArr.sort(function(a, b) {
					return a - b;
				});
				info["betData"] = data[i];
				var realType = type;
				var dataObj = getDataObj2(realType, dataArr, info);
				var mainOrder = getMainOrders2([dataObj],info);
				if(mainOrder != null){
					responseArray.push(mainOrder);
				}
			}
		}
		groupMainOrderObj = responseArray;
		getEle("mainOrder").value = JSON.stringify(groupMainOrderObj);
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

// 二中特
function ctt_mark6lm2ct(bet, typeArr, info) {
	var stars = 2;
	try {
		if (Array.isArray(typeArr) == false) {
			return responseFormat('error', ' type 不符合');
		}
		if (bet.trim().length == 0) {
			return responseFormat('error', '最小長度不符合');
		}

		var responseArray = [];
		var betData = bet.split(",");
		if (betData.length < stars) {
			return responseFormat('error', '最小長度不符合');
		}
		
//		var betArray = [];
//		
//		for (var j = 0; j < typeArr.length; j++) {
//			var realType = typeArr[j];
//			if (realType == "mark6lmtc") {
//				var dataObj = getDataObj2(realType, betData = betData.map(function(a) {
//					return "" + (parseInt(a) + 49);
//				}), info);
//				dataObj.amount = "0";
//				dataObj.noOfBet = "0";
//				betArray.push(dataObj);
//			} else {
//				var dataObj = getDataObj2(realType, betData, info);
//				
//				var amount = 0;
//				var noOfBet = toInt(computXuanhaoZhushu(betData.length , stars));
//				amount = toFloat(dataObj["amount"])*noOfBet;
//				dataObj["noOfBet"] = noOfBet;
//				dataObj["amount"] = amount;
//				
//				betArray.push(dataObj);
//			}
//		}
//		var mainOrder = getMainOrders2(betArray,info);
//		
//		if(mainOrder != null){
//			responseArray.push(mainOrder);
//		}
		
		
		
		var data = newCombine(betData, stars);

		for (var i = 0; i < data.length; i++) {
			var dataArr = data[i].split(",");
			for(var j = 0 ; j < dataArr.length ; j++){
				dataArr[j] = toInt(dataArr[j]);
			}
			if (dataArr.length == stars) {
				dataArr.sort(function(a, b) {
					return a - b;
				});
				info["betData"] = data[i];
				var betArray = [];
				for (var j = 0; j < typeArr.length; j++) {
					var realType = typeArr[j];
					if (j == 0) {
						var dataObj = getDataObj2(realType, dataArr = dataArr.map(function(a) {
							return "" + (parseInt(a) + 49);
						}), info);
						dataObj.amount = "0";
						dataObj.noOfBet = "0";
						
						dataObj.subAction = "2";
						
						betArray.push(dataObj);
					} else {
						var dataObj = getDataObj2(realType, dataArr, info);
						
						dataObj.subAction = "1";
						
						betArray.push(dataObj);
					}
				}
				var mainOrder = getMainOrders2(betArray,info);
				if(mainOrder != null){
					responseArray.push(mainOrder);
				}

			}
		}
		groupMainOrderObj = responseArray;
		getEle("mainOrder").value = JSON.stringify(groupMainOrderObj);
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

// ------------------------------------------------------------------------------------------------------

// 特碼
function mark6tms(data) {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6tm';
		var result = ctt_mark6tms(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 正碼
function mark6zms(data) {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6zm_';
		var result = ctt_mark6zms(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		showBetData();

		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 正一特
function mark6zmt1() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6zmt1';
		var result = ctt_mark6tms(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 正二特
function mark6zmt2() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6zmt2';
		var result = ctt_mark6tms(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 正三特
function mark6zmt3() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		showBetData();
		
		var type = 'mark6zmt3';
		var result = ctt_mark6tms(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);

		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 正四特
function mark6zmt4() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6zmt4';
		var result = ctt_mark6tms(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 正五特
function mark6zmt5() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6zmt5';
		var result = ctt_mark6tms(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 正六特
function mark6zmt6() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6zmt6';
		var result = ctt_mark6tms(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 正碼1
function mark6zm1() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var result = ctt_mark6zmR(otherInfo["betData"], 1, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, json;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 正碼2
function mark6zm2() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var result = ctt_mark6zmR(otherInfo["betData"], 2, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, json;
		data = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 正碼3
function mark6zm3() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var result = ctt_mark6zmR(otherInfo["betData"], 3, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, json;
		data = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 正碼4
function mark6zm4() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var result = ctt_mark6zmR(otherInfo["betData"], 4, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, json;
		data = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 正碼5
function mark6zm5() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var result = ctt_mark6zmR(otherInfo["betData"], 5, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, json;
		data = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 正碼6
function mark6zm6() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var result = ctt_mark6zmR(otherInfo["betData"], 6, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, json;
		data = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}

// 三全中
function mark6lm3c3() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var result = ctt_mark6lm3c3(otherInfo["betData"], "mark6lm3c3", otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, json;
		data = undefined;

		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 三中二
function mark6lm3c2() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var result = ctt_mark6lm3c2(otherInfo["betData"], [ "mark6lm3c2", "mark6lm3c2" ], otherInfo);
//		var result = ctt_mark6lm3c3(otherInfo["betData"], "mark6lm3c2", otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, json;
		data = undefined;

		json = undefined;


		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 二全中
function mark6lm2c2() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var result = ctt_mark6lm2c2(otherInfo["betData"], "mark6lm2c2", otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, json;
		data = undefined;

		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 二中特
function mark6lm2ct() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var result = ctt_mark6lm2ct(otherInfo["betData"], [ "mark6lm2ct", "mark6lm2ct" ], otherInfo);
//		var result = ctt_mark6lm2c2(otherInfo["betData"], "mark6lm2ct", otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, json;
		data = undefined;

		json = undefined;

		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 特串
function mark6lmtc() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var result = ctt_mark6lm2c2(otherInfo["betData"], "mark6lmtc", otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, json;
		data = undefined;

		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}

// 半波
function mark6bb() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var result = ctt_mark6bb(otherInfo["betData"], otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, json;
		data = undefined;

		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 特碼生肖
function mark6animaltm() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var result = ctt_mark6animaltm(otherInfo["betData"], "mark6animaltm", otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, json;
		data = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 一肖
function mark6animal1() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6animal1_';
		var result = ctt_mark6animal(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}

}
// 兩肖連中
function mark6animal2() {
	// mark6animal2_1_2
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6animal2_';
		var result = ctt_mark6animals(otherInfo["betData"], 2, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}

}
// 三肖連中
function mark6animal3() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6animal3_';
		var result = ctt_mark6animals(otherInfo["betData"], 3, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 四肖連中
function mark6animal4() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6animal4_';
		var result = ctt_mark6animals(otherInfo["betData"], 4, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 五肖連中
function mark6animal5() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6animal5_';
		var result = ctt_mark6animals(otherInfo["betData"], 5, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 尾數
function mark6ws() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6ws_';
		var result = ctt_mark6ws(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 二尾連中
function mark6ws2() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6ws2_';
		var result = ctt_mark6wss(otherInfo["betData"], 2, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 三尾連中
function mark6ws3() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6ws3_';
		var result = ctt_mark6wss(otherInfo["betData"], 3, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 四尾連中
function mark6ws4() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6ws4_';
		var result = ctt_mark6wss(otherInfo["betData"], 4, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 五不中
function mark6bc5() {
	// mark6bc
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6bc';
		var result = ctt_mark6bc(otherInfo["betData"], 5, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
//		getMainOrders(result,otherInfo);
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 六不中
function mark6bc6() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6bc';
		var result = ctt_mark6bc(otherInfo["betData"], 6, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
//		getMainOrders(result,otherInfo);
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 七不中
function mark6bc7() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6bc';
		var result = ctt_mark6bc(otherInfo["betData"], 7, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
//		getMainOrders(result,otherInfo);
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 八不中
function mark6bc8() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6bc';
		var result = ctt_mark6bc(otherInfo["betData"], 8, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
//		getMainOrders(result,otherInfo);
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 九不中
function mark6bc9() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6bc';
		var result = ctt_mark6bc(otherInfo["betData"], 9, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
//		getMainOrders(result,otherInfo);
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 十不中
function mark6bc10() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6bc';
		var result = ctt_mark6bc(otherInfo["betData"], 10, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
//		getMainOrders(result,otherInfo);
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 十一不中
function mark6bc11() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6bc';
		var result = ctt_mark6bc(otherInfo["betData"], 11, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
//		getMainOrders(result,otherInfo);
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}
// 十二不中
function mark6bc12() {
	var otherInfo = getOtherInfo2();
	if (otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined") {
		var data = getContent();
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;

		var type = 'mark6bc';
		var result = ctt_mark6bc(otherInfo["betData"], 12, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		showBetData();
//		getMainOrders(result,otherInfo);
		
		delete data, type, json;
		data = undefined;
		type = undefined;
		json = undefined;
		return responseFormat('success', {
			'result' : result,
			'noOfBet' : noOfBet,
			'otherInfo' : otherInfo
		});
	}
}