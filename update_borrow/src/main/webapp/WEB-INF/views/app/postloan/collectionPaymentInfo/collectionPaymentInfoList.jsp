<%@ page contentType="text/html;charset=UTF-8"%>
<title>催收管理</title>
<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					saveForm();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		
	</script>
<div class="wrapper">
	<sys:message content="${message}" />
	<div class="tableList">
		<h3 class="searchTitle">客户回款详情列表</h3>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th width="5%">序号</th>
					<th width="10%">回款日期</th>
					<th width="10%">回款金额</th>
					<th width="10%">回款方式</th>
					<th width="10%">催收人</th>
					<!-- <th width="19%">操作</th> -->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${collectionPaymentInfoList}" var="collectionPaymentInfo" varStatus="i">
					<tr>
						<td id="num" class="title" title="序号">${i.index+1}</td>
						<td id="paymentDate" class="title" title="${collectionPaymentInfo.paymentDate}">${collectionPaymentInfo.paymentDate}</td>
						<td id="paymentAmount" class="title" title="${collectionPaymentInfo.paymentAmount}">${collectionPaymentInfo.paymentAmount}</td>
						<td id="paymentType" class="title" title="${collectionPaymentInfo.paymentType}">${fns:getDictLabel(collectionPaymentInfo.paymentType, 'PAYMENT_TYPE', '')}</td>
						<td id="collector" class="title" title="${collectionPaymentInfo.collector}">${collectionPaymentInfo.collector}</td>
						<%-- <td>
									 <a href="javascript:void(0);"
									  onclick="details('${ctx}/postloan/checkTwentyFive/check','核查','${page.contractNo}','${flag}','${page.contractAmount}','');">核查</a>
							    </td> --%>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</div>
