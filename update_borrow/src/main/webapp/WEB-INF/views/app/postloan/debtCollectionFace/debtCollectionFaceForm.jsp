<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>上门催收管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#inputForm").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});

	function loadCity(val) {
		//重置市、县下拉框
		if ("reg" == val) {
			$("#addressCity").empty();
			$("#addressCity").append("<option value=''>请选择</option>");
			var $addressCity = $("#s2id_addressCity>.select2-choice>.select2-chosen");
			$addressCity.html("请选择");
			$("#addressDistinct").empty();
			$("#addressDistinct").append("<option value=''>请选择</option>");
			var $addressDistinct = $("#s2id_addressDistinct>.select2-choice>.select2-chosen");
			$addressDistinct.html("请选择");

			$.post("${ctx}/sys/area/treeNode", {
				parentId : $("#addressPro").val()
			}, function(data) {
				$.each(data, function(i, val) {
					$("#addressCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
				});
			});
		} else {
			$("#addressCity").empty();
			$("#addressCity").append("<option value=''>请选择</option>");
			var $addressCity = $("#s2id_addressCity>.select2-choice>.select2-chosen");
			$addressCity.html("请选择");
			$("#addressDistinct").empty();
			$("#addressDistinct").append("<option value=''>请选择</option>");
			var $addressDistinct = $("#s2id_addressDistinct>.select2-choice>.select2-chosen");
			$addressDistinct.html("请选择");

			$.post("${ctx}/sys/area/treeNode", {
				parentId : $("#addressPro").val()
			}, function(data) {
				$.each(data, function(i, val) {
					$("#addressCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
				});
			});
		}
	}

	function saveForm() {
		loading();
		top.$.jBox.tip.mess = null;
		var formJson = $("#inputForm").serializeJson();
		$.post("${ctx}/postloan/debtCollectionFace/save", formJson, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {
					alertx(data.message, function() {
						parent.$.loadDiv("debtCollectionFaceDiv", "${ctx }/postloan/debtCollectionFace/list", {
							debtId : data.debtId
						}, "post");
						closeJBox();
					});
				} else {
					alertx(data.message);
				}
			}
		});
	}

	function loadDistinct(val) {
		if ("reg" == val) {
			$("#addressDistinct").empty();
			$("#addressDistinct").append("<option value=''>请选择</option>");
			var $addressDistinct = $("#s2id_addressDistinct>.select2-choice>.select2-chosen");
			$addressDistinct.html("请选择");

			$.post("${ctx}/sys/area/treeNode", {
				parentId : $("#addressCity").val()
			}, function(data) {
				$.each(data, function(i, val) {
					$("#addressDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
				});
			});
		} else {
			$("#addressDistinct").empty();
			$("#addressDistinct").append("<option value=''>请选择</option>");
			var $addressDistinct = $("#s2id_addressDistinct>.select2-choice>.select2-chosen");
			$addressDistinct.html("请选择");
			$.post("${ctx}/sys/area/treeNode", {
				parentId : $("#addressCity").val()
			}, function(data) {
				$.each(data, function(i, val) {
					$("#addressDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
				});
			});
		}
	}
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="debtCollectionFace" action="#" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="contractNo" />
		<form:hidden path="debtId" />
		<sys:message content="${message}" />
		<table class="fromTable filter">
			<tr>
				<td class="ft_label">上门时间：</td>
				<td class="ft_content">
					<input id="collectionDate" name="collectionDate" type="text" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${debtCollectionFace.collectionDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',maxDate:new Date(),isShowClear:false});" />
				</td>
				<td class="ft_label">上门人：</td>
				<td class="ft_content">
					<form:input path="collector" htmlEscape="false" maxlength="32" class="input-medium " />
				</td>
			</tr>
			<tr>
				<td class="ft_label">上门地址：</td>
				<td class="ft_content" colspan="5">
					<form:select path="addressPro" class="input-small required" onchange="loadCity('reg')">
						<option value="">请选择</option>
						<form:options items="${debtProvinceMap}" htmlEscape="false" />
					</form:select>
					&nbsp;省
					<form:select path="addressCity" id="addressCity" class="input-small required" onchange="loadDistinct('reg')">
						<option value="">请选择</option>
						<form:options items="${debtCityMap}" htmlEscape="false" />
					</form:select>
					&nbsp;市
					<form:select path="addressDistinct" id="addressDistinct" class="input-small required">
						<option value="">请选择</option>
						<form:options items="${debtDistinctMap}" htmlEscape="false" />
					</form:select>
					&nbsp;区&nbsp;
					<font color="red">*</font>
					<span style="width: 15px; display: inline-block;"></span>
				</td>
			</tr>
			<tr>
				<td class="ft_label">地址明细：</td>
				<td class="ft_content" colspan="5">
					<form:input type="text" path="addressDetail" class="input-medium required" maxlength="200" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 285px" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">催收对象类型：</td>
				<td class="ft_content">
					<form:select path="custType" class="input-medium ">
						<form:option value="" label="请选择" />
						<form:options items="${fns:getDictList('ROLE_TYPE_P')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<td class="ft_label">催收对象名称：</td>
				<td class="ft_content">
					<form:input path="custName" htmlEscape="false" maxlength="30" class="input-medium " />
				</td>
			</tr>
			<tr>
				<td class="ft_label">催收详情：</td>
				<td class="ft_content" colspan="5">
					<form:textarea path="description" rows="5" class="textarea-width textarea-style required" maxlength="1000" htmlEscape="false" />
					<font class="font" color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">客户回款描述：</td>
				<td class="ft_content" colspan="5">
					<form:textarea path="custResult" rows="5" class="textarea-width textarea-style required" maxlength="1000" htmlEscape="false" />
					<font class="font" color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="searchButton" colspan="6" style="text-align: right;">
					<input id="btnSubmit" type="submit" class="btn btn-primary" value="保 存" />
					&nbsp;
					<input id="btnCancel" class="btn btn-primary " type="button" value="返回" onclick="closeJBox()" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>