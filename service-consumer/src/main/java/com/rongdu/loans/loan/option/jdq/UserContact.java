package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/10/12.
 */
@Data
public class UserContact implements Serializable {
    private static final long serialVersionUID = 3272327472077108407L;

    private String name;
    private String mobile;
    private String relation;
    @JsonProperty("name_spare")
    private String nameSpare;
    @JsonProperty("mobile_spare")
    private String mobileSpare;
    @JsonProperty("relation_spare")
    private String relationSpare;

}
