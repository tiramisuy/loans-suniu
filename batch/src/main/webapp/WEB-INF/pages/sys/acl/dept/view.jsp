<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 部门管理 - 查看</div>
			<div class="clear"></div>
		</div>
		<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
			<tr>
				<td width="12%" class="pn-flabel">ID</td>
				<td  width="38%" class="pn-fcontent">${id }</td>			
				<td width="12%" class="pn-flabel">部门编号</td>
				<td  width="38%" class="pn-fcontent">${deptNo }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">部门名称</td>
				<td  width="38%" class="pn-fcontent">${deptName }</td>			
				<td width="12%" class="pn-flabel">上级部门ID</td>
				<td  width="38%" class="pn-fcontent">${parentId }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">状态</td>
				<td  width="38%" class="pn-fcontent">${status }</td>			
				<td width="12%" class="pn-flabel">备注</td>
				<td  width="38%" class="pn-fcontent">${remark }</td>			
			</tr>					
			<tr>
				<td colspan="4" class="pn-fbutton">
					<input type="button" value="返回" class="opt-btn" onclick="history.back();" />
				</td>
			</tr>
			</table>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscssview.jsp" %>