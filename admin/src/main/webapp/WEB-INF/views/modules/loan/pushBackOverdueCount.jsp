<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>

    <title>催收回款率统计</title>
    <meta name="decorator" content="default"/>

    <link rel="stylesheet" href="${ctxStatic}/layui-master/build/css/layui.css">
    <link href="${ctxStatic}/position/basic.css" rel="stylesheet"/>
    <link href="${ctxStatic}/position/hjd-sh.css" rel="stylesheet"/>

    <%--<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>--%>
    <script src="${ctxStatic}/layui-master/src/layui.js"></script>
    <script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>

    <script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js" type="text/javascript"></script>
    <script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery-validation/1.11.1/jquery.metadata.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.js" type="text/javascript"></script>
    <style type="text/css">
        .form-search .ul-form li .error {
            width: auto !important;
        }

        #collection {
            width: 1051px;
            height: 791px;
            border: 1px solid rgba(221, 221, 221, 1);
            position: relative;
        }

        #collections {
            height: 37px;
            line-height: 37px;
            background: rgba(242, 242, 242, 1);
        }

        .collection {
            margin: 100px auto 0;
            width: 867px;
            height: 377px;
            border: 1px solid #cccccc;
            overflow: hidden;
            position: relative;
        }

        .collections {
            width: 839px;
            height: 330px;
            background-color: rgba(236, 246, 251, 1);
            margin-top: 23px;
            margin-left: 13px;
        }

        .clears:after {
            content: "";
            display: block;
            clear: both;
        }

        .collections-top {
            height: 36px;
            line-height: 36px;
            font-size: 14px;
            width: 820px;
            margin: 0 auto;
            border-bottom: 1px solid #cccccc;
        }

        .collections-top div, .collections-con div {
            float: left;
            width: 200px;
        }

        .collections-con {
            width: 800px;
            height: 278px;
            overflow-y: auto;
        }

        .collections-top em {
            color: #FF0000;
        }

        .collections-con div {
            font-size: 12px;
            color: #666;
            text-indent: 10px;
            height: 40px;
            line-height: 40px;
        }

        .collections-con div input {
            margin-right: 15px;
            margin-top: 3px;
        }

        .collections-con .collections-cons {
            width: 500px;
        }

        .collection select {
            width: 152px;
            height: 22px;
            font-size: 12px;
        }

        .collections-name, .collections-time {
            position: absolute;
            left: 422px;
            top: 68px;
        }

        .collections-time {
            left: 621px;
        }

        .collection-bot {
            width: 328px;
            margin: 28px auto 0;
        }

        .collection-bot-left, .collection-bot-right {
            width: 164px;
            height: 45px;
            text-align: center;
            line-height: 45px;
            cursor: pointer;
            color: #fff;
            background: rgba(25, 158, 216, 1);
            border-radius: 6px;
            float: left;
        }

        .collection-bot-right {
            background: #ffffff;
            color: #333;
        }

        a {
            color: #2fa4e7;
            text-decoration: none;
        }

        #menu {
            font-size: 18px;
            font-weight: bold;
        }

        #menu li {
            text-decoration: none;
            list-style: none;
            display: inline-block;
            float: left;
            padding-left: 10px;
        }
    </style>
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
    <script type="text/javascript">
        $(document).ready(function () {

        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>

</head>


<body>

<ul class="nav nav-tabs">
    <li class="active">
        <a href="${ctx}/loan/collection/pushBackCount">催收回款率统计</a>
    </li>
</ul>

<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
    <div class="pull-left">
        <ol class="breadcrumb page-breadcrumb">
            <li><i class="fa fa-home"></i>&nbsp;<a href="#">运营管理</a></li>
            <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;催收回款率统计</li>
        </ol>
    </div>
    <div class="clearfix"></div>
</div>
<!--END TITLE & BREADCRUMB PAGE-->

<form:form id="searchForm" modelAttribute="op" action="${ctx}/loan/collection/pushBackCount" method="post"
           class="breadcrumb form-search layui-form">


    <ul class="ul-form">

        <li><label>申请期限</label>
            <form:select path="termType" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="1">8天</form:option>
                <form:option value="4">28天</form:option>
            </form:select>
        </li>
        <li><label>是否复贷</label>
            <form:select path="loanSucess" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="yes">是</form:option>
                <form:option value="no">否</form:option>
            </form:select>
        </li>
        <li class="time_li"><label>应还日期</label>

            <form:input path="startTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                        value="${op.startTime }"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd',startTime:'%y-%M-%d',isShowClear:false,isShowToday:true,isShowClear:true});"/>

            <h>-</h>
            <form:input path="endTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                        value="${op.endTime }"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd',startDate:'%y-%M-%d',isShowClear:false,isShowToday:true});"/>
        </li class="time_li">
        <input id="channel" name="channelId" type="hidden" value="${op.channelId}" />
        <input id="allChannel" name="allChannel" type="hidden" value="${allChannel}" />
        <li class="operSearch-btn r">
            <a href="javascript:void(0);" id="operSearch-btn"
               class="border-radius3">搜索</a>
        </li>
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

<table id="contentTable" class="table">
    <thead>
    <tr>
        <th>催收人</th>
        <th>分单数</th>
        <th>总回单数</th>
        <th>总回收率</th>
        <th>老案件回收</th>
        <th>新单回收</th>
        <th>新单回收率</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="item" varStatus="xh">
        <tr>
            <td>${item.userName}</td>
            <td>${item.allSettlement+item.allOutstanding}</td>
            <td>${item.allSettlement}</td>
            <td>
                <fmt:formatNumber type="number"
                                  value="${item.allSettlement * 100 / (item.allSettlement+item.allOutstanding)}"
                                  pattern="0.00"
                                  maxFractionDigits="2"/>%
            </td>
            <td>${item.allSettlement -item.todaySettlement }</td>
            <td>${item.todaySettlement }</td>
            <td>

                <c:if test="${item.todaySettlement == 0 }">
                    0%
                </c:if>
                <c:if test="${item.todaySettlement != 0 }">
                    <fmt:formatNumber type="number"
                                      value="${item.todaySettlement * 100 / (item.todaySettlement+item.todayOutstanding)}"
                                      pattern="0.00"
                                      maxFractionDigits="2"/>%
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<script>
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

    function issue(applyId) {
        layer.confirm('确认发放旅游券吗？', {
            btn: ['提交', '取消']
        }, function (index) {
            issueConfirm(applyId);
            layer.close(index);
        });
    }

    function issueConfirm(applyId) {
        var param = {
            applyId: applyId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/loanTrip/issueTicket",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    location.reload(true);
                } else {
                    alert(data.msg)
                }
            },

        });
    };


    layui.use(['form', 'element'], function () {
        var $ = layui.jquery, element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块

        var form = layui.form()
    });
</script>
</body>

</html>