<%@ page contentType="text/html;charset=UTF-8" %>
    <div class="collection">
        <div class="collections">
            <div class="collections-top clear">
                <div>
                    <span>借款人</span>
                    <input type="checkbox" checked="checked" class="selects"/>
                    <span>全选</span>
                </div>
                <div>分配前催收人</div>
                <div>分配后催收人<em>*</em></div>
                <div>分配后退回时间<em>*</em></div>
            </div>
            <div class="collections-con">
                {{#each list}}
                <div><input type="checkbox" checked="checked" data-id="{{id}}"/><span>{{username}}</span></div>
                <div class="collections-cons">{{operatorName}}</div>
                {{/each}}
                
            </div>
            <div style="position:absolute; height:285px;width:150px; overflow:auto" class="collections-name" id="collections-name" >
            	
            </div>
            <div class="collections-time">
                <select id="collections-time">
                    <option>--请选择--</option>
                    <option value="1">今日24:00</option>
                    <option value="2">明日24:00</option>
                    <option value="3">永久分配</option>
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item mgt-30">
        <div class="layui-input-block" style="width:20%;margin:auto;">
            <div class="collection-bot clears">
                <div class="collection-bot-left" onclick="collectionBot1()">提交</div>
                <div class="collection-bot-right" onclick="layer.close(layer.index);">取消</div>
            </div>
        </div>
    </div>
