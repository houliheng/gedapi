<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>共借人信息</title>
</head>
<script type="text/javascript">
	$(document).ready(function() {
		if ('${readOnly}' == '0') {
			setPageReadOnly();
		}
	});

	var width = $(document).width() - 200;
	var height = 1000;
	//新增共借人信息
	function addCoCustInfo() {
		var url = "${ctx}/credit/coCustInfo/form";
		openJBox("coCustInfo-form", url, "新增共借人信息", 800, 600, {
			applyNo : $("#applyNoForCoCust").val()
		});
	}

	//删除共借人信息
	function del() {
		var $checkLine = $("input[name='coCustInfoIds']:checked");
		var $len = $checkLine.length;
		if ($len < 1) {
			alertx("请选择要删除的共借人信息");
		} else {
			var checkedValue = getCheckedValue("coCustInfoIds");
			var url = "${ctx}/credit/coCustInfo/delete?ids=" + checkedValue + "&&applyNo=${applyNo}";
			confirmx('确认要删除勾选的共借人信息吗？', function() {
				saveJson(url, null, function(data) {
					if (data) {
						if (data.status == 1) {
							alertx(data.message, function() {
								$.loadDiv("coCustInfo", "${ctx }/credit/coCustInfo/list", {
									applyNo : $("#applyNoForCoCust").val()
								}, "post");
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
	function detail(custId) {
		var applyNo = $("#applyNoForCoCust").val();
		var url = "${ctx}/credit/coCustInfo/form?custId=" + custId + "&&readOnly=0&&applyNo=" + applyNo;
		openJBox("coCustInfo-detai", url, "共借人信息详情", 800, 600, null);
	}

	//编辑
	function edit() {
		var $checkLine = $("input[name='coCustInfoIds']:checked");
		var $len = $checkLine.length;
		var applyNo = $("#applyNoForCoCust").val();
		if ($len != 1) {//需要勾选一条信息进行修改
			alertx("请选择一条共借人信息");
		} else {
			var url = "${ctx}/credit/coCustInfo/form?custId=" + $checkLine.val() + "&&readOnly=1&&applyNo=" + applyNo;
			openJBox("coCustInfo-detai", url, "编辑共借人信息", 800, 600, null);
		}

	}
	
	function coCustClick(){
	$("#coCustId").toggle(600);
	}
</script>
<body>
	<div class="tableList">
		<form action="" id="emptyForm"></form>
		<sys:message content="${message}" />
		<div id="tableDataId" style="max-height: 400px; overflow: auto;">
			<div class="searchInfo">
				<h3 onclick="coCustClick()" class="searchTitle">共借人信息</h3>
				<div id="coCustId" class="ribbon filter">
					<ul class="layout">
						<li class="add">
							<a id="add" href="javascript:void(0);" onclick="addCoCustInfo();">
								<span>
									<b></b>
									新增
								</span>
							</a>
						</li>
						<li class="edit">
							<a id="edit" href="#" onclick="edit();">
								<span>
									<b></b>
									编辑
								</span>
							</a>
						</li>
						<li class="delete">
							<a id="delete" href="javascript:void(0);" onclick="del();">
								<span>
									<b></b>
									删除
								</span>
							</a>
						</li>
					</ul>
					<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
						<thead>
							<tr>
								<th width="3%">
									<input type="checkbox" id="checkAllCoCustInfo" name="checkAllCoCustInfo" onclick="allCheck('checkAllCoCustInfo','coCustInfoIds');" />
								</th>
								<th width="3%">序号</th>
								<th width="10%">姓名</th>
								<th width="10%">与借款人关系</th>
								<th width="10%">证件类型</th>
								<th width="15%">证件号</th>
								<th width="15%">手机号</th>
								<th width="10%">单位名称</th>
								<th width="10%">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${coCustRelations}" var="custRelation" varStatus="coCustRelations">
								<tr>
									<td>
										<input type="checkbox" name="coCustInfoIds" id="coCustInfoIds" value="${custRelation.custInfo.id }" />
									</td>
									<td>${coCustRelations.index+1 }</td>
									<td>${custRelation.custInfo.custName}</td>
									<td>${fns:getDictLabel(custRelation.relationForApply, 'CONTACT_RELATIONS', '')}</td>
									<td>${fns:getDictLabel(custRelation.custInfo.idType, 'CUSTOMER_P_ID_TYPE', '')}</td>
									<td>${custRelation.custInfo.idNum}</td>
									<td>${custRelation.custInfo.mobileNum}</td>
									<td>${coCustWorkInfoList[coCustRelations.index].companyName }</td>
									<td>
										<a href="javascript:void(0)" onclick="detail('${custRelation.custInfo.id}')">详情</a>
									</td>
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
