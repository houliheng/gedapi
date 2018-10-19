<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>补录流水</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#inputForm").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});
	function saveForm() {
		var streamNo = $("#streamNo").val();
		loading();
		$.ajax({
		url : "${ctx}/accounting/deductResult/validateStreamNo",
		type : "post",
		data:{
			streamNo:streamNo
		},
		async : false,
		dataType : "JSON",
		success : function(data) {
			closeTip();
			if (data.status == 1) {
				top.$.jBox.tip.mess = null;
				var formJson = $("#inputForm").serializeJson();
				$.post("${ctx}/accounting/deductResult/save", formJson, function(data) {
					if (data) {
						if (data.status == 1) {//保存成功
							alertx(data.message, function() {
								parent.$.loadDiv("deductResultId", "${ctx}/accounting/deductResult",{contractNo:'${deductResult.contractNo}'} ,"post");
								closeJBox();
							});
						} else {
							alertx(data.message);
						}
					}
				});
			} else {
				alertx(data.message);
			}
		}
		});
	}
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="deductResult" action="" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" name="contractNo" value="${deductResult.contractNo}" />
		<input type="hidden" name="flag" value="${flag}" />
		<sys:message content="${message}" />
		<div class="searchInfo" id="">
			<h3 class="searchTitle">补录流水</h3>
			<div class="searchCon">
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">流水号：</td>
						<td class="ft_content">
							<form:input path="streamNo" htmlEscape="false" class="input-medium required " />
							<font style="color: red">*</font>
						</td>
						<td class="ft_label">还款金额：</td>
						<td class="ft_content">
							<form:input path="deductAmount" htmlEscape="false" class="input-medium number required" />
							<font style="color: red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">操作人员代码：</td>
						<td class="ft_content">
							<form:input path="deductUserName" htmlEscape="false" readonly="true" class="input-medium" />
							<form:hidden path="deductUserId"/>
						</td>
						<td class="ft_label">入账时间：</td>
						<td class="ft_content">
							<input name="entryTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${deductResult.entryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
							<font style="color: red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label" style="width: 10%;">备注：</td>
						<td class="ft_content" colspan="3" style="width: 70%;">
							<form:textarea path="description" htmlEscape="false" rows="5" cols="3" maxlength="1000" class="area-length required" style="width: 90%;" />
							<font style="color: red">*</font>
						</td>
					</tr>
					<shiro:hasPermission name="accounting:deductResult:edit">
						<tr>
							<td colspan="4" style="text-align: right;">
								<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
							</td>
						</tr>
					</shiro:hasPermission>
				</table>
			</div>
		</div>
	</form:form>
</body>
</html>