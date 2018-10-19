<!--
* @reqno:H1509230028
* @date-designer:2015年9月24日-songmin
* @date-author:2015年9月24日-songmin:添加最新统一样式
-->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>实地调查模板上传</title>
	<meta name="decorator" content="default1"/>
	<script type="text/javascript">
			window.name="spare";
		$(document).ready(function() {
			resetTip();
			$("#inputForm").validate({
				rules: {
				},
				submitHandler: function(form){
				
					var maxSize = 1*1024*1024;
					var errorMsg="上传的文件大小不能大于1M";
					var tipMsg = "你的浏览器暂时不支持计算文件的大小,为了确保上传文件不要大于2M,建议使用IE、火狐、Chrome浏览器";
					var browserCfg = {};
					var ua = window.navigator.userAgent;
					if(ua.indexOf("MSIE")>=1){
						browserCfg.ie = true;
					}else if(ua.indexOf("Firefox")>=1){
						browserCfg.firefox = true;
					}
					var objFile = document.getElementById("uploadFile");
					var fileSize = 0;
					if(browserCfg.firefox){
						fileSize = objFile.files[0].size;
					}else if(browserCfg.ie){
						/* var objImg = document.getElementById("temping");
						objImg.dynsrc = objFile.value;
						fileSize = objImg.fileSize; */
					}else{
					alert(tipMsg);
					}
					if(fileSize==-1){
						alert(tipMsg);
						return;
					}else if(fileSize>maxSize){
						alert(errorMsg);
						return;
					}
				
					loading('正在上传，请稍等...');
					form.submit();
					
				}
			});
		});
	/*
		function formSubmit(){
					var uploadFile = $("#uploadFile").val();
					if(uploadFile==''){
						$("#meg").text("必填选项");
						return false;
					}else{
					    $("#meg").text("");
					}
					var productTypeName = $("#productTypeName").val();
					var templateName = $("#templateName").val();
					var uploadFile = $("#uploadFile").val();
					$.ajax({
						type:"post",
						url:"${ctx}/credit/sys/template/upload?fileType=2&id=${template.id}&productTypeName=" + productTypeName + "&templateName=" + templateName + "&uploadFile=" + uploadFile,
						dataType:"json",
						success:function(data){
						if(data.result=="success"){
				 			window.parent.returnValue = 3;
				 			window.close();
				 		}else{
				 			alert(data.mes);
				 		}	 
				 	   
						},
						error:function(msg){
								alert("未能上传，请查看后台信息");
						}
					});
		
		}
		*/
	</script>
</head>
<body>
	<!--
	/**
	* @reqno: H1509130058
	* @date-designer:2015年9月20日-wujing01
	* @date-author:2015年9月20日-wujing01:根据模块ID产品资料模板文件上传发布
	*/
	-->
	<!-- 
		 @reqno: H1510280084
		 @date-designer:20151030-gengchang
		 @date-author:20151030-gengchang:CRE_信贷审批_系统管理_实地调查模板配置_页面中“存疑调查”字样改为“实地调查”
	 -->
	<div class="wrapper">
		<div class="infoList">
<!-- 			<h3 class="infoListTitle">存疑调查模板上传</h3> -->
			<h3 class="infoListTitle">实地调查模板上传</h3>
	        <div class="infoListCon">
	        <!-- 
				 * @reqno: 	H1512250042
				 * @date-designer:2015年12月28日-lirongchao
				 * @e-ctrl : btnSubmit -查询: 查询
				 * @date-author:2015年12月28日-lirongchao:1）当上传超大文件时，给出友好提示,不让上传；
												2）修改分页并查询时，按当前设置的分页进行刷新，不要恢复初始状态，重置时才恢复初始状态，与系统其他界面的分页功能保持一致；;
												        
	         -->
				<form:form id="inputForm" action="${ctx}/credit/sys/template/upload?fileType=1&id=${template.id}" method="post" enctype="multipart/form-data" target="spare">
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
									<img id="temping" dynsrc="" src="" style="display:none" />
									<input id="uploadFile" name="file" type="file"  class="required input-medium"/>
									<span class="help-inline"><font color="red" id="meg">*</font> </span>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="上 传 "/>&nbsp;
							<input id="btnSubmit" class="btn btn-primary" type="button" onclick="javascript:window.close();" value="关闭"/>
						</div>
					</div>
				</form:form>
				<sys:message content="${message}" />
	        </div>
	    </div>
	</div>
</body>
</html>