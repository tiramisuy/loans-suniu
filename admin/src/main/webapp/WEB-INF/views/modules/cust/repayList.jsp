<%@ page contentType="text/html;charset=UTF-8" %>
<fieldset class="layui-elem-field">
    <legend>借款详情</legend>
    <div class="layui-field-box">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">订单号</label>

                <div class="layui-input-inline">
                    <input type="text" name="id" value="{{id}}" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">借款人姓名</label>

                <div class="layui-input-inline">
                    <input type="text" name="userName" value="{{userName}}" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">申请时间</label>

                <div class="layui-input-inline">
                    <input type="text" name="marital" value="{{formatDate applyTime 'yyyy-MM-dd HH:mm:ss'}}" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">借款产品</label>

                <div class="layui-input-inline">
                    <input type="text" name="productName" value="{{productName}}" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">借款金额（元）</label>

                <div class="layui-input-inline">
                    <input type="text" style="color: red" class="layui-input" name="applyAmt" value="{{#if approveAmt}}{{approveAmt}}{{else}}{{applyAmt}}{{/if}}" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">借款期限</label>

                <div class="layui-input-inline">
                    <input type="text" style="color: red" class="layui-input" name="applyTerm" value="{{#if approveTerm}}{{approveTerm}}{{else}}{{applyTerm}}{{/if}}天" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">计息方式</label>

                <div class="layui-input-inline">
                    <input type="text" name="repayMethod" value="{{repayMethod}}" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">借款利率（年化）</label>

                <div class="layui-input-inline">
                    <input type="text" name="rateStr" value="{{rateStr}}" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">每日利息(元)</label>

                <div class="layui-input-inline">
                    <input type="text" style="color: red" class="layui-input" name="everydayInterest" value="{{everydayInterest}}元" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
        	<!-- 
            <div class="layui-inline">
                <label class="layui-form-label">计息开始时间</label>

                <div class="layui-input-inline">
                    <input type="text" name="startInterest" value="{{startInterest}}天" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
             -->
            <div class="layui-inline">
                <label class="layui-form-label">借款服务费率</label>

                <div class="layui-input-inline">
                    <input type="text" name="servFeeRateStr" value="{{servFeeRateStr}}" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">借款服务费（元）</label>

                <div class="layui-input-inline">
                    <input type="text" style="color: red" class="layui-input" name="servFee" value="{{servFee}}" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
        </div>
        <!-- 
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">是否支持提前还款</label>

                <div class="layui-input-inline">
                    <input type="text" name="prepay" value="{{#equal prepay 1}}是{{/equal}}{{#equal prepay 0}}否{{/equal}}" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">提前还款锁定天数</label>

                <div class="layui-input-inline">
                    <input type="text" name="minLoanDay" value="{{minLoanDay}}天" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">提前还款罚率</label>

                <div class="layui-input-inline">
                    <input type="text" name="prepayFee" value="{{prepayFeeRate}}" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">逾期还款日罚率</label>

                <div class="layui-input-inline">
                    <input type="text" name="overdueFeeRate" value="{{overdueFeeRate}}" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">逾期管理费率</label>

                <div class="layui-input-inline">
                    <input type="text" name="overdueFee" value="{{overdueFee}}" class="layui-input"
                           readonly="readonly">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">逾期开始时间</label>

                <div class="layui-input-inline">
                    <input type="text" name="graceDay" value="{{graceDay}}天" class="layui-input"
                           readonly="readonly">
                </div>
            </div>

        </div>
         -->
    </div>
</fieldset>

<fieldset class="layui-elem-field">
    <legend>还款计划</legend>
    <table class="layui-table" lay-even="" lay-skin="row">
        <thead>
        <tr>
            <th>待还ID</th>
            <th>合同编号</th>
            <th>期数</th>
            <th>应还本金（元）</th>
            <th>应还利息（元）</th>
            <th>已借期限</th>
            <th>提前天数</th>
            <th>提前还款违约金（元）</th>
            <th>逾期天数</th>
            <th>逾期利息（元）</th>
            <th>逾期管理费（元）</th>
            <th>分期服务费（元）</th>
            <th>减免金额（元）</th>
            <th>应还总额（元）</th>
            <th>实还总额（元）</th>
            <th>借款日期</th>
            <th>应还日期</th>
            <th>实还日期</th>
            <th>还款状态</th>
            <th>标签</th>
        </tr>
        </thead>
        <tbody>
        {{#each list}}
        <tr>
            <td>{{id}}</td>
            <td>{{contNo}}</td>
            <td>{{thisTerm}}/{{totalTerm}}</td>
            <td>{{principal}}</td>
            <td>{{currentInterest}}</td>
            <td>
                {{#if borrow}}
                {{borrow}}天
                {{/if}}
            </td>
            <td>
                {{#if ahead}}
                {{ahead}}天
                {{/if}}
            </td>
            <td>{{prepayFee}}</td>
            <td>
                {{#if overdue}}
                {{overdue}}天
                {{/if}}
            </td>
            <td>{{penalty}}</td>
            <td>{{overdueFee}}</td>
            <td>{{termServFee}}</td>
            <td>{{deduction}}</td>
            <td>{{totalAmount}}</td>
            <td>{{actualRepayAmt}}</td>
            <td>{{formatDate loanStartDate "yyyy-MM-dd"}}</td>
            <td>{{formatDate repayDate "yyyy-MM-dd"}}</td>
            <td>{{formatDate actualRepayTime "yyyy-MM-dd HH:mm"}}</td>
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
                <div class="text-blue">逾期</div>
                {{/equal}}
            </td>
        </tr>
        {{/each}}
    </table>
</fieldset>