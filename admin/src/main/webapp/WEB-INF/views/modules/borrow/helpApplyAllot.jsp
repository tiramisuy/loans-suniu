<%@ page contentType="text/html;charset=UTF-8" %>
    <div class="collection">
        <div class="collections">
            <div class="collections-top clear">
                <div class="l">
                    <span>借款人</span>
                    <input type="checkbox" checked="checked" class="selects"/>
                    <span>全选</span>
                </div>
                <div class="l wAuto">分配人<em>*</em></div>
                <div class="collections-name r">
	                <select id="collections-name">
	                    <option>--选择分配人--</option>
	
	                </select>
           		</div>
            </div>
            <div class="collections-con">
                {{#each list}}
                <div><input type="checkbox" checked="checked" data-id="{{id}}"/><span>{{username}}</span></div>
                {{/each}}    
            </div>
        </div>
    </div>
    <div class="layui-form-item mgt-30">
        <div class="layui-input-block" style="width:20%;margin:auto;">
            <div class="collection-bot clears">
                <div class="collection-bot-left" onclick="warnBot1()">提交</div>
                <div class="collection-bot-right" onclick="layer.close(layer.index);">取消</div>
            </div>
        </div>
    </div>
