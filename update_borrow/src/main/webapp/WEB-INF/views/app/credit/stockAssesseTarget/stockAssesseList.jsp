<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
//新增
function addStockAssesse(url, title) {
	var width = $(top.document).width() - 500;
	width = Math.max(width, 1000);
	var height = $(top.document).height() - 150;
	/* height = Math.max(height,1000);  */
	url = url + "&grade="+$("#grade").val();
	openJBox('', url, title, width, height);
}

//修改
function editStockAssesse(urlSingle, title) {
	var width = $(top.document).width() - 300;
	width = Math.max(width, 1000);
	var $checkLine = $("input[name='assetsType']:checked");
	var $len = $checkLine.length;
	if ($len != 1) {
		alertx("请选择一条数据");
	} else {
		var url = urlSingle + "?id=" + $checkLine.val()+"&taskDefKey=${taskDefKey}"+"&grade="+$("#grade").val();
		openJBox('', url, title, width, $(top.document).height() - 200);
	}
}
//删除
function delStockAssesse(url, divName, divUrl) {
	var $checkLine = $("input[name='assetsType']:checked");
	if (0 == $checkLine.length) {
		alertx("请选择需要删除的数据！");
	} else {
		confirmx("是否删除?", function() {
			delOper(url, divName, divUrl);
		});
	}
}

function delOper(url, divName, divUrl) {
	var $checkLine = $("input[name='assetsType']:checked");
	if (null != $checkLine && $checkLine.length > 0) {
		var ids = "";
		$checkLine.each(function(v) {
			ids += (this.value + ",");
		});
		$.post(url, {
			"ids" : ids
		}, function(data) {
			if ("success" == data) {
				alertx("删除成功！");
				 $.loadDiv("targetListDiv", "${ctx }/credit/stockAssesseTarget/list", {
					applyNo : "${applyNo}",
					taskDefKey:$("#taskDefKey").val(),
					grade:$("#grade").val()
				}, "post"); 
			}
		});
	}
}

//查看详情
function details(url, message) {
	openJBox('', url, message, 1000, 500);
}
</script>
<title>股权尽调</title>
</head>
<body>
	<sys:message content="${message}" />
	<div class="tableList">
	<h3 class="tableTitle">年度考核时点及指标</h3>
		<div class="ribbon" id="buttonDiv">
			<ul class="layout">
				<li class="add">
					<a href="#" onclick="addStockAssesse('${ctx}/credit/stockAssesseTarget/form?applyNo=${stockAssesseTarget.applyNo}&taskDefKey=${taskDefKey}','年度考核时点及指标');">
						<span>
							<b></b>
							新增
						</span>
					</a>
				</li>
				<li class="edit">
					<a id="edit" href="javascript:void(0)" onclick="editStockAssesse('${ctx}/credit/stockAssesseTarget/form','编辑年度考核点及指标');">
						<span>
							<b></b>
							编辑
						</span>
					</a>
				</li>
				<li class="delete">
					<a id="delete" href="#" onclick="delStockAssesse('${ctx}/credit/stockAssesseTarget/batchDelete','${ctx }/credit/stockAssesseTarget/list')">
						<span>
							<b></b>
							删除
						</span>
					</a>
				</li>
			</ul>
		</div>
		<div id="tableDataId">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th width="20px;">
							<input type="checkbox" onclick="allCheck('assesse','assetsType');" name="assesse" id="assesse">
						</th>
						<th width="20px">序号</th>
						<th>考核时间</th>
						<th>考核项目</th>
						<th>考核内容(元)</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<input type="hidden" value="${taskDefKey}" id="taskDefKey">
					<input type="hidden" value="${grade}" id="grade">
					<c:forEach items="${stockList}" var="stock" varStatus="i">
						<tr>
							<td width="20px">
								<input type="checkbox" value="${stock.id}" name="assetsType">
							</td>
							<td id="num" class="title" title="序号">${i.index+1}</td>
							<td id="assesseDate" class="title" title="<fmt:formatDate value='${stock.assesseTime}' pattern='yyyy-MM-dd' />"><fmt:formatDate value='${stock.assesseTime}' pattern='yyyy-MM-dd' /></td>
							<td id="assesseProject" class="title" title="${stock.assesseProject}">${stock.assesseProject}</td>
							<td id="assesseContent" class="title" title="${stock.assesseContent}">${stock.assesseContent}</td>
							<td>
								<a href="javascript:void(0);" onclick="addStockAssesse('${ctx}/credit/stockAssesseTarget/form?readOnly=true&id=${stock.id}','查看年度考核时点及指标');">详情</a>
							</td>
						</tr>   
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>