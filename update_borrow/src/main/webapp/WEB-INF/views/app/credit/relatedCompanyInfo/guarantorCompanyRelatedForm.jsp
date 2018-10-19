<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>关联企业管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#guaranCompanyRelatedForm").validate({
		submitHandler : function(form) {
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">担保企业关联企业信息</h3>
		<br>
		<div class="searchCon">
			<form:form id="guaranCompanyRelatedForm" modelAttribute="companyRelated" action="${ctx}/credit/guarantorCompanyRelated/save?applyNo=${applyNo}" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<sys:message content="${message}" />
				<div class="control-group">
					<label class="control-label">担保企业名称：</label>
					<div class="controls">
						<form:select path="companyId" class="input-xlarge required">
							<form:option value="" label="" />
							<form:options items="${companyInfoList}" itemLabel="busiRegName" itemValue="id" htmlEscape="false" />
						</form:select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">关联企业名称：</label>
					<div class="controls">
						<form:input path="relatedCompanyName" htmlEscape="false" maxlength="300" class="input-xlarge required" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">组织机构代码：</label>
					<div class="controls">
						<form:input path="relatedCompanyOrg" htmlEscape="false" maxlength="50" class="input-xlarge required" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">与担保企业关系：</label>
					<div class="controls">
						<form:input path="relatedCompanyType" htmlEscape="false" maxlength="50" class="input-xlarge required" />
					</div>
				</div>
				<div class="searchButton">
					<shiro:hasPermission name="credit:guarantorInfo:edit">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
				</shiro:hasPermission>
					<input id="btnCancel" class="btn btn-primary" type="button" value="关闭" onclick="closeJBox()" />
				</div>
			</form:form>
		</div>
	</div>
	<c:if test="${not empty close }">
		<script type="text/javascript">
			parent.$.loadDiv('guarantorCompanyRelatedListDiv', '${ctx}/credit/guarantorCompanyRelated/list', {
				applyNo : '${applyNo}'
			}, 'post');
			closeTip();
			closeJBox();
		</script>
	</c:if>
</body>
</html>