  <%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<title>抵质押物检查信息管理</title>
	<meta name="decorator" content="default" />
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
				var param = $("#inputForm").serializeJson();
					$.post("${ctx}/postloan/mortgageEvaluateInfo/save", param, function(data) {
						if (data.status == '1') {
							alertx("检查信息保存成功", function() {
							closeJBox();
							});
						}else{
							alertx("检查信息保存失败");
						}
					});
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
						checkReq(error,element);
				}
			});
		});
	//编辑
	</script>
<div class="wrapper">
		<div class="searchInfo">
	<form:form id="inputForm" modelAttribute="mortgageInfo" method="post" class="form-horizontal">
		<input type="hidden" id="applyNo" name="applyNo" value="${mortgageInfo.applyNo}"/>
		<input type="hidden" id="infoId" name="infoId" value="${mortgageInfo.infoId}"/>
		<input type="hidden" id="id" name="id" value="${mortgageInfo.id}"/>
		<sys:message content="${message}"/>		
		<div id="complianceDiv" class="searchInfo">
	<h3  class="searchTitle">其他情况检查</h3>
	<div id="borrowAfter" class="searchCon">
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">是否保险：</td>
						<td class="ft_content">
							<select id="isSafe" name="isSafe"  class="input-medium required">
							<option value="" selected>请选择</option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="dict">
								<c:if  test="${dict.value==mortgageInfo.isSafe}">
									<option value="${mortgageInfo.isSafe}" selected>${dict.label}</option>
								</c:if> 
								<c:if  test="${dict.value!=mortgageInfo.isSafe}">
									<option value="${dict.value}">${dict.label}</option>
								</c:if>
					         </c:forEach>
							</select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">保险是否在有效期内：</td>
						<td class="ft_content">
							<select id="isInsuranceValid" name="isInsuranceValid"  class="input-medium required">
								<option value="" selected>请选择</option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="dict">
						<c:if  test="${dict.value==mortgageInfo.isInsuranceValid}">
							<option value="${mortgageInfo.isInsuranceValid}" selected>${dict.label}</option>
								</c:if> 
								<c:if  test="${dict.value!=mortgageInfo.isInsuranceValid}">
								<option value="${dict.value}">${dict.label}</option>
								</c:if>
					</c:forEach>
							</select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">保险权益是否在我公司：</td>
						<td class="ft_content">
							<select id="isInsuranceIncom" name="isInsuranceIncom"  class="input-medium required">
									<option value="" selected>请选择</option>
									<c:forEach items="${fns:getDictList('yes_no')}" var="dict">
						<c:if  test="${dict.value==mortgageInfo.isInsuranceIncom}">
									<option value="${mortgageInfo.isInsuranceIncom}" selected>${dict.label}</option>
								</c:if> 
								<c:if  test="${dict.value!=mortgageInfo.isInsuranceIncom}">
									<option value="${dict.value}">${dict.label}</option>
								</c:if>
					</c:forEach>
							</select>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
					<td class="ft_label">抵质押人营业执照是否在有效期内：</td>
						<td class="ft_content">
							<select id="isInsuranceLicValid" name="isInsuranceLicValid" class="input-medium required">
								<option value="" selected>请选择</option>
									<c:forEach items="${fns:getDictList('yes_no')}" var="dict">
						<c:if  test="${dict.value==mortgageInfo.isInsuranceLicValid}">
									<option value="${mortgageInfo.isInsuranceLicValid}" selected>${dict.label}</option>
								</c:if> 
								<c:if  test="${dict.value!=mortgageInfo.isInsuranceLicValid}">
									<option value="${dict.value}">${dict.label}</option>
								</c:if>
					</c:forEach>
							</select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">抵质押人意愿：</td>
						<td class="ft_content">
							<select id="mortgagedPersonWish" name="mortgagedPersonWish" class="input-medium required">
									<option value="" selected>请选择</option>
									<c:forEach items="${fns:getDictList('MORTGAGED_PERSON_WISH')}" var="dict">
								<c:if  test="${dict.value==mortgageInfo.mortgagedPersonWish}">
									<option value="${mortgageInfo.mortgagedPersonWish}" selected>${dict.label}</option>
								</c:if> 
								<c:if  test="${dict.value!=mortgageInfo.mortgagedPersonWish}">
									<option value="${dict.value}">${dict.label}</option>
								</c:if>
									</c:forEach>
							</select>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">抵质押情况综合检查结论：</td>
						<td class="ft_content" colspan="5">
						<pre class="input-xxlarge pre-style textareaWidth" id="pre1"></pre>
							<textarea id="checkResult" name="checkResult" rows="4" cols="3" maxlength="1000" class="area-xxlarge textarea-style required" onkeyup="adjustTextareaLength('checkResult','pre1')">${mortgageInfo.checkResult}</textarea>
						<font color="red">*</font>
						</td>
						
					</tr> 
					<tr>
						<td class="ft_label">审批意见：</td>
						<td class="ft_content" colspan="5">
						<pre class="input-xxlarge pre-style textareaWidth" id="pre2"></pre>
							<textarea id="checkAdvice" name="checkAdvice" rows="4" cols="3" maxlength="1000" class="area-xxlarge textarea-style required" onkeyup="adjustTextareaLength('checkAdvice','pre2')">${mortgageInfo.checkAdvice}</textarea>
						<font color="red">*</font>
						</td>
						
					</tr> 
				</table>
					</div>
					</div>
		<div class="form-actions">
			<input id="submitBtn" class="btn btn-primary" type="submit" value="保 存"  />&nbsp;
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="closeJBox()"/>
		</div>
	</form:form>

			</div>
		</div>
</html> 