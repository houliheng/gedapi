<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
	$(document).ready(function() {
	});
</script>
</head>
<body>
	<style>
.a {
	text-decoration: none;
	color: #FFFFFF;
	font-size: 9pt;
	font-weight: bolder;
}

.grey {
	background-color: #AAAAAA;
	border: 1px solid #ddd;
	color: #000000;
	font-weight: bold;
	text-align: center;
}

.green{
	background-color: #7CCD7C;
	border: 1px solid #ddd;
	color: #000000;
	font-weight: bold;
	text-align: center;
}

#showFullMarks td {
	background-color: #AAAAAA;
	border: 1px solid #ddd;
	color: #000000;
	font-weight: bold;
	text-align: center;
}

#sTr td {
	height: 37px;
	text-align: center;
}

.specialTitle {
	height: 37px;
}
</style>
<!-- 	<div style="float: left; width: 20%;"> -->
<!-- 		<table id="showTableHead" class="table-hover" style="width: 100%;"> -->
<!-- 			<c:forEach items="${themisReviewIndexList }" var="themisReportDic" varStatus="themisReportDicList"> -->
<!-- 				<tr> -->
<!-- 					<td>${themisReportDic.reportIndexName }</td> -->
<!-- 				</tr> -->
<!-- 			</c:forEach> -->
<!-- 		</table> -->
<!-- 	</div> -->
		<table style="width: 100%; table-layout: fixed;">
			<c:if test="${not empty themisReturnReviewList }">
				<c:forEach items="${themisReturnReviewList }" var="themisReturnReview" varStatus="themisReturnReviewList">
					<tr>
						<td style="width: 20%;" class= "grey">${themisReturnReview.returnIndexName }</td>
						<td style="width: 80%;" class= "green">${themisReturnReview.review }</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty themisReturnReviewList }">
				<c:forEach items="${themisReviewIndexList }" var="themisReportDic" varStatus="themisReportDicList">
					<tr>
						<td>-</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
</body>
</html>