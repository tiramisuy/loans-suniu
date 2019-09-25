<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>导流平台产品信息管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>
    
	    <link rel="stylesheet" href="${ctxStatic}/layui_2/css/layui.css">
    <script src="${ctxStatic}/layui_2/layui.js"></script>
    
	<script type="text/javascript">
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
			
			layui.use('upload', function(){
				  var upload = layui.upload;
				   
				  //执行实例
				  var uploadInst = upload.render({
				    elem: '#logoUrl_btn' //绑定元素
				    ,exts:'jpg|png|gif|bmp|jpeg'
				    ,url: '/a/upload/logo' //上传接口
				    ,done: function(res){
				     if(res.url && res.url !="null"){
				    	$("[name='logoUrl']").val(res.url); 
				    	 alert("上传成功");
				     }else{
				    	 alert("上传失败");
				     }
				    }
				    ,error: function(){
				      //请求异常回调
				    	alert("上传失败");
				    }
				  });
				});
			
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/loan/loanTraffic/">导流平台产品信息列表</a></li>
		<li class="active"><a href="${ctx}/loan/loanTraffic/form?id=${loanTraffic.id}">导流平台产品信息${not empty loanTraffic.id?'修改':'添加'}</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="loanTraffic" action="${ctx}/loan/loanTraffic/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">产品类型（0-现金贷）：</label>
			<div class="controls">
				<form:input path="type" htmlEscape="false" maxlength="4" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">平台名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起始金额：</label>
			<div class="controls">
				<form:input path="minAmt" htmlEscape="false" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大金额：</label>
			<div class="controls">
				<form:input path="maxAmt" htmlEscape="false" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最小周期：</label>
			<div class="controls">
				<form:input path="minTerm" htmlEscape="false" maxlength="10" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大周期：</label>
			<div class="controls">
				<form:input path="maxTerm" htmlEscape="false" maxlength="10" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借款单位：</label>
			<div class="controls">				
				<form:select path="repayUnit" htmlEscape="false" maxlength="10" class="input-xlarge required">
				<form:option value="">请选择</form:option>
				<form:option value="M">月</form:option>
				<form:option value="Q">季</form:option>
				<form:option value="Y">年</form:option>	
				<form:option value="D">天</form:option>		
				</form:select>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">logo：</label>
			<div class="controls">
				<form:input path="logoUrl" htmlEscape="false" maxlength="100" class="input-xlarge " readonly="true"/>
				
				<button type="button" class="layui-btn" id="logoUrl_btn">
	 				 <i class="layui-icon">&#xe67c;</i>上传图片
				</button>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">跳转链接：</label>
			<div class="controls">
				<form:input path="platformUrl" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品描述：</label>
			<div class="controls">
				<form:input path="desc" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<!-- 
		<div class="control-group">
			<label class="control-label">点击量：</label>
			<div class="controls">
				<form:input path="hits" htmlEscape="false" maxlength="50" class="input-xlarge  digits"/>
			</div>
		</div>
		 -->
		<div class="control-group">
			<label class="control-label">排位：</label>
			<div class="controls">
				<form:input path="scort" htmlEscape="false" maxlength="10" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品状态：</label>
			<div class="controls">
				
				<form:select path="status" htmlEscape="false" maxlength="10" class="input-xlarge required">
				<form:option value="1">正常</form:option>			
				<form:option value="2">下架</form:option>		
				</form:select>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">显示终端：</label>
			<div class="controls">
				<form:select path="remark" htmlEscape="false" maxlength="10" class="input-xlarge required">
				<form:option value="3">全部</form:option>	
				<form:option value="1">ios</form:option>			
				<form:option value="2">安卓</form:option>		
				</form:select>
			</div>
		</div>
		
		<div class="form-actions">
			<%--<shiro:hasPermission name="loan:loanTraffic:edit"></shiro:hasPermission> --%>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>