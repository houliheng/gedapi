<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>银行卡管理</title>
<script type="text/javascript">
	$(document).ready(function() {
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="tableList">
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>
								<input type="checkbox" name="topNum" disabled="disabled" />
							</th>
							<th>姓名</th>
							<th>银行卡号</th>
							<th>开户银行</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="index">
							<tr>
								<td>
									<input type="checkbox" name="num" bank="${index.bank}" custId="${index.custId}" bankcardNo="${index.bankcardNo}" value="${index.custName}" onclick="selectSingle('num');" />
								</td>
								<td id="custName" class="title" title="${index.custName}">${index.custName}</td>
								<td id="bankcardNo" class="title" title="${index.bankcardNo}">${index.bankcardNo}</td>
								<td id="bank" class="title" title="${fns:getDictLabel(index.bank, 'BANKS', '')}">${fns:getDictLabel(index.bank, 'BANKS', '')}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>