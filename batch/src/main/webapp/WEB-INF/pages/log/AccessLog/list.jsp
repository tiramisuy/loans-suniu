<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 访问日志管理 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/log/access-log.action">
			<input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
			<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
			<input type="hidden" name="page.order" id="order" value="${page.order}"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">用户名</td>
					<td  width="38%" class="pn-fcontent">
							<input type="text" maxlength="20" name="filter_LIKES_username" value="${param.filter_LIKES_username }"/>
					</td>	
					<td width="12%" class="pn-flabel">姓名</td>
					<td  width="38%" class="pn-fcontent">
							<input type="text" maxlength="20" name="filter_LIKES_empName" value="${param.filter_LIKES_empName }"/>
					</td>	
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">模块名称</td>
					<td  width="38%" class="pn-fcontent">
							<input type="text" maxlength="20" name="filter_LIKES_moduleName" value="${param.filter_LIKES_moduleName }"/>
					</td>	
					<td width="12%" class="pn-flabel">访问日期</td>
					<td  width="38%" class="pn-fcontent">
							<input type="text" maxlength="255" name="filter_LIKES_accessDate" value="${param.filter_LIKES_accessDate }" class="Wdate" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
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
						<th>ID</th>
						<th>用户名</th>
						<th>姓名</th>
						<th>访问地址</th>
						<th>模块名称</th>
						<th>耗时（ms）</th>
						<th>IP地址</th>
						<th>访问时间</th>
						<th>操作选项</th>
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
					<s:iterator value="page.result">
						<tr align="center">
						<td>${id}</td>
						<td>${username}</td>
						<td>${empName}</td>
						<td>${requestUrl}</td>
						<td>${moduleName}</td>
						<td>${costedTime}</td>
						<td>${ip}</td>
						<td>${accessTime}</td>
							<td>
								<c:if test="${session.hasPermission['AccessLog:view'] }">
								<input type="button" onclick="Utils.forward('${ctx }/log/access-log!view.action?id=${id}')" title="查看"  class="opt-view"> </c:if> 
								<c:if test="${session.hasPermission['AccessLog:delete'] }">
								<input type="button" onclick="Utils.doDelete('${ctx }/log/access-log!delete.action?id=${id}')" title="删除"    class="opt-delete">  </c:if>				
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>
			<div class="ps-btn-opt">
		
			</div>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscsslist.jsp" %>
<!--日期选择器 -->
<script src="${ctx}/res/widget/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
