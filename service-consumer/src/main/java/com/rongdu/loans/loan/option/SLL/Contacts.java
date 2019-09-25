package com.rongdu.loans.loan.option.SLL;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Auto-generated: 2018-12-10 13:56:59
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Contacts implements Serializable{

    private static final long serialVersionUID = -3238620959719097636L;
    @JsonProperty("device_num")
    private String deviceNum;
    private String platform;
    @JsonProperty("device_info")
    private String deviceInfo;
    @JsonProperty("app_location")
    private AppLocation appLocation;
    @JsonProperty("installed_apps")
    private List<InstalledApps> installedApps;
    @JsonProperty("phone_list")
    private List<PhoneList> phoneList;
    @JsonProperty("call_log")
    private List<String> callLog;

}