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

.store-table th, .store-table td {
	line-height: 40px;
	border-bottom: 1px solid #cccccc;
	border-right: 1px solid #cccccc;
}

.form-search .ul-form li {
    width: 33%;
}

.form-search .ul-form li label {
   
    margin-right: 0;
}


</style>
</head>
<c:set var="productList" value="${fns:getProductList()}" />
<body>
	<ul class="nav nav-tabs">
		<li class="active">门店产品统计</li>

	</ul>

	<!--搜索条件  s-->
	<div class="operation-search">
		<form:form id="searchForm" modelAttribute="applyOP"
			action="${ctx}/loan/apply/officeProductCount" method="post"
			class="breadcrumb form-search">
			<div class="operation-search-time">
				<ul class="ul-form">
					<li><select id="product" name="productId" class="l"
						style="display: inline-block; margin-right: 10px;">
							<option value="">请选择产品
								</opition>
								<c:forEach items="${productList}" var="detail">
									<option value="${detail.id}">${detail.name}</option>
								</c:forEach>
					</select> <select id="office" name="officeId" class="l">
							<option value="">请选择门店
								</opition>
					</select></li>



					<div class="operSearch-date l">
						<label class="operSearch-tit">统计时间</label> <input type="text"
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
							id="time4" href="javascript:void(0);" val="-29">最近30天</a> <a
							id="time5" href="javascript:void(0);" val="-364">全部</a>
					
					<input id="operSearch-btn"
						class="btn btn-primary" type="submit" value="查询" /></li>
				</ul>
				<div class="clear"></div>
				<input id="checkStart" name="checkStart" type="hidden"
					value="${applyOP.checkStart}" />
			</div>
		</form:form>
		<input id="pId" type="hidden" value="${applyOP.productId}" /> <input
			id="oId" type="hidden" value="${applyOP.officeId}" />
	</div>
	<!--搜索条件  e-->


<c:if test="${fn:length(list) > 0 }">
	<table class="store-table">
		<colgroup>
			<col width="200">
			<col width="200">
			<col width="200">
			<col width="150">
			<col width="150">
			<col width="150">
			<col width="150">
			<col width="150">
			<col width="150">
		</colgroup>
		<thead>
			<tr style="color: #000000">
				<th>产品</th>
				<th>门店</th>
				<th>注册人数(人)</th>
				<th>审批通过(单)</th>
				<th>审批通过金额(元)</th>
				<th>放款成功(单)</th>
				<th>放款金额(元)</th>
				<th>还款金额(元)</th>
				<th>逾期金额(元)</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="area" varStatus="xh">
				<tr style="color: #000000; text-align: center;">
					<td rowspan="${fn:length(area.areaResult)+1}">${area.product}</td>
					<c:forEach items="${area.areaResult}" var="group" varStatus="xh">
						<tr style="color: #000000; text-align: center;">
							<td>${group.office}</td>
							<td>${group.officeResult.totalReg}</td>
							<td>${group.officeResult.totalApplyAccess}</td>
							<td>${group.officeResult.totalApplyMoney}</td>
							<td>${group.officeResult.loanPass}</td>
							<td>${group.officeResult.totalLoan}</td>
							<td>${group.officeResult.totalRepay}</td>
							<td>${group.officeResult.overDueAmt}</td>
						</tr>
					</c:forEach>
				</tr>
			</c:forEach>
			<tr style="color: #000000; text-align: center;">

				<td colspan="2">合计</td>
				<td>${sumTotalReg}</td>
				<td>${sumTotalApplyAccess}</td>
				<td>${sumTotalApplyMoney}</td>
				<td>${sumLoanPass}</td>
				<td>${sumTotalLoan}</td>
				<td>${sumTotalRepay}</td>
				<td>${sumOverDueAmt}</td>
			</tr>
		</tbody>
	</table>
</c:if>
	<script type="text/javascript">
		/**
		 * 加载门店
		 */
		function loadOffice() {
			$.ajax({
				type : "get",
				url : "${ctx}/loan/apply/getOfficeByProduct",
				cache : false,
				async : true,
				data : "productId=" + $("#product option:selected").val(),
				dataType : "json",
				success : function(datas) {
					if (null != datas.data && datas.data.length > 0) {
						$("#office").empty();
						//很无奈,此处清空之后不补全这个经成空白的了
						$("#office").append("<option value=''>请选择门店</option>");
						for (var i = 0; i < datas.data.length; i++) {
							if (datas.data[i].id == $("#oId").val()) {
								$("#office").append(
										"<option value="+datas.data[i].id+" selected='selected'>"
												+ datas.data[i].office + "</option>");
							} else {
								$("#office").append(
										"<option value="+datas.data[i].id+">" + datas.data[i].office + "</option>");
							}
						}
					}else{
						$("#office").empty();
						//很无奈,此处清空之后不补全这个经成空白的了
						$("#office").append("<option value=''>请选择门店</option>");
					}
				},
				error : function() {
					alert("operation failed!");
				}
			});
		}

		$(function() {
			//页面加载便加载区域
			loadOffice();
			$("#product").change(function() {
				$("#productId").attr('value', $("#product option:selected").val());
				if (!$("#product option:selected").val() == '') {
					loadOffice();
				} else {
					$("#office").empty();
					$("#office").append("<option value=''>请选择门店</option>");
				}
			});

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

			//选中时间按钮
			var checkStart = $("#checkStart").val();
			if (!checkStart) {
			} else if (checkStart == '0') {
				$('#time1').addClass('operTime-cur');
			} else if (checkStart == '-1') {
				$('#time2').addClass('operTime-cur');
			} else if (checkStart == '-6') {
				$('#time3').addClass('operTime-cur');
			} else if (checkStart == '-29') {
				$('#time4').addClass('operTime-cur');
			} else if (checkStart == '-364') {
				$('#time5').addClass('operTime-cur');
			}

		});

		//按条件搜索
		function operSearch() {
			var post = "";
			var arr = new Array();

			arr.push("applyStart=" + $(".operDate_picker").val());
			arr.push("&applyEnd=" + $(".operDate_picker1").val());
			arr.push("&checkStart=" + $(".operTime-cur").attr("val") + "");
			post += arr.join("");

			$("#applyStart").attr("value", $(".operDate_picker").val());
			$("#applyEnd").attr("value", $(".operDate_picker1").val());
			$("#checkStart").attr("value", $(".operTime-cur").attr("val"));

		}

		$("#operSearch-btn").click(function() {
			operSearch();
			$("#searchForm").submit();
			return false;
		});

	</script>

</body>
</html>