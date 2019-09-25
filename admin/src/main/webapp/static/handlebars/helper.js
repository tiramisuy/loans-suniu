Handlebars.registerHelper({
    //block helper
    'formatDate': function (timestamp, format) {//格式化时间
        if (timestamp) {
            return new Date(timestamp).Format(format);
        } else {
            return '';
        }
    },
    'addOne': function (index) {
        return index + 1;
    },
    'equal': function (a, b, opts) {
        if (a == b)
            return opts.fn(this);
        else
            return opts.inverse(this);
    },
    'notEqual': function (a, b, c, opts) {
        if (a != b && a != c)
            return opts.fn(this);
        else
            return opts.inverse(this);
    },
    'equalYield': function (a, opts) {
        if (a == null || isNaN(a) || parseFloat(a) == 0)
            return opts.inverse(this);
        else
            return opts.fn(this);
    },
    'formatYield': function (num1, num2) {
        var rtn;
        if (num1 == null || isNaN(num1)) {
            rtn = 0;
        }
        if (num2 == null || isNaN(num2)) {
            rtn = 0;
        }
        rtn = (parseFloat(num1) - parseFloat(num2)).toFixed(2);
        return rtn;
    },
    'formatBank': function (str) {
        var banks = str.split("#");
        return banks[3] + "承兑";
    },
    'formatYield2': function (num1) {
        var rtn = parseFloat(num1).toFixed(2);
        return rtn;
    },
    'compareDate': function (num1, opts) {
        num1 = num1.replace(/-/g, '/')
        if (Date.parse(num1) > new Date())
            return opts.fn(this);
        else
            return opts.inverse(this);
    },
    'compareStatus': function (num1, opts) {

        if (num1 <= 4)
            return opts.fn(this);
        else
            return opts.inverse(this);
    },
    'compareMinAmount': function (leftAmount, minAmount, flag, opts) {
        var remainAmount = parseFloat(leftAmount.replaceAll(",", ""));
        if (flag == 1) {
            if (remainAmount < minAmount)
                return opts.fn(this);
            else
                return opts.inverse(this);
        } else {
            if (remainAmount >= minAmount)
                return opts.fn(this);
            else
                return opts.inverse(this);
        }

    },//减
    'subtract': function (number1, number2) {
        var big = 0;
        try {
            big = eval(number1 + "-" + number2);
        } catch (e) {
            throw new Error('Handlerbars Helper "subtract" can not deal with wrong expression:' + arguments);
        }
        return big;
    },//加
    'computeAdd': function () {
        var big = 0;
        try {
            var len = arguments.length - 1;
            for (var i = 0; i < len; i++) {
                if (arguments[i]) {
                    big = eval(big + "+" + arguments[i]);
                }
            }
        } catch (e) {
            throw new Error('Handlerbars Helper "computeAdd" can not deal with wrong expression:' + arguments);
        }
        return big;
    },//除法
    'division': function (number1, number2) {
        var big = 0;
        try {
            big = eval(number1 + "/" + number2);
        } catch (e) {
            throw new Error('Handlerbars Helper "division" can not deal with wrong expression:' + arguments);
        }
        return big.toFixed(1);
    },//乘法
    'multiplication': function (number1, number2) {
        var big = 0;
        try {
            big = eval(number1 + "*" + number2);
        } catch (e) {
            throw new Error('Handlerbars Helper "multiplication" can not deal with wrong expression:' + arguments);
        }
        return big.toFixed(1);
    }
});

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}