<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>信审意见书资产清单管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	adjustTextareaLength("detailComment","pre");
// 	adjustTextareaLength("remarks","pre1");
		document.getElementById("marketValue").value = outputmoney("${creditAssets.marketValue}");
		$("#inputForm").validate({
		submitHandler : function(form) {
			var assetsOwnerName = $("#assetsOwnerId").find("option:selected").text();
			$("#assetsOwnerName").val(assetsOwnerName);
			var valueT = $("#marketValue").val().replace(/,/g, "");
			$("#marketValue").val(valueT);
			loading('正在提交，请稍等...');
			form.submit();
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
		$("#assetsOwnerId").empty();
		$("#assetsOwnerId").append("<option value=''>请选择</option>");
		var $assetsOwnerId = $("#s2id_assetsOwnerId>.select2-choice>.select2-chosen");
		$assetsOwnerId.html("请选择");
		$("#idNum").val(null);
		$.post("${ctx}/credit/creditAndLine/creditLineBank/findCustNameByRoleType", {
		roleType : $("#roleType").val(),
		applyNo : '${creditAssets.applyNo}'
		}, function(data) {
			$.each(data, function(i, val) {
				$("#assetsOwnerId").append("<option value='"+val["custId"]+"' label='"+val["custName"]+"'>" + val["custName"] + "</option>");
			});
		});
	}
</script>
<c:if test="${true == readOnly}">
	<script type="text/javascript">
		$(document).ready(function() {
			$("input[type!='button']").attr("readOnly", "readOnly");
			$("input[type='radio']").attr("disabled", "disabled");
			$("input[type='checkbox']").attr("disabled", "disabled");
			$("#detailComment").attr("disabled", "disabled");
// 			$("#remarks").attr("disabled", "disabled");
			disableSelect2();
			$("div[class='ribbon']").remove();
			$("div[class='searchButton']").remove();
			$("font[color='red']").remove();
			$(".Wdate").attr("onclick", "");
			$(".Wdate").removeClass("Wdate");
			$(".qdelete").remove();
			$("#btnSubmit").remove();
			$("#btnClose").attr("value", "关闭");
		});
	</script>
</c:if>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">资产清单信息</h3>
		<div class="searchCon">
			<form:form id="inputForm" modelAttribute="creditAssets" action="${ctx}/credit/creditViewBook/creditAssets/save" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="applyNo" value="${actTaskParam.applyNo}" />
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">资产名称：</td>
						<td class="ft_content">
							<form:input path="assetsName" htmlEscape="false" maxlength="300" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">权属人类型：</td>
						<td class="ft_content">
							<form:select path="roleType" id="roleType" class="input-medium required " onchange="findCustByRoleType();">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('ROLE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">权属人姓名：</td>
						<td class="ft_content">
							<form:select path="assetsOwnerId" id="assetsOwnerId" class="input-medium required ">
								<form:option value="" label="" />
								<form:options items="${custNameMap}" itemLabel="assetsOwnerName" itemValue="assetsOwnerId" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
							<form:hidden path="assetsOwnerName" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">市值：</td>
						<td class="ft_content">
							<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" path="marketValue" htmlEscape="false" maxlength="300" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">是否已抵押：</td>
						<td class="ft_content">
							<form:select path="isMortgage" id="isMortgage" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">是否外访：</td>
						<td class="ft_content">
							<form:select path="isCheck" id="isCheck" class="input-medium required">
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
							<form:textarea path="detailComment" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge textarea-style  textareaWidth required"  onkeyup="adjustTextareaLength('detailComment','pre')"  />
						
							<font color="red">*</font>
						</td>
					</tr>
					<%-- <tr>
						<td class="ft_label">备注：</td>
						<td class="ft_content" colspan="5">
						<pre class="input-xxlarge pre-style textareaWidth" id="pre1"></pre>
							<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge textarea-style textareaWidth required"  onkeyup="adjustTextareaLength('remarks','pre1')"  />
							
							<font color="red">*</font>
						</td>
					</tr> --%>
					<tr>
						<td colspan="5"></td>
						<td class="searchButton" style="text-align: right">
							<shiro:hasPermission name="credit:creditViewBook:edit">
								<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
							</shiro:hasPermission>
							<input id="btnClose" class="btn btn-primary" type="button" value="取 消" onclick="closeJBox();" />
							&nbsp;
						</td>

						<c:if test="${readOnly eq true }">
							<td><input id="btnCancel" class="btn-primary btn "
								type="button" value="关闭" onclick="closeJBox()" /></td>
						</c:if>

					</tr>
				</table>
					
			</form:form>
			<c:if test="${not empty closeWindow}">
				<script type="text/javascript">
					alertx('${saveMessage}', function() {
						/* parent.$("#searchForm").submit(); */
						parent.$.loadDiv("assetDiv", "${ctx }/credit/creditViewBook/creditAssets", {
							applyNo : "${creditAssets.applyNo}"
						}, "post");
						closeJBox();
					});
				</script>
			</c:if>
		</div>
	</div>
</body>
</html>