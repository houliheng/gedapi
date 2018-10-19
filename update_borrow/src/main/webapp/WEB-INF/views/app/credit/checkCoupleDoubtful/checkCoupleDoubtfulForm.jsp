<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>两人外访管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#inputForm").validate({
		submitHandler : function(form) {
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
		adjustTextareaLength("notice","preNotice");
		adjustTextareaLength("description","preDescription");
	});

	function beforeSubmit() {
		$("#roleType").removeAttr("disabled");
	}
</script>
<c:if test="${true == readOnly}">
	<script type="text/javascript">
		$(document).ready(function() {
			$(document).ready(function() {
				$("input[type!='button']").attr("readOnly", "readOnly");
				$("input[type='radio']").attr("disabled", "disabled");
				$("input[type='checkbox']").attr("disabled", "disabled");
				$("#description").attr("disabled", "disabled");
				$("#description1").attr("disabled", "disabled");
				disableSelect2();
				$("div[class='ribbon']").remove();
				$("div[class='searchButton']").remove();
				$("font[color='red']").remove();
				$(".Wdate").attr("onclick", "");
				$(".Wdate").removeClass("Wdate");
				$(".qdelete").remove();
				$("#btnSubmit").remove();
				$("#btnCancel").attr("value", "关闭");
			});
		});
	</script>
</c:if>
</head>
<body>
	<form:form id="inputForm" modelAttribute="checkCoupleDoubtful" action="${ctx}/credit/checkCoupleDoubtful/save" method="post" class="form-horizontal">
		<form:hidden path="applyNo" value="${applyNo}" />
		<form:hidden path="custId" value="${custId}" />
		<sys:message content="${message}" />
		<table class="filter">
			<tr>
				<td class="ft_label">电核网查外访，注意事项：</td>
				<td class="ft_content" colspan="5">
				    <pre class="input-xxlarge textarea-width pre-style"  id="preNotice"></pre>
					<textarea id="notice" rows="5" cols="300" class="input-xxlarge textarea-width textarea-style" maxlength="1000"  readonly="true">${notice}</textarea>
				</td>
			</tr>
			<tr>
				<td class="ft_label">外访对象：</td>
				<td class="ft_content">
					<form:select path="roleType" id="roleType" class="input-medium " disabled="true">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('ROLE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<td class="ft_label">姓名：</td>
				<td class="ft_content">
					<form:input path="custName" htmlEscape="false" maxlength="20" class="input-medium " readonly="true" />
				</td>
				<%-- <td class="ft_label">是否收取外访费：</td>
				<td class="ft_content">
					<form:select path="isCheckFee" class="input-medium required">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					<font color="red">*</font>
				</td> --%>
			</tr>
			<tr>
				<td class="ft_label">外访类型：</td>
				<td class="ft_content">
					<form:select path="checkType" class="input-medium required">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('CHECK_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					<font color="red">*</font>
				</td>
				<td class="ft_label">外访时间：</td>
				<td class="ft_content">
					<input id="checkDate" name="checkDate" type="text" readonly="true" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${checkCoupleDoubtful.checkDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date()})" />
					<font color="red">*</font>
				</td>
				<td class="ft_label">外访人岗位：</td>
				<td class="ft_content">
					<form:select path="checkUserDept" class="input-medium required">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('CHECK_USER_DEPT')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">外访地点：</td>
				<td class="ft_content" colspan="3">
					<form:input path="checkAddress" htmlEscape="false" maxlength="300" class="input-medium required" style="width:400px" />
					<font color="red">*</font>
				</td>
				<td class="ft_label">公里数：</td>
				<td class="ft_content">
					<form:input path="checkKm" htmlEscape="false" maxlength="15" class="input-medium number required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">外访意见：</td>
				<td class="ft_content" colspan="5">
				    <pre class="textarea-width pre-style"  id="preDescription"></pre>
					<form:textarea path="description" id="description" htmlEscape="false" rows="4" maxlength="1000"  class="textarea-style textarea-width required" onkeyup="adjustTextareaLength('description','preDescription')"/>
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="searchButton" colspan="6">
					<shiro:hasPermission name="credit:checkCoupleDoubtful:edit">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="beforeSubmit()" />&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="取消" onclick="closeJBox()" />
				</td>
				<c:if test="${readOnly eq true }">
						<td>
							<input id="btnCancel" class="btn-primary btn " type="button"
							value="关闭" onclick="closeJBox()" />
						</td>
					</c:if>
			</tr>
				
		</table>
	</form:form>
	<c:if test="${not empty closeWindow}">
		<script type="text/javascript">
			alertx('${saveMessage}', function() {
				parent.location.reload();
				closeJBox();
			});
		</script>
	</c:if>
</body>
</html>