<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 定时任务执行日志 - ${id eq null?"新增":"修改" }</div>
			<div class="ropt"><input type="button" value="返回列表" class="comm-btn" onclick="history.back();" /></div>
			<div class="clear"></div>
		</div>
		<form method="post" action="${ctx }/batch/schedule-task-log!save.action" id="form">
			<input type="hidden" name="id" value="${id }"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<s:token/>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>日志ID</td>
					<td  width="38%" class="pn-fcontent">
						<c:if test="${id ne null}"><input type="hidden" name="id" value="${id }"/>${id }</c:if>	
						<c:if test="${id eq null}"><input type="text" name="id" value="${id }" validate="{required:true,maxlength:50,remote:'${ctx }/batch/schedule-task-log!checkUnique.action?propertyName=id',messages:{remote:'此日志ID已被使用'}}"/></c:if>							
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>任务ID</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="taskId" value="${taskId }" validate="{required:true,maxlength:50}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>任务名称</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="taskName" value="${taskName }" validate="{required:true,maxlength:30}"/>
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>任务分组</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="groupName" value="${groupName }" validate="{required:true,maxlength:20}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">任务说明</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="description" value="${description }" validate="{maxlength:50}"/>
					</td>
					<td width="12%" class="pn-flabel">批次编号</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="batchNo" value="${batchNo }" validate="{maxlength:20}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>开始时间</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="startTime" value="${startTime }" validate="{required:true}"/>
					</td>
					<td width="12%" class="pn-flabel">结束时间</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="endTime" value="${endTime }" validate="{}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">耗时（秒）</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="costTime" value="${costTime }" validate="{digits:true}"/>
					</td>
					<td width="12%" class="pn-flabel">总计处理多少数据</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="totalNum" value="${totalNum }" validate="{digits:true}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">成功数量</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="succNum" value="${succNum }" validate="{digits:true}"/>
					</td>
					<td width="12%" class="pn-flabel">失败数量</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="failNum" value="${failNum }" validate="{digits:true}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">状态</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="status" value="${status }" validate="{maxlength:10}"/>
					</td>
					<td width="12%" class="pn-flabel">备注信息</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="remark" value="${remark }" validate="{maxlength:100}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>创建者</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="createBy" value="${createBy }" validate="{required:true,maxlength:50}"/>
					</td>
					<td width="12%" class="pn-flabel">创建时间</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="createTime" value="${createTime }" validate="{}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>最后修改者</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="updateBy" value="${updateBy }" validate="{required:true,maxlength:50}"/>
					</td>
					<td width="12%" class="pn-flabel">最后修改时间</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="updateTime" value="${updateTime }" validate="{}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>删除标记：0-正常，1-已经删除</td>
					<td colspan='3' width="38%" class="pn-fcontent">
						<input type="text" name="del" value="${del }" validate="{required:true}"/>
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