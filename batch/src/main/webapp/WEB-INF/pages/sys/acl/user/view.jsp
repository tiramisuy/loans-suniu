<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 用户管理 - 查看</div>
			<div class="clear"></div>
		</div>
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<tr>
					<td width="12%" class="pn-flabel">用户名</td>
					<td colspan="1" width="38%" class="pn-fcontent">${username }</td>
					<td width="12%" class="pn-flabel">姓名</td>
					<td colspan="1" width="38%" class="pn-fcontent">${name }</td>					
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">电子邮箱</td>
					<td colspan="1" width="38%" class="pn-fcontent">
						${email }
					</td>
					<td width="12%" class="pn-flabel">用户状态</td>
					<td colspan="3" width="88%" class="pn-fcontent">
						<s:property value="@com.rongdu.loans.service.dict.DictHelper@get(@com.rongdu.loans.service.dict.DictCode@USER_STATE)[status]"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">备注</td>
					<td colspan="3" width="88%" class="pn-fcontent">
						${remark }
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">角色</td>
					<td colspan="3" width="88%" class="pn-fcontent">
						<s:iterator value="roleList" status="rolestatus"> 
							${name }<s:if test="!#rolestatus.last == true">，</s:if>				
						</s:iterator>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="reset" value="返回" class="opt-btn" onclick="Utils.goback();" />
					</td>
				</tr>
			</table>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscssview.jsp" %>