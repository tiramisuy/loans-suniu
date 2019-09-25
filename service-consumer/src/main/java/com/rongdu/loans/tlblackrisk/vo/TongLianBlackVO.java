package com.rongdu.loans.tlblackrisk.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fy
 * @Package com.rongdu.loans.tlblackrisk.vo
 * @date 2019/7/25 14:57
 */
@Data
public class TongLianBlackVO implements Serializable {

    private String code;
    private String seralNo;
    private String timestamp;
    private String msg;
    private TongLianBlackDetail data;
}
