<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {

            $(".unSocCreditNoShow").hide();
            $(".corporationMobileShow").hide();
            $(".mobileNumShow").hide();
            if('${roleType}'!='5'){
                $("#registerChoose").hide();
            }else{
                $(".mobileNumShow").show();
            }
            //$("#name").focus();
           $("#inputForm").validate({
                submitHandler: function(form){
                       loading('正在注册，请稍等...');
                    var param = $("#inputForm").serialize();

              $.post("${ctx}/credit/mortgagedperson/saveCompanyGedapi", param, function(data) {
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
        });
        function chooseAccountList(status){
            if(status=='1'){
                $(".unSocCreditNoShow").hide();
                $(".corporationMobileShow").hide();
                $(".mobileNumShow").show();
            }
            if(status=='2'){
                $(".unSocCreditNoShow").hide();
                $(".corporationMobileShow").show();
                $(".mobileNumShow").hide();
            }
            if(status=='3'){
                $(".unSocCreditNoShow").show();
                $(".corporationMobileShow").hide();
                $(".mobileNumShow").hide();
            }
        }
    </script>

</head>
<body>
<form id="inputForm" action="#" method="post" class="form-horizontal">
    <table class="fromTable filter">
        <tr>
            <td class="ft_label">企业名称：</td>
            <td class="ft_content">
                <input type="text" readonly="readonly" id="borrowName" name="borrowName" value="${borrowName}"  class="required" />
                <input type="hidden"  id="unSocCreditNo" name="unSocCreditNo" value="${unSocCreditNo}"/>
                <input type="hidden"  id="corporaTionMobile" name="corporaTionMobile" value="${corporaTionMobile}"/>
                <input type="hidden"  id="mobileNum" name="mobileNum" value="${mobileNum}"/>
                <input type="hidden"  id="borrowType" name="borrowType" value="${borrowType}"/>
                <input type="hidden"  id="applyNo" name="applyNo" value="${applyNo}"/>
                <input type="hidden"  id="custId" name="custId" value="${custId}"/>
                <input type="hidden"  id="recBankcareNo" name="recBankcareNo" value="${recBankcareNo}"/>

                <font color="red">*</font>
            </td>
        </tr>

        <tr>
            <td class="ft_label"></td>
            <c:if test="${borrowType != 6}">
            <td id="redioId" class="ft_content"   colspan="7">
                <input type="radio" name="accountType"   value="1" id="radio_1" class="required" onclick="chooseAccountList(1)">
                <label for="radio_yes">主借人手机号</label>&nbsp;&nbsp;
            </td>
            </c:if>
            <td id="redioId" class="ft_content"  colspan="7">
                <input type="radio" name="accountType" value="2" id="radio_2" class="required" onclick="chooseAccountList(2)">
                <label for="radio_no">法人代表手机号</label>&nbsp;&nbsp;
            </td>
        </tr>
        <tr>
            <td class="ft_label"></td>
            <td id="redioId" class="ft_content"   colspan="7">
                <input type="radio" name="accountType" value="3" id="radio_3" class="required" onclick="chooseAccountList(3)">
                <label for="radio_no">统一社会信用代码</label>&nbsp;&nbsp;
            </td>
        </tr>
        <tr>
            <td class="ft_label">冠易贷账号 ：</td>
            <td class="ft_content">
                <input type="text" readonly="readonly"   value="${unSocCreditNo}" class="unSocCreditNoShow" />
                <input type="text" readonly="readonly"   value="${corporaTionMobile}" class="corporationMobileShow"/>
                <input type="text" readonly="readonly"   value="${mobileNum}" class="mobileNumShow"/>
            </td>
        </tr>
    </table>
    <div class="searchButton" id="buttonDiv">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
    </div>
</form>
</body>
</html>