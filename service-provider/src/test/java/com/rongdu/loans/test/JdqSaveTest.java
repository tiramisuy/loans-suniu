package com.rongdu.loans.test;

import com.rongdu.common.test.SpringTransactionalContextTests;
import com.rongdu.common.utils.SpringContextHolder;
import com.rongdu.loans.loan.option.jdq.IntoOrder;
import com.rongdu.loans.loan.service.impl.JDQServiceImpl;
import org.junit.Test;

/**
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/5/17
 */
public class JdqSaveTest extends SpringTransactionalContextTests {

    @Test
    public void testSave() {
        String orderSn = "R556158989199209035";
        JDQServiceImpl jdqService = SpringContextHolder.getBean("jdqService");
        IntoOrder base = jdqService.getPushBaseData(orderSn);
        IntoOrder additional =jdqService.getPushBaseTwoData(orderSn);
        jdqService.savejdqData(base, additional);
    }


}
