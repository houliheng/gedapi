<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>担保企业选择</title>
    <meta name="decorator" content="default" />
    <%@ include file="/WEB-INF/views/include/cityselectJS.jsp"%>
    <script type="text/javascript">
        $(document).ready(function() {

        });

        // 添加要跟订单关联的担保公司信息
        function  saveCompany(){

            var applyNo  = $("#applyNo").val();
            var piliId = "${piliId}";

            var $checkLine = $("input[name='guarantorIds']:checked");
            var $len = $checkLine.length;
            if ($len < 1) {
                alertx("请选择要担保的担保人信息");
                return false;
            }else {
                var checkedValue = getCheckedValue("guarantorIds");
                $.ajax({
                    url:"${ctx}/credit/creApplyCompanyRelation/saveCompanyFromList",
                    data:{
                        ids : checkedValue,
                        applyNo : applyNo,
                        piliId : piliId
                    },
                    type : "post",
                    success : function (data) {
                        if(data.status == '1'){
                            alertx(data.message);
                   /*  $.loadDiv("guarantorCompanyInfo", "${ctx}/credit/guarantorCompanyInfo/guaranteeList", {
                                custId : '${custId}',
                                applyNo : '${applyNo}',
                                readOnly :'${readOnly}',
                            }, "post");*/
                            parent.$.loadDiv("guarantorCompanyInfo", "${ctx}/credit/guarantorCompanyInfo/guaranteeList", {
                                custId :  piliId,
                                applyNo :  applyNo
                            }, "post");

                            closeJBox();
                        } else {
                            alertx(data.message)
                        }
                    }
                });
            }

        }

    </script>
</head>
<body>
<div class="searchInfo">
    <h3 class="searchTitle">担保企业列表</h3>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>

        <tr>

            <input type="hidden" id="applyNo" value="${applyNo}"> <!--申请编号-->
            <input type="hidden" id="piliId" value="${piliId}"><!--借款企业主键-->
            <th width="3%">
                <input type="checkbox" id="checkAllGuarantor" name="checkAllGuarantor" onclick="allCheck('checkAllGuarantor','guarantorIds');" />
            </th>
            <th width="3%">序号</th>
            <th width="10%">工商登记名称</th>
            <th width="10%">组织机构代码</th>
            <th width="10%">统一社会信用代码</th>
            <th width="10%">经营期限</th>
            <th width="10%">注册资金</th>
            <th width="10%">成立时间</th>
        </tr>
        </thead>
        <tbody>


        <c:forEach items="${companyInfoList}" var="companyInfo"
                   varStatus="companyInfoList">
            <tr>
                <td>
                    <input type="checkbox" name="guarantorIds" id="guarantorIds" value="${companyInfo.id }"/>
                </td>
                <td>${companyInfoList.index+1 }</td>
                <td>${companyInfo.busiRegName}</td>
                <td>${companyInfo.organizationNo}</td>
                <td>${companyInfo.unSocCreditNo }</td>
                <td>${companyInfo.operatePeriod }</td>
                <td><fmt:formatNumber value="${companyInfo.registerCapital }" pattern="###,##0.00"></fmt:formatNumber></td>
                <td><fmt:formatDate value="${companyInfo.foundDate}" pattern="yyyy-MM-dd"/></td>
            </tr>
        </c:forEach>

        </tbody>
        <tr>
            <td class="searchButton" colspan="10" style="text-align: right;">

                <input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveCompany();" />&nbsp;
                <input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="closeJBox();" />&nbsp;
            </td>
        </tr>
    </table>



</div>
</body>
</html>