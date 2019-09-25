<%@ page contentType="text/html;charset=UTF-8"%>
<form class="breadcrumb  form-search">
	<legend>预提醒</legend>
	<div class="control-group">
		<ul class="ul-form">
		
			<li><label>历史记录内容</label></li>
			<div class="layui-input-block">
				{{#each conarr}}
					{{addOne @index}}&nbsp;&nbsp;--&nbsp;&nbsp;{{this}}<br/>
				{{/each}}
			</div>
		
			<li><label>新增记录内容</label></li>
			<div class="layui-input-block">
				<textarea placeholder="记录内容共200字以内" class="layui-textarea" name="content" id="content" maxlength="200"></textarea>
			</div>
		</ul>
	</div>
	
	<div class="layui-form-item mgt-30">
		<div class="layui-input-block">
			<button type="button" class="layui-btn" onclick="warnConfirm('{{repayId}}','{{content}}')">提交</button>
			<button type="reset" class="layui-btn layui-btn-primary" onclick="layer.close(layer.index);">取消</button>
		</div>
	</div>
</form>

