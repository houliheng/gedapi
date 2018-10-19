<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
	$(document).ready(function() {
		var reportYearMonthJson = '${reportYearMonthJson}';
		var reportYearMonthList = eval(reportYearMonthJson);
		var sheetNum = 0;
		//有数据
		if (reportYearMonthList != null && typeof (sheetNum) != 'undefined' && reportYearMonthList.length != 0) {
			$.loadDiv("sheet", "${ctx }/credit/supportDecision/showSheet", {
				reportYearMonth : reportYearMonthList[0],applyNo:'${applyNo}'
			}, "post");
		} else {//无数据
			$.loadDiv("sheet", "${ctx }/credit/supportDecision/showSheet", null, "post");
		}
	});

	function getSheet(reportYearMonth) {
		$.loadDiv("sheet", "${ctx }/credit/supportDecision/showSheet", {
			reportYearMonth : reportYearMonth,applyNo:'${applyNo}'
		}, "post");
	}
</script>
<style>
A {
	text-decoration: none;
	color: #000000;
	font-size: 9pt;
}

.divP {
	border: 0 solid #e2e7eb;
	border-bottom-left-radius: 3px;
	border-bottom-right-radius: 3px;
	background-color: #f7f7f7;
	border: 1px solid #ddd;
	margin-left: 10px;
	margin-right: 10px;
	padding: 0;
}

#tabstrip td {
	background-color: #808080;
	border: 1px solid #ddd;
	width: 35%;
	text-align: center;
}

#tabstrip a {
	font: 宋体;
	color: #FFFFFF;
	font-size: large;
	padding-left: 10px;
	padding-right: 10px;
	padding-bottom: 0px;
	padding-top: 3px;
	font-weight: bold;
}
</style>
</head>
<body>
	<div style="float: none;" class="wrapper searchInfo" id="tabstrip"">
		<h3 class="searchTitle">Themis专家分析</h3>
		<table id="tabTable" class="table-hover">
			<tr>
				<c:if test="${not empty reportYearMonthList }">
					<c:forEach items="${reportYearMonthList }" var="reportYearMonth" varStatus="reportYearMonthList">
						<td>
							<a href="javascript:void(0);" onclick="getSheet('${reportYearMonth }')">${reportYearMonth }</a>
						</td>
					</c:forEach>
				</c:if>
				<c:if test="${empty reportYearMonthList }">
					<c:forEach items="${reportYearMonthList }" var="reportYearMonth" varStatus="reportYearMonthList">
						<td>
							<a href="javascript:void(0);">暂无数据</a>
						</td>
					</c:forEach>
				</c:if>
<!-- 				<td>评级得分：<span>&nbsp;</span>评估等级：<span>&nbsp;</span></td> -->
			</tr>
		</table>
	</div>
	<div id="sheet" class="divP"></div>
</body>
</html>
