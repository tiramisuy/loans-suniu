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
                <input type="text" id="totalAmount" name="totalAmount" value="{{totalAmount}}" readonly="readonly"/> 元
            </li>
        </ul>
    </div>
    <div class="control-group">
        <ul class="ul-form">
            <li><label>已还金额</label>
                <input type="text" id="currActualRepayAmt" name="currActualRepayAmt" value="{{currActualRepayAmt}}" readonly="readonly"/> 元

            </li>
        </ul>
    </div>
    <div class="control-group">
        <ul class="ul-form">
            <li><label>剩余应还金额</label>
                <input type="text" id="currRepayAmt" name="currRepayAmt" value="{{currRepayAmt}}" readonly="readonly"/> 元
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
            <li><label>实还金额</label>
                <input type="text" id="actualRepayAmt" name="actualRepayAmt" class="{required:true,number:true,min:100}" /> 元
            </li>
        </ul>
    </div>
    <div class="layui-form-item mgt-10">
        <div class="layui-input-block" style="margin-top : 6px">
            <button type="button" onclick="processAdminWithhold()"  class="layui-btn">提交</button>
            <button type="reset" class="layui-btn layui-btn-primary" onclick="layer.closeAll();">取消</button>
        </div>
    </div>
</form>
