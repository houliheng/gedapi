<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>合同管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#loanCompanyName").val('');
            $("#contractNo").val('');
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        function del() {
        	$("#loanCompanyName").val('');
            $("#contractNo").val('');
        }
       
        function changeStatus(contractNo,type){
			$.post("${ctx}/credit/contractSearch/getDownloadPath?contractNo="+contractNo+"&&type="+type,null,function(data){
	    		if(data.status == '1'){
  		    			var msg = data.message;
						msg =encodeURI(encodeURI(msg));
						var url = "${ctx}/credit/contractSearch/download?contractNo="+contractNo+"&&type="+type+"&&msg="+msg;
						window.location.href=url; 
	    		}else{
	    			alertx("文件不存在！");
	    		}
		    });
			
			
			
		}
        
			/* var url = "${ctx}/credit/contractSearch/download?downType=1&&contractNo="+contractNo+"&&type"+type;
			window.location.href=url; */
    </script>
</head>
<body>
<div class="wrapper">
    <div class="searchInfo">
        <h3 class="searchTitle">查询条件</h3>
        <div class="searchCon">
            <form:form id="searchForm" modelAttribute="contract" action="${ctx}/credit/contractSearch/list" method="post" class="breadcrumb form-search">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <div class="filter">
                    <table class="fromTable filter">
                        <tr>
                            <td class="ft_label">客户名称：</td>
                            <td class="ft_content">
                                <form:input path="loanCompanyName" htmlEscape="false" maxlength="32" class="input-medium"/>
                            </td>
                            <td class="ft_label">合同编号:</td>
                            <td class="ft_content">
                                <form:input path="contractNo" htmlEscape="false" maxlength="32" class="input-medium"/>
                            </td>
                        </tr>
                    </table>
                    <div class="searchButton">
                        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp;
                        <input id="btnReset"class="btn btn-primary" type="button" value="重置" onclick="del()" />
                    </div>
                </div>
            </form:form>
        </div>
    </div>
    <sys:message content="${message}"/>
    <div class="tableList">
        <h3 class="tableTitle">数据列表</h3>
        <div id="tableDataId">
            <table id="contentTable" class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th>客户名称</th>
                    <th>产品类型 </th>
                    <th>担保公司</th>
                    <th>合同编号</th>
                    <th>电子合同</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="contract"  varStatus="status">
                    <tr>
                        <td class="title" title="${contract.loanCompanyName}">${contract.loanCompanyName}</td>
                        <td class="title" title="${contract.approProductTypeName}">${contract.approProductTypeName}</td>
                        <td class="title" title="${contract.guaranteeCompanyName}">${contract.guaranteeCompanyName}</td>
                        <td class="title" title="${contract.contractNo}">${contract.contractNo}</td>
                        
                        <td class="title">
                        	<a href="javascript:void(0)" onclick="changeStatus('${contract.contractNo}','201')">借款服务协议</a><br>
                        	<a href="javascript:void(0)" onclick="changeStatus('${contract.contractNo}','202')">信息采集授权书-企业</a><br>
                        	<a href="javascript:void(0)" onclick="changeStatus('${contract.contractNo}','203')">信息采集授权书-个人</a><br>
                        	<a href="javascript:void(0)" onclick="changeStatus('${contract.contractNo}','205')">授权提现函</a><br>
                        	<a href="javascript:void(0)" onclick="changeStatus('${contract.contractNo}','206')">电子签名数字证书用户申请确认函</a><br>
                        	<a href="javascript:void(0)" onclick="changeStatus('${contract.contractNo}','207')">网络借贷平台借款情况披露及承诺书</a><br>
                        	<a href="javascript:void(0)" onclick="changeStatus('${contract.contractNo}','2')">借款合同</a>
                        </td>
                        <td>
                            <a href="javascript:void(0)" onclick="changeStatus('${contract.contractNo}','1')">下载电子合同</a>
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