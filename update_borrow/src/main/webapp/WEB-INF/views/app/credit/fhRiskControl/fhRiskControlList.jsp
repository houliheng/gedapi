<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>法海风控信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	//预查
	function queryData(applyNo, idNum, custId, custName) {
		$.ajax({
		url : "${ctx}/credit/fhRiskControl/queryData?custName=" + encodeX(custName),
		data : {
		applyNo : applyNo,
		idNum : idNum,
		custId : custId
		},
		async : false,
		dataType : "JSON",
		success : function(data) {
			closeTip();
			if (data.status == "1") {
				alertx(data.message, function() {
					location.reload();
				});
			} else {
				alertx(data.message);
			}
		}
		});
	}

	//生成报告
	function generateReport(applyNo, idNum, custId, custName) {
		$.ajax({
		url : "${ctx}/credit/fhRiskControl/generateReport",
		data : {
		applyNo : applyNo,
		idNum : idNum,
		custId : custId,
		custName : custName
		},
		async : false,
		dataType : "JSON",
		success : function(data) {
			closeTip();
			if (data.status == 1) {
				alertx(data.message, function() {
					location.reload();
				});
			} else {
				alertx(data.message);
			}
		}
		});
	}
	//查看报告
	function queryReport(applyNo, idNum, custId, custName) {
		var width = $(window).width() - 300;
		width = Math.max(width, 1000);
		var height = $(window).height() - 200;
		var url = "${ctx}/credit/fhRiskControl/form";
		openJBox("applyMarginRepayBox", url, "查看报告", width, height, {
		applyNo : applyNo,
		idNum : idNum,
		custId : custId,
		custName : custName
		});
	}
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
			<form:form id="searchForm" modelAttribute="fhRiskControl" action="${ctx}/credit/fhRiskControl/list" method="post"> 
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="actTaskParam" name="taskDefKey" type="hidden" value="${actTaskParam.taskDefKey}" />
					<input id = "applyNo" name="applyNo" type="hidden" value="${actTaskParam.applyNo}"> 
				<table id="contentTable" cellpadding="0" cellspacing="0" border="0" width="100%">
					<thead>
						<tr>
							<th width="4%">序号</th>
							<th width="8%">核查对象</th>
							<th width="8%">姓名</th>
							<th width="6%">核查次数</th>
							<th width="6%">开庭公告</th>
							<th width="6%">案件流程</th>
							<th width="6%">新闻媒体</th>
							<th width="6%">裁判文书</th>
							<th width="6%">执行公告</th>
							<th width="6%">失信公告</th>
							<th width="6%">法院公告</th>
							<th width="6%">曝光台</th>
							<th width="8%">报告状态</th>
							<th width="18%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="fhRiskControl" varStatus="i">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="roleType" class="title" title="${fhRiskControl.roleType}">${fns:getDictLabel(fhRiskControl.roleType, 'ROLE_TYPE', '')}</td>
								<td id="custName" class="title" title="${fhRiskControl.custName}">${fhRiskControl.custName}</td>
								<td id="checkNum" class="title" title="${fhRiskControl.checkNum}">${fhRiskControl.checkNum}</td>
								<td id="ktggCount" class="title" title="${fhRiskControl.ktggCount}">${fhRiskControl.ktggCount}</td>
								<td id="ajlcCount" class="title" title="${fhRiskControl.ajlcCount}">${fhRiskControl.ajlcCount}</td>
								<td id="newsCount" class="title" title="${fhRiskControl.newsCount}">${fhRiskControl.newsCount}</td>
								<td id="cpwsCount" class="title" title="${fhRiskControl.cpwsCount}">${fhRiskControl.cpwsCount}</td>
								<td id="zxggCount" class="title" title="${fhRiskControl.zxggCount}">${fhRiskControl.zxggCount}</td>
								<td id="sxggCount" class="title" title="${fhRiskControl.sxggCount}">${fhRiskControl.sxggCount}</td>
								<td id="fyggCount" class="title" title="${fhRiskControl.fyggCount}">${fhRiskControl.fyggCount}</td>
								<td id="bgtCount" class="title" title="${fhRiskControl.bgtCount}">${fhRiskControl.bgtCount}</td>
								<td id="reportStatus" class="title" title="${fns:getDictLabel(fhRiskControl.reportStatus, 'REPORT_STATUS', '')}">${fns:getDictLabel(fhRiskControl.reportStatus, 'REPORT_STATUS', '')}</td>
								<td>
									<c:if test="${actTaskParam.taskDefKey == 'utask_ms'}">
										<a href="javascript:void(0);" onclick="queryData('${fhRiskControl.applyNo}','${fhRiskControl.idNum}','${fhRiskControl.custId}','${fhRiskControl.custName}');">预查</a>
									</c:if>
									
									<c:if test="${(fhRiskControl.reportStatus == '1' || fhRiskControl.reportStatus == '4')
													&&(fhRiskControl.checkNum!='0')
													&&(fhRiskControl.ktggCount!='0'||fhRiskControl.ajlcCount!='0'
														||fhRiskControl.newsCount!='0'||fhRiskControl.cpwsCount!='0'
														||fhRiskControl.zxggCount!='0'||fhRiskControl.sxggCount!='0'
														||fhRiskControl.fyggCount!='0'||fhRiskControl.bgtCount!='0')
													&&(actTaskParam.taskDefKey == 'utask_ms')}">
										<a href="javascript:void(0);" onclick="generateReport('${fhRiskControl.applyNo}','${fhRiskControl.idNum}','${fhRiskControl.custId}','${fhRiskControl.custName}');">生成报告</a>
									</c:if>
									<c:if test="${fhRiskControl.reportStatus == '3'}">
										<a href="javascript:void(0);" onclick="window.open('http://${yxUrl}/${fhRiskControl.realFilePath }')">查阅报告</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</form:form>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>