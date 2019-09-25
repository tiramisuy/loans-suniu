package com.rongdu.loans.loan.service;

import com.rongdu.loans.loan.option.CollectionRecordOP;
import com.rongdu.loans.loan.vo.CollectionRecordVO;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 催收记录-业务逻辑接口
 * @author zhangxiaolong
 * @version 2017-10-9
 */
public interface CollectionRecordService {

    Integer save(@NotNull(message = "参数不能为空")CollectionRecordOP op);

    List<CollectionRecordVO> list(@NotBlank(message = "参数不能为空")String itemId);
}
