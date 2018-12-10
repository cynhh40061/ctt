const ORDER_JS = true;
const order = {
	"orderStatus" : {
		"1" : "待審核",
		"2" : "審核中",
		"3" : "複審核",
		"4" : "複審中",
		"5" : "待複查",
		"6" : "複查中",
		"7" : "完成",
		"8" : "拒絕",
		"9" : "異常",
		"10" : "API完成"
	},
	"rechargeType" : {
		"1" : "網路銀行",
		"2" : "第三方API",
		"3" : "類第三方API"
	},
	"orderType" : {
		"0" : "充值",
		"1" : "提現"
	}
};

function Order_RechargeCarrOut() {
	Order_into();
	onClickOrderRechargeCarrOut();
}

function Order_WithdrawalCarryOut() {
	Order_into();
	onClickOrderWithdrawalCarryOut();
}

function Order_RechargeUndone() {
	Order_into();
	onClickOrderRechargeUndone();
}

function Order_WithdrawalUndone() {
	Order_into();
	onClickOrderWithdrawalUndone();
}

function Order_Review() {
	Order_into();
	onClickOrderReview();
}

// <input type="hidden" name="orderAuthJson" value="0">
// <input type="hidden" name="orderJson" value="">
// <input type="hidden" name="auditOrder" value="">
// <input type="hidden" name="carryoutOrder" value="">
// <input type="hidden" name="ReviewPassOrder" value="">
// <input type="hidden" name="orderAes" value="0">
// <input type="hidden" name="orderSort" value="0">
// <input type="hidden" name="orderMaxPage" value="1">
// <input type="hidden" name="orderPage" value="1">
// <input type="hidden" name="orderNextPage" value="1">
// <input type="hidden" name="orderSelectCount" value="25">

function Order_into() {
	var str = '';
	if (document.getElementsByName("orderJson").length != 1) {
		str += '\n<input type="hidden" name="orderJson" value = "">';
		str += '\n<input type="hidden" name="auditOrder" value = "">';
		str += '\n<input type="hidden" name="carryoutOrder" value = "">';
		str += '\n<input type="hidden" name="ReviewPassOrder" value = "">';

		str += '\n<input type="hidden" name="orderAes" value = "0">';
		str += '\n<input type="hidden" name="orderSort" value = "0">';

		str += '\n<input type="hidden" name="orderMaxPage" value = "1">';
		str += '\n<input type="hidden" name="orderPage" value = "1">';
		str += '\n<input type="hidden" name="orderNextPage" value = "1">';
		str += '\n<input type="hidden" name="orderSelectCount" value = "25">';
	}

	if (str != '') {
		document.getElementById("extraHidden").innerHTML = str;
	}
	delete str;
	str = undefined;
	if (document.getElementById("mainContain") != null) {
		document.getElementById("mainContain").innerHTML = "";
	}
	if (document.getElementById("myModal") != null) {
		document.getElementById("myModal").innerHTML = "";
	}
	if (document.getElementById("myModalV2") != null) {
		document.getElementById("myModalV2").innerHTML = "";
	}
}

function onChangeOrderSort(idx, searchOrderType) {
	orderSort(idx, searchOrderType);
	showOrderTable(idx, searchOrderType);
}

function orderSort(idx, searchOrderType) {
	if (getEle("orderSort").value != idx) {
		getEle("orderSort").value = idx;
		getEle("orderAes").value = 1;

	} else {
		if (getEle("orderAes").value == 0) {
			getEle("orderAes").value = 1;
		} else {
			getEle("orderAes").value = 0;
		}
	}

}

function showOrderTable(idx, searchOrderType) {
	var sort = [ "orderId", "amount", "createDatetime", "firstDatetime" ];
	var json = JSON.parse(getEle("orderJson").value);
	if (searchOrderType == 1) {
		// searchRechargeCarryOutAjax();
		if (getEle("orderAes").value == 0) {
			json.RechargeCarryOut = jsonDataSort(json.RechargeCarryOut, sort[idx], false);
		} else {
			json.RechargeCarryOut = jsonDataSort(json.RechargeCarryOut, sort[idx], true);
		}
		if (Object.keys(json.RechargeCarryOut).length > 0) {
			getEle("orderJson").value = JSON.stringify(json);
		}

		showRechargeCarryOut();
	} else if (searchOrderType == 2) {
		// searchRechargeUndoneAjax();
		if (getEle("orderAes").value == 0) {
			json.RechargeUndone = jsonDataSort(json.RechargeUndone, sort[idx], false);
		} else {
			json.RechargeUndone = jsonDataSort(json.RechargeUndone, sort[idx], true);
		}

		if (Object.keys(json.RechargeUndone).length > 0) {
			getEle("orderJson").value = JSON.stringify(json);
		}

		showRechargeUndone();
	} else if (searchOrderType == 3) {
		// searchWithdrawalCarryOutAjax();
		if (getEle("orderAes").value == 0) {
			json.WithdrawalCarryOut = jsonDataSort(json.WithdrawalCarryOut, sort[idx], false);
		} else {
			json.WithdrawalCarryOut = jsonDataSort(json.WithdrawalCarryOut, sort[idx], true);
		}

		if (Object.keys(json.WithdrawalCarryOut).length > 0) {
			getEle("orderJson").value = JSON.stringify(json);
		}

		showWithdrawalCarryOut();
	} else if (searchOrderType == 4) {
		// searchWithdrawalUndoneAjax();
		if (getEle("orderAes").value == 0) {
			json.WithdrawalUndone = jsonDataSort(json.WithdrawalUndone, sort[idx], false);
		} else {
			json.WithdrawalUndone = jsonDataSort(json.WithdrawalUndone, sort[idx], true);
		}
		if (Object.keys(json.WithdrawalUndone).length > 0) {
			getEle("orderJson").value = JSON.stringify(json);
		}
		showWithdrawalUndone();
	} else if (searchOrderType == 5) {
		// getOrderReviewDataAjax();
		if (getEle("orderAes").value == 0) {
			json.Review = jsonDataSort(json.Review, sort[idx], false);
		} else {
			json.Review = jsonDataSort(json.Review, sort[idx], true);
		}
		if (Object.keys(json.Review).length > 0) {
			getEle("orderJson").value = JSON.stringify(json);
		}

		showReview();
	}
}

function showRechargeCarryOut() {
	var json = JSON.parse(getEle("orderJson").value);
	var str = '<div id = "orderSearchTime">'
			+ json.searchTime
			+ '</div><table class="width-percent-910 tr-hover"><tbody><tr><th><a onclick="onChangeOrderSort(0,1);"> \n'
			+ '單號<i class=\"icon-arrow-down\" id = "orderIconArrow0"></i></a></th><th>會員帳號</th><th><a onclick="onChangeOrderSort(1,1);">充值金額<i class=\"icon-arrow-down\" id = "orderIconArrow1"> \n'
			+ '</i></a></th><th>充值通道</th><th><a onclick="onChangeOrderSort(2,1);">訂單時間<i class=\"icon-arrow-down\" id = "orderIconArrow2"></i></a></th><th>訂單狀態</th><th>附言</th> \n'
			+ '<th>審核人員1</th><th><a onclick="onChangeOrderSort(3,1);">完成時間<i class=\"icon-arrow-down\" id = "orderIconArrow3"></a></th><th>審核人員2<th>類型</th><th>功能</th></tr>';
	var str2 = '';
	var josnObject = json.RechargeCarryOut;
	for (var i = 0; i < Object.keys(josnObject).length; i++) {
		if (josnObject[i].orderId != "") {
			var statusCheck = '';
			if (order.orderStatus[josnObject[i].status] == '待複查') {
				statusCheck = '<a href="javascript:reviewPassOrder(' + josnObject[i].orderId + ',' + 0 + ');">待複查</a>'
			} else {
				statusCheck = order.orderStatus[josnObject[i].status];
			}
			str2 += '<tr><td>' + josnObject[i].orderId + '</td><td>' + josnObject[i].accName + '</td><td>' + josnObject[i].amount + '</td>' + '<td>'
					+ order.rechargeType[josnObject[i].rechargeType] + '</td><td>' + josnObject[i].createDatetime + '</td><td>' + statusCheck
					+ '</td>' + '<td>' + josnObject[i].note + '</td><td>' + (josnObject[i].firstAccName == null ? '' : josnObject[i].firstAccName)
					+ '</td><td>' + (josnObject[i].firstDatetime == null ? '' : josnObject[i].firstDatetime) + '</td><td>'
					+ (josnObject[i].secondAccName == null ? '' : josnObject[i].secondAccName) + '</td><td>' + josnObject[i].backOrderStatus
					+ '</td><td><a href="javascript:checkRechargeCarryOutOrderAjax(' + josnObject[i].orderType + ' , ' + josnObject[i].orderId
					+ ');">查看</a></td></tr>';

		}
	}

	var strEnd = '</tbody></table>';
	var strHtml = "";
	strHtml = strHtml.concat(str, str2, strEnd);
	getEle("mainContain").innerHTML = strHtml;

	if (parseInt(getEle("orderAes").value) == 1) {
		if (!getEle("orderIconArrow" + getEle("orderSort").value).classList.contains("icon-arrow-up")) {
			getEle("orderIconArrow" + getEle("orderSort").value).classList.add("icon-arrow-up");
		}
	} else {
		if (getEle("orderIconArrow" + getEle("orderSort").value).classList.contains("icon-arrow-up")) {
			getEle("orderIconArrow" + getEle("orderSort").value).classList.remove("icon-arrow-up");
		}
	}

}
function showRechargeUndone() {
	var json = JSON.parse(getEle("orderJson").value);
	var str = '<div id = "orderSearchTime">'
			+ json.searchTime
			+ '</div><table class="width-percent-910 tr-hover"><tbody><tr><th><a onclick="onChangeOrderSort(0,2);">單號<i class=\"icon-arrow-down\" id = "orderIconArrow0"></i></a></th><th>會員帳號</th><th><a onclick="onChangeOrderSort(1,2);">充值金額<i class=\"icon-arrow-down\" id = "orderIconArrow1"></i></a></th><th>充值通道</th><th><a onclick="onChangeOrderSort(2,2);">訂單時間<i class=\"icon-arrow-down\" id = "orderIconArrow2"></i></a></th><th>訂單狀態</th><th>附言</th><th>審核人員1</th><th><a onclick="onChangeOrderSort(3,2);">完成時間<i class=\"icon-arrow-down\" id = "orderIconArrow3"></a></th>'
			+ '<th>審核人員2<th>類型</th><th>功能</th></tr>';
	var str2 = '';
	var josnObject = json.RechargeUndone;
	for (var i = 0; i < Object.keys(josnObject).length; i++) {
		if (josnObject[i].orderId != "") {
			str2 += '<tr><td>' + josnObject[i].orderId + '</td><td>' + josnObject[i].accName + '</td><td>' + josnObject[i].amount + '</td>' + '<td>'
					+ order.rechargeType[josnObject[i].rechargeType] + '</td><td>' + josnObject[i].createDatetime + '</td><td>'
					+ order.orderStatus[josnObject[i].status] + '</td>' + '<td>' + josnObject[i].note + '</td><td>'
					+ (josnObject[i].firstAccName == null ? '' : josnObject[i].firstAccName) + '</td><td>'
					+ (josnObject[i].firstDatetime == null ? '' : josnObject[i].firstDatetime) + '</td><td>'
					+ (josnObject[i].secondAccName == null ? '' : josnObject[i].secondAccName) + '</td><td>' + josnObject[i].backOrderStatus
					+ '</td><td><a href="javascript:getAuditRechargeOrderAjax(' + josnObject[i].orderType + ' , ' + josnObject[i].orderId + ' , '
					+ josnObject[i].status + ' , ' + josnObject[i].rechargeType + ');">受理</a></td></tr>';

		}
	}

	var strEnd = '</tbody></table>';
	var strHtml = "";
	strHtml = strHtml.concat(str, str2, strEnd);
	getEle("mainContain").innerHTML = strHtml;

	if (parseInt(getEle("orderAes").value) == 1) {
		if (!getEle("orderIconArrow" + getEle("orderSort").value).classList.contains("icon-arrow-up")) {
			getEle("orderIconArrow" + getEle("orderSort").value).classList.add("icon-arrow-up");
		}
	} else {
		if (getEle("orderIconArrow" + getEle("orderSort").value).classList.contains("icon-arrow-up")) {
			getEle("orderIconArrow" + getEle("orderSort").value).classList.remove("icon-arrow-up");
		}
	}
}

function showWithdrawalCarryOut() {
	var json = JSON.parse(getEle("orderJson").value);
	var str = '<div id = "orderSearchTime">'
			+ json.searchTime
			+ '</div><table class = "tr-hover">'
			+ '<tbody><tr><th><a onclick="onChangeOrderSort(0,3);">單號<i class=\"icon-arrow-down\" id = "orderIconArrow0"></i></a></th><th>會員帳號</th><th><a onclick="onChangeOrderSort(2,3);">訂單時間<i class=\"icon-arrow-down\" id = "orderIconArrow2"></i></a></th><th><a onclick="onChangeOrderSort(1,3);">提現金額<i class=\"icon-arrow-down\" id = "orderIconArrow1"></i></a></th><th>提現銀行</th><th>帳戶名</th><th>提現帳戶</th><th>訂單狀態</th><th>審核人員</th><th><a onclick="onChangeOrderSort(3,3);">完成時間<i class=\"icon-arrow-down\" id = "orderIconArrow3"></a></th>'
			+ '<th>類型</th><th>功能</th></tr>';
	var str2 = '';
	var josnObject = json.WithdrawalCarryOut;
	for (var i = 0; i < Object.keys(josnObject).length; i++) {
		if (josnObject[i].orderId != "") {
			if (order.orderStatus[josnObject[i].status] == '待複查') {
				statusCheck = '<a href="javascript:reviewPassOrder(' + josnObject[i].orderId + ',' + 1 + ');">待複查</a>'
			} else {
				statusCheck = order.orderStatus[josnObject[i].status];
			}
			str2 += '<tr><td>' + josnObject[i].orderId + '</td><td>' + josnObject[i].accName + '</td><td>' + josnObject[i].createDatetime + '</td>'
					+ '<td>' + josnObject[i].amount + '</td><td>' + josnObject[i].bank + '</td><td>' + josnObject[i].bankAccName + '</td>' + '<td>'
					+ josnObject[i].bankAcc + '</td><td>' + statusCheck + '</td><td>'
					+ (josnObject[i].firstAccName == null ? '' : josnObject[i].firstAccName) + '</td><td>'
					+ (josnObject[i].firstDatetime == null ? '' : josnObject[i].firstDatetime) + '</td><td>' + josnObject[i].type
					+ '</td><td><a href="javascript:checkWithdrawalCarryOutOrderAjax(' + josnObject[i].orderType + ' , ' + josnObject[i].orderId
					+ ');">查看</a></td></tr>';

		}
	}

	var strEnd = '</tbody></table>';
	var strHtml = "";
	strHtml = strHtml.concat(str, str2, strEnd);
	getEle("mainContain").innerHTML = strHtml;

	if (parseInt(getEle("orderAes").value) == 1) {
		if (!getEle("orderIconArrow" + getEle("orderSort").value).classList.contains("icon-arrow-up")) {
			getEle("orderIconArrow" + getEle("orderSort").value).classList.add("icon-arrow-up");
		}
	} else {
		if (getEle("orderIconArrow" + getEle("orderSort").value).classList.contains("icon-arrow-up")) {
			getEle("orderIconArrow" + getEle("orderSort").value).classList.remove("icon-arrow-up");
		}
	}

}
function showWithdrawalUndone() {
	var json = JSON.parse(getEle("orderJson").value);
	var str = '<div id = "orderSearchTime">'
			+ json.searchTime
			+ '</div><table class = "tr-hover"><tbody><tr><th><a onclick="onChangeOrderSort(0,4);">單號<i class=\"icon-arrow-down\" id = "orderIconArrow0"></i></a></th>'
			+ '<th>會員帳號</th><th><a onclick="onChangeOrderSort(2,4);">訂單時間<i class=\"icon-arrow-down\" id = "orderIconArrow2"></i></a></th><th><a onclick="onChangeOrderSort(1,4);">提現金額<i class=\"icon-arrow-down\" id = "orderIconArrow1"></i></a></th><th>提現銀行</th><th>帳戶名</th><th>提現帳戶</th><th>訂單狀態</th><th>審核人員</th><th>類型</th><th>功能</th></tr>';
	var str2 = '';
	var josnObject = json.WithdrawalUndone;
	for (var i = 0; i < Object.keys(josnObject).length; i++) {
		if (josnObject[i].orderId != "") {
			str2 += '<tr><td>' + josnObject[i].orderId + '</td><td>' + josnObject[i].accName + '</td><td>' + josnObject[i].createDatetime + '</td>'
					+ '<td>' + josnObject[i].amount + '</td><td>' + josnObject[i].bank + '</td><td>' + josnObject[i].bankAccName + '</td>' + '<td>'
					+ josnObject[i].bankAcc + '</td><td>' + order.orderStatus[josnObject[i].status] + '</td><td>'
					+ (josnObject[i].firstAccName == null ? '' : josnObject[i].firstAccName) + '</td><td>' + josnObject[i].type
					+ '</td><td><a href="javascript:getAuditWithdrawalOrderAjax(' + josnObject[i].orderType + ' , ' + josnObject[i].orderId + ' , '
					+ josnObject[i].status + ' , ' + josnObject[i].rechargeType + ');">受理</a></td></tr>';

		}
	}

	var strEnd = '</tbody></table>';
	var strHtml = "";
	strHtml = strHtml.concat(str, str2, strEnd);
	getEle("mainContain").innerHTML = strHtml;

	if (parseInt(getEle("orderAes").value) == 1) {
		if (!getEle("orderIconArrow" + getEle("orderSort").value).classList.contains("icon-arrow-up")) {
			getEle("orderIconArrow" + getEle("orderSort").value).classList.add("icon-arrow-up");
		}
	} else {
		if (getEle("orderIconArrow" + getEle("orderSort").value).classList.contains("icon-arrow-up")) {
			getEle("orderIconArrow" + getEle("orderSort").value).classList.remove("icon-arrow-up");
		}
	}
}

function showReview() {
	var json = JSON.parse(getEle("orderJson").value);
	var str = '<div id = "orderSearchTime">'
			+ json.searchTime
			+ '</div><table class = "tr-hover"><tbody><tr><th><a onclick="onChangeOrderSort(0,5);">單號<i class=\"icon-arrow-down\" id = "orderIconArrow0"></i></a></th><th>會員帳號</th><th><a onclick="onChangeOrderSort(1,5);">金額<i class=\"icon-arrow-down\" id = "orderIconArrow1"></i></a></th><th>訂單類型</th><th><a onclick="onChangeOrderSort(2,5);">訂單時間<i class=\"icon-arrow-down\" id = "orderIconArrow2"></i></a></th><th>審核人員1</th>'
			+ '<th><a onclick="onChangeOrderSort(3,5);">完成時間<i class=\"icon-arrow-down\" id = "orderIconArrow3"></i></a></th><th>審核人員2</th><th>完成時間</th><th>備註</th><th>功能</th></tr>';
	var str2 = '';
	var josnObject = json.Review;
	for (var i = 0; i < Object.keys(josnObject).length; i++) {
		if (josnObject[i].orderId != "") {
			str2 += '<tr><td>' + josnObject[i].orderId + '</td><td>' + josnObject[i].accName + '</td><td>' + josnObject[i].amount + '</td>' + '<td>'
					+ order.orderType[josnObject[i].orderType] + '</td><td>' + josnObject[i].createDatetime + '</td>' + '<td>'
					+ (josnObject[i].firstAccName == null ? '' : josnObject[i].firstAccName) + '</td><td>'
					+ (josnObject[i].firstDatetime == null ? '' : josnObject[i].firstDatetime) + '</td><td>'
					+ (josnObject[i].secondAccName == null ? '' : josnObject[i].secondAccName) + '</td><td>'
					+ (josnObject[i].secondDatetime == null ? '' : josnObject[i].secondDatetime) + '</td><td>' + josnObject[i].backOrderStatus
					+ '</td><td><a href="javascript:reviewPassOrder(' + josnObject[i].orderId + ',' + josnObject[i].orderType
					+ ');">複查</a></td></tr>';
		}
	}

	var strEnd = '</tbody></table>';
	var strHtml = "";
	strHtml = strHtml.concat(str, str2, strEnd);

	getEle("mainContain").innerHTML = strHtml;

	if (parseInt(getEle("orderAes").value) == 1) {
		if (!getEle("orderIconArrow" + getEle("orderSort").value).classList.contains("icon-arrow-up")) {
			getEle("orderIconArrow" + getEle("orderSort").value).classList.add("icon-arrow-up");
		}
	} else {
		if (getEle("orderIconArrow" + getEle("orderSort").value).classList.contains("icon-arrow-up")) {
			getEle("orderIconArrow" + getEle("orderSort").value).classList.remove("icon-arrow-up");
		}
	}
}
function firstOrderPage() {
	getEle("orderNextPage").value = "1";
	// getEle("displayOrderNowPage").value = getEle("orderNextPage").value;

	if (getEle("orderNextPage").value != getEle("orderPage").value) {
		getSearchDataAjax(getEle("page").value, getEle("orderNextPage").value);
	}
}
function previousOrderPage() {
	var previousPage = parseInt(getEle("orderPage").value) - 1;
	var page = "1"
	if (previousPage > 0) {
		page = previousPage;
	}
	getEle("orderNextPage").value = page;
	// getEle("displayOrderNowPage").value = getEle("orderNextPage").value;

	if (getEle("orderPage").value != getEle("orderNextPage").value) {
		getSearchDataAjax(getEle("page").value, getEle("orderNextPage").value);
	}

}
function nextOrderPage() {

	var nextPageNum = parseInt(getEle("orderPage").value) + 1;
	if (nextPageNum <= parseInt(getEle("orderMaxPage").value)) {
		page = nextPageNum;
	} else {
		page = getEle("orderMaxPage").value;
	}

	getEle("orderNextPage").value = page;
	// getEle("displayOrderNowPage").value = getEle("orderNextPage").value;

	if (getEle("orderPage").value != getEle("orderNextPage").value) {
		getSearchDataAjax(getEle("page").value, getEle("orderNextPage").value);
	}
}
function nextOrderMaxPage() {
	var maxPage = getEle("orderMaxPage").value;
	getEle("orderNextPage").value = maxPage;
	// getEle("displayOrderNowPage").value = getEle("orderNextPage").value;

	if (getEle("orderPage").value != getEle("orderNextPage").value) {
		getSearchDataAjax(getEle("page").value, getEle("orderNextPage").value);
	}
}
function checkOrderPageText(id, value) {
	getEle(id).value = checkInputNumberVal(value);
}
function changOrderPageBnt() {
	var pageNumber = getEle("changeOrderPageText").value;
	var maxPage = getEle("orderMaxPage").value;

	if (pageNumber > 0 && pageNumber != "" && pageNumber != null && pageNumber <= maxPage) {
		getEle("orderNextPage").value = pageNumber;
		// getEle("displayOrderNowPage").value = getEle("orderNextPage").value;
		getSearchDataAjax(getEle("page").value, getEle("orderNextPage").value);
	}

}

function showSearchList() {
	var count = 25;
	var str = [];
	str.push('<ul> \n');
	str.push('<li class = "break-line" >會員帳號：<input type="text" id = "searchAccName" maxlength="21" onkeyup="checkSearchAccName();"></input> \n');
	str.push('審核人員：<input type="text" id = "searchCustomerService" maxlength="21" onkeyup="checkSearchCustomerService();"></input> \n');
	str.push('<select id = "status"></select> \n');
	str
			.push('訂單時間：<input type = "text" id = "orderCreateFirstDatetime" size="10px" maxlength="10" onclick ="newCalendar(this, this.id,1)"  readonly> \n');
	str.push('到<input type = "text" id = "orderCreateLastDatetime" size="10px" maxlength="10" onclick ="newCalendar(this, this.id,2)"  readonly> \n');
	str.push('<span id = "checkDatetimeMessage"></span> \n');
	str
			.push('<select id="changOrderSelectCount"><option value="25">25筆</option><option value="50">50筆</option><option value="75">75筆</option><option value="100">100筆</option></select> \n');
	str.push('<button id = "searchBnt">搜尋</button>  \n');
	str.push('<button onclick = "resetSearchData()">重置</button> \n');
	str.push('<button id = "makeUp" onclick="showMakeUp();" style = "display:none;">補單</button></li> \n');
	str.push('<li class="media-control">  \n ');
	str.push('<a href="javascript:void(0);" onclick="firstOrderPage();" class="backward"></a>  \n ');
	str.push('<a href="javascript:void(0);"  onclick = "previousOrderPage();" class="backward-fast"></a>  \n ');
	str.push('<span>總頁數：  \n ');
	str.push('<i id = "displayOrderNowPage"></i>  \n ');
	str.push('<span>/</span><i id = "displayOrderMaxPage"></i>頁</span>  \n ');
	str.push('<a href="javascript:void(0);" onclick = "nextOrderPage();" class="forward"></a>  \n ');
	str.push('<a href="javascript:void(0);" onclick = "nextOrderMaxPage();" class="forward-fast"></a>  \n ');
	str.push('</li>  \n ');
	str
			.push('<li class="media-control"><input id="changeOrderPageText" onKeyup = "checkOrderPageText(this.id,this.value);" type="text" class="margin-fix-1">頁\n');
	str.push('<button onclick = "changOrderPageBnt();">跳轉</button> \n');
	str.push('<button onclick = "confromShowOrderLog(' + LOG_ACTION_ADD_RECHARGE_ORDER_CHANGE + ');">紀錄</button> \n');
	str.push('</li></ul>  \n ');

	getEle("searchArea").innerHTML = str.join("");
}

function checkDateTime() {
	var lastDateTime = getEle("orderCreateLastDatetime").value;
	var firstDateTime = getEle("orderCreateFirstDatetime").value;
	var nowDate = new Date();
	var MonthAgoDate = new Date(nowDate.getTime() - (60 * 60 * 24 * 30 * 1000)).getFromFormat('yyyy/MM/dd');
	var formattedDate = nowDate.getFromFormat('yyyy/MM/dd');
	var formattedlastDateTime = "";
	var formattedFirstDateTime = "";

	if (firstDateTime != null && firstDateTime != undefined && firstDateTime != "") {
		formattedFirstDateTime = new Date(firstDateTime).getFromFormat('yyyy/MM/dd');
	}

	if (lastDateTime != null && lastDateTime != undefined && lastDateTime != "") {
		formattedlastDateTime = new Date(lastDateTime).getFromFormat('yyyy/MM/dd');
	}

	if (formattedlastDateTime != "" && formattedFirstDateTime == "") {
		formattedFirstDateTime = formattedlastDateTime;
	} else if (formattedlastDateTime == "" && formattedFirstDateTime != "") {
		formattedlastDateTime = formattedFirstDateTime;
	}

	if (lastDateTime != null && lastDateTime != undefined && lastDateTime != "" && firstDateTime != null && firstDateTime != undefined
			&& firstDateTime != "") {

		if (new Date(formattedlastDateTime).getTime() > nowDate.getTime()) {
			formattedlastDateTime = new Date(nowDate).getFromFormat('yyyy/MM/dd');
			getEle("orderCreateLastDatetime").value = formattedlastDateTime;
		} else if (new Date(formattedlastDateTime).getTime() < new Date(MonthAgoDate).getTime()) {
			formattedlastDateTime = MonthAgoDate;
			getEle("orderCreateLastDatetime").value = formattedlastDateTime;
		}

		if (new Date(formattedFirstDateTime).getTime() < new Date(MonthAgoDate).getTime()) {
			formattedFirstDateTime = MonthAgoDate;
			getEle("orderCreateFirstDatetime").value = formattedFirstDateTime;
		} else if (new Date(formattedFirstDateTime).getTime() > nowDate.getTime()) {
			formattedFirstDateTime = new Date(nowDate).getFromFormat('yyyy/MM/dd');
			getEle("orderCreateFirstDatetime").value = formattedFirstDateTime;
		}

	}

	if (new Date(formattedFirstDateTime).getTime() > new Date(formattedlastDateTime).getTime()) {
		return false;
	} else {
		return true;
	}

}

function checkDateTimeNoLimit() {
	var lastDateTime = getEle("orderCreateLastDatetime").value;
	var firstDateTime = getEle("orderCreateFirstDatetime").value;
	var nowDate = new Date();
	var formattedDate = nowDate.getFromFormat('yyyy/MM/dd');
	var formattedlastDateTime = "";
	var formattedFirstDateTime = "";

	if (firstDateTime != null && firstDateTime != undefined && firstDateTime != "") {
		formattedFirstDateTime = new Date(firstDateTime).getFromFormat('yyyy/MM/dd');
	}

	if (lastDateTime != null && lastDateTime != undefined && lastDateTime != "") {
		formattedlastDateTime = new Date(lastDateTime).getFromFormat('yyyy/MM/dd');
	}

	if (formattedlastDateTime != "" && formattedFirstDateTime == "") {
		formattedFirstDateTime = formattedlastDateTime;
	} else if (formattedlastDateTime == "" && formattedFirstDateTime != "") {
		formattedlastDateTime = formattedFirstDateTime;
	}

	if (lastDateTime != null && lastDateTime != undefined && lastDateTime != "" && firstDateTime != null && firstDateTime != undefined
			&& firstDateTime != "") {

		if (new Date(formattedlastDateTime).getTime() > nowDate.getTime()) {
			formattedlastDateTime = new Date(nowDate).getFromFormat('yyyy/MM/dd');
			getEle("orderCreateLastDatetime").value = formattedlastDateTime;
		}

		if (new Date(formattedFirstDateTime).getTime() > nowDate.getTime()) {
			formattedFirstDateTime = new Date(nowDate).getFromFormat('yyyy/MM/dd');
			getEle("orderCreateFirstDatetime").value = formattedFirstDateTime;
		}

	}

	if (new Date(formattedFirstDateTime).getTime() > new Date(formattedlastDateTime).getTime()) {
		return false;
	} else {
		return true;
	}

}

function changeSelectCount(val) {
	getEle("orderSelectCount").value = val;
	getEle("orderPage").value = 1;
	getEle("orderMaxPage").value = 0;
	getEle("orderNextPage").value = 1;
	getOrderReviewDataAjax();
}

function checkSearchAccName() {
	getEle("searchAccName").value = checkAccount(getEle("searchAccName").value);
}

function checkSearchCustomerService() {
	getEle("searchCustomerService").value = checkAccount(getEle("searchCustomerService").value);
}

function onClickOrderRechargeCarrOut() {
	getEle("orderSort").value = 0;
	getEle("orderAes").value = 0;
	getEle("orderSelectCount").value = 25;
	getEle("orderPage").value = 1;
	getEle("orderMaxPage").value = 0;
	getEle("orderNextPage").value = 1;

	getOrderRechargeCarryOutDataAjax();

	showSearchList();
	searchBnt.onclick = function() {
		getEle("orderSort").value = 0;
		getEle("orderAes").value = 0;

		getEle("orderPage").value = 1;
		getEle("orderMaxPage").value = 0;
		getEle("orderNextPage").value = 1;
		getEle("orderSelectCount").value = getEle("changOrderSelectCount").value;

		if (checkDateTime()) {
			searchRechargeCarryOutAjax();
		}
	};
	removeAllOption("status");
	addOptionNoDup("status", "請選擇", 0);
	addOptionNoDup("status", "審核通過", 7);
	addOptionNoDup("status", "拒絕", 8);
	addOptionNoDup("status", "異常", 9);
}

function onClickOrderRechargeUndone() {
	getEle("orderSort").value = 0;
	getEle("orderAes").value = 0;
	getEle("orderSelectCount").value = 25;
	getEle("orderPage").value = 1;
	getEle("orderMaxPage").value = 0;
	getEle("orderNextPage").value = 1;

	getOrderRechargeUndoneDataAjax();

	showSearchList();
	getEle("makeUp").style.display = "";
	searchBnt.onclick = function() {
		getEle("orderSort").value = 0;
		getEle("orderAes").value = 0;

		getEle("orderPage").value = 1;
		getEle("orderMaxPage").value = 0;
		getEle("orderNextPage").value = 1;
		getEle("orderSelectCount").value = getEle("changOrderSelectCount").value;
		if (checkDateTimeNoLimit()) {
			searchRechargeUndoneAjax();
		}
	};
	removeAllOption("status");
	addOptionNoDup("status", "請選擇", 0);
	addOptionNoDup("status", "待審核", 1);
	addOptionNoDup("status", "審核中", 2);
	addOptionNoDup("status", "複審核", 3);
	addOptionNoDup("status", "複審中", 4);
}

function onClickOrderWithdrawalCarryOut() {
	getEle("orderSort").value = 0;
	getEle("orderAes").value = 0;
	getEle("orderSelectCount").value = 25;
	getEle("orderPage").value = 1;
	getEle("orderMaxPage").value = 0;
	getEle("orderNextPage").value = 1;

	getOrderWithdrawalCarryOutDataAjax();
	showSearchList();
	searchBnt.onclick = function() {
		getEle("orderSort").value = 0;
		getEle("orderAes").value = 0;

		getEle("orderPage").value = 1;
		getEle("orderMaxPage").value = 0;
		getEle("orderNextPage").value = 1;
		getEle("orderSelectCount").value = getEle("changOrderSelectCount").value;
		if (checkDateTimeNoLimit()) {
			searchWithdrawalCarryOutAjax();
		}
	};
	removeAllOption("status");
	addOptionNoDup("status", "請選擇", 0);
	addOptionNoDup("status", "審核通過", 6);
	addOptionNoDup("status", "拒絕", 7);
	addOptionNoDup("status", "異常", 8);
}

function onClickOrderWithdrawalUndone() {
	getEle("orderSort").value = 0;
	getEle("orderAes").value = 0;
	getEle("orderSelectCount").value = 25;
	getEle("orderPage").value = 1;
	getEle("orderMaxPage").value = 0;
	getEle("orderNextPage").value = 1;

	getOrderWithdrawalUndoneDataAjax();

	showSearchList();
	searchBnt.onclick = function() {
		getEle("orderSort").value = 0;
		getEle("orderAes").value = 0;

		getEle("orderPage").value = 1;
		getEle("orderMaxPage").value = 0;
		getEle("orderNextPage").value = 1;

		getEle("orderSelectCount").value = getEle("changOrderSelectCount").value;
		if (checkDateTime()) {
			searchWithdrawalUndoneAjax();
		}
	};
	removeAllOption("status");
	addOptionNoDup("status", "請選擇", 0);
	addOptionNoDup("status", "待審核", 1);
	addOptionNoDup("status", "審核中", 2);
}

function onClickOrderReview() {
	getEle("orderSort").value = 0;
	getEle("orderAes").value = 0;
	getEle("orderSelectCount").value = 25;
	getEle("orderPage").value = 1;
	getEle("orderMaxPage").value = 0;
	getEle("orderNextPage").value = 1;

	getOrderReviewDataAjax();
	var str = '<ul> \n'
			+ '<li><select id="changOrderSelectCount" onchange = "changeSelectCount(this.value);"><option value="25">25筆</option><option value="50">50筆</option><option value="75">75筆</option><option value="100">100筆</option></select></li> \n'
			+ '<li class="media-control"> \n'
			+ '<a href="javascript:void(0);" onclick="firstOrderPage();" class="backward"></a> \n'
			+ '<a href="javascript:void(0);"  onclick = "previousOrderPage();" class="backward-fast"></a> \n'
			+ '<span>總頁數：<i id = "displayOrderNowPage"></i> \n'
			+ '<span>/</span><i id = "displayOrderMaxPage"></i>頁</span> \n'
			+ '<a href="javascript:void(0);" onclick = "nextOrderPage();" class="forward"></a> \n'
			+ '<a href="javascript:void(0);" onclick = "nextOrderMaxPage();" class="forward-fast"></a> \n'
			+ '</li> \n'
			+ '<li class="media-control"><input id="changeOrderPageText" onKeyup = "checkOrderPageText(this.id,this.value);" type="text" class="margin-fix-1">頁\n<button onclick = "changOrderPageBnt( \n'
			+ ');">跳轉</button></li></ul> \n';
	getEle("searchArea").innerHTML = str;
}

function getOrderRechargeCarryOutDataAjax() {
	getEle("searchOrderInfo").value = "";
	getEle("research").value = "";

	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&count=" + getEle("orderSelectCount").value;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleGetOrderRechargeCarryOutData;
		XHR.open("POST", "./Order!getOrderRechargeCarryOutData.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpStr + "&page=" + getEle("orderPage").value + "&nextPage=" + getEle("orderNextPage").value);
		enableLoadingPage();
		getEle("searchOrderInfo").value = tmpStr;
		getEle("searchOrderAction").value = "./Order!getOrderRechargeCarryOutData.php";
		getEle("handleName").value = "handleGetOrderRechargeCarryOutData";
	}
}

function handleGetOrderRechargeCarryOutData() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					getEle("orderJson").value = XHR.responseText;
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (json.orderMaxPage != null && json.orderMaxPage != undefined) {
							getEle("orderMaxPage").value = json.orderMaxPage;
							getEle("displayOrderMaxPage").innerHTML = json.orderMaxPage;

						}
						if (json.page != null && json.page != undefined) {
							getEle("orderPage").value = json.page;
							getEle("displayOrderNowPage").innerHTML = json.page;
						}
						// showRechargeCarryOut();
						showOrderTable(0, 1);
					}

				}
			} catch (error) {
				console_Log("handleGetOrderRechargeCarryOutData error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function getOrderRechargeUndoneDataAjax() {
	getEle("searchOrderInfo").value = "";
	getEle("research").value = "";

	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&count=" + getEle("orderSelectCount").value;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleGetOrderRechargeUndoneData;
		XHR.open("POST", "./Order!getOrderRechargeUndoneData.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpStr + "&page=" + getEle("orderPage").value + "&nextPage=" + getEle("orderNextPage").value);
		enableLoadingPage();
		getEle("searchOrderInfo").value = tmpStr;
		getEle("searchOrderAction").value = "./Order!getOrderRechargeUndoneData.php";
		getEle("handleName").value = "handleGetOrderRechargeUndoneData";
	}
}

function handleGetOrderRechargeUndoneData() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					getEle("orderJson").value = XHR.responseText;
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (json.orderMaxPage != null && json.orderMaxPage != undefined) {
							getEle("orderMaxPage").value = json.orderMaxPage;
							getEle("displayOrderMaxPage").innerHTML = json.orderMaxPage;
						}
						if (json.page != null && json.page != undefined) {
							getEle("orderPage").value = json.page;
							getEle("displayOrderNowPage").innerHTML = json.page;
						}
						// showRechargeUndone();
						showOrderTable(0, 2);
					}
				}
			} catch (error) {
				console_Log("handleGetOrderRechargeUndoneData error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function getOrderWithdrawalCarryOutDataAjax() {
	getEle("searchOrderInfo").value = "";
	getEle("research").value = "";

	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&count=" + getEle("orderSelectCount").value;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleGetOrderWithdrawalCarryOutData;
		XHR.open("POST", "./Order!getOrderWithdrawalCarryOutData.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpStr + "&page=" + getEle("orderPage").value + "&nextPage=" + getEle("orderNextPage").value);
		enableLoadingPage();
		getEle("searchOrderInfo").value = tmpStr;
		getEle("searchOrderAction").value = "./Order!getOrderWithdrawalCarryOutData.php";
		getEle("handleName").value = "handleGetOrderWithdrawalCarryOutData";
	}
}

function handleGetOrderWithdrawalCarryOutData() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					getEle("orderJson").value = XHR.responseText;
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (json.orderMaxPage != null && json.orderMaxPage != undefined) {
							getEle("orderMaxPage").value = json.orderMaxPage;
							getEle("displayOrderMaxPage").innerHTML = json.orderMaxPage;
						}
						if (json.page != null && json.page != undefined) {
							getEle("orderPage").value = json.page;
							getEle("displayOrderNowPage").innerHTML = json.page;
						}
						// showWithdrawalCarryOut();
						showOrderTable(0, 3);
					}
				}
			} catch (error) {
				console_Log("handleGetOrderWithdrawalCarryOutData error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function getOrderWithdrawalUndoneDataAjax() {
	getEle("searchOrderInfo").value = "";
	getEle("research").value = "";

	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&count=" + getEle("orderSelectCount").value;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleGetOrderWithdrawalUndoneData;
		XHR.open("POST", "./Order!getOrderWithdrawalUndoneData.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpStr + "&page=" + getEle("orderPage").value + "&nextPage=" + getEle("orderNextPage").value);
		enableLoadingPage();
		getEle("searchOrderInfo").value = tmpStr;
		getEle("searchOrderAction").value = "./Order!getOrderWithdrawalUndoneData.php";
		getEle("handleName").value = "handleGetOrderWithdrawalUndoneData";
	}
}

function handleGetOrderWithdrawalUndoneData() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					getEle("orderJson").value = XHR.responseText;
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (json.orderMaxPage != null && json.orderMaxPage != undefined) {
							getEle("orderMaxPage").value = json.orderMaxPage;
							getEle("displayOrderMaxPage").innerHTML = json.orderMaxPage;
						}
						if (json.page != null && json.page != undefined) {
							getEle("orderPage").value = json.page;
							getEle("displayOrderNowPage").innerHTML = json.page;
						}
					}
					// showWithdrawalUndone();
					showOrderTable(0, 4);
				}
			} catch (error) {
				console_Log("handleGetOrderWithdrawalUndoneData error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

// 複查訂單
function getOrderReviewDataAjax() {
	getEle("searchOrderInfo").value = "";
	getEle("research").value = "";

	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&count=" + getEle("orderSelectCount").value;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleGetOrderReviewData;
		XHR.open("POST", "./Order!getOrderReviewData.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpStr + "&page=" + getEle("orderPage").value + "&nextPage=" + getEle("orderNextPage").value);
		enableLoadingPage();
		getEle("searchOrderInfo").value = tmpStr;
		getEle("searchOrderAction").value = "./Order!getOrderReviewData.php";
		getEle("handleName").value = "handleGetOrderReviewData";
	}
}
function handleGetOrderReviewData() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					getEle("orderJson").value = XHR.responseText;
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (typeof json.orderMaxPage != "undefined" && json.orderMaxPage != null) {
							getEle("orderMaxPage").value = json.orderMaxPage;
							getEle("displayOrderMaxPage").innerHTML = json.orderMaxPage;
						}
						if (typeof json.page != "undefined" && json.page != null) {
							getEle("orderPage").value = json.page;
							getEle("displayOrderNowPage").innerHTML = json.page;
						}
					}
					// showReview();
					showOrderTable(0, 5);
				}
			} catch (error) {
				console_Log("handleGetOrderReviewData error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function showMakeUp() {
	selecthrList = "";
	for (var i = 0; i <= 23; i++) {
		if (i < 10) {
			selecthrList += '<option value = "0' + i + '">' + '0' + i + '</option>';
		} else {
			selecthrList += '<option value = "' + i + '">' + '' + i + '</option>';
		}
	}
	selectminList = "";
	for (var i = 0; i <= 59; i++) {
		if (i < 10) {
			selectminList += '<option value = "0' + i + '">' + '0' + i + '</option>';
		} else {
			selectminList += '<option value = "' + i + '">' + '' + i + '</option>';
		}
	}

	var str1 = '<div class="modal-content width-percent-460 margin-fix-5">';
	var str2 = '<span class="close" onclick="onClickCloseModal();">&times;</span>';
	var str3 = '<h3>補單</h3>';
	var str4 = '<table class="first-th-fix-1">';
	var str5 = '<tr><th>會員帳號</th><td><input type="text" class="margin-fix-2 text-left" id="inputMemberAccName" placeholder="此欄位必填" onchange = "checkMemberAccName(this.value);" maxlength="20"><span id = "checkInputMemberAccNameIcon"></span><span id = "checkInputMemberAccName"></span></td></tr>';
	var str6 = '<tr><th>充值金額</th><td><input type="text" class="margin-fix-2 text-left" id="inputAmount" placeholder="此欄位必填" onkeyup = "checkAmount();"><span id = "checkInputAmountIcon" maxlength="10"></span><span id = "checkInputAmount"></td></tr>';
	var str7 = '<tr><th>帳戶名稱</th><td><input type="text" class="margin-fix-2 text-left" id="inputBankAccName" onkeyup = "checkInputBankAccName(this.value);" maxlength="20"></td></tr>';
	var str8 = '<tr><th>卡號</th><td><input type="text" class="margin-fix-2 text-left" id="inputBankAcc" onKeyUp="this.value=chkInputBankNo(this.value);" maxlength="20" onMouseOut="this.value=onchgInputBankNo(this.value);" onChange="this.value=onchgInputBankNo(this.value);"><span id = "checkInputBankAcc"></td></tr>';
	var str9 = '<tr><th>銀行</th><td><input type="text" class="margin-fix-2 text-left" id="inputBank" onkeyup = "checkInputBank(this.value);" maxlength="20"></td></tr>';
	var str10 = '<tr><th>附言</th><td><input type="text" class="margin-fix-2 text-left" id="inputNote" onkeyup = "checkInputNote(this.value);" maxlength="6"></td></tr>';
	var str11 = '<tr><th>銀行單號</th><td><input type="text" class="margin-fix-2 text-left" id="inputBankSid" onkeyup = "checkInputBankSid();" maxlength="20"></td></tr>';
	var str12 = '<tr><th>匯款金額</th><td><input type="text" class="margin-fix-2 text-left" maxlength="10" id="inputBankCheckAmount" onkeyup = "checkBankAmount();" maxlength="20"><span id = "checkInputBankCheckAmount"></td></tr>';
	var str13 = '<tr><th>銀行到帳時間</th><td><input type="text" class="margin-fix-2 text-left" id="inputBankDepositTime" onclick="newCalendar(this, this.id,1)" readonly>'
			+ '<select id="hrList">'
			+ selecthrList
			+ '</select>:'
			+ '<select id="minList">'
			+ selectminList
			+ '</select>'
			+ '<span id = "checkInputBankAccNameIcon"></span><span id = "checkInputBankAccName"></td></tr>';

	var str14 = '<tr><th>備註</th><td><textarea id="inputRemark" placeholder="請在此填入補單原因"></textarea></td></tr>';
	var str15 = '</table><div class="btn-area">';
	var str16 = '<button class="btn-lg btn-orange" onclick="conformAddOrder();">確定</button>\n<button class="btn-lg btn-gray" onclick="onClickCloseModal();">取消</button>';
	var str17 = '</div>';
	var addorderHtml = "";
	addorderHtml = addorderHtml.concat(str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14, str15, str16, str17);
	onClickOpenModal(addorderHtml);
}
function checkInputBankAccName(value) {
	getEle("inputBankAccName").value = checkInputNameOutVal(getEle("inputBankAccName").value);
}
function checkInputBank(value) {
	getEle("inputBank").value = checkInputNameOutVal(getEle("inputBank").value);
}
function checkInputNote(value) {
	getEle("inputNote").value = checkInputTextVal(getEle("inputNote").value);
}
function checkInputBankSid(value) {
	getEle("inputBankSid").value = checkInputTextVal(getEle("inputBankSid").value);
}
function checkMemberAccName(value) {
	if (value.length >= 4 && value.length <= 20) {
		checkMemberAccNameAjax(value);
	} else {
		getEle("checkInputMemberAccNameIcon").classList.remove("icon-pass");
		getEle("checkInputMemberAccNameIcon").classList.add("icon-fail");
		getEle("checkInputMemberAccName").innerHTML = "帳號格式錯誤!";
	}
}
function checkAmount() {
	getEle("inputAmount").value = checkInputNumberVal(getEle("inputAmount").value);
	if (parseInt(getEle("inputAmount").value) > 0) {
		getEle("checkInputAmountIcon").classList.remove("icon-fail");
		getEle("checkInputAmountIcon").classList.add("icon-pass");
		getEle("checkInputAmount").innerHTML = "";
	} else {
		getEle("checkInputAmountIcon").classList.remove("icon-pass");
		getEle("checkInputAmountIcon").classList.add("icon-fail");
		getEle("checkInputAmount").innerHTML = " 請輸入充值金額 ";
	}
}
function checkBankAmount() {
	getEle("inputBankCheckAmount").value = checkInputNumberVal(getEle("inputBankCheckAmount").value);
}
function checkMemberAccNameAjax(str) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&acc_name=" + str;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleCheckMemberAccName;
		XHR.open("POST", "./Order!checkMemberAccName.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpstr);
	}
}
function handleCheckMemberAccName() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						var bool = json.result
						if (bool == 0) {
							getEle("checkInputMemberAccNameIcon").classList.remove("icon-pass");
							getEle("checkInputMemberAccNameIcon").classList.add("icon-fail");
							getEle("checkInputMemberAccName").innerHTML = " 此帳號不存在";
						} else {
							getEle("checkInputMemberAccNameIcon").classList.remove("icon-fail");
							getEle("checkInputMemberAccNameIcon").classList.add("icon-pass");
							getEle("checkInputMemberAccName").innerHTML = " ";
						}
					}
				}
			} catch (error) {
				console_Log("handleCheckMemberAccName error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function conformAddOrder() {
	var str = '';
	var member_acc_name = getEle("inputMemberAccName").value;
	var amount = getEle("inputAmount").value;
	var bank_acc_name = getEle("inputBankAccName").value;
	var bank_acc = getEle("inputBankAcc").value;
	var bank = getEle("inputBank").value;
	var note = getEle("inputNote").value;
	var bank_sid = getEle("inputBankSid").value;
	var bank_check_amount = getEle("inputBankCheckAmount").value;
	if (getEle("inputBankDepositTime").value != '') {
		var bank_deposit_datetime = getEle("inputBankDepositTime").value + " " + getEle("hrList").value + ":" + getEle("minList").value;
	} else {
		var bank_deposit_datetime = '';
	}

	var remark = getEle("inputRemark").value;
	var str = '';
	str += "member_acc_name=" + member_acc_name + "&amount=" + amount + "&bank_acc_name=" + bank_acc_name + "&bank_acc=" + bank_acc + "&bank=" + bank
			+ "&note=" + note + "&bank_sid=" + bank_sid + "&bank_check_amount=" + bank_check_amount + "&bank_deposit_datetime="
			+ bank_deposit_datetime + "&remark=" + remark;
	if (member_acc_name != '' && amount != '' && remark != '' && getEle("checkInputMemberAccNameIcon").classList.contains("icon-pass")
			&& getEle("checkInputAmountIcon").classList.contains("icon-pass")) {
		insertAddOrderAjax(str);
	} else {
		onCheckModelPage2("必填欄位內容錯誤或尚未輸入");
	}
}
function insertAddOrderAjax(str) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&" + str;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleInsertAddOrder;
		XHR.open("POST", "./Order!addOrder.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();
	}
}
function handleInsertAddOrder() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (json.message == "fail") {
							onCheckModelPage2("新增水單失敗");
						} else if (json.message == "success") {
							onClickCloseModal();
							onCheckModelPage2("新增水單成功");
						}
					}
				}
			} catch (error) {
				console_Log("handleInsertAddOrder error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
				if (typeof json.message != "undefined" && json.message != null && json.message == "success") {
					getSearchDataAjax(getEle("orderPage").value, getEle("orderNextPage").value);
				}
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function searchRechargeCarryOutAjax() {

	var searchAccName = checkNull(getEle("searchAccName").value);
	var searchAudiAccName = checkNull(getEle("searchCustomerService").value);
	var orderCreateFirstDatetime = checkNull(getEle("orderCreateFirstDatetime").value);
	var orderCreateLastDatetime = checkNull(getEle("orderCreateLastDatetime").value);
	var status = checkNull(getEle("status").value);

	var OrderSelectCount = getEle("orderSelectCount").value;

	getEle("ReviewPassOrder").value = "";
	getEle("carryoutOrder").value = "";
	getEle("auditOrder").value = "";
	getEle("orderJson").value = "";

	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&accName=" + searchAccName + "&status=" + status + "&audiAccName="
				+ searchAudiAccName + "&firstDatetime=" + orderCreateFirstDatetime + "&lastDatetime=" + orderCreateLastDatetime + "&count="
				+ OrderSelectCount;
		if (getEle("research").value == '1') {
			tmpStr = getEle("searchOrderInfo").value;
		}
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleSearchRechargeCarryOut;
		XHR.open("POST", "./Order!searchOrderRechargeCarryOutData.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpStr + "&page=" + getEle("orderPage").value + "&nextPage=" + getEle("orderNextPage").value);
		enableLoadingPage();
		if (getEle("research").value != '1') {
			getEle("searchOrderInfo").value = tmpStr;
		} else {
			getEle("research").value = '0';
		}
		getEle("searchOrderAction").value = "./Order!searchOrderRechargeCarryOutData.php";
		getEle("handleName").value = "handleSearchRechargeCarryOut";
	}

}

function handleSearchRechargeCarryOut() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					getEle("orderJson").value = XHR.responseText;
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (typeof json.orderMaxPage != "undefined" && json.orderMaxPage != null) {
							getEle("orderMaxPage").value = json.orderMaxPage;
							getEle("displayOrderMaxPage").innerHTML = json.orderMaxPage;
						}
						if (typeof json.page != "undefined" && json.page != null) {
							getEle("orderPage").value = json.page;
							getEle("displayOrderNowPage").innerHTML = json.page;
						}
					}
					showRechargeCarryOut();
				}
			} catch (error) {
				console_Log("handleSearchRechargeCarryOut error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function searchRechargeUndoneAjax() {

	var searchAccName = checkNull(getEle("searchAccName").value);
	var searchAudiAccName = checkNull(getEle("searchCustomerService").value);
	var orderCreateFirstDatetime = checkNull(getEle("orderCreateFirstDatetime").value);
	var orderCreateLastDatetime = checkNull(getEle("orderCreateLastDatetime").value);
	var status = checkNull(getEle("status").value);

	var OrderSelectCount = getEle("orderSelectCount").value;

	getEle("ReviewPassOrder").value = "";
	getEle("carryoutOrder").value = "";
	getEle("auditOrder").value = "";
	getEle("orderJson").value = "";

	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&accName=" + searchAccName + "&status=" + status + "&audiAccName="
				+ searchAudiAccName + "&firstDatetime=" + orderCreateFirstDatetime + "&lastDatetime=" + orderCreateLastDatetime + "&count="
				+ OrderSelectCount;
		if (getEle("research").value == '1') {
			tmpStr = getEle("searchOrderInfo").value;
		}
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleSearchRechargeUndone;
		XHR.open("POST", "./Order!searchOrderRechargeUndoneData.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpStr + "&page=" + getEle("orderPage").value + "&nextPage=" + getEle("orderNextPage").value);
		enableLoadingPage();
		if (getEle("research").value != '1') {
			getEle("searchOrderInfo").value = tmpStr;
		} else {
			getEle("research").value = '0';
		}
		getEle("searchOrderAction").value = "./Order!searchOrderRechargeUndoneData.php";
		getEle("handleName").value = "handleSearchRechargeUndone";
	}

}

function handleSearchRechargeUndone() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					getEle("orderJson").value = XHR.responseText;
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (typeof json.orderMaxPage != "undefined" && json.orderMaxPage != null) {
							getEle("orderMaxPage").value = json.orderMaxPage;
							getEle("displayOrderMaxPage").innerHTML = json.orderMaxPage;
						}
						if (typeof json.page != "undefined" && json.page != null) {
							getEle("orderPage").value = json.page;
							getEle("displayOrderNowPage").innerHTML = json.page;
						}
						showRechargeUndone();
					}
				}
			} catch (error) {
				console_Log("handleSearchRechargeUndone error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function searchWithdrawalCarryOutAjax() {

	var searchAccName = checkNull(getEle("searchAccName").value);
	var searchAudiAccName = checkNull(getEle("searchCustomerService").value);
	var orderCreateFirstDatetime = checkNull(getEle("orderCreateFirstDatetime").value);
	var orderCreateLastDatetime = checkNull(getEle("orderCreateLastDatetime").value);
	var status = checkNull(getEle("status").value);

	var OrderSelectCount = getEle("orderSelectCount").value;

	getEle("ReviewPassOrder").value = "";
	getEle("carryoutOrder").value = "";
	getEle("auditOrder").value = "";
	getEle("orderJson").value = "";

	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&accName=" + searchAccName + "&status=" + status + "&audiAccName="
				+ searchAudiAccName + "&firstDatetime=" + orderCreateFirstDatetime + "&lastDatetime=" + orderCreateLastDatetime + "&count="
				+ OrderSelectCount;
		if (getEle("research").value == '1') {
			tmpStr = getEle("searchOrderInfo").value;
		}
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleSearchWithdrawalCarryOut;
		XHR.open("POST", "./Order!searchOrderWithdrawalCarryOutData.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpStr + "&page=" + getEle("orderPage").value + "&nextPage=" + getEle("orderNextPage").value);
		enableLoadingPage();
		if (getEle("research").value != '1') {
			getEle("searchOrderInfo").value = tmpStr;
		} else {
			getEle("research").value = '0';
		}
		getEle("searchOrderAction").value = "./Order!searchOrderWithdrawalCarryOutData.php";
		getEle("handleName").value = "handleSearchWithdrawalCarryOut";
	}

}

function handleSearchWithdrawalCarryOut() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					getEle("orderJson").value = XHR.responseText;
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (typeof json.orderMaxPage != "undefined" && json.orderMaxPage != null) {
							getEle("orderMaxPage").value = json.orderMaxPage;
							getEle("displayOrderMaxPage").innerHTML = json.orderMaxPage;
						}
						if (typeof json.page != "undefined" && json.page != null) {
							getEle("orderPage").value = json.page;
							getEle("displayOrderNowPage").innerHTML = json.page;
						}
					}
					showWithdrawalCarryOut();
				}
			} catch (error) {
				console_Log("handleSearchWithdrawalCarryOut error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function searchWithdrawalUndoneAjax() {

	var searchAccName = checkNull(getEle("searchAccName").value);
	var searchAudiAccName = checkNull(getEle("searchCustomerService").value);
	var orderCreateFirstDatetime = checkNull(getEle("orderCreateFirstDatetime").value);
	var orderCreateLastDatetime = checkNull(getEle("orderCreateLastDatetime").value);
	var status = checkNull(getEle("status").value);

	var OrderSelectCount = getEle("orderSelectCount").value;

	getEle("ReviewPassOrder").value = "";
	getEle("carryoutOrder").value = "";
	getEle("auditOrder").value = "";
	getEle("orderJson").value = "";

	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&accName=" + searchAccName + "&status=" + status + "&audiAccName="
				+ searchAudiAccName + "&firstDatetime=" + orderCreateFirstDatetime + "&lastDatetime=" + orderCreateLastDatetime + "&count="
				+ OrderSelectCount;
		if (getEle("research").value == '1') {
			tmpStr = getEle("searchOrderInfo").value;
		}
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleSearchWithdrawalUndone;
		XHR.open("POST", "./Order!searchOrderWithdrawalUndoneData.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpStr + "&page=" + getEle("orderPage").value + "&nextPage=" + getEle("orderNextPage").value);
		enableLoadingPage();
		if (getEle("research").value != '1') {
			getEle("searchOrderInfo").value = tmpStr;
		} else {
			getEle("research").value = '0';
		}
		getEle("searchOrderAction").value = "./Order!searchOrderWithdrawalUndoneData.php";
		getEle("handleName").value = "handleSearchWithdrawalUndone";
	}

}

function handleSearchWithdrawalUndone() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					getEle("orderJson").value = XHR.responseText;
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (typeof json.orderMaxPage != "undefined" && json.orderMaxPage != null) {
							getEle("orderMaxPage").value = json.orderMaxPage;
							getEle("displayOrderMaxPage").innerHTML = json.orderMaxPage;
						}
						if (typeof json.page != "undefined" && json.page != null) {
							getEle("orderPage").value = json.page;
							getEle("displayOrderNowPage").innerHTML = json.page;
						}
						showWithdrawalUndone();
					}
				}
			} catch (error) {
				console_Log("handleSearchWithdrawalUndone error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function resetSearchData() {
	getEle("searchAccName").value = "";
	getEle("searchCustomerService").value = "";
	getEle("orderCreateFirstDatetime").value = "";
	getEle("orderCreateLastDatetime").value = "";
	getEle("changeOrderPageText").value = "";
	selectItemByValue(getEle("status"), 0);
}

function showReviewPassOrder() {
	var json = JSON.parse(getEle("ReviewPassOrder").value);
	var str = '<div class="modal-content width-percent-460 margin-fix-5"><span class="close" onclick="onClickCloseModal();">×</span><table class="first-th-fix-1">'
			+ '<h3>複審</h3>' + '<tbody><tr><th>單號</th><td>'
			+ json.orderId
			+ '</td></tr><tr><th>訂單狀態</th><td>'
			+ order.orderStatus[json.status]
			+ '</td></tr><tr><th>會員帳號</th><td>'
			+ json.accName
			+ '</td></tr><tr><th>金額</th><td>'// 充值or提現
			+ json.amount
			+ '</td>'
			+ '</tr><tr><th>帳戶名稱</th><td>'
			+ json.bankAccName
			+ '</td></tr><tr><th>卡號</th><td>'
			+ json.bankAcc
			+ '</td></tr><tr><th>銀行</th><td>'
			+ json.bank
			+ '</td></tr><tr><th>銀行單號</th><td>'
			+ json.bankSid
			+ '</td>'
			+ '</tr><tr><th>匯款金額</th><td>'
			+ json.bankChekAmount
			+ '</td></tr><tr><th>銀行到帳時間</th><td>'
			+ (json.bankDepositDatetime == null ? '' : json.bankDepositDatetime)
			+ '</td></tr><tr><th>備註</th><td></td></tr></tbody></table><div class="btn-area">'
			+ '<button class="btn-xl btn-orange" onclick="lastPassCheck('
			+ json.accId
			+ ','
			+ json.status
			+ ','
			+ json.orderId
			+ ','
			+ json.orderType
			+ ');">審核通過</button>\n<button class="btn-xl btn-gray" onclick="showAbnormalPage('
			+ json.accId
			+ ','
			+ json.status
			+ ','
			+ json.orderId + ',' + json.orderType + ');">異常</button>' + '</div></div>';
	onClickOpenModal(str);
}

function showAbnormalPage(accId, status, orderId, orderType) {
	var str = '<div class="modal-content width-percent-960 margin-fix-4"><span class="close" onclick="onClickCloseModalV2();">×</span><p>請填寫訂單異常原因:</p>'
			+ '<textarea id = "lastRefuseReson" placeholder="異常原因必填" class="unusual"></textarea><div class="btn-area"><button class="btn-xl btn-orange" onclick="lastrefuseCheck('
			+ accId + ',' + status + ',' + orderId + ',' + orderType + ');">確定</button>' + '</div></div>';
	onClickOpenModalV2(str);
}

function showAudiOrder(orderType) {
	var json = JSON.parse(getEle("auditOrder").value);
	var str1 = "";
	var str2 = "";
	var str3 = "";
	var str4 = "";
	var str5 = "";
	var title = "";
	if (orderType == 0 && json.enable == 1) {
		title = "充值水單審核";
	} else if (orderType == 0 && json.enable == 0) {
		title = "此充值水單無法審核!";
	} else if (orderType == 1 && json.enable == 1) {
		title = "取款水單審核";
	} else if (orderType == 1 && json.enable == 0) {
		title = "此取款水單無法審核!";
	}

	str1 += '<div class="modal-content width-percent-460 margin-fix-5"><span class="close" onclick="onClickCloseModal();">×</span>';
	str2 += '<h3>' + title + '</h3><table class="first-th-fix-1"><tbody><tr><th>單號</th><td>' + json.orderId + '</td></tr><tr><th>會員帳號</th><td>'
			+ json.accName + '</td></tr>' + '<tr><th>取款金額</th><td>' + json.amount + '</td></tr><tr><th>帳戶名稱</th><td>' + json.bankAccName
			+ '</td></tr><tr><th>卡號</th><td>' + json.bankAcc + '</td></tr><tr><th>銀行</th><td>' + json.bank + '</td>';
	if (orderType == 0) {
		str3 += '</tr><tr><th>附言</th><td>' + json.note + '</td></tr>';
	}
	if (orderType == 0) {
		str4 += '<tr><th>銀行單號</th><td>' + json.bankSid + '</td></tr>' + '<tr><th>匯款金額</th><td>' + json.bankCheckAmount + '</td></tr>'
				+ '<tr><th>銀行到帳時間</th><td>' + (json.bankDepositDatetime == null ? '' : json.bankDepositDatetime) + '</td></tr>'
				+ '<tr><th>補單原因</th><td>' + (json.remark == null ? '' : json.remark) + '</td></tr>';
	} else if (orderType == 1 && json.enable == 1) {
		str4 += '<tr><th>銀行單號</th><td><input type="text" class="margin-fix-2" id="inputBankSid" placeholder="此欄位必填" onkeyup = "checkInputBankSid();" maxlength="20"></td></tr>'
				+ '<tr><th>匯款金額</th><td><input type="text" class="margin-fix-2" maxlength="10" id="inputBankCheckAmount" placeholder="此欄位必填" onkeyup ="checkBankAmount();"><span id = "checkInputBankCheckAmountIcon"></span><span id = "checkInputBankCheckAmount" maxlength="20"></td></tr>'
				+ '<tr><th>銀行到帳時間</th><td><input type="text" class="margin-fix-2" id="inputBankDepositTime" placeholder="此欄位必填" onclick="newCalendar(this, this.id,1)" readonly>'
				+ '<span id = "checkInputBankAccNameIcon"></span><span id = "checkInputBankAccName"></td></tr>';
	}
	if (orderType == 0) {
		var passCheck = "rechargePassCheck";
		var refuseCheck = "rechargeRefuseCheck";
	} else {
		var passCheck = "withdrawalPassCheck";
		var refuseCheck = "withdrawalRefuseCheck";
	}
	str5 = '<tr><th>備註</th><td><textarea id="refuseReson" placeholder="若需拒絕此筆訂單，請在此填入原因"></textarea></td>'
			+ '</tr></tbody></table><div class="btn-area">'
	str6 = '<button class="btn-xl btn-orange" onclick="' + passCheck + '(' + json.status + ',' + json.orderId + ',' + json.orderType
			+ ');">審核通過</button>' + '<button class="btn-xl btn-gray" onclick="' + refuseCheck + '(' + json.status + ',' + json.orderId + ','
			+ json.orderType + ');">拒絕</button></div></div>';
	var htmlStr = "";
	if (json.enable == 1) {
		htmlStr = htmlStr.concat(str1, str2, str3, str4, str5, str6);
	} else if (json.enable == 0) {
		htmlStr = htmlStr.concat(str1, str2, str3, str4);
	}

	onClickOpenModal(htmlStr);
}

function auditPassAjax(tmpstr) {
	getEle("research").value = "1";
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleAudit;
		XHR.open("POST", "./Order!audit.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send("tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&" + tmpstr);
		enableLoadingPage();
	}
}
function auditRefuseAjax(tmpstr) {
	getEle("research").value = "1";
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleAudit;
		XHR.open("POST", "./Order!audit.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send("tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&" + tmpstr);
		enableLoadingPage();
		onCheckCloseModelPage();
	}
}
function handleAudit() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (json.message == "fail") {
							onCheckModelPage2("提交失敗");
						} else if (json.message == "success") {
							onClickCloseModal();
							onCheckModelPage2("提交通過");
						}
					}
				}
			} catch (error) {
				console_Log("handleAudit error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
				if (typeof json.message != "undefined" && json.message != null && json.message == "success") {
					getSearchDataAjax(getEle("orderPage").value, getEle("orderNextPage").value);
				}
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function showCheckOrderCarryOut() {
	var json = JSON.parse(getEle("carryoutOrder").value);
	var str1 = "";
	var str2 = "";
	var str3 = "";
	var title = "";
	if (json.orderType == 0 && true) {
		title = "充值完成水單";
	} else if (json.orderType == 1 && true) {
		title = "取款完成水單";
	}
	// else if (json.orderType == 0 && false) {
	// title = "3";
	// }else if (json.orderType == 1 && false) {
	// title = "4";
	// }

	str1 += '<div class="modal-content width-percent-460 margin-fix-5"><span class="close" onclick="onClickCloseModal();">×</span><h3>' + title
			+ '</h3><table class="first-th-fix-1">' + '<tbody><tr><th>單號</th><td>' + json.orderId + '</td></tr><tr><th>訂單狀態</th><td>'
			+ order.orderStatus[json.status] + '</td></tr><tr><th>會員帳號</th><td>' + json.accName + '</td></tr><tr><th>充值金額</th>' + '<td>'
			+ json.amount + '</td></tr><tr><th>帳戶名稱</th><td>' + json.bankAccName + '</td></tr><tr><th>卡號</th><td>' + json.bankAcc
			+ '</td></tr><tr><th>銀行</th><td>' + json.bank + '</td></tr>';
	if (json.orderType == 0 && false) {
		str2 += '<tr><th>附言</th><td>' + json.note + '</td></tr>';
	}
	str3 += '<tr><th>銀行單號</th><td>' + json.bankSid + '</td></tr><tr><th>匯款金額</th><td>' + json.bankCheckAmount + '</td></tr><tr><th>銀行到帳時間</th><td>'
			+ (json.bankDepositDatetime == null ? '' : json.bankDepositDatetime) + '</td></tr><tr><th>備註</th>' + '<td>'
			+ (json.remark == null ? '' : json.remark) + '</td></tr></tbody></table>'
			+ '<div class="btn-area"><button class="btn-xl btn-gray" onclick="onClickCloseModal();">關閉</button></div></div>';

	var htmlStr = "";

	htmlStr = htmlStr.concat(str1, str2, str3);
	onClickOpenModal(htmlStr);
}

function reviewPassOrder(orderId, orderType) {
	getEle("ReviewPassOrder").value = "";
	getEle("carryoutOrder").value = "";
	getEle("auditOrder").value = "";
	getEle("orderJson").value = "";

	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&orderType=" + orderType + "&orderId=" + orderId;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleReviewPassOrder;
		XHR.open("POST", "./Order!checkOrderReview.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpStr);
		enableLoadingPage();
	}

}

function handleReviewPassOrder() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText).reviewData;
					if (!isNull(json)) {
						getEle("ReviewPassOrder").value = JSON.stringify(json);
						showReviewPassOrder();
					}
				}
			} catch (error) {
				console_Log("handleReviewPassOrder error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
				getSearchDataAjax(getEle("orderPage").value, getEle("orderNextPage").value);
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function checkRechargeCarryOutOrderAjax(orderType, orderId) {
	getEle("ReviewPassOrder").value = "";
	getEle("carryoutOrder").value = "";
	getEle("auditOrder").value = "";
	getEle("orderJson").value = "";

	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&orderType=" + orderType + "&orderId=" + orderId;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleCheckRechargeCarryOutOrderAjax;
		XHR.open("POST", "./Order!checkRechargeCarryOutOrderData.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handleCheckRechargeCarryOutOrderAjax() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText).data;
					if (checkTokenIdfail(json)) {
						getEle("carryoutOrder").value = JSON.stringify(json);
						showCheckOrderCarryOut();
					}
				}
			} catch (error) {
				console_Log("handleCheckRechargeCarryOutOrderAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
				getSearchDataAjax(getEle("orderPage").value, getEle("orderNextPage").value);
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}
function checkWithdrawalCarryOutOrderAjax(orderType, orderId) {
	getEle("ReviewPassOrder").value = "";
	getEle("carryoutOrder").value = "";
	getEle("auditOrder").value = "";
	getEle("orderJson").value = "";

	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&orderType=" + orderType + "&orderId=" + orderId;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleCheckWithdrawalCarryOutOrderAjax;
		XHR.open("POST", "./Order!checkWithdrawalCarryOutOrderData.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handleCheckWithdrawalCarryOutOrderAjax() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText).data;
					if (checkTokenIdfail(json)) {
						getEle("carryoutOrder").value = JSON.stringify(json);
						showCheckOrderCarryOut();
					}
				}
			} catch (error) {
				console_Log("handleCheckWithdrawalCarryOutOrderAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
				getSearchDataAjax(getEle("orderPage").value, getEle("orderNextPage").value);
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function getAuditRechargeOrderAjax(orderType, orderId, status, rechargeType) {
	getEle("ReviewPassOrder").value = "";
	getEle("carryoutOrder").value = "";
	getEle("auditOrder").value = "";
	getEle("orderJson").value = "";

	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&orderType=" + orderType + "&orderId=" + orderId + "&status="
				+ status + "&rechargeType=" + rechargeType;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = function() {
			handleGetAuditRechargeOrderAjax(orderType)
		};
		XHR.open("POST", "./Order!getAuditRechargeOrderData.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handleGetAuditRechargeOrderAjax(orderType) {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText).data;
					if (!isNull(json)) {
						getEle("auditOrder").value = JSON.stringify(json);
						showAudiOrder(orderType);
					}
				}
			} catch (error) {
				console_Log("handleGetAuditRechargeOrderAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
				getSearchDataAjax(getEle("orderPage").value, getEle("orderNextPage").value);
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function getAuditWithdrawalOrderAjax(orderType, orderId, status, rechargeType) {
	getEle("ReviewPassOrder").value = "";
	getEle("carryoutOrder").value = "";
	getEle("auditOrder").value = "";
	getEle("orderJson").value = "";

	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&orderType=" + orderType + "&orderId=" + orderId + "&status="
				+ status + "&rechargeType=" + rechargeType;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = function() {
			handleGetAuditWithdrawalOrderAjax(orderType)
		};
		XHR.open("POST", "./Order!getAuditWithdrawalOrderData.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpStr);
		enableLoadingPage();
	}
}

function handleGetAuditWithdrawalOrderAjax(orderType) {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				var json = JSON.parse(XHR.responseText).data;
				var tmp = JSON.parse(XHR.responseText);
				if (!isNull(json)) {
					getEle("auditOrder").value = JSON.stringify(json);
					showAudiOrder(orderType);
				}
			} catch (error) {
				console_Log("handleGetAuditWithdrawalOrderAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
				getSearchDataAjax(getEle("orderPage").value, getEle("orderNextPage").value);
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function getSearchDataAjax(page, nextpage) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = getEle("searchOrderInfo").value + "&page=" + page + "&nextPage=" + nextpage;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = eval(getEle("handleName").value);
		XHR.open("POST", getEle("searchOrderAction").value + "?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpStr);
	}
}
function rechargePassCheck(status, orderId, orderType) {
	getEle("research").value = "1";
	var json = JSON.parse(getEle("auditOrder").value);
	var tmpstr = "status=" + status + "&orderId=" + orderId + "&orderType=" + orderType + "&success=true" + "&memberAccId=" + json.accId
			+ "&orderAmount=" + json.amount + "&remark=&bankSid=&bankCheckAmount=0&bankDepositTime=";
	if (status > 0 && orderId > 0 && orderType >= 0 && json.accId > 0 && json.amount > 0) {
		onCheckModelPage('確定送出', 'auditPassAjax(\'' + tmpstr + '\')');
	}
}
function rechargeRefuseCheck(status, orderId, orderType) {
	getEle("research").value = "1";
	var remark = getEle("refuseReson").value;
	var json = JSON.parse(getEle("auditOrder").value);
	var tmpstr = "status=" + status + "&orderId=" + orderId + "&orderType=" + orderType + "&success=false" + "&memberAccId=" + json.accId
			+ "&orderAmount=0&remark=" + remark + "&bankSid=&bankCheckAmount=0&bankDepositTime=";
	if (status > 0 && orderId > 0 && orderType >= 0 && remark != '') {
		onCheckModelPage('確定送出', 'auditRefuseAjax(\'' + tmpstr + '\')');
	} else {
		onCheckModelPage2("拒絕原因不能為空");
	}
}
function withdrawalPassCheck(status, orderId, orderType) {
	getEle("research").value = "1";
	var json = JSON.parse(getEle("auditOrder").value);
	var bankSid = getEle("inputBankSid").value;
	var bankCheckAmount = getEle("inputBankCheckAmount").value;
	var bankDepositTime = getEle("inputBankDepositTime").value;

	var tmpstr = "status=" + status + "&orderId=" + orderId + "&orderType=" + orderType + "&success=true" + "&memberAccId=" + json.accId
			+ "&orderAmount=" + json.amount + "&bankSid=" + bankSid + "&bankCheckAmount=" + bankCheckAmount + "&bankDepositTime=" + bankDepositTime
			+ "&remark=";
	if (status > 0 && orderId > 0 && orderType >= 0 && json.accId > 0 && json.amount > 0 && bankSid != '' && bankCheckAmount > 0
			&& bankDepositTime != '') {
		onCheckModelPageSetFunction('確定送出', function() {
			auditPassAjax(tmpstr);
		});
	} else {
		onCheckModelPage2("有欄位尚未填寫");
	}
}
function withdrawalRefuseCheck(status, orderId, orderType) {
	getEle("research").value = "1";
	var json = JSON.parse(getEle("auditOrder").value);
	var remark = getEle("refuseReson").value;
	var tmpstr = "status=" + status + "&orderId=" + orderId + "&orderType=" + orderType + "&success=false" + "&memberAccId=" + json.accId
			+ "&orderAmount=" + json.amount + "&remark=" + remark + "&bankSid=&bankCheckAmount=0&bankDepositTime=";
	if (status > 0 && orderId > 0 && orderType >= 0 && remark != '') {
		onCheckModelPage('確定送出', 'auditRefuseAjax(\'' + tmpstr + '\')');
	} else {
		onCheckModelPage2("拒絕原因不能為空");
	}
}
function lastPassCheck(accId, status, orderId, orderType) {
	getEle("research").value = "1";
	var tmpstr = "memberAccId=" + accId + "&status=" + status + "&orderId=" + orderId + "&orderType=" + orderType + "&success=true"
			+ "&memberAccId=0&orderAmount=0&remark=&bankSid=&bankCheckAmount=0&bankDepositTime=";
	if (status == 6 && orderId > 0 && orderType >= 0) {
		onCheckModelPage('確定送出', 'auditPassAjax(\'' + tmpstr + '\')');
	}
}
function lastrefuseCheck(accId, status, orderId, orderType) {
	getEle("research").value = "1";
	var remark = getEle("lastRefuseReson").value;
	var tmpstr = "memberAccId=" + accId + "&status=" + status + "&orderId=" + orderId + "&orderType=" + orderType + "&success=false" + "&remark="
			+ remark + "&memberAccId=0&orderAmount=0&remark=&bankSid=&bankCheckAmount=0&bankDepositTime=";
	if (status == 6 && orderId > 0 && orderType >= 0 && remark != '') {
		onCheckModelPage('確定送出', 'auditRefuseAjax(\'' + tmpstr + '\')');
	} else {
		onCheckModelPage2("拒絕原因不能為空");
	}
}
function confromShowOrderLog(upperAction) {
	var logPage = getEle("logPage").value;
	var logNextPage = logPage;
	var count = getEle("logCount").value;
	if (upperAction == LOG_ACTION_ADD_RECHARGE_ORDER_CHANGE || upperAction == LOG_ACTION_AUDIT_WITHDRAWAL_ORDER
			|| upperAction == LOG_ACTION_AUDIT_RECHARGE_ORDER || upperAction == LOG_ACTION_AUDIT_ORDER_REVIEW || upperAction == LOG_ACTION_ADD_ORDER) {
		var str = "&upperAction=" + upperAction + "&count=" + count + "&logPage=" + logPage + "&logNextPage=" + logNextPage;
		getEle("nowActionUpperId").value = upperAction;
		showOrderLogAjax(str);
	}
}
function showOrderLogAjax(str) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + str;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();
			XHR.abort();
		}
		XHR.onreadystatechange = handleshowOrderLogAjax;
		XHR.open("POST", "./AccountManage!showOrderLog.php?date=" + getNewTime(), true);
		XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR.send(tmpStr);
		enableLoadingPage();
	}
}
function handleshowOrderLogAjax() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					getEle("getLogInfo").value = XHR.responseText;
					var json = JSON.parse(getEle("getLogInfo").value);
					if (checkTokenIdfail(json)) {
						showOrderLog();
						if (typeof json.logPage != "undefined" && json.logPage != null) {
							getEle("logPage").value = json.logPage;
							if (getEle("displayNowLogPage") != null && getEle("displayNowLogPage") != undefined) {
								getEle("displayNowLogPage").innerHTML = json.logPage;
							}
						}
						if (typeof json.logMaxPage != "undefined" && json.logMaxPage != null) {
							getEle("logMaxPage").value = json.logMaxPage;
							if (getEle("displayNowLogMaxPage") != null && getEle("displayNowLogMaxPage") != undefined) {
								getEle("displayNowLogMaxPage").innerHTML = json.logMaxPage;
							}
						}
					}
				}
			} catch (error) {
				console_Log("handleshowOrderLogAjax error" + error.message);
			} finally {
				disableLoadingPage();
				XHR.abort();
			}
		} else {
			disableLoadingPage();
			XHR.abort();
		}
	}
}
function showOrderLog() {
	var json = JSON.parse(getEle("getLogInfo").value);
	var josnObject = json.showOrderLog;
	var logTableTitle = '';
	var logTableData = '';
	var buttonHTML = '';
	var orderId = '';
	var action = '';
	var detail = '';
	var accName = '';
	var status = '';
	var opsName = '';
	var datetime = '';
	var ip = '';
	var str = [];
	var showHtml = '';

	if (getEle("nowActionUpperId").value == LOG_ACTION_ADD_RECHARGE_ORDER_CHANGE) {
		logTableTitle = logTableHTML([ '編號', '水單編號', '金額', '會員帳號', '補單人員帳號', '日期', 'IP', '內容' ]);
	} else if (getEle("nowActionUpperId").value == LOG_ACTION_ADD_ORDER) {
		logTableTitle = logTableHTML([ '編號', '水單編號', '金額', '會員帳號', '日期', 'IP', '內容' ]);
	} else if (getEle("nowActionUpperId").value == LOG_ACTION_AUDIT_WITHDRAWAL_ORDER) {
		logTableTitle = logTableHTML([ '編號', '水單編號', '金額', '會員帳號', '審核人員帳號', '水單狀態', '日期', 'IP', '內容' ]);
	} else if (getEle("nowActionUpperId").value == LOG_ACTION_AUDIT_RECHARGE_ORDER) {
		logTableTitle = logTableHTML([ '編號', '水單編號', '金額', '會員帳號', '審核人員帳號', '水單狀態', '日期', 'IP', '內容' ]);
	} else if (getEle("nowActionUpperId").value == LOG_ACTION_AUDIT_ORDER_REVIEW) {
		logTableTitle = logTableHTML([ '編號', '水單編號', '水單類型', '金額', '會員帳號', '審核人員帳號', '日期', 'IP', '內容' ]);
	}

	str[0] = '<div class="modal-content width-percent-960 margin-fix-4">';
	str[1] = '<span class="close" onclick="onClickCloseModal();">×</span>';
	str[2] = '<h3>' + ORDER_TABLE_NAME_LOG_JSON[getEle("nowActionUpperId").value] + '</h3>';
	str[3] = '<p class="media-control text-center">';
	str[4] = '<a href="javascript:void(0);" onclick="firstOrderLogPage();" class="backward"></a>';
	str[5] = '<a href="javascript:void(0);" onclick="previousOrderLogPage();" class="backward-fast"></a>';
	str[6] = '<span>總頁數：<i id= "displayNowLogPage">1</i><span>/</span><i id = "displayNowLogMaxPage">1</i>頁</span>';
	str[7] = '<a href="javascript:void(0);" onclick="nextOrderLogPage();" class="forward"></a>';
	str[8] = '<a href="javascript:void(0);" onclick="nextOrderLogMaxPage();" class="forward-fast"></a></p>';
	str[9] = '<div class="tab-area">';
	str[10] = '<button onclick="confromShowOrderLog(' + LOG_ACTION_ADD_RECHARGE_ORDER_CHANGE + ');">充值補單建立紀錄</button>\n';
	str[11] = '<button onclick="confromShowOrderLog(' + LOG_ACTION_ADD_ORDER + ');">水單建立紀錄</button>\n';
	str[12] = '<button onclick="confromShowOrderLog(' + LOG_ACTION_AUDIT_WITHDRAWAL_ORDER + ');">提款水單審核紀錄</button>\n';
	str[13] = '<button onclick="confromShowOrderLog(' + LOG_ACTION_AUDIT_RECHARGE_ORDER + ');">充值水單審核紀錄</button>\n';
	str[14] = '<button onclick="confromShowOrderLog(' + LOG_ACTION_AUDIT_ORDER_REVIEW + ');">複審紀錄</button>\n';
	str[15] = '</div>';
	str[16] = '<div class="tab-content">';
	str[17] = '<table class="table-zebra tr-hover">';
	str[18] = '<tbody>';
	str[19] = logTableTitle;
	str[20] = '</tbody>';
	str[21] = '</table>';
	str[22] = '</div>';
	str[23] = '</div>';

	for (var i = 0; i < Object.keys(josnObject).length; i++) {
		detail = strToJson(josnObject[i].detail);
		buttonHTML = '<button onclick="showOrderDetailModalV2(' + i + ');">詳細內容</button>\n';
		if (!isNull(josnObject[i].action)) {
			action = josnObject[i].action;
		}
		if (!isNull(josnObject[i].dataId)) {
			orderId = josnObject[i].dataId;
		}
		if (!isNull(josnObject[i].opsAccName)) {
			opsAccName = josnObject[i].opsAccName;
		}
		if (!isNull(josnObject[i].accName)) {
			accName = josnObject[i].accName;
		}
		if (detail != null && detail != "") {
			if (!isNull(detail.status)) {
				status = ORDER_VALUE_LOG_JSON["status"][detail.status];
			}
			if (isNull(detail.secondAuditor)) {
				orderType = "提款";
			} else {
				orderType = "充值";
			}
			if (!isNull(detail.amount)) {
				amount = detail.amount;
			}
		}
		if (!isNull(josnObject[i].opsDatetime)) {
			datetime = josnObject[i].opsDatetime;
		}
		if (!isNull(josnObject[i].ip)) {
			ip = josnObject[i].ip;
		}
		if (getEle("nowActionUpperId").value == LOG_ACTION_ADD_RECHARGE_ORDER_CHANGE) {
			logTableData = logTableHTML([ (i + 1), orderId, amount, accName, opsAccName, datetime, ip, buttonHTML ]);
		} else if (getEle("nowActionUpperId").value == LOG_ACTION_ADD_ORDER) {
			logTableData = logTableHTML([ (i + 1), orderId, amount, accName, datetime, ip, buttonHTML ]);
		} else if (getEle("nowActionUpperId").value == LOG_ACTION_AUDIT_WITHDRAWAL_ORDER) {
			logTableData = logTableHTML([ (i + 1), orderId, amount, accName, opsAccName, status, datetime, ip, buttonHTML ]);
		} else if (getEle("nowActionUpperId").value == LOG_ACTION_AUDIT_RECHARGE_ORDER) {
			logTableData = logTableHTML([ (i + 1), orderId, amount, accName, opsAccName, status, datetime, ip, buttonHTML ]);
		} else if (getEle("nowActionUpperId").value == LOG_ACTION_AUDIT_ORDER_REVIEW) {
			logTableData = logTableHTML([ (i + 1), orderId, orderType, amount, accName, opsAccName, datetime, ip, buttonHTML ]);
		}
		str[19] += logTableData;
	}
	showHtml = str.join('');
	onClickOpenModal(showHtml);
}
function showOrderDetailModalV2(i) {
	var json = '';
	var josnObject = '';
	var detail = '';
	var data = '';
	var detailKey = '';
	var addOrderKeys = '';
	var action = '';
	var str = [];
	var showHtml = '';
	try {
		json = JSON.parse(getEle("getLogInfo").value);
		josnObject = json.showOrderLog;
		detail = strToJson(josnObject[i].detail);
		detailKey = Object.keys(detail);
		addOrderKeys = Object.keys(ORDER_KEYS_LOG_JSON);
		if (!isNull(josnObject[i].action)) {
			action = josnObject[i].action;
		}
		if (!isNull(josnObject[i].dataId)) {
			orderId = josnObject[i].dataId;
		}
		if (!isNull(josnObject[i].accName)) {
			accName = josnObject[i].accName;
		}
		str[0] = '<div class="modal-content width-percent-20">';
		str[1] = '<span class="close" onclick="onClickCloseModalV2();">×</span>';
		str[2] = '<h3>' + '水單單號:' + orderId + ' 會員名稱:' + accName + '</h3>';
		str[3] = '<div class="tab-content">';
		str[4] = '<table class="table-zebra tr-hover">';
		str[5] = '<tbody>';
		str[6] = '';
		str[7] = '</tbody>';
		str[8] = '</table>';
		str[9] = '</div>';
		str[10] = '</div>';
		for (var i = 0; i < addOrderKeys.length; i++) {
			for (var j = 0; j < detailKey.length; j++) {
				if (detailKey[j] == addOrderKeys[i]) {
					if (detailKey[j] == 'orderType' || detailKey[j] == 'rechargeType' || detailKey[j] == 'currency' || detailKey[j] == 'status'
							|| detailKey[j] == 'backOrderStatus') {
						data = data.concat('<tr><td>', ORDER_KEYS_LOG_JSON[detailKey[j]], '</td><td>',
								ORDER_VALUE_LOG_JSON[detailKey[j]][detail[detailKey[j]]], '</td></tr>');
					} else {
						data = data.concat('<tr><td>', ORDER_KEYS_LOG_JSON[detailKey[j]], '</td><td>', detail[detailKey[j]], '</td></tr>');
					}
				}
			}
		}
		str[6] = data;
	} catch (error) {
		data = '';
		console_Log("getOrderDetail error=" + error.message);
	} finally {
		delete json;
		delete josnObject;
		delete detail;
		delete detailKey;
		delete addOrderKeys;
		delete action;
		if (data.length > 0) {
			showHtml = str.join('');
			onClickOpenModalV2(showHtml);
		}
	}
}
function nextOrderLogPage() {
	var nextPageNum = parseInt(getEle("logPage").value) + 1;
	if (nextPageNum <= parseInt(getEle("logMaxPage").value)) {
		page = nextPageNum;
	} else {
		page = getEle("logMaxPage").value;
	}
	var nowActionUpperId = getEle("nowActionUpperId").value;
	var str = "&upperAction=" + nowActionUpperId + "&count=" + getEle("logCount").value + "&nextPage=" + page;
	showOrderLogAjax(str);
}
function nextOrderLogMaxPage() {
	var maxPage = getEle("logMaxPage").value;
	var nowActionUpperId = getEle("nowActionUpperId").value;
	var str = "&upperAction=" + nowActionUpperId + "&count=" + getEle("logCount").value + "&nextPage=" + maxPage;
	showOrderLogAjax(str);
}

function previousOrderLogPage() {
	var previousPage = parseInt(getEle("logPage").value) - 1;
	var page = "1"
	if (previousPage > 0) {
		page = previousPage;
	}
	var nowActionUpperId = getEle("nowActionUpperId").value;
	var str = "&upperAction=" + nowActionUpperId + "&count=" + getEle("logCount").value + "&nextPage=" + page;
	showOrderLogAjax(str);
}

function firstOrderLogPage() {
	var nowActionUpperId = getEle("nowActionUpperId").value;
	var str = "&upperAction=" + nowActionUpperId + "&count=" + getEle("logCount").value + "&nextPage=1";
	showOrderLogAjax(str);
}
