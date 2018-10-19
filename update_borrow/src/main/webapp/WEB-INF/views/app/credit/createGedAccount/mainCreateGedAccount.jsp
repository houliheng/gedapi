<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>创建冠易贷账号企业列表</title>
</head>
<script type="text/javascript">
	function guaranA() {
		$("#guaranAId").toggle(600);
	}
	function createGedAccount(unSocCreditNo,corporationMobile,roleType,busiRegName,id){
		var applyNo = $("#applyNo").val();
		var width = $(window).width() - 300;
		var url = "${ctx}/credit/createGedAccount/showCreateAccount?unSocCreditNo=" + unSocCreditNo +"&&applyNo="+applyNo +"&&corporationMobile="+corporationMobile+"&&flag=1"+"&&roleType="+roleType+"&&busiRegName="+busiRegName+"&&id="+id;
		openJBox("main-list", url, "创建冠易贷账号", width, 250, null);
	}
	function setGuaranteorInfo(id,unSocCreditNo,corporationMobile){
		$.loadDiv("guarantorInfoIndex", "${ctx }/credit/createGedAccount/guarantorInfo", {applyNo : '${applyNo}',id:id}, "post");
		$.loadDiv("guarantorCompanyIndex", "${ctx}/credit/createGedAccount/guarantorCompany", {applyNo : '${applyNo}',id:id}, "post");
	}
	
</script>
<body>
	<div class="tableList">
		<div id="tableDataId" style="max-height: 400px; overflow: auto;">
			<div class="searchInfo">
				<h3 onclick="guaranA()" class="searchTitle">创建冠易贷账号企业列表</h3>
				<div id="guaranAId" class="ribbon filter">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<!-- <th width="3%">
									<input type="checkbox" id="checkAllGuarantor" name="checkAllGuarantor" onclick="allCheck('checkAllGuarantor','guarantorIds');" />
								</th> -->
								<th width="3%">序号</th>
								<th width="10%">企业名称</th>
								<th width="5%">借款企业类型</th>
								<th width="15%">社会统一信用代码</th>
								<th width="10%">法人代表手机号</th>
								<th width="10%">冠易贷账号</th>
								<th width="15%">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${companyInfoList}" var="companyInfo" varStatus="companyInfoList">	
							<tr>
									<%-- <td>
										<input type="checkbox" name="guarantorIds" id="guarantorIds" value="${guarantorRelation.custInfo.id }" />
									</td> --%>
									<td>${companyInfoList.index+1 }</td>
									<td>${companyInfo.busiRegName}</td>
									<td>${fns:getDictLabel(companyInfo.roleType, 'ROLE_TYPE', '')}</td>
									<td>${companyInfo.unSocCreditNo }</td>
									<td>${companyInfo.corporationMobile }</td>
									<td>${companyInfo.gedAccount }</td>
									<%-- <td>${fns:getDictLabel(guarantorRelation.relationForApply, 'CONTACT_RELATIONS', '')}</td> --%>
									<td>

										 <c:if test="${empty companyInfo.gedAccount}">
											<a href="javascript:void(0)" onclick="createGedAccount('${companyInfo.unSocCreditNo}','${companyInfo.corporationMobile}','${companyInfo.roleType}','${companyInfo.busiRegName}','${companyInfo.id}')">创建冠易贷账号</a>
										 </c:if>
										<a href="javascript:void(0)" onclick="setGuaranteorInfo('${companyInfo.id}','${companyInfo.unSocCreditNo}','${companyInfo.corporationMobile}')">创建担保账号</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
