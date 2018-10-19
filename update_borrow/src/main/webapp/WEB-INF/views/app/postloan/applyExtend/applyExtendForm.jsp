<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#applyExtendFormId").validate({
		submitHandler : saveApplyExtendForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});
	//区分是保存还是提交
	function validateSubmit(v) {
		$("#saveFlag").attr("value", v);
	}
	function applyExtend() {
		$("#applyExtendId").toggle(600);
	}
</script>
<div class="searchInfo">
	<h3 onclick="applyExtend()" class="searchTitle">展期申请详情</h3>
	<div id="applyExtendId" class="searchCon">
		<form:form id="applyExtendFormId" modelAttribute="applyExtend" action="#" method="post" class="form-horizontal">
			<form:hidden path="id" />
			<form:hidden path="contractNo" />
			<input type="hidden" id="saveFlag" name="saveFlag" />
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label">展期金额：</td>
					<td class="ft_content">
						<form:input type="text" path="extendAmount" readonly="true" maxlength="15" htmlEscape="false" class="input-medium number" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">展期费率(%)：</td>
					<td class="ft_content">
						<nobr>
							<form:input type="text" path="extendFeeRate1" maxlength="4" htmlEscape="false" class="input-minii number required" />
							+
							<form:input type="text" path="extendFeeRate2" maxlength="4" htmlEscape="false" class="input-minii  number required" />
							<font color="red">*</font>
						</nobr>
					</td>
					<td class="ft_label">展期保证金：</td>
					<td class="ft_content">
						<form:input type="text" path="extendMargin" maxlength="15" htmlEscape="false" class="input-medium number required" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">展期起始日期：</td>
					<td class="ft_content">
						<input id="extendStartDate" name="extendStartDate" readonly="true" type="text" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value='${applyExtend.extendStartDate }' pattern='yyyy-MM-dd'/>" onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date(),maxDate:'#F{$dp.$D(\'extendEndDate\')}'})" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">展期到期日期：</td>
					<td class="ft_content">
						<input id="extendEndDate" name="extendEndDate" readonly="true" type="text" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value='${applyExtend.extendEndDate }' pattern='yyyy-MM-dd'/>" onclick="WdatePicker({onpicked:dateWhite(this),minDate:'#F{$dp.$D(\'extendStartDate\')||\'new Date()\'}'})" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">展期服务费：</td>
					<td class="ft_content">
						<form:input type="text" path="extendServiceFee" maxlength="15" class="input-medium required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">展期期限(月)：</td>
					<td class="ft_content">
						<form:select path="extendPeriod" class="input-medium required">
							<form:option value="" label=""></form:option>
							<form:options items="${fns:getDictList('PRODUCT_PERIOD_VALUE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font style="color: red">*</font>
					</td>
					<td class="ft_label">应缴利息：</td>
					<td class="ft_content">
						<form:input path="extendInterest" type="text" maxlength="15" class="input-medium number required" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">展期费比例(%)：</td>
					<td class="ft_content">
						<form:input type="text" path="extendFeePercent" maxlength="2" class="input-medium number required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">展期费：</td>
					<td class="ft_content">
						<form:input type="text" path="extendFee" maxlength="15" class="input-medium number required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">应缴总额：</td>
					<td class="ft_content">
						<form:input type="text" path="extendAllAmount" maxlength="15" class="input-medium number required" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">展期理由及风控措施：</td>
					<td class="ft_content" colspan="5">
						<form:textarea path="extendRerson" rows="5" class="textarea-width textarea-style required" maxlength="1000" htmlEscape="false" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">特别申请事项：</td>
					<td class="ft_content" colspan="5">
						<form:textarea path="applySpeciall" rows="5" class="textarea-width textarea-style required" maxlength="1000" htmlEscape="false" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="searchButton" colspan="6" style="text-align: right;">
						<input id="btnSubmit" type="submit" class="btn btn-primary" value="保 存" onclick="validateSubmit('save')" />
						&nbsp;
						<input id="btnSubmitt" type="submit" class="btn btn-primary" value="提 交" onclick="validateSubmit('submit')" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>