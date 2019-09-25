package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-05-23 9:41:38
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@Data
public class IdcardInfo implements Serializable{

    private static final long serialVersionUID = 1814002990058493176L;
    @JsonProperty("idcard_info")
    private IdcardInfoDetail idcardInfoDetail;

}