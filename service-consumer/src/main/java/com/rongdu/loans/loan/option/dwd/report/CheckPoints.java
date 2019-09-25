package com.rongdu.loans.loan.option.dwd.report;

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
public class CheckPoints implements Serializable {

    private static final long serialVersionUID = -9140164049271699899L;

    public CourtBlacklist court_blacklist;
    public String key_value;
    public String gender;
    public String age;
    public String province;
    public String city;
    public String region;
    public String website;
    public String reliability;
    public String reg_time;
    public String check_name;
    public String check_idcard;
    public String check_ebusiness;
    public String check_addr;
    public String relationship;
    public String contact_name;
    public String check_xiaohao;
    public String check_mobile;
    public FinancialBlacklist financial_blacklist;

    public static class CourtBlacklist implements Serializable {

        public Boolean arised;
        public List<String> black_type;
    }

    public static class FinancialBlacklist implements Serializable {

        public Boolean arised;
        public List<String> black_type;
    }

}