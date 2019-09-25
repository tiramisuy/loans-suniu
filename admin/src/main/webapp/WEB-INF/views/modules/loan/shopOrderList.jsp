<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>购物订单表管理</title>
	<meta name="decorator" content="default"/>
	  <link rel="stylesheet" href="${ctxStatic}/layui-master/build/css/layui.css">
    <script src="${ctxStatic}/layui-master/src/layui.js"></script>
    <script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>
    <script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js" type="text/javascript"></script>
    <script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>
	 <style type="text/css">
        .form-search .ul-form li .error {
            width: auto !important;
        }
        #collection{
            width: 1051px;
            height: 791px;
            border: 1px solid rgba(221,221,221,1);
            position: relative;
        }
        #collections{
            height: 37px;
            line-height: 37px;
            background: rgba(242,242,242,1);
        }
        .collection{
            margin: 100px auto 0;
            width:867px;
            height:377px;
            border: 1px solid #cccccc;
            overflow: hidden;
            position: relative;
        }
        .collections{
            width:839px;
            height:330px;
            background-color:rgba(236, 246, 251, 1);
            margin-top: 23px;
            margin-left: 13px;
        }
        .clears:after{
            content: "";
            display: block;
            clear: both;
        }
        .collections-top{
            height: 36px;
            line-height: 36px;
            font-size: 14px;
            width: 820px;
            margin: 0 auto;
            border-bottom: 1px solid #cccccc;
        }
        .collections-top div,.collections-con div{
            float: left;
            width: 200px;
        }
        .collections-con{
            width: 800px;
            height: 278px;
            overflow-y: auto;
        }
        .collections-top em{
            color: #FF0000;
        }
        .collections-con div{
            font-size: 12px;
            color: #666;
            text-indent: 10px;
            height: 40px;
            line-height: 40px;
        }
        .collections-con div input{
            margin-right: 15px;
            margin-top: 3px;
        }
        .collections-con .collections-cons{
            width: 500px;
        }
        .collection select{
            width: 152px;
            height: 22px;
            font-size: 12px;
        }
        .collections-name,.collections-time{
            position: absolute;
            left: 422px;
            top: 68px;
        }
        .collections-time{
            left: 621px;
        }
        .collection-bot{
            width: 328px;
            margin: 28px auto 0;
        }
        .collection-bot-left,.collection-bot-right{
            width: 164px;
            height: 45px;
            text-align: center;
            line-height: 45px;
            cursor: pointer;
            color: #fff;
            background: rgba(25, 158, 216, 1);
            border-radius: 6px;
            float: left;
        }
        .collection-bot-right{
            background: #ffffff;
            color: #333;
        }
        a{
    		color: #2fa4e7;
    		text-decoration: none;
		}
		
		#menu{  
            font-size: 18px;  
            font-weight: bold;  
        }  
        #menu li{  
            text-decoration: none;  
            list-style: none;  
            display: inline-block;
            float: left;
            padding-left: 10px;
        }  
    </style>
	
	
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/loan/shopOrder/list">购物订单表列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="shopOrder" action="${ctx}/loan/shopOrder/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单ID</label> 
				<form:input path="id" htmlEscape="false" class="input-medium" />
			</li>
			<li><label>客户姓名</label>
	            <form:input path="userName" htmlEscape="false" maxlength="20" class="input-medium"/>
	        </li>
	        <li><label>客户手机号码</label>
	            <form:input path="mobile" htmlEscape="false" maxlength="11" class="input-medium"/>
	        </li>
		</ul>
		<ul class="ul-form">
	        <li><label>订单状态</label>
	            <form:select path="status" class="input-medium">
	           		<form:option value="">全部</form:option>
	                <form:option value="0">未支付</form:option>
	                <form:option value="1">已支付</form:option>
	                <form:option value="2">已取消</form:option>
	                <form:option value="3">已发货</form:option>
	                <form:option value="4">付款中</form:option>
	            </form:select>
	        </li>
	        <li><label>收货人姓名</label>
	            <form:input path="deliveryName" htmlEscape="false" maxlength="20" class="input-medium"/>
	        </li>
	        <li><label>收货人号码</label>
	            <form:input path="deliveryMobile" htmlEscape="false" maxlength="11" class="input-medium"/>
	        </li>
	        <li class="time_li"><label>申请时间</label> <form:input id="startTime"
					path="startTime" type="text" readonly="readonly" maxlength="20"
					class="input-small Wdate"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});" />
			<h>-</h> <form:input id="endTime" path="endTime"
					type="text" readonly="readonly" maxlength="20"
					class="input-small Wdate"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});" />
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>订单ID</th>
				<th>商品名称</th>
				<th>客户姓名</th>
				<th>客户手机号码</th>
				<th>收货人姓名</th>
        		<th>收货人手机号码</th>
        		<th>收货地址</th>
        		<th>快递单号</th>
        		<th>商品价格</th>
        		<th>商品详情</th>
        		<th>优惠券面值</th>	
        		<th>结算价格</th>
        		<th>订单状态</th>
        		<th>申请时间</th>
        		<th>支付时间</th>
        		<th>发货时间</th>
        		<th style="min-width: 100px;">操作</th>	
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item" varStatus="xh">
			<tr>
				<td>
					${xh.count}
				</td>
				<td>
					${item.id}
				</td>
				<td>
					${item.goodsName}
				</td>
				<td>
					${item.userName}
				</td>
				<td>
					${item.userPhone}
				</td>
				<td>
					${item.name}
				</td>
				<td>
					${item.phone}
				</td>
				<td>
					${item.address}
				</td>
				<td>
					${item.expressNo}
				</td>
				<td>
					${item.goodsPrice}
				</td>
				<td>
					${item.remark}
				</td>
				<td>
					${item.coupon}
				</td>
				<td>
					${item.price}
				</td>
				<td>
					<c:choose>
						<c:when test="${item.status == '0'}">
							未支付
						</c:when>
						<c:when test="${item.status == '1'}">
							已支付
						</c:when>
						<c:when test="${item.status == '2'}">
							已取消
						</c:when>
						<c:when test="${item.status == '3'}">
							已发货
						</c:when>
						<c:otherwise>
							付款中
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${item.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${item.deliverTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:if test="${item.status == '1'}">
						<a href="javascript:void(0)" onclick="shopExpressNo('${item.id}','${ctx}')" style="padding-left:5px;">确认发货</a>
					</c:if>
					<c:if test="${item.status != '3' and item.status != '4' and item.status != '2'}">
						<a href="javascript:void(0)" onclick="cancelDeliver('${item.id}')" style="padding-left:5px;">取消</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<script id="shop_express_no" type="text/x-handlebars-template">
    		<jsp:include page="shopExpressNo.jsp"></jsp:include>
	</script>
	<script>
	
	
	
	
	function shopExpressNo(shopOrderID,ctx) {
		var param = {
				shopOrderID : shopOrderID
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/shopOrder/toConfimShop",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						var myTemplate = Handlebars.compile($("#shop_express_no")
								.html());
						
						var data1 = {
								shopOrderID : data.data
							};
						var html = myTemplate(data1);
						layer.open({
							type : 1,
							title : '确认发货',
							area : [ '30%', '25%' ], //宽高
							content : html
						})
					} else {
						alert(data.msg)
					}
		
				},
		
			});
		
	};
	
	
	
	
	
		function deliverConfirm(shopOrderID) {
			var exPressNo = $("#exPressNo").val();
			if(exPressNo==undefined || exPressNo==""){
				alert("请填写快递单号");
				return;
			}
			
			var param = {
				shopOrderID : shopOrderID,
				expressNo : exPressNo
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/shopOrder/deliverConfirm",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						$("#searchForm").submit();
						//　window.location.href="${ctx}/loan/shopOrder/list";
					} else {
						alert(data.msg)
					}
				},
	
			});
		};
		
	
		
		function cancelDeliver(shopOrderID) {
			layer.confirm('您确定要取消发货吗？', {
				btn : [ '提交', '取消' ]
			}, function(index) {
				cancelConfirm(shopOrderID);
				layer.close(index);
			});
		};
		function cancelConfirm(shopOrderID) {
			var param = {
				shopOrderID : shopOrderID
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/shopOrder/cancelDeliver",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						location.reload(true);
					} else {
						alert(data.msg)
					}
				},
	
			});
		};
	</script>
</body>
</html>