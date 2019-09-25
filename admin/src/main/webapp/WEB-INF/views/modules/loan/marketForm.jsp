<%@ page contentType="text/html;charset=UTF-8"%>
<legend>营销提醒</legend>
<table class="table">
	<thead>
		<tr>
			<th>姓名</th>
			<th>手机号码</th>
			<th>申请时间</th>
			<th>审批金额</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>{{userName}}</td>
			<td>{{mobile}}</td>
			<td>{{applyTime}}</td>
			<td>
				{{approveAmt}}
			</td>
		</tr>
</table>


	
<form class="breadcrumb  form-search">
	<legend>营销提醒</legend>
	<div class="control-group">
		<ul class="ul-form">
		
			<li><label>历史提醒内容</label></li>
			<div class="layui-input-block">
			 {{#each conarr}}
			{{addOne @index}} {{this}}	<br>
			{{/each}}
				
			</div>
		
			<li><label>提醒内容</label></li>
			<div class="layui-input-block">
				<textarea placeholder="记录内容共200字以内" class="layui-textarea" name="content"
					id="content" maxlength="200"></textarea>
			</div>
		</ul>
	</div>
	
	<div class="layui-form-item mgt-30">
		<div class="layui-input-block">
			<button type="button" class="layui-btn"
				onclick="marketConfirm('{{marketVo.id}}','{{marketVo.content}}')">提交</button>
			<button type="reset" class="layui-btn layui-btn-primary"
				onclick="layer.close(layer.index);">取消</button>
		</div>
	</div>
</form>

