<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>担保公司管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			document.getElementById("registerCapital").value = outputmoney("${creGuaranteeCompany.registerCapital}");
			document.getElementById("guaranteeLimit").value = outputmoney("${creGuaranteeCompany.guaranteeLimit}");	
			new CitySelect({
				data : data,
				provId : '#regProvince',
				cityId : '#regCity',
				areaId : '#regDistinct',
				isSelect : false
				});
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					var valueT1 = $("#registerCapital").val().replace(/,/g, "");
					var valueT2 = $("#guaranteeLimit").val().replace(/,/g, "");
					$("#registerCapital").val(valueT1);
					$("#guaranteeLimit").val(valueT2);
					var param = $("#inputForm").serialize();
					$.post("${ctx}/credit/creGuaranteeCompany/save", param, function(data) {
						if (data) {
							closeTip();
							if (data.status == '1') {
								alertx(data.message, function() {
									parent.location.reload();
                                    closeJBox();
								});
							} else {
								alertx(data.message);
							}
						}
					});
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			
			if (!checkIsNull('${creGuaranteeCompany.regDistinct}')) {
				setName(data, "reg", '${creGuaranteeCompany.regProvince}', '${creGuaranteeCompany.regCity}', '${creGuaranteeCompany.regDistinct}');
			}
			if ('${readOnly}' == '0') {
				setPageReadOnly();
				$("#btnSubmit").hide();
				$(".dbgs").hide();
				$("#guaranteeLimit").attr("readonly","true");
				$("[type=radio]").attr("disabled","true");
			}
			if ('${readOnly}' == '2') {
				$("input.edit").attr("readonly","true");
			}
			 function contractLoadCity() {
				$("#regCity").empty();
				$("#contractCity").append("<option value=''>请选择</option>");
				var $contractCity = $("#s2id_contractCity>.select2-choice>.select2-chosen");
				$contractCity.html("请选择");
				$.post("${ctx}/sys/area/treeNode", {
					parentId : $("#regProvince").val()
				}, function(data) {
					$.each(data, function(i, val) {
						$("#regCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
					});
					contractLoadDistinct();
				});
			} 
			 
			 function workLoadDistinct() {
					$("#regDistinct").empty();
					$("#regDistinct").append("<option value=''>请选择</option>");
					var $companyDistinct = $("#s2id_regDistinct>.select2-choice>.select2-chosen");
					$companyDistinct.html("请选择");

					$.post("${ctx}/sys/area/treeNode", {
						parentId : $("#regCity").val()
					}, function(data) {
						$.each(data, function(i, val) {
							$("#regDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
						});
					});
				}
			
		});
			
		function guaranA(){
			$("#contractId").toggle(600);
			}
			function guaranB(){
			$("#guranteeBase").toggle(600);
			}
			function guaranC(){
			$("#accountId").toggle(600);
			$("#guanED").toggle(600);
			}	
			function guaranD(){
				$("#upload").toggle(600);
				}
			
			function uploadPic(name,taskDefeKey){
				if(($("#unSocCreditNo").val() == null || $("#unSocCreditNo").val() == '') ||  ($("#guaranteeCompanyName").val() == null || $("#guaranteeCompanyName").val() == '')){
					alertx("公司名称和统一社会信用代码不能为空");
				}else{
					var url = "${ctx}/credit/videoUpload/webuploader?applyNo="+$("#unSocCreditNo").val()+"&taskDefKey="+taskDefeKey+"&fileDir="+$("#guaranteeCompanyName").val()+"/"+name+"&id=";
					window.open(url); 
				}
				 
			}
			
			function viewImage(taskDefeKey) {
				var windowWidth = document.body.offsetWidth - 50;
				var url = "${ctx}/credit/ApplyRegisterVO/form?applyNo="+$("#unSocCreditNo").val()+"&taskDefKey="+taskDefeKey;
				window.open(url);
			}
			
			
			function calculateGuranteeAmount(){
				var guranteeAmount = $("#registerCapital").val().replace(/,/g, "");// 注册资本
				$("#guaranteeLimit").val(outputmoney((guranteeAmount*10)));
			}
	</script>
	<style type="text/css">
		.input-medium {
    	width: 150px;
		}
		.table th, .table td {
    	text-align: center;
		}
	</style>
</head>
<body>
	<form:form id="inputForm" modelAttribute="creGuaranteeCompany" action="${ctx}/credit/creGuaranteeCompany/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="searchInfo">
				<h3  class="searchTitle" onclick="guaranA()">基本信息</h3>
				<div id="contractId" class="searchCon">
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">担保公司名称：</td>
							<td class="ft_content" colspan="4">
								<form:input path="guaranteeCompanyName"  htmlEscape="false" maxlength="100" class="input-medium required "/>
								<font color="red">*</font>
							</td>
							<td class="ft_label">公司性质：</td>
							<td class="ft_content" colspan="4">
								<form:select id="companyType" path="companyType" class="input-medium required">
								<form:option value="" td="" />
								<form:options items="${fns:getDictList('GURANTEE_NATURE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">统一社会信用代码：</td>
							<td class="ft_content"  colspan="4">
								<form:input path="unSocCreditNo" htmlEscape="false" maxlength="30" class="input-medium required edit"/>
								<font color="red">*</font>
							</td>
							<td class="ft_label">注册资本：</td>
							<td class="ft_content" colspan="4">
								<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);calculateGuranteeAmount();" id="registerCapital" path="registerCapital" htmlEscape="false" class="input-medium required" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">公司注册地址：</td>
							<td class="ft_content" colspan="7">
								<form:select path="regProvince"  class="input-small nuNullCheck" data-code="-1"  />
								&nbsp;省
								<form:select path="regCity"  class="input-small nuNullCheck" data-code="-1"  />
								&nbsp;市
								<form:select path="regDistinct"  class="input-small nuNullCheck" data-code="-1" />
								&nbsp;区&nbsp;
								<font color="red">*</font>
								&nbsp;
								地址明细：
								<input type="text" name="guranteeDetails" id="companyRegDetails" class="input-medium required" maxlength="200" value="${creGuaranteeCompany.guranteeDetails}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width:150px"  />
								<font color="red">*</font>
							</td>	
						</tr>
						<tr>
							<td class="ft_label">公司营业地址：</td>
							<td class="ft_content">
								<form:input path="operateAddress" htmlEscape="false" maxlength="50" class="input-medium required "/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">公司法人代表代表：</td>
							<td class="ft_content" colspan="2">
								<form:input path="corporationRepresent" htmlEscape="false" maxlength="30" class="input-medium required"/>
								<font color="red">*</font>
							</td>
							<td class="ft_label">联系人：</td>
							<td class="ft_content" colspan="2">
								<form:input path="linkman" htmlEscape="false" maxlength="30" class="input-medium required"/>
								<font color="red">*</font>
							</td>
							<td class="ft_label">联系方式：</td>
							<td class="ft_content" colspan="2">
								<form:input path="linkMobile" htmlEscape="false" maxlength="11" class="input-medium mobile required "/>
								<font color="red">*</font>
							</td>
						</tr>
					</table>
				</div>
			</div>
			
			<div class="searchInfo">
				<h3  class="searchTitle" onclick="guaranB()">配置信息</h3>
				<div id="guranteeBase" class="searchCon">
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">担保额度：</td>
							<td class="ft_content" colspan="4">
								<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="guaranteeLimit" path="guaranteeLimit" htmlEscape="false" class="input-medium required" />
								<font color="red">*</font>
							</td>
						<td></td>
						<td></td>
						</tr>
						<tr>
							<td class="ft_label">担保费费率(%)：</td>
							<td class="ft_content" colspan="4">
								<form:input path="costRate" htmlEscape="false" class="input-medium" onblur="this.value=gdpMoney(this.value);" onkeyup="gdpMax(this)" maxlength="30" />
								<font color="red">*</font>
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td class="ft_label">担保状态：</td>
							<td class="ft_content">
								<form:radiobuttons class="input-medium required" path="guaranteeState" items="${fns:getDictList('ACCOUNT_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								<font color="red">*</font>
							</td>	
							<td></td>
						</tr>
					</table>
				</div>
			</div>	
			<div class="searchInfo">
				<h3  class="searchTitle" onclick="guaranC()">开户信息</h3>
				<div id="guanED" class="searchCon">
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">冠E贷账号：</td>
							<td class="ft_content">${mobileGed}</td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</table>
				</div>
				<div id="accountId" class="searchCon">
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">开户状态：</td>
							<td class="ft_content">
								${empty  creGedAccountCompany.socialCreditCode?"未开户":"已开户"}
							</td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td class="ft_label">账户名称：</td>
							<td class="ft_content">
								${creGedAccountCompany.companyName}
							</td>
							<td class="ft_label">开户银行：</td>
							<td class="ft_content">
								${creGedAccountCompany.companyBankOfDepositValue}
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td class="ft_label">银行账户：</td>
							<td class="ft_content">
								${creGedAccountCompany.companyAccount}
							</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>	
						</tr>
					</table>
				</div>
			</div>
			<div class="searchInfo">
				<h3  class="searchTitle" onclick="guaranD()">影像信息</h3>
				<div id="upload" class="searchCon">
					<table id="contentTable" class="table table-striped table-bordered table-condensed" align="center">
						<thead>
							<tr>
								<th width="30%">资料类型</th>
								<th width="30%">图片数量</th>
								<th width="30%">操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="ft_label">营业执照</td>
								<td class="ft_content">
									${businessLicenceNum}
								</td>
								<td><a href="javascript:void(0);" class="dbgs" onclick="uploadPic('营业执照','100')" >上传图片</a>    
								<a href="#" onclick="viewImage('100');"><span><b></b>查看</span></a>
								</td>
							</tr>
							<tr>
								<td class="ft_label">经营许可证</td>
								<td class="ft_content">
									${operatePermitNum}
								</td>
								<td><a href="javascript:void(0);" class="dbgs" onclick="uploadPic('经营许可证','101')" >上传图片</a>    
								<a href="#" onclick="viewImage('101');"><span><b></b>查看</span></a></td>
							</tr>
							<tr>
								<td class="ft_label">担保函模板</td>
								<td class="ft_content">
									${certificateSecurityNum}
								</td>
								<td><a href="javascript:void(0);" class="dbgs" onclick="uploadPic('担保函模板','102')" >上传图片</a>    
								<a href="#" onclick="viewImage('102');"><span><b></b>查看</span></a></td>
							</tr>
							<tr>
								<td class="ft_label">其它</td>
								<td class="ft_content">
									${otherNum}
								</td>
								<td><a class="dbgs" href="javascript:void(0);" onclick="uploadPic('其它','103')" >上传图片</a>    
								<a href="#" onclick="viewImage('103');"><span><b></b>查看</span></a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<c:if test="${readOnly ne '0' }">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="增 加" />&nbsp; 
			</c:if>
			<input id="btnCancel" class="btn btn-primary" type="button" value="取 消" onclick="closeJBox();" />
			&nbsp;
	</form:form>
</body>
</html>