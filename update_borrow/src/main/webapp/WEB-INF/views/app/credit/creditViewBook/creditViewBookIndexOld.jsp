<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<style type="text/css" media="print">
.noprint {
	display: none !important;
}
</style>
<title>信审意见书</title> 
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		if("${actTaskParam.taskDefKey}" == "utask_fgsfksh"){
			if (!checkIsNull('${checkApprove.id}')) {
				parent.showGetInfoTab();
			}else{
				parent.hideGetInfoTab();
			}
		}
		$("#creditViewBookForm").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
		//外访费收取情况
		$.loadDiv("checkFeeDiv", "${ctx }/credit/checkFee/creditViewBook", {
			applyNo : "${actTaskParam.applyNo}"
		}, "post");
		//资产清单列表
		$.loadDiv("assetDiv", "${ctx }/credit/creditViewBook/creditAssets/list", {
			applyNo : "${actTaskParam.applyNo}"
		}, "post");
		
		adjustTextareaLength("creditAnalysis.loanPurposeDesc","preLoanPurposeDesc");
		adjustTextareaLength("creditAnalysis.categoryDesc","preCategoryDesc");
		adjustTextareaLength("creditAnalysis.companyDesc","preCompanyDesc");
		adjustTextareaLength("creditAnalysis.loanRepayDesc","preLoanRepayDesc");
		adjustTextareaLength("creditAnalysis.guaranteeDesc","preGuaranteeDesc");
		adjustTextareaLength("creditAnalysis.riskPoint","preRiskPoint");
		adjustTextareaLength("creditAnalysis.edgeComment","preEdgeComment");
		adjustTextareaLength("suggestionBranch","preSuggestionBranch");
		adjustTextareaLength("suggestionArea","preSuggestionArea");
		adjustTextareaLength("suggestionLargeArea","preSuggestionLargeArea");
		adjustTextareaLength("suggestionHead","preSuggestionHead");
	});

	function saveForm() {
		loading();
		top.$.jBox.tip.mess = null;
		var company = $("#creditViewBookForm").serializeJson();
		$.post("${ctx}/credit/creditViewBook/save", company, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {
					alertx(data.message, function() {
					});
				} else {
					alertx(data.message);
				}

			}
		});
	}

	//新增
	function add(url, title) {
		var width = $(top.document).width() - 500;
		width = Math.max(width, 1000);
		var height = $(top.document).height() - 150;
		/* height = Math.max(height,1000);  */
		openJBox('', url, title, width, height);
	}

	//修改
	function edit(urlSingle, title) {
		var width = $(top.document).width() - 300;
		width = Math.max(width, 1000);
		var $checkLine = $("input[name='assetsType']:checked");
		var $len = $checkLine.length;
		if ($len != 1) {
			alertx("请选择一条数据");
		} else {
			var url = urlSingle + "?id=" + $checkLine.val();
			openJBox('', url, title, width, $(top.document).height() - 200);
		}
	}
	//删除
	function del(url, divName, divUrl) {
		var $checkLine = $("input[name='assetsType']:checked");
		if (0 == $checkLine.length) {
			alertx("请选择需要删除的数据！");
		} else {
			confirmx("是否删除?", function() {
				delOper(url, divName, divUrl);
			});
		}
	}

	function delOper(url, divName, divUrl) {
		var $checkLine = $("input[name='assetsType']:checked");
		if (null != $checkLine && $checkLine.length > 0) {
			var ids = "";
			$checkLine.each(function(v) {
				ids += (this.value + ",");
			});
			$.post(url, {
				"ids" : ids
			}, function(data) {
				if ("success" == data) {
					alertx("删除成功！");
					$.loadDiv("assetDiv", "${ctx }/credit/creditViewBook/creditAssets", {
						applyNo : "${actTaskParam.applyNo}"
					}, "post");
				}
			});
		}
	}

	//查看详情
	function details(url, message) {
		openJBox('', url, message, 1000, 500);
	}
</script>
</head>
<body>
	<!-- 申请信息 -->
	<div id="applyInfo">
		<%@ include file="/WEB-INF/views/app/credit/creditViewBook/applyInfoForCredit/applyInfoForCreditForm.jsp"%>
	</div>
	<!-- 外访费收取情况 -->
	<div id="checkFeeDiv"></div>
	<!-- 综合意见 -->
	<div id="ComprehensiveOpinionDiv">
		<%@ include file="/WEB-INF/views/app/credit/creditViewBook/compositeOpinion/compositeOpinionForm.jsp"%>
	</div>
	<div class="searchInfo">
		<h3 class="searchTitle">其他信息</h3>
		<form:form id="creditViewBookForm" modelAttribute="creditViewBook" action="${ctx}/credit/creditViewBook/save" method="post" class="form-horizontal">
			<form:hidden path="id" />
			<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
			<sys:message content="${otherInfoMessage}" />
			<div class="searchCon">
				<table id="otherInfoTable" class="fromTable filter">
					<tr>
						<td class="ft_label">借款人夫妻资产占借款金额比例：</td>
						<td class="ft_content">
							<form:select path="creditOtherInfo.coupleAssetsOfLoan" id="coupleAssetsOfLoan" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('COUPLE_ASSETS_OF_LOAN')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">担保人资产占借款金额比例：</td>
						<td class="ft_content">
							<form:select path="creditOtherInfo.guaranteeAssetsOfLoan" id="guaranteeAssetsOfLoan" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('GUARANTEE_ASSETS_OF_LOAN')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">最近一年股权变更：</td>
						<td class="ft_content">
							<form:select path="creditOtherInfo.lastYearStockChange" id="lastYearStockChange" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('LAST_YEAR_STOCK_CHANGE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">缴税情况：</td>
						<td class="ft_content">
							<form:select path="creditOtherInfo.payTaxStatus" id="payTaxStatus" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('PAY_TAX_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">物业费缴纳情况：</td>
						<td class="ft_content">
							<form:select path="creditOtherInfo.managementFeeStatus" id="managementFeeStatus" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('MANAGEMENT_FEE_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">电费缴纳情况：</td>
						<td class="ft_content">
							<form:select path="creditOtherInfo.powerFeeStatus" id="powerFeeStatus" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('POWER_FEE_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">借款企业中借款人占股(%)：</td>
						<td class="ft_content">
							<form:input path="creditOtherInfo.mainManOfStock" id="mainManOfStock" htmlEscape="false" onblur="this.value=gdpMoney(this.value);" onkeyup="gdpMax(this)" maxlength="6" class="input-medium required" />
							<span class="help-inline">
								<font color="red">*</font>
							</span>
						</td>
						<td class="ft_label">申请人在用款企业的出资年限：</td>
						<td class="ft_content">
							<form:input path="creditOtherInfo.capitalContributionPeriod" onblur="this.value=capMoney(this.value);" onkeyup="gdpMax(this)" maxlength="4" id="capitalContributionPeriod" htmlEscape="false" class="input-medium number required" />
							<span class="help-inline">
								<font color="red">*</font>
							</span>
						</td>
						<td class="ft_label">借款到期日期：</td>
						<td class="ft_content">
							<input id="landEndDate" name="creditOtherInfo.landEndDate" type="text" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value='${creditViewBook.creditOtherInfo.landEndDate}' pattern='yyyy-MM'/>" onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date(),dateFmt:'yyyy-MM'});" />
							<span class="help-inline">
								<font color="red">*</font>
							</span>
						</td>
					</tr>
				</table>
			</div>
			<!-- 外访费收取情况 -->
			<div id="assetDiv"></div>
			<h3 class="searchTitle">分析信息</h3>
			<div class="searchCon">
				<table class="fromTable filter">
					<tr>
						<td class="ft_label" style="width: 10%;">借款用途说明：</td>
						<td class="ft_content" style="width: 90%;">
							<form:textarea path="creditAnalysis.loanPurposeDesc"  htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth  textarea-style required" onkeyup="adjustTextareaLength('creditAnalysis.loanPurposeDesc','preLoanPurposeDesc')"/>
							<font color="red">*</font>
							<pre class="textareaWidth pre-style"  id="preLoanPurposeDesc"></pre>
							<pre class="textareaWidth pre-style"  id="preCategoryDesc"></pre>
							<pre class="textareaWidth pre-style"  id="preCompanyDesc"></pre>
							<pre class="textareaWidth pre-style"  id="preLoanRepayDesc"></pre>
							<pre class="textareaWidth pre-style"  id="preGuaranteeDesc"></pre>
							<pre class="textareaWidth pre-style"  id="preRiskPoint"></pre>
							<pre class="textareaWidth pre-style"  id="preEdgeComment"></pre>
							<pre class="textareaWidth pre-style"  id="preSuggestionBranch"></pre>
							<pre class="textareaWidth pre-style"  id="preSuggestionArea"></pre>
							<pre class="textareaWidth pre-style"  id="preSuggestionLargeArea"></pre>
						    <pre class="textareaWidth pre-style"  id="preSuggestionHead"></pre>
						</td>
					</tr>
					<tr>
						<td class="ft_label" style="width: 10%;">行业状况分析：</td>
						<td class="ft_content" style="width: 90%;">
							<form:textarea path="creditAnalysis.categoryDesc"  htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysis.categoryDesc','preCategoryDesc')"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label" style="width: 10%;">借款企业情况分析：</td>
						<td class="ft_content" style="width: 90%;">
							<form:textarea path="creditAnalysis.companyDesc"  htmlEscape="false" rows="4" maxlength="5000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysis.companyDesc','preCompanyDesc')"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label" style="width: 10%;">还款来源分析：</td>
						<td class="ft_content" style="width: 90%;">
							<form:textarea path="creditAnalysis.loanRepayDesc"  htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysis.loanRepayDesc','preLoanRepayDesc')"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label" style="width: 10%;">担保情况分析：</td>
						<td class="ft_content" style="width: 90%;">
							<form:textarea path="creditAnalysis.guaranteeDesc"  htmlEscape="false" rows="4" maxlength="6000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysis.guaranteeDesc','preGuaranteeDesc')"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label" style="width: 10%;">风险点：</td>
						<td class="ft_content" style="width: 90%;">
							<form:textarea path="creditAnalysis.riskPoint"  htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysis.riskPoint','preRiskPoint')"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label" style="width: 10%;">优势：</td>
						<td class="ft_content" style="width: 90%;">
							<form:textarea path="creditAnalysis.edgeComment"  htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysis.edgeComment','preEdgeComment')"/>
							<font color="red">*</font>
							
						</td>
					</tr>
				</table>
			</div>
			<h3 class="searchTitle">审批信息</h3>
			<div class="searchCon">
				<table class="fromTable filter">
					<tr>
						<td class="ft_label" style="width: 10%;">分公司综合意见：</td>
						<td class="ft_content" style="width: 90%;">
							<form:textarea path="suggestionBranch"  htmlEscape="false" rows="4" maxlength="2000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('suggestionBranch','preSuggestionBranch')"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr id="suggestionAreaTr">
						<td class="ft_label" style="width: 10%;">区域综合意见：</td>
						<td class="ft_content" style="width: 90%;">
							<form:textarea path="suggestionArea" id="suggestionArea" htmlEscape="false" rows="4" maxlength="2000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('suggestionArea','preSuggestionArea')"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr id="suggestionLargeAreaTr">
						<td class="ft_label" style="width: 10%;">大区综合意见：</td>
						<td class="ft_content" style="width: 90%;">
							<form:textarea path="suggestionLargeArea" id="suggestionLargeArea"  htmlEscape="false" rows="4" maxlength="2000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('suggestionLargeArea','preSuggestionLargeArea')"/>
							<font color="red">*</font>
							
						</td>
					</tr>
					<tr id="suggestionHeadTr">
						<td class="ft_label" style="width: 10%;">总公司综合意见：</td>
						<td class="ft_content" style="width: 90%;">
							<form:textarea path="suggestionHead" id="suggestionHead"  htmlEscape="false" rows="4" maxlength="2000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('suggestionHead','preSuggestionHead')"/>
							<font color="red">*</font>
							
						</td>
					</tr>
				</table>
			</div>
			<div class="form-actions" style="text-align: right;">
				<input id="btnSubmit" class="btn btn-primary noprint" type="submit" value="保 存" />
				&nbsp;
				<input id="btnPrint" class="btn btn-primary noprint" type="button" value="打印本页面 " onclick="window.print()" />
			</div>
		</form:form>
	</div>
	<!-- 分公司风控审核:移除区域、大区、总公司综合意见，将分公司综合意见设置为可以编辑 -->
	<c:if test="${taskDefKeyFlag == 'utask_fgsfksh'}">
		<script type="text/javascript">
			$(document).ready(function() {
				$("#suggestionAreaTr").hide();
				$("#suggestionLargeAreaTr").hide();
				$("#suggestionHeadTr").hide();
			});
		</script>
	</c:if>
	<!-- 分公司经理审核:移除区域、大区、总公司综合意见 -->
	<c:if test="${taskDefKeyFlag == 'utask_fgsjlsh'}">
		<script type="text/javascript">
			$(document).ready(function() {
				$("#suggestionAreaTr").hide();
				$("#suggestionLargeAreaTr").hide();
				$("#suggestionHeadTr").hide();
				//文本框、大文本框、单选框、复选框只读处理
				disableBaseElement();
				//时间插件只读处理
				disableWdate();
				//下拉框只读处理
				disableSelect2();
				//新增、修改、删除、保存按钮只读处理
				hideButtons();
				//审批意见只读处理
				hideSuggDiv();
				//由于页面的特殊性，所以这里直接将所有的fongt节点删除
				$("font").hide();
			});
		</script>
	</c:if>
	<!-- 区域风控专员审核:移除大区、总公司综合意见，将区域综合意见设置为可以编辑 -->
	<c:if test="${taskDefKeyFlag == 'utask_qyfksh'}">
		<script type="text/javascript">
			$(document).ready(function() {
				$("#suggestionLargeAreaTr").hide();
				$("#suggestionHeadTr").hide();
				//文本框、大文本框、单选框、复选框只读处理
				disableBaseElement();
				//时间插件只读处理
				disableWdate();
				//下拉框只读处理
				disableSelect2();
				//新增、修改、删除、保存按钮只读处理
				hideButtons();
				//审批意见只读处理
				hideSuggDiv();
				//由于页面的特殊性，所以这里直接将所有的fongt节点删除
				$("font").hide();
				if ('${actTaskParam.status}' == 0) {//待办
					//放开区域综合意见填写权限
					$("#suggestionArea").removeAttr("readOnly");
					//显示保存按钮
					$("#btnSubmit").show();
				}
			});
		</script>
	</c:if>
	<!-- 区域风控经理审核:移除大区、总公司综合意见 -->
	<c:if test="${taskDefKeyFlag == 'utask_qyjlsh'}">
		<script type="text/javascript">
			$(document).ready(function() {
				$("#suggestionLargeAreaTr").hide();
				$("#suggestionHeadTr").hide();
				//文本框、大文本框、单选框、复选框只读处理
				disableBaseElement();
				//时间插件只读处理
				disableWdate();
				//下拉框只读处理
				disableSelect2();
				//新增、修改、删除、保存按钮只读处理
				hideButtons();
				//审批意见只读处理
				hideSuggDiv();
				//由于页面的特殊性，所以这里直接将所有的fongt节点删除
				$("font").hide();
			});
		</script>
	</c:if>
	<!-- 大区风控专员审核:移除总公司综合意见，将大区综合意见设置为可以编辑-->
	<c:if test="${taskDefKeyFlag == 'utask_dqfkzysh'}">
		<script type="text/javascript">
			$(document).ready(function() {
				$("#suggestionHeadTr").hide();
				//文本框、大文本框、单选框、复选框只读处理
				disableBaseElement();
				//时间插件只读处理
				disableWdate();
				//下拉框只读处理
				disableSelect2();
				//新增、修改、删除、保存按钮只读处理
				hideButtons();
				//审批意见只读处理
				hideSuggDiv();
				//由于页面的特殊性，所以这里直接将所有的fongt节点删除
				$("font").hide();
				if ('${actTaskParam.status}' == 0) {//待办
					//放开区域综合意见填写权限
					$("#suggestionLargeArea").removeAttr("readOnly");
					//显示保存按钮
					$("#btnSubmit").show();
				}
			});
		</script>
	</c:if>
	<!-- 大区风控经理审核:移除总公司综合意见-->
	<c:if test="${taskDefKeyFlag == 'utask_dqfkjlsh'}">
		<script type="text/javascript">
			$(document).ready(function() {
				$("#suggestionHeadTr").hide();
				//文本框、大文本框、单选框、复选框只读处理
				disableBaseElement();
				//时间插件只读处理
				disableWdate();
				//下拉框只读处理
				disableSelect2();
				//新增、修改、删除、保存按钮只读处理
				hideButtons();
				//审批意见只读处理
				hideSuggDiv();
				//由于页面的特殊性，所以这里直接将所有的fongt节点删除
				$("font").hide();
			});
		</script>
	</c:if>
	<!-- 分公司复议 -->
	<c:if test="${taskDefKeyFlag == 'utask_fgsfy'}">
		<script type="text/javascript">
			$(document).ready(function() {
				$("#suggestionHeadTr").hide();
				//文本框、大文本框、单选框、复选框只读处理
				disableBaseElement();
				//时间插件只读处理
				disableWdate();
				//下拉框只读处理
				disableSelect2();
				//新增、修改、删除、保存按钮只读处理
				hideButtons();
				//审批意见只读处理
				hideSuggDiv();
				//由于页面的特殊性，所以这里直接将所有的fongt节点删除
				$("font").hide();
			});
		</script>
	</c:if>
	<!-- 总公司风控专员审核:移除总公司综合意见，将大区综合意见设置为可以编辑-->
	<c:if test="${taskDefKeyFlag == 'utask_zgsfksh'}">
		<script type="text/javascript">
			$(document).ready(function() {
				//文本框、大文本框、单选框、复选框只读处理
				disableBaseElement();
				//时间插件只读处理
				disableWdate();
				//下拉框只读处理
				disableSelect2();
				//新增、修改、删除、保存按钮只读处理
				hideButtons();
				//审批意见只读处理
				hideSuggDiv();
				//由于页面的特殊性，所以这里直接将所有的fongt节点删除
				$("font").hide();
				if ('${actTaskParam.status}' == 0) {//待办
					//放开区域综合意见填写权限
					$("#suggestionHead").removeAttr("readOnly");
					//显示保存按钮
					$("#btnSubmit").show();
				}
			});
		</script>
	</c:if>
	<!-- 总公司经理审核:移除保存按钮-->
	<c:if test="${taskDefKeyFlag == 'utask_zgsjlsh'}">
		<script type="text/javascript">
			$(document).ready(function() {
				//文本框、大文本框、单选框、复选框只读处理
				disableBaseElement();
				//时间插件只读处理
				disableWdate();
				//下拉框只读处理
				disableSelect2();
				//新增、修改、删除、保存按钮只读处理
				hideButtons();
				//审批意见只读处理
				hideSuggDiv();
				//由于页面的特殊性，所以这里直接将所有的fongt节点删除
				$("font").hide();
			});
		</script>
	</c:if>
	<!-- 总公司总经理审核:移除保存按钮-->
	<c:if test="${taskDefKeyFlag == 'utask_zgszjlsh'}">
		<script type="text/javascript">
			$(document).ready(function() {
				//文本框、大文本框、单选框、复选框只读处理
				disableBaseElement();
				//时间插件只读处理
				disableWdate();
				//下拉框只读处理
				disableSelect2();
				//新增、修改、删除、保存按钮只读处理
				hideButtons();
				//审批意见只读处理
				hideSuggDiv();
				//由于页面的特殊性，所以这里直接将所有的fongt节点删除
				$("font").hide();
			});
		</script>
	</c:if>
</body>
</html>