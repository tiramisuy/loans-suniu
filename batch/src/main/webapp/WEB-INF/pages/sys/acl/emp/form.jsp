<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 员工 - ${id eq null?"新增":"修改" }</div>
			<div class="ropt"><input type="button" value="返回列表" class="comm-btn" onclick="history.back();" /></div>
			<div class="clear"></div>
		</div>
		<form method="post" action="${ctx }/acl/emp!save.action" id="form">
			<input type="hidden" name="id" value="${id }"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<s:token/>
				<tr>
			<td width="12%" class="pn-flabel">部门编号</td>
					<td  width="38%" class="pn-fcontent">
						<c:if test="${deptNo ne null }">
							${deptNo }
						</c:if>
						<c:if test="${deptNo eq null }">
							<input type="text" name="deptNo" value="${deptNo }" validate="{maxlength:20}"/>
						</c:if>
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>岗位</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="job" value="${job }" validate="{required:true,maxlength:20}"/>
					</td>

				</tr>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>工号</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="empNo" value="${empNo }" validate="{required:true,maxlength:20}"/>
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>姓名</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="empName" value="${empName }" validate="{required:true,maxlength:20}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">入职日期</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="hireDate" value="${hireDate }" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					</td>
					<td width="12%" class="pn-flabel">状态</td>
					<td  width="38%" class="pn-fcontent">
							<s:select list="@com.rongdu.loans.service.dict.DictHelper@get(@com.rongdu.loans.service.dict.DictCode@USER_STATE)" listKey="key"
						listValue="value" name="status" value="status"  headerKey="" headerValue="请选择" theme="simple" ></s:select>		
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">备注</td>
					<td  width="38%" class="pn-fcontent" colspan="3">
						<textarea  name="remark"  validate="{maxlength:200}">${remark }</textarea>
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