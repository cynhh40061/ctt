
function loadPage(file) {
	XHR = createXMLHttpRequest();
	if (XHR != null && XHR != undefined) {
		XHR.open("GET", file, false);
		XHR.onreadystatechange = function() {
			if (XHR.readyState == 4) {
				if (XHR.status == 200) {
					document.getElementById("mainContain").innerHTML = 	XHR.responseText;
				}
			}
		}
		XHR.send(null);
	}
}

function loadPageByDiv(sourceDiv) {
	document.getElementById("mainContain").innerHTML = 	document.getElementById(sourceDiv).innerHTML;
}
function openModal(id, content, style = "modal"){
	if(!isNull(id) && id !='' && !isNull(document.getElementById(id)) && !isNull(content) && content != ''){
		document.getElementById(id).innerHTML = content;
		for(var i = 0 ; i < document.getElementById(id).classList.length ; i++){
			document.getElementById(id).classList.remove(document.getElementById(id).classList[i]);
		}
		if(isNull(style) || style == ''){
			style = 'modal';
		}
		var arr = style.split(',');
		var newArr = Array.from(new Set(arr));
		for(var j in newArr){
			if(!isNull(newArr[j]) && newArr[j] != ''){
				document.getElementById(id).classList.add(newArr[j]);
			}
		}
		document.getElementById(id).style.display = "block";
	}
}
function onClickOpenModal(content) {
	openModal("myModal",content);
}
function onClickOpenModalV2(content) {
	openModal("myModalV2",content);
}
function closeModal(id) {
	document.getElementById(id).innerHTML = '';
	document.getElementById(id).style.display = "none";
	for(var i = 0 ; i < document.getElementById(id).classList.length ; i++){
		document.getElementById(id).classList.remove(document.getElementById(id).classList[i]);
	}
	document.getElementById(id).classList.add('modal');
}
function onClickCloseModal() {
	closeModal("myModal");
}
function onClickCloseModalV2() {
	closeModal("myModalV2");
}

function onCheckModelPage(str ,functionName){
	var fName = functionName;
	if(fName == null || typeof fName == "undefined" || fName == ""){
		fName = "onCheckCloseModelPage()";
	}
	var text = '<div class="modal-content width-percent-20"> \n'+
				'<span class="close" onclick="onCheckCloseModelPage();">×</span> \n'+
			    '<h3 class="text-center">'+str+'</h3> \n'+
			    '<div class="btn-area">'+
// '<button class="btn-lg btn-orange"onclick = '+fName+'>確定</button> \n'+
// '<button class="btn-lg btn-gray"
// onclick="onCheckCloseModelPage();">取消</button> \n'+
			    	'<button onclick = '+fName+'>確定</button> \n'+
			    	'<button onclick="onCheckCloseModelPage();">取消</button> \n'+
			   ' </div></div> \n';
	getEle("myModalV2").innerHTML = text;
	getEle("myModalV2").style.display = "block";
}

function onCheckModelPageSetFunction(str ,functionName){
	if(functionName == null || typeof functionName == "undefined" || functionName == ""){
		functionName = function(){onCheckCloseModelPage();};
	}
	var text = '<div class="modal-content width-percent-20"> \n'+
				'<span class="close" onclick="onCheckCloseModelPage();">×</span> \n'+
			    '<h3 class="text-center">'+str+'</h3> \n'+
			    '<div class="btn-area"> \n'+
// '<button class="btn-lg btn-orange" id = "doubleCheck">確定</button> \n'+
// '<button class="btn-lg btn-gray"
// onclick="onCheckCloseModelPage();">取消</button> \n'+
			    	'<button id = "doubleCheck">確定</button> \n'+
			    	'<button onclick="onCheckCloseModelPage();">取消</button> \n'+
			   ' </div></div> \n';
	getEle("myModalV2").innerHTML = text;
	getEle("doubleCheck").onclick= functionName;
	getEle("myModalV2").style.display = "block";
}

function showModePage(str , functionName){
	var fName;
	if(functionName == null || typeof functionName == "undefined" || functionName == ""){
		fName = function(){onCheckCloseModelPage();};
	}
	else{
		fName = functionName;
	}
	var text = '<div class="modal-content width-percent-20"> \n'+
				'<span class="close" onclick="onCheckCloseModelPage();">×</span> \n'+
			    '<h3 class="text-center">'+str+'</h3> \n'+
			    '<div class="btn-area"> \n'+
// '<button class="btn-lg btn-orange" id = "doubleCheck">確定</button> \n'+
			    	'<button id = "doubleCheck">確定</button> \n'+
			   ' </div></div> \n';
	getEle("myModalV2").innerHTML = text;
	getEle("doubleCheck").onclick= fName;
	getEle("myModalV2").style.display = "block";
}


function onCheckCloseModelPage(){
	getEle("myModalV2").innerHTML = "";
	getEle("myModalV2").style.display = "none";
}

function onCheckModelPage2(str){
	var text = '<div class="modal-content width-percent-20"> \n'+
				'<span class="close" onclick="onCheckCloseModelPage();">×</span> \n'+
			    '<h3 class="text-center">'+str+'</h3> \n'+
			    '<div class="btn-area"> \n'+
// '<button class="btn-lg btn-orange"onclick =
// "onCheckCloseModelPage();">確定</button> \n'+
			    	'<button onclick = "onCheckCloseModelPage();">確定</button> \n'+
			   ' </div></div> \n';
	getEle("myModalV2").innerHTML = text;
	getEle("myModalV2").style.display = "block";
}

/* 左側選單收合按鈕 */
function leftNavClose() {
    document.getElementById("leftNav").classList.toggle("left-nav-close");
    document.getElementById("navBtn").classList.toggle("left-nav-flip");
    document.getElementById("accountOnline").classList.toggle("hidden");
    document.getElementById("containerSection").classList.toggle("padding-fix-1");
    document.getElementById("breadCrumb").classList.toggle("left-fix-1");
    document.getElementById("searchArea").classList.toggle("left-fix-2");
}

// 日曆開始
var calendarDate = '';

function drawCal(inc, cname, sort)
{
	var yr = parseInt(getEle('calendarYearText').value);
	var mth = parseInt(getEle('calendarMonthText').value);
	if(inc == 1 || inc == -1){
		month+=inc;
	}else{
		year=yr;
		month=mth;
	}
  if(month == 0 )
  {
    month=12;
    year--;
  }
  else if( month == 13 )
       {
         month=1;
         year++;
       }
  calendar(year, month, cname, sort);
}
function calendar(yr, mth, cname, sort)
{
  var td=new Date();
  var today=new Date( td.getFullYear(), td.getMonth(), td.getDate());
  var s='<table class=cal><col>';
  s+='<tr><th onclick="drawCal(-1,\''+cname+'\','+sort+')"><<<th colspan=5>';
  s+='<input type="text" id="calendarYearText" name="calendarYear" value="'+yr+'" onkeyup="chkYear(0,\''+cname+'\','+sort+');"><span>年</span>';
  s+='<input type="text" id="calendarMonthText" name="calendarMonth" value="'+mth+'" onkeyup="chkMounth(0,\''+cname+'\','+sort+');"><span>月</span>';
  s+='<th onclick="drawCal(1,\''+cname+'\','+sort+')">>>';
  mth--;
  s+='<tr><th>日<th>一<th>二<th>三<th>四<th>五<th>六';
  var dt=new Date(yr, mth, 1);
  var wd=dt.getDay(); // week day
  var i;
  if(sort == 1){
		var hr = '00';
		var min = '00';
		var sec = '00';
	}else if (sort == 2){
		var hr = '23';
		var min = '59';
		var sec = '59';
	}
  if(sort == 1){
		calendarDate = td.getFromFormat("yyyy/MM/dd") + ' 00:00:00';
	}else if (sort == 2){
		calendarDate = td.getFromFormat("yyyy/MM/dd") + ' 23:59:59';
	}
  s+='<tr>';
  for( ; wd > 0 ; wd--)   s+='<td>';
  for( i=1; i<32; )
  {
    dt=new Date(yr, mth, i);
    if( dt.toString() == today.toString() )
      s+='<td class="activecalander" onclick="clickCalendar(\''+cname+'\', '+yr+', '+mth+', '+i+', this)">'+i;
    else
      s+='<td class="nonecalander" onclick="clickCalendar(\''+cname+'\', '+yr+', '+mth+', '+i+', this)">'+i;
    dt=new Date(yr, mth, ++i);
    if( dt.getMonth() != mth ) break;
    wd=dt.getDay();
    if( wd == 0 )
      s+='<tr>';
  }
  for( ; wd < 6; wd++)  s+='<td>';
  s+='<tr class="calendarLastTr"><td colspan = "7" class="calendarLastTd">';
  s+='<span>時間:</span><input type="text" size="2" maxlength="2" width="2" placeholder="'+hr+'" id="calendarHr" onkeyup="chkHr();">';
  s+='<span>:</span><input type="text" size="2" maxlength="2" width="2" placeholder="'+min+'" id="calendarMin" onkeyup="chkMin();">';
  s+='<span>:</span><input type="text" size="2" maxlength="2" width="2" placeholder="'+sec+'" id="calendarSec" onkeyup="chkSec();"><br>';
  s+='<button onclick="clearCalendar();">清空</button>&nbsp<button onclick="choseTodayCalendar(\''+cname+'\','+sort+');">今天</button>&nbsp<button onclick="calendarDone(\''+cname+'\','+sort+');">更新</button></td></tr></table>';
  document.getElementById('calendar').innerHTML=s;
}
function chkYear(inc, cname, sort){
	if(checkInputNumberVal(getEle('calendarYearText').value) >= 0 && checkInputNumberVal(getEle('calendarYearText').value) <= 2100){
		getEle('calendarYearText').value = checkInputNumberVal(getEle('calendarYearText').value);
	}else{
		getEle('calendarYearText').value = '';
	}
	if(!isNull(getEle('calendarYearText').value) && getEle('calendarYearText').value != '' && getEle('calendarYearText').value.length == 4){
		drawCal(inc, cname, sort);
	}
}
function chkMounth(inc, cname, sort){
	if(checkInputNumberVal(getEle('calendarMonthText').value) >= 1 && checkInputNumberVal(getEle('calendarMonthText').value) <= 12){
		getEle('calendarMonthText').value = checkInputNumberVal(getEle('calendarMonthText').value);
	}else{
		getEle('calendarMonthText').value = '';
	}
	if(!isNull(getEle('calendarMonthText').value) && getEle('calendarMonthText').value != ''){
		drawCal(inc, cname, sort);
	}
}
function chkHr(){
	if(checkInputNumberVal(getEle('calendarHr').value) >= 0 && checkInputNumberVal(getEle('calendarHr').value) <= 23){
		getEle('calendarHr').value = checkInputNumberVal(getEle('calendarHr').value);
	}else{
		getEle('calendarHr').value = '';
	}
}
function chkMin(){
	if(checkInputNumberVal(getEle('calendarMin').value) >= 0 && checkInputNumberVal(getEle('calendarMin').value) <= 59){
		getEle('calendarMin').value = checkInputNumberVal(getEle('calendarMin').value);
	}else{
		getEle('calendarMin').value = '';
	}
}
function chkSec(){
	if(checkInputNumberVal(getEle('calendarSec').value) >= 0 && checkInputNumberVal(getEle('calendarSec').value) <= 59){
		getEle('calendarSec').value = checkInputNumberVal(getEle('calendarSec').value);
	}else{
		getEle('calendarSec').value = '';
	}
}
function clearCalendar(){
	var act = document.getElementsByClassName('activecalander');
	getEle('calendarHr').value = '';
	getEle('calendarMin').value = '';
	getEle('calendarSec').value = '';
	for(var len = 0; len < act.length;len++){
		act[len].className = 'nonecalander';
	}
	calendarDate = '';
}
function choseTodayCalendar(cname, sort){
	var now = new Date();
	if(sort == 1){
		getEle(cname).value = now.getFromFormat("yyyy/MM/dd") + ' 00:00:00';
		calendarDate = now.getFromFormat("yyyy/MM/dd") + ' 00:00:00';
	}else if (sort == 2){
		getEle(cname).value = now.getFromFormat("yyyy/MM/dd") + ' 23:59:59';
		calendarDate = now.getFromFormat("yyyy/MM/dd") + ' 23:59:59';
	}
	document.getElementById('calendar').style.display="none";
	document.getElementById('wrapperCalendar').style.display="none";
}
function calendarDone(cname, sort){
	var tmpInput = document.getElementById(cname);
	if(sort == 1){
		var hr = '00';
		var min = '00';
		var sec = '00';
	}else if (sort == 2){
		var hr = '23';
		var min = '59';
		var sec = '59';
	}
	if(!isNull(getEle('calendarHr').value) && getEle('calendarHr').value != ''){
		if(getEle('calendarHr').value.length == 1){
			hr = '0'+getEle('calendarHr').value;
		}else if(getEle('calendarHr').value.length == 2){
			hr = getEle('calendarHr').value;
		}
	}
	if(!isNull(getEle('calendarMin').value) && getEle('calendarMin').value != ''){
		if(getEle('calendarMin').value.length == 1){
			min = '0'+getEle('calendarMin').value;
		}else if(getEle('calendarMin').value.length == 2){
			min = getEle('calendarMin').value;
		}
	}
	if(!isNull(getEle('calendarSec').value) && getEle('calendarSec').value != ''){
		if(getEle('calendarSec').value.length == 1){
			sec = '0'+getEle('calendarSec').value;
		}else if(getEle('calendarSec').value.length == 2){
			sec = getEle('calendarSec').value;
		}
	}
	if(calendarDate != ''){
		tmpInput.value = calendarDate.split(' ')[0]+' '+hr+':'+min+':'+sec;
		
		document.getElementById('calendar').style.display="none";
		document.getElementById('wrapperCalendar').style.display="none";
	}else{
		choseTodayCalendar(cname, sort);
		document.getElementById('calendar').style.display="none";
		document.getElementById('wrapperCalendar').style.display="none";
	}
}
function clickCalendar(cname, yr, mth, i, ele){
	var act = document.getElementsByClassName('activecalander');
	if(!isNull(getEle('calendarYearText').value) && getEle('calendarYearText').value != ''){
		yr = getEle('calendarYearText').value;
	}
	if(!isNull(getEle('calendarMonthText').value) && getEle('calendarMonthText').value != ''){
		mth = getEle('calendarMonthText').value;
	}
	for(var len = 0; len < act.length;len++){
		act[len].className = 'nonecalander';
	}
	ele.classList.add('activecalander');
	    if(mth<10){
	        mth = '0' + mth;
	    }
	    if(i<10){
	        i = '0' + i;
	    }
	    calendarDate = yr+'/'+mth+'/'+i;
}

var td=new Date();
var year=td.getFullYear();
var month=td.getMonth()+1;

function newCalendar(ele, cname, sort){
    document.getElementById("wrapperCalendar").style.display = "block";
    document.getElementById('calendar').style.display="none";
    td=new Date();
    year=td.getFullYear();
    month=td.getMonth()+1;
    calendar(year, month, cname, sort);
    document.getElementById('calendar').style.display="";
    var topC = document.getElementById('calendar').scrollHeight;
    var leftC = document.getElementById('calendar').scrollWidth;
    var topB = ele.getBoundingClientRect().top;
    var leftB = ele.getBoundingClientRect().left;
    if(topB>topC){
        document.getElementById('calendar').style.top = topB-topC+'px';
    }else{
        document.getElementById('calendar').style.top = '0px';
    }
    if(leftB>leftC){
        document.getElementById('calendar').style.left = leftB-leftC+'px';
    }else{
        document.getElementById('calendar').style.left = '0px';
    }
}

function closeCalendar(){
    document.getElementById('calendar').style.display ='none';
    document.getElementById('wrapperCalendar').style.display ='none';
}
function getNDaysAgo(n){
	var now = new Date();
	var date = new Date(now.getTime()-n*24*3600*1000);
	var yr = date.getFullYear();
	var mth = date.getMonth()+1 > 9 ? date.getMonth()+1 : '0'+(date.getMonth()+1);
	var day = date.getDate() > 9 ? date.getDate() : '0'+(date.getDate());
	var newDate = yr+'/'+mth+'/'+day;
return newDate;
}
function checkNull(val) {
	if (typeOf(val) === "null" || typeOf(val) === "undefined" || val == null) {
		return '';
	} else {
		return val;
	}
}

function chkTableToExcel(tableId,dataName){
	var csvString = tableToCSV(tableId);
	
	if(csvString.split('\n').length == 2){
		onCheckModelPage('請查詢需匯出資料!', '');
	}else{
		onCheckModelPage('確定匯出資料為csv檔?', 'exportToCSV(\'' + encodeURIComponent(csvString) + '\',\''+dataName+'\')');
	}
}

function tableToCSV(tableId) {
	var getTable = getEle(tableId).rows;
	var csvStr = '';

	for (var row = 0; row < getTable.length; row++) {
		if (!isNull(getTable[row].cells)) {
			var cellStr = getTable[row].cells;
			for (var cell = 0; cell < cellStr.length; cell++) {
				csvStr += cellStr[cell].innerText;
				if (cell < (parseInt(cellStr.length) - 1)) {
					csvStr += ',';
				}
			}
			csvStr += '\r\n';
		}
	}

	return csvStr;
}

function exportToCSV(_csvString,dataName) {
	var downloadLink = document.createElement("a");
	var nowTime = new Date();

	downloadLink.download = dataName + "DataTable_" + nowTime.getFromFormat("yyyy_MM_dd_hh_mm_ss") + ".csv";
	downloadLink.innerHTML = "Download File";

	if (window.webkitURL != null) {
		var code = _csvString;
		if (navigator.appVersion.indexOf("Win") == -1 && navigator.appVersion.indexOf("Mac OS X") == -1) {
			downloadLink.href = "data:application/csv;charset=utf-8," + code;
		} else {
			downloadLink.href = "data:application/csv;charset=utf-8,%EF%BB%BF" + code;
		}
	}

	downloadLink.click();
	onCheckCloseModelPage();
}