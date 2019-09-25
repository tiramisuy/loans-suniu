package com.rongdu.loans.loan.vo.sll;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈推送用户绑定银行卡〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class CardBindVO implements Serializable {
    private static final long serialVersionUID = -3626525633758632073L;

    @JsonProperty("need_confirm")
    private String needConfirm;

    @JsonProperty("order_no")
    private String orderNo;

    @JsonProperty("bind_status")
    private Integer bindStatus;

    private String reason;

}
