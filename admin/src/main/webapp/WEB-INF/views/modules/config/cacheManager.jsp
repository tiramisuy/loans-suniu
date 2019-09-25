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
  <a href="#">配置管理</a>
  <a><cite>缓存管理</cite></a>
</span>

<form class="layui-form" >
    <%--身份信息start--%>
    <fieldset class="layui-elem-field">
        <legend>清除REDIS缓存</legend>
        <div class="layui-field-box">

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">缓存key</label>

                    <div class="layui-input-inline">
                        <input type="text" name="key" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">匹配方式</label>
                    <div class="layui-input-inline">
                        <select name="type">
                            <option value="">请选择匹配方式</option>
                            <option value="1" >前匹配</option>
                            <option value="2">后匹配</option>
                            <option value="3">前后匹配</option>
                        </select>
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
    <fieldset class="layui-elem-field">
        <legend>清除EhCache缓存</legend>
        <div class="layui-field-box">

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">缓存name</label>

                    <div class="layui-input-inline">
                        <input type="text" name="name" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">缓存key</label>

                    <div class="layui-input-inline">
                        <input type="text" name="key1" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                        <div class="layui-input-block">
                            <button class="layui-btn" lay-submit="" lay-filter="demo1" onClick="return clearEhCache(this.form)">
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
            alert("请填写缓存key");
            return false;
        }
        if (form.type.value == null || form.type.value == '') {
            alert("请选择匹配方式");
            return false;
        }
        var param = {
            key: form.key.value,
            type: form.type.value
        };
        $.ajax({
            type: "post",
            url: "${ctx}/config/cache/clearCache",
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
    function clearEhCache(form) {
        if (form.key1.value == null || form.key1.value == '') {
            alert("请填写Ehcache缓存key");
            return false;
        }
        if (form.name.value == null || form.name.value == '') {
            alert("请填写Ehcache缓存name");
            return false;
        }
        var param = {
        	key: form.key1.value,
            name: form.name.value
        };
        $.ajax({
            type: "post",
            url: "${ctx}/config/cache/clearEhCache",
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