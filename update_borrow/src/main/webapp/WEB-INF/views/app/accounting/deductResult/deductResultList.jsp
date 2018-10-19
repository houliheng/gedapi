<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>还款明细管理</title>
<script type="text/javascript">
	$(document).ready(function() {
		if ('${readonly}' == "true") {
			$("#saveDeduct").hide();
            alertx("此合同正在账务调整中，可直接点击'生成罚息'按钮进行手工调整。");
		}
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function saveDeductResult(contractNo) {
		var flag = "";
		top.$.jBox.warning("是否属于中间人代扣补录的流水？", '系统提示', function(v, h, f) {
			if (v != 'cancel') {
				if (v == 'yes') {
					var flag = "1";
				}
				var url = "${ctx}/accounting/deductResult/form";
				openJBox("saveDeductResultBox", url, "补录流水", 800, 350,{contractNo:contractNo,flag:flag});
			}
		})
	}
	function deleteForm() {
		var streamNoStr = $("input[name='num']:checked").val();
		if (streamNo != null) {
			$.ajax({
			url : "${ctx}/accounting/deductResult/delete",
			type : "post",
			data:{
				streamNo:streamNoStr
			},
			async : false,
			dataType : "JSON",
			success : function(data) {
				if (data.status == 1) {
					$.loadDiv("deductResultId", "${ctx}/accounting/deductResult",{contractNo:contractNo}, "post");
				} else {
					alertx(data.message);
				}
			}
			});
		} else {
			alertx("请选择一条记录");
		}
	}
	//账务调整
	function createFineAmount(contractNo) {
		$.ajax({
		url : "${ctx}/accounting/staRepayPlanStatus/validateIdLock",
		type:"post",
		data:{
			contractNo:contractNo
		},
		async : false,
		dataType : "JSON",
		success : function(data) {
			if (data.status == 1) {
				$("#deductResultDiv").hide();
				$.loadDiv("staRepayPlan", "${ctx}/accounting/staRepayPlanStatus",{contractNo:contractNo}, "post");
			} else {
				alertx(data.message);
			}
		}
		});
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
	<div id="deductResultId" class="wrapper">
		<div id="deductResultDiv">
			<sys:message content="${message}" />
			<div class="tableList">
				<a href="javaScript:void(0)" id="saveDeduct" class="btn btn-primary" onclick="saveDeductResult('${contractNo}')">补录流水</a>
				<a href="javaScript:void(0)" class="btn btn-primary" onclick="createFineAmount('${contractNo}')">生成罚息</a>
				<a href="javaScript:void(0)" class="btn btn-primary" onclick="deleteForm()">删除所选</a>
				<a href="javaScript:void(0)" class="btn btn-primary" onclick="closeJBox();">关闭</a>
				<h3 class="tableTitle">列表信息</h3>
				<div id="tableDataId">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>
									<input type="radio" name="topNum" disabled="disabled" />
								</th>
								<th>序号</th>
								<th>流水号</th>
								<th>还款金额</th>
								<th>入账时间</th>
								<th>操作人员代码</th>
								<th>备注</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list}" var="deductResult" varStatus="deductResultList">
								<tr>
									<td>
										<input type="radio" name="num" value="${deductResult.streamNo}" />
									</td>
									<td>${deductResultList.count }</td>
									<td id="streamNo" class="title" title="${deductResult.streamNo}">${deductResult.streamNo}</td>
									<td id="deductAmount" class="title" title="${deductResult.deductAmount}">${deductResult.deductAmount}</td>
									<td id="streamTime" class="title" title="${deductResult.streamTime}">
										<fmt:formatDate value="${deductResult.streamTime}" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td id="deductUserId" class="title" title="${deductResult.deductUserId}">${deductResult.deductUserId}</td>
									<td id="description" class="title" title="${deductResult.description}">${deductResult.description}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="staRepayPlan"></div>
</body>
</html>