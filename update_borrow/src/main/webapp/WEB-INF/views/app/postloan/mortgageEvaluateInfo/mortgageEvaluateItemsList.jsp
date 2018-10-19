  <%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>检查项目</title>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					loading();
						var param = $("#inputForm").serializeJson();
					$.post("${ctx}/postloan/mortgageEvaluateInfo/save", param, function(data) {
						closeTip();
						if (data.status == '1') {
							alertx("基本信息保存成功", function() {
							parent.$.loadDiv("mortgageEvaluateItemsList", "${ctx}/postloan/mortgageEvaluateInfo/editInfo", {
								applyNo : '${mortgageHouseInfo.applyNo}'
							}, "post");
							closeJBox();
							});
						}else{
							alertx("基本信息保存失败");
						}
					});
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
						checkReq(error,element);
				}
			});
		});
	//编辑
	</script>
</head>
<body>
	<div id="tableDataCheck" class="tableList">
			<h3 class="tableTitle">检查项目</h3>
			<div id="tableDataCheck">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
						<th width="20">序号</th>
						<th width="40%">检查项目</th>
						<th width="20%">检查结果</th>
						<th width="25%">备注</th>
							<shiro:hasPermission name="postloan:mortgageEvaluateInfo:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${mortgageItemsList}" var="mortgageItems" varStatus="i" >
						<tr>
							<td class="title" title="${i.index+1}">
								${i.index+1}
							</td>
						<td class="title" title="${fns:getDictLabel(mortgageItems.checkItems, 'CHECK_ITEMS', '')}">${fns:getDictLabel(mortgageItems.checkItems, 'CHECK_ITEMS', '')}</td>
						 <td class="title" title="${fns:getDictLabel(mortgageItems.checkValue, 'yes_no', '')}">${fns:getDictLabel(mortgageItems.checkValue, 'yes_no', '')}</td>
						 <td class="title" title="${mortgageItems.description}">${mortgageItems.description}</td>
							<td>	
						 	<a href="javascript:void(0)" onclick="showEdit('${mortgageItems.id}')">编辑</a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html> 