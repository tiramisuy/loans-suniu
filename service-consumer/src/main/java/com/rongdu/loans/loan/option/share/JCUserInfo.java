package com.rongdu.loans.loan.option.share;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fy
 * @Package com.rongdu.loans.loan.option.share
 * @date 2019/7/11 14:30
 */
@Data
public class JCUserInfo implements Serializable {

    private String name;

    private String mobile;

    private String idCard;

    private String workCity;

    private String chargeDataUrl;

    private String additionDataUrl;

    private String faceVerify;

    private String backIdcard;

    private String frontIdcard;
}
