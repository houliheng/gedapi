<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>25日复核信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
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
		<li><a href="${ctx}/postloan/checkTwentyFiveInfo/">25日复核信息列表</a></li>
		<li class="active"><a href="${ctx}/postloan/checkTwentyFiveInfo/form?id=${checkTwentyFiveInfo.id}">25日复核信息<shiro:hasPermission name="postloan:checkTwentyFiveInfo:edit">${not empty checkTwentyFiveInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="postloan:checkTwentyFiveInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="checkTwentyFiveInfo" action="${ctx}/postloan/checkTwentyFiveInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">合同编号：</label>
			<div class="controls">
				<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">角色类型(主借人，共借人，担保人，主借企业，担保企业)',：</label>
			<div class="controls">
				<form:select path="roleType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">基本信息是否核查（0否1是）：</label>
			<div class="controls">
				<form:input path="checkBase" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网查信息是否核查（0否1是）：</label>
			<div class="controls">
				<form:input path="checkWeb" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">家庭状况信息是否核查（0否1是）：</label>
			<div class="controls">
				<form:input path="checkFamily" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">财务状况信息是否核查（0否1是）：</label>
			<div class="controls">
				<form:input path="checkFinancial" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借款用途信息是否核查：</label>
			<div class="controls">
				<form:input path="checkLoanPurpost" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行流水信息是否核查：</label>
			<div class="controls">
				<form:input path="checkBank" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业征信是否核查：</label>
			<div class="controls">
				<form:input path="checkCredit" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资产信息是否核查：</label>
			<div class="controls">
				<form:input path="checkProperty" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">抵质押品是否核查：</label>
			<div class="controls">
				<form:input path="checkMortgage" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经营情况是否核查：</label>
			<div class="controls">
				<form:input path="checkOperate" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否建档：</label>
			<div class="controls">
				<form:input path="checkArchive" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">流程是否合格：</label>
			<div class="controls">
				<form:input path="checkProc" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否有信访咨询费：</label>
			<div class="controls">
				<form:input path="checkGetFee" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否实地外访：</label>
			<div class="controls">
				<form:input path="checkGetAddress" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">核查是否执行签约条件：</label>
			<div class="controls">
				<form:input path="checkSign" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="postloan:checkTwentyFiveInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>