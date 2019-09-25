<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>借款人详情</title>

</head>
<link href="${ctxStatic}/position/basic.css" rel="stylesheet"/>
<link rel="stylesheet" href="${ctxStatic}/layui-master/build/css/layui.css" media="all">
<link href="${ctxStatic}/position/hjd-sh.css" rel="stylesheet"/>

<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic}/layui-master/src/layui.js"></script>

<body style="padding: 20px">
<span class="layui-breadcrumb">
  <a href="#">客户管理</a>
  <a><cite>短信缓存管理</cite></a>
</span>

<form class="layui-form" >
    <%--身份信息start--%>
    <fieldset class="layui-elem-field">
        <legend>清除短信缓存</legend>
        <div class="layui-field-box">

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">手机号</label>

                    <div class="layui-input-inline">
                        <input type="text" name="key" class="layui-input" maxlength="11" style="border:0.5px solid #378888">
                    </div>
                </div>
                <div class="layui-inline">
                        <div class="layui-input-block">
                            <button class="layui-btn" lay-submit="" lay-filter="demo1" onClick="return clearCache(this.form)">
                                提交
                            </button>
                        </div>

                </div>
            </div>
            <div class="layui-form-item" style="color: blue">
                <p id="clues"></p>
            </div>
        </div>
    </fieldset>
</form>
</body>
<script>
    $(function(){
        layui.use(['form', 'element'], function () {
            var $ = layui.jquery
                    , element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块

            var form = layui.form();
        });
    });

    function clearCache(form) {
        if (form.key.value == null || form.key.value == '') {
            alert("请填写手机号");
            return false;
        }
        var myreg=/^[1][3,4,5,6,7,8,9][0-9]{9}$/; 
        if (!myreg.test(form.key.value)) { 
        	alert("请填写正确手机号");
        	return false;　
        }
        var param = {
            key: form.key.value,
            type: "3"
        };
        $.ajax({
            type: "post",
            url: "${ctx}/config/cache/clearMsgCache",
            data: param,
            dataType: "json",
            error: function (request) {
                alert("系统繁忙,请稍后再试");
            },
            success: function (data, textStatus) {
                if (data.code == "1") {
                    $("#clues").text(data.data);
                } else {
                    alert(data.msg)
                }
            },

        });
        return false;
    }
</script>
</html>