function js_helper_validate(){return true;}


/**
 * @constructor
 * @description         自定義Error
 * @param {string} position		Error發生位置
 * @param {string} message		Error文字內容
 * @param {string} printMessage 印出console log
 */
/*
class CustomError {
    constructor(position = '', message = '') {
        this.position = position;
        this.message = message;
        this.printMessage = function () {
            console.error(`[${this.position}] Function. Content : ${this.message}`); // ES6寫法，Keyword: Template Literals
        };
    }
}
*/
var CustomError = (function () {
    function CustomError(position, message) {
		if(position==null || typeof position === "undefined"){
			position = '';
		}
		if(message==null || typeof message === "undefined"){
			message = '';
		}
        this.position = position;
        this.message = message;
		this.printMessage = function () {
            console.error('[' + this.position + '] Function. Content : ' + this.message);
        };
    }
	return CustomError;
})();




/**
 * @description     初步驗證直選輸入內容
 * @param {string} bet		投注列表：'123,45,2,59'
 *
 */

function checkStringInput(bet) {
    // 確認輸入的內容是不是 undefined null 空值 NaN
    if (!bet) {
        throw new CustomError('checkInput', '輸入的內容為空或輸入的格式錯誤');
    }

    // 不接受非 string 格式
    if (typeof bet !== 'string') {
        throw new CustomError('checkInput', '輸入的不是 string 格式');
    }
    return true;
}