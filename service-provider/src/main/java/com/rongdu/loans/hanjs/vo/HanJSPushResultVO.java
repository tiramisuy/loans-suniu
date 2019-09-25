package com.rongdu.loans.hanjs.vo;

import java.io.Serializable;

public class HanJSPushResultVO implements Serializable{

	private static final long serialVersionUID = -7530001987219914285L;

	private Object body;
	
	private HanJSPushHeadVO head;

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public HanJSPushHeadVO getHead() {
		return head;
	}

	public void setHead(HanJSPushHeadVO head) {
		this.head = head;
	}
	
	
}
