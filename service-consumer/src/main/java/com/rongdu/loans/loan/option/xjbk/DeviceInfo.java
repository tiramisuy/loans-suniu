package com.rongdu.loans.loan.option.xjbk;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceInfo implements Serializable{

    private static final long serialVersionUID = 7993719599427866254L;
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("device_info")
    private String deviceInfo;
    @JsonProperty("os_type")
    private String osType;
    @JsonProperty("os_version")
    private String osVersion;
    private String ip;
    private String memory;
    private String storage;
    @JsonProperty("unuse_storage")
    private String unuseStorage;
    @JsonProperty("gps_longitude")
    private String gpsLongitude;
    @JsonProperty("gps_latitude")
    private String gpsLatitude;
    @JsonProperty("gps_address")
    private String gpsAddress;
    private String wifi;
    @JsonProperty("wifi_name")
    private String wifiName;
    private String bettary;
    private String carrier;
    @JsonProperty("tele_num")
    private String teleNum;
    @JsonProperty("app_market")
    private String appMarket;
    @JsonProperty("is_root")
    private String isRoot;
    private String dns;
    @JsonProperty("is_simulator")
    private String isSimulator;
    @JsonProperty("last_login_time")
    private String lastLoginTime;
    @JsonProperty("pic_count")
    private String picCount;
    @JsonProperty("android_id")
    private String androidId;
    private String udid;
    private String imsi;
    private String mac;
    private String sdcard;
    @JsonProperty("unuse_sdcard")
    private String unuseSdcard;
    private String idfv;
    private String uuid;
    private String idfa;
    @JsonProperty("tongdun_device_id")
    private String tongdunDeviceId;
    @JsonProperty("baiqishi_device_id")
    private String baiqishiDeviceId;
}