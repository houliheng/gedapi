<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@page isELIgnored="false" %>
<%@include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>错误信息</title>
</head>
<body>
	<div >
		<div align="center" style="width: 100%;height: 100%;">
			<h1 style="padding-top: 100px">${error}</h1>
		</div>
	</div>
</body>
</html>
<!-- 
			 * @reqno:H1512250042
			 * @date-designer:20151229-lirongchao
			 * @date-author:20151229-lirongchao:监测指标报送-数据填报-指标导入功能，导入文件大于10M时，页面报错
			 * Any spring mvc配置异常统一处理，上传文件过大，spring mvc异常报错MaxUploadSizeExceededException
			 * Ans spring mvc统一配置异常MaxUploadSizeExceededException处理，提示用户导入文件过大
 -->