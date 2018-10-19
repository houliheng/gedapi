<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同号变更</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#inputFormId").validate({
		submitHandler :saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});

	});

	function saveForm() {
		top.$.jBox.tip.mess = null;
		loading("更新中，请稍等");
		var paramJson = $("#inputFormId").serializeJson();
		$.post("${ctx}/credit/contract/updateContractNo", paramJson, function(data) {
			if (data) {
				closeTip();
				alertx(data.message, function() {
					parent.location.reload();
					closeJBox();
				});
			}
		});
	}
</script>
</head>
<body>
	<div class="searchInfo">
		<div class="searchCon">
			<form:form id="inputFormId" modelAttribute="contract" action="" method="post" class="form-horizontal">
				<h3 class="searchTitle">合同数据导入</h3>
				<form:hidden path="id"/>
				<sys:message content="${message}" />
				<div class="filter">
					<table class="table table-striped table-bordered table-condensed">
						<tr>
							<td class="ft_label" style="text-align: right;width:40%">申请编号：</td>
							<td class="ft_content" style="width:60%">
								<form:input path="applyNo" htmlEscape="false" readonly="true"  class="input-xlarge" />
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="text-align: right;">旧合同编号：</td>
							<td class="ft_content">
								<form:input path="contractNo" htmlEscape="false" readonly="true"  class="input-xlarge" />
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="text-align: right;">新合同编号：</td>
							<td class="ft_content">
								<form:input path="newContractNo" htmlEscape="false" class="input-xlarge required" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: center;">
								<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" />
							</td>
						</tr>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>
