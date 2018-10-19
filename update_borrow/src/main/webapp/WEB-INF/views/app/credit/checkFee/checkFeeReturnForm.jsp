<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>外访费返还</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#checkFeeForm").validate({
		submitHandler : saveCheckFee,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			checkReq(error, element)
		}
		});
		document.getElementById("returnCheckFee").value = outputmoney("${checkFee.returnCheckFee}");
		adjustTextareaLength("descriptionReturn", "preDescriptionReturn");
	});

	//保存外访费信息
	function saveCheckFee() {
		var value1 = $("#returnCheckFee").val().replace(/,/g, "");
		$("#returnCheckFee").val(value1);
		top.$.jBox.tip.mess = null;
		var checkFee = $("#checkFeeForm").serializeJson();
		$.post("${ctx}/credit/checkFee/saveReturn", checkFee, function(data) {
			if (data) {
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						parent.parent.$("#checkFeeVOForm").submit();
						parent.parent.$.jBox.close('box');
					});
				} else {
					alertx(data.message);
				}
			}
		});
	}
</script>
</head>
<body>
	<form:form id="checkFeeForm" modelAttribute="checkFee" action="#" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="applyNo" />
		<sys:message content="${message}" />
		<h3 class="infoListTitle">外访费返还</h3>
		<div class="filter">
			<div class="control-group">
				<label class="control-label">返还时间：</label>
				<div class="controls">
					<input id="returnTime" name="returnTime" type="text" readonly="true" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${checkFee.returnTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({ onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'checkDate\')}',maxDate:new Date(),isShowClear:false});" />
					<input id="checkDate" name="checkDate" type="hidden" readonly="true" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${checkFee.checkDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({ onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',isShowClear:false});" />
					<font color="red">*</font>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">操作人：</label>
				<div class="controls">
					<form:input path="returnUserName" htmlEscape="false" maxlength="20" class="input-xlarge required" />
					<font color="red">*</font>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">返还金额(元)：</label>
				<div class="controls">
					<form:input path="returnCheckFee" htmlEscape="false" class="input-xlarge required" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" />
					<font color="red">*</font>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">备注：</label>
				<div class="controls">
					<pre class="pre-style input-xxlarge" id="preDescriptionReturn"></pre>
					<form:textarea path="descriptionReturn" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge textarea-style " onkeyup="adjustTextareaLength('descriptionReturn','preDescriptionReturn')" />
				</div>
			</div>
			<div class="form-actions">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
				&nbsp;
				<input id="btnSubmit3" class="btn btn-primary" type="button" value="关 闭" onclick="parent.parent.$.jBox.close('box')" />
			</div>
		</div>
	</form:form>
</body>
</html>