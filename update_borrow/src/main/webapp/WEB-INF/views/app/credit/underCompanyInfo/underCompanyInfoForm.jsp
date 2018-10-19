<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>线下借款-企业信息管理</title>
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
		<li><a href="${ctx}/credit/underCompanyInfo/">线下借款-企业信息列表</a></li>
		<li class="active"><a href="${ctx}/credit/underCompanyInfo/form?id=${underCompanyInfo.id}">线下借款-企业信息<shiro:hasPermission name="credit:underCompanyInfo:edit">${not empty underCompanyInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="credit:underCompanyInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="underCompanyInfo" action="${ctx}/credit/underCompanyInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">申请编号：</label>
			<div class="controls">
				<form:input path="applyNo" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">流程实例ID：</label>
			<div class="controls">
				<form:input path="procInstId" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借款状态：</label>
			<div class="controls">
				<form:input path="loanStatus" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业工商登记名称：</label>
			<div class="controls">
				<form:input path="busiRegName" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">统一社会信用代码：</label>
			<div class="controls">
				<form:input path="unSocCreditNo" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业执照注册号：</label>
			<div class="controls">
				<form:input path="busiLicRegNo" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实缴资本(单位：元)：</label>
			<div class="controls">
				<form:input path="paidInCapital" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">register_capital：</label>
			<div class="controls">
				<form:input path="registerCapital" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">成立时间：</label>
			<div class="controls">
				<input name="companyStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${underCompanyInfo.companyStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人名称：</label>
			<div class="controls">
				<form:input path="corporationName" htmlEscape="false" maxlength="300" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人证件号：</label>
			<div class="controls">
				<form:input path="corporationCardIdNo" htmlEscape="false" maxlength="18" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经营区域：</label>
			<div class="controls">
				<form:input path="regDistinct" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法定代表人行业-门类(行业表-levle1)：</label>
			<div class="controls">
				<form:input path="categoryMain" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法定代表人行业-大类(行业表-levle2)：</label>
			<div class="controls">
				<form:input path="categoryLarge" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法定代表人行业-中类(行业表-levle3)：</label>
			<div class="controls">
				<form:input path="categoryMedium" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法定代表人行业-小类(行业表-levle4)：</label>
			<div class="controls">
				<form:input path="categorySmall" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法定代表人地址：省：</label>
			<div class="controls">
				<form:input path="operateProvince" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法定代表人地址：市：</label>
			<div class="controls">
				<form:input path="operateCity" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法定代表人地址：区：</label>
			<div class="controls">
				<form:input path="operateDistinct" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法定代表人地址详细：</label>
			<div class="controls">
				<form:input path="operateDetails" htmlEscape="false" maxlength="300" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册地址：省：</label>
			<div class="controls">
				<form:input path="registerProvince" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册地址：市：</label>
			<div class="controls">
				<form:input path="registerCity" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册地址：区：</label>
			<div class="controls">
				<form:input path="registerDistinct" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册地址详细：</label>
			<div class="controls">
				<form:input path="registerDetails" htmlEscape="false" maxlength="300" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">办公地址：省：</label>
			<div class="controls">
				<form:input path="officeProvince" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">办公地址：市：</label>
			<div class="controls">
				<form:input path="officeCity" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">办公地址：区：</label>
			<div class="controls">
				<form:input path="officeDistinct" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">办公地址详细：</label>
			<div class="controls">
				<form:input path="officeDetails" htmlEscape="false" maxlength="300" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业信息：</label>
			<div class="controls">
				<form:input path="companyInfo" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业产品介绍：</label>
			<div class="controls">
				<form:input path="companyProductInfo" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">股东信息：</label>
			<div class="controls">
				<form:input path="stockInfo" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业法人征信：</label>
			<div class="controls">
				<form:input path="creditCorporation" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其它平台借款情况：</label>
			<div class="controls">
				<form:input path="otherLoanplatInfo" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否有贷款记录：</label>
			<div class="controls">
				<form:input path="isLoan" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">共几笔贷款记录：</label>
			<div class="controls">
				<form:input path="loanRecode" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">当前是否有逾期：</label>
			<div class="controls">
				<form:input path="isOverDue" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">企业征信记录等级：</label>
			<div class="controls">
				<form:input path="sourceOfCreregist" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分公司名称：</label>
			<div class="controls">
				<form:input path="gqCompanyName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属大区：</label>
			<div class="controls">
				<form:input path="gqAreaName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">债权所属公司：</label>
			<div class="controls">
				<form:input path="loanBeCompany" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="credit:underCompanyInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>