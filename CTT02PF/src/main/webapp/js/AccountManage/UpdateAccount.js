const UPDATE_ACCOUNT_JS = true;
const params =JSON.parse('{'+
		'"comSectionClass" : "class=\'create-company-add\'",'+
		'"levelTypeArr" : {'+
			'"'+ACC_LEVEL_COM.toString()+'": "'+ACC_LEVEL_COM_NICKNAME.toString()+'",'+
			'"'+ACC_LEVEL_SC.toString() +'": "'+ACC_LEVEL_SC_NICKNAME.toString()+'",'+
			'"'+ACC_LEVEL_BC.toString() +'": "'+ACC_LEVEL_BC_NICKNAME.toString()+'",'+
			'"'+ACC_LEVEL_CO.toString() +'": "'+ACC_LEVEL_CO_NICKNAME.toString()+'",'+
			'"'+ACC_LEVEL_SA.toString() +'": "'+ACC_LEVEL_SA_NICKNAME.toString()+'",'+
			'"'+ACC_LEVEL_AG.toString() +'": "'+ACC_LEVEL_AG_NICKNAME.toString()+'",'+
			'"'+ACC_LEVEL_AG1.toString() +'": "'+ACC_LEVEL_AG1_NICKNAME.toString()+'",'+
			'"'+ACC_LEVEL_AG2.toString() +'": "'+ACC_LEVEL_AG2_NICKNAME.toString()+'",'+
			'"'+ACC_LEVEL_AG3.toString() +'": "'+ACC_LEVEL_AG3_NICKNAME.toString()+'",'+
			'"'+ACC_LEVEL_AG4.toString() +'": "'+ACC_LEVEL_AG4_NICKNAME.toString()+'",'+
			'"'+ACC_LEVEL_AG5.toString() +'": "'+ACC_LEVEL_AG5_NICKNAME.toString()+'",'+
			'"'+ACC_LEVEL_AG6.toString() +'": "'+ACC_LEVEL_AG6_NICKNAME.toString()+'",'+
			'"'+ACC_LEVEL_AG7.toString() +'": "'+ACC_LEVEL_AG7_NICKNAME.toString()+'",'+
			'"'+ACC_LEVEL_AG8.toString() +'": "'+ACC_LEVEL_AG8_NICKNAME.toString()+'",'+
			'"'+ACC_LEVEL_AG9.toString() +'": "'+ACC_LEVEL_AG9_NICKNAME.toString()+'",'+
			'"'+ACC_LEVEL_AG10.toString() +'": "'+ACC_LEVEL_AG10_NICKNAME.toString()+'",'+
			'"'+ACC_LEVEL_MEM.toString() +'": "'+ACC_LEVEL_MEM_NICKNAME.toString()+'",'+
			'"-1": ""'+
		'},'+
		'"marginFix" : {'+
		'"'+ACC_LEVEL_COM.toString()+'": "",'+
		'"'+ACC_LEVEL_SC.toString() +'": "margin-fix-4",'+
		'"'+ACC_LEVEL_BC.toString() +'": "margin-fix-5",'+
		'"'+ACC_LEVEL_CO.toString() +'": "margin-fix-6",'+
		'"'+ACC_LEVEL_SA.toString() +'": "margin-fix-6",'+
		'"'+ACC_LEVEL_AG.toString() +'": "margin-fix-6",'+
		'"'+ACC_LEVEL_AG1.toString() +'": "margin-fix-6",'+
		'"'+ACC_LEVEL_AG2.toString() +'": "margin-fix-6",'+
		'"'+ACC_LEVEL_AG3.toString() +'": "margin-fix-6",'+
		'"'+ACC_LEVEL_AG4.toString() +'": "margin-fix-6",'+
		'"'+ACC_LEVEL_AG5.toString() +'": "margin-fix-6",'+
		'"'+ACC_LEVEL_AG6.toString() +'": "margin-fix-6",'+
		'"'+ACC_LEVEL_AG7.toString() +'": "margin-fix-6",'+
		'"'+ACC_LEVEL_AG8.toString() +'": "margin-fix-6",'+
		'"'+ACC_LEVEL_AG9.toString() +'": "margin-fix-6",'+
		'"'+ACC_LEVEL_AG10.toString() +'":"margin-fix-6",'+
		'"'+ACC_LEVEL_MEM.toString() +'": "margin-fix-6",'+
		'"-1": ""'+
		'},'+
		'"colspan" : {'+
		'"'+ACC_LEVEL_COM.toString() +'": "",'+
		'"'+ACC_LEVEL_SC.toString() +'": "colspan=\'3\'",'+
		'"'+ACC_LEVEL_BC.toString() +'": "colspan=\'5\'",'+
		'"'+ACC_LEVEL_CO.toString() +'": "colspan=\'5\'",'+
		'"'+ACC_LEVEL_SA.toString() +'": "colspan=\'5\'",'+
		'"'+ACC_LEVEL_AG.toString() +'": "colspan=\'5\'",'+
		'"'+ACC_LEVEL_AG1.toString() +'": "colspan=\'5\'",'+
		'"'+ACC_LEVEL_AG2.toString() +'": "colspan=\'5\'",'+
		'"'+ACC_LEVEL_AG3.toString() +'": "colspan=\'5\'",'+
		'"'+ACC_LEVEL_AG4.toString() +'": "colspan=\'5\'",'+
		'"'+ACC_LEVEL_AG5.toString() +'": "colspan=\'5\'",'+
		'"'+ACC_LEVEL_AG6.toString() +'": "colspan=\'5\'",'+
		'"'+ACC_LEVEL_AG7.toString() +'": "colspan=\'5\'",'+
		'"'+ACC_LEVEL_AG8.toString() +'": "colspan=\'5\'",'+
		'"'+ACC_LEVEL_AG9.toString() +'": "colspan=\'5\'",'+
		'"'+ACC_LEVEL_AG10.toString() +'":"colspan=\'5\'",'+
		'"'+ACC_LEVEL_MEM.toString() +'": "colspan=\'3\'",'+
		'"-1": ""'+
		'},'+
		'"titleColspanNum" : {'+
		'"'+ACC_LEVEL_COM.toString() +'": "2",'+
		'"'+ACC_LEVEL_SC.toString() +'": "4",'+
		'"'+ACC_LEVEL_BC.toString() +'": "6",'+
		'"'+ACC_LEVEL_CO.toString() +'": "6",'+
		'"'+ACC_LEVEL_SA.toString() +'": "6",'+
		'"'+ACC_LEVEL_AG.toString() +'": "6",'+
		'"'+ACC_LEVEL_AG1.toString() +'": "6",'+
		'"'+ACC_LEVEL_AG2.toString() +'": "6",'+
		'"'+ACC_LEVEL_AG3.toString() +'": "6",'+
		'"'+ACC_LEVEL_AG4.toString() +'": "6",'+
		'"'+ACC_LEVEL_AG5.toString() +'": "6",'+
		'"'+ACC_LEVEL_AG6.toString() +'": "6",'+
		'"'+ACC_LEVEL_AG7.toString() +'": "6",'+
		'"'+ACC_LEVEL_AG8.toString() +'": "6",'+
		'"'+ACC_LEVEL_AG9.toString() +'": "6",'+
		'"'+ACC_LEVEL_AG10.toString() +'": "6",'+
		'"'+ACC_LEVEL_MEM.toString() +'": "4",'+
		'"-1": ""'+
		'},'+
		'"moneyColspanNum" : {'+
		'"'+ACC_LEVEL_COM.toString() +'": "3",'+
		'"'+ACC_LEVEL_SC.toString() +'": "5",'+
		'"'+ACC_LEVEL_BC.toString() +'": "5",'+
		'"'+ACC_LEVEL_CO.toString() +'": "5",'+
		'"'+ACC_LEVEL_SA.toString() +'": "5",'+
		'"'+ACC_LEVEL_AG.toString() +'": "5",'+
		'"'+ACC_LEVEL_AG1.toString() +'": "5",'+
		'"'+ACC_LEVEL_AG2.toString() +'": "5",'+
		'"'+ACC_LEVEL_AG3.toString() +'": "5",'+
		'"'+ACC_LEVEL_AG4.toString() +'": "5",'+
		'"'+ACC_LEVEL_AG5.toString() +'": "5",'+
		'"'+ACC_LEVEL_AG6.toString() +'": "5",'+
		'"'+ACC_LEVEL_AG7.toString() +'": "5",'+
		'"'+ACC_LEVEL_AG8.toString() +'": "5",'+
		'"'+ACC_LEVEL_AG9.toString() +'": "5",'+
		'"'+ACC_LEVEL_AG10.toString() +'": "5",'+
		'"'+ACC_LEVEL_MEM.toString() +'": "3",'+
		'"-1": ""'+
		'},'+
		'"inputAccNameMessage":"帳號為6~20個字元長，請以<i class=\'note-1\'>[英文或數字(0~9,a~z,A~Z)]</i>的組合作為您的帳號",'+
		'"inputAccPwdMessage":"密碼為6~20個字元長，請以<i class=\'note-1\'>[英文、數字或符號]</i>的組合作為您的密碼"'+
		'}');



function showAddAccount() {
	getEle("upAddAccountJson").value = '';
	var selectedSecMenu = getEle("selectedSecMenu").value;
	if(selectedSecMenu != null && selectedSecMenu != undefined){
		if (selectedSecMenu > ACC_LEVEL_ADMIN) {
			XHR = checkXHR(XHR);
			if (typeof XHR != "undefined" && XHR != null) {
				var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&selectedSecMenu=" + selectedSecMenu;
				XHR.timeout = 10000;
				XHR.ontimeout=function(){disableLoadingPage();XHR.abort();}
				XHR.onerror=function(){disableLoadingPage();XHR.abort();}
				XHR.onreadystatechange = handleAddAccount;
				XHR.open("POST","./AccountManage!showAddAccount.php?date="+ getNewTime(), true);
				XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				XHR.send(tmpstr);
				enableLoadingPage();
			}
		} 
	}
}

function handleAddAccount() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try{
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if(checkTokenIdfail(json)){
						getEle("upAddAccountJson").value = XHR.responseText;
						showAccountPage("add");
						setAddAccountPage();
					}
				}
			}catch(error){
				console_Log("handleAddAccount error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR.abort();
			}
		}else{
			disableLoadingPage();
			XHR.abort();
		}
	}
}
function showAccountPage(type) {
	
	getEle("depositMoney").value =0;
	getEle("withdrawalMoney").value =0;

	for(var i = 0 ; i < GAME_TYPE_ARR.length ; i++){
		getEle("rationMaxG"+(i+1)).value = 0;
	}
	
	var selectedSecMenu = getEle("selectedSecMenu").value;
	
	var str = [];
	
	str[0] = '<div class="modal-content width-percent-960 '+params.marginFix[selectedSecMenu]+'"> \n';
	str[1] = '';
// str[1] = '<span class="close" onclick="onClickCloseModal();">×</span> \n';
	str[2] = '<section> \n';
	str[3] = '<table><tr><th colspan="'+params.titleColspanNum[selectedSecMenu]+'"> \n';
	
	if(type == "add"){
		str[4] = '創建'+params.levelTypeArr[selectedSecMenu]+'帳號</th></tr><tr id = "trSelectUpAccName"></tr> \n';
		str[5] = '<tr><td class="text-right">帳號</td> <td class="text-left" '+ params.colspan[selectedSecMenu]+' > \n';
	}
	else if(type == "set"){
		str[4] = '基本資料設定</th></tr><tr id = "trSelectUpAccName"></tr><tr><td class="text-right">帳號</td> \n';
		str[5] = '<button onclick="confromShowSettingLog(4);">LOG</button><td class="text-left" '+ params.colspan[selectedSecMenu]+' >\n';
	}
	
	if(type == "set" && isMEM(selectedSecMenu)){
		str[6] = '<span id = "upAccName"></span> \n';
		str[7] = '<input type="text" class="margin-fix-2 text-left"  id = "inputAccName"  onchange = "checkAccName(this.value);" onkeyup = "this.value = checkAccount(this.value);"> \n';
		str[8] = '<span id = "accNameRemindIcon"></span><span id = "accNameRemind"></span><p class="margin-fix-8">'+params.inputAccNameMessage+'</p> \n';
		str[9] = '</td></tr> \n';
		str[10] = '<tr><td class="text-right">密碼</td><td class="text-left" '+params.colspan[selectedSecMenu]+' >  \n';
		str[11] = '<button id = "updatePwdBnt" onclick = "updatePwd();">重置密碼</button></td></tr>  \n';
		str[12] = '<tr><td class="text-right">提款密碼</td><td class="text-left" '+params.colspan[selectedSecMenu]+' >  \n';
		str[13] = '<button id = "updateWithdrawPwdBnt" onclick = "updateWithdraw();">重置提款密碼</button>  \n';
		str[14] = '</td></tr>  \n';
		str[15] = '<tr><td class="text-right">'+params.levelTypeArr[selectedSecMenu]+'名稱</td>  \n';
		str[16] = '<td class="text-left" '+params.colspan[selectedSecMenu]+' >  \n';
	}
	else{
		str[6] = '<span id = "upAccName"></span><input type="text" class="margin-fix-2 text-left"  id = "inputAccName"  onchange = "checkAccName(this.value);" onkeyup = "this.value = checkAccount(this.value);">  \n';
		str[7] = '<span id = "accNameRemindIcon"></span><span id = "accNameRemind"></span><p class="margin-fix-8">'+params.inputAccNameMessage+'</p>  \n';
		str[8] = '</td></tr>  \n';
		str[9] = '<td class="text-right">密碼</td><td class="text-left" '+params.colspan[selectedSecMenu]+' > \n';
		str[10] = '<input type="password" class="margin-fix-2" id = "inputPassword"  onchange = "checkAccPwd(this.value);" onkeyup = "checkAccPwd(this.value);"> \n';
		str[11] = '';
		str[12] = '<span id = "passwordRemindIcon"></span><span id = "passwordRemind"></span><p class="margin-fix-8">'+params.inputAccPwdMessage+'</p></td></tr> \n';
		str[13] = '<tr><td class="text-right"  >確認密碼</td><td class="text-left" '+params.colspan[selectedSecMenu]+' >  \n';
		str[14] = '<input type="password" id = "checkPassword" class="margin-fix-2" onchange = "checkAccPwds();"  onkeyup = "checkAccPwds();" ><span id = "checkPasswordRemindIcon">\n';
		str[15] = '</span><span id = "checkPasswordRemind"></span> </td></tr> \n';
		str[16] = '<tr><td class="text-right"> '+params.levelTypeArr[selectedSecMenu]+'名稱</td><td class="text-left" '+params.colspan[selectedSecMenu]+' >  \n';
	}
	
	str[17] = '<input type="text" id = "inputNickName" class="margin-fix-2 text-left" maxlength = "'+ACC_NICK_LENGTH_MAX+'" onchange = "checkNickName();" onkeyup = "checkNickName();" >  \n';
	str[18] = '<span id = "nickNameRemindIcon"></span><span id = "nickNameRemind"></span></td></tr>  \n';
	str[19] = '<tr id = trSelectAuth></tr><tr id = "auth"><td class="text-right">權限</td><td class="text-left" '+params.colspan[selectedSecMenu]+' >  \n';
	str[20] = '<select name="selectAuth" class="margin-fix-2" onchange="checkAuthList();"></select><span id = "authRemindIcon"></span><span id = "authRemind"></span></td></tr><tr id = "rechargeMethod"></tr> \n';
	
	str[21] = '<tr><th colspan="'+params.titleColspanNum[selectedSecMenu]+'">下注資料設定</th></tr>  \n';
	
	str[22] = '<tr id = "trUpAccBalance"><tr><tr><td>總點數</td><td colspan="'+params.moneyColspanNum[selectedSecMenu]+'" class="text-left">  \n';
	str[23] = '現金點數<input type="text" value="0"  class="margin-fix-3 text-right"  id = "accNowBalance" disabled>  \n';
	str[24] = '<span id = "moneyBntSpan"></span><div id = "sumAccBalanceText"></div></td></tr>  \n';
	
	str[25] = '<tr id = "trFullRatio"><td>占成方式選擇</td><td colspan="'+params.moneyColspanNum[selectedSecMenu]+'" class="text-left">  \n';
	str[26] = '<span id = "fullRatioSpan"></span><select id = "fullRatio"><option value = 0>不占滿</option><option value = 1> 占滿</option></select>  \n';
	str[27] = '</br>占滿（下線未占滿成數，'+params.levelTypeArr[selectedSecMenu]+'層占滿），不占（下線未占成數往上層給）</td></tr>  \n';
	
	str[28] = '<tr><td>盤口設定</td><td colspan="'+params.moneyColspanNum[selectedSecMenu]+'" class="text-left"><span id = "sHandicapA">A<input type="checkbox" id = "handicapA"  name = "handicap" value = 1 style="min-width: 6px;"></span> \n';
	str[29] = '<span id = "sHandicapB">B<input type="checkbox" id = "handicapB" name = "handicap" value = 2 style="min-width: 6px;"></span><span id = "sHandicapC">C<input type="checkbox" id = "handicapC" name = "handicap"  value = 4 style="min-width: 6px;"></span>';
	str[30] = '<span id = "sHandicapD">D<input type="checkbox" id = "handicapD" name = "handicap" value = 8 style="min-width: 6px;"></span><span id = "sHandicapE">E<input type="checkbox" id = "handicapE" name = "handicap"  value = 16 style="min-width: 6px;"></span> </td></tr>';
	
	str[31] = '<tr class="bg-2" id = "trAccSetRatio"> \n';
	str[32] = '<tr class="bg-2" id = "ratioTableName"></tr> \n';
	str[33] = '<tr id = "g1Ratio"></tr> \n';
	str[34] = '<tr id = "g2Ratio"></tr> \n';
	str[35] = '<tr id = "g3Ratio"></tr> \n';
	str[36] = '<tr id = "g4Ratio"></tr> \n';
	str[37] = '<tr id = "g5Ratio"></tr> \n';
	str[38] = '<tr id = "ratioNotice"></tr>\n';

	str[39] = ' </table><div class="btn-area"><button class="btn-lg btn-orange" id = "conformBnt">確定</button> \n';
	str[40] = '<button class="btn-lg btn-gray" onclick="onClickCloseModal();">取消</button></div></section></div> \n';
	
	
	
	
	if(isCOM(selectedSecMenu)){
		var scAddHtml = "";
		scAddHtml = scAddHtml.concat(str[0],str[1],str[2],str[3],str[4],str[5]
						,str[6],str[7],str[8],str[9],str[10],str[11],str[12]
						,str[13],str[14],str[15],str[16],str[17],str[18],str[19]
						,str[20],str[39],str[40]);
		onClickOpenModal(scAddHtml);
	}
	else{
		onClickOpenModal(str.join(""));
	}
	
	
	if(type == "add"){
		getEle("conformBnt").onclick = function(){onCheckModelPage('確定送出','conformAddAccount()');};
	}
	else if(type == "set"){
		getEle("conformBnt").onclick = function(){onCheckModelPage('確定送出','conformSetAccount()');};
	}
}

function setAddAccountPage(){
	var selectedSecMenu = getEle("selectedSecMenu").value;
	getEle("accBalance").value = 0; 
	getEle("upAccBalance").value = 0;
	
	
	for(var i = 0 ; i < GAME_TYPE_ARR.length ; i++){
		getEle("rationMaxG"+(i+1)).value = 0;
	}
	
	if(isCOM(selectedSecMenu)){
		getAuthGroupName();
	}
	else{
		
		getEle("moneyBntSpan").innerHTML = '<button class="margin-fix-1" id = "depositBnt" onclick = "showDepositPage();">存入</button>';
		
		if(isSC(selectedSecMenu)){
			getEle("inputAccName").maxLength = ""+MANAGER_ACC_NAME_LENGTH_MAX;
			getEle("checkPassword").maxLength = ""+ACC_PASSWORD_LENGTH_MAX;
			getEle("inputPassword").maxLength = ""+ACC_PASSWORD_LENGTH_MAX;
			
			getEle("ratioTableName").innerHTML = "<td></td> <td></td> <td>上限</td> <td>總監可占成數</td>";
			
	
			getEle("g1Ratio").innerHTML = "<td>彩票</td> <td><button onclick = 'unite(1);'>以我同步</button></td> <td id = 'maxG1'>0%</td> <td><select id = 'currentRatioG1'><option value = 0>0%</option></select></td>";
			getEle("g2Ratio").innerHTML = "<td>真人視訊</td> <td><button onclick = 'unite(2);'>以我同步</button></td> <td id = 'maxG2'>0%</td> <td><select id = 'currentRatioG2'><option value = 0>0%</option></select></td>";
			getEle("g3Ratio").innerHTML = "<td>運動球類</td> <td><button onclick = 'unite(3);'>以我同步</button></td> <td id = 'maxG3'>0%</td> <td><select id = 'currentRatioG3'><option value = 0>0%</option></select></td>";
			getEle("g4Ratio").innerHTML = "<td>電動</td> <td><button onclick = 'unite(4);'>以我同步</button></td> <td id = 'maxG4'>0%</td> <td><select id = 'currentRatioG4'><option value = 0>0%</option></select></td>";
			getEle("g5Ratio").innerHTML = "<td>遊戲</td> <td><button onclick = 'unite(5);'>以我同步</button></td> <td id = 'maxG5'>0%</td> <td><select id = 'currentRatioG5'><option value = 0>0%</option></select></td>";
	
			
			for(var i = 0 ; i < GAME_TYPE_ARR.length ; i++){
				getEle("rationMaxG"+(i+1)).value = SC_RATIO_MAX;
				
				getEle("maxG" + (i+1)).innerHTML = SC_RATIO_MAX+"%";
				getEle("currentRatioG" + (i+1)).innerHTML = setOptionRatio(getEle("rationMaxG"+(i+1)).value, 0, 0);
			}

			getAuthGroupName();
		}
		else if(isMEM(selectedSecMenu)){
			
			
			displayAllHandicap(false,false);
			
			
			getEle("inputAccName").maxLength = ""+MEMBER_ACC_NAME_LENGTH_MAX;
			getEle("checkPassword").maxLength = ""+ACC_PASSWORD_LENGTH_MAX;
			getEle("inputPassword").maxLength = ""+ACC_PASSWORD_LENGTH_MAX;
			
			getEle("trUpAccBalance").innerHTML = '<td>上層剩餘總點數</td><td colspan="5" class="text-left" id = "upAccBalanceText" >0</td>';
			
			getEle("trFullRatio").style.display = "none";
			getEle("sumAccBalanceText").style.display = "none";
			getEle("fullRatio").style.display = "none";
			getEle("auth").style.display = "none";
			
			
			
			getEle("trSelectUpAccName").innerHTML = '<td class="text-right">選擇</td><td colspan="3" class="text-left">'
					+ '<select id = "upperAccType" onchange = "changeUpAccType(this.value);"></select>'
					+ '<select id ="selectUpAccName" class="margin-fix-2" onchange = "changeAccName(this.value);"></select> ' 
					+'<span id = "upAccNameRemindIcon"></span><span id = "upAccNameRemind"></span></td>';
			
			getEle("ratioTableName").innerHTML = "<td></td><td></td><td>最少占成數</td><td>上限</td>";
			
			getEle("rechargeMethod").innerHTML = '<td class="text-right">充值方式</td><td class="text-left" colspan="3"><select id = "selectRechargeMethod" onchange = "changRechargeMethod(this.value);"></select></td>';

			addOptionNoDup("selectRechargeMethod",DIRECTLY_UNDER_MEM_TEXT,DIRECTLY_UNDER_MEM);
			addOptionNoDup("selectRechargeMethod",GRNERAL_MEM_TEXT,GRNERAL_MEM);
			
			getEle("g1Ratio").innerHTML = "<td>彩票</td> <td><button onclick = 'unite(1);'>以我同步</button></td>  <td><select id = 'minRatioG1'><option value = 0>0%</option></select></td>  <td id = 'maxG1'>0%</td>";
			getEle("g2Ratio").innerHTML = "<td>真人視訊</td> <td><button onclick = 'unite(2);'>以我同步</button></td>  <td><select id = 'minRatioG2'><option value = 0>0%</option></select></td>  <td id = 'maxG2'>0%</td>";
			getEle("g3Ratio").innerHTML = "<td>運動球類</td> <td><button onclick = 'unite(3);'>以我同步</button></td>  <td><select id = 'minRatioG3'><option value = 0>0%</option></select></td>  <td id = 'maxG3'>0%</td>";
			getEle("g4Ratio").innerHTML = "<td>電動</td> <td><button onclick = 'unite(4);'>以我同步</button></td>  <td><select id = 'minRatioG4'><option value = 0>0%</option></select></td> <td id = 'maxG4'>0%</td>";
			getEle("g5Ratio").innerHTML = "<td>遊戲</td> <td><button onclick = 'unite(5);'>以我同步</button></td>  <td><select id = 'minRatioG5'><option value = 0>0%</option></select></td> <td id = 'maxG5'>0%</td>";
			
			if(isJSON(getEle("loginInfo").value)){
				var userAccLevel = 0;
				var jsonObj = JSON.parse(getEle("loginInfo").value);
				if(isNull(jsonObj.basic.acc_level_type) == false){
					userAccLevel = parseInt(jsonObj.basic.acc_level_type);
				}
				
				for(var i = 0; i < MANAGER_LEVEL_TYPE_ARR.length; i++){
					if(MANAGER_LEVEL_TYPE_ARR[i] >= userAccLevel){
						if(MANAGER_LEVEL_TYPE_ARR[i] == ACC_LEVEL_AG){
							addOptionNoDup("upperAccType","一般會員",MANAGER_LEVEL_TYPE_ARR[i]);
						}else{
							addOptionNoDup("upperAccType",params.levelTypeArr[MANAGER_LEVEL_TYPE_ARR[i]]+"直屬會員",MANAGER_LEVEL_TYPE_ARR[i]);
						}
					}
				}
				
				changeUpAccType(getEle("upperAccType").value);
			}	
		}
		else if(isManager(selectedSecMenu)){
			
			displayAllHandicap(false,false);
			
			
			getEle("inputAccName").maxLength = ""+MANAGER_ACC_NAME_LENGTH_MAX;
			getEle("checkPassword").maxLength = ""+ACC_PASSWORD_LENGTH_MAX;
			getEle("inputPassword").maxLength = ""+ACC_PASSWORD_LENGTH_MAX;
			
			getEle("trUpAccBalance").innerHTML = '<td>上層剩餘總點數</td><td colspan="5" class="text-left" id = "upAccBalanceText" >0</td>';
			
			var upLevel = getUpLevel(selectedSecMenu);
			
			getEle("trSelectUpAccName").innerHTML = '<td class="text-right">'+params.levelTypeArr[upLevel]+ '</td>'
					+'<td colspan="5" class="text-left"><select  id = "selectUpAccName" class="margin-fix-2" onchange = "changeAccName(this.value);"></select>'
					+'<span id = "upAccNameRemindIcon"></span><span id = "upAccNameRemind"></span></td>';
			
			getEle("ratioTableName").innerHTML = "<td></td><td></td><td>"
					+ params.levelTypeArr[upLevel] + "最少占</td><td>"
					+ params.levelTypeArr[upLevel] + "最大占</td><td>上限</td><td>"
					+ params.levelTypeArr[selectedSecMenu] + "可占成數</td>";

			getEle("g1Ratio").innerHTML = "<td>彩票</td> <td><button onclick = 'unite(1);'>以我同步</button></td>  <td><select onchange = 'checkRatio(1)' id = 'minRatioG1'><option value = 0>0%</option></select></td> <td><select onchange = 'checkRatio(1)' id = 'maxRatioG1'><option value = 0>0%</option></select></td> <td id = 'maxG1'>0%</td> <td><select onchange = 'checkRatio(1)' id = 'currentRatioG1'><option value = 0>0%</option></select></td>";
			getEle("g2Ratio").innerHTML = "<td>真人視訊</td> <td><button onclick = 'unite(2);'>以我同步</button></td>  <td><select onchange = 'checkRatio(2)' id = 'minRatioG2'><option value = 0>0%</option></select></td> <td><select onchange = 'checkRatio(2)' id = 'maxRatioG2'><option value = 0>0%</option></select></td> <td id = 'maxG2'>0%</td> <td><select onchange = 'checkRatio(2)' id = 'currentRatioG2'><option value = 0>0%</option></select></td>";
			getEle("g3Ratio").innerHTML = "<td>運動球類</td> <td><button onclick = 'unite(3);' >以我同步</button></td>  <td><select onchange = 'checkRatio(3)' id = 'minRatioG3'><option value = 0>0%</option></select></td> <td><select onchange = 'checkRatio(3)' id = 'maxRatioG3'><option value = 0>0%</option></select></td> <td id = 'maxG3'>0%</td> <td><select onchange = 'checkRatio(3)' id = 'currentRatioG3'><option value = 0>0%</option></select></td>";
			getEle("g4Ratio").innerHTML = "<td>電動</td> <td><button onclick = 'unite(4);'>以我同步</button></td>  <td><select onchange = 'checkRatio(4)' id = 'minRatioG4'><option value = 0>0%</option></select></td> <td><select onchange = 'checkRatio(4)' id = 'maxRatioG4'><option value = 0>0%</option></select></td> <td id = 'maxG4'>0%</td> <td><select onchange = 'checkRatio(4)' id = 'currentRatioG4'><option value = 0>0%</option></<select></td>";
			getEle("g5Ratio").innerHTML = "<td>遊戲</td> <td><button onclick = 'unite(5);'>以我同步</button></td>  <td><select onchange = 'checkRatio(5)' id = 'minRatioG5'><option value = 0>0%</option></select></td> <td><select onchange = 'checkRatio(5)' id = 'maxRatioG5'><option value = 0>0%</option></select></td> <td id = 'maxG5'>0%</td> <td><select onchange = 'checkRatio(5)' id = 'currentRatioG5'><option value = 0>0%</option></<select></td>";
			getEle("ratioNotice").innerHTML = '<td>注意!!!</td><td colspan="5" class="text-left"><ol><li>'+params.levelTypeArr[upLevel]+'最少占成 + '+params.levelTypeArr[upLevel]+'可占成數 不可比<span class="text-alert-1">上限</span>多</li><li class="text-alert-1">'+params.levelTypeArr[upLevel]+'最大占成不可以比最少占成少</li></ol></td>';

			
			
			if(isJSON(getEle("loginInfo").value)){
				var basic = JSON.parse(getEle("loginInfo").value).basic;
				var upLevel = getUpLevel(selectedSecMenu);
				if(upLevel == toInt(basic.acc_level_type)){
					if(isNull(basic.acc_name) == false && isNull(basic.acc_id) == false){
						addOptionNoDup("selectUpAccName",basic.acc_name,basic.acc_id);
					}
				}
				else if(isJSON(getEle("upAddAccountJson").value)){
					
					var jsonObj = JSON.parse(getEle("upAddAccountJson").value).managerRatio;
					addOptionNoDup("selectUpAccName","請選擇",0);
					
					for(var key in jsonObj){
						if(toInt(jsonObj[key].accLevelType) == upLevel){
							addOptionNoDup("selectUpAccName",jsonObj[key].accName,key);
						}
					}
				}
			}
			getAuthGroupName();
		}
	}
}

function changeAccName(key = 0) {
	 getEle("accNowBalance").value = 0;
	 getEle("depositMoney").value =0;
	 getEle("upAccBalance").value = 0;
	var selectedSecMenu = toInt(getEle("selectedSecMenu").value);
	
	displayAllHandicap(false,false);
	
	if(key > 0){
		if(isJSON(getEle("upAddAccountJson").value)){
			var jsonObj = JSON.parse(getEle("upAddAccountJson").value).managerRatio;
			
			getEle("upAccBalance").value = jsonObj[key].balance;
			getEle("upAccBalanceText").innerHTML = jsonObj[key].balance;
			
			displayHandicap(true,false,jsonObj[key].handicap);
			
			
			if(isManager(selectedSecMenu)){

				for (var i = 0; i < GAME_TYPE_ARR.length; i++) {
					getEle("rationMaxG"+(i+1)).value = jsonObj[key].currentRatio[i];
					getEle("maxG"+(i+1)).innerHTML = jsonObj[key].currentRatio[i]+"%";

					if (jsonObj[key].fullRatio == FULL_RATIO_ENABLED) {
						getEle("maxRatioG"+(i+1)).classList.add("disable");
						getEle("maxRatioG"+(i+1)).innerHTML = setOptionRatio(jsonObj[key].currentRatio[i],jsonObj[key].currentRatio[i],jsonObj[key].currentRatio[i]);
						getEle("minRatioG"+(i+1)).innerHTML = setOptionRatio(jsonObj[key].currentRatio[i], 0, 0);
					} else if(jsonObj[key].fullRatio == FULL_RATIO_DISABLED){
						getEle("maxRatioG"+(i+1)).classList.remove("disable");
						getEle("maxRatioG"+(i+1)).innerHTML = setOptionRatio(jsonObj[key].currentRatio[i], 0, 0);
						getEle("minRatioG"+(i+1)).innerHTML = setOptionRatio(0, 0, 0);
					}
					getEle("currentRatioG"+(i+1)).innerHTML = setOptionRatio(jsonObj[key].currentRatio[i], 0, 0);
				}

			}
			else if(isMEM(selectedSecMenu)){
				for (var i = 0; i < GAME_TYPE_ARR.length; i++) {
					getEle("rationMaxG"+(i+1)).value = jsonObj[key].currentRatio[i];
					getEle("maxG"+(i+1)).innerHTML = jsonObj[key].currentRatio[i]+"%";
					
					if (jsonObj[key].fullRatio == FULL_RATIO_ENABLED) {
						getEle("minRatioG"+(i+1)).classList.add("disable");
						getEle("minRatioG"+(i+1)).innerHTML = setOptionRatio(jsonObj[key].currentRatio[i], jsonObj[key].currentRatio[i], jsonObj[key].currentRatio[i]);
					} else if(jsonObj[key].fullRatio == FULL_RATIO_DISABLED){
						getEle("minRatioG"+(i+1)).classList.remove("disable");
						getEle("minRatioG"+(i+1)).innerHTML = setOptionRatio(jsonObj[key].currentRatio[i], 0, 0);
					}
				}
			}
		}
		
	}
	else if(selectedSecMenu > ACC_LEVEL_SC && selectedSecMenu < ACC_LEVEL_MEM){
		clearIcon("accNameRemindIcon");
		getEle("accNameRemind").innerHTML = "";
		getEle("upAccBalance").value = 0;
		getEle("upAccBalanceText").innerHTML = 0;
		
		for (var i = 0; i < GAME_TYPE_ARR.length; i++) {
			if(isNotMEM(selectedSecMenu)){
				getEle("maxRatioG" + (i+1)).disabled = false;
				getEle("maxRatioG" + (i+1)).innerHTML = setOptionRatio(0, 0, 0);
				getEle("currentRatioG" + (i+1)).innerHTML = setOptionRatio(0, 0, 0);
			}			
			getEle("rationMaxG"+(i+1)).value = 0;
			getEle("maxG" + (i+1)).innerHTML = "0%";
			getEle("minRatioG" + (i+1)).innerHTML = setOptionRatio(0, 0, 0);
		}
	}
}

function setOptionRatio(max, min, num) {
	max = parseInt(max);
	min = parseInt(min);
	num = parseInt(num);
	var selectValueHtml = "";
	for (var i = min; i <= max; i = i + 5) {
		if (i == num) {
			selectValueHtml += "<option value =" + i + " selected >" + i
					+ "%</option>";
		} else {
			selectValueHtml += "<option value =" + i + ">" + i + "%</option>";
		}

	}
	return selectValueHtml;
}

function checkRatio(gameType) {
	if (getEle("selectedSecMenu").value < ACC_LEVEL_MEM
			&& getEle("selectedSecMenu").value > ACC_LEVEL_SC) {

		var currentRatio = parseInt(getEle("currentRatioG" + gameType).value);
		var maxRatio = parseInt(getEle("maxRatioG" + gameType).value);
		var minRatio = parseInt(getEle("minRatioG" + gameType).value);

		var max = parseInt(getEle("rationMaxG" + gameType).value);

		var minRatiOoption = setOptionRatio(
				maxRatio < (max - currentRatio) ? maxRatio
						: (max - currentRatio), 0, minRatio);
		var maxRatiOoption = setOptionRatio(max, minRatio,
				maxRatio >= minRatio ? maxRatio : minRatio);
		var currentRatiOoption = setOptionRatio((max - minRatio), 0,
				currentRatio);

		getEle("minRatioG" + gameType).innerHTML = minRatiOoption;
		getEle("maxRatioG" + gameType).innerHTML = maxRatiOoption;
		getEle("currentRatioG" + gameType).innerHTML = currentRatiOoption;

	}
}
function changeUpAccType(value) {
	changeAccName(0);
	var typeName = getAccLevelNickname(value);
	getEle("ratioTableName").innerHTML = "<td></td><td></td><td>"+typeName+"最少占成數</td><td>上限</td>";
	if (isJSON(getEle("upAddAccountJson").value)) {
		var jsonObj = JSON.parse(getEle("upAddAccountJson").value).managerRatio;
		removeAllOption('selectUpAccName');
		addOptionNoDup("selectUpAccName","請選擇",0);
		for(var key in Object.keys(jsonObj)){
			if(jsonObj[Object.keys(jsonObj)[key]].accLevelType == value){
				addOptionNoDup("selectUpAccName",jsonObj[Object.keys(jsonObj)[key]].accName,Object.keys(jsonObj)[key]);
			}
			
		}
	}
}

function getAuthGroupName() {
	var jsonObj = JSON.parse(getEle("upAddAccountJson").value);
	var tmpStr = "";
	removeAllOption('selectAuth');
	addOptionNoDup('selectAuth', '請選擇', 0);
	for(var i = 0; i < Object.keys(jsonObj.createauth).length; i++){
		tmpStr = jsonObj.createauth[Object.keys(jsonObj.createauth)[i]];
		if(isNull(tmpStr.groupId) == false && isNull(tmpStr.groupText) == false && tmpStr.groupId != "" && tmpStr.groupText != ""){
			addOptionNoDup('selectAuth', tmpStr.groupText, tmpStr.groupId);
		}
	}
}

function showDepositPage() {
	var levelType = getEle("selectedSecMenu").value;
	var accNowBalance = getEle("accNowBalance").value;
	var accName = getEle("inputAccName").value;
	var originalUpAccBalance = 0;
	var typeName = "";
	var selectAccId = 0;
	var upAccBalance = 0;
	var jsonObj;
	if(levelType <= ACC_LEVEL_MEM && levelType >= ACC_LEVEL_SC){
		if(isSC(levelType) == false){
			selectAccId = getEle("selectUpAccName").value;
			upAccBalance = getEle("upAccBalance").value;
		}
		if(isJSON(getEle("upAddAccountJson").value)){
			jsonObj = JSON.parse(getEle("upAddAccountJson").value).managerRatio;
			
			if(selectAccId > 0){
				originalUpAccBalance = upAccBalance;
			}
			if(isMEM(levelType)){
				typeName = getEle("upperAccType").options[getEle("upperAccType").selectedIndex].text;
			}
			else{
				typeName = getAccLevelNickname(levelType);
			}
			if(isSC(levelType)){
				originalUpAccBalance = SC_DEPOSIT_MAX.toString();
			}
			var str = [];
			str[0] = '<div class="modal-content width-percent-460"><h3>存入現金</h3>  \n';
			str[1] = '<table><tr><th>'+typeName+' </th> <td>'+accName+'</td></tr> \n';
			str[2] = '<tr><th>可用現金</th><td>0</td></tr><tr><th>存入現金</th><td>  \n';
			str[3] = '<input type="text" placeholder="0" class="margin-fix-3 text-right" id = "inputBalance">  \n';
			str[4] = '/<span>'+originalUpAccBalance+'</span></td></tr></table><div class="btn-area">  \n';
			str[5] = '<button class="btn-lg btn-orange" onclick="conformDeposit('+levelType+','+originalUpAccBalance+');">確定</button>  \n';
			str[6] = '<button class="btn-lg btn-gray" onclick="onClickCloseModalV2();">取消</button>  \n';
			str[7] = '</div></div>  \n';
			onClickOpenModalV2(str.join(""));
			
			getEle("inputBalance").onkeyup = function(){getEle("inputBalance").value = checkInputDeposit(getEle("inputBalance").value,originalUpAccBalance);};
		}
	}
}

function conformDeposit(levelType,originalUpAccBalance){
	getEle("depositMoney").value = 0;
	getEle("withdrawalMoney").value = 0;
	var depositMoney =0;
	if(levelType <= ACC_LEVEL_MEM && levelType >= ACC_LEVEL_SC){
		if(typeOf(getEle("inputBalance")) != "undefined" && getEle("inputBalance") != undefined && getEle("inputBalance").value != ""){
			getEle("inputBalance").value = checkInputDeposit(getEle("inputBalance").value,originalUpAccBalance);
			depositMoney =  getEle("inputBalance").value;
		}
		if(levelType <= ACC_LEVEL_MEM && levelType > ACC_LEVEL_SC){
			getEle("accNowBalance").value = Math.floor((getEle("accBalance").value*100 + depositMoney*100)/100);
			getEle("upAccBalanceText").innerHTML = Math.floor((originalUpAccBalance*100 + depositMoney*100)/100);
			getEle("depositMoney").value =depositMoney;
		}
		else if(isSC(levelType)){
			getEle("accNowBalance").value = Math.floor((getEle("accBalance").value*100 + depositMoney*100)/100)
			getEle("depositMoney").value =depositMoney;
		}
	}
	onClickCloseModalV2();
}

function conformWithdrawal(levelType,originalUpAccBalance){
	getEle("depositMoney").value =0;
	getEle("withdrawalMoney").value =0;
	var withdrawalMoney =  0;
	if(levelType <= ACC_LEVEL_MEM && levelType >= ACC_LEVEL_SC){
		if(typeOf(getEle("inputBalance")) != "undefined" && getEle("inputBalance") != undefined && getEle("inputBalance").value != ""){
			getEle("inputBalance").value = checkInputWithdrawal(getEle("inputBalance").value,getEle("accBalance").value);
			withdrawalMoney =  getEle("inputBalance").value;
		}
		if(levelType <= ACC_LEVEL_MEM && levelType > ACC_LEVEL_SC){
			getEle("accNowBalance").value = Math.floor((getEle("accBalance").value*100 - withdrawalMoney*100)/100);
			getEle("upAccBalanceText").innerHTML = originalUpAccBalance;
			getEle("withdrawalMoney").value =withdrawalMoney;
		}
		else if(levelType == ACC_LEVEL_SC){
			getEle("accNowBalance").value = Math.floor((getEle("accBalance").value*100 - withdrawalMoney*100)/100);
			getEle("withdrawalMoney").value =withdrawalMoney;
		}
	}
	 onClickCloseModalV2();
}

function checkInputDeposit(num,originalUpAccBalance){
	
	var inputNum = checkInputDecimalVal(num);
	var maxNum = checkInputDecimalVal(originalUpAccBalance);
	
	if(inputNum == ""){
		inputNum = 0;
	}
	if(maxNum == ""){
		maxNum = 0;
	}
	if(parseFloat(inputNum) < parseFloat(maxNum)){
		return inputNum;
		
	}
	else{
		if(parseFloat(maxNum) <= 0){
    		return "0";
    	}
		else{
			return  maxNum;
		}
	}
}


function checkInputWithdrawal(num,originalUpAccBalance){
	
	var inputNum = checkInputDecimalVal(num);
	var maxNum = checkInputDecimalVal(originalUpAccBalance);
	
	if(inputNum == ""){
		inputNum = 0;
	}
	if(maxNum == ""){
		maxNum = 0;
	}
	
	if(parseFloat(inputNum) < parseFloat(maxNum)){
		return inputNum;
	}
	else{
		if(parseFloat(maxNum) <= 0){
    		return "0";
    	}
		else{
			return  maxNum;
		}
	}
}

function checkAuthList(){
	if(getEle("selectAuth").value == 0){
		setIconFail("authRemindIcon");
		getEle("authRemind").innerHTML = " 請選擇權限 ";
	}else {
		setIconPass("authRemindIcon");
		getEle("authRemind").innerHTML = "";
		return true;
	}
	return false;
}

function checkAccPwd(value){
	var str = "";
	if(value.length >= ACC_PASSWORD_LENGTH_MIN && value.length <= ACC_PASSWORD_LENGTH_MAX){
		if(checkPassWordVal(value)){
			if(getEle("checkPassword").value != null){
				checkAccPwds(getEle("checkPassword").value);
			}
			setIconPass("passwordRemindIcon");
			getEle("passwordRemind").innerHTML = "";
			return true;
		}
		else{
			setIconFail("passwordRemindIcon");
			getEle("passwordRemind").innerHTML = " 輸入格式不符";
		}
	}else{
		setIconFail("passwordRemindIcon");
		getEle("passwordRemind").innerHTML = " 輸入長度不符";
	}
	return false;
}

function checkAccPwds(){
	if(getEle("checkPassword").value.length >= ACC_PASSWORD_LENGTH_MIN && getEle("checkPassword").value.length <= ACC_PASSWORD_LENGTH_MAX){
		if(getEle("inputPassword").value.length >= ACC_PASSWORD_LENGTH_MIN && getEle("inputPassword").value.length <= ACC_PASSWORD_LENGTH_MAX){
			if(getEle("checkPassword").value == getEle("inputPassword").value){
				getEle("checkPasswordRemind").innerHTML = "";
				setIconPass("checkPasswordRemindIcon");
				return true;
			}
			else{
				setIconFail("checkPasswordRemindIcon");
				getEle("checkPasswordRemind").innerHTML = " 跟密碼不一致";
				return false;
			}
		}
		else{
			setIconFail("passwordRemindIcon");
			getEle("passwordRemind").innerHTML = " 輸入長度不符";
			return false;
		}
	}
	else{
		setIconFail("checkPasswordRemindIcon");
		getEle("checkPasswordRemind").innerHTML = " 輸入長度不符";
		return false;
	}
	
}

function checkAccName(value) {
	var lev = getEle("selectedSecMenu").value;
	if(isCOM(lev)){
		if(value.length <= COM_ACC_NAME_LENGTH_MAX && value.length >= COM_ACC_NAME_LENGTH_MIN ){
			checkAccNameIsItRedundantAjax(value);
			return true;
		}
		else{
			setIconFail("accNameRemindIcon");
			getEle("accNameRemind").innerHTML = " 輸入長度不符";
		}
	}
	else if(isMEM(lev)){
		if(value.length <= MEMBER_ACC_NAME_LENGTH_MAX && value.length >= MEMBER_ACC_NAME_LENGTH_MIN ){
			var s = value.substr(0,1);
			if(checkEnglishVal(s)){
				checkAccNameIsItRedundantAjax(value);
				return true;
			}
			else{
				setIconFail("accNameRemindIcon");
				getEle("accNameRemind").innerHTML = " 第一個字請輸入英文字母";
			}
		}
		else{
			setIconFail("accNameRemindIcon");
			getEle("accNameRemind").innerHTML = " 輸入長度不符";
		}
	}
	else if(isManager(lev)){
		if(value.length <= MANAGER_ACC_NAME_LENGTH_MAX && value.length >= MANAGER_ACC_NAME_LENGTH_MIN ){
			var s = value.substr(0,1);
			if(checkEnglishVal(s)){
				checkAccNameIsItRedundantAjax(value);
				return true;
			}
			else{
				setIconFail("accNameRemindIcon");
				getEle("accNameRemind").innerHTML = " 第一個字請輸入英文字母";
			}
		}
		else{
			setIconFail("accNameRemindIcon");
			getEle("accNameRemind").innerHTML = " 輸入長度不符";
		}
	}
	return false;
	
}

function checkAccNameLength(value) {
	var lev = getEle("selectedSecMenu").value;
	if(isCOM(lev)){
		if(value.length <= COM_ACC_NAME_LENGTH_MAX && value.length >= COM_ACC_NAME_LENGTH_MIN ){
			checkAccNameIsItRedundantAjax(value);
			return true;
		}
		else{
			setIconFail("accNameRemindIcon");
			getEle("accNameRemind").innerHTML = " 輸入長度不符";
		}
	}
	else if(isMEM(lev)){
		if(value.length <= MEMBER_ACC_NAME_LENGTH_MAX && value.length >= MEMBER_ACC_NAME_LENGTH_MIN ){
			var s = value.substr(0,1);
			if(checkEnglishVal(s)){
				var upAccName = getEle("upAccName").innerHTML;
				
				return true;
			}
			else{
				setIconFail("accNameRemindIcon");
				getEle("accNameRemind").innerHTML = " 第一個字請輸入英文字母";
			}
		}
		else{
			setIconFail("accNameRemindIcon");
			getEle("accNameRemind").innerHTML = " 輸入長度不符";
		}
	}
	else if(isManager(lev)){
		if(value.length <= MANAGER_ACC_NAME_LENGTH_MAX && value.length >= MANAGER_ACC_NAME_LENGTH_MIN ){
			var s = value.substr(0,1);
			if(checkEnglishVal(s)){
				var upAccName = getEle("upAccName").innerHTML;
				return true;
			}
			else{
				setIconFail("accNameRemindIcon");
				getEle("accNameRemind").innerHTML = " 第一個字請輸入英文字母";
			}
		}
		else{
			setIconFail("accNameRemindIcon");
			getEle("accNameRemind").innerHTML = " 輸入長度不符";
		}
	}
	return false;
	
}

function checkNickName(){
	getEle("inputNickName").value = checkInputNameOutVal(getEle("inputNickName").value);
	if(getEle("inputNickName").value.length >= ACC_NICK_LENGTH_MIN && getEle("inputNickName").value.length <= ACC_NICK_LENGTH_MAX){
		setIconPass("nickNameRemindIcon");
		getEle("nickNameRemind").innerHTML = "";
		return true;
	}
	else{
		setIconFail("nickNameRemindIcon");
		getEle("nickNameRemind").innerHTML = " 輸入長度不符";
		return false;
	}
}

function checkAccNameIsItRedundantAjax(str) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&acc_name="+str+"&acc_level_type="+getEle("selectedSecMenu").value;
		XHR.timeout = 10000;
		XHR.ontimeout=function(){disableLoadingPage();XHR.abort();}
		XHR.onerror=function(){disableLoadingPage();XHR.abort();}
		XHR.onreadystatechange = handleCheckAccNameIsItRedundant;
		XHR.open("POST","./AccountManage!checkAccName.php?date="+ getNewTime(), true);
		XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XHR.send(tmpstr);
	}
}
function handleCheckAccNameIsItRedundant(){
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try{
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if(checkTokenIdfail(json)){
						var bool = json.result
						if(bool == 0){
							setIconFail("accNameRemindIcon");
							getEle("accNameRemind").innerHTML = " 此帳號已重複";
							return false;
						}
						else{
							setIconPass("accNameRemindIcon");
							getEle("accNameRemind").innerHTML = "";
							return true;
						}
					}
				}
			}catch(error){
				console_Log("handleCheckAccNameIsItRedundant error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR.abort();
			}
		}else{
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function insertAddAccountAjax(str) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&"+str;
		XHR = checkXHR(XHR);
		XHR.timeout = 10000;
		XHR.ontimeout=function(){disableLoadingPage();XHR.abort();}
		XHR.onerror=function(){disableLoadingPage();XHR.abort();}
		XHR.onreadystatechange = handleInsertAddAccount;
		XHR.open("POST","./AccountManage!add.php?date="+ getNewTime(), true);
		XHR.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();
		onCheckCloseModelPage();
	}
}

function handleInsertAddAccount() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try{
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if(checkTokenIdfail(json)){
						if(json.message == 'duplicate'){
							setIconFail("accNameRemindIcon");
							getEle("accNameRemind").innerHTML = " 此帳號已重複";
						}else if (json.message == "fail"){
							onCheckModelPage2("新增帳號失敗");
						}else if (json.message == "success"){
							onClickCloseModal();
							onCheckModelPage2("新增帳號成功");
						}
					}
				}
			}catch(error){
				onCheckModelPage2("新增帳號失敗");
				console_Log("handleInsertAddAccount error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR.abort();
				if (typeof json.message != "undefined" && json.message != null && json.message == "success"){
// getSearchAccDataAjax(getEle("searchOrderInfo").value);
					renewSearchLastDataAjax();
				}
			}
		}else{
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function conformAddAccount(){
	onCheckCloseModelPage();
	var acclevelType = getEle("selectedSecMenu").value;
	var accName = getEle("inputAccName").value;
	var pwd = getEle("inputPassword").value;
	var nickname = getEle("inputNickName").value;
	var authGroup = getEle("selectAuth").value;
	
	var obj = {};
	obj["accLevelType"] = acclevelType;
	obj["accName"] = accName;
	obj["pwd"] = pwd;
	obj["nickname"] = nickname;
	obj["authGroup"] = authGroup;
	if(acclevelType >= ACC_LEVEL_SC && acclevelType <= ACC_LEVEL_MEM){
		var fullRatio = getEle("fullRatio").value;
		var depositMoney = getEle("depositMoney").value;
		obj["fullRatio"] = fullRatio;
		obj["depositMoney"] = depositMoney;
		
		
		var handicap = document.getElementsByName("handicap");
		var handicapNum = 0;
		for(var j = 0 ; j < handicap.length ; j++){
			if(handicap[j].checked == true){
				handicapNum += toInt(handicap[j].value);
			}
		}
		obj["handicap"] = handicapNum;
		
		if(isSC(acclevelType)){
			for(var i = 0 ; i < GAME_TYPE_ARR.length ; i++){
				obj["currentRatioG"+GAME_TYPE_ARR[i]] = getEle("currentRatioG"+(i+1)).value;
				obj["minRatioG"+GAME_TYPE_ARR[i]] = 0;
				obj["maxRatioG"+GAME_TYPE_ARR[i]] = 100;
			}
		}
		else if(isManager(acclevelType)){
			var upAccId = getEle("selectUpAccName").value;
			obj["upAccId"] = upAccId;
			for(var i = 0 ; i < GAME_TYPE_ARR.length ; i++){
				obj["currentRatioG"+GAME_TYPE_ARR[i]] = getEle("currentRatioG"+(i+1)).value;
				obj["minRatioG"+GAME_TYPE_ARR[i]] = getEle("minRatioG"+(i+1)).value;
				obj["maxRatioG"+GAME_TYPE_ARR[i]] = getEle("maxRatioG"+(i+1)).value;
			}
		}
		else if(isMEM(acclevelType)){
			var upAccId = getEle("selectUpAccName").value;
			var memberType=getEle("selectRechargeMethod").value;
			var rechargeMethod=getEle("selectRechargeMethod").value;
			obj["upAccId"] = upAccId;
			obj["memberType"] = memberType;
			for(var i = 0 ; i < GAME_TYPE_ARR.length ; i++){
				obj["minRatioG"+GAME_TYPE_ARR[i]] = getEle("minRatioG"+(i+1)).value;
			}
		}
	}
	
	if(acclevelType > ACC_LEVEL_SC && acclevelType <= ACC_LEVEL_MEM ){
		if(getEle("selectUpAccName").value == 0){
			setIconFail("upAccNameRemindIcon");
			getEle("upAccNameRemind").innerHTML = " 請選擇其中一個上層";
		}
		else{
			setIconPass("upAccNameRemindIcon");
			getEle("upAccNameRemind").innerHTML = "";
		}
		
	}

	var checkAuth = (checkAuthList() || isMEM(acclevelType));
	var checkAccNameLengths = checkAccNameLength(accName);
	var checkNickNameStatus =   checkNickName();
	var checkAccNameStatus = accName != "" ? true : false;
	var checkAccPwdStatus = checkAccPwd(pwd);
	var checkAccPwdsStatus = checkAccPwds();
	if(checkNickNameStatus && checkAccNameStatus && checkAccPwdStatus && checkAccNameLengths 
			&& checkAccPwdsStatus && checkAuth 
			&& ((acclevelType > ACC_LEVEL_SC && acclevelType <= ACC_LEVEL_MEM && getEle("selectUpAccName").value > 0) 
			|| acclevelType == ACC_LEVEL_SC || acclevelType == ACC_LEVEL_COM) ){
		
		var str = joinVars("&", obj,true);
		insertAddAccountAjax(str)
	}
	
		
}

function unite(num){
	var level = getEle("selectedSecMenu").value;
	if(isMEM(level)){
		var minRatio = parseInt(getEle("minRatioG"+num).value);
		var jsonObj = JSON.parse(getEle("upAddAccountJson").value).managerRatio;
		for(var i = 0 ; i < GAME_TYPE_ARR.length ; i++){
			var max = jsonObj[getEle("selectUpAccName").value].currentRatio[i];
			if(minRatio <= max){
				if(jsonObj[getEle("selectUpAccName").value].fullRatio == FULL_RATIO_DISABLED){
					getEle("minRatioG" + (i+1)).innerHTML = setOptionRatio(max,0,minRatio);
				}
				checkRatio(GAME_TYPE_ARR[i]);
			}
		}
	}
	else if(isSC(level)){
		var currentRatio = parseInt(getEle("currentRatioG"+num).value);
		for(var i = 0 ; i < GAME_TYPE_ARR.length ; i++){
			var maxLength = getEle("maxG" + (i+1)).innerHTML.length;
			var max = parseInt(getEle("maxG" + (i+1)).innerHTML.substr(0, maxLength - 1));
			getEle("currentRatioG" + (i+1)).innerHTML = setOptionRatio(max,0,currentRatio);
			checkRatio(GAME_TYPE_ARR[i]);
		}
	}
	else if(isManager(level)){
		var currentRatio = parseInt(getEle("currentRatioG"+num).value);
		var minRatio = parseInt(getEle("minRatioG"+num).value);
		var maxRatio = parseInt(getEle("maxRatioG"+num).value);
		var jsonObj = JSON.parse(getEle("upAddAccountJson").value).managerRatio;
		for(var i = 0 ; i < GAME_TYPE_ARR.length ; i++){
			var maxLength = getEle("maxG" + (i+1)).innerHTML.length;
			var max = parseInt(getEle("maxG" + (i+1)).innerHTML.substr(0, maxLength - 1));
			if((currentRatio+minRatio) <= max){
				getEle("minRatioG" + (i+1)).innerHTML = setOptionRatio(max,0,minRatio);
				getEle("currentRatioG" +(i+1)).innerHTML = setOptionRatio(max,0,currentRatio);
				if(jsonObj[getEle("selectUpAccName").value].fullRatio == FULL_RATIO_DISABLED){
					if(maxRatio <= max){
						getEle("maxRatioG" + (i+1)).innerHTML = setOptionRatio(max,0,maxRatio);
					}
					else{
						getEle("maxRatioG" + (i+1)).innerHTML = setOptionRatio(max,0,max);
					}
					
				}
				checkRatio(GAME_TYPE_ARR[i]);
				
			}
		}
	}
}

function changRechargeMethod(value){
	if(value == GRNERAL_MEM){
		getEle("depositBnt").classList.add("disable");
		getEle("upAccBalanceText").innerHTML = getEle("upAccBalance").value;
		getEle("accNowBalance").value = 0;
		getEle("depositMoney").value =0;
	}
	else{
		getEle("depositBnt").classList.remove("disable");
		
	}
}

// SettingAccount
function onClickSetting(accLevelType , accId){
	getAccountDataAjax(accLevelType,accId);
}

function getAccountDataAjax(accLevelType,accId){
	enableLoadingPage();
	XHR = checkXHR(XHR);
	getEle("seetingAccountJson").value = '';
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&url=settingAcc&accId="+accId+"&accLevelType="+accLevelType;
		XHR.timeout = 10000;
		XHR.ontimeout=function(){disableLoadingPage();XHR.abort();}
		XHR.onerror=function(){disableLoadingPage();XHR.abort();}
		XHR.onreadystatechange = handlegetAccountDataAjax;
		XHR.open("POST","./AccountManage!getSeetingAccData.php?date="+ getNewTime(), true);
		XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XHR.send(tmpstr);
	}
}

function handlegetAccountDataAjax(){
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try{
				if (isJSON(XHR.responseText)) {
					getEle("seetingAccountJson").value = XHR.responseText;
					showAccountPage("set");
					setSettingAccountPage();
				}
			}catch(error){
				console_Log("handlegetAccountDataAjax error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR.abort();
			}
		}
		else{
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function setSettingAccountPage(){
	var selectedSecMenu = 0;
	var json = "";
	if(isJSON(getEle("seetingAccountJson").value)){
		selectedSecMenu = getEle("selectedSecMenu").value;
		json = JSON.parse(getEle("seetingAccountJson").value);
		getEle("inputAccName").disabled = true;
		getEle("accBalance").value = 0;
		getEle("upAccBalance").value = 0;
		
		if(isNotMEM(selectedSecMenu)){
			for(var i = 0 ; i < Object.keys(json.createauth).length ; i++){
				if(json.createauth[i].groupId > 0 && json.createauth[i].groupText != ""){
					addOptionNoDup("selectAuth",json.createauth[i].groupText,json.createauth[i].groupId);
				}
			}
		}
		
		if(isCOM(selectedSecMenu)){
			addOptionNoDup("selectAuth",json.accData.groupText,json.accData.authGroup);
			
			selectItemByValue(getEle("selectAuth"),json.accData.authGroup);
			getEle("inputAccName").value = json.accData.accName;
			getEle("inputNickName").value = json.accData.nickname;
		}
		else if(isSC(selectedSecMenu)){
			
			displayAllHandicap(true,false);
			setHandicap(json.accData.handicap);
			

			getEle("inputAccName").maxLength = ""+MANAGER_ACC_NAME_LENGTH_MAX;
			getEle("checkPassword").maxLength = ""+ACC_PASSWORD_LENGTH_MAX;
			getEle("inputPassword").maxLength = ""+ACC_PASSWORD_LENGTH_MAX;
			
			getEle("accBalance").value = json.accData.balance;
			getEle("accNowBalance").value = json.accData.balance;
			getEle("inputAccName").value = json.accData.accName;
			getEle("inputNickName").value = json.accData.nickname;
			
			addOptionNoDup("selectAuth",json.accData.groupText,json.accData.authGroup);
			
			selectItemByValue(getEle("selectAuth"),json.accData.authGroup);
			
			for(var i = 0 ; i < GAME_TYPE_ARR.length ; i++){
				getEle("rationMaxG"+(i+1)).value = SC_RATIO_MAX;
			}
			
			if(json.accData.fullRatio == FULL_RATIO_ENABLED){
				getEle("fullRatioSpan").innerHTML = "本周:占滿 下周:";
			}
			else if(json.accData.fullRatio == FULL_RATIO_DISABLED){
				getEle("fullRatioSpan").innerHTML = "本周:不占滿 下周:";
			}
			
			selectItemByValue(getEle("fullRatio"),json.accData.nextFullRatio);
			
			getEle("moneyBntSpan").innerHTML = "<button class='margin-fix-1' id = 'depositBnt' onclick = 'showSeetingDepositPage();'>存入</button>"
											+"<button id = 'withdrawalBnt'  class='margin-fix-1' onclick = 'showSeetingWithdrawalPage();'>取出</button>" 
											+"<span id = 'totalSumMoney'>下線現金可用點數加總："+json.accData.lowerLevelAccBalance+"</span>";
			
			getEle("ratioTableName").innerHTML = "<td></td> <td></td> <td>上限(本周/下周)</td> <td>"+ params.levelTypeArr[selectedSecMenu] + "可占成數(本周/下周)</td>";
			
			getEle("g1Ratio").innerHTML = "<td>彩票</td> <td><button onclick = 'seetingUnite(1);'>以我同步</button></td> <td id = 'maxG1'>0%</td> <td>"+json.accData.g1CurrentMaxRatio+"% / <select id = 'currentRatioG1'><option value = 0>0%</option></select></td>";
			getEle("g2Ratio").innerHTML = "<td>真人視訊</td> <td><button onclick = 'seetingUnite(2);'>以我同步</button></td> <td id = 'maxG2'>0%</td> <td>"+json.accData.g2CurrentMaxRatio+"% / <select id = 'currentRatioG2'><option value = 0>0%</option></select></td>";
			getEle("g3Ratio").innerHTML = "<td>運動球類</td> <td><button onclick = 'seetingUnite(3);'>以我同步</button></td> <td id = 'maxG3'>0%</td> <td>"+json.accData.g3CurrentMaxRatio+"% / <select id = 'currentRatioG3'><option value = 0>0%</option></select></td>";
			getEle("g4Ratio").innerHTML = "<td>電動</td> <td><button onclick = 'seetingUnite(4);'>以我同步</button></td> <td id = 'maxG4'>0%</td> <td>"+json.accData.g4CurrentMaxRatio+"% / <select id = 'currentRatioG4'><option value = 0>0%</option></select></td>";
			getEle("g5Ratio").innerHTML = "<td>遊戲</td> <td><button onclick = 'seetingUnite(5);'>以我同步</button></td> <td id = 'maxG5'>0%</td> <td>"+json.accData.g5CurrentMaxRatio+"% / <select id = 'currentRatioG5'><option value = 0>0%</option></select></td>";
		
			for(var i = 0 ; i < GAME_TYPE_ARR.length ; i++){
				var maxRatio = json.accData["g"+GAME_TYPE_ARR[i]+"CurrentMaxRatioNext"];
				getEle("maxG" + (i+1) ).innerHTML = getEle("rationMaxG"+(i+1)).value+"% / "+getEle("rationMaxG"+(i+1)).value+"%";
				getEle("currentRatioG" + (i+1)).innerHTML = setOptionRatio(getEle("rationMaxG"+(i+1)).value,0,maxRatio);
			}
		}
		else if(selectedSecMenu > ACC_LEVEL_SC && selectedSecMenu <= ACC_LEVEL_MEM){
			var handicapEle = document.getElementsByName("handicap"); 
			for(var i = 0 ; i < handicapEle.length ; i++){
				var hNum = handicapEle[i].value;
				if((hNum&json.accData.upAccData.handicap) == 0){
					
				}
			}
			displayAllHandicap(false,false);
			displayHandicap(true,false,json.accData.upAccData.handicap);
			setHandicap(json.accData.accData.handicap);
			
			for(var i = 0 ; i < GAME_TYPE_ARR.length ; i++){
				getEle("rationMaxG"+(i+1)).value = json.accData.upAccData["upG"+GAME_TYPE_ARR[i]+"CurrentRatioNext"];
			}
			
			getEle("accBalance").value = json.accData.accData.balance;
			getEle("accNowBalance").value = json.accData.accData.balance;
			getEle("upAccBalance").value = json.accData.upAccData.upBalance;
			getEle("inputAccName").value = json.accData.accData.accName;
			getEle("inputNickName").value = json.accData.accData.nickname;
	
			getEle("trUpAccBalance").innerHTML = '<td>上層剩餘總點數</td><td colspan="5" class="text-left" id = "upAccBalanceText" >'+json.accData.upAccData.upBalance+'</td>';
		
			if(isMEM(selectedSecMenu)){
				var upAccTypeName = "";
				getEle("inputAccName").maxLength = ""+MEMBER_ACC_NAME_LENGTH_MAX;
				
				getEle("updatePwdBnt").onclick = function (){ updatePwd(json.accData.accData.accId);};
				getEle("updateWithdrawPwdBnt").onclick = function (){ updateWithdraw(json.accData.accData.accId);};
				
				
				upAccTypeName = getAccLevelNickname(json.accData.accData.upperAccLevelType);
				
				
				getEle("trFullRatio").style.display = "none";
				getEle("sumAccBalanceText").style.display = "none";
				getEle("fullRatio").style.display = "none";
				getEle("auth").style.display = "none";
				
				getEle("trSelectUpAccName").innerHTML = '<td class="text-right">'+upAccTypeName+'</td><td colspan="3" class="text-left" id = "tdUpAccName">'+json.accData.upAccData.upAccName+'</td>';
				getEle("ratioTableName").innerHTML = "<td></td><td></td><td>最少占成數(本周/下周)</td><td>上限(本周/下周)</td>";
				
				var memberType = "";
				if(json.accData.accData.memberType == DIRECTLY_UNDER_MEM){
					getEle("moneyBntSpan").innerHTML = "<button class='margin-fix-1' id = 'depositBnt' onclick = 'showSeetingDepositPage();'>存入</button>" 
												+"<button id = 'withdrawalBnt'  class='margin-fix-1' onclick = 'showSeetingWithdrawalPage();'>取出</button>";
					memberType = "上層充值";
				}else{
					memberType = "會員充值";
				}
				getEle("rechargeMethod").innerHTML = '<td class="text-right">充值方式</td><td class="text-left" colspan="3">'+memberType+'</td>';
		
				getEle("g1Ratio").innerHTML = "<td>彩票</td> <td><button onclick = 'seetingUnite(1);'>以我同步</button></td>  <td>"+json.accData.accData.g1MinRatio+"% / <select id = 'minRatioG1'><option value = 0>0%</option></select></td>  <td id = 'maxG1'>"+json.accData.upAccData.upG1CurrentRatio+"% / "+json.accData.upAccData.upG1CurrentRatioNext+"%</td>";// json.accData.accData.g1CurrentMaxRatio
																																																																																									// 20180509
																																																																																									// 要改
				getEle("g2Ratio").innerHTML = "<td>真人視訊</td> <td><button onclick = 'seetingUnite(2);'>以我同步</button></td>  <td>"+json.accData.accData.g2MinRatio+"% / <select id = 'minRatioG2'><option value = 0>0%</option></select></td>  <td id = 'maxG2'>"+json.accData.upAccData.upG2CurrentRatio+"% / "+json.accData.upAccData.upG2CurrentRatioNext+"%</td>";
				getEle("g3Ratio").innerHTML = "<td>運動球類</td> <td><button onclick = 'seetingUnite(3);'>以我同步</button></td>  <td>"+json.accData.accData.g3MinRatio+"% / <select id = 'minRatioG3'><option value = 0>0%</option></select></td>  <td id = 'maxG3'>"+json.accData.upAccData.upG3CurrentRatio+"% / "+json.accData.upAccData.upG3CurrentRatioNext+"%</td>";
				getEle("g4Ratio").innerHTML = "<td>電動</td> <td><button onclick = 'seetingUnite(4);'>以我同步</button></td>  <td>"+json.accData.accData.g4MinRatio+"% / <select id = 'minRatioG4'><option value = 0>0%</option></select></td> <td id = 'maxG4'>"+json.accData.upAccData.upG4CurrentRatio+"% / "+json.accData.upAccData.upG4CurrentRatioNext+"%</td>";
				getEle("g5Ratio").innerHTML = "<td>遊戲</td> <td><button onclick = 'seetingUnite(5);'>以我同步</button></td>  <td>"+json.accData.accData.g5MinRatio+"% / <select id = 'minRatioG5'><option value = 0>0%</option></select></td> <td id = 'maxG5'>"+json.accData.upAccData.upG5CurrentRatio+"% / "+json.accData.upAccData.upG5CurrentRatioNext+"%</td>";
				
				for(var i = 0 ; i < GAME_TYPE_ARR.length ; i++){
					if(json.accData.upAccData.upNextFullRatio == 1){
						getEle("minRatioG" + (i+1)).classList.add("disable");
					}
					var max = json.accData.upAccData["upG"+GAME_TYPE_ARR[i]+"CurrentRatioNext"];
					var minRatio = json.accData.accData["g"+GAME_TYPE_ARR[i]+"MinRatioNext"];
					
					getEle("minRatioG" + (i+1)).innerHTML = setOptionRatio(max,0,minRatio);
					
				}
			}
			else if(isManager(selectedSecMenu)){
				getEle("inputAccName").maxLength = ""+MANAGER_ACC_NAME_LENGTH_MAX;
				getEle("checkPassword").maxLength = ""+ACC_PASSWORD_LENGTH_MAX;
				getEle("inputPassword").maxLength = ""+ACC_PASSWORD_LENGTH_MAX;
				
				getEle("moneyBntSpan").innerHTML = "<button class='margin-fix-1' id = 'depositBnt' onclick = 'showSeetingDepositPage();'>存入</button>" +
											"<button id = 'withdrawalBnt'  class='margin-fix-1' onclick = 'showSeetingWithdrawalPage();'>取出</button>" +
											"<span id = 'totalSumMoney'>下線現金可用點數加總："+json.accData.accData.lowerLevelAccBalance+"</span>";
				
				addOptionNoDup("selectAuth",json.accData.accData.groupText,json.accData.accData.authGroup);
				
				selectItemByValue(getEle("selectAuth"),json.accData.accData.authGroup);
				
				if(json.accData.accData.fullRatio == FULL_RATIO_ENABLED){
					getEle("fullRatioSpan").innerHTML = "本周:占滿 下周:";
				}
				else if(json.accData.accData.fullRatio == FULL_RATIO_DISABLED){
					getEle("fullRatioSpan").innerHTML = "本周:不占滿 下周:";
				}
				
				selectItemByValue(getEle("fullRatio"),json.accData.accData.nextFullRatio);
				
				var upLevel = getUpLevel(selectedSecMenu);
				getEle("trSelectUpAccName").innerHTML = '<td class="text-right">'+params.levelTypeArr[upLevel]+'</td>'
								+'<td colspan="5" class="text-left" id = "tdUpAccName">'+json.accData.upAccData.upAccName+'</td>';
				
		
				getEle("ratioTableName").innerHTML = "<td></td><td></td><td>"
						+ params.levelTypeArr[upLevel] + "最少占(本周/下周)</td><td>"
						+ params.levelTypeArr[upLevel]
						+ "最大占(本周/下周)</td><td>上限(本周/下周)</td><td>"
						+ params.levelTypeArr[selectedSecMenu] + "可占成數(本周/下周)</td>";
		
				getEle("g1Ratio").innerHTML = "<td>彩票</td> <td><button onclick = 'seetingUnite(1);'>以我同步</button></td>  <td>"+json.accData.accData.g1MinRatio+"% / <select onchange = 'checkRatio(1)' id = 'minRatioG1'><option value = 0>0%</option></select></td> <td>"+json.accData.accData.g1MaxRatio+"% / <select onchange = 'checkRatio(1)' id = 'maxRatioG1'><option value = 0>0%</option></select></td> <td id = 'maxG1'>"+json.accData.upAccData.upG1CurrentRatio+"% / "+json.accData.upAccData.upG1CurrentRatioNext+"%</td> <td>"+json.accData.accData.g1CurrentMaxRatio+"% / <select onchange = 'checkRatio(1)' id = 'currentRatioG1'><option value = 0>0%</option></select></td>";
				getEle("g2Ratio").innerHTML = "<td>真人視訊</td> <td><button onclick = 'seetingUnite(2);'>以我同步</button></td>  <td>"+json.accData.accData.g2MinRatio+"% / <select onchange = 'checkRatio(2)' id = 'minRatioG2'><option value = 0>0%</option></select></td> <td>"+json.accData.accData.g2MaxRatio+"% / <select onchange = 'checkRatio(2)' id = 'maxRatioG2'><option value = 0>0%</option></select></td> <td id = 'maxG2'>"+json.accData.upAccData.upG2CurrentRatio+"% / "+json.accData.upAccData.upG2CurrentRatioNext+"%</td> <td>"+json.accData.accData.g2CurrentMaxRatio+"% / <select onchange = 'checkRatio(2)' id = 'currentRatioG2'><option value = 0>0%</option></select></td>";
				getEle("g3Ratio").innerHTML = "<td>運動球類</td> <td><button onclick = 'seetingUnite(3);' >以我同步</button></td>  <td>"+json.accData.accData.g3MinRatio+"% / <select onchange = 'checkRatio(3)' id = 'minRatioG3'><option value = 0>0%</option></select></td> <td>"+json.accData.accData.g3MaxRatio+"% / <select onchange = 'checkRatio(3)' id = 'maxRatioG3'><option value = 0>0%</option></select></td> <td id = 'maxG3'>"+json.accData.upAccData.upG3CurrentRatio+"% / "+json.accData.upAccData.upG3CurrentRatioNext+"%</td> <td>"+json.accData.accData.g3CurrentMaxRatio+"% / <select onchange = 'checkRatio(3)' id = 'currentRatioG3'><option value = 0>0%</option></select></td>";
				getEle("g4Ratio").innerHTML = "<td>電動</td> <td><button onclick = 'seetingUnite(4);'>以我同步</button></td>  <td>"+json.accData.accData.g4MinRatio+"% / <select onchange = 'checkRatio(4)' id = 'minRatioG4'><option value = 0>0%</option></select></td> <td>"+json.accData.accData.g4MaxRatio+"% / <select onchange = 'checkRatio(4)' id = 'maxRatioG4'><option value = 0>0%</option></select></td> <td id = 'maxG4'>"+json.accData.upAccData.upG4CurrentRatio+"% / "+json.accData.upAccData.upG4CurrentRatioNext+"%</td> <td>"+json.accData.accData.g4CurrentMaxRatio+"% / <select onchange = 'checkRatio(4)' id = 'currentRatioG4'><option value = 0>0%</option></<select></td>";
				getEle("g5Ratio").innerHTML = "<td>遊戲</td> <td><button onclick = 'seetingUnite(5);'>以我同步</button></td>  <td>"+json.accData.accData.g5MinRatio+"% / <select onchange = 'checkRatio(5)' id = 'minRatioG5'><option value = 0>0%</option></select></td> <td>"+json.accData.accData.g5MaxRatio+"% / <select onchange = 'checkRatio(5)' id = 'maxRatioG5'><option value = 0>0%</option></select></td> <td id = 'maxG5'>"+json.accData.upAccData.upG5CurrentRatio+"% / "+json.accData.upAccData.upG5CurrentRatioNext+"%</td> <td>"+json.accData.accData.g5CurrentMaxRatio+"% / <select onchange = 'checkRatio(5)' id = 'currentRatioG5'><option value = 0>0%</option></<select></td>";
				getEle("ratioNotice").innerHTML = '<td>注意!!!</td><td colspan="5" class="text-left"><ol><li>'+params.levelTypeArr[upLevel]+'最少占成 + '+params.levelTypeArr[upLevel]+'可占成數 不可比<span class="text-alert-1">上限</span>多</li><li class="text-alert-1">'+params.levelTypeArr[upLevel]+'最大占成不可以比最少占成少</li></ol></td>';
				
				for(var i = 0 ; i < GAME_TYPE_ARR.length ; i++){
					if(json.accData.upAccData.upNextFullRatio == 1){
						getEle("maxRatioG" + (i+1)).classList.add("disable");
					}
					var max = json.accData.upAccData["upG"+GAME_TYPE_ARR[i]+"CurrentRatioNext"];
					var currentRatio = json.accData.accData["g"+GAME_TYPE_ARR[i]+"CurrentMaxRatioNext"];
					var maxRatio = json.accData.accData["g"+GAME_TYPE_ARR[i]+"MaxRatioNext"];
					var minRatio = json.accData.accData["g"+GAME_TYPE_ARR[i]+"MinRatioNext"];
					
					var minRatiOoption = setOptionRatio(
							maxRatio < (max - currentRatio) ? maxRatio
									: (max - currentRatio), 0, minRatio);
					var maxRatiOoption = setOptionRatio(max, minRatio,
							maxRatio >= minRatio ? maxRatio : minRatio);
					var currentRatiOoption = setOptionRatio((max - minRatio), 0,
							currentRatio);

					getEle("minRatioG" + (i+1)).innerHTML = minRatiOoption;
					getEle("maxRatioG" + (i+1)).innerHTML = maxRatiOoption;
					getEle("currentRatioG" + (i+1)).innerHTML = currentRatiOoption;
					
				}
			}
		}
	}
}

function conformSetAccount(){
	onCheckCloseModelPage();
	var accName = getEle("inputAccName").value;
	var nickname = getEle("inputNickName").value;
	var accId = 0;
	var upAccId = 0;
	var levelType = 0;
	var password = "";
	var checkPassword = "";
	var authGroup = 0;
	var fullRatio =0;
	var depositMoney = 0;
	var withdrawalMoney = 0;
	var obj = {};
	
	if(isJSON(getEle("seetingAccountJson").value)){
		levelType = getEle("selectedSecMenu").value;
		json = JSON.parse(getEle("seetingAccountJson").value);
		if(levelType <= ACC_LEVEL_SC){
			accId = json.accData.accId;
		}
		else{
			accId = json.accData.accData.accId;
		}
		
		obj["accId"] = accId;
		obj["accLevelType"] = levelType;
		obj["nickname"] = nickname;
		
		if(isCOM(levelType)){
			password = getEle("inputPassword").value;
			checkPassword = getEle("checkPassword").value;
			authGroup = getEle("selectAuth").value;
			obj["pwd"] = password;
			obj["authGroup"] = authGroup;
		}
		else if(levelType > ACC_LEVEL_COM && levelType <= ACC_LEVEL_MEM){
			
			var handicap = document.getElementsByName("handicap");
			var handicapNum = 0;
			for(var j = 0 ; j < handicap.length ; j++){
				if(handicap[j].checked == true){
					handicapNum += toInt(handicap[j].value);
				}
			}
			obj["handicap"] = handicapNum;
			
			
			depositMoney = getEle("depositMoney").value;
			withdrawalMoney = getEle("withdrawalMoney").value;
			
			obj["depositMoney"] = depositMoney;
			obj["withdrawalMoney"] = withdrawalMoney;
			
			if(isSC(levelType)){
				password = getEle("inputPassword").value;
				checkPassword = getEle("checkPassword").value;
				authGroup = getEle("selectAuth").value;
				fullRatio = getEle("fullRatio").value;

				obj["upAccId"] = upAccId;
				obj["pwd"] = password;
				obj["authGroup"] = authGroup;
				obj["fullRatio"] = fullRatio;
				
				for(var i = 0 ; i < GAME_TYPE_ARR.length ; i++){
					obj["currentRatioG"+GAME_TYPE_ARR[i]] = getEle("currentRatioG"+(i+1)).value;
					obj["minRatioG"+GAME_TYPE_ARR[i]] = 0;
					obj["maxRatioG"+GAME_TYPE_ARR[i]] = SC_RATIO_MAX;
				}
			}
			else if(isManager(levelType)){
				password = getEle("inputPassword").value;
				checkPassword = getEle("checkPassword").value;
				authGroup = getEle("selectAuth").value;
				fullRatio = getEle("fullRatio").value;
				upAccId = json.accData.upAccData.upAccId
				
				obj["upAccId"] = upAccId;
				obj["pwd"] = password;
				obj["authGroup"] = authGroup;
				obj["fullRatio"] = fullRatio;
				
				
				if(upAccId > 0){
					for(var i = 0 ; i <  GAME_TYPE_ARR.length ; i++){
						obj["currentRatioG"+GAME_TYPE_ARR[i]] = getEle("currentRatioG"+(i+1)).value;
						obj["minRatioG"+GAME_TYPE_ARR[i]] = getEle("minRatioG"+(i+1)).value;
						obj["maxRatioG"+GAME_TYPE_ARR[i]] = getEle("maxRatioG"+(i+1)).value;;
					}
				}
			}
			else if(isMEM(levelType)){
				upAccId = json.accData.upAccData.upAccId
				obj["upAccId"] = upAccId;
				if(upAccId > 0){
					for(var i = 0 ; i <  GAME_TYPE_ARR.length ; i++){
						obj["minRatioG"+GAME_TYPE_ARR[i]] = getEle("minRatioG"+(i+1)).value;
					}
				}
				
			}
			
		}
	}

	var checkAccpwd = true;
	var checkAuth = (checkAuthList() || isMEM(levelType));
	var checknickname =  checkNickName();
	
	if(isNotMEM(levelType)){
		if(getEle("inputPassword").value != "" || getEle("checkPassword").value != "" ){
			checkAccpwd = checkAccPwds();
		}
	}
	
	if(checknickname && checkAccpwd && checkAuth){
		var str = joinVars("&", obj,true);
		updateAcc(str);
	}
	
}

function showSeetingWithdrawalPage(){
	var levelType = getEle("selectedSecMenu").value;
	var accNowBalance = getEle("accNowBalance").value;
	var accName = getEle("inputAccName").value;
	var accBalance = getEle("accBalance").value;
	
	var originalUpAccBalance = 0;
	var typeName = "";
	var selectAccId = 0;
	var upAccBalance = 0;
	var jsonObj;
	if(levelType <= ACC_LEVEL_MEM && levelType >= ACC_LEVEL_SC){
		if(isJSON(getEle("seetingAccountJson").value)){
			jsonObj = JSON.parse(getEle("seetingAccountJson").value);
			
			if(isSC(levelType) == false){
				upAccBalance = getEle("upAccBalance").value;
				originalUpAccBalance = upAccBalance;
			}
			
			if(isMEM(levelType)){
				typeName =  getAccLevelNickname(jsonObj.accData.accData.upperAccLevelType);
			}
			else{
				typeName = getAccLevelNickname(levelType);
			}
			
// if(isSC(levelType)){
// originalUpAccBalance = SC_DEPOSIT_MAX.toString();
// }
			var str = [];
			str[0] = '<div class="modal-content width-percent-460"><h3>取款現金</h3>  \n';
			str[1] = '<table><tr><th>'+typeName+' </th> <td>'+accName+'</td></tr> \n';
			str[2] = '<tr><th>可用現金</th><td>'+accBalance+'</td></tr><tr><th>取款現金</th><td>  \n';
			str[3] = '<input type="text" placeholder="0" class="margin-fix-3 text-right" id = "inputBalance">  \n';
			str[4] = '</td></tr></table><div class="btn-area">  \n';
			str[5] = '<button class="btn-lg btn-orange" onclick="conformWithdrawal('+levelType+','+originalUpAccBalance+');">確定</button>  \n';
			str[6] = '<button class="btn-lg btn-gray" onclick="onClickCloseModalV2();">取消</button>  \n';
			str[7] = '</div></div>  \n';
			onClickOpenModalV2(str.join(""));
			
			getEle("inputBalance").onkeyup = function(){getEle("inputBalance").value = checkInputWithdrawal(this.value,accBalance);};
		}
	}
}

function showSeetingDepositPage(){
	var levelType = getEle("selectedSecMenu").value;
	var accNowBalance = getEle("accNowBalance").value;
	var accBalance = getEle("accBalance").value;
	var accName = getEle("inputAccName").value;
	var originalUpAccBalance = 0;
	var typeName = "";
	var selectAccId = 0;
	var upAccBalance = 0;
	var jsonObj;
	if(levelType <= ACC_LEVEL_MEM && levelType >= ACC_LEVEL_SC){
		
		if(isJSON(getEle("seetingAccountJson").value)){
			jsonObj = JSON.parse(getEle("seetingAccountJson").value);
			
			if(isSC(levelType) == false){
				upAccBalance = getEle("upAccBalance").value;
				originalUpAccBalance = upAccBalance;
			}
			
			if(isMEM(levelType)){
				typeName = getAccLevelNickname(jsonObj.accData.accData.upperAccLevelType);
			}
			else{
				typeName = getAccLevelNickname(levelType);
			}
			if(isSC(levelType)){
				originalUpAccBalance = SC_DEPOSIT_MAX.toString();
			}
			var str = [];
			str[0] = '<div class="modal-content width-percent-460"><h3>存入現金</h3>  \n';
			str[1] = '<table><tr><th>'+typeName+' </th> <td>'+accName+'</td></tr> \n';
			str[2] = '<tr><th>可用現金</th><td>'+accBalance+'</td></tr><tr><th>存入現金</th><td>  \n';
			str[3] = '<input type="text" placeholder="0" class="margin-fix-3 text-right" id = "inputBalance">  \n';
			str[4] = '/<span>'+originalUpAccBalance+'</span></td></tr></table><div class="btn-area">  \n';
			str[5] = '<button class="btn-lg btn-orange" onclick="conformDeposit('+levelType+','+originalUpAccBalance+');">確定</button>  \n';
			str[6] = '<button class="btn-lg btn-gray" onclick="onClickCloseModalV2();">取消</button>  \n';
			str[7] = '</div></div>  \n';
			onClickOpenModalV2(str.join(""));
			
			getEle("inputBalance").onkeyup = function(){getEle("inputBalance").value = checkInputDeposit(this.value,originalUpAccBalance);};
		}
	}
}

function seetingUnite(num){
	if(isJSON(getEle("seetingAccountJson").value)){
		var jsonObj = JSON.parse(getEle("seetingAccountJson").value);
		var selectedSecMenu = getEle("selectedSecMenu").value;
		if(isMEM(selectedSecMenu)){
			var memMinRatio = toInt(getEle("minRatioG"+num).value);
			for(var i=0 ; i < GAME_TYPE_ARR.length ; i++){
				var memMaxRatio = jsonObj.accData.upAccData["upG"+GAME_TYPE_ARR[i]+"CurrentRatioNext"];
				if(memMinRatio <= memMaxRatio){
					if(jsonObj.accData.upAccData.upNextFullRatio == FULL_RATIO_DISABLED){
						getEle("minRatioG"+(i+1)).innerHTML = setOptionRatio(memMaxRatio,0,memMinRatio);
					}
					checkRatio(GAME_TYPE_ARR[i]);
				}
			}
		}
		else if(isSC(selectedSecMenu)){
			var currentRatio = toInt(getEle("currentRatioG"+num).value);
			for(var i=0 ; i < GAME_TYPE_ARR.length ; i++){
				getEle("currentRatioG" + (i+1)).innerHTML = setOptionRatio(SC_RATIO_MAX,0,currentRatio);
				checkRatio(GAME_TYPE_ARR[i]);
			}
		}
		else if(isManager(selectedSecMenu)){
			var magCurrentRatio = toInt(getEle("currentRatioG"+num).value);
			var magMinRatio = toInt(getEle("minRatioG"+num).value);
			var magMaxRatio = toInt(getEle("maxRatioG"+num).value);
			
			for(var i=0 ; i < GAME_TYPE_ARR.length ; i++){
				var max = jsonObj.accData.upAccData["upG"+i+"CurrentRatio"];
				if((magCurrentRatio+magMinRatio) <= max){
					getEle("minRatioG"+(i+1)).innerHTML = setOptionRatio(max,0,magMinRatio);
					getEle("currentRatioG"+(i+1)).innerHTML = setOptionRatio(max,0,magCurrentRatio);
					if(jsonObj.accData.upAccData.upNextFullRatio == FULL_RATIO_DISABLED){
						if(magMaxRatio <= max){
							getEle("maxRatioG"+(i+1)).innerHTML = setOptionRatio(max,0,magMaxRatio);
						}
						else{
							getEle("maxRatioG"+(i+1)).innerHTML = setOptionRatio(max,0,max);
						}
					}
					checkRatio(GAME_TYPE_ARR[i]);
				}
			}
			
		}
		
	}
}

function updatePwd(accId){
	enableLoadingPage();
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&accId="+accId;
		XHR.timeout = 10000;
		XHR.ontimeout=function(){disableLoadingPage();XHR.abort();}
		XHR.onerror=function(){disableLoadingPage();XHR.abort();}
		XHR.onreadystatechange = handleUpdatePwd;
		XHR.open("POST","./AccountManage!updateMemberPwd.php?date="+ getNewTime(), true);
		XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();
	}
} 
function handleUpdatePwd(){
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try{
				if (isJSON(XHR.responseText)) {
					var tmp = JSON.parse(XHR.responseText);
					if (tmp.message == "fail"){
						onCheckModelPage2("重置密碼成功失敗");
					}else if (tmp.message == "success"){
						checkPwdModePage("新密碼:</br> "+tmp.pwd ,tmp.pwd);
					}
				}
			}catch(error){
				console_Log("handleUpdatePwd error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR.abort();
			}
		}
		else{
			disableLoadingPage();
			XHR.abort();
		}
	}
}
function updateWithdraw(accId){
	enableLoadingPage();
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&accId="+accId;
		XHR.timeout = 10000;
		XHR.ontimeout=function(){disableLoadingPage();XHR.abort();}
		XHR.onerror=function(){disableLoadingPage();XHR.abort();}
		XHR.onreadystatechange = handleUpdateWithdraw;
		XHR.open("POST","./AccountManage!updateMemberWithdrawPwd.php?date="+ getNewTime(), true);
		XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();
	}
}
function handleUpdateWithdraw(){
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try{
				if (isJSON(XHR.responseText)) {
					var tmp = JSON.parse(XHR.responseText);
					if (tmp.message == "fail"){
						onCheckModelPage2("重置提款密碼成功失敗");
					}else if (tmp.message == "success"){
						onCheckModelPage2("重置提款密碼成功");
					}
				}
			}catch(error){
				console_Log("handleUpdateWithdraw error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR.abort();
			}
		}
		else{
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function updateAcc(str) {
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = str+"&tokenId="+encodeURIComponent(getEle("tokenId").value);
		XHR.timeout = 10000;
		XHR.ontimeout=function(){disableLoadingPage();XHR.abort();}
		XHR.onerror=function(){disableLoadingPage();XHR.abort();}
		XHR.onreadystatechange = handleUpdateAcc;
		XHR.open("POST","./AccountManage!seetingAccData.php?date="+ getNewTime(), true);
		XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();
		onCheckCloseModelPage();
	}
}

function handleUpdateAcc() {
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try{
				if (isJSON(XHR.responseText)) {
					var tmp = JSON.parse(XHR.responseText);
					if(checkTokenIdfail(tmp)){
						if (tmp.message == "fail"){
							if(tmp.overAccList != null && Object.keys(tmp.overAccList).length > 0){
								var str = "";
								var gmaeName = ["彩票","真人視訊","運動球類","電動","遊戲"];
								var title = '操作失敗，以下帳號已佔滿!';
								for(var i = 0 ; i < Object.keys(tmp.overAccList).length ; i++){
									if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].sc != "" || tmp.overAccList[Object.keys(tmp.overAccList)[i].bc] != "" || tmp.overAccList[Object.keys(tmp.overAccList)[i].co] != "" || tmp.overAccList[Object.keys(tmp.overAccList)[i]].sa != ""
										|| tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag != "" || tmp.overAccList[Object.keys(tmp.overAccList)[i]].mem != ""){
										str += gmaeName[(parseInt(Object.keys(tmp.overAccList)[i])-1)]+"：</br>";
										if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].sc != "" && tmp.overAccList[Object.keys(tmp.overAccList)[i]].sc != null && tmp.overAccList[Object.keys(tmp.overAccList)[i]].sc != undefined){
											str += "總監:"+tmp.overAccList[Object.keys(tmp.overAccList)[i]].sc+"</br>";
										}
										if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].bc != "" && tmp.overAccList[Object.keys(tmp.overAccList)[i]].bc != null && tmp.overAccList[Object.keys(tmp.overAccList)[i]].bc != undefined){
											str += "大股東:"+tmp.overAccList[Object.keys(tmp.overAccList)[i]].bc+"</br>";
										}
										if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].co != "" && tmp.overAccList[Object.keys(tmp.overAccList)[i]].co != null && tmp.overAccList[Object.keys(tmp.overAccList)[i]].co != undefined){
											str += "股東:"+tmp.overAccList[Object.keys(tmp.overAccList)[i]].co+"</br>";
										}
										if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].sa != "" && tmp.overAccList[Object.keys(tmp.overAccList)[i]].sa != null && tmp.overAccList[Object.keys(tmp.overAccList)[i]].sa != undefined){
											str += "總代理:"+tmp.overAccList[Object.keys(tmp.overAccList)[i]].sa+"</br>";
										}
										if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag != "" && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag != null && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag != undefined){
											str += "代理:"+tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag+"</br>";
										}
										if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag1 != "" && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag1 != null && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag1 != undefined){
											str += "代理1:"+tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag1+"</br>";
										}if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag2 != "" && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag2 != null && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag2 != undefined){
											str += "代理2:"+tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag2+"</br>";
										}if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag3 != "" && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag3 != null && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag3 != undefined){
											str += "代理3:"+tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag3+"</br>";
										}if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag4 != "" && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag4 != null && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag4 != undefined){
											str += "代理4:"+tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag4+"</br>";
										}if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag5 != "" && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag5 != null && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag5 != undefined){
											str += "代理5:"+tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag5+"</br>";
										}if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag6 != "" && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag6 != null && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag6 != undefined){
											str += "代理6:"+tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag6+"</br>";
										}if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag7 != "" && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag7 != null && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag7 != undefined){
											str += "代理7:"+tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag7+"</br>";
										}if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag8 != "" && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag8 != null && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag8 != undefined){
											str += "代理8:"+tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag8+"</br>";
										}if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag9 != "" && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag9 != null && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag9 != undefined){
											str += "代理9:"+tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag9+"</br>";
										}if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag10 != "" && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag10 != null && tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag10 != undefined){
											str += "代理10:"+tmp.overAccList[Object.keys(tmp.overAccList)[i]].ag10+"</br>";
										}
										
										if(tmp.overAccList[Object.keys(tmp.overAccList)[i]].mem != "" && tmp.overAccList[Object.keys(tmp.overAccList)[i]].mem != null && tmp.overAccList[Object.keys(tmp.overAccList)[i]].mem != undefined){
											str += "會員:"+tmp.overAccList[Object.keys(tmp.overAccList)[i]].mem+"</br>";
										}
									}
								}
								if(str != ""){
									showOverRatioMessageModal(title,str);
								}
							}
							else{
								onCheckModelPage2("設定帳號失敗");
							}
						}else if (tmp.message == "success"){
							onClickCloseModal();
							onCheckModelPage2("設定帳號成功");
						}
					}
				}
			}catch(error){
				onCheckModelPage2("設定帳號失敗");
				console_Log("handleUpdateAcc error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR.abort();
				if (typeof tmp.message != "undefined" && tmp.message != null && tmp.message == "success"){
// getSearchAccDataAjax(getEle("searchOrderInfo").value);
					renewSearchLastDataAjax();
				}
			}
		}else{
			disableLoadingPage();
			XHR.abort();
		}
	}
}


function getAccDetails(id){
	getEle("detailsAccId").value = id;
	getAccDetailsAjax(id);
}

function getAccDetailsAjax(id){
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+ "&accId=" + id ;
		XHR.timeout = 10000;
		XHR.ontimeout = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onerror = function() {
			disableLoadingPage();XHR.abort();
		}
		XHR.onreadystatechange = handleGetAccDetailsAjax;
		XHR.open("POST", "./AccountManage!getAccDetails.php?date="+ getNewTime(), true);
		XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();
	}
	
}
function handleGetAccDetailsAjax(){
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try {
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if(checkTokenIdfail(json)){
						getEle("accDetails").value = XHR.responseText;
						// showAccDetails();
						showAccDetails2();
					}
					else{
						getEle("accDetails").value = "";
					}
				}
			} catch (error) {
				console_Log("handleGetAccDetailsAjax error:"+error.message);
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

function showAccDetails(){
	var json = JSON.parse(getEle("accDetails").value);
	if(Object.keys(json).length > 0){
		var accId = checkObjectIsNotNull(json.accId);
		var accName = checkObjectIsNotNull(json.accName) ;
		var balance = checkObjectIsNotNull(json.balance);
		var firstRechargeTime = checkObjectIsNotNull(json.firstRechargeTime) ;
		var lastLoginTime = checkObjectIsNotNull(json.lastLoginTime) ;
		var nickname = checkObjectIsNotNull(json.nickname) ;
		var rechargeCashTotal = checkObjectIsNotNull(json.rechargeCashTotal);
		var firstWithdrawTime = checkObjectIsNotNull(json.firstWithdrawTime) ;
		var online = checkObjectIsNotNull(json.online) ;
		var COM = "CTT";
		var withdrawCashTotal = checkObjectIsNotNull(json.withdrawCashTotal) ;
		var lastWithdrawTime = checkObjectIsNotNull(json.lastWithdrawTime) ;
		var gameHall = checkObjectIsNotNull(json.gameHall) ;
		var scAccName = checkObjectIsNotNull(json.scAccName) ;
		var rewardCashTotal = checkObjectIsNotNull(json.rewardCashTotal) ;
		var lastRechargeTime = checkObjectIsNotNull(json.lastRechargeTime);
		var nowGame = checkObjectIsNotNull(json.nowGame);
		var bcAccName = checkObjectIsNotNull(json.bcAccName);
		var currency = checkObjectIsNotNull(json.currency);
		var withdrawTotal = checkObjectIsNotNull(json.withdrawTotal) ;
		var coAccName = checkObjectIsNotNull(json.coAccName);
		var upperAccLevelType = checkObjectIsNotNull(json.upperAccLevelType);
		var rechargeTotal = checkObjectIsNotNull(json.rechargeTotal);
		var saAccName = checkObjectIsNotNull(json.saAccName);
		var createIp = checkObjectIsNotNull(json.createIp) ;
		var accStatus = checkObjectIsNotNull(json.accStatus);
		var agAccName = checkObjectIsNotNull(json.agAccName);
		var lastIp = checkObjectIsNotNull(json.lastIp);
		var createTime = checkObjectIsNotNull(json.createTime);
		var memberRealName = checkObjectIsNotNull(json.memberRealName);
		var qqAcc = checkObjectIsNotNull(json.qqAcc);
		var wechatAcc = checkObjectIsNotNull(json.wechatAcc);
		var phoneNumber = checkObjectIsNotNull(json.phoneNumber);
		
		var handicap = checkObjectIsNotNull(json.handicap).split("").join(",");
		
		
		var bankCardId1 = checkObjectIsNotNull(json.bankCardId1);
		var bankCardId2 = checkObjectIsNotNull(json.bankCardId2);
		var bank1 = checkObjectIsNotNull(json.bank1);
		var bank2 = checkObjectIsNotNull(json.bank2);
		var bankAccName1 = checkObjectIsNotNull(json.bankAccName1);
		var bankAccName2 = checkObjectIsNotNull(json.bankAccName2);
		var bankAcc1 = checkObjectIsNotNull(json.bankAcc1);
		var bankAcc2 = checkObjectIsNotNull(json.bankAcc2);
		var bankCardBranches1 = checkObjectIsNotNull(json.bankCardBranches1);
		var bankCardBranches2 = checkObjectIsNotNull(json.bankCardBranches2);
		var area1 = checkObjectIsNotNull(json.area1);
		var area2 = checkObjectIsNotNull(json.area2);
		
		var str = []; 
		str[0] = '<div class="modal-content width-percent-960 margin-fix-5"><span class="close" onclick="onClickCloseModal();">×</span><table class="user-query" id = "detailsTable"> \n';
		str[1] = '<tbody><tr><th colspan="8">會員平台資料</th></tr> \n';
		str[2] = '<tr class="bg-color-odd"> \n';
		str[3] = '<td>會員帳號</td><td>'+accName+'</td><td>當前餘額</td><td>'+balance+'</td><td>首次充值時間</td><td>'+firstRechargeTime+'</td><td>最後登錄時間</td><td>'+lastLoginTime+'</td> \n';
		str[4] = '</tr> \n';
		str[5] = '<tr class="bg-color-odd"> \n';
		str[6] = '<td>用戶名稱</td><td>'+nickname+'</td><td>總充值金額</td><td>'+rechargeCashTotal+'</td><td>首次提現時間</td><td>'+firstWithdrawTime+'</td><td>是否在線</td><td>'+online+'</td> \n';
		str[7] = '</tr> \n';
		str[8] = '<tr class="bg-color-odd"> \n';
		str[9] = '<td>所屬公司</td><td>'+COM+'</td><td>總提現金額</td><td>'+withdrawCashTotal+'</td><td>最後充值時間</td><td>'+lastRechargeTime+'</td><td>當前遊戲廳</td><td>'+gameHall+'</td> \n';
		str[10] = '</tr> \n';
		str[11] = '<tr class="bg-color-odd"> \n';
		str[12] = '<td>總監</td><td>'+scAccName+'</td><td>總退水金額</td><td>'+rewardCashTotal+'</td><td>最後提現時間</td><td>'+lastWithdrawTime+'</td><td>現在遊戲</td><td>'+nowGame+'</td>  \n';
		str[13] = '</tr> \n';
		str[14] = '<tr class="bg-color-odd"> \n';
		str[15] = '<td>大股東</td><td>'+bcAccName+'</td><td>貨幣類型</td><td>'+currency+'</td><td>總提現次數</td><td>'+withdrawTotal+'</td><td>盤口</td><td>'+handicap+'</td> \n';
		str[16] = '</tr> \n';
		str[17] = '<tr class="bg-color-odd"> \n';
		str[18] = '<td>股東</td><td>'+coAccName+'</td><td>會員類型</td><td>'+upperAccLevelType+'</td><td>總充值次數</td><td>'+rechargeTotal+'</td><td></td><td></td> \n';
		str[19] = '</tr> \n';
		str[20] = '<tr class="bg-color-odd"> \n';
		str[21] = '<td>總代</td><td>'+saAccName+'</td><td>註冊IP</td><td>'+createIp+'</td><td>啟用狀態</td><td>'+accStatus+'</td><td></td><td></td> \n';
		str[23] = '</tr> \n';
		str[24] = '<tr class="bg-color-odd"> \n';
		str[25] = '<td>代理</td><td>'+agAccName+'</td><td>最後登入IP</td><td>'+lastIp+'</td><td>註冊時間</td><td>'+createTime+'</td><td></td><td></td> \n';
		str[26] = '</tr> \n';
		str[27] = '<tr><th colspan="8">會員個人資料</th></tr> \n';
		str[28] = '<tr class="text-left"> \n';
		str[29] = '<td colspan="4">會員真實姓名:'+memberRealName+'</td><td colspan="4">密碼:<button onclick="updatePwd(\''+accId+'\');">重置密碼</button></td> \n';
		str[30] = '</tr> \n';
		str[31] = '<tr class="text-left"> \n';
		str[32] = '<td colspan="4">手機號:'+phoneNumber+'</td><td colspan="4">取款密碼:<button onclick="updateWithdrawPwdDetailsAjax(\''+accId+'\');">重置取款密碼</button><span class="note" id = "updateWithdrawPwdMesseg"></span></td> \n';
		str[33] = '</tr> \n';
		str[34] = '<tr class="text-left"> \n';
		str[35] = '<td colspan="4">QQ號:'+qqAcc+'</td><td colspan="4"></td></tr><tr class="text-left"><td colspan="4">微信號:'+wechatAcc+'</td><td colspan="4"></td> \n';
		str[36] = '</tr> \n';
		str[37] = '<tr><th colspan="8">會員銀行資料</th></tr> \n';
		str[38] = '<tr class="text-left bg-color"> \n';
		str[39] = '<td colspan="4">銀行</td><td colspan="4">銀行</td> \n';
		str[40] = '</tr> \n';
		str[41] = '<tr class="text-left"> \n';
		str[42] = '<td colspan="4">開戶行:'+bank1+'</td><td colspan="4">開戶行:'+bank2+'</td> \n';
		str[43] = '</tr> \n';
		str[44] = '<tr class="text-left"> \n';
		str[45] = '<td colspan="4">戶名:'+bankAccName1+'</td><td colspan="4">戶名:'+bankAccName2+'</td> \n';
		str[46] = '</tr> \n';
		str[47] = '<tr class="text-left"> \n';
		str[48] = '<td colspan="4">銀行卡號:'+bankAcc1+'</td><td colspan="4">銀行卡號:'+bankAcc2+'</td> \n';
		str[49] = '</tr> \n';
		str[50] = '<tr class="text-left"> \n';
		str[51] = '<td colspan="4">銀行卡所屬支行:'+bankCardBranches1+'</td><td colspan="4">銀行卡所屬支行:'+bankCardBranches2+'</td> \n';
		str[52] = '</tr> \n';
		str[53] = '<tr class="text-left"> \n';
		str[54] = '<td colspan="4">開戶行所在地區:'+area1+'</td><td colspan="4">開戶行所在地區:'+area2+'</td> \n';
		str[55] = '</tr> \n';
		
		if(bankCardId1 != "" ){
			str[56] = '<tr><td colspan="4"><button id = "deleteBank1Bnt" onclick="javascript:disableBankCard(\''+bankCardId1+'\',\''+accId+'\');">刪除</button></td> \n';
		}
		if(bankCardId2 != ""){
			str[57] = '<td colspan="4"><button id = "deleteBank2Bnt" onclick="javascript:disableBankCard(\''+bankCardId2+'\',\''+accId+'\');">刪除</button></td></tr>';
		}
		str[58] = "</tbody></table></div>";
		
		onClickOpenModal(str.join(""));

	}
}



function showAccDetails2(){
	var json = JSON.parse(getEle("accDetails").value);
	if(Object.keys(json).length > 0){
		var accId = checkObjectIsNotNull(json.accId);
		var accName = checkObjectIsNotNull(json.accName) ;
		var balance = checkObjectIsNotNull(json.balance);
		var firstRechargeTime = checkObjectIsNotNull(json.firstRechargeTime) ;
		var lastLoginTime = checkObjectIsNotNull(json.lastLoginTime) ;
		var nickname = checkObjectIsNotNull(json.nickname) ;
		var rechargeCashTotal = checkObjectIsNotNull(json.rechargeCashTotal);
		var firstWithdrawTime = checkObjectIsNotNull(json.firstWithdrawTime) ;
		var online = checkObjectIsNotNull(json.online) ;
		var COM = "CTT";
		var withdrawCashTotal = checkObjectIsNotNull(json.withdrawCashTotal) ;
		var lastWithdrawTime = checkObjectIsNotNull(json.lastWithdrawTime) ;
		var gameHall = checkObjectIsNotNull(json.gameHall) ;
		var scAccName = checkObjectIsNotNull(json.scAccName) ;
		var rewardCashTotal = checkObjectIsNotNull(json.rewardCashTotal) ;
		var lastRechargeTime = checkObjectIsNotNull(json.lastRechargeTime);
		var nowGame = checkObjectIsNotNull(json.nowGame);
		var bcAccName = checkObjectIsNotNull(json.bcAccName);
		var currency = checkObjectIsNotNull(json.currency);
		var withdrawTotal = checkObjectIsNotNull(json.withdrawTotal) ;
		var coAccName = checkObjectIsNotNull(json.coAccName);
		var upperAccLevelType = checkObjectIsNotNull(json.upperAccLevelType);
		var rechargeTotal = checkObjectIsNotNull(json.rechargeTotal);
		var saAccName = checkObjectIsNotNull(json.saAccName);
		var createIp = checkObjectIsNotNull(json.createIp) ;
		var accStatus = checkObjectIsNotNull(json.accStatus);
		var agAccName = checkObjectIsNotNull(json.agAccName);
		var lastIp = checkObjectIsNotNull(json.lastIp);
		var createTime = checkObjectIsNotNull(json.createTime);
		var memberRealName = checkObjectIsNotNull(json.memberRealName);
		var qqAcc = checkObjectIsNotNull(json.qqAcc);
		var wechatAcc = checkObjectIsNotNull(json.wechatAcc);
		var phoneNumber = checkObjectIsNotNull(json.phoneNumber);
		
		var handicap = checkObjectIsNotNull(json.handicap).split("").join(",");
		
		
		var bankCardId1 = checkObjectIsNotNull(json.bankCardId1);
		var bankCardId2 = checkObjectIsNotNull(json.bankCardId2);
		var bank1 = checkObjectIsNotNull(json.bank1);
		var bank2 = checkObjectIsNotNull(json.bank2);
		var bankAccName1 = checkObjectIsNotNull(json.bankAccName1);
		var bankAccName2 = checkObjectIsNotNull(json.bankAccName2);
		var bankAcc1 = checkObjectIsNotNull(json.bankAcc1);
		var bankAcc2 = checkObjectIsNotNull(json.bankAcc2);
		var bankCardBranches1 = checkObjectIsNotNull(json.bankCardBranches1);
		var bankCardBranches2 = checkObjectIsNotNull(json.bankCardBranches2);
		var area1 = checkObjectIsNotNull(json.area1);
		var area2 = checkObjectIsNotNull(json.area2);

		var str = [];
		str[0] = '<div class="modal-content width-percent-960 margin-fix-5"><span class="close" onclick="onClickCloseModal();">×</span><table class="user-query" id = "detailsTable"></table></div> \n';
		onClickOpenModal(str.join(""));
		
		var accDetailsArea1 = [1,2,3,4,5,6,7,8];
		var accDetailsArea2 = [10,11,12,13,16,17,18,19,20];
		var accDetailsArea3 = [15];
		
		var titleNameArray = ["會員平台資料","會員個人資料","會員銀行資料"];
		var accDetailHtmlArray1 = ["會員ID",accId,"會員帳號",accName,"當前餘額",balance,"首次充值時間",firstRechargeTime
					   ,"用戶名稱",nickname,"總充值金額",rechargeCashTotal,"首次提現時間",firstRechargeTime,"最後登錄時間",lastLoginTime
					   ,"所屬公司",COM,"總提現金額",withdrawCashTotal,"最後充值時間",lastRechargeTime,"是否在線",online
					   ,"總監",scAccName,"總退水金額",rewardCashTotal,"最後提現時間",lastWithdrawTime,"當前遊戲廳",gameHall
					   ,"大股東",bcAccName,"貨幣類型",currency,"總提現次數",withdrawTotal,"現在遊戲",nowGame
					   ,"股東",coAccName,"會員類型",upperAccLevelType,"總充值次數",rechargeTotal,"盤口",handicap
					   ,"總代",saAccName,"註冊IP",createIp,"啟用狀態",accStatus,"",""
					   ,"代理",agAccName,"最後登入IP",lastIp,"註冊時間",createTime,"",""
					   ];

		
		var accDetailHtmlArray2 = ["會員真實姓名:"+memberRealName,'密碼:<button onclick="updatePwd(\''+accId+'\');">重置密碼</button>'
					  ,"手機號:"+phoneNumber,'取款密碼:<button onclick="updateWithdrawPwdDetailsAjax(\''+accId+'\');">重置取款密碼</button><span class="note" id = "updateWithdrawPwdMesseg"></span>'
					  ,"QQ號:"+qqAcc,""
					  ,"微信號:"+wechatAcc,""
					  ,"開戶行:"+bank1,"開戶行:"+bank2
					  ,"戶名:"+bankAccName1,"戶名:"+bankAccName2
					  ,"銀行卡號:"+bankAcc1,"銀行卡號:"+bankAcc2
					  ,"銀行卡所屬支行:"+bankCardBranches1,"銀行卡所屬支行:"+bankCardBranches2
					  ,"開戶行所在地區:"+area1,"開戶行所在地區:"+area2
					  ];
		
		var accDetailsDataKey1 = 0;
		var accDetailsDataKey2 = 0;
		var titleKey = 0;
		var y = 21;
		var arrObj = [];
		arrObj[0] = {};
		arrObj[0].type = "tbody";
		arrObj[0].html = [];
		for (var i = 0; i < y; i++) {
			if(accDetailsArea1.indexOf(i) >= 0){
				arrObj[0].html[i] = {};
				arrObj[0].html[i].type = "tr";
				arrObj[0].html[i].className = "bg-color-odd";
				arrObj[0].html[i].html = [];
				for(var j = 0 ; j < 8 ; j++){
					arrObj[0].html[i].html[j] = {};
					arrObj[0].html[i].html[j].type = "td";
					arrObj[0].html[i].html[j].text = accDetailHtmlArray1[accDetailsDataKey1];
					
					accDetailsDataKey1++;
				}
				
			}
			else if(accDetailsArea2.indexOf(i) >= 0){
				arrObj[0].html[i] = {};
				arrObj[0].html[i].type = "tr";
				arrObj[0].html[i].className = "text-left";
				arrObj[0].html[i].html = [];
				for(var j = 0 ; j < 2 ; j++){
					arrObj[0].html[i].html[j] = {};
					arrObj[0].html[i].html[j].type = "td";
					arrObj[0].html[i].html[j].colspan = "4";
					arrObj[0].html[i].html[j].text = accDetailHtmlArray2[accDetailsDataKey2];
					accDetailsDataKey2++;
				}
				
			}
			else if(accDetailsArea3.indexOf(i) >= 0){
				arrObj[0].html[i] = {};
				arrObj[0].html[i].type = "tr";
				arrObj[0].html[i].className = "text-left bg-color";
				arrObj[0].html[i].html = [];
				for(var j = 0 ; j < 2 ; j++){
					arrObj[0].html[i].html[j] = {};
					arrObj[0].html[i].html[j].type = "td";
					arrObj[0].html[i].html[j].colspan = "4";
					arrObj[0].html[i].html[j].text = "銀行";
				}
				
				
			}
			else {
				arrObj[0].html[i] = {};
				arrObj[0].html[i].type = "tr";
				arrObj[0].html[i].html = [];
				
				arrObj[0].html[i].html[0] = {};
				arrObj[0].html[i].html[0].type = "th";
				arrObj[0].html[i].html[0].colspan = "8";
				arrObj[0].html[i].html[0].text = ""+titleNameArray[titleKey];
				titleKey++;
			}
		}
		insertTableEle("detailsTable",arrObj);
	}
}



function disableBankCard(bankCardId,accId){
	if(ocjectIsNull(bankCardId) && ocjectIsNull(accId)){
		onCheckModelPage("是否確定要刪除此銀行卡","disableBankCardAjax(\""+bankCardId+"\",\""+accId+"\")");
	}
}

function disableBankCardAjax(bankCardId,accId){
	enableLoadingPage();
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "url=updateBankData&tokenId="+encodeURIComponent(getEle("tokenId").value)+"&accId="+encodeURIComponent(accId)+"&bankCardId="+encodeURIComponent(bankCardId);
		XHR.timeout = 10000;
		XHR.ontimeout=function(){disableLoadingPage();XHR.abort();}
		XHR.onerror=function(){disableLoadingPage();XHR.abort();}
		XHR.onreadystatechange = handleDisableBankCardAjax;
		XHR.open("POST","./AccountManage!disableBankCard.php?date="+ getNewTime(), true);
		XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();
	}
}
function handleDisableBankCardAjax(){
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try{
				if (isJSON(XHR.responseText)) {
					var json = JSON.parse(XHR.responseText);
					if(checkTokenIdfail(json)){
						if (json.isSuccess){
							onCheckCloseModelPage();
							onClickCloseModal();
							
						}
					}
				}
			}catch(error){
				console_Log("handleDisableBankCardAjax error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR.abort();
				if (json.isSuccess && typeof json.isSuccess != undefined && json.isSuccess != null){
					getAccDetails(getEle("detailsAccId").value);
				}
			}
		}
		else{
			disableLoadingPage();
			XHR.abort();
		}
	}
}
function checkDetailInputBank() {
	getEle("bank").value = checkInputNameOutVal(getEle("bank").value);
}
function checkDetailInputBankAccName() {
	getEle("bankAccName").value = checkInputNameOutVal(getEle("bankAccName").value);
}
function checkDetailInputBankBranches() {
	getEle("bankCardBranches").value = checkInputNameOutVal(getEle("bankCardBranches").value);
}
function checkDetailInputBankArea() {
	getEle("area").value = checkInputNameOutVal(getEle("area").value);
}
function checkBankCard(id,value){
	if(checkBankCard != "" && checkBankCard != null && checkBankCard != undefined){
		getEle(id).value = chkInputBankNo(value);
	}
}

function updateBankData(bankType,accId){
	var bank = getEle("bank").value;
	var bankAcc = getEle("bankAcc").value;
	var bankAccName= getEle("bankAccName").value;
	var bankCardBranches = getEle("bankCardBranches").value;
	var area = getEle("area").value;
	
	var checkBankDataJson = JSON.parse(getEle("checkBankDataJson").value);
	if(bank != "" && bankAcc != "" && bankAccName != "" && bankCardBranches != "" && area != ""){
		if(bank != checkBankDataJson.bank || bankAcc != checkBankDataJson.bankAcc || bankAccName != checkBankDataJson.bankAccName || bankCardBranches != checkBankDataJson.bankCardBranches || area != checkBankDataJson.area){
			updateBankDataAjax(bankType,accId,bank,bankAcc,bankAccName,bankCardBranches,area);
		}
		else{
			onCheckCloseModelPage();
		}
		
	}
}

function updateBankDataAjax(bankType,accId,bank,bankAcc,bankAccName,bankCardBranches,area){
	enableLoadingPage();
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "url=updateBankData&tokenId="+encodeURIComponent(getEle("tokenId").value)+"&accId="+encodeURIComponent(accId)+"&banktype="+encodeURIComponent(bankType)+"&bank="+encodeURIComponent(bank)
		+"&bankAcc="+encodeURIComponent(bankAcc)+"&bankAccName="+encodeURIComponent(bankAccName)+"&bankCardBranches="+encodeURIComponent(bankCardBranches)+"&area="+encodeURIComponent(area);
		XHR.timeout = 10000;
		XHR.ontimeout=function(){disableLoadingPage();XHR.abort();}
		XHR.onerror=function(){disableLoadingPage();XHR.abort();}
		XHR.onreadystatechange = handleUpdateBankDataAjax;
		XHR.open("POST","./AccountManage!updateBankData.php?date="+ getNewTime(), true);
		XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();
	}
}

function handleUpdateBankDataAjax(){
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try{
				if (isJSON(XHR.responseText)) {
					var tmp = JSON.parse(XHR.responseText);
					if(checkTokenIdfail(tmp)){
						if (tmp.isSuccess){
							onCheckCloseModelPage();
							onClickCloseModal();
						}
					}
				}
			}catch(error){
				console_Log("handleUpdateBankDataAjax error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR.abort();
			}
		}
		else{
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function updateWithdrawPwdDetailsAjax(accId){
	enableLoadingPage();
	XHR = checkXHR(XHR);
	if (typeof XHR != "undefined" && XHR != null) {
		var tmpstr = "tokenId="+encodeURIComponent(getEle("tokenId").value)+"&accId="+accId;
		XHR.timeout = 10000;
		XHR.ontimeout=function(){disableLoadingPage();XHR.abort();}
		XHR.onerror=function(){disableLoadingPage();XHR.abort();}
		XHR.onreadystatechange = handleUpdateWithdrawPwdDetailsAjax;
		XHR.open("POST","./AccountManage!updateMemberWithdrawPwd.php?date="+ getNewTime(), true);
		XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XHR.send(tmpstr);
		enableLoadingPage();
	}
}

function handleUpdateWithdrawPwdDetailsAjax(){
	if (XHR.readyState == 4) {
		if (XHR.status == 200) {
			try{
				if (isJSON(XHR.responseText)) {
					var tmp = JSON.parse(XHR.responseText);
					if(checkTokenIdfail(tmp)){
						if (tmp.message == "success"){
							getEle("updateWithdrawPwdMesseg").innerHTML = "已重置";
						}
					}
				}
			}catch(error){
				console_Log("handleUpdateWithdrawPwdDetailsAjax error:"+error.message);
			}finally{
				disableLoadingPage();
				XHR.abort();
			}
		}
		else{
			disableLoadingPage();
			XHR.abort();
		}
	}
}

function setHandicap(val , checkedVal=true){
	var accHandicap = toInt(val);
	var handicapEle = document.getElementsByName("handicap");
	for(var i = 0 ; i < handicapEle.length ; i++){
		var hNum = toInt(handicapEle[i].value);
		if((accHandicap&hNum) > 0){
			handicapEle[i].checked = checkedVal;
		}
	}
}

function displayHandicap(isDisplay=false,isChecked=false,handicapNum){
	var handicapArr = ["sHandicapA","sHandicapB","sHandicapC","sHandicapD"];
	var handicapEle = document.getElementsByName("handicap");
	for(var i = 0 ; i < handicapEle.length ; i++){
		if((handicapNum&handicapEle[i].value) > 0){
			if(isDisplay){
				handicapEle[i].style.display = "";
				handicapEle[i].parentNode.style.display = "";
			}
			else{
				handicapEle[i].style.display = "none";
				handicapEle[i].parentNode.style.display = "none";
			}
			if(isChecked){
				handicapEle[i].checked = true;
			}
			else{
				handicapEle[i].checked = false;
			}
		}
	}
}


function displayAllHandicap(isDisplay=false,isChecked=false){
	var handicapArr = ["sHandicapA","sHandicapB","sHandicapC","sHandicapD"];
	var handicapEle = document.getElementsByName("handicap");
	for(var i = 0 ; i < handicapEle.length ; i++){
		if(isDisplay){
			handicapEle[i].style.display = "";
			handicapEle[i].parentNode.style.display = "";
		}
		else{
			handicapEle[i].style.display = "none";
			handicapEle[i].parentNode.style.display = "none";
			
		}
		if(isChecked){
			handicapEle[i].checked = true;
		}
		else{
			handicapEle[i].checked = false;
		}
		
	}
}

function checkPwdModePage(val,pwd){
	var str = [];
	str.push('<div class="modal-content width-percent-20"> \n');
	str.push('<span class="close" onclick="onCheckCloseModelPage();">×</span> \n');
	str.push( '<h3 class="text-center">'+val+'</h3> \n');
	str.push('<div class="btn-area"> \n');
	str.push('<button onclick = "onCheckCloseModelPage();">確定</button> <button onclick = "copyStrFun(\''+pwd+'\');">複製</button> \n');
	str.push(' </div></div> \n');
	
	
	getEle("myModalV2").innerHTML = str.join('');
	getEle("myModalV2").style.display = "block";
}

function copyStrFun(s) {
	var clip_area = document.createElement('textarea');
	clip_area.textContent = s;
	document.body.appendChild(clip_area);
	clip_area.select();
	document.execCommand('copy');
	clip_area.remove();
	onCheckCloseModelPage();
	onCheckModelPage2("複製成功")
}




