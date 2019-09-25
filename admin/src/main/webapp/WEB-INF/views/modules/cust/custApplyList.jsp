<%@ page contentType="text/html;charset=UTF-8"%>
<table class="table">
	<thead>
		<tr>
			<th>合同编号</th>
			<th>贷款期限</th>
			<th>审批金额</th>
			<th>审批时间</th>
			<th>是否通过</th>
			<th>附件上传</th>
		</tr>
	</thead>
	<tbody>
		{{#each data}}
	    <tr>
	        <td>{{contractNo}}</td>
		  	<td>{{approveTerm}}</td>
		  	<td>{{approveAmt}}</td>
		  	<td>{{formatDate approveTime 'yyyy-MM-dd'}}</td>
	        <td>
				{{status}}
	        </td>
	        <td>
	        	{{#notEqual productId 'XJD' 'XJD'}}
	        	{{#if contractNo}}
	        	<a href="javascript:void(0)" class="text-blue"
                   onclick="upDateFile('{{id}}')" style="padding-left:10px;">附件上传</a>
	            <a href="javascript:void(0)" class="text-blue"
                   onclick="upDateChangeFile('{{id}}')" style="padding-left:10px;">附件更改</a>
		        {{/if}}
	            {{/notEqual}}
	        </td>
	    </tr>
	    {{/each}}
	</tbody>
</table>