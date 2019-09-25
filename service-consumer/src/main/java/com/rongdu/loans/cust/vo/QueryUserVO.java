package com.rongdu.loans.cust.vo;

import java.io.Serializable;
import java.util.List;

import com.rongdu.loans.loan.vo.CheckLogVO;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
public class QueryUserVO implements Serializable {
    private static final long serialVersionUID = -7472436330059264845L;

    /**
     * 客户信息
     */
    private UserInfoVO userInfoVO;
    /**
     * 贷款审核记录
     */
    private List<CheckLogVO> checkLogVOList;
    /**
     * 进件大纲
     */
    private List<CustHitRuleVO> juqianbao;
    /**
     * 黑名单
     */
    private List<CustHitRuleVO> juqianbaoBlackList;
    /**
     * 三方规则
     */
    private List<CustHitRuleVO> third;
    /**
     * 三方黑名单
     */
    private List<CustHitRuleVO> thirdBlackList;
    /**
     * 是否显示更多
     */
    private Integer more = 0;

    public List<CheckLogVO> getCheckLogVOList() {
        return checkLogVOList;
    }

    public void setCheckLogVOList(List<CheckLogVO> checkLogVOList) {
        this.checkLogVOList = checkLogVOList;
    }
    public UserInfoVO getUserInfoVO() {
        return userInfoVO;
    }

    public void setUserInfoVO(UserInfoVO userInfoVO) {
        this.userInfoVO = userInfoVO;
    }

    public List<CustHitRuleVO> getJuqianbao() {
        return juqianbao;
    }

    public void setJuqianbao(List<CustHitRuleVO> juqianbao) {
        this.juqianbao = juqianbao;
    }

    public List<CustHitRuleVO> getJuqianbaoBlackList() {
        return juqianbaoBlackList;
    }

    public void setJuqianbaoBlackList(List<CustHitRuleVO> juqianbaoBlackList) {
        this.juqianbaoBlackList = juqianbaoBlackList;
    }

    public List<CustHitRuleVO> getThird() {
        return third;
    }

    public void setThird(List<CustHitRuleVO> third) {
        this.third = third;
    }

    public List<CustHitRuleVO> getThirdBlackList() {
        return thirdBlackList;
    }

    public void setThirdBlackList(List<CustHitRuleVO> thirdBlackList) {
        this.thirdBlackList = thirdBlackList;
    }

    public Integer getMore() {
        return more;
    }

    public void setMore(Integer more) {
        this.more = more;
    }
}
