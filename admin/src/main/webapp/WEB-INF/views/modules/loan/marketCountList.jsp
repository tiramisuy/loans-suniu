<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
            <title>營銷统计</title>
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
        <a href="${ctx}/loan/market/countList">
	       	营销统计
        </a>
    </li>


</ul>
<!--BEGIN TITLE & BREADCRUMB PAGE-->
<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
    <div class="pull-left">
        <ol class="breadcrumb page-breadcrumb">
                    <li><i class="fa fa-home"></i>&nbsp;<a href="#">客服管理</a></li>
                    <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;营销统计</li>
        </ol>
    </div>
    <div class="clearfix"></div>
</div>
<!--END TITLE & BREADCRUMB PAGE-->

<form:form id="searchForm" modelAttribute="op" action="${ctx}/loan/market/countList" method="post"
           class="breadcrumb form-search">
    <ul class="ul-form">
      
        
        <li><label>分配人</label>
            <form:select path="userId" class="input-medium" >
                <c:if test="${fns:haveRole('kefu_dianxiao') == false}">
                    <form:option value="">全部</form:option>
                </c:if>
                <c:forEach items="${userList}" var="detail">
                    <c:choose>
                        <c:when test="${loginUser == detail.id}">
                            <form:option value="${detail.id}" selected="selected">${detail.name}</form:option>
                        </c:when>
                        <c:when test="${loginUser != detail.id}">
                            <form:option value="${detail.id}">${detail.name}</form:option>
                        </c:when>
                        <c:otherwise>

                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </form:select>
        </li>
            <li class="time_li"><label>分配时间</label>
                <form:input id='searchStrat' path="searchStrat" type="text" readonly="readonly" maxlength="20"
                            class="input-middle Wdate" value="${op.searchStrat}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
           
                <h>-</h>
                <form:input id='searchEnd' path="searchEnd" type="text" readonly="readonly" maxlength="20" class="input-middle Wdate"
                            value="${op.searchEnd}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
            </li>
		

        <li class="btns"><input id="btnSubmit1" class="btn btn-primary" type="submit" value="查询"/></li>
    </ul>
</form:form>


<sys:message content="${message}"/>







<table id="contentTable" class="table">
    <colgroup>
    </colgroup>
    <thead>
    <tr>
        <th>序号</th>
		<th>分配人</th>
        <th>总单量</th>
        <th>出单量</th>
        <th>转化率</th>
    </tr>
    </thead>
    <tbody>
    
    <c:forEach items="${voList}" var="apply" varStatus="xh">
        <tr>
            <td>
                    ${xh.count}
            </td>
             <td>
             		${apply.userName }
             </td>
            <td>
                    ${apply.allNum}
            </td>
            <td>
                    ${apply.outNum}
            </td>

            <td>
                    ${apply.outPersent}%
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>

<script id="loan_market" type="text/x-handlebars-template">
    <jsp:include page="marketForm.jsp"></jsp:include>
</script>
<script id="loan_market_result" type="text/x-handlebars-template">
    <jsp:include page="marketResult.jsp"></jsp:include>
</script>



<script id="market_allot" type="text/x-handlebars-template">
    <jsp:include page="marketAllot.jsp"></jsp:include>
</script>


<script>
		function expCustUser(){
			
			var startTime = $("#applyTimeStart").val();
			var endTime = $("#applyTimeEnd").val();
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
			return exportExcel('${ctx}/loan/market/exportMarketList','${ctx}/loan/market/list');
			
		}







function marketAllot() {
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
		var myTemplate = Handlebars.compile($("#market_allot").html());
		var html = myTemplate(param);
		layer.open({
			type : 1,
			title : '订单分配',
			area : [ '60%', '80%' ], //宽高
			content : html
		})
		$.ajax({
			type : "post",
			url : "${ctx}/loan/market/getAllCsUser",
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
						str += '<option value="'+data.data[i].id+'-'+data.data[i].name+'">'
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
		
		
		

	function warnBot1() {
		if ($("#collections-name").val() == "--选择提醒人员--") {
			alert("请选择分配的提醒人员");
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
			url : '${ctx}/loan/market/doHandAllot',
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
		















	function confimMarket(applyId, marketId,ctx) {
		var param = {
			applyId : applyId,
			marketId : marketId
		};
		$.ajax({
			type : "post",
			url : "${ctx}/loan/market/toConfimMarket",
			data : param,
			dataType : "json",
			error : function(request) {
				alert("系统繁忙,请稍后再试");
			},
			success : function(data, textStatus) {
				if (data.code == "1") {
					var myTemplate = Handlebars.compile($("#loan_market")
							.html());
					var html = myTemplate(data.data);
					layer.open({
						type : 1,
						title : '营销提醒',
						area : [ '80%', '70%' ], //宽高
						content : html
					})
				} else {
					alert(data.msg)
				}
	
			},
	
		});
	};
	
	
	
	function marketConfirm(marketId, oldContent) {
		var content = $("#content").val();
		var param = {
			marketId : marketId,
			content : content,
			oldContent : oldContent
		};
		$.ajax({
			type : "post",
			url : "${ctx}/loan/market/marketConfirm",
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

	
	
	
	function marketResult(marketId,ctx) {
		var param = {
			marketId : marketId
		};
		$.ajax({
			type : "post",
			url : "${ctx}/loan/market/toMarketResult",
			data : param,
			dataType : "json",
			error : function(request) {
				alert("系统繁忙,请稍后再试");
			},
			success : function(data, textStatus) {
				if (data.code == "1") {
					var myTemplate = Handlebars.compile($("#loan_market_result")
							.html());
					var html = myTemplate(data.data);
					layer.open({
						type : 1,
						title : '拨打结果',
						area : [ '35%', '28%' ], //宽高
						content : html
					})
				} else {
					alert(data.msg)
				}
	
			},
	
		});
	};
	
	
	

	function confimMarketResult(marketId, resultType) {
		var markResult = $("input[name='markResult']:checked").val();
		if(markResult==undefined || markResult==""){
			alert("请选择拨打情况");
			return;
		}
		var param = {
			marketId : marketId,
			resultType : markResult
		};
		$.ajax({
			type : "post",
			url : "${ctx}/loan/market/confimMarketResult",
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