<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            /**
             * @reqno        :H1509070147
             * @date-designer:20150910-jiangbing
             * @date-author  :20150910-jiangbing:查看的情况下不设置焦点
             **/
            if (${isCheck} != 1) {
				$("#no").focus();
			}
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')},
		             /**
		              * @reqno        :H1601040014
		              * @date-designer:20160112-zhangzhida
		              * @date-author  :20160112-zhangzhida:当表单提交时,校验表单数据,增加对邮件,电话,手机的正则表达式的验证
		              **/
					email : {email : true},
					phone : {simplePhone : true},
					mobile : {mobile : true}
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
				submitHandler: function(form){
				// 姓名去空格
				$("#name").val($("#name").val().replace(/\s/g,""));
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
			 * @reqno: H1507070030
			 * @date-designer:20150710-zhangxingyu
			 * @e-in-text : companyName - input text: 输入框
			 * @e-in-text : officeName - input text: 输入框
			 * @e-out-other : officeButton - input text: 超链接
			 * @date-author:20150710-zhangxingyu:为归属部门的div注册mousedown事件
			 * 									判断当归属机构为空时不允许选择部门，并给出提示
			 */
			$("#officeButton").parent().mousedown(function(){
				if($("#companyId").val()==""||$("#companyName").val()==""){
					top.$.jBox.tip("请先选择归属机构");
					$("#officeButton").attr("disabled","disabled");
				}
			});
			if($("#companyId").val()==""||$("#companyName").val()==""){
				$("#officeButton").attr("disabled","disabled");
			}
			/**
		 * 
		 * @reqno: 	H1506250120
		 * @date-designer:20150630-zhangxingyu
		 * @e-ctrl : ID编号 - if : 判断isCheck是否是”1“ 是的话执行禁用页面元素的方法 
		 * @date-author:20150630-zhangxingyu:禁用页面元素
		 */
			var isCheck=${isCheck};
			if (isCheck=="1") {
				disabledAll("btnCancel");
			}
			/**
             * @reqno        :H1601040014
             * @date-designer:20160112-zhangzhida
             * @date-author  :20160112-zhangzhida:取消"是否有效用户"的下拉框的搜索功能
             **/
			$("#loginFlag").select2({
				minimumResultsForSearch: -1
		 });
		});
		/**
		 * 
		 * @reqno: 	H1506250120
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
<style type="text/css">
.span4Tree {
	width : 240px;
}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/list" >用户列表</a></li>
		<!-- /**
			 * 
			 * @reqno: 	H1506250120
			 * @date-designer:20150630-zhangxingyu
			 * @date-author:20150630-zhangxingyu:判断是否是查看 如果是  将页签明写为用户查看
			 */ -->
		<li class="active"><a href="${ctx}/sys/user/form?id=${user.id}">用户<shiro:hasPermission name="sys:user:edit">${not empty user.id?(isCheck=='1'?'查看':'修改'):'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:user:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
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
			<label class="control-label">头像:</label>
			<div class="controls">
				<form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<!--
					@reqno        :H1508310083
					@date-designer:20150902-jiangbing
					@e-out-other  :<a>:图片删除按钮 
					@e-ctrl : ID编号 - c:if : isCheck==1时为查看模式 图片只能查看
					@e-ctrl : ID编号 - c:if : isCheck!=1为非查看模式图片可修改
					@date-author  :20150902-jiangbing:在查看页面(isCheck==1)将图片查看插件设置成readonly
				-->
				<!-- 
				<c:if test="${isCheck=='1'}">
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100" readonly="true"/>
				</c:if>
				<c:if test="${isCheck!='1'}"> -->
				<!--
		        @reqno        :H1509070147
		        @date-designer:20150910-jiangbing
		        @e-ctrl       :sys ckfinder:图片上传、显示
		        @date-author  :20150910-jiangbing:由于将查看页面单独抽出，将原来的用户头像查看判断去掉
		        -->
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
				<!--</c:if>-->
			</div>
		</div>
		<table>
			<tr><td>
					<div class="control-group">
					<!--
					 @reqno        :H1511230055
					 @date-designer:20151123-lvhaishan
					 @e-in-text    :office:归属机构
					 @date-author  :20151123-lvhaishan:把用户管理页面中的“公司”字样改为“机构”
					-->
					<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;归属机构:</label>
					<div class="controls">
						<!-- /**
						 * 
						 * @reqno: H1507070030
						 * @date-designer:20150710-zhangxingyu
						 * @e-in-text : oldcompanyId - input type="hidden": 隐藏域
						 * @date-author:20150710-zhangxingyu:用来存放每次更改前的归属机构id
						 * 									将sys:treeselect标签改为新定义的sys:usertreeselect标签
						 */ -->
						<input id="oldCompanyId"  type="hidden" value="${user.company.id}"/>
		               <!-- 
		               /**
			             * @reqno        :H1601040014
			             * @date-designer:20160112-zhangzhida
			             * @e-in-text : company - 归属机构: 归属机构
			             * @date-author  :20160112-zhangzhida:对页面的组件重新排版布局,使归属机构组件长度保持一致.因为treeselect组件自带搜索按钮,所以设置样式span4Tree固定长度
			             **/
		                -->
		                <sys:usertreeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
							title="机构" url="/sys/office/treeData?type=1" cssClass="required span4Tree" dataMsgRequired="必填信息"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">归属部门:</label>
					<div class="controls">
				<!-- /**
						 * 
						 * @reqno: H1507070030
						 * @date-designer:20150710-zhangxingyu
						 * @e-tree : office - sys:treeselect : 引入的ztree树结构标签
						 * @date-author:20150710-zhangxingyu:修改url属性 添加一个参数&companyId=${user.company.id}
						 * 										将sys:treeselect标签改为新定义的sys:usertreeselect标签
						 */ -->
						 <!--
						 * @reqno: 	H1507220112
						 * @date-designer:20150728-chenshaojia
						 * @e-in-text : office - 选择框 : 归属部门
						 * @db-j : sys_user : OFFICE_ID
						 * @date-author:20150728-chenshaojia: 允许添加、修改用户时，归属部门项允许为空，删除必填标示
						-->
						 <!-- 
		               /**
			             * @reqno        :H1601040014
			             * @date-designer:20160112-zhangzhida
			             * @e-in-text : office - 部门: 部门
			             * @date-author  :20160112-zhangzhida:对页面的组件重新排版布局,使部门组件长度保持一致.因为treeselect组件自带搜索按钮,所以设置样式span4Tree固定长度
			             **/
		                -->
		                <sys:usertreeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
							title="部门" url="/sys/office/treeData?type=2&companyId=${user.company.id}" notAllowSelectParent="true" allowClear="true" cssClass="span4Tree"/>
						<!--
						 * @reqno: 	H1506270002
						 * @date-designer:20150629-zhunan
						 * @e-in-text : company - 选择框 : 归属机构
						 * @db-j : sys_user : OFFICE_ID
						 * @date-author:20150629-zhunan: 此项为必输项，但是没有加上必输标示，需要添加上
						-->
					</div>
				</div>
				<!--  
				/**
				 * 
				 * @reqno:H1512150148
				 * @date-designer:20151215-sunna
				 * @date-author:20150925-sunna:“工号”字段，无使用，隐藏
				 */
				<div class="control-group">
				-->
		            <!--
		            @reqno        :H1509060061
		            @date-designer:20150909-jiangbing
		            @e-in-text    :no:工号
		            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
		            -->
		            <!-- 
		            * @reqno:H1512150148
				 	* @date-designer:20151215-sunna
				 	* @date-author:20150925-sunna:“工号”字段，无使用，隐藏
					<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;工号:</label>
					<div class="controls">
					 -->
						<!--
		                @reqno        :H1508310067
		                @date-designer:20150907-jiangbing
		                @date-author  :20150907-jiangbing:保存修改时的原工号
		                -->
		        <!-- 
		        	* @reqno:H1512150148
				 	* @date-designer:20151215-sunna
				 	* @date-author:20150925-sunna:“工号”字段，无使用，隐藏
					    <input id="oldNo" name="oldNo" type="hidden" value="${user.oldNo}">
						<form:input path="no" htmlEscape="false" maxlength="50" class="required"/>
					</div>
				</div>
				 -->
				<div class="control-group">
		            <!--
		            @reqno        :H1509060061
		            @date-designer:20150909-jiangbing
		            @e-in-text    :name:姓名
		            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
		            -->
					<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;姓名:</label>
					<div class="controls">
					<!-- 
		               /**
			             * @reqno        :H1601040014
			             * @date-designer:20160112-zhangzhida
			             * @e-in-text : name - 姓名: 姓名
			             * @date-author  :20160112-zhangzhida:对页面的组件重新排版布局,使姓名组件长度保持一致.使用bootstrap自带span4
			             **/
		                -->
						<form:input path="name" htmlEscape="false" maxlength="50" class="required span4"/>
					</div>
				</div>
				<div class="control-group">
		            <!--
		            @reqno        :H1509060061
		            @date-designer:20150909-jiangbing
		            @e-in-text    :loginName:登录名
		            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
		            -->
					<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;登录名:</label>
					<div class="controls">
						<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
					<!--
		            @reqno        :H1509070144
		            @date-designer:20150910-sunna
		            @e-in-text    :loginName:登录名
		            @date-author  :20150910-sunna:为登录名增加autocomplete="off"属性，避免火狐浏览器自动填充用户名密码的问题，次页面为添加页面不需要自动填充
		            -->
		            <c:if test="${not empty user.id}">
		           <!-- 
		               /**
			             * @reqno        :H1601040014
			             * @date-designer:20160112-zhangzhida
			             * @e-in-text : loginName - 登录名: 登录名
			             * @date-author  :20160112-zhangzhida:对页面的组件重新排版布局,使登录名组件长度保持一致.使用bootstrap自带span4
			             **/
		                -->
						<form:input path="loginName" disabled="true" autocomplete="off" htmlEscape="false" maxlength="50" class="required userName span4"/>
					</c:if>
					<c:if test="${empty user.id}">
						<form:input path="loginName" autocomplete="off" htmlEscape="false" maxlength="50" class="required userName span4"/>
					</c:if>
					</div>
				</div>
				<div class="control-group">
		            <!--
		            @reqno        :H1509060061
		            @date-designer:20150909-jiangbing
		            @e-in-text    :password:密码
		            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
		            -->
					<label class="control-label"><c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>&nbsp;密码:</label>
					<div class="controls">
					<!-- 
		               /**
			             * @reqno        :H1601040014
			             * @date-designer:20160112-zhangzhida
			             * @e-in-text : newPassword - 密码: 密码
			             * @date-author  :20160112-zhangzhida:对页面的组件重新排版布局,使密码组件长度保持一致.使用bootstrap自带span4
			             **/
		                -->
						<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="6" class="${empty user.id?'required':''} span4"/>
						<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
					</div>
				</div>
				<div class="control-group">
		            <!--
		            @reqno        :H1509060061
		            @date-designer:20150909-jiangbing
		            @e-in-text    :comfirmPassword:确认密码
		            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
		            -->
					<label class="control-label"><c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>&nbsp;确认密码:</label>
					<div class="controls">
			            <!-- 
		               /**
			             * @reqno        :H1601040014
			             * @date-designer:20160112-zhangzhida
			             * @e-in-text : confirmNewPassword - 确认密码: 确认密码
			             * @date-author  :20160112-zhangzhida:对页面的组件重新排版布局,使确认密码组件长度保持一致.使用bootstrap自带span4
			             **/
		                -->
						<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="6" class="${empty user.id?'required':''} span4" equalTo="#newPassword"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">邮箱:</label>
					 <!-- 
		               /**
			             * @reqno        :H1601040014
			             * @date-designer:20160112-zhangzhida
			             * @e-in-text : email - 邮箱: 邮箱
			             * @date-author  :20160112-zhangzhida:对页面的组件重新排版布局,使邮箱组件长度保持一致.使用bootstrap自带span4
			             **/
		                -->
					<div class="controls">
						<form:input path="email" htmlEscape="false" maxlength="100" class="span4"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">电话:</label>
					 <!-- 
		               /**
			             * @reqno        :H1601040014
			             * @date-designer:20160112-zhangzhida
			             * @e-in-text : phone - 电话:电话
			             * @date-author  :20160112-zhangzhida:对页面的组件重新排版布局,使电话组件长度保持一致.使用bootstrap自带span4
			             **/
		                -->
					<div class="controls">
						<form:input path="phone" htmlEscape="false" maxlength="100" class="span4"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">手机:</label>
					 <!-- 
		               /**
			             * @reqno        :H1601040014
			             * @date-designer:20160112-zhangzhida
			             * @e-in-text : mobile - 手机: 手机
			             * @date-author  :20160112-zhangzhida:对页面的组件重新排版布局,使手机组件长度保持一致.使用bootstrap自带span4
			             **/
		                -->
					<div class="controls">
						<form:input path="mobile" htmlEscape="false" maxlength="100" class="span4"/>
					</div>
				</div>
			</td>
			 <!-- 
               /**
	             * @reqno        :H1601040014
	             * @date-designer:20160112-zhangzhida
	             * @e-in-list : roleIdList - 用户角色: 用户角色
	             * @date-author  :20160112-zhangzhida:将页面中的设置用户角色的逻辑移除,挪到用户列表中设置
	             **/
                -->
			 <!--  
			<td width="20%"></td>
			<td align="left" valign="top">
					<div>
		            @reqno        :H1509060061
		            @date-designer:20150909-jiangbing
		            @e-in-text    :roleIdList:用户角色
		            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
					<label><span class="help-inline"><font color="red">*</font></span>&nbsp;用户角色:</label>
					<div>
						<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" element="br" itemValue="id" htmlEscape="false" class="required"/>
					</div>
				</div>
			</td>
		 -->
	</tr>
</table>
		<div class="control-group">
            <!--
            @reqno        :H1509060061
            @date-designer:20150909-jiangbing
            @e-in-text    :loginFlag:是否允许输入
            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
            -->
            <!--
            * @reqno:H1512150148
		 	* @date-designer:20151215-sunna
		 	* @date-author:20150925-sunna:"是否允许登录"修改为是否有效用户
            -->
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;是否有效用户:</label>
			<div class="controls">
			<!-- 
               /**
	             * @reqno        :H1601040014
	             * @date-designer:20160112-zhangzhida
	             * @e-in-text : loginFlag - 是否有效用户: 是否有效用户
	             * @date-author  :20160112-zhangzhida:对页面的组件重新排版布局,使是否有效用户组件长度保持一致.使用bootstrap自带span4
	             **/
                -->
				<form:select path="loginFlag"  class="span4">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
			</div>
		</div>
		<!-- 
			* @reqno:H1512150148
		 	* @date-designer:20151215-sunna
		 	* @date-author:20150925-sunna:隐藏用户类型
		<div class="control-group">
			<label class="control-label">用户类型:</label>
			<div class="controls">
				<form:select path="userType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		 -->
		
		<div class="control-group">
			<label class="control-label">备注:</label>
			<!-- 
               /**
	             * @reqno        :H1601040014
	             * @date-designer:20160112-zhangzhida
	             * @e-in-text : remarks - 备注: 备注
	             * @date-author  :20160112-zhangzhida:对页面的组件重新排版布局,使备注组件长度保持一致.使用bootstrap自带span4
	             **/
                -->
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="span4"/>
			</div>
		</div>
		</c:if>
		<!--
        @reqno        :H1509070147
        @date-designer:20150910-jiangbing
        @e-out-other  :nameImage:头像
        @e-out-other  :company.name:归属机构
        @e-out-other  :office.name:归属部门
        @e-out-other  :no:工号
        @e-out-other  :name:姓名
        @e-out-other  :loginName:登录名
        @e-out-other  :email:邮箱
        @e-out-other  :phone:电话
        @e-out-other  :mobile:手机
        @e-out-other  :loginFlagLabel:是否允许登录
        @e-out-other  :userTypeLabel:用户类型
        @e-out-other  :roleLabel:用户角色
        @e-out-other  :remarks:备注
        @e-ctrl       :c if:isCheck=='1' 查看的情况
        @date-author  :20150910-jiangbing:添加专门用于查看的HTML
        -->
		<c:if test="${isCheck=='1'}">	
		<div class="control-group">
            <label class="control-label">头像:</label>
            <div class="controls">
                <form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
                <sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100" readonly="true"/>
            </div>
        </div>
		<div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;归属机构:</label>
            <div class="controls">
                <label class="lbl area">${user.company.name}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">归属部门:</label>
            <div class="controls">
                <label class="lbl area">${user.office.name}</label>
            </div>
        </div>
        <!-- 
        	* @reqno:H1512150148
		 	* @date-designer:20151215-sunna
		 	* @date-author:20150925-sunna:“工号”字段，无使用，隐藏
        <div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;工号:</label>
            <div class="controls">
                <label class="lbl">${user.no}</label>
            </div>
        </div>
         -->
        <div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;姓名:</label>
            <div class="controls">
                <label class="lbl">${user.name}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;登录名:</label>
            <div class="controls">
                <label class="lbl">${user.loginName}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">邮箱:</label>
            <div class="controls">
                <label class="lbl">${user.email}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">电话:</label>
            <div class="controls">
                <label class="lbl">${user.phone}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">手机:</label>
            <div class="controls">
                <label class="lbl">${user.mobile}</label>
            </div>
        </div>
        <!--
        @reqno        :H1509070148
        @date-designer:20150910-jiangbing
        @e-out-other  :loginFlagLabel:是否允许登录
        @e-out-other  :userTypeLabel:用户类型
        @e-out-other  :roleLabel:用户角色
        @date-author  :20150910-jiangbing:将选择的内容在查看页面表示出来
        -->
        <div class="control-group">
        	<!--
        	* @reqno:H1512150148
		 	* @date-designer:20151215-sunna
		 	* @date-author:20150925-sunna:"是否允许登录"修改为是否有效用户
        	-->
            <label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;是否有效用户:</label>
            <div class="controls">
                <label class="lbl">${user.loginFlagLabel}</label>
                <span class="help-inline lbl">&nbsp;&nbsp;“是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
            </div>
        </div>
        <!-- 
        	* @reqno:H1512150148
		 	* @date-designer:20151215-sunna
		 	* @date-author:20150925-sunna:隐藏用户类型
        <div class="control-group">
            <label class="control-label">用户类型:</label>
            <div class="controls">
                <label class="lbl area">${user.userTypeLabel}</label>
            </div>
        </div> -->
        <div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;用户角色:</label>
            <div class="controls">
                <!-- <form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/> -->
                <label class="lbl area">${user.roleLabel}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">备注:</label>
            <div class="controls">
                <label class="lbl area">${user.remarks}</label>
            </div>
        </div>
		</c:if>
		<c:if test="${not empty user.id}">
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
			<div class="control-group">
				<!-- /**
				 * 
				 * @reqno:H1507070036
				 * @date-designer:20150709-zhangxingyu
				 * @e-out-other :label: 文本
				 * @date-author:20150709-zhangxingyu:将“最后登陆”字样改为：“最近一次登录”
				 */ -->
				<label class="control-label">最近一次登录:</label>
				<div class="controls">
					<label class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="sys:user:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<!--
			 * @reqno: H1507150039
			 * @date-designer:20150717-zhunan
			 * @e-out-other : button - 按钮 : 返回按钮
			 * @date-author:20150717-zhunan: 返回按钮返回功能是通过history.go(-1)形式返回，在当前数据权限下，此种返回在上一个页面时进行查询条件筛选时候会出现找不到页面或权限不够的情况，这些都是由于数据权限问题，但如果指定返回页面则不会存在此问题，所以改成指定返回页面
			-->
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="location='${ctx}/sys/user/list'"/>
			<!--@end-->
		</div>
	</form:form>
</body>
</html>