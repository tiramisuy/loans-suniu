package com.rongdu.loans.payroll.web;

import com.allinpay.demo.xstruct.trans.qry.QTDetail;
import com.allinpay.demo.xstruct.trans.qry.QTransRsp;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.loans.common.ApiResult;
import com.rongdu.loans.common.BadRequestException;
import com.rongdu.loans.common.HandlerTypeEnum;
import com.rongdu.loans.loan.vo.PayLogVO;
import com.rongdu.loans.pay.op.ConfirmBindCardOP;
import com.rongdu.loans.pay.op.DirectBindCardOP;
import com.rongdu.loans.pay.op.TlAgreementPayOP;
import com.rongdu.loans.pay.op.TlWithholdQueryOP;
import com.rongdu.loans.pay.service.TltAgreementPayService;
import com.rongdu.loans.pay.vo.BindCardResultVO;
import com.rongdu.loans.pay.vo.TlAgreementPayResultVO;
import com.rongdu.loans.pay.vo.TonglianQueryResultVo;
import com.rongdu.loans.payroll.op.*;
import com.rongdu.loans.payroll.vo.PayRollBindCardVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 发工资
 *
 * @author wl_code@163.com
 * @version 1.0
 * @date 2019/4/10
 */
@Slf4j
@Controller
@RequestMapping("${adminPath}/payRoll/")
public class PayRollController {

    @Autowired
    private TltAgreementPayService tltAgreementPayService;

    /**
     * 绑卡发送短信
     *
     * @param bindCardOp
     * @param errors
     * @return
     */
    @RequestMapping("bindCardPreMsg")
    public ApiResult bindCardPreMsg(@Valid BindCardPreOp bindCardPreOp, Errors errors) {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        ApiResult apiResult = new ApiResult();
        DirectBindCardOP directBindCardOP = new DirectBindCardOP();
        directBindCardOP.setRealName(bindCardPreOp.getAccountName());
        directBindCardOP.setCardNo(bindCardPreOp.getAccountNo());
        directBindCardOP.setMobile(bindCardPreOp.getMobile());
        directBindCardOP.setIdNo(bindCardPreOp.getIdCard());
//        directBindCardOP.setRealName("谢轩");
//        directBindCardOP.setCardNo("6215593202010282773");
//        directBindCardOP.setMobile("18611807455");
//        directBindCardOP.setIdNo("430102198310101013");
        BindCardResultVO bindCardResultVO = null;
        try {
            bindCardResultVO = tltAgreementPayService.agreementPayMsgSend(directBindCardOP, HandlerTypeEnum.SYSTEM_HANDLER);
            //保存
            if (bindCardResultVO.isSuccess()) {
                PayRollBindCardVo rollBindCardVo = new PayRollBindCardVo();
                rollBindCardVo.setAccountName(bindCardPreOp.getAccountName());
                rollBindCardVo.setAccountNo(bindCardPreOp.getAccountNo());
                rollBindCardVo.setIdCard(bindCardPreOp.getIdCard());
                rollBindCardVo.setMobile(bindCardPreOp.getMobile());
                rollBindCardVo.setReqNo(bindCardResultVO.getOrderNo());
                rollBindCardVo.setIsBind("0");
                rollBindCardVo.setBindNo("");
                //TODO 保存
                //String id = payRollBindCardService.save();
                //apiResult.setData(id);
            }
            apiResult.setCode(bindCardResultVO.getCode());
            apiResult.setMsg(bindCardResultVO.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResult;
    }

    /**
     * 绑卡
     *
     * @param bindCardOp
     * @param errors
     * @return
     */
    @RequestMapping("bindCard")
    @ResponseBody
    public ApiResult bindCard(@Valid BindCardOp bindCardOp, Errors errors) {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        ConfirmBindCardOP confirmBindCardOP = new ConfirmBindCardOP();
        try {
//            PayRollBindCardVo payRollBindCardVo = payRollBindCardService.get(bindCardOp.getPayRollBindCardID());
            PayRollBindCardVo payRollBindCardVo = null;


            confirmBindCardOP.setOrderNo("1111111");
            confirmBindCardOP.setSource("4");
            confirmBindCardOP.setType(1);


            //预绑卡流水号
//            confirmBindCardOP.setBindId(payRollBindCardVo.getReqNo());
            confirmBindCardOP.setBindId(bindCardOp.getReqNo());


            confirmBindCardOP.setMsgVerCode(bindCardOp.getPreMsgCode());
            BindCardResultVO bindCardResultVO = tltAgreementPayService.agreementPaySign(confirmBindCardOP);
            if (bindCardResultVO.isSuccess()) {
//                payRollBindCardVo.setBindNo(bindCardResultVO.getBindId());
//                payRollBindCardVo.setIsBind("1");
                //TODO 保存
                //String id = payRollBindCardService.update(payRollBindCardVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ApiResult();
    }


    /**
     * 根据用户加载绑定信息
     *
     * @return
     */
//    @RequestMapping("loadBindCardInfo")
    @ResponseBody
    public ApiResult loadBindCardInfo(String userId) {

        //绑定用户
//        PayRollBindCardVo cardVo = bindCardService.findInfoByUserId(userId);

        ApiResult apiResult = new ApiResult();
        apiResult.setCode("0000");
        apiResult.setMsg("success");
//        apiResult.setData(cardVo);
        return apiResult;
    }


    /**
     * 通联A账户放款
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("signOrderPayroll")
    public ApiResult signOrderPayroll(@Valid PayRollOp payRollOp, Errors errors) throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        PayLogVO payLogVO = new PayLogVO();
        payLogVO.setToAccName(payRollOp.getAccountName());
        payLogVO.setToAccNo(payRollOp.getAccountNo());
        payLogVO.setTxAmt(new BigDecimal(payRollOp.getAmount().toString()));
        payLogVO.setToBankCode(payRollOp.getBankCode());
        payLogVO.setToMobile(payRollOp.getMobile());
        payLogVO.setTxDate(Integer.valueOf(DateUtils.getDate("yyyyMMdd")));
        payLogVO.setToIdno("123123");
        payLogVO.setTxTime(new Date());
        boolean b = tltAgreementPayService.signOrderPayroll(payLogVO);
        ApiResult apiResult = new ApiResult();
        apiResult.setCode("0000");
//        if (b) {
//            apiResult.setMsg("放款成功");
//        } else {
//            apiResult.setMsg("放款失败");
//        }
        return apiResult;
    }


    /**
     * 通联A账户查询放款
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("querySignOrderPayroll")
    public ApiResult querySignOrderPayroll(@Valid QueryPayOp queryPayOp, Errors errors) throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }

        TlWithholdQueryOP withholdQueryOP = new TlWithholdQueryOP();
        withholdQueryOP.setReqSn(queryPayOp.getReqNo());
        QTransRsp qTransRsp = (QTransRsp) tltAgreementPayService.query(withholdQueryOP);
        if (qTransRsp != null && qTransRsp.getDetails().size() > 0) {
            List<QTDetail> details = qTransRsp.getDetails();
            for (QTDetail detail : details) {
                log.info("查询{} 信息为：{},{},{}", queryPayOp.getReqNo(), detail.getAMOUNT(), detail.getRET_CODE(), detail.getERR_MSG());
            }
        } else {
            log.info("查询放款对象为:", qTransRsp);
        }
        return new ApiResult();
    }


    /**
     * 通联独立2账户放款
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("makeLoans")
    public ApiResult makeLoans(@Valid PayRollOp payRollOp, Errors errors) throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }
        PayLogVO payLogVO = new PayLogVO();
        payLogVO.setToAccName(payRollOp.getAccountName());
        payLogVO.setToAccNo(payRollOp.getAccountNo());
        payLogVO.setTxAmt(new BigDecimal(payRollOp.getAmount()));
        payLogVO.setToBankCode(payRollOp.getBankCode());
        payLogVO.setToMobile(payRollOp.getMobile());
        payLogVO.setTxDate(Integer.valueOf(DateUtils.getDate("yyyyMMdd")));
        payLogVO.setToIdno("123123");
        payLogVO.setTxTime(new Date());
        tltAgreementPayService.tonghuaLoan(payLogVO);
        ApiResult apiResult = new ApiResult();
        apiResult.setCode("0000");
//        if (b) {
//            apiResult.setMsg("放款成功");
//        } else {
//            apiResult.setMsg("放款失败");
//        }
        return apiResult;
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.formatDate(new Date(),"yyyyMMddHHssmm"));

    }

    /**
     * 通联代扣
     *
     * @param agreementPayOP
     * @return
     */
    @RequestMapping("withholdTonglian")
    @ResponseBody
    public ApiResult withholdTonglian(AgreementPayOP agreementPayOP) {
        TlAgreementPayOP payOP = new TlAgreementPayOP();

        payOP.setPayType(1);
        payOP.setUserId("123123");
        payOP.setApplyId("123123");
        payOP.setBindId(agreementPayOP.getBindId());
        payOP.setRealName(agreementPayOP.getRealName());
        payOP.setAmount(agreementPayOP.getAmount());
        payOP.setUserId("123123");
        try {
            TlAgreementPayResultVO resultVO = tltAgreementPayService.agreementPay(payOP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiResult apiResult = new ApiResult();
        apiResult.setMsg("success");
        return apiResult;
    }


    /**
     * 通联B账户查询放款
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("querySignOrderPayrollByTlTwo")
    public ApiResult querySignOrderPayrollByTlTwo(@Valid QueryPayOp queryPayOp, Errors errors) throws Exception {
        // 参数验证结果判断
        if (errors.hasErrors()) {
            throw new BadRequestException(errors);
        }

        TlWithholdQueryOP queryOP = new TlWithholdQueryOP();
        queryOP.setReqSn(queryPayOp.getReqNo());
        queryOP.setTxnDate(queryPayOp.getTxnDate());
        TonglianQueryResultVo tonglianQueryResultVo = tltAgreementPayService.tonghuaLoanQuery(queryOP);
        System.out.println(JsonMapper.toJsonString(tonglianQueryResultVo));
        return new ApiResult();
    }

}
