package com.rongdu.loans.anrong.service;

import com.rongdu.loans.anrong.op.IterationShareOP;
import com.rongdu.loans.anrong.vo.IterationShareVO;
import com.rongdu.loans.anrong.vo.MSPReprtVO;
import com.rongdu.loans.anrong.vo.ShareResultVO;
import com.rongdu.loans.loan.vo.LoanApplySimpleVO;

import java.util.Map;

/**
 * 安融-业务接口
 * @author fy
 * @version 2019-06-17
 */
public interface AnRongService {
    MSPReprtVO getMSPReport(LoanApplySimpleVO vo,String regCity);

    IterationShareVO getIterationShareList(IterationShareOP op);

    ShareResultVO sendShareApproveResult(Map<String,String> param);

    ShareResultVO sendShareOrderResult(Map<String,String> param);
}
