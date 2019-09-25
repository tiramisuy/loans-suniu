<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 字典明细 - ${id eq null?'新增':'修改' }</div>
			<div class="clear"></div>
		</div>
		<form method="post" action="${ctx }/dict/dict-item!save.action" id="form">
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<input type="hidden" name="id" value="${id}"/>
				<input type="hidden" name="dictCode" value="${session.dictCode}"/>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>代码</td>
					<td colspan="1" width="38%" class="pn-fcontent">
					<c:if test="${id eq null }"><input type="text"  name="code" value="${code }" maxlength="20"  validate="{required:true,loginname:true,remote:'${ctx }/dict/dict-item!checkDictItemCode.action',messages:{remote:'此代码已被使用'}}" /></c:if>
					<c:if test="${id ne null }"><input type="hidden"  name="code" value="${code }" maxlength="100"/>${code }</c:if>	
					</td>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>名称</td>
					<td colspan="1" width="38%" class="pn-fcontent">
						<input type="text"  name="name" value="${name }"  validate="{required:true,maxlength:25}" maxlength="25" />
					</td>					
				</tr>
				<tr>
					<td width="12%" class="pn-flabel">备注</td>
					<td colspan="3" width="88%" class="pn-fcontent">
						<textarea name="remark" maxlength="50">${remark }</textarea>							
					</td>
				</tr>
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="submit"  value="提交" class="opt-btn"/> &nbsp; 
						<input type="reset" value="重置" class="opt-btn" />&nbsp; 
						<input type="button" value="返回" class="opt-btn" onclick="window.close();" />
					</td>
				</tr>
			</table>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscssform.jsp" %>