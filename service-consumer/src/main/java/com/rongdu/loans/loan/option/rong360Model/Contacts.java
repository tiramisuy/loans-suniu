package com.rongdu.loans.loan.option.rong360Model;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * Auto-generated: 2018-07-12 10:2:17
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Contacts implements Serializable{

	private static final long serialVersionUID = -8220684283790912380L;
	
	private String id;
    private String uid;
    @JsonProperty("device_num")
    private String deviceNum;
    @JsonProperty("device_info")
    private String deviceInfo;
    private String platform;
    @JsonProperty("installed_apps")
    private String installedApps;
    @JsonProperty("installed_apps_version")
    private int installedAppsVersion;
    @JsonProperty("app_location_json")
    private String appLocationJson;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("update_time")
    private String updateTime;
    @JsonProperty("delete_time")
    private String deleteTime;
    @JsonProperty("phone_list")
    private List<PhoneList> phoneList;
    @JsonProperty("call_log")
    private List<CallLog> callLog;
    @JsonProperty("app_location")
    private AppLocation appLocation;

}