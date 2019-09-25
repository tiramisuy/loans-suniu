<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
<%@ include file="/page/jscssform.jsp" %>
<script src="${pageContext.request.contextPath}/res/modules/batch/ScheduleTask/form.js" type="text/javascript"></script>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 定时任务 - ${id eq null?"新增":"修改" }</div>
			<div class="ropt"><input type="button" value="返回列表" class="comm-btn" onclick="history.back();" /></div>
			<div class="clear"></div>
		</div>
		<form method="post" action="${ctx }/batch/schedule-task!save.action" id="form">
			<input type="hidden" name="id" value="${id }"/>
			<input type="hidden" name="springBean" value="true"/>	
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<s:token/>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>任务名称</td>
					<td  width="38%" class="pn-fcontent">
						<c:if test="${taskName ne null }">
							<input type="hidden" name="taskName" value="${taskName }"/>${taskName }
						</c:if>
						<c:if test="${taskName eq null }">
							<input type="text" name="taskName" value="${taskName }" validate="{required:true,maxlength:30,remote:'${ctx }/batch/schedule-task!checkUnique.action?propertyName=taskName',messages:{remote:'此任务名称已被使用'}}"/>
						</c:if>
						
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>任务说明</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="description" value="${description }" validate="{required:true,maxlength:50}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>任务分组</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="groupName" value="${groupName }" validate="{required:true,maxlength:20}"/>
					</td>				
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>是否支持并发</td>
					<td  width="38%" class="pn-fcontent">
					<select name="concurrent" id="concurrent">						
   						 <option value="false" <c:if test="${!concurrent}">selected="selected"</c:if>>不支持</option>
    					 <option value="true" <c:if test="${concurrent}">selected="selected"</c:if>>支持</option>
    				</select>
					</td>
				</tr>
				<tr>
					
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>目标对象</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="targetObject" value="${targetObject }" validate="{required:true,maxlength:30}"/>
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>执行方法</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="targetMethod" value="${targetMethod }" validate="{required:true,maxlength:30}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>失败处理策略</td>
					<td  width="38%" class="pn-fcontent">
					<select name="failStrategy" id="failStrategy">						
   						 <option value="ALARM" <c:if test="${failStrategy eq  'ALARM'}">selected="selected"</c:if>>警报</option>
    					 <option value="RETRY" <c:if test="${failStrategy eq  'RETRY'}">selected="selected"</c:if>>重试</option>
    				</select>
					</td>
										<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>失败后重试次数</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="retryTimes" id="retryTimes" value="0" validate="{required:true}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>Cron表达式</td>
					<td  width="38%" class="pn-fcontent" colspan="3">
						<input type="text" name="cronExpression" id="cronExpression" value="${cronExpression }" validate="{required:true,maxlength:50,remote:'${ctx }/batch/schedule-task!validateCronExpression.action?propertyName=cronExpression',messages:{remote:'请输入正确的cron表达式'}}" style="width: 18.5%;"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>Cron表达式</td>
					<td  width="38%" class="pn-fcontent" colspan="3">
						<jsp:include page="/res/widget/cron/cron.jsp"></jsp:include>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">备注信息</td>
					<td  width="38%" class="pn-fcontent" colspan="3">
						<textarea name="remark" validate="{maxlength:100}">${remark }</textarea>
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
<style type="text/css">
.tabs-header{background-color: #f9fbfd;}
</style>