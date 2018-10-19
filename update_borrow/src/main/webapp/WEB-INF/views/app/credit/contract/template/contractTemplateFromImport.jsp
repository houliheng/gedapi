<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<!--
	**
	 * @reqno: aH1509130050
	 * @date-designer:20150916-lirongchao
	 * @e-out-other : templateType - 输出组件 : 模板类型
	 * @e-out-other : productType - 输出组件 : 产品类型
	 * @e-out-other : templateName - 输出组件 : 模板名称
	 * @e-out-other : uploadFile - 输出组件 : 模板文件
	 * @e-ctrl		: btnImportSubmit	- 上传:上传
	 * @e-ctrl		: btnSubmit	- 关闭:关闭
	 * @e-in-other		: uploadFile	- 文件地址:
	 * @date-author:20150917-lirongchao: 合同模板管理上传
	 *
-->
<head>
	<title>合同模板上传</title>
	<meta name="decorator" content="default" />
	<script type="text/javascript">
		window.name="spare";
		$(document).ready(function() {
			$("#inputForm").validate({
				rules: {
				},
				submitHandler: function(form){
					loading('正在上传，请稍等...');
					form.submit();
					//window.close();
				}
			});
		});
	</script>
</head>
<body>
	<div class="wrapper">
		<div class="infoList">
			<h3 class="infoListTitle">合同模板上传</h3>
	        <div class="infoListCon"> 
				<form:form id="inputForm" action="${ctx}/credit/contractTemplate/import?id=${contractTemplate.id}" method="post" enctype="multipart/form-data" target="spare">
					<input type="hidden" id="templateTypeSearch" name="templateTypeSearch" value="${templateTypeSearch}"/>
					<input type="hidden" id="productTypeSearch" name="productTypeSearch" value="${productTypeSearch}"/>
					<input type="hidden" id="orgplatformSearch" name="orgplatformSearch" value="${orgplatformSearch}"/>
					<input type="hidden" id="templateNameSearch" name="templateNameSearch" value="${templateNameSearch}"/>
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td width="20%" class="ft_label">模板类型:</td>
								<td width="80%" class="ft_content">
									<input id="templateType" name="templateType" type="text" value="${contractTemplate.templateType}" readonly="readonly" class="input-medium"/>
								</td>
							</tr>
							<tr>
								<td class="ft_label">产品类型:</td>
								<td class="ft_content">
									<input id="productType" value="${contractTemplate.productType}" readonly="readonly" name="productType" type="text" class="input-medium">
								</td>
							</tr>
							<tr>
								<td class="ft_label">模板名称:</td>
								<td class="ft_content">
									<input id="templateName" value="${contractTemplate.templateName}" readonly="readonly" name="templateName" type="text" class="input-medium">
								</td>
							</tr>
							<tr>
								<td class="ft_label">模板文件:</td>
								<td class="ft_content">
									<input id="uploadFile" name="file" type="file"  class="required input-medium"/>
								</td>
							</tr>
							<tr>
								<td class="ft_label">是否联合授信:</td>
								<td class="ft_content">
									<select name="isUnion" style="width:176px;">
										<option value='1' selected="selected">是</option>
										<option value='0' >否</option>
									</select>
								</td>
							</tr>							
						</table>
						<div class="searchButton">
							<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="上 传 "/>&nbsp;
							<input id="btnSubmit" class="btn btn-primary" type="button" onclick="closeJBox();" value="关闭"/>
						</div>
					</div>
				</form:form>
				<sys:message content="${message}" />
	        </div>
	    </div>
	</div>		
	<c:if test="${not empty close}">
	<script type="text/javascript">
		  var templateNameSearchImport = '${templateNameSearchImport}';
		  templateNameSearchImport = encodeX(templateNameSearchImport);
		  parent.location.href="${ctx}/credit/contractTemplate/list?templateType=" + '${templateTypeSearch}' +"&productType=" + '${productTypeSearch}' +"&orgplatform=" + '${orgplatformSearch}' +"&templateNameSearch=" + templateNameSearchImport
		  closeJBox();
	</script>
</c:if>
</body>
</html>