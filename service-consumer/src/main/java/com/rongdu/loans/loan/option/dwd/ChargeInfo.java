package com.rongdu.loans.loan.option.dwd;

import com.rongdu.loans.loan.option.dwd.charge.Charge;
import com.rongdu.loans.loan.option.dwd.report.ReportInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈大王贷运营商原始数据和运营商报告信息〉
 *
 * @author yuanxianchu
 * @create 2019/5/27
 * @since 1.0.0
 */
@Data
public class ChargeInfo implements Serializable {
    private static final long serialVersionUID = 6669117428097061437L;

    private Charge charge;
    private ReportInfo reportInfo;

    private String orderNo;
    private String userId;
    private String channelCode;
}
