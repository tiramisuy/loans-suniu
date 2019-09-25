<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>员工信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
	</script>
</head>
<body>
	<!-- 导航菜单 -->
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="pull-left">
			<ol class="breadcrumb page-breadcrumb">
				<li><i class="fa fa-home"></i>&nbsp;<a href="#">一级菜单(请修改)</a></li>
				<li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">二级菜单(请修改)</a></li>
				<li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;员工信息管理</li>
			</ol>
		</div>
		<div class="clearfix"></div>
	</div>
	<!-- 查询条件 -->
	<form:form id="searchForm" modelAttribute="sysEmp" action="${ctx}/test/sysEmp/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>员工号：</label>
				<form:input path="empNo" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="empName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<!-- 工具栏 -->
	<div class="row">
		<div class="col-sm-12 pull-left">
				<!-- 增加按钮 -->
				<button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="openFormDialog('新增用户信息', '${ctx}/test/sysEmp/form', '800px', '500px','officeContent');"><i class="fa fa-plus"></i> 添加</button>
			    <!-- 删除按钮 -->
			    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" onclick="Utils.batch('${ctx}/test/sysEmp/batchDelete','删除','用户信息');"><i class="fa fa-trash-o"> 删除</i></button>
		</div>
	</div>
	<!-- 数据列表 -->
	<table id="contentTable" class="table table-bordered table-condensed">
		<thead>
			<tr>
				<th class="check"><input type="checkbox" name="batchOptControl"></th>
				<th>ID</th>
				<th>员工号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>生日</th>
				<th>备注</th>
				<shiro:hasPermission name="test:sysEmp:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysEmp">
			<tr>
				<td class="check"><input type="checkbox" value="${sysEmp.id}" name="batchOptIds"></td>
				<td><a href="${ctx}/test/sysEmp/form?id=${sysEmp.id}">
					${sysEmp.id}
				</a></td>
				<td>
					${sysEmp.empNo}
				</td>
				<td>
					${sysEmp.empName}
				</td>
				<td>
					${sysEmp.sex}
				</td>
				<td>
					<fmt:formatDate value="${sysEmp.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sysEmp.remark}
				</td>
				<shiro:hasPermission name="test:sysEmp:edit"><td>
					<a href="javascript:void(0);" onclick="openFormDialog('修改员工信息', '${ctx}/test/sysEmp/form?id=${sysEmp.id}', '800px', '500px','mainFrame');">修改</a>
					<a href="${ctx}/test/sysEmp/delete?id=${sysEmp.id}" onclick="return confirmx('确认要删除该员工信息吗？', this.href)">删除</a>
					
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>