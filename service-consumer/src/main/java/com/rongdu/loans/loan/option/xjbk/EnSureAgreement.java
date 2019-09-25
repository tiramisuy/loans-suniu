package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/8/15.
 */
@Data
public class EnSureAgreement implements Serializable {

    private static final long serialVersionUID = 5518054583106753508L;


    private String user_name;


    private String user_phone;


    private String user_idcard;


    private String return_url;


    private String sign;

    private String order_sn;

}
