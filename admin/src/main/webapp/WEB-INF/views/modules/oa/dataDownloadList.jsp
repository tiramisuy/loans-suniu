<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据下载表管理</title>
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
		<li class="active"><a href="${ctx}/oa/dataDownload/">数据下载表列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="dataDownloadOP" action="${ctx}/oa/dataDownload/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="time_li"><label>时间</label>
            <form:input path="startTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
        
            <h>-</h>
            <form:input path="endTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
        </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>标题</th>
				<th>类型</th>
				<th>链接</th>
				<th>备注信息</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<shiro:hasPermission name="oa:dataDownload:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dataDownload">
			<tr>
				<td>
					${dataDownload.title}
				</td>
				
				<td>
					<c:forEach items="${typeList}" var="item">
						<c:if test="${dataDownload.type eq item.value}">${item.desc}</c:if>
					</c:forEach>
				</td>
				
				<td>
					<a href="https://www.51cunzheng.com/searchResult?r=${dataDownload.dataUrl}" target="view_window"/>https://www.51cunzheng.com/searchResult?r=${dataDownload.dataUrl}
				</td>
				
				<td>
					${dataDownload.remark}
				</td>
				<td>
					<fmt:formatDate value="${dataDownload.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${dataDownload.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="oa:dataDownload:edit"><td>
					<%-- <a href="javascript:void(0);" onclick="openFormDialog('数据下载表', '${ctx}/oa/dataDownload/form?id=${dataDownload.id}', '800px', '480px','mainFrame');">修改</a> --%>
    				<a href="javascript:void(0);" onclick="openViewDialog('数据下载表', '${ctx}/oa/dataDownload/view?id=${dataDownload.id}', '800px', '480px');">查看</a>
					<a href="${ctx}/oa/dataDownload/delete?id=${dataDownload.id}" onclick="return confirmx('确认要删除该数据下载表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>