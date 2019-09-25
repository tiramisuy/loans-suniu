<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 员工管理 - 查看</div>
			<div class="clear"></div>
		</div>
		<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
			<tr>
				<td width="12%" class="pn-flabel">ID</td>
				<td  width="38%" class="pn-fcontent">${id }</td>			
				<td width="12%" class="pn-flabel">工号</td>
				<td  width="38%" class="pn-fcontent">${empNo }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">员工姓名</td>
				<td  width="38%" class="pn-fcontent">${empName }</td>			
				<td width="12%" class="pn-flabel">部门编号</td>
				<td  width="38%" class="pn-fcontent">${deptNo }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">岗位</td>
				<td  width="38%" class="pn-fcontent">${job }</td>			
				<td width="12%" class="pn-flabel">入职日期</td>
				<td  width="38%" class="pn-fcontent">${hireDate }</td>			
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