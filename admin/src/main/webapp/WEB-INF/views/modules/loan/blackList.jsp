<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<title>黑名单</title>
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
                <a href="${ctx}/loan/repay/pressList?status=0">黑名单</a>
    	</li>
	</ul>
	
	<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
		<div class="pull-left">
			<ol class="breadcrumb page-breadcrumb">
				<li><i class="fa fa-home"></i>&nbsp;<a href="#">贷后管理</a></li>
				<li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">催收管理</a></li>
				 <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;黑名单</li>
			</ol>
		</div>
		<div class="clearfix"></div>
	</div>
	<!--END TITLE & BREADCRUMB PAGE-->

	<form:form id="searchForm" modelAttribute="BlacklistOP" action="${ctx}/loan/collection/blackList" method="post" class="breadcrumb form-search layui-form">

		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		
		 <ul class="ul-form">
			        <li><label>姓名</label>
			        	<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			        </li>
			        <li><label>手机号</label>
			        <form:input path="mobile" htmlEscape="false" maxlength="20" class="input-medium"/>
			        </li>
			        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
   		 </ul>

	</form:form>

	<table id="contentTable" class="table">
		<thead>
			<tr>
				<th>用户名</th>
				<th>证件号码</th>
				<th>手机号码</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="item" varStatus="xh">
				<td>${item.name}</td>
				<td>${item.idNo}</td>
				<td>${item.mobile}</td>
				<td>
					<a  href="${ctx}/loan/apply/checkFrom?id=${item.userId}&sign=detail&applyId=${item.extendInfo}">详情</a>
					<a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="contentBlackListTable('${item.id}','${item.userId }','${ctx}')" style="padding-left: 5px;">审核</a>
				</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

	<script id="tpl_creditList" type="text/x-handlebars-template">
    <jsp:include page="repayList.jsp"></jsp:include>
</script>

	<script id="tpl_creditList_2" type="text/x-handlebars-template">
    <jsp:include page="userInfo.jsp"></jsp:include>
</script>

	<script id="tpl_creditList_3" type="text/x-handlebars-template">
    <jsp:include page="deductionForm.jsp"></jsp:include>
</script>

	<%--拉入黑名单--%>
	<script id="tpl_creditList_4" type="text/x-handlebars-template">
    <jsp:include page="pressBlackForm.jsp"></jsp:include>
	</script>
	<%--审核黑名单--%>
	<script id="blackList_detail" type="text/x-handlebars-template">
    <jsp:include page="blackListReview.jsp"></jsp:include>
</script>



	<script id="tpl_detail" type="text/x-handlebars-template">
    <jsp:include page="allotmentList.jsp"></jsp:include>
</script>
	<script id="tpl_assignment" type="text/x-handlebars-template">
    <jsp:include page="collectionAssignment.jsp"></jsp:include>
</script>
	<script id="tpl_record" type="text/x-handlebars-template">
    <%--催收记录--%>
    <jsp:include page="collectionRecord.jsp"></jsp:include>
</script>

	<script>
		function assignment() {
			var list = [];
			var param = {
				list : list
			};

			if (!$(".check").is(":checked")) {
				alert("请勾选选项");
				return false;
			} else {
				$.each($(".check"), function() {
					if ($(this).is(":checked")) {
						var obj = {
							id : "",
							username : "",
							operatorName : ""
						};
						obj.id = $(this).attr("value");
						obj.username = $(this).attr("username");
						obj.operatorName = $(this).attr("operatorName");
						list.push(obj);
					}
				});
				var myTemplate = Handlebars
						.compile($("#tpl_assignment").html());
				var html = myTemplate(param);
				layer.open({
					type : 1,
					title : '催收分配',
					area : [ '60%', '80%' ], //宽高
					content : html
				})
				$.ajax({
					type : "post",
					url : "${ctx}/loan/collection/getOperator",
					data : "",
					dataType : "json",
					error : function(request) {
						alert("系统繁忙,请稍后再试");
					},
					success : function(data, textStatus) {
						if (data.code == "1") {
							var collectionsSelecter = $("#collections-name");
							var str = "";
							for (var i = 0; i < data.data.length; i++) {
								str += '<option value="'+data.data[i].id+'">'
										+ data.data[i].name + '</option>';
							}
							collectionsSelecter.append(str);
						} else {
							alert(data.msg)
						}

					},

				});
			}
		};

		function collectionBot1() {
			if ($("#collections-name").val() == "--请选择--") {
				alert("请选择分配后的催收人");
				return false;
			}
			if ($("#collections-time").val() == "--请选择--") {
				alert("请选择分配后退回时间");
				return false;
			}
			var arrId = "";//存储更改催收人的时候  借款人的id
			$.each($(".collections-con input"), function(i) {
				if ($(this).is(":checked")) {
					arrId += $(this).attr("data-id") + "|";
				}
			});
			console.log(arrId)
			var datas = {
				ids : arrId,
				operatorId : $("#collections-name").val(), //重新分配后的催收人id
				time : $("#collections-time").val()
			//重新分配时间的id
			}
			$.ajax({
				url : '${ctx}/loan/collection/doAllotment',
				type : 'post',
				data : datas,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						layer.close(layer.index);
						location.reload(true);
					} else {
						alert(data.msg)
					}

				},
			});
		};

		function detail(itemId) {
			var param = {
				itemId : itemId
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/collection/assignmentDetail",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						var myTemplate = Handlebars.compile($("#tpl_detail")
								.html());
						var html = myTemplate(data.data);
						layer.open({
							type : 1,
							title : '催收分配',
							area : [ '95%', '50%' ], //宽高
							content : html
						})
					} else {
						alert(data.msg)
					}

				},

			});
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

		function contentTable_2(id, applyId, sign) {
			var param = {
				id : id,
				applyId : applyId,
				sign : sign
			};
			$.ajax({
				type : "post",
				url : "${ctx}/sys/custUser/userDetail",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						var myTemplate = Handlebars.compile($(
								"#tpl_creditList_2").html());
						var html = myTemplate(data.data);
						layer.open({
							type : 1,
							title : '客户信息',
							area : [ '95%', '80%' ], //宽高
							content : html
						})
					} else {
						alert(data.msg)
					}

				},

			});
		};

		function contentTable_3(id, ctx, source) {
			var param = {
				id : id,
				ctx : ctx,
				source : source
			};
			$.ajax({
				type : "get",
				url : "${ctx}/loan/deduction/deductionFrom",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						var myTemplate = Handlebars.compile($(
								"#tpl_creditList_3").html());
						var html = myTemplate(data.data);
						layer.open({
							type : 1,
							title : '逾期记录',
							area : [ '95%', '80%' ], //宽高
							content : html
						});
						$("#myform").validate();
					} else {
						alert(data.msg)
					}

				},

			});
		};

		$.validator
				.setDefaults({
					submitHandler : function() {
						if ($('#myform').valid()) {
							$
									.ajax({
										cache : true,
										type : "POST",
										url : "${ctx}/loan/deduction/apply",
										data : $('#myform').serialize(),
										async : false,
										error : function(request) {
											alert("系统繁忙,请稍后再试");
										},
										success : function(data) {
											if (data.code == "1") {
												alert("提交成功");
												window.location.href = "${ctx}/loan/collection/pressList?status=0";
											} else {
												alert(data.msg)
											}
										}
									});
						}
						;
					}
				});

		function contentTable_4(itemId, userId, applyId, page) {
			var param = {
				itemId : itemId,
				userId : userId,
				applyId : applyId,
				page : page
			};
			$.ajax({
				type : "get",
				url : "${ctx}/loan/collection/pressBlackFrom",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						var myTemplate = Handlebars.compile($(
								"#tpl_creditList_4").html());
						var html = myTemplate(data.data);
						layer.open({
							type : 1,
							title : '拉入黑名单',
							area : [ '95%', '80%' ], //宽高
							content : html
						});
						$("#myBlackForm").validate();
					} else {
						alert(data.msg)
					}

				},

			});
		};

		$.validator
				.setDefaults({
					submitHandler : function() {
						if ($('#myBlackForm').valid()) {
							$
									.ajax({
										cache : true,
										type : "POST",
										url : "${ctx}/loan/collection/saveBlackForm",
										data : $('#myBlackForm').serialize(),
										async : false,
										error : function(request) {
											alert("系统繁忙,请稍后再试");
										},
										success : function(data) {
											if (data.code == "1") {
												alert("提交成功");
												window.location.href = "${ctx}/loan/collection/pressList?status=0";
											} else {
												alert(data.msg)
											}
										}
									});
						}
						;
					}
				});

		function contentBlackListTable(id, userId,ctx) {
			var param = {
				id : id,
				userId : userId
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/collection/blackListReview",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						var myTemplate = Handlebars.compile($(
								"#blackList_detail").html());
						var html = myTemplate(data.data);
						layer.open({
							type : 1,
							title : '黑名单审核',
							area : [ '95%', '50%' ], //宽高
							content : html
						})
						$("#blackListReview").validate();
					} else {
						alert(data.msg)
					}

				},

			});
		};

		$.validator
				.setDefaults({
					submitHandler : function() {
						if ($('#blackListReview').valid()) {
							$.ajax({
										cache : true,
										type : "POST",
										url : "${ctx}/loan/collection/checkBlackList",
										data : $('#blackListReview').serialize(),
										async : false,
										error : function(request) {
											alert("系统繁忙,请稍后再试");
										},
										success : function(data) {
											if (data.code == "1") {
												alert("提交成功");
												window.location.href = "${ctx}/loan/collection/blackList?status=0";
											} else {
												alert(data.msg)
											}
										}
									});
						}
						;
					}
				});

		function approve(id, repayPlanItemId, status) {
			var param = {
				id : id,
				repayPlanItemId : repayPlanItemId,
				status : status
			};

			$
					.ajax({
						cache : true,
						type : "POST",
						url : "${ctx}/loan/deduction/approve",
						data : param,
						async : false,
						error : function(request) {
							alert("系统繁忙,请稍后再试");
						},
						success : function(data) {
							if (data.code == "1") {
								window.location.href = "${ctx}/loan/collection/pressList?status=0";
							} else {
								alert(data.msg)
							}
						}
					});
		};

		function record(itemId) {
			var param = {
				itemId : itemId
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/collection/list",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						var myTemplate = Handlebars.compile($("#tpl_record")
								.html());
						var html = myTemplate(data.data);
						layer.open({
							type : 1,
							title : '催收记录',
							area : [ '95%', '80%' ], //宽高
							content : html
						});
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