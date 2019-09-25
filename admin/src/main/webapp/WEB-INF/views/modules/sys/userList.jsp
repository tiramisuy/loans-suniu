<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
	    
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/sys/user/import/template">下载模板</a>
		</form>
	</div>
	<!--BEGIN TITLE & BREADCRUMB PAGE-->
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="pull-left">
			<ol class="breadcrumb page-breadcrumb">
				<li><i class="fa fa-home"></i>&nbsp;<a href="#">系统设置</a></li>
				<li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">机构用户</a></li>
				<li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;用户管理</li>
			</ol>
		</div>
		<div class="clearfix"></div>
	</div>
	<!--END TITLE & BREADCRUMB PAGE-->

	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>归属公司：</label><sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}" 
				title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/></li>
			<li><label>登录名：</label><form:input path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/></li>			
			<li class="clearfix"></li>
			<li><label>归属部门：</label><sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}" 
				title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/></li>
			<li><label>姓&nbsp;&nbsp;&nbsp;名：   </label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<!-- 表单  -->
	<sys:message content="${message}"/>
	<!-- 工具栏 -->
	<div class="row">
		<div class="col-sm-12 pull-left">
				<!-- 增加按钮 -->
				<button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="openFormDialog('新增用户信息', '${ctx}/sys/user/form?id=${user.id}', '800px', '500px','officeContent');"><i class="fa fa-plus"></i> 添加</button>
			    <!-- 删除按钮 -->
			    <button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" onclick="Utils.batch('${ctx}/sys/user/batchDelete','删除','用户信息');"><i class="fa fa-trash-o"> 删除</i></button>
			    <!-- 导入按钮 -->
			    <button id="btnImport" class="btn btn-white btn-sm"  data-toggle="tooltip" data-placement="left" onclick="add()" ><i class="fa fa-folder-open-o"></i> 导入</button>
			    <!-- 导出按钮 -->
			    <button id="btnExport" class="btn btn-white btn-sm"  data-toggle="tooltip"  data-placement="left" onclick="return exportExcel('${ctx}/sys/user/export','${ctx}/sys/user/list');"><i class="fa fa-file-excel-o"></i> 导出</button>
		</div>
	</div>
	<table id="contentTable" class="table">
		<thead>
			<tr>
			<th class="check"><input type="checkbox" name="batchOptControl"></th>
			<th>归属公司</th>
			<th>归属部门</th>
			<th class="sort-column login_name">登录名</th>
			<th>工号</th>
			<th class="sort-column name">姓名</th>
			<th>电话</th>
			<th>手机/qq</th>
			<%--<th>角色</th> --%>
			<th>状态</th>
			<shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td class="check"><input type="checkbox" value="${user.id}" name="batchOptIds"></td>
				<td>${user.company.name}</td>
				<td>${user.office.name}</td>
				<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
				<td>${user.no}</td>
				<td>${user.name}</td>
				<td>${user.phone}</td>
				<td>${user.mobile}</td><%--
				<td>${user.roleNames}</td> --%>
				<td>
					<c:if test="${user.loginFlag eq '1'}"><span class="text-success">启用</span></c:if>
					<c:if test="${user.loginFlag eq '0'}"><span class="text-error">禁用</span></c:if>
				</td>
				<shiro:hasPermission name="sys:user:edit"><td>
    				<a href="javascript:void(0);" onclick="openFormDialog('修改用户信息', '${ctx}/sys/user/form?id=${user.id}', '800px', '500px','officeContent');">修改</a>
					<a href="${ctx}/sys/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>