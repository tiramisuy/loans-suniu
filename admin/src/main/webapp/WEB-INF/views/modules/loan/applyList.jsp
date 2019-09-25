<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <c:choose>
    	<c:when test="${applyOP.stage == 0}">
            <title>机器待审核</title>
        </c:when>
        <c:when test="${applyOP.stage == 1}">
            <title>人工待审核</title>
        </c:when>
        <c:when test="${applyOP.stage == 2}">
            <title>已过审项目</title>
        </c:when>
        <c:when test="${applyOP.stage == 3}">
            <title>已否决项目</title>
        </c:when>
        <c:when test="${applyOP.stage == 4}">
            <title>办理中项目</title>
        </c:when>
        <c:when test="${applyOP.stage == 5}">
            <title>待复审项目</title>
        </c:when>
        <c:when test="${applyOP.stage == 6}">
            <title>已取消项目</title>
        </c:when>
    </c:choose>
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
        }
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
    <style type="text/css">
        .l{float: left;}
        .operSearch-tit{ display:inline-block; width:70px; height:24px; line-height:24px; font-size:14px; padding-left: 50px; padding-top: 20px}
        .operSearch-btn{ display:inline-block;}
        .operSearch-btn a, .daochu-btn{ display:inline-block; padding:0 15px; height:30px; line-height:30px; font-size:14px; color:#ffffff; background:#2fa4e7;}
        .operSearch-channel{ width:90%; padding-top: 20px}
        .operSearch-channel a{ display:inline-block; margin:0 10px 10px 0; padding:0 10px; height:22px; line-height:22px; color:#333333; font-size:14px; border:1px solid #bbbbbb; -moz-border-radius:3px; -webkit-border-radius:3px; -ms-border-radius:3px; border-radius:3px;}
        .operSearch-channel a.operChannel-cur{ color:#2fa4e7; border:1px solid #2fa4e7;}
        .daochu{ padding:10px 0; height:30px; text-align:right;}
        .daochu-btn{ background:#f93500;}
    </style>
</head>
<body>

<c:set var="productList" value="${fns:getProductList()}" />
<c:set var="channelList" value="${fns:getChannelList()}" />

<ul class="nav nav-tabs">
    <li class="active">
        <a href="${ctx}/loan/apply/list?stage=${applyOP.stage}">
            <c:choose>
	            <c:when test="${applyOP.stage == 0}">
	                机器待审核
	       		</c:when>
                <c:when test="${applyOP.stage == 1}">
                    人工待审核
                </c:when>
                <c:when test="${applyOP.stage == 2}">
                    已过审项目
                </c:when>
                <c:when test="${applyOP.stage == 3}">
                    已否决项目
                </c:when>
                <c:when test="${applyOP.stage == 4}">
                    办理中项目
                </c:when>
                <c:when test="${applyOP.stage == 5}">
		  待复审项目
		        </c:when>
		        <c:when test="${applyOP.stage == 6}">
		  已取消项目
		        </c:when>
            </c:choose>
        </a>
    </li>


</ul>
<!--BEGIN TITLE & BREADCRUMB PAGE-->
<div id="title-breadcrumb-option-demo" class="page-title-breadcrumb">
    <div class="pull-left">
        <ol class="breadcrumb page-breadcrumb">
            <c:choose>
            	<c:when test="${applyOP.stage == 0}">
                    <li><i class="fa fa-home"></i>&nbsp;<a href="#">贷前管理</a></li>
                    <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;机器待审核</li>
                </c:when>
                <c:when test="${applyOP.stage == 1}">
                    <li><i class="fa fa-home"></i>&nbsp;<a href="#">贷前管理</a></li>
                    <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;人工待审核</li>
                </c:when>
                <c:when test="${applyOP.stage == 2}">
                    <li><i class="fa fa-home"></i>&nbsp;<a href="#">贷前管理</a></li>
                    <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;已过审项目</li>
                </c:when>
                <c:when test="${applyOP.stage == 3}">
                    <li><i class="fa fa-home"></i>&nbsp;<a href="#">贷前管理</a></li>
                    <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;已否决项目</li>
                </c:when>
                <c:when test="${applyOP.stage == 4}">
                    <li><i class="fa fa-home"></i>&nbsp;<a href="#">贷中管理</a></li>
                    <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;办理中项目</li>
                </c:when>
                <c:when test="${applyOP.stage == 5}">
                    <li><i class="fa fa-home"></i>&nbsp;<a href="#">贷前管理</a></li>
                    <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;待复审项目</li>
                </c:when>
                <c:when test="${applyOP.stage == 6}">
                    <li><i class="fa fa-home"></i>&nbsp;<a href="#">贷前管理</a></li>
                    <li class="active">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;已取消项目</li>
                </c:when>
            </c:choose>
        </ol>
    </div>
    <div class="clearfix"></div>
</div>
<!--END TITLE & BREADCRUMB PAGE-->

<form:form id="searchForm" modelAttribute="applyOP" action="${ctx}/loan/apply/list?stage=${applyOP.stage}" method="post"
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
        <c:if test="${applyOP.stage == 1}">
        	<li><label>审核人</label>
                <form:select path="auditor" class="input-medium">
                	<form:option value="">全部</form:option>
                    <c:forEach items="${auditor}" var="detail">
                        <c:choose>
                            <c:when test="${applyOP.auditor == detail.id}">
                                <form:option value="${detail.id}" selected="selected">${detail.name}</form:option>
                            </c:when>
                            <c:otherwise>
                                <form:option value="${detail.id}">${detail.name}</form:option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
            </li>
        </c:if>
        <c:if test="${applyOP.stage == 1 or applyOP.stage == 2}">
            <li><label>宜信等级</label>
                <form:select path="compositeScore" class="input-medium">
                    <form:option value="">全部</form:option>
                    <form:option value="A">A</form:option>
                    <form:option value="B">B</form:option>
                    <form:option value="C">C</form:option>
                    <form:option value="D">D</form:option>
                    <form:option value="E">E</form:option>
                </form:select>
            </li>
        </c:if>
        <c:if test="${applyOP.stage > 1}">
        	<li><label>是否自动审核</label>
                <form:select path="autoCheck" class="input-medium">
                    <c:forEach items="${autoCheck}" var="detail">
                        <c:choose>
                            <c:when test="${applyOP.autoCheck == detail.value}">
                                <form:option value="${detail.value}" selected="selected">${detail.desc}</form:option>
                            </c:when>
                            <c:otherwise>
                                <form:option value="${detail.value}">${detail.desc}</form:option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
            </li>

            <li><label>是否复贷</label>
                <form:select path="loanSucess" class="input-medium">
                    <form:option value="">全部</form:option>
                    <form:option value="1">是</form:option>
                    <form:option value="0">否</form:option>
                </form:select>
            </li>

            <li><label>审核人</label>
                <form:select path="auditor" class="input-medium">
                	<form:option value="">全部</form:option>
                    <c:forEach items="${auditor}" var="detail">
                        <c:choose>
                            <c:when test="${applyOP.auditor == detail.id}">
                                <form:option value="${detail.id}" selected="selected">${detail.name}</form:option>
                            </c:when>
                            <c:otherwise>
                                <form:option value="${detail.id}">${detail.name}</form:option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
            </li>
            <c:if test="${applyOP.stage == 2 or applyOP.stage == 4}">
                <li><label>订单状态</label>
                    <form:select path="status" class="input-medium">
                        <form:option value="">全部</form:option>
                        <c:forEach items="${statusList}" var="detail">
                            <c:choose>
                                <c:when test="${applyOP.status == detail.value}">
                                    <form:option value="${detail.value}" selected="selected">${detail.desc}</form:option>
                                </c:when>
                                <c:otherwise>
                                    <form:option value="${detail.value}">${detail.desc}</form:option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>
                </li>
            </c:if>
            <li class="time_li"><label>审核时间</label>
                <form:input id='checkStart' path="checkStart" type="text" readonly="readonly" maxlength="20"
                            class="input-middle Wdate" value="${applyOP.checkStart}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>
           
                <h>-</h>
                <form:input id='checkEnd' path="checkEnd" type="text" readonly="readonly" maxlength="20" class="input-middle Wdate"
                            value="${applyOP.checkEnd}"
                            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
            </li>
		</c:if>
    </ul>
    <ul class="ul-form">
        <li class="time_li"><label>申请时间</label>
            <form:input id='applyStart' path="applyStart" type="text" readonly="readonly" maxlength="20" class="input-middle Wdate"
                        value="${applyOP.applyStart}"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 00:00:00',isShowClear:false,isShowToday:false});"/>

            <h>-</h>
            <form:input id='applyEnd' path="applyEnd" type="text" readonly="readonly" maxlength="20" class="input-middle Wdate"
                        value="${applyOP.applyEnd}"
                        onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',startDate:'%y-%M-%d 23:59:59',isShowClear:false,isShowToday:false});"/>
        </li>
        <input id="channel" name="channel" type="hidden" value="${applyOP.channel}" />
        <input id="allChannel" name="allChannel" type="hidden" value="${allChannel}" />

        <div class="operSearch-btn r">
            <a href="javascript:void(0);" id="operSearch-btn"
               class="border-radius3">搜索</a>
        </div>
    </ul>
    <ul class="ul-form">
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
    </ul>
</form:form>

<%--
<c:if test="${applyOP.stage == 1 and fns:haveRole('dept') == true}"> <!-- and isCsy != true -->
    <li class="btns"><input id="csSubmit" class="btn btn-primary" type="button" onclick="assignment()" value="订单分配"/>
    </li>
</c:if>
<c:if test="${applyOP.stage == 2 and fns:haveRole('export') == true}">
    <div class="row">
        <div class="col-sm-12 pull-left">
            <!-- 导出按钮 -->
            <button id="btnExport" class="btn btn-white btn-sm" id="btnImport"
                    data-toggle="tooltip" data-placement="left"
                    onclick="return exportExcel('${ctx}/loan/apply/exportAuditPass?stage=${applyOP.stage}','${ctx}/loan/apply/list?stage=${applyOP.stage}');"
                    onclick="return validateExport()">
                <i class="fa fa-file-excel-o"></i> 导出
            </button>
        </div>
    </div>
</c:if>--%>

<sys:message content="${message}"/>
<table id="contentTable" class="table">
    <colgroup>
    </colgroup>
    <thead>
    <tr>
    	<th>
            <c:if test="${applyOP.stage == 1 || applyOP.stage == 3}">
                <input type="checkbox" name="batchOptControl">
            </c:if>
        </th>
        <th>序号</th>
        <c:if test="${applyOP.stage == 2 || applyOP.stage == 4}">
        	<th>合同编号</th>
        </c:if>
		<th>借款人姓名</th>
        <th>手机号码</th>
        <%--<th>证件号码</th>--%>
        <c:if test="${applyOP.stage == 1 || applyOP.stage == 2}">
            <th>宜信等级</th>
            <th>宜信借贷记录数量</th>
        </c:if>
        <th>借款产品</th>
        <th>申请本金</th>
        <c:if test="${applyOP.stage == 2 || applyOP.stage == 4 || applyOP.stage == 5 || applyOP.stage == 6}">
            <th>审批本金</th>
        </c:if>
        <th>申请期限</th>
        <c:if test="${applyOP.stage == 2 || applyOP.stage == 4 || applyOP.stage == 5 || applyOP.stage == 6}">
            <th>审批期限</th>
        </c:if>
        <th>还款方式</th>
        <th>还款期数</th>
        <th>利率</th>
        <th>渠道</th>
        <th>来源</th>
        <th>申请时间</th>
        <c:if test="${applyOP.stage == 4 || applyOP.stage == 2}"> 
         <th>放款渠道</th>
        </c:if>
        
        <c:if test="${applyOP.stage > 1}">
            <th>审核时间</th>
            <th>自动审核</th>
        </c:if>
        <c:if test="${applyOP.stage != 0}">
            <th>审核人</th>
        </c:if>
        <c:if test="${applyOP.stage == 1 || applyOP.stage == 5}">
        	<th>放款次数</th>
        </c:if>
        <c:if test="${applyOP.stage == 5}">
            <th>初审结果</th>
        </c:if>
        <th>订单状态</th><%--
        <th>标签</th>
        <th>是否进入过绑卡页</th>--%>
        <c:if test="${applyOP.stage == 1 || applyOP.stage == 2}">
        <th>二次机审结果</th>
        </c:if>
        <c:choose>
        	<c:when test="${applyOP.stage == 4}">
        		<th style="min-width:200px;">操作</th>
        	</c:when>
        	<c:when test="${applyOP.stage == 2}">
        		<th style="min-width:200px;">操作</th>
        	</c:when>
        	<c:otherwise>
        		<c:if test="${applyOP.stage != 6}">
        			<th style="min-width:65px;">操作</th>
        		</c:if>
        	</c:otherwise>
        </c:choose>
    </tr>
    </thead>
    <tbody>
    
    <c:forEach items="${page.list}" var="apply" varStatus="xh">
        <tr>
        	<td>
            <c:if test="${applyOP.stage == 1 || applyOP.stage == 3}">  <!-- and isCsy != true -->
                <input class="check" type="checkbox" value="${apply.id}" username="${apply.userName}" companyId="${apply.companyId}" name="batchOptIds">
            </c:if>
        	</td>
            <td>
                    ${xh.count}
            </td>

            <c:if test="${applyOP.stage == 2 || applyOP.stage == 4}">
	            <td>
	                    ${apply.contNo}
	            </td>
            </c:if>
            <td>
                    ${apply.userName}
            </td>
            <td>
                    ${apply.mobile}
            </td>

            <c:if test="${applyOP.stage == 1 || applyOP.stage == 2}">
                <td>
                    <c:choose>
                        <c:when test="${apply.compositeScore > 550 && apply.compositeScore <= 650}">
                            A
                        </c:when>
                        <c:when test="${apply.compositeScore > 500 && apply.compositeScore <= 550}">
                            B
                        </c:when>
                        <c:when test="${apply.compositeScore > 450 && apply.compositeScore <= 500}">
                            C
                        </c:when>
                        <c:when test="${apply.compositeScore > 400 && apply.compositeScore <= 450}">
                            D
                        </c:when>
                        <c:when test="${apply.compositeScore >= 300 && apply.compositeScore <= 400}">
                            E
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${apply.loanRecord}</td>
            </c:if>

            <%--<td>--%>
                    <%--${apply.idNo}--%>
            <%--</td>--%>
            <c:forEach items="${productList}" var="detail">
            	<c:if test="${apply.productId == detail.id}">
	             		   <td>${detail.name}</td>
	            </c:if>
            </c:forEach>
            <td>
                    ${apply.applyAmt}
            </td>
            <c:if test="${applyOP.stage == 2 || applyOP.stage == 4 || applyOP.stage == 5 || applyOP.stage == 6}">
            <td>
                    ${apply.approveAmt}
            </td>
        	</c:if>
            <td>
                    ${apply.applyTerm}天
            </td>
            <c:if test="${applyOP.stage == 2 || applyOP.stage == 4 || applyOP.stage == 5 || applyOP.stage == 6}">
            <td>
                    ${apply.approveTermStr}
            </td>
            </c:if>
            <td>
                    ${apply.repayMethodStr}
            </td>
            <td>
                    ${apply.term}
            </td>
            <td>
                    ${apply.basicRateStr}
            </td>
            
           	<td>
           		<c:forEach items="${channelList}" var="detail">
	           		<c:if test="${apply.channel == detail.cid}">
	             		   ${detail.cName}
	            	</c:if>
                </c:forEach>
            </td>
            <td>
            	<c:choose>
                    <c:when test="${apply.source==1}">
                    	IOS
                    </c:when>
                    <c:when test="${apply.source==2}">
                    	Android
                    </c:when>
                    <c:when test="${apply.source==3}">
                    	H5
                    </c:when>
                    <c:when test="${apply.source==4}">
                    	API
                    </c:when>
                    <c:otherwise>
                                                              未知
                   </c:otherwise>
                </c:choose>
            </td>
            <td>
                <fmt:formatDate value="${apply.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            
             <c:if test="${applyOP.stage == 4 || applyOP.stage == 2}"> 
	            <td>
	            	<c:choose>
	                    <c:when test="${apply.payChannel==7}">
	                    	通联
	                    </c:when>
	                    <c:otherwise>
	                        --
	                   </c:otherwise>
	                </c:choose>
	            </td>
            </c:if>
            
            <c:if test="${applyOP.stage > 1}">
                <td><fmt:formatDate value="${apply.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${apply.autoCheck}</td>
            </c:if>
            <c:if test="${applyOP.stage != 0}">
                <td>${apply.approverName}</td>
            </c:if>
            <c:if test="${applyOP.stage == 1 || applyOP.stage == 5}">
               <td>${apply.loanSuccCount}</td>
            </c:if>
            <c:if test="${applyOP.stage == 5}">
           		<c:if test="${apply.checkStatus == 3}"><td>通过</td></c:if>
           		<c:if test="${apply.checkStatus == 4}"><td>拒绝</td></c:if>
        	</c:if>
            <td>
                <c:if test="${applyOP.stage == 1 || applyOP.stage == 5}"><div class="required">${apply.statusStr}</div></c:if>
                <c:if test="${applyOP.stage != 1 && applyOP.stage != 5}"><div>${apply.statusStr}</div></c:if>
            </td>
<%--            <td>
                <c:if test="${apply.productId=='XJD' and apply.status==510}">
                <div>已购买加急券</div>
                </c:if>
            </td>--%>
<%--            <td style="text-align:center">
                <c:if test="${apply.countUserBindCardPv == 0}">否</c:if>
                <c:if test="${apply.countUserBindCardPv == 1}">是</c:if>
            </td>--%>
            <c:if test="${applyOP.stage == 1 || applyOP.stage == 2}">
            <td style="text-align:center">
                <c:if test="${apply.userCreditline == 0}">拒绝</c:if>
                <c:if test="${apply.userCreditline == 1}">通过</c:if>
            </td>
            </c:if>
            <td>
                <c:choose>
                    <c:when test="${applyOP.stage == 1  && fns:haveRole('xinshen') == true}">
                        <a href="javascript:void(0)" onclick="openTabPage('${ctx}/loan/apply/checkFrom?id=${apply.userId}&sign=check&applyId=${apply.id}')">审核</a>
                        <a href="javascript:void(0)" onclick="cancel('${apply.id}')" style="padding-left:5px;">取消</a>
                        <a href="javascript:void(0)" onclick="openTabPage('${ctx}/loan/apply/checkFrom?id=${apply.userId}&sign=detail&applyId=${apply.id}')">详情</a>
                    </c:when>
                    <c:when test="${applyOP.stage == 4  && fns:haveRole('xinshen') == true}">
                        <a href="javascript:void(0)" onclick="cancel('${apply.id}')" style="padding-left:5px;">取消</a>
                        <a href="javascript:void(0)" onclick="openTabPage('${ctx}/loan/apply/checkFrom?id=${apply.userId}&sign=detail&applyId=${apply.id}')">详情</a>
                    </c:when>
                    <c:otherwise>
                        <a href="javascript:void(0)" onclick="openTabPage('${ctx}/loan/apply/checkFrom?id=${apply.userId}&sign=detail&applyId=${apply.id}')">详情</a>
                    </c:otherwise>
                </c:choose>

                <%--<c:choose>--%>
                    <%-- 现金贷人工待审核取消 --%>
                    <%--<c:when test="${applyOP.stage == 1}">

                    </c:when>--%>
	                	<%--<c:if test="${applyOP.stage == 1}">
							&lt;%&ndash;<a href="javascript:void(0)" onclick="openTabPage('${ctx}/loan/apply/approval?id=${apply.userId}&sign=check&applyId=${apply.id}')">快速审核</a>&ndash;%&gt;
	                    </c:if>
	                    
	                    <c:if test="${applyOP.stage != 1}"> 
	                	        <a href="javascript:void(0)" onclick="openTabPage('${ctx}/loan/apply/checkFrom?id=${apply.userId}&sign=check&applyId=${apply.id}')">审核</a>
	                    </c:if>--%>

                    <%-- 已拒绝订单撤销 --%>
                   <%-- <c:when test="${applyOP.stage == 3}">
                    	<a href="javascript:void(0)" onclick="openTabPage('${ctx}/loan/apply/checkFrom?id=${apply.userId}&sign=detail&applyId=${apply.id}')">详情</a>
                        <a href="javascript:void(0)" onclick="resetCheck('${apply.id}')" style="padding-left:5px;">撤销</a>
                    </c:when>--%>
                	<%-- 现金贷14天扣除后放款或取消 --%>
                    <%--<c:when test="${applyOP.stage == 4　and apply.status==410}">
                    	<a href="javascript:void(0)" onclick="openTabPage('${ctx}/loan/apply/checkFrom?id=${apply.userId}&sign=detail&applyId=${apply.id}')">详情</a>
                    	<a href="javascript:void(0)" onclick="processAdminLendPay('${apply.id}')" style="padding-left:5px;">扣除后放款</a>
                    	<a href="javascript:void(0)" onclick="cancel('${apply.id}')" style="padding-left:5px;">取消</a>
                    	
                    	 <c:if test="${(apply.payChannel==3 or apply.payChannel==5) and fns:pastMinutes(apply.time)>30}">
                    		<a href="javascript:void(0)" onclick="toUPayChannel('${apply.id}','${ctx}')" style="padding-left:5px;">更改放款渠道</a> 
                    	</c:if>
                    	
                     	<a href="javascript:void(0)" onclick="handelExtend('${apply.id}')" style="padding-left:5px;">手动放款</a>
                    </c:when>--%>
                    <%-- 现金贷14天买加急券放款或取消 --%>
                    <%--
                    <c:when test="${applyOP.stage == 4 and (apply.productId=='XJD') and fns:haveRole('system')==true and fns:haveRole('jbqb')==true and fns:haveRole('sdcz')==true and apply.status==510 and apply.term==1}">
                    	<a href="javascript:void(0)" onclick="openTabPage('${ctx}/loan/apply/checkFrom?id=${apply.userId}&sign=detail&applyId=${apply.id}')">详情</a>
                    	<a href="javascript:void(0)" onclick="processAdminLendPay('${apply.id}')" style="padding-left:5px;">放款</a> 
                    	<a href="javascript:void(0)" onclick="cancel('${apply.id}')" style="padding-left:5px;">取消</a>
                    </c:when>
                    --%>
                    <%-- 现金贷90天取消 --%>
                    <%-- 
                    <c:when test="${applyOP.stage == 4 and (apply.productId=='XJD') and fns:haveRole('sdcz')==true and fns:haveRole('jbqb')==true and apply.status==410 and apply.term>1}">
                    	<a href="javascript:void(0)" onclick="openTabPage('${ctx}/loan/apply/checkFrom?id=${apply.userId}&sign=detail&applyId=${apply.id}')">详情</a>
                    	<a href="javascript:void(0)" onclick="cancel('${apply.id}')" style="padding-left:5px;">取消</a>
                    </c:when>
                     --%>
                    <%-- 现金贷90天线下买旅游券后推标放款--%>
                    <%-- <c:when test="${applyOP.stage == 4 and (apply.productId=='XJD') and fns:haveRole('tuibiao')==true and fns:haveRole('jbqb')==true and fns:haveRole('sdcz')==true and apply.status==410 and apply.term>1}">
                    	<a href="javascript:void(0)" onclick="openTabPage('${ctx}/loan/apply/checkFrom?id=${apply.userId}&sign=detail&applyId=${apply.id}')">详情</a>
                    	<a href="javascript:void(0)" onclick="processBorrowLendPay('${apply.id}')" style="padding-left:5px;">推标放款</a>
                    	<a href="javascript:void(0)" onclick="cancel('${apply.id}')" style="padding-left:5px;">取消</a>
                    </c:when> --%>
  <%--                  <c:otherwise>
                        <c:if test="${applyOP.stage == 4 and applyOP.status != 510}">
                            <a href="${ctx}/loan/apply/cancle?applyId=${apply.id}"
                               onclick="return confirmx('撤销后该笔借款申请将被取消，确定撤销？', this.href)">撤销</a>
                        </c:if>

                    </c:otherwise>--%>
               <%-- </c:choose>--%>
<%--               <c:if test="${applyOP.stage == 2 and apply.status >=420}">
               <a href="javascript:void(0)" onclick="cancelCoupon('${apply.id}')" style="padding-left:5px;">取消卡券</a>
               </c:if>--%>
               
<%--               <c:if test="${applyOP.stage == 2 and apply.status >= 514}">
					<a href="javascript:void(0)" onclick="generateCoupon('${apply.id}')" style="padding-left:5px;">发放购物券</a>
               </c:if>--%>
               
               <%--
               <c:if test="${applyOP.stage == 2 and fns:haveRole('dept')==true}">
               		<a href="javascript:void(0)" onclick="resetCheck('${apply.id}')" style="padding-left:5px;">撤销</a>
                </c:if>
                --%>
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
<script id="update_pay_channel" type="text/x-handlebars-template">
    		<jsp:include page="updatePayChannel.jsp"></jsp:include>
	</script>
<script>
    $(function() {
        //  搜索条件选中/未选中
        $(".operSearch-channel a").click(function() {
            $("#operChannel-all").removeClass("operChannel-cur");
            $(this).toggleClass("operChannel-cur");
        });
        $("#operChannel-all").click(function() {
            $(this).siblings().removeClass("operChannel-cur");
        });

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
    });

    //按条件搜索
    function operSearch() {
        var channel = "";
        $(".operChannel-cur").each(function(k, v) {
            channel += $(this).attr("value") + ","
        })
        channel = channel.substring(0, channel.length - 1);
        $("#channel").attr("value", channel);
    };

    $("#operSearch-btn").click(function() {
        operSearch();
        $("#searchForm").submit();
        return false;
    });

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
	
	function processBorrowLendPay(applyId) {
		layer.confirm('您确定要推标放款吗？', {
			btn : [ '提交', '取消' ]
		}, function(index) {
			processBorrowLendPayConfirm(applyId);
			layer.close(index);
		});
	};

	function processBorrowLendPayConfirm(applyId, loanTime) {
		var param = {
			applyId : applyId
		};
		$.ajax({
			type : "post",
			url : "${ctx}/loan/withdraw/processBorrowLendPay",
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

	function resetCheck(applyId) {
		layer.confirm('您确定要撤销，重新人工审核吗？', {
			btn : [ '提交', '取消' ]
		}, function(index) {
			resetCheckConfirm(applyId);
			layer.close(index);
		});
	}
	function resetCheckConfirm(applyId) {
		var param = {
			applyId : applyId
		};
		$.ajax({
			type : "post",
			url : "${ctx}/loan/apply/resetcheck",
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
			url : "${ctx}/loan/apply/cancel",
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
	
	
	function handelExtend(applyId){
		layer.confirm('您确定要手动放款吗？', {
			btn : [ '提交', '取消' ]
		}, function(index) {
			doExtend(applyId);
			layer.close(index);
		});
	}
	function doExtend(applyId) {
		var param = {
			applyId : applyId
		};
		$.ajax({
			type : "post",
			url : "${ctx}/loan/apply/handelExtend",
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
	
	
	
	
	function cancelCoupon(applyId) {
		layer.confirm('您确定要取消吗？', {
			btn : [ '提交', '取消' ]
		}, function(index) {
			cancelCouponConfirm(applyId);
			layer.close(index);
		});
	}
	
	//取消卡券
	function cancelCouponConfirm(applyId) {
		var param = {
			applyId : applyId
		};
		$.ajax({
			type : "post",
			url : "${ctx}/loan/apply/cancelCoupon",
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

	function generateCoupon(applyId) {
		layer.confirm('您确定要发放购物券吗？', {
			btn : [ '提交', '取消' ]
		}, function(index) {
			generateCouponConfirm(applyId);
			layer.close(index);
		});
	};
	
	function generateCouponConfirm(applyId) {
		var param = {
			applyId : applyId
		};
		$.ajax({
			type : "post",
			url : "${ctx}/loan/shopcoupon/generateCoupon",
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
	
	

	/**
	 * 加载更改放款渠道页面
	 */
	function toUPayChannel(applyId ,ctx) {
		var param = {
				applyId : applyId
			};
			$.ajax({
				type : "post",
				url : "${ctx}/loan/apply/toUpdatePayChannel",
				data : param,
				dataType : "json",
				error : function(request) {
					alert("系统繁忙,请稍后再试");
				},
				success : function(data, textStatus) {
					if (data.code == "1") {
						var myTemplate = Handlebars.compile($("#update_pay_channel")
								.html());
						
						var data1 = {
								applyId : data.data
							};
						var html = myTemplate(data1);
						layer.open({
							type : 1,
							title : '更改放款渠道',
							area : [ '30%', '25%' ], //宽高
							content : html
						})
					} else {
						alert(data.msg)
					}
		
				},
		
			});
		
	};
	
	
	function updatePayChannel(applyId) {
		
		var payChannel =$("#pChannel option:selected").val();// $("#payChannel").val();
		if(payChannel==undefined || payChannel==""){
			alert("请选择放款渠道");
			return;
		}
		
		
		var param = {
				applyId : applyId,
				payChannel : payChannel
		};
		$.ajax({
			type : "post",
			url : "${ctx}/loan/apply/updatePayChannel",
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
