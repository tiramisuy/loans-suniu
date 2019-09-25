package com.rongdu.loans.common.fmk;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * freemarker 工具类
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2018/12/3
 */
public class Fmkr {

    public static Configuration cfg = null;

    /**
     * 构建
     *
     * @param ftlFileName  模板文件名
     * @param data         写入数据
     * @param fmkrCallback 回调接口
     */
    public static String builder(String ftlFileName, Map<String, Object> data, FmkrCallback fmkrCallback) {
        StringWriter osw = null;
        String url = "";
//        OutputStreamWriter osw = null;
        try {
//            osw = new OutputStreamWriter(new FileOutputStream(toFileName, false), "UTF-8");
            osw = new StringWriter();
            Template template = cfg.getTemplate(ftlFileName);
            template.process(data, osw);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (osw != null) {
                    osw.flush();
                }
                if (fmkrCallback != null) {
                    try {
                        url = fmkrCallback.handler(osw.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (osw != null) {
                    osw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return url;
            }

        }

    }

}
