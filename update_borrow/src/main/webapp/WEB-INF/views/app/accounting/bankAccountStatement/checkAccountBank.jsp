<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>收款账户列表</title>
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
            $("#searchForm1").submit();
            return false;
        }

        function checkAccountBankFromReset() {
            $("#receiveBankName").val('');
            $("#accountName").val('');
            $("#accountNumber").val('');

        }


        function checkAccountBankTimeJudge() {
            $("#searchForm1").submit();
        }

        $(document).ready(function () {
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
            document.getElementById("newButton").onclick = function () {

                $("#receiveBankName2").val('');
                $("#accountName2").val('');
                $("#accountNumber2").val('');
                $("#bankDepositBranchName2").val('');
                $("#remark2").val('');
                $('#loginModalId').modal('show');
            }
            document.getElementById("newModalYesId").onclick = function () {

                var receiveBankName = $("#receiveBankName2").val();
                var accountName = $("#accountName2").val();
                var accountNumber = $("#accountNumber2").val();
                var bankDepositBranchName = $("#bankDepositBranchName2").val();
                var remark = $("#remark2").val();
                var url = "${ctx}/accounting/bankAccountStatement/insertCheckAccountBank";
                if (receiveBankName == '' || receiveBankName.length == 0) {
                    alertx("收款银行名称必填！(50个字符)");
                    return;
                }
                if (accountName == '' || accountName.length == 0) {
                    alertx("收款户名必填！(50个字符)");
                    return;
                }
                if (accountNumber == '' || accountNumber.length == 0) {
                    alertx("收款银行帐号必填！(20位数字)");
                    return;
                }
                $.ajax({
                    type: "post",
                    url: url,
                    dataType: "json",
                    data: {
                        receiveBankName: receiveBankName,
                        accountName: accountName,
                        accountNumber: accountNumber,
                        bankDepositBranchName: bankDepositBranchName,
                        remark: remark,
                        status: 1,
                    },
                    success: function (data) {
                        $('#loginModalId').modal('hide');
                        alertx(data.message);
                        window.location.href = "${ctx}/accounting/bankAccountStatement/checkAccountList";
                    },
                    error: function (msg) {
                        alertx("新增异常，请查看后台信息");
                    }
                });
            }


            document.getElementById("updateModalYesId").onclick = function () {
                var id = $("#id").val();
                var receiveBankName = $("#receiveBankName3").val();
                var accountName = $("#accountName3").val();
                var accountNumber = $("#accountNumber3").val();
                var bankDepositBranchName = $("#bankDepositBranchName3").val();
                var remark = $("#remark3").val();
                var url = "${ctx}/accounting/bankAccountStatement/updateAccountBankByModel";
                if (receiveBankName == '' || receiveBankName.length == 0) {
                    alertx("收款银行名称必填！(50个字符)");
                    return;
                }
                if (accountName == '' || accountName.length == 0) {
                    alertx("收款户名必填！(50个字符)");
                    return;
                }
                if (accountNumber == '' || accountNumber.length == 0) {
                    alertx("收款银行帐号必填！(20位数字)");
                    return;
                }
                $.ajax({
                    type: "post",
                    url: url,
                    dataType: "json",
                    data: {
                        id: id,
                        receiveBankName: receiveBankName,
                        accountName: accountName,
                        accountNumber: accountNumber,
                        bankDepositBranchName: bankDepositBranchName,
                        remark: remark
                    },
                    success: function (data) {
                        $('#updateModalId').modal('hide');
                        alertx(data.message);
                        window.location.href = "${ctx}/accounting/bankAccountStatement/checkAccountList";
                    },
                    error: function (msg) {
                        alertx("修改异常，请查看后台信息");
                    }
                });
            }
        });

        function updateCheckAccountStatus(id, status) {
            var url = "${ctx}/accounting/bankAccountStatement/updateCheckAccountBank";
            $.ajax({
                data: {id: id, status: status},
                type: "post",
                dataType: "json",
                url: url,
                success: function (data) {
                    if (data.msg == "success") {
                        alertx(data.message);
                        window.location.href = "${ctx}/accounting/bankAccountStatement/checkAccountList";
                    }
                    else {
                        alertx(data.message);
                    }
                }
            })
        }

        function getCheckAccountById(id) {
            $.ajax({
                type: "post",
                dataType: "json",
                data: {id: id},
                url: "${ctx}/accounting/bankAccountStatement/getCheckAccountById",
                success: function (data) {
                    $('#updateModalId').modal('show');
                    showUpdateCheckAccountBank(data);
                }, error: function (msg) {
                    alertx(msg);
                }
            });
        }

        function showUpdateCheckAccountBank(data) {
            data = data.data;
            $("#id").val(data.id);
            $("#receiveBankName3").val(data.receiveBankName);
            $("#accountName3").val(data.accountName);
            $("#accountNumber3").val(data.accountNumber);
            $("#bankDepositBranchName3").val(data.bankDepositBranchName);
            $("#remark3").val(data.remark);
        }


        var attime;
        function clock(){
            var mydate = new Date();
            var str = "" + mydate.getFullYear() + "-";
            str += (mydate.getMonth() + 1) + "-";
            str += mydate.getDate() + " ";
            str += mydate.getHours() + ":";
            str += mydate.getMinutes() + ":";
            str += mydate.getSeconds() + "";
            document.getElementById("createTime2").value = str;
        }
        setInterval(clock,1000);

    </script>
</head>
<body>
<div class="wrapper">
    <div class="searchInfo">
        <h3 class="searchTitle">查询条件</h3>
        <div class="searchCon">
            <form:form id="searchForm1" modelAttribute="checkAccountForm"
                       action="${ctx}/accounting/bankAccountStatement/checkAccountList" method="post">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <div class="filter">
                    <table class="searchTable" style="z-index: 99999999;">
                        <tr>
                            <td class="ft_label">收款银行：</td>
                            <td class="ft_content">
                                <select class="form-control" name="receiveBankName" id="branchBankNames"
                                        style="width: 180px;">
                                    <option value="" selected>请选择</option>
                                    <option value="">全部</option>
                                </select>
                            </td>
                            <td class="ft_label">收款户名：</td>
                            <td class="ft_content">
                                <input id="accountName" name="accountName" type="text"
                                       value="${checkAccountForm.accountName }" maxlength="50" class="input-medium"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="ft_label">收款账号：</td>
                            <td class="ft_content">
                                <input id="accountNumber" name="accountNumber" type="text"
                                       maxlength="50" class="input-medium"
                                       value="${checkAccountForm.accountNumber }"/>
                            </td>
                            <td class="ft_label" colspan="2" valign="middle">
                                <div class="searchButton">
                                    <input id="checkAccountBankbtnSubmit" class="btn btn-primary" type="button"
                                           value="查询"
                                           onclick="checkAccountBankTimeJudge();"/>
                                    &nbsp;
                                    <input id="checkAccountBankbtnReset" class="btn btn-primary" type="button"
                                           value="重置"
                                           onclick="return checkAccountBankFromReset();"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </form:form>
        </div>
    </div>
    <div>
        <input id="newButton" class="btn btn-primary" type="button" value="新增"/>
    </div>
    <div class="tableList">
        <h3 class="tableTitle">数据列表</h3>
        <div id="tableDataId" style="max-height:400px;overflow:auto;">
            <table cellpadding="0" cellspacing="0" border="0"
                   class="table table-striped table-bordered table-condensed table-hover">
                <thead>
                <tr>
                    <th width="20px">序号</th>
                    <th width="10%">收款户名</th>
                    <th width="10%">收款账号</th>
                    <th width="10%">收款银行</th>
                    <th width="10%">开户行</th>
                    <th width="15%">创建日期</th>
                    <th width="10%">备注</th>
                    <th width="10%">状态</th>
                    <th width="10%">操作</th>
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
                    <td class="title" title="${process.accountName}">${process.accountName}</td>
                    <td class="title" title="${process.accountNumber}">${process.accountNumber}</td>
                    <td class="title" title="${process.receiveBankName}">${process.receiveBankName}</td>
                    <td class="title" title="${process.bankDepositBranchName}">${process.bankDepositBranchName}</td>
                    <td class="title" title="<fmt:formatDate value="${process.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${process.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td class="title" title="${process.remark}">${process.remark}</td>
                    <td class="title" title="${process.status}">
                        <c:if test="${process.status eq 0}">
                            禁用
                        </c:if>
                        <c:if test="${process.status eq 1}">
                            启用
                        </c:if>
                    </td>
                    <td class="title" title="">
                        <a id="updateCheckAccountStatus"
                           onclick="updateCheckAccountStatus(${process.id},${process.status})"
                           href="javascript:void(0);">
                            <c:if test="${process.status eq 0}">
                                启用
                            </c:if>
                            <c:if test="${process.status eq 1}">
                                禁用
                            </c:if>
                        </a>
                        &nbsp;
                        <a onclick="getCheckAccountById(${process.id})"
                           href="javascript:void(0);">
                            <c:if test="${process.status eq 0}">
                                修改
                            </c:if>
                        </a>
                    </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="pagination">${page}</div>
    </div>

    <%--新增收款账户--%>
    <div class="modal hide fade" id="loginModalId" tabindex="-1" role="dialog" aria-hidden="true"
         data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    ×
                </button>
                <h4 class="modal-title" id="myModalLabel">新增收款账户信息》》
                    &nbsp;<span style="color: red;">带*为必填项</span></h4>
            </div>
            <div class="modal-body">
                <div class="input-group">
                    <span class="input-group-addon">收款银行：</span>
                    <input type="text" class="input-sm" id="receiveBankName2"
                           aria-describedby="basic-addon3">&nbsp;<span
                        style="color: red;">*</span>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">收款户名：</span>
                    <input type="text" class="input-sm" id="accountName2"
                           aria-describedby="basic-addon3">&nbsp;<span style="color: red;">*</span>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">收款账号：</span>
                    <input type="text" class="input-sm" id="accountNumber2"
                           aria-describedby="basic-addon3">&nbsp;<span style="color: red;">*</span>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">开户行：</span>
                    <input type="text" class="input-sm" id="bankDepositBranchName2" aria-describedby="basic-addon3">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">创建日期：</span>
                    <input type='text' class="form-control" id='createTime2' readonly="readonly"/>&nbsp;<span
                        style="color: red;">*</span>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">备注：</span>
                    <textarea name="remark2" id="remark2" cols="90" rows="5" class="form-control"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="newModalYesId">新增</button>
            </div>
        </div>
    </div>


    <%--修改收款账户--%>
    <div class="modal hide fade" id="updateModalId" tabindex="-1" role="dialog" aria-hidden="true"
         data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    ×
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改收款账户信息》》
                    &nbsp;<span style="color: red;">带*为必填项</span></h4>
            </div>
            <div class="modal-body">
                <div class="input-group">
                    <input type="hidden" id="id"/>
                    <span class="input-group-addon">收款银行：</span>
                    <input type="text" class="input-sm" id="receiveBankName3"
                           aria-describedby="basic-addon3">&nbsp;<span
                        style="color: red;">*</span>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">收款户名：</span>
                    <input type="text" class="input-sm" id="accountName3"
                           aria-describedby="basic-addon3">&nbsp;<span style="color: red;">*</span>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">收款账号：</span>
                    <input type="text" class="input-sm" id="accountNumber3"
                           aria-describedby="basic-addon3">&nbsp;<span style="color: red;">*</span>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">开户行：</span>
                    <input type="text" class="input-sm" id="bankDepositBranchName3" aria-describedby="basic-addon3">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">备注：</span>
                    <textarea id="remark3" cols="90" rows="5" class="form-control"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="updateModalYesId">确认修改</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>