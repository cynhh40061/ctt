function js_p3p5(){return true;}


var title_P3P5 = 'ssc'; 

   // 一星定位膽 z
	function p3p5dwdlx(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_P3P5 + 'dwd1x_';
			var result = ctt_sscdwdlx(otherInfo["betData"], type, position, otherInfo);
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

	// 任二星直選 z
	function p3p5r2(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = "";
			var posArr = ['萬','千','百','十','個'];
			var po = otherInfo["betData"].split("|");
			for(var i = 0 ; i < po.length ; i++){
				if(po[i] != ""){
					position += posArr[i]+",";
				}
			}
			position = position.substring(0,position.length-1);
			var type = title_P3P5 + 'r2_';
			var result = ctt_sscr2(otherInfo["betData"], type, position, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,position,type,json,posArr,po;
			data = undefined;
			position = undefined;
			type = undefined;
			json = undefined;
			posArr = undefined;
			po = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	} 

	// 前二星直選 z
	function p3p5r2q(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千';
			var type = title_P3P5 + 'r2_';
			var result = ctt_sscr2(otherInfo["betData"], type, position, otherInfo);
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

	// 後二星直選 z
	function p3p5r2h(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '十,個';
			var type = title_P3P5 + 'r2_';
			var result = ctt_sscr2(otherInfo["betData"], type, position, otherInfo);
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


	// 前二位直選和值 z
	function p3p5r2hzq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千';
			var type = title_P3P5 + 'r2hz_';
			var result = ctt_sscr2hz(otherInfo["betData"], type, position, otherInfo);
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

	// 後二位直選和值 z
	function p3p5r2hzh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '十,個';
			var type = title_P3P5 + 'r2hz_';
			var result = ctt_sscr2hz(otherInfo["betData"], type, position, otherInfo);
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
	
	// 前二位直選跨度 z
	function p3p5r2kdq(data) {

		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千';
			var type = title_P3P5 + 'r2kd_';
			var result = ctt_sscr2kd(otherInfo["betData"], type, position, otherInfo);
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

	// 後二位直選跨度 z
	function p3p5r2kdh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '十,個';
			var type = title_P3P5 + 'r2kd_';
			var result = ctt_sscr2kd(otherInfo["betData"], type, position, otherInfo);
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

	
	//任二組選 z
	function p3p5r2z(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r2z_';
			var result = ctt_sscr2z(otherInfo["betData"], type, position, otherInfo);
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
	function p3p5r2zq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千';
			var type = title_P3P5 + 'r2z_';
			var result = ctt_sscr2z(otherInfo["betData"], type, position, otherInfo);
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
	function p3p5r2zh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '十,個';
			var type = title_P3P5 + 'r2z_';
			var result = ctt_sscr2z(otherInfo["betData"], type, position, otherInfo);
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

	// 前二位組選和值 z
	function p3p5r2zhzq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千';
			var type = title_P3P5 + 'r2zhz_';
			var result = ctt_sscr2zhz(otherInfo["betData"], type, position, otherInfo);
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

	// 後二位組選和值 z
	function p3p5r2zhzh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '十,個';
			var type = title_P3P5 + 'r2zhz_';
			var result = ctt_sscr2zhz(otherInfo["betData"], type, position, otherInfo);
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


	// 前二包膽 zx
	function p3p5r2bdq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千';
			var type = title_P3P5 + 'r2bd_';
			var result = ctt_sscr2bd(otherInfo["betData"], type, position, otherInfo);
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

	// 後二包膽 zx
	function p3p5r2bdh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '十,個';
			var type = title_P3P5 + 'r2bd_';
			var result = ctt_sscr2bd(otherInfo["betData"], type, position, otherInfo);
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



	// 任三星直選 z
	function p3p5r3(data) { 
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = "";
			var posArr = ['萬','千','百','十','個'];
			var po = otherInfo["betData"].split("|");
			for(var i = 0 ; i < po.length ; i++){
				if(po[i] != ""){
					position += posArr[i]+",";
				}
			}
			
			position = position.substring(0,position.length-1);
			var type = title_P3P5 + 'r3_';
			var result = ctt_sscr3(otherInfo["betData"], type, position, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,position,type,json,posArr,po;
			data = undefined;
			position = undefined;
			type = undefined;
			json = undefined;
			posArr = undefined;
			po = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	}

	// 前三星直選 z
	function p3p5r3q(data) {
		
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_P3P5 + 'r3_';
			var result = ctt_sscr3(otherInfo["betData"], type, position, otherInfo);
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

	// 中三星直選 z
	function p3p5r3z(data) {

		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_P3P5 + 'r3_';
			var result = ctt_sscr3(otherInfo["betData"], type, position, otherInfo);
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

	// 後三星直選 z
	function p3p5r3h(data) {
		
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_P3P5 + 'r3_';
			var result = ctt_sscr3(otherInfo["betData"], type, position, otherInfo);
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


	// 任三組三 z
	function p3p5r3z3(data) { 
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r3z3_';
			var result = ctt_sscr3z3(otherInfo["betData"], type, position, otherInfo);
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

	// 前三組三 z
	function p3p5r3z3q(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_P3P5 + 'r3z3_';
			var result = ctt_sscr3z3(otherInfo["betData"], type, position, otherInfo);
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

	// 中三組三 z
	function p3p5r3z3z(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_P3P5 + 'r3z3_';
			var result = ctt_sscr3z3(otherInfo["betData"], type, position, otherInfo);
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

	// 後三組三 z
	function p3p5r3z3h(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_P3P5 + 'r3z3_';
			var result = ctt_sscr3z3(otherInfo["betData"], type, position, otherInfo);
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

	// 任三組六 z
	function p3p5r3z6(data, position) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r3z6_';
			var result = ctt_sscr3z6(otherInfo["betData"], type, position, otherInfo);
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

	// 前三組六 z
	function p3p5r3z6q(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_P3P5 + 'r3z6_';
			var result = ctt_sscr3z6(otherInfo["betData"], type, position, otherInfo);
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

	// 中三組六 z
	function p3p5r3z6z(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_P3P5 + 'r3z6_';
			var result = ctt_sscr3z6(otherInfo["betData"], type, position, otherInfo);
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

	// 後三組六 z
	function p3p5r3z6h(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_P3P5 + 'r3z6_';
			var result = ctt_sscr3z6(otherInfo["betData"], type, position, otherInfo);
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


	// 任選三位直選和值 z
	function p3p5r3hz(data, selectionQty, position, otherInfo ) { 
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r3hz_';
			var result = ctt_sscr3hz(otherInfo["betData"], type, position, otherInfo);
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
	function p3p5r3hzq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_P3P5 + 'r3hz_';
			var result = ctt_sscr3hz(otherInfo["betData"], type, position, otherInfo);
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

	// 中三直選和值 z
	function p3p5r3hzz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_P3P5 + 'r3hz_';
			var result = ctt_sscr3hz(otherInfo["betData"], type, position, otherInfo);
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

	// 後三直選和值 z
	function p3p5r3hzh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_P3P5 + 'r3hz_';
			var result = ctt_sscr3hz(otherInfo["betData"], type, position, otherInfo);
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

	// 任選三位直選跨度  z
	function p3p5r3kd(data, selectionQty, position, otherInfo ) {  
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r3kd_';
			var result = ctt_sscr3kd(otherInfo["betData"] , type, position, otherInfo);
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

	// 前三直選跨度 z
	function p3p5r3kdq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_P3P5 + 'r3kd_';
			var result = ctt_sscr3kd(otherInfo["betData"], type, position, otherInfo);
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

	// 中三直選跨度 z
	function p3p5r3kdz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_P3P5 + 'r3kd_';
			var result = ctt_sscr3kd(otherInfo["betData"], type, position, otherInfo);
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

	// 後三直選跨度 z
	function p3p5r3kdh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_P3P5 + 'r3kd_';
			var result = ctt_sscr3kd(otherInfo["betData"], type, position, otherInfo);
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

	// 任三組選和值 z
	function p3p5r3zhz(data) { 
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r3zhz_';
			var result = ctt_sscr3zhz(otherInfo["betData"], type, position, otherInfo);
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
	//前三組選和值 z
	function p3p5r3zhzq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_P3P5 + 'r3zhz_';
			var result = ctt_sscr3zhz(otherInfo["betData"], type, position, otherInfo);
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
	//中三組選和值 z
	function p3p5r3zhzz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_P3P5 + 'r3zhz_';
			var result = ctt_sscr3zhz(otherInfo["betData"], type, position, otherInfo);
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
	//後三組選和值 z
	function p3p5r3zhzh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_P3P5 + 'r3zhz_';
			var result = ctt_sscr3zhz(otherInfo["betData"], type, position, otherInfo);
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

	// 前三包膽 z
	function p3p5r3bdq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_P3P5 + 'r3bd_';
			var result = ctt_sscr3bd(otherInfo["betData"], type, position, otherInfo);
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


	// 中三包膽 z
	function p3p5r3bdz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_P3P5 + 'r3bd_';
			var result = ctt_sscr3bd(otherInfo["betData"], type, position, otherInfo);
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


	// 後三包膽 z
	function p3p5r3bdh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_P3P5 + 'r3bd_';
			var result = ctt_sscr3bd(otherInfo["betData"], type, position, otherInfo);
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


	// 前三和值尾數 z
	function p3p5r3hzwsq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_P3P5 + 'r3hzws_';
			var result = ctt_sscr3hzws(otherInfo["betData"], type, position, otherInfo);
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

	// 中三和值尾數 z
	function p3p5r3hzwsz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_P3P5 + 'r3hzws_';
			var result = ctt_sscr3hzws(otherInfo["betData"], type, position, otherInfo);
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

	// 後三和值尾數 z
	function p3p5r3hzwsh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_P3P5 + 'r3hzws_';
			var result = ctt_sscr3hzws(otherInfo["betData"], type, position, otherInfo);
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

	//任四直選 z
	function p3p5r4(data){
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = "";
			var posArr = ['萬','千','百','十','個'];
			var po = otherInfo["betData"].split("|");
			for(var i = 0 ; i < po.length ; i++){
				if(po[i] != ""){
					position += posArr[i]+",";
				}
			}
			
			position = position.substring(0,position.length-1);
			var type = title_P3P5 + 'r4_';
			var result = ctt_sscr4(otherInfo["betData"], type, position, otherInfo);
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
	
	// 前四星直選 z
	function p3p5r4q(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十';
			var type = title_P3P5 + 'r4_';
			var result = ctt_sscr4(otherInfo["betData"], type, position, otherInfo);
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

	// 後四星直選 z
	function p3p5r4h(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十,個';
			var type = title_P3P5 + 'r4_';
			var result = ctt_sscr4(otherInfo["betData"], type, position, otherInfo);
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

	// 任四組24 z
	function p3p5r4z24() {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r4z24_';
			var result = ctt_sscr4z24(otherInfo["betData"], type, position, otherInfo);
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

	// 前四位組24 z
	function p3p5r4z24q(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十';
			var type = title_P3P5 + 'r4z24_';
			var result = ctt_sscr4z24(otherInfo["betData"], type, position, otherInfo);
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

	// 後四位組24 z
	function p3p5r4z24h(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十,個';
			var type = title_P3P5 + 'r4z24_';
			var result = ctt_sscr4z24(otherInfo["betData"], type, position, otherInfo);
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

	// 任四組12 z
	function p3p5r4z12() {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r4z12_';
			var result = ctt_sscr4z12(otherInfo["betData"], type, position, otherInfo);
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
 
	// 前四組12 z
	function p3p5r4z12q(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十';
			var type = title_P3P5 + 'r4z12_';
			var result = ctt_sscr4z12(otherInfo["betData"], type, position, otherInfo);
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

	// 後四組12 z
	function p3p5r4z12h(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十,個';
			var type = title_P3P5 + 'r4z12_';
			var result = ctt_sscr4z12(otherInfo["betData"], type, position, otherInfo);
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

	// 任四組6 z
	function p3p5r4z6() {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r4z6_';
			var result = ctt_sscr4z6(otherInfo["betData"], type, position, otherInfo);
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

	// 前四組6 z
	function p3p5r4z6q(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十';
			var type = title_P3P5 + 'r4z6_';
			var result = ctt_sscr4z6(otherInfo["betData"], type, position, otherInfo);
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

	// 後四組6 z
	function p3p5r4z6h(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十,個';
			var type = title_P3P5 + 'r4z6_';
			var result = ctt_sscr4z6(otherInfo["betData"], type, position, otherInfo);
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

	// 任四組4 z
	function p3p5r4z4() {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r4z4_';
			var result = ctt_sscr4z4(otherInfo["betData"], type, position, otherInfo);
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
	// 前四組4 z
	function p3p5r4z4q(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十';
			var type = title_P3P5 + 'r4z4_';
			var result = ctt_sscr4z4(otherInfo["betData"], type, position, otherInfo);
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
	// 後四組4 z
	function p3p5r4z4h(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十,個';
			var type = title_P3P5 + 'r4z4_';
			var result = ctt_sscr4z4(otherInfo["betData"], type, position, otherInfo);
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
	
	//----------------------------------------------------五星---------------------------------------------------- -------------------------------------------------------------
	
	// 五星直選 z
	function p3p55x(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_P3P5 + '5x';
			var result = ctt_ssc5x(otherInfo["betData"], type, otherInfo);
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

	// 五星組選120 z
	function p3p55xz120(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_P3P5 + '5xz120';
			var result = ctt_ssc5xz120(otherInfo["betData"], type, otherInfo);
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
	// 五星組選60 z
	function p3p55xz60(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_P3P5 + '5xz60';
			var result = ctt_ssc5xz60(otherInfo["betData"], type, otherInfo);
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
	// 五星組選30 z
	function p3p55xz30(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_P3P5 + '5xz30';
			var result = ctt_ssc5xz30(otherInfo["betData"], type, otherInfo);
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
	// 五星組選20 z
	function p3p55xz20(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_P3P5 + '5xz20';
			var result = ctt_ssc5xz20(otherInfo["betData"], type, otherInfo);
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
	// 五星組選10 z
	function p3p55xz10(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_P3P5 + '5xz10';
			var result = ctt_ssc5xz10(otherInfo["betData"], type, otherInfo);
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
	// 五星組選5 z
	function p3p55xz5(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_P3P5 + '5xz5';
			var result = ctt_ssc5xz5(otherInfo["betData"], type, otherInfo);
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

	// 五星總和大小單雙 z
	function p3p55zxh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_P3P5 + '5xbxds_';
			var result = ctt_ssc5xbxds(otherInfo["betData"], type, otherInfo);
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

	// 五星總和組合大小單雙 z
	function p3p55xbxdszh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_P3P5 + '5xbxdszh_';
			var result = ctt_ssc5xbxdszh(otherInfo["betData"], type, otherInfo);
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


	//------------豹子 對子 順子 半順 雜六-------------------
	
	//前三 z
	function p3p5r3specialq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_P3P5 + 'r3special_';
			var result = ctt_sscr3special(otherInfo["betData"], type , position, otherInfo);
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


	// 中三 z
	function p3p5r3specialz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_P3P5 + 'r3special_';
			var result = ctt_sscr3special(otherInfo["betData"], type, position, otherInfo);
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

	// 後三 z
	function p3p5r3specialh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_P3P5 + 'r3special_';
			var result = ctt_sscr3special(otherInfo["betData"], type, position, otherInfo);
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
	
	//-----------------------------單式---------------------------------------------------
	
	// 任二單式 z
	function p3p5r2ds(data, position) {

		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r2_';
			var result = ctt_sscr2ds(json.data, type, position, otherInfo);
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
	
	// 前二單式 z
	function p3p5r2dsq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '萬,千';
			var type = title_P3P5 + 'r2_';
			var result = ctt_sscr2ds(json.data, type, position, otherInfo);
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
	// 後二單式 z
	function p3p5r2dsh(data, position) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '十,個';
			var type = title_P3P5 + 'r2_';
			var result = ctt_sscr2ds(json.data, type, position, otherInfo);
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
	//任三直選單式 z
	function p3p5r3ds(data, position) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r3_';
			var result = ctt_sscr3ds(json.data, type, position, otherInfo);
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
	
	//前三單式 z
	function p3p5r3dsq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '萬,千,百';
			var type = title_P3P5 + 'r3_';
			var result = ctt_sscr3ds(json.data, type, position, otherInfo);
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
	
	//中三單式 z
	function p3p5r3dsz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '千,百,十';
			var type = title_P3P5 + 'r3_';
			var result = ctt_sscr3ds(json.data, type, position, otherInfo);
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
	
	//後三單式 z
	function p3p5r3dsh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '百,十,個';
			var type = title_P3P5 + 'r3_';
			var result = ctt_sscr3ds(json.data, type, position, otherInfo);
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
	
	//任四直選單式 z
	function p3p5r4ds(data) {		
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r4_';
			var result = ctt_sscr4ds(json.data, type, position, otherInfo);
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
	
	
	//前四單式 z
	function p3p5r4dsq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = "萬,千,百,十";
			var type = title_P3P5 + 'r4_';
			var result = ctt_sscr4ds(json.data, type, position, otherInfo);
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
	
	//後四單式 z
	function p3p5r4dsh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = "千,百,十,個";
			var type = title_P3P5 + 'r4_';
			var result = ctt_sscr4ds(json.data, type, position, otherInfo);
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
	//五星單式 z
	function p3p5r5ds(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '萬,千,百,十,個';
			var type = title_P3P5 + '5x';
			var result = ctt_sscr5ds(json.data, type, position, otherInfo);
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
	//任二組選單式 z
	function p3p5r2zds(data) {		
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r2z_';
			var result = ctt_sscr2zds(json.data, type, position, otherInfo);
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
	
	

	// 前二組選單式 z
	function p3p5r2zdsq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '萬,千';
			var type = title_P3P5 + 'r2z_';
			var result = ctt_sscr2zds(json.data, type, position, otherInfo);
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

	// 後二組選單式 z
	function p3p5r2zdsh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '十,個';
			var type = title_P3P5 + 'r2z_';
			var result = ctt_sscr2zds(json.data, type, position, otherInfo);
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

	//任三組三單式 z
	function p3p5r3z3ds(data) {		
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r3z3ds_';
			var result = ctt_sscr3z3ds(json.data, type, position, otherInfo);
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
	
	
	//前三組三單式 z
	function p3p5r3z3dsq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '萬,千,百';
			var type = title_P3P5 + 'r3z3ds_';
			var result = ctt_sscr3z3ds(json.data, type, position, otherInfo);
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
	//中三組三單式 z
	function p3p5r3z3dsz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '千,百,十';
			var type = title_P3P5 + 'r3z3ds_';
			var result = ctt_sscr3z3ds(json.data, type, position, otherInfo);
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
	//後三組三單式 z
	function p3p5r3z3dsh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '百,十,個';
			var type = title_P3P5 + 'r3z3ds_';
			var result = ctt_sscr3z3ds(json.data, type, position, otherInfo);
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
	//任三組六單式 z
	function p3p5r3z6ds(data) {		
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r3z6_';
			var result = ctt_sscr3z6ds(json.data, type, position, otherInfo);
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
	
	
	//前三組六單式 z
	function p3p5r3z6dsq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '萬,千,百';
			var type = title_P3P5 + 'r3z6_';
			var result = ctt_sscr3z6ds(json.data, type, position, otherInfo);
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
	
	//中三組六單式 z
	function p3p5r3z6dsz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '千,百,十';
			var type = title_P3P5 + 'r3z6_';
			var result = ctt_sscr3z6ds(json.data, type, position, otherInfo);
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
	
	//後三組六單式 z
	function p3p5r3z6dsh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '百,十,個';
			var type = title_P3P5 + 'r3z6_';
			var result = ctt_sscr3z6ds(json.data, type, position, otherInfo);
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
	
	//任三混合組選單式 z
	function p3p5r3hhds(data) {		
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5;
			var result = ctt_sscr3hhds(json.data, type, position, otherInfo);
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
	
	//前三混合組選單式 z
	function p3p5r3hhdsq(data) {
		var otherInfo = getOtherInfo();
		
		if (typeof data === "undefined" || data === '') {
				data = getContent();
		}
		var json = JSON.parse(data);

		var position = '萬,千,百';
		var type = title_P3P5;
		var result = ctt_sscr3hhds(json.data,type, position,otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,position,type,json;
		data = undefined;
		position = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	} 
	
	//中三混合組選單式 z
	function p3p5r3hhdsz(data) {
		var otherInfo = getOtherInfo();
		
		if (typeof data === "undefined" || data === '') {
				data = getContent();
		}
		var json = JSON.parse(data);
			
		var position = '萬,千,百';
		var type = title_P3P5;
		var result = ctt_sscr3hhds(json.data,type, position,otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,position,type,json;
		data = undefined;
		position = undefined;
		type = undefined;
		json = undefined;
		getMainOrders(result,otherInfo);

		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	} 
	
	//後三混合組選單式 z
	function p3p5r3hhdsh(data) {
		var otherInfo = getOtherInfo();
		
		if (typeof data === "undefined" || data === '') {
				data = getContent();
		}
		var json = JSON.parse(data);
		
		var position = '百,十,個';
		var type = title_P3P5;
		var result = ctt_sscr3hhds(json.data,type, position,otherInfo);
		var noOfBet = getCurrentBetTotal(result);
		
		delete data,position,type,json;
		data = undefined;
		position = undefined;
		type = undefined;
		json = undefined;
			getMainOrders(result,otherInfo);
		return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
	} 


	// -----------------------不定位-------------------------------

	// 前三星的一碼不定位 z
	function p3p5r3bdw1mq() {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_P3P5 + 'r3bdw1m_';
			var result = ctt_sscr3bdw1m(otherInfo["betData"], type, position, otherInfo);
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

	// 後三星的一碼不定位 z
	function p3p5r3bdw1mh() {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_P3P5 + 'r3bdw1m_';
			var result = ctt_sscr3bdw1m(otherInfo["betData"], type, position, otherInfo);
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

	// 任三星的一碼不定位 z 
	function p3p5r3bdw1m(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r3bdw1m_';
			var result = ctt_sscr3bdw1m(otherInfo["betData"], type, position, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,position,type,json;
			data = undefined;
			position = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
		
		
		if (typeof data === "undefined" || data === '') {
			data = getContent();
		}
	}
	
	

	// 四星的一碼不定位 z
	function p3p5r4bdw1m(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十,個';
			var type = title_P3P5 + 'h4bdw1m_';
			var result = ctt_sscr4bdw1m(otherInfo["betData"], type, position, otherInfo);
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

	// 五星的一碼不定位  z
	function p3p5r5bdw1m(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_P3P5 + '5xbdw1m';
			var result = ctt_sscr5bdw1m(otherInfo["betData"], type, position, otherInfo);
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

	 // 前三星的二碼不定位 z
	 function p3p5r3bdw2mq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_P3P5 + 'r3bdw2m_';
			var result = ctt_sscr3bdw2m(otherInfo["betData"], type, position, otherInfo);
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
	// 後三星的二碼不定位 z
	function p3p5r3bdw2mh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_P3P5 + 'r3bdw2m_';
			var result = ctt_sscr3bdw2m(otherInfo["betData"], type, position, otherInfo);
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

	// 四星的二碼不定位 z
	function p3p5r4bdw2m(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十,個';
			var type = title_P3P5 + 'h4bdw2m_';
			var result = ctt_sscr4bdw2m(otherInfo["betData"], type, position, otherInfo);
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
	// 五星的二碼不定位 z
	function p3p5r5bdw2m(data, selectionQty, position) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_P3P5 + '5xbdw2m';
			var result = ctt_sscr5bdw2m(otherInfo["betData"], type, position, otherInfo);
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
	// 五星的三碼不定位 z
	function p3p5r5bdw3m(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_P3P5 + '5xbdw3m';
			var result = ctt_sscr5bdw3m(otherInfo["betData"], type, position, otherInfo);
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

	// 任二大小單雙  z
	function p3p5r2bxds(data, position, otherInfo ) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = "";
			var posArr = ['萬','千','百','十','個'];
			var po = otherInfo["betData"].split("|");
			for(var i = 0 ; i < po.length ; i++){
				if(po[i] != ""){
					position += posArr[i]+",";
				}
			}
			position = position.substring(0,position.length-1);
			var type = title_P3P5 + 'r2bxds_';
			var result = ctt_sscr2bxds(otherInfo["betData"], type, position, otherInfo);
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
	
	// 前二 大小單雙 z
	function p3p5r2bxdsq(data){
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千';
			var type = title_P3P5 + 'r2bxds_';
			var result = ctt_sscr2bxds(otherInfo["betData"], type, position, otherInfo);
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
	// 後二 大小單雙 z
	function p3p5r2bxdsh(data){
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '十,個';
			var type = title_P3P5 + 'r2bxds_';
			var result = ctt_sscr2bxds(otherInfo["betData"], type, position, otherInfo);
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
	
	// 前三大小單雙 z
	function p3p5r3qxdsq (data, otherInfo ) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_P3P5 + 'q3bxds_';
			var result = ctt_sscq3bxds(otherInfo["betData"], type, position, otherInfo);
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
	// 後三大小單雙 z
	function p3p5r3hxdsh(data, otherInfo ) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_P3P5 + 'h3bxds_';
			var result = ctt_ssch3bxds(otherInfo["betData"], type, position, otherInfo);
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

	// 任選二位直選和值 (跟sschz差不多，但因為位數可以N選2，所以另外寫) z
	function p3p5r2hz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r2hz_';
			var result = ctt_sscr2hz(otherInfo["betData"], type, position, otherInfo);
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


	// 任選二位直選跨度 z
	function p3p5r2kd(data, selectionQty, position, otherInfo ) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r2kd_';
			var result = ctt_sscr2kd(otherInfo["betData"], type, position, otherInfo);
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



	// 任選二位組選和值 z
	function p3p5r2zhz(data, selectionQty, position, otherInfo ) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_P3P5 + 'r2zhz_';
			var result = ctt_sscr2zhz(otherInfo["betData"], type, position, otherInfo);
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




	// 任二龍虎和 z
	function p3p5r2llh(data , position) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			//var position = '十,個';
			var type = title_P3P5 + 'r2lh';
			var result = ctt_sscr2lh(otherInfo["betData"], type, position, otherInfo);
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
	
	
	function p3p5wqlhh(data){
		p3p5r2llh(data,"萬,千");
	}
	function p3p5wblhh(data){
		p3p5r2llh(data,"萬,百");
	}
	function p3p5wslhh(data){
		p3p5r2llh(data,"萬,十");
	}
	function p3p5wglhh(data){
		p3p5r2llh(data,"萬,個");
	}
	function p3p5qblhh(data){
		p3p5r2llh(data,"千,百");
	}
	function p3p5qslhh(data){
		p3p5r2llh(data,"千,十");
	}
	function p3p5qglhh(data){
		p3p5r2llh(data,"千,個");
	}
	function p3p5bslhh(data){
		p3p5r2llh(data,"百,十");
	}
	function p3p5bglhh(data){
		p3p5r2llh(data,"百,個");
	}
	function p3p5sglhh(data){
		p3p5r2llh(data,"十,個");
	}


	
	//一帆風順 z
	function p3p5yffs(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_P3P5 + 'yffs';
			var result = ctt_sscyffs(otherInfo["betData"], type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);

			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	} 
	// z
	function p3p5hscs(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_P3P5 + 'hscs';
			var result = ctt_sschscs(otherInfo["betData"], type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);

			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	} 
	// z
	function p3p5sxbx(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_P3P5 + 'sxbx';
			var result = ctt_sscsxbx(otherInfo["betData"], type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);

			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	} 
	// z
	function p3p5sjfc(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_P3P5 + 'sjfc';
			var result = ctt_sscsjfc(otherInfo["betData"], type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);

			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	} 

	// 五碼趣味三星 z
	function p3p55mqw3x(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_P3P5 + '5mqw3x';
			var result = ctt_ssc5mqw3x(otherInfo["betData"] , type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);

			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	} 
	// 四碼趣味三星 z
	function p3p54mqw3x(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_P3P5 + '4mqw3x';
			var result = ctt_ssc4mqw3x(otherInfo["betData"], type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);

			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	} 
	// 前三趣味二星 z
	function p3p5q3qw2x(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_P3P5 + 'q3qw2x';
			var result = ctt_sscq3qw2x(otherInfo["betData"], type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
			getMainOrders(result,otherInfo);

			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	} 
	// 後三趣味二星 z
	function p3p5h3qw2x(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_P3P5 + 'h3qw2x';
			var result = ctt_ssch3qw2x(otherInfo["betData"] , type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;

			getMainOrders(result,otherInfo);
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	} 

	// 五碼區間三星 z
	function p3p55mqc3x(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_P3P5 + '5mqc3x';
			var result = ctt_ssc5mqc3x(otherInfo["betData"], type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;

			getMainOrders(result,otherInfo);
			
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	} 
	// 四碼區間三星 z
	function p3p54mqc2x(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_P3P5 + '4mqc3x';
			var result = ctt_ssc4mqc3x(otherInfo["betData"], type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
			
		getMainOrders(result,otherInfo);

			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	} 
	// 前三區間三星 z
	function p3p5q3qc2xq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_P3P5 + 'q3qc2x';
			var result = ctt_sscq3qc2x(otherInfo["betData"], type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;

			getMainOrders(result,otherInfo);
			
			return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	} 
	// 後三區間三星 z
	function p3p5h3qc2xh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_P3P5 + 'h3qc2x';
			var result = ctt_ssch3qc2x(otherInfo["betData"], type, otherInfo);
			var noOfBet = getCurrentBetTotal(result);
			
			delete data,type,json;
			data = undefined;
			type = undefined;
			json = undefined;
			
			getMainOrders(result,otherInfo);

			//return responseFormat('success', {'result':result,'noOfBet':noOfBet,'otherInfo':otherInfo});
		}
	}