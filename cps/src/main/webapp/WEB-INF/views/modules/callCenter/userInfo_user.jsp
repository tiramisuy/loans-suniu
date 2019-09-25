<%@ page contentType="text/html;charset=UTF-8" %>

<!-- 客户信息 -->
<div id="clientInfo">
               
           
 <div class="container">
 
                
                
                    <!-- 身份信息 -->
                    <div class="info userInfo">
                        <div class="title">身份信息:</div>
                        <ul>
                            <li>姓名: <span>{{realName}}</span></li>
                            <li>手机号: <span>{{mobile}}</span></li>
                            <li>身份证号: <span>{{idNo}}</span></li>
                            <li>单位名称: <span>{{comName}}</span></li>
                        </ul>
                    </div>
                    <!-- 影像信息 -->
                    <div class="info imageInfo">
                        <div class="title">身份证信息:</div>
                        <div class="identityCard">
                        
                 <div>身份证正面:

                    {{#each fileList}}
                    {{#equal bizCode 'front_idcard'}}
                    <img src="{{url}}" alt="">
                    {{/equal}}
                    {{/each}}
                </div>
                <div>身份证反面:
                    {{#each fileList}}
                    {{#equal bizCode 'back_idcard'}}
                    <img src="{{url}}" alt="">
                    {{/equal}}
                    {{/each}}
                </div>
                <div>人脸识别:
                    {{#each fileList}}
                    {{#equal bizCode 'face_verify'}}
                    <img src="{{url}}" alt="">
                    {{/equal}}
                    {{/each}}
                </div>
                                                   
                        </div>
                    </div>
                    <!-- 联系人信息 -->
                    <div class="info contactInfo">
                        <div class="title">联系人信息:</div>
                        <table>
                            <thead>
                                <tr>
                                    <!-- tr：table row：表格的行 -->
                                    <th>序号</th>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>与本人关系</th>
                                    <th>是否匹配呼入前10</th>
                                    <th>是否匹配呼出前10</th>
                                    <th>是否匹配通讯录</th>
                                </tr>
                            </thead>
                            <tbody>
                                {{#each contactList}}
                    <tr>
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
                    </tr>
                    {{/each}}
                            </tbody>
                        </table>
                    </div>
                    <!-- 查重信息 -->
                    <div class="info importInfo">
                        <div class="title">查重信息</div>
                        <table>
                            <thead>
                                <tr>
                                    <th>关系</th>
                                    <th>姓名</th>
                                    <th>手机号码</th>
                                    <th></th>
                                    <th>订单号</th>
                                    <th>进件时间</th>
                                    <th>关系</th>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                </tr>
                            </thead>
                            <tbody>
                                {{#each removeDuplicateQueryVO.list}}
        <tr>
            <td>{{sourceRelationship}}</td>
            <td>{{sourceUserName}}</td>
            <td>{{sourceMobile}}</td>
            <td class="red">匹配到---></td>
            {{#equal ../data/flag '0'}}
            <td>{{applyId}}</td>
            {{else}}
            <td>{{applyId}}</td>
            {{/equal}}
            <td>{{formatDate createTime 'yyyy-MM-dd HH:mm:ss'}}</td>
            <td>{{relationshipStr}}</td>
            <td>{{name}}</td>
            <td>{{mobile}}</td>
        </tr>
        {{/each}}
                            </tbody>
                        </table>
                    </div>
                    
                </div>
 </div>
</div>