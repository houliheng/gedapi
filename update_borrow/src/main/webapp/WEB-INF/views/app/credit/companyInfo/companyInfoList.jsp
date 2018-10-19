<%@ page contentType="text/html;charset=UTF-8" %>
	<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<script type="text/javascript">
	
	function company(){
	$("#companyId").toggle(600);
	}
	</script>
</head>	
<body>
		<div id="companyId">
		<div class="tableList">
		<div class="ribbon">
		    	<ul class="layout">
		    		<li class="add"><a id="add" href="${ctx}/credit/mortgageCarInfo/form" ><span><b></b>新增</span></a></li>
		        	<li class="edit"><a id="edit" href="#" onclick="editData();" ><span><b></b>编辑</span></a></li>
		        	<li class="delete"><a id="delete" href="${ctx}/credit/mortgageCarInfo/delete" onclick="deleteData();"><span><b></b>删除</span></a></li>
		        </ul>
			</div>
		<sys:message content="${message}"/>
			<h3 onclick="company()" class="tableTitle">借款人企业关联企业信息</h3>
			<div>
				<table id="companyId" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
						<th style="width:50px"></th>
							<th>工商登记名称</th>
							<th>组织机构代码</th>
							<th>与借款人企业关系</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="companyInfo">
						<tr>
							<td>
							${companyInfo.index+1 }
							</td>
							<td id="mobileNum" class="title" title="${companyInfo.busiRegName}">
								${companyInfo.busiRegName}
							</td>
							<td id="mobileNum" class="title" title="${companyInfo.organizationNo}">
								${companyInfo.organizationNo}
							</td>
							<td>
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