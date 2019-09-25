<%@ page contentType="text/html;charset=UTF-8" %>
<div id="loanDay" type="text/x-handlebars-template">
    <div class="mgt-30" style="padding-top:20px;padding-left:60px">
	  <li>
	     <label>放款时间</label>
	     <input id="loantime" name="loantime" value="{{date}}" maxlength="20" type="text" readonly="readonly" class="input-middle Wdate" onfocus="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,position:{left:100,top:5}});">
      </li> 
    </div>
    <div style="width:20%;top:45px;margin-left:100px">
            <div style="width: 328px; margin: 28px;">
                <div class="collection-bot-left" style='width:80px' onclick="popupFrame('{{applyId}}')">提交</div>
            </div>
    </div>
</div>
