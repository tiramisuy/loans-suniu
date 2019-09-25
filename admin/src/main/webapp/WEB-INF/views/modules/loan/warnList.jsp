<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>还款-预提醒表管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet"
		  href="${ctxStatic}/layui-master/build/css/layui.css">
	<script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>
	<script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js"
			type="text/javascript"></script>
	<script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/loan/repay/warnList">还款预提醒列表</a></li>
	</ul>
	<!--BEGIN TITLE & BREADCRUMB PAGE-->
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="pull-left">
			<ol class="breadcrumb page-breadcrumb">
				<li><i class="fa fa-home"></i>&nbsp;<a href="#">贷后管理</a></li>
				<li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">还款管理</a></li>
				<li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;还款预提醒</li>
			</ol>
		</div>
		<div class="clearfix"></div>
	</div>
	<!--END TITLE & BREADCRUMB PAGE-->

	<form:form id="searchForm" modelAttribute="warnOP" action="${ctx}/loan/repay/warnList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名</label> <form:input path="userName" htmlEscape="false" maxlength="20" class="input-medium" /></li>
			<li><label>手机号码</label> <form:input path="mobile" htmlEscape="false" maxlength="11" class="input-medium" /></li>
			<li><label>审核人</label> <form:select path="sysUserId" class="input-medium">
				<form:option value="">全部</form:option>
				<c:forEach items="${auditor}" var="detail">
					<c:choose>
						<c:when test="${warnOP.sysUserId == detail.id}">
							<form:option value="${detail.id}" selected="selected">${detail.name}</form:option>
						</c:when>
						<c:otherwise>
							<form:option value="${detail.id}">${detail.name}</form:option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				</form:select></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>借款人姓名</th>
				<th>手机号码</th>
				<th>应还日期</th>
				<th>应还总金额</th>
				<th>实还时间</th>
				<th>实还金额</th>
				<th>预提醒人员</th>
				<th>预提醒内容</th>
				<th>预提醒结果</th>
				<th style="min-width: 135px;">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item" varStatus="xh">
			<tr>
				<td>${xh.count}</td>
				<td>${item.userName}</td>
				<td>${item.mobile}</td>
				<td>${item.repayDate}</td>
				<td>${item.totalAmount}</td>
				<td>${item.actualRepayTime}</td>
				<td>${item.actualRepayAmt}</td>
				<td>${item.sysUserName}</td>
				<td>${item.content}</td>
				<td>${item.warnResult}</td>
				<td><a href="javascript:void(0)" data-method="offset" data-type="auto" style="padding-left: 5px;color: #0B61A4"
					   onclick="warnContent('${item.id}','${item.content }')">预提醒</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script id="warn_detail" type="text/x-handlebars-template">
		<jsp:include page="warnDetail.jsp"/>
	</script>
	<script>
		function warnContent(repayId, content) {
			var data = {
				repayId : repayId,
				content : content,
				conarr : content.split(">")
			}
			var myTemplate = Handlebars.compile($("#warn_detail").html());
			var html = myTemplate(data);
			layer.open({
				type : 1,
				title : '预提醒',
				area : [ '80%', '70%' ],
				content : html
			});
		};
		function warnConfirm(id, oldContent) {
			var content = $("#content").val();
			var param = {
				id : id,
				content : content,
				oldContent : oldContent
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/repay/warnConfirm",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
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