package com.rongdu.loans.loan.option.share;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fy
 * @Package com.rongdu.loans.loan.option.share
 * @date 2019/7/11 14:30
 */
@Data
public class JCShareResult implements Serializable {

    private static final long serialVersionUID = 7762545068165068665L;

    private String code;//0000成功 9999异常/失败
    private String msg;//返回信息
}
