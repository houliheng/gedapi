<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="acc" tagdir="/WEB-INF/tags/accounting" %>
<html>
<head>
<title>办结流程监控</title>
<meta name="decorator" content="default"/>
<script type="text/javascript">
	function page(n,s){
	
	
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		if($("#pageNo")[0].value.length>10){
			top.$.jBox.tip('当前页码大小长度不能大于10位！');
			return true;
		}
		if($("#pageSize")[0].value.length>10){
			top.$.jBox.tip('每页条数大小的长度不能大于10位！');
			return true
		}
		$("#searchForm").submit();
	    return false;
	}
	
	//单选框
	function selectSingle(){
		$("[name=pcheck]:checkbox").each(function(){
			$(this).click(function(){
				if($(this).attr("checked")){
					$("[name=pcheck]:checkbox").removeAttr("checked");
					$(this).attr("checked","checked");
				}
			});
		});
	}
	//重置
	function fromReset() {
		$("#proDefKey").val('');
		$("#custName").val('');
		$("#officeId").val("");
		$("#officeLevel").val("");
		$("#officeName").val("");
		$("#createStartTime").val('');
		$("#createEndTime").val('');
	}
	//查询（时间条件）
	function timeJudge() {
		$("#searchForm").submit();
	}
	function searchForm() {
		$("#searchForm").submit();
	}
	//获取checkBox的值
	function getCheckBoxVal() {
		var str = "";
		$("input[name=pcheck]:checkbox").each(function() {
			if ($(this).attr("checked")) {
				str += $(this).val() + ",";
			}
		});
		return str;
	}
	//获取procInstId
	function getProcHiddenVal() {
		var str = "";
		$("input[name=pcheck]:checkbox").each(function() {
			if ($(this).attr("checked")) {
				str = $(this).val();
			}
		});
		return str;
	}
	//流程轨迹
	function processTrack() {
		var str = getCheckBoxVal();
		if (str != "") {
			if (str.indexOf(",") != str.length - 1) {
				alertx("请不要选择多条对应的流程信息");
			} else {
				var proId = getProcHiddenVal();
				var url = "${ctx}/credit/taskCenter/processTrack?procInstId=" + proId;
				window.showModalDialog(url, null, "dialogWidth:1000px;dialogHeight:600px;status:no;help:no;resizable:yes;");
			}
		} else {
			alertx("请选择对应的流程信息");
		}
	}
	//流程图
	function tracePhoto() {
		var str = getCheckBoxVal();
		if (str != "") {
			if (str.indexOf(",") != str.length - 1) {
				alertx("请不要选择多条对应的流程信息");
			} else {
				var proId = getProcHiddenVal();
				var url = "${ctx}/credit/taskCenter/trace/photo/customer?procInstId=" + proId;
				var windowWidth = window.parent.window.document.body.offsetWidth - 50;
				var windowHeight = window.parent.window.document.body.offsetHeight - 50;
				window.showModalDialog(url, null, "dialogWidth:" + windowWidth + "px" + ";dialogHeight:" + windowHeight + "px" + ";status:no;help:no;resizable:yes;");
			}
		} else {
			alertx("请选择对应的流程信息");
		}
	}
	//页面数据列表高度设置
	$(document).ready(function() {
		//var windowHeight = window.parent.window.document.body.offsetHeight;
		//var x = (windowHeight - 504) + "px";
		//document.getElementById("tableDataId").style.height = x;

		var tds = $(".title");
		$.each(tds, function() {
			$(this).attr("title", $.trim($(this).text()));
		});
	});

	//详情功能
	function showInfoBtn() {
		var str = getCheckBoxVal();
		if (str != "") {
			if (str.indexOf(",") != str.length - 1) {
				alertx("请不要选择多条对应的流程信息");
			} else {
				$("input[name=pcheck]:checkbox").each(function() {
					if ($(this).attr("checked")) {
						var checkboxId = $(this).attr("id");
						$("#" + checkboxId + "_alink").click();
					}
				});
			}
		} else {
			alertx("请选择对应的流程信息");
		}
	}
	function showInfo(procInstId, applyNo, title) {
		var url = "${ctx}/credit/taskCenter/infoView?status=1&procInstId=" + procInstId + "&applyNo=" + applyNo + "&title=" + encodeX("详情查看---" + title);
		var windowWidth = window.parent.window.document.body.offsetWidth - 50;
		var windowHeight = window.parent.window.document.body.offsetHeight - 50;
		window.showModalDialog(url, null, "dialogWidth:" + windowWidth + "px" + ";dialogHeight:" + windowHeight + "px" + ";status:no;help:no;resizable:yes;");
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="taskVO" action="${ctx}/credit/taskCenter/todoneSupervisor" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="taskDefKey" name="taskDefKey" type="hidden" value="${taskDefKey}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">流程名称：</td>
								<td class="ft_content">
									<form:select path="proDefKey" class="input-medium">
										<form:option value="" label="" />
										<form:options items="${processList}" itemLabel="name" itemValue="id" htmlEscape="false" />
									</form:select>
								</td>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<form:input path="custName" maxlength="50" class="input-medium" />
								</td>
							</tr>
							<tr>
								<td class="ft_label">数据范围：</td>
								<td class="ft_content">
									<acc:officeselect url="/sys/customOffice/treeData" id="office" name="office.id" value="${taskVO.office.id}" labelName="office.name" labelValue="${taskVO.office.name}" orgLevel="office.levelnumString" title="机构" allowClear="true" cssClass="input-medium"></acc:officeselect>
								</td>
								<td class="ft_label">完成时间：</td>
								<td class="ft_content" style="width: 240px;">
									<input id="createStartTime" name="createStartTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${taskVO.createStartTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
									<label>&nbsp;--&nbsp;</label>
									<input id="createEndTime" name="createEndTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${taskVO.createEndTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="timeJudge();" />
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return fromReset();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<div class="ribbon">
			<ul class="layout">
				<li class="mcp_pic"><a href="#" id="processPicture" onclick="tracePhoto()"><span><b></b>流程图</span></a></li>
				<li class="mcp_change"><a href="#" id="processTrack" onclick="processTrack()"><span><b></b>流程轨迹</span></a></li>
				<li class="mcp_info"><a href="#" id="processInfoView" onclick="showInfoBtn()"><span><b></b>详情</span></a></li>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th  width="1%" ></th>
						<th width="10%">流程名称</th>
						<th width="10%">客户名称</th>
						<th width="10%">登记门店</th>
						<th width="10%">区域</th>
						<th width="13%">大区</th>
						<th width="11%">完成时间</th>
					</tr>
					
					<c:forEach items="${page.list}" var="process" varStatus="index">
					<tr>
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td width="4px">
						<input type="checkbox"  value="${process.PROC_INS_ID}" name="pcheck"  onclick="selectSingle()" id="c_${index.count}">
						</td>
						<td class="title" title="${process.PROCESSNAME}">${process.PROCESSNAME}</td>
						<td class="title" title="${process.CUST_NAME}">${process.CUST_NAME}</td>
						<td class="title" title="${process.REGIST_ORG_NAME}">${process.REGIST_ORG_NAME}</td>
						<td class="title" title="${process.AREA_NAME}">${process.AREA_NAME}</td>
						<td class="title" title="${process.LARGE_AREA_NAME}">${process.LARGE_AREA_NAME}</td>
						<td class="title" title="">
							<fmt:formatDate value="${process.CREATETIME}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>
