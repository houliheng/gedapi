\<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
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
			 * @reqno: 	H1506290081
			 * @date-designer:20150630-zhangxingyu
			 * @e-ctrl : ID编号 - if : 判断isCheck是否是”1“ 是的话执行禁用页面元素的方法 
			 * @date-author:20150630-zhangxingyu:判断是否是查看 如果是  禁用页面元素
			 */
			var isCheck=${isCheck};
			if (isCheck=="1") {
				disabledAll("btnCancel");
			}
		});
			/**
			 * 
			 * @reqno: 	H1506290081
			 * @date-designer:20150630-zhangxingyu
			 * @e-in-other : ID编号 - disabledAll(cancel) : 禁用除id=cancel外的所有input,select,textarea标签和class='select2-choice'的a标签  移除掉  除id=cancel以外的所有按钮
			 * @date-author:20150630-zhangxingyu:禁用页面元素的方法
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
		<li><a href="${ctx}/sys/menu/">菜单列表</a></li>
		<!-- /**
	 * 
	 * @reqno: 	H1506290081
	 * @date-designer:20150630-zhangxingyu
	 * @date-author:20150630-zhangxingyu:判断是否是查看 如果是  将页签明写为菜单查看
	 */ -->
		<li class="active"><a href="${ctx}/sys/menu/form?id=${menu.id}&parent.id=${menu.parent.id}">菜单<shiro:hasPermission name="sys:menu:edit">${empty menu.id?'添加':(isCheck=='1'?'查看':'修改')}</shiro:hasPermission><shiro:lacksPermission name="sys:menu:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="menu" action="${ctx}/sys/menu/save" method="post" class="form-horizontal">
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
			<label class="control-label">上级菜单:</label>
			<div class="controls">
                <sys:treeselect id="menu" name="parent.id" value="${menu.parent.id}" labelName="parent.name" labelValue="${menu.parent.name}"
					title="菜单" url="/sys/menu/treeData" extId="${menu.id}" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
            <!--
            @reqno        :H1509060061
            @date-designer:20150909-jiangbing
            @e-in-text    :name:名称
            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
            -->
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>&nbsp;名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">链接:</label>
			<div class="controls">
				<form:input path="href" htmlEscape="false" maxlength="2000" class="input-xxlarge"/>
				<span class="help-inline">点击菜单跳转的页面</span>
			</div>
		</div>
		<%--
			/**
			 * @reqno: H1511170152
			 * @date-designer:20151118-xudong
			 * @db-j : sys_system : 系统表
			 * @date-author:20151118-xudong:增加所属系统的配置项
			 */
		--%>
		<%-- <div class="control-group">
			<label class="control-label">所属系统:</label>
			<div class="controls">
				<form:select path="sysNo" class="input-xxlarge" multiple="true">
                    <form:options items="${fns:getSystemList(fns:getUser().loginName)}" itemLabel="name" itemValue="no" htmlEscape="false"/>
                </form:select>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">目标:</label>
			<div class="controls">
				<form:input path="target" htmlEscape="false" maxlength="10" class="input-small"/>
				<span class="help-inline">链接地址打开的目标窗口，默认：mainFrame</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图标:</label>
			<div class="controls">
				<sys:iconselect id="icon" name="icon" value="${menu.icon}"/>
			</div>
		</div>
		<div class="control-group">
            <!--
            @reqno        :H1509060061
            @date-designer:20150909-jiangbing
            @e-in-text    :sort:排序
            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
            -->
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;排序:</label>
			<div class="controls">
				<!--
				 * @reqno: 	H1506290092
				 * @date-designer:20150629-zhunan
				 * @e-in-text : sorts - 输入框 : 输入排序号
				 * @date-author:20150629-zhunan: 由于后台数据库只能存10个字符，所以此处最大字符数由50改成9,此项也是必填项，需要加入必填标示
				-->
				<form:input path="sort" htmlEscape="false" maxlength="9" class="required digits input-small"/>
				<span class="help-inline">排列顺序，升序。</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型:</label>
			<div class="controls">
			<!-- /**
			 * 
			 * @reqno: H1506030057
			 * @date-designer:20150605-zhangxingyu
			 * @e-in-other : ID编号 - span : 对类型选项加的描述
			 * 
			 * @date-author:20150605-zhangxingyu:“显示”改为“菜单”，“隐藏”改为“功能”，“可见”改为“类型”。对类型加描述“只有类型为菜单的资源才可以在系统菜单中显示”
			 */ -->
				<form:radiobuttons path="isShow" items="${fns:getDictList('menu_function')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline">只有类型为菜单的资源才可以在系统菜单中显示</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权限标识:</label>
			<div class="controls">
				<form:input path="permission" htmlEscape="false" maxlength="100" class="input-xxlarge"/>
				<span class="help-inline">控制器中定义的权限标识，如：@RequiresPermissions("权限标识")</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		</c:if>
		<!--
        @reqno        :H1509070147
        @date-designer:20150910-jiangbing
        @e-out-other  :parent.name:上级菜单
        @e-out-other  :name:名称
        @e-out-other  :href:链接
        @e-out-other  :target:目标
        @e-out-other  :icon:图标
        @e-out-other  :sort:排序
        @e-out-other  :isShowLabel:类型
        @e-out-other  :permission:权限标识
        @e-out-other  :remarks:备注
        @e-ctrl       :c if:isCheck=='1' 查看的情况
        @date-author  :20150910-jiangbing:添加专门用于查看的HTML
        -->
		<c:if test="${isCheck=='1'}">
		<div class="control-group">
            <label class="control-label">上级菜单:</label>
            <div class="controls">
                <label class="lbl area">${menu.parent.name}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font> </span>&nbsp;名称:</label>
            <div class="controls">
                <label class="lbl area">${menu.name}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">链接:</label>
            <div class="controls">
                <label class="lbl">${menu.href}</label>
                <span class="help-inline lbl">&nbsp;&nbsp;点击菜单跳转的页面</span>
            </div>
        </div>
        <%--
			/**
			 * @reqno: H1511170152
			 * @date-designer:20151118-xudong
			 * @db-j : sys_system : 系统表
			 * @date-author:20151118-xudong:增加所属系统的配置项
			 */
		--%>
		<div class="control-group">
			<label class="control-label">所属系统:</label>
			<div class="controls">
				<label class="lbl">${menu.sysManagesName}</label>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">目标:</label>
            <div class="controls">
                <label class="lbl">${menu.target}</label>
                <span class="help-inline lbl">&nbsp;&nbsp;链接地址打开的目标窗口，默认：mainFrame</span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">图标:</label>
            <div class="controls">
                <sys:iconselect id="icon" name="icon" value="${menu.icon}"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;排序:</label>
            <div class="controls">
                <label class="lbl">${menu.sort}</label>
                <span class="help-inline lbl">&nbsp;&nbsp;排列顺序，升序。</span>
            </div>
        </div>
        <!--
        @reqno        :H1509070148
        @date-designer:20150910-jiangbing
        @e-out-other  :isShowLabel:类型
        @date-author  :20150910-jiangbing:将选择的内容在查看页面表示出来
        -->
        <div class="control-group">
            <label class="control-label">类型:</label>
            <div class="controls">
                <label class="lbl">${menu.isShowLabel}</label>
                <span class="help-inline lbl">&nbsp;&nbsp;只有类型为菜单的资源才可以在系统菜单中显示</span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">权限标识:</label>
            <div class="controls">
                <label class="lbl">${menu.permission}</label>
                <span class="help-inline lbl">&nbsp;&nbsp;控制器中定义的权限标识，如：@RequiresPermissions("权限标识")</span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">备注:</label>
            <div class="controls">
                <label class="lbl area">${menu.remarks}</label>
            </div>
        </div>
        </c:if>
		<div class="form-actions">
			<shiro:hasPermission name="sys:menu:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>