package com.rongdu.loans.loan.option.dwd;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Auto-generated: 2018-10-30 16:11:56
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Contacts implements Serializable {

    @JsonProperty("app_location")
    private List<AppLocation> appLocation;
    @JsonProperty("battery_list")
    private List<BatteryList> batteryList;
    @JsonProperty("call_log")
    private List<CallLog> callLog;
    @JsonProperty("device_info")
    private String deviceInfo;
    @JsonProperty("device_num")
    private String deviceNum;
    @JsonProperty("gyro_list")
    private List<GyroList> gyroList;
    @JsonProperty("installed_apps")
    private List<InstalledApps> installedApps;
    @JsonProperty("phone_list")
    private List<PhoneList> phoneList;
    private String platform;
    @JsonProperty("wifi_list")
    private List<WifiList> wifiList;
    public void setAppLocation(List<AppLocation> appLocation) {
         this.appLocation = appLocation;
     }
     public List<AppLocation> getAppLocation() {
         return appLocation;
     }

    public void setBatteryList(List<BatteryList> batteryList) {
         this.batteryList = batteryList;
     }
     public List<BatteryList> getBatteryList() {
         return batteryList;
     }

    public void setCallLog(List<CallLog> callLog) {
         this.callLog = callLog;
     }
     public List<CallLog> getCallLog() {
         return callLog;
     }

    public void setDeviceInfo(String deviceInfo) {
         this.deviceInfo = deviceInfo;
     }
     public String getDeviceInfo() {
         return deviceInfo;
     }

    public void setDeviceNum(String deviceNum) {
         this.deviceNum = deviceNum;
     }
     public String getDeviceNum() {
         return deviceNum;
     }

    public void setGyroList(List<GyroList> gyroList) {
         this.gyroList = gyroList;
     }
     public List<GyroList> getGyroList() {
         return gyroList;
     }

    public void setInstalledApps(List<InstalledApps> installedApps) {
         this.installedApps = installedApps;
     }
     public List<InstalledApps> getInstalledApps() {
         return installedApps;
     }

    public void setPhoneList(List<PhoneList> phoneList) {
         this.phoneList = phoneList;
     }
     public List<PhoneList> getPhoneList() {
         return phoneList;
     }

    public void setPlatform(String platform) {
         this.platform = platform;
     }
     public String getPlatform() {
         return platform;
     }

    public void setWifiList(List<WifiList> wifiList) {
         this.wifiList = wifiList;
     }
     public List<WifiList> getWifiList() {
         return wifiList;
     }

}