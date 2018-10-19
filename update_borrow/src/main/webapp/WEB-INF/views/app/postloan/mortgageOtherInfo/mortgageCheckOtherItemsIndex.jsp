<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>抵质押物信息管理</title>
<script type="text/javascript">
	$(document).ready(function() {
	 $.loadDiv("mortgageEvaluateItemsList", "${ctx}/postloan/mortgageEvaluateInfo/editInfo", {
			 applyNo : '${mortgageOtherInfo.applyNo}',infoId:'${mortgageOtherInfo.id}' 
		}, "post");
	});
 	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	} 
	//查询
	 function query(id, urlSingle, title) {
		var width = 1000;
		var height = 600;
		var url = urlSingle + id + "&flag=1";
		openJBox('formBox', url, title, width, $(top.document).height() - 200,{applyNo:applyNo,id:id});
	}   
	
</script>
</head>
<body>
 		<!-- 房屋抵质押物基本信息 -->
	<div id="mortgageOtherEvaluateInfoFormDiv">
		<%@ include file="/WEB-INF/views/app/postloan/mortgageOtherInfo/mortgageOtherEvaluateInfoForm.jsp"%>
	</div>  
 	<!-- 抵质押物检查信息 -->
	<div id="mortgageEvaluateInfoFormDiv">
		<%@ include file="/WEB-INF/views/app/postloan/mortgageEvaluateInfo/mortgageEvaluateInfoForm.jsp"%>
	</div> 
		<!--  检查项目信息 -->
	  <div id="mortgageEvaluateItemsList"></div> 
	<div style="overflow: auto;">
	</div>
</body>
</html>