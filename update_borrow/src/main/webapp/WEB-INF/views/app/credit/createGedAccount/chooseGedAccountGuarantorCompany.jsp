<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>选择担保人</title>
<meta name="decorator" content="default" />

<script type="text/javascript">
//关联担保企业信息
	function addCompanyGuInfo() {
	//var companyId = '${companyId}';
	 	var $checkLine = $("input[name='guarantorIds']:checked");
	 	var $len = $checkLine.length;
	 	if ($len < 1) {
	  		alertx("请选择要关联的担保企业信息");
	 	} else {
	  		var checkedValue = getCheckedValue("guarantorIds");
	  		var applyNo = '${applyNo}';
	  		var url = "${ctx}/credit/guaranteeRelation/saveRelationCompany?ids=" + checkedValue + "&&applyNo=" + '${applyNo}'+"&&companyId=" + '${companyId}';
	  		confirmx('确认要关联勾选的担保企业吗？', function() {
	   			saveJson(url, null, function(data) {
	    			if (data) {
	     				if (data.status == 1) {
	      					alertx(data.message, function() {
	       						parent.$.loadDiv("guarantorCompanyIndex", "${ctx}/credit/createGedAccount/guarantorCompany", {
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
		<div id="tableDataId" style="max-height: 400px; overflow: auto;">
			<div class="searchInfo">
				<h3 onclick="guaranB()" class="searchTitle">担保企业信息 列表</h3>
				<div id="guaranBId" class="ribbon filter">
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="3%">
									<input type="checkbox" id="checkAllGuarantor" name="checkAllGuarantor" onclick="allCheck('checkAllGuarantor','guarantorIds');" />
								</th>
								<th width="3%">序号</th>
								<th width="10%">企业名称</th>
								<th width="10%">统一社会信用代码</th>
								<th width="10%">法人代表手机号</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${companyInfoList}" var="companyInfo"
								varStatus="companyInfoList">
								<tr>
									<td>
										<input type="checkbox" name="guarantorIds" id="guarantorIds" value="${companyInfo.id }" />
									</td>
									<td>${companyInfoList.index+1 }</td>
									<td>${companyInfo.busiRegName}</td>
									<td>${companyInfo.unSocCreditNo }</td>
									<td>${companyInfo.corporationMobile }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="searchButton" id="buttonDives">
		<input id="asdf" class="btn btn-primary" onclick="addCompanyGuInfo();" type="button"  value="保存"/>
	</div>
</body>
</html>
