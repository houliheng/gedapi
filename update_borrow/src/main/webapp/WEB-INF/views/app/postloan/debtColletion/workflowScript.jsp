<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	//获取iframe的高度
	function calHeight(doc) {
		var ch = Math.max(doc.body.clientHeight, doc.documentElement.clientHeight);
		var sh = Math.max(doc.body.scrollHeight, doc.documentElement.scrollHeight);
		return Math.max(ch, sh);
	}
	//iframe加载时初始化高度
	function reSizeFrame(frameId) {
		var doc = window.frames[frameId].document;
		var height = calHeight(doc);
		$("#" + frameId).height(height + 80);
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
			var urlsuffix = "&applyNo=${plContract.applyNo}&contractNo=${contractNo}";
			url = url + urlsuffix;
			$("#" + tabId).html("<iframe style=\"min-height: 100px;\" id='frame_" + tabId + "' name='frame_" + tabId + "' src='" + url + "' width='100%' height='100%' frameborder='0' onload='reSizeFrame(\"frame_" + tabId + "\")'></iframe>");
		}
		return false;
	}
	//借款申请
	function showLoanApply(readOnly) {
		showTab('tab_loanApply', '${ctx}/postloan/debtCollectionDetailsPage/toCreLoanApplyPage?readOnly=' + readOnly);
	}
	//客户信息
	function showCustInfoTab(readOnly) {
		//showTab('tab_custInfo', '${ctx}/custinfo/custInfoIndex/index?readOnly=' + readOnly);
		showTab('tab_custInfo', '${ctx}/postloan/debtCollectionDetailsPage/toCreCustInfoPage?readOnly=' + readOnly);
	}
	//担保信息
	function showGuarantorInfoTab(readOnly) {
		//showTab('tab_guarantorInfo', '${ctx}/credit/guarantorInfoIndex/index?readOnly=' + readOnly);
		showTab('tab_guarantorInfo', '${ctx}/postloan/debtCollectionDetailsPage/toCreGuarantorInfoPage?readOnly=' + readOnly);
	}
	//抵质押物信息
	function showMortgageCarEvaluateInfoTab(readOnly) {
		//showTab('tab_mortgageCarInfo', '${ctx}/credit/mortgageCarInfo/mortgage?readOnly=' + readOnly);
		showTab('tab_mortgageCarInfo', '${ctx}/postloan/debtCollectionDetailsPage/toCreMortgageCarInfoPage?readOnly=' + readOnly);
	}
	//还款明细
	function showContractStatusTab(readOnly) {
		showTab('tab_ContractStatus', '${ctx}/postloan/debtCollectionDetailsPage/toAccDetailsPage?readOnly=' + readOnly);
	}

    //批量借款企业
    function showCompanyInfoTab(readOnly) {
        showTab('tab_companyInfo', '${ctx}/credit/companyInfo/index?readOnly=' + readOnly, null, forceFlag);
    }
</script>