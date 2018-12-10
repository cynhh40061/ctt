
function checkArrayRepeat(arr) {
	var obj = {};
	for (var i = 0; i < arr.length; i++) {
		if (typeof obj[arr[i]] === "undefined") {
			obj[arr[i]] = 1;
		}
	}

	return arr.length == Object.keys(obj).length;
}

function checkLotteryNum_SSC(str) {
	var regExp = /^[0-9]{1}[,]{1}[0-9]{1}[,]{1}[0-9]{1}[,]{1}[0-9]{1}[,]{1}[0-9]{1}$/;
	if (regExp.test(str)) {
		return true;
	} else {
		return false;
	}
}
function checkLotteryNum_3D(str) {
	var regExp = /^[0-9]{1}[,]{1}[0-9]{1}[,]{1}[0-9]{1}$/;
	if (regExp.test(str)) {
		return true;
	} else {
		return false;
	}
}
function checkLotteryNum_PK10(str) {
	var regExp = /^([0-9]{1}|[1][0])[,]{1}([0-9]{1}|[1][0])[,]{1}([0-9]{1}|[1][0])[,]{1}([0-9]{1}|[1][0])[,]{1}([0-9]{1}|[1][0])[,]{1}([0-9]{1}|[1][0])[,]{1}([0-9]{1}|[1][0])[,]{1}([0-9]{1}|[1][0])[,]{1}([0-9]{1}|[1][0])[,]{1}([0-9]{1}|[1][0])$/;
	if (regExp.test(str)) {
		var strArr = str.split(",");
		for (var i = 0; i < strArr.length; i++) {
			var num = parseInt(strArr[i]);
			if (num < 1 && num > 10) {
				return false;
			}
			
			lotteryDataArr.push(num);
		}

		return checkArrayRepeat(strArr);
	} else {
		return false;
	}
}
function checkLotteryNum_K3(str) {
	var regExp = /^[1-6]{1}[,]{1}[1-6]{1}[,]{1}[1-6]{1}$/;
	if (regExp.test(str)) {
		return true;
	} else {
		return false;
	}
}
function checkLotteryNum_11X5(str) {
	var regExp = /^([0-9]{1}|[1][0]|[1]{2})[,]{1}([0-9]{1}|[1][0]|[1]{2})[,]{1}([0-9]{1}|[1][0]|[1]{2})[,]{1}([0-9]{1}|[1][0]|[1]{2})[,]{1}([0-9]{1}|[1][0]|[1]{2})$/;
	if (regExp.test(str)) {
		var strArr = str.split(",");

		for (var i = 0; i < strArr.length; i++) {
			var num = parseInt(strArr[i]);
			if (num < 1 && num > 11) {
				return false;
			}
			lotteryDataArr.push(num);
		}
		return checkArrayRepeat(lotteryDataArr);
	} else {
		return false;
	}
}
function checkLotteryNum_LHC(str) {
	//var regExp = /^([0-4]{0,1}[1-9]|[1][0]|[2][0]|[3][0]|[4][0])[,]([0-4]{0,1}[1-9]|[1][0]|[2][0]|[3][0]|[4][0])[,]([0-4]{0,1}[1-9]|[1][0]|[2][0]|[3][0]|[4][0])[,]([0-4]{0,1}[1-9]|[1][0]|[2][0]|[3][0]|[4][0])[,]([0-4]{0,1}[1-9]|[1][0]|[2][0]|[3][0]|[4][0])[,]([0-4]{0,1}[1-9]|[1][0]|[2][0]|[3][0]|[4][0])[,]([0-4]{0,1}[1-9]|[1][0]|[2][0]|[3][0]|[4][0])$/;
	var reg = new RegExp("^([0-4]{0,1}[1-9]|10|20|30|40)[,]([0-4]{0,1}[1-9]|10|20|30|40)[,]([0-4]{0,1}[1-9]|10|20|30|40)[,]([0-4]{0,1}[1-9]|10|20|30|40)[,]([0-4]{0,1}[1-9]|10|20|30|40)[,]([0-4]{0,1}[1-9]|10|20|30|40)[,]([0-4]{0,1}[1-9]|10|20|30|40)$");
	var lotteryDataArr =[]; 
	if (reg.test(str)) {
		var strArr = str.split(",");

		for (var i = 0; i < strArr.length; i++) {
			var num = parseInt(strArr[i]);
			lotteryDataArr.push(num);
		}

		return checkArrayRepeat(lotteryDataArr);
		//return strArr;
	} else {
		return false;
	}
}