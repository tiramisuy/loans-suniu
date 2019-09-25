<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="error" value="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message }"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>聚宝钱包-任务调度平台</title>
<%@ include file="/page/jscssform.jsp"%>
<script type="text/javascript">
	if (top != this) {
		top.location = this.location;
	}
	$(function() {
		$("#j_username").focus();
		$("#loginForm").validate();
		$("#verifyCodeImg").next("a").click(function(){
			$('#verifyCodeImg').attr('src','${ctx}/verifyCode.jpg?' + Math.floor(Math.random()*100));
		});
	});	
</script>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	font-size: 12px;
	background: url(${ctx }/res/sys/img/login/bg.jpg) top repeat-x;
}
input[type=text],input[type=password]{width:150px;}
input[name=j_verify_code]{width:50px;}
#verifyCodeImg{vertical-align: bottom;}
</style>
</head>
<body>
	<form id="loginForm" action="${ctx}/j_spring_security_check" method="post">
		<table width="750" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td height="200">&nbsp;</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0"cellpadding="0">
						<tr>
							<td width="423" height="280" valign="top">
								<table	width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr><td><img src="${ctx }/res/sys/img/login/ltop.jpg" /></td></tr>
									<tr><td><!--  <img src="${ctx }/res/sys/img/login/llogo.jpg" />--></td></tr>
									</table>
								</td>
							<td width="40" align="center" valign="bottom">
								<img src="${ctx }/res/sys/img/login/line.jpg" width="23" height="232" />
							</td>
							<td valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td height="90" align="center" valign="bottom"><img src="${ctx }/res/sys/img/login/ltitle.jpg" /></td>
									</tr>
									<tr>
										<td>
											<div></div>
											<table width="100%" border="0" align="center" cellpadding="0" cellspacing="5">
												<tr>
													<td height="40" align="right"><strong>账号</strong></td>
													<td><input type="text" name="j_username" validate="{required:true}" style="width: 150px;" maxlength="30"/></td>
												</tr>
												<tr>
													<td height="40" align="right"><strong>密码</strong></td>
													<td><input type="password" name="j_password"  validate="{required:true}" style="width: 150px;" maxlength="30"/></td>
												</tr>	
												<tr>
													<td height="40" align="right"><strong>验证码</strong></td>
													<td style=""><input type='text' name='j_verify_code' validate="{required:true}" style="width: 50px;" maxlength="10" /> 
													<img id="verifyCodeImg" src="${ctx }/verifyCode.jpg"/> <a href="#"> 换一张</a></td>
												</tr>	
												<c:if test="${error ne null }">
													<tr>
														<td></td>
														<td class="red">${error }</td>
													</tr>
												</c:if>																			
												<tr>
													<td height="40" colspan="2" align="center">
														<input type="image" src="${ctx }/res/sys/img/login/login.jpg" name="submit" /> &nbsp; &nbsp; 
														<img name="reg" style="cursor: pointer" src="${ctx }/res/sys/img/login/reset.jpg" onclick="document.forms[0].reset()" /></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table></td>
			</tr>
		</table>
	</form>
</body>
</html>