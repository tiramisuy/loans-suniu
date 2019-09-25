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
public class GyroList implements Serializable {

    private static final long serialVersionUID = -692837987252266557L;
    private String gyroscopex;
    private String gyroscopey;
    private String gyroscopez;
    public void setGyroscopex(String gyroscopex) {
         this.gyroscopex = gyroscopex;
     }
     public String getGyroscopex() {
         return gyroscopex;
     }

    public void setGyroscopey(String gyroscopey) {
         this.gyroscopey = gyroscopey;
     }
     public String getGyroscopey() {
         return gyroscopey;
     }

    public void setGyroscopez(String gyroscopez) {
         this.gyroscopez = gyroscopez;
     }
     public String getGyroscopez() {
         return gyroscopez;
     }

}