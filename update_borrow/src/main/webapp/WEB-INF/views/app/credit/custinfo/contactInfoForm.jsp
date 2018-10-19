<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#contactForm").validate({
		submitHandler : function(form) {
			loading();
			var id1 = $("#id").val();
			var contactMobile = $("#contactMobile").val();
			$.ajax({
			type : "post",
			data : {
			id : id1,
			mobileNum : contactMobile,
			applyNo : '${applyNo}'
			},
			url : "${ctx}/custinfo/custInfo/validateIdNum",
			dataType : "json",
			success : function(data) {
				if (data.status == "1") {
					var param = $("#contactForm").serialize();
					if (beforeSubmit) {
						$("#btnSubmit").attr("disabled", "true");
						$.post("${ctx}/credit/contactInfo/save", param, function(data) {
							if (data) {
								closeTip();
								if (data.status == "1") {
									alertx("新增联系人保存成功", function() {
										parent.$.loadDiv('contactCustInfo', '${ctx}/credit/contactInfo', {
											relationId : $("#relationId").val(),
											applyNo:$("#applyNo").val()
										}, 'post');
										closeJBox();
									});
								} else {
									alertx("新增联系人保存失败");
									$("#btnSubmit").removeAttr("disabled");
								}
							}
						});
					}

				} else {
					closeTip();
					alertx(data.message);
				}
			}
			});
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
		if ('${readOnly}' == 'true') {
			setPageReadOnly();
		}
	});

	function beforeSubmit() {
		$("#btnSubmit").attr("disabled", "true");
		return true;
	}
</script>
</head>
<body>
	<div>
		<sys:message content="${message}" />
		<h3 class="searchTitle">联系人信息</h3>
		<div class="searchCon">
			<form:form id="contactForm" modelAttribute="contactInfo" action="${ctx}/credit/contactInfo/save" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="relationId" />
				<input type="hidden" id="applyNo" name="applyNo" value="${applyNo}" />
				<div class="filter">
					<table class="fromTable">
						<tr>
							<td class="ft_label">姓名：</td>
							<td class="ft_content" nowrap>
								<form:input path="contactName" htmlEscape="false" maxlength="20" class="input-medium required" />
								<font color="red">*</font>
							</td>
							<td class="ft_label">性别：</td>
							<td class="ft_content">
								<form:select path="sexNo" class="input-medium required">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								<font color="red">*</font>
							</td>
							<td class="ft_label" nowrap>是否知晓本次贷款：</td>
							<td class="ft_content">
								<form:select path="isKnow" class="input-medium required">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">与借款人关系：</td>
							<td class="ft_content">
								<form:select path="contactRelations" class="input-medium required">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('CONTACT_RELATIONS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								<font color="red">*</font>
							</td>
							<td class="ft_label">移动电话：</td>
							<td class="ft_content">
								<form:input path="contactMobile" style="width: 150px;" htmlEscape="false" maxlength="11" class="input-medium mobile required" />
								<font color="red">*</font>
							</td>
							<td class="ft_label">住宅电话：</td>
							<td class="ft_content">
								<form:input path="housePhoneNo" htmlEscape="false" maxlength="12" class="input-medium simplePhone" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">家庭地址：</td>
							<td class="ft_content" colspan="4">
								<form:input path="houseAddressDetails" maxlength="300" class="input-large required" style="width:90%" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">单位名称：</td>
							<td class="ft_content" colspan="4">
								<form:input path="deptName" style="width:90%;" htmlEscape="false" maxlength="300" class="input-medium required" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">单位地址：</td>
							<td class="ft_content" colspan="4">
								<form:input path="deptAddressDetails" maxlength="300" class="input-medium required" style="width:90%;" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">部门名称：</td>
							<td class="ft_content">
								<form:input path="department" htmlEscape="false" maxlength="30" class="input-medium" />
							</td>
							<td class="ft_label">职位名称：</td>
							<td class="ft_content">
								<form:input path="postName" htmlEscape="false" maxlength="30" class="input-medium" />
							</td>
						</tr>
					</table>
					<c:if test="${!(readOnly eq true) }">
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
							<input id="btnCancel" class="btn btn-primary" type="button" value="关闭" onclick="closeJBox()" />
						</div>
					</c:if>
					<c:if test="${readOnly eq true }">
						<div style="text-align: right;">
							<input id="btnCancel" class="btn-primary btn " type="button" value="关闭" onclick="closeJBox()" />
						</div>
					</c:if>
				</div>
			</form:form>
		</div>
	</div>
	<c:if test="${not empty close }">
		<script type="text/javascript">
			parent.$.loadDiv('contactCustInfo', '${ctx}/credit/contactInfo', {
			relationId : '${contactInfo.relationId}',
			applyNo : '${applyNo}'
			}, 'post');
			closeJBox();
		</script>
	</c:if>
	<script type="text/javascript">
		
	</script>
</body>
</html>