<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function(){
            <%--
           	/**
			 * @reqno: H1511170155
			 * @date-designer:20151118-xudong
			 * @db-j : sys_role : 角色表
			 * @date-author:20151118-xudong:修改角色名称的验证方法，改为使用post方式提交，因后台存在乱码问题
			 */
            --%>
            if (${isCheck} != 1) {
			$("#name").focus();
            }
			$("#inputForm").validate({
				rules: {
					name: {remote: {
									url : "${ctx}/sys/role/checkName?oldName=" + encodeURIComponent(encodeURIComponent("${role.name}")),
									type : 'POST'
									}
							},
					enname: {remote: "${ctx}/sys/role/checkEnname?oldEnname=" + encodeURIComponent("${role.enname}")}
				},
				messages: {
					name: {remote: "角色名已存在"},
					enname: {remote: "英文名已存在"}
				},
				submitHandler: function(form){
// 					var ids = [], nodes = tree.getCheckedNodes(true);
// 					for(var i=0; i<nodes.length; i++) {
// 						ids.push(nodes[i].id);
// 					}
// 					$("#menuIds").val(ids);
// 					var ids2 = [], nodes2 = tree2.getCheckedNodes(true);
// 					for(var i=0; i<nodes2.length; i++) {
// 						ids2.push(nodes2[i].id);
// 					}
// 					$("#officeIds").val(ids2);
// 					loading('正在提交，请稍等...');
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
 			 * @reqno:H1512210064
 			 * @date-designer:2015年12月16日-zhangzhida
 			 * @date-author:2015年12月16日-zhangzhida:将角色添加页面中的权限控制的逻辑移除.将功能权限树和数据权限树的构建语句注释
 			 */
			/*  
			var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}}};
			
			// 用户-菜单
			var zNodes=[
					<c:forEach items="${menuList}" var="menu">{id:"${menu.id}", pId:"${not empty menu.parent.id?menu.parent.id:0}", name:"${not empty menu.parent.id?menu.name:'权限列表'}"},
					</c:forEach>];
			// 初始化树结构
			var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
			// 不选择父节点
			tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认选择节点
			var ids = "${role.menuIds}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				try{tree.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree.expandAll(true);
			
			// 用户-机构
			var zNodes2=[
					<c:forEach items="${officeList}" var="office">{id:"${office.id}", pId:"${not empty office.parent?office.parent.id:0}", name:"${office.name}"},
					</c:forEach>];
			// 初始化树结构
			var tree2 = $.fn.zTree.init($("#officeTree"), setting, zNodes2);
			// 不选择父节点
			tree2.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认选择节点
			var ids2 = "${role.officeIds}".split(",");
			for(var i=0; i<ids2.length; i++) {
				var node = tree2.getNodeByParam("id", ids2[i]);
				try{tree2.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree2.expandAll(true);
			// 刷新（显示/隐藏）机构
			refreshOfficeTree();
			$("#dataScope").change(function(){
				refreshOfficeTree();
			});
			*/
			var isCheck=${isCheck};
			if (isCheck=="1") {
				
				 /**
	 			 * 
	 			 * @reqno:H1512210064
	 			 * @date-designer:2015年12月16日-zhangzhida
	 			 * @date-author:2015年12月16日-zhangzhida:页面的权限逻辑已注释掉.将涉及的禁用tree的方法注释掉
	 			 */
// 				disabledTree(tree);
// 				disabledTree(tree2);
				disabledAll("btnCancel");
			}
			
			/**
 			 * 
 			 * @reqno:H1512210064
 			 * @date-designer:2015年12月16日-zhangzhida
 			 * @date-author:2015年12月16日-zhangzhida:将是否系统数据,是否可用.数据范围的下拉选择中的搜索框隐藏掉
 			 */
			$("#sysData").select2({
				minimumResultsForSearch: -1
			 });
			 $("#dataScope").select2({
					minimumResultsForSearch: -1
			 });
			 $("#useable").select2({
					minimumResultsForSearch: -1
			 });
		});
		/**
	 * 
	 * @reqno: 	H1506150058
	 * @date-designer:20150616-zhangxingyu
	 * @e-ctrl : ID编号 - if : 判断isCheck是否是”1“ 是的话执行禁用页面元素的方法 
	 * @e-in-other : ID编号 - disabledTree(tree) : 禁用树结构tree
	 * @e-in-other : ID编号 - disabledAll(cancel) : 禁用除id=cancel外的所有input,select,textarea标签和class='select2-choice'的a标签  移除掉  除id=cancel以外的所有按钮
	 * @date-author:20150616-zhangxingyu:判断是否是查看 如果是  禁用页面元素
	 */
		//禁用ztree
		function disabledTree(tree){
			var nodes = tree.transformToArray(tree.getNodes());
			for(var i=0,l=nodes.length;i<l;i++){
				tree.setChkDisabled(nodes[i],true);
			}
		}
		//禁用页面元素
		function disabledAll(cancel){
			$("input,select,textarea,a[class='select2-choice']").prop("disabled",true);//禁用所有input,select,textarea标签和class='select2-choice'的a标签  移除掉  除id=cancel以外的所有按钮
			$("#"+cancel).prop("disabled",false);//恢复id=cancel的标签
			$(".btn:not(#"+cancel+")").remove(); //移除id=cancel以外的所有样式为btn的标签
		}
		/*end*/
		function refreshOfficeTree(){
			if($("#dataScope").val()==9){
				$("#officeTree").show();
			}else{
				$("#officeTree").hide();
			}
		}
		/**
		 * 
		 * @reqno: 	H1509220020
		 * @date-designer:20150925-sunna
		 * @date-author:20150925-sunna:增加英文名称的输入判断，英文名称输入必须是字母
		 */
		function checkReg(enname){
			 if(enname!=""){
				 var re=new RegExp("^[a-zA-Z]+$");
					var jname=re.test(enname);
					if(jname==false){
						alert("英文名称请输入英文");
						$("#enname").val("");
			 		}
			}
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
		<li><a href="${ctx}/sys/role/">角色列表</a></li>
		<!-- /**
	 * 
	 * @reqno: 	H1506150058
	 * @date-designer:20150616-zhangxingyu
	 * @date-author:20150616-zhangxingyu:判断是否是查看 如果是  将页签明写为角色查看
	 */ -->
		<li class="active"><a href="${ctx}/sys/role/form?id=${role.id}">角色<shiro:hasPermission name="sys:role:edit">${empty role.id?'添加':(isCheck=='1'?'查看':'修改')}</shiro:hasPermission><shiro:lacksPermission name="sys:role:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="role" action="${ctx}/sys/role/save" method="post" class="form-horizontal">
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
			<!--
			 @reqno        :H1511230055
			 @date-designer:20151123-lvhaishan
			 @e-in-text    :office:归属机构
			 @date-author  :20151123-lvhaishan:把用户管理页面中的“公司”字样改为“机构”
			-->
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;归属机构:</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${role.office.id}" labelName="office.name" labelValue="${role.office.name}"
					title="机构" url="/sys/office/treeData" cssClass="required span4Tree" dataMsgRequired="必填信息" />
			</div>
		</div>
		<div class="control-group">
            <!--
            @reqno        :H1509060061
            @date-designer:20150909-jiangbing
            @e-in-text    :name:角色名称
            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
            -->
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;角色名称:</label>
			<div class="controls">
				<input id="oldName" name="oldName" type="hidden" value="${role.name}">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required span4"/>
				
			</div>
		</div>
		<div class="control-group">
            <!--
            @reqno        :H1509060061
            @date-designer:20150909-jiangbing
            @e-in-text    :ename:英文名称
            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
            -->
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;英文名称:</label>
			<div class="controls">
				<input id="oldEnname" name="oldEnname" type="hidden" value="${role.enname}">
				<!--
				/**
		 		 * 
		 		 * @reqno: 	H1509220020
		 		 * @date-designer:20150925-sunna
		 		 * @e-in-text    :ename:英文名称
		 		 * @date-author:20150925-sunna:增加英文名称的输入判断事件，英文名称输入必须是字母
		 		 */
		 		 -->
				<form:input path="enname" htmlEscape="false" maxlength="50" class="required  span4" onblur="checkReg(this.value)"/>
				<span class="help-inline"> 工作流用户组标识</span>
			</div>
		</div>
		<%--  
		 /**
 			 * 
 			 * @reqno:H1512210064
 			 * @date-designer:2015年12月16日-zhangzhida
 			 * @e-in-list    :roleType:角色类型
			 * @db-j         :sys_dict:字典表
 			 * @date-author:2015年12月16日-zhangzhida:系统中角色类型参数并未被真正的使用.
 			 									           所以在角色信息添加 的时候将角色类型的信息输入框隐藏掉
 			 */
		<div class="control-group">
			<label class="control-label">角色类型:</label>
			<div class="controls"><%--
				<form:input path="roleType" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline" title="activiti有3种预定义的组类型：security-role、assignment、user 如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务">
					工作流组用户组类型（security-role：管理员、assignment：可进行任务分配、user：普通用户）</span> 
				<!--<form:select path="roleType" class="input-medium">
					<form:option value="assignment">任务分配</form:option>
					<form:option value="security-role">管理角色</form:option>
					<form:option value="user">普通角色</form:option>
				</form:select>-->
				<!--
				@reqno        :H1509070148
				@date-designer:20150910-jiangbing
				@e-in-list    :roleType:角色类型
				@db-j         :sys_dict:字典表
				@date-author  :20150910-jiangbing:将角色类型选择框内容生成标签改为通过form:options从DB查询内容，键值、标签和类型不变
				-->
				<form:select path="roleType" class="input-medium">
                    <form:options items="${fns:getDictList('role_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
				<span class="help-inline" title="activiti有3种预定义的组类型：security-role、assignment、user 如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务">
					工作流组用户组类型（任务分配：assignment、管理角色：security-role、普通角色：user）</span>
			</div>
		</div>
		--%>
		<div class="control-group">
			<label class="control-label">是否系统数据:</label>
			<div class="controls">
				<form:select path="sysData" class="span4">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">“是”代表此数据只有超级管理员能进行修改，“否”则表示拥有角色修改人员的权限都能进行修改</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否可用</label>
			<div class="controls">
				<form:select path="useable" class="span4">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">“是”代表此数据可用，“否”则表示此数据不可用</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数据范围:</label>
			<div class="controls">
				<form:select path="dataScope" class="span4">
					<form:options items="${fns:getDictList('sys_data_scope')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">特殊情况下，设置为“按明细设置”，可进行跨机构授权</span>
			</div>
		</div>
		<%--
			/**
			 * @reqno: H1511170155
			 * @date-designer:20151118-xudong
			 * @db-j : sys_role : 角色表
			 * @db-j : rl_role_sys : 系统角色关联表
			 * @db-j : sys_system : 系统表
			 * @date-author:20151118-xudong:增加所属系统的配置项
			 */
		--%>
		<div class="control-group">
			<label class="control-label">所属系统:</label>
			<div class="controls">
				<form:select path="sysNo" class="span4" multiple="true">
                    <form:options items="${fns:getSystemList(fns:getUser().loginName)}" itemLabel="name" itemValue="no" htmlEscape="false"/>
                </form:select>
			</div>
		</div>
		<!-- 
		 /**
 			 * 
 			 * @reqno:H1512210064
 			 * @date-designer:2015年12月16日-zhangzhida
 			 * @e-out-tree : ztree - 功能权限树 : 功能权限树
 			 * @e-out-tree : officeTree - 数据权限树 : 数据权限树
 			 * @e-in-text : menuIds - 隐藏功能编号:隐藏功能编号
 			 * @e-in-text : officeIds - 数据功能编号:数据功能编号
 			 * @date-author:2015年12月16日-zhangzhida:将用户信息添加页面的角色授权逻辑移除.注释掉功能权限和数据权限的树
 			 */ 
		<div class="control-group">
			<label class="control-label">角色授权:</label>
			<div class="controls">
				<div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<form:hidden path="menuIds"/>
				<div id="officeTree" class="ztree" style="margin-left:100px;margin-top:3px;float:left;"></div>
				<form:hidden path="officeIds"/>
			</div>
		</div>
		-->
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="span4"/>
			</div>
		</div>
		</c:if>
		<!--
        @reqno        :H1509070147
        @date-designer:20150910-jiangbing
        @e-out-other  :office.name:上级机构
        @e-out-other  :name:角色名称
        @e-out-other  :enname:英文名称
        @e-out-other  :roleTypeLabel:角色类型
        @e-out-other  :sysDataLabel:是否系统数据
        @e-out-other  :useableLabel:是否可用
        @e-out-other  :dataScopeLabel:数据范围
        @e-tree       :menuTree:功能菜单
        @e-tree       :officeTree:机构菜单
        @e-out-other  :menuIds:功能菜单结构树ID 隐藏
        @e-out-other  :officeIds:机构菜单结构树ID 隐藏
        @e-out-other  :dataScope:数据范围 判断是否显示机构菜单 隐藏
        @e-out-other  :remarks:备注
        @e-ctrl       :c if:isCheck=='1' 查看的情况
        @date-author  :20150910-jiangbing:添加专门用于查看的HTML
        -->
		<c:if test="${isCheck=='1'}">
		<div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;归属机构:</label>
            <div class="controls">
                <label class="lbl area">${role.office.name}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;角色名称:</label>
            <div class="controls">
                <label class="lbl area">${role.name}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;英文名称:</label>
            <div class="controls">
                <label class="lbl">${role.enname}</label>
                <span class="help-inline lbl">&nbsp;&nbsp;工作流用户组标识</span>
            </div>
        </div>
        <!--
        @reqno        :H1509070148
        @date-designer:20150910-jiangbing
        @e-out-other  :roleTypeLabel:角色类型
        @e-out-other  :sysDataLabel:是否系统数据
        @e-out-other  :useableLabel:是否可用
        @e-out-other  :dataScopeLabel:数据范围
        @date-author  :20150910-jiangbing:将选择的内容在查看页面表示出来
        -->
        <!-- 
         /**
 			 * 
 			 * @reqno:H1512210064
 			 * @date-designer:2015年12月16日-zhangzhida
 			 * @date-author:2015年12月16日-zhangzhida:系统中角色类型参数并未被真正的使用.
 			 									           所以在查看角色信息 的时候将角色类型的信息输出框一暗藏掉
 			 */
 		   <div class="control-group">
            <label class="control-label">角色类型:</label>
            <div class="controls">
                <label class="lbl">${role.roleTypeLabel}</label>
                <span class="help-inline lbl" title="activiti有3种预定义的组类型：security-role、assignment、user 如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务">
                    &nbsp;&nbsp;工作流组用户组类型（任务分配：assignment、管理角色：security-role、普通角色：user）</span>
            </div>
        </div>
         -->
        <div class="control-group">
            <label class="control-label">是否系统数据:</label>
            <div class="controls">
                <label class="lbl">${role.sysDataLabel}</label>
                <span class="help-inline lbl">&nbsp;&nbsp;“是”代表此数据只有超级管理员能进行修改，“否”则表示拥有角色修改人员的权限都能进行修改</span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">是否可用</label>
            <div class="controls">
                <label class="lbl">${role.useableLabel}</label>
                <span class="help-inline lbl">&nbsp;&nbsp;“是”代表此数据可用，“否”则表示此数据不可用</span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">数据范围:</label>
            <div class="controls">
                <label class="lbl">${role.dataScopeLabel}</label>
                <span class="help-inline lbl">&nbsp;&nbsp;特殊情况下，设置为“按明细设置”，可进行跨机构授权</span>
            </div>
        </div>
        <%--
			/**
			 * @reqno: H1511170155
			 * @date-designer:20151118-xudong
			 * @db-j : sys_system : 系统表
			 * @date-author:20151118-xudong:增加所属系统的配置项
			 */
		--%>
		<div class="control-group">
			<label class="control-label">所属系统:</label>
			<div class="controls">
				<label class="lbl">${role.sysManagesName}</label>
			</div>
		</div>
		<!-- 
		 /**
 			 * 
 			 * @reqno:H1512210064
 			 * @date-designer:2015年12月16日-zhangzhida
 			 * @date-author:2015年12月16日-zhangzhida:将角色添加页面中的权限控制的逻辑移除.在查看用户信息的时候将功能权限树和数据权限树隐藏掉
 			 */ 
        <div class="control-group">
            <label class="control-label">角色授权:</label>
            <div class="controls">
                <div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
                <form:hidden path="menuIds"/>
                <div id="officeTree" class="ztree" style="margin-left:100px;margin-top:3px;float:left;"></div>
                <form:hidden path="officeIds"/>
                <form:hidden path="dataScope"/>
            </div>
        </div>
        -->
        <div class="control-group">
            <label class="control-label">备注:</label>
            <div class="controls">
                <label class="lbl area">${role.remarks}</label>
            </div>
        </div>
        </c:if>
		<div class="form-actions">
			<c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
				<shiro:hasPermission name="sys:role:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>