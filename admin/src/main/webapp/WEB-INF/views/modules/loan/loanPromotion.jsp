<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
            <title>借款期限Tag</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        };
        function openTabPage(url) {
            if(cookie('tabmode')=='1'){
                addTabPage('借款人详情',url,null);
            }else{
                window.location.href=url;
            }
        };
    </script>
    <link rel="stylesheet" href="${ctxStatic}/layui-master/build/css/layui.css">
    <script src="${ctxStatic}/layui-master/src/layui.js"></script>
    <script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>
    <script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js" type="text/javascript"></script>
    <script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>
    <style type="text/css">
    	.l{ float: left;}
    	.r{ float: right;}
    	
        .form-search .ul-form li .error {
            width: auto !important;
        }
        #collection{
            width: 1051px;
            height: 791px;
            border: 1px solid rgba(221,221,221,1);
            position: relative;
        }
        #collections{
            height: 37px;
            line-height: 37px;
            background: rgba(242,242,242,1);
        }
        .collection{
            margin: 100px auto 0;
            width:867px;
            height:377px;
            border: 1px solid #cccccc;
            overflow: hidden;
            position: relative;
        }
        .collections{
            width:839px;
            height:330px;
            background-color:rgba(236, 246, 251, 1);
            margin-top: 23px;
            margin-left: 13px;
        }
        .clears:after{
            content: "";
            display: block;
            clear: both;
        }
        .collections-top{
            height: 36px;
            line-height: 36px;
            font-size: 14px;
            width: 820px;
            margin: 0 auto;
            border-bottom: 1px solid #cccccc;
        }
        .collections-top div{
            float: left;
            width: 200px;
        }
        .collections-con{
            width: 800px;
            height: 278px;
            overflow-y: auto;
        }
        .collections-top em{
            color: #FF0000;
        }
        .collections-con div{
            font-size: 12px;
            color: #666;
            text-indent: 10px;
            height: 40px;
            line-height: 40px;
        }
        .collections-con div input{
            margin-right: 15px;
            margin-top: 3px;
        }
        .collections-con .collections-cons{
            width: 500px;
        }
        .collection select{
            width: 152px;
            /*height: 22px;
            font-size: 12px;*/
        }
        /* .collections-name,.collections-time{
            position: absolute;
            left: 422px;
            top: 68px;
        } */
        .collections-time{
            left: 621px;
        }
        .collection-bot{
            width: 328px;
            margin: 28px auto 0;
        }
        .collection-bot-left,.collection-bot-right{
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
        .collection-bot-right{
            background: #ffffff;
            color: #333;
        }
        .collections-top .wAuto{ display:inline-block; width:auto; margin-right: 18px;}
        a{
    		color: #2fa4e7;
    		text-decoration: none;
		}
    </style>
    
</head>
<body>

<c:set var="channelList" value="${fns:getChannelList()}" />
<c:set var="companyList" value="${fns:getCompanyList()}" />
<c:set var="productList" value="${fns:getProductList()}" />
    
<ul class="nav nav-tabs">
    <li class="active">
        <a href="${ctx}/loan/promotion/list">
		借款期限Tag
        </a>
    </li>


</ul>
<!--BEGIN TITLE & BREADCRUMB PAGE-->
<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
    <div class="pull-left">
        <ol class="breadcrumb page-breadcrumb">
                    <li><i class="fa fa-home"></i>&nbsp;<a href="#">运营管理</a></li>
                    <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;贷款期限Tag</li>
        </ol>
    </div>
    <div class="clearfix"></div>
</div>
<!--END TITLE & BREADCRUMB PAGE-->

<form:form id="searchForm" modelAttribute="op" action="${ctx}/loan/promotion/list" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
       <%--  <li><label>产品</label>
            <form:select path="productId" class="input-medium">
           		<form:option value="">全部</form:option>
                <c:forEach items="${productList}" var="detail">
                     <form:option value="${detail.id}">${detail.name}</form:option>
                </c:forEach>
            </form:select>
        </li>
       	<li><label>渠道</label>
            <form:select path="channelId" class="input-medium">
           		<form:option value="">全部</form:option>
                <c:forEach items="${channelList}" var="detail">
                    <c:choose>
                        <c:when test="${borrowerOP.channel == detail.cid}">
                            <form:option value="${detail.cid}" selected="selected">${detail.cName}</form:option>
                        </c:when>
                        <c:otherwise>
                            <form:option value="${detail.cid}">${detail.cName}</form:option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </form:select>
        </li>
        
         <li class="btns"><input id="btnSubmit1" class="btn btn-primary" type="submit" value="查询"/></li>  --%>
    </ul>
</form:form>


<sys:message content="${message}"/>
<table id="contentTable" class="table">
    <colgroup>
    </colgroup>
    <thead>
    <tr>
    		<th>序号</th>
    		<th>贷款利率（年化）</th>
    		<th>服务费率</th>
    		<th>借款期限区间</th>
    		<!-- <th>专案状态</th> -->		<!--  0,停用；1，正常 -->
    		<!-- <th>贷款利率（年化）</th> -->
    		<th>最后修改时间</th>
    		<th>当前是否默认</th>
    		<th>操作</th>
    </tr>
    </thead>
    <tbody>
    
    <c:forEach items="${page.list}" var="vo" varStatus="xh">
        <tr>
        	
            <td>
                    ${xh.count}
            </td>
             <td>${vo.yearInterest}</td>
          
    		<td>${vo.servFee}</td>
    		<td>${vo.period}</td>
    	<%-- 	<td>${vo.pStatus}</td> --%>		<!--  0,停用；1，正常 -->
    		<%--  <td> ${vo.dayInterest}</td> --%>
    		<td><fmt:formatDate value="${vo.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    		<td>
	    		 <c:choose>
	    			<c:when test="${xh.count==1}">
	    				是
	    			</c:when>
	    			<c:otherwise>
	    				否
	    			</c:otherwise>
	    		</c:choose>
    		</td>
            <td>
               <a href="javascript:void(0)" onclick="resetDefault('${vo.id}','${op.productId }')" style="padding-left:5px;">设置为默认</a>
                
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>

<script id="tpl_assignment" type="text/x-handlebars-template">
    <jsp:include page="applyAssignment.jsp"></jsp:include>
</script>
<script id="loanDialog" type="text/x-handlebars-template">
    <jsp:include page="/WEB-INF/views/include/dateDialog.jsp"></jsp:include>
</script>
<script>
	
	function validateExport() {
		var startTime = $("#checkStart").val();
		var endTime = $("#checkEnd").val();
		var startTime1 = $("#applyStart").val();
		var endTime1 = $("#applyEnd").val();
		var flag1 = false;
		var flag2 = false;
		if ("" == startTime && "" == endTime) {
			flag1 = true;
		}
		;
		if ("" == startTime1 && "" == endTime1) {
			flag2 = true;
		}
		;
		if (("" == startTime && "" != endTime) || ("" != startTime && "" == endTime)) {
			layer.alert('请选择时间段', {
				icon : 7
			});
			return false;
		};
		if (("" == startTime1 && "" != endTime1) || ("" != startTime1 && "" == endTime1)) {
			layer.alert('请选择时间段', {
				icon : 7
			});
			return false;
		};
		if (flag1 && flag2) {
			layer.alert('请选择导出时间', {
				icon : 7
			});
			return false;
		}
		var startTimestamp = new Date(startTime).getTime();
		var endTimestamp = new Date(endTime).getTime();
		var startTimestamp1 = new Date(startTime1).getTime();
		var endTimestamp1 = new Date(endTime1).getTime();
		if (startTimestamp1 > endTimestamp1) {
			layer.alert('起始日期应在结束日期之前', {
				icon : 7
			});
			return false;
		}
		if (startTimestamp > endTimestamp) {
			layer.alert('起始日期应在结束日期之前', {
				icon : 7
			});
			return false;
		}
		if ((endTimestamp1 - startTimestamp1) > 31 * 24 * 60 * 60 * 1000) {
			layer.alert('时间段最长为一个月', {
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
		return exportExcel(
				'${ctx}/loan/apply/exportAuditPass?stage=${applyOP.stage}',
				'${ctx}/loan/apply/list?stage=${applyOP.stage}');
	};
	
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
						companyId : ""
					};
					obj.id = $(this).attr("value");
					obj.username = $(this).attr("username");
					obj.companyId = $(this).attr("companyId");
					list.push(obj);
				}
			});
			var myTemplate = Handlebars.compile($("#tpl_assignment").html());
			var html = myTemplate(param);
			layer.open({
				type : 1,
				title : '订单分配',
				area : [ '60%', '80%' ], //宽高
				content : html
			})
			$
					.ajax({
						type : "post",
						url : "${ctx}/loan/apply/getAllCompany",
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
									str += '<option value="'+data.data[i].companyId+'">'
											+ data.data[i].name + '</option>';
								}
								collectionsSelecter.append(str);
							} else {
								alert(data.msg);
							}

						},

					});
		}
	};

	function collectionBot1() {
		if ($("#collections-name").val() == "--请选择--") {
			alert("请选择分配后的商户");
			return false;
		}
		var arrId = "";
		$.each($(".collections-con input"), function(i) {
			if ($(this).is(":checked")) {
				arrId += $(this).attr("data-id") + "|";
			}
		});
		var datas = {
			ids : arrId,
			companyId : $("#collections-name").val(),
		}
		$.ajax({
			url : '${ctx}/loan/apply/doAllotment',
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

	function addzero(v) {
		if (v < 10)
			return '0' + v;
		return v.toString();
	}

	function popupFrame(applyId) {
		var loanTime = $("#loantime").val();
		processAdminLendPayConfirm(applyId, loanTime);
		layer.close(index);
	}

	function processAdminLendPay(applyId) {
		var d = new Date();
		var str = d.getFullYear().toString() + '-' + addzero(d.getMonth() + 1)
				+ '-' + addzero(d.getDate()) + ' ' + addzero(d.getHours())
				+ ':' + addzero(d.getMinutes()) + ':' + addzero(d.getSeconds());
		var param = {
			applyId : applyId,
			date : str
		};
		var myTemplate = Handlebars.compile($("#loanDialog").html());
		var html = myTemplate(param);
		layer.open({
			type : 1,
			title : '您确定已经放款？',
			area : [ '420px', '240px' ], //宽高
			content : html
		});
	};

	function processAdminLendPayConfirm(applyId, loanTime) {
		var param = {
			applyId : applyId,
			loanTime : loanTime
		};
		$.ajax({
			type : "post",
			url : "${ctx}/loan/withdraw/processAdminLendPay",
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

	function resetDefault(id,productId) {
		layer.confirm('您确定要设置为默认吗？', {
			btn : [ '提交', '取消' ]
		}, function(index) {
			resetCheckConfirm(id,productId);
			layer.close(index);
		});
	}
	function resetCheckConfirm(id,productId) {
		var param = {
			id : id,
			productId:productId
		};
		$.ajax({
			type : "post",
			url : "${ctx}/loan/promotion/resetDefault",
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

	function cancel(applyId) {
		layer.confirm('您确定要取消吗？', {
			btn : [ '提交', '取消' ]
		}, function(index) {
			cancelConfirm(applyId);
			layer.close(index);
		});
	}
	function cancelConfirm(applyId) {
		var param = {
			applyId : applyId
		};
		$.ajax({
			type : "post",
			url : "${ctx}/loan/applyAllot/cancel",
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
</script>
</body>
</html>