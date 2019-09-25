<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>借款人列表</title>
    <style type="text/css">
        .upload-popBg {
            display: none;
            position: fixed;
            z-index: 9919891016;
            left: 0;
            top: 0;
            right: 0;
            bottom: 0;
            width: 100%;
            height: 100%;
            opacity: 0.5;
            filter: alpha(opacity=50);
            -moz-opacity: .5;
            background: #000;
        }

        .upload-popBg img {
            display: block;
            z-index: 9919891019;
            position: fixed;
            left: 50%;
            top: 50%;
            -webkit-transform: translate(-50%, -50%);
            -moz-transform: translate(-50%, -50%);
            -o-transform: translate(-50%, -50%);
            -ms-transform: translate(-50%, -50%);
            transform: translate(-50%, -50%);
            overflow: hidden;
        }

        .upDateBtn {
            display: inline-block;
            padding: 0 12px;
            line-height: 30px;
            color: #ffffff;
            background: #3daae9;
            border: 1px solid #ccc;
            cursor: pointer;
        }
    </style>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        };

        function openTabPage(url) {
            if (cookie('tabmode') == '1') {
                addTabPage('借款人详情', url, null);
            } else {
                window.location.href = url;
            }
        };
    </script>
    <script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
    <script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js" type="text/javascript"></script>
    <script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>
    <style type="text/css">
        .l{float: left;}
        .operSearch-tit{ display:inline-block; width:70px; height:24px; line-height:24px; font-size:14px; padding-left: 50px}
        .operSearch-btn{ display:inline-block;}
        .operSearch-btn a, .daochu-btn{ display:inline-block; padding:0 15px; height:30px; line-height:30px; font-size:14px; color:#ffffff; background:#2fa4e7;}
        .operSearch-channel{ width:90%;}
        .operSearch-channel a{ display:inline-block; margin:0 10px 10px 0; padding:0 10px; height:22px; line-height:22px; color:#333333; font-size:14px; border:1px solid #bbbbbb; -moz-border-radius:3px; -webkit-border-radius:3px; -ms-border-radius:3px; border-radius:3px;}
        .operSearch-channel a.operChannel-cur{ color:#2fa4e7; border:1px solid #2fa4e7;}
        .daochu{ padding:10px 0; height:30px; text-align:right;}
        .daochu-btn{ background:#f93500;}
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/custUser/custuserList">借款人列表</a></li>
</ul>
<!--BEGIN TITLE & BREADCRUMB PAGE-->
<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
    <div class="pull-left">
        <ol class="breadcrumb page-breadcrumb">
            <li><i class="fa fa-home"></i>&nbsp;<a href="#">客户管理</a></li>
            <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;借款人列表</li>
        </ol>
    </div>
    <div class="clearfix"></div>
</div>
<!--END TITLE & BREADCRUMB PAGE-->

<form:form id="searchForm" modelAttribute="borrowerOP" action="${ctx}/sys/custUser/custuserList" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>姓名</label>
            <form:input path="realName" htmlEscape="false" maxlength="20" class="input-medium"/>
        </li>
        <li><label>手机号码</label>
            <form:input path="mobile" htmlEscape="false" maxlength="11" class="input-medium"/>
        </li>
        <li><label>身份证号</label>
            <form:input path="idNo" htmlEscape="false" maxlength="25" class="input-medium"/>
        </li>
        <li class="time_li"><label>注册时间</label>
            <form:input path="registerStart" type="text" readonly="readonly" maxlength="20" class="input-middle Wdate"
                        value="${borrowerOP.registerStart}"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>

            <h>-</h>
            <form:input path="registerEnd" type="text" readonly="readonly" maxlength="20" class="input-middle Wdate"
                        value="${borrowerOP.registerEnd}"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
        </li>
        <input id="channel" name="channel" type="hidden" value="${borrowerOP.channel}" />
        <input id="allChannel" name="allChannel" type="hidden" value="${allChannel}" />

        <div class="operSearch-btn r">
            <a href="javascript:void(0);" id="operSearch-btn"
               class="border-radius3">搜索</a>
        </div>
        <li class="clearfix"></li>
    </ul>
    <ul class="ul-form">
        <div class="operation-search-channel">
            <label class="operSearch-tit l">渠道</label>
            <div class="operSearch-channel">
                <a id="operChannel-all" href="javascript:void(0);"
                   value="${allChannel}">全部</a>
                <c:forEach items="${channels}" var="ch" varStatus="xh">
                    <a id="channel_${xh.index}" href="javascript:void(0);"
                       value="'${ch.cid}'">${ch.cName}</a>
                </c:forEach>
                <div class="clear"></div>

            </div>
            <div class="clear"></div>
        </div>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table">
    <thead>
    <tr>
        <th>序号</th>
        <th>姓名</th>
        <th>性别</th>
        <th>手机号码</th>
        <th>证件号码</th>
        <th>渠道</th>
        <th>银行名称</th>
        <th>银行卡号</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="custUser" varStatus="xh">
        <tr>
            <td>
                    ${xh.count}
            </td>
            <td>
                    ${custUser.realName}
            </td>
            <td>
                    ${custUser.sexStr}
            </td>
            <td>
                    ${custUser.mobile}
            </td>
            <td>
                    ${custUser.idNo}
            </td>
            <td>
                <c:forEach items="${channels}" var="detail">
                    <c:if test="${custUser.channel == detail.cid}">
                        ${detail.cName}
                    </c:if>
                </c:forEach>
            </td>
            <td>${custUser.bankName}</td>
            <td>${custUser.cardNo}</td>
            <td>
                <fmt:formatDate value="${custUser.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                <c:if test="${(custUser.sexStr != null and custUser.sexStr != '')}">
                    <a href="javascript:void(0)"
                       onclick="openTabPage('${ctx}/sys/custUser/custuserDetail?id=${custUser.id}')">详情</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
<div class="upload-popBg"><img src="${ctxStatic}/images/upload.gif"/></div>
<script type="text/javascript">
    $(function() {
        //  搜索条件选中/未选中
        $(".operSearch-channel a").click(function() {
            $("#operChannel-all").removeClass("operChannel-cur");
            $(this).toggleClass("operChannel-cur");
        });
        $("#operChannel-all").click(function() {
            $(this).siblings().removeClass("operChannel-cur");
        });

        //选中渠道按钮
        var channel = $("#channel").val();
        var allChannel = $("#allChannel").val();
        if (channel) {
            var strs = new Array(); //定义一数组
            strs = channel.split(","); //字符分割
            var allchannelstrs = new Array(); //定义一数组
            allchannelstrs = allChannel.split(","); //字符分割
            if (allChannel == channel) {
                $('#operChannel-all').addClass('operChannel-cur');
            } else {
                for (var i = 0; i < strs.length; i++) {
                    for (var j = 0; j < allchannelstrs.length; j++) {
                        if (strs[i] == allchannelstrs[j]) {
                            $('#channel_' + j).addClass('operChannel-cur');
                        }
                    }

                }
            }
        }
    });

    //按条件搜索
    function operSearch() {
        var channel = "";
        $(".operChannel-cur").each(function(k, v) {
            channel += $(this).attr("value") + ","
        })
        channel = channel.substring(0, channel.length - 1);
        $("#channel").attr("value", channel);
    };

    $("#operSearch-btn").click(function() {
        operSearch();
        $("#searchForm").submit();
        return false;
    });
</script>
</body>
</html>



