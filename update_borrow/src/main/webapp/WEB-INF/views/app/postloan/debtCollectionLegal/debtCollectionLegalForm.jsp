<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>法务催收管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#inputForm").validate({
		submitHandler : function(form) {
			saveForm();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});

		if ('${readOnly}' == true) {
			setDivReadOnly('debtCollectionLegalDiv');
		}
	});

	function saveForm() {
		loading();
		var result = $("#legalResult").val();
		if (result == "2") {
			top.$.jBox.confirm("确定要将催收终止，并转入核销吗？", '系统提示', function(v, h, f) {
				if (v == 'ok') {
					top.$.jBox.tip.mess = null;
					var formJson = $("#inputForm").serializeJson();
					$.post("${ctx}/postloan/debtCollectionLegal/save", formJson, function(data) {
						if (data) {
							if (data.status == 1) {
								alertx(data.message, function() {
									if (data.legalResult == "1") {
										parent.$.loadDiv("debtCollectionLegalDiv", "${ctx }/postloan/debtCollectionLegal/toDebtCollectionLegal", {
											debtId : data.debtId
										}, "post");
										closeJBox("debtCollectionLegalForm");
									} else {
										closeAndReload();
									}
								});
							} else {
								alertx(data.message);
							}
						}
					});

				}
			})
		} else {
			top.$.jBox.tip.mess = null;
			var formJson = $("#inputForm").serializeJson();
			$.post("${ctx}/postloan/debtCollectionLegal/save", formJson, function(data) {
				if (data) {
					if (data.status == 1) {
						alertx(data.message, function() {
							parent.$.loadDiv("debtCollectionLegalDiv", "${ctx }/postloan/debtCollectionLegal/toDebtCollectionLegal", {
								debtId : data.debtId
							}, "post");
							closeJBox("debtCollectionLegalForm");
						});
					} else {
						alertx(data.message);
					}
				}
			});
		}
		closeTip();
	}
</script>
</head>
<body>
	<div id="debtCollectionLegalDiv" class="wrapper">
		<form:form id="inputForm" modelAttribute="debtCollectionLegal" action="${ctx}/postloan/debtCollectionLegal/save" method="post" class="form-horizontal">
			<table>
				<form:hidden path="id" />
				<form:hidden path="contractNo" />
				<form:hidden path="debtId" />
				<tr>
					<td class="ft_label">立案时间：</td>
					<td class="ft_content">
						<input id="collectionDate" name="collectionDate" type="text" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${debtCollectionLegal.collectionDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',maxDate:new Date(),isShowClear:false});" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">立案类型：</td>
					<td class="ft_content">
						<form:select path="legalType" class="input-medium required">
							<form:option value="" label="请选择" />
							<form:options items="${fns:getDictList('LEGAL_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">立案人：</td>
					<td class="ft_content">
						<form:hidden path="collector" />
						<form:input path="collectorName" htmlEscape="false" maxlength="32" class="input-medium required" style="margin-bottom: 3px" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">立案机构：</td>
					<td class="ft_content">
						<form:select path="legalOrg" class="input-medium required">
							<form:option value="" label="请选择" />
							<form:options items="${fns:getDictList('LEGAL_ORG')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">处理状态：</td>
					<td class="ft_content">
						<form:select path="legalStatus" class="input-medium required">
							<form:option value="" label="请选择" />
							<form:options items="${fns:getDictList('LEGAL_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">催收结果：</td>
					<td class="ft_content">
						<form:select path="legalResult" class="input-medium required">
							<form:option value="" label="请选择" />
							<form:options items="${fns:getDictList('LEGAL_RESULT')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">登记人：</td>
					<td class="ft_content">
						<form:input path="register" htmlEscape="false" readonly="true" maxlength="20" class="input-medium required" style="margin-bottom: 3px" />
					</td>
					<td class="ft_label">登记时间：</td>
					<td>
						<input id="registrationTime" name="registrationTime" type="text" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value='${debtCollectionLegal.registrationTime}' pattern='yyyy-MM-dd'/>" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">处理详情：</td>
					<td class="ft_content" colspan="5">
						<form:textarea path="description" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge required" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="searchButton" colspan="6">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
						&nbsp;
						<input id="btnCancel" class="btn btn-primary " type="button" value="取消" onclick="closeJBox()" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>