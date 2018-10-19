<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link href="/assets/style.css" rel="stylesheet">
</head>
<body>
<div class="box">
    <header class="h_box">
        <div class="ph_box ov-hid">
            <div class="c_box ">
                <span>4000-652-6818[出借]</span>
                <span>4000-833-5050[借款]</span>
                <!-- <em>-我要借款</em> -->
            </div>
        </div>

    </header>
    <section>
        <#if resForm.resp_code == "0000">
            <div class="pay_box" style="display:;">
                <div class="f_bg ov-hid pay_bank_tip p_box ">
                    <div class="suc_box">
                        <h5 class="suc_tip"><img src="/assets/suc_icon.jpg" alt width="78" height="78"> 充值成功</h5>
                        <p class="suc_tip_info"><strong>充值金额${data.amount}元</strong> </p>
                        <!-- <div class="suc_info_list">
                            <p>订单号：3345457567563423434</p>
                            <p>支付方式：网银支付</p>
                            <p>支付类型：费用</p>
                        </div> -->
                        <p>
                            <input class="suc_btn c_blue" type="button" onclick="window.location.href='http://10.100.32.125:8080/sponsor'" value="返回">
                        </p><p class="suc_tip_info f_s">详细请咨询客服热线：400-833-5050</p>
                    </div>
                </div>
            </div>
        <#else>
            <div class="pay_box" style="display:block;">
                <div class="f_bg ov-hid pay_bank_tip p_box ">
                    <div class="suc_box">
                        <h5 class="suc_tip"><img src="/assets/d_faild.jpg" alt width="78" height="78"> 对不起，充值失败！</h5>
                        <!-- <p class="suc_tip_info"><em>提现失败原因，{{exception}}</em> </p> -->

                        <p>
                            <input class="suc_btn c_blue" type="button" onclick="window.location.href='${gedh5url}/orderlist/payment'" value="重新充值">
                        </p><p class="suc_tip_info f_s">详细请咨询客服热线：400-833-5050</p>
                    </div>
                </div>
            </div>
        </#if>
    </section>
    <footer class="f_box">
        <div class="f_help_list f_bg">
            <div class="c_box help_list">
                <ul>
                    <li>
                        <h5>公司概况</h5>
                        <a href="https://www.gqget.com/company/introduction" target="_blank">公司介绍</a>
                        <a href="https://www.gqget.com/company/honor" target="_blank">荣誉资质</a>
                        <a href="https://www.gqget.com/company/partners" target="_blank">合作机构</a>
                        <a href="https://www.gqget.com/company/econtact" target="_blank">联系我们</a>
                    </li>
                    <li>
                        <h5>网贷课堂</h5>
                        <a href="https://www.gqget.com/company/recommended" target="_blank">推荐阅读</a>

                        <a href="https://www.gqget.com/company/media" target="_blank">媒体报道</a>

                        <a href="https://www.gqget.com/company/financial1" target="_blank">网贷百科</a>
                        <a href="https://www.gqget.com/company/regulation" target="_blank">政策法规</a>
                    </li>
                    <li>
                        <h5>帮助中心</h5>
                        <a href="https://www.gqget.com/help/detail116" target="_blank">新手必读</a>

                        <a href="https://www.gqget.com/company/feescale" target="_blank">收费标准</a>

                        <a href="https://www.gqget.com/company/notice" target="_blank">平台公告</a>

                        <a href="https://www.gqget.com/sitemap" target="_blank">网站地图</a>
                    </li>

                    <li>
                        <h5>客服电话</h5>
                        <p class="f_red_last">
                            400-652-6818 <em>[出借]</em>
                        </p>
                        <p class="f_red_last">
                            400-833-5050 <em>[借款]</em>
                        </p>
                        <p class="f_time">周一至周日 9：00-21：00</p>
                    </li>
                </ul>
            </div>
        </div>
        <div class="f_copy f_bg">
            <p>2015冠群驰骋 All rights reserved | 冠群驰骋投资管理(北京)有限公司 | 京ICP备11025014号-2 | <a href="https://www.gqget.com/icpPage" target="_blank" style="color:black;">京ICP证140545号</a> | <a href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11010102002306" target="_blank" style="color:black;">
                <img class="pr5" src="https://www.gqget.com/images/icon_ga.png">京公网安备 11010102002306号</a></p>
            <p>页面中名称仅为不同出借模式的标示，非理财产品；历史年化收益率仅供参考，不代表未来实际收益。出借有风险，选择需谨慎。</p>
        </div>
        <div class="f_logo_icon f_bg">
            <!--<img src="../../assets/f_l_icon1.jpg" alt="">
                <img src="../../assets/f_l_icon2.jpg" alt="">
                <img src="../../assets/f_l_icon3.jpg" alt="">
                <img src="../../assets/f_l_icon4.jpg" alt="">
                <img src="../../assets/f_l_icon5.jpg" alt="">-->
            <ul class="mt20 pb10 tc" id="szfw_logo">
                <li><a href="https://ss.knet.cn/verifyseal.dll?sn=e18011811010671538fxbn000000&ct=df&a=1&pa=0.07775086907059792" rel="nofollow"></a></li>
                <li><a id="___szfw_logo___" href="https://credit.szfw.org/CX20150812010705580368.html" rel="nofollow" style="background:none;" target="_blank"><img src="https://www.gqget.com/images/cert.png" alt="诚信网站" style="position:relative;top:-12px;" border="0"></a></li>
                <li><a class="ico4" href="http://www.wdzj.com/" rel="nofollow" target="_blank"></a></li>

            </ul>
        </div>
    </footer>
</div>
</body>
</html>