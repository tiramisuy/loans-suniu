package com.rongdu.loans.loan.option;

import com.rongdu.common.exception.ErrInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * api应答结果:
 * code-应答状态
 * msg-应答消息
 * data-应答消息内容
 *
 * @author sunda
 */
@Data
public class ApiResultVO extends HashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = 6059789383786071296L;

    private String code;
    private String msg;
    private Object Data;

    public ApiResultVO() {
        this.code = ErrInfo.SUCCESS.getCode();
        this.msg = ErrInfo.SUCCESS.getMsg();
        super.put("code", this.code);
        super.put("msg", this.msg);
    }

    public ApiResultVO(ErrInfo e) {
        this.code = e.getCode();
        this.msg = e.getMsg();
        super.put("code", this.code);
        super.put("msg", this.msg);
    }

    public ApiResultVO(String code,String msg){
        this.code = code;
        this.msg =msg;
        super.put("code", this.code);
        super.put("msg", this.msg);
    }
}
