<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>待办任务列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function addTabPage002(title, url, applyNo, taskId, taskDefKey, procDefId, status){
			url = url + '?applyNo=' + applyNo + '&taskId=' + taskId + '&taskDefKey=' + taskDefKey + '&procDefId=' + procDefId + '&status=' + status;
			addTabPage(title, url);
		}
	</script>
</head>
<body>
	<div class="wrapper">
		<div class="tableList">
			<h3 class="infoListTitle">待办任务列表(临时测试功能使用)</h3>
	    	<table cellpadding="0" cellspacing="0" border="0" width="100%">
	        	<tr>
					<th width="60px;">任务名称</th>
					<th width="60px;">客户名称</th>
					<th width="60px;">证件类型</th>
					<th width="150px;">证件号</th>
					<th width="120px;">申请编号</th>
					<th width="100px;">申请金额(元)</th>
					<th width="100px;">登记门店</th>
					<th width="100px;">创建时间</th>
					<th>操作</th>
	        	</tr>
	        </table>
	        <div id="tableDataId" style="height:500px;overflow:auto;">
		    	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		        	<tr>
						<td width="60px;"><a href="javascript:void(0)" onclick="addTabPage002('申请录入', '${ctx}/credit/workflow/loanApply', 311, 2, 3, 4, 5);">申请录入</a></td>
						<td width="60px;">李雪华</td>
						<td width="60px;">身份证</td>
						<td width="150px;">412827198408082668</td>
						<td width="120px;">AC2015080800001</td>
						<td width="100px;" style="text-align:right">50000</td>
						<td width="100px;">立水桥店</td>
						<td width="100px;">2015-08-08</td>
						<td>办理</td>
					</tr>
		        	<tr class="doubleRow">
						<td>
						<a href="javascript:void(0)" onclick="addTabPage002('借前外访', '${ctx}/credit/workflow/beforeBorrowingVisit', 1, 2, 3, 4, 5);">借前外访</a>
						</td>
						<td>李雪华</td>
						<td>身份证</td>
						<td>412827198408082668</td>
						<td>AC2015080800001</td>
						<td style="text-align:right">50000</td>
						<td>立水桥店</td>
						<td>2015-08-08</td>
						<td>办理</td>
					</tr>
		        	<tr>
						<td><a href="javascript:void(0)" onclick="addTabPage002('面审', '${ctx}/credit/workflow/checkFace', 1, 2, 3, 4, 5);">面审</a></td>
						<td>李雪华</td>
						<td>身份证</td>
						<td>412827198408082668</td>
						<td>AC2015080800001</td>
						<td style="text-align:right">50000</td>
						<td>立水桥店</td>
						<td>2015-08-08</td>
						<td><a href="javascript:void(0)" onclick="addTabPage002('面审', '${ctx}/credit/workflow/checkFace', 1, 2, 3, 4, 5);">办理</a></td>
					</tr>
		        	<tr class="doubleRow">
						<td><a href="javascript:void(0)" onclick="addTabPage002('两人外访', '${ctx}/credit/workflow/checkCoupleDoubt', 1, 2, 3, 4, 5);">两人外访</a></td>
						<td>李雪华</td>
						<td>身份证</td>
						<td>412827198408082668</td>
						<td>AC2015080800001</td>
						<td style="text-align:right">50000</td>
						<td>立水桥店</td>
						<td>2015-08-08</td>
						<td>办理</td>
					</tr>
		        	<tr>
						<td><a href="javascript:void(0)" onclick="addTabPage002('电话核查', '${ctx}/credit/workflow/checkPhone', 1, 2, 3, 4, 5);">电话核查</a></td>
						<td>李雪华</td>
						<td>身份证</td>
						<td>412827198408082668</td>
						<td>AC2015080800001</td>
						<td style="text-align:right">50000</td>
						<td>立水桥店</td>
						<td>2015-08-08</td>
						<td>办理</td>
					</tr>
		        	<tr class="doubleRow">
						<td><a href="javascript:void(0)" onclick="addTabPage002('网查', '${ctx}/credit/workflow/checkWeb', 1, 2, 3, 4, 5);">网查</a></td>
						<td>李雪华</td>
						<td>身份证</td>
						<td>412827198408082668</td>
						<td>AC2015080800001</td>
						<td style="text-align:right">50000</td>
						<td>立水桥店</td>
						<td>2015-08-08</td>
						<td>办理</td>
					</tr>
		        	<tr>
						<td><a href="javascript:void(0)" onclick="addTabPage002('外访费登记', '${ctx}/credit/workflow/checkFee', 1, 2, 3, 4, 5);">外访费登记</a></td>
						<td>李雪华</td>
						<td>身份证</td>
						<td>412827198408082668</td>
						<td>AC2015080800001</td>
						<td style="text-align:right">50000</td>
						<td>立水桥店</td>
						<td>2015-08-08</td>
						<td>办理</td>
					</tr>
		        	<tr class="doubleRow">
						<td><a href="javascript:void(0)" onclick="addTabPage002('分公司风控审核/分公司经理审核', '${ctx}/credit/workflow/branchCompanyRiskAudit', 1, 2, 3, 4, 5);">分公司经理审核</a></td>
						<td>李雪华</td>
						<td>身份证</td>
						<td>412827198408082668</td>
						<td>AC2015080800001</td>
						<td style="text-align:right">50000</td>
						<td>立水桥店</td>
						<td>2015-08-08</td>
						<td>办理</td>
					</tr>
		        	<tr>
						<td><a href="javascript:void(0)" onclick="addTabPage002('分公司复议', '${ctx}/credit/workflow/branchReview', 1, 2, 3, 4, 5);">分公司复议</a></td>
						<td>李雪华</td>
						<td>身份证</td>
						<td>412827198408082668</td>
						<td>AC2015080800001</td>
						<td style="text-align:right">50000</td>
						<td>立水桥店</td>
						<td>2015-08-08</td>
						<td>办理</td>
					</tr>
		        	<tr class="doubleRow">
						<td><a href="javascript:void(0)" onclick="addTabPage002('区域审核', '${ctx}/credit/workflow/regionAudit', 1, 2, 3, 4, 5);">区域审核</a></td>
						<td>李雪华</td>
						<td>身份证</td>
						<td>412827198408082668</td>
						<td>AC2015080800001</td>
						<td style="text-align:right">50000</td>
						<td>立水桥店</td>
						<td>2015-08-08</td>
						<td><a href="javascript:void(0)" onclick="addTabPage002('合同面签', '${ctx}/credit/workflow/regionAudit', 1, 2, 3, 4, 5);">办理</a></td>
					</tr>
		        	<tr>
						<td>
						
						<a href="javascript:void(0)" onclick="addTabPage002('大区审核', '${ctx}/credit/workflow/largeRegionAudit', 1, 2, 3, 4, 5);">大区审核</a>
						</td>
						<td>李雪华</td>
						<td>身份证</td>
						<td>412827198408082668</td>
						<td>AC2015080800001</td>
						<td style="text-align:right">50000</td>
						<td>立水桥店</td>
						<td>2015-08-08</td>
						<td>办理</td>
					</tr>
		        	<tr class="doubleRow">
						<td><a href="javascript:void(0)" onclick="addTabPage002('总公司风控审核', '${ctx}/credit/workflow/parentCompanyAudit', 1, 2, 3, 4, 5);">总公司风控审核</a></td>
						<td>李雪华</td>
						<td>身份证</td>
						<td>412827198408082668</td>
						<td>AC2015080800001</td>
						<td style="text-align:right">50000</td>
						<td>立水桥店</td>
						<td>2015-08-08</td>
						<td>办理</td>
					</tr>
		        	<tr>
						<td><a href="javascript:void(0)" onclick="addTabPage002('合同预约', '${ctx}/credit/workflow/contractAppoint', 1, 2, 3, 4, 5);">合同预约</a></td>
						<td>李雪华</td>
						<td>身份证</td>
						<td>412827198408082668</td>
						<td>AC2015080800001</td>
						<td style="text-align:right">50000</td>
						<td>立水桥店</td>
						<td>2015-08-08</td>
						<td>办理</td>
					</tr>
		        	<tr class="doubleRow">
						<td>
						<a href="javascript:void(0)" onclick="addTabPage002('取消审批', '${ctx}/credit/workflow/cancelAppoint', 1, 2, 3, 4, 5);">取消审批</a>
						</td>
						<td>李雪华</td>
						<td>身份证</td>
						<td>412827198408082668</td>
						<td>AC2015080800001</td>
						<td style="text-align:right">50000</td>
						<td>立水桥店</td>
						<td>2015-08-08</td>
						<td>办理</td>
					</tr>
		        	<tr class="doubleRow">
						<td>
						<a href="javascript:void(0)" onclick="addTabPage002('合同面签', '${ctx}/credit/workflow/contractFaceSign', 1, 2, 3, 4, 5);">合同面签/法务审核/合同审核</a>
						</td>
						<td>李雪华</td>
						<td>身份证</td>
						<td>412827198408082668</td>
						<td>AC2015080800001</td>
						<td style="text-align:right">50000</td>
						<td>立水桥店</td>
						<td>2015-08-08</td>
						<td>办理</td>
					</tr>
		        	<tr class="doubleRow">
						<td>
						<a href="javascript:void(0)" onclick="addTabPage002('财务放款', '${ctx}/credit/workflow/financeLoan', 1, 2, 3, 4, 5);">财务放款</a>
						</td>
						<td>李雪华</td>
						<td>身份证</td>
						<td>412827198408082668</td>
						<td>AC2015080800001</td>
						<td style="text-align:right">50000</td>
						<td>立水桥店</td>
						<td>2015-08-08</td>
						<td>办理</td>
					</tr>
					<!-- 待完善 -->
					<tr>
						<td><a href="javascript:void(0)" onclick="addTabPage002('外访费返还', '${ctx}/credit/checkFee/checkFeeReturnIndex', 1, 2, 3, 4, 5);">外访费返还</a></td>
						<td>李雪华</td>
						<td>身份证</td>
						<td>412827198408082668</td>
						<td>AC2015080800001</td>
						<td style="text-align:right">50000</td>
						<td>立水桥店</td>
						<td>2015-08-08</td>
						<td>办理</td>
					</tr>
		        </table>
	        </div>
			<div class="pagination"></div>
		</div>
	</div>
</body>
</html>