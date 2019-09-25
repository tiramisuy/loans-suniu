<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>导流平台产品信息管理</title>
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
		<li class="active"><a href="${ctx}/loan/loanTraffic/">导流平台产品信息列表</a></li>
		<!-- <shiro:hasPermission name="loan:loanTraffic:edit"></shiro:hasPermission>
		 -->
		<li><a href="${ctx}/loan/loanTraffic/form">导流平台产品信息添加</a></li>
		
	</ul>
	<form:form id="searchForm" modelAttribute="loanTrafficVO" action="${ctx}/loan/loanTraffic/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%--<ul class="ul-form">
			<li><label>平台名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul> --%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>平台名称</th>
				<th>产品描述</th>
				<th>点击量</th>
				<th>排位</th>
				<th>产品状态</th>
				<th>显示终端</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="loanTraffic">
			<tr>
				<td>
					${loanTraffic.name}
				</td>
				<td>
					${loanTraffic.desc}
				</td>
				<td>
					${loanTraffic.hits}
				</td>
				<td>
					${loanTraffic.scort}
				</td>
				<td>
					${loanTraffic.status ==0?"初始":loanTraffic.status ==1?"正常":loanTraffic.status ==2?"下架":loanTraffic.status}
				</td>
				<td>
					${loanTraffic.remark == "1"?"ios":loanTraffic.remark == "2"?"android":loanTraffic.remark == "3"?"全部":loanTraffic.remark}
				</td>
				<td>
				<a href="javascript:void(0);" onclick="openViewDialog('导流平台产品信息', '${ctx}/loan/loanTraffic/view?id=${loanTraffic.id}', '1200px', '700px',false);">查看</a>
				
				<a href="javascript:void(0);" onclick="openViewDialog('贷超导流统计列表', '${ctx}/loan/loanTraffic/trafficStatisticsList?trafficId=${loanTraffic.id}', '1200px', '700px',false);">统计明细</a>
				
				
				<shiro:hasPermission name="loan:loanTraffic:edit">
				</shiro:hasPermission>
					<a href="${ctx}/loan/loanTraffic/form?id=${loanTraffic.id}">修改</a>
    				
					<!-- <a href="${ctx}/loan/loanTraffic/delete?id=${loanTraffic.id}" onclick="return confirmx('确认要删除该导流平台产品信息吗？', this.href)">删除</a>
				 -->
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>