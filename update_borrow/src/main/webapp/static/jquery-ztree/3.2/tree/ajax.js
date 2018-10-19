var xmlHttp = createXmlHttp();
function AjaxCheckUserName(url, method) {
	var httpMethod = "POST";
	var url = url;
	xmlHttp = createXmlHttp();
	xmlHttp.onreadystatechange = method;
	xmlHttp.open(httpMethod, url, true);
	xmlHttp.send(null);
}
/*
 * @date-author : 20141114-zhuangmofei @desc:
 * 用上面的方法在传递参数为中文的时候会有问题，这里重写一个方法，暂时只有在其他名单部分使用
 */
function AjaxCheckUserNameForOther(url, method, value) {
	var httpMethod = "POST";
	var url = url;
	xmlHttp = createXmlHttp();
	xmlHttp.onreadystatechange = method;
	xmlHttp.open(httpMethod, url, true);
	xmlHttp.setRequestHeader("cache-control", "no-cache");
	xmlHttp.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	xmlHttp.send("value=" + value);
}
/* @end */
/*
 * @date-author : 20141222-zhuangmofei @desc:
 * 试用于白名单在新增的时候，对客户号进行唯一性验证多个参数&中文参数无法传递到后台的
 */
function AjaxCheckUserNameForWhite(url, method, value1, value2) {
	var httpMethod = "POST";
	var url = url;
	xmlHttp = createXmlHttp();
	xmlHttp.onreadystatechange = method;
	xmlHttp.open(httpMethod, url, true);
	xmlHttp.setRequestHeader("cache-control", "no-cache");
	xmlHttp.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	xmlHttp.send("custId=" + value1 + "&hp=" + value2);
}
/* @end */




/**
 * @reqno:H1503130023
 * @date-designer:20150313-lijia01
 * @date-author:20150313-lijia01:试用于名单复核中的单个类型为客户风险评级的新增待审核数据的是否进入反洗钱选项的验证
 */
function AjaxCheckUserNameForToAmlm(url, method, value1, value2) {
	var httpMethod = "POST";
	var url = url;
	xmlHttp = createXmlHttp();
	xmlHttp.onreadystatechange = method;
	xmlHttp.open(httpMethod, url, true);
	xmlHttp.setRequestHeader("cache-control", "no-cache");
	xmlHttp.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	xmlHttp.send("custId=" + value1 + "&listType=" + value2);
}
/* @end */
function createXmlHttp() {
	var xmlHttp = false;
	try {
		if (window.ActiveXObject) {
			for (var i = 6; i; i--) {
				try {
					if (i == 2) {
						xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
					} else {
						xmlHttp = new ActiveXObject("Msxml2.XMLHTTP." + i
								+ ".0");
						xmlHttp.setRequestHeader("Content-Type", "text/xml");
						xmlHttp.setRequestHeader("Content-Type", "gb2312");
					}
					break;
				} catch (e) {
					xmlHttp = false;
				}
			}
		} else if (window.XMLHttpRequest) {
			xmlHttp = new XMLHttpRequest();
			if (xmlhttp_request.overrideMimeType) {
				xmlHttp.overrideMimeType('text/xml');
			}
		}
	} catch (e) {
		xmlHttp = false;
	}
	return xmlHttp;
}