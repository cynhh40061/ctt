function js_f3d(){return true;}


function f_3dq2ds(){
	var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var type = '3dq2';
			var result = ctt_3dr2ds(json.data, type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
}


	
function f_3dq2zds(){
	var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '萬,千';
			var type = '3dzq2';
			var result = ctt_3dr2zds(json.data, type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
}

function f_3dh2ds(){
	var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var type = '3dh2';
			var result = ctt_3dr2ds(json.data, type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}

}

function f_3dh2zds(){
	var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '十,個';
			var type = '3dzh2';
			var result = ctt_3dr2zds(json.data, type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
}

	
	
function f_3d3xds(){
	var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var type = '3d3x';
			var result = ctt_3d3xds(json.data, type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
}

function f_3dz3d(){
	var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var type = '3dz3d';
			var result = ctt_3dz3d(json.data, type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
}


function f_3dz6ds(){
	var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var type = '3dz6';
			var result = ctt_3dz6d(json.data, type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
}

function f_3dr3mix(){
	var otherInfo = getOtherInfo();
		
		if (typeof data === "undefined" || data === '') {
				data = getContent();
		}
		var json = JSON.parse(data);
		
		var result = ctt_3dr3mix(json.data,otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,json;
		data = undefined;
		json = undefined;
			getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
}

   // 一星定位膽 z
	function f_3ddwd(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = '3ddw';
			var result = ctt_f3ddwdlx(otherInfo["betData"], type, position, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,position,type,json;
			data = undefined;
			position = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	} 
	
	// 前二星直選 z
	function f_3dq2(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十';
			var type = '3dq2';
			var result = ctt_f3dr2(otherInfo["betData"], type, position, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,position,type,json;
			data = undefined;
			position = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	}
	
	// 前二組選複式 z
	function f_3dq2z(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = '3dzq2';
			var result = ctt_f3dr2z(otherInfo["betData"], type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
						getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	}
	
	// 後二星直選 z
	function f_3dh2(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '十,個';
			var type = '3dh2';
			var result = ctt_f3dr2(otherInfo["betData"], type, position, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,position,type,json;
			data = undefined;
			position = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	}
	
	// 後二組選複式 z
	function f_3dh2z(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = '3dzh2';
			var result = ctt_f3dr2z(otherInfo["betData"], type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
						getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	}
	
		// 前三星直選 z
	function f_3d3x(data) {
		
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = '3d3x';
			var result = ctt_f3dr3(otherInfo["betData"], type, position, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,position,type,json;
			data = undefined;
			position = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}

	}
	
		// 前三直選和值 z
	function f_3d3xhz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = '3d3xhz';
			var result = ctt_f3dr3hz(otherInfo["betData"], type, position, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,position,type,json;
			data = undefined;
			position = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}

	}
	
	
	function f_3dz3f(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = '3dz3f';
			var result = ctt_f3dr3z3(otherInfo["betData"], type, position, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,position,type,json;
			data = undefined;
			position = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	}
	
	function f_3dz6(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = '3dz6';
			var result = ctt_f3dr3z6(otherInfo["betData"], type, position, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,position,type,json;
			data = undefined;
			position = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	} 
	
	
	function f_3dzxhz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = '3dzxhz';
			var result = ctt_f3dr3zhz(otherInfo["betData"], type, position, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,position,type,json;
			data = undefined;
			position = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	}
	
	function f_3dbdd() {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = '3dbdd';
			var result = ctt_f3dr3bdw1m(otherInfo["betData"], type, position, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,position,type,json;
			data = undefined;
			position = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	}
	
	function f_3dbddwq32(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = '3dbdwq32';
			var result = ctt_f3dr3bdw2m(otherInfo["betData"], type, position, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,position,type,json;
			data = undefined;
			position = undefined;
			type = undefined;
			json = undefined;
						getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	} 
	
	function f_3dbslhh(data){
		f3dr2llh(data,"百,十", "1");
	}
	function f_3dbglhh(data){
		f3dr2llh(data,"百,個", "2");
	}
	function f_3dsglhh(data){
		f3dr2llh(data,"十,個", "3");
	}
	
	// 任二龍虎和 z
	function f3dr2llh(data , position, posIndex) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type =  '3dlh';
			var result = ctt_f3dr2lh(otherInfo["betData"], type, position,posIndex, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,position,type,json;
			data = undefined;
			position = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	}


	

