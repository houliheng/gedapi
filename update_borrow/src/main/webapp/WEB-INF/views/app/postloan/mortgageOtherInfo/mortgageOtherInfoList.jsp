<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>其他抵质押物信息管理</title>
<!-- <meta name="decorator" content="default"/> -->
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function mainBorrow2() {
		$("#mainBorrowOtherId").toggle(600);
	}
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 onclick="mainBorrow2()" class="tableTitle">其他抵质押物数据列表</h3>
			<div id="mainBorrowOtherId">
				<div class="ribbon">
					<ul class="layout">
						<li class="add">
							<a href="#" onclick="add('${ctx}/credit/mortgageOtherInfo/form','新增其它抵质押物信息');">
								<span>
									<b></b>
									新增
								</span>
							</a>
						</li>
						<li class="edit">
							<a id="edit" href="javascript:void(0)" onclick="edit('otherType','${ctx}/credit/mortgageOtherInfo/form','编辑其它抵质押物信息');">
								<span>
									<b></b>
									编辑
								</span>
							</a>
						</li>
						<li class="delete">
							<a id="delete" href="#" onclick="del('otherType','${ctx}/credit/mortgageOtherInfo/batchDelete','other','${ctx }/credit/mortgageOtherInfo')">
								<span>
									<b></b>
									删除
								</span>
							</a>
						</li>
					</ul>
				</div>
				<div id="tableDataId" style="max-height:300px;overflow:auto;">
					<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover" style="height:10%">
						<thead>
							<tr>
								<th width="20px;">
									<input type="checkbox" onclick="allCheck('other','otherType');" name="other" id="other">
								</th>
								<th width="20px">序号</th>
								<th>资产名称</th>
								<th>备注</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${mortgageOtherInfoList}" var="mortgageOtherInfo" varStatus="i">
								<tr>
									<td width="20px">
										<input type="checkbox" value="${mortgageOtherInfo.id}" name="otherType">
									</td>
									<td id="num" class="title" title="序号">${i.index+1}</td>
									<td id="assetsName" class="title" title="${mortgageOtherInfo.assetsName}">
										${mortgageOtherInfo.assetsName}
										</a>
									</td>
									<td id="description" class="title" title="${mortgageOtherInfo.description}">${mortgageOtherInfo.description}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
