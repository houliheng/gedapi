<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据处理成功管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			loading();
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function resetForm(){
			$("#contractNo").val('');
		}
		
		function saveCleanContract(contractNo){
			if(contractNo != null && contractNo !=''){
			loading();
			$.ajax({
			url : "${ctx}/accounting/cleanContract/save",
			data : {
				contractNo:contractNo
			},
			type : "post",
			async : false,
			dataType : "JSON",
			success : function(data) {
				closeTip();
				if (data.status == 1) {
					alertx(data.message,function(){
						page();
					})
				} else {
					alertx(data.message);
				}
			}
			});
			}else{
				alertx("请在合同号栏输入合同号");
			}
		}
		

		function cleanData(contractNo){
			if(contractNo != null && contractNo !=''){
				confirmx('清空数据不包含冲账入账功能，请再次确认入账是否正确？', function(){
				loading();
				$.ajax({
				url : "${ctx}/accounting/cleanContract/clean",
				data : {
					contractNo:contractNo
				},
				type : "post",
				async : false,
				dataType : "JSON",
				success : function(data) {
					closeTip();
					if (data.status == 1) {
						alertx(data.message,function(){
							page();
						})
					} else {
						alertx(data.message);
					}
				}
				});
				});
			}else{
				alertx("请在合同号栏输入合同号");
			}
		}
		
		function deleteData(contractNo,periodNum){
			confirmx('确认要删除该数据吗？', function(){
				loading();
				$.ajax({
				url : "${ctx}/accounting/cleanContract/delete",
				data : {
					contractNo:contractNo,
					periodNum:periodNum
				},
				type : "post",
				async : false,
				dataType : "JSON",
				success : function(data) {
					closeTip();
					if (data.status == 1) {
						alertx(data.message,function(){
							page();
						})
					} else {
						alertx(data.message);
					}
				}
				});
			});
		}
		
		function queryData(contractNo,periodNum){
			var width = $(window).width() - 300;
			width = Math.max(width, 1000);
			var height = $(window).height() - 200;
			var url = "${ctx}/accounting/cleanContract/form?contractNo="+contractNo+"&periodNum="+periodNum;
			openJBox("applyMarginRepayBox", url, "保证金退还申请", width, height);
		}
		
	</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="cleanContract" action="${ctx}/accounting/cleanContract/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td>
									<label>合同编号：</label>
									<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-medium" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="page()"  />&nbsp; 
							<input id="btnReset"class="btn btn-primary" type="button" value="重置" onclick="resetForm()" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList">
			<h3 class="tableTitle">数据列表(<span style="color: red">此页面操作不涉及任何冲账入账功能，仅供调整数据使用，请谨慎操作！</span>)</h3>
			<shiro:hasRole name="admin">
			<div class="ribbon">
			<ul class="layout" name="pl">
				<li>
					<a href="javaScript:void(0)" onclick="saveCleanContract('${cleanContract.contractNo}')">
						<span>新增行</span>
					</a>
				</li>
				<li>
					<a href="javaScript:void(0)" onclick="cleanData('${cleanContract.contractNo}')">
						<span>清空数据</span>
					</a>
				</li>
			</ul>
			</div>
			</shiro:hasRole>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="3%">期数</th>
							<th width="7%">状态</th>
							<th width="8%">当期本息结清日期</th>
							<th width="8%">当期总结清日期</th>
							<th width="8%">实还总金额</th>
							<th width="7%">实还服务费</th>
							<th width="7%">实还账户管理费</th>
							<th width="7%">实还利息</th>
							<th width="7%">实还本金</th>
							<th width="7%">实还违约金</th>
							<th width="7%">实还罚息</th>
							<th width="6%">实还营业外收入额</th>
							<th width="6%">提前还款费用</th>
							<th width="5%">退回</th>
							<shiro:hasPermission name="accounting:cleanContract:edit"><th width="7%">操作</th></shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="cleanContract">
						<tr>
						<td class="title" title="${cleanContract.periodNum}">${cleanContract.periodNum}</td>
						<td class="title" title="${fns:getDictLabel(cleanContract.repayPeriodStatus, 'PERIOD_STATE', '')}">${fns:getDictLabel(cleanContract.repayPeriodStatus, 'PERIOD_STATE', '')}</td>
						<td class="title" title="<fmt:formatDate value="${cleanContract.capitalInterestRepayDate}" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${cleanContract.capitalInterestRepayDate}" pattern="yyyy-MM-dd"/></td>
						<td class="title" title="<fmt:formatDate value="${cleanContract.allRepayDate}" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${cleanContract.allRepayDate}" pattern="yyyy-MM-dd"/></td>
						<td class="title" title="${cleanContract.factRepayAmount}">${cleanContract.factRepayAmount}</td>
						<td class="title" title="${cleanContract.factServiceFee}">${cleanContract.factServiceFee}</td>
						<td class="title" title="${cleanContract.factManagementFee}">${cleanContract.factManagementFee}</td>
						<td class="title" title="${cleanContract.factInterestAmount}">${cleanContract.factInterestAmount}</td>
						<td class="title" title="${result.factCapitalAmount}">${result.factCapitalAmount}</td>
						<td class="title" title="${cleanContract.factPenaltyAmount}">${cleanContract.factPenaltyAmount}</td>
						<td class="title" title="${cleanContract.factFineAmount}">${cleanContract.factFineAmount}</td>
						<td class="title" title="${cleanContract.factAddAmount}">${cleanContract.factAddAmount}</td>
						<td class="title" style="text-align: center;" title="${cleanContract.factBreakAmount}">${cleanContract.factBreakAmount}</td>
						<td class="title" title="${cleanContract.backAmount}">${cleanContract.backAmount}</td>
							<shiro:hasRole name="admin"><td>
			    				<a href="#" onclick="queryData('${cleanContract.contractNo}', '${cleanContract.periodNum}')">修改</a>
								<a href="#" onclick="deleteData('${cleanContract.contractNo}', '${cleanContract.periodNum}')">删除</a>
							</td></shiro:hasRole>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>