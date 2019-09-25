<%@ page contentType="text/html;charset=UTF-8" %>
<form class="layui-form" action="">
    <%--绑定银行卡start--%>
    <fieldset class="layui-elem-field">
        <legend>绑定银行卡</legend>
        <div class="layui-field-box">
            <div class="layui-form-item">
                <label class="layui-form-label">真实姓名</label>

                <div class="layui-input-block">
                    <input type="text" name="realName" class="layui-input" value="{{data.realName}}"
                           readonly="readonly">
                </div>
            </div>
        </div>
        <div class="layui-field-box">
            <div class="layui-form-item">
                <label class="layui-form-label">开户银行类型</label>

                <div class="layui-input-block">
                    <input type="text" name="bankName" class="layui-input" value="{{data.bankName}}"
                           readonly="readonly">
                </div>
            </div>
        </div>
        <div class="layui-field-box">
            <div class="layui-form-item">
                <label class="layui-form-label">开户银行账户</label>

                <div class="layui-input-block">
                    <input type="text" name="accountId" class="layui-input" value="{{data.cardNo}}"
                           readonly="readonly">
                </div>
            </div>
        </div>
    </fieldset>
    <%--绑定银行卡end--%>
    <%--银行管存账户start--%>
    <fieldset class="layui-elem-field">
        <legend>银行管存账户</legend>
        <div class="layui-field-box">
            <div class="layui-form-item">
                <label class="layui-form-label">开户银行</label>

                <div class="layui-input-block">
                    <input type="text" name="" class="layui-input" value="恒丰银行股份有限公司总行营业部"
                           readonly="readonly">
                </div>
            </div>
        </div>
        <div class="layui-field-box">
            <div class="layui-form-item">
                <label class="layui-form-label">银行电子账号</label>

                <div class="layui-input-block">
                    <input type="text" name="accountId" class="layui-input" value="{{data.accountId}}"
                           readonly="readonly">
                </div>
            </div>
        </div>
    </fieldset>
    <%--银行管存账户end--%>
</form>
