<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 任务执行日志 - 查看</div>
			<div class="clear"></div>
		</div>
		<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
			<!--  
			<tr>
				<td width="12%" class="pn-flabel">日志ID</td>
				<td  width="38%" class="pn-fcontent">${id }</td>			
				<td width="12%" class="pn-flabel">任务ID</td>
				<td  width="38%" class="pn-fcontent">${taskId }</td>			
			</tr>
			-->
			<tr>
				<td width="12%" class="pn-flabel">任务名称</td>
				<td  width="38%" class="pn-fcontent">${taskName }</td>			
				<td width="12%" class="pn-flabel">任务分组</td>
				<td  width="38%" class="pn-fcontent">${groupName }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">任务说明</td>
				<td  width="38%" class="pn-fcontent">${description }</td>			
				<td width="12%" class="pn-flabel">批次编号</td>
				<td  width="38%" class="pn-fcontent">${batchNo }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">开始时间</td>
				<td  width="38%" class="pn-fcontent">${startTime }</td>			
				<td width="12%" class="pn-flabel">结束时间</td>
				<td  width="38%" class="pn-fcontent">${endTime }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">耗时（秒）</td>
				<td  width="38%" class="pn-fcontent">${costTime }</td>			
				<td width="12%" class="pn-flabel">总计处理多少数据</td>
				<td  width="38%" class="pn-fcontent">${totalNum }</td>			
			</tr>
			<tr>
				<td width="12%" class="pn-flabel">成功数量</td>
				<td  width="38%" class="pn-fcontent">${succNum }</td>			
				<td width="12%" class="pn-flabel">失败数量</td>
				<td  width="38%" class="pn-fcontent">${failNum }</td>			
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
				<td width="12%" class="pn-flabel">执行结果</td>
				<td  width="38%" class="pn-fcontent" colspan="3">
						<c:if test="${status eq 'SUCCESS'}"> <span class="green">成功</span></c:if>
						<c:if test="${status eq 'FAILURE'}"><span class="red">失败</span></c:if>
				</td>
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