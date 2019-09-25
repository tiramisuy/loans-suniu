<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/page/jscssform.jsp" %>
</head>
<body>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 授权管理 - 维护</div>
			<div class="clear"></div>
		</div>
		<s:actionmessage />
		<form method="post" action="${ctx }/acl/assign-authority!save.action" id="form">
		<input type="hidden" name="roleId" value="${roleId }"/>
		<s:set name="first" value="@com.rongdu.loans.utils.CollectionUtil@values(#menu,1)"/>
		<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
		<s:iterator value="first"><s:set name="checked" value="@com.rongdu.loans.utils.CollectionUtil@contains(#ids,id)"/>
		<tr> 
			<td class="first"><input type="checkbox" name="id" value="${id }" id="${id }" class="${pid }" ${checked?'checked=true':'' }/>${name }</td>
			<td colspan="2" class="pn-fcontent"></td>
		</tr>
		<s:set name="second" value="@com.rongdu.loans.utils.CollectionUtil@values(#menu,id)"/>
		<s:iterator value="second"><s:set name="checked" value="@com.rongdu.loans.utils.CollectionUtil@contains(#ids,id)"/>
		<tr>
			<td class="pn-fcontent"></td>
			<td class="second"><input type="checkbox" name="id" value="${id }"  id="${id }" class="${pid }" ${checked?'checked=true':'' }/>${name }</td>
			<s:set name="third" value="@com.rongdu.loans.utils.CollectionUtil@values(#opt,id)"/>
			<td class="pn-fcontent"><s:iterator value="third"><s:set name="checked" value="@com.rongdu.loans.utils.CollectionUtil@contains(#ids,id)"/>
				<input type="checkbox" name="id" value="${id }" id="${id }" class="${pid }" ${checked?'checked=true':'' }/>${name }</s:iterator>
			</td>
		</tr></s:iterator>		
		</s:iterator>
		<tr>
			<td colspan="3" class="pn-fbutton">
				<input type="submit" value="提交" class="opt-btn"/> &nbsp; 
				<input type="reset" value="重置" class="opt-btn" />&nbsp; 
				<!--  <input type="button" value="返回" class="opt-btn" onclick="Utils.goback();" />-->
			</td>
		</tr>
	 </table>
		</form>
	</div>
</body>
</html>
<script type="text/javascript">
	$(function() {
		$("#form").validate();
	});
	$(":checkbox").bind("click",function(){
		checkParentCheckbox(this.className,this.checked);			
		checkChildCheckbox(this.id,this.checked);			
	});	
	function checkChildCheckbox(id,isChecked){	
		if(id){			
			$(":checkbox."+id).each(function(){
				this.checked = isChecked;
				checkChildCheckbox(this.id,isChecked);				
			});
		}
	}
	function checkParentCheckbox(className,isChecked){	
		if(className){
			var parent = $(":checkbox#"+className);
			if(isChecked&&parent){
				$(parent).attr("checked",isChecked);				
			}
			checkParentCheckbox($(parent).attr("class"),isChecked);
		}
	}
</script>
<style type="text/css">
.first{background-color:#F9FBFD;text-align:right;padding-right:3px;height: 17px;width:10%}
.second{background-color:#F9FBFD;text-align:left;padding-right:3px;height: 17px;width:12%}
</style>