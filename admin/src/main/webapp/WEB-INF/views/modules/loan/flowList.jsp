<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>还款流水</title>
    <meta name="decorator" content="default"/>

    <script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js" type="text/javascript"></script>
    <script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>

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
        <a href="${ctx}/loan/repay/flowList">还款流水</a>
    </li>


</ul>
<!--BEGIN TITLE & BREADCRUMB PAGE-->
<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
    <div class="pull-left">
        <ol class="breadcrumb page-breadcrumb">
            <li><i class="fa fa-home"></i>&nbsp;<a href="#">贷后管理</a></li>
            <li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">还款管理</a></li>
            <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;还款流水</li>
        </ol>
    </div>
    <div class="clearfix"></div>
</div>
<!--END TITLE & BREADCRUMB PAGE-->

<form:form id="searchForm" modelAttribute="flowOP" action="${ctx}/loan/repay/flowList" method="post"
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
        <li><label>实还金额</label>
            <form:input path="amount" htmlEscape="false" maxlength="25" class="input-medium"/>元
        </li>
    </ul>
    <ul class="ul-form">
        <li><label>申请期限</label>
            <form:select path="termType" class="input-medium">
                <form:option value="">全部</form:option>
                <%--	                <form:option value="1">14天</form:option>
                                    <form:option value="2">3个月</form:option>
                                    <form:option value="3">6个月</form:option>--%>
                <form:option value="4">28天</form:option>
                <form:option value="5">其他</form:option>
            </form:select>
        </li>
        <li class="time_li"><label>还款日期</label>
            <form:input path="repayStart" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>

            <h>-</h>
            <form:input path="repayEnd" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
        </li>
    </ul>
    <ul class="ul-form">
        <li><label>付款渠道</label>
            <form:select path="chlCode" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="BAOFOO">宝付支付</form:option>
                <form:option value="KJTPAY">海尔支付</form:option>
                <form:option value="XIANFENG">先锋支付</form:option>
                <form:option value="XIANXIA">线下支付</form:option>
                <form:option value="alipaypub">支付宝对公</form:option>
                <form:option value="alipay">支付宝</form:option>
                <form:option value="weixin">微信</form:option>
                <form:option value="ccb">建设银行</form:option>
                <form:option value="boc">中国银行</form:option>
                <form:option value="icbc">工商银行</form:option>
                <form:option value="hkb">汉口银行</form:option>
                <form:option value="HENGFENG">恒丰银行</form:option>
            </form:select>
        </li>
        <li><label>付款方式</label>
            <form:select path="txType" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="AUTH_PAY">快捷还款</form:option>
                <form:option value="WITHHOLD">系统代扣</form:option>
                <form:option value="AM_PAY">直接还款</form:option>
                <form:option value="MANPAY">手动还款</form:option>
                <form:option value="WH_ADMIN">手动代扣</form:option>
                <form:option value="AUTO_PAY">主动支付</form:option>
            </form:select>
        </li>
        <li><label>付款分类</label>
            <form:select path="payType" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="1">还款</form:option>
                <form:option value="2">延期</form:option>
                <form:option value="3">加急券</form:option>
                <form:option value="4">购物金</form:option>
                <form:option value="5">旅游券</form:option>
                <form:option value="6">商城购物</form:option>
                <form:option value="7">助贷服务</form:option>
                <form:option value="8">部分代扣</form:option>
                <form:option value="9">手动扣款</form:option>
            </form:select>
        </li>
        <li><label>状态</label>
            <form:select path="status" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="1">成功</form:option>
                <form:option value="0">失败</form:option>
                <form:option value="2">处理中</form:option>
            </form:select>
        </li>
        <li><label>失败原因</label>
            <form:select path="remarkNo" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="1">未开通银联无卡支付</form:option>
                <form:option value="2">不支持该银行</form:option>
                <form:option value="3">联系发卡行</form:option>
                <form:option value="4">金额超限</form:option>
                <form:option value="5">卡状态异常</form:option>
            </form:select>
        </li>

        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
    </ul>
</form:form>
<sys:message content="${message}"/>

<!-- 工具栏 -->
<%-- <c:if test="${fns:haveRole('export') == true}">
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
</c:if> --%>

<table class="table">
    <thead>
    <tr>
        <th>序号</th>
        <th>借款订单号</th>
        <th>合同编号</th>
        <th>扣款订单号</th>
        <th>借款人姓名</th>
        <th>手机号码</th>
        <th>证件号码</th>
        <th>银行名称</th>
        <th>银行卡号</th>
        <th>实还金额</th>
        <!--<th>产品名称</th>-->
        <th>付款渠道</th>
        <th>付款方式</th>
        <th>付款分类</th>
        <th>还款时间</th>
        <th>订单信息</th>
        <th>备注</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item" varStatus="xh">
        <tr>
            <td>
                    ${xh.count}
            </td>
            <td>
                    ${item.applyId}
            </td>
            <td>
                <a href="javascript:void(0)" data-method="offset" data-type="auto"
                   onclick="contentTable_1('${item.contractId}')">${item.contractId}</a>
            </td>
            <td>
                    ${item.id}
            </td>
            <td>
                <c:choose>
                    <c:when test="${item.userId != '' and item.userId != null}">
                        <a href="javascript:void(0)" data-method="offset" data-type="auto"
                           onclick="contentTable_2('${item.userId}','${item.applyId}','detail')">${item.userName}</a>
                    </c:when>
                    <c:otherwise>
                        ${item.userName}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                    ${item.mobile}
            </td>
            <td>
                    ${item.idNo}
            </td>
            <td>
                    ${item.bankName}
            </td>
            <td>
                    ${item.cardNo}
            </td>
            <td>
                    ${item.txAmt}
            </td>
            <!--
            <td>
                    ${item.productName}
            </td>
            -->
            <td class="text-blue">
                    ${item.chlName}
            </td>
            <td class="text-blue">
                <c:choose>
                    <c:when test="${item.txType == 'AUTH_PAY'}">
                        快捷还款
                    </c:when>
                    <c:when test="${item.txType == 'WITHHOLD'}">
                        系统代扣
                    </c:when>
                    <c:when test="${item.txType == 'AM_PAY'}">
                        直接还款
                    </c:when>
                    <c:when test="${item.txType == 'MANPAY'}">
                        手动还款
                    </c:when>
                    <c:when test="${item.txType == 'WH_ADMIN'}">
                        手动代扣
                    </c:when>
                    <c:when test="${item.txType == 'AUTO_PAY'}">
                        主动支付
                    </c:when>
                    <c:when test="${item.txType == 'alipaypub'}">
                        支付宝对公
                    </c:when>
                    <c:when test="${item.txType == 'alipay'}">
                        支付宝
                    </c:when>
                    <c:when test="${item.txType == 'weixin'}">
                        微信支付
                    </c:when>
                    <c:when test="${item.txType == 'ccb'}">
                        建设银行
                    </c:when>
                    <c:when test="${item.txType == 'boc'}">
                        中国银行
                    </c:when>
                    <c:when test="${item.txType == 'icbc'}">
                        工商银行
                    </c:when>
                    <c:when test="${item.txType == 'hkb'}">
                        汉口银行
                    </c:when>
                    <c:when test="${item.txType == 'HENGFENG'}">
                        恒丰银行
                    </c:when>
                    <c:otherwise>
                    </c:otherwise>
                </c:choose>
            </td>
            <td class="text-blue">
                <c:choose>
                    <c:when test="${item.payType == '1'}">
                        还款
                    </c:when>
                    <c:when test="${item.payType == '2'}">
                        延期
                    </c:when>
                    <c:when test="${item.payType == '3'}">
                        加急券
                    </c:when>
                    <c:when test="${item.payType == '4'}">
                        购物金
                    </c:when>
                    <c:when test="${item.payType == '5'}">
                        旅游券
                    </c:when>
                    <c:when test="${item.payType == '6'}">
                        商城购物
                    </c:when>
                    <c:when test="${item.payType == '7'}">
                        助贷服务
                    </c:when>
                    <c:when test="${item.payType == '8'}">
                        部分代扣
                    </c:when>
                    <c:otherwise>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <fmt:formatDate value="${item.txTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                    ${item.orderInfo}
            </td>
            <td>
                <c:choose>
                    <c:when test="${item.status == 'I'}">
                        交易处理中,请稍后查询
                    </c:when>
                    <c:otherwise>
                        ${item.remark}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${item.status == 'SUCCESS' or item.status == 'S'}">
                        <div>成功</div>
                    </c:when>
                    <c:when test="${item.status == 'I'}">
                        <div>处理中</div>
                    </c:when>
                    <c:otherwise>
                        <div class="required">失败</div>
                    </c:otherwise>
                </c:choose>
            </td>
            <td class="text-blue">
                    <%--<c:if test="${item.chlCode == 'XIANFENG' and item.status == 'I' and fns:pastMinutes(item.txTime)>=60}">
                        <a href="javascript:void(0)" onclick="ing2fail('${item.id}')"
                            style="padding-left: 5px;">设置为失败</a>
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

<script id="tpl_creditList_2" type="text/x-handlebars-template">
    <jsp:include page="userInfo.jsp"></jsp:include>
</script>


<script>
    function validateExport() {
        var startTime = $("#repayStart").val();
        var endTime = $("#repayEnd").val();
        var flag1 = false;
        if ("" == startTime || "" == endTime) {
            layer.alert('请选择导出时间', {
                icon: 7
            });
            return false;
        }
        var startTimestamp = new Date(startTime).getTime();
        var endTimestamp = new Date(endTime).getTime();
        if (startTimestamp > endTimestamp) {
            layer.alert('起始日期应在结束日期之前', {
                icon: 7
            });
            return false;
        }
        if ((endTimestamp - startTimestamp) > 31 * 24 * 60 * 60 * 1000) {
            layer.alert('时间段最长为1个月', {
                icon: 7
            });
            return false;
        }
        return exportExcel('${ctx}/loan/repay/exportFlowList',
            '${ctx}/loan/repay/flowList');
    };

    function contentTable_1(contNo) {
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
                    var myTemplate = Handlebars.compile($("#tpl_creditList").html());
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
                    var myTemplate = Handlebars.compile($("#tpl_creditList_2").html());
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

    function ing2fail(id) {
        layer.confirm('您确定将该交易流水修改为失败状态吗？', {
            btn: ['提交', '取消']
        }, function (index) {
            ing2failConfirm(id);
            layer.close(index);
        });
    }

    function ing2failConfirm(id) {
        var param = {
            id: id
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/repay/ing2fail",
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