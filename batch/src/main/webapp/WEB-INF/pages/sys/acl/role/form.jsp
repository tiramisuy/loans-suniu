<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<%@ include file="/page/jscssform.jsp" %>
 
</head>
<body>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 角色管理 - ${id eq null?'新增':'修改' }</div>
			<div class="clear"></div>
		</div>
		<form method="post" action="${ctx }/acl/role!save.action" id="form">
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<input type="hidden" name="id" value="${id}"/>
				<tr>
					<s:if test="id != null">
						<td width="12%" class="pn-flabel">角色ID</td>
						<td colspan="1" width="38%" class="pn-fcontent">${id }</td>
					</s:if>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>角色</td>
					<td colspan="${id ne null?1:3 }" width="38%" class="pn-fcontent">
						<input type="text"  name="name" value="${name }" maxlength="100"  validate="{required:true,maxlength:50}"/>
					</td>					
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">描述</td>
					<td colspan="3" width="88%" class="pn-fcontent">
						<textarea name="intro" maxlength="255">${intro }</textarea>							
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="submit" value="提交" class="opt-btn"/> &nbsp; 
						<input type="reset" value="重置" class="opt-btn" />&nbsp; 
						<input type="button" value="返回" class="opt-btn" onclick="Utils.goback();" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>