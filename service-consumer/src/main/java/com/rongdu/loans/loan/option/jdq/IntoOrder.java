package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-10-12 10:27:22
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
@Data
public class IntoOrder implements Serializable {

    private static final long serialVersionUID = 6124253060342201461L;
    @JsonProperty("user_info")
    private UserInfo userInfo;

    @JsonProperty("loan_info")
    private LoanInfo loanInfo;

    @JsonProperty("moxie_telecom")
    private MoxieTelecom moxieTelecom;

    @JsonProperty("moxie_telecom_report")
    private MoxieTelecomReport moxieTelecomReport;

    @JsonProperty("face_result")
    private FaceResult faceResult;

    @JsonProperty("face_result_active")
    private FaceResultActive faceResultActive;

    @JsonProperty("jdq_order_id")
    private String jdqOrderId;

    @JsonProperty("agree_contract_flag")
    private Integer agreeContractFlag;

    @JsonProperty("user_contact")
    private UserContact userContact;

    @JsonProperty("device_info")
    private DeviceInfo deviceInfo;

    @JsonProperty("address_book")
    private List<AddressBook> addressBook;

    @JsonProperty("gps")
    private Gps gps;

    private Telecom telecom;

    @JsonProperty("user_login_upload_log")
    private UserLoginUploadLog userLoginUploadLog;

    private String channelCode;
}