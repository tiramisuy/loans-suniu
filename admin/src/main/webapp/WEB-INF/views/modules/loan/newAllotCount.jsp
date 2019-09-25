<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<meta name="decorator" content="default" />
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/applycount/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/applycount/css/jquery.date_input.pack.css" />
<script type="text/javascript"
	src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/applycount/js/jquery.date_input.pack.js"></script>
	
<style type="text/css">
.store-table {
	margin: 0 auto;
	width: 90%;
	text-align: center;
	border-top: 1px solid #cccccc;
	border-left: 1px solid #cccccc;
}

.store-table th,.store-table td {
	line-height: 40px;
	border-bottom: 1px solid #cccccc;
	border-right: 1px solid #cccccc;
}

label{
	margin-right:15px;
}

.input-medium {
    width: 15%;
    margin-right: 5%;
} 

</style>
</head>

<body>
	<c:set var="companyList" value="${fns:getCompanyList()}" />
	<c:set var="channelList" value="${fns:getChannelList()}" />
	<ul class="nav nav-tabs">
		<li class="active">审核统计</li>
	</ul>

	<!--搜索条件  s-->
	<div class="operation-search">
		<form:form id="searchForm" modelAttribute="applyOP"
			action="${ctx}/loan/apply/newAllotCount" method="post"
			class="breadcrumb form-search">
			<div class="ul-form">
				<label>产品</label>
				<form:select path="productId" class="input-medium">
	           		<form:option value="">全部</form:option>
	                <form:option value="JDQXJD">金牛分期</form:option>
	                <form:option value="JN">金牛单期</form:option>
	            </form:select>
	            
	            <label>申请期限</label>
	            <form:select path="termType" class="input-medium">
	           		<form:option value="">全部</form:option>
	                <form:option value="1">8天</form:option>
	                <form:option value="5">28天</form:option>
	            </form:select>

				<label>是否复贷</label>
				<form:select path="loanSucess" class="input-medium">
					<form:option value="">全部</form:option>
					<form:option value="yes">是</form:option>
					<form:option value="no">否</form:option>
				</form:select>
			</div>
			<br/>
			<div class="operation-search-time">
				<label>审核人</label>
				<form:select path="auditor" class="input-medium">
					<form:option value="">全部</form:option>
					<c:forEach items="${auditor}" var="detail">
						<c:choose>
							<c:when test="${applyOP.auditor == detail.id}">
								<form:option value="${detail.id}" selected="selected">${detail.name}</form:option>
							</c:when>
							<c:otherwise>
								<form:option value="${detail.id}">${detail.name}</form:option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</form:select>

				<div class="operSearch-date l">
					<label class="operSearch-tit">统计时间</label> <input type="text"
						value="${applyOP.applyStart}" id="applyStart" name="applyStart"
						readonly="true" class="operDate_picker border-radius3"
						placeholder="年/月/日"> <label>&nbsp;&nbsp;至&nbsp;&nbsp;</label>
					<input type="text" value="${applyOP.applyEnd}" id="applyEnd"
						name="applyEnd" readonly="true"
						class="operDate_picker1 border-radius3" placeholder="年/月/日">
				</div>
				<input id="channel" name="channel" type="hidden" value="${applyOP.channel}" />
				<input id="allChannel" name="allChannel" type="hidden" value="${allChannel}" />
				<input id="operSearch-btn" class="btn btn-primary" type="submit" value="查询"/>
				<input id="checkStart" name="checkStart" type="hidden"
					value="${applyOP.checkStart}" />
			</div>
			<div class="operation-search-channel">
				<label class="operSearch-tit l">渠道</label>
				<div class="operSearch-channel">
					<a id="operChannel-all" href="javascript:void(0);"
					   value="${allChannel}">全部</a>
					<c:forEach items="${channels}" var="ch" varStatus="xh">
						<a id="channel_${xh.index}" href="javascript:void(0);"
						   value="'${ch.cid}'">${ch.cName}</a>
					</c:forEach>
					<div class="clear"></div>

				</div>
				<div class="clear"></div>
			</div>
		</form:form>
	</div>
	<!--搜索条件  e-->

	<table class="store-table">
		<colgroup>
			<col width="200">
			<col width="200">
			<col width="200">
			<col width="150">
			<col width="150">
			<col width="150">
		</colgroup>
		<thead>
			<tr style="color: #000000">
				<th>信审人</th>
				<th>已处理订单</th>
				<th>已通过订单</th>
				<th>已拒绝订单</th>
				<th>已提现放款订单</th>
				<th>提现放款率</th>
				<th>审核通过率</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="auditor" varStatus="xh">
				<c:if test="${auditor.total != 0}">
					<tr style="color: #000000; text-align: center;">
						<td>${auditor.AuditorName}</td>
						<td>${auditor.total}</td>
						<td>${auditor.apprv}</td>
						<td>${auditor.notapprv}</td>
						<td>${auditor.withdrawrvCount}</td>
						<td>${auditor.withdrawrvCountTotal}</td>
						<td>${auditor.accessRate}</td>
					</tr>
				</c:if>
			</c:forEach>
			<tr style="color: #000000; text-align: center;">
				<td colspan="1">合计</td>
				<td>${primaryResultTotal.sumTotal}</td>
				<td>${primaryResultTotal.apprvTotal}</td>
				<td>${primaryResultTotal.notapprvTotal}</td>
				<td>${primaryResultTotal.withdrawrv}</td>
				<td>${primaryResultTotal.withdrawrvTotal}</td>
				<td>${primaryResultTotal.accessRateTotal}</td>
			</tr>
		</tbody>
	</table>

	<script type="text/javascript">
		$(function() {

			//	统计时间插件
			jQuery.browser = {};
			(function() {
				jQuery.browser.msie = false;
				jQuery.browser.version = 0;
				if (navigator.userAgent.match(/MSIE ([0-9]+)./)) {
					jQuery.browser.msie = true;
					jQuery.browser.version = RegExp.$1;
				}
			})();
			$('.operDate_picker').date_input();
			$('.operDate_picker1').date_input();

			$(".selectable_day").click(function() {
				$(".operSearch-time a").removeClass("operTime-cur");
			});

			//  搜索条件选中/未选中
			$(".operSearch-time a").click(function() {
				$(".operDate_picker").val("");
				$(".operDate_picker1").val("");
				$(".operSearch-time a").removeClass("operTime-cur");
				$(this).toggleClass("operTime-cur");
			});

			$(".operSearch-channel a").click(function() {
				$("#operChannel-all").removeClass("operChannel-cur");
				$(this).toggleClass("operChannel-cur");
			});
			$("#operChannel-all").click(function() {
				$(this).siblings().removeClass("operChannel-cur");
			});


			var applyStart = $("#applyStart").val();
			if(applyStart !=null){
				$("#checkStart").val('');
			}

			//选中时间按钮
			var checkStart = $("#checkStart").val();
			if (!checkStart) {
			} else if (checkStart == '0') {
				$('#time1').addClass('operTime-cur');
			} else if (checkStart == '-1') {
				$('#time2').addClass('operTime-cur');
			} else if (checkStart == '-7') {
				$('#time3').addClass('operTime-cur');
			} else if (checkStart == '-30') {
				$('#time4').addClass('operTime-cur');
			}

			//选中渠道按钮
			var channel = $("#channel").val();
			var allChannel = $("#allChannel").val();
			if (channel) {
				var strs = new Array(); //定义一数组
				strs = channel.split(","); //字符分割

				var allchannelstrs = new Array(); //定义一数组
				allchannelstrs = allChannel.split(","); //字符分割
				if (allChannel == channel) {
					$('#operChannel-all').addClass('operChannel-cur');
				} else {
					for (var i = 0; i < strs.length; i++) {
						for (var j = 0; j < allchannelstrs.length; j++) {
							if (strs[i] == allchannelstrs[j]) {
								$('#channel_' + j).addClass('operChannel-cur');
							}
						}

					}
				}
			}
		});

		//按条件搜索
		function operSearch() {
			var post = "";
			var Channel = "";
			var arr = new Array();

			$(".operChannel-cur").each(function(k, v) {
				Channel += $(this).attr("value") + ","
			})
			Channel = Channel.substring(0, Channel.length - 1);

			arr.push("applyStart=" + $(".operDate_picker").val());
			arr.push("&applyEnd=" + $(".operDate_picker1").val());
			arr.push("&checkStart=" + $(".operTime-cur").attr("val") + "");
			arr.push("&channel=" + Channel);
			post += arr.join("");
			// 	alert($(".operTime-cur").attr("val"));

			$("#applyStart").attr("value", $(".operDate_picker").val());
			$("#applyEnd").attr("value", $(".operDate_picker1").val());
			$("#checkStart").attr("value", $(".operTime-cur").attr("val"));
			$("#channel").attr("value", Channel);

		}

		$("#operSearch-btn").click(function() {
			operSearch();
			$("#searchForm").submit();
			return false;
		});
	</script>

</body>
</html>