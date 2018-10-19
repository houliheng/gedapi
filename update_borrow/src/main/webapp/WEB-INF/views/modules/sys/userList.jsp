<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			/**
 			* @reqno: 	H1601040015
 			* @date-designer:20160112-sunna
 			* @date-author:20160112-sunna:修改原有是否有效用户的功能，将下拉框中搜索功能去除；弹出下拉框中显示的页面元素：全部、是、否
 			*/
			$("#type_select").select2({
				minimumResultsForSearch: -1
				/**
	 			* @reqno: 	H1601040015
	 			* @date-designer:20160119-sunna
	 			* @date-author:20160119-sunna:修改原有是否有效用户的功能，解决是否有效用户的选项在点击查询的时候值被更改的问题
	 			*/
				//$("#type_select").val($("#type_select").val()).change();
			});
			//$("#type_select").find("option[value='a']").attr("selected","selected");
			//select2对应的的下拉框设置值后需要调用change事件触发select2值变化
			//$("#type_select").val($("#type_select").val()).change();
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					/**
                     * @reqno:H1509070143
                     * @date-designer:20150914-jiangbing
                     * @date-author:20150914-jiangbing:判断confirm返回值，导出数据内容
                     */
					if(v=="allpage"){//导出当前查询结果的全部数据
						/**
                         * @reqno:H1508270081
                         * @date-designer:20150829-jiangbing
                         * @date-author:20150829-jiangbing:添加lodaing效果 跳转导出处理
                         */
						loading('正在导出，请稍等...');
						$("#searchForm").attr("action","${ctx}/sys/user/export?exportFlag=allpage");
						
						/**
                         * @reqno:H1508270081
                         * @date-designer:20150829-jiangbing
                         * @date-author:20150829-jiangbing:将检索出当前页面内容的条件传入form，避免人为改变检索框内容导致导出内容改变。
                         */
						var newCompanyId = $("#companyId").val();
						var newOfficeId = $("#officeId").val();
						var newLoginName = $("#loginName").val();
						var newName = $("#name").val();
						//检索条件 
						$("#companyId").val("${user.company.id}"); //归属机构
						$("#officeId").val("${user.office.id}");   //归属部门
						$("#loginName").val("${user.loginName}");  //登录名
						$("#name").val("${user.name}");            //姓名
						
						$("#searchForm").submit();
						setTimeout("closeTip();", 4000);//关闭loading

						/**
                         * @reqno:H1508270081
                         * @date-designer:20150829-jiangbing
                         * @date-author:20150829-jiangbing:将检索框内容传入form,使页面的检索框显示最新的检索条件。
                         */
						$("#companyId").val(newCompanyId);
                        $("#officeId").val(newOfficeId);
                        $("#loginName").val(newLoginName);
                        $("#name").val(newName);
					} else if (v=="onepage") {//导出当前查询结果当前页数据
                        loading('正在导出，请稍等...');
                        $("#searchForm").attr("action","${ctx}/sys/user/export?exportFlag=onepage");
                        
                        /**
                         * @reqno:H1508270081
                         * @date-designer:20150829-jiangbing
                         * @date-author:20150829-jiangbing:将检索出当前页面内容的条件传入form，避免人为改变检索框内容导致导出内容改变。
                         */
                        var newCompanyId = $("#companyId").val();
                        var newOfficeId = $("#officeId").val();
                        var newLoginName = $("#loginName").val();
                        var newName = $("#name").val();
                        //检索条件 
                        $("#companyId").val("${user.company.id}"); //归属机构
                        $("#officeId").val("${user.office.id}");   //归属部门
                        $("#loginName").val("${user.loginName}");  //登录名
                        $("#name").val("${user.name}");            //姓名
                        
                        $("#searchForm").submit();
                        setTimeout("closeTip();", 3000);//关闭loading
                        /**
                         * @reqno:H1508270081
                         * @date-designer:20150829-jiangbing
                         * @date-author:20150829-jiangbing:将检索框内容传入form,使页面的检索框显示最新的检索条件。
                         */
                        $("#companyId").val(newCompanyId);
                        $("#officeId").val(newOfficeId);
                        $("#loginName").val(newLoginName);
                        $("#name").val(newName);
					}
					/**
                     * @reqno:H1509070143
                     * @date-designer:20150914-jiangbing
                     * @date-author:20150914-jiangbing:设置confirm按钮及返回值，传入exportFlag
                     */
				},{buttons:{"导出全部":"allpage","导出当页":"onepage","取消":"cancel"}},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
			
			/**
			 * 
			 * @reqno:H1601040014
			 * @date-designer:2016年01月12日-zhangzhida
			 * @date-author:2016年01月12日-zhangzhida:表单提交的验证事件
			 */
			$("#userForm").validate({
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
		/*
		* @reqno: H1507030028
		* @date-designer:2015年7月2日-daijun
		* @date-author:2015年7月2日-daijun:分页添加提示窗口，如果输入数据长度超过10位需要提示
		*/
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/user/list");
			if($("#pageNo")[0].value.length>10){
				top.$.jBox.tip('当前页码大小长度不能大于10位！');
				return true;
			}
			else if($("#pageSize")[0].value.length>10){
				top.$.jBox.tip('每页条数大小的长度不能大于10位！');
				return true
			}else{
				$("#searchForm").submit();
		    	return false;
			}
	    }
	    
		/**
		 * 
		 * @reqno:H1601040014
		 * @date-designer:2016年01月12日-zhangzhida
		 * @date-author:2016年01月12日-zhangzhida:点击用户角色按钮,将用户角色设置的弹出窗体中的内容情况.设置用户编号隐藏域的值
		 */
		function showUserDialog(userId){
			 selectedRoleArray = new Array();
			$("#userBody").empty();
			$("#userId").val(userId);
			$.ajax({
				type: "POST",
				url: "${ctx}/sys/user/roleList",
			   	data:{"id": userId},
			   	datatype: 'json',
			   	success: function(result){
			   		/**
					 * 
					 * @reqno:H1601040014
					 * @date-designer:2016年01月12日-zhangzhida
					 * @date-author:2016年01月12日-zhangzhida:通过ajax请求获取该用户的拥有的角色信息.并获取所有的角色信息.设置部门以及归属机构的隐藏域的值
					 										遍历角色信息列表.并判断用户是否包含此用户.如包含则将checkbox默认选中
					 */
			   		var roleList = result.allRoles;
			   		var userRoles = result.user.roleNames.split(",");
			   		var roleHtml = "";
			   		$("#userCompanyId").val(result.companyId);
			   		$("#userOfficeId").val(result.officeId);
			   		for(var i = 0;i< roleList.length;i++){
			   			var role = roleList[i];
			   			var index = $.inArray(role.name,userRoles);
			   			if(index != -1){
			   				roleHtml += "<input id='roleIdList1' class='required' type='checkbox' value='"+role.id+"' name='roleIdList' onclick='onCheckBoxClick(\""+role.id+"\");' checked><label for='roleIdList1'>"+role.name+"</label><br>";
			   				selectedRoleArray.push(role.id);
			   			}else{
			   				roleHtml += "<input id='roleIdList1' class='required' type='checkbox' value='"+role.id+"' name='roleIdList' onclick='onCheckBoxClick(\""+role.id+"\");'><label for='roleIdList1'>"+role.name+"</label><br>";
			   			}
			   		}
			   		$("#userBody").append(roleHtml);
			   	}
			});
			$("#userDialog").modal("show");
		}
		
		/**
		 * 
		 * @reqno:H1601040014
		 * @date-designer:2016年01月12日-zhangzhida
		 * @date-author:2016年01月12日-zhangzhida:存储选中的用户角色信息.根据vlaue返回索引值
		 */
		var selectedRoleArray = new Array();
		function getIndexOfArray(value){
	  		var index = -1;
	  		for(var i=0;i<selectedRoleArray.length;i++){
				if(selectedRoleArray[i] == value){
					index = i;
				}
			}
	  		return index;
	  	}
		
		/**
		 * 
		 * @reqno:H1601040014
		 * @date-designer:2016年01月12日-zhangzhida
		 * @date-author:2016年01月12日-zhangzhida:用户角色多选框的点击时间,获取数据中的索引,如果包含就删除
		 */
		function onCheckBoxClick(code){
			var index = getIndexOfArray(code) ;
			if(index == -1){
				selectedRoleArray.push(code);
			}else{
				selectedRoleArray.splice(index,1);
			}
		}
		
		/**
		 * 
		 * @reqno:H1601040014
		 * @date-designer:2016年01月12日-zhangzhida
		 * @date-author:2016年01月12日-zhangzhida:表单提交事件.判断数组长度.如果为0.则提示"请选择用户角色"
		 */
		function validateForm(){
			if(selectedRoleArray.length != 0){
				$("#userForm").submit();
			}else{
				top.$.jBox.tip("请选择用户角色");
			}
		}
		
			/**
		 * 
		 * @reqno:H1601040014
		 * @date-designer:2016年01月12日-zhaohuakui
		 * @date-author:2016年01月12日-zhaohuakui:点击用户群组按钮,将用户角色设置的弹出窗体中的内容情况.设置用户编号隐藏域的值
		 */
		function showGroupDialog(userId){
		 	var width = $(window).width() - 100;
	   		var height = $(window).height()-50;
			openJBox("UserGroup","${ctx}/credit/userGroup/list","群组管理",width,height,{userId:userId});
		}
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/sys/user/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/list">用户列表</a></li>
		<shiro:hasPermission name="sys:user:edit"><li><a href="${ctx}/sys/user/form">用户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<!--
			 @reqno        :H1511230055
			 @date-designer:20151123-lvhaishan
			 @e-in-text    :office:归属机构
			 @date-author  :20151123-lvhaishan:把用户管理页面中的“公司”字样改为“机构”
			-->
			<li><label>归属机构：</label><sys:usertreeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}" 
				title="机构" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/></li>
		 	<input id="oldCompanyId"  type="hidden" value="${user.company.id}"/>
			<li><label>登录名：</label><form:input path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<!-- 
				/**
	 			* @reqno:H1512150151
	 			* @date-designer:20151217-sunna
	 			* @date-author:20151217-sunna:用户管理查询条件增加是否有效用户。“有效用户”改为“是否有效用户”，并增加style="word-break:keep-all"，保证文字不换行。
	 			*/
			 -->
			<li>
				<label style="word-break:keep-all">是否有效用户： </label>
				<!--
				/**
	 			* @reqno: 	H1601040015
	 			* @date-designer:20160112-sunna
	 			* @date-author:20160112-sunna:修改原有是否有效用户的功能，将下拉框中搜索功能去除；弹出下拉框中显示的页面元素：全部、是、否
	 			*/
				-->
				&nbsp;
				<form:select path="loginFlag" style="width:88px" id="type_select">
					<form:option value="" htmlEscape="false">全部 </form:option>
    				<form:option value="1" htmlEscape="false">是 </form:option>
    				<form:option value="0" htmlEscape="false">否 </form:option>
				</form:select>
			</li>
			
			<li class="clearfix"></li>
			<!-- /**
				 * 
				 * @reqno: H1508040026
				 * @date-designer:20150805-zhangxingyu
				 * @e-tree : office - sys:treeselect : 引入的ztree树结构标签
				 * @date-author:20150805-zhangxingyu:判断当user.company.id有不为空是，修改url属性 添加一个参数&companyId=${user.company.id}
				 * 										将sys:treeselect标签改为新定义的sys:usertreeselect标签
				 */ -->
			<li><label>归属部门：</label>
			<c:if test="${not empty user.company.id}">
			<sys:usertreeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}" 
				title="部门" url="/sys/office/treeData?type=2&companyId=${user.company.id}" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</c:if>
			<c:if test="${empty user.company.id}">
			<sys:usertreeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}" 
				title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</c:if>
			</li>
			<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<!-- 
				/**
	 			* @reqno:H1512150151
	 			* @date-designer:20151217-sunna
	 			* @date-author:20151217-sunna:用户管理列表增加是否有效用户的展示
	 			*/
	-->
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
		<thead><tr><th>归属机构</th><th>归属部门</th><th class="sort-column login_name">登录名</th><th class="sort-column name">姓名</th><th>是否有效用户</th><th>电话</th><th>手机</th><%--<th>角色</th> --%><shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td>${user.company.name}</td>
				<td>${user.office.name}</td>
				<!-- 	/**
					 * 
					 * @reqno: 	H1506250120
					 * @date-designer:20150630-zhangxingyu
					 * 
					 * @date-author:20150630-zhangxingyu:加一个请求参数isCheck=1用来标记该请求是查看
					 */ -->
				<td><a href="${ctx}/sys/user/form?id=${user.id}&isCheck=1">${user.loginName}</a></td>
				
				<td>${user.name}</td>
				<!-- 
				/**
	 			* @reqno:H1512150151
	 			* @date-designer:20151217-sunna
	 			* @date-author:20151217-sunna:用户管理查询条件增加是否有效用户。1显示为“是”，0显示为“否”
	 			*/
			 	-->
				<c:if test="${user.loginFlag=='1'}">
					<td>是</td>
				</c:if>
				<c:if test="${user.loginFlag=='0'}">
					<td>否</td>
				</c:if>
				
				<td>${user.phone}</td>
				<td>${user.mobile}</td><%--
				<td>${user.roleNames}</td> --%>
				<shiro:hasPermission name="sys:user:edit"><td>
					<!-- 
					 /**
					   * @reqno:H1511260120
					   * @date-designer:2015年11月30日-hanmeng
					   * @date-author:2015年11月30日-hanmeng:集成统门户
					   */
					-->
					<c:if test="${fns:getConfig('openOlatform')=='yes'}">
					<shiro:hasPermission name="oa:moduleResource:assign">
						<a href="${ctx}/oa/moduleResource/assign?userId=${user.id}">分配组件</a>
					</shiro:hasPermission>
					</c:if>
    				<a href="${ctx}/sys/user/form?id=${user.id}">修改</a>
					<a href="${ctx}/sys/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
					<!--
					/**
		 			 * 
		 			 * @reqno:H1601040014
		 			 * @date-designer:2016年01月12日-zhangzhida
					 * @e-ctrl : userButton - 用户角色 : 用户角色
		 			 * @date-author:2015年01月12日-zhangzhida:在用户信息列表界面增加用户角色控制按钮.点击按钮弹出功能信息.提供增删改操作
		 			 */
					  -->
					<a href="#" id="userButton" onclick="showUserDialog('${user.id}')">用户角色</a>
					<a href="#" id="userButton" onclick="showGroupDialog('${user.id}')">用户群组</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<!--
	/**
		 * 
		 * @reqno:H1512210067
		 * @date-designer:2015年12月16日-zhangzhida
		 * @e-in-text : userId - 用户编号 : 用户编号
		 * @e-in-text : userCompanyId - 部门编号 : 区域编号
		 * @e-in-text : userOfficeId - 机构编号 : 机构编号
		 * @e-ctrl : submit - 确定 : 确定
		 * @e-ctrl : button - 返回 : 返回
		 * @db-z : sys_user : 用户信息表
		 * @date-author:2015年12月16日-zhangzhida:设置用户角色的窗体.当点击用户角色时对该用户所拥有的角色进行增删改操作
		 */
	  -->
	<div  class="modal fade" id="userDialog" tabindex="-1" role="dialog" aria-labelledby="123" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form action="${ctx}/sys/user/save" id="userForm" method="post" >
					<input type="hidden" name="id" id="userId">
					<input type="hidden" name="company.id" id="userCompanyId">
					<input type="hidden" name="office.id" id="userOfficeId">
					<div class="modal-header">
						<h4 class="modal-title"  id="roleTitle">用户角色</h4>
					</div>
					<div class="modal-body" id="userBody">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" onclick="validateForm();">确定</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>