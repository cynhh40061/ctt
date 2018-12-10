function js_pk10(){return true;}


function pk10gyhz(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'pk10_gy2_';
		var result = directTransfer2OutputGy(otherInfo["betData"], 7, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function pk10gyhzbxds(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'pk10_gy2dds_';
		var result = directTransfer2OutputGyBXDS(otherInfo["betData"], 7, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function pk10cmz(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'pk10_sort';
		var result = pk10dwd(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function pk10lh(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'pk10_lh_';
		var result = pk10lhcal(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function pk10q2(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'pk10_q2';
		var result = gd11x5Q2Q3(otherInfo["betData"], 2, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function pk10q2ds(){
	
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		
		var type = 'pk10_q2';
		var result = ctt_pk10q2ds(json.data, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
	
}

function pk10h2(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'pk10_h2';
		var result = gd11x5Q2Q3(otherInfo["betData"], 2, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function pk10h2ds(){
	
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		
		var type = 'pk10_h2';
		var result = ctt_pk10q2ds(json.data, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function pk10q3(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'pk10_q3';
		var result = gd11x5Q2Q3(otherInfo["betData"], 3, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function pk10q3ds(){
	
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		
		var type = 'pk10_q3';
		var result = ctt_pk10q3ds(json.data, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function pk10h3(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'pk10_h3';
		var result = gd11x5Q2Q3(otherInfo["betData"], 3, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function pk10h3ds(){
	
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		
		var type = 'pk10_h3';
		var result = ctt_pk10q3ds(json.data, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}
