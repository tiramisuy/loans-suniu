package com.rongdu.loans.loan.option;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/9/5.
 */
@Data
public class LiangHuaPaiOP implements Serializable {

    private static final long serialVersionUID = 3478669220561942316L;
    private String partnerName;
    private String channelId;
    private String requestId;
    private String requestData;
}
