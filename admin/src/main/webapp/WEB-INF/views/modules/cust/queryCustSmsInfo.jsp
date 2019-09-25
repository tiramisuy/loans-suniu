<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>短信查询</title>
<meta name="decorator" content="default" />
<script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>
<script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>
</head>
<body>

	<c:set var="productList" value="${fns:getProductList()}" />
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/custUser/getCustUserSms">短信查询</a></li>
	</ul>
	<!--BEGIN TITLE & BREADCRUMB PAGE-->
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="pull-left">
			<ol class="breadcrumb page-breadcrumb">
				<li><i class="fa fa-home"></i>&nbsp;<a href="#">客户管理</a></li>
				<li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;短信查询
				</li>
			</ol>
		</div>
		<div class="clearfix"></div>
	</div>
	<!--END TITLE & BREADCRUMB PAGE-->

	<form:form id="searchForm" modelAttribute="querySmsOP"
		action="${ctx}/sys/custUser/getCustUserSms" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>手机号码</label> <form:input path="mobile"
					htmlEscape="false" maxlength="11" class="input-medium" /></li>


					<li><label>产品</label> <form:select path="productId"
							class="input-medium">
							<form:option value="">全部</form:option>
							<c:forEach items="${productList}" var="detail">
								<form:option value="${detail.id}">${detail.name}</form:option>
							</c:forEach>
						</form:select></li>
				<
				<li><label>短信类型</label> <form:select path="type"
						class="input-medium">
						<form:option value="">全部</form:option>
						<form:option value="code">验证码短信</form:option>
						<form:option value="warn">还款提醒短信</form:option>
					</form:select></li>

			<li class="time_li"><label>发送时间</label> <form:input id='sendStart'
					path="sendStart" type="text" readonly="readonly" maxlength="20"
					class="input-middle Wdate" value="${sendStart}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});" />
			<h>-</h> <form:input id='sendEnd' path="sendEnd"
					type="text" readonly="readonly" maxlength="20"
					class="input-middle Wdate" value="${sendEnd}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});" />
			</li>
			<input type="hidden" name="first" value="false" />
			<li>&nbsp;&nbsp;&nbsp;<input id="btnSubmit"
				class="btn btn-primary" type="submit" value="查询" />
			</li>
		</ul>
	</form:form>

<%-- 	<c:if test="${fns:haveRole('export') == true}">
		<div class="row">
			<div class="col-sm-12 pull-left">
				<!-- 导出按钮 -->
				<button id="btnExport" class="btn btn-white btn-sm" id="btnImport"
					data-toggle="tooltip" data-placement="left"
					onclick="return exportExcel('${ctx}/sys/custUser/exportSms','');"
					onclick="return validateExport()">
					<i class="fa fa-file-excel-o"></i> 导出
				</button>
			</div>
		</div>
	</c:if> --%>
	<sys:message content="${message}" />

	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>客户手机号</th>
				<th>短信内容</th>
				<th>发送时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="item" varStatus="xh">
				<tr>
					<td>${item.mobile }</td>
					<td>${item.smsCode}</td>
					<td><fmt:formatDate value="${item.sendTime }"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script>
		function validateExport() {
			var startTime = $("#sendStart").val();
			var endTime = $("#sendEnd").val();
			var startTimestamp = new Date(startTime).getTime();
			var endTimestamp = new Date(endTime).getTime();
			if ("" == startTime && "" == endTime) {
				layer.alert('请选择导出时间', {
					icon : 7
				});
				return false;
			}
			if (("" == startTime && "" != endTime)
					|| ("" != startTime && "" == endTime)) {
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
			return exportExcel('${ctx}/sys/custUser/exportSms', '');
		};
	</script>
</body>
</html>