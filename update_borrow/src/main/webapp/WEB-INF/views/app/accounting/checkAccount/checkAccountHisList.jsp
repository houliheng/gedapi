<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>查账申请历史</title>
<meta name="decorator" content="default" />
</head>
<body>
<div class="tableList">
    <h3 class="tableList">历史数据列表</h3>
    <div id="tableDataId">
        <table id="contentTable" class="table table-striped table-bordered table-condensed">
            <thead>
                <tr>
                    <th>节点</th>
                    <th>操作人</th>
                    <th>操作时间</th>
                    <th>操作内容</th>
                    <th>备注</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="checkAccountHis" varStatus="checkAccountHisList">
                <tr>
                    <td class="title" title="${checkAccountHis.nodeName}">${checkAccountHis.nodeName}</td>
                    <td class="title" title="${checkAccountHis.createBy.name}">${checkAccountHis.createBy.name}</td>
                    <td class="title" title="<fmt:formatDate value="${checkAccountHis.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />">
                        <fmt:formatDate value="${checkAccountHis.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    <td class="title" title="${checkAccountHis.content}">${checkAccountHis.content}</td>
                    <td class="title" title="${checkAccountHis.remark}">${checkAccountHis.remark}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
