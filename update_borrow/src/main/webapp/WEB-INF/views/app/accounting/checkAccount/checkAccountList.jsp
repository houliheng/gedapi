<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>查账申请待办列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
    function page(n, s) {
        $("#pageNo").val(n);
        $("#pageSize").val(s);
        $("#searchForm").submit();
        return false;
    }

    function checkReset() {
        $("#contractNo").val("");
        $("#borrowName").val("");
        $("#loanType").select2("val", "");
        $("#customName").val("");
        $("#repayStatus").select2("val", "");
    }

    function checkAccountApply(contractNo) {
        var url = "${ctx}/accounting/checkAccount/checkAccountApply?contractNoTemp=" + contractNo;
        $("#searchForm").attr("action", url);
        $("#searchForm").submit();
    }

    function listCheckAccountHis(contractNo) {
        var url = "${ctx}/accounting/checkAccount/listHistory?contractNo=" + contractNo;
        openJBox("checkAccountHisForm", url, "查账历史", 800, 500);
    }

</script>
</head>
<body>
<div class="wrapper">
    <div class="searchInfo">
        <h3 class="searchTitle">查询条件</h3>
        <div class="searchCon">
            <form:form id="searchForm" modelAttribute="checkAccount" action="${ctx}/accounting/checkAccount"
                       method="post" class="breadcrumb form-search">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
                <input type="hidden" id="contractNoTmp" name="contractNoTmp" />
                <div class="filter">
                    <table class="searchTable">
                        <tr>
                            <td class="ft_label">合同编号</td>
                            <td class="ft_content">
                                <form:input path="contractNo" htmlEscape="false" maxlength="30" class="input-medium" />
                            </td>
                            <td class="ft_label">主借人姓名</td>
                            <td class="ft_content">
                                <form:input path="borrowName" htmlEscape="false" maxlength="30" class="input-medium" />
                            </td>
                        </tr>
                        <tr>
                            <td class="ft_label">借款类型</td>
                            <td class="ft_content">
                                <form:select path="loanType" class="input-medium">
                                    <form:option value="" label="全部"/>
                                    <form:option value="1" label="个人"/>
                                    <form:option value="2" label="企业"/>
                                </form:select>
                            </td>
                            <td class="ft_label">企业客户名称</td>
                            <td class="ft_content">
                                <form:input path="customName" htmlEscape="false" maxlength="30" class="input-medium" />
                            </td>
                        </tr>
                        <tr>
                            <td class="ft_label">还款状态</td>
                            <td class="ft_content">
                                <form:select path="repayStatus" class="input-medium">
                                    <form:option value="" label="请选择" />
                                    <form:option value="0600" label="无逾期-还款中" />
                                    <form:option value="0800" label="有逾期-还款中" />
                                </form:select>
                            </td>
                        </tr>
                    </table>
                    <div class="searchButton">
                        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
                        &nbsp;
                        <input id="btnReset" class="btn btn-primary" type="button" onclick="return checkReset();" value="重置" />
                    </div>
                </div>
            </form:form>
        </div>
    </div>
    <sys:message content="${message}" />
    <div class="tableList">
        <h3 class="tableList">数据列表</h3>
        <div id="tableDataId">
            <table id="contentTable" class="table table-striped table-bordered table-condensed">
                <thead>
                    <tr>
                        <th width="4%">序号</th>
                        <th width="10%">合同编号</th>
                        <th width="10%">借款人名称</th>
                        <th width="6%">借款类型</th>
                        <th width="8%">合同金额</th>
                        <th width="6%">产品名称</th>
                        <th width="10%">企业客户名称</th>
                        <th width="8%">还款状态</th>
                        <th width="8%">大区</th>
                        <th width="8%">区域</th>
                        <th width="8%">分公司</th>
                        <th width="14%">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${page.list}" var="checkAccount" varStatus="checkAccountList">
                        <tr>
                            <td class="title" title="${checkAccountList.count}">${checkAccountList.count}</td>
                            <td class="title" title="${checkAccount.contractNo}">${checkAccount.contractNo}</td>
                            <td class="title" title="${checkAccount.borrowName}">${checkAccount.borrowName}</td>
                            <td class="title" title="${fns:getDictLabel(checkAccount.loanType, 'CUST_TYPE', '无')}">
                                    ${fns:getDictLabel(checkAccount.loanType, 'CUST_TYPE', '无')}</td>
                            <td class="title" title="${checkAccount.contractAmount}">${checkAccount.contractAmount}</td>
                            <td class="title" title="${checkAccount.productName}">${checkAccount.productName}</td>
                            <td class="title" title="${checkAccount.customName}">${checkAccount.customName}</td>
                            <td class="title" title="${fns:getDictLabel(checkAccount.repayStatus, 'CONTRACT_STATE', '')}">
                                    ${fns:getDictLabel(checkAccount.repayStatus, 'CONTRACT_STATE', '')}</td>
                            <td class="title" title="${checkAccount.region}">${checkAccount.region}</td>
                            <td class="title" title="${checkAccount.area}">${checkAccount.area}</td>
                            <td class="title" title="${checkAccount.branchOffice}">${checkAccount.branchOffice}</td>
                            <td class="title">
                                <a href="#" onclick="checkAccountApply('${checkAccount.contractNo}')">申请查账</a>
                                &nbsp;
                                <a href="#" onclick="listCheckAccountHis('${checkAccount.contractNo}')">历史</a>
                            </td>
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
