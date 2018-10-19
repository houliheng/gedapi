<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>担保企业</title>
</head>
<script type="text/javascript">
		function createGedAccountCompany(unSocCreditNo,corporationMobile,custId,busiRegName){
			var applyNo = $("#applyNo").val();
			var width = $(window).width() - 300;
			var url = "${ctx}/credit/createGedAccount/showCreateAccount?unSocCreditNo=" + unSocCreditNo +"&&applyNo="+applyNo +"&&corporationMobile="+corporationMobile+"&&flag=2"+"&&busiRegName="+busiRegName+"&&custId="+custId;
			openJBox("main-list", url, "创建冠易贷账号", width, 250, null);
		}
	
	function chooseCompanyGuarantorInfo() {
		var companyId = '${companyId}';
		if(companyId==null||companyId==''){
			alertx("请先选择批量企业");
		}else{
			var url = "${ctx}/credit/createGedAccount/guaranteeCompanyList";
			var width = $(window).width() - 100;
			//var url="${ctx }/credit/guarantorInfo/list";
			openJBox("guarantorInfo-form", url, "担保企业", width, 600, {
				applyNo : $("#applyNo").val(),companyId:'${companyId}'
			});
		}
	}
</script>
<body>
	<div class="tableList">
		<div id="tableDataId" style="max-height: 400px; overflow: auto;">
			<div class="searchInfo">
				<h3 class="searchTitle">${companyName }担保企业列表</h3>
				<div  class="ribbon filter">
					<!-- <ul class="layout">
						<li class="add">
							<a id="add" href="javascript:void(0);" onclick="chooseCompanyGuarantorInfo();">
								<span>
									<b></b>
									担保企业选择
								</span>
							</a>
						</li>
					</ul> -->
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<!-- <th width="3%">
									<input type="checkbox" id="checkAllGuarantor" name="checkAllGuarantor" onclick="allCheck('checkAllGuarantor','guarantorIds');" />
								</th> -->
								<th width="3%">序号</th>
								<th width="10%">企业名称</th>
								<th width="10%">社会统一信用代码</th>
								<th width="15%">法人代表手机号</th>
								<th width="10%">冠易贷账号</th>
								<th width="10%">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${guaranteeRelationList1}" var="guaranteeRelation" varStatus="guaranteeRelationIndex">	
							<tr>
									<%-- <td>
										<input type="checkbox" name="guarantorIds" id="guarantorIds" value="${guarantorRelation.custInfo.id }" />
									</td> --%>
									<td>${guaranteeRelationIndex.index+1 }</td>
									<td>${guaranteeRelation.custName}</td>
									<td>${guaranteeRelation.idNum }</td>
									<td>${guaranteeRelation.mobileNum }</td>
									<td>${guaranteeRelation.gedAccount }</td>
									<td>
										<c:if test="${empty guaranteeRelation.gedAccount}">
											<a href="javascript:void(0)" onclick="createGedAccountCompany('${guaranteeRelation.idNum}','${guaranteeRelation.mobileNum}','${guaranteeRelation.custId}','${guaranteeRelation.custName}')">创建冠易贷账号</a>
										</c:if>
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
