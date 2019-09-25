package com.rongdu.loans.tlblackrisk.service;

import com.rongdu.loans.tlblackrisk.op.TongLianBlackOP;
import com.rongdu.loans.tlblackrisk.vo.TongLianBlackVO;

/**
 * 通联查询网贷黑名单
 * @author fy
 * @Package com.rongdu.loans.tlblackrisk.service
 * @date 2019/7/25 14:20
 */
public interface TongLianBlackService {

    TongLianBlackVO getBlackDetail(TongLianBlackOP op);
}
