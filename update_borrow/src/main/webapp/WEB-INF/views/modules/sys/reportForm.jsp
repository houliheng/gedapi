<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- 
/**
 * @reqno: 	H1512300129
 * @date-designer:20160112-sunna
 * @date-author:20160112-sunna:增加报表管理功能:包括报表的增加、删除、修改、展示、查询功能。
 */
 -->
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//${report.id}=1;
			if (${isCheck} != 1) {
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
			}
			H1601050065
			/**
			 * @reqno: 	H1601050065
			 * @date-designer:20160123-sunna
			 * @date-author:20160123-sunna:解决报表中增加和修改报表信息时有时候不弹出提示信息的问题。
			 */

			var isCheck=${isCheck};
			if (isCheck=="1") {
				disabledAll("btnCancel");
				
			}
		});

		//禁用页面元素
		function disabledAll(cancel){
			$("input,select,textarea,a[class='select2-choice']").prop("disabled",true);//禁用所有input,select,textarea标签和class='select2-choice'的a标签  移除掉  除id=cancel以外的所有按钮
			$("#"+cancel).prop("disabled",false);//恢复id=cancel的标签
			$(".btn:not(#"+cancel+")").remove(); //移除id=cancel以外的所有样式为btn的标签
		}
	</script>
</head>
<body>
<!-- 
	/**
	* @reqno: 	H1512300131
	* @date-designer:20160112-sunna
	* @date-author:20160112-sunna:报表管理增加报表添加功能。添加报表编号和报表名称。
	*/
 -->
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/report/">报表列表</a></li>
		
		<li class="active"><a href="${ctx}/sys/report/form?id=${report.id}">报表<shiro:hasPermission name="sys:report:edit">${not empty report.id?(isCheck=='1'?'查看':'修改'):'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:report:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<!--
	/**
	* @reqno: 	H1512300131
	* @date-designer:20160112-sunna
	* @date-author:20160112-sunna:报表管理增加报表添加功能。添加报表编号和报表名称。添加相同编号的报表弹出提示，并不能添加。
	*/
	-->
	<form:form id="inputForm" modelAttribute="report" action="${ctx}/sys/report/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		<c:if test="${isCheck!='1'}">
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;报表编号:</label>
			<div class="controls">
				<form:input path="no" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;报表名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		
		</c:if>
		
		
		<c:if test="${isCheck=='1'}">
		<div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;报表编号:</label>
            <div class="controls">
                <label class="lbl area">${report.no}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;报表名称:</label>
            <div class="controls">
                <label class="lbl area">${report.name}</label>
            </div>
        </div>
        
        </c:if>
		<div class="form-actions">
			<shiro:hasPermission name="sys:report:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="location='${ctx}/sys/report/list'"/>
		</div>
	</form:form>
</body>
</html>