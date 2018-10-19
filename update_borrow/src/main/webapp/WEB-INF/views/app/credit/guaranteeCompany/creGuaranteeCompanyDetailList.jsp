<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>担保公司信息</title>
</head>
<script type="text/javascript">
    var width = $(document).width() - 200;

    function addGuarantor111() {

        var flag = $("#flag").val();
        var applyId = $("#applyId").val();

        var url = "${ctx}/credit/creGuaranteeCompany/GuananteeList?companyFlag="+flag+"&applyId="+applyId;
        openJBox("guarantorInfo-form", url, "担保公司列表", width, 600, {
            applyNo : $("#applyNo").val()
        });
    }

    $(document).ready(function() {

    });


    //删除担保公司
    function deleteGuaranteeCompany(id){
        confirmx('确认要解除该担保公司信息吗？', function() {
               $.ajax({
                   url:"${ctx}/credit/creGuaranteeCompany/deleteCompany",
                   data:{
                       id :id
                   },
                   type : "post",
                   success:function (data) {
                       if(data.status == 1){
                            alertx(data.message);
                             $("#flag").val(1)
                           $.loadDiv("guarantorCompanyList", "${ctx}/credit/mortgagedperson/queryContract" , "post");
                       } else {
                           alertx(data.message);
                           var flag = $("#flag").val(0)
                           alertx(flag);
                       }
                   }

               });
        });
    }

    //详情
    function detailGuarantee(id) {
        var width = $(document).width() - 200;
        var url = "${ctx}/credit/creGuaranteeCompany/form?id=" + id + "&&readOnly=0";
        openJBox("guaranteeInfo-detai", url, "担保公司信息详情", width, 600, null);
    }


    function guarA() {
         $("#companyDiv").toggle(600);

    }
</script>
<body>
<div class="tableList">
    <sys:message content="${message}" />
    <div id="tableDataId" style="max-height: 400px; overflow: auto;">
        <div class="searchInfo">
            <h3 onclick="guarA()" class="searchTitle">第三方担保公司</h3>
            <div id="companyDiv" class="ribbon filter">
                <ul class="layout">
                  <li class="add">
                        <a id="add" href="javascript:void(0);" onclick="addGuarantor111();">
								<span>
                                    <b></b>
									担保公司选择
								</span>
                        </a>
                    </li>


                </ul>
                <table id="contentTable" class="table table-striped table-bordered table-condensed">
                    <thead>

                    <tr>
                        <th width="3%">
                            <input type="checkbox" id="checkAll" name="checkAll" onclick="allCheck('checkAll','companyId');" />
                        </th>
                        <th width="3%">序号</th>
                        <th width="10%">担保公司名称</th>
                        <th width="10%">统一社会信用代码</th>
                        <th width="10%">企业性质</th>
                        <th width="15%">担保余额（元）</th>
                        <c:if test="${taskDefKey == 'utask_htmq'}">
                        <th width="10%">担保状态</th>
                        </c:if>
                        <th width="10%">操作</th>
                        <!--一个订单目前只有一个担保公司做担保 只是标记位-->
                        <input type="hidden" id="flag" value="${flag}" />

                        <input type="hidden" id="applyId" value="${applyNo}">
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                       <c:if test="${not empty company}" >

                            <td>
                                <input type="checkbox" name="companyId" id="companyId" value="${company.id}" />
                            </td>
                            <td>1</td>
                            <td>${company.guaranteeCompanyName}</td>
                            <td>${company.unSocCreditNo}</td>
                            <td>${fns:getDictLabel(company.companyType, 'GURANTEE_NATURE', '')}</td>
                            <td>${company.guaranteeLimit}</td>
                            <c:if test="${taskDefKey == 'utask_htmq'}">
                            <td>${relation.isConfirm}</td>
                            </c:if>
                            <td>
                                <a href="javascript:void(0)" onclick="detailGuarantee('${company.id}');">详情</a>

                           <%--    <c:if test="${taskDefKey == 'utask_sqlr'}">
                                   <a href="javascript:void(0)" onclick="deleteGuaranteeCompany('${relation.id}');">删除</a>
                               </c:if>--%>

                            </td>
                       </c:if>

                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
