package com.rongdu.loans.loan.manager;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.loans.loan.dao.LoanProductTermDAO;
import com.rongdu.loans.loan.entity.LoanProductTerm;
import com.rongdu.loans.loan.option.LoanProductTermOP;
import com.rongdu.loans.loan.vo.LoanProductTermVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangxiaolong on 2017/6/29.
 */
@Service("loanProductTermManager")
public class LoanProductTermManager {

    @Autowired
    private LoanProductTermDAO loanProductTermDao;

    /**
     * 插入数据
     * @param op
     * @return
     */
    public int saveLoanProductTerm(LoanProductTermOP op) {
        LoanProductTerm loanProductTerm = new LoanProductTerm();

        // 清理产品信息缓存
        JedisUtils.delObject(op.getProductId()+ Global.LOAN_PRO_SUFFIX);

        loanProductTerm.setTermUnit(op.getTermUnit());
        loanProductTerm.setTerm(op.getTerm());
        loanProductTerm.setProductId(op.getProductId());
        loanProductTerm.setCreateBy("system");
        loanProductTerm.setUpdateBy("system");
        loanProductTerm.preInsert();
        return loanProductTermDao.insert(loanProductTerm);
    }

    /**
     * 通过产品代码获取产品周期列表
     * @param productId
     * @return
     */
    public List<LoanProductTermVO> getByProductId(String productId) {
        return loanProductTermDao.getByProductId(productId);
    }

}
