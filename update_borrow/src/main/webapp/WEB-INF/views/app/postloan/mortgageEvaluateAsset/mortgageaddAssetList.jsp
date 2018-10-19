<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资产评估管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
 	//新增
	function addSave(applyNo,id){
	 var width = $(window).width() - 200;
	   	var height = $(window).height()-80;
		openJBox('contactBoxAseet',"${ctx}/postloan/mortgageEvaluateAsset/addSave","新增资产",width,height,{applyNo:applyNo,id:id});
	}
	     //编辑
     function showDetail(applyNo,id){
        var width = $(window).width() - 100;
	   	var height = $(window).height()-50;
		openJBox('editAseet',"${ctx}/postloan/mortgageEvaluateAsset/edit","编辑基本信息",width,height,{applyNo:applyNo,id:id});
	}
	//修改备注
	function revice(applyNoRe,id){
	 var width = $(window).width() - 100;
	   	var height = $(window).height()-80; 
	   	var heightTrue=Math.min(height,320);
		openJBox(null,"${ctx}/postloan/mortgageEvaluateAsset/reviceAsset","修改备注",width,heightTrue,{applyNo:applyNoRe,id:id});
	} 
	</script>
</head>
<body>
	<div class="wrapper">
		<div>
			<input id="addSave"class="btn btn-primary" onclick="addSave('${mortgageEvaluateAsset.applyNo}','${mortgageEvaluateAsset.id}')" type="button" value="新增"/>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="AddAssetSelect" class="table table-striped table-bordered table-condensed">
					<thead>
							<tr>
						<th width="20">序号</th>
						<th width="10%">资产名称</th>
						<th width="10%">账面价值</th>
						<th width="10%">评估价值</th>
						<th width="10%">增减值</th>
						<th width="10%">增值率</th>
						<th width="10%">备注</th>
						<th width="15%">是否追加数据</th>
							<shiro:hasPermission name="postloan:mortgageEvaluateInfo:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					</thead>
					<tbody> 
					<c:forEach items="${page.list}" var="mortgageEvaluateAsset" varStatus="i">
						<tr>
							<td class="title" title="${i.index+1}" >
								${i.index+1}
							</td>
						 <td class="title" title="${mortgageEvaluateAsset.assetName}">${mortgageEvaluateAsset.assetName}</td>
						 <td class="title" title="${mortgageEvaluateAsset.bookValue}"><fmt:formatNumber value="${mortgageEvaluateAsset.bookValue}" pattern="###,##0.00"></fmt:formatNumber></td>
						 <td class="title" title="${mortgageEvaluateAsset.evaluatePrice}"><fmt:formatNumber value="${mortgageEvaluateAsset.evaluatePrice}" pattern="###,##0.00"></fmt:formatNumber></td>
						 <td class="title" title="${mortgageEvaluateAsset.moreOrLessValue}"><fmt:formatNumber value="${mortgageEvaluateAsset.moreOrLessValue}" pattern="###,##0.00"></fmt:formatNumber></td>
						 <td class="title" title="${mortgageEvaluateAsset.moreOrLessRate}"><fmt:formatNumber value="${mortgageEvaluateAsset.moreOrLessRate}" pattern="###,##0.00"></fmt:formatNumber></td>
						 <td class="title" title="${mortgageEvaluateAsset.remarks}">${mortgageEvaluateAsset.remarks}</td>
						  <td id="isPushData" class="title" title="${fns:getDictLabel(mortgageEvaluateAsset.isPushData, 'yes_no', '')}">${fns:getDictLabel(mortgageEvaluateAsset.isPushData, 'yes_no', '')}</td>
						 	<td>	
						 		<a href="javascript:void(0)" onclick="showDetail('${mortgageEvaluateAsset.applyNo}','${mortgageEvaluateAsset.id}')">编辑</a>
						 	<a href="javascript:void(0)" onclick="revice('${mortgageEvaluateAsset.applyNo}','${mortgageEvaluateAsset.id}')">修改备注</a>
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