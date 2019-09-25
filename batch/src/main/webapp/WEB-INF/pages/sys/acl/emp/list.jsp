<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 员工管理 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/acl/emp.action">
			<input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
			<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
			<input type="hidden" name="page.order" id="order" value="${page.order}"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">工号</td>
					<td  width="38%" class="pn-fcontent">
							<input type="text" maxlength="20" name="filter_LIKES_empNo" value="${param.filter_LIKES_empNo }"/>
					</td>	
					<td width="12%" class="pn-flabel">员工姓名</td>
					<td  width="38%" class="pn-fcontent">
							<input type="text" maxlength="20" name="filter_LIKES_empName" value="${param.filter_LIKES_empName }"/>
					</td>	
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">部门编号</td>
					<td  width="38%" class="pn-fcontent">
							<input type="text" maxlength="20" name="filter_LIKES_deptNo" value="${param.filter_LIKES_deptNo }"/>
					</td>	
					<td width="12%" class="pn-flabel">状态</td>
					<td  width="38%" class="pn-fcontent">
							<input type="text" maxlength="10" name="filter_LIKES_status" value="${param.filter_LIKES_status }"/>
					</td>	
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="submit" value="查询" class="opt-btn" />
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
						<th>工号</th>
						<th>员工姓名</th>
						<th>部门编号</th>
						<th>岗位</th>
						<th>入职日期</th>
						<th>状态</th>
						<th>操作选项</th>
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
					<s:iterator value="page.result">
						<tr align="center">
						<td><input type='checkbox' name='ids' value='${id}' /></td>
						<td>${empNo}</td>
						<td>${empName}</td>
						<td>${deptNo}</td>
						<td>${job}</td>
						<td>${hireDate}</td>
						<td>${status}</td>
							<td>
								<a href="${ctx }/acl/emp!view.action?id=${id}" class="pn-opt">查看</a> |
								<a href="${ctx }/acl/emp!input.action?id=${id}&page.pageNo=${page.pageNo}" class="pn-opt">修改</a> | 
								<a href="${ctx }/acl/emp!delete.action?id=${id}" class="pn-opt delete">删除</a>								
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>
			<div class="ps-btn-opt">
				<input type="button" class="opt-btn" value="新增"  onclick="Utils.forward('${ctx }/acl/emp!input.action?deptNo=${param.filter_LIKES_deptNo }');"/> 
				<input type="button" class="opt-btn" value="删除"  onclick="Utils.batch('${ctx }/acl/emp!batchDelete.action','删除','条','员工');"/> 
			</div>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscsslist.jsp" %>