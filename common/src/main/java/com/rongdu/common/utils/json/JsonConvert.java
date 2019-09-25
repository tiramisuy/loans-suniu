package com.rongdu.common.utils.json;

import java.util.List;

/**
 * 数据接口转换器
 * @author wl_code@163.com
 * @version 1.0
 * @date 2018/12/3
 * 
 */
public interface JsonConvert<T> {
    /**
     * <b>转换</b>
     * <p>
     * 转换
     * </p>
     *
     * @param json 数据
     * @return 转换好的集合
     */
    List<T> parser(String json);

}
