<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>购物款代扣信息</title>
<meta name="decorator" content="default" />
<link rel="stylesheet"
	href="${ctxStatic}/layui-master/build/css/layui.css">
<script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>
<script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>

<style type="text/css">
a {
	color: #2fa4e7;
	text-decoration: none;
}

<
style type ="text/css">.l {
	float: left;
}

.r {
	float: right;
}

.form-search .ul-form li .error {
	width: auto !important;
}

#collection {
	width: 1051px;
	height: 791px;
	border: 1px solid rgba(221, 221, 221, 1);
	position: relative;
}

#collections {
	height: 37px;
	line-height: 37px;
	background: rgba(242, 242, 242, 1);
}

.collection {
	margin: 100px auto 0;
	width: 867px;
	height: 377px;
	border: 1px solid #cccccc;
	overflow: hidden;
	position: relative;
}

.collections {
	width: 839px;
	height: 330px;
	background-color: rgba(236, 246, 251, 1);
	margin-top: 23px;
	margin-left: 13px;
}

.clears:after {
	content: "";
	display: block;
	clear: both;
}

.collections-top {
	height: 36px;
	line-height: 36px;
	font-size: 14px;
	width: 820px;
	margin: 0 auto;
	border-bottom: 1px solid #cccccc;
}

.collections-top div {
	float: left;
	width: 200px;
}

.collections-con {
	width: 800px;
	height: 278px;
	overflow-y: auto;
}

.collections-top em {
	color: #FF0000;
}

.collections-con div {
	font-size: 12px;
	color: #666;
	text-indent: 10px;
	height: 40px;
	line-height: 40px;
}

.collections-con div input {
	margin-right: 15px;
	margin-top: 3px;
}

.collections-con .collections-cons {
	width: 500px;
}

.collection select {
	width: 152px;
	/*height: 22px;
            font-size: 12px;*/
}
/* .collections-name,.collections-time{
            position: absolute;
            left: 422px;
            top: 68px;
        } */
.collections-time {
	left: 621px;
}

.collection-bot {
	width: 328px;
	margin: 28px auto 0;
}

.collection-bot-left,.collection-bot-right {
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

.collection-bot-right {
	background: #ffffff;
	color: #333;
}

.collections-top .wAuto {
	display: inline-block;
	width: auto;
	margin-right: 18px;
}

a {
	color: #2fa4e7;
	text-decoration: none;
}
</style>



<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>

	<ul class="nav nav-tabs">
		<li class="active"><a
			href="${ctx}/loan/shopWithHold/withHoldList">购物款代扣信息</a></li>
	</ul>

	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="pull-left">
			<ol class="breadcrumb page-breadcrumb">
				<li><i class="fa fa-home"></i>&nbsp;<a href="#">贷后管理</a></li>
				<li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">催收管理</a></li>
				<li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;购物款代扣信息
				</li>
			</ol>
		</div>
		<div class="clearfix"></div>
	</div>
	<!--END TITLE & BREADCRUMB PAGE-->

	<form:form id="searchForm" modelAttribute="ShopWithholdOP"
		action="${ctx}/loan/shopWithHold/withHoldList" method="post"
		class="breadcrumb form-search layui-form">

		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />

		<ul class="ul-form">
			<li><label>姓名</label> <form:input path="realName"
					htmlEscape="false" maxlength="20" class="input-medium" /></li>
			<li><label>手机号</label> <form:input path="mobile"
					htmlEscape="false" maxlength="20" class="input-medium" /></li>
			<li><label>证件号码</label> <form:input path="idNo"
					htmlEscape="false" maxlength="20" class="input-medium" /></li>
		</ul>
		
		<ul class="ul-form">
			<li class="time_li"><label>代扣时间</label> <form:input id='searchStrat'
					path="searchStrat" type="text" readonly="readonly" maxlength="20"
					class="input-middle Wdate" value="${ShopWithholdOP.searchStrat}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});" />
			<h>-</h> <form:input id='searchEnd' path="searchEnd"
					type="text" readonly="readonly" maxlength="20"
					class="input-middle Wdate" value="${ShopWithholdOP.searchEnd}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});" />
			</li>
			<li>
				<label>代扣状态</label> 
				<form:select path="withholdStatus" id="withholdStatus" class="input-medium">
					<form:option value="">全部</form:option>
					<form:option value="0">成功</form:option>
					<form:option value="1">失败</form:option>
					<form:option value="2">处理中</form:option>
					<form:option value="3">已取消</form:option>
				</form:select>
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li>
		</ul>

	</form:form>

	<div class="row">
			<div class="col-sm-12 pull-left">
				<!-- 导出按钮 -->
	<!-- 			<button id="btnExport" class="btn btn-white btn-sm" id="btnImport"
					data-toggle="tooltip" data-placement="left"
					onclick="return validateExport(1)">
					<i class="fa fa-file-excel-o"></i> 导出
				</button> -->
				
<!-- 				<button id="btnExport" class="btn btn-white btn-sm" id="btnImport"
					data-toggle="tooltip" data-placement="left"
					onclick="return validateExport(2)">
					<i class="fa fa-file-excel-o"></i> 导出还款计划
				</button> -->
				
			</div>
		</div>



	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>借款人姓名</th>
				<th>证件号码</th>
				<th>手机号码</th>
				<th>创建时间</th>
				<th>代扣金额</th>
				<th>代扣时间</th>
				<th>代扣状态</th>
				<th>代扣次数</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="item" varStatus="xh">
				<tr>
					<td>${xh.count}</td>
					<td>${item.realName}</td>
					<td>${item.idNo}</td>
					<td>${item.mobile}</td>
					<td><fmt:formatDate value="${item.createTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${item.withholdFee}</td>
					<td><fmt:formatDate value="${item.withholdTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><c:if test="${item.withholdStatus == 0}"><label style="color: green;">成功</label></c:if> <c:if
							test="${item.withholdStatus == 1}"><label style="color: red;">失败</label></c:if> <c:if
							test="${item.withholdStatus == 2}"><label style="color:orange;">处理中</label></c:if>
							<c:if test="${item.withholdStatus == 3}"><label style="color:orange;">已取消</label></c:if>
							
							</td>
					<td>${item.withholdNumber}</td>
					<td>${item.remark}</td>
					<td>
						<c:if
							test="${item.withholdStatus == 1 and item.withholdNumber > 3}">
							<a href="javascript:void(0)" data-method="offset"
								data-type="auto" style="padding-left: 5px;"
								onclick="confirmShopWithhold('${item.id }')">手动代扣</a>
								
									<a href="javascript:void(0)" data-method="offset"
								data-type="auto" style="padding-left: 5px;"
								onclick="confirmUnderLineShopWithhold('${item.applyId }')">线下还款</a>
							<a href="javascript:void(0)" data-method="offset"
								data-type="auto" style="padding-left: 5px;"
								onclick="canselShopWithhold('${item.applyId }')">取消放款</a>
						</c:if>
						
						</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

	<script id="tpl_creditList" type="text/x-handlebars-template">
    <jsp:include page="repayList.jsp"></jsp:include>
</script>

	<script id="tpl_creditList_2" type="text/x-handlebars-template">
    <jsp:include page="userInfo.jsp"></jsp:include>
</script>

	<script id="tpl_creditList_3" type="text/x-handlebars-template">
    <jsp:include page="deductionForm.jsp"></jsp:include>
</script>

<script id="tpl_withhold_bank" type="text/x-handlebars-template">
    <jsp:include page="shopWithholdBank.jsp"></jsp:include>
</script>


	<script id="tpl_detail" type="text/x-handlebars-template">
    <jsp:include page="allotmentList.jsp"></jsp:include>
</script>
	<script id="tpl_assignment" type="text/x-handlebars-template">
    <jsp:include page="collectionAssignment.jsp"></jsp:include>
</script>

	<script>
	
	
	
	
	function validateExport(str) {
		var startTime = $("#searchStrat").val();
		var endTime = $("#searchEnd").val();
		var withholdStatus = $("#withholdStatus").val();
		
		var flag1 = false;
		if ("" == startTime && "" == endTime) {
			flag1 = true;
		}
		;
		
		if (("" == startTime && "" != endTime) || ("" != startTime && "" == endTime)) {
			layer.alert('请选择时间段', {
				icon : 7
			});
			return false;
		};
		
		if (flag1) {
			layer.alert('请选择导出时间', {
				icon : 7
			});
			return false;
		}
		var startTimestamp = new Date(startTime).getTime();
		var endTimestamp = new Date(endTime).getTime();
	
		
		if (startTimestamp > endTimestamp) {
			layer.alert('起始日期应在结束日期之前', {
				icon : 7
			});
			return false;
		}
		
		if ((endTimestamp - startTimestamp) > 183 * 24 * 60 * 60 * 1000) {
			layer.alert('时间段最长为半年', {
				icon : 7
			});
			return false;
		}
			if(str == 1){
				return exportExcel(
						'${ctx}/loan/shopWithHold/withHoldExport',
						'${ctx}/loan/shopWithHold/withHoldList');
			}
			else if(str == 2){
				if("" == withholdStatus){
					layer.alert('请选择代扣状态', {
						icon : 7
					});
					return false;
				}
				if(withholdStatus != 1 && withholdStatus != 3){
					layer.alert('请选择适合的代扣状态', {
						icon : 7
					});
					return false;
				}
				return exportExcel(
						'${ctx}/loan/shopWithHold/exportRepayPlan',
						'${ctx}/loan/shopWithHold/withHoldList');
			}
	};
	
	
	
	
	function confirmShopWithhold(handShopWithHoldId) {
		layer.confirm('您确定手动代扣吗？', {
			btn : [ '提交', '取消' ]
		}, function(index) {
			commitShopWithHold(handShopWithHoldId);
			layer.close(index);
		});
	}
	
	
		function commitShopWithHold(handShopWithHoldId) {
			var param = {
				id : handShopWithHoldId
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/shopWithHold/handShopWithHold",
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
		
		
		
		
		function confirmUnderLineShopWithhold(handShopWithHoldId) {
			/* layer.confirm('您确定线下还款吗？', {
				btn : [ '提交', '取消' ]
			}, function(index) { */
			/* 	commitUnderLine(handShopWithHoldId); */
					var myTemplate = Handlebars.compile($("#tpl_withhold_bank").html());
					var date = {
								applyId : handShopWithHoldId,
								operateType : 0	//操作类型 0 为线下还款
								
								};
							var html = myTemplate(date);
							layer.open({
								type : 1,
								title : '线下还款',
								area : [ '30%', '35%' ], //宽高
								content : html
							})
						
			/* 	layer.close(index); */
			/* }); */
		}
		
		
		function commitUnderLine(handShopWithHoldId) {
			var chlId =  $("#repayType ").val();	//渠道id
			var chlName = $("#repayType").find("option:selected").text();
			if(chlId == ""){
				layer.alert('请选择还款渠道', {
					icon : 7
				});
				return false;
			};

			   $(".layui-btn").addClass("layui-btn-disabled");
	            $(".layui-btn").attr('disabled',true);
			
			var param = {
				applyId : handShopWithHoldId,
				chlId :chlId,
				chlName : chlName
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/shopWithHold/underLineShopWithHold",
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

		
		//取消
		function canselShopWithhold(handShopWithHoldId){
							var myTemplate = Handlebars.compile($("#tpl_withhold_bank").html());
							var date = {
										applyId : handShopWithHoldId,
										operateType : 1	//操作类型 0 为线下还款
										};
									var html = myTemplate(date);
									layer.open({
										type : 1,
										title : '取消放款',
										area : [ '30%', '35%' ], //宽高
										content : html
									})
						/* layer.close(index); */
		}
		
		
		
		function commitCansel(handShopWithHoldId) {
			var chlId =  $("#repayType ").val();	//渠道id
			var chlName = $("#repayType").find("option:selected").text();
			if(chlId == ""){
				layer.alert('请选择还款渠道', {
					icon : 7
				});
				return false;
			};
			
			$(".layui-btn").addClass("layui-btn-disabled");
	        $(".layui-btn").attr('disabled',true);
			
			var param = {
				applyId : handShopWithHoldId,
				chlId :chlId,
				chlName : chlName
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/shopWithHold/canselShopWithHold",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						location.reload(true);
					} else {
						alert(data.msg);
						location.reload(true);
					}
				},

			});
		};

		

		layui.use([ 'form', 'element' ], function() {
			var $ = layui.jquery, element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块

			var form = layui.form()
		});
	</script>
</body>

</html>