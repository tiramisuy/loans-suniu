<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 定时任务 - 查看</div>
			<div class="clear"></div>
		</div>
		<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
			<tr>	
				<td width="12%" class="pn-flabel">任务名称</td>
				<td  width="38%" class="pn-fcontent">${taskName }</td>
				<td width="12%" class="pn-flabel">任务分组</td>
				<td  width="38%" class="pn-fcontent">${groupName }</td>	
			</tr>
			<tr>			
				<td width="12%" class="pn-flabel">任务说明</td>
				<td  width="38%" class="pn-fcontent">${description }</td>	
				<td width="12%" class="pn-flabel">是否支持并发</td>
				<td  width="38%" class="pn-fcontent">
					<c:if test="${concurrent}">支持</c:if>
					<c:if test="${!concurrent}">不支持</c:if>
				</td>				
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">最近执行时间</td>
				<td  width="38%" class="pn-fcontent">${previousTime }</td>			
				<td width="12%" class="pn-flabel">下次执行时间</td>
				<td  width="38%" class="pn-fcontent">${nextTime }</td>			
			</tr>
			<tr>	
				<td width="12%" class="pn-flabel">目标对象</td>
				<td  width="38%" class="pn-fcontent">${targetObject }</td>
				<td width="12%" class="pn-flabel">执行方法</td>
				<td  width="38%" class="pn-fcontent">${targetMethod }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">Cron表达式</td>
				<td  width="38%" class="pn-fcontent">${cronExpression }</td>					
				<td width="12%" class="pn-flabel">是否为SpringBean</td>
				<td  width="38%" class="pn-fcontent">
					<c:if test="${springBean}">是</c:if>
					<c:if test="${!springBean}">否</c:if>
				</td>			
			</tr>
			<tr>						
				<td width="12%" class="pn-flabel">失败处理策略</td>
				<td  width="38%" class="pn-fcontent">
					<c:if test="${failStrategy eq 'ALARM'}">警报</c:if>
					<c:if test="${failStrategy eq 'RETRY'}">重试</c:if>
				</td>
				<td width="12%" class="pn-flabel">失败后重试次数</td>
				<td  width="38%" class="pn-fcontent">${retryTimes }</td>						
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">开始时间</td>
				<td  width="38%" class="pn-fcontent">${startTime }</td>	
				<td width="12%" class="pn-flabel">任务状态</td>
				<td  width="38%" class="pn-fcontent">
					<c:if test="${status eq 'DEFAULT'}">初始</c:if>
					<c:if test="${status eq 'PAUSE'}"><span class="red">暂停</span></c:if>
					<c:if test="${status eq 'ON'}"> <span class="green">开启</span></c:if>
					<c:if test="${status eq 'OFF'}"><span class="red">移除</span></c:if>
				</td>			
			</tr>
			<tr>		
				<td width="12%" class="pn-flabel">创建者</td>
				<td  width="38%" class="pn-fcontent">${createBy }</td>	
				<td width="12%" class="pn-flabel">创建时间</td>
				<td  width="38%" class="pn-fcontent">${createTime }</td>				
			</tr>
			<tr>	
				<td width="12%" class="pn-flabel">最后修改者</td>
				<td  width="38%" class="pn-fcontent">${updateBy }</td>
				<td width="12%" class="pn-flabel">最后修改时间</td>
				<td  width="38%" class="pn-fcontent">${updateTime }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">备注信息</td>
				<td  width="38%" class="pn-fcontent" colspan="3">${remark }</td>			
			</tr>					
			<tr>
				<td colspan="4" class="pn-fbutton">
					<input type="button" value="返回" class="opt-btn" onclick="history.back();" />
				</td>
			</tr>
			</table>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscssview.jsp" %>