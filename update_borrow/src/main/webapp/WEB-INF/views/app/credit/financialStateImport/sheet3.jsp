<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
	$(document).ready(function(){
		if($("#ThemisReportInfo2").val().length>2){
			hasData();
		}else{
			noData();
		}
	});
	function noData(){
		var table=	$("#showTable");
		var html="";
		for(i=0;i<41;i++){
			html+="<tr>";
			for(j=0;j<8;j++){
				html+="<td>&nbsp</td>";
			}
			html+="</tr>";
		}
		table.append(html);
	}
	function hasData(){
	var table=	$("#showTable");	
	var obj = $("#ThemisReportInfo2").val();
	var aobj = eval(obj);
	var html="";
	//输出前两行年、月
	for(j=0;j<2;j++){
		html+="<tr>";
		for(i=0;i<aobj.length;i++){
			if(typeof(aobj[i].reportYearMonth)!="undefined"){
				yearMonth=aobj[i].reportYearMonth;
				if(j==0){
					year=yearMonth.substr(0,4);
				}else{
					year=yearMonth.substr(4);
				}
			}else{
				year="&nbsp";
			}
			html+="<td>"+year+"</td>";
			if(aobj.length<8&&(i+1)==aobj.length){
				for(k=0;k<(8-aobj.length);k++){
					html+="<td>&nbsp</td>";
				}
			}
		}
		html+="</tr>";
	}
	var leng=aobj[0].reportInfo;
	//广度优先遍历输出单行
	for(j=0;j<leng.length;j++){
		html+="<tr>";
		for(i=0;i<aobj.length;i++){
			list = aobj[i].reportInfo;
			value = list[j].reportIndexValue
			html+="<td>"+value+"</td>";
			if(aobj.length<8&&(i+1)==aobj.length){
				for(k=0;k<(8-aobj.length);k++){
					html+="<td>&nbsp</td>";
				}
			}
			}
		html+="</tr>";
		}
	table.append(html);
	}
</script>
</head>
<body>
<style>
.a{
    text-decoration:none;
    color:#FFFFFF;
    font-size:9pt;
    font-weight: bolder;
}
#showTable td{  background-color:  #7CCD7C;border: 1px solid #ddd;color: #000000;font-weight: bold;text-align: center;}
#showTableHead td{  background-color:  #AAAAAA;border: 1px solid #ddd;color: #000000;font-weight: bold;text-align: center;}
#sTr td{height: 37px;text-align: center;}
</style>
<p>${ThemisReportInfo2}</p>
<div style="float:left;width:5%">
<table style="width:100%;" id ="showTableHead">
<tr id="sTr"><td >行次</td></tr>
<c:forEach var="s" begin="1" end="39">
<tr><td>${s}</td></tr>
</c:forEach>
</table>
</div>
<div style="float:left;width:25%">
<table style="width:100%;" id ="showTableHead">
<tr><td>报表年份</td></tr>
<tr><td>报表月份</td></tr>
<tr><td >一、营业总收入</td>	</tr>
<tr><td>其中：营业收入</td></tr>
<tr><td>利息收入</td></tr>
<tr><td>已赚保费</td></tr>
<tr><td>手续费及佣金收入</td></tr>
<tr><td>二、营业总成本</td></tr>
<tr><td>其中：营业成本</td></tr>
<tr><td>利息支出</td></tr>
<tr><td>手续费及佣金支出</td></tr>
<tr><td>退保金</td></tr>
<tr><td>赔付支出净额</td></tr>
<tr><td>提取保险合同准备金净额</td></tr>
<tr><td>保单红利支出</td></tr>
<tr><td>分保费用</td></tr>
<tr><td>营业税金及附加</td></tr>
<tr><td>销售费用</td></tr>
<tr><td>管理费用</td></tr>
<tr><td>财务费用</td></tr>
<tr><td>资产减值损失</td></tr>
<tr><td>加：公允价值变动收益</td></tr>
<tr ><td >投资收益（损失以“-”号填列）</td></tr>
<tr><td>其中：对联营企业和合营企业的投资收益</td></tr>
<tr><td>汇兑收益</td></tr>
<tr><td>三、营业利润</td></tr>
<tr><td>加：营业外收入</td></tr>
<tr><td>减：营业外支出</td></tr>
<tr><td>其中：非流动资产处理损失</td></tr>
<tr><td>四、利润总额</td></tr>
<tr><td>减：所得税费用</td></tr>
<tr><td>五、净利润</td></tr>
<tr><td>归属于母公司所有者的净利润</td></tr>
<tr><td>*少数股东损益</td></tr>
<tr><td>六、每股收益：</td></tr>
<tr><td>基本每股收益</td></tr>
<tr><td>稀释每股收益</td></tr>
<tr><td>七、其他综合收益</td></tr>
<tr><td>八、综合收益总额</td></tr>
<tr><td>归属于母公司所有者的综合收益总额</td></tr>
<tr><td>归属于少数股东的综合收益总额</td></tr>

</table>
</div>
<div style=" width:70%; float:left;">
<table id ="showTable" style="width:100%;table-layout: fixed;"/>
</div>
</body>
</html>