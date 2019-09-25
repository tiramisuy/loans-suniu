<%@ page contentType="text/html;charset=UTF-8" %>

<style>

#repayForm .ul-form li{
	width:100%!important;
	height:auto!important
}
#repayForm label{
	margin-right:10px!important;
	margin-top:4px!important
} 
#repayForm input{
	margin-top:4px!important
} 

</style>

<form class="breadcrumb  form-search" id="repayForm">
    <input id="repayPlanItemId" name="repayPlanItemId" type="hidden" value="{{repayPlanItemId}}"/>
    <input id="type" name="type" type="hidden" value="{{type}}"/>
   
    <div class="control-group">
        <ul class="ul-form">
            <li><label>应还金额</label>
                <input type="text" id="totalAmount" name="totalAmount" value="{{totalAmount}}" readonly="readonly"/>
            </li>
        </ul>
    </div>
    <div class="control-group">
        <ul class="ul-form">
            <li><label>已还金额</label>
                <input type="text" id="currActualRepayAmt" name="currActualRepayAmt" value="{{currActualRepayAmt}}" readonly="readonly"/>
            </li>
        </ul>
    </div>
    <div class="control-group">
        <ul class="ul-form">
            <li><label>剩余应还金额</label>
                <input type="text" id="currRepayAmt" name="currRepayAmt" value="{{currRepayAmt}}" readonly="readonly"/>
            </li>
        </ul>
    </div>
    
    {{#equal type 3}}
    <div class="control-group">
        <ul class="ul-form">
            <li><label>提前手续费</label>
                <input type="text" id="prepayFee" name="prepayFee" value="" onBlur="restAmount()"/>
            </li>
        </ul>
    </div>
    <div class="control-group">
        <ul class="ul-form">
            <li><label>减免金额</label>
                <input type="text" id="deductionAmt" name="deductionAmt" value="" onBlur="restAmount()"/>
            </li>
        </ul>
    </div>
    {{/equal}}
    
    {{#equal type 4}}
    <div class="control-group">
        <ul class="ul-form">
            <li><label>提前手续费</label>
                <input type="text" id="prepayFee" name="prepayFee" value="" onBlur="restAmount()"/>
            </li>
        </ul>
    </div>
    <div class="control-group">
        <ul class="ul-form">
            <li><label>减免金额</label>
                <input type="text" id="deductionAmt" name="deductionAmt" value="" onBlur="restAmount()"/>
            </li>
        </ul>
    </div>
    {{/equal}}
    
    <div class="control-group">
        <ul class="ul-form">
            <li><label>实还时间</label>
                <input id="actualRepayTime" name="actualRepayTime" value="" maxlength="20" type="text" readonly="readonly" class="input-middle Wdate" onfocus="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,onpicked:restAmount,position:{left:0,top:-250}});">
            </li>
        </ul>
    </div>
    <div class="control-group">
        <ul class="ul-form">
            <li><label>还款渠道</label>
            <select id="repayType" class="select">
		        <option value="">请选择支付渠道</option>
				<option value="alipaypub">支付宝对公</option>
				<option value="alipay">支付宝</option>
				<option value="weixin">微信</option>
				<option value="tonglian">通联支付</option>
				<option value="ccb">建设银行</option>
				<option value="boc">中国银行</option>
				<option value="icbc">工商银行</option>
				<option value="hkb">汉口银行</option>
				<option value="BAOFOO">宝付支付</option>
                <option value="XIANFENG">先锋支付</option>
                {{#equal type 4}}
                	<option value="HENGFENG">恒丰银行</option>
                {{/equal}}
		    </select></li>
        </ul>
    </div>
	<div class="control-group">
        <ul class="ul-form">
            <li><label>实还金额</label>
                <input type="text" id="actualRepayAmt" name="actualRepayAmt" class="{required:true,number:true,min:0.01}" />
            </li>
        </ul>
    </div>
    <div class="layui-form-item mgt-30">
        <div class="layui-input-block" style="margin-top : 6px">
            <button type="button" onclick="processAdminWithhold()"  class="layui-btn">提交</button>
            <button type="reset" class="layui-btn layui-btn-primary" onclick="layer.closeAll();">取消</button>
        </div>
    </div>
</form>

