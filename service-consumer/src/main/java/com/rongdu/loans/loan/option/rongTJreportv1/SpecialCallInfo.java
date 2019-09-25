package com.rongdu.loans.loan.option.rongTJreportv1;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * Auto-generated: 2018-07-19 11:4:24
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class SpecialCallInfo implements Serializable{

	private static final long serialVersionUID = 6569660560690875503L;
	
	@JsonProperty("called_cnt")
    private int calledCnt;
    @JsonProperty("talk_seconds")
    private int talkSeconds;
    @JsonProperty("talk_cnt")
    private int talkCnt;
    @JsonProperty("msg_cnt")
    private int msgCnt;
    @JsonProperty("called_seconds")
    private int calledSeconds;
    @JsonProperty("receive_cnt")
    private int receiveCnt;
    private List<Detail> detail;
    @JsonProperty("call_cnt")
    private int callCnt;
    @JsonProperty("unknown_cnt")
    private int unknownCnt;
    @JsonProperty("send_cnt")
    private int sendCnt;
    @JsonProperty("call_seconds")
    private int callSeconds;
    @JsonProperty("phone_list")
    private List<String> phoneList;
    
	@SuppressWarnings("unchecked")
	public void setDetail(Object detail) {
		if (detail instanceof List<?>) {
			this.detail = (List<Detail>) detail;
			return;
		}
		List<Detail> details = new ArrayList<>();
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(detail);
		Iterator<String> keys = jsonObject.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			JSONObject object = jsonObject.getJSONObject(key);
			Detail detail2 = new Detail();
			detail2.setKey(key);
			detail2.setCallCnt(object.getIntValue("call_cnt"));
			detail2.setCalledCnt(object.getIntValue("called_cnt"));
			detail2.setCalledSeconds(object.getIntValue("called_seconds"));
			detail2.setCallSeconds(object.getIntValue("call_seconds"));
			detail2.setContactPhoneCnt(object.getIntValue("contact_phone"));
			detail2.setMonth(object.getString("month"));
			detail2.setMsgCnt(object.getIntValue("msg_cnt"));
			detail2.setReceiveCnt(object.getIntValue("receive_cnt"));
			detail2.setSendCnt(object.getIntValue("send_cnt"));
			detail2.setTalkCnt(object.getIntValue("talk_cnt"));
			detail2.setTalkSeconds(object.getIntValue("talk_seconds"));
			detail2.setUnknownCnt(object.getIntValue("unknown_cnt"));
			details.add(detail2);
		}
		this.detail = details;
	}

}