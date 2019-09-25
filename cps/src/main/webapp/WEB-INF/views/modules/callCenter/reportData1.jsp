<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!-- 资信云一 -->
<div role="tabpanel" class="tab-pane" id="messages">
                <div class="box">
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
                                    <th>第一次联系时间</th>
                                    <th>最近联系时间</th>
                                    <th>被叫次数/时间（秒）</th>
                                </tr>
                            </thead>
                            <tbody>
                            {{#each data.ccmList1}}
                                <tr>
                                    <td>{{addOne @index}}</td>
                                    <td>{{contactName}}</td>
                                    <td>{{mobile}}</td>
                                    <td>{{belongTo}}</td>
                                    <td>{{beginTime}}</td>
                                    <td>{{endTime}}</td>
                                    <td>{{terminatingCallCount}}/{{terminatingTime}}</td>
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
                                    <th>第一次联系时间</th>
                                    <th>最近联系时间</th>
                                    <th>被叫次数/时间（秒）</th>
                                </tr>
                            </thead>
                            <tbody>
                            {{#each data.ccmList2}}
                                <tr>
                                    <td>{{addOne @index}}</td>
                                    <td>{{contactName}}</td>
                                    <td>{{mobile}}</td>
                                    <td>{{belongTo}}</td>
                                    <td>{{beginTime}}</td>
                                    <td>{{endTime}}</td>
                                    <td>{{originatingCallCount}}/{{originatingTime}}</td>
                                </tr>
                                 {{/each}}
                                
                            </tbody>
                        </table>
                    </div>
                    <!-- 本人通话活动地区 -->
                    <div class="info onwCallCity">
                        <div class="title">本人通话活动地区:</div>
                        <table>
                            <thead>
                                <tr>
                                    <!-- tr：table row：表格的行 -->
                                    <th>序号</th>
                                    <th>地区</th>
                                    <th>通话次数</th>
                                    <th>呼入次数/呼入时间</th>
                                    <th>呼出次数/呼出时间</th>
                                    <th>呼入次数占比</th>
                                    <th>呼出次数占比</th>
                                    <th>呼入时间占比</th>
                                    <th>呼出时间占比</th>
                                </tr>
                            </thead>
                            <tbody>
                            {{#each data.mnoCommonlyConnectAreas}}
                                <tr>
                                    <td>{{addOne @index}}</td>
                                    <td>{{area}}</td>
                                    <td>{{numberCount}}</td>
                                    <td>{{terminatingCallCount}}/{{terminatingCallTime}}</td>
                                    <td>{{originatingCallCount}}/{{originatingCallTime}}</td>
                                    <td>{{callInCountPercentage}}</td>
                                    <td>{{callOutCountPercentage}}</td>
                                    <td>{{callInTimePercentage}}</td>
                                    <td>{{callOutTimePercentage}}</td>
                                </tr>
                                {{/each}}
                            </tbody>
                        </table>
                    </div>
                    <!-- 联系人通话活动地区 -->
                    <div class="info linkCallCity">
                        <div class="title">联系人通话活动地区:</div>
                        <table>
                            <thead>
                                <tr>
                                    <!-- tr：table row：表格的行 -->
                                    <th>序号</th>
                                    <th>地区</th>
                                    <th>通话次数</th>
                                    <th>呼入次数/呼入时间</th>
                                    <th>呼出次数/呼出时间</th>
                                    <th>呼入次数占比</th>
                                    <th>呼出次数占比</th>
                                    <th>呼入时间占比</th>
                                    <th>呼出时间占比</th>
                                </tr>
                            </thead>
                            <tbody>
                            {{#each data.mnoContactsCommonlyConnectAreas}}
                                <tr>
                                    <td>{{addOne @index}}</td>
                                    <td>{{area}}</td>
                                    <td>{{numberCount}}</td>
                                    <td>{{terminatingCallCount}}/{{terminatingCallTime}}</td>
                                    <td>{{originatingCallCount}}/{{originatingCallTime}}</td>
                                    <td>{{callInCountPercentage}}</td>
                                    <td>{{callOutCountPercentage}}</td>
                                    <td>{{callInTimePercentage}}</td>
                                    <td>{{callOutTimePercentage}}</td>
                                </tr>
                                {{/each}}
                            </tbody>
                        </table>
                    </div>
                    <!-- 分时间段统计 -->
                    <div class="info deciTimePlan">
                        <div class="title">分时间段统计:</div>
                        <table>
                            <thead>
                                <tr>
                                    <!-- tr：table row：表格的行 -->
                                    <th>序号</th>
                                    <th>时间段</th>
                                    <th>活动次数</th>
                                    <th>占比</th>
                                </tr>
                            </thead>
                            <tbody>
                             {{#each data.mnoPeriodUsedInfos}}
                                <tr>
                                 	<td>{{addOne @index}}</td>
                                    <td>{{periodType}}</td>
                                    <td>{{count}}</td>
                                    <td>{{percentage}}</td>
                                </tr>
                                {{/each}}
                            </tbody>
                        </table>
                    </div>
                    
                   
                    
                </div>
            </div>
