// 2018/02/12 Ason
//顯示或關閉設定區塊
function openSubmenu(){
	checkAccTokenTimeOutAjax();
	document.getElementById("subMenu").classList.add("visible");
}
//關閉選單
function menuHidden() {
    document.getElementById("subMenu").classList.remove("visible");
}

//關閉連線中提示
function exitNotice() {
	disAppear("bgNotice");
	document.getElementById("bgNotice").style.display = "none";
}
//連線中提示
function showNotice() {
	appear("bgNotice");
	document.getElementById("bgNotice").style.display = "block";
}

//關閉比賽結果
function closeRelust(){
	document.getElementById("gameRelust").style.display = "none";
}

//顯示或關閉電腦託管區塊
function closeComDiv(){
	disAppear("btnComputer");
	appear("computerAgency");
//	document.getElementById("btnComputer").style.display = "none";
//	document.getElementById("computerAgency").style.display = "block";
}
function showComBtn(){
	appear("btnComputer");
	disAppear("computerAgency");
//	document.getElementById("btnComputer").style.display = "block";
//	document.getElementById("computerAgency").style.display = "none";
}

//關閉提示視窗 開始
function closeFirstNotice(){
	checkAccTokenTimeOutAjax();
	document.getElementById("noticeFirstDiv").style.display = "none";
}

//function closeBackNotice(){
//	document.getElementById("noticgoBackeDiv").style.display = "none";
//}

//顯示或關閉首頁
function openIndex(){
	menuHidden();
	getPlayerBasicInfo();
	getGameServerList();	
	appear("changeRoleBtn");	
	//getEle("goBackAllBtn").innerHTML = '<a href="#" class="back"></a>';
	getEle("goBackAllBtn").innerHTML = '';
	document.getElementById("indexSection").style.display = "block";
	document.getElementById("characterSection").style.display = "none";
	document.getElementById("gameSection").style.display = "none";
	var tmpId2 = "selectRole_" + getEle("realRoleNumber").value + "_2";
	getEle("playerRoleDiv").src = getEle(tmpId2).src ;
	getEle("showLoadingPage").value = 0;
}

//顯示或關閉角色選取
function openCharacter(){
	checkAccTokenTimeOutAjax();
	menuHidden();
	selectRoleCountry(1);
	disAppear("changeRoleBtn");
	getEle("goBackAllBtn").innerHTML = '<a href="javascript:openIndex()" class="back"  title = "返回"></a>';
	document.getElementById("indexSection").style.display = "none";
	document.getElementById("characterSection").style.display = "block";
	document.getElementById("gameSection").style.display = "none";
}

//顯示或關閉對戰場景
function openGame(){	
	disAppear("changeRoleBtn");
	getEle("goBackAllBtn").innerHTML = '<a href="javascript:openIndex()" class="back"  title = "返回"></a>';	
	document.getElementById("indexSection").style.display = "none";
	document.getElementById("characterSection").style.display = "none";
	document.getElementById("gameSection").style.display = "block";
}