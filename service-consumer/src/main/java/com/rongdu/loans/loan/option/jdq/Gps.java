package com.rongdu.loans.loan.option.jdq;

import lombok.Data;

import java.io.Serializable;

/**
 * gps
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/5/16
 */
@Data
public class Gps implements Serializable {
    /** 地址 */
    private String address;
    /** 纬度 */
    private String latitude;
    /** 经度 */
    private String longitude;
}
