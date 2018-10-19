<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>股权尽调</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#inputForm").validate({
		submitHandler : function(form) {
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
				error.appendTo(element.parent().parent());
			} else {
				checkReq(error, element);
			}
		}
		});
	});	
</script>
<script type="text/javascript">

</script>
<c:if test="${true == readOnly}">
	<script type="text/javascript">
		$(document).ready(function() { 
			$("input[type!='button']").attr("readOnly", "readOnly");
			$("input[type='radio']").attr("disabled", "disabled");
			$("input[type='checkbox']").attr("disabled", "disabled");
			$("input[type='text']").attr("disabled", "disabled");
			disableSelect2();
			$("div[class='ribbon']").remove();
			$("div[class='searchButton']").remove();
			$("font[color='red']").remove();
			$(".Wdate").attr("onclick", "");
			$(".Wdate").removeClass("Wdate");
			$(".qdelete").remove();
			$("#btnSubmit").remove();
			$("#btnClose").attr("value", "关闭");
			$("#validateMoney").keyup(function () {
	            var reg = $(this).val().match(/\d+\.?\d{0,2}/);
	            var txt = '';
	            if (reg != null) {
	                txt = reg[0];
	            }
	            $(this).val(txt);
	        }).change(function () {
	            $(this).keypress();
	            var v = $(this).val();
	            if (/\.$/.test(v))
	            {
	                $(this).val(v.substr(0, v.length - 1));
	            }
	        });
		});
		
		
	</script>
</c:if>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">年度考核时点及指标</h3>
		<div class="searchCon">
			<form:form id="inputForm" modelAttribute="stockAssesseTarget" action="${ctx}/credit/stockAssesseTarget/save" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<input type="hidden" name="taskDefKey" value="${taskDefKey}">
				<form:hidden path="applyNo" value="${applyNo}" />
				<form:hidden path="grade" value="${grade}" />
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">考核时间：</td>
						<td class="ft_content">	
						<c:if test="${true == readOnly}">
						<input name="assesseTime" style="background-color: #f4f4f4;" id="checkDate" type="text" maxlength="300" class="required input-medium Wdate" value="<fmt:formatDate value="${stockAssesseTarget.assesseTime}" pattern="yyyy-MM-dd" />"  onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd'})" />
							</c:if>
							<c:if test="${true != readOnly}">
						<input name="assesseTime"  id="checkDate" type="text" maxlength="300" class="required input-medium Wdate" value="<fmt:formatDate value="${stockAssesseTarget.assesseTime}" pattern="yyyy-MM-dd" />"  onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd'})" />
							</c:if>
							<font color="red">*</font>
						</td>
						<td class="ft_label">考核项目：</td>
						<td class="ft_content">
						
						<c:if test="${true == readOnly}">
						
						<select name="assesseProject" id="assesseProject"  disabled = "disabled" class="input-medium required">
						</c:if>
						<c:if test="${true != readOnly}">
						<select name="assesseProject" id="assesseProject"   class="input-medium required">
						</c:if>
   						 	<option value="">请选择</option>
   						 	<c:if test="${stockAssesseTarget.assesseProject == '营业额'}">
   						 		<option value="营业额" selected = "selected" <c:if test="${true == readOnly}">style="background-color:#ccc;"</c:if>>营业额</option>
   						 	</c:if>
							<c:if test="${stockAssesseTarget.assesseProject == '年度分红'}">
   						 		<option value="年度分红" selected = "selected" <c:if test="${true == readOnly}">style="background-color:#ccc;"</c:if>>年度分红</option>
   						 	</c:if>
   						 	<c:if test="${stockAssesseTarget.assesseProject == '年利润'}">
   						 		<option value="年利润" selected = "selected" <c:if test="${true == readOnly}">style="background-color:#ccc;"</c:if>>年利润</option>
   						 	</c:if>
   						 	<c:if test="${stockAssesseTarget.assesseProject != '营业额'}">
   						 		<option value="营业额" >营业额</option>
   						 	</c:if>
							<c:if test="${stockAssesseTarget.assesseProject != '年度分红'}">
   						 		<option value="年度分红" >年度分红</option>
   						 	</c:if>
   						 	<c:if test="${stockAssesseTarget.assesseProject != '年利润'}">
   						 		<option value="年利润" >年利润</option>
   						 	</c:if>
    					</select>
							<font color="red">*</font>

						</td>
						<td class="ft_label">考核内容(元)：</td>
						<td class="ft_content">
						<c:if test="${true != readOnly}">
							<form:input   path="assesseContent"  htmlEscape="false" maxlength="300" style="width:155px;" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="validateMoney" class="input-medium required" />
						</c:if>
						<c:if test="${true == readOnly}">
							<form:input   path="assesseContent"  htmlEscape="false" maxlength="300" style="width:155px;background-color: #f4f4f4;" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="validateMoney" class="input-medium required" />
						</c:if>
							<font color="red">*</font>
						</td>
					</tr>

					<tr>
						<td colspan="5"></td>
						<td class="searchButton" style="text-align: right">
						<c:if test="${true != readOnly}">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
							<input id="btnClose" class="btn btn-primary" type="button" value="取 消" onclick="closeJBox();" />
							&nbsp;
						</c:if>
						</td>

						<c:if test="${readOnly eq true }">
							<td><input id="btnCancel" class="btn-primary btn "
								type="button" value="关闭" onclick="closeJBox()" /></td>
						</c:if>

					</tr>
				</table>
					
			</form:form>
			<c:if test="${not empty closeWindow}">
				<script type="text/javascript">
					alertx('${saveMessage}', function() {
						/* parent.$("#searchForm").submit(); */
						parent.$.loadDiv("targetListDiv", "${ctx }/credit/stockAssesseTarget/list", {
							applyNo : "${applyNo}",
							taskDefKey:"${taskDefKey}",
							grade:"${grade}"
						}, "post");
						closeJBox();
					});
				</script>
			</c:if>
		</div>
	</div>
</body>
</html>