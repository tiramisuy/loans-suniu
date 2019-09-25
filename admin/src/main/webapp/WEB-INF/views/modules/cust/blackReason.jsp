<%@ page contentType="text/html;charset=UTF-8"%>
<form class="breadcrumb  form-search">
	<div class="control-group">
			<div class="layui-input-block" style="margin-left: 50px;">
				<label>拉黑原因</label>	<input type="text" name="blackReason" id="blackReason">				
			</div>
	</div>
	
	<div class="layui-form-item mgt-30" style="margin-top: 20px;" align="center">
		<div class="layui-input-block" align="center">
			<button type="button" class="layui-btn"
				onclick="insertConfirm('{{custUserId}}')">确定</button>
			<button type="reset" class="layui-btn layui-btn-primary"
				onclick="layer.close(layer.index);">取消</button>
		</div>
	</div>
</form>

