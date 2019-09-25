<%@ page contentType="text/html;charset=UTF-8" %>

<style>

#repayForm .ul-form li{
	width:100%!important;
	height:auto!important
}
#repayForm label{
	margin-right:10px!important;
	margin-top:4px!important
} 
#repayForm input{
	margin-top:4px!important
} 

</style>

<form class="breadcrumb  form-search" id="configForm">
    <input id="id" name="id" type="hidden" value="{{id}}"/>
    
    {{#each data}}
    	{{#equal name ../key}}
   			<div class="control-group">
		        <ul class="ul-form">
					<label style="margin-left: 110px;">{{title}}</label>
		            <li style="width: 100%;height:auto;text-align: center">
		            <select id="value" class="select">
				        <option value="">请选择</option>
				        {{#each data}}
						<option value="{{value}}">{{text}}</option>
						{{/each}}
				    </select></li>
		        </ul>
		    </div> 
    	{{/equal}}
    {{/each}} 
    <div class="layui-form-item mgt-30">
        <div class="layui-input-block" style="margin-top : 6px">
            <button type="button" onclick="updateBasicConfig()"  class="layui-btn">提交</button>
            <button type="reset" class="layui-btn layui-btn-primary" onclick="layer.closeAll();">取消</button>
        </div>
    </div>
</form>

