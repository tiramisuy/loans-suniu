package com.rongdu.loans.api.web.aspect;

import com.rongdu.loans.api.common.Rong360Util;
import com.rongdu.loans.api.common.StringUtils;
import com.rongdu.loans.loan.option.rong360Model.Rong360Req;
import com.rongdu.loans.loan.option.rong360Model.Rong360Resp;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by lee on 2018/7/18.
 */
@Slf4j
@Aspect
@Component
public class CheckSignAspect {

    @Around("@annotation(com.rongdu.loans.api.web.annotation.CheckSign)")
    public Object doCheck(ProceedingJoinPoint pjp) throws Throwable {
        log.info("融360执行校验：{}", pjp.getSignature().getName());
        Object[] arguments = pjp.getArgs();
        Rong360Req rong360Req = (Rong360Req) arguments[0];
        String check = Rong360Util.check(rong360Req);
        if (StringUtils.isNoneBlank(check)) {
            Rong360Resp resp = new Rong360Resp();
            log.info("融360校验失败：{},{}", pjp.getSignature().getName(), rong360Req);
            resp.setCode("101");
            resp.setMsg(check);
            return resp;
        } else {
            log.info("融360校验成功：{},{}", pjp.getSignature().getName(), rong360Req);
            Object object = pjp.proceed();
            return object;
        }
    }
}
