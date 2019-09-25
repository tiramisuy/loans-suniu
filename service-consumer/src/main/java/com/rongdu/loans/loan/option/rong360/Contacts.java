package com.rongdu.loans.loan.option.rong360;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Auto-generated: 2018-06-29 11:7:0
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Contacts implements Serializable{

	private static final long serialVersionUID = -9147970697926810233L;
	
	@JsonProperty("phone_list")
    private List<String> phoneList;
    @JsonProperty("call_log")
    private List<String> callLog;
    @JsonProperty("installed_apps")
    private String installedApps;
    @JsonProperty("installed_apps_version")
    private int installedAppsVersion;
    @JsonProperty("app_location")
    private AppLocation appLocation;
    @JsonProperty("device_num")
    private String deviceNum;
    private String platform;
    @JsonProperty("device_info")
    private String deviceInfo;
    public void setPhoneList(List<String> phoneList) {
         this.phoneList = phoneList;
     }
     public List<String> getPhoneList() {
         return phoneList;
     }

    public void setCallLog(List<String> callLog) {
         this.callLog = callLog;
     }
     public List<String> getCallLog() {
         return callLog;
     }

    public void setInstalledApps(String installedApps) {
         this.installedApps = installedApps;
     }
     public String getInstalledApps() {
         return installedApps;
     }

    public void setInstalledAppsVersion(int installedAppsVersion) {
         this.installedAppsVersion = installedAppsVersion;
     }
     public int getInstalledAppsVersion() {
         return installedAppsVersion;
     }

    public void setAppLocation(AppLocation appLocation) {
         this.appLocation = appLocation;
     }
     public AppLocation getAppLocation() {
         return appLocation;
     }

    public void setDeviceNum(String deviceNum) {
         this.deviceNum = deviceNum;
     }
     public String getDeviceNum() {
         return deviceNum;
     }

    public void setPlatform(String platform) {
         this.platform = platform;
     }
     public String getPlatform() {
         return platform;
     }

    public void setDeviceInfo(String deviceInfo) {
         this.deviceInfo = deviceInfo;
     }
     public String getDeviceInfo() {
         return deviceInfo;
     }

}