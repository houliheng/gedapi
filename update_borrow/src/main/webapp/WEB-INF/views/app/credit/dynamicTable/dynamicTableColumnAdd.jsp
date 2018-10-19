<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!--  
@reqno:H1512140102
@date-designer:20151215-songmin
@date-author:20151215-songmin:系统页面布局分辨率优化_在低分辨率下，部分表格、表单显示错位，统一调整优化
-->
<html>
<head>
<c:if test="${creProFromColumn.id == null}">
	<title>表单数据项新增</title>
</c:if>
<c:if test="${creProFromColumn.id != null}">
	<title>表单数据项修改</title>
</c:if>
    <!--  
	@reqno:H1510170011
	@date-designer:20151022-huangxuecheng
	@date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单数据项配置_数据项配置新增
	@e-ctrl:btnSubmit-保存:保存
	@e-ctrl:btnClose-关闭:关闭
	@e-in-list:columnCode-数据项列名:数据项列名
	@e-in-list:isRequired-是否必填:是否必填
    -->   
    <!--  
	@reqno:H1510170012
	@date-designer:20151022-huangxuecheng
	@date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单数据项配置_数据项配置修改
	@e-ctrl:btnSubmit-保存:保存
	@e-ctrl:btnClose-关闭:关闭
	@e-in-list:columnCode-数据项列名:数据项列名
	@e-in-list:isRequired-是否必填:是否必填
    -->
    
<meta name="decorator" content="default" />
<base target="_self"/>
<script type="text/javascript">
	function fromClose(){
	 window.close();
	}
	function selectAll() {
		if ($('[name=all]:checkbox').attr("checked") == "checked") {
			$('[name=pcheck]:checkbox').attr("checked", true);
		} else {
			$('[name=pcheck]:checkbox').attr("checked", false);
		}
	}
	/**
	 * @reqno:H1511050008
	 * @date-designer:20151109-huangxuecheng
	 * @date-author:20151109-huangxuecheng:本需求返回的result中，需要将alert改为alertx，在返回的alert窗口中需要符合系统当前框架使用alertx
	 */
	 $(document).ready(function() {
		$("#searchForm").validate({
				rules: {
				},
				messages: {
				},
				submitHandler: function(form){
					//loading("正在提交，请稍等...");
					var columnCode = $("#columnCode").val();
					var columnName = $("#columnName").val();
					var regex = /^[a-zA-Z0-9_\u4E00-\u9FA5-]+$/;
					if(!$("#columnName").val().match(regex)){
					$("#meg").text("无效特殊字符");
						return false;
					}
					var id = $("#tableColumnId").val();
					//dataGroupLabel = encodeX(dataGroupLabel);
					var columnLength = $("#columnLength").val();
					var isRequired = $("#isRequired").val();
					var productType = $("#productType").val();
					var dataGroupId = $("#dataGroupId").val();
					var columnCodeOld = $("#columnCodeOld").val();
					var sort = $("#sort").val();
					$.ajax({
						type:"post",
						url:"${ctx}/sys/dynamicTableColumn/saveTableColumn?columnCode=" + columnCode + "&columnLength=" + columnLength + "&isRequired=" + isRequired + "&sort=" + sort + "&productType=" + productType + "&dataGroupId=" + dataGroupId +"&id=" + id +"&columnCodeOld="+columnCodeOld,
						dataType:"json",
						data:{"columnName":columnName},
						success:function(data){
					if(data.result=="success"){
				 		window.returnValue = data.reval;
				 		window.close();
				 	}else{
				 		alertx(data.result);
				 		resetTip();
				 	}	 
				 	},
					error:function(msg){
						alert("未能保存，请查看后台信息");
						resetTip();
						}
					});
				} 
			});
		}); 
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<c:if test="${creProFromColumn.id == null}">
				<h3 class="searchTitle">表单数据项新增</h3>
			</c:if>
			<c:if test="${creProFromColumn.id != null}">
				<h3 class="searchTitle">表单数据项修改</h3>
			</c:if>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="creProFromColumn" action="${ctx}/sys/dynamicTable/saveTableColumn" method="post"> 
					<input  type="hidden" id="tableColumnId" class="btn btn-primary" value="${creProFromColumn.id}" /> 
					<input  type="hidden" id="productType" class="btn btn-primary" value="${creProFromColumn.productType }"  /> 
					<input  type="hidden" id="dataGroupId" class="btn btn-primary" value="${creProFromColumn.dataGroupId }"  /> 
					<input  type="hidden" id="columnCodeOld" class="btn btn-primary" value="${creProFromColumn.columnCode }"  /> 
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td class="ft_label">数据项列名：</td>
									<td  class="ft_content" >
											<form:select id="columnCode" name="columnCode" path="columnCode" style="width:220px" class="input-large" value="${creProFromColumn.columnCode }" cssClass="required">
											 	 <c:if test="${creProFromColumn.columnCode == null}">
											 	 		<form:option value="" label="--请选择--" />
											 	 </c:if>
											 	 <c:if test="${creProFromColumn.columnCode != null}">					
											 	 </c:if>
											     <c:forEach items="${creProFromColumn.columnNameList }" var="tableList" varStatus="id"> 
													<form:option value="${tableList }" label="${tableList }" />
												</c:forEach>	
											</form:select>
											<span class="help-inline"><font color="red" id="dataTableNameValidate">*</font> </span>
									</td>
							</tr>
							<tr>
								<td class="ft_label">数据项名称：</td>
								<td class="ft_content" >
								<%-- <form:input id="columnName" name="columnName" path="columnName" htmlEscape="false" maxlength="100"  style="width:130px" value="${creProFromColumn.columnName }"  cssClass="required noLegalInput"/> --%>
								<form:input id="columnName" name="columnName" path="columnName" htmlEscape="false" maxlength="100"  value="${creProFromColumn.columnName }"  cssClass="required noLegalInput"/>
									<span class="help-inline"><font color="red" id="meg">*</font> </span>
								</td>
							</tr>
							<%-- <tr>
								<td class="ft_label">数据项名称：</td>
									<td class="ft_content">
											<form:select id="columnName" name="columnName" path="columnName" style="width:170px" class="input-large" value="${creProFromColumn.columnName }" cssClass="required">
											 	 <c:if test="${creProFromColumn.columnName == null}">
											 	 		<form:option value="" label="--请选择--" />
											 	 </c:if>
											 	  <c:if test="${creProFromColumn.columnName != null}">
											 	 		 <form:option value="${creProFromColumn.dataTableName }" label="${creProFromColumn.tableComment } -- ${creProFromColumn.dataTableName }" />
											 	 </c:if>
											 	 
											     <c:forEach items="${creProFromColumn.columnCommentList }" var="tableList" > 
													<form:option value="${tableList }" label="${tableList }" />
												</c:forEach>	
											</form:select>
											<span class="help-inline"><font color="red" id="dataTableNameValidate">*</font> </span>
									</td>
							</tr> --%>
							<tr>
								<td class="ft_label">是否必填项：</td>
									<td class="ft_content">
										<form:select id="isRequired" name="isRequired" path="isRequired" style="width:220px" class="input-medium" value="${creProFromColumn.isRequired }" cssClass="required">
											<form:option value="" label="--请选择--" />
											<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
										</form:select>
										<span class="help-inline"><font color="red" id="positionValidate">*</font> </span>
									</td>
							</tr>
							<tr>
								<td class="ft_label">最大输入长度：</td>
									<td class="ft_content">
										<form:input id="columnLength" name="columnLength" path="columnLength"  htmlEscape="false" maxlength="5"  value="${creProFromColumn.columnLength }"  cssClass="required newDigits"/>
										<span class="help-inline"><font color="red" id="sortValidate">*</font> </span>
									</td>
							</tr>
							<tr>
								<td class="ft_label">序号：</td>
									<td class="ft_content">
										<form:input id="sort" name="sort" path="sort" htmlEscape="false" maxlength="5"  value="${creProFromColumn.sort }"  cssClass="required newDigits"/>
										<span class="help-inline"><font color="red" id="sortValidate">*</font> </span>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存"/>&nbsp; 
							<input id="btnClose" class="btn btn-primary" type="button" value="关闭" onclick="return fromClose();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
