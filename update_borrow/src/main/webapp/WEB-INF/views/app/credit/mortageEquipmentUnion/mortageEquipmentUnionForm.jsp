<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>抵押设备管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#equipForm").validate({
		submitHandler : function(form) {
			loading();
			var param = $("#equipForm").serialize();
			$.post("${ctx}/credit/mortageEquipmentUnion/save", param, function(data) {
				if (data) {
					closeTip();
					if (data.status == "1") {
						alertx("新增抵押设备保存成功", function() {
							parent.$.loadDiv('equip', '${ctx}/credit/gqgetComInfoUnion/mortEquip', {
								applyNo:$("#applyNo").val(),
								approveId:$("#approveId").val()
							}, 'post');
							closeJBox();
						});
					} else {
						alertx("新增抵押设备保存失败");
					}
				}
			});
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element)
		}
		});
	});
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">抵押设备信息</h3>
		<br>
		<div class="searchCon">
			<form:form id="equipForm" modelAttribute="mortageEquipmentUnion"  class="form-horizontal">
				<form:hidden path="id" />
				<input type="hidden" id="applyNo" name="applyNo" value="${mortageEquipmentUnion.applyNo}" />
				<input type="hidden" id="approveId" name="approveId" value="${mortageEquipmentUnion.approveId}" />
				<sys:message content="${message}" />
				<div class="control-group">
					<label class="control-label">型号：</label>
					<div class="controls">
						<form:input path="model" htmlEscape="false" maxlength="40" class="input-xlarge required" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">购买价格：</label>
					<div class="controls">
						<form:input path="buyingPrice" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium required" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">估值：</label>
					<div class="controls">
						<form:input path="valueOfAssessment" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium required" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">市值：</label>
					<div class="controls">
						<form:input path="maketValue" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium required" />
					</div>
				</div>
				
				<div class="searchButton">
					<shiro:hasPermission name="credit:gqgetComInfo:edit">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
					</shiro:hasPermission>
					<input id="btnCancel" class="btn btn-primary" type="button" value="关闭" onclick="closeJBox()" />
				</div>
			</form:form>
		</div>
	</div>
	
</body>
</html>