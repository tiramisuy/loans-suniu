package com.rongdu.loans.cust.vo;

import com.rongdu.loans.basic.vo.FileInfoVO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxiaolong on 2017/8/21.
 */
public class UserFileInfoVO implements Serializable {

    private List<FileInfoVO> list;

    private String idNo;

    private String realName;

    public List<FileInfoVO> getList() {
        return list;
    }

    public void setList(List<FileInfoVO> list) {
        this.list = list;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
