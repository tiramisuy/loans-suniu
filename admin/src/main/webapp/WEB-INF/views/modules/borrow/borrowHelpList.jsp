<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
            <title>助贷管理</title>
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
        <a href="${ctx}/borrow/helpApply/list">
	       	助贷管理
        </a>
    </li>


</ul>
<!--BEGIN TITLE & BREADCRUMB PAGE-->
<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
    <div class="pull-left">
        <ol class="breadcrumb page-breadcrumb">
                    <li><i class="fa fa-home"></i>&nbsp;<a href="#">助贷管理</a></li>
                    <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;助贷营销</li>
        </ol>
    </div>
    <div class="clearfix"></div>
</div>
<!--END TITLE & BREADCRUMB PAGE-->

<form:form id="searchForm" modelAttribute="op" action="${ctx}/borrow/helpApply/list" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>姓名</label>
            <form:input path="userName" htmlEscape="false" maxlength="20" class="input-medium"/>
        </li>
        <li><label>手机号码</label>
            <form:input path="mobile" htmlEscape="false" maxlength="11" class="input-medium"/>
        </li>
       	
      <%--  	
          <li><label>拨打情况</label>
	            <form:select path="isPush" class="input-medium">
	           		<form:option value="">全部</form:option>
	                <form:option value="1">接通</form:option>
	                <form:option value="2">未接通</form:option>
	                <form:option value="3">考虑</form:option>
	                <form:option value="4">需要</form:option>
	                <form:option value="5">不需要</form:option>
	            </form:select>
	        </li>
	        
	         --%>
   			 <li><label>支付状态</label>
	            <form:select path="status" class="input-medium">
	           		<form:option value="">全部</form:option>
	                <form:option value="0">未支付</form:option>
	                <form:option value="1">已支付</form:option>
	                 <form:option value="2">支付中</form:option>
	            </form:select>
	        </li>
   
            <li class="time_li"><label>申请时间</label>
                <form:input id='applyTimeStart' path="applyTimeStart" type="text" readonly="readonly" maxlength="20"
                            class="input-middle Wdate" value="${op.applyTimeStart}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
           
                <h>-</h>
                <form:input id='applyTimeEnd' path="applyTimeEnd" type="text" readonly="readonly" maxlength="20" class="input-middle Wdate"
                            value="${op.applyTimeEnd}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
            </li>
		

        <li class="btns"><input id="btnSubmit1" class="btn btn-primary" type="submit" value="查询"/></li>
    </ul>
</form:form>


<sys:message content="${message}"/>




	<!-- 工具栏 -->
	<div class="row">
		<div class="col-sm-12 pull-left">

			<!-- 分配还款提醒 -->
				<input id="csSubmit" class="btn btn-primary" type="button"
					onclick="helpApplyAllot()" value="订单分配" />
		</div>
	</div>



<table id="contentTable" class="table">
    <colgroup>
    </colgroup>
    <thead>
    <tr>
    	<th><input type="checkbox" name="batchOptControl"></th>
        <th>序号</th>
        <th>分配人</th>
		<th>客户姓名</th>
		 <th>手机号码</th>
		<th>身份证</th>
        <th>银行卡号</th>
        <th>申请时间</th>
        <th>支付金额</th>
        <th>支付时间</th>
        <th>是否支付</th>
        <!-- <th>来源</th>
        <th>交易状态</th> -->
        <th>交易返回信息</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    
    <c:forEach items="${page.list}" var="apply" varStatus="xh">
        <tr>
        	<td><input class="check" type="checkbox" value="${apply.id}"
						username="${apply.userName}" companyId="${apply.mobile}"
						name="batchOptIds">
			</td>
            <td>
                    ${xh.count}
            </td>
             <td>
             		${apply.allotUserName }
             </td>
            <td>
                    ${apply.userName}
            </td>
            <td>
                    ${apply.mobile}
            </td>
            <td>
            		${apply.idNo }
            </td>
             <td>
                    ${apply.cardNo}
            </td>
             <td>
                <fmt:formatDate value="${apply.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
            	${apply.serviceAmt }
            </td>
            <td>
				<fmt:formatDate value="${apply.payTime }" pattern="yyyy-MM-dd HH:mm:ss"/>            
            </td>
            <td>
            	<c:if test="${apply.status == 0}">
            		未支付
            	</c:if>
            	<c:if test="${apply.status == 1}">
            		已支付
            	</c:if>
            	<c:if test="${apply.status == 3}">
            		支付中
            	</c:if>
            </td>
           <%--  <td>
                    ${apply.source}
            </td>
            <td>
                    ${apply.retCode}
            </td> --%>
          	<td>
          		${apply.retMsg }
          	</td>
           <td>
           		 <a href="javascript:void(0)" data-method="offset" data-type="auto"
							onclick="confimBorrow('${apply.id}','${ctx}')"
							style="padding-left: 5px;">助贷记录</a>
							
           </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>

<script id="borrow_help" type="text/x-handlebars-template">
    <jsp:include page="borrowHelpForm.jsp"></jsp:include>
</script>

<script id="allot_help_apply" type="text/x-handlebars-template">
    <jsp:include page="helpApplyAllot.jsp"></jsp:include>
</script>

<script>


		
function helpApplyAllot() {
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
		var myTemplate = Handlebars.compile($("#allot_help_apply").html());
		var html = myTemplate(param);
		layer.open({
			type : 1,
			title : '订单分配',
			area : [ '60%', '80%' ], //宽高
			content : html
		})
		$.ajax({
			type : "post",
			url : "${ctx}/borrow/helpApply/getAllCsUser",
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
			url : '${ctx}/borrow/helpApply/doHandAllot',
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



	function confimBorrow(applyId,ctx) {
		var param = {
			id : applyId
		};
		$.ajax({
			type : "post",
			url : "${ctx}/borrow/helpApply/toConfimHelp",
			data : param,
			dataType : "json",
			error : function(request) {
				alert("系统繁忙,请稍后再试");
			},
			success : function(data, textStatus) {
				if (data.code == "1") {
					var myTemplate = Handlebars.compile($("#borrow_help")
							.html());
					var html = myTemplate(data.data);
					layer.open({
						type : 1,
						title : '助贷记录',
						area : [ '90%', '80%' ], //宽高
						content : html
					})
				} else {
					alert(data.msg)
				}
	
			},
	
		});
	};
	
	
	
	function BorrowConfirm(borrowId,mobile) {
		var company = $("#company").val();
		var borrowAmt = $("#borrowAmt").val();
		
		
		var regu = /^[1-9]\d*$|^[1-9]\d*\.\d\d?$|^0\.\d\d?$/;
		if(borrowAmt !=""){
			if(!regu.test(borrowAmt)){
				 layer.alert('借款金额输入错误！',{
			    	   icon : 7
			       });
			       return false;
			}
		}
		var giveAmt = $("#giveAmt").val();
		if(giveAmt !=""){
			if(!regu.test(giveAmt)){
				 layer.alert('放款金额输入错误！',{
			    	   icon : 7
			       });
			       return false;
			}
		}
		
		
		
		var source =$("#source").val();
		/* var status = $("#status").val(); */
		
		var status = $("#allotStatus").find("option:selected").val();
		
		
		if(borrowId == ""){
			layer.alert('用户不存在', {
				icon : 7
			});
			return false;
		};

		   $(".layui-btn").addClass("layui-btn-disabled");
           $(".layui-btn").attr('disabled',true);
		
		
		var param = {
				company : company,
				borrowId : borrowId,
				borrowAmt : borrowAmt,
				giveAmt : giveAmt,
				source : source,
				status :status,
				mobile : mobile
		};
		$.ajax({
			type : "post",
			url : "${ctx}/borrow/helpApply/borrowResultConfirm",
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