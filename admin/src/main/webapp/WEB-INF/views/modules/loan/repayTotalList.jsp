<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>还款明细</title>
    <meta name="decorator" content="default"/>
    <script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>
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
        <a href="${ctx}/loan/repay/repayTotalList">还款总账</a>
    </li>


</ul>
<!--BEGIN TITLE & BREADCRUMB PAGE-->
<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
    <div class="pull-left">
        <ol class="breadcrumb page-breadcrumb">
            <li><i class="fa fa-home"></i>&nbsp;<a href="#">贷后管理</a></li>
            <li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">还款管理</a></li>
            <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;还款总账</li>
        </ol>
    </div>
    <div class="clearfix"></div>
</div>
<!--END TITLE & BREADCRUMB PAGE-->

<form:form id="searchForm" modelAttribute="repayOP" action="${ctx}/loan/repay/repayTotalList" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>合同编号</label>
            <form:input path="contNo" htmlEscape="false"  class="input-medium"/>
        </li>
        <li><label>姓名</label>
            <form:input path="userName" htmlEscape="false" maxlength="20" class="input-medium"/>
        </li>
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
	                <form:option value="1">14天</form:option>
	                <form:option value="2">3个月</form:option>
	                <form:option value="3">6个月</form:option>
	                <form:option value="4">28天</form:option>
	                <form:option value="5">其他</form:option>
	            </form:select>
	       </li>
        <li class="time_li"><label>借款时间</label>
            <form:input id="borrowStart" path="borrowStart" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
        
            <h>-</h>
            <form:input id="borrowEnd" path="borrowEnd" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
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
					onclick="return exportExcel('${ctx}/loan/repay/repayTotalexport','${ctx}/loan/repay/repayTotalList');"
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
        <th>合同编号</th>
        <th>放款时间</th>
        <th>客户姓名</th>
        <th>合同金额</th>
        <th>合同期数</th>
        <th>月利率</th>
        <th>签署日期</th>
        <th>终止日期</th>
        <th>还款方式</th>
        <th>放款金额</th>
        <th>管理费率</th>
        <th>管理费</th>
        <th>还款次数</th>
        <th>期利率</th>
        <th>期还款额</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item" varStatus="xh">
    	<tr>
            <td>${xh.count}</td>
            <td><%--<a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="contentTable('${item.contNo}');">--%>${item.contNo}</td>
            <td>${item.loanStartDate}</td>
            <td>${item.userName}</td>
            <td>${item.approveAmt}</td>
            <td>
               ${item.thisTerm}
	           <%-- <c:choose>
	                <c:when test="${item.productId == 'XJD' or item.productId == 'TFL' or item.productId == 'LYFQ' or item.productId == 'XJDFQ'}">
	                    ${item.thisTerm}
	                </c:when>
	                <c:otherwise>
	                    <fmt:formatNumber type="number" value="${item.thisTerm/2}" maxFractionDigits="0"/>
	                </c:otherwise>
	            </c:choose>--%>
            </td>
            <td>
            	<c:choose>
	                <c:when test="${item.productId == 'XJD'}">
	                    ${item.basicRate/365}
	                </c:when>
	                <c:otherwise>
	                    ${item.basicRate/12}
	                </c:otherwise>
	            </c:choose>
            </td>
            <td>${item.loanStartDate}</td>
            <td><fmt:formatDate value="${item.loanEndDate}" pattern="yyyy-MM-dd"/></td>
            <td>${item.repayMethodStr}</td>
            <td>${item.approveAmt}</td>
            <td>${item.servFeeRateStr}</td>
            <td>${item.servFee}</td>
            <td>${item.thisTerm}</td>
            <td>
            	<c:if test="${item.productId == 'XJD'}">${item.basicRate/365}</c:if>
            	<c:if test="${item.productId == 'TFL' or item.productId == 'LYFQ' or item.productId == 'XJDFQ'}">${item.basicRate/12}</c:if>
            	<c:if test="${item.productId == 'CCD'}">${item.basicRate/24}</c:if>
            </td>
            <td>${item.totalAmount}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="pagination">${page}</div>

<script id="tpl_creditList" type="text/x-handlebars-template">
    <jsp:include page="repayList.jsp"></jsp:include>
</script>

<script>

	function validateExport() {
		var startTime = $("#borrowStart").val();
		var endTime = $("#borrowEnd").val();
		var startTimestamp = new Date(startTime).getTime();
		var endTimestamp = new Date(endTime).getTime();
		if ("" == startTime && "" == endTime) {
			layer.alert('请选择导出时间', {
				icon : 7
			});
			return false;
		}
		if (("" == startTime && "" != endTime) || ("" != startTime && "" == endTime)) {
			layer.alert('请选择时间段', {
				icon : 7
			});
			return false;
		}
		if (startTimestamp > endTimestamp) {
			layer.alert('起始日期应在结束日期之前', {
				icon : 7
			});
			return false;
		}
		if ((endTimestamp - startTimestamp) > 31 * 24 * 60 * 60 * 1000) {
			layer.alert('时间段最长为一个月', {
				icon : 7
			});
			return false;
		}
		return exportExcel('${ctx}/loan/repay/repayTotalexport','${ctx}/loan/repay/repayTotalList');
	};

	function contentTable(contNo) {
		var param = {
			contNo : contNo
		};
		$.ajax({
			type : "post",
			url : "${ctx}/loan/repay/repayPlanItemList",
			data : param,
			dataType : "json",
			error : function(request) {
				alert("系统繁忙,请稍后再试");
			},
			success : function(data, textStatus) {
				if (data.code == "1") {
					var myTemplate = Handlebars.compile($("#tpl_creditList")
							.html());
					var html = myTemplate(data.data);
					layer.open({
						type : 1,
						title : '还款计划',
						area : [ '95%', '50%' ], //宽高
						content : html
					})
				} else {
					alert(data.msg)
				}

			},

		});
	};
</script>
</body>
</html>