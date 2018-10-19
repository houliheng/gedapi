<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>编辑检查项目</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		 	adjustTextareaLength("description", "pre1");
		  	if(!checkIsNull('${mortgageItems.checkValue}')){
		  		if ("${mortgageItems.checkValue}"==0){
					 $("#checkValueNo").attr("checked", "checked");
				}
				if ("${mortgageItems.checkValue}"==1){
					 $("#checkValueYes").attr("checked", "checked");
				} 
			} 
			$("#inputForm").validate({
				submitHandler: function(form){
				loading();
				var param = $("#inputForm").serialize();
					$.post("${ctx}/postloan/mortgageEvaluateInfo/saveEdit", param, function(data) {
						closeTip();
						if (data.status == '1') {
							alertx("基本信息保存成功", function() {
							parent.$.loadDiv("mortgageEvaluateItemsList", "${ctx}/postloan/mortgageEvaluateInfo/editInfo", {
								applyNo : '${mortgageItems.applyNo}',infoId:'${mortgageItems.infoId}'
							}, "post");
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
	<form:form id="inputForm" modelAttribute="mortgageItems" action="${ctx}/postloan/mortgageEvaluateInfo/saveEdit" method="post" class="form-horizontal">
		<input type="hidden" id="id" name="id" value="${mortgageItems.id}"/>
		<input type="hidden" id="infoId" name="infoId" value="${mortgageItems.infoId}"/>
		<sys:message content="${message}"/>		
		<div class="searchInfo">
			<h3 class="searchTitle">基本信息</h3>
			<div class="searchCon">
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">申请编号：</td>
						<td class="ft_content">
							<input type="text" name="applyNo" id="applyNo" value="${mortgageItems.applyNo}" readonly="true" class="input-medium " />
						</td>
						<td class="ft_label">检查项目：</td>
						<td class="ft_content">
							<input type="text" name="checkItems" id="checkItems" style="width:380px"   value="${fns:getDictLabel(mortgageItems.checkItems, 'CHECK_ITEMS', '')}" readonly="true"  class="input-medium " />
						</td>
					</tr>
					<tr>
					
						<td class="ft_label" style="width: 10%;">检查结果：</td>
							<td class="ft_content" style="width: 20%;">
								<input type="radio" name="checkValue"  value="1" id="checkValueYes" class="required">
								<label for="radio_yes">是</label>
								&nbsp;&nbsp;
								<input type="radio" name="checkValue" value="0" id="checkValueNo"  class="required"  >
								<label for="radio_no">否</label>
								<font color="red">*</font>
								&nbsp;&nbsp;
							</td>
					</tr>
					<tr>
					<td class="ft_label">备注：</td>
						<td class="ft_content" colspan="5">
						<pre class="input-xxlarge pre-style textareaWidth" id="pre1"></pre>
							<textarea id="description" name="description" rows="4" cols="3" value="${mortgageItems.description}" maxlength="1000" class="area-xxlarge textarea-style " onkeyup="adjustTextareaLength('description','pre1')">${mortgageItems.description}</textarea>
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