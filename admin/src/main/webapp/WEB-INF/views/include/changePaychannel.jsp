<%@ page contentType="text/html;charset=UTF-8" %>
<div id="loanDay" type="text/x-handlebars-template">
    <div class="mgt-30" style="padding-top:20px;padding-left:60px">
	     <label>放款渠道：</label>
	     <select id="cgPaychannel" name="cgPaychannel" class="input-medium">
                <option value="4">乐视</option>
         </select>
            	     
    </div>
    <div style="width:20%;top:45px;margin-left:100px">
            <div style="width: 328px; margin: 28px;">
                <button class="collection-bot-left"  href="javascript:void(0)"align="center" style='width:80px;background-color: #33A6E7;color:#FFFFFF;' onclick="changePaychannel('{{id}}','{{applyId}}')">
                	提交
                </button>
            </div>      
    </div>
</div>
