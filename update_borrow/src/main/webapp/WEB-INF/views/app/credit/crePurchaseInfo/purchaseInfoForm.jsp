<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>关联企业管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#purchaseInfoForm").validate({
		submitHandler : function(form) {
			loading();
			//保存前先格式化数据
			var commodityNum = $("#commodityNum").val().replace(/,/g, "");
			$("#commodityNum").val(commodityNum);
			var commodityPrePrice = $("#commodityPrePrice").val().replace(/,/g, "");
			$("#commodityPrePrice").val(commodityPrePrice);
			var commodityDiscountPrice = $("#commodityDiscountPrice").val().replace(/,/g, "");
			$("#commodityDiscountPrice").val(commodityDiscountPrice);
			var commodityMargin = $("#commodityMargin").val().replace(/,/g, "");
			$("#commodityMargin").val(commodityMargin);
			var applyNo=$("#applyNo").val();
			var param = $("#purchaseInfoForm").serialize();
			$.post("${ctx}/credit/purchaseInfo/save", param, function(data) {
				if (data) {
					closeTip();
					if (data.status == '1') {
						alertx(data.message, function() {
							parent.$.loadDiv('purchaseInfoListDiv', '${ctx}/credit/purchaseInfo/list', {
							applyNo : '${purchaseInfo.applyNo}',
							}, 'post');
							closeJBox();
						});
					} else {
						alertx(data.message);
					}
				}
			});
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
		});
	});
	
	
	function getCommodityMargin(){
		var commodityNum = $("#commodityNum").val().replace(/,/g, "");
		if(!(commodityNum%1 === 0)){
			alertx("采购商品数量应为整数！");
			return;
		}
		var commodityPrePrice = $("#commodityPrePrice").val().replace(/,/g, "");
		var commodityDiscountPrice = $("#commodityDiscountPrice").val().replace(/,/g, "");
		if(commodityNum!=null&&commodityNum!=''&&commodityPrePrice!=null&&commodityPrePrice!=''&&commodityDiscountPrice!=null&&commodityDiscountPrice!=''){
			commodityPrePrice=parseFloat(commodityPrePrice); 
			commodityDiscountPrice=parseFloat(commodityDiscountPrice); 
			commodityNum=parseFloat(commodityNum); 
			var marginTemp=parseFloat(commodityPrePrice-commodityDiscountPrice);
			if(marginTemp==''){
				var commodityMargin='0';
			}else{
				var commodityMargin=outputmoney(marginTemp*commodityNum);
			}
			$("#commodityMargin").val(commodityMargin);
			
		}
	}
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">采购商品信息</h3>
		<br />
		<div class="searchCon">
			<form:form id="purchaseInfoForm" modelAttribute="purchaseInfo" action="${ctx}/credit/purchaseInfo/save" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="applyNo" />
				<sys:message content="${message}" />
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">采购商品名称：</td>
							<td class="ft_content">
								<form:input path="commodityName" maxlength="18" htmlEscape="false" class=" input-medium required" />
								<font color="red">*</font>
							</td>
							<td class="ft_label">商品规格：</td>
							<td class="ft_content">
								<form:input path="commodityFormat" maxlength="18" htmlEscape="false" class=" input-medium required" />
								<font color="red">*</font>
							</td>
							<td class="ft_label">采购商品数量：</td>
							<td class="ft_content"><!-- onkeyup="value=value.match(/^-?[0-9]\d*$/) -->
								<form:input onkeyup="keyPress11(this);" onblur="getCommodityMargin();"  path="commodityNum" maxlength="18" htmlEscape="false" class=" input-medium number required" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">采购商品单位：</td>
							<td class="ft_content">
								<form:input  path="commodityCompany" maxlength="18" htmlEscape="false" class=" input-medium required" />
								<font color="red">*</font>
							</td>
							<td class="ft_label">采购商品单位原价格(元)：</td>
							<td class="ft_content">
								<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);getCommodityMargin();" path="commodityPrePrice" maxlength="18" htmlEscape="false" class=" input-medium required"/>
								<font color="red">*</font>
							</td>
							<td class="ft_label">采购商品优惠后价格(元)：</td>
							<td class="ft_content">
								<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);getCommodityMargin();" path="commodityDiscountPrice" maxlength="18" htmlEscape="false" class=" input-medium required"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">货物利差(元)：</td>
							<td class="ft_content">
								<form:input  path="commodityMargin" maxlength="18" htmlEscape="false" class=" input-medium" readonly="true"/>
							</td>
						</tr>
					</table>
					<div class="searchButton" id="buttonDiv">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
					</div>
			</form:form>
		</div>
	</div>
</body>
</html>