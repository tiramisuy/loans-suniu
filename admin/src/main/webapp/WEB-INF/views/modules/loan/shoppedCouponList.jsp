<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>购物券详情</title>
	<meta name="decorator" content="default"/>
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
		<li class="active"><a href="${ctx}/loan/shopcoupon/list">购物券详情</a></li>	
	</ul>
	<!--BEGIN TITLE & BREADCRUMB PAGE-->
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="pull-left">
			<ol class="breadcrumb page-breadcrumb">
				<li><i class="fa fa-home"></i>&nbsp;<a href="#">运营管理</a></li>
				<li class="active">&nbsp;<i class="fa fa-angle-right"></i>
						&nbsp;购物券详情
				</li>
			</ol>
		</div>
		<div class="clearfix"></div>
	</div>
	
	<form:form id="searchForm" modelAttribute="ShoppedCouponOP" action="${ctx}/loan/shopcoupon/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<li><label>姓名</label> 
		<form:input path="userName" htmlEscape="false" maxlength="20" class="input-medium" /></li>
		<li><label>手机号码</label> 
		<form:input path="mobile" htmlEscape="false" maxlength="11" class="input-medium" /></li>
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" /></li>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>订单编号</th>
				<th>客户姓名</th>
				<th>手机号码</th>
				<th>申请时间</th>
				<th>卡券名称</th>
				<th>卡券金额</th>
				<th>卡券发放时间</th>
				<th>卡券过期时间</th>
				<th>卡券状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td>${item.applyId }</td>
				<td>${item.userName }</td>
				<td>${item.mobile }</td>
				<td>${item.approveTime }</td>
				<td>${item.couponName }</td>
				<td>${item.amount }</td>
				<td>${item.startTime }</td>
				<td>${item.endTime }</td>
				<td>${item.status == 0 ? "未使用" : "已使用" }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>