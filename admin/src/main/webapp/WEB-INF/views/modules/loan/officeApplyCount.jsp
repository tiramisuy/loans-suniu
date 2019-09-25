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

.form-search .ul-form li {
    width: 50%;
}

.form-search .ul-form li label {
   
    margin-right: 0;
}

</style>
</head>

<body>
	<ul class="nav nav-tabs">
		<li class="active">门店统计</li>

	</ul>

	<!--搜索条件  s-->
	<div class="operation-search">
		<form:form id="searchForm" modelAttribute="applyOP"
			action="${ctx}/loan/apply/officeCount" method="post"
			class="breadcrumb form-search">
			<div class="operation-search-time">
				<ul class="ul-form">
					<li><select id="area" name="areaId" class="l"
						style="display: inline-block; margin-right: 10px;">
							<option value="">请选择区域
								</opition>
					</select> <select style="margin-right:10px;" id="office" name="officeId" class="l">
							<option value="">请选择门店
								</opition>
					</select> <select style="margin-right:10px;" id="group" name="groupId" class="l">
							<option value="">请选择组
								</opition>
					</select></li>
					<li><label>按组查询</label> <form:radiobutton path="typeCheck"
							value="allGroup" /> <label>按门店查询</label> <form:radiobutton
							path="typeCheck" value="allOffice" />
						<input style="margin-left:20px;" id="operSearch-btn" class="btn btn-primary" type="submit" value="查询"/>
					</li>
				</ul>

				<div class="operSearch-date l" style="margin-top:10px;">
					<label class="operSearch-tit">统计时间</label> <input type="text"
						value="${applyOP.applyStart}" id="applyStart" name="applyStart"
						readonly="true" class="operDate_picker border-radius3"
						placeholder="年/月/日"> <label>&nbsp;&nbsp;至&nbsp;&nbsp;</label>
					<input type="text" value="${applyOP.applyEnd}" id="applyEnd"
						name="applyEnd" readonly="true"
						class="operDate_picker1 border-radius3" placeholder="年/月/日">
				</div>

				<div class="operSearch-time l" style="margin-top:10px;">
					<a id="time1" href="javascript:void(0);" val="0">今天</a> <a
						id="time2" href="javascript:void(0);" val="-1">昨天</a> <a
						id="time3" href="javascript:void(0);" val="-6">最近7天</a> <a
						id="time4" href="javascript:void(0);" val="-29">最近30天</a> <a
						id="time5" href="javascript:void(0);" val="-364">全部</a>
				</div>

				<div class="clear"></div>
				<input id="checkStart" name="checkStart" type="hidden"
					value="${applyOP.checkStart}" />
			</div>
		</form:form>
		<input id="aId" type="hidden" value="${applyOP.areaId}" /> <input
			id="oId" type="hidden" value="${applyOP.officeId}" /> <input
			id="gId" type="hidden" value="${applyOP.groupId}" />
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
			<col width="150">
			<col width="150">
			<col width="150">
			<col width="200">
		</colgroup>
		<thead>
			<tr style="color: #000000">
				<th>区域</th>
				<th>门店</th>
				<c:if test="${applyOP.typeCheck != 'allOffice' }"><th>分组</th></c:if>
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
					<c:choose>
						<c:when test="${applyOP.typeCheck == 'allOffice' }">
							<td rowspan="${fn:length(area.areaResult)+1}">${area.area}</td>
							<c:forEach items="${area.areaResult}" var="group"
								varStatus="xh">
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
						</c:when>
						<c:otherwise>
							<c:if test="${area.size > 0}">
							<td rowspan="${area.size +1}">${area.area}</td></c:if>
							<c:forEach items="${area.areaResult}" var="office" varStatus="xh">
								<tr style="color: #000000; text-align: center;">
									<td rowspan="${fn:length(office.officeResult)+1}">${office.office}</td>
								</tr>
								<c:forEach items="${office.officeResult}" var="group"
									varStatus="xh">
									<tr style="color: #000000; text-align: center;">
										<td>${group.groupName}</td>
										<td>${group.groupResult.totalReg}</td>
										<td>${group.groupResult.totalApplyAccess}</td>
										<td>${group.groupResult.totalApplyMoney}</td>
										<td>${group.groupResult.loanPass}</td>
										<td>${group.groupResult.totalLoan}</td>
										<td>${group.groupResult.totalRepay}</td>
										<td>${group.groupResult.overDueAmt}</td>
									</tr>
								</c:forEach>
								<!--         		</tr> -->
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<script type="text/javascript">
		/**
		 * 加载区域
		 */
		function loadArea() {
			$.ajax({
				type : "get",
				url : "${ctx}/loan/apply/getAllArea",
				cache : false,
				async : true,
				dataType : "json",
				success : function(datas) {
					if (datas.data && datas.data.length > 0) {
						for (var i = 0; i < datas.data.length; i++) {
							//初始化选中
							if ($("#aId").val() == datas.data[i].id) {
								$("#area").append(
										"<option value='"+datas.data[i].id+"' selected='selected'>"
												+ datas.data[i].name + "</option>");
								loadOffice();
							} else {
								$("#area").append(
										"<option value='"+datas.data[i].id+"'>" + datas.data[i].name + "</option>");
							}
						}
					}
				},
				error : function() {
					alert("operation failed!");
				}
			});
		}

		/**
		 * 加载门店
		 */
		function loadOffice() {
			$.ajax({
				type : "get",
				url : "${ctx}/loan/apply/getOfficeByArea",
				cache : false,
				async : true,
				data : "areaId=" + $("#area option:selected").val(),
				dataType : "json",
				success : function(datas) {
					if (datas.data.length > 0) {
						$("#office").empty();
						//很无奈,此处清空之后不补全这个经成空白的了
						$("#office").append("<option value=''>请选择门店</option>");
						for (var i = 0; i < datas.data.length; i++) {
							if (datas.data[i].id == $("#oId").val()) {
								$("#office").append(
										"<option value="+datas.data[i].id+" selected='selected'>"
												+ datas.data[i].office + "</option>");
								loadGroup();
							} else {
								$("#office").append(
										"<option value="+datas.data[i].id+">" + datas.data[i].office + "</option>");
							}
						}
					}
				},
				error : function() {
					alert("operation failed!");
				}
			});
		}

		/**
		 * 加载门店下组
		 */
		function loadGroup() {
			$.ajax({
				type : "get",
				url : "${ctx}/loan/apply/getGroupByOffice",
				cache : false,
				async : true,
				data : "officeId=" + $("#office option:selected").val(),
				dataType : "json",
				success : function(datas) {
					if (datas.data.length > 0) {
						$("#group").empty();
						$("#group").append("<option value=''>请选择组</option>");
						for (var i = 0; i < datas.data.length; i++) {
							if (datas.data[i].id == $("#gId").val()) {
								$("#group").append(
										"<option value="+datas.data[i].id+" selected='selected'>"
												+ datas.data[i].groupName + "</option>");
							} else {
								$("#group").append(
										"<option value="+datas.data[i].id+">" + datas.data[i].groupName + "</option>");
							}
						}
					}
				},
				error : function() {
					alert("operation failed!");
				}
			});
		};

		$(function() {
			//页面加载便加载区域
			loadArea();
			$("#area").change(function() {
				$("#areaId").attr('value', $("#area option:selected").val());
				$("#areaName").attr('value', $("#area option:selected").text());
				if (!$("#area option:selected").val() == '') {
					loadOffice();
					$("#group").empty();
					$("#group").append("<option value=''>请选择组</option>");
				} else {
					$("#office").empty();
					$("#group").empty();
					$("#office").append("<option value=''>请选择门店</option>");
					$("#group").append("<option value=''>请选择组</option>");
				}
			});

			$("#office").change(function() {
				$("#officeId").attr('value', $("#office option:selected").val());
				if (!$("#office option:selected").val() == '') {
					loadGroup();
				} else {
					$("#group").empty();
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

		function exportData() {
			operSearch();
			return exportExcel('${ctx}/loan/apply/export', '${ctx}/loan/apply/applyCount');
		}
	</script>

</body>
</html>