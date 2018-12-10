var XHR_SetLotteryTime = null;

var pageCount = 25;
var nowPage = 1;
var totalPage = 1;
var nowFirstCount = 0;
var nowLastCount = nowFirstCount + pageCount;
var totalCount = 0;

function SetLotteryNum() {
	SetLotteryNum_into();
	getLotteryTypeAjax2();
}

function SetLotteryNum_into() {
	var str = '';
	if (document.getElementsByName("onLoadFunction").length != 1) {
		str += '\n<input type="hidden" name="lotteryTypeJson" value = "">';
		str += '\n<input type="hidden" name="lotteryListData" value = "">';
		str += '\n<input type="hidden" name="lotteryId" value = "">';
		str += '\n<input type="hidden" name="onLoadFunction" value = "">';
		str += '\n<input type="hidden" name="lotteryNumSetLogList" value = "">';
		str += '\n<input type="hidden" name="opsJsonData" value = "">';
		
		str += '\n<input type="hidden" name="showLotteryDate" value = "">';

	}
	if (str != '') {
		document.getElementById("extraHidden").innerHTML = str;
	}
	delete str;
	str = undefined;
	if (document.getElementById("mainContain") != null) {
		document.getElementById("mainContain").innerHTML = "";
	}
	if (document.getElementById("searchArea") != null) {
		document.getElementById("searchArea").innerHTML = "";
	}
	if (document.getElementById("myModal") != null) {
		document.getElementById("myModal").innerHTML = "";
	}
	if (document.getElementById("myModalV2") != null) {
		document.getElementById("myModalV2").innerHTML = "";
	}
}

function getLotteryTypeAjax2() {
	XHR_SetLotteryTime = checkXHR(XHR_SetLotteryTime);
	if (typeof XHR_SetLotteryTime != "undefined" && XHR_SetLotteryTime != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value);
		XHR_SetLotteryTime.timeout = 10000;
		XHR_SetLotteryTime.ontimeout = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onerror = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onreadystatechange = handleGetLotteryTypeAjax2;
		XHR_SetLotteryTime.open("POST", "./SetLotteryTime!getLotteryType.php?date=" + getNewTime(), true);
		XHR_SetLotteryTime.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR_SetLotteryTime.send(tmpStr);
		enableLoadingPage();
	}
}

function handleGetLotteryTypeAjax2() {
	if (XHR_SetLotteryTime.readyState == 4) {
		if (XHR_SetLotteryTime.status == 200) {
			try {
				if (isJSON(XHR_SetLotteryTime.responseText)) {
					var json = JSON.parse(XHR_SetLotteryTime.responseText);
					if (checkTokenIdfail(json)) {
						getEle("lotteryTypeJson").value = XHR_SetLotteryTime.responseText;
						showLotteryTypeTable2();
					}
				}
			} catch (error) {
				console_Log("handleGetLotteryTypeAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR_SetLotteryTime.abort();
			}
		} else {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
	}
}
function showLotteryTypeTable2() {
	var lotteryTypeJson = getEle("lotteryTypeJson").value;
	var lotteryTypeId = [ "p35", "k3", "fc", "ssc", "x5", "ff", "pk10" ]
	var arrObj = [];
	var str = [];
	if (isJSON(lotteryTypeJson)) {
		var lotteryTypeObj = JSON.parse(lotteryTypeJson).lotteryType;
		str.push('	<div class="contentDiv2-11">	\n');
		str.push('	<button class="" onclick="lotteryNumSetLogAjax();">操作紀錄</button>	\n');
		str.push('		<div id = "lotteryList">	\n');
		str.push('		</div>	\n');
		str.push('	</div>	\n');

		getEle("mainContain").innerHTML = str.join('');

		var lotteryTypeObjKey = Object.keys(JSON.parse(getEle("lotteryTypeJson").value).lotteryType).sort();
		var ele = getEle("lotteryList");
		for (var k = 0; k < lotteryTypeObjKey.length; k++) {

			var lotteryTypeTitle = document.createElement("h6");

			lotteryTypeTitle.innerHTML = lotteryTypeObjKey[k].substring(4, lotteryTypeObjKey[k].length);
			var table = document.createElement("table");

			var num = 0;
			var x = 4;
			var y = lotteryTypeObj[lotteryTypeObjKey[k]].length / x;
			
			lotteryTypeObj[lotteryTypeObjKey[k]] = jsonDataSort(lotteryTypeObj[lotteryTypeObjKey[k]] , "id" , true);

			arrObj[0] = {};
			arrObj[0].type = "tbody";
			arrObj[0].html = [];
			for (var i = 0; i < y; i++) {
				arrObj[0].html[i] = {};
				arrObj[0].html[i].type = "tr";
				arrObj[0].html[i].html = [];
				for (var j = 0; j < x; j++) {
					if (lotteryTypeObj[lotteryTypeObjKey[k]].length > num) {
						arrObj[0].html[i].html[j] = {};
						arrObj[0].html[i].html[j].type = "td";
						arrObj[0].html[i].html[j].text = '<a href = "javaScript:void(0);" onclick="getLotteryListData(\'' + lotteryTypeObj[lotteryTypeObjKey[k]][num]["id"]
								+ '\')">' + lotteryTypeObj[lotteryTypeObjKey[k]][num]["title"] + '</a>';
						num++;
					} else {
						arrObj[0].html[i].html[j] = {};
						arrObj[0].html[i].html[j].type = "td";
					}
				}
			}

			eleInsertTableEle(table, arrObj);
			lotteryTypeTitle.insertBefore(table, null);
			getEle("lotteryList").insertBefore(lotteryTypeTitle, null);

		}
	}
}

function getLotteryListData(id) {
	getEle("lotteryId").value = id;
	getEle("showLotteryDate").value = "";
	searchNowaDayData("");
}

function getLotteryListDataAjax(id, searchDate) {
	getEle("lotteryId").value = id;
	searchDate = typeof searchDate != "undefined" ? searchDate : "";
	XHR_SetLotteryTime = checkXHR(XHR_SetLotteryTime);
	if (typeof XHR_SetLotteryTime != "undefined" && XHR_SetLotteryTime != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&lotteryId=" + id + "&searchDate=" + searchDate;
		XHR_SetLotteryTime.timeout = 10000;
		XHR_SetLotteryTime.ontimeout = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onerror = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onreadystatechange = handleGetLotteryListDataAjax;
		XHR_SetLotteryTime.open("POST", "./SetLotteryNum!getLotteryListData.php?date=" + getNewTime(), true);
		XHR_SetLotteryTime.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR_SetLotteryTime.send(tmpStr);
		enableLoadingPage();
	}
}

function handleGetLotteryListDataAjax() {
	if (XHR_SetLotteryTime.readyState == 4) {
		if (XHR_SetLotteryTime.status == 200) {
			try {
				if (isJSON(XHR_SetLotteryTime.responseText)) {
					var json = JSON.parse(XHR_SetLotteryTime.responseText);
					if (checkTokenIdfail(json)) {
						getEle("lotteryListData").value = XHR_SetLotteryTime.responseText;
						
						showLotteryListMode();
					}
				}
			} catch (error) {
				console_Log("handleGetLotteryDataAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR_SetLotteryTime.abort();
			}
		} else {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
	}
}

function showLotteryListMode() {
	var lotteryListData = getEle("lotteryListData").value;
	var arrObj = [];
	if (lotteryListData != "" && isJSON(lotteryListData)) {
		
		var lotteryListDataJson = JSON.parse(lotteryListData).lotteryList;
		
		var key = Object.keys(lotteryListDataJson);
		if(key.length > 0){
			var d1,d2,nextLotteryDate,previousLotteryDate2;
			
			if(key.length == 1){
				nextLotteryDate = key[0] ;
				previousLotteryDate2 = key[0] ;
			}
			else{
				d1 = new Date(key[0]).getTime();
				d2 = new Date(key[1]).getTime();
				nextLotteryDate = d1 > d2 ? key[0] : key[1];
				previousLotteryDate2 = d1 < d2 ? key[0] : key[1];
			}
			
			
			if(typeof lotteryListDataJson[getEle("showLotteryDate").value] !== "undefined"){
				lotteryListDataJson = lotteryListDataJson[getEle("showLotteryDate").value];
			}else{
				lotteryListDataJson = lotteryListDataJson[nextLotteryDate];
			}
			
			
			
			var str = [];
			str.push('	<div class="modal-content modal-central lotterycontrol">	\n');
			str.push('		<span class="close" onclick="onClickCloseModal();">×</span>	\n');
			str.push('		<h3>開獎數據</h3>	\n');
			str.push('		<div>	\n');
			str.push('		<ul><li><button onclick = "searchNowaDayData(\''+previousLotteryDate2+'\');">上一期開獎日期</button></li><li><button onclick = "searchNowaDayData(\''+nextLotteryDate+'\');">下一期開獎日期</button></li></ul>	\n');
			str.push('			<h5>與官方同步彩種顯示內容：</h5>	\n');
			str.push('			<table id = "lotteryTableData" class = "tr-hover">	\n');
			str.push('				\n');
			str.push('			</table>	\n');
			str.push('		</div>	\n');
			str.push('	</div>	\n');
		
			onClickOpenModal(str.join(''));
		
			var tableTh = [ "彩種", "獎期", "日期", "開獎結果" , "正確號碼", "注單量", "投注用戶數", "錄號狀態", "開獎時間", "錄號時間", "投注額", "中獎金額", "操作" ];
			var tableTd = [ "title", "periodNum", "date", "data","actualData", "totalMidOrder", "totalAcc", "status", "kjTime", "actualKjTime", "totalBet", "totalBonus", "featuresType" ];
			var statusText = [ "等待中", "系統錄號", "手動錄號", "錄號錯誤", "撤單", "已退款","重新對獎完成", "開獎中" ];
		
			
	
			var num = 0;
			var x = tableTh.length;
			var y = lotteryListDataJson.length + 1;
			arrObj[0] = {};
			arrObj[0].type = "tbody";
			arrObj[0].html = [];
			for (var i = 0; i < y; i++) {
				arrObj[0].html[i] = {};
				arrObj[0].html[i].type = "tr";
				arrObj[0].html[i].html = [];
				for (var j = 0; j < x; j++) {
					if (i == 0) {
						arrObj[0].html[i].html[j] = {};
						arrObj[0].html[i].html[j].type = "th";
						arrObj[0].html[i].html[j].text = tableTh[j];
					} else {
						arrObj[0].html[i].html[j] = {};
						arrObj[0].html[i].html[j].type = "td";
	
						var text = "";
						if (tableTd[j] == "status") {
							if (lotteryListDataJson[i - 1][tableTd[j]] == 2 || lotteryListDataJson[i - 1][tableTd[j]] == 3 || lotteryListDataJson[i - 1][tableTd[j]] == 4) {
								text += "<span class='red'>" + statusText[lotteryListDataJson[i - 1][tableTd[j]]] + "</span>";
							} else {
								text += "" + statusText[lotteryListDataJson[i - 1][tableTd[j]]];
							}
						} else if (tableTd[j] == "featuresType") {
							// 0 - 1撤單 2 手動錄號 4 對獎 8 已撤除 16
							// 錄號錯誤
							if (lotteryListDataJson[i - 1][tableTd[j]] > 0) {
								if ((lotteryListDataJson[i - 1][tableTd[j]] & 2) > 0) {
									text += '<a href="javascript:void(0);" onclick = "showSetLotteryNum(\'' + lotteryListDataJson[i - 1]["periodNum"] + '\',\''
											+ lotteryListDataJson[i - 1]["kjTime"] + '\');" >手動錄號</a>';
								}
								if ((lotteryListDataJson[i - 1][tableTd[j]] & 4) > 0) {
									text += '<a href="javascript:void(0);" onclick = "showCheckTheLottery(\'' + lotteryListDataJson[i - 1]["periodNum"] + '\');" >對獎</a>';
								}
								if ((lotteryListDataJson[i - 1][tableTd[j]] & 64) > 0) {
									text += '<a href="javascript:void(0);" onclick = "showCheckTheLottery2(\'' + lotteryListDataJson[i - 1]["periodNum"] + '\');" >對獎</a>';
								}
								if ((lotteryListDataJson[i - 1][tableTd[j]] & 1) > 0) {
									text += '<a href="javascript:void(0);"  onclick = "showWithdrawalPeriodNum(\'' + lotteryListDataJson[i - 1]["periodNum"]
											+ '\');" >撤單</a>';
								}
								if ((lotteryListDataJson[i - 1][tableTd[j]] & 8) > 0) {
									text += '<span class="red">已撤除</span>';
								}
								if ((lotteryListDataJson[i - 1][tableTd[j]] & 32) > 0) {
									text += '<a href="javascript:void(0);" onclick = "showRecoverWinningMoney(\'' + lotteryListDataJson[i - 1]["periodNum"] + '\');" >退款</a>';
								}
								if ((lotteryListDataJson[i - 1][tableTd[j]] & 16) > 0) {
									text += '<a href="javascript:void(0);" onclick = "showLotteryFail(\'' + lotteryListDataJson[i - 1]["periodNum"] + '\');" >錄號錯誤</a>';
								}
								
	
							} else {
								text += '-';
							}
	
						} else {
							if (typeof lotteryListDataJson[i - 1][tableTd[j]] != "undefined") {
								text += lotteryListDataJson[i - 1][tableTd[j]];
							} else {
								text += "";
							}
	
						}
	
						arrObj[0].html[i].html[j].text = text;
						num++;
					}
				}
			}
			insertTableEle("lotteryTableData", arrObj);
		}
	}
}

function searchNowaDayData(date) {
	getEle("showLotteryDate").value = date;
	getEle("onLoadFunction").value = "searchNowaDayData('"+date+"')";
	var lotteryId = getEle("lotteryId").value;
	var date = new Date().getFromFormat("yyyy/MM/dd");
	getLotteryListDataAjax(lotteryId, date);
}

function searchYesterDayData() {
	getEle("onLoadFunction").value = "searchYesterDayData";
	var lotteryId = getEle("lotteryId").value;
	var date = new Date((new Date().getTime() - 24 * 60 * 60 * 1000)).getFromFormat("yyyy/MM/dd");
	getLotteryListDataAjax(lotteryId, date);
}

function showLotteryFail(periodNum) {
	var str = [];
	str.push('	<div class="modal-content modal-central width-percent-460 lotteryerror">	\n');
	str.push('		<span class="close" onclick="onClickCloseModalV2();">×</span>	\n');
	str.push('		<p>請再次確認錄號結果是否錯誤<br>請輸入正確的開號結果用以紀錄：<br><input type="text" id = "correctLotteryNum"></p>	\n');
	str.push('		<div class="button-area">	\n');
	str.push('			<button onclick="onClickCloseModalV2();">取消</button>	\n');
	str.push('			<button onclick="setCorrectLotteryNum(\'' + periodNum + '\');">確認</button>	\n');
	str.push('		</div>	\n');
	str.push('	</div>	\n');

	onClickOpenModalV2(str.join(''));
}
function setCorrectLotteryNum(periodNum) {
	var correctLotteryNum = getEle("correctLotteryNum").value;
	if (correctLotteryNum != "") {
		if (checkLotteryNum(correctLotteryNum)) {
			var lotteryId = getEle("lotteryId").value;
			setLotteryFailAjax(lotteryId, periodNum, correctLotteryNum);
		} else {
			getEle("correctLotteryNum").value = "";
			onClickCloseModalV2();
			showModePage("格式不符");
		}
	}
}

function setLotteryFailAjax(id, periodNum, correctLotteryNum) {
	getEle("lotteryId").value = id;
	XHR_SetLotteryTime = checkXHR(XHR_SetLotteryTime);
	if (typeof XHR_SetLotteryTime != "undefined" && XHR_SetLotteryTime != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&lotteryId=" + id + "&periodNum=" + periodNum + "&data=" + correctLotteryNum;
		XHR_SetLotteryTime.timeout = 10000;
		XHR_SetLotteryTime.ontimeout = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onerror = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onreadystatechange = handleSetLotteryFailAjax;
		XHR_SetLotteryTime.open("POST", "./SetLotteryNum!setLotteryFail.php?date=" + getNewTime(), true);
		XHR_SetLotteryTime.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR_SetLotteryTime.send(tmpStr);
		enableLoadingPage();
	}
}

function handleSetLotteryFailAjax() {
	if (XHR_SetLotteryTime.readyState == 4) {
		if (XHR_SetLotteryTime.status == 200) {
			try {
				if (isJSON(XHR_SetLotteryTime.responseText)) {
					var json = JSON.parse(XHR_SetLotteryTime.responseText);
					if (checkTokenIdfail(json)) {
						if (json.lotteryStatus != null && typeof json.lotteryStatus != "undefined") {
							if (json.lotteryStatus) {
								onClickCloseModalV2();
								showModePage("錄號錯誤修改成功");
							} else {
								onClickCloseModalV2();
								showModePage("錄號錯誤修改失敗");
							}
						}
					}
				}
			} catch (error) {
				console_Log("handleSetLotteryFailAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR_SetLotteryTime.abort();
				var onLoadFunction = getEle("onLoadFunction").value;
				if (onLoadFunction != "" && typeof eval(onLoadFunction.substr(0,onLoadFunction.indexOf("("))) === "function") {
					eval(onLoadFunction);
				}
			}
		} else {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
	}
}

function showSetLotteryNum(periodNum, kjTime) {
	var str = [];
	str.push('	<div class="modal-content modal-central width-percent-580 lotterypresetting">	\n');
	str.push('		<span class="close" onclick="onClickCloseModalV2();">×</span>	\n');
	str.push('		<p>手動／預設錄號</p>	\n');
	str.push('		<table>	\n');
	str.push('			<tbody><tr>	\n');
	str.push('				<th>獎期：</th>	\n');
	str.push('				<td>' + periodNum + '</td>	\n');
	str.push('			</tr>	\n');
	str.push('			<tr>	\n');
	str.push('				<th>開獎時間：</th>	\n');
	str.push('				<td>' + kjTime + '</td>	\n');
	str.push('			</tr>	\n');
	str.push('			<tr>	\n');
	str.push('				<th>開獎號：</th>	\n');
	str.push('				<td><input type="text" id = "lotteryNum"></td>	\n');
	str.push('			</tr>	\n');
	str.push('			<tr>	\n');
	str.push('				<th>提示：</th>	\n');
	str.push('				<td>輸入號碼前，請確認獎期是否正確，輸入時請以（，）  為號與號之間的分隔，例如：0,1,2,3,4；輸入完畢後，請點擊確認</td>	\n');
	str.push('			</tr>	\n');
	str.push('		</tbody></table>	\n');
	str.push('		<div class="button-area">	\n');
	str.push('			<button onclick="onClickCloseModalV2();">取消</button>	\n');
	str.push('			<button onclick="setLotteryNum(\'' + periodNum + '\');">確認</button>	\n');
	str.push('		</div>	\n');
	str.push('	</div>	\n');

	onClickOpenModalV2(str.join(''));
}

function setLotteryNum(periodNum) {
	var lotteryNum = getEle("lotteryNum").value;
	if (lotteryNum != "") {
		if (checkLotteryNum(lotteryNum)) {
			var lotteryId = getEle("lotteryId").value;
			
			showCheckSetLotteryNum(lotteryId,periodNum,lotteryNum);
			
		} else {
			getEle("lotteryNum").value = "";

			onClickCloseModalV2();
			showModePage("格式不符");

		}
	}
}
function showCheckSetLotteryNum(lotteryId,periodNum,lotteryNum){
	onClickCloseModalV2();
	var str = [];
	str.push('	<div class="modal-content modal-central width-percent-580 lotteryerror lotterypresetting">	\n');
	str.push('		<span class="close" onclick="onClickCloseModalV2();">×</span>	\n');
	str.push('		<p>請再次確認輸入的號碼是否正確？<br>您輸入的號為：<br>'+lotteryNum+'</p>	\n');
	str.push('		<div class="button-area">	\n');
	str.push('			<button onclick="onClickCloseModalV2();">取消</button>	\n');
	str.push('			<button onclick="setLotteryNumAjax(\''+lotteryId+'\', \''+periodNum+'\', \''+lotteryNum+'\');">確認</button>	\n');
	str.push('		</div>	\n');
	str.push('	</div>	\n');
	
	onClickOpenModalV2(str.join(''));
}



function setLotteryNumAjax(id, periodNum, lotteryNum) {
	getEle("lotteryId").value = id;
	XHR_SetLotteryTime = checkXHR(XHR_SetLotteryTime);
	if (typeof XHR_SetLotteryTime != "undefined" && XHR_SetLotteryTime != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&lotteryId=" + id + "&periodNum=" + periodNum + "&data=" + lotteryNum;
		XHR_SetLotteryTime.timeout = 10000;
		XHR_SetLotteryTime.ontimeout = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onerror = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onreadystatechange = handleSetLotteryNumAjax;
		XHR_SetLotteryTime.open("POST", "./SetLotteryNum!setLotteryNum.php?date=" + getNewTime(), true);
		XHR_SetLotteryTime.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR_SetLotteryTime.send(tmpStr);
		enableLoadingPage();
	}
}

function handleSetLotteryNumAjax() {
	if (XHR_SetLotteryTime.readyState == 4) {
		if (XHR_SetLotteryTime.status == 200) {
			try {
				if (isJSON(XHR_SetLotteryTime.responseText)) {
					var json = JSON.parse(XHR_SetLotteryTime.responseText);
					if (checkTokenIdfail(json)) {
						if (json.lotteryStatus != null && typeof json.lotteryStatus != "undefined") {
							if (json.lotteryStatus) {
								onClickCloseModalV2();
								showModePage("錄號成功");
							} else {
								onClickCloseModalV2();
								showModePage("錄號失敗");
							}
						}
					}
				}
			} catch (error) {
				console_Log("handleSetLotteryNumAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR_SetLotteryTime.abort();

				var onLoadFunction = getEle("onLoadFunction").value;
				if (onLoadFunction != "" && typeof eval(onLoadFunction.substr(0,onLoadFunction.indexOf("("))) === "function") {
					eval(onLoadFunction);
				}
			}
		} else {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
	}
}

function showWithdrawalPeriodNum(periodNum) {
	var str = [];
	str.push('	<div class="modal-content modal-central width-percent-460 lotteryerror">	\n');
	str.push('		<span class="close" onclick="onClickCloseModalV2();">×</span>	\n');
	str.push('		<p>注意：即將進行撤單，是否確定撤單？</p>	\n');
	str.push('		<p><input type="radio" name="remove" id="radio1" checked="checked" value = 1>一般撤單<input type="radio" name="remove" id="radio2" value = 2>本期為多開期數</p>	\n');
	str.push('		<div class="button-area">	\n');
	str.push('			<button onclick="onClickCloseModalV2();">取消</button>	\n');
	str.push('			<button onclick="setWithdrawalPeriodNum(\'' + periodNum + '\');">確認</button>	\n');
	str.push('		</div>	\n');
	str.push('	</div>	\n');

	onClickOpenModalV2(str.join(''));
}

function setWithdrawalPeriodNum(periodNum) {
	var lotteryId = getEle("lotteryId").value;
	var radioEle = document.getElementsByName("remove");
	var val = 0;
	for (var i = 0; i < radioEle.length; i++) {
		if (radioEle[i].checked == true) {
			val = radioEle[i].value;
			break;
		}
	}

	if (val == 1) {
		setWithdrawalPeriodNumAjax(lotteryId, periodNum);
	} else if (val == 2) {
		setDeletePeriodNumAjax(lotteryId, periodNum);
	}
}

function setWithdrawalPeriodNumAjax(id, periodNum) {
	getEle("lotteryId").value = id;
	XHR_SetLotteryTime = checkXHR(XHR_SetLotteryTime);
	if (typeof XHR_SetLotteryTime != "undefined" && XHR_SetLotteryTime != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&lotteryId=" + id + "&periodNum=" + periodNum;
		XHR_SetLotteryTime.timeout = 10000;
		XHR_SetLotteryTime.ontimeout = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onerror = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onreadystatechange = handleSetWithdrawalPeriodNumAjax;
		XHR_SetLotteryTime.open("POST", "./SetLotteryNum!withdrawalPeriodNum.php?date=" + getNewTime(), true);
		XHR_SetLotteryTime.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR_SetLotteryTime.send(tmpStr);
		enableLoadingPage();
	}
}

function setDeletePeriodNumAjax(id, periodNum) {
	getEle("lotteryId").value = id;
	XHR_SetLotteryTime = checkXHR(XHR_SetLotteryTime);
	if (typeof XHR_SetLotteryTime != "undefined" && XHR_SetLotteryTime != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&lotteryId=" + id + "&periodNum=" + periodNum;
		XHR_SetLotteryTime.timeout = 10000;
		XHR_SetLotteryTime.ontimeout = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onerror = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onreadystatechange = handleSetWithdrawalPeriodNumAjax;
		XHR_SetLotteryTime.open("POST", "./SetLotteryNum!deletePeriodNum.php?date=" + getNewTime(), true);
		XHR_SetLotteryTime.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR_SetLotteryTime.send(tmpStr);
		enableLoadingPage();
	}
}

function handleSetWithdrawalPeriodNumAjax() {
	if (XHR_SetLotteryTime.readyState == 4) {
		if (XHR_SetLotteryTime.status == 200) {
			try {
				if (isJSON(XHR_SetLotteryTime.responseText)) {
					var json = JSON.parse(XHR_SetLotteryTime.responseText);
					if (checkTokenIdfail(json)) {
						if (json.lotteryStatus != null && typeof json.lotteryStatus != "undefined") {
							if (json.lotteryStatus) {
								onClickCloseModalV2();
								showModePage("撤單成功");
							} else {
								onClickCloseModalV2();
								showModePage("撤單失敗");
							}
						}
					}
				}
			} catch (error) {
				console_Log("handleSetWithdrawalPeriodNumAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR_SetLotteryTime.abort();

				var onLoadFunction = getEle("onLoadFunction").value;
				if (onLoadFunction != "" && typeof eval(onLoadFunction.substr(0,onLoadFunction.indexOf("("))) === "function") {
					eval(onLoadFunction);
				}
			}
		} else {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
	}
}

function showCheckTheLottery(periodNum) {
	var str = [];
	str.push('	<div class="modal-content modal-central width-percent-460 lotteryerror">	\n');
	str.push('		<span class="close" onclick="onClickCloseModalV2();">×</span>	\n');
	str.push('		<p>是否對獎!?</p>	\n');
	str.push('		<div class="button-area">	\n');
	str.push('			<button onclick="onClickCloseModalV2();">取消</button>	\n');
	str.push('			<button onclick="callCheckTheLotteryAjax(\'' + periodNum + '\');">確認</button>	\n');
	str.push('		</div>	\n');
	str.push('	</div>	\n');

	onClickOpenModalV2(str.join(''));
}
function callCheckTheLotteryAjax(periodNum) {
	var id = getEle("lotteryId").value;
	XHR_SetLotteryTime = checkXHR(XHR_SetLotteryTime);
	if (typeof XHR_SetLotteryTime != "undefined" && XHR_SetLotteryTime != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&lotteryId=" + id + "&periodNum=" + periodNum;
		XHR_SetLotteryTime.timeout = 10000;
		XHR_SetLotteryTime.ontimeout = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onerror = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onreadystatechange = handleCallCheckTheLotteryAjax;
		XHR_SetLotteryTime.open("POST", "./SetLotteryNum!callCheckTheLottery.php?date=" + getNewTime(), true);
		XHR_SetLotteryTime.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR_SetLotteryTime.send(tmpStr);
		enableLoadingPage();
	}
}

function handleCallCheckTheLotteryAjax() {
	if (XHR_SetLotteryTime.readyState == 4) {
		if (XHR_SetLotteryTime.status == 200) {
			try {
				if (isJSON(XHR_SetLotteryTime.responseText)) {
					var json = JSON.parse(XHR_SetLotteryTime.responseText);
					if (checkTokenIdfail(json)) {
						if (json.lotteryStatus != null && typeof json.lotteryStatus != "undefined") {
							if (json.lotteryStatus) {
								onClickCloseModalV2();
								showModePage("對獎成功");
							} else {
								onClickCloseModalV2();
								showModePage("對獎失敗");
							}
						}
					}
				}
			} catch (error) {
				console_Log("handleCallCheckTheLotteryAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR_SetLotteryTime.abort();

				var onLoadFunction = getEle("onLoadFunction").value;
				if (onLoadFunction != "" && typeof eval(onLoadFunction.substr(0,onLoadFunction.indexOf("("))) === "function") {
					eval(onLoadFunction);
				}
			}
		} else {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
	}
}

function showCheckTheLottery2(periodNum) {
	var str = [];
	str.push('	<div class="modal-content modal-central width-percent-460 lotteryerror">	\n');
	str.push('		<span class="close" onclick="onClickCloseModalV2();">×</span>	\n');
	str.push('		<p>是否重新對獎!?</p>	\n');
	str.push('		<div class="button-area">	\n');
	str.push('			<button onclick="onClickCloseModalV2();">取消</button>	\n');
	str.push('			<button onclick="renewCallCheckTheLotteryAjax(\'' + periodNum + '\');">確認</button>	\n');
	str.push('		</div>	\n');
	str.push('	</div>	\n');

	onClickOpenModalV2(str.join(''));
}

function renewCallCheckTheLotteryAjax(periodNum) {
	var id = getEle("lotteryId").value;
	XHR_SetLotteryTime = checkXHR(XHR_SetLotteryTime);
	if (typeof XHR_SetLotteryTime != "undefined" && XHR_SetLotteryTime != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&lotteryId=" + id + "&periodNum=" + periodNum;
		XHR_SetLotteryTime.timeout = 10000;
		XHR_SetLotteryTime.ontimeout = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onerror = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onreadystatechange = handleRenewCallCheckTheLotteryAjax;
		XHR_SetLotteryTime.open("POST", "./SetLotteryNum!renewCallCheckTheLottery.php?date=" + getNewTime(), true);
		XHR_SetLotteryTime.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR_SetLotteryTime.send(tmpStr);
		enableLoadingPage();
	}
}

function handleRenewCallCheckTheLotteryAjax() {
	if (XHR_SetLotteryTime.readyState == 4) {
		if (XHR_SetLotteryTime.status == 200) {
			try {
				if (isJSON(XHR_SetLotteryTime.responseText)) {
					var json = JSON.parse(XHR_SetLotteryTime.responseText);
					if (checkTokenIdfail(json)) {
						if (json.lotteryStatus != null && typeof json.lotteryStatus != "undefined") {
							if (json.lotteryStatus) {
								onClickCloseModalV2();
								showModePage("對獎成功");
							} else {
								onClickCloseModalV2();
								showModePage("對獎失敗");
							}
						}
					}
				}
			} catch (error) {
				console_Log("handleCallCheckTheLotteryAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR_SetLotteryTime.abort();

				var onLoadFunction = getEle("onLoadFunction").value;
				if (onLoadFunction != "" && typeof eval(onLoadFunction.substr(0,onLoadFunction.indexOf("("))) === "function") {
					eval(onLoadFunction);
				}
			}
		} else {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
	}
}


function showRecoverWinningMoney(periodNum){
	var str = [];
	str.push('	<div class="modal-content modal-central width-percent-460 lotteryerror">	\n');
	str.push('		<span class="close" onclick="onClickCloseModalV2();">×</span>	\n');
	str.push('		<p>注意：即將進行退款，是否確定退款？</p>	\n');
	str.push('		<div class="button-area">	\n');
	str.push('			<button onclick="onClickCloseModalV2();">取消</button>	\n');
	str.push('			<button onclick="recoverPeriodNumWinningMoneyAjax(\'' + periodNum + '\');">確認</button>	\n');
	str.push('		</div>	\n');
	str.push('	</div>	\n');

	onClickOpenModalV2(str.join(''));
}

function recoverPeriodNumWinningMoneyAjax(periodNum){
	var id = getEle("lotteryId").value;
	XHR_SetLotteryTime = checkXHR(XHR_SetLotteryTime);
	if (typeof XHR_SetLotteryTime != "undefined" && XHR_SetLotteryTime != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&lotteryId=" + id + "&periodNum=" + periodNum;
		XHR_SetLotteryTime.timeout = 10000;
		XHR_SetLotteryTime.ontimeout = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onerror = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onreadystatechange = handleRecoverPeriodNumWinningMoneyAjax;
		XHR_SetLotteryTime.open("POST", "./SetLotteryNum!recoverPeriodNumWinningMoney.php?date=" + getNewTime(), true);
		XHR_SetLotteryTime.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR_SetLotteryTime.send(tmpStr);
		enableLoadingPage();
	}
}

function handleRecoverPeriodNumWinningMoneyAjax() {
	if (XHR_SetLotteryTime.readyState == 4) {
		if (XHR_SetLotteryTime.status == 200) {
			try {
				if (isJSON(XHR_SetLotteryTime.responseText)) {
					var json = JSON.parse(XHR_SetLotteryTime.responseText);
					if (checkTokenIdfail(json)) {
						if (json.lotteryStatus != null && typeof json.lotteryStatus != "undefined") {
							if (json.lotteryStatus) {
								onClickCloseModalV2();
								showModePage("退款成功");
							} else {
								onClickCloseModalV2();
								showModePage("退款失敗");
							}
						}
					}
				}
			} catch (error) {
				console_Log("handleRecoverPeriodNumWinningMoneyAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR_SetLotteryTime.abort();
				
				var onLoadFunction = getEle("onLoadFunction").value;
				if (onLoadFunction != "" && typeof eval(onLoadFunction.substr(0,onLoadFunction.indexOf("("))) === "function") {
					eval(onLoadFunction);
				}
			}
		} else {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
	}
}


function checkLotteryNum(str) {

	var lotteryId = getEle("lotteryId").value;
	if (lotteryNumArr_1.indexOf(parseInt(lotteryId)) >= 0) {
		return checkLotteryNum_SSC(str);
	} else if (lotteryNumArr_2.indexOf(parseInt(lotteryId)) >= 0) {
		return checkLotteryNum_3D(str);
	} else if (lotteryNumArr_3.indexOf(parseInt(lotteryId)) >= 0) {
		return checkLotteryNum_PK10(str);
	} else if (lotteryNumArr_4.indexOf(parseInt(lotteryId)) >= 0) {
		return checkLotteryNum_K3(str);
	} else if (lotteryNumArr_5.indexOf(parseInt(lotteryId)) >= 0) {
		return checkLotteryNum_11X5(str);
	}
	else if (lotteryNumArr_6.indexOf(parseInt(lotteryId)) >= 0) {
		return checkLotteryNum_LHC(str);
	}
	return false;
}


function lotteryNumSetLogAjax(endTime, startTime , logAction){
	logAction = typeof logAction != "undefined" ? logAction : 0;
	startTime = typeof startTime != "undefined" ? startTime : "";
	endTime = typeof endTime != "undefined" ? endTime : "";
	XHR_SetLotteryTime = checkXHR(XHR_SetLotteryTime);
	if (typeof XHR_SetLotteryTime != "undefined" && XHR_SetLotteryTime != null) {
		var tmpStr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&logAction=" + logAction + "&startTime=" + startTime + "&endTime=" + endTime;
		XHR_SetLotteryTime.timeout = 10000;
		XHR_SetLotteryTime.ontimeout = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onerror = function() {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
		XHR_SetLotteryTime.onreadystatechange = handleLotteryNumSetLogAjax;
		XHR_SetLotteryTime.open("POST", "./SetLotteryNum!getLotteryNumSetLog.php?date=" + getNewTime(), true);
		XHR_SetLotteryTime.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		XHR_SetLotteryTime.send(tmpStr);
		enableLoadingPage();
	}
}

function handleLotteryNumSetLogAjax(){
	if (XHR_SetLotteryTime.readyState == 4) {
		if (XHR_SetLotteryTime.status == 200) {
			try {
				if (isJSON(XHR_SetLotteryTime.responseText)) {
					var json = JSON.parse(XHR_SetLotteryTime.responseText);
					if (checkTokenIdfail(json)) {
						getEle("lotteryNumSetLogList").value = XHR_SetLotteryTime.responseText;
						getEle("lotteryNumSetLogList").value = XHR_SetLotteryTime.responseText;
						var lotteryNumSetLogListStr = getEle("lotteryNumSetLogList").value;
						if(isJSON(lotteryNumSetLogListStr) && lotteryNumSetLogListStr != ""){
							var lotteryNumSetLogListJSON = JSON.parse(lotteryNumSetLogListStr).lotteryNumSetLog;
							changePage_init(showlotteryNumSetLog,lotteryNumSetLogListJSON.length,1,25,"LotteryOpsLogNowPage","LotteryOpsLogTotalPage");
						}
					}
				}
			} catch (error) {
				console_Log("handleGetLotteryTypeAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				XHR_SetLotteryTime.abort();
			}
		} else {
			disableLoadingPage();
			XHR_SetLotteryTime.abort();
		}
	}
}

function showlotteryNumSetLog(){
	var lotteryNumSetLogList = getEle("lotteryNumSetLogList").value;
	
	if (isJSON(lotteryNumSetLogList) && lotteryNumSetLogList != "") {
		var lotteryNumSetLogListJson = JSON.parse(getEle("lotteryNumSetLogList").value).lotteryNumSetLog;
		var titleArr = [ "操作者","彩種", "期號","操作內容", "操作時間", "操作IP", "詳細" ];
		var statusText = [ "等待中", "系統錄號", "手動錄號", "錄號錯誤", "撤單", "開獎中" ];
		var dataKeyArr = [ "opsAccName","title", "periodNum", "actionText", "opsDatetime", "ip", "actionId" ];
		if(getEle("lotteryNumSetLogPage") == null){
			var str = [];
			
			str.push('<div class="modal-content width-percent-960 margin-fix-4" id = "lotteryNumSetLogPage">		');
			str.push('<span class="close" onclick="onClickCloseModal();">×</span>		');
			str.push('<h3>彩票錄號操作日志</h3>		');
			str.push('	<div class="contentDiv2-12">	\n');
			str.push('		<div>操作類型：	\n');
			str.push('			<select id = "logAction">	\n');
			str.push('				<option value = 0>所有操作</option>	\n');
			str.push('				<option value = '+LOG_ACTION_LOTTERY_WITHDRAWAL_PERIOD_NUM+'>一般撤單</option>	\n');
			str.push('				<option value = '+LOG_ACTION_LOTTERY_DELETE_PERIOD_NUM+'>多期撤單</option>	\n');
			str.push('				<option value = '+LOG_ACTION_LOTTERY_NUM_FAIL+'>錄號錯誤</option>	\n');
			str.push('				<option value = '+LOG_ACTION_LOTTERY_NUM_SET+'>手動錄號</option>	\n');
			str.push('				<option value = '+LOG_ACTION_LOTTERY_CHECK_THE_LOTTERY+'>對獎</option>	\n');
			str.push('				<option value = '+LOG_ACTION_LOTTERY_REFUND+'>退款</option>	\n');
			str.push('			</select>操作時間：<input type="text" id = "startTime" onclick="newCalendar(this,this.id,1);">到<input type="text" id = "endTime" onclick="newCalendar(this,this.id,2);"><button onclick = "searchLotteryNumSetLog();">搜尋</button></div>	\n');
			str.push('		<table id = "lotteryOpsLogTable" class = "tr-hover"></table>	\n');
			str.push('	<p class="media-control text-center">	\n');
			str.push('	<span>一頁	\n');
			str.push('	<select id="selectLotteryOpsLogCount" onchange = "onChangeDataCountLimitSize(this.value);">	\n');
			str.push('	<option value="25">25筆</option>	\n');
			str.push('	<option value="50">50筆</option>	\n');
			str.push('	<option value="75">75筆</option>	\n');
			str.push('	<option value="100">100筆</option>	\n');
			str.push('	</select>	\n');
			str.push('	</span>	\n');
			str.push('	<a href="javascript:void(0);" onclick="onClickFirstPage();" class="backward"></a>	\n');
			str.push('	<a href="javascript:void(0);" onclick="onClickPreviousPage();" class="backward-fast"></a>	\n');
			str.push('	<span>總頁數：<i id="LotteryOpsLogNowPage">1</i></span><span>/</span>	\n');
			str.push('	<i id="LotteryOpsLogTotalPage">1</i>頁<a href="javascript:void(0);" onclick="onClickNextPage();" class="forward"></a>	\n');
			str.push('	<a href="javascript:void(0);" onclick="onClickLastPage();" class="forward-fast"></a></p>	\n');
	
			str.push('	</div>	\n');
			str.push('	</div>	\n');
			
			onClickOpenModal(str.join(''));
		}
		
		getEle("lotteryOpsLogTable").innerHTML = "";
		var tableObj = [];

		var x = titleArr.length;
		var startCount = toInt(getEle("startCount").value);
		var endCount = toInt(getEle("endCount").value);
		
		var obj = {};
		tableObj[0] = {};
		tableObj[0].type = "tbody";
		tableObj[0].html = [];
		for (var i = startCount; i < endCount+1; i++) {
			tableObj[0].html[i] = {};
			tableObj[0].html[i].type = "tr";
			tableObj[0].html[i].html = [];
			for (var j = 0; j < x; j++) {
				tableObj[0].html[i].html[j] = {};
				if (i == startCount) {
					tableObj[0].html[i].html[j].type = "th";
					tableObj[0].html[i].html[j].text = titleArr[j];
				} else {
					if(isJSON(lotteryNumSetLogListJson[0].detail)){
						if(dataKeyArr[j] == "title" || dataKeyArr[j] == "periodNum"){
							if(isJSON(lotteryNumSetLogListJson[i-1].detail)){
								var lotteryJson = JSON.parse(lotteryNumSetLogListJson[i-1].detail);
								if(typeof lotteryJson[dataKeyArr[j]] != "undefined" && lotteryJson[dataKeyArr[j]] != ""){
									tableObj[0].html[i].html[j].type = "td";
									tableObj[0].html[i].html[j].text = lotteryJson[dataKeyArr[j]];
								}
								else if(Array.isArray(lotteryJson) == true){
									var periodNumMax = 0;
									var periodNumMin = 0;
									var title = "--";
									for(var key in lotteryJson){
										if(dataKeyArr[j] == "title" && typeof lotteryJson[key][dataKeyArr[j]] != "undefined" && lotteryJson[key][dataKeyArr[j]] != ""){
											title = lotteryJson[key][dataKeyArr[j]];
											break;
										}
										else if(dataKeyArr[j] == "periodNum" && typeof lotteryJson[key][dataKeyArr[j]] != "undefined" && lotteryJson[key][dataKeyArr[j]] != ""){
											if(toInt(lotteryJson[key][dataKeyArr[j]]) > periodNumMax || periodNumMax == 0){
												periodNumMax = toInt(lotteryJson[key][dataKeyArr[j]]);
											}
											if(toInt(lotteryJson[key][dataKeyArr[j]]) < periodNumMin || periodNumMin == 0){
												periodNumMin = toInt(lotteryJson[key][dataKeyArr[j]]);
											}
										}
									}
									if(dataKeyArr[j] == "title"){
										tableObj[0].html[i].html[j].type = "td";
										tableObj[0].html[i].html[j].text = title;	
									}
									else if(dataKeyArr[j] == "periodNum"){
										tableObj[0].html[i].html[j].type = "td";
										tableObj[0].html[i].html[j].text = periodNumMin+"-"+periodNumMax;
									}
									
								}
							}
							
						}
						else if(dataKeyArr[j] == "actionId"){
							var actionText = typeof lotteryNumSetLogListJson[i-1]["actionText"] !== "undefined" ? lotteryNumSetLogListJson[i-1]["actionText"] : "";
							
							
							
							switch(toInt(lotteryNumSetLogListJson[i-1][dataKeyArr[j]])){
								case LOG_ACTION_LOTTERY_WITHDRAWAL_PERIOD_NUM:
									obj[i] = lotteryNumSetLogListJson[i-1].detail.toString();
									tableObj[0].html[i].html[j].type = "td";
									tableObj[0].html[i].html[j].text = '<a href="javascript:void(0);" onclick="showWithdrawalPeriodNumLog(\''+actionText+'\',\''+i+'\');">詳細</a>';
									break;
								case LOG_ACTION_LOTTERY_DELETE_PERIOD_NUM:
									obj[i] = lotteryNumSetLogListJson[i-1].detail.toString();
									tableObj[0].html[i].html[j].type = "td";
									tableObj[0].html[i].html[j].text = '<a href="javascript:void(0);" onclick="showDeletePeriodNumLog(\''+actionText+'\',\''+i+'\');">詳細</a>';
									break;
								case LOG_ACTION_LOTTERY_NUM_FAIL:
									obj[i] = lotteryNumSetLogListJson[i-1].detail.toString();
									tableObj[0].html[i].html[j].type = "td";
									tableObj[0].html[i].html[j].text = '<a href="javascript:void(0);" onclick="showLotteryNumFailLog(\''+actionText+'\',\''+i+'\');">詳細</a>';
									break;
								case LOG_ACTION_LOTTERY_NUM_SET:
									obj[i] = lotteryNumSetLogListJson[i-1].detail.toString();
									tableObj[0].html[i].html[j].type = "td";
									tableObj[0].html[i].html[j].text = '<a href="javascript:void(0);" onclick="showLotteryNumSetLog(\''+actionText+'\',\''+i+'\');">詳細</a>';
									break;
								case LOG_ACTION_LOTTERY_CHECK_THE_LOTTERY:
									obj[i] = lotteryNumSetLogListJson[i-1].detail.toString();
									tableObj[0].html[i].html[j].type = "td";
									tableObj[0].html[i].html[j].text = '<a href="javascript:void(0);" onclick="showCheckTheLotteryLog(\''+actionText+'\',\''+i+'\');">詳細</a>';
									break;
								case LOG_ACTION_LOTTERY_REFUND:
									obj[i] = lotteryNumSetLogListJson[i-1].detail.toString();
									tableObj[0].html[i].html[j].type = "td";
									tableObj[0].html[i].html[j].text = '<a href="javascript:void(0);" onclick="showLotteryRefundLog(\''+actionText+'\',\''+i+'\');">詳細</a>';
									break;
								default:
									obj[i] = "";
									tableObj[0].html[i].html[j].type = "td";
									tableObj[0].html[i].html[j].text = '<a href="javascript:void(0);">詳細</a>';
									break;
							}
							
						}
						else{
							tableObj[0].html[i].html[j].type = "td";
							tableObj[0].html[i].html[j].text = lotteryNumSetLogListJson[i-1][dataKeyArr[j]];
						}
					}
				}
			}
		}
		getEle("opsJsonData").value = JSON.stringify(obj);
		
		insertTableEle("lotteryOpsLogTable", tableObj);
	}
}

function showLotteryNumSetDataLog(actionText,opsId , titleArr , dataKeyArr){
	var jsonStr = getEle("opsJsonData").value;
	if(isJSON(jsonStr) && titleArr.length > 0 && dataKeyArr.length > 0){
		jsonStr = JSON.parse(jsonStr);
		if(typeof jsonStr[opsId] !== "undefined" && isJSON(jsonStr[opsId])){
//			var titleArr = ["彩種","期號","原本號碼","原本狀態","修改號碼","修改狀態"];
			var statusText = [ "等待中", "系統錄號", "手動錄號", "錄號錯誤", "撤單" , "已退款" ,"重新對獎完成", "開獎中" ];
//			var dataKeyArr = [ "title","periodNum", "data","status","updateData","updateStatus"];
			var str = [];
			str.push('<div class="modal-content width-percent-60">');
			str.push('	<span class="close" onclick="onClickCloseModalV2();">×</span>');
			str.push('	<h3>'+actionText+'</h3>');
			str.push('	<table id = "lotteryLogTable" class = "tr-hover"></table>');
			str.push('</div>');
			
			onClickOpenModalV2(str.join(''));
			
			
			var lotteryJSON = JSON.parse(jsonStr[opsId]);
			
			lotteryJSON =  Array.isArray(lotteryJSON) == false ? new Array(lotteryJSON) : lotteryJSON;
			var tableObj = [];
			
			
			var x = titleArr.length;
			var endCount = lotteryJSON.length;
			
			
			tableObj[0] = {};
			tableObj[0].type = "tbody";
			tableObj[0].html = [];
			for (var i = 0; i < endCount+1; i++) {
				tableObj[0].html[i] = {};
				tableObj[0].html[i].type = "tr";
				tableObj[0].html[i].html = [];
				for (var j = 0; j < x; j++) {
					tableObj[0].html[i].html[j] = {};
					if(i == 0){
						tableObj[0].html[i].html[j].type = "th";
						tableObj[0].html[i].html[j].text = ""+titleArr[j];
					}
					else{
						
						if(typeof lotteryJSON[i-1][dataKeyArr[j]] === "undefined" || lotteryJSON[i-1][dataKeyArr[j]] == null){
							tableObj[0].html[i].html[j].type = "td";
							tableObj[0].html[i].html[j].text = "";
						}
						else if(dataKeyArr[j] == "updateStatus" || dataKeyArr[j] == "status"){
							if(typeof statusText[lotteryJSON[i-1][dataKeyArr[j]]] !== "undefined" && statusText[lotteryJSON[i-1][dataKeyArr[j]]] != null){
								tableObj[0].html[i].html[j].type = "td";
								tableObj[0].html[i].html[j].text = statusText[lotteryJSON[i-1][dataKeyArr[j]]];
							}
							else{
								tableObj[0].html[i].html[j].type = "td";
								tableObj[0].html[i].html[j].text = "";
							}
						}
						else{
							tableObj[0].html[i].html[j].type = "td";
							tableObj[0].html[i].html[j].text = lotteryJSON[i-1][dataKeyArr[j]];
						}
					}
				}
			}
			insertTableEle("lotteryLogTable", tableObj);
		}
	}
}
//手動錄號
function showLotteryNumSetLog(actionText,opsId){
	var titleArr = ["彩種","期號","原本號碼","原本狀態","修改號碼","修改狀態"];
	var dataKeyArr = [ "title","periodNum", "data","status","updateData","updateStatus"];
	showLotteryNumSetDataLog(actionText,opsId,titleArr,dataKeyArr);
}
//錄號錯誤
function showLotteryNumFailLog(actionText,opsId){
	var titleArr = ["彩種","期號","原本號碼","原本狀態","修改號碼","修改狀態"];
	var dataKeyArr = [ "title","periodNum", "data","status","updateData","updateStatus"];
	showLotteryNumSetDataLog(actionText,opsId,titleArr,dataKeyArr);
}
//多期撤單
function showDeletePeriodNumLog(actionText,opsId){
	var titleArr = ["彩種","期號","原本狀態"];
	var dataKeyArr = [ "title","periodNum","status"];
	showLotteryNumSetDataLog(actionText,opsId,titleArr,dataKeyArr);
}
//一般撤單
function showWithdrawalPeriodNumLog(actionText,opsId){
	var titleArr = ["彩種","期號","原本狀態","修改狀態"];
	var dataKeyArr = [ "title","periodNum","status","updateStatus"];
	showLotteryNumSetDataLog(actionText,opsId,titleArr,dataKeyArr);
}
//對獎
function showCheckTheLotteryLog(actionText,opsId){
	var titleArr = ["彩種","期號","官方開獎時間","實際錄號時間","號碼","原本狀態","修改狀態"];
	var dataKeyArr = [ "title","periodNum","kjTime","actualKjTime", "data","status","updateStatus"];
	showLotteryNumSetDataLog(actionText,opsId,titleArr,dataKeyArr);
}
//退款
function showLotteryRefundLog(actionText,opsId){
	var titleArr = ["彩種","期號","原本號碼","原本狀態","修改號碼","修改狀態"];
	var dataKeyArr = [ "title","periodNum", "data","status","updateData","updateStatus"];
	showLotteryNumSetDataLog(actionText,opsId,titleArr,dataKeyArr);
}

function searchLotteryNumSetLog(){
	var logAction = getEle("logAction").value;
	var startTime = getEle("startTime").value;
	var endTime = getEle("endTime").value;
	
	lotteryNumSetLogAjax(endTime,startTime,logAction);
}


