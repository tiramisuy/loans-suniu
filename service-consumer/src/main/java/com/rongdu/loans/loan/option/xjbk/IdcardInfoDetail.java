package com.rongdu.loans.loan.option.xjbk;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lee on 2018/5/23.
 */
@Data
public class IdcardInfoDetail implements Serializable{
    private static final long serialVersionUID = -7601246976755768966L;
    private String face_recognition_picture;
    private String id_number_z_picture;
    private String id_number_f_picture;
    private String ocr_race;
    private String ocr_address;
    private String ocr_name;
    private String ocr_id_number;
    private String ocr_sex;
    private Date ocr_birthday;
    private String ocr_issued_by;
    private String ocr_start_time;
    private String ocr_end_time;
}
