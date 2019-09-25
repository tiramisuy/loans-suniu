package com.rongdu.loans.loan.option.xjbk;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class ApplyThirdOP implements Serializable {

    private static final long serialVersionUID = 6217468386813584730L;

    private Date applyTimeStart;
    private Date applyTimeEnd;
    private List<Integer> statusList;
    private String notice;

}
