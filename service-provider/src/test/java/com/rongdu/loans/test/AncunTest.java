package com.rongdu.loans.test;

import com.google.common.collect.Maps;
import com.rongdu.common.config.Global;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.loan.service.CarefreeCounterfoilService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * @author wl_code@163.com
 * @version 1.0
 * @date 2018/12/3
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:**/spring-**.xml"})
public class AncunTest {

    @Test
    public void testGenerate()
    {
        Map<String, String> rePush = Maps.newHashMap();
        rePush.put("R556437819199463575", String.valueOf(System.currentTimeMillis()));
        JedisUtils.mapPut("JDQ:third_key", rePush);
//        carefreeCounterfoilService.generateCardfreeCounterfoil(null);
    }

}
