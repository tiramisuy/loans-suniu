<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 数据字典 - 查看</div>
			<div class="clear"></div>
		</div>		
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">代码</td>
					<td colspan="1" width="38%" class="pn-fcontent">${code }</td>
					<td width="12%" class="pn-flabel">名称</td>
					<td colspan="1" width="38%" class="pn-fcontent">${name }</td>					
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">说明</td>
					<td colspan="3" width="88%" class="pn-fcontent">${remark }</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="reset" value="返回" class="opt-btn" onclick="Utils.goback();" />
					</td>
				</tr>
			</table>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscssview.jsp" %>