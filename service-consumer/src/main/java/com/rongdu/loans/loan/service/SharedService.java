package com.rongdu.loans.loan.service;

import com.rongdu.loans.loan.option.share.CustInfo;
import com.rongdu.loans.loan.option.share.JCUserInfo;

/**
 * 〈一句话功能简述〉<br>
 * 〈共享服务类〉
 *
 * @author yuanxianchu
 * @create 2019/5/20
 * @since 1.0.0
 */
public interface SharedService {

    /**
     * 推送进件用户基础三要素
     */
    boolean pushCustInfo(CustInfo custInfo);

    boolean shareToJuCai(JCUserInfo userInfo);
}
