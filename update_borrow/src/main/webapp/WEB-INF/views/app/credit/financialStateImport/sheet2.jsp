<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
	$(document).ready(function() {
		if ($("#ThemisReportInfo1").val().length > 2) {
			hasData();
		} else {
			noData();
		}
	});
	function noData() {
		var table = $("#showTable");
		var html = "";
		for (i = 0; i < 88; i++) {
			html += "<tr>";
			for (j = 0; j < 8; j++) {
				html += "<td>&nbsp</td>";
			}
			html += "</tr>";
		}
		table.append(html);
	}
	function hasData() {
		var table = $("#showTable");
		var obj = $("#ThemisReportInfo1").val();
		var aobj = eval(obj);
		var html = "";
		//创建前两行年、月
		for (j = 0; j < 2; j++) {
			html += "<tr>";
			for (i = 0; i < aobj.length; i++) {
				if (typeof (aobj[i].reportYearMonth) != "undefined") {
					yearMonth = aobj[i].reportYearMonth;
					if (j == 0) {
						year = yearMonth.substr(0, 4);
					} else {
						year = yearMonth.substr(4);
					}
				} else {
					year = "&nbsp";
				}
				html += "<td>" + year + "</td>";
				if (aobj.length < 8 && (i + 1) == aobj.length) {
					for (k = 0; k < (8 - aobj.length); k++) {
						html += "<td>&nbsp</td>";
					}
				}
			}
			html += "</tr>";
		}
		var leng = aobj[0].reportInfo;
		//设置跳过某行输出
		var jump = "|1||21||42||65||75|";
		//广度优先遍历输出单行
		var lengb =-1;
		for (j = 0; j < leng.length; j++) {
			html += "<tr>";
			lengb++;
			for (i = 0; i < aobj.length; i++) {
				//根据跳过内容该行输出"-"
				if (jump.indexOf("|" + (lengb+ 1) + "|") >= 0) {
					var conter = 0;
					if (aobj.length < 8) {
						conter = 8;
					} else {
						conter = aobj.length;
					}
					for (k = 0; k < conter; k++) {
						html += "<td>-</td>";
					}
					j--;
					break;
				}
				list = aobj[i].reportInfo;
				value = list[j].reportIndexValue
				html += "<td>" + value + "</td>";
				if (aobj.length < 8 && (i + 1) == aobj.length) {
					for (k = 0; k < (8 - aobj.length); k++) {
						html += "<td>&nbsp</td>";
					}
				}
			}
			html += "</tr>";
		}
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

#sTr td {
	height: 37px;
	text-align: center;
}
</style>
	<div style="float:left;width:5%">
		<table style="width: 100%;" id="showTableHead">
			<tr id="sTr">
				<td>行次</td>
			</tr>
			<c:forEach var="s" begin="1" end="86">
				<tr>
					<td>${s}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div style="float:left;width:25%">
		<table style="width: 100%;" id="showTableHead">
			<tr>
				<td>报表年份</td>
			</tr>
			<tr>
				<td>报表月份</td>
			</tr>
			<tr class="sTr1">
				<td>流动资产：</td>
			</tr>
			<tr>
				<td>货币资金</td>
			</tr>
			<tr>
				<td>结算备付金</td>
			</tr>
			<tr>
				<td>拆出资金</td>
			</tr>
			<tr>
				<td>交易性金融资产</td>
			</tr>
			<tr>
				<td>应收票据</td>
			</tr>
			<tr>
				<td>应收账款</td>
			</tr>
			<tr>
				<td>预付账款</td>
			</tr>
			<tr>
				<td>应收保费</td>
			</tr>
			<tr>
				<td>应收分保合同准备金</td>
			</tr>
			<tr>
				<td>应收分保账款</td>
			</tr>
			<tr>
				<td>应收利息</td>
			</tr>
			<tr>
				<td>应收股利</td>
			</tr>
			<tr>
				<td>其他应收款</td>
			</tr>
			<tr>
				<td>买入返售金融资产</td>
			</tr>
			<tr>
				<td>存货</td>
			</tr>
			<tr>
				<td>其中：消耗性生物资产</td>
			</tr>
			<tr>
				<td>一年内到期的非流动资产</td>
			</tr>
			<tr>
				<td>其他流动资产</td>
			</tr>
			<tr>
				<td>流动资产合计</td>
			</tr>
			<tr class="sTr1">
				<td>非流动资产：</td>
			</tr>
			<tr>
				<td>发放贷款及垫款</td>
			</tr>
			<tr>
				<td>可供出售金融资产</td>
			</tr>
			<tr>
				<td>持有至到期投资</td>
			</tr>
			<tr>
				<td>长期应收款</td>
			</tr>
			<tr>
				<td>长期股权投资</td>
			</tr>
			<tr>
				<td>投资性房地产</td>
			</tr>
			<tr>
				<td>固定资产</td>
			</tr>
			<tr>
				<td>在建工程</td>
			</tr>
			<tr>
				<td>工程物资</td>
			</tr>
			<tr>
				<td>固定资产清理</td>
			</tr>
			<tr>
				<td>生产性生物资产</td>
			</tr>
			<tr>
				<td>油气资产</td>
			</tr>
			<tr>
				<td>无形资产</td>
			</tr>
			<tr>
				<td>开发支出</td>
			</tr>
			<tr>
				<td>商誉</td>
			</tr>
			<tr>
				<td>长期待摊费用</td>
			</tr>
			<tr>
				<td>递延所得税资产</td>
			</tr>
			<tr>
				<td>其他非流动资产</td>
			</tr>
			<tr>
				<td>非流动资产合计</td>
			</tr>
			<tr>
				<td>资产总计</td>
			</tr>
			<tr class="sTr1">
				<td>流动负债：</td>
			</tr>
			<tr>
				<td>短期借款</td>
			</tr>
			<tr>
				<td>向中央银行借款</td>
			</tr>
			<tr>
				<td>吸收存款及同业存放</td>
			</tr>
			<tr>
				<td>拆入资金</td>
			</tr>
			<tr>
				<td>交易性金融负债</td>
			</tr>
			<tr>
				<td>应付票据</td>
			</tr>
			<tr>
				<td>应付账款</td>
			</tr>
			<tr>
				<td>预收账款</td>
			</tr>
			<tr>
				<td>卖出回购金融资产款</td>
			</tr>
			<tr>
				<td>应付手续费及佣金</td>
			</tr>
			<tr>
				<td>应付职工薪酬</td>
			</tr>
			<tr>
				<td>应交税费</td>
			</tr>
			<tr>
				<td>应付利息</td>
			</tr>
			<tr>
				<td>应付股利</td>
			</tr>
			<tr>
				<td>其他应付款</td>
			</tr>
			<tr>
				<td>应付分保账款</td>
			</tr>
			<tr>
				<td>保险合同准备金</td>
			</tr>
			<tr>
				<td>代理买卖证券债</td>
			</tr>
			<tr>
				<td>代理承销证券债</td>
			</tr>
			<tr>
				<td>一年内到期的非流动负债</td>
			</tr>
			<tr>
				<td>其他流动负债</td>
			</tr>
			<tr>
				<td>流动负债合计</td>
			</tr>
			<tr class="sTr1">
				<td>非流动负债：</td>
			</tr>
			<tr>
				<td>长期借款</td>
			</tr>
			<tr>
				<td>应付债券</td>
			</tr>
			<tr>
				<td>长期应付款</td>
			</tr>
			<tr>
				<td>专项应付款</td>
			</tr>
			<tr>
				<td>预计负债</td>
			</tr>
			<tr>
				<td>递延所得税负债</td>
			</tr>
			<tr>
				<td>其他非流动负债</td>
			</tr>
			<tr>
				<td>非流动负债合计</td>
			</tr>
			<tr>
				<td>负债合计</td>
			</tr>
			<tr class="sTr1">
				<td>所有者权益（或股东权益）：</td>
			</tr>
			<tr>
				<td>实收资本（股本）</td>
			</tr>
			<tr>
				<td>资本公积</td>
			</tr>
			<tr>
				<td>减：库存股</td>
			</tr>
			<tr>
				<td>盈余公积*</td>
			</tr>
			<tr>
				<td>一般风险准备</td>
			</tr>
			<tr>
				<td>未分配利润</td>
			</tr>
			<tr>
				<td>外币报表折算差额</td>
			</tr>
			<tr>
				<td>归属母公司所有者权益合计</td>
			</tr>
			<tr>
				<td>少数股东权益</td>
			</tr>
			<tr>
				<td>所有者权益合计</td>
			</tr>
			<tr>
				<td>负债和所有者权益总计</td>
			</tr>
		</table>
	</div>
	<div style=" width:70%; float:left;">
		<table id="showTable" style="width: 100%; table-layout: fixed;">
		</table>
	</div>
</body>
</html>