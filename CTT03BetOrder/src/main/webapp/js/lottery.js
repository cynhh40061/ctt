var Dropdown = function(el) {
	function init() {
		var elem = document.getElementsByClassName("submenuItems");
		for(var i=0; i < elem.length; i++){
			if(elem[i]!=el){
				if(!elem[i].classList.contains("hide")){
					//elem[i].classList.toggle("hide");
					checkClassAdd(elem[i],"hide");
				}
				elem[i].style.height = "0px";
			}
			else{
				//elem[i].classList.toggle("hide");
				checkClassToggle(elem[i],"hide");
				elem[i].style.height = 32*elem[i].children.length + "px";
				if(elem[i].classList.contains("hide")){
					elem[i].style.height = "0px";
				}else{
					elem[i].style.height = 32*elem[i].children.length + "px";
				}
			}
			
//			if(elem[i].style.height == "0px"){
//				if(elem[i].parentNode.classList.contains("open")){
//					elem[i].parentNode.classList.remove("open");
//				}
//			}else{
//				if(!elem[i].parentNode.classList.contains("open")){
//					elem[i].parentNode.classList.toggle("open");
//				}
//			}
//			 for(var j =0; j< elem[i].children.length; j++ ){
//			 	if(elem[i].style.height == "0px"){
//			 		elem[i].children[j].children[0].style.height = "0px";
//			 		elem[i].children[j].children[0].style.visibility = "hidden";
//			 	}else{
//			 		elem[i].children[j].children[0].style.height = "32px";
//			 		elem[i].children[j].children[0].style.visibility = "visible";
//			 	}
//			 }
		}
		
	}

	function add(val){
		var elem = document.getElementsByClassName("submenuItems");
		for(var i=0; i < elem.length; i++){
			if(elem[i]!=el){
				checkClassAdd(elem[i],"hide");
				elem[i].style.height = "0px";
			}else{
				checkClassRemove(elem[i],"hide");
				elem[i].style.height = 32*elem[i].children.length + "px";
				if(elem[i].classList.contains("hide")){
					elem[i].style.height = "0px";
				}else{
					elem[i].style.height = 32*elem[i].children.length + "px";
				}
				
			}
		}
	}
	
	function toggleSubmenuActive(val){
		var elem = document.getElementsByClassName("submenuItems");
		for(var i=0; i < elem.length; i++){
			if(elem[i]!=el){
				for(var j=0 ; j < elem[i].children.length ; j++){
					checkClassRemove(elem[i].children[j].children[0],"submenu-active");
				}
			}
			else if(elem[i]==el && val >= 0){
				for(var j=0 ; j < elem[i].children.length ; j++){
					if(val == j){
						checkClassAdd(elem[i].children[j].children[0],"submenu-active");
					}
					else{
						checkClassRemove(elem[i].children[j].children[0],"submenu-active");
					}
				}
			}
			else if(elem[i]==el && val == -1){
				var eableVal = 0;
				for(var j=0 ; j < elem[i].children.length ; j++){
					if(checkClass(elem[i].children[j].children[0],"submenu-active")){
						eableVal = j;
					}
					else{
						checkClassRemove(elem[i].children[j].children[0],"submenu-active");
					}
				}
				checkClassAdd(elem[i].children[eableVal].children[0],"submenu-active");
			}
		}
	}
	
	function checkClass(element,className){
		if(typeOf(element) != "undefined" && element != null && className != "" && className != null){
			if(element.classList.contains(className)){
				return true;
			}
		}
		return false;
	}
	function checkClassAdd(element,className){
		if(!checkClass(element,className)){
			if(typeOf(element) != "undefined"){
				element.classList.add(className);
			}
		
		}
	}
	function checkClassRemove(element,className){
		if(checkClass(element,className)){
			if(typeOf(element) != "undefined"){
				element.classList.remove(className);
			}
		}
	}
	function checkClassToggle(element,className){
		if(typeOf(element) != "undefined"){
			if(checkClass(element,className)){
				element.classList.remove(className);
			}
			else{
				element.classList.add(className);
			}
		}
	}
	return {
		init : init,
		add : add,
		toggleSubmenuActive : toggleSubmenuActive
	}
};
function initDropdownlink(){
	var hallElem = document.getElementsByClassName("hall")[0];
	var elem1 = document.getElementsByClassName("submenuItems");
	var j = 0;
	for(var i = 0 ; i < hallElem.children[1].children[0].children.length ; i++){
		var ele = hallElem.children[1].children[0].children[i];
		ele.addEventListener("mouseover",  enableFirstMenu);
	}
}

initDropdownlink();


