<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 字典明细 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/dict/dict-item.action">
			<input type="hidden" name="page.pageNo" id="pageNo" value="1"/>
			<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
			<input type="hidden" name="page.order" id="order" value="${page.order}"/>
			<input type="hidden" name="dictCode" id="dictCode" value="${session.dictCode}"/>					
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">代码</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							<input type="text" maxlength="100" name="code" value=""/>
					</td>
					<td width="12%" class="pn-flabel">名称</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							<input type="text" maxlength="100" name="name" value=""/>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="button" onclick="Utils.forward('${ctx }/dict/dict-item.action');" value="查询" class="opt-btn" />
					</td>
				</tr>
			</table>
		</form>
		<s:actionmessage />
		<form id="dataListForm" method="post">
			<table class="pn-ltable" width="100%" cellspacing="1" cellpadding="0" border="0">
				<thead class="pn-lthead">
					<tr>
						<th width="20"><input type='checkbox' onclick='Utils.toggleCheckState("ids")'/></th>
						<th>代码</th>
						<th>名称</th>
						<th>描述</th>
						<th>操作选项</th>							
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
					<s:iterator value="page.result">
						<tr align="center">
							<td><input type='checkbox' name='ids' value='${id}' /></td>
							<td>${code}</td>
							<td>${name}</td>
							<td>${remark}</td>
							<td>
								<c:if test="${session.hasPermission['Dict:Item:view'] }">
								<a onclick="Utils.forward('${ctx }/dict/dict-item!view.action?id=${id}')" href="javascript:void(0)" class="pn-opt">查看</a> </c:if>
								<c:if test="${session.hasPermission['Dict:Item:update'] }">
								<a onclick="Utils.forward('${ctx }/dict/dict-item!input.action?id=${id}&page.pageNo=${page.pageNo}');"  href="javascript:void(0)" class="pn-opt">修改</a> </c:if>
								<c:if test="${session.hasPermission['Dict:Item:delete'] }">
								<a onclick="Utils.doDelete('${ctx }/dict/dict-item!delete.action?id=${id}')"  href="javascript:void(0)" class="pn-opt delete">删除</a> 	</c:if>							
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>			
			<div class="ps-btn-opt">
				<c:if test="${session.hasPermission['Dict:Item:add'] }">
				<input type="button" class="opt-btn" onclick="Utils.forward('${ctx }/dict/dict-item!input.action')"  value="新增"   /> </c:if>			 
			</div>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscsslist.jsp" %>