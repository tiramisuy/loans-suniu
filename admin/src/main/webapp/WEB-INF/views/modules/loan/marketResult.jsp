<%@ page contentType="text/html;charset=UTF-8"%>
<form class="breadcrumb  form-search">
	<div class="control-group">
			<div class="layui-input-block" style="margin-left: 50px;">
			<label><b>未接通</b></label>
				<input type="radio" class="layui-radio" name="markResult" value="2" id="noBody" style="margin-left: 20px;"><label for="noBody" style="margin-left: 5px;">无人接听</label>
				<br/>
				<br/>
			<label><b>接&nbsp;&nbsp;&nbsp;通</b></label>
				<input type="radio" class="layui-radio" name="markResult" value="4" id="toNeed" style="margin-left: 20px;"><label for="toNeed" style="margin-left: 5px;">需要</label> 
				<input type="radio" class="layui-radio" name="markResult" value="5" id="notNeed" style="margin-left: 20px;"><label for="notNeed" style="margin-left: 5px;">不需要</label> 
				<input type="radio" class="layui-radio" name="markResult" value="3" id="toThink" style="margin-left: 20px;"><label for="toThink" style="margin-left: 5px;">考虑</label> 
				<input type="radio" class="layui-radio" name="markResult" value="6" id="toOthers" style="margin-left: 20px;"><label for="toOthers" style="margin-left: 5px;">第三方接听</label> 
				<input type="radio" class="layui-radio" name="markResult" value="1" id="never" style="margin-left: 20px;"><label for="never" style="margin-left: 5px;">永久拒绝营销</label> 
				
				
				
			</div>
	</div>
	
	<div class="layui-form-item mgt-30" style="margin-top: 20px;">
		<div class="layui-input-block">
			<button type="button" class="layui-btn"
				onclick="confimMarketResult('{{marketVo.id}}')">确定</button>
			<button type="reset" class="layui-btn layui-btn-primary"
				onclick="layer.close(layer.index);">取消</button>
		</div>
	</div>
</form>

