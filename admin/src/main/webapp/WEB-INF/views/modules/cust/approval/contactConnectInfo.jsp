<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!-- 通讯录 -->
            <div id="addressList">
                
           

<div class="container">
                    <!-- 呼入前十 -->
                    <div class="info incomingCall">
                        <div class="title">呼入前十:</div>
                        <table>
                            <thead>
                                <tr>
                                    <!-- tr：table row：表格的行 -->
                                    <th>序号</th>
                                    <th>匹配通讯录联系人</th>
                                    <th>通话号码</th>
                                    <th>归属地</th>
                                    <th>最近一周联系次数</th>
                                    <th>最近一月联系次数</th>
                                    <th>最近三月联系次数</th>
                                    <th>通话次数/通话时间（秒）</th>
                                    <th>被叫次数/时间（秒）</th>
                                    
                                    
                                </tr>
                            </thead>
                            <tbody>
                                 {{#each callInCntList}}
                                <tr>
                                    <td>{{addOne @index}}</td>
					                <td>{{contactName}}</td>
					                <td>{{phone}}</td>
					                <td>{{phoneLocation}}</td>
					                <td align="center">{{contact1w}}</td>
					                <td align="center">{{contact1m}}</td>
					                <td align="center">{{contact3m}}</td>
					                <td>{{talkCnt}}/{{talkSeconds}}</td>
					                <td>{{calledCnt}}/{{calledSeconds}}</td>
                                </tr>
                                {{/each}}
                            </tbody>
                        </table>
                    </div>
                    <!-- 呼出前十 -->
                    <div class="info callOut">
                        <div class="title">呼出前十:</div>
                        <table>
                            <thead>
                                <tr>
                                    <!-- tr：table row：表格的行 -->
                                    <th>序号</th>
                                    <th>匹配通讯录联系人</th>
                                    <th>通话号码</th>
                                    <th>归属地</th>
                                    <th>最近一周联系次数</th>
                                    <th>最近一月联系次数</th>
                                    <th>最近三月联系次数</th>
                                    <th>通话次数/通话时间（秒）</th>
                                    <th>主叫次数/时间（秒）</th> 
                                    
                                    
                                </tr>
                            </thead>
                            <tbody>
                                {{#each callOutCntList}}
                                <tr>
                                    <td>{{addOne @index}}</td>
					                <td>{{contactName}}</td>
					                <td>{{phone}}</td>
					                <td>{{phoneLocation}}</td>
					                <td align="center">{{contact1w}}</td>
					                <td align="center">{{contact1m}}</td>
					                <td align="center">{{contact3m}}</td>
					                <td>{{talkCnt}}/{{talkSeconds}}</td>
					                <td>{{callCnt}}/{{callSeconds}}</td>
                                </tr>
                                 {{/each}}
                            </tbody>
                        </table>
                    </div>
                    <!-- 联系人通讯录统计（按通话时长排序） -->
                    <div class="info contactComInfo">
                        <div class="title">联系人通讯录统计（按通话时长排序）:</div>
                        <table>
                            <thead>
                                <tr>
                                    <!-- tr：table row：表格的行 -->
                                    <th>序号</th>
                                    <th>姓名</th>
                                    <th>手机号码</th>
                                    <th>通话次数</th>
                                    <th>通话时长（秒）</th>
                                </tr>
                            </thead>
                            <tbody>
                               {{#each contactChecks}}
                    <tr>
                        <td>{{addOne @index}}</td>
                        <td>{{name}}</td>
                        <td>{{mobile}}</td>
                        <td>{{call_cnt}}</td>
                        <td>{{call_len}}</td>
                    </tr>
                    {{/each}}
                            </tbody>
                        </table>
                    </div>
                    
                </div>
                
 </div>