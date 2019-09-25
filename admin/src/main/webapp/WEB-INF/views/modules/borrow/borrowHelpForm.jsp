<%@ page contentType="text/html;charset=UTF-8"%>
<legend>助贷记录</legend>
<table class="table">
	<thead>
		<tr>
			<th>姓名</th>
			<th>手机号码</th>
			<th>申请时间</th>
			<th>来源</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>{{userName}}</td>
			<td>{{mobile}}</td>
			<td>{{applyTime}}</td>
			<td>
				{{source}}
			</td>
		</tr>
</table>



		
		  <fieldset class="layui-elem-field">
		      <legend>历史营销记录</legend>
		      <table class="layui-table mgt-20">
		        <colgroup>
		          <col width="3%">
		          <col width="10%">
		          <col width="10%">
		          <col width="10%">
		          <col width="10%">
		          <col width="10%">
		          <col width="10%">
		          <col width="10%">
		        </colgroup>
		        <thead>
		        <tr>
		          <th>编号</th>
		          <th>借款公司</th>
		          <th>借款金额</th>
		          <th>成功放款金额</th>
		          <th>来源</th>
		          <th>交易状态</th>
		         <!--  <th>备注</th> -->
		           <th>时间</th>
		        </tr>
		        </thead>
		        <tbody>
		        {{#each resultList}}
		        <tr>
		          <td>{{addOne @index}}</td>
		          <td>{{loanCompany}}</td>
		          <td>{{loanAmt}}</td>
		          <td>{{succAmt}} </td>
		          <td>{{source}}</td>
		          <td>
		          		{{#equal status 0}}
		            		未放款
			            {{/equal}}
			            {{#equal status 1}}
			            	已放款
			            {{/equal}}
		          </td>
		      <!--     <td>{{remark}}</td> -->
		          <td>{{formatDate createTime "yyyy-MM-dd HH:mm:ss"}}</td>
		         <!--  <td>
		            {{#equal promise 0}}
		            否
		            {{/equal}}
		            {{#equal promise 1}}
		            是
		            {{/equal}}
		          </td>
		          <td>{{formatDate promiseDate "yyyy-MM-dd"}}</td>
		          <td>{{formatDate nextContactTime "yyyy-MM-dd HH:mm:ss"}}</td>
		        </tr> -->
		        {{/each}}
		
		        </tbody>
		      </table>
		    </fieldset>


       
	
<form class="breadcrumb  form-search">
	<legend>助贷记录</legend>
	
	<div class="align-center">
	 <div class="control-group">
              <label style="width: 100px;">借款公司:</label>
                    <input type="text" style="margin-left: 50px;" id="company" name="company" value="{{company}}"/>
        </div>
	
	 <div class="control-group">
                <label  style="width: 100px;">借款金额:</label>
                    <input type="text" style="margin-left: 50px;" id="borrowAmt" name="borrowAmt" value="{{borrowAmt}}"/>
        </div>
        <div class="control-group">
                <label style="width: 100px;">成功放款金额:</label>
                    <input type="text" style="margin-left: 50px;"  id="giveAmt" name="giveAmt" value="{{giveAmt}}"/>
        </div>
             <div class="control-group">
                <label style="width: 100px;">来源:</label>
                    <input type="text"  style="margin-left: 50px;"  id="source" name="source"/>
        </div>
        <div class="control-group">
                <label style="width: 100px;">交易状态:</label>
                     <select id="allotStatus" name="allotStatus" class="select" style="margin-left: 50px;">
							<option value="1">已放款</option>
							<option value="0">未放款</option>
			    	</select>
        </div>
     <!--    <div class="control-group">
            <ul>
                <li><label>延期时间</label>
                    <input id="delayTime" name="delayTime" value="" maxlength="20" type="text" readonly="readonly"
                           class="input-middle Wdate"
                           onfocus="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,onpicked:delayAmount,position:{left:0,top:-250}});">
                </li>
            </ul>
        </div> -->
		
	
	<div class="layui-form-item mgt-30">
		<div class="layui-input-block">
			<button type="button" class="layui-btn"
				onclick="BorrowConfirm('{{borrowId}}','{{mobile}}')">提交</button>
			<button type="reset" class="layui-btn layui-btn-primary"
				onclick="layer.closeAll();">取消</button>
		</div>
	</div>
	
	</div>
</form>

