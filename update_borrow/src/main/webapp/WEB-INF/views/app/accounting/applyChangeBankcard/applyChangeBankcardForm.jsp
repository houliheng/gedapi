<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
	});

	function validateForm() {
		var newRepBankcardNo = $("#newRepBankcardNo").val();
		var newRepBankcardNoo = $("#newRepBankcardNoo").val();
		var newRepBankcardName = $("#newRepBankcardName").val();
		var newRepBank = $("#newRepBank").val();
		var newRepBankName = $("#newRepBankName").val();
		var newRepBankDistinct = $("#newRepBankDistinct").val();
		var newRepBankDetail = $("#newRepBankDetail").val();
		var reg = /^[0-9]\d*$/;
		if (!reg.test(newRepBankcardNoo)) {
			alertx("请输入正确格式的卡号");
			return false;
		} else if (Number(newRepBankcardNo) != Number(newRepBankcardNoo)) {
			alertx("请确认卡号是否相同。");
			return false;
		} else if (newRepBankcardName == "") {
			alertx("请填写还款账户名称。");
			return false;
		} else if (newRepBank == "") {
			alertx("请填写还款银行。");
			return false;
		} else if (newRepBankName == "") {
			alertx("请填写还款开户行名称。");
			return false;
		} else if (newRepBankDistinct == "" || newRepBankDetail == "") {
			alertx("请填写还款银行地址");
			return false;
		} else {
			loading();
			return true;
		}

	}
	function loadCity() {
		//重置市、县下拉框
		$("#newRepBankCity").empty();
		$("#newRepBankCity").append("<option value=''>请选择</option>");
		var $regCity = $("#s2id_newRepBankCity>.select2-choice>.select2-chosen");
		$regCity.html("请选择");
		$("#newRepBankDistinct").empty();
		$("#newRepBankDistinct").append("<option value=''>请选择</option>");
		var $regDistinct = $("#s2id_newRepBankDistinct>.select2-choice>.select2-chosen");
		$regDistinct.html("请选择");

		$.post("${ctx}/sys/area/treeNode", {
			parentId : $("#newRepBankProvince").val()
		}, function(data) {
			$.each(data, function(i, val) {
				$("#newRepBankCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
			});
		});
	}

	function loadDistinct() {
		$("#newRepBankDistinct").empty();
		$("#newRepBankDistinct").append("<option value=''>请选择</option>");
		var $regDistinct = $("#s2id_newRepBankDistinct>.select2-choice>.select2-chosen");
		$regDistinct.html("请选择");

		$.post("${ctx}/sys/area/treeNode", {
			parentId : $("#newRepBankCity").val()
		}, function(data) {
			$.each(data, function(i, val) {
				$("#newRepBankDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
			});
		});
	}
</script>
<div class="searchInfo">
	<h3 class="searchTitle">借款人银行卡变更</h3>
	<div class="searchCon">
		<form:form id="inputForm" modelAttribute="applyChangeBankcard" action="${ctx}/accounting/applyChangeBankcardIndex/save" onsubmit="return validateForm();" enctype="multipart/form-data" method="post" class="form-horizontal">
			<input type="hidden" name="contractNo" value="${contract.contractNo}" />
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label">还款银行卡号：</td>
					<td class="ft_content">
						<form:input path="newRepBankcardNo" htmlEscape="false" class="input-medium" />
						<font style="color: red">*</font>
					</td>
					<td class="ft_label">还款银行卡号确认：</td>
					<td class="ft_content">
						<form:input path="newRepBankcardNoo" htmlEscape="false" class="input-medium required" />
						<font style="color: red">*</font>
					</td>
					<td class="ft_label">还款账户名称：</td>
					<td class="ft_content">
						<form:input path="newRepBankcardName" htmlEscape="false" class="input-medium required" />
						<font style="color: red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">还款银行：</td>
					<td class="ft_content">
						<form:select path="newRepBank" class="input-medium required">
							<form:option value="" label="请选择" />
							<form:options items="${fns:getDictList('BANKS') }" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font style="color: red">*</font>
					</td>
					<td class="ft_label">还款开户行名称：</td>
					<td class="ft_content">
						<form:input path="newRepBankName" htmlEscape="false" class="input-medium required" />
						<font style="color: red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">还款银行地址：</td>
					<td class="ft_content" colspan="5">
						<form:select path="newRepBankProvince" class="input-medium" onchange="loadCity()">
							<form:option value="">请选择</form:option>
							<form:options items="${provinceMap}" htmlEscape="false" />
						</form:select>
						<span>
							<font>省</font>
						</span>
						<form:select path="newRepBankCity" class="input-medium" onchange="loadDistinct()">
							<form:option value="">请选择</form:option>
							<form:options items="${cityMap}" htmlEscape="false" />
						</form:select>
						<span>
							<font>市</font>
						</span>
						<form:select path="newRepBankDistinct" class="input-medium required">
							<form:option value="">请选择</form:option>
							<form:options items="${distinctMap}" htmlEscape="false" />
						</form:select>
						<span>
							<font>区</font>
						</span>
						<form:input path="newRepBankDetail" htmlEscape="false" class="input-medium required" />
						<font style="color: red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">证件图片：</td>
					<td class="ft_content">
						<input type="file" name="pictureAddress" id="pictureAddress" class="input-medium" />
					</td>
				</tr>
				<tr>
					<td colspan="6" style="text-align: right;">
						<input id="btnCancel" class="btn btn-primary" type="submit" value="银行卡变更" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>