<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {
		if ('${noGurrCust}' == 'true') {
			$("#gurrCustCheckTwentyFiveDiv").remove();
		}
		if ('${noMainCom}' == 'true') {
			$("#mainComCheckTwentyFiveDiv").remove();
		}
		if ('${noGurrCom}' == 'true') {
			$("#gurrComCheckTwentyFiveDiv").remove();
		}
		selectCheckBoxes("checkTwentyFiveResult", "boxStr");
		
		if (!checkIsNull('${checkTwentyFive.checkTwentyFiveResult}')) {
			var value = '${checkTwentyFive.checkTwentyFiveResult}';
			var checkBoxes = $("input[name='checkTwentyFiveResult']");
			getCheckBoxesValue(value, checkBoxes);
		}
	});

	function mainCustCheckTwentyFiveIdToggle() {
		$("#mainCustCheckTwentyFiveId").toggle(600);
	}

	function gurrCustCheckTwentyFiveIdToggle() {
		$("#gurrCustCheckTwentyFiveId").toggle(600);
	}

	function mainComCheckTwentyFiveIdToggle() {
		$("#mainComCheckTwentyFiveId").toggle(600);
	}

	function gurrComCheckTwentyFiveIdToggle() {
		$("#gurrComCheckTwentyFiveId").toggle(600);
	}

	function complianceToggle() {
		$("#complianceId").toggle(600);
	}

	function postloanManageSuggToggle() {
		$("#postloanManageSuggId").toggle(600);
	}

</script>

<div id="proCheckDiv" class="searchInfo">
	<h3 class="searchTitle">项目审查</h3>
	<div id="proCheckId" class="searchCon">
		<form:form id="proCheckForm" modelAttribute="checkTwentyFive" action="#" method="post" class="form-horizontal">
			<sys:message content="${message}" />
			<table class="fromtable">
				<tr>
					<td class="ft_label">复核方式：</td>
					<!-- 复合方式电话 -->
					<c:if test="${checkTwentyFive.checkTwentyFiveType eq '1' }">
						<td>
							<input type="checkbox" checked="checked" name="checkTwentyFiveType" id="checkTwentyFiveType" value="1">
							电话
						</td>
						<td>&nbsp;</td>
						<td>联系电话:</td>
						<td>
							<form:input path="checkPhone" maxlength="20" class="input-medium" />
						</td>
						<td colspan="2">&nbsp;</td>
						<td>
							<input type="checkbox" name="checkTwentyFiveType" id="checkTwentyFiveType" value="2">
							实地
						</td>
						<td>&nbsp;</td>
						<td>走访地址:</td>
						<td>
							<form:input path="checkAddress" maxlength="300" class="input-medium" />
						</td>
					</c:if>
					<!-- 复合方式实地 -->
					<c:if test="${checkTwentyFive.checkTwentyFiveType eq '2' }">
						<td>
							<input type="checkbox" name="checkTwentyFiveType" id="checkTwentyFiveType" value="1">
							电话
						</td>
						<td>&nbsp;</td>
						<td>联系电话:</td>
						<td>
							<form:input path="checkPhone" maxlength="20" class="input-medium" />
						</td>
						<td colspan="2">&nbsp;</td>
						<td>
							<input type="checkbox" checked="checked" name="checkTwentyFiveType" id="checkTwentyFiveType" value="2">
							实地
						</td>
						<td>&nbsp;</td>
						<td>走访地址:</td>
						<td>
							<form:input path="checkAddress" maxlength="300" class="input-medium" />
						</td>
					</c:if>
					<!-- 复合方式为空 -->
					<c:if test="${checkTwentyFive.checkTwentyFiveType ne '1' && checkTwentyFive.checkTwentyFiveType ne '2'}">
						<td>
							<input type="checkbox" name="checkTwentyFiveType" id="checkTwentyFiveType" value="1">
							电话
						</td>
						<td>&nbsp;</td>
						<td>联系电话:</td>
						<td>
							<form:input path="checkPhone" maxlength="20" class="input-medium" />
						</td>
						<td colspan="2">&nbsp;</td>
						<td>
							<input type="checkbox" name="checkTwentyFiveType" id="checkTwentyFiveType" value="2">
							实地
						</td>
						<td>&nbsp;</td>
						<td>走访地址:</td>
						<td>
							<form:input path="checkAddress" maxlength="300" class="input-medium" />
						</td>
					</c:if>
				</tr>
			</table>
		</form:form>
	</div>
</div>

<div id="mainCustCheckTwentyFiveDiv" class="searchInfo">
	<h3 onclick="mainCustCheckTwentyFiveIdToggle()" class="searchTitle">主借人</h3>
	<div id="mainCustCheckTwentyFiveId" class="searchCon">
		<form:form id="mainCustCheckTwentyFiveForm" modelAttribute="mainCustCheckTwentyFive" action="${ctx}/contractBaseInfo/contractBaseInfo/save" method="post" class="form-horizontal">
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label_radio">基本资料：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkBase" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">网查情况：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkWeb" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">家庭状况：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkFamily" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">财务状况：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkFinancial" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>
<div id="gurrCustCheckTwentyFiveDiv" class="searchInfo">
	<h3 onclick="gurrCustCheckTwentyFiveIdToggle()" class="searchTitle">担保人</h3>
	<div id="gurrCustCheckTwentyFiveId" class="searchCon">
		<form:form id="gurrCustCheckTwentyFiveForm" modelAttribute="gurrCustCheckTwentyFive" action="${ctx}/contractBaseInfo/contractBaseInfo/save" method="post" class="form-horizontal">
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label_radio">基本资料：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkBase" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">网查情况：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkWeb" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">家庭状况：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkFamily" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">财务状况：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkFinancial" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>
<div id="mainComCheckTwentyFiveDiv" class="searchInfo">
	<h3 onclick="mainComCheckTwentyFiveIdToggle()" class="searchTitle">主借企业</h3>
	<div id="mainComCheckTwentyFiveId" class="searchCon">
		<form:form id="mainComCheckTwentyFiveForm" modelAttribute="mainComCheckTwentyFive" action="${ctx}/contractBaseInfo/contractBaseInfo/save" method="post" class="form-horizontal">
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label_radio">基本资料：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkBase" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">网查情况：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkWeb" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">借款用途：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkLoanPurpost" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">银行流水：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkBank" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
				</tr>
				<tr>
					<td class="ft_label_radio">征信报告：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkCredit" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">资产情况：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkProperty" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">抵质押品：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkMortgage" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">经营情况：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkOperate" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>
<div id="gurrComCheckTwentyFiveDiv" class="searchInfo">
	<h3 onclick="gurrComCheckTwentyFiveIdToggle()" class="searchTitle">担保企业</h3>
	<div id="gurrComCheckTwentyFiveId" class="searchCon">
		<form:form id="gurrComCheckTwentyFiveForm" modelAttribute="gurrComCheckTwentyFive" action="${ctx}/contractBaseInfo/contractBaseInfo/save" method="post" class="form-horizontal">
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label_radio">基本资料：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkBase" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">网查情况：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkWeb" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">借款用途：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkLoanPurpost" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">银行流水：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkBank" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
				</tr>
				<tr>
					<td class="ft_label_radio">征信报告：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkCredit" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">资产情况：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkProperty" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">抵质押品：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkMortgage" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">经营情况：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkOperate" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>
<div id="complianceDiv" class="searchInfo">
	<h3 onclick="complianceToggle()" class="searchTitle">合规性</h3>
	<div id="complianceId" class="searchCon">
		<form:form id="complianceForm" modelAttribute="compliance" action="${ctx}/contractBaseInfo/contractBaseInfo/save" method="post" class="form-horizontal">
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label_radio">建档情况：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkArchive" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">流程合规性：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkProc" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">信访咨询费：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkGetFee" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
					<td class="ft_label_radio">实地外访：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkGetAddress" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
				</tr>
				<tr>
					<td class="ft_label_radio">签约条件执行情况：</td>
					<td class="ft_content_radio">
						<form:radiobuttons path="checkSign" items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" cssClass="input-mini" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>
<div id="complianceDiv" class="searchInfo">
	<h3 onclick="postloanManageSuggToggle()" class="searchTitle">25日复核借后管理建议</h3>
	<div id="postloanManageSuggId" class="searchCon">
		<form:form id="postloanManageSuggForm" modelAttribute="checkTwentyFive" action="${ctx}/contractBaseInfo/contractBaseInfo/save" method="post" class="form-horizontal">
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label" style="width: 8%;">审核意见：</td>
					<td class="ft_content" style="width: 92%;">
						<form:checkboxes items="${fns:getDictList('CHECK_RESULT') }" path="checkTwentyFiveResult" itemLabel="label" itemValue="value" htmlEscape="false" />
						<input type="hidden" id="boxStr" value="${postloanManageSugg.checkTwentyFiveResult }">
					</td>
				</tr>
				<tr>
					<td class="ft_label">具体建议：</td>
					<td class="ft_content">
						<form:textarea path="checkTwentyFiveAdvice" cssClass="area-xxlarge" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>
