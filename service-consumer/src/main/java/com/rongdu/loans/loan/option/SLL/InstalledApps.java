package com.rongdu.loans.loan.option.SLL;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import java.io.Serializable;

/**
 * Auto-generated: 2018-12-10 13:56:59
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class InstalledApps implements Serializable{

    private static final long serialVersionUID = -7977361717456860520L;
    private String name;
    @JsonProperty("packageName")
    private String packagename;
    @JsonProperty("versionName")
    private String versionname;

}