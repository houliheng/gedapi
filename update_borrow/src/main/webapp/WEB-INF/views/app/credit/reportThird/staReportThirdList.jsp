<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>统计报表3管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	});
	function base_shClick() {
		$("#base_sh").toggle(600);
	}
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle" onclick="base_shClick()">基础审核</h3>
			<div id="base_sh">
				<table id="contentTable" class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th width="5%">审核科目</th>
							<th width="15%">审核对象</th>
							<th width="10%">审核工具</th>
							<th width="10%">材料要求</th>
							<th width="50%">禁入标准</th>
							<th width="5%">替换手段</th>
							<th width="5%">统计数量</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td rowspan="1">${ruleLst0.reviewedBook}</td>
							<td rowspan="3">${ruleLst0.reviewedTarget}</td>
							<td rowspan="3">${ruleLst0.reviewedTool}</td>
							<td rowspan="3">${ruleLst0.dataRequied}</td>
							<td rowspan="1">${ruleLst0.banRule}</td>
							<td rowspan="3">${ruleLst0.replaceMeans}</td>
							<td>${ruleLst0.bookCount}</td>
						</tr>
						<tr>
							<td rowspan="1">${ruleLst1.reviewedBook}</td>
							<td rowspan="1">${ruleLst1.banRule}</td>
							<td>${ruleLst0.bookCount}</td>
						</tr>
						<tr>
							<td rowspan="1">${ruleLst2.reviewedBook}</td>
							<td rowspan="1">${ruleLst2.banRule}</td>
							<td>${ruleLst0.bookCount}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div>
		<%@ include file="s2ruleCList.jsp"%>
	</div>
	<div>
		<%@ include file="s3ruleCList.jsp"%>
	</div>
	<div>
		<%@ include file="s3ruleCListS1.jsp"%>
	</div>
	<div>
		<%@ include file="s3ruleCListS2.jsp"%>
	</div>
	<div>
		<%@ include file="s3ruleCListS3.jsp"%>
	</div>
	<div>
		<%@ include file="s4_1ruleCList.jsp"%>
	</div>
	<div>
		<%@ include file="s4_2ruleCList.jsp"%>
	</div>
	<div>
		<%@ include file="s5ruleCList.jsp"%>
	</div>
	<div>
		<%@ include file="s6ruleCList.jsp"%>
	</div>
</body>
</html>
