<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!--  
@reqno:H1512140102
@date-designer:20151215-songmin
@date-author:20151215-songmin:系统页面布局分辨率优化_在低分辨率下，部分表格、表单显示错位，统一调整优化
-->
<html>
<head>
<title>表单栏目修改</title>
<meta name="decorator" content="default" />
<base target="_self"/>
    <!--  
	@reqno:H1510170008
	@date-designer:20151022-huangxuecheng
	@date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单栏目配置_修改
	@e-in-list:dataTableName-栏目名称:栏目名称
	@e-in-list:showPosition-栏目类型:栏目类型
	@e-ctrl:btnSubmit-查询:查询
	@e-ctrl:btnReset-重置:重置
    -->
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
	//dataTableNameValidate positionValidate sortValidate
	 $(document).ready(function() {
		$("#searchForm").validate({
				rules: {
				},
				messages: {
				},
				submitHandler: function(form){
					loading("正在提交，请稍等...");
					var tableColumnId = $("#tableColumnId").val();
					var dataGroupValue = $("#dataGroupValue").val();
					//dataGroupLabel = encodeX(dataGroupLabel);
					var dataTableName = $("#dataTableName").val();
					var showPosition = $("#showPosition").val();
					var sort = $("#sort").val();
					$.ajax({
						type:"post",
						url:"${ctx}/sys/dynamicTable/saveDynamicTable?id=" + tableColumnId + "&dataGroup=" + dataGroupValue + "&dataTableName=" + dataTableName + "&showPosition=" + showPosition + "&sort=" + sort,
						dataType:"json",
						success:function(data){
					if(data.result=="success"){
				 		window.parent.returnValue = 1;
				 		window.close();
				 	}else{
				 		alert(data.result);
				 	}	 
				 	},
					error:function(msg){
						alert("未能保存，请查看后台信息");
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
			<h3 class="searchTitle">表单栏目修改</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="creDataGroupTable" action="${ctx}/sys/dynamicTable/saveDynamicTable" method="post"> 
					<input  type="hidden" id="tableColumnId" class="btn btn-primary" value="${creDataGroupTable.id}" /> 
					<input  type="hidden" id="dataGroup" class="btn btn-primary" value="${creDataGroupTable.dataGroup }"  /> 
					<input  type="hidden" id="dataGroupValue" class="btn btn-primary" value="${creDataGroupTable.dataGroupValue }"  /> 
					<div class="filter">
	<!--  
	@reqno:H1511300032
	@date-designer:20151202-huangxuecheng
	@date-author:20151202-huangxuecheng:开发原因：修改不好看的样式问题。CRE_信贷审批_系统管理_表单栏目配置_修改功能，当后台数据库名不是“mcp”时，"物理表名"对应的下拉列表框无法把数据库中的表名加载出来
    -->
						<table class="fromTable" width="650">
							<tr>
								<td class="ft_label">栏目名称：</td>
								<td class="ft_content">
								<%-- <form:input id="dataGroupLabel" name="dataGroupLabel" path="dataGroupLabel" htmlEscape="false" maxlength="50"  style="width:150px" readonly="readonly"  value="${creDataGroupTable.dataGroupLabel }" /> --%>
								<input id="dataGroupLabel" name="dataGroupLabel" type="text" value="${creDataGroupTable.dataGroupLabel }" readonly="readonly" class="input-medium"/>
								</td>
							</tr>
							<tr>
								<td class="ft_label" >物理表名：</td>
									<td class="ft_content">
											<form:select id="dataTableName" name="dataTableName" path="dataTableName" style="width:350px" class="input-large" value="${creDataGroupTable.dataTableName }" cssClass="required">
											 	 <c:if test="${creDataGroupTable.dataTableName == null}">
											 	 		<form:option value="" label="--请选择--" />
											 	 </c:if>
											 	  <c:if test="${creDataGroupTable.dataTableName != null}">
											 	 		 <%-- <form:option value="${creDataGroupTable.dataTableName }" label="${creDataGroupTable.tableComment } -- ${creDataGroupTable.dataTableName }" /> --%>
											 	 </c:if>
											 	 
											     <c:forEach items="${creDataGroupTable.tableList }" var="tableMap">
													<c:forEach items="${tableMap }" var="entry">
														<form:option value="${entry.value }" label="${entry.key } -- ${entry.value }" />
													</c:forEach>
												</c:forEach>	
											</form:select>
											<span class="help-inline"><font color="red" id="dataTableNameValidate">*</font> </span>
									</td>
							</tr>
							<tr>
								<td class="ft_label">栏目类型：</td>
									<td class="ft_content">
										<form:select id="showPosition" name="showPosition" path="showPosition" style="width:178px" class="input-medium" value="${creDataGroupTable.showPosition }" cssClass="required">
											<form:option value="" label="--请选择--" />
											<form:options items="${fns:getDictList('SHOW_POSITION')}" itemLabel="label" itemValue="value" htmlEscape="false" />
										</form:select>
										<span class="help-inline"><font color="red" id="positionValidate">*</font> </span>
									</td>
							</tr>
							<tr>
								<td class="ft_label">序号：</td>
								<td class="ft_content">
								<form:input id="sort" name="sort" path="sort" htmlEscape="false" maxlength="5"  style="width:50px" value="${creDataGroupTable.sort }"  cssClass="required newDigits"/>
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
