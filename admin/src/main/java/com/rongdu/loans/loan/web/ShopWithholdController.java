package com.rongdu.loans.loan.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.BankLimitUtils;
import com.rongdu.loans.cust.entity.CustUser;
import com.rongdu.loans.cust.service.CustUserService;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.enums.PayTypeEnum;
import com.rongdu.loans.loan.service.RepayLogService;
import com.rongdu.loans.loan.vo.RepayLogVO;
import com.rongdu.loans.pay.op.*;
import com.rongdu.loans.pay.service.*;
import com.rongdu.loans.pay.vo.TlAgreementPayResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.internal.LinkedTreeMap;
import com.rongdu.common.annotation.ExportLimit;
import com.rongdu.common.persistence.Page;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.excel.ExportExcel;
import com.rongdu.common.web.BaseController;
import com.rongdu.loans.common.IdUtils;
import com.rongdu.loans.common.WebResult;
import com.rongdu.loans.enums.BankCodeEnum;
import com.rongdu.loans.enums.HaiErBankCodeEnum;
import com.rongdu.loans.loan.option.ShopWithholdOP;
import com.rongdu.loans.loan.service.LoanApplyService;
import com.rongdu.loans.loan.service.ShopWithholdService;
import com.rongdu.loans.loan.vo.RepayDetailListVO;
import com.rongdu.loans.loan.vo.RepayReportVo;
import com.rongdu.loans.loan.vo.ShopWithholdVO;
import com.rongdu.loans.pay.vo.XfAgreementPayResultVO;
import com.rongdu.loans.sys.entity.Log;
import com.rongdu.loans.sys.entity.User;
import com.rongdu.loans.sys.service.LogService;
import com.rongdu.loans.sys.utils.UserUtils;

/**
 * Created by zhangxiaolong on 2017/6/27.
 */
@Controller
@RequestMapping(value = "${adminPath}/loan/shopWithHold")
public class ShopWithholdController extends BaseController {

    @Autowired
    private ShopWithholdService shopWithholdService;

    @Autowired
    private LoanApplyService loanApplyService;

    @Autowired
    private XianFengAgreementPayService xianfengPayService;

    @Autowired
    private TltAgreementPayService tltAgreementPayService;

    @Autowired
    private LogService logService;
    @Autowired
    private KjtpayService kjtpayService;

    @Autowired
    private CustUserService custUserService;

    @Autowired
    private BaofooWithholdService baofooWithholdService;

    @Autowired
    private XianFengWithdrawService xianFengWithdrawService;

    @Autowired
    private RepayLogService repayLogService;

    /**
     * 系统代扣三次失败的代扣信息列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "withHoldList")
    public String withHoldList(@ModelAttribute("ShopWithholdOP") ShopWithholdOP op, Boolean first, Model model) {
        if (null != first && first) {
            model.addAttribute("page", new Page(1, 10));
            return "modules/loan/shopWithholdList";
        }
        model.addAttribute("ShopWithholdOP", op);
        model.addAttribute("page", shopWithholdService.selectShopWithholdList(op));
        return "modules/loan/shopWithholdList";
    }

    /**
     * 导出代扣失败信息
     *
     * @param user
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "withHoldExport", method = RequestMethod.POST)
    @ExportLimit()
    public void OverDueDataExport(ShopWithholdOP op, HttpServletRequest request, HttpServletResponse response,
                                  RedirectAttributes redirectAttributes) throws IOException {
        User user = UserUtils.getUser();
        logger.info("导出服务费代扣失败数据--->{}--->{}", user.getId(), user.getName());
        ExportExcel excel = null;
        try {
            excel = new ExportExcel("服务费代扣失败信息", ShopWithholdVO.class);
            String fileName = "服务费代扣失败数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            op.setPageSize(-1);
            Page<ShopWithholdVO> page = shopWithholdService.selectShopWithholdList(op);
            if (page != null) {
                List<ShopWithholdVO> result = page.getList();
                excel.setDataList(result).write(response, fileName);
            } else {
                Page<ShopWithholdVO> pages = new Page<ShopWithholdVO>();
            }
        } finally {
            if (excel != null)
                excel.dispose();
        }
    }

    /**
     * 导出还款数据
     *
     * @param user
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "exportRepayPlan", method = RequestMethod.POST)
    @ExportLimit()
    public void exportFile(ShopWithholdOP op, HttpServletRequest request, HttpServletResponse response,
                           RedirectAttributes redirectAttributes) throws IOException {
        User user = UserUtils.getUser();
        logger.info("导出代扣服务费失败还款明细数据--->{}--->{}", user.getId(), user.getName());
        ExportExcel excel = null;
        try {
            excel = new ExportExcel("还款数据", RepayReportVo.class);
            String fileName = "还款明细" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<RepayDetailListVO> list = shopWithholdService.getLoanApply(op);
            for (RepayDetailListVO repayVo : list) {
                repayVo.setEgTisTerm(repayVo.getThisTerm() + "/" + repayVo.getTotalTerm());
            }
            excel.setDataList(list).write(response, fileName);
        } finally {
            if (excel != null)
                excel.dispose();
        }
    }

    /**
     * 手动代扣
     *
     * @param id
     * @param isPush
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "handShopWithHold")
    public WebResult handShopWithHold(@RequestParam(value = "id", required = false) String id) {
        User user = UserUtils.getUser();
        logger.info("服务费手动代扣--->{}--->{}", user.getId(), user.getName());
        // 插入日志
        Log entity = new Log();
        entity.setTitle("服务费代扣记录-手动代扣");
        entity.setCreateBy(user);
        entity.setParams("ShopWithHoldId=" + id);
        logService.save(entity);
        try {
            boolean flag = shopWithholdService.handShopWithhold(id);
            if (flag) {
                return new WebResult("1", "代扣成功！");
            } else {
                return new WebResult("1", "代扣失败！");
            }

        } catch (Exception e) {
            logger.error("手动代扣异常：op = ", e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 线下催还购物款
     *
     * @param applyId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "underLineShopWithHold")
    public WebResult underLineShopWithHold(@RequestParam(value = "applyId", required = false) String applyId,
                                           @RequestParam(value = "chlId", required = true) String chlId,
                                           @RequestParam(value = "chlName", required = true) String chlName) {
        User user = UserUtils.getUser();
        logger.info("服务费代扣线下还款--->{}--->{}", user.getId(), user.getName());
        // 插入日志
        Log entity = new Log();
        entity.setTitle("服务费代扣记录-线下催还购物款");
        entity.setCreateBy(user);
        entity.setParams("applyId=" + applyId);
        logService.save(entity);
        try {
            if (StringUtils.isNoneBlank(applyId)) {
                boolean flag = shopWithholdService.updateShopWithhold(applyId, "线下还款", 0);
                if (flag) {
                    int stu = shopWithholdService.insertRepayLog(applyId, chlId, chlName); // 插入流水记录
                    if (stu > 0) {
                        return new WebResult("1", "操作成功！");
                    } else {
                        return new WebResult("99", "系统异常");
                    }
                } else {
                    return new WebResult("1", "操作失败！");
                }
            } else {
                return new WebResult("99", "系统异常");
            }
        } catch (Exception e) {
            logger.error("线下还款异常：op = ", e);
            return new WebResult("99", "系统异常");
        }
    }

    /**
     * 取消
     *
     * @param applyId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "canselShopWithHold")
    public WebResult canselShopWithHold(@RequestParam(value = "applyId", required = false) String applyId,
                                        @RequestParam(value = "chlId", required = true) String chlId,
                                        @RequestParam(value = "chlName", required = true) String chlName) {
        User user = UserUtils.getUser();
        logger.info("取消放款-->{}--->{}--->{}--->{}", user.getId(), user.getName(), applyId);
        // 插入日志
        Log entity = new Log();
        entity.setTitle("服务费代扣记录-取消放款");
        entity.setCreateBy(user);
        entity.setParams("applyId=" + applyId);
        logService.save(entity);
        try {
            if (StringUtils.isNoneBlank(applyId)) {

                int applyStu = loanApplyService.updateCancelShopWithhold(applyId); // 更改申请表状态
                // 状态为411
                // 推送成功才做update
                if (applyStu == 100) {
                    return new WebResult("99", "订单状态已经超过可取消放款阶段！");
                } else if (applyStu == 101) {
                    return new WebResult("99", "请求平台取消借款接口失败，请联系平台技术人员！");
                } else {
                    boolean flag = shopWithholdService.updateShopWithhold(applyId, "已取消放款", 3);
                    if (flag) {
                        int stu = shopWithholdService.insertRepayLog(applyId, chlId, chlName); // 插入流水记录
                        if (stu > 0) {
                            return new WebResult("1", "操作成功！");
                        } else {
                            return new WebResult("99", "系统异常");
                        }
                    }
                }
            } else {
                return new WebResult("99", "系统异常");
            }
        } catch (Exception e) {
            logger.error("取消还款异常：op = ", e);
            return new WebResult("99", "系统异常");
        }
        return new WebResult("99", "系统异常");
    }

    /**
     * 调用先锋代扣，传入四要素代扣
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "xianFengWithhold")
    public String xianFengWithhold(Model model) {
        return "modules/loan/xianFengWithhold";
    }


    /**
     * 手动先锋代扣
     *
     * @param id
     * @param isPush
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "handXianFengWithHold")
    public WebResult handXianFengWithHold(String userName, String mobile, String bankCard, String withholdAmt,
                                          String remark, String idNo, String channel, String bankCode, String bankName) {
        User user = UserUtils.getUser();
        logger.info("手动代扣--->{}--->{}--->{}--->{}", user.getId(), user.getName(), userName, mobile);
        try {
            if (channel.equals("tonglian")) {
                logger.info("手动代扣==>通联->{}--->{}--->{}--->{}", user.getId(), user.getName(), userName, mobile);


//                XfAgreementAdminPayOP op = new XfAgreementAdminPayOP();
//                op.setAmount(withholdAmt);
//                op.setCardNo(bankCard);
//                op.setIdNo(idNo);
//                op.setMobile(mobile);
//                op.setRemark(remark);
//                op.setUserName(userName);
//                op.setTxType("WH_ADMIN");// 手动代扣
//                XfAgreementPayResultVO vo = xianfengPayService.agreementAdminPay(op);
                CustUserVO custUserVO = custUserService.getCustUserByMobile(mobile);
                if (custUserVO != null) {
//                    WithholdOP param = getWithholdOP(custUserVO, withholdAmt);
                    TlAgreementPayOP payOP = new TlAgreementPayOP();
                    payOP.setBindId(custUserVO.getBindId());
                    payOP.setAmount(new BigDecimal(withholdAmt).multiply(BigDecimal.valueOf(100)).toString());
                    payOP.setUserId(custUserVO.getId());
                    payOP.setRealName(custUserVO.getRealName());
                    payOP.setApplyId("system_initiate");
                    payOP.setPayType(PayTypeEnum.HT_ADMIN.getId());
                    TlAgreementPayResultVO vo = tltAgreementPayService.agreementPay(payOP);
                    //保存流水
                    saveRepayLog(custUserVO, vo, PayTypeEnum.HT_ADMIN.getId(), payOP);
                } else {
                    return new WebResult("99", "系统错误,用户数据不完整...");
                }
            }
//            else if (channel.equals("1")) {
//                logger.info("手动代扣==>先锋->{}--->{}--->{}--->{}", user.getId(), user.getName(), userName, mobile);
//                XfAgreementAdminPayOP op = new XfAgreementAdminPayOP();
//                op.setAmount(withholdAmt);
//                op.setCardNo(bankCard);
//                op.setIdNo(idNo);
//                op.setMobile(mobile);
//                op.setRemark(remark);
//                op.setUserName(userName);
//                op.setTxType("WH_ADMIN");// 手动代扣
//                XfAgreementPayResultVO vo = xianfengPayService.agreementAdminPay(op);
//            } else {
//                logger.info("手动代扣==>宝付->{}--->{}--->{}--->{}--->{}", user.getId(), user.getName(), userName, mobile, bankCard);
//                BfWithholdOP bfop = new BfWithholdOP();
//                bfop.setRealName(userName);
//                bfop.setMobile(mobile);
//                bfop.setCardNo(bankCard);
//                bfop.setBankCode(bankCode);
//                bfop.setTxnAmt(withholdAmt);
//                bfop.setIdNo(idNo);
//                bfop.setRemark(remark);
//                bfop.setTxType("WH_ADMIN");// 手动代扣
//                bfop.setBankName(bankName);
//                baofooWithholdService.handerTransaction(bfop);
//            }
            return new WebResult("1", "提交成功，请在交易流水中查询结果!");
        } catch (Exception e) {
            logger.error("手动代扣异常：op = ", e);
            return new WebResult("99", "系统异常");
        }
    }


    /**
     * 海尔支付代付
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "haiErPay")
    public String haiErPay(Model model) {


        Map<String, String> bankCodeData = new LinkedTreeMap<>();
        for (HaiErBankCodeEnum temp : HaiErBankCodeEnum.values()) {
            bankCodeData.put(temp.getbCode(), temp.getbName());
        }
        model.addAttribute("bankData", bankCodeData);

        return "modules/loan/haiErPay";
    }

    /**
     * 海尔支付代付
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "handHaiErPay")
    @ResponseBody
    public WebResult handHaiErPay(String cardNo, String realName, String bankCode, String amount, String channel) {

        try {
            if (channel.equals("2")) {
                logger.info("手动代付==>先锋->{}--->{}--->{}--->{}--->{}", realName, amount, cardNo, bankCode);
                XfHandelWithdrawOP op = new XfHandelWithdrawOP();
                op.setAmount(amount);
                op.setCardNo(cardNo);
                op.setBankCode(bankCode);
                op.setRealName(realName);
                xianFengWithdrawService.xfHandekWithDraw(op);


            } else {
                kjtpayService.transferToCard(IdUtils.getApplyId("1"), cardNo, realName, bankCode, amount);
            }
        } catch (Exception e) {
            logger.error("海尔支付代付异常：", e);
            return new WebResult("99", "系统异常");
        }
        return new WebResult("1", "代付请求交易成功");
    }


    /**
     * 保存还款记录
     *
     * @param vo
     * @param param
     * @param payType code y0524
     * @return
     */
    private RepayLogVO saveRepayLog(CustUserVO user, TlAgreementPayResultVO vo, Integer payType,TlAgreementPayOP payOP) {
        Date now = new Date();
        RepayLogVO repayLog = new RepayLogVO();
        repayLog.setId(vo.getTradeNo());
        repayLog.setNewRecord(true);
//        repayLog.setApplyId(param.getApplyId());
//        repayLog.setContractId(param.getContNo());
//        repayLog.setRepayPlanItemId(param.getRepayPlanItemId());
        repayLog.setUserId(user.getId());
        repayLog.setUserName(user.getRealName());
        repayLog.setIdNo(user.getIdNo());
        repayLog.setMobile(user.getMobile());
        repayLog.setTxType("WITHHOLD");
        repayLog.setTxDate(Long.parseLong(DateUtils.getDate("yyyyMMdd")));
//        if (StringUtils.isNotBlank(vo.getTradeTime())) {
        repayLog.setTxTime(new Date());
//        }
        BigDecimal txAmt = new BigDecimal(payOP.getAmount())
                .divide(BigDecimal.valueOf(100), Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
        repayLog.setTxAmt(txAmt);
        repayLog.setTxFee(calWithholdFee(txAmt));
        repayLog.setTerminal(String.valueOf(Global.SOURCE_SYSTEM));
        repayLog.setChlOrderNo(vo.getTradeNo());
        repayLog.setChlName("通联支付");
        repayLog.setChlCode("TONGLIAN");
        repayLog.setBindId(StringUtils.isNotBlank(user.getBindId()) ? user.getBindId() : user.getProtocolNo());
        repayLog.setBankCode(user.getBankCode());
        repayLog.setCardNo(user.getCardNo());
        repayLog.setBankName(BankLimitUtils.getNameByBankCode(user.getBankCode()));
//		repayLog.setGoodsName("聚宝钱包还款");
        repayLog.setGoodsName("手动扣款");
        repayLog.setGoodsNum(1);
        repayLog.setStatus(vo.getStatus());
        repayLog.setRemark(vo.getResCode() + "," + vo.getResMessage());
        repayLog.setPayType(payType);
//        if (vo.getSuccess()) {
//            BigDecimal succAmt = new BigDecimal(vo.getAmount())
//                    .divide(BigDecimal.valueOf(100), Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
//            repayLog.setSuccAmt(succAmt);
//            repayLog.setSuccTime(now);
//            repayLog.setStatus(ErrInfo.SUCCESS.getCode());
//        }
        int num = repayLogService.save(repayLog);
        if (num == 0) {
            return null;
        }
        return repayLog;
    }


    /**
     * 计算每笔代扣交易费用
     * 千分之2.5
     *
     * @param fee 单位元
     * @return
     */
    private BigDecimal calWithholdFee(BigDecimal fee) {
        if (fee.compareTo(new BigDecimal(100000)) > 0) {
            return BigDecimal.valueOf(0);
        }
        return BigDecimal.valueOf(0);
    }
}
