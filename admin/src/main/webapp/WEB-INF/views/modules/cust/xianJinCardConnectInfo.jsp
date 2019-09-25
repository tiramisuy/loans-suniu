<%@ page contentType="text/html;charset=UTF-8" %>

<form id="xianjinContactConnectionInfo" class="layui-form" action="">
   
    <%--通讯录联系人通讯信息start--%>
    <fieldset class="layui-elem-field">
        <legend>联系人通讯录统计（按通话时长排序）</legend>
        <table style="display:inline;" class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
                <col width="68">
                <col width="129">
                <col width="99">
                <col width="99">
                <col width="99">
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>姓名</th>
                <th>手机号码</th>
                <th>通话次数</th>
                <th>通话时长（秒）</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.data1}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>{{name}}</td>
                <td>{{mobile}}</td>
                <td>{{call_cnt}}</td>
                <td>{{call_len}}</td>
            </tr>
            {{/each}
            </tbody>
        </table>
        
        <table style="display:inline;margin-left:50px" class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
                <col width="68">
                <col width="129">
                <col width="99">
                <col width="99">
                <col width="99">
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>姓名</th>
                <th>手机号码</th>
                <th>通话次数</th>
                <th>通话时长（秒）</th>
            </tr>
            </thead>
            <tbody>
            {{#each data.data3}}
            <tr data-id="{{id}}">
                <td>{{addOne index}}</td>
                <td>{{name}}</td>
                <td>{{mobile}}</td>
                <td>{{call_cnt}}</td>
                <td>{{call_len}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>


    </fieldset>
    <%--通讯录联系人通讯end--%>
</form>
<%--通讯录上传 --%>
<div class="layui-form-item">
	<label class="layui-form-label">通讯录文件上传</label>
	<div class="layui-input-inline" style=" width:480px; height: 28px; padding: 5px; margin-bottom: 0;">
			<input type="file" id="ContactFile" multiple="multiple" style=" float:left; width:180px;" />
	     	<input type="button" id="uploadContactFile" value="上传" style=" float:left;" onclick="uploadContactFile('${vo.userInfoVO.id}')"/>
	     	<span style="font-size:12px;color:red">&nbsp;&nbsp;支持类型为xls|xlsx|csv|pdf|jpg|jpeg|png文件上传</span>
	</div>
</div>

<%--通讯录文件下载 --%>
{{#if data.data2}}
<div class="layui-field-box pd-10 hidden">
    <dl class="zj-dl">
        <dt>通讯录文件下载: </dt>
        <dd>
            {{#each data.data2}}
			{{#equal bizCode 'contact_file'}}
	        	<div>{{origName}}&nbsp;&nbsp;&nbsp;<a href="{{url}}" style="color: blue;" target="_blank">下载</a>&nbsp;&nbsp;&nbsp;&nbsp;<a style="color:red; width:220px;" onclick="deleteContactFile('{{id}}')">删除</a></div>
	        {{/equal}}
	        {{/each}}
        </dd>
    </dl>
</div>
{{/if}}
