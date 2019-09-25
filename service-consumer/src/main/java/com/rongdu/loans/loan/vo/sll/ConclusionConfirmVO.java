package com.rongdu.loans.loan.vo.sll;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈推送用户确认收款信息〉
 *
 * @author yuanxianchu
 * @create 2018/12/10
 * @since 1.0.0
 */
@Data
public class ConclusionConfirmVO implements Serializable {
    private static final long serialVersionUID = -4495304320837398529L;

    @JsonProperty("confirm_url")
    private String confirmUrl;
}
