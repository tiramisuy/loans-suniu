<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 访问日志管理 - 查看</div>
			<div class="clear"></div>
		</div>
		<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
			<tr>
				<td width="12%" class="pn-flabel">ID</td>
				<td  width="38%" class="pn-fcontent">${id }</td>			
				<td width="12%" class="pn-flabel">登录日志ID</td>
				<td  width="38%" class="pn-fcontent">${loginLogId }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">用户ID</td>
				<td  width="38%" class="pn-fcontent">${userId }</td>			
				<td width="12%" class="pn-flabel">用户名</td>
				<td  width="38%" class="pn-fcontent">${username }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">姓名</td>
				<td  width="38%" class="pn-fcontent">${empName }</td>			
				<td width="12%" class="pn-flabel">请求方式</td>
				<td  width="38%" class="pn-fcontent">${method }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">访问地址</td>
				<td  width="38%" class="pn-fcontent">${requestUrl }</td>			
				<td width="12%" class="pn-flabel">模块名称</td>
				<td  width="38%" class="pn-fcontent">${moduleName }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">耗时（ms）</td>
				<td  width="38%" class="pn-fcontent">${costedTime }</td>			
				<td width="12%" class="pn-flabel">浏览器信息</td>
				<td  width="38%" class="pn-fcontent">${userAgent }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">IP地址</td>
				<td  width="38%" class="pn-fcontent">${ip }</td>			
				<td width="12%" class="pn-flabel">访问日期</td>
				<td  width="38%" class="pn-fcontent">${accessDate }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">访问时间</td>
				<td  width="38%" class="pn-fcontent">${accessTime }</td>	
				<td width="12%" class="pn-flabel">状态</td>
				<td colspan='3' width="38%" class="pn-fcontent">${status }</td>				
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">备注</td>
				<td  width="38%" class="pn-fcontent" colspan="3">${params }</td>			
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