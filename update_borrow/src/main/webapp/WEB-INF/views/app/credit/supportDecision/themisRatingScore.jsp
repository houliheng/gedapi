<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
	$(document).ready(function() {
		if ('${trsListForColJson}'.length > 2) {
			hasData();
		} else {
			noData();
		}
	});
	function noData() {
		var table = $("#showTable");
		var html = "";
		for (i = 0; i < 18; i++) {
			html += "<tr>";
			for (j = 0; j < 8; j++) {
				html += "<td>&nbsp</td>";
			}
			html += "</tr>";
		}
		table.append(html);

		var fullMarksTable = $("#showFullMarks");
		var fullMarksHtml = "";
		for (i = 0; i < 18; i++) {
			if (i == 0) {
				fullMarksHtml += "<tr><td>满分</td></tr>";
			} else if (i == 1) {
				fullMarksHtml += "<tr><td>100</td></tr>";
			} else {
				fullMarksHtml += "<tr><td>-</td></tr>";
			}

		}
		fullMarksTable.append(fullMarksHtml);
	}
	function hasData() {
		var trsListForColJson = '${trsListForColJson}';
		//所有列集合
		var trsListForCol = eval(trsListForColJson);
		//列数
		var colNum = trsListForCol.length;
		//评分项目
		var themisReportDicListJson = '${themisReportDicListJson}';
		var themisReportDicList = eval(themisReportDicListJson);
		//行号
		var rowNum = themisReportDicList.length;
		var table = $("#showTable");
		var html = "";
		var fullMarksTable = $("#showFullMarks");
		var fullMarksHtml = "<tr height='5.5%'><td>满分</td></tr><tr height='5.5%'><td>100</td></tr>";

		//广度优先遍历输出单行
		for (j = 0; j < rowNum + 2; j++) {
			//创建第一行年、月 
			if (j == 0) {
				html += "<tr height='5.5%'>";
				for (i = 0; i < colNum; i++) {
					var trsList = trsListForCol[i];
					var trsObj = eval(trsList);
					var yearStr = trsObj.reportYearMonth;
					if (typeof (yearStr) != "undefined") {
						year = yearStr.substr(0, 4) + "." + yearStr.substr(4);
					} else {
						year = "&nbsp;";
					}
					html += "<td colspan='2'>" + year + "</td>";
					if (colNum < 8 && (i + 1) == colNum) {
						for (k = 0; k < (7 - colNum); k += 2) {
							html += "<td colspan='2'>-</td>";
						}
					}
				}
				html += "</tr>";
			} else if (j == 1) {//创建第二行得分、预警
				html += "<tr  height='5.5%'>";
				for (i = 0; i < colNum; i++) {
					html += "<td>得分</td><td>预警</td>";
					if (colNum < 8 && (i + 1) == colNum) {
						for (k = 0; k < (7 - colNum); k += 2) {
							html += "<td>-</td><td>-</td>";
						}
					}
				}
				html += "</tr>";
			} else {
				//其他行
				html += "<tr height='5.5%'>";
				for (i = 0; i < colNum; i++) {
					var trsList = trsListForCol[i].themisReturnScoreList;
					for (q = 0; q < trsList.length; q++) {
						var trs = trsList[q];
						var score;
						var warnning;
						score = trs.score;
						warnning = trs.warnning;
						if (trs != null && trs.returnOrderCol == j - 1 && trs.returnOrderCol < 4) {
							if (score != null) {
								if(score.indexOf("*") > 0){
								   score= score.substring(0,score.indexOf("*"));
								   warnning = "*";
								}
								html += "<td>" + score + "</td>";
							} else {
								html += "<td>-</td>";
							}
							if (warnning != null) {
								html += "<td>" + warnning + "</td>";
							} else {
								html += "<td>-</td>";
							}
						}
						
						if (trs != null && trs.returnOrderCol == j && trs.returnOrderCol > 4) {
							if (score != null) {
								if(score.indexOf("*") > 0){
									   score= score.substring(0,score.indexOf("*"));
									   warnning = "*";
									}
								html += "<td>" + score + "</td>";
							} else {
								html += "<td>-</td>";
							}
							if (warnning != null) {
								html += "<td>" + warnning + "</td>";
							} else {
								html += "<td>-</td>";
							}
						}
					}
					if (colNum < 8 && (i + 1) == colNum) {
						for (k = 0; k < (7 - colNum); k += 2) {
							html += "<td>-</td><td>-</td>";
						}
					}
				}
				html += "</tr>";
			}
		}
		//满分
		var trsList = trsListForCol[0].themisReturnScoreList;
		for (s = 0; s < trsList.length; s++) {
			var trs = trsList[s];
			var fullMarks;
			/* if (trs != null&&trs!="<null>"&&trs.fullMarks!=null) {
				fullMarks = trs.fullMarks;
			} else {
				fullMarks = "-";
			} */
			
	        if (s >= 0 && s<3 || s>6 && s < 12) {
				fullMarks = "10";
			} else if (s > 2 && s < 7) {
				fullMarks = "5";
			}else if(s > 11 && s < 15){
			    fullMarks = "100";
			}else{
			    fullMarks = "A";
			}
			fullMarksHtml += "<tr height='5.5%'><td>" + fullMarks + "</td></tr>";
		}
		var trsList2 = trsListForCol[0].themisReturnScoreList;
		if (trsList2.length < 16) {
			for (k = 0; k < (16 - trsList2.length); k++) {
				fullMarksHtml += "<tr height='5.5%'><td>-</td></tr>";
			}
		}
		fullMarksTable.append(fullMarksHtml);
		table.append(html);

	}
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

#showTable td {
	background-color: #7CCD7C;
	border: 1px solid #ddd;
	color: #000000;
	font-weight: bold;
	text-align: center;
}

#showTableHead td {
	background-color: #AAAAAA;
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
	<div class="wrapper searchInfo">
		<h3 class="searchTitle">Themis评级得分</h3>
		<div style="float: left; width: 20%;">
			<table id="showTableHead" class="table-hover" style="width: 100%;">
				<tr height="11%">
					<td colspan="2" class="specialTitle">评分项目</td>
				</tr>
				<c:forEach items="${themisReportDicList }" var="themisReportDic" varStatus="themisReportDicList">
					<c:if test="${themisReportDic.reportOrderCol > 12 }">
						<tr height="5.5%">
							<td colspan="2">${themisReportDic.reportIndexName }</td>
						</tr>
					</c:if>
					<c:if test="${themisReportDic.reportOrderCol <= 12 }">
						<tr height="5.5%">
							<td>${themisReportDic.reportOrderCol }</td>
							<td>${themisReportDic.reportIndexName }</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
		<div style="float: left; width: 10%;">
			<table id="showFullMarks" style="width: 100%; table-layout: fixed;">
			</table>
		</div>
		<div style="float: left; width: 70%;">
			<table id="showTable" style="width: 100%; table-layout: fixed;">
			</table>
		</div>
		<div style="float: none; height: 363px;" ></div>
	</div>
</body>
</html>