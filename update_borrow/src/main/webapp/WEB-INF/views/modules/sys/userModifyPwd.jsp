<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>修改密码</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#oldPassword").focus();
			$("#inputForm").validate({
				rules: {
				},
				messages: {
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
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
	<ul class="nav nav-tabs">
		<!-- /**
			 * 
			 * @reqno: H1506010052
			 * @date-designer:20150603-zhangxingyu
			 * @e-ctrl : ID编号 - c:if : 判断
			 * 
			 * @date-author:20150603-zhangxingyu:判断isCheck=1用来确定是否需要个人信息链接
			 */ -->
		<c:if test="${isCheck eq '1'}"><li><a href="${ctx}/sys/user/info">个人信息</a></li></c:if>
		
		<li class="active"><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/modifyPwd" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<!--
		@reqno        :H1509070145
		@date-designer:20150909-jiangbing
		@e-out-other  :isCheck:判断isCheck=1用来确定是否需要个人信息链接
		@date-author  :20150909-jiangbing:添加isCheck隐藏项用来在体检表单时传递isCheck的值
		-->
		<input id="isCheck" name="isCheck" value="${isCheck}" type="hidden"/>
		<sys:message content="${message}"/>
		<div class="control-group">
            <!--
            @reqno        :H1509060061
            @date-designer:20150909-jiangbing
            @e-in-text    :oldPassword:旧密码
            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
            -->
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;旧密码:</label>
			<div class="controls">
				<input id="oldPassword" name="oldPassword" type="password" value="" maxlength="50" minlength="3" class="required"/>
			</div>
		</div>
		<div class="control-group">
            <!--
            @reqno        :H1509060061
            @date-designer:20150909-jiangbing
            @e-in-text    :newPassword:新密码
            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
            -->
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;新密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="6" class="required"/>
			</div>
		</div>
		<div class="control-group">
            <!--
            @reqno        :H1509060061
            @date-designer:20150909-jiangbing
            @e-in-text    :comfirmNewPassword:确认新密码
            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
            -->
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;确认新密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="6" class="required" equalTo="#newPassword"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
	</form:form>
</body>
</html>