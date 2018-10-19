<%@ page contentType="text/html;charset=UTF-8"%>
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
	<div class="tableList">
			<h3 class="tableTitle">网查信息列表</h3>
			<div id="tableDataId" style="max-height:300px;overflow:auto;">
				<table id="contentTable" cellpadding="0" cellspacing="0" border="0" width="100%">
					<thead>
						<tr>
							<th width="30px">序号</th>
							<th>核查时间</th>
							<th>核查人</th>
							<th>核查对象</th>
							<th>姓名</th>
							<th>移动电话</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${checkWebList}" var="checkWeb" varStatus="i">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="checkDate" class="title" title="${checkWeb.checkDate}">
									<fmt:formatDate value="${checkWeb.checkDate}"
										pattern="yyyy-MM-dd" />
								</td>
								<td id="checkUserName" class="title"
									title="${checkWeb.checkUserName}">
									${checkWeb.checkUserName}</td>
								<td id="roleType" class="title" title="${checkWeb.roleType}">
										${fns:getDictLabel(checkWeb.roleType, 'ROLE_TYPE', '')}</td>
								<td id="custName" class="title" title="${checkWeb.custName}">
									${checkWeb.custName}</td>
								<td id="mobileNum" class="title" title="${checkWeb.mobileNum}">
									${checkWeb.mobileNum}</td>
								<td>
									<a href="javascript:void(0);"  onclick="add('${ctx}/credit/checkWeb/form?readOnly=true&id=${checkWeb.id}','查看网查信息','${checkWeb.custName}','${checkWeb.roleType}','${checkWeb.applyNo}');">详情</a>
								</td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
