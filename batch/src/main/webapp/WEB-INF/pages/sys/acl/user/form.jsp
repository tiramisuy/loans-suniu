<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 用户管理 - ${id eq null?"新增":"修改" }</div>
			<div class="ropt"></div>
			<div class="clear"></div>			
		</div>
		<form method="post" action="${ctx }/acl/user!save.action" id="form">
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<s:token/>
				<input type="hidden" name="id" value="${id}"/>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>用户名</td>
					<td colspan="1" width="38%" class="pn-fcontent">
					<c:if test="${id eq null}">
						<input type="text" name="username"  maxlength="100"  value=""
						validate="{required:true,username:true,remote:'${ctx }/acl/user!checkUsername.action?oldUsername=${username}',messages:{remote:'此用户名已被使用'}}" />
					</c:if>
					<c:if test="${id ne null}">
						<input type="hidden" name="username" value="${username }">${username }
					</c:if>
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>姓名</td>
					<td colspan="1" width="38%" class="pn-fcontent">
						<input type="text"  name="name" value="${name }"  validate="{required:true,maxlength:20}"/>
					</td>					
				</tr>
				<c:if test="${id eq null}">
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>密码</td>
					<td colspan="1" width="38%" class="pn-fcontent">
						<input type="password" autocomplete="off" id="password" name="password" value="${password }" class="required" maxlength="100" />
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>确认密码</td>
					<td colspan="1" width="38%" class="pn-fcontent">
						<input type="password" value="${password }" autocomplete="off" equalTo="#password" validate="{required:true}" />
					</td>
				</tr>
				</c:if>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>电子邮箱</td>
					<td colspan="1" width="38%" class="pn-fcontent">
						<input type="text" name="email"  value="${email }"  validate="{required:true,email:true,maxlength:40}"/>
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>用户状态</td>
					<td colspan="3" width="88%" class="pn-fcontent">
						<s:select list="@com.rongdu.loans.service.dict.DictHelper@get(@com.rongdu.loans.service.dict.DictCode@USER_STATE)" listKey="key"
						listValue="value" name="status" value="status"  headerKey="" headerValue="请选择" theme="simple" ></s:select>					
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">备注</td>
					<td colspan="3" width="88%" class="pn-fcontent">
						<textarea name="remark" validate="{maxlength:100}">${remark }</textarea>	
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
						<input type="submit" value="提交" class="opt-btn"/> &nbsp; 
						<input type="reset" value="重置" class="opt-btn" />&nbsp; 
						<input type="reset" value="返回" class="opt-btn" onclick="Utils.goback();" />
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