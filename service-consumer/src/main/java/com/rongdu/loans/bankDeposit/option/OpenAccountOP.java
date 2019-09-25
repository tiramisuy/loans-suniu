package com.rongdu.loans.bankDeposit.option;

import java.io.Serializable;

/**
 * 开户入参对象
 * @author likang
 *
 */
public class OpenAccountOP implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -4371254470032173464L;
    
    /**
	 * 短信验证码
	 */
    private String msgVerCode;
    /**
     * 真实名称
     */
    private String realName;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 身份证编号
     */
    private String idCard;
    /**
     * 银行卡号
     */
    private String capAcntNo;
    /**
     * 业务授权码
     */
    private String lastSrvAuthCode;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * ip
     */
    private String ip;
    /**
     * 渠道
     */
    private String channel;

    /**
     * 初始密码
     */
    private String password;
    
    /**
     * 资产合作单位码
     */
    private String assetCode;
    
    private String sourceType;
    
    /**
     * 银行行别号
     */
    private String parent_bank_id;
    /**
     * 名称
     */
    private String bankName;
    
    /*
     * 用户昵称
     */
    private String uname;
    
    /*
     * 银行卡开户行所在城市编号
     */
    private String city_id;
    
    /*
     * 用户类型
     */
    private String v;
    
    /**
     * 用户邮箱
     */
    private String email;
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getParent_bank_id() {
		return parent_bank_id;
	}

	public void setParent_bank_id(String parent_bank_id) {
		this.parent_bank_id = parent_bank_id;
	}

	public String getCapAcntNo() {
		return capAcntNo;
	}

	public void setCapAcntNo(String capAcntNo) {
		this.capAcntNo = capAcntNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMsgVerCode() {
        return msgVerCode;
    }

    public void setMsgVerCode(String msgVerCode) {
        this.msgVerCode = msgVerCode;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getLastSrvAuthCode() {
        return lastSrvAuthCode;
    }

    public void setLastSrvAuthCode(String lastSrvAuthCode) {
        this.lastSrvAuthCode = lastSrvAuthCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
