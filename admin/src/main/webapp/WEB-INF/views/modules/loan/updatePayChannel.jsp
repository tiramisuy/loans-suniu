<%@ page contentType="text/html;charset=UTF-8"%>
<form class="breadcrumb  form-search">
	<div class="control-group">
			<div class="layui-input-block" style="margin-left: 50px;">
				<label>放款渠道</label>
					<select name="pChannel" id="pChannel">
						<!-- <option value="3">口袋存管</option> -->
						<option value="4">乐视</option>
						<!-- <option value="5">汉金所</option> -->
					</select>
							
			</div>
	</div>
	
	<div class="layui-form-item mgt-30" style="margin-top: 20px;" align="center">
		<div class="layui-input-block" align="center">
			<button type="button" class="layui-btn"
				onclick="updatePayChannel('{{applyId}}')">确定</button>
			<button type="reset" class="layui-btn layui-btn-primary"
				onclick="layer.close(layer.index);">取消</button>
		</div>
	</div>
</form>

