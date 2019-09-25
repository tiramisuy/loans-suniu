<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/server/info">服务器信息</a></li>
	</ul><br/>
	<form:form id="inputForm" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">JDK版本：</label>
			<div class="controls">
				<label class="lbl"><%=System.getProperty("java.version") %></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作系统版本：</label>
			<div class="controls">
				<label class="lbl"><%=System.getProperty("os.name") %> <%=System.getProperty("os.version") %></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作系统类型：</label>
			<div class="controls">
				<label class="lbl"><%=System.getProperty("os.arch") %> <%=System.getProperty("sun.arch.data.model") %></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户,目录,临时目录：</label>
			<div class="controls">
				<label class="lbl"><%=System.getProperty("user.name") %>,<%=System.getProperty("user.dir") %>,<%=System.getProperty("java.io.tmpdir") %></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">JAVA运行环境：</label>
			<div class="controls">
				<label class="lbl"><%=System.getProperty("java.runtime.name") %> <%=System.getProperty("java.runtime.version") %></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">JAVA虚拟机：</label>
			<div class="controls">
				<label class="lbl"><%=System.getProperty("java.vm.name") %> <%=System.getProperty("java.vm.version") %></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已用/剩余/最大 内存：</label>
			<div class="controls">
				<label class="lbl"> <%=(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1048576 %>MB/
    <%=(Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1048676 %>MB/
    <%=Runtime.getRuntime().maxMemory()/1048576 %>MB</label>
			</div>
		</div>
		</form:form>
</body>
</html>