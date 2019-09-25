package com.rongdu.loans.loan.option.rong360Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2019/2/17
 * @since 1.0.0
 */
@Data
public class CreateAccountVO implements Serializable {
    private static final long serialVersionUID = 1741303592830836130L;
    @JsonProperty("bind_card_url")
    private String bindCardUrl;
}
