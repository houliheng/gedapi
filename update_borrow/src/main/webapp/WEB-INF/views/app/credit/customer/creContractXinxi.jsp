<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
    <title>创建冠易贷账号</title>
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
        //提交
        function formSubmit(){
            loading();
            $("#searchForm").submit();

        }
        //重置
        function formReset(){
            //$("#searchForm").get(0).reset();
            $("#contractNo").val("");

            $("#repayContractStatus").find("option").remove();
            $("#borrowName").val("");
            $("#approProductName").val("");
            $("#approProductName").select2("val","");
        }


        function delOper(){
            var $checkLine =  $("input[name='type']:checked");
            if(null!=$checkLine && $checkLine.length>0){
                var ids = "";
                $checkLine.each(function(v){
                    ids+=(this.value+",");
                });
                $.post("${ctx}/credit/mortgagedperson/delete",{"ids":ids},function(data){
                    if("success"==data){
                        alertx("删除成功！");
                        setTimeout(function(){
                            formSubmit();
                        }, 1200);
                    }
                });
            }
        }
        $(document).ready(function() {
            var tds = $(".title").filter("td");
            $.each(tds,function(){
                $(this).attr("title",$.trim($(this).text()));
            });
        });
        //推送订单
        function saveGedApi(contractNo,applyNo,repayContractStatus,isFlag,guanyiFlag,custName,custId) {
            var url = "${ctx}/credit/mortgagedperson/saveGedApi";
            var pageNo = '${page.pageNo}';
            var pageSize = '${page.pageSize}';
            loading('正在推送订单，请稍等...');
            $.ajax({
                 url:"${ctx}/credit/mortgagedperson/saveGedApi",
                 data:{
                     contractNo:contractNo,
                     applyNo:applyNo,
                     repayContractStatus:repayContractStatus,
                     isFlag:isFlag,
                     guanyiFlag:guanyiFlag,
                     custName:custName,
                     custId:custId

                 },
                 type:"post",
                 success:function (data) {
                   if(data.status == '1'){
                       closeTip();
                       alertx(data.message)

                       $("#searchForm").submit();
                   }else{
                       alertx(data.message)
                       closeTip();
                   }
                 },
                 error:function (data) {
                     alertx("系统繁忙,请稍后重试");

                  }
             })

        }

        //创建冠易贷账号
        function createGedAccount111(borrowName,unSocCreditNo,custName,mobileNum,corporaTionMobile,isFlag,borrowType,applyNo,custId,recBankcareNo){

            var width = $(window).width() - 300;
            var url = "${ctx}/credit/mortgagedperson/saveGedApiXinxi?borrowName=" + borrowName +"&&unSocCreditNo="+unSocCreditNo +"&&custName="+custName+"&&mobileNum="+mobileNum+"&&corporaTionMobile="+corporaTionMobile+"&&isFlag="+isFlag+"&&borrowType="+borrowType+"&&applyNo="+applyNo+"&&custId="+custId+"&&recBankcareNo="+recBankcareNo;
            openJBox("main-list", url, "创建冠易贷账号", width, 250, null);
        }

    </script>
</head>

<body>
<div class="wrapper">
    <div class="searchInfo">
        <h3 class="searchTitle">查询条件</h3>
        <div class="searchCon">
            <form:form id="searchForm" modelAttribute="mortgagedPerson"
                       action="${ctx}/credit/mortgagedperson/queryContract" method="post" class="breadcrumb form-search">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <div class="filter">
                    <table class="searchTable">
                        <tr>
                            <td class="ft_label">合同编号</td>
                            <td class="ft_content">
                                <input id="contractNo" name="contractNo" type="text" maxlength="50" class="input-medium"  />
                            </td>

                            <td class="ft_label">借款状态</td>
                            <td class="ft_content">

                                 <select id="repayContractStatus" name="repayContractStatus" class="input-medium">
                                     <option value=""></option>
                                     <option value="0600">无逾期-还款中</option>
                                     <option value="0800">有逾期-还款中</option>
                                 </select>
                            </td>

                        </tr>
                        <tr>
                            <td class="ft_label">企业名称</td>
                            <td class="ft_content">
                                <input id="borrowName" name="borrowName" type="text" maxlength="50" class="input-medium"  />
                            </td>
                            <td class="ft_label">产品名称</td>
                            <td class="ft_content">
                                 <input id="approProductName" name="approProductName" type="text" maxlength="50" class="input-medium"  />

                            </td>
                        </tr>
                    </table>
                    <div class="searchButton">
                        <input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="formSubmit()"/>
                        <input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="formReset()"/>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
    <div class="ribbon">

    </div>
    <sys:message content="${message}"/>
    <div class="tableList">
        <h3 class="tableTitle">数据列表</h3>
        <div id="tableDataId" style="max-height:400px;overflow:auto;">
            <table   class="table table-striped table-bordered table-condensed">
                <tr>
                    <th width="3%">序号</th>
                    <th width="15%">合同编号</th>
                    <th width="7%">产品名称</th>
                    <th width="10%">借款人名称</th>
                    <th width="7%">企业类型</th>
                    <th width="15%">社会统一信用代码</th>
                    <th width="10%">主借人</th>
                    <th width="10%">主借人手机号</th>
                    <th width="10%">法人代表</th>
                    <th width="10%">法人代表手机号</th>
                    <th width="10%">借款状态</th>
                    <th width="15%">冠易贷账号</th>
                    <th width="10%">操作</th>
                </tr>

                <c:forEach items="${page.list}" var="product" varStatus="index">
                    <td title="title">${index.count}</td>
                    <td class="title">${product.contractNo} </td>
                    <td title="${product.approProductName}"  class="title">${product.approProductName} </td>
                    <td title="${product.borrowName}"  class="title">${product.borrowName}  </td>
                    <td title="${fns:getDictLabel(product.borrowType, 'ROLE_TYPE', '')}"  class="title">${fns:getDictLabel(product.borrowType, 'ROLE_TYPE', '')}</td>
                    <td title="${product.unSocCreditNo}"class="title">${product.unSocCreditNo} </td>
                    <c:choose>
                        <c:when test="${product.borrowType!= 6}">
                            <td title="${product.custName}" class="title">${product.custName} </td>
                            <td title="${product.mobileNum}"  class="title">${product.mobileNum} </td>
                        </c:when>
                          <c:otherwise>
                              <td></td>
                              <td></td>
                          </c:otherwise>
                    </c:choose>

                    <td title="${product.corporaTionName}"  class="title">${product.corporaTionName} </td>
                    <td title="${product.corporaTionMobile}"  class="title">${product.corporaTionMobile}  </td>
                    <td title="${fns:getDictLabel(product.repayContractStatus, 'CONTRACT_STATE', '')}"  class="title"> ${fns:getDictLabel(product.repayContractStatus, 'CONTRACT_STATE', '')} </td>

                    <td title="${product.guanyiFlag}"  class="title">${product.guanyiFlag} </td>
                    <td>
                     <c:choose>
                         <c:when test="${ empty product.guanyiFlag}" >
                             <a href="javascript:void(0);" onclick="createGedAccount111('${product.borrowName}','${product.unSocCreditNo}','${product.custName}','${product.mobileNum}','${product.corporaTionMobile}','${product.isFlag}','${product.borrowType}','${product.applyNo}','${product.custId}','${product.recBankcareNo}');">创建冠易贷账号</a>
                         </c:when>

                             <c:when test="${product.gedApiSave == 0 }">
                                 <a href="javascript:void(0);" onclick="saveGedApi('${product.contractNo}','${product.applyNo}','${product.repayContractStatus}','${product.isFlag}','${product.guanyiFlag}','${product.custName}','${product.custId}');">推送订单</a>
                             </c:when>
                         <c:otherwise>
                             <b>订单已推送</b>
                         </c:otherwise>
                     </c:choose>
                </td>
                </tr>
                </c:forEach>
            </table>
        </div>
        <div class="pagination">${page}</div>
    </div>
</body>
</html>

