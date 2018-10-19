<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同模板管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	
	function printWord(){
		var $checkLine = $("input[name='type']:checked");
		window.location.href="${ctx}/postloan/checkTwentyFive/printGuarantyContract?id=" + $checkLine.val();
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
</script>
</head>
<body>
	<div class="wrapper">
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
						<th width="5%"></th>
						<th width="22%">模板类型</th>
						<th width="10%">产品类型</th>
						<th width="40%">模板名称</th>
						
					</tr>
					<c:forEach items="${page.list}" var="guarantyContract" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td id="id">
							<input name="type" type="checkbox" value="${guarantyContract.id}" onclick="selectSingle('type')"/>
						</td>
						<td class="title" id="templateType">${fns:getDictLabel(guarantyContract.templateType, 'CONTRACT_TEMPLATE_TYPE', '')}</td>
						<td class="title" id="productType">${fns:getDictLabel(guarantyContract.productType, 'PRODUCT_TYPE', '')}</td>
						<td class="title">${guarantyContract.templateName}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>