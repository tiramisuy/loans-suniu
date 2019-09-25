<%@ page contentType="text/html;charset=UTF-8"%>
<legend>黑名单审核</legend>
<table class="table">
	<thead>
		<tr>
			<th>姓名</th>
			<th>手机号码</th>
			<th>证件号码</th>
			<th>拉黑原因</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>{{userRealName}}</td>
			<td>{{userMobile}}</td>
			<td>{{userIdNo}}</td>
			<td>{{reason}}</td>
		</tr>
</table>



<form class="breadcrumb  form-search" id="blackListReview">
	<input id="userId" name="userId" type="hidden" value="{{userId}}" /> <input
		id="id" name="id" type="hidden" value="{{id}}" />
	<legend>黑名单审核</legend>

	<div class="control-group">
		<ul class="ul-form">
			<li><label>审核结果</label></li>
			<div class="layui-input-block">
				<label for="radio1"
					style="width: 80px; height: 50px; display: block; line-height: 50px;float:left;"><input
					type="radio" name="status" value="1" id="radio1"> 通过</label><label
					for="radio2"
					style="width: 80px; height: 50px; display: block; line-height: 50px;float:left;"><input
					type="radio" name="status" value="2" id="radio2"> 不通过 </label>
			</div>
		</ul>
	</div>
	<div class="layui-form-item mgt-30">
		<div class="layui-input-block">
			<button type="submit" class="layui-btn">提交</button>
			<button type="reset" class="layui-btn layui-btn-primary"
				onclick="layer.close(layer.index);">取消</button>
		</div>
	</div>
</form>

