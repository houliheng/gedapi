<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>担保公司管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
		$(document).ready(function() {

        });
		function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
		
		//新增担保人信息
		/* function addGuaranteeCompany() {
			var width = $(document).width() - 200;
			var mobileGed = '';
			var url = "${ctx}/credit/creGuaranteeCompany/form?mobileGed="+mobileGed;
			openJBox("guarantorInfo-form", url, "新增担保企业信息", width, 500, {
				applyNo : $("#applyNo").val()
			});
		} */
		
		function add() {
			var width = $(document).width() - 200;
			//var mobileGed = '';
			var url = "${ctx}/credit/creGuaranteeCompany/form";
			openJBox("guarantorInfo-form", url, "新增担保公司信息", width, 500, {
				applyNo : $("#applyNo").val()
			});
		}
		//删除担保人信息
		function delGuaranteeCompany() {
			var $checkLine = $("input[name='guranteeCompanyIds']:checked");
			var $len = $checkLine.length;
			if ($len < 1) {
				alertx("请选择要删除的担保企业信息");
			} else {
				var checkedValue = getCheckedValue("guranteeCompanyIds");
				var url = "${ctx}/credit/creGuaranteeCompany/delete?ids=" + checkedValue;
				confirmx('确认要删除勾选的担保企业信息吗？', function() {
					saveJson(url, null, function(data) {
						if (data) {
							if (data.status == 1) {
								alertx(data.message, function() {
									document.location.reload();
								//	window.location.reload();
								});
							} else {
								alertx(data.message);
							}

						}
					});
				});
			}
		}

		//详情
		function detailGuarantee(id,mobileGed) {
			var width = $(document).width() - 200;
			var url = "${ctx}/credit/creGuaranteeCompany/form?id=" + id + "&&readOnly=0"+"&mobileGed="+mobileGed;
			openJBox("guaranteeInfo-detai", url, "担保公司信息详情", width, 600, null);
		}

		//编辑
		function editGuaranteeCompany(id,mobileGed) {
			var width = $(document).width() - 200;
			var url = "${ctx}/credit/creGuaranteeCompany/form?id=" + id + "&readOnly=2"+"&mobileGed="+mobileGed;
			openJBox("guaranteeCompany-detai", url, "编辑担保公司信息", width, 600, null);
		
		}
		//创建冠E贷账号
		 function createGuranteeComGedNum(id){
			var width = $(document).width() - 200;
			var url = "${ctx}/credit/creGuaranteeCompany/isRegisterGED?id=" + id;
			openJBox("creGuaranteeCompany", url, "创建冠E贷账号", width, 600, null);
		} 
		function set() {
			$("#guaranteeCompanyName").val('');
			$(".select2-chosen").html("请选择");
			$(".class1").attr("selected", 'selected');
			$("input.input-medium").val("");
		}
	</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="creGuaranteeCompany" action="${ctx}/credit/creGuaranteeCompany/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="filter">
						<ul class="ul-form">
							<li><label>公司名：</label>
								<form:input path="guaranteeCompanyName" htmlEscape="false" maxlength="32" class="input-medium"/>
							</li>
							<li><label>状态：</label>
								<form:select path="guaranteeState" class="input-medium">
										<form:option value="" class="class1" label="请选择" />
										<form:options items="${fns:getDictList('ACCOUNT_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
							</li>
						</ul>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查 询" />&nbsp; 
							<input id="btnReset"class="btn btn-primary" type="button" value="重 置" onclick="set()"/>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList" id="guranteeCompanyList">
			<h3 class="tableTitle">数据列表</h3>
			<div class="ribbon filter">
			<ul class="layout">
						<li class="add">
							<a id="add" href="javascript:void(0);" onclick="add();">
								<span>
									<b></b>
									新增
								</span>
							</a>
						</li>
						<!-- <li class="edit">
							<a id="edit" href="javascript:void(0);" onclick="editGuaranteeCompany();">
								<span>
									<b></b>
									编辑
								</span>
							</a>
						</li> -->
						<!-- <li class="delete">
							<a id="delete" href="javascript:void(0);" onclick="delGuaranteeCompany();">
								<span>
									<b></b>
									删除
								</span>
							</a>
						</li> -->
			</ul>
			</div>
			<div id="tableDataId">
			<table id="contentTable" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-condensed table-hover">
						<thead>
							<tr>
								<th width="3%">序号</th>
								<th width="10%">公司名称</th>
								<th width="10%">担保额度（元）</th>
								<th width="9%">已担保额度（元）</th>
								<th width="13%">可担保余额（元）</th>
								<th width="15%">已担保数量</th>
								<th width="10%">状态</th>
								<th width="13%">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list}" var="creGuaranteeCompany" varStatus="page">
								<tr>
									<%-- <td>
										<input type="checkbox" name="guranteeCompanyIds" id="guranteeCompanyIds" value="${creGuaranteeCompany.id }" />
									</td> --%>
									<td>${page.index+1 }</td>
									<td class="title" title="${creGuaranteeCompany.guaranteeCompanyName}">${creGuaranteeCompany.guaranteeCompanyName}</td>
									<td class="title" title="${creGuaranteeCompany.guaranteeLimit}">${creGuaranteeCompany.guaranteeLimit}</td>
									<td class="title" title="${creGuaranteeCompany.guaranteeAmount}">${creGuaranteeCompany.guaranteeAmount}</td>
									<td class="title" title="${creGuaranteeCompany.guaranty}">${creGuaranteeCompany.guaranty}</td>
									<td class="title" title="${creGuaranteeCompany.guranteeCount}">${creGuaranteeCompany.guranteeCount}</td>
									<td class="title" title="${fns:getDictLabel(creGuaranteeCompany.guaranteeState, 'ACCOUNT_STATUS', '')}">${fns:getDictLabel(creGuaranteeCompany.guaranteeState, 'ACCOUNT_STATUS', '')}</td>
									<td>
										<a href="javascript:void(0)" onclick="detailGuarantee('${creGuaranteeCompany.id}','${creGuaranteeCompany.mobileGed}');">详情</a>
										<a href="javascript:void(0)" onclick="editGuaranteeCompany('${creGuaranteeCompany.id}','${creGuaranteeCompany.mobileGed}');">修改</a>
										<c:if test="${creGuaranteeCompany.isGEDNum != '1' }">
											<a href="javascript:void(0)" onclick="createGuranteeComGedNum('${creGuaranteeCompany.id}');">创建冠E贷账户</a>
										</c:if>
										<c:if test="${creGuaranteeCompany.isGEDNum == '1' }">
											<a href="javascript:void(0)" onclick="createGuranteeComGedNum('${creGuaranteeCompany.id}');">查看冠E贷账户</a>
										</c:if>
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