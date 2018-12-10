function js_k3(){return true;}


function k3hz(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'k3hz_';
		var result = directTransfer2OutputK3Hz(otherInfo["betData"], 3, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function k33dx(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'k33dx';
		var result = directTransfer2OutputK33Dx(otherInfo["betData"], 3, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function k33tx(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'k33tx';
		var result = directTransfer2OutputK33Tx(otherInfo["betData"], 3, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function k32dx(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'k22dx';
		var result = k32dxCalc(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function k32fx(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'k22fx';
		var result = directTransfer2OutputK22Fx(otherInfo["betData"], 3, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function k32ds(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		
		var type = 'k22fx';
		var result = ctt_k32ds(json.data, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function k33x(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'k33x';
		var result = ssczx(otherInfo["betData"], 3, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function k33xds(){
	
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		
		var type = 'k33x';
		var result = ctt_k33xds(json.data, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
	
	
	
}

function k32x(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'k32x';
		var result = ssczx(otherInfo["betData"], 2, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function k32xds(){
	
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		
		var type = 'k32x';
		var result = ctt_k32xds(json.data, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
	
	
}

function k33ltx(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'k33ltx';
		var result = lhtx(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function k3dx_dx(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'k3dx_';
		var result = directTransfer2OutputK3Bx(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function k3ds_ds(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'k3ds_';
		var result = directTransfer2OutputK3Ds(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function k3czw(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'k3czw';
		var result = directTransfer2OutputK3Czw(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function k3red(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'k3red';
		var result = lhtx(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function k3black(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = 'k3black';
		var result = lhtx(otherInfo["betData"], type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}