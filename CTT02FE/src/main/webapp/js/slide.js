// 2018/02/13 Ason

//浮動廣告
window.onscroll = function() {adFloat()};
function adFloat() {
    document.getElementById("adLeft").style.top = (window.pageYOffset + 80) +"px";
    document.getElementById("adLeftBottom").style.top = (window.pageYOffset + 460) +"px";
    document.getElementById("adRight").style.top = (window.pageYOffset + 80) +"px";
    document.getElementById("adRightBottom").style.top = (window.pageYOffset + 460) +"px";
}

// 關閉廣告 按鈕
function closeAd(ad) {
	if(!ad.parentElement.classList.contains("hidden")){
		ad.parentElement.classList.add("hidden");
	}
}

// 跑馬燈
var marqueeScroll = new function() {
    this.height = 20; // 高度
    this.currentHeight = 0;
    this.delay = 4000; // 換行的時間
    this.scrollSpeed = 4; // 捲動速度
    this.scrollTimer = null;
    this.timer = null;
    this.enableRun = true;
    this.start = function() { // 開始翻頁-調用go
        this.enableRun = true;
        // console.log("on");
        // this.go();
    }
    this.go = function() {
        var self = this;
        if (this.currentHeight == this.height) { // 假設一行顯示完成
            this.timer = setTimeout(function() { // 計時器-設定下一段的翻頁時間
                self.go();
            }, this.delay);
            this.currentHeight = 0;
            if (this.element.scrollTop >= this.element.scrollHeight - this.height) { // 滾動完成
                this.element.scrollTop = 0;
            }
            return true;
        } else {
            if ((this.enableRun == true) || (this.currentHeight != this.height)){
                this.element.scrollTop += this.scrollSpeed;
                this.currentHeight += this.scrollSpeed;
            }
                this.timer = setTimeout(function() { // 設定翻頁自動計時器
                    self.go();
                }, 30);
        }
    }
    this.stop = function() { // 清除定時器，停止滾動
        this.enableRun = false;
        // console.log("off");
        // clearTimeout(this.timer);
    }
}

marqueeScroll.element = document.getElementById('marqueeArea');

setTimeout(function(){ marqueeScroll.go(); }, 4000);

// 登入
function logIn() {
	var x = document.getElementsByClassName("account-area-login");
    var y = document.getElementsByClassName("account-area-logout");
    x[0].style.display = "block";
    y[0].style.display = "none";
}

// 登出
function logOut() {
    var x = document.getElementsByClassName("account-area-login");
    var y = document.getElementsByClassName("account-area-logout");
    x[0].style.display = "none";
    y[0].style.display = "block";
}

// 設定區域顯示或是隱藏
function setIdDisplay(id, val){
    var tmpID = document.getElementById(id);
    if(!tmpID || tmpID==null || typeof tmpID === "undefined"){
        tmpID = document.getElementsByName(id);
        if(tmpID && tmpID != null && typeof(tmpID) != "undefined"){
            tmpID = tmpID[tmpID.length-1];
        }
    }
    if(tmpID && tmpID != null && typeof(tmpID) != "undefined"){
        tmpID.style.display = val;
        tmpID = null;
        return true;
    }else{
        tmpID = null;
        return false;
    }
}
function setIdVisibility(id, val){
    var tmpID = document.getElementById(id);
    if(!tmpID || tmpID==null || typeof tmpID === "undefined"){
        tmpID = document.getElementsByName(id);
        if(tmpID && tmpID != null && typeof(tmpID) != "undefined"){
            tmpID = tmpID[tmpID.length-1];
        }
    }
    if(tmpID && tmpID != null && typeof(tmpID) != "undefined"){
        tmpID.style.visibility = val;
        tmpID = null;
        return true;
    }else{
        tmpID = null;
        return false;
    }
}

// 遊戲頁面開啟及關閉
function openGameArea() {
    setIdDisplay("gameArea", "block");
    setIdDisplay("adLeft", "none");
    setIdDisplay("adLeftBottom", "none");
    setIdDisplay("adRight", "none");
    setIdDisplay("adRightBottom", "none");
    setIdVisibility("leftSide", "hidden");
    setIdVisibility("rightSide", "hidden");
    setIdDisplay("headerDiv", "none");
    setIdDisplay("navDiv", "none");
    setIdDisplay("marqueeDiv", "none");
    setIdDisplay("egameTop", "block");
    
}
function closeGameArea() {
    setIdDisplay("gameArea", "none");
    setIdDisplay("adLeft", "block");
    setIdDisplay("adLeftBottom", "block");
    setIdDisplay("adRight", "block");
    setIdDisplay("adRightBottom", "block");
    setIdVisibility("leftSide", "visible");
    setIdVisibility("rightSide", "visible");
    setIdDisplay("headerDiv", "block");
    setIdDisplay("navDiv", "block");
    setIdDisplay("marqueeDiv", "block");
    setIdDisplay("egameTop", "none");
}


// 充值中心_銀行匯款
function openInfoDiv(){
    document.getElementById("infoDiv").style.display = "block";
    document.getElementById("saveDiv").style.display = "none";
}

function closeInfoDiv(){
    document.getElementById("infoDiv").style.display = "none";
    document.getElementById("saveDiv").style.display = "block";
}

// 彈出入帳訊息
function openMessageSave(){
    document.getElementById("messageSave").style.display ="block";

    // 關閉 充值中心_銀行匯款_匯款資訊
    document.getElementById("infoDiv").style.display = "none";
    document.getElementById("saveDiv").style.display = "block";
    
}


// 會員中心_帳戶資料 修改資料
function openSave(){
    document.getElementById("nickNameData").style.display = "none";
    document.getElementById("phoneNumberData").style.display = "none";
    document.getElementById("qqAccountData").style.display = "none";
    document.getElementById("weChatAccountData").style.display = "none";
    document.getElementById("modifyBtn").style.display = "none";
    document.getElementById("nickName").style.display = "inline-block";
    document.getElementById("phoneNumber").style.display = "inline-block";
    document.getElementById("qqAccount").style.display = "inline-block";
    document.getElementById("weChatAccount").style.display = "inline-block";
    
    document.getElementById("saveBtn").style.display = "inline-block";
    document.getElementById("cancelBtn").style.display = "inline-block";
    
   
}
function closeSave(){
    document.getElementById("nickNameData").style.display = "inline-block";
    document.getElementById("phoneNumberData").style.display = "inline-block";
    document.getElementById("qqAccountData").style.display = "inline-block";
    document.getElementById("weChatAccountData").style.display = "inline-block";
    document.getElementById("modifyBtn").style.display = "inline-block";
    document.getElementById("nickName").style.display = "none";
    document.getElementById("phoneNumber").style.display = "none";
    document.getElementById("qqAccount").style.display = "none";
    document.getElementById("weChatAccount").style.display = "none";
    document.getElementById("saveBtn").style.display = "none";
    document.getElementById("cancelBtn").style.display = "none";
    
}

// 充值中心_銀行匯款_匯款資訊
function openBankCard(){
    document.getElementById("bankCardDiv").style.display = "block";
    getEle("bankAccName").value = getEle("hiddenRealName").value;
    cleanAddBankCardText();
}
function closeBankCardDiv(){
    document.getElementById("bankCardDiv").style.display = "none";
}

// 會員中心_修改密碼 頁籤切換
function openTabLogin(){
    document.getElementById("tabLogin").style.display = "block";
    document.getElementById("tabTake").style.display = "none";
    document.getElementById("changeTakeBtn").classList.remove("active");
    document.getElementById("changeLoginBtn").classList.add("active");
    
}
function openTake(){
    document.getElementById("tabLogin").style.display = "none";
    document.getElementById("tabTake").style.display = "block";
    document.getElementById("changeTakeBtn").classList.add("active");
    document.getElementById("changeLoginBtn").classList.remove("active");
}

// 忘記密碼
function openForgotDiv(){
    setIdDisplay("forgotDiv", "block");
}
function closeForgotBtn(){
    setIdDisplay("forgotDiv", "none");
}

// 首次登入
function logInFirst(){
    setIdDisplay("firstLoginDiv", "block");

}
function closeFirstLogin(){
    setIdDisplay("firstLoginDiv", "none");
}

// 建議瀏覽器
function closeBrowserArea(){
    setIdDisplay("browserArea", "none");
}



// POP UP MODEL
function openModelDiv(){
  setIdDisplay("modelDiv", "block");
}
function closeModelBtn(){
  setIdDisplay("modelDiv", "none");
  getEle("modelBnt").onclick = function(){closeModelBtn();};
}

// 遊戲裡廣告輪播
var myIndex = 0;
carousel();

function carousel() {
  var i;
  var x = document.getElementsByClassName("Slides");
  if (typeof x != "undefined" && typeof x == "object" && Object.keys(x).length > 0) {
      for (i = 0; i < x.length; i++) {
         x[i].style.display = "none";  
      }
      myIndex++;
      if (myIndex > x.length) {myIndex = 1}    
      x[myIndex-1].style.display = "block";  
      setTimeout(carousel, 3000); // Change image every 2 seconds
  }
}


// 輪播橫幅廣告(大)
var slideIndex = 1;
showSlides(slideIndex);

function plusSlides(n) {
    showSlides(slideIndex += n);
}

function currentSlide(n) {
    showSlides(slideIndex = n);
}

function showSlides(n) {
    var i;
    var slides = document.getElementsByClassName("mySlides");
    var dots = document.getElementsByClassName("dot");
    if (n > slides.length) { slideIndex = 1 }
    if (n < 1) { slideIndex = slides.length }
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
    	if(typeof dots[i] != "undefined" && dots[i].classList.contains("active")){
    		dots[i].classList.remove("active");
    	}
    }
    slides[slideIndex - 1].style.display = "block";
    if(typeof dots[slideIndex - 1] != "undefined" && !dots[slideIndex - 1].classList.contains("active")){
    	dots[slideIndex - 1].classList.add("active");
    }
}

// 自動輪播
function showSlidesAuto() {
    plusSlides(1);
    setTimeout(showSlidesAuto, 2000); // Change image every 2 seconds
}

// 彈跳視窗
//function showModelPageType1(title,text,bntText,fName = null,isCloseModelWindow = true){
function showModelPageType1(title,text,bntText,fName,isCloseModelWindows){
	if(!isCloseModelWindows){
		isCloseModelWindows = true;
	}
	if(!fName){
		fName = null;
	}
	var tmpFunction;
	var closeModeBtnFun = function (){closeModelBtn();};
	if(typeOf(fName) != "function"){
		tmpFunction = closeModeBtnFun
	}
	else{
		tmpFunction = function (){closeModeBtnFun(); fName();};
	}
	getEle("modelTitle").innerHTML = title;
	getEle("modelMessage").innerHTML = text;
	getEle("modelBnt").innerHTML = bntText;
	getEle("modelBnt").onclick = tmpFunction;
	if(isCloseModelWindows){
		getEle("closeModelWindow").onclick = tmpFunction;
	}
	else{
		getEle("closeModelWindow").onclick = closeModeBtnFun;
	}
	
    openModelDiv();
}
