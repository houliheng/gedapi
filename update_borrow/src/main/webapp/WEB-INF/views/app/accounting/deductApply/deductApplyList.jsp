<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>还款划扣管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	//重置按钮
	function del() {
		$("#companyId").val('');
		$("#companyName").val('');
		$("#streamStartTime").val('');
		$("#streamEndTime").val('');
		$(".select2-chosen").html("请选择");
		$(".class1").attr("selected", 'selected');
		$("input.input-medium").val("");
	}

	function adjustAccount() {
		var $checkLine = $("input[name='type']:checked");
		if (null != $checkLine && $checkLine.length == 1) {
			var deductResult = $("input[name='type']:checked").attr("deductResult");
			if (deductResult == '1') {
				var strikeFlag = $("input[name='type']:checked").attr("strikeFlag");
				if (checkIsNull(strikeFlag)) {
					var contractNo = $("input[name='type']:checked").val();
					var streamNo = $("input[name='type']:checked").attr("streamNo");
					var deductApplyNo = $("input[name='type']:checked").attr("deductApplyNo");
					var streamTimeStr = $("input[name='type']:checked").attr("streamTimeStr");
					$.ajax({
					url : "${ctx}/accounting/deductApply/validateAdjustAccountLock",
					type:"post",
					data:{
						contractNo:contractNo
					},
					async : false,
					dataType : "JSON",
					success : function(data) {
						if (data.status == 1) {
							var width = $(window).width() - 100;
							width = Math.max(width, 1000);
							var height = $(window).height() - 100;
							openJBox("zxc", "${ctx}/accounting/deductApply/adjustAccount", "冲正流水", width, height, {
							contractNo : contractNo,
							streamNo : streamNo,
							deductApplyNo : deductApplyNo,
							streamTimeStr : streamTimeStr
							});
						} else {
							alertx(data.message);
						}
					}
					});
				} else {
					alertx("请选择未冲正或未被冲正的数据");
				}
			} else {
				alertx("请选择划扣成功的数据");
			}
		} else {
			alertx("请选择一条数据");
		}
	}
	function saveDeductResultBatch(contractNo) {
		var flag = "";
		top.$.jBox.warning("是否属于中间人代扣补录的流水？", '系统提示', function(v, h, f) {
			if (v != 'cancel') {
				if (v == 'yes') {
					var flag = "1";
				}
				var url = "${ctx}/accounting/deductResult/formBatch";
				openJBox("saveDeductResultBox", url, "批量补录流水", 800, 350,{contractNo:contractNo,flag:flag});
			}
		})
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="deductApplyVO" action="${ctx}/accounting/deductApply/list?queryFlag=button" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="filter">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<form:input path="custName" htmlEscape="false" maxlength="20" class="input-medium" />
								</td>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content">
									<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-medium" />
								</td>
								<td class="ft_label">划扣人：</td>
								<td class="ft_content">
									<form:input path="deductCustId" htmlEscape="false" maxlength="20" class="input-medium" />
								</td>
							</tr>
							<tr>
								<td class="ft_label">所属机构：</td>
								<td class="ft_content">
									<sys:usertreeselect id="company" name="company.id" value="${deductApplyVO.company.id}" labelName="company.name" labelValue="${deductApplyVO.company.name}" title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true" />
								</td>
								<td class="ft_label">划扣状态：</td>
								<td class="ft_content">
									<form:select path="deductResult" class="input-medium">
										<form:option value="" class="class1" label="请选择" />
										<form:options items="${fns:getDictList('DEDUCT_RESULT')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td class="ft_label">转账结果：</td>
								<td class="ft_content">
									<form:select path="transferDeductResult" class="input-medium">
										<form:option value="" class="class1" label="请选择" />
										<form:options items="${fns:getDictList('TRANSFER_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								</tr>
								<tr>
								<td class="ft_label">划扣结果日期：</td>
								<td class="ft_content">
									<nobr>
										<input id="streamStartTime" name="streamStartTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate" value="<fmt:formatDate value="${deductApplyVO.streamStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
										至
										<input id="streamEndTime" name="streamEndTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate" value="<fmt:formatDate value="${deductApplyVO.streamEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
									</nobr>
								</td>
							</tr>
							<tr>
								<td class="ft_content" colspan="6" style="text-align:right">
									<shiro:hasPermission name="accounting:deductApply:view">
										<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp;
									</shiro:hasPermission>
									<input id="btnReset" class="btn btn-primary" type="button" onclick="del()" value="重置" />&nbsp;
									<input type="button"  class="btn btn-primary" onclick="saveDeductResultBatch('${contractNo}')" value="批量补录流水" />
								</td>
							</tr>
						</table>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<shiro:hasPermission name="adjustAccount"  >
				<div class="ribbon">
					<ul class="layout">
						<li class="">
							<a id="add" href="javascript:void(0);" onclick="adjustAccount();">
								<span>冲正流水</span>
							</a>
						</li>
					</ul>
				</div>
			</shiro:hasPermission>
			<div id="tableDataId" style="width:100%; overflow-x:auto;overflow-y:hidden">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="4%">
								<input type="checkbox">
							</th>
							<th width="6%">客户名称</th>
							<th width="6%">合同编号</th>
							<th width="6%">大区</th>
							<th width="6%">区域</th>
							<th width="6%">分公司</th>
							<th width="6%">划扣类型</th>
							<th width="6%">流水号</th>
							<th width="6%">申请划扣金额</th>
							<th width="6%">成功划扣金额</th>
							<th width="6%">划扣状态</th>
							<th width="6%">原因</th>
							<th width="6%">转账结果</th>
							<th width="6%">划扣结果日期</th>
							<th width="6%">划扣人</th>
							<th width="6%">标记</th>
							<th width="6%">备注</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="deductApplyVO">
							<tr>
								<td>
									<input name="type" type="checkbox" strikeFlag="${deductApplyVO.strikeFlag}" deductApplyNo="${deductApplyVO.deductApplyNo}" deductResult="${deductApplyVO.deductResult}" streamTimeStr="${deductApplyVO.streamTimeStr}" streamNo="${deductApplyVO.streamNo}" value="${deductApplyVO.contractNo}" onclick="selectSingle('type')">
								</td>
								<td class="title" title="${deductApplyVO.custName}">${deductApplyVO.custName}</td>
								<td class="title" title="${deductApplyVO.contractNo}">${deductApplyVO.contractNo}</td>
								<td class="title" title="${deductApplyVO.orgLevel2.name}">${deductApplyVO.orgLevel2.name}</td>
								<td class="title" title="${deductApplyVO.orgLevel3.name}">${deductApplyVO.orgLevel3.name}</td>
								<td class="title" title="${deductApplyVO.orgLevel4.name}">${deductApplyVO.orgLevel4.name}</td>
								<td class="title" title="${fns:getDictLabel(deductApplyVO.deductType, 'DEDUCT_TYPE', '')}">${fns:getDictLabel(deductApplyVO.deductType, 'DEDUCT_TYPE', '')}</td>
								<td class="title" title="${deductApplyVO.streamNo}">${deductApplyVO.streamNo}</td>
								<td class="title" title="${deductApplyVO.applyDeductAmount}">${deductApplyVO.applyDeductAmount}</td>
								<td class="title" title="${deductApplyVO.deductAmountResult}">${deductApplyVO.deductAmountResult}</td>
								<c:choose>
									<c:when test="${fns:getDictLabel(deductApplyVO.deductResult, 'DEDUCT_RESULT', '') eq '成功'}">
										<td class="title" title="${fns:getDictLabel(deductApplyVO.deductResult, 'DEDUCT_RESULT', '')}">${fns:getDictLabel(deductApplyVO.deductResult, 'DEDUCT_RESULT', '')}</td>
									</c:when>
									<c:otherwise>
										<td>
											<a href="${ctx}/accounting/staContractStatus/form?backUrl=${ctx}/accounting/deductApply/list&contractNo=${deductApplyVO.contractNo}">${fns:getDictLabel(deductApplyVO.deductResult, 'DEDUCT_RESULT', '')}</a>
										</td>
									</c:otherwise>
								</c:choose>
								<td class="title" title="${deductApplyVO.deductMsg }">${deductApplyVO.deductMsg }</td>
								<td class="title" title="${fns:getDictLabel(deductApplyVO.transferDeductResult, 'TRANSFER_TYPE', '')}">${fns:getDictLabel(deductApplyVO.transferDeductResult, 'TRANSFER_TYPE', '')}</td>
								<td class="title" title="<fmt:formatDate value='${deductApplyVO.streamTime}' pattern='yyyy-MM-dd' />">
									<fmt:formatDate value="${deductApplyVO.streamTime}" pattern="yyyy-MM-dd" />
								</td>
								<td id="deductCustId" class="title" title="${deductApplyVO.deductCustName}">${deductApplyVO.deductCustName}</td>
								<td id="strikeFlag" class="title" title="${fns:getDictLabel(deductApplyVO.strikeFlag, 'STRIKE_FLG', '')}">${fns:getDictLabel(deductApplyVO.strikeFlag, 'STRIKE_FLG', '')}</td>
								<td id="description" class="title" title="${fns:getDictLabel(deductApplyVO.description, 'CONTRACT_STATE', '')}">${fns:getDictLabel(deductApplyVO.description, 'CONTRACT_STATE', '')}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>