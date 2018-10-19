<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	//获取iframe的高度
	function calHeight(doc) {
		var ch = Math.max(doc.body.clientHeight, doc.documentElement.clientHeight);
		var sh = Math.max(doc.body.scrollHeight, doc.documentElement.scrollHeight);
		return Math.max(ch, sh);
	}

	var assessPostSelectElement;
	//任务分配
	function selectStockPerson(assessPostSelect) {
		assessPostSelectElement = assessPostSelect;
		var urlsuffix = "?applyNo=${actTaskParam.applyNo}&taskId=${actTaskParam.taskId}&headUrl=${actTaskParam.headUrl}&taskDefKey=${actTaskParam.taskDefKey}&procDefId=${actTaskParam.procDefId}&status=${actTaskParam.status}&procInstId=${actTaskParam.procInstId}";
		var url = "${ctx}/credit/stockTaskDistribute/gotoLoadOrgUser" + urlsuffix;
		var width = $(document).width() - 100;
		width = Math.max($(document).width() - 100, 800);
		var height = $(document).height() - 100;
		height = Math.min($(document).height() - 100, 400);
		openJBox("viewImageBox", url, "股权尽调人员选择", width, height);
	}

	function disabledAssessPostSelect(){
		assessPostSelectElement.disabled = "disabled";
	}

	//iframe加载时初始化高度
	function reSizeFrame(frameId) {
		var doc = window.frames[frameId].document;
		var height = calHeight(doc);
		$("#" + frameId).height(height + 80);
	}
	//流程轨迹
	function processTrack(taskDefKey, procInstId) {
		var url = "${ctx}/credit/taskCenter/processTrack?procInstId=" + procInstId;
		openJBox("trackBox", url, "流程轨迹", $(document).width() - 100, $(window).height() - 50);
	}
	//流程图
	function tracePhoto(procDefId, execId) {
		var url = "${ctx}/credit/taskCenter/trace/photo/" + procDefId + "/" + execId;
		openJBox("tracePhotoBox", url, "流程图", $(document).width() - 100, $(window).height() - 50);
	}

	//影像上传
	/* function uploadImage() {
		var windowWidth = document.body.offsetWidth - 50;
		var url = "${ctx}/credit/applyRegister/toUploadPage?applyNo=${actTaskParam.applyNo}&taskDefKey=${actTaskParam.taskDefKey}&system=audit&optype=7&productTypeId=${productType}";
		//openJBox("uploadImageBox", url, "影像上传", windowWidth, height);
		window.open(url,'${actTaskParam.applyNo}',"width= 500px" + ",height=250px");
	} */
	function uploadImage() {
		var url = "${ctx}/credit/videoUpload?applyNo=${actTaskParam.applyNo}&taskDefKey=${actTaskParam.taskDefKey}&type=${productType}";
		window.open(url);
	}

	//查看影像
	function viewImage(status) {
		var windowWidth = document.body.offsetWidth - 50;
		var url = "${ctx}/credit/ApplyRegisterVO/form?applyNo=${actTaskParam.applyNo}&taskDefKey=${actTaskParam.taskDefKey}&optype=1&productTypeId=${productType}&status=" + status;
		//openJBox("viewImageBox", url, "查看影像", windowWidth, height);
		//window.showModalDialog(url,null,"dialogWidth:550px;dialogHeight:300px;status:no;help:no;resizable:yes;");
		window.open(url, '${actTaskParam.applyNo}');
		//window.location.href=url;
	}

	//查看影像  借后的无权限影像
	function showPostViewImage(status){
		var windowWidth = document.body.offsetWidth - 50;
		var url = "${ctx}/credit/ApplyRegisterVO/post_form?applyNo=${actTaskParam.applyNo}&taskDefKey=${actTaskParam.taskDefKey}&optype=1&productTypeId=${productType}&status=" + status;
		window.open(url, '${actTaskParam.applyNo}');
	}

	//转办
	function change() {
		var urlsuffix = "?applyNo=${actTaskParam.applyNo}&taskId=${actTaskParam.taskId}&headUrl=${actTaskParam.headUrl}&taskDefKey=${actTaskParam.taskDefKey}&procDefId=${actTaskParam.procDefId}&status=${actTaskParam.status}&procInstId=${actTaskParam.procInstId}";
		var url = "${ctx}/credit/taskCenter/change" + urlsuffix;
		var width = $(document).width() - 100;
		width = Math.max($(document).width() - 100, 800);
		var height = $(document).height() - 100;
		height = Math.min($(document).height() - 100, 400);
		openJBox("viewImageBox", url, "转办", width, height);
	}
	/* 加载Tab标签页,
	   @param tabId 页签id
	   @param ulId  主页签id
	   @param force 为true,强制重新加载页面*/
	function showTab(tabId, url, ulId, force) {
		if (ulId) {
			$('#' + ulId + 'a[href="#' + tabId + '"]').tab('show');
		} else {
			$('#mainTabs a[href="#' + tabId + '"]').tab('show');
		}
		if ($("#" + tabId).html().length < 20 || force) {
			if (null == url || "" == url) {
				return false;
			}
			var urlsuffix = "&applyNo=${actTaskParam.applyNo}&headUrl=${actTaskParam.headUrl}&taskId=${actTaskParam.taskId}&taskDefKey=${actTaskParam.taskDefKey}&procDefId=${actTaskParam.procDefId}&status=${actTaskParam.status}&procInstId=${actTaskParam.procInstId}&oper=apply&custId=${actTaskParam.custId}";
			url = url + urlsuffix;
			$("#" + tabId).html("<iframe style=\"min-height: 100px;\" id='frame_" + tabId + "' name='frame_" + tabId + "' src='" + url + "' width='100%' height='100%' frameborder='0' onload='reSizeFrame(\"frame_" + tabId + "\")'></iframe>");
		}
		return false;
	}
	var forceFlag;
	//借款申请
	function showLoanApply(readOnly) {
		if (readOnly == "false") {
			forceFlag = true;
		} else {
			forceFlag = false;
		}
		showTab('tab_loanApply', '${ctx}/credit/applyInfo/loanApplyInfoForm?readOnly=' + readOnly, null, forceFlag);
	}
	//客户信息
	function showCustInfoTab(readOnly) {
		if (readOnly == "false") {
			forceFlag = true;
		} else {
			forceFlag = false;
		}
		showTab('tab_custInfo', '${ctx}/custinfo/custInfoIndex/index?readOnly=' + readOnly, null, forceFlag);
	}
	//批量借款企业
	function showCompanyInfoTab(readOnly) {
		if (readOnly == "false") {
			forceFlag = true;
		} else {
			forceFlag = false;
		}
		 showTab('tab_companyInfo', '${ctx}/credit/companyInfo/index?readOnly=' + readOnly, null, forceFlag);

	}

	///担保公司信息
    function showGuarantorInfoTab(readOnly) {
        if (readOnly == "false") {
            forceFlag = true;
        } else {
            forceFlag = false;
        }
        showTab('tab_guarantorInfo', '${ctx}/credit/guarantorInfoIndex/index?readOnly=' + readOnly, null, forceFlag);
    }




	//抵质押物信息
	function showMortgageCarInfoTab(readOnly) {
		showTab('tab_mortgageCarInfo', '${ctx}/credit/mortgageCarInfo/mortgage?readOnly=' + readOnly);
	}
	//抵质押物信息评估
	function showMortgageCarEvaluateInfoTab(readOnly) {
		showTab('tab_mortgageCarInfo', '${ctx}/credit/mortgageCarEvaluateInfo?readOnly=' + readOnly);
	}
	//录入结论
	function showConclusionTab(readOnly) {
		showTab('tab_conclusion', '${ctx}/credit/conclusion/index?readOnly=' + readOnly);
	}
    //线下录单录入结论
    function showUnderConclusionTab(readOnly) {
        showTab('tab_underConclusion', '${ctx}/credit/underConclusion/index?readOnly=' + readOnly);
    }
    //线下录单企业信息
    function showUnderCompanyTab(readOnly) {
        showTab('tab_underCompanyInfo', '${ctx}/credit/underCompanyInfo/underCompanyDetail?readOnly=' + readOnly );
    }

    //抵质押物信息
    function showUnderMortgageCarEvaluateInfoTab(readOnly) {
        //showTab('tab_mortgageCarInfo', '${ctx}/credit/mortgageCarInfo/mortgage?readOnly=' + readOnly);
        <%--showTab('tab_underMortgageCarInfo', '${ctx}/postloan/debtCollectionDetailsPage/toCreMortgageCarInfoPage?readOnly=' + readOnly);--%>
        showTab('tab_underMortgageCarInfo', '${ctx}/credit/underCompanyInfo/zichan?readOnly=' + readOnly);
    }


	//借前外访信息
	function showCheckDoubtfulTab(readOnly) {
		showTab('tab_checkDoubtful', '${ctx}/credit/checkDoubtful?readOnly=' + readOnly);
	}
	//面审信息
	function showCheckFaceTab(readOnly) {
		showTab('tab_checkFace', '${ctx}/credit/checkFace/list?readOnly=' + readOnly);
	}
	//法海风控
	function showFhRiskControlTab(readOnly) {
		showTab('tab_fhRiskControl', '${ctx}/credit/fhRiskControl/list?readOnly=' + readOnly);

	}
	//外访信息
	function showCheckCoupleDoubtfulTab(readOnly) {
		showTab('tab_checkCoupleDoubtful', '${ctx}/credit/checkCoupleDoubtful?readOnly=' + readOnly);
	}
	//电话核查
	function showCheckPhoneTab(readOnly) {
		showTab('tab_checkPhone', '${ctx}/credit/checkPhone?readOnly=' + readOnly);
	}
	//网查
	function showCheckWebTab(readOnly) {
		showTab('tab_checkWeb', '${ctx}/credit/checkWeb?readOnly=' + readOnly);
	}
	//外访费登记
	function showCheckFeeTab(readOnly) {
		showTab('tab_checkFee', '${ctx}/credit/checkFee/list?readOnly=' + readOnly);
	}
	//综合信息
	function showInformationTab(readOnly) {
		showTab('tab_information', '${ctx}/credit/information/index?readOnly=' + readOnly);
	}
	//征信及流水
	function showCreditAndLineTab(readOnly) {
		showTab('tab_creditAndLine', '${ctx}/credit/creditAndLine/load?readOnly=' + readOnly);
	}
	//财报导入
	function showFinanceImportTab(readOnly) {
		showTab('tab_financeImport', '${ctx}/credit/financialStateImport/form?readOnly=' + readOnly);
	}
	//信审报告
	function showCreditReportTab(readOnly) {
		showTab('tab_creditReport', '${ctx}/credit/creditReport/list?readOnly=' + readOnly);
	}
	//信审意见书
	function showCreditViewBookTab(readOnly,controGZ) {
		showTab('tab_creditViewBook', '${ctx}/credit/creditViewBook/load?controGZ='+controGZ+'&showStatus=${showStatus}&readOnly=' + readOnly,null, true);
	}
	//批复信息
	function showCheckApproveTab(readOnly) {
		showTab('tab_checkApprove', '${ctx}/credit/checkApprove/load?readOnly=' + readOnly);
	}
	//冠e通
	function showGetTab(readOnly) {
		if (readOnly == "false") {
			forceFlag = true;
		} else {
			forceFlag = false;
		}
		showTab('tab_get', '${ctx}/credit/gqgetComInfo/form?readOnly=' + readOnly, null, forceFlag);
	}
	// 黑名单规则
	function showRevieved(readOnly) {
		showTab('tab_revieved', '${ctx}/credit/reviewedRule?readOnly=' + readOnly, null, forceFlag);
	}
	//复议结论
	function showReviewResultTab(readOnly) {
		showTab('tab_reviewResult', '${ctx}/credit/reviewConclusionIndex/load?readOnly=' + readOnly);
	}
	//关联匹配
	function showAssociationTab(readOnly) {
		showTab('tab_association', '${ctx}/credit/applyRelation/relationalMatch?readOnly=' + readOnly);
	}
	//支持决策
	function showDSSTab(readOnly) {
		showTab('tab_dss', '${ctx}/credit/supportDecision/index?readOnly=' + readOnly);
	}
	//预约信息
	function showAppointInfoTab(readOnly) {
		showTab('tab_appointInfo', '${ctx}/credit/appointInfo/load?readOnly=' + readOnly);
	}
	//取消审批
	function showCancelApproveTab(readOnly) {
		showTab('tab_cancelApprove', '${ctx}/credit/processSuggestionInfo/cancelApprove?readOnly=' + readOnly);
	}
	//面签信息
	function showFaceSignTab(readOnly) {
		showTab('tab_faceSign', '${ctx}/credit/contract/contractSignInfo?readOnly=' + readOnly);
	}
	//财务放款
	function showFinanceLoanTab(readOnly) {
		showTab('tab_financeLoan', '${ctx}/credit/applyLoanStatus/form?readOnly=' + readOnly);
	}
	//外访费返还
	function showCheckFeeReturnFormTab(checkFeeId, readOnly) {
		showTab('tab_checkFeeReturn', '${ctx}/credit/checkFee/checkFeeReturn?checkFeeId=' + checkFeeId + '&readOnly=' + readOnly);
	}
	//外访费返还-客户借款信息
	function showApplyInfoViewTab(applyNo, readOnly) {
		showTab('tab_applyInfoView', '${ctx}/credit/applyInfo/loanApplyInfoForm?readOnly=' + readOnly + '&applyNo=' + applyNo);
	}
	//联合授信预约信息
	function showUnionAppointInfoTab(readOnly) {
		showTab('tab_unionAppointInfo', '${ctx}/credit/appointInfo/loadUnion?readOnly=' + readOnly);
	}
	//联合授信面签信息
	function showFaceSignUnionTab(readOnly) {
		showTab('tab_faceSignUnion', '${ctx}/credit/contractUnion/contractSignInfo?readOnly=' + readOnly);
	}
	//批复信息(联合授信)
	function showCheckApproveUnionTab(readOnly) {
		showTab('tab_checkApprove_union', '${ctx}/credit/checkApproveUnion/load?readOnly=' + readOnly);
	}
	//批复信息详情(联合授信)
	function showCheckApproveUnionDetailTab(readOnly) {
		var approId = $("#approId").val();
		showTab('tab_checkApprove_union_detail', '${ctx}/credit/checkApproveUnion/form?readOnly=' + readOnly + '&id=' + approId);
	}
	//财务放款  new
	function showFinanceLoanTabNew(readOnly) {
		showTab('tab_financeLoan', '${ctx}/credit/applyLoanStatus/formNew?readOnly=' + readOnly);
	}
	//联合授信冠e通
	function showGetUnionTab(readOnly) {
		if (readOnly == "false") {
			forceFlag = true;
		} else {
			forceFlag = false;
		}
		showTab('tab_getUnion', '${ctx}/credit/gqgetComInfoUnion/form?readOnly=' + readOnly + '&approveId=${approId}', null, forceFlag);
	}
		//批复信息详情(联合授信)
	function showApplyLoanUnionDetailTab(approId,custId) {
		var url = "${ctx}/credit/contractUnion/contractAuditForm?readOnly=true&approId="+approId;
		showTab('tab_applyLoan_union_detail',url);
	}

	//加减分项
	function showMarkNormTab(readOnly){
		showTab('tab_markNormDiv', '${ctx}/credit/creStockMarkNorm/form?readOnly=' + readOnly);
	}

	//一次网查
	function showStockWebCheck(readOnly) {
		showTab('tab_stockWebCheck', '${ctx}/credit/stockWebCheck/form?readOnly=' + readOnly);
	}

	//股权尽调
	function showValueStationTab(readOnly) {
		showTab('tab_valueStation', '${ctx}/credit/stockInfo/form?readOnly=' + readOnly + '&id=${stockInfoId}'+'&stockTaskReceiveId=${stockTaskReceiveId}');
	}
	//创建冠易贷账号
	function showGedAccount(readOnly) {
		showTab('tab_gedAccount', '${ctx}/credit/createGedAccount/index?readOnly=' + readOnly,null, true);
	}

    //线下还款-借款信息
    function showLoanInfoTab(readOnly) {
        showTab('tab_loanInfo', '${ctx}/credit/checkApprove/underCheckApproveIndex?readOnly=' + readOnly);
    }

    //线下还款-还款来源
    function showRepaySourceTab(readOnly) {
        showTab('tab_repaySource', '${ctx}/credit/gqgetComInfo/repaySourceIndex?readOnly=' + readOnly);
    }

    //线下还款-担保信息
    function showUnderGuarantorInfoTab(readOnly) {
        showTab('tab_underGuarantorInfo', '${ctx}/credit/guarantorInfoIndex/underGuarantorInfo?readOnly=' + readOnly);
    }
</script>