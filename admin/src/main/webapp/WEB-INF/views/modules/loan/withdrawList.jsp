<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>提现明细</title>
    <meta name="decorator" content="default"/>

    <script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js" type="text/javascript"></script>
    <script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(document).ready(function () {

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
        <a href="${ctx}/loan/withdraw/list">提现明细</a>
    </li>


</ul>
<!--BEGIN TITLE & BREADCRUMB PAGE-->
<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
    <div class="pull-left">
        <ol class="breadcrumb page-breadcrumb">
            <li><i class="fa fa-home"></i>&nbsp;<a href="#">贷后管理</a></li>
            <li>&nbsp;<i class="fa fa-angle-right"></i>&nbsp;<a href="#">放款管理</a></li>
            <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;提现明细</li>
        </ol>
    </div>
    <div class="clearfix"></div>
</div>
<!--END TITLE & BREADCRUMB PAGE-->

<form:form id="searchForm" modelAttribute="withdrawOP" action="${ctx}/loan/withdraw/list" method="post"
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
        <li><label>身份证号</label>
            <form:input path="idNo" htmlEscape="false" maxlength="25" class="input-medium"/>
        </li>
        <li><label>提现金额</label>
            <form:input path="txAmt" htmlEscape="false" maxlength="25" class="input-medium"/>
        </li>
<%--        <li><label>是否自动提现</label>
            <form:select path="txType" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="auto">是</form:option>
                <form:option value="manual">否</form:option>
            </form:select>
        </li>--%>
   
<%--    	<li><label>放款渠道</label>
            <form:select path="chlCode" class="input-medium">
                <form:option value="">全部</form:option>
                <form:option value="BAOFOO">宝付支付</form:option>  
                <form:option value="TONGRONG">通融</form:option>
                <form:option value="XIANFENG">先锋支付</form:option>
                <form:option value="KJTPAY">海尔支付</form:option>
                <form:option value="HANJS">汉金所</form:option>
            </form:select>
        </li>--%>
        <li><label>提现状态</label>
            <form:select path="status" class="input-medium">
                <form:option value="">全部</form:option>
                <c:forEach items="${withdrawEnums}" var="detail">
                    <form:option value="${detail.value}">${detail.desc}</form:option>
                </c:forEach>
            </form:select>
        </li>
        <%--<li><label>放款时间</label>--%>
            <%--<form:input path="sendStart" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"--%>
                        <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<h>-</h>--%>
            <%--<form:input path="sendEnd" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"--%>
                        <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
        <%--</li>--%>
        <li class="time_li"><label>申请时间</label>
            <form:input path="applyStart" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
        
            <h>-</h>
            <form:input path="applyEnd" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
        </li>
        <li class="time_li"><label>到账时间</label>
            <form:input path="accountStart" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
        
            <h>-</h>
            <form:input path="accountEnd" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
        </li>

        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
        	<%--<input id="btnGenerateEquitableAssignment" class="btn btn-primary" type="submit" onclick="generateEquitableAssignment(this);return false;" value="生成债转合同"/>--%>
        </li>
    </ul>
</form:form>
<c:if test="${fns:haveRole('export') == true}">
    <div class="row">
			<div class="col-sm-12 pull-left">
				<%--导出按钮--%>
				<button id="btnExport" class="btn btn-white btn-sm" id="btnImport"
					data-toggle="tooltip" data-placement="left"
					onclick="return validateExport()">
					<i class="fa fa-file-excel-o"></i> 导出
				</button>
			</div>
    </div>
</c:if>

<sys:message content="${message}"/>
<table id="contentTable" class="table">
    <thead>
    <tr>
        <th>序号</th>
        <th>借款订单号</th>
        <th>提现订单号</th>
        <th>支付订单号</th>
        <th>借款人姓名</th>
        <th>手机号码</th>
        <th>证件号码</th>
        <th>银行名称</th>
        <th>银行卡号</th>
        <th>支付渠道</th>
        <!--
        <th>是否自动提现</th>
        -->
        <th>提现金额</th>
        <th>提现申请时间</th>
        <th>到账时间</th>
        <th>提现状态</th>
        <th>备注</th>
        <th style="min-width:220px;">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="item" varStatus="xh">
        <tr>
            <td>
                    ${xh.count}
            </td>
            <td>
                    ${item.applyId}
            </td>
            <td>
                    ${item.id}
            </td>
            <td>
                    ${item.chlOrderNo}
            </td>
            <td>
                <a href="javascript:void(0)" data-method="offset" data-type="auto"
                   onclick="contentTable_2('${item.userId}','${item.applyId}','detail')">${item.name}</a>

            </td>
            <td>
                    ${item.mobile}
            </td>
            <td>
                    ${item.idNo}
            </td>
            <td>
                    ${item.toBankName}
            </td>
            <td>
                    ${item.toAccNo}
            </td>

            <td>
                    ${item.chlName}
            </td>
            <!--
            <td>
                    ${item.txType == "auto" ? "是" : "否"}
            </td>
            -->
            <td>
                    ${item.txAmt}
            </td>
            <td>
                <fmt:formatDate value="${item.txTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                <fmt:formatDate value="${item.succTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                <c:choose>
                    <c:when test="${item.statusStr == '待提现'}">
                        <div class="text-blue">${item.statusStr}</div>
                    </c:when>
                    <c:when test="${item.statusStr == '提现失败'}">
                        <div class="required">${item.statusStr}</div>
                    </c:when>
                    <c:when test="${item.status == '0000'}">
                        <div class="required">代付请求交易成功</div>
                    </c:when>
                    <c:when test="${item.status == 'ERROR'}">
                        <div class="required">代付失败</div>
                    </c:when>
                    <c:otherwise>
                        ${item.statusStr}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                    ${item.remark}
            </td>
            <td>
<%--            <c:if test="${item.chlCode == 'HANJS'}">
            		<a href="javascript:void(0)" onclick="queryHanjsOrderStatus('${item.applyId}')" style="padding-left: 10px;">查询状态</a>
            	</c:if>

                <c:if test="${item.chlCode == 'HANJS' and item.status==512 and fns:pastMinutes(item.txTime)>1440}">
                    <a href="javascript:void(0)" onclick="showChangePaychannel('${item.id}','${item.applyId}')" style="padding-left: 10px;">修改放款渠道</a>
                    <a href="javascript:void(0)" onclick="cancelHJSOrder('${item.applyId}','${item.id}')" style="padding-left:5px;">取消借款</a>
            	</c:if>--%>
            	<%--<c:choose>
            		<c:when test="${item.chlCode == 'BAOFOO' and item.status==514}">
            			<c:choose>
            				<c:when test="${not empty item.contractUrl}">
            					<a href="javascript:void(0)" data-method="offset" data-type="auto" style="padding-left:5px;" onclick="checkContract('${item.contractUrl}');">查看合同</a>
            				</c:when>
            				<c:otherwise>
            					<a href="javascript:void(0)" data-method="offset" data-type="auto" style="padding-left:5px;" onclick="generatorContract('${item.id}', this);">生成合同</a>
            				</c:otherwise>
            			</c:choose>
            		</c:when>
            		<c:otherwise>
            			<c:if test="${item.chlCode != 'BAOFOO'}">
            				<a href='${ctx}/loan/withdraw/exportContract?contNo=${item.applyId}&type=xjd' style="padding-left:5px;">借款协议</a>
            			</c:if>
            		</c:otherwise>
            	</c:choose>
            	--%>

                <%--
                <c:if test="${fns:haveRole('boss') == true}">
                	<a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="changeRepayTime('${item.applyId}')" style="padding-left:5px;">修改账单日</a>
                </c:if> 
                <c:if test="${(fns:haveRole('boss') == true or fns:haveRole('caiwu') == true) and item.chlCode == 'BAOFOO' and item.lastStatus!='514'}">
                	<a href="javascript:void(0)" onclick="reWithdraw('${item.applyId}','${item.id}','${item.chlCode}')" style="padding-left:5px;">重新放款</a>
                </c:if>
                <c:if test="${(fns:haveRole('boss') == true or fns:haveRole('caiwu') == true) and item.chlCode == 'TONGRONG' and item.lastStatus!='514'}">
                	<a href="javascript:void(0)" onclick="reWithdraw('${item.applyId}','${item.id}','${item.chlCode}')" style="padding-left:5px;">重新放款</a>
                </c:if>
                --%>

                <c:if test="${(fns:haveRole('boss') == true or fns:haveRole('caiwu') == true) and item.chlCode == 'TONGLIAN_LOAN' and item.lastStatus!='514'}">
                    <a href="javascript:void(0)" onclick="reWithdraw('${item.applyId}','${item.id}','${item.chlCode}')" style="padding-left:5px;">重新放款</a>
                </c:if>
               <%--
                <c:if test="${fns:haveRole('boss') == true and item.chlCode == 'HANJS' and item.lastStatus!='512' and item.lastStatus!='514' && !fn:contains(item.remark,'_TO_')}">
                	<a href="javascript:void(0)" data-method="offset" data-type="auto" onclick="showChangePaychannel('${item.id}')" style="padding-left:5px;">修改放款渠道</a>
                </c:if> 
                --%>

            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>

<script id="tpl_creditList_2" type="text/x-handlebars-template">
    <jsp:include page="userInfo.jsp"></jsp:include>
</script>


<script id="repayTimeDialog" type="text/x-handlebars-template">
    <jsp:include page="/WEB-INF/views/include/repayDateDialog.jsp"></jsp:include>
</script>

<script id="changePaychannel" type="text/x-handlebars-template">
    <jsp:include page="/WEB-INF/views/include/changePaychannel.jsp"></jsp:include>
</script>

<script id="tpl_checkContract" type="text/x-handlebars-template">
	<jsp:include page="/WEB-INF/views/modules/cust/checkContract.jsp"></jsp:include>
</script>

<script>

function dateFtt(fmt,date)   
{ //author: meizz   
  var o = {   
    "M+" : date.getMonth()+1,                 //月份   
    "d+" : date.getDate(),                    //日   
    "h+" : date.getHours(),                   //小时   
    "m+" : date.getMinutes(),                 //分   
    "s+" : date.getSeconds(),                 //秒   
    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
    "S"  : date.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
} 

function changeRepayTime(applyId) {
	var d = new Date();
	var str = dateFtt('yyyy-MM-dd hh:mm:ss',d);
	//var str = d.getFullYear().toString() + '-' + addzero(d.getMonth() + 1) + '-' + addzero(d.getDate()) + ' ' + addzero(d.getHours()) + ':' + addzero(d.getMinutes()) + ':' + addzero(d.getSeconds());
	var param = { 
		applyId : applyId,
		date : str
	};
	var myTemplate = Handlebars.compile($("#repayTimeDialog").html());
	var html = myTemplate(param);
	layer.open({
		type : 1,
		title : '修改账单日',
		area : [ '500px', '240px' ], //宽高
		content : html
	});
};


function changeRepayDate(applyId) {
	var loanTime = $("#loantime").val();
	changeRepayDateConfirm(applyId, loanTime);
	layer.close(index);
}



function changeRepayDateConfirm(applyId, loanTime) {
	var param = {
		applyId : applyId,
		loanTime : loanTime
	};
	$.ajax({
		type : "post",
		url : "${ctx}/loan/withdraw/changeRepayDate",
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


function showChangePaychannel(id,applyId) {
	
	var param = { 
		id : id,
        applyId : applyId
	};
	var myTemplate = Handlebars.compile($("#changePaychannel").html());
	var html = myTemplate(param);
	layer.open({
		type : 1,
		title : '修改放款渠道',
		area : [ '500px', '240px' ], //宽高
		content : html
	});
};

function changePaychannel(id,applyId) {
	var cgPaychannel = $("#cgPaychannel").val();
	changePaychannelConfirm(id, cgPaychannel,applyId);
	layer.close(layer.index);
}



function changePaychannelConfirm(id, cgPaychannel,applyId) {
	var param = {
		id : id,
		paychannel : cgPaychannel,
        applyId : applyId
	};
	$.ajax({
		type : "post",
		url : "${ctx}/loan/withdraw/changePaychannel",
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


function validateExport() {
	var startTime = $("#applyStart").val();
	var endTime = $("#applyEnd").val();
	var startTime1 = $("#accountStart").val();
	var endTime1 = $("#accountEnd").val();
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
			'${ctx}/loan/withdraw/exportWithdraw',
			'${ctx}/loan/withdraw/list');
};

    function withdraw(applyId){
        var param = {
            applyId:applyId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/withdraw/withdraw",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    location.reload(true);
                } else {
                    alert(data.msg)
                }

            },

        });
    }
    ;


    function contentTable_2(id, applyId, sign) {
        var param = {
            id: id,
            applyId: applyId,
            sign: sign
        };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/custUser/userDetail",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_creditList_2").html());
                    var html = myTemplate(data.data);
                    layer.open({
                        type: 1,
                        title: '客户信息',
                        area: ['95%', '80%'], //宽高
                        content: html
                    })
                } else {
                    alert(data.msg)
                }

            },

        });
    };
    
    function checkContract(contracts){
    	debugger;
    	var contractList = new Array();
    	contractList = contracts.split(",");
    	var param = {
    			data : contractList
    	};
    	var myTemplate = Handlebars.compile($("#tpl_checkContract").html());
        var html = myTemplate(param);
        layer.open({
            type: 1,
            title: '查看合同',
            area: ['20%', '30%'], //宽高
            content: html
        });
    };
    
    function generatorContract(payLogId, document){
    	debugger;
    	var generatorMsg = '合同生成中。。';
    	if($(document).html() == generatorMsg) return;
    	$(document).html(generatorMsg);
    	var param = {
    		payLogId : payLogId
    	};
    	 $.ajax({
             type: "post",
             url: "${ctx}/loan/withdraw/generatorContract",
             data: param,
             dataType: "json",
             error: function (request) {
            	 $(document).html('生成合同');
                 alert("系统繁忙,请稍后再试");
             },
             success: function (data, textStatus) {
                 if (data.code == "1") {
                	 $(document).replaceWith('<a href="javascript:void(0)" data-method="offset" data-type="auto" style="padding-left:5px;" onclick="checkContract(\'' + data.data + '\');">查看合同</a>');
                 } else {
                	 $(document).html('生成合同');
                	 alert("系统繁忙,请稍后再试");
                 }
             },

         });
    };
    function reWithdraw(applyId,payNo,chlCode) {
		layer.confirm('您确定要重新放款吗?', {
			btn : [ '提交', '取消' ]
		}, function(index) {
			reWithdrawConfirm(applyId,payNo,chlCode);
			layer.close(index);
		});
	};
	
	function reWithdrawConfirm(applyId,payNo,chlCode){
		var param = {
			applyId:applyId,
			payNo : payNo,
			chlCode:chlCode
		};
		$.ajax({
			type : "post",
			url : "${ctx}/loan/withdraw/reWithdraw",
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
	
	// 生成债转合同
	function generateEquitableAssignment() {
		var param = {};
	    var t = $('#searchForm').serializeArray();
	    $.each(t, function() {
	    	param[this.name] = this.value;
	    });
	    if(!param.accountStart || !param.accountEnd) {
	    	layer.alert('请选择到账时间段', {
				icon : 7
			});
	    	return false;
	    }
	    $.ajax({
			type : "post",
			url : "${ctx}/loan/withdraw/generateEquitableAssignment",
			data : param,
			dataType : "json",
			error : function(request) {
				alert("系统繁忙,请稍后再试");
			},
			success : function(data, textStatus) {
				if (data.code == "1") {
					alert("生成债转合同成功！");
				} else {
					alert(data.msg)
				}
			},
		});
	    
	}
	
	function queryHanjsOrderStatus(applyId){
			var param = {
					applyId : applyId
				};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/withdraw/queryHanjsOrderStatus",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						layer.alert(data.msg);
					} else {
						alert(data.msg)
					}
				},

			});
		};


    function cancelHJSOrder(applyId,payNo) {
        layer.confirm('您确定要取消借款吗?', {
            btn : [ '提交', '取消' ]
        }, function(index) {
            cancelHJSOrderConfirm(applyId,payNo);
            layer.close(index);
        });
    };

    function cancelHJSOrderConfirm(applyId,payNo){
        var param = {
            applyId:applyId,
            id : payNo
        };
        $.ajax({
            type : "post",
            url : "${ctx}/loan/withdraw/cancelHanjsOrder",
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