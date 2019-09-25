<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 分配角色 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/acl/assign-role.action">
			<input type="hidden" name="page.pageNo" id="pageNo" value="1"/>
			<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
			<input type="hidden" name="page.order" id="order" value="${page.order}"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">用户名</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							<input type="text" maxlength="100" name="filter_LIKES_username" value="${param.filter_LIKES_username }"/>
					</td>
					<td width="12%" class="pn-flabel">姓名</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							<input type="text" maxlength="100" name="filter_LIKES_name" value="${param.filter_LIKES_name }"/>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="button" onclick="Utils.forward('${ctx }/acl/assign-role.action');" value="查询" class="opt-btn" />
					</td>
				</tr>
			</table>
		</form>
		<form id="dataListForm" method="post">
			<table class="pn-ltable" style="" width="100%" cellspacing="1" cellpadding="0" border="0">
				<thead class="pn-lthead">
					<tr>
						<th width="20"><input type='checkbox' onclick='Utils.toggleCheckState("ids")' /></th>
						<th>ID</th>
						<th>用户名<span><input type="hidden" name="fieldName" value="username" disabled="disabled"/> </span></th>
						<th>姓名</th>
						<th>部门编号</th>
						<th>部门名称</th>
						<th>用户状态</th>
						<th>当前角色</th>
						<th>操作选项</th>
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
					<s:iterator value="page.result">
						<tr align="center" style="background-color: D8E5F2;">
							<td><input type='checkbox' name='ids' value='${id}' /></td>
							<td>${id}</td>
							<td>${username}</td>
							<td>${name}</td>
							<td>${deptCode}</td>
							<td>${deptName}</td>
							<td ${status eq 0?'class=red':''}><s:property value="@com.rongdu.loans.service.dict.DictHelper@get(@com.rongdu.loans.service.dict.DictCode@USER_STATE)[status]"/> </td>
							<td width="25%">${roleNames}</td>
							<td>
								<c:if test="${session.hasPermission['AssignRole:assignRole']   }">
								<a href="${ctx }/acl/assign-role!assignRole.action?id=${id }"  class="pn-opt">分配角色</a>	</c:if>						
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscsslist.jsp" %>
 