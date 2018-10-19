<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>股权加减分项管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	//显示或隐藏加分项
	function addDivClick() {
		$("#addDiv").toggle(600);
	}

	//显示或隐藏减分项
	function minusDivClick() {
		$("#minusDiv").toggle(600);
	}

	function ajaxSubmit(ids,markType){
		$.ajax({
			url : "${ctx}/credit/creStockMarkNorm/saveMarkApplyRelationAjax",
			type : "POST",
			data : {
				applyNo : '${actTaskParam.applyNo}',
				normIds : ids,
				markType : markType
			},
			async : false,
			dataType : "json",
			success : function(data) {
				if(data.status == '1'){
					alert("保存成功！"); 
				}else{
					alert(data.message);
				}
			}
		});
	}	

	//保丰加分项
	function saveAdd() {
		var addIds = getCheckedIds('addType') + '';
		ajaxSubmit(addIds,'1');
	}

	//保丰加分项
	function saveMinus() {
		var minusIds = getCheckedIds('minusType') + '';
		ajaxSubmit(minusIds,'2');
	}
</script>
</head>
<body>
	<h3 onclick="addDivClick()" class="searchTitle">加分项</h3>
	<div class="searchCon">
		<div id="addDivId" class="ribbon filter">
			<div id="addDiv" style="max-height: 300px; overflow: auto;">
				<table id="addTable"
					class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="5%"><input type="checkbox"
								onclick="allCheck('addAll','addType');" name="addAll"
								id="addAll"></th>
							<th>序号</th>
							<th>分数</th>
							<th>描述</th>
						</tr>
					</thead>
					<c:forEach items="${addList}" var="addMarkNorm" varStatus="i">
						<tr>
							<td><input type="checkbox" 
							<c:if test="${addMarkNorm.isChecked == '1'}">
							checked="checked"</c:if> 
							value="${addMarkNorm.id}"
								name="addType"></td>
							<td class="title" title="${i.index+1}">${i.index+1}</td>
							<td class="title" title="${addMarkNorm.score}">
								${addMarkNorm.score}</td>
							<td id="contactName" class="title"
								title="${addMarkNorm.description}">
								${addMarkNorm.description}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<c:if test="${readOnly ne 'true'}">
				<input class="btn btn-primary noprint" type="button" onclick="saveAdd()" value="保存" />
			</c:if>
		</div>
	</div>

	<h3 onclick="minusDivClick()" class="searchTitle">减分项</h3>
	<div class="searchCon">
		<div id="minusDivId" class="ribbon filter">
			<div id="minusDiv" style="max-height: 300px; overflow: auto;">
				<table id="minusTable"
					class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="5%"><input type="checkbox"
								onclick="allCheck('minusAll','minusType');" name="minusAll"
								id="minusAll"></th>
							<th>序号</th>
							<th>分数</th>
							<th>描述</th>
						</tr>
					</thead>
					<c:forEach items="${minusList}" var="minusMarkNorm" varStatus="i">
						<tr>
							<td><input type="checkbox" value="${minusMarkNorm.id}"
								<c:if test="${minusMarkNorm.isChecked == '1'}">
								checked="checked"</c:if> 
								name="minusType"></td>
							<td class="title" title="${i.index+1}">${i.index+1}</td>
							<td class="title" title="${minusMarkNorm.score}">
								${minusMarkNorm.score}</td>
							<td id="contactName" class="title"
								title="${minusMarkNorm.description}">
								${minusMarkNorm.description}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<c:if test="${readOnly ne 'true'}">
				<input class="btn btn-primary noprint" type="button" onclick="saveMinus()" value="保存" />
			</c:if>
		</div>
	</div>
</body>
</html>