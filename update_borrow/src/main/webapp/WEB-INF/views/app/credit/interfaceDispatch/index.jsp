<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>接口调度</title>
<meta name="decorator" content="default" />
<script  type="text/javascript">
function sender(obj){
	applyNo=$(obj).parent().prev().prev().children().val();
	url=$(obj).parent().parent().parent().parent().attr("id");
	$.get("${pageContext.request.contextPath}/f/"+url+applyNo,function(data){
	obj.parentNode.parentNode.childNodes[5].innerHTML="成功";
	});
}

function toPrepare() {
		var contractNo = $("#contractNo").val().trim();
		var time = $("#cashTime").val();
		if (contractNo == null || contractNo == "") {
			alertx("请输入合同号");
		} else if (time == null || time == "") {
			alertx("请选择时间");
		} else {
			$.ajax({
			url : "${ctx}/credit/applyLoanStatus/toPrepare",
			type : "post",
			data : {
			contractNo : contractNo,
			time : time
			},
			dataType : "json",
			success : function(data) {
				if (data.status == "1") {
					alertx(data.message, function() {
					});
				} else {
					alertx(data.message);
				}
			},
			error : function(msg) {
				alertx("系统错误，请查看后台信息！");
			}
			});
		}
	}
</script>
</head>
<body>
<div class="filter">
		<h3 class="searchTitle" onclick="javascript:$('#zjpt').toggle(600);">资金平台</h3>
<div id ="zjpt" style="display:block;">
		<div class="filter" >
		<h4 class="tableTitle" onclick="javascript:$('#rest\\/funds\\/client\\/createAccount\\/').toggle(600);">借款人开户</h4>
			<table class="fromTable" id="rest/funds/client/createAccount/" style="display: none;" >
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" cssClass="input-medium" /></td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
		</div>
		<div class="filter">
		<h4 class="tableTitle" onclick="javascript:$('#rest\\/funds\\/client\\/mortgageeCreateAccount\\/').toggle(600);">抵押权人开户</h4>
			<table class="fromTable" id="rest/funds/client/mortgageeCreateAccount/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">抵押权人身份证号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" cssClass="input-medium" /></td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
		</div>
		<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#rest\\/funds\\/client\\/borrowerLoan\\/').toggle(600);">借款人放款</h4>
			<table class="fromTable" id="rest/funds/client/borrowerLoan/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" cssClass="input-medium" /></td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
		</div>
		<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#rest\\/funds\\/client\\/borrowerDeposit\\/').toggle(600);">借款人提现</h4>
			<table class="fromTable" id="rest/funds/client/borrowerDeposit/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" cssClass="input-large" /></td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
		</div>
		<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#rest\\/funds\\/client\\/mortgageeDeposit\\/').toggle(600);">抵押权人提现</h4>
			<table class="fromTable" id="rest/funds/client/mortgageeDeposit/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" cssClass="input-medium" /></td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
		</div>
		<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#rest\\/funds\\/client\\/repaymentWithholding\\/').toggle(600);">还款划扣</h4>
			<table class="fromTable" id="rest/funds/client/repaymentWithholding/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" disabled="disabled" cssClass="input-medium required" /></td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
		</div>
		<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#rest\\/funds\\/client\\/accountEnter\\/').toggle(600);">入账</h4>
			<table class="fromTable" id="rest/funds/client/accountEnter/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" disabled="disabled" cssClass="input-medium"</td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
		</div>
		<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#rest\\/funds\\/client\\/bankCardChange\\/').toggle(600);">银行卡变更</h4>
			<table class="fromTable" id="rest/funds/client/bankCardChange/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" cssClass="input-large" /></td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
		</div>
		<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#rest\\/funds\\/client\\/abortiveTender\\/').toggle(600);">流标</h4>
			<table class="fromTable" id="rest/funds/client/abortiveTender/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" cssClass="input-large" /></td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
		</div>
		<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#rest\\/funds\\/client\\/refundDeposit\\/').toggle(600);">退还保证金</h4>
			<table class="fromTable" id="rest/funds/client/refundDeposit/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" cssClass="input-large" /></td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
		</div>
</div>
</div>
<div class="filter">
		<h3 class="searchTitle" onclick="javascript:$('#get').toggle(600);">冠E通</h3>
		<div id ="get" style="display:block;">
			<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#rest\\/Get\\/service\\/GetIssuingTenderData\\/').toggle(600);">发标</h4>
			<table class="fromTable" id="rest/Get/service/GetIssuingTenderData/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="100" style="height:25px;" cssClass="input-large" /></td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
			</div>
			<div class="filter">
				<h4 class="tableTitle" onclick="javascript:$('#toPrepareId').toggle(600);">重新建账</h4>
				<table class="fromTable" id="toPrepareId" style="display: none;">
					<tr>
						<td width="13%" height="20px" class="ft_label">合同号：</td>
						<td width="25%">
							<input id="contractNo" htmlEscape="false" maxlength="50" style="height:25px;" cssClass="input-large" />
						</td>
						<td width="13%" height="20px" class="ft_label">提现时间：</td>
						<td width="25%">
							<input id="cashTime" htmlEscape="false" maxlength="50" style="height:25px;" class=" input-large Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
						</td>
						<td>
							<input class="uploadify-button" style="width: 120px;height:30px; " type="button" value="发送" onclick="toPrepare()">
						</td>
					</tr>
				</table>
			</div>
			<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#rest\\/Get\\/service\\/GetWithdrawSuccessBidDataRequest\\/').toggle(600);">满标提现</h4>
			<table class="fromTable" id="rest/Get/service/GetWithdrawSuccessBidDataRequest/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" cssClass="input-large" /></td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
			</div>
			<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#rest\\/Get\\/service\\/GetAbortiveTenderRequest\\/').toggle(600);">流标</h4>
			<table class="fromTable" id="rest/Get/service/GetAbortiveTenderRequest/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" cssClass="input-large" /></td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
			</div>
			<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#rest\\/Get\\/service\\/GetRepaymentBidDataRequest\\/').toggle(600);">提前还款</h4>
			<table class="fromTable" id="rest/Get/service/GetRepaymentBidDataRequest/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" cssClass="input-large" /></td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
			</div>
		</div>
</div>
<div class="filter">
<h3 class="searchTitle" onclick="javascript:$('#wfxt').toggle(600);">外访系统</h3>
	<div id ="wfxt" style="display:block;">
			<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#sv\\/request\\/getSVClient2\\/').toggle(600);">外访接口</h4>
			<table class="fromTable" id="sv/request/getSVClient2/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" cssClass="input-large" /></td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
			</div>
			<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#sv\\/informationMatch\\/getSVClient3\\/').toggle(600);">外访字段对接接口</h4>
			<table class="fromTable" id="sv/informationMatch/getSVClient3/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" cssClass="input-large" /></td>
					<td width="10%" id ="zjrkuMessage1"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
			</div>
	</div>
</div>
<div class="filter">
<h3 class="searchTitle" onclick="javascript:$('#themis').toggle(600);">Themis系统</h3>
	<div id ="themis" style="display:block;">
			<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#webService\\/themis\\/return\\/').toggle(600);">Themis接口</h4>
			<table class="fromTable" id="webService/themis/return/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" cssClass="input-large" /></td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
			</div>
	</div>
</div>
<div class="filter">
<h3 class="searchTitle" onclick="javascript:$('#experian').toggle(600);">Experian系统</h3>
	<div id ="experian" style="display:block;">
			<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#webService\\/experian\\/GetExperianClient\\/').toggle(600);">Experian接口</h4>
			<table class="fromTable" id="webService/experian/GetExperianClient/" style="display: none;">
				<tr>
					<td width="13%" height="20px" class="ft_label">进件号：</td>
					<td width="25%"><input  htmlEscape="false" maxlength="50" style="height:25px;" cssClass="input-large" /></td>
					<td width="10%" id ="zjrkuMessage"></td>
					<td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
			</div>
	</div>
</div>
</body>
</html><td><input class="uploadify-button" style="width: 120px;height:30px; " type="button"  value="发送" onclick="sender(this)"></td>
				</tr>
			</table>
			</div>
	</div>
</div>
<div class="filter">
<h3 class="searchTitle" onclick="javascript:$('#experian').toggle(600);">Experian系统</h3>
	<div id ="experian" style="display:block;">
			<div class="filter">
			<h4 class="tableTitle" onclick="javascript:$('#webService\\/experian\\/GetExperianClient\\/').toggle(600);">Experian接口</h4>
			<table class="fromTable" id="webService/experian/GetExperianClient/" style="display: none;">
				<