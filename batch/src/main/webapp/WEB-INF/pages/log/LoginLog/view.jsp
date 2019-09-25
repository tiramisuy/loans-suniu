<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div ><h5>${entity.empName }[${entity.username }]在${entity.loginTime }从${entity.ip }登录本系统，进行了如下操作：</h5></div>
			<div class="clear"></div>
		</div>
		<table class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
		<tr>
			<th>序号</th>
			<th>人员</th>
			<th>时间</th>
			<th>访问功能</th>
			<th>耗时(ms)</th>
		</tr>		
		<s:iterator value="list" status="stat">
			<tr>
				<td class="pn-fcontent">${stat.index+1}</td>
				<td class="pn-fcontent">${empName}</td>
				<td class="pn-fcontent">${accessTime}</td>
				<c:if test="${moduleName ne null}">
					<td class="pn-fcontent">${moduleName}</td>
				</c:if>
				<c:if test="${moduleName eq null}">
					<td class="pn-fcontent">${requestUrl}</td>
				</c:if>
				<td class="pn-fcontent">${costedTime}</td>
			</tr>			
		</s:iterator>
			</table>
				<div id="buttondiv" name="buttondiv" class="pn-fbutton"><input type=button value="打印" onclick="window.print();this.style.display='none';">
	</div>

</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscssview.jsp" %>
<style type="text/css">
.body-box{margin-left: -356px;position: absolute;left: 50%; width:712px; }
.rhead{border:#000 0;width: 700px;}
.rpos{color:#000; }
.pn-ftable{background-color: #000;width: 712px;}
.pn-ftable th{background-color: #fff;height: 20px;text-align: left;}
.pn-fbutton{margin-top: 10px; }
</style>