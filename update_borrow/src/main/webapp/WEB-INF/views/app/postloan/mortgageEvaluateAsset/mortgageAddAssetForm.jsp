<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>信审意见书资产清单管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	//findCustByRoleType();
 	 var min = $("select:eq(1)");
	min.empty();
	var custNameValue = "${mortgageEvaluateAsset.custName}";
	min.append("<option value="+"${mortgageEvaluateAsset.custId}"+">"+custNameValue+"</option>");
	$("#s2id_custId .select2-chosen").html("${mortgageEvaluateAsset.custName}");  
	window.contractNoRe = "${mortgageEvaluateAsset.applyNo}";
	adjustTextareaLength("detailComment","pre");
	adjustTextareaLength("remarks","pre1");
		$("#inputFormAsset").validate({
		submitHandler : function(form) {
	 		var valueT1 = $("#bookValue").val().replace(/,/g, "");
			var valueT2 = $("#evaluatePrice").val().replace(/,/g, "");
			var valueT3 = $("#marketValue").val().replace(/,/g, "");
			var valueT4 = $("#moreOrLessValue").val().replace(/,/g, "");
			$("#bookValue").val(valueT1);
			$("#evaluatePrice").val(valueT2);
			$("#marketValue").val(valueT3);
			$("#moreOrLessValue").val(valueT4); 
			if (${readOnlyAsset}==false){
			$("#custName").val($("#custId").find("option:selected").text()); 
			}
			loading();
			var param = $("#inputFormAsset").serialize();
					$.post("${ctx}/postloan/mortgageEvaluateAsset/saveAsset", param, function(data) {
						closeTip();
						if (data.status == '1') {
							alertx(data.message, function() {
								parent.location.href="${ctx}/postloan/mortgageEvaluateAsset/addAsset?applyNo="+contractNoRe;
								this.close();
							});
						}else{
							alertx(data.message);
						}
					});
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
				error.appendTo(element.parent().parent());
			} else {
				checkReq(error, element);
			}
		}
		});
	});
		//根绝角色类型查询人员姓名List
	function findCustByRoleType() {
		$("#custId").empty();
		$("#custId").append("<option value=''>请选择</option>");
		var $assetsOwnerId = $("#s2id_assetsOwnerId>.select2-choice>.select2-chosen");
		$assetsOwnerId.html("请选择");
		$.post("${ctx}/postloan/mortgageEvaluateAsset/findCustNameByRoleType", {
		roleType : $("#roleType").val(),
		contractNo : '${mortgageEvaluateAsset.contractNo}'
		}, function(data) {
			$.each(data, function(i, val) {
				$("#custId").append("<option value='"+val["custId"]+"' label='"+val["custName"]+"'>" 
				+ val["custName"] + "</option>");
			});
		});
	}
	
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">资产清单信息</h3>
		<div class="searchCon">
	 	<form:form id="inputFormAsset" modelAttribute="mortgageEvaluateAsset" action="${ctx}/postloan/mortgageEvaluateAsset/saveAsset" method="post" class="form-horizontal">
				<form:hidden path="applyNo" value="${mortgageEvaluateAsset.applyNo}" />
				<form:hidden path="contractNo" value="${mortgageEvaluateAsset.contractNo}" />
				<form:hidden path="id" value="${mortgageEvaluateAsset.id}" />
				<form:hidden path="isPushData" value="${mortgageEvaluateAsset.isPushData}" />
				 <c:if test="${readOnlyAsset eq true }">
						<td>
							<form:hidden path="custId" value="${mortgageEvaluateAsset.custId}" />
							<form:hidden path="roleType" value="${mortgageEvaluateAsset.roleType}" />
							<form:hidden path="custName" value="${mortgageEvaluateAsset.custName}" />
							<form:hidden path="isMortgage" value="${mortgageEvaluateAsset.isMortgage}" />
							<form:hidden path="isCheck" value="${mortgageEvaluateAsset.isCheck}" />
						</td>
					</c:if>
						<c:if test="${readOnlyAsset eq false }">
						<td>
							 <form:hidden path="custName"  /> 
						</td>
					</c:if> 
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">资产名称：</td>
						<td class="ft_content">
							<form:input path="assetName" readonly="${readOnlyAsset}" htmlEscape="false" maxlength="30" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">账面价值：</td>
						<td class="ft_content">
							<form:input path="bookValue" readonly="${readOnlyAsset}" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" maxlength="18" class="input-medium required" />
							<font color="red">*</font>
						</td>
						
						</tr>
					<tr>
						<td class="ft_label">权属人类型：</td>
						<td class="ft_content">
							<form:select path="roleType" id="roleType" disabled="${readOnlyAsset}" class="input-medium required " onchange="findCustByRoleType();">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('ROLE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
					  <td class="ft_label">权属人姓名：</td>
						<td class="ft_content">
							<form:select path="custId" disabled="${readOnlyAsset}"  class="input-medium required ">
								<form:option value="" label="" />
								<form:options items="${custNameMap}" itemLabel="assetsOwnerName" itemValue="assetsOwnerId" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td> 
						
					</tr>
					<tr>
						<td class="ft_label">市值：</td>
						<td class="ft_content">
							<form:input onkeyup="keyPress11(this);" readonly="${readOnlyAsset}" onblur="this.value=outputmoney(this.value);" path="marketValue" htmlEscape="false" maxlength="18" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">是否已抵押：</td>
						<td class="ft_content">
							<form:select path="isMortgage" disabled="${readOnlyAsset}"  class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">是否外访：</td>
						<td class="ft_content">
							<form:select path="isCheck" disabled="${readOnlyAsset}" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						
					</tr>
					<tr>
						<td class="ft_label">详细情况：</td>
						<td class="ft_content" colspan="5">
						<pre class="input-xxlarge pre-style textareaWidth " id="pre"></pre>
							<form:textarea path="detailComment" readonly="${readOnlyAsset}" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge textarea-style  textareaWidth required"  onkeyup="adjustTextareaLength('detailComment','pre')"  />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">备注：</td>
						<td class="ft_content" colspan="5">
						<pre class="input-xxlarge pre-style textareaWidth" id="pre1"></pre>
							<form:textarea path="remarks" readonly="${readOnlyAsset}" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge textarea-style textareaWidth required"  onkeyup="adjustTextareaLength('remarks','pre1')"  />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">评估价值(元)：</td>
						<td class="ft_content">
							<form:input path="evaluatePrice" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" maxlength="18" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">增减值(元)：</td>
						<td class="ft_content">
							<form:input path="moreOrLessValue" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" maxlength="18" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">增值率(%)：</td>
						<td class="ft_content">
							<form:input path="moreOrLessRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" htmlEscape="false" maxlength="5" class="input-medium required" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="5"></td>
						<td class="searchButton" style="text-align: right">
							<shiro:hasPermission name="credit:creditViewBook:edit">
								<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
							</shiro:hasPermission>
							<input id="btnClose" class="btn btn-primary" type="button" value="取消" onclick="closeJBox();" />
							&nbsp;
						</td>

						<c:if test="${readOnly eq true }">
							<td><input id="btnCancel" class="btn-primary btn "
								type="button" value="关闭" onclick="closeJBox()" /></td>
						</c:if>

					</tr>
				</table>
			</form:form>
		</div>
	</div>
</body>
</html>