<%@ page contentType="text/html;charset=UTF-8" %>

<form class="layui-form" action="">
    <%--资金情况start--%>
    <fieldset class="layui-elem-field">
        <legend>资金情况</legend>
        <div class="layui-field-box">
            <div class="layui-form-item">
                <label class="layui-form-label">账户总额（元）</label>

                <div class="layui-input-block">
                    <input type="text" name="currBal" value="{{data.currBal}}" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-field-box">
            <div class="layui-form-item">
                <label class="layui-form-label">可用余额（元）</label>

                <div class="layui-input-block">
                    <input type="text" name="availBal" value="{{data.availBal}}" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-field-box">
            <div class="layui-form-item">
                <label class="layui-form-label">冻结金额（元）</label>

                <div class="layui-input-block">
                    <input type="text" name="freezeBal" value="{{data.freezeBal}}" class="layui-input">
                </div>
            </div>
        </div>
    </fieldset>
    <%--资金情况end--%>
</form>

