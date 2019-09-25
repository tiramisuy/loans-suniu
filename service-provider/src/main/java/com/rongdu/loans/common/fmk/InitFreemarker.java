package com.rongdu.loans.common.fmk;

import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

/**
 * Freemark初始化
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2018/12/3
 */
@Slf4j
@Component
public class InitFreemarker implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * 初始化freemarker配置
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("============初始化freemarker配置==============");
        Fmkr.cfg = new Configuration();
        try {
            Fmkr.cfg.setDirectoryForTemplateLoading(ResourceUtils.getFile("classpath:ftl"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Fmkr.cfg.setDefaultEncoding("UTF-8");
    }
}
