<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>银行流水查询列表</title>
    <meta name="decorator" content="default"/>

    <script type="text/javascript">
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            if ($("#pageNo")[0].value.length > 10) {
                top.$.jBox.tip('当前页码大小长度不能大于10位！');
                return true;
            }
            if ($("#pageSize")[0].value.length > 10) {
                top.$.jBox.tip('每页条数大小的长度不能大于10位！');
                return true;
            }
            $("#searchForm2").submit();
            return false;
        }

        function fromReset() {
            $("#createTradeStartTime").val('');
            $("#createTradeEndTime").val('');
            $("#userName3").val('');
            $("#receiveAccountName3").val('');
            $("#receiveAccountNumber3").val('');
        }

        function timeJudge() {
            if ($("#createTradeStartTime").val() != '' & $("#createTradeEndTime").val() != '') {
                if ($("#createTradeStartTime").val() > $("#createTradeEndTime").val()) {
                    alertx("开始时间应小于结束时间！");
                    return;
                } else {
                    $("#searchForm2").submit();
                    return;
                }
            }
            $("#searchForm2").submit();
        }

        $(document).ready(function () {
            document.getElementById("exportCheckAccountStatementButton").onclick = function () {
                loadReceliveBankImport();
                $("#receiveAccountName2").val("");
                $('#importModalId').modal('show');
            }
            document.getElementById("importModalYesId").onclick = function () {
                var excelFile = $("#excelFile").val();
                var receiveBankName = $("#banknamesimport option:selected").val();
                var accountNum = $("#receiveAccountNumbers option:selected").val();
                var aNmae = $("#receiveAccountName2").val();
                if (receiveBankName == "" || receiveBankName.length == 0) {
                    alertx("收款银行你还没有选择！");
                    return;
                }
                if (accountNum == "" || accountNum.length == 0) {
                    alertx("收款账号你还没有选择！");
                    return;
                }
                if (aNmae == "" || aNmae.length == 0) {
                    alertx("收款户名你还没有选择！");
                    return;
                }
                if (excelFile == "" || excelFile.length == 0) {
                    alertx("Excel文件你还没有选择！");
                    return;
                }

                $("#importExcelForm").submit();
                $('#importModalId').modal('hide');
            }

        });

        function loadReceliveBankImport() {
            $("#banknamesimport").empty();
            $.ajax({
                type: "post",
                dataType: "json",
                url: "${ctx}/accounting/bankAccountStatement/loadBankImport",
                success: function (data) {
                    var info = "<option value='' selected>请选择</option>";
                    var temp = eval(data);
                    for (var i = 0; i < temp.length; i++) {
                        info += "<option value='" + temp[i].receiveBankName + "'>" + temp[i].receiveBankName + "</option>";
                    }
                    $("#banknamesimport").append(info);
                }
            });
        }




        function loadReceliveBank() {
            $.ajax({
                type: "post",
                dataType: "json",
                url: "${ctx}/accounting/bankAccountStatement/loadBank",
                success: function (data) {
                    var info = '';
                    var temp = eval(data);
                    for (var i = 0; i < temp.length; i++) {
                        info += "<option value='" + temp[i].receiveBankName + "'>" + temp[i].receiveBankName + "</option>";
                    }
                    $("#branchBankNames").append(info);
                }
            });
        }

        $(document).ready(function () {
            loadReceliveBank();
            $("#banknamesimport").change(function () {
                $("#receiveAccountName2").val('');
                var receiveBankName = $("#banknamesimport option:selected").val();//获取下拉列表中的选中项
                $("#receiveAccountNumbers").empty();
                $.post("${ctx}/accounting/bankAccountStatement/loadReceiveBankNumber", {receiveBankName: receiveBankName}, function (data) {
                    var profession = "<option value='' selected>请选择</option>";
                    $("#receiveAccountNumbers").append(profession);
                    var temp = eval(data);
                    for (var i = 0; i < temp.length; i++) {
                        var opt = "<option value='" + temp[i].accountNumber + "'>" + temp[i].accountNumber + "</option>";
                        $("#receiveAccountNumbers").append(opt);
                    }
                });
            });
            $("#receiveAccountNumbers").change(function () {
                var accountNum = $("#receiveAccountNumbers option:selected").val();//获取下拉列表中的选中项
                $.post("${ctx}/accounting/bankAccountStatement/loadAccountName", {accountNum: accountNum}, function (data) {
                    $("#receiveAccountName2").val(data.accountName);
                });
            });
        });
    </script>
</head>
<body>
<div class="wrapper">
    <div class="searchInfo">
        <h3 class="searchTitle">查询条件</h3>
        <div class="searchCon">
            <form:form id="searchForm2" modelAttribute="checkAccountForm"
                       action="${ctx}/accounting/bankAccountStatement/checkAccountStatementList"
                       method="post">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <div class="filter">
                    <table class="searchTable" style="z-index: 99999999;">
                        <tr>
                            <td class="ft_label">交易日期：</td>
                            <td class="ft_content" style="width: 340px;">
                                <input id="createTradeStartTime" name="createTradeStartTime" type="text"
                                       readonly="readonly"
                                       style="width: 120px;"
                                       maxlength="50" class="input-mini Wdate"
                                       value="<fmt:formatDate value="${checkAccountForm.createTradeStartTime }" pattern="yyyy-MM-dd"/>"
                                       onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
                                <label>&nbsp;--&nbsp;</label>
                                <input id="createTradeEndTime" name="createTradeEndTime" type="text" readonly="readonly"
                                       maxlength="50" class="input-mini Wdate" style="width: 120px;"
                                       value="<fmt:formatDate value="${checkAccountForm.createTradeEndTime }" pattern="yyyy-MM-dd"/>"
                                       onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
                            </td>
                            <td class="ft_label">对方户名：</td>
                            <td class="ft_content">
                                <input id="userName3" name="userName" type="text" maxlength="50" class="input-medium"
                                       value="${checkAccountForm.userName }"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="ft_label">交易银行：</td>
                            <td class="ft_content">
                                <div class="form-group">
                                    <select class="form-control" name="branchBankName" id="branchBankNames"
                                            style="width: 180px;">
                                        <option value="" selected>请选择</option>
                                        <option value="">全部</option>
                                    </select>
                                </div>
                            </td>
                            <td class="ft_label">收款户名：</td>
                            <td class="ft_content">
                                <input id="receiveAccountName3" name="receiveAccountName" type="text" maxlength="50"
                                       class="input-medium"
                                       value="${checkAccountForm.receiveAccountName }"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="ft_label">收款账号：</td>
                            <td class="ft_content">
                                <input id="receiveAccountNumber3" name="receiveAccountNumber" type="text" maxlength="50"
                                       class="input-medium"
                                       value="${checkAccountForm.receiveAccountNumber }"/>
                            </td>
                            <td class="ft_label">状态：</td>
                            <td class="ft_content">
                                <div class="form-group">
                                    <select class="form-control" name="status" style="width: 180px;">
                                        <option value="" selected>请选择</option>
                                        <option value="0">未入账</option>
                                        <option value="1">已入账</option>
                                    </select>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <div class="searchButton">
                        <input id="btnSubmitStatement" class="btn btn-primary" type="button" value="查询"
                               onclick="timeJudge();"/>
                        &nbsp;
                        <input id="btnResetStatement" class="btn btn-primary" type="button" value="重置"
                               onclick="return fromReset();"/>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
    <div>
        <input id="exportCheckAccountStatementButton" class="btn btn-primary" type="button" value="导入"/>
        <span>导入状态：</span><span style="color: red;">${message}</span>
        <h3 style="margin-left: 20px;">收入总金额：${sumAmount}</h3>
    </div>
    <div class="tableList">
        <h3 class="tableTitle">数据列表</h3>
        <div id="tableDataId" style="max-height:400px;overflow:auto;">
            <table cellpadding="0" cellspacing="0" border="0"
                   class="table table-striped table-bordered table-condensed table-hover">
                <thead>
                <tr>
                    <th width="20px">序号</th>
                    <th width="25%">交易日期</th>
                    <th width="10%">收入金额（元）</th>
                    <th width="10%">未入账金额</th>
                    <th width="10%">已入账金额</th>
                    <th width="10%">对方户名</th>
                    <th width="20%">对方账号</th>
                    <th width="10%">交易银行</th>
                    <th width="20%">交易银行分支机构</th>
                    <th width="10%">收款户名</th>
                    <th width="20%">收款账号</th>
                    <th width="10%">摘要</th>
                    <th width="10%">状态</th>
                </tr>
                </thead>
                <c:forEach items="${page.list}" var="process" varStatus="index">
                    <c:if test="${0 == index.count%2}">
                        <tr class="doubleRow">
                    </c:if>
                    <c:if test="${1 == index.count%2}">
                        <tr>
                    </c:if>

                    <td class="title" title="${index.count}">${index.count}</td>
                    <td class="title" title="<fmt:formatDate value="${process.tradeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${process.tradeDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td class="title" title="${process.tradeAmount}">${process.tradeAmount}</td>
                    <td class="title" title="${process.unAccountAmount}">${process.unAccountAmount}</td>
                    <td class="title" title="${process.enterAccountAmount}">${process.enterAccountAmount}</td>
                    <td class="title" title="${process.userName}">${process.userName}</td>
                    <td class="title" title="${process.accountNumber}">${process.accountNumber}</td>
                    <td class="title" title="${process.bankCode}">${process.bankCode}</td>
                    <td class="title" title="${process.branchBankName}">${process.branchBankName}</td>
                    <td class="title" title="${process.receiveAccountName}">${process.receiveAccountName}</td>
                    <td class="title" title="${process.receiveAccountNumber}">${process.receiveAccountNumber}</td>
                    <td class="title" title="${process.remark}">${process.remark}</td>
                    <td class="title" title="${process.status}">
                        <c:if test="${process.status==0}">
                            未入账
                        </c:if>
                        <c:if test="${process.status==1}">
                            已入账
                        </c:if>
                    </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="pagination">${page}</div>
    </div>
    <div class="modal hide fade" id="importModalId" tabindex="-1" role="dialog" aria-hidden="true"
         data-backdrop="static">
        <form:form action="${ctx}/accounting/bankAccountStatement/bankStatementImport" id="importExcelForm" method="post"
                   enctype="multipart/form-data">
            <div class="modal-dialog">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        ×
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入数据 》》
                        &nbsp;<span style="color: red;">带*为必填项</span>
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon">收款银行：</span>
                        <select class="form-control" name="branchBankName" id="banknamesimport" style="width: 200px;">
                            <option value="">请选择</option>
                        </select>&nbsp;<span
                            style="color: red;">*</span>
                    </div>
                    <div class="input-group" style="margin-top: 20px;">
                        <span class="input-group-addon">收款账号：</span>
                        <select class="form-control" name="receiveAccountNumber" id="receiveAccountNumbers"

                                style="width: 200px;">
                            <option value="" selected>请选择</option>
                        </select>&nbsp;<span
                            style="color: red;">*</span>
                    </div>
                    <div class="input-group"  style="margin-top: 20px;">
                        <span class="input-group-addon">收款户名：</span>
                        <input type="text" class="input-sm" id="receiveAccountName2" name="receiveAccountName"
                               aria-describedby="basic-addon3"
                               value="" readonly="readonly">&nbsp;<span
                            style="color: red;">*</span>
                    </div>
                    <div class="input-group"  style="margin-top: 20px;">
                        <span class="input-group-addon">文件选择：</span>
                        <input type="file" class="input-sm" id="excelFile" name="file"
                               aria-describedby="basic-addon3"
                               accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">&nbsp;<span
                            style="color: red;">*(只能导入EXCEL格式的文件)</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="importModalYesId">导入确认</button>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>