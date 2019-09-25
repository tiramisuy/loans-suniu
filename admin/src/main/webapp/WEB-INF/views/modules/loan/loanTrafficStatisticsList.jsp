<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>贷超导流统计管理</title>
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
	<form:form id="searchForm" modelAttribute="loanTrafficStatisticsOP" action="${ctx}/loan/loanTraffic/trafficStatisticsList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<input id="trafficId" name="trafficId" type="hidden" value="${loanTrafficStatisticsOP.trafficId}"/>
			<li><label>统计时间：</label>
				<input name="beginStatsDate" style="width: 100px;" type="text" readonly="readonly" maxlength="11" class="input-medium Wdate"
					value="${loanTrafficStatisticsOP.beginStatsDate}"
					onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:false});"/> - 
				
				<input name="endStatsDate" style="width: 100px;" type="text" readonly="readonly" maxlength="11" class="input-medium Wdate"
					value="${loanTrafficStatisticsOP.endStatsDate}"
					onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>贷超产品名称</th>
				<th>点击数</th>
				<th>统计时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="loan:loanTrafficStatistics:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="loanTrafficStatistics">
			<tr>
				<td>
					${loanTrafficStatistics.trafficName}
				</td>
				<td>
					${loanTrafficStatistics.hits}
				</td>
				<td>
					${loanTrafficStatistics.statsDate}
				</td>
				<td>
					${loanTrafficStatistics.remark}
				</td>
				<%--
				<shiro:hasPermission name="loan:loanTrafficStatistics:edit"><td>
					<a href="javascript:void(0);" onclick="openFormDialog('贷超导流统计', '${ctx}/loan/loanTrafficStatistics/form?id=${loanTrafficStatistics.id}', '800px', '480px','mainFrame');">修改</a>
    				<a href="javascript:void(0);" onclick="openViewDialog('贷超导流统计', '${ctx}/loan/loanTrafficStatistics/view?id=${loanTrafficStatistics.id}', '800px', '480px');">查看</a>
					<a href="${ctx}/loan/loanTrafficStatistics/delete?id=${loanTrafficStatistics.id}" onclick="return confirmx('确认要删除该贷超导流统计吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
				 --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>