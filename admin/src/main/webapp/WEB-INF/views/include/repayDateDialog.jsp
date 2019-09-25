<%@ page contentType="text/html;charset=UTF-8" %>
<div id="loanDay" type="text/x-handlebars-template">
    <div class="mgt-30" style="padding-top:20px;padding-left:60px">
	     <label>放款时间</label>
	     <input id="loantime" name="loantime" value="{{date}}" maxlength="20" type="text" readonly="readonly" class="input-middle Wdate" onfocus="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,position:{left:100,top:5}});">
    </div>
    <div style="width:20%;top:45px;margin-left:100px">
            <div style="width: 328px; margin: 28px;">
                <button class="collection-bot-left"  href="javascript:void(0)"align="center" style='width:80px;background-color: #33A6E7;color:#FFFFFF;' onclick="changeRepayDate('{{applyId}}')">
                	提交
                </button>
            </div>
            
            
            
    </div>
</div>
