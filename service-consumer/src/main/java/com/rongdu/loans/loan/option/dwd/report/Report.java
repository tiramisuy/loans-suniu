package com.rongdu.loans.loan.option.dwd.report;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
/**
 * Auto-generated: 2019-05-17 16:4:39
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class Report implements Serializable {

    private static final long serialVersionUID = 8680852191620490750L;
    @JsonProperty("cell_behavior")
    private List<CellBehavior> cellBehavior;
    @JsonProperty("deliver_address")
    private List<String> deliverAddress;
    @JsonProperty("contact_region")
    private List<ContactRegion> contactRegion;
    @JsonProperty("application_check")
    private List<ApplicationCheck> applicationCheck;
    @JsonProperty("trip_info")
    private List<String> tripInfo;
    @JsonProperty("main_service")
    private List<MainService> mainService;
    @JsonProperty("user_info_check")
    private UserInfoCheck userInfoCheck;
    @JsonProperty("ebusiness_expense")
    private List<String> ebusinessExpense;
    @JsonProperty("collection_contact")
    private List<CollectionContact> collectionContact;
    @JsonProperty("report")
    private ReportDesc report;
    @JsonProperty("contact_list")
    private List<ContactList> contactList;
    @JsonProperty("behavior_check")
    private List<BehaviorCheck> behaviorCheck;
    @JsonProperty("_id")
    private String Id;
}