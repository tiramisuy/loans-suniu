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
			<div class="rpos">当前位置: 分配角色</div>
			<div class="clear"></div>			
		</div>
		<form method="post" action="${ctx }/acl/user!save.action" id="form">
			<input type="hidden" name="id" value="${user.id }"> 
			<s:token/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
			<tr>
				<td width="12%" class="pn-flabel">用户名</td>
				<td  width="38%" class="pn-fcontent">${user.username }</td>			
				<td width="12%" class="pn-flabel">姓名</td>
				<td  width="38%" class="pn-fcontent">${user.name }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">部门编号</td>
				<td  width="38%" class="pn-fcontent">${user.deptCode }</td>			
				<td width="12%" class="pn-flabel">部门名称</td>
				<td  width="38%" class="pn-fcontent">${user.deptName }</td>			
			</tr>
			<tr>
					<td width="12%" class="pn-flabel">电子邮箱</td>
					<td width="38%" class="pn-fcontent">${user.email }</td>
					<td width="12%" class="pn-flabel">用户状态</td>
					<td colspan="3" width="88%" class="pn-fcontent">
						<s:property value="@com.rongdu.loans.service.dict.DictHelper@get(@com.rongdu.loans.service.dict.DictCode@USER_STATE)[user.status]"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">备注</td>
					<td colspan="3" width="88%" class="pn-fcontent">
						${user.remark }
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">角色</td>
					<td colspan="3" width="88%" class="pn-fcontent">
						<s:checkboxlist name="checkedRoleIds" list="allRoleList" listKey="id" listValue="name" theme="custom"/>	
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="submit"  value="提交" class="opt-btn"/> &nbsp; 
						<input type="reset" value="重置" class="opt-btn" />&nbsp; 
						<input type="reset" value="返回" class="opt-btn" onclick="Utils.goback();" />
					</td>
				</tr>
			</table>
		</form>
	</div>
<%@ include file="/page/foot.html" %>