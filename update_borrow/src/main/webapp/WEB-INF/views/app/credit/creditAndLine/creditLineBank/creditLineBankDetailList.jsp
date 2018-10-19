<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>银行卡流水明细管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	});
	//新增
	function addCreditLineBankDetail(){
		var url = "${ctx}/credit/creditAndLine/creditLineBank/creditLineBankDetail/form";
		openJBox("creditLineBankDetail-form", url, "新增银行卡流水明细", $(window).width()-100, 380, {creditLineBankId : '${creditLineBankId }',readOnly:'${readOnly}',applyNo:'${applyNo}'});
	}
	//修改
	function editCreditLineBankDetail(){
		var $checkLine = $("input[name='creditLineBankDetailIds']:checked");
		var $len = $checkLine.length;
		if ($len != 1) {//需要勾选一条信息进行修改
			alertx("请选择一条银行卡流水明细！");
		} else {
			var url = "${ctx}/credit/creditAndLine/creditLineBank/creditLineBankDetail/form?creditLineBankDetailId=" + $checkLine.val() + "&&readOnly=1" + "&&applyNo="+'${applyNo}';
			openJBox("creditLineBankDetail-detail", url, "编辑银行卡流水明细信息", $(window).width()-100,380, null);
		}
	}
	//删除
	function delCreditLineBankDetail(creditLineBankId){
		var $checkLine = $("input[name='creditLineBankDetailIds']:checked");
		var $len = $checkLine.length;
		if ($len < 1) {
			alertx("请选择要删除的银行卡流水明细！ ");
		} else {
			var checkedValue = getCheckedValue("creditLineBankDetailIds");
			var url = "${ctx}/credit/creditAndLine/creditLineBank/creditLineBankDetail/delete?ids=" + checkedValue;
			confirmx('确认要删除勾选的银行卡流水明细吗？', function() {
				saveJson(url, null, function(data) {
					if (data) {
						alertx(data.message);
					    parent.$.loadDiv("creditLineBankDiv","${ctx }/credit/creditAndLine/creditLineBank/list",{applyNo:'${applyNo}', readOnly:'${readOnly}'},"post");
						window.location.href="${ctx}/credit/creditAndLine/creditLineBank/creditLineBankDetail/list?creditLineBankId="+creditLineBankId+"&applyNo="+'${applyNo}'+"&readOnly="+'${readOnly}';
					   
					}
				});
			});
		}
	}
	
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">流水明细列表</h3>
			<div class="ribbon filter">
		    	<ul class="layout">
		    		<li class="add"><a id="add" href="#" onclick="addCreditLineBankDetail();" ><span><b></b>新增</span></a></li>
		        	<li class="edit"><a id="edit" href="#" onclick="editCreditLineBankDetail();" ><span><b></b>编辑</span></a></li>
		        	<li class="delete"><a id="delete" href="#" onclick="delCreditLineBankDetail('${creditLineBankId}');"><span><b></b>删除</span></a></li>
		        </ul>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="3%"><input type="checkbox" id="creditLineBankDetailCheckAll" name="creditLineBankDetailCheckAll" onclick="allCheck('creditLineBankDetailCheckAll','creditLineBankDetailIds')"/></th>
							<th>序号</th>
							<th>户名</th>
							<th>银行卡号</th>
							<th>流水月份</th>
							<th>进项额(元)</th>
							<th>出项额(元)</th>
							<th>当月有效流水</th>
							<th>期末余额(元)</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${creditLineBankDetailList}" var="creditLineBankDetail" varStatus="creditLineBankDetailList">
							<tr>
								<td><input type="checkbox" name="creditLineBankDetailIds" id="creditLineBankDetailIds" value="${creditLineBankDetail.id }"/></td>
								<!-- 序号 -->
								<td>${creditLineBankDetailList.count }</td>
								<!-- 户名 -->
								<td id="creditLineBank.custName" class="title" title="${creditLineBankDetail.creditLineBank.custName }">${creditLineBankDetail.creditLineBank.custName }</td>
								<!-- 银行卡号 -->
								<td id="lineBankId" class="title" title="${creditLineBankDetail.creditLineBank.bankcardNo }">${creditLineBankDetail.creditLineBank.bankcardNo }</td>
								<!-- 流水月份 -->
								<td id="lineMonth" class="title" title="${creditLineBankDetail.lineMonth }">${creditLineBankDetail.lineMonth }</td>
								<!-- 进项额 -->
								<td id="incomeAmount" class="title" title="${creditLineBankDetail.incomeAmount }">  <fmt:formatNumber value="${creditLineBankDetail.incomeAmount }" pattern="###,##0.00"></fmt:formatNumber></td>
								<!-- 出项额  -->
								<td id="expenseAmount" class="title" title="${creditLineBankDetail.expenseAmount }"> <fmt:formatNumber value="${creditLineBankDetail.expenseAmount }" pattern="###,##0.00"></fmt:formatNumber> </td>
								<!-- 当月有效流水 -->
								<td id="curreValidLineAmount" class="title" title="${creditLineBankDetail.curreValidLineAmount }"> <fmt:formatNumber value="${creditLineBankDetail.curreValidLineAmount }" pattern="###,##0.00"></fmt:formatNumber> </td>
								<!-- 期末余额 -->
								<td id="cycleEndAmount" class="title" title="${creditLineBankDetail.cycleEndAmount }"> <fmt:formatNumber value="${creditLineBankDetail.cycleEndAmount }" pattern="###,##0.00"></fmt:formatNumber> </td>
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