<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>账务清收管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
		$(document).ready(function() {
			if('${readOnly}' == 'readOnly'){
			$("[id = 'buttonDiv']").hide();
			}
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
         //新增
		function add(url, title,contractNo,flag) {
			openJBox('accountCleanBox', url, title, $(window).width()-50, $(window).height()-50, {contractNo : contractNo,flag : flag});
		} 
		
		//重置
		function resetField() {
	 		$("#cleanBy").val("");
	 	}
	 	
	 	function finish() {
		confirmx("确定结束清收？", function() {
			loading();
			$.post("${ctx}/postloan/accountCleanAllocate/finishAccountClean", {
				contractNo : '${contractNo}',
				flag : '${flag}'
			}, function(data) {
				closeTip();
				if (data) {
					if (data.status == 1) {
						alertx(data.message, function() {
							parent.location.href="${ctx}/postloan/checkTwentyFive/list?flag=" + data.data;
							closeJBox();
						});
					} else {
						alertx(data.message);
					}
				}
			});
		});
	}
	</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div class="ribbon">
				<div id="buttonDiv">
					<ul class="layout">
						<li class="add"><a href="#" onclick="add('${ctx}/postloan/accountClean/form','新增清收信息','${contractNo}','${flag}');"> <span> <b></b> 新增
							</span>
						</a></li>
						<li class="delete"><a href="#" onclick="finish();"> <span> <b></b> 结束清收
							</span>
						</a></li>
					</ul>
				</div>
			</div>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="5%">序号</th>
							<th width="10%">合同编号</th>
							<th width="10%">清收人姓名</th>
							<th width="10%">清收金额</th>
							<th width="10%">清收回款金额</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="page" varStatus="i">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="contractNo" class="title" title="${page.contractNo}">${page.contractNo}</td>
								<td id="cleanBy" class="title" title="${page.cleanBy}">${page.cleanBy}</td>
								<td id="cleanAmount" class="title" title="${page.cleanAmount}"><fmt:formatNumber value="${page.cleanAmount }" pattern="###,##0.00"></fmt:formatNumber></td>
								<td id="cleanReturnAmount" class="title" title="${page.cleanReturnAmount}"><fmt:formatNumber value="${page.cleanReturnAmount}" pattern="###,##0.00"></fmt:formatNumber></td>
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