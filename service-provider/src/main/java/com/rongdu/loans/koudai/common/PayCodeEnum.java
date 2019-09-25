package com.rongdu.loans.koudai.common;

/**
 * @author liuzhuang
 */
public enum PayCodeEnum {
	CODE_0(0, "申请成功"), 
	CODE_200001(200001, "今日放款金额超限"),
	CODE_200002(200002, "放款订单重复"),
	CODE_200003(200003, "放款项目不存在"),
	CODE_200004(200004, "放款订单不存在"),
	CODE_200005(200005, "暂无可放款通道"),
	CODE_200006(200006, "请求参数异常"),
	CODE_200007(200007, "失败订单重复发起不能超过24小时"),
	CODE_200008(200008, "重复打款信息不一致"),
	CODE_200009(200009, "单笔请求金额超限"),
	CODE_200010(200010, "单笔请求金额超限(非招行最多5w)"),
	CODE_200011(200011, "手续费大于提现金额"),
	CODE_200012(200012, "单日次数超限"),
	CODE_500(500, "系统异常");
	


	
	private int code;
	private String desc;

	PayCodeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static String getDesc(int code) {
		for (PayCodeEnum p : PayCodeEnum.values()) {
			if (code==p.getCode()) {
				return p.getDesc();
			}
		}
		return null;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


}
