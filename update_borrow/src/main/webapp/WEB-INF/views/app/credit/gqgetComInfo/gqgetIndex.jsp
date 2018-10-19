<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%-- <%@ include file="/WEB-INF/views/app/credit/creditAndLine/creditAndLineScript.jsp"%> --%>
<html>
<head>
<title>冠e通</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						//借款人信息
// 						$.loadDiv("ads","${ctx }/credit/creditViewBook/applyInfoForCredit/form",null, "post");
						//企业信息
// 						$.loadDiv("ads1","${ctx }/credit/creditViewBook/checkFeeForCredit/form",null, "post");
						//车产抵押
// 						$.loadDiv("ads2","${ctx }/credit/creditViewBook/compositeOpinion/form",null,"post");
						//房产抵押
// 						$.loadDiv("ads3","${ctx }/credit/creditViewBook/creditOtherInfo/form",null, "post");
						//设备抵押
// 						$.loadDiv("ads4","${ctx }/credit/creditViewBook/creditAssets/list",null, "post");
						//担保人
// 						$.loadDiv("ads5","${ctx }/credit/creditAndLine/creditAnalysis/form",null, "post");
						//担保企业
// 						$.loadDiv("ads6","${ctx }/credit/processSuggestionInfo/formCreditViewBookApproveInfo",null,"post");
						//借后信息披露字段
// 						$.loadDiv("ads7","${ctx }/credit/processSuggestionInfo/formCreditViewBookApproveInfo",null,"post");
					});
</script>
</head>
<body>
	<!-- 借款人信息 -->
	<div id="ads">
	</div>
	<!-- 企业信息 -->
	<div id="ads1">
	</div>
	<!-- 车产抵押 -->
	<div id="ads2">
	</div>
	<!-- 房产抵押 -->
	<div id="ads3">
	</div>
	<!-- 设备抵押 -->
	<div id="ads4">
	</div>
	<!-- 担保人 -->
	<div id="ads5">
	</div>
	<!-- 担保企业 -->
	<div id="ads6">
	</div>
	<!-- 借后信息披露字段 -->
	<div id="ads7">
	</div>
	<div class="form-actions" style="text-align: right;">
		<input id="btnSubmit" class="btn btn-primary" type="submit"
			value="保 存" />
	</div>
</body>
</html>
