<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        /*自适应圆角投影结束*/
        .zxx_zoom_left{width:100%; float:left; margin-top:20px;}
        .zxx_zoom_left h4{margin:5px 0px 15px 5px; font-size:1.1em;text-align: center;}
        .small_pic{display:inline-block; width:19%; height:150px; text-align:center; *display:inline; zoom:1; vertical-align:middle;}
        .small_pic img{padding:3px; background:#ffffff; border:1px solid #cccccc; vertical-align:middle;}
    </style>
    <script type="text/javascript" src="${ctxStatic}/common/filelook/js/jquery-1.2.6.pack.js"></script>
    <script type="text/javascript" src="${ctxStatic}/common/filelook/js/content_zoom.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("div.small_pic a").fancyZoom({scaleImg: true, closeOnClick: true});
        });
    </script>
</head>



<body>
<div class="zxx_out_box">
    <div class="zxx_in_box">
        <div class="zxx_header">
            <div class="zxx_main_con fix mb20">
                <div class="zxx_zoom_left">
                    <div class="small_pic">
                        <span>身份证正面</span>
                        <a href="#pic_one">
                            <img src="${idCardFaceUrl}" />
                        </a>
                    </div>
                    <div class="small_pic">
                        <span>身份证反面</span>
                        <a href="#pic_two">
                            <img src="${idCardBackUrl}" />
                        </a>
                    </div>
                    <div class="small_pic">
                        <span>手持身份证</span>
                        <a href="#pic_three">
                            <img src="${idCardHoldUrl}" />
                        </a>
                    </div>
                    <div class="small_pic">
                        <span>营业执照图片</span>
                        <a href="#pic_four">
                            <img src="${businessLicenceUrl}" />
                        </a>
                    </div>
                    <div class="small_pic">
                        <span>开户许可证</span>
                        <a href="#pic_five">
                            <img src="${accountsPermitsUrl}" />
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
    <!-- 要放大显示的div -->
    <div id="pic_one" style="display:none;"><img src="${idCardFaceUrl}"/></div>
    <div id="pic_two" style="display:none;"><img src="${idCardBackUrl}" /></div>
    <div id="pic_three" style="display:none;"><img src="${idCardHoldUrl}"/></div>
    <div id="pic_four" style="display:none;"><img src="${businessLicenceUrl}"/></div>
    <div id="pic_five" style="display:none;"><img src="${accountsPermitsUrl}"/></div>
</body>
</html>

