<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<title>旅游券发放列表</title>
 <meta name="decorator" content="default"/>

    <link rel="stylesheet" href="${ctxStatic}/layui-master/build/css/layui.css">
    <link href="${ctxStatic}/position/basic.css" rel="stylesheet"/>
    <link href="${ctxStatic}/position/hjd-sh.css" rel="stylesheet"/>

    <%--<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>--%>
    <script src="${ctxStatic}/layui-master/src/layui.js"></script>
    <script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>

    <script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js" type="text/javascript"></script>
    <script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery-validation/1.11.1/jquery.metadata.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.js" type="text/javascript"></script>
<style type="text/css">
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

.collections-top div,.collections-con div {
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
	height: 22px;
	font-size: 12px;
}

.collections-name,.collections-time {
	position: absolute;
	left: 422px;
	top: 68px;
}

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

a {
	color: #2fa4e7;
	text-decoration: none;
}

#menu {
	font-size: 18px;
	font-weight: bold;
}

#menu li {
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
                <a href="${ctx}/loan/loanTrip/list">发放旅游券</a>
    	</li>
	</ul>
	
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="pull-left">
			<ol class="breadcrumb page-breadcrumb">
				<li><i class="fa fa-home"></i>&nbsp;<a href="#">客户管理</a></li>
				<li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">旅游券列表</a></li>
				 <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;发放旅游券</li>
			</ol>
		</div>
		<div class="clearfix"></div>
	</div>
	<!--END TITLE & BREADCRUMB PAGE-->

	<form:form id="searchForm" modelAttribute="op" action="${ctx}/loan/loanTrip/list" method="post" class="breadcrumb form-search layui-form">

		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		
		 <ul class="ul-form">
			        <li><label>借款人姓名</label>
			        	<form:input path="userName" htmlEscape="false" maxlength="20" class="input-medium"/>
			        </li>
			        <li><label>手机号</label>
			        <form:input path="mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			        </li>
			         <li class="time_li"><label>审批时间</label>
                		<form:input id='approveStart' path="approveStart" type="text" readonly="readonly" maxlength="20"
                            class="input-middle Wdate" value="${op.approveStart}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
              				  <h>-</h>
               			 <form:input id='approveEnd' path="approveEnd" type="text" readonly="readonly" maxlength="20" class="input-middle Wdate"
                            value="${op.approveEnd}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
            		</li>
   		 </ul>
   		 
   		 <ul class="ul-form">
   		 	  <li class="time_li"><label>购买时间</label>
                		<form:input id='payStart' path="payStart" type="text" readonly="readonly" maxlength="20"
                            class="input-middle Wdate" value="${op.payStart}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
              				  <h>-</h>
               			 <form:input id='payEnd' path="payEnd" type="text" readonly="readonly" maxlength="20" class="input-middle Wdate"
                            value="${op.payEnd}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
            		</li>
            	<%-- 	<li class="time_li"><label>发放时间</label>
                		<form:input id='issueStart' path="issueStart" type="text" readonly="readonly" maxlength="20"
                            class="input-middle Wdate" value="${op.issueStart}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
              				  <h>-</h>
               			 <form:input id='issueEnd' path="issueEnd" type="text" readonly="readonly" maxlength="20" class="input-middle Wdate"
                            value="${op.issueEnd}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
            		</li> --%>
            		  <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
   		 </ul>

	</form:form>

	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>用户名</th>
				<th>证件号码</th>
				<th>手机号码</th>
				<th>审批时间</th>
				<th>审批金额</th>
				<th>购买时间</th>
				<th>服务费</th>
			<!-- 	<th>发放时间</th> -->
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="item" varStatus="xh">
				<td>${item.userName}</td>
				<td>${item.idNo}</td>
				<td>${item.mobile}</td>
				<td>${item.approveTime }</td>
				<td>${item.approveAmt }</td>
				<td>${item.createTime }</td>
				<td>${item.servFee }</td>
				<%-- <td>${item.updateTime }</td> --%>
				<td>
					    <a href="javascript:void(0)" onclick="issue('${item.applyId}')" style="padding-left:5px;">发放旅游券</a>
				</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>


	<script>
	
	
	function issue(applyId) {
		layer.confirm('确认发放旅游券吗？', {
			btn : [ '提交', '取消' ]
		}, function(index) {
			issueConfirm(applyId);
			layer.close(index);
		});
	}
	function issueConfirm(applyId) {
		var param = {
			applyId : applyId
		};
		$.ajax({
			type : "post",
			url : "${ctx}/loan/loanTrip/issueTicket",
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
	
	
	

		layui.use([ 'form', 'element' ], function() {
			var $ = layui.jquery, element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块

			var form = layui.form()
		});
	</script>
</body>

</html>