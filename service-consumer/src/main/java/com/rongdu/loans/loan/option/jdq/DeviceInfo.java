package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-10-12 10:27:22
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class DeviceInfo implements Serializable {

    private static final long serialVersionUID = -4644787648136561977L;

    @JsonProperty("device_id")
    private String deviceId;

    private String ip;

    @JsonProperty("device_type")
    private String deviceType;

    @JsonProperty("device_model")
    private String deviceModel;

    @JsonProperty("device_os")
    private String deviceOs;

    private String openudid;

    @JsonProperty("jailbreak_flag")
    private String jailbreakFlag;

    @JsonProperty("root_flag")
    private String rootFlag;
}