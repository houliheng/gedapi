<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>贴息确认</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		
	});
	function saveForm() {
		loading('正在贴息，请稍等...');
		var formJson = $("#registerForm").serializeJson();
		$.post("${ctx}/accounting/accDiscountStream/save",formJson,function(data){
			if(data){
				closeTip();
				if(data.status == '1'){
					alertx(data.message,function(){
						parent.location.reload();
                        closeJBox();
					});
				}else{
				alertx(data.message);
				}
			}
		});
	}

	</script>
	<style type="text/css">
	.tableList table td, .tableList th {
    	border: 0px solid #d7e3f1;
    	text-align: ;
	}
	
	.ft_label {
    text-align: right;
	}
	</style>
</head>
<body>
	<div class="tableList">
		<sys:message content="${message}"/>
		<div class="searchInfo">
			<h3 class="searchTitle">贴息确认</h3>
			<div id="discountConifrm"  class="ribbon filter">	
				<div id="registerFormDiv">
					<form id="registerForm" method="post">
						<table class="fromTable filter">
						<input type="hidden" name="discountType" value="${type}" readonly="readonly">
							<tr>
								<td class="ft_label" style="text-align: right;">
									合同号：
								</td>
								<td class="ft_content">
									<input type="text" name="contractNo" value="${accDiscount.contractNo}" readonly="readonly">
								</td>
							</tr>
							<tr>
								<td class="ft_label" style="text-align: right;">
									期数：
								</td>
								<td class="ft_content">
									<input type="text" name="periodNum" value="${accDiscount.periodNum}" readonly="readonly">
								</td>
							</tr>
							<tr>
								<td class="ft_label" style="text-align: right;">
									应贴息金额：
								</td>
								<td class="ft_content">
									<input type="text" name="discountFee" value="${accDiscount.discountFee}" readonly="readonly">
								</td>
							</tr>
							<tr>
								<td class="ft_label" style="text-align: right;">
									实贴息金额：
								</td>
								<td class="ft_content">
									<input type="text"  name ="factDiscountFee" value="${accDiscount.discountFee}" readonly="readonly">
								</td>
							</tr>
							<tr>
								<td class="ft_label" style="text-align: right;">
									贴息日期：
								</td>
								<td class="ft_content">
									<input  name ="discountDate" type="text" readonly="readonly" maxlength="20" class="input-medium"
						value="<fmt:formatDate value="${accDiscount.discountDate}" type="both" pattern="yyyy-MM-dd" />"/>
								</td>
							</tr>
							<tr>
								<td class="ft_label" style="text-align: right;">
									贴息账户：
								</td>
								<td class="ft_content">
									<input type="text" name="fromDiscountAccount" value="${accDiscount.discountAccount}" readonly="readonly">
								<!-- 	<input type="text" name="fromDiscountAccount" value="6228480018534869118" readonly="readonly"> -->
									<input type="hidden" name="toAccount" value="${toAccount}" readonly="readonly">
									<input type="hidden" name="fromCapitalNo" value="${fromCapitalNo}" readonly="readonly">
									<input type="hidden" name="toCapitalNo" value="${toCapitalNo}" readonly="readonly">
								</td>
							</tr>
							<tr>
								<td style="text-align:right">
								</td>
								<td style="text-align:left">
									<input id="btnSubmit"  class="btn btn-primary setwidths" type="button" onclick="saveForm();" value="确 认"/>
									<input id="btnCancel" class="btn btn-primary" type="button" value="取 消" onclick="closeJBox();" />
								</td>
							</tr>
						</table>
					</form>
				</div>	
			</div>
		</div>
	</div>
</body>
</html>

