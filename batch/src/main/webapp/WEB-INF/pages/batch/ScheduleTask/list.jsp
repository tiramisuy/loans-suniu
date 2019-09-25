<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 定时任务 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/batch/schedule-task.action">
			<input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
			<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
			<input type="hidden" name="page.order" id="order" value="${page.order}"/>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">任务名称</td>
					<td  width="38%" class="pn-fcontent">
							<input type="text" maxlength="20" name="filter_LIKES_taskName" value="${param.filter_LIKES_taskName }"/>
					</td>	
					<td width="12%" class="pn-flabel">任务分组</td>
					<td  width="38%" class="pn-fcontent">
							<input type="text" maxlength="20" name="filter_LIKES_groupName" value="${param.filter_LIKES_groupName }"/>
					</td>	
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">任务说明</td>
					<td  width="38%" class="pn-fcontent">
							<input type="text" maxlength="50" name="filter_LIKES_description" value="${param.filter_LIKES_description }"/>
					</td>	
					<td width="12%" class="pn-flabel">状态</td>
					<td  width="38%" class="pn-fcontent">
					<select name="filter_EQS_status" id="filter_EQS_status">
						 <option value="" <c:if test="${param.filter_EQS_status eq ''}">selected="selected"</c:if>></option>			
   						 <option value="DEFAULT" <c:if test="${param.filter_EQS_status eq 'DEFAULT'}">selected="selected"</c:if>>初始</option>
   						 <option value="PAUSE" <c:if test="${param.filter_EQS_status eq 'PAUSE'}">selected="selected"</c:if>>暂停</option>
   						 <option value="ON" <c:if test="${param.filter_EQS_status eq 'ON'}">selected="selected"</c:if>>开启</option>
   						 <option value="OFF" <c:if test="${param.filter_EQS_status eq 'OFF'}">selected="selected"</c:if>>移除</option>
    				</select>
					</td>	
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="submit" value="查询" class="opt-btn" />
					</td>
				</tr>
			</table>
		</form>
		<s:actionmessage />
		<form id="dataListForm" method="post">
			<table class="pn-ltable" style="" width="100%" cellspacing="1" cellpadding="0" border="0">
				<thead class="pn-lthead">
					<tr>
						<th>任务名称</th>
						<th>任务分组</th>
						<th>任务说明</th>
						<!--  
						<th>是否支持并发</th>
						-->
						<th>上次执行时间</th>
						<th>下次执行时间</th>						
						<th>目标对象</th>
						<th>执行方法</th>
						<th>Cron表达式</th>
						<th>Trigger状态</th>
						<th>任务状态</th>
						<th style="max-width: 20%;">操作选项</th>
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
					<s:iterator value="page.result">
						<tr align="center">
						<td>${taskName}</td>
						<td>${groupName}</td>
						<td>${description}</td>
						<!--
						<td>
						<c:if test="${!concurrent}">不支持</c:if>
						<c:if test="${concurrent}">支持</c:if>
						</td>
						-->
						<td>${previousTime}</td>
						<td>${nextTime}</td>						
						<td>${targetObject}</td>
						<td>${targetMethod}</td>
						<td>${cronExpression}</td>
						<td>
							${triggerStatusMap[taskName] }
						</td>
						<td>
							<c:if test="${status eq 'DEFAULT'}">初始</c:if>
							<c:if test="${status eq 'PAUSE'}"><span class="red">暂停</span></c:if>
							<c:if test="${status eq 'ON'}"> <span class="green">开启</span></c:if>
							<c:if test="${status eq 'OFF'}"><span class="red">移除</span></c:if>
						</td>
							<td>
								<a href="${ctx }/batch/schedule-task!view.action?id=${id}" class="pn-opt">查看</a>&nbsp;
								<c:if test="${status eq 'DEFAULT' or status eq 'OFF'}">
									<a href="${ctx }/batch/schedule-task!input.action?id=${id}&page.pageNo=${page.pageNo}" class="pn-opt">修改</a>&nbsp;  								
								</c:if>
								<c:if test="${status eq 'DEFAULT'}">									
									<a href="javascript:Utils.confirm('${ctx }/batch/schedule-task!delete.action?id=${id}','您是否确认删除此任务?');" class="pn-opt delete">删除</a>&nbsp;
								</c:if>
								<c:if test="${status eq 'DEFAULT' or status eq 'OFF'}">						
									<a href="${ctx }/batch/schedule-task!addTask.action?id=${id}" class="pn-opt">加入计划</a>&nbsp;
								</c:if>
								<c:if test="${status eq 'ON'}">									
									<a href="javascript:Utils.confirm('${ctx }/batch/schedule-task!runTask.action?id=${id}','您是否确认立即执行此任务?');" class="pn-opt">立即执行</a>&nbsp;
								</c:if>
								<c:if test="${status eq 'ON'}">									
									<a href="javascript:Utils.confirm('${ctx }/batch/schedule-task!pauseTask.action?id=${id}','您是否确认暂停此任务?');" class="pn-opt">暂停</a>&nbsp;
								</c:if>
								<c:if test="${status eq 'PAUSE'}">									
									<a href="javascript:Utils.confirm('${ctx }/batch/schedule-task!deleteTask.action?id=${id}','您是否确认移除此任务?');" class="pn-opt">移除</a>	&nbsp;
								</c:if>
								<c:if test="${status eq 'PAUSE'}">									
									<a href="javascript:Utils.confirm('${ctx }/batch/schedule-task!resumeTask.action?id=${id}','您是否确认恢复此任务?');" class="pn-opt">恢复</a>	&nbsp;
								</c:if>	
								<a href="javascript:Utils.doOpenWindow('${ctx }/batch/schedule-task-log.action?filter_LIKES_taskName=${taskName}','执行日志');" class="pn-opt">执行日志</a>	&nbsp;
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>
			<div class="ps-btn-opt">
				<input type="button" class="opt-btn" value="新增"  onclick="Utils.forward('${ctx }/batch/schedule-task!input.action');"/> 
			</div>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscsslist.jsp" %>