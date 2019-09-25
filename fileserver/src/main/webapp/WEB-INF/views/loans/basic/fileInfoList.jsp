<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>影像资料管理</title>
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
		<li class="active"><a href="${ctx}/basic/fileInfo/">影像资料列表</a></li>
		<shiro:hasPermission name="basic:fileInfo:edit"><li><a href="${ctx}/basic/fileInfo/form">影像资料添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="fileInfo" action="${ctx}/basic/fileInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>备注信息</th>
				<th>创建时间</th>
				<shiro:hasPermission name="basic:fileInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fileInfo">
			<tr>
				<td><a href="${ctx}/basic/fileInfo/form?id=${fileInfo.id}">
					${fileInfo.remark}
				</a></td>
				<td>
					<fmt:formatDate value="${fileInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="basic:fileInfo:edit"><td>
					<a href="javascript:void(0);" onclick="openFormDialog('影像资料', '${ctx}/basic/fileInfo/form?id=${fileInfo.id}', '800px', '480px','mainFrame');">修改</a>
    				<a href="javascript:void(0);" onclick="openViewDialog('影像资料', '${ctx}/basic/fileInfo/view?id=${fileInfo.id}', '800px', '480px');">查看</a>
					<a href="${ctx}/basic/fileInfo/delete?id=${fileInfo.id}" onclick="return confirmx('确认要删除该影像资料吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>