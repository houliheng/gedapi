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

        var piliId = $("#piliId").val();
        var url = "${ctx}/credit/creApplyCompanyRelation/CustInfoForm?piliId="+piliId;
        openJBox("guarantorInfo-fo", url, "新增担保人信息", width, 600, {
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
            var piliId = $("#piliId").val();
            var url = "${ctx}/credit/creApplyCompanyRelation/deleteCustInfo?ids=" + checkedValue + "&&applyNo=" + '${applyNo}'+"&&companyId="+piliId ;

            confirmx('确认要删除勾选的担保人信息吗？', function() {
                saveJson(url, null, function(data) {

                        if (data.status == 1) {
                            alertx(data.message, function() {
                                $.loadDiv("guarantorInfoList", "${ctx}/credit/creApplyCompanyRelation/selectCustInfo", {
                                    custId : '${custId}',
                                    applyNo : '${applyNo}'
                                }, "post");

                            });

                        } else {
                            alertx(data.message);
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
  
    
    //担保人选择
    function  addList() {
        var piliId = "${custId}";

        var url = "${ctx}/credit/creApplyCompanyRelation/CustInfoList111?piliId="+piliId+ "&applyNo=${applyNo}";
            openJBox("adsafdsgh", url, "担保人选择", width, 600, null);
    }


    function guaranbbd() {
        $("#guaAId").toggle(600);
    }
</script>
<body>
<div class="tableList">
    <sys:message content="${message}" />
    <div id="tableDataId" style="max-height: 400px; overflow: auto;">
        <div class="searchInfo">
            <h3 onclick="guaranbbd()" class="searchTitle">担保人信息列表</h3>
            <div id="guaAId" class="ribbon filter">
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

                    <li class="add">
                    <a id="list" href="javascript:void(0);" onclick="addList();">
								<span>
									<b></b>
									 担保人选择
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
                        <th width="10%">姓名</th>
                        <th width="10%">证件类型</th>
                        <th width="15%">证件号</th>
                        <th width="15%">手机号</th>
                        <c:if test="${taskDefKey == 'utask_htmq'}">
                        <th width="10%">担保状态</th>
                        </c:if>
                        <th width="10%">操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <input type="hidden" id="piliId" value="${custId}" />
                    <input type="hidden" id="applyNo" value="${applyNo}" />

                    <c:forEach items="${guarantorRelationList}" var="guarantorRelation" varStatus="guarantorRelationList">
                        <tr>
                            <td>
                                <input type="checkbox" name="guarantorIds" id="guarantorIds" value="${guarantorRelation.id}" />
                            </td>
                            <td>${guarantorRelationList.index+1 }</td>
                            <td>${guarantorRelation.custName}</td>
                            <td>${fns:getDictLabel(guarantorRelation.idType, 'CUSTOMER_P_ID_TYPE', '')}</td>
                            <td>${guarantorRelation.idNum}</td>
                            <td>${guarantorRelation.mobileNum}</td>
                            <c:if test="${taskDefKey == 'utask_htmq'}">
                            <td>${guarantorRelation.isConfirm}</td>
                            </c:if>
                            <td>
                                <a href="javascript:void(0)" onclick="detailGuarantor('${guarantorRelation.id}');">详情</a>
                            </td>
                        </tr>
                    </c:forEach>


                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</div>
</html>