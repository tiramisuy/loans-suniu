<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>

    <c:choose>
        <c:when test="${pressOP.status == 0}">
            <title>逾期催收明细</title>
        </c:when>
        <c:when test="${pressOP.status == 1}">
            <title>催收已还明细</title>
        </c:when>

    </c:choose>
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

        function openTabPage(url) {
            //调用父页面获取选中的tab的id 页签模式有id  非页签该id为undifine
            var select_id = this.parent.getSelectTab();
            url += "&parentId=" + select_id;
            if (cookie('tabmode') == '1') {
                addTabPage('催收处理', url, null);
            } else {
                window.location.href = url;
            }
        };

    </script>

</head>


<body>


<c:set var="channelList" value="${fns:getChannelList()}"/>
<c:set var="productList" value="${fns:getProductList()}"/>
<ul class="nav nav-tabs">
    <li class="active">
        <c:choose>
            <c:when test="${pressOP.status == 0}">
                <a href="${ctx}/loan/repay/pressList?status=0">逾期催收明细</a>
            </c:when>
            <c:when test="${pressOP.status == 1}">
                <a href="${ctx}/loan/repay/pressList?status=1">催收已还明细</a>
            </c:when>
        </c:choose>
    </li>


</ul>
<!--BEGIN TITLE & BREADCRUMB PAGE-->
<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
    <div class="pull-left">
        <ol class="breadcrumb page-breadcrumb">
            <li><i class="fa fa-home"></i>&nbsp;<a href="#">贷后管理</a></li>
            <li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">催收管理</a></li>
            <c:choose>
                <c:when test="${pressOP.status == 0}">
                    <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;逾期催收明细</li>
                </c:when>
                <c:when test="${pressOP.status == 1}">
                    <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;催收已还明细</li>
                </c:when>
            </c:choose>

        </ol>
    </div>
    <div class="clearfix"></div>
</div>
<!--END TITLE & BREADCRUMB PAGE-->

<form:form id="searchForm" modelAttribute="pressOP" action="${ctx}/loan/collection/pressList"
           method="post"
           class="breadcrumb form-search layui-form">

    <input id="pageNo" name="pageNo" type="hidden" value="${pressOP.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${pressOP.pageSize}"/>
    <c:choose>
        <c:when test="${pressOP.status == 0}">
            <input id="status" name="status" type="hidden" value="0"/>
        </c:when>
        <c:when test="${pressOP.status == 1}">
            <input id="status" name="status" type="hidden" value="1"/>
        </c:when>
    </c:choose>

    <ul class="ul-form">
        <li><label>姓名</label>
            <form:input path="userName" htmlEscape="false" maxlength="20" class="input-medium"
                        value="${pressOP.userName }"/>
        </li>
        <li><label>手机号码</label>
            <form:input path="mobile" htmlEscape="false" maxlength="11" class="input-medium"
                        value="${pressOP.mobile }"/>
        </li>
        <li><label>身份证号</label>
            <form:input path="idNo" htmlEscape="false" maxlength="25" class="input-medium" value="${pressOP.idNo }"/>
        </li>
        <li><label>催收人员</label>
            <form:select path="operatorId" class="input-medium">
                <c:if test="${isCsy != 'true'}">
                    <form:option value="">全部</form:option>
                </c:if>
                <c:forEach items="${csy}" var="detail">
                    <c:choose>
                        <c:when test="${pressOP.operatorId == detail.id}">
                            <form:option value="${detail.id}" selected="selected">${detail.name}</form:option>
                        </c:when>
                        <c:when test="${pressOP.operatorId != detail.id and isCsy != true}">
                            <form:option value="${detail.id}">${detail.name}</form:option>
                        </c:when>

                        <c:otherwise>

                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </form:select>
        </li>
        </div>
    </ul>
    <ul class="ul-form">
        <c:if test="${pressOP.status == 0}">
            <li><label>催收结果</label>
                <form:select path="result" class="input-medium">
                    <form:option value="">全部</form:option>
                    <c:forEach items="${resultList}" var="detail">
                        <c:choose>
                            <c:when test="${pressOP.result == detail.value}">
                                <form:option value="${detail.value}"
                                             selected="selected">${detail.value}-${detail.desc}</form:option>
                            </c:when>
                            <c:otherwise>
                                <form:option value="${detail.value}">${detail.value}-${detail.desc}</form:option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
            </li>
            <li><label>催收内容</label>
                <form:input path="content" htmlEscape="false" maxlength="20" class="input-medium"/>
            </li>
        </c:if>


    </ul>


    <ul class="ul-form">


        <li><label>产品</label>
            <form:select path="productId" class="input-medium">
                <form:option value="">全部</form:option>
                <c:forEach items="${fns:getProductList()}" var="detail">
                    <form:option value="${detail.id}">${detail.name}</form:option>
                </c:forEach>
            </form:select>
        </li>


        <li><label>申请期限</label>
            <form:select path="termType" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="1">8天</form:option><%--
                <form:option value="2">3个月</form:option>
                <form:option value="3">6个月</form:option>--%>
                <form:option value="4">28天</form:option>
               <%-- <form:option value="5">其他</form:option>--%>
            </form:select>
        </li>
    </ul>

    <ul class="ul-form">

        <li class="time_li"><label>借款时间</label>

            <form:input path="borrowStart" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                        value="${pressOP.borrowStart }"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>

            <h>-</h>
            <form:input path="borrowEnd" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                        value="${pressOP.borrowEnd }"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
        </li class="time_li">
        <li class="time_li"><label>应还日期</label>
            <form:input path="expectStart" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                        value="${pressOP.expectStart }"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>

            <h>-</h>
            <form:input path="expectEnd" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                        value="${pressOP.expectEnd }"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
        </li>

        <c:if test="${pressOP.status == 1}">
            <li class="time_li"><label>实还时间</label>
                <form:input path="actualStart" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                            value="${pressOP.actualStart }"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>

                <h>-</h>
                <form:input path="actualEnd" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                            value="${pressOP.actualEnd }"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
            </li>
        </c:if>

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
    </ul>

    <ul class="ul-form">
        <li class="checkbox_li"><label class="layui-form-label">逾期天数</label>

            <div class="layui-input-block">
                <c:forEach items="${enums}" var="detail">
                    <input type="checkbox" name="overdue" value="${detail.value}" title="${detail.desc}"

                    <c:forEach items="${pressOP.overdue}" var="item">
                    <c:if test="${item == detail.value}"> checked</c:if>
                    </c:forEach>
                    >
                </c:forEach>
            </div>
        </li>
        <input id="channel" name="channelId" type="hidden" value="${pressOP.channelId}" />
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
<ul id="menu">
    <%--<c:if test="${pressOP.status == 0 and isCsy != true}">
        <li class="btns"><input id="csSubmit" class="btn btn-primary"  type="button" onclick="assignment()" value="催收分配"/></li>
    </c:if>--%>
    <%-- <c:if test="${pressOP.status == 0 and fns:haveRole('export') == true}">
        <li class="btns"><input id="csSubmit" class="btn btn-primary"
            type="button"
            onclick="return exportExcel('${ctx}/loan/collection/OverDueDataExport?stage=2&status=0','');"
            onclick="return validateExport('0')"
            value="导出" /></li>
    </c:if> --%>
</ul>
<!-- 工具栏 -->
<%-- <c:if test="${pressOP.status == 1 and fns:haveRole('export') == true}">
		<div class="row">
			<div class="col-sm-12 pull-left">
				<!-- 导出按钮 -->
				<button id="btnExport" class="btn btn-white btn-sm" id="btnImport"
					data-toggle="tooltip" data-placement="left"
					onclick="return exportExcel('${ctx}/loan/collection/export?stage=2&status=1','');"
					onclick="return validateExport('1')">
					<i class="fa fa-file-excel-o"></i> 导出
				</button>
			</div>
		</div>
	</c:if> --%>


<table id="contentTable" class="table">
    <thead>
    <tr>
        <th>
            <c:if test="${pressOP.status == 0 and isCsy != true}">
                <input type="checkbox" name="batchOptControl">
            </c:if>
        </th>
        <th>序号</th>
        <!-- <th>待还ID</th> -->
        <th>合同编号</th>
        <th>催收人</th>
        <th>借款人姓名</th>
        <c:if test="${pressOP.status == 0}">
            <th>催收结果</th>
        </c:if>

        <c:if test="${pressOP.status == 0}">
            <th style="min-width:180px;">操作</th>
            <th style="min-width:180px;">代扣操作</th>
        </c:if>
        <c:if test="${pressOP.status == 1}">
            <th style="min-width:122px;">操作</th>
        </c:if>
        <th>手机号码</th>
        <th>证件号码</th>
        <!--   <th>借款产品</th> -->
        <th>借款渠道</th>
        <th>借款本金（元）</th>
        <th>借款期限</th>
        <!-- <th>还款方式</th>
        <th>基准利率（年化）</th> -->
        <!-- <th>折扣</th> -->
        <th>期数</th>
        <!--  <th>借款服务费率</th> -->
        <!--        <th>借款服务费用（元）</th> -->
        <th>应还本金（元）</th>
        <th>应还利息（元）</th>
        <!--<th>当前利息（元）</th>-->
        <!--  <th>已借期限</th> -->
        <th>逾期天数</th>
        <!-- <th>逾期利息（元）</th> -->
        <th>逾期管理费</th>
        <!--  <th>分期服务费</th> -->
        <th>减免金额（元）</th>
        <th>应还金额（元）</th>
        <th>实还金额（元）</th>
        <th>差额（元）</th>
        <th>借款时间</th>
        <th>应还日期</th>
        <th>实还时间</th>
        <th>最后登陆时间</th>
        <!--         <th>历史逾期次数</th>
                <th>历史最长逾期天数</th> -->
        <th>放款次数</th>
        <th>订单状态</th>
        <th>标签</th>
        <!-- <th>操作</th> -->
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item" varStatus="xh">
        <td>
            <c:if test="${pressOP.status == 0 and isCsy != true}">
                <input class="check" type="checkbox" value="${item.id}" username="${item.userName}"
                       operatorName="${item.operatorName}" name="batchOptIds">
            </c:if>
        </td>
        <td>${xh.count}</td>
        <%--  <td>${item.id}</td> --%>
        <td><a href="javascript:void(0)" data-method="offset" data-type="auto"
               onclick="contentTable('${item.contNo}');">${item.contNo}</a></td>
        <td>${item.operatorName}</td>
        <td>
            <c:if test="${pressOP.status == 0}">
                <a href="javascript:void(0)" data-method="offset" data-type="auto"
                   onclick="contentTable_2('${item.userId}','${item.applyId}','detail')">${item.userName}</a>
            </c:if>
            <c:if test="${pressOP.status == 1}">
                ${item.userName}
            </c:if>
        </td>
        <c:if test="${pressOP.status == 0}">
            <td>${item.result}</td>
        </c:if>

        <td>
            <c:if test="${pressOP.status == 0}">
                <c:if test="${item.isPushOverdue ==false }">
                    <a href="javascript:void(0)"
                       onclick="openTab(this,'${item.id}','${item.userId}','${item.applyId}','${item.overdue }',2)"
                       style="padding-left:5px;">催收</a>
                    <%--<a href="javascript:void(0)" data-method="offset" data-type="auto" style="padding-left: 5px;" onclick="updateOverdue('${item.id }',99)">暂停催收</a>
                    <a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="contentTable_3('${item.id}','${ctx}',1)" style="padding-left:5px;">减免</a>
                    <c:if test="${fns:haveRole('annul') == true}">
                        <a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="contentTable_3('${item.id}','${ctx}',2)" style="padding-left:5px;">审核</a>
                    </c:if>
                    --%>
                    <a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="detail('${item.id}');"
                       style="padding-left:5px;">分配详情</a>
                    <c:if test="${fns:haveRole('addBlackList') == true}">
                        <a href="javascript:void(0)" data-method="offset" data-type="auto"
                           onclick="saveBlackList('${item.id}','${item.userId}','${item.applyId}')"
                           style="padding-left:5px;">加入黑名单</a>
                    </c:if>
                </c:if>
                <c:if test="${item.isPushOverdue == true}">
                    <a href="javascript:void(0)" data-method="offset" data-type="auto" style="padding-left: 5px;"
                       onclick="updateOverdue('${item.id }',17)">重新催收</a>
                </c:if>
            </c:if>

            <c:if test="${pressOP.status == 1}">
                <a href="javascript:void(0)" data-method="offset" data-type="auto"
                   onclick="record('${item.id}');">催收记录</a>
                <a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="detail('${item.id}');"
                   style="padding-left:5px;">分配详情</a>
                <c:if test="${fns:haveRole('addBlackList') == true}">
                    <a href="javascript:void(0)" data-method="offset" data-type="auto"
                       onclick="saveBlackList('${item.id}','${item.userId}','${item.applyId}')"
                       style="padding-left:5px;">加入黑名单</a>
                </c:if>
            </c:if>
        </td>

        <c:if test="${pressOP.status == 0}">
            <td> <a href="javascript:void(0)" onclick="processAdminWithholdQuery('${item.id}','8','部分代扣')" style="padding-left:5px;">通联部分代扣</a></td>
        </c:if>
        <%--<td>--%>
                <%--<c:if test="${item.isPushOverdue ==false }">
                    <c:if test="${fns:haveRole('yqdk') == true and fns:haveRole('jbqb') == true}">
                        <a href="javascript:void(0)" onclick="withhold('${item.id}','${item.applyId}','${item.contNo}','${item.userId}','${item.thisTerm}','${item.totalAmount}','${item.actualRepayAmt}','baofoo','宝付')" style="padding-left:5px;">宝付</a>
                    </c:if>
                    <c:if test="${fns:haveRole('more_withhold') == true and fns:haveRole('jbqb') == true}">
                        <a href="javascript:void(0)" onclick="withhold('${item.id}','${item.applyId}','${item.contNo}','${item.userId}','${item.thisTerm}','${item.totalAmount}','${item.actualRepayAmt}','xianfeng','先锋')" style="padding-left:5px;">先锋</a>
                        <!--<a href="javascript:void(0)" onclick="withhold('${item.id}','${item.applyId}','${item.contNo}','${item.userId}','${item.thisTerm}','${item.totalAmount}','${item.actualRepayAmt}','haier','海尔')" style="padding-left:5px;">海尔</a>-->
                    </c:if>

                </c:if>--%>
        <%--</td>--%>


        <td>${item.mobile}</td>
        <td>${item.idNo}</td>
        <%--   <c:forEach items="${productList}" var="detail">
              <c:if test="${item.productId == detail.id}">
                    <td>${detail.name}</td>
              </c:if>
          </c:forEach>
           --%>
        <%-- <c:forEach items="${channelList}" var="detail">
           <c:if test="${item.channelId == detail.cid}">
                 <td>${detail.cName}</td>
           </c:if>
       </c:forEach> --%>

        <td>${item.channelId}</td>

        <td>${item.approveAmt}</td>
        <td>${item.approveTerm}<c:if test="${item.approveTerm != null}">天</c:if></td>
        <%--    <td>${item.repayMethodStr}</td>
           <td>${item.basicRateStr}<c:if test="${item.basicRateStr != null}">%</c:if></td> --%>
        <%-- <td>${item.discountRateStr}<c:if test="${item.discountRateStr != null}">%</c:if></td> --%>
        <td>${item.thisTerm}/${item.totalTerm}</td>
        <%--  <td>${item.servFeeRateStr}<c:if test="${item.servFeeRateStr != null}">%</c:if></td> --%>
        <%-- <td>${item.servFee}</td> --%>
        <td>${item.principal}</td>
        <td>${item.interest}</td>
        <%--<td>${item.currentInterest}</td>--%>
        <%--  <td>${item.borrow}<c:if test="${item.borrow != null}">天</c:if></td> --%>
        <td>${item.overdue}<c:if test="${item.overdue != null}">天</c:if></td>
        <%-- <td>${item.penalty}</td> --%>
        <td>${item.overdueFee}</td>
        <%-- <td>${item.termServFee}</td> --%>
        <td>${item.deduction}</td>
        <td>${item.totalAmount}</td>
        <td>${item.actualRepayAmt}</td>
        <td>${item.subAmt}</td>
        <td><fmt:formatDate value="${item.loanStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        <td><fmt:formatDate value="${item.repayDate}" pattern="yyyy-MM-dd"/></td>
        <td><fmt:formatDate value="${item.actualRepayTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        <td><fmt:formatDate value="${item.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        <%--     <td>${item.overdueTimes}</td>
            <td>${item.maxOverdueDays}</td> --%>
        <td>
                ${item.loanSuccCount +1 }
        </td>
        <td>
            <c:if test="${item.status ==1}">
                <div class="text-blue">已还款</div>
            </c:if>
            <c:if test="${item.status ==0}">
                <div class="required">待还款</div>
            </c:if>
        </td>
        <td>
            <div class="required">逾期</div>
        </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>

<script id="tpl_creditList" type="text/x-handlebars-template">
    <jsp:include page="repayList.jsp"></jsp:include>
</script>

<script id="tpl_creditList_2" type="text/x-handlebars-template">
    <jsp:include page="userInfo.jsp"></jsp:include>
</script>

<script id="tpl_creditList_3" type="text/x-handlebars-template">
    <jsp:include page="deductionForm.jsp"></jsp:include>
</script>

<script id="tpl_creditList_4" type="text/x-handlebars-template">
    <jsp:include page="pressBlackForm.jsp"></jsp:include>
</script>

<script id="tpl_detail" type="text/x-handlebars-template">
    <jsp:include page="allotmentList.jsp"></jsp:include>
</script>
<script id="tpl_assignment" type="text/x-handlebars-template">
    <jsp:include page="collectionAssignment.jsp"></jsp:include>
</script>
<script id="tpl_record" type="text/x-handlebars-template">
    <%--催收记录--%>
    <jsp:include page="collectionRecord.jsp"></jsp:include>
</script>
<script id="tpl_partWithholdForm" type="text/x-handlebars-template">
    <jsp:include page="partWithholdForm.jsp"></jsp:include>
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

    function withhold(itemId, applyId, contNo, userId, thisTerm, totalAmount, actualRepayAmt, channel, title) {
        layer.confirm('您确定要' + title + "代扣吗？", {
            btn: ['提交', '取消']
        }, function (index) {
            withholdConfirm(itemId, applyId, contNo, userId, thisTerm, totalAmount, actualRepayAmt, channel);
            layer.close(index);
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
                    var myTemplate = Handlebars.compile($("#tpl_partWithholdForm")
                        .html());
                    var html = myTemplate(data.data);
                    layer.open({
                        type: 1,
                        title: title,
                        area: ['30%', '52%'], //宽高
                        content: html
                    })
                } else {
                    top.layer.msg(data.msg, {
                        icon: 2
                    });
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
        var regAmt = /^\d{3,7}(\.\d{1,2})?$/;

        if (parseFloat(actualRepayAmt) == parseFloat(currRepayAmt)) {
            type = 1;// 还款结清
        } else {
            if (!regAmt.test(actualRepayAmt)) {
                layer.alert('请填写正确的还款金额：还款金额不少于100，且至多精确到分', {
                    icon: 7
                });
                return;
            }
            if (parseFloat(actualRepayAmt) > parseFloat(currRepayAmt)) {
                layer.alert('请填写正确的还款金额：还款金额不能大于应还金额', {
                    icon: 7
                });
                return;
            }
        }
        var param = {
            repayPlanItemId: repayPlanItemId,
            actualRepayAmt: actualRepayAmt,
            type: type
        };
        var mess = "是否确定进行部分代扣操作？";
        top.layer.confirm(mess, {
            icon: 3,
            title: '系统提示'
        }, function (index) {
            // resetTip();
            $.ajax({
                type: "post",
                url: "${ctx}/loan/withhold/partWithhold",
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
                    } else if (data.code == "3") {
                        top.layer.msg(data.msg, {
                            icon: 7
                        });
                        location.reload(true);
                    } else {
                        top.layer.msg(data.msg, {
                            icon: 2
                        });
                    }

                }

            });
            // top.layer.close(index);
        });


    }

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
    }

    function validateExport(a) {
        var startTime = $("#actualStart").val();
        var endTime = $("#actualEnd").val();
        var startTime1 = $("#expectStart").val();
        var endTime1 = $("#expectEnd").val();
        var startTime2 = $("#borrowStart").val();
        var endTime2 = $("#borrowEnd").val();
        var flag1 = false;
        var flag2 = false;
        var flag3 = false;
        if (("" == startTime && "" == endTime) || (typeof(startTime) == "undefined" && typeof(endTime) == "undefined")) {
            flag1 = true;
        }
        ;
        if (("" == startTime1 && "" == endTime1) || (typeof(startTime1) == "undefined" && typeof(endTime1) == "undefined")) {
            flag2 = true;
        }
        ;
        if (("" == startTime2 && "" == endTime2) || (typeof(startTime2) == "undefined" && typeof(endTime2) == "undefined")) {
            flag3 = true;
        }
        ;
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
        ;
        if (flag1 && flag2 && flag3) {
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
        if ((endTimestamp1 - startTimestamp1) > 31 * 24 * 60 * 60 * 1000) {
            layer.alert('时间段最长为一个月', {
                icon: 7
            });
            return false;
        }
        if ((endTimestamp2 - startTimestamp2) > 31 * 24 * 60 * 60 * 1000) {
            layer.alert('时间段最长为一个月', {
                icon: 7
            });
            return false;
        }
        if ((endTimestamp - startTimestamp) > 31 * 24 * 60 * 60 * 1000) {
            layer.alert('时间段最长为一个月', {
                icon: 7
            });
            return false;
        }
        if ("1" == a) {
            return exportExcel(
                '${ctx}/loan/collection/export?stage=2&status=1', '${ctx}/loan/collection/pressList?stage=2&status=1');
        } else if ("0" == a) {
            return exportExcel(
                '${ctx}/loan/collection/OverDueDataExport?stage=2&status=0', '${ctx}/loan/collection/pressList?stage=2&status=0');
        }

    };

    function assignment() {
        var list = [];
        var param = {
            list: list
        };

        if (!$(".check").is(":checked")) {
            alert("请勾选选项");
            return false;
        } else {
            $.each($(".check"), function () {
                if ($(this).is(":checked")) {
                    var obj = {
                        id: "",
                        username: "",
                        operatorName: ""
                    };
                    obj.id = $(this).attr("value");
                    obj.username = $(this).attr("username");
                    obj.operatorName = $(this).attr("operatorName");
                    list.push(obj);
                }
            });
            var myTemplate = Handlebars.compile($("#tpl_assignment").html());
            var html = myTemplate(param);
            layer.open({
                type: 1,
                title: '催收分配',
                area: ['60%', '80%'], //宽高
                content: html
            })
            $.ajax({
                type: "post",
                url: "${ctx}/loan/collection/getOperator",
                data: "",
                dataType: "json",
                error: function (request) {
                    alert("系统繁忙,请稍后再试");
                },
                success: function (data, textStatus) {
                    if (data.code == "1") {
                        var collectionsSelecter = $("#collections-name");
                        var str = "";
                        for (var i = 0; i < data.data.length; i++) {
                            /* 	str += '<option value="'+data.data[i].id+'">'
                                        + data.data[i].name + '</option>'; */
                            if (i != 0) {
                                str += '<div style="padding-top:10px;">';
                            } else {
                                str += '<div>';
                            }

                            str += '<input type="checkbox" data-id="' + data.data[i].id + '-' + data.data[i].name + '"/><span>' + data.data[i].name + '</span></div>';
                        }
                        collectionsSelecter.append(str);
                    } else {
                        alert(data.msg)
                    }

                },

            });
        }
    };

    function collectionBot1() {
        /* if ($("#collections-name").val() == "") {
            alert("请选择分配后的催收人");
            return false;
        } */
        if ($("#collections-time").val() == "--请选择--") {
            alert("请选择分配后退回时间");
            return false;
        }
        var arrId = "";//存储更改催收人的时候  借款人的id
        $.each($(".collections-con input"), function (i) {
            if ($(this).is(":checked")) {
                arrId += $(this).attr("data-id") + "|";
            }
        });

        var arrUId = "";//存储多个催收员的id
        $.each($(".collections-name input"), function (i) {
            if ($(this).is(":checked")) {
                arrUId += $(this).attr("data-id") + "|";
            }
        });
        //alert(arrUId);
        console.log(arrId)
        var datas = {
            ids: arrId,
            operatorId: arrUId,    // $("#collections-name").val(), //重新分配后的催收人id
            time: $("#collections-time").val()
            //重新分配时间的id
        }
        $.ajax({
            url: '${ctx}/loan/collection/doAllotment',
            type: 'post',
            data: datas,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    layer.close(layer.index);
                    location.reload(true);
                } else {
                    alert(data.msg)
                }

            },
        });
    }

    function detail(itemId) {
        var param = {
            itemId: itemId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/collection/assignmentDetail",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars
                        .compile($("#tpl_detail").html());
                    var html = myTemplate(data.data);
                    layer.open({
                        type: 1,
                        title: '催收分配',
                        area: ['95%', '50%'], //宽高
                        content: html
                    })
                } else {
                    alert(data.msg)
                }

            },

        });
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
                    var myTemplate = Handlebars.compile($("#tpl_creditList")
                        .html());
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

    function contentTable_2(id, applyId, sign) {
        var param = {
            id: id,
            applyId: applyId,
            sign: sign
        };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/custUser/userDetail",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_creditList_2")
                        .html());
                    var html = myTemplate(data.data);
                    layer.open({
                        type: 1,
                        title: '客户信息',
                        area: ['95%', '80%'], //宽高
                        content: html
                    })
                } else {
                    alert(data.msg)
                }

            },

        });
    };

    function contentTable_3(id, ctx, source) {
        var param = {
            id: id,
            ctx: ctx,
            source: source
        };
        $.ajax({
            type: "get",
            url: "${ctx}/loan/deduction/deductionFrom",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_creditList_3")
                        .html());
                    var html = myTemplate(data.data);
                    layer.open({
                        type: 1,
                        title: '逾期记录',
                        area: ['95%', '80%'], //宽高
                        content: html
                    });
                    $("#myform").validate();
                } else {
                    alert(data.msg)
                }

            },

        });
    };

    $.validator
        .setDefaults({
            submitHandler: function () {
                if ($('#myform').valid()) {
                    $
                        .ajax({
                            cache: true,
                            type: "POST",
                            url: "${ctx}/loan/deduction/apply",
                            data: $('#myform').serialize(),
                            async: false,
                            error: function (request) {
                                alert("系统繁忙,请稍后再试");
                            },
                            success: function (data) {
                                if (data.code == "1") {
                                    alert("提交成功");
                                    window.location.href = "${ctx}/loan/collection/pressList?status=0";
                                } else {
                                    alert(data.msg)
                                }
                            }
                        });
                }
                ;

            }
        });

    function saveBlackList(itemId, userId, applyId) {
        var param = {
            itemId: itemId,
            userId: userId,
            applyId: applyId
        };
        $.ajax({
            type: "get",
            url: "${ctx}/loan/collection/pressBlackFrom",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_creditList_4")
                        .html());
                    var html = myTemplate(data.data);
                    layer.open({
                        type: 1,
                        title: '拉入黑名单',
                        area: ['95%', '80%'], //宽高
                        content: html
                    });
                    $("#myBlackForm").validate();
                } else {
                    alert(data.msg)
                }

            },

        });
    };

    function submitBlackList() {
        if ($.trim($("#remark").val()) == "") {
            layer.alert('拉黑原因不能为空！', {
                icon: 7
            });
        } else {
            $
                .ajax({
                    cache: true,
                    type: "POST",
                    url: "${ctx}/loan/collection/saveBlackForm",
                    data: $('#myBlackForm').serialize(),
                    async: false,
                    error: function (request) {
                        alert("系统繁忙,请稍后再试");
                    },
                    success: function (data) {
                        if (data.code == "1") {
                            alert("提交成功");
                            window.location.href = "${ctx}/loan/collection/pressList?status=0";
                        } else {
                            alert(data.msg)
                        }
                    }
                });
        }
    }

    function approve(id, repayPlanItemId, status) {
        var param = {
            id: id,
            repayPlanItemId: repayPlanItemId,
            status: status
        };

        $
            .ajax({
                cache: true,
                type: "POST",
                url: "${ctx}/loan/deduction/approve",
                data: param,
                async: false,
                error: function (request) {
                    alert("系统繁忙,请稍后再试");
                },
                success: function (data) {
                    if (data.code == "1") {
                        window.location.href = "${ctx}/loan/collection/pressList?status=0";
                    } else {
                        alert(data.msg)
                    }
                }
            });
    };

    function record(itemId) {
        var param = {
            itemId: itemId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/collection/list",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars
                        .compile($("#tpl_record").html());
                    var html = myTemplate(data.data);
                    layer.open({
                        type: 1,
                        title: '催收记录',
                        area: ['95%', '80%'], //宽高
                        content: html
                    });
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

    function openTab(row, id, userId, applyId, overday, page) {
        var searchFormUrl = $("#searchForm").serialize();
        searchFormUrl = decodeURIComponent(searchFormUrl, true);
        var userNameEncode = "";
        if ($("#userName").val() != null && $("#userName").val() != "") {
            userNameEncode = encodeURI(encodeURI($("#userName").val()));
        }
        var contentEncode = "";
        if ($("#content").val() != null && $("#content").val() != "") {
            contentEncode = encodeURI(encodeURI($("#content").val()));
        }
        var index = $(row).closest('tr')[0].rowIndex;
        var url = '${ctx}/loan/collection/pressFrom?itemId=' + id + '&userId=' + userId + '&applyId=' + applyId + '&overday=' + overday + '&page=' + page + '&' + searchFormUrl + '&userNameEncode=' + userNameEncode + '&index=' + index + '&contentEncode=' + contentEncode;
        openTabPage(url);
    }


    function updateOverdue(itemId, resultType) {
        layer.confirm('您确定要暂停催收吗？', {
            btn: ['提交', '取消']
        }, function (index) {
            updateConfirm(itemId, resultType);
            layer.close(index);
        });
    }

    function updateConfirm(itemId, resultType) {
        var param = {
            overdueId: itemId,
            resultType: resultType
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/collection/updateOverdue",
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


</script>
</body>

</html>