package com.rongdu.loans.loan.option;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/9/5.
 */
@Data
public class LiangHuaPaiDataVO implements Serializable {

    private static final long serialVersionUID = -4078789893004086593L;
    private String url;
    private String flowNo;

    public static LiangHuaPaiDataVO getInstance(String url, String flowNo) {
        LiangHuaPaiDataVO liangHuaPaiDataVO = new LiangHuaPaiDataVO();
        liangHuaPaiDataVO.setFlowNo(flowNo);
        liangHuaPaiDataVO.setUrl(url);
        return liangHuaPaiDataVO;
    }

}
