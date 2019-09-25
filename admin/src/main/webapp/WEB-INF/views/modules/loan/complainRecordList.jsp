<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>投诉工单管理</title>
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
		<li class="active"><a href="${ctx}/loan/complainRecord/">投诉工单列表</a></li>
		<shiro:hasPermission name="loan:complainRecord:edit"><li><a href="${ctx}/loan/complainRecord/form">投诉工单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="complainRecord" action="${ctx}/loan/complainRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>状态：</label>
				<form:select path="status" htmlEscape="false" maxlength="2" class="input-medium">
				<form:option value="">全部</form:option>
				<form:option value="1">未跟进</form:option>
				<form:option value="2">已跟进未完结</form:option>
				<form:option value="3">已完结</form:option>		
				</form:select>
			</li>
			
			<li><label>部门：</label>
				<form:select path="type" htmlEscape="false" maxlength="2" class="input-medium">
					<form:option value="">全部</form:option>
					<form:option value="2002">风控</form:option>
					<form:option value="2006">催收</form:option>
					<form:option value="2007">客服</form:option>
					<form:option value="2009">质检</form:option>
					<form:option value="2011">预提醒</form:option>
					<form:option value="2012">电销</form:option>
					
				</form:select>
			</li>
			
		<%-- 	<shiro:lacksRole name="complain_handle_user"> --%>
			
			<li><label>跟进人：</label>
				<form:select path="handleUserId" htmlEscape="false" maxlength="50" class="input-medium">
					<form:option value="">全部</form:option>
					<c:forEach items="${handleUserList}" var="item">
	                    <form:option value="${item.id}">${item.name}</form:option>	                   
	                </c:forEach>
				</form:select>
			</li>
			<li><label>提交人：</label>
				<form:select path="createBy" htmlEscape="false" maxlength="50" class="input-medium">
					<form:option value="">全部</form:option>
					<c:forEach items="${handleUserList}" var="item">
	                    <form:option value="${item.name}">${item.name}</form:option>	                   
	                </c:forEach>
				</form:select>
			</li>
		<%-- 	</shiro:lacksRole> --%>
			<li><label>投诉性质：</label>
				<form:select path="emergency" htmlEscape="false" maxlength="2" class="input-medium">
					<form:option value="">全部</form:option>
					<form:option value="1">一般</form:option>
					<form:option value="2">紧急</form:option>
				</form:select>
			</li>
			<li><label>用户名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>手机号码：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			
			<li class="time_li"><label>创建时间</label>
                <form:input id='startTime' path="startTime" type="text" readonly="readonly" maxlength="20"
                            class="input-middle Wdate" 
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
           
                <h>-</h>
                <form:input id='endTime' path="endTime" type="text" readonly="readonly" maxlength="20" class="input-middle Wdate"
                            
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<c:if test="${fns:haveRole('complain') == true}">
		<div class="row">
			<div class="col-sm-12 pull-left">
				<!-- 导出按钮 -->
<!-- 				<button id="btnExport" class="btn btn-white btn-sm" id="btnImport"
					data-toggle="tooltip" data-placement="left"
					onclick="return validateExport()">
					<i class="fa fa-file-excel-o"></i> 导出
				</button> -->
			</div>
		</div>
	</c:if>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>创建时间</th>
				<th>提交人</th>
				<th>投诉渠道</th>
				<th>标题</th>
				<th>用户名称</th>
				<th>手机号码</th>
				<th>状态</th>
				<th>部门</th>
				<th>投诉类型</th>
				<th>投诉点</th>
				<th>紧急情况</th>
				<th>跟进人</th>
				<shiro:hasPermission name="loan:complainRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="complainRecord">
			<tr>
				<td>
					<fmt:formatDate value="${complainRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${complainRecord.createBy}
				</td>
				<td>
					${complainRecord.channel}
				</td>
				<td>
					${complainRecord.subject}
				</td>
				<td>
					${complainRecord.name}
				</td>
				<td>
					${complainRecord.mobile}
				</td>
				<td>
					${complainRecord.status == 1?"未跟进":complainRecord.status == 2?"已跟进未完结":complainRecord.status == 3?"已完结":""}					 
				</td>
				<td>
				   ${complainRecord.type == "2002"?"风控":complainRecord.type == "2006"?"催收":complainRecord.type == "2007"?"客服":
				   complainRecord.type == "2009"?"质检":complainRecord.type == "2011"?"预提醒":complainRecord.type == "2012"?"电销":""}	
				</td>
				<td>
					${complainRecord.complainType == "3110"?"服务态度":complainRecord.complainType == "3120"?"承诺未兑现":complainRecord.complainType == "3130"?"业务操作错误":
					complainRecord.complainType == "3140"?"业务规则":complainRecord.complainType == "3150"?"提供错误信息":complainRecord.complainType == "3160"?"业务流程":complainRecord.complainType == "3170"?"其他":complainRecord.complainType}
				</td>
				<td>
					${complainRecord.complainPoint }
				</td>
				<td>
					${complainRecord.emergency == 1?"一般":complainRecord.emergency == 2?"紧急":""}		
				</td>
				<td>
					${complainRecord.handleUserName}
				</td>
				<shiro:hasPermission name="loan:complainRecord:edit"><td>
				<a href="javascript:void(0);" onclick="openViewDialog('投诉工单', '${ctx}/loan/complainRecord/view?id=${complainRecord.id}&handleUserId=${complainRecord.handleUserId }', '1000px', '700px',false);">查看</a>
					
					<c:choose>
							<c:when test="${lognUName ==complainRecord.createBy or lognUName == complainRecord.handleUserName}">
			    				<c:if test="${complainRecord.message==1 or complainRecord.status ==1  or complainRecord.status ==2 }">
									<a href="${ctx}/loan/complainRecord/form?id=${complainRecord.id}">修改</a>
								<shiro:lacksRole name="complain_handle_user"> 		<!-- 当用户不属于该角色时才显示 -->
									<a href="${ctx}/loan/complainRecord/delete?id=${complainRecord.id}" onclick="return confirmx('确认要删除该投诉工单吗？', this.href)">删除</a>
								</shiro:lacksRole>
			    				</c:if>
			    			</c:when>
						<c:otherwise>
								<c:if test="${complainRecord.status ==1 or complainRecord.status ==2}">
									<c:if test="${lognUName ==complainRecord.createBy or lognUName == complainRecord.handleUserName}">
											<a href="${ctx}/loan/complainRecord/form?id=${complainRecord.id}">修改</a>
									</c:if>
								</c:if>
								<shiro:lacksRole name="complain_handle_user"> 		<!-- 当用户不属于该角色时才显示 -->
									<a href="${ctx}/loan/complainRecord/delete?id=${complainRecord.id}" onclick="return confirmx('确认要删除该投诉工单吗？', this.href)">删除</a>
								</shiro:lacksRole>
						</c:otherwise>					
					</c:choose>
					<a href="#" style="color: #0007;">${complainRecord.message == 1?"未读":complainRecord.message == 2?"已读":""}</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script>
	
	function validateExport() {
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if ("" == startTime && "" == endTime) {
			layer.alert('请选择导出时间', {
				icon : 7
			});
			return false;
		};
		if (("" == startTime && "" != endTime) || ("" != startTime && "" == endTime)) {
			layer.alert('请选择时间段', {
				icon : 7
			});
			return false;
		};
		var startTimestamp = new Date(startTime).getTime();
		var endTimestamp = new Date(endTime).getTime();
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
		return exportExcel(
				'${ctx}/loan/complainRecord/exportComplainRecord',
				'${ctx}/loan/complainRecord/');
	};
	</script>
</body>
</html>