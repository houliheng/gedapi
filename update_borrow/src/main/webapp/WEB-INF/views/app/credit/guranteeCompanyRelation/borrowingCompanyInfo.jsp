<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>借款企业信息列表 </title>
</head>
<script type="text/javascript">
    $(document).ready(function() {
        if ('${readOnly}' == '0') {
            setPageReadOnly();
        }
    });
    // -100
    var width = $(window).width() - 100;
    //新增担保企业信息  不用这个
    function addGuarantorCompany() {
        var url = "${ctx}/credit/creApplyCompanyRelation/jumpCompanyInfo";
        openJBox("companyInfo-form", url, "新增批量借款企业信息", width, 600, {
            applyNo : $("#applyNo").val()
        });
    }


    //新增批量借款企业  这是现在使用的
    function ApplyCompanyRelation(){
        var applyNo = $("#applyNo").val();
        var url = "${ctx}/credit/creApplyCompanyRelation/compinySave";
        openJBox("companfo-form", url, "新增批量借款企业信息", width, 600, {
            applyNo : applyNo
        });




    }

    // 批量借款企业与担保公司 担保人 等的关联
    function luruGuarantorCompany(cust_id){
        var applyNo = $("#applyNo").val();

        /*   弹窗样式显示不行
          var url = "${ctx}/credit/creApplyCompanyRelation/jump?applyNo="+applyNo+"&custId="+cust_id;
        openJBox("companyInm", url, "新增批量借款企业信息", width, 600, {
            applyNo : $("#applyNo").val()
        });*/

        window.location.href="${ctx}/credit/creApplyCompanyRelation/jump?applyNo="+applyNo+"&custId="+cust_id+"&readOnly="+'${readOnly}';
    }


    //删除担保企业信息
    function deleteGuarantorCompany() {
        var $checkLine = $("input[name='guarantorCompanyIds']:checked");
        var $len = $checkLine.length;
        if ($len < 1) {
            alertx("请选择要删除的借款企业");
        } else {
            var checkedValue = getCheckedValue("guarantorCompanyIds");
            var applyNo = $("#applyNo").val();
            var url = "${ctx}/credit/guarantorCompanyInfo/delete?ids=" + checkedValue +"&&applyNo="+applyNo;
            confirmx('确认要删除勾选的借款企业信息吗？', function() {
                saveJson(url, null, function(data) {
                    if (data) {
                        if (data.status == 1) {

                            alertx(data.message);
                            $.loadDiv("guarantorCompanyInfo", "${ctx}/credit/creApplyCompanyRelation/queryCompanyList", {
                                applyNo : $("#applyNo").val()
                            }, "post");

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
        var url = "${ctx}/credit/guarantorCompanyInfo/form?companyId=" + companyId
            + "&&readOnly=0&&applyNo="+$("#applyNo").val();
        openJBox("companyInfo-detai", url, "借款企业信息详情", width, 600, null);
    }

    //编辑
    function editGuarantorCompany() {
        var $checkLine = $("input[name='guarantorCompanyIds']:checked");
        var $len = $checkLine.length;
        if ($len != 1) {//需要勾选一条信息进行修改
            alertx("请选择一条借款企业信息！");
        } else {
            var url = "${ctx}/credit/guarantorCompanyInfo/form?companyId="
                + $checkLine.val() + "&&readOnly=1&&applyNo="+$("#applyNo").val();
            openJBox("companyInfo-detai", url, "编辑借款企业信息", width, 600, null);
        }

    }

    function guasdafranB(){
        $("#guaBId").toggle(600);
    }
</script>
<body>
<div class="tableList">
    <sys:message content="${message}" />
    <div id="tableDataId" style="max-height: 400px; overflow: auto;">
        <div class="searchInfo">
            <h3 onclick="guasdafranB()" class="searchTitle">批量借款企业信息</h3>
            <div id="guaBId" class="ribbon filter">

                <table id="contentTable"
                       class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th width="10%">工商登记名称</th>
                        <th width="10%">组织机构代码</th>
                        <th width="10%">统一社会信用代码</th>
                        <th width="10%">经营期限</th>
                        <th width="10%">注册资金(元)</th>
                        <th width="10%">成立时间</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:if test="${not empty companyInfo}" >
                            <tr>
                            <td>${companyInfo.busiRegName}</td>
                            <td>${companyInfo.organizationNo}</td>
                            <td>${companyInfo.unSocCreditNo }</td>
                            <td>${companyInfo.operatePeriod }</td>
                            <td><fmt:formatNumber value="${companyInfo.registerCapital }" pattern="###,##0.00"></fmt:formatNumber></td>
                            <td><fmt:formatDate value="${companyInfo.foundDate}" pattern="yyyy-MM-dd"/></td>
                            </tr>
                        </c:if>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
