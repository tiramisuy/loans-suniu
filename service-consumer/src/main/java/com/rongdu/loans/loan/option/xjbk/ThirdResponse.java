package com.rongdu.loans.loan.option.xjbk;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/6/1.
 */
@Data
public class ThirdResponse implements Serializable {
    private static final long serialVersionUID = 5541948604077476050L;
    private String status;
    private String message;
    private String response;
}
