<%@ page contentType="text/html;charset=UTF-8" %>
<div type="text/x-handlebars-template">
	<div class="mgt-30" style="padding-top:20px;padding-left:60px;">
	  <li>
	  	 <input type="file" id="upDateALIYUNfile" style=" float:left; width:220px;" />
       	 <input type="button" value="上传文件" onclick="upDateALIYUNFile('{{applyId}}')" style=" float:left;" />
      </li> 
    </div>
    {{#equal type 2}}
    <div style="text-align: center; margin-top:30px; color: #32CD32" >1.选择文件 2.点击上传文件 3.等确定按钮变为蓝色后,点击确定按钮完成更改</div>
    <div style="text-align: center; margin-top:30px;">
        <button id="enSureBtn" class="collection-bot-left upDateBtn" onclick="enSureUpload('{{applyId}}')" disabled="disabled" style="background: #a9a9a9;">确认</button>         
    </div>
    {{/equal}}
</div>
