<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
	$(document).ready(function(){
		var obj = $("#ThemisReportHead").val();
		if(!checkIsNull(obj)){
			var aobj = eval('('+obj+')');
			$("#companyCode").html(aobj.companycode);
			$("#companyChnName").html(aobj.companyChnName);
			$("#companyEngName").html(aobj.companyEngName);
			$("#createdate").html(aobj.buildDate);
			$("#companynature").html(aobj.companynature);
			$("#gbIndu1").html(aobj.gbIndu1);
			$("#gbIndu2").html(aobj.gbIndu2);
			$("#marketClass").html(aobj.marketClass);
			$("#postcode").html(aobj.postcode);
			$("#isMerger").html(aobj.isMerger);
			$("#reportType").html(aobj.reportType);
			$("#reportUnit").html(aobj.reportUnit);
		}
	});
</script>
<style>
.a{
    text-decoration:none;
    color:#FFFFFF;
    font-size:9pt;
    font-weight: bolder;
}
/* #container td{  background-color:  #7CCD7C;border: 1px solid #ddd;color: #000000;font-weight: bolder;} */
/* #containerHead td{  background-color:  #AAAAAA;border: 1px solid #ddd;color: #000000;font-weight: bolder;}  */
.grey{  background-color:  #AAAAAA;border: 1px solid #ddd;color: #000000;font-weight: bolder;width: 15%;}
.green{  background-color:  #7CCD7C;border: 1px solid #ddd;color: #000000;font-weight: bolde;width:12.14%}
</style>
</head>
<body>
<div >
<table style="width:100%;" id ="containerHead">
<tr><td class="grey">企业编号</td><td class="green" id="companyCode">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td></tr>
<tr><td class="grey" >企业中文名称</td><td class="green"id="companyChnName">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td></tr>
<tr><td class="grey">企业英文名称</td><td class="green" id="companyEngName">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td></tr>
<tr><td class="grey">成立年月</td><td class="green" id="createdate">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td></tr>
<tr><td class="grey" >企业性质</td><td class="green"id="companynature">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td></tr>
<tr><td class="grey" >国标一级行业</td><td class="green"id="gbIndu1">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td></tr>
<tr><td class="grey" >国标二级行业</td><td class="green"id="gbIndu2">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td></tr>
<tr><td class="grey" >是否上市</td><td class="green"id="marketClass">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td></tr>
<tr><td class="grey">邮政编码</td><td class="green" id="postcode">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td></tr>
<tr><td class="grey">是否合并</td><td class="green" id="isMerger">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td></tr>
<tr><td class="grey">报表单位</td><td class="green" id="reportType">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td></tr>
<tr><td class="grey" >报表类型</td><td class="green"id="reportUnit">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td><td class="green">&nbsp</td></tr>
</table>
</div>
<!-- <div style=" width:90%;float:right;"> -->
<!-- <table id="container" style="width:100%;table-layout: fixed;"> -->
<!-- </table> -->
<!-- </div> -->
</body>
</html>