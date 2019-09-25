<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据下载表-详情</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	</script>
</head>
<body>
	<form:form id="inputForm" method="post" class="form-horizontal">
		<sys:message content="${message}"/>		
		<%-- <div class="control-group">
			<label class="control-label"><span class="required">*</span>id：</label>
			<div class="controls">
				${dataDownload.id}
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">数据类型：</label>
			<div class="controls">
				<c:forEach items="${typeList}" var="item">
						<c:if test="${dataDownload.type eq item.value}">${item.desc}</c:if>
					</c:forEach>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="required">*</span>标题：</label>
			<div class="controls">
				${dataDownload.title}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="required">*</span>数据连接：</label>
			<div class="controls">
				${dataDownload.dataUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<fmt:formatDate value="${dataDownload.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<fmt:formatDate value="${dataDownload.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				${dataDownload.remark}
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label"><span class="required">*</span>创建者：</label>
			<div class="controls">
				${dataDownload.createBy.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="required">*</span>创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${dataDownload.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="required">*</span>最后修改者：</label>
			<div class="controls">
				${dataDownload.updateBy.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="required">*</span>最后修改时间：</label>
			<div class="controls">
				<fmt:formatDate value="${dataDownload.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label"><span class="required">*</span>删除标记：0-正常，1-已经删除：</label>
			<div class="controls">
				${dataDownload.del}
			</div>
		</div> --%>
	</form:form>
</body>
</html>