<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>担保人列表</title>
</head>
<script type="text/javascript">
	
	function createGedAccountInfo(unSocCreditNo,corporationMobile,custId){
		var applyNo = $("#applyNo").val();
		var url = "${ctx}/credit/createGedAccount/createAccount?unSocCreditNo=" + unSocCreditNo +"&&applyNo="+applyNo +"&&corporationMobile="+corporationMobile+"&&flag=2"+"&&custId="+custId;
		confirmx('确认要创建冠易贷账号吗？', function() {
			saveJson(url, null, function(data) {
				if (data) {
					if (data.status == 1) {
						alertx(data.message, function() {
							$.loadDiv("guarantorInfoIndex", "${ctx }/credit/createGedAccount/guarantorInfo", {applyNo : '${applyNo}',id:'${companyId}'}, "post");
						});
					} else {
						alertx(data.message);
					}
				}
			});
		});
	}
	
	function chooseGuarantorInfo() {
		var companyId = '${companyId}';
		if(companyId==null||companyId==''){
			alertx("请先选择需要查看的企业！");
		}else{
			var url = "${ctx}/credit/createGedAccount/guaranteeInfoList";
			var width = $(window).width() - 100;
			//var url="${ctx }/credit/guarantorInfo/list";
			openJBox("guarantorInfo-form", url, "担保人", width, 600, {
				applyNo : $("#applyNo").val(),companyId:'${companyId}'
			});
		}
	}
</script>
<body>
	<div class="tableList">
		<div id="tableDataId" style="max-height: 400px; overflow: auto;">
			<div class="searchInfo">
				<h3 class="searchTitle">${companyName }担保人列表</h3>
				<div  class="ribbon filter">
					<!-- <ul class="layout">
						<li class="add">
							<a id="add" href="javascript:void(0);" onclick="chooseGuarantorInfo();">
								<span>
									<b></b>
									担保人选择
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
								<th width="10%">担保人姓名</th>
								<th width="10%">身份证号</th>
								<th width="15%">手机号</th>
								<th width="10%">冠易贷账号</th>
								<th width="10%">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${guaranteeRelationList}" var="guaranteeRelation" varStatus="guaranteeRelationIndex">	
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
											<a href="javascript:void(0)" onclick="createGedAccountInfo('${guaranteeRelation.idNum}','${guaranteeRelation.mobileNum}','${guaranteeRelation.custId}')">创建冠易贷账号</a>
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
