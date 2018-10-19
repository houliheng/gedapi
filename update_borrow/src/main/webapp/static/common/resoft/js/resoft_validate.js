jQuery.validator.addMethod("ip", function(b, a) {
	return this.optional(a)
			|| (/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/.test(b) && (RegExp.$1 < 256
					&& RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));
}, "请输入合法的IP地址");
jQuery.validator.addMethod("newDigits", function(value, element) {
	return this.optional(element) || /^[1-9]\d*$/.test(value);
}, "请输入非0开头正整数");
jQuery.validator.addMethod("noLegalInput", function(value, element) {
	return this.optional(element) || /^[a-zA-Z0-9_\u4E00-\u9FA5-]+$/.test(value);
}, "无效特殊字符");
jQuery.validator.addMethod("abc", function(b, a) {
	return this.optional(a) || /^[a-zA-Z0-9_]*$/.test(b);
}, "请输入字母数字或下划线");
jQuery.validator.addMethod("username", function(b, a) {
	return this.optional(a) || /^[a-zA-Z0-9][a-zA-Z0-9_]{2,19}$/.test(b);
}, "3-20位字母或数字开头，允许字母数字下划线");
jQuery.validator.addMethod("noEqualTo", function(b, a, c) {
	return b != $(c).val();
}, "请再次输入不同的值");
jQuery.validator.addMethod("realName", function(b, a) {
	return this.optional(a) || /^[\u4e00-\u9fa5]{2,30}$/.test(b);
}, "姓名只能为2-30个汉字");
jQuery.validator.addMethod("userName", function(b, a) {
	return this.optional(a) || /^[\u0391-\uFFE5\w]+$/.test(b);
}, "登录名只能包括中文字、英文字母、数字和下划线");
jQuery.validator.addMethod("mobile",function(c, a) {
	var b = c.length;
	return this.optional(a)|| (b == 11 && /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(166)|(17[0-9]{1})|(18[0-9]{1})|(19[8,9]{1}))+\d{8})$/.test(c));
}, "请正确填写您的手机号码");
jQuery.validator.addMethod("simplePhone", function(c, b) {
	var a = /^(\d{3,4}-?)?\d{7,9}$/g;
	return this.optional(b) || (a.test(c));
}, "请正确填写您的电话号码");
jQuery.validator.addMethod("phone", function(c, b) {
	var a = /(^0[1-9]{1}\d{9,10}$)|(^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(166)|(17[0-9]{1})|(18[0-9]{1})|(19[8,9]{1}))+\d{8})$)/g;
	return this.optional(b) || (a.test(c));
}, "格式为:固话为区号(3-4位)号码(7-9位),手机为:13,14,15,17,18号段");
jQuery.validator.addMethod("zipCode", function(c, b) {
	var a = /^[0-9]{6}$/;
	return this.optional(b) || (a.test(c));
}, "请正确填写您的邮政编码");
jQuery.validator.addMethod("qq", function(c, b) {
	var a = /^[1-9][0-9]{4,}$/;
	return this.optional(b) || (a.test(c));
}, "请正确填写您的QQ号码");
jQuery.validator.addMethod("card", function(b, a) {
	return this.optional(a) || checkIdcard(b);
}, "请输入正确的身份证号码");
function checkIdcard(d) {
	d = d.toString();
	var f = new Array(true, false, false, false, false);
	var e = {
		11 : "北京",
		12 : "天津",
		13 : "河北",
		14 : "山西",
		15 : "内蒙古",
		21 : "辽宁",
		22 : "吉林",
		23 : "黑龙江",
		31 : "上海",
		32 : "江苏",
		33 : "浙江",
		34 : "安徽",
		35 : "福建",
		36 : "江西",
		37 : "山东",
		41 : "河南",
		42 : "湖北",
		43 : "湖南",
		44 : "广东",
		45 : "广西",
		46 : "海南",
		50 : "重庆",
		51 : "四川",
		52 : "贵州",
		53 : "云南",
		54 : "西藏",
		61 : "陕西",
		62 : "甘肃",
		63 : "青海",
		64 : "宁夏",
		65 : "新疆",
		71 : "台湾",
		81 : "香港",
		82 : "澳门",
		91 : "国外"
	};
	var d, g, b;
	var c, h;
	var a = new Array();
	a = d.split("");
	if (e[parseInt(d.substr(0, 2))] == null) {
		return f[4];
	}
	switch (d.length) {
	case 15:
		if ((parseInt(d.substr(6, 2)) + 1900) % 4 == 0
				|| ((parseInt(d.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(d
						.substr(6, 2)) + 1900) % 4 == 0)) {
			ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;
		} else {
			ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;
		}
		if (ereg.test(d)) {
			return f[0];
		} else {
			return f[2];
		}
		break;
	case 18:
		if (parseInt(d.substr(6, 4)) % 4 == 0
				|| (parseInt(d.substr(6, 4)) % 100 == 0 && parseInt(d.substr(6,
						4)) % 4 == 0)) {
			ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;
		} else {
			ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;
		}
		if (ereg.test(d)) {
			c = (parseInt(a[0]) + parseInt(a[10])) * 7
					+ (parseInt(a[1]) + parseInt(a[11])) * 9
					+ (parseInt(a[2]) + parseInt(a[12])) * 10
					+ (parseInt(a[3]) + parseInt(a[13])) * 5
					+ (parseInt(a[4]) + parseInt(a[14])) * 8
					+ (parseInt(a[5]) + parseInt(a[15])) * 4
					+ (parseInt(a[6]) + parseInt(a[16])) * 2 + parseInt(a[7])
					* 1 + parseInt(a[8]) * 6 + parseInt(a[9]) * 3;
			g = c % 11;
			h = "F";
			b = "10X98765432";
			h = b.substr(g, 1);
			if (h == a[17]) {
				return f[0];
			} else {
				return f[3];
			}
		} else {
			return f[2];
		}
		break;
	default:
		return f[1];
		break;
	}
};

$.validator.addMethod("money",function(value,element){
	var reg = /^([1-9][\d]{0,10}|0)(\.[\d]{1,2})?$/g;
	return this.optional(element) || reg.test(value);
},"格式错误：整数位最多11位，小数位精确到小数点后2位");//格式错误{11,2}=======

jQuery.validator.addMethod("idCardNoChinese", function(b, a) {
	return this.optional(a) || /^[^\u0391-\uFFE5]+$/.test(b);
}, "证件号码不能包含中文");