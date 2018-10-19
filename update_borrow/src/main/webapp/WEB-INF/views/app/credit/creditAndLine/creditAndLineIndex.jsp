<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%-- <%@ include file="/WEB-INF/views/app/credit/creditAndLine/creditAndLineScript.jsp"%> --%>
<html>
<head>
	<title>征信及流水</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
			//流水信息列表
				$.loadDiv("creditLineBankDiv","${ctx }/credit/creditAndLine/creditLineBank/list",{applyNo:'${actTaskParam.applyNo }', readOnly:'${readOnly}'},"post");
			//个人征信信息列表
				$.loadDiv("creditCustDiv","${ctx }/credit/creditAndLine/creditCust/list",{applyNo:'${actTaskParam.applyNo }', readOnly:'${readOnly}'},"post");
			//企业征信信息列表
				$.loadDiv("creditCompanyDiv","${ctx }/credit/creditAndLine/creditCompany/list",{applyNo:'${actTaskParam.applyNo }', readOnly:'${readOnly}'},"post");
			//分析信息
				$.loadDiv("creditAnalysisDiv","${ctx }/credit/creditAndLine/creditAnalysis/form",{applyNo:'${actTaskParam.applyNo }', readOnly:'${readOnly}'},"post");
		});
		
		
		
	</script>
</head>
<body>
	<!-- 流水信息列表 -->
 	<div id="creditLineBankDiv">
 	</div>
 	<!-- 个人征信信息列表 -->
 	<div id="creditCustDiv">
 	</div>
 	<!-- 企业征信信息列表 -->
 	<div id="creditCompanyDiv">
 	</div>
 	<!-- 分析信息 -->
 	<div id="creditAnalysisDiv">
 	</div>
</body>
</html>
