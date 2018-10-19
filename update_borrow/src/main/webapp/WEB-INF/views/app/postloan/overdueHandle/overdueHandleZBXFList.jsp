<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>贷后逾期处理管理</title>
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
		//重置
		function resetForm() {
			$("#contractNo").val('');
			$("#custName").val('');
		}
		
		function zbxf(contractNo,periodNum){
			contractNo = encodeX(contractNo);
			//alert(contractNo);
			$.ajax({
				type : "post",
				url : "${ctx}/postloan/overdueHandle/zbxf?contractNo=" + contractNo + "&periodNum=" + periodNum,
				dataType : "json",
				success : function(data) {
					alertx(data.message);
				},
				error : function(msg) {
					alertx("操作失败，请查看后台信息");
				}
			});
		}
		
		function zbxfAssignList(contractNo,periodNum){
			contractNo = encodeX(contractNo);
			var urlsuffix = "?contractNo= " + contractNo + "&periodNum= " +　periodNum;
			var url = "${ctx}/postloan/overdueHandle/zbxfAssignList" + urlsuffix;
			var width = Math.max($(window).width() - 100, 800);
			var height = Math.min($(window).height() - 100, 400);
			openJBox("assignBox", url, "任务下发", width, height);
		}
		
	</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="overdueHandle" action="${ctx}/postloan/overdueHandle/zbxfList" method="post" >
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<form:input path="custName" htmlEscape="false" class="input-medium"/>
								</td>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content">
									<form:input path="contractNo" htmlEscape="false" maxlength="32" class="input-medium"/>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp; 
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" onclick="resetForm();" value="重置" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>客户名称</th>
							<th>合同编号</th>
							<th>合同金额</th>
							<th>期数</th>
							<th>逾期金额</th>
							<th>逾期天数</th>
							<th>大区</th>
							<th>区域</th>
							<th>分公司</th>
							<th>登记人</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="overdueHandle">
						<tr>
							<td id="custName" class="title" title="${overdueHandle.custName}">
								${overdueHandle.custName}
							</td>
							<td id="contractNo" class="title" title="${overdueHandle.contractNo}">
								${overdueHandle.contractNo}
							</td>
							<td id="contractAmount" class="title" title="${overdueHandle.contractAmount}">
								${overdueHandle.contractAmount}
							</td>
							<td id="periodNum" class="title" title="${overdueHandle.periodNum}">
								${overdueHandle.periodNum}
							</td>
							<td id="overdueAmount" class="title" title="${overdueHandle.overdueAmount}">
								${overdueHandle.overdueAmount}
							</td>
							<td id="overdueDays" class="title" title="${overdueHandle.overdueDays}">
								${overdueHandle.overdueDays}
							</td>
							<td id="orgLevel2" class="title" title="${overdueHandle.orgLevel2}">
								${overdueHandle.orgLevel2}
							</td>
							<td id="orgLevel3" class="title" title="${overdueHandle.orgLevel3}">
								${overdueHandle.orgLevel3}
							</td>
							<td id="orgLevel4" class="title" title="${overdueHandle.orgLevel4}">
								${overdueHandle.orgLevel4}
							</td>
							<td id="registerName" class="title" title="${overdueHandle.registerName}">
								${overdueHandle.registerName}
							</td>
							<td>
								<a href="#" onclick="zbxfAssignList('${overdueHandle.contractNo}','${overdueHandle.periodNum}');">任务下发</a>
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