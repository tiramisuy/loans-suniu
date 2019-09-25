<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 任务执行日志 - 列表</div>
			<div class="clear"></div>
		</div>
		<form id="searchForm" method="post" action="${ctx }/batch/schedule-task-log.action">
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
					<td width="12%" class="pn-flabel">执行结果</td>
					<td  width="38%" class="pn-fcontent">
					<select name="filter_EQS_status" id="filter_EQS_status">
						 <option value="" <c:if test="${param.filter_EQS_status eq ''}">selected="selected"</c:if>></option>			
   						 <option value="SUCCESS" <c:if test="${param.filter_EQS_status eq 'SUCCESS'}">selected="selected"</c:if>>成功</option>
   						 <option value="FAILURE" <c:if test="${param.filter_EQS_status eq 'FAILURE'}">selected="selected"</c:if>>失败</option>
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
						<th>批次编号</th>
						<th>开始时间</th>
						<th>耗时（ms）</th>
						<th>成功数量</th>
						<th>失败数量</th>
						<th>执行结果</th>
						<th>操作选项</th>
					</tr>
				</thead>				
				<tbody class="pn-ltbody">
					<s:iterator value="page.result">
						<tr align="center">
						<td>${taskName}</td>
						<td>${groupName}</td>
						<td>${description}</td>
						<td>${batchNo}</td>
						<td>${startTime}</td>
						<td>${costTime}</td>
						<td>${succNum}</td>
						<td>${failNum}</td>
						<td>
							<c:if test="${status eq 'SUCCESS'}"> <span class="green">成功</span></c:if>
							<c:if test="${status eq 'FAILURE'}"><span class="red">失败</span></c:if>
						</td>
							<td>
								<a href="${ctx }/batch/schedule-task-log!view.action?id=${id}" class="pn-opt">查看</a>&nbsp;
								
								<a href="javascript:Utils.doDelete('${ctx }/batch/schedule-task-log!delete.action?id=${id}');" class="pn-opt delete">删除</a>	&nbsp;							
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<jsp:include page="/page/page.jsp"/>
			<div class="ps-btn-opt">
			</div>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscsslist.jsp" %>