<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>导流平台产品信息-详情</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	</script>
</head>
<body>
	<form:form id="inputForm" method="post" class="form-horizontal">
		<sys:message content="${message}"/>		
		<!-- 
		<div class="control-group">
			<label class="control-label"><span class="required">*</span>id：</label>
			<div class="controls">
				${loanTraffic.id}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品类型（0-现金贷）：</label>
			<div class="controls">
				${loanTraffic.type}
			</div>
		</div>
		 -->
		<div class="control-group">
			<label class="control-label">平台名称：</label>
			<div class="controls">
				${loanTraffic.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起始金额：</label>
			<div class="controls">
				${loanTraffic.minAmt}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大金额：</label>
			<div class="controls">
				${loanTraffic.maxAmt}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最小周期：</label>
			<div class="controls">
				${loanTraffic.minTerm}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最大周期：</label>
			<div class="controls">
				${loanTraffic.maxTerm}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借款单位：</label>
			<div class="controls">
				${loanTraffic.repayUnit == "M"?"月":loanTraffic.repayUnit == "Q"?"季":loanTraffic.repayUnit == "Y"?"年":loanTraffic.repayUnit == "D"?"天":""}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">logo：</label>
			<div class="controls">
				${loanTraffic.logoUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">跳转链接：</label>
			<div class="controls">
				${loanTraffic.platformUrl}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品描述：</label>
			<div class="controls">
				${loanTraffic.desc}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">点击量：</label>
			<div class="controls">
				${loanTraffic.hits}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排位：</label>
			<div class="controls">
				${loanTraffic.scort}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品状态：</label>
			<div class="controls">
				${loanTraffic.status ==0?"初始":loanTraffic.status ==1?"正常":loanTraffic.status ==2?"下架":loanTraffic.status}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">显示终端：</label>
			<div class="controls">
				${loanTraffic.remark == "1"?"ios":loanTraffic.remark == "2"?"android":loanTraffic.remark == "3"?"全部":loanTraffic.remark}
			</div>
		</div>
		<!-- 
		
		<div class="control-group">
			<label class="control-label">创建者：</label>
			<div class="controls">
				${loanTraffic.createBy}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				${loanTraffic.createTime}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后修改者：</label>
			<div class="controls">
				${loanTraffic.updateBy}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后修改时间：</label>
			<div class="controls">
				${loanTraffic.updateTime}
			</div>
		</div>
		 -->
		
	</form:form>
</body>
</html>