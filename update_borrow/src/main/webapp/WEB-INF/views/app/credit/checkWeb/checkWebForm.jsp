<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>网查管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		adjustTextareaLength("notice", "pre");
		adjustTextareaLength("zhixingCourtGovDesc", "preZhixingCourtGovDesc");
		adjustTextareaLength("baiduDesc", "preBaiduDesc");
		adjustTextareaLength("nacaoOrgDesc", "preNacaoOrgDesc");
		adjustTextareaLength("judgementsOnlineDesc", "preJudgementsOnlineDesc");
		adjustTextareaLength("cfdaDesc", "preCfdaDesc");
		adjustTextareaLength("zhongdengwangOrgDesc", "preZhongdengwangOrgDesc");
		adjustTextareaLength("gacprcDesc", "preGacprcDesc");
		adjustTextareaLength("gsxtSaicGovDesc", "preGsxtSaicGovDesc");
		adjustTextareaLength("nationalPromiseToBeExecutedDesc", "preNationalPromiseToBeExecutedDesc");
		adjustTextareaLength("bjygscxDesc", "preBjygscxDesc");

		$("#inputForm").validate({
		submitHandler : function(form) {
			$("#roleType").removeAttr("disabled");
			$("#idType").removeAttr("disabled");
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {

			$("#messageBox").text("输入有误，请先更正。");
			var str2 = element[0].outerHTML;
			if (element.is(":radio")) {
				error.appendTo(element.parent().parent());
			} else {
				var str = element[0].outerHTML.substring(0, 2);
				var div = $("#s2id_" + element.attr("id"));
				var a = $(div).children("a.select2-choice");
				if (str == '<i') {
					element.get(0).style.backgroundColor = "pink";
					if (checkStr == true) {
						element.get(0).style.backgroundColor = "";
						checkStr = false;
					}
					element.live("input", function(event) {
						element.get(0).style.backgroundColor = "";
						// document.getElementById(IdCheck).style.backgroundColor = "";
					});
				}
				if (str == '<t') {
					element.get(0).style.backgroundColor = "pink";
					element.live("input", function(event) {
						element.get(0).style.backgroundColor = "";
					});
				}
				if (str == '<s') {
					a.get(0).style.background = "pink";
					// a.get(0).style.color = "#dbffff";
					a.get(0).style.backgroundColor = "pink";
					element.live("change", function(event) {
						event.stopPropagation();
						a.get(0).style.background = "";
					});
				}
			}

		}
		});
	});
</script>
<c:if test="${true == readOnly}">
	<script type="text/javascript">
		$(document).ready(function() {
			$(document).ready(function() {
				$("input[type!='button']").attr("readOnly", "readOnly");
				$("input[type='radio']").attr("disabled", "disabled");
				$("input[type='checkbox']").attr("disabled", "disabled");
				$("#description").attr("disabled", "disabled");
				disableSelect2();
				disabledTextarea();
				$("div[class='ribbon']").remove();
				$("div[class='searchButton']").remove();
				$("font[color='red']").remove();
				$(".Wdate").attr("onclick", "");
				$(".Wdate").removeClass("Wdate");
				$(".qdelete").remove();
				$("#btnSubmit").remove();
				$("#btnCancel").attr("value", "关闭");
			});
		});

		function disabledTextarea() {
			for (var i = 1; i < 10; i++) {
				$("#search" + i).attr("disabled", "true");
			}
		}
	</script>
</c:if>
</head>
<body>
	<form:form id="inputForm" modelAttribute="checkWeb" action="${ctx}/credit/checkWeb/save" method="post" class="form-horizontal">
		<form:hidden path="applyNo" value="${applyNo}" />
		<form:hidden path="custId" value="${custId}" />
		<sys:message content="${message}" />
		<table class="filter">
			<tr>
				<td class="ft_label">电核网查外访，注意事项：</td>
				<td class="ft_content" colspan="5">
					<pre class=" pre-style required" style="width: 700px" id="pre"></pre>
					<textarea rows="5" id="notice" cols="300" class=" textarea-style required" maxlength="1000" style="width: 700px" readonly="true">${notice}</textarea>
				</td>
			</tr>
			<tr>
				<td class="ft_label">核查对象：</td>
				<td class="ft_content">
					<form:select path="roleType" class="input-medium" disabled="true">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('ROLE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<td class="ft_label">名称：</td>
				<td class="ft_content">
					<form:input path="custName" htmlEscape="false" maxlength="30" class="input-medium" readonly="true" />
				</td>
				<td class="ft_label">移动电话：</td>
				<td class="ft_content">
					<form:input path="mobileNum" htmlEscape="false" maxlength="20" class="input-medium" readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">核查时间：</td>
				<td class="ft_content">
					<input id="checkDate" name="checkDate" type="text" readonly="true" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${checkWeb.checkDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',maxDate:new Date(),isShowClear:false});" />
					<font color="red">*</font>
				</td>
				<td class="ft_label">证件类型：</td>
				<td class="ft_content">
					<form:select path="idType" id="idType" class="input-medium" disabled="true">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('CUSTOMER_C_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<!-- 个人类型 -->
				<c:if test="${checkWeb.idType eq '1' || checkWeb.idType eq '8'}">
					<td class="ft_label">证件号码：</td>
					<td class="ft_content">
						<form:input path="idNum" htmlEscape="false" maxlength="20" class="input-medium" readonly="true" />
					</td>
				</c:if>
			</tr>
			<!-- 企业类型 -->
			<c:if test="${checkWeb.idType eq '2' || checkWeb.idType eq '3'}">
				<tr>
					<td class="ft_label">统一社会信用代码：</td>
					<td class="ft_content">
						<input type="text" id="unSocCreditNo" name="unSocCreditNo" value="${myCom.unSocCreditNo }" htmlEscape="false" class="input-medium" readonly="readonly" />
					</td>
					<td class="ft_label">组织机构代码：</td>
					<td class="ft_content">
						<input type="text" id="organizationNo" name="unSocCreditNo" value="${myCom.organizationNo }" htmlEscape="false" class="input-medium" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">营业执照注册号：</td>
					<td class="ft_content">
						<input type="text" id="busiLicRegNo" name="unSocCreditNo" value="${myCom.busiLicRegNo }" htmlEscape="false" class="input-medium" readonly="readonly" />
					</td>
					<td class="ft_label">登记税号：</td>
					<td class="ft_content">
						<input type="text" id="registerNationalTaxNo" name="unSocCreditNo" value="${myCom.registerNationalTaxNo }" htmlEscape="false" class="input-medium" readonly="readonly" />
					</td>
				</tr>
			</c:if>
			<tr>
				<td class="ft_label">
					<a href="http://zhixing.court.gov.cn/search/" target="_blank">全国法院被执行人信息查询：</a>
				</td>
				<td>
					<form:radiobuttons path="zhixingCourtGovFlg" items="${fns:getDictList('WEB_CHECK_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="5" style="width: 720px">
					<pre class="textarea-width pre-style" id="preZhixingCourtGovDesc"></pre>
					<form:textarea path="zhixingCourtGovDesc" htmlEscape="false" rows="4" class="textarea-width textarea-style required" maxlength="1000" style="margin-bottom:2px;margin-top:2px" onkeyup="adjustTextareaLength('zhixingCourtGovDesc','preZhixingCourtGovDesc')" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">
					<a href="http://www.baidu.com" target="_blank">百度查询：</a>
				</td>
				<td nowrap>
					<form:radiobuttons path="baiduFlg" items="${fns:getDictList('WEB_CHECK_STATUS_BD')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="5">
					<pre class="textarea-width pre-style" id="preBaiduDesc"></pre>
					<form:textarea path="baiduDesc" htmlEscape="false" rows="4" maxlength="1000" class="textarea-width textarea-style required" style="margin-bottom:2px;margin-top:2px" onkeyup="adjustTextareaLength('baiduDesc','preBaiduDesc')" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">
					<a href="http://shixin.court.gov.cn/" target="_blank">全国失信被执行人查询：</a>
				</td>
				<td>
					<form:radiobuttons path="nationalPromiseToBeExecutedFlag" items="${fns:getDictList('WEB_CHECK_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="5">
					<pre class="textarea-width pre-style" id="preNationalPromiseToBeExecutedDesc"></pre>
					<form:textarea path="nationalPromiseToBeExecutedDesc" htmlEscape="false" rows="4" maxlength="1000" class="textarea-width textarea-style required" style="margin-bottom:2px;margin-top:2px" onkeyup="adjustTextareaLength('nationalPromiseToBeExecutedDesc','preNationalPromiseToBeExecutedDesc')" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">
					<a href="http://www.court.gov.cn/zgcpwsw/" target="_blank">中国裁判文书网查询：</a>
				</td>
				<td>
					<form:radiobuttons path="judgementsOnlineFlg" items="${fns:getDictList('WEB_CHECK_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="5">
					<pre class="textarea-width pre-style" id="preJudgementsOnlineDesc"></pre>
					<form:textarea path="judgementsOnlineDesc" htmlEscape="false" rows="4" maxlength="1000" class="textarea-width textarea-style required" style="margin-bottom:2px;margin-top:2px" onkeyup="adjustTextareaLength('judgementsOnlineDesc','preJudgementsOnlineDesc')" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">
					<a href="http://www.sda.gov.cn/WS01/CL0001/" target="_blank">国家食品药品监督管理总局数据查询：</a>
				</td>
				<td>
					<form:radiobuttons path="cfdaFlg" items="${fns:getDictList('WEB_CHECK_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="5">
					<pre class="textarea-width pre-style" id="preCfdaDesc"></pre>
					<form:textarea path="cfdaDesc" class="textarea-width textarea-style " htmlEscape="false" rows="4" maxlength="1000" style="margin-bottom:2px;margin-top:2px" onkeyup="adjustTextareaLength('cfdaDesc','preCfdaDesc')" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">
					<a href="http://www.ylyszjcx.com/" target="_blank">全国道路运输证件查询系统查询： </a>
				</td>
				<td>
					<form:radiobuttons path="bjygscxFlg" items="${fns:getDictList('WEB_CHECK_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="5">
					<pre class="textarea-width pre-style" id="preBjygscxDesc"></pre>
					<form:textarea path="bjygscxDesc" class="textarea-width textarea-style " htmlEscape="false" rows="4" maxlength="1000" style="margin-bottom:2px;margin-top:2px" onkeyup="adjustTextareaLength('bjygscxDesc','preBjygscxDesc')" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">
					<a href="http://www.zhongdengwang.org.cn/zhongdeng/index.shtml" target="_blank">中登网查询：</a>
				</td>
				<td>
					<form:radiobuttons path="zhongdengwangOrgFlg" items="${fns:getDictList('WEB_CHECK_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</td>
			<tr>
			<tr>
				<td></td>
				<td colspan="5">
					<pre class="textarea-width pre-style" id="preZhongdengwangOrgDesc"></pre>
					<form:textarea path="zhongdengwangOrgDesc" class="textarea-width textarea-style " htmlEscape="false" rows="4" maxlength="1000" style="margin-bottom:2px;margin-top:2px" onkeyup="adjustTextareaLength('zhongdengwangOrgDesc','preZhongdengwangOrgDesc')" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">
					<a href="http://www.haiguan.info/" target="_blank">海关总署进出口报关证查询：</a>
				</td>
				<td>
					<form:radiobuttons path="gacprcFlg" items="${fns:getDictList('WEB_CHECK_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="5">
					<pre class="textarea-width pre-style" id="preGacprcDesc"></pre>
					<form:textarea path="gacprcDesc" class="textarea-width textarea-style " htmlEscape="false" rows="4" maxlength="1000" style="margin-bottom:2px;margin-top:2px" onkeyup="adjustTextareaLength('gacprcDesc','preGacprcDesc')" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">
					<a href="http://gsxt.saic.gov.cn/" target="_blank">全国企业信用信息公示系统查询： </a>
				</td>
				<td>
					<form:radiobuttons path="gsxtSaicGovFlg" items="${fns:getDictList('WEB_CHECK_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="5">
					<pre class="textarea-width pre-style" id="preGsxtSaicGovDesc"></pre>
					<form:textarea path="gsxtSaicGovDesc" class="textarea-width textarea-style" htmlEscape="false" rows="4" maxlength="1000" style="margin-bottom:2px;margin-top:2px" onkeyup="adjustTextareaLength('gsxtSaicGovDesc','preGsxtSaicGovDesc')" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">
					<a href="http://www.nacao.org.cn/publish/main/24/index.html" target="_blank">全国组织代码管理中心查询：</a>
				</td>
				<td>
					<form:radiobuttons path="nacaoOrgFlg" items="${fns:getDictList('WEB_CHECK_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="5">
					<pre class="textarea-width pre-style" id="preNacaoOrgDesc"></pre>
					<form:textarea path="nacaoOrgDesc" htmlEscape="false" rows="4" maxlength="1000" class="textarea-width textarea-style" style="margin-bottom:2px;margin-top:2px" onkeyup="adjustTextareaLength('nacaoOrgDesc','preNacaoOrgDesc')" />
				</td>
			</tr>
			<tr>
				<td class="searchButton" colspan="6" style="text-align: right">
					<shiro:hasPermission name="credit:checkWeb:edit">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeJBox()" />
				</td>
				<c:if test="${readOnly eq true }">
					<td>
						<input id="btnCancel" class="btn-primary btn " type="button" value="关闭" onclick="closeJBox()" />
					</td>
				</c:if>
			</tr>
		</table>
	</form:form>
	<c:if test="${closeWindow eq 'true'}">
		<script type="text/javascript">
			alertx('${saveMessage}', function() {
				parent.location.reload();
				closeJBox();
			});
		</script>
	</c:if>
	<c:if test="${closeWindow eq 'false'}">
		<script type="text/javascript">
			$("#roleType").addAttr("disabled");
			$("#idType").addAttr("disabled");
			alertx('${saveMessage}');
		</script>
	</c:if>
</body>
</html>