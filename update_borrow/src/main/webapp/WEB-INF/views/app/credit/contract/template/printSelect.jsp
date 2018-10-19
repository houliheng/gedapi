<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同模板管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
		function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function printWord(){
		var contractNo = "${contractNo}";
		var applyNo = "${applyNo}";
		var str = getCheckBoxVal();
		if(str!=""){
			if(str.indexOf(",")!=str.length-1){
				alertx("请不要选择多条对应的流程任务");
			}else{
				window.location.href="${ctx}/credit/contractTemplate/printWord?contractNo=" + contractNo + "&&applyNo=" + applyNo+"&&id="+str;
// 		 url = "${ctx}/credit/contractTemplate/printWord?contractNo=" + contractNo + "&&applyNo=" + applyNo+"&&id="+str;
// 				$.post(url,null,function(data){
// 					if (data=="true"){
// 					alertx("成功");
// 					}else{
// 					alertx("失败");
// 					}
// 				});
			} 
		}else{
			alertx("请选择对应的流程任务");
		}
 	}
	/*获取选择框 */
	function getCheckBoxVal(){
	 	var str = "";
		$("input[name=type]:checkbox").each(function(){
			if($(this).attr("checked")){
		    str += $(this).val() + ",";
			}
		});
		return str;
	}
		function resetForm() {
		$("#templateType").select2("val", "");
		$("#productType").select2("val", "");
		$("#orgplatform").select2("val", "");
		$('#templateType').val('');
		$('#productType').val('');
		$('#templateName').val('');
		$('#orgplatform').val('');
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="contractTemplate"  method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">模板类型：</td>
								<td class="ft_content">
									<form:select id="templateType" name="templateType" path="templateType" class="input-medium">
										<form:option value="" label="--请选择--" />
										<form:options items="${fns:getDictList('CONTRACT_TEMPLATE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td class="ft_label">归属地：</td>
								<td class="ft_content">
									<form:select id="orgplatform" name="orgplatform" path="orgplatform" class="input-medium">
										<form:option value="" label="--请选择--" />
										<form:options items="${fns:getDictList('ORG_PLATFORM')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<%-- <td class="ft_label">产品类型：</td>
								<td class="ft_content">
									<form:select id="productType" name="productType" path="productType" class="input-medium">
										<form:option value="" label="--请选择--" />
										<form:options items="${fns:getDictList('PRODUCT_TYPE_CONTRACT')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td> --%>
								</tr>
								<tr style="height: 4px">
								</tr>
							<tr>
								<td class="ft_label">模板名称：</td>
								<td class="ft_content"><input name="templateName"
									id="templateName" type="text" maxlength="50"
									class="input-medium" value="${contractTemplate.templateName}" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="resetForm();" />
							&nbsp;
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="ribbon">
			<ul class="layout">
				<li class="delete">
				 <a href="#" onclick="printWord()" id="printWord"><span><b></b>打印</span></a> 
				</li>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height: 400px; overflow: auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th width="5%">
							<input id="all" name="all" type="checkbox" onclick="allCheck();" />
						</th>
						<th width="22%">模板类型</th>
						<!-- <th width="10%">产品类型</th> -->
						<th width="40%">模板名称</th>
						
					</tr>
					<c:forEach items="${page.list}" var="ctl" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td id="id">
							<input name="type" type="checkbox" value="${ctl.id}" />
						</td>
						<td class="title" id="templateType">${ctl.templateType}</td>
						<%-- <td class="title" id="productType">${ctl.productType}</td> --%>
						<td class="title">${ctl.templateName}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>