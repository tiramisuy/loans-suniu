<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<%@ include file="/page/jscssview.jsp" %>
</head>
<body>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 角色管理 - 查看</div>
			<div class="clear"></div>
		</div>		
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">角色ID</td>
					<td colspan="1" width="38%" class="pn-fcontent">${id }</td>
					<td width="12%" class="pn-flabel">角色名称</td>
					<td colspan="1" width="38%" class="pn-fcontent">
							${name }
					</td>					
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">备注</td>
					<td colspan="3" width="88%" class="pn-fcontent">
							${intro }
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="reset" value="返回" class="opt-btn" onclick="Utils.goback();" />
					</td>
				</tr>
			</table>

	</div>
</body>
</html>