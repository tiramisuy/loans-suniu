<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>影像资料-详情</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	</script>
</head>
<body>
	<form:form id="inputForm" method="post" class="form-horizontal">
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label"><span class="required">*</span>文件ID：</label>
			<div class="controls">
				${fileInfo.id}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="required">*</span>业务ID：</label>
			<div class="controls">
				${fileInfo.bizId}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务名称：</label>
			<div class="controls">
				${fileInfo.bizName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文件名称：</label>
			<div class="controls">
				${fileInfo.fileName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文件原名：</label>
			<div class="controls">
				${fileInfo.origName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文件类型：</label>
			<div class="controls">
				${fileInfo.fileType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文件后缀：</label>
			<div class="controls">
				${fileInfo.fileExt}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文件大小：</label>
			<div class="controls">
				${fileInfo.fileSize}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文件大小描述：</label>
			<div class="controls">
				${fileInfo.fileSizeDesc}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在服务器：</label>
			<div class="controls">
				${fileInfo.server}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相对地址：</label>
			<div class="controls">
				${fileInfo.relativePath}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">绝对地址：</label>
			<div class="controls">
				${fileInfo.absolutePath}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">访问地址：</label>
			<div class="controls">
				${fileInfo.url}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文件来源IP：</label>
			<div class="controls">
				${fileInfo.ip}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="required">*</span>状态：</label>
			<div class="controls">
				${fileInfo.status}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="required">*</span>备注信息：</label>
			<div class="controls">
				${fileInfo.remark}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="required">*</span>创建者：</label>
			<div class="controls">
				${fileInfo.createBy}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="required">*</span>创建时间：</label>
			<div class="controls">
				${fileInfo.createTime}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="required">*</span>最后修改者：</label>
			<div class="controls">
				${fileInfo.updateBy}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="required">*</span>最后修改时间：</label>
			<div class="controls">
				${fileInfo.updateTime}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="required">*</span>删除标记：0-正常，1-已经删除：</label>
			<div class="controls">
				${fileInfo.del}
			</div>
		</div>
	</form:form>
</body>
</html>