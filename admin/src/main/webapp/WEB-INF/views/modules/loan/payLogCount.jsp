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
	<!--搜索条件  s-->
	<div class="operation-search">
		<form:form id="searchForm" modelAttribute="applyOP"
			action="${ctx}/loan/apply/getPayCount" method="get"
			class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${thirtyRepayCount.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${thirtyRepayCount.pageSize}" />
			<div class="operSearch-date l" style="margin-left:0px;">
	            <label>放款渠道</label>&nbsp;&nbsp;&nbsp;
				<form:select path="status" class="input-medium">
	                <%--<form:option value="1">口袋放款</form:option>--%>
	                <%--<form:option value="2">宝付放款</form:option>--%>
	                <%--<form:option value="3">平台放款</form:option>--%>
	                <form:option value="4">通联放款</form:option>
	            </form:select>
			</div>
				        
			<div class="operSearch-date l">
				<label class="operSearch-tit">放款时间</label> <input type="text"
					value="${applyOP.applyStart}" id="applyStart" name="applyStart"
					readonly="true" class="operDate_picker border-radius3"
					placeholder="年/月/日"> <label>&nbsp;&nbsp;至&nbsp;&nbsp;</label>
				<input type="text" value="${applyOP.applyEnd}" id="applyEnd"
					name="applyEnd" readonly="true"
					class="operDate_picker1 border-radius3" placeholder="年/月/日">
			</div>
						
			<div class="operSearch-time l">
				<a id="time1" href="javascript:void(0);" val="0">今天</a> <a
					id="time2" href="javascript:void(0);" val="-1">昨天</a> <a
					id="time3" href="javascript:void(0);" val="-6">最近7天</a> <a
					id="time4" href="javascript:void(0);" val="-29">最近30天</a>
			</div>

			<li class="operSearch-btn r">
				<a href="javascript:void(0);" id="operSearch-btn"
				   class="border-radius3">搜索</a>
			</li>
			<input id="channel" name="channelId" type="hidden" value="${applyOP.channelId}" />
			<input id="allChannel" name="allChannel" type="hidden" value="${allChannel}" />
			<input id="checkStart" name="checkStart" type="hidden" value="${applyOP.checkStart}" />
			
		</form:form>
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
	<!--搜索条件  e-->
	
	<!-- 现金贷还款统计 code y2316-->

	<div>
		<table class="store-table">
		<thead>
			<tr style="color: #000000">
					<th>放款日期</th>
					<th>总放款金额</th>
					<th>总笔数</th>
			<c:choose>
				<c:when test="${applyOP.status == '1'}">
					<th>成功放款金额</th>
					<th>成功笔数</th>
					<th>其他金额(失败,取消)</th>
					<th>其他笔数(失败,取消)</th>
				</c:when>
			</c:choose>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${thirtyRepayCount.list}" var="detail">
				<tr style="color: #000000; text-align: center;">
							<td>${detail.time}</td>
							<td>${detail.allAmt}</td>
							<td>${detail.allTimes}</td>
						<c:choose>
							<c:when test="${applyOP.status == '1'}">
								<td>${detail.succAmt}</td>
								<td>${detail.succTimes}</td>
								<td>${detail.otherAmt}</td>
								<td>${detail.otherTimes}</td>
							</c:when>
						</c:choose>
				</tr>
			</c:forEach>
		</tbody>
		</table>
	</div>
	<div class="pagination" style="width: 95%;" align="center">${thirtyRepayCount}</div>



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