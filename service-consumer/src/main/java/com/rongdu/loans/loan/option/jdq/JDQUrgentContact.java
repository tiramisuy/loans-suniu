package com.rongdu.loans.loan.option.jdq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/6/11.
 */
@Data
public class JDQUrgentContact implements Serializable {
    private static final long serialVersionUID = -3213491953900402132L;
    private String name;
    private String mobile;
    private String relation;
    @JsonProperty("contact_noon")
    private int contactNoon;
    @JsonProperty("phone_num_loc")
    private String phoneNumLoc;
    @JsonProperty("contact_3m")
    private int contact3m;
    @JsonProperty("contact_1m")
    private int contact1m;
    @JsonProperty("contact_1w")
    private int contact1w;
    @JsonProperty("p_relation")
    private String pRelation;
    @JsonProperty("phone_num")
    private String phoneNum;
    @JsonProperty("contact_name")
    private String contactName;
    @JsonProperty("call_in_cnt")
    private int callInCnt;
    @JsonProperty("call_out_cnt")
    private int callOutCnt;
    /**
     * 呼出时间:秒
     */
    @JsonProperty("call_out_len")
    private int callOutLen;
    /**
     * 呼出时间:时分秒
     */
    @JsonProperty("call_out_len_str")
    private String callOutLenStr;
    @JsonProperty("contact_holiday")
    private int contactHoliday;
    @JsonProperty("needs_type")
    private String needsType;
    @JsonProperty("contact_weekday")
    private int contactWeekday;
    @JsonProperty("contact_afternoon")
    private int contactAfternoon;
    @JsonProperty("call_len")
    private double callLen;
    @JsonProperty("contact_early_morning")
    private int contactEarlyMorning;
    @JsonProperty("contact_night")
    private int contactNight;
    @JsonProperty("contact_3m_plus")
    private int contact3mPlus;
    @JsonProperty("call_cnt")
    private int callCnt;
    /**
     * 呼入时间:秒
     */
    @JsonProperty("call_in_len")
    private double callInLen;
    /**
     *呼入时间时分秒
     */
    @JsonProperty("call_in_len_str")
    private String callInLenStr;
    @JsonProperty("contact_all_day")
    private boolean contactAllDay;
    @JsonProperty("contact_morning")
    private int contactMorning;
    @JsonProperty("contact_weekend")
    private int contactWeekend;

    private String lastCallIn;
    private String firstCallIn;

    private String lastCallOut;
    private String firstCallOut;

}
