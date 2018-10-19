<%@ page contentType="text/html;charset=UTF-8" %>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
	<div class="wrapper">
		<sys:message content="${message}"/>
		<div class="tableList" >
			<h3 class="tableTitle">两人外访信息列表</h3>
			<div id="tableDataId" style="max-height:300px;overflow:auto;">
				<table id="contentTable" cellpadding="0" cellspacing="0" border="0" width="100%">
					<thead>
						<tr>
						<th width="30px">序号</th>
							<th>外访对象</th>
							<th>姓名</th>
							<th>外访类型</th>
							<th>外访地点</th>
							<th>外访时间</th>
							<th>外访人</th>
							<th>外访意见</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${checkCoupleDoubtfulList}" var="checkCoupleDoubtful" varStatus="i">
						<tr>
						<!-- 列表中添加序号 -->
						<td id="num" class="title" title="序号">
								${i.index+1}
							</td>
							<td id="roleType" class="title" title="${checkCoupleDoubtful.roleType}">
								${fns:getDictLabel(checkCoupleDoubtful.roleType, 'ROLE_TYPE', '')}
							</td>
							<td id="custName" class="title" title="${checkCoupleDoubtful.custName}">
								${checkCoupleDoubtful.custName}
							</td>
							<td id="checkType" class="title" title="${checkCoupleDoubtful.checkType}">
								${fns:getDictLabel(checkCoupleDoubtful.checkType, 'CHECK_TYPE', '')}
							</td>
							<td id="checkAddress" class="title" title="${checkCoupleDoubtful.checkAddress}">
								${checkCoupleDoubtful.checkAddress}
							</td>
							<td id="checkDate" class="title" title="${checkCoupleDoubtful.checkDate}">
								<fmt:formatDate value="${checkCoupleDoubtful.checkDate}"
										pattern="yyyy-MM-dd" />
								</td>
							<td id="checkUserName" class="title" title="${checkCoupleDoubtful.checkUserName}">
								${checkCoupleDoubtful.checkUserName}
							</td>
							<td id="description" class="title" title="${checkCoupleDoubtful.description}">
								${checkCoupleDoubtful.description}
							</td>
							<td>
							<a href="javascript:void(0);" onclick="add('${ctx}/credit/checkCoupleDoubtful/form?readOnly=true&id=${checkCoupleDoubtful.id}','查看两人外访信息','${checkCoupleDoubtful.custName}','${checkCoupleDoubtful.roleType}','${checkCoupleDoubtful.applyNo}');">详情</a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
