package com.rongdu.loans.loan.option.SLL;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈奇虎360反馈响应〉
 *
 * @author yuanxianchu
 * @create 2018/12/11
 * @since 1.0.0
 */
@Data
public class SLLFeedbackResp implements Serializable {
    private static final long serialVersionUID = -2196696225349127983L;

    private Integer error;

    private String msg;
}
