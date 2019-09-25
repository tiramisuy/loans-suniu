<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 部门 - ${id eq null?"新增":"修改" }</div>
			<div class="ropt"><input type="button" value="返回列表" class="comm-btn" onclick="history.back();" /></div>
			<div class="clear"></div>
		</div>
		<form method="post" action="${ctx }/acl/dept!save.action" id="form">
			<input type="hidden" name="id" value="${id }"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<s:token/>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>ID</td>
					<td  width="38%" class="pn-fcontent">
						<c:if test="${id ne null}"><input type="hidden" name="id" value="${id }"/>${id }</c:if>	
						<c:if test="${id eq null}"><input type="text" name="id" value="${id }" validate="{required:true,number:true,remote:'${ctx }/acl/dept!checkUnique.action?propertyName=id',messages:{remote:'此ID已被使用'}}"/></c:if>							
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>部门编号</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="deptNo" value="${deptNo }" validate="{required:true,maxlength:20}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>部门名称</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="deptName" value="${deptName }" validate="{required:true,maxlength:20}"/>
					</td>
					<td width="12%" class="pn-flabel">上级部门ID</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="parentId" value="${parentId }" validate="{number:true}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">状态</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="status" value="${status }" validate="{maxlength:10}"/>
					</td>
					<td width="12%" class="pn-flabel">备注</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="remark" value="${remark }" validate="{maxlength:200}"/>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="submit" value="提交" class="opt-btn"/>&nbsp; 
						<input type="reset" value="重置" class="opt-btn" />&nbsp; 
						<input type="button" value="返回" class="opt-btn" onclick="history.back();" />
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