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
					$.post("${ctx}/credit/videoUpload/save", param, function(data) {
						if (data) {
							closeTip();
							if (data.status == "1") {
								alertx("新增自定义目录保存成功", function() {
									parent.refreshTree(false, 0);
									closeJBox();
								});
							}else if(data.status == "999"){
								alertx(data.message);
							}else {
								alertx("新增自定义目录保存失败");
							}
						}
					});
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					checkReq(error, element);
				}
			});
		});
	</script>
</head>
<body>
		<h3 class="searchTitle">自定义目录</h3>
	<br/>
	<form:form id="inputForm" modelAttribute="videoDir" action="${ctx}/credit/videoUpload/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<form:hidden path="id" />
		<form:hidden path="applyNo" />
		<form:hidden path="type"/>
		<div class="control-group">
			<label class="control-label">上级目录:</label>
			<div class="controls">
                <sys:treeselect id="videoDir" name="parent.id" value="${videoDir.parent.id}" labelName="parent.name" labelValue="${videoDir.parent.name}"
					title="目录" url="/credit/videoUpload/userDefined?applyNo=${videoDir.applyNo}&type=${videoDir.type}" extId="${videoDir.id}"  cssClass="span4Tree" />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">当前目录：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="400" class="input-xlarge required"/>
				<font style="color: red">*</font>
			</div>
		</div>

		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeJBox()"/>
		</div>
	</form:form>
</body>
</html>