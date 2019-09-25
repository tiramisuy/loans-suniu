package com.rongdu.loans.test;

import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.service.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈基础测试类〉
 *
 * @author yuanxianchu
 * @create 2019/5/23
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:**/spring-**.xml")
@ActiveProfiles(value = "test")
@Slf4j
public class TestBase {
	@Autowired
	private SystemService systemService;


	@Test
	public void test() {
	}

	@Test
	public void test1() {

	}

}
