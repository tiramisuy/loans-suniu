<%@ page contentType="text/html;charset=UTF-8" %>

<form class="layui-form" action="">
    <%--身份信息start--%>
    <fieldset class="layui-elem-field">
        <legend>身份信息</legend>
        <div class="layui-field-box">

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">姓名</label>

                    <div class="layui-input-inline">
                        <input type="text" value="{{realName}}" name="realName"
                               class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">手机号码</label>

                    <div class="layui-input-inline">
                        <input type="text" value="{{mobile}}" name="mobile" class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">邮箱</label>

                    <div class="layui-input-inline">
                        <input type="text" value="{{email}}" name="email" class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">证件类型</label>

                    <div class="layui-input-inline">
                        <input type="text" value="{{idType}}" name="idType" class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">证件号码</label>

                    <div class="layui-input-inline">
                        <input type="text" name="idNo" value="{{idNo}}" class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">性别</label>

                    <div class="layui-input-inline">
                        <input type="text" name="sexStr" value="{{sexStr}}" class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">年龄</label>

                    <div class="layui-input-inline">
                        <input type="text" name="age" value="{{age}}" class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">发证机关</label>

                    <div class="layui-input-inline">
                        <input type="text" name="idRegOrg" value="{{idRegOrg}}"
                               class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">证件有效期</label>

                    <div class="layui-input-inline" style="width: 100px;">
                        <input type="text" name="idTermBegin" value="{{idTermBegin}}"
                               class="layui-input"
                               readonly="readonly">
                    </div>
                    <div class="layui-form-mid">-</div>
                    <div class="layui-input-inline" style="width: 100px;">
                        <input type="text" name="idTermEnd" value="{{idTermEnd}}"
                               class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
            	<div class="layui-inline">
                    <label class="layui-form-label">用户类型</label>

                    <div class="layui-input-inline">
                        <input type="text" name="type" value="{{type}}" class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">最高学历</label>

                    <div class="layui-input-inline">
                        <input type="text" name="degree" value="{{degree}}" class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">证件地址</label>

                    <div class="layui-input-inline">
                        <input type="text" name="regAddr" value="{{regAddr}}"
                               class="layui-input" style="width: 600px"
                               readonly="readonly">
                    </div>
                </div>

            </div>
            
			<div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">是否有房</label>

                    <div class="layui-input-inline">
                        <input type="text" name="house" value="{{house}}" class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">是否有房贷</label>

                    <div class="layui-input-inline">
                        <input type="text" name="houseLoan" value="{{houseLoan}}"
                               class="layui-input" readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">是否有车</label>

                    <div class="layui-input-inline">
                        <input type="text" name="car" value="{{car}}"
                               class="layui-input" readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">是否有车贷</label>

                    <div class="layui-input-inline">
                        <input type="text" name="carLoan" value="{{carLoan}}"
                               class="layui-input" readonly="readonly">
                    </div>
                </div>

            </div>
        </div>
    </fieldset>
    <%--身份信息end--%>

    <%--单位信息start--%>
    <fieldset class="layui-elem-field">
        <legend>单位信息</legend>
        <div class="layui-field-box">

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">单位名称</label>

                    <div class="layui-input-inline">
                        <input type="text" name="comName" value="{{comName}}"
                               class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">工作地址</label>

                    <div class="layui-input-inline">
                        <input type="text" name="workAddr"
                               value="{{workProvince}}{{workCity}}{{workDistrict}}{{workAddr}}"
                               class="layui-input" style="width: 600px" readonly="readonly">
                    </div>
                </div>

            </div>
            
			<div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">公司行业</label>

                    <div class="layui-input-inline">
                        <input type="text" value="{{industry}}" name="industry"
                               class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">公司类型</label>

                    <div class="layui-input-inline">
                        <input type="text" value="{{workCategory}}" name="workCategory" class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">公司规模</label>

                    <div class="layui-input-inline">
                        <input type="text" value="{{workSize}}" name="workSize" class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">工作岗位</label>

                    <div class="layui-input-inline">
                        <input type="text" name="workPosition" value="{{workPosition}}"
                               class="layui-input" readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">工作时间</label>

                    <div class="layui-input-inline">
                        <input type="text" value="{{workYear}}" name="workYear" class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">工作收入</label>

                    <div class="layui-input-inline">
                        <input type="text" value="{{indivMonthIncome}}" name="indivMonthIncome" class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">办公电话</label>

                    <div class="layui-input-inline" style="width: 100px;">
                        <input type="text" name="comTelZone" value="{{comTelZone}}"
                               class="layui-input"
                               readonly="readonly">
                    </div>
                    <div class="layui-form-mid">-</div>
                    <div class="layui-input-inline" style="width: 100px;">
                        <input type="text" name="comTelNo" value="{{comTelNo}}"
                               class="layui-input"
                               readonly="readonly">
                    </div>
                    <div class="layui-form-mid">-</div>
                    <div class="layui-input-inline" style="width: 100px;">
                        <input type="text" name="comTelExt" value="{{comTelExt}}"
                               class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
			</div>
        </div>
    </fieldset>
    <%--单位信息end--%>

    <%--家庭信息start--%>
    <fieldset class="layui-elem-field">
        <legend>家庭信息</legend>
        <div class="layui-field-box">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">居住时长</label>

                    <div class="layui-input-inline">
                        <input type="text" name="resideYear" value="{{resideYear}}"
                               class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">婚姻状况</label>

                    <div class="layui-input-inline">
                        <input type="text" name="marital" value="{{marital}}"
                               class="layui-input"
                               readonly="readonly">
                    </div>
                </div>

            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">居住地址</label>

                    <div class="layui-input-inline">
                        <input type="text" name="resideAddr"
                               value="{{resideProvince}}{{resideCity}}{{resideDistrict}}{{resideAddr}}"
                               class="layui-input" readonly="readonly" style="width: 600px">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">父母居住地址</label>

                    <div class="layui-input-inline">
                        <input type="text" name="parentAddress"
                               value="{{parentProvince}}{{parentCity}}{{parentDistrict}}{{parentAddress}}"
                               class="layui-input" readonly="readonly" style="width: 600px">
                    </div>
                </div>
            </div>
        </div>
    </fieldset>
    <%--家庭信息end--%>

    <%--联系人信息start--%>
    <fieldset class="layui-elem-field">
        <legend>联系人信息</legend>
        <div class="add" onclick="add()">添加</div>

        <table class="layui-table" lay-even="" lay-skin="row" id="contactTable">
            <colgroup>
                <col width="150">
                <col width="150">
                <col width="150">
                <col width="200">
                <col width="200">
            </colgroup>
            <thead>
            <tr>
                <th>序号</th>
                <th>姓名</th>
                <th>手机号码</th>
                <th>与本人关系</th>
                <th>是否匹配呼入前10</th>
                <th>是否匹配呼出前10</th>
                <th>是否匹配通讯录</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            {{#each contactList}}
            <tr data-id="{{id}}">
                <td>{{addOne @index}}</td>
                <td>{{name}}</td>
                <td>{{mobile}}</td>
                <td>{{relationshipStr}}</td>
				<td>
					{{#equal isTerminatingCall 1}}
						<span style="color:green">是</span>
					{{/equal}}
					{{#equal isTerminatingCall 0}}
						<span style="color:red">否</span>
					{{/equal}}
				</td>
				<td>
					{{#equal isOriginatingCall 1}}
						<span style="color:green">是</span>
					{{/equal}}
					{{#equal isOriginatingCall 0}}
						<span style="color:red">否</span>
					{{/equal}}
				</td>
				<td>
					{{#equal isDeviceContact 1}}
						<span style="color:green">是</span>
					{{/equal}}
					{{#equal isDeviceContact 0}}
						<span style="color:red">否</span>
					{{/equal}}
				</td>
                <td>
                    {{#equal source 3}}
                    <span id="contactSub" onclick="remove(this)">删除</span>
                    {{/equal}}
                </td>

            </tr>
            {{/each}}
            </tbody>
        </table>
    </fieldset>
    <%--联系人信息end--%>
    
    <%--借款信息start--%>
    <fieldset class="layui-elem-field">
    <legend>当前申请订单</legend>
    <table class="layui-table" lay-even="" lay-skin="row">
        <colgroup>
            <col width="200">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="150">
            <col width="200">
            <col width="150">
        </colgroup>
        <thead>
        <tr>
            <th>订单号</th>
            <th>订单状态</th>
            <th>借款产品</th>
            <th>借款本金（元）</th>
            <th>借款期限</th>
            <th>借款利率（年化）</th>
            <th>服务费</th>
            <th>借款用途</th>
            <th>还款方式</th>
            <th>还款期数</th>
            <th>申请时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        {{#each repayItemDetailList}}
        {{#equal status 171}}
        <tr>
            <td><a class="text-blue"
                   href="{{../../adminPath}}/loan/apply/checkFrom?id={{userId}}&sign=detail&applyId={{id}}&flag=0"
                   target="_blank">{{id}}</a></td>
            <td>{{statusStr}}</td>
            <td>{{productName}}</td>
            <td>{{applyAmt}}</td>
            <td>{{applyTerm}}天</td>
            <td>{{actualRate}}%</td>
            <td>{{servFee}}</td>
            <td>{{purpose}}</td>
            <td>{{repayMethod}}</td>
            <td>{{term}}</td>
            <td>{{formatDate applyTime 'yyyy-MM-dd HH:mm:ss'}}</td>
            <td><a class="text-blue"
                   onclick="contentTable_1('{{contNo}}','{{id}}','{{productId}}')">详情</a>
            </td>
        </tr>
        {{/equal}}
        {{#equal status 220}}
        <tr>
            <td><a class="text-blue"
                   href="{{../../adminPath}}/loan/apply/checkFrom?id={{userId}}&sign=detail&applyId={{id}}&flag=0"
                   target="_blank">{{id}}</a></td>
            <td>{{statusStr}}</td>
            <td>{{productName}}</td>
            <td>{{applyAmt}}</td>
            <td>{{applyTerm}}天</td>
            <td>{{actualRate}}%</td>
            <td>{{servFee}}</td>
            <td>{{purpose}}</td>
            <td>{{repayMethod}}</td>
            <td>{{term}}</td>
            <td>{{formatDate applyTime 'yyyy-MM-dd HH:mm:ss'}}</td>
            <td><a href="javascript:void(0)" class="text-blue"
                   onclick="contentTable_1('{{contNo}}','{{id}}','{{productId}}')">详情</a>
            </td>
        </tr>
        {{/equal}}
        {{#equal status 223}}
        <tr>
            <td><a class="text-blue"
                   href="{{../../adminPath}}/loan/apply/checkFrom?id={{userId}}&sign=detail&applyId={{id}}&flag=0"
                   target="_blank">{{id}}</a></td>
            <td>{{statusStr}}</td>
            <td>{{productName}}</td>
            <td>{{applyAmt}}</td>
            <td>{{applyTerm}}天</td>
            <td>{{actualRate}}%</td>
            <td>{{servFee}}</td>
            <td>{{purpose}}</td>
            <td>{{repayMethod}}</td>
            <td>{{term}}</td>
            <td>{{formatDate applyTime 'yyyy-MM-dd HH:mm:ss'}}</td>
            <td><a href="javascript:void(0)" class="text-blue"
                   onclick="contentTable_1('{{contNo}}','{{id}}','{{productId}}')">详情</a>
            </td>
        </tr>
        {{/equal}}

        {{/each}}
        </tbody>
    </table>
</fieldset>



<%--影像信息start--%>
<fieldset class="layui-elem-field">
<legend>影像信息</legend>
<div id="layer-photos">
<table class="table">
    <div class="layui-field-box pd-10 hidden">
        <dl class="zj-dl" style="width:400px; height:300px;float:left;">
            <dt>证件信息</dt>
            <dt>姓名：{{realName}}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp身份证号码：{{idNo}}</dt>
            <dd class="hand">
                {{#each fileList}}
                {{#equal bizCode 'front_idcard'}}
                <img style="width:350px;height:250px;" src="{{url}}" layer-src="{{url}}" title="{{bizName}}" border="0" onclick="layer.photos({photos: '#layer-photos',offset: '80px',shade: [0.8, '#393D49']});" onmousemove="cursor: pointer">
                {{/equal}}
                {{/each}}

            </dd>
        </dl>
        <dl class="zj-dl" style="width:400px; height:300px; float:left; ">
            <dt>&nbsp</dt>
            <dt>&nbsp</dt>
            <dd class="hand">
                {{#each fileList}}
                {{#equal bizCode 'back_idcard'}}
                <img style="width:350px;height:250px;" src="{{url}}" layer-src="{{url}}" title="{{bizName}}" border="0" onclick="layer.photos({photos: '#layer-photos',offset: '80px',shade: [0.8, '#393D49']});" onmousemove="cursor: pointer">
                {{/equal}}
                {{/each}}
            </dd>
        </dl>
        <dl class="zj-dl" style="width:400px; height:300px; float:left;">
            <dt>&nbsp</dt>
            <dt>人脸识别照</dt>
            <dd class="hand">
                {{#each fileList}}
                {{#equal bizCode 'face_verify'}}
                <img style="width:350px;height:250px;" src="{{url}}" layer-src="{{url}}" title="{{bizName}}" border="0" onclick="layer.photos({photos: '#layer-photos',offset: '80px',shade: [0.8, '#393D49']});" onmousemove="cursor: pointer">
                {{/equal}}
                {{/each}}
            </dd>
        </dl>
    </div>
</table>
</div>
</fieldset>
<%--影像信息end--%>



    <%--其他信息start--%>
    <fieldset class="layui-elem-field">
        <legend>其他信息</legend>
        <div class="layui-field-box">

            <div class="layui-form-item">
            	<div class="layui-inline">
                    <label class="layui-form-label">渠道</label>

                    <div class="layui-input-inline">
                        <input type="text" name="channelName" value="{{channelName}}" class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">QQ号码</label>

                    <div class="layui-input-inline">
                        <input type="text" name="qq" value="{{qq}}" class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
                <c:if test="${sign == 'check' or sign == 'detail' }">
                    <div class="layui-inline">
                        <label class="layui-form-label">申请借款次数</label>

                        <div class="layui-input-inline">
                            <input type="text" name="count" value="{{count}}"
                                   class="layui-input" readonly="readonly">
                        </div>
                    </div>
                    
                    <div class="layui-inline">
                        <label class="layui-form-label">贷款成功次数</label>

                        <div class="layui-input-inline">
                            <input type="text" name="loanSuccCount" value="{{loanSuccCount}}"
                                   class="layui-input" readonly="readonly">
                        </div>
                    </div>
                </c:if>
            </div>
            
            <div class="layui-form-item">
            	 <label class="layui-form-label">
                 <a href ="https://www.baidu.com/s?wd={{mobile}}" class="text-blue" target="_blank">百度查手机号</a>
                 </label>
                 <label class="layui-form-label">
                 <a href ="http://shixin.court.gov.cn" class="text-blue" target="_blank">失信网查询</a>
                 </label>
                 <label class="layui-form-label">
                 <a href ="http://zhixing.court.gov.cn/search" class="text-blue" target="_blank">人法网查询</a>
          		 </label>
            </div>
            
        </div>
    </fieldset>
    <%--其他信息end--%>
    {{#equal sign user}}
    <%--账户信息start--%>
    <fieldset class="layui-elem-field">
        <legend>账户信息</legend>
        <div class="layui-field-box">

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">创建时间</label>

                    <div class="layui-input-inline">
                        <input type="text" name="createTime" value="{{formatDate createTime 'yyyy-MM-dd HH:mm:ss'}}"
                               class="layui-input" readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">更新时间</label>

                    <div class="layui-input-inline">
                        <input type="text" name="updateTime" value="{{formatDate updateTime 'yyyy-MM-dd HH:mm:ss'}}"
                               class="layui-input" readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">是否冻结</label>

                    <div class="layui-input-inline">
                        <input type="text" name="status"
                               value="{{#equal status 0}}是{{/equal}}{{#equal status 1}}否{{/equal}}"
                               class="layui-input" readonly="readonly">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">是否黑名单</label>

                    <div class="layui-input-inline">
                        <input type="text" name="blacklist"
                               value="{{#equal blacklist 1}}是{{/equal}}{{#equal blacklist 0}}否{{/equal}}"
                               class="layui-input"
                               readonly="readonly">
                    </div>
                </div>
            </div>
        </div>
    </fieldset>
    <%--账户信息end--%>
    {{/equal}}
</form>
