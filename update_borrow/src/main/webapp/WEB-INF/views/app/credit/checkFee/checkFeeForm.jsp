<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>外访费登记管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		window.checkStr = false; /* 定义全局变量，判断输入框的颜色改变 */
		adjustTextareaLength("description", "pre");
		document.getElementById("checkFee").value = outputmoney("${checkFee.checkFee}");
		$("#checkFeeForm").validate({
		submitHandler : function(form) {
			validateSubmit();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});

	function setOperate(v) {
		$("#btnSave").attr("disabled", "true");
		$("#btnSubmit").attr("disabled", "true");
		loading();
		$("#operate").attr("value", v);
		$("#checkFeeForm").submit();
	}
	function validateSubmit() {
		//查询实时进件状态，若不是审批中状态，提示不可提交
		var valueT = $("#checkFee").val().replace(/,/g, "");
		$("#checkFee").val(valueT);
		$.post("${ctx}/credit/applyRegister/isSubmitting", {
			applyNo : '${actTaskParam.applyNo}'
		}, function(data) {
			if (data == "true") {
				$("#btnSave").removeAttr("disabled");
				$("#btnSubmit").removeAttr("disabled");
				top.$.jBox.tip.mess = null;
				var saveForm = $("#checkFeeForm").serializeJson();
				$.post("${ctx}/credit/checkFee/save?operate=" + $("#operate").val(), saveForm, function(data) {
					if (data) {
						if (data.status == 1) {//保存成功
							alertx(data.message);
							if('${actTaskParam.taskDefKey}' == 'utask_fgsfksh'){
								window.location.reload();
							}else{
								goToPage('${ctx}${actTaskParam.headUrl}','checkFeeSkipId');
							}
						} else {
							alertx(data.message);
						}
					}
				});
			} else if (data == "false") {
				alertx("进件参数发生错误,请联系管理员！");
			} else {
				alertx("进件正处于" + data + "状态，不可提交，请关闭窗口并刷新列表！");
			}
		});
	}

	function removeRequired() {
		var $isCollect = $("input[name='isCollect']:checked").val();
		if (0 == $isCollect) {
			$("#checkDate").removeClass("required");
			document.getElementById("checkDate").style.backgroundColor = "";
			$("#checkUserName").removeClass("required");
			document.getElementById("checkUserName").style.backgroundColor = "";
			$("#checkFee").removeClass("required");
			document.getElementById("checkFee").style.backgroundColor = "";
		}
	}
</script>
</head>
<body>
	<form:form id="checkFeeForm" modelAttribute="checkFee" action="${ctx}/credit/checkFee/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
		<input type="hidden" id="operate" name="operate" value="" />
		<a id="checkFeeSkipId" target="_parent"></a>
		<sys:message content="${message}" />
		<h3 class="infoListTitle">外访费登记</h3>
		<div class="filter">
			<pre class="textarea-width pre-style" id="pre"></pre>
			<div class="control-group">
				<label class="control-label">是否收取外访费：</label>
				<div class="controls">
					<form:radiobuttons path="isCollect" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" onchange="removeRequired();" />
					<font color="red">*</font>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">收取时间：</label>
				<div class="controls">
					<input id="checkDate" name="checkDate" type="text" readonly="true" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${checkFee.checkDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',maxDate:new Date(),isShowClear:false});" />
					<font color="red">*</font>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">收取人：</label>
				<div class="controls">
					<form:input path="checkUserName" htmlEscape="false" maxlength="20" class="input-xlarge required" />
					<font color="red">*</font>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">收取金额(元)：</label>
				<div class="controls">
					<td>
						<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" path="checkFee" htmlEscape="false" class="input-xlarge required" />
						<font color="red">*</font>
					</td>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">备注：</label>
				<div class="controls">
					<form:textarea path="description" htmlEscape="false" rows="4" maxlength="1000" class="textarea-width textarea-style " onkeyup="adjustTextareaLength('description','pre')" />
				</div>
			</div>
			<div class="form-actions">
				<shiro:hasPermission name="credit:checkFee:edit">
					<input id="btnSave" class="btn btn-primary" type="button" value="保 存" onclick="setOperate('save')" />&nbsp;
					<c:if test="${actTaskParam.taskDefKey ne 'utask_fgsfksh'}"> 
					<input id="btnSubmit" class="btn btn-primary" type="button" value="提交" onclick="setOperate('submit')" />
					</c:if>
				</shiro:hasPermission>
			</div>
		</div>
	</form:form>
</body>
</html>