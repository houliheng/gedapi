<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>用户信息</title>
<meta name="decorator" content="default" />

</head>
<body>

	<form:form id="inputForm" modelAttribute="user"
		action="${ctx}/sys/user/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		 <!--
    @reqno:H1507080068
    @date-designer:20150717-laiwenchao
    @@e-out-table : usermessage - umname : 此为一个七行两列的表格，为了方便页面的布局，把用户信息放进表格的行或列当中，对用户信息进行直观的展现。
    @date-author:20150717-laiwenchao:点击系统管理->角色管理->分配，后弹出用户信息的新窗口，窗口内容显示用户的基本信息，内容包括：
    1）头像,2）归属机构，3）归属部门，4）工号，5）姓名，6）登录名，7）邮箱，8）电话，9）手机，10）是否允许登录，10）用户类型，11）备注，12）创建时间，13）最后登陆信息。
    -->  
    <table id="usermessage" name="umname">  
    <tr>
    <td colspan="2"><div class="control-group" style="margin: auto;">
			<label class="control-label">头像:</label>
			<div class="controls" >
				<!-- /**
				 * 
				 * @reqno:H1507080068
				 * @date-designer:20150721-zhangxingyu
				 * @e-out-char : img : 输出图片
				 * @date-author:20150721-zhangxingyu:修改图片路径， 设置图片大小
				 */ -->
				<img class="nav-user-photo" src="${user.photo}" width="100" height="100"/> 	
			</div>
		</div></td>  
    </tr>
    
	<!--
	 @reqno        :H1511230055
	 @date-designer:20151123-lvhaishan
	 @date-author  :20151123-lvhaishan:把用户管理页面中的“公司”字样改为“机构”
	-->
    <tr>
    <td><div class="control-group">
			<label class="control-label">归属机构:</label>
			<div class="controls">
			<label class="lbl">${user.company.name}</label>					
			</div>
		</div></td>
    <td><div class="control-group">
			<label class="control-label">归属部门:</label>
			<div class="controls">
			<label class="lbl">${user.office.name}</label>				
			</div>
		</div></td>   
    </tr>
    
    <tr>
    <!-- 
    		* @reqno:H1512150148
		 	* @date-designer:20151215-sunna
		 	* @date-author:20150925-sunna:隐蔽工号
    <td><div class="control-group">
			<label class="control-label">工号:</label>
			<div class="controls">
			<label class="lbl">${user.no }</label>
		</div>
		</div></td>
	 -->
    <td><div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
			<label class="lbl">${user.name }</label>
			</div>
		</div></td>   
    </tr>   
    <tr>
    <td><div class="control-group">
			<label class="control-label">登录名:</label>
			<div class="controls">
			<label class="lbl">${user.loginName}</label>
			</div>
		</div></td>
    <td><div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
			<label class="lbl">${user.email }</label>				
			</div>
		</div></td>   
    </tr> 
    <tr>
    <td><div class="control-group">
			<label class="control-label">电话:</label>
			
			<div class="controls">
			<label class="lbl">${user.phone }</label>				
			</div>
		</div></td>
    <td><div class="control-group">
			<label class="control-label">手机:</label>		
			<div class="controls">
				<label class="lbl">${user.mobile}</label>
			</div>
		</div></td>   
    </tr>  
    <tr>
    <td><div class="control-group">
    		<!--
    		* @reqno:H1512150148
		 	* @date-designer:20151215-sunna
		 	* @date-author:20150925-sunna:"是否允许登录"修改为是否有效用户
    		-->
			<label class="control-label">是否有效用户:</label>
			<div class="controls">
			<c:if test="${user.loginFlag=='1' }">		
			<label class="lbl">是</label>
			</c:if>
			<c:if test="${user.loginFlag=='0' }">		
			<label class="lbl">否</label>
			</c:if>		
			</div>
		</div></td>
		<!-- 
			* @reqno:H1512150148
		 	* @date-designer:20151215-sunna
		 	* @date-author:20150925-sunna:隐藏用户类型
    <td><div class="control-group">
			<label class="control-label">用户类型:</label>
			<div class="controls">
			<label class="lbl">${user.userType }</label>			
			</div>
		</div></td>  
		 --> 
    </tr>    
    <tr>
    <td colspan="2"><div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
			<label class="lbl">${user.remarks }</label>				
			</div>
		</div></td>   
    </tr>   
    <tr>

    <td  colspan="2"><div class="control-group">
			<label class="control-label">创建时间:</label>
			<div class="controls">
				<label class="lbl">
				<!--
			    @reqno:H1507080068
			    @date-designer:20150721-zhangxingyu  
			    @e-out-other : ID编号 - fmt:formatDate : 显示时间标签
			    @date-author:20150721-zhangxingyu:改用fmt:formatDate标签来显示时间，以便统一系统中所有时间的显示格式
			    -->
					<fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/>
				</label>					
			</div>
		</div></td>
    </tr> 
    
    <tr>
    <td><div class="control-group">
			<label class="control-label">最后登录IP:</label>
			<div class="controls">			
				<label class="lbl">
					${user.loginIp}
				</label>
			</div>
		</div></td>
    <td><div class="control-group">
			<label class="control-label">最后登录时间:</label>
			<div class="controls">			
				<label class="lbl">
					<!--
			    @reqno:H1507080068
			    @date-designer:20150721-zhangxingyu   
			    @e-out-other : ID编号 - fmt:formatDate : 显示时间标签
			    @date-author:20150721-zhangxingyu:改用fmt:formatDate标签来显示时间，以便统一系统中所有时间的显示格式
			    -->
					<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/>
				</label>
			</div>
		</div></td>
    </tr>

    </table>	
	</form:form>
</body>
</html>