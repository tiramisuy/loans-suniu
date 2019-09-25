<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>
		配置管理
</title>
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
		<li class="active">
			配置管理
		</li>
	</ul>
	<!--BEGIN TITLE & BREADCRUMB PAGE-->
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="pull-left">
			<ol class="breadcrumb page-breadcrumb">
				<li><i class="fa fa-home"></i>&nbsp;<a href="#">我的工作台</a></li>
				<li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">配置管理</a></li>
			</ol>
		</div>
		<div class="clearfix"></div>
	</div>
	<!--END TITLE & BREADCRUMB PAGE-->

	<form:form id="searchForm" modelAttribute="config"
		action="${ctx}/sys/config/list" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table class="table">
		<thead>
			<tr>
				<th style="min-width: 150px;">编号</th>
				<th style="min-width: 150px;">值</th>
				<th style="min-width: 150px;">备注</th>
				<th style="min-width: 150px;">操作人</th>
				<th style="min-width: 150px;">操作时间</th>
				<th style="min-width: 150px;">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="item" varStatus="xh">
				<tr>
					<td>${item.key }</td>
					<td>${item.value }</td>
					<td>${item.remark }</td>
					<td>${item.updateBy }</td>
					<td><fmt:formatDate value="${item.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
						<a href="javascript:void(0)" onclick="changeBasicConfig('${item.id}','${item.key }')"
							style="padding-left: 5px;">修改</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="pagination">${page}</div>
	<script id="tpl_configForm" type="text/x-handlebars-template">
    	<jsp:include page="configForm.jsp"></jsp:include>
	</script>
	<script>
		function changeBasicConfig(id,key) {
			var list = [
				{
					name : 'day_15_pay_channel',
					title : '放款渠道',
					data : [{text:'口袋存管',value:3},{text:'乐视',value:4},{text:'汉金所',value:5},{text:'通联',value:7}]
				},
				{
					name : 'koudai_day_max_amt',
					title : '口袋放款金额',
					data : [{text:'200000',value:200000},{text:'500000',value:500000},{text:'700000',value:700000},{text:'1000000',value:1000000}]
				},
                {
                    name : 'tonglian_day_max_amt',
                    title : '通联每日放款限额',
                    data : [{text:'11000',value:11000},{text:'20000',value:20000},{text:'50000',value:50000},{text:'100000',value:100000},{text:'500000',value:500000},{text:'1000000',value:1000000}]
                },
				{
					name : 'xjbkfq_limit_day',
					title : '限制天数',
					data : [{text:"50",value:"50"}]
				},
				{
					name : 'repay_method',
					title : '还款方式',
					data : [{text:'宝付代扣',value:"1"},{text:'先锋支付',value:"2"},{text:'通联支付',value:"3"}]
				},
				{
					name : 'leshi_day_max_amt',
					title : '乐视放款金额',
					data : [{text:'3000000',value:3000000},{text:'3500000',value:3500000},{text:'4000000',value:4000000},{text:'4500000',value:4500000},{text:'5000000',value:5000000}]
				},
				{
					name : 'hanjs_day_max_amt',
					title : '汉金所放款金额',
					data : [{text:'100000',value:100000},{text:'200000',value:200000},{text:'300000',value:300000},{text:'400000',value:400000},{text:'500000',value:500000}]
				},
				{
					name : 'share_jucai_status_flag',
					title : '推送给聚财用户开关',
					data : [{text:'开启',value:0},{text:'关闭',value:1}]
				},
				{
					name : 'share_jucai_max_amount',
					title : '推送给聚财用户每日限额',
					data : [{text:'1000',value:'1000'},{text:'1500',value:'1500'},{text:'2000',value:'2000'}]
				},
				{
					name : 'user_approve_apply_num_lock',
					title : '信审每日同时审核单数上限',
					data : [{text:'5',value:'5'},{text:'6',value:'6'},{text:'7',value:'7'},{text:'8',value:'8'},{text:'9',value:'9'},{text:'10',value:'10'}]
				},
				{
					name : 'leshi_pay_type',
					title : '乐视放款类型',
					data : [{text:'全部',value:1},{text:'单期',value:2},{text:'分期',value:3}]
				}
			];
			var param = {
				id : id,
				key: key,
				data: list
			};

			var myTemplate = Handlebars.compile($("#tpl_configForm")
					.html());
			var html = myTemplate(param);
			layer.open({
				type : 1,
				title : "修改配置",
				area : [ '25%', '50%' ], //宽高
				content : html
			})

		};
		function updateBasicConfig() {
			var id = $("#configForm").find("#id").val();
			var value = $("#configForm").find("#value").val();
			if(value==""){
				alert("请选择配置项");
				return ;
			}
			var param = {
					id : id,
					value : value,
				};
				$.ajax({
					type : "post",
					url : "${ctx}/sys/config/updateBasicConfig",
					data : param,
					dataType : "json",
					error : function(request) {
						alert("系统繁忙,请稍后再试");
					},
					success : function(data) {
						if (data.code == "1") {
							location.reload(true);
						} else {
							alert(data.msg)
						}

					},

				});
		}
	</script>
</body>
</html>