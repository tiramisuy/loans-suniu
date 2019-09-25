<%@ page contentType="text/html;charset=UTF-8" %>

<form class="layui-form" action="">
   
    <%--认证管理start--%>
    <fieldset class="layui-elem-field">
        <legend>认证管理</legend>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
                <col width="50">
                <col width="150">
                <col width="150">
                <col width="150">
            </colgroup>
            <thead>
            <tr>
            	<th>序号</th>
                <th>认证项</th>
                <th>认证时间</th>
                <th>操作(删除后该项认证失效)</th>
            </tr>
            </thead>
            <tbody>
            {{#each data}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>
                	{{#equal stage 11}}
			    		身份证OCR认证
			    	{{/equal}}
	                {{#equal stage 12}}
			    		绑卡开通存管账户
			    	{{/equal}}
			    	{{#equal stage 13}}
			    		基本信息填写
			    	{{/equal}}
			    	{{#equal stage 14}}
			    		人脸识别认证
			    	{{/equal}}
			    	{{#equal stage 15}}
			    		运营商授权认证
			    	{{/equal}}
			    	{{#equal stage 16}}
			    		芝麻信用认证
			    	{{/equal}}
			    	{{#equal stage 162}}
			    		学信网认证
			    	{{/equal}}
			    	{{#equal stage 163}}
			    		淘宝认证
			    	{{/equal}}
	                {{#equal stage 164}}
			    		投复利代扣签约
			    	{{/equal}}
	                {{#equal stage 165}}
			    		信用卡认证
			    	{{/equal}}
		    	</td>
                <td>{{formatDate time 'yyyy-MM-dd HH:mm:ss'}}</td>
                <td>
			    	{{#equal stage 13}}
			    		<a href="javascript:void(0)" class="text-blue" onclick="delAuth('{{userId}}','{{status}}')">删除</a>
			    	{{/equal}}  
			    	{{#equal stage 14}}
			    		<a href="javascript:void(0)" class="text-blue" onclick="delAuth('{{userId}}','{{status}}')">删除</a>
			    	{{/equal}} 
			    	{{#equal stage 165}}
			    		<a href="javascript:void(0)" class="text-blue" onclick="delAuth('{{userId}}','{{status}}')">删除</a>
			    	{{/equal}} 
					{{#equal stage 15}}
			    		<a href="javascript:void(0)" class="text-blue" onclick="delAuth('{{userId}}','{{status}}')">删除</a>
			    	{{/equal}}  
			    	{{#equal stage 163}}
			    		<a href="javascript:void(0)" class="text-blue" onclick="delAuth('{{userId}}','{{status}}')">删除</a>
			    	{{/equal}}
			    	{{#equal stage 162}}
			    		<a href="javascript:void(0)" class="text-blue" onclick="delAuth('{{userId}}','{{status}}')">删除</a>
			    	{{/equal}}                  
                </td>
            </tr>
            {{/each}}
            </tbody>
        </table>


    </fieldset>
    <%--认证管理end--%>
</form>
