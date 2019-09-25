<%@ page contentType="text/html;charset=UTF-8" %>

<style type="text/css">
    .align-center {
        margin: 0 auto;
        text-align: center;
    }
</style>

<form class="breadcrumb" id="delayForm">

    <input id="repayPlanItemId" name="repayPlanItemId" type="hidden" value="{{repayPlanItemId}}"/>
    <input id="type" name="type" type="hidden" value="{{type}}"/>
    <div class="align-center">
        <div class="control-group">
            <ul class="ul-form">
                <li><label>延期金额</label>
                    <input type="text" id="delayAmt" name="delayAmt" value="{{delayAmt}}" readonly="readonly"/>
                </li>
            </ul>
        </div>
        <div class="control-group">
            <ul class="ul-form">
                <li><label>延期时间</label>
                    <input id="delayTime" name="delayTime" value="" maxlength="20" type="text" readonly="readonly"
                           class="input-middle Wdate"
                           onfocus="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,onpicked:delayAmount,position:{left:0,top:-250}});">
                </li>
            </ul>
        </div>
        <div class="control-group">
	        <ul class="ul-form">
	            <li><label>支付渠道</label>
	            <select id="repayType" class="select">
			        <option value="">请选择支付渠道</form:option>
					<option value="alipaypub">支付宝对公</option>
					<option value="alipay">支付宝</option>
					<option value="weixin">微信</option>
					<option value="ccb">建设银行</option>
					<option value="boc">中国银行</option>
					<option value="icbc">工商银行</option>
			    </select></li>
	        </ul>
    	</div>
        <div class="control-group">
            <ul class="ul-form">
                <li><label>支付金额</label>
                    <input type="text" id="actualRepayAmt" name="actualRepayAmt"
                           class="{required:true,number:true,min:0.01}"/>
                </li>
            </ul>
        </div>
        <div class="layui-form-item">
            <button type="button" onclick="delayDealWithhold()" class="layui-btn">代扣延期</button>
            <button type="button" onclick="delayDeal()" class="layui-btn">手动延期</button>
            <button type="reset" class="layui-btn layui-btn-primary" onclick="layer.closeAll();">取消</button>
        </div>
    </div>
</form>

