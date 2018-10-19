<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同模板添加</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#addnewForm").validate({
		submitHandler : function(form) {
			loading('正在提交，请稍等...');
			form.submit();
			window.close();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
		});
	});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li>
			<a href="${ctx}/credit/contractTemplate">合同模板管理列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/credit/contractTemplate/Form">合同模板添加</a>
		</li>
	</ul>
	<sys:message content="${message}" />
	<div class="searchInfo">
		<h3 class="searchTitle">合同模板信息</h3>
		<div class="searchCon">
			<form:form id="addnewForm" modelAttribute="contractTemplate" action="${ctx}/credit/contractTemplate/addnewForm" method="post">
				<div class="filter">
					<table class="fromTable">
						<tr>
							<td class="ft_label">模板类型：</td>
							<td class="ft_content" nowrap>
								<form:select id="templateType" name="templateType" cssClass="required input-medium" cssStyle="width: 136px;" path="templateType" data-options="required:true">
									<form:option value="" label="请选择" />
									<form:options items="${fns:getDictList('CONTRACT_TEMPLATE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								<font color="red">*</font>
							</td>
							<td class="ft_label">归属地：</td>
							<td class="ft_content" nowrap>
								<form:select id="orgplatform" name="orgplatform" path="orgplatform" cssClass="required input-medium" data-options="required:true" cssStyle="width: 136px;">
									<form:option value="" label="请选择" />
									<form:options items="${fns:getDictList('ORG_PLATFORM')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">模板名称：</td>
							<td class="ft_content" nowrap>
								<input name="templateName" id="templateName" type="text" maxlength="50" class="input-medium required" style="width: 136px;" />
								<font color="red">*</font>
							</td>
<%-- 							<td class="ft_label">产品类型：</td>
							<td class="ft_content" nowrap>
								<form:select id="productType" name="productType" path="productType" cssClass="required input-medium" data-options="required:true" cssStyle="width: 150px;">
									<form:option value="" label="请选择" />
									<form:options items="${fns:getDictList('PRODUCT_TYPE_CONTRACT')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								<font color="red">*</font>
							</td> --%>
						</tr>
					</table>
					<div class="searchButton">
						<input id="btnSave" class="btn btn-primary" type="submit" value="保存" />
						&nbsp;
						<input id="btnClose" class="btn btn-primary" type="button" value="返回" onclick="history.go(-1)" />
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>