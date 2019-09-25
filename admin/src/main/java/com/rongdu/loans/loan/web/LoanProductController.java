package com.rongdu.loans.loan.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rongdu.common.config.Global;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.enums.ChannelEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.loan.option.PromotionOP;
import com.rongdu.loans.loan.service.LoanProductService;
import com.rongdu.loans.loan.vo.PromotionCaseVO;
import com.rongdu.loans.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/loan/promotion")
public class LoanProductController extends BaseController {


	@Autowired
	private LoanProductService loanProductService;
	
	/**
	 * 
	 * 
	 * @param applyOP
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list(PromotionOP op, Model model) {
		model.addAttribute("op", op);
		
		op.setProductId(LoanProductEnum.XJD.getId());
		op.setChannelId(ChannelEnum.JUQIANBAO.getCode());
		// 查询页面默认的多种状态
		Page<PromotionCaseVO> voPage = null;
		voPage = loanProductService.getLoanProductDetail(op);
		model.addAttribute("page", voPage);
		return "modules/loan/loanPromotion";
	}
	
	
	
	
	
	/**
	 * 设置为默认
	 */
	@ResponseBody
	@RequestMapping(value = "resetDefault")
	public WebResult resetDefault(@RequestParam(value = "id") String id,@RequestParam(value = "productId") String productId) {
		try {
			int fla =0;
			synchronized (LoanProductController.class) {
				fla = loanProductService.updatePromotion(id);
			}
			if(fla>0){
				String cacheKey = productId + Global.LOAN_PRO_SUFFIX + "_NEW";
				JedisUtils.delObject(cacheKey);//清除缓存
			}else{
				return new WebResult("99", "操作异常");
			}
			return new WebResult("1", "提交成功", null);
		} catch (Exception e) {
			logger.error("操作异常：applyId = " + id, e);
			return new WebResult("99", "操作异常");
		}
	}
}
