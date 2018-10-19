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
			<h3 class="tableTitle">电话核查信息列表</h3>
			<div id="tableDataId" style="max-height:300px;overflow:auto;">
				<table id="contentTable" cellpadding="0" cellspacing="0" border="0" width="100%">
					<thead>
						<tr>
							<th width="30px">序号</th>
							<th>拨打时间</th>
							<th>拨打人</th>
							<th>核查对象</th>
							<th>姓名</th>
							<th>移动电话</th>
							<th>异常风险点</th>
							<th>电核详情</th>
							<th>操作</th>
							
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${checkPhoneList}" var="checkPhone" varStatus="i">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="dialTime" class="title" title="${checkPhone.dialTime}">
									<fmt:formatDate value="${checkPhone.dialTime}"
										pattern="yyyy-MM-dd"/>
								</td>
								<td id="checkUserName" class="title"
									title="${checkPhone.checkUserName}">
									${checkPhone.checkUserName}</td>
								<td id="roleType" class="title" title="${checkPhone.roleType}">
								${fns:getDictLabel(checkPhone.roleType, 'ROLE_TYPE', '')}</td>
								<td id="custName" class="title" title="${checkPhone.custName}">
									${checkPhone.custName}</td>
								<td id="mobileNum" class="title" title="${checkPhone.mobileNum}">
									${checkPhone.mobileNum}</td>
								<td id="riskPoint" class="title" title="${fns:getDictLabels(checkPhone.riskPoint, 'CUST_RISK_POINT', '')}">
									${fns:getDictLabels(checkPhone.riskPoint, 'CUST_RISK_POINT', '')}</td>
								<td id="description" class="title"
									title="${checkPhone.description}">
									${checkPhone.description}</td>
					      	    <td><a href="javascript:void(0);"
							onclick="add('${ctx}/credit/checkPhone/form?readOnly=true&id=${checkPhone.id}','查看电话核查信息','${checkPhone.custName}','${checkPhone.roleType}','${checkPhone.applyNo}');">详情</a></td>
					</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>