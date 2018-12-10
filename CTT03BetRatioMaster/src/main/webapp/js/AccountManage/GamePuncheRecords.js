const GAME_PUNCHES_RECORDS_JS = true;

function showGamePunchesPage(){
	var str = [];
	
	var now = new Date();
	
	str.push('<ul class="Div2-7-searcharea"> \n');
	str.push('<li> \n');
	str.push('<span>帳號：</span> \n');
	str.push('<input type="text" name=""> \n');
	str.push('</li> \n');
	str.push('<li> \n');
	str.push('<span>局號：</span> \n');
	str.push('<input type="text" name=""> \n');
	str.push('</li> \n');
	str.push('<li> \n');
	str.push('<span>時間：</span> \n');
	str.push('<input type="text" id = "startTime"> - <input type="text" id = "endTime"> \n');
	str.push('</li> \n');
	str.push('<li> \n');
	str.push('<span>遊戲類型：</span> \n');
	str.push('<select> \n');
	str.push('<option>請選擇</option> \n');
	str.push('<option>快速戰</option> \n');
	str.push('<option>三戰兩勝</option> \n');
	str.push('<option>五戰三勝</option> \n');
	str.push('</select> \n');
	str.push('</li> \n');
	str.push('<li> \n');
	str.push('<button onclick = "searchGmaePuncheRecords();">搜尋</button> \n');
	str.push('<button>重置</button> \n');
	str.push('</li> \n');
	str.push('</ul> \n');
	str.push('<div class="Div2-7-tablearea"> \n');
	str.push('<table class="table-zebra tr-hover">');
	str.push('<tbody> \n');
	str.push('<tr> \n');
	str.push('<th>帳號</th> \n');
	str.push('<th>局號</th> \n');
	str.push('<th>遊戲類型</th> \n');
	str.push('<th>開始時間</th> \n');
	str.push('<th>結束時間</th> \n');
	str.push('<th>押注金額</th> \n');
	str.push('<th>開始金額</th> \n');
	str.push('<th>結束金額</th> \n');
	str.push('<th>總局數</th> \n');
	str.push('<th>輸贏金額</th> \n');
	str.push('<th>詳細</th> \n');
	str.push('</tr> \n');
	str.push('</tbody> \n');
	str.push('</table> \n');
	str.push('<p class="media-control text-center"> \n');
	str.push('<span>一頁 \n');
	str.push('<select><option value = "25">25筆</option><option value = "50">50筆</option><option value = "75">75筆</option><option value = "100">100筆</option></select>\n');
	str.push('</span>  \n');
	str.push(' <a href="#" class="backward"></a>');
	str.push('<a href="#" class="backward-fast"></a>');
	str.push('<span>總頁數：<i>1</i><span>/</span><i>1</i>頁</span>');
	str.push('<a href="#" class="forward"></a>');
	str.push('<a href="#" class="forward-fast"></a>');
	str.push('</p>');
	str.push('</div>');
	getEle("mainContain").innerHTML =  str.join("");
	
	getEle("startTime").value = now.getFromFormat("yyyy-MM-dd hh:mm:ss");
	getEle("endTime").value = now.getFromFormat("yyyy-MM-dd hh:mm:ss");
	
}

function searchGmaePuncheRecords(){
	var gameId = "";
	var accName = "";
	var startTime = "";
	var endTime = "";
	var gamePuncheType = "";
	
	searchGmaePuncheRecordsAjax(gameId,accName,startTime,endTime,gamePuncheType);
}

function searchGmaePuncheRecordsAjax(gameId,accName,startTime,endTime,gamePuncheType){
	
}
