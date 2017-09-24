var sexMap = function () {
    return {"1":"公","2":"母"};
};
//小鸡状态
var statusMap = function () {
    return {"1":"正常散养","2":"异常处理","3":"允许出栏","4": "出栏待宰","5": "过检待售","6": "已售出"};
};
//订单状态
var orderStatusMap = function () {
    return {"1":"初始状态","2":"进脱温室","3":"出脱温室","4": "散养登记"};
};
var slaughterApplyState = {"0":"未申请","1":"已申请","2":"审批通过","3":"审批不通过"};
var orderState = {"1":"待确认","2":"已确认","3":"已取消","4": "被拒绝","5":"已发货","6": "已完成"};
var tenderStatus = {"10":"项目审核","50":"审核失败","100":"招标公告中","110":"报名期","120":"投标期","140":"等待开标","150":"开标评标","160":"所有投标审查完成，等待选标","170":"中标公示","180":"合同可签订","190":"合同已上传","200":"招标完成","300":"流标"};
var projectFamily = {"1":"鸡苗","2":"饲料","3":"疫苗","4":"工程建设","5":"屠宰","6":"物流运输","7":"农具","8":"其他"};
var projectNature = {"1":"普通","2":"扶贫"};
var bidStatus = {"ENROLL":"已报名","BID":"已投标","AUDITED":"已审查","AUDIT_FAILED":"审查失败","FAIL":"竞标失败","WIN":"竞标成功"};
var orderState = {"0":"","1":"待确认","2":"已确认","3":"已取消","4":"被拒绝","5":"已发货","6":"已完成"}
var purchaseOrderStatus = {"0":"","1":"初始化","2":"已入栏","3":"已出栏","4":"已入仓","5":"已售完"}
//新闻状态
var newsState= {"1":"草稿","2":"显示","3":"不显示"};

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

var currentDate = function () {
    var date = new Date();
    return date.Format("yyyy-MM-dd");
};

/**
 * 是否为正整数
 *
 * @param word
 * @returns {boolean}
 */
var isPositiveInteger = function (word) {
    var positive_integer_rule = /^[1-9]+[0-9]*]*$/;
    if (positive_integer_rule.test(word)) {
        return true;
    }
    return false;
};

/**
 * 是否为正数
 *
 * @param word
 * @returns {boolean}
 */
var isPositiveNumber = function (word) {
    var positive_number_rule = /^\d+(?=\.{0,1}\d+$|$)/;
    if (positive_number_rule.test(word)) {
        return true;
    }
    return false;
};
/**
 * 非负数
 * @param word
 * @returns {boolean}
 * @constructor
 */
var isNonNegative = function (word) {
    var rule = /^(0|[1-9]\d*)$/;
    if (rule.test(word)) {
        return true;
    }
    return false;
};


/**
 * 校验字段是否为空
 *
 * @param word
 * @returns {boolean}
 */
var isNull = function (word) {
    var regu = "^[ ]+$";
    var re = new RegExp(regu);
    if(!word || word == "" || re.test(word)) {
        return true;
    }
    return false;
}

/**
 * 校验手机号的长度
 *
 * @param mobile
 * @returns {boolean}
 */
var checkMobileLen = function (mobile) {
    if (mobile.length != 11) {
        return true;
    }
    return false;
};

/**
 * 座机校验
 *
 * @param phone
 * @returns {boolean}
 */
var isValidPhone = function (phone) {
    var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
    if (isPhone.test(phone)) {
        return true;
    }
    return false;
};

/**
 * 校验手机号的格式
 *
 * @param mobile
 * @returns {boolean}
 */
var checkMobileFormat = function (mobile) {
    var mobile_rule = /^1[012345789]\d{9}$/;
    if (!mobile_rule.test(mobile)) {
        return true;
    }
    return false;
};

/**
 * 校验邮箱格式
 * @param email
 * @returns {boolean}
 */
var isInvalid4Email = function (email) {
    apos=email.indexOf("@");
    dotpos=email.lastIndexOf(".");
    if (apos<1||dotpos-apos<2) {
        return true;
    }
    return false;
};

/**
 * 校验邮政编码的有效性
 *
 * @param post
 * @returns {boolean}
 */
var isValidPost = function (post) {
    var isValid = /^[1-9][0-9]{5}$/;
    if (isValid.test(post)) {
        return true;
    }
    return false;
};