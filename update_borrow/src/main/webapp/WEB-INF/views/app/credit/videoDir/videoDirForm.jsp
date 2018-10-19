<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>影像目录管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			if (${isCheck} != 1) {
				$("#name").focus();
				var jgType = '${videoDir.type}';
				if(jgType == '1' || jgType == '2'){
					$("#type").attr("readOnly","true");
				}
			}
			$("#inputForm").validate({

				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					checkReq(error, element);
				}
			});

			var isCheck=${isCheck};
			if (isCheck=="1") {
				disabledAll("btnCancel");
			}

			$("#type").select2({
				minimumResultsForSearch: -1
			 });
		});

		function disabledAll(cancel){
			$("input,select,textarea,a[class='select2-choice']").prop("readonly",true);//禁用所有input,select,textarea标签和class='select2-choice'的a标签  移除掉  除id=cancel以外的所有按钮
			$("#"+cancel).prop("disabled",false);//恢复id=cancel的标签
			$(".btn:not(#"+cancel+")").remove(); //移除id=cancel以外的所有样式为btn的标签
		}
	</script>

	<style type="text/css">
		.span4Tree {
			width : 240px;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/credit/videoDir/list?id=&&parent.id=0">目录列表</a></li>
		<li class="active"><a href="${ctx}/credit/videoDir/form?id=${videoDir.id}&parent.id=${videoDir.parent.id}">目录<shiro:hasPermission name="credit:videoDir:edit">${not empty videoDir.id?(isCheck=='1'?'查看':'修改'):'添加'}</shiro:hasPermission><shiro:lacksPermission name="credit:videoDir:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>

	<form:form id="inputForm" modelAttribute="videoDir" action="${ctx}/credit/videoDir/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="parentIds"/>
		<sys:message content="${message}"/>

		<div class="control-group">
			<label class="control-label">上级目录:</label>
			<div class="controls">
                <sys:treeselect id="videoDir" name="parent.id" value="${videoDir.parent.id}" labelName="parent.name" labelValue="${videoDir.parent.name}"
					title="目录" url="/credit/videoDir/treeData" extId="${videoDir.id}"  allowClear="${videoDir.currentUser.admin}"  cssClass="span4Tree" />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;当前目录:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required span4"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;对应路径:</label>
			<div class="controls">
				<form:input path="fileDir" htmlEscape="false" maxlength="50" class="span4"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">产品类型:</label>
			<div class="controls">
				<form:select path="type" class="span4">
					<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>

		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="location='${ctx}/credit/videoDir/list'"/>
		</div>
	</form:form>
</body>
</html>