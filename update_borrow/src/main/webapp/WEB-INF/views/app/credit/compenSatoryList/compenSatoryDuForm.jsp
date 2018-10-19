<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代偿</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$(document).ready(function() {
				//$("#name").focus();
				$("#inputForm").validate({
					submitHandler: function(form){
						loading('正在提交，请稍等...');
						var compensatoryAmount = $("#compensatoryAmount").val().replace(/,/g, "");
					    $("#compensatoryAmount").val(compensatoryAmount);
						var param = $("#inputForm").serialize();
						$.post("${ctx}/credit/compensatoryDetail/saveDetail", param, function(data) {
							if (data) {
								closeTip();
								if (data.status == '1') {
									alertx(data.message, function() {
										loading('正在刷新，请稍等...');
										parent.location.reload();
										closeJBox();
									});
								} else {
									alertx(data.message, function() {
										loading('正在刷新，请稍等...');
										parent.location.reload();
										closeJBox();
									});
								}
							}
						});
					},
					errorContainer: "#messageBox",
					errorPlacement: function(error, element) {
						$("#messageBox").text("输入有误，请先更正。");
						if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
							error.appendTo(element.parent().parent());
						} else {
							error.insertAfter(element);
						}
					}
				});
			});
		});
		function chooseAccountList(accountType,contractNo,periodNum){
			$("#custId").empty();
			$("#compensatoryAccount").val("");
			$("#compensatoryName").val("");
			$("#s2id_custId>.select2-choice>.select2-chosen").html("");
			$.post("${ctx}/credit/compenSatoryList/compensatoryAccount", {
				"accountType" : accountType,
				"contractNo" : contractNo,
				"periodNum":periodNum
				}, function(data) {
					var opstr = "<option value=''></option>";
					$.each(data, function(i, val) {
						//opstr += "<option value='"+val.custId+"'>" + val.custId +"-"+ val.compensatoryAccount + "</option>";
						opstr += "<option value='"+val.custId+"'>" + val.compensatoryAccount + "</option>";
					});
					$("#custId").append(opstr); 
				});
		}
		function showAccountName(){
			var custId = $('#custId option:selected').val();
			var type = $('#redioId input[name="compensatoryType"]:checked ').val();
			$("#compensatoryAccount").val($("#custId").find("option:selected").text());
			$.post("${ctx}/credit/compenSatoryList/showAccountName?custId="+custId, null, function(data) {
				if (data) {
					if (data.status == 1) {//保存成功
						$("#compensatoryName").val(data.message);
					}else{
						alertx(data.message);
					}
				}
			});
		}
	</script>

</head>
<body>
	<form:form id="inputForm" modelAttribute="compensatoryDetail" action="#" method="post" class="form-horizontal">
	<%-- <form id="inputForm" action="${ctx}/credit/compensatoryDetail/save" method="post" class="form-horizontal"> --%>
		<sys:message content="${message}"/>	
		<table class="fromTable filter">
			<%-- <tr>
				<td class="ft_label">代偿账户：</td>
				<td class="ft_content">
					<select name="id" id="id" class="input-medium required" style="width:176px;">
						<c:forEach items="${compensatoryAccountLists}" var="compenSatoryAccount">
							<option value="${compenSatoryAccount.id }" <c:if test="${compensatoryAccountList[0].id == compenSatoryAccount.id}">selected="selected"</c:if>>${compenSatoryAccount.compensatoryAccount }</option>
						</c:forEach>
					</select>
					<font color="red">*</font>
				</td>
			</tr> --%>
			<tr>
				<td class="ft_label">代偿类型：</td>
				<td id="redioId" class="ft_content"   colspan="7">
					<input type="radio" name="compensatoryType" value="1" id="radio_1" class="required" onclick="chooseAccountList(1,'${compensatoryDetail.contractNo}','${compensatoryDetail.periodNum}')">
					<label for="radio_yes">自有担保人</label>&nbsp;&nbsp;
					<input type="radio" name="compensatoryType" value="2" id="radio_2" class="required"  onclick="chooseAccountList(2,'${compensatoryDetail.contractNo}','${compensatoryDetail.periodNum}')">
					<label for="radio_no">担保公司</label>&nbsp;&nbsp;
					<input type="radio" name="compensatoryType" value="3" id="radio_3" class="required"  onclick="chooseAccountList(3,'${compensatoryDetail.contractNo}','${compensatoryDetail.periodNum}')">
					<label for="radio_no">平台保证金账户</label>&nbsp;&nbsp;
					<font style="color: red">*</font>
				</td>
			</tr>
			
			<tr>
				<td class="ft_label">代偿账户：</td>
				<td class="ft_content">
					<form:select id="custId" path="custId" onchange="showAccountName()" class="input-medium required" cssStyle="width:164px;">
						<form:option value=""></form:option>
						<form:options items="${compensatoryAccountList }" htmlEscape="false" itemLabel="productName" itemValue="id" />
					</form:select>
					<font color="red">*</font>
					<input type="hidden" id="compensatoryAccount" name="compensatoryAccount" value="" /> 
				</td>
			</tr>
			<tr>
				<td class="ft_label">代偿账户名称：</td>
				<td class="ft_content"  colspan="7">
					<input type="text" readonly="readonly" id="compensatoryName" name="compensatoryName" value="" maxlength="18" htmlEscape="false" class=" input-medium" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">代偿金额：</td>
				<td class="ft_content"  colspan="7">
					<input type="text" readonly="readonly" id="compensatoryAmount" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" name="compensatoryAmount" value="${compensatoryDetail.compensatoryAmount}" maxlength="18" htmlEscape="false" class=" input-medium required" />
					<font color="red">*</font>
					<input type="hidden" value="${compensatoryDetail.compensatoryAmount}" id="compensatoryAmountOld" name="compensatoryAmountOld">
				</td>
			</tr>
			<tr>
				<td class="ft_label">代偿日期：</td>
				<td class="ft_content"  colspan="7"><!-- yyyy-MM-dd HH:mm:ss -->
					<input  type="text" readonly="readonly" maxlength="20" class="input-medium"
						value="<fmt:formatDate value="${newDate }" type="both" pattern="yyyy-MM-dd" />"
					/>
					<font color="red">*</font>
					<input type="hidden" name="contractNo" value="${compensatoryDetail.contractNo}" id="contractNo" >
					<input type="hidden" name="periodNum" value="${compensatoryDetail.periodNum}" id="periodNum" >
				</td>
			</tr>
		</table>
		<div class="searchButton" id="buttonDiv">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
		</div>
	<!-- </form> -->
	</form:form>
</body>
</html>