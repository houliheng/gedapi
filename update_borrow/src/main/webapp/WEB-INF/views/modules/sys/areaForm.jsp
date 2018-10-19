<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>区域管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			/**
			* @reqno        :H1509070147
			* @date-designer:20150910-jiangbing
			* @date-author  :20150910-jiangbing:查看的情况下不设置焦点
			**/
			if (${isCheck} != 1) {
				$("#name").focus();
			}
			
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
		/**
		 * 
		 * @reqno: 	H1506290076
		 * @date-designer:20150630-zhangxingyu
		 * @e-ctrl : ID编号 - if : 判断isCheck是否是”1“ 是的话执行禁用页面元素的方法 
		 * @date-author:20150630-zhangxingyu:禁用页面元素
		 */
			var isCheck=${isCheck};
			if (isCheck=="1") {
				disabledAll("btnCancel");
			}
		});
		/**
		 * 
		 * @reqno: 	H1506290076
		 * @date-designer:20150630-zhangxingyu
		 * @e-in-other : ID编号 - disabledAll(cancel) : 禁用除id=cancel外的所有input,select,textarea标签和class='select2-choice'的a标签  移除掉  除id=cancel以外的所有按钮
		 * @date-author:20150630-zhangxingyu:定义禁用页面元素方法
		 */
		//禁用页面元素
		function disabledAll(cancel){
			$("input,select,textarea,a[class='select2-choice']").prop("disabled",true);//禁用所有input,select,textarea标签和class='select2-choice'的a标签  移除掉  除id=cancel以外的所有按钮
			$("#"+cancel).prop("disabled",false);//恢复id=cancel的标签
			$(".btn:not(#"+cancel+")").remove(); //移除id=cancel以外的所有样式为btn的标签
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/area/">区域列表</a></li>
		<!-- /**
			 * 
			 * @reqno: 	H1506290076
			 * @date-designer:20150630-zhangxingyu
			 * @date-author:20150630-zhangxingyu:判断是否是查看 如果是  将页签明写为查看
			 */ -->
		<li class="active"><a href="form?id=${area.id}&parent.id=${area.parent.id}">区域<shiro:hasPermission name="sys:area:edit">${not empty area.id?(isCheck=='1'?'查看':'修改'):'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:area:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="area" action="${ctx}/sys/area/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<!--
		@reqno        :H1509070147
		@date-designer:20150910-jiangbing
		@e-ctrl       :c if:isCheck!='1' 修改或是添加的情况
		@date-author  :20150910-jiangbing:在修改或是添加的情况下显示输入框选择框等控件
		-->
		<c:if test="${isCheck!='1'}">
		<div class="control-group">
			<label class="control-label">上级区域:</label>
			<div class="controls">
				<sys:treeselect id="area" name="parent.id" value="${area.parent.id}" labelName="parent.name" labelValue="${area.parent.name}"
					title="区域" url="/sys/area/treeData" extId="${area.id}" cssClass="" allowClear="true"/>
			</div>
		</div>
		<div class="control-group">
			<!--
			@reqno        :H1509060061
			@date-designer:20150909-jiangbing
			@e-in-text    :name:区域名称
			@date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
			-->
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;区域名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区域编码:</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区域类型:</label>
			<div class="controls">
				<form:select path="type" class="input-medium">
					<form:options items="${fns:getDictList('sys_area_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		</c:if>
        <!--
		@reqno        :H1509070147
		@date-designer:20150910-jiangbing
		@e-out-other  :parent.name:上级区域
		@e-out-other  :name:区域名称
		@e-out-other  :code:区域编码
		@e-out-other  :typeLabel:区域类型
		@e-out-other  :remarks:备注
		@e-ctrl       :c if:isCheck=='1' 查看的情况
		@date-author  :20150910-jiangbing:添加专门用于查看的HTML
		-->
		<c:if test="${isCheck=='1'}">
		<div class="control-group">
            <label class="control-label">上级区域:</label>
            <div class="controls">
                <label class="lbl area">${area.parent.name}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;区域名称:</label>
            <div class="controls">
                <label class="lbl area">${area.name}</label>
                
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">区域编码:</label>
            <div class="controls">
                <label class="lbl">${area.code}</label>
            </div>
        </div>
        <!--
        @reqno        :H1509070148
        @date-designer:20150910-jiangbing
        @e-out-other  :typeLabel:区域类型
        @date-author  :20150910-jiangbing:将选择的内容在查看页面表示出来
        -->
        <div class="control-group">
            <label class="control-label">区域类型:</label>
            <div class="controls">
                <label class="lbl">${area.typeLabel}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">备注:</label>
            <div class="controls">
                <label class="lbl area">${area.remarks}</label>
            </div>
        </div>
        </c:if>
		<div class="form-actions">
			<shiro:hasPermission name="sys:area:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>