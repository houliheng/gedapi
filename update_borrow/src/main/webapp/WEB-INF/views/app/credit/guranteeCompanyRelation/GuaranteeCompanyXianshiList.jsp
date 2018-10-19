<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>担保公司信息列表</title>
    <meta name="decorator" content="default" />
    <%@ include file="/WEB-INF/views/include/cityselectJS.jsp"%>
    <script type="text/javascript">
        $(document).ready(function() {


        });

        // 添加要跟订单关联的担保公司信息
        function  saveCompany(){
            var companyId = $('input:radio[name="companyRadio"]:checked').val();
            var applyNo  = $("#applyNo").val();
            var custId = $("#custId").val();
            var roleType = "3";
            if(!companyId) {
                alertx('请选择要做担保的担保公司')
                return false;
            }  else {

                    //开始创建一个管理信息
                    // companyId 担保公司id
                    $.ajax({
                        url:"${ctx}/credit/creApplyCompanyRelation/saveApplyCompanyRelation",
                        data:{
                            companyId : companyId,
                            applyNo : applyNo,
                            custId:custId,
                            roleType:roleType
                        },
                        type:"post",
                        success : function (data) {
                            if(data.status == 1){
                                alertx(data.message);
                                // 刷新信息
                                parent.$.loadDiv("guarantorCompanyList", "${ctx}/credit/creApplyCompanyRelation/selectCompany", {
                                    applyNo : '${applyNo}',
                                    custId : '${custId}'
                                }, "post");
                                closeJBox();
                            } else {
                                alertx(data.message);

                            }
                        }

                    });


                }

        }

    </script>
</head>
<body>
<div class="searchInfo">
    <h3 class="searchTitle">担保公司</h3>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>

        <tr>
            <input type="hidden" id="flagId" value="${flag}" >
            <input type="hidden" id="applyNo" value="${applyNo}">
            <input type="hidden" id="custId" value="${custId}">
            <th width="3%" >选择</th>
            <th width="3%">序号</th>
            <th width="10%">担保公司名称</th>
            <th width="10%">统一社会信用代码</th>
            <th width="10%">企业性质</th>
            <th width="15%">担保余额(元)</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${companyList}" var="company" varStatus="companylist" >
            <tr>
                <td>
                    <input type="radio" name="companyRadio" id="companyId" value="${company.id}"/>
                </td>
                <td>${companylist.index+1}</td>
                <td>${company.guaranteeCompanyName}</td>
                <td>${company.unSocCreditNo}</td>
                <td>${fns:getDictLabel(company.companyType, 'GURANTEE_NATURE', '')}</td>
                <td>${company.guaranteeLimit}</td>
            </tr>
        </c:forEach>
        </tbody>
        <tr>
            <td class="searchButton" colspan="6" style="text-align: right;">

                <input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveCompany();"  />&nbsp;
                <input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="closeJBox();" />
                &nbsp;
            </td>
        </tr>
    </table>



</div>
</body>
</html>