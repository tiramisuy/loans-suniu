<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 用户管理 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/acl/user.action">
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
					<td width="12%" class="pn-flabel">所在机构</td>
					<td colspan="1" width="38%" class="pn-fcontent">
						<input type="text" maxlength="30"  name="filter_LIKES_deptName" value="${param.filter_LIKES_deptName }"/>
					</td>
					<td width="12%" class="pn-flabel">用户状态</td>
					<td colspan="1" width="38%" class="pn-fcontent">
						<s:select list="@com.rongdu.loans.service.dict.DictHelper@get(@com.rongdu.loans.service.dict.DictCode@USER_STATE)" listKey="key" listValue="value" name="filter_EQS_status" value="#parameters.filter_EQS_status"  headerKey="" headerValue="请选择" theme="simple"></s:select>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="button" onclick="Utils.doSearch('${ctx }/acl/user.action');" value="查询" class="opt-btn" />
					</td>
				</tr>
			</table>
		</form>
		<s:actionmessage />
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
							<td 
							<c:if test="${status eq 0}">class="red"</c:if>
							<c:if test="${status eq 1}">class="green"</c:if> >
							<s:property value="@com.rongdu.loans.service.dict.DictHelper@get(@com.rongdu.loans.service.dict.DictCode@USER_STATE)[status]"/> </td>
							<td>
								<c:if test="${session.hasPermission['User:view']  }">
								<input type="button"  onclick="Utils.forward('${ctx }/acl/user!view.action?id=${id}');" title="查看"  class="opt-view" /> </c:if>
								<c:if test="${session.hasPermission['User:update']  }">
								<input type="button"   onclick="Utils.forward('${ctx }/acl/user!input.action?id=${id}&page.pageNo=${page.pageNo}');" title="修改"  class="opt-edit"> </c:if>
								<c:if test="${session.hasPermission['User:delete']  }">
								<input type="button"  onclick="Utils.doDelete('${ctx }/acl/user!delete.action?id=${id}');"  title="删除" class="opt-delete"> </c:if>						
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>
			<div class="ps-btn-opt">
				<c:if test="${session.hasPermission['User:add']  }">
				<input type="button" class="opt-btn" value="新增"  onclick="Utils.forward('${ctx }/acl/user!input.action');"/> </c:if>
			</div>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscsslist.jsp" %>