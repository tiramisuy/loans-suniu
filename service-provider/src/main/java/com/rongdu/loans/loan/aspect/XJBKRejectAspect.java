package com.rongdu.loans.loan.aspect;

import com.rongdu.common.persistence.criteria.Criteria;
import com.rongdu.common.persistence.criteria.Criterion;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.MD5Util;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.loan.entity.UserMd5;
import com.rongdu.loans.loan.manager.UserMd5Manager;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaCommonOP;
import com.rongdu.loans.loan.option.xjbk.XianJinBaiKaVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lee on 2018/8/27.
 */
@Slf4j
//@Aspect
//@Component
public class XJBKRejectAspect {
    @Autowired
    private CustUserService custUserService;
    @Autowired
    private UserMd5Manager userMd5Manager;

//    @Pointcut(value = "execution(* com.rongdu.loans.loan.service.XianJinBaiKaService.isUserAcceptFQ(..))")
    void saveRejectApplyPointcut() {
    }

//    @Async
//    @AfterReturning(value = "saveRejectApplyPointcut()&&args(xianJinBaiKaCommonRequest)", returning = "xianJinBaiKaVO")
    public void saveRejectApply(XianJinBaiKaCommonOP xianJinBaiKaCommonRequest, XianJinBaiKaVO xianJinBaiKaVO) throws Exception {
        try {
            Map<String, Object> map = (Map<String, Object>) xianJinBaiKaVO.getResponse();
            Date date = new Date();
            String start = "'" + DateUtils.formatDate(date, "yyyy-MM-dd") + " 00:00:00'";
            String end = "'" + DateUtils.formatDate(date, "yyyy-MM-dd") + " 23:59:59'";
            String userName = xianJinBaiKaCommonRequest.getUser_name();
            String userPhone = xianJinBaiKaCommonRequest.getUser_phone();
            String userIdCard = xianJinBaiKaCommonRequest.getUser_idcard();
            userIdCard = userIdCard.replace("*", "%");
            userPhone = userPhone.replace("*", "%");
            CustUserVO custUserVO = custUserService.isRegister(userName, userPhone, userIdCard);
            String mobile = custUserVO.getMobile();
            String idNo = custUserVO.getIdNo();
            String mobileAndIdNo = mobile + idNo;
            String md5 = MD5Util.string2MD5(mobileAndIdNo).toUpperCase();
            String userId = custUserVO.getId();
            UserMd5 userMd5 = new UserMd5();
            userMd5.setMobile(mobile);
            userMd5.setIdNo(idNo);
            userMd5.setMd5(md5);
            userMd5.setUserNo(userId);
            userMd5.setRealName(custUserVO.getRealName());
            Criteria criteria = new Criteria();
            criteria.add(Criterion.eq("md5", md5));
            criteria.and(Criterion.between("create_time", start, end));
            List<UserMd5> userMd5List = userMd5Manager.findAllByCriteria(criteria);
            if (userMd5List.size() == 0) {
                userMd5Manager.insert(userMd5);
            }
        } catch (Exception e) {
        }
    }


}
