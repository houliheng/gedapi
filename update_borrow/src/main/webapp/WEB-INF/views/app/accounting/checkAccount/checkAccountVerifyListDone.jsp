<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>待查账列表</title>
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
        $("#region").val("");
        $("#startDate").val("");
        $("#endDate").val("");
        $("#repayStatus").select2("val", "");
    }

    function listCheckAccountHis(applyId) {
        var url = "${ctx}/accounting/checkAccount/listApplyHis?applyId=" + applyId;
        openJBox("checkAccountHisForm", url, "查账历史", 800, 500);
    }
</script>
</head>
<body>
<div class="wrapper">
    <div class="searchInfo">
        <h3 class="searchTitle">查询条件</h3>
        <div class="searchCon">
            <form:form id="searchForm" modelAttribute="checkAccountVerify" action="${ctx}/accounting/checkAccountVerify/listDone"
                method="post" class="breadcrumb form-search">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
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
                            <td class="ft_label">大区</td>
                            <td class="ft_content">
                                <form:input path="region" htmlEscape="false" class="input-medium"/>
                            </td>
                            <td class="ft_label">交易日期</td>
                            <td class="ft_content">
                                <input id="startDate" name="startDate" type="text" class="input-medium required Wdate"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})">
                                <label>&nbsp;--&nbsp;</label>
                                <input id="endDate" name="endDate" type="text" class="input-medium required Wdate"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})">
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
                        <input id="btnReset" class="btn btn-primary" type="button" onclick="checkReset()" value="重置" />
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
                    <th width="10%">借款人姓名</th>
                    <th width="6%">汇款人姓名</th>
                    <th width="8%">还款日期</th>
                    <th width="8%">申请查账日期</th>
                    <th width="8%">交易日期</th>
                    <th width="8%">待还金额</th>
                    <th width="8%">查账金额</th>
                    <th width="8%">还款状态</th>
                    <th width="8%">大区</th>
                    <th width="14%">操作</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${page.list}" var="checkAccountVerify" varStatus="checkAccountVerifyList">
                        <tr>
                            <td class="title" title="${checkAccountVerifyList.count}">${checkAccountVerifyList.count}</td>
                            <td class="title" title="${checkAccountVerify.contractNo}">${checkAccountVerify.contractNo}</td>
                            <td class="title" title="${checkAccountVerify.borrowName}">${checkAccountVerify.borrowName}</td>
                            <td class="title" title="${checkAccountVerify.remittanceName}">${checkAccountVerify.remittanceName}</td>
                            <td class="title" title="<fmt:formatDate value="${checkAccountVerify.repayDate}" pattern="yyyy-MM-dd" />">
                                <fmt:formatDate value="${checkAccountVerify.repayDate}" pattern="yyyy-MM-dd" />
                            </td>
                            <td class="title" title="<fmt:formatDate value="${checkAccountVerify.applyDate}" pattern="yyyy-MM-dd" />">
                                <fmt:formatDate value="${checkAccountVerify.applyDate}" pattern="yyyy-MM-dd" />
                            </td>
                            <td class="title" type="<fmt:formatDate value="${checkAccountVerify.tradeDate}" pattern="yyyy-MM-dd HH:mm" />">
                                <fmt:formatDate value="${checkAccountVerify.tradeDate}" pattern="yyyy-MM-dd HH:mm" />
                            </td>
                            <td class="title" title="${checkAccountVerify.repayAmount}">${checkAccountVerify.repayAmount}</td>
                            <td class="title" title="${checkAccountVerify.checkAmount}">${checkAccountVerify.checkAmount}</td>
                            <td class="title" title="${fns:getDictLabel(checkAccountVerify.repayStatus, 'CONTRACT_STATE', '')}">
                                ${fns:getDictLabel(checkAccountVerify.repayStatus, 'CONTRACT_STATE', '')}
                            </td>
                            <td class="title" title="${checkAccountVerify.region}">${checkAccountVerify.region}</td>
                            <td class="title">
                                <a href="#" onclick="listCheckAccountHis('${checkAccountVerify.applyId}')">历史</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
