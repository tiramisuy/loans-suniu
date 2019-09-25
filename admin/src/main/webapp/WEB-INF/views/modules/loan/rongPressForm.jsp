<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>客户管理</title>
    <style type="text/css">
        .text-blue {
            color: blue;
        }

        .required {
            color: red;
        }
        #promiseDateStr,#nextContactTimeStr{
            width: 170px;
            height: 30px;
            padding: 4px 6px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-top: 3px;
        }

        .add{
            width:59px;
            height: 28px;
            line-height: 28px;
            color: #fff;
            background: rgba(25, 158, 216, 1);
            text-align: center;
            font-size: 14px;
            cursor: pointer;
        }

        tr td input{
            width: 144px;
            height: 25px;
        }
        tr td select{
            width: 110px;
            height: 29px;

        }
        tr td span{
            display: inline-block;
            width: 50px;
            height: 20px;
            line-height: 20px;
            text-align: center;
            background: rgba(25, 158, 216, 1);
            cursor: pointer;
            color: #fff;
            font-size: 12px;
        }
        #relation{
            display: block;
        }
    </style>
    <link href="${ctxStatic}/position/basic.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctxStatic}/layui-master/build/css/layui.css" media="all">
    <link href="${ctxStatic}/position/hjd-sh.css" rel="stylesheet"/>

    <script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
    <script src="${ctxStatic}/layui-master/src/layui.js"></script>
    <script src="${ctxStatic}/layer/3.0/layer.js" type="text/javascript"></script>

    <script src="${ctxStatic}/handlebars/handlebars-v3.0.3.js" type="text/javascript"></script>
    <script src="${ctxStatic}/handlebars/helper.js" type="text/javascript"></script>

    <link href="${ctxStatic}/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
    <script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

</head>

<body>
<span class="layui-breadcrumb">
  <a href="${ctx}/loan/repay/pressList?stage=1">贷后管理</a>
  <a href="${ctx}/loan/repay/pressList?stage=1">催收管理</a>
  <a href="${ctx}/loan/repay/pressList?stage=1">逾期催收明细</a>
  <a><cite>催收详情</cite></a>
</span>

<div class="layui-tab layui-tab-card">
    <ul class="layui-tab-title">
    	 <li <c:if test="${page == 2}">class="layui-this"</c:if> onclick="detail1('${userId}','${applyId}',1,'${itemId}')">催收详情</li>
        <li <c:if test="${page == 1}">class="layui-this" </c:if> onclick="userInfo('${userId}','${applyId}', '1','${overday}')">客户信息</li>
         <c:if test="${overday > 1 }">
        <li onclick="rongContactConectInfo('${applyId}','${userId}')">手机通讯录</li>
        </c:if>
<%--          <c:if test="${succCount > 0 and overday > 14 }">
         <li onclick="rongContactConectInfo('${applyId}','${userId}')">手机通讯录</li>
         </c:if> --%>
        <%--<li onclick="getReportPage('${userIdNo}','${userRealName}','${userMobile}')">现金白卡报告</li>--%>
       <%--  <li <c:if test="${page == 2}">class="layui-this"</c:if> onclick="detail('${userId}','${applyId}',1,'${itemId}')">催收详情</li>
       		<li onclick="record()">催收记录</li>
          --%>


        <%--<li onclick="rongData('${applyId}')">融360运营商报告</li>--%>

    </ul>

    <div class="layui-tab-content" style="">
        <div id="tabDiv" class="layui-tab-item layui-show"/>
    </div>
</div>

</body>

<script id="tpl_userinfo" type="text/x-handlebars-template">
    <%--客户信息--%>
    <jsp:include page="../cust/rongUserInfo.jsp"></jsp:include>
</script>
<script id="tpl_8" type="text/x-handlebars-template">
    <%--通讯录信息--%>
    <jsp:include page="../cust/rongConnectInfo.jsp"></jsp:include>
</script>
<script id="tpl_detail_all" type="text/x-handlebars-template">
    <%--催收详情--%>
    <jsp:include page="collectionAllDetail.jsp"></jsp:include>
</script>
 <%--
<script id="tpl_record" type="text/x-handlebars-template">
    催收记录
    <jsp:include page="collectionRecord.jsp"></jsp:include>
</script>
--%>

<script>

    $(document).ready(function () {
       /*  detail('${userId}','${applyId}','','${itemId}'); */
        detail1('${userId}','${applyId}','','${itemId}');

      //  collectionRecord();
    });


    function rongContactConectInfo(applyId, userId) {
        var param = {
            applyId: applyId,
            userId: userId,
            flag: '${flag}',
            ctx: '${ctx}'
        };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/custUser/getRongConnectInfo",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_8").html());
                    var html = myTemplate(data);
                    document.getElementById("tabDiv").innerHTML = html;
                } else {
                    alert(data.msg);
                }
            },

        });
    }
    ;



    function rongData(applyId) {
        var param = {
            applyId: applyId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/apply/rongData",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate;
                    /* myTemplate = Handlebars.compile($("#tpl_16").html());
                    var html = myTemplate(data); */
                    document.getElementById("tabDiv").innerHTML = data.data;
                } else {
                    alert(data.msg);
                }
            },
        });
    }
    ;

    function doSubmit(){
        var type = $('#contactType').val()
        var contactId = $('#contactId').val()
        var result = $('#result').val()
        var content = $('#content').val()
        var promise = $('#promise').val()
        var promiseDateStr = $('#promiseDateStr').val()
        var nextContactTimeStr = $('#nextContactTimeStr').val()
        if (content == '') {
            alert("请填写催收内容");
            return false;
        }
        if (content.length > 50) {
            alert("催收内容不得超过50字");
            return false;
        }
/*         if ( type == '') {
            alert("请选择催收方式");
            return false;
        }
        if (contactId == '') {
            alert("请选择与本人关系");
            return false;
        }
        if (result == '') {
            alert("请选择催收结果");
            return false;
        }


        if (promise == '') {
            alert("请选择是否承诺付款");
            return false;
        }
        if (promise == '1' && promiseDateStr == '') {
            alert("请选择承诺付款时间");
            return false;
        }
        if (nextContactTimeStr == '') {
            alert("请选择下次跟进时间");
            return false;
        } */
        var param = {
            itemId: '${itemId}',
            contactId: contactId,
            type: type,
            result: result,
            content: content,
            promise: promise,
            promiseDateStr: promiseDateStr,
            nextContactTimeStr: nextContactTimeStr
        };
        $(".layui-btn").attr({"disabled":"disabled"});
        $.ajax({
            type: "post",
            url: "${ctx}/loan/collection/saveRecord",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
                $(".layui-btn").removeAttr("disabled");
            },
            success: function (data) {
                if (data.code == "1") {

                    alert(data.msg);
                    $(".layui-btn").removeAttr("disabled");
                  /*  	detail('${userId}','${applyId}',1); */

                   	detail1('${userId}','${applyId}',1);


                } else {
                	$(".layui-btn").removeAttr("disabled");
                    alert(data.msg)
                }
            }
        });
        return false;
    };

    function userInfo(id, applyId, sign,overday) {
        var param = {
            id: id,
            applyId: applyId,
            sign: sign
        };
        $.ajax({
            type: "post",
            url: "${ctx}/sys/custUser/rongUserDetail",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
            	data.data.overday = overday;
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_userinfo").html());
                    var html = myTemplate(data.data);
                    document.getElementById("tabDiv").innerHTML = html
                } else {
                    alert(data.msg)
                }

            },

        });
    };

    function detail(id, applyId,num,thisTermId) {
        if(num==1){
            location.replace(location.href)
            return false;
        }
        var param = {
            userId: id,
            applyId: applyId,
            thisTermId: thisTermId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/collection/detail",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_detail").html());
                    var html = myTemplate(data.data);
                    document.getElementById("tabDiv").innerHTML = html

                    timer();

                } else {
                    alert(data.msg)
                }

            },

        });
    };

    function detail1(id, applyId,num,thisTermId) {
        if(num==1){
            location.replace(location.href)
            return false;
        }
        var param = {
            userId: id,
            applyId: applyId,
            thisTermId: thisTermId
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/collection/detail",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_detail_all").html());
                    var html = myTemplate(data.data);
                    document.getElementById("tabDiv").innerHTML = html

                    timer();

                } else {
                    alert(data.msg)
                }

            },

        });
    };


    function collectionRecord() {
        var param = {
            itemId: '${itemId}'
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/collection/list",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_detail_all").html());
                    var html = myTemplate(data.data);
                   document.getElementById("collectionRecord").innerHTML = html
                   timer();
                } else {
                    alert(data.msg)
                }

            },

        });
    };






    function record() {
        var param = {
            itemId: '${itemId}'
        };
        $.ajax({
            type: "post",
            url: "${ctx}/loan/collection/list",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    var myTemplate = Handlebars.compile($("#tpl_record").html());
                    var html = myTemplate(data.data);
                    document.getElementById("tabDiv").innerHTML = html
                } else {
                    alert(data.msg)
                }

            },

        });
    };
    var flag=true;
    function add() {
        if (flag) {
            var html ='';
            html +='<tr data-id="4"><td></td>';
            html +='<td><input  class="add_name" maxlength="11" type="text"></td>';
            html +='<td><input  class="add_phone" maxlength="11"  type="text"></td>'
            html +='<td><select  id="relation"><option>--请选择--</option><option>本人</option><option>父母</option><option>朋友</option><option>配偶</option><option>同事</option><option>其他联系人</option></select></td>';
            html +='<td><span onclick="preservation()">保存</span>';
            $("table").append(html);
            flag=false;
        }else{
            alert("您有未保存的添加记录哦")
        }
    }


    //保存
    function preservation(){
        if($("input.add_name").val()==""){
            alert("请填写联系人姓名");
            return false;
        }
        if($("input.add_phone").val()==""){
            alert("请填写联系人联系方式");
            return false;
        }
        if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test($("input.add_phone").val()))){
            alert("手机号不正确");
            return false;
        }
        if($("#relation").val()=="--请选择--"){
            alert("请选择关系")
            return false;
        }
        var data={
            userId:'${userId}',
            applyId:'${applyId}',
            name:$("input.add_name").val(),
            mobile:$("input.add_phone").val(),
            relation:$("#relation").val(),
            source:2
        }
        console.log(data)
        $.ajax({
            url: "${ctx}/sys/custUser/saveCollectionContact",
            type: 'post',
            data: data,
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    flag=true;
                    userInfo('${userId}','${applyId}', '1',1);
                } else {
                    alert(data.msg)
                }

              //  collectionRecord();	//保存成功刷新催收记录

            },
        });
    }

    //删除

    function remove(e){
        var id=$(e).parents("tr").attr("data-id");
        $.ajax({
            url: "${ctx}/sys/custUser/deleteCollectionContact",
            type: 'post',
            data: {id:id},
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    userInfo('${userId}','${applyId}', '1',1);
                } else {
                    alert(data.msg)
                }

            },
        });
    }

    function timer(){
        layui.use(['form', 'element'], function () {
            var $ = layui.jquery
                    , element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块

            var form = layui.form();
            form.on('select(type)', function(data) {
                if(data.value == '1'){
                    $('#promiseDateDiv').show();
                }else{
                    $('#promiseDateDiv').hide();
                }
            });
        });
    }
</script>
</html>