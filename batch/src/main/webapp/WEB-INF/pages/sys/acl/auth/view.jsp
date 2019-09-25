<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
<c:if test="${isReload }">
<script type="text/javascript">window.parent.frames['treeFrameset'].location.reload();</script>
</c:if>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">资源权限 - 查看</div>
			<div class="clear"></div>
		</div>
		<s:actionmessage />
		<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
			<tr>
				<td width="12%" class="pn-flabel">资源代码</td>
				<td  width="38%" class="pn-fcontent">${entity.code }</td>			
				<td width="12%" class="pn-flabel">上级资源</td>
				<td width="38%" class="pn-fcontent">${entity.pname }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">资源名称</td>
				<td  width="38%" class="pn-fcontent">${entity.name }</td>			
				<td width="12%" class="pn-flabel">排列顺序</td>
				<td width="38%" class="pn-fcontent">${entity.rank }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">资源URL</td>
				<td colspan="3" width="38%" class="pn-fcontent">${entity.url }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">操作</td>
				<td  colspan='3' width="38%" class="pn-fcontent">
				<c:if test="${list ne null }">
					<table cellpadding="2" cellspacing="1" border="0" style="width:76%;text-align: center;background-color:#E2EBEF;">
						<tr>
							<td class="pn-sp">URL</td>
							<td class="pn-sp">代码</td>	
							<td class="pn-sp">名称</td>	
						</tr>
						<tbody class="pn-ltbody">
					<s:iterator value="list">
						<tr>
							<td>${url }</td>
							<td>${code }</td>	
							<td>${name }</td>	
						</tr>
					</s:iterator>
						</tbody>
						</table>
					</c:if>
				</td>			
			</tr>								
			<tr>
				<td colspan="4" class="pn-fbutton">
				    <c:if test="${session.hasPermission['Resource:add']  }">
					<input type="button" value="新增" class="opt-btn" onclick="Utils.forward('${ctx }/acl/authority!form.action?pid=${entity.id}');"/>	</c:if>	
					<c:if test="${session.hasPermission['Resource:update']  }">
					<input type="button" value="修改" class="opt-btn" onclick="Utils.forward('${ctx }/acl/authority!form.action?eid=${entity.id}');"/></c:if>
					<c:if test="${session.hasPermission['Resource:delete'] }">
					<input type="button" value="删除" class="opt-btn" onclick="Utils.doDelete('${ctx }/acl/authority!delete.action?eid=${entity.id}');" ${entity.num ne 0?'disabled=true':'' }/></c:if>
				</td>
			</tr>
			</table>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscsslist.jsp" %>