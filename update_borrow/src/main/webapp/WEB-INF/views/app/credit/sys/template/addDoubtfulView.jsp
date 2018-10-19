<!--
* @reqno:H1509230028
* @date-designer:2015年9月24日-songmin
* @date-author:2015年9月24日-songmin:添加最新统一样式
-->
<!-- 
* @reqno:H1511100082
* @date-designer:2015年11月09日-songmin
* @date-author:2015年11月09日-songmin:模板名称放宽点号的正则校验
-->
<!--  
@reqno:H1512140102
@date-designer:20151215-songmin
@date-author:20151215-songmin:系统页面布局分辨率优化_在低分辨率下，部分表格、表单显示错位，统一调整优化
-->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>实地调查模板配置信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	/**
	 * @reqno:AH1509130057
	 * @date-designer:20151020-huangxuecheng
	 * @date-author:20151020-huangxuecheng:系统管理-存疑调查模板配置信息页面，增加js方法，先进行resetTip对存于session的信息进行回显，然后取出validate的校验，全部使用
	 * 原生的js校验
	 */
		$(document).ready(function() {
			resetTip();
		});
		function submitForm(){
			//已存在的产品类型
					var isTemplate = "${str}";
					//验证，如果已存在的产品类型不允许新增
					if($("#productType").val()==''&$("#templateName").val()==''){
						$("#msg").text("必填选项");
						$("#meg").text("必填选项");
						return false;
					}
					if($("#productType").val()==''){
						$("#msg").text("必填选项");
						if($("#templateName").val()!=''){
						$("#meg").text("");
						}
						return false;
					}else{
						$("#msg").text("");
					}
					if($("#templateName").val()==''){
						$("#meg").text("必填选项");
						return false;
					}else{
					    $("#meg").text("");
					}
					if(isTemplate.indexOf($("#productType").val())>-1){
						$("#msg").text("已存在"+$("#productType").find("option:selected").text());
						return false;
					}
					var regex = /^[a-zA-Z0-9_.\u4E00-\u9FA5-]+$/;
					if(!$("#templateName").val().match(regex)){
					$("#meg").text("无效特殊字符");
						return false;
					}
					var productType = $("#productType").val();
					var productTypeName = $("#productTypeName").val();
					var templateName = $("#templateName").val();
					var fileType = $("#fileType").val();
					/**
					 * @reqno: H1511300033
					 * @date-designer:20151202-lirongchao
					 * @date-author:20151202-lirongchao:1.点击CRE_信贷审批_系统管理_实地调查模板配置 菜单；进入实地调查模板配置页面；
					 2.点击页面“新增”按钮或“修改”链接，弹出新增或修改页面；
					 3.填写相应表单信息，点击“保存”按钮，页面没反应，无任务操作
					 当前环节-删除return事件
					 */
					$.ajax({
						type:"post",
						url:"${ctx}/credit/sys/template/saveDoubtful?productType=" + productType + "&productTypeName=" + productTypeName + "&fileType=" + fileType,
						data:{"templateName":templateName},
						dataType:"json",
						success:function(data){
						if(data.result=="success"){
				 			window.parent.returnValue = 1;
				 			window.close();
				 		}else{
				 			alert(data.mes);
				 		}	 
				 	   
						},
						error:function(msg){
								alert("未能新增，请查看后台信息");
						}
					});
		
		}
	</script>
</head>
<body>
	<!-- 
	/**
	 * @reqno: AH1509130057
	 * @date-designer:2015年9月20日-wujing01
	 * @date-author:2015年9月20日-wujing01:新增某一产品类型的模板信息(断定产品类型的唯一性)
	 */
	-->
	<!-- 
	 @reqno:AH1509130057
	 @date-designer:20151020-huangxuecheng
	 @date-author:20151020-huangxuecheng:系统管理-存疑调查模板配置信息页面，修改以前留下的问题，增加productType隐藏回显，改变以前的submit按钮改成button后使用js的ajax提交而不是form提交
	 @e-in-text:productTypeName-产品类型名称:产品类型名称
	 @e-in-list:productType-产品类型:产品类型
	 @e-in-text:templateName-模板名称:模板名称
	 @e-ctrl:submitButton-提交按钮:提交按钮
	 @e-ctrl:btnSubmit-返回按钮:返回按钮	 
	-->
	
	<!-- 
		 @reqno: H1510280084
		 @date-designer:20151030-gengchang
		 @date-author:20151030-gengchang:CRE_信贷审批_系统管理_实地调查模板配置_页面中“存疑调查”字样改为“实地调查”
	 -->
	<div class="wrapper">
		<div class="infoList">
			<c:if test="${not empty updateTemplate}">
<!-- 	    		<h3 class="infoListTitle">修改存疑调查模板配置信息</h3> -->
	    		<h3 class="infoListTitle">修改实地调查模板配置信息</h3>
			</c:if>
			<c:if test="${empty updateTemplate}">
<!-- 	    		<h3 class="infoListTitle">新增存疑调查模板配置信息</h3> -->
	    		<h3 class="infoListTitle">新增实地调查模板配置信息</h3>
	    	</c:if>
	        <div class="infoListCon">
				<form:form id="inputForm" modelAttribute="template" action="${ctx}/credit/sys/template/saveDoubtful" method="post">
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td width="20%" class="ft_label">产品类型:</td>
								<td width="80%" class="ft_content">
									<c:if test="${not empty updateTemplate}">
										<input id="productTypeName" name="productTypeName" type="text" value="${updateTemplate.productTypeName}" readonly="readonly" class="input-medium"/>
										<input id="productType" name="productType" type="hidden" value="${updateTemplate.productType}">
									</c:if>
									<c:if test="${empty updateTemplate}">
										<input id="productTypeName" name="productTypeName" type="hidden" value="${updateTemplate.productTypeName}" class="input-medium"/>	
										<form:select id="productType" name="productType" path="productType" class="required input-medium" value="${product.productType}" cssStyle="width:178px;">
											<form:option value="" label="" />
											<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
										</form:select>
										<span class="help-inline"><font color="red" id="msg">*</font> </span>
									</c:if>
								</td>
							</tr>
							<tr>
								<td class="ft_label">模板名称:</td>
								<td class="ft_content">
									<input id="templateName" name="templateName" type="text" value="${updateTemplate.templateName}" maxlength="50" class="required input-medium" />
									<input id="fileType" name="fileType" value="1" type="hidden">
									<span class="help-inline"><font color="red" id="meg">*</font> </span>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="button" onclick="javascript:submitForm();" value="保 存" />&nbsp;
							<input id="btnSubmit" class="btn btn-primary" type="button" onclick="javascript:window.close();" value="关闭"/>
						</div>
					</div>
				</form:form>
	        </div>
	    </div>
	</div>
</body>
</html>