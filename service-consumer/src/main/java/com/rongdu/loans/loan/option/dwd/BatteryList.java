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
public class BatteryList implements Serializable{

    private static final long serialVersionUID = -8442181810661199458L;
    private String batterystatus;
    private String level;
    private String scale;
    private String temperature;
    private String voltage;
    public void setBatterystatus(String batterystatus) {
         this.batterystatus = batterystatus;
     }
     public String getBatterystatus() {
         return batterystatus;
     }

    public void setLevel(String level) {
         this.level = level;
     }
     public String getLevel() {
         return level;
     }

    public void setScale(String scale) {
         this.scale = scale;
     }
     public String getScale() {
         return scale;
     }

    public void setTemperature(String temperature) {
         this.temperature = temperature;
     }
     public String getTemperature() {
         return temperature;
     }

    public void setVoltage(String voltage) {
         this.voltage = voltage;
     }
     public String getVoltage() {
         return voltage;
     }

}