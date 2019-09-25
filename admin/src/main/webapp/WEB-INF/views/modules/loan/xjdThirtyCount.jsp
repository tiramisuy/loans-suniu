<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%@ page import="java.util.Date"%>
<%@ page import="com.rongdu.common.utils.DateUtils" %>
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
	.hide{
		display: none;
	}
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
<c:set var="nowDate">
	<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd"/>
</c:set>
<body>
	<!--搜索条件  s-->
	<div class="operation-search">
		<form:form id="searchForm" modelAttribute="applyOP"
			action="${ctx}/loan/apply/xjdThirtyCount" method="get"
			class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${thirtyRepayCount.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${thirtyRepayCount.pageSize}" />
			
			<div id="loan_sucess" class="operSearch-date l" style="margin-left:0px;" >
				<label>是否首贷</label>&nbsp;&nbsp;&nbsp;
				<form:select path="loanSucess" class="input-medium">
	           		<form:option value="">全部</form:option>
	                <form:option value="yes">首贷</form:option>
	                <form:option value="no">复贷</form:option>
	            </form:select>
			</div>
			
			<div class="operSearch-date l" style="margin-left:0px;">
	            <label>产品期限</label>&nbsp;&nbsp;&nbsp;
				<form:select path="termType" class="input-medium" onchange="change()">
					<form:option value="fore">28天</form:option>
					<form:option value="one">8天</form:option>
					<%--<form:option value="">90天</form:option>--%>
	                <%--<form:option value="ones">14天</form:option>--%>
	            </form:select>
			</div>

			<div class="operSearch-date l" style="margin-left:0px;">
	            <label>宜信等级</label>&nbsp;&nbsp;&nbsp;
				<form:select path="compositeScore" class="input-medium">
					<form:option value="">全部</form:option>
					<form:option value="A">A</form:option>
					<form:option value="B">B</form:option>
					<form:option value="C">C</form:option>
					<form:option value="D">D</form:option>
					<form:option value="E">E</form:option>
				</form:select>
			</div>

			<div class="operSearch-date l" style="margin-left:0px;">
				<label>二次机审</label>&nbsp;&nbsp;&nbsp;
				<form:select path="userCreditLine" class="input-medium">
					<form:option value="">全部</form:option>
					<form:option value="1">通过</form:option>
					<form:option value="0">拒绝</form:option>
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
					id="time3" href="javascript:void(0);" val="-7">最近8天</a> <a
					id="time4" href="javascript:void(0);" val="-29">最近30天</a>
			</div>

			<div class="operSearch-btn r">
				<a href="javascript:void(0);" id="operSearch-btn"
					class="border-radius3">搜索</a>
			</div>
			
			<input id="channel" name="channel" type="hidden"
					value="${applyOP.channel}" /> <input id="allChannel"
					name="allChannel" type="hidden" value="${allChannel}" /> <input
					id="checkStart" name="checkStart" type="hidden"
					value="${applyOP.checkStart}" />
				<div class="clear"></div>
			
		</form:form>
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
	</div>
	<!--搜索条件  e-->
	
	<!-- 现金贷还款统计 code y2316-->

	<div>
		<table class="store-table">
		<thead>
			<c:choose>
				<c:when test="${applyOP.termType == 'one' or applyOP.termType == 'ones'}">
				<tr style="color: #000000">
					<th rowspan="2">放款日期</th>
					<th rowspan="2">还款日期</th>
					<th colspan="3">新贷</th>
					<th colspan="3">复贷</th>
					<th colspan="3">总体</th>
				</tr>
					<tr style="color: #000000">
						<th >应还</th>
						<th >实还</th>
						<th >还款比例</th>
						<th >应还</th>
						<th >实还</th>
						<th >还款比例</th>
						<th >应还</th>
						<th >实还</th>
						<th >还款比例</th>
					</tr>
				</c:when>
				<c:otherwise>
				<tr style="color: #000000">
					<th>放款日期</th>
					<c:choose>
						<c:when test="${applyOP.termType == 'fore'}">
							<th>还款1/4</th>
						</c:when>
						<c:otherwise>
							<th>还款1/3</th>
						</c:otherwise>
					</c:choose>
					<th>应还笔数</th>
					<th>已还笔数</th>
					<th>已还比例</th>
					<c:choose>
						<c:when test="${applyOP.termType == 'fore'}">
							<th>还款2/4</th>
						</c:when>
						<c:otherwise>
							<th>还款2/3</th>
						</c:otherwise>
					</c:choose>
					<th>应还笔数</th>
					<th>已还笔数</th>
					<th>已还比例</th>
					<c:choose>
						<c:when test="${applyOP.termType == 'fore'}">
							<th>还款3/4</th>
						</c:when>
						<c:otherwise>
							<th>还款3/3</th>
						</c:otherwise>
					</c:choose>
					<th>应还笔数</th>
					<th>已还笔数</th>
					<th>已还比例</th>
					<c:if test="${applyOP.termType == 'fore'}">
						<th>还款4/4</th>
						<th>应还笔数</th>
						<th>已还笔数</th>
						<th>已还比例</th>
					</c:if>
				</tr>
				</c:otherwise>
			</c:choose>
		</thead>
		<tbody>
		<c:set var="nowDate" value="<%=DateUtils.getDate()%>"/>
		<c:forEach items="${thirtyRepayCount.list}" var="detail">
				<c:choose>
					<c:when test="${nowDate eq detail.loanEndDate}">
						<tr style="color:#ff0000; text-align: center;">
					</c:when>
					<c:otherwise>
						<tr style="color:#000000; text-align: center;">
					</c:otherwise>
				</c:choose>
					<c:choose>
						<c:when test="${applyOP.termType == 'one' or applyOP.termType == 'ones'}">
							<td>${detail.loanStartDate}</td>
							<td>${detail.loanEndDate}</td>
							<td>${detail.firstLoan}</td>
							<td>${detail.payedFirstLoan }</td>
							<td><fmt:formatNumber value="${detail.payedFirstLoanRate == null ? 0 :
							detail.payedFirstLoanRate*100}" pattern="0.00"/>%</td>
							<td>${detail.doubleLoan}</td>
							<td>${detail.payedDoubleLoan}</td>
							<td><fmt:formatNumber value="${detail.payedDoubleLoanRate == null ? 0 :
							detail.payedDoubleLoanRate*100}" pattern="0.00"/>%</td>
							<td>${detail.p1Total}</td>
							<td>${detail.p1Payed == null ? 0 : detail.p1Payed}</td>
							<td><fmt:formatNumber value="${detail.payedRate == null ? 0 : detail.payedRate*100}"
                                                  pattern="0.00"/>%</td>
						</c:when>
						<c:otherwise>
							<td>${detail.loanStartDate}</td>

							<td>
							<c:if test="${nowDate == detail.p1Date }">
								<font color="red"/>
							</c:if>${detail.p1Date}</td>
							<td>${detail.p1Total}</td>
							<td>${detail.p1Payed}</td>
							<td><fmt:formatNumber value="${detail.p1Rate*100}" pattern="0.00"/>%</td>
							<td>
								<c:if test="${nowDate == detail.p2Date }">
									<font color="red"/>
								</c:if>${detail.p2Date}</td>
							<td>${detail.p2Total}</td>
							<td>${detail.p2Payed}</td>
							<td><fmt:formatNumber value="${detail.p2Rate*100}" pattern="0.00"></fmt:formatNumber>%</td>
							<td>
								<c:if test="${nowDate == detail.p3Date }">
									<font color="red"/>
								</c:if>${detail.p3Date}</td>
							<td>${detail.p3Total}</td>
							<td>${detail.p3Payed}</td>
							<td><fmt:formatNumber value="${detail.p3Rate*100}" pattern="0.00"></fmt:formatNumber>%</td>
							<c:if test="${applyOP.termType == 'fore'}">
								<td>
									<c:if test="${nowDate == detail.p4Date }">
										<font color="red"/>
									</c:if>${detail.p4Date}</td>
								<td>${detail.p4Total}</td>
								<td>${detail.p4Payed}</td>
								<td><fmt:formatNumber value="${detail.p4Rate*100}" pattern="0.00"></fmt:formatNumber>%</td>
							</c:if>
						</c:otherwise>
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

            change();
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

		function change(){
		    var termType = $("#termType").val();
		    if (termType == "fore"){
                $("#loan_sucess").removeClass("hide");
            }else {
                $("#loan_sucess").addClass("hide");
                $("#loanSucess").val("");
			}
		}

		$("#operSearch-btn").click(function() {
			operSearch();
			$("#searchForm").submit();
			return false;
		});

	</script>

</body>
</html>