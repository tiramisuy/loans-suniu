package com.rongdu.loans.loan.option.xjbk;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-05-23 9:41:38
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class OperatorReportVerify implements Serializable{

    private static final long serialVersionUID = 6765455378546114350L;
    private Report report;
    @JsonProperty("user_info_check")
    private UserInfoCheck userInfoCheck;
    @JsonProperty("main_service")
    private List<MainService> mainService;
    @JsonProperty("contact_list")
    private List<ContactList> contactList;
    @JsonProperty("behavior_check")
    private List<BehaviorCheck> behaviorCheck;
    @JsonProperty("ebusiness_expense")
    private List<EbusinessExpense> ebusinessExpense;
    @JsonProperty("contact_region")
    private List<ContactRegion> contactRegion;
    @JsonProperty("application_check")
    private List<ApplicationCheck> applicationCheck;
    @JsonProperty("cell_behavior")
    private List<CellBehavior> cellBehavior;
    @JsonProperty("trip_info")
    private List<TripInfo> tripInfo;
    @JsonProperty("_id")
    private String Id;

}