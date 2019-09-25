<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<title>逾期统计</title>
<meta name="decorator" content="default" />

<link rel="stylesheet"
	href="${ctxStatic}/layui-master/build/css/layui.css">
<link href="${ctxStatic}/position/basic.css" rel="stylesheet" />
<link href="${ctxStatic}/position/hjd-sh.css" rel="stylesheet" />

<%--<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>--%>
<script src="${ctxStatic}/layui-master/src/layui.js"></script>
<script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>

<script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.metadata.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.js"
	type="text/javascript"></script>
<style type="text/css">
.store-table {
	margin: 0 auto;
	width: 90%;
	text-align: center;
	border-top: 1px solid #cccccc;
	border-left: 1px solid #cccccc;
}

.store-table th,.store-table td {
	line-height: 40px;
	border-bottom: 1px solid #cccccc;
	border-right: 1px solid #cccccc;
}
</style>

<script type="text/javascript">
	$(document).ready(function() {

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
		<li class="active">逾期统计</li>
	</ul>

	<!--搜索条件  s-->
	<div class="operation-search">
		<!--END TITLE & BREADCRUMB PAGE-->
		<form:form id="searchForm" modelAttribute="overdueCountOP"
			action="${ctx}/loan/apply/overdueCount" method="post"
			class="breadcrumb form-search layui-form">

			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden"
				value="${page.pageSize}" />

			<ul class="ul-form">
				<li><label>催收员姓名</label> <form:input path="operatorName"
						htmlEscape="false" maxlength="20" class="input-medium" /></li>
				<li class="time_li"><label>逾期时间</label> <form:input id='searchStrat'
						path="searchStrat" type="text" readonly="readonly" maxlength="20"
						class="input-middle Wdate" value="${searchStrat}"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});" />
				<h>-</h> <form:input id='searchEnd' path="searchEnd"
						type="text" readonly="readonly" maxlength="20"
						class="input-middle Wdate" value="${searchEnd}"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});" />
				</li>
				<li class="btns"><input id="btnSubmit" class="btn btn-primary"
					type="submit" value="查询" /></li>
			</ul>

		</form:form>

		<table class="store-table">
			<colgroup>
				<col width="200">
				<col width="200">
				<col width="200">
				<col width="150">
			</colgroup>
			<thead>
				<tr style="color: #000000">
					<th>催收员</th>
					<th>分单数</th>
					<th>应还金额</th>
					<th>已还笔数</th>
					<th>已还金额</th>
					<th>已还罚息</th>
					<th>未还笔数</th>
					<th>未还金额</th>
					<th>展期笔数</th>
					<th>展期金额</th>
					<th>已换比例</th>
					<th>未还比例</th>
					<th>展期比例</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="item" varStatus="xh">
					<tr style="color: #000000; text-align: center;">
						<td>${item.operatorName}</td>
						<td>${item.stageNum}</td>
						<td>${item.principal}</td>
						<td>${item.backNum + item.mandelay}</td>
						<td>${item.payedPrincipal + item.payedInterest + item.overdueFee - item.deduction}</td>
						<td>${item.overdueFee}</td>
						<td>${item.stageNum - item.backNum}</td>
						<td>${item.unpayPrincipal + item.unpayInterest + item.overdueFee}</td>
						<td>${item.mandelay}</td>
						<td><%-- ${item.principal + item.overdueFee - item.deduction} --%>
							${item.mandelayFee }
						</td>
						<td><fmt:formatNumber type="number"
								value="${item.backNum*100/item.stageNum}" pattern="0.00"
								maxFractionDigits="2" />%</td>
						<td><fmt:formatNumber type="number"
								value="${(item.stageNum-item.backNum)*100/item.stageNum}"
								pattern="0.00" maxFractionDigits="2" />%</td>
						<td><fmt:formatNumber type="number"
								value="${item.mandelay*100/item.backNum}" pattern="0.00"
								maxFractionDigits="2" />%</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="pagination" style="width: 95%;" align="center">${page}</div>


		<script>
			layui.use([ 'form', 'element' ], function() {
				var $ = layui.jquery, element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块

				var form = layui.form()
			});
		</script>
</body>

</html>