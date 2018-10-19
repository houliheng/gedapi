<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>抵押设备管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		adjustTextareaLength("description", "preDescription");
		$("#warehouseForm").validate({
		submitHandler : function(form) {
			loading();
			var param = $("#warehouseForm").serialize();
			$.post("${ctx}/credit/gqgetComInfoUnion/warehouseSave", param, function(data) {
				if (data) {
					closeTip();
					if (data.status == "1") {
						alertx(data.message, function() {
							parent.$.loadDiv('warehouse', '${ctx}/credit/gqgetComInfoUnion/warehouseList', {
							applyNo : $("#applyNo").val(),
							approId : data.approId
							}, 'post');
							closeJBox();
						});
					} else {
						alertx(data.message);
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
		<h3 class="searchTitle">库存货物信息</h3>
		<br>
		<div class="searchCon">
			<form:form id="warehouseForm" modelAttribute="warehouseGoods" class="form-horizontal">
				<form:hidden path="id" />
				<input type="hidden" id="applyNo" name="applyNo" value="${warehouseGoods.applyNo}" />
				<input type="hidden" id="approId" name="approId" value="${warehouseGoods.approId}" />
				<sys:message content="${message}" />
				<div class="control-group">
					<label class="control-label">类型：</label>
					<div class="controls">
						<form:select path="wareType" class="input-medium required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('WARE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">描述：</label>
					<div class="controls">
						<pre class="input-xxlarge pre-style textarea-width" id="preDescription"></pre>
						<form:textarea path="description" id="description" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge textarea-style textarea-width required" onkeyup="adjustTextareaLength('description','preDescription')" />
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