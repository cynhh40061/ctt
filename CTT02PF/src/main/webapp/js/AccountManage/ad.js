const AD_JS = true;
var AD_XHR = null;
var isAdPositionRepeat = false;
var adPositionTitleJsonKey = {
		"leftBig":"左側可關閉廣告(大)",
		"leftSmall":"左側可關閉廣告(小)",
		"rightBig":"右側可關閉廣告(大)",
		"rightSmall":"右側可關閉廣告(小)",
		"banner1":"廣告banner1",
		"banner2":"廣告banner2",
		"banner3":"廣告banner3",
		"banner4":"廣告banner4",
		"banner5":"廣告banner5"
};
var adPositionJsonKey = [ 'leftBig', 'leftSmall', 'rightBig', 'rightSmall', 'banner1', 'banner2', 'banner3', 'banner4', 'banner5' ];


function getAdAjax(){
	AD_XHR = checkXHR(AD_XHR);
	if (typeof AD_XHR != "undefined" && AD_XHR != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&platformId="+platformId;
		AD_XHR.timeout = 10000;
		AD_XHR.ontimeout=function(){disableLoadingPage();AD_XHR.abort();}
		AD_XHR.onerror=function(){disableLoadingPage();AD_XHR.abort();}
		AD_XHR.onreadystatechange = handleGetAdDataAjax;
		AD_XHR.open("POST",
				"./Advertising!getAd.php?date="
						+ getNewTime(), true);
		AD_XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		AD_XHR.send(tmpstr);
		enableLoadingPage();
	}
}

function handleGetAdAjaxx(){
	if (AD_XHR.readyState == 4) {
		if (AD_XHR.status == 200) {
			try{
				if(isJSON(AD_XHR.responseText)){
					var json = JSON.parse(AD_XHR.responseText);
					console_Log(json);
					if(checkTokenIdfail(json)){
						getEle("adAuthJson").value = AD_XHR.responseText;
						showSetAdPage();
						searchAdData();
					}
				}
			}catch(error){
				console_Log("handleGetAdDataAjax error:"+error.message);
			}
			finally{
				disableLoadingPage();
				AD_XHR.abort();
			}
		}
		else{
			disableLoadingPage();
			AD_XHR.abort();
		}
	}
}

function showSetAdPage() {
	var str = [];
	var tableTitalArr = [];
	var now = new Date();

	if (isJSON(getEle("adAuthJson").value)) {
		var json = JSON.parse(getEle("adAuthJson").value);

//		str.push('<ul class="Div2-7-searcharea"> \n');
//		str.push('<li> \n');
//		str.push('<span>站台查詢：</span> \n');
//		str.push('</li> \n');
//		str.push('<li> \n');
//		str.push('<select id = "platformSelect"></select> \n');
//		str.push('</li> \n');
//		str.push('<li> \n');
//		str.push('<button onclick="searchAdData();">搜尋</button> \n');
//		str.push('</li> \n');
//		str.push('<li> \n');
//		str.push('<button onclick = "showAddAdPage()">新增</button> \n');
//		str.push('</li> \n');
//		str.push('</ul> \n');
		str.push('<div class="Div2-7-tablearea"> \n');
		str.push('<table class="table-zebra" id = "adTable">');
		str.push('</table> \n');
		str.push('</div>');
		getEle("mainContain").innerHTML = str.join("");

		tableTitalArr.push('<tbody>');
		tableTitalArr.push('<tr><th colspan="5">左側可關閉廣告(大)</th></tr>');
		tableTitalArr
				.push('<tr><td>圖檔:</td><td id="leftBigSrc" colspan="2"></td><td rowspan="2"><button onclick = "onClickUpdateAd(1,0);">修改</button></td><td rowspan="2"><button onclick = "onClickDeleteAd(1,0);">清除</button></td></tr>');
		tableTitalArr.push('<tr><td>連接:</td><td id="leftBigUrl" colspan="2"></td></tr>');

		tableTitalArr.push('<tr><th colspan="5">左側可關閉廣告(小)</th></tr>');
		tableTitalArr
				.push('<tr><td>圖檔:</td><td id="leftSmallSrc" colspan="2"></td><td rowspan="2"><button onclick = "onClickUpdateAd(1,1);">修改</button></td><td rowspan="2"><button onclick = "onClickDeleteAd(1,1);">清除</button></td></tr>');
		tableTitalArr.push('<tr><td>連接:</td><td id="leftSmallUrl" colspan="2"></td></tr>');
		tableTitalArr.push('<tr><th colspan="5">右側可關閉廣告(大)</th></tr>');

		tableTitalArr
				.push('<tr><td>圖檔:</td><td id="rightBigSrc" colspan="2"></td><td rowspan="2"><button onclick = "onClickUpdateAd(1,2);">修改</button></td><td rowspan="2"><button onclick = "onClickDeleteAd(1,2);">清除</button></td></tr>');
		tableTitalArr.push('<tr><td>連接:</td><td id="rightBigUrl" colspan="2"></td></tr>');
		tableTitalArr.push('<tr><th colspan="5">右側可關閉廣告(小)</th></tr>');

		tableTitalArr
				.push('<tr><td>圖檔:</td><td id="rightSmallSrc" colspan="2"></td><td rowspan="2"><button onclick = "onClickUpdateAd(1,3);">修改</button></td><td rowspan="2"><button onclick = "onClickDeleteAd(1,3);">清除</button></td></tr>');
		tableTitalArr.push('<tr><td>連接:</td><td id="rightSmallUrl" colspan="2"></td></tr>');
		tableTitalArr.push('<tr><th colspan="5">廣告banner</th></tr>');

		tableTitalArr
				.push('<tr><td rowspan="2">1</td><td>圖檔:</td><td id="banner1Src"></td><td rowspan="2"><button onclick = "onClickUpdateAd(1,4);">修改</button></td><td rowspan="2"><button onclick = "onClickDeleteAd(1,4);">清除</button></td></tr>');
		tableTitalArr.push('<tr><td>連接:</td><td id="banner1Url"></td></tr>');
		tableTitalArr
				.push('<tr><td rowspan="2">2</td><td>圖檔:</td><td id="banner2Src"></td><td rowspan="2"><button onclick = "onClickUpdateAd(1,5);">修改</button></td><td rowspan="2"><button onclick = "onClickDeleteAd(1,5);">清除</button></td></tr>');
		tableTitalArr.push('<tr><td>連接:</td><td id="banner2Url"></td></tr>');
		tableTitalArr
				.push('<tr><td rowspan="2">3</td><td>圖檔:</td><td id="banner3Src"></td><td rowspan="2"><button onclick = "onClickUpdateAd(1,6);">修改</button></td><td rowspan="2"><button onclick = "onClickDeleteAd(1,6);">清除</button></td></tr>');
		tableTitalArr.push('<tr><td>連接:</td><td id="banner3Url"></td></tr>');
		tableTitalArr
				.push('<tr><td rowspan="2">4</td><td>圖檔:</td><td id="banner4Src"></td><td rowspan="2"><button onclick = "onClickUpdateAd(1,7);">修改</button></td><td rowspan="2"><button onclick = "onClickDeleteAd(1,7);">清除</button></td></tr>');
		tableTitalArr.push('<tr><td>連接:</td><td id="banner4Url"></td></tr>');
		tableTitalArr
				.push('<tr><td rowspan="2">5</td><td>圖檔:</td><td id="banner5Src"></td><td rowspan="2"><button onclick = "onClickUpdateAd(1,8);">修改</button></td><td rowspan="2"><button onclick = "onClickDeleteAd(1,8);">清除</button></td></tr>');
		tableTitalArr.push('<tr><td>連接:</td><td id="banner5Url"></td></tr>');
		tableTitalArr.push('<tbody>');

		getEle("adTable").innerHTML = tableTitalArr.join("");

//		for (var i = 0; i < json.data.length; i++) {
//			var adData = json.data[i];
//			addOptionNoDup("platformSelect", adData.platformId, adData.platformId);
//		}
	}
	showAdData();
}

function showAdData() {
	if (typeof getEle("adJsonData") != "undefined" && isJSON(getEle("adJsonData").value)) {
		var json = JSON.parse(getEle("adJsonData").value);
		var adData = json.adData;

		for (var i = 0; i < adPositionJsonKey.length; i++) {
			for (var j = 0; j < adData.length; j++) {
				if (adData[j].adPositionCode == adPositionJsonKey[i]) {
					getEle(adPositionJsonKey[i] + 'Url').innerHTML = adData[j].adUrl;
					getEle(adPositionJsonKey[i] + 'Src').innerHTML = adData[j].adSrc;
					getEle(adPositionJsonKey[i] + 'Url').value = adData[j].adUrl;
					getEle(adPositionJsonKey[i] + 'Src').value = adData[j].adSrc;
				}
			}
		}
	} else {
		//
	}
}

function getAdDataAjax(platformId) {
	AD_XHR = checkXHR(AD_XHR);
	if (typeof AD_XHR != "undefined" && AD_XHR != null) {
		var tmpstr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&platformId=" + platformId;
		AD_XHR.timeout = 10000;
		AD_XHR.ontimeout = function() {
			disableLoadingPage();
			AD_XHR.abort();
		}
		AD_XHR.onerror = function() {
			disableLoadingPage();
			AD_XHR.abort();
		}
		AD_XHR.onreadystatechange = handleGetAdDataAjax;
		AD_XHR.open("POST", "./Advertising!getAdData.php?date=" + getNewTime(), true);
		AD_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		AD_XHR.send(tmpstr);
		enableLoadingPage();
	}
}

function handleGetAdDataAjax() {
	if (AD_XHR.readyState == 4) {
		if (AD_XHR.status == 200) {
			try {
				if (isJSON(AD_XHR.responseText)) {
					var json = JSON.parse(AD_XHR.responseText);
					console_Log(json);
					if (checkTokenIdfail(json)) {
						getEle("adJsonData").value = AD_XHR.responseText;
//						showAdTable();
						showSetAdPage()
					}
				}
			} catch (error) {
				console_Log("handleGetAdDataAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				AD_XHR.abort();
			}
		} else {
			disableLoadingPage();
			AD_XHR.abort();
		}
	}
}

function showAdTable() {
	if (typeof getEle("adJsonData") != "undefined" && isJSON(getEle("adJsonData").value)) {
		var json = JSON.parse(getEle("adJsonData").value);
		var adJsonKey = [ 'platformId', 'adSrc', 'adUrl', 'adText', 'lastUpdateTime' ];
		var adData = json.adData;

		// deleteAllTableTr("adTable");
		for (var i = 0; i < adData.length; i++) {
			var adDataArr = [];
			for (var k = 0; k < adJsonKey.length; k++) {
				var tmpStr = (typeOf(adData[i][adJsonKey[k]]) != "undefined" && adData[i][adJsonKey[k]] != null) ? adData[i][adJsonKey[k]].toString()
						: "";
				adDataArr.push(tmpStr.toString());
				delete tmpStr;
			}
			var platformIdx = (typeOf(adData[i].platformId) != "undefined" && adData[i].platformId != null) ? adData[i].platformId.toString() : "";
			var adPositionx = (typeOf(adData[i].adPositionCode) != "undefined" && adData[i].adPositionCode != null) ? adData[i].adPositionCode
					.toString() : "";
			var adSrcx = (typeOf(adData[i].adSrc) != "undefined" && adData[i].adSrc != null) ? "" + adData[i].adSrc.toString() : "";
			var adUrlx = (typeOf(adData[i].adUrl) != "undefined" && adData[i].adUrl != null) ? "" + adData[i].adUrl.toString() : "";
			var adTextx = (typeOf(adData[i].adText) != "undefined" && adData[i].adText != null) ? "" + adData[i].adText.toString() : "";
			var arr = [];
			arr[0] = '<button type="button" name = "updateAdBnt' + i + '" onclick="';
			arr[1] = 'onClickUpdateAd(\'';
			arr[2] = platformIdx.toString();
			arr[3] = '\',\'';
			arr[4] = adPositionx.toString();
			arr[5] = '\',\'';
			arr[6] = adUrlx.toString();
			arr[7] = '\',\'';
			arr[8] = adSrcx.toString();
			arr[9] = '\',\'';
			arr[10] = adTextx.toString();
			arr[11] = '\');';
			arr[12] = '" >修改</button> \n';
			arr[13] = '<button type="button" name = "deleteAdBnt' + i + '" onclick="';
			arr[14] = 'onClickDeleteAd(\'';
			arr[15] = platformIdx.toString();
			arr[16] = '\',\'';
			arr[17] = adPositionx.toString();
			arr[18] = '\');';
			arr[19] = '" >刪除</button>';
			adDataArr.push(arr.join(""));
			// insertTableTrTd("adTable",adDataArr);
			// console.log(adDataArr);
			delete arr;
			delete adDataArr;
			delete adTextx;
			delete adUrlx;
			delete adSrcx;
			delete adPositionx;
			delete platformIdx;
		}
		delete adData;
		delete adJsonKey;
		delete json;
	} else {
		//
	}
}

function showAddAdPage() {

	if (isJSON(getEle("adAuthJson").value)) {
		var json = JSON.parse(getEle("adAuthJson").value);
		var str = [];

		str.push('<div class="modal-content width-percent-460 margin-fix-9"> \n');
		str.push('<span class="close" onclick="onClickCloseModal();">×</span> \n');
		str.push('<table class="role-add-permission"> \n');
		str.push('<tbody>\n');
		str.push('<tr>\n');
		str.push('<td>站點：</td>\n');
		str.push('<td><select id = "addAdPlatformSelect" onchange = "checkAdPosition();"></select></td>\n');
		str.push('</tr>\n');
		str.push('<tr>\n');
		str.push('<td>廣告位置：</td>\n');
		str.push('<td>\n');
		str
				.push('<input type="text" id = "adPosition" style="width:100px" maxlength= "20" placeholder="(用英文命名)" onkeyup = "checkAdPosition();" onchange="checkAdPositionIsRepeat()"><span id = "adPositionIcon">\n');
		str.push('</td>\n');
		str.push('</tr>\n');
		str.push('<tr>\n');
		str.push('<td>備註：</td>\n');
		str.push('<td>\n');
		str
				.push('<textarea type="text" id = "imgText" maxlength= "100" placeholder="(說明圖片大小及位置說明)限100字以內" onkeyup = "checkImgText();"></textarea> <span id = "imgTextIcon"></span>\n');
		str.push('</td>\n');
		str.push('</tr>\n');
		str.push('</tbody>\n');
		str.push('</table>\n');
		str.push('<div class="btn-area">\n');
		str.push('<button class="btn-lg btn-orange" id = "confomAddAdDataBnt" >確定</button>\n');
		str.push('<button class="btn-lg btn-gray" onclick="onClickCloseModal();">取消</button>\n');
		str.push('</div>\n');
		str.push('</div>\n');

		onClickOpenModal(str.join(""));

		getEle("confomAddAdDataBnt").onclick = function() {
			onClickAddAdData();
		};

		for (var i = 0; i < json.data.length; i++) {
			var adData = json.data[i];
			addOptionNoDup("addAdPlatformSelect", adData.platformId, adData.platformId);
		}
	}

}

function checkImgText() {
	if (typeOf(getEle("imgText")) != "undefined") {
		var imgText = getEle("imgText").value;
		if (imgText != "" && imgText.length > 0) {
			setIconPass("imgTextIcon");
			return true;
		}
		setIconFail("imgTextIcon");
	}
	return false;
}
function checkAdPosition() {
	if (typeOf(getEle("adPosition")) != "undefined") {
		var imgText = getEle("adPosition").value;
		if (imgText != "" && imgText.length > 0) {
			setIconPass("adPositionIcon");
			return true;
		}
		setIconFail("adPositionIcon");
	}
	return false;
}

function onClickAddAdData() {
	if (checkImgText() && checkAdPosition()) {

		var platformId = getEle("addAdPlatformSelect").value;
		var adPosition = getEle("adPosition").value;
		var imgText = getEle("imgText").value;
		addAdDataAjax(platformId, adPosition, imgText);
	}

}

function addAdDataAjax(platformId, adPosition, imgText) {
	AD_XHR = checkXHR(AD_XHR);
	if (typeof AD_XHR != "undefined" && AD_XHR != null) {
		var tmpstr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&platformId=" + encodeURIComponent(platformId) + "&adPosition="
				+ encodeURIComponent(adPosition) + "&imgText=" + encodeURIComponent(imgText);
		AD_XHR.timeout = 10000;
		AD_XHR.ontimeout = function() {
			disableLoadingPage();
			AD_XHR.abort();
		}
		AD_XHR.onerror = function() {
			disableLoadingPage();
			AD_XHR.abort();
		}
		AD_XHR.onreadystatechange = handleAddAdDataAjax;
		AD_XHR.open("POST", "./Advertising!addAdData.php?date=" + getNewTime(), true);
		AD_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		AD_XHR.send(tmpstr);
		enableLoadingPage();
	}
}

function handleAddAdDataAjax() {
	if (AD_XHR.readyState == 4) {
		if (AD_XHR.status == 200) {
			try {
				if (isJSON(AD_XHR.responseText)) {
					var json = JSON.parse(AD_XHR.responseText);
					console_Log(json);
					if (checkTokenIdfail(json)) {
						if (typeOf(json.isSuccess) != "undefined" && json.isSuccess != null) {
							if (json.isSuccess) {
								showModePage("新增成功", "");
							}
						}
					}
				}
			} catch (error) {
				console_Log("handleAddAdDataAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				AD_XHR.abort();

				if (checkTokenIdfail(json)) {
					if (typeOf(json.isSuccess) != "undefined" && json.isSuccess != null) {
						if (json.isSuccess) {
							onClickCloseModal();
							searchAdData();
						}
					}
				}
			}
		} else {
			disableLoadingPage();
			AD_XHR.abort();
		}
	}
}

//function onClickUpdateAd(platformId, adPosition, adUrl, adSrc, adText) {
function onClickUpdateAd(platformId, adPosition) {
	console_Log("updateAd");
//	showUpdateAdPage(platformId, adPosition, adUrl, adSrc, adText);
	showUpdateAdPage(platformId, adPosition);
}
function showUpdateAdPage(platformId, adPosition) {
	var adPosition = adPositionJsonKey[adPosition];
	var adPositionTitle = adPositionTitleJsonKey[adPosition];
	if (isJSON(getEle("adAuthJson").value)) {
		var json = JSON.parse(getEle("adAuthJson").value);
		var str = [];

		str.push('<div class="modal-content width-percent-460 margin-fix-9"> \n');
		str.push('<span class="close" onclick="onClickCloseModal();">×</span> \n');
		str.push('<table class="role-add-permission"> \n');
		str.push('<tbody>\n');
		str.push('<tr>\n');
		str.push('<th colspan="2">'+adPositionTitle+'</th>\n');
		str.push('</tr>\n');
		str.push('<tr>\n');
		str.push('<td>圖檔:</td>\n');
		str.push('<td>\n');
//		action="./Advertising!getIframe.php"
		str.push('		<form enctype="multipart/form-data" method="post" name="imgFormSrc" id="imgFormSrc" target="imgIframe">');
		str.push('			<input type="file" name="file" style="width:200px">\n');
		str.push('			<input type="submit" value="上傳" onclick="upLoadImg();">\n');
		str.push('		</form>');
		str.push('	<iframe name="imgIframe" src="" width="0" height="0"></iframe>')
		str.push('</td>\n');
		str.push('</tr>\n');
		str.push('<tr>\n');
		str.push('<td>連接:</td>\n');
		str.push('<td>\n');
		str.push('<input type="text" id = "imgUrl" style="width:200px" onchange = "checkImgUrl(this.file);"><span id = imgUrlIcon></span>\n');
		str.push('</td>\n');
		str.push('</tr>\n');
		str.push('</tbody>\n');
		str.push('</table>\n');
		str.push('<div class="btn-area">\n');
		str.push('<button class="btn-lg btn-orange" id = "confomUpdateAdBnt" >保存</button>\n');
		str.push('<button class="btn-lg btn-gray" onclick="onClickCloseModal();">取消</button>\n');
		str.push('</div>\n');
		str.push('</div>\n');

		onClickOpenModal(str.join(""));

		getEle("imgUrl").value = getEle(adPosition + 'Url').value;

		getEle("confomUpdateAdBnt").onclick = function() {
			confomUpdateAd(platformId, adPosition);
		};
	}
}

function upLoadImg(){
	var file =new FormData(getEle("imgFormSrc"));
	imgAjax(file);
}

function imgAjax(file){
	console_Log(file);
	AD_XHR = checkXHR(AD_XHR);
	if (typeof AD_XHR != "undefined" && AD_XHR != null) {
		AD_XHR.timeout = 10000;
		AD_XHR.ontimeout = function() {
			disableLoadingPage();
			AD_XHR.abort();
		}
		AD_XHR.onerror = function() {
			disableLoadingPage();
			AD_XHR.abort();
		}
		AD_XHR.onreadystatechange = handImgAjax;
		AD_XHR.open("POST", "./Advertising!getIframe.php?date=" + getNewTime(), true);
//		AD_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		AD_XHR.send(file);
		enableLoadingPage();
	}
}
function handImgAjax() {
	if (AD_XHR.readyState == 4) {
		if (AD_XHR.status == 200) {
			try {
				
			} catch (error) {
				console_Log("handImgAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				AD_XHR.abort();
			}
		} else {
			disableLoadingPage();
			AD_XHR.abort();
		}
	}
}

function showUpdateAdPageOranl(platformId, adPosition, adUrl, adSrc, adText) {

	if (isJSON(getEle("adAuthJson").value)) {
		var json = JSON.parse(getEle("adAuthJson").value);
		var str = [];

		str.push('<div class="modal-content width-percent-460 margin-fix-9"> \n');
		str.push('<span class="close" onclick="onClickCloseModal();">×</span> \n');
		str.push('<table class="role-add-permission"> \n');
		str.push('<tbody>\n');
		str.push('<tr>\n');
		str.push('<td>站點：</td>\n');
		str.push('<td>' + platformId + '</td>\n');
		str.push('</tr>\n');
		str.push('<tr>\n');
		str.push('<td>圖片位置：</td>\n');
		str.push('<td>' + adPosition + '</td>\n');
		str.push('</tr>\n');
		str.push('<tr>\n');
		str.push('<td>圖片路徑(SRC)</td>\n');
		str.push('<td>\n');
		str.push('<input type="text" id = "imgSrc" style="width:200px" onchange = "checkImgSrc();"> <span id = imgSrcIcon></span>\n');
		str.push('</td>\n');
		str.push('</tr>\n');
		str.push('<tr>\n');
		str.push('<td>圖片點擊路徑(URL)</td>\n');
		str.push('<td>\n');
		str.push('<input type="text" id = "imgUrl" style="width:200px" onchange = "checkImgUrl();"><span id = imgUrlIcon></span>\n');
		str.push('</td>\n');
		str.push('</tr>\n');
		str.push('<tr>\n');
		str.push('<td>備註</td>\n');
		str.push('<td>\n');
		str.push(adText + '\n');
		str.push('</td>\n');
		str.push('</tr>\n');
		str.push('</tbody>\n');
		str.push('</table>\n');
		str.push('<div class="btn-area">\n');
		str.push('<button class="btn-lg btn-orange" id = "confomUpdateAdBnt" >確定</button>\n');
		str.push('<button class="btn-lg btn-gray" onclick="onClickCloseModal();">取消</button>\n');
		str.push('</div>\n');
		str.push('</div>\n');

		onClickOpenModal(str.join(""));

		getEle("imgSrc").value = adSrc;
		getEle("imgUrl").value = adUrl;

		getEle("confomUpdateAdBnt").onclick = function() {
			confomUpdateAd(platformId, adPosition);
		};
	}
}
function getImgSrc(val){
	console_Log(val.value);
	getEle("imgSrc").value = val.value;
}
function confomUpdateAd(platformId, adPosition) {
	var imgUrl = getEle("imgUrl").value.trim();
	var imgSrc = getEle("imgSrc").value.trim();
//	if (checkImgSrc() && checkImgUrl()) {
		updateAdDataAjax(platformId, adPosition, imgUrl, imgSrc);
//	}
}

function checkImgSrc() {
	var imgSrc = getEle("imgSrc").value;
	if (imgSrc != "" && imgSrc.length > 0) {
		if (validateURL(imgSrc)) {
			setIconPass("imgSrcIcon");
			return true;
		}
	}
	setIconFail("imgSrcIcon");
	return false;
}

function checkImgUrl() {
	var imgUrl = getEle("imgUrl").value;
	if (imgUrl != "" && imgUrl.length > 0) {
		if (validateURL(imgUrl)) {
			setIconPass("imgUrlIcon");
			return true;
		}
	} else if (imgUrl == "" && imgUrl.length == 0) {
		return true;
	}
	setIconFail("imgUrlIcon");
	return false;
}

function updateAdDataAjax(platformId, adPosition, imgUrl, imgSrc) {

	AD_XHR = checkXHR(AD_XHR);
	if (typeof AD_XHR != "undefined" && AD_XHR != null) {
		var tmpstr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&platformId=" + platformId + "&adPosition=" + adPosition
				+ "&imgUrl=" + imgUrl + "&imgSrc=" + imgSrc;
		AD_XHR.timeout = 10000;
		AD_XHR.ontimeout = function() {
			disableLoadingPage();
			AD_XHR.abort();
		}
		AD_XHR.onerror = function() {
			disableLoadingPage();
			AD_XHR.abort();
		}
		AD_XHR.onreadystatechange = handleUpdateAdDataAjax;
		AD_XHR.open("POST", "./Advertising!updateAdData.php?date=" + getNewTime(), true);
		AD_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		AD_XHR.send(tmpstr);
		enableLoadingPage();
	}
}

function handleUpdateAdDataAjax() {
	if (AD_XHR.readyState == 4) {
		if (AD_XHR.status == 200) {
			try {
				if (isJSON(AD_XHR.responseText)) {
					var json = JSON.parse(AD_XHR.responseText);
					console_Log(json);
					if (checkTokenIdfail(json)) {
						if (typeOf(json.isSuccess) != "undefined" && json.isSuccess != null) {
							if (json.isSuccess) {
								showModePage("修改成功", "");
								searchAdData();
							}
						}
					}
				}
			} catch (error) {
				console_Log("handleGetAdDataAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				AD_XHR.abort();
				if (checkTokenIdfail(json)) {
					if (typeOf(json.isSuccess) != "undefined" && json.isSuccess != null) {
						if (json.isSuccess) {
							onClickCloseModal();
							searchAdData();
						}
					}
				}
			}
		} else {
			disableLoadingPage();
			AD_XHR.abort();
		}
	}
}
function onClickDeleteAd(platformId, adPosition) {
	var json = JSON.parse(getEle("adJsonData").value);
	var adData = json.adData;
	var adPosition = adPositionJsonKey[adPosition];
	var src = getEle(adPosition+'Src').value;
	var url = getEle(adPosition+'Url').value;
	
	console_Log("deleteAd");
	if(!isNull(src) && !isNull(url) && src != '' && url != ''){
		deleteAdDataAjax(platformId, adPosition);
	}
}
function deleteAdDataAjax(platformId, adPosition) {
	AD_XHR = checkXHR(AD_XHR);
	if (typeof AD_XHR != "undefined" && AD_XHR != null) {
		var tmpstr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&platformId=" + platformId + "&adPosition=" + adPosition;
		AD_XHR.timeout = 10000;
		AD_XHR.ontimeout = function() {
			disableLoadingPage();
			AD_XHR.abort();
		}
		AD_XHR.onerror = function() {
			disableLoadingPage();
			AD_XHR.abort();
		}
		AD_XHR.onreadystatechange = handleDeleteAdDataAjax;
		AD_XHR.open("POST", "./Advertising!deleteAdData.php?date=" + getNewTime(), true);
		AD_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		AD_XHR.send(tmpstr);
		enableLoadingPage();
	}
}

function handleDeleteAdDataAjax() {
	if (AD_XHR.readyState == 4) {
		if (AD_XHR.status == 200) {
			try {
				if (isJSON(AD_XHR.responseText)) {
					var json = JSON.parse(AD_XHR.responseText);
					console_Log(json);
					if (checkTokenIdfail(json)) {
						if (typeOf(json.isSuccess) != "undefined" && json.isSuccess != null) {
							if (json.isSuccess) {
								showModePage("刪除成功", "");
								searchAdData();
							}

						}
					}

				}
			} catch (error) {
				console_Log("handleGetAdDataAjax error:" + error.message);
			} finally {
				disableLoadingPage();
				AD_XHR.abort();
				if (checkTokenIdfail(json)) {
					if (typeOf(json.isSuccess) != "undefined" && json.isSuccess != null) {
						if (json.isSuccess) {
							searchAdData();
						}

					}
				}
			}
		} else {
			disableLoadingPage();
			AD_XHR.abort();
		}
	}
}
function searchAdData() {
	console_Log("searchAdData");
//	var platformId = getEle("platformSelect").value;
	var platformId = 1;
	getAdDataAjax(platformId);
}

function checkAdPositionAjax(platformId, adPosition) {
	AD_XHR = checkXHR(AD_XHR);
	if (typeof AD_XHR != "undefined" && AD_XHR != null) {
		var tmpstr = "tokenId=" + encodeURIComponent(getEle("tokenId").value) + "&platformId=" + platformId + "&adPosition=" + adPosition;
		AD_XHR.timeout = 10000;
		AD_XHR.ontimeout = function() {
			disableLoadingPage();
			AD_XHR.abort();
		}
		AD_XHR.onerror = function() {
			disableLoadingPage();
			AD_XHR.abort();
		}
		AD_XHR.onreadystatechange = handleCheckAdPositionAjax;
		AD_XHR.open("POST", "./Advertising!checkAdPosition.php?date=" + getNewTime(), true);
		AD_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		AD_XHR.send(tmpstr);
	}
}

function handleCheckAdPositionAjax() {
	if (AD_XHR.readyState == 4) {
		if (AD_XHR.status == 200) {
			try {
				if (isJSON(AD_XHR.responseText)) {
					var json = JSON.parse(AD_XHR.responseText);
					console_Log(AD_XHR.responseText);
					if (checkTokenIdfail(json)) {
						if (typeOf(json.isCheckAdPosition) != "undefined" && json.isCheckAdPosition != null) {
							if (json.isCheckAdPosition) {
								isAdPositionRepeat = true;
								setIconPass("adPositionIcon");
							} else {
								isAdPositionRepeat = false;
								setIconFail("adPositionIcon");
							}
						}
					}

				}
			} catch (error) {
				console_Log("handleCheckAdPositionAjax error:" + error.message);
			} finally {
				AD_XHR.abort();
			}
		} else {
			AD_XHR.abort();
		}
	}
}

function checkAdPositionIsRepeat() {
	var addAdPlatformId = getEle("addAdPlatformSelect").value;
	var addAdPosition = getEle("adPosition").value;
	if (checkAdPosition()) {
		checkAdPositionAjax(addAdPlatformId, addAdPosition);
	}

}
