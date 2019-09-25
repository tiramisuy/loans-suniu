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
public class OcrLegalityBack implements Serializable {

    private static final long serialVersionUID = -9066517430908990532L;
    @JsonProperty("id_photo")
    private int idPhoto;
    @JsonProperty("temporary_id_photo")
    private int temporaryIdPhoto;
    private int photocopy;
    private int screen;
    private int edited;


}