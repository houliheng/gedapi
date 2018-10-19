<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<!--
用户控制栏
-->
<ul id="userControl" class="nav pull-right">
	
	<li id="themeSwitch" class="dropdown">
		<a class="dropdown-toggle" data-toggle="dropdown" href="#" title="主题切换"><i class="icon-th-large"></i></a>
		<ul class="dropdown-menu">
			<c:forEach items="${fns:getDictList('theme')}" var="dict">
				<li><a href="#" onclick="location='${pageContext.request.contextPath}/a?theme=${dict.value}'">${dict.label}</a></li>
			</c:forEach>
		</ul>
		<!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
	</li>
	<li id="userInfo" class="dropdown">
		<a class="dropdown-toggle" data-toggle="dropdown" href="#" title="个人信息">您好, ${fns:getUser().name}&nbsp;<span id="notifyNum" class="label label-info hide"></span></a>
		<ul class="dropdown-menu">
			<li><a href="${ctx}/sys/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp; 个人信息</a></li>
			<li><a href="${ctx}/sys/user/modifyPwd" target="mainFrame"><i class="icon-lock"></i>&nbsp;  修改密码</a></li>
			<li><a href="${ctx}/oa/oaNotify/self" target="mainFrame"><i class="icon-bell"></i>&nbsp;  我的通知 <span id="notifyNum2" class="label label-info hide"></span></a></li>
		</ul>
	</li>
	<li><a href="${ctx}/logout" title="退出登录">退出</a></li>
	<li>&nbsp;</li>
</ul>
