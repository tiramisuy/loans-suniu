package com.rongdu.loans.zhicheng.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author fy
 * @Package com.rongdu.loans.zhicheng.vo
 * @date 2019/7/5 10:18
 */
@Data
public class RiskResult implements Serializable {
    private String dataType;
    private List<DishonestDetails> dishonestDetails;
    private String riskDetail;
    private String riskItemType;
    private String riskItemValue;
    private String riskTime;
}
