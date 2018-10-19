<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${actTaskParam.title}</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		//初始化最初展示的Tab页面
		$(document).ready(function(){
			showLoanApply(true);
		});
		var width = $(window).width()-200;
		var height = $(window).height()-100;
		//流程轨迹
		function processTrackDone(taskDefKey,procInstId){
		  	var windowWidth = window.parent.width-100;
		    	//alert(windowWidth);
				var windowHeight = window.parent.height-100;
		    var url = "${ctx}/credit/taskCenter/processTrack?procInstId=" + procInstId;
			openJBox("trackBox", url, "流程轨迹", windowWidth, windowHeight);
		}
		//流程图
		function tracePhotoDone(){
			var windowWidth = window.parent.width-100;
		    	//alert(windowWidth);
				var windowHeight = window.parent.height-100;
			var url = "${ctx}/credit/taskCenter/trace/photo/customer?procInstId=${actTaskParam.procInstId}";
	    	openJBox("tracePhotoBox", url, "流程图", windowWidth, windowHeight);
		}
		//查看影像
		function viewImageDone() {
			var windowWidth = document.body.offsetWidth -50;
			var url ="${ctx}/credit/ApplyRegisterVO/form?applyNo=${actTaskParam.applyNo}&optype=1&productTypeId=${productType}";
			window.open(url,'${actTaskParam.applyNo}');
		}
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/app/credit/workflow/include/workflowScript.jsp"%>
	<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
	<div class="wrapper">
		<div class="ribbon">
	    	<ul class="layout">
	        	<li class="mcp_pic"><a href="#" onclick="viewImageDone();"><span><b></b>影像查阅</span></a></li>
	        	<li class="mcp_pic"><a href="#" onclick="tracePhotoDone()"><span><b></b>流程图</span></a></li>
	        	<li class="mcp_pic"><a href="#" onclick="processTrackDone()"><span><b></b>流程轨迹</span></a></li>
	        	<li class="mcp_close"><a href="#" onclick="goToPage('${ctx}${actTaskParam.headUrl}','processInfoIndexSkipId');"><span><b></b>关闭</span></a><a id="processInfoIndexSkipId"></a></li>
	        </ul>
	    </div>
	    <sys:message content="${message}"/>	
		<div style="margin-top: 5px;">
			<ul class="nav nav-tabs" id="mainTabs">
				<li class="">
					<a href="#tab_loanApply" onclick="showLoanApply(true)">借款申请信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_custInfo" onclick="showCustInfoTab(true)">客户信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_guarantorInfo" onclick="showGuarantorInfoTab(true)">担保信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_mortgageCarInfo" onclick="showMortgageCarEvaluateInfoTab(true)">抵质押物信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_information" onclick="showInformationTab(true)">综合信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkFace" onclick="showCheckFaceTab(true)">面审信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_creditAndLine" onclick="showCreditAndLineTab(true)">征信及流水</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_financeImport" onclick="showFinanceImportTab(true)">财报导入</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_creditViewBook" onclick="showCreditViewBookTab(true)">信审意见书</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkApprove" onclick="showCheckApproveTab(true)">批复信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_get" onclick="showGetTab(true)">冠e通</a>
				</li>
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane" id="tab_loanApply"></div>
			<div class="tab-pane" id="tab_custInfo"></div>
			<div class="tab-pane" id="tab_guarantorInfo"></div>
			<div class="tab-pane" id="tab_mortgageCarInfo"></div>
			<div class="tab-pane" id="tab_information"></div>
			<div class="tab-pane" id="tab_checkFace"></div>
			<div class="tab-pane" id="tab_creditAndLine"></div>
			<div class="tab-pane" id="tab_financeImport"></div>
			<div class="tab-pane" id="tab_creditViewBook"></div>
			<div class="tab-pane" id="tab_checkApprove"></div>
			<div class="tab-pane" id="tab_get"></div>
		</div>
	</div>
</body>
</html>