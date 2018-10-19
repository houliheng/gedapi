<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>信审报告</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
// $(document).ready(function() {
// 	var apply= ${applyNo};
// 	$.post("${ctx}/credit/creditReport/show", {"applyNO":apply}, function(data) {
		
			
// 		}
// 	});
// });
</script>
</head>
<body>
	<div class="searchInfo" id="pdfbox">
<!-- 	<object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" style="width:100%;height:500px"> -->
<!-- 	<param name="src" value="${ctx}/credit/creditReport/show?applyNo=${applyNo}&fileName=${fileName}&fileStoragePath=${fileStoragePath"> -->
<!-- 	</object> -->
		<embed src="${ctx}/credit/creditReport/show?applyNo=${applyNo}&fileName=${fileName}&fileStoragePath=${fileStoragePath}"  style="width:100%;height:500px" id ="showPdf">
		</embed>
	</div>
</body>
</html>
