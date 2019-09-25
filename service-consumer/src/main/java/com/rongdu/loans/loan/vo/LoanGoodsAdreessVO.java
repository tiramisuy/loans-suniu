package com.rongdu.loans.loan.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/9/4.
 */
@Data
public class LoanGoodsAdreessVO implements Serializable{
    private String id;
    private String userName; // 姓名
    private String mobile; // 手机号码
    private String resideProvince; // 居住地所在省份
    private String resideProvinceName; // 居住地所在省份名称
    private String resideCity; // 居住地所在城市
    private String resideCityName; // 居住地所在城市名称
    private String resideDistrict; // 居住地所在区县
    private String resideDistrictName; // 居住地所在区县名称
    private String resideAddr; // 居住地址
}
