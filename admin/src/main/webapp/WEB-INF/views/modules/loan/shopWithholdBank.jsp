<%@ page contentType="text/html;charset=UTF-8"%>
	
<form class="breadcrumb  form-search">
	<div class="control-group">
			<label>还款渠道</label>
	            <select id="repayType" class="select">
			        <option value="">请选择支付渠道</option>
					<option value="alipaypub">支付宝对公</option>
					<option value="alipay">支付宝</option>
					<option value="weixin">微信</option>
					<option value="ccb">建设银行</option>
					<option value="boc">中国银行</option>
					<option value="icbc">工商银行</option>
					{{#equal operateType 1}}
					<option value="HENGFENG">恒丰银行</option>
					{{/equal}}	
			    </select>
	</div>
	
	<div class="layui-form-item mgt-30">
		<div class="layui-input-block">
		
		{{#equal operateType 0}}
          	 <button type="button" class="layui-btn" onclick="commitUnderLine('{{applyId}}')">确定</button>
        {{/equal}}
        {{#equal operateType 1}}
        	 <button type="button" class="layui-btn" onclick="commitCansel('{{applyId}}')">确定</button>
        {{/equal}}
		
			
			<button type="reset" class="layui-btn layui-btn-primary" onclick="layer.closeAll();">取消</button>
		</div>
	</div>
</form>

