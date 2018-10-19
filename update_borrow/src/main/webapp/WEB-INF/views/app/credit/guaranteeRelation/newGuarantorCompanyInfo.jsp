<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>批量企业的担保企业</title>
</head>
<script type="text/javascript">
	$(document).ready(function() {
		if ('${readOnly}' == '0') {
			setPageReadOnly();
		}
	});

	var width = $(window).width() - 100;
	//新增担保企业信息
	function addGuarantorCompany() {
		var applyNo =  $("#applyNo").val()
		var url = "${ctx}/credit/creApplyCompanyRelation/form?guaranteeCompanyId="+'${companyId}'+"&&applyNo="+applyNo;
		openJBox("companyInfo-form", url, "新增担保企业信息", width, 600, null);
	}

	//删除担保企业信息
	function deleteGuarantorCompany() {
		var $checkLine = $("input[name='guarantorCompanyIds']:checked");
		var $len = $checkLine.length;
		if ($len < 1) {
			alertx("请选择要删除的担保企业信息");
		} else {
			var checkedValue = getCheckedValue("guarantorCompanyIds");
			var applyNo = $("#applyNo").val();
			var url = "${ctx}/credit/creApplyCompanyRelation/banchDelete?guaranteeCompanyId="+'${companyId}'+"&&ids=" + checkedValue +"&&applyNo="+applyNo;
			confirmx('确认要删除勾选的担保企业信息吗？', function() {
				saveJson(url, null, function(data) {
					if (data) {
						if (data.status == 1) {
							alertx(data.message, function() {
								$.loadDiv("guarantorCompanyInfo", "${ctx}/credit/guarantorCompanyInfo/guaranteeList?custId="+'${companyId}'+"&&applyNo="+applyNo, null, "post");
							});
						} else {
							alertx(data.message);
						}

					}
				});
			});
		}
	}

	//详情
	function detailGuarantorCompany(companyId) {
		var url = "${ctx}/credit/guarantorCompanyInfo/form?companyId=" + companyId+ "&&readOnly=0&&applyNo="+$("#applyNo").val();
		openJBox("companyInfo-detai", url, "担保企业信息详情", width, 600, null);
	}

	//编辑
	function editGuarantorCompany() {
		var $checkLine = $("input[name='guarantorCompanyIds']:checked");
		var $len = $checkLine.length;
		if ($len != 1) {//需要勾选一条信息进行修改
			alertx("请选择一条担保企业信息！");
		} else {
			var url = "${ctx}/credit/guarantorCompanyInfo/form?companyId="
					+ $checkLine.val() + "&&readOnly=1&&applyNo="+$("#applyNo").val();
			openJBox("companyInfo-detai", url, "编辑担保企业信息", width, 600, null);
		}
	}
	
	function guaranB(){
	$("#guaranBId").toggle(600);
	}



    //担保企业选择
    function  addcompanyList() {
        var piliId = "${companyId}";
        var applyNo = '${applyNo}';
        var url = "${ctx}/credit/creApplyCompanyRelation/ComPanyInfoFromList111?piliId="+piliId+ "&applyNo=${applyNo}";
        openJBox("adsasdfsdaf", url, "担保企业选择", width, 600, null);
    }



</script>
<body>
	<div class="tableList">
		<input type="hidden" name="newGuarantorCompanyInfo" id="newGuarantorCompanyInfo" value="${companyId}">
		<sys:message content="${message}" />
		<div id="tableDataId" style="max-height: 400px; overflow: auto;">
			<div class="searchInfo">
				<h3 onclick="guaranB()" class="searchTitle">担保企业信息列表</h3>
				<div id="guaranBId" class="ribbon filter">
					<ul class="layout">
						<li class="add">
							<a id="add" href="javascript:void(0);" onclick="addGuarantorCompany();">
								<span>
									<b></b>
									新增
								</span>
							</a>
						</li>
						<li class="edit">
							<a id="edit" href="javascript:void(0);" onclick="editGuarantorCompany();">
								<span>
									<b></b>
									编辑
								</span>
							</a>
						</li>
						<li class="delete">
							<a id="delete" href="javascript:void(0);" onclick="deleteGuarantorCompany();">
								<span>
									<b></b>
									删除
								</span>
							</a>
						</li>

						<li class="add">
							<a id="list" href="javascript:void(0);" onclick="addcompanyList();">
								<span>
									<b></b>
									 担保企业选择
								</span>
							</a>
						</li>

					</ul>
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="3%"><input type="checkbox" onclick="allCheck('guarantorCompanyCheckAll','guarantorCompanyIds')"
									name="guarantorCompanyCheckAll" id="guarantorCompanyCheckAll" /></th>
								<th width="3%">序号</th>
								<th width="10%">工商登记名称</th>
								<th width="10%">组织机构代码</th>
								<th width="10%">统一社会信用代码</th>
								<th width="10%">经营期限</th>
								<th width="10%">注册资金</th>
								<th width="10%">成立时间</th>
								<c:if test="${taskDefKey == 'utask_htmq'}">
								<th width="10%">担保状态</th>
								</c:if>
								<th width="10%">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${companyInfoList}" var="companyInfo"
								varStatus="companyInfoList">
								<tr>
									<td><input type="checkbox" id="guarantorCompanyIds"
										name="guarantorCompanyIds" value="${companyInfo.id }"/></td>
									<td>${companyInfoList.index+1 }</td>
									<td>${companyInfo.busiRegName}</td>
									<td>${companyInfo.organizationNo}</td>
									<td>${companyInfo.unSocCreditNo }</td>
									<td>${companyInfo.operatePeriod }</td>
									<td><fmt:formatNumber value="${companyInfo.registerCapital }" pattern="###,##0.00"></fmt:formatNumber></td>
									<td><fmt:formatDate value="${companyInfo.foundDate}" pattern="yyyy-MM-dd"/></td>
									<c:if test="${taskDefKey == 'utask_htmq'}">
									<td>${companyInfo.isConfirm}</td>
									</c:if>
									<td><a href="javascript:void(0)"
										onclick="detailGuarantorCompany('${companyInfo.id}')">详情</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
