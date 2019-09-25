package com.rongdu.loans.loan.option;

import com.alibaba.druid.sql.dialect.mysql.ast.MysqlForeignKey;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lee on 2018/9/5.
 */
@Data
public class LiangHuaPaiVO implements Serializable {

    private static final long serialVersionUID = 620020240945643754L;
    private String flag;
    private String code;
    private String msg;
    private LiangHuaPaiDataVO data;

    public static LiangHuaPaiVO getInstance(String flag, String code, String msg, LiangHuaPaiDataVO data) {
        LiangHuaPaiVO liangHuaPaiVO = new LiangHuaPaiVO();
        liangHuaPaiVO.setFlag(flag);
        liangHuaPaiVO.setCode(code);
        liangHuaPaiVO.setMsg(msg);
        liangHuaPaiVO.setData(data);
        return liangHuaPaiVO;
    }

}
