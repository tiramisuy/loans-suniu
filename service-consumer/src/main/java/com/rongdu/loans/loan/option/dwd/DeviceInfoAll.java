package com.rongdu.loans.loan.option.dwd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-10-30 16:11:56
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class DeviceInfoAll  implements Serializable{

    private static final long serialVersionUID = 7575666051874457674L;
    @JsonProperty("android_id")
    private String androidId;
    @JsonProperty("android_ver")
    private String androidVer;
    @JsonProperty("ava_storage_size")
    private String avaStorageSize;
    @JsonProperty("device_model")
    private String deviceModel;
    private String idfv;
    private String imei;
    private String mac;
    @JsonProperty("mem_size")
    private String memSize;
    @JsonProperty("seria_no")
    private String seriaNo;
    @JsonProperty("storage_size")
    private String storageSize;
    public void setAndroidId(String androidId) {
         this.androidId = androidId;
     }
     public String getAndroidId() {
         return androidId;
     }

    public void setAndroidVer(String androidVer) {
         this.androidVer = androidVer;
     }
     public String getAndroidVer() {
         return androidVer;
     }

    public void setAvaStorageSize(String avaStorageSize) {
         this.avaStorageSize = avaStorageSize;
     }
     public String getAvaStorageSize() {
         return avaStorageSize;
     }

    public void setDeviceModel(String deviceModel) {
         this.deviceModel = deviceModel;
     }
     public String getDeviceModel() {
         return deviceModel;
     }

    public void setIdfv(String idfv) {
         this.idfv = idfv;
     }
     public String getIdfv() {
         return idfv;
     }

    public void setImei(String imei) {
         this.imei = imei;
     }
     public String getImei() {
         return imei;
     }

    public void setMac(String mac) {
         this.mac = mac;
     }
     public String getMac() {
         return mac;
     }

    public void setMemSize(String memSize) {
         this.memSize = memSize;
     }
     public String getMemSize() {
         return memSize;
     }

    public void setSeriaNo(String seriaNo) {
         this.seriaNo = seriaNo;
     }
     public String getSeriaNo() {
         return seriaNo;
     }

    public void setStorageSize(String storageSize) {
         this.storageSize = storageSize;
     }
     public String getStorageSize() {
         return storageSize;
     }

}