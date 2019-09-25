package com.rongdu.common.persistence;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.*;
import tk.mybatis.mapper.common.base.BaseInsertMapper;
import tk.mybatis.mapper.common.base.BaseSelectMapper;
import tk.mybatis.mapper.common.base.BaseUpdateMapper;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author yuanxianchu
 * @create 2019/5/23
 * @since 1.0.0
 */
@RegisterMapper
public interface MyBaseMapper<T> extends BaseSelectMapper<T>,
        BaseInsertMapper<T>,
        BaseUpdateMapper<T>,
        ExampleMapper<T>,
        RowBoundsMapper<T>,
        Marker,
        IdsMapper<T>, MySqlMapper<T> {

}
