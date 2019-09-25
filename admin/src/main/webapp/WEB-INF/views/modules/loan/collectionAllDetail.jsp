<%@ page contentType="text/html;charset=UTF-8" %>


<%--催收详情--%>

<form id="detail" class="layui-form" action="">
  <%--联系人信息start--%>
  <fieldset class="layui-elem-field">
    <legend>逾期记录</legend>
    <table id="detail_table" class="layui-table" lay-even="" lay-skin="row">
      <colgroup>
      </colgroup>
      <thead>
      <tr>
        <th>合同编号</th>
        <th>借款期限</th>
        <th>应还本金（元）</th>
        <th>应还利息（元）</th>
        <th>已借期限</th>
        <th>逾期天数</th>
        <th>逾期利息（元）</th>
        <th>逾期管理费</th>
        <th>分期服务费</th>
        <th>减免金额（元）</th>
        <th>应还总额（元）</th>
        <th>实还总额（元）</th>
        <th>差额（元）</th>
        <th>借款日期</th>
        <th>应还日期</th>
        <th>实还日期</th>
  <!--       <th>历史逾期次数</th>
        <th>历史最长逾期天数</th> -->
        <th>还款状态</th>
        <th>标签</th>
      </tr>
      </thead>
      <tbody>
      <%--<c:forEach var="item" items="${detailList}" varStatus="xh">--%>
      {{#each detailList}}
        <tr>
          <td>{{contNo}}</td>
          <td>{{approveTerm}}天</td>
          <td>{{principal}}</td>
          <td>{{interest}}</td>
          <td>{{borrow}}天</td>
          <td>{{overdue}}天</td>
          <td>{{penalty}}</td>
          <td>{{overdueFee}}</td>
          <td>{{termServFee}}</td>
          <td>{{deduction}}</td>
          <td>{{totalAmount}}</td>
          <td>{{actualRepayAmt}}</td>
          <td>{{subAmt}}</td>
          <td>{{formatDate loanStartDate "yyyy-MM-dd HH:mm:ss"}}</td>
          <td>{{formatDate repayDate "yyyy-MM-dd"}}</td>
          <td>{{formatDate actualRepayTime "yyyy-MM-dd HH:mm:ss"}}</td>
       <!--    <td>{{overdueTimes}}</td>
          <td>{{maxOverdueDays}}</td> -->
          <td>
            {{#equal status 1}}
            <div class="text-blue">已还款</div>
            {{/equal}}
            {{#equal status 0}}
            <div class="required">待还款</div>
            {{/equal}}
          </td>
          <td>
            {{#equal sign 0}}
            <div>正常</div>
            {{/equal}}
            {{#equal sign 1}}
            <div class="text-blue">提前</div>
            {{/equal}}
            {{#equal sign 2}}
            <div class="required">逾期</div>
            {{/equal}}
          </td>
        </tr>
      {{/each}}
      <%--</c:forEach>--%>
      </tbody>
    </table>
  </fieldset>

  <fieldset class="layui-elem-field" style="width:43%;float: left;">
    <legend>催收办理</legend>
    <form id="submitForm"  class="layui-form" >

      <div class="layui-form-item" style="width: 100%;">
        <!-- <div class="layui-inline" style="width:1150px"> -->
        <div class="layui-inline" style="width:100%;" align="center"> 
          <div class="layui-form-item" >
          	<div class = "collection_div">
          		<label class="layui-form-label">催收方式</label>
	            <div class="layui-input-inline">
	              <select id="contactType">
	                <option value="">--请选择--</option>
	                <option value="1">电话</option>
	                <option value="2">短信</option>
	                <option value="3">其他</option>
	              </select>
	            </div>
          	</div>
            <div class = "collection_div">
	            <label class="layui-form-label">与本人关系</label>
	            <div class="layui-input-inline">
	              <select id="contactId"  class="input-medium">
	                <option value="">--请选择--</option>
	                {{#each contactList}}
	                  <option value="{{id}}">{{detail}}</option>
	                {{/each}}
	                  <option value="00">其他</option>
	
	                <%--<c:forEach items="${contactList}" var="detail">--%>
	                  <%--<option value="${detail.id}">${detail.detail}</option>--%>
	                <%--</c:forEach>--%>
	              </select>
	            </div>
            </div>
            <div class = "collection_div">
            	<label class="layui-form-label">催收结果</label>
	            <div class="layui-input-inline">
	              <select id="collection_result"  class="input-medium">
	                <option value="">--请选择--</option>
	                {{#each resultList}}
	                <option value="{{value}}">{{value}}-{{desc}}</option>
	                {{/each}}
	                <%--<c:forEach items="${resultList}" var="detail">--%>
	                  <%--<option value="${detail.value}">${detail.value}-${detail.desc}</option>--%>
	                <%--</c:forEach>--%>
	              </select>
	            </div>
            </div>
          </div>

          <div class="layui-form-item layui-form-text">
            <label class="layui-form-label"><em class="required">*</em>催收内容</label>

            <div class="layui-input-block">
              <textarea placeholder="请输入内容" class="layui-textarea" id="collection_content"></textarea>
            </div>
          </div>
          <div class="layui-form-item" style="width: 100%">

            <label class="layui-form-label">是否承诺付款</label>

            <div class="layui-input-inline">
              <select id="promise" lay-filter="type">
                <option value="">--请选择--</option>
                <option value="1">是</option>
                <option value="0">否</option>
              </select>
            </div>
           </div>
          <div class="layui-form-item" style="width: 100%">
            <div id="promiseDateDiv">
              <label class="layui-form-label">承诺付款时间</label>

              <div class="layui-input-inline">
                <input id="promiseDateStr" type="text" readonly="readonly" maxlength="20"
                       class="input-middle Wdate"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
              </div>
            </div>
           </div> 
         <div class="layui-form-item" style="width: 100%">
            <label class="layui-form-label">下次跟进时间</label>

            <div class="layui-input-inline">
              <input id="nextContactTimeStr" type="text" readonly="readonly" maxlength="20"
                     class="input-middle Wdate"
                     onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
            </div>
            	<button class="layui-btn" lay-submit="" lay-filter="demo1" onClick="return doSubmit()" style="margin-top: 10px;">提交</button>
           
         </div>
           
        </div>

      </div>
    </form>
  </fieldset>
  </form>
  
  
  
  
  
  <!-- 手机通讯录 -->
<form class="layui-form" action="">
   
    <%--通讯录联系人通讯信息start--%>
    <fieldset  class="layui-elem-field" style="width:50%;height:458px;float: left;margin-left:60px;padding-left: 50px;">
        <legend>联系人通讯录统计（按通话时长排序）</legend>
        <table style="display:inline;float: left;width: 80%;" class="layui-table" lay-even="" lay-skin="row">
            <thead>
            <tr>
                <th width="100">序号</th>
                <th width="100">姓名</th>
                <th width="100">手机号码</th>
                <th width="100">通话次数</th>
                <th width="100">通话时长（秒）</th>
            </tr>
            </thead>
            <tbody style="height:335px;width:100%;overflow: auto;display: block;">
            {{#if showContact}}
	            {{#each contactConnect}}
	            <tr data-id="{{id}}">
	                <td width="100">{{addOne @index}}</td>
	                <td width="100">{{name}}</td>
	                <td width="100">{{mobile}}</td>
	                <td width="100">{{connectCount}}</td>
	                <td width="100">{{connectTime}}</td>
	            </tr>
	            {{/each}}
	         {{/if}}
            </tbody>
        </table>
        
      <!--    <table style="display:inline;margin-left:20px;float: left;width: 48%;" class="layui-table" lay-even="" lay-skin="row">
            <thead>
            <tr>
                <th width="68">序号</th>
                <th width="70">姓名</th>
                <th width="99">手机号码</th>
                <th width="65">通话次数</th>
                <th width="99">通话时长（秒）</th>
            </tr>
            </thead>
            <tbody style="height:335px;width:100%;overflow: auto;display: block;">
            {{#each data.data3}}
            <tr data-id="{{id}}">
                <td width="68">{{addOne index}}</td>
                <td width="70">{{name}}</td>
                <td width="99">{{mobile}}</td>
                <td width="65">{{connectCount}}</td>
                <td width="99">{{connectTime}}</td>
            </tr>
            {{/each}}
            </tbody>
        </table>
 -->
			<%--通讯录上传 --%>
<%-- <div class="layui-form-item">
	<label class="layui-form-label">通讯录文件上传</label>
	<div class="layui-input-inline" style=" width:480px; height: 28px; padding: 5px; margin-bottom: 0;">
			<input type="file" id="ContactFile" multiple="multiple" style=" float:left; width:180px;" />
	     	<input type="button" id="uploadContactFile" value="上传" style=" float:left;" onclick="uploadContactFile('${vo.userInfoVO.id}')"/>
	     	<span style="font-size:12px;color:red">&nbsp;&nbsp;支持类型为xls|xlsx|csv|pdf|jpg|jpeg|png文件上传</span>
	</div>
</div> --%>

<%--通讯录文件下载 --%>
<!-- {{#if fileInfoList}}
<div class="layui-field-box pd-10 hidden">
    <dl class="zj-dl">
        <dt>通讯录文件下载: </dt>
        <dd>
            {{#each fileInfoList}}
			{{#equal bizCode 'contact_file'}}
	        	<div>{{origName}}&nbsp;&nbsp;&nbsp;<a href="{{url}}" style="color: blue;" target="_blank">下载</a>&nbsp;&nbsp;&nbsp;&nbsp;<a style="color:red; width:220px;" onclick="deleteContactFile('{{id}}')">删除</a></div>
	        {{/equal}}
	        {{/each}}
        </dd>
    </dl>
</div>
{{/if}} -->
    </fieldset>
    <%--通讯录联系人通讯end--%>
</form>


		 <!-- 电话记录 -->
	  <div  id="warnRecord1" style="float:left">
	 	 <form class="layui-form" action="">
		  <fieldset class="layui-elem-field">
		      <legend>电话记录</legend>
		      <table class="layui-table mgt-20">
		        <colgroup>
		          <col width="24%">
		          <col width="24%">
		          <col  width="24%">
		          <col>
		          <col width="22%">
		        </colgroup>
		        <thead>
		        <tr>
		          <th>待还款id</th>
		          <th>提醒人员</th>
		          <th>提醒时间</th>
		           <th>是否接电话</th>
		          <th>提醒结果</th>
		        </tr>
		        </thead>
		        <tbody>
				        <tr>
				          <td>{{warnInfo.repayId}}</td>
				          <td>{{warnInfo.sysUserName}}</td>
				          <td>{{warnInfo.warnTime}}</td>
				          <td>
					            {{#equal warnInfo.isPush 0}}
					            	否
					            {{/equal}}
					            {{#equal warnInfo.isPush 1}}
					            	是
					            {{/equal}}
				          </td>
				          <td>{{warnInfo.content}}</td>
				        </tr>
		        </tbody>
		      </table>
		    </fieldset>
		    </form>
	  </div>

  
  
  <!-- 催收记录 -->
  
  <div  id="collectionRecord">
 <form class="layui-form" action="">
  <fieldset class="layui-elem-field">
      <legend>催收记录</legend>
      <table class="layui-table mgt-20">
        <colgroup>
          <col width="3%">
          <col width="10%">
          <col width="7%">
          <col width="7%">
          <col width="8%">
          <col width="7%">
          <col width="8%">
          <col width="7%">
          <col width="18%">
          <col width="5%">
          <col width="10%">
          <col width="10%">
        </colgroup>
        <thead>
        <tr>
          <th>编号</th>
          <th>创建时间</th>
          <th>催收人员</th>
          <th>催收方式</th>
          <th>与本人关系</th>
          <th>联系人姓名</th>
          <th>联系人电话</th>
          <th>催收结果</th>
          <th>催收内容</th>
          <th>是否承诺付款</th>
          <th>承诺付款时间</th>
          <th>下次跟进时间</th>
        </tr>
        </thead>
        <tbody>
        
       			  {{#each recordList}}
        <tr>
          <td>{{addOne @index}}</td>
          <td>{{formatDate createTime "yyyy-MM-dd HH:mm:ss"}}</td>
          <td>{{operatorName}}</td>
          <td>
            {{#equal type 1}}
            电话
            {{/equal}}
            {{#equal type 2}}
            短信
            {{/equal}}
            {{#equal type 3}}
            其他
            {{/equal}}
          </td>
          <td>{{relationshipStr}}</td>
          <td>{{contactName}}</td>
          <td>{{contactMobile}}</td>
          <td>{{resultStr}}</td>
          <td>{{content}}</td>
          <td>
            {{#equal promise 0}}
            否
            {{/equal}}
            {{#equal promise 1}}
            是
            {{/equal}}
          </td>
          <td>{{formatDate promiseDate "yyyy-MM-dd"}}</td>
          <td>{{formatDate nextContactTime "yyyy-MM-dd HH:mm:ss"}}</td>
        </tr>
        {{/each}}
       			
        </tbody>
      </table>
    </fieldset>
  </form>
  </div>



