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
            border-left: 1px solid #cccccc;
        }

        .store-table th, .store-table td {
            line-height: 40px;
            border-bottom: 1px solid #cccccc;
            border-right: 1px solid #cccccc;
        }

        label {
            margin-right: 15px;
        }

        .input-medium {
            width: 15%;
            margin-right: 5%;
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
<!--搜索条件  s-->
<div class="operation-search">
    <form:form id="searchForm" modelAttribute="applyOP"
               action="${ctx}/loan/apply/approverRepayCount" method="get"
               class="breadcrumb form-search">
        <input id="pageNo" name="pageNo" type="hidden"
               value="${approverRepayCount.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden"
               value="${approverRepayCount.pageSize}"/>
        <div class="operation-search-time">
            <div class="operSearch-date l">
                <label class="operSearch-tit">放款时间</label>
                <input type="text" value="${applyOP.applyStart}" id="applyStart" name="applyStart" readonly="true" class="operDate_picker border-radius3" placeholder="年/月/日">
                <label>&nbsp;&nbsp;至&nbsp;&nbsp;</label>
                <input type="text" value="${applyOP.applyEnd}" id="applyEnd"
                       name="applyEnd" readonly="true"
                       class="operDate_picker1 border-radius3" placeholder="年/月/日">
            </div>
            <div class="operSearch-time l">
                <a id="time1" href="javascript:void(0);" val="0">今天</a> <a
                    id="time2" href="javascript:void(0);" val="-1">昨天</a> <a
                    id="time3" href="javascript:void(0);" val="-6">最近7天</a> <a
                    id="time4" href="javascript:void(0);" val="-29">最近30天</a>
            </div>
        </div>
        <div style="padding-top: 30px">
            <label>申请期限</label>
            <form:select path="termType" class="input-medium">
                <form:option value="one">8天</form:option>
                <form:option value="fore">28天</form:option>
            </form:select>

            <label>宜信等级</label>
            <form:select path="compositeScore" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="A">A</form:option>
                <form:option value="B">B</form:option>
                <form:option value="C">C</form:option>
                <form:option value="D">D</form:option>
                <form:option value="E">E</form:option>
            </form:select>

            <label>信审员</label>
            <form:select path="auditor" class="input-medium">
                <form:option value="">全部</form:option>
                <c:forEach items="${auditor}" var="detail">
                    <c:choose>
                        <c:when test="${applyOP.auditor == detail.id}">
                            <form:option value="${detail.id}" selected="selected">${detail.name}</form:option>
                        </c:when>
                        <c:otherwise>
                            <form:option value="${detail.id}">${detail.name}</form:option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </form:select>

            <label>是否复贷</label>
            <form:select path="loanSucess" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="yes">是</form:option>
                <form:option value="no">否</form:option>
            </form:select>
            <div class="operSearch-btn">
                <a href="javascript:void(0);" id="operSearch-btn"
                   class="border-radius3">搜索</a>
            </div>
        </div>
        <input id="channel" name="channel" type="hidden"
               value="${applyOP.channel}"/>
        <input id="allChannel" name="allChannel" type="hidden"
               value="${allChannel}"/>
        <input id="checkStart" name="checkStart" type="hidden"
               value="${applyOP.checkStart}"/>
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
</div>
<!--搜索条件  e-->

<!-- 现金贷还款统计 code y2316-->

<div>
    <table class="store-table">
        <thead>
        <tr style="color: #000000">
            <th>信审员</th>
            <c:choose>
                <c:when test="${applyOP.termType == 'one'}">
                    <th>应还笔数</th>
                    <th>已还笔数</th>
                    <th>已还比例</th>
                </c:when>
                <c:when test="${applyOP.termType == 'fore'}">
                    <th>应还笔数1/4</th>
                    <th>已还笔数1/4</th>
                    <th>已还比例1/4</th>
                    <th>应还笔数2/4</th>
                    <th>已还笔数2/4</th>
                    <th>已还比例2/4</th>
                    <th>应还笔数3/4</th>
                    <th>已还笔数3/4</th>
                    <th>已还比例3/4</th>
                    <th>应还笔数4/4</th>
                    <th>已还笔数4/4</th>
                    <th>已还比例4/4</th>
                </c:when>
                <c:otherwise>
                    <th>应还笔数1/3</th>
                    <th>已还笔数1/3</th>
                    <th>已还比例1/3</th>
                    <th>应还笔数2/3</th>
                    <th>已还笔数2/3</th>
                    <th>已还比例2/3</th>
                    <th>应还笔数3/3</th>
                    <th>已还笔数3/3</th>
                    <th>已还比例3/3</th>
                </c:otherwise>
            </c:choose>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${approverRepayCount.list}" var="detail">
            <tr style="color: #000000; text-align: center;">
                <c:choose>
                    <c:when test="${applyOP.termType == 'one'}">
                        <td>${detail.approverName}</td>
                        <td>${detail.p1Total}</td>
                        <td>${detail.p1Payed}</td>
                        <td><fmt:formatNumber value="${detail.p1Rate*100}" pattern="0.00"></fmt:formatNumber>%</td>
                    </c:when>
                    <c:when test="${applyOP.termType == 'fore'}">
                        <td>${detail.approverName}</td>
                        <td>${detail.p1Total}</td>
                        <td>${detail.p1Payed}</td>
                        <td><fmt:formatNumber value="${detail.p1Rate*100}" pattern="0.00"></fmt:formatNumber>%</td>
                        <td>${detail.p2Total}</td>
                        <td>${detail.p2Payed}</td>
                        <td><fmt:formatNumber value="${detail.p2Rate*100}" pattern="0.00"></fmt:formatNumber>%</td>
                        <td>${detail.p3Total}</td>
                        <td>${detail.p3Payed}</td>
                        <td><fmt:formatNumber value="${detail.p3Rate*100}" pattern="0.00"></fmt:formatNumber>%</td>
                        <td>${detail.p4Total}</td>
                        <td>${detail.p4Payed}</td>
                        <td><fmt:formatNumber value="${detail.p4Rate*100}" pattern="0.00"></fmt:formatNumber>%</td>
                    </c:when>
                    <c:otherwise>
                        <td>${detail.approverName}</td>
                        <td>${detail.p1Total}</td>
                        <td>${detail.p1Payed}</td>
                        <td><fmt:formatNumber value="${detail.p1Rate*100}" pattern="0.00"></fmt:formatNumber>%</td>
                        <td>${detail.p2Total}</td>
                        <td>${detail.p2Payed}</td>
                        <td><fmt:formatNumber value="${detail.p2Rate*100}" pattern="0.00"></fmt:formatNumber>%</td>
                        <td>${detail.p3Total}</td>
                        <td>${detail.p3Payed}</td>
                        <td><fmt:formatNumber value="${detail.p3Rate*100}" pattern="0.00"></fmt:formatNumber>%</td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="pagination" style="width: 95%;" align="center">${approverRepayCount}</div>


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
        } else if (checkStart == '-7') {
            $('#time3').addClass('operTime-cur');
        } else if (checkStart == '-30') {
            $('#time4').addClass('operTime-cur');
        }

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
</script>

</body>
</html>