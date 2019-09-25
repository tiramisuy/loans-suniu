package com.rongdu.loans.loan.option.dwd;

import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2018-10-30 16:11:56
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class InstalledApps implements Serializable{

    private static final long serialVersionUID = 3321352843636958980L;
    private String appname;
    private String packagename;
    private String versionname;
    private String versionnumber;
    public void setAppname(String appname) {
         this.appname = appname;
     }
     public String getAppname() {
         return appname;
     }

    public void setPackagename(String packagename) {
         this.packagename = packagename;
     }
     public String getPackagename() {
         return packagename;
     }

    public void setVersionname(String versionname) {
         this.versionname = versionname;
     }
     public String getVersionname() {
         return versionname;
     }

    public void setVersionnumber(String versionnumber) {
         this.versionnumber = versionnumber;
     }
     public String getVersionnumber() {
         return versionnumber;
     }

}