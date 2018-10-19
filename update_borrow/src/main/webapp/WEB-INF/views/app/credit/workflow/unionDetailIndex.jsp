<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>联合授信</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		//初始化最初展示的Tab页面
		$(document).ready(function(){
			showCheckApproveUnionDetailTab(true);
		});
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/app/credit/workflow/include/workflowScript.jsp"%>
	<div class="wrapper">
		<div style="margin-top: 5px;">
			<ul class="nav nav-tabs" id="mainTabs">
				<li class="">
					<a href="#tab_checkApprove_union_detail" onclick="showCheckApproveUnionDetailTab(true)">批复信息</a>
				</li>


				<!--批量借款企业-->
				<c:if test="${flag == 100}">

					<li class="line" id="lineId"></li>
					<li class="" id="companyInfo">
						<a href="#tab_companyInfo" onclick="showCompanyInfoTab('${actTaskParam.status == 1}')">批量处理</a>
					</li>

				</c:if>
				<li class="line"></li>
				<li class="">
					<a href="#tab_getUnion" onclick="showGetUnionTab('${actTaskParam.status == 1}')">冠e通</a>
				</li>
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane" id="tab_checkApprove_union_detail"></div>
			<div class="tab-pane" id="tab_getUnion"></div>
			<input type="hidden" id="approId" name="approId" value="${approId}" />
			<div class="tab-pane" id="tab_companyInfo"></div>
		</div>
	</div>
</body>
</html>