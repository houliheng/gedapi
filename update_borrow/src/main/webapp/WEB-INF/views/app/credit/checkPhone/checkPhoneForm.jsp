<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>电话核查管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		window.checkStr = false; /* 定义全局变量，判断输入框的颜色改变 */
		showListenerType();
		adjustTextareaLength("notice", "pre");
		adjustTextareaLength("description", "descriptionPre");
		if (!checkIsNull('${checkPhone.riskPoint}')) {
			checkPhoneRiskPoint();
		}
		//$("#name").focus();
		$("#inputForm").validate({
		submitHandler : function(form) {
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			var str2 = element[0].outerHTML;
			if (element.is(":checkbox")|| element.parent().is(".input-append")) {
				error.appendTo(element.parent().parent());
			}else if(element.is(":radio")){
				error.appendTo(element.parent().parent());
			}else {
				var str = element[0].outerHTML.substring(0, 2);
				var div = $("#s2id_" + element.attr("id"));
				var a = $(div).children("a.select2-choice");
				if (str == '<i') {
					element.get(0).style.backgroundColor = "pink";
					if (checkStr == true) {
						element.get(0).style.backgroundColor = "";
						checkStr = false;
					}
					element.live("input", function(event) {
						element.get(0).style.backgroundColor = "";
						// document.getElementById(IdCheck).style.backgroundColor = "";
					});
				}
				if (str == '<t') {
					element.get(0).style.backgroundColor = "pink";
					element.live("input", function(event) {
						element.get(0).style.backgroundColor = "";
					});
				}
				if (str == '<s') {
					a.get(0).style.background = "pink";
					// a.get(0).style.color = "#dbffff";
					a.get(0).style.backgroundColor = "pink";
					element.live("change", function(event) {
						event.stopPropagation();
						a.get(0).style.background = "";
					});
				}
			}
			$("#roleType").attr("disabled", "disabled");
		}
		});
	});

	//加载复选框
	function checkPhoneRiskPoint() {
		var riskPointStr = '${checkPhone.riskPoint}';
		var riskPointArray = riskPointStr.split(",");
		var checkBoxAll = $("input[name='riskPoint']");
		for (var i = 0; i < riskPointArray.length; i++) {
			$.each(checkBoxAll, function(j, checkBox) {
				var checkValue = $(checkBox).val();
				if (riskPointArray[i] == checkValue) {
					$(checkBox).attr("checked", true);
				}
			});
		}
	}

	function showListenerType(){
		var listener = $("#dialResc").val();
		if(listener == "1"){
			$("#listenerTypeTd").show();
			$("#listenerText").show();
		}else{
			$("#listenerType").val('');
			$("#listenerType").select2("val", "");
			$("#listenerTypeTd").hide();
			$("#listenerText").hide();
		}
	}

	function beforeSubmit() {
		$("#roleType").removeAttr("disabled");
	}
	function dateWhiteOnly(ss) {
	var idd = ss.id;
	$('#' + idd).css('backgroundColor', 'white');
	// $('#' + idd).css('background', 'white');
	checkStr = true;
}
</script>
<c:if test="${true == readOnly}">
	<script type="text/javascript">
		$(document).ready(function() {
			$(document).ready(function() {
				$("input[type!='button']").attr("readOnly", "readOnly");
				$("input[type='radio']").attr("disabled", "disabled");
				$("input[type='checkbox']").attr("disabled", "disabled");
				$("#description").attr("disabled", "disabled");
				$("#description1").attr("disabled", "disabled");
				disableSelect2();
				$("div[class='ribbon']").remove();
				$("div[class='searchButton']").remove();
				$("font[color='red']").remove();
				$(".Wdate").attr("onclick", "");
				$(".Wdate").removeClass("Wdate");
				$(".qdelete").remove();
				$("#btnSubmit").remove();
				$("#btnCancel").attr("value", "关闭");
			});
		});
	</script>
</c:if>
</head>
<body>
	<form:form id="inputForm" modelAttribute="checkPhone" action="${ctx}/credit/checkPhone/save" method="post" class="form-horizontal">
		<form:hidden path="applyNo" value="${applyNo}" />
		<form:hidden path="custId" />
		<sys:message content="${message}" />
		<table class="filter">
			<tr>
				<td class="ft_label">电核网查外访，注意事项：</td>
				<td class="ft_content" colspan="5">
					<pre class="textarea-width pre-style" id="pre"></pre>
					<textarea rows="5" id="notice" cols="300" class="textarea-width textarea-style" maxlength="1000" style="margin-bottom: 3px" readonly="true">${notice}</textarea>
				</td>
			</tr>
			<tr>
				<td class="ft_label">外访对象：</td>
				<td class="ft_content">
					<form:select path="roleType" id="roleType" class="input-medium" disabled="true">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('ROLE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<td class="ft_label">姓名：</td>
				<td class="ft_content">
					<form:input path="custName" htmlEscape="false" maxlength="20" class="input-medium" style="margin-bottom: 3px" readonly="true" />
				</td>
				<td class="ft_label">移动电话：</td>
				<td class="ft_content">
					<form:input path="mobileNum" htmlEscape="false" maxlength="15" class="input-medium" readonly="true" />
				</td>
			</tr>
			<tr height="20%">
				<td class="ft_label">拨打时间：</td>
				<td class="ft_content">
					<input id="dialTime" style="width:136px;margin-top: 3px" name="dialTime" type="text" readonly="true" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${checkPhone.dialTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhiteOnly(this),maxDate:new Date()})" />
					<font color="red">*</font>
				</td>
				<td class="ft_label">拨打情况：</td>
				<td class="ft_content">
					<form:select path="dialResc" class="input-medium required" onchange="showListenerType()" >
						<form:option value="" label="请选择" />
						<form:options items="${fns:getDictList('DIAL_RESC')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					<font color="red">*</font>
				</td>
				<td class="ft_label"><span id="listenerText">接听者身份：</span></td>
				<td class="ft_content" id="listenerTypeTd" >
					<form:select path="listenerType"  class="input-medium required">
						<form:option value="" label="请选择" />
						<form:options items="${fns:getDictList('LISTENER_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">异常风险点：</td>
				<td class="ft_content" colspan="5">
					<form:checkboxes path="riskPoint" class="required" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('CUST_RISK_POINT')}" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">企业电话是否经过114网查：</td>
				<td class="ft_content">
					<form:radiobuttons path="is114Check" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">电核详情：</td>
				<td class="ft_content" colspan="5">
					<pre class="textarea-width pre-style required" id="descriptionPre"></pre>
					<textarea name="description" id="description" rows="5" cols="300" class="textarea-width textarea-style required" onkeyup="adjustTextareaLength('description', 'descriptionPre');" maxlength="1000" >${checkPhone.description}</textarea>
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="searchButton" colspan="6">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="beforeSubmit()" />
					&nbsp;
					<input id="btnCancel" class="btn" type="button" value="取消" onclick="closeJBox()" />
				</td>
						<c:if test="${readOnly eq true }">
						<td>
							<input id="btnCancel" class="btn-primary btn " type="button"
								value="关闭" onclick="closeJBox()" />
						</td>
					</c:if>
			</tr>
		</table>
	</form:form>
	<c:if test="${not empty closeWindow}">
		<script type="text/javascript">
			alertx('${saveMessage}', function() {
				parent.location.reload();
				closeJBox();
			});
		</script>
	</c:if>
</body>
</html>