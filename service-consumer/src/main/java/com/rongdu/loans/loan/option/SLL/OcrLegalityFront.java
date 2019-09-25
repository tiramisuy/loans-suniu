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
public class OcrLegalityFront implements Serializable {

    private static final long serialVersionUID = 8138184735971670426L;
    @JsonProperty("id_photo")
    private double idPhoto;
    @JsonProperty("temporary_id_photo")
    private int temporaryIdPhoto;
    private int photocopy;
    private double screen;
    private int edited;

}