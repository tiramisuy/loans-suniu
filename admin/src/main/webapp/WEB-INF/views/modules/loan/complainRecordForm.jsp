<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>投诉工单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
	function setHandleUserId(type,userId){
		if(type){
			 
			  $.post("${ctx}/loan/complainRecord/getUserByDept",{type:type},function(result){
				  var html = '<option value="">请选择</option>';
				  
				  if(result){
					  for(var i=0;i<result.length;i++){
						  html += '<option value="'+result[i].id+'" >'+result[i].name+'</option>';
					  }
				  }
				  						  
				  $("#handleUserId").html(html);
				  
				  if(userId){
					 
					  $("#handleUserId").val(userId);
					  var userName = $("#handleUserId").find("option[value='"+userId+"']").html();
					  $("#handleUserId_hid").parent().find("div a .select2-chosen").html(userName);
				  }else{
					  $("#handleUserId_hid").parent().find("div a .select2-chosen").html("请选择");
				  }
				  
				  });
			  
			 
		  }else{
			  $("#handleUserId").html('<option value="">请选择</option>');  
			  $("#handleUserId_hid").parent().find("div a .select2-chosen").html("请选择");
		  }
	}
	
	function setComplainType(complainType,userId){
		if(complainType){
			 var html = '<option value="">请选择</option>';
			  if(complainType == "3110"){
					html += '<option value="恶语质问客户" >恶语质问客户</option>'
					+ '<option value="辱骂客户" >辱骂客户</option>'
					+ '<option value="威胁恐吓" >威胁恐吓</option>';
			  } else if (complainType == "3120") {
				  html += '<option value="未按时回电" >未按时回电</option>'
				  + '<option value="随意承诺客户" >随意承诺客户</option>';
			  } else if(complainType == "3130"){
				  html += '<option value="代扣错误" >代扣错误</option>'
				  + '<option value="延期未操作" >延期未操作</option>';
			  }else if (complainType == "3140") {
				  html += '<option value="费用高" >费用高</option>'
				  + '<option value="代扣问题" >代扣问题</option>'
				  + '<option value="购物券" >购物券</option>'
				  + '<option value="还款周期" >还款周期</option>';
		      } else if (complainType == "3150") {
		    	  html += '<option value="还款信息提供错误" >还款信息提供错误</option>'
		    	 	 + '<option value="未按规则详细解释" >未按规则详细解释</option>';
			  } else if (complainType == "3160") {
				  html += '<option value="骚扰/欺诈/私自联系三方" >骚扰/欺诈/私自联系三方</option>'
						  + '<option value="爆通讯录" >爆通讯录</option>'
						  + '<option value="P图" >P图</option>'
						  + '<option value="暴力催收" >暴力催收</option>'
						  + '<option value="虚假销售" >虚假销售</option>';
			  } else if (complainType == "3170") {
				  html += '<option value="其他">其他</option>';
			  }
			  $("#complainType_point").parent().find("div a .select2-chosen").html("请选择");
			  $("#complainPoint").html(html);
			  
			  if(userId){
				  $("#complainPoint").html('<option value="'+userId+'">'+userId+'</option>'); 
				  $("#complainType_point").parent().find("div a .select2-chosen").html(userId);
			  }else{
				  $("#complainType_point").parent().find("div a .select2-chosen").html("请选择");
			  }

		  }else{
			  $("#complainPoint").html('<option value="">请选择</option>');  
			  $("#complainType_point").parent().find("div a .select2-chosen").html("请选择");
		  }
	}
	
	
	
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			$("#complainType").change(function(){					
				  var type = $(this).val();
				  setComplainType(type);
				});
			$("#type").change(function(){					
				  var type = $(this).val();
				  setHandleUserId(type);
				});
			$("#status").change(function(){					
				  var status = $(this).val();
				  $("#nextTime").addClass("required");
				  if (status == "3") {
					$("#nextTime").removeClass("required");
				}				  
				});
			
			setHandleUserId($("#type").val(),$("#handleUserId_hid").val());
			setComplainType($("#complainType").val(),$("#complainType_point").val());
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/loan/complainRecord/">投诉工单列表</a></li>
		<li class="active"><a href="${ctx}/loan/complainRecord/form?id=${complainRecord.id}">投诉工单<shiro:hasPermission name="loan:complainRecord:edit">${not empty complainRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="loan:complainRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="complainRecord" action="${ctx}/loan/complainRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="subject" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号码：</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投诉渠道：</label>
			<div class="controls">
			<form:select path="channel" htmlEscape="false" maxlength="2" class="input-xlarge required">
				<form:option value="400热线">400热线</form:option>
				<form:option value="21CN">21CN</form:option>
				<form:option value="微信">微信</form:option>		
				<form:option value="其他">其他</form:option>		
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
			
			<c:choose>
						<c:when test="${ complainRecord.id != null and isHandle != 'y'}">
							<form:select path="status" htmlEscape="false"  maxlength="2" disabled="true" class="input-xlarge required">
							<form:option value="1">未跟进</form:option>
							<form:option value="2">已跟进未完结</form:option>
							<form:option value="3">已完结</form:option>		
							</form:select>
						</c:when>
						<c:otherwise>
								<form:select path="status" htmlEscape="false"   maxlength="2" class="input-xlarge required">
								<form:option value="1">未跟进</form:option>
								<form:option value="2">已跟进未完结</form:option>
								<form:option value="3">已完结</form:option>		
								</form:select>
						</c:otherwise>
			</c:choose>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门：</label>
			<div class="controls">
				<form:select path="type" htmlEscape="false" maxlength="2" class="input-xlarge required">
					<form:option value="">请选择</form:option>
					<form:option value="2002">风控</form:option>
					<form:option value="2006">催收</form:option>
					<form:option value="2007">客服</form:option>
					<form:option value="2009">质检</form:option>
					<form:option value="2011">预提醒</form:option>
					<form:option value="2012">电销</form:option>		
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">跟进人：</label>
			<div class="controls">
			
			<input type="hidden" id="handleUserId_hid" value="${complainRecord.handleUserId}">
					<form:select path="handleUserId" htmlEscape="false"  maxlength="30" class="input-xlarge required">
					
					</form:select>
					
				
				<span class="help-inline"><font color="red">*</font> </span>
				
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">投诉类型：</label>
			<div class="controls">
				
				<form:select path="complainType" id="complainType" htmlEscape="false" maxlength="30" class="input-xlarge required">
					<form:option value="">请选择</form:option>
					<form:option value="3110">服务态度</form:option>
					<form:option value="3120">承诺未兑现</form:option>
					<form:option value="3130">业务操作错误</form:option>
					<form:option value="3140">业务规则</form:option>
					<form:option value="3150">提供错误信息</form:option>
					<form:option value="3160">业务流程</form:option>	
					<form:option value="3170">其它</form:option>		
				</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">投诉点：</label>
			<div class="controls">
			
			<input type="hidden" id="complainType_point" value="${complainRecord.complainPoint}">
				
				<form:select path="complainPoint" id="complainPoint" htmlEscape="false" maxlength="30" class="input-xlarge required">
					
				</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">紧急情况：</label>
			<div class="controls">
				<form:select path="emergency" htmlEscape="false" maxlength="2" class="input-xlarge">
					<form:option value="1">一般</form:option>
					<form:option value="2">紧急</form:option>
				</form:select>
			</div>
		</div>
		<!-- ------------------------------------ -->
		<c:if test="${isCreate == 'y' }">
			<c:if test="${isHandle == 'y' }">
				<form:hidden path="isHhandleUser" value="3"/>
			</c:if>
			<c:if test="${isHandle != 'y' }">
				<form:hidden path="isHhandleUser" value="1"/>
			</c:if>
		</c:if>
		<c:if test="${isCreate != 'y' }">
			<c:if test="${isHandle == 'y' }">
				<form:hidden path="isHhandleUser" value="2"/>
			</c:if>
			<c:if test="${isHandle != 'y' }">
				<form:hidden path="isHhandleUser" value="1"/>
			</c:if>
		</c:if>
		<!-- ------------------------------------ -->
		
		
		<c:choose>
			<c:when test="${ complainRecord.id != null}">
				<div class="control-group">
					<label class="control-label">反映问题：</label>
					<div class="controls">
						<c:if test="${isCreate == 'y' }">
							<form:textarea 	 path="content" htmlEscape="false" rows="4" style="height: 80px;" maxlength="1000" class="input-xxlarge "/>
						</c:if>
						<c:if test="${isCreate != 'y' }">
							<form:textarea   readonly="true"	 path="content" htmlEscape="false" rows="4" style="height: 80px;" maxlength="1000" class="input-xxlarge "/>
						</c:if>
						
					</div>
				</div>
				
				<c:if test="${isHandle == 'y' }">
					<div class="control-group">
						<label class="control-label">跟进处理：</label>
						<div class="controls">
							<form:textarea path="remark" htmlEscape="false" rows="4" style="height: 80px;" maxlength="1000" class="input-xxlarge required"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">下次跟进时间：</label>
						<div class="controls">
							<form:input path="nextTime" type="text" readonly="readonly" maxlength="200"
	                            class="input-middle Wdate" 
	                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
	                        <span class="help-inline"><font color="red">*</font> </span>
						</div>
					</div>
				</c:if>					
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">反映问题：</label>
					<div class="controls">
						<form:textarea path="content" htmlEscape="false" rows="4" style="height: 80px;" maxlength="1000" class="input-xxlarge "/>
					</div>
				</div>
			</c:otherwise>
		</c:choose>

		<div class="form-actions">
			<shiro:hasPermission name="loan:complainRecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>