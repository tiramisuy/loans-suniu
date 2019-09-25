package com.rongdu.loans.loan.option.dwd.report;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Auto-generated: 2019-05-17 16:4:39
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class UserInfoCheck implements Serializable {

    private static final long serialVersionUID = -4204160693238374261L;
    @JsonProperty("check_search_info")
    private CheckSearchInfo checkSearchInfo;
    @JsonProperty("check_black_info")
    private CheckBlackInfo checkBlackInfo;
    public void setCheckSearchInfo(CheckSearchInfo checkSearchInfo) {
         this.checkSearchInfo = checkSearchInfo;
     }
     public CheckSearchInfo getCheckSearchInfo() {
         return checkSearchInfo;
     }

    public void setCheckBlackInfo(CheckBlackInfo checkBlackInfo) {
         this.checkBlackInfo = checkBlackInfo;
     }
     public CheckBlackInfo getCheckBlackInfo() {
         return checkBlackInfo;
     }

}