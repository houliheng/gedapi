<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>处置页面</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#dealForm").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});

	});
	function saveForm() {
		loading();
		top.$.jBox.tip.mess = null;
		var formJson = $("#dealForm").serializeJson();
		$.post("${ctx}/postloan/mortgage/save", formJson, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {
					alertx(data.message, function() {
						if ("car" == '${mortgageFlag}') {
							parent.$.loadDiv("car", "${ctx }/postloan/mortgageCarInfo/dealList", {
								applyNo : data.dealApplyNo
							}, "post");
						} else if ("house" == '${mortgageFlag}') {
							parent.$.loadDiv("house", "${ctx }/postloan/mortgageHouseInfo/dealList", {
								applyNo : data.dealApplyNo
							}, "post");
						} else if ("other" == '${mortgageFlag}') {
							parent.$.loadDiv("other", "${ctx }/postloan/mortgageOtherInfo/dealList", {
								applyNo : data.dealApplyNo
							}, "post");
						}
						parent.getDealAllAmountFuntion();
						closeJBox();
					});
				} else {
					alertx(data.message);
				}
			}
		});
	}
	//影像上传
	function uploadImage() {
		var contractNo = $("#contractNo").val();
		var url = "${ctx}/postloan/overdueHandle/videoUpload?applyNo=${actTaskParam.applyNo}&taskDefKey=${actTaskParam.taskDefKey}&contractNo=" + contractNo;
		window.open(url);
	}

	//查看影像
	function viewImage() {
		var contractNo = $("#contractNo").val();
		var windowWidth = document.body.offsetWidth - 50;
		var url = "${ctx}/postloan/overdueHandle/videoView?applyNo=${actTaskParam.applyNo}&taskDefKey=${actTaskParam.taskDefKey}&contractNo=" + contractNo + "&status=1";
		window.open(url);
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="ribbon">
			<ul class="layout">
				<li class="mcp_upload">
					<a href="#" onclick="uploadImage();">
						<span>
							<b></b>
							影像上传
						</span>
					</a>
				</li>
				<li class="mcp_pic">
					<a href="#" onclick="viewImage();">
						<span>
							<b></b>
							影像查阅
						</span>
					</a>
				</li>
			</ul>
		</div>
		<div class="searchInfo">
			<h3 class="searchTitle">处置信息</h3>
			<div class="searchCon">
				<form:form id="dealForm" modelAttribute="mortgage" action="#" method="post" class="form-horizontal">
					<form:hidden path="mortgageId" />
					<form:hidden path="dealApplyNo" />
					<form:hidden path="contractNo" />
					<input type="hidden" id="mortgageFlag" name="mortgageFlag" value="${mortgageFlag}" />
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">处置总金额：</td>
							<td class="ft_content">
								<form:input path="dealAmount" htmlEscape="false" maxlength="15" class="input-medium number required" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">意见说明：</td>
							<td class="ft_content" colspan="5">
								<form:textarea path="dealDescription" rows="5" class="textarea-width textarea-style required" maxlength="1000" htmlEscape="false" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="searchButton" colspan="6" style="text-align: right;">
								<input id="btnSubmitt" type="submit" class="btn btn-primary" value="保 存" />
								&nbsp;
								<input id="btnButtonn" type="button" class="btn btn-primary" value="关 闭" onclick="closeJBox()" />
							</td>
						</tr>
					</table>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
