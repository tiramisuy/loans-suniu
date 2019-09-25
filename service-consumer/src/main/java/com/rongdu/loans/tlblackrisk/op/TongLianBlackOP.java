package com.rongdu.loans.tlblackrisk.op;

import lombok.Data;

import java.io.Serializable;

/**
 * 通联查询网贷黑名单
 * @author fy
 * @Package com.rongdu.loans.tlblackrisk.op
 * @date 2019/7/25 14:22
 */
@Data
public class TongLianBlackOP implements Serializable {

    private String merId;
    private String idCard;
    private String bankNo;
    private String timestamp;
    private String beforeDate;
    private String custOrderId;
}
