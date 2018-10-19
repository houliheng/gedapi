<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
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
		<li class="active"><a href="${ctx}/sys/user/info">个人信息</a></li>
		<!-- /**
	 * 
	 * @reqno: H1506010052
	 * @date-designer:20150603-zhangxingyu
	 * @e-ctrl : ID编号 - shiro:hasPermission : 控制权限的shiro标签
	 * 
	 * @date-author:20150603-zhangxingyu:为修改密码链接加权限sys:user:modify     
	 									 加一个请求参数isCheck=1用来标记是否需要个人信息链接
	 */ -->
		<shiro:hasPermission name="sys:user:modifyPwd" >
		<li><a href="${ctx}/sys/user/modifyPwd?isCheck=1">修改密码</a></li>
		</shiro:hasPermission>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/info" method="post" class="form-horizontal"><%--
		<form:hidden path="email" htmlEscape="false" maxlength="255" class="input-xlarge"/>
		<sys:ckfinder input="email" type="files" uploadPath="/mytask" selectMultiple="false"/> --%>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">头像:</label>
			<div class="controls">
				<form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<!-- /**
				 * 
				 * @reqno: H1506010052
				 * @date-designer:20150603-zhangxingyu
				 * 
				 * @e-ctrl : ID编号 - shiro:hasPermission : 有sys:user:modifyTitle权限的shiro标签
 				 * @e-ctrl : ID编号 - shiro:lacksPermission : 无sys:user:modifyTitle权限的shiro标签
 				 * @e-ctrl : ID编号 - sys:ckfinder : 无sys:user:modifyTitle权限时显示只读的头像
				 * @date-author:20150603-zhangxingyu:为头像的选择清除按钮加权限sys:user:modify
				 */ -->
				<shiro:hasPermission name="sys:user:modifyTitle" >
					<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
				</shiro:hasPermission>
				<shiro:lacksPermission name="sys:user:modifyTitle">
					<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100" readonly="true"/>
				</shiro:lacksPermission>
			</div>
		<!--
		 @reqno        :H1511230055
		 @date-designer:20151123-lvhaishan
		 @date-author  :20151123-lvhaishan:把用户管理页面中的“公司”字样改为“机构”
		-->
		<div class="control-group">
			<label class="control-label">归属机构:</label>
			<div class="controls">
				<label class="lbl">${user.company.name}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属部门:</label>
			<div class="controls">
				<label class="lbl">${user.office.name}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="50" class="email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<!-- 
			* @reqno:H1512150148
		 	* @date-designer:20151215-sunna
		 	* @date-author:20150925-sunna:隐藏用户类型
		<div class="control-group">
			<label class="control-label">用户类型:</label>
			<div class="controls">
				<label class="lbl">${fns:getDictLabel(user.userType, 'sys_user_type', '无')}</label>
			</div>
		</div>
		 -->
		<div class="control-group">
			<label class="control-label">用户角色:</label>
			<div class="controls">
				<label class="lbl">${user.roleNames}</label>
			</div>
		</div>
		<div class="control-group">
			<!-- /**
				 * 
				 * @reqno:H1507070036
				 * @date-designer:20150709-zhangxingyu
				 * @db-j : sys_user : loginDate
				 * @e-out-other :label: 文本
				 * @e-out-other :fmt:formatDate: 按指定格式输出时间
				 * @date-author:20150709-zhangxingyu:将“上次登录”字样改为：“最近一次登录”
				 									将输出的user.oldLoginIp改为user.loginIp
				 									user.oldLoginDate改为user.loginDate
				 									去掉上次添加的fmt:formatDate的timeZone="cn"时区属性
				 */ -->
			<label class="control-label">最近一次登录:</label>
			<div class="controls">
				<label class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
	</form:form>
</body>
</html>