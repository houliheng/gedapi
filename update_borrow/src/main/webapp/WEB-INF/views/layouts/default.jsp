<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html style="overflow-x: auto; overflow-y: auto;">
<head>
<title><sitemesh:title /> - Powered By Resoft
</title>
<%@include file="/WEB-INF/views/include/head.jsp"%>
<sitemesh:head />
<c:if test="${true == readOnly}">
	<!-- 查看详细信息时生效 -->
	<script type="text/javascript">
		$(document).ready(function() {
			//文本框、大文本框、单选框、复选框只读处理
			disableBaseElement();
			//时间插件只读处理
			disableWdate();
			//下拉框只读处理
			disableSelect2();
			//新增、修改、删除、保存按钮只读处理
			hideButtons();
			//审批意见只读处理
			hideSuggDiv();
			//由于页面的特殊性，所以这里直接将所有的fongt节点删除
			$("font").hide();
		});
	</script>
</c:if>
<script type="text/javascript">
		// 文本框只读的时候 禁止浏览器回退
		document.onkeydown = function(e){
			var ev = e||window.event;// 获取对象
			var obj = ev.target || ev.srcElement;// 获取事件源
			var t = obj.type || obj.getAttribute('type');// 获取事件源类型
			var vReadOnly = obj.getAttribute('readonly');
			var vEnabled = obj.getAttribute('enabled');
			// 处理null值 情况
			vReadOnly = (vReadOnly == null) ? false : vReadOnly;
			vEnabled = (vEnabled == null) ? false : vEnabled;
			// readonly属性为true或者Enabled为false的，退格失效
			var flag1 = ((ev.keyCode==8) &&(t=="password"||t=="text"||t=="textarea") 
			&& (vReadOnly == "true" || vEnabled == "true"||vReadOnly=="readonly")) ? true : false;
			// 当敲击backspace键时，事件源类型非密码或单行，多行文本的，则退格键失效。
			var flag2 = ((ev.keyCode == 8) && t != "password" && t != "text" && t != "textarea") ? true : false;
			if (flag1){
				return false;
				}
			if (flag2){
				return false;
				}
				return true;
			};
	</script>
</head>
<body>
	<sitemesh:body />
</body>
</html>