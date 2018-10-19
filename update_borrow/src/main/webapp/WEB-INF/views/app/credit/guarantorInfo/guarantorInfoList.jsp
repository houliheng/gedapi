<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>担保人信息</title>
</head>
<script type="text/javascript">
    var width = $(document).width() - 200;
    //新增担保人信息
    function addGuarantor() {
        var url = "${ctx}/credit/guarantorInfo/form";
        openJBox("guarantorInfo-form", url, "新增担保人信息", width, 600, {
            applyNo : $("#applyNo").val()
        });
    }

    //删除担保人信息
    function delGuarantor() {
        var $checkLine = $("input[name='guarantorIds']:checked");
        var $len = $checkLine.length;
        if ($len < 1) {
            alertx("请选择要删除的担保人信息");
        } else {
            var checkedValue = getCheckedValue("guarantorIds");
            var applyNo = '${applyNo}';
            var url = "${ctx}/credit/guarantorInfo/newDelete?ids=" + checkedValue + "&&applyNo=" + '${applyNo}';
            confirmx('确认要删除勾选的担保人信息吗？', function() {
                saveJson(url, null, function(data) {
                    if (data) {
                        if (data.status == 1) {
                            alertx(data.message, function() {
                                $.loadDiv("guarantorInfoList", "${ctx }/credit/guarantorInfo/list", {
                                    applyNo : '${applyNo}'
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
    function detailGuarantor(custId) {
        var url = "${ctx}/credit/guarantorInfo/form?custId=" + custId + "&&readOnly=0&applyNo=${applyNo}";
        openJBox("adsafdsfdsaf", url, "担保人信息详情", width, 600, null);
    }

    //编辑
    function editGuarantor() {
        var $checkLine = $("input[name='guarantorIds']:checked");
        var $len = $checkLine.length;
        if ($len != 1) {//需要勾选一条信息进行修改
            alertx("请选择一条个人客户信息");
        } else {
            var url = "${ctx}/credit/guarantorInfo/form?custId=" + $checkLine.val() + "&&readOnly=1&applyNo=${applyNo}";
            openJBox("guarantorInfo-detai", url, "编辑担保人信息", width, 600, null);
        }

    }
    function guaranA() {
        $("#guaranAId").toggle(600);

    }
    function createGedAccountInfo(mobileNum,id){
        var url = "${ctx}/credit/createGedAccount/createAccountGR?mobileNum=" + mobileNum +"&&flag=1"+"&&id="+id +"&&applyNo="+'${applyNo}';
        confirmx('确认要创建冠易贷账号吗？', function() {
            saveJson(url, null, function(data) {
                if (data) {
                    if (data.status == 1) {
                        alertx(data.message, function() {
                            $.loadDiv("guarantorInfoList", "${ctx}/credit/guarantorInfo/list", {
                                applyNo : '${applyNo}',
                                taskDefKey : '${taskDefKey}'
                            }, "post");
                        });
                    } else {
                        alertx(data.message);
                    }
                }
            });
        });

    }
</script>
<body>
<div class="tableList">
	<sys:message content="${message}" />
	<div id="tableDataId" style="max-height: 400px; overflow: auto;">
		<div class="searchInfo">
			<h3 onclick="guaranA()" class="searchTitle">担保人信息列表</h3>
			<div id="guaranAId" class="ribbon filter">
				<ul class="layout">
					<li class="add">
						<a id="add" href="javascript:void(0);" onclick="addGuarantor();">
								<span>
									<b></b>
									新增
								</span>
						</a>
					</li>
					<li class="edit">
						<a id="edit" href="javascript:void(0);" onclick="editGuarantor();">
								<span>
									<b></b>
									编辑
								</span>
						</a>
					</li>
					<li class="delete">
						<a id="delete" href="javascript:void(0);" onclick="delGuarantor();">
								<span>
									<b></b>
									删除
								</span>
						</a>
					</li>
				</ul>
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th width="3%">
							<input type="checkbox" id="checkAllGuarantor" name="checkAllGuarantor" onclick="allCheck('checkAllGuarantor','guarantorIds');" />
						</th>
						<th width="3%">序号</th>
						<c:if test="${taskDefKey != 'under_dqglr'}">
						<th width="10%">姓名</th>
						<th width="10%">与借款人关系</th>
						<th width="10%">证件类型</th>
						<th width="15%">证件号</th>
						<th width="15%">手机号</th>
						<th width="10%">单位名称</th>
						<c:if test="${taskDefKey == 'utask_htyy'}">
							<th width="10%">冠易贷账号</th>
						</c:if>
						<c:if test="${taskDefKey == 'utask_htmq'}">
						<th width="10%">担保状态</th>
						</c:if>
						<th width="10%">操作</th>
						</c:if>
						<c:if test="${taskDefKey == 'under_dqglr'}">
						<th width="8%">姓名</th>
						<th width="8%">性别</th>
						<th width="12%">证件号</th>
						<th width="8%">婚姻状况</th>
						<th width="8%">单位名称</th>
						<th width="8%">与借款人关系</th>
						<th width="12%">房产地址</th>
						<th width="10%">房产估值</th>
						<th width="8%">手机号</th>
						<th width="8%">冠易贷账号</th>
						<th width="10%">操作</th>
						</c:if>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${guarantorRelationList}" var="guarantorRelation" varStatus="guarantorRelationList">
						<tr>
							<c:if test="${guarantorRelation.relationForApply != '1' }" >
								<td>
									<input type="checkbox" name="guarantorIds" id="guarantorIds" value="${guarantorRelation.custInfo.id }" />
								</td>
							</c:if>
							<c:if test="${guarantorRelation.relationForApply == '1' }">
								<td></td>
							</c:if>
							<td>${guarantorRelationList.index+1 }</td>
							<c:if test="${taskDefKey != 'under_dqglr'}">
							<td>${guarantorRelation.custInfo.custName}</td>
							<td>${fns:getDictLabel(guarantorRelation.relationForApply, 'CONTACT_RELATIONS', '')}</td>
							<td>${fns:getDictLabel(guarantorRelation.custInfo.idType, 'CUSTOMER_P_ID_TYPE', '')}</td>
							<td>${guarantorRelation.custInfo.idNum}</td>
							<td>${guarantorRelation.custInfo.mobileNum}</td>
							<td>${guarantorWorkInfoList[guarantorRelationList.index].companyName }</td>
							<c:if test="${taskDefKey == 'utask_htyy'}">
							<td>${guarantorRelation.custInfo.gedAccount}</td>
							</c:if>
							<c:if test="${taskDefKey == 'utask_htmq'}">
							<td>${guarantorRelation.guaranteeConfirm}</td>
							</c:if>
							<td>
								<a href="javascript:void(0)" onclick="detailGuarantor('${guarantorRelation.custInfo.id}');">详情</a>
								<c:if test="${showGedRegister == 1}">
									<c:if test="${empty guarantorRelation.custInfo.gedAccount}">
										<c:if test="${taskDefKey == 'utask_htyy'}">
										<a class="createGEDAccount" href="javascript:void(0)" onclick="createGedAccountInfo('${guarantorRelation.custInfo.mobileNum}','${guarantorRelation.custInfo.id}');">创建冠易贷账号</a>
										</c:if>
									</c:if>
								</c:if>
							</td>
							</c:if>
							<c:if test="${taskDefKey == 'under_dqglr'}">
							<td>${guarantorRelation.custInfo.custName}</td>
							<td>${fns:getDictLabel(guarantorRelation.custInfo.sexNo, 'sex', '')}</td>
							<td>${guarantorRelation.custInfo.idNum}</td>
							<td>${fns:getDictLabel(guarantorRelation.custInfo.wedStatus, 'WED_STATUS', '')}</td>
							<td>${guarantorWorkInfoList[guarantorRelationList.index].companyName }</td>
							<td>${fns:getDictLabel(guarantorRelation.relationForApply, 'CONTACT_RELATIONS', '')}</td>
							<td>${guarantorRelation.custInfo.houseAddress}</td>
							<td>${guarantorRelation.custInfo.estateValuation}</td>
							<td>${guarantorRelation.custInfo.mobileNum}</td>
							<td>${guarantorRelation.custInfo.gedAccount}</td>
							<td>
								<c:if test="${not empty guarantorRelation.custInfo.gedAccount}">
									<a href="javascript:void(0)"
									   onclick="detailGuarantor('${guarantorRelation.custInfo.id}');">详情</a>
								</c:if>
								<c:if test="${empty guarantorRelation.custInfo.gedAccount}">
									<a class="createGEDAccount" href="javascript:void(0)"
									   onclick="createGedAccountInfo('${guarantorRelation.custInfo.mobileNum}','${guarantorRelation.custInfo.id}');">创建冠易贷账号</a>
								</c:if>
							</td>
							</c:if>
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