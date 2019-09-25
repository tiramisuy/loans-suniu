<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 后台登录日志 - ${id eq null?"新增":"修改" }</div>
			<div class="ropt"><input type="button" value="返回列表" class="ps-btn" onclick="history.back();" /></div>
			<div class="clear"></div>
		</div>
		<form method="post" action="${ctx }/log/login-log!save.action" id="form">
			<input type="hidden" name="id" value="${id }"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<s:token/>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>ID</td>
					<td  width="38%" class="pn-fcontent">
						<c:if test="${id ne null}"><input type="hidden" name="id" value="${id }"/>${id }</c:if>	
						<c:if test="${id eq null}"><input type="text" name="id" value="${id }" validate="{required:true,number:true,remote:'${ctx }/log/login-log!checkUnique.action?propertyName=id',messages:{remote:'此ID已被使用'}}"/></c:if>							
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>用户</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="username" value="${username }" validate="{required:true,maxlength:20}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>操作</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="opt" value="${opt }" validate="{required:true,maxlength:40}"/>
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>来源</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="source" value="${source }" validate="{required:true,maxlength:40}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>登录时间</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="loginTime" value="${loginTime }" validate="{required:true,maxlength:20}"/>
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>IP地址</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="ip" value="${ip }" validate="{required:true,maxlength:20}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>状态</td>
					<td colspan='3' width="38%" class="pn-fcontent">
						<input type="text" name="status" value="${status }" validate="{required:true,maxlength:10}"/>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="submit" value="提交" class="ps-btn"/>&nbsp; 
						<input type="reset" value="重置" class="ps-btn" />&nbsp; 
						<input type="button" value="返回" class="ps-btn" onclick="history.back();" />
					</td>
				</tr>
			</table>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscssform.jsp" %>
<script type="text/javascript">
	$(function() {
		$("#form").validate();
	});
</script>