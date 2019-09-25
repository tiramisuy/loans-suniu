<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<c:choose>
	<c:when test="${applyOP.stage == 1}">
		<title>待审核项目</title>
	</c:when>
</c:choose>

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
	<ul class="nav nav-tabs">
		<li class="active">运营管理</li>

	</ul>

	<!--搜索条件  s-->
	<div class="operation-search">
		<form:form id="searchForm" modelAttribute="applyOP"
			action="${ctx}/loan/apply/applyCount" method="get"
			class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${repayCountPage.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${repayCountPage.pageSize}" />
			
			<div class="operation-search-time">
				<div  class="operSearch l">
				<li><label>产品</label>
		            <form:select id="productId" path="productId" class="input-medium" onchange="changekoudai()">
		           		<form:option value="">全部</form:option>
		                <form:option value="XJD">聚宝钱包</form:option>
		                <form:option value="KOUDAI">口袋放款</form:option>
		                <form:option value="XJDFQ">信用分期</form:option>
		            </form:select>
	       		 </li>
			</div>
			<div class="operSearch l">
			 	<li><label>申请期限</label>
		            <form:select path="termType" class="input-medium">
		           		<form:option value="">全部</form:option>
		                <form:option value="1">14天/15天</form:option>
		                <form:option value="5">28天</form:option>
		                <form:option value="2">3个月</form:option>
		                <form:option value="3">6个月</form:option>
		                <form:option value="4">其他</form:option>
		            </form:select>
	      		</li>
			</div>
				<div class="operSearch-date l">
					<label class="operSearch-tit">统计时间</label><input type="text"
						value="${applyOP.applyStart}" id="applyStart" name="applyStart"
						readonly="true" class="operDate_picker border-radius3"
						placeholder="年/月/日"> <label>&nbsp;&nbsp;至&nbsp;&nbsp;</label>
					<input type="text" value="${applyOP.applyEnd}" id="applyEnd"
						name="applyEnd" readonly="true"
						class="operDate_picker1 border-radius3" placeholder="年/月/日">
						<div id="koudai"  style="color: red;width: 200px;margin-left: 9px"></div> 
				</div>
					
				<div class="operSearch-time l">
					<a id="time1" href="javascript:void(0);" val="0">今天</a> <a
						id="time2" href="javascript:void(0);" val="-1">昨天</a> <a
						id="time3" href="javascript:void(0);" val="-6">最近7天</a> <a
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
			</div>
			
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

		<!--导出数据  s-->
		<div class="daochu">
			<button id="btnExport" class="btn btn-white btn-sm" id="btnImport"
				data-toggle="tooltip" data-placement="left" onclick="exportData();">
				<i class="fa fa-file-excel-o"></i> 导出
			</button>
		</div>
		<!--导出数据  e-->
	</div>
	<!--搜索条件  e-->

	<!--搜索结果  s-->
	<div class="operation-con">
		<ul>
			<li class="operCon-li2">
				<p>
					注册人数(人)：<i id="totalReg">${model.totalReg}</i>
				</p>
				<p>
					注册转化率：<i id="applyRate">${model.applyRateStr}</i>
				</p>
			</li>
			<li class="operCon-li2">
				<p>
					申请人数(人)：<i id="totalApply">${model.totalApply}</i>
				</p>
				<p>
					申请通过率：<i id="applyPassRate">${model.applyPassRateStr}</i>
				</p>
				<%--<p>--%>
					<%--现金白卡首贷通过率：<i id="applyPassRate">${xmodel.xjbkApplyPassRateStr}</i>--%>
				<%--</p>--%>
			</li>
			<li>
				<p>
					放款笔数：<i id="loanPass">${model.loanPass}</i>
				</p> <%--                 <p>放款成功率：<i id="loanPassRate">${model.loanPassRateStr}</i></p> --%>
				<p>
					首贷笔数：<i id="loanPass">${model.loanPassNew}</i>
				</p>
				<p>
					复贷笔数：<i id="loanPass">${model.loanPassOld}</i>
				</p>
				<p>
					待放款笔数：<i id="loanReady">${model.loanReady}</i>
				</p>
			</li>
			<li>
<%-- 				<p>
					申请金额(元)：<i id="totalApplyAmt"><fmt:formatNumber
							value="${model.totalApplyAmt}" pattern="#"
							type="number"></fmt:formatNumber></i>
				</p> --%>
				<p>
					放款金额(元)：<i id="totalLoan"><fmt:formatNumber
							value="${model.totalLoan}" pattern="#" type="number"></fmt:formatNumber></i>
				</p>
				<p>
					累计放款(元)：<i id="totalLoan"><fmt:formatNumber
							value="${delaySum+model.totalLoan}" pattern="#" type="number"></fmt:formatNumber></i>
				</p>
				<p>
					待放款金额(元)：<i id="totalLoanReady"><fmt:formatNumber
							value="${model.totalLoanReady}" pattern="#"
							type="number"></fmt:formatNumber></i>
				</p>
				<p>
					募集中笔数：<i id="raiseCount">${model.raiseCount}</i>
				</p>
				<p>
					募集中金额(元)：<i id="raiseAmt"><fmt:formatNumber
							value="${model.raiseAmt}" pattern="#"
							type="number"></fmt:formatNumber></i>
				</p>
				<p>
					待提现金额(元)：<i id="withdrawAmt"><fmt:formatNumber
							value="${model.withdrawAmt}" pattern="#"
							type="number"></fmt:formatNumber></i>
				</p>
			</li>
			<li>
				<c:choose>
					<c:when test="${applyOP.typeCheck == 'KOUDAI'}">
						<p>
							口袋实际放款金额(元)：<i id="payAmt">${payAmt}</i>
						</p>
					</c:when>
					<c:otherwise>
						<p>
						逾期人数(人)：<i id="overDue">${model.overDue}</i>
						</p>
						<p>
							逾期金额(元)：<i id="overDueAmt">${model.overDueAmt}</i>
						</p>
					</c:otherwise>
				</c:choose>
			</li>
		</ul>
		<div class="clear"></div>
	</div>
	<!--搜索结果  e-->
	
	<!-- 现金贷还款统计 code y2316-->
	<c:if test="${not empty repayCountPage.list }">
	<div>
		<table class="store-table">
		<thead>
			<tr style="color: #000000">
				<th>还款时间</th>
				<th>应还笔数</th>
				<th>应还金额</th>
				<th>应还本金</th>
				<th>已还笔数</th>
				<th>已还金额</th>
				<th>已还本金</th>
				<th>已还部分</th>
				<th>未还笔数</th>
				<th>未还金额</th>
				<th>展期笔数</th>
				<th>展期金额 </th>
				<th>已还比例</th>
				<th>未还比例</th>
				<th>展期比例</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${repayCountPage.list}" var="repayCountVO">
				<tr style="color: #000000; text-align: center;">
					<td>${repayCountVO.repayDate}</td>
					<td>${repayCountVO.totalNum}</td>
					<td><fmt:formatNumber value="${repayCountVO.totalAmt}" pattern="0.00"></fmt:formatNumber></td>
					<td><fmt:formatNumber value="${repayCountVO.principal}" pattern="0.00"></fmt:formatNumber></td>
					<td>${repayCountVO.payedNum}</td>
					<td><fmt:formatNumber value="${repayCountVO.payedAmt}" pattern="0.00"></fmt:formatNumber></td>
					<td><fmt:formatNumber value="${repayCountVO.payedPrincipal}" pattern="0.00"></fmt:formatNumber></td>
					<td><fmt:formatNumber value="${repayCountVO.partPayAmt}" pattern="0.00"></fmt:formatNumber></td>
					<td>${repayCountVO.unpayNum}</td>
					<td><fmt:formatNumber value="${repayCountVO.unpayAmt}" pattern="0.00"></fmt:formatNumber></td>
					
					
						<c:choose>
							<c:when test="${applyOP.productId == 'XJDFQ' }">
								<td>0</td>
								<td>0.00</td>
							</c:when>
							<c:otherwise>
								<td>${repayCountVO.delayNum}</td>
								<c:choose>
									<c:when test="${repayCountVO.delayFee == null}">
			                           <td>0.00</td>
			                        </c:when>
									<c:otherwise>
										<td>${repayCountVO.delayFee}</td>
			                        </c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					
					<td><fmt:formatNumber value="${repayCountVO.payedRate*100}" pattern="0.00"></fmt:formatNumber>%</td>
					<td><fmt:formatNumber value="${repayCountVO.unpayRate*100}" pattern="0.00"></fmt:formatNumber>%</td>
						<c:choose>
							<c:when test="${applyOP.productId == 'XJDFQ' }">
								<td>0.00%</td>
							</c:when>
							<c:otherwise>
								<td><fmt:formatNumber value="${repayCountVO.delayRate*100}" pattern="0.00"></fmt:formatNumber>%</td>
							</c:otherwise>
					</c:choose>
					
				</tr>
			</c:forEach>
		</tbody>
		</table>
	</div>
	<div class="pagination" style="width: 95%;" align="center">${repayCountPage}</div>
	</c:if>


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
			// 	alert(channel);
			// 	alert(allChannel);
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
							// 					alert(strs[i]);
							// 					alert(allchannelstrs[j]);
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

		function exportData() {
			operSearch();
			return exportExcel('${ctx}/loan/apply/export',
					'${ctx}/loan/apply/applyCount');
		}
		
		function changekoudai() {
			var objS = document.getElementById("productId");
	        var value = objS.options[objS.selectedIndex].value;
	        if ("KOUDAI" == value) {
	        	$("#koudai").text("口袋统计数据自2018-09-26开始");
			} else{
				$("#koudai").text("");
			}
		}
	</script>

</body>
</html>