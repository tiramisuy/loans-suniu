/*
 * Metadata - jQuery plugin for parsing metadata from elements
 */
(function($){$.extend({metadata:{defaults:{type:'class',name:'metadata',cre:/({.*})/,single:'metadata'},setType:function(type,name){this.defaults.type=type;this.defaults.name=name;},get:function(elem,opts){var settings=$.extend({},this.defaults,opts);if(!settings.single.length)settings.single='metadata';var data=$.data(elem,settings.single);if(data)return data;data="{}";if(settings.type=="class"){var m=settings.cre.exec(elem.className);if(m)data=m[1];}else if(settings.type=="elem"){if(!elem.getElementsByTagName)return undefined;var e=elem.getElementsByTagName(settings.name);if(e.length)data=$.trim(e[0].innerHTML);}else if(elem.getAttribute!=undefined){var attr=elem.getAttribute(settings.name);if(attr)data=attr;}if(data.indexOf('{')<0)data="{"+data+"}";data=eval("("+data+")");$.data(elem,settings.single,data);return data;}}});$.fn.metadata=function(opts){return $.metadata.get(this[0],opts);};})(jQuery);

$.metadata.setType("attr", "validate");

$.validator.AlertError = {
	invalidHandler : function(form, validator) {
		var errors = validator.numberOfInvalids();
		if (errors) {
			for (var name in validator.invalid) {
				alert(validator.invalid[name]);
				return;
			}
		}
	},
	showErrors : function(errors) {
	}
};

$.validator.defaults.success = "valid";

$.validator.prototype.showLabel =function( element, message ) {
	var label = this.errorsFor( element );
	if ( label.length ) {
		// refresh error/success class
		label.removeClass( this.settings.validClass ).addClass( this.settings.errorClass );
		// replace message on existing label
		label.html(message);
	} else {
		// create label
		label = $("<" + this.settings.errorElement + ">")
			.attr("for", this.idOrName(element))
			.addClass(this.settings.errorClass)
			.html(message || "");
		if ( this.settings.wrapper ) {
			// make sure the element is visible, even in IE
			// actually showing the wrapped element is handled elsewhere
			label = label.hide().show().wrap("<" + this.settings.wrapper + "/>").parent();
		}
		if ( !this.labelContainer.append(label).length ) {
			if ( this.settings.errorPlacement ) {
				this.settings.errorPlacement(label, $(element) );
			} else {
				//自定义错误提示的位置
				label.appendTo($(element).parent());
			}
		}
	}
	if ( !message && this.settings.success ) {
		label.text("");
		if ( typeof this.settings.success === "string" ) {
			label.addClass( this.settings.success );
		} else {
			this.settings.success( label, element );
		}
	}
	this.toShow = this.toShow.add(label);
};

jQuery.validator.addMethod("ip", function(value, element) {
	return this.optional(element) || (/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/.test(value) && (RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256));
}, "请输入合法的IP地址");

jQuery.validator.addMethod("abc",function(value, element) {
	return this.optional(element) || /^[a-zA-Z0-9_]*$/.test(value);
},"请输入字母数字或下划线");

jQuery.validator.addMethod("username",function(value, element) {
	return this.optional(element) || /^[a-zA-Z0-9][a-zA-Z0-9_]{2,19}$/.test(value);
},"3-20位字母或数字开头，允许字母数字下划线");

// 不等于验证
jQuery.validator.addMethod("noEqualTo",function(value, element, param) {
	return value != $(param).val();
},"请再次输入不同的值");

// 真实姓名验证
jQuery.validator.addMethod("realName", function(value, element) {
	return this.optional(element) || /^[\u4E00-\u9FA5]{2,5}(?:(·|•)[\u4E00-\u9FA5]{2,5})*$/.test(value);
}, "姓名只能为2-30个汉字");

// 字符验证
jQuery.validator.addMethod("userName", function(value, element) {
	return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);
}, "登录名只能包括中文字、英文字母、数字和下划线");

// 手机号码验证
jQuery.validator.addMethod("mobile", function(value, element) {
	var length = value.length;
	return this.optional(element) || (length == 11 && /^1[34578]\d{9}$/.test(value));
}, "请输入正确的手机号码");

// 电话号码验证
jQuery.validator.addMethod("simplePhone", function(value, element) {
	var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
	return this.optional(element) || (tel.test(value));
}, "请输入正确的电话号码");

// 电话号码验证
jQuery.validator.addMethod("phone", function(value, element) {
	var tel = /(^0[1-9]{1}\d{9,10}$)|(^1[3,5,8]\d{9}$)/g;
	return this.optional(element) || (tel.test(value));
}, "格式为:固话为区号(3-4位)号码(7-9位),手机为:13,15,18号段");

// 邮政编码验证
jQuery.validator.addMethod("zipCode", function(value, element) {
	var tel = /^[0-9]{6}$/;
	return this.optional(element) || (tel.test(value));
}, "请输入正确的邮政编码");

//QQ号码验证
jQuery.validator.addMethod("qq", function(value, element) {
	var tel = /^[1-9][0-9]{4,}$/;
	return this.optional(element) || (tel.test(value));
}, "请输入正确的QQ号码");

//校验身份证好
jQuery.validator.addMethod("idcard",function(value, element) {
	return this.optional(element) || checkIdcard(value);
},"请输入正确的身份证号码(15-18位)")

//验证身份证函数
function checkIdcard(idcard){
	idcard = idcard.toString();
	//var Errors=new Array("验证通过!","身份证号码位数不对!","身份证号码出生日期超出范围或含有非法字符!","身份证号码校验错误!","身份证地区非法!");
	var Errors=new Array(true,false,false,false,false);
	var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
	var idcard,Y,JYM;
	var S,M;
	var idcard_array = new Array();
	idcard_array = idcard.split("");
	//地区检验
	if(area[parseInt(idcard.substr(0,2))]==null) return Errors[4];
	//身份号码位数及格式检验
	switch(idcard.length){
		case 15:
			if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){
				ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性
			} else {
				ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性
			}
			if(ereg.test(idcard)) return Errors[0];
			else return Errors[2];
			break;
		case 18:
			//18 位身份号码检测
			//出生日期的合法性检查
			//闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
			//平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
			if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){
				ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式
			} else {
				ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式
			}
			if(ereg.test(idcard)) {//测试出生日期的合法性
				//计算校验位
				S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
					+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
					+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
					+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
					+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
					+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
					+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
					+ parseInt(idcard_array[7]) * 1
					+ parseInt(idcard_array[8]) * 6
					+ parseInt(idcard_array[9]) * 3 ;
				Y = S % 11;
				M = "F";
				JYM = "10X98765432";
				M = JYM.substr(Y,1);//判断校验位
				if(M == idcard_array[17]) return Errors[0]; //检测ID的校验位
				else return Errors[3];
			}
			else return Errors[2];
			break;
		default:
			return Errors[1];
			break;
	}
}

$.validator.addMethod("smsCode", function(value) {
	if(value.length==0) {return true;}
	var p = /^\d{6}$/;
	return p.exec(value) ? true : false;
}, "Please enter smsCode");

$.validator.addMethod("amount", function(value) {
	if(value.length==0) {return true;}
	var p = /^([1-9]\d{0,9}|0)(\.\d{1,2})?$/;
	return p.exec(value) ? true : false;
}, "Please enter amount");


$.validator.addMethod("dependon", function( value, element, param ) {
	var target = $(param);
	if ( this.settings.onfocusout ) {
		target.unbind(".validate-dependon").bind("blur.validate-dependon", function() {
			target.valid();
		});
	}
	var tval = target.val();
	return tval!="" && value.length>0;
} , "Please enter dependon element");

$.validator.addMethod("lessEqual", function( value, element, param ) {
	return parseFloat(value) <= parseFloat($(param).val());
} , "Please enter a value less than or equal to the target");

//根据身份证号检查用户是否18岁
jQuery.validator.addMethod("checkAgeByIdcard",function(value, element, param) {
	return this.optional(element) || checkAgeByIdcard(value,param);
},"您的年龄不合符合要求");

function checkAgeByIdcard(idcard,age){
	var year = new Date().getFullYear();
	var birth =idcard.substr(6,4);
	return (year-birth-age)>=0;
}

$.extend($.validator.messages, {
	required : "该项为必填项",
	remote : "请修正该字段",
	email : "请输入正确格式的电子邮件",
	url : "请输入合法的网址",
	date : "请输入合法的日期",
	dateISO : "请输入合法的日期 ",
	number : "请输入合法的数字",
	digits : "只能输入整数",
	creditcard : "请输入合法的信用卡号",
	equalTo : "请再次输入相同的值",
	accept : "请输入拥有合法后缀名的字符串",
	maxlength : $.format("请输入一个长度最多是 {0} 的字符串"),
	minlength : $.format("请输入一个长度最少是 {0} 的字符串"),
	rangelength : $.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
	range : $.format("请输入一个介于 {0} 和 {1} 之间的值"),
	max : $.format("该项不能大于 {0}"),
	min : $.format("该项不能小于 {0}"),
	username : "3-20位字母或数字开头，允许字母数字下划线",
	mobile : "请输入正确的手机号码",
	smsCode : "请输入正确的短信验证码",
	ip : "请输入合法的IP地址",
	abc : "请输入字母数字或下划线",
	noEqualTo : "请再次输入不同的值",
	realName : "姓名只能为2-30个汉字",
	simplePhone : "请输入正确的电话号码",
	phone : "格式为:固话为区号(3-4位)号码(7-9位),手机为:13,15,18号段",
	zipCode : "请输入正确的邮政编码",
	qq : "请输入正确的QQ号码",
	idcard : "请输入正确的身份证号码",
	amount : "请输入正确的金额",
	loginname : "只能输入数字、字母和下划线的组合"
});

$.fn.extend( {
	showBy : function(target) {
		var offset = target.offset();
		var top, left;
		var b = $(window).height() + $(document).scrollTop() - offset.top
				- target.outerHeight();
		var t = offset.top - $(document).scrollTop();
		var r = $(window).width() + $(document).scrollLeft() - offset.left;
		var l = offset.left + target.outerWidth() - $(document).scrollLeft();
		if (b - this.outerHeight() < 0 && t > b) {
			top = offset.top - this.outerHeight() - 1;
		} else {
			top = offset.top + target.outerHeight() + 1;
		}
		if (r - this.outerWidth() < 0 && l > r) {
			left = offset.left + target.outerWidth() - this.outerWidth();
		} else {
			left = offset.left;
		}
		this.css("top", top).css("left", left).show();
	}
});

function loadCss( url ){
	var link = document.createElement( "link" );
	link.type = "text/css";
	link.rel = "stylesheet";
	link.href = url;
	document.getElementsByTagName( "head" )[0].appendChild( link );
};

//loadCss("/js/jquery-validation/1.11.1/jquery.validate.min.css");