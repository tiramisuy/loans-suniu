<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<%@ include file="/page/jscsslist.jsp" %>
</head>
<body>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 角色管理 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/acl/role.action">
			<input type="hidden" name="page.pageNo" id="pageNo" value="1"/>
			<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
			<input type="hidden" name="page.order" id="order" value="${page.order}"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">角色</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							<input type="text" maxlength="100" name="filter_LIKES_name" value="${param.filter_LIKES_name }"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="pn-fbutton">
						<input type="button" onclick="Utils.doSearch('${ctx }/acl/role.action')" value="查询" class="opt-btn" />
					</td>
				</tr>
			</table>
		</form>
		<s:actionmessage />
		<form id="dataListForm" method="post">
			<table class="pn-ltable" width="100%" cellspacing="1" cellpadding="0" border="0">
				<thead class="pn-lthead">
					<tr>
						<th width="20"><input type='checkbox' onclick='Utils.toggleCheckState("ids")' /></th>
						<th>ID</th>
						<th>角色</th>
						<th>用户数量</th>
						<th>描述</th>
						<th>操作选项</th>							
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
					<s:iterator value="page.result">
						<tr align="center">
							<td><input type='checkbox' name='ids' value='${id}' /></td>
							<td>${id}</td>
							<td>${name}</td>
							<td>${userNum}</td>
							<td>${intro}</td>
							<td>
								<c:if test="${session.hasPermission['Role:view']}">
								<input type="button" onclick="Utils.forward('${ctx }/acl/role!view.action?id=${id}');"  class="opt-view" title="查看" />  </c:if>
								<c:if test="${session.hasPermission['Role:update']  }">
								<input type="button" onclick="Utils.forward('${ctx }/acl/role!input.action?id=${id}&page.pageNo=${page.pageNo}');" title="修改"  class="opt-edit" />  </c:if>
								<c:if test="${session.hasPermission['Role:delete'] }">
								<c:if test="${userNum eq null}">
								  <input type="button" onclick="Utils.doDelete('${ctx }/acl/role!delete.action?id=${id}');"   class="opt-delete"  title="删除"/></c:if>								
							     </c:if>
							    <c:if test="${userNum != null}">
							    	<input type="button" onclick="Utils.doOpenWindow('${ctx }/acl/v-user-role.action?filter_EQL_roleId=${id }','查看用户');" title="查看用户"  class="opt-search" />  
							    </c:if>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>			
			<div class="ps-btn-opt">
				<c:if test="${session.hasPermission['Role:add']  }">
				<input type="button" class="opt-btn" value="新增"  onclick="Utils.forward('${ctx }/acl/role!input.action');"/></c:if> 
			</div>
		</form>
	</div>
</body>
</html>
<script type="text/javascript">
	function queryUser(roleId) {
		var url =  "${ctx }/acl/v-user-role.action?filter_EQL_roleId=" + roleId+"&r="+Math.random();
		var result = window.showModalDialog(url, window, "dialogWidth:800px;dialogHeight:500px;resizable:no;scroll:no;status=0;");
		window.location.reload();			
	}
</script>