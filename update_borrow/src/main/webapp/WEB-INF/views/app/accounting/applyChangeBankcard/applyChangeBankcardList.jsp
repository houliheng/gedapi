<%@ page contentType="text/html;charset=UTF-8"%>
<div class="wrapper">
	<sys:message content="${message}" />
	<div class="tableList">
		<h3 class="tableTitle">借款人还款银行卡变更列表</h3>
		<div id="tableDataId">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>申请时间</th>
						<th>还款银行卡号</th>
						<th>账户名称</th>
						<th>银行</th>
						<th>开户行</th>
						<th>审核状态</th>
						<th>审核时间</th>
						<th>审核说明</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="applyChangeBankcard">
						<tr>
							<td id="createTime" class="title" title="${applyChangeBankcard.createDate}">
								<fmt:formatDate value="${applyChangeBankcard.createDate}" pattern="yyyy-MM-dd" />
							</td>
							<td id="newRepBankcardNo" class="title" title="${applyChangeBankcard.newRepBankcardNo}">${applyChangeBankcard.newRepBankcardNo}</td>
							<td id="newRepBankcardName" class="title" title="${applyChangeBankcard.newRepBankcardName}">${applyChangeBankcard.newRepBankcardName}</td>
							<td id="newRepBank" class="title" title="${applyChangeBankcard.newRepBank}">${fns:getDictLabel(applyChangeBankcard.newRepBank, 'BANKS', '')}</td>
							<td id="newRepBankName" class="title" title="${applyChangeBankcard.newRepBankName}">${applyChangeBankcard.newRepBankName}</td>
							<td id="flowState" class="title" title="${applyChangeBankcard.flowState}">${fns:getDictLabel(applyChangeBankcard.flowState, 'FLOW_STATE', '')}</td>
							<td id="replyTime" class="title" title="${applyChangeBankcard.replyTime}">
								<fmt:formatDate value="${applyChangeBankcard.replyTime}" pattern="yyyy-MM-dd" />
							</td>
							<td id="replyDesc" class="title" title="${applyChangeBankcard.replyDesc}">${applyChangeBankcard.replyDesc}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>