<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {

		if (!checkIsNull('${checkDailyAllocate.checkDaily.checkDailyResult}')) {
			var value = '${checkDailyAllocate.checkDaily.checkDailyResult}';
			var checkBoxes = $("input[name='checkDaily.checkDailyResult']");
			getCheckBoxesValue(value, checkBoxes);
		}

		$("#checkDailyAllocateForm").validate({
		submitHandler : function() {
			var subFlag = $("#subFlag").val();
			switch (subFlag){
				case "save" :
					save();
					break;
				case "legal" :
					legal();
					break;
				case "collection" :
					collection();
					break;
				case "applyExtend" :
					applyExtend();
					break;
				case "borrowNew" :
					borrowNew();
					break;
				case "specialCase" :
					specialCase();
					break;
				case "signContract" :
					signContract();
					break;
				default :
					alertx("系统参数发生错误，请联系相关人员解决！");
					break;
			}
			/* if ("save" == subFlag) {
				save();
			} else if ("legal" == subFlag) {
				legal();
			} else if ("collection" == subFlag) {
				collection();
			} else if ("applyExtend" == subFlag) {
				applyExtend();
			} else if ("borrowNew" == subFlag) {
				borrowNew();
			} else if ("specialCase" == subFlag) {
				specialCase();
			} else if ("signContract" == subFlag) {
				signContract();
			} else {
				alertx("系统参数发生错误，请联系相关人员解决！");
			} */
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});

	function checkDailyAllocateToggle() {
		$("#checkDailyAllocateId").toggle(600);
	}

	//保存之前
	function beforeSubmit(subFlag) {
		$("#subFlag").val(subFlag)
		$("#checkDailyAllocateForm").submit();
	}

	//签署保证合同
	function signContract() {
		confirmx("您确定要签署保证合同吗？", function() {
			loading();
			var hiddenResult = getCheckedValue('checkDaily.checkDailyResult');
			var url = "${ctx}/postloan/checkDaily/signContract?hiddenResult=" + hiddenResult;
			var formJson = $("#checkDailyAllocateForm").serializeJson();
			$.post(url, formJson, function(data) {
				if (data) {
					closeTip();
					if (data.status == 1) {
						var url = "${ctx}/postloan/checkTwentyFive/signGuarContract";
						var title = "签署保证合同";
						var width = $(window).width() - 100;
						width = Math.max(width, 800);
						var height = $(window).height() - 100;
						height = Math.min(height, 600);
						openGuarantyContractJBox("signGuarContractBox", url, title, width, height, {
						contractNo : '${checkDailyAllocate.contractNo}',
						flag : '${flag}'
						});
					} else {
						alertx(data.message, function() {
							enableButtons("complianceDiv");
						});
					}
				}
			});
		});

	}

	//借新还旧
	function borrowNew() {
		var formJson = $("#checkDailyAllocateForm").serializeJson();
		var hiddenResult = getCheckedValue('checkDaily.checkDailyResult');
		var contractNo = '${checkDailyAllocate.contractNo}';
		var url = "${ctx}/postloan/borrowNew/form?hiddenResult=" + hiddenResult + "&applyFlag=apply";
		var title = "借新还旧申请";
		var width = $(window).width() - 100;
		width = Math.max(width, 800);
		var height = $(window).height() - 200;
		height = Math.min(height, 400);
		openJBox("borrowNewForm", url, title, width, height, formJson);

	}

	//特殊情况处理
	function specialCase() {
		var formJson = $("#checkDailyAllocateForm").serializeJson();
		var hiddenResult = getCheckedValue('checkDaily.checkDailyResult');
		var url = "${ctx}/postloan/checkDaily/specialCaseForm?hiddenResult=" + hiddenResult;
		var title = "特殊情况处理";
		var width = $(window).width() - 100;
		width = Math.max(width, 800);
		var height = $(window).height() - 200;
		height = Math.min(height, 400);
		openJBox("specialCaseForm", url, title, width, height, formJson);
	}
	//保存
	function save() {
		if (disableButtons("complianceDiv")) {
			confirmx("您确定要提交日常检查结果吗？", function() {
				loading();
				var hiddenResult = getCheckedValue('checkDaily.checkDailyResult');
				var url = "${ctx}/postloan/checkDaily/save?hiddenResult=" + hiddenResult;
				var formJson = $("#checkDailyAllocateForm").serializeJson();
				$.post(url, formJson, function(data) {
					if (data) {
						closeTip();
						if (data.status == 1) {
							alertx(data.message, function() {
								enableButtons("complianceDiv");
								parent.page();
								closeJBox();
							});
						} else {
							alertx(data.message, function() {
								enableButtons("complianceDiv");
							});
						}
					}
				});
			});
		}
	}

	//法务
	function legal() {
		confirmx("您确定要进行法务催收吗？", function() {
			loading();
			var type = '${currCollectionTypeLegal}';
			$("#currCollectionType").val(type);
			var hiddenResult = getCheckedValue('checkDaily.checkDailyResult');
			var formJson = $("#checkDailyAllocateForm").serializeJson();
			$.post("${ctx}/postloan/checkDaily/collection?hiddenResult=" + hiddenResult, formJson, function(data) {
				if (data) {
					closeTip();
					if (data.status == 1) {
						alertx(data.message, function() {
							enableButtons("complianceDiv");
							parent.page();
							closeJBox();
						});
					} else {
						alertx(data.message);
					}
				}
			});
		});
	}

	//催收
	function collection() {
		confirmx("您确定要进行催收吗？", function() {
			loading();
			var type = '${currCollectionType}';
			$("#currCollectionType").val(type);
			var hiddenResult = getCheckedValue('checkDaily.checkDailyResult');
			var formJson = $("#checkDailyAllocateForm").serializeJson();
			$.post("${ctx}/postloan/checkDaily/collection?hiddenResult" + hiddenResult, formJson, function(data) {
				if (data) {
					closeTip();
					if (data.status == 1) {
						alertx(data.message, function() {
							enableButtons("complianceDiv");
							parent.page();
							closeJBox();
						});
					} else {
						alertx(data.message);
					}
				}
			});
		});
	}

	//申请展期
	function applyExtend() {
		confirmx("您确定要进入合同展期申请吗？", function() {
			loading();
			var url1 = "${ctx}/postloan/applyExtend/validateApplyExtendForm?contractNo=" + '${checkDailyAllocate.contractNo }';
			$.post(url1, null, function(data) {
				if (data) {
					closeTip();
					if (data.status == 1) {
						var url = "${ctx}/postloan/applyExtend/list/0?contractNo=" + '${checkDailyAllocate.contractNo }';
						var title = "展期申请";
						parent.window.location.href = url;
					} else {
						alertx("该合同已经进入展期流程，不可再次进行合同展期！");
					}
				}
			});

		});
	}
</script>
<div id="complianceDiv" class="searchInfo">
	<h3 onclick="checkDailyAllocateToggle()" class="searchTitle">日常检查借后管理建议</h3>
	<div id="checkDailyAllocateId" class="searchCon">
		<form:form id="checkDailyAllocateForm" modelAttribute="checkDailyAllocate" action="${ctx}/postloan/checkDaily/save" method="post" class="form-horizontal">
			<sys:message content="${message}" />
			<form:hidden path="id" />
			<form:hidden path="contractNo" />
			<form:hidden path="checkDaily.id" />
			<form:hidden path="checkDaily.contractNo" />
			<input type="hidden" id="currCollectionType" name="currCollectionType" />
			<input type="hidden" id="currCollectionStatus" name="currCollectionStatus" value="${currCollectionStatus}" />
			<input type="hidden" id="currCollectionFrom" name="currCollectionFrom" value="${currCollectionFrom}" />
			<input type="hidden" id="hiddenResult" />
			<input type="hidden" id="subFlag" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label_radio" style="width: 8%;">审核意见：</td>
					<td class="ft_content_radio" style="width: 92%;">
						<form:checkboxes items="${fns:getDictList('CHECK_RESULT') }" path="checkDaily.checkDailyResult" cssClass="required" itemLabel="label" itemValue="value" htmlEscape="false" />
					</td>
				</tr>
				<tr>
					<td class="ft_label_radio" style="width: 8%;">具体建议：</td>
					<td class="ft_content_radio" style="width: 92%;">
						<form:textarea path="checkDaily.checkDailyAdvice" cssClass="area-xxlarge required" />
					</td>
				</tr>
			</table>
		</form:form>
		<div class="searchButton">
			<input id="btnSave" class="btn btn-primary" type="button" value="提 交" onclick="beforeSubmit('save');" />
			&nbsp;
			<input id="btnCollection" class="btn btn-primary" type="button" value="催 收" onclick="beforeSubmit('collection');" />
			&nbsp;
			<input id="btnLaw" class="btn btn-primary" type="button" value="法 务" onclick="beforeSubmit('legal');" />
			&nbsp;
			<input id="btnExtend" class="btn btn-primary" type="button" value="展 期" onclick="beforeSubmit('applyExtend');" />
			&nbsp;
			<input id="btnReBorrow" class="btn btn-primary" style="width: 100px;" type="button" value="借新还旧" onclick="beforeSubmit('borrowNew');" />
			&nbsp;
			<input id="btnSign" class="btn btn-primary" style="width: 100px;" type="button" value="签署保证合同" onclick="beforeSubmit('signContract');" />
			&nbsp;
			<input id="btnSpecial" class="btn btn-primary" style="width: 100px;" type="button" value="特殊情况处理" onclick="beforeSubmit('specialCase');" />
		</div>
	</div>
</div>