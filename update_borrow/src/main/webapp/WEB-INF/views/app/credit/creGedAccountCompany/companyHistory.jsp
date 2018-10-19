<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
    <title>操作历史</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">

        $(document).ready(function() {
            var tds = $(".title").filter("td");
            $.each(tds,function(){
                $(this).attr("title",$.trim($(this).text()));
            });
        });

    </script>
</head>

<body>
    <div class="tableList">
        <div id="tableDataId" style="max-height:400px;overflow:auto;">
            <table   class="table table-striped table-bordered table-condensed">
                <tr>
                    <th width="10%">状态</th>
                    <th width="13%">操作</th>
                    <th width="13%">操作时间</th>
                    <th width="13%">操作人</th>
                    <th width="13%">备注</th>
                </tr>
                <c:forEach items="${accCompanyHistoList}" var="histo" varStatus="index">
                <tr>
                    <td title="${fns:getDictLabel(histo.status, 'ACCOUNT_COMPANY', '')}"  class="title">${fns:getDictLabel(histo.status, 'ACCOUNT_COMPANY', '')}</td>
                    <td title="${histo.operate}"  class="title">${histo.operate} </td>
                    <td class="title">
                        <fmt:formatDate value="${histo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td title="${histo.userName}"  class="title">${histo.userName}  </td>
                    <td title="${histo.comment}"  class="title">${histo.comment}  </td>
                </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</body>
</html>

