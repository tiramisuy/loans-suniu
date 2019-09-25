<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 数据字典 - ${id == null ?'新增':'修改' }</div>
			<div class="clear"></div>
		</div>
		<form method="post" action="${ctx }/dict/dict!save.action" id="form">
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<input type="hidden" name="id" value="${code}"/>				
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>代码</td>
					<td colspan="1" width="38%" class="pn-fcontent">
						<c:if test="${code eq null}"><input type="text"  name="code" value="${code }" maxlength="20"  validate="{maxlength:20,required:true,loginname:true,remote:'${ctx }/dict/dict!checkDictCode.action',messages:{remote:'此代码已被使用'}}"/></c:if>
						<c:if test="${code ne null}"><input type="hidden" name="code" value="${code}"/>${code }</c:if>					
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>名称</td>
					<td colspan="1" width="38%" class="pn-fcontent">
						<input type="text"  name="name" value="${name }" validate="{required:true,maxlength:20}"/>
					</td>					
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">说明</td>
					<td colspan="3" width="88%" class="pn-fcontent">
						<textarea name="remark" validate="{maxlength:50}">${remark }</textarea>							
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="submit" value="提交" class="opt-btn"/> &nbsp; 
						<input type="reset" value="重置" class="opt-btn" />&nbsp; 
						<input type="button" value="返回" class="opt-btn" onclick="Utils.goback();" />
					</td>
				</tr>
			</table>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscssform.jsp" %>
