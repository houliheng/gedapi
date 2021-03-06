<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<!-- /**
	 * 
	 * @reqno:H1506240030
	 * @date-designer:20150629-zhangxingyu
	 * 
	 * 
	 * @date-author:20150629-zhangxingyu:将文件中的引用的参数名productName用copyright.productName替换
	 */ -->
	<title>${fns:getConfig('copyright.productName')} 登录</title>
	<meta name="decorator" content="blank"/>
	<style type="text/css">
html,body,table {
	background-color: #f5f5f5;
	width: 100%;
	text-align: center;
}

.form-signin-heading {
	font-family: Helvetica, Georgia, Arial, sans-serif, 黑体;
	font-size: 36px;
	margin-bottom: 20px;
	color: #0663a2;
}

.form-signin {
	position: relative;
	text-align: left;
	width: 300px;
	padding: 25px 29px 29px;
	margin: 0 auto 20px;
	background-color: #fff;
	border: 1px solid #e5e5e5;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.form-signin .checkbox {
	margin-bottom: 10px;
	color: #0663a2;
}

.form-signin .input-label {
	font-size: 16px;
	line-height: 23px;
	color: #999;
}

.form-signin .input-block-level {
	font-size: 16px;
	height: auto;
	margin-bottom: 15px;
	padding: 7px;
	*width: 283px;
	*padding-bottom: 0;
	_padding: 7px 7px 9px 7px;
}

.form-signin .btn.btn-large {
	font-size: 16px;
}

.form-signin #themeSwitch {
	position: absolute;
	right: 15px;
	bottom: 10px;
}

.form-signin div.validateCode {
	padding-bottom: 15px;
}

.mid {
	vertical-align: middle;
}

.header {
	height: 80px;
	padding-top: 20px;
}

.alert {
	position: relative;
	width: 300px;
	margin: 0 auto;
	*padding-bottom: 0px;
}

label.error {
	background: none;
	width: 270px;
	font-weight: normal;
	color: inherit;
	margin: 0;
}
</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</head>
<body>
	<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您升级到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
	<div class="header">
		<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error">${message}</label>
		</div>
	</div>
	<h1 class="form-signin-heading">${fns:getConfig('copyright.productName')}</h1>
	<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
		<label class="input-label" for="username">登录名</label>
		<!--
		 * @reqno: H1506250026
		 * @date-designer:20150626-zhunan
		 * @e-in-text : username - 文本输入框 : 输入用户名
		 * @e-in-text : password - 文本输入框 : 输入密码
		 * @date-author:20150626-zhunan: 去掉默认用户名和密码，原因由于不同的库默认的用户名和密码可能不正确，为了避免歧义，去掉默认用户名密码
		-->
		<input type="text" id="username" name="username" class="input-block-level required" value="${fns:getConfig('sec.defaultLoginName')}">
		<label class="input-label" for="password">密码</label>
		<input type="password" id="password" name="password" class="input-block-level required" value="${fns:getConfig('sec.defaultLoginPwd')}">
		<!--@end-->
		<c:if test="${isValidateCodeLogin}"><div class="validateCode">
			<label class="input-label mid" for="validateCode">验证码</label>
			<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
		</div></c:if><%--
		<label for="mobile" title="手机登录"><input type="checkbox" id="mobileLogin" name="mobileLogin" ${mobileLogin ? 'checked' : ''}/></label> --%>
		<input class="btn btn-large btn-primary" type="submit" value="登 录"/>&nbsp;&nbsp;
		<label for="rememberMe" title="下次不需要再登录"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住我（公共场所慎用）</label>
		<!--
		 * @reqno: H1507150049
		 * @date-designer:20150716-zhunan
		 * @e-out-other : themeSwitch - 下拉选择框 : 选择不同主题
		 * @date-author:20150716-zhunan: 登陆页面的主题选择模块去掉，此处无用，切换主题可以登陆进去后进行切换，不需要再此处进行
		-->
<!-- 		<div id="themeSwitch" class="dropdown"> -->
<!-- 			<a class="dropdown-toggle" data-toggle="dropdown" href="#">${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}<b class="caret"></b></a> -->
<!-- 			<ul class="dropdown-menu"> -->
<!-- 			  <c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href">${dict.label}</a></li></c:forEach> -->
<!-- 			</ul> -->
			<!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
<!-- 		</div> -->
	</form>
	<div class="footer">
		<!--
		 @reqno        :H1511230062
		 @date-designer:20151123-lvhaishan
		 @date-author  :20151123-lvhaishan:去掉“Powered By JeeSite”无意义的标题内容
			1.将Resoft放在配置文件中
			2.将第一个2005放在配置文件中
		  	3.去掉“GDS基础平台”外的a标签
		-->
		Copyright &copy; ${fns:getConfig('copyright.beginYear')}-${fns:getConfig('copyright.endYear')} ${fns:getConfig('copyright.productName')} <a href="${fns:getConfig('copyright.companyURL')}" target="_blank">${fns:getConfig('copyright.companyName')}</a> ${fns:getConfig('copyright.version')} 
	</div>
	<!-- 
	 * @reqno: H1506250117
	 * @date-designer:20150627-zhunan
	 * @date-author:20150627-zhunan: 去掉此JS，会提示安装flash插件，暂时不需要
	
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>-->
</body>
</html>