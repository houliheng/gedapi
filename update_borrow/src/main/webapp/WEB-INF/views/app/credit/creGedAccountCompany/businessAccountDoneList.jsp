<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
    <title>企业开户已办列表</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            if($("#pageNo")[0].value.length>10){
                top.$.jBox.tip('当前页码大小长度不能大于10位！');
                return true;
            }
            if($("#pageSize")[0].value.length>10){
                top.$.jBox.tip('每页条数大小的长度不能大于10位！');
                return true
            }
            $("#searchForm").submit();
            return false;
        }
        //提交
        function formSubmit(){
            loading();
            $("#searchForm").submit();

        }
        //重置
        function formReset(){
            $("#companyName").val("");
            $("#createTime").val("");
        }

        $(document).ready(function() {
            var tds = $(".title").filter("td");
            $.each(tds,function(){
                $(this).attr("title",$.trim($(this).text()));
            });
        });

        //历史
        function detailHistory(id){
            var width = $(window).width() - 100;
            var url = "${ctx}/credit/accCompanyHistory/historyDetail?id="+id;
            openJBox("historyDetail", url, "历史", width, 500, null);
        }

    </script>
</head>

<body>
<div class="wrapper">
    <div class="searchInfo">
        <h3 class="searchTitle">查询条件</h3>
        <div class="searchCon">
            <form:form id="searchForm" modelAttribute="creGedAccountCompany"
                       action="${ctx}/credit/creGedAccountCompany/accountDoneBusiness" method="post" class="breadcrumb form-search">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <div class="filter">
                    <table class="searchTable">
                        <tr>

                            <td class="ft_label">企业客户名称</td>
                            <td class="ft_content">
                                <input id="companyName" name="companyName" type="text" maxlength="50" class="input-medium"  value="${creGedAccountCompany.companyName}" />
                            </td>
                        </tr>
                        <tr>
                            <td class="ft_label">审核日期</td>
                            <td class="ft_content">
                                <input id="createTime" name="createTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${creGedAccountCompany.createTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',isShowClear:true});" />
                            </td>
                        </tr>
                    </table>
                    <div class="searchButton">
                        <input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="formSubmit()"/>
                        <input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="formReset()"/>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
    <div class="ribbon">

    </div>
    <sys:message content="${message}"/>
    <div class="tableList">
        <h3 class="tableTitle">数据列表</h3>
        <div id="tableDataId" style="max-height:400px;overflow:auto;">
            <table   class="table table-striped table-bordered table-condensed">
                <tr>
                    <th width="10%">序号</th>
                    <th width="13%">企业客户名称</th>
                    <th width="13%">社会统一信用代码</th>
                    <th width="13%">联系人手机号</th>
                    <th width="13%">审核状态</th>
                    <th width="13%">审核日期</th>
                    <th width="30%">操作</th>
                </tr>

                <c:forEach items="${page.list}" var="accountCompany" varStatus="index">
                <tr>
                    <td title="title">${index.count}</td>
                    <td title="${accountCompany.companyName}"  class="title">${accountCompany.companyName} </td>
                    <td title="${accountCompany.socialCreditCode}"  class="title">${accountCompany.socialCreditCode}  </td>
                    <td title="${accountCompany.contactMobile}"class="title">${accountCompany.contactMobile} </td>
                    <td title="${fns:getDictLabel(accountCompany.status, 'ACCOUNT_COMPANY', '')}"  class="title">${fns:getDictLabel(accountCompany.status, 'ACCOUNT_COMPANY', '')}</td>
                    <td class="title">
                        <fmt:formatDate value="${accountCompany.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <a href="javascript:void(0)" onclick="detailHistory('${accountCompany.id}')">历史</a>
                    </td>
                </tr>
                </c:forEach>
            </table>
        </div>
        <div class="pagination">${page}</div>
    </div>
</body>
</html>

