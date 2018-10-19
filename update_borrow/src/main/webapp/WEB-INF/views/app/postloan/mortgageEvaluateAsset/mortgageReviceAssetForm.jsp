<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>备注修改</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			var applyRe = "${mortgage.applyNo}";
			$("#inputForm").validate({
				submitHandler: function(form){
				loading();
				var param = $("#inputForm").serialize();
					$.post("${ctx}/postloan/mortgageEvaluateAsset/saveRemark", param, function(dataRem) {
						closeTip();
						if (dataRem.status == "1") {
						alertx("基本信息保存成功", function() {
						parent.location.href="${ctx}/postloan/mortgageEvaluateAsset/addAsset?applyNo="+applyRe;
						closeJBox();
							}); 
						}else{
							alertx("基本信息保存失败");
						}
					});
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
	<form:form id="inputForm" modelAttribute="mortgage" action="${ctx}/postloan/mortgageEvaluateAsset/saveRemark" method="post" class="form-horizontal">
	<input type="hidden" id="id" name="id" value="${mortgage.id}"/>
		<sys:message content="${message}"/>
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">备注：</td>
						<td class="ft_content" colspan="5">
						<pre class="input-xxlarge pre-style textareaWidth" id="pre1"></pre>
							<textarea id="remarks" name="remarks" rows="4" cols="3" maxlength="1000" class="area-xxlarge textarea-style required" onkeyup="adjustTextareaLength('remarks','pre1')">${mortgage.remarks}</textarea>
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
		
	</form:form>
</body>
</html>