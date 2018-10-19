<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>股权尽调</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.parent{
			position:relative;
		}
		.son_left{
			position:absolute;
		}
		.son_right{
			position:absolute;
			right:50px;
		}
	</style>
	<script type="text/javascript">
	$(document).ready(function() {
		var isStockPost='${isStockPost}';
		if(isStockPost=='0'){
			$(".son_right").hide();
		}
		
		if("${stockInfo.registeredCapital}"==0){
			$("#registeredCapital").val("0.00");
		}else{
			var registeredCapital=new Number("${stockInfo.registeredCapital}");
			$("#registeredCapital").val(outputmoney(registeredCapital));
		}
		if("${stockInfo.paidInCapital}"==0){
			$("#paidInCapital").val("0.00");
		}else{
			var paidInCapital=new Number("${stockInfo.paidInCapital}");
			$("#paidInCapital").val(outputmoney(paidInCapital));
		}
		
		if("${stockInfo.businessValuation}"==0){
			$("#businessValuation").val("0.00");
		}else{
			var businessValuation=new Number("${stockInfo.businessValuation}");
			$("#businessValuation").val(outputmoney(businessValuation));
		} 
		
		if("${stockInfo.stockServiceFee}"==0){
			$("#stockServiceFee").val("0.00");
		}else{
			var stockServiceFee=new Number("${stockInfo.stockServiceFee}");
			$("#stockServiceFee").val(outputmoney(stockServiceFee));
		}
		
		if("${stockInfo.additionMoney}"==0){
			$("#additionMoney").val("0.00");
		}else{
			var additionMoney=new Number("${stockInfo.additionMoney}");
			$("#additionMoney").val(outputmoney(additionMoney));
		}
		
		if("${stockInfo.oneTransferAmount}"==0){
			$("#oneTransferAmount").val("0.00");
		}else{
			var oneTransferAmount=new Number("${stockInfo.oneTransferAmount}");
			$("#oneTransferAmount").val(outputmoney(oneTransferAmount));
		}
		
		if("${stockInfo.dividendAmount}"==0){
			$("#dividendAmount").val("0.00");
		}else{
			var dividendAmount=new Number("${stockInfo.dividendAmount}");
			$("#dividendAmount").val(outputmoney(dividendAmount));
		}
		
		if("${stockInfo.addServiceFee}"==0){
			$("#addServiceFee").val("0.00");
		}else{
			var addServiceFee=new Number("${stockInfo.addServiceFee}");
			$("#addServiceFee").val(outputmoney(addServiceFee));
		}
		 $.loadDiv("targetListDiv", "${ctx }/credit/stockAssesseTarget/list", {
			applyNo : '${actTaskParam.applyNo}',
			taskDefKey:'${actTaskParam.taskDefKey}',
			grade:'${grade}'
		}, "post"); 
			$("#inputForm").validate({
			submitHandler : function(form) {
				confirmx("保存提交后，审批意见不可更改！", function() {
					loading('正在提交，请稍等...');
					//格式化数据
				     var registeredCapital = $("#registeredCapital").val().replace(/,/g, "");
				     $("#registeredCapital").val(registeredCapital);
				     var paidInCapital = $("#paidInCapital").val().replace(/,/g, "");
				     $("#paidInCapital").val(paidInCapital);
				     var businessValuation = $("#businessValuation").val().replace(/,/g, "");
				     $("#businessValuation").val(businessValuation);
				     var stockServiceFee = $("#stockServiceFee").val().replace(/,/g, "");
				     $("#stockServiceFee").val(stockServiceFee);
				     var additionMoney = $("#additionMoney").val().replace(/,/g, "");
				     $("#additionMoney").val(additionMoney);
				     var oneTransferAmount = $("#oneTransferAmount").val().replace(/,/g, "");
				     $("#oneTransferAmount").val(oneTransferAmount);
				     var quasiShareRatio = $("#quasiShareRatio").val().replace(/,/g, "");
				     $("#quasiShareRatio").val(quasiShareRatio);
				     var increaseProportion = $("#increaseProportion").val().replace(/,/g, "");
				     $("#increaseProportion").val(increaseProportion);
				     var oneTransferProportion = $("#oneTransferProportion").val().replace(/,/g, "");
				     $("#oneTransferProportion").val(oneTransferProportion);
				     var dividendAmount = $("#dividendAmount").val().replace(/,/g, "");
				     $("#dividendAmount").val(dividendAmount);
				     var addServiceFee = $("#addServiceFee").val().replace(/,/g, "");
				     $("#addServiceFee").val(addServiceFee);
                     var branchName = $("#branchName").val();
				     var branchNo = $("#branchNo").val();
				     if(branchName.indexOf("，") > 0 )
				     {
				    	 $("#branchName").val(branchName.replace(/，/ig,','));
				     }
				     if(branchNo.indexOf("，") > 0 )
				     {
				    	 $("#branchNo").val(branchNo.replace(/，/ig,','));
				     }
				     var areaName = $("#areaName").val();
				     var areaNo = $("#areaNo").val();
				     if(areaName.indexOf("，") > 0 )
				     {
				    	 $("#areaName").val(areaName.replace(/，/ig,','));
				     }
				     if(areaNo.indexOf("，") > 0 )
				     {
				    	 $("#areaNo").val(areaNo.replace(/，/ig,','));
				     }
				     var largeAreaName = $("#largeAreaName").val();
				     var largeAreaNo = $("#largeAreaNo").val();
				     if(largeAreaName.indexOf("，") > 0 )
				     {
				    	 $("#largeAreaName").val(largeAreaName.replace(/，/ig,','));
				     }
				     if(largeAreaNo.indexOf("，") > 0 )
				     {
				    	 $("#largeAreaNo").val(largeAreaNo.replace(/，/ig,','));
				     }
				     var headName = $("#headName").val();
				     var headNo = $("#headNo").val();
				     if(headName.indexOf("，") > 0 )
				     {
				    	 $("#headName").val(headName.replace(/，/ig,','));
				     }
				     if(headNo.indexOf("，") > 0 )
				     {
				    	 $("#headNo").val(headNo.replace(/，/ig,','));
				     }
					var data = $("#inputForm").serializeJson();
					$.post("${ctx}/credit/stockInfo/save", data, function(data) {
						if (data) {
							closeTip();
							if (data.status == 1) {
								alertx(data.message, function() {
									//window.location.reload();
									goToPage('${ctx}/credit/stockTaskReceive/list', 'stockInfoSkipId');
									
								});
							} else {
								alertx(data.message);
							}
						}
					});
				});
			
			}, 
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
					error.appendTo(element.parent().parent());
				} else {
					checkReq(error, element);
				}
			}
			});
		});	
	
	function employeeValidate(obj){
		obj.value=obj.value.replace(/[^\u4e00-\u9fa5,，]/g,'');
	}
	function employeeNoValidate(obj){
		obj.value=obj.value.replace(/[^\d\,，]/g,'');
	}
	function stockTrans(){
		var urlsuffix = "?stockTaskReceiveId=${stockTaskReceiveId}&stockType=1&applyNo=${actTaskParam.applyNo}&taskId=${actTaskParam.taskId}&headUrl=${actTaskParam.headUrl}&taskDefKey=${actTaskParam.taskDefKey}&procDefId=${actTaskParam.procDefId}&status=${actTaskParam.status}&procInstId=${actTaskParam.procInstId}";
		var url = "${ctx}/credit/stockTaskDistribute/gotoLoadOrgUser" + urlsuffix;
		var width = $(document).width() - 100;
		width = Math.max($(document).width() - 100, 800);
		var height = $(document).height() - 100;
		height = Math.min($(document).height() - 100, 400);
		openJBox("viewImageBox", url, "股权尽调人员选择", width, height);
	}
	
	function goToList(){
		goToPage('${ctx}/credit/stockTaskReceive/list', 'stockInfoSkipId');
	}
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="stockInfo" 
			action="${ctx}/credit/stockInfo/save"  method="post" class="form-horizontal">
		<form:hidden path="id" />		
		<input type="hidden" name="stockTaskReceiveId" value="${stockTaskReceiveId }">
		<input type="hidden" name="applyNo" id="applyNo" value="${actTaskParam.applyNo }">
		<h3 class="searchTitle parent">
				<span class="son_left">股权估值基础信息</span>
				<input id="btnSubmit" onclick="stockTrans();" class="btn btn-primary son_right" type="button" value="转办"/>
		</h3>
		<!-- 跳转用a标签 --><a id="stockInfoSkipId" target="_parent" ></a>
		<div id="valuePart1">
			<table class="fromTable filter">
				<tr>
					<td class="ft_label">目标公司名称：</td>
					<td class="ft_content" colspan="3">
						<input type="text" name="targetCompany" id="targetCompany"  class="input-medium required" maxlength="32" value="${stockInfo.targetCompany}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 420px"  />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">持股公司名称：</td>
					<td class="ft_content" colspan="3">
						<input type="text" name="stockCompany" id="stockCompany"  class="input-medium required" maxlength="32" value="${stockInfo.stockCompany}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 420px"  />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">注册资本(元)：</td>
					<td class="ft_content">
						<form:input path="registeredCapital" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"  maxlength="20" id="registeredCapital" htmlEscape="false" class="input-medium number required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">实缴资本(元)：</td>
					<td class="ft_content">
						<form:input path="paidInCapital" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"  maxlength="18" id="paidInCapital" htmlEscape="false" class="input-medium number required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">企业估值(元)：</td>
					<td class="ft_content">
						<form:input path="businessValuation" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"   maxlength="18" id="businessValuation" htmlEscape="false" class="input-medium number required" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">服务费金额(元)：</td>
					<td class="ft_content">
						<form:input path="stockServiceFee" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"  maxlength="18" id="stockServiceFee" htmlEscape="false" class="input-medium number required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">拟增资金额(元)：</td>
					<td class="ft_content">
						<form:input path="additionMoney" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"  maxlength="18" id="additionMoney" htmlEscape="false" class="input-medium number required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">一元转让金额(元)：</td>
					<td class="ft_content">
						<form:input path="oneTransferAmount" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"  maxlength="18" id="oneTransferAmount" htmlEscape="false" class="input-medium number required" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">拟占股比例(%)：</td>
					<td class="ft_content">
						<form:input path="quasiShareRatio" onblur="this.value=gdpMoney(this.value);" onkeyup="gdpMax(this)" maxlength="6"   id="quasiShareRatio" htmlEscape="false" class="input-medium number required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">增资占股比例(%)：</td>
					<td class="ft_content">
						<form:input path="increaseProportion" onblur="this.value=gdpMoney(this.value);" onkeyup="gdpMax(this)" maxlength="6" id="increaseProportion" htmlEscape="false" class="input-medium number required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">一元转让拟占股比例(%)：</td>
					<td class="ft_content">
						<form:input path="oneTransferProportion" onblur="this.value=gdpMoney(this.value);" onkeyup="gdpMax(this)" maxlength="6" id="oneTransferProportion" htmlEscape="false" class="input-medium number required" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">年度分红数额(元)：</td>
					<td class="ft_content">
						<form:input path="dividendAmount" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"  maxlength="18" id="dividendAmount" htmlEscape="false" class="input-medium required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">增值服务费金额(元)：</td>
					<td class="ft_content">
						<form:input path="addServiceFee" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"  maxlength="18" id="addServiceFee" htmlEscape="false" class="input-medium number required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">提名董事姓名：</td>
					<td class="ft_content">
						<form:input path="chairmanName"  maxlength="18" id="chairmanName" htmlEscape="false" class="input-medium required" />
						<font color="red">*</font>
					</td>
				</tr>
				<!--  <tr>
					<td class="ft_label">年度考核点及指标：</td>
				</tr>
				<tr>
					<td colspan="6">
					
					</td>
				</tr>  -->
			</table>
		</div>	
		<div id="targetListDiv"></div> 
		<h3 class="searchTitle">股权端风控措施</h3>
		<div id="valuePart2">
			<table class="fromTable filter">
				<tr>
					<td class="ft_label" style="width: 10%;"></td>
					<td class="ft_content" style="width: 90%;">
						<pre class="textareaWidth pre-style"  id="preContractClause"></pre>
						<pre class="textareaWidth pre-style"  id="preGuaranteeMeasures"></pre>
						<pre class="textareaWidth pre-style"  id="preOtherControMeasures"></pre>
					</td>
				</tr>
				<tr>
					<td class="ft_label" style="width: 10%;">股权端合同风控条款：</td>
					<td class="ft_content" style="width: 90%;">
						<form:textarea id="contractClause" path="contractClause" style="overflow-y:visible"  htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('contractClause','preContractClause'),this.value=this.value.replace(/[, ]/g,'')"/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label" style="width: 10%;">担保措施：</td>
					<td class="ft_content" style="width: 90%;">
						<form:textarea id="guaranteeMeasures" path="guaranteeMeasures" style="overflow-y:visible"  htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('guaranteeMeasures','preGuaranteeMeasures'),this.value=this.value.replace(/[, ]/g,'')"/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label" style="width: 10%;">其他风控措施：</td>
					<td class="ft_content" style="width: 90%;">
						<form:textarea id="otherControMeasures" path="otherControMeasures" style="overflow-y:visible"  htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('otherControMeasures','preOtherControMeasures'),this.value=this.value.replace(/[, ]/g,'')"/>
						<font color="red">*</font>
					</td>
				</tr>
			</table>
		</div>
		<h3 class="searchTitle">审批意见</h3>
		<div id="valuePart3">
			<table class="fromTable filter">
					<tr>
						<td class="ft_label"></td>
						<td class="ft_content">
							<pre class="textareaWidth pre-style"  id="preSuggestionBranch"></pre>
							<pre class="textareaWidth pre-style"  id="preSuggestionArea"></pre>
							<pre class="textareaWidth pre-style"  id="preSuggestionLargeArea"></pre>
					    	<pre class="textareaWidth pre-style"  id="preSuggestionHead"></pre>
						</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>	
					<tr class="branchPart">
						<td class="ft_label" style="width: 10%;">分公司团队尽调意见：</td>
						<td class="ft_content" colspan="4">
							<form:textarea path="suggestionBranch"  htmlEscape="false" style="overflow-y:visible" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('suggestionBranch','preSuggestionBranch'),this.value=this.value.replace(/[, ]/g,'')"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr class="branchPart">
						<td></td>
						<td class="ft_label" style="width: 10%;">尽调团队人员姓名：</td>
						<td class="ft_content" style="width: 40%;">
							<input type="text" name="branchName" id="branchName"  placeholder="只允许输入中文和间隔符为逗号（中文，英文,）" class="input-medium required" maxlength="70" value="${stockInfo.branchName}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 320px"  onkeyup="employeeValidate(this)"/>
						</td>
						<td class="ft_label" style="width: 10%;">工号：</td>
						<td class="ft_content" style="width: 40%;">
							<input type="text" name="branchNo" id="branchNo" placeholder="只允许输入数字和间隔符为逗号（中文，英文,）" class="input-medium required" maxlength="50" value="${stockInfo.branchNo}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 320px"  onkeyup="employeeNoValidate(this)"/>
						</td>
					</tr>
					<tr class="areaPart">
						<td class="ft_label" style="width: 10%;">区域团队尽调意见：</td>
						<td class="ft_content" colspan="4">
							<form:textarea id="suggestionArea" style="overflow-y:visible" path="suggestionArea"  htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('suggestionArea','preSuggestionArea'),this.value=this.value.replace(/[, ]/g,'')"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr class="areaPart">
						<td></td>
						<td class="ft_label" style="width: 10%;">尽调团队人员姓名：</td>
						<td class="ft_content" style="width: 40%;">
							<input type="text" name="areaName" id="areaName"  placeholder="只允许输入中文和间隔符为逗号（中文，英文,）" class="input-medium required" maxlength="50" value="${stockInfo.areaName}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 320px" onkeyup="employeeValidate(this)" />
						</td>
						<td class="ft_label" style="width: 10%;">工号：</td>
						<td class="ft_content" style="width: 40%;">
							<input type="text" name="areaNo" id="areaNo"  placeholder="只允许输入数字和间隔符为逗号（中文，英文,）" class="input-medium required" maxlength="50" value="${stockInfo.areaNo}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 320px"  onkeyup="employeeNoValidate(this)"/>
						</td>
					</tr>
					
					<tr class="largeAreaPart">	
						<td class="ft_label">大区团队尽调意见：</td>
						<td class="ft_content" style="width: 90%;"  colspan="4">
							<form:textarea id="suggestionLargeArea" style="overflow-y:visible" path="suggestionLargeArea"  htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('suggestionLargeArea','preSuggestionLargeArea'),this.value=this.value.replace(/[, ]/g,'')"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr class="largeAreaPart">
						<td></td>
						<td class="ft_label" style="width: 10%;">尽调团队人员姓名：</td>
						<td class="ft_content" style="width: 40%;">
							<input type="text" name="largeAreaName" id="largeAreaName"  placeholder="只允许输入中文和间隔符为逗号（中文，英文,）" class="input-medium required" maxlength="50" value="${stockInfo.largeAreaName}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 320px"  onkeyup="employeeValidate(this)"/>
						</td>
						<td class="ft_label" style="width: 10%;">工号：</td>
						<td class="ft_content" style="width: 40%;">
							<input type="text" name="largeAreaNo" id="largeAreaNo"  placeholder="只允许输入数字和间隔符为逗号（中文，英文,）" class="input-medium required" maxlength="50" value="${stockInfo.largeAreaNo}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 320px"  onkeyup="employeeNoValidate(this)"/>
						</td>
					</tr>
					
					<tr class="headPart">	
						<td class="ft_label">总公司团队尽调意见： </td>
						<td class="ft_content" style="width: 90%;"  colspan="4">
							<form:textarea id="suggestionHead" path="suggestionHead" style="overflow-y:visible"  htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('suggestionHead','preSuggestionHead'),this.value=this.value.replace(/[, ]/g,'')"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr  class="headPart">
						<td></td>
						<td class="ft_label" style="width: 10%;">尽调团队人员姓名：</td>
						<td class="ft_content style="width: 40%;">
							<input type="text" name="headName" id="headName"  placeholder="只允许输入中文和间隔符为逗号（中文，英文,）" class="input-medium required" maxlength="50" value="${stockInfo.headName}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 320px"  onkeyup="employeeValidate(this)"/>
						</td>
						<td class="ft_label" style="width: 10%;">工号：</td>
						<td class="ft_content" style="width: 40%;">
							<input type="text" name="headNo" id="headNo"  placeholder="只允许输入数字和间隔符为逗号（中文，英文,）" class="input-medium required" maxlength="50" value="${stockInfo.headNo}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 320px"  onkeyup="employeeNoValidate(this)"/>
						</td>
					</tr>
			</table>
		</div>
		<div class="form-actions" style="text-align: right;">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存"/>
 			<input id="btnPrint" class="btn btn-primary noprint" type="button" value="打印本页面" onclick="window.print()" />
		</div>
	</form:form>
	<c:if test="${grade=='4'}"><!-- 分公司 -->
		<script type="text/javascript">
			$(document).ready(function() {
				$(".areaPart").hide();
				$(".largeAreaPart").hide();
				$(".headPart").hide();
				var branchStatus='${branchStatus}';
				if(branchStatus=='1'){
					$("#suggestionBranch").attr("readonly","readonly");
					$("#branchName").attr("readonly","readonly");
					$("#branchNo").attr("readonly","readonly");
				}
			});
		</script>
	</c:if>
	<c:if test="${grade=='3'}"><!-- 区域 -->
		<script type="text/javascript">
			$(document).ready(function() {
				$("#suggestionBranch").attr("readonly","readonly");
				$("#branchName").attr("readonly","readonly");
				$("#branchNo").attr("readonly","readonly");
				$(".largeAreaPart").hide();
				$(".headPart").hide();
				var areaStatus='${areaStatus}';
				if(areaStatus=='1'){
					$("#suggestionArea").attr("readonly","readonly");
					$("#areaName").attr("readonly","readonly");
					$("#areaNo").attr("readonly","readonly");
				}
			});
		</script>
	</c:if>
	<c:if test="${grade=='2'}"><!-- 大区 -->
		<script type="text/javascript">
			$(document).ready(function() {
				$("#suggestionBranch").attr("readonly","readonly");
				$("#branchName").attr("readonly","readonly");
				$("#branchNo").attr("readonly","readonly");
				$("#suggestionArea").attr("readonly","readonly");
				$("#areaName").attr("readonly","readonly");
				$("#areaNo").attr("readonly","readonly");
				$(".headPart").hide();
				var largeAreaStatus='${largeAreaStatus}';
				if(largeAreaStatus=='1'){
					$("#suggestionLargeArea").attr("readonly","readonly");
					$("#largeAreaName").attr("readonly","readonly");
					$("#largeAreaNo").attr("readonly","readonly");
				}
			});
		</script>
	</c:if>
	<c:if test="${grade=='1'}"><!-- zongbu -->
		<script type="text/javascript">
			$(document).ready(function() {
				$("#suggestionBranch").attr("readonly","readonly");
				$("#branchName").attr("readonly","readonly");
				$("#branchNo").attr("readonly","readonly");
				$("#suggestionArea").attr("readonly","readonly");
				$("#areaName").attr("readonly","readonly");
				$("#areaNo").attr("readonly","readonly");
				$("#suggestionLargeArea").attr("readonly","readonly");
				$("#largeAreaName").attr("readonly","readonly");
				$("#largeAreaNo").attr("readonly","readonly");
				var headStatus='${headStatus}';
				if(headStatus=='1'){
					$("#suggestionHead").attr("readonly","readonly");
					$("#headName").attr("readonly","readonly");
					$("#headNo").attr("readonly","readonly");
				}
			});
		</script>
	</c:if>
</body>
</html>