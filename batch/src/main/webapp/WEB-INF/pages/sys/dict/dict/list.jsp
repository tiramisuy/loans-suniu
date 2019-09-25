<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 数据字典 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/dict/dict.action">
			<input type="hidden" name="page.pageNo" id="pageNo" value="1"/>
			<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
			<input type="hidden" name="page.order" id="order" value="${page.order}"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">代码</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							<input type="text" maxlength="100" name="filter_LIKES_code" value="${param.filter_LIKES_code }"/>
					</td>
					<td width="12%" class="pn-flabel">名称</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							<input type="text" maxlength="100" name="filter_LIKES_name" value="${param.filter_LIKES_name }"/>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="button" onclick="Utils.doSearch('${ctx }/dict/dict.action')" value="查询" class="opt-btn" />
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
						<th>代码</th>
						<th>名称</th>
						<th>说明</th>
						<th>操作选项</th>							
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
					<s:iterator value="page.result">
						<tr align="center">
							<td><input type='checkbox' name='ids' value='${code}' /></td>
							<td>${code}</td>
							<td>${name}</td>
							<td>${remark}</td>
							<td>
								<c:if test="${session.hasPermission['Dict:view'] }">
								<input type="button" onclick="Utils.forward('${ctx }/dict/dict!view.action?id=${code}')" title="查看"  class="opt-view"> </c:if>
								<c:if test="${session.hasPermission['Dict:update'] }">
								<input type="button" title="修改" onclick="Utils.forward('${ctx }/dict/dict!input.action?id=${code}&page.pageNo=${page.pageNo}');"   class="opt-edit">   </c:if> 
								<c:if test="${session.hasPermission['Dict:delete'] }">
								<input type="button" title="删除" onclick="Utils.doDelete('${ctx }/dict/dict!delete.action?id=${code}')"   class="opt-delete">  </c:if>
								<c:if test="${session.hasPermission['Dict:mainDictItem'] }">
								<a href="javascript:void(0)" onclick="Utils.doOpenWindow('${ctx }/dict/dict-item.action?dictCode=${code }','维护子项');" class="pn-opt">维护子项</a>	</c:if>							
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>			
			<div class="ps-btn-opt">
				<c:if test="${session.hasPermission['Dict:add'] }">
				<input type="button" class="opt-btn" value="新增"  onclick="Utils.forward('${ctx }/dict/dict!input.action');"/> </c:if>
			</div>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscsslist.jsp" %>
<script type="text/javascript">
	function mainDictItem(id) {
		var url =  "${ctx }/dict/dict-item.action?dictCode=" + id + "&rdm=" + Math.random();
		var result = window.showModalDialog(url, window, "dialogWidth:800px;dialogHeight:450px;resizable:no;scroll:no;status=0;");
		if(result){
			alert(result.msg);
			window.location.reload();			
		}
	}
</script>