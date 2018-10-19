<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	<%-- /**
	 * 
	 * @reqno:H1511160121
	 * @date-designer:20151117-xudong
	 * @db-j : sys_system : sys_system
	 * @e-out-table : 统列系表 : 系统列表
	 * @date-author:20151117-xudong:增加系统添加的页面，设定字段的验证方法
	 */ --%>
		$(document).ready(function() {
			jQuery.validator.addMethod("sysNo", function(value, element, param){
				var sysNo = /^[0-9a-zA-Z-_]{0,50}$/;
				return this.optional(element) || (sysNo.test(value));
			}, "系统编号只允许输入英文、数字、下划线、中线");
			
			$("#inputForm").validate({
				rules: {
					no: {sysNo:true,remote: "${ctx}/sys/sysManage/checkSysNo?id=" + encodeURIComponent("${sysManage.id}")}
				},
				messages: {
					no: {remote: "系统编号已存在"}
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
		/**
		 * 
		 * @reqno: 	H1506290083
		 * @date-designer:20150630-zhangxingyu
		 * @e-in-other : ID编号 - disabledAll(cancel) : 禁用除id=cancel外的所有input,select,textarea标签和class='select2-choice'的a标签  移除掉  除id=cancel以外的所有按钮
		 * @date-author:20150630-zhangxingyu:定义禁用页面元素方法
		 */
		//禁用页面元素
		function disabledAll(cancel){
			$("input,select,textarea,a[class='select2-choice']").prop("disabled",true);//禁用所有input,select,textarea标签和class='select2-choice'的a标签  移除掉  除id=cancel以外的所有按钮
			$("#"+cancel).prop("disabled",false);//恢复id=can1cel的标签
			$(".btn:not(#"+cancel+")").remove(); //移除id=cancel以外的所有样式为btn的标签
		}
		 
		/**
		 * 
		 * @reqno: 	H1512180081 
		 * @date-designer:20160104-sunna
		 * @date-author:220160104-sunna:增加英文名称的输入判断，英文名称输入必须是英文、数字、下划线、中线
		 */
		function checkReg(enname){
			if(enname!=""){
				var re=new RegExp("^[0-9a-zA-Z-_]{0,50}$");
				var jname=re.test(enname);
				if(jname==false){
					alert("英文名称请输入英文，包括英文、数字、下划线、中线");
					$("#sysEnname").val("");
				 }
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/sysManage/">系统列表</a></li>
		<li class="active"><a href="${ctx}/sys/sysManage/form?id=${sysManage.id}">系统<shiro:hasPermission name="sys:sysManage:edit">${not empty sysManage.id?'修改':'添加'}</shiro:hasPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysManage" action="${ctx}/sys/sysManage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;系统编号:</label>
			<div class="controls">
				<form:input path="no" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;系统名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
		<!--/**
		 	* @reqno: 	H1512180081
		 	* @date-designer:20151223-sunna
		 	* @date-author:20151223-sunna:系统添加页面增加“英文名称”和“系统全名”
		 	*/-->
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;英文名称:</label>
			<div class="controls">
				<form:input path="sysEnname" htmlEscape="false" maxlength="200" class="required" onblur="checkReg(this.value)"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;系统全名:</label>
			<div class="controls">
				<form:input path="sysAllname" htmlEscape="false" maxlength="200" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;系统URL:</label>
			<div class="controls">
				<form:input path="accUrl" htmlEscape="false" maxlength="500" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;WebService服务地址:</label>
			<div class="controls">
				<form:input path="wbsUrl" htmlEscape="false" maxlength="500" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;单点登录地址:</label>
			<div class="controls">
				<form:input path="slnUrl" htmlEscape="false" maxlength="500" class="required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="location='${ctx}/sys/sysManage/list'"/>
		</div>
	</form:form>
</body>
</html>