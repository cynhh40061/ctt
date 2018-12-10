var v_BW_XHR = null;

// second load js , to initial env
function blackWhite(){
	console.log("Browser has loaded blackWhite.js");
	f_BW_init();
	f_BW_query();
	return true;
}

function f_BW_init(){
	// console.log("f_BW_init");
	var str = '';
	if(document.getElementsByName("BW_json_data").length!=1){
		str += '\n<input type="hidden" name="BW_json_data">';
		str += '\n<input type="hidden" name="BW_q_name_val">';
		str += '\n<input type="hidden" name="BW_q_ip_val">';
		str += '\n<input type="hidden" name="BW_rid">';
	}
	if(str!=''){
		document.getElementById("extraHidden").innerHTML = str;
	}
	delete str;
	str = undefined;
	if(document.getElementById("mainContain") != null){
		document.getElementById("mainContain").innerHTML = "";
	}
	if(document.getElementById("blackandwhitelist") != null){
		document.getElementById("blackandwhitelist").innerHTML = "";
	}
	if(document.getElementById("myModal") != null){
		document.getElementById("myModal").innerHTML = "";
	}
	if(document.getElementById("myModalV2") != null){
		document.getElementById("myModalV2").innerHTML = "";
	}
}
// first load js , to initial env 
if(document.getElementsByName("BW_json_data").length!=1){
	f_BW_init();
	f_BW_query();
}

function f_BW_showTableDefault(){
	var str = [];
	str.push('<div id="contentDiv2-13">');
	str.push('    <div class="contentDiv2-13">');
	str.push('        <ul>');
	str.push('            <li>名稱：<input type="text" name="BW_q_name" >IP：<input type="text" name="BW_q_ip" onkeyup="f_BW_checkIP(this, this.value);" ></li>');
	str.push('            <li><button onclick="f_BW_query();">搜尋</button><button onclick="f_BW_showDetail()">新增</button></li>');
	str.push('        </ul>');
	str.push('       <table>');
	str.push('           <tr><th>名稱</th><th>ＩＰ</th><th>類型</th><th>所在地區</th><th>描述</th><th colspan="2">操作</th></tr><x></x>');
	str.push('       </table>');
	str.push('    </div>');
	str.push('</div>');
	str.push('<div id="blackandwhitelist"></div>');
	return str.join("");
}
function f_BW_closeDetail(){
	document.getElementById("blackandwhitelist").innerHTML = "";
	onClickCloseModal();
	onClickCloseModalV2();
	return true;
}
function f_BW_save(){
	if(!f_BW_update()){
		closeModal("myModalV2");
		return false;
	}
	document.getElementById("blackandwhitelist").innerHTML = "";
	return true;
}
function f_BW_remove(id){
	getEle("BW_rid").value = id;
	f_BW_update();
	document.getElementById("blackandwhitelist").innerHTML = "";
	return true;
}
function f_BW_showDetail(id=-1){
	var str = [];
	str.push('<div class="modal-content modal-central width-percent-460 blackandwhitelist">');
	str.push('    <span class="close" onclick="f_BW_closeDetail();">&times;</span>');
	str.push('        <table>');
	str.push('            <tr>');
	str.push('                <th>ＩＰ</th>');
	str.push('                <td><input type="text" name="BW_ip" onkeyup="f_BW_checkIP(this, this.value);" onchange="checkIpRule(this.value);"></td>');
	str.push('            </tr>');
	str.push('            <tr>');
	str.push('                <th>名稱</th>');
	str.push('                <td><input type="text" name="BW_name"></td>');
	str.push('            </tr>');
	str.push('            <tr>');
	str.push('                <th>描述</th>');
	str.push('                <td><input type="text" name="BW_text"></td>');
	str.push('            </tr>');
	str.push('            <tr>');
	str.push('                <th>所在地區</th>');
	str.push('                <td><input type="text" name="BW_area"></td>');
	str.push('            </tr>');
	str.push('            <tr>');
	str.push('                <th>類型</th>');
	str.push('                <td><input type="radio" id="listChoice1" checked="checked" name="BW_type" value="0">黑名單<input type="radio" id="listChoice2" name="BW_type" value="1">白名單</td>');
	str.push('            </tr>');
	str.push('        </table>');
	str.push('        <div class="button-areau">');
	str.push('            <button onclick="onCheckModelPage(\'您確定要取消嗎?\', \'f_BW_closeDetail();\')">取消</button>');
	str.push('            <button onclick="onCheckModelPage(\'您確定要保存嗎?\', \'f_BW_save();\')">保存</button>');
	str.push('            <input type="hidden" name="BW_id">');
	str.push('        </div>');
	str.push('</div>');
	document.getElementById("blackandwhitelist").innerHTML = str.join("");
	delete str;
	str = undefined;
	if(id==-1){
		return true;
	} else {
		if (getEle("BW_json_data").value == null || getEle("BW_json_data").value!="") {
			if(isJSON(getEle("BW_json_data").value)){
				var jsonObj = JSON.parse(getEle("BW_json_data").value);
				if(typeof jsonObj[""+id] != "undefined"){
					getEle("BW_ip").value = jsonObj[""+id]['ip'];
					getEle("BW_name").value = jsonObj[""+id]['name'];
					getEle("BW_text").value = jsonObj[""+id]['text'];
					getEle("BW_area").value = jsonObj[""+id]['area'];
					getEle("BW_id").value = jsonObj[""+id]['id'];
					if(jsonObj[""+id]['type']=="1" || jsonObj[""+id]['type']=="true"){
						document.getElementsByName("BW_type")[0].checked = false;
						document.getElementsByName("BW_type")[1].checked = true;
					}else{
						document.getElementsByName("BW_type")[0].checked = true;
						document.getElementsByName("BW_type")[1].checked = false;
					}
				}
				delete jsonObj;
				jsonObj = undefined;
				return true;
			}
		}
	}
	return false;
}

function checkIpRule(ip){
	if (/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(ip)) {  
		return (true);
	}else{
		alert("You have entered an invalid IP address!");
		return (false);
	}
}

function f_BW_checkIP(ele, ip){
	if(ip!=null && ip!=""){
		var val = "";
		var tmpStr = "";
		var allow = [];
		try{
			val = ip;
			tmpStr = "";
			allow = ["0","1","2","3","4","5","6","7","8","9","."];
			for(var i=0; i<val.length;i++){
				if(allow.includes(val.substring(i,i+1))){
					tmpStr += val.substring(i,i+1);
				}else{
					ele.value = tmpStr;
				}
			}
		}catch(error){
			console.log(error.message);
			ele.value = tmpStr;
		}finally{
			delete val;
			delete tmpStr;
			delete allow;
			val = undefined;
			tmpStr = undefined;
			allow = undefined;
		}
	}
	return false;
}
function f_BW_query(){
	// console.log("f_BW_query");
	v_BW_XHR = null;
	v_BW_XHR = checkXHR(v_BW_XHR);
	if (typeof v_BW_XHR != "undefined" && v_BW_XHR != null) {
		var tmpstr = "";
		v_BW_XHR.timeout = 10000;
		v_BW_XHR.ontimeout = function() {
			disableLoadingPage();
			if(v_BW_XHR!=null){
				v_BW_XHR.abort();
				v_BW_XHR = null;
			}
		}
		v_BW_XHR.onerror = function() {
			disableLoadingPage();
			if(v_BW_XHR!=null){
				v_BW_XHR.abort();
				v_BW_XHR = null;
			}
		}
		if(getEle("BW_q_name")!=null && getEle("BW_q_name").value!=""){
			getEle("BW_q_name_val").value = getEle("BW_q_name").value;
			if(tmpstr==""){
				tmpstr = "name=" + encodeURIComponent(getEle("BW_q_name").value);
			} else {
				tmpstr += "&name=" + encodeURIComponent(getEle("BW_q_name").value);
			}
		} else {
			getEle("BW_q_name_val").value = "";
		}
		if(getEle("BW_q_ip")!=null && getEle("BW_q_ip").value!=""){
			getEle("BW_q_ip_val").value = getEle("BW_q_ip").value;
			if(tmpstr==""){
				tmpstr = "ip=" + encodeURIComponent(getEle("BW_q_ip").value);
			} else {
				tmpstr += "&ip=" + encodeURIComponent(getEle("BW_q_ip").value);
			}
		} else {
			getEle("BW_q_ip_val").value = "";
		}
		var tmpURL = "./BlackWhite!showAll.php?date=" + getNewTime();
		if(tmpstr!=""){
			 tmpURL = "./BlackWhite!show.php?date=" + getNewTime();
		}
		v_BW_XHR.onreadystatechange = f_BW_handleQuery;
		v_BW_XHR.open("POST", tmpURL, true);
		v_BW_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		v_BW_XHR.send(tmpstr);
		enableLoadingPage();
		delete tmpURL;
		delete tmpStr;
		tmpURL = undefined;
		tmpStr = undefined;
	}
}
function f_BW_handleQuery() {
	// console.log(" in f_BW_handleQuery() 1");
	if (v_BW_XHR.readyState == 4) {
		if (v_BW_XHR.status == 200) {
			try {
				if (isJSON(v_BW_XHR.responseText)) {
					f_BW_init();
					getEle("BW_json_data").value = v_BW_XHR.responseText;
					// console.log(getEle("BW_json_data").value);
					var jsonObj = JSON.parse(v_BW_XHR.responseText);
					var tmpStr1 = f_BW_showTableDefault();
					var tmpStr2 = '';
					for(var x in jsonObj){
						if(typeof jsonObj[x]['name'] != "undefined"){
							tmpStr2 += '<tr>';
							tmpStr2 += '<td>'+jsonObj[x]['name']+'</td>';
							tmpStr2 += '<td>'+jsonObj[x]['ip']+'</td>';
							var tmpStr3 = (jsonObj[x]['type']=="1" || jsonObj[x]['type']=="true") ? "白名單" : "黑名單";
							tmpStr2 += '<td>'+tmpStr3+'</td>';
							delete tmpStr3;
							tmpStr2 += '<td>'+jsonObj[x]['area']+'</td>';
							tmpStr2 += '<td>'+jsonObj[x]['text']+'</td>';
							tmpStr2 += '<td><a href="javascript:void(0);" onclick="f_BW_showDetail('+x+')">修改</a></td>';
							tmpStr2 += '<td><a href="javascript:void(0);" onclick="f_BW_remove('+jsonObj[x]['id']+')">刪除</a></td>';
							tmpStr2 += "</tr>";
						}
					}
					if(tmpStr2!=''){
						tmpStr1 = tmpStr1.replace('<x></x>', tmpStr2);
					}
					delete tmpStr2;
					tmpStr2 = undefined;
					getEle("mainContain").innerHTML = tmpStr1;
					getEle("BW_q_name").value = getEle("BW_q_name_val").value;
					getEle("BW_q_ip").value = getEle("BW_q_ip_val").value;
					delete tmpStr1;
					tmpStr1 = undefined;
//					getEle("loginDiv").style.display = "none";
					delete jsonObj;
					jsonObj = undefined;
				}
			} catch (error) {
				console_Log("f_BW_handleQuery error Message:"+error.message);
				console_Log("f_BW_handleQuery error Code:"+(error.number & 0xFFFF));
				console_Log("f_BW_handleQuery facility Code:"+(error.number>>16 & 0x1FFF));
				console_Log("f_BW_handleQuery error Name:"+error.name);
			} finally {
				disableLoadingPage();
				if(v_BW_XHR!=null){
					v_BW_XHR.abort();
					v_BW_XHR = null;
				}
			}
		} else {
			disableLoadingPage();
			if(v_BW_XHR!=null){
				v_BW_XHR.abort();
				v_BW_XHR = null;
			}
		}
	}
}
function f_BW_update(){
	v_BW_XHR = null;
	v_BW_XHR = checkXHR(v_BW_XHR);
	if (typeof v_BW_XHR != "undefined" && v_BW_XHR != null) {
		var tmpstr = "";
		v_BW_XHR.timeout = 10000;
		v_BW_XHR.ontimeout = function() {
			disableLoadingPage();
			if(v_BW_XHR!=null){
				v_BW_XHR.abort();
				v_BW_XHR = null;
			}
		}
		v_BW_XHR.onerror = function() {
			disableLoadingPage();
			if(v_BW_XHR!=null){
				v_BW_XHR.abort();
				v_BW_XHR = null;
			}
		}
		var mode = 0;
		if(getEle("BW_rid")!=null && getEle("BW_rid").value!=""){
			mode = 3;
			if(tmpstr==""){
				tmpstr = "mode=" + encodeURIComponent(mode) + "&id=" + encodeURIComponent(getEle("BW_rid").value);
			} else {
				tmpstr += "&mode=" + encodeURIComponent(mode) + "&id=" + encodeURIComponent(getEle("BW_rid").value);
			}
			getEle("BW_rid").value = "";
		} else if (getEle("BW_id")!=null){
			if(getEle("BW_id").value == "" || getEle("BW_id").value==""){
				mode = 1;
				if(tmpstr==""){
					tmpstr = "mode=" + encodeURIComponent(mode);
				} else {
					tmpstr += "&mode=" + encodeURIComponent(mode);
				}
			} else {
				mode = 2;
				if(tmpstr==""){
					tmpstr = "mode=" + encodeURIComponent(mode);
				} else {
					tmpstr += "&mode=" + encodeURIComponent(mode);
				}
			}
		}
		if(mode==1 || mode==2){
			if(getEle("BW_ip")!=null && getEle("BW_ip").value==""){
				alert("No IP address!");
				return false;
			}else{
				if(!checkIpRule(getEle("BW_ip").value)){
					return false;
				}
			}
			if(getEle("BW_name")!=null && getEle("BW_name").value==""){
				alert("No name!");
				return false;
			}
		}
		if(getEle("BW_ip")!=null && getEle("BW_ip").value!=""){
			if(tmpstr==""){
				tmpstr = "ip=" + encodeURIComponent(getEle("BW_ip").value);
			} else {
				tmpstr += "&ip=" + encodeURIComponent(getEle("BW_ip").value);
			}
		}
		if(getEle("BW_name")!=null && getEle("BW_name").value!=""){
			if(tmpstr==""){
				tmpstr = "name=" + encodeURIComponent(getEle("BW_name").value);
			} else {
				tmpstr += "&name=" + encodeURIComponent(getEle("BW_name").value);
			}
		}
		if(getEle("BW_text")!=null && getEle("BW_text").value!=""){
			if(tmpstr==""){
				tmpstr = "text=" + encodeURIComponent(getEle("BW_text").value);
			} else {
				tmpstr += "&text=" + encodeURIComponent(getEle("BW_text").value);
			}
		}
		if(getEle("BW_area")!=null && getEle("BW_area").value!=""){
			if(tmpstr==""){
				tmpstr = "area=" + encodeURIComponent(getEle("BW_area").value);
			} else {
				tmpstr += "&area=" + encodeURIComponent(getEle("BW_area").value);
			}
		}
		// console.log(document.getElementsByName("BW_type").length);
		if(document.getElementsByName("BW_type").length==2){
			var tmpstr1 = document.getElementsByName("BW_type")[0].checked ? encodeURIComponent(0) : encodeURIComponent(1);
			if(tmpstr==""){
				tmpstr1
				tmpstr = "type=" + tmpstr1;
			} else {
				tmpstr += "&type=" + tmpstr1;
			}
			delete tmpstr1;
			tmpstr1 = undefined;
		}
		// console.log(tmpstr);
		if(getEle("BW_id")!=null && getEle("BW_id").value!=""){
			var id = getEle("BW_id").value;
			if(getEle("BW_id").value == "" || getEle("BW_id").value=="0"){
				id = -1;
			}
			if(tmpstr==""){
				tmpstr = "id=" + encodeURIComponent(id);
			} else {
				tmpstr += "&id=" + encodeURIComponent(id);
			}
			delete id;
			id = undefined;
		}
		// console.log(tmpstr);
		var tmpURL = "./BlackWhite!update.php?date=" + getNewTime();
		v_BW_XHR.onreadystatechange = f_BW_handleUpdate;
		v_BW_XHR.open("POST", tmpURL, true);
		v_BW_XHR.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		v_BW_XHR.send(tmpstr);
		// console.log(tmpstr);
		enableLoadingPage();
		delete tmpURL;
		delete tmpStr;
		delete mode;
		tmpURL = undefined;
		tmpStr = undefined;
		mode = undefined;
		return true;
	}
	return false;
}
function f_BW_handleUpdate() {
	if (v_BW_XHR.readyState == 4) {
		if (v_BW_XHR.status == 200) {
			try {
				if (isJSON(v_BW_XHR.responseText)) {
					// console.log(v_BW_XHR.responseText);
					var jsonObj = JSON.parse(v_BW_XHR.responseText);
//					getEle("loginDiv").style.display = "none";
					if(typeof jsonObj["result"] != "undefined"){
						if(jsonObj["result"] == 0){
							showModePage('更新失敗', function(){onClickCloseModalV2();onCheckCloseModelPage();});
						} else if(jsonObj["result"] == 1){
							showModePage('更新成功', function(){f_BW_query();onCheckCloseModelPage();});
						}
					}
					delete jsonObj;
					jsonObj = undefined;
				}
			} catch (error) {
				console_Log("f_BW_handleUpdate error Message:"+error.message);
				console_Log("f_BW_handleUpdate error Code:"+(error.number & 0xFFFF));
				console_Log("f_BW_handleUpdate facility Code:"+(error.number>>16 & 0x1FFF));
				console_Log("f_BW_handleUpdate error Name:"+error.name);
			} finally {
				disableLoadingPage();
				if(v_BW_XHR!=null){
					v_BW_XHR.abort();
					v_BW_XHR = null;
				}
			}
		} else {
			disableLoadingPage();
			if(v_BW_XHR!=null){
				v_BW_XHR.abort();
				v_BW_XHR = null;
			}
		}
	}
}
