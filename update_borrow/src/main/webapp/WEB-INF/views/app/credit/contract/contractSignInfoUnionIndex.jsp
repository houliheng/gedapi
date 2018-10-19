<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>联合授信面签信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#inputForm2").validate({
			submitHandler : function(form) {
				$.post("${ctx}/credit/contractUnion/submitValidate", {
					passFlag : $("input[name='passFlag']:checked").val(),
					applyNo : '${actTaskParam.applyNo}',
					taskDefKey : '${actTaskParam.taskDefKey}'
					}, function(data) {
						//alert(data.status);
						if (data) {
							if (data.status == 1) {//检验成功
								loading('正在提交，请稍等...');
								form.submit();
							} else {
								alertx(data.message);
							}
						}
					});
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				checkReq(error, element);
			}
		});
		$("#inputForm3").validate({
			submitHandler : function(form) {
				loading('正在提交，请稍等...');
				form.submit();
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				checkReq(error, element);
			}
		});

		$("#inputForm4").validate({
			submitHandler : function(form) {
				var passFlag = $("input[name='passFlag']:checked").val();
				if(passFlag == "yes" && '${confirmxFlag}'=="true"){
					confirmx("此进件涉及合同号变更，请再次确认合同号是否正确", function() {
						$.post("${ctx}/credit/contract/checkIsSign?applyNo="+'${actTaskParam.applyNo}',null,
                                function(data) {
                                    if (data) {
                                        if (data.status == '1') {
                                          loading('正在提交，请稍等...');
                                            form.submit();
                                        } else {
                                            alertx(data.message);
                                        }
                                    }
                          });
					});
				}else{
					if(passFlag == "yes"){
						$.post("${ctx}/credit/contract/checkIsSign?applyNo="+'${actTaskParam.applyNo}',null,
                                function(data) {
                                    if (data) {
                                        if (data.status == '1') {
                                            loading('正在提交，请稍等...');
                                            form.submit();
                                        } else {
                                            alertx(data.message);
                                        }
                                    }
                          });
					}else{
						loading('正在提交，请稍等...');
						form.submit();
					}
				}
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				checkReq(error, element);
			}
		});
	});
	
	//面签详情
	function sign(approId,custId){
		if('${taskDefKeyFlag}' == 'utask_htmq'){
			var url = "${ctx}/credit/contractUnion/contractSignForm?approId="+approId+"&custId="+custId;
		}else{
			var url = "${ctx}/credit/contractUnion/contractAuditForm?approId="+approId+"&custId="+custId;
		}
		var urlsuffix = "&applyNo=${actTaskParam.applyNo}&headUrl=${actTaskParam.headUrl}&taskId=${actTaskParam.taskId}&taskDefKey=${actTaskParam.taskDefKey}&procDefId=${actTaskParam.procDefId}&status=${actTaskParam.status}&procInstId=${actTaskParam.procInstId}&oper=apply";
		url = url+urlsuffix;
		var width = $(window).width() - 50;
		var height = $(window).height()-50;
		//alert(width+"and"+height);
		openJBox("signUnion-form", url, "面签信息详情", width, height);
	}
	function lhsxClick(){
		$("#tableDataId").toggle(600);
	}
	function mqjlClick() {
		$("#mqjlId").toggle(600);
	}
	
	//打印
	function printSelect() {
		var width = $(document).width() - 50;
		var height = $(document).height() - 50;
		var contractNo = $("#contractNo").val();
		var url = "${ctx}/credit/contractTemplate/printSelect?contractNo=" + "${originContractNo}" + "&applyNo=" + "${applyNo}";
		openJBox('', url, "打印", width, 800);
	}
</script>
<!-- 合同面签阶段 -->
<c:if test="${taskDefKeyFlag == 'utask_htmq'}">
	<script type="text/javascript">
		$(document).ready(function() {
			$("#legalCheckSuggestionDiv").hide();
			$("#contractCheckSuggestionDiv").hide();
		});
	</script>
</c:if>
<!-- 法务审核阶段 -->
<c:if test="${taskDefKeyFlag == 'utask_fwsh'}">
	<script type="text/javascript">
		$(document).ready(function() {
			$("#contractSignSuggestionDiv").hide();
			$("#contractCheckSuggestionDiv").hide();
			setDivReadOnly("readOnlyDiv");

		});
	</script>
</c:if>
<!-- 合同审核阶段 -->
<c:if test="${taskDefKeyFlag == 'utask_htsh'}">
	<script type="text/javascript">
		$(document).ready(function() {
			$("#contractSignSuggestionDiv").hide();
			$("#legalCheckSuggestionDiv").hide();
			setDivReadOnly("readOnlyDiv");
		});
	</script>
</c:if>
</head>
<body>
	<a id="contractSignUnionInfoSkipId" target="_parent"></a>
	<c:if test="${returnFlag eq 'true' && readOnly eq 'false'}">
		<div id='messageBox' class='alert alert-error'>
			<font>请注意：该任务为被打回任务!</font>
		</div>
	</c:if>
	<!-- 批复信息 -->
	<div class="tableList">
		<h3 onclick="lhsxClick()" class="tableTitle">联合授信表</h3>
		<div  id="tableDataId" >
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<!-- <th>序号</th> -->
						<th>客户姓名</th>
						<th>产品类型</th>
						<th>期数</th>
						<th>批复金额</th>
						<th>合同号</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${checkApproveUnionList}" var="checkApproveUnion" varStatus="unionStatus">
						<tr>
							<%-- <td id="num" class="title" >${unionStatus.count}</td> --%>
							<td id="num" class="title" >${checkApproveUnion.custName}</td>
							<td id="num" class="title" >${checkApproveUnion.approProductTypeName}</td>
							<td id="num" class="title" >${checkApproveUnion.approPeriodValue}</td>
							<td id="num" class="title" ><fmt:formatNumber value="${checkApproveUnion.contractAmount}" pattern="###,##0.00"/></td>
							<td id="num" class="title" >${checkApproveUnion.contractNo}</td>
				
					<c:if test="${actTaskParam.taskDefKey eq 'utask_htsh'}">
					<td><a href="javascript:void(0);" onclick="sign('${checkApproveUnion.id}','${checkApproveUnion.custId}');">详情</a></td>
					</c:if>
					<c:if test="${actTaskParam.taskDefKey eq 'utask_fwsh'}">
					<td><a href="javascript:void(0);" onclick="sign('${checkApproveUnion.id}','${checkApproveUnion.custId}');">详情</a></td>
					</c:if>
					<c:if test="${actTaskParam.taskDefKey eq 'utask_htmq'}">
					<td><a href="javascript:void(0);" onclick="sign('${checkApproveUnion.id}','${checkApproveUnion.custId}');">面签</a></td>
					</c:if>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="6" style="text-align: right;">
							<input class="btn btn-primary" value="打印" type="button" onclick="printSelect()"/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<div id="isHideSuggestionDiv">
		<!-- 面签结论 -->
		<div id="contractSignSuggestionDiv">
			<form:form id="inputForm2" modelAttribute="contractSignSuggestion" action="${ctx}/credit/contractUnion/saveSuggestion" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<sys:message content="${message}" />
				<div class="searchInfo">
					<h3 onclick="mqjlClick()" class="searchTitle">面签结论</h3>
					<input type="hidden" id="contractAmount" name="contractAmount" value="${contractAmount}" />
					<input type="hidden" id="applyNo" name="applyNo" value="${actTaskParam.applyNo}" />
					<input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}" />
					<input type="hidden" id="taskDefKey" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
					<input type="hidden" id="procDefId" name="procDefId" value="${actTaskParam.procDefId}" />
					<input type="hidden" id="status" name="status" value="${actTaskParam.status}" />
					<input type="hidden" id="procInstId" name="procInstId" value="${actTaskParam.procInstId}" />
					<input type="hidden" id="headUrl" name="headUrl" value="${actTaskParam.headUrl}" />
					<div id="mqjlId" class="infoListCon">
						<div class="searchCon">
							<table class="fromTable filter">
								<tr>
									<td class="ft_label" style="width: 10%;">面签结论：</td>
									<td class="ft_content" style="width: 90%;">
										<input type="radio" name="passFlag" value="yes" id="sign_yes" class="required">
										<label for="radio_yes">通过</label>
										&nbsp;&nbsp;
										<input type="radio" name="passFlag" value="no" id="sign_no" class="required">
										<label for="radio_no">打回</label>
									</td>
								</tr>
								<tr>
									<td class="ft_label" style="width: 10%;">面签意见：</td>
									<td class="ft_content" style="width: 90%;">
										<pre class="textarea-width required pre-style" id="signSuggestionPre"></pre>
										<form:textarea path="suggestionDesc" id="signSuggestion" class="textarea-width textarea-style required" rows="5" htmlEscape="false" maxlength="1000" onkeyup="adjustTextareaLength('signSuggestion','signSuggestionPre')" />
									</td>
								</tr>
								<tr>
									<td colspan="6" style="text-align: right;">
										<input id="btnSubmitSign" class="btn btn-primary" type="submit" value="提 交"  />
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</form:form>
		</div>
		<!-- 法务审核 -->
		<div id="legalCheckSuggestionDiv">
			<form:form id="inputForm3" modelAttribute="legalCheckSuggestion" action="${ctx}/credit/contractUnion/saveSuggestion" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<sys:message content="${message}" />
				<div class="searchInfo">
					<h3 class="searchTitle">法务审核结论</h3>
					<input type="hidden" id="contractAmount" name="contractAmount" value="${contractAmount}" />
					<input type="hidden" id="applyNo" name="applyNo" value="${actTaskParam.applyNo}" />
					<input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}" />
					<input type="hidden" id="taskDefKey" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
					<input type="hidden" id="procDefId" name="procDefId" value="${actTaskParam.procDefId}" />
					<input type="hidden" id="status" name="status" value="${actTaskParam.status}" />
					<input type="hidden" id="procInstId" name="procInstId" value="${actTaskParam.procInstId}" />
					<input type="hidden" id="headUrl" name="headUrl" value="${actTaskParam.headUrl}" />
					<div class="searchCon">
						<table class="fromTable filter">
							<tr>
								<td class="ft_label" style="width: 10%;">审核结论：</td>
								<td class="ft_content" style="width: 90%;">
									<input type="radio" name="passFlag" value="yes" id="legal_yes" class="required">
									<label for="radio_yes">通过</label>
									&nbsp;&nbsp;
									<input type="radio" name="passFlag" value="no" id="legal_no" class="required">
									<label for="radio_no">打回</label>
								</td>
							</tr>
							<tr>
								<td class="ft_label" style="width: 10%;">审核意见：</td>
								<td class="ft_content" style="width: 90%;">
									<pre class="textarea-width required pre-style" id="suggestionDescPre"></pre>
									<form:textarea path="suggestionDesc" class="textarea-width textarea-style required" onkeyup="adjustTextareaLength('suggestionDesc','suggestionDescPre')" rows="5" htmlEscape="false" maxlength="1000" />
								</td>
							</tr>
							<tr>
								<td colspan="6" style="text-align: right;">
									<input id="btnSubmitLegal" class="btn btn-primary" type="submit" value="提 交" />
								</td>
							</tr>
						</table>
					</div>
				</div>
			</form:form>
		</div>
		<!-- 合同审核 -->
		<div id="contractCheckSuggestionDiv">
			<form:form id="inputForm4" modelAttribute="contractCheckSuggestion" action="${ctx}/credit/contractUnion/saveSuggestion" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<sys:message content="${message}" />
				<div class="searchInfo">
					<h3 class="searchTitle">合同审核结论</h3>
					<input type="hidden" id="contractAmount" name="contractAmount" value="${contractAmount}" />
					<input type="hidden" id="applyNo" name="applyNo" value="${actTaskParam.applyNo}" />
					<input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}" />
					<input type="hidden" id="taskDefKey" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
					<input type="hidden" id="procDefId" name="procDefId" value="${actTaskParam.procDefId}" />
					<input type="hidden" id="status" name="status" value="${actTaskParam.status}" />
					<input type="hidden" id="procInstId" name="procInstId" value="${actTaskParam.procInstId}" />
					<input type="hidden" id="headUrl" name="headUrl" value="${actTaskParam.headUrl}" />
					<input type="hidden" name="approProductTypeNameCode" value="${checkApprove.approProductTypeCode}" />
					<div class="infoListCon">
					<div class="searchCon">
						<table class="fromTable filter">
							<tr>
								<td class="ft_label" style="width: 10%;">审核结论：</td>
								<td class="ft_content" style="width: 90%;">
									<input type="radio" name="passFlag" value="yes" id="radio_yes" class="required">
									<label for="radio_yes">通过</label>
									&nbsp;&nbsp;
									<input type="radio" name="passFlag" value="no" id="radio_no" class="required">
									<label for="radio_no">打回</label>
								</td>
							</tr>
							<tr>
								<td class="ft_label" style="width: 10%;">审核意见：</td>
								<td class="ft_content" style="width: 90%;">
									<pre class="textarea-width required pre-style" id="suggestionDesc1Pre"></pre>
									<form:textarea path="suggestionDesc" id="suggestionDesc1" rows="5" htmlEscape="false" onkeyup="adjustTextareaLength('suggestionDesc1','suggestionDesc1Pre')" maxlength="1000" class="textarea-width textarea-style required" />
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
				</div>
			</form:form>
		</div>
	</div>
	<c:if test="${not empty checkFlag}">
		<script type="text/javascript">
			alertx('操作成功', function() {
				goToPage('${ctx}${actTaskParam.headUrl}', 'contractSignUnionInfoSkipId');
			});
		</script>
	</c:if>
	<div style="height:200px; overflow: auto;">
	</div>
</body>
</html>