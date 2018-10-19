<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
 $(document).ready(function(){
	 $.loadDiv("sheet","${ctx }/credit/financialStateImport/showSheet1",null, "post");
 });
function getSheet( colNo) {
	if(colNo=="1")
		$.loadDiv("sheet","${ctx }/credit/financialStateImport/showSheet1",null, "post");
	else if(colNo=="2")
		$.loadDiv("sheet","${ctx }/credit/financialStateImport/showSheet2",null, "post");
	else if(colNo=="3")
		$.loadDiv("sheet","${ctx }/credit/financialStateImport/showSheet3",null, "post");
}
</script>
<style>
A {
    text-decoration:none;
    color:#000000;
    font-size:9pt;
}
.divP{border: 0 solid #e2e7eb;
    border-bottom-left-radius: 3px;
    border-bottom-right-radius: 3px;
    background-color: #f7f7f7;
    border: 1px solid #ddd;
    margin-left:10px  ;
    margin-right:10px  ;
    padding: 0;}
#tabstrip td{  background-color:  #808080;border: 1px solid #ddd; width:35%;    text-align: center;}
#tabstrip a{
 font:宋体;
 color:#FFFFFF;
 font-size: small;
 padding-left: 10px;
 padding-right: 10px;
 padding-bottom: 3px;
 padding-top: 3px;
 font-weight:bold;
}
</style>
</head>
<div id ="sheet" class=divP>
</div>
<div  style="float:none;">
	<div class="divP" id ="tabstrip"">
	<table border=0; cellspacing=1; >
	 <tr  >
	 <td><a href="javascript:void(0);"onclick="getSheet(1);return false;" target="frSheet">公司信息</a></td>
	 <td><a href="javascript:void(0);"onclick ="getSheet(2);return false;" target="frSheet">新会计准则资产负债表</a></td>
	 <td><a  href="javascript:void(0);"onclick ="getSheet(3);return false;" target="frSheet">新会计准则利润表</a></td>
	 </tr>
	</table>
	</div>
</div>
</html>
