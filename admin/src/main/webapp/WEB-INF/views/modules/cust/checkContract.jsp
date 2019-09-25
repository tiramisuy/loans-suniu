<%@ page contentType="text/html;charset=UTF-8"%>
<table class="table"  border="1"  style="width: 70%;margin:auto">
	<thead>
		<tr>
			<th>合同标号</th>
		</tr>
	</thead>
	<tbody>

			{{#each data}} 
			    <tr>
				  	<td><a href="https://www.51cunzheng.com/searchResult?r={{this}}" target="view_window">查看{{this}}合同</td>
			    </tr>
		    {{/each}}			
	
	</tbody>
</table>