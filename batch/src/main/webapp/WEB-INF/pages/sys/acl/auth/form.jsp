<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/taglibs.jsp" %>
<%@ include file="/page/head.html" %>
	<div class="body-box">
		<div class="rhead">
			<div class="rpos">当前位置: 资源权限 - ${entity.id ==null ?"新增":"修改" }</div>
			<div class="clear"></div>
		</div>
		<form method="post" action="${ctx }/acl/authority!save.action" id="form">
			<input type="hidden" name="eid" value="${entity.id }">	
			<input type="hidden" name="id" value="${entity.id }"/>
			<input type="hidden" name="type" value="MENU">
			<table width="100%" class="pn-ftable" cellpadding="2" cellspacing="1" border="0">
				<s:token/>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>资源代码</td>
					<td  width="38%" class="pn-fcontent">
						<c:if test="${entity.id != null}"><input type="hidden" name="code" value="${entity.code }"/>${entity.code }</c:if>
						<c:if test="${entity.id == null}"><input type="text" name="code" value="${entity.code }" validate="{required:true,maxlength:20,remote:'${ctx }/acl/authority!checkUnique.action?propertyName=code',messages:{remote:'此资源代码已被使用'}}"/></c:if>							
					</td>
					<td width="12%" class="pn-flabel">上级资源</td>
					<td  width="38%" class="pn-fcontent">
						<input type="hidden" name="pid" value="${entity.pid }"/>${entity.pname }
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>资源名称</td>
					<td  width="38%" class="pn-fcontent">
						<input type="text" name="name" value="${entity.name }" validate="{required:true,maxlength:50}"/>
					</td>
					<td width="12%" class="pn-flabel">排列顺序</td>
					<td width="38%" class="pn-fcontent">
						<input type="text" name="rank" value="${entity.rank }" validate="{number:true}"/>
					</td>
				</tr>
				<tr>
					<td width="12%" class="pn-flabel"><span class="pn-frequired">*</span>资源URL</td>
					<td colspan="3" width="38%" class="pn-fcontent">
						<input type="text" name="url" value="${entity.url }" validate="{maxlength:200}"/>
					</td>					
				</tr>
			<tr>
				<td width="12%" class="pn-flabel">操作</td>
				<td  colspan='3' width="38%" class="pn-fcontent">
					<table cellpadding="2" cellspacing="1" border="0" style="width:76%;text-align: center;" class="quest">
							<tr>
								<td class="pn-sp">URL</td>
								<td class="pn-sp">代码</td>	
								<td class="pn-sp">名称</td>
								<td class="pn-sp" width="60">操作</td>	
							</tr>
					<c:if test="${list ne null }">
					<s:iterator value="list">
							<tr class="authority">
								<td style="width: 40%;"><input type="text" name="optUrl" value="${url }" class="text"/></td>	
								<td style="width: 25%;"><input type="text" name="optCode" value="${code }" class="text"/></td>	
								<td style="width: 25%;"><input type="text" name="optName" value="${name }" class="text"/></td>
								<td>
									<input type="hidden" name="optId" value="${id }"/>
									<button type="button" class="add" title="添加"></button>
        							<button type="button" class="delete" title="删除"></button>
        						</td>	
							</tr>
						</s:iterator>						
						</c:if>
						<c:if test="${list eq null }">
							<tr class="authority">
								<td><input type="text" name="optUrl" class="text" validate="{maxlength:50}"/></td>
								<td><input type="text" name="optCode" class="text" validate="{maxlength:40}"/></td>	
								<td><input type="text" name="optName" class="text" validate="{maxlength:20}"/></td>
								<td>
									<input type="hidden" name="optId"/>
									<button type="button" class="add" title="添加"></button>
        							<button type="button" class="delete" title="删除"></button>
        						</td>	
							</tr>
						</c:if>
					</table>
				</td>			
			</tr>			
				<tr>
					<td colspan="4" class="pn-fbutton">
						<input type="submit" value="提交" class="opt-btn"/>&nbsp; 
						<input type="reset" value="重置" class="opt-btn" />&nbsp; 
						<input type="button" value="返回" class="opt-btn" onclick="Utils.goback();" />
					</td>
				</tr>
			</table>
		</form>
	</div>
<%@ include file="/page/foot.html" %>
<%@ include file="/page/jscssform.jsp" %>
<style type="text/css">
<!--
button{cursor:pointer;height: 30px;margin: 0 2px;}
button.add{background:url(${ctx}/res/common/img/btn/add1.gif);width:16px;height:16px;padding:0;border:0}
button.delete{background:url(${ctx}/res/common/img/btn/del1.gif);width:16px;height:16px;padding:0;border:0}
input.text{width:100%;}
-->
</style>
<script type="text/javascript">
	$(function() {
		$("#form").validate();
	});
	$(".add").click(function(){
		var row = $(this).parents(".authority").get(0);
		var newRow = $(row).clone(true);
		$(newRow).find("input[type='text']").val('');
		$(newRow).find("input[type='hidden']").val('');
		$(newRow).addClass("authority");
		$(newRow).insertAfter(row);
	});
	$(".delete").click(function(){	
		var row = $(this).parents(".authority").get(0);
		var optId = $(row).find("input[name='optId']").val();
		if(optId&&confirm("一经删除，不可恢复!您是否确认删除条数据？")){
			var url = "${ctx}/acl/authority!ajaxDelete.action";
			var param = {"eid":optId};
			$.getJSON(url, param,function(data){	
				if(data.code=='sucess'){
					alert("已经成功删除");				
					remove(row);
				}else{
					alert("未能删除!");	
				}
			});				
		}else{
			remove(row);
		}
	});	
	
	function remove(row){
		if($(row).siblings(".authority").size()>0){
			$(row).remove();			
		}else{
			$(row).find("input[type='text']").val('');
			$(row).find("input[type='hidden']").val('');
		}		
	}
</script>
