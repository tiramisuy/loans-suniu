package com.rongdu.loans.loan.option.xjbk;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2018-06-06 17:34:9
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class TripInfo implements Serializable {

    private static final long serialVersionUID = 8316273741337967717L;
    @JsonProperty("trip_dest")
    private String tripDest;
    @JsonProperty("trip_start_time")
    private Date tripStartTime;
    @JsonProperty("trip_end_time")
    private Date tripEndTime;
    @JsonProperty("trip_transportation")
    private List<String> tripTransportation;
    @JsonProperty("trip_person")
    private List<String> tripPerson;
    @JsonProperty("trip_leave")
    private String tripLeave;
    @JsonProperty("trip_data_source")
    private List<String> tripDataSource;
    @JsonProperty("trip_type")
    private String tripType;
    public void setTripDest(String tripDest) {
         this.tripDest = tripDest;
     }
     public String getTripDest() {
         return tripDest;
     }

    public void setTripStartTime(Date tripStartTime) {
         this.tripStartTime = tripStartTime;
     }
     public Date getTripStartTime() {
         return tripStartTime;
     }

    public void setTripEndTime(Date tripEndTime) {
         this.tripEndTime = tripEndTime;
     }
     public Date getTripEndTime() {
         return tripEndTime;
     }

    public void setTripTransportation(List<String> tripTransportation) {
         this.tripTransportation = tripTransportation;
     }
     public List<String> getTripTransportation() {
         return tripTransportation;
     }

    public void setTripPerson(List<String> tripPerson) {
         this.tripPerson = tripPerson;
     }
     public List<String> getTripPerson() {
         return tripPerson;
     }

    public void setTripLeave(String tripLeave) {
         this.tripLeave = tripLeave;
     }
     public String getTripLeave() {
         return tripLeave;
     }

    public void setTripDataSource(List<String> tripDataSource) {
         this.tripDataSource = tripDataSource;
     }
     public List<String> getTripDataSource() {
         return tripDataSource;
     }

    public void setTripType(String tripType) {
         this.tripType = tripType;
     }
     public String getTripType() {
         return tripType;
     }

}