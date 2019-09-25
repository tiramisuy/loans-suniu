<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>
		当天还款
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

	<c:set var="channelList" value="${fns:getChannelList()}" />
	<c:set var="companyList" value="${fns:getCompanyList()}" />
	<c:set var="productList" value="${fns:getProductList()}" />

	<ul class="nav nav-tabs">
		<li class="active">
				<a href="${ctx}/loan/repay/todayRepayList">当天还款</a>
		</li>


	</ul>
	<!--BEGIN TITLE & BREADCRUMB PAGE-->
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="pull-left">
			<ol class="breadcrumb page-breadcrumb">
				<li><i class="fa fa-home"></i>&nbsp;<a href="#">贷后管理</a></li>
				<li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">还款管理</a></li>
				<li class="active">&nbsp;<i class="fa fa-angle-right"></i>
						&nbsp;当天还款
				</li>
			</ol>
		</div>
		<div class="clearfix"></div>
	</div>
	<!--END TITLE & BREADCRUMB PAGE-->

	<form:form id="searchForm" modelAttribute="repayOP"
		action="${ctx}/loan/repay/todayRepayList" method="post"
		class="breadcrumb form-search">
		
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			
		
			<li><label>姓名</label> <form:input path="userName"
					htmlEscape="false" maxlength="20" class="input-medium" /></li>
			<li><label>手机号码</label> <form:input path="mobile"
					htmlEscape="false" maxlength="11" class="input-medium" /></li>
		
		
			<li><label>订单状态</label> <form:select path="status"
					class="input-medium">
					<form:option value="">全部</form:option>
					<form:option value="0">待还款</form:option>
					<form:option value="1">已还款</form:option>
					<form:option value="3">处理中</form:option>
				</form:select></li>
			
		
			<li><label>申请期限</label>
	            <form:select path="termType" class="input-medium">
	           		<form:option value="">全部</form:option>
	                <form:option value="1">14天</form:option>
	                <form:option value="2">3个月</form:option>
	                <form:option value="3">6个月</form:option>
	                <form:option value="4">28天</form:option>
	                <form:option value="5">其他</form:option>
	            </form:select>
	        </li>
		
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />

	<!-- 工具栏 -->
<%-- 	<c:if test="${fns:haveRole('export') == true}">
		<div class="row">
			<div class="col-sm-12 pull-left">
				<!-- 导出按钮 -->
				<button id="btnExport" class="btn btn-white btn-sm" id="btnImport"
					data-toggle="tooltip" data-placement="left"
					onclick="return exportExcel('${ctx}/loan/repay/export','${ctx}/loan/repay/detailList');"
					onclick="return validateExport()">
					<i class="fa fa-file-excel-o"></i> 导出
				</button>
				
			</div>
		</div>
	</c:if> --%>

	<table class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>借款人姓名</th>
				<th>手机号码</th>
				<th>借款时间</th>
				<th>借款本金（元）</th>
				<th>借款期限</th>
				<th>期数</th>
				<th>应还本息（元）</th>
				<th>应还日期</th>
				<th>审核人</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="item" varStatus="xh">
				<tr> 
					<td>${xh.count}</td>
					<td>${item.userName}</td>
					<td>${item.mobile}</td>
					<td><fmt:formatDate value="${item.approveTime}" pattern="yyyy-MM-dd"/></td>
					<td>${item.approveAmt}</td>
					<td>${item.applyTerm}天</td>
					<td>${item.thisTerm}/${item.totalTerm}</td>
					<td>${item.totalAmount}</td>
				 	<td><fmt:formatDate value="${item.repayDate}" pattern="yyyy-MM-dd"/></td>
				 	<td>${item.approverName}</td>
				 	<td>
				 		<c:if test="${item.status ==1}">
						<div class="text-blue">已还款</div>
						</c:if> <c:if test="${item.status ==0}">
							<div class="required">待还款</div>
						</c:if> <c:if test="${item.status ==3}">
							<div class="required">处理中</div>
						</c:if>
				 	</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="pagination">${page}</div>


	<script>
		function validateExport() {
			return exportExcel('${ctx}/loan/repay/exportToday',
					'${ctx}/loan/repay/todayRepayList');
		};

		
		
		
		function contentTable(contNo) {
			var param = {
				contNo : contNo
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/repay/repayPlanItemList",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						var myTemplate = Handlebars
								.compile($("#tpl_creditList").html());
						var html = myTemplate(data.data);
						layer.open({
							type : 1,
							title : '还款计划',
							area : [ '95%', '50%' ], //宽高
							content : html
						})
					} else {
						alert(data.msg)
					}

				},

			});
		};

		function withhold(itemId) {
			var param = {
				itemId : itemId
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/withhold/withhold",
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

		function processAdminDelay(repayPlanItemId) {
			layer.confirm('您确定延期吗？', {
				btn : [ '提交', '取消' ]
			}, function(index) {
				processAdminDelayConfirm(repayPlanItemId);
				layer.close(index);
			});
		}
		function processAdminDelayConfirm(repayPlanItemId) {
			var param = {
				repayPlanItemId : repayPlanItemId
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/repay/processAdminDelay",
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
		function processAdminWithholdQuery(repayPlanItemId, type, title) {
			var param = {
				repayPlanItemId : repayPlanItemId,
				actualRepayTime : "",
				type : type
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/withhold/processAdminWithholdQuery",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						var myTemplate = Handlebars.compile($("#tpl_repayForm")
								.html());
						var html = myTemplate(data.data);
						layer.open({
							type : 1,
							title : title,
							area : [ '30%', '50%' ], //宽高
							content : html
						})
					} else {
						alert(data.msg)
					}

				},

			});
		};
		function processAdminWithhold() {
			var repayPlanItemId = $("#repayForm").find("#repayPlanItemId")
					.val();
			var type = $("#repayForm").find("#type").val();
			var totalAmount = $("#repayForm").find("#totalAmount").val();
			var currRepayAmt = $("#repayForm").find("#currRepayAmt").val();
			var actualRepayAmt = $("#repayForm").find("#actualRepayAmt").val();
			var actualRepayTime = $("#repayForm").find("#actualRepayTime")
					.val();
			var repayType = $("#repayForm").find("#repayType").val();
			var repayTypeName = $("#repayForm").find("#repayType option:selected").text();
			var prepayFee = "";
			var deductionAmt = "";
			var regAmt = /^\d{1,7}(\.\d{1,2})?$/;
			if (type == 3) {
				prepayFee = $("#repayForm").find("#prepayFee").val();
				deductionAmt = $("#repayForm").find("#deductionAmt").val();
				if (prepayFee == '') {
					layer.alert('请填写提前还款手续费', {
						icon : 7
					});
					return;
				}
				if (!regAmt.test(prepayFee)) {
					layer.alert('请填写正确的提前还款手续费', {
						icon : 7
					});
					return;
				}
				if (deductionAmt == '') {
					layer.alert('请填写减免金额', {
						icon : 7
					});
					return;
				}
				if (!regAmt.test(deductionAmt)) {
					layer.alert('请填写正确的减免金额', {
						icon : 7
					});
					return;
				}
			}
			if (actualRepayAmt == '') {
				layer.alert('请填写实际还款金额', {
					icon : 7
				});
				return;
			}
			if (!regAmt.test(actualRepayAmt)) {
				layer.alert('请填写正确的还款金额', {
					icon : 7
				});
				return;
			}
			if (type == 2 && actualRepayAmt == '0') {
				layer.alert('请填写正确的还款金额', {
					icon : 7
				});
				return;
			}
			if (actualRepayTime == '') {
				layer.alert('请选择实际还款时间', {
					icon : 7
				});
				return;
			}
			if (repayType == '') {
				layer.alert('请选择还款渠道', {
					icon : 7
				});
				return;
			}
			if ((type == 1 || type == 3) && currRepayAmt > 0) {
				var bisicRate = 0.99;
				if (type == 3) {
					bisicRate = 1;
				}
				var rate = actualRepayAmt / currRepayAmt;
				if (rate < bisicRate) {
					layer.alert('实际还款金额/剩余应还金额必须>=' + bisicRate, {
						icon : 7
					});
					return;
				}
			}
			var param = {
				repayPlanItemId : repayPlanItemId,
				actualRepayAmt : actualRepayAmt,
				actualRepayTime : actualRepayTime,
				prepayFee : prepayFee,
				type : type,
				deductionAmt : deductionAmt,
				repayType : repayType,
				repayTypeName : repayTypeName
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/withhold/processAdminWithhold",
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
		}

		function restAmount() {
			var type = $("#repayForm").find("#type").val();
			var param = {
				repayPlanItemId : $("#repayForm").find("#repayPlanItemId")
						.val(),
				actualRepayTime : $("#repayForm").find("#actualRepayTime")
						.val(),
				type : type
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/withhold/processAdminWithholdQuery",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						$("#repayForm").find("#totalAmount").val(
								data.data.totalAmount);
						$("#repayForm").find("#currRepayAmt").val(
								data.data.currRepayAmt);
						if (type == 3) {
							// 重新计算金额 加提前手续费
							var prepayFee = $("#repayForm").find("#prepayFee")
									.val();
							if (prepayFee == "") {
								prepayFee = "0";
							}
							// 重新计算金额 减去减免金额
							var deductionAmt = $("#repayForm").find("#deductionAmt")
							.val();
							if (deductionAmt == "") {
								deductionAmt == "0";
							}
								
							$("#repayForm").find("#totalAmount").val(
									Number(data.data.totalAmount)
											+ Number(prepayFee) - Number(deductionAmt));
							$("#repayForm").find("#currRepayAmt").val(
									Number(data.data.currRepayAmt)
											+ Number(prepayFee) - Number(deductionAmt));
						}
					} else {
						alert(data.msg)
					}

				},

			});
		}

		function delayAmount() {
			var type = $("#delayForm").find("#type").val();
			var param = {
				repayPlanItemId : $("#delayForm").find("#repayPlanItemId")
						.val(),
				delayDate : $("#delayForm").find("#delayTime").val(),
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/repay/getDelayAmount",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						$("#delayForm").find("#delayAmt").val(
								data.data.delayAmt);
					} else {
						alert(data.msg)
					}

				},

			});
		}

		function delayDeal() {
			var repayPlanItemId = $("#delayForm").find("#repayPlanItemId")
					.val();
			var delayAmt = $("#delayForm").find("#delayAmt").val();
			var actualRepayAmt = $("#delayForm").find("#actualRepayAmt").val();
			var delayDate = $("#delayForm").find("#delayTime").val();
			
			var repayType = $("#delayForm").find("#repayType").val();
			var repayTypeName = $("#delayForm").find("#repayType option:selected").text();
			if (delayAmt != actualRepayAmt) {
				layer.alert('延期金额不对', {
					icon : 7
				});
				return;
			}
			if (delayDate == '') {
				layer.alert('请选择实际延期时间', {
					icon : 7
				});
				return;
			}
			if (repayType == '') {
				layer.alert('请选择支付渠道', {
					icon : 7
				});
				return;
			}

			var param = {
				repayPlanItemId : repayPlanItemId,
				delayDate : delayDate,
				repayType : repayType,
				repayTypeName : repayTypeName
			};

			var mess = "您确定通过手动延期吗？";
			top.layer.confirm(mess, {
				icon : 3,
				title : '系统提示'
			}, function(index) {
				//do something
				resetTip(); //loading();
				$.ajax({
					type : "post",
					url : "${ctx}/loan/repay/delayDeal",
					data : param,
					dataType : "json",
					error : function(request) {
						top.layer.alert("系统繁忙,请稍后再试", {
							icon : 2,
							title : '系统提示'
						});
					},
					success : function(data, textStatus) {
						if (data.code == "1") {
							top.layer.msg(data.msg, {
								icon : 1
							});
							location.reload(true);
						} else {
							top.layer.msg(data.msg, {
								icon : 2
							});
						}
					}
				});
				top.layer.close(index);
			});

		}
		//延期代扣 code y0524
		function delayDealWithhold() {
			var repayPlanItemId = $("#delayForm").find("#repayPlanItemId")
					.val();
			var delayAmt = $("#delayForm").find("#delayAmt").val();
			var actualRepayAmt = $("#delayForm").find("#actualRepayAmt").val();

			if (delayAmt != actualRepayAmt) {
				layer.alert('延期金额不对', {
					icon : 7
				});
				return;
			}
			//参数
			var param = {
				repayPlanItemId : repayPlanItemId,
				delayAmt : delayAmt
			};
			var mess = "您确定通过代扣延期吗？";
			top.layer.confirm(mess, {
				icon : 3,
				title : '系统提示'
			}, function(index) {
				//do something
				resetTip(); //loading();
				$.ajax({
					type : "post",
					url : "${ctx}/loan/repay/delayDealWithhold",
					data : param,
					dataType : "json",
					error : function(request) {
						top.layer.alert("系统繁忙,请稍后再试", {
							icon : 2,
							title : '系统提示'
						});
					},
					success : function(data, textStatus) {
						if (data.code == "1") {
							top.layer.msg(data.msg, {
								icon : 1
							});
							location.reload(true);
						} 
						else if (data.code == "3"){
							top.layer.msg(data.msg, {
								icon : 7
							});
						}else{
							top.layer.msg(data.msg, {
								icon : 2
							});
						}
					}
				});
				top.layer.close(index);
			});
		}

		function delayDialog(repayPlanItemId, title) {
			var param = {
				repayPlanItemId : repayPlanItemId,
				delayDate : ""
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/repay/getDelayAmount",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						var myTemplate = Handlebars.compile($("#tpl_delayForm")
								.html());
						var html = myTemplate(data.data);
						layer.open({
							type : 1,
							title : title,
							area : [ '40%', '50%' ], //宽高
							content : html
						});
					} else {
						alert(data.msg)
					}

				},

			});
		};
		
		function deductionRepayDetail(repayPlanDetailItemId, totalDetailAmount,lastDeductionDetailAmt) {
			var param = {
				repayPlanDetailItemId : repayPlanDetailItemId,
				totalDetailAmount : totalDetailAmount,
				lastDeductionDetailAmt : lastDeductionDetailAmt
			};
			
			var myTemplate = Handlebars.compile($("#tpl_deductionRepayDetailForm")
					.html());
			var html = myTemplate(param);
			layer.open({
				type : 1,
				title : "还款减免",
				area : [ '30%', '40%' ], //宽高
				content : html
			})
		};
		
		function ensureDeduction() {
			var repayPlanItemId = $("#deductionRepayDetailFrom").find("#repayPlanDetailItemId").val();
			var totalDetailAmount = $("#deductionRepayDetailFrom").find("#totalDetailAmount").val();
			var deductionDetailAmt = $("#deductionRepayDetailFrom").find("#deductionDetailAmt").val();
			var deductionReason = $("#deductionRepayDetailFrom").find("#deductionReason").val();
			
			var param = {
				repayPlanItemId : repayPlanItemId,
				deductionDetailAmt : deductionDetailAmt,
				deductionReason : deductionReason
			};
			var regAmt = /^\d{1,7}(\.\d{1,2})?$/;
			
			if (!regAmt.test(deductionDetailAmt)) {
				layer.alert('请填写正确的减免金额', {
					icon : 7
				});
				return;
			}
			
			if (Number(totalDetailAmount) < Number(deductionDetailAmt)) {
				layer.alert('减免金额大于应还金额', {
					icon : 7
				});
				return;
			}

			var mess = "您确定减免金额吗？";
			top.layer.confirm(mess, {
				icon : 3,
				title : '系统提示'
			}, function(index) {
				resetTip(); //loading();
				$.ajax({
					type : "post",
					url : "${ctx}/loan/deduction/deductionRepayDetailAmount",
					data : param,
					dataType : "json",
					error : function(request) {
						top.layer.alert("系统繁忙,请稍后再试", {
							icon : 2,
							title : '系统提示'
						});
					},
					success : function(data, textStatus) {
						if (data.code == "1") {
							top.layer.msg(data.msg, {
								icon : 1
							});
							location.reload(true);
						} else {
							top.layer.msg(data.msg, {
								icon : 2
							});
						}
					}
				});
				top.layer.close(index);
			});

		}
	</script>
</body>
</html>