<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">拥有【${page.result[0].roleName }】角色的用户</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/acl/v-user-role.action">
			<input type="hidden" name="page.pageNo" id="pageNo" value="1"/>
			<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
			<input type="hidden" name="page.order" id="order" value="${page.order}"/>
			<input type="hidden"  name="filter_EQL_roleId" value="${param.filter_EQL_roleId}"/>								
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">用户名</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							<input type="text" maxlength="20" name="filter_LIKES_username" value="${param.filter_LIKES_username}"/>
					</td>
					<td width="12%" class="pn-flabel">姓名</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							<input type="text" maxlength="20" name="filter_LIKES_name" value="${param.filter_LIKES_name}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">部门编号</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							<input type="text" maxlength="20"  name="filter_LIKES_deptCode" value="${param.filter_LIKES_deptCode}"/>
					</td>
					<td width="12%" class="pn-flabel">部门名称</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							<input type="text" maxlength="20" name="filter_LIKES_deptName" value="${param.filter_LIKES_deptName}"/>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="button" onclick="Utils.doSearch('${ctx }/acl/v-user-role.action');" value="查询" class="opt-btn" />
					</td>
				</tr>
			</table>
		</form>
		<s:actionmessage />
		<form id="dataListForm" method="post">
			<input type="hidden"  name="filter_EQL_roleId" value="${param.filter_EQL_roleId}"/>		
			<table class="pn-ltable" width="100%" cellspacing="1" cellpadding="0" border="0">
				<thead class="pn-lthead">
					<tr>
						<th width="20"><input type='checkbox' onclick='Utils.toggleCheckState("ids")'/></th>
						<th>用户名</th>
						<th>姓名</th>
						<th>部门编号</th>
						<th>部门名称</th>	
						<th>角色名称</th>						
						<th>操作选项</th>							
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
					<s:iterator value="page.result">
						<tr align="center">
							<td><input type='checkbox' name='ids' value='${userId}' ${userId ne 1?'':'disabled=true' }/></td>
							<td>${username}</td>
							<td>${name}</td>
							<td>${deptCode}</td>
							<td>${deptName}</td>
							<td>${roleName}</td>
							<td>
								<c:if test="${userId ne 1&&session.hasPermission['Dict:Item:delete']  }">
								   <a onclick="Utils.doDelete('${ctx }/acl/v-user-role!delete.action?userId=${userId}&filter_EQL_roleId=${roleId}');" href="javascript:void(0)" class="pn-opt delete">移除角色</a> 								
								</c:if>														
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>			
			<div class="ps-btn-opt">
				<c:if test="${session.hasPermission['Dict:Item:delete'] }">
					<input type="button" class="opt-btn" value="批量移除角色"  onclick="Utils.batch('${ctx }/acl/v-user-role!batchDelete.action','移除','用户角色');"/> 
				</c:if>					
			</div>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscsslist.jsp" %>
