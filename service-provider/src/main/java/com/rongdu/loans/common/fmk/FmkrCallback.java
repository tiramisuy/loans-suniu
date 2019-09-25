package com.rongdu.loans.common.fmk;

/**
 * freemarker 回调函数
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2018/12/3
 */
public interface FmkrCallback {
    String handler(Object data) throws Exception;
}
