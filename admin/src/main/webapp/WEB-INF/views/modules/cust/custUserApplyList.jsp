<%@ page contentType="text/html;charset=UTF-8"%>
<table class="table">
	<thead>
		<tr>
			<th>贷款期限</th>
			<th>贷款产品</th>
			<th>审批金额</th>
			<th>审批时间</th>
			<th>申请状态</th>
		</tr>
	</thead>
	<tbody>
		{{#each data}}
	    <tr>
		  	<td>{{approveTerm}}</td>
		  	<td>{{productId}}</td>
		  	<td>{{approveAmt}}</td>
		  	<td>{{formatDate approveTime 'yyyy-MM-dd'}}</td>
	        <td>
		        {{status}}
	        </td>
	    </tr>
	    {{/each}}
	</tbody>
</table>