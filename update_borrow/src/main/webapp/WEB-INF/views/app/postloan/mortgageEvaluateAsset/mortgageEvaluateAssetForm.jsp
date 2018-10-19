<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>编辑基本信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
				var param = $("#inputForm").serialize();
					$.post("${ctx}/postloan/mortgageEvaluateAsset/save", param, function(data) {
						if (data.status == '1') {
							alertx("基本信息保存成功", function() {
						//	parent.$.loadDiv('editAseet', '${ctx}/postloan/mortgageEvaluateAsset',null, 'post');
							parent.location.href="${ctx}/postloan/mortgageEvaluateAsset/list";
								parent.location.reload();
								closeJBox();
							});
						}else{
							alertx("基本信息保存失败");
						}
					});
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					checkReq(error,element);
				}
			});
		});
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="mortgage" action="${ctx}/postloan/mortgageEvaluateAsset/save" method="post" class="form-horizontal">
		<input type="hidden" id="id" name="id" value="${mortgage.id}"/>
		<sys:message content="${message}"/>		
		<div class="searchInfo">
			<h3 class="searchTitle">基本信息</h3>
			<div class="searchCon">
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">主合同号：</td>
						<td class="ft_content">
							<input type="text" name="contractNo" id="contractNo" value="${mortgage.contractNo}" readonly="true" class="input-medium " />
						</td>
						<td class="ft_label">被评估资产：</td>
						<td class="ft_content">
							<input type="text" name="assetName" id="assetName" readonly="readonly" value="${mortgage.assetName}"  class="input-medium " />
						</td>
					</tr>
					<tr>
						<td class="ft_label">评估基准日期</td>
						<td class="ft_content">
							<input name="mortgageDate" id="mortgageDate" type="text"  maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${mortgage.mortgageDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({ onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',isShowClear:false});" />
							<font color="red">*</font>
						</td>
							<td class="ft_label">评估人员：</td>
						<td class="ft_content">
							<input type="text" name="mortgageUserId" id="mortgageUserId" value="${mortgage.mortgageUserId}"  class="input-medium " />
						</td>
					</tr>
					<tr>
						<td class="searchButton" colspan="6">
					<shiro:hasPermission name="credit:checkCoupleDoubtful:edit">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"  />&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn btn-primary" type="button" value="取消" onclick="closeJBox()" />
				</td>
				</tr>
				</table>
			</div>
		</div>
	</form:form>
</body>
</html>