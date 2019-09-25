<%@ page contentType="text/html;charset=UTF-8" %>

<form class="breadcrumb  form-search" id="deductionRepayDetailFrom">
	<input id="repayPlanDetailItemId" name="repayPlanDetailItemId" type="hidden" value="{{repayPlanDetailItemId}}"/>
 
	<div align="center">
		<label>应还金额</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="totalDetailAmount" name="totalDetailAmount" value="{{totalDetailAmount}}" readonly="readonly">
	</div>
	<br>
	<div align="center">
		<label>当前减免金额</label>&nbsp;&nbsp;&nbsp;<input type="text" id="lastDeductionDetailAmt" name="lastDeductionDetailAmt" value="{{lastDeductionDetailAmt}}" readonly="readonly">
	</div>
	<br>
	<div align="center">
		<label>重新减免金额</label>&nbsp;&nbsp;&nbsp;<input type="text" id="deductionDetailAmt" name="deductionDetailAmt" value="">
	</div>
	<br>
	<div align="center">
		<label>减免理由</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="deductionReason" name="deductionReason" value="">
	</div>
	<br>
	<br>
    <div align="center">
        <button type="button" onclick="ensureDeduction()"  class="layui-btn">提交</button>
        <button type="reset" class="layui-btn layui-btn-primary" onclick="layer.closeAll();">取消</button>
    </div>
</form>