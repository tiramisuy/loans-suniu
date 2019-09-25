<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="/page/taglibs.jsp"%>
<%@ include file="/page/head.html"%>
<%@ include file="/page/jscssform.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#form").validate();
	});
</script>
<div class="body-box">
	<div class="rhead">
		<div class="rpos">当前位置: 修改密码</div>
		<form class="ropt"></form>
		<div class="clear"></div>
	</div>
	<s:actionmessage />
	<form method="post" action="${ctx }/per/personal!savePswd.action" id="form">
		<input type="hidden" name="id" value="<sec:authentication property="principal.id"/>"/>
		<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
			<tr>
				<td width="20%" class="pn-flabel pn-flabel-h">登录名:</td>
				<td width="80%" class="pn-fcontent"><sec:authentication property="principal.username"/></td>
			</tr>
			<tr>
				<td width="20%" class="pn-flabel pn-flabel-h">用户名:</td>
				<td width="80%" class="pn-fcontent"><sec:authentication property="principal.name"/></td>
			</tr>
			<tr>
				<td width="20%" class="pn-flabel pn-flabel-h"><span class="pn-frequired">*</span>原密码:</td>
				<td width="80%" class="pn-fcontent">
					<input type="password" autocomplete="off" maxlength="32" name="origPwd" validate="{required:true,remote:'${ctx }/per/personal!checkPswd.action?id=<sec:authentication property="principal.id"/>',messages:{remote:'与原密码不符'}}"/>
				</td>
			</tr>
			<tr>
				<td width="20%" class="pn-flabel pn-flabel-h">新密码:</td>
				<td width="80%" class="pn-fcontent">
					<input type="password" autocomplete="off" id="newPwd" maxlength="32" name="password"  validate="{required:true}"/>
				</td>
			</tr>
			<tr>
				<td width="20%" class="pn-flabel pn-flabel-h">确认密码:</td>
				<td width="80%" class="pn-fcontent">
					<input type="password" autocomplete="off" maxlength="32" equalTo="#newPwd"  validate="{required:true}"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="pn-fbutton">
					<input type="submit" class="opt-btn" value="提交" /> &nbsp; 
					<input type="reset" class="opt-btn" value="重置" />
				</td>
			</tr>
		</table>
	</form>
</div>
<%@ include file="/page/foot.html"%>