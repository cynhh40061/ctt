function js_ffc(){return true;}

var title_FFC = 'ssc'; 

   // 一星定位膽 z
	function ffcdwdlx(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_FFC + 'dwd1x_';
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
	function ffcr2(data) {
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
			var type = title_FFC + 'r2_';
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
	function ffcr2q(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千';
			var type = title_FFC + 'r2_';
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
	function ffcr2h(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '十,個';
			var type = title_FFC + 'r2_';
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
	function ffcr2hzq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千';
			var type = title_FFC + 'r2hz_';
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
	function ffcr2hzh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '十,個';
			var type = title_FFC + 'r2hz_';
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
	function ffcr2kdq(data) {

		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千';
			var type = title_FFC + 'r2kd_';
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
	function ffcr2kdh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '十,個';
			var type = title_FFC + 'r2kd_';
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
	function ffcr2z(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r2z_';
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
	function ffcr2zq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千';
			var type = title_FFC + 'r2z_';
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
	function ffcr2zh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '十,個';
			var type = title_FFC + 'r2z_';
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
	function ffcr2zhzq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千';
			var type = title_FFC + 'r2zhz_';
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
	function ffcr2zhzh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '十,個';
			var type = title_FFC + 'r2zhz_';
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
	function ffcr2bdq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千';
			var type = title_FFC + 'r2bd_';
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
	function ffcr2bdh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '十,個';
			var type = title_FFC + 'r2bd_';
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
	function ffcr3(data) { 
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
			var type = title_FFC + 'r3_';
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
	function ffcr3q(data) {
		
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_FFC + 'r3_';
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
	function ffcr3z(data) {

		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_FFC + 'r3_';
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
	function ffcr3h(data) {
		
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_FFC + 'r3_';
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
	function ffcr3z3(data) { 
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r3z3_';
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
	function ffcr3z3q(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_FFC + 'r3z3_';
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
	function ffcr3z3z(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_FFC + 'r3z3_';
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
	function ffcr3z3h(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_FFC + 'r3z3_';
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
	function ffcr3z6(data, position) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r3z6_';
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
	function ffcr3z6q(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_FFC + 'r3z6_';
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
	function ffcr3z6z(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_FFC + 'r3z6_';
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
	function ffcr3z6h(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_FFC + 'r3z6_';
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
	function ffcr3hz(data, selectionQty, position, otherInfo ) { 
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r3hz_';
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
	function ffcr3hzq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_FFC + 'r3hz_';
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
	function ffcr3hzz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_FFC + 'r3hz_';
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
	function ffcr3hzh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_FFC + 'r3hz_';
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
	function ffcr3kd(data, selectionQty, position, otherInfo ) {  
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r3kd_';
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
	function ffcr3kdq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_FFC + 'r3kd_';
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
	function ffcr3kdz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_FFC + 'r3kd_';
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
	function ffcr3kdh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_FFC + 'r3kd_';
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
	function ffcr3zhz(data) { 
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r3zhz_';
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
	function ffcr3zhzq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_FFC + 'r3zhz_';
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
	function ffcr3zhzz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_FFC + 'r3zhz_';
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
	function ffcr3zhzh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_FFC + 'r3zhz_';
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
	function ffcr3bdq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_FFC + 'r3bd_';
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
	function ffcr3bdz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_FFC + 'r3bd_';
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
	function ffcr3bdh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_FFC + 'r3bd_';
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
	function ffcr3hzwsq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_FFC + 'r3hzws_';
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
	function ffcr3hzwsz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_FFC + 'r3hzws_';
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
	function ffcr3hzwsh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_FFC + 'r3hzws_';
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
	function ffcr4(data){
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
			var type = title_FFC + 'r4_';
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
	function ffcr4q(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十';
			var type = title_FFC + 'r4_';
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
	function ffcr4h(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十,個';
			var type = title_FFC + 'r4_';
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
	function ffcr4z24() {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r4z24_';
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
	function ffcr4z24q(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十';
			var type = title_FFC + 'r4z24_';
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
	function ffcr4z24h(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十,個';
			var type = title_FFC + 'r4z24_';
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
	function ffcr4z12() {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r4z12_';
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
	function ffcr4z12q(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十';
			var type = title_FFC + 'r4z12_';
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
	function ffcr4z12h(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十,個';
			var type = title_FFC + 'r4z12_';
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
	function ffcr4z6() {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r4z6_';
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
	function ffcr4z6q(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十';
			var type = title_FFC + 'r4z6_';
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
	function ffcr4z6h(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十,個';
			var type = title_FFC + 'r4z6_';
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
	function ffcr4z4() {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r4z4_';
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
	function ffcr4z4q(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十';
			var type = title_FFC + 'r4z4_';
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
	function ffcr4z4h(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十,個';
			var type = title_FFC + 'r4z4_';
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
	function ffc5x(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_FFC + '5x';
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
	function ffc5xz120(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_FFC + '5xz120';
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
	function ffc5xz60(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_FFC + '5xz60';
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
	function ffc5xz30(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_FFC + '5xz30';
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
	function ffc5xz20(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_FFC + '5xz20';
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
	function ffc5xz10(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_FFC + '5xz10';
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
	function ffc5xz5(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_FFC + '5xz5';
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
	function ffc5zxh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_FFC + '5xbxds_';
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
	function ffc5xbxdszh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_FFC + '5xbxdszh_';
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
	function ffcr3specialq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_FFC + 'r3special_';
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
	function ffcr3specialz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十';
			var type = title_FFC + 'r3special_';
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
	function ffcr3specialh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_FFC + 'r3special_';
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
	function ffcr2ds(data, position) {

		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				//data = getContent2();
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r2_';
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
	function ffcr2dsq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				//data = getContent2();
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '萬,千';
			var type = title_FFC + 'r2_';
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
	function ffcr2dsh(data, position) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '十,個';
			var type = title_FFC + 'r2_';
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
	function ffcr3ds(data, position) {
		
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r3_';
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
	function ffcr3dsq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '萬,千,百';
			var type = title_FFC + 'r3_';
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
	function ffcr3dsz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '千,百,十';
			var type = title_FFC + 'r3_';
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
	function ffcr3dsh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '百,十,個';
			var type = title_FFC + 'r3_';
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
	function ffcr4ds(data) {		
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r4_';
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
	function ffcr4dsq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = "萬,千,百,十";
			var type = title_FFC + 'r4_';
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
	function ffcr4dsh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = "千,百,十,個";
			var type = title_FFC + 'r4_';
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
	function ffcr5ds(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '萬,千,百,十,個';
			var type = title_FFC + '5x';
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
	function ffcr2zds(data) {		
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r2z_';
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
	function ffcr2zdsq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '萬,千';
			var type = title_FFC + 'r2z_';
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
	function ffcr2zdsh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '十,個';
			var type = title_FFC + 'r2z_';
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
	function ffcr3z3ds(data) {		
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r3z3ds_';
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
	function ffcr3z3dsq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '萬,千,百';
			var type = title_FFC + 'r3z3ds_';
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
	function ffcr3z3dsz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '千,百,十';
			var type = title_FFC + 'r3z3ds_';
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
	function ffcr3z3dsh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '百,十,個';
			var type = title_FFC + 'r3z3ds_';
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
	function ffcr3z6ds(data) {		
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r3z6_';
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
	function ffcr3z6dsq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '萬,千,百';
			var type = title_FFC + 'r3z6_';
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
	function ffcr3z6dsz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = '千,百,十';
			var type = title_FFC + 'r3z6_';
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
	function ffcr3z6dsh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);

			var position = '百,十,個';
			var type = title_FFC + 'r3z6_';
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
	function ffcr3hhds(data) {		
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC;
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
	function ffcr3hhdsq(data) {
		var otherInfo = getOtherInfo();
		
		if (typeof data === "undefined" || data === '') {
				data = getContent();
		}
		var json = JSON.parse(data);

		var position = '萬,千,百';
		var type = title_FFC;
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
	function ffcr3hhdsz(data) {
		var otherInfo = getOtherInfo();
		
		if (typeof data === "undefined" || data === '') {
				data = getContent();
		}
		var json = JSON.parse(data);
			
		var position = '萬,千,百';
		var type = title_FFC;
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
	function ffcr3hhdsh(data) {
		var otherInfo = getOtherInfo();
		
		if (typeof data === "undefined" || data === '') {
				data = getContent();
		}
		var json = JSON.parse(data);
		
		var position = '百,十,個';
		var type = title_FFC;
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
	function ffcr3bdw1mq() {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_FFC + 'r3bdw1m_';
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
	function ffcr3bdw1mh() {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_FFC + 'r3bdw1m_';
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
	function ffcr3bdw1m(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r3bdw1m_';
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
	function ffcr4bdw1m(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十,個';
			var type = title_FFC + 'h4bdw1m_';
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
	function ffcr5bdw1m(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_FFC + '5xbdw1m';
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
	 function ffcr3bdw2mq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_FFC + 'r3bdw2m_';
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
	function ffcr3bdw2mh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_FFC + 'r3bdw2m_';
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
	function ffcr4bdw2m(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '千,百,十,個';
			var type = title_FFC + 'h4bdw2m_';
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
	function ffcr5bdw2m(data, selectionQty, position) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_FFC + '5xbdw2m';
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
	function ffcr5bdw3m(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百,十,個';
			var type = title_FFC + '5xbdw3m';
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
	function ffcr2bxds(data, position, otherInfo ) {
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
			var type = title_FFC + 'r2bxds_';
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
	function ffcr2bxdsq(data){
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千';
			var type = title_FFC + 'r2bxds_';
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
	function ffcr2bxdsh(data){
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '十,個';
			var type = title_FFC + 'r2bxds_';
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
	function ffcr3qxdsq (data, otherInfo ) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '萬,千,百';
			var type = title_FFC + 'q3bxds_';
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
	function ffcr3hxdsh(data, otherInfo ) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = '百,十,個';
			var type = title_FFC + 'h3bxds_';
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
	function ffcr2hz(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r2hz_';
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
	function ffcr2kd(data, selectionQty, position, otherInfo ) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r2kd_';
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
	function ffcr2zhz(data, selectionQty, position, otherInfo ) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var position = json.position;
			otherInfo["position"] = position;
			var type = title_FFC + 'r2zhz_';
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
	function ffcr2llh(data , position) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			//var position = '十,個';
			var type = title_FFC + 'r2lh';
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
	
	
	function ffcwqlhh(data){
		ffcr2llh(data,"萬,千");
	}
	function ffcwblhh(data){
		ffcr2llh(data,"萬,百");
	}
	function ffcwslhh(data){
		ffcr2llh(data,"萬,十");
	}
	function ffcwglhh(data){
		ffcr2llh(data,"萬,個");
	}
	function ffcqblhh(data){
		ffcr2llh(data,"千,百");
	}
	function ffcqslhh(data){
		ffcr2llh(data,"千,十");
	}
	function ffcqglhh(data){
		ffcr2llh(data,"千,個");
	}
	function ffcbslhh(data){
		ffcr2llh(data,"百,十");
	}
	function ffcbglhh(data){
		ffcr2llh(data,"百,個");
	}
	function ffcsglhh(data){
		ffcr2llh(data,"十,個");
	}


	
	//一帆風順 z
	function ffcyffs(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_FFC + 'yffs';
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
	function ffchscs(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_FFC + 'hscs';
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
	function ffcsxbx(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_FFC + 'sxbx';
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
	function ffcsjfc(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_FFC + 'sjfc';
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
	function ffc5mqw3x(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_FFC + '5mqw3x';
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
	function ffc4mqw3x(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_FFC + '4mqw3x';
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
	function ffcq3qw2x(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_FFC + 'q3qw2x';
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
	function ffch3qw2x(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_FFC + 'h3qw2x';
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
	function ffc5mqc3x(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_FFC + '5mqc3x';
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
	function ffc4mqc2x(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_FFC + '4mqc3x';
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
	function ffcq3qc2xq(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_FFC + 'q3qc2x';
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
	function ffch3qc2xh(data) {
		var otherInfo = getOtherInfo();
		if(otherInfo != "" && otherInfo != null && typeof otherInfo != "undefined"){
			if (typeof data === "undefined" || data === '') {
				data = getContent();
			}
			var json = JSON.parse(data);
			otherInfo["betData"] = json.data;
			
			var type = title_FFC + 'h3qc2x';
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