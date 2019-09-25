<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" type="text/css"
          href="${ctxStatic}/applycount/css/base.css"/>
    <link rel="stylesheet" type="text/css"
          href="${ctxStatic}/applycount/css/jquery.date_input.pack.css"/>
    <script type="text/javascript"
            src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript"
            src="${ctxStatic}/applycount/js/jquery.date_input.pack.js"></script>

    <style type="text/css">
        .store-table {
            margin: 0 auto;
            width: 90%;
            text-align: center;
            border-top: 1px solid #cccccc;
            border-bottom: 1px solid #cccccc;
            border-left: 1px solid #cccccc;
            border-right: 1px solid #cccccc;
        }

        .store-table th, .store-table td {
            line-height: 40px;
        }

        .store-table th {
            background: #F2F2F2;
        }
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
    <li class="active">运营管理</li>

</ul>

<!--搜索条件  s-->
<div class="operation-search">
    <form:form id="searchForm" modelAttribute="applyOP"
               action="${ctx}/loan/apply/operationalStatistics" method="get"
               class="breadcrumb form-search">
        <input id="pageNo" name="pageNo" type="hidden" value="${repayCountPage.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${repayCountPage.pageSize}"/>

        <div class="operation-search-time">
            <%--<div class="operSearch l">
                <li><label>产品</label>
                    <form:select id="productId" path="productId" class="input-medium" onchange="changekoudai()">
                        <form:option value="">全部</form:option>
                        <form:option value="XJD">聚宝钱包</form:option>
                        <form:option value="KOUDAI">口袋放款</form:option>
                        <form:option value="XJDFQ">信用分期</form:option>
                    </form:select>
                </li>
            </div>--%>

            <div class="operSearch-date l">
                <label class="operSearch-tit">统计时间</label>
                <input type="text" value="${applyOP.applyStart}" id="applyStart" name="applyStart"
                       readonly="true"
                       class="operDate_picker border-radius3"
                       placeholder="年/月/日">
                <label>&nbsp;&nbsp;至&nbsp;&nbsp;</label>
                <input type="text" value="${applyOP.applyEnd}" id="applyEnd" name="applyEnd" readonly="true" class="operDate_picker1 border-radius3"
                       placeholder="年/月/日">
                <%--<div id="koudai" style="color: red;width: 200px;margin-left: 9px"></div>--%>
            </div>

            <div class="operSearch-time l">
                <a id="time1" href="javascript:void(0);" val="0">今天</a> <a
                    id="time2" href="javascript:void(0);" val="-1">昨天</a> <a
                    id="time3" href="javascript:void(0);" val="-6">最近7天</a> <a
                    id="time4" href="javascript:void(0);" val="-29">最近30天</a>
            </div>
            <div class="operSearch-btn r">
                <a href="javascript:void(0);" id="operSearch-btn"
                   class="border-radius3">搜索</a>
            </div>
            <input id="channel" name="channel" type="hidden" value="${applyOP.channel}"/>
            <input id="allChannel" name="allChannel" type="hidden" value="${allChannel}"/>
            <input id="checkStart" name="checkStart" type="hidden" value="${applyOP.checkStart}"/>
            <div class="clear"></div>
        </div>

    </form:form>
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

    <!--导出数据  s-->
        <%--<div class="daochu">
            <button id="btnExport" class="btn btn-white btn-sm" id="btnImport"
                    data-toggle="tooltip" data-placement="left" onclick="exportData();">
                <i class="fa fa-file-excel-o"></i> 导出
            </button>
        </div>--%>
</div>

<!-- 运营统计 code y20190729-->
<c:if test="${vo != null}">
    <div>
        <table class="store-table">
            <thead>
            <tr style="color: #000000;">
                    <%--<th>渠道</th>--%>
                <th rowspan="2">一推成功</th>
                <th rowspan="2">生成订单</th>
                <th rowspan="2">机审通过</th>
                <th style="line-height: 18px;" valign="bottom">审核通过</th>
                <th style="line-height: 18px;" valign="bottom">绑卡</th>
                <th style="line-height: 18px;" valign="bottom">放款</th>
                <th style="line-height: 18px;" valign="bottom">放款金额(万元)</th>
                <th rowspan="2">生成订单->机审</th>
                <th rowspan="2">信审总数->信审</th>
                <th rowspan="2">审核->绑卡</th>
                <th rowspan="2">一推->审核</th>
                <th rowspan="2">审核->放款</th>
                <th rowspan="2">生成订单->放款</th>
            </tr>
            <tr style="color: #000000;">
                <th style="line-height: 18px;" valign="top">自动审/信审</th>
                <th style="line-height: 18px;" valign="top">自动审/信审</th>
                <th style="line-height: 18px;" valign="top">自动审/信审</th>
                <th style="line-height: 18px;" valign="top">自动审/信审</th>
            </tr>
            </thead>
            <tbody>
            <tr style="color: #000000; text-align: center;">
                <td rowspan="2">${vo.totalReg}</td>
                <td rowspan="2">${vo.totalApply}</td>
                <td rowspan="2">${vo.applyAuto1Pass}</td>
                <td>${vo.applyPass}</td>
                <td>${vo.bindBank}</td>
                <td>${vo.withdrawCount}</td>
                <td>${vo.withdrawAmt}</td>
                <td rowspan="2"><fmt:formatNumber value="${vo.auto1PassApplyRate*100}" pattern="0.00"/>%</td>
                <td rowspan="2"><fmt:formatNumber value="${vo.manPassAuto1Rate*100}" pattern="0.00"/>%</td>
                <td rowspan="2"><fmt:formatNumber value="${vo.bindBankApplyPassRate*100}" pattern="0.00"/>%</td>
                <td rowspan="2"><fmt:formatNumber value="${vo.auto1PassRegRate*100}" pattern="0.00"/>%</td>
                <td rowspan="2"><fmt:formatNumber value="${vo.withdrawCountApplyPassRate*100}" pattern="0.00"/>%</td>
                <td rowspan="2"><fmt:formatNumber value="${vo.withdrawCountApplyRate*100}" pattern="0.00"/>%</td>
            </tr>
            <tr style="color: #000000; text-align: center;">
                <td>${vo.applyAuto2Pass}/${vo.applyPass}</td>
                <td>${vo.bindBankAuto2}/${vo.bindBank}</td>
                <td>${vo.withdrawCountAuto2}/${vo.withdrawCount}</td>
                <td>${vo.withdrawAmtAuto2}/${vo.withdrawAmt}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="pagination" style="width: 95%;" align="center">${repayCountPage}</div>
</c:if>


<script type="text/javascript">
    $(function () {

        //	统计时间插件
        jQuery.browser = {};
        (function () {
            jQuery.browser.msie = false;
            jQuery.browser.version = 0;
            if (navigator.userAgent.match(/MSIE ([0-9]+)./)) {
                jQuery.browser.msie = true;
                jQuery.browser.version = RegExp.$1;
            }
        })();
        $('.operDate_picker').date_input();
        $('.operDate_picker1').date_input();

        $(".selectable_day").click(function () {
            $(".operSearch-time a").removeClass("operTime-cur");
            $("#checkStart").val("");
        });

        //  搜索条件选中/未选中
        $(".operSearch-time a").click(function () {
            $(".operDate_picker").val("");
            $(".operDate_picker1").val("");
            $(".operSearch-time a").removeClass("operTime-cur");
            $(this).toggleClass("operTime-cur");
        });
        $(".operSearch-channel a").click(function () {
            $("#operChannel-all").removeClass("operChannel-cur");
            $(this).toggleClass("operChannel-cur");
        });
        $("#operChannel-all").click(function () {
            $(this).siblings().removeClass("operChannel-cur");
        });

        //选中时间按钮
        var checkStart = $("#checkStart").val();
        if (!checkStart) {
        } else if (checkStart == '0') {
            $('#time1').addClass('operTime-cur');
        } else if (checkStart == '-1') {
            $('#time2').addClass('operTime-cur');
        } else if (checkStart == '-6') {
            $('#time3').addClass('operTime-cur');
        } else if (checkStart == '-29') {
            $('#time4').addClass('operTime-cur');
        }

        //选中渠道按钮
        var channel = $("#channel").val();
        var allChannel = $("#allChannel").val();
        // 	alert(channel);
        // 	alert(allChannel);
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
                        // 					alert(strs[i]);
                        // 					alert(allchannelstrs[j]);
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
        var post = "";
        var Channel = "";
        var arr = new Array();

        $(".operChannel-cur").each(function (k, v) {
            Channel += $(this).attr("value") + ","
        })
        Channel = Channel.substring(0, Channel.length - 1);

        arr.push("applyStart=" + $(".operDate_picker").val());
        arr.push("&applyEnd=" + $(".operDate_picker1").val());
        arr.push("&checkStart=" + $(".operTime-cur").attr("val") + "");
        arr.push("&channel=" + Channel);
        post += arr.join("");
        // 	alert($(".operTime-cur").attr("val"));

        $("#applyStart").attr("value", $(".operDate_picker").val());
        $("#applyEnd").attr("value", $(".operDate_picker1").val());
        $("#checkStart").attr("value", $(".operTime-cur").attr("val"));
        $("#channel").attr("value", Channel);

    }

    $("#operSearch-btn").click(function () {
        operSearch();
        $("#searchForm").submit();
        return false;
    });

    function exportData() {
        operSearch();
        return exportExcel('${ctx}/loan/apply/export',
            '${ctx}/loan/apply/applyCount');
    }

</script>

</body>
</html>