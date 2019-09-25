<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 后台登录日志 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/log/login-log.action">
			<input type="hidden" name="page.pageNo" id="pageNo" value="1"/>
			<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
			<input type="hidden" name="page.order" id="order" value="${page.order}"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">用户</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" maxlength="20" name="filter_LIKES_username" value="${param.filter_LIKES_username }"/>
					</td>	
					<td width="12%" class="pn-flabel">状态</td>
					<td  width="38%" class="pn-fcontent">
						<s:select list="#{\"Y\":'成功',\"N\":'失败'}" listKey="key" listValue="value" name="filter_EQS_status" value="#parameters.filter_EQS_status"  headerKey="" headerValue="" theme="simple"></s:select>						
					</td>	
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">用户名</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" maxlength="20" name="filter_LIKES_empName" value="${param.filter_LIKES_empName }"/>
					</td>
					<td width="12%" class="pn-flabel">登录时间</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" maxlength="20" name="filter_LIKES_loginTime" value="${param.filter_LIKES_loginTime }"/>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="button" value="查询" onclick="Utils.doSearch('${ctx }/log/login-log.action')" class="opt-btn" />
					</td>
				</tr>
			</table>
		</form>
		<s:actionmessage />
		<form id="dataListForm" method="post">
			<table class="pn-ltable" style="" width="100%" cellspacing="1" cellpadding="0" border="0">
				<thead class="pn-lthead">
					<tr>
						<th>ID</th>
						<th>用户</th>
						<th>用户姓名</th>
						<th>部门编号</th>
						<th>部门名称</th>
						<th>操作</th>
						<th>登录时间</th>
						<th>IP地址</th>
						<th>结果</th>
						<th>操作选项</th>
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
					<s:iterator value="page.result">
						<tr align="center">
						<td>${id}</td>
						<td>${username}</td>
						<td>${empName}</td>
						<td>${deptCode}</td>
						<td>${deptName}</td>
						<td>${opt}</td>
						<td>${loginTime}</td>
						<td>${ip}</td>
						<td>${result}</td>
							<td>
							<c:if test="${session.hasPermission['LoginLog:delete'] }">
								<input type="button" onclick="Utils.forward('${ctx }/log/login-log!delete.action?id=${id}')" title="删除" class="opt-delete">
							</c:if>
							<c:if test="${session.hasPermission['LoginLog:view'] }">
									<input type="button" onclick="window.open('${ctx }/log/login-log!viewLog.action?id=${id}')" title="查看" class="opt-view">
							</c:if>						
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>
			<div class="ps-btn-opt">
			    <c:if test="${session.hasPermission['LoginLog:batchDelete'] }">
				<input type="button" class="opt-btn" value="批量删除"   onclick="Utils.batch('${ctx }/log/login-log!batchDelete.action','删除','登录日志')"/></c:if>
				<c:if test="${session.hasPermission['LoginLog:exportExcel'] }"> 
				<input type="button" class="opt-btn" value="导出excel"  onclick="Utils.doImport('${ctx }/log/login-log!exportExcel.action');"/> </c:if>
			</div>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscsslist.jsp" %>