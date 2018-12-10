function js_115(){return true;}


function f_115q3(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_q3';
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

function f_115q3ds(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		
		var type = '115_q3';
		var result = ctt_11x5q3ds(json.data, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115q3z(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_q3z';
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

function f_115q3zds(){
	
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		
		var type = '115_q3z';
		var result = ctt_11x5q3zds(json.data, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
	
}

function f_115q3zdt(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_q3z';
		var result = ctt_dt3x2x(otherInfo["betData"], 3, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115q2(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_q2';
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

function f_115q2ds(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		
		var type = '115_q2';
		var result = ctt_11x5q2ds(json.data, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}

}

function f_115q2z(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_q2z';
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

function f_115q2zds(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		
		var type = '115_q2z';
		var result = ctt_11x5q2zds(json.data, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115q2zdt(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_q2z';
		var result = ctt_dt3x2x(otherInfo["betData"], 2, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115q3bdw(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_q3bdw';
		var result = directTransfer2OutputBdw(otherInfo["betData"], 3, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115dds(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_dds_';
		var result = directTransfer2OutputDds(otherInfo["betData"], 3, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115czw(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_czw_';
		var result = directTransfer2OutputCzw(otherInfo["betData"], 3, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115dwd(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_dwd_';
		var result = ctt_115dwdlx(otherInfo["betData"],type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r1z1(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r1';
		var result = c11x5zx(otherInfo["betData"], 1, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r2z2(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r2';
		var result = c11x5zx(otherInfo["betData"], 2, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r3z3(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r3';
		var result = c11x5zx(otherInfo["betData"], 3, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r4z4(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r4';
		var result = c11x5zx(otherInfo["betData"], 4, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r5z5(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r5';
		var result = c11x5zx(otherInfo["betData"], 5, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r6z5(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r6';
		var result = c11x5zx(otherInfo["betData"], 6, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r7z5(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r7';
		var result = c11x5zx(otherInfo["betData"], 7, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r8z5(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r8';
		var result = c11x5zx(otherInfo["betData"], 8, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r1z1ds(){
	
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r1';
		var result = ctt_11x5rx(otherInfo["betData"], 1, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
	
}

function f_115r2z2ds(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r2';
		var result = ctt_11x5rx(otherInfo["betData"], 2, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r3z3ds(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r3';
		var result = ctt_11x5rx(otherInfo["betData"], 3, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r4z4ds(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r4';
		var result = ctt_11x5rx(otherInfo["betData"], 4, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r5z5ds(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r5';
		var result = ctt_11x5rx(otherInfo["betData"], 5, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r6z5ds(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r6';
		var result = ctt_11x5rx(otherInfo["betData"], 6, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r7z5ds(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r7';
		var result = ctt_11x5rx(otherInfo["betData"], 7, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r8z5ds(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r8';
		var result = ctt_11x5rx(otherInfo["betData"], 8, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r2z2dt(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r2';
		var result = ctt_dt(otherInfo["betData"], 2, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r3z3dt(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r3';
		var result = ctt_dt(otherInfo["betData"], 3, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r4z4dt(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r4';
		var result = ctt_dt(otherInfo["betData"], 4, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r5z5dt(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r5';
		var result = ctt_dt(otherInfo["betData"], 5, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r6z5dt(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r6';
		var result = ctt_dt(otherInfo["betData"], 6, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r7z5dt(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r7';
		var result = ctt_dt(otherInfo["betData"], 7, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}

function f_115r8z5dt(){
	var otherInfo = getOtherInfo();
	if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
		var json = JSON.parse(data);
		otherInfo["betData"] = json.data;
		
		var type = '115_r8';
		var result = ctt_dt(otherInfo["betData"], 8, type, otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,type,json;
		data = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	}
}