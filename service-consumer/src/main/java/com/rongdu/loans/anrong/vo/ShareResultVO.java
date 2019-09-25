/**
  * Copyright 2019 bejson.com 
  */
package com.rongdu.loans.anrong.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShareResultVO implements Serializable {

    /**
     * 错误信息
     */
    private List<Object> errors;

}