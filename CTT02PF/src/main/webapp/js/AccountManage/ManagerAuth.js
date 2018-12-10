XHR_AuthGroup = null;
const MANAGER_AUTH_JS = true;


function authGroup(){
	authGroup_inti();
	getAuthGroupAjax();
	return true;
}

function authGroup_inti(){
	var str = '';
	if(document.getElementsByName("authJsonModify").length!=1){
		str += '\n<input type="hidden" name="authJsonModify">';
		str += '\n<input type="hidden" name="authJsonOrgin">';
		str += '\n<input type="hidden" name="authGroupJson">';
		str += '\n<input type="hidden" name="upAuthJson">';
		str += '\n<input type="hidden" name="authGroupId">';
		str += '\n<input type="hidden" name="authGroupType">';
		str += '\n<input type="hidden" name="PortopnAuthGroup">';
	}
	if(str!=''){
		document.getElementById("extraHidden").innerHTML = str;
	}
	delete str;
	str = undefined;
	if(document.getElementById("mainContain") != null){
		document.getElementById("mainContain").innerHTML = "";
	}
	if(document.getElementById("myModal") != null){
		document.getElementById("myModal").innerHTML = "";
	}
	if(document.getElementById("myModalV2") != null){
		document.getElementById("myModalV2").innerHTML = "";
	}
}

function getAuthGroupAjax() {
	XHR_AuthGroup = checkXHR(XHR_AuthGroup);
	if (typeof XHR_AuthGroup != "undefined" && XHR_AuthGroup != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value);
		XHR_AuthGroup.timeout = 10000;
		XHR_AuthGroup.ontimeout = function() {
			disableLoadingPage();XHR_AuthGroup.abort();
		}
		XHR_AuthGroup.onerror = function() {
			disableLoadingPage();XHR_AuthGroup.abort();
		}
		XHR_AuthGroup.onreadystatechange = handleGetAuthGroupAjax;
		XHR_AuthGroup.open("POST", "./AuthGroup!getAuthGroup.php?date=" + getNewTime(),true);
		XHR_AuthGroup.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XHR_AuthGroup.send(tmpstr);
		enableLoadingPage();
	}
}

function handleGetAuthGroupAjax() {
	if (XHR_AuthGroup.readyState == 4) {
		if (XHR_AuthGroup.status == 200) {
			try {
				console.log(XHR_AuthGroup.responseText);
				setClassFix();
				var json = JSON.parse(XHR_AuthGroup.responseText);
				if(checkTokenIdfail(json)){
					getEle("authGroupJson").value = XHR_AuthGroup.responseText;
					showAuth();
				}
			} catch (error) {
				console_Log("handleAuthData error:"+error.message);
			} finally {
				disableLoadingPage();
				XHR_AuthGroup.abort();
			}
		}
	}
}

function showAuth(){
	var json = JSON.parse(getEle("authGroupJson").value).data;
	if(json != "" && json != null && json != undefined){
		var str1 = "<section><div class='role-create'><button onclick='getPortopnAuthGroupAjax();'>分配權限</button><button onclick='javascript:getUpLevelAuthAjax();'>"+
		"新增權限</button><button onclick='getCreateAuthGroupAjax();' >角色創建</button></div><table class='role-list tr-hover'><tbody><tr><th>編號</th><th>角色</th><th>建立時間</th><th>修改時間</th><th>功能</th></tr>";
		var str2 = "";
		for(var i = 0 ; i < Object.keys(json).length ; i++){
			if(json[i].groupId != "" && json[i].groupId !=null && json[i].groupId != undefined){
				var tmpStr2 = "updateAuthGroupClick("+json[i].groupId+")";
				str2+="<tr><td>"+json[i].groupId+"</td><td>"+json[i].groupText+"</td><td>"+json[i].createDatetime+"</td><td>"+json[i].updateDatetime+"</td><td><button onclick = "+tmpStr2+" >編輯</button></td></tr>";

			}
		}
		var strEnd = '</tbody></table></section>';
		
		var authHtml = "";
		authHtml = authHtml.concat(str1+str2+strEnd);
		getEle("mainContain").innerHTML = authHtml;
		
	}
}

function updateAuthGroupClick(groupId){
	getEle("authGroupId").value = groupId;
	getUpdateAuthGroupAjax(groupId);
}

function showAuthGroup(){
	var json = JSON.parse(getEle("authJsonModify").value);
	if(json != "" && json != null && json != undefined){
		var str1 = '<div class="modal-content width-percent-960 margin-fix-5"><span class="close" onclick="onClickCloseModal();">×</span>'+
		'<p class="role-name-area"><span class="float-left" id = "authGroupName"></span><span class="float-right text-right"><button onclick ="onCheckModelPage(\'確定是否保存?\',\'authGroupSave()\')">保存</button>'+
		'<button onclick="javascript:onClickCloseModal();">取消</button></span></p><p class="role-create-header">'+
		'<span class="float-left">功能名稱</span><span class="float-right">功能備註</span></p><div class="role-create-list">';
		var str2 = "";
		for(var i = 0 ; i < Object.keys(json).length ; i++){
			if(!isNull(json[i].adminAuth)){
				var authType = json[i].adminAuth;
			}
			if(authType == ACC_LEVEL_ADMIN){
				str2 +='<ul><li>';
				str2 += '<p><a href="javascript:onClickDisplay('+json[i].authId+')" id = "upLevelAuthIcon'+json[i].authId+'" class="icon-arrow-open"></a>'+json[i].authName+'<span>('+json[i].authId+')'+
					'</span>&nbsp;&nbsp;<span id = "level3Total_'+json[i].authId+'"></span><span class="note">'+json[i].authRemark+'</span></p>';		
				str2 += '<ul id = "ul_'+json[i].authId+'">';
			}else {
				str2 +='<ul><li>';
				str2 += '<p><a href="javascript:onClickDisplay('+json[i].authId+')" id = "upLevelAuthIcon'+json[i].authId+'" class="icon-arrow-open"></a>'+
					'<input type="checkbox" name = "auth" id = "auth'+json[i].authId+'" value = "'+json[i].authId+'" onchange = "chkGroupCheckbox(this);">'+json[i].authName+'<span>('+json[i].authId+')'+
					'</span>&nbsp;&nbsp;<span id = "level3Total_'+json[i].authId+'"></span><span class="note">'+json[i].authRemark+'</span></p>';		
				str2 += '<ul id = "ul_'+json[i].authId+'">';
			}
			for(var j = 0 ; j < Object.keys(json[i].list).length ; j++){
				if(!isNull(json[i].list[j].adminAuth)){
					var authType = json[i].list[j].adminAuth;
				}
				if(authType == ACC_LEVEL_ADMIN){
					str2 +='<li>';
					str2 += '<p><a href="javascript:onClickDisplay('+json[i].list[j].authId+')" id = "upLevelAuthIcon'+json[i].list[j].authId+'" class="icon-arrow-open"></a>'+json[i].list[j].authName+'<span>('+json[i].list[j].authId+')'+
						'</span>&nbsp;&nbsp;<span id = "level3Total_'+json[i].list[j].authId+'"></span><span class="note">'+json[i].list[j].authRemark+'</span></p>';	
						str2 +='<ul id = "ul_'+json[i].list[j].authId+'">';
				}else{
					str2 +='<li>';
					str2 += '<p><a href="javascript:onClickDisplay('+json[i].list[j].authId+')" id = "upLevelAuthIcon'+json[i].list[j].authId+'" class="icon-arrow-open"></a>'+
						'<input type="checkbox" name = "auth" id = "auth'+json[i].list[j].authId+'" value = '+json[i].list[j].authId+' onchange = "chkGroupCheckbox(this);">'+json[i].list[j].authName+'<span>('+json[i].list[j].authId+')'+
						'</span>&nbsp;&nbsp;<span id = "level3Total_'+json[i].list[j].authId+'"></span><span class="note">'+json[i].list[j].authRemark+'</span></p>';	
						str2 +='<ul id = "ul_'+json[i].list[j].authId+'">';
				}
					for(var k = 0 ; k < Object.keys(json[i].list[j].list).length ; k++){
						if(!isNull(json[i].list[j].list[k].adminAuth)){
							var authType = json[i].list[j].list[k].adminAuth;
						}
						if(authType == ACC_LEVEL_ADMIN){
							str2 += '<li><p>'+json[i].list[j].list[k].authName+'<span>('+json[i].list[j].list[k].authId+')'+
							'</span><span class="note">'+json[i].list[j].list[k].authRemark+'</span></p></li>';	
						}else{
							str2 += '<li><p>'+
							'<input type="checkbox" name = "auth" id = "auth'+json[i].list[j].list[k].authId+'" value = '+json[i].list[j].list[k].authId+' onchange = "chkGroupCheckbox(this);">'+json[i].list[j].list[k].authName+'<span>('+json[i].list[j].list[k].authId+')'+
							'</span><span class="note">'+json[i].list[j].list[k].authRemark+'</span></p></li>';	
						}
					}
					
				str2 +='</li></ul>';
			}
			str2 +='</ul>';
			str2 +='</li></ul>';
		}
		var strEnd = '</div><p class="role-name-area margin-fix-10">'+
		'<span class="float-right text-right"><button onclick ="onCheckModelPage(\'確定是否保存?\',\'authGroupSave()\')">保存</button><button onclick="javascript:onClickCloseModal();">取消</button></span></p></div>';
		
		onClickOpenModal(str1+str2+strEnd);
	}
}

function showAddAuth(){
	if(getEle("upAuthJson").value != "" && getEle("upAuthJson").value != null && getEle("upAuthJson").value != undefined){
		var str = '<div class="modal-content width-percent-960 margin-fix-9"><span class="close" onclick="onClickCloseModal();">×</span>'+
				'<p><label><input name = "levelType" type="radio" value = "1" onchange = "changeAuthLevelType(this.value)">第一層</label><label>'+
				'<input name = "levelType" type="radio" value = "2" onchange = "changeAuthLevelType(this.value)">第二層</label><label><input name = "levelType" type="radio" value = "3" onchange = "changeAuthLevelType(this.value)">第三層</label>&nbsp;&nbsp;<span id = "radioLevelTypeIcon"></span>'+
				'</p><table class="role-add-permission"><tbody><tr><td>上層權限</td><td><select id = "selectLevel1" onchange = "changeUpAuthSelect(this.value);"><option value = "0">請選擇</option></select><select id = "selectLevel2"><option value = "0">請選擇</option></select>&nbsp;<span id = "selectUpLevelIcon"></span></td>'+
				'</tr><tr><td>名稱</td><td><input type="text" id = "authName" maxlength = "20" class="width-fix-4 text-left" >&nbsp;<span id = "inputAuthNameIcon"></span></td></tr>'+
				'<tr><td>URL</td><td><input type="text" id = "url" class="width-fix-4 text-left">&nbsp;<span id = "inputUrlIcon"></span></td></tr><tr><td>管理者權限</td><td><select id = "adminAuth"><option value = 0>只有Admin能用</option><option value = 1>都可以使用</option></select></td></tr><tr><td>說明</td>'+
				'<td><textarea id = "authRemark" maxlength="50" onchange = "checkAuthRemarkText(this.value)"></textarea><div id = "textareaAuthRemarkIcon"></div></td></tr></tbody></table><div class="btn-area"><button class="btn-lg btn-orange" onclick="conformAddAuth();">確定</button>\n'+
				'<button class="btn-lg btn-gray" onclick="onClickCloseModal();">取消</button></div></div>';
		
		onClickOpenModal(str);
		getEle("levelType",0).checked = true;
		getEle("selectLevel1").style.display = "none";
		getEle("selectLevel2").style.display = "none";
	}
}

function showPortionAuthGroup(){
	var json = JSON.parse(getEle("authGroupJson").value).data;
	if(json != "" && json != null && json != undefined){
		var str = '<div class="modal-content width-percent-460 margin-fix-5"><span class="close" onclick="onClickCloseModal();">×</span>'+
		'<select id = "levelType" onchange = "changeLevelType(this.value)">'+
		'</select><div class="table-share"><table><tbody><tr><th class="width-fix-5"></th><th>角色名稱</th></tr>';
		for(var i = 0 ; i < Object.keys(json).length ; i++){
			if(json[i].groupId != "" && json[i].groupId != null && json[i].groupId != undefined)
			str+='<tr><td><label class="container"><input type="checkbox" value = '+json[i].groupId+' name = "groupId" id = "groupId'+json[i].groupId+'"><span class="checkmark"></span></label></td><td>'+json[i].groupText+'</td></tr>';
		}
		
		var strEnd = '</tbody></table></div><div class="btn-area"><button class="btn-lg btn-orange" onclick="conformUpdateLevelTypeGroup();">確定</button>\n'+
		'<button class="btn-lg btn-gray" onclick="onClickCloseModal();">取消</button> </div>';
		
		onClickOpenModal(str+strEnd);
		changeLevelType(getEle("levelType").value);
		for(var i = 0;i < ACC_LEVEL_TYPE_ARR.length;i++){
			if(ACC_LEVEL_TYPE_ARR[i] != ACC_LEVEL_ADMIN && isNotMEM(ACC_LEVEL_TYPE_ARR[i])){
				addOptionNoDup("levelType", getAccLevelNickname(ACC_LEVEL_TYPE_ARR[i]), ACC_LEVEL_TYPE_ARR[i]);
			}
		}
		changeLevelType(getEle("levelType").value);
	}
}

function changeLevelType(levelType){
	var jsonObject = JSON.parse(getEle("PortopnAuthGroup").value);
	
	var group =  document.getElementsByName("groupId");
	for(var i=0, n=group.length; i<n; i++){
		group[i].checked = false;
	}
	for(var i = 0 ; i < Object.keys(jsonObject).length ; i++){
		if(levelType == jsonObject[i].accLevelType ){
			getEle("groupId"+jsonObject[i].groupId).checked = true;
		}
	}
}

function  conformUpdateLevelTypeGroup(){
	var str = "";
	var group =  document.getElementsByName("groupId");
	for(var i=0, n=group.length; i<n; i++){
		if(group[i].checked == true){
			str += group[i].value+","
		}
	}
	str= str.substring(0,str.length-1);
	if(str != "" && str != null){
		updayeLevelTypeGroup(str);
	}
	
}

function updateAuthGroup(){
	getEle("authGroupType").value = "update";
	var jsonObject = JSON.parse(getEle("authGroupJson").value).data;
	var groupId = getEle("authGroupId").value;
	var groupText = "";
	for(var i = 0 ; i < Object.keys(jsonObject).length ; i++){
		if(jsonObject[i].groupId == groupId){
			groupText = jsonObject[i].groupText;
			break;
		}
	}
	
	getEle("authGroupName").innerHTML = '角色名稱：'+groupText;
	var json = JSON.parse(getEle("authJsonModify").value);
	for(var i = 0,n1= Object.keys(json).length ; i < n1 ; i++){
		if(!isNull(getEle("auth"+json[i].authId))){
			if(json[i].checked == 1){
				getEle("auth"+json[i].authId).checked = true;
			}
			else{
				getEle("auth"+json[i].authId).checked = false;
			}
		}
		for(var j = 0,n2 = Object.keys(json[i].list).length ; j < n2 ; j++){
			if(!isNull(getEle("auth"+json[i].list[j].authId))){
				if(json[i].list[j].checked == 1){
					getEle("auth"+json[i].list[j].authId).checked = true;
				}
				else{
					getEle("auth"+json[i].list[j].authId).checked = false;
				}
			}
			for(var k = 0,n3= Object.keys(json[i].list[j].list).length; k < n3 ; k++){
				if(!isNull(getEle("auth"+json[i].list[j].list[k].authId))){
					if(json[i].list[j].list[k].checked == 1){
						getEle("auth"+json[i].list[j].list[k].authId).checked = true;
					}
					else{
						getEle("auth"+json[i].list[j].list[k].authId).checked = false;
					}
				}
			}
		}
	}
	
	var auth = document.getElementsByName("auth");
	for(var i=0, n=auth.length; i<n; i++){
		chkGroupCheckbox2(auth[i]);
	}
}

function createAuthGroup(){
	getEle("authGroupType").value = "create";
	getEle("authGroupName").innerHTML = '角色名稱：<input type="text" id = "groupName" onchange = "checkAuthGroupNameAjax(this.value);"'+
										'  placeholder="請輸入角色名稱">&nbsp;<i span id = "groupNameIcon"></i><i span id = "groupNameRemind"></i>';
	var auth = document.getElementsByName("auth");
	for(var i=0, n=auth.length; i<n; i++){
		chkGroupCheckbox(auth[i]);
	}
}

function onClickDisplay(authId){
	if(getEle("ul_"+authId).style.display == ""){
		getEle("ul_"+authId).style.display = "none";
		getEle("upLevelAuthIcon"+authId).className = "icon-arrow-close";
	}
	else{
		getEle("ul_"+authId).style.display = "";
		getEle("upLevelAuthIcon"+authId).className = "icon-arrow-open";
	}
	
}

function authGroupSave(){
	
	
	var auth = document.getElementsByName("auth");
	var str = "authList=";
	var authId = "";
	for(var i = 0 , n =auth.length ; i < n ; i++ ){
		if(auth[i].checked == true){
			authId += auth[i].value+",";
		}
	}
	if(authId.length>1){
		authId = authId.substring(0,authId.length-1);
	}
	str+=authId;
	if(getEle("authGroupType").value == "update"){
		str +="&groupId="+getEle("authGroupId").value;
		updateAuthGroupAjax(str);
	}
	else if(getEle("authGroupType").value == "create"){
		if(getEle("groupName").value != ""){
			str+="&groupName="+getEle("groupName").value;
			createAuthGroupAjax(str);
		}
		else{
			alert("no input 'authGroupType'");
		}
	}
}

function changeAuthLevelType(value){
	var json = JSON.parse(getEle("upAuthJson").value);
	var str = "<option value = '0'>請選擇</option>";
	
	getEle("selectUpLevelIcon").className = "";
	if(value == 1){
		getEle("selectLevel1").style.display = "none";
		getEle("selectLevel2").style.display = "none";
		getEle("selectLevel1").innerHTML = "<option value = '0'>請選擇</option>";
		getEle("selectLevel2").innerHTML = "<option value = '0'>請選擇</option>";
	}
	else if(value == 2){
		getEle("selectLevel1").style.display = "";
		getEle("selectLevel2").style.display = "none";
		for(var i = 0 ; i < Object.keys(json).length ; i++ ){
			str += "<option value = "+json[i].authId+">"+json[i].authName+"</option>";
		}
		getEle("selectLevel1").innerHTML = str;
		getEle("selectLevel2").innerHTML = "<option value = '0'>請選擇</option>";
		
	}
	else if(value == 3){
		getEle("selectLevel1").style.display = "";
		getEle("selectLevel2").style.display = "";
		for(var i = 0 ; i < Object.keys(json).length ; i++ ){
			str += "<option value = "+json[i].authId+">"+json[i].authName+"</option>";
		}
		getEle("selectLevel1").innerHTML = str;
		getEle("selectLevel2").innerHTML = "<option value = '0'>請選擇</option>";
	}
	
}
function changeUpAuthSelect(value){
	 var addAuthLevelType = 0;
	for(var i = 0 ; i < 3 ; i++){
		 if(getEle("levelType",i).checked == true){
			 addAuthLevelType = getEle("levelType",i).value;
		 }
	}
	if(addAuthLevelType == 3){
		var json = JSON.parse(getEle("upAuthJson").value);
		var str = "";
		for(var i = 0 ; i < Object.keys(json).length ; i++ ){
			if(json[i].authId == value){
				for(var j = 0 ; j < Object.keys(json[i].list).length ; j++){
					str += "<option value = "+json[i].list[j].authId+">"+json[i].list[j].authName+"</option>";
				}
			}
			
		}
		getEle("selectLevel2").innerHTML = str;
		if(str == ""){
			getEle("selectUpLevelIcon").className  = "icon-fail";
		}
		else{
			getEle("selectUpLevelIcon").className  = "icon-pass";
		}
	}
	
}

function conformAddAuth(){
	var levelType = 0;
	for(var i = 0 ; i < 3 ; i++){
		 if(getEle("levelType",i).checked == true){
			 levelType = getEle("levelType",i).value;
		 }
	}
	var selectLevel1 = getEle("selectLevel1").value;
	var selectLevel2 = getEle("selectLevel2").value;
	var url = getEle("url").value;
	var authName = getEle("authName").value;
	var authRemark = getEle("authRemark").value;
	var adminAuth = getEle("adminAuth").value;
	
	var boolean = true;
	
	if(levelType != 0){
		getEle("radioLevelTypeIcon").className = "icon-pass";
	}
	else{
		boolean = false;
		getEle("radioLevelTypeIcon").className = "icon-fail";
	}
	if(authRemark != ""){
		getEle("textareaAuthRemarkIcon").className = "icon-pass";
	}
	else{
		boolean = false;
		getEle("textareaAuthRemarkIcon").className = "icon-fail";
	}
	if(authName != ""){
		getEle("inputAuthNameIcon").className = "icon-pass";
	}
	else{
		boolean = false;
		getEle("inputAuthNameIcon").className = "icon-fail";
	}
	getEle("inputUrlIcon").className = "icon-pass";
				
	if(boolean){
		var str = "levelType="+levelType+"&level1="+selectLevel1+"&level2="+selectLevel2+"&addUrl="+url+"&authName="+authName+"&authRemark="+authRemark+"&adminAuth="+adminAuth;
		
		if(levelType == 2){
			if(selectLevel1 > 0 && selectLevel2 == 0){
				getEle("selectUpLevelIcon").className  = "icon-pass";
				addAuthAjax(str);
			}else{
				getEle("selectUpLevelIcon").className  = "icon-fail";
			}
		}
		else if(levelType == 3){
			if(selectLevel1 > 0 && selectLevel2 > 0){
				getEle("selectUpLevelIcon").className  = "icon-pass";
				addAuthAjax(str);
			}else{
				getEle("selectUpLevelIcon").className  = "icon-fail";
			}
		}
		else{
			addAuthAjax(str);	
		}
	}
}

function addAuthAjax(str){
	XHR_AuthGroup = checkXHR(XHR_AuthGroup);
	if (typeof XHR_AuthGroup != "undefined" && XHR_AuthGroup != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&url=AddAuth&"+str;
		XHR_AuthGroup.timeout = 10000;
		XHR_AuthGroup.ontimeout=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onerror=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onreadystatechange = handleAddAuthAjax;
		XHR_AuthGroup.open("POST",
				"./AuthGroup!addAuth.php?date="
						+ getNewTime(), true);
		XHR_AuthGroup.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR_AuthGroup.send(tmpstr);
		enableLoadingPage();
	}
}

function handleAddAuthAjax(){
	if (XHR_AuthGroup.readyState == 4) {
		if (XHR_AuthGroup.status == 200) {
			try{
				if(isJSON(XHR_AuthGroup.responseText)){
					var json = JSON.parse(XHR_AuthGroup.responseText);
					if(checkTokenIdfail(json)){
						if(json.result == "Y"){
							onClickCloseModal();
							onCheckModelPage2("創建成功");
						}else{
							onCheckModelPage2("創建失敗");
						}
					}
					
				}
			}catch(error){
				console_Log("handleAddAuthAjax error:"+error.message);
			}
			finally{
				disableLoadingPage();
				XHR_AuthGroup.abort();
			}
		}
		else{
			disableLoadingPage();
			XHR_AuthGroup.abort();
		}
	}
}

function updateAuthGroupAjax(str){
	XHR_AuthGroup = checkXHR(XHR_AuthGroup);
	if (typeof XHR_AuthGroup != "undefined" && XHR_AuthGroup != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&url=updateAuthGroup&"+str;
		XHR_AuthGroup.timeout = 10000;
		XHR_AuthGroup.ontimeout=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onerror=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onreadystatechange = handleUpdateAuthGroupAjax;
		XHR_AuthGroup.open("POST",
				"./AuthGroup!updateAuthGroup.php?date="
						+ getNewTime(), true);
		XHR_AuthGroup.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR_AuthGroup.send(tmpstr);
		enableLoadingPage();
	}
}

function handleUpdateAuthGroupAjax(){
	if (XHR_AuthGroup.readyState == 4) {
		if (XHR_AuthGroup.status == 200) {
			try{
				if(isJSON(XHR_AuthGroup.responseText)){
					var json = JSON.parse(XHR_AuthGroup.responseText);
					if(checkTokenIdfail(json)){
						if(json.result == "Y"){
							onClickCloseModal();
							//onClickSecondMenu(64, 1,'auth');
							onCheckModelPage2("修改成功");
						}
						else{
							onCheckCloseModelPage();
							onCheckModelPage2("修改失敗");
						}
					}
				}
			}catch(error){
				onCheckCloseModelPage();
				console_Log("handleUpdateAuthGroupAjax error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR_AuthGroup.abort();
				onCheckCloseModelPage();
				authGroup();
			}
		}
		else{
			disableLoadingPage();XHR_AuthGroup.abort();
		}
	}
}

function createAuthGroupAjax(str){
	XHR_AuthGroup = checkXHR(XHR_AuthGroup);
	if (typeof XHR_AuthGroup != "undefined" && XHR_AuthGroup != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&url=createAuthGroup&"+str;
		XHR_AuthGroup.timeout = 10000;
		XHR_AuthGroup.ontimeout=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onerror=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onreadystatechange = handleCreateAuthGroupAjax;
		XHR_AuthGroup.open("POST",
				"./AuthGroup!createAuthGroup.php?date="
						+ getNewTime(), true);
		XHR_AuthGroup.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR_AuthGroup.send(tmpstr);
		enableLoadingPage();
	}
}
function handleCreateAuthGroupAjax(){
	if (XHR_AuthGroup.readyState == 4) {
		if (XHR_AuthGroup.status == 200) {
			try{
				if(isJSON(XHR_AuthGroup.responseText)){
					var json = JSON.parse(XHR_AuthGroup.responseText);
					if(checkTokenIdfail(json)){
						if(json.result == "Y"){
							onClickCloseModal();
							//onClickSecondMenu(64, 1,'auth');
							onCheckModelPage2("創建成功");
						}else{
							onCheckCloseModelPage();
							onCheckModelPage2("創建失敗");
						}
					}
				}
			}catch(error){
				onCheckCloseModelPage();
				console_Log("handleCreateAuthGroupAjax error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR_AuthGroup.abort();
				onCheckCloseModelPage();
				authGroup();
			}
		}
		else{
			disableLoadingPage();
			XHR_AuthGroup.abort();
		}
	}
}

function checkAuthGroupNameAjax(str){
	if(str == "" || str == null){
		getEle("groupNameIcon").className = "icon-fail";
		getEle("groupNameRemind").innerHTML = "請輸入角色名稱";
	}
	else{
		XHR_AuthGroup = checkXHR(XHR_AuthGroup);
		if (typeof XHR_AuthGroup != "undefined" && XHR_AuthGroup != null) {
			var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&url=createAuthGroup&groupName="+str;
			XHR_AuthGroup.timeout = 10000;
			XHR_AuthGroup.ontimeout=function(){disableLoadingPage();XHR_AuthGroup.abort();}
			XHR_AuthGroup.onerror=function(){disableLoadingPage();XHR_AuthGroup.abort();}
			XHR_AuthGroup.onreadystatechange = handleCheckAuthGroupNameAjax;
			XHR_AuthGroup.open("POST",
					"./AuthGroup!checkAuthGroupName.php?date="
							+ getNewTime(), true);
			XHR_AuthGroup.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			XHR_AuthGroup.send(tmpstr);
		}
	}
}

function handleCheckAuthGroupNameAjax(){
	if (XHR_AuthGroup.readyState == 4) {
		if (XHR_AuthGroup.status == 200) {
			try{
				if(isJSON(XHR_AuthGroup.responseText)){
					var json = JSON.parse(XHR_AuthGroup.responseText);
					if(json.result == "Y"){
						getEle("groupNameIcon").className = "icon-pass";
						getEle("groupNameRemind").innerHTML = "";
					}
					else{
						getEle("groupNameIcon").className = "icon-fail";
						getEle("groupNameRemind").innerHTML = "此名稱重複";
					}
				}
			}catch(error){
				getEle("groupNameIcon").className = "icon-fail";
				getEle("groupNameRemind").innerHTML = "此名稱重複";
				console_Log("handleCheckAuthGroupNameAjax error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR_AuthGroup.abort();
			}
		}
		else{
			 disableLoadingPage();XHR_AuthGroup.abort();
		}
	}
}

function getPortopnAuthGroupAjax(){
	XHR_AuthGroup = checkXHR(XHR_AuthGroup);
	if (typeof XHR_AuthGroup != "undefined" && XHR_AuthGroup != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&url=updayeLevelTypeGroup";
		XHR_AuthGroup.timeout = 10000;
		XHR_AuthGroup.ontimeout=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onerror=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onreadystatechange = handleGetPortopnAuthGroupAjax;
		XHR_AuthGroup.open("POST",
				"./AuthGroup!portopnGroupToAccLevel.php?date="
						+ getNewTime(), true);
		XHR_AuthGroup.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR_AuthGroup.send(tmpstr);
		enableLoadingPage();
	}
}

function handleGetPortopnAuthGroupAjax(){
	if (XHR_AuthGroup.readyState == 4) {
		if (XHR_AuthGroup.status == 200) {
			try{
				if(isJSON(XHR_AuthGroup.responseText)){
					var json = JSON.parse(XHR_AuthGroup.responseText);
					if(checkTokenIdfail(json)){
						getEle("PortopnAuthGroup").value = JSON.stringify(JSON.parse(XHR_AuthGroup.responseText).result);
						showPortionAuthGroup();
					}
				}
			}catch(error){
				console_Log("handleGetPortopnAuthGroupAjax error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR_AuthGroup.abort();
			}
		}else{
			disableLoadingPage();
			XHR_AuthGroup.abort();
		}
	}
}

function updayeLevelTypeGroup(str){
	XHR_AuthGroup = checkXHR(XHR_AuthGroup);
	if (typeof XHR_AuthGroup != "undefined" && XHR_AuthGroup != null) {
		levelType = getEle("levelType").value;
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&url=updayeLevelTypeGroup&levelType="+levelType+"&groupIdStr="+str;
		XHR_AuthGroup.timeout = 10000;
		XHR_AuthGroup.ontimeout=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onerror=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onreadystatechange = handleUpdayeLevelTypeGroup;
		XHR_AuthGroup.open("POST",
				"./AuthGroup!updayeLevelTypeGroup.php?date="
						+ getNewTime(), true);
		XHR_AuthGroup.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR_AuthGroup.send(tmpstr);
		enableLoadingPage();
	}
}
function handleUpdayeLevelTypeGroup(){
	if (XHR_AuthGroup.readyState == 4) {
		if (XHR_AuthGroup.status == 200) {
			try{
				if(isJSON(XHR_AuthGroup.responseText)){
					var json = JSON.parse(XHR_AuthGroup.responseText);
					if(checkTokenIdfail(json)){
						if(json.result == "Y"){
							onClickCloseModal();
							onCheckModelPage2("分配角色成功");
							
						}else{
							onCheckModelPage2("分配角色失敗");
						}
					}
				}
			}catch(error){
				console_Log("handleUpdayeLevelTypeGroup error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR_AuthGroup.abort();
			}
		}
		else{
			disableLoadingPage();
			XHR_AuthGroup.abort();
		}
	}
}

function checkAuthRemarkText(str){
	XHR_AuthGroup = checkXHR(XHR_AuthGroup);
	if (typeof XHR_AuthGroup != "undefined" && XHR_AuthGroup != null) {
		levelType = getEle("levelType").value;
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&url=AddAuth&AuthRemark="+str;
		XHR_AuthGroup.timeout = 10000;
		XHR_AuthGroup.ontimeout=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onerror=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onreadystatechange = handleCheckAuthRemarkText;
		XHR_AuthGroup.open("POST",
				"./AuthGroup!checkAuthRemarkText.php?date="
						+ getNewTime(), true);
		XHR_AuthGroup.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR_AuthGroup.send(tmpstr);
	}
}
function handleCheckAuthRemarkText(){
	if (XHR_AuthGroup.readyState == 4) {
		if (XHR_AuthGroup.status == 200) {
			try{
				if(isJSON(XHR_AuthGroup.responseText)){
					var json = JSON.parse(XHR_AuthGroup.responseText);
					if(checkTokenIdfail(json)){
						if(json.result == "Y"){
							getEle("textareaAuthRemarkIcon").className = "icon-pass";
							
						}else{
							getEle("textareaAuthRemarkIcon").className = "icon-fail";
						}
					}
				}
			}catch(error){
				getEle("textareaAuthRemarkIcon").className = "icon-fail";
				console_Log("handleCheckAuthRemarkText error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR_AuthGroup.abort();
			}
		}
		else{
			 disableLoadingPage();
			 XHR_AuthGroup.abort();
		}
	}
}

function getUpdateAuthGroupAjax(groupId){
	XHR_AuthGroup = checkXHR(XHR_AuthGroup);
	if (typeof XHR_AuthGroup != "undefined" && XHR_AuthGroup != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&url=updateAuthGroup&groupId="+groupId;
		XHR_AuthGroup.timeout = 10000;
		XHR_AuthGroup.ontimeout=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onerror=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onreadystatechange = handleGetUpdateAuthGroupAjax;
		XHR_AuthGroup.open("POST",
				"./AuthGroup!getUpdateAuthGroup.php?date="
						+ getNewTime(), true);
		XHR_AuthGroup.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR_AuthGroup.send(tmpstr);
		enableLoadingPage();
	}
}
function handleGetUpdateAuthGroupAjax(){
	if (XHR_AuthGroup.readyState == 4) {
		if (XHR_AuthGroup.status == 200) {
			try{
				if(isJSON(XHR_AuthGroup.responseText)){
					var json = JSON.parse(XHR_AuthGroup.responseText);
					if(checkTokenIdfail(json)){
						getEle("authJsonOrgin").value = JSON.stringify(json.result);
						getEle("authJsonModify").value = JSON.stringify(json.result);
						showAuthGroup();
						updateAuthGroup();
					}
				}
			}catch(error){
				console_Log("handleGetUpdateAuthGroupAjax error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR_AuthGroup.abort();
			}
		}
		else{
			disableLoadingPage();
			XHR_AuthGroup.abort();
		}
	}
	
}

function getCreateAuthGroupAjax(){
	XHR_AuthGroup = checkXHR(XHR_AuthGroup);
	if (typeof XHR_AuthGroup != "undefined" && XHR_AuthGroup != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&url=createAuthGroup"
		XHR_AuthGroup.timeout = 10000;
		XHR_AuthGroup.ontimeout=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onerror=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onreadystatechange = handleGetCreateAuthGroupAjax;
		XHR_AuthGroup.open("POST",
				"./AuthGroup!getCreateAuthGroup.php?date="
						+ getNewTime(), true);
		XHR_AuthGroup.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR_AuthGroup.send(tmpstr);
		enableLoadingPage();
	}
}
function handleGetCreateAuthGroupAjax(){
	if (XHR_AuthGroup.readyState == 4) {
		if (XHR_AuthGroup.status == 200) {
			try{
				if(isJSON(XHR_AuthGroup.responseText)){
					var json = JSON.parse(XHR_AuthGroup.responseText);
					if(checkTokenIdfail(json)){
						getEle("authJsonOrgin").value = "";
						getEle("authJsonModify").value = JSON.stringify(json.result);
						showAuthGroup();
						createAuthGroup();
					}
				}
			}catch(error){
				console_Log("handleGetCreateAuthGroupAjax error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR_AuthGroup.abort();
			}
		}
		else{
			disableLoadingPage();XHR_AuthGroup.abort();
		}
	}
}

function getUpLevelAuthAjax(){
	XHR_AuthGroup = checkXHR(XHR_AuthGroup);
	if (typeof XHR_AuthGroup != "undefined" && XHR_AuthGroup != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&url=AddAuth";
		XHR_AuthGroup.timeout = 10000;
		XHR_AuthGroup.ontimeout=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onerror=function(){disableLoadingPage();XHR_AuthGroup.abort();}
		XHR_AuthGroup.onreadystatechange = handlegetUpLevelAuthAjax;
		XHR_AuthGroup.open("POST",
				"./AuthGroup!getUpLevelAuth.php?date="
						+ getNewTime(), true);
		XHR_AuthGroup.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR_AuthGroup.send(tmpstr);
		enableLoadingPage();
	}
}

function handlegetUpLevelAuthAjax(){
	if (XHR_AuthGroup.readyState == 4) {
		if (XHR_AuthGroup.status == 200) {
			try{
				if(isJSON(XHR_AuthGroup.responseText)){
					var json = JSON.parse(XHR_AuthGroup.responseText);
					if(checkTokenIdfail(json)){
						getEle("upAuthJson").value = JSON.stringify(json.result);
						showAddAuth();
					}
				}
			}catch(error){
				console_Log("handlegetUpLevelAuthAjax error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR_AuthGroup.abort();
			}
		}
		else{
			disableLoadingPage();
			XHR_AuthGroup.abort();
		}
	}
}

function chkGroupCheckbox(ele){
	var a = ele;
	var c = ele.parentNode.parentNode.parentNode.parentNode;
	var chked = ele.checked;
	var l1 = null;
	var l2 = null;
	for(var i=0; i<1024; i++){
		if(c==null){
			break;
		}else{
			if(c.tagName==null){
				break;
			}else{
				if(c.tagName=='DIV'){
					break;
				}
				if(c.tagName=='LI'){
					var b = c.getElementsByTagName("INPUT")[0];
					if(ele==b){
						//
					}else{
						if(chked){
							if(b!=null){
								b.checked = chked;
							}
						}
						if(c.getElementsByTagName("UL").length!=0){
							if(l2==null){
								l2 = b;
							}else if(l2!=null && l1==null){
								l1 = b;
							}
						}
					}
				}
			}
		}
		a = c;
		c = c.parentNode;
	}
	if(l1==null && l2==null){
		var allL = ele.parentNode.parentNode.getElementsByTagName("INPUT");
		for(var i=0, n=allL.length; i<n; i++){
			allL[i].checked = chked;
		}
	}else if(l1==null && l2!=null){
		var allL = l2.parentNode.parentNode.getElementsByTagName("UL");
		if(allL!=null && allL.length>0){
			allL = allL[0].getElementsByTagName("INPUT");
		}
		if(ele.parentNode.parentNode.getElementsByTagName("INPUT").length>1){
			allL = ele.parentNode.parentNode.getElementsByTagName("INPUT");
			for(var i=0, n=allL.length; i<n; i++){
				allL[i].checked = chked;
			}
		}
	}
	var lenL1 = 0;
	var couL1 = 0;
	var lenL2 = 0;
	var couL2 = 0;
	if(l2!=null){
		lenL2 = 0;
		couL2 = 0;
		var allL2 = l2.parentNode.parentNode.getElementsByTagName("INPUT");
		for(var i=0, n=allL2.length; i<n; i++){
			if(allL2[i].tagName=="INPUT"){
				if(allL2[i].parentNode.parentNode.getElementsByTagName("INPUT").length==1){
					lenL2++;
					if(allL2[i].checked){
						couL2++;
					}
				}
			}
		}
		if(couL2 == 0){
			l2.checked = false;
		}
	}
	if(l1!=null){
		lenL1 = l1.getElementsByTagName("INPUT").length;
		couL1 = 0;
		var allL1 = l1.parentNode.parentNode.getElementsByTagName("INPUT");
		var allL2 = l1.parentNode.parentNode.parentNode.getElementsByTagName("UL")[0];
		var L2 = [];
		for(var i=0, n=allL2.length; i<n; i++){
			if(allL2[i].getElementsByTagName("INPUT")[0]!=null){
				L2[L2.length] = allL2[i].getElementsByTagName("INPUT")[0];
			}
		}
		for(var i=0, n=allL1.length; i<n; i++){
			if(allL1[i].tagName=="INPUT"){
				if(allL1[i].parentNode.parentNode.getElementsByTagName("INPUT").length==1){
					lenL1++;
					if(allL1[i].checked){
						couL1++;
					}
				}
			}
		}
		if(couL1 == 0){
			l1.checked = false;
		}
	}
	if(l1!=null && l2!=null){
		l1.parentNode.getElementsByTagName("SPAN")[1].innerHTML="("+couL1+"/"+lenL1+")";
		ele.parentNode.parentNode.parentNode.parentNode.getElementsByTagName("SPAN")[1].innerHTML="("+couL2+"/"+lenL2+")";
	}else if(l1==null && l2!=null){
		l2.parentNode.parentNode.getElementsByTagName("SPAN")[1].innerHTML="("+couL2+"/"+lenL2+")";
		if(ele.parentNode.parentNode.getElementsByTagName("INPUT").length>1){
			var allL = ele.parentNode.parentNode.getElementsByTagName("INPUT");
			for(var i=0, n=allL.length; i<n; i++){
				if(allL[i].parentNode.parentNode.getElementsByTagName("INPUT").length==1){
					lenL1++;
					if(allL[i].checked){
						couL1++;
					}
				}
			}
			ele.parentNode.parentNode.getElementsByTagName("SPAN")[1].innerHTML="("+couL1+"/"+lenL1+")";
		}
	}else if(l1==null && l2==null){
		if(ele.parentNode.parentNode.getElementsByTagName("INPUT").length>1){
			var allL = ele.parentNode.parentNode.getElementsByTagName("INPUT");
			for(var i=0, n=allL.length; i<n; i++){
				if(allL[i].parentNode.parentNode.getElementsByTagName("INPUT").length==1){
					lenL1++;
					if(allL[i].checked){
						couL1++;
					}
				}
			}
			ele.parentNode.getElementsByTagName("SPAN")[1].innerHTML="("+couL1+"/"+lenL1+")";
			lenL2 = 0;
			couL2 = 0;
			var tmpL = null;
			for(var i=0, n=allL.length; i<n; i++){
				if(allL[i].parentNode.parentNode.getElementsByTagName("INPUT").length>1){
					tmpL = allL[i].parentNode.parentNode.getElementsByTagName("SPAN")[1];
					if(lenL2>0){
						tmpL.innerHTML="("+couL2+"/"+lenL2+")";
					}
					lenL2 = 0;
					couL2 = 0;
				}
				if(allL[i].parentNode.parentNode.getElementsByTagName("INPUT").length==1){
					lenL2++;
					if(allL[i].checked){
						couL2++;
					}
				}
			}
			if(lenL2>0){
				tmpL.innerHTML="("+couL2+"/"+lenL2+")";
			}
		}
	}
	
}
function chkGroupCheckbox2(ele){
	var a = ele;
	var c = ele.parentNode.parentNode.parentNode.parentNode;
	var chked = ele.checked;
	var l1 = null;
	var l2 = null;
	for(var i=0; i<1024; i++){
		if(c==null){
			break;
		}else{
			if(c.tagName==null){
				break;
			}else{
				if(c.tagName=='DIV'){
					break;
				}
				if(c.tagName=='LI'){
					var b = c.getElementsByTagName("INPUT")[0];
					if(ele==b){
						//
					}else{
						if(c.getElementsByTagName("UL").length!=0){
							if(l2==null){
								l2 = b;
							}else if(l2!=null && l1==null){
								l1 = b;
							}
						}
					}
				}
			}
		}
		a = c;
		c = c.parentNode;
	}
	var lenL1 = 0;
	var couL1 = 0;
	var lenL2 = 0;
	var couL2 = 0;
	if(l2!=null){
		lenL2 = 0;
		couL2 = 0;
		var allL2 = l2.parentNode.parentNode.getElementsByTagName("INPUT");
		for(var i=0, n=allL2.length; i<n; i++){
			if(allL2[i].tagName=="INPUT"){
				if(allL2[i].parentNode.parentNode.getElementsByTagName("INPUT").length==1){
					lenL2++;
					if(allL2[i].checked){
						couL2++;
					}
				}
			}
		}
		if(couL2 == 0){
			l2.checked = false;
		}
	}
	if(l1!=null){
		lenL1 = l1.getElementsByTagName("INPUT").length;
		couL1 = 0;
		var allL1 = l1.parentNode.parentNode.getElementsByTagName("INPUT");
		var allL2 = l1.parentNode.parentNode.parentNode.getElementsByTagName("UL")[0];
		var L2 = [];
		for(var i=0, n=allL2.length; i<n; i++){
			if(allL2[i].getElementsByTagName("INPUT")[0]!=null){
				L2[L2.length] = allL2[i].getElementsByTagName("INPUT")[0];
			}
		}
		for(var i=0, n=allL1.length; i<n; i++){
			if(allL1[i].tagName=="INPUT"){
				if(allL1[i].parentNode.parentNode.getElementsByTagName("INPUT").length==1){
					lenL1++;
					if(allL1[i].checked){
						couL1++;
					}
				}
			}
		}
		if(couL1 == 0){
			l1.checked = false;
		}
	}
	if(l1!=null && l2!=null){
		l1.parentNode.getElementsByTagName("SPAN")[1].innerHTML="("+couL1+"/"+lenL1+")";
		ele.parentNode.parentNode.parentNode.parentNode.getElementsByTagName("SPAN")[1].innerHTML="("+couL2+"/"+lenL2+")";
	}else if(l1==null && l2!=null){
		l2.parentNode.parentNode.getElementsByTagName("SPAN")[1].innerHTML="("+couL2+"/"+lenL2+")";
		if(ele.parentNode.parentNode.getElementsByTagName("INPUT").length>1){
			var allL = ele.parentNode.parentNode.getElementsByTagName("INPUT");
			for(var i=0, n=allL.length; i<n; i++){
				if(allL[i].parentNode.parentNode.getElementsByTagName("INPUT").length==1){
					lenL1++;
					if(allL[i].checked){
						couL1++;
					}
				}
			}
			ele.parentNode.parentNode.getElementsByTagName("SPAN")[1].innerHTML="("+couL1+"/"+lenL1+")";
		}
	}else if(l1==null && l2==null){
		if(ele.parentNode.parentNode.getElementsByTagName("INPUT").length>1){
			var allL = ele.parentNode.parentNode.getElementsByTagName("INPUT");
			for(var i=0, n=allL.length; i<n; i++){
				if(allL[i].parentNode.parentNode.getElementsByTagName("INPUT").length==1){
					lenL1++;
					if(allL[i].checked){
						couL1++;
					}
				}
			}
			ele.parentNode.getElementsByTagName("SPAN")[1].innerHTML="("+couL1+"/"+lenL1+")";
			lenL2 = 0;
			couL2 = 0;
			var tmpL = null;
			for(var i=0, n=allL.length; i<n; i++){
				if(allL[i].parentNode.parentNode.getElementsByTagName("INPUT").length>1){
					tmpL = allL[i].parentNode.parentNode.getElementsByTagName("SPAN")[1];
					if(lenL2>0){
						tmpL.innerHTML="("+couL2+"/"+lenL2+")";
					}
					lenL2 = 0;
					couL2 = 0;
				}
				if(allL[i].parentNode.parentNode.getElementsByTagName("INPUT").length==1){
					lenL2++;
					if(allL[i].checked){
						couL2++;
					}
				}
			}
			if(lenL2>0){
				tmpL.innerHTML="("+couL2+"/"+lenL2+")";
			}
		}
	}
}