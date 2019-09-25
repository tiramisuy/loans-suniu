<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>投诉工单-详情</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	</script>
</head>
<body>
	<form:form id="inputForm" method="post" class="form-horizontal">
		<sys:message content="${message}"/>		
		
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				${complainRecord.subject}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户名称：</label>
			<div class="controls">
				${complainRecord.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号码：</label>
			<div class="controls">
				${complainRecord.mobile}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投诉渠道：</label>
			<div class="controls">
				${complainRecord.channel}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				${complainRecord.status == 1?"未跟进":complainRecord.status == 2?"已跟进未完结":complainRecord.status == 3?"已完结":""}	
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门：</label>
			<div class="controls">
				 ${complainRecord.type == "2002"?"风控":complainRecord.type == "2006"?"催收":complainRecord.type == "2007"?"客服":
				   complainRecord.type == "2009"?"质检":complainRecord.type == "2011"?"预提醒":complainRecord.type == "2012"?"电销":""}	
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">紧急情况：</label>
			<div class="controls">
				${complainRecord.emergency == 1?"一般":complainRecord.emergency == 2?"紧急":""}
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">跟进人姓名：</label>
			<div class="controls">
				${complainRecord.handleUserName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投诉类型：</label>
			<div class="controls">
					${complainRecord.complainType == "3110"?"服务态度":complainRecord.complainType == "3120"?"承诺未兑现":complainRecord.complainType == "3130"?"业务操作错误":
					complainRecord.complainType == "3140"?"业务规则":complainRecord.complainType == "3150"?"提供错误信息":complainRecord.complainType == "3160"?"业务流程":complainRecord.complainType == "3170"?"其他":complainRecord.complainType}
			
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反映问题：</label>
			<div class="controls">
				${complainRecord.complainPoint}
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">反映问题：</label>
			<div class="controls">
				${complainRecord.content}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">跟进处理：</label>
			<div class="controls">
				${complainRecord.remark}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建者：</label>
			<div class="controls">
				${complainRecord.createBy}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<fmt:formatDate value="${complainRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后修改者：</label>
			<div class="controls">
				${complainRecord.updateBy}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后修改时间：</label>
			<div class="controls">
				<fmt:formatDate value="${complainRecord.lastCreaterTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后跟进人：</label>
			<div class="controls">
				${complainRecord.handleUserName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">跟进人最后修改时间：</label>
			<div class="controls">
				<fmt:formatDate value="${complainRecord.lastHanderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		
	</form:form>
</body>
</html>