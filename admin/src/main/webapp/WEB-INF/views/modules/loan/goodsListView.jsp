<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品信息表-详情</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	</script>
</head>
<body>
	<form:form id="inputForm" method="post" class="form-horizontal">
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label"><span class="required">*</span>id：</label>
			<div class="controls">
				${goodsList.id}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品名称：</label>
			<div class="controls">
				${goodsList.goodsName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品单价：</label>
			<div class="controls">
				${goodsList.goodsPrice}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市场价：</label>
			<div class="controls">
				${goodsList.marketPrice}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品图片url：</label>
			<div class="controls">
				${goodsList.goodsPic}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建者：</label>
			<div class="controls">
				${goodsList.createBy}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				${goodsList.createTime}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后修改者：</label>
			<div class="controls">
				${goodsList.updateBy}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后修改时间：</label>
			<div class="controls">
				${goodsList.updateTime}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">删除标记：0-正常，1-已经删除：</label>
			<div class="controls">
				${goodsList.del}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">0:下架 1：上架：</label>
			<div class="controls">
				${goodsList.status}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">简称：</label>
			<div class="controls">
				${goodsList.simpleName}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">销量：</label>
			<div class="controls">
				${goodsList.salesVolume}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展示图片：</label>
			<div class="controls">
				${goodsList.picBanner}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详情：</label>
			<div class="controls">
				${goodsList.picDetail}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">max_coupon：</label>
			<div class="controls">
				${goodsList.maxCoupon}
			</div>
		</div>
	</form:form>
</body>
</html>