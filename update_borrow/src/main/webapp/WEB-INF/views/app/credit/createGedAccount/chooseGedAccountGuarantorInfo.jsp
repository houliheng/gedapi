<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>选择担保人</title>
<meta name="decorator" content="default" />

<script type="text/javascript">
//关联担保人信息
	function addGuInfo() {
	//var companyId = '${companyId}';
	 	var $checkLine = $("input[name='guarantorIds']:checked");
	 	var $len = $checkLine.length;
	 	if ($len < 1) {
	  		alertx("请选择要关联的担保人信息");
	 	} else {
	  		var checkedValue = getCheckedValue("guarantorIds");
	  		var applyNo = '${applyNo}';
	  		var url = "${ctx}/credit/guaranteeRelation/saveRelation?ids=" + checkedValue + "&&applyNo=" + '${applyNo}'+"&&companyId=" + '${companyId}';
	  		confirmx('确认要关联勾选的担保人吗？', function() {
	   			saveJson(url, null, function(data) {
	    			if (data) {
	     				if (data.status == 1) {
	      					alertx(data.message, function() {
	       						parent.$.loadDiv("guarantorInfoIndex", "${ctx }/credit/createGedAccount/guarantorInfo", {
	        						applyNo : '${applyNo}',id:'${companyId}'
	       						}, "post");
	       						closeJBox();
	      					});
	     				} else {
	      					alertx(data.message);
	     				}
	    			}
	   			});
	  		});
	 	} 
	}
</script>
</head>
<body>
	<div class="tableList">
		<sys:message content="${message}" />
		<div id="tableDataId" style="max-height: 400px; overflow: auto;">
			<div class="searchInfo">
				<h3 class="searchTitle">担保人信息列表</h3>
				<div id="guaranABId" class="ribbon filter">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="3%">
									<input type="checkbox" id="checkAllGuarantor" name="checkAllGuarantor" onclick="allCheck('checkAllGuarantor','guarantorIds');" />
								</th>
								<th width="3%">序号</th>
								<th width="10%">姓名</th>
								<th width="15%">身份证件号</th>
								<th width="15%">手机号</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${guarantorRelationList}" var="guarantorRelation" varStatus="guarantorRelationList">
								<tr>
									<td>
										<input type="checkbox" name="guarantorIds" id="guarantorIds" value="${guarantorRelation.custInfo.id }" />
									</td>
									<td>${guarantorRelationList.index+1 }</td>
									<td>${guarantorRelation.custInfo.custName}</td>
									<td>${guarantorRelation.custInfo.idNum}</td>
									<td>${guarantorRelation.custInfo.mobileNum}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					
				</div>
			</div>
		</div>
	</div>
	<div class="searchButton" id="buttonDives">
		<input id="asdf" class="btn btn-primary" onclick="addGuInfo();" type="button"  value="保存"/>
	</div>
</body>
</html>
