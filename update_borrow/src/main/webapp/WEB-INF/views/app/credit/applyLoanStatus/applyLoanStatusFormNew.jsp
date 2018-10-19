		<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>财务放款管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	var checkStr = false;
 		if ('${applyLoanStatus.loanStatus}' != '20') {
 			$("#btnPush").attr("disabled", true);
 		}
		//页面初始化后，设置“加黑说明”不可编辑。
		$("#blacklistDiv").hide();
		//$("#name").focus();
		$("#processSuggestionInfoForm").validate({
		submitHandler : function() {
			var passFlag = $("input[name='passFlag']:checked").val();
			$.ajax({
			url : "${ctx}/credit/applyLoanStatus/validateLoanStatusNew",
			type : "post",
			data : {
			passFlag : passFlag,
			productTypeCode : '${contract.approProductTypeCode}',
			applyNo : '${actTaskParam.applyNo}'
			},
			dataType : "json",
			success : function(data) {
				if (data.status == "1") {
					save();
				} else {
					alertx(data.message);
				}
			},
			error : function(msg) {
				alertx("未能完成操作，请查看后台信息");
			}
			});
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkRequied(error, element);
		}
		});
	});
	
	function validate(show) {
		//当选择"拒绝并加入黑名单"之外的选项时，"加黑说明"设置为不可编辑。
		if (show == 'non_display') {
			$("#blacklistDiv").hide();
			$("#btnSubmit").show();
		} else {
			//当选择“拒绝并加入黑名单”后，将“加黑说明”设置为可编辑
			$("#blacklistDiv").show();
			$("#btnSubmit").hide();
		}
	}

	function validateSubmit(v) {
		$("#sta").attr("value", v);
	}


	function save() {
		top.$.jBox.tip.mess = null;
		var paramJson = $("#processSuggestionInfoForm").serializeJson();
		$.post("${ctx}/credit/applyLoanStatus/saveLoanSuggestion" , paramJson, function(data) {
			if (data) {
				if (data.status == 1) {
					alertx(data.suggestionMessage, function() {
						goToPage('${ctx}${actTaskParam.headUrl}', 'applyLoanStatusSkipId');
					});
				} else {
					alertx(data.suggestionMessage);
				}
				closeTip();
			}
		});
	}

	//冠E通发标接口
	function targetPush(loanId,custId,checkId) {
		loading("正在推送，请稍等");
		top.$.jBox.tip.mess = null;
		$.post("${ctx}/credit/applyLoanStatus/saveLoan?id="+loanId+"&custId="+custId+"&checkId="+checkId, function(data) {
			if (data.status == 1) {
			alertx(data.message);
			window.location.reload();
		//	window.location.href="${ctx}/credit/applyLoanStatus/form?readOnly=" + "true" + "&&applyNo=" + "GJXgqcczb161214150258AAF";
			} else {
				closeTip();
				alertx(data.message);
			}
		});
	}
		//面签详情
 		function detailUnion(approId,custId) {
	 //	var newUrl = "${ctx}/credit/workflow/contractAuditForm";
		var url = "${ctx}/credit/workflow/contractAuditForm?approId="+approId+"&custId="+custId;
		var urlsuffix = "&applyNo=${actTaskParam.applyNo}&headUrl=${actTaskParam.headUrl}&taskId=${actTaskParam.taskId}&taskDefKey=${actTaskParam.taskDefKey}&procDefId=${actTaskParam.procDefId}&status=${actTaskParam.status}&procInstId=${actTaskParam.procInstId}&oper=apply";
		url = url+urlsuffix;
		openJBox("detailUnionBoxId", url, "详情", $(window).width() - 50, 500);
	}   
/* 		function detailUnion(approId,custId){
			var url = "${ctx}/credit/contractUnion/contractAuditForm?approId="+approId+"&custId="+custId;
		var urlsuffix = "&applyNo=${actTaskParam.applyNo}&headUrl=${actTaskParam.headUrl}&taskId=${actTaskParam.taskId}&taskDefKey=${actTaskParam.taskDefKey}&procDefId=${actTaskParam.procDefId}&status=${actTaskParam.status}&procInstId=${actTaskParam.procInstId}&oper=apply";
		url = url+urlsuffix;
		var width = $(window).width() - 50;
		var height = $(window).height()-50;
		//alert(width+"and"+height);
		openJBox("signUnion-form", url, "面签信息详情", width, height);
	} */
	// 详情 批复信息

	function checkRequied(error, element) {
	$("#messageBox").text("输入有误，请先更正。");
	var str2 = element[0].outerHTML;
	if (element.is(":checkbox") || element.parent().is(".input-append")) {
		error.appendTo(element.parent().parent());
	} else if (element.is(":radio")) {
		error.appendTo(element.parent());
	} else {
		var str = element.prop("outerHTML").substring(0, 2);
		var div = $("[id='s2id_" + element.attr("id") + "']");

		var a = div.children("a.select2-choice");

		if (str == '<i') {
			element.get(0).style.backgroundColor = "pink";
			if (checkStr == true) {
				element.get(0).style.backgroundColor = "";
				checkStr = false;
			}
			element.live("input", function(event) {
				element.get(0).style.backgroundColor = "";
				// document.getElementById(IdCheck).style.backgroundColor = "";
			});
		}
		if (str == '<t') {
			element.get(0).style.backgroundColor = "pink";
			element.live("input", function(event) {
				element.get(0).style.backgroundColor = "";
			});
		}
		if (str == '<s') {
			a.css("background", "pink");
			a.css("backgroundColor", "pink");
			element.live("change", function(event) {
				event.stopPropagation();
				a.get(0).style.background = "";
			});
		}
	}
}
	//确认线下已缴费
	/* function targetLoan(id,contractNo,applyNo){
		confirmx("请确认担保费和保证金线下已缴纳",function(){
			loading("正在推送，请稍等");
			top.$.jBox.tip.mess = null;
			$.ajax({
				type : "post",
				data : {
					applyNo : applyNo,
					contractNo:contractNo
				},
				dataType : "json",
				url : "${ctx}/credit/applyLoanStatus/pushGedLoan",
				success : function(msg) {
					closeTip();
					if (msg.status == "0") {
						alertx(msg.message);
					} else {
						alertx(data.message);
					}
				},
				error : function(msg) {
					closeTip();
					alertx("查询异常，请查看后台信息");
				}
				});
			
		})
		
		
	} */
	
	function targetLoan(contractNo,applyNo){
		var url = "${ctx}/credit/applyLoanStatus/sureGuaranteeForm?applyNo="+applyNo+"&contractNo="+contractNo;
		openJBox("applyLoanStatus-form", url, "确认缴费", 800, 400,null);
	}
</script>
</head>
<body>
	<form id="emptyForm" action="#"></form>
	<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th>客户名称</th>
							<th style="width: 18%">合同号</th>
							<th>产品类型</th>
							<th>期数</th>
							<th>批复金额</th>
							<c:if test="${readOnly ne 'true'}">
							<th>放款状态</th>
							</c:if>
							<th>操作</th>
						</tr>
					</thead>
					<c:forEach items="${CheckApproveUnionLst}" var="process" varStatus="index">
					<tr>
						 <td class="title">${process.custName}</td> 
						 <td class="title">${process.contractNo}</td> 
						 <td class="title">${process.approProductName}</td> 
						 <td class="title">${process.approPeriodId}</td> 
						 <td class="title">${process.contractAmount}</td> 
						 <c:if test="${readOnly ne 'true'}">
		         	<c:choose>
		    			<c:when test="${process.loanStatus eq '20'}">
			    			<td class="title" title="放款状态" >
									<a href="javascript:void(0)" id="${process.loanId}"  onclick="targetPush('${process.loanId}','${process.custId}','${process.id}')" >${fns:getDictLabel(process.loanStatus, 'LOAN_STATUS', '')}</a>
									<a id="showUnion" href="javascript:void(0)"></a>
							</td>
		    			</c:when>
		    			<c:otherwise> <td class="title">${fns:getDictLabel(process.loanStatus, 'LOAN_STATUS', '')}</td> </c:otherwise>
		    		</c:choose>
         				 </c:if>
						<td class="title" title="详情">
							<a href="javascript:void(0)" id="${process.id}" onclick="detailUnion('${process.id}','${process.custId}');">详情</a>
							<a id="showUnion" href="javascript:void(0)"></a>
							<c:if test="${process.confirmFeeFlag eq '0'}">
							<span>已缴费</span>
							</c:if>
							<c:if test="${process.confirmFeeFlag eq '2'}">
							<span>未缴费</span>
							</c:if>
							<c:if test="${process.confirmFeeFlag eq '1'}">
							<a href="javascript:void(0)" id="${process.id}"  onclick="targetLoan('${process.contractNo}','${actTaskParam.applyNo}');">确认已缴费</a>
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>

	<!-- 放款结论 -->
	<div id="contractCheckSugg">
		<form:form id="processSuggestionInfoForm" modelAttribute="processSuggestionInfo" action="${ctx}/credit/applyLoanStatus/saveLoanSuggestion?applyLoanStatusId=${applyLoanStatus.id }" method="post" class="form-horizontal">
			<form:hidden path="id" />
			<a id="applyLoanStatusSkipId" target="_parent"></a>
			<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
			<input type="hidden" name="sta" id="sta" value="submit" />
			<div class="searchInfo">
				<h3 class="searchTitle">放款结论</h3>
				<div class="searchCon filter">
					<sys:message content="${suggestionMessage}" />
					<table class="fromTable">
						<tr>
							<td class="ft_label" style="width: 10%;">放款结论：</td>
							<td class="ft_content" style="width: 90%;">
								<input type="radio" name="passFlag" value="success" id="radio_success" class="required" onclick="validate('non_display')">
								<label for="radio_yes">放款成功结束流程</label>
								&nbsp;&nbsp;
								<input type="radio" name="passFlag" value="flow" id="radio_flow" class="required" onclick="validate('non_display')">
								<label for="radio_yes">流标结束流程</label>
								<font color="red">*</font>
								&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 10%;">放款意见：</td>
							<td class="ft_content" style="width: 60%;">
								<pre class="area-xxlarge pre-style required" id="suggestionDescPre"></pre>
								<form:textarea path="suggestionDesc" rows="5" htmlEscape="false" maxlength="1000" class="area-xxlarge textarea-style required " onkeyup="adjustTextareaLength('suggestionDesc','suggestionDescPre')" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td colspan="6" style="text-align: right;">
								<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>