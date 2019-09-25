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
public class PhotoScore implements Serializable {

    private static final long serialVersionUID = 4231855667600645125L;
    @JsonProperty("ocr_legality_front")
    private OcrLegalityFront ocrLegalityFront;
    @JsonProperty("ocr_legality_back")
    private OcrLegalityBack ocrLegalityBack;

}