<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>信审报告列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
// 	function show(applyNo, fileName, fileStoragePath) {
// 		var url = "${ctx}/credit/creditReport/form?applyNo=" + applyNo + "&fileName=" + fileName + "&fileStoragePath=" + fileStoragePath;
// 		var title = "信审报告";
// 		var width = $(document).width() - 100;
// 		width = Math.max(width, 800);
// 		var height = $(document).height() - 100;
// 		height = Math.max(height, 510);
// 		openJBox("creditReport", url, title, width, height);
// 	}
</script>
</head>
<body>
	<div style="height: 600px;" class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">信审报告列表</h3>
			<div id="tableDataId" style="max-height: 400px; overflow: auto;">
				<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="5%">序号</th>
							<th width="20%">申请编号</th>
							<th width="20%">文件名</th>
							<th width="40%">存储路径</th>
							<th width="20%">操作</th>
						</tr>
					</thead>
					<c:forEach items="${pdfList}" var="pdfObj" varStatus="pdfList">
						<tr>
							<td class="title" title="${pdfList.count }">${pdfList.count }</td>
							<td class="title" title="${pdfObj.applyNo }">${pdfObj.applyNo }</td>
							<td class="title" title="${pdfObj.fileName }">${pdfObj.fileName }</td>
							<td class="title" title="${pdfObj.fileStoragePath }">${pdfObj.fileStoragePath }</td>
							<td>
							<a href="#" onClick="window.open('http://${yxUrl}/${pdfObj.fileStoragePath }')" >查看</a>
<!-- 								<a href="#" onclick="show('${pdfObj.applyNo }','${pdfObj.fileName }','${pdfObj.fileStoragePath }')">查看</a> -->
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>