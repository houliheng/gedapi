<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					
					var param = $("#inputForm").serialize();
					$.post("${ctx}/credit/gqgetAssetHouse/save", param, function(data) {
						if (data) {
							closeTip();
							if (data.status == "1") {
								alertx("新增房屋资产保存成功", function() {
									parent.$.loadDiv('assetHouse', '${ctx}/credit/gqgetAssetHouse/list', {
										applyNo:$("#applyNo").val()
									}, 'post');
									closeJBox();
								});
							} else {
								alertx("新增房屋资产保存失败");
							}
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
		<h3 class="searchTitle">房产信息</h3>
	<br/>
	<form:form id="inputForm" modelAttribute="gqgetAssetHouse" action="${ctx}/credit/gqgetAssetHouse/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>		
		<form:hidden path="id" />
		<form:hidden path="applyNo" />
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<form:input path="contDetails" htmlEscape="false" maxlength="500" class="input-xlarge required"/>
				<font style="color: red">*</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">面积：</label>
			<div class="controls">
				<form:input path="buildingArea" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<font style="color: red">*</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">估值：</label>
			<div class="controls">
				<form:input path="evaluatePrice" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<font style="color: red">*</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市值：</label>
			<div class="controls">
				<form:input path="marketPrice" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<font style="color: red">*</font>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="credit:gqgetComInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>