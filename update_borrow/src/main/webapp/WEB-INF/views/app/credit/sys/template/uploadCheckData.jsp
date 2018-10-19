<!--
* @reqno:H1509230028
* @date-designer:2015年9月24日-songmin
* @date-author:2015年9月24日-songmin:添加最新统一样式
-->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资料核查模板上传</title>
	<meta name="decorator" content="default1"/>
	<script type="text/javascript">
		window.name="spare";
		$(document).ready(function() {
			$("#inputForm").validate({
				rules: {
				},
				submitHandler: function(form){
					loading('正在上传，请稍等...');
					form.submit();
				}
			});
		});
		
	</script>
</head>
<body>
	<!--
	/**
	* @reqno: H1509130054
	* @date-designer:2015年9月20日-wujing01
	* @date-author:2015年9月20日-wujing01:根据模块ID产品资料模板文件上传发布
	*/
	-->
	<!--
	/**
	 * @reqno:AH1509130053
	 * @date-designer:20151020-huangxuecheng
	 * @date-author:20151020-huangxuecheng:系统管理-资料核查模板配置信息页面，增加js方法，先进行resetTip对存于session的信息进行回显，然后取出validate的校验，全部使用
	 * 原生的js校验
	 */
	 -->
	<div class="wrapper">
		<div class="infoList">
			<h3 class="infoListTitle">资料核查模板上传</h3>
	        <div class="infoListCon">
				<form:form id="inputForm" action="${ctx}/credit/sys/template/upload?fileType=2&id=${template.id}" method="post" enctype="multipart/form-data" target="spare">
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td width="20%" class="ft_label">产品类型:</td>
								<td width="80%" class="ft_content">
									<input id="productTypeName" name="productTypeName" type="text" value="${template.productTypeName}" readonly="readonly" class="input-medium"/>
								</td>
							</tr>
							<tr>
								<td class="ft_label">模板名称:</td>
								<td class="ft_content">
									<input id="templateName" value="${template.templateName}" readonly="readonly" name="templateName" type="text" class="input-medium">
								</td>
							</tr>
							<tr>
								<td class="ft_label">模板文件:</td>
								<td class="ft_content">
									<input id="uploadFile" name="file" type="file"  class="required input-medium"/>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="上 传 "/>&nbsp;
							<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="javascript:window.close();" value="关闭"/>
						</div>
					</div>
				</form:form>
				<sys:message content="${message}" />
	        </div>
	    </div>
	</div>
</body>
</html>