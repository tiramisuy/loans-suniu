package com.rongdu.loans.loan.option.xjbk;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/6/21.
 */
@Data
public class FileUploadResult implements Serializable {
    private static final long serialVersionUID = -3306403494955551806L;
    private String code;
    private String msg;
    private Object data;
}
