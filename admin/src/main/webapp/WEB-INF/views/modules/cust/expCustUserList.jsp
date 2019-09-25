<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<title>导出客户</title>
<meta name="decorator" content="default" />

<link rel="stylesheet"
	href="${ctxStatic}/layui-master/build/css/layui.css">
<link href="${ctxStatic}/position/basic.css" rel="stylesheet" />
<link href="${ctxStatic}/position/hjd-sh.css" rel="stylesheet" />

<%--<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>--%>
<script src="${ctxStatic}/layui-master/src/layui.js"></script>
<script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>

<script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.metadata.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.js"
	type="text/javascript"></script>
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
		<li class="active"><a
			href="${ctx}/sys/expCustUser/expLoanApplyList">导出客户</a></li>
	</ul>

	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="pull-left">
			<ol class="breadcrumb page-breadcrumb">
				<li><i class="fa fa-home"></i>&nbsp;<a href="#">客户管理</a></li>
				<!-- 
				<li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">催收管理</a></li> -->
				<li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;导出客户
				</li>
			</ol>
		</div>
		<div class="clearfix"></div>
	</div>
	<!--END TITLE & BREADCRUMB PAGE-->

	<form:form id="searchForm" modelAttribute="expCustuserOP"
		action="${ctx}/sys/expCustUser/expLoanApplyList" method="post"
		class="breadcrumb form-search layui-form">

		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
	
		<ul class="ul-form">
			<li><label>姓名</label> <form:input path="userName"
					htmlEscape="false" maxlength="20" class="input-medium" /></li>
			<li><label>手机号 </label> <form:input path="mobile"
					htmlEscape="false" maxlength="20" class="input-medium" /></li>
			 <li><label>渠道</label>
            <form:select path="channelName" class="input-medium">
           		<form:option value="">全部</form:option>
                <c:forEach items="${channel}" var="detail">
                      <form:option value="${detail.cid}">${detail.cName}</form:option>
                </c:forEach>
            </form:select>
        </li>
					
			 <li class="time_li"><label>时间</label>
                <form:input id='checkStart' path="checkStart" type="text" readonly="readonly" maxlength="20"
                            class="input-middle Wdate" value="${checkStart}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
            
                <h>-</h>
                <form:input id='checkEnd' path="checkEnd" type="text" readonly="readonly" maxlength="20" class="input-middle Wdate"
                            value="${checkEnd}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
            </li>
					
			
			<li><label>导出类型</label>
	            <form:select path="approveResult" class="input-medium">
	                <form:option value="0">未买加急券</form:option>
	                <form:option value="1">注册未申请</form:option>
	                <form:option value="2">被拒</form:option>
	                 <form:option value="3">未绑卡</form:option>
	                <form:option value="4">已还未复贷</form:option>
	            </form:select>
	        </li>
	        
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li>
		</ul>

	</form:form>

<!-- 	<div class="row">
		<div class="col-sm-12 pull-left">
			导出按钮
			<button id="btnExport" class="btn btn-white btn-sm" 
				data-toggle="tooltip" data-placement="left"
				onclick="return expCustUser();">
				<i class="fa fa-file-excel-o"></i> 导出
			</button>
		</div>
	</div> -->

	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>用户名</th>
				<th>手机号码</th>
				
			<c:if test="${appesult == 1 or appesult == 3}">
				<th>注册时间</th>
			</c:if>
			<c:if test="${appesult == 0 or appesult == 2}">
				<th>审核时间</th>
				<th>复贷次数</th>
			</c:if>
			<c:if test="${appesult == 4 }">
				<th>还款时间</th>
			</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="item" varStatus="xh">
				<td>${item.userName}</td>
				<td>${item.mobile}</td>
				<td><fmt:formatDate value="${item.approveTime}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
						
					<c:if test="${appesult == 0 or appesult == 2}">
						<td>${item.succCount}</td>
					</c:if>			
			
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	
	<script type="text/javascript">
	
		function expCustUser(){
			
			var startTime = $("#checkStart").val();
			var endTime = $("#checkEnd").val();
			var flag1 = false;
			if ("" == startTime && "" == endTime) {
				flag1 = true;
			}
			;
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
		
			if ((endTimestamp - startTimestamp) > 31 * 24 * 60 * 60 * 1000) {
				layer.alert('时间段最长为一个月', {
					icon : 7
				});
				return false;
			}
			return exportExcel('${ctx}/sys/expCustUser/exportApply','${ctx}/sys/expCustUser/expLoanApplyList');
			
		}
	
	
	</script>
	
	
	
</body>
</html>