<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>
        <c:if test="${current != 1 }">
            还款明细
        </c:if>
        <c:if test="${current == 1 }">
            当天还款
        </c:if>
    </title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet"
          href="${ctxStatic}/layui-master/build/css/layui.css">
    <script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>
    <script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js"
            type="text/javascript"></script>
    <script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>

    <style type="text/css">
        a {
            color: #2fa4e7;
            text-decoration: none;
        }

    </style>
    <style type="text/css">
        .l{float: left;}
        .operSearch-tit{ display:inline-block; width:70px; height:24px; line-height:24px; font-size:14px; padding-left: 50px; padding-top: 20px}
        .operSearch-btn{ display:inline-block;}
        .operSearch-btn a, .daochu-btn{ display:inline-block; padding:0 15px; height:30px; line-height:30px; font-size:14px; color:#ffffff; background:#2fa4e7;}
        .operSearch-channel{ width:90%; padding-top: 20px}
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
        <c:if test="${current != 1 }">
            <a href="${ctx}/loan/repay/detailList">还款明细</a>
        </c:if>
        <c:if test="${current == 1 }">
            <a href="${ctx}/loan/repay/detailList?current=1">当天还款</a>
        </c:if>
    </li>


</ul>
<c:set var="channelList" value="${fns:getChannelList()}" />
<!--BEGIN TITLE & BREADCRUMB PAGE-->
<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
    <div class="pull-left">
        <ol class="breadcrumb page-breadcrumb">
            <li><i class="fa fa-home"></i>&nbsp;<a href="#">贷后管理</a></li>
            <li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">还款管理</a></li>
            <li class="active">&nbsp;<i class="fa fa-angle-right"></i>
                <c:if test="${current != 1 }">
                    &nbsp;还款明细
                </c:if>
                <c:if test="${current == 1 }">
                    &nbsp;当天还款
                </c:if>
            </li>
        </ol>
    </div>
    <div class="clearfix"></div>
</div>
<!--END TITLE & BREADCRUMB PAGE-->

<form:form id="searchForm" modelAttribute="repayOP"
           action="${ctx}/loan/repay/detailList" method="post"
           class="breadcrumb form-search">

    <input type="hidden" id="current" name="current" value="${current}"/>
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden"
           value="${page.pageSize}"/>
    <ul class="ul-form">
        <!--
			<li><label>待还ID</label> <form:input path="id" htmlEscape="false"
					class="input-medium" /></li>
			<li><label>合同编号</label> <form:input path="contNo"
					htmlEscape="false" class="input-medium" /></li>
			-->
        <li><label>姓名</label> <form:input path="userName"
                                          htmlEscape="false" maxlength="20" class="input-medium"/></li>
        <li><label>手机号码</label> <form:input path="mobile"
                                            htmlEscape="false" maxlength="11" class="input-medium"/></li>
        <li><label>身份证号</label> <form:input path="idNo"
                                            htmlEscape="false" maxlength="25" class="input-medium"/></li>

        <li><label>订单状态</label> <form:select path="status"
                                             class="input-medium">
            <form:option value="">全部</form:option>
            <form:option value="0">待还款</form:option>
            <form:option value="1">已还款</form:option>
            <form:option value="3">处理中</form:option>
        </form:select></li>
        <li><label>标签</label> <form:select path="sign"
                                           class="input-medium">
            <form:option value="">全部</form:option>
            <form:option value="0">正常</form:option>
            <form:option value="2">逾期</form:option>
            <form:option value="1">提前</form:option>
        </form:select></li>
        <li><label>付款方式</label> <form:select path="repayType"
                                             class="input-medium">
            <form:option value="">全部</form:option>
            <form:option value="manual">快捷还款</form:option>
            <form:option value="auto">系统代扣</form:option>
            <form:option value="manpay">手动还款</form:option>
            <form:option value="alipaypub">支付宝对公</form:option>
            <form:option value="alipay">支付宝</form:option>
            <form:option value="weixin">微信</form:option>
            <form:option value="tonglian">通联支付</form:option>
            <form:option value="ccb">建设银行</form:option>
            <form:option value="boc">中国银行</form:option>
            <form:option value="icbc">工商银行</form:option>
            <form:option value="hkb">汉口银行</form:option>
            <form:option value="BAOFOO">宝付支付</form:option>
            <form:option value="XIANFENG">先锋支付</form:option>
            <form:option value="HENGFENG">恒丰银行</form:option>
            <form:option value="TONGLIAN">通联支付</form:option>
        </form:select></li>
        <li><label>审核人</label> <form:select path="approverName"
                                            class="input-medium">
            <form:option value="">全部</form:option>
            <c:forEach items="${auditor}" var="detail">
                <form:option value="${detail.name}">${detail.name}</form:option>
            </c:forEach>
        </form:select></li>
        <li><label>申请期限</label>
            <form:select path="termType" class="input-medium">
                <form:option value="">全部</form:option>
                <%--	                <form:option value="1">14天</form:option>
                                    <form:option value="2">3个月</form:option>
                                    <form:option value="3">6个月</form:option>--%>
                <form:option value="1">8天</form:option>
                <form:option value="4">28天</form:option>
                <%--<form:option value="5">其他</form:option>--%>
            </form:select>
        </li>
        <li><label>宜信等级</label>
            <form:select path="compositeScore" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="A">A</form:option>
                <form:option value="B">B</form:option>
                <form:option value="C">C</form:option>
                <form:option value="D">D</form:option>
                <form:option value="E">E</form:option>
            </form:select>
        </li>
        <!-- 查全部的时候才有以下时间的选择，current为1，即为当天还款 -->

        <li class="time_li"><label>借款时间</label> <form:input id="borrowStart"
                                                            path="borrowStart" type="text" readonly="readonly"
                                                            maxlength="20"
                                                            class="input-small Wdate"
                                                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
            <h>-</h>
            <form:input id="borrowEnd" path="borrowEnd"
                        type="text" readonly="readonly" maxlength="20"
                        class="input-small Wdate"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
        </li>


        <c:if test="${current == 2 }">
            <li class="time_li"><label>应还日期</label> <form:input id="expectStart"
                                                                path="expectStart" type="text" readonly="readonly"
                                                                maxlength="20"
                                                                class="input-small Wdate"
                                                                onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
                <h>-</h>
                <form:input id='expectEnd' path="expectEnd"
                            type="text" readonly="readonly" maxlength="20"
                            class="input-small Wdate"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
            </li>
        </c:if>

        <c:if test="${current == 1 }">
            <input type="hidden" id="expectStart" name="expectStart" value="${expectStart }"/>
            <input type="hidden" id="expectEnd" name="expectEnd" value="${expectEnd }"/>
        </c:if>

        <li class="time_li"><label>实还时间</label> <form:input id='actualStart'
                                                            path="actualStart" type="text" readonly="readonly"
                                                            maxlength="20"
                                                            class="input-small Wdate"
                                                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
            <h>-</h>
            <form:input id='actualEnd' path="actualEnd"
                        type="text" readonly="readonly" maxlength="20"
                        class="input-small Wdate"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
        </li>

        <li class="time_li"><label>申请时间</label> <form:input id='applyCreateStart'
                                                            path="applyCreateStart" type="text" readonly="readonly"
                                                            maxlength="20"
                                                            class="input-small Wdate"
                                                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
            <h>-</h>
            <form:input id='applyCreateEnd' path="applyCreateEnd"
                        type="text" readonly="readonly" maxlength="20"
                        class="input-small Wdate"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
        </li>


        <li><label>延期结清</label> <form:select path="isDelaySettlement"
                                             class="input-medium">
            <form:option value="0">全部</form:option>
            <form:option value="1">是</form:option>
            <form:option value="2">否</form:option>
        </form:select></li>
        <li><label>期数</label><form:select path="thisTerm"
                                          class="input-medium">
            <form:option value="">全部</form:option>
            <form:option value="1">1</form:option>
            <form:option value="2">2</form:option>
            <form:option value="3">3</form:option>
            <form:option value="4">4</form:option>
        </form:select></li>

        <li><label>是否复贷</label>
            <form:select path="loanSucess" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="yes">是</form:option>
                <form:option value="no">否</form:option>
            </form:select>
        </li>
        <li class="operSearch-btn r">
            <a href="javascript:void(0);" id="operSearch-btn"
               class="border-radius3">搜索</a>
        </li>
        <input id="channel" name="channelId" type="hidden" value="${repayOP.channelId}" />
        <input id="allChannel" name="allChannel" type="hidden" value="${allChannel}" />
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

<!-- 工具栏 -->
<c:if test="${fns:haveRole('export') == true}">
    <div class="row">
        <div class="col-sm-12 pull-left">
            <!-- 导出按钮 -->
            <button id="btnExport" class="btn btn-white btn-sm" id="btnImport"
                    data-toggle="tooltip" data-placement="left"
                    onclick="return validateExport()">
                <i class="fa fa-file-excel-o"></i> 导出
            </button>

        </div>
    </div>
</c:if>

<table class="table">
    <thead>
    <tr>
        <th>序号</th>
        <c:if test="${chlOrderNoShow}">
            <th>支付订单号</th>
        </c:if>
        <c:if test="${!chlOrderNoShow}">
            <th style="min-width: 280px;">还款操作</th>
            <th style="min-width: 120px;">代扣操作</th>
        </c:if>
        <!-- <th>待还ID</th>
        <th>合同编号</th> -->
        <th>借款人姓名</th>
        <th>手机号码</th>
        <th>证件号码</th>
        <th>宜信等级</th>
        <!-- <th>归属门店</th> -->
        <th>审核人</th>
        <th>借款本金（元）</th>
        <th>借款期限</th>
        <th>还款方式</th>
        <th>基准利率（年化）</th>
        <!-- <th>折扣</th> -->
        <th>期数</th>
        <!-- 				<th>借款服务费率</th>
                        <th>借款服务费用（元）</th> -->
        <th>应还本金（元）</th>
        <th>应还利息（元）</th>
        <th>已借期限</th>
        <th>提前天数</th>
        <!-- <th>提前违约金（元）</th> -->
        <th>逾期天数</th>
        <!-- <th>逾期利息（元）</th> -->
        <th>逾期管理费</th>
        <th>提前还款手续费</th>
        <th>分期服务费</th>
        <th>减免金额（元）</th>
        <th>应还金额（元）</th>
        <th>实还金额（元）</th>
        <th>差额（元）</th>
        <th>申请时间</th>
        <th>借款时间</th>
        <!--<th>开始时间</th>-->
        <th>应还日期</th>
        <th>实还时间</th>
        <th>进件渠道</th>
        <th>借款状态</th>
        <th>付款方式</th>
        <th>订单状态</th>
        <th>标签</th>
        <th style="min-width: 80px;">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item" varStatus="xh">
        <c:if test="${item.sign ==2 and item.status !=1}">
            <tr style="color: #ff0000;">
        </c:if>
        <c:if test="${item.sign ==1 or item.sign ==0}">
            <tr>
        </c:if>
        <c:if test="${item.sign ==3 and item.status !=1}">
            <tr style="color: #FF8C00;">
        </c:if>
        <td>${xh.count}</td>
        <c:if test="${chlOrderNoShow}">
            <td>${item.chlOrderNo}</td>
        </c:if>
        <c:if test="${!chlOrderNoShow}">
            <td class="text-blue">
                <c:if test="${item.status == 0 && item.thisTerm==item.currentTerm && fns:haveRole('sdhkdk_role') == true}">
                    <a href="javascript:void(0)" onclick="processAdminWithholdQuery('${item.id}',1,'一次性还款付息')"
                       style="padding-left: 5px;">结清还款</a>
                    <a href="javascript:void(0)" onclick="processAdminWithholdQuery('${item.id}',2,'部分还款')"
                       style="padding-left: 5px;">部分还款</a>
                    <a href="javascript:void(0)" onclick="processAdminWithholdQuery('${item.id}',3,'提前结清')"
                       style="padding-left: 5px;">提前结清</a>
                </c:if>
                    <%--<c:if test="${item.status == 0 && item.thisTerm==1 && item.withdrawalSource==0}">--%>
                    <%--<a href="javascript:void(0)" onclick="processAdminWithholdQuery('${item.id}',4,'取消借款')" style="padding-left: 10px;">取消借款</a>--%>
                    <%--</c:if>--%>
            </td>
            <td class="text-blue">
                <!--还款日和逾期代扣-->
                <c:if test="${item.status == 0 && (item.sign==2 or item.sign==3) && item.thisTerm==item.currentTerm && fns:haveRole('sdhkdk_role') == true}">
                    <a href="javascript:void(0)"
                       onclick="withhold('${item.id}','${item.applyId}','${item.contNo}','${item.userId}','${item.thisTerm}','${item.totalAmount}','${item.actualRepayAmt}','tonglian','通联')"
                       style="padding-left: 5px;">通联</a>
                    <%--<a href="javascript:void(0)" onclick="withhold('${item.id}','${item.applyId}','${item.contNo}','${item.userId}','${item.thisTerm}','${item.totalAmount}','${item.actualRepayAmt}','baofoo','宝付')" style="padding-left: 5px;">宝付</a>--%>
                    <%--<c:if test="${fns:haveRole('more_withhold') == true and fns:haveRole('jbqb') == true}">--%>
                    <%--<a href="javascript:void(0)" onclick="withhold('${item.id}','${item.applyId}','${item.contNo}','${item.userId}','${item.thisTerm}','${item.totalAmount}','${item.actualRepayAmt}','xianfeng','先锋')" style="padding-left:5px;">先锋</a>--%>
                    <%--<!--<a href="javascript:void(0)" onclick="withhold('${item.id}','${item.applyId}','${item.contNo}','${item.userId}','${item.thisTerm}','${item.totalAmount}','${item.actualRepayAmt}','haier','海尔')" style="padding-left:5px;">海尔</a>-->--%>
                    <%--</c:if>--%>
                </c:if>

                <!--贷后开通通联单功能权限 还款日和逾期代扣-->
                <c:if test="${item.status == 0 && (item.sign==2 or item.sign==3) && item.thisTerm==item.currentTerm && fns:haveRole('sdhkdk_role_daihou') == true}">
                    <a href="javascript:void(0)"
                       onclick="withhold('${item.id}','${item.applyId}','${item.contNo}','${item.userId}','${item.thisTerm}','${item.totalAmount}','${item.actualRepayAmt}','tonglian','通联')"
                       style="padding-left: 5px;">通联</a>
                    <%--<a href="javascript:void(0)" onclick="withhold('${item.id}','${item.applyId}','${item.contNo}','${item.userId}','${item.thisTerm}','${item.totalAmount}','${item.actualRepayAmt}','baofoo','宝付')" style="padding-left: 5px;">宝付</a>--%>
                    <%--<c:if test="${fns:haveRole('more_withhold') == true and fns:haveRole('jbqb') == true}">--%>
                    <%--<a href="javascript:void(0)" onclick="withhold('${item.id}','${item.applyId}','${item.contNo}','${item.userId}','${item.thisTerm}','${item.totalAmount}','${item.actualRepayAmt}','xianfeng','先锋')" style="padding-left:5px;">先锋</a>--%>
                    <%--<!--<a href="javascript:void(0)" onclick="withhold('${item.id}','${item.applyId}','${item.contNo}','${item.userId}','${item.thisTerm}','${item.totalAmount}','${item.actualRepayAmt}','haier','海尔')" style="padding-left:5px;">海尔</a>-->--%>
                    <%--</c:if>--%>
                </c:if>

                <!--提前代扣-->
                <c:if test="${item.status == 0 && item.sign!=2 and item.sign!=3 && item.thisTerm==item.currentTerm && fns:haveRole('sdhkdk_role') == true}">
                    <a href="javascript:void(0)"
                       onclick="withhold('${item.id}','${item.applyId}','${item.contNo}','${item.userId}','${item.thisTerm}','${item.totalAmount}','${item.actualRepayAmt}','tonglian','通联')"
                       style="padding-left: 5px;">通联</a>
                    <%--<a href="javascript:void(0)" onclick="withhold('${item.id}','${item.applyId}','${item.contNo}','${item.userId}','${item.thisTerm}','${item.totalAmount}','${item.actualRepayAmt}','baofoo','宝付')" style="padding-left: 5px;">宝付</a>--%>
                    <%--<c:if test="${fns:haveRole('more_withhold') == true }">--%>
                    <%--<a href="javascript:void(0)" onclick="withhold('${item.id}','${item.applyId}','${item.contNo}','${item.userId}','${item.thisTerm}','${item.totalAmount}','${item.actualRepayAmt}','xianfeng','先锋')" style="padding-left:5px;">先锋</a>--%>
                    <%--</c:if>--%>
                </c:if>
            </td>
        </c:if>

        <%-- 				<td>${item.id}</td>
                        <td><a href="javascript:void(0)" data-method="offset"
                            data-type="auto" onclick="contentTable('${item.contNo}');">${item.contNo}</a></td> --%>
        <td>${item.userName}</td>
        <td>${item.mobile}</td>
        <td>${item.idNo}</td>
        <td>
            <c:if test="${applyOP.stage == 1 || applyOP.stage == 2}">
                <c:choose>
                    <c:when test="${apply.compositeScore > 550 && apply.compositeScore <= 650}">
                        A
                    </c:when>
                    <c:when test="${apply.compositeScore > 500 && apply.compositeScore <= 550}">
                        B
                    </c:when>
                    <c:when test="${apply.compositeScore > 450 && apply.compositeScore <= 500}">
                        C
                    </c:when>
                    <c:when test="${apply.compositeScore > 400 && apply.compositeScore <= 450}">
                        D
                    </c:when>
                    <c:when test="${apply.compositeScore >= 300 && apply.compositeScore <= 400}">
                        E
                    </c:when>
                    <c:otherwise>
                        -
                    </c:otherwise>
                </c:choose>
            </c:if>
        </td>
        <%-- 				<td><c:forEach items="${companyList}" var="detail">
                                <c:if test="${item.companyId == detail.companyId}">
                                 ${detail.name}
                             </c:if>
                            </c:forEach></td> --%>
        <td>${item.approverName}</td>
        <td>${item.approveAmt}</td>
        <td>${item.approveTerm}<c:if test="${item.approveTerm != null}">天</c:if></td>
        <td>${item.repayMethodStr}</td>
        <td>${item.basicRateStr}<c:if
                test="${item.basicRateStr != null}"></c:if></td>
        <%-- <td>${item.discountRateStr}<c:if test="${item.discountRateStr != null}"></c:if></td> --%>
        <td>${item.thisTerm}/${item.totalTerm}</td>
        <%-- 				<td>${item.servFeeRateStr}<c:if
                                test="${item.servFeeRateStr != null}"></c:if></td>
                        <td>${item.servFee}</td> --%>
        <td>${item.principal}</td>
        <td>${item.interest}</td>
        <td>${item.borrow}<c:if test="${item.borrow != null}">天</c:if></td>
        <td>${item.ahead}<c:if test="${item.ahead != null}">天</c:if></td>
        <%-- <td>${item.prepayFee}</td> --%>
        <td>${item.overdue}<c:if test="${item.overdue != null}">天</c:if></td>
        <%-- <td>${item.penalty}</td> --%>
        <td>${item.overdueFee}</td>
        <td>${item.prepayFee}</td>
        <td>${item.termServFee}</td>
        <td>${item.deduction}</td>
        <td>${item.totalAmount}</td>
        <td>${item.actualRepayAmt}</td>
        <td>${item.subAmt}</td>
        <td>${item.applyCreateTime}</td>
        <td>${item.loanStartDate}</td>
        <%--             <td><fmt:formatDate value="${item.repayDate}" pattern="yyyy-MM-dd"/></td> --%>
        <%-- <td>${item.startDateStr}</td>--%>
        <td>${item.repayDateStr}</td>
        <td><fmt:formatDate value="${item.actualRepayTime}"
                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
        <td>
            <c:forEach items="${channelList}" var="detail">
                <c:if test="${item.channelId == detail.cid}">
                    ${detail.cName}
                </c:if>
            </c:forEach>
        </td>

        <td>
            <c:if test="${item.loanStatus ==1 }">
                取消放款
            </c:if>
            <c:if test="${item.loanStatus !=1 }">
                放款
            </c:if>

        </td>

        <td class="text-blue"><c:choose>
            <c:when test="${item.repayType == 'manual'}">
                快捷还款
            </c:when>
            <c:when test="${item.repayType == 'auto'}">
                系统代扣
            </c:when>
            <c:when test="${item.repayType == 'manpay'}">
                手动还款
            </c:when>
            <c:when test="${item.repayType == 'alipaypub'}">
                支付宝对公
            </c:when>
            <c:when test="${item.repayType == 'alipay'}">
                支付宝
            </c:when>
            <c:when test="${item.repayType == 'weixin'}">
                微信支付
            </c:when>
            <c:when test="${item.repayType == 'tonglian'}">
                通联支付
            </c:when>
            <c:when test="${item.repayType == 'ccb'}">
                建设银行
            </c:when>
            <c:when test="${item.repayType == 'boc'}">
                中国银行
            </c:when>
            <c:when test="${item.repayType == 'icbc'}">
                工商银行
            </c:when>
            <c:when test="${item.repayType == 'hkb'}">
                汉口银行
            </c:when>
            <c:when test="${item.repayType == 'manualdelay'}">
                主动延期
            </c:when>
            <c:when test="${item.repayType == 'mandelay'}">
                手动延期
            </c:when>
            <c:when test="${item.repayType == 'autodelay'}">
                代扣延期
            </c:when>
            <c:when test="${item.repayType == 'BAOFOO'}">
                宝付支付
            </c:when>
            <c:when test="${item.repayType == 'XIANFENG'}">
                先锋支付
            </c:when>
            <c:when test="${item.repayType == 'HENGFENG'}">
                恒丰银行
            </c:when>
            <c:otherwise>
                未还款
            </c:otherwise>
        </c:choose></td>
        <td><c:if test="${item.status ==1}">
            <div class="text-blue">已还款</div>
        </c:if> <c:if test="${item.status ==0}">
            <div class="required">待还款</div>
        </c:if> <c:if test="${item.status ==3}">
            <div class="required">处理中</div>
        </c:if></td>
        <td><c:if test="${item.sign ==0 or item.sign ==3}">
            <div>正常</div>
        </c:if> <c:if test="${item.sign ==1}">
            <div class="text-blue">提前</div>
        </c:if> <c:if test="${item.sign ==2}">
            <div class="required">逾期</div>
        </c:if></td>
        <td class="text-blue">
            <a href="${ctx}/loan/apply/checkFrom?id=${item.userId}&sign=detail&applyId=${item.applyId}">详情</a>

                <%--					<c:if test="${item.status == 0 and fns:haveRole('sdcz')==true and item.productId=='XJD' and item.totalTerm==1}">
                                        <a href="javascript:void(0)"
                                            onclick="delayDialog('${item.id}','手动延期')"
                                            style="padding-left: 5px;">延期</a>
                                        &lt;%&ndash;<a href="javascript:void(0)" onclick="processAdminDelay('${item.id}')" style="padding-left:5px;">延期</a>&ndash;%&gt;
                                    </c:if>

                                    <c:if test="${item.status == 0 and fns:haveRole('jmbj') == true}">
                                        <a href="javascript:void(0)" onclick="deductionRepayDetail('${item.id}','${item.totalAmount}','${item.deduction}')"
                                            style="padding-left: 5px;">减免</a>
                                    </c:if> --%>
        </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="pagination">${page}</div>

<script id="tpl_creditList" type="text/x-handlebars-template">
    <jsp:include page="repayList.jsp"></jsp:include>
</script>

<script id="tpl_repayForm" type="text/x-handlebars-template">
    <jsp:include page="repayForm.jsp"></jsp:include>
</script>

<script id="tpl_delayForm" type="text/x-handlebars-template">
    <jsp:include page="delayForm.jsp"></jsp:include>
</script>

<script id="tpl_deductionRepayDetailForm" type="text/x-handlebars-template">
    <jsp:include page="deductionRepayDetailForm.jsp"></jsp:include>
</script>

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
    function validateExport() {

        var startTime = $("#actualStart").val();
        var endTime = $("#actualEnd").val();
        var applyCreateStart = $("#applyCreateStart").val();
        var applyCreateEnd = $("#applyCreateEnd").val();
        var startTime1 = $("#expectStart").val();
        var endTime1 = $("#expectEnd").val();
        var startTime2 = $("#borrowStart").val();
        var endTime2 = $("#borrowEnd").val();
        var flag1 = false;
        var flag2 = false;
        var flag3 = false;
        var flag4 = false;
        if ("" == startTime && "" == endTime) {
            flag1 = true;
        }
        ;
        if ("" == startTime1 && "" == endTime1) {
            flag2 = true;
        }
        ;
        if ("" == startTime2 && "" == endTime2) {
            flag3 = true;
        }
        if ("" == applyCreateStart && "" == applyCreateEnd) {
            flag4 = true;
        }
        if (("" == startTime && "" != endTime)
            || ("" != startTime && "" == endTime)) {
            layer.alert('请选择时间段', {
                icon: 7
            });
            return false;
        }
        ;
        if (("" == startTime1 && "" != endTime1)
            || ("" != startTime1 && "" == endTime1)) {
            layer.alert('请选择时间段', {
                icon: 7
            });
            return false;
        }
        ;
        if (("" == startTime2 && "" != endTime2)
            || ("" != startTime2 && "" == endTime2)) {
            layer.alert('请选择时间段', {
                icon: 7
            });
            return false;
        }

        if (("" == applyCreateStart && "" != applyCreateEnd)
            || ("" != applyCreateStart && "" == applyCreateEnd)) {
            layer.alert('请选择时间段', {
                icon: 7
            });
            return false;
        }

        if (flag1 && flag2 && flag3  && flag4) {
            layer.alert('请选择导出时间', {
                icon: 7
            });
            return false;
        }

        var startTimestamp = new Date(startTime).getTime();
        var endTimestamp = new Date(endTime).getTime();
        var startTimestamp1 = new Date(startTime1).getTime();
        var endTimestamp1 = new Date(endTime1).getTime();
        var startTimestamp2 = new Date(startTime2).getTime();
        var endTimestamp2 = new Date(endTime2).getTime();

        var startTimestamp3 = new Date(applyCreateStart).getTime();
        var endTimestamp3 = new Date(applyCreateEnd).getTime();
        if (startTimestamp1 > endTimestamp1) {
            layer.alert('起始日期应在结束日期之前', {
                icon: 7
            });
            return false;
        }
        if (startTimestamp2 > endTimestamp2) {
            layer.alert('起始日期应在结束日期之前', {
                icon: 7
            });
            return false;
        }
        if (startTimestamp > endTimestamp) {
            layer.alert('起始日期应在结束日期之前', {
                icon: 7
            });
            return false;
        }

        if (startTimestamp3 > endTimestamp3) {
            layer.alert('起始日期应在结束日期之前', {
                icon: 7
            });
            return false;
        }

        // if ((endTimestamp1 - startTimestamp1) > 31 * 24 * 60 * 60 * 1000) {
        //     layer.alert('时间段最长为一个月', {
        //         icon: 7
        //     });
        //     return false;
        // }
        // if ((endTimestamp2 - startTimestamp2) > 31 * 24 * 60 * 60 * 1000) {
        //     layer.alert('时间段最长为一个月', {
        //         icon: 7
        //     });
        //     return false;
        // }
        // if ((endTimestamp - startTimestamp) > 31 * 24 * 60 * 60 * 1000) {
        //     layer.alert('时间段最长为一个月', {
        //         icon: 7
        //     });
        //     return false;
        // }

        var current = $("#current").val();

        var url = "";

        if (current == "2") {
            url = "${ctx}/loan/repay/export";
        } else if (current == "1") {
            url = "${ctx}/loan/repay/exportToday";
        }

        return exportExcel(url,
            '${ctx}/loan/repay/detailList');
    };


    function contentTable(contNo) {
        var param = {
            contNo: contNo
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/repay/repayPlanItemList",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars
                        .compile($("#tpl_creditList").html());
                    var html = myTemplate(data.data);
                    layer.open({
                        type: 1,
                        title: '还款计划',
                        area: ['95%', '50%'], //宽高
                        content: html
                    })
                } else {
                    alert(data.msg)
                }

            },

        });
    };

    function withhold(itemId, applyId, contNo, userId, thisTerm, totalAmount, actualRepayAmt, channel, title) {
        layer.confirm('您确定要' + title + "代扣吗？", {
            btn: ['提交', '取消']
        }, function (index) {
            withholdConfirm(itemId, applyId, contNo, userId, thisTerm, totalAmount, actualRepayAmt, channel);
            layer.close(index);
        });
    };

    function withholdConfirm(itemId, applyId, contNo, userId, thisTerm, totalAmount, actualRepayAmt, channel) {
        var param = {
            itemId: itemId,
            applyId: applyId,
            contNo: contNo,
            userId: userId,
            thisTerm: thisTerm,
            totalAmount: totalAmount,
            actualRepayAmt: actualRepayAmt,
            channel: channel
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/withhold/withhold",
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

    function processAdminDelay(repayPlanItemId) {
        layer.confirm('您确定延期吗？', {
            btn: ['提交', '取消']
        }, function (index) {
            processAdminDelayConfirm(repayPlanItemId);
            layer.close(index);
        });
    }

    function processAdminDelayConfirm(repayPlanItemId) {
        var param = {
            repayPlanItemId: repayPlanItemId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/repay/processAdminDelay",
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

    function processAdminWithholdQuery(repayPlanItemId, type, title) {
        var param = {
            repayPlanItemId: repayPlanItemId,
            actualRepayTime: "",
            type: type
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/withhold/processAdminWithholdQuery",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_repayForm")
                        .html());
                    console.log(data);
                    var html = myTemplate(data.data);
                    layer.open({
                        type: 1,
                        title: title,
                        area: ['30%', '60%'], //宽高
                        content: html
                    })
                } else {
                    alert(data.msg)
                }

            },

        });
    };

    function processAdminWithhold() {
        var repayPlanItemId = $("#repayForm").find("#repayPlanItemId")
            .val();
        var type = $("#repayForm").find("#type").val();
        var totalAmount = $("#repayForm").find("#totalAmount").val();
        var currRepayAmt = $("#repayForm").find("#currRepayAmt").val();
        var actualRepayAmt = $("#repayForm").find("#actualRepayAmt").val();
        var actualRepayTime = $("#repayForm").find("#actualRepayTime")
            .val();
        var repayType = $("#repayForm").find("#repayType").val();
        var repayTypeName = $("#repayForm").find("#repayType option:selected").text();
        var prepayFee = "";
        var deductionAmt = "";
        var regAmt = /^\d{1,7}(\.\d{1,2})?$/;
        if (type == 3 || type == 4) {
            prepayFee = $("#repayForm").find("#prepayFee").val();
            deductionAmt = $("#repayForm").find("#deductionAmt").val();
            if (prepayFee == '') {
                layer.alert('请填写提前还款手续费', {
                    icon: 7
                });
                return;
            }
            if (!regAmt.test(prepayFee)) {
                layer.alert('请填写正确的提前还款手续费', {
                    icon: 7
                });
                return;
            }
            if (deductionAmt == '') {
                layer.alert('请填写减免金额', {
                    icon: 7
                });
                return;
            }
            if (!regAmt.test(deductionAmt)) {
                layer.alert('请填写正确的减免金额', {
                    icon: 7
                });
                return;
            }
        }
        if (actualRepayAmt == '') {
            layer.alert('请填写实际还款金额', {
                icon: 7
            });
            return;
        }
        if (!regAmt.test(actualRepayAmt)) {
            layer.alert('请填写正确的还款金额', {
                icon: 7
            });
            return;
        }
        if (type == 2 && actualRepayAmt == '0') {
            layer.alert('请填写正确的还款金额', {
                icon: 7
            });
            return;
        }
        if (actualRepayTime == '') {
            layer.alert('请选择实际还款时间', {
                icon: 7
            });
            return;
        }
        if (repayType == '') {
            layer.alert('请选择还款渠道', {
                icon: 7
            });
            return;
        }
        if ((type == 1 || type == 3 || type == 4) && currRepayAmt > 0) {
            var bisicRate = 0.99;
            if (type == 3 || type == 4) {
                bisicRate = 1;
            }
            var rate = actualRepayAmt / currRepayAmt;
            if (rate < bisicRate) {
                // layer.alert('实际还款金额/剩余应还金额必须>=' + bisicRate, {
                // 	icon : 7
                // });
                // return;
            }
        }
        var param = {
            repayPlanItemId: repayPlanItemId,
            actualRepayAmt: actualRepayAmt,
            actualRepayTime: actualRepayTime,
            prepayFee: prepayFee,
            type: type,
            deductionAmt: deductionAmt,
            repayType: repayType,
            repayTypeName: repayTypeName
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/withhold/processAdminWithhold",
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
    }

    function restAmount() {
        var type = $("#repayForm").find("#type").val();
        var param = {
            repayPlanItemId: $("#repayForm").find("#repayPlanItemId")
                .val(),
            actualRepayTime: $("#repayForm").find("#actualRepayTime")
                .val(),
            type: type
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/withhold/processAdminWithholdQuery",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    $("#repayForm").find("#totalAmount").val(
                        data.data.totalAmount);
                    $("#repayForm").find("#currRepayAmt").val(
                        data.data.currRepayAmt);
                    if (type == 3 || type == 4) {
                        // 重新计算金额 加提前手续费
                        var prepayFee = $("#repayForm").find("#prepayFee")
                            .val();
                        if (prepayFee == "") {
                            prepayFee = "0";
                        }
                        // 重新计算金额 减去减免金额
                        var deductionAmt = $("#repayForm").find("#deductionAmt")
                            .val();
                        if (deductionAmt == "" || deductionAmt == undefined) {
                            deductionAmt = "0";
                            // $("#repayForm").find("#deductionAmt").val(deductionAmt);
                        }
                        $("#repayForm").find("#totalAmount").val(
                            Number(data.data.totalAmount)
                            + Number(prepayFee) - Number(deductionAmt));
                        $("#repayForm").find("#currRepayAmt").val(
                            Number(data.data.currRepayAmt)
                            + Number(prepayFee) - Number(deductionAmt));
                    }
                } else {
                    alert(data.msg)
                }

            },

        });
    }

    function delayAmount() {
        var type = $("#delayForm").find("#type").val();
        var param = {
            repayPlanItemId: $("#delayForm").find("#repayPlanItemId")
                .val(),
            delayDate: $("#delayForm").find("#delayTime").val(),
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/repay/getDelayAmount",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    $("#delayForm").find("#delayAmt").val(
                        data.data.delayAmt);
                } else {
                    alert(data.msg)
                }

            },

        });
    }

    function delayDeal() {
        var repayPlanItemId = $("#delayForm").find("#repayPlanItemId")
            .val();
        var delayAmt = $("#delayForm").find("#delayAmt").val();
        var actualRepayAmt = $("#delayForm").find("#actualRepayAmt").val();
        var delayDate = $("#delayForm").find("#delayTime").val();

        var repayType = $("#delayForm").find("#repayType").val();
        var repayTypeName = $("#delayForm").find("#repayType option:selected").text();
        if (actualRepayAmt < delayAmt) {
            layer.alert('延期金额不对', {
                icon: 7
            });
            return;
        }
        if (delayDate == '') {
            layer.alert('请选择实际延期时间', {
                icon: 7
            });
            return;
        }
        if (repayType == '') {
            layer.alert('请选择支付渠道', {
                icon: 7
            });
            return;
        }

        var param = {
            repayPlanItemId: repayPlanItemId,
            delayDate: delayDate,
            repayType: repayType,
            repayTypeName: repayTypeName
        };

        var mess = "您确定通过手动延期吗？";
        top.layer.confirm(mess, {
            icon: 3,
            title: '系统提示'
        }, function (index) {
            //do something
            resetTip(); //loading();
            $.ajax({
                type: "post",
                url: "${ctx}/loan/repay/delayDeal",
                data: param,
                dataType: "json",
                error: function (request) {
                    top.layer.alert("系统繁忙,请稍后再试", {
                        icon: 2,
                        title: '系统提示'
                    });
                },
                success: function (data, textStatus) {
                    if (data.code == "1") {
                        top.layer.msg(data.msg, {
                            icon: 1
                        });
                        location.reload(true);
                    } else {
                        top.layer.msg(data.msg, {
                            icon: 2
                        });
                    }
                }
            });
            top.layer.close(index);
        });

    }

    //延期代扣 code y0524
    function delayDealWithhold() {
        var repayPlanItemId = $("#delayForm").find("#repayPlanItemId")
            .val();
        var delayAmt = $("#delayForm").find("#delayAmt").val();
        var actualRepayAmt = $("#delayForm").find("#actualRepayAmt").val();

        if (actualRepayAmt < delayAmt) {
            layer.alert('延期金额不对', {
                icon: 7
            });
            return;
        }
        //参数
        var param = {
            repayPlanItemId: repayPlanItemId,
            delayAmt: delayAmt
        };
        var mess = "您确定通过代扣延期吗？";
        top.layer.confirm(mess, {
            icon: 3,
            title: '系统提示'
        }, function (index) {
            //do something
            resetTip(); //loading();
            $.ajax({
                type: "post",
                url: "${ctx}/loan/repay/delayDealWithhold",
                data: param,
                dataType: "json",
                error: function (request) {
                    top.layer.alert("系统繁忙,请稍后再试", {
                        icon: 2,
                        title: '系统提示'
                    });
                },
                success: function (data, textStatus) {
                    if (data.code == "1") {
                        top.layer.msg(data.msg, {
                            icon: 1
                        });
                        location.reload(true);
                    }
                    else if (data.code == "3") {
                        top.layer.msg(data.msg, {
                            icon: 7
                        });
                    } else {
                        top.layer.msg(data.msg, {
                            icon: 2
                        });
                    }
                }
            });
            top.layer.close(index);
        });
    }

    function delayDialog(repayPlanItemId, title) {
        var param = {
            repayPlanItemId: repayPlanItemId,
            delayDate: ""
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/repay/getDelayAmount",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_delayForm")
                        .html());
                    var html = myTemplate(data.data);
                    layer.open({
                        type: 1,
                        title: title,
                        area: ['40%', '50%'], //宽高
                        content: html
                    });
                } else {
                    alert(data.msg)
                }

            },

        });
    };

    function deductionRepayDetail(repayPlanDetailItemId, totalDetailAmount, lastDeductionDetailAmt) {
        var param = {
            repayPlanDetailItemId: repayPlanDetailItemId,
            totalDetailAmount: totalDetailAmount,
            lastDeductionDetailAmt: lastDeductionDetailAmt
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/deduction/checkDeduction",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_deductionRepayDetailForm")
                        .html());
                    var html = myTemplate(param);
                    layer.open({
                        type: 1,
                        title: "还款减免",
                        area: ['30%', '40%'], //宽高
                        content: html
                    });
                } else {
                    alert(data.msg)
                }

            },

        });
    };

    function ensureDeduction() {
        var repayPlanItemId = $("#deductionRepayDetailFrom").find("#repayPlanDetailItemId").val();
        var totalDetailAmount = $("#deductionRepayDetailFrom").find("#totalDetailAmount").val();
        var deductionDetailAmt = $("#deductionRepayDetailFrom").find("#deductionDetailAmt").val();
        var deductionReason = $("#deductionRepayDetailFrom").find("#deductionReason").val();

        var param = {
            repayPlanItemId: repayPlanItemId,
            deductionDetailAmt: deductionDetailAmt,
            deductionReason: deductionReason
        };
        var regAmt = /^\d{1,7}(\.\d{1,2})?$/;

        if (!regAmt.test(deductionDetailAmt)) {
            layer.alert('请填写正确的减免金额', {
                icon: 7
            });
            return;
        }

        if (Number(totalDetailAmount) < Number(deductionDetailAmt)) {
            layer.alert('减免金额大于应还金额', {
                icon: 7
            });
            return;
        }

        var mess = "您确定减免金额吗？";
        top.layer.confirm(mess, {
            icon: 3,
            title: '系统提示'
        }, function (index) {
            resetTip(); //loading();
            $.ajax({
                type: "post",
                url: "${ctx}/loan/deduction/deductionRepayDetailAmount",
                data: param,
                dataType: "json",
                error: function (request) {
                    top.layer.alert("系统繁忙,请稍后再试", {
                        icon: 2,
                        title: '系统提示'
                    });
                },
                success: function (data, textStatus) {
                    if (data.code == "1") {
                        top.layer.msg(data.msg, {
                            icon: 1
                        });
                        location.reload(true);
                    } else {
                        top.layer.msg(data.msg, {
                            icon: 2
                        });
                    }
                }
            });
            top.layer.close(index);
        });

    }
</script>
</body>
</html>