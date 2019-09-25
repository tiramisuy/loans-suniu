package com.rongdu.loans.loan.option.xjbk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/6/11.
 */
@Data
public class GpsAddress implements Serializable {


    private static final long serialVersionUID = 5930815446766080030L;
    private String gpsAddress;
    private String gpsAddressTime;
    private String gpsLongitude;
    private String gpsLatitude;
}
